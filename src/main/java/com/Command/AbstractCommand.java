package com.Command;

import com.SQLConnect;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class AbstractCommand implements Command{
    ObjectOutputStream coos;
    ObjectInputStream cois;
    SQLConnect sqlConnect;
}
