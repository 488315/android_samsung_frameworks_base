package android.companion.virtual;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.companion.virtual.IVirtualDeviceIntentInterceptor;
import android.companion.virtual.audio.IAudioConfigChangedCallback;
import android.companion.virtual.audio.IAudioRoutingCallback;
import android.companion.virtual.camera.VirtualCameraConfig;
import android.companion.virtual.sensor.VirtualSensor;
import android.companion.virtual.sensor.VirtualSensorEvent;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.graphics.PointF;
import android.hardware.input.VirtualDpadConfig;
import android.hardware.input.VirtualKeyEvent;
import android.hardware.input.VirtualKeyboardConfig;
import android.hardware.input.VirtualMouseButtonEvent;
import android.hardware.input.VirtualMouseConfig;
import android.hardware.input.VirtualMouseRelativeEvent;
import android.hardware.input.VirtualMouseScrollEvent;
import android.hardware.input.VirtualNavigationTouchpadConfig;
import android.hardware.input.VirtualStylusButtonEvent;
import android.hardware.input.VirtualStylusConfig;
import android.hardware.input.VirtualStylusMotionEvent;
import android.hardware.input.VirtualTouchEvent;
import android.hardware.input.VirtualTouchscreenConfig;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.os.ResultReceiver;
import java.util.List;

/* loaded from: classes.dex */
public interface IVirtualDevice extends IInterface {
    public static final String DESCRIPTOR = "android.companion.virtual.IVirtualDevice";

    void addActivityPolicyExemption(ComponentName componentName) throws RemoteException;

    void close() throws RemoteException;

    void createVirtualDpad(VirtualDpadConfig virtualDpadConfig, IBinder iBinder) throws RemoteException;

    void createVirtualKeyboard(VirtualKeyboardConfig virtualKeyboardConfig, IBinder iBinder) throws RemoteException;

    void createVirtualMouse(VirtualMouseConfig virtualMouseConfig, IBinder iBinder) throws RemoteException;

    void createVirtualNavigationTouchpad(VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig, IBinder iBinder) throws RemoteException;

    void createVirtualStylus(VirtualStylusConfig virtualStylusConfig, IBinder iBinder) throws RemoteException;

    void createVirtualTouchscreen(VirtualTouchscreenConfig virtualTouchscreenConfig, IBinder iBinder) throws RemoteException;

    int getAssociationId() throws RemoteException;

    PointF getCursorPosition(IBinder iBinder) throws RemoteException;

    int getDeviceId() throws RemoteException;

    int getDevicePolicy(int i) throws RemoteException;

    int[] getDisplayIds() throws RemoteException;

    int getInputDeviceId(IBinder iBinder) throws RemoteException;

    String getPersistentDeviceId() throws RemoteException;

    String getVirtualCameraId(VirtualCameraConfig virtualCameraConfig) throws RemoteException;

    List<VirtualSensor> getVirtualSensorList() throws RemoteException;

    boolean hasCustomAudioInputSupport() throws RemoteException;

    void launchPendingIntent(int i, PendingIntent pendingIntent, ResultReceiver resultReceiver) throws RemoteException;

    void onAudioSessionEnded() throws RemoteException;

    void onAudioSessionStarting(int i, IAudioRoutingCallback iAudioRoutingCallback, IAudioConfigChangedCallback iAudioConfigChangedCallback) throws RemoteException;

    void registerIntentInterceptor(IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor, IntentFilter intentFilter) throws RemoteException;

    void registerVirtualCamera(VirtualCameraConfig virtualCameraConfig) throws RemoteException;

    void removeActivityPolicyExemption(ComponentName componentName) throws RemoteException;

    boolean sendButtonEvent(IBinder iBinder, VirtualMouseButtonEvent virtualMouseButtonEvent) throws RemoteException;

