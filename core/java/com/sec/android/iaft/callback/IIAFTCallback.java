package com.sec.android.iaft.callback;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IIAFTCallback extends IInterface {
    public static final String DESCRIPTOR = "com.sec.android.iaft.callback.IIAFTCallback";

    void traceResult(String str, int i, int i2, int i3, int i4) throws RemoteException;

    public static class Default implements IIAFTCallback {
        @Override // com.sec.android.iaft.callback.IIAFTCallback
        public void traceResult(String packageName, int tid, int code, int freq, int policy)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IIAFTCallback {
        static final int TRANSACTION_traceResult = 1;

        public Stub() {
            attachInterface(this, IIAFTCallback.DESCRIPTOR);
        }

        public static IIAFTCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IIAFTCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IIAFTCallback)) {
                return (IIAFTCallback) iin;
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
                    return "traceResult";
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
                data.enforceInterface(IIAFTCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IIAFTCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    traceResult(_arg0, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IIAFTCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIAFTCallback.DESCRIPTOR;
            }

            @Override // com.sec.android.iaft.callback.IIAFTCallback
            public void traceResult(String packageName, int tid, int code, int freq, int policy)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IIAFTCallback.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(tid);
                    _data.writeInt(code);
                    _data.writeInt(freq);
                    _data.writeInt(policy);
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
