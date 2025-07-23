package android.gsi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IProgressCallback extends IInterface {
    public static final String DESCRIPTOR = "android.gsi.IProgressCallback";

    public static class Default implements IProgressCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.gsi.IProgressCallback
        public void onProgress(long j, long j2) throws RemoteException {
        }
    }

    void onProgress(long j, long j2) throws RemoteException;

    public static abstract class Stub extends Binder implements IProgressCallback {
        static final int TRANSACTION_onProgress = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IProgressCallback.DESCRIPTOR);
        }

        public static IProgressCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IProgressCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProgressCallback)) {
                return (IProgressCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onProgress";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProgressCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProgressCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long readLong = parcel.readLong();
                long readLong2 = parcel.readLong();
                parcel.enforceNoDataAvail();
                onProgress(readLong, readLong2);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IProgressCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProgressCallback.DESCRIPTOR;
            }

            @Override // android.gsi.IProgressCallback
            public void onProgress(long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProgressCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
