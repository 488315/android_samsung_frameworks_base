package com.android.systemui.qs.pipeline.data.repository;

import android.content.ComponentName;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface CustomTileAddedRepository {
    boolean isTileAdded(int i, ComponentName componentName);

    void setTileAdded(ComponentName componentName, boolean z, int i);
}
