package com.android.systemui.statusbar.commandline;

import java.util.ListIterator;

public interface SubCommand extends Describable {
    ParseableCommand getCmd();

    boolean getValidationStatus();

    void parseSubCommandArgs(ListIterator listIterator);
}
