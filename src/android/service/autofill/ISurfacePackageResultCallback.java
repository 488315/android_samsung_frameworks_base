package android.service.autofill;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControlViewHost;

/* loaded from: classes3.dex */
public interface ISurfacePackageResultCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.autofill.ISurfacePackageResultCallback";

    public static class Default implements ISurfacePackageResultCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.autofill.ISurfacePackageResultCallback
        public void onResult(SurfaceControlViewHost.SurfacePackage surfacePackage) throws RemoteException {
        }
    }

    void onResult(SurfaceControlViewHost.SurfacePackage surfacePackage) throws RemoteException;

    public static abstract class Stub extends Binder implements ISurfacePackageResultCallback {
        static final int TRANSACTION_onResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISurfacePackageResultCallback.DESCRIPTOR);
        }

        public static ISurfacePackageResultCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISurfacePackageResultCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISurfacePackageResultCallback)) {
                return (ISurfacePackageResultCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onResult";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISurfacePackageResultCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISurfacePackageResultCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SurfaceControlViewHost.SurfacePackage surfacePackage = (SurfaceControlViewHost.SurfacePackage) parcel.readTypedObject(SurfaceControlViewHost.SurfacePackage.CREATOR);
                parcel.enforceNoDataAvail();
                onResult(surfacePackage);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISurfacePackageResultCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISurfacePackageResultCallback.DESCRIPTOR;
            }

            @Override // android.service.autofill.ISurfacePackageResultCallback
            public void onResult(SurfaceControlViewHost.SurfacePackage surfacePackage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISurfacePackageResultCallback.DESCRIPTOR);
                    obtain.writeTypedObject(surfacePackage, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
