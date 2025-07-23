package android.accessibilityservice;

import android.Manifest;
import android.accessibilityservice.IBrailleDisplayController;
import android.app.ActivityThread;
import android.content.pm.ParceledListSlice;
import android.graphics.Region;
import android.hardware.usb.UsbDevice;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.view.SurfaceControl;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import android.window.ScreenCapture;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IAccessibilityServiceConnection extends IInterface {

    public static class Default implements IAccessibilityServiceConnection {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void attachAccessibilityOverlayToDisplay(int i, int i2, SurfaceControl surfaceControl, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void attachAccessibilityOverlayToWindow(int i, int i2, SurfaceControl surfaceControl, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void connectBluetoothBrailleDisplay(String str, IBrailleDisplayController iBrailleDisplayController) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void connectUsbBrailleDisplay(UsbDevice usbDevice, IBrailleDisplayController iBrailleDisplayController) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void disableSelf() throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void dispatchGesture(int i, ParceledListSlice parceledListSlice, int i2) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public String[] findAccessibilityNodeInfoByAccessibilityId(int i, long j, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, long j2, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public String[] findAccessibilityNodeInfosByText(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public String[] findAccessibilityNodeInfosByViewId(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public String[] findFocus(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public String[] focusSearch(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public Region getCurrentMagnificationRegion(int i) throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public List<AccessibilityServiceInfo> getInstalledAndEnabledServices() throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public float getMagnificationCenterX(int i) throws RemoteException {
            return 0.0f;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public float getMagnificationCenterY(int i) throws RemoteException {
            return 0.0f;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public MagnificationConfig getMagnificationConfig(int i) throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public Region getMagnificationRegion(int i) throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public float getMagnificationScale(int i) throws RemoteException {
            return 0.0f;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public IBinder getOverlayWindowToken(int i) throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public AccessibilityServiceInfo getServiceInfo() throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public int getSoftKeyboardShowMode() throws RemoteException {
            return 0;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public List<AccessibilityNodeInfo.AccessibilityAction> getSystemActions() throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public AccessibilityWindowInfo getWindow(int i) throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public int getWindowIdForLeashToken(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public AccessibilityWindowInfo.WindowListSparseArray getWindows() throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public List<AccessibilityWindowInfo> getWindowsMainDisplay(int i) throws RemoteException {
            return null;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean isAccessibilityButtonAvailable() throws RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean isFingerprintGestureDetectionAvailable() throws RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void logTrace(long j, String str, long j2, String str2, int i, long j3, int i2, Bundle bundle) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void onDoubleTap(int i) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void onDoubleTapAndHold(int i) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean performAccessibilityAction(int i, long j, int i2, Bundle bundle, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean performGlobalAction(int i) throws RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void requestDelegating(int i) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void requestDragging(int i, int i2) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void requestTouchExploration(int i) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean resetCurrentMagnification(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean resetMagnification(int i, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void sendGesture(int i, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setAnimationScale(float f) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setAttributionTag(String str) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setCacheEnabled(boolean z) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setFocusAppearance(int i, int i2) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setGestureDetectionPassthroughRegion(int i, Region region) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public int setInputMethodEnabled(String str, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setInstalledAndEnabledServices(List<AccessibilityServiceInfo> list) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setMagnificationCallbackEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean setMagnificationConfig(int i, MagnificationConfig magnificationConfig, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setOnKeyEventResult(boolean z, int i) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setServiceDetectsGesturesEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setServiceInfo(AccessibilityServiceInfo accessibilityServiceInfo) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setSoftKeyboardCallbackEnabled(boolean z) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean setSoftKeyboardShowMode(int i) throws RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setTestBrailleDisplayData(List<Bundle> list) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void setTouchExplorationPassthroughRegion(int i, Region region) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public boolean switchToInputMethod(String str) throws RemoteException {
            return false;
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void takeScreenshot(int i, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.accessibilityservice.IAccessibilityServiceConnection
        public void takeScreenshotOfWindow(int i, int i2, ScreenCapture.ScreenCaptureListener screenCaptureListener, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
        }
    }

    void attachAccessibilityOverlayToDisplay(int i, int i2, SurfaceControl surfaceControl, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException;

    void attachAccessibilityOverlayToWindow(int i, int i2, SurfaceControl surfaceControl, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException;

    void connectBluetoothBrailleDisplay(String str, IBrailleDisplayController iBrailleDisplayController) throws RemoteException;

    void connectUsbBrailleDisplay(UsbDevice usbDevice, IBrailleDisplayController iBrailleDisplayController) throws RemoteException;

    void disableSelf() throws RemoteException;

    void dispatchGesture(int i, ParceledListSlice parceledListSlice, int i2) throws RemoteException;

    String[] findAccessibilityNodeInfoByAccessibilityId(int i, long j, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, long j2, Bundle bundle) throws RemoteException;

    String[] findAccessibilityNodeInfosByText(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException;

    String[] findAccessibilityNodeInfosByViewId(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException;

    String[] findFocus(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException;

    String[] focusSearch(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException;

    Region getCurrentMagnificationRegion(int i) throws RemoteException;

    List<AccessibilityServiceInfo> getInstalledAndEnabledServices() throws RemoteException;

    float getMagnificationCenterX(int i) throws RemoteException;

    float getMagnificationCenterY(int i) throws RemoteException;

    MagnificationConfig getMagnificationConfig(int i) throws RemoteException;

    Region getMagnificationRegion(int i) throws RemoteException;

    float getMagnificationScale(int i) throws RemoteException;

    IBinder getOverlayWindowToken(int i) throws RemoteException;

    AccessibilityServiceInfo getServiceInfo() throws RemoteException;

    int getSoftKeyboardShowMode() throws RemoteException;

    List<AccessibilityNodeInfo.AccessibilityAction> getSystemActions() throws RemoteException;

    AccessibilityWindowInfo getWindow(int i) throws RemoteException;

    int getWindowIdForLeashToken(IBinder iBinder) throws RemoteException;

    AccessibilityWindowInfo.WindowListSparseArray getWindows() throws RemoteException;

    List<AccessibilityWindowInfo> getWindowsMainDisplay(int i) throws RemoteException;

    boolean isAccessibilityButtonAvailable() throws RemoteException;

    boolean isFingerprintGestureDetectionAvailable() throws RemoteException;

    void logTrace(long j, String str, long j2, String str2, int i, long j3, int i2, Bundle bundle) throws RemoteException;

    void onDoubleTap(int i) throws RemoteException;

    void onDoubleTapAndHold(int i) throws RemoteException;

    boolean performAccessibilityAction(int i, long j, int i2, Bundle bundle, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException;

    boolean performGlobalAction(int i) throws RemoteException;

    void requestDelegating(int i) throws RemoteException;

    void requestDragging(int i, int i2) throws RemoteException;

    void requestTouchExploration(int i) throws RemoteException;

    boolean resetCurrentMagnification(int i, boolean z) throws RemoteException;

    boolean resetMagnification(int i, boolean z) throws RemoteException;

    void sendGesture(int i, ParceledListSlice parceledListSlice) throws RemoteException;

    void setAnimationScale(float f) throws RemoteException;

    void setAttributionTag(String str) throws RemoteException;

    void setCacheEnabled(boolean z) throws RemoteException;

    void setFocusAppearance(int i, int i2) throws RemoteException;

    void setGestureDetectionPassthroughRegion(int i, Region region) throws RemoteException;

    int setInputMethodEnabled(String str, boolean z) throws RemoteException;

    void setInstalledAndEnabledServices(List<AccessibilityServiceInfo> list) throws RemoteException;

    void setMagnificationCallbackEnabled(int i, boolean z) throws RemoteException;

    boolean setMagnificationConfig(int i, MagnificationConfig magnificationConfig, boolean z) throws RemoteException;

    void setOnKeyEventResult(boolean z, int i) throws RemoteException;

    void setServiceDetectsGesturesEnabled(int i, boolean z) throws RemoteException;

    void setServiceInfo(AccessibilityServiceInfo accessibilityServiceInfo) throws RemoteException;

    void setSoftKeyboardCallbackEnabled(boolean z) throws RemoteException;

    boolean setSoftKeyboardShowMode(int i) throws RemoteException;

    void setTestBrailleDisplayData(List<Bundle> list) throws RemoteException;

    void setTouchExplorationPassthroughRegion(int i, Region region) throws RemoteException;

    boolean switchToInputMethod(String str) throws RemoteException;

    void takeScreenshot(int i, RemoteCallback remoteCallback) throws RemoteException;

    void takeScreenshotOfWindow(int i, int i2, ScreenCapture.ScreenCaptureListener screenCaptureListener, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IAccessibilityServiceConnection {
        public static final String DESCRIPTOR = "android.accessibilityservice.IAccessibilityServiceConnection";
        static final int TRANSACTION_attachAccessibilityOverlayToDisplay = 54;
        static final int TRANSACTION_attachAccessibilityOverlayToWindow = 55;
        static final int TRANSACTION_connectBluetoothBrailleDisplay = 56;
        static final int TRANSACTION_connectUsbBrailleDisplay = 57;
        static final int TRANSACTION_disableSelf = 15;
        static final int TRANSACTION_dispatchGesture = 34;
        static final int TRANSACTION_findAccessibilityNodeInfoByAccessibilityId = 3;
        static final int TRANSACTION_findAccessibilityNodeInfosByText = 4;
        static final int TRANSACTION_findAccessibilityNodeInfosByViewId = 5;
        static final int TRANSACTION_findFocus = 6;
        static final int TRANSACTION_focusSearch = 7;
        static final int TRANSACTION_getCurrentMagnificationRegion = 22;
        static final int TRANSACTION_getInstalledAndEnabledServices = 53;
        static final int TRANSACTION_getMagnificationCenterX = 19;
        static final int TRANSACTION_getMagnificationCenterY = 20;
        static final int TRANSACTION_getMagnificationConfig = 17;
        static final int TRANSACTION_getMagnificationRegion = 21;
        static final int TRANSACTION_getMagnificationScale = 18;
        static final int TRANSACTION_getOverlayWindowToken = 36;
        static final int TRANSACTION_getServiceInfo = 11;
        static final int TRANSACTION_getSoftKeyboardShowMode = 28;
        static final int TRANSACTION_getSystemActions = 14;
        static final int TRANSACTION_getWindow = 9;
        static final int TRANSACTION_getWindowIdForLeashToken = 37;
        static final int TRANSACTION_getWindows = 10;
        static final int TRANSACTION_getWindowsMainDisplay = 12;
        static final int TRANSACTION_isAccessibilityButtonAvailable = 32;
        static final int TRANSACTION_isFingerprintGestureDetectionAvailable = 35;
        static final int TRANSACTION_logTrace = 44;
        static final int TRANSACTION_onDoubleTap = 49;
        static final int TRANSACTION_onDoubleTapAndHold = 50;
        static final int TRANSACTION_performAccessibilityAction = 8;
        static final int TRANSACTION_performGlobalAction = 13;
        static final int TRANSACTION_requestDelegating = 48;
        static final int TRANSACTION_requestDragging = 47;
        static final int TRANSACTION_requestTouchExploration = 46;
        static final int TRANSACTION_resetCurrentMagnification = 24;
        static final int TRANSACTION_resetMagnification = 23;
        static final int TRANSACTION_sendGesture = 33;
        static final int TRANSACTION_setAnimationScale = 51;
        static final int TRANSACTION_setAttributionTag = 2;
        static final int TRANSACTION_setCacheEnabled = 43;
        static final int TRANSACTION_setFocusAppearance = 42;
        static final int TRANSACTION_setGestureDetectionPassthroughRegion = 40;
        static final int TRANSACTION_setInputMethodEnabled = 31;
        static final int TRANSACTION_setInstalledAndEnabledServices = 52;
        static final int TRANSACTION_setMagnificationCallbackEnabled = 26;
        static final int TRANSACTION_setMagnificationConfig = 25;
        static final int TRANSACTION_setOnKeyEventResult = 16;
        static final int TRANSACTION_setServiceDetectsGesturesEnabled = 45;
        static final int TRANSACTION_setServiceInfo = 1;
        static final int TRANSACTION_setSoftKeyboardCallbackEnabled = 29;
        static final int TRANSACTION_setSoftKeyboardShowMode = 27;
        static final int TRANSACTION_setTestBrailleDisplayData = 58;
        static final int TRANSACTION_setTouchExplorationPassthroughRegion = 41;
        static final int TRANSACTION_switchToInputMethod = 30;
        static final int TRANSACTION_takeScreenshot = 38;
        static final int TRANSACTION_takeScreenshotOfWindow = 39;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 57;
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

        public static IAccessibilityServiceConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAccessibilityServiceConnection)) {
                return (IAccessibilityServiceConnection) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setServiceInfo";
                case 2:
                    return "setAttributionTag";
                case 3:
                    return "findAccessibilityNodeInfoByAccessibilityId";
                case 4:
                    return "findAccessibilityNodeInfosByText";
                case 5:
                    return "findAccessibilityNodeInfosByViewId";
                case 6:
                    return "findFocus";
                case 7:
                    return "focusSearch";
                case 8:
                    return "performAccessibilityAction";
                case 9:
                    return "getWindow";
                case 10:
                    return "getWindows";
                case 11:
                    return "getServiceInfo";
                case 12:
                    return "getWindowsMainDisplay";
                case 13:
                    return "performGlobalAction";
                case 14:
                    return "getSystemActions";
                case 15:
                    return "disableSelf";
                case 16:
                    return "setOnKeyEventResult";
                case 17:
                    return "getMagnificationConfig";
                case 18:
                    return "getMagnificationScale";
                case 19:
                    return "getMagnificationCenterX";
                case 20:
                    return "getMagnificationCenterY";
                case 21:
                    return "getMagnificationRegion";
                case 22:
                    return "getCurrentMagnificationRegion";
                case 23:
                    return "resetMagnification";
                case 24:
                    return "resetCurrentMagnification";
                case 25:
                    return "setMagnificationConfig";
                case 26:
                    return "setMagnificationCallbackEnabled";
                case 27:
                    return "setSoftKeyboardShowMode";
                case 28:
                    return "getSoftKeyboardShowMode";
                case 29:
                    return "setSoftKeyboardCallbackEnabled";
                case 30:
                    return "switchToInputMethod";
                case 31:
                    return "setInputMethodEnabled";
                case 32:
                    return "isAccessibilityButtonAvailable";
                case 33:
                    return "sendGesture";
                case 34:
                    return "dispatchGesture";
                case 35:
                    return "isFingerprintGestureDetectionAvailable";
                case 36:
                    return "getOverlayWindowToken";
                case 37:
                    return "getWindowIdForLeashToken";
                case 38:
                    return "takeScreenshot";
                case 39:
                    return "takeScreenshotOfWindow";
                case 40:
                    return "setGestureDetectionPassthroughRegion";
                case 41:
                    return "setTouchExplorationPassthroughRegion";
                case 42:
                    return "setFocusAppearance";
                case 43:
                    return "setCacheEnabled";
                case 44:
                    return "logTrace";
                case 45:
                    return "setServiceDetectsGesturesEnabled";
                case 46:
                    return "requestTouchExploration";
                case 47:
                    return "requestDragging";
                case 48:
                    return "requestDelegating";
                case 49:
                    return "onDoubleTap";
                case 50:
                    return "onDoubleTapAndHold";
                case 51:
                    return "setAnimationScale";
                case 52:
                    return "setInstalledAndEnabledServices";
                case 53:
                    return "getInstalledAndEnabledServices";
                case 54:
                    return "attachAccessibilityOverlayToDisplay";
                case 55:
                    return "attachAccessibilityOverlayToWindow";
                case 56:
                    return "connectBluetoothBrailleDisplay";
                case 57:
                    return "connectUsbBrailleDisplay";
                case 58:
                    return "setTestBrailleDisplayData";
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
                    AccessibilityServiceInfo accessibilityServiceInfo = (AccessibilityServiceInfo) parcel.readTypedObject(AccessibilityServiceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setServiceInfo(accessibilityServiceInfo);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAttributionTag(readString);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    long readLong = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback asInterface = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt3 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] findAccessibilityNodeInfoByAccessibilityId = findAccessibilityNodeInfoByAccessibilityId(readInt, readLong, readInt2, asInterface, readInt3, readLong2, bundle);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(findAccessibilityNodeInfoByAccessibilityId);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    String readString2 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback asInterface2 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String[] findAccessibilityNodeInfosByText = findAccessibilityNodeInfosByText(readInt4, readLong3, readString2, readInt5, asInterface2, readLong4);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(findAccessibilityNodeInfosByText);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    long readLong5 = parcel.readLong();
                    String readString3 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback asInterface3 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String[] findAccessibilityNodeInfosByViewId = findAccessibilityNodeInfosByViewId(readInt6, readLong5, readString3, readInt7, asInterface3, readLong6);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(findAccessibilityNodeInfosByViewId);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    long readLong7 = parcel.readLong();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback asInterface4 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String[] findFocus = findFocus(readInt8, readLong7, readInt9, readInt10, asInterface4, readLong8);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(findFocus);
                    return true;
                case 7:
                    int readInt11 = parcel.readInt();
                    long readLong9 = parcel.readLong();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback asInterface5 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong10 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String[] focusSearch = focusSearch(readInt11, readLong9, readInt12, readInt13, asInterface5, readLong10);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(focusSearch);
                    return true;
                case 8:
                    int readInt14 = parcel.readInt();
                    long readLong11 = parcel.readLong();
                    int readInt15 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt16 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback asInterface6 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long readLong12 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean performAccessibilityAction = performAccessibilityAction(readInt14, readLong11, readInt15, bundle2, readInt16, asInterface6, readLong12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(performAccessibilityAction);
                    return true;
                case 9:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AccessibilityWindowInfo window = getWindow(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(window, 1);
                    return true;
                case 10:
                    AccessibilityWindowInfo.WindowListSparseArray windows = getWindows();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windows, 1);
                    return true;
                case 11:
                    AccessibilityServiceInfo serviceInfo = getServiceInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(serviceInfo, 1);
                    return true;
                case 12:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AccessibilityWindowInfo> windowsMainDisplay = getWindowsMainDisplay(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(windowsMainDisplay, 1);
                    return true;
                case 13:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean performGlobalAction = performGlobalAction(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(performGlobalAction);
                    return true;
                case 14:
                    List<AccessibilityNodeInfo.AccessibilityAction> systemActions = getSystemActions();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(systemActions, 1);
                    return true;
                case 15:
                    disableSelf();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOnKeyEventResult(readBoolean, readInt20);
                    return true;
                case 17:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    MagnificationConfig magnificationConfig = getMagnificationConfig(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(magnificationConfig, 1);
                    return true;
                case 18:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float magnificationScale = getMagnificationScale(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeFloat(magnificationScale);
                    return true;
                case 19:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float magnificationCenterX = getMagnificationCenterX(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeFloat(magnificationCenterX);
                    return true;
                case 20:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float magnificationCenterY = getMagnificationCenterY(readInt24);
                    parcel2.writeNoException();
                    parcel2.writeFloat(magnificationCenterY);
                    return true;
                case 21:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Region magnificationRegion = getMagnificationRegion(readInt25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(magnificationRegion, 1);
                    return true;
                case 22:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Region currentMagnificationRegion = getCurrentMagnificationRegion(readInt26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentMagnificationRegion, 1);
                    return true;
                case 23:
                    int readInt27 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean resetMagnification = resetMagnification(readInt27, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetMagnification);
                    return true;
                case 24:
                    int readInt28 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean resetCurrentMagnification = resetCurrentMagnification(readInt28, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetCurrentMagnification);
                    return true;
                case 25:
                    int readInt29 = parcel.readInt();
                    MagnificationConfig magnificationConfig2 = (MagnificationConfig) parcel.readTypedObject(MagnificationConfig.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean magnificationConfig3 = setMagnificationConfig(readInt29, magnificationConfig2, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(magnificationConfig3);
                    return true;
                case 26:
                    int readInt30 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMagnificationCallbackEnabled(readInt30, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean softKeyboardShowMode = setSoftKeyboardShowMode(readInt31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(softKeyboardShowMode);
                    return true;
                case 28:
                    int softKeyboardShowMode2 = getSoftKeyboardShowMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(softKeyboardShowMode2);
                    return true;
                case 29:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSoftKeyboardCallbackEnabled(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean switchToInputMethod = switchToInputMethod(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(switchToInputMethod);
                    return true;
                case 31:
                    String readString5 = parcel.readString();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int inputMethodEnabled = setInputMethodEnabled(readString5, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeInt(inputMethodEnabled);
                    return true;
                case 32:
                    boolean isAccessibilityButtonAvailable = isAccessibilityButtonAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAccessibilityButtonAvailable);
                    return true;
                case 33:
                    int readInt32 = parcel.readInt();
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendGesture(readInt32, parceledListSlice);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int readInt33 = parcel.readInt();
                    ParceledListSlice parceledListSlice2 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchGesture(readInt33, parceledListSlice2, readInt34);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    boolean isFingerprintGestureDetectionAvailable = isFingerprintGestureDetectionAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFingerprintGestureDetectionAvailable);
                    return true;
                case 36:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IBinder overlayWindowToken = getOverlayWindowToken(readInt35);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(overlayWindowToken);
                    return true;
                case 37:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int windowIdForLeashToken = getWindowIdForLeashToken(readStrongBinder);
                    parcel2.writeNoException();
                    parcel2.writeInt(windowIdForLeashToken);
                    return true;
                case 38:
                    int readInt36 = parcel.readInt();
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    takeScreenshot(readInt36, remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    ScreenCapture.ScreenCaptureListener screenCaptureListener = (ScreenCapture.ScreenCaptureListener) parcel.readTypedObject(ScreenCapture.ScreenCaptureListener.CREATOR);
                    IAccessibilityInteractionConnectionCallback asInterface7 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    takeScreenshotOfWindow(readInt37, readInt38, screenCaptureListener, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int readInt39 = parcel.readInt();
                    Region region = (Region) parcel.readTypedObject(Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    setGestureDetectionPassthroughRegion(readInt39, region);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt40 = parcel.readInt();
                    Region region2 = (Region) parcel.readTypedObject(Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTouchExplorationPassthroughRegion(readInt40, region2);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusAppearance(readInt41, readInt42);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCacheEnabled(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    long readLong13 = parcel.readLong();
                    String readString6 = parcel.readString();
                    long readLong14 = parcel.readLong();
                    String readString7 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    long readLong15 = parcel.readLong();
                    int readInt44 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    logTrace(readLong13, readString6, readLong14, readString7, readInt43, readLong15, readInt44, bundle3);
                    return true;
                case 45:
                    int readInt45 = parcel.readInt();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setServiceDetectsGesturesEnabled(readInt45, readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestTouchExploration(readInt46);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestDragging(readInt47, readInt48);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestDelegating(readInt49);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDoubleTap(readInt50);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDoubleTapAndHold(readInt51);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setAnimationScale(readFloat);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(AccessibilityServiceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInstalledAndEnabledServices(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    List<AccessibilityServiceInfo> installedAndEnabledServices = getInstalledAndEnabledServices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(installedAndEnabledServices, 1);
                    return true;
                case 54:
                    int readInt52 = parcel.readInt();
                    int readInt53 = parcel.readInt();
                    SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    IAccessibilityInteractionConnectionCallback asInterface8 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachAccessibilityOverlayToDisplay(readInt52, readInt53, surfaceControl, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    int readInt54 = parcel.readInt();
                    int readInt55 = parcel.readInt();
                    SurfaceControl surfaceControl2 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    IAccessibilityInteractionConnectionCallback asInterface9 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachAccessibilityOverlayToWindow(readInt54, readInt55, surfaceControl2, asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    String readString8 = parcel.readString();
                    IBrailleDisplayController asInterface10 = IBrailleDisplayController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    connectBluetoothBrailleDisplay(readString8, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    UsbDevice usbDevice = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    IBrailleDisplayController asInterface11 = IBrailleDisplayController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    connectUsbBrailleDisplay(usbDevice, asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTestBrailleDisplayData(createTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAccessibilityServiceConnection {
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

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setServiceInfo(AccessibilityServiceInfo accessibilityServiceInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(accessibilityServiceInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setAttributionTag(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public String[] findAccessibilityNodeInfoByAccessibilityId(int i, long j, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, long j2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeInt(i3);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public String[] findAccessibilityNodeInfosByText(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeLong(j2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public String[] findAccessibilityNodeInfosByViewId(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeLong(j2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public String[] findFocus(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeLong(j2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public String[] focusSearch(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeLong(j2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean performAccessibilityAction(int i, long j, int i2, Bundle bundle, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    obtain.writeLong(j2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public AccessibilityWindowInfo getWindow(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AccessibilityWindowInfo) obtain2.readTypedObject(AccessibilityWindowInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public AccessibilityWindowInfo.WindowListSparseArray getWindows() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AccessibilityWindowInfo.WindowListSparseArray) obtain2.readTypedObject(AccessibilityWindowInfo.WindowListSparseArray.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public AccessibilityServiceInfo getServiceInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AccessibilityServiceInfo) obtain2.readTypedObject(AccessibilityServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public List<AccessibilityWindowInfo> getWindowsMainDisplay(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AccessibilityWindowInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean performGlobalAction(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public List<AccessibilityNodeInfo.AccessibilityAction> getSystemActions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AccessibilityNodeInfo.AccessibilityAction.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void disableSelf() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setOnKeyEventResult(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public MagnificationConfig getMagnificationConfig(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MagnificationConfig) obtain2.readTypedObject(MagnificationConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public float getMagnificationScale(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public float getMagnificationCenterX(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public float getMagnificationCenterY(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public Region getMagnificationRegion(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Region) obtain2.readTypedObject(Region.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public Region getCurrentMagnificationRegion(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Region) obtain2.readTypedObject(Region.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean resetMagnification(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean resetCurrentMagnification(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean setMagnificationConfig(int i, MagnificationConfig magnificationConfig, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(magnificationConfig, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setMagnificationCallbackEnabled(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean setSoftKeyboardShowMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public int getSoftKeyboardShowMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setSoftKeyboardCallbackEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean switchToInputMethod(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public int setInputMethodEnabled(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean isAccessibilityButtonAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void sendGesture(int i, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void dispatchGesture(int i, ParceledListSlice parceledListSlice, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean isFingerprintGestureDetectionAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public IBinder getOverlayWindowToken(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public int getWindowIdForLeashToken(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void takeScreenshot(int i, RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void takeScreenshotOfWindow(int i, int i2, ScreenCapture.ScreenCaptureListener screenCaptureListener, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(screenCaptureListener, 0);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setGestureDetectionPassthroughRegion(int i, Region region) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(region, 0);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setTouchExplorationPassthroughRegion(int i, Region region) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(region, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setFocusAppearance(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setCacheEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void logTrace(long j, String str, long j2, String str2, int i, long j3, int i2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeLong(j2);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeLong(j3);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(44, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setServiceDetectsGesturesEnabled(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void requestTouchExploration(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void requestDragging(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void requestDelegating(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void onDoubleTap(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void onDoubleTapAndHold(int i) throws RemoteException {
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

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setAnimationScale(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setInstalledAndEnabledServices(List<AccessibilityServiceInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public List<AccessibilityServiceInfo> getInstalledAndEnabledServices() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AccessibilityServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void attachAccessibilityOverlayToDisplay(int i, int i2, SurfaceControl surfaceControl, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void attachAccessibilityOverlayToWindow(int i, int i2, SurfaceControl surfaceControl, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(surfaceControl, 0);
                    obtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void connectBluetoothBrailleDisplay(String str, IBrailleDisplayController iBrailleDisplayController) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iBrailleDisplayController);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void connectUsbBrailleDisplay(UsbDevice usbDevice, IBrailleDisplayController iBrailleDisplayController) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usbDevice, 0);
                    obtain.writeStrongInterface(iBrailleDisplayController);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setTestBrailleDisplayData(List<Bundle> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void connectBluetoothBrailleDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BLUETOOTH_CONNECT, getCallingPid(), getCallingUid());
        }

        protected void setTestBrailleDisplayData_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ACCESSIBILITY, getCallingPid(), getCallingUid());
        }
    }
}
