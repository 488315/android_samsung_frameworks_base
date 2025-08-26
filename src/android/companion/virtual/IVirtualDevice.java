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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVirtualDevice.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVirtualDevice)) {
                return (IVirtualDevice) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int devicePolicy = getDevicePolicy(i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(devicePolicy);
                    return true;
                case 6:
                    boolean zHasCustomAudioInputSupport = hasCustomAudioInputSupport();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasCustomAudioInputSupport);
                    return true;
                case 7:
                    boolean zCanCreateMirrorDisplays = canCreateMirrorDisplays();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanCreateMirrorDisplays);
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
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDevicePolicy(i4, i5);
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
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDevicePolicyForDisplay(i6, i7, i8);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i9 = parcel.readInt();
                    IAudioRoutingCallback iAudioRoutingCallbackAsInterface = IAudioRoutingCallback.Stub.asInterface(parcel.readStrongBinder());
                    IAudioConfigChangedCallback iAudioConfigChangedCallbackAsInterface = IAudioConfigChangedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onAudioSessionStarting(i9, iAudioRoutingCallbackAsInterface, iAudioConfigChangedCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    onAudioSessionEnded();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    VirtualDisplayConfig virtualDisplayConfig = (VirtualDisplayConfig) parcel.readTypedObject(VirtualDisplayConfig.CREATOR);
                    IVirtualDisplayCallback iVirtualDisplayCallbackAsInterface = IVirtualDisplayCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iCreateVirtualDisplay = createVirtualDisplay(virtualDisplayConfig, iVirtualDisplayCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateVirtualDisplay);
                    return true;
                case 18:
                    VirtualDpadConfig virtualDpadConfig = (VirtualDpadConfig) parcel.readTypedObject(VirtualDpadConfig.CREATOR);
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualDpad(virtualDpadConfig, strongBinder);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    VirtualKeyboardConfig virtualKeyboardConfig = (VirtualKeyboardConfig) parcel.readTypedObject(VirtualKeyboardConfig.CREATOR);
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualKeyboard(virtualKeyboardConfig, strongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    VirtualMouseConfig virtualMouseConfig = (VirtualMouseConfig) parcel.readTypedObject(VirtualMouseConfig.CREATOR);
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualMouse(virtualMouseConfig, strongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    VirtualTouchscreenConfig virtualTouchscreenConfig = (VirtualTouchscreenConfig) parcel.readTypedObject(VirtualTouchscreenConfig.CREATOR);
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualTouchscreen(virtualTouchscreenConfig, strongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig = (VirtualNavigationTouchpadConfig) parcel.readTypedObject(VirtualNavigationTouchpadConfig.CREATOR);
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualNavigationTouchpad(virtualNavigationTouchpadConfig, strongBinder5);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    VirtualStylusConfig virtualStylusConfig = (VirtualStylusConfig) parcel.readTypedObject(VirtualStylusConfig.CREATOR);
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualStylus(virtualStylusConfig, strongBinder6);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    VirtualRotaryEncoderConfig virtualRotaryEncoderConfig = (VirtualRotaryEncoderConfig) parcel.readTypedObject(VirtualRotaryEncoderConfig.CREATOR);
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    createVirtualRotaryEncoder(virtualRotaryEncoderConfig, strongBinder7);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterInputDevice(strongBinder8);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    int inputDeviceId = getInputDeviceId(strongBinder9);
                    parcel2.writeNoException();
                    parcel2.writeInt(inputDeviceId);
                    return true;
                case 27:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    VirtualKeyEvent virtualKeyEvent = (VirtualKeyEvent) parcel.readTypedObject(VirtualKeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSendDpadKeyEvent = sendDpadKeyEvent(strongBinder10, virtualKeyEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendDpadKeyEvent);
                    return true;
                case 28:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    VirtualKeyEvent virtualKeyEvent2 = (VirtualKeyEvent) parcel.readTypedObject(VirtualKeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSendKeyEvent = sendKeyEvent(strongBinder11, virtualKeyEvent2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendKeyEvent);
                    return true;
                case 29:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    VirtualMouseButtonEvent virtualMouseButtonEvent = (VirtualMouseButtonEvent) parcel.readTypedObject(VirtualMouseButtonEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSendButtonEvent = sendButtonEvent(strongBinder12, virtualMouseButtonEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendButtonEvent);
                    return true;
                case 30:
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    VirtualMouseRelativeEvent virtualMouseRelativeEvent = (VirtualMouseRelativeEvent) parcel.readTypedObject(VirtualMouseRelativeEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSendRelativeEvent = sendRelativeEvent(strongBinder13, virtualMouseRelativeEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendRelativeEvent);
                    return true;
                case 31:
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    VirtualMouseScrollEvent virtualMouseScrollEvent = (VirtualMouseScrollEvent) parcel.readTypedObject(VirtualMouseScrollEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSendScrollEvent = sendScrollEvent(strongBinder14, virtualMouseScrollEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendScrollEvent);
                    return true;
                case 32:
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    VirtualTouchEvent virtualTouchEvent = (VirtualTouchEvent) parcel.readTypedObject(VirtualTouchEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSendTouchEvent = sendTouchEvent(strongBinder15, virtualTouchEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendTouchEvent);
                    return true;
                case 33:
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    VirtualStylusMotionEvent virtualStylusMotionEvent = (VirtualStylusMotionEvent) parcel.readTypedObject(VirtualStylusMotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSendStylusMotionEvent = sendStylusMotionEvent(strongBinder16, virtualStylusMotionEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendStylusMotionEvent);
                    return true;
                case 34:
                    IBinder strongBinder17 = parcel.readStrongBinder();
                    VirtualStylusButtonEvent virtualStylusButtonEvent = (VirtualStylusButtonEvent) parcel.readTypedObject(VirtualStylusButtonEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSendStylusButtonEvent = sendStylusButtonEvent(strongBinder17, virtualStylusButtonEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendStylusButtonEvent);
                    return true;
                case 35:
                    IBinder strongBinder18 = parcel.readStrongBinder();
                    VirtualRotaryEncoderScrollEvent virtualRotaryEncoderScrollEvent = (VirtualRotaryEncoderScrollEvent) parcel.readTypedObject(VirtualRotaryEncoderScrollEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSendRotaryEncoderScrollEvent = sendRotaryEncoderScrollEvent(strongBinder18, virtualRotaryEncoderScrollEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendRotaryEncoderScrollEvent);
                    return true;
                case 36:
                    List<VirtualSensor> virtualSensorList = getVirtualSensorList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(virtualSensorList, 1);
                    return true;
                case 37:
                    IBinder strongBinder19 = parcel.readStrongBinder();
                    VirtualSensorEvent virtualSensorEvent = (VirtualSensorEvent) parcel.readTypedObject(VirtualSensorEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSendSensorEvent = sendSensorEvent(strongBinder19, virtualSensorEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendSensorEvent);
                    return true;
                case 38:
                    IBinder strongBinder20 = parcel.readStrongBinder();
                    VirtualSensorAdditionalInfo virtualSensorAdditionalInfo = (VirtualSensorAdditionalInfo) parcel.readTypedObject(VirtualSensorAdditionalInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zSendSensorAdditionalInfo = sendSensorAdditionalInfo(strongBinder20, virtualSensorAdditionalInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendSensorAdditionalInfo);
                    return true;
                case 39:
                    int i10 = parcel.readInt();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    launchPendingIntent(i10, pendingIntent, resultReceiver);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IBinder strongBinder21 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    PointF cursorPosition = getCursorPosition(strongBinder21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cursorPosition, 1);
                    return true;
                case 41:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShowPointerIcon(z);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayImePolicy(i11, i12);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptorAsInterface = IVirtualDeviceIntentInterceptor.Stub.asInterface(parcel.readStrongBinder());
                    IntentFilter intentFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerIntentInterceptor(iVirtualDeviceIntentInterceptorAsInterface, intentFilter);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptorAsInterface2 = IVirtualDeviceIntentInterceptor.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterIntentInterceptor(iVirtualDeviceIntentInterceptorAsInterface2);
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
                    IVirtualDeviceActivityListener iVirtualDeviceActivityListenerAsInterface = IVirtualDeviceActivityListener.Stub.asInterface(parcel.readStrongBinder());
                    IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListenerAsInterface = IVirtualDeviceSoundEffectListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListeners(iVirtualDeviceActivityListenerAsInterface, iVirtualDeviceSoundEffectListenerAsInterface);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getDeviceId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public String getPersistentDeviceId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int[] getDisplayIds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getDevicePolicy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean hasCustomAudioInputSupport() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean canCreateMirrorDisplays() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void goToSleep() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void wakeUp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setDevicePolicy(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void addActivityPolicyExemption(ActivityPolicyExemption activityPolicyExemption) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(activityPolicyExemption, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void removeActivityPolicyExemption(ActivityPolicyExemption activityPolicyExemption) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(activityPolicyExemption, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setDevicePolicyForDisplay(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void onAudioSessionStarting(int i, IAudioRoutingCallback iAudioRoutingCallback, IAudioConfigChangedCallback iAudioConfigChangedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAudioRoutingCallback);
                    parcelObtain.writeStrongInterface(iAudioConfigChangedCallback);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void onAudioSessionEnded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int createVirtualDisplay(VirtualDisplayConfig virtualDisplayConfig, IVirtualDisplayCallback iVirtualDisplayCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(virtualDisplayConfig, 0);
                    parcelObtain.writeStrongInterface(iVirtualDisplayCallback);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualDpad(VirtualDpadConfig virtualDpadConfig, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(virtualDpadConfig, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualKeyboard(VirtualKeyboardConfig virtualKeyboardConfig, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(virtualKeyboardConfig, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualMouse(VirtualMouseConfig virtualMouseConfig, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(virtualMouseConfig, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualTouchscreen(VirtualTouchscreenConfig virtualTouchscreenConfig, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(virtualTouchscreenConfig, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualNavigationTouchpad(VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(virtualNavigationTouchpadConfig, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualStylus(VirtualStylusConfig virtualStylusConfig, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(virtualStylusConfig, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void createVirtualRotaryEncoder(VirtualRotaryEncoderConfig virtualRotaryEncoderConfig, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(virtualRotaryEncoderConfig, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void unregisterInputDevice(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public int getInputDeviceId(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendDpadKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(virtualKeyEvent, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendKeyEvent(IBinder iBinder, VirtualKeyEvent virtualKeyEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(virtualKeyEvent, 0);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendButtonEvent(IBinder iBinder, VirtualMouseButtonEvent virtualMouseButtonEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(virtualMouseButtonEvent, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendRelativeEvent(IBinder iBinder, VirtualMouseRelativeEvent virtualMouseRelativeEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(virtualMouseRelativeEvent, 0);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendScrollEvent(IBinder iBinder, VirtualMouseScrollEvent virtualMouseScrollEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(virtualMouseScrollEvent, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendTouchEvent(IBinder iBinder, VirtualTouchEvent virtualTouchEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(virtualTouchEvent, 0);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendStylusMotionEvent(IBinder iBinder, VirtualStylusMotionEvent virtualStylusMotionEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(virtualStylusMotionEvent, 0);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendStylusButtonEvent(IBinder iBinder, VirtualStylusButtonEvent virtualStylusButtonEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(virtualStylusButtonEvent, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendRotaryEncoderScrollEvent(IBinder iBinder, VirtualRotaryEncoderScrollEvent virtualRotaryEncoderScrollEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(virtualRotaryEncoderScrollEvent, 0);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public List<VirtualSensor> getVirtualSensorList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(VirtualSensor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendSensorEvent(IBinder iBinder, VirtualSensorEvent virtualSensorEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(virtualSensorEvent, 0);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public boolean sendSensorAdditionalInfo(IBinder iBinder, VirtualSensorAdditionalInfo virtualSensorAdditionalInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(virtualSensorAdditionalInfo, 0);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void launchPendingIntent(int i, PendingIntent pendingIntent, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public PointF getCursorPosition(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PointF) parcelObtain2.readTypedObject(PointF.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setShowPointerIcon(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setDisplayImePolicy(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void registerIntentInterceptor(IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor, IntentFilter intentFilter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVirtualDeviceIntentInterceptor);
                    parcelObtain.writeTypedObject(intentFilter, 0);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void unregisterIntentInterceptor(IVirtualDeviceIntentInterceptor iVirtualDeviceIntentInterceptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVirtualDeviceIntentInterceptor);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void registerVirtualCamera(VirtualCameraConfig virtualCameraConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(virtualCameraConfig, 0);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void unregisterVirtualCamera(VirtualCameraConfig virtualCameraConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(virtualCameraConfig, 0);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public String getVirtualCameraId(VirtualCameraConfig virtualCameraConfig) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(virtualCameraConfig, 0);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.companion.virtual.IVirtualDevice
            public void setListeners(IVirtualDeviceActivityListener iVirtualDeviceActivityListener, IVirtualDeviceSoundEffectListener iVirtualDeviceSoundEffectListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVirtualDevice.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iVirtualDeviceActivityListener);
                    parcelObtain.writeStrongInterface(iVirtualDeviceSoundEffectListener);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
