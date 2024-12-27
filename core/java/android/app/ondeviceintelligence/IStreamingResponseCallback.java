package android.app.ondeviceintelligence;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;

public interface IStreamingResponseCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.app.ondeviceintelligence.IStreamingResponseCallback";

    void onDataAugmentRequest(Bundle bundle, RemoteCallback remoteCallback) throws RemoteException;

    void onFailure(int i, String str, PersistableBundle persistableBundle) throws RemoteException;

    void onNewContent(Bundle bundle) throws RemoteException;

    void onSuccess(Bundle bundle) throws RemoteException;

    public static class Default implements IStreamingResponseCallback {
        @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
        public void onNewContent(Bundle processedResult) throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
        public void onSuccess(Bundle result) throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
        public void onFailure(int errorCode, String errorMessage, PersistableBundle errorParams)
                throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
        public void onDataAugmentRequest(Bundle processedContent, RemoteCallback responseCallback)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IStreamingResponseCallback {
        static final int TRANSACTION_onDataAugmentRequest = 5;
        static final int TRANSACTION_onFailure = 4;
        static final int TRANSACTION_onNewContent = 2;
        static final int TRANSACTION_onSuccess = 3;

        public Stub() {
            attachInterface(this, IStreamingResponseCallback.DESCRIPTOR);
        }

        public static IStreamingResponseCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IStreamingResponseCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IStreamingResponseCallback)) {
                return (IStreamingResponseCallback) iin;
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
                    return "onNewContent";
                case 3:
                    return "onSuccess";
                case 4:
                    return "onFailure";
                case 5:
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
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IStreamingResponseCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IStreamingResponseCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 2:
                    Bundle _arg0 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    onNewContent(_arg0);
                    return true;
                case 3:
                    Bundle _arg02 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    onSuccess(_arg02);
                    return true;
                case 4:
                    int _arg03 = data.readInt();
                    String _arg1 = data.readString();
                    PersistableBundle _arg2 =
                            (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    data.enforceNoDataAvail();
                    onFailure(_arg03, _arg1, _arg2);
                    return true;
                case 5:
                    Bundle _arg04 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    RemoteCallback _arg12 =
                            (RemoteCallback) data.readTypedObject(RemoteCallback.CREATOR);
                    data.enforceNoDataAvail();
                    onDataAugmentRequest(_arg04, _arg12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IStreamingResponseCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStreamingResponseCallback.DESCRIPTOR;
            }

            @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
            public void onNewContent(Bundle processedResult) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStreamingResponseCallback.DESCRIPTOR);
                    _data.writeTypedObject(processedResult, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
            public void onSuccess(Bundle result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStreamingResponseCallback.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
            public void onFailure(int errorCode, String errorMessage, PersistableBundle errorParams)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStreamingResponseCallback.DESCRIPTOR);
                    _data.writeInt(errorCode);
                    _data.writeString(errorMessage);
                    _data.writeTypedObject(errorParams, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IStreamingResponseCallback
            public void onDataAugmentRequest(
                    Bundle processedContent, RemoteCallback responseCallback)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStreamingResponseCallback.DESCRIPTOR);
                    _data.writeTypedObject(processedContent, 0);
                    _data.writeTypedObject(responseCallback, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
