/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import gestor.GestorParticipante;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.*;

/**
 *
 * @author Martin
 */
public class ListarParticipantes extends javax.swing.JPanel {
    CompetenciaAux compAux;
    /**
     * Creates new form ListarParticipantes
     * @param param
     */
    public ListarParticipantes(CompetenciaAux param) {
        compAux=param;
        
        initComponents();
        
        verNombre();
        llenarTabla();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setLayout(null);

        jButton1.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButton1.setText("Agregar Participante");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(532, 495, 192, 31);

        jButton2.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButton2.setText("Modificar Participante");
        jButton2.setEnabled(false);
        add(jButton2);
        jButton2.setBounds(290, 495, 192, 31);

        jButton3.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButton3.setText("Eliminar Participante");
        jButton3.setEnabled(false);
        jButton3.setMaximumSize(new java.awt.Dimension(150, 31));
        jButton3.setMinimumSize(new java.awt.Dimension(150, 31));
        jButton3.setPreferredSize(new java.awt.Dimension(150, 31));
        add(jButton3);
        jButton3.setBounds(48, 495, 192, 30);

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Correo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(20);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);

        add(jScrollPane1);
        jScrollPane1.setBounds(53, 140, 670, 340);

        jButton4.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButton4.setText("Atras");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(150, 536, 90, 31);

        jButton5.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButton5.setText("Menu P.");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(48, 536, 90, 31);

        jLabel1.setFont(new java.awt.Font("Agency FB", 0, 23)); // NOI18N
        jLabel1.setText("Competencia Seleccionada:");
        add(jLabel1);
        jLabel1.setBounds(55, 90, 190, 40);

        jLabel2.setFont(new java.awt.Font("Agency FB", 0, 23)); // NOI18N
        jLabel2.setText("Nombre Competencia");
        add(jLabel2);
        jLabel2.setBounds(240, 90, 480, 40);

        jLabel4.setFont(new java.awt.Font("Agency FB", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Listar Participantes");
        add(jLabel4);
        jLabel4.setBounds(0, 0, 800, 60);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sports logo released.png"))); // NOI18N
        jButton6.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sports logo pressed.png")));
        jButton6.setBorder(null);
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.setFocusPainted(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add(jButton6);
        jButton6.setBounds(663, 543, 130, 50);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img_general.jpg"))); // NOI18N
        add(jLabel3);
        jLabel3.setBounds(0, 0, 800, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Si esta planificada se debe preguntar si desea eliminar el fixture
        if("Planificada".equals(compAux.getEstado())){
            int respuesta = JOptionPane.showConfirmDialog(null, "Si agrega un participante se eliminara el fixture",
                    "Agregar participante", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                GestorVentanas.get().altaParticipante(compAux);
            }
        }
        else if("Creada".equals(compAux.getEstado()) || "Planificada".equals(compAux.getEstado())){
            GestorVentanas.get().altaParticipante(compAux);
        }
        else{
            JOptionPane.showMessageDialog(null,"<html>La competencia debe estar en<br>"+
                                                "estado 'Creada' o 'Planificada'",
                    "Error al agregar participante", JOptionPane.INFORMATION_MESSAGE);
            GestorVentanas.get().alerta();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        GestorVentanas.get().remove(this);
        GestorVentanas.get().verCompetenciaRecargar(compAux);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        GestorVentanas.get().remove(this);
        GestorVentanas.get().verCompetenciaSalir();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        GestorVentanas.get().integrantes(this);
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    
    private void verNombre(){
        
        jLabel2.setVisible(true);
        jLabel2.setText(compAux.getNombre());
    }
    private void llenarTabla(){
        
        // Llamar a la funcion para rellenar la tabla (devuelve matriz)
        
        ArrayList<ParticipanteAux> listap = GestorParticipante.listarParticipantes(compAux.getId());
        
        DefaultTableModel modelo=(DefaultTableModel) jTable1.getModel();
        int filas=jTable1.getRowCount();
        int i;
        for (i=0;filas>i; i++) {
            modelo.removeRow(0);
        }
        
        for(i=0;i < listap.size();i++){
            
            String fila[]=new String[2];
            
            fila[0]= listap.get(i).getNombre();
            fila[1]= listap.get(i).getCorreo();
            
            modelo.addRow(fila);
        }
        jTable1.setModel(modelo);
        
    }
}
