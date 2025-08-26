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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICameraService)) {
                return (ICameraService) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    AttributionSourceState attributionSourceState = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int numberOfCameras = getNumberOfCameras(i3, attributionSourceState, i4);
                    parcel2.writeNoException();
                    parcel2.writeInt(numberOfCameras);
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    AttributionSourceState attributionSourceState2 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CameraInfo cameraInfo = getCameraInfo(i5, i6, attributionSourceState2, i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraInfo, 1);
                    return true;
                case 3:
                    ICameraClient iCameraClientAsInterface = ICameraClient.Stub.asInterface(parcel.readStrongBinder());
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    AttributionSourceState attributionSourceState3 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ICamera iCameraConnect = connect(iCameraClientAsInterface, i8, i9, i10, z, attributionSourceState3, i11);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCameraConnect);
                    return true;
                case 4:
                    ICameraDeviceCallbacks iCameraDeviceCallbacksAsInterface = ICameraDeviceCallbacks.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    AttributionSourceState attributionSourceState4 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int i15 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ICameraDeviceUser iCameraDeviceUserConnectDevice = connectDevice(iCameraDeviceCallbacksAsInterface, string, i12, i13, i14, attributionSourceState4, i15, z2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCameraDeviceUserConnectDevice);
                    return true;
                case 5:
                    ICameraServiceListener iCameraServiceListenerAsInterface = ICameraServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    CameraStatus[] cameraStatusArrAddListener = addListener(iCameraServiceListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(cameraStatusArrAddListener, 1);
                    return true;
                case 6:
                    ConcurrentCameraIdCombination[] concurrentCameraIds = getConcurrentCameraIds();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(concurrentCameraIds, 1);
                    return true;
                case 7:
                    CameraIdAndSessionConfiguration[] cameraIdAndSessionConfigurationArr = (CameraIdAndSessionConfiguration[]) parcel.createTypedArray(CameraIdAndSessionConfiguration.CREATOR);
                    int i16 = parcel.readInt();
                    AttributionSourceState attributionSourceState5 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsConcurrentSessionConfigurationSupported = isConcurrentSessionConfigurationSupported(cameraIdAndSessionConfigurationArr, i16, attributionSourceState5, i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsConcurrentSessionConfigurationSupported);
                    return true;
                case 8:
                    String string2 = parcel.readString();
                    CameraMetadataNative cameraMetadataNative = (CameraMetadataNative) parcel.readTypedObject(CameraMetadataNative.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectSessionParams(string2, cameraMetadataNative);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ICameraServiceListener iCameraServiceListenerAsInterface2 = ICameraServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeListener(iCameraServiceListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string3 = parcel.readString();
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    AttributionSourceState attributionSourceState6 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative cameraCharacteristics = getCameraCharacteristics(string3, i18, i19, attributionSourceState6, i20);
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
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String legacyParameters = getLegacyParameters(i21);
                    parcel2.writeNoException();
                    parcel2.writeString(legacyParameters);
                    return true;
                case 14:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsHiddenPhysicalCamera = isHiddenPhysicalCamera(string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHiddenPhysicalCamera);
                    return true;
                case 15:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    ICameraInjectionCallback iCameraInjectionCallbackAsInterface = ICameraInjectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    ICameraInjectionSession iCameraInjectionSessionInjectCamera = injectCamera(string5, string6, string7, iCameraInjectionCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iCameraInjectionSessionInjectCamera);
                    return true;
                case 16:
                    String string8 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    IBinder strongBinder = parcel.readStrongBinder();
                    AttributionSourceState attributionSourceState7 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTorchMode(string8, z3, strongBinder, attributionSourceState7, i22);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String string9 = parcel.readString();
                    int i23 = parcel.readInt();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    AttributionSourceState attributionSourceState8 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    turnOnTorchWithStrengthLevel(string9, i23, strongBinder2, attributionSourceState8, i24);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String string10 = parcel.readString();
                    AttributionSourceState attributionSourceState9 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int torchStrengthLevel = getTorchStrengthLevel(string10, attributionSourceState9, i25);
                    parcel2.writeNoException();
                    parcel2.writeInt(torchStrengthLevel);
                    return true;
                case 19:
                    int i26 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    notifySystemEvent(i26, iArrCreateIntArray);
                    return true;
                case 20:
                    notifyDisplayConfigurationChange();
                    return true;
                case 21:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyDeviceStateChange(j);
                    return true;
                case 22:
                    CameraExtensionSessionStats cameraExtensionSessionStats = (CameraExtensionSessionStats) parcel.readTypedObject(CameraExtensionSessionStats.CREATOR);
                    parcel.enforceNoDataAvail();
                    String strReportExtensionSessionStats = reportExtensionSessionStats(cameraExtensionSessionStats);
                    parcel2.writeNoException();
                    parcel2.writeString(strReportExtensionSessionStats);
                    return true;
                case 23:
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    notifyDeviceStateChangeSync(j2);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String string11 = parcel.readString();
                    int i27 = parcel.readInt();
                    AttributionSourceState attributionSourceState10 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative cameraMetadataNativeCreateDefaultRequest = createDefaultRequest(string11, i27, attributionSourceState10, i28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cameraMetadataNativeCreateDefaultRequest, 1);
                    return true;
                case 25:
                    String string12 = parcel.readString();
                    int i29 = parcel.readInt();
                    SessionConfiguration sessionConfiguration = (SessionConfiguration) parcel.readTypedObject(SessionConfiguration.CREATOR);
                    AttributionSourceState attributionSourceState11 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSessionConfigurationWithParametersSupported = isSessionConfigurationWithParametersSupported(string12, i29, sessionConfiguration, attributionSourceState11, i30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSessionConfigurationWithParametersSupported);
                    return true;
                case 26:
                    String string13 = parcel.readString();
                    int i31 = parcel.readInt();
                    int i32 = parcel.readInt();
                    SessionConfiguration sessionConfiguration2 = (SessionConfiguration) parcel.readTypedObject(SessionConfiguration.CREATOR);
                    AttributionSourceState attributionSourceState12 = (AttributionSourceState) parcel.readTypedObject(AttributionSourceState.CREATOR);
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CameraMetadataNative sessionCharacteristics = getSessionCharacteristics(string13, i31, i32, sessionConfiguration2, attributionSourceState12, i33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionCharacteristics, 1);
                    return true;
                case 27:
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsHiddenIdPermittedPackage = isHiddenIdPermittedPackage(string14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHiddenIdPermittedPackage);
                    return true;
                case 28:
                    int i34 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    notifyPkgListParamChange(i34, strArrCreateStringArray, strArrCreateStringArray2);
                    return true;
                case 29:
                    PersistableBundle[] persistableBundleArr = (PersistableBundle[]) parcel.createTypedArray(PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zApplyExtraRequestsToRequestInjector = applyExtraRequestsToRequestInjector(persistableBundleArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zApplyExtraRequestsToRequestInjector);
                    return true;
                case 30:
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    updateRequestInjectorAllowedList(strArrCreateStringArray3);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IRequestInjectorCallback iRequestInjectorCallbackAsInterface = IRequestInjectorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean requestInjectorCallback = setRequestInjectorCallback(iRequestInjectorCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestInjectorCallback);
                    return true;
                case 32:
                    removeRequestInjectorCallback();
                    parcel2.writeNoException();
                    return true;
                case 33:
                    String[] strArrCreateStringArray4 = parcel.createStringArray();
                    String[] strArrCreateStringArray5 = parcel.createStringArray();
                    String string15 = parcel.readString();
                    IDeviceInjectorCallback iDeviceInjectorCallbackAsInterface = IDeviceInjectorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startDeviceInjector(strArrCreateStringArray4, strArrCreateStringArray5, string15, iDeviceInjectorCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String[] strArrCreateStringArray6 = parcel.createStringArray();
                    String[] strArrCreateStringArray7 = parcel.createStringArray();
                    IRemoteDevice iRemoteDeviceAsInterface = IRemoteDevice.Stub.asInterface(parcel.readStrongBinder());
                    IDeviceInjectorCallback iDeviceInjectorCallbackAsInterface2 = IDeviceInjectorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startRemoteDeviceInjector(strArrCreateStringArray6, strArrCreateStringArray7, iRemoteDeviceAsInterface, iDeviceInjectorCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    stopDeviceInjector();
                    parcel2.writeNoException();
                    return true;
                case 36:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceInjectorPending(z4);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraInfo getCameraInfo(int i, int i2, AttributionSourceState attributionSourceState, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CameraInfo) parcelObtain2.readTypedObject(CameraInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public ICamera connect(ICameraClient iCameraClient, int i, int i2, int i3, boolean z, AttributionSourceState attributionSourceState, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCameraClient);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICamera.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public ICameraDeviceUser connectDevice(ICameraDeviceCallbacks iCameraDeviceCallbacks, String str, int i, int i2, int i3, AttributionSourceState attributionSourceState, int i4, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCameraDeviceCallbacks);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICameraDeviceUser.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraStatus[] addListener(ICameraServiceListener iCameraServiceListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCameraServiceListener);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CameraStatus[]) parcelObtain2.createTypedArray(CameraStatus.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public ConcurrentCameraIdCombination[] getConcurrentCameraIds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ConcurrentCameraIdCombination[]) parcelObtain2.createTypedArray(ConcurrentCameraIdCombination.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isConcurrentSessionConfigurationSupported(CameraIdAndSessionConfiguration[] cameraIdAndSessionConfigurationArr, int i, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(cameraIdAndSessionConfigurationArr, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void injectSessionParams(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(cameraMetadataNative, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void removeListener(ICameraServiceListener iCameraServiceListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCameraServiceListener);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraMetadataNative getCameraCharacteristics(String str, int i, int i2, AttributionSourceState attributionSourceState, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CameraMetadataNative) parcelObtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public VendorTagDescriptor getCameraVendorTagDescriptor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VendorTagDescriptor) parcelObtain2.readTypedObject(VendorTagDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public VendorTagDescriptorCache getCameraVendorTagCache() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (VendorTagDescriptorCache) parcelObtain2.readTypedObject(VendorTagDescriptorCache.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public String getLegacyParameters(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isHiddenPhysicalCamera(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public ICameraInjectionSession injectCamera(String str, String str2, String str3, ICameraInjectionCallback iCameraInjectionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeStrongInterface(iCameraInjectionCallback);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICameraInjectionSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void setTorchMode(String str, boolean z, IBinder iBinder, AttributionSourceState attributionSourceState, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void turnOnTorchWithStrengthLevel(String str, int i, IBinder iBinder, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public int getTorchStrengthLevel(String str, AttributionSourceState attributionSourceState, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifySystemEvent(int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDisplayConfigurationChange() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDeviceStateChange(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public String reportExtensionSessionStats(CameraExtensionSessionStats cameraExtensionSessionStats) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cameraExtensionSessionStats, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDeviceStateChangeSync(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraMetadataNative createDefaultRequest(String str, int i, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CameraMetadataNative) parcelObtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isSessionConfigurationWithParametersSupported(String str, int i, SessionConfiguration sessionConfiguration, AttributionSourceState attributionSourceState, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sessionConfiguration, 0);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraMetadataNative getSessionCharacteristics(String str, int i, int i2, SessionConfiguration sessionConfiguration, AttributionSourceState attributionSourceState, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(sessionConfiguration, 0);
                    parcelObtain.writeTypedObject(attributionSourceState, 0);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CameraMetadataNative) parcelObtain2.readTypedObject(CameraMetadataNative.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isHiddenIdPermittedPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyPkgListParamChange(int i, String[] strArr, String[] strArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStringArray(strArr2);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean applyExtraRequestsToRequestInjector(PersistableBundle[] persistableBundleArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(persistableBundleArr, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void updateRequestInjectorAllowedList(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean setRequestInjectorCallback(IRequestInjectorCallback iRequestInjectorCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRequestInjectorCallback);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void removeRequestInjectorCallback() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void startDeviceInjector(String[] strArr, String[] strArr2, String str, IDeviceInjectorCallback iDeviceInjectorCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStringArray(strArr2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iDeviceInjectorCallback);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void startRemoteDeviceInjector(String[] strArr, String[] strArr2, IRemoteDevice iRemoteDevice, IDeviceInjectorCallback iDeviceInjectorCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStringArray(strArr2);
                    parcelObtain.writeStrongInterface(iRemoteDevice);
                    parcelObtain.writeStrongInterface(iDeviceInjectorCallback);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void stopDeviceInjector() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void setDeviceInjectorPending(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDeviceInjectorOrientationChange() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
