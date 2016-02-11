/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paradrums;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 *
 * @author martinez
 */
public class SequenceEngine {

    private final static int TEMPO = 120;
    private final static int STEPS = 16;
    private final static int RESOLUTION = 4;
    private final static int CHANNEL = 9;
    private Sequencer sequencer;
    private Sequence sequence;
    private Track[] tracks = new Track[16];

    public SequenceEngine() throws InvalidMidiDataException, MidiUnavailableException {
        sequencer = MidiSystem.getSequencer();
        sequence = new Sequence(Sequence.PPQ, RESOLUTION);
        for (int i = 0; i < tracks.length; i++) {
            tracks[i] = sequence.createTrack();
        }
        sequencer.open();
        sequencer.setSequence(sequence);
        sequencer.setTempoInBPM(TEMPO);
        init();
    }

    private void init() throws InvalidMidiDataException, MidiUnavailableException {
        for (int t = 0; t < tracks.length; t++) {
            ShortMessage initMessage = new ShortMessage();
            initMessage.setMessage(ShortMessage.NOTE_OFF, CHANNEL, 0, 0);
            for (int s = 1; s <= STEPS; s++) {
                MidiEvent event = new MidiEvent(initMessage, s);
                tracks[t].add(event);
            }
        }
    }

    public void start() {
        sequencer.setLoopStartPoint(0);
        sequencer.setLoopEndPoint(-1);
        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        sequencer.start();
    }

    public void stop() {
        sequencer.stop();
        sequencer.close();
    }

    public void addEvent(int track, int key, int velocity, int amount, int offset, int burst) throws InvalidMidiDataException {
        ShortMessage onMessage = new ShortMessage();
        ShortMessage offMessage = new ShortMessage();
        onMessage.setMessage(ShortMessage.NOTE_ON, CHANNEL, key, velocity);
        offMessage.setMessage(ShortMessage.NOTE_OFF, CHANNEL, key, 0);
        int t = (int) Math.round(STEPS / (double) amount);
        double b = ((double) burst) / amount;
        System.out.println(b);
        int burstCount = 1;
        for (int i = 0; i < amount; i++) {
            double aBurst = (i + 1) * b;
            int realBurst = 0;
            if (aBurst >= burstCount) {
                realBurst = (int) Math.floor(aBurst) - (burstCount - 1);
                System.err.println(realBurst);
                burstCount += realBurst;
            } else {
                realBurst = 0;
            }
            int tick = ((((i * t - realBurst)) + offset) % STEPS) + 1;
            MidiEvent event = new MidiEvent(onMessage, tick);
            tracks[track].add(event);
            event = new MidiEvent(offMessage, ++tick > STEPS ? 1 : tick);
            tracks[track].add(event);
        }
    }

    public MidiEvent[] getEvents(int track) {
        MidiEvent[] events = new MidiEvent[tracks[track].size()];
        for (int i = 0; i < tracks[track].size(); i++) {
            events[i] = tracks[track].get(i);
        }
        return events;
    }

    public void clearEvents(int track) {
        while (tracks[track].size() > 0) {
            tracks[track].remove(tracks[track].get(tracks[track].size() - 1));
        }
    }

    public void setReceiver(Receiver receiver) throws MidiUnavailableException {
        sequencer.getTransmitter().setReceiver(receiver);
    }

    public void addMetaEventListener(MetaEventListener listener) {
        sequencer.addMetaEventListener(listener);
    }

    public long getLenght(int track) {
        return tracks[track].ticks();
    }
}
