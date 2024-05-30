/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package biomons.bio.monsters;
import java.util.*;
/**
 *
 * @author pedro
 */
public class TelaQuiz extends javax.swing.JFrame {
    public int indexPergunta =1;
    public int vidaJogador;
    public int vidaInimigo;
    public int acertos;
    public int respondidas;
    public boolean acabou = false;
    public boolean perdeu = false;
    public boolean resp1Certa;
    public boolean resp2Certa;
    public boolean resp3Certa;
    public boolean resp4Certa;
    public boolean perguntaRespondida = false;
    public List<Pergunta> perguntas = new ArrayList<Pergunta>();
    public List<Resposta> respostas = new ArrayList<Resposta>();
    
    //adiciona as perguntas de uma dificuldade do banco de dados para uma lista randomizada
    public void addPerguntas (int dificuldade){
        /*int k = //count de perguntas na dificuldade escolhida
        for (int i=1; i=<k; i++){
            //perguntas.add(pergunta com nivelDificulade=dificulade);
        }*/
        //teste
        Pergunta pergunta1 = new Pergunta(1,"pergunta 1", 1);
        Pergunta pergunta2 = new Pergunta(1,"pergunta 2", 2);
        perguntas.add(pergunta1);
        perguntas.add(pergunta2);
        
        perguntas = randPerguntas(perguntas);
    } 
    
    //adiciona as respostas de uma pergunta do banco de dados para uma lista randomizada
    public void addRespostas(int idPergunta){
        respostas = new ArrayList<Resposta>();
        /*for (int i=1; i=<4; i++){
//            respostas.add(resposta com idPergunta=idPergunta e idResposta diferente do anterior)
        }*/
        //teste
        Resposta resp1 = new Resposta(true,"correta",1);
        RespostaErrada resp2 = new RespostaErrada("errada1",1,"correct");
        RespostaErrada resp3 = new RespostaErrada("errada2",1,"correct");
        RespostaErrada resp4 = new RespostaErrada("errada3",1,"correct");
        respostas.add(resp1);
        respostas.add(resp2);
        respostas.add(resp3);
        respostas.add(resp4);
        
        respostas = randRespostas(respostas);
    }
        
    //randomiza uma lista de perguntas
    public List<Pergunta> randPerguntas(List<Pergunta> perguntas){
        int numPerguntas = perguntas.size();
        Random rand = new Random();
        List<Pergunta> newPerguntas = new ArrayList<>();
        List<Integer> contagem = new ArrayList<>();
        for (int i =0; i<numPerguntas ; i++){
            contagem.add(i);
        }
        while(newPerguntas.size()<numPerguntas){
            int index = rand.nextInt(contagem.size());
            newPerguntas.add(perguntas.get(index));
            contagem.remove(index);
        }
        return newPerguntas;
    }
    
    //randomiza uma lista de respostas
    public List<Resposta> randRespostas(List<Resposta> respostas){
        Random rand = new Random();
        List<Resposta> newRespostas = new ArrayList<>();
        List<Integer> contagem = new ArrayList<>();
        for (int i =0; i<= 3; i++){
            contagem.add(i);
        }
        while(newRespostas.size()<4){
            int index = rand.nextInt(contagem.size());
            newRespostas.add(respostas.get(contagem.get(index)));
            contagem.remove(index);
        }
        return newRespostas;
    }
    
    public int getAcertos(){
        return acertos;
    }
    public void setAcertos(int acertos){
        this.acertos= acertos;
    }
    
    public int getRespondidas(){
        return respondidas;
    }
    public void setRespondidas(int respondidas){
        this.respondidas= respondidas;
    }
    
    public boolean getAcabou(){
        return acabou;
    }
    public boolean getPerdeu(){
        return perdeu;
    }
    
    //configuração inicial de uma dificuldade
    public void initDificuldade(int dificuldade, int acertos, int respondidas){
        setAcertos(acertos);
        setRespondidas(respondidas);
        addPerguntas(dificuldade);
        initPergunta();
        int numPerguntas = perguntas.size();
        vidaJogador = (numPerguntas)/2;
        vidaInimigo = ((numPerguntas)/2)+1;
        initBarraVida(vidaInimigoBarra,vidaInimigo);
        initBarraVida(vidaJogadorBarra,vidaJogador);
    }
    
