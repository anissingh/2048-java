package com.anis.drivers;

import com.anis.interfaceadapters.KeyboardInput;

import java.awt.event.KeyEvent;

/**
 * Class that gets the input from a user in the form of keys that are pressed
 */
public class Keyboard implements KeyboardInput {

    /**
     * Update our pressed and prev arrays depending on the keys that are pressed
     */
    public void update() {
        //Since we are only dealing with 4 keys, there is no point in looping through all 256 slots,
        //so we are only going to loop through 4 things instead.
        for(int i = 0; i < 4; i++) {
            //Set all of the previous keys (4 because we are only dealing with 4 keys) to the pressed keys.
            if(i == 0) {
                prev[KeyEvent.VK_LEFT] = pressed[KeyEvent.VK_LEFT];
            } else if(i == 1) {
                prev[KeyEvent.VK_RIGHT] = pressed[KeyEvent.VK_RIGHT];
            } else if(i == 2) {
                prev[KeyEvent.VK_UP] = pressed[KeyEvent.VK_UP];
            } else { // i = 3 in this case
                prev[KeyEvent.VK_DOWN] = pressed[KeyEvent.VK_DOWN];
            }
        }
    }

    /**
     * Type method for keys pressed
     * @param e KeyEvent corresponding to the key that is pressed
     */
    public void keyPressed(KeyEvent e) {
        pressed[e.getKeyCode()] = true;
    }

    /**
     * Type method for keys released
     * @param e KeyEvent corresponding to the key that is released
     */
    public void keyReleased(KeyEvent e) {
        pressed[e.getKeyCode()] = false;
    }

    /**
     * Capture key typed events
     * @param keyEvent An integer corresponding to the key code of the KeyEvent to be captured
     * @return Whether the key is not pressed and it was previously pressed
     */
    public boolean typed(int keyEvent) {
        return !pressed[keyEvent] && prev[keyEvent];
    }

}
