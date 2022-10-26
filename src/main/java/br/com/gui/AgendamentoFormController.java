package br.com.gui;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import br.com.db.DbException;
import br.com.gui.listeners.DataChangeListener;
import br.com.gui.util.Alerts;
import br.com.gui.util.Constraints;
import br.com.gui.util.Utils;
import br.com.model.entities.Consulta;
import br.com.model.entities.Especializacao;
import br.com.model.entities.Paciente;
import br.com.model.entities.Profissional;
import br.com.model.exceptions.ValidationException;
import br.com.model.services.ConsultaService;
import br.com.model.services.EspecializacaoService;
import br.com.model.services.PacienteService;
import br.com.model.services.ProfissionalService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class AgendamentoFormController implements Initializable {

	private Consulta entidade;

	private ProfissionalService serviceMedico;

	private PacienteService servicePaciente;
	
	private ConsultaService serviceConsulta;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	@FXML
	private DatePicker dpDataAniv;

	@FXML
	private ComboBox<Paciente> comboBoxPaciente;
	
	@FXML
	private ComboBox<Profissional> comboBoxMedico;
	
	@FXML
	private ComboBox<Integer> comboBoxHorario;

	@FXML
	private Label labelErrorPaciente;

	@FXML
	private Label labelErrorHorario;

	@FXML
	private Label labelErrorData;

	@FXML
	private Label labelErrorMedico;

	@FXML
	private ObservableList<Paciente> obsListPaciente;
	
	@FXML
	private ObservableList<Profissional> obsListMedico;
	
	@FXML
	private ObservableList<Integer> obsListHorario;

	public void setEntidade(Consulta entidade) {
		this.entidade = entidade;
	}

	public void setService(ProfissionalService serviceMedico, PacienteService servicePaciente,
			ConsultaService serviceConsulta) {
		this.serviceMedico = serviceMedico;
		this.servicePaciente = servicePaciente;
		this.serviceConsulta = serviceConsulta;
	}

	// esse metodo inscreve a outra interface para ouvir essa interface
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	// esse metodo que gera o novo alerta para o sistema
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entidade == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (serviceConsulta == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entidade = getFormData();
			serviceConsulta.saveOrUptade(entidade);
			// quando � confirmado o salvar ele emite um alerta para atualizar a pagina
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorsMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error savings Object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private Consulta getFormData() {
		Consulta obj = new Consulta();

		ValidationException exception = new ValidationException("Validation Errors");
		obj.setIdConsulta(entidade.getIdConsulta());

		if (dpDataAniv.getValue() == null) {
			exception.addError("dataAniv", "Campo est� vazio!!");
		} else {
			Instant instant = Instant.from(dpDataAniv.getValue().atStartOfDay(ZoneId.systemDefault()));

			obj.setDataConsul(Date.from(instant));
		}

		obj.setPaciente(comboBoxPaciente.getValue());
		obj.setProfissional(comboBoxMedico.getValue());
		obj.setHorario(comboBoxHorario.getValue());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	public void initializeNodes() {
		Utils.formatDatePicker(dpDataAniv, "dd/MM/yyyy");
		initializecomboBoxPaciente();
		initializecomboBoxMedico();
		initializeComboBoxHorario();
	}

	private void initializecomboBoxPaciente() {
		Callback<ListView<Paciente>, ListCell<Paciente>> factory = lv -> new ListCell<Paciente>() {
			@Override
			protected void updateItem(Paciente item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNomePaciente());
			}
		};
		comboBoxPaciente.setCellFactory(factory);
		comboBoxPaciente.setButtonCell(factory.call(null));
	}
	
	private void initializecomboBoxMedico() {
		Callback<ListView<Profissional>, ListCell<Profissional>> factory = lv -> new ListCell<Profissional>() {
			@Override
			protected void updateItem(Profissional item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		comboBoxMedico.setCellFactory(factory);
		comboBoxMedico.setButtonCell(factory.call(null));
	}
	
	private void initializeComboBoxHorario() {
		Callback<ListView<Integer>, ListCell<Integer>> factory = lv -> new ListCell<Integer>() {
			@Override
			protected void updateItem(Integer item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.toString());
			}
		};
		comboBoxHorario.setCellFactory(factory);
		comboBoxHorario.setButtonCell(factory.call(null));
	}

	public void loadAssociatedObjects() {
		if (servicePaciente == null) {
			throw new IllegalStateException("Sem Especializa��es!");
		}
		if (serviceMedico == null) {
			throw new IllegalStateException("Sem Especializa��es!");
		}
		List<Paciente> listPac = servicePaciente.findAll();

		// esse fun��o pega os itens da lista e passa para obsList
		obsListPaciente = FXCollections.observableArrayList(listPac);

		// comboBox s� aceita itens que estejam no ObservableList
		comboBoxPaciente.setItems(obsListPaciente);
		
		List<Profissional> listMed = serviceMedico.findAll();
		obsListMedico = FXCollections.observableArrayList(listMed);
		comboBoxMedico.setItems(obsListMedico);
		
		List<Integer> listHorario = new ArrayList<>();
		for(int i = 8; i <= 17; i++) {
			listHorario.add(i);
		}
		obsListHorario = FXCollections.observableArrayList(listHorario);
		comboBoxHorario.setItems(obsListHorario);
	}

	// Esse metodo inicia os valores no campos
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalStateException("Entity was null!!");
		}

		// se a data na entidade estiver vazia ele manda nulo para a tela
		if (entidade.getDataConsul() != null) {
			dpDataAniv.setValue(LocalDate.parse(sdf.format(entidade.getDataConsul()).toString()));

		}

		if (entidade.getPaciente() == null) {
			comboBoxPaciente.getSelectionModel().selectFirst();
		} else {
			comboBoxPaciente.setValue(entidade.getPaciente());
		}
		
		if (entidade.getProfissional() == null) {
			comboBoxMedico.getSelectionModel().selectFirst();
		} else {
			comboBoxMedico.setValue(entidade.getProfissional());
		}
		
		if (entidade.getHorario() == null) {
			comboBoxHorario.getSelectionModel().selectFirst();
		} else {
			comboBoxHorario.setValue(entidade.getHorario());
		}
	}

	// esse metodo verifica se tem o erro e manda ele para o label
	public void setErrorsMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
//		
//		labelErrorNome.setText(fields.contains("nome") ? errors.get("nome") : "");
//		
//		labelErrorEmail.setText(fields.contains("email") ? errors.get("email") : "");
//
//		labelErrorSalario.setText(fields.contains("salario") ? errors.get("salario") : "");
		
		labelErrorData.setText(fields.contains("dataAniv") ? errors.get("dataAniv") : "");
	}

}
