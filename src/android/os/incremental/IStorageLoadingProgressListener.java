package android.os.incremental;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IStorageLoadingProgressListener extends IInterface {
    public static final String DESCRIPTOR = "android.os.incremental.IStorageLoadingProgressListener";

    public static class Default implements IStorageLoadingProgressListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.incremental.IStorageLoadingProgressListener
        public void onStorageLoadingProgressChanged(int i, float f) throws RemoteException {
        }
    }

    void onStorageLoadingProgressChanged(int i, float f) throws RemoteException;

    public static abstract class Stub extends Binder implements IStorageLoadingProgressListener {
        static final int TRANSACTION_onStorageLoadingProgressChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IStorageLoadingProgressListener.DESCRIPTOR);
        }

        public static IStorageLoadingProgressListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IStorageLoadingProgressListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IStorageLoadingProgressListener)) {
                return (IStorageLoadingProgressListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onStorageLoadingProgressChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IStorageLoadingProgressListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IStorageLoadingProgressListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                float f = parcel.readFloat();
                parcel.enforceNoDataAvail();
                onStorageLoadingProgressChanged(i3, f);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IStorageLoadingProgressListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStorageLoadingProgressListener.DESCRIPTOR;
            }

            @Override // android.os.incremental.IStorageLoadingProgressListener
            public void onStorageLoadingProgressChanged(int i, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IStorageLoadingProgressListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
