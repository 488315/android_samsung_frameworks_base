package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IDockedStackListener extends IInterface {

    public static class Default implements IDockedStackListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.IDockedStackListener
        public void onAdjustedForImeChanged(boolean z, long j) throws RemoteException {
        }

        @Override // android.view.IDockedStackListener
        public void onDividerVisibilityChanged(boolean z) throws RemoteException {
        }

        @Override // android.view.IDockedStackListener
        public void onDockSideChanged(int i) throws RemoteException {
        }

        @Override // android.view.IDockedStackListener
        public void onDockedStackExistsChanged(boolean z) throws RemoteException {
        }

        @Override // android.view.IDockedStackListener
        public void onDockedStackMinimizedChanged(boolean z, long j, boolean z2) throws RemoteException {
        }
    }

    void onAdjustedForImeChanged(boolean z, long j) throws RemoteException;

    void onDividerVisibilityChanged(boolean z) throws RemoteException;

    void onDockSideChanged(int i) throws RemoteException;

    void onDockedStackExistsChanged(boolean z) throws RemoteException;

    void onDockedStackMinimizedChanged(boolean z, long j, boolean z2) throws RemoteException;

    public static abstract class Stub extends Binder implements IDockedStackListener {
        public static final String DESCRIPTOR = "android.view.IDockedStackListener";
        static final int TRANSACTION_onAdjustedForImeChanged = 4;
        static final int TRANSACTION_onDividerVisibilityChanged = 1;
        static final int TRANSACTION_onDockSideChanged = 5;
        static final int TRANSACTION_onDockedStackExistsChanged = 2;
        static final int TRANSACTION_onDockedStackMinimizedChanged = 3;

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

        public static IDockedStackListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDockedStackListener)) {
                return (IDockedStackListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onDividerVisibilityChanged";
            }
            if (i == 2) {
                return "onDockedStackExistsChanged";
            }
            if (i == 3) {
                return "onDockedStackMinimizedChanged";
            }
            if (i == 4) {
                return "onAdjustedForImeChanged";
            }
            if (i != 5) {
                return null;
            }
            return "onDockSideChanged";
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
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onDividerVisibilityChanged(readBoolean);
            } else if (i == 2) {
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onDockedStackExistsChanged(readBoolean2);
            } else if (i == 3) {
                boolean readBoolean3 = parcel.readBoolean();
                long readLong = parcel.readLong();
                boolean readBoolean4 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onDockedStackMinimizedChanged(readBoolean3, readLong, readBoolean4);
            } else if (i == 4) {
                boolean readBoolean5 = parcel.readBoolean();
                long readLong2 = parcel.readLong();
                parcel.enforceNoDataAvail();
                onAdjustedForImeChanged(readBoolean5, readLong2);
            } else if (i == 5) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onDockSideChanged(readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDockedStackListener {
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

            @Override // android.view.IDockedStackListener
            public void onDividerVisibilityChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IDockedStackListener
            public void onDockedStackExistsChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IDockedStackListener
            public void onDockedStackMinimizedChanged(boolean z, long j, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IDockedStackListener
            public void onAdjustedForImeChanged(boolean z, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IDockedStackListener
            public void onDockSideChanged(int i) throws RemoteException {
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
