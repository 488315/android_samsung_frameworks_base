package com.android.internal.app;

import android.app.AsyncNotedAppOp;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes4.dex */
public interface IAppOpsAsyncNotedCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.app.IAppOpsAsyncNotedCallback";

    void opNoted(AsyncNotedAppOp asyncNotedAppOp) throws RemoteException;

    public static class Default implements IAppOpsAsyncNotedCallback {
        @Override // com.android.internal.app.IAppOpsAsyncNotedCallback
        public void opNoted(AsyncNotedAppOp op) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAppOpsAsyncNotedCallback {
        static final int TRANSACTION_opNoted = 1;

        public Stub() {
            attachInterface(this, IAppOpsAsyncNotedCallback.DESCRIPTOR);
        }

        public static IAppOpsAsyncNotedCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAppOpsAsyncNotedCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IAppOpsAsyncNotedCallback)) {
                return (IAppOpsAsyncNotedCallback) iin;
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
                    return "opNoted";
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
                data.enforceInterface(IAppOpsAsyncNotedCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IAppOpsAsyncNotedCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            AsyncNotedAppOp _arg0 = (AsyncNotedAppOp) data.readTypedObject(AsyncNotedAppOp.CREATOR);
                            data.enforceNoDataAvail();
                            opNoted(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IAppOpsAsyncNotedCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppOpsAsyncNotedCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.app.IAppOpsAsyncNotedCallback
            public void opNoted(AsyncNotedAppOp op) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAppOpsAsyncNotedCallback.DESCRIPTOR);
                    _data.writeTypedObject(op, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
