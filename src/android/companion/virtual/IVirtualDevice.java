package android.companion.virtual;

import android.app.PendingIntent;
import android.companion.virtual.IVirtualDeviceActivityListener;
import android.companion.virtual.IVirtualDeviceIntentInterceptor;
import android.companion.virtual.IVirtualDeviceSoundEffectListener;
import android.companion.virtual.audio.IAudioConfigChangedCallback;
import android.companion.virtual.audio.IAudioRoutingCallback;
import android.companion.virtual.camera.VirtualCameraConfig;
import android.companion.virtual.sensor.VirtualSensor;
import android.companion.virtual.sensor.VirtualSensorAdditionalInfo;
import android.companion.virtual.sensor.VirtualSensorEvent;
import android.content.IntentFilter;
import android.graphics.PointF;
import android.hardware.display.IVirtualDisplayCallback;
import android.hardware.display.VirtualDisplayConfig;
import android.hardware.input.VirtualDpadConfig;
import android.hardware.input.VirtualKeyEvent;
import android.hardware.input.VirtualKeyboardConfig;
import android.hardware.input.VirtualMouseButtonEvent;
import android.hardware.input.VirtualMouseConfig;
import android.hardware.input.VirtualMouseRelativeEvent;
import android.hardware.input.VirtualMouseScrollEvent;
import android.hardware.input.VirtualNavigationTouchpadConfig;
import android.hardware.input.VirtualRotaryEncoderConfig;
import android.hardware.input.VirtualRotaryEncoderScrollEvent;
import android.hardware.input.VirtualStylusButtonEvent;
import android.hardware.input.VirtualStylusConfig;
import android.hardware.input.VirtualStylusMotionEvent;
import android.hardware.input.VirtualTouchEvent;
import android.hardware.input.VirtualTouchscreenConfig;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import java.util.List;

/* loaded from: classes.dex */
public interface IVirtualDevice extends IInterface {
    public static final String DESCRIPTOR = "android.companion.virtual.IVirtualDevice";

