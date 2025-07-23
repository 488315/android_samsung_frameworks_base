package com.android.internal.policy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IDeviceLockedStateListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.policy.IDeviceLockedStateListener";

    public static class Default implements IDeviceLockedStateListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.policy.IDeviceLockedStateListener
        public void onDeviceLockedStateChanged(boolean z) throws RemoteException {
        }
    }

    void onDeviceLockedStateChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IDeviceLockedStateListener {
        static final int TRANSACTION_onDeviceLockedStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDeviceLockedStateListener.DESCRIPTOR);
        }

        public static IDeviceLockedStateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDeviceLockedStateListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDeviceLockedStateListener)) {
                return (IDeviceLockedStateListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onDeviceLockedStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDeviceLockedStateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDeviceLockedStateListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onDeviceLockedStateChanged(readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDeviceLockedStateListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDeviceLockedStateListener.DESCRIPTOR;
            }

            @Override // com.android.internal.policy.IDeviceLockedStateListener
            public void onDeviceLockedStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDeviceLockedStateListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
