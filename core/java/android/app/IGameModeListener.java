package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IGameModeListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.IGameModeListener";

    void onGameModeChanged(String str, int i, int i2, int i3) throws RemoteException;

    public static class Default implements IGameModeListener {
        @Override // android.app.IGameModeListener
        public void onGameModeChanged(String packageName, int gameModeFrom, int gameModeTo, int userId) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGameModeListener {
        static final int TRANSACTION_onGameModeChanged = 1;

        public Stub() {
            attachInterface(this, IGameModeListener.DESCRIPTOR);
        }

        public static IGameModeListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGameModeListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IGameModeListener)) {
                return (IGameModeListener) iin;
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
                    return "onGameModeChanged";
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
                data.enforceInterface(IGameModeListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGameModeListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    onGameModeChanged(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGameModeListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameModeListener.DESCRIPTOR;
            }

            @Override // android.app.IGameModeListener
            public void onGameModeChanged(String packageName, int gameModeFrom, int gameModeTo, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameModeListener.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(gameModeFrom);
                    _data.writeInt(gameModeTo);
                    _data.writeInt(userId);
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
