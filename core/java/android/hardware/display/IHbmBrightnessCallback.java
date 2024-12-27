package android.hardware.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IHbmBrightnessCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.display.IHbmBrightnessCallback";

    void onChanged(int i, boolean z) throws RemoteException;

    public static class Default implements IHbmBrightnessCallback {
        @Override // android.hardware.display.IHbmBrightnessCallback
        public void onChanged(int displayId, boolean isHbm) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IHbmBrightnessCallback {
        static final int TRANSACTION_onChanged = 1;

        public Stub() {
            attachInterface(this, IHbmBrightnessCallback.DESCRIPTOR);
        }

        public static IHbmBrightnessCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IHbmBrightnessCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IHbmBrightnessCallback)) {
                return (IHbmBrightnessCallback) iin;
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
                    return "onChanged";
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
                data.enforceInterface(IHbmBrightnessCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IHbmBrightnessCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    boolean _arg1 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onChanged(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IHbmBrightnessCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHbmBrightnessCallback.DESCRIPTOR;
            }

            @Override // android.hardware.display.IHbmBrightnessCallback
            public void onChanged(int displayId, boolean isHbm) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IHbmBrightnessCallback.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeBoolean(isHbm);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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
