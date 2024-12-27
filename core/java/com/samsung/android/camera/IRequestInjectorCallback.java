package com.samsung.android.camera;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRequestInjectorCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.camera.IRequestInjectorCallback";

    void onCaptureResult(
            CameraMetadataNative cameraMetadataNative, String str, String str2, int i, long j)
            throws RemoteException;

    public static class Default implements IRequestInjectorCallback {
        @Override // com.samsung.android.camera.IRequestInjectorCallback
        public void onCaptureResult(
                CameraMetadataNative result,
                String clientName,
                String cameraId,
                int requestId,
                long frameNumber)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRequestInjectorCallback {
        static final int TRANSACTION_onCaptureResult = 1;

        public Stub() {
            attachInterface(this, IRequestInjectorCallback.DESCRIPTOR);
        }

        public static IRequestInjectorCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRequestInjectorCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IRequestInjectorCallback)) {
                return (IRequestInjectorCallback) iin;
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
                    return "onCaptureResult";
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
                data.enforceInterface(IRequestInjectorCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRequestInjectorCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    CameraMetadataNative _arg0 =
                            (CameraMetadataNative)
                                    data.readTypedObject(CameraMetadataNative.CREATOR);
                    String _arg1 = data.readString();
                    String _arg2 = data.readString();
                    int _arg3 = data.readInt();
                    long _arg4 = data.readLong();
                    data.enforceNoDataAvail();
                    onCaptureResult(_arg0, _arg1, _arg2, _arg3, _arg4);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRequestInjectorCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRequestInjectorCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.camera.IRequestInjectorCallback
            public void onCaptureResult(
                    CameraMetadataNative result,
                    String clientName,
                    String cameraId,
                    int requestId,
                    long frameNumber)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRequestInjectorCallback.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
                    _data.writeString(clientName);
                    _data.writeString(cameraId);
                    _data.writeInt(requestId);
                    _data.writeLong(frameNumber);
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
