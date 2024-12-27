package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IStreamingCallAdapter extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.IStreamingCallAdapter";

    void setStreamingState(int i) throws RemoteException;

    public static class Default implements IStreamingCallAdapter {
        @Override // com.android.internal.telecom.IStreamingCallAdapter
        public void setStreamingState(int state) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IStreamingCallAdapter {
        static final int TRANSACTION_setStreamingState = 1;

        public Stub() {
            attachInterface(this, IStreamingCallAdapter.DESCRIPTOR);
        }

        public static IStreamingCallAdapter asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IStreamingCallAdapter.DESCRIPTOR);
            if (iin != null && (iin instanceof IStreamingCallAdapter)) {
                return (IStreamingCallAdapter) iin;
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
                    return "setStreamingState";
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
                data.enforceInterface(IStreamingCallAdapter.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IStreamingCallAdapter.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    setStreamingState(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IStreamingCallAdapter {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStreamingCallAdapter.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IStreamingCallAdapter
            public void setStreamingState(int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStreamingCallAdapter.DESCRIPTOR);
                    _data.writeInt(state);
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
