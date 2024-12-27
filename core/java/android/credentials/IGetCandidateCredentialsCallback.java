package android.credentials;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IGetCandidateCredentialsCallback extends IInterface {
    public static final String DESCRIPTOR = "android.credentials.IGetCandidateCredentialsCallback";

    void onError(String str, String str2) throws RemoteException;

    void onResponse(GetCandidateCredentialsResponse getCandidateCredentialsResponse)
            throws RemoteException;

    public static class Default implements IGetCandidateCredentialsCallback {
        @Override // android.credentials.IGetCandidateCredentialsCallback
        public void onResponse(GetCandidateCredentialsResponse response) throws RemoteException {}

        @Override // android.credentials.IGetCandidateCredentialsCallback
        public void onError(String errorType, String message) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IGetCandidateCredentialsCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResponse = 1;

        public Stub() {
            attachInterface(this, IGetCandidateCredentialsCallback.DESCRIPTOR);
        }

        public static IGetCandidateCredentialsCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGetCandidateCredentialsCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IGetCandidateCredentialsCallback)) {
                return (IGetCandidateCredentialsCallback) iin;
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
                    return "onResponse";
                case 2:
                    return "onError";
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
                data.enforceInterface(IGetCandidateCredentialsCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGetCandidateCredentialsCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    GetCandidateCredentialsResponse _arg0 =
                            (GetCandidateCredentialsResponse)
                                    data.readTypedObject(GetCandidateCredentialsResponse.CREATOR);
                    data.enforceNoDataAvail();
                    onResponse(_arg0);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    onError(_arg02, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGetCandidateCredentialsCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetCandidateCredentialsCallback.DESCRIPTOR;
            }

            @Override // android.credentials.IGetCandidateCredentialsCallback
            public void onResponse(GetCandidateCredentialsResponse response)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGetCandidateCredentialsCallback.DESCRIPTOR);
                    _data.writeTypedObject(response, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.credentials.IGetCandidateCredentialsCallback
            public void onError(String errorType, String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGetCandidateCredentialsCallback.DESCRIPTOR);
                    _data.writeString(errorType);
                    _data.writeString(message);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
