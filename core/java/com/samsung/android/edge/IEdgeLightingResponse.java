package com.samsung.android.edge;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IEdgeLightingResponse extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.edge.IEdgeLightingResponse";

    void onFinished() throws RemoteException;

    public static class Default implements IEdgeLightingResponse {
        @Override // com.samsung.android.edge.IEdgeLightingResponse
        public void onFinished() throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IEdgeLightingResponse {
        static final int TRANSACTION_onFinished = 1;

        public Stub() {
            attachInterface(this, IEdgeLightingResponse.DESCRIPTOR);
        }

        public static IEdgeLightingResponse asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IEdgeLightingResponse.DESCRIPTOR);
            if (iin != null && (iin instanceof IEdgeLightingResponse)) {
                return (IEdgeLightingResponse) iin;
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
                    return "onFinished";
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
                data.enforceInterface(IEdgeLightingResponse.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IEdgeLightingResponse.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            onFinished();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IEdgeLightingResponse {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IEdgeLightingResponse.DESCRIPTOR;
            }

            @Override // com.samsung.android.edge.IEdgeLightingResponse
            public void onFinished() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IEdgeLightingResponse.DESCRIPTOR);
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
