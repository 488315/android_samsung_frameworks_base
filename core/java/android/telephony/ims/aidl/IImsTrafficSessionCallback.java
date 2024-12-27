package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.feature.ConnectionFailureInfo;

/* loaded from: classes4.dex */
public interface IImsTrafficSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsTrafficSessionCallback";

    void onError(ConnectionFailureInfo connectionFailureInfo) throws RemoteException;

    void onReady() throws RemoteException;

    public static class Default implements IImsTrafficSessionCallback {
        @Override // android.telephony.ims.aidl.IImsTrafficSessionCallback
        public void onReady() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsTrafficSessionCallback
        public void onError(ConnectionFailureInfo info) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImsTrafficSessionCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onReady = 1;

        public Stub() {
            attachInterface(this, IImsTrafficSessionCallback.DESCRIPTOR);
        }

        public static IImsTrafficSessionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IImsTrafficSessionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IImsTrafficSessionCallback)) {
                return (IImsTrafficSessionCallback) iin;
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
                    return "onReady";
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
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IImsTrafficSessionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IImsTrafficSessionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onReady();
                    return true;
                case 2:
                    ConnectionFailureInfo _arg0 = (ConnectionFailureInfo) data.readTypedObject(ConnectionFailureInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onError(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IImsTrafficSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsTrafficSessionCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsTrafficSessionCallback
            public void onReady() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsTrafficSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsTrafficSessionCallback
            public void onError(ConnectionFailureInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsTrafficSessionCallback.DESCRIPTOR);
                    _data.writeTypedObject(info, 0);
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
