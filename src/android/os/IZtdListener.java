package android.os;

/* loaded from: classes3.dex */
public interface IZtdListener extends IInterface {
    public static final String DESCRIPTOR = "android.os.IZtdListener";

    public static class Default implements IZtdListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IZtdListener
        public void onSysDataTraced(int i, int i2, String[] strArr) throws RemoteException {
        }

        @Override // android.os.IZtdListener
        public void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2) throws RemoteException {
        }
    }

    void onSysDataTraced(int i, int i2, String[] strArr) throws RemoteException;

    void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IZtdListener {
        static final int TRANSACTION_onSysDataTraced = 1;
        static final int TRANSACTION_onUnauthorizedAccessDetected = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IZtdListener.DESCRIPTOR);
        }

        public static IZtdListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IZtdListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IZtdListener)) {
                return (IZtdListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSysDataTraced";
            }
            if (i != 2) {
                return null;
            }
            return "onUnauthorizedAccessDetected";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IZtdListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IZtdListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                String[] createStringArray = parcel.createStringArray();
                parcel.enforceNoDataAvail();
                onSysDataTraced(readInt, readInt2, createStringArray);
                parcel2.writeNoException();
            } else if (i == 2) {
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                long readLong = parcel.readLong();
                int readInt6 = parcel.readInt();
                int readInt7 = parcel.readInt();
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onUnauthorizedAccessDetected(readInt3, readInt4, readInt5, readLong, readInt6, readInt7, readString, readString2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IZtdListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IZtdListener.DESCRIPTOR;
            }

            @Override // android.os.IZtdListener
            public void onSysDataTraced(int i, int i2, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IZtdListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IZtdListener
            public void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IZtdListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLong(j);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
