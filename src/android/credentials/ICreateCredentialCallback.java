package android.credentials;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ICreateCredentialCallback extends IInterface {
    public static final String DESCRIPTOR = "android.credentials.ICreateCredentialCallback";

    public static class Default implements ICreateCredentialCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onError(String str, String str2) throws RemoteException {
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onPendingIntent(PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onResponse(CreateCredentialResponse createCredentialResponse) throws RemoteException {
        }
    }

    void onError(String str, String str2) throws RemoteException;

    void onPendingIntent(PendingIntent pendingIntent) throws RemoteException;

    void onResponse(CreateCredentialResponse createCredentialResponse) throws RemoteException;

    public static abstract class Stub extends Binder implements ICreateCredentialCallback {
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onPendingIntent = 1;
        static final int TRANSACTION_onResponse = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, ICreateCredentialCallback.DESCRIPTOR);
        }

        public static ICreateCredentialCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICreateCredentialCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICreateCredentialCallback)) {
                return (ICreateCredentialCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onPendingIntent";
            }
            if (i == 2) {
                return "onResponse";
            }
            if (i != 3) {
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
                parcel.enforceInterface(ICreateCredentialCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICreateCredentialCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                parcel.enforceNoDataAvail();
                onPendingIntent(pendingIntent);
            } else if (i == 2) {
                CreateCredentialResponse createCredentialResponse = (CreateCredentialResponse) parcel.readTypedObject(CreateCredentialResponse.CREATOR);
                parcel.enforceNoDataAvail();
                onResponse(createCredentialResponse);
            } else if (i == 3) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(readString, readString2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICreateCredentialCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICreateCredentialCallback.DESCRIPTOR;
            }

            @Override // android.credentials.ICreateCredentialCallback
            public void onPendingIntent(PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICreateCredentialCallback.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICreateCredentialCallback
            public void onResponse(CreateCredentialResponse createCredentialResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICreateCredentialCallback.DESCRIPTOR);
                    obtain.writeTypedObject(createCredentialResponse, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICreateCredentialCallback
            public void onError(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICreateCredentialCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
