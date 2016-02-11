/*
 * ParaSeqGuiApp.java
 */

package paradrums.gui;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Transmitter;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import paradrums.MidiReceiver;
import paradrums.ParaSeq;
import paradrums.SequenceEngine;
import paradrums.SynthEngine;

/**
 * The main class of the application.
 */
public class ParaSeqGuiApp extends SingleFrameApplication {
    
    private static MidiDevice maschine;

    private static Transmitter midiIn;

    private static SynthEngine synth;

    private static SequenceEngine sequ;

    private static List<ParaSeqGuiView> listeners;

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new ParaSeqGuiView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    @Override protected void shutdown() {
        super.shutdown();
        sequ.stop();
        midiIn.close();
        maschine.close();
        synth.close();
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of ParaSeqGuiApp
     */
    public static ParaSeqGuiApp getApplication() {
        return Application.getInstance(ParaSeqGuiApp.class);
    }

    public void addListener(ParaSeqGuiView view) {
        listeners.add(view);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
         try {
            maschine = MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[1]);

            maschine.open();

            midiIn = maschine.getTransmitter();

            synth = new SynthEngine();

            sequ = new SequenceEngine();

            midiIn.setReceiver(new MidiReceiver(sequ));

            sequ.setReceiver(synth.getReceiver());

            sequ.start();

        } catch (Exception ex) {
            Logger.getLogger(ParaSeq.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        launch(ParaSeqGuiApp.class, args);
    }
}