    public static class Default implements IVirtualDevice {
        @Override // android.companion.virtual.IVirtualDevice
        public void addActivityPolicyExemption(ActivityPolicyExemption activityPolicyExemption) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean canCreateMirrorDisplays() throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void close() throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback iVirtualDisplayCallback) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualDpad(VirtualDpadConfig virtualDpadConfig, IBinder iBinder) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualKeyboard(VirtualKeyboardConfig virtualKeyboardConfig, IBinder iBinder) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualMouse(VirtualMouseConfig virtualMouseConfig, IBinder iBinder) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualNavigationTouchpad(VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig, IBinder iBinder) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualRotaryEncoder(VirtualRotaryEncoderConfig virtualRotaryEncoderConfig, IBinder iBinder) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualStylus(VirtualStylusConfig virtualStylusConfig, IBinder iBinder) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void createVirtualTouchscreen(VirtualTouchscreenConfig virtualTouchscreenConfig, IBinder iBinder) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int getAssociationId() throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public PointF getCursorPosition(IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int getDeviceId() throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int getDevicePolicy(int i) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int[] getDisplayIds() throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public int getInputDeviceId(IBinder iBinder) throws RemoteException {
            return 0;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public String getPersistentDeviceId() throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public String getVirtualCameraId(VirtualCameraConfig virtualCameraConfig) throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public List<VirtualSensor> getVirtualSensorList() throws RemoteException {
            return null;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void goToSleep() throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean hasCustomAudioInputSupport() throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void launchPendingIntent(int i, PendingIntent pendingIntent, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void onAudioSessionEnded() throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void onAudioSessionStarting(int i, IAudioRoutingCallback iAudioRoutingCallback, IAudioConfigChangedCallback iAudioConfigChangedCallback) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void registerIntentInterceptor(IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor, IntentFilter intentFilter) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void registerVirtualCamera(VirtualCameraConfig virtualCameraConfig) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void removeActivityPolicyExemption(ActivityPolicyExemption activityPolicyExemption) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendButtonEvent(IBinder iBinder, VirtualMouseButtonEvent virtualMouseButtonEvent) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendDpadKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendRelativeEvent(IBinder iBinder, VirtualMouseRelativeEvent virtualMouseRelativeEvent) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendRotaryEncoderScrollEvent(IBinder iBinder, VirtualRotaryEncoderScrollEvent virtualRotaryEncoderScrollEvent) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendScrollEvent(IBinder iBinder, VirtualMouseScrollEvent virtualMouseScrollEvent) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendSensorAdditionalInfo(IBinder iBinder, VirtualSensorAdditionalInfo virtualSensorAdditionalInfo) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendSensorEvent(IBinder iBinder, VirtualSensorEvent virtualSensorEvent) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendStylusButtonEvent(IBinder iBinder, VirtualStylusButtonEvent virtualStylusButtonEvent) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendStylusMotionEvent(IBinder iBinder, VirtualStylusMotionEvent virtualStylusMotionEvent) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public boolean sendTouchEvent(IBinder iBinder, VirtualTouchEvent virtualTouchEvent) throws RemoteException {
            return false;
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void setDevicePolicy(int i, int i2) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void setDevicePolicyForDisplay(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void setDisplayImePolicy(int i, int i2) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void setListeners(IVirtualDeviceActivityListener iVirtualDeviceActivityListener, IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void setShowPointerIcon(boolean z) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void unregisterInputDevice(IBinder iBinder) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void unregisterIntentInterceptor(IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void unregisterVirtualCamera(VirtualCameraConfig virtualCameraConfig) throws RemoteException {
        }

        @Override // android.companion.virtual.IVirtualDevice
        public void wakeUp() throws RemoteException {
        }
    }

    void addActivityPolicyExemption(ActivityPolicyExemption activityPolicyExemption) throws RemoteException;

    boolean canCreateMirrorDisplays() throws RemoteException;

    void close() throws RemoteException;

    int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback iVirtualDisplayCallback) throws RemoteException;

    void createVirtualDpad(VirtualDpadConfig virtualDpadConfig, IBinder iBinder) throws RemoteException;

    void createVirtualKeyboard(VirtualKeyboardConfig virtualKeyboardConfig, IBinder iBinder) throws RemoteException;

    void createVirtualMouse(VirtualMouseConfig virtualMouseConfig, IBinder iBinder) throws RemoteException;

    void createVirtualNavigationTouchpad(VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig, IBinder iBinder) throws RemoteException;

    void createVirtualRotaryEncoder(VirtualRotaryEncoderConfig virtualRotaryEncoderConfig, IBinder iBinder) throws RemoteException;

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

    void goToSleep() throws RemoteException;

    boolean hasCustomAudioInputSupport() throws RemoteException;

    void launchPendingIntent(int i, PendingIntent pendingIntent, ResultReceiver resultReceiver) throws RemoteException;

    void onAudioSessionEnded() throws RemoteException;

    void onAudioSessionStarting(int i, IAudioRoutingCallback iAudioRoutingCallback, IAudioConfigChangedCallback iAudioConfigChangedCallback) throws RemoteException;

    void registerIntentInterceptor(IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor, IntentFilter intentFilter) throws RemoteException;

    void registerVirtualCamera(VirtualCameraConfig virtualCameraConfig) throws RemoteException;

    void removeActivityPolicyExemption(ActivityPolicyExemption activityPolicyExemption) throws RemoteException;

    boolean sendButtonEvent(IBinder iBinder, VirtualMouseButtonEvent virtualMouseButtonEvent) throws RemoteException;

    boolean sendDpadKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) throws RemoteException;

    boolean sendKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) throws RemoteException;

    boolean sendRelativeEvent(IBinder iBinder, VirtualMouseRelativeEvent virtualMouseRelativeEvent) throws RemoteException;

    boolean sendRotaryEncoderScrollEvent(IBinder iBinder, VirtualRotaryEncoderScrollEvent virtualRotaryEncoderScrollEvent) throws RemoteException;

    boolean sendScrollEvent(IBinder iBinder, VirtualMouseScrollEvent virtualMouseScrollEvent) throws RemoteException;

    boolean sendSensorAdditionalInfo(IBinder iBinder, VirtualSensorAdditionalInfo virtualSensorAdditionalInfo) throws RemoteException;

    boolean sendSensorEvent(IBinder iBinder, VirtualSensorEvent virtualSensorEvent) throws RemoteException;

    boolean sendStylusButtonEvent(IBinder iBinder, VirtualStylusButtonEvent virtualStylusButtonEvent) throws RemoteException;

    boolean sendStylusMotionEvent(IBinder iBinder, VirtualStylusMotionEvent virtualStylusMotionEvent) throws RemoteException;

    boolean sendTouchEvent(IBinder iBinder, VirtualTouchEvent virtualTouchEvent) throws RemoteException;

    void setDevicePolicy(int i, int i2) throws RemoteException;

    void setDevicePolicyForDisplay(int i, int i2, int i3) throws RemoteException;

    void setDisplayImePolicy(int i, int i2) throws RemoteException;

    void setListeners(IVirtualDeviceActivityListener iVirtualDeviceActivityListener, IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener) throws RemoteException;

    void setShowPointerIcon(boolean z) throws RemoteException;

    void unregisterInputDevice(IBinder iBinder) throws RemoteException;

    void unregisterIntentInterceptor(IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor) throws RemoteException;

    void unregisterVirtualCamera(VirtualCameraConfig virtualCameraConfig) throws RemoteException;

    void wakeUp() throws RemoteException;

    public static abstract class Stub extends Binder implements IVirtualDevice {
        static final int TRANSACTION_addActivityPolicyExemption = 12;
        static final int TRANSACTION_canCreateMirrorDisplays = 7;
        static final int TRANSACTION_close = 10;
        static final int TRANSACTION_createVirtualDisplay = 17;
        static final int TRANSACTION_createVirtualDpad = 18;
        static final int TRANSACTION_createVirtualKeyboard = 19;
        static final int TRANSACTION_createVirtualMouse = 20;
        static final int TRANSACTION_createVirtualNavigationTouchpad = 22;
        static final int TRANSACTION_createVirtualRotaryEncoder = 24;
        static final int TRANSACTION_createVirtualStylus = 23;
        static final int TRANSACTION_createVirtualTouchscreen = 21;
        static final int TRANSACTION_getAssociationId = 1;
        static final int TRANSACTION_getCursorPosition = 40;
        static final int TRANSACTION_getDeviceId = 2;
        static final int TRANSACTION_getDevicePolicy = 5;
        static final int TRANSACTION_getDisplayIds = 4;
        static final int TRANSACTION_getInputDeviceId = 26;
        static final int TRANSACTION_getPersistentDeviceId = 3;
        static final int TRANSACTION_getVirtualCameraId = 47;
        static final int TRANSACTION_getVirtualSensorList = 36;
        static final int TRANSACTION_goToSleep = 8;
        static final int TRANSACTION_hasCustomAudioInputSupport = 6;
        static final int TRANSACTION_launchPendingIntent = 39;
        static final int TRANSACTION_onAudioSessionEnded = 16;
        static final int TRANSACTION_onAudioSessionStarting = 15;
        static final int TRANSACTION_registerIntentInterceptor = 43;
        static final int TRANSACTION_registerVirtualCamera = 45;
        static final int TRANSACTION_removeActivityPolicyExemption = 13;
        static final int TRANSACTION_sendButtonEvent = 29;
        static final int TRANSACTION_sendDpadKeyEvent = 27;
        static final int TRANSACTION_sendKeyEvent = 28;
        static final int TRANSACTION_sendRelativeEvent = 30;
        static final int TRANSACTION_sendRotaryEncoderScrollEvent = 35;
        static final int TRANSACTION_sendScrollEvent = 31;
        static final int TRANSACTION_sendSensorAdditionalInfo = 38;
        static final int TRANSACTION_sendSensorEvent = 37;
        static final int TRANSACTION_sendStylusButtonEvent = 34;
        static final int TRANSACTION_sendStylusMotionEvent = 33;
        static final int TRANSACTION_sendTouchEvent = 32;
        static final int TRANSACTION_setDevicePolicy = 11;
        static final int TRANSACTION_setDevicePolicyForDisplay = 14;
        static final int TRANSACTION_setDisplayImePolicy = 42;
        static final int TRANSACTION_setListeners = 48;
        static final int TRANSACTION_setShowPointerIcon = 41;
        static final int TRANSACTION_unregisterInputDevice = 25;
        static final int TRANSACTION_unregisterIntentInterceptor = 44;
        static final int TRANSACTION_unregisterVirtualCamera = 46;
        static final int TRANSACTION_wakeUp = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 47;
        }

        public Stub() {
            attachInterface(this, IVirtualDevice.DESCRIPTOR);
        }

        public static IVirtualDevice asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVirtualDevice.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVirtualDevice)) {
                return (IVirtualDevice) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "canCreateMirrorDisplays";
                case 8:
                    return "goToSleep";
                case 9:
                    return "wakeUp";
                case 10:
                    return "close";
                case 11:
                    return "setDevicePolicy";
                case 12:
                    return "addActivityPolicyExemption";
                case 13:
                    return "removeActivityPolicyExemption";
                case 14:
                    return "setDevicePolicyForDisplay";
                case 15:
                    return "onAudioSessionStarting";
                case 16:
                    return "onAudioSessionEnded";
                case 17:
                    return "createVirtualDisplay";
                case 18:
                    return "createVirtualDpad";
                case 19:
                    return "createVirtualKeyboard";
                case 20:
                    return "createVirtualMouse";
                case 21:
                    return "createVirtualTouchscreen";
                case 22:
                    return "createVirtualNavigationTouchpad";
                case 23:
                    return "createVirtualStylus";
                case 24:
                    return "createVirtualRotaryEncoder";
                case 25:
                    return "unregisterInputDevice";
                case 26:
                    return "getInputDeviceId";
                case 27:
                    return "sendDpadKeyEvent";
                case 28:
                    return "sendKeyEvent";
                case 29:
                    return "sendButtonEvent";
                case 30:
                    return "sendRelativeEvent";
                case 31:
                    return "sendScrollEvent";
                case 32:
                    return "sendTouchEvent";
                case 33:
                    return "sendStylusMotionEvent";
                case 34:
                    return "sendStylusButtonEvent";
                case 35:
                    return "sendRotaryEncoderScrollEvent";
                case 36:
                    return "getVirtualSensorList";
                case 37:
                    return "sendSensorEvent";
                case 38:
                    return "sendSensorAdditionalInfo";
                case 39:
                    return "launchPendingIntent";
                case 40:
                    return "getCursorPosition";
                case 41:
                    return "setShowPointerIcon";
                case 42:
                    return "setDisplayImePolicy";
                case 43:
                    return "registerIntentInterceptor";
                case 44:
                    return "unregisterIntentInterceptor";
                case 45:
                    return "registerVirtualCamera";
                case 46:
                    return "unregisterVirtualCamera";
                case 47:
                    return "getVirtualCameraId";
                case 48:
                    return "setListeners";
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
                parcel.enforceInterface(IVirtualDevice.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVirtualDevice.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int associationId = getAssociationId();
                    parcel2.writeNoException();
                    parcel2.writeInt(associationId);
                    return true;
                case 2:
                    int deviceId = getDeviceId();
                    parcel2.writeNoException();
                    parcel2.writeInt(deviceId);
                    return true;
                case 3:
                    String persistentDeviceId = getPersistentDeviceId();
                    parcel2.writeNoException();
                    parcel2.writeString(persistentDeviceId);
                    return true;
                case 4:
                    int[] displayIds = getDisplayIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(displayIds);
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int devicePolicy = getDevicePolicy(readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(devicePolicy);
                    return true;
                case 6:
                    boolean hasCustomAudioInputSupport = hasCustomAudioInputSupport();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasCustomAudioInputSupport);
                    return true;
                case 7:
                    boolean canCreateMirrorDisplays = canCreateMirrorDisplays();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canCreateMirrorDisplays);
                    return true;
                case 8:
                    goToSleep();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    wakeUp();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDevicePolicy(readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    ActivityPolicyExemption activityPolicyExemption = (ActivityPolicyExemption) parcel.readTypedObject(ActivityPolicyExemption.CREATOR);
                    parcel.enforceNoDataAvail();
                    addActivityPolicyExemption(activityPolicyExemption);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    ActivityPolicyExemption activityPolicyExemption2 = (ActivityPolicyExemption) parcel.readTypedObject(ActivityPolicyExemption.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeActivityPolicyExemption(activityPolicyExemption2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDevicePolicyForDisplay(readInt4, readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt7 = parcel.readInt();
                    IAudioRoutingCallback asInterface = IAudioRoutingCallback.Stub.asInterface(parcel.readStrongBinder());
                    IAudioConfigChangedCallback asInterface2 = IAudioConfigChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onAudioSessionStarting(readInt7, asInterface, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    onAudioSessionEnded();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    VirtualDisplayConfig virtualDisplayConfig = (VirtualDisplayConfig) parcel.readTypedObject(VirtualDisplayConfig.CREATOR);
                    IVirtualDisplayCallback asInterface3 = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int createVirtualDisplay = createVirtualDisplay(virtualDisplayConfig, asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(createVirtualDisplay);
                    return true;
                case 18:
                    VirtualDpadConfig virtualDpadConfig = (VirtualDpadConfig) parcel.readTypedObject(VirtualDpadConfig.CREATOR);
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualDpad(virtualDpadConfig, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    VirtualKeyboardConfig virtualKeyboardConfig = (VirtualKeyboardConfig) parcel.readTypedObject(VirtualKeyboardConfig.CREATOR);
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualKeyboard(virtualKeyboardConfig, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    VirtualMouseConfig virtualMouseConfig = (VirtualMouseConfig) parcel.readTypedObject(VirtualMouseConfig.CREATOR);
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualMouse(virtualMouseConfig, readStrongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    VirtualTouchscreenConfig virtualTouchscreenConfig = (VirtualTouchscreenConfig) parcel.readTypedObject(VirtualTouchscreenConfig.CREATOR);
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualTouchscreen(virtualTouchscreenConfig, readStrongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig = (VirtualNavigationTouchpadConfig) parcel.readTypedObject(VirtualNavigationTouchpadConfig.CREATOR);
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualNavigationTouchpad(virtualNavigationTouchpadConfig, readStrongBinder5);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    VirtualStylusConfig virtualStylusConfig = (VirtualStylusConfig) parcel.readTypedObject(VirtualStylusConfig.CREATOR);
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualStylus(virtualStylusConfig, readStrongBinder6);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    VirtualRotaryEncoderConfig virtualRotaryEncoderConfig = (VirtualRotaryEncoderConfig) parcel.readTypedObject(VirtualRotaryEncoderConfig.CREATOR);
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualRotaryEncoder(virtualRotaryEncoderConfig, readStrongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterInputDevice(readStrongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int inputDeviceId = getInputDeviceId(readStrongBinder9);
                    parcel2.writeNoException();
                    parcel2.writeInt(inputDeviceId);
                    return true;
                case 27:
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    VirtualKeyEvent virtualKeyEvent = (VirtualKeyEvent) parcel.readTypedObject(VirtualKeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendDpadKeyEvent = sendDpadKeyEvent(readStrongBinder10, virtualKeyEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendDpadKeyEvent);
                    return true;
                case 28:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    VirtualKeyEvent virtualKeyEvent2 = (VirtualKeyEvent) parcel.readTypedObject(VirtualKeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendKeyEvent = sendKeyEvent(readStrongBinder11, virtualKeyEvent2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendKeyEvent);
                    return true;
                case 29:
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    VirtualMouseButtonEvent virtualMouseButtonEvent = (VirtualMouseButtonEvent) parcel.readTypedObject(VirtualMouseButtonEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendButtonEvent = sendButtonEvent(readStrongBinder12, virtualMouseButtonEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendButtonEvent);
                    return true;
                case 30:
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    VirtualMouseRelativeEvent virtualMouseRelativeEvent = (VirtualMouseRelativeEvent) parcel.readTypedObject(VirtualMouseRelativeEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendRelativeEvent = sendRelativeEvent(readStrongBinder13, virtualMouseRelativeEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendRelativeEvent);
                    return true;
                case 31:
                    IBinder readStrongBinder14 = parcel.readStrongBinder();
                    VirtualMouseScrollEvent virtualMouseScrollEvent = (VirtualMouseScrollEvent) parcel.readTypedObject(VirtualMouseScrollEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendScrollEvent = sendScrollEvent(readStrongBinder14, virtualMouseScrollEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendScrollEvent);
                    return true;
                case 32:
                    IBinder readStrongBinder15 = parcel.readStrongBinder();
                    VirtualTouchEvent virtualTouchEvent = (VirtualTouchEvent) parcel.readTypedObject(VirtualTouchEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendTouchEvent = sendTouchEvent(readStrongBinder15, virtualTouchEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendTouchEvent);
                    return true;
                case 33:
                    IBinder readStrongBinder16 = parcel.readStrongBinder();
                    VirtualStylusMotionEvent virtualStylusMotionEvent = (VirtualStylusMotionEvent) parcel.readTypedObject(VirtualStylusMotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendStylusMotionEvent = sendStylusMotionEvent(readStrongBinder16, virtualStylusMotionEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendStylusMotionEvent);
                    return true;
                case 34:
                    IBinder readStrongBinder17 = parcel.readStrongBinder();
                    VirtualStylusButtonEvent virtualStylusButtonEvent = (VirtualStylusButtonEvent) parcel.readTypedObject(VirtualStylusButtonEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendStylusButtonEvent = sendStylusButtonEvent(readStrongBinder17, virtualStylusButtonEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendStylusButtonEvent);
                    return true;
                case 35:
                    IBinder readStrongBinder18 = parcel.readStrongBinder();
                    VirtualRotaryEncoderScrollEvent virtualRotaryEncoderScrollEvent = (VirtualRotaryEncoderScrollEvent) parcel.readTypedObject(VirtualRotaryEncoderScrollEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendRotaryEncoderScrollEvent = sendRotaryEncoderScrollEvent(readStrongBinder18, virtualRotaryEncoderScrollEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendRotaryEncoderScrollEvent);
                    return true;
                case 36:
                    List<VirtualSensor> virtualSensorList = getVirtualSensorList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(virtualSensorList, 1);
                    return true;
                case 37:
                    IBinder readStrongBinder19 = parcel.readStrongBinder();
                    VirtualSensorEvent virtualSensorEvent = (VirtualSensorEvent) parcel.readTypedObject(VirtualSensorEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendSensorEvent = sendSensorEvent(readStrongBinder19, virtualSensorEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendSensorEvent);
                    return true;
                case 38:
                    IBinder readStrongBinder20 = parcel.readStrongBinder();
                    VirtualSensorAdditionalInfo virtualSensorAdditionalInfo = (VirtualSensorAdditionalInfo) parcel.readTypedObject(VirtualSensorAdditionalInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendSensorAdditionalInfo = sendSensorAdditionalInfo(readStrongBinder20, virtualSensorAdditionalInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendSensorAdditionalInfo);
                    return true;
                case 39:
                    int readInt8 = parcel.readInt();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    launchPendingIntent(readInt8, pendingIntent, resultReceiver);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IBinder readStrongBinder21 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    PointF cursorPosition = getCursorPosition(readStrongBinder21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cursorPosition, 1);
                    return true;
                case 41:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShowPointerIcon(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayImePolicy(readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    IVirtualDeviceIntentInterceptor asInterface4 = IVirtualDeviceIntentInterceptor.Stub.asInterface(parcel.readStrongBinder());
                    IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerIntentInterceptor(asInterface4, intentFilter);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    IVirtualDeviceIntentInterceptor asInterface5 = IVirtualDeviceIntentInterceptor.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterIntentInterceptor(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    VirtualCameraConfig virtualCameraConfig = (VirtualCameraConfig) parcel.readTypedObject(VirtualCameraConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerVirtualCamera(virtualCameraConfig);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    VirtualCameraConfig virtualCameraConfig2 = (VirtualCameraConfig) parcel.readTypedObject(VirtualCameraConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterVirtualCamera(virtualCameraConfig2);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    VirtualCameraConfig virtualCameraConfig3 = (VirtualCameraConfig) parcel.readTypedObject(VirtualCameraConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    String virtualCameraId = getVirtualCameraId(virtualCameraConfig3);
                    parcel2.writeNoException();
                    parcel2.writeString(virtualCameraId);
                    return true;
                case 48:
                    IVirtualDeviceActivityListener asInterface6 = IVirtualDeviceActivityListener.Stub.asInterface(parcel.readStrongBinder());
                    IVirtualDeviceSoundEffectListener asInterface7 = IVirtualDeviceSoundEffectListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListeners(asInterface6, asInterface7);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVirtualDevice {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getDeviceId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public String getPersistentDeviceId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int[] getDisplayIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getDevicePolicy(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean hasCustomAudioInputSupport() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean canCreateMirrorDisplays() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void goToSleep() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void wakeUp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setDevicePolicy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void addActivityPolicyExemption(ActivityPolicyExemption activityPolicyExemption) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(activityPolicyExemption, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void removeActivityPolicyExemption(ActivityPolicyExemption activityPolicyExemption) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(activityPolicyExemption, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setDevicePolicyForDisplay(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void onAudioSessionStarting(int i, IAudioRoutingCallback iAudioRoutingCallback, IAudioConfigChangedCallback iAudioConfigChangedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAudioRoutingCallback);
                    obtain.writeStrongInterface(iAudioConfigChangedCallback);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void onAudioSessionEnded() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback iVirtualDisplayCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualDisplayConfig, 0);
                    obtain.writeStrongInterface(iVirtualDisplayCallback);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualDpad(VirtualDpadConfig virtualDpadConfig, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualDpadConfig, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualKeyboard(VirtualKeyboardConfig virtualKeyboardConfig, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualKeyboardConfig, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualMouse(VirtualMouseConfig virtualMouseConfig, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualMouseConfig, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualTouchscreen(VirtualTouchscreenConfig virtualTouchscreenConfig, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualTouchscreenConfig, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualNavigationTouchpad(VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualNavigationTouchpadConfig, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualStylus(VirtualStylusConfig virtualStylusConfig, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualStylusConfig, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualRotaryEncoder(VirtualRotaryEncoderConfig virtualRotaryEncoderConfig, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualRotaryEncoderConfig, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void unregisterInputDevice(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getInputDeviceId(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendDpadKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualKeyEvent, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualKeyEvent, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendButtonEvent(IBinder iBinder, VirtualMouseButtonEvent virtualMouseButtonEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualMouseButtonEvent, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendRelativeEvent(IBinder iBinder, VirtualMouseRelativeEvent virtualMouseRelativeEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualMouseRelativeEvent, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendScrollEvent(IBinder iBinder, VirtualMouseScrollEvent virtualMouseScrollEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualMouseScrollEvent, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendTouchEvent(IBinder iBinder, VirtualTouchEvent virtualTouchEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualTouchEvent, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendStylusMotionEvent(IBinder iBinder, VirtualStylusMotionEvent virtualStylusMotionEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualStylusMotionEvent, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendStylusButtonEvent(IBinder iBinder, VirtualStylusButtonEvent virtualStylusButtonEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualStylusButtonEvent, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendRotaryEncoderScrollEvent(IBinder iBinder, VirtualRotaryEncoderScrollEvent virtualRotaryEncoderScrollEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualRotaryEncoderScrollEvent, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public List<VirtualSensor> getVirtualSensorList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(VirtualSensor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendSensorEvent(IBinder iBinder, VirtualSensorEvent virtualSensorEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualSensorEvent, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendSensorAdditionalInfo(IBinder iBinder, VirtualSensorAdditionalInfo virtualSensorAdditionalInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(virtualSensorAdditionalInfo, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void launchPendingIntent(int i, PendingIntent pendingIntent, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public PointF getCursorPosition(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PointF) obtain2.readTypedObject(PointF.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setShowPointerIcon(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setDisplayImePolicy(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void registerIntentInterceptor(IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor, IntentFilter intentFilter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDeviceIntentInterceptor);
                    obtain.writeTypedObject(intentFilter, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void unregisterIntentInterceptor(IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDeviceIntentInterceptor);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void registerVirtualCamera(VirtualCameraConfig virtualCameraConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualCameraConfig, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void unregisterVirtualCamera(VirtualCameraConfig virtualCameraConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualCameraConfig, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public String getVirtualCameraId(VirtualCameraConfig virtualCameraConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeTypedObject(virtualCameraConfig, 0);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setListeners(IVirtualDeviceActivityListener iVirtualDeviceActivityListener, IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    obtain.writeStrongInterface(iVirtualDeviceActivityListener);
                    obtain.writeStrongInterface(iVirtualDeviceSoundEffectListener);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
