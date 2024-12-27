package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IGameStateListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.IGameStateListener";

    void onGameStateChanged(String str, GameState gameState, int i) throws RemoteException;

    public static class Default implements IGameStateListener {
        @Override // android.app.IGameStateListener
        public void onGameStateChanged(String packageName, GameState state, int userId) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGameStateListener {
        static final int TRANSACTION_onGameStateChanged = 1;

        public Stub() {
            attachInterface(this, IGameStateListener.DESCRIPTOR);
        }

        public static IGameStateListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGameStateListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IGameStateListener)) {
                return (IGameStateListener) iin;
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
                    return "onGameStateChanged";
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
                data.enforceInterface(IGameStateListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGameStateListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    GameState _arg1 = (GameState) data.readTypedObject(GameState.CREATOR);
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    onGameStateChanged(_arg0, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGameStateListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameStateListener.DESCRIPTOR;
            }

            @Override // android.app.IGameStateListener
            public void onGameStateChanged(String packageName, GameState state, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameStateListener.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(state, 0);
                    _data.writeInt(userId);
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
