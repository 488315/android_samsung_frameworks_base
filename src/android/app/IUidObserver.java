package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IUidObserver extends IInterface {

    public static class Default implements IUidObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IUidObserver
        public void onUidActive(int i) throws RemoteException {
        }

        @Override // android.app.IUidObserver
        public void onUidCachedChanged(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.IUidObserver
        public void onUidGone(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.IUidObserver
        public void onUidIdle(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.IUidObserver
        public void onUidProcAdjChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.app.IUidObserver
        public void onUidStateChanged(int i, int i2, long j, int i3) throws RemoteException {
        }
    }

    void onUidActive(int i) throws RemoteException;

    void onUidCachedChanged(int i, boolean z) throws RemoteException;

    void onUidGone(int i, boolean z) throws RemoteException;

    void onUidIdle(int i, boolean z) throws RemoteException;

    void onUidProcAdjChanged(int i, int i2) throws RemoteException;

    void onUidStateChanged(int i, int i2, long j, int i3) throws RemoteException;

    public static abstract class Stub extends Binder implements IUidObserver {
        public static final String DESCRIPTOR = "android.app.IUidObserver";
        static final int TRANSACTION_onUidActive = 2;
        static final int TRANSACTION_onUidCachedChanged = 6;
        static final int TRANSACTION_onUidGone = 1;
        static final int TRANSACTION_onUidIdle = 3;
        static final int TRANSACTION_onUidProcAdjChanged = 5;
        static final int TRANSACTION_onUidStateChanged = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUidObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUidObserver)) {
                return (IUidObserver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUidGone";
                case 2:
                    return "onUidActive";
                case 3:
                    return "onUidIdle";
                case 4:
                    return "onUidStateChanged";
                case 5:
                    return "onUidProcAdjChanged";
                case 6:
                    return "onUidCachedChanged";
                default:
                    return null;
            }
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
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUidGone(readInt, readBoolean);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUidActive(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUidIdle(readInt3, readBoolean2);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    long readLong = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUidStateChanged(readInt4, readInt5, readLong, readInt6);
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUidProcAdjChanged(readInt7, readInt8);
                    return true;
                case 6:
                    int readInt9 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUidCachedChanged(readInt9, readBoolean3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IUidObserver {
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

            @Override // android.app.IUidObserver
            public void onUidGone(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUidObserver
            public void onUidActive(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUidObserver
            public void onUidIdle(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUidObserver
            public void onUidStateChanged(int i, int i2, long j, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    obtain.writeInt(i3);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUidObserver
            public void onUidProcAdjChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IUidObserver
            public void onUidCachedChanged(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
