/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import gestor.GestorParticipante;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import modelo.CompetenciaAux;
import sonidos.alertaSuave;

/**
 *
 * @author GabrielV
 */
public class AltaParticipante extends javax.swing.JPanel {
    
    CompetenciaAux compAux;
    ImageIcon avatar= new ImageIcon(getClass().getResource("/imagenes/img_avatar.jpg"));
    alertaSuave alerta= new alertaSuave();
    
    /*
     * Creates new form AltaParticipante
     */
    public AltaParticipante(CompetenciaAux param) {
        compAux=param;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jIcon1 = new javax.swing.JLabel();
        jIcon2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(800, 600));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setLayout(null);

        jTextField1.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        ((AbstractDocument)jTextField1.getDocument()).setDocumentFilter(new LimitadorTextField(50));
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
        });
        add(jTextField1);
        jTextField1.setBounds(475, 198, 200, 30);

        jTextField2.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        ((AbstractDocument)jTextField2.getDocument()).setDocumentFilter(new LimitadorTextField(50));
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField2FocusGained(evt);
            }
        });
        add(jTextField2);
        jTextField2.setBounds(475, 245, 200, 30);
        jTextField2.addKeyListener(new KeyAdapter() {

            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
            }

        });

        jLabel1.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jLabel1.setText("Correo Electrónico:");
        add(jLabel1);
        jLabel1.setBounds(355, 201, 110, 30);

        jLabel2.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jLabel2.setText("Nombre:");
        add(jLabel2);
        jLabel2.setBounds(355, 245, 110, 30);

        jButton1.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(585, 285, 90, 30);

        jButton2.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButton2.setText("Atras");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add(jButton2);
        jButton2.setBounds(136, 530, 70, 31);

        jButton5.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButton5.setText("Borrar Imagen");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add(jButton5);
        jButton5.setBounds(190, 420, 140, 31);

        jButton3.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButton3.setText("Adjuntar Imagen");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3);
        jButton3.setBounds(40, 420, 140, 31);
        add(jLabel3);
        jLabel3.setBounds(41, 119, 290, 290);
        avatar=new ImageIcon(avatar.getImage().getScaledInstance(jLabel3.getWidth(), jLabel3.getHeight(), Image.SCALE_DEFAULT));
        jLabel3.setIcon(avatar);

        jButton4.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jButton4.setText("Menu P.");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4);
        jButton4.setBounds(41, 530, 86, 31);

        jLabel5.setFont(new java.awt.Font("Agency FB", 0, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Alta de Participante");
        add(jLabel5);
        jLabel5.setBounds(1, 2, 800, 60);

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

        jLabel6.setForeground(new java.awt.Color(255, 0, 51));
        jLabel6.setText("<html>Ingrese<br>      un correo");
        jLabel6.setVisible(false);
        add(jLabel6);
        jLabel6.setBounds(720, 198, 80, 30);

        jLabel7.setForeground(new java.awt.Color(255, 0, 51));
        jLabel7.setText("<html>Ingrese<br> un nombre");
        jLabel7.setVisible(false);
        add(jLabel7);
        jLabel7.setBounds(720, 245, 80, 30);

        jIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/warning64x64.png"))); // NOI18N
        jIcon1.setVisible(false);
        add(jIcon1);
        jIcon1.setBounds(680, 198, 32, 30);

        jIcon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/warning64x64.png"))); // NOI18N
        jIcon2.setVisible(false);
        add(jIcon2);
        jIcon2.setBounds(680, 245, 32, 30);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/img_general.jpg"))); // NOI18N
        add(jLabel4);
        jLabel4.setBounds(0, 0, 800, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        // Alta de participante
        
        boolean nomb=verificarNombre();
        boolean corr=verificarCorreo();
        if(nomb || corr){
            Thread thread = new Thread(alerta);
            thread.start();
        }
        else{
            boolean continuar=true;
            if("Planificada".equals(compAux.getEstado())){
                int respuesta = JOptionPane.showConfirmDialog(null, "Se eliminará el fixture, ¿desea continuar?",
                        "Agregar participante", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    continuar=true;
                }
                else continuar=false;
            }
            if (continuar){
                // Se extraen todos los datos para pasar al gestor de alta de participante
                GestorParticipante.altaParticipante(compAux.getId(),
                        jTextField2.getText(), jTextField1.getText(), null/*FileInputStream*/);
                // Pasar a CREADA .. Eliminar el fixture
                // (dentro del alta de participante se hace)
                
                JOptionPane.showMessageDialog(null, "Participante dado de alta exitosamente", "", JOptionPane.INFORMATION_MESSAGE);
                
                V.get().remove(this);
                V.get().listarParticipantesVolver2(compAux);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        elegirImagen();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        V.get().remove(this);
        V.get().listarParticipantesSalir();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        V.get().remove(this);
        V.get().listarParticipantesVolver();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        V.get().integrantes(this);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jLabel3.setIcon(avatar);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        jTextField1.setBackground(Color.WHITE);
    }//GEN-LAST:event_jTextField1FocusGained

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
        jTextField2.setBackground(Color.WHITE);
    }//GEN-LAST:event_jTextField2FocusGained
    
    
    private boolean verificarCorreo(){
        
        String aux = jTextField1.getText();
        
        // ELimina espacios al inicio y final
        aux = aux.trim();
        jTextField1.setText(aux);
        
        if(aux.length() == 0 ){
            // El usuario no ingreso correo de participante .
            jLabel6.setText("<html>Ingrese<br>un correo");
            jLabel6.setVisible(true);
            jIcon1.setVisible(true);
            jTextField1.setBackground(new Color(0xFF, 0x80, 0x80));
            return true;
        }
        else if (!correoValido()){
            // El correo no es valido
            jLabel6.setText("<html>Correo<br>no válido");
            jLabel6.setVisible(true);
            jIcon1.setVisible(true);
            jTextField1.setBackground(new Color(0xFF, 0x80, 0x80));
            return true;
        }
        else if (gestor.GestorParticipante.verificarCorreo(compAux.getId(), aux)){
            // El correo para esa competencia ya existe.
            jLabel6.setText("<html>Correo<br>ya existe");
            jLabel6.setVisible(true);
            jIcon1.setVisible(true);
            jTextField1.setBackground(new Color(0xFF, 0x80, 0x80));
            return true;
            }
        else {
            jLabel6.setVisible(false);
            jIcon1.setVisible(false);
            return false;
        }
    }
    private boolean correoValido() {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(jTextField1.getText());
        return m.matches();
    }
    
    private boolean verificarNombre(){
        
        String aux = jTextField2.getText();
        
        // ELimina espacios al inicio y final
        aux = aux.trim();
        jTextField2.setText(aux);
        
        if(aux.length() == 0 ){
            // El usuario no ingreso nombre de participante.
            jLabel7.setText("<html>Ingrese<br>un nombre");
            jLabel7.setVisible(true);
            jIcon2.setVisible(true);
            jTextField2.setBackground(new Color(0xFF, 0x80, 0x80));
            return true;
        }
        else if (gestor.GestorParticipante.verificarNombre(compAux.getId(), aux)){
            // Nombre de participante ya existe, para esa competencia.
            jLabel7.setText("<html>Nombre<br>ya existe");
            jLabel7.setVisible(true);
            jIcon2.setVisible(true);
            jTextField2.setBackground(new Color(0xFF, 0x80, 0x80));
            return true;
            }
        else {
            jLabel7.setVisible(false);
            jIcon2.setVisible(false);
            return false;
        }
    }
    
    private void elegirImagen(){
        // Adjuntar foto
        int resultado;
        
        JFileChooser jFileChooser1= new JFileChooser();
        
        //Filtro del FileChooser
        FileFilter filtro = new FileNameExtensionFilter(
                "Imagenes JPG", "jpg"); /* ImageIO.getReaderFileSuffixes()  para todas*/
        jFileChooser1.setFileFilter(filtro);
        jFileChooser1.setAcceptAllFileFilterUsed(false);
        
        resultado= jFileChooser1.showOpenDialog(this);
        
        if (JFileChooser.APPROVE_OPTION == resultado){
            
            File fichero = jFileChooser1.getSelectedFile();
            
            try{
                
                ImageIcon icon = new ImageIcon(fichero.toString());
                
                Icon icono = new ImageIcon(icon.getImage().getScaledInstance(jLabel3.getWidth(),
                        jLabel3.getHeight(), Image.SCALE_DEFAULT));
                
                jLabel3.setIcon(icono);
                
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Error abriendo la imagen "+ ex);
            }
            
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jIcon1;
    private javax.swing.JLabel jIcon2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
