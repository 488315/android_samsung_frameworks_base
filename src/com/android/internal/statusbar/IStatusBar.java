package com.android.internal.statusbar;

import android.app.ITransientNotificationCallback;
import android.content.ComponentName;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.hardware.biometrics.IBiometricContextListener;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.PromptInfo;
import android.hardware.display.SemWifiDisplayParameter;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.media.INearbyMediaDevicesProvider;
import android.media.MediaRoute2Info;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RemoteViews;
import com.android.internal.statusbar.IAddTileResultCallback;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.android.internal.view.AppearanceRegion;

/* loaded from: classes4.dex */
public interface IStatusBar extends IInterface {

    public static class Default implements IStatusBar {
        @Override // com.android.internal.statusbar.IStatusBar
        public void abortTransient(int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void addQsTile(ComponentName componentName) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void addQsTileToFrontOrEnd(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void animateCollapsePanels() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void animateExpandNotificationsPanel() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void animateExpandSettingsPanel(String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void appTransitionCancelled(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void appTransitionFinished(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void appTransitionPending(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void appTransitionStarting(int i, long j, long j2) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void cancelPreloadRecentApps() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void cancelRequestAddTile(String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void clickQsTile(ComponentName componentName) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void confirmImmersivePrompt() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void disable(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void disableForAllDisplays(DisableStates disableStates) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void dismissInattentiveSleepWarning(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void dismissKeyboardShortcutsMenu() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void dumpProto(String[] strArr, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void handleSystemKey(KeyEvent keyEvent) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void hideAuthenticationDialog(long j) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void hideRecentApps(boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void hideToast(String str, IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void immersiveModeChanged(int i, boolean z, int i2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void moveFocusedTaskToDesktop(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void moveFocusedTaskToFullscreen(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void moveFocusedTaskToStageSplit(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void notifyPenState(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void notifyRequestedGameToolsWin(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void notifyRequestedSystemKey(boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void notifySamsungPayInfo(int i, boolean z, Rect rect) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onBiometricAuthenticated(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onBiometricError(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onBiometricHelp(int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onCameraLaunchGestureDetected(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onDisplayAddSystemDecorations(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onDisplayRemoveSystemDecorations(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onEmergencyActionLaunchGestureDetected() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onFlashlightKeyPressed(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onFocusedDisplayChanged(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onProposedRotationChanged(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onWalletLaunchGestureDetected() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void passThroughShellCommand(String[] strArr, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void preloadRecentApps() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void remQsTile(ComponentName componentName) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void removeIcon(String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void requestAddTile(int i, ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, IAddTileResultCallback iAddTileResultCallback) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void requestMagnificationConnection(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void requestTileServiceListeningState(ComponentName componentName) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void resetScheduleAutoHide() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void runGcForTest() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void sendKeyEventToDesktopTaskbar(KeyEvent keyEvent) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void sendThreeFingerGestureKeyEvent(KeyEvent keyEvent) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setBlueLightFilter(boolean z, int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setIcon(String str, StatusBarIcon statusBarIcon) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setImeWindowStatus(int i, int i2, int i3, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setNavigationBarLumaSamplingEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setQsTiles(String[] strArr) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setSplitscreenFocus(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setTopAppHidesStatusBar(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setWindowState(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showAssistDisclosure() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showGlobalActionsMenu(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showInattentiveSleepWarning() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showMediaOutputSwitcher(String str, UserHandle userHandle) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showPictureInPictureMenu() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showPinningEnterExitToast(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showPinningEscapeToast() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showRearDisplayDialog(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showRecentApps(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showScreenPinningRequest(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showShutdownUi(boolean z, String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showToast(int i, String str, IBinder iBinder, CharSequence charSequence, IBinder iBinder2, int i2, ITransientNotificationCallback iTransientNotificationCallback, int i3) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showTransient(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showWirelessChargingAnimation(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void startAssist(Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void startSearcleByHomeKey(boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void startTracing() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void stopTracing() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void suppressAmbientDisplay(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void toggleKeyboardShortcutsMenu(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void toggleNotificationsPanel() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void toggleRecentApps() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void toggleSplitScreen() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void toggleTaskbar() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) throws RemoteException {
        }
    }

    void abortTransient(int i, int i2) throws RemoteException;

    void addQsTile(ComponentName componentName) throws RemoteException;

    void addQsTileToFrontOrEnd(ComponentName componentName, boolean z) throws RemoteException;

    void animateCollapsePanels() throws RemoteException;

    void animateExpandNotificationsPanel() throws RemoteException;

    void animateExpandSettingsPanel(String str) throws RemoteException;

    void appTransitionCancelled(int i) throws RemoteException;

    void appTransitionFinished(int i) throws RemoteException;

    void appTransitionPending(int i) throws RemoteException;

    void appTransitionStarting(int i, long j, long j2) throws RemoteException;

    void cancelPreloadRecentApps() throws RemoteException;

    void cancelRequestAddTile(String str) throws RemoteException;

    void clickQsTile(ComponentName componentName) throws RemoteException;

    void confirmImmersivePrompt() throws RemoteException;

    void disable(int i, int i2, int i3) throws RemoteException;

    void disableForAllDisplays(DisableStates disableStates) throws RemoteException;

    void dismissInattentiveSleepWarning(boolean z) throws RemoteException;

    void dismissKeyboardShortcutsMenu() throws RemoteException;

    void dumpProto(String[] strArr, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void handleSystemKey(KeyEvent keyEvent) throws RemoteException;

    void hideAuthenticationDialog(long j) throws RemoteException;

    void hideRecentApps(boolean z, boolean z2) throws RemoteException;

    void hideToast(String str, IBinder iBinder) throws RemoteException;

    void immersiveModeChanged(int i, boolean z, int i2) throws RemoteException;

    void moveFocusedTaskToDesktop(int i) throws RemoteException;

    void moveFocusedTaskToFullscreen(int i) throws RemoteException;

    void moveFocusedTaskToStageSplit(int i, boolean z) throws RemoteException;

    void notifyPenState(int i) throws RemoteException;

    void notifyRequestedGameToolsWin(boolean z) throws RemoteException;

    void notifyRequestedSystemKey(boolean z, boolean z2) throws RemoteException;

    void notifySamsungPayInfo(int i, boolean z, Rect rect) throws RemoteException;

    void onBiometricAuthenticated(int i) throws RemoteException;

    void onBiometricError(int i, int i2, int i3) throws RemoteException;

    void onBiometricHelp(int i, String str) throws RemoteException;

    void onCameraLaunchGestureDetected(int i) throws RemoteException;

    void onDisplayAddSystemDecorations(int i) throws RemoteException;

    void onDisplayRemoveSystemDecorations(int i) throws RemoteException;

    void onEmergencyActionLaunchGestureDetected() throws RemoteException;

    void onFlashlightKeyPressed(int i) throws RemoteException;

    void onFocusedDisplayChanged(int i) throws RemoteException;

    void onProposedRotationChanged(int i, boolean z) throws RemoteException;

    void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) throws RemoteException;

    void onWalletLaunchGestureDetected() throws RemoteException;

    void passThroughShellCommand(String[] strArr, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void preloadRecentApps() throws RemoteException;

    void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException;

    void remQsTile(ComponentName componentName) throws RemoteException;

    void removeIcon(String str) throws RemoteException;

    void requestAddTile(int i, ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, IAddTileResultCallback iAddTileResultCallback) throws RemoteException;

    void requestMagnificationConnection(boolean z) throws RemoteException;

    void requestTileServiceListeningState(ComponentName componentName) throws RemoteException;

    void resetScheduleAutoHide() throws RemoteException;

    void runGcForTest() throws RemoteException;

    void sendKeyEventToDesktopTaskbar(KeyEvent keyEvent) throws RemoteException;

    void sendThreeFingerGestureKeyEvent(KeyEvent keyEvent) throws RemoteException;

    void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) throws RemoteException;

    void setBlueLightFilter(boolean z, int i) throws RemoteException;

    void setIcon(String str, StatusBarIcon statusBarIcon) throws RemoteException;

    void setImeWindowStatus(int i, int i2, int i3, boolean z) throws RemoteException;

    void setNavigationBarLumaSamplingEnabled(int i, boolean z) throws RemoteException;

    void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) throws RemoteException;

    void setQsTiles(String[] strArr) throws RemoteException;

    void setSplitscreenFocus(boolean z) throws RemoteException;

    void setTopAppHidesStatusBar(boolean z) throws RemoteException;

    void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws RemoteException;

    void setWindowState(int i, int i2, int i3) throws RemoteException;

    void showAssistDisclosure() throws RemoteException;

    void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) throws RemoteException;

    void showGlobalActionsMenu(int i) throws RemoteException;

    void showInattentiveSleepWarning() throws RemoteException;

    void showMediaOutputSwitcher(String str, UserHandle userHandle) throws RemoteException;

    void showPictureInPictureMenu() throws RemoteException;

    void showPinningEnterExitToast(boolean z) throws RemoteException;

    void showPinningEscapeToast() throws RemoteException;

    void showRearDisplayDialog(int i) throws RemoteException;

    void showRecentApps(boolean z) throws RemoteException;

    void showScreenPinningRequest(int i) throws RemoteException;

    void showShutdownUi(boolean z, String str) throws RemoteException;

    void showToast(int i, String str, IBinder iBinder, CharSequence charSequence, IBinder iBinder2, int i2, ITransientNotificationCallback iTransientNotificationCallback, int i3) throws RemoteException;

    void showTransient(int i, int i2, boolean z) throws RemoteException;

    void showWirelessChargingAnimation(int i) throws RemoteException;

    void startAssist(Bundle bundle) throws RemoteException;

    void startSearcleByHomeKey(boolean z, boolean z2) throws RemoteException;

    void startTracing() throws RemoteException;

    void stopTracing() throws RemoteException;

    void suppressAmbientDisplay(boolean z) throws RemoteException;

    void toggleKeyboardShortcutsMenu(int i) throws RemoteException;

    void toggleNotificationsPanel() throws RemoteException;

    void toggleRecentApps() throws RemoteException;

    void toggleSplitScreen() throws RemoteException;

    void toggleTaskbar() throws RemoteException;

    void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException;

    void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) throws RemoteException;

    void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IStatusBar {
        public static final String DESCRIPTOR = "com.android.internal.statusbar.IStatusBar";
        static final int TRANSACTION_abortTransient = 57;
        static final int TRANSACTION_addQsTile = 37;
        static final int TRANSACTION_addQsTileToFrontOrEnd = 38;
        static final int TRANSACTION_animateCollapsePanels = 7;
        static final int TRANSACTION_animateExpandNotificationsPanel = 5;
        static final int TRANSACTION_animateExpandSettingsPanel = 6;
        static final int TRANSACTION_appTransitionCancelled = 25;
        static final int TRANSACTION_appTransitionFinished = 27;
        static final int TRANSACTION_appTransitionPending = 24;
        static final int TRANSACTION_appTransitionStarting = 26;
        static final int TRANSACTION_cancelPreloadRecentApps = 18;
        static final int TRANSACTION_cancelRequestAddTile = 71;
        static final int TRANSACTION_clickQsTile = 41;
        static final int TRANSACTION_confirmImmersivePrompt = 20;
        static final int TRANSACTION_disable = 3;
        static final int TRANSACTION_disableForAllDisplays = 4;
        static final int TRANSACTION_dismissInattentiveSleepWarning = 59;
        static final int TRANSACTION_dismissKeyboardShortcutsMenu = 22;
        static final int TRANSACTION_dumpProto = 76;
        static final int TRANSACTION_handleSystemKey = 42;
        static final int TRANSACTION_hideAuthenticationDialog = 50;
        static final int TRANSACTION_hideRecentApps = 13;
        static final int TRANSACTION_hideToast = 61;
        static final int TRANSACTION_immersiveModeChanged = 21;
        static final int TRANSACTION_moveFocusedTaskToDesktop = 82;
        static final int TRANSACTION_moveFocusedTaskToFullscreen = 78;
        static final int TRANSACTION_moveFocusedTaskToStageSplit = 79;
        static final int TRANSACTION_notifyPenState = 94;
        static final int TRANSACTION_notifyRequestedGameToolsWin = 90;
        static final int TRANSACTION_notifyRequestedSystemKey = 88;
        static final int TRANSACTION_notifySamsungPayInfo = 86;
        static final int TRANSACTION_onBiometricAuthenticated = 47;
        static final int TRANSACTION_onBiometricError = 49;
        static final int TRANSACTION_onBiometricHelp = 48;
        static final int TRANSACTION_onCameraLaunchGestureDetected = 30;
        static final int TRANSACTION_onDisplayAddSystemDecorations = 53;
        static final int TRANSACTION_onDisplayRemoveSystemDecorations = 54;
        static final int TRANSACTION_onEmergencyActionLaunchGestureDetected = 32;
        static final int TRANSACTION_onFlashlightKeyPressed = 92;
        static final int TRANSACTION_onFocusedDisplayChanged = 87;
        static final int TRANSACTION_onProposedRotationChanged = 35;
        static final int TRANSACTION_onSystemBarAttributesChanged = 55;
        static final int TRANSACTION_onWalletLaunchGestureDetected = 31;
        static final int TRANSACTION_passThroughShellCommand = 66;
        static final int TRANSACTION_preloadRecentApps = 17;
        static final int TRANSACTION_registerNearbyMediaDevicesProvider = 74;
        static final int TRANSACTION_remQsTile = 39;
        static final int TRANSACTION_removeIcon = 2;
        static final int TRANSACTION_requestAddTile = 70;
        static final int TRANSACTION_requestMagnificationConnection = 65;
        static final int TRANSACTION_requestTileServiceListeningState = 69;
        static final int TRANSACTION_resetScheduleAutoHide = 85;
        static final int TRANSACTION_runGcForTest = 68;
        static final int TRANSACTION_sendKeyEventToDesktopTaskbar = 93;
        static final int TRANSACTION_sendThreeFingerGestureKeyEvent = 89;
        static final int TRANSACTION_setBiometicContextListener = 51;
        static final int TRANSACTION_setBlueLightFilter = 83;
        static final int TRANSACTION_setIcon = 1;
        static final int TRANSACTION_setImeWindowStatus = 10;
        static final int TRANSACTION_setNavigationBarLumaSamplingEnabled = 67;
        static final int TRANSACTION_setNavigationBarShortcut = 84;
        static final int TRANSACTION_setQsTiles = 40;
        static final int TRANSACTION_setSplitscreenFocus = 80;
        static final int TRANSACTION_setTopAppHidesStatusBar = 36;
        static final int TRANSACTION_setUdfpsRefreshRateCallback = 52;
        static final int TRANSACTION_setWindowState = 11;
        static final int TRANSACTION_showAssistDisclosure = 28;
        static final int TRANSACTION_showAuthenticationDialog = 46;
        static final int TRANSACTION_showGlobalActionsMenu = 34;
        static final int TRANSACTION_showInattentiveSleepWarning = 58;
        static final int TRANSACTION_showMediaOutputSwitcher = 81;
        static final int TRANSACTION_showPictureInPictureMenu = 33;
        static final int TRANSACTION_showPinningEnterExitToast = 43;
        static final int TRANSACTION_showPinningEscapeToast = 44;
        static final int TRANSACTION_showRearDisplayDialog = 77;
        static final int TRANSACTION_showRecentApps = 12;
        static final int TRANSACTION_showScreenPinningRequest = 19;
        static final int TRANSACTION_showShutdownUi = 45;
        static final int TRANSACTION_showToast = 60;
        static final int TRANSACTION_showTransient = 56;
        static final int TRANSACTION_showWirelessChargingAnimation = 9;
        static final int TRANSACTION_startAssist = 29;
        static final int TRANSACTION_startSearcleByHomeKey = 91;
        static final int TRANSACTION_startTracing = 62;
        static final int TRANSACTION_stopTracing = 63;
        static final int TRANSACTION_suppressAmbientDisplay = 64;
        static final int TRANSACTION_toggleKeyboardShortcutsMenu = 23;
        static final int TRANSACTION_toggleNotificationsPanel = 8;
        static final int TRANSACTION_toggleRecentApps = 14;
        static final int TRANSACTION_toggleSplitScreen = 16;
        static final int TRANSACTION_toggleTaskbar = 15;
        static final int TRANSACTION_unregisterNearbyMediaDevicesProvider = 75;
        static final int TRANSACTION_updateMediaTapToTransferReceiverDisplay = 73;
        static final int TRANSACTION_updateMediaTapToTransferSenderDisplay = 72;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 93;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStatusBar asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IStatusBar)) {
                return (IStatusBar) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setIcon";
                case 2:
                    return "removeIcon";
                case 3:
                    return SemWifiDisplayParameter.VALUE_DISABLE;
                case 4:
                    return "disableForAllDisplays";
                case 5:
                    return "animateExpandNotificationsPanel";
                case 6:
                    return "animateExpandSettingsPanel";
                case 7:
                    return "animateCollapsePanels";
                case 8:
                    return "toggleNotificationsPanel";
                case 9:
                    return "showWirelessChargingAnimation";
                case 10:
                    return "setImeWindowStatus";
                case 11:
                    return "setWindowState";
                case 12:
                    return "showRecentApps";
                case 13:
                    return "hideRecentApps";
                case 14:
                    return "toggleRecentApps";
                case 15:
                    return "toggleTaskbar";
                case 16:
                    return "toggleSplitScreen";
                case 17:
                    return "preloadRecentApps";
                case 18:
                    return "cancelPreloadRecentApps";
                case 19:
                    return "showScreenPinningRequest";
                case 20:
                    return "confirmImmersivePrompt";
                case 21:
                    return "immersiveModeChanged";
                case 22:
                    return "dismissKeyboardShortcutsMenu";
                case 23:
                    return "toggleKeyboardShortcutsMenu";
                case 24:
                    return "appTransitionPending";
                case 25:
                    return "appTransitionCancelled";
                case 26:
                    return "appTransitionStarting";
                case 27:
                    return "appTransitionFinished";
                case 28:
                    return "showAssistDisclosure";
                case 29:
                    return "startAssist";
                case 30:
                    return "onCameraLaunchGestureDetected";
                case 31:
                    return "onWalletLaunchGestureDetected";
                case 32:
                    return "onEmergencyActionLaunchGestureDetected";
                case 33:
                    return "showPictureInPictureMenu";
                case 34:
                    return "showGlobalActionsMenu";
                case 35:
                    return "onProposedRotationChanged";
                case 36:
                    return "setTopAppHidesStatusBar";
                case 37:
                    return "addQsTile";
                case 38:
                    return "addQsTileToFrontOrEnd";
                case 39:
                    return "remQsTile";
                case 40:
                    return "setQsTiles";
                case 41:
                    return "clickQsTile";
                case 42:
                    return "handleSystemKey";
                case 43:
                    return "showPinningEnterExitToast";
                case 44:
                    return "showPinningEscapeToast";
                case 45:
                    return "showShutdownUi";
                case 46:
                    return "showAuthenticationDialog";
                case 47:
                    return "onBiometricAuthenticated";
                case 48:
                    return "onBiometricHelp";
                case 49:
                    return "onBiometricError";
                case 50:
                    return "hideAuthenticationDialog";
                case 51:
                    return "setBiometicContextListener";
                case 52:
                    return "setUdfpsRefreshRateCallback";
                case 53:
                    return "onDisplayAddSystemDecorations";
                case 54:
                    return "onDisplayRemoveSystemDecorations";
                case 55:
                    return "onSystemBarAttributesChanged";
                case 56:
                    return "showTransient";
                case 57:
                    return "abortTransient";
                case 58:
                    return "showInattentiveSleepWarning";
                case 59:
                    return "dismissInattentiveSleepWarning";
                case 60:
                    return "showToast";
                case 61:
                    return "hideToast";
                case 62:
                    return "startTracing";
                case 63:
                    return "stopTracing";
                case 64:
                    return "suppressAmbientDisplay";
                case 65:
                    return "requestMagnificationConnection";
                case 66:
                    return "passThroughShellCommand";
                case 67:
                    return "setNavigationBarLumaSamplingEnabled";
                case 68:
                    return "runGcForTest";
                case 69:
                    return "requestTileServiceListeningState";
                case 70:
                    return "requestAddTile";
                case 71:
                    return "cancelRequestAddTile";
                case 72:
                    return "updateMediaTapToTransferSenderDisplay";
                case 73:
                    return "updateMediaTapToTransferReceiverDisplay";
                case 74:
                    return "registerNearbyMediaDevicesProvider";
                case 75:
                    return "unregisterNearbyMediaDevicesProvider";
                case 76:
                    return "dumpProto";
                case 77:
                    return "showRearDisplayDialog";
                case 78:
                    return "moveFocusedTaskToFullscreen";
                case 79:
                    return "moveFocusedTaskToStageSplit";
                case 80:
                    return "setSplitscreenFocus";
                case 81:
                    return "showMediaOutputSwitcher";
                case 82:
                    return "moveFocusedTaskToDesktop";
                case 83:
                    return "setBlueLightFilter";
                case 84:
                    return "setNavigationBarShortcut";
                case 85:
                    return "resetScheduleAutoHide";
                case 86:
                    return "notifySamsungPayInfo";
                case 87:
                    return "onFocusedDisplayChanged";
                case 88:
                    return "notifyRequestedSystemKey";
                case 89:
                    return "sendThreeFingerGestureKeyEvent";
                case 90:
                    return "notifyRequestedGameToolsWin";
                case 91:
                    return "startSearcleByHomeKey";
                case 92:
                    return "onFlashlightKeyPressed";
                case 93:
                    return "sendKeyEventToDesktopTaskbar";
                case 94:
                    return "notifyPenState";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    StatusBarIcon statusBarIcon = (StatusBarIcon) parcel.readTypedObject(StatusBarIcon.CREATOR);
                    parcel.enforceNoDataAvail();
                    setIcon(readString, statusBarIcon);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeIcon(readString2);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disable(readInt, readInt2, readInt3);
                    return true;
                case 4:
                    DisableStates disableStates = (DisableStates) parcel.readTypedObject(DisableStates.CREATOR);
                    parcel.enforceNoDataAvail();
                    disableForAllDisplays(disableStates);
                    return true;
                case 5:
                    animateExpandNotificationsPanel();
                    return true;
                case 6:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    animateExpandSettingsPanel(readString3);
                    return true;
                case 7:
                    animateCollapsePanels();
                    return true;
                case 8:
                    toggleNotificationsPanel();
                    return true;
                case 9:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showWirelessChargingAnimation(readInt4);
                    return true;
                case 10:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImeWindowStatus(readInt5, readInt6, readInt7, readBoolean);
                    return true;
                case 11:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWindowState(readInt8, readInt9, readInt10);
                    return true;
                case 12:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showRecentApps(readBoolean2);
                    return true;
                case 13:
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    hideRecentApps(readBoolean3, readBoolean4);
                    return true;
                case 14:
                    toggleRecentApps();
                    return true;
                case 15:
                    toggleTaskbar();
                    return true;
                case 16:
                    toggleSplitScreen();
                    return true;
                case 17:
                    preloadRecentApps();
                    return true;
                case 18:
                    cancelPreloadRecentApps();
                    return true;
                case 19:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showScreenPinningRequest(readInt11);
                    return true;
                case 20:
                    confirmImmersivePrompt();
                    return true;
                case 21:
                    int readInt12 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    immersiveModeChanged(readInt12, readBoolean5, readInt13);
                    return true;
                case 22:
                    dismissKeyboardShortcutsMenu();
                    return true;
                case 23:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    toggleKeyboardShortcutsMenu(readInt14);
                    return true;
                case 24:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    appTransitionPending(readInt15);
                    return true;
                case 25:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    appTransitionCancelled(readInt16);
                    return true;
                case 26:
                    int readInt17 = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    appTransitionStarting(readInt17, readLong, readLong2);
                    return true;
                case 27:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    appTransitionFinished(readInt18);
                    return true;
                case 28:
                    showAssistDisclosure();
                    return true;
                case 29:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startAssist(bundle);
                    return true;
                case 30:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraLaunchGestureDetected(readInt19);
                    return true;
                case 31:
                    onWalletLaunchGestureDetected();
                    return true;
                case 32:
                    onEmergencyActionLaunchGestureDetected();
                    return true;
                case 33:
                    showPictureInPictureMenu();
                    return true;
                case 34:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showGlobalActionsMenu(readInt20);
                    return true;
                case 35:
                    int readInt21 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onProposedRotationChanged(readInt21, readBoolean6);
                    return true;
                case 36:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTopAppHidesStatusBar(readBoolean7);
                    return true;
                case 37:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    addQsTile(componentName);
                    return true;
                case 38:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    addQsTileToFrontOrEnd(componentName2, readBoolean8);
                    return true;
                case 39:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    remQsTile(componentName3);
                    return true;
                case 40:
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setQsTiles(createStringArray);
                    return true;
                case 41:
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    clickQsTile(componentName4);
                    return true;
                case 42:
                    KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleSystemKey(keyEvent);
                    return true;
                case 43:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showPinningEnterExitToast(readBoolean9);
                    return true;
                case 44:
                    showPinningEscapeToast();
                    return true;
                case 45:
                    boolean readBoolean10 = parcel.readBoolean();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showShutdownUi(readBoolean10, readString4);
                    return true;
                case 46:
                    PromptInfo promptInfo = (PromptInfo) parcel.readTypedObject(PromptInfo.CREATOR);
                    IBiometricSysuiReceiver asInterface = IBiometricSysuiReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int[] createIntArray = parcel.createIntArray();
                    boolean readBoolean11 = parcel.readBoolean();
                    boolean readBoolean12 = parcel.readBoolean();
                    int readInt22 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    String readString5 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    showAuthenticationDialog(promptInfo, asInterface, createIntArray, readBoolean11, readBoolean12, readInt22, readLong3, readString5, readLong4);
                    return true;
                case 47:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricAuthenticated(readInt23);
                    return true;
                case 48:
                    int readInt24 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiometricHelp(readInt24, readString6);
                    return true;
                case 49:
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricError(readInt25, readInt26, readInt27);
                    return true;
                case 50:
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    hideAuthenticationDialog(readLong5);
                    return true;
                case 51:
                    IBiometricContextListener asInterface2 = IBiometricContextListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setBiometicContextListener(asInterface2);
                    return true;
                case 52:
                    IUdfpsRefreshRateRequestCallback asInterface3 = IUdfpsRefreshRateRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setUdfpsRefreshRateCallback(asInterface3);
                    return true;
                case 53:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayAddSystemDecorations(readInt28);
                    return true;
                case 54:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayRemoveSystemDecorations(readInt29);
                    return true;
                case 55:
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    AppearanceRegion[] appearanceRegionArr = (AppearanceRegion[]) parcel.createTypedArray(AppearanceRegion.CREATOR);
                    boolean readBoolean13 = parcel.readBoolean();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    String readString7 = parcel.readString();
                    LetterboxDetails[] letterboxDetailsArr = (LetterboxDetails[]) parcel.createTypedArray(LetterboxDetails.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSystemBarAttributesChanged(readInt30, readInt31, appearanceRegionArr, readBoolean13, readInt32, readInt33, readString7, letterboxDetailsArr);
                    return true;
                case 56:
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showTransient(readInt34, readInt35, readBoolean14);
                    return true;
                case 57:
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    abortTransient(readInt36, readInt37);
                    return true;
                case 58:
                    showInattentiveSleepWarning();
                    return true;
                case 59:
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dismissInattentiveSleepWarning(readBoolean15);
                    return true;
                case 60:
                    int readInt38 = parcel.readInt();
                    String readString8 = parcel.readString();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt39 = parcel.readInt();
                    ITransientNotificationCallback asInterface4 = ITransientNotificationCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showToast(readInt38, readString8, readStrongBinder, charSequence, readStrongBinder2, readInt39, asInterface4, readInt40);
                    return true;
                case 61:
                    String readString9 = parcel.readString();
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    hideToast(readString9, readStrongBinder3);
                    return true;
                case 62:
                    startTracing();
                    return true;
                case 63:
                    stopTracing();
                    return true;
                case 64:
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressAmbientDisplay(readBoolean16);
                    return true;
                case 65:
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    requestMagnificationConnection(readBoolean17);
                    return true;
                case 66:
                    String[] createStringArray2 = parcel.createStringArray();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    passThroughShellCommand(createStringArray2, parcelFileDescriptor);
                    return true;
                case 67:
                    int readInt41 = parcel.readInt();
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNavigationBarLumaSamplingEnabled(readInt41, readBoolean18);
                    return true;
                case 68:
                    runGcForTest();
                    return true;
                case 69:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestTileServiceListeningState(componentName5);
                    return true;
                case 70:
                    int readInt42 = parcel.readInt();
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    CharSequence charSequence3 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    Icon icon = (Icon) parcel.readTypedObject(Icon.CREATOR);
                    IAddTileResultCallback asInterface5 = IAddTileResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestAddTile(readInt42, componentName6, charSequence2, charSequence3, icon, asInterface5);
                    return true;
                case 71:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelRequestAddTile(readString10);
                    return true;
                case 72:
                    int readInt43 = parcel.readInt();
                    MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    IUndoMediaTransferCallback asInterface6 = IUndoMediaTransferCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateMediaTapToTransferSenderDisplay(readInt43, mediaRoute2Info, asInterface6);
                    return true;
                case 73:
                    int readInt44 = parcel.readInt();
                    MediaRoute2Info mediaRoute2Info2 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    Icon icon2 = (Icon) parcel.readTypedObject(Icon.CREATOR);
                    CharSequence charSequence4 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    updateMediaTapToTransferReceiverDisplay(readInt44, mediaRoute2Info2, icon2, charSequence4);
                    return true;
                case 74:
                    INearbyMediaDevicesProvider asInterface7 = INearbyMediaDevicesProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerNearbyMediaDevicesProvider(asInterface7);
                    return true;
                case 75:
                    INearbyMediaDevicesProvider asInterface8 = INearbyMediaDevicesProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterNearbyMediaDevicesProvider(asInterface8);
                    return true;
                case 76:
                    String[] createStringArray3 = parcel.createStringArray();
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    dumpProto(createStringArray3, parcelFileDescriptor2);
                    return true;
                case 77:
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showRearDisplayDialog(readInt45);
                    return true;
                case 78:
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moveFocusedTaskToFullscreen(readInt46);
                    return true;
                case 79:
                    int readInt47 = parcel.readInt();
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    moveFocusedTaskToStageSplit(readInt47, readBoolean19);
                    return true;
                case 80:
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSplitscreenFocus(readBoolean20);
                    return true;
                case 81:
                    String readString11 = parcel.readString();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    showMediaOutputSwitcher(readString11, userHandle);
                    return true;
                case 82:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moveFocusedTaskToDesktop(readInt48);
                    return true;
                case 83:
                    boolean readBoolean21 = parcel.readBoolean();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBlueLightFilter(readBoolean21, readInt49);
                    return true;
                case 84:
                    String readString12 = parcel.readString();
                    RemoteViews remoteViews = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    int readInt50 = parcel.readInt();
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNavigationBarShortcut(readString12, remoteViews, readInt50, readInt51);
                    return true;
                case 85:
                    resetScheduleAutoHide();
                    return true;
                case 86:
                    int readInt52 = parcel.readInt();
                    boolean readBoolean22 = parcel.readBoolean();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySamsungPayInfo(readInt52, readBoolean22, rect);
                    return true;
                case 87:
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFocusedDisplayChanged(readInt53);
                    return true;
                case 88:
                    boolean readBoolean23 = parcel.readBoolean();
                    boolean readBoolean24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyRequestedSystemKey(readBoolean23, readBoolean24);
                    return true;
                case 89:
                    KeyEvent keyEvent2 = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendThreeFingerGestureKeyEvent(keyEvent2);
                    return true;
                case 90:
                    boolean readBoolean25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyRequestedGameToolsWin(readBoolean25);
                    return true;
                case 91:
                    boolean readBoolean26 = parcel.readBoolean();
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startSearcleByHomeKey(readBoolean26, readBoolean27);
                    return true;
                case 92:
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFlashlightKeyPressed(readInt54);
                    return true;
                case 93:
                    KeyEvent keyEvent3 = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendKeyEventToDesktopTaskbar(keyEvent3);
                    return true;
                case 94:
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPenState(readInt55);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IStatusBar {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setIcon(String str, StatusBarIcon statusBarIcon) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(statusBarIcon, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void removeIcon(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void disable(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void disableForAllDisplays(DisableStates disableStates) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(disableStates, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void animateExpandNotificationsPanel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void animateExpandSettingsPanel(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void animateCollapsePanels() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleNotificationsPanel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showWirelessChargingAnimation(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setImeWindowStatus(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setWindowState(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showRecentApps(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void hideRecentApps(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleRecentApps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleTaskbar() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleSplitScreen() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void preloadRecentApps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void cancelPreloadRecentApps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showScreenPinningRequest(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void confirmImmersivePrompt() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void immersiveModeChanged(int i, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void dismissKeyboardShortcutsMenu() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleKeyboardShortcutsMenu(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionPending(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionCancelled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionStarting(int i, long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionFinished(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showAssistDisclosure() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void startAssist(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onCameraLaunchGestureDetected(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onWalletLaunchGestureDetected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onEmergencyActionLaunchGestureDetected() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showPictureInPictureMenu() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showGlobalActionsMenu(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onProposedRotationChanged(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setTopAppHidesStatusBar(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void addQsTile(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void addQsTileToFrontOrEnd(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void remQsTile(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setQsTiles(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void clickQsTile(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(41, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void handleSystemKey(KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(42, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showPinningEnterExitToast(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showPinningEscapeToast() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showShutdownUi(boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(45, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(promptInfo, 0);
                    obtain.writeStrongInterface(iBiometricSysuiReceiver);
                    obtain.writeIntArray(iArr);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeLong(j2);
                    this.mRemote.transact(46, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onBiometricAuthenticated(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onBiometricHelp(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(48, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onBiometricError(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(49, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void hideAuthenticationDialog(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(50, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricContextListener);
                    this.mRemote.transact(51, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUdfpsRefreshRateRequestCallback);
                    this.mRemote.transact(52, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onDisplayAddSystemDecorations(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onDisplayRemoveSystemDecorations(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(appearanceRegionArr, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeString(str);
                    obtain.writeTypedArray(letterboxDetailsArr, 0);
                    this.mRemote.transact(55, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showTransient(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(56, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void abortTransient(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(57, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showInattentiveSleepWarning() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(58, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void dismissInattentiveSleepWarning(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(59, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showToast(int i, String str, IBinder iBinder, CharSequence charSequence, IBinder iBinder2, int i2, ITransientNotificationCallback iTransientNotificationCallback, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iTransientNotificationCallback);
                    obtain.writeInt(i3);
                    this.mRemote.transact(60, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void hideToast(String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(61, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void startTracing() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void stopTracing() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void suppressAmbientDisplay(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(64, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void requestMagnificationConnection(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(65, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void passThroughShellCommand(String[] strArr, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(66, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setNavigationBarLumaSamplingEnabled(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(67, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void runGcForTest() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void requestTileServiceListeningState(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(69, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void requestAddTile(int i, ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, IAddTileResultCallback iAddTileResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (charSequence2 != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence2, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeTypedObject(icon, 0);
                    obtain.writeStrongInterface(iAddTileResultCallback);
                    this.mRemote.transact(70, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void cancelRequestAddTile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(71, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeStrongInterface(iUndoMediaTransferCallback);
                    this.mRemote.transact(72, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeTypedObject(icon, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(73, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNearbyMediaDevicesProvider);
                    this.mRemote.transact(74, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNearbyMediaDevicesProvider);
                    this.mRemote.transact(75, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void dumpProto(String[] strArr, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(76, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showRearDisplayDialog(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(77, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void moveFocusedTaskToFullscreen(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(78, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void moveFocusedTaskToStageSplit(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(79, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setSplitscreenFocus(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(80, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showMediaOutputSwitcher(String str, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(81, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void moveFocusedTaskToDesktop(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(82, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setBlueLightFilter(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteViews, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(84, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void resetScheduleAutoHide() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void notifySamsungPayInfo(int i, boolean z, Rect rect) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(86, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onFocusedDisplayChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(87, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void notifyRequestedSystemKey(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(88, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void sendThreeFingerGestureKeyEvent(KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(89, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void notifyRequestedGameToolsWin(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(90, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void startSearcleByHomeKey(boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(91, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onFlashlightKeyPressed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(92, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void sendKeyEventToDesktopTaskbar(KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(93, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void notifyPenState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(94, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
