package android.hardware.fingerprint;

import android.Manifest;
import android.app.ActivityThread;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.biometrics.AuthenticationStateListener;
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
import android.hardware.fingerprint.IUdfpsOverlayController;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import com.samsung.android.bio.fingerprint.ISemFingerprintAodController;
import com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback;
import com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback;
import java.util.List;

/* loaded from: classes2.dex */
public interface IFingerprintService extends IInterface {

    public static class Default implements IFingerprintService {
        @Override // android.hardware.fingerprint.IFingerprintService
        public void addAuthenticatorsRegisteredCallback(IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallback) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void addClientActiveCallback(IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void addLockoutResetCallback(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long authenticate(IBinder iBinder, long j, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void cancelAuthentication(IBinder iBinder, String str, String str2, long j) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void cancelAuthenticationFromService(int i, IBinder iBinder, String str, long j) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void cancelEnrollment(IBinder iBinder, long j) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void cancelFingerprintDetect(IBinder iBinder, String str, long j) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long detectFingerprint(IBinder iBinder, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public byte[] dumpSensorServiceStateProto(int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long enroll(IBinder iBinder, byte[] bArr, int i, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str, int i2, FingerprintEnrollOptions fingerprintEnrollOptions) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void generateChallenge(IBinder iBinder, int i, int i2, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long getAuthenticatorId(int i, int i2) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public List<Fingerprint> getEnrolledFingerprints(int i, String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int getLockoutModeForUser(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public FingerprintSensorPropertiesInternal getSensorProperties(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public List<FingerprintSensorPropertiesInternal> getSensorPropertiesInternal(String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean hasEnrolledFingerprints(int i, int i2, String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean hasEnrolledFingerprintsDeprecated(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void invalidateAuthenticatorId(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean isClientActive() throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean isHardwareDetected(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean isHardwareDetectedDeprecated(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void onPointerDown(long j, int i, PointerContext pointerContext) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void onPointerUp(long j, int i, PointerContext pointerContext) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void onPowerPressed() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void onPowerSinglePressed() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void onUdfpsUiEvent(int i, long j, int i2) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void prepareForAuthentication(IBinder iBinder, long j, IBiometricSensorReceiver iBiometricSensorReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, long j2, int i, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void registerAuthenticators(FingerprintSensorConfigurations fingerprintSensorConfigurations) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void registerBiometricStateListener(IBiometricStateListener iBiometricStateListener) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void remove(IBinder iBinder, int i, int i2, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void removeAll(IBinder iBinder, int i, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void removeClientActiveCallback(IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void rename(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void resetLockout(IBinder iBinder, int i, int i2, byte[] bArr, String str) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void revokeChallenge(IBinder iBinder, int i, int i2, String str, long j) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void scheduleWatchdog() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public IBinder semAddMaskView(IBinder iBinder, String str) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public long semAuthenticate(IBinder iBinder, long j, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, Bundle bundle) throws RemoteException {
            return 0L;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semBioSysUiRequest(int i, int i2, long j, String str) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean semCanChangeDeviceColorMode() throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semForceCBGE() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public String semGetDaemonVersion() throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semGetIconBottomMargin() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semGetMaxEnrollmentNumber() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semGetRemainingLockoutTime(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semGetSecurityLevel() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public Rect semGetSensorAreaInDisplay(int i, int i2, Point point) throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semGetSensorData(Bundle bundle) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public String semGetSensorInfo() throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semGetSensorStatus() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semGetSensorTestResult(byte[] bArr) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public String semGetTrustAppVersion() throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public String[] semGetUserIdList() throws RemoteException {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean semHasFeature(int i) throws RemoteException {
            return false;
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
        public void semMoveSensorIconInDisplay(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean semOpenSession() throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean semPauseEnroll() throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semProcessFido(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semRegisterAodController(IBinder iBinder, ISemFingerprintAodController iSemFingerprintAodController) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semRegisterDisplayBrightnessCallback(ISemBiometricSysUiDisplayBrightnessCallback iSemBiometricSysUiDisplayBrightnessCallback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semRegisterDisplayStateCallback(ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semRemoveMaskView(IBinder iBinder, String str) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semRequest(IBinder iBinder, int i, byte[] bArr, byte[] bArr2, int i2, int i3, String str, ISemFingerprintRequestCallback iSemFingerprintRequestCallback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public boolean semResumeEnroll() throws RemoteException {
            return false;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semRunSensorTest(IBinder iBinder, int i, int i2, ISemFingerprintRequestCallback iSemFingerprintRequestCallback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semSetCalibrationMode(IBinder iBinder, int i, String str) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semSetFlagForIFAA(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semSetFodStrictMode(boolean z) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semSetScreenStatus(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public int semShowBouncerScreen(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semShowUdfpsIcon() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semUnregisterAodController(IBinder iBinder) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semUnregisterDisplayBrightnessCallback() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semUnregisterDisplayStateCallback() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void semUpdateTrustApp(String str, ISemFingerprintRequestCallback iSemFingerprintRequestCallback, String str2) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void setIgnoreDisplayTouches(long j, int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void setUdfpsOverlayController(IUdfpsOverlayController iUdfpsOverlayController) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void startPreparedClient(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintService
        public void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
        }
    }

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

    long enroll(IBinder iBinder, byte[] bArr, int i, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str, int i2, FingerprintEnrollOptions fingerprintEnrollOptions) throws RemoteException;

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

    void onPowerSinglePressed() throws RemoteException;

    void onUdfpsUiEvent(int i, long j, int i2) throws RemoteException;

    void prepareForAuthentication(IBinder iBinder, long j, IBiometricSensorReceiver iBiometricSensorReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, long j2, int i, boolean z, boolean z2) throws RemoteException;

    void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException;

    void registerAuthenticators(FingerprintSensorConfigurations fingerprintSensorConfigurations) throws RemoteException;

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

    void semShowUdfpsIcon() throws RemoteException;

    void semUnregisterAodController(IBinder iBinder) throws RemoteException;

    void semUnregisterDisplayBrightnessCallback() throws RemoteException;

    void semUnregisterDisplayStateCallback() throws RemoteException;

    void semUpdateTrustApp(String str, ISemFingerprintRequestCallback iSemFingerprintRequestCallback, String str2) throws RemoteException;

    void setIgnoreDisplayTouches(long j, int i, boolean z) throws RemoteException;

    void setUdfpsOverlayController(IUdfpsOverlayController iUdfpsOverlayController) throws RemoteException;

    void startPreparedClient(int i, int i2) throws RemoteException;

    void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException;

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
        static final int TRANSACTION_onPowerPressed = 42;
        static final int TRANSACTION_onPowerSinglePressed = 85;
        static final int TRANSACTION_onUdfpsUiEvent = 36;
        static final int TRANSACTION_prepareForAuthentication = 7;
        static final int TRANSACTION_registerAuthenticationStateListener = 39;
        static final int TRANSACTION_registerAuthenticators = 32;
        static final int TRANSACTION_registerBiometricStateListener = 41;
        static final int TRANSACTION_remove = 14;
        static final int TRANSACTION_removeAll = 15;
        static final int TRANSACTION_removeClientActiveCallback = 31;
        static final int TRANSACTION_rename = 16;
        static final int TRANSACTION_resetLockout = 27;
        static final int TRANSACTION_revokeChallenge = 21;
        static final int TRANSACTION_scheduleWatchdog = 43;
        static final int TRANSACTION_semAddMaskView = 61;
        static final int TRANSACTION_semAuthenticate = 44;
        static final int TRANSACTION_semBioSysUiRequest = 72;
        static final int TRANSACTION_semCanChangeDeviceColorMode = 82;
        static final int TRANSACTION_semForceCBGE = 47;
        static final int TRANSACTION_semGetDaemonVersion = 56;
        static final int TRANSACTION_semGetIconBottomMargin = 67;
        static final int TRANSACTION_semGetMaxEnrollmentNumber = 45;
        static final int TRANSACTION_semGetRemainingLockoutTime = 81;
        static final int TRANSACTION_semGetSecurityLevel = 69;
        static final int TRANSACTION_semGetSensorAreaInDisplay = 65;
        static final int TRANSACTION_semGetSensorData = 77;
        static final int TRANSACTION_semGetSensorInfo = 54;
        static final int TRANSACTION_semGetSensorStatus = 50;
        static final int TRANSACTION_semGetSensorTestResult = 58;
        static final int TRANSACTION_semGetTrustAppVersion = 70;
        static final int TRANSACTION_semGetUserIdList = 55;
        static final int TRANSACTION_semHasFeature = 46;
        static final int TRANSACTION_semIsEnrollSession = 48;
        static final int TRANSACTION_semIsTemplateDbCorrupted = 49;
        static final int TRANSACTION_semMoveSensorIconInDisplay = 68;
        static final int TRANSACTION_semOpenSession = 53;
        static final int TRANSACTION_semPauseEnroll = 51;
        static final int TRANSACTION_semProcessFido = 80;
        static final int TRANSACTION_semRegisterAodController = 63;
        static final int TRANSACTION_semRegisterDisplayBrightnessCallback = 75;
        static final int TRANSACTION_semRegisterDisplayStateCallback = 73;
        static final int TRANSACTION_semRemoveMaskView = 62;
        static final int TRANSACTION_semRequest = 84;
        static final int TRANSACTION_semResumeEnroll = 52;
        static final int TRANSACTION_semRunSensorTest = 57;
        static final int TRANSACTION_semSetCalibrationMode = 79;
        static final int TRANSACTION_semSetFlagForIFAA = 83;
        static final int TRANSACTION_semSetFodStrictMode = 78;
        static final int TRANSACTION_semSetScreenStatus = 59;
        static final int TRANSACTION_semShowBouncerScreen = 60;
        static final int TRANSACTION_semShowUdfpsIcon = 66;
        static final int TRANSACTION_semUnregisterAodController = 64;
        static final int TRANSACTION_semUnregisterDisplayBrightnessCallback = 76;
        static final int TRANSACTION_semUnregisterDisplayStateCallback = 74;
        static final int TRANSACTION_semUpdateTrustApp = 71;
        static final int TRANSACTION_setIgnoreDisplayTouches = 37;
        static final int TRANSACTION_setUdfpsOverlayController = 38;
        static final int TRANSACTION_startPreparedClient = 8;
        static final int TRANSACTION_unregisterAuthenticationStateListener = 40;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 84;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IFingerprintService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFingerprintService)) {
                return (IFingerprintService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "onUdfpsUiEvent";
                case 37:
                    return "setIgnoreDisplayTouches";
                case 38:
                    return "setUdfpsOverlayController";
                case 39:
                    return "registerAuthenticationStateListener";
                case 40:
                    return "unregisterAuthenticationStateListener";
                case 41:
                    return "registerBiometricStateListener";
                case 42:
                    return "onPowerPressed";
                case 43:
                    return "scheduleWatchdog";
                case 44:
                    return "semAuthenticate";
                case 45:
                    return "semGetMaxEnrollmentNumber";
                case 46:
                    return "semHasFeature";
                case 47:
                    return "semForceCBGE";
                case 48:
                    return "semIsEnrollSession";
                case 49:
                    return "semIsTemplateDbCorrupted";
                case 50:
                    return "semGetSensorStatus";
                case 51:
                    return "semPauseEnroll";
                case 52:
                    return "semResumeEnroll";
                case 53:
                    return "semOpenSession";
                case 54:
                    return "semGetSensorInfo";
                case 55:
                    return "semGetUserIdList";
                case 56:
                    return "semGetDaemonVersion";
                case 57:
                    return "semRunSensorTest";
                case 58:
                    return "semGetSensorTestResult";
                case 59:
                    return "semSetScreenStatus";
                case 60:
                    return "semShowBouncerScreen";
                case 61:
                    return "semAddMaskView";
                case 62:
                    return "semRemoveMaskView";
                case 63:
                    return "semRegisterAodController";
                case 64:
                    return "semUnregisterAodController";
                case 65:
                    return "semGetSensorAreaInDisplay";
                case 66:
                    return "semShowUdfpsIcon";
                case 67:
                    return "semGetIconBottomMargin";
                case 68:
                    return "semMoveSensorIconInDisplay";
                case 69:
                    return "semGetSecurityLevel";
                case 70:
                    return "semGetTrustAppVersion";
                case 71:
                    return "semUpdateTrustApp";
                case 72:
                    return "semBioSysUiRequest";
                case 73:
                    return "semRegisterDisplayStateCallback";
                case 74:
                    return "semUnregisterDisplayStateCallback";
                case 75:
                    return "semRegisterDisplayBrightnessCallback";
                case 76:
                    return "semUnregisterDisplayBrightnessCallback";
                case 77:
                    return "semGetSensorData";
                case 78:
                    return "semSetFodStrictMode";
                case 79:
                    return "semSetCalibrationMode";
                case 80:
                    return "semProcessFido";
                case 81:
                    return "semGetRemainingLockoutTime";
                case 82:
                    return "semCanChangeDeviceColorMode";
                case 83:
                    return "semSetFlagForIFAA";
                case 84:
                    return "semRequest";
                case 85:
                    return "onPowerSinglePressed";
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
                    ITestSessionCallback iTestSessionCallbackAsInterface = ITestSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ITestSession iTestSessionCreateTestSession = createTestSession(i3, iTestSessionCallbackAsInterface, string);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iTestSessionCreateTestSession);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] bArrDumpSensorServiceStateProto = dumpSensorServiceStateProto(i4, z);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrDumpSensorServiceStateProto);
                    return true;
                case 3:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<FingerprintSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal(string2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(sensorPropertiesInternal, 1);
                    return true;
                case 4:
                    int i5 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    FingerprintSensorPropertiesInternal sensorProperties = getSensorProperties(i5, string3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sensorProperties, 1);
                    return true;
                case 5:
                    IBinder strongBinder = parcel.readStrongBinder();
                    long j = parcel.readLong();
                    IFingerprintServiceReceiver iFingerprintServiceReceiverAsInterface = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FingerprintAuthenticateOptions fingerprintAuthenticateOptions = (FingerprintAuthenticateOptions) parcel.readTypedObject(FingerprintAuthenticateOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long jAuthenticate = authenticate(strongBinder, j, iFingerprintServiceReceiverAsInterface, fingerprintAuthenticateOptions);
                    parcel2.writeNoException();
                    parcel2.writeLong(jAuthenticate);
                    return true;
                case 6:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    IFingerprintServiceReceiver iFingerprintServiceReceiverAsInterface2 = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FingerprintAuthenticateOptions fingerprintAuthenticateOptions2 = (FingerprintAuthenticateOptions) parcel.readTypedObject(FingerprintAuthenticateOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long jDetectFingerprint = detectFingerprint(strongBinder2, iFingerprintServiceReceiverAsInterface2, fingerprintAuthenticateOptions2);
                    parcel2.writeNoException();
                    parcel2.writeLong(jDetectFingerprint);
                    return true;
                case 7:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    long j2 = parcel.readLong();
                    IBiometricSensorReceiver iBiometricSensorReceiverAsInterface = IBiometricSensorReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FingerprintAuthenticateOptions fingerprintAuthenticateOptions3 = (FingerprintAuthenticateOptions) parcel.readTypedObject(FingerprintAuthenticateOptions.CREATOR);
                    long j3 = parcel.readLong();
                    int i6 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    prepareForAuthentication(strongBinder3, j2, iBiometricSensorReceiverAsInterface, fingerprintAuthenticateOptions3, j3, i6, z2, z3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPreparedClient(i7, i8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    long j4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(strongBinder4, string4, string5, j4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    String string6 = parcel.readString();
                    long j5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelFingerprintDetect(strongBinder5, string6, j5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i9 = parcel.readInt();
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    String string7 = parcel.readString();
                    long j6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthenticationFromService(i9, strongBinder6, string7, j6);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i10 = parcel.readInt();
                    IFingerprintServiceReceiver iFingerprintServiceReceiverAsInterface3 = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string8 = parcel.readString();
                    int i11 = parcel.readInt();
                    FingerprintEnrollOptions fingerprintEnrollOptions = (FingerprintEnrollOptions) parcel.readTypedObject(FingerprintEnrollOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long jEnroll = enroll(strongBinder7, bArrCreateByteArray, i10, iFingerprintServiceReceiverAsInterface3, string8, i11, fingerprintEnrollOptions);
                    parcel2.writeNoException();
                    parcel2.writeLong(jEnroll);
                    return true;
                case 13:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    long j7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelEnrollment(strongBinder8, j7);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    IFingerprintServiceReceiver iFingerprintServiceReceiverAsInterface4 = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    remove(strongBinder9, i12, i13, iFingerprintServiceReceiverAsInterface4, string9);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    int i14 = parcel.readInt();
                    IFingerprintServiceReceiver iFingerprintServiceReceiverAsInterface5 = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeAll(strongBinder10, i14, iFingerprintServiceReceiverAsInterface5, string10);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rename(i15, i16, string11);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int i17 = parcel.readInt();
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<Fingerprint> enrolledFingerprints = getEnrolledFingerprints(i17, string12, string13);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enrolledFingerprints, 1);
                    return true;
                case 18:
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsHardwareDetectedDeprecated = isHardwareDetectedDeprecated(string14, string15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHardwareDetectedDeprecated);
                    return true;
                case 19:
                    int i18 = parcel.readInt();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsHardwareDetected = isHardwareDetected(i18, string16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsHardwareDetected);
                    return true;
                case 20:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    IFingerprintServiceReceiver iFingerprintServiceReceiverAsInterface6 = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    generateChallenge(strongBinder11, i19, i20, iFingerprintServiceReceiverAsInterface6, string17);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    String string18 = parcel.readString();
                    long j8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    revokeChallenge(strongBinder12, i21, i22, string18, j8);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i23 = parcel.readInt();
                    String string19 = parcel.readString();
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasEnrolledFingerprintsDeprecated = hasEnrolledFingerprintsDeprecated(i23, string19, string20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasEnrolledFingerprintsDeprecated);
                    return true;
                case 23:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zHasEnrolledFingerprints = hasEnrolledFingerprints(i24, i25, string21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasEnrolledFingerprints);
                    return true;
                case 24:
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockoutModeForUser = getLockoutModeForUser(i26, i27);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockoutModeForUser);
                    return true;
                case 25:
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    IInvalidationCallback iInvalidationCallbackAsInterface = IInvalidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    invalidateAuthenticatorId(i28, i29, iInvalidationCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long authenticatorId = getAuthenticatorId(i30, i31);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticatorId);
                    return true;
                case 27:
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetLockout(strongBinder13, i32, i33, bArrCreateByteArray2, string22);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallbackAsInterface = IBiometricServiceLockoutResetCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addLockoutResetCallback(iBiometricServiceLockoutResetCallbackAsInterface, string23);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    boolean zIsClientActive = isClientActive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsClientActive);
                    return true;
                case 30:
                    IFingerprintClientActiveCallback iFingerprintClientActiveCallbackAsInterface = IFingerprintClientActiveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addClientActiveCallback(iFingerprintClientActiveCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IFingerprintClientActiveCallback iFingerprintClientActiveCallbackAsInterface2 = IFingerprintClientActiveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeClientActiveCallback(iFingerprintClientActiveCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    FingerprintSensorConfigurations fingerprintSensorConfigurations = (FingerprintSensorConfigurations) parcel.readTypedObject(FingerprintSensorConfigurations.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerAuthenticators(fingerprintSensorConfigurations);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallbackAsInterface = IFingerprintAuthenticatorsRegisteredCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addAuthenticatorsRegisteredCallback(iFingerprintAuthenticatorsRegisteredCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    long j9 = parcel.readLong();
                    int i34 = parcel.readInt();
                    PointerContext pointerContext = (PointerContext) parcel.readTypedObject(PointerContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPointerDown(j9, i34, pointerContext);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    long j10 = parcel.readLong();
                    int i35 = parcel.readInt();
                    PointerContext pointerContext2 = (PointerContext) parcel.readTypedObject(PointerContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPointerUp(j10, i35, pointerContext2);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int i36 = parcel.readInt();
                    long j11 = parcel.readLong();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUdfpsUiEvent(i36, j11, i37);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    long j12 = parcel.readLong();
                    int i38 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setIgnoreDisplayTouches(j12, i38, z4);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    IUdfpsOverlayController iUdfpsOverlayControllerAsInterface = IUdfpsOverlayController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setUdfpsOverlayController(iUdfpsOverlayControllerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    AuthenticationStateListener authenticationStateListenerAsInterface = AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthenticationStateListener(authenticationStateListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    AuthenticationStateListener authenticationStateListenerAsInterface2 = AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAuthenticationStateListener(authenticationStateListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    IBiometricStateListener iBiometricStateListenerAsInterface = IBiometricStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerBiometricStateListener(iBiometricStateListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    onPowerPressed();
                    return true;
                case 43:
                    scheduleWatchdog();
                    return true;
                case 44:
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    long j13 = parcel.readLong();
                    IFingerprintServiceReceiver iFingerprintServiceReceiverAsInterface7 = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FingerprintAuthenticateOptions fingerprintAuthenticateOptions4 = (FingerprintAuthenticateOptions) parcel.readTypedObject(FingerprintAuthenticateOptions.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    long jSemAuthenticate = semAuthenticate(strongBinder14, j13, iFingerprintServiceReceiverAsInterface7, fingerprintAuthenticateOptions4, bundle);
                    parcel2.writeNoException();
                    parcel2.writeLong(jSemAuthenticate);
                    return true;
                case 45:
                    int iSemGetMaxEnrollmentNumber = semGetMaxEnrollmentNumber();
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemGetMaxEnrollmentNumber);
                    return true;
                case 46:
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSemHasFeature = semHasFeature(i39);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemHasFeature);
                    return true;
                case 47:
                    semForceCBGE();
                    parcel2.writeNoException();
                    return true;
                case 48:
                    boolean zSemIsEnrollSession = semIsEnrollSession();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsEnrollSession);
                    return true;
                case 49:
                    boolean zSemIsTemplateDbCorrupted = semIsTemplateDbCorrupted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemIsTemplateDbCorrupted);
                    return true;
                case 50:
                    int iSemGetSensorStatus = semGetSensorStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemGetSensorStatus);
                    return true;
                case 51:
                    boolean zSemPauseEnroll = semPauseEnroll();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemPauseEnroll);
                    return true;
                case 52:
                    boolean zSemResumeEnroll = semResumeEnroll();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemResumeEnroll);
                    return true;
                case 53:
                    boolean zSemOpenSession = semOpenSession();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemOpenSession);
                    return true;
                case 54:
                    String strSemGetSensorInfo = semGetSensorInfo();
                    parcel2.writeNoException();
                    parcel2.writeString(strSemGetSensorInfo);
                    return true;
                case 55:
                    String[] strArrSemGetUserIdList = semGetUserIdList();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrSemGetUserIdList);
                    return true;
                case 56:
                    String strSemGetDaemonVersion = semGetDaemonVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(strSemGetDaemonVersion);
                    return true;
                case 57:
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    int i40 = parcel.readInt();
                    int i41 = parcel.readInt();
                    ISemFingerprintRequestCallback iSemFingerprintRequestCallbackAsInterface = ISemFingerprintRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iSemRunSensorTest = semRunSensorTest(strongBinder15, i40, i41, iSemFingerprintRequestCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemRunSensorTest);
                    return true;
                case 58:
                    int i42 = parcel.readInt();
                    if (i42 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i42);
                    }
                    byte[] bArr = i42 < 0 ? null : new byte[i42];
                    parcel.enforceNoDataAvail();
                    int iSemGetSensorTestResult = semGetSensorTestResult(bArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemGetSensorTestResult);
                    parcel2.writeByteArray(bArr);
                    return true;
                case 59:
                    int i43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iSemSetScreenStatus = semSetScreenStatus(i43);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemSetScreenStatus);
                    return true;
                case 60:
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iSemShowBouncerScreen = semShowBouncerScreen(i44);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemShowBouncerScreen);
                    return true;
                case 61:
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBinder iBinderSemAddMaskView = semAddMaskView(strongBinder16, string24);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(iBinderSemAddMaskView);
                    return true;
                case 62:
                    IBinder strongBinder17 = parcel.readStrongBinder();
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iSemRemoveMaskView = semRemoveMaskView(strongBinder17, string25);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemRemoveMaskView);
                    return true;
                case 63:
                    IBinder strongBinder18 = parcel.readStrongBinder();
                    ISemFingerprintAodController iSemFingerprintAodControllerAsInterface = ISemFingerprintAodController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    semRegisterAodController(strongBinder18, iSemFingerprintAodControllerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    IBinder strongBinder19 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    semUnregisterAodController(strongBinder19);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int i45 = parcel.readInt();
                    int i46 = parcel.readInt();
                    Point point = (Point) parcel.readTypedObject(Point.CREATOR);
                    parcel.enforceNoDataAvail();
                    Rect rectSemGetSensorAreaInDisplay = semGetSensorAreaInDisplay(i45, i46, point);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rectSemGetSensorAreaInDisplay, 1);
                    return true;
                case 66:
                    semShowUdfpsIcon();
                    parcel2.writeNoException();
                    return true;
                case 67:
                    int iSemGetIconBottomMargin = semGetIconBottomMargin();
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemGetIconBottomMargin);
                    return true;
                case 68:
                    int i47 = parcel.readInt();
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semMoveSensorIconInDisplay(i47, i48);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    int iSemGetSecurityLevel = semGetSecurityLevel();
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemGetSecurityLevel);
                    return true;
                case 70:
                    String strSemGetTrustAppVersion = semGetTrustAppVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(strSemGetTrustAppVersion);
                    return true;
                case 71:
                    String string26 = parcel.readString();
                    ISemFingerprintRequestCallback iSemFingerprintRequestCallbackAsInterface2 = ISemFingerprintRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semUpdateTrustApp(string26, iSemFingerprintRequestCallbackAsInterface2, string27);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    int i49 = parcel.readInt();
                    int i50 = parcel.readInt();
                    long j14 = parcel.readLong();
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iSemBioSysUiRequest = semBioSysUiRequest(i49, i50, j14, string28);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemBioSysUiRequest);
                    return true;
                case 73:
                    ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallbackAsInterface = ISemBiometricSysUiDisplayStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iSemRegisterDisplayStateCallback = semRegisterDisplayStateCallback(iSemBiometricSysUiDisplayStateCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemRegisterDisplayStateCallback);
                    return true;
                case 74:
                    semUnregisterDisplayStateCallback();
                    parcel2.writeNoException();
                    return true;
                case 75:
                    ISemBiometricSysUiDisplayBrightnessCallback iSemBiometricSysUiDisplayBrightnessCallbackAsInterface = ISemBiometricSysUiDisplayBrightnessCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iSemRegisterDisplayBrightnessCallback = semRegisterDisplayBrightnessCallback(iSemBiometricSysUiDisplayBrightnessCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemRegisterDisplayBrightnessCallback);
                    return true;
                case 76:
                    semUnregisterDisplayBrightnessCallback();
                    parcel2.writeNoException();
                    return true;
                case 77:
                    Bundle bundle2 = new Bundle();
                    parcel.enforceNoDataAvail();
                    semGetSensorData(bundle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundle2, 1);
                    return true;
                case 78:
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semSetFodStrictMode(z5);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    IBinder strongBinder20 = parcel.readStrongBinder();
                    int i51 = parcel.readInt();
                    String string29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iSemSetCalibrationMode = semSetCalibrationMode(strongBinder20, i51, string29);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemSetCalibrationMode);
                    return true;
                case 80:
                    byte[] bArr2 = null;
                    int i52 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    int i53 = parcel.readInt();
                    if (i53 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i53);
                    }
                    if (i53 >= 0) {
                        bArr2 = new byte[i53];
                    }
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iSemProcessFido = semProcessFido(i52, bArrCreateByteArray3, bArr2, string30);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemProcessFido);
                    parcel2.writeByteArray(bArr2);
                    return true;
                case 81:
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iSemGetRemainingLockoutTime = semGetRemainingLockoutTime(i54);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemGetRemainingLockoutTime);
                    return true;
                case 82:
                    boolean zSemCanChangeDeviceColorMode = semCanChangeDeviceColorMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSemCanChangeDeviceColorMode);
                    return true;
                case 83:
                    int i55 = parcel.readInt();
                    String string31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semSetFlagForIFAA(i55, string31);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    IBinder strongBinder21 = parcel.readStrongBinder();
                    byte[] bArr3 = null;
                    int i56 = parcel.readInt();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    int i57 = parcel.readInt();
                    if (i57 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i57);
                    }
                    if (i57 >= 0) {
                        bArr3 = new byte[i57];
                    }
                    byte[] bArr4 = bArr3;
                    int i58 = parcel.readInt();
                    int i59 = parcel.readInt();
                    String string32 = parcel.readString();
                    ISemFingerprintRequestCallback iSemFingerprintRequestCallbackAsInterface3 = ISemFingerprintRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iSemRequest = semRequest(strongBinder21, i56, bArrCreateByteArray4, bArr4, i58, i59, string32, iSemFingerprintRequestCallbackAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSemRequest);
                    parcel2.writeByteArray(bArr4);
                    return true;
                case 85:
                    onPowerSinglePressed();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IFingerprintService {
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

            @Override // android.hardware.fingerprint.IFingerprintService
            public ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iTestSessionCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ITestSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public byte[] dumpSensorServiceStateProto(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public List<FingerprintSensorPropertiesInternal> getSensorPropertiesInternal(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(FingerprintSensorPropertiesInternal.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public FingerprintSensorPropertiesInternal getSensorProperties(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FingerprintSensorPropertiesInternal) parcelObtain2.readTypedObject(FingerprintSensorPropertiesInternal.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long authenticate(IBinder iBinder, long j, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iFingerprintServiceReceiver);
                    parcelObtain.writeTypedObject(fingerprintAuthenticateOptions, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long detectFingerprint(IBinder iBinder, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iFingerprintServiceReceiver);
                    parcelObtain.writeTypedObject(fingerprintAuthenticateOptions, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void prepareForAuthentication(IBinder iBinder, long j, IBiometricSensorReceiver iBiometricSensorReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, long j2, int i, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iBiometricSensorReceiver);
                    parcelObtain.writeTypedObject(fingerprintAuthenticateOptions, 0);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void startPreparedClient(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelAuthentication(IBinder iBinder, String str, String str2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelFingerprintDetect(IBinder iBinder, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelAuthenticationFromService(int i, IBinder iBinder, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long enroll(IBinder iBinder, byte[] bArr, int i, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str, int i2, FingerprintEnrollOptions fingerprintEnrollOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iFingerprintServiceReceiver);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(fingerprintEnrollOptions, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelEnrollment(IBinder iBinder, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void remove(IBinder iBinder, int i, int i2, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iFingerprintServiceReceiver);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void removeAll(IBinder iBinder, int i, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iFingerprintServiceReceiver);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void rename(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public List<Fingerprint> getEnrolledFingerprints(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(Fingerprint.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean isHardwareDetectedDeprecated(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean isHardwareDetected(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void generateChallenge(IBinder iBinder, int i, int i2, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iFingerprintServiceReceiver);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void revokeChallenge(IBinder iBinder, int i, int i2, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean hasEnrolledFingerprintsDeprecated(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean hasEnrolledFingerprints(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int getLockoutModeForUser(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void invalidateAuthenticatorId(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long getAuthenticatorId(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void resetLockout(IBinder iBinder, int i, int i2, byte[] bArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void addLockoutResetCallback(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBiometricServiceLockoutResetCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean isClientActive() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void addClientActiveCallback(IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFingerprintClientActiveCallback);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void removeClientActiveCallback(IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFingerprintClientActiveCallback);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void registerAuthenticators(FingerprintSensorConfigurations fingerprintSensorConfigurations) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(fingerprintSensorConfigurations, 0);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void addAuthenticatorsRegisteredCallback(IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iFingerprintAuthenticatorsRegisteredCallback);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPointerDown(long j, int i, PointerContext pointerContext) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(pointerContext, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPointerUp(long j, int i, PointerContext pointerContext) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(pointerContext, 0);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onUdfpsUiEvent(int i, long j, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void setIgnoreDisplayTouches(long j, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void setUdfpsOverlayController(IUdfpsOverlayController iUdfpsOverlayController) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iUdfpsOverlayController);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void registerBiometricStateListener(IBiometricStateListener iBiometricStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iBiometricStateListener);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPowerPressed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void scheduleWatchdog() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long semAuthenticate(IBinder iBinder, long j, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iFingerprintServiceReceiver);
                    parcelObtain.writeTypedObject(fingerprintAuthenticateOptions, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetMaxEnrollmentNumber() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semHasFeature(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semForceCBGE() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semIsEnrollSession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semIsTemplateDbCorrupted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetSensorStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semPauseEnroll() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semResumeEnroll() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semOpenSession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public String semGetSensorInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public String[] semGetUserIdList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public String semGetDaemonVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRunSensorTest(IBinder iBinder, int i, int i2, ISemFingerprintRequestCallback iSemFingerprintRequestCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iSemFingerprintRequestCallback);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetSensorTestResult(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(bArr.length);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr);
                    return i;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semSetScreenStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semShowBouncerScreen(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public IBinder semAddMaskView(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readStrongBinder();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRemoveMaskView(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semRegisterAodController(IBinder iBinder, ISemFingerprintAodController iSemFingerprintAodController) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iSemFingerprintAodController);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semUnregisterAodController(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public Rect semGetSensorAreaInDisplay(int i, int i2, Point point) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(point, 0);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Rect) parcelObtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semShowUdfpsIcon() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetIconBottomMargin() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semMoveSensorIconInDisplay(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetSecurityLevel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public String semGetTrustAppVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semUpdateTrustApp(String str, ISemFingerprintRequestCallback iSemFingerprintRequestCallback, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iSemFingerprintRequestCallback);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semBioSysUiRequest(int i, int i2, long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRegisterDisplayStateCallback(ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSemBiometricSysUiDisplayStateCallback);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semUnregisterDisplayStateCallback() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRegisterDisplayBrightnessCallback(ISemBiometricSysUiDisplayBrightnessCallback iSemBiometricSysUiDisplayBrightnessCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSemBiometricSysUiDisplayBrightnessCallback);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semUnregisterDisplayBrightnessCallback() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semGetSensorData(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    if (parcelObtain2.readInt() != 0) {
                        bundle.readFromParcel(parcelObtain2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semSetFodStrictMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semSetCalibrationMode(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semProcessFido(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(bArr2.length);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i2 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr2);
                    return i2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetRemainingLockoutTime(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semCanChangeDeviceColorMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semSetFlagForIFAA(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRequest(IBinder iBinder, int i, byte[] bArr, byte[] bArr2, int i2, int i3, String str, ISemFingerprintRequestCallback iSemFingerprintRequestCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(bArr2.length);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iSemFingerprintRequestCallback);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i4 = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr2);
                    return i4;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPowerSinglePressed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
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

        protected void onUdfpsUiEvent_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void setIgnoreDisplayTouches_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void setUdfpsOverlayController_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void registerAuthenticationStateListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void unregisterAuthenticationStateListener_enforcePermission() throws SecurityException {
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

        protected void onPowerSinglePressed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.USE_BIOMETRIC_INTERNAL, getCallingPid(), getCallingUid());
        }
    }
}
