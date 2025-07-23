package com.android.internal.policy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IKeyguardLockedStateListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.policy.IKeyguardLockedStateListener";

    public static class Default implements IKeyguardLockedStateListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.policy.IKeyguardLockedStateListener
        public void onKeyguardLockedStateChanged(boolean z) throws RemoteException {
        }
    }

    void onKeyguardLockedStateChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IKeyguardLockedStateListener {
        static final int TRANSACTION_onKeyguardLockedStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IKeyguardLockedStateListener.DESCRIPTOR);
        }

        public static IKeyguardLockedStateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKeyguardLockedStateListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKeyguardLockedStateListener)) {
                return (IKeyguardLockedStateListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onKeyguardLockedStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKeyguardLockedStateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKeyguardLockedStateListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onKeyguardLockedStateChanged(readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IKeyguardLockedStateListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeyguardLockedStateListener.DESCRIPTOR;
            }

            @Override // com.android.internal.policy.IKeyguardLockedStateListener
            public void onKeyguardLockedStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IKeyguardLockedStateListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
