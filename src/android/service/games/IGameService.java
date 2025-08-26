package android.service.games;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.games.IGameServiceController;

/* loaded from: classes3.dex */
public interface IGameService extends IInterface {
    public static final String DESCRIPTOR = "android.service.games.IGameService";

    public static class Default implements IGameService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.games.IGameService
        public void connected(IGameServiceController iGameServiceController) throws RemoteException {
        }

        @Override // android.service.games.IGameService
        public void disconnected() throws RemoteException {
        }

        @Override // android.service.games.IGameService
        public void gameStarted(GameStartedEvent gameStartedEvent) throws RemoteException {
        }
    }

    void connected(IGameServiceController iGameServiceController) throws RemoteException;

    void disconnected() throws RemoteException;

    void gameStarted(GameStartedEvent gameStartedEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements IGameService {
        static final int TRANSACTION_connected = 1;
        static final int TRANSACTION_disconnected = 2;
        static final int TRANSACTION_gameStarted = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IGameService.DESCRIPTOR);
        }

        public static IGameService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGameService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGameService)) {
                return (IGameService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "connected";
            }
            if (i == 2) {
                return "disconnected";
            }
            if (i != 3) {
                return null;
            }
            return "gameStarted";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGameService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGameService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IGameServiceController iGameServiceControllerAsInterface = IGameServiceController.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                connected(iGameServiceControllerAsInterface);
            } else if (i == 2) {
                disconnected();
            } else if (i == 3) {
                GameStartedEvent gameStartedEvent = (GameStartedEvent) parcel.readTypedObject(GameStartedEvent.CREATOR);
                parcel.enforceNoDataAvail();
                gameStarted(gameStartedEvent);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGameService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameService.DESCRIPTOR;
            }

            @Override // android.service.games.IGameService
            public void connected(IGameServiceController iGameServiceController) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGameService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iGameServiceController);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.games.IGameService
            public void disconnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGameService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.games.IGameService
            public void gameStarted(GameStartedEvent gameStartedEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGameService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(gameStartedEvent, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
