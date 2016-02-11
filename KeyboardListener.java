/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paradrums;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author martinez
 */
public class KeyboardListener {

    private InputStreamReader reader;

    public KeyboardListener() {
        reader = new InputStreamReader(System.in);
    }

    public void listen() throws IOException {
        reader.read();
    }

}
