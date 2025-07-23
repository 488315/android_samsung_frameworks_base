package com.android.systemui.statusbar.commandline;

import java.util.ListIterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SubCommand extends Describable {
    ParseableCommand getCmd();

    boolean getValidationStatus();

    void parseSubCommandArgs(ListIterator listIterator);
}