    //configura os valores dos items da tela de uma pergunta 
    public void initPergunta(){
        addRespostas((perguntas.get(indexPergunta)).getIdPergunta());
        resposta1.setText((respostas.get(0)).getResposta());
        resposta2.setText((respostas.get(1)).getResposta());
        resposta3.setText((respostas.get(2)).getResposta());
        resposta4.setText((respostas.get(3)).getResposta());
        resp1Certa=(respostas.get(0)).getCerta();
        resp2Certa=(respostas.get(1)).getCerta();
        resp3Certa=(respostas.get(2)).getCerta();
        resp4Certa=(respostas.get(3)).getCerta();
        areaPerg.setText((perguntas.get(indexPergunta)).getPergunta());
    }
    
        
    //configura uma barra de vida
    public void initBarraVida(javax.swing.JProgressBar barraVida, int vida){
        barraVida.setMaximum(vida);
        barraVida.setMinimum(0);
        barraVida.setValue(vida);
    }
    
    //comportamento de uma resposta
    public void respondido(int respNum){
        if (!perguntaRespondida){
            boolean certa = (respostas.get(respNum)).getCerta();
            if (certa == true){
                vidaInimigo--;
                acertos++;
                vidaInimigoBarra.setValue(vidaInimigo);
                if (vidaInimigo<=0) {
                    perdeu = false;
                    acabou = true;
                }
            } else {
                vidaJogador--;
                vidaJogadorBarra.setValue(vidaJogador);
                if (vidaJogador<=0){
                    perdeu = true;
                    acabou = true;
                }
            }
            perguntaRespondidaFunc(certa, respNum);
        }else{
            indexPergunta++;
            initPergunta();
        }
    }
    
    public void perguntaRespondidaFunc(boolean certa,int respNum){
        resposta1.setText("Continuar");
        resposta2.setText("Continuar");
        resposta3.setText("Continuar");
        resposta4.setText("Continuar");
        if (certa){
            areaPerg.setText("Você acertou!!!");
        } else{
            RespostaErrada respErrada = (RespostaErrada) respostas.get(respNum);
            areaPerg.setText("Correção: " + (respErrada).getCorrect());
        }
        perguntaRespondida = true;
        respondidas++;
    }

    /**
     * Creates new form TelaQuiz
     */
    public TelaQuiz(int dificuldade,int acertos, int respondidas) {

        initComponents();
        
        initDificuldade(dificuldade, acertos, respondidas);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        resposta3 = new javax.swing.JButton();
        resposta4 = new javax.swing.JButton();
        resposta1 = new javax.swing.JButton();
        resposta2 = new javax.swing.JButton();
        vidaJogadorBarra = new javax.swing.JProgressBar();
        vidaInimigoBarra = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaPerg = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        resposta3.setText("resposta3");
        resposta3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resposta3ActionPerformed(evt);
            }
        });

        resposta4.setText("resposta4");
        resposta4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resposta4ActionPerformed(evt);
            }
        });

        resposta1.setText("resposta1");
        resposta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resposta1ActionPerformed(evt);
            }
        });

        resposta2.setText("resposta2");
        resposta2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resposta2ActionPerformed(evt);
            }
        });

        areaPerg.setEditable(false);
        jScrollPane1.setViewportView(areaPerg);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(vidaInimigoBarra, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addComponent(resposta3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resposta1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vidaJogadorBarra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                    .addComponent(resposta2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resposta4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 270, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(vidaInimigoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 382, Short.MAX_VALUE)
                        .addComponent(vidaJogadorBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(resposta2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(resposta1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(resposta3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(resposta4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void resposta3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resposta3ActionPerformed
        respondido(2);
    }//GEN-LAST:event_resposta3ActionPerformed

    private void resposta4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resposta4ActionPerformed
        respondido(3);
    }//GEN-LAST:event_resposta4ActionPerformed

    private void resposta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resposta1ActionPerformed
        respondido(0);
    }//GEN-LAST:event_resposta1ActionPerformed

    private void resposta2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resposta2ActionPerformed
        respondido(1);
    }//GEN-LAST:event_resposta2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaQuiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaQuiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaQuiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaQuiz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane areaPerg;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton resposta1;
    private javax.swing.JButton resposta2;
    private javax.swing.JButton resposta3;
    private javax.swing.JButton resposta4;
    private javax.swing.JProgressBar vidaInimigoBarra;
    private javax.swing.JProgressBar vidaJogadorBarra;
    // End of variables declaration//GEN-END:variables
}
