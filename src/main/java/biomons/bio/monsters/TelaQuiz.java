/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package biomons.bio.monsters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author pedro
 */
public class TelaQuiz extends javax.swing.JPanel {

    private int indexPergunta = 0;
    private int vidaJogador;
    private int vidaInimigo;
    private int acertos;
    private int respondidas;
    private boolean perdeu = false;
    private boolean resp1Certa;
    private boolean resp2Certa;
    private boolean resp3Certa;
    private boolean resp4Certa;
    private final Semaphore semaphore = new Semaphore(0);
    private QuizListener listener;
    private boolean perguntaRespondida = false;
    private List<Pergunta> perguntas = new ArrayList<Pergunta>();
    private List<Resposta> respostas = new ArrayList<Resposta>();
    private int dificuldade;
    
    public String imagemInimigo(){
        String animal = "arquivo inimigo";
        switch (dificuldade){
            case 1 -> animal = "tucano.png";
            case 2 -> animal = "mico.png";
            case 3 -> animal ="arara.png";
            case 4 -> animal ="loboGuara.png";
        }
        return animal;
    }

    //adiciona as perguntas de uma dificuldade do banco de dados para uma lista randomizada
    public void addPerguntas(int dificuldade, int codeSala) {

        int k = 0;
        try {
            DAO dao = new DAO();
            k = dao.countPerguntas(dificuldade, codeSala);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= k; i++) {
            try {
                DAO dao = new DAO();
                perguntas.add(dao.addPergunta(dificuldade, codeSala, i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // perguntas = randPerguntas(perguntas);
    }

    //adiciona as respostas de uma pergunta do banco de dados para uma lista randomizada
    public void addRespostas(int idPergunta) {
        respostas = new ArrayList<Resposta>();
        for (int i = 1; i <= 4; i++) {
            try {
                DAO dao = new DAO();
                respostas.add(dao.addResposta(idPergunta, i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        respostas = randRespostas(respostas);
    }

    //randomiza uma lista de perguntas
    public List<Pergunta> randPerguntas(List<Pergunta> perguntas) {
        int numPerguntas = perguntas.size();
        Random rand = new Random();
        List<Pergunta> newPerguntas = new ArrayList<>();
        List<Integer> contagem = new ArrayList<>();
        for (int i = 0; i < numPerguntas; i++) {
            contagem.add(i);
        }
        while (newPerguntas.size() < numPerguntas) {
            int index = rand.nextInt(contagem.size());
            newPerguntas.add(perguntas.get(index));
            contagem.remove(index);
        }
        return newPerguntas;
    }

    //randomiza uma lista de respostas
    public List<Resposta> randRespostas(List<Resposta> respostas) {
        Random rand = new Random();
        List<Resposta> newRespostas = new ArrayList<>();
        List<Integer> contagem = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            contagem.add(i);
        }
        while (newRespostas.size() < 4) {
            int index = rand.nextInt(contagem.size());
            newRespostas.add(respostas.get(contagem.get(index)));
            contagem.remove(index);
        }
        return newRespostas;
    }

    public void waitQuiz() throws InterruptedException {
        semaphore.acquire();
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public int getRespondidas() {
        return respondidas;
    }

    public void setRespondidas(int respondidas) {
        this.respondidas = respondidas;
    }

    public boolean getPerdeu() {
        return perdeu;
    }

    //configuração inicial de uma dificuldade
    public void initDificuldade(int dificuldade, int acertos, int respondidas, int codeSala) {
        setAcertos(acertos);
        setRespondidas(respondidas);
        addPerguntas(dificuldade, codeSala);
        initPergunta();
        int numPerguntas = perguntas.size();
        vidaJogador = (numPerguntas) / 2;
        vidaInimigo = (numPerguntas) / 2;
        initBarraVida(vidaInimigoBarra, vidaInimigo);
        initBarraVida(vidaJogadorBarra, vidaJogador);
    }

    //configura os valores dos items da tela de uma pergunta 
    public void initPergunta() {
        addRespostas((perguntas.get(indexPergunta)).getIdPergunta());
        resposta1.setText((respostas.get(0)).getResposta());
        resposta2.setText((respostas.get(1)).getResposta());
        resposta3.setText((respostas.get(2)).getResposta());
        resposta4.setText((respostas.get(3)).getResposta());
        resp1Certa = (respostas.get(0)).getCerta();
        resp2Certa = (respostas.get(1)).getCerta();
        resp3Certa = (respostas.get(2)).getCerta();
        resp4Certa = (respostas.get(3)).getCerta();
        areaPerg.setText((perguntas.get(indexPergunta)).getPergunta());
    }

    //configura uma barra de vida
    public void initBarraVida(javax.swing.JProgressBar barraVida, int vida) {
        barraVida.setMaximum(vida);
        barraVida.setMinimum(0);
        barraVida.setValue(vida);
    }

    //comportamento de uma resposta
    public void respondido(int respNum) {
        if (!perguntaRespondida) {
            boolean certa = (respostas.get(respNum)).getCerta();
            if (certa == true) {
                vidaInimigo--;
                acertos++;
                vidaInimigoBarra.setValue(vidaInimigo);
            } else {
                vidaJogador--;
                vidaJogadorBarra.setValue(vidaJogador);
            }
            perguntaRespondidaFunc(certa, respNum);
        } else if (vidaInimigo <= 0) {
            if (listener != null) {
                listener.onQuiz();
            }
            semaphore.release();
        } else if (vidaJogador <= 0) {
            perdeu = true;
            if (listener != null) {
                listener.onQuiz();
            }
            semaphore.release();
        } else {
            indexPergunta++;
            perguntaRespondida = false;
            initPergunta();
        }
    }

    public void perguntaRespondidaFunc(boolean certa, int respNum) {
        resposta1.setText("Continuar");
        resposta2.setText("Continuar");
        resposta3.setText("Continuar");
        resposta4.setText("Continuar");
        if (certa) {
            areaPerg.setText("Você acertou!!!");
        } else {
            Resposta respErrada = (Resposta) respostas.get(respNum);
            areaPerg.setText("Correção: " + (respErrada).getCorrect());
        }
        perguntaRespondida = true;
        respondidas++;
    }

    /**
     * Creates new form panelTest
     */
    public TelaQuiz(int dificuldade, int acertos, int respondidas, int codeSala) {
        this.dificuldade = dificuldade;
        initComponents();
        initDificuldade(dificuldade, acertos, respondidas, codeSala);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(128, 72));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(1280, 720));

        resposta1.setText("jButton1");
        resposta1.setPreferredSize(new java.awt.Dimension(400, 80));
        resposta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resposta1ActionPerformed(evt);
            }
        });

        vidaJogadorBarra.setForeground(new java.awt.Color(0, 255, 0));
        vidaJogadorBarra.setPreferredSize(new java.awt.Dimension(400, 20));

        vidaInimigoBarra.setForeground(new java.awt.Color(0, 255, 0));
        vidaInimigoBarra.setPreferredSize(new java.awt.Dimension(400, 20));

        areaPerg.setEditable(false);
        areaPerg.setPreferredSize(new java.awt.Dimension(400, 200));
        jScrollPane1.setViewportView(areaPerg);

        resposta2.setText("jButton2");
        resposta2.setPreferredSize(new java.awt.Dimension(400, 80));
        resposta2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resposta2ActionPerformed(evt);
            }
        });

        resposta3.setText("jButton3");
        resposta3.setPreferredSize(new java.awt.Dimension(400, 80));
        resposta3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resposta3ActionPerformed(evt);
            }
        });

        resposta4.setText("jButton4");
        resposta4.setPreferredSize(new java.awt.Dimension(400, 80));
        resposta4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resposta4ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") +"\\src\\main\\java\\TeladeLogin\\onça.png"));

        jLabel3.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") +"\\src\\main\\java\\TeladeLogin\\" + imagemInimigo()));

            jLabel1.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") +"\\src\\main\\java\\TeladeLogin\\BATALHA_CENA_1280X720.png"));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(420, 420, 420)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(resposta2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(resposta4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(10, 10, 10)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(resposta3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(750, 750, 750)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(vidaJogadorBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(870, 870, 870)
                            .addComponent(vidaInimigoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(resposta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(54, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(490, 490, 490)
                            .addComponent(resposta2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(60, 60, 60)
                            .addComponent(resposta4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(490, 490, 490)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(630, 630, 630)
                            .addComponent(resposta3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(150, 150, 150)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(120, 120, 120)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(vidaJogadorBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(vidaInimigoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(490, 490, 490)
                            .addComponent(resposta1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(31, Short.MAX_VALUE))
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton resposta1;
    private javax.swing.JButton resposta2;
    private javax.swing.JButton resposta3;
    private javax.swing.JButton resposta4;
    private javax.swing.JProgressBar vidaInimigoBarra;
    private javax.swing.JProgressBar vidaJogadorBarra;
    // End of variables declaration//GEN-END:variables
}
