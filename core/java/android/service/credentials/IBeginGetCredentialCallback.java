package android.service.credentials;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;

public interface IBeginGetCredentialCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.service.credentials.IBeginGetCredentialCallback";

    void onCancellable(ICancellationSignal iCancellationSignal) throws RemoteException;

    void onFailure(String str, CharSequence charSequence) throws RemoteException;

    void onSuccess(BeginGetCredentialResponse beginGetCredentialResponse) throws RemoteException;

    public static class Default implements IBeginGetCredentialCallback {
        @Override // android.service.credentials.IBeginGetCredentialCallback
        public void onSuccess(BeginGetCredentialResponse response) throws RemoteException {}

        @Override // android.service.credentials.IBeginGetCredentialCallback
        public void onFailure(String errorType, CharSequence message) throws RemoteException {}

        @Override // android.service.credentials.IBeginGetCredentialCallback
        public void onCancellable(ICancellationSignal cancellation) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IBeginGetCredentialCallback {
        static final int TRANSACTION_onCancellable = 3;
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, IBeginGetCredentialCallback.DESCRIPTOR);
        }

        public static IBeginGetCredentialCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IBeginGetCredentialCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IBeginGetCredentialCallback)) {
                return (IBeginGetCredentialCallback) iin;
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
                data.enforceInterface(IBeginGetCredentialCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IBeginGetCredentialCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    BeginGetCredentialResponse _arg0 =
                            (BeginGetCredentialResponse)
                                    data.readTypedObject(BeginGetCredentialResponse.CREATOR);
                    data.enforceNoDataAvail();
                    onSuccess(_arg0);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    CharSequence _arg1 =
                            (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    data.enforceNoDataAvail();
                    onFailure(_arg02, _arg1);
                    return true;
                case 3:
                    ICancellationSignal _arg03 =
                            ICancellationSignal.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onCancellable(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBeginGetCredentialCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBeginGetCredentialCallback.DESCRIPTOR;
            }

            @Override // android.service.credentials.IBeginGetCredentialCallback
            public void onSuccess(BeginGetCredentialResponse response) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBeginGetCredentialCallback.DESCRIPTOR);
                    _data.writeTypedObject(response, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.credentials.IBeginGetCredentialCallback
            public void onFailure(String errorType, CharSequence message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBeginGetCredentialCallback.DESCRIPTOR);
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

            @Override // android.service.credentials.IBeginGetCredentialCallback
            public void onCancellable(ICancellationSignal cancellation) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBeginGetCredentialCallback.DESCRIPTOR);
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
