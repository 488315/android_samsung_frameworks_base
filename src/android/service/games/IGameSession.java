package android.service.games;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IGameSession extends IInterface {
    public static final String DESCRIPTOR = "android.service.games.IGameSession";

    public static class Default implements IGameSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.games.IGameSession
        public void onDestroyed() throws RemoteException {
        }

        @Override // android.service.games.IGameSession
        public void onTaskFocusChanged(boolean z) throws RemoteException {
        }

        @Override // android.service.games.IGameSession
        public void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean z) throws RemoteException {
        }
    }

    void onDestroyed() throws RemoteException;

    void onTaskFocusChanged(boolean z) throws RemoteException;

    void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IGameSession {
        static final int TRANSACTION_onDestroyed = 1;
        static final int TRANSACTION_onTaskFocusChanged = 3;
        static final int TRANSACTION_onTransientSystemBarVisibilityFromRevealGestureChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IGameSession.DESCRIPTOR);
        }

        public static IGameSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGameSession.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGameSession)) {
                return (IGameSession) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onDestroyed";
            }
            if (i == 2) {
                return "onTransientSystemBarVisibilityFromRevealGestureChanged";
            }
            if (i != 3) {
                return null;
            }
            return "onTaskFocusChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGameSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGameSession.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onDestroyed();
            } else if (i == 2) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onTransientSystemBarVisibilityFromRevealGestureChanged(z);
            } else if (i == 3) {
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onTaskFocusChanged(z2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGameSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameSession.DESCRIPTOR;
            }

            @Override // android.service.games.IGameSession
            public void onDestroyed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGameSession.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.games.IGameSession
            public void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGameSession.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.games.IGameSession
            public void onTaskFocusChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGameSession.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
