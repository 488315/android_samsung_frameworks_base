package com.samsung.android.content.smartclip;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IBleSpenChargeLockStateChangedListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.content.smartclip.IBleSpenChargeLockStateChangedListener";

    public static class Default implements IBleSpenChargeLockStateChangedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.content.smartclip.IBleSpenChargeLockStateChangedListener
        public void onChanged(boolean z) throws RemoteException {
        }
    }

    void onChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IBleSpenChargeLockStateChangedListener {
        static final int TRANSACTION_onChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IBleSpenChargeLockStateChangedListener.DESCRIPTOR);
        }

        public static IBleSpenChargeLockStateChangedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBleSpenChargeLockStateChangedListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBleSpenChargeLockStateChangedListener)) {
                return (IBleSpenChargeLockStateChangedListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBleSpenChargeLockStateChangedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBleSpenChargeLockStateChangedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onChanged(z);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IBleSpenChargeLockStateChangedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBleSpenChargeLockStateChangedListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.content.smartclip.IBleSpenChargeLockStateChangedListener
            public void onChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBleSpenChargeLockStateChangedListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
