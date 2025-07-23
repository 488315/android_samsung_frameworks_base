package android.hardware;

import android.hardware.IRemoteDeviceCallback;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.params.OutputConfiguration;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.RcsContactPresenceTuple;

/* loaded from: classes2.dex */
public interface IRemoteDevice extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.IRemoteDevice";

    public static class Default implements IRemoteDevice {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public void clearRequest() throws RemoteException {
        }

        @Override // android.hardware.IRemoteDevice
        public void close() throws RemoteException {
        }

        @Override // android.hardware.IRemoteDevice
        public CameraMetadataNative createDefaultRequest() throws RemoteException {
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public int createStream(OutputConfiguration outputConfiguration) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.IRemoteDevice
        public void deleteStream(int i) throws RemoteException {
        }

        @Override // android.hardware.IRemoteDevice
        public CameraMetadataNative getCameraCharacteristic() throws RemoteException {
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public String open(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.IRemoteDevice
        public void setCallback(IRemoteDeviceCallback iRemoteDeviceCallback) throws RemoteException {
        }

        @Override // android.hardware.IRemoteDevice
        public void submitRequest(CameraMetadataNative cameraMetadataNative, int[] iArr, boolean z) throws RemoteException {
        }
    }

    void clearRequest() throws RemoteException;

    void close() throws RemoteException;

    CameraMetadataNative createDefaultRequest() throws RemoteException;

    int createStream(OutputConfiguration outputConfiguration) throws RemoteException;

    void deleteStream(int i) throws RemoteException;

    CameraMetadataNative getCameraCharacteristic() throws RemoteException;

    String open(String str, int i) throws RemoteException;

    void setCallback(IRemoteDeviceCallback iRemoteDeviceCallback) throws RemoteException;

    void submitRequest(CameraMetadataNative cameraMetadataNative, int[] iArr, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteDevice {
        static final int TRANSACTION_clearRequest = 7;
        static final int TRANSACTION_close = 9;
        static final int TRANSACTION_createDefaultRequest = 5;
        static final int TRANSACTION_createStream = 3;
        static final int TRANSACTION_deleteStream = 4;
        static final int TRANSACTION_getCameraCharacteristic = 2;
        static final int TRANSACTION_open = 1;
        static final int TRANSACTION_setCallback = 8;
        static final int TRANSACTION_submitRequest = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, IRemoteDevice.DESCRIPTOR);
        }

        public static IRemoteDevice asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRemoteDevice.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteDevice)) {
                return (IRemoteDevice) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteDevice.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteDevice.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String open = open(readString, readInt);
                    parcel2.writeNoException();
                    parcel2.writeString(open);
                    return true;
                case 2:
                    CameraMetadataNative cameraCharacteristic = getCameraCharacteristic();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraCharacteristic, 1);
                    return true;
                case 3:
                    OutputConfiguration outputConfiguration = (OutputConfiguration) parcel.readTypedObject(OutputConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    int createStream = createStream(outputConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeInt(createStream);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteStream(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    CameraMetadataNative createDefaultRequest = createDefaultRequest();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createDefaultRequest, 1);
                    return true;
                case 6:
                    CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    int[] createIntArray = parcel.createIntArray();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    submitRequest(cameraMetadataNative, createIntArray, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    clearRequest();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IRemoteDeviceCallback asInterface = IRemoteDeviceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    close();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRemoteDevice {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteDevice.DESCRIPTOR;
            }

            @Override // android.hardware.IRemoteDevice
            public String open(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public CameraMetadataNative getCameraCharacteristic() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraMetadataNative) obtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public int createStream(OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    obtain.writeTypedObject(outputConfiguration, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void deleteStream(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public CameraMetadataNative createDefaultRequest() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraMetadataNative) obtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void submitRequest(CameraMetadataNative cameraMetadataNative, int[] iArr, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    obtain.writeIntArray(iArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void clearRequest() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void setCallback(IRemoteDeviceCallback iRemoteDeviceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteDeviceCallback);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
