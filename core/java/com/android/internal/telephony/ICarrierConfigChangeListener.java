package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ICarrierConfigChangeListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.telephony.ICarrierConfigChangeListener";

    void onCarrierConfigChanged(int i, int i2, int i3, int i4) throws RemoteException;

    public static class Default implements ICarrierConfigChangeListener {
        @Override // com.android.internal.telephony.ICarrierConfigChangeListener
        public void onCarrierConfigChanged(
                int slotIndex, int subId, int carrierId, int specificCarrierId)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ICarrierConfigChangeListener {
        static final int TRANSACTION_onCarrierConfigChanged = 1;

        public Stub() {
            attachInterface(this, ICarrierConfigChangeListener.DESCRIPTOR);
        }

        public static ICarrierConfigChangeListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICarrierConfigChangeListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ICarrierConfigChangeListener)) {
                return (ICarrierConfigChangeListener) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onCarrierConfigChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICarrierConfigChangeListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICarrierConfigChangeListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    onCarrierConfigChanged(_arg0, _arg1, _arg2, _arg3);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICarrierConfigChangeListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICarrierConfigChangeListener.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ICarrierConfigChangeListener
            public void onCarrierConfigChanged(
                    int slotIndex, int subId, int carrierId, int specificCarrierId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICarrierConfigChangeListener.DESCRIPTOR);
                    _data.writeInt(slotIndex);
                    _data.writeInt(subId);
                    _data.writeInt(carrierId);
                    _data.writeInt(specificCarrierId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
