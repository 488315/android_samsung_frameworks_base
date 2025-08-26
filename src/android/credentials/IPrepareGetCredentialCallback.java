package android.credentials;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IPrepareGetCredentialCallback extends IInterface {
    public static final String DESCRIPTOR = "android.credentials.IPrepareGetCredentialCallback";

    public static class Default implements IPrepareGetCredentialCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.credentials.IPrepareGetCredentialCallback
        public void onError(String str, String str2) throws RemoteException {
        }

        @Override // android.credentials.IPrepareGetCredentialCallback
        public void onResponse(PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal) throws RemoteException {
        }
    }

    void onError(String str, String str2) throws RemoteException;

    void onResponse(PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal) throws RemoteException;

    public static abstract class Stub extends Binder implements IPrepareGetCredentialCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResponse = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IPrepareGetCredentialCallback.DESCRIPTOR);
        }

        public static IPrepareGetCredentialCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPrepareGetCredentialCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPrepareGetCredentialCallback)) {
                return (IPrepareGetCredentialCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onResponse";
            }
            if (i != 2) {
                return null;
            }
            return "onError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPrepareGetCredentialCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPrepareGetCredentialCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal = (PrepareGetCredentialResponseInternal) parcel.readTypedObject(PrepareGetCredentialResponseInternal.CREATOR);
                parcel.enforceNoDataAvail();
                onResponse(prepareGetCredentialResponseInternal);
            } else if (i == 2) {
                String string = parcel.readString();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(string, string2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPrepareGetCredentialCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPrepareGetCredentialCallback.DESCRIPTOR;
            }

            @Override // android.credentials.IPrepareGetCredentialCallback
            public void onResponse(PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPrepareGetCredentialCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(prepareGetCredentialResponseInternal, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.IPrepareGetCredentialCallback
            public void onError(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPrepareGetCredentialCallback.DESCRIPTOR);
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
