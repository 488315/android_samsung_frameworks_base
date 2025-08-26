package android.app.admin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControlViewHost;

/* loaded from: classes.dex */
public interface IKeyguardCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.admin.IKeyguardCallback";

    public static class Default implements IKeyguardCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.admin.IKeyguardCallback
        public void onDismiss() throws RemoteException {
        }

        @Override // android.app.admin.IKeyguardCallback
        public void onRemoteContentReady(SurfaceControlViewHost.SurfacePackage surfacePackage) throws RemoteException {
        }
    }

    void onDismiss() throws RemoteException;

    void onRemoteContentReady(SurfaceControlViewHost.SurfacePackage surfacePackage) throws RemoteException;

    public static abstract class Stub extends Binder implements IKeyguardCallback {
        static final int TRANSACTION_onDismiss = 2;
        static final int TRANSACTION_onRemoteContentReady = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IKeyguardCallback.DESCRIPTOR);
        }

        public static IKeyguardCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKeyguardCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKeyguardCallback)) {
                return (IKeyguardCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onRemoteContentReady";
            }
            if (i != 2) {
                return null;
            }
            return "onDismiss";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKeyguardCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKeyguardCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SurfaceControlViewHost.SurfacePackage surfacePackage = (SurfaceControlViewHost.SurfacePackage) parcel.readTypedObject(SurfaceControlViewHost.SurfacePackage.CREATOR);
                parcel.enforceNoDataAvail();
                onRemoteContentReady(surfacePackage);
            } else if (i == 2) {
                onDismiss();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IKeyguardCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeyguardCallback.DESCRIPTOR;
            }

            @Override // android.app.admin.IKeyguardCallback
            public void onRemoteContentReady(SurfaceControlViewHost.SurfacePackage surfacePackage) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IKeyguardCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(surfacePackage, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.admin.IKeyguardCallback
            public void onDismiss() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IKeyguardCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
