/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paradrums;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Synthesizer;

/**
 *
 * @author martinez
 */
public class SynthEngine {

    private Synthesizer synthesizer;

    public SynthEngine() throws MidiUnavailableException {
        synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
    }

    public Receiver getReceiver() throws MidiUnavailableException {
        return synthesizer.getReceiver();

    }

    public void close() {
        synthesizer.close();
    }

}
