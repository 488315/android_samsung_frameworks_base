package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ILongConsumer extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ILongConsumer";

    public static class Default implements ILongConsumer {
        @Override // com.android.internal.telephony.ILongConsumer
        public void accept(long j) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void accept(long j) throws RemoteException;

    public static abstract class Stub extends Binder implements ILongConsumer {
        static final int TRANSACTION_accept = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ILongConsumer.DESCRIPTOR);
        }

        public static ILongConsumer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ILongConsumer.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ILongConsumer)) {
                return (ILongConsumer) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "accept";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILongConsumer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILongConsumer.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long j = parcel.readLong();
                parcel.enforceNoDataAvail();
                accept(j);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ILongConsumer {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILongConsumer.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ILongConsumer
            public void accept(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ILongConsumer.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
