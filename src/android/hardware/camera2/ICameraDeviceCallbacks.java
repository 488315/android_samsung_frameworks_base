package android.hardware.camera2;

import android.hardware.camera2.impl.CaptureResultExtras;
import android.hardware.camera2.impl.PhysicalCaptureResultInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ICameraDeviceCallbacks extends IInterface {
    public static final int ERROR_CAMERA_BUFFER = 5;
    public static final int ERROR_CAMERA_DEVICE = 1;
    public static final int ERROR_CAMERA_DISABLED = 6;
    public static final int ERROR_CAMERA_DISABLED_AND_FLUSH = 100;
    public static final int ERROR_CAMERA_DISCONNECTED = 0;
    public static final int ERROR_CAMERA_INVALID_ERROR = -1;
    public static final int ERROR_CAMERA_REQUEST = 3;
    public static final int ERROR_CAMERA_RESULT = 4;
    public static final int ERROR_CAMERA_SERVICE = 2;

    public static class Default implements ICameraDeviceCallbacks {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onCaptureStarted(CaptureResultExtras captureResultExtras, long j) throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onClientSharedAccessPriorityChanged(boolean z) throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onDeviceError(int i, CaptureResultExtras captureResultExtras) throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onDeviceIdle() throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onPrepared(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onRepeatingRequestError(long j, int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onRequestQueueEmpty() throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceCallbacks
        public void onResultReceived(CameraMetadataInfo cameraMetadataInfo, CaptureResultExtras captureResultExtras, PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) throws RemoteException {
        }
    }

    void onCaptureStarted(CaptureResultExtras captureResultExtras, long j) throws RemoteException;

    void onClientSharedAccessPriorityChanged(boolean z) throws RemoteException;

    void onDeviceError(int i, CaptureResultExtras captureResultExtras) throws RemoteException;

    void onDeviceIdle() throws RemoteException;

    void onPrepared(int i) throws RemoteException;

    void onRepeatingRequestError(long j, int i) throws RemoteException;

    void onRequestQueueEmpty() throws RemoteException;

    void onResultReceived(CameraMetadataInfo cameraMetadataInfo, CaptureResultExtras captureResultExtras, PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ICameraDeviceCallbacks {
        public static final String DESCRIPTOR = "android.hardware.camera2.ICameraDeviceCallbacks";
        static final int TRANSACTION_onCaptureStarted = 3;
        static final int TRANSACTION_onClientSharedAccessPriorityChanged = 8;
        static final int TRANSACTION_onDeviceError = 1;
        static final int TRANSACTION_onDeviceIdle = 2;
        static final int TRANSACTION_onPrepared = 5;
        static final int TRANSACTION_onRepeatingRequestError = 6;
        static final int TRANSACTION_onRequestQueueEmpty = 7;
        static final int TRANSACTION_onResultReceived = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICameraDeviceCallbacks asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICameraDeviceCallbacks)) {
                return (ICameraDeviceCallbacks) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDeviceError";
                case 2:
                    return "onDeviceIdle";
                case 3:
                    return "onCaptureStarted";
                case 4:
                    return "onResultReceived";
                case 5:
                    return "onPrepared";
                case 6:
                    return "onRepeatingRequestError";
                case 7:
                    return "onRequestQueueEmpty";
                case 8:
                    return "onClientSharedAccessPriorityChanged";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    CaptureResultExtras captureResultExtras = (CaptureResultExtras) parcel.readTypedObject(CaptureResultExtras.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDeviceError(readInt, captureResultExtras);
                    return true;
                case 2:
                    onDeviceIdle();
                    return true;
                case 3:
                    CaptureResultExtras captureResultExtras2 = (CaptureResultExtras) parcel.readTypedObject(CaptureResultExtras.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onCaptureStarted(captureResultExtras2, readLong);
                    return true;
                case 4:
                    CameraMetadataInfo cameraMetadataInfo = (CameraMetadataInfo) parcel.readTypedObject(CameraMetadataInfo.CREATOR);
                    CaptureResultExtras captureResultExtras3 = (CaptureResultExtras) parcel.readTypedObject(CaptureResultExtras.CREATOR);
                    PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr = (PhysicalCaptureResultInfo[]) parcel.createTypedArray(PhysicalCaptureResultInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResultReceived(cameraMetadataInfo, captureResultExtras3, physicalCaptureResultInfoArr);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPrepared(readInt2);
                    return true;
                case 6:
                    long readLong2 = parcel.readLong();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRepeatingRequestError(readLong2, readInt3);
                    return true;
                case 7:
                    onRequestQueueEmpty();
                    return true;
                case 8:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onClientSharedAccessPriorityChanged(readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICameraDeviceCallbacks {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onDeviceError(int i, CaptureResultExtras captureResultExtras) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(captureResultExtras, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onDeviceIdle() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onCaptureStarted(CaptureResultExtras captureResultExtras, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(captureResultExtras, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onResultReceived(CameraMetadataInfo cameraMetadataInfo, CaptureResultExtras captureResultExtras, PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(cameraMetadataInfo, 0);
                    obtain.writeTypedObject(captureResultExtras, 0);
                    obtain.writeTypedArray(physicalCaptureResultInfoArr, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onPrepared(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onRepeatingRequestError(long j, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onRequestQueueEmpty() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceCallbacks
            public void onClientSharedAccessPriorityChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
