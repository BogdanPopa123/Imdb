package org.example.guiViews;

import org.example.LoggedUser;
import org.example.Request;
import org.example.Staff;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SolveRequestsGUI extends JFrame {

    public static List<Request> requestList = new ArrayList<>();
    public SolveRequestsGUI(){
        updateList();

        JPanel listRequestsFlow = new JPanel(new FlowLayout());
        JButton listRequestsButton = new JButton("List current Requests");
        listRequestsFlow.add(listRequestsButton);
        listRequestsButton.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.setTitle("Solve Requests");

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);

            textArea.setText("");
            textArea.append(getRequestString());

            JScrollPane scrollPane = new JScrollPane(textArea);
            dialog.add(scrollPane);

            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(null); // Center the dialog on the screen
            dialog.setVisible(true);
        });

        JPanel solveRequestFlow = new JPanel(new FlowLayout());
        SpinnerNumberModel numberModel = new SpinnerNumberModel(1, 1,
                SolveRequestsGUI.requestList.size() > 0 ? SolveRequestsGUI.requestList.size() : 1,
                1);
        JSpinner spinner = new JSpinner(numberModel);
        JButton solveRequest = new JButton("Mark request as solved");
        JButton denyRequest = new JButton("Mark request as denied");
        solveRequestFlow.add(spinner);
        solveRequestFlow.add(solveRequest);
        solveRequestFlow.add(denyRequest);

        if (SolveRequestsGUI.requestList.size() == 0) {
            solveRequest.setEnabled(false);
            denyRequest.setEnabled(false);
            spinner.setEnabled(false);
        }

        if (SolveRequestsGUI.requestList.size() > 0) {
            solveRequest.setEnabled(true);
            denyRequest.setEnabled(true);
            spinner.setEnabled(true);
        }

        solveRequest.addActionListener(e -> {
            int index = (int) spinner.getValue();
            index--;

            Request request = SolveRequestsGUI.requestList.get(index);
            ((Staff)LoggedUser.currentUser).solveRequest(request);
            SolveRequestsGUI.requestList.remove(request);

            if (SolveRequestsGUI.requestList.size() == 0) {
                spinner.setEnabled(false);
                solveRequest.setEnabled(false);
                denyRequest.setEnabled(false);
            }

            if (SolveRequestsGUI.requestList.size() > 0) {
                spinner.setEnabled(true);
                solveRequest.setEnabled(true);
                denyRequest.setEnabled(true);
            }

            spinner.setValue(1);
            numberModel.setMaximum(SolveRequestsGUI.requestList.size() > 0 ?
                    SolveRequestsGUI.requestList.size() : 1);
        });

        denyRequest.addActionListener(e -> {
            int index = (int) spinner.getValue();
            index--;

            Request request = SolveRequestsGUI.requestList.get(index);
            ((Staff)LoggedUser.currentUser).denyRequest(request);
            SolveRequestsGUI.requestList.remove(request);

            if (SolveRequestsGUI.requestList.size() == 0) {
                spinner.setEnabled(false);
                solveRequest.setEnabled(false);
                denyRequest.setEnabled(false);
            }

            if (SolveRequestsGUI.requestList.size() > 0) {
                spinner.setEnabled(true);
                solveRequest.setEnabled(true);
                denyRequest.setEnabled(true);
            }

            spinner.setValue(1);
            numberModel.setMaximum(SolveRequestsGUI.requestList.size() > 0 ?
                    SolveRequestsGUI.requestList.size() : 1);
        });

        setLayout(new GridLayout(3, 1));
        add(listRequestsFlow);
        add(solveRequestFlow);

        setSize(600, 400);
        setVisible(true);
    }

    private void updateList() {
        SolveRequestsGUI.requestList = new ArrayList<>();

        for (Request request : Request.RequestManager.getAdminTeamRequests()) {
            SolveRequestsGUI.requestList.add(request);
        }

        Staff staff = (Staff) LoggedUser.currentUser;

        for (Request request : staff.getRequests()) {
            SolveRequestsGUI.requestList.add(request);
        }
    }

    private String getRequestString(){
        String type = null;
        StringBuilder sb = new StringBuilder();
        updateList();

        if (SolveRequestsGUI.requestList.size() == 0) {
            sb.append("You currently have no active request\n");
            return sb.toString();
        }

        sb.append("These are your requests:" +
                LoggedUser.currentUser.getUsername() + "\n");

        int index = 0;
        for (Request request : SolveRequestsGUI.requestList) {
            index++;
            sb.append("    " + index + ") Created at: " +
                    request.getCreationTime().toString() +
                    "\n    Type: " + request.getRequestType().toString() +
                    "\n    Description: " +
                    request.getDescription() + "\n");
        }

        return sb.toString();
    }
}
