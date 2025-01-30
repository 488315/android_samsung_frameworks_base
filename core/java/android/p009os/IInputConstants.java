package android.p009os;

/* loaded from: classes3.dex */
public interface IInputConstants extends IInterface {
    public static final int DEFAULT_POINTER_ACCELERATION = 3;
    public static final String DESCRIPTOR = "android.os.IInputConstants";
    public static final int INPUT_EVENT_FLAG_IS_ACCESSIBILITY_EVENT = 2048;
    public static final int INVALID_BATTERY_CAPACITY = -1;
    public static final int INVALID_INPUT_DEVICE_ID = -2;
    public static final int INVALID_INPUT_EVENT_ID = 0;
    public static final int POLICY_FLAG_INJECTED_FROM_ACCESSIBILITY = 131072;
    public static final int UNMULTIPLIED_DEFAULT_DISPATCHING_TIMEOUT_MILLIS = 10000;

    public static class Default implements IInputConstants {
        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInputConstants {
        public Stub() {
            attachInterface(this, IInputConstants.DESCRIPTOR);
        }

        public static IInputConstants asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInputConstants.DESCRIPTOR);
            if (iin != null && (iin instanceof IInputConstants)) {
                return (IInputConstants) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            return null;
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IInputConstants.DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInputConstants {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputConstants.DESCRIPTOR;
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
