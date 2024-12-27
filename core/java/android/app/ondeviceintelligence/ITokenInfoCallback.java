package android.app.ondeviceintelligence;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ITokenInfoCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.ondeviceintelligence.ITokenInfoCallback";

    void onFailure(int i, String str, PersistableBundle persistableBundle) throws RemoteException;

    void onSuccess(TokenInfo tokenInfo) throws RemoteException;

    public static class Default implements ITokenInfoCallback {
        @Override // android.app.ondeviceintelligence.ITokenInfoCallback
        public void onSuccess(TokenInfo tokenInfo) throws RemoteException {}

        @Override // android.app.ondeviceintelligence.ITokenInfoCallback
        public void onFailure(int errorCode, String errorMessage, PersistableBundle errorParams)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ITokenInfoCallback {
        static final int TRANSACTION_onFailure = 3;
        static final int TRANSACTION_onSuccess = 2;

        public Stub() {
            attachInterface(this, ITokenInfoCallback.DESCRIPTOR);
        }

        public static ITokenInfoCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITokenInfoCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ITokenInfoCallback)) {
                return (ITokenInfoCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 2:
                    return "onSuccess";
                case 3:
                    return "onFailure";
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
                data.enforceInterface(ITokenInfoCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITokenInfoCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 2:
                    TokenInfo _arg0 = (TokenInfo) data.readTypedObject(TokenInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onSuccess(_arg0);
                    return true;
                case 3:
                    int _arg02 = data.readInt();
                    String _arg1 = data.readString();
                    PersistableBundle _arg2 =
                            (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    data.enforceNoDataAvail();
                    onFailure(_arg02, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITokenInfoCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITokenInfoCallback.DESCRIPTOR;
            }

            @Override // android.app.ondeviceintelligence.ITokenInfoCallback
            public void onSuccess(TokenInfo tokenInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITokenInfoCallback.DESCRIPTOR);
                    _data.writeTypedObject(tokenInfo, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.ITokenInfoCallback
            public void onFailure(int errorCode, String errorMessage, PersistableBundle errorParams)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITokenInfoCallback.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    _data.writeString(errorMessage);
                    _data.writeTypedObject(errorParams, 0);
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
