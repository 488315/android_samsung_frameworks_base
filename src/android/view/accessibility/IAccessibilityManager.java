package android.view.accessibility;

import android.Manifest;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.app.ActivityThread;
import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.graphics.Rect;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.view.IWindow;
import android.view.InputEvent;
import android.view.MagnificationSpec;
import android.view.SurfaceControl;
import android.view.accessibility.IAccessibilityInteractionConnection;
import android.view.accessibility.IAccessibilityManagerClient;
import android.view.accessibility.IMagnificationConnection;
import android.view.accessibility.IUserInitializationCompleteCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IAccessibilityManager extends IInterface {

    public static class Default implements IAccessibilityManager {
        @Override // android.view.accessibility.IAccessibilityManager
        public boolean OnStartGestureWakeup() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean OnStopGestureWakeup() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int addAccessibilityInteractionConnection(IWindow iWindow, IBinder iBinder, IAccessibilityInteractionConnection iAccessibilityInteractionConnection, String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public long addClient(IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void associateEmbeddedHierarchy(IBinder iBinder, IBinder iBinder2) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void attachAccessibilityOverlayToDisplay(int i, SurfaceControl surfaceControl) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int convertPixelToDpi(float f) throws RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void disassociateEmbeddedHierarchy(IBinder iBinder) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void enableShortcutsForTargets(boolean z, int i, List<String> list, int i2) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public Bundle getA11yFeatureToTileMap(int i) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public List<String> getAccessibilityShortcutTargets(int i) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int getAccessibilityWindowId(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int getFocusColor() throws RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public int getFocusStrokeWidth() throws RemoteException {
            return 0;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public ParceledListSlice<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int i) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public long getRecommendedTimeoutMillis() throws RemoteException {
            return 0L;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public String getScreenReaderName() throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public IBinder getWindowToken(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public WindowTransformationSpec getWindowTransformationSpec(int i) throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void injectInputEventToInputFilter(InputEvent inputEvent) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void interrupt(int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isAccessibilityServiceWarningRequired(AccessibilityServiceInfo accessibilityServiceInfo) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isAccessibilityTargetAllowed(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isActivatedMagnification() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isAudioDescriptionByDefaultEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isCameraFlashNotificationRunning() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isScreenReaderEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isSystemAudioCaptioningUiEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean isTwoFingerGestureRecognitionEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void notifyAccessibilityButtonClicked(int i, String str) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void notifyAccessibilityButtonLongClicked(int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void notifyAccessibilityButtonVisibilityChanged(boolean z) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void notifyQuickSettingsTilesChanged(int i, List<ComponentName> list) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void performAccessibilityDirectAccess(String str) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void performAccessibilityShortcut(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean registerProxyForDisplay(IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void registerSystemAction(RemoteAction remoteAction, int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void registerUiTestAutomationService(IBinder iBinder, IAccessibilityServiceClient iAccessibilityServiceClient, AccessibilityServiceInfo accessibilityServiceInfo, int i, int i2) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void registerUserInitializationCompleteCallback(IUserInitializationCompleteCallback iUserInitializationCompleteCallback) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void removeAccessibilityInteractionConnection(IWindow iWindow) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean removeClient(IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semCheckMdnieColorBlind(int[] iArr) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semDisableMdnieColorFilter() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semDisableWindowMagnification() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semDumpCallStack(String str) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semEnableMdnieColorFilter(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semEnableWindowMagnification(int i, int i2) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public Rect semGetWindowMagnificationBounds() throws RemoteException {
            return null;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public float semGetWindowMagnificationScale() throws RemoteException {
            return 0.0f;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semInjectInputEventToInputFilter(InputEvent inputEvent, int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semIsAccessibilityButtonShown() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semIsAccessibilityServiceEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semIsDarkScreenMode() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semIsWindowMagnificationEnabled() throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semLockNow() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semMoveWindowMagnification(float f, float f2) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semPerformAccessibilityButtonClick(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semRegisterAssistantMenu(IBinder iBinder) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semSetColorBlind(boolean z, float f) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semSetMdnieAccessibilityMode(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semSetTwoFingerGestureRecognitionEnabled(boolean z) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semStartFlashNotificationSequence(String str, int i, IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean semStopFlashNotificationSequence(String str) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semToggleDarkScreenMode() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semTurnOffAccessibilityService(int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semTurnOnAccessibilityService(int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void semUpdateAssitantMenu(Bundle bundle) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void sendAccessibilityEvent(AccessibilityEvent accessibilityEvent, int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean sendFingerprintGesture(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean sendRestrictedDialogIntent(String str, int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setAccessibilityWindowAttributes(int i, int i2, int i3, AccessibilityWindowAttributes accessibilityWindowAttributes) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setMagnificationConnection(IMagnificationConnection iMagnificationConnection) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setMagnificationDisactivate() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection iAccessibilityInteractionConnection) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setScreenReaderEnabled(boolean z) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setSystemAudioCaptioningEnabled(boolean z, int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setSystemAudioCaptioningUiEnabled(boolean z, int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void setTalkbackMode() throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean startFlashNotificationEvent(String str, int i, String str2) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean startFlashNotificationSequence(String str, int i, IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean stopFlashNotificationSequence(String str) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public boolean unregisterProxyForDisplay(int i) throws RemoteException {
            return false;
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void unregisterSystemAction(int i) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void unregisterUiTestAutomationService(IAccessibilityServiceClient iAccessibilityServiceClient) throws RemoteException {
        }

        @Override // android.view.accessibility.IAccessibilityManager
        public void unregisterUserInitializationCompleteCallback(IUserInitializationCompleteCallback iUserInitializationCompleteCallback) throws RemoteException {
        }
    }

    boolean OnStartGestureWakeup() throws RemoteException;

    boolean OnStopGestureWakeup() throws RemoteException;

    int addAccessibilityInteractionConnection(IWindow iWindow, IBinder iBinder, IAccessibilityInteractionConnection iAccessibilityInteractionConnection, String str, int i) throws RemoteException;

    long addClient(IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws RemoteException;

    void associateEmbeddedHierarchy(IBinder iBinder, IBinder iBinder2) throws RemoteException;

    void attachAccessibilityOverlayToDisplay(int i, SurfaceControl surfaceControl) throws RemoteException;

    int convertPixelToDpi(float f) throws RemoteException;

    void disassociateEmbeddedHierarchy(IBinder iBinder) throws RemoteException;

    void enableShortcutsForTargets(boolean z, int i, List<String> list, int i2) throws RemoteException;

    Bundle getA11yFeatureToTileMap(int i) throws RemoteException;

    List<String> getAccessibilityShortcutTargets(int i) throws RemoteException;

    int getAccessibilityWindowId(IBinder iBinder) throws RemoteException;

    List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, int i2) throws RemoteException;

    int getFocusColor() throws RemoteException;

    int getFocusStrokeWidth() throws RemoteException;

    ParceledListSlice<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int i) throws RemoteException;

    long getRecommendedTimeoutMillis() throws RemoteException;

    String getScreenReaderName() throws RemoteException;

    IBinder getWindowToken(int i, int i2) throws RemoteException;

    WindowTransformationSpec getWindowTransformationSpec(int i) throws RemoteException;

    void injectInputEventToInputFilter(InputEvent inputEvent) throws RemoteException;

    void interrupt(int i) throws RemoteException;

    boolean isAccessibilityServiceWarningRequired(AccessibilityServiceInfo accessibilityServiceInfo) throws RemoteException;

    boolean isAccessibilityTargetAllowed(String str, int i, int i2) throws RemoteException;

    boolean isActivatedMagnification() throws RemoteException;

    boolean isAudioDescriptionByDefaultEnabled() throws RemoteException;

    boolean isCameraFlashNotificationRunning() throws RemoteException;

    boolean isScreenReaderEnabled() throws RemoteException;

    boolean isSystemAudioCaptioningUiEnabled(int i) throws RemoteException;

    boolean isTwoFingerGestureRecognitionEnabled() throws RemoteException;

    void notifyAccessibilityButtonClicked(int i, String str) throws RemoteException;

    void notifyAccessibilityButtonLongClicked(int i) throws RemoteException;

    void notifyAccessibilityButtonVisibilityChanged(boolean z) throws RemoteException;

    void notifyQuickSettingsTilesChanged(int i, List<ComponentName> list) throws RemoteException;

    void performAccessibilityDirectAccess(String str) throws RemoteException;

    void performAccessibilityShortcut(int i, int i2, String str) throws RemoteException;

    boolean registerProxyForDisplay(IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws RemoteException;

    void registerSystemAction(RemoteAction remoteAction, int i) throws RemoteException;

    void registerUiTestAutomationService(IBinder iBinder, IAccessibilityServiceClient iAccessibilityServiceClient, AccessibilityServiceInfo accessibilityServiceInfo, int i, int i2) throws RemoteException;

    void registerUserInitializationCompleteCallback(IUserInitializationCompleteCallback iUserInitializationCompleteCallback) throws RemoteException;

    void removeAccessibilityInteractionConnection(IWindow iWindow) throws RemoteException;

    boolean removeClient(IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws RemoteException;

    boolean semCheckMdnieColorBlind(int[] iArr) throws RemoteException;

    boolean semDisableMdnieColorFilter() throws RemoteException;

    void semDisableWindowMagnification() throws RemoteException;

    void semDumpCallStack(String str) throws RemoteException;

    boolean semEnableMdnieColorFilter(int i, int i2) throws RemoteException;

    void semEnableWindowMagnification(int i, int i2) throws RemoteException;

    Rect semGetWindowMagnificationBounds() throws RemoteException;

    float semGetWindowMagnificationScale() throws RemoteException;

    void semInjectInputEventToInputFilter(InputEvent inputEvent, int i) throws RemoteException;

    boolean semIsAccessibilityButtonShown() throws RemoteException;

    boolean semIsAccessibilityServiceEnabled(int i) throws RemoteException;

    boolean semIsDarkScreenMode() throws RemoteException;

    boolean semIsWindowMagnificationEnabled() throws RemoteException;

    void semLockNow() throws RemoteException;

    void semMoveWindowMagnification(float f, float f2) throws RemoteException;

    void semPerformAccessibilityButtonClick(int i, int i2, String str) throws RemoteException;

    void semRegisterAssistantMenu(IBinder iBinder) throws RemoteException;

    boolean semSetColorBlind(boolean z, float f) throws RemoteException;

    boolean semSetMdnieAccessibilityMode(int i, boolean z) throws RemoteException;

    void semSetTwoFingerGestureRecognitionEnabled(boolean z) throws RemoteException;

    boolean semStartFlashNotificationSequence(String str, int i, IBinder iBinder) throws RemoteException;

    boolean semStopFlashNotificationSequence(String str) throws RemoteException;

    void semToggleDarkScreenMode() throws RemoteException;

    void semTurnOffAccessibilityService(int i) throws RemoteException;

    void semTurnOnAccessibilityService(int i) throws RemoteException;

    void semUpdateAssitantMenu(Bundle bundle) throws RemoteException;

    void sendAccessibilityEvent(AccessibilityEvent accessibilityEvent, int i) throws RemoteException;

    boolean sendFingerprintGesture(int i) throws RemoteException;

    boolean sendRestrictedDialogIntent(String str, int i, int i2) throws RemoteException;

    void setAccessibilityWindowAttributes(int i, int i2, int i3, AccessibilityWindowAttributes accessibilityWindowAttributes) throws RemoteException;

    void setMagnificationConnection(IMagnificationConnection iMagnificationConnection) throws RemoteException;

    void setMagnificationDisactivate() throws RemoteException;

    void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection iAccessibilityInteractionConnection) throws RemoteException;

    void setScreenReaderEnabled(boolean z) throws RemoteException;

    void setSystemAudioCaptioningEnabled(boolean z, int i) throws RemoteException;

    void setSystemAudioCaptioningUiEnabled(boolean z, int i) throws RemoteException;

    void setTalkbackMode() throws RemoteException;

    boolean startFlashNotificationEvent(String str, int i, String str2) throws RemoteException;

    boolean startFlashNotificationSequence(String str, int i, IBinder iBinder) throws RemoteException;

    boolean stopFlashNotificationSequence(String str) throws RemoteException;

    boolean unregisterProxyForDisplay(int i) throws RemoteException;

    void unregisterSystemAction(int i) throws RemoteException;

    void unregisterUiTestAutomationService(IAccessibilityServiceClient iAccessibilityServiceClient) throws RemoteException;

    void unregisterUserInitializationCompleteCallback(IUserInitializationCompleteCallback iUserInitializationCompleteCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IAccessibilityManager {
        public static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityManager";
        static final String[] PERMISSIONS_notifyQuickSettingsTilesChanged = {Manifest.permission.STATUS_BAR_SERVICE, Manifest.permission.MANAGE_ACCESSIBILITY};
        static final int TRANSACTION_OnStartGestureWakeup = 69;
        static final int TRANSACTION_OnStopGestureWakeup = 70;
        static final int TRANSACTION_addAccessibilityInteractionConnection = 7;
        static final int TRANSACTION_addClient = 3;
        static final int TRANSACTION_associateEmbeddedHierarchy = 24;
        static final int TRANSACTION_attachAccessibilityOverlayToDisplay = 43;
        static final int TRANSACTION_convertPixelToDpi = 66;
        static final int TRANSACTION_disassociateEmbeddedHierarchy = 25;
        static final int TRANSACTION_enableShortcutsForTargets = 45;
        static final int TRANSACTION_getA11yFeatureToTileMap = 46;
        static final int TRANSACTION_getAccessibilityShortcutTargets = 17;
        static final int TRANSACTION_getAccessibilityWindowId = 19;
        static final int TRANSACTION_getEnabledAccessibilityServiceList = 6;
        static final int TRANSACTION_getFocusColor = 27;
        static final int TRANSACTION_getFocusStrokeWidth = 26;
        static final int TRANSACTION_getInstalledAccessibilityServiceList = 5;
        static final int TRANSACTION_getRecommendedTimeoutMillis = 20;
        static final int TRANSACTION_getScreenReaderName = 64;
        static final int TRANSACTION_getWindowToken = 12;
        static final int TRANSACTION_getWindowTransformationSpec = 42;
        static final int TRANSACTION_injectInputEventToInputFilter = 35;
        static final int TRANSACTION_interrupt = 1;
        static final int TRANSACTION_isAccessibilityServiceWarningRequired = 41;
        static final int TRANSACTION_isAccessibilityTargetAllowed = 39;
        static final int TRANSACTION_isActivatedMagnification = 81;
        static final int TRANSACTION_isAudioDescriptionByDefaultEnabled = 28;
        static final int TRANSACTION_isCameraFlashNotificationRunning = 84;
        static final int TRANSACTION_isScreenReaderEnabled = 63;
        static final int TRANSACTION_isSystemAudioCaptioningUiEnabled = 30;
        static final int TRANSACTION_isTwoFingerGestureRecognitionEnabled = 62;
        static final int TRANSACTION_notifyAccessibilityButtonClicked = 13;
        static final int TRANSACTION_notifyAccessibilityButtonLongClicked = 14;
        static final int TRANSACTION_notifyAccessibilityButtonVisibilityChanged = 15;
        static final int TRANSACTION_notifyQuickSettingsTilesChanged = 44;
        static final int TRANSACTION_performAccessibilityDirectAccess = 74;
        static final int TRANSACTION_performAccessibilityShortcut = 16;
        static final int TRANSACTION_registerProxyForDisplay = 33;
        static final int TRANSACTION_registerSystemAction = 21;
        static final int TRANSACTION_registerUiTestAutomationService = 10;
        static final int TRANSACTION_registerUserInitializationCompleteCallback = 47;
        static final int TRANSACTION_removeAccessibilityInteractionConnection = 8;
        static final int TRANSACTION_removeClient = 4;
        static final int TRANSACTION_semCheckMdnieColorBlind = 53;
        static final int TRANSACTION_semDisableMdnieColorFilter = 56;
        static final int TRANSACTION_semDisableWindowMagnification = 78;
        static final int TRANSACTION_semDumpCallStack = 73;
        static final int TRANSACTION_semEnableMdnieColorFilter = 55;
        static final int TRANSACTION_semEnableWindowMagnification = 77;
        static final int TRANSACTION_semGetWindowMagnificationBounds = 75;
        static final int TRANSACTION_semGetWindowMagnificationScale = 76;
        static final int TRANSACTION_semInjectInputEventToInputFilter = 83;
        static final int TRANSACTION_semIsAccessibilityButtonShown = 71;
        static final int TRANSACTION_semIsAccessibilityServiceEnabled = 49;
        static final int TRANSACTION_semIsDarkScreenMode = 57;
        static final int TRANSACTION_semIsWindowMagnificationEnabled = 80;
        static final int TRANSACTION_semLockNow = 68;
        static final int TRANSACTION_semMoveWindowMagnification = 79;
        static final int TRANSACTION_semPerformAccessibilityButtonClick = 82;
        static final int TRANSACTION_semRegisterAssistantMenu = 60;
        static final int TRANSACTION_semSetColorBlind = 52;
        static final int TRANSACTION_semSetMdnieAccessibilityMode = 54;
        static final int TRANSACTION_semSetTwoFingerGestureRecognitionEnabled = 61;
        static final int TRANSACTION_semStartFlashNotificationSequence = 85;
        static final int TRANSACTION_semStopFlashNotificationSequence = 86;
        static final int TRANSACTION_semToggleDarkScreenMode = 58;
        static final int TRANSACTION_semTurnOffAccessibilityService = 50;
        static final int TRANSACTION_semTurnOnAccessibilityService = 51;
        static final int TRANSACTION_semUpdateAssitantMenu = 59;
        static final int TRANSACTION_sendAccessibilityEvent = 2;
        static final int TRANSACTION_sendFingerprintGesture = 18;
        static final int TRANSACTION_sendRestrictedDialogIntent = 40;
        static final int TRANSACTION_setAccessibilityWindowAttributes = 32;
        static final int TRANSACTION_setMagnificationConnection = 23;
        static final int TRANSACTION_setMagnificationDisactivate = 72;
        static final int TRANSACTION_setPictureInPictureActionReplacingConnection = 9;
        static final int TRANSACTION_setScreenReaderEnabled = 65;
        static final int TRANSACTION_setSystemAudioCaptioningEnabled = 29;
        static final int TRANSACTION_setSystemAudioCaptioningUiEnabled = 31;
        static final int TRANSACTION_setTalkbackMode = 67;
        static final int TRANSACTION_startFlashNotificationEvent = 38;
        static final int TRANSACTION_startFlashNotificationSequence = 36;
        static final int TRANSACTION_stopFlashNotificationSequence = 37;
        static final int TRANSACTION_unregisterProxyForDisplay = 34;
        static final int TRANSACTION_unregisterSystemAction = 22;
        static final int TRANSACTION_unregisterUiTestAutomationService = 11;
        static final int TRANSACTION_unregisterUserInitializationCompleteCallback = 48;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 85;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IAccessibilityManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAccessibilityManager)) {
                return (IAccessibilityManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "interrupt";
                case 2:
                    return "sendAccessibilityEvent";
                case 3:
                    return "addClient";
                case 4:
                    return "removeClient";
                case 5:
                    return "getInstalledAccessibilityServiceList";
                case 6:
                    return "getEnabledAccessibilityServiceList";
                case 7:
                    return "addAccessibilityInteractionConnection";
                case 8:
                    return "removeAccessibilityInteractionConnection";
                case 9:
                    return "setPictureInPictureActionReplacingConnection";
                case 10:
                    return "registerUiTestAutomationService";
                case 11:
                    return "unregisterUiTestAutomationService";
                case 12:
                    return "getWindowToken";
                case 13:
                    return "notifyAccessibilityButtonClicked";
                case 14:
                    return "notifyAccessibilityButtonLongClicked";
                case 15:
                    return "notifyAccessibilityButtonVisibilityChanged";
                case 16:
                    return "performAccessibilityShortcut";
                case 17:
                    return "getAccessibilityShortcutTargets";
                case 18:
                    return "sendFingerprintGesture";
                case 19:
                    return "getAccessibilityWindowId";
                case 20:
                    return "getRecommendedTimeoutMillis";
                case 21:
                    return "registerSystemAction";
                case 22:
                    return "unregisterSystemAction";
                case 23:
                    return "setMagnificationConnection";
                case 24:
                    return "associateEmbeddedHierarchy";
                case 25:
                    return "disassociateEmbeddedHierarchy";
                case 26:
                    return "getFocusStrokeWidth";
                case 27:
                    return "getFocusColor";
                case 28:
                    return "isAudioDescriptionByDefaultEnabled";
                case 29:
                    return "setSystemAudioCaptioningEnabled";
                case 30:
                    return "isSystemAudioCaptioningUiEnabled";
                case 31:
                    return "setSystemAudioCaptioningUiEnabled";
                case 32:
                    return "setAccessibilityWindowAttributes";
                case 33:
                    return "registerProxyForDisplay";
                case 34:
                    return "unregisterProxyForDisplay";
                case 35:
                    return "injectInputEventToInputFilter";
                case 36:
                    return "startFlashNotificationSequence";
                case 37:
                    return "stopFlashNotificationSequence";
                case 38:
                    return "startFlashNotificationEvent";
                case 39:
                    return "isAccessibilityTargetAllowed";
                case 40:
                    return "sendRestrictedDialogIntent";
                case 41:
                    return "isAccessibilityServiceWarningRequired";
                case 42:
                    return "getWindowTransformationSpec";
                case 43:
                    return "attachAccessibilityOverlayToDisplay";
                case 44:
                    return "notifyQuickSettingsTilesChanged";
                case 45:
                    return "enableShortcutsForTargets";
                case 46:
                    return "getA11yFeatureToTileMap";
                case 47:
                    return "registerUserInitializationCompleteCallback";
                case 48:
                    return "unregisterUserInitializationCompleteCallback";
                case 49:
                    return "semIsAccessibilityServiceEnabled";
                case 50:
                    return "semTurnOffAccessibilityService";
                case 51:
                    return "semTurnOnAccessibilityService";
                case 52:
                    return "semSetColorBlind";
                case 53:
                    return "semCheckMdnieColorBlind";
                case 54:
                    return "semSetMdnieAccessibilityMode";
                case 55:
                    return "semEnableMdnieColorFilter";
                case 56:
                    return "semDisableMdnieColorFilter";
                case 57:
                    return "semIsDarkScreenMode";
                case 58:
                    return "semToggleDarkScreenMode";
                case 59:
                    return "semUpdateAssitantMenu";
                case 60:
                    return "semRegisterAssistantMenu";
                case 61:
                    return "semSetTwoFingerGestureRecognitionEnabled";
                case 62:
                    return "isTwoFingerGestureRecognitionEnabled";
                case 63:
                    return "isScreenReaderEnabled";
                case 64:
                    return "getScreenReaderName";
                case 65:
                    return "setScreenReaderEnabled";
                case 66:
                    return "convertPixelToDpi";
                case 67:
                    return "setTalkbackMode";
                case 68:
                    return "semLockNow";
                case 69:
                    return "OnStartGestureWakeup";
                case 70:
                    return "OnStopGestureWakeup";
                case 71:
                    return "semIsAccessibilityButtonShown";
                case 72:
                    return "setMagnificationDisactivate";
                case 73:
                    return "semDumpCallStack";
                case 74:
                    return "performAccessibilityDirectAccess";
                case 75:
                    return "semGetWindowMagnificationBounds";
                case 76:
                    return "semGetWindowMagnificationScale";
                case 77:
                    return "semEnableWindowMagnification";
                case 78:
                    return "semDisableWindowMagnification";
                case 79:
                    return "semMoveWindowMagnification";
                case 80:
                    return "semIsWindowMagnificationEnabled";
                case 81:
                    return "isActivatedMagnification";
                case 82:
                    return "semPerformAccessibilityButtonClick";
                case 83:
                    return "semInjectInputEventToInputFilter";
                case 84:
                    return "isCameraFlashNotificationRunning";
                case 85:
                    return "semStartFlashNotificationSequence";
                case 86:
                    return "semStopFlashNotificationSequence";
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    interrupt(i3);
                    return true;
                case 2:
                    AccessibilityEvent accessibilityEvent = (AccessibilityEvent) parcel.readTypedObject(AccessibilityEvent.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAccessibilityEvent(accessibilityEvent, i4);
                    return true;
                case 3:
                    IAccessibilityManagerClient iAccessibilityManagerClientAsInterface = IAccessibilityManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long jAddClient = addClient(iAccessibilityManagerClientAsInterface, i5);
                    parcel2.writeNoException();
                    parcel2.writeLong(jAddClient);
                    return true;
                case 4:
                    IAccessibilityManagerClient iAccessibilityManagerClientAsInterface2 = IAccessibilityManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveClient = removeClient(iAccessibilityManagerClientAsInterface2, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveClient);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<AccessibilityServiceInfo> installedAccessibilityServiceList = getInstalledAccessibilityServiceList(i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedAccessibilityServiceList, 1);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AccessibilityServiceInfo> enabledAccessibilityServiceList = getEnabledAccessibilityServiceList(i8, i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledAccessibilityServiceList, 1);
                    return true;
                case 7:
                    IWindow iWindowAsInterface = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder = parcel.readStrongBinder();
                    IAccessibilityInteractionConnection iAccessibilityInteractionConnectionAsInterface = IAccessibilityInteractionConnection.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iAddAccessibilityInteractionConnection = addAccessibilityInteractionConnection(iWindowAsInterface, strongBinder, iAccessibilityInteractionConnectionAsInterface, string, i10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAddAccessibilityInteractionConnection);
                    return true;
                case 8:
                    IWindow iWindowAsInterface2 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeAccessibilityInteractionConnection(iWindowAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IAccessibilityInteractionConnection iAccessibilityInteractionConnectionAsInterface2 = IAccessibilityInteractionConnection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setPictureInPictureActionReplacingConnection(iAccessibilityInteractionConnectionAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    IAccessibilityServiceClient iAccessibilityServiceClientAsInterface = IAccessibilityServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    AccessibilityServiceInfo accessibilityServiceInfo = (AccessibilityServiceInfo) parcel.readTypedObject(AccessibilityServiceInfo.CREATOR);
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerUiTestAutomationService(strongBinder2, iAccessibilityServiceClientAsInterface, accessibilityServiceInfo, i11, i12);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IAccessibilityServiceClient iAccessibilityServiceClientAsInterface2 = IAccessibilityServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUiTestAutomationService(iAccessibilityServiceClientAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IBinder windowToken = getWindowToken(i13, i14);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(windowToken);
                    return true;
                case 13:
                    int i15 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyAccessibilityButtonClicked(i15, string2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAccessibilityButtonLongClicked(i16);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyAccessibilityButtonVisibilityChanged(z);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    performAccessibilityShortcut(i17, i18, string3);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> accessibilityShortcutTargets = getAccessibilityShortcutTargets(i19);
                    parcel2.writeNoException();
                    parcel2.writeStringList(accessibilityShortcutTargets);
                    return true;
                case 18:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSendFingerprintGesture = sendFingerprintGesture(i20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendFingerprintGesture);
                    return true;
                case 19:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int accessibilityWindowId = getAccessibilityWindowId(strongBinder3);
                    parcel2.writeNoException();
                    parcel2.writeInt(accessibilityWindowId);
                    return true;
                case 20:
                    long recommendedTimeoutMillis = getRecommendedTimeoutMillis();
                    parcel2.writeNoException();
                    parcel2.writeLong(recommendedTimeoutMillis);
                    return true;
                case 21:
                    RemoteAction remoteAction = (RemoteAction) parcel.readTypedObject(RemoteAction.CREATOR);
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerSystemAction(remoteAction, i21);
                    return true;
                case 22:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterSystemAction(i22);
                    return true;
                case 23:
                    IMagnificationConnection iMagnificationConnectionAsInterface = IMagnificationConnection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setMagnificationConnection(iMagnificationConnectionAsInterface);
                    return true;
                case 24:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    associateEmbeddedHierarchy(strongBinder4, strongBinder5);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    disassociateEmbeddedHierarchy(strongBinder6);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int focusStrokeWidth = getFocusStrokeWidth();
                    parcel2.writeNoException();
                    parcel2.writeInt(focusStrokeWidth);
                    return true;
                case 27:
                    int focusColor = getFocusColor();
                    parcel2.writeNoException();
                    parcel2.writeInt(focusColor);
                    return true;
                case 28:
                    boolean zIsAudioDescriptionByDefaultEnabled = isAudioDescriptionByDefaultEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAudioDescriptionByDefaultEnabled);
                    return true;
                case 29:
                    boolean z2 = parcel.readBoolean();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSystemAudioCaptioningEnabled(z2, i23);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSystemAudioCaptioningUiEnabled = isSystemAudioCaptioningUiEnabled(i24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSystemAudioCaptioningUiEnabled);
                    return true;
                case 31:
                    boolean z3 = parcel.readBoolean();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSystemAudioCaptioningUiEnabled(z3, i25);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    AccessibilityWindowAttributes accessibilityWindowAttributes = (AccessibilityWindowAttributes) parcel.readTypedObject(AccessibilityWindowAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAccessibilityWindowAttributes(i26, i27, i28, accessibilityWindowAttributes);
                    return true;
                case 33:
                    IAccessibilityServiceClient iAccessibilityServiceClientAsInterface3 = IAccessibilityServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRegisterProxyForDisplay = registerProxyForDisplay(iAccessibilityServiceClientAsInterface3, i29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterProxyForDisplay);
                    return true;
                case 34:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterProxyForDisplay = unregisterProxyForDisplay(i30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterProxyForDisplay);
                    return true;
                case 35:
                    InputEvent inputEvent = (InputEvent) parcel.readTypedObject(InputEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectInputEventToInputFilter(inputEvent);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    String string4 = parcel.readString();
                    int i31 = parcel.readInt();
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zStartFlashNotificationSequence = startFlashNotificationSequence(string4, i31, strongBinder7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartFlashNotificationSequence);
                    return true;
                case 37:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zStopFlashNotificationSequence = stopFlashNotificationSequence(string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStopFlashNotificationSequence);
                    return true;
                case 38:
                    String string6 = parcel.readString();
                    int i32 = parcel.readInt();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zStartFlashNotificationEvent = startFlashNotificationEvent(string6, i32, string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartFlashNotificationEvent);
                    return true;
                case 39:
                    String string8 = parcel.readString();
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAccessibilityTargetAllowed = isAccessibilityTargetAllowed(string8, i33, i34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAccessibilityTargetAllowed);
                    return true;
                case 40:
                    String string9 = parcel.readString();
                    int i35 = parcel.readInt();
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSendRestrictedDialogIntent = sendRestrictedDialogIntent(string9, i35, i36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendRestrictedDialogIntent);
                    return true;
                case 41:
                    AccessibilityServiceInfo accessibilityServiceInfo2 = (AccessibilityServiceInfo) parcel.readTypedObject(AccessibilityServiceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsAccessibilityServiceWarningRequired = isAccessibilityServiceWarningRequired(accessibilityServiceInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAccessibilityServiceWarningRequired);
                    return true;
                case 42:
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WindowTransformationSpec windowTransformationSpec = getWindowTransformationSpec(i37);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windowTransformationSpec, 1);
                    return true;
                case 43:
                    int i38 = parcel.readInt();
                    SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    attachAccessibilityOverlayToDisplay(i38, surfaceControl);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int i39 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyQuickSettingsTilesChanged(i39, arrayListCreateTypedArrayList);
                    return true;
                case 45:
                    boolean z4 = parcel.readBoolean();
                    int i40 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableShortcutsForTargets(z4, i40, arrayListCreateStringArrayList, i41);
                    return true;
                case 46:
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle a11yFeatureToTileMap = getA11yFeatureToTileMap(i42);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(a11yFeatureToTileMap, 1);
                    return true;
                case 47:
                    IUserInitializationCompleteCallback iUserInitializationCompleteCallbackAsInterface = IUserInitializationCompleteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerUserInitializationCompleteCallback(iUserInitializationCompleteCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    IUserInitializationCompleteCallback iUserInitializationCompleteCallbackAsInterface2 = IUserInitializationCompleteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUserInitializationCompleteCallback(iUserInitializationCompleteCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSemIsAccessibilityServiceEnabled = semIsAccessibilityServiceEnabled(i43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsAccessibilityServiceEnabled);
                    return true;
                case 50:
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semTurnOffAccessibilityService(i44);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semTurnOnAccessibilityService(i45);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    boolean z5 = parcel.readBoolean();
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    boolean zSemSetColorBlind = semSetColorBlind(z5, f);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemSetColorBlind);
                    return true;
                case 53:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    boolean zSemCheckMdnieColorBlind = semCheckMdnieColorBlind(iArrCreateIntArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemCheckMdnieColorBlind);
                    return true;
                case 54:
                    int i46 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zSemSetMdnieAccessibilityMode = semSetMdnieAccessibilityMode(i46, z6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemSetMdnieAccessibilityMode);
                    return true;
                case 55:
                    int i47 = parcel.readInt();
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSemEnableMdnieColorFilter = semEnableMdnieColorFilter(i47, i48);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemEnableMdnieColorFilter);
                    return true;
                case 56:
                    boolean zSemDisableMdnieColorFilter = semDisableMdnieColorFilter();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemDisableMdnieColorFilter);
                    return true;
                case 57:
                    boolean zSemIsDarkScreenMode = semIsDarkScreenMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsDarkScreenMode);
                    return true;
                case 58:
                    semToggleDarkScreenMode();
                    parcel2.writeNoException();
                    return true;
                case 59:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    semUpdateAssitantMenu(bundle);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    semRegisterAssistantMenu(strongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semSetTwoFingerGestureRecognitionEnabled(z7);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    boolean zIsTwoFingerGestureRecognitionEnabled = isTwoFingerGestureRecognitionEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTwoFingerGestureRecognitionEnabled);
                    return true;
                case 63:
                    boolean zIsScreenReaderEnabled = isScreenReaderEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsScreenReaderEnabled);
                    return true;
                case 64:
                    String screenReaderName = getScreenReaderName();
                    parcel2.writeNoException();
                    parcel2.writeString(screenReaderName);
                    return true;
                case 65:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setScreenReaderEnabled(z8);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    float f2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    int iConvertPixelToDpi = convertPixelToDpi(f2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iConvertPixelToDpi);
                    return true;
                case 67:
                    setTalkbackMode();
                    parcel2.writeNoException();
                    return true;
                case 68:
                    semLockNow();
                    parcel2.writeNoException();
                    return true;
                case 69:
                    boolean zOnStartGestureWakeup = OnStartGestureWakeup();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zOnStartGestureWakeup);
                    return true;
                case 70:
                    boolean zOnStopGestureWakeup = OnStopGestureWakeup();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zOnStopGestureWakeup);
                    return true;
                case 71:
                    boolean zSemIsAccessibilityButtonShown = semIsAccessibilityButtonShown();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsAccessibilityButtonShown);
                    return true;
                case 72:
                    setMagnificationDisactivate();
                    parcel2.writeNoException();
                    return true;
                case 73:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semDumpCallStack(string10);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    performAccessibilityDirectAccess(string11);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    Rect rectSemGetWindowMagnificationBounds = semGetWindowMagnificationBounds();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rectSemGetWindowMagnificationBounds, 1);
                    return true;
                case 76:
                    float fSemGetWindowMagnificationScale = semGetWindowMagnificationScale();
                    parcel2.writeNoException();
                    parcel2.writeFloat(fSemGetWindowMagnificationScale);
                    return true;
                case 77:
                    int i49 = parcel.readInt();
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semEnableWindowMagnification(i49, i50);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    semDisableWindowMagnification();
                    parcel2.writeNoException();
                    return true;
                case 79:
                    float f3 = parcel.readFloat();
                    float f4 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    semMoveWindowMagnification(f3, f4);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    boolean zSemIsWindowMagnificationEnabled = semIsWindowMagnificationEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsWindowMagnificationEnabled);
                    return true;
                case 81:
                    boolean zIsActivatedMagnification = isActivatedMagnification();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsActivatedMagnification);
                    return true;
                case 82:
                    int i51 = parcel.readInt();
                    int i52 = parcel.readInt();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semPerformAccessibilityButtonClick(i51, i52, string12);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    InputEvent inputEvent2 = (InputEvent) parcel.readTypedObject(InputEvent.CREATOR);
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semInjectInputEventToInputFilter(inputEvent2, i53);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    boolean zIsCameraFlashNotificationRunning = isCameraFlashNotificationRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCameraFlashNotificationRunning);
                    return true;
                case 85:
                    String string13 = parcel.readString();
                    int i54 = parcel.readInt();
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean zSemStartFlashNotificationSequence = semStartFlashNotificationSequence(string13, i54, strongBinder9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemStartFlashNotificationSequence);
                    return true;
                case 86:
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zSemStopFlashNotificationSequence = semStopFlashNotificationSequence(string14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemStopFlashNotificationSequence);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAccessibilityManager {
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

            @Override // android.view.accessibility.IAccessibilityManager
            public void interrupt(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void sendAccessibilityEvent(AccessibilityEvent accessibilityEvent, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(accessibilityEvent, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public long addClient(IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccessibilityManagerClient);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean removeClient(IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccessibilityManagerClient);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public ParceledListSlice<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AccessibilityServiceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int addAccessibilityInteractionConnection(IWindow iWindow, IBinder iBinder, IAccessibilityInteractionConnection iAccessibilityInteractionConnection, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnection);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void removeAccessibilityInteractionConnection(IWindow iWindow) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection iAccessibilityInteractionConnection) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnection);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void registerUiTestAutomationService(IBinder iBinder, IAccessibilityServiceClient iAccessibilityServiceClient, AccessibilityServiceInfo accessibilityServiceInfo, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iAccessibilityServiceClient);
                    parcelObtain.writeTypedObject(accessibilityServiceInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void unregisterUiTestAutomationService(IAccessibilityServiceClient iAccessibilityServiceClient) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccessibilityServiceClient);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public IBinder getWindowToken(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyAccessibilityButtonClicked(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyAccessibilityButtonLongClicked(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyAccessibilityButtonVisibilityChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void performAccessibilityShortcut(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public List<String> getAccessibilityShortcutTargets(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean sendFingerprintGesture(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int getAccessibilityWindowId(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public long getRecommendedTimeoutMillis() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void registerSystemAction(RemoteAction remoteAction, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(remoteAction, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void unregisterSystemAction(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setMagnificationConnection(IMagnificationConnection iMagnificationConnection) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMagnificationConnection);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void associateEmbeddedHierarchy(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void disassociateEmbeddedHierarchy(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int getFocusStrokeWidth() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int getFocusColor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isAudioDescriptionByDefaultEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setSystemAudioCaptioningEnabled(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isSystemAudioCaptioningUiEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setSystemAudioCaptioningUiEnabled(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setAccessibilityWindowAttributes(int i, int i2, int i3, AccessibilityWindowAttributes accessibilityWindowAttributes) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(accessibilityWindowAttributes, 0);
                    this.mRemote.transact(32, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean registerProxyForDisplay(IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAccessibilityServiceClient);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean unregisterProxyForDisplay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void injectInputEventToInputFilter(InputEvent inputEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputEvent, 0);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean startFlashNotificationSequence(String str, int i, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean stopFlashNotificationSequence(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean startFlashNotificationEvent(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isAccessibilityTargetAllowed(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean sendRestrictedDialogIntent(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isAccessibilityServiceWarningRequired(AccessibilityServiceInfo accessibilityServiceInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(accessibilityServiceInfo, 0);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public WindowTransformationSpec getWindowTransformationSpec(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (WindowTransformationSpec) parcelObtain2.readTypedObject(WindowTransformationSpec.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void attachAccessibilityOverlayToDisplay(int i, SurfaceControl surfaceControl) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyQuickSettingsTilesChanged(int i, List<ComponentName> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(44, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void enableShortcutsForTargets(boolean z, int i, List<String> list, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(45, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public Bundle getA11yFeatureToTileMap(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void registerUserInitializationCompleteCallback(IUserInitializationCompleteCallback iUserInitializationCompleteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUserInitializationCompleteCallback);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void unregisterUserInitializationCompleteCallback(IUserInitializationCompleteCallback iUserInitializationCompleteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUserInitializationCompleteCallback);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semIsAccessibilityServiceEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semTurnOffAccessibilityService(int i) throws RemoteException {
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

            @Override // android.view.accessibility.IAccessibilityManager
            public void semTurnOnAccessibilityService(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semSetColorBlind(boolean z, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semCheckMdnieColorBlind(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semSetMdnieAccessibilityMode(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semEnableMdnieColorFilter(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semDisableMdnieColorFilter() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semIsDarkScreenMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semToggleDarkScreenMode() throws RemoteException {
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

            @Override // android.view.accessibility.IAccessibilityManager
            public void semUpdateAssitantMenu(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semRegisterAssistantMenu(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semSetTwoFingerGestureRecognitionEnabled(boolean z) throws RemoteException {
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

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isTwoFingerGestureRecognitionEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isScreenReaderEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public String getScreenReaderName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setScreenReaderEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int convertPixelToDpi(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setTalkbackMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semLockNow() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean OnStartGestureWakeup() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean OnStopGestureWakeup() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semIsAccessibilityButtonShown() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setMagnificationDisactivate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semDumpCallStack(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void performAccessibilityDirectAccess(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public Rect semGetWindowMagnificationBounds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Rect) parcelObtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public float semGetWindowMagnificationScale() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semEnableWindowMagnification(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semDisableWindowMagnification() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semMoveWindowMagnification(float f, float f2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeFloat(f2);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semIsWindowMagnificationEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isActivatedMagnification() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semPerformAccessibilityButtonClick(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semInjectInputEventToInputFilter(InputEvent inputEvent, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputEvent, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isCameraFlashNotificationRunning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semStartFlashNotificationSequence(String str, int i, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semStopFlashNotificationSequence(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void setPictureInPictureActionReplacingConnection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MODIFY_ACCESSIBILITY_DATA, getCallingPid(), getCallingUid());
        }

        protected void registerUiTestAutomationService_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.RETRIEVE_WINDOW_CONTENT, getCallingPid(), getCallingUid());
        }

        protected void getWindowToken_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.RETRIEVE_WINDOW_CONTENT, getCallingPid(), getCallingUid());
        }

        protected void notifyAccessibilityButtonClicked_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STATUS_BAR_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void notifyAccessibilityButtonLongClicked_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STATUS_BAR_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void notifyAccessibilityButtonVisibilityChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STATUS_BAR_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void performAccessibilityShortcut_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void getAccessibilityShortcutTargets_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void registerSystemAction_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void unregisterSystemAction_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void setMagnificationConnection_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.STATUS_BAR_SERVICE, getCallingPid(), getCallingUid());
        }

        protected void setSystemAudioCaptioningEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SET_SYSTEM_AUDIO_CAPTION, getCallingPid(), getCallingUid());
        }

        protected void setSystemAudioCaptioningUiEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.SET_SYSTEM_AUDIO_CAPTION, getCallingPid(), getCallingUid());
        }

        protected void registerProxyForDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void unregisterProxyForDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void injectInputEventToInputFilter_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission("android.permission.INJECT_EVENTS", getCallingPid(), getCallingUid());
        }

        protected void startFlashNotificationSequence_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void stopFlashNotificationSequence_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void startFlashNotificationEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void isAccessibilityServiceWarningRequired_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void attachAccessibilityOverlayToDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.INTERNAL_SYSTEM_WINDOW, getCallingPid(), getCallingUid());
        }

        protected void notifyQuickSettingsTilesChanged_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_notifyQuickSettingsTilesChanged, getCallingPid(), getCallingUid());
        }

        protected void enableShortcutsForTargets_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void getA11yFeatureToTileMap_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semEnableMdnieColorFilter_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_DISPLAY_COLOR, getCallingPid(), getCallingUid());
        }

        protected void semDisableMdnieColorFilter_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_DISPLAY_COLOR, getCallingPid(), getCallingUid());
        }

        protected void semIsDarkScreenMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_DISPLAY_COLOR, getCallingPid(), getCallingUid());
        }

        protected void semToggleDarkScreenMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CHANGE_DISPLAY_COLOR, getCallingPid(), getCallingUid());
        }

        protected void setScreenReaderEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setMagnificationDisactivate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void performAccessibilityDirectAccess_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semGetWindowMagnificationBounds_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semGetWindowMagnificationScale_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semEnableWindowMagnification_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semDisableWindowMagnification_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semMoveWindowMagnification_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semIsWindowMagnificationEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void isActivatedMagnification_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }

        protected void semInjectInputEventToInputFilter_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission("android.permission.INJECT_EVENTS", getCallingPid(), getCallingUid());
        }

        protected void semStartFlashNotificationSequence_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.POST_NOTIFICATIONS, getCallingPid(), getCallingUid());
        }

        protected void semStopFlashNotificationSequence_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.POST_NOTIFICATIONS, getCallingPid(), getCallingUid());
        }
    }

    public static class WindowTransformationSpec implements Parcelable {
        public static final Parcelable.Creator<WindowTransformationSpec> CREATOR = new Parcelable.Creator<WindowTransformationSpec>() { // from class: android.view.accessibility.IAccessibilityManager.WindowTransformationSpec.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public WindowTransformationSpec createFromParcel(Parcel parcel) {
                WindowTransformationSpec windowTransformationSpec = new WindowTransformationSpec();
                windowTransformationSpec.readFromParcel(parcel);
                return windowTransformationSpec;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public WindowTransformationSpec[] newArray(int i) {
                return new WindowTransformationSpec[i];
            }
        };
        public MagnificationSpec magnificationSpec;
        public float[] transformationMatrix;

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeFloatArray(this.transformationMatrix);
            parcel.writeTypedObject(this.magnificationSpec, i);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.transformationMatrix = parcel.createFloatArray();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.magnificationSpec = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                        if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.magnificationSpec);
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }
}
