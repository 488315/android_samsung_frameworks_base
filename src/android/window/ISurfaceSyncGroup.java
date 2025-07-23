package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ISurfaceSyncGroup extends IInterface {
    public static final String DESCRIPTOR = "android.window.ISurfaceSyncGroup";

    public static class Default implements ISurfaceSyncGroup {
        @Override // android.window.ISurfaceSyncGroup
        public boolean addToSync(ISurfaceSyncGroup iSurfaceSyncGroup, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.ISurfaceSyncGroup
        public boolean onAddedToSyncGroup(IBinder iBinder, boolean z) throws RemoteException {
            return false;
        }
    }

    boolean addToSync(ISurfaceSyncGroup iSurfaceSyncGroup, boolean z) throws RemoteException;

    boolean onAddedToSyncGroup(IBinder iBinder, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISurfaceSyncGroup {
        static final int TRANSACTION_addToSync = 2;
        static final int TRANSACTION_onAddedToSyncGroup = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISurfaceSyncGroup.DESCRIPTOR);
        }

        public static ISurfaceSyncGroup asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISurfaceSyncGroup.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISurfaceSyncGroup)) {
                return (ISurfaceSyncGroup) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onAddedToSyncGroup";
            }
            if (i != 2) {
                return null;
            }
            return "addToSync";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISurfaceSyncGroup.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISurfaceSyncGroup.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                boolean onAddedToSyncGroup = onAddedToSyncGroup(readStrongBinder, readBoolean);
                parcel2.writeNoException();
                parcel2.writeBoolean(onAddedToSyncGroup);
            } else if (i == 2) {
                ISurfaceSyncGroup asInterface = asInterface(parcel.readStrongBinder());
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                boolean addToSync = addToSync(asInterface, readBoolean2);
                parcel2.writeNoException();
                parcel2.writeBoolean(addToSync);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISurfaceSyncGroup {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISurfaceSyncGroup.DESCRIPTOR;
            }

            @Override // android.window.ISurfaceSyncGroup
            public boolean onAddedToSyncGroup(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISurfaceSyncGroup.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.window.ISurfaceSyncGroup
            public boolean addToSync(ISurfaceSyncGroup iSurfaceSyncGroup, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISurfaceSyncGroup.DESCRIPTOR);
                    obtain.writeStrongInterface(iSurfaceSyncGroup);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
