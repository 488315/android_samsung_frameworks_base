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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IStatusBarService)) {
                return (IStatusBarService) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disable(readInt, readStrongBinder, readString);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableForUser(readInt2, readStrongBinder2, readString2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disable2(readInt4, readStrongBinder3, readString3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    String readString4 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disable2ForUser(readInt5, readStrongBinder4, readString4, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] disableFlags = getDisableFlags(readStrongBinder5, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(disableFlags);
                    return true;
                case 9:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setIcon(readString5, readString6, readInt8, readInt9, readString7);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString8 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setIconVisibility(readString8, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeIcon(readString9);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImeWindowStatus(readInt10, readInt11, readInt12, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    expandSettingsPanel(readString10);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IStatusBar asInterface = IStatusBar.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    RegisterStatusBarResult registerStatusBar = registerStatusBar(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registerStatusBar, 1);
                    return true;
                case 15:
                    IStatusBar asInterface2 = IStatusBar.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    Map<String, RegisterStatusBarResult> registerStatusBarForAllDisplays = registerStatusBarForAllDisplays(asInterface2);
                    parcel2.writeNoException();
                    if (registerStatusBarForAllDisplays == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(registerStatusBarForAllDisplays.size());
                        registerStatusBarForAllDisplays.forEach(new BiConsumer() { // from class: com.android.internal.statusbar.IStatusBarService$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IStatusBarService.Stub.lambda$onTransact$0(Parcel.this, (String) obj, (RegisterStatusBarResult) obj2);
                            }
                        });
                    }
                    return true;
                case 16:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPanelRevealed(readBoolean3, readInt13);
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
                    String readString11 = parcel.readString();
                    NotificationVisibility notificationVisibility = (NotificationVisibility) parcel.readTypedObject(NotificationVisibility.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationClick(readString11, notificationVisibility);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String readString12 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    Notification.Action action = (Notification.Action) parcel.readTypedObject(Notification.Action.CREATOR);
                    NotificationVisibility notificationVisibility2 = (NotificationVisibility) parcel.readTypedObject(NotificationVisibility.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationActionClick(readString12, readInt14, action, notificationVisibility2, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    String readString15 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationError(readString13, readString14, readInt15, readInt16, readInt17, readString15, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt19 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onClearAllNotifications(readInt19, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String readString16 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    String readString17 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    NotificationVisibility notificationVisibility3 = (NotificationVisibility) parcel.readTypedObject(NotificationVisibility.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationClear(readString16, readInt20, readString17, readInt21, readInt22, notificationVisibility3);
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
                    String readString18 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    boolean readBoolean7 = parcel.readBoolean();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationExpansionChanged(readString18, readBoolean6, readBoolean7, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNotificationDirectReplied(readString19);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    String readString20 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationSmartSuggestionsAdded(readString20, readInt24, readInt25, readBoolean8, readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString21 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt27 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationSmartReplySent(readString21, readInt26, charSequence, readInt27, readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNotificationSettingsViewed(readString22);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String readString23 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationBubbleChanged(readString23, readBoolean11, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String readString24 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBubbleMetadataFlagChanged(readString24, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hideCurrentInputMethodForBubbles(readInt30);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    String readString25 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    grantInlineReplyUriPermission(readString25, uri, userHandle, readString26);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearInlineReplyUriPermissions(readString27);
                    return true;
                case 35:
                    String readString28 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationFeedbackReceived(readString28, bundle);
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
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reboot(readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    boolean isFOTAAvailableForGlobalActions = isFOTAAvailableForGlobalActions();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFOTAAvailableForGlobalActions);
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
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showPinningEnterExitToast(readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    showPinningEscapeToast();
                    parcel2.writeNoException();
                    return true;
                case 49:
                    PromptInfo promptInfo = (PromptInfo) parcel.readTypedObject(PromptInfo.CREATOR);
                    IBiometricSysuiReceiver asInterface3 = IBiometricSysuiReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int[] createIntArray = parcel.createIntArray();
                    boolean readBoolean14 = parcel.readBoolean();
                    boolean readBoolean15 = parcel.readBoolean();
                    int readInt31 = parcel.readInt();
                    long readLong = parcel.readLong();
                    String readString29 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    showAuthenticationDialog(promptInfo, asInterface3, createIntArray, readBoolean14, readBoolean15, readInt31, readLong, readString29, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricAuthenticated(readInt32);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int readInt33 = parcel.readInt();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiometricHelp(readInt33, readString30);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricError(readInt34, readInt35, readInt36);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    hideAuthenticationDialog(readLong3);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    IBiometricContextListener asInterface4 = IBiometricContextListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setBiometicContextListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    IUdfpsRefreshRateRequestCallback asInterface5 = IUdfpsRefreshRateRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setUdfpsRefreshRateCallback(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    showInattentiveSleepWarning();
                    parcel2.writeNoException();
                    return true;
                case 57:
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dismissInattentiveSleepWarning(readBoolean16);
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
                    boolean isTracing = isTracing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTracing);
                    return true;
                case 61:
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressAmbientDisplay(readBoolean17);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestTileServiceListeningState(componentName4, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    Icon icon = (Icon) parcel.readTypedObject(Icon.CREATOR);
                    int readInt38 = parcel.readInt();
                    IAddTileResultCallback asInterface6 = IAddTileResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestAddTile(componentName5, charSequence2, icon, readInt38, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelRequestAddTile(readString31);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNavBarMode(readInt39);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int navBarMode = getNavBarMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(navBarMode);
                    return true;
                case 67:
                    int readInt40 = parcel.readInt();
                    ISessionListener asInterface7 = ISessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSessionListener(readInt40, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    int readInt41 = parcel.readInt();
                    ISessionListener asInterface8 = ISessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSessionListener(readInt41, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    int readInt42 = parcel.readInt();
                    InstanceId instanceId = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSessionStarted(readInt42, instanceId);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    int readInt43 = parcel.readInt();
                    InstanceId instanceId2 = (InstanceId) parcel.readTypedObject(InstanceId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSessionEnded(readInt43, instanceId2);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    int readInt44 = parcel.readInt();
                    MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    IUndoMediaTransferCallback asInterface9 = IUndoMediaTransferCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateMediaTapToTransferSenderDisplay(readInt44, mediaRoute2Info, asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    int readInt45 = parcel.readInt();
                    MediaRoute2Info mediaRoute2Info2 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    Icon icon2 = (Icon) parcel.readTypedObject(Icon.CREATOR);
                    CharSequence charSequence3 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    updateMediaTapToTransferReceiverDisplay(readInt45, mediaRoute2Info2, icon2, charSequence3);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    INearbyMediaDevicesProvider asInterface10 = INearbyMediaDevicesProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerNearbyMediaDevicesProvider(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    INearbyMediaDevicesProvider asInterface11 = INearbyMediaDevicesProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterNearbyMediaDevicesProvider(asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showRearDisplayDialog(readInt46);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    int readInt47 = parcel.readInt();
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    String readString32 = parcel.readString();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableToType(readInt47, readStrongBinder6, readString32, readInt48);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    int readInt49 = parcel.readInt();
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    String readString33 = parcel.readString();
                    int readInt50 = parcel.readInt();
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableForUserToType(readInt49, readStrongBinder7, readString33, readInt50, readInt51);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    int readInt52 = parcel.readInt();
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    String readString34 = parcel.readString();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disable2ToType(readInt52, readStrongBinder8, readString34, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    int readInt54 = parcel.readInt();
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    String readString35 = parcel.readString();
                    int readInt55 = parcel.readInt();
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disable2ForUserToType(readInt54, readStrongBinder9, readString35, readInt55, readInt56);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    int readInt57 = parcel.readInt();
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] disableFlagsToType = getDisableFlagsToType(readStrongBinder10, readInt57, readInt58);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(disableFlagsToType);
                    return true;
                case 81:
                    IStatusBar asInterface12 = IStatusBar.Stub.asInterface(parcel.readStrongBinder());
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    RegisterStatusBarResult registerStatusBarAsType = registerStatusBarAsType(asInterface12, readInt59);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registerStatusBarAsType, 1);
                    return true;
                case 82:
                    boolean readBoolean18 = parcel.readBoolean();
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setPanelExpandStateToType(readBoolean18, readInt60);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean panelExpandStateToType = getPanelExpandStateToType(readInt61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(panelExpandStateToType);
                    return true;
                case 84:
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean quickSettingPanelExpandStateToType = getQuickSettingPanelExpandStateToType(readInt62);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(quickSettingPanelExpandStateToType);
                    return true;
                case 85:
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    expandNotificationsPanelToType(readInt63);
                    parcel2.writeNoException();
                    return true;
                case 86:
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    collapsePanelsToType(readInt64);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    String readString36 = parcel.readString();
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    expandSettingsPanelToType(readString36, readInt65);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    togglePanelForDisplay(readInt66);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    expandSettingsPanelForDisplay(readInt67);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    String readString37 = parcel.readString();
                    RemoteViews remoteViews = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    int readInt68 = parcel.readInt();
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNavigationBarShortcut(readString37, remoteViews, readInt68, readInt69);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    resetScheduleAutoHide();
                    parcel2.writeNoException();
                    return true;
                case 92:
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIndicatorBgColor(readInt70);
                    parcel2.writeNoException();
                    return true;
                case 93:
                    boolean readBoolean19 = parcel.readBoolean();
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBlueLightFilter(readBoolean19, readInt71);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    boolean isSysUiSafeModeEnabled = isSysUiSafeModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSysUiSafeModeEnabled);
                    return true;
                case 95:
                    shutdownByBixby();
                    parcel2.writeNoException();
                    return true;
                case 96:
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    rebootByBixby(readBoolean20);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    IStatusBarCarLife asInterface13 = IStatusBarCarLife.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerStatusBarForCarLife(asInterface13);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    KeyEvent keyEvent2 = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendKeyEventToDesktopTaskbar(keyEvent2);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    onNotificationDataUpdateFromPDC(createStringArrayList);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void collapsePanels() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void togglePanel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable(int i, IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disableForUser(int i, IBinder iBinder, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2(int i, IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2ForUser(int i, IBinder iBinder, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int[] getDisableFlags(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setIcon(String str, String str2, int i, int i2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setIconVisibility(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void removeIcon(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setImeWindowStatus(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandSettingsPanel(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public RegisterStatusBarResult registerStatusBar(IStatusBar iStatusBar) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStatusBar);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RegisterStatusBarResult) obtain2.readTypedObject(RegisterStatusBarResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public Map<String, RegisterStatusBarResult> registerStatusBarForAllDisplays(IStatusBar iStatusBar) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStatusBar);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final HashMap hashMap = readInt < 0 ? null : new HashMap();
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: com.android.internal.statusbar.IStatusBarService$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            hashMap.put(r0.readString(), (RegisterStatusBarResult) Parcel.this.readTypedObject(RegisterStatusBarResult.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onPanelRevealed(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onPanelHidden() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void clearNotificationEffects() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationClick(String str, NotificationVisibility notificationVisibility) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(notificationVisibility, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationActionClick(String str, int i, Notification.Action action, NotificationVisibility notificationVisibility, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(action, 0);
                    obtain.writeTypedObject(notificationVisibility, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationError(String str, String str2, int i, int i2, int i3, String str3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onClearAllNotifications(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationClear(String str, int i, String str2, int i2, int i3, NotificationVisibility notificationVisibility) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(notificationVisibility, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationVisibilityChanged(NotificationVisibility[] notificationVisibilityArr, NotificationVisibility[] notificationVisibilityArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(notificationVisibilityArr, 0);
                    obtain.writeTypedArray(notificationVisibilityArr2, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationExpansionChanged(String str, boolean z, boolean z2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationDirectReplied(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationSmartSuggestionsAdded(String str, int i, int i2, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationSmartReplySent(String str, int i, CharSequence charSequence, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationSettingsViewed(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationBubbleChanged(String str, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBubbleMetadataFlagChanged(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void hideCurrentInputMethodForBubbles(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void grantInlineReplyUriPermission(String str, Uri uri, UserHandle userHandle, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void clearInlineReplyUriPermissions(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationFeedbackReceived(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onGlobalActionsShown() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onGlobalActionsHidden() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void shutdown() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void reboot(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean isFOTAAvailableForGlobalActions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void restart() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void addTile(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void remTile(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void clickTile(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void handleSystemKey(KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int getLastSystemKey() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showPinningEnterExitToast(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showPinningEscapeToast() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
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
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBiometricAuthenticated(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBiometricHelp(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBiometricError(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void hideAuthenticationDialog(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricContextListener);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUdfpsRefreshRateRequestCallback);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showInattentiveSleepWarning() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void dismissInattentiveSleepWarning(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void startTracing() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void stopTracing() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean isTracing() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void suppressAmbientDisplay(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void requestTileServiceListeningState(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void requestAddTile(ComponentName componentName, CharSequence charSequence, Icon icon, int i, IAddTileResultCallback iAddTileResultCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeTypedObject(icon, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAddTileResultCallback);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void cancelRequestAddTile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setNavBarMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int getNavBarMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void registerSessionListener(int i, ISessionListener iSessionListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSessionListener);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void unregisterSessionListener(int i, ISessionListener iSessionListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSessionListener);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onSessionStarted(int i, InstanceId instanceId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(instanceId, 0);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onSessionEnded(int i, InstanceId instanceId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(instanceId, 0);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeStrongInterface(iUndoMediaTransferCallback);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
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
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNearbyMediaDevicesProvider);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNearbyMediaDevicesProvider);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showRearDisplayDialog(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disableToType(int i, IBinder iBinder, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disableForUserToType(int i, IBinder iBinder, String str, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2ToType(int i, IBinder iBinder, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2ForUserToType(int i, IBinder iBinder, String str, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int[] getDisableFlagsToType(IBinder iBinder, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public RegisterStatusBarResult registerStatusBarAsType(IStatusBar iStatusBar, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStatusBar);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RegisterStatusBarResult) obtain2.readTypedObject(RegisterStatusBarResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setPanelExpandStateToType(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean getPanelExpandStateToType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean getQuickSettingPanelExpandStateToType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandNotificationsPanelToType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void collapsePanelsToType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandSettingsPanelToType(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void togglePanelForDisplay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandSettingsPanelForDisplay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setNavigationBarShortcut(String str, RemoteViews remoteViews, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteViews, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void resetScheduleAutoHide() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setIndicatorBgColor(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setBlueLightFilter(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean isSysUiSafeModeEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void shutdownByBixby() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void rebootByBixby(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void registerStatusBarForCarLife(IStatusBarCarLife iStatusBarCarLife) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStatusBarCarLife);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void sendKeyEventToDesktopTaskbar(KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationDataUpdateFromPDC(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
