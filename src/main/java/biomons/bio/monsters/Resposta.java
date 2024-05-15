/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biomons.bio.monsters;

/**
 *
 * @author pedro
 */
public class Resposta {
    private boolean certa;
    private String resposta;
    
    public Resposta(boolean certa, String resposta){
        this.certa = certa;
        this.resposta = resposta;        
    }
    
    public boolean getCerta(){
        return certa;
    }
    public void setCerta(boolean certa){
        this.certa = certa;
    }
    public String getResposta(){
        return resposta;
    }
    public void setResposta(String resposta){
        this.resposta = resposta;
    }
    
}
