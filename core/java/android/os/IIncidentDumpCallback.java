package android.os;

/* loaded from: classes3.dex */
public interface IIncidentDumpCallback extends IInterface {
    public static final String DESCRIPTOR = "android.os.IIncidentDumpCallback";

    void onDumpSection(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    public static class Default implements IIncidentDumpCallback {
        @Override // android.os.IIncidentDumpCallback
        public void onDumpSection(ParcelFileDescriptor fd) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IIncidentDumpCallback {
        static final int TRANSACTION_onDumpSection = 1;

        public Stub() {
            attachInterface(this, IIncidentDumpCallback.DESCRIPTOR);
        }

        public static IIncidentDumpCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IIncidentDumpCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IIncidentDumpCallback)) {
                return (IIncidentDumpCallback) iin;
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
                    return "onDumpSection";
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
                data.enforceInterface(IIncidentDumpCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IIncidentDumpCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ParcelFileDescriptor _arg0 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    data.enforceNoDataAvail();
                    onDumpSection(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IIncidentDumpCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIncidentDumpCallback.DESCRIPTOR;
            }

            @Override // android.os.IIncidentDumpCallback
            public void onDumpSection(ParcelFileDescriptor fd) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IIncidentDumpCallback.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
