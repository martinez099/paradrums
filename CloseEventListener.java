/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paradrums;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

/**
 *
 * @author martinez
 */
public class CloseEventListener implements MetaEventListener {

    private SequenceEngine sequencer;
    private SynthEngine synthesizer;

    public CloseEventListener(SequenceEngine sequencer, SynthEngine synthesizer) {
        this.sequencer = sequencer;
        this.synthesizer = synthesizer;
    }

    public void meta(MetaMessage meta) {
        if (meta.getType() == 47) {
            sequencer.stop();
            if (synthesizer != null) {
                synthesizer.close();
            }
        }
    }
}
