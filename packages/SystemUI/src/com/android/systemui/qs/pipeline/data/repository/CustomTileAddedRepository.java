package com.android.systemui.qs.pipeline.data.repository;

import android.content.ComponentName;

public interface CustomTileAddedRepository {
    boolean isTileAdded(int i, ComponentName componentName);

    void setTileAdded(ComponentName componentName, boolean z, int i);
}
