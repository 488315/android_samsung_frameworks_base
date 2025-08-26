package com.android.internal.widget;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IWeakEscrowTokenActivatedListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.widget.IWeakEscrowTokenActivatedListener";

    public static class Default implements IWeakEscrowTokenActivatedListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.widget.IWeakEscrowTokenActivatedListener
        public void onWeakEscrowTokenActivated(long j, int i) throws RemoteException {
        }
    }

    void onWeakEscrowTokenActivated(long j, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IWeakEscrowTokenActivatedListener {
        static final int TRANSACTION_onWeakEscrowTokenActivated = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IWeakEscrowTokenActivatedListener.DESCRIPTOR);
        }

        public static IWeakEscrowTokenActivatedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IWeakEscrowTokenActivatedListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IWeakEscrowTokenActivatedListener)) {
                return (IWeakEscrowTokenActivatedListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onWeakEscrowTokenActivated";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWeakEscrowTokenActivatedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWeakEscrowTokenActivatedListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long j = parcel.readLong();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onWeakEscrowTokenActivated(j, i3);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IWeakEscrowTokenActivatedListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWeakEscrowTokenActivatedListener.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.IWeakEscrowTokenActivatedListener
            public void onWeakEscrowTokenActivated(long j, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IWeakEscrowTokenActivatedListener.DESCRIPTOR);
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
