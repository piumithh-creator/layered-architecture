package lk.ijse.foundation_360.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.foundation_360.bo.BOFactory;
import lk.ijse.foundation_360.bo.custom.ApprovalBO;
import lk.ijse.foundation_360.entity.Approval;
import java.net.URL;
import java.util.ResourceBundle;

public class ApprovalController implements Initializable {

    @FXML private TableView<Approval> tblApprovals;
    @FXML private TableColumn<Approval, String> colReferenceType, colReferenceId, colRequestedBy, colStatus, colRequestDate;
    @FXML private TextArea txtRemarks;

    private final ObservableList<Approval> approvalList = FXCollections.observableArrayList();
    private final ApprovalBO approvalBO = (ApprovalBO) BOFactory.getInstance().getBO(BOFactory.BOType.APPROVAL);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colReferenceType.setCellValueFactory(new PropertyValueFactory<>("referenceType"));
        colReferenceId.setCellValueFactory(new PropertyValueFactory<>("referenceId"));
        colRequestedBy.setCellValueFactory(new PropertyValueFactory<>("requestedBy"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRequestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        tblApprovals.setItems(approvalList);
        loadApprovals();
    }

    private void loadApprovals() {
        approvalList.clear();
        try { approvalList.setAll(approvalBO.getPendingApprovals()); } catch (Exception e) {  }
    }

    @FXML void btnApproveOnAction(ActionEvent event) { processApproval("APPROVED"); }
    @FXML void btnRejectOnAction(ActionEvent event) { processApproval("REJECTED"); }

    private void processApproval(String newStatus) {
        Approval selected = tblApprovals.getSelectionModel().getSelectedItem();
        if (selected == null) { new Alert(Alert.AlertType.WARNING, "Please select a request").show(); return; }
        try {
            if (approvalBO.updateApprovalStatus(selected.getReferenceId(), selected.getReferenceType(), newStatus, txtRemarks.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Request " + newStatus).show();
                loadApprovals(); txtRemarks.clear();
            }
        } catch (Exception e) { new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show(); }
    }
}
