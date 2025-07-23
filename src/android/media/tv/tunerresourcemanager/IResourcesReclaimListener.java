package android.media.tv.tunerresourcemanager;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IResourcesReclaimListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.tunerresourcemanager.IResourcesReclaimListener";

    public static class Default implements IResourcesReclaimListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.tunerresourcemanager.IResourcesReclaimListener
        public void onReclaimResources() throws RemoteException {
        }
    }

    void onReclaimResources() throws RemoteException;

    public static abstract class Stub extends Binder implements IResourcesReclaimListener {
        static final int TRANSACTION_onReclaimResources = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IResourcesReclaimListener.DESCRIPTOR);
        }

        public static IResourcesReclaimListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IResourcesReclaimListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IResourcesReclaimListener)) {
                return (IResourcesReclaimListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IResourcesReclaimListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IResourcesReclaimListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onReclaimResources();
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IResourcesReclaimListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResourcesReclaimListener.DESCRIPTOR;
            }

            @Override // android.media.tv.tunerresourcemanager.IResourcesReclaimListener
            public void onReclaimResources() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IResourcesReclaimListener.DESCRIPTOR);
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