    boolean sendDpadKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) throws RemoteException;

    boolean sendKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) throws RemoteException;

    boolean sendRelativeEvent(IBinder iBinder, VirtualMouseRelativeEvent virtualMouseRelativeEvent) throws RemoteException;

    boolean sendScrollEvent(IBinder iBinder, VirtualMouseScrollEvent virtualMouseScrollEvent) throws RemoteException;

    boolean sendSensorEvent(IBinder iBinder, VirtualSensorEvent virtualSensorEvent) throws RemoteException;

    boolean sendStylusButtonEvent(IBinder iBinder, VirtualStylusButtonEvent virtualStylusButtonEvent) throws RemoteException;

    boolean sendStylusMotionEvent(IBinder iBinder, VirtualStylusMotionEvent virtualStylusMotionEvent) throws RemoteException;

    boolean sendTouchEvent(IBinder iBinder, VirtualTouchEvent virtualTouchEvent) throws RemoteException;

    void setDevicePolicy(int i, int i2) throws RemoteException;

    void setDisplayImePolicy(int i, int i2) throws RemoteException;

    void setShowPointerIcon(boolean z) throws RemoteException;

    void unregisterInputDevice(IBinder iBinder) throws RemoteException;

    void unregisterIntentInterceptor(IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor) throws RemoteException;

    void unregisterVirtualCamera(VirtualCameraConfig virtualCameraConfig) throws RemoteException;

    public static class Default implements IVirtualDevice {
        @Override // android.companion.virtual.IVirtualDevice
        public int getAssociationId() throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int getDeviceId() throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public String getPersistentDeviceId() throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int[] getDisplayIds() throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int getDevicePolicy(int policyType) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean hasCustomAudioInputSupport() throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void close() throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void setDevicePolicy(int policyType, int devicePolicy) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void addActivityPolicyExemption(ComponentName exemption) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void removeActivityPolicyExemption(ComponentName exemption) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void onAudioSessionStarting(int displayId, IAudioRoutingCallback routingCallback, IAudioConfigChangedCallback configChangedCallback) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void onAudioSessionEnded() throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualDpad(VirtualDpadConfig config, IBinder token) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualKeyboard(VirtualKeyboardConfig config, IBinder token) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualMouse(VirtualMouseConfig config, IBinder token) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualTouchscreen(VirtualTouchscreenConfig config, IBinder token) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualNavigationTouchpad(VirtualNavigationTouchpadConfig config, IBinder token) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualStylus(VirtualStylusConfig config, IBinder token) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void unregisterInputDevice(IBinder token) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int getInputDeviceId(IBinder token) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendDpadKeyEvent(IBinder token, VirtualKeyEvent event) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendKeyEvent(IBinder token, VirtualKeyEvent event) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendButtonEvent(IBinder token, VirtualMouseButtonEvent event) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendRelativeEvent(IBinder token, VirtualMouseRelativeEvent event) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendScrollEvent(IBinder token, VirtualMouseScrollEvent event) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendTouchEvent(IBinder token, VirtualTouchEvent event) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendStylusMotionEvent(IBinder token, VirtualStylusMotionEvent event) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendStylusButtonEvent(IBinder token, VirtualStylusButtonEvent event) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public List<VirtualSensor> getVirtualSensorList() throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendSensorEvent(IBinder token, VirtualSensorEvent event) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void launchPendingIntent(int displayId, PendingIntent pendingIntent, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public PointF getCursorPosition(IBinder token) throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void setShowPointerIcon(boolean showPointerIcon) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void setDisplayImePolicy(int displayId, int policy) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void registerIntentInterceptor(IVirtualDeviceIntentInterceptor intentInterceptor, IntentFilter filter) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void unregisterIntentInterceptor(IVirtualDeviceIntentInterceptor intentInterceptor) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void registerVirtualCamera(VirtualCameraConfig camera) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void unregisterVirtualCamera(VirtualCameraConfig camera) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public String getVirtualCameraId(VirtualCameraConfig camera) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVirtualDevice {
        static final int TRANSACTION_addActivityPolicyExemption = 9;
        static final int TRANSACTION_close = 7;
        static final int TRANSACTION_createVirtualDpad = 13;
        static final int TRANSACTION_createVirtualKeyboard = 14;
        static final int TRANSACTION_createVirtualMouse = 15;
        static final int TRANSACTION_createVirtualNavigationTouchpad = 17;
        static final int TRANSACTION_createVirtualStylus = 18;
        static final int TRANSACTION_createVirtualTouchscreen = 16;
        static final int TRANSACTION_getAssociationId = 1;
        static final int TRANSACTION_getCursorPosition = 32;
        static final int TRANSACTION_getDeviceId = 2;
        static final int TRANSACTION_getDevicePolicy = 5;
        static final int TRANSACTION_getDisplayIds = 4;
        static final int TRANSACTION_getInputDeviceId = 20;
        static final int TRANSACTION_getPersistentDeviceId = 3;
        static final int TRANSACTION_getVirtualCameraId = 39;
        static final int TRANSACTION_getVirtualSensorList = 29;
        static final int TRANSACTION_hasCustomAudioInputSupport = 6;
        static final int TRANSACTION_launchPendingIntent = 31;
        static final int TRANSACTION_onAudioSessionEnded = 12;
        static final int TRANSACTION_onAudioSessionStarting = 11;
        static final int TRANSACTION_registerIntentInterceptor = 35;
        static final int TRANSACTION_registerVirtualCamera = 37;
        static final int TRANSACTION_removeActivityPolicyExemption = 10;
        static final int TRANSACTION_sendButtonEvent = 23;
        static final int TRANSACTION_sendDpadKeyEvent = 21;
        static final int TRANSACTION_sendKeyEvent = 22;
        static final int TRANSACTION_sendRelativeEvent = 24;
        static final int TRANSACTION_sendScrollEvent = 25;
        static final int TRANSACTION_sendSensorEvent = 30;
        static final int TRANSACTION_sendStylusButtonEvent = 28;
        static final int TRANSACTION_sendStylusMotionEvent = 27;
        static final int TRANSACTION_sendTouchEvent = 26;
        static final int TRANSACTION_setDevicePolicy = 8;
        static final int TRANSACTION_setDisplayImePolicy = 34;
        static final int TRANSACTION_setShowPointerIcon = 33;
        static final int TRANSACTION_unregisterInputDevice = 19;
        static final int TRANSACTION_unregisterIntentInterceptor = 36;
        static final int TRANSACTION_unregisterVirtualCamera = 38;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, IVirtualDevice.DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IVirtualDevice asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVirtualDevice.DESCRIPTOR);
            if (iin != null && (iin instanceof IVirtualDevice)) {
                return (IVirtualDevice) iin;
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
                    return "getAssociationId";
                case 2:
                    return "getDeviceId";
                case 3:
                    return "getPersistentDeviceId";
                case 4:
                    return "getDisplayIds";
                case 5:
                    return "getDevicePolicy";
                case 6:
                    return "hasCustomAudioInputSupport";
                case 7:
                    return "close";
                case 8:
                    return "setDevicePolicy";
                case 9:
                    return "addActivityPolicyExemption";
                case 10:
                    return "removeActivityPolicyExemption";
                case 11:
                    return "onAudioSessionStarting";
                case 12:
                    return "onAudioSessionEnded";
                case 13:
                    return "createVirtualDpad";
                case 14:
                    return "createVirtualKeyboard";
                case 15:
                    return "createVirtualMouse";
                case 16:
                    return "createVirtualTouchscreen";
                case 17:
                    return "createVirtualNavigationTouchpad";
                case 18:
                    return "createVirtualStylus";
                case 19:
                    return "unregisterInputDevice";
                case 20:
                    return "getInputDeviceId";
                case 21:
                    return "sendDpadKeyEvent";
                case 22:
                    return "sendKeyEvent";
                case 23:
                    return "sendButtonEvent";
                case 24:
                    return "sendRelativeEvent";
                case 25:
                    return "sendScrollEvent";
                case 26:
                    return "sendTouchEvent";
                case 27:
                    return "sendStylusMotionEvent";
                case 28:
                    return "sendStylusButtonEvent";
                case 29:
                    return "getVirtualSensorList";
                case 30:
                    return "sendSensorEvent";
                case 31:
                    return "launchPendingIntent";
                case 32:
                    return "getCursorPosition";
                case 33:
                    return "setShowPointerIcon";
                case 34:
                    return "setDisplayImePolicy";
                case 35:
                    return "registerIntentInterceptor";
                case 36:
                    return "unregisterIntentInterceptor";
                case 37:
                    return "registerVirtualCamera";
                case 38:
                    return "unregisterVirtualCamera";
                case 39:
                    return "getVirtualCameraId";
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
                data.enforceInterface(IVirtualDevice.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVirtualDevice.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _result = getAssociationId();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    int _result2 = getDeviceId();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 3:
                    String _result3 = getPersistentDeviceId();
                    reply.writeNoException();
                    reply.writeString(_result3);
                    return true;
                case 4:
                    int[] _result4 = getDisplayIds();
                    reply.writeNoException();
                    reply.writeIntArray(_result4);
                    return true;
                case 5:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result5 = getDevicePolicy(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 6:
                    boolean _result6 = hasCustomAudioInputSupport();
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 7:
                    close();
                    reply.writeNoException();
                    return true;
                case 8:
                    int _arg02 = data.readInt();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    setDevicePolicy(_arg02, _arg1);
                    reply.writeNoException();
                    return true;
                case 9:
                    ComponentName _arg03 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    addActivityPolicyExemption(_arg03);
                    reply.writeNoException();
                    return true;
                case 10:
                    ComponentName _arg04 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    removeActivityPolicyExemption(_arg04);
                    reply.writeNoException();
                    return true;
                case 11:
                    int _arg05 = data.readInt();
                    IAudioRoutingCallback _arg12 = IAudioRoutingCallback.Stub.asInterface(data.readStrongBinder());
                    IAudioConfigChangedCallback _arg2 = IAudioConfigChangedCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onAudioSessionStarting(_arg05, _arg12, _arg2);
                    reply.writeNoException();
                    return true;
                case 12:
                    onAudioSessionEnded();
                    reply.writeNoException();
                    return true;
                case 13:
                    VirtualDpadConfig _arg06 = (VirtualDpadConfig) data.readTypedObject(VirtualDpadConfig.CREATOR);
                    IBinder _arg13 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    createVirtualDpad(_arg06, _arg13);
                    reply.writeNoException();
                    return true;
                case 14:
                    VirtualKeyboardConfig _arg07 = (VirtualKeyboardConfig) data.readTypedObject(VirtualKeyboardConfig.CREATOR);
                    IBinder _arg14 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    createVirtualKeyboard(_arg07, _arg14);
                    reply.writeNoException();
                    return true;
                case 15:
                    VirtualMouseConfig _arg08 = (VirtualMouseConfig) data.readTypedObject(VirtualMouseConfig.CREATOR);
                    IBinder _arg15 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    createVirtualMouse(_arg08, _arg15);
                    reply.writeNoException();
                    return true;
                case 16:
                    VirtualTouchscreenConfig _arg09 = (VirtualTouchscreenConfig) data.readTypedObject(VirtualTouchscreenConfig.CREATOR);
                    IBinder _arg16 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    createVirtualTouchscreen(_arg09, _arg16);
                    reply.writeNoException();
                    return true;
                case 17:
                    VirtualNavigationTouchpadConfig _arg010 = (VirtualNavigationTouchpadConfig) data.readTypedObject(VirtualNavigationTouchpadConfig.CREATOR);
                    IBinder _arg17 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    createVirtualNavigationTouchpad(_arg010, _arg17);
                    reply.writeNoException();
                    return true;
                case 18:
                    VirtualStylusConfig _arg011 = (VirtualStylusConfig) data.readTypedObject(VirtualStylusConfig.CREATOR);
                    IBinder _arg18 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    createVirtualStylus(_arg011, _arg18);
                    reply.writeNoException();
                    return true;
                case 19:
                    IBinder _arg012 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    unregisterInputDevice(_arg012);
                    reply.writeNoException();
                    return true;
                case 20:
                    IBinder _arg013 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    int _result7 = getInputDeviceId(_arg013);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 21:
                    IBinder _arg014 = data.readStrongBinder();
                    VirtualKeyEvent _arg19 = (VirtualKeyEvent) data.readTypedObject(VirtualKeyEvent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result8 = sendDpadKeyEvent(_arg014, _arg19);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 22:
                    IBinder _arg015 = data.readStrongBinder();
                    VirtualKeyEvent _arg110 = (VirtualKeyEvent) data.readTypedObject(VirtualKeyEvent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result9 = sendKeyEvent(_arg015, _arg110);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 23:
                    IBinder _arg016 = data.readStrongBinder();
                    VirtualMouseButtonEvent _arg111 = (VirtualMouseButtonEvent) data.readTypedObject(VirtualMouseButtonEvent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result10 = sendButtonEvent(_arg016, _arg111);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 24:
                    IBinder _arg017 = data.readStrongBinder();
                    VirtualMouseRelativeEvent _arg112 = (VirtualMouseRelativeEvent) data.readTypedObject(VirtualMouseRelativeEvent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result11 = sendRelativeEvent(_arg017, _arg112);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 25:
                    IBinder _arg018 = data.readStrongBinder();
                    VirtualMouseScrollEvent _arg113 = (VirtualMouseScrollEvent) data.readTypedObject(VirtualMouseScrollEvent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result12 = sendScrollEvent(_arg018, _arg113);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 26:
                    IBinder _arg019 = data.readStrongBinder();
                    VirtualTouchEvent _arg114 = (VirtualTouchEvent) data.readTypedObject(VirtualTouchEvent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result13 = sendTouchEvent(_arg019, _arg114);
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 27:
                    IBinder _arg020 = data.readStrongBinder();
                    VirtualStylusMotionEvent _arg115 = (VirtualStylusMotionEvent) data.readTypedObject(VirtualStylusMotionEvent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result14 = sendStylusMotionEvent(_arg020, _arg115);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 28:
                    IBinder _arg021 = data.readStrongBinder();
                    VirtualStylusButtonEvent _arg116 = (VirtualStylusButtonEvent) data.readTypedObject(VirtualStylusButtonEvent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result15 = sendStylusButtonEvent(_arg021, _arg116);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 29:
                    List<VirtualSensor> _result16 = getVirtualSensorList();
                    reply.writeNoException();
                    reply.writeTypedList(_result16, 1);
                    return true;
                case 30:
                    IBinder _arg022 = data.readStrongBinder();
                    VirtualSensorEvent _arg117 = (VirtualSensorEvent) data.readTypedObject(VirtualSensorEvent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result17 = sendSensorEvent(_arg022, _arg117);
                    reply.writeNoException();
                    reply.writeBoolean(_result17);
                    return true;
                case 31:
                    int _arg023 = data.readInt();
                    PendingIntent _arg118 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    ResultReceiver _arg22 = (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    data.enforceNoDataAvail();
                    launchPendingIntent(_arg023, _arg118, _arg22);
                    reply.writeNoException();
                    return true;
                case 32:
                    IBinder _arg024 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    PointF _result18 = getCursorPosition(_arg024);
                    reply.writeNoException();
                    reply.writeTypedObject(_result18, 1);
                    return true;
                case 33:
                    boolean _arg025 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setShowPointerIcon(_arg025);
                    reply.writeNoException();
                    return true;
                case 34:
                    int _arg026 = data.readInt();
                    int _arg119 = data.readInt();
                    data.enforceNoDataAvail();
                    setDisplayImePolicy(_arg026, _arg119);
                    reply.writeNoException();
                    return true;
                case 35:
                    IVirtualDeviceIntentInterceptor _arg027 = IVirtualDeviceIntentInterceptor.Stub.asInterface(data.readStrongBinder());
                    IntentFilter _arg120 = (IntentFilter) data.readTypedObject(IntentFilter.CREATOR);
                    data.enforceNoDataAvail();
                    registerIntentInterceptor(_arg027, _arg120);
                    reply.writeNoException();
                    return true;
                case 36:
                    IVirtualDeviceIntentInterceptor _arg028 = IVirtualDeviceIntentInterceptor.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterIntentInterceptor(_arg028);
                    reply.writeNoException();
                    return true;
                case 37:
                    VirtualCameraConfig _arg029 = (VirtualCameraConfig) data.readTypedObject(VirtualCameraConfig.CREATOR);
                    data.enforceNoDataAvail();
                    registerVirtualCamera(_arg029);
                    reply.writeNoException();
                    return true;
                case 38:
                    VirtualCameraConfig _arg030 = (VirtualCameraConfig) data.readTypedObject(VirtualCameraConfig.CREATOR);
                    data.enforceNoDataAvail();
                    unregisterVirtualCamera(_arg030);
                    reply.writeNoException();
                    return true;
                case 39:
                    VirtualCameraConfig _arg031 = (VirtualCameraConfig) data.readTypedObject(VirtualCameraConfig.CREATOR);
                    data.enforceNoDataAvail();
                    String _result19 = getVirtualCameraId(_arg031);
                    reply.writeNoException();
                    reply.writeString(_result19);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVirtualDevice {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualDevice.DESCRIPTOR;
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getAssociationId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getDeviceId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public String getPersistentDeviceId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int[] getDisplayIds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getDevicePolicy(int policyType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeInt(policyType);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean hasCustomAudioInputSupport() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void close() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setDevicePolicy(int policyType, int devicePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeInt(policyType);
                    _data.writeInt(devicePolicy);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void addActivityPolicyExemption(ComponentName exemption) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeTypedObject(exemption, 0);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void removeActivityPolicyExemption(ComponentName exemption) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeTypedObject(exemption, 0);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void onAudioSessionStarting(int displayId, IAudioRoutingCallback routingCallback, IAudioConfigChangedCallback configChangedCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeStrongInterface(routingCallback);
                    _data.writeStrongInterface(configChangedCallback);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void onAudioSessionEnded() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualDpad(VirtualDpadConfig config, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualKeyboard(VirtualKeyboardConfig config, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualMouse(VirtualMouseConfig config, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualTouchscreen(VirtualTouchscreenConfig config, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualNavigationTouchpad(VirtualNavigationTouchpadConfig config, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualStylus(VirtualStylusConfig config, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeTypedObject(config, 0);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void unregisterInputDevice(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getInputDeviceId(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendDpadKeyEvent(IBinder token, VirtualKeyEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendKeyEvent(IBinder token, VirtualKeyEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendButtonEvent(IBinder token, VirtualMouseButtonEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendRelativeEvent(IBinder token, VirtualMouseRelativeEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendScrollEvent(IBinder token, VirtualMouseScrollEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendTouchEvent(IBinder token, VirtualTouchEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendStylusMotionEvent(IBinder token, VirtualStylusMotionEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendStylusButtonEvent(IBinder token, VirtualStylusButtonEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public List<VirtualSensor> getVirtualSensorList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    List<VirtualSensor> _result = _reply.createTypedArrayList(VirtualSensor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendSensorEvent(IBinder token, VirtualSensorEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeTypedObject(event, 0);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void launchPendingIntent(int displayId, PendingIntent pendingIntent, ResultReceiver resultReceiver) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeTypedObject(pendingIntent, 0);
                    _data.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public PointF getCursorPosition(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    PointF _result = (PointF) _reply.readTypedObject(PointF.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setShowPointerIcon(boolean showPointerIcon) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeBoolean(showPointerIcon);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setDisplayImePolicy(int displayId, int policy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(policy);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void registerIntentInterceptor(IVirtualDeviceIntentInterceptor intentInterceptor, IntentFilter filter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongInterface(intentInterceptor);
                    _data.writeTypedObject(filter, 0);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void unregisterIntentInterceptor(IVirtualDeviceIntentInterceptor intentInterceptor) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeStrongInterface(intentInterceptor);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void registerVirtualCamera(VirtualCameraConfig camera) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeTypedObject(camera, 0);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void unregisterVirtualCamera(VirtualCameraConfig camera) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeTypedObject(camera, 0);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public String getVirtualCameraId(VirtualCameraConfig camera) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    _data.writeTypedObject(camera, 0);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void close_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void setDevicePolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void addActivityPolicyExemption_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void removeActivityPolicyExemption_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void onAudioSessionStarting_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void onAudioSessionEnded_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void createVirtualDpad_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void createVirtualKeyboard_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void createVirtualMouse_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void createVirtualTouchscreen_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void createVirtualNavigationTouchpad_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void createVirtualStylus_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void unregisterInputDevice_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendDpadKeyEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendKeyEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendButtonEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendRelativeEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendScrollEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendTouchEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendStylusMotionEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendStylusButtonEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void getVirtualSensorList_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void sendSensorEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void setShowPointerIcon_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void setDisplayImePolicy_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void registerIntentInterceptor_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void unregisterIntentInterceptor_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void registerVirtualCamera_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void unregisterVirtualCamera_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        protected void getVirtualCameraId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.CREATE_VIRTUAL_DEVICE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 38;
        }
    }
}
