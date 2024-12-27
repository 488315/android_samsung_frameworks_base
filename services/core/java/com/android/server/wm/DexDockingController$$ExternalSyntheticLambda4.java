package com.android.server.wm;

import android.util.Slog;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
