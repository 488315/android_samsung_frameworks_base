package android.credentials;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISetEnabledProvidersCallback extends IInterface {
    public static final String DESCRIPTOR = "android.credentials.ISetEnabledProvidersCallback";

    public static class Default implements ISetEnabledProvidersCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.credentials.ISetEnabledProvidersCallback
        public void onError(String str, String str2) throws RemoteException {
        }

        @Override // android.credentials.ISetEnabledProvidersCallback
        public void onResponse() throws RemoteException {
        }
    }

    void onError(String str, String str2) throws RemoteException;

    void onResponse() throws RemoteException;

    public static abstract class Stub extends Binder implements ISetEnabledProvidersCallback {
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
            attachInterface(this, ISetEnabledProvidersCallback.DESCRIPTOR);
        }

        public static ISetEnabledProvidersCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISetEnabledProvidersCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISetEnabledProvidersCallback)) {
                return (ISetEnabledProvidersCallback) queryLocalInterface;
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
                parcel.enforceInterface(ISetEnabledProvidersCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISetEnabledProvidersCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onResponse();
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

        private static class Proxy implements ISetEnabledProvidersCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISetEnabledProvidersCallback.DESCRIPTOR;
            }

            @Override // android.credentials.ISetEnabledProvidersCallback
            public void onResponse() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISetEnabledProvidersCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ISetEnabledProvidersCallback
            public void onError(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISetEnabledProvidersCallback.DESCRIPTOR);
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
