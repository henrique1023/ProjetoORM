package br.com.gui;

import java.net.URL;
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
import br.com.model.entities.Especializacao;
import br.com.model.exceptions.ValidationException;
import br.com.model.services.EspecializacaoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EspecializacaoFormController implements Initializable {

	private Especializacao entidade;

	private EspecializacaoService service;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	@FXML
	private TextField txtNome;

	@FXML
	private Label labelErrorNome;

	public void setEntidade(Especializacao entidade) {
		this.entidade = entidade;
	}

	public void setService(EspecializacaoService service) {
		this.service = service;
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
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entidade = getFormData();
			service.saveOrUptade(entidade);
			// quando � confirmado o salvar ele emite um alerta para atualizar a pagina
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {
			setErrorsMessages(e.getErrors());
		} catch (DbException e) {
			Alerts.showAlert("Error savings Object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private Especializacao getFormData() {
		Especializacao obj = new Especializacao();

		ValidationException exception = new ValidationException("Validation Errors");

		obj.setIdEspeci(entidade.getIdEspeci());
		obj.setNomeEspeci(txtNome.getText());

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "Campo n�o preenchido");
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
		Constraints.setTextFieldMaxLength(txtNome, 30);
	}

	// Esse metodo inicia os valores no campos
	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalStateException("Entity was null!!");
		}
		txtNome.setText(entidade.getNomeEspeci());
	}

	// esse metodo verifica se tem o erro e manda ele para o label
	public void setErrorsMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelErrorNome.setText(fields.contains("nome") ? errors.get("nome") : "");
	}

}
