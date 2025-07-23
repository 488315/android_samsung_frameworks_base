package android.os;

/* loaded from: classes3.dex */
public interface IIncidentDumpCallback extends IInterface {
    public static final String DESCRIPTOR = "android.os.IIncidentDumpCallback";

    public static class Default implements IIncidentDumpCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IIncidentDumpCallback
        public void onDumpSection(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }
    }

    void onDumpSection(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    public static abstract class Stub extends Binder implements IIncidentDumpCallback {
        static final int TRANSACTION_onDumpSection = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IIncidentDumpCallback.DESCRIPTOR);
        }

        public static IIncidentDumpCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIncidentDumpCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIncidentDumpCallback)) {
                return (IIncidentDumpCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onDumpSection";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIncidentDumpCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIncidentDumpCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                parcel.enforceNoDataAvail();
                onDumpSection(parcelFileDescriptor);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IIncidentDumpCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIncidentDumpCallback.DESCRIPTOR;
            }

            @Override // android.os.IIncidentDumpCallback
            public void onDumpSection(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIncidentDumpCallback.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
