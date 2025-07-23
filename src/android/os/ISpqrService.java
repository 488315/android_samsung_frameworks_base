package android.os;

/* loaded from: classes3.dex */
public interface ISpqrService extends IInterface {
    public static final String DESCRIPTOR = "android.os.ISpqrService";

    public static class Default implements ISpqrService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.ISpqrService
        public boolean createInvariantProfile(String str, String str2, int i, int i2, String str3, String str4, String str5, String str6) throws RemoteException {
            return false;
        }
    }

    boolean createInvariantProfile(String str, String str2, int i, int i2, String str3, String str4, String str5, String str6) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpqrService {
        static final int TRANSACTION_createInvariantProfile = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISpqrService.DESCRIPTOR);
        }

        public static ISpqrService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISpqrService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISpqrService)) {
                return (ISpqrService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "createInvariantProfile";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISpqrService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISpqrService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                String readString3 = parcel.readString();
                String readString4 = parcel.readString();
                String readString5 = parcel.readString();
                String readString6 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean createInvariantProfile = createInvariantProfile(readString, readString2, readInt, readInt2, readString3, readString4, readString5, readString6);
                parcel2.writeNoException();
                parcel2.writeBoolean(createInvariantProfile);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISpqrService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpqrService.DESCRIPTOR;
            }

            @Override // android.os.ISpqrService
            public boolean createInvariantProfile(String str, String str2, int i, int i2, String str3, String str4, String str5, String str6) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpqrService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
