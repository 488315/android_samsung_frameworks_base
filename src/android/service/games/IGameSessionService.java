package android.service.games;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.games.IGameSessionController;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes3.dex */
public interface IGameSessionService extends IInterface {
    public static final String DESCRIPTOR = "android.service.games.IGameSessionService";

    public static class Default implements IGameSessionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.games.IGameSessionService
        public void create(IGameSessionController iGameSessionController, CreateGameSessionRequest createGameSessionRequest, GameSessionViewHostConfiguration gameSessionViewHostConfiguration, AndroidFuture androidFuture) throws RemoteException {
        }
    }

    void create(IGameSessionController iGameSessionController, CreateGameSessionRequest createGameSessionRequest, GameSessionViewHostConfiguration gameSessionViewHostConfiguration, AndroidFuture androidFuture) throws RemoteException;

    public static abstract class Stub extends Binder implements IGameSessionService {
        static final int TRANSACTION_create = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IGameSessionService.DESCRIPTOR);
        }

        public static IGameSessionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGameSessionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGameSessionService)) {
                return (IGameSessionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "create";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGameSessionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGameSessionService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IGameSessionController asInterface = IGameSessionController.Stub.asInterface(parcel.readStrongBinder());
                CreateGameSessionRequest createGameSessionRequest = (CreateGameSessionRequest) parcel.readTypedObject(CreateGameSessionRequest.CREATOR);
                GameSessionViewHostConfiguration gameSessionViewHostConfiguration = (GameSessionViewHostConfiguration) parcel.readTypedObject(GameSessionViewHostConfiguration.CREATOR);
                AndroidFuture androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                parcel.enforceNoDataAvail();
                create(asInterface, createGameSessionRequest, gameSessionViewHostConfiguration, androidFuture);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGameSessionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameSessionService.DESCRIPTOR;
            }

            @Override // android.service.games.IGameSessionService
            public void create(IGameSessionController iGameSessionController, CreateGameSessionRequest createGameSessionRequest, GameSessionViewHostConfiguration gameSessionViewHostConfiguration, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGameSessionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGameSessionController);
                    obtain.writeTypedObject(createGameSessionRequest, 0);
                    obtain.writeTypedObject(gameSessionViewHostConfiguration, 0);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
