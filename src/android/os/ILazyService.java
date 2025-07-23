package android.os;

/* loaded from: classes3.dex */
public interface ILazyService extends IInterface {
    public static final String DESCRIPTOR = "android.os.ILazyService";

    public static class Default implements ILazyService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.ILazyService
        public IBinder getService(String str) throws RemoteException {
            return null;
        }
    }

    IBinder getService(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ILazyService {
        static final int TRANSACTION_getService = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ILazyService.DESCRIPTOR);
        }

        public static ILazyService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ILazyService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ILazyService)) {
                return (ILazyService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getService";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILazyService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILazyService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                IBinder service = getService(readString);
                parcel2.writeNoException();
                parcel2.writeStrongBinder(service);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ILazyService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILazyService.DESCRIPTOR;
            }

            @Override // android.os.ILazyService
            public IBinder getService(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ILazyService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
