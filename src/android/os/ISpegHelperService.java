package android.os;

/* loaded from: classes3.dex */
public interface ISpegHelperService extends IInterface {
    public static final String DESCRIPTOR = "android.os.ISpegHelperService";

    public static class Default implements ISpegHelperService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.ISpegHelperService
        public boolean storePrimaryProf(String str, String str2, int i) throws RemoteException {
            return false;
        }
    }

    boolean storePrimaryProf(String str, String str2, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISpegHelperService {
        static final int TRANSACTION_storePrimaryProf = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISpegHelperService.DESCRIPTOR);
        }

        public static ISpegHelperService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISpegHelperService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISpegHelperService)) {
                return (ISpegHelperService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "storePrimaryProf";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISpegHelperService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISpegHelperService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                String string2 = parcel.readString();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean zStorePrimaryProf = storePrimaryProf(string, string2, i3);
                parcel2.writeNoException();
                parcel2.writeBoolean(zStorePrimaryProf);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISpegHelperService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISpegHelperService.DESCRIPTOR;
            }

            @Override // android.os.ISpegHelperService
            public boolean storePrimaryProf(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpegHelperService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
