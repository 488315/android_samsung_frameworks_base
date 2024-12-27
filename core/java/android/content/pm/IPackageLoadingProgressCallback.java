package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IPackageLoadingProgressCallback extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IPackageLoadingProgressCallback";

    void onPackageLoadingProgressChanged(float f) throws RemoteException;

    public static class Default implements IPackageLoadingProgressCallback {
        @Override // android.content.pm.IPackageLoadingProgressCallback
        public void onPackageLoadingProgressChanged(float progress) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IPackageLoadingProgressCallback {
        static final int TRANSACTION_onPackageLoadingProgressChanged = 1;

        public Stub() {
            attachInterface(this, IPackageLoadingProgressCallback.DESCRIPTOR);
        }

        public static IPackageLoadingProgressCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IPackageLoadingProgressCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IPackageLoadingProgressCallback)) {
                return (IPackageLoadingProgressCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onPackageLoadingProgressChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IPackageLoadingProgressCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IPackageLoadingProgressCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    float _arg0 = data.readFloat();
                    data.enforceNoDataAvail();
                    onPackageLoadingProgressChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPackageLoadingProgressCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPackageLoadingProgressCallback.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageLoadingProgressCallback
            public void onPackageLoadingProgressChanged(float progress) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPackageLoadingProgressCallback.DESCRIPTOR);
                    _data.writeFloat(progress);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
