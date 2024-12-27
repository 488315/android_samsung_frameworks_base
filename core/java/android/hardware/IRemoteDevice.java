package android.hardware;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.params.OutputConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.RcsContactPresenceTuple;

/* loaded from: classes.dex */
public interface IRemoteDevice extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.IRemoteDevice";

    void clearRequest() throws RemoteException;

    void close() throws RemoteException;

    CameraMetadataNative createDefaultRequest() throws RemoteException;

    int createStream(OutputConfiguration outputConfiguration) throws RemoteException;

    void deleteStream(int i) throws RemoteException;

    CameraMetadataNative getCameraCharacteristic() throws RemoteException;

    String open(String str, int i) throws RemoteException;

    void setCallback(IRemoteDeviceCallback iRemoteDeviceCallback) throws RemoteException;

    void submitRequest(CameraMetadataNative cameraMetadataNative, int[] iArr, boolean z)
            throws RemoteException;

    public static class Default implements IRemoteDevice {
        @Override // android.hardware.IRemoteDevice
        public String open(String targetId, int targetLensFacing) throws RemoteException {
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public CameraMetadataNative getCameraCharacteristic() throws RemoteException {
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public int createStream(OutputConfiguration outputConfiguration) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.IRemoteDevice
        public void deleteStream(int streamId) throws RemoteException {}

        @Override // android.hardware.IRemoteDevice
        public CameraMetadataNative createDefaultRequest() throws RemoteException {
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public void submitRequest(
                CameraMetadataNative request, int[] outputStreams, boolean streaming)
                throws RemoteException {}

        @Override // android.hardware.IRemoteDevice
        public void clearRequest() throws RemoteException {}

        @Override // android.hardware.IRemoteDevice
        public void setCallback(IRemoteDeviceCallback callback) throws RemoteException {}

        @Override // android.hardware.IRemoteDevice
        public void close() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRemoteDevice {
        static final int TRANSACTION_clearRequest = 7;
        static final int TRANSACTION_close = 9;
        static final int TRANSACTION_createDefaultRequest = 5;
        static final int TRANSACTION_createStream = 3;
        static final int TRANSACTION_deleteStream = 4;
        static final int TRANSACTION_getCameraCharacteristic = 2;
        static final int TRANSACTION_open = 1;
        static final int TRANSACTION_setCallback = 8;
        static final int TRANSACTION_submitRequest = 6;

        public Stub() {
            attachInterface(this, IRemoteDevice.DESCRIPTOR);
        }

        public static IRemoteDevice asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRemoteDevice.DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteDevice)) {
                return (IRemoteDevice) iin;
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
                    return RcsContactPresenceTuple.TUPLE_BASIC_STATUS_OPEN;
                case 2:
                    return "getCameraCharacteristic";
                case 3:
                    return "createStream";
                case 4:
                    return "deleteStream";
                case 5:
                    return "createDefaultRequest";
                case 6:
                    return "submitRequest";
                case 7:
                    return "clearRequest";
                case 8:
                    return "setCallback";
                case 9:
                    return "close";
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
                data.enforceInterface(IRemoteDevice.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRemoteDevice.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result = open(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 2:
                    CameraMetadataNative _result2 = getCameraCharacteristic();
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    OutputConfiguration _arg02 =
                            (OutputConfiguration) data.readTypedObject(OutputConfiguration.CREATOR);
                    data.enforceNoDataAvail();
                    int _result3 = createStream(_arg02);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 4:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    deleteStream(_arg03);
                    reply.writeNoException();
                    return true;
                case 5:
                    CameraMetadataNative _result4 = createDefaultRequest();
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                case 6:
                    CameraMetadataNative _arg04 =
                            (CameraMetadataNative)
                                    data.readTypedObject(CameraMetadataNative.CREATOR);
                    int[] _arg12 = data.createIntArray();
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    submitRequest(_arg04, _arg12, _arg2);
                    reply.writeNoException();
                    return true;
                case 7:
                    clearRequest();
                    reply.writeNoException();
                    return true;
                case 8:
                    IRemoteDeviceCallback _arg05 =
                            IRemoteDeviceCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setCallback(_arg05);
                    reply.writeNoException();
                    return true;
                case 9:
                    close();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteDevice {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteDevice.DESCRIPTOR;
            }

            @Override // android.hardware.IRemoteDevice
            public String open(String targetId, int targetLensFacing) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    _data.writeString(targetId);
                    _data.writeInt(targetLensFacing);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public CameraMetadataNative getCameraCharacteristic() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    CameraMetadataNative _result =
                            (CameraMetadataNative)
                                    _reply.readTypedObject(CameraMetadataNative.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public int createStream(OutputConfiguration outputConfiguration)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    _data.writeTypedObject(outputConfiguration, 0);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void deleteStream(int streamId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    _data.writeInt(streamId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public CameraMetadataNative createDefaultRequest() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    CameraMetadataNative _result =
                            (CameraMetadataNative)
                                    _reply.readTypedObject(CameraMetadataNative.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void submitRequest(
                    CameraMetadataNative request, int[] outputStreams, boolean streaming)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeIntArray(outputStreams);
                    _data.writeBoolean(streaming);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void clearRequest() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void setCallback(IRemoteDeviceCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void close() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
