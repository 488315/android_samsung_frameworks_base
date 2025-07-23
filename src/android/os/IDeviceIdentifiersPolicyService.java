package android.os;

/* loaded from: classes3.dex */
public interface IDeviceIdentifiersPolicyService extends IInterface {

    public static class Default implements IDeviceIdentifiersPolicyService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IDeviceIdentifiersPolicyService
        public String getSerial() throws RemoteException {
            return null;
        }

        @Override // android.os.IDeviceIdentifiersPolicyService
        public String getSerialForPackage(String str, String str2) throws RemoteException {
            return null;
        }
    }

    String getSerial() throws RemoteException;

    String getSerialForPackage(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IDeviceIdentifiersPolicyService {
        public static final String DESCRIPTOR = "android.os.IDeviceIdentifiersPolicyService";
        static final int TRANSACTION_getSerial = 1;
        static final int TRANSACTION_getSerialForPackage = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDeviceIdentifiersPolicyService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDeviceIdentifiersPolicyService)) {
                return (IDeviceIdentifiersPolicyService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getSerial";
            }
            if (i != 2) {
                return null;
            }
            return "getSerialForPackage";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String serial = getSerial();
                parcel2.writeNoException();
                parcel2.writeString(serial);
            } else if (i == 2) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                String serialForPackage = getSerialForPackage(readString, readString2);
                parcel2.writeNoException();
                parcel2.writeString(serialForPackage);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDeviceIdentifiersPolicyService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.os.IDeviceIdentifiersPolicyService
            public String getSerial() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdentifiersPolicyService
            public String getSerialForPackage(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
