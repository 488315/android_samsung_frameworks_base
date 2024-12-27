package android.hardware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICameraServiceListener extends IInterface {
    public static final int STATUS_ENUMERATING = 2;
    public static final int STATUS_NOT_AVAILABLE = -2;
    public static final int STATUS_NOT_PRESENT = 0;
    public static final int STATUS_PRESENT = 1;
    public static final int STATUS_UNKNOWN = -1;
    public static final int TORCH_STATUS_AVAILABLE_OFF = 1;
    public static final int TORCH_STATUS_AVAILABLE_ON = 2;
    public static final int TORCH_STATUS_NOT_AVAILABLE = 0;
    public static final int TORCH_STATUS_UNKNOWN = -1;

    void onCameraAccessPrioritiesChanged() throws RemoteException;

    void onCameraClosed(String str, int i) throws RemoteException;

    void onCameraDeviceStateChanged(String str, int i, int i2, String str2, int i3, int i4, int i5)
            throws RemoteException;

    void onCameraOpened(String str, String str2, int i) throws RemoteException;

    void onPhysicalCameraStatusChanged(int i, String str, String str2, int i2)
            throws RemoteException;

    void onStatusChanged(int i, String str, int i2) throws RemoteException;

    void onTorchStatusChanged(int i, String str, int i2) throws RemoteException;

    void onTorchStrengthLevelChanged(String str, int i, int i2) throws RemoteException;

    public static class Default implements ICameraServiceListener {
        @Override // android.hardware.ICameraServiceListener
        public void onStatusChanged(int status, String cameraId, int deviceId)
                throws RemoteException {}

        @Override // android.hardware.ICameraServiceListener
        public void onPhysicalCameraStatusChanged(
                int status, String cameraId, String physicalCameraId, int deviceId)
                throws RemoteException {}

        @Override // android.hardware.ICameraServiceListener
        public void onTorchStatusChanged(int status, String cameraId, int deviceId)
                throws RemoteException {}

        @Override // android.hardware.ICameraServiceListener
        public void onTorchStrengthLevelChanged(String cameraId, int newTorchStrength, int deviceId)
                throws RemoteException {}

        @Override // android.hardware.ICameraServiceListener
        public void onCameraAccessPrioritiesChanged() throws RemoteException {}

        @Override // android.hardware.ICameraServiceListener
        public void onCameraOpened(String cameraId, String clientPackageId, int deviceId)
                throws RemoteException {}

        @Override // android.hardware.ICameraServiceListener
        public void onCameraClosed(String cameraId, int deviceId) throws RemoteException {}

        @Override // android.hardware.ICameraServiceListener
        public void onCameraDeviceStateChanged(
                String cameraId,
                int facing,
                int newCameraState,
                String clientName,
                int apiLevel,
                int userId,
                int deviceId)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ICameraServiceListener {
        public static final String DESCRIPTOR = "android.hardware.ICameraServiceListener";
        static final int TRANSACTION_onCameraAccessPrioritiesChanged = 5;
        static final int TRANSACTION_onCameraClosed = 7;
        static final int TRANSACTION_onCameraDeviceStateChanged = 8;
        static final int TRANSACTION_onCameraOpened = 6;
        static final int TRANSACTION_onPhysicalCameraStatusChanged = 2;
        static final int TRANSACTION_onStatusChanged = 1;
        static final int TRANSACTION_onTorchStatusChanged = 3;
        static final int TRANSACTION_onTorchStrengthLevelChanged = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICameraServiceListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ICameraServiceListener)) {
                return (ICameraServiceListener) iin;
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
                    return "onStatusChanged";
                case 2:
                    return "onPhysicalCameraStatusChanged";
                case 3:
                    return "onTorchStatusChanged";
                case 4:
                    return "onTorchStrengthLevelChanged";
                case 5:
                    return "onCameraAccessPrioritiesChanged";
                case 6:
                    return "onCameraOpened";
                case 7:
                    return "onCameraClosed";
                case 8:
                    return "onCameraDeviceStateChanged";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    String _arg1 = data.readString();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    onStatusChanged(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    String _arg12 = data.readString();
                    String _arg22 = data.readString();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    onPhysicalCameraStatusChanged(_arg02, _arg12, _arg22, _arg3);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    String _arg13 = data.readString();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    onTorchStatusChanged(_arg03, _arg13, _arg23);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    int _arg14 = data.readInt();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    onTorchStrengthLevelChanged(_arg04, _arg14, _arg24);
                    return true;
                case 5:
                    onCameraAccessPrioritiesChanged();
                    return true;
                case 6:
                    String _arg05 = data.readString();
                    String _arg15 = data.readString();
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    onCameraOpened(_arg05, _arg15, _arg25);
                    return true;
                case 7:
                    String _arg06 = data.readString();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    onCameraClosed(_arg06, _arg16);
                    return true;
                case 8:
                    String _arg07 = data.readString();
                    int _arg17 = data.readInt();
                    int _arg26 = data.readInt();
                    String _arg32 = data.readString();
                    int _arg4 = data.readInt();
                    int _arg5 = data.readInt();
                    int _arg6 = data.readInt();
                    data.enforceNoDataAvail();
                    onCameraDeviceStateChanged(_arg07, _arg17, _arg26, _arg32, _arg4, _arg5, _arg6);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICameraServiceListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.ICameraServiceListener
            public void onStatusChanged(int status, String cameraId, int deviceId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    _data.writeString(cameraId);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onPhysicalCameraStatusChanged(
                    int status, String cameraId, String physicalCameraId, int deviceId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    _data.writeString(cameraId);
                    _data.writeString(physicalCameraId);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onTorchStatusChanged(int status, String cameraId, int deviceId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    _data.writeString(cameraId);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onTorchStrengthLevelChanged(
                    String cameraId, int newTorchStrength, int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeInt(newTorchStrength);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraAccessPrioritiesChanged() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraOpened(String cameraId, String clientPackageId, int deviceId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeString(clientPackageId);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraClosed(String cameraId, int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraServiceListener
            public void onCameraDeviceStateChanged(
                    String cameraId,
                    int facing,
                    int newCameraState,
                    String clientName,
                    int apiLevel,
                    int userId,
                    int deviceId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeInt(facing);
                    _data.writeInt(newCameraState);
                    _data.writeString(clientName);
                    _data.writeInt(apiLevel);
                    _data.writeInt(userId);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
