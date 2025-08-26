package android.media.tv.extension.clienttoken;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IClientToken extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.clienttoken.IClientToken";

    public static class Default implements IClientToken {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.clienttoken.IClientToken
        public String generateClientToken() throws RemoteException {
            return null;
        }
    }

    String generateClientToken() throws RemoteException;

    public static abstract class Stub extends Binder implements IClientToken {
        static final int TRANSACTION_generateClientToken = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.clienttoken.IClientToken");
        }

        public static IClientToken asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.clienttoken.IClientToken");
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IClientToken)) {
                return (IClientToken) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "generateClientToken";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.clienttoken.IClientToken");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.clienttoken.IClientToken");
                return true;
            }
            if (i == 1) {
                String strGenerateClientToken = generateClientToken();
                parcel2.writeNoException();
                parcel2.writeString(strGenerateClientToken);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IClientToken {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.clienttoken.IClientToken";
            }

            @Override // android.media.tv.extension.clienttoken.IClientToken
            public String generateClientToken() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.media.tv.extension.clienttoken.IClientToken");
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
