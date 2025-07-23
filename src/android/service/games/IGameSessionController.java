package android.service.games;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes3.dex */
public interface IGameSessionController extends IInterface {
    public static final String DESCRIPTOR = "android.service.games.IGameSessionController";

    public static class Default implements IGameSessionController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.games.IGameSessionController
        public void restartGame(int i) throws RemoteException {
        }

        @Override // android.service.games.IGameSessionController
        public void takeScreenshot(int i, AndroidFuture androidFuture) throws RemoteException {
        }
    }

    void restartGame(int i) throws RemoteException;

    void takeScreenshot(int i, AndroidFuture androidFuture) throws RemoteException;

    public static abstract class Stub extends Binder implements IGameSessionController {
        static final int TRANSACTION_restartGame = 2;
        static final int TRANSACTION_takeScreenshot = 1;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IGameSessionController.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IGameSessionController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGameSessionController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGameSessionController)) {
                return (IGameSessionController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "takeScreenshot";
            }
            if (i != 2) {
                return null;
            }
            return "restartGame";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGameSessionController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGameSessionController.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                AndroidFuture androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                parcel.enforceNoDataAvail();
                takeScreenshot(readInt, androidFuture);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                restartGame(readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGameSessionController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameSessionController.DESCRIPTOR;
            }

            @Override // android.service.games.IGameSessionController
            public void takeScreenshot(int i, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGameSessionController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.games.IGameSessionController
            public void restartGame(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGameSessionController.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        protected void takeScreenshot_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_GAME_ACTIVITY, getCallingPid(), getCallingUid());
        }

        protected void restartGame_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_GAME_ACTIVITY, getCallingPid(), getCallingUid());
        }
    }
}
