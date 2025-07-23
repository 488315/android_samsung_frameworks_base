package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ICarrierConfigChangeListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ICarrierConfigChangeListener";

    public static class Default implements ICarrierConfigChangeListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ICarrierConfigChangeListener
        public void onCarrierConfigChanged(int i, int i2, int i3, int i4) throws RemoteException {
        }
    }

    void onCarrierConfigChanged(int i, int i2, int i3, int i4) throws RemoteException;

    public static abstract class Stub extends Binder implements ICarrierConfigChangeListener {
        static final int TRANSACTION_onCarrierConfigChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ICarrierConfigChangeListener.DESCRIPTOR);
        }

        public static ICarrierConfigChangeListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICarrierConfigChangeListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICarrierConfigChangeListener)) {
                return (ICarrierConfigChangeListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onCarrierConfigChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICarrierConfigChangeListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICarrierConfigChangeListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCarrierConfigChanged(readInt, readInt2, readInt3, readInt4);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICarrierConfigChangeListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICarrierConfigChangeListener.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ICarrierConfigChangeListener
            public void onCarrierConfigChanged(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICarrierConfigChangeListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
