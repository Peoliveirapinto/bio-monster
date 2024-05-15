/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biomons.bio.monsters;

/**
 *
 * @author pedro
 */
public class Pergunta extends Dificuldade{
    private String pergunta;
    private int idPergunta;
    
    
    public Pergunta (int nivel, String pergunta, int idPergunta){
        super(nivel);
        setPergunta(pergunta);
        setIdPergunta(idPergunta);
    }
    
    public String getPergunta(){
        return pergunta;
    }
    public void setPergunta(String pergunta){
        this.pergunta = pergunta;
    }
    public int getIdPergunta(){
        return idPergunta;
    }
    public void setIdPergunta(int idPergunta){
        this.idPergunta = idPergunta;
    }
}
