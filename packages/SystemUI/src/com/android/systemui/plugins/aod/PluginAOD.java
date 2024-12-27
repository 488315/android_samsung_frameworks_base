package com.android.systemui.plugins.aod;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@ProvidesInterface(action = PluginAOD.ACTION, version = 1)
public interface PluginAOD extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_AOD";
    public static final int MACHINE_MOD_REASON = 1;
    public static final int REASON_MOD_CHARGING = 32;
    public static final int REASON_MOD_EDGE_LIGHTING = 2;
    public static final int REASON_MOD_HUN = 64;
    public static final int REASON_MOD_IN_DISPLAY = 8;
    public static final int REASON_MOD_NONE = 1;
    public static final int REASON_MOD_POWER_UI = 16;
    public static final int REASON_MOD_TOAST = 4;
    public static final int REASON_MOD_VOLUME = 128;
    public static final int STATE_DISPLAY_ON = 8;
    public static final int STATE_DOZE = 2;
    public static final int STATE_DOZE_AOD = 4;
    public static final int STATE_DOZE_AOD_PAUSED = 5;
    public static final int STATE_DOZE_MOD = 6;
    public static final int STATE_DOZE_REQUEST_AOD = 3;
    public static final int STATE_FINISH = 7;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_UNINITIALIZED = 0;
    public static final int VERSION = 1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
        void onRequestState(int i);

        default Bundle onSendExtraData(Bundle bundle) {
            return null;
        }

        default void onUpdateDozeBrightness(int i, int i2) {
        }

        default void onUpdateDozeBrightness(int i, int i2, int i3) {
        }

        default void dozeTimeTick() {
        }

        default void onUpdateWindowLayoutParams() {
        }

        default void onFinishMOD(int i) {
        }

        default void onRequestMOD(int i) {
        }
    }

    void applyAODFlags(WindowManager.LayoutParams layoutParams, boolean z, boolean z2);

    void dump(PrintWriter printWriter);

    View getAODClockContainer(boolean z);

    int getAODClockType();

    PluginAODParameter getAODParameter();

    View getBottomArea();

    PluginAODFaceWidgetManager getFaceWidgetManager();

    PluginAODNotificationManager getNotificationManager();

    Point getZigzagPosition();

    void hideChargingInfoByFinger(long j);

    boolean needDozeAlwaysOn();

    void onChargingAnimStarted(boolean z);

    void onConfigurationChanged(Configuration configuration);

    void onDozeAmountChanged(float f, float f2);

    boolean onDreamingStarted(ViewGroup viewGroup, PluginAODSystemUIConfiguration pluginAODSystemUIConfiguration);

    void onDreamingStopped();

    void onFaceWidgetPositionChanged();

    void onFinishedGoingToSleep();

    void onFolderStateChanged(boolean z);

    void onSystemUIConfigurationChanged(PluginAODSystemUIConfiguration pluginAODSystemUIConfiguration);

    void onUnlockedScreenOffAmountChanged(float f);

    void onUnlockedScreenOffAnimationEnd();

    void requestMODState(int i, boolean z);

    Bundle sendExtraData(Bundle bundle);

    void sendIntent(Intent intent);

    void setAODPluginCallback(Callback callback);

    void setAODUICallback(UICallback uICallback);

    void setDozeScreenState(int i, int i2);

    void setIsDozing(boolean z, boolean z2);

    void setTouchMode(boolean z);

    void setTouchModeWhileClockTransition(boolean z);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface UICallback {
        int getKeyguardOrientation();

        default Bundle getLockStarData(boolean z) {
            return new Bundle();
        }

        boolean isCaptureEnabled();

        boolean isWonderLandAmbientWallpaperEnabled();

        void registerAODDoubleTouchListener(View.OnTouchListener onTouchListener);

        void unregisterAODDoubleTouchListener();

        default void setBottomArea(View view) {
        }
    }
}
