package com.samsung.android.remoteappmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IVirtualDisplayAliveChecker extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker";

    public static class Default implements IVirtualDisplayAliveChecker {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker
        public void onVirtualDisplayCreated(int i) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker
        public void onVirtualDisplayReleased(int i) throws RemoteException {
        }
    }

    void onVirtualDisplayCreated(int i) throws RemoteException;

    void onVirtualDisplayReleased(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IVirtualDisplayAliveChecker {
        static final int TRANSACTION_onVirtualDisplayCreated = 1;
        static final int TRANSACTION_onVirtualDisplayReleased = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IVirtualDisplayAliveChecker.DESCRIPTOR);
        }

        public static IVirtualDisplayAliveChecker asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVirtualDisplayAliveChecker.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVirtualDisplayAliveChecker)) {
                return (IVirtualDisplayAliveChecker) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onVirtualDisplayCreated";
            }
            if (i != 2) {
                return null;
            }
            return "onVirtualDisplayReleased";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVirtualDisplayAliveChecker.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVirtualDisplayAliveChecker.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onVirtualDisplayCreated(readInt);
                parcel2.writeNoException();
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onVirtualDisplayReleased(readInt2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVirtualDisplayAliveChecker {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualDisplayAliveChecker.DESCRIPTOR;
            }

            @Override // com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker
            public void onVirtualDisplayCreated(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDisplayAliveChecker.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.IVirtualDisplayAliveChecker
            public void onVirtualDisplayReleased(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVirtualDisplayAliveChecker.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
