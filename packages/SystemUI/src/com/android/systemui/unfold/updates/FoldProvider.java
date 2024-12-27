package com.android.systemui.unfold.updates;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface FoldProvider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface FoldCallback {
        void onFoldUpdated(boolean z);
    }

    void registerCallback(FoldCallback foldCallback, Executor executor);

    void unregisterCallback(FoldCallback foldCallback);
}
