package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IPackageLoadingProgressCallback extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IPackageLoadingProgressCallback";

    public static class Default implements IPackageLoadingProgressCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IPackageLoadingProgressCallback
        public void onPackageLoadingProgressChanged(float f) throws RemoteException {
        }
    }

    void onPackageLoadingProgressChanged(float f) throws RemoteException;

    public static abstract class Stub extends Binder implements IPackageLoadingProgressCallback {
        static final int TRANSACTION_onPackageLoadingProgressChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IPackageLoadingProgressCallback.DESCRIPTOR);
        }

        public static IPackageLoadingProgressCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPackageLoadingProgressCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPackageLoadingProgressCallback)) {
                return (IPackageLoadingProgressCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onPackageLoadingProgressChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPackageLoadingProgressCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPackageLoadingProgressCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                float f = parcel.readFloat();
                parcel.enforceNoDataAvail();
                onPackageLoadingProgressChanged(f);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IPackageLoadingProgressCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPackageLoadingProgressCallback.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageLoadingProgressCallback
            public void onPackageLoadingProgressChanged(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPackageLoadingProgressCallback.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
