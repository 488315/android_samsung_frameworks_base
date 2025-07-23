package com.samsung.android.remoteappmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IRemoteAppModeListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.remoteappmode.IRemoteAppModeListener";

    public static class Default implements IRemoteAppModeListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.remoteappmode.IRemoteAppModeListener
        public void onRemoteAppModeStateChanged(boolean z) throws RemoteException {
        }
    }

    void onRemoteAppModeStateChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteAppModeListener {
        static final int TRANSACTION_onRemoteAppModeStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IRemoteAppModeListener.DESCRIPTOR);
        }

        public static IRemoteAppModeListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRemoteAppModeListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteAppModeListener)) {
                return (IRemoteAppModeListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onRemoteAppModeStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteAppModeListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteAppModeListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onRemoteAppModeStateChanged(readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteAppModeListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteAppModeListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.remoteappmode.IRemoteAppModeListener
            public void onRemoteAppModeStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteAppModeListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
