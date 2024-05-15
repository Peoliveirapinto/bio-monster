/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biomons.bio.monsters;

/**
 *
 * @author pedro
 */
public class Dificuldade {
    private int nivel;
    
    public Dificuldade(int nivel){
        setNivel(nivel);
    }
    public int getNivel(){
        return nivel;
    }
    public void setNivel(int nivel){
        this.nivel = nivel;
    }
}
