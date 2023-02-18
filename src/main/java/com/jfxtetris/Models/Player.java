package com.jfxtetris.Models;

import java.io.Serializable;
import java.util.UUID;

public class Player implements Serializable {
    String name;
    UUID id = java.util.UUID.randomUUID();
    boolean isSinglePlayer = true;
    public int Level = 1;
    public int Score = 0;
}
