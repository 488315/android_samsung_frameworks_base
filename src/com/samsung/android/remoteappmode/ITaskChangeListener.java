package com.samsung.android.remoteappmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ITaskChangeListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.remoteappmode.ITaskChangeListener";

    public static class Default implements ITaskChangeListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onRecentTaskListUpdated() throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskDisplayChanged(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskPlayed(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskRemoved(int i) throws RemoteException {
        }

        @Override // com.samsung.android.remoteappmode.ITaskChangeListener
        public void onTaskTriedToGoToBackground(int i, int i2) throws RemoteException {
        }
    }

    void onRecentTaskListUpdated() throws RemoteException;

    void onTaskDisplayChanged(int i, int i2) throws RemoteException;

    void onTaskPlayed(int i, int i2) throws RemoteException;

    void onTaskRemoved(int i) throws RemoteException;

    void onTaskTriedToGoToBackground(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements ITaskChangeListener {
        static final int TRANSACTION_onRecentTaskListUpdated = 5;
        static final int TRANSACTION_onTaskDisplayChanged = 4;
        static final int TRANSACTION_onTaskPlayed = 2;
        static final int TRANSACTION_onTaskRemoved = 1;
        static final int TRANSACTION_onTaskTriedToGoToBackground = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ITaskChangeListener.DESCRIPTOR);
        }

        public static ITaskChangeListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITaskChangeListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITaskChangeListener)) {
                return (ITaskChangeListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onTaskRemoved";
            }
            if (i == 2) {
                return "onTaskPlayed";
            }
            if (i == 3) {
                return "onTaskTriedToGoToBackground";
            }
            if (i == 4) {
                return "onTaskDisplayChanged";
            }
            if (i != 5) {
                return null;
            }
            return "onRecentTaskListUpdated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITaskChangeListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITaskChangeListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onTaskRemoved(i3);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onTaskPlayed(i4, i5);
            } else if (i == 3) {
                int i6 = parcel.readInt();
                int i7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onTaskTriedToGoToBackground(i6, i7);
            } else if (i == 4) {
                int i8 = parcel.readInt();
                int i9 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onTaskDisplayChanged(i8, i9);
            } else if (i == 5) {
                onRecentTaskListUpdated();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITaskChangeListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITaskChangeListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.remoteappmode.ITaskChangeListener
            public void onTaskRemoved(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskChangeListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.ITaskChangeListener
            public void onTaskPlayed(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskChangeListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.ITaskChangeListener
            public void onTaskTriedToGoToBackground(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskChangeListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.ITaskChangeListener
            public void onTaskDisplayChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskChangeListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.remoteappmode.ITaskChangeListener
            public void onRecentTaskListUpdated() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskChangeListener.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
