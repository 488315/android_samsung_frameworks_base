package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IPrepareDownloadCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.telephony.euicc.IPrepareDownloadCallback";

    void onComplete(int i, byte[] bArr) throws RemoteException;

    public static class Default implements IPrepareDownloadCallback {
        @Override // com.android.internal.telephony.euicc.IPrepareDownloadCallback
        public void onComplete(int resultCode, byte[] response) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IPrepareDownloadCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, IPrepareDownloadCallback.DESCRIPTOR);
        }

        public static IPrepareDownloadCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IPrepareDownloadCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IPrepareDownloadCallback)) {
                return (IPrepareDownloadCallback) iin;
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
                    return "onComplete";
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
                data.enforceInterface(IPrepareDownloadCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IPrepareDownloadCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    byte[] _arg1 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onComplete(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPrepareDownloadCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPrepareDownloadCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IPrepareDownloadCallback
            public void onComplete(int resultCode, byte[] response) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPrepareDownloadCallback.DESCRIPTOR);
                    _data.writeInt(resultCode);
                    _data.writeByteArray(response);
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
