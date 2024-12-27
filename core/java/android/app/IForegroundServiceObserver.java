package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IForegroundServiceObserver extends IInterface {
    public static final String DESCRIPTOR = "android.app.IForegroundServiceObserver";

    void onForegroundStateChanged(IBinder iBinder, String str, int i, boolean z)
            throws RemoteException;

    public static class Default implements IForegroundServiceObserver {
        @Override // android.app.IForegroundServiceObserver
        public void onForegroundStateChanged(
                IBinder serviceToken, String packageName, int userId, boolean isForeground)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IForegroundServiceObserver {
        static final int TRANSACTION_onForegroundStateChanged = 1;

        public Stub() {
            attachInterface(this, IForegroundServiceObserver.DESCRIPTOR);
        }

        public static IForegroundServiceObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IForegroundServiceObserver.DESCRIPTOR);
            if (iin != null && (iin instanceof IForegroundServiceObserver)) {
                return (IForegroundServiceObserver) iin;
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
                    return "onForegroundStateChanged";
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
                data.enforceInterface(IForegroundServiceObserver.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IForegroundServiceObserver.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    String _arg1 = data.readString();
                    int _arg2 = data.readInt();
                    boolean _arg3 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onForegroundStateChanged(_arg0, _arg1, _arg2, _arg3);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IForegroundServiceObserver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IForegroundServiceObserver.DESCRIPTOR;
            }

            @Override // android.app.IForegroundServiceObserver
            public void onForegroundStateChanged(
                    IBinder serviceToken, String packageName, int userId, boolean isForeground)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IForegroundServiceObserver.DESCRIPTOR);
                    _data.writeStrongBinder(serviceToken);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeBoolean(isForeground);
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
