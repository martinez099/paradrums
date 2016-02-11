/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paradrums;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

/**
 *
 * @author martinez
 */
public class MidiReceiver implements Receiver {

    private ParameterEngine para;

    public MidiReceiver(SequenceEngine sequ) throws InvalidMidiDataException, MidiUnavailableException {
        this.para = new ParameterEngine(sequ);
    }

    public void send(MidiMessage message, long timeStamp) {

        int len = message.getLength();
        int stat = message.getStatus();
        byte[] msg = message.getMessage();
        //System.out.println(stat);
        int num = (int) msg[1] & 0xFF;
        int val = (int) msg[2] & 0xFF;

         try {
            if (num >= 14 && num <= 21) {
                para.setParameter(0, num - 14, val);
            } else if (num >= 22 && num <= 29) {
                para.setParameter(1, num - 22, val);
            } else if (num >= 30 && num <= 37) {
                para.setParameter(2, num - 30, val);
            }
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(MidiReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
