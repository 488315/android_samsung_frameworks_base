package android.service.credentials;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

public interface IClearCredentialStateCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.service.credentials.IClearCredentialStateCallback";

    void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException;

    void onFailure(String str, CharSequence charSequence) throws RemoteException;

    void onSuccess() throws RemoteException;

    public static class Default implements IClearCredentialStateCallback {
        @Override // android.service.credentials.IClearCredentialStateCallback
        public void onSuccess() throws RemoteException {}

        @Override // android.service.credentials.IClearCredentialStateCallback
        public void onFailure(String errorType, CharSequence message) throws RemoteException {}

        @Override // android.service.credentials.IClearCredentialStateCallback
        public void onCancellable(ICancellationSignal cancellation) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IClearCredentialStateCallback {
        static final int TRANSACTION_onCancellable = 3;
        static final int TRANSACTION_onFailure = 2;
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onSuccess";
                case 2:
                    return "onFailure";
                case 3:
                    return "onCancellable";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IClearCredentialStateCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IClearCredentialStateCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onSuccess();
                    return true;
                case 2:
                    String _arg0 = data.readString();
                    CharSequence _arg1 =
                            (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    onFailure(_arg0, _arg1);
                    return true;
                case 3:
                    ICancellationSignal _arg02 =
                            ICancellationSignal.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onCancellable(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IClearCredentialStateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IClearCredentialStateCallback.DESCRIPTOR;
            }

            @Override // android.service.credentials.IClearCredentialStateCallback
            public void onSuccess() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IClearCredentialStateCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.credentials.IClearCredentialStateCallback
            public void onFailure(String errorType, CharSequence message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IClearCredentialStateCallback.DESCRIPTOR);
                    _data.writeString(errorType);
                    if (message != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(message, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.credentials.IClearCredentialStateCallback
            public void onCancellable(ICancellationSignal cancellation) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IClearCredentialStateCallback.DESCRIPTOR);
                    _data.writeStrongInterface(cancellation);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
