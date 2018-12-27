package com.Command;

import com.View;

public interface Command {
    public void execute();
    View view = View.getView();

}
