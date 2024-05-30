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
        //chama tela inicial
        
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
            telaQuiz.setVisible(true);
            while(!telaQuiz.getAcabou()){}
            if (!telaQuiz.getPerdeu()){
                acertos = telaQuiz.getAcertos();
                respondidas = telaQuiz.getRespondidas();
                dificuldade++;
            }
            telaQuiz.dispose();
        }
        
        // tela de resultados
        
    }
}
