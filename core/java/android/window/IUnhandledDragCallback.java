package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IUnhandledDragCallback extends IInterface {
    public static final String DESCRIPTOR = "android.window.IUnhandledDragCallback";

    void notifyUnhandledDropComplete(boolean z) throws RemoteException;

    public static class Default implements IUnhandledDragCallback {
        @Override // android.window.IUnhandledDragCallback
        public void notifyUnhandledDropComplete(boolean handled) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IUnhandledDragCallback {
        static final int TRANSACTION_notifyUnhandledDropComplete = 1;

        public Stub() {
            attachInterface(this, IUnhandledDragCallback.DESCRIPTOR);
        }

        public static IUnhandledDragCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUnhandledDragCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IUnhandledDragCallback)) {
                return (IUnhandledDragCallback) iin;
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
                    return "notifyUnhandledDropComplete";
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
                data.enforceInterface(IUnhandledDragCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IUnhandledDragCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    notifyUnhandledDropComplete(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IUnhandledDragCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUnhandledDragCallback.DESCRIPTOR;
            }

            @Override // android.window.IUnhandledDragCallback
            public void notifyUnhandledDropComplete(boolean handled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IUnhandledDragCallback.DESCRIPTOR);
                    _data.writeBoolean(handled);
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
