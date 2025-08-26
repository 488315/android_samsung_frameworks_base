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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteDevice.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteDevice)) {
                return (IRemoteDevice) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strOpen = open(string, i3);
                    parcel2.writeNoException();
                    parcel2.writeString(strOpen);
                    return true;
                case 2:
                    CameraMetadataNative cameraCharacteristic = getCameraCharacteristic();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraCharacteristic, 1);
                    return true;
                case 3:
                    OutputConfiguration outputConfiguration = (OutputConfiguration) parcel.readTypedObject(OutputConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iCreateStream = createStream(outputConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateStream);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteStream(i4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    CameraMetadataNative cameraMetadataNativeCreateDefaultRequest = createDefaultRequest();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraMetadataNativeCreateDefaultRequest, 1);
                    return true;
                case 6:
                    CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    submitRequest(cameraMetadataNative, iArrCreateIntArray, z);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    clearRequest();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IRemoteDeviceCallback iRemoteDeviceCallbackAsInterface = IRemoteDeviceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(iRemoteDeviceCallbackAsInterface);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public CameraMetadataNative getCameraCharacteristic() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CameraMetadataNative) parcelObtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public int createStream(OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(outputConfiguration, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void deleteStream(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public CameraMetadataNative createDefaultRequest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CameraMetadataNative) parcelObtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void submitRequest(CameraMetadataNative cameraMetadataNative, int[] iArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cameraMetadataNative, 0);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void clearRequest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void setCallback(IRemoteDeviceCallback iRemoteDeviceCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteDeviceCallback);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.IRemoteDevice
            public void close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteDevice.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
