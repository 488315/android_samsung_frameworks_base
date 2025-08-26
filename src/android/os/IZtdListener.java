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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IZtdListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IZtdListener)) {
                return (IZtdListener) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                String[] strArrCreateStringArray = parcel.createStringArray();
                parcel.enforceNoDataAvail();
                onSysDataTraced(i3, i4, strArrCreateStringArray);
                parcel2.writeNoException();
            } else if (i == 2) {
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                int i7 = parcel.readInt();
                long j = parcel.readLong();
                int i8 = parcel.readInt();
                int i9 = parcel.readInt();
                String string = parcel.readString();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onUnauthorizedAccessDetected(i5, i6, i7, j, i8, i9, string, string2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IZtdListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IZtdListener
            public void onUnauthorizedAccessDetected(int i, int i2, int i3, long j, int i4, int i5, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IZtdListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
