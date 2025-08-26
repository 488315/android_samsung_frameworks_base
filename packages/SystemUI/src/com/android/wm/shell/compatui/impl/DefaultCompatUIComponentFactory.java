package com.android.wm.shell.compatui.impl;

import android.content.Context;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.SyncTransactionQueue;

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
