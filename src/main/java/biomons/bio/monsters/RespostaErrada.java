/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biomons.bio.monsters;

/**
 *
 * @author pedro
 */
public class RespostaErrada extends Resposta {
    private String correct;
    
    public RespostaErrada(String resposta,int idPergunta, String correct){
        super(false, resposta,idPergunta);
        setCorrect(correct);
    }
    
    public String getCorrect(){
        return correct;
    }
    public void setCorrect(String correct){
        this.correct = correct;
    }
}
