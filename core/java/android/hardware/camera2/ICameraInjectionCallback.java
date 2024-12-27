package android.hardware.camera2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ICameraInjectionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.camera2.ICameraInjectionCallback";
    public static final int ERROR_INJECTION_INVALID_ERROR = -1;
    public static final int ERROR_INJECTION_SERVICE = 1;
    public static final int ERROR_INJECTION_SESSION = 0;
    public static final int ERROR_INJECTION_UNSUPPORTED = 2;

    void onInjectionError(int i) throws RemoteException;

    public static class Default implements ICameraInjectionCallback {
        @Override // android.hardware.camera2.ICameraInjectionCallback
        public void onInjectionError(int errorCode) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ICameraInjectionCallback {
        static final int TRANSACTION_onInjectionError = 1;

        public Stub() {
            attachInterface(this, ICameraInjectionCallback.DESCRIPTOR);
        }

        public static ICameraInjectionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICameraInjectionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ICameraInjectionCallback)) {
                return (ICameraInjectionCallback) iin;
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
                    return "onInjectionError";
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
                data.enforceInterface(ICameraInjectionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICameraInjectionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onInjectionError(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICameraInjectionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICameraInjectionCallback.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.ICameraInjectionCallback
            public void onInjectionError(int errorCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICameraInjectionCallback.DESCRIPTOR);
                    _data.writeInt(errorCode);
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
