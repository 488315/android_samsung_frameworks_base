package com.android.systemui.unfold.updates;

import java.util.concurrent.Executor;

public interface FoldProvider {

    public interface FoldCallback {
        void onFoldUpdated(boolean z);
    }

    void registerCallback(FoldCallback foldCallback, Executor executor);

    void unregisterCallback(FoldCallback foldCallback);
}
