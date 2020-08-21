package sample;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class PrintReport extends JFrame {
    public void showReport(Connection conn, String reportFile) throws JRException, URISyntaxException {
        URI uri = getClass().getResource(reportFile).toURI();
        String reportSrcFile = uri.getPath();
        //String reportSrcFile = "C:\\Users\\Windows 10\\IdeaProjects\\projekat\\resources\\reports\\vozniPark.jrxml";
        //String reportsDir = getClass().getResource("/reports").getFile();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        // Fields for resources path
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        //parameters.put("reportsDirPath", reportsDir);
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(parameters);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
    }
}