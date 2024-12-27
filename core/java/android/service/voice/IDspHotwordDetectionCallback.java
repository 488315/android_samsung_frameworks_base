package android.service.voice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDspHotwordDetectionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.voice.IDspHotwordDetectionCallback";

    void onDetected(HotwordDetectedResult hotwordDetectedResult) throws RemoteException;

    void onRejected(HotwordRejectedResult hotwordRejectedResult) throws RemoteException;

    public static class Default implements IDspHotwordDetectionCallback {
        @Override // android.service.voice.IDspHotwordDetectionCallback
        public void onDetected(HotwordDetectedResult hotwordDetectedResult)
                throws RemoteException {}

        @Override // android.service.voice.IDspHotwordDetectionCallback
        public void onRejected(HotwordRejectedResult result) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDspHotwordDetectionCallback {
        static final int TRANSACTION_onDetected = 1;
        static final int TRANSACTION_onRejected = 2;

        public Stub() {
            attachInterface(this, IDspHotwordDetectionCallback.DESCRIPTOR);
        }

        public static IDspHotwordDetectionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDspHotwordDetectionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IDspHotwordDetectionCallback)) {
                return (IDspHotwordDetectionCallback) iin;
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
                    return "onDetected";
                case 2:
                    return "onRejected";
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
                data.enforceInterface(IDspHotwordDetectionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDspHotwordDetectionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    HotwordDetectedResult _arg0 =
                            (HotwordDetectedResult)
                                    data.readTypedObject(HotwordDetectedResult.CREATOR);
                    data.enforceNoDataAvail();
                    onDetected(_arg0);
                    return true;
                case 2:
                    HotwordRejectedResult _arg02 =
                            (HotwordRejectedResult)
                                    data.readTypedObject(HotwordRejectedResult.CREATOR);
                    data.enforceNoDataAvail();
                    onRejected(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDspHotwordDetectionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDspHotwordDetectionCallback.DESCRIPTOR;
            }

            @Override // android.service.voice.IDspHotwordDetectionCallback
            public void onDetected(HotwordDetectedResult hotwordDetectedResult)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDspHotwordDetectionCallback.DESCRIPTOR);
                    _data.writeTypedObject(hotwordDetectedResult, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.voice.IDspHotwordDetectionCallback
            public void onRejected(HotwordRejectedResult result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDspHotwordDetectionCallback.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
