package com.android.systemui.qs.external;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.service.quicksettings.Tile;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    default void lazyInitialize() {
    }

    default void refreshDetailInfo() {
    }

    default void refreshMetaInfo() {
    }

    default void fireToggleStateChanged(boolean z) {
    }

    default void setToggleEnabledState(boolean z) {
    }
}
