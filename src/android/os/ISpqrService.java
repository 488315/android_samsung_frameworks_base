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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISpqrService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISpqrService)) {
                return (ISpqrService) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                String string2 = parcel.readString();
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                String string3 = parcel.readString();
                String string4 = parcel.readString();
                String string5 = parcel.readString();
                String string6 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean zCreateInvariantProfile = createInvariantProfile(string, string2, i3, i4, string3, string4, string5, string6);
                parcel2.writeNoException();
                parcel2.writeBoolean(zCreateInvariantProfile);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpqrService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeString(str6);
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
