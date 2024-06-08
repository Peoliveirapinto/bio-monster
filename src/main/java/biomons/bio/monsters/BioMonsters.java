/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package biomons.bio.monsters;


/**
 *
 * @author pedro
 */
public class BioMonsters {

    
    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
        //chama tela inicial
        
        frame.setSize(720,480);
        
        //chama tela de inserir codigo da sala
        //int codeSala = retorno do valor da sala
        
        int acertos=0;
        int respondidas=0;
        int dificuldade = 1;
        
        while (dificuldade <= 4){
            TelaQuiz telaQuiz = new TelaQuiz(dificuldade,acertos,respondidas);
            TelaInterDificuldade telaInter = new TelaInterDificuldade();
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
                while(!telaInter.getContinuar())
                dificuldade++;
            }else{
                frame.getContentPane().removeAll();
                frame.getContentPane().add(telaPerdeu);
                telaPerdeu.setVisible(true);
                frame.setVisible(true);
                while(!telaPerdeu.getTentarDeNovo()){}
            }
            frame.getContentPane().removeAll();
        }
        frame.getContentPane().removeAll();
        TelaResultados telaResult = new TelaResultados(acertos,respondidas);
        frame.getContentPane().add(telaResult);
        telaResult.setVisible(true);
        frame.setVisible(true);
        
    }
}
