package android.hardware.camera2;

import android.hardware.camera2.ICameraDeviceCallbacks;
import android.hardware.camera2.ICameraOfflineSession;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.utils.SubmitInfo;
import android.hardware.common.fmq.MQDescriptor;
import android.media.MediaMetrics;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;

/* loaded from: classes2.dex */
public interface ICameraDeviceUser extends IInterface {
    public static final int AUDIO_RESTRICTION_NONE = 0;
    public static final int AUDIO_RESTRICTION_VIBRATION = 1;
    public static final int AUDIO_RESTRICTION_VIBRATION_SOUND = 3;
    public static final int CONSTRAINED_HIGH_SPEED_MODE = 1;
    public static final int NORMAL_MODE = 0;
    public static final int NO_IN_FLIGHT_REPEATING_FRAMES = -1;
    public static final int SHARED_MODE = 2;
    public static final int TEMPLATE_MANUAL = 6;
    public static final int TEMPLATE_PREVIEW = 1;
    public static final int TEMPLATE_RECORD = 3;
    public static final int TEMPLATE_STILL_CAPTURE = 2;
    public static final int TEMPLATE_VIDEO_SNAPSHOT = 4;
    public static final int TEMPLATE_ZERO_SHUTTER_LAG = 5;
    public static final int VENDOR_MODE_START = 32768;

