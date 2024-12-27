package android.os;

/* loaded from: classes3.dex */
public interface IZtdListener extends IInterface {
    public static final String DESCRIPTOR = "android.os.IZtdListener";

    void onSysDataTraced(int i, int i2, String[] strArr) throws RemoteException;

    void onUnauthorizedAccessDetected(
            int i, int i2, int i3, long j, int i4, int i5, String str, String str2)
            throws RemoteException;

    public static class Default implements IZtdListener {
        @Override // android.os.IZtdListener
        public void onSysDataTraced(int traceType, int resultCode, String[] dataList)
                throws RemoteException {}

        @Override // android.os.IZtdListener
        public void onUnauthorizedAccessDetected(
                int traceClass,
                int traceSystem,
                int traceEvent,
                long accessTime,
                int accessorUid,
                int accessorPid,
                String accessorName,
                String objectName)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IZtdListener {
        static final int TRANSACTION_onSysDataTraced = 1;
        static final int TRANSACTION_onUnauthorizedAccessDetected = 2;

        public Stub() {
            attachInterface(this, IZtdListener.DESCRIPTOR);
        }

        public static IZtdListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IZtdListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IZtdListener)) {
                return (IZtdListener) iin;
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
                    return "onSysDataTraced";
                case 2:
                    return "onUnauthorizedAccessDetected";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IZtdListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IZtdListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    String[] _arg2 = data.createStringArray();
                    data.enforceNoDataAvail();
                    onSysDataTraced(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    int _arg22 = data.readInt();
                    long _arg3 = data.readLong();
                    int _arg4 = data.readInt();
                    int _arg5 = data.readInt();
                    String _arg6 = data.readString();
                    String _arg7 = data.readString();
                    data.enforceNoDataAvail();
                    onUnauthorizedAccessDetected(
                            _arg02, _arg12, _arg22, _arg3, _arg4, _arg5, _arg6, _arg7);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IZtdListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IZtdListener.DESCRIPTOR;
            }

            @Override // android.os.IZtdListener
            public void onSysDataTraced(int traceType, int resultCode, String[] dataList)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IZtdListener.DESCRIPTOR);
                    _data.writeInt(traceType);
                    _data.writeInt(resultCode);
                    _data.writeStringArray(dataList);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.IZtdListener
            public void onUnauthorizedAccessDetected(
                    int traceClass,
                    int traceSystem,
                    int traceEvent,
                    long accessTime,
                    int accessorUid,
                    int accessorPid,
                    String accessorName,
                    String objectName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IZtdListener.DESCRIPTOR);
                    _data.writeInt(traceClass);
                    _data.writeInt(traceSystem);
                    _data.writeInt(traceEvent);
                    _data.writeLong(accessTime);
                    _data.writeInt(accessorUid);
                    _data.writeInt(accessorPid);
                    _data.writeString(accessorName);
                    _data.writeString(objectName);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
