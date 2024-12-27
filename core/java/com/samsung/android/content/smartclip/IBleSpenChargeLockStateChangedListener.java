package com.samsung.android.content.smartclip;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBleSpenChargeLockStateChangedListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.content.smartclip.IBleSpenChargeLockStateChangedListener";

    void onChanged(boolean z) throws RemoteException;

    public static class Default implements IBleSpenChargeLockStateChangedListener {
        @Override // com.samsung.android.content.smartclip.IBleSpenChargeLockStateChangedListener
        public void onChanged(boolean isLocked) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder
            implements IBleSpenChargeLockStateChangedListener {
        static final int TRANSACTION_onChanged = 1;

        public Stub() {
            attachInterface(this, IBleSpenChargeLockStateChangedListener.DESCRIPTOR);
        }

        public static IBleSpenChargeLockStateChangedListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin =
                    obj.queryLocalInterface(IBleSpenChargeLockStateChangedListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IBleSpenChargeLockStateChangedListener)) {
                return (IBleSpenChargeLockStateChangedListener) iin;
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
                    return "onChanged";
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
                data.enforceInterface(IBleSpenChargeLockStateChangedListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IBleSpenChargeLockStateChangedListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBleSpenChargeLockStateChangedListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBleSpenChargeLockStateChangedListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.content.smartclip.IBleSpenChargeLockStateChangedListener
            public void onChanged(boolean isLocked) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBleSpenChargeLockStateChangedListener.DESCRIPTOR);
                    _data.writeBoolean(isLocked);
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
