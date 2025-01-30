package android.credentials;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IClearCredentialStateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.credentials.IClearCredentialStateCallback";

    void onError(String str, String str2) throws RemoteException;

    void onSuccess() throws RemoteException;

    public static class Default implements IClearCredentialStateCallback {
        @Override // android.credentials.IClearCredentialStateCallback
        public void onSuccess() throws RemoteException {
        }

        @Override // android.credentials.IClearCredentialStateCallback
        public void onError(String errorType, String message) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IClearCredentialStateCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, IClearCredentialStateCallback.DESCRIPTOR);
        }

        public static IClearCredentialStateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IClearCredentialStateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IClearCredentialStateCallback)) {
                return (IClearCredentialStateCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onSuccess";
                case 2:
                    return "onError";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IClearCredentialStateCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IClearCredentialStateCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onSuccess();
                            return true;
                        case 2:
                            String _arg0 = data.readString();
                            String _arg1 = data.readString();
                            data.enforceNoDataAvail();
                            onError(_arg0, _arg1);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IClearCredentialStateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IClearCredentialStateCallback.DESCRIPTOR;
            }

            @Override // android.credentials.IClearCredentialStateCallback
            public void onSuccess() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IClearCredentialStateCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.credentials.IClearCredentialStateCallback
            public void onError(String errorType, String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IClearCredentialStateCallback.DESCRIPTOR);
                    _data.writeString(errorType);
                    _data.writeString(message);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
