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
        
        //int numDificuldades = count de dificuldades da sala;
        //teste
        int numDificuldade =4;
        
        while (dificuldade <= numDificuldade){
            TelaQuiz telaQuiz = new TelaQuiz(dificuldade,acertos,respondidas);
            frame.getContentPane().add(telaQuiz);
            telaQuiz.setVisible(true);
            frame.setVisible(true);
            while(!telaQuiz.getAcabou()){}
            if (!telaQuiz.getPerdeu()){
                acertos = telaQuiz.getAcertos();
                respondidas = telaQuiz.getRespondidas();
                telaQuiz.setVisible(false);
                TelaInterDificuldade telaInter = new TelaInterDificuldade();
                frame.getContentPane().add(telaInter);
                telaInter.setVisible(true);
                frame.setVisible(true);
                while(!telaInter.getContinuar()){}
                frame.getContentPane().remove(telaInter);
                dificuldade++;
            }else{
                TelaPerdeu telaPerdeu = new TelaPerdeu();
                frame.getContentPane().add(telaPerdeu);
                telaPerdeu.setVisible(true);
                frame.setVisible(true);
                while(!telaPerdeu.getTentarDeNovo()){}
                frame.getContentPane().remove(telaPerdeu);
            }
            frame.getContentPane().remove(telaQuiz);
        }
        
        TelaResultados telaResult = new TelaResultados(acertos,respondidas);
        frame.getContentPane().add(telaResult);
        telaResult.setVisible(true);
        frame.setVisible(true);
        
    }
}
