package com.anis.interfaceadapters;

import java.awt.event.KeyEvent;

public interface KeyboardInput {

    // TODO: Don't know if these need to be in here (i.e., if they need to be public)
    boolean[] pressed = new boolean[256];
    boolean[] prev = new boolean[256];

    void update();
    void keyPressed(KeyEvent e);
    void keyReleased(KeyEvent e);
    boolean typed(int keyEvent);

}
