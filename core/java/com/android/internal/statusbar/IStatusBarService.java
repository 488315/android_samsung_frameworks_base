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
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;
import java.util.List;

/* loaded from: classes5.dex */
public interface IStatusBarService extends IInterface {
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

    void onClearAllNotifications(int i) throws RemoteException;

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

    void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) throws RemoteException;

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

    void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws RemoteException;

    void unregisterSessionListener(int i, ISessionListener iSessionListener) throws RemoteException;

    void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) throws RemoteException;

    void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) throws RemoteException;

    public static class Default implements IStatusBarService {
        @Override // com.android.internal.statusbar.IStatusBarService
        public void expandNotificationsPanel() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void collapsePanels() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void togglePanel() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disable(int what, IBinder token, String pkg) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disableForUser(int what, IBinder token, String pkg, int userId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disable2(int what, IBinder token, String pkg) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disable2ForUser(int what, IBinder token, String pkg, int userId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public int[] getDisableFlags(IBinder token, int userId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setIcon(String slot, String iconPackage, int iconId, int iconLevel, String contentDescription) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setIconVisibility(String slot, boolean visible) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void removeIcon(String slot) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setImeWindowStatus(int displayId, IBinder token, int vis, int backDisposition, boolean showImeSwitcher) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void expandSettingsPanel(String subPanel) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public RegisterStatusBarResult registerStatusBar(IStatusBar callbacks) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onPanelRevealed(boolean clearNotificationEffects, int numItems) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onPanelHidden() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void clearNotificationEffects() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationClick(String key, NotificationVisibility nv) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationActionClick(String key, int actionIndex, Notification.Action action, NotificationVisibility nv, boolean generatedByAssistant) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationError(String pkg, String tag, int id, int uid, int initialPid, String message, int userId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onClearAllNotifications(int userId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationClear(String pkg, int userId, String key, int dismissalSurface, int dismissalSentiment, NotificationVisibility nv) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationVisibilityChanged(NotificationVisibility[] newlyVisibleKeys, NotificationVisibility[] noLongerVisibleKeys) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationExpansionChanged(String key, boolean userAction, boolean expanded, int notificationLocation) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationDirectReplied(String key) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationSmartSuggestionsAdded(String key, int smartReplyCount, int smartActionCount, boolean generatedByAsssistant, boolean editBeforeSending) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationSmartReplySent(String key, int replyIndex, CharSequence reply, int notificationLocation, boolean modifiedBeforeSending) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationSettingsViewed(String key) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationBubbleChanged(String key, boolean isBubble, int flags) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onBubbleMetadataFlagChanged(String key, int flags) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void hideCurrentInputMethodForBubbles(int displayId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void grantInlineReplyUriPermission(String key, Uri uri, UserHandle user, String packageName) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void clearInlineReplyUriPermissions(String key) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationFeedbackReceived(String key, Bundle feedback) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onGlobalActionsShown() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onGlobalActionsHidden() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void shutdown() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void reboot(boolean safeMode) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public boolean isFOTAAvailableForGlobalActions() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void restart() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void addTile(ComponentName tile) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void remTile(ComponentName tile) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void clickTile(ComponentName tile) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void handleSystemKey(KeyEvent key) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public int getLastSystemKey() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showPinningEnterExitToast(boolean entering) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showPinningEscapeToast() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver sysuiReceiver, int[] sensorIds, boolean credentialAllowed, boolean requireConfirmation, int userId, long operationId, String opPackageName, long requestId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onBiometricAuthenticated(int modality) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onBiometricHelp(int modality, String message) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onBiometricError(int modality, int error, int vendorCode) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void hideAuthenticationDialog(long requestId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setBiometicContextListener(IBiometricContextListener listener) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback callback) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showInattentiveSleepWarning() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void dismissInattentiveSleepWarning(boolean animated) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void startTracing() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void stopTracing() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public boolean isTracing() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void suppressAmbientDisplay(boolean suppress) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void requestTileServiceListeningState(ComponentName componentName, int userId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void requestAddTile(ComponentName componentName, CharSequence label, Icon icon, int userId, IAddTileResultCallback callback) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void cancelRequestAddTile(String packageName) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setNavBarMode(int navBarMode) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public int getNavBarMode() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void registerSessionListener(int sessionFlags, ISessionListener listener) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void unregisterSessionListener(int sessionFlags, ISessionListener listener) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onSessionStarted(int sessionType, InstanceId instanceId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onSessionEnded(int sessionType, InstanceId instanceId) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void updateMediaTapToTransferSenderDisplay(int displayState, MediaRoute2Info routeInfo, IUndoMediaTransferCallback undoCallback) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void updateMediaTapToTransferReceiverDisplay(int displayState, MediaRoute2Info routeInfo, Icon appIcon, CharSequence appName) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider provider) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider provider) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showRearDisplayDialog(int currentBaseState) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disableToType(int what, IBinder token, String pkg, int barType) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disableForUserToType(int what, IBinder token, String pkg, int userId, int barType) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disable2ToType(int what, IBinder token, String pkg, int barType) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disable2ForUserToType(int what, IBinder token, String pkg, int userId, int barType) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public int[] getDisableFlagsToType(IBinder token, int userId, int barType) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public RegisterStatusBarResult registerStatusBarAsType(IStatusBar callbacks, int barType) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setPanelExpandStateToType(boolean state, int barType) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public boolean getPanelExpandStateToType(int barType) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public boolean getQuickSettingPanelExpandStateToType(int barType) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void expandNotificationsPanelToType(int barType) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void collapsePanelsToType(int barType) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void expandSettingsPanelToType(String subPanel, int barType) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setNavigationBarShortcut(String requestClass, RemoteViews remoteViews, int position, int priority) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void resetScheduleAutoHide() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setIndicatorBgColor(int color) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setBlueLightFilter(boolean on, int intensity) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public boolean isSysUiSafeModeEnabled() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void shutdownByBixby() throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void rebootByBixby(boolean safeMode) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void registerStatusBarForCarLife(IStatusBarCarLife callbacks) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void sendKeyEventToDesktopTaskbar(KeyEvent event) throws RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationDataUpdateFromPDC(List<String> importantContacts) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStatusBarService {
        public static final String DESCRIPTOR = "com.android.internal.statusbar.IStatusBarService";
        static final int TRANSACTION_addTile = 41;
        static final int TRANSACTION_cancelRequestAddTile = 63;
        static final int TRANSACTION_clearInlineReplyUriPermissions = 33;
        static final int TRANSACTION_clearNotificationEffects = 17;
        static final int TRANSACTION_clickTile = 43;
        static final int TRANSACTION_collapsePanels = 2;
        static final int TRANSACTION_collapsePanelsToType = 85;
        static final int TRANSACTION_disable = 4;
        static final int TRANSACTION_disable2 = 6;
        static final int TRANSACTION_disable2ForUser = 7;
        static final int TRANSACTION_disable2ForUserToType = 78;
        static final int TRANSACTION_disable2ToType = 77;
        static final int TRANSACTION_disableForUser = 5;
        static final int TRANSACTION_disableForUserToType = 76;
        static final int TRANSACTION_disableToType = 75;
        static final int TRANSACTION_dismissInattentiveSleepWarning = 56;
        static final int TRANSACTION_expandNotificationsPanel = 1;
        static final int TRANSACTION_expandNotificationsPanelToType = 84;
        static final int TRANSACTION_expandSettingsPanel = 13;
        static final int TRANSACTION_expandSettingsPanelToType = 86;
        static final int TRANSACTION_getDisableFlags = 8;
        static final int TRANSACTION_getDisableFlagsToType = 79;
        static final int TRANSACTION_getLastSystemKey = 45;
        static final int TRANSACTION_getNavBarMode = 65;
        static final int TRANSACTION_getPanelExpandStateToType = 82;
        static final int TRANSACTION_getQuickSettingPanelExpandStateToType = 83;
        static final int TRANSACTION_grantInlineReplyUriPermission = 32;
        static final int TRANSACTION_handleSystemKey = 44;
        static final int TRANSACTION_hideAuthenticationDialog = 52;
        static final int TRANSACTION_hideCurrentInputMethodForBubbles = 31;
        static final int TRANSACTION_isFOTAAvailableForGlobalActions = 39;
        static final int TRANSACTION_isSysUiSafeModeEnabled = 91;
        static final int TRANSACTION_isTracing = 59;
        static final int TRANSACTION_onBiometricAuthenticated = 49;
        static final int TRANSACTION_onBiometricError = 51;
        static final int TRANSACTION_onBiometricHelp = 50;
        static final int TRANSACTION_onBubbleMetadataFlagChanged = 30;
        static final int TRANSACTION_onClearAllNotifications = 21;
        static final int TRANSACTION_onGlobalActionsHidden = 36;
        static final int TRANSACTION_onGlobalActionsShown = 35;
        static final int TRANSACTION_onNotificationActionClick = 19;
        static final int TRANSACTION_onNotificationBubbleChanged = 29;
        static final int TRANSACTION_onNotificationClear = 22;
        static final int TRANSACTION_onNotificationClick = 18;
        static final int TRANSACTION_onNotificationDataUpdateFromPDC = 96;
        static final int TRANSACTION_onNotificationDirectReplied = 25;
        static final int TRANSACTION_onNotificationError = 20;
        static final int TRANSACTION_onNotificationExpansionChanged = 24;
        static final int TRANSACTION_onNotificationFeedbackReceived = 34;
        static final int TRANSACTION_onNotificationSettingsViewed = 28;
        static final int TRANSACTION_onNotificationSmartReplySent = 27;
        static final int TRANSACTION_onNotificationSmartSuggestionsAdded = 26;
        static final int TRANSACTION_onNotificationVisibilityChanged = 23;
        static final int TRANSACTION_onPanelHidden = 16;
        static final int TRANSACTION_onPanelRevealed = 15;
        static final int TRANSACTION_onSessionEnded = 69;
        static final int TRANSACTION_onSessionStarted = 68;
        static final int TRANSACTION_reboot = 38;
        static final int TRANSACTION_rebootByBixby = 93;
        static final int TRANSACTION_registerNearbyMediaDevicesProvider = 72;
        static final int TRANSACTION_registerSessionListener = 66;
        static final int TRANSACTION_registerStatusBar = 14;
        static final int TRANSACTION_registerStatusBarAsType = 80;
        static final int TRANSACTION_registerStatusBarForCarLife = 94;
        static final int TRANSACTION_remTile = 42;
        static final int TRANSACTION_removeIcon = 11;
        static final int TRANSACTION_requestAddTile = 62;
        static final int TRANSACTION_requestTileServiceListeningState = 61;
        static final int TRANSACTION_resetScheduleAutoHide = 88;
        static final int TRANSACTION_restart = 40;
        static final int TRANSACTION_sendKeyEventToDesktopTaskbar = 95;
        static final int TRANSACTION_setBiometicContextListener = 53;
        static final int TRANSACTION_setBlueLightFilter = 90;
        static final int TRANSACTION_setIcon = 9;
        static final int TRANSACTION_setIconVisibility = 10;
        static final int TRANSACTION_setImeWindowStatus = 12;
        static final int TRANSACTION_setIndicatorBgColor = 89;
        static final int TRANSACTION_setNavBarMode = 64;
        static final int TRANSACTION_setNavigationBarShortcut = 87;
        static final int TRANSACTION_setPanelExpandStateToType = 81;
        static final int TRANSACTION_setUdfpsRefreshRateCallback = 54;
        static final int TRANSACTION_showAuthenticationDialog = 48;
        static final int TRANSACTION_showInattentiveSleepWarning = 55;
        static final int TRANSACTION_showPinningEnterExitToast = 46;
        static final int TRANSACTION_showPinningEscapeToast = 47;
        static final int TRANSACTION_showRearDisplayDialog = 74;
        static final int TRANSACTION_shutdown = 37;
        static final int TRANSACTION_shutdownByBixby = 92;
        static final int TRANSACTION_startTracing = 57;
        static final int TRANSACTION_stopTracing = 58;
        static final int TRANSACTION_suppressAmbientDisplay = 60;
        static final int TRANSACTION_togglePanel = 3;
        static final int TRANSACTION_unregisterNearbyMediaDevicesProvider = 73;
        static final int TRANSACTION_unregisterSessionListener = 67;
        static final int TRANSACTION_updateMediaTapToTransferReceiverDisplay = 71;
        static final int TRANSACTION_updateMediaTapToTransferSenderDisplay = 70;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStatusBarService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IStatusBarService)) {
                return (IStatusBarService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
                    return "onPanelRevealed";
                case 16:
                    return "onPanelHidden";
                case 17:
                    return "clearNotificationEffects";
                case 18:
                    return "onNotificationClick";
                case 19:
                    return "onNotificationActionClick";
                case 20:
                    return "onNotificationError";
                case 21:
                    return "onClearAllNotifications";
                case 22:
                    return "onNotificationClear";
                case 23:
                    return "onNotificationVisibilityChanged";
                case 24:
                    return "onNotificationExpansionChanged";
                case 25:
                    return "onNotificationDirectReplied";
                case 26:
                    return "onNotificationSmartSuggestionsAdded";
                case 27:
                    return "onNotificationSmartReplySent";
                case 28:
                    return "onNotificationSettingsViewed";
                case 29:
                    return "onNotificationBubbleChanged";
                case 30:
                    return "onBubbleMetadataFlagChanged";
                case 31:
                    return "hideCurrentInputMethodForBubbles";
                case 32:
                    return "grantInlineReplyUriPermission";
                case 33:
                    return "clearInlineReplyUriPermissions";
                case 34:
                    return "onNotificationFeedbackReceived";
                case 35:
                    return "onGlobalActionsShown";
                case 36:
                    return "onGlobalActionsHidden";
                case 37:
                    return UsbManager.USB_FUNCTION_SHUTDOWN;
                case 38:
                    return "reboot";
                case 39:
                    return "isFOTAAvailableForGlobalActions";
                case 40:
                    return DefaultActionNames.ACTION_RESTART;
                case 41:
                    return "addTile";
                case 42:
                    return "remTile";
                case 43:
                    return "clickTile";
                case 44:
                    return "handleSystemKey";
                case 45:
                    return "getLastSystemKey";
                case 46:
                    return "showPinningEnterExitToast";
                case 47:
                    return "showPinningEscapeToast";
                case 48:
                    return "showAuthenticationDialog";
                case 49:
                    return "onBiometricAuthenticated";
                case 50:
                    return "onBiometricHelp";
                case 51:
                    return "onBiometricError";
                case 52:
                    return "hideAuthenticationDialog";
                case 53:
                    return "setBiometicContextListener";
                case 54:
                    return "setUdfpsRefreshRateCallback";
                case 55:
                    return "showInattentiveSleepWarning";
                case 56:
                    return "dismissInattentiveSleepWarning";
                case 57:
                    return "startTracing";
                case 58:
                    return "stopTracing";
                case 59:
                    return "isTracing";
                case 60:
                    return "suppressAmbientDisplay";
                case 61:
                    return "requestTileServiceListeningState";
                case 62:
                    return "requestAddTile";
                case 63:
                    return "cancelRequestAddTile";
                case 64:
                    return "setNavBarMode";
                case 65:
                    return "getNavBarMode";
                case 66:
                    return "registerSessionListener";
                case 67:
                    return "unregisterSessionListener";
                case 68:
                    return "onSessionStarted";
                case 69:
                    return "onSessionEnded";
                case 70:
                    return "updateMediaTapToTransferSenderDisplay";
                case 71:
                    return "updateMediaTapToTransferReceiverDisplay";
                case 72:
                    return "registerNearbyMediaDevicesProvider";
                case 73:
                    return "unregisterNearbyMediaDevicesProvider";
                case 74:
                    return "showRearDisplayDialog";
                case 75:
                    return "disableToType";
                case 76:
                    return "disableForUserToType";
                case 77:
                    return "disable2ToType";
                case 78:
                    return "disable2ForUserToType";
                case 79:
                    return "getDisableFlagsToType";
                case 80:
                    return "registerStatusBarAsType";
                case 81:
                    return "setPanelExpandStateToType";
                case 82:
                    return "getPanelExpandStateToType";
                case 83:
                    return "getQuickSettingPanelExpandStateToType";
                case 84:
                    return "expandNotificationsPanelToType";
                case 85:
                    return "collapsePanelsToType";
                case 86:
                    return "expandSettingsPanelToType";
                case 87:
                    return "setNavigationBarShortcut";
                case 88:
                    return "resetScheduleAutoHide";
                case 89:
                    return "setIndicatorBgColor";
                case 90:
                    return "setBlueLightFilter";
                case 91:
                    return "isSysUiSafeModeEnabled";
                case 92:
                    return "shutdownByBixby";
                case 93:
                    return "rebootByBixby";
                case 94:
                    return "registerStatusBarForCarLife";
                case 95:
                    return "sendKeyEventToDesktopTaskbar";
                case 96:
                    return "onNotificationDataUpdateFromPDC";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    expandNotificationsPanel();
                    reply.writeNoException();
                    return true;
                case 2:
                    collapsePanels();
                    reply.writeNoException();
                    return true;
                case 3:
                    togglePanel();
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg0 = data.readInt();
                    IBinder _arg1 = data.readStrongBinder();
                    String _arg2 = data.readString();
                    data.enforceNoDataAvail();
                    disable(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg02 = data.readInt();
                    IBinder _arg12 = data.readStrongBinder();
                    String _arg22 = data.readString();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    disableForUser(_arg02, _arg12, _arg22, _arg3);
                    reply.writeNoException();
                    return true;
                case 6:
                    int _arg03 = data.readInt();
                    IBinder _arg13 = data.readStrongBinder();
                    String _arg23 = data.readString();
                    data.enforceNoDataAvail();
                    disable2(_arg03, _arg13, _arg23);
                    reply.writeNoException();
                    return true;
                case 7:
                    int _arg04 = data.readInt();
                    IBinder _arg14 = data.readStrongBinder();
                    String _arg24 = data.readString();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    disable2ForUser(_arg04, _arg14, _arg24, _arg32);
                    reply.writeNoException();
                    return true;
                case 8:
                    IBinder _arg05 = data.readStrongBinder();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result = getDisableFlags(_arg05, _arg15);
                    reply.writeNoException();
                    reply.writeIntArray(_result);
                    return true;
                case 9:
                    String _arg06 = data.readString();
                    String _arg16 = data.readString();
                    int _arg25 = data.readInt();
                    int _arg33 = data.readInt();
                    String _arg4 = data.readString();
                    data.enforceNoDataAvail();
                    setIcon(_arg06, _arg16, _arg25, _arg33, _arg4);
                    reply.writeNoException();
                    return true;
                case 10:
                    String _arg07 = data.readString();
                    boolean _arg17 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setIconVisibility(_arg07, _arg17);
                    reply.writeNoException();
                    return true;
                case 11:
                    String _arg08 = data.readString();
                    data.enforceNoDataAvail();
                    removeIcon(_arg08);
                    reply.writeNoException();
                    return true;
                case 12:
                    int _arg09 = data.readInt();
                    IBinder _arg18 = data.readStrongBinder();
                    int _arg26 = data.readInt();
                    int _arg34 = data.readInt();
                    boolean _arg42 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setImeWindowStatus(_arg09, _arg18, _arg26, _arg34, _arg42);
                    reply.writeNoException();
                    return true;
                case 13:
                    String _arg010 = data.readString();
                    data.enforceNoDataAvail();
                    expandSettingsPanel(_arg010);
                    reply.writeNoException();
                    return true;
                case 14:
                    IStatusBar _arg011 = IStatusBar.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    RegisterStatusBarResult _result2 = registerStatusBar(_arg011);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 15:
                    boolean _arg012 = data.readBoolean();
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    onPanelRevealed(_arg012, _arg19);
                    reply.writeNoException();
                    return true;
                case 16:
                    onPanelHidden();
                    reply.writeNoException();
                    return true;
                case 17:
                    clearNotificationEffects();
                    return true;
                case 18:
                    String _arg013 = data.readString();
                    NotificationVisibility _arg110 = (NotificationVisibility) data.readTypedObject(NotificationVisibility.CREATOR);
                    data.enforceNoDataAvail();
                    onNotificationClick(_arg013, _arg110);
                    reply.writeNoException();
                    return true;
                case 19:
                    String _arg014 = data.readString();
                    int _arg111 = data.readInt();
                    Notification.Action _arg27 = (Notification.Action) data.readTypedObject(Notification.Action.CREATOR);
                    NotificationVisibility _arg35 = (NotificationVisibility) data.readTypedObject(NotificationVisibility.CREATOR);
                    boolean _arg43 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onNotificationActionClick(_arg014, _arg111, _arg27, _arg35, _arg43);
                    reply.writeNoException();
                    return true;
                case 20:
                    String _arg015 = data.readString();
                    String _arg112 = data.readString();
                    int _arg28 = data.readInt();
                    int _arg36 = data.readInt();
                    int _arg44 = data.readInt();
                    String _arg5 = data.readString();
                    int _arg6 = data.readInt();
                    data.enforceNoDataAvail();
                    onNotificationError(_arg015, _arg112, _arg28, _arg36, _arg44, _arg5, _arg6);
                    reply.writeNoException();
                    return true;
                case 21:
                    int _arg016 = data.readInt();
                    data.enforceNoDataAvail();
                    onClearAllNotifications(_arg016);
                    reply.writeNoException();
                    return true;
                case 22:
                    String _arg017 = data.readString();
                    int _arg113 = data.readInt();
                    String _arg29 = data.readString();
                    int _arg37 = data.readInt();
                    int _arg45 = data.readInt();
                    NotificationVisibility _arg52 = (NotificationVisibility) data.readTypedObject(NotificationVisibility.CREATOR);
                    data.enforceNoDataAvail();
                    onNotificationClear(_arg017, _arg113, _arg29, _arg37, _arg45, _arg52);
                    reply.writeNoException();
                    return true;
                case 23:
                    NotificationVisibility[] _arg018 = (NotificationVisibility[]) data.createTypedArray(NotificationVisibility.CREATOR);
                    NotificationVisibility[] _arg114 = (NotificationVisibility[]) data.createTypedArray(NotificationVisibility.CREATOR);
                    data.enforceNoDataAvail();
                    onNotificationVisibilityChanged(_arg018, _arg114);
                    reply.writeNoException();
                    return true;
                case 24:
                    String _arg019 = data.readString();
                    boolean _arg115 = data.readBoolean();
                    boolean _arg210 = data.readBoolean();
                    int _arg38 = data.readInt();
                    data.enforceNoDataAvail();
                    onNotificationExpansionChanged(_arg019, _arg115, _arg210, _arg38);
                    reply.writeNoException();
                    return true;
                case 25:
                    String _arg020 = data.readString();
                    data.enforceNoDataAvail();
                    onNotificationDirectReplied(_arg020);
                    reply.writeNoException();
                    return true;
                case 26:
                    String _arg021 = data.readString();
                    int _arg116 = data.readInt();
                    int _arg211 = data.readInt();
                    boolean _arg39 = data.readBoolean();
                    boolean _arg46 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onNotificationSmartSuggestionsAdded(_arg021, _arg116, _arg211, _arg39, _arg46);
                    reply.writeNoException();
                    return true;
                case 27:
                    String _arg022 = data.readString();
                    int _arg117 = data.readInt();
                    CharSequence _arg212 = (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int _arg310 = data.readInt();
                    boolean _arg47 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onNotificationSmartReplySent(_arg022, _arg117, _arg212, _arg310, _arg47);
                    reply.writeNoException();
                    return true;
                case 28:
                    String _arg023 = data.readString();
                    data.enforceNoDataAvail();
                    onNotificationSettingsViewed(_arg023);
                    reply.writeNoException();
                    return true;
                case 29:
                    String _arg024 = data.readString();
                    boolean _arg118 = data.readBoolean();
                    int _arg213 = data.readInt();
                    data.enforceNoDataAvail();
                    onNotificationBubbleChanged(_arg024, _arg118, _arg213);
                    reply.writeNoException();
                    return true;
                case 30:
                    String _arg025 = data.readString();
                    int _arg119 = data.readInt();
                    data.enforceNoDataAvail();
                    onBubbleMetadataFlagChanged(_arg025, _arg119);
                    reply.writeNoException();
                    return true;
                case 31:
                    int _arg026 = data.readInt();
                    data.enforceNoDataAvail();
                    hideCurrentInputMethodForBubbles(_arg026);
                    reply.writeNoException();
                    return true;
                case 32:
                    String _arg027 = data.readString();
                    Uri _arg120 = (Uri) data.readTypedObject(Uri.CREATOR);
                    UserHandle _arg214 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    String _arg311 = data.readString();
                    data.enforceNoDataAvail();
                    grantInlineReplyUriPermission(_arg027, _arg120, _arg214, _arg311);
                    reply.writeNoException();
                    return true;
                case 33:
                    String _arg028 = data.readString();
                    data.enforceNoDataAvail();
                    clearInlineReplyUriPermissions(_arg028);
                    return true;
                case 34:
                    String _arg029 = data.readString();
                    Bundle _arg121 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    onNotificationFeedbackReceived(_arg029, _arg121);
                    reply.writeNoException();
                    return true;
                case 35:
                    onGlobalActionsShown();
                    reply.writeNoException();
                    return true;
                case 36:
                    onGlobalActionsHidden();
                    reply.writeNoException();
                    return true;
                case 37:
                    shutdown();
                    reply.writeNoException();
                    return true;
                case 38:
                    boolean _arg030 = data.readBoolean();
                    data.enforceNoDataAvail();
                    reboot(_arg030);
                    reply.writeNoException();
                    return true;
                case 39:
                    boolean _result3 = isFOTAAvailableForGlobalActions();
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 40:
                    restart();
                    reply.writeNoException();
                    return true;
                case 41:
                    ComponentName _arg031 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    addTile(_arg031);
                    reply.writeNoException();
                    return true;
                case 42:
                    ComponentName _arg032 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    remTile(_arg032);
                    reply.writeNoException();
                    return true;
                case 43:
                    ComponentName _arg033 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    clickTile(_arg033);
                    reply.writeNoException();
                    return true;
                case 44:
                    KeyEvent _arg034 = (KeyEvent) data.readTypedObject(KeyEvent.CREATOR);
                    data.enforceNoDataAvail();
                    handleSystemKey(_arg034);
                    reply.writeNoException();
                    return true;
                case 45:
                    int _result4 = getLastSystemKey();
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 46:
                    boolean _arg035 = data.readBoolean();
                    data.enforceNoDataAvail();
                    showPinningEnterExitToast(_arg035);
                    reply.writeNoException();
                    return true;
                case 47:
                    showPinningEscapeToast();
                    reply.writeNoException();
                    return true;
                case 48:
                    PromptInfo _arg036 = (PromptInfo) data.readTypedObject(PromptInfo.CREATOR);
                    IBiometricSysuiReceiver _arg122 = IBiometricSysuiReceiver.Stub.asInterface(data.readStrongBinder());
                    int[] _arg215 = data.createIntArray();
                    boolean _arg312 = data.readBoolean();
                    boolean _arg48 = data.readBoolean();
                    int _arg53 = data.readInt();
                    long _arg62 = data.readLong();
                    String _arg7 = data.readString();
                    long _arg8 = data.readLong();
                    data.enforceNoDataAvail();
                    showAuthenticationDialog(_arg036, _arg122, _arg215, _arg312, _arg48, _arg53, _arg62, _arg7, _arg8);
                    reply.writeNoException();
                    return true;
                case 49:
                    int _arg037 = data.readInt();
                    data.enforceNoDataAvail();
                    onBiometricAuthenticated(_arg037);
                    reply.writeNoException();
                    return true;
                case 50:
                    int _arg038 = data.readInt();
                    String _arg123 = data.readString();
                    data.enforceNoDataAvail();
                    onBiometricHelp(_arg038, _arg123);
                    reply.writeNoException();
                    return true;
                case 51:
                    int _arg039 = data.readInt();
                    int _arg124 = data.readInt();
                    int _arg216 = data.readInt();
                    data.enforceNoDataAvail();
                    onBiometricError(_arg039, _arg124, _arg216);
                    reply.writeNoException();
                    return true;
                case 52:
                    long _arg040 = data.readLong();
                    data.enforceNoDataAvail();
                    hideAuthenticationDialog(_arg040);
                    reply.writeNoException();
                    return true;
                case 53:
                    IBiometricContextListener _arg041 = IBiometricContextListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setBiometicContextListener(_arg041);
                    reply.writeNoException();
                    return true;
                case 54:
                    IUdfpsRefreshRateRequestCallback _arg042 = IUdfpsRefreshRateRequestCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setUdfpsRefreshRateCallback(_arg042);
                    reply.writeNoException();
                    return true;
                case 55:
                    showInattentiveSleepWarning();
                    reply.writeNoException();
                    return true;
                case 56:
                    boolean _arg043 = data.readBoolean();
                    data.enforceNoDataAvail();
                    dismissInattentiveSleepWarning(_arg043);
                    reply.writeNoException();
                    return true;
                case 57:
                    startTracing();
                    reply.writeNoException();
                    return true;
                case 58:
                    stopTracing();
                    reply.writeNoException();
                    return true;
                case 59:
                    boolean _result5 = isTracing();
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 60:
                    boolean _arg044 = data.readBoolean();
                    data.enforceNoDataAvail();
                    suppressAmbientDisplay(_arg044);
                    reply.writeNoException();
                    return true;
                case 61:
                    ComponentName _arg045 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg125 = data.readInt();
                    data.enforceNoDataAvail();
                    requestTileServiceListeningState(_arg045, _arg125);
                    reply.writeNoException();
                    return true;
                case 62:
                    ComponentName _arg046 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    CharSequence _arg126 = (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    Icon _arg217 = (Icon) data.readTypedObject(Icon.CREATOR);
                    int _arg313 = data.readInt();
                    IAddTileResultCallback _arg49 = IAddTileResultCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    requestAddTile(_arg046, _arg126, _arg217, _arg313, _arg49);
                    reply.writeNoException();
                    return true;
                case 63:
                    String _arg047 = data.readString();
                    data.enforceNoDataAvail();
                    cancelRequestAddTile(_arg047);
                    reply.writeNoException();
                    return true;
                case 64:
                    int _arg048 = data.readInt();
                    data.enforceNoDataAvail();
                    setNavBarMode(_arg048);
                    reply.writeNoException();
                    return true;
                case 65:
                    int _result6 = getNavBarMode();
                    reply.writeNoException();
                    reply.writeInt(_result6);
                    return true;
                case 66:
                    int _arg049 = data.readInt();
                    ISessionListener _arg127 = ISessionListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerSessionListener(_arg049, _arg127);
                    reply.writeNoException();
                    return true;
                case 67:
                    int _arg050 = data.readInt();
                    ISessionListener _arg128 = ISessionListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterSessionListener(_arg050, _arg128);
                    reply.writeNoException();
                    return true;
                case 68:
                    int _arg051 = data.readInt();
                    InstanceId _arg129 = (InstanceId) data.readTypedObject(InstanceId.CREATOR);
                    data.enforceNoDataAvail();
                    onSessionStarted(_arg051, _arg129);
                    reply.writeNoException();
                    return true;
                case 69:
                    int _arg052 = data.readInt();
                    InstanceId _arg130 = (InstanceId) data.readTypedObject(InstanceId.CREATOR);
                    data.enforceNoDataAvail();
                    onSessionEnded(_arg052, _arg130);
                    reply.writeNoException();
                    return true;
                case 70:
                    int _arg053 = data.readInt();
                    MediaRoute2Info _arg131 = (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                    IUndoMediaTransferCallback _arg218 = IUndoMediaTransferCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    updateMediaTapToTransferSenderDisplay(_arg053, _arg131, _arg218);
                    reply.writeNoException();
                    return true;
                case 71:
                    int _arg054 = data.readInt();
                    MediaRoute2Info _arg132 = (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                    Icon _arg219 = (Icon) data.readTypedObject(Icon.CREATOR);
                    CharSequence _arg314 = (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    updateMediaTapToTransferReceiverDisplay(_arg054, _arg132, _arg219, _arg314);
                    reply.writeNoException();
                    return true;
                case 72:
                    INearbyMediaDevicesProvider _arg055 = INearbyMediaDevicesProvider.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerNearbyMediaDevicesProvider(_arg055);
                    reply.writeNoException();
                    return true;
                case 73:
                    INearbyMediaDevicesProvider _arg056 = INearbyMediaDevicesProvider.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterNearbyMediaDevicesProvider(_arg056);
                    reply.writeNoException();
                    return true;
                case 74:
                    int _arg057 = data.readInt();
                    data.enforceNoDataAvail();
                    showRearDisplayDialog(_arg057);
                    reply.writeNoException();
                    return true;
                case 75:
                    int _arg058 = data.readInt();
                    IBinder _arg133 = data.readStrongBinder();
                    String _arg220 = data.readString();
                    int _arg315 = data.readInt();
                    data.enforceNoDataAvail();
                    disableToType(_arg058, _arg133, _arg220, _arg315);
                    reply.writeNoException();
                    return true;
                case 76:
                    int _arg059 = data.readInt();
                    IBinder _arg134 = data.readStrongBinder();
                    String _arg221 = data.readString();
                    int _arg316 = data.readInt();
                    int _arg410 = data.readInt();
                    data.enforceNoDataAvail();
                    disableForUserToType(_arg059, _arg134, _arg221, _arg316, _arg410);
                    reply.writeNoException();
                    return true;
                case 77:
                    int _arg060 = data.readInt();
                    IBinder _arg135 = data.readStrongBinder();
                    String _arg222 = data.readString();
                    int _arg317 = data.readInt();
                    data.enforceNoDataAvail();
                    disable2ToType(_arg060, _arg135, _arg222, _arg317);
                    reply.writeNoException();
                    return true;
                case 78:
                    int _arg061 = data.readInt();
                    IBinder _arg136 = data.readStrongBinder();
                    String _arg223 = data.readString();
                    int _arg318 = data.readInt();
                    int _arg411 = data.readInt();
                    data.enforceNoDataAvail();
                    disable2ForUserToType(_arg061, _arg136, _arg223, _arg318, _arg411);
                    reply.writeNoException();
                    return true;
                case 79:
                    IBinder _arg062 = data.readStrongBinder();
                    int _arg137 = data.readInt();
                    int _arg224 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result7 = getDisableFlagsToType(_arg062, _arg137, _arg224);
                    reply.writeNoException();
                    reply.writeIntArray(_result7);
                    return true;
                case 80:
                    IStatusBar _arg063 = IStatusBar.Stub.asInterface(data.readStrongBinder());
                    int _arg138 = data.readInt();
                    data.enforceNoDataAvail();
                    RegisterStatusBarResult _result8 = registerStatusBarAsType(_arg063, _arg138);
                    reply.writeNoException();
                    reply.writeTypedObject(_result8, 1);
                    return true;
                case 81:
                    boolean _arg064 = data.readBoolean();
                    int _arg139 = data.readInt();
                    data.enforceNoDataAvail();
                    setPanelExpandStateToType(_arg064, _arg139);
                    reply.writeNoException();
                    return true;
                case 82:
                    int _arg065 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result9 = getPanelExpandStateToType(_arg065);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 83:
                    int _arg066 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result10 = getQuickSettingPanelExpandStateToType(_arg066);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 84:
                    int _arg067 = data.readInt();
                    data.enforceNoDataAvail();
                    expandNotificationsPanelToType(_arg067);
                    reply.writeNoException();
                    return true;
                case 85:
                    int _arg068 = data.readInt();
                    data.enforceNoDataAvail();
                    collapsePanelsToType(_arg068);
                    reply.writeNoException();
                    return true;
                case 86:
                    String _arg069 = data.readString();
                    int _arg140 = data.readInt();
                    data.enforceNoDataAvail();
                    expandSettingsPanelToType(_arg069, _arg140);
                    reply.writeNoException();
                    return true;
                case 87:
                    String _arg070 = data.readString();
                    RemoteViews _arg141 = (RemoteViews) data.readTypedObject(RemoteViews.CREATOR);
                    int _arg225 = data.readInt();
                    int _arg319 = data.readInt();
                    data.enforceNoDataAvail();
                    setNavigationBarShortcut(_arg070, _arg141, _arg225, _arg319);
                    reply.writeNoException();
                    return true;
                case 88:
                    resetScheduleAutoHide();
                    reply.writeNoException();
                    return true;
                case 89:
                    int _arg071 = data.readInt();
                    data.enforceNoDataAvail();
                    setIndicatorBgColor(_arg071);
                    reply.writeNoException();
                    return true;
                case 90:
                    boolean _arg072 = data.readBoolean();
                    int _arg142 = data.readInt();
                    data.enforceNoDataAvail();
                    setBlueLightFilter(_arg072, _arg142);
                    reply.writeNoException();
                    return true;
                case 91:
                    boolean _result11 = isSysUiSafeModeEnabled();
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 92:
                    shutdownByBixby();
                    reply.writeNoException();
                    return true;
                case 93:
                    boolean _arg073 = data.readBoolean();
                    data.enforceNoDataAvail();
                    rebootByBixby(_arg073);
                    reply.writeNoException();
                    return true;
                case 94:
                    IStatusBarCarLife _arg074 = IStatusBarCarLife.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerStatusBarForCarLife(_arg074);
                    reply.writeNoException();
                    return true;
                case 95:
                    KeyEvent _arg075 = (KeyEvent) data.readTypedObject(KeyEvent.CREATOR);
                    data.enforceNoDataAvail();
                    sendKeyEventToDesktopTaskbar(_arg075);
                    reply.writeNoException();
                    return true;
                case 96:
                    List<String> _arg076 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    onNotificationDataUpdateFromPDC(_arg076);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IStatusBarService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void collapsePanels() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void togglePanel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable(int what, IBinder token, String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeStrongBinder(token);
                    _data.writeString(pkg);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disableForUser(int what, IBinder token, String pkg, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeStrongBinder(token);
                    _data.writeString(pkg);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2(int what, IBinder token, String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeStrongBinder(token);
                    _data.writeString(pkg);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2ForUser(int what, IBinder token, String pkg, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeStrongBinder(token);
                    _data.writeString(pkg);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int[] getDisableFlags(IBinder token, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setIcon(String slot, String iconPackage, int iconId, int iconLevel, String contentDescription) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(slot);
                    _data.writeString(iconPackage);
                    _data.writeInt(iconId);
                    _data.writeInt(iconLevel);
                    _data.writeString(contentDescription);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setIconVisibility(String slot, boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(slot);
                    _data.writeBoolean(visible);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void removeIcon(String slot) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(slot);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setImeWindowStatus(int displayId, IBinder token, int vis, int backDisposition, boolean showImeSwitcher) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeStrongBinder(token);
                    _data.writeInt(vis);
                    _data.writeInt(backDisposition);
                    _data.writeBoolean(showImeSwitcher);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandSettingsPanel(String subPanel) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(subPanel);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public RegisterStatusBarResult registerStatusBar(IStatusBar callbacks) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callbacks);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    RegisterStatusBarResult _result = (RegisterStatusBarResult) _reply.readTypedObject(RegisterStatusBarResult.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onPanelRevealed(boolean clearNotificationEffects, int numItems) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(clearNotificationEffects);
                    _data.writeInt(numItems);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onPanelHidden() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void clearNotificationEffects() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationClick(String key, NotificationVisibility nv) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeTypedObject(nv, 0);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationActionClick(String key, int actionIndex, Notification.Action action, NotificationVisibility nv, boolean generatedByAssistant) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(actionIndex);
                    _data.writeTypedObject(action, 0);
                    _data.writeTypedObject(nv, 0);
                    _data.writeBoolean(generatedByAssistant);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationError(String pkg, String tag, int id, int uid, int initialPid, String message, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeString(tag);
                    _data.writeInt(id);
                    _data.writeInt(uid);
                    _data.writeInt(initialPid);
                    _data.writeString(message);
                    _data.writeInt(userId);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onClearAllNotifications(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationClear(String pkg, int userId, String key, int dismissalSurface, int dismissalSentiment, NotificationVisibility nv) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    _data.writeInt(userId);
                    _data.writeString(key);
                    _data.writeInt(dismissalSurface);
                    _data.writeInt(dismissalSentiment);
                    _data.writeTypedObject(nv, 0);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationVisibilityChanged(NotificationVisibility[] newlyVisibleKeys, NotificationVisibility[] noLongerVisibleKeys) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(newlyVisibleKeys, 0);
                    _data.writeTypedArray(noLongerVisibleKeys, 0);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationExpansionChanged(String key, boolean userAction, boolean expanded, int notificationLocation) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeBoolean(userAction);
                    _data.writeBoolean(expanded);
                    _data.writeInt(notificationLocation);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationDirectReplied(String key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationSmartSuggestionsAdded(String key, int smartReplyCount, int smartActionCount, boolean generatedByAsssistant, boolean editBeforeSending) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(smartReplyCount);
                    _data.writeInt(smartActionCount);
                    _data.writeBoolean(generatedByAsssistant);
                    _data.writeBoolean(editBeforeSending);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationSmartReplySent(String key, int replyIndex, CharSequence reply, int notificationLocation, boolean modifiedBeforeSending) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(replyIndex);
                    if (reply != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(reply, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(notificationLocation);
                    _data.writeBoolean(modifiedBeforeSending);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationSettingsViewed(String key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationBubbleChanged(String key, boolean isBubble, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeBoolean(isBubble);
                    _data.writeInt(flags);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBubbleMetadataFlagChanged(String key, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(flags);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void hideCurrentInputMethodForBubbles(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void grantInlineReplyUriPermission(String key, Uri uri, UserHandle user, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeTypedObject(uri, 0);
                    _data.writeTypedObject(user, 0);
                    _data.writeString(packageName);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void clearInlineReplyUriPermissions(String key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    this.mRemote.transact(33, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationFeedbackReceived(String key, Bundle feedback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeTypedObject(feedback, 0);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onGlobalActionsShown() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onGlobalActionsHidden() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void shutdown() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void reboot(boolean safeMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(safeMode);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean isFOTAAvailableForGlobalActions() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void restart() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void addTile(ComponentName tile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(tile, 0);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void remTile(ComponentName tile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(tile, 0);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void clickTile(ComponentName tile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(tile, 0);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void handleSystemKey(KeyEvent key) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(key, 0);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int getLastSystemKey() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showPinningEnterExitToast(boolean entering) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(entering);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showPinningEscapeToast() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver sysuiReceiver, int[] sensorIds, boolean credentialAllowed, boolean requireConfirmation, int userId, long operationId, String opPackageName, long requestId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(promptInfo, 0);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStrongInterface(sysuiReceiver);
                    try {
                        _data.writeIntArray(sensorIds);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeBoolean(credentialAllowed);
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeBoolean(requireConfirmation);
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(userId);
                    try {
                        _data.writeLong(operationId);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(opPackageName);
                        try {
                            _data.writeLong(requestId);
                        } catch (Throwable th7) {
                            th = th7;
                        }
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(48, _data, _reply, 0);
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th9) {
                        th = th9;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th10) {
                    th = th10;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBiometricAuthenticated(int modality) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(modality);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBiometricHelp(int modality, String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(modality);
                    _data.writeString(message);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBiometricError(int modality, int error, int vendorCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(modality);
                    _data.writeInt(error);
                    _data.writeInt(vendorCode);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void hideAuthenticationDialog(long requestId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(requestId);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setBiometicContextListener(IBiometricContextListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setUdfpsRefreshRateCallback(IUdfpsRefreshRateRequestCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showInattentiveSleepWarning() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void dismissInattentiveSleepWarning(boolean animated) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(animated);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void startTracing() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void stopTracing() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean isTracing() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void suppressAmbientDisplay(boolean suppress) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(suppress);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void requestTileServiceListeningState(ComponentName componentName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(componentName, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void requestAddTile(ComponentName componentName, CharSequence label, Icon icon, int userId, IAddTileResultCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(componentName, 0);
                    if (label != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(label, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeTypedObject(icon, 0);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void cancelRequestAddTile(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setNavBarMode(int navBarMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(navBarMode);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int getNavBarMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void registerSessionListener(int sessionFlags, ISessionListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionFlags);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void unregisterSessionListener(int sessionFlags, ISessionListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionFlags);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onSessionStarted(int sessionType, InstanceId instanceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionType);
                    _data.writeTypedObject(instanceId, 0);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onSessionEnded(int sessionType, InstanceId instanceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionType);
                    _data.writeTypedObject(instanceId, 0);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void updateMediaTapToTransferSenderDisplay(int displayState, MediaRoute2Info routeInfo, IUndoMediaTransferCallback undoCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayState);
                    _data.writeTypedObject(routeInfo, 0);
                    _data.writeStrongInterface(undoCallback);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void updateMediaTapToTransferReceiverDisplay(int displayState, MediaRoute2Info routeInfo, Icon appIcon, CharSequence appName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayState);
                    _data.writeTypedObject(routeInfo, 0);
                    _data.writeTypedObject(appIcon, 0);
                    if (appName != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(appName, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void registerNearbyMediaDevicesProvider(INearbyMediaDevicesProvider provider) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(provider);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void unregisterNearbyMediaDevicesProvider(INearbyMediaDevicesProvider provider) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(provider);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showRearDisplayDialog(int currentBaseState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(currentBaseState);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disableToType(int what, IBinder token, String pkg, int barType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeStrongBinder(token);
                    _data.writeString(pkg);
                    _data.writeInt(barType);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disableForUserToType(int what, IBinder token, String pkg, int userId, int barType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeStrongBinder(token);
                    _data.writeString(pkg);
                    _data.writeInt(userId);
                    _data.writeInt(barType);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2ToType(int what, IBinder token, String pkg, int barType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeStrongBinder(token);
                    _data.writeString(pkg);
                    _data.writeInt(barType);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2ForUserToType(int what, IBinder token, String pkg, int userId, int barType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(what);
                    _data.writeStrongBinder(token);
                    _data.writeString(pkg);
                    _data.writeInt(userId);
                    _data.writeInt(barType);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int[] getDisableFlagsToType(IBinder token, int userId, int barType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(userId);
                    _data.writeInt(barType);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public RegisterStatusBarResult registerStatusBarAsType(IStatusBar callbacks, int barType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callbacks);
                    _data.writeInt(barType);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                    RegisterStatusBarResult _result = (RegisterStatusBarResult) _reply.readTypedObject(RegisterStatusBarResult.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setPanelExpandStateToType(boolean state, int barType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(state);
                    _data.writeInt(barType);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean getPanelExpandStateToType(int barType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(barType);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean getQuickSettingPanelExpandStateToType(int barType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(barType);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandNotificationsPanelToType(int barType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(barType);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void collapsePanelsToType(int barType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(barType);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandSettingsPanelToType(String subPanel, int barType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(subPanel);
                    _data.writeInt(barType);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setNavigationBarShortcut(String requestClass, RemoteViews remoteViews, int position, int priority) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(requestClass);
                    _data.writeTypedObject(remoteViews, 0);
                    _data.writeInt(position);
                    _data.writeInt(priority);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void resetScheduleAutoHide() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setIndicatorBgColor(int color) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(color);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setBlueLightFilter(boolean on, int intensity) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(on);
                    _data.writeInt(intensity);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean isSysUiSafeModeEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void shutdownByBixby() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void rebootByBixby(boolean safeMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(safeMode);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void registerStatusBarForCarLife(IStatusBarCarLife callbacks) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callbacks);
                    this.mRemote.transact(94, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void sendKeyEventToDesktopTaskbar(KeyEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(95, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationDataUpdateFromPDC(List<String> importantContacts) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(importantContacts);
                    this.mRemote.transact(96, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 95;
        }
    }
}
