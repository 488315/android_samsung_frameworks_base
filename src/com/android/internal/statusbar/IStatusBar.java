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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IStatusBar)) {
                return (IStatusBar) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    StatusBarIcon statusBarIcon = (StatusBarIcon) parcel.readTypedObject(StatusBarIcon.CREATOR);
                    parcel.enforceNoDataAvail();
                    setIcon(string, statusBarIcon);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeIcon(string2);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disable(i3, i4, i5);
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
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    animateExpandSettingsPanel(string3);
                    return true;
                case 7:
                    animateCollapsePanels();
                    return true;
                case 8:
                    toggleNotificationsPanel();
                    return true;
                case 9:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showWirelessChargingAnimation(i6);
                    return true;
                case 10:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImeWindowStatus(i7, i8, i9, z);
                    return true;
                case 11:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWindowState(i10, i11, i12);
                    return true;
                case 12:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showRecentApps(z2);
                    return true;
                case 13:
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    hideRecentApps(z3, z4);
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
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showScreenPinningRequest(i13);
                    return true;
                case 20:
                    confirmImmersivePrompt();
                    return true;
                case 21:
                    int i14 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    immersiveModeChanged(i14, z5, i15);
                    return true;
                case 22:
                    dismissKeyboardShortcutsMenu();
                    return true;
                case 23:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    toggleKeyboardShortcutsMenu(i16);
                    return true;
                case 24:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    appTransitionPending(i17);
                    return true;
                case 25:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    appTransitionCancelled(i18);
                    return true;
                case 26:
                    int i19 = parcel.readInt();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    appTransitionStarting(i19, j, j2);
                    return true;
                case 27:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    appTransitionFinished(i20);
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
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraLaunchGestureDetected(i21);
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
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showGlobalActionsMenu(i22);
                    return true;
                case 35:
                    int i23 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onProposedRotationChanged(i23, z6);
                    return true;
                case 36:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTopAppHidesStatusBar(z7);
                    return true;
                case 37:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    addQsTile(componentName);
                    return true;
                case 38:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    addQsTileToFrontOrEnd(componentName2, z8);
                    return true;
                case 39:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    remQsTile(componentName3);
                    return true;
                case 40:
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setQsTiles(strArrCreateStringArray);
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
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showPinningEnterExitToast(z9);
                    return true;
                case 44:
                    showPinningEscapeToast();
                    return true;
                case 45:
                    boolean z10 = parcel.readBoolean();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showShutdownUi(z10, string4);
                    return true;
                case 46:
                    PromptInfo promptInfo = (PromptInfo) parcel.readTypedObject(PromptInfo.CREATOR);
                    IBiometricSysuiReceiver iBiometricSysuiReceiverAsInterface = IBiometricSysuiReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    boolean z11 = parcel.readBoolean();
                    boolean z12 = parcel.readBoolean();
                    int i24 = parcel.readInt();
                    long j3 = parcel.readLong();
                    String string5 = parcel.readString();
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    showAuthenticationDialog(promptInfo, iBiometricSysuiReceiverAsInterface, iArrCreateIntArray, z11, z12, i24, j3, string5, j4);
                    return true;
                case 47:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricAuthenticated(i25);
                    return true;
                case 48:
                    int i26 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiometricHelp(i26, string6);
                    return true;
                case 49:
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricError(i27, i28, i29);
                    return true;
                case 50:
                    long j5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    hideAuthenticationDialog(j5);
                    return true;
                case 51:
                    IBiometricContextListener iBiometricContextListenerAsInterface = IBiometricContextListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setBiometicContextListener(iBiometricContextListenerAsInterface);
                    return true;
                case 52:
                    IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallbackAsInterface = IUdfpsRefreshRateRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setUdfpsRefreshRateCallback(iUdfpsRefreshRateRequestCallbackAsInterface);
                    return true;
                case 53:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayAddSystemDecorations(i30);
                    return true;
                case 54:
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayRemoveSystemDecorations(i31);
                    return true;
                case 55:
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    AppearanceRegion[] appearanceRegionArr = (AppearanceRegion[]) parcel.createTypedArray(AppearanceRegion.CREATOR);
                    boolean z13 = parcel.readBoolean();
                    int i34 = parcel.readInt();
                    int i35 = parcel.readInt();
                    String string7 = parcel.readString();
                    LetterboxDetails[] letterboxDetailsArr = (LetterboxDetails[]) parcel.createTypedArray(LetterboxDetails.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSystemBarAttributesChanged(i32, i33, appearanceRegionArr, z13, i34, i35, string7, letterboxDetailsArr);
                    return true;
                case 56:
                    int i36 = parcel.readInt();
                    int i37 = parcel.readInt();
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showTransient(i36, i37, z14);
                    return true;
                case 57:
                    int i38 = parcel.readInt();
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    abortTransient(i38, i39);
                    return true;
                case 58:
                    showInattentiveSleepWarning();
                    return true;
                case 59:
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dismissInattentiveSleepWarning(z15);
                    return true;
                case 60:
                    int i40 = parcel.readInt();
                    String string8 = parcel.readString();
                    IBinder strongBinder = parcel.readStrongBinder();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i41 = parcel.readInt();
                    ITransientNotificationCallback iTransientNotificationCallbackAsInterface = ITransientNotificationCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showToast(i40, string8, strongBinder, charSequence, strongBinder2, i41, iTransientNotificationCallbackAsInterface, i42);
                    return true;
                case 61:
                    String string9 = parcel.readString();
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    hideToast(string9, strongBinder3);
                    return true;
                case 62:
                    startTracing();
                    return true;
                case 63:
                    stopTracing();
                    return true;
                case 64:
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressAmbientDisplay(z16);
                    return true;
                case 65:
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    requestMagnificationConnection(z17);
                    return true;
                case 66:
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    passThroughShellCommand(strArrCreateStringArray2, parcelFileDescriptor);
                    return true;
                case 67:
                    int i43 = parcel.readInt();
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNavigationBarLumaSamplingEnabled(i43, z18);
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
                    int i44 = parcel.readInt();
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    CharSequence charSequence3 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    Icon icon = (Icon) parcel.readTypedObject(Icon.CREATOR);
                    IAddTileResultCallback iAddTileResultCallbackAsInterface = IAddTileResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestAddTile(i44, componentName6, charSequence2, charSequence3, icon, iAddTileResultCallbackAsInterface);
                    return true;
                case 71:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelRequestAddTile(string10);
                    return true;
                case 72:
                    int i45 = parcel.readInt();
                    MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    IUndoMediaTransferCallback iUndoMediaTransferCallbackAsInterface = IUndoMediaTransferCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateMediaTapToTransferSenderDisplay(i45, mediaRoute2Info, iUndoMediaTransferCallbackAsInterface);
                    return true;
                case 73:
                    int i46 = parcel.readInt();
                    MediaRoute2Info mediaRoute2Info2 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    Icon icon2 = (Icon) parcel.readTypedObject(Icon.CREATOR);
                    CharSequence charSequence4 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    updateMediaTapToTransferReceiverDisplay(i46, mediaRoute2Info2, icon2, charSequence4);
                    return true;
                case 74:
                    INearbyMediaDevicesProvider iNearbyMediaDevicesProviderAsInterface = INearbyMediaDevicesProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerNearbyMediaDevicesProvider(iNearbyMediaDevicesProviderAsInterface);
                    return true;
                case 75:
                    INearbyMediaDevicesProvider iNearbyMediaDevicesProviderAsInterface2 = INearbyMediaDevicesProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterNearbyMediaDevicesProvider(iNearbyMediaDevicesProviderAsInterface2);
                    return true;
                case 76:
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    dumpProto(strArrCreateStringArray3, parcelFileDescriptor2);
                    return true;
                case 77:
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showRearDisplayDialog(i47);
                    return true;
                case 78:
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moveFocusedTaskToFullscreen(i48);
                    return true;
                case 79:
                    int i49 = parcel.readInt();
                    boolean z19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    moveFocusedTaskToStageSplit(i49, z19);
                    return true;
                case 80:
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSplitscreenFocus(z20);
                    return true;
                case 81:
                    String string11 = parcel.readString();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    showMediaOutputSwitcher(string11, userHandle);
                    return true;
                case 82:
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moveFocusedTaskToDesktop(i50);
                    return true;
                case 83:
                    boolean z21 = parcel.readBoolean();
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBlueLightFilter(z21, i51);
                    return true;
                case 84:
                    String string12 = parcel.readString();
                    RemoteViews remoteViews = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    int i52 = parcel.readInt();
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNavigationBarShortcut(string12, remoteViews, i52, i53);
                    return true;
                case 85:
                    resetScheduleAutoHide();
                    return true;
                case 86:
                    int i54 = parcel.readInt();
                    boolean z22 = parcel.readBoolean();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifySamsungPayInfo(i54, z22, rect);
                    return true;
                case 87:
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFocusedDisplayChanged(i55);
                    return true;
                case 88:
                    boolean z23 = parcel.readBoolean();
                    boolean z24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyRequestedSystemKey(z23, z24);
                    return true;
                case 89:
                    KeyEvent keyEvent2 = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendThreeFingerGestureKeyEvent(keyEvent2);
                    return true;
                case 90:
                    boolean z25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyRequestedGameToolsWin(z25);
                    return true;
                case 91:
                    boolean z26 = parcel.readBoolean();
                    boolean z27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startSearcleByHomeKey(z26, z27);
                    return true;
                case 92:
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFlashlightKeyPressed(i56);
                    return true;
                case 93:
                    KeyEvent keyEvent3 = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendKeyEventToDesktopTaskbar(keyEvent3);
                    return true;
                case 94:
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyPenState(i57);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(statusBarIcon, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void removeIcon(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void disable(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void disableForAllDisplays(DisableStates disableStates) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(disableStates, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void animateExpandNotificationsPanel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void animateExpandSettingsPanel(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void animateCollapsePanels() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleNotificationsPanel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showWirelessChargingAnimation(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setImeWindowStatus(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setWindowState(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showRecentApps(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void hideRecentApps(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleRecentApps() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleTaskbar() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleSplitScreen() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void preloadRecentApps() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void cancelPreloadRecentApps() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showScreenPinningRequest(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void confirmImmersivePrompt() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void immersiveModeChanged(int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void dismissKeyboardShortcutsMenu() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleKeyboardShortcutsMenu(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionPending(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionCancelled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionStarting(int i, long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionFinished(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showAssistDisclosure() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void startAssist(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onCameraLaunchGestureDetected(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onWalletLaunchGestureDetected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onEmergencyActionLaunchGestureDetected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showPictureInPictureMenu() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showGlobalActionsMenu(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onProposedRotationChanged(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setTopAppHidesStatusBar(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(36, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void addQsTile(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(37, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void addQsTileToFrontOrEnd(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(38, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void remQsTile(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(39, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setQsTiles(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(40, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void clickQsTile(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(41, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void handleSystemKey(KeyEvent keyEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(42, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showPinningEnterExitToast(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showPinningEscapeToast() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showShutdownUi(boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(45, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(promptInfo, 0);
                    parcelObtain.writeStrongInterface(iBiometricSysuiReceiver);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(46, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onBiometricAuthenticated(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(47, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onBiometricHelp(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(48, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onBiometricError(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(49, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void hideAuthenticationDialog(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(50, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBiometricContextListener);
                    this.mRemote.transact(51, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUdfpsRefreshRateRequestCallback);
                    this.mRemote.transact(52, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onDisplayAddSystemDecorations(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(53, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onDisplayRemoveSystemDecorations(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(54, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, String str, LetterboxDetails[] letterboxDetailsArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(appearanceRegionArr, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedArray(letterboxDetailsArr, 0);
                    this.mRemote.transact(55, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showTransient(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(56, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void abortTransient(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(57, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showInattentiveSleepWarning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(58, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void dismissInattentiveSleepWarning(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(59, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showToast(int i, String str, IBinder iBinder, CharSequence charSequence, IBinder iBinder2, int i2, ITransientNotificationCallback iTransientNotificationCallback, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iTransientNotificationCallback);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(60, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void hideToast(String str, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(61, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void startTracing() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void stopTracing() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void suppressAmbientDisplay(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(64, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void requestMagnificationConnection(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(65, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void passThroughShellCommand(String[] strArr, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(66, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setNavigationBarLumaSamplingEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(67, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void runGcForTest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void requestTileServiceListeningState(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(69, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void requestAddTile(int i, ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, IAddTileResultCallback iAddTileResultCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (charSequence2 != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence2, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeTypedObject(icon, 0);
                    parcelObtain.writeStrongInterface(iAddTileResultCallback);
                    this.mRemote.transact(70, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void cancelRequestAddTile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(71, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    parcelObtain.writeStrongInterface(iUndoMediaTransferCallback);
                    this.mRemote.transact(72, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    parcelObtain.writeTypedObject(icon, 0);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(73, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNearbyMediaDevicesProvider);
                    this.mRemote.transact(74, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNearbyMediaDevicesProvider);
                    this.mRemote.transact(75, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void dumpProto(String[] strArr, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(76, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showRearDisplayDialog(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(77, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void moveFocusedTaskToFullscreen(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(78, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void moveFocusedTaskToStageSplit(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(79, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setSplitscreenFocus(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(80, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showMediaOutputSwitcher(String str, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(81, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void moveFocusedTaskToDesktop(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(82, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setBlueLightFilter(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(83, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(remoteViews, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(84, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void resetScheduleAutoHide() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void notifySamsungPayInfo(int i, boolean z, Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(86, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onFocusedDisplayChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(87, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void notifyRequestedSystemKey(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(88, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void sendThreeFingerGestureKeyEvent(KeyEvent keyEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(89, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void notifyRequestedGameToolsWin(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(90, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void startSearcleByHomeKey(boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(91, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onFlashlightKeyPressed(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(92, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void sendKeyEventToDesktopTaskbar(KeyEvent keyEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(93, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void notifyPenState(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(94, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
