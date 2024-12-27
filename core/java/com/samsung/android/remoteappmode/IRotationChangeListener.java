package com.samsung.android.remoteappmode;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IRotationChangeListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.remoteappmode.IRotationChangeListener";

    void onRotationChanged(int i, int i2) throws RemoteException;

    public static class Default implements IRotationChangeListener {
        @Override // com.samsung.android.remoteappmode.IRotationChangeListener
        public void onRotationChanged(int displayId, int rotation) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRotationChangeListener {
        static final int TRANSACTION_onRotationChanged = 1;

        public Stub() {
            attachInterface(this, IRotationChangeListener.DESCRIPTOR);
        }

        public static IRotationChangeListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRotationChangeListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IRotationChangeListener)) {
                return (IRotationChangeListener) iin;
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
                    return "onRotationChanged";
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
                data.enforceInterface(IRotationChangeListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRotationChangeListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    onRotationChanged(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRotationChangeListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRotationChangeListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.remoteappmode.IRotationChangeListener
            public void onRotationChanged(int displayId, int rotation) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRotationChangeListener.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(rotation);
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
