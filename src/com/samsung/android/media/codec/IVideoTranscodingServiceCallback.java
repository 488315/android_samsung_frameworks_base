package com.samsung.android.media.codec;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IVideoTranscodingServiceCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.media.codec.IVideoTranscodingServiceCallback";

    public static class Default implements IVideoTranscodingServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onCompleted() throws RemoteException {
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onError() throws RemoteException {
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onProgressChanged(int i) throws RemoteException {
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onReady() throws RemoteException {
        }

        @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
        public void onStarted() throws RemoteException {
        }
    }

    void onCompleted() throws RemoteException;

    void onError() throws RemoteException;

    void onProgressChanged(int i) throws RemoteException;

    void onReady() throws RemoteException;

    void onStarted() throws RemoteException;

    public static abstract class Stub extends Binder implements IVideoTranscodingServiceCallback {
        static final int TRANSACTION_onCompleted = 4;
        static final int TRANSACTION_onError = 5;
        static final int TRANSACTION_onProgressChanged = 3;
        static final int TRANSACTION_onReady = 1;
        static final int TRANSACTION_onStarted = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IVideoTranscodingServiceCallback.DESCRIPTOR);
        }

        public static IVideoTranscodingServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVideoTranscodingServiceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVideoTranscodingServiceCallback)) {
                return (IVideoTranscodingServiceCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onReady";
            }
            if (i == 2) {
                return "onStarted";
            }
            if (i == 3) {
                return "onProgressChanged";
            }
            if (i == 4) {
                return "onCompleted";
            }
            if (i != 5) {
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
                parcel.enforceInterface(IVideoTranscodingServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVideoTranscodingServiceCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onReady();
                parcel2.writeNoException();
            } else if (i == 2) {
                onStarted();
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onProgressChanged(readInt);
                parcel2.writeNoException();
            } else if (i == 4) {
                onCompleted();
                parcel2.writeNoException();
            } else if (i == 5) {
                onError();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVideoTranscodingServiceCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVideoTranscodingServiceCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
            public void onReady() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoTranscodingServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
            public void onStarted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoTranscodingServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
            public void onProgressChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoTranscodingServiceCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
            public void onCompleted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoTranscodingServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.media.codec.IVideoTranscodingServiceCallback
            public void onError() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVideoTranscodingServiceCallback.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
