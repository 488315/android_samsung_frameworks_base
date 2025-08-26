package android.credentials;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IGetCredentialCallback extends IInterface {
    public static final String DESCRIPTOR = "android.credentials.IGetCredentialCallback";

    public static class Default implements IGetCredentialCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onError(String str, String str2) throws RemoteException {
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onPendingIntent(PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onResponse(GetCredentialResponse getCredentialResponse) throws RemoteException {
        }
    }

    void onError(String str, String str2) throws RemoteException;

    void onPendingIntent(PendingIntent pendingIntent) throws RemoteException;

    void onResponse(GetCredentialResponse getCredentialResponse) throws RemoteException;

    public static abstract class Stub extends Binder implements IGetCredentialCallback {
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
            attachInterface(this, IGetCredentialCallback.DESCRIPTOR);
        }

        public static IGetCredentialCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGetCredentialCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGetCredentialCallback)) {
                return (IGetCredentialCallback) iInterfaceQueryLocalInterface;
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
                parcel.enforceInterface(IGetCredentialCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGetCredentialCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                parcel.enforceNoDataAvail();
                onPendingIntent(pendingIntent);
            } else if (i == 2) {
                GetCredentialResponse getCredentialResponse = (GetCredentialResponse) parcel.readTypedObject(GetCredentialResponse.CREATOR);
                parcel.enforceNoDataAvail();
                onResponse(getCredentialResponse);
            } else if (i == 3) {
                String string = parcel.readString();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(string, string2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGetCredentialCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetCredentialCallback.DESCRIPTOR;
            }

            @Override // android.credentials.IGetCredentialCallback
            public void onPendingIntent(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGetCredentialCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.IGetCredentialCallback
            public void onResponse(GetCredentialResponse getCredentialResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGetCredentialCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(getCredentialResponse, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.credentials.IGetCredentialCallback
            public void onError(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGetCredentialCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
