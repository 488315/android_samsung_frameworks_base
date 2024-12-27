package com.android.systemui.statusbar.commandline;

import java.util.ListIterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface SubCommand extends Describable {
    ParseableCommand getCmd();

    boolean getValidationStatus();

    void parseSubCommandArgs(ListIterator listIterator);
}
