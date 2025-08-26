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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAccessibilityServiceConnection)) {
                return (IAccessibilityServiceConnection) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAttributionTag(string);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    long j = parcel.readLong();
                    int i4 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i5 = parcel.readInt();
                    long j2 = parcel.readLong();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] strArrFindAccessibilityNodeInfoByAccessibilityId = findAccessibilityNodeInfoByAccessibilityId(i3, j, i4, iAccessibilityInteractionConnectionCallbackAsInterface, i5, j2, bundle);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrFindAccessibilityNodeInfoByAccessibilityId);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    long j3 = parcel.readLong();
                    String string2 = parcel.readString();
                    int i7 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface2 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String[] strArrFindAccessibilityNodeInfosByText = findAccessibilityNodeInfosByText(i6, j3, string2, i7, iAccessibilityInteractionConnectionCallbackAsInterface2, j4);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrFindAccessibilityNodeInfosByText);
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    long j5 = parcel.readLong();
                    String string3 = parcel.readString();
                    int i9 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface3 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long j6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String[] strArrFindAccessibilityNodeInfosByViewId = findAccessibilityNodeInfosByViewId(i8, j5, string3, i9, iAccessibilityInteractionConnectionCallbackAsInterface3, j6);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrFindAccessibilityNodeInfosByViewId);
                    return true;
                case 6:
                    int i10 = parcel.readInt();
                    long j7 = parcel.readLong();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface4 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long j8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String[] strArrFindFocus = findFocus(i10, j7, i11, i12, iAccessibilityInteractionConnectionCallbackAsInterface4, j8);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrFindFocus);
                    return true;
                case 7:
                    int i13 = parcel.readInt();
                    long j9 = parcel.readLong();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface5 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long j10 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    String[] strArrFocusSearch = focusSearch(i13, j9, i14, i15, iAccessibilityInteractionConnectionCallbackAsInterface5, j10);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrFocusSearch);
                    return true;
                case 8:
                    int i16 = parcel.readInt();
                    long j11 = parcel.readLong();
                    int i17 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i18 = parcel.readInt();
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface6 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    long j12 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zPerformAccessibilityAction = performAccessibilityAction(i16, j11, i17, bundle2, i18, iAccessibilityInteractionConnectionCallbackAsInterface6, j12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPerformAccessibilityAction);
                    return true;
                case 9:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AccessibilityWindowInfo window = getWindow(i19);
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
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<AccessibilityWindowInfo> windowsMainDisplay = getWindowsMainDisplay(i20);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(windowsMainDisplay, 1);
                    return true;
                case 13:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zPerformGlobalAction = performGlobalAction(i21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPerformGlobalAction);
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
                    boolean z = parcel.readBoolean();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOnKeyEventResult(z, i22);
                    return true;
                case 17:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    MagnificationConfig magnificationConfig = getMagnificationConfig(i23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(magnificationConfig, 1);
                    return true;
                case 18:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float magnificationScale = getMagnificationScale(i24);
                    parcel2.writeNoException();
                    parcel2.writeFloat(magnificationScale);
                    return true;
                case 19:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float magnificationCenterX = getMagnificationCenterX(i25);
                    parcel2.writeNoException();
                    parcel2.writeFloat(magnificationCenterX);
                    return true;
                case 20:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float magnificationCenterY = getMagnificationCenterY(i26);
                    parcel2.writeNoException();
                    parcel2.writeFloat(magnificationCenterY);
                    return true;
                case 21:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Region magnificationRegion = getMagnificationRegion(i27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(magnificationRegion, 1);
                    return true;
                case 22:
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Region currentMagnificationRegion = getCurrentMagnificationRegion(i28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentMagnificationRegion, 1);
                    return true;
                case 23:
                    int i29 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zResetMagnification = resetMagnification(i29, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetMagnification);
                    return true;
                case 24:
                    int i30 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zResetCurrentMagnification = resetCurrentMagnification(i30, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetCurrentMagnification);
                    return true;
                case 25:
                    int i31 = parcel.readInt();
                    MagnificationConfig magnificationConfig2 = (MagnificationConfig) parcel.readTypedObject(MagnificationConfig.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean magnificationConfig3 = setMagnificationConfig(i31, magnificationConfig2, z4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(magnificationConfig3);
                    return true;
                case 26:
                    int i32 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMagnificationCallbackEnabled(i32, z5);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean softKeyboardShowMode = setSoftKeyboardShowMode(i33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(softKeyboardShowMode);
                    return true;
                case 28:
                    int softKeyboardShowMode2 = getSoftKeyboardShowMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(softKeyboardShowMode2);
                    return true;
                case 29:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSoftKeyboardCallbackEnabled(z6);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zSwitchToInputMethod = switchToInputMethod(string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSwitchToInputMethod);
                    return true;
                case 31:
                    String string5 = parcel.readString();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int inputMethodEnabled = setInputMethodEnabled(string5, z7);
                    parcel2.writeNoException();
                    parcel2.writeInt(inputMethodEnabled);
                    return true;
                case 32:
                    boolean zIsAccessibilityButtonAvailable = isAccessibilityButtonAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAccessibilityButtonAvailable);
                    return true;
                case 33:
                    int i34 = parcel.readInt();
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendGesture(i34, parceledListSlice);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int i35 = parcel.readInt();
                    ParceledListSlice parceledListSlice2 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchGesture(i35, parceledListSlice2, i36);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    boolean zIsFingerprintGestureDetectionAvailable = isFingerprintGestureDetectionAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFingerprintGestureDetectionAvailable);
                    return true;
                case 36:
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IBinder overlayWindowToken = getOverlayWindowToken(i37);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(overlayWindowToken);
                    return true;
                case 37:
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int windowIdForLeashToken = getWindowIdForLeashToken(strongBinder);
                    parcel2.writeNoException();
                    parcel2.writeInt(windowIdForLeashToken);
                    return true;
                case 38:
                    int i38 = parcel.readInt();
                    RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    takeScreenshot(i38, remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int i39 = parcel.readInt();
                    int i40 = parcel.readInt();
                    ScreenCapture.ScreenCaptureListener screenCaptureListener = (ScreenCapture.ScreenCaptureListener) parcel.readTypedObject(ScreenCapture.ScreenCaptureListener.CREATOR);
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface7 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    takeScreenshotOfWindow(i39, i40, screenCaptureListener, iAccessibilityInteractionConnectionCallbackAsInterface7);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    int i41 = parcel.readInt();
                    Region region = (Region) parcel.readTypedObject(Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    setGestureDetectionPassthroughRegion(i41, region);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int i42 = parcel.readInt();
                    Region region2 = (Region) parcel.readTypedObject(Region.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTouchExplorationPassthroughRegion(i42, region2);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int i43 = parcel.readInt();
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFocusAppearance(i43, i44);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCacheEnabled(z8);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    long j13 = parcel.readLong();
                    String string6 = parcel.readString();
                    long j14 = parcel.readLong();
                    String string7 = parcel.readString();
                    int i45 = parcel.readInt();
                    long j15 = parcel.readLong();
                    int i46 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    logTrace(j13, string6, j14, string7, i45, j15, i46, bundle3);
                    return true;
                case 45:
                    int i47 = parcel.readInt();
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setServiceDetectsGesturesEnabled(i47, z9);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestTouchExploration(i48);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int i49 = parcel.readInt();
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestDragging(i49, i50);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestDelegating(i51);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int i52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDoubleTap(i52);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int i53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDoubleTapAndHold(i53);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setAnimationScale(f);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(AccessibilityServiceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInstalledAndEnabledServices(arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    List<AccessibilityServiceInfo> installedAndEnabledServices = getInstalledAndEnabledServices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(installedAndEnabledServices, 1);
                    return true;
                case 54:
                    int i54 = parcel.readInt();
                    int i55 = parcel.readInt();
                    SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface8 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachAccessibilityOverlayToDisplay(i54, i55, surfaceControl, iAccessibilityInteractionConnectionCallbackAsInterface8);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    int i56 = parcel.readInt();
                    int i57 = parcel.readInt();
                    SurfaceControl surfaceControl2 = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallbackAsInterface9 = IAccessibilityInteractionConnectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    attachAccessibilityOverlayToWindow(i56, i57, surfaceControl2, iAccessibilityInteractionConnectionCallbackAsInterface9);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    String string8 = parcel.readString();
                    IBrailleDisplayController iBrailleDisplayControllerAsInterface = IBrailleDisplayController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    connectBluetoothBrailleDisplay(string8, iBrailleDisplayControllerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    UsbDevice usbDevice = (UsbDevice) parcel.readTypedObject(UsbDevice.CREATOR);
                    IBrailleDisplayController iBrailleDisplayControllerAsInterface2 = IBrailleDisplayController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    connectUsbBrailleDisplay(usbDevice, iBrailleDisplayControllerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTestBrailleDisplayData(arrayListCreateTypedArrayList2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(accessibilityServiceInfo, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setAttributionTag(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public String[] findAccessibilityNodeInfoByAccessibilityId(int i, long j, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, long j2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public String[] findAccessibilityNodeInfosByText(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public String[] findAccessibilityNodeInfosByViewId(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public String[] findFocus(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public String[] focusSearch(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean performAccessibilityAction(int i, long j, int i2, Bundle bundle, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public AccessibilityWindowInfo getWindow(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AccessibilityWindowInfo) parcelObtain2.readTypedObject(AccessibilityWindowInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public AccessibilityWindowInfo.WindowListSparseArray getWindows() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AccessibilityWindowInfo.WindowListSparseArray) parcelObtain2.readTypedObject(AccessibilityWindowInfo.WindowListSparseArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public AccessibilityServiceInfo getServiceInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AccessibilityServiceInfo) parcelObtain2.readTypedObject(AccessibilityServiceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public List<AccessibilityWindowInfo> getWindowsMainDisplay(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AccessibilityWindowInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean performGlobalAction(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public List<AccessibilityNodeInfo.AccessibilityAction> getSystemActions() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AccessibilityNodeInfo.AccessibilityAction.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void disableSelf() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setOnKeyEventResult(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public MagnificationConfig getMagnificationConfig(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MagnificationConfig) parcelObtain2.readTypedObject(MagnificationConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public float getMagnificationScale(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public float getMagnificationCenterX(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public float getMagnificationCenterY(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public Region getMagnificationRegion(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Region) parcelObtain2.readTypedObject(Region.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public Region getCurrentMagnificationRegion(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Region) parcelObtain2.readTypedObject(Region.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean resetMagnification(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean resetCurrentMagnification(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean setMagnificationConfig(int i, MagnificationConfig magnificationConfig, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(magnificationConfig, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setMagnificationCallbackEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean setSoftKeyboardShowMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public int getSoftKeyboardShowMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setSoftKeyboardCallbackEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean switchToInputMethod(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public int setInputMethodEnabled(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean isAccessibilityButtonAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void sendGesture(int i, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void dispatchGesture(int i, ParceledListSlice parceledListSlice, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public boolean isFingerprintGestureDetectionAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public IBinder getOverlayWindowToken(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public int getWindowIdForLeashToken(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void takeScreenshot(int i, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void takeScreenshotOfWindow(int i, int i2, ScreenCapture.ScreenCaptureListener screenCaptureListener, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(screenCaptureListener, 0);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setGestureDetectionPassthroughRegion(int i, Region region) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(region, 0);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setTouchExplorationPassthroughRegion(int i, Region region) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(region, 0);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setFocusAppearance(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setCacheEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void logTrace(long j, String str, long j2, String str2, int i, long j3, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(44, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setServiceDetectsGesturesEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void requestTouchExploration(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void requestDragging(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void requestDelegating(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void onDoubleTap(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void onDoubleTapAndHold(int i) throws RemoteException {
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

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setAnimationScale(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setInstalledAndEnabledServices(List<AccessibilityServiceInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public List<AccessibilityServiceInfo> getInstalledAndEnabledServices() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AccessibilityServiceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void attachAccessibilityOverlayToDisplay(int i, int i2, SurfaceControl surfaceControl, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void attachAccessibilityOverlayToWindow(int i, int i2, SurfaceControl surfaceControl, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    parcelObtain.writeStrongInterface(iAccessibilityInteractionConnectionCallback);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void connectBluetoothBrailleDisplay(String str, IBrailleDisplayController iBrailleDisplayController) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iBrailleDisplayController);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void connectUsbBrailleDisplay(UsbDevice usbDevice, IBrailleDisplayController iBrailleDisplayController) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(usbDevice, 0);
                    parcelObtain.writeStrongInterface(iBrailleDisplayController);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IAccessibilityServiceConnection
            public void setTestBrailleDisplayData(List<Bundle> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
