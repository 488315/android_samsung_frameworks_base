package com.android.internal.statusbar;

import android.app.Notification;
import android.content.ComponentName;
import android.graphics.drawable.Icon;
import android.hardware.biometrics.IBiometricContextListener;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.PromptInfo;
import android.hardware.display.SemWifiDisplayParameter;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.hardware.usb.UsbManager;
import android.media.INearbyMediaDevicesProvider;
import android.media.MediaRoute2Info;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RemoteViews;
import com.android.internal.carlife.IStatusBarCarLife;
import com.android.internal.logging.InstanceId;
import com.android.internal.statusbar.IAddTileResultCallback;
import com.android.internal.statusbar.ISessionListener;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes4.dex */
public interface IStatusBarService extends IInterface {

    public static class Default implements IStatusBarService {
        @Override // com.android.internal.statusbar.IStatusBarService
        public void addTile(ComponentName componentName) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void cancelRequestAddTile(String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void clearInlineReplyUriPermissions(String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void clearNotificationEffects() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void clickTile(ComponentName componentName) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void collapsePanels() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void collapsePanelsToType(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disable(int i, IBinder iBinder, String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disable2(int i, IBinder iBinder, String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disable2ForUser(int i, IBinder iBinder, String str, int i2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disable2ForUserToType(int i, IBinder iBinder, String str, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disable2ToType(int i, IBinder iBinder, String str, int i2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disableForUser(int i, IBinder iBinder, String str, int i2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disableForUserToType(int i, IBinder iBinder, String str, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disableToType(int i, IBinder iBinder, String str, int i2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void dismissInattentiveSleepWarning(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void expandNotificationsPanel() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void expandNotificationsPanelToType(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void expandSettingsPanel(String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void expandSettingsPanelForDisplay(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void expandSettingsPanelToType(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public int[] getDisableFlags(IBinder iBinder, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public int[] getDisableFlagsToType(IBinder iBinder, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public int getLastSystemKey() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public int getNavBarMode() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public boolean getPanelExpandStateToType(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public boolean getQuickSettingPanelExpandStateToType(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void grantInlineReplyUriPermission(String str, Uri uri, UserHandle userHandle, String str2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void handleSystemKey(KeyEvent keyEvent) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void hideAuthenticationDialog(long j) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void hideCurrentInputMethodForBubbles(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public boolean isFOTAAvailableForGlobalActions() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public boolean isSysUiSafeModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public boolean isTracing() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onBiometricAuthenticated(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onBiometricError(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onBiometricHelp(int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onBubbleMetadataFlagChanged(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onClearAllNotifications(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onGlobalActionsHidden() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onGlobalActionsShown() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationActionClick(String str, int i, Notification.Action action, NotificationVisibility notificationVisibility, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationBubbleChanged(String str, boolean z, int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationClear(String str, int i, String str2, int i2, int i3, NotificationVisibility notificationVisibility) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationClick(String str, NotificationVisibility notificationVisibility) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationDataUpdateFromPDC(List<String> list) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationDirectReplied(String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationError(String str, String str2, int i, int i2, int i3, String str3, int i4) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationExpansionChanged(String str, boolean z, boolean z2, int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationFeedbackReceived(String str, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationSettingsViewed(String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationSmartReplySent(String str, int i, CharSequence charSequence, int i2, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationSmartSuggestionsAdded(String str, int i, int i2, boolean z, boolean z2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationVisibilityChanged(NotificationVisibility[] notificationVisibilityArr, NotificationVisibility[] notificationVisibilityArr2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onPanelHidden() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onPanelRevealed(boolean z, int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onSessionEnded(int i, InstanceId instanceId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onSessionStarted(int i, InstanceId instanceId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void reboot(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void rebootByBixby(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void registerSessionListener(int i, ISessionListener iSessionListener) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public RegisterStatusBarResult registerStatusBar(IStatusBar iStatusBar) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public RegisterStatusBarResult registerStatusBarAsType(IStatusBar iStatusBar, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public Map<String, RegisterStatusBarResult> registerStatusBarForAllDisplays(IStatusBar iStatusBar) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void registerStatusBarForCarLife(IStatusBarCarLife iStatusBarCarLife) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void remTile(ComponentName componentName) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void removeIcon(String str) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void requestAddTile(ComponentName componentName, CharSequence charSequence, Icon icon, int i, IAddTileResultCallback iAddTileResultCallback) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void requestTileServiceListeningState(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void resetScheduleAutoHide() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void restart() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void sendKeyEventToDesktopTaskbar(KeyEvent keyEvent) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setBlueLightFilter(boolean z, int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setIcon(String str, String str2, int i, int i2, String str3) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setIconVisibility(String str, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setImeWindowStatus(int i, int i2, int i3, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setIndicatorBgColor(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setNavBarMode(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setPanelExpandStateToType(boolean z, int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showInattentiveSleepWarning() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showPinningEnterExitToast(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showPinningEscapeToast() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showRearDisplayDialog(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void shutdown() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void shutdownByBixby() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void startTracing() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void stopTracing() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void suppressAmbientDisplay(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void togglePanel() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void togglePanelForDisplay(int i) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void unregisterSessionListener(int i, ISessionListener iSessionListener) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) throws RemoteException {
        }
    }

    void addTile(ComponentName componentName) throws RemoteException;

    void cancelRequestAddTile(String str) throws RemoteException;

    void clearInlineReplyUriPermissions(String str) throws RemoteException;

    void clearNotificationEffects() throws RemoteException;

    void clickTile(ComponentName componentName) throws RemoteException;

    void collapsePanels() throws RemoteException;

    void collapsePanelsToType(int i) throws RemoteException;

    void disable(int i, IBinder iBinder, String str) throws RemoteException;

    void disable2(int i, IBinder iBinder, String str) throws RemoteException;

    void disable2ForUser(int i, IBinder iBinder, String str, int i2) throws RemoteException;

    void disable2ForUserToType(int i, IBinder iBinder, String str, int i2, int i3) throws RemoteException;

    void disable2ToType(int i, IBinder iBinder, String str, int i2) throws RemoteException;

    void disableForUser(int i, IBinder iBinder, String str, int i2) throws RemoteException;

    void disableForUserToType(int i, IBinder iBinder, String str, int i2, int i3) throws RemoteException;

    void disableToType(int i, IBinder iBinder, String str, int i2) throws RemoteException;

    void dismissInattentiveSleepWarning(boolean z) throws RemoteException;

    void expandNotificationsPanel() throws RemoteException;

    void expandNotificationsPanelToType(int i) throws RemoteException;

    void expandSettingsPanel(String str) throws RemoteException;

    void expandSettingsPanelForDisplay(int i) throws RemoteException;

    void expandSettingsPanelToType(String str, int i) throws RemoteException;

    int[] getDisableFlags(IBinder iBinder, int i) throws RemoteException;

    int[] getDisableFlagsToType(IBinder iBinder, int i, int i2) throws RemoteException;

    int getLastSystemKey() throws RemoteException;

    int getNavBarMode() throws RemoteException;

    boolean getPanelExpandStateToType(int i) throws RemoteException;

    boolean getQuickSettingPanelExpandStateToType(int i) throws RemoteException;

    void grantInlineReplyUriPermission(String str, Uri uri, UserHandle userHandle, String str2) throws RemoteException;

    void handleSystemKey(KeyEvent keyEvent) throws RemoteException;

    void hideAuthenticationDialog(long j) throws RemoteException;

    void hideCurrentInputMethodForBubbles(int i) throws RemoteException;

    boolean isFOTAAvailableForGlobalActions() throws RemoteException;

    boolean isSysUiSafeModeEnabled() throws RemoteException;

    boolean isTracing() throws RemoteException;

    void onBiometricAuthenticated(int i) throws RemoteException;

    void onBiometricError(int i, int i2, int i3) throws RemoteException;

    void onBiometricHelp(int i, String str) throws RemoteException;

    void onBubbleMetadataFlagChanged(String str, int i) throws RemoteException;

    void onClearAllNotifications(int i, boolean z) throws RemoteException;

    void onGlobalActionsHidden() throws RemoteException;

    void onGlobalActionsShown() throws RemoteException;

    void onNotificationActionClick(String str, int i, Notification.Action action, NotificationVisibility notificationVisibility, boolean z) throws RemoteException;

    void onNotificationBubbleChanged(String str, boolean z, int i) throws RemoteException;

    void onNotificationClear(String str, int i, String str2, int i2, int i3, NotificationVisibility notificationVisibility) throws RemoteException;

    void onNotificationClick(String str, NotificationVisibility notificationVisibility) throws RemoteException;

    void onNotificationDataUpdateFromPDC(List<String> list) throws RemoteException;

    void onNotificationDirectReplied(String str) throws RemoteException;

    void onNotificationError(String str, String str2, int i, int i2, int i3, String str3, int i4) throws RemoteException;

    void onNotificationExpansionChanged(String str, boolean z, boolean z2, int i) throws RemoteException;

    void onNotificationFeedbackReceived(String str, Bundle bundle) throws RemoteException;

    void onNotificationSettingsViewed(String str) throws RemoteException;

    void onNotificationSmartReplySent(String str, int i, CharSequence charSequence, int i2, boolean z) throws RemoteException;

    void onNotificationSmartSuggestionsAdded(String str, int i, int i2, boolean z, boolean z2) throws RemoteException;

    void onNotificationVisibilityChanged(NotificationVisibility[] notificationVisibilityArr, NotificationVisibility[] notificationVisibilityArr2) throws RemoteException;

    void onPanelHidden() throws RemoteException;

    void onPanelRevealed(boolean z, int i) throws RemoteException;

    void onSessionEnded(int i, InstanceId instanceId) throws RemoteException;

    void onSessionStarted(int i, InstanceId instanceId) throws RemoteException;

    void reboot(boolean z) throws RemoteException;

    void rebootByBixby(boolean z) throws RemoteException;

    void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException;

    void registerSessionListener(int i, ISessionListener iSessionListener) throws RemoteException;

    RegisterStatusBarResult registerStatusBar(IStatusBar iStatusBar) throws RemoteException;

    RegisterStatusBarResult registerStatusBarAsType(IStatusBar iStatusBar, int i) throws RemoteException;

    Map<String, RegisterStatusBarResult> registerStatusBarForAllDisplays(IStatusBar iStatusBar) throws RemoteException;

    void registerStatusBarForCarLife(IStatusBarCarLife iStatusBarCarLife) throws RemoteException;

    void remTile(ComponentName componentName) throws RemoteException;

    void removeIcon(String str) throws RemoteException;

    void requestAddTile(ComponentName componentName, CharSequence charSequence, Icon icon, int i, IAddTileResultCallback iAddTileResultCallback) throws RemoteException;

    void requestTileServiceListeningState(ComponentName componentName, int i) throws RemoteException;

    void resetScheduleAutoHide() throws RemoteException;

    void restart() throws RemoteException;

    void sendKeyEventToDesktopTaskbar(KeyEvent keyEvent) throws RemoteException;

    void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) throws RemoteException;

    void setBlueLightFilter(boolean z, int i) throws RemoteException;

    void setIcon(String str, String str2, int i, int i2, String str3) throws RemoteException;

    void setIconVisibility(String str, boolean z) throws RemoteException;

    void setImeWindowStatus(int i, int i2, int i3, boolean z) throws RemoteException;

    void setIndicatorBgColor(int i) throws RemoteException;

    void setNavBarMode(int i) throws RemoteException;

    void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) throws RemoteException;

    void setPanelExpandStateToType(boolean z, int i) throws RemoteException;

    void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws RemoteException;

    void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) throws RemoteException;

    void showInattentiveSleepWarning() throws RemoteException;

    void showPinningEnterExitToast(boolean z) throws RemoteException;

    void showPinningEscapeToast() throws RemoteException;

    void showRearDisplayDialog(int i) throws RemoteException;

    void shutdown() throws RemoteException;

    void shutdownByBixby() throws RemoteException;

    void startTracing() throws RemoteException;

    void stopTracing() throws RemoteException;

    void suppressAmbientDisplay(boolean z) throws RemoteException;

    void togglePanel() throws RemoteException;

    void togglePanelForDisplay(int i) throws RemoteException;

    void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException;

    void unregisterSessionListener(int i, ISessionListener iSessionListener) throws RemoteException;

    void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) throws RemoteException;

    void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IStatusBarService {
        public static final String DESCRIPTOR = "com.android.internal.statusbar.IStatusBarService";
        static final int TRANSACTION_addTile = 42;
        static final int TRANSACTION_cancelRequestAddTile = 64;
        static final int TRANSACTION_clearInlineReplyUriPermissions = 34;
        static final int TRANSACTION_clearNotificationEffects = 18;
        static final int TRANSACTION_clickTile = 44;
        static final int TRANSACTION_collapsePanels = 2;
        static final int TRANSACTION_collapsePanelsToType = 86;
        static final int TRANSACTION_disable = 4;
        static final int TRANSACTION_disable2 = 6;
        static final int TRANSACTION_disable2ForUser = 7;
        static final int TRANSACTION_disable2ForUserToType = 79;
        static final int TRANSACTION_disable2ToType = 78;
        static final int TRANSACTION_disableForUser = 5;
        static final int TRANSACTION_disableForUserToType = 77;
        static final int TRANSACTION_disableToType = 76;
        static final int TRANSACTION_dismissInattentiveSleepWarning = 57;
        static final int TRANSACTION_expandNotificationsPanel = 1;
        static final int TRANSACTION_expandNotificationsPanelToType = 85;
        static final int TRANSACTION_expandSettingsPanel = 13;
        static final int TRANSACTION_expandSettingsPanelForDisplay = 89;
        static final int TRANSACTION_expandSettingsPanelToType = 87;
        static final int TRANSACTION_getDisableFlags = 8;
        static final int TRANSACTION_getDisableFlagsToType = 80;
        static final int TRANSACTION_getLastSystemKey = 46;
        static final int TRANSACTION_getNavBarMode = 66;
        static final int TRANSACTION_getPanelExpandStateToType = 83;
        static final int TRANSACTION_getQuickSettingPanelExpandStateToType = 84;
        static final int TRANSACTION_grantInlineReplyUriPermission = 33;
        static final int TRANSACTION_handleSystemKey = 45;
        static final int TRANSACTION_hideAuthenticationDialog = 53;
        static final int TRANSACTION_hideCurrentInputMethodForBubbles = 32;
        static final int TRANSACTION_isFOTAAvailableForGlobalActions = 40;
        static final int TRANSACTION_isSysUiSafeModeEnabled = 94;
        static final int TRANSACTION_isTracing = 60;
        static final int TRANSACTION_onBiometricAuthenticated = 50;
        static final int TRANSACTION_onBiometricError = 52;
        static final int TRANSACTION_onBiometricHelp = 51;
        static final int TRANSACTION_onBubbleMetadataFlagChanged = 31;
        static final int TRANSACTION_onClearAllNotifications = 22;
        static final int TRANSACTION_onGlobalActionsHidden = 37;
        static final int TRANSACTION_onGlobalActionsShown = 36;
        static final int TRANSACTION_onNotificationActionClick = 20;
        static final int TRANSACTION_onNotificationBubbleChanged = 30;
        static final int TRANSACTION_onNotificationClear = 23;
        static final int TRANSACTION_onNotificationClick = 19;
        static final int TRANSACTION_onNotificationDataUpdateFromPDC = 99;
        static final int TRANSACTION_onNotificationDirectReplied = 26;
        static final int TRANSACTION_onNotificationError = 21;
        static final int TRANSACTION_onNotificationExpansionChanged = 25;
        static final int TRANSACTION_onNotificationFeedbackReceived = 35;
        static final int TRANSACTION_onNotificationSettingsViewed = 29;
        static final int TRANSACTION_onNotificationSmartReplySent = 28;
        static final int TRANSACTION_onNotificationSmartSuggestionsAdded = 27;
        static final int TRANSACTION_onNotificationVisibilityChanged = 24;
        static final int TRANSACTION_onPanelHidden = 17;
        static final int TRANSACTION_onPanelRevealed = 16;
        static final int TRANSACTION_onSessionEnded = 70;
        static final int TRANSACTION_onSessionStarted = 69;
        static final int TRANSACTION_reboot = 39;
        static final int TRANSACTION_rebootByBixby = 96;
        static final int TRANSACTION_registerNearbyMediaDevicesProvider = 73;
        static final int TRANSACTION_registerSessionListener = 67;
        static final int TRANSACTION_registerStatusBar = 14;
        static final int TRANSACTION_registerStatusBarAsType = 81;
        static final int TRANSACTION_registerStatusBarForAllDisplays = 15;
        static final int TRANSACTION_registerStatusBarForCarLife = 97;
        static final int TRANSACTION_remTile = 43;
        static final int TRANSACTION_removeIcon = 11;
        static final int TRANSACTION_requestAddTile = 63;
        static final int TRANSACTION_requestTileServiceListeningState = 62;
        static final int TRANSACTION_resetScheduleAutoHide = 91;
        static final int TRANSACTION_restart = 41;
        static final int TRANSACTION_sendKeyEventToDesktopTaskbar = 98;
        static final int TRANSACTION_setBiometicContextListener = 54;
        static final int TRANSACTION_setBlueLightFilter = 93;
        static final int TRANSACTION_setIcon = 9;
        static final int TRANSACTION_setIconVisibility = 10;
        static final int TRANSACTION_setImeWindowStatus = 12;
        static final int TRANSACTION_setIndicatorBgColor = 92;
        static final int TRANSACTION_setNavBarMode = 65;
        static final int TRANSACTION_setNavigationBarShortcut = 90;
        static final int TRANSACTION_setPanelExpandStateToType = 82;
        static final int TRANSACTION_setUdfpsRefreshRateCallback = 55;
        static final int TRANSACTION_showAuthenticationDialog = 49;
        static final int TRANSACTION_showInattentiveSleepWarning = 56;
        static final int TRANSACTION_showPinningEnterExitToast = 47;
        static final int TRANSACTION_showPinningEscapeToast = 48;
        static final int TRANSACTION_showRearDisplayDialog = 75;
        static final int TRANSACTION_shutdown = 38;
        static final int TRANSACTION_shutdownByBixby = 95;
        static final int TRANSACTION_startTracing = 58;
        static final int TRANSACTION_stopTracing = 59;
        static final int TRANSACTION_suppressAmbientDisplay = 61;
        static final int TRANSACTION_togglePanel = 3;
        static final int TRANSACTION_togglePanelForDisplay = 88;
        static final int TRANSACTION_unregisterNearbyMediaDevicesProvider = 74;
        static final int TRANSACTION_unregisterSessionListener = 68;
        static final int TRANSACTION_updateMediaTapToTransferReceiverDisplay = 72;
        static final int TRANSACTION_updateMediaTapToTransferSenderDisplay = 71;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 98;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStatusBarService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IStatusBarService)) {
                return (IStatusBarService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "expandNotificationsPanel";
                case 2:
                    return "collapsePanels";
                case 3:
                    return "togglePanel";
                case 4:
                    return SemWifiDisplayParameter.VALUE_DISABLE;
                case 5:
                    return "disableForUser";
                case 6:
                    return "disable2";
                case 7:
                    return "disable2ForUser";
                case 8:
                    return "getDisableFlags";
                case 9:
                    return "setIcon";
                case 10:
                    return "setIconVisibility";
                case 11:
                    return "removeIcon";
                case 12:
                    return "setImeWindowStatus";
                case 13:
                    return "expandSettingsPanel";
                case 14:
                    return "registerStatusBar";
                case 15:
                    return "registerStatusBarForAllDisplays";
                case 16:
                    return "onPanelRevealed";
                case 17:
                    return "onPanelHidden";
                case 18:
                    return "clearNotificationEffects";
                case 19:
                    return "onNotificationClick";
                case 20:
                    return "onNotificationActionClick";
                case 21:
                    return "onNotificationError";
                case 22:
                    return "onClearAllNotifications";
                case 23:
                    return "onNotificationClear";
                case 24:
                    return "onNotificationVisibilityChanged";
                case 25:
                    return "onNotificationExpansionChanged";
                case 26:
                    return "onNotificationDirectReplied";
                case 27:
                    return "onNotificationSmartSuggestionsAdded";
                case 28:
                    return "onNotificationSmartReplySent";
                case 29:
                    return "onNotificationSettingsViewed";
                case 30:
                    return "onNotificationBubbleChanged";
                case 31:
                    return "onBubbleMetadataFlagChanged";
                case 32:
                    return "hideCurrentInputMethodForBubbles";
                case 33:
                    return "grantInlineReplyUriPermission";
                case 34:
                    return "clearInlineReplyUriPermissions";
                case 35:
                    return "onNotificationFeedbackReceived";
                case 36:
                    return "onGlobalActionsShown";
                case 37:
                    return "onGlobalActionsHidden";
                case 38:
                    return UsbManager.USB_FUNCTION_SHUTDOWN;
                case 39:
                    return "reboot";
                case 40:
                    return "isFOTAAvailableForGlobalActions";
                case 41:
                    return DefaultActionNames.ACTION_RESTART;
                case 42:
                    return "addTile";
                case 43:
                    return "remTile";
                case 44:
                    return "clickTile";
                case 45:
                    return "handleSystemKey";
                case 46:
                    return "getLastSystemKey";
                case 47:
                    return "showPinningEnterExitToast";
                case 48:
                    return "showPinningEscapeToast";
                case 49:
                    return "showAuthenticationDialog";
                case 50:
                    return "onBiometricAuthenticated";
                case 51:
                    return "onBiometricHelp";
                case 52:
                    return "onBiometricError";
                case 53:
                    return "hideAuthenticationDialog";
                case 54:
                    return "setBiometicContextListener";
                case 55:
                    return "setUdfpsRefreshRateCallback";
                case 56:
                    return "showInattentiveSleepWarning";
                case 57:
                    return "dismissInattentiveSleepWarning";
                case 58:
                    return "startTracing";
                case 59:
                    return "stopTracing";
                case 60:
                    return "isTracing";
                case 61:
                    return "suppressAmbientDisplay";
                case 62:
                    return "requestTileServiceListeningState";
                case 63:
                    return "requestAddTile";
                case 64:
                    return "cancelRequestAddTile";
                case 65:
                    return "setNavBarMode";
                case 66:
                    return "getNavBarMode";
                case 67:
                    return "registerSessionListener";
                case 68:
                    return "unregisterSessionListener";
                case 69:
                    return "onSessionStarted";
                case 70:
                    return "onSessionEnded";
                case 71:
                    return "updateMediaTapToTransferSenderDisplay";
                case 72:
                    return "updateMediaTapToTransferReceiverDisplay";
                case 73:
                    return "registerNearbyMediaDevicesProvider";
                case 74:
                    return "unregisterNearbyMediaDevicesProvider";
                case 75:
                    return "showRearDisplayDialog";
                case 76:
                    return "disableToType";
                case 77:
                    return "disableForUserToType";
                case 78:
                    return "disable2ToType";
                case 79:
                    return "disable2ForUserToType";
                case 80:
                    return "getDisableFlagsToType";
                case 81:
                    return "registerStatusBarAsType";
                case 82:
                    return "setPanelExpandStateToType";
                case 83:
                    return "getPanelExpandStateToType";
                case 84:
                    return "getQuickSettingPanelExpandStateToType";
                case 85:
                    return "expandNotificationsPanelToType";
                case 86:
                    return "collapsePanelsToType";
                case 87:
                    return "expandSettingsPanelToType";
                case 88:
                    return "togglePanelForDisplay";
                case 89:
                    return "expandSettingsPanelForDisplay";
                case 90:
                    return "setNavigationBarShortcut";
                case 91:
                    return "resetScheduleAutoHide";
                case 92:
                    return "setIndicatorBgColor";
                case 93:
                    return "setBlueLightFilter";
                case 94:
                    return "isSysUiSafeModeEnabled";
                case 95:
                    return "shutdownByBixby";
                case 96:
                    return "rebootByBixby";
                case 97:
                    return "registerStatusBarForCarLife";
                case 98:
                    return "sendKeyEventToDesktopTaskbar";
                case 99:
                    return "onNotificationDataUpdateFromPDC";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, final Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    expandNotificationsPanel();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    collapsePanels();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    togglePanel();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i3 = parcel.readInt();
                    IBinder strongBinder = parcel.readStrongBinder();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disable(i3, strongBinder, string);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i4 = parcel.readInt();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    String string2 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableForUser(i4, strongBinder2, string2, i5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i6 = parcel.readInt();
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disable2(i6, strongBinder3, string3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i7 = parcel.readInt();
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    String string4 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disable2ForUser(i7, strongBinder4, string4, i8);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] disableFlags = getDisableFlags(strongBinder5, i9);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(disableFlags);
                    return true;
                case 9:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setIcon(string5, string6, i10, i11, string7);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string8 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setIconVisibility(string8, z);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeIcon(string9);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImeWindowStatus(i12, i13, i14, z2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    expandSettingsPanel(string10);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IStatusBar iStatusBarAsInterface = IStatusBar.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    RegisterStatusBarResult registerStatusBarResultRegisterStatusBar = registerStatusBar(iStatusBarAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registerStatusBarResultRegisterStatusBar, 1);
                    return true;
                case 15:
                    IStatusBar iStatusBarAsInterface2 = IStatusBar.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    Map<String, RegisterStatusBarResult> mapRegisterStatusBarForAllDisplays = registerStatusBarForAllDisplays(iStatusBarAsInterface2);
                    parcel2.writeNoException();
                    if (mapRegisterStatusBarForAllDisplays == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(mapRegisterStatusBarForAllDisplays.size());
                        mapRegisterStatusBarForAllDisplays.forEach(new BiConsumer() { // from class: com.android.internal.statusbar.IStatusBarService$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IStatusBarService.Stub.lambda$onTransact$0(parcel2, (String) obj, (RegisterStatusBarResult) obj2);
                            }
                        });
                    }
                    return true;
                case 16:
                    boolean z3 = parcel.readBoolean();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPanelRevealed(z3, i15);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    onPanelHidden();
                    parcel2.writeNoException();
                    return true;
                case 18:
                    clearNotificationEffects();
                    return true;
                case 19:
                    String string11 = parcel.readString();
                    NotificationVisibility notificationVisibility = (NotificationVisibility) parcel.readTypedObject(NotificationVisibility.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationClick(string11, notificationVisibility);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String string12 = parcel.readString();
                    int i16 = parcel.readInt();
                    Notification.Action action = (Notification.Action) parcel.readTypedObject(Notification.Action.CREATOR);
                    NotificationVisibility notificationVisibility2 = (NotificationVisibility) parcel.readTypedObject(NotificationVisibility.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationActionClick(string12, i16, action, notificationVisibility2, z4);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    String string15 = parcel.readString();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationError(string13, string14, i17, i18, i19, string15, i20);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i21 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onClearAllNotifications(i21, z5);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String string16 = parcel.readString();
                    int i22 = parcel.readInt();
                    String string17 = parcel.readString();
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    NotificationVisibility notificationVisibility3 = (NotificationVisibility) parcel.readTypedObject(NotificationVisibility.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationClear(string16, i22, string17, i23, i24, notificationVisibility3);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    NotificationVisibility[] notificationVisibilityArr = (NotificationVisibility[]) parcel.createTypedArray(NotificationVisibility.CREATOR);
                    NotificationVisibility[] notificationVisibilityArr2 = (NotificationVisibility[]) parcel.createTypedArray(NotificationVisibility.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationVisibilityChanged(notificationVisibilityArr, notificationVisibilityArr2);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String string18 = parcel.readString();
                    boolean z6 = parcel.readBoolean();
                    boolean z7 = parcel.readBoolean();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationExpansionChanged(string18, z6, z7, i25);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNotificationDirectReplied(string19);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String string20 = parcel.readString();
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    boolean z8 = parcel.readBoolean();
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationSmartSuggestionsAdded(string20, i26, i27, z8, z9);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String string21 = parcel.readString();
                    int i28 = parcel.readInt();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int i29 = parcel.readInt();
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationSmartReplySent(string21, i28, charSequence, i29, z10);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNotificationSettingsViewed(string22);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String string23 = parcel.readString();
                    boolean z11 = parcel.readBoolean();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationBubbleChanged(string23, z11, i30);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String string24 = parcel.readString();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBubbleMetadataFlagChanged(string24, i31);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hideCurrentInputMethodForBubbles(i32);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    String string25 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    grantInlineReplyUriPermission(string25, uri, userHandle, string26);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearInlineReplyUriPermissions(string27);
                    return true;
                case 35:
                    String string28 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationFeedbackReceived(string28, bundle);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    onGlobalActionsShown();
                    parcel2.writeNoException();
                    return true;
                case 37:
                    onGlobalActionsHidden();
                    parcel2.writeNoException();
                    return true;
                case 38:
                    shutdown();
                    parcel2.writeNoException();
                    return true;
                case 39:
                    boolean z12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reboot(z12);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    boolean zIsFOTAAvailableForGlobalActions = isFOTAAvailableForGlobalActions();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFOTAAvailableForGlobalActions);
                    return true;
                case 41:
                    restart();
                    parcel2.writeNoException();
                    return true;
                case 42:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    addTile(componentName);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    remTile(componentName2);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    clickTile(componentName3);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleSystemKey(keyEvent);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int lastSystemKey = getLastSystemKey();
                    parcel2.writeNoException();
                    parcel2.writeInt(lastSystemKey);
                    return true;
                case 47:
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showPinningEnterExitToast(z13);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    showPinningEscapeToast();
                    parcel2.writeNoException();
                    return true;
                case 49:
                    PromptInfo promptInfo = (PromptInfo) parcel.readTypedObject(PromptInfo.CREATOR);
                    IBiometricSysuiReceiver iBiometricSysuiReceiverAsInterface = IBiometricSysuiReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    boolean z14 = parcel.readBoolean();
                    boolean z15 = parcel.readBoolean();
                    int i33 = parcel.readInt();
                    long j = parcel.readLong();
                    String string29 = parcel.readString();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    showAuthenticationDialog(promptInfo, iBiometricSysuiReceiverAsInterface, iArrCreateIntArray, z14, z15, i33, j, string29, j2);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricAuthenticated(i34);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int i35 = parcel.readInt();
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiometricHelp(i35, string30);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int i36 = parcel.readInt();
                    int i37 = parcel.readInt();
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricError(i36, i37, i38);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    hideAuthenticationDialog(j3);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    IBiometricContextListener iBiometricContextListenerAsInterface = IBiometricContextListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setBiometicContextListener(iBiometricContextListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallbackAsInterface = IUdfpsRefreshRateRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setUdfpsRefreshRateCallback(iUdfpsRefreshRateRequestCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    showInattentiveSleepWarning();
                    parcel2.writeNoException();
                    return true;
                case 57:
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dismissInattentiveSleepWarning(z16);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    startTracing();
                    parcel2.writeNoException();
                    return true;
                case 59:
                    stopTracing();
                    parcel2.writeNoException();
                    return true;
                case 60:
                    boolean zIsTracing = isTracing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTracing);
                    return true;
                case 61:
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressAmbientDisplay(z17);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestTileServiceListeningState(componentName4, i39);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    Icon icon = (Icon) parcel.readTypedObject(Icon.CREATOR);
                    int i40 = parcel.readInt();
                    IAddTileResultCallback iAddTileResultCallbackAsInterface = IAddTileResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestAddTile(componentName5, charSequence2, icon, i40, iAddTileResultCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelRequestAddTile(string31);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNavBarMode(i41);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int navBarMode = getNavBarMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(navBarMode);
                    return true;
                case 67:
                    int i42 = parcel.readInt();
                    ISessionListener iSessionListenerAsInterface = ISessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSessionListener(i42, iSessionListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    int i43 = parcel.readInt();
                    ISessionListener iSessionListenerAsInterface2 = ISessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSessionListener(i43, iSessionListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    int i44 = parcel.readInt();
                    InstanceId instanceId = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSessionStarted(i44, instanceId);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    int i45 = parcel.readInt();
                    InstanceId instanceId2 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSessionEnded(i45, instanceId2);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    int i46 = parcel.readInt();
                    MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    IUndoMediaTransferCallback iUndoMediaTransferCallbackAsInterface = IUndoMediaTransferCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateMediaTapToTransferSenderDisplay(i46, mediaRoute2Info, iUndoMediaTransferCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    int i47 = parcel.readInt();
                    MediaRoute2Info mediaRoute2Info2 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    Icon icon2 = (Icon) parcel.readTypedObject(Icon.CREATOR);
                    CharSequence charSequence3 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    updateMediaTapToTransferReceiverDisplay(i47, mediaRoute2Info2, icon2, charSequence3);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    INearbyMediaDevicesProvider iNearbyMediaDevicesProviderAsInterface = INearbyMediaDevicesProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerNearbyMediaDevicesProvider(iNearbyMediaDevicesProviderAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    INearbyMediaDevicesProvider iNearbyMediaDevicesProviderAsInterface2 = INearbyMediaDevicesProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterNearbyMediaDevicesProvider(iNearbyMediaDevicesProviderAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showRearDisplayDialog(i48);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    int i49 = parcel.readInt();
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    String string32 = parcel.readString();
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableToType(i49, strongBinder6, string32, i50);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    int i51 = parcel.readInt();
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    String string33 = parcel.readString();
                    int i52 = parcel.readInt();
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableForUserToType(i51, strongBinder7, string33, i52, i53);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    int i54 = parcel.readInt();
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    String string34 = parcel.readString();
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disable2ToType(i54, strongBinder8, string34, i55);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    int i56 = parcel.readInt();
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    String string35 = parcel.readString();
                    int i57 = parcel.readInt();
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disable2ForUserToType(i56, strongBinder9, string35, i57, i58);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    int i59 = parcel.readInt();
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] disableFlagsToType = getDisableFlagsToType(strongBinder10, i59, i60);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(disableFlagsToType);
                    return true;
                case 81:
                    IStatusBar iStatusBarAsInterface3 = IStatusBar.Stub.asInterface(parcel.readStrongBinder());
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    RegisterStatusBarResult registerStatusBarResultRegisterStatusBarAsType = registerStatusBarAsType(iStatusBarAsInterface3, i61);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registerStatusBarResultRegisterStatusBarAsType, 1);
                    return true;
                case 82:
                    boolean z18 = parcel.readBoolean();
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPanelExpandStateToType(z18, i62);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean panelExpandStateToType = getPanelExpandStateToType(i63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(panelExpandStateToType);
                    return true;
                case 84:
                    int i64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean quickSettingPanelExpandStateToType = getQuickSettingPanelExpandStateToType(i64);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(quickSettingPanelExpandStateToType);
                    return true;
                case 85:
                    int i65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    expandNotificationsPanelToType(i65);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    int i66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    collapsePanelsToType(i66);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    String string36 = parcel.readString();
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    expandSettingsPanelToType(string36, i67);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    int i68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    togglePanelForDisplay(i68);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    expandSettingsPanelForDisplay(i69);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    String string37 = parcel.readString();
                    RemoteViews remoteViews = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    int i70 = parcel.readInt();
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNavigationBarShortcut(string37, remoteViews, i70, i71);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    resetScheduleAutoHide();
                    parcel2.writeNoException();
                    return true;
                case 92:
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIndicatorBgColor(i72);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    boolean z19 = parcel.readBoolean();
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBlueLightFilter(z19, i73);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    boolean zIsSysUiSafeModeEnabled = isSysUiSafeModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSysUiSafeModeEnabled);
                    return true;
                case 95:
                    shutdownByBixby();
                    parcel2.writeNoException();
                    return true;
                case 96:
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    rebootByBixby(z20);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    IStatusBarCarLife iStatusBarCarLifeAsInterface = IStatusBarCarLife.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStatusBarForCarLife(iStatusBarCarLifeAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    KeyEvent keyEvent2 = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendKeyEventToDesktopTaskbar(keyEvent2);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    onNotificationDataUpdateFromPDC(arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel parcel, String str, RegisterStatusBarResult registerStatusBarResult) {
            parcel.writeString(str);
            parcel.writeTypedObject(registerStatusBarResult, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IStatusBarService {
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

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandNotificationsPanel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void collapsePanels() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void togglePanel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable(int i, IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disableForUser(int i, IBinder iBinder, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2(int i, IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2ForUser(int i, IBinder iBinder, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int[] getDisableFlags(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setIcon(String str, String str2, int i, int i2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setIconVisibility(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void removeIcon(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setImeWindowStatus(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandSettingsPanel(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public RegisterStatusBarResult registerStatusBar(IStatusBar iStatusBar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStatusBar);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RegisterStatusBarResult) parcelObtain2.readTypedObject(RegisterStatusBarResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public Map<String, RegisterStatusBarResult> registerStatusBarForAllDisplays(IStatusBar iStatusBar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStatusBar);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    final HashMap map = i < 0 ? null : new HashMap();
                    IntStream.range(0, i).forEach(new IntConsumer() { // from class: com.android.internal.statusbar.IStatusBarService$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            Parcel parcel = parcelObtain2;
                            map.put(parcel.readString(), (RegisterStatusBarResult) parcel.readTypedObject(RegisterStatusBarResult.CREATOR));
                        }
                    });
                    return map;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onPanelRevealed(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onPanelHidden() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void clearNotificationEffects() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationClick(String str, NotificationVisibility notificationVisibility) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(notificationVisibility, 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationActionClick(String str, int i, Notification.Action action, NotificationVisibility notificationVisibility, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(action, 0);
                    parcelObtain.writeTypedObject(notificationVisibility, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationError(String str, String str2, int i, int i2, int i3, String str3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onClearAllNotifications(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationClear(String str, int i, String str2, int i2, int i3, NotificationVisibility notificationVisibility) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(notificationVisibility, 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationVisibilityChanged(NotificationVisibility[] notificationVisibilityArr, NotificationVisibility[] notificationVisibilityArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(notificationVisibilityArr, 0);
                    parcelObtain.writeTypedArray(notificationVisibilityArr2, 0);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationExpansionChanged(String str, boolean z, boolean z2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationDirectReplied(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationSmartSuggestionsAdded(String str, int i, int i2, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationSmartReplySent(String str, int i, CharSequence charSequence, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationSettingsViewed(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationBubbleChanged(String str, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBubbleMetadataFlagChanged(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void hideCurrentInputMethodForBubbles(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void grantInlineReplyUriPermission(String str, Uri uri, UserHandle userHandle, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void clearInlineReplyUriPermissions(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(34, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationFeedbackReceived(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onGlobalActionsShown() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onGlobalActionsHidden() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void shutdown() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void reboot(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean isFOTAAvailableForGlobalActions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void restart() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void addTile(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void remTile(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void clickTile(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void handleSystemKey(KeyEvent keyEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int getLastSystemKey() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showPinningEnterExitToast(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showPinningEscapeToast() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
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
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBiometricAuthenticated(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBiometricHelp(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBiometricError(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void hideAuthenticationDialog(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBiometricContextListener);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUdfpsRefreshRateRequestCallback);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showInattentiveSleepWarning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void dismissInattentiveSleepWarning(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void startTracing() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void stopTracing() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean isTracing() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void suppressAmbientDisplay(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void requestTileServiceListeningState(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void requestAddTile(ComponentName componentName, CharSequence charSequence, Icon icon, int i, IAddTileResultCallback iAddTileResultCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeTypedObject(icon, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAddTileResultCallback);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void cancelRequestAddTile(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setNavBarMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int getNavBarMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void registerSessionListener(int i, ISessionListener iSessionListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSessionListener);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void unregisterSessionListener(int i, ISessionListener iSessionListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSessionListener);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onSessionStarted(int i, InstanceId instanceId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(instanceId, 0);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onSessionEnded(int i, InstanceId instanceId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(instanceId, 0);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    parcelObtain.writeStrongInterface(iUndoMediaTransferCallback);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
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
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNearbyMediaDevicesProvider);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNearbyMediaDevicesProvider);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showRearDisplayDialog(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disableToType(int i, IBinder iBinder, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disableForUserToType(int i, IBinder iBinder, String str, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2ToType(int i, IBinder iBinder, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2ForUserToType(int i, IBinder iBinder, String str, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int[] getDisableFlagsToType(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public RegisterStatusBarResult registerStatusBarAsType(IStatusBar iStatusBar, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStatusBar);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RegisterStatusBarResult) parcelObtain2.readTypedObject(RegisterStatusBarResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setPanelExpandStateToType(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean getPanelExpandStateToType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean getQuickSettingPanelExpandStateToType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandNotificationsPanelToType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void collapsePanelsToType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandSettingsPanelToType(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void togglePanelForDisplay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandSettingsPanelForDisplay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(remoteViews, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void resetScheduleAutoHide() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setIndicatorBgColor(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setBlueLightFilter(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean isSysUiSafeModeEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void shutdownByBixby() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void rebootByBixby(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void registerStatusBarForCarLife(IStatusBarCarLife iStatusBarCarLife) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStatusBarCarLife);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void sendKeyEventToDesktopTaskbar(KeyEvent keyEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationDataUpdateFromPDC(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
