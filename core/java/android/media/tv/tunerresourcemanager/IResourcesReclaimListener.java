package android.media.tv.tunerresourcemanager;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IResourcesReclaimListener extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.tunerresourcemanager.IResourcesReclaimListener";

    void onReclaimResources() throws RemoteException;

    public static class Default implements IResourcesReclaimListener {
        @Override // android.media.tv.tunerresourcemanager.IResourcesReclaimListener
        public void onReclaimResources() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IResourcesReclaimListener {
        static final int TRANSACTION_onReclaimResources = 1;

        public Stub() {
            attachInterface(this, IResourcesReclaimListener.DESCRIPTOR);
        }

        public static IResourcesReclaimListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IResourcesReclaimListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IResourcesReclaimListener)) {
                return (IResourcesReclaimListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IResourcesReclaimListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IResourcesReclaimListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onReclaimResources();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IResourcesReclaimListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IResourcesReclaimListener.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
