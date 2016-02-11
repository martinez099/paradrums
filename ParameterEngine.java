/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paradrums;

import java.util.HashMap;
import java.util.Map;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author martinez
 */
public class ParameterEngine {

    private SequenceEngine sequ;

    private Map<Integer, Parameter[]> paras = new HashMap<Integer, Parameter[]>();

    public ParameterEngine(SequenceEngine sequ) throws InvalidMidiDataException, MidiUnavailableException {
        this.sequ = sequ;
        init();
    }

    private void init() throws InvalidMidiDataException {
        for (int i = 0; i < 16; i++) {
            Parameter[] p = new Parameter[5];
            p[0] = new Parameter("key");
            p[1] = new Parameter("velocity");
            p[2] = new Parameter("ammount");
            p[3] = new Parameter("offset");
            p[4] = new Parameter("variation");
            paras.put(new Integer(i), p);
        }


        // kick
        Parameter[] p = paras.get(0);
        p[0].value = 36;
        p[1].value = 111;
        p[2].value = 4;
        p[3].value = 0;
        p[4].value = 0;
        setParameters(0, p);

        // snare
        p = paras.get(1);
        p[0].value = 38;
        p[1].value = 88;
        p[2].value = 2;
        p[3].value = 4;
        p[4].value = 0;
        setParameters(1, p);

        // hihat
        p = paras.get(2);
        p[0].value = 42;
        p[1].value = 66;
        p[2].value = 8;
        p[3].value = 2;
        p[4].value = 0;
        setParameters(2, p);


/*
        setParameter(0, 0, 36); // kick key
        setParameter(0, 1, 111); // kick velocity
        setParameter(0, 2, 2); // kick ammount
        setParameter(0, 3, 0); // kick offset

        setParameter(1, 0, 40); // snare key
        setParameter(1, 1, 88); // snare velocity
        setParameter(1, 2, 2); // snare ammount
        setParameter(1, 3, 4); // snare offset

        setParameter(2, 0, 42); // hihat key
        setParameter(2, 1, 66); // hihat velocity
        setParameter(2, 2, 8); // hihat ammount
        setParameter(2, 3, 2); // hihat offset
 
 */

    }

    public void setParameter(int track, int parameter, int value) throws InvalidMidiDataException {
        Parameter[] p = paras.get(track);
        p[parameter].value = value;
        sequ.clearEvents(track);
        sequ.addEvent(track, p[0].getValue(), p[1].getValue(), p[2].getValue(), p[3].getValue(), p[4].getValue());
    }

    public void setParameters(int track, Parameter[] p) throws InvalidMidiDataException {
        paras.put(track, p);
        sequ.clearEvents(track);
        sequ.addEvent(track, p[0].getValue(), p[1].getValue(), p[2].getValue(), p[3].getValue(), p[4].getValue());
    }


    static class Parameter {

        private String name;

        private int value;

        public Parameter(String name) {
            this.name = name;
        }

        public Parameter(String name, int value) {
            this(name);
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return name + ": " + value;
        }
        
    }



}
