/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 * teste
 */
package biomons.bio.monsters;

/**
 *
 * @author pedro
 */
public class Inimigo extends Dificuldade {
    private String nomeInimigo;
    private String infoInimigo;
    
    public Inimigo(int nivel, String nomeInimigo, String infoInimigo){
        super(nivel);
        setNomeInimigo(nomeInimigo);
        setInfoInimigo(infoInimigo);
    }
    
    public String getNomeInimigo(){
        return nomeInimigo;
    }
    public String getInfoInimigo(){
        return infoInimigo;
    }
    public void setNomeInimigo(String nomeInimigo){
        this.nomeInimigo = nomeInimigo;
    }
    public void setInfoInimigo(String infoInimigo){
        this.infoInimigo = infoInimigo;
    }
}
