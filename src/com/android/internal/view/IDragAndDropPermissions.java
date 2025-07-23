package com.android.internal.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IDragAndDropPermissions extends IInterface {

    public static class Default implements IDragAndDropPermissions {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.view.IDragAndDropPermissions
        public int getFlags() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.view.IDragAndDropPermissions
        public void release() throws RemoteException {
        }

        @Override // com.android.internal.view.IDragAndDropPermissions
        public void take(IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.view.IDragAndDropPermissions
        public void takeTransient() throws RemoteException {
        }
    }

    int getFlags() throws RemoteException;

    void release() throws RemoteException;

    void take(IBinder iBinder) throws RemoteException;

    void takeTransient() throws RemoteException;

    public static abstract class Stub extends Binder implements IDragAndDropPermissions {
        public static final String DESCRIPTOR = "com.android.internal.view.IDragAndDropPermissions";
        static final int TRANSACTION_getFlags = 4;
        static final int TRANSACTION_release = 3;
        static final int TRANSACTION_take = 1;
        static final int TRANSACTION_takeTransient = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDragAndDropPermissions asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDragAndDropPermissions)) {
                return (IDragAndDropPermissions) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "take";
            }
            if (i == 2) {
                return "takeTransient";
            }
            if (i == 3) {
                return "release";
            }
            if (i != 4) {
                return null;
            }
            return "getFlags";
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
                IBinder readStrongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                take(readStrongBinder);
                parcel2.writeNoException();
            } else if (i == 2) {
                takeTransient();
                parcel2.writeNoException();
            } else if (i == 3) {
                release();
                parcel2.writeNoException();
            } else if (i == 4) {
                int flags = getFlags();
                parcel2.writeNoException();
                parcel2.writeInt(flags);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDragAndDropPermissions {
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

            @Override // com.android.internal.view.IDragAndDropPermissions
            public void take(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IDragAndDropPermissions
            public void takeTransient() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IDragAndDropPermissions
            public void release() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.view.IDragAndDropPermissions
            public int getFlags() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
