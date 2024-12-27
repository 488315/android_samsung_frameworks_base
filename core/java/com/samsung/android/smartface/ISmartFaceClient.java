package com.samsung.android.smartface;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISmartFaceClient extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.smartface.ISmartFaceClient";

    void onInfo(int i, FaceInfo faceInfo, int i2) throws RemoteException;

    public static class Default implements ISmartFaceClient {
        @Override // com.samsung.android.smartface.ISmartFaceClient
        public void onInfo(int msg_type, FaceInfo data, int service_type) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISmartFaceClient {
        static final int TRANSACTION_onInfo = 1;

        public Stub() {
            attachInterface(this, ISmartFaceClient.DESCRIPTOR);
        }

        public static ISmartFaceClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISmartFaceClient.DESCRIPTOR);
            if (iin != null && (iin instanceof ISmartFaceClient)) {
                return (ISmartFaceClient) iin;
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
                    return "onInfo";
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
                data.enforceInterface(ISmartFaceClient.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISmartFaceClient.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    FaceInfo _arg1 = (FaceInfo) data.readTypedObject(FaceInfo.CREATOR);
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    onInfo(_arg0, _arg1, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISmartFaceClient {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISmartFaceClient.DESCRIPTOR;
            }

            @Override // com.samsung.android.smartface.ISmartFaceClient
            public void onInfo(int msg_type, FaceInfo data, int service_type)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISmartFaceClient.DESCRIPTOR);
                    _data.writeInt(msg_type);
                    _data.writeTypedObject(data, 0);
                    _data.writeInt(service_type);
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
