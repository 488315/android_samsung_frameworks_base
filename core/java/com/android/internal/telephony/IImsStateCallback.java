package com.android.internal.telephony;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IImsStateCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IImsStateCallback";

    void onAvailable() throws RemoteException;

    void onUnavailable(int i) throws RemoteException;

    public static class Default implements IImsStateCallback {
        @Override // com.android.internal.telephony.IImsStateCallback
        public void onUnavailable(int reason) throws RemoteException {
        }

        @Override // com.android.internal.telephony.IImsStateCallback
        public void onAvailable() throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImsStateCallback {
        static final int TRANSACTION_onAvailable = 2;
        static final int TRANSACTION_onUnavailable = 1;

        public Stub() {
            attachInterface(this, IImsStateCallback.DESCRIPTOR);
        }

        public static IImsStateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IImsStateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IImsStateCallback)) {
                return (IImsStateCallback) iin;
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
                    return "onUnavailable";
                case 2:
                    return "onAvailable";
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
                data.enforceInterface(IImsStateCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IImsStateCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            onUnavailable(_arg0);
                            return true;
                        case 2:
                            onAvailable();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IImsStateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsStateCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IImsStateCallback
            public void onUnavailable(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsStateCallback.DESCRIPTOR);
                    _data.writeInt(reason);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telephony.IImsStateCallback
            public void onAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IImsStateCallback.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
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
