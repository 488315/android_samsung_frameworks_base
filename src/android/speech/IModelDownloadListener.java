package android.speech;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IModelDownloadListener extends IInterface {
    public static final String DESCRIPTOR = "android.speech.IModelDownloadListener";

    public static class Default implements IModelDownloadListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.speech.IModelDownloadListener
        public void onError(int i) throws RemoteException {
        }

        @Override // android.speech.IModelDownloadListener
        public void onProgress(int i) throws RemoteException {
        }

        @Override // android.speech.IModelDownloadListener
        public void onScheduled() throws RemoteException {
        }

        @Override // android.speech.IModelDownloadListener
        public void onSuccess() throws RemoteException {
        }
    }

    void onError(int i) throws RemoteException;

    void onProgress(int i) throws RemoteException;

    void onScheduled() throws RemoteException;

    void onSuccess() throws RemoteException;

    public static abstract class Stub extends Binder implements IModelDownloadListener {
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onProgress = 1;
        static final int TRANSACTION_onScheduled = 3;
        static final int TRANSACTION_onSuccess = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IModelDownloadListener.DESCRIPTOR);
        }

        public static IModelDownloadListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IModelDownloadListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IModelDownloadListener)) {
                return (IModelDownloadListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onProgress";
            }
            if (i == 2) {
                return "onSuccess";
            }
            if (i == 3) {
                return "onScheduled";
            }
            if (i != 4) {
                return null;
            }
            return "onError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IModelDownloadListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IModelDownloadListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onProgress(readInt);
            } else if (i == 2) {
                onSuccess();
            } else if (i == 3) {
                onScheduled();
            } else if (i == 4) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IModelDownloadListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IModelDownloadListener.DESCRIPTOR;
            }

            @Override // android.speech.IModelDownloadListener
            public void onProgress(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IModelDownloadListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IModelDownloadListener
            public void onSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IModelDownloadListener.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IModelDownloadListener
            public void onScheduled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IModelDownloadListener.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.speech.IModelDownloadListener
            public void onError(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IModelDownloadListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
