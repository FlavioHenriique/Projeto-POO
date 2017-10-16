/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.etapa2.modelo;

import static java.awt.Color.BLUE;
import java.util.Locale;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Flavio
 */
public class app {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DefaultCategoryDataset dt = new DefaultCategoryDataset();

        dt.setValue(10, "x", "janeiro");
        dt.setValue(20, "x", "fevereiro");
        dt.setValue(15, "x", "março");
        dt.setValue(5, "x", "abril");
        
        JFreeChart grafico = ChartFactory.createBarChart("titulos", "a", "b", dt,
                PlotOrientation.VERTICAL, false, true, false);
        grafico.setBorderPaint(BLUE);
        ChartPanel painel = new ChartPanel(grafico);
        JFrame frame = new JFrame();
        frame.pack();
        frame.add(painel);
        frame.setVisible(true);
    }

}
