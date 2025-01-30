package android.hardware.fingerprint;

import android.Manifest;
import android.app.ActivityThread;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.biometrics.IBiometricStateListener;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.fingerprint.PointerContext;
import android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback;
import android.hardware.fingerprint.IFingerprintClientActiveCallback;
import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.hardware.fingerprint.ISidefpsController;
import android.hardware.fingerprint.IUdfpsOverlay;
import android.hardware.fingerprint.IUdfpsOverlayController;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.PermissionEnforcer;
import android.p009os.RemoteException;
import com.samsung.android.bio.fingerprint.ISemFingerprintAodController;
import com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback;
import java.util.List;

/* loaded from: classes2.dex */
public interface IFingerprintService extends IInterface {
    void addAuthenticatorsRegisteredCallback(IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallback) throws RemoteException;

    void addClientActiveCallback(IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws RemoteException;

    void addLockoutResetCallback(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, String str) throws RemoteException;

    long authenticate(IBinder iBinder, long j, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws RemoteException;

    void cancelAuthentication(IBinder iBinder, String str, String str2, long j) throws RemoteException;

    void cancelAuthenticationFromService(int i, IBinder iBinder, String str, long j) throws RemoteException;

    void cancelEnrollment(IBinder iBinder, long j) throws RemoteException;

    void cancelFingerprintDetect(IBinder iBinder, String str, long j) throws RemoteException;

    ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) throws RemoteException;

    long detectFingerprint(IBinder iBinder, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws RemoteException;

    byte[] dumpSensorServiceStateProto(int i, boolean z) throws RemoteException;

    long enroll(IBinder iBinder, byte[] bArr, int i, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str, int i2) throws RemoteException;

    void generateChallenge(IBinder iBinder, int i, int i2, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) throws RemoteException;

    long getAuthenticatorId(int i, int i2) throws RemoteException;

    List<Fingerprint> getEnrolledFingerprints(int i, String str, String str2) throws RemoteException;

    int getLockoutModeForUser(int i, int i2) throws RemoteException;

    FingerprintSensorPropertiesInternal getSensorProperties(int i, String str) throws RemoteException;

    List<FingerprintSensorPropertiesInternal> getSensorPropertiesInternal(String str) throws RemoteException;

    boolean hasEnrolledFingerprints(int i, int i2, String str) throws RemoteException;

    boolean hasEnrolledFingerprintsDeprecated(int i, String str, String str2) throws RemoteException;

    void invalidateAuthenticatorId(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException;

    boolean isClientActive() throws RemoteException;

    boolean isHardwareDetected(int i, String str) throws RemoteException;

    boolean isHardwareDetectedDeprecated(String str, String str2) throws RemoteException;

    void onPointerDown(long j, int i, PointerContext pointerContext) throws RemoteException;

    void onPointerUp(long j, int i, PointerContext pointerContext) throws RemoteException;

    void onPowerPressed() throws RemoteException;

    void onUiReady(long j, int i) throws RemoteException;

    void prepareForAuthentication(IBinder iBinder, long j, IBiometricSensorReceiver iBiometricSensorReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, long j2, int i, boolean z) throws RemoteException;

    void registerAuthenticators(List<FingerprintSensorPropertiesInternal> list) throws RemoteException;

    void registerBiometricStateListener(IBiometricStateListener iBiometricStateListener) throws RemoteException;

    void remove(IBinder iBinder, int i, int i2, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) throws RemoteException;

    void removeAll(IBinder iBinder, int i, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) throws RemoteException;

    void removeClientActiveCallback(IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws RemoteException;

    void rename(int i, int i2, String str) throws RemoteException;

    void resetLockout(IBinder iBinder, int i, int i2, byte[] bArr, String str) throws RemoteException;

    void revokeChallenge(IBinder iBinder, int i, int i2, String str, long j) throws RemoteException;

    void scheduleWatchdog() throws RemoteException;

    IBinder semAddMaskView(IBinder iBinder, String str) throws RemoteException;

    long semAuthenticate(IBinder iBinder, long j, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, Bundle bundle) throws RemoteException;

    int semBioSysUiRequest(int i, int i2, long j, String str) throws RemoteException;

    boolean semCanChangeDeviceColorMode() throws RemoteException;

    void semForceCBGE() throws RemoteException;

    String semGetDaemonVersion() throws RemoteException;

    int semGetIconBottomMargin() throws RemoteException;

    int semGetMaxEnrollmentNumber() throws RemoteException;

    int semGetRemainingLockoutTime(int i) throws RemoteException;

    int semGetSecurityLevel() throws RemoteException;

    Rect semGetSensorAreaInDisplay(int i, int i2, Point point) throws RemoteException;

    void semGetSensorData(Bundle bundle) throws RemoteException;

    String semGetSensorInfo() throws RemoteException;

    int semGetSensorStatus() throws RemoteException;

    int semGetSensorTestResult(byte[] bArr) throws RemoteException;

    String semGetTrustAppVersion() throws RemoteException;

    String[] semGetUserIdList() throws RemoteException;

    boolean semHasFeature(int i) throws RemoteException;

    boolean semIsEnrollSession() throws RemoteException;

    boolean semIsTemplateDbCorrupted() throws RemoteException;

    void semMoveSensorIconInDisplay(int i, int i2) throws RemoteException;

    boolean semOpenSession() throws RemoteException;

    boolean semPauseEnroll() throws RemoteException;

    int semProcessFido(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException;

    void semRegisterAodController(IBinder iBinder, ISemFingerprintAodController iSemFingerprintAodController) throws RemoteException;

    int semRegisterDisplayBrightnessCallback(ISemBiometricSysUiDisplayBrightnessCallback iSemBiometricSysUiDisplayBrightnessCallback) throws RemoteException;

    int semRegisterDisplayStateCallback(ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback) throws RemoteException;

    int semRemoveMaskView(IBinder iBinder, String str) throws RemoteException;

    int semRequest(IBinder iBinder, int i, byte[] bArr, byte[] bArr2, int i2, int i3, String str, ISemFingerprintRequestCallback iSemFingerprintRequestCallback) throws RemoteException;

    boolean semResumeEnroll() throws RemoteException;

    int semRunSensorTest(IBinder iBinder, int i, int i2, ISemFingerprintRequestCallback iSemFingerprintRequestCallback) throws RemoteException;

    int semSetCalibrationMode(IBinder iBinder, int i, String str) throws RemoteException;

    void semSetFlagForIFAA(int i, String str) throws RemoteException;

    void semSetFodStrictMode(boolean z) throws RemoteException;

    int semSetScreenStatus(int i) throws RemoteException;

    int semShowBouncerScreen(int i) throws RemoteException;

    void semUnregisterAodController(IBinder iBinder) throws RemoteException;

    void semUnregisterDisplayBrightnessCallback() throws RemoteException;

    void semUnregisterDisplayStateCallback() throws RemoteException;

    void semUpdateTrustApp(String str, ISemFingerprintRequestCallback iSemFingerprintRequestCallback, String str2) throws RemoteException;

    void setSidefpsController(ISidefpsController iSidefpsController) throws RemoteException;

    void setUdfpsOverlay(IUdfpsOverlay iUdfpsOverlay) throws RemoteException;

    void setUdfpsOverlayController(IUdfpsOverlayController iUdfpsOverlayController) throws RemoteException;

    void startPreparedClient(int i, int i2) throws RemoteException;

    public static class Default implements IFingerprintService {
        @Override // android.hardware.fingerprint.IFingerprintService
        public ITestSession createTestSession(int sensorId, ITestSessionCallback callback, String opPackageName) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public byte[] dumpSensorServiceStateProto(int sensorId, boolean clearSchedulerBuffer) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public List<FingerprintSensorPropertiesInternal> getSensorPropertiesInternal(String opPackageName) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public FingerprintSensorPropertiesInternal getSensorProperties(int sensorId, String opPackageName) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long authenticate(IBinder token, long operationId, IFingerprintServiceReceiver receiver, FingerprintAuthenticateOptions options) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long detectFingerprint(IBinder token, IFingerprintServiceReceiver receiver, FingerprintAuthenticateOptions options) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void prepareForAuthentication(IBinder token, long operationId, IBiometricSensorReceiver sensorReceiver, FingerprintAuthenticateOptions options, long requestId, int cookie, boolean allowBackgroundAuthentication) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void startPreparedClient(int sensorId, int cookie) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void cancelAuthentication(IBinder token, String opPackageName, String attributionTag, long requestId) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void cancelFingerprintDetect(IBinder token, String opPackageName, long requestId) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void cancelAuthenticationFromService(int sensorId, IBinder token, String opPackageName, long requestId) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long enroll(IBinder token, byte[] hardwareAuthToken, int userId, IFingerprintServiceReceiver receiver, String opPackageName, int enrollReason) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void cancelEnrollment(IBinder token, long requestId) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void remove(IBinder token, int fingerId, int userId, IFingerprintServiceReceiver receiver, String opPackageName) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void removeAll(IBinder token, int userId, IFingerprintServiceReceiver receiver, String opPackageName) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void rename(int fingerId, int userId, String name) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public List<Fingerprint> getEnrolledFingerprints(int userId, String opPackageName, String attributionTag) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean isHardwareDetectedDeprecated(String opPackageName, String attributionTag) throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean isHardwareDetected(int sensorId, String opPackageName) throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void generateChallenge(IBinder token, int sensorId, int userId, IFingerprintServiceReceiver receiver, String opPackageName) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void revokeChallenge(IBinder token, int sensorId, int userId, String opPackageName, long challenge) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean hasEnrolledFingerprintsDeprecated(int userId, String opPackageName, String attributionTag) throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean hasEnrolledFingerprints(int sensorId, int userId, String opPackageName) throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int getLockoutModeForUser(int sensorId, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void invalidateAuthenticatorId(int sensorId, int userId, IInvalidationCallback callback) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long getAuthenticatorId(int sensorId, int callingUserId) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void resetLockout(IBinder token, int sensorId, int userId, byte[] hardwareAuthToken, String opPackageNAame) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void addLockoutResetCallback(IBiometricServiceLockoutResetCallback callback, String opPackageName) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean isClientActive() throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void addClientActiveCallback(IFingerprintClientActiveCallback callback) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void removeClientActiveCallback(IFingerprintClientActiveCallback callback) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void registerAuthenticators(List<FingerprintSensorPropertiesInternal> hidlSensors) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void addAuthenticatorsRegisteredCallback(IFingerprintAuthenticatorsRegisteredCallback callback) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void onPointerDown(long requestId, int sensorId, PointerContext pc) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void onPointerUp(long requestId, int sensorId, PointerContext pc) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void onUiReady(long requestId, int sensorId) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void setUdfpsOverlayController(IUdfpsOverlayController controller) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void setSidefpsController(ISidefpsController controller) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void setUdfpsOverlay(IUdfpsOverlay controller) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void registerBiometricStateListener(IBiometricStateListener listener) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void onPowerPressed() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void scheduleWatchdog() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long semAuthenticate(IBinder token, long operationId, IFingerprintServiceReceiver receiver, FingerprintAuthenticateOptions options, Bundle bundle) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semGetMaxEnrollmentNumber() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean semHasFeature(int feature) throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semForceCBGE() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean semIsEnrollSession() throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean semIsTemplateDbCorrupted() throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semGetSensorStatus() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean semPauseEnroll() throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean semResumeEnroll() throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean semOpenSession() throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public String semGetSensorInfo() throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public String[] semGetUserIdList() throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public String semGetDaemonVersion() throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semRunSensorTest(IBinder token, int cmd, int param, ISemFingerprintRequestCallback callback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semGetSensorTestResult(byte[] outBuffer) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semSetScreenStatus(int screenStatus) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semShowBouncerScreen(int showStatus) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public IBinder semAddMaskView(IBinder token, String opPackageName) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semRemoveMaskView(IBinder token, String opPackageName) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semRegisterAodController(IBinder token, ISemFingerprintAodController aodController) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semUnregisterAodController(IBinder token) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public Rect semGetSensorAreaInDisplay(int type, int rotation, Point point) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semGetIconBottomMargin() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semMoveSensorIconInDisplay(int x, int y) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semGetSecurityLevel() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public String semGetTrustAppVersion() throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semUpdateTrustApp(String path, ISemFingerprintRequestCallback receiver, String opPackageName) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semBioSysUiRequest(int cmd, int arg1, long arg2, String arg3) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semRegisterDisplayStateCallback(ISemBiometricSysUiDisplayStateCallback cb) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semUnregisterDisplayStateCallback() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semRegisterDisplayBrightnessCallback(ISemBiometricSysUiDisplayBrightnessCallback cb) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semUnregisterDisplayBrightnessCallback() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semGetSensorData(Bundle bundle) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semSetFodStrictMode(boolean isStrictMode) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semSetCalibrationMode(IBinder token, int param, String opPackageName) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semProcessFido(int userId, byte[] inBuf, byte[] outBuf, String opPackageName) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semGetRemainingLockoutTime(int userId) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean semCanChangeDeviceColorMode() throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semSetFlagForIFAA(int flag, String targetAppPackageName) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semRequest(IBinder token, int cmd, byte[] inputBuf, byte[] outputBuf, int inParam, int groupId, String opPackageName, ISemFingerprintRequestCallback receiver) throws RemoteException {
            return 0;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IFingerprintService {
        public static final String DESCRIPTOR = "android.hardware.fingerprint.IFingerprintService";
        static final int TRANSACTION_addAuthenticatorsRegisteredCallback = 33;
        static final int TRANSACTION_addClientActiveCallback = 30;
        static final int TRANSACTION_addLockoutResetCallback = 28;
        static final int TRANSACTION_authenticate = 5;
        static final int TRANSACTION_cancelAuthentication = 9;
        static final int TRANSACTION_cancelAuthenticationFromService = 11;
        static final int TRANSACTION_cancelEnrollment = 13;
        static final int TRANSACTION_cancelFingerprintDetect = 10;
        static final int TRANSACTION_createTestSession = 1;
        static final int TRANSACTION_detectFingerprint = 6;
        static final int TRANSACTION_dumpSensorServiceStateProto = 2;
        static final int TRANSACTION_enroll = 12;
        static final int TRANSACTION_generateChallenge = 20;
        static final int TRANSACTION_getAuthenticatorId = 26;
        static final int TRANSACTION_getEnrolledFingerprints = 17;
        static final int TRANSACTION_getLockoutModeForUser = 24;
        static final int TRANSACTION_getSensorProperties = 4;
        static final int TRANSACTION_getSensorPropertiesInternal = 3;
        static final int TRANSACTION_hasEnrolledFingerprints = 23;
        static final int TRANSACTION_hasEnrolledFingerprintsDeprecated = 22;
        static final int TRANSACTION_invalidateAuthenticatorId = 25;
        static final int TRANSACTION_isClientActive = 29;
        static final int TRANSACTION_isHardwareDetected = 19;
        static final int TRANSACTION_isHardwareDetectedDeprecated = 18;
        static final int TRANSACTION_onPointerDown = 34;
        static final int TRANSACTION_onPointerUp = 35;
        static final int TRANSACTION_onPowerPressed = 41;
        static final int TRANSACTION_onUiReady = 36;
        static final int TRANSACTION_prepareForAuthentication = 7;
        static final int TRANSACTION_registerAuthenticators = 32;
        static final int TRANSACTION_registerBiometricStateListener = 40;
        static final int TRANSACTION_remove = 14;
        static final int TRANSACTION_removeAll = 15;
        static final int TRANSACTION_removeClientActiveCallback = 31;
        static final int TRANSACTION_rename = 16;
        static final int TRANSACTION_resetLockout = 27;
        static final int TRANSACTION_revokeChallenge = 21;
        static final int TRANSACTION_scheduleWatchdog = 42;
        static final int TRANSACTION_semAddMaskView = 60;
        static final int TRANSACTION_semAuthenticate = 43;
        static final int TRANSACTION_semBioSysUiRequest = 70;
        static final int TRANSACTION_semCanChangeDeviceColorMode = 80;
        static final int TRANSACTION_semForceCBGE = 46;
        static final int TRANSACTION_semGetDaemonVersion = 55;
        static final int TRANSACTION_semGetIconBottomMargin = 65;
        static final int TRANSACTION_semGetMaxEnrollmentNumber = 44;
        static final int TRANSACTION_semGetRemainingLockoutTime = 79;
        static final int TRANSACTION_semGetSecurityLevel = 67;
        static final int TRANSACTION_semGetSensorAreaInDisplay = 64;
        static final int TRANSACTION_semGetSensorData = 75;
        static final int TRANSACTION_semGetSensorInfo = 53;
        static final int TRANSACTION_semGetSensorStatus = 49;
        static final int TRANSACTION_semGetSensorTestResult = 57;
        static final int TRANSACTION_semGetTrustAppVersion = 68;
        static final int TRANSACTION_semGetUserIdList = 54;
        static final int TRANSACTION_semHasFeature = 45;
        static final int TRANSACTION_semIsEnrollSession = 47;
        static final int TRANSACTION_semIsTemplateDbCorrupted = 48;
        static final int TRANSACTION_semMoveSensorIconInDisplay = 66;
        static final int TRANSACTION_semOpenSession = 52;
        static final int TRANSACTION_semPauseEnroll = 50;
        static final int TRANSACTION_semProcessFido = 78;
        static final int TRANSACTION_semRegisterAodController = 62;
        static final int TRANSACTION_semRegisterDisplayBrightnessCallback = 73;
        static final int TRANSACTION_semRegisterDisplayStateCallback = 71;
        static final int TRANSACTION_semRemoveMaskView = 61;
        static final int TRANSACTION_semRequest = 82;
        static final int TRANSACTION_semResumeEnroll = 51;
        static final int TRANSACTION_semRunSensorTest = 56;
        static final int TRANSACTION_semSetCalibrationMode = 77;
        static final int TRANSACTION_semSetFlagForIFAA = 81;
        static final int TRANSACTION_semSetFodStrictMode = 76;
        static final int TRANSACTION_semSetScreenStatus = 58;
        static final int TRANSACTION_semShowBouncerScreen = 59;
        static final int TRANSACTION_semUnregisterAodController = 63;
        static final int TRANSACTION_semUnregisterDisplayBrightnessCallback = 74;
        static final int TRANSACTION_semUnregisterDisplayStateCallback = 72;
        static final int TRANSACTION_semUpdateTrustApp = 69;
        static final int TRANSACTION_setSidefpsController = 38;
        static final int TRANSACTION_setUdfpsOverlay = 39;
        static final int TRANSACTION_setUdfpsOverlayController = 37;
        static final int TRANSACTION_startPreparedClient = 8;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IFingerprintService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IFingerprintService)) {
                return (IFingerprintService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "createTestSession";
                case 2:
                    return "dumpSensorServiceStateProto";
                case 3:
                    return "getSensorPropertiesInternal";
                case 4:
                    return "getSensorProperties";
                case 5:
                    return "authenticate";
                case 6:
                    return "detectFingerprint";
                case 7:
                    return "prepareForAuthentication";
                case 8:
                    return "startPreparedClient";
                case 9:
                    return "cancelAuthentication";
                case 10:
                    return "cancelFingerprintDetect";
                case 11:
                    return "cancelAuthenticationFromService";
                case 12:
                    return "enroll";
                case 13:
                    return "cancelEnrollment";
                case 14:
                    return "remove";
                case 15:
                    return "removeAll";
                case 16:
                    return "rename";
                case 17:
                    return "getEnrolledFingerprints";
                case 18:
                    return "isHardwareDetectedDeprecated";
                case 19:
                    return "isHardwareDetected";
                case 20:
                    return "generateChallenge";
                case 21:
                    return "revokeChallenge";
                case 22:
                    return "hasEnrolledFingerprintsDeprecated";
                case 23:
                    return "hasEnrolledFingerprints";
                case 24:
                    return "getLockoutModeForUser";
                case 25:
                    return "invalidateAuthenticatorId";
                case 26:
                    return "getAuthenticatorId";
                case 27:
                    return "resetLockout";
                case 28:
                    return "addLockoutResetCallback";
                case 29:
                    return "isClientActive";
                case 30:
                    return "addClientActiveCallback";
                case 31:
                    return "removeClientActiveCallback";
                case 32:
                    return "registerAuthenticators";
                case 33:
                    return "addAuthenticatorsRegisteredCallback";
                case 34:
                    return "onPointerDown";
                case 35:
                    return "onPointerUp";
                case 36:
                    return "onUiReady";
                case 37:
                    return "setUdfpsOverlayController";
                case 38:
                    return "setSidefpsController";
                case 39:
                    return "setUdfpsOverlay";
                case 40:
                    return "registerBiometricStateListener";
                case 41:
                    return "onPowerPressed";
                case 42:
                    return "scheduleWatchdog";
                case 43:
                    return "semAuthenticate";
                case 44:
                    return "semGetMaxEnrollmentNumber";
                case 45:
                    return "semHasFeature";
                case 46:
                    return "semForceCBGE";
                case 47:
                    return "semIsEnrollSession";
                case 48:
                    return "semIsTemplateDbCorrupted";
                case 49:
                    return "semGetSensorStatus";
                case 50:
                    return "semPauseEnroll";
                case 51:
                    return "semResumeEnroll";
                case 52:
                    return "semOpenSession";
                case 53:
                    return "semGetSensorInfo";
                case 54:
                    return "semGetUserIdList";
                case 55:
                    return "semGetDaemonVersion";
                case 56:
                    return "semRunSensorTest";
                case 57:
                    return "semGetSensorTestResult";
                case 58:
                    return "semSetScreenStatus";
                case 59:
                    return "semShowBouncerScreen";
                case 60:
                    return "semAddMaskView";
                case 61:
                    return "semRemoveMaskView";
                case 62:
                    return "semRegisterAodController";
                case 63:
                    return "semUnregisterAodController";
                case 64:
                    return "semGetSensorAreaInDisplay";
                case 65:
                    return "semGetIconBottomMargin";
                case 66:
                    return "semMoveSensorIconInDisplay";
                case 67:
                    return "semGetSecurityLevel";
                case 68:
                    return "semGetTrustAppVersion";
                case 69:
                    return "semUpdateTrustApp";
                case 70:
                    return "semBioSysUiRequest";
                case 71:
                    return "semRegisterDisplayStateCallback";
                case 72:
                    return "semUnregisterDisplayStateCallback";
                case 73:
                    return "semRegisterDisplayBrightnessCallback";
                case 74:
                    return "semUnregisterDisplayBrightnessCallback";
                case 75:
                    return "semGetSensorData";
                case 76:
                    return "semSetFodStrictMode";
                case 77:
                    return "semSetCalibrationMode";
                case 78:
                    return "semProcessFido";
                case 79:
                    return "semGetRemainingLockoutTime";
                case 80:
                    return "semCanChangeDeviceColorMode";
                case 81:
                    return "semSetFlagForIFAA";
                case 82:
                    return "semRequest";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            byte[] _arg0;
            byte[] _arg2;
            byte[] _arg3;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg02 = data.readInt();
                            ITestSessionCallback _arg1 = ITestSessionCallback.Stub.asInterface(data.readStrongBinder());
                            String _arg22 = data.readString();
                            data.enforceNoDataAvail();
                            ITestSession _result = createTestSession(_arg02, _arg1, _arg22);
                            reply.writeNoException();
                            reply.writeStrongInterface(_result);
                            return true;
                        case 2:
                            int _arg03 = data.readInt();
                            boolean _arg12 = data.readBoolean();
                            data.enforceNoDataAvail();
                            byte[] _result2 = dumpSensorServiceStateProto(_arg03, _arg12);
                            reply.writeNoException();
                            reply.writeByteArray(_result2);
                            return true;
                        case 3:
                            String _arg04 = data.readString();
                            data.enforceNoDataAvail();
                            List<FingerprintSensorPropertiesInternal> _result3 = getSensorPropertiesInternal(_arg04);
                            reply.writeNoException();
                            reply.writeTypedList(_result3, 1);
                            return true;
                        case 4:
                            int _arg05 = data.readInt();
                            String _arg13 = data.readString();
                            data.enforceNoDataAvail();
                            FingerprintSensorPropertiesInternal _result4 = getSensorProperties(_arg05, _arg13);
                            reply.writeNoException();
                            reply.writeTypedObject(_result4, 1);
                            return true;
                        case 5:
                            IBinder _arg06 = data.readStrongBinder();
                            long _arg14 = data.readLong();
                            IFingerprintServiceReceiver _arg23 = IFingerprintServiceReceiver.Stub.asInterface(data.readStrongBinder());
                            FingerprintAuthenticateOptions _arg32 = (FingerprintAuthenticateOptions) data.readTypedObject(FingerprintAuthenticateOptions.CREATOR);
                            data.enforceNoDataAvail();
                            long _result5 = authenticate(_arg06, _arg14, _arg23, _arg32);
                            reply.writeNoException();
                            reply.writeLong(_result5);
                            return true;
                        case 6:
                            IBinder _arg07 = data.readStrongBinder();
                            IFingerprintServiceReceiver _arg15 = IFingerprintServiceReceiver.Stub.asInterface(data.readStrongBinder());
                            FingerprintAuthenticateOptions _arg24 = (FingerprintAuthenticateOptions) data.readTypedObject(FingerprintAuthenticateOptions.CREATOR);
                            data.enforceNoDataAvail();
                            long _result6 = detectFingerprint(_arg07, _arg15, _arg24);
                            reply.writeNoException();
                            reply.writeLong(_result6);
                            return true;
                        case 7:
                            IBinder _arg08 = data.readStrongBinder();
                            long _arg16 = data.readLong();
                            IBiometricSensorReceiver _arg25 = IBiometricSensorReceiver.Stub.asInterface(data.readStrongBinder());
                            FingerprintAuthenticateOptions _arg33 = (FingerprintAuthenticateOptions) data.readTypedObject(FingerprintAuthenticateOptions.CREATOR);
                            long _arg4 = data.readLong();
                            int _arg5 = data.readInt();
                            boolean _arg6 = data.readBoolean();
                            data.enforceNoDataAvail();
                            prepareForAuthentication(_arg08, _arg16, _arg25, _arg33, _arg4, _arg5, _arg6);
                            reply.writeNoException();
                            return true;
                        case 8:
                            int _arg09 = data.readInt();
                            int _arg17 = data.readInt();
                            data.enforceNoDataAvail();
                            startPreparedClient(_arg09, _arg17);
                            reply.writeNoException();
                            return true;
                        case 9:
                            IBinder _arg010 = data.readStrongBinder();
                            String _arg18 = data.readString();
                            String _arg26 = data.readString();
                            long _arg34 = data.readLong();
                            data.enforceNoDataAvail();
                            cancelAuthentication(_arg010, _arg18, _arg26, _arg34);
                            reply.writeNoException();
                            return true;
                        case 10:
                            IBinder _arg011 = data.readStrongBinder();
                            String _arg19 = data.readString();
                            long _arg27 = data.readLong();
                            data.enforceNoDataAvail();
                            cancelFingerprintDetect(_arg011, _arg19, _arg27);
                            reply.writeNoException();
                            return true;
                        case 11:
                            int _arg012 = data.readInt();
                            IBinder _arg110 = data.readStrongBinder();
                            String _arg28 = data.readString();
                            long _arg35 = data.readLong();
                            data.enforceNoDataAvail();
                            cancelAuthenticationFromService(_arg012, _arg110, _arg28, _arg35);
                            reply.writeNoException();
                            return true;
                        case 12:
                            IBinder _arg013 = data.readStrongBinder();
                            byte[] _arg111 = data.createByteArray();
                            int _arg29 = data.readInt();
                            IFingerprintServiceReceiver _arg36 = IFingerprintServiceReceiver.Stub.asInterface(data.readStrongBinder());
                            String _arg42 = data.readString();
                            int _arg52 = data.readInt();
                            data.enforceNoDataAvail();
                            long _result7 = enroll(_arg013, _arg111, _arg29, _arg36, _arg42, _arg52);
                            reply.writeNoException();
                            reply.writeLong(_result7);
                            return true;
                        case 13:
                            IBinder _arg014 = data.readStrongBinder();
                            long _arg112 = data.readLong();
                            data.enforceNoDataAvail();
                            cancelEnrollment(_arg014, _arg112);
                            reply.writeNoException();
                            return true;
                        case 14:
                            IBinder _arg015 = data.readStrongBinder();
                            int _arg113 = data.readInt();
                            int _arg210 = data.readInt();
                            IFingerprintServiceReceiver _arg37 = IFingerprintServiceReceiver.Stub.asInterface(data.readStrongBinder());
                            String _arg43 = data.readString();
                            data.enforceNoDataAvail();
                            remove(_arg015, _arg113, _arg210, _arg37, _arg43);
                            reply.writeNoException();
                            return true;
                        case 15:
                            IBinder _arg016 = data.readStrongBinder();
                            int _arg114 = data.readInt();
                            IFingerprintServiceReceiver _arg211 = IFingerprintServiceReceiver.Stub.asInterface(data.readStrongBinder());
                            String _arg38 = data.readString();
                            data.enforceNoDataAvail();
                            removeAll(_arg016, _arg114, _arg211, _arg38);
                            reply.writeNoException();
                            return true;
                        case 16:
                            int _arg017 = data.readInt();
                            int _arg115 = data.readInt();
                            String _arg212 = data.readString();
                            data.enforceNoDataAvail();
                            rename(_arg017, _arg115, _arg212);
                            reply.writeNoException();
                            return true;
                        case 17:
                            int _arg018 = data.readInt();
                            String _arg116 = data.readString();
                            String _arg213 = data.readString();
                            data.enforceNoDataAvail();
                            List<Fingerprint> _result8 = getEnrolledFingerprints(_arg018, _arg116, _arg213);
                            reply.writeNoException();
                            reply.writeTypedList(_result8, 1);
                            return true;
                        case 18:
                            String _arg019 = data.readString();
                            String _arg117 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result9 = isHardwareDetectedDeprecated(_arg019, _arg117);
                            reply.writeNoException();
                            reply.writeBoolean(_result9);
                            return true;
                        case 19:
                            int _arg020 = data.readInt();
                            String _arg118 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result10 = isHardwareDetected(_arg020, _arg118);
                            reply.writeNoException();
                            reply.writeBoolean(_result10);
                            return true;
                        case 20:
                            IBinder _arg021 = data.readStrongBinder();
                            int _arg119 = data.readInt();
                            int _arg214 = data.readInt();
                            IFingerprintServiceReceiver _arg39 = IFingerprintServiceReceiver.Stub.asInterface(data.readStrongBinder());
                            String _arg44 = data.readString();
                            data.enforceNoDataAvail();
                            generateChallenge(_arg021, _arg119, _arg214, _arg39, _arg44);
                            reply.writeNoException();
                            return true;
                        case 21:
                            IBinder _arg022 = data.readStrongBinder();
                            int _arg120 = data.readInt();
                            int _arg215 = data.readInt();
                            String _arg310 = data.readString();
                            long _arg45 = data.readLong();
                            data.enforceNoDataAvail();
                            revokeChallenge(_arg022, _arg120, _arg215, _arg310, _arg45);
                            reply.writeNoException();
                            return true;
                        case 22:
                            int _arg023 = data.readInt();
                            String _arg121 = data.readString();
                            String _arg216 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result11 = hasEnrolledFingerprintsDeprecated(_arg023, _arg121, _arg216);
                            reply.writeNoException();
                            reply.writeBoolean(_result11);
                            return true;
                        case 23:
                            int _arg024 = data.readInt();
                            int _arg122 = data.readInt();
                            String _arg217 = data.readString();
                            data.enforceNoDataAvail();
                            boolean _result12 = hasEnrolledFingerprints(_arg024, _arg122, _arg217);
                            reply.writeNoException();
                            reply.writeBoolean(_result12);
                            return true;
                        case 24:
                            int _arg025 = data.readInt();
                            int _arg123 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result13 = getLockoutModeForUser(_arg025, _arg123);
                            reply.writeNoException();
                            reply.writeInt(_result13);
                            return true;
                        case 25:
                            int _arg026 = data.readInt();
                            int _arg124 = data.readInt();
                            IInvalidationCallback _arg218 = IInvalidationCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            invalidateAuthenticatorId(_arg026, _arg124, _arg218);
                            reply.writeNoException();
                            return true;
                        case 26:
                            int _arg027 = data.readInt();
                            int _arg125 = data.readInt();
                            data.enforceNoDataAvail();
                            long _result14 = getAuthenticatorId(_arg027, _arg125);
                            reply.writeNoException();
                            reply.writeLong(_result14);
                            return true;
                        case 27:
                            IBinder _arg028 = data.readStrongBinder();
                            int _arg126 = data.readInt();
                            int _arg219 = data.readInt();
                            byte[] _arg311 = data.createByteArray();
                            String _arg46 = data.readString();
                            data.enforceNoDataAvail();
                            resetLockout(_arg028, _arg126, _arg219, _arg311, _arg46);
                            reply.writeNoException();
                            return true;
                        case 28:
                            IBiometricServiceLockoutResetCallback _arg029 = IBiometricServiceLockoutResetCallback.Stub.asInterface(data.readStrongBinder());
                            String _arg127 = data.readString();
                            data.enforceNoDataAvail();
                            addLockoutResetCallback(_arg029, _arg127);
                            reply.writeNoException();
                            return true;
                        case 29:
                            boolean _result15 = isClientActive();
                            reply.writeNoException();
                            reply.writeBoolean(_result15);
                            return true;
                        case 30:
                            IFingerprintClientActiveCallback _arg030 = IFingerprintClientActiveCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            addClientActiveCallback(_arg030);
                            reply.writeNoException();
                            return true;
                        case 31:
                            IFingerprintClientActiveCallback _arg031 = IFingerprintClientActiveCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            removeClientActiveCallback(_arg031);
                            reply.writeNoException();
                            return true;
                        case 32:
                            List<FingerprintSensorPropertiesInternal> _arg032 = data.createTypedArrayList(FingerprintSensorPropertiesInternal.CREATOR);
                            data.enforceNoDataAvail();
                            registerAuthenticators(_arg032);
                            reply.writeNoException();
                            return true;
                        case 33:
                            IFingerprintAuthenticatorsRegisteredCallback _arg033 = IFingerprintAuthenticatorsRegisteredCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            addAuthenticatorsRegisteredCallback(_arg033);
                            reply.writeNoException();
                            return true;
                        case 34:
                            long _arg034 = data.readLong();
                            int _arg128 = data.readInt();
                            PointerContext _arg220 = (PointerContext) data.readTypedObject(PointerContext.CREATOR);
                            data.enforceNoDataAvail();
                            onPointerDown(_arg034, _arg128, _arg220);
                            reply.writeNoException();
                            return true;
                        case 35:
                            long _arg035 = data.readLong();
                            int _arg129 = data.readInt();
                            PointerContext _arg221 = (PointerContext) data.readTypedObject(PointerContext.CREATOR);
                            data.enforceNoDataAvail();
                            onPointerUp(_arg035, _arg129, _arg221);
                            reply.writeNoException();
                            return true;
                        case 36:
                            long _arg036 = data.readLong();
                            int _arg130 = data.readInt();
                            data.enforceNoDataAvail();
                            onUiReady(_arg036, _arg130);
                            reply.writeNoException();
                            return true;
                        case 37:
                            IUdfpsOverlayController _arg037 = IUdfpsOverlayController.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            setUdfpsOverlayController(_arg037);
                            reply.writeNoException();
                            return true;
                        case 38:
                            ISidefpsController _arg038 = ISidefpsController.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            setSidefpsController(_arg038);
                            reply.writeNoException();
                            return true;
                        case 39:
                            IUdfpsOverlay _arg039 = IUdfpsOverlay.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            setUdfpsOverlay(_arg039);
                            reply.writeNoException();
                            return true;
                        case 40:
                            IBiometricStateListener _arg040 = IBiometricStateListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            registerBiometricStateListener(_arg040);
                            reply.writeNoException();
                            return true;
                        case 41:
                            onPowerPressed();
                            return true;
                        case 42:
                            scheduleWatchdog();
                            reply.writeNoException();
                            return true;
                        case 43:
                            IBinder _arg041 = data.readStrongBinder();
                            long _arg131 = data.readLong();
                            IFingerprintServiceReceiver _arg222 = IFingerprintServiceReceiver.Stub.asInterface(data.readStrongBinder());
                            FingerprintAuthenticateOptions _arg312 = (FingerprintAuthenticateOptions) data.readTypedObject(FingerprintAuthenticateOptions.CREATOR);
                            Bundle _arg47 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            data.enforceNoDataAvail();
                            long _result16 = semAuthenticate(_arg041, _arg131, _arg222, _arg312, _arg47);
                            reply.writeNoException();
                            reply.writeLong(_result16);
                            return true;
                        case 44:
                            int _result17 = semGetMaxEnrollmentNumber();
                            reply.writeNoException();
                            reply.writeInt(_result17);
                            return true;
                        case 45:
                            int _arg042 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result18 = semHasFeature(_arg042);
                            reply.writeNoException();
                            reply.writeBoolean(_result18);
                            return true;
                        case 46:
                            semForceCBGE();
                            reply.writeNoException();
                            return true;
                        case 47:
                            boolean _result19 = semIsEnrollSession();
                            reply.writeNoException();
                            reply.writeBoolean(_result19);
                            return true;
                        case 48:
                            boolean _result20 = semIsTemplateDbCorrupted();
                            reply.writeNoException();
                            reply.writeBoolean(_result20);
                            return true;
                        case 49:
                            int _result21 = semGetSensorStatus();
                            reply.writeNoException();
                            reply.writeInt(_result21);
                            return true;
                        case 50:
                            boolean _result22 = semPauseEnroll();
                            reply.writeNoException();
                            reply.writeBoolean(_result22);
                            return true;
                        case 51:
                            boolean _result23 = semResumeEnroll();
                            reply.writeNoException();
                            reply.writeBoolean(_result23);
                            return true;
                        case 52:
                            boolean _result24 = semOpenSession();
                            reply.writeNoException();
                            reply.writeBoolean(_result24);
                            return true;
                        case 53:
                            String _result25 = semGetSensorInfo();
                            reply.writeNoException();
                            reply.writeString(_result25);
                            return true;
                        case 54:
                            String[] _result26 = semGetUserIdList();
                            reply.writeNoException();
                            reply.writeStringArray(_result26);
                            return true;
                        case 55:
                            String _result27 = semGetDaemonVersion();
                            reply.writeNoException();
                            reply.writeString(_result27);
                            return true;
                        case 56:
                            IBinder _arg043 = data.readStrongBinder();
                            int _arg132 = data.readInt();
                            int _arg223 = data.readInt();
                            ISemFingerprintRequestCallback _arg313 = ISemFingerprintRequestCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            int _result28 = semRunSensorTest(_arg043, _arg132, _arg223, _arg313);
                            reply.writeNoException();
                            reply.writeInt(_result28);
                            return true;
                        case 57:
                            int _arg0_length = data.readInt();
                            if (_arg0_length < 0) {
                                _arg0 = null;
                            } else {
                                _arg0 = new byte[_arg0_length];
                            }
                            data.enforceNoDataAvail();
                            int _result29 = semGetSensorTestResult(_arg0);
                            reply.writeNoException();
                            reply.writeInt(_result29);
                            reply.writeByteArray(_arg0);
                            return true;
                        case 58:
                            int _arg044 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result30 = semSetScreenStatus(_arg044);
                            reply.writeNoException();
                            reply.writeInt(_result30);
                            return true;
                        case 59:
                            int _arg045 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result31 = semShowBouncerScreen(_arg045);
                            reply.writeNoException();
                            reply.writeInt(_result31);
                            return true;
                        case 60:
                            IBinder _arg046 = data.readStrongBinder();
                            String _arg133 = data.readString();
                            data.enforceNoDataAvail();
                            IBinder _result32 = semAddMaskView(_arg046, _arg133);
                            reply.writeNoException();
                            reply.writeStrongBinder(_result32);
                            return true;
                        case 61:
                            IBinder _arg047 = data.readStrongBinder();
                            String _arg134 = data.readString();
                            data.enforceNoDataAvail();
                            int _result33 = semRemoveMaskView(_arg047, _arg134);
                            reply.writeNoException();
                            reply.writeInt(_result33);
                            return true;
                        case 62:
                            IBinder _arg048 = data.readStrongBinder();
                            ISemFingerprintAodController _arg135 = ISemFingerprintAodController.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            semRegisterAodController(_arg048, _arg135);
                            reply.writeNoException();
                            return true;
                        case 63:
                            IBinder _arg049 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            semUnregisterAodController(_arg049);
                            reply.writeNoException();
                            return true;
                        case 64:
                            int _arg050 = data.readInt();
                            int _arg136 = data.readInt();
                            Point _arg224 = (Point) data.readTypedObject(Point.CREATOR);
                            data.enforceNoDataAvail();
                            Rect _result34 = semGetSensorAreaInDisplay(_arg050, _arg136, _arg224);
                            reply.writeNoException();
                            reply.writeTypedObject(_result34, 1);
                            return true;
                        case 65:
                            int _result35 = semGetIconBottomMargin();
                            reply.writeNoException();
                            reply.writeInt(_result35);
                            return true;
                        case 66:
                            int _arg051 = data.readInt();
                            int _arg137 = data.readInt();
                            data.enforceNoDataAvail();
                            semMoveSensorIconInDisplay(_arg051, _arg137);
                            reply.writeNoException();
                            return true;
                        case 67:
                            int _result36 = semGetSecurityLevel();
                            reply.writeNoException();
                            reply.writeInt(_result36);
                            return true;
                        case 68:
                            String _result37 = semGetTrustAppVersion();
                            reply.writeNoException();
                            reply.writeString(_result37);
                            return true;
                        case 69:
                            String _arg052 = data.readString();
                            ISemFingerprintRequestCallback _arg138 = ISemFingerprintRequestCallback.Stub.asInterface(data.readStrongBinder());
                            String _arg225 = data.readString();
                            data.enforceNoDataAvail();
                            semUpdateTrustApp(_arg052, _arg138, _arg225);
                            reply.writeNoException();
                            return true;
                        case 70:
                            int _arg053 = data.readInt();
                            int _arg139 = data.readInt();
                            long _arg226 = data.readLong();
                            String _arg314 = data.readString();
                            data.enforceNoDataAvail();
                            int _result38 = semBioSysUiRequest(_arg053, _arg139, _arg226, _arg314);
                            reply.writeNoException();
                            reply.writeInt(_result38);
                            return true;
                        case 71:
                            ISemBiometricSysUiDisplayStateCallback _arg054 = ISemBiometricSysUiDisplayStateCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            int _result39 = semRegisterDisplayStateCallback(_arg054);
                            reply.writeNoException();
                            reply.writeInt(_result39);
                            return true;
                        case 72:
                            semUnregisterDisplayStateCallback();
                            reply.writeNoException();
                            return true;
                        case 73:
                            ISemBiometricSysUiDisplayBrightnessCallback _arg055 = ISemBiometricSysUiDisplayBrightnessCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            int _result40 = semRegisterDisplayBrightnessCallback(_arg055);
                            reply.writeNoException();
                            reply.writeInt(_result40);
                            return true;
                        case 74:
                            semUnregisterDisplayBrightnessCallback();
                            reply.writeNoException();
                            return true;
                        case 75:
                            Bundle _arg056 = new Bundle();
                            data.enforceNoDataAvail();
                            semGetSensorData(_arg056);
                            reply.writeNoException();
                            reply.writeTypedObject(_arg056, 1);
                            return true;
                        case 76:
                            boolean _arg057 = data.readBoolean();
                            data.enforceNoDataAvail();
                            semSetFodStrictMode(_arg057);
                            reply.writeNoException();
                            return true;
                        case 77:
                            IBinder _arg058 = data.readStrongBinder();
                            int _arg140 = data.readInt();
                            String _arg227 = data.readString();
                            data.enforceNoDataAvail();
                            int _result41 = semSetCalibrationMode(_arg058, _arg140, _arg227);
                            reply.writeNoException();
                            reply.writeInt(_result41);
                            return true;
                        case 78:
                            int _arg059 = data.readInt();
                            byte[] _arg141 = data.createByteArray();
                            int _arg2_length = data.readInt();
                            if (_arg2_length < 0) {
                                _arg2 = null;
                            } else {
                                _arg2 = new byte[_arg2_length];
                            }
                            String _arg315 = data.readString();
                            data.enforceNoDataAvail();
                            int _result42 = semProcessFido(_arg059, _arg141, _arg2, _arg315);
                            reply.writeNoException();
                            reply.writeInt(_result42);
                            reply.writeByteArray(_arg2);
                            return true;
                        case 79:
                            int _arg060 = data.readInt();
                            data.enforceNoDataAvail();
                            int _result43 = semGetRemainingLockoutTime(_arg060);
                            reply.writeNoException();
                            reply.writeInt(_result43);
                            return true;
                        case 80:
                            boolean _result44 = semCanChangeDeviceColorMode();
                            reply.writeNoException();
                            reply.writeBoolean(_result44);
                            return true;
                        case 81:
                            int _arg061 = data.readInt();
                            String _arg142 = data.readString();
                            data.enforceNoDataAvail();
                            semSetFlagForIFAA(_arg061, _arg142);
                            reply.writeNoException();
                            return true;
                        case 82:
                            IBinder _arg062 = data.readStrongBinder();
                            int _arg143 = data.readInt();
                            byte[] _arg228 = data.createByteArray();
                            int _arg3_length = data.readInt();
                            if (_arg3_length < 0) {
                                _arg3 = null;
                            } else {
                                byte[] _arg316 = new byte[_arg3_length];
                                _arg3 = _arg316;
                            }
                            int _arg48 = data.readInt();
                            int _arg53 = data.readInt();
                            String _arg62 = data.readString();
                            ISemFingerprintRequestCallback _arg7 = ISemFingerprintRequestCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            int _result45 = semRequest(_arg062, _arg143, _arg228, _arg3, _arg48, _arg53, _arg62, _arg7);
                            reply.writeNoException();
                            reply.writeInt(_result45);
                            reply.writeByteArray(_arg3);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IFingerprintService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public ITestSession createTestSession(int sensorId, ITestSessionCallback callback, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorId);
                    _data.writeStrongInterface(callback);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ITestSession _result = ITestSession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public byte[] dumpSensorServiceStateProto(int sensorId, boolean clearSchedulerBuffer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorId);
                    _data.writeBoolean(clearSchedulerBuffer);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public List<FingerprintSensorPropertiesInternal> getSensorPropertiesInternal(String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    List<FingerprintSensorPropertiesInternal> _result = _reply.createTypedArrayList(FingerprintSensorPropertiesInternal.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public FingerprintSensorPropertiesInternal getSensorProperties(int sensorId, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorId);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    FingerprintSensorPropertiesInternal _result = (FingerprintSensorPropertiesInternal) _reply.readTypedObject(FingerprintSensorPropertiesInternal.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long authenticate(IBinder token, long operationId, IFingerprintServiceReceiver receiver, FingerprintAuthenticateOptions options) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeLong(operationId);
                    _data.writeStrongInterface(receiver);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long detectFingerprint(IBinder token, IFingerprintServiceReceiver receiver, FingerprintAuthenticateOptions options) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeStrongInterface(receiver);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void prepareForAuthentication(IBinder token, long operationId, IBiometricSensorReceiver sensorReceiver, FingerprintAuthenticateOptions options, long requestId, int cookie, boolean allowBackgroundAuthentication) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeLong(operationId);
                    _data.writeStrongInterface(sensorReceiver);
                    _data.writeTypedObject(options, 0);
                    _data.writeLong(requestId);
                    _data.writeInt(cookie);
                    _data.writeBoolean(allowBackgroundAuthentication);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void startPreparedClient(int sensorId, int cookie) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorId);
                    _data.writeInt(cookie);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelAuthentication(IBinder token, String opPackageName, String attributionTag, long requestId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(opPackageName);
                    _data.writeString(attributionTag);
                    _data.writeLong(requestId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelFingerprintDetect(IBinder token, String opPackageName, long requestId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(opPackageName);
                    _data.writeLong(requestId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelAuthenticationFromService(int sensorId, IBinder token, String opPackageName, long requestId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorId);
                    _data.writeStrongBinder(token);
                    _data.writeString(opPackageName);
                    _data.writeLong(requestId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long enroll(IBinder token, byte[] hardwareAuthToken, int userId, IFingerprintServiceReceiver receiver, String opPackageName, int enrollReason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeByteArray(hardwareAuthToken);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(receiver);
                    _data.writeString(opPackageName);
                    _data.writeInt(enrollReason);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelEnrollment(IBinder token, long requestId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeLong(requestId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void remove(IBinder token, int fingerId, int userId, IFingerprintServiceReceiver receiver, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(fingerId);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(receiver);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void removeAll(IBinder token, int userId, IFingerprintServiceReceiver receiver, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(receiver);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void rename(int fingerId, int userId, String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(fingerId);
                    _data.writeInt(userId);
                    _data.writeString(name);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public List<Fingerprint> getEnrolledFingerprints(int userId, String opPackageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    List<Fingerprint> _result = _reply.createTypedArrayList(Fingerprint.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean isHardwareDetectedDeprecated(String opPackageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(opPackageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean isHardwareDetected(int sensorId, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorId);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void generateChallenge(IBinder token, int sensorId, int userId, IFingerprintServiceReceiver receiver, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(sensorId);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(receiver);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void revokeChallenge(IBinder token, int sensorId, int userId, String opPackageName, long challenge) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(sensorId);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    _data.writeLong(challenge);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean hasEnrolledFingerprintsDeprecated(int userId, String opPackageName, String attributionTag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    _data.writeString(attributionTag);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean hasEnrolledFingerprints(int sensorId, int userId, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorId);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int getLockoutModeForUser(int sensorId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorId);
                    _data.writeInt(userId);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void invalidateAuthenticatorId(int sensorId, int userId, IInvalidationCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorId);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long getAuthenticatorId(int sensorId, int callingUserId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sensorId);
                    _data.writeInt(callingUserId);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void resetLockout(IBinder token, int sensorId, int userId, byte[] hardwareAuthToken, String opPackageNAame) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(sensorId);
                    _data.writeInt(userId);
                    _data.writeByteArray(hardwareAuthToken);
                    _data.writeString(opPackageNAame);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void addLockoutResetCallback(IBiometricServiceLockoutResetCallback callback, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean isClientActive() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void addClientActiveCallback(IFingerprintClientActiveCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void removeClientActiveCallback(IFingerprintClientActiveCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void registerAuthenticators(List<FingerprintSensorPropertiesInternal> hidlSensors) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(hidlSensors, 0);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void addAuthenticatorsRegisteredCallback(IFingerprintAuthenticatorsRegisteredCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPointerDown(long requestId, int sensorId, PointerContext pc) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(requestId);
                    _data.writeInt(sensorId);
                    _data.writeTypedObject(pc, 0);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPointerUp(long requestId, int sensorId, PointerContext pc) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(requestId);
                    _data.writeInt(sensorId);
                    _data.writeTypedObject(pc, 0);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onUiReady(long requestId, int sensorId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(requestId);
                    _data.writeInt(sensorId);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void setUdfpsOverlayController(IUdfpsOverlayController controller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(controller);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void setSidefpsController(ISidefpsController controller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(controller);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void setUdfpsOverlay(IUdfpsOverlay controller) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(controller);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void registerBiometricStateListener(IBiometricStateListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPowerPressed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(41, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void scheduleWatchdog() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long semAuthenticate(IBinder token, long operationId, IFingerprintServiceReceiver receiver, FingerprintAuthenticateOptions options, Bundle bundle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeLong(operationId);
                    _data.writeStrongInterface(receiver);
                    _data.writeTypedObject(options, 0);
                    _data.writeTypedObject(bundle, 0);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetMaxEnrollmentNumber() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semHasFeature(int feature) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(feature);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semForceCBGE() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semIsEnrollSession() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semIsTemplateDbCorrupted() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetSensorStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semPauseEnroll() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semResumeEnroll() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semOpenSession() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public String semGetSensorInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public String[] semGetUserIdList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public String semGetDaemonVersion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRunSensorTest(IBinder token, int cmd, int param, ISemFingerprintRequestCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(cmd);
                    _data.writeInt(param);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetSensorTestResult(byte[] outBuffer) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(outBuffer.length);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(outBuffer);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semSetScreenStatus(int screenStatus) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(screenStatus);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semShowBouncerScreen(int showStatus) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(showStatus);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public IBinder semAddMaskView(IBinder token, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRemoveMaskView(IBinder token, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semRegisterAodController(IBinder token, ISemFingerprintAodController aodController) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeStrongInterface(aodController);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semUnregisterAodController(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public Rect semGetSensorAreaInDisplay(int type, int rotation, Point point) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(rotation);
                    _data.writeTypedObject(point, 0);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                    Rect _result = (Rect) _reply.readTypedObject(Rect.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetIconBottomMargin() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semMoveSensorIconInDisplay(int x, int y) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetSecurityLevel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public String semGetTrustAppVersion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semUpdateTrustApp(String path, ISemFingerprintRequestCallback receiver, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeStrongInterface(receiver);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semBioSysUiRequest(int cmd, int arg1, long arg2, String arg3) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cmd);
                    _data.writeInt(arg1);
                    _data.writeLong(arg2);
                    _data.writeString(arg3);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRegisterDisplayStateCallback(ISemBiometricSysUiDisplayStateCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semUnregisterDisplayStateCallback() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRegisterDisplayBrightnessCallback(ISemBiometricSysUiDisplayBrightnessCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semUnregisterDisplayBrightnessCallback() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semGetSensorData(Bundle bundle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        bundle.readFromParcel(_reply);
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semSetFodStrictMode(boolean isStrictMode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(isStrictMode);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semSetCalibrationMode(IBinder token, int param, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(param);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semProcessFido(int userId, byte[] inBuf, byte[] outBuf, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeByteArray(inBuf);
                    _data.writeInt(outBuf.length);
                    _data.writeString(opPackageName);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(outBuf);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetRemainingLockoutTime(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semCanChangeDeviceColorMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semSetFlagForIFAA(int flag, String targetAppPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flag);
                    _data.writeString(targetAppPackageName);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRequest(IBinder token, int cmd, byte[] inputBuf, byte[] outputBuf, int inParam, int groupId, String opPackageName, ISemFingerprintRequestCallback receiver) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(cmd);
                    _data.writeByteArray(inputBuf);
                    _data.writeInt(outputBuf.length);
                    _data.writeInt(inParam);
                    _data.writeInt(groupId);
                    _data.writeString(opPackageName);
                    _data.writeStrongInterface(receiver);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(outputBuf);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void createTestSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void dumpSensorServiceStateProto_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getSensorProperties_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void detectFingerprint_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void prepareForAuthentication_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void startPreparedClient_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void cancelFingerprintDetect_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void cancelAuthenticationFromService_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_BIOMETRIC, getCallingPid(), getCallingUid());
        }

        protected void enroll_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void cancelEnrollment_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void remove_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void removeAll_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void rename_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void isHardwareDetected_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void generateChallenge_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void revokeChallenge_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void hasEnrolledFingerprints_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getLockoutModeForUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void invalidateAuthenticatorId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void getAuthenticatorId_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void resetLockout_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.RESET_FINGERPRINT_LOCKOUT, getCallingPid(), getCallingUid());
        }

        protected void addLockoutResetCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void isClientActive_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void addClientActiveCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void removeClientActiveCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void registerAuthenticators_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void addAuthenticatorsRegisteredCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void onPointerDown_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void onPointerUp_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void onUiReady_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void setUdfpsOverlayController_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void setSidefpsController_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void setUdfpsOverlay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerBiometricStateListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void onPowerPressed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void scheduleWatchdog_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void semForceCBGE_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semIsEnrollSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semIsTemplateDbCorrupted_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semPauseEnroll_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semResumeEnroll_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semOpenSession_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semGetSensorInfo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semGetUserIdList_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semGetDaemonVersion_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semRunSensorTest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semGetSensorTestResult_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semSetScreenStatus_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semShowBouncerScreen_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semAddMaskView_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semRemoveMaskView_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semRegisterAodController_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semUnregisterAodController_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semMoveSensorIconInDisplay_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semGetSecurityLevel_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semGetTrustAppVersion_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semUpdateTrustApp_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semBioSysUiRequest_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semRegisterDisplayStateCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semUnregisterDisplayStateCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semRegisterDisplayBrightnessCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semUnregisterDisplayBrightnessCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semGetSensorData_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semSetFodStrictMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semSetCalibrationMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semGetRemainingLockoutTime_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semCanChangeDeviceColorMode_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        protected void semSetFlagForIFAA_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_FINGERPRINT, getCallingPid(), getCallingUid());
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 81;
        }
    }
}
