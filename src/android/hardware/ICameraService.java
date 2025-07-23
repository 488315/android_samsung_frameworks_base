package android.hardware;

import android.content.AttributionSourceState;
import android.hardware.ICamera;
import android.hardware.ICameraClient;
import android.hardware.ICameraServiceListener;
import android.hardware.IDeviceInjectorCallback;
import android.hardware.IRemoteDevice;
import android.hardware.camera2.ICameraDeviceCallbacks;
import android.hardware.camera2.ICameraDeviceUser;
import android.hardware.camera2.ICameraInjectionCallback;
import android.hardware.camera2.ICameraInjectionSession;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.params.VendorTagDescriptor;
import android.hardware.camera2.params.VendorTagDescriptorCache;
import android.hardware.camera2.utils.CameraIdAndSessionConfiguration;
import android.hardware.camera2.utils.ConcurrentCameraIdCombination;
import android.media.MediaMetrics;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.RemoteException;
import com.samsung.android.camera.IRequestInjectorCallback;

/* loaded from: classes2.dex */
public interface ICameraService extends IInterface {
    public static final String BUNDLE_KEY_I32 = "key.i32";
    public static final String BUNDLE_KEY_TAG_NAME = "key.tagName";
    public static final String BUNDLE_KEY_U8 = "key.u8";
    public static final int CAMERA_TYPE_ALL = 1;
    public static final int CAMERA_TYPE_BACKWARD_COMPATIBLE = 0;
    public static final int DEVICE_STATE_BACK_COVERED = 1;
    public static final int DEVICE_STATE_FOLDED = 4;
    public static final int DEVICE_STATE_FRONT_COVERED = 2;
    public static final int DEVICE_STATE_LAST_FRAMEWORK_BIT = Integer.MIN_VALUE;
    public static final int DEVICE_STATE_NORMAL = 0;
    public static final int ERROR_ALREADY_EXISTS = 2;
    public static final int ERROR_CAMERA_IN_USE = 7;
    public static final int ERROR_DEPRECATED_HAL = 9;
    public static final int ERROR_DISABLED = 6;
    public static final int ERROR_DISABLED_AND_FLUSH = 100;
    public static final int ERROR_DISCONNECTED = 4;
    public static final int ERROR_ILLEGAL_ARGUMENT = 3;
    public static final int ERROR_INVALID_OPERATION = 10;
    public static final int ERROR_MAX_CAMERAS_IN_USE = 8;
    public static final int ERROR_PERMISSION_DENIED = 1;
    public static final int ERROR_TIMED_OUT = 5;
    public static final int EVENT_NONE = 0;
    public static final int EVENT_USB_DEVICE_ATTACHED = 2;
    public static final int EVENT_USB_DEVICE_DETACHED = 3;
    public static final int EVENT_USER_SWITCHED = 1;
    public static final int ROTATION_OVERRIDE_NONE = 0;
    public static final int ROTATION_OVERRIDE_OVERRIDE_TO_PORTRAIT = 1;
    public static final int ROTATION_OVERRIDE_ROTATION_ONLY = 2;
    public static final int USE_CALLING_PID = -1;
    public static final int USE_CALLING_UID = -1;

