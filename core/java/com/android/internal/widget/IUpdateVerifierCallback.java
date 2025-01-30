package com.android.internal.widget;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IUpdateVerifierCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.widget.IUpdateVerifierCallback";

    void onReceiveSaGuid(String str) throws RemoteException;

    public static class Default implements IUpdateVerifierCallback {
        @Override // com.android.internal.widget.IUpdateVerifierCallback
        public void onReceiveSaGuid(String saGuid) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUpdateVerifierCallback {
        static final int TRANSACTION_onReceiveSaGuid = 1;

        public Stub() {
            attachInterface(this, IUpdateVerifierCallback.DESCRIPTOR);
        }

        public static IUpdateVerifierCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IUpdateVerifierCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IUpdateVerifierCallback)) {
                return (IUpdateVerifierCallback) iin;
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
                    return "onReceiveSaGuid";
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
                data.enforceInterface(IUpdateVerifierCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IUpdateVerifierCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            String _arg0 = data.readString();
                            data.enforceNoDataAvail();
                            onReceiveSaGuid(_arg0);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IUpdateVerifierCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUpdateVerifierCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.IUpdateVerifierCallback
            public void onReceiveSaGuid(String saGuid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IUpdateVerifierCallback.DESCRIPTOR);
                    _data.writeString(saGuid);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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
