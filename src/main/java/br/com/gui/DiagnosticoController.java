package br.com.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.application.Main;
import br.com.db.DbException;
import br.com.gui.listeners.DataChangeListener;
import br.com.gui.util.Alerts;
import br.com.gui.util.Utils;
import br.com.model.entities.Diagnostico;
import br.com.model.services.DiagnosticoService;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class DiagnosticoController implements Initializable, DataChangeListener {

	DiagnosticoService service = new DiagnosticoService();

	@FXML
	private Button btNovo;

	@FXML
	private Button btBuscar;

	@FXML
	private TextField txtCampoBuscar;

	@FXML
	private TableView<Diagnostico> tableViewDiagnostico;

	@FXML
	private TableColumn<Diagnostico, String> tableColumnNome;

	@FXML
	private TableColumn<Diagnostico, Diagnostico> tableColumnEDIT;

	@FXML
	private TableColumn<Diagnostico, Diagnostico> tableColumnREMOVE;

	private ObservableList<Diagnostico> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Diagnostico obj = new Diagnostico();
		createDialogForm(obj, "/gui/DiagnosticoForm.fxml", parentStage);

	}

	public void setService(DiagnosticoService service) {
		this.service = service;
	}

	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();

	}

	private void initializeNodes() {
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeDiag"));
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDiagnostico.prefHeightProperty().bind(stage.heightProperty());

	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		List<Diagnostico> list = service.findAll();
		// somente um ObservableList pode passar parametros para o setItems
		obsList = FXCollections.observableArrayList(list);
		tableViewDiagnostico.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}
	
	private void createDialogForm(Diagnostico obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			// esse metodo injeta o paciente iniciando o objeto
			// sempre que tem que ter um objeto no formulario
			// precisa injetar ele aqui
			DiagnosticoFormController controller = loader.getController();
			controller.setEntidade(obj);
			controller.setService(new DiagnosticoService());
			// esse metodo que gera a atualiza��o ap�s salvar um novo departamento
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();

			dialogStage.setTitle("Diagnostico");
			// passa o FXML departmentForm como cena
			dialogStage.setScene(new Scene(pane));
			// Essa janela n�o pode ser redimencionada
			dialogStage.setResizable(false);
			// Informa qual � o stage pai dessa nova janela
			dialogStage.initOwner(parentStage);
			// informa que n�o pode mexer no programa sem fechar essa janela
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	// ESSE METODO CRIA OS BOT�ES DE EDITAR NO PAINEL
		private void initEditButtons() {
			tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
			tableColumnEDIT.setCellFactory(param -> new TableCell<Diagnostico, Diagnostico>() {
				private final Button button = new Button("Editar");

				@Override
				protected void updateItem(Diagnostico obj, boolean empty) {
					super.updateItem(obj, empty);
					if (obj == null) {
						setGraphic(null);
						return;
					}
					setGraphic(button);
					button.setOnAction(
							event -> createDialogForm(obj, "/gui/DiagnosticoForm.fxml", Utils.currentStage(event)));
				}
			});
		}

		// ESSE METODO CRIA OS BOT�ES DE REMOVER NO PAINEL
		private void initRemoveButtons() {
			tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
			tableColumnREMOVE.setCellFactory(param -> new TableCell<Diagnostico,Diagnostico>() {
				private final Button button = new Button("Delete");

				@Override
				protected void updateItem(Diagnostico obj, boolean empty) {
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
		
		private void removeEntity(Diagnostico obj) {
			Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");
			
			if(result.get() == ButtonType.OK) {
				if(service == null) {
					throw new IllegalStateException("Service was null");
				}
				try {
					service.remove(obj);
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
