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

    void onResult(SurfaceControlViewHost.SurfacePackage surfacePackage) throws RemoteException;

    public static class Default implements ISurfacePackageResultCallback {
        @Override // android.service.autofill.ISurfacePackageResultCallback
        public void onResult(SurfaceControlViewHost.SurfacePackage result) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISurfacePackageResultCallback {
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, ISurfacePackageResultCallback.DESCRIPTOR);
        }

        public static ISurfacePackageResultCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISurfacePackageResultCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISurfacePackageResultCallback)) {
                return (ISurfacePackageResultCallback) iin;
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
                    return "onResult";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISurfacePackageResultCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISurfacePackageResultCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SurfaceControlViewHost.SurfacePackage _arg0 = (SurfaceControlViewHost.SurfacePackage) data.readTypedObject(SurfaceControlViewHost.SurfacePackage.CREATOR);
                    data.enforceNoDataAvail();
                    onResult(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISurfacePackageResultCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISurfacePackageResultCallback.DESCRIPTOR;
            }

            @Override // android.service.autofill.ISurfacePackageResultCallback
            public void onResult(SurfaceControlViewHost.SurfacePackage result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISurfacePackageResultCallback.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
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
