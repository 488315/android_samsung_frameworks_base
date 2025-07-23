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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFingerprintService)) {
                return (IFingerprintService) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    ITestSessionCallback asInterface = ITestSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ITestSession createTestSession = createTestSession(readInt, asInterface, readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createTestSession);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] dumpSensorServiceStateProto = dumpSensorServiceStateProto(readInt2, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(dumpSensorServiceStateProto);
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<FingerprintSensorPropertiesInternal> sensorPropertiesInternal = getSensorPropertiesInternal(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(sensorPropertiesInternal, 1);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    FingerprintSensorPropertiesInternal sensorProperties = getSensorProperties(readInt3, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sensorProperties, 1);
                    return true;
                case 5:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    long readLong = parcel.readLong();
                    IFingerprintServiceReceiver asInterface2 = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FingerprintAuthenticateOptions fingerprintAuthenticateOptions = (FingerprintAuthenticateOptions) parcel.readTypedObject(FingerprintAuthenticateOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long authenticate = authenticate(readStrongBinder, readLong, asInterface2, fingerprintAuthenticateOptions);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticate);
                    return true;
                case 6:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    IFingerprintServiceReceiver asInterface3 = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FingerprintAuthenticateOptions fingerprintAuthenticateOptions2 = (FingerprintAuthenticateOptions) parcel.readTypedObject(FingerprintAuthenticateOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long detectFingerprint = detectFingerprint(readStrongBinder2, asInterface3, fingerprintAuthenticateOptions2);
                    parcel2.writeNoException();
                    parcel2.writeLong(detectFingerprint);
                    return true;
                case 7:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    long readLong2 = parcel.readLong();
                    IBiometricSensorReceiver asInterface4 = IBiometricSensorReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FingerprintAuthenticateOptions fingerprintAuthenticateOptions3 = (FingerprintAuthenticateOptions) parcel.readTypedObject(FingerprintAuthenticateOptions.CREATOR);
                    long readLong3 = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    prepareForAuthentication(readStrongBinder3, readLong2, asInterface4, fingerprintAuthenticateOptions3, readLong3, readInt4, readBoolean2, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startPreparedClient(readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthentication(readStrongBinder4, readString4, readString5, readLong4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    String readString6 = parcel.readString();
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelFingerprintDetect(readStrongBinder5, readString6, readLong5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt7 = parcel.readInt();
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    String readString7 = parcel.readString();
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelAuthenticationFromService(readInt7, readStrongBinder6, readString7, readLong6);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt8 = parcel.readInt();
                    IFingerprintServiceReceiver asInterface5 = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString8 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    FingerprintEnrollOptions fingerprintEnrollOptions = (FingerprintEnrollOptions) parcel.readTypedObject(FingerprintEnrollOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    long enroll = enroll(readStrongBinder7, createByteArray, readInt8, asInterface5, readString8, readInt9, fingerprintEnrollOptions);
                    parcel2.writeNoException();
                    parcel2.writeLong(enroll);
                    return true;
                case 13:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelEnrollment(readStrongBinder8, readLong7);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    IFingerprintServiceReceiver asInterface6 = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    remove(readStrongBinder9, readInt10, readInt11, asInterface6, readString9);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    int readInt12 = parcel.readInt();
                    IFingerprintServiceReceiver asInterface7 = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeAll(readStrongBinder10, readInt12, asInterface7, readString10);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rename(readInt13, readInt14, readString11);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt15 = parcel.readInt();
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<Fingerprint> enrolledFingerprints = getEnrolledFingerprints(readInt15, readString12, readString13);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enrolledFingerprints, 1);
                    return true;
                case 18:
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isHardwareDetectedDeprecated = isHardwareDetectedDeprecated(readString14, readString15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHardwareDetectedDeprecated);
                    return true;
                case 19:
                    int readInt16 = parcel.readInt();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isHardwareDetected = isHardwareDetected(readInt16, readString16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHardwareDetected);
                    return true;
                case 20:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    IFingerprintServiceReceiver asInterface8 = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    generateChallenge(readStrongBinder11, readInt17, readInt18, asInterface8, readString17);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    String readString18 = parcel.readString();
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    revokeChallenge(readStrongBinder12, readInt19, readInt20, readString18, readLong8);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt21 = parcel.readInt();
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasEnrolledFingerprintsDeprecated = hasEnrolledFingerprintsDeprecated(readInt21, readString19, readString20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnrolledFingerprintsDeprecated);
                    return true;
                case 23:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasEnrolledFingerprints = hasEnrolledFingerprints(readInt22, readInt23, readString21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnrolledFingerprints);
                    return true;
                case 24:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockoutModeForUser = getLockoutModeForUser(readInt24, readInt25);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockoutModeForUser);
                    return true;
                case 25:
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    IInvalidationCallback asInterface9 = IInvalidationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    invalidateAuthenticatorId(readInt26, readInt27, asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long authenticatorId = getAuthenticatorId(readInt28, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeLong(authenticatorId);
                    return true;
                case 27:
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    resetLockout(readStrongBinder13, readInt30, readInt31, createByteArray2, readString22);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    IBiometricServiceLockoutResetCallback asInterface10 = IBiometricServiceLockoutResetCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addLockoutResetCallback(asInterface10, readString23);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    boolean isClientActive = isClientActive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isClientActive);
                    return true;
                case 30:
                    IFingerprintClientActiveCallback asInterface11 = IFingerprintClientActiveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addClientActiveCallback(asInterface11);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IFingerprintClientActiveCallback asInterface12 = IFingerprintClientActiveCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeClientActiveCallback(asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    FingerprintSensorConfigurations fingerprintSensorConfigurations = (FingerprintSensorConfigurations) parcel.readTypedObject(FingerprintSensorConfigurations.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerAuthenticators(fingerprintSensorConfigurations);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IFingerprintAuthenticatorsRegisteredCallback asInterface13 = IFingerprintAuthenticatorsRegisteredCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addAuthenticatorsRegisteredCallback(asInterface13);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    long readLong9 = parcel.readLong();
                    int readInt32 = parcel.readInt();
                    PointerContext pointerContext = (PointerContext) parcel.readTypedObject(PointerContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPointerDown(readLong9, readInt32, pointerContext);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    long readLong10 = parcel.readLong();
                    int readInt33 = parcel.readInt();
                    PointerContext pointerContext2 = (PointerContext) parcel.readTypedObject(PointerContext.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPointerUp(readLong10, readInt33, pointerContext2);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int readInt34 = parcel.readInt();
                    long readLong11 = parcel.readLong();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUdfpsUiEvent(readInt34, readLong11, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    long readLong12 = parcel.readLong();
                    int readInt36 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setIgnoreDisplayTouches(readLong12, readInt36, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    IUdfpsOverlayController asInterface14 = IUdfpsOverlayController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setUdfpsOverlayController(asInterface14);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    AuthenticationStateListener asInterface15 = AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAuthenticationStateListener(asInterface15);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    AuthenticationStateListener asInterface16 = AuthenticationStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAuthenticationStateListener(asInterface16);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    IBiometricStateListener asInterface17 = IBiometricStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerBiometricStateListener(asInterface17);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    onPowerPressed();
                    return true;
                case 43:
                    scheduleWatchdog();
                    return true;
                case 44:
                    IBinder readStrongBinder14 = parcel.readStrongBinder();
                    long readLong13 = parcel.readLong();
                    IFingerprintServiceReceiver asInterface18 = IFingerprintServiceReceiver.Stub.asInterface(parcel.readStrongBinder());
                    FingerprintAuthenticateOptions fingerprintAuthenticateOptions4 = (FingerprintAuthenticateOptions) parcel.readTypedObject(FingerprintAuthenticateOptions.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    long semAuthenticate = semAuthenticate(readStrongBinder14, readLong13, asInterface18, fingerprintAuthenticateOptions4, bundle);
                    parcel2.writeNoException();
                    parcel2.writeLong(semAuthenticate);
                    return true;
                case 45:
                    int semGetMaxEnrollmentNumber = semGetMaxEnrollmentNumber();
                    parcel2.writeNoException();
                    parcel2.writeInt(semGetMaxEnrollmentNumber);
                    return true;
                case 46:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean semHasFeature = semHasFeature(readInt37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semHasFeature);
                    return true;
                case 47:
                    semForceCBGE();
                    parcel2.writeNoException();
                    return true;
                case 48:
                    boolean semIsEnrollSession = semIsEnrollSession();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsEnrollSession);
                    return true;
                case 49:
                    boolean semIsTemplateDbCorrupted = semIsTemplateDbCorrupted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semIsTemplateDbCorrupted);
                    return true;
                case 50:
                    int semGetSensorStatus = semGetSensorStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(semGetSensorStatus);
                    return true;
                case 51:
                    boolean semPauseEnroll = semPauseEnroll();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semPauseEnroll);
                    return true;
                case 52:
                    boolean semResumeEnroll = semResumeEnroll();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semResumeEnroll);
                    return true;
                case 53:
                    boolean semOpenSession = semOpenSession();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semOpenSession);
                    return true;
                case 54:
                    String semGetSensorInfo = semGetSensorInfo();
                    parcel2.writeNoException();
                    parcel2.writeString(semGetSensorInfo);
                    return true;
                case 55:
                    String[] semGetUserIdList = semGetUserIdList();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(semGetUserIdList);
                    return true;
                case 56:
                    String semGetDaemonVersion = semGetDaemonVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(semGetDaemonVersion);
                    return true;
                case 57:
                    IBinder readStrongBinder15 = parcel.readStrongBinder();
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    ISemFingerprintRequestCallback asInterface19 = ISemFingerprintRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int semRunSensorTest = semRunSensorTest(readStrongBinder15, readInt38, readInt39, asInterface19);
                    parcel2.writeNoException();
                    parcel2.writeInt(semRunSensorTest);
                    return true;
                case 58:
                    int readInt40 = parcel.readInt();
                    if (readInt40 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt40);
                    }
                    byte[] bArr = readInt40 < 0 ? null : new byte[readInt40];
                    parcel.enforceNoDataAvail();
                    int semGetSensorTestResult = semGetSensorTestResult(bArr);
                    parcel2.writeNoException();
                    parcel2.writeInt(semGetSensorTestResult);
                    parcel2.writeByteArray(bArr);
                    return true;
                case 59:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int semSetScreenStatus = semSetScreenStatus(readInt41);
                    parcel2.writeNoException();
                    parcel2.writeInt(semSetScreenStatus);
                    return true;
                case 60:
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int semShowBouncerScreen = semShowBouncerScreen(readInt42);
                    parcel2.writeNoException();
                    parcel2.writeInt(semShowBouncerScreen);
                    return true;
                case 61:
                    IBinder readStrongBinder16 = parcel.readStrongBinder();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IBinder semAddMaskView = semAddMaskView(readStrongBinder16, readString24);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(semAddMaskView);
                    return true;
                case 62:
                    IBinder readStrongBinder17 = parcel.readStrongBinder();
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int semRemoveMaskView = semRemoveMaskView(readStrongBinder17, readString25);
                    parcel2.writeNoException();
                    parcel2.writeInt(semRemoveMaskView);
                    return true;
                case 63:
                    IBinder readStrongBinder18 = parcel.readStrongBinder();
                    ISemFingerprintAodController asInterface20 = ISemFingerprintAodController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    semRegisterAodController(readStrongBinder18, asInterface20);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    IBinder readStrongBinder19 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    semUnregisterAodController(readStrongBinder19);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    Point point = (Point) parcel.readTypedObject(Point.CREATOR);
                    parcel.enforceNoDataAvail();
                    Rect semGetSensorAreaInDisplay = semGetSensorAreaInDisplay(readInt43, readInt44, point);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semGetSensorAreaInDisplay, 1);
                    return true;
                case 66:
                    semShowUdfpsIcon();
                    parcel2.writeNoException();
                    return true;
                case 67:
                    int semGetIconBottomMargin = semGetIconBottomMargin();
                    parcel2.writeNoException();
                    parcel2.writeInt(semGetIconBottomMargin);
                    return true;
                case 68:
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    semMoveSensorIconInDisplay(readInt45, readInt46);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    int semGetSecurityLevel = semGetSecurityLevel();
                    parcel2.writeNoException();
                    parcel2.writeInt(semGetSecurityLevel);
                    return true;
                case 70:
                    String semGetTrustAppVersion = semGetTrustAppVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(semGetTrustAppVersion);
                    return true;
                case 71:
                    String readString26 = parcel.readString();
                    ISemFingerprintRequestCallback asInterface21 = ISemFingerprintRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semUpdateTrustApp(readString26, asInterface21, readString27);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    long readLong14 = parcel.readLong();
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int semBioSysUiRequest = semBioSysUiRequest(readInt47, readInt48, readLong14, readString28);
                    parcel2.writeNoException();
                    parcel2.writeInt(semBioSysUiRequest);
                    return true;
                case 73:
                    ISemBiometricSysUiDisplayStateCallback asInterface22 = ISemBiometricSysUiDisplayStateCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int semRegisterDisplayStateCallback = semRegisterDisplayStateCallback(asInterface22);
                    parcel2.writeNoException();
                    parcel2.writeInt(semRegisterDisplayStateCallback);
                    return true;
                case 74:
                    semUnregisterDisplayStateCallback();
                    parcel2.writeNoException();
                    return true;
                case 75:
                    ISemBiometricSysUiDisplayBrightnessCallback asInterface23 = ISemBiometricSysUiDisplayBrightnessCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int semRegisterDisplayBrightnessCallback = semRegisterDisplayBrightnessCallback(asInterface23);
                    parcel2.writeNoException();
                    parcel2.writeInt(semRegisterDisplayBrightnessCallback);
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
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semSetFodStrictMode(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    IBinder readStrongBinder20 = parcel.readStrongBinder();
                    int readInt49 = parcel.readInt();
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int semSetCalibrationMode = semSetCalibrationMode(readStrongBinder20, readInt49, readString29);
                    parcel2.writeNoException();
                    parcel2.writeInt(semSetCalibrationMode);
                    return true;
                case 80:
                    byte[] bArr2 = null;
                    int readInt50 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    int readInt51 = parcel.readInt();
                    if (readInt51 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt51);
                    }
                    if (readInt51 >= 0) {
                        bArr2 = new byte[readInt51];
                    }
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int semProcessFido = semProcessFido(readInt50, createByteArray3, bArr2, readString30);
                    parcel2.writeNoException();
                    parcel2.writeInt(semProcessFido);
                    parcel2.writeByteArray(bArr2);
                    return true;
                case 81:
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int semGetRemainingLockoutTime = semGetRemainingLockoutTime(readInt52);
                    parcel2.writeNoException();
                    parcel2.writeInt(semGetRemainingLockoutTime);
                    return true;
                case 82:
                    boolean semCanChangeDeviceColorMode = semCanChangeDeviceColorMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(semCanChangeDeviceColorMode);
                    return true;
                case 83:
                    int readInt53 = parcel.readInt();
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semSetFlagForIFAA(readInt53, readString31);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    IBinder readStrongBinder21 = parcel.readStrongBinder();
                    byte[] bArr3 = null;
                    int readInt54 = parcel.readInt();
                    byte[] createByteArray4 = parcel.createByteArray();
                    int readInt55 = parcel.readInt();
                    if (readInt55 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt55);
                    }
                    if (readInt55 >= 0) {
                        bArr3 = new byte[readInt55];
                    }
                    byte[] bArr4 = bArr3;
                    int readInt56 = parcel.readInt();
                    int readInt57 = parcel.readInt();
                    String readString32 = parcel.readString();
                    ISemFingerprintRequestCallback asInterface24 = ISemFingerprintRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int semRequest = semRequest(readStrongBinder21, readInt54, createByteArray4, bArr4, readInt56, readInt57, readString32, asInterface24);
                    parcel2.writeNoException();
                    parcel2.writeInt(semRequest);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTestSessionCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return ITestSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public byte[] dumpSensorServiceStateProto(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public List<FingerprintSensorPropertiesInternal> getSensorPropertiesInternal(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(FingerprintSensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public FingerprintSensorPropertiesInternal getSensorProperties(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FingerprintSensorPropertiesInternal) obtain2.readTypedObject(FingerprintSensorPropertiesInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long authenticate(IBinder iBinder, long j, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iFingerprintServiceReceiver);
                    obtain.writeTypedObject(fingerprintAuthenticateOptions, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long detectFingerprint(IBinder iBinder, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iFingerprintServiceReceiver);
                    obtain.writeTypedObject(fingerprintAuthenticateOptions, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void prepareForAuthentication(IBinder iBinder, long j, IBiometricSensorReceiver iBiometricSensorReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, long j2, int i, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iBiometricSensorReceiver);
                    obtain.writeTypedObject(fingerprintAuthenticateOptions, 0);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void startPreparedClient(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelAuthentication(IBinder iBinder, String str, String str2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelFingerprintDetect(IBinder iBinder, String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelAuthenticationFromService(int i, IBinder iBinder, String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long enroll(IBinder iBinder, byte[] bArr, int i, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str, int i2, FingerprintEnrollOptions fingerprintEnrollOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iFingerprintServiceReceiver);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(fingerprintEnrollOptions, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void cancelEnrollment(IBinder iBinder, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void remove(IBinder iBinder, int i, int i2, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iFingerprintServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void removeAll(IBinder iBinder, int i, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iFingerprintServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void rename(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public List<Fingerprint> getEnrolledFingerprints(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Fingerprint.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean isHardwareDetectedDeprecated(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean isHardwareDetected(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void generateChallenge(IBinder iBinder, int i, int i2, IFingerprintServiceReceiver iFingerprintServiceReceiver, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iFingerprintServiceReceiver);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void revokeChallenge(IBinder iBinder, int i, int i2, String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean hasEnrolledFingerprintsDeprecated(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean hasEnrolledFingerprints(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int getLockoutModeForUser(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void invalidateAuthenticatorId(int i, int i2, IInvalidationCallback iInvalidationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iInvalidationCallback);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long getAuthenticatorId(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void resetLockout(IBinder iBinder, int i, int i2, byte[] bArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void addLockoutResetCallback(IBiometricServiceLockoutResetCallback iBiometricServiceLockoutResetCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricServiceLockoutResetCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean isClientActive() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void addClientActiveCallback(IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iFingerprintClientActiveCallback);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void removeClientActiveCallback(IFingerprintClientActiveCallback iFingerprintClientActiveCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iFingerprintClientActiveCallback);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void registerAuthenticators(FingerprintSensorConfigurations fingerprintSensorConfigurations) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fingerprintSensorConfigurations, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void addAuthenticatorsRegisteredCallback(IFingerprintAuthenticatorsRegisteredCallback iFingerprintAuthenticatorsRegisteredCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iFingerprintAuthenticatorsRegisteredCallback);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPointerDown(long j, int i, PointerContext pointerContext) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pointerContext, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPointerUp(long j, int i, PointerContext pointerContext) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(pointerContext, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onUdfpsUiEvent(int i, long j, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void setIgnoreDisplayTouches(long j, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void setUdfpsOverlayController(IUdfpsOverlayController iUdfpsOverlayController) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUdfpsOverlayController);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(authenticationStateListener);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void registerBiometricStateListener(IBiometricStateListener iBiometricStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricStateListener);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPowerPressed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void scheduleWatchdog() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public long semAuthenticate(IBinder iBinder, long j, IFingerprintServiceReceiver iFingerprintServiceReceiver, FingerprintAuthenticateOptions fingerprintAuthenticateOptions, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iFingerprintServiceReceiver);
                    obtain.writeTypedObject(fingerprintAuthenticateOptions, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetMaxEnrollmentNumber() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semHasFeature(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semForceCBGE() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semIsEnrollSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semIsTemplateDbCorrupted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetSensorStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semPauseEnroll() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semResumeEnroll() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semOpenSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public String semGetSensorInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public String[] semGetUserIdList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public String semGetDaemonVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRunSensorTest(IBinder iBinder, int i, int i2, ISemFingerprintRequestCallback iSemFingerprintRequestCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iSemFingerprintRequestCallback);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetSensorTestResult(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(bArr.length);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semSetScreenStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semShowBouncerScreen(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public IBinder semAddMaskView(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRemoveMaskView(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semRegisterAodController(IBinder iBinder, ISemFingerprintAodController iSemFingerprintAodController) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iSemFingerprintAodController);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semUnregisterAodController(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public Rect semGetSensorAreaInDisplay(int i, int i2, Point point) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(point, 0);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Rect) obtain2.readTypedObject(Rect.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semShowUdfpsIcon() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetIconBottomMargin() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semMoveSensorIconInDisplay(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetSecurityLevel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public String semGetTrustAppVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semUpdateTrustApp(String str, ISemFingerprintRequestCallback iSemFingerprintRequestCallback, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSemFingerprintRequestCallback);
                    obtain.writeString(str2);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semBioSysUiRequest(int i, int i2, long j, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRegisterDisplayStateCallback(ISemBiometricSysUiDisplayStateCallback iSemBiometricSysUiDisplayStateCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSemBiometricSysUiDisplayStateCallback);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semUnregisterDisplayStateCallback() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRegisterDisplayBrightnessCallback(ISemBiometricSysUiDisplayBrightnessCallback iSemBiometricSysUiDisplayBrightnessCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSemBiometricSysUiDisplayBrightnessCallback);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semUnregisterDisplayBrightnessCallback() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semGetSensorData(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semSetFodStrictMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semSetCalibrationMode(IBinder iBinder, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semProcessFido(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(bArr2.length);
                    obtain.writeString(str);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr2);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semGetRemainingLockoutTime(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public boolean semCanChangeDeviceColorMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void semSetFlagForIFAA(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public int semRequest(IBinder iBinder, int i, byte[] bArr, byte[] bArr2, int i2, int i3, String str, ISemFingerprintRequestCallback iSemFingerprintRequestCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(bArr2.length);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSemFingerprintRequestCallback);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr2);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintService
            public void onPowerSinglePressed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, obtain, null, 1);
                } finally {
                    obtain.recycle();
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
