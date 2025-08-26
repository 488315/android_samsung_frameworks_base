package android.security.identity;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.identity.ICredentialStore;

/* loaded from: classes3.dex */
public interface ICredentialStoreFactory extends IInterface {
    public static final int CREDENTIAL_STORE_TYPE_DEFAULT = 0;
    public static final int CREDENTIAL_STORE_TYPE_DIRECT_ACCESS = 1;
    public static final String DESCRIPTOR = "android.security.identity.ICredentialStoreFactory";

    public static class Default implements ICredentialStoreFactory {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.identity.ICredentialStoreFactory
        public ICredentialStore getCredentialStore(int i) throws RemoteException {
            return null;
        }
    }

    ICredentialStore getCredentialStore(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ICredentialStoreFactory {
        static final int TRANSACTION_getCredentialStore = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ICredentialStoreFactory.DESCRIPTOR);
        }

        public static ICredentialStoreFactory asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICredentialStoreFactory.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICredentialStoreFactory)) {
                return (ICredentialStoreFactory) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getCredentialStore";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICredentialStoreFactory.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICredentialStoreFactory.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                ICredentialStore credentialStore = getCredentialStore(i3);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(credentialStore);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICredentialStoreFactory {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICredentialStoreFactory.DESCRIPTOR;
            }

            @Override // android.security.identity.ICredentialStoreFactory
            public ICredentialStore getCredentialStore(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredentialStoreFactory.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICredentialStore.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
