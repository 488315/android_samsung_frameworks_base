package android.credentials;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IClearCredentialStateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.credentials.IClearCredentialStateCallback";

    public static class Default implements IClearCredentialStateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.credentials.IClearCredentialStateCallback
        public void onError(String str, String str2) throws RemoteException {
        }

        @Override // android.credentials.IClearCredentialStateCallback
        public void onSuccess() throws RemoteException {
        }
    }

    void onError(String str, String str2) throws RemoteException;

    void onSuccess() throws RemoteException;

    public static abstract class Stub extends Binder implements IClearCredentialStateCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IClearCredentialStateCallback.DESCRIPTOR);
        }

        public static IClearCredentialStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IClearCredentialStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IClearCredentialStateCallback)) {
                return (IClearCredentialStateCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
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
                parcel.enforceInterface(IClearCredentialStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IClearCredentialStateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onSuccess();
            } else if (i == 2) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(readString, readString2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IClearCredentialStateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IClearCredentialStateCallback.DESCRIPTOR;
            }

            @Override // android.credentials.IClearCredentialStateCallback
            public void onSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IClearCredentialStateCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.credentials.IClearCredentialStateCallback
            public void onError(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IClearCredentialStateCallback.DESCRIPTOR);
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
