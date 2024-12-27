package com.android.server.wm;

import android.util.Slog;

import java.util.function.Consumer;

public final /* synthetic */ class DexDockingController$$ExternalSyntheticLambda4
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Task task = (Task) obj;
        if (task.isDexTaskDocked()) {
            Slog.d("DexDockingController", "clear task=" + task);
            task.setDexTaskDocking(0);
        }
    }
}
