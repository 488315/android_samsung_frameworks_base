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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKeyguardLockedStateListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKeyguardLockedStateListener)) {
                return (IKeyguardLockedStateListener) iInterfaceQueryLocalInterface;
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
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onKeyguardLockedStateChanged(z);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IKeyguardLockedStateListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
