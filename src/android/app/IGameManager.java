package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IGameManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.IGameManager";

    public static class Default implements IGameManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IGameManager
        public int getGameMode() throws RemoteException {
            return 0;
        }
    }

    int getGameMode() throws RemoteException;

    public static abstract class Stub extends Binder implements IGameManager {
        static final int TRANSACTION_getGameMode = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IGameManager.DESCRIPTOR);
        }

        public static IGameManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGameManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGameManager)) {
                return (IGameManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getGameMode";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGameManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGameManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int gameMode = getGameMode();
                parcel2.writeNoException();
                parcel2.writeInt(gameMode);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGameManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameManager.DESCRIPTOR;
            }

            @Override // android.app.IGameManager
            public int getGameMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
