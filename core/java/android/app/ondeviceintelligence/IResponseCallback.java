package android.app.ondeviceintelligence;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IResponseCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.ondeviceintelligence.IResponseCallback";

    void onDataAugmentRequest(Bundle bundle, RemoteCallback remoteCallback) throws RemoteException;

    void onFailure(int i, String str, PersistableBundle persistableBundle) throws RemoteException;

    void onSuccess(Bundle bundle) throws RemoteException;

    public static class Default implements IResponseCallback {
        @Override // android.app.ondeviceintelligence.IResponseCallback
        public void onSuccess(Bundle resultBundle) throws RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IResponseCallback
        public void onFailure(int errorCode, String errorMessage, PersistableBundle errorParams) throws RemoteException {
        }

        @Override // android.app.ondeviceintelligence.IResponseCallback
        public void onDataAugmentRequest(Bundle processedContent, RemoteCallback responseCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IResponseCallback {
        static final int TRANSACTION_onDataAugmentRequest = 4;
        static final int TRANSACTION_onFailure = 3;
        static final int TRANSACTION_onSuccess = 2;

        public Stub() {
            attachInterface(this, IResponseCallback.DESCRIPTOR);
        }

        public static IResponseCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IResponseCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IResponseCallback)) {
                return (IResponseCallback) iin;
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
                case 4:
                    return "onDataAugmentRequest";
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
                data.enforceInterface(IResponseCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IResponseCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 2:
                    Bundle _arg0 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    onSuccess(_arg0);
                    return true;
                case 3:
                    int _arg02 = data.readInt();
                    String _arg1 = data.readString();
                    PersistableBundle _arg2 = (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    data.enforceNoDataAvail();
                    onFailure(_arg02, _arg1, _arg2);
                    return true;
                case 4:
                    Bundle _arg03 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    RemoteCallback _arg12 = (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    onDataAugmentRequest(_arg03, _arg12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IResponseCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResponseCallback.DESCRIPTOR;
            }

            @Override // android.app.ondeviceintelligence.IResponseCallback
            public void onSuccess(Bundle resultBundle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IResponseCallback.DESCRIPTOR);
                    _data.writeTypedObject(resultBundle, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IResponseCallback
            public void onFailure(int errorCode, String errorMessage, PersistableBundle errorParams) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IResponseCallback.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    _data.writeString(errorMessage);
                    _data.writeTypedObject(errorParams, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IResponseCallback
            public void onDataAugmentRequest(Bundle processedContent, RemoteCallback responseCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IResponseCallback.DESCRIPTOR);
                    _data.writeTypedObject(processedContent, 0);
                    _data.writeTypedObject(responseCallback, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
