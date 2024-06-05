/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package biomons.bio.monsters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author pedro
 */
public class TelaQuiz extends javax.swing.JPanel {
    private int indexPergunta =1;
    private int vidaJogador;
    private int vidaInimigo;
    private int acertos;
    private int respondidas;
    private boolean acabou = false;
    private boolean perdeu = false;
    private boolean resp1Certa;
    private boolean resp2Certa;
    private boolean resp3Certa;
    private boolean resp4Certa;
    private boolean perguntaRespondida = false;
    private List<Pergunta> perguntas = new ArrayList<Pergunta>();
    private List<Resposta> respostas = new ArrayList<Resposta>();
    
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
        vidaInimigo = (numPerguntas)/2;
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
            } else {
                vidaJogador--;
                vidaJogadorBarra.setValue(vidaJogador);
            }
            perguntaRespondidaFunc(certa, respNum);
        } else if (vidaInimigo<=0){
            acabou = true;
        }else if (vidaJogador<=0){
            perdeu = true;
            acabou = true;
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
     * Creates new form panelTest
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

        resposta1 = new javax.swing.JButton();
        vidaJogadorBarra = new javax.swing.JProgressBar();
        vidaInimigoBarra = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaPerg = new javax.swing.JTextPane();
        resposta2 = new javax.swing.JButton();
        resposta3 = new javax.swing.JButton();
        resposta4 = new javax.swing.JButton();

        resposta1.setText("jButton1");
        resposta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resposta1ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(areaPerg);

        resposta2.setText("jButton2");
        resposta2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resposta2ActionPerformed(evt);
            }
        });

        resposta3.setText("jButton3");
        resposta3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resposta3ActionPerformed(evt);
            }
        });

        resposta4.setText("jButton4");
        resposta4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resposta4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(resposta3)
                            .addComponent(resposta1))
                        .addGap(36, 36, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(resposta2)
                            .addComponent(resposta4))
                        .addGap(43, 43, 43)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(vidaJogadorBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vidaInimigoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(vidaJogadorBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(vidaInimigoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(resposta1)
                            .addComponent(resposta2))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(resposta3)
                            .addComponent(resposta4))))
                .addGap(39, 39, 39))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void resposta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resposta1ActionPerformed
        respondido(0);
    }//GEN-LAST:event_resposta1ActionPerformed

    private void resposta2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resposta2ActionPerformed
        respondido(1);
    }//GEN-LAST:event_resposta2ActionPerformed

    private void resposta3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resposta3ActionPerformed
        respondido(2);
    }//GEN-LAST:event_resposta3ActionPerformed

    private void resposta4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resposta4ActionPerformed
        respondido(3);
    }//GEN-LAST:event_resposta4ActionPerformed
                       


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
