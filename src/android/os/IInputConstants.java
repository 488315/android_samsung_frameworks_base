package android.os;

/* loaded from: classes3.dex */
public interface IInputConstants extends IInterface {
    public static final int DEFAULT_MOUSE_WHEEL_ACCELERATION = 4;
    public static final int DEFAULT_POINTER_ACCELERATION = 3;
    public static final String DESCRIPTOR = "android.os.IInputConstants";
    public static final int DEVICE_CLASS_ALPHAKEY = 2;
    public static final int DEVICE_CLASS_BATTERY = 16384;
    public static final int DEVICE_CLASS_CURSOR = 8;
    public static final int DEVICE_CLASS_DPAD = 32;
    public static final int DEVICE_CLASS_EXTERNAL = 1073741824;
    public static final int DEVICE_CLASS_EXTERNAL_STYLUS = 2048;
    public static final int DEVICE_CLASS_GAMEPAD = 64;
    public static final int DEVICE_CLASS_JOYSTICK = 256;
    public static final int DEVICE_CLASS_KEYBOARD = 1;
    public static final int DEVICE_CLASS_LIGHT = 32768;
    public static final int DEVICE_CLASS_MIC = 1024;
    public static final int DEVICE_CLASS_ROTARY_ENCODER = 4096;
    public static final int DEVICE_CLASS_SENSOR = 8192;
    public static final int DEVICE_CLASS_SWITCH = 128;
    public static final int DEVICE_CLASS_TOUCH = 4;
    public static final int DEVICE_CLASS_TOUCHPAD = 65536;
    public static final int DEVICE_CLASS_TOUCH_MT = 16;
    public static final int DEVICE_CLASS_VIBRATOR = 512;
    public static final int DEVICE_CLASS_VIRTUAL = 536870912;
    public static final int INPUT_EVENT_FLAG_CANCELED = 32;
    public static final int INPUT_EVENT_FLAG_INJECTED_FROM_ACCESSIBILITY_TOOL = 4096;
    public static final int INPUT_EVENT_FLAG_IS_ACCESSIBILITY_EVENT = 2048;
    public static final int INPUT_EVENT_FLAG_TAINTED = Integer.MIN_VALUE;
    public static final int INVALID_BATTERY_CAPACITY = -1;
    public static final int INVALID_INPUT_DEVICE_ID = -2;
    public static final int INVALID_INPUT_EVENT_ID = 0;
    public static final int MOTION_EVENT_FLAG_BY_WHEEL_SCROLL_PAD = 1048576;
    public static final int MOTION_EVENT_FLAG_DISPATCH_WHEN_NON_INTERACTIVE = 2097152;
    public static final int MOTION_EVENT_FLAG_EVENT_BY_TWO_FINGER_GESTURE = 268435456;
    public static final int MOTION_EVENT_FLAG_FROM_WFD = 134217728;
    public static final int MOTION_EVENT_FLAG_INTERNAL_DISPLAY_FOR_USER_ACTIVITY = 67108864;
    public static final int MOTION_EVENT_FLAG_KEEP_DEVICE_ID = 4194304;
    public static final int MOTION_EVENT_FLAG_NOT_RESET_USER_ACTIVITY_TIMEOUT = 16777216;
    public static final int MOTION_EVENT_FLAG_SUPPORT_SPEN_ORIENTATION = 524288;
    public static final int MOTION_EVENT_FLAG_UP_PENDING = 33554432;
    public static final int MOTION_EVENT_FLAG_WINDOW_IS_ACCESSIBILITY = 8388608;
    public static final int MOTION_EVENT_FLAG_WINDOW_IS_EASYONEHAND = 536870912;
    public static final int POLICY_FLAG_INJECTED_FROM_ACCESSIBILITY = 131072;
    public static final int POLICY_FLAG_INJECTED_FROM_ACCESSIBILITY_TOOL = 524288;
    public static final int POLICY_FLAG_KEY_GESTURE_COMBINATION_TRIGGERED = 1048576;
    public static final int POLICY_FLAG_KEY_GESTURE_TRIGGERED = 262144;
    public static final int UNMULTIPLIED_DEFAULT_DISPATCHING_TIMEOUT_MILLIS = 10000;
    public static final int VELOCITY_TRACKER_STRATEGY_DEFAULT = -1;
    public static final int VELOCITY_TRACKER_STRATEGY_IMPULSE = 0;
    public static final int VELOCITY_TRACKER_STRATEGY_INT1 = 7;
    public static final int VELOCITY_TRACKER_STRATEGY_INT2 = 8;
    public static final int VELOCITY_TRACKER_STRATEGY_LEGACY = 9;
    public static final int VELOCITY_TRACKER_STRATEGY_LSQ1 = 1;
    public static final int VELOCITY_TRACKER_STRATEGY_LSQ2 = 2;
    public static final int VELOCITY_TRACKER_STRATEGY_LSQ3 = 3;
    public static final int VELOCITY_TRACKER_STRATEGY_WLSQ2_CENTRAL = 5;
    public static final int VELOCITY_TRACKER_STRATEGY_WLSQ2_DELTA = 4;
    public static final int VELOCITY_TRACKER_STRATEGY_WLSQ2_RECENT = 6;

    public static class Default implements IInputConstants {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInputConstants {
        public static String getDefaultTransactionName(int i) {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IInputConstants.DESCRIPTOR);
        }

        public static IInputConstants asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInputConstants.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInputConstants)) {
                return (IInputConstants) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IInputConstants.DESCRIPTOR);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IInputConstants {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputConstants.DESCRIPTOR;
            }
        }
    }
}
