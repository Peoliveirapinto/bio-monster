/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package biomons.bio.monsters;

import java.util.concurrent.Semaphore;

/**
 *
 * @author pedro
 */
public class TelaInserirSala extends javax.swing.JPanel {
    private int codeSala;
    private SalaListener listener;
    private final Semaphore semaphore = new Semaphore(0);

    public int getCodeSala(){
        return codeSala;
    }
    
    public void waitSala() throws InterruptedException {
        semaphore.acquire();
    }
    
    /**
     * Creates new form TelaInserirSala
     */
    public TelaInserirSala() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textField = new javax.swing.JTextField();
        começar = new javax.swing.JButton();
        defaultSala = new javax.swing.JButton();

        textField.setText("Código da sala");

        começar.setText("começar");
        começar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                começarActionPerformed(evt);
            }
        });

        defaultSala.setText("começar sem código da sala");
        defaultSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultSalaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(defaultSala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(começar)
                    .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(começar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(defaultSala)
                .addContainerGap(122, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void começarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_começarActionPerformed
        codeSala = Integer.parseInt(textField.getText());
        if (listener !=null){
                listener.onSalaClick();
        }
        semaphore.release();
    }//GEN-LAST:event_começarActionPerformed

    private void defaultSalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultSalaActionPerformed
        codeSala = 1;
        if (listener !=null){
                listener.onSalaClick();
        }
        semaphore.release();
    }//GEN-LAST:event_defaultSalaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton começar;
    private javax.swing.JButton defaultSala;
    private javax.swing.JTextField textField;
    // End of variables declaration//GEN-END:variables
}