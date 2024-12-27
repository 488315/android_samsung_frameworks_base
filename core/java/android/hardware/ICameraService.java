package android.hardware;

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

/* loaded from: classes.dex */
public interface ICameraService extends IInterface {
    public static final int API_VERSION_1 = 1;
    public static final int API_VERSION_2 = 2;
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

    CameraStatus[] addListener(ICameraServiceListener iCameraServiceListener) throws RemoteException;

    boolean applyExtraRequestsToRequestInjector(PersistableBundle[] persistableBundleArr) throws RemoteException;

    ICamera connect(ICameraClient iCameraClient, int i, String str, int i2, int i3, int i4, int i5, boolean z, int i6, int i7) throws RemoteException;

    ICameraDeviceUser connectDevice(ICameraDeviceCallbacks iCameraDeviceCallbacks, String str, String str2, String str3, int i, int i2, int i3, int i4, int i5, int i6) throws RemoteException;

    CameraMetadataNative createDefaultRequest(String str, int i, int i2, int i3) throws RemoteException;

    CameraMetadataNative getCameraCharacteristics(String str, int i, int i2, int i3, int i4) throws RemoteException;

    CameraInfo getCameraInfo(int i, int i2, int i3, int i4) throws RemoteException;

    VendorTagDescriptorCache getCameraVendorTagCache() throws RemoteException;

    VendorTagDescriptor getCameraVendorTagDescriptor() throws RemoteException;

    ConcurrentCameraIdCombination[] getConcurrentCameraIds() throws RemoteException;

    String getLegacyParameters(int i) throws RemoteException;

    int getNumberOfCameras(int i, int i2, int i3) throws RemoteException;

    CameraMetadataNative getSessionCharacteristics(String str, int i, int i2, SessionConfiguration sessionConfiguration, int i3, int i4) throws RemoteException;

    int getTorchStrengthLevel(String str, int i, int i2) throws RemoteException;

    ICameraInjectionSession injectCamera(String str, String str2, String str3, ICameraInjectionCallback iCameraInjectionCallback) throws RemoteException;

    void injectSessionParams(String str, CameraMetadataNative cameraMetadataNative) throws RemoteException;

    boolean isConcurrentSessionConfigurationSupported(CameraIdAndSessionConfiguration[] cameraIdAndSessionConfigurationArr, int i, int i2, int i3) throws RemoteException;

    boolean isHiddenIdPermittedPackage(String str) throws RemoteException;

    boolean isHiddenPhysicalCamera(String str) throws RemoteException;

    boolean isSessionConfigurationWithParametersSupported(String str, int i, SessionConfiguration sessionConfiguration, int i2, int i3) throws RemoteException;

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

    void setTorchMode(String str, boolean z, IBinder iBinder, int i, int i2) throws RemoteException;

    void startDeviceInjector(String[] strArr, String[] strArr2, String str, IDeviceInjectorCallback iDeviceInjectorCallback) throws RemoteException;

    void startRemoteDeviceInjector(String[] strArr, String[] strArr2, IRemoteDevice iRemoteDevice, IDeviceInjectorCallback iDeviceInjectorCallback) throws RemoteException;

    void stopDeviceInjector() throws RemoteException;

    boolean supportsCameraApi(String str, int i) throws RemoteException;

    void turnOnTorchWithStrengthLevel(String str, int i, IBinder iBinder, int i2, int i3) throws RemoteException;

    void updateRequestInjectorAllowedList(String[] strArr) throws RemoteException;

    public static class Default implements ICameraService {
        @Override // android.hardware.ICameraService
        public int getNumberOfCameras(int type, int deviceId, int devicePolicy) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.ICameraService
        public CameraInfo getCameraInfo(int cameraId, int rotationOverride, int deviceId, int devicePolicy) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public ICamera connect(ICameraClient client, int cameraId, String opPackageName, int clientUid, int clientPid, int targetSdkVersion, int rotationOverride, boolean forceSlowJpegMode, int deviceId, int devicePolicy) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public ICameraDeviceUser connectDevice(ICameraDeviceCallbacks callbacks, String cameraId, String opPackageName, String featureId, int clientUid, int oomScoreOffset, int targetSdkVersion, int rotationOverride, int deviceId, int devicePolicy) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public CameraStatus[] addListener(ICameraServiceListener listener) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public ConcurrentCameraIdCombination[] getConcurrentCameraIds() throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public boolean isConcurrentSessionConfigurationSupported(CameraIdAndSessionConfiguration[] sessions, int targetSdkVersion, int deviceId, int devicePolicy) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public void injectSessionParams(String cameraId, CameraMetadataNative sessionParams) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void removeListener(ICameraServiceListener listener) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public CameraMetadataNative getCameraCharacteristics(String cameraId, int targetSdkVersion, int rotationOverride, int deviceId, int devicePolicy) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public VendorTagDescriptor getCameraVendorTagDescriptor() throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public VendorTagDescriptorCache getCameraVendorTagCache() throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public String getLegacyParameters(int cameraId) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public boolean supportsCameraApi(String cameraId, int apiVersion) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public boolean isHiddenPhysicalCamera(String cameraId) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public ICameraInjectionSession injectCamera(String packageName, String internalCamId, String externalCamId, ICameraInjectionCallback CameraInjectionCallback) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public void setTorchMode(String cameraId, boolean enabled, IBinder clientBinder, int deviceId, int devicePolicy) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void turnOnTorchWithStrengthLevel(String cameraId, int strengthLevel, IBinder clientBinder, int deviceId, int devicePolicy) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public int getTorchStrengthLevel(String cameraId, int deviceId, int devicePolicy) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.ICameraService
        public void notifySystemEvent(int eventId, int[] args) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void notifyDisplayConfigurationChange() throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void notifyDeviceStateChange(long newState) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public String reportExtensionSessionStats(CameraExtensionSessionStats stats) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public void notifyDeviceStateChangeSync(long newState) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public CameraMetadataNative createDefaultRequest(String cameraId, int templateId, int deviceId, int devicePolicy) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public boolean isSessionConfigurationWithParametersSupported(String cameraId, int targetSdkVersion, SessionConfiguration sessionConfiguration, int deviceId, int devicePolicy) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public CameraMetadataNative getSessionCharacteristics(String cameraId, int targetSdkVersion, int rotationOverride, SessionConfiguration sessionConfiguration, int deviceId, int devicePolicy) throws RemoteException {
            return null;
        }

        @Override // android.hardware.ICameraService
        public boolean isHiddenIdPermittedPackage(String pkgName) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public void notifyPkgListParamChange(int type, String[] pkgList, String[] args) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public boolean applyExtraRequestsToRequestInjector(PersistableBundle[] bundles) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public void updateRequestInjectorAllowedList(String[] pkgList) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public boolean setRequestInjectorCallback(IRequestInjectorCallback callback) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ICameraService
        public void removeRequestInjectorCallback() throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void startDeviceInjector(String[] targetPackages, String[] targetIds, String sourceId, IDeviceInjectorCallback deviceInjectorCallback) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void startRemoteDeviceInjector(String[] targetPackages, String[] targetIds, IRemoteDevice sourceDevice, IDeviceInjectorCallback deviceInjectorCallback) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void stopDeviceInjector() throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void setDeviceInjectorPending(boolean pending) throws RemoteException {
        }

