package android.credentials;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IPrepareGetCredentialCallback extends IInterface {
    public static final String DESCRIPTOR = "android.credentials.IPrepareGetCredentialCallback";

    void onError(String str, String str2) throws RemoteException;

    void onResponse(PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal) throws RemoteException;

    public static class Default implements IPrepareGetCredentialCallback {
        @Override // android.credentials.IPrepareGetCredentialCallback
        public void onResponse(PrepareGetCredentialResponseInternal response) throws RemoteException {
        }

        @Override // android.credentials.IPrepareGetCredentialCallback
        public void onError(String errorType, String message) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPrepareGetCredentialCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResponse = 1;

        public Stub() {
            attachInterface(this, IPrepareGetCredentialCallback.DESCRIPTOR);
        }

        public static IPrepareGetCredentialCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IPrepareGetCredentialCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IPrepareGetCredentialCallback)) {
                return (IPrepareGetCredentialCallback) iin;
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
                    return "onResponse";
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
                data.enforceInterface(IPrepareGetCredentialCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IPrepareGetCredentialCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            PrepareGetCredentialResponseInternal _arg0 = (PrepareGetCredentialResponseInternal) data.readTypedObject(PrepareGetCredentialResponseInternal.CREATOR);
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
        }

        private static class Proxy implements IPrepareGetCredentialCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPrepareGetCredentialCallback.DESCRIPTOR;
            }

            @Override // android.credentials.IPrepareGetCredentialCallback
            public void onResponse(PrepareGetCredentialResponseInternal response) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPrepareGetCredentialCallback.DESCRIPTOR);
                    _data.writeTypedObject(response, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.credentials.IPrepareGetCredentialCallback
            public void onError(String errorType, String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPrepareGetCredentialCallback.DESCRIPTOR);
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
