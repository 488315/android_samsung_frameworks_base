package com.android.wm.shell.compatui.impl;

import android.content.Context;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.SyncTransactionQueue;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DefaultCompatUIComponentFactory {
    public final Context context;
    public final DisplayController displayController;
    public final SyncTransactionQueue syncQueue;

    public DefaultCompatUIComponentFactory(Context context, SyncTransactionQueue syncTransactionQueue, DisplayController displayController) {
        this.context = context;
        this.syncQueue = syncTransactionQueue;
        this.displayController = displayController;
    }
}
