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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAccessibilityManager)) {
                return (IAccessibilityManager) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    interrupt(readInt);
                    return true;
                case 2:
                    AccessibilityEvent accessibilityEvent = (AccessibilityEvent) parcel.readTypedObject(AccessibilityEvent.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAccessibilityEvent(accessibilityEvent, readInt2);
                    return true;
                case 3:
                    IAccessibilityManagerClient asInterface = IAccessibilityManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long addClient = addClient(asInterface, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeLong(addClient);
                    return true;
                case 4:
                    IAccessibilityManagerClient asInterface2 = IAccessibilityManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeClient = removeClient(asInterface2, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeClient);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice<AccessibilityServiceInfo> installedAccessibilityServiceList = getInstalledAccessibilityServiceList(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedAccessibilityServiceList, 1);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AccessibilityServiceInfo> enabledAccessibilityServiceList = getEnabledAccessibilityServiceList(readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledAccessibilityServiceList, 1);
                    return true;
                case 7:
                    IWindow asInterface3 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    IAccessibilityInteractionConnection asInterface4 = IAccessibilityInteractionConnection.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int addAccessibilityInteractionConnection = addAccessibilityInteractionConnection(asInterface3, readStrongBinder, asInterface4, readString, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(addAccessibilityInteractionConnection);
                    return true;
                case 8:
                    IWindow asInterface5 = IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeAccessibilityInteractionConnection(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IAccessibilityInteractionConnection asInterface6 = IAccessibilityInteractionConnection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setPictureInPictureActionReplacingConnection(asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    IAccessibilityServiceClient asInterface7 = IAccessibilityServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    AccessibilityServiceInfo accessibilityServiceInfo = (AccessibilityServiceInfo) parcel.readTypedObject(AccessibilityServiceInfo.CREATOR);
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerUiTestAutomationService(readStrongBinder2, asInterface7, accessibilityServiceInfo, readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IAccessibilityServiceClient asInterface8 = IAccessibilityServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUiTestAutomationService(asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IBinder windowToken = getWindowToken(readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(windowToken);
                    return true;
                case 13:
                    int readInt13 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyAccessibilityButtonClicked(readInt13, readString2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAccessibilityButtonLongClicked(readInt14);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyAccessibilityButtonVisibilityChanged(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    performAccessibilityShortcut(readInt15, readInt16, readString3);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> accessibilityShortcutTargets = getAccessibilityShortcutTargets(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeStringList(accessibilityShortcutTargets);
                    return true;
                case 18:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean sendFingerprintGesture = sendFingerprintGesture(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendFingerprintGesture);
                    return true;
                case 19:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int accessibilityWindowId = getAccessibilityWindowId(readStrongBinder3);
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
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerSystemAction(remoteAction, readInt19);
                    return true;
                case 22:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterSystemAction(readInt20);
                    return true;
                case 23:
                    IMagnificationConnection asInterface9 = IMagnificationConnection.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setMagnificationConnection(asInterface9);
                    return true;
                case 24:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    associateEmbeddedHierarchy(readStrongBinder4, readStrongBinder5);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    disassociateEmbeddedHierarchy(readStrongBinder6);
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
                    boolean isAudioDescriptionByDefaultEnabled = isAudioDescriptionByDefaultEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAudioDescriptionByDefaultEnabled);
                    return true;
                case 29:
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSystemAudioCaptioningEnabled(readBoolean2, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSystemAudioCaptioningUiEnabled = isSystemAudioCaptioningUiEnabled(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSystemAudioCaptioningUiEnabled);
                    return true;
                case 31:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSystemAudioCaptioningUiEnabled(readBoolean3, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    AccessibilityWindowAttributes accessibilityWindowAttributes = (AccessibilityWindowAttributes) parcel.readTypedObject(AccessibilityWindowAttributes.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAccessibilityWindowAttributes(readInt24, readInt25, readInt26, accessibilityWindowAttributes);
                    return true;
                case 33:
                    IAccessibilityServiceClient asInterface10 = IAccessibilityServiceClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean registerProxyForDisplay = registerProxyForDisplay(asInterface10, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerProxyForDisplay);
                    return true;
                case 34:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean unregisterProxyForDisplay = unregisterProxyForDisplay(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterProxyForDisplay);
                    return true;
                case 35:
                    InputEvent inputEvent = (InputEvent) parcel.readTypedObject(InputEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectInputEventToInputFilter(inputEvent);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    String readString4 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean startFlashNotificationSequence = startFlashNotificationSequence(readString4, readInt29, readStrongBinder7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startFlashNotificationSequence);
                    return true;
                case 37:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean stopFlashNotificationSequence = stopFlashNotificationSequence(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopFlashNotificationSequence);
                    return true;
                case 38:
                    String readString6 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean startFlashNotificationEvent = startFlashNotificationEvent(readString6, readInt30, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startFlashNotificationEvent);
                    return true;
                case 39:
                    String readString8 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAccessibilityTargetAllowed = isAccessibilityTargetAllowed(readString8, readInt31, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAccessibilityTargetAllowed);
                    return true;
                case 40:
                    String readString9 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean sendRestrictedDialogIntent = sendRestrictedDialogIntent(readString9, readInt33, readInt34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendRestrictedDialogIntent);
                    return true;
                case 41:
                    AccessibilityServiceInfo accessibilityServiceInfo2 = (AccessibilityServiceInfo) parcel.readTypedObject(AccessibilityServiceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isAccessibilityServiceWarningRequired = isAccessibilityServiceWarningRequired(accessibilityServiceInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAccessibilityServiceWarningRequired);
                    return true;
                case 42:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    WindowTransformationSpec windowTransformationSpec = getWindowTransformationSpec(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windowTransformationSpec, 1);
                    return true;
                case 43:
                    int readInt36 = parcel.readInt();
                    SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    attachAccessibilityOverlayToDisplay(readInt36, surfaceControl);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int readInt37 = parcel.readInt();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyQuickSettingsTilesChanged(readInt37, createTypedArrayList);
                    return true;
                case 45:
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt38 = parcel.readInt();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enableShortcutsForTargets(readBoolean4, readInt38, createStringArrayList, readInt39);
                    return true;
                case 46:
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle a11yFeatureToTileMap = getA11yFeatureToTileMap(readInt40);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(a11yFeatureToTileMap, 1);
                    return true;
                case 47:
                    IUserInitializationCompleteCallback asInterface11 = IUserInitializationCompleteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerUserInitializationCompleteCallback(asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    IUserInitializationCompleteCallback asInterface12 = IUserInitializationCompleteCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterUserInitializationCompleteCallback(asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean semIsAccessibilityServiceEnabled = semIsAccessibilityServiceEnabled(readInt41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsAccessibilityServiceEnabled);
                    return true;
                case 50:
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semTurnOffAccessibilityService(readInt42);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semTurnOnAccessibilityService(readInt43);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    boolean readBoolean5 = parcel.readBoolean();
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    boolean semSetColorBlind = semSetColorBlind(readBoolean5, readFloat);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semSetColorBlind);
                    return true;
                case 53:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    boolean semCheckMdnieColorBlind = semCheckMdnieColorBlind(createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semCheckMdnieColorBlind);
                    return true;
                case 54:
                    int readInt44 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean semSetMdnieAccessibilityMode = semSetMdnieAccessibilityMode(readInt44, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semSetMdnieAccessibilityMode);
                    return true;
                case 55:
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean semEnableMdnieColorFilter = semEnableMdnieColorFilter(readInt45, readInt46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semEnableMdnieColorFilter);
                    return true;
                case 56:
                    boolean semDisableMdnieColorFilter = semDisableMdnieColorFilter();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semDisableMdnieColorFilter);
                    return true;
                case 57:
                    boolean semIsDarkScreenMode = semIsDarkScreenMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsDarkScreenMode);
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
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    semRegisterAssistantMenu(readStrongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semSetTwoFingerGestureRecognitionEnabled(readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    boolean isTwoFingerGestureRecognitionEnabled = isTwoFingerGestureRecognitionEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTwoFingerGestureRecognitionEnabled);
                    return true;
                case 63:
                    boolean isScreenReaderEnabled = isScreenReaderEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isScreenReaderEnabled);
                    return true;
                case 64:
                    String screenReaderName = getScreenReaderName();
                    parcel2.writeNoException();
                    parcel2.writeString(screenReaderName);
                    return true;
                case 65:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setScreenReaderEnabled(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    float readFloat2 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    int convertPixelToDpi = convertPixelToDpi(readFloat2);
                    parcel2.writeNoException();
                    parcel2.writeInt(convertPixelToDpi);
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
                    boolean OnStartGestureWakeup = OnStartGestureWakeup();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(OnStartGestureWakeup);
                    return true;
                case 70:
                    boolean OnStopGestureWakeup = OnStopGestureWakeup();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(OnStopGestureWakeup);
                    return true;
                case 71:
                    boolean semIsAccessibilityButtonShown = semIsAccessibilityButtonShown();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsAccessibilityButtonShown);
                    return true;
                case 72:
                    setMagnificationDisactivate();
                    parcel2.writeNoException();
                    return true;
                case 73:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semDumpCallStack(readString10);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    performAccessibilityDirectAccess(readString11);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    Rect semGetWindowMagnificationBounds = semGetWindowMagnificationBounds();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semGetWindowMagnificationBounds, 1);
                    return true;
                case 76:
                    float semGetWindowMagnificationScale = semGetWindowMagnificationScale();
                    parcel2.writeNoException();
                    parcel2.writeFloat(semGetWindowMagnificationScale);
                    return true;
                case 77:
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semEnableWindowMagnification(readInt47, readInt48);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    semDisableWindowMagnification();
                    parcel2.writeNoException();
                    return true;
                case 79:
                    float readFloat3 = parcel.readFloat();
                    float readFloat4 = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    semMoveWindowMagnification(readFloat3, readFloat4);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    boolean semIsWindowMagnificationEnabled = semIsWindowMagnificationEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsWindowMagnificationEnabled);
                    return true;
                case 81:
                    boolean isActivatedMagnification = isActivatedMagnification();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivatedMagnification);
                    return true;
                case 82:
                    int readInt49 = parcel.readInt();
                    int readInt50 = parcel.readInt();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semPerformAccessibilityButtonClick(readInt49, readInt50, readString12);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    InputEvent inputEvent2 = (InputEvent) parcel.readTypedObject(InputEvent.CREATOR);
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semInjectInputEventToInputFilter(inputEvent2, readInt51);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    boolean isCameraFlashNotificationRunning = isCameraFlashNotificationRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCameraFlashNotificationRunning);
                    return true;
                case 85:
                    String readString13 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean semStartFlashNotificationSequence = semStartFlashNotificationSequence(readString13, readInt52, readStrongBinder9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semStartFlashNotificationSequence);
                    return true;
                case 86:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean semStopFlashNotificationSequence = semStopFlashNotificationSequence(readString14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semStopFlashNotificationSequence);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void sendAccessibilityEvent(AccessibilityEvent accessibilityEvent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(accessibilityEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public long addClient(IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityManagerClient);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean removeClient(IAccessibilityManagerClient iAccessibilityManagerClient, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityManagerClient);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public ParceledListSlice<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AccessibilityServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int addAccessibilityInteractionConnection(IWindow iWindow, IBinder iBinder, IAccessibilityInteractionConnection iAccessibilityInteractionConnection, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnection);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void removeAccessibilityInteractionConnection(IWindow iWindow) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection iAccessibilityInteractionConnection) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnection);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void registerUiTestAutomationService(IBinder iBinder, IAccessibilityServiceClient iAccessibilityServiceClient, AccessibilityServiceInfo accessibilityServiceInfo, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iAccessibilityServiceClient);
                    obtain.writeTypedObject(accessibilityServiceInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void unregisterUiTestAutomationService(IAccessibilityServiceClient iAccessibilityServiceClient) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityServiceClient);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public IBinder getWindowToken(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyAccessibilityButtonClicked(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyAccessibilityButtonLongClicked(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyAccessibilityButtonVisibilityChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void performAccessibilityShortcut(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public List<String> getAccessibilityShortcutTargets(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean sendFingerprintGesture(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int getAccessibilityWindowId(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public long getRecommendedTimeoutMillis() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void registerSystemAction(RemoteAction remoteAction, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(remoteAction, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void unregisterSystemAction(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setMagnificationConnection(IMagnificationConnection iMagnificationConnection) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMagnificationConnection);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void associateEmbeddedHierarchy(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void disassociateEmbeddedHierarchy(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int getFocusStrokeWidth() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int getFocusColor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isAudioDescriptionByDefaultEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setSystemAudioCaptioningEnabled(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isSystemAudioCaptioningUiEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setSystemAudioCaptioningUiEnabled(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setAccessibilityWindowAttributes(int i, int i2, int i3, AccessibilityWindowAttributes accessibilityWindowAttributes) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(accessibilityWindowAttributes, 0);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean registerProxyForDisplay(IAccessibilityServiceClient iAccessibilityServiceClient, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAccessibilityServiceClient);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean unregisterProxyForDisplay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void injectInputEventToInputFilter(InputEvent inputEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputEvent, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean startFlashNotificationSequence(String str, int i, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean stopFlashNotificationSequence(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean startFlashNotificationEvent(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isAccessibilityTargetAllowed(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean sendRestrictedDialogIntent(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isAccessibilityServiceWarningRequired(AccessibilityServiceInfo accessibilityServiceInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(accessibilityServiceInfo, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public WindowTransformationSpec getWindowTransformationSpec(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return (WindowTransformationSpec) obtain2.readTypedObject(WindowTransformationSpec.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void attachAccessibilityOverlayToDisplay(int i, SurfaceControl surfaceControl) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void notifyQuickSettingsTilesChanged(int i, List<ComponentName> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(44, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void enableShortcutsForTargets(boolean z, int i, List<String> list, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeInt(i2);
                    this.mRemote.transact(45, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public Bundle getA11yFeatureToTileMap(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void registerUserInitializationCompleteCallback(IUserInitializationCompleteCallback iUserInitializationCompleteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUserInitializationCompleteCallback);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void unregisterUserInitializationCompleteCallback(IUserInitializationCompleteCallback iUserInitializationCompleteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUserInitializationCompleteCallback);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semIsAccessibilityServiceEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semTurnOffAccessibilityService(int i) throws RemoteException {
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

            @Override // android.view.accessibility.IAccessibilityManager
            public void semTurnOnAccessibilityService(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semSetColorBlind(boolean z, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeFloat(f);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semCheckMdnieColorBlind(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semSetMdnieAccessibilityMode(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semEnableMdnieColorFilter(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semDisableMdnieColorFilter() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semIsDarkScreenMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semToggleDarkScreenMode() throws RemoteException {
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

            @Override // android.view.accessibility.IAccessibilityManager
            public void semUpdateAssitantMenu(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semRegisterAssistantMenu(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semSetTwoFingerGestureRecognitionEnabled(boolean z) throws RemoteException {
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

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isTwoFingerGestureRecognitionEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isScreenReaderEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public String getScreenReaderName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setScreenReaderEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public int convertPixelToDpi(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setTalkbackMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semLockNow() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean OnStartGestureWakeup() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean OnStopGestureWakeup() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semIsAccessibilityButtonShown() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void setMagnificationDisactivate() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semDumpCallStack(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void performAccessibilityDirectAccess(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public Rect semGetWindowMagnificationBounds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Rect) obtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public float semGetWindowMagnificationScale() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semEnableWindowMagnification(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semDisableWindowMagnification() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semMoveWindowMagnification(float f, float f2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semIsWindowMagnificationEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isActivatedMagnification() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semPerformAccessibilityButtonClick(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public void semInjectInputEventToInputFilter(InputEvent inputEvent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputEvent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean isCameraFlashNotificationRunning() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semStartFlashNotificationSequence(String str, int i, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.accessibility.IAccessibilityManager
            public boolean semStopFlashNotificationSequence(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeFloatArray(this.transformationMatrix);
            parcel.writeTypedObject(this.magnificationSpec, i);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.transformationMatrix = parcel.createFloatArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.magnificationSpec = (MagnificationSpec) parcel.readTypedObject(MagnificationSpec.CREATOR);
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
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
