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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICameraDeviceUser)) {
                return (ICameraDeviceUser) iInterfaceQueryLocalInterface;
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
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    SubmitInfo submitInfoSubmitRequest = submitRequest(captureRequest, z);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(submitInfoSubmitRequest, 1);
                    return true;
                case 3:
                    CaptureRequest[] captureRequestArr = (CaptureRequest[]) parcel.createTypedArray(CaptureRequest.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    SubmitInfo submitInfoSubmitRequestList = submitRequestList(captureRequestArr, z2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(submitInfoSubmitRequestList, 1);
                    return true;
                case 4:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    SubmitInfo submitInfoStartStreaming = startStreaming(iArrCreateIntArray, iArrCreateIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(submitInfoStartStreaming, 1);
                    return true;
                case 5:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long jCancelRequest = cancelRequest(i3);
                    parcel2.writeNoException();
                    parcel2.writeLong(jCancelRequest);
                    return true;
                case 6:
                    beginConfigure();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i4 = parcel.readInt();
                    CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int[] iArrEndConfigure = endConfigure(i4, cameraMetadataNative, j);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(iArrEndConfigure);
                    return true;
                case 8:
                    SessionConfiguration sessionConfiguration = (SessionConfiguration) parcel.readTypedObject(SessionConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSessionConfigurationSupported = isSessionConfigurationSupported(sessionConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSessionConfigurationSupported);
                    return true;
                case 9:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteStream(i5);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    OutputConfiguration outputConfiguration = (OutputConfiguration) parcel.readTypedObject(OutputConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iCreateStream = createStream(outputConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateStream);
                    return true;
                case 11:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iCreateInputStream = createInputStream(i6, i7, i8, z3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateInputStream);
                    return true;
                case 12:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setParameters(string);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    Surface inputSurface = getInputSurface();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputSurface, 1);
                    return true;
                case 14:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative cameraMetadataNativeCreateDefaultRequest = createDefaultRequest(i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraMetadataNativeCreateDefaultRequest, 1);
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
                    long jFlush = flush();
                    parcel2.writeNoException();
                    parcel2.writeLong(jFlush);
                    return true;
                case 18:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepare(i10);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    tearDown(i11);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepare2(i12, i13);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i14 = parcel.readInt();
                    OutputConfiguration outputConfiguration2 = (OutputConfiguration) parcel.readTypedObject(OutputConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateOutputConfiguration(i14, outputConfiguration2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i15 = parcel.readInt();
                    OutputConfiguration outputConfiguration3 = (OutputConfiguration) parcel.readTypedObject(OutputConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    finalizeOutputConfigurations(i15, outputConfiguration3);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    MQDescriptor<Byte, Byte> captureResultMetadataQueue = getCaptureResultMetadataQueue();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(captureResultMetadataQueue, 1);
                    return true;
                case 24:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCameraAudioRestriction(i16);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int globalAudioRestriction = getGlobalAudioRestriction();
                    parcel2.writeNoException();
                    parcel2.writeInt(globalAudioRestriction);
                    return true;
                case 26:
                    ICameraDeviceCallbacks iCameraDeviceCallbacksAsInterface = ICameraDeviceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    ICameraOfflineSession iCameraOfflineSessionSwitchToOffline = switchToOffline(iCameraDeviceCallbacksAsInterface, iArrCreateIntArray3);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCameraOfflineSessionSwitchToOffline);
                    return true;
                case 27:
                    boolean zIsPrimaryClient = isPrimaryClient();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPrimaryClient);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public SubmitInfo submitRequest(CaptureRequest captureRequest, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(captureRequest, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SubmitInfo) parcelObtain2.readTypedObject(SubmitInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public SubmitInfo submitRequestList(CaptureRequest[] captureRequestArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(captureRequestArr, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SubmitInfo) parcelObtain2.readTypedObject(SubmitInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public SubmitInfo startStreaming(int[] iArr, int[] iArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeIntArray(iArr2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SubmitInfo) parcelObtain2.readTypedObject(SubmitInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public long cancelRequest(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void beginConfigure() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public int[] endConfigure(int i, CameraMetadataNative cameraMetadataNative, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(cameraMetadataNative, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(sessionConfiguration, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void deleteStream(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public int createStream(OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(outputConfiguration, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public int createInputStream(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void setParameters(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public Surface getInputSurface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Surface) parcelObtain2.readTypedObject(Surface.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public CameraMetadataNative createDefaultRequest(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CameraMetadataNative) parcelObtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public CameraMetadataNative getCameraInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CameraMetadataNative) parcelObtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void waitUntilIdle() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public long flush() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void prepare(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void tearDown(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void prepare2(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void updateOutputConfiguration(int i, OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(outputConfiguration, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void finalizeOutputConfigurations(int i, OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(outputConfiguration, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public MQDescriptor<Byte, Byte> getCaptureResultMetadataQueue() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MQDescriptor) parcelObtain2.readTypedObject(MQDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public void setCameraAudioRestriction(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public int getGlobalAudioRestriction() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public ICameraOfflineSession switchToOffline(ICameraDeviceCallbacks iCameraDeviceCallbacks, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCameraDeviceCallbacks);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICameraOfflineSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.camera2.ICameraDeviceUser
            public boolean isPrimaryClient() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
