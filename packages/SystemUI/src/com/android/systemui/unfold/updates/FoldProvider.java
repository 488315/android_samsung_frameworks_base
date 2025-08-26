package com.android.systemui.unfold.updates;

import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public interface FoldProvider {

    public interface FoldCallback {
        void onFoldUpdated(boolean z);
    }

    void registerCallback(FoldCallback foldCallback, Executor executor);

    void unregisterCallback(FoldCallback foldCallback);
}
