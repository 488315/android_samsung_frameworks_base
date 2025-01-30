package com.samsung.android.hardware.context;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISemContextCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.hardware.context.ISemContextCallback";

    String getListenerInfo() throws RemoteException;

    void semContextCallback(SemContextEvent semContextEvent) throws RemoteException;

    public static class Default implements ISemContextCallback {
        @Override // com.samsung.android.hardware.context.ISemContextCallback
        public void semContextCallback(SemContextEvent scontextEvent) throws RemoteException {
        }

        @Override // com.samsung.android.hardware.context.ISemContextCallback
        public String getListenerInfo() throws RemoteException {
            return null;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemContextCallback {
        static final int TRANSACTION_getListenerInfo = 2;
        static final int TRANSACTION_semContextCallback = 1;

        public Stub() {
            attachInterface(this, ISemContextCallback.DESCRIPTOR);
        }

        public static ISemContextCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemContextCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemContextCallback)) {
                return (ISemContextCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "semContextCallback";
                case 2:
                    return "getListenerInfo";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemContextCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISemContextCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            SemContextEvent _arg0 = (SemContextEvent) data.readTypedObject(SemContextEvent.CREATOR);
                            data.enforceNoDataAvail();
                            semContextCallback(_arg0);
                            return true;
                        case 2:
                            String _result = getListenerInfo();
                            reply.writeNoException();
                            reply.writeString(_result);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISemContextCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemContextCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.hardware.context.ISemContextCallback
            public void semContextCallback(SemContextEvent scontextEvent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemContextCallback.DESCRIPTOR);
                    _data.writeTypedObject(scontextEvent, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.hardware.context.ISemContextCallback
            public String getListenerInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContextCallback.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
