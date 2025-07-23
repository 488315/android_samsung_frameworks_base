package android.media.projection;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IMediaProjectionCallback extends IInterface {

    public static class Default implements IMediaProjectionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onCapturedContentResize(int i, int i2) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onCapturedContentVisibilityChanged(boolean z) throws RemoteException {
        }

        @Override // android.media.projection.IMediaProjectionCallback
        public void onStop() throws RemoteException {
        }
    }

    void onCapturedContentResize(int i, int i2) throws RemoteException;

    void onCapturedContentVisibilityChanged(boolean z) throws RemoteException;

    void onStop() throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaProjectionCallback {
        public static final String DESCRIPTOR = "android.media.projection.IMediaProjectionCallback";
        static final int TRANSACTION_onCapturedContentResize = 2;
        static final int TRANSACTION_onCapturedContentVisibilityChanged = 3;
        static final int TRANSACTION_onStop = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaProjectionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaProjectionCallback)) {
                return (IMediaProjectionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStop";
            }
            if (i == 2) {
                return "onCapturedContentResize";
            }
            if (i != 3) {
                return null;
            }
            return "onCapturedContentVisibilityChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onStop();
            } else if (i == 2) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCapturedContentResize(readInt, readInt2);
            } else if (i == 3) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onCapturedContentVisibilityChanged(readBoolean);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMediaProjectionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.projection.IMediaProjectionCallback
            public void onStop() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionCallback
            public void onCapturedContentResize(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.projection.IMediaProjectionCallback
            public void onCapturedContentVisibilityChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