    public static class Default implements ICameraDeviceUser {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void beginConfigure() throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public long cancelRequest(int i) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public CameraMetadataNative createDefaultRequest(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public int createInputStream(int i, int i2, int i3, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public int createStream(OutputConfiguration outputConfiguration) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void deleteStream(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void disconnect() throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public int[] endConfigure(int i, CameraMetadataNative cameraMetadataNative, long j) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void finalizeOutputConfigurations(int i, OutputConfiguration outputConfiguration) throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public long flush() throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public CameraMetadataNative getCameraInfo() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public MQDescriptor<Byte, Byte> getCaptureResultMetadataQueue() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public int getGlobalAudioRestriction() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public Surface getInputSurface() throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public boolean isPrimaryClient() throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws RemoteException {
            return false;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void prepare(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void prepare2(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void setCameraAudioRestriction(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void setParameters(String str) throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public SubmitInfo startStreaming(int[] iArr, int[] iArr2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public SubmitInfo submitRequest(CaptureRequest captureRequest, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public SubmitInfo submitRequestList(CaptureRequest[] captureRequestArr, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public ICameraOfflineSession switchToOffline(ICameraDeviceCallbacks iCameraDeviceCallbacks, int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void tearDown(int i) throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void updateOutputConfiguration(int i, OutputConfiguration outputConfiguration) throws RemoteException {
        }

        @Override // android.hardware.camera2.ICameraDeviceUser
        public void waitUntilIdle() throws RemoteException {
        }
    }

    void beginConfigure() throws RemoteException;

    long cancelRequest(int i) throws RemoteException;

    CameraMetadataNative createDefaultRequest(int i) throws RemoteException;

    int createInputStream(int i, int i2, int i3, boolean z) throws RemoteException;

    int createStream(OutputConfiguration outputConfiguration) throws RemoteException;

    void deleteStream(int i) throws RemoteException;

    void disconnect() throws RemoteException;

    int[] endConfigure(int i, CameraMetadataNative cameraMetadataNative, long j) throws RemoteException;

    void finalizeOutputConfigurations(int i, OutputConfiguration outputConfiguration) throws RemoteException;

    long flush() throws RemoteException;

    CameraMetadataNative getCameraInfo() throws RemoteException;

    MQDescriptor<Byte, Byte> getCaptureResultMetadataQueue() throws RemoteException;

    int getGlobalAudioRestriction() throws RemoteException;

    Surface getInputSurface() throws RemoteException;

    boolean isPrimaryClient() throws RemoteException;

    boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws RemoteException;

    void prepare(int i) throws RemoteException;

    void prepare2(int i, int i2) throws RemoteException;

    void setCameraAudioRestriction(int i) throws RemoteException;

    void setParameters(String str) throws RemoteException;

    SubmitInfo startStreaming(int[] iArr, int[] iArr2) throws RemoteException;

    SubmitInfo submitRequest(CaptureRequest captureRequest, boolean z) throws RemoteException;

    SubmitInfo submitRequestList(CaptureRequest[] captureRequestArr, boolean z) throws RemoteException;

    ICameraOfflineSession switchToOffline(ICameraDeviceCallbacks iCameraDeviceCallbacks, int[] iArr) throws RemoteException;

    void tearDown(int i) throws RemoteException;

    void updateOutputConfiguration(int i, OutputConfiguration outputConfiguration) throws RemoteException;

    void waitUntilIdle() throws RemoteException;

    public static abstract class Stub extends Binder implements ICameraDeviceUser {
        public static final String DESCRIPTOR = "android.hardware.camera2.ICameraDeviceUser";
        static final int TRANSACTION_beginConfigure = 6;
        static final int TRANSACTION_cancelRequest = 5;
        static final int TRANSACTION_createDefaultRequest = 14;
        static final int TRANSACTION_createInputStream = 11;
        static final int TRANSACTION_createStream = 10;
        static final int TRANSACTION_deleteStream = 9;
        static final int TRANSACTION_disconnect = 1;
        static final int TRANSACTION_endConfigure = 7;
        static final int TRANSACTION_finalizeOutputConfigurations = 22;
        static final int TRANSACTION_flush = 17;
        static final int TRANSACTION_getCameraInfo = 15;
        static final int TRANSACTION_getCaptureResultMetadataQueue = 23;
        static final int TRANSACTION_getGlobalAudioRestriction = 25;
        static final int TRANSACTION_getInputSurface = 13;
        static final int TRANSACTION_isPrimaryClient = 27;
        static final int TRANSACTION_isSessionConfigurationSupported = 8;
        static final int TRANSACTION_prepare = 18;
        static final int TRANSACTION_prepare2 = 20;
        static final int TRANSACTION_setCameraAudioRestriction = 24;
        static final int TRANSACTION_setParameters = 12;
        static final int TRANSACTION_startStreaming = 4;
        static final int TRANSACTION_submitRequest = 2;
        static final int TRANSACTION_submitRequestList = 3;
        static final int TRANSACTION_switchToOffline = 26;
        static final int TRANSACTION_tearDown = 19;
        static final int TRANSACTION_updateOutputConfiguration = 21;
        static final int TRANSACTION_waitUntilIdle = 16;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 26;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICameraDeviceUser asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICameraDeviceUser)) {
                return (ICameraDeviceUser) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return MediaMetrics.Value.DISCONNECT;
                case 2:
                    return "submitRequest";
                case 3:
                    return "submitRequestList";
                case 4:
                    return "startStreaming";
                case 5:
                    return "cancelRequest";
                case 6:
                    return "beginConfigure";
                case 7:
                    return "endConfigure";
                case 8:
                    return "isSessionConfigurationSupported";
                case 9:
                    return "deleteStream";
                case 10:
                    return "createStream";
                case 11:
                    return "createInputStream";
                case 12:
                    return "setParameters";
                case 13:
                    return "getInputSurface";
                case 14:
                    return "createDefaultRequest";
                case 15:
                    return "getCameraInfo";
                case 16:
                    return "waitUntilIdle";
                case 17:
                    return "flush";
                case 18:
                    return "prepare";
                case 19:
                    return "tearDown";
                case 20:
                    return "prepare2";
                case 21:
                    return "updateOutputConfiguration";
                case 22:
                    return "finalizeOutputConfigurations";
                case 23:
                    return "getCaptureResultMetadataQueue";
                case 24:
                    return "setCameraAudioRestriction";
                case 25:
                    return "getGlobalAudioRestriction";
                case 26:
                    return "switchToOffline";
                case 27:
                    return "isPrimaryClient";
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
                    disconnect();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    CaptureRequest captureRequest = (CaptureRequest) parcel.readTypedObject(CaptureRequest.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    SubmitInfo submitRequest = submitRequest(captureRequest, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(submitRequest, 1);
                    return true;
                case 3:
                    CaptureRequest[] captureRequestArr = (CaptureRequest[]) parcel.createTypedArray(CaptureRequest.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    SubmitInfo submitRequestList = submitRequestList(captureRequestArr, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(submitRequestList, 1);
                    return true;
                case 4:
                    int[] createIntArray = parcel.createIntArray();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    SubmitInfo startStreaming = startStreaming(createIntArray, createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startStreaming, 1);
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long cancelRequest = cancelRequest(readInt);
                    parcel2.writeNoException();
                    parcel2.writeLong(cancelRequest);
                    return true;
                case 6:
                    beginConfigure();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt2 = parcel.readInt();
                    CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int[] endConfigure = endConfigure(readInt2, cameraMetadataNative, readLong);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(endConfigure);
                    return true;
                case 8:
                    SessionConfiguration sessionConfiguration = (SessionConfiguration) parcel.readTypedObject(SessionConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSessionConfigurationSupported = isSessionConfigurationSupported(sessionConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSessionConfigurationSupported);
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteStream(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    OutputConfiguration outputConfiguration = (OutputConfiguration) parcel.readTypedObject(OutputConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    int createStream = createStream(outputConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeInt(createStream);
                    return true;
                case 11:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int createInputStream = createInputStream(readInt4, readInt5, readInt6, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeInt(createInputStream);
                    return true;
                case 12:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setParameters(readString);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    Surface inputSurface = getInputSurface();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputSurface, 1);
                    return true;
                case 14:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative createDefaultRequest = createDefaultRequest(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createDefaultRequest, 1);
                    return true;
                case 15:
                    CameraMetadataNative cameraInfo = getCameraInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraInfo, 1);
                    return true;
                case 16:
                    waitUntilIdle();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    long flush = flush();
                    parcel2.writeNoException();
                    parcel2.writeLong(flush);
                    return true;
                case 18:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepare(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    tearDown(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepare2(readInt10, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt12 = parcel.readInt();
                    OutputConfiguration outputConfiguration2 = (OutputConfiguration) parcel.readTypedObject(OutputConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateOutputConfiguration(readInt12, outputConfiguration2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt13 = parcel.readInt();
                    OutputConfiguration outputConfiguration3 = (OutputConfiguration) parcel.readTypedObject(OutputConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    finalizeOutputConfigurations(readInt13, outputConfiguration3);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    MQDescriptor<Byte, Byte> captureResultMetadataQueue = getCaptureResultMetadataQueue();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(captureResultMetadataQueue, 1);
                    return true;
                case 24:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCameraAudioRestriction(readInt14);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int globalAudioRestriction = getGlobalAudioRestriction();
                    parcel2.writeNoException();
                    parcel2.writeInt(globalAudioRestriction);
                    return true;
                case 26:
                    ICameraDeviceCallbacks asInterface = ICameraDeviceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    ICameraOfflineSession switchToOffline = switchToOffline(asInterface, createIntArray3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(switchToOffline);
                    return true;
                case 27:
                    boolean isPrimaryClient = isPrimaryClient();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPrimaryClient);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICameraDeviceUser {
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

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void disconnect() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public SubmitInfo submitRequest(CaptureRequest captureRequest, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(captureRequest, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SubmitInfo) obtain2.readTypedObject(SubmitInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public SubmitInfo submitRequestList(CaptureRequest[] captureRequestArr, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(captureRequestArr, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SubmitInfo) obtain2.readTypedObject(SubmitInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public SubmitInfo startStreaming(int[] iArr, int[] iArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SubmitInfo) obtain2.readTypedObject(SubmitInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public long cancelRequest(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void beginConfigure() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public int[] endConfigure(int i, CameraMetadataNative cameraMetadataNative, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(sessionConfiguration, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void deleteStream(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public int createStream(OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(outputConfiguration, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public int createInputStream(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void setParameters(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public Surface getInputSurface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Surface) obtain2.readTypedObject(Surface.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public CameraMetadataNative createDefaultRequest(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraMetadataNative) obtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public CameraMetadataNative getCameraInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraMetadataNative) obtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void waitUntilIdle() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public long flush() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void prepare(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void tearDown(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void prepare2(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void updateOutputConfiguration(int i, OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(outputConfiguration, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void finalizeOutputConfigurations(int i, OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(outputConfiguration, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public MQDescriptor<Byte, Byte> getCaptureResultMetadataQueue() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MQDescriptor) obtain2.readTypedObject(MQDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void setCameraAudioRestriction(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public int getGlobalAudioRestriction() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public ICameraOfflineSession switchToOffline(ICameraDeviceCallbacks iCameraDeviceCallbacks, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCameraDeviceCallbacks);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICameraOfflineSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public boolean isPrimaryClient() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
