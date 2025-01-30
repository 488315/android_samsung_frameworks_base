package com.android.systemui.unfold.updates;

import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface FoldProvider {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface FoldCallback {
        void onFoldUpdated(boolean z);
    }

    void registerCallback(FoldCallback foldCallback, Executor executor);
}
