package com.android.internal.widget;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IWeakEscrowTokenRemovedListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.widget.IWeakEscrowTokenRemovedListener";

    public static class Default implements IWeakEscrowTokenRemovedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.widget.IWeakEscrowTokenRemovedListener
        public void onWeakEscrowTokenRemoved(long j, int i) throws RemoteException {
        }
    }

    void onWeakEscrowTokenRemoved(long j, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IWeakEscrowTokenRemovedListener {
        static final int TRANSACTION_onWeakEscrowTokenRemoved = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IWeakEscrowTokenRemovedListener.DESCRIPTOR);
        }

        public static IWeakEscrowTokenRemovedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IWeakEscrowTokenRemovedListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWeakEscrowTokenRemovedListener)) {
                return (IWeakEscrowTokenRemovedListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onWeakEscrowTokenRemoved";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWeakEscrowTokenRemovedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWeakEscrowTokenRemovedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long j = parcel.readLong();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onWeakEscrowTokenRemoved(j, i3);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IWeakEscrowTokenRemovedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWeakEscrowTokenRemovedListener.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.IWeakEscrowTokenRemovedListener
            public void onWeakEscrowTokenRemoved(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWeakEscrowTokenRemovedListener.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
