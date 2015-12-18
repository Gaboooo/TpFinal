/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sonidos;

import java.io.File;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Martin
 */
public class alertaSuave implements Runnable {
    
    @Override
    public void run(){
        
        InputStream path = this.getClass().getResourceAsStream("alertaSuave.wav");
        
        try {
            
            // Se obtiene un Clip de sonido
            Clip sonido = AudioSystem.getClip();
            
            // Se carga con un fichero wav
            sonido.open(AudioSystem.getAudioInputStream(path));
            
            // Comienza la reproducción
            sonido.start();
            
            // Se cierra el clip.
            //sonido.close();
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }
}
