package android.companion.virtual.camera;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;

public interface IVirtualCameraCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.companion.virtual.camera.IVirtualCameraCallback";

    void onProcessCaptureRequest(int i, long j) throws RemoteException;

    void onStreamClosed(int i) throws RemoteException;

    void onStreamConfigured(int i, Surface surface, int i2, int i3, int i4) throws RemoteException;

    public static class Default implements IVirtualCameraCallback {
        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onStreamConfigured(
                int streamId, Surface surface, int width, int height, int format)
                throws RemoteException {}

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onProcessCaptureRequest(int streamId, long frameId) throws RemoteException {}

        @Override // android.companion.virtual.camera.IVirtualCameraCallback
        public void onStreamClosed(int streamId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IVirtualCameraCallback {
        static final int TRANSACTION_onProcessCaptureRequest = 2;
        static final int TRANSACTION_onStreamClosed = 3;
        static final int TRANSACTION_onStreamConfigured = 1;

        public Stub() {
            attachInterface(this, IVirtualCameraCallback.DESCRIPTOR);
        }

        public static IVirtualCameraCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVirtualCameraCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IVirtualCameraCallback)) {
                return (IVirtualCameraCallback) iin;
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
                    return "onStreamConfigured";
                case 2:
                    return "onProcessCaptureRequest";
                case 3:
                    return "onStreamClosed";
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
                data.enforceInterface(IVirtualCameraCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVirtualCameraCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    Surface _arg1 = (Surface) data.readTypedObject(Surface.CREATOR);
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    data.enforceNoDataAvail();
                    onStreamConfigured(_arg0, _arg1, _arg2, _arg3, _arg4);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    long _arg12 = data.readLong();
                    data.enforceNoDataAvail();
                    onProcessCaptureRequest(_arg02, _arg12);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    onStreamClosed(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVirtualCameraCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVirtualCameraCallback.DESCRIPTOR;
            }

            @Override // android.companion.virtual.camera.IVirtualCameraCallback
            public void onStreamConfigured(
                    int streamId, Surface surface, int width, int height, int format)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVirtualCameraCallback.DESCRIPTOR);
                    _data.writeInt(streamId);
                    _data.writeTypedObject(surface, 0);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeInt(format);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.camera.IVirtualCameraCallback
            public void onProcessCaptureRequest(int streamId, long frameId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVirtualCameraCallback.DESCRIPTOR);
                    _data.writeInt(streamId);
                    _data.writeLong(frameId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.companion.virtual.camera.IVirtualCameraCallback
            public void onStreamClosed(int streamId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVirtualCameraCallback.DESCRIPTOR);
                    _data.writeInt(streamId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
