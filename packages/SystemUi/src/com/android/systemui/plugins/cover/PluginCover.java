package com.android.systemui.plugins.cover;

import android.app.PendingIntent;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.aod.PluginAODFaceWidgetManager;
import com.android.systemui.plugins.aod.PluginAODNotificationManager;
import com.android.systemui.plugins.subscreen.PluginSubScreen;
import com.samsung.android.cover.CoverState;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PluginCover extends Plugin {
    void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    default View getCoverScreenPreview() {
        return null;
    }

    PluginAODFaceWidgetManager getFaceWidgetManager();

    PluginAODNotificationManager getNotificationManager();

    void onConfigurationChanged(Configuration configuration);

    void onCoverAppCovered(boolean z);

    void onCoverAttached(ViewGroup viewGroup, CoverState coverState);

    void onCoverAttached(Window window, CoverState coverState);

    void onCoverDetached();

    void onCoverStateUpdated(CoverState coverState);

    void onScreenInternalTurningOff();

    void onScreenInternalTurningOn();

    void onScreenTurnedOff();

    void onScreenTurningOn();

    void onStartedWakingUp();

    void showCoverToast(PendingIntent pendingIntent, boolean z);

    default View getCoverScreenPreview(int i) {
        return null;
    }

    default View getCoverScreenPreview(int i, int i2, int i3) {
        return null;
    }

    default void onDozeAmountChanged(float f) {
    }

    default void onDozingChanged(boolean z) {
    }

    default void setPluginCallback(PluginSubScreen.Callback callback) {
    }

    default void dozeTimeTick() {
    }

    default void setClockColor(View view, int i, int i2, int i3) {
    }
}
