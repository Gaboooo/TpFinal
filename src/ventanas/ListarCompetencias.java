
package ventanas;

import gestor.GestorCD;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import modelo.CompetenciaAux;

public class ListarCompetencias extends javax.swing.JPanel {
    
    ArrayList<CompetenciaAux> listaprueba;
    
    /**
     * Creates new form Auxiliar
     */
    
    public ListarCompetencias() {
        initComponents();
        textFieldCompetencia.requestFocusInWindow();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        labelCompetencia = new javax.swing.JLabel();
        labelCompetencia2 = new javax.swing.JLabel();
        textFieldCompetencia = new javax.swing.JTextField();
        comboBoxDeporte = new javax.swing.JComboBox();
        comboBoxEstado = new javax.swing.JComboBox();
        comboBoxModalidad = new javax.swing.JComboBox();
        labelModalidad = new javax.swing.JLabel();
        labelEstado = new javax.swing.JLabel();
        labelDeporte = new javax.swing.JLabel();
        jButtonBuscar = new javax.swing.JButton();
        jButtonAtras = new javax.swing.JButton();
        jButtonCrearCompetencia = new javax.swing.JButton();
        jButtonVerCompetencia = new javax.swing.JButton();
        jButtonTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonIntegrantes = new javax.swing.JButton();
        jLabelFondo = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setLayout(null);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros de búsqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 0, 18))); // NOI18N

        labelCompetencia.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        labelCompetencia.setText("Nombre de la Competencia");

        labelCompetencia2.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        labelCompetencia2.setText("(contiene)");

        textFieldCompetencia.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        ((AbstractDocument)textFieldCompetencia.getDocument()).setDocumentFilter(new LimitadorTextField(50));

        String[] listaNombresDeportes = gestor.GestorCD.getListaDeportes();
        String[] listaND= new String[listaNombresDeportes.length+1];
        listaND[0]="";
        for(int j=0; j<listaNombresDeportes.length; j++){
            listaND[j+1]=listaNombresDeportes[j];
        }
        Arrays.sort(listaND);
        comboBoxDeporte.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        comboBoxDeporte.setModel(new javax.swing.DefaultComboBoxModel<String>(listaND));

        comboBoxEstado.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        comboBoxEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Creada", "Planificada", "En disputa", "Finalizada" }));

        comboBoxModalidad.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        String[] listaModalidades = gestor.GestorCD.getListaModalidades();
        String[] listaM= new String[listaModalidades.length+1];
        listaM[0]="";
        for(int j=0; j<listaModalidades.length; j++){
            listaM[j+1]=listaModalidades[j];
        }
        Arrays.sort(listaM);
        comboBoxModalidad.setModel(new javax.swing.DefaultComboBoxModel(listaM));

        labelModalidad.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        labelModalidad.setText("Modalidad");

        labelEstado.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        labelEstado.setText("Estado");

        labelDeporte.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        labelDeporte.setText("Deporte");

        jButtonBuscar.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButtonBuscar.setText("Buscar");
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelCompetencia, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(textFieldCompetencia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelModalidad, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelDeporte, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelEstado, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(labelCompetencia2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(comboBoxDeporte, javax.swing.GroupLayout.Alignment.LEADING, 0, 136, Short.MAX_VALUE)
                        .addComponent(comboBoxModalidad, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboBoxEstado, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelCompetencia)
                .addGap(3, 3, 3)
                .addComponent(labelCompetencia2)
                .addGap(4, 4, 4)
                .addComponent(textFieldCompetencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(labelDeporte)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBoxDeporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(labelEstado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(labelModalidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBoxModalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonBuscar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        textFieldCompetencia.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
            }

        });

        add(jPanel2);
        jPanel2.setBounds(28, 82, 197, 450);
        jPanel2.getAccessibleContext().setAccessibleName("");

        jButtonAtras.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButtonAtras.setText("Atras");
        jButtonAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasActionPerformed(evt);
            }
        });
        add(jButtonAtras);
        jButtonAtras.setBounds(260, 500, 90, 31);

        jButtonCrearCompetencia.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButtonCrearCompetencia.setText("Crear Competencia");
        jButtonCrearCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCrearCompetenciaActionPerformed(evt);
            }
        });
        add(jButtonCrearCompetencia);
        jButtonCrearCompetencia.setBounds(455, 500, 150, 31);

        jButtonVerCompetencia.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButtonVerCompetencia.setText("Ver Competencia");
        jButtonVerCompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVerCompetenciaActionPerformed(evt);
            }
        });
        add(jButtonVerCompetencia);
        jButtonVerCompetencia.setBounds(625, 500, 140, 31);

        jButtonTitulo.setFont(new java.awt.Font("Agency FB", 0, 36)); // NOI18N
        jButtonTitulo.setForeground(new java.awt.Color(255, 255, 255));
        jButtonTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jButtonTitulo.setText("Listar Competencias Deportivas");
        add(jButtonTitulo);
        jButtonTitulo.setBounds(0, 0, 800, 60);

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Deporte", "Modalidad", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(261, 85, 504, 400);

        jButtonIntegrantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sports logo released.png"))); // NOI18N
        jButtonIntegrantes.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sports logo pressed.png")));
        jButtonIntegrantes.setBorder(null);
        jButtonIntegrantes.setBorderPainted(false);
        jButtonIntegrantes.setContentAreaFilled(false);
        jButtonIntegrantes.setFocusPainted(false);
        jButtonIntegrantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIntegrantesActionPerformed(evt);
            }
        });
        add(jButtonIntegrantes);
        jButtonIntegrantes.setBounds(663, 543, 130, 50);

        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img_general.jpg"))); // NOI18N
        add(jLabelFondo);
        jLabelFondo.setBounds(0, 0, 800, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasActionPerformed
        V.get().remove(this);
        V.get().menuVolver();
    }//GEN-LAST:event_jButtonAtrasActionPerformed

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        if(comboBoxDeporte.getSelectedIndex()==0 && comboBoxModalidad.getSelectedIndex()==0 &&
                comboBoxEstado.getSelectedIndex()==0 && textFieldCompetencia.getText().isEmpty()){
            errorFiltros();
        }
        else{
            
            // Llamar a la funcion para rellenar la tabla (devuelve matriz)
            Object estado = comboBoxEstado.getSelectedItem(); 
            String textEstado = String.valueOf(estado);
            Object deporte = comboBoxDeporte.getSelectedItem(); 
            String textDeporte= String.valueOf(deporte);
            Object modalidad = comboBoxModalidad.getSelectedItem(); 
            String textModalidad= String.valueOf(modalidad);
            
            String nombre=textFieldCompetencia.getText();
            
            if("".equals(textDeporte)){textDeporte=null;}
            if("".equals(textEstado)){textEstado=null;}
            if("".equals(textModalidad)){textModalidad=null;}
            
            if("".equals(textFieldCompetencia.getText())){
                nombre=null;
            }
            
            // Se recuperan las competenciasAux de la base de datos
            listaprueba = GestorCD.listarCD(nombre, textDeporte,textModalidad,textEstado);
            
            
            // Eliminacion de la tabla actual
            DefaultTableModel modelo=(DefaultTableModel) jTable1.getModel();
            int filas=jTable1.getRowCount();
            int i;
            for (i=0;filas>i; i++) {
                modelo.removeRow(0);
            }
            
            // Se le asignan las competencias recuperadas
            for(i=0;i < listaprueba.size();i++){
                
                CompetenciaAux elem=listaprueba.get(i);
                
                String fila[]=new String[4];
                
                fila[0]= elem.getNombre();
                fila[1]= elem.getDeporte();
                fila[2]= elem.getModalidad();
                fila[3]= elem.getEstado();
                
                modelo.addRow(fila);
            }
            jTable1.setModel(modelo);
            
            if(jTable1.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"No se han encontrado resultados.",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jButtonCrearCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCrearCompetenciaActionPerformed
        this.setVisible(false);
        V.get().altaCompetencia();
    }//GEN-LAST:event_jButtonCrearCompetenciaActionPerformed

    private void jButtonVerCompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVerCompetenciaActionPerformed
        
        int row = jTable1.getSelectedRow();
        
        if(row == -1){
            V.get().alerta();
            JOptionPane.showMessageDialog(null,"Debe seleccionar una competencia",
                    "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        
        else{
            
            String nombre=jTable1.getValueAt(row, 0).toString();
            String deporte=jTable1.getValueAt(row, 1).toString();
            String modalidad=jTable1.getValueAt(row, 2).toString();
            String estado=jTable1.getValueAt(row, 3).toString(); 
            
            int idComp=GestorCD.obtenerIDCD(nombre);
            
            CompetenciaAux elem=listaprueba.get(row);
            
            CompetenciaAux compAux= new CompetenciaAux(estado, deporte, modalidad,
                    nombre, elem.getFormaPuntuacion(), idComp);
            V.get().verCompetencia(compAux);
        }
        
        
    }//GEN-LAST:event_jButtonVerCompetenciaActionPerformed

    private void jButtonIntegrantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIntegrantesActionPerformed
        V.get().integrantes(this);
    }//GEN-LAST:event_jButtonIntegrantesActionPerformed

    private void errorFiltros(){
        V.get().alerta();
        JOptionPane.showMessageDialog(null, "Ingrese un filtro de busqueda", "", JOptionPane.INFORMATION_MESSAGE);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox comboBoxDeporte;
    private javax.swing.JComboBox comboBoxEstado;
    private javax.swing.JComboBox comboBoxModalidad;
    private javax.swing.JButton jButtonAtras;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonCrearCompetencia;
    private javax.swing.JButton jButtonIntegrantes;
    private javax.swing.JLabel jButtonTitulo;
    private javax.swing.JButton jButtonVerCompetencia;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel labelCompetencia;
    private javax.swing.JLabel labelCompetencia2;
    private javax.swing.JLabel labelDeporte;
    private javax.swing.JLabel labelEstado;
    private javax.swing.JLabel labelModalidad;
    private javax.swing.JTextField textFieldCompetencia;
    // End of variables declaration//GEN-END:variables
}
