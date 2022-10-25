package br.com.gui;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.application.Main;
import br.com.db.DbException;
import br.com.gui.listeners.DataChangeListener;
import br.com.gui.util.Alerts;
import br.com.gui.util.Utils;
import br.com.model.entities.Consulta;
import br.com.model.entities.Especializacao;
import br.com.model.services.ConsultaService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AgendamentoListController implements Initializable, DataChangeListener{


	private ConsultaService ConsultaService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@FXML
	private Button btNovo;
	
	@FXML
	private Button btBuscar;
	
	@FXML
	private TextField txtCampoNome;
	
	@FXML
	private TextField txtCampoCpf;
	
	@FXML
	private DatePicker dpDataConsulta;


	@FXML
	private TableView<Consulta> tableViewConsulta;

	@FXML
	private TableColumn<Consulta, String> tableColumnNome;

	@FXML
	private TableColumn<Consulta, Date> tableColumnData;

	@FXML
	private TableColumn<Consulta, String> tableColumnMedico;
	
	@FXML
	private TableColumn<Consulta, String> tableColumnStatus;

	@FXML
	private TableColumn<Consulta, String> tableColumnEspec;
	
	@FXML
	private TableColumn<Consulta, Consulta> tableColumnEDIT;

	@FXML
	private TableColumn<Consulta, Consulta> tableColumnREMOVE;
	
	private ObservableList<Consulta> obsList;
	
	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Consulta obj = new Consulta();
		createDialogForm(obj, "/gui/MedicoForm.fxml", parentStage);
		
	}
	
	@FXML
	public void onBtBuscar(ActionEvent event) {
//		if(txtCampoBuscar.getText() == null || txtCampoBuscar.getText().trim().equals("")) {
			updateTableView();
			initializeNodes();
//		}else {
//			List<Consulta> list = medicoService.findByNome(txtCampoBuscar.getText());
//			obsList = FXCollections.observableArrayList(list);
//			tableViewConsulta.setItems(obsList);
//			initializeNodes();
//		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}
	
	public void setConsultaService(ConsultaService service) {
		this.ConsultaService = service;
	}
	
	public void updateTableView() {
		if (ConsultaService == null) {
			throw new IllegalStateException("Service was null");
		}

		List<Consulta> list = ConsultaService.findAll();
		// somente um ObservableList pode passar parametros para o setItems
		obsList = FXCollections.observableArrayList(list);
		tableViewConsulta.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void initializeNodes() {
		tableColumnNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getPaciente().getNomePaciente()));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dataConsul"));
		tableColumnMedico.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getProfissional().getNome()));
		tableColumnEspec.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getProfissional().getEspecializacao().getNomeEspeci()));
//		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>(""));
		Utils.formatTableColumnDate(tableColumnData, "dd/MM/yyyy");
		// essa metodo faz a tabela acompanhar o tamanho da tela
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewConsulta.prefHeightProperty().bind(stage.heightProperty());
	}
	
	private void createDialogForm(Consulta obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			// esse metodo injeta o paciente iniciando o objeto
			// sempre que tem que ter um objeto no formulario
			// precisa injetar ele aqui
			MedicoFormController controller = loader.getController();
//			controller.setEntidade(obj);
//			controller.setService(new ConsultaService(), new EspecializacaoService());
			controller.loadAssociatedObjects();
			// esse metodo que gera a atualização após salvar um novo departamento
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();

			dialogStage.setTitle("Modificar Médico");
			// passa o FXML departmentForm como cena
			dialogStage.setScene(new Scene(pane));
			// Essa janela não pode ser redimencionada
			dialogStage.setResizable(false);
			// Informa qual é o stage pai dessa nova janela
			dialogStage.initOwner(parentStage);
			// informa que não pode mexer no programa sem fechar essa janela
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	// ESSE METODO CRIA OS BOTÕES DE EDITAR NO PAINEL
	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Consulta, Consulta>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Consulta obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(obj, "/gui/MedicoForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	// ESSE METODO CRIA OS BOTÕES DE REMOVER NO PAINEL
	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Consulta, Consulta>() {
			private final Button button = new Button("Delete");

			@Override
			protected void updateItem(Consulta obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}

		});
	}
	
	private void removeEntity(Consulta obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
		
		if(result.get() == ButtonType.OK) {
			if(ConsultaService == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				ConsultaService.remove(obj);
				updateTableView();
			}catch (DbException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

}