    public static class Default implements ICameraService {
        @Override // android.hardware.ICameraService
        public CameraStatus[] addListener(ICameraServiceListener iCameraServiceListener) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public boolean applyExtraRequestsToRequestInjector(PersistableBundle[] persistableBundleArr) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.ICameraService
        public ICamera connect(ICameraClient iCameraClient, int i, int i2, int i3, boolean z, AttributionSourceState attributionSourceState, int i4) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public ICameraDeviceUser connectDevice(ICameraDeviceCallbacks iCameraDeviceCallbacks, String str, int i, int i2, int i3, AttributionSourceState attributionSourceState, int i4, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public CameraMetadataNative createDefaultRequest(String str, int i, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public CameraMetadataNative getCameraCharacteristics(String str, int i, int i2, AttributionSourceState attributionSourceState, int i3) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public CameraInfo getCameraInfo(int i, int i2, AttributionSourceState attributionSourceState, int i3) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public VendorTagDescriptorCache getCameraVendorTagCache() throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public VendorTagDescriptor getCameraVendorTagDescriptor() throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public ConcurrentCameraIdCombination[] getConcurrentCameraIds() throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public String getLegacyParameters(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public int getNumberOfCameras(int i, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.ICameraService
        public CameraMetadataNative getSessionCharacteristics(String str, int i, int i2, SessionConfiguration sessionConfiguration, AttributionSourceState attributionSourceState, int i3) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public int getTorchStrengthLevel(String str, AttributionSourceState attributionSourceState, int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.ICameraService
        public ICameraInjectionSession injectCamera(String str, String str2, String str3, ICameraInjectionCallback iCameraInjectionCallback) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public void injectSessionParams(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public boolean isConcurrentSessionConfigurationSupported(CameraIdAndSessionConfiguration[] cameraIdAndSessionConfigurationArr, int i, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public boolean isHiddenIdPermittedPackage(String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public boolean isHiddenPhysicalCamera(String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public boolean isSessionConfigurationWithParametersSupported(String str, int i, SessionConfiguration sessionConfiguration, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public void notifyDeviceInjectorOrientationChange() throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void notifyDeviceStateChange(long j) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void notifyDeviceStateChangeSync(long j) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void notifyDisplayConfigurationChange() throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void notifyPkgListParamChange(int i, String[] strArr, String[] strArr2) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void notifySystemEvent(int i, int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void removeListener(ICameraServiceListener iCameraServiceListener) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void removeRequestInjectorCallback() throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public String reportExtensionSessionStats(CameraExtensionSessionStats cameraExtensionSessionStats) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public void setDeviceInjectorPending(boolean z) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public boolean setRequestInjectorCallback(IRequestInjectorCallback iRequestInjectorCallback) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public void setTorchMode(String str, boolean z, IBinder iBinder, AttributionSourceState attributionSourceState, int i) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void startDeviceInjector(String[] strArr, String[] strArr2, String str, IDeviceInjectorCallback iDeviceInjectorCallback) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void startRemoteDeviceInjector(String[] strArr, String[] strArr2, IRemoteDevice iRemoteDevice, IDeviceInjectorCallback iDeviceInjectorCallback) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void stopDeviceInjector() throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void turnOnTorchWithStrengthLevel(String str, int i, IBinder iBinder, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void updateRequestInjectorAllowedList(String[] strArr) throws RemoteException {
        }
    }

    CameraStatus[] addListener(ICameraServiceListener iCameraServiceListener) throws RemoteException;

    boolean applyExtraRequestsToRequestInjector(PersistableBundle[] persistableBundleArr) throws RemoteException;

    ICamera connect(ICameraClient iCameraClient, int i, int i2, int i3, boolean z, AttributionSourceState attributionSourceState, int i4) throws RemoteException;

    ICameraDeviceUser connectDevice(ICameraDeviceCallbacks iCameraDeviceCallbacks, String str, int i, int i2, int i3, AttributionSourceState attributionSourceState, int i4, boolean z) throws RemoteException;

    CameraMetadataNative createDefaultRequest(String str, int i, AttributionSourceState attributionSourceState, int i2) throws RemoteException;

    CameraMetadataNative getCameraCharacteristics(String str, int i, int i2, AttributionSourceState attributionSourceState, int i3) throws RemoteException;

    CameraInfo getCameraInfo(int i, int i2, AttributionSourceState attributionSourceState, int i3) throws RemoteException;

    VendorTagDescriptorCache getCameraVendorTagCache() throws RemoteException;

    VendorTagDescriptor getCameraVendorTagDescriptor() throws RemoteException;

    ConcurrentCameraIdCombination[] getConcurrentCameraIds() throws RemoteException;

    String getLegacyParameters(int i) throws RemoteException;

    int getNumberOfCameras(int i, AttributionSourceState attributionSourceState, int i2) throws RemoteException;

    CameraMetadataNative getSessionCharacteristics(String str, int i, int i2, SessionConfiguration sessionConfiguration, AttributionSourceState attributionSourceState, int i3) throws RemoteException;

    int getTorchStrengthLevel(String str, AttributionSourceState attributionSourceState, int i) throws RemoteException;

    ICameraInjectionSession injectCamera(String str, String str2, String str3, ICameraInjectionCallback iCameraInjectionCallback) throws RemoteException;

    void injectSessionParams(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException;

    boolean isConcurrentSessionConfigurationSupported(CameraIdAndSessionConfiguration[] cameraIdAndSessionConfigurationArr, int i, AttributionSourceState attributionSourceState, int i2) throws RemoteException;

    boolean isHiddenIdPermittedPackage(String str) throws RemoteException;

    boolean isHiddenPhysicalCamera(String str) throws RemoteException;

    boolean isSessionConfigurationWithParametersSupported(String str, int i, SessionConfiguration sessionConfiguration, AttributionSourceState attributionSourceState, int i2) throws RemoteException;

    void notifyDeviceInjectorOrientationChange() throws RemoteException;

    void notifyDeviceStateChange(long j) throws RemoteException;

    void notifyDeviceStateChangeSync(long j) throws RemoteException;

    void notifyDisplayConfigurationChange() throws RemoteException;

    void notifyPkgListParamChange(int i, String[] strArr, String[] strArr2) throws RemoteException;

    void notifySystemEvent(int i, int[] iArr) throws RemoteException;

    void removeListener(ICameraServiceListener iCameraServiceListener) throws RemoteException;

    void removeRequestInjectorCallback() throws RemoteException;

    String reportExtensionSessionStats(CameraExtensionSessionStats cameraExtensionSessionStats) throws RemoteException;

    void setDeviceInjectorPending(boolean z) throws RemoteException;

    boolean setRequestInjectorCallback(IRequestInjectorCallback iRequestInjectorCallback) throws RemoteException;

    void setTorchMode(String str, boolean z, IBinder iBinder, AttributionSourceState attributionSourceState, int i) throws RemoteException;

    void startDeviceInjector(String[] strArr, String[] strArr2, String str, IDeviceInjectorCallback iDeviceInjectorCallback) throws RemoteException;

    void startRemoteDeviceInjector(String[] strArr, String[] strArr2, IRemoteDevice iRemoteDevice, IDeviceInjectorCallback iDeviceInjectorCallback) throws RemoteException;

    void stopDeviceInjector() throws RemoteException;

    void turnOnTorchWithStrengthLevel(String str, int i, IBinder iBinder, AttributionSourceState attributionSourceState, int i2) throws RemoteException;

    void updateRequestInjectorAllowedList(String[] strArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ICameraService {
        public static final String DESCRIPTOR = "android.hardware.ICameraService";
        static final int TRANSACTION_addListener = 5;
        static final int TRANSACTION_applyExtraRequestsToRequestInjector = 29;
        static final int TRANSACTION_connect = 3;
        static final int TRANSACTION_connectDevice = 4;
        static final int TRANSACTION_createDefaultRequest = 24;
        static final int TRANSACTION_getCameraCharacteristics = 10;
        static final int TRANSACTION_getCameraInfo = 2;
        static final int TRANSACTION_getCameraVendorTagCache = 12;
        static final int TRANSACTION_getCameraVendorTagDescriptor = 11;
        static final int TRANSACTION_getConcurrentCameraIds = 6;
        static final int TRANSACTION_getLegacyParameters = 13;
        static final int TRANSACTION_getNumberOfCameras = 1;
        static final int TRANSACTION_getSessionCharacteristics = 26;
        static final int TRANSACTION_getTorchStrengthLevel = 18;
        static final int TRANSACTION_injectCamera = 15;
        static final int TRANSACTION_injectSessionParams = 8;
        static final int TRANSACTION_isConcurrentSessionConfigurationSupported = 7;
        static final int TRANSACTION_isHiddenIdPermittedPackage = 27;
        static final int TRANSACTION_isHiddenPhysicalCamera = 14;
        static final int TRANSACTION_isSessionConfigurationWithParametersSupported = 25;
        static final int TRANSACTION_notifyDeviceInjectorOrientationChange = 37;
        static final int TRANSACTION_notifyDeviceStateChange = 21;
        static final int TRANSACTION_notifyDeviceStateChangeSync = 23;
        static final int TRANSACTION_notifyDisplayConfigurationChange = 20;
        static final int TRANSACTION_notifyPkgListParamChange = 28;
        static final int TRANSACTION_notifySystemEvent = 19;
        static final int TRANSACTION_removeListener = 9;
        static final int TRANSACTION_removeRequestInjectorCallback = 32;
        static final int TRANSACTION_reportExtensionSessionStats = 22;
        static final int TRANSACTION_setDeviceInjectorPending = 36;
        static final int TRANSACTION_setRequestInjectorCallback = 31;
        static final int TRANSACTION_setTorchMode = 16;
        static final int TRANSACTION_startDeviceInjector = 33;
        static final int TRANSACTION_startRemoteDeviceInjector = 34;
        static final int TRANSACTION_stopDeviceInjector = 35;
        static final int TRANSACTION_turnOnTorchWithStrengthLevel = 17;
        static final int TRANSACTION_updateRequestInjectorAllowedList = 30;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 36;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICameraService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICameraService)) {
                return (ICameraService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getNumberOfCameras";
                case 2:
                    return "getCameraInfo";
                case 3:
                    return MediaMetrics.Value.CONNECT;
                case 4:
                    return "connectDevice";
                case 5:
                    return "addListener";
                case 6:
                    return "getConcurrentCameraIds";
                case 7:
                    return "isConcurrentSessionConfigurationSupported";
                case 8:
                    return "injectSessionParams";
                case 9:
                    return "removeListener";
                case 10:
                    return "getCameraCharacteristics";
                case 11:
                    return "getCameraVendorTagDescriptor";
                case 12:
                    return "getCameraVendorTagCache";
                case 13:
                    return "getLegacyParameters";
                case 14:
                    return "isHiddenPhysicalCamera";
                case 15:
                    return "injectCamera";
                case 16:
                    return "setTorchMode";
                case 17:
                    return "turnOnTorchWithStrengthLevel";
                case 18:
                    return "getTorchStrengthLevel";
                case 19:
                    return "notifySystemEvent";
                case 20:
                    return "notifyDisplayConfigurationChange";
                case 21:
                    return "notifyDeviceStateChange";
                case 22:
                    return "reportExtensionSessionStats";
                case 23:
                    return "notifyDeviceStateChangeSync";
                case 24:
                    return "createDefaultRequest";
                case 25:
                    return "isSessionConfigurationWithParametersSupported";
                case 26:
                    return "getSessionCharacteristics";
                case 27:
                    return "isHiddenIdPermittedPackage";
                case 28:
                    return "notifyPkgListParamChange";
                case 29:
                    return "applyExtraRequestsToRequestInjector";
                case 30:
                    return "updateRequestInjectorAllowedList";
                case 31:
                    return "setRequestInjectorCallback";
                case 32:
                    return "removeRequestInjectorCallback";
                case 33:
                    return "startDeviceInjector";
                case 34:
                    return "startRemoteDeviceInjector";
                case 35:
                    return "stopDeviceInjector";
                case 36:
                    return "setDeviceInjectorPending";
                case 37:
                    return "notifyDeviceInjectorOrientationChange";
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
                    AttributionSourceState attributionSourceState = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int numberOfCameras = getNumberOfCameras(readInt, attributionSourceState, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(numberOfCameras);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    AttributionSourceState attributionSourceState2 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CameraInfo cameraInfo = getCameraInfo(readInt3, readInt4, attributionSourceState2, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraInfo, 1);
                    return true;
                case 3:
                    ICameraClient asInterface = ICameraClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    AttributionSourceState attributionSourceState3 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ICamera connect = connect(asInterface, readInt6, readInt7, readInt8, readBoolean, attributionSourceState3, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(connect);
                    return true;
                case 4:
                    ICameraDeviceCallbacks asInterface2 = ICameraDeviceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    AttributionSourceState attributionSourceState4 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int readInt13 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ICameraDeviceUser connectDevice = connectDevice(asInterface2, readString, readInt10, readInt11, readInt12, attributionSourceState4, readInt13, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(connectDevice);
                    return true;
                case 5:
                    ICameraServiceListener asInterface3 = ICameraServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    CameraStatus[] addListener = addListener(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(addListener, 1);
                    return true;
                case 6:
                    ConcurrentCameraIdCombination[] concurrentCameraIds = getConcurrentCameraIds();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(concurrentCameraIds, 1);
                    return true;
                case 7:
                    CameraIdAndSessionConfiguration[] cameraIdAndSessionConfigurationArr = (CameraIdAndSessionConfiguration[]) parcel.createTypedArray(CameraIdAndSessionConfiguration.CREATOR);
                    int readInt14 = parcel.readInt();
                    AttributionSourceState attributionSourceState5 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isConcurrentSessionConfigurationSupported = isConcurrentSessionConfigurationSupported(cameraIdAndSessionConfigurationArr, readInt14, attributionSourceState5, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isConcurrentSessionConfigurationSupported);
                    return true;
                case 8:
                    String readString2 = parcel.readString();
                    CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectSessionParams(readString2, cameraMetadataNative);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ICameraServiceListener asInterface4 = ICameraServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString3 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    AttributionSourceState attributionSourceState6 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative cameraCharacteristics = getCameraCharacteristics(readString3, readInt16, readInt17, attributionSourceState6, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraCharacteristics, 1);
                    return true;
                case 11:
                    VendorTagDescriptor cameraVendorTagDescriptor = getCameraVendorTagDescriptor();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraVendorTagDescriptor, 1);
                    return true;
                case 12:
                    VendorTagDescriptorCache cameraVendorTagCache = getCameraVendorTagCache();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraVendorTagCache, 1);
                    return true;
                case 13:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String legacyParameters = getLegacyParameters(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeString(legacyParameters);
                    return true;
                case 14:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isHiddenPhysicalCamera = isHiddenPhysicalCamera(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHiddenPhysicalCamera);
                    return true;
                case 15:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    ICameraInjectionCallback asInterface5 = ICameraInjectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    ICameraInjectionSession injectCamera = injectCamera(readString5, readString6, readString7, asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(injectCamera);
                    return true;
                case 16:
                    String readString8 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    AttributionSourceState attributionSourceState7 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTorchMode(readString8, readBoolean3, readStrongBinder, attributionSourceState7, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String readString9 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    AttributionSourceState attributionSourceState8 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    turnOnTorchWithStrengthLevel(readString9, readInt21, readStrongBinder2, attributionSourceState8, readInt22);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String readString10 = parcel.readString();
                    AttributionSourceState attributionSourceState9 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int torchStrengthLevel = getTorchStrengthLevel(readString10, attributionSourceState9, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeInt(torchStrengthLevel);
                    return true;
                case 19:
                    int readInt24 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifySystemEvent(readInt24, createIntArray);
                    return true;
                case 20:
                    notifyDisplayConfigurationChange();
                    return true;
                case 21:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyDeviceStateChange(readLong);
                    return true;
                case 22:
                    CameraExtensionSessionStats cameraExtensionSessionStats = (CameraExtensionSessionStats) parcel.readTypedObject(CameraExtensionSessionStats.CREATOR);
                    parcel.enforceNoDataAvail();
                    String reportExtensionSessionStats = reportExtensionSessionStats(cameraExtensionSessionStats);
                    parcel2.writeNoException();
                    parcel2.writeString(reportExtensionSessionStats);
                    return true;
                case 23:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyDeviceStateChangeSync(readLong2);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String readString11 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    AttributionSourceState attributionSourceState10 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative createDefaultRequest = createDefaultRequest(readString11, readInt25, attributionSourceState10, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createDefaultRequest, 1);
                    return true;
                case 25:
                    String readString12 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    SessionConfiguration sessionConfiguration = (SessionConfiguration) parcel.readTypedObject(SessionConfiguration.CREATOR);
                    AttributionSourceState attributionSourceState11 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSessionConfigurationWithParametersSupported = isSessionConfigurationWithParametersSupported(readString12, readInt27, sessionConfiguration, attributionSourceState11, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSessionConfigurationWithParametersSupported);
                    return true;
                case 26:
                    String readString13 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    SessionConfiguration sessionConfiguration2 = (SessionConfiguration) parcel.readTypedObject(SessionConfiguration.CREATOR);
                    AttributionSourceState attributionSourceState12 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative sessionCharacteristics = getSessionCharacteristics(readString13, readInt29, readInt30, sessionConfiguration2, attributionSourceState12, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionCharacteristics, 1);
                    return true;
                case 27:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isHiddenIdPermittedPackage = isHiddenIdPermittedPackage(readString14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHiddenIdPermittedPackage);
                    return true;
                case 28:
                    int readInt32 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    notifyPkgListParamChange(readInt32, createStringArray, createStringArray2);
                    return true;
                case 29:
                    PersistableBundle[] persistableBundleArr = (PersistableBundle[]) parcel.createTypedArray(PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean applyExtraRequestsToRequestInjector = applyExtraRequestsToRequestInjector(persistableBundleArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applyExtraRequestsToRequestInjector);
                    return true;
                case 30:
                    String[] createStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    updateRequestInjectorAllowedList(createStringArray3);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IRequestInjectorCallback asInterface6 = IRequestInjectorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean requestInjectorCallback = setRequestInjectorCallback(asInterface6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestInjectorCallback);
                    return true;
                case 32:
                    removeRequestInjectorCallback();
                    parcel2.writeNoException();
                    return true;
                case 33:
                    String[] createStringArray4 = parcel.createStringArray();
                    String[] createStringArray5 = parcel.createStringArray();
                    String readString15 = parcel.readString();
                    IDeviceInjectorCallback asInterface7 = IDeviceInjectorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startDeviceInjector(createStringArray4, createStringArray5, readString15, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String[] createStringArray6 = parcel.createStringArray();
                    String[] createStringArray7 = parcel.createStringArray();
                    IRemoteDevice asInterface8 = IRemoteDevice.Stub.asInterface(parcel.readStrongBinder());
                    IDeviceInjectorCallback asInterface9 = IDeviceInjectorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startRemoteDeviceInjector(createStringArray6, createStringArray7, asInterface8, asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    stopDeviceInjector();
                    parcel2.writeNoException();
                    return true;
                case 36:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceInjectorPending(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    notifyDeviceInjectorOrientationChange();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICameraService {
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

            @Override // android.hardware.ICameraService
            public int getNumberOfCameras(int i, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraInfo getCameraInfo(int i, int i2, AttributionSourceState attributionSourceState, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraInfo) obtain2.readTypedObject(CameraInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public ICamera connect(ICameraClient iCameraClient, int i, int i2, int i3, boolean z, AttributionSourceState attributionSourceState, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCameraClient);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeInt(i4);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICamera.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public ICameraDeviceUser connectDevice(ICameraDeviceCallbacks iCameraDeviceCallbacks, String str, int i, int i2, int i3, AttributionSourceState attributionSourceState, int i4, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCameraDeviceCallbacks);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeInt(i4);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICameraDeviceUser.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraStatus[] addListener(ICameraServiceListener iCameraServiceListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCameraServiceListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraStatus[]) obtain2.createTypedArray(CameraStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public ConcurrentCameraIdCombination[] getConcurrentCameraIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ConcurrentCameraIdCombination[]) obtain2.createTypedArray(ConcurrentCameraIdCombination.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isConcurrentSessionConfigurationSupported(CameraIdAndSessionConfiguration[] cameraIdAndSessionConfigurationArr, int i, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(cameraIdAndSessionConfigurationArr, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void injectSessionParams(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void removeListener(ICameraServiceListener iCameraServiceListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCameraServiceListener);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraMetadataNative getCameraCharacteristics(String str, int i, int i2, AttributionSourceState attributionSourceState, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraMetadataNative) obtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public VendorTagDescriptor getCameraVendorTagDescriptor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VendorTagDescriptor) obtain2.readTypedObject(VendorTagDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public VendorTagDescriptorCache getCameraVendorTagCache() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (VendorTagDescriptorCache) obtain2.readTypedObject(VendorTagDescriptorCache.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public String getLegacyParameters(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isHiddenPhysicalCamera(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public ICameraInjectionSession injectCamera(String str, String str2, String str3, ICameraInjectionCallback iCameraInjectionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iCameraInjectionCallback);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICameraInjectionSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void setTorchMode(String str, boolean z, IBinder iBinder, AttributionSourceState attributionSourceState, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void turnOnTorchWithStrengthLevel(String str, int i, IBinder iBinder, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public int getTorchStrengthLevel(String str, AttributionSourceState attributionSourceState, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifySystemEvent(int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDisplayConfigurationChange() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDeviceStateChange(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public String reportExtensionSessionStats(CameraExtensionSessionStats cameraExtensionSessionStats) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(cameraExtensionSessionStats, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDeviceStateChangeSync(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraMetadataNative createDefaultRequest(String str, int i, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraMetadataNative) obtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isSessionConfigurationWithParametersSupported(String str, int i, SessionConfiguration sessionConfiguration, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sessionConfiguration, 0);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraMetadataNative getSessionCharacteristics(String str, int i, int i2, SessionConfiguration sessionConfiguration, AttributionSourceState attributionSourceState, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(sessionConfiguration, 0);
                    obtain.writeTypedObject(attributionSourceState, 0);
                    obtain.writeInt(i3);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CameraMetadataNative) obtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isHiddenIdPermittedPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyPkgListParamChange(int i, String[] strArr, String[] strArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeStringArray(strArr2);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean applyExtraRequestsToRequestInjector(PersistableBundle[] persistableBundleArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(persistableBundleArr, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void updateRequestInjectorAllowedList(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean setRequestInjectorCallback(IRequestInjectorCallback iRequestInjectorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRequestInjectorCallback);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void removeRequestInjectorCallback() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void startDeviceInjector(String[] strArr, String[] strArr2, String str, IDeviceInjectorCallback iDeviceInjectorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeStringArray(strArr2);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iDeviceInjectorCallback);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void startRemoteDeviceInjector(String[] strArr, String[] strArr2, IRemoteDevice iRemoteDevice, IDeviceInjectorCallback iDeviceInjectorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeStringArray(strArr2);
                    obtain.writeStrongInterface(iRemoteDevice);
                    obtain.writeStrongInterface(iDeviceInjectorCallback);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void stopDeviceInjector() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void setDeviceInjectorPending(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDeviceInjectorOrientationChange() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
