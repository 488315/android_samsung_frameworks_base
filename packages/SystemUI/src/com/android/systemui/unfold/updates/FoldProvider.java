package com.android.systemui.unfold.updates;

import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface FoldProvider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface FoldCallback {
        void onFoldUpdated(boolean z);
    }

    void registerCallback(FoldCallback foldCallback, Executor executor);

    void unregisterCallback(FoldCallback foldCallback);
}
