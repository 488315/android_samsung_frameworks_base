package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUserSwitchObserver extends IInterface {

    public static class Default implements IUserSwitchObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IUserSwitchObserver
        public void onBeforeUserSwitching(int i, IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override // android.app.IUserSwitchObserver
        public void onForegroundProfileSwitch(int i) throws RemoteException {
        }

        @Override // android.app.IUserSwitchObserver
        public void onLockedBootComplete(int i) throws RemoteException {
        }

        @Override // android.app.IUserSwitchObserver
        public void onUserSwitchComplete(int i) throws RemoteException {
        }

        @Override // android.app.IUserSwitchObserver
        public void onUserSwitching(int i, IRemoteCallback iRemoteCallback) throws RemoteException {
        }
    }

    void onBeforeUserSwitching(int i, IRemoteCallback iRemoteCallback) throws RemoteException;

    void onForegroundProfileSwitch(int i) throws RemoteException;

    void onLockedBootComplete(int i) throws RemoteException;

    void onUserSwitchComplete(int i) throws RemoteException;

    void onUserSwitching(int i, IRemoteCallback iRemoteCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IUserSwitchObserver {
        public static final String DESCRIPTOR = "android.app.IUserSwitchObserver";
        static final int TRANSACTION_onBeforeUserSwitching = 1;
        static final int TRANSACTION_onForegroundProfileSwitch = 4;
        static final int TRANSACTION_onLockedBootComplete = 5;
        static final int TRANSACTION_onUserSwitchComplete = 3;
        static final int TRANSACTION_onUserSwitching = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUserSwitchObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUserSwitchObserver)) {
                return (IUserSwitchObserver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onBeforeUserSwitching";
            }
            if (i == 2) {
                return "onUserSwitching";
            }
            if (i == 3) {
                return "onUserSwitchComplete";
            }
            if (i == 4) {
                return "onForegroundProfileSwitch";
            }
            if (i != 5) {
                return null;
            }
            return "onLockedBootComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                IRemoteCallback asInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onBeforeUserSwitching(readInt, asInterface);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                IRemoteCallback asInterface2 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onUserSwitching(readInt2, asInterface2);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onUserSwitchComplete(readInt3);
            } else if (i == 4) {
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onForegroundProfileSwitch(readInt4);
            } else if (i == 5) {
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onLockedBootComplete(readInt5);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IUserSwitchObserver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IUserSwitchObserver
            public void onBeforeUserSwitching(int i, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUserSwitchObserver
            public void onUserSwitching(int i, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUserSwitchObserver
            public void onUserSwitchComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUserSwitchObserver
            public void onForegroundProfileSwitch(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUserSwitchObserver
            public void onLockedBootComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
