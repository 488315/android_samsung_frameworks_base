package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IGameStateListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.IGameStateListener";

    public static class Default implements IGameStateListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IGameStateListener
        public void onGameStateChanged(String str, GameState gameState, int i) throws RemoteException {
        }
    }

    void onGameStateChanged(String str, GameState gameState, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IGameStateListener {
        static final int TRANSACTION_onGameStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IGameStateListener.DESCRIPTOR);
        }

        public static IGameStateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGameStateListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGameStateListener)) {
                return (IGameStateListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onGameStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGameStateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGameStateListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                GameState gameState = (GameState) parcel.readTypedObject(GameState.CREATOR);
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onGameStateChanged(string, gameState, i3);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGameStateListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameStateListener.DESCRIPTOR;
            }

            @Override // android.app.IGameStateListener
            public void onGameStateChanged(String str, GameState gameState, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGameStateListener.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(gameState, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
