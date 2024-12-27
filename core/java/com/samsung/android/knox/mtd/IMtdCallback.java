package com.samsung.android.knox.mtd;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface IMtdCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.mtd.IMtdCallback";

    void onFinished(List<AnalysisResult> list) throws RemoteException;

    public static class Default implements IMtdCallback {
        @Override // com.samsung.android.knox.mtd.IMtdCallback
        public void onFinished(List<AnalysisResult> result) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IMtdCallback {
        static final int TRANSACTION_onFinished = 1;

        public Stub() {
            attachInterface(this, IMtdCallback.DESCRIPTOR);
        }

        public static IMtdCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMtdCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IMtdCallback)) {
                return (IMtdCallback) iin;
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
                    return "onFinished";
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
                data.enforceInterface(IMtdCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IMtdCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<AnalysisResult> _arg0 = data.createTypedArrayList(AnalysisResult.CREATOR);
                    data.enforceNoDataAvail();
                    onFinished(_arg0);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMtdCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMtdCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.mtd.IMtdCallback
            public void onFinished(List<AnalysisResult> result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IMtdCallback.DESCRIPTOR);
                    _data.writeTypedList(result, 0);
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
