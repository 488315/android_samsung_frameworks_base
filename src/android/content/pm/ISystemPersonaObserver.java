package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.SemPersonaState;

/* loaded from: classes.dex */
public interface ISystemPersonaObserver extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.ISystemPersonaObserver";

    public static class Default implements ISystemPersonaObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.ISystemPersonaObserver
        public void onKnoxContainerLaunch(int i) throws RemoteException {
        }

        @Override // android.content.pm.ISystemPersonaObserver
        public void onPersonaActive(int i) throws RemoteException {
        }

        @Override // android.content.pm.ISystemPersonaObserver
        public void onRemovePersona(int i) throws RemoteException {
        }

        @Override // android.content.pm.ISystemPersonaObserver
        public void onResetPersona(int i) throws RemoteException {
        }

        @Override // android.content.pm.ISystemPersonaObserver
        public void onStateChange(int i, SemPersonaState semPersonaState, SemPersonaState semPersonaState2) throws RemoteException {
        }
    }

    void onKnoxContainerLaunch(int i) throws RemoteException;

    void onPersonaActive(int i) throws RemoteException;

    void onRemovePersona(int i) throws RemoteException;

    void onResetPersona(int i) throws RemoteException;

    void onStateChange(int i, SemPersonaState semPersonaState, SemPersonaState semPersonaState2) throws RemoteException;

    public static abstract class Stub extends Binder implements ISystemPersonaObserver {
        static final int TRANSACTION_onKnoxContainerLaunch = 4;
        static final int TRANSACTION_onPersonaActive = 1;
        static final int TRANSACTION_onRemovePersona = 2;
        static final int TRANSACTION_onResetPersona = 3;
        static final int TRANSACTION_onStateChange = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ISystemPersonaObserver.DESCRIPTOR);
        }

        public static ISystemPersonaObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISystemPersonaObserver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISystemPersonaObserver)) {
                return (ISystemPersonaObserver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onPersonaActive";
            }
            if (i == 2) {
                return "onRemovePersona";
            }
            if (i == 3) {
                return "onResetPersona";
            }
            if (i == 4) {
                return "onKnoxContainerLaunch";
            }
            if (i != 5) {
                return null;
            }
            return "onStateChange";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISystemPersonaObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISystemPersonaObserver.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onPersonaActive(readInt);
                parcel2.writeNoException();
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onRemovePersona(readInt2);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onResetPersona(readInt3);
                parcel2.writeNoException();
            } else if (i == 4) {
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onKnoxContainerLaunch(readInt4);
                parcel2.writeNoException();
            } else if (i == 5) {
                int readInt5 = parcel.readInt();
                SemPersonaState semPersonaState = (SemPersonaState) parcel.readTypedObject(SemPersonaState.CREATOR);
                SemPersonaState semPersonaState2 = (SemPersonaState) parcel.readTypedObject(SemPersonaState.CREATOR);
                parcel.enforceNoDataAvail();
                onStateChange(readInt5, semPersonaState, semPersonaState2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISystemPersonaObserver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISystemPersonaObserver.DESCRIPTOR;
            }

            @Override // android.content.pm.ISystemPersonaObserver
            public void onPersonaActive(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISystemPersonaObserver.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ISystemPersonaObserver
            public void onRemovePersona(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISystemPersonaObserver.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ISystemPersonaObserver
            public void onResetPersona(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISystemPersonaObserver.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ISystemPersonaObserver
            public void onKnoxContainerLaunch(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISystemPersonaObserver.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ISystemPersonaObserver
            public void onStateChange(int i, SemPersonaState semPersonaState, SemPersonaState semPersonaState2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISystemPersonaObserver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(semPersonaState, 0);
                    obtain.writeTypedObject(semPersonaState2, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
