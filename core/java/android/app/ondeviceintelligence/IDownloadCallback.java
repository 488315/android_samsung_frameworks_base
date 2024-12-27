package android.app.ondeviceintelligence;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IDownloadCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.ondeviceintelligence.IDownloadCallback";

    void onDownloadCompleted(PersistableBundle persistableBundle) throws RemoteException;

    void onDownloadFailed(int i, String str, PersistableBundle persistableBundle)
            throws RemoteException;

    void onDownloadProgress(long j) throws RemoteException;

    void onDownloadStarted(long j) throws RemoteException;

    public static class Default implements IDownloadCallback {
        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadStarted(long bytesToDownload) throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadProgress(long bytesDownloaded) throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadFailed(
                int failureStatus, String errorMessage, PersistableBundle errorParams)
                throws RemoteException {}

        @Override // android.app.ondeviceintelligence.IDownloadCallback
        public void onDownloadCompleted(PersistableBundle downloadParams) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDownloadCallback {
        static final int TRANSACTION_onDownloadCompleted = 5;
        static final int TRANSACTION_onDownloadFailed = 4;
        static final int TRANSACTION_onDownloadProgress = 3;
        static final int TRANSACTION_onDownloadStarted = 2;

        public Stub() {
            attachInterface(this, IDownloadCallback.DESCRIPTOR);
        }

        public static IDownloadCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDownloadCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IDownloadCallback)) {
                return (IDownloadCallback) iin;
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
                    return "onDownloadStarted";
                case 3:
                    return "onDownloadProgress";
                case 4:
                    return "onDownloadFailed";
                case 5:
                    return "onDownloadCompleted";
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
                data.enforceInterface(IDownloadCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDownloadCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 2:
                    long _arg0 = data.readLong();
                    data.enforceNoDataAvail();
                    onDownloadStarted(_arg0);
                    return true;
                case 3:
                    long _arg02 = data.readLong();
                    data.enforceNoDataAvail();
                    onDownloadProgress(_arg02);
                    return true;
                case 4:
                    int _arg03 = data.readInt();
                    String _arg1 = data.readString();
                    PersistableBundle _arg2 =
                            (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    data.enforceNoDataAvail();
                    onDownloadFailed(_arg03, _arg1, _arg2);
                    return true;
                case 5:
                    PersistableBundle _arg04 =
                            (PersistableBundle) data.readTypedObject(PersistableBundle.CREATOR);
                    data.enforceNoDataAvail();
                    onDownloadCompleted(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDownloadCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDownloadCallback.DESCRIPTOR;
            }

            @Override // android.app.ondeviceintelligence.IDownloadCallback
            public void onDownloadStarted(long bytesToDownload) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDownloadCallback.DESCRIPTOR);
                    _data.writeLong(bytesToDownload);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IDownloadCallback
            public void onDownloadProgress(long bytesDownloaded) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDownloadCallback.DESCRIPTOR);
                    _data.writeLong(bytesDownloaded);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IDownloadCallback
            public void onDownloadFailed(
                    int failureStatus, String errorMessage, PersistableBundle errorParams)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDownloadCallback.DESCRIPTOR);
                    _data.writeInt(failureStatus);
                    _data.writeString(errorMessage);
                    _data.writeTypedObject(errorParams, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.ondeviceintelligence.IDownloadCallback
            public void onDownloadCompleted(PersistableBundle downloadParams)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDownloadCallback.DESCRIPTOR);
                    _data.writeTypedObject(downloadParams, 0);
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
