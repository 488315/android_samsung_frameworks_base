package com.android.systemui.statusbar.commandline;

import java.util.ListIterator;

/* loaded from: classes3.dex */
public interface SubCommand extends Describable {
    ParseableCommand getCmd();

    boolean getValidationStatus();

    void parseSubCommandArgs(ListIterator listIterator);
}
