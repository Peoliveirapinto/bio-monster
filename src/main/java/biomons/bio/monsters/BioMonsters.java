/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package biomons.bio.monsters;

import TeladeLogin.TelaPrincipal;

/**
 *
 * @author pedro
 */
public class BioMonsters {

    
    public static void main(String[] args) {
        while(true){
        TelaPrincipal telaPrincipal = new TelaPrincipal();
        telaPrincipal.setVisible(true);
        try {
            telaPrincipal.waitIniciar();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        GameFrame frame = new GameFrame();
        frame.setSize(720,480);

        TelaInserirSala telaSala = new TelaInserirSala();
        frame.getContentPane().add(telaSala);
        telaSala.setVisible(true);
        frame.setVisible(true);
        try {
            telaSala.waitSala();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int codeSala = telaSala.getCodeSala();
        //se o code sala não estiver na tabela codeSala = 1;
        
        int acertos=0;
        int respondidas=0;
        int dificuldade = 1;
        
        while (dificuldade <= 4){
            frame.getContentPane().removeAll();
            TelaQuiz telaQuiz = new TelaQuiz(dificuldade,acertos,respondidas,codeSala);
            TelaInterDificuldade telaInter = new TelaInterDificuldade(codeSala,dificuldade);
            TelaPerdeu telaPerdeu = new TelaPerdeu();
            frame.getContentPane().add(telaQuiz);
            telaQuiz.setVisible(true);
            frame.setVisible(true);
            while(!telaQuiz.getAcabou()){}
            boolean perdeu = telaQuiz.getPerdeu();
            if (!perdeu){
                acertos = telaQuiz.getAcertos();
                respondidas = telaQuiz.getRespondidas();
                frame.getContentPane().removeAll();
                frame.getContentPane().add(telaInter);
                telaInter.setVisible(true);
                frame.setVisible(true);
                try {
                    telaInter.waitInterContinuar();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                dificuldade++;
            }else{
                frame.getContentPane().removeAll();
                frame.getContentPane().add(telaPerdeu);
                telaPerdeu.setVisible(true);
                frame.setVisible(true);
                try {
                    telaPerdeu.waitTryAgain();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        frame.getContentPane().removeAll();
        TelaResultados telaResult = new TelaResultados(acertos,respondidas);
        frame.getContentPane().add(telaResult);
        telaResult.setVisible(true);
        frame.setVisible(true);
        try {
            telaResult.waitResultados();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.dispose();
        }
    }
}