        @Override // android.hardware.ICameraService
        public void notifyDeviceInjectorOrientationChange() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICameraService {
        public static final String DESCRIPTOR = "android.hardware.ICameraService";
        static final int TRANSACTION_addListener = 5;
        static final int TRANSACTION_applyExtraRequestsToRequestInjector = 30;
        static final int TRANSACTION_connect = 3;
        static final int TRANSACTION_connectDevice = 4;
        static final int TRANSACTION_createDefaultRequest = 25;
        static final int TRANSACTION_getCameraCharacteristics = 10;
        static final int TRANSACTION_getCameraInfo = 2;
        static final int TRANSACTION_getCameraVendorTagCache = 12;
        static final int TRANSACTION_getCameraVendorTagDescriptor = 11;
        static final int TRANSACTION_getConcurrentCameraIds = 6;
        static final int TRANSACTION_getLegacyParameters = 13;
        static final int TRANSACTION_getNumberOfCameras = 1;
        static final int TRANSACTION_getSessionCharacteristics = 27;
        static final int TRANSACTION_getTorchStrengthLevel = 19;
        static final int TRANSACTION_injectCamera = 16;
        static final int TRANSACTION_injectSessionParams = 8;
        static final int TRANSACTION_isConcurrentSessionConfigurationSupported = 7;
        static final int TRANSACTION_isHiddenIdPermittedPackage = 28;
        static final int TRANSACTION_isHiddenPhysicalCamera = 15;
        static final int TRANSACTION_isSessionConfigurationWithParametersSupported = 26;
        static final int TRANSACTION_notifyDeviceInjectorOrientationChange = 38;
        static final int TRANSACTION_notifyDeviceStateChange = 22;
        static final int TRANSACTION_notifyDeviceStateChangeSync = 24;
        static final int TRANSACTION_notifyDisplayConfigurationChange = 21;
        static final int TRANSACTION_notifyPkgListParamChange = 29;
        static final int TRANSACTION_notifySystemEvent = 20;
        static final int TRANSACTION_removeListener = 9;
        static final int TRANSACTION_removeRequestInjectorCallback = 33;
        static final int TRANSACTION_reportExtensionSessionStats = 23;
        static final int TRANSACTION_setDeviceInjectorPending = 37;
        static final int TRANSACTION_setRequestInjectorCallback = 32;
        static final int TRANSACTION_setTorchMode = 17;
        static final int TRANSACTION_startDeviceInjector = 34;
        static final int TRANSACTION_startRemoteDeviceInjector = 35;
        static final int TRANSACTION_stopDeviceInjector = 36;
        static final int TRANSACTION_supportsCameraApi = 14;
        static final int TRANSACTION_turnOnTorchWithStrengthLevel = 18;
        static final int TRANSACTION_updateRequestInjectorAllowedList = 31;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICameraService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ICameraService)) {
                return (ICameraService) iin;
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
                    return "supportsCameraApi";
                case 15:
                    return "isHiddenPhysicalCamera";
                case 16:
                    return "injectCamera";
                case 17:
                    return "setTorchMode";
                case 18:
                    return "turnOnTorchWithStrengthLevel";
                case 19:
                    return "getTorchStrengthLevel";
                case 20:
                    return "notifySystemEvent";
                case 21:
                    return "notifyDisplayConfigurationChange";
                case 22:
                    return "notifyDeviceStateChange";
                case 23:
                    return "reportExtensionSessionStats";
                case 24:
                    return "notifyDeviceStateChangeSync";
                case 25:
                    return "createDefaultRequest";
                case 26:
                    return "isSessionConfigurationWithParametersSupported";
                case 27:
                    return "getSessionCharacteristics";
                case 28:
                    return "isHiddenIdPermittedPackage";
                case 29:
                    return "notifyPkgListParamChange";
                case 30:
                    return "applyExtraRequestsToRequestInjector";
                case 31:
                    return "updateRequestInjectorAllowedList";
                case 32:
                    return "setRequestInjectorCallback";
                case 33:
                    return "removeRequestInjectorCallback";
                case 34:
                    return "startDeviceInjector";
                case 35:
                    return "startRemoteDeviceInjector";
                case 36:
                    return "stopDeviceInjector";
                case 37:
                    return "setDeviceInjectorPending";
                case 38:
                    return "notifyDeviceInjectorOrientationChange";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
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
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result = getNumberOfCameras(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    int _arg22 = data.readInt();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    CameraInfo _result2 = getCameraInfo(_arg02, _arg12, _arg22, _arg3);
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                case 3:
                    ICameraClient _arg03 = ICameraClient.Stub.asInterface(data.readStrongBinder());
                    int _arg13 = data.readInt();
                    String _arg23 = data.readString();
                    int _arg32 = data.readInt();
                    int _arg4 = data.readInt();
                    int _arg5 = data.readInt();
                    int _arg6 = data.readInt();
                    boolean _arg7 = data.readBoolean();
                    int _arg8 = data.readInt();
                    int _arg9 = data.readInt();
                    data.enforceNoDataAvail();
                    ICamera _result3 = connect(_arg03, _arg13, _arg23, _arg32, _arg4, _arg5, _arg6, _arg7, _arg8, _arg9);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result3);
                    return true;
                case 4:
                    ICameraDeviceCallbacks _arg04 = ICameraDeviceCallbacks.Stub.asInterface(data.readStrongBinder());
                    String _arg14 = data.readString();
                    String _arg24 = data.readString();
                    String _arg33 = data.readString();
                    int _arg42 = data.readInt();
                    int _arg52 = data.readInt();
                    int _arg62 = data.readInt();
                    int _arg72 = data.readInt();
                    int _arg82 = data.readInt();
                    int _arg92 = data.readInt();
                    data.enforceNoDataAvail();
                    ICameraDeviceUser _result4 = connectDevice(_arg04, _arg14, _arg24, _arg33, _arg42, _arg52, _arg62, _arg72, _arg82, _arg92);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result4);
                    return true;
                case 5:
                    ICameraServiceListener _arg05 = ICameraServiceListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    CameraStatus[] _result5 = addListener(_arg05);
                    reply.writeNoException();
                    reply.writeTypedArray(_result5, 1);
                    return true;
                case 6:
                    ConcurrentCameraIdCombination[] _result6 = getConcurrentCameraIds();
                    reply.writeNoException();
                    reply.writeTypedArray(_result6, 1);
                    return true;
                case 7:
                    CameraIdAndSessionConfiguration[] _arg06 = (CameraIdAndSessionConfiguration[]) data.createTypedArray(CameraIdAndSessionConfiguration.CREATOR);
                    int _arg15 = data.readInt();
                    int _arg25 = data.readInt();
                    int _arg34 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result7 = isConcurrentSessionConfigurationSupported(_arg06, _arg15, _arg25, _arg34);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 8:
                    String _arg07 = data.readString();
                    CameraMetadataNative _arg16 = (CameraMetadataNative) data.readTypedObject(CameraMetadataNative.CREATOR);
                    data.enforceNoDataAvail();
                    injectSessionParams(_arg07, _arg16);
                    reply.writeNoException();
                    return true;
                case 9:
                    ICameraServiceListener _arg08 = ICameraServiceListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeListener(_arg08);
                    reply.writeNoException();
                    return true;
                case 10:
                    String _arg09 = data.readString();
                    int _arg17 = data.readInt();
                    int _arg26 = data.readInt();
                    int _arg35 = data.readInt();
                    int _arg43 = data.readInt();
                    data.enforceNoDataAvail();
                    CameraMetadataNative _result8 = getCameraCharacteristics(_arg09, _arg17, _arg26, _arg35, _arg43);
                    reply.writeNoException();
                    reply.writeTypedObject(_result8, 1);
                    return true;
                case 11:
                    VendorTagDescriptor _result9 = getCameraVendorTagDescriptor();
                    reply.writeNoException();
                    reply.writeTypedObject(_result9, 1);
                    return true;
                case 12:
                    VendorTagDescriptorCache _result10 = getCameraVendorTagCache();
                    reply.writeNoException();
                    reply.writeTypedObject(_result10, 1);
                    return true;
                case 13:
                    int _arg010 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result11 = getLegacyParameters(_arg010);
                    reply.writeNoException();
                    reply.writeString(_result11);
                    return true;
                case 14:
                    String _arg011 = data.readString();
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result12 = supportsCameraApi(_arg011, _arg18);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 15:
                    String _arg012 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result13 = isHiddenPhysicalCamera(_arg012);
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 16:
                    String _arg013 = data.readString();
                    String _arg19 = data.readString();
                    String _arg27 = data.readString();
                    ICameraInjectionCallback _arg36 = ICameraInjectionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    ICameraInjectionSession _result14 = injectCamera(_arg013, _arg19, _arg27, _arg36);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result14);
                    return true;
                case 17:
                    String _arg014 = data.readString();
                    boolean _arg110 = data.readBoolean();
                    IBinder _arg28 = data.readStrongBinder();
                    int _arg37 = data.readInt();
                    int _arg44 = data.readInt();
                    data.enforceNoDataAvail();
                    setTorchMode(_arg014, _arg110, _arg28, _arg37, _arg44);
                    reply.writeNoException();
                    return true;
                case 18:
                    String _arg015 = data.readString();
                    int _arg111 = data.readInt();
                    IBinder _arg29 = data.readStrongBinder();
                    int _arg38 = data.readInt();
                    int _arg45 = data.readInt();
                    data.enforceNoDataAvail();
                    turnOnTorchWithStrengthLevel(_arg015, _arg111, _arg29, _arg38, _arg45);
                    reply.writeNoException();
                    return true;
                case 19:
                    String _arg016 = data.readString();
                    int _arg112 = data.readInt();
                    int _arg210 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result15 = getTorchStrengthLevel(_arg016, _arg112, _arg210);
                    reply.writeNoException();
                    reply.writeInt(_result15);
                    return true;
                case 20:
                    int _arg017 = data.readInt();
                    int[] _arg113 = data.createIntArray();
                    data.enforceNoDataAvail();
                    notifySystemEvent(_arg017, _arg113);
                    return true;
                case 21:
                    notifyDisplayConfigurationChange();
                    return true;
                case 22:
                    long _arg018 = data.readLong();
                    data.enforceNoDataAvail();
                    notifyDeviceStateChange(_arg018);
                    return true;
                case 23:
                    CameraExtensionSessionStats _arg019 = (CameraExtensionSessionStats) data.readTypedObject(CameraExtensionSessionStats.CREATOR);
                    data.enforceNoDataAvail();
                    String _result16 = reportExtensionSessionStats(_arg019);
                    reply.writeNoException();
                    reply.writeString(_result16);
                    return true;
                case 24:
                    long _arg020 = data.readLong();
                    data.enforceNoDataAvail();
                    notifyDeviceStateChangeSync(_arg020);
                    reply.writeNoException();
                    return true;
                case 25:
                    String _arg021 = data.readString();
                    int _arg114 = data.readInt();
                    int _arg211 = data.readInt();
                    int _arg39 = data.readInt();
                    data.enforceNoDataAvail();
                    CameraMetadataNative _result17 = createDefaultRequest(_arg021, _arg114, _arg211, _arg39);
                    reply.writeNoException();
                    reply.writeTypedObject(_result17, 1);
                    return true;
                case 26:
                    String _arg022 = data.readString();
                    int _arg115 = data.readInt();
                    SessionConfiguration _arg212 = (SessionConfiguration) data.readTypedObject(SessionConfiguration.CREATOR);
                    int _arg310 = data.readInt();
                    int _arg46 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result18 = isSessionConfigurationWithParametersSupported(_arg022, _arg115, _arg212, _arg310, _arg46);
                    reply.writeNoException();
                    reply.writeBoolean(_result18);
                    return true;
                case 27:
                    String _arg023 = data.readString();
                    int _arg116 = data.readInt();
                    int _arg213 = data.readInt();
                    SessionConfiguration _arg311 = (SessionConfiguration) data.readTypedObject(SessionConfiguration.CREATOR);
                    int _arg47 = data.readInt();
                    int _arg53 = data.readInt();
                    data.enforceNoDataAvail();
                    CameraMetadataNative _result19 = getSessionCharacteristics(_arg023, _arg116, _arg213, _arg311, _arg47, _arg53);
                    reply.writeNoException();
                    reply.writeTypedObject(_result19, 1);
                    return true;
                case 28:
                    String _arg024 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result20 = isHiddenIdPermittedPackage(_arg024);
                    reply.writeNoException();
                    reply.writeBoolean(_result20);
                    return true;
                case 29:
                    int _arg025 = data.readInt();
                    String[] _arg117 = data.createStringArray();
                    String[] _arg214 = data.createStringArray();
                    data.enforceNoDataAvail();
                    notifyPkgListParamChange(_arg025, _arg117, _arg214);
                    return true;
                case 30:
                    PersistableBundle[] _arg026 = (PersistableBundle[]) data.createTypedArray(PersistableBundle.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result21 = applyExtraRequestsToRequestInjector(_arg026);
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 31:
                    String[] _arg027 = data.createStringArray();
                    data.enforceNoDataAvail();
                    updateRequestInjectorAllowedList(_arg027);
                    reply.writeNoException();
                    return true;
                case 32:
                    IRequestInjectorCallback _arg028 = IRequestInjectorCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result22 = setRequestInjectorCallback(_arg028);
                    reply.writeNoException();
                    reply.writeBoolean(_result22);
                    return true;
                case 33:
                    removeRequestInjectorCallback();
                    reply.writeNoException();
                    return true;
                case 34:
                    String[] _arg029 = data.createStringArray();
                    String[] _arg118 = data.createStringArray();
                    String _arg215 = data.readString();
                    IDeviceInjectorCallback _arg312 = IDeviceInjectorCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    startDeviceInjector(_arg029, _arg118, _arg215, _arg312);
                    reply.writeNoException();
                    return true;
                case 35:
                    String[] _arg030 = data.createStringArray();
                    String[] _arg119 = data.createStringArray();
                    IRemoteDevice _arg216 = IRemoteDevice.Stub.asInterface(data.readStrongBinder());
                    IDeviceInjectorCallback _arg313 = IDeviceInjectorCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    startRemoteDeviceInjector(_arg030, _arg119, _arg216, _arg313);
                    reply.writeNoException();
                    return true;
                case 36:
                    stopDeviceInjector();
                    reply.writeNoException();
                    return true;
                case 37:
                    boolean _arg031 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDeviceInjectorPending(_arg031);
                    reply.writeNoException();
                    return true;
                case 38:
                    notifyDeviceInjectorOrientationChange();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICameraService {
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

            @Override // android.hardware.ICameraService
            public int getNumberOfCameras(int type, int deviceId, int devicePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(deviceId);
                    _data.writeInt(devicePolicy);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraInfo getCameraInfo(int cameraId, int rotationOverride, int deviceId, int devicePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cameraId);
                    _data.writeInt(rotationOverride);
                    _data.writeInt(deviceId);
                    _data.writeInt(devicePolicy);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    CameraInfo _result = (CameraInfo) _reply.readTypedObject(CameraInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public ICamera connect(ICameraClient client, int cameraId, String opPackageName, int clientUid, int clientPid, int targetSdkVersion, int rotationOverride, boolean forceSlowJpegMode, int deviceId, int devicePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeInt(cameraId);
                    _data.writeString(opPackageName);
                    _data.writeInt(clientUid);
                    _data.writeInt(clientPid);
                    _data.writeInt(targetSdkVersion);
                    _data.writeInt(rotationOverride);
                    _data.writeBoolean(forceSlowJpegMode);
                    _data.writeInt(deviceId);
                    _data.writeInt(devicePolicy);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    ICamera _result = ICamera.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public ICameraDeviceUser connectDevice(ICameraDeviceCallbacks callbacks, String cameraId, String opPackageName, String featureId, int clientUid, int oomScoreOffset, int targetSdkVersion, int rotationOverride, int deviceId, int devicePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callbacks);
                    _data.writeString(cameraId);
                    _data.writeString(opPackageName);
                    _data.writeString(featureId);
                    _data.writeInt(clientUid);
                    _data.writeInt(oomScoreOffset);
                    _data.writeInt(targetSdkVersion);
                    _data.writeInt(rotationOverride);
                    _data.writeInt(deviceId);
                    _data.writeInt(devicePolicy);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    ICameraDeviceUser _result = ICameraDeviceUser.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraStatus[] addListener(ICameraServiceListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    CameraStatus[] _result = (CameraStatus[]) _reply.createTypedArray(CameraStatus.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public ConcurrentCameraIdCombination[] getConcurrentCameraIds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    ConcurrentCameraIdCombination[] _result = (ConcurrentCameraIdCombination[]) _reply.createTypedArray(ConcurrentCameraIdCombination.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isConcurrentSessionConfigurationSupported(CameraIdAndSessionConfiguration[] sessions, int targetSdkVersion, int deviceId, int devicePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(sessions, 0);
                    _data.writeInt(targetSdkVersion);
                    _data.writeInt(deviceId);
                    _data.writeInt(devicePolicy);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void injectSessionParams(String cameraId, CameraMetadataNative sessionParams) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeTypedObject(sessionParams, 0);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void removeListener(ICameraServiceListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraMetadataNative getCameraCharacteristics(String cameraId, int targetSdkVersion, int rotationOverride, int deviceId, int devicePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeInt(targetSdkVersion);
                    _data.writeInt(rotationOverride);
                    _data.writeInt(deviceId);
                    _data.writeInt(devicePolicy);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    CameraMetadataNative _result = (CameraMetadataNative) _reply.readTypedObject(CameraMetadataNative.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public VendorTagDescriptor getCameraVendorTagDescriptor() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    VendorTagDescriptor _result = (VendorTagDescriptor) _reply.readTypedObject(VendorTagDescriptor.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public VendorTagDescriptorCache getCameraVendorTagCache() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    VendorTagDescriptorCache _result = (VendorTagDescriptorCache) _reply.readTypedObject(VendorTagDescriptorCache.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public String getLegacyParameters(int cameraId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cameraId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean supportsCameraApi(String cameraId, int apiVersion) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeInt(apiVersion);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isHiddenPhysicalCamera(String cameraId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public ICameraInjectionSession injectCamera(String packageName, String internalCamId, String externalCamId, ICameraInjectionCallback CameraInjectionCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(internalCamId);
                    _data.writeString(externalCamId);
                    _data.writeStrongInterface(CameraInjectionCallback);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    ICameraInjectionSession _result = ICameraInjectionSession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void setTorchMode(String cameraId, boolean enabled, IBinder clientBinder, int deviceId, int devicePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeBoolean(enabled);
                    _data.writeStrongBinder(clientBinder);
                    _data.writeInt(deviceId);
                    _data.writeInt(devicePolicy);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void turnOnTorchWithStrengthLevel(String cameraId, int strengthLevel, IBinder clientBinder, int deviceId, int devicePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeInt(strengthLevel);
                    _data.writeStrongBinder(clientBinder);
                    _data.writeInt(deviceId);
                    _data.writeInt(devicePolicy);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public int getTorchStrengthLevel(String cameraId, int deviceId, int devicePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeInt(deviceId);
                    _data.writeInt(devicePolicy);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifySystemEvent(int eventId, int[] args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(eventId);
                    _data.writeIntArray(args);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDisplayConfigurationChange() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDeviceStateChange(long newState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(newState);
                    this.mRemote.transact(22, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public String reportExtensionSessionStats(CameraExtensionSessionStats stats) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(stats, 0);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDeviceStateChangeSync(long newState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(newState);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraMetadataNative createDefaultRequest(String cameraId, int templateId, int deviceId, int devicePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeInt(templateId);
                    _data.writeInt(deviceId);
                    _data.writeInt(devicePolicy);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    CameraMetadataNative _result = (CameraMetadataNative) _reply.readTypedObject(CameraMetadataNative.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isSessionConfigurationWithParametersSupported(String cameraId, int targetSdkVersion, SessionConfiguration sessionConfiguration, int deviceId, int devicePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeInt(targetSdkVersion);
                    _data.writeTypedObject(sessionConfiguration, 0);
                    _data.writeInt(deviceId);
                    _data.writeInt(devicePolicy);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public CameraMetadataNative getSessionCharacteristics(String cameraId, int targetSdkVersion, int rotationOverride, SessionConfiguration sessionConfiguration, int deviceId, int devicePolicy) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(cameraId);
                    _data.writeInt(targetSdkVersion);
                    _data.writeInt(rotationOverride);
                    _data.writeTypedObject(sessionConfiguration, 0);
                    _data.writeInt(deviceId);
                    _data.writeInt(devicePolicy);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    CameraMetadataNative _result = (CameraMetadataNative) _reply.readTypedObject(CameraMetadataNative.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean isHiddenIdPermittedPackage(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyPkgListParamChange(int type, String[] pkgList, String[] args) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeStringArray(pkgList);
                    _data.writeStringArray(args);
                    this.mRemote.transact(29, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean applyExtraRequestsToRequestInjector(PersistableBundle[] bundles) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(bundles, 0);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void updateRequestInjectorAllowedList(String[] pkgList) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(pkgList);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public boolean setRequestInjectorCallback(IRequestInjectorCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void removeRequestInjectorCallback() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void startDeviceInjector(String[] targetPackages, String[] targetIds, String sourceId, IDeviceInjectorCallback deviceInjectorCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(targetPackages);
                    _data.writeStringArray(targetIds);
                    _data.writeString(sourceId);
                    _data.writeStrongInterface(deviceInjectorCallback);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void startRemoteDeviceInjector(String[] targetPackages, String[] targetIds, IRemoteDevice sourceDevice, IDeviceInjectorCallback deviceInjectorCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(targetPackages);
                    _data.writeStringArray(targetIds);
                    _data.writeStrongInterface(sourceDevice);
                    _data.writeStrongInterface(deviceInjectorCallback);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void stopDeviceInjector() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void setDeviceInjectorPending(boolean pending) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(pending);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.ICameraService
            public void notifyDeviceInjectorOrientationChange() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 37;
        }
    }
}
