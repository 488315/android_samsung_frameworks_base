package android.service.ondeviceintelligence;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IProcessingUpdateStatusCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.ondeviceintelligence.IProcessingUpdateStatusCallback";

    void onFailure(int i, String str) throws RemoteException;

    void onSuccess(PersistableBundle persistableBundle) throws RemoteException;

    public static class Default implements IProcessingUpdateStatusCallback {
        @Override // android.service.ondeviceintelligence.IProcessingUpdateStatusCallback
        public void onSuccess(PersistableBundle statusParams) throws RemoteException {
        }

        @Override // android.service.ondeviceintelligence.IProcessingUpdateStatusCallback
        public void onFailure(int errorCode, String errorMessage) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IProcessingUpdateStatusCallback {
        static final int TRANSACTION_onFailure = 3;
        static final int TRANSACTION_onSuccess = 2;

        public Stub() {
            attachInterface(this, IProcessingUpdateStatusCallback.DESCRIPTOR);
        }

        public static IProcessingUpdateStatusCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IProcessingUpdateStatusCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IProcessingUpdateStatusCallback)) {
                return (IProcessingUpdateStatusCallback) iin;
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
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IProcessingUpdateStatusCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IProcessingUpdateStatusCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 2:
                    PersistableBundle _arg0 = (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    data.enforceNoDataAvail();
                    onSuccess(_arg0);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg02 = data.readInt();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    onFailure(_arg02, _arg1);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IProcessingUpdateStatusCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProcessingUpdateStatusCallback.DESCRIPTOR;
            }

            @Override // android.service.ondeviceintelligence.IProcessingUpdateStatusCallback
            public void onSuccess(PersistableBundle statusParams) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProcessingUpdateStatusCallback.DESCRIPTOR);
                    _data.writeTypedObject(statusParams, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.service.ondeviceintelligence.IProcessingUpdateStatusCallback
            public void onFailure(int errorCode, String errorMessage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProcessingUpdateStatusCallback.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    _data.writeString(errorMessage);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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
