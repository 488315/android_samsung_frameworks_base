package com.android.systemui.plugins.subscreen;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.aod.PluginAODNotificationManager;
import com.android.systemui.plugins.keyguardstatusview.PluginSystemUIWallpaperUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@ProvidesInterface(action = PluginSubScreen.ACTION, version = 1)
/* loaded from: classes2.dex */
public interface PluginSubScreen extends Plugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_SUB_SCREEN";
    public static final int BIOMETRIC_TYPE_FACE = 1001;
    public static final int BIOMETRIC_TYPE_FINGERPRINT = 1002;
    public static final String KEY_AUTO_WIPE_WARNING_MESSAGE = "auto_wipe_warning_message";
    public static final String KEY_BOUNCER_MESSAGE = "bouncer_message";
    public static final String KEY_INCORRECT_BOUNCER_MESSAGE = "bouncer_incorrect_message";
    public static final String KEY_STRONG_AUTH_MESSAGE = "strong_auth_message";
    public static final String KEY_STRONG_AUTH_POPUP_MESSAGE = "strong_auth_popup_message";
    public static final int SUB_ROOM_MUSIC_WIDGET = 304;
    public static final int SUB_ROOM_NETWORK = 303;
    public static final int SUB_ROOM_NOTIFICATION = 301;
    public static final int SUB_ROOM_QUICK_PANEL = 300;
    public static final int SUB_ROOM_WIDGET = 302;
    public static final int VERSION = 1;

    default boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    default PluginAODNotificationManager getNotificationManager() {
        return null;
    }

    default Rect getNowTouchableRect() {
        return null;
    }

    default View getSubScreenPreview() {
        return null;
    }

    default boolean isTurnOnSmartCase() {
        return false;
    }

    void onConfigurationChanged(Configuration configuration);

    void onScreenTurnedOff();

    void onScreenTurnedOn();

    @Deprecated
    void onSubUIStarted(ViewGroup viewGroup, Bundle bundle);

    void onSubUIStarted(Window window, Bundle bundle);

    void onSubUIStopped();

    default void requestOpenAppPopup(PendingIntent pendingIntent, Intent intent) {
    }

    void setPluginCallback(Callback callback);

    default View getSubScreenPreview(int i) {
        return null;
    }

    default void requestOpenAppPopup(PendingIntent pendingIntent, boolean z) {
    }

    default View getSubScreenPreview(int i, int i2, int i3) {
        return null;
    }

    default void requestOpenAppPopup(PendingIntent pendingIntent, boolean z, String str) {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
        Bundle getBouncerMessage();

        Bundle getIncorrectBouncerMessage();

        SubRoom getSubRoom(int i);

        default List<StatusBarNotification> getVisibleNotificationList() {
            return null;
        }

        PluginSystemUIWallpaperUtils getWallpaperUtils();

        boolean isCaptureEnabled();

        boolean isDualDarInnerAuthRequired();

        boolean isFullscreenBouncer();

        boolean isKeyguardShowing();

        boolean isSecure();

        boolean isSimPinSecure();

        boolean isUserUnlocked();

        void onClockPageClicked();

        void setEnableDLS(boolean z);

        boolean shouldControlScreenOff();

        void startBiometricState();

        void startFingerprintState();

        void stopBiometricState();

        void updateBiometricState();

        void verifyCredential(String str);

        default void startSubHomeActivity() {
        }

        default void startSubHomeActivityIfNeeded() {
        }

        default void launchApp(ComponentName componentName) {
        }

        default void onSubScreenBouncerStateChanged(boolean z) {
        }

        default void onSubScreenNavBarStateChanged(boolean z) {
        }

        default void requestDualState(boolean z) {
        }

        default void setAODVisibleState(int i) {
        }

        default void updateSubScreenFallback(boolean z) {
        }

        default void setDisplayStateOverrideWithDisplayId(int i, int i2) {
        }
    }

    default void dozeTimeTick() {
    }

    default void onCameraLaunchedDoubleTap() {
    }

    default void onFinishedGoingToSleep() {
    }

    default void onFinishedWakingUp() {
    }

    @Deprecated
    default void onLockModeChanged() {
    }

    @Deprecated
    default void onResumeSubHomeActivity() {
    }

    default void onStartedGoingToSleep() {
    }

    default void onStartedWakingUp() {
    }

    default void onUserUnlocked() {
    }

    default void disableTask(Bundle bundle) {
    }

    default void enableTask(Bundle bundle) {
    }

    default void onBiometricAuthFailed(int i) {
    }

    default void onCoverOccludedStateChanged(boolean z) {
    }

    default void onDeviceStateChanged(int i) {
    }

    default void onDozeAmountChanged(float f) {
    }

    default void onDozingChanged(boolean z) {
    }

    default void onDreamingInAwakeChanged(boolean z) {
    }

    default void onDualDARInnerLockscreenRequirementChanged(boolean z) {
    }

    default void onEntryUpdated(StatusBarNotification statusBarNotification) {
    }

    default void onFolderStateChanged(boolean z) {
    }

    default void onKeyguardShowingChanged(boolean z) {
    }

    default void onLockDisabledChanged(boolean z) {
    }

    default void onPackageAdded(String str) {
    }

    default void onPackageChanged(String str) {
    }

    default void onPackageDataCleared(String str) {
    }

    default void onReceivedIntent(Intent intent) {
    }

    default void onSemWallpaperChanged(Bundle bundle) {
    }

    default void onUnlockedChanged(boolean z) {
    }

    default void onVisibilityChanged(boolean z) {
    }

    default void setSubHomeActivityResumed(boolean z) {
    }

    default void showWipeWarningDialog(String str) {
    }

    default void updateCapsule(Bundle bundle) {
    }

    default void onBiometricRunningStateChanged(boolean z, int i) {
    }

    default void onEntryAdded(String str, StatusBarNotification statusBarNotification) {
    }

    default void onEntryRemoved(StatusBarNotification statusBarNotification, int i) {
    }

    default void onFullscreenBouncerChanged(boolean z, boolean z2) {
    }

    default void onPackageRemoved(String str, boolean z) {
    }

    default void onPasswordChecked(boolean z, int i) {
    }

    default void onBiometricAuthenticated(int i, int i2, boolean z) {
    }

    default void onBiometricError(int i, String str, int i2) {
    }

    default void onBiometricHelp(int i, String str, int i2) {
    }

    default void setClockColor(View view, int i, int i2, int i3) {
    }
}
