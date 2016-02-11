/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paradrums;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Transmitter;

/**
 *
 * @author martinez
 */
public class ParaSeq {

    public static void main(String[] args) {
        try {
            MidiDevice maschine = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[1]);

            maschine.open();

            Transmitter midiIn = maschine.getTransmitter();

            SynthEngine synth = new SynthEngine();
            
            SequenceEngine sequ = new SequenceEngine();

            midiIn.setReceiver(new MidiReceiver(sequ));

            sequ.setReceiver(synth.getReceiver());

            sequ.start();

            System.out.println(sequ.getLenght(0));

            KeyboardListener listener = new KeyboardListener();

            listener.listen();

            sequ.stop();

            midiIn.close();

            maschine.close();

            synth.close();
      

        } catch (Exception ex) {
            Logger.getLogger(ParaSeq.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
