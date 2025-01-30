package com.android.systemui.qs.pipeline.data.repository;

import android.content.ComponentName;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface CustomTileAddedRepository {
    boolean isTileAdded(int i, ComponentName componentName);

    void setTileAdded(ComponentName componentName, boolean z, int i);
}
