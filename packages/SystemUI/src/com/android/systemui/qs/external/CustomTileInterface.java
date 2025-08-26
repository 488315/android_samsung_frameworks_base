package com.android.systemui.qs.external;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.service.quicksettings.Tile;

/* loaded from: classes2.dex */
public interface CustomTileInterface {
    ComponentName getComponent();

    Tile getQsTile();

    String getTileSpec();

    int getUser();

    default boolean isInitialized() {
        return false;
    }

    default boolean isSecActiveTile() {
        return false;
    }

    void onDialogHidden();

    void onDialogShown();

    void refreshState();

    void startActivityAndCollapse(PendingIntent pendingIntent);

    void startUnlockAndRun();

    void updateTileState(Tile tile, int i);

    default void fireToggleStateChanged(boolean z) {
    }

    default void setToggleEnabledState(boolean z) {
    }

    default void lazyInitialize() {
    }

    default void refreshDetailInfo() {
    }

    default void refreshMetaInfo() {
    }
}
