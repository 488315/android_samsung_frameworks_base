package com.samsung.android.multiwindow;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDexTransientCaptionDelayListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multiwindow.IDexTransientCaptionDelayListener";

    void onDelayChanged(int i) throws RemoteException;

    public static class Default implements IDexTransientCaptionDelayListener {
        @Override // com.samsung.android.multiwindow.IDexTransientCaptionDelayListener
        public void onDelayChanged(int delay) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDexTransientCaptionDelayListener {
        static final int TRANSACTION_onDelayChanged = 1;

        public Stub() {
            attachInterface(this, IDexTransientCaptionDelayListener.DESCRIPTOR);
        }

        public static IDexTransientCaptionDelayListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDexTransientCaptionDelayListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IDexTransientCaptionDelayListener)) {
                return (IDexTransientCaptionDelayListener) iin;
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
                    return "onDelayChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IDexTransientCaptionDelayListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDexTransientCaptionDelayListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onDelayChanged(_arg0);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDexTransientCaptionDelayListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDexTransientCaptionDelayListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.multiwindow.IDexTransientCaptionDelayListener
            public void onDelayChanged(int delay) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDexTransientCaptionDelayListener.DESCRIPTOR);
                    _data.writeInt(delay);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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
