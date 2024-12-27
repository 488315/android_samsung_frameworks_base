package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUiModeManagerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.IUiModeManagerCallback";

    void notifyContrastChanged(float f) throws RemoteException;

    public static class Default implements IUiModeManagerCallback {
        @Override // android.app.IUiModeManagerCallback
        public void notifyContrastChanged(float contrast) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IUiModeManagerCallback {
        static final int TRANSACTION_notifyContrastChanged = 1;

        public Stub() {
            attachInterface(this, IUiModeManagerCallback.DESCRIPTOR);
        }

        public static IUiModeManagerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUiModeManagerCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IUiModeManagerCallback)) {
                return (IUiModeManagerCallback) iin;
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
                    return "notifyContrastChanged";
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
                data.enforceInterface(IUiModeManagerCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IUiModeManagerCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    float _arg0 = data.readFloat();
                    data.enforceNoDataAvail();
                    notifyContrastChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IUiModeManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUiModeManagerCallback.DESCRIPTOR;
            }

            @Override // android.app.IUiModeManagerCallback
            public void notifyContrastChanged(float contrast) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IUiModeManagerCallback.DESCRIPTOR);
                    _data.writeFloat(contrast);
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
