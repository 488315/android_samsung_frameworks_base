package com.android.server.biometrics;

import android.R;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.biometrics.IAuthService;
import android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.PromptInfo;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.face.IFaceService;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IFingerprintService;
import android.hardware.iris.IIrisService;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.SystemService;
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class AuthService extends SystemService {
    public IBiometricService mBiometricService;
    final IAuthService.Stub mImpl;
    public final Injector mInjector;

    public static int getCredentialBackupModality(int i) {
        return i == 1 ? i : i & (-2);
    }

    public class Injector {
        public IBiometricService getBiometricService() {
            return IBiometricService.Stub.asInterface(ServiceManager.getService("biometric"));
        }

        public void publishBinderService(AuthService authService, IAuthService.Stub stub) {
            authService.publishBinderService("auth", stub);
        }

        public String[] getConfiguration(Context context) {
            return Utils.getHidlSensorConfiguration();
        }

        public IFingerprintService getFingerprintService() {
            return IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint"));
        }

        public IFaceService getFaceService() {
            return IFaceService.Stub.asInterface(ServiceManager.getService("face"));
        }

        public IIrisService getIrisService() {
            return IIrisService.Stub.asInterface(ServiceManager.getService("iris"));
        }

        public AppOpsManager getAppOps(Context context) {
            return (AppOpsManager) context.getSystemService(AppOpsManager.class);
        }

        public boolean isHidlDisabled(Context context) {
            return (Build.IS_ENG || Build.IS_USERDEBUG) && Settings.Secure.getIntForUser(context.getContentResolver(), "com.android.server.biometrics.AuthService.hidlDisabled", 0, -2) == 1;
        }
    }

    public final class AuthServiceImpl extends IAuthService.Stub {
        public AuthServiceImpl() {
        }

        public ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) {
            super.createTestSession_enforcePermission();
            Slog.d("AuthService", "createTestSession: " + str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return AuthService.this.mInjector.getBiometricService().createTestSession(i, iTestSessionCallback, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public List getSensorProperties(String str) {
            super.getSensorProperties_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return AuthService.this.mInjector.getBiometricService().getSensorProperties(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public String getUiPackage() {
            super.getUiPackage_enforcePermission();
            return AuthService.this.getContext().getResources().getString(R.string.config_wwan_data_service_package);
        }

        public long authenticate(IBinder iBinder, long j, int i, IBiometricServiceReceiver iBiometricServiceReceiver, String str, PromptInfo promptInfo) {
            Slog.i("AuthService", "authenticate: [" + i + "] from pkg=" + str);
            int callingUserId = UserHandle.getCallingUserId();
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            if (i == callingUserId) {
                AuthService.this.checkPermission();
            } else {
                Slog.w("AuthService", "User " + callingUserId + " is requesting authentication of userid: " + i);
                AuthService.this.checkInternalPermission();
            }
            if (!AuthService.this.checkAppOps(callingUid, str, "authenticate()")) {
                authenticateFastFail("Denied by app ops: " + str, iBiometricServiceReceiver);
                return -1L;
            }
            if (iBinder == null || iBiometricServiceReceiver == null || str == null || promptInfo == null) {
                authenticateFastFail("Unable to authenticate, one or more null arguments", iBiometricServiceReceiver);
                return -1L;
            }
            if (!Utils.isForeground(callingUid, callingPid)) {
                authenticateFastFail("Caller is not foreground: " + str, iBiometricServiceReceiver);
                return -1L;
            }
            if (promptInfo.containsTestConfigurations() && AuthService.this.getContext().checkCallingOrSelfPermission("android.permission.TEST_BIOMETRIC") != 0) {
                AuthService.this.checkInternalPermission();
            }
            if (promptInfo.containsPrivateApiConfigurations()) {
                AuthService.this.checkInternalPermission();
            }
            if (promptInfo.semGetPrivilegedFlag() != 0 || promptInfo.semGetBiometricType() != 0) {
                AuthService.this.checkPrivilegedPermission();
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                VirtualDeviceManagerInternal virtualDeviceManagerInternal = (VirtualDeviceManagerInternal) AuthService.this.getLocalService(VirtualDeviceManagerInternal.class);
                if (virtualDeviceManagerInternal != null) {
                    virtualDeviceManagerInternal.onAuthenticationPrompt(callingUid);
                }
                return AuthService.this.mBiometricService.authenticate(iBinder, j, i, iBiometricServiceReceiver, str, promptInfo);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void authenticateFastFail(String str, IBiometricServiceReceiver iBiometricServiceReceiver) {
            Slog.e("AuthService", "authenticateFastFail: " + str);
            try {
                iBiometricServiceReceiver.onError(0, 5, 0);
            } catch (RemoteException e) {
                Slog.e("AuthService", "authenticateFastFail failed to notify caller", e);
            }
        }

        public void cancelAuthentication(IBinder iBinder, String str, long j) {
            Slog.d("AuthService", "cancelAuthentication: [" + iBinder + "], [" + str + "]");
            AuthService.this.checkPermission();
            if (iBinder == null || str == null) {
                Slog.e("AuthService", "Unable to cancel authentication, one or more null arguments");
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuthService.this.mBiometricService.cancelAuthentication(iBinder, str, j);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int canAuthenticate(String str, int i, int i2) {
            int callingUserId = UserHandle.getCallingUserId();
            if (i != callingUserId) {
                AuthService.this.checkInternalPermission();
            } else {
                AuthService.this.checkPermission();
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int canAuthenticate = AuthService.this.mBiometricService.canAuthenticate(str, i, callingUserId, i2);
                Slog.d("AuthService", "canAuthenticate, userId: " + i + ", callingUserId: " + callingUserId + ", authenticators: " + i2 + ", result: " + canAuthenticate);
                return canAuthenticate;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean hasEnrolledBiometrics(int i, String str) {
            AuthService.this.checkInternalPermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return AuthService.this.mBiometricService.hasEnrolledBiometrics(i, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) {
            AuthService.this.checkInternalPermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuthService.this.mBiometricService.registerEnabledOnKeyguardCallback(iBiometricEnabledOnKeyguardCallback);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback) {
            AuthService.this.checkInternalPermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuthService.this.mBiometricService.invalidateAuthenticatorIds(i, i2, iInvalidationCallback);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public long[] getAuthenticatorIds(int i) {
            if (i != UserHandle.getCallingUserId()) {
                AuthService.this.getContext().enforceCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL", "Must have android.permission.USE_BIOMETRIC_INTERNAL permission.");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return AuthService.this.mBiometricService.getAuthenticatorIds(i);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr) {
            AuthService.this.checkInternalPermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuthService.this.mBiometricService.resetLockoutTimeBound(iBinder, str, i, i2, bArr);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void resetLockout(int i, byte[] bArr) {
            AuthService.this.checkInternalPermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuthService.this.mBiometricService.resetLockout(i, bArr);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public CharSequence getButtonLabel(int i, String str, int i2) {
            String str2;
            int callingUserId = UserHandle.getCallingUserId();
            if (i != callingUserId) {
                AuthService.this.checkInternalPermission();
            } else {
                AuthService.this.checkPermission();
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int credentialBackupModality = AuthService.getCredentialBackupModality(AuthService.this.mBiometricService.getCurrentModality(str, i, callingUserId, i2));
                if (credentialBackupModality == 0) {
                    str2 = null;
                } else if (credentialBackupModality == 1) {
                    str2 = AuthService.this.getContext().getString(17042524);
                } else if (credentialBackupModality == 2) {
                    str2 = AuthService.this.getContext().getString(R.string.launchBrowserDefault);
                } else if (credentialBackupModality == 8) {
                    str2 = AuthService.this.getContext().getString(R.string.keyguard_accessibility_unlock_area_expanded);
                } else {
                    str2 = AuthService.this.getContext().getString(R.string.config_defaultDndDeniedPackages);
                }
                return str2;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public CharSequence getPromptMessage(int i, String str, int i2) {
            String str2;
            int callingUserId = UserHandle.getCallingUserId();
            if (i != callingUserId) {
                AuthService.this.checkInternalPermission();
            } else {
                AuthService.this.checkPermission();
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int currentModality = AuthService.this.mBiometricService.getCurrentModality(str, i, callingUserId, i2);
                boolean isCredentialRequested = Utils.isCredentialRequested(i2);
                int credentialBackupModality = AuthService.getCredentialBackupModality(currentModality);
                if (credentialBackupModality == 0) {
                    str2 = null;
                } else if (credentialBackupModality == 1) {
                    str2 = AuthService.this.getContext().getString(17042525);
                } else if (credentialBackupModality != 2) {
                    if (credentialBackupModality != 8) {
                        if (isCredentialRequested) {
                            str2 = AuthService.this.getContext().getString(R.string.config_defaultNetworkRecommendationProviderPackage);
                        } else {
                            str2 = AuthService.this.getContext().getString(R.string.config_defaultDockManagerPackageName);
                        }
                    } else if (isCredentialRequested) {
                        str2 = AuthService.this.getContext().getString(R.string.kg_failed_attempts_now_wiping);
                    } else {
                        str2 = AuthService.this.getContext().getString(R.string.keyguard_accessibility_widget_changed);
                    }
                } else if (isCredentialRequested) {
                    str2 = AuthService.this.getContext().getString(R.string.lockscreen_access_pattern_start);
                } else {
                    str2 = AuthService.this.getContext().getString(R.string.launch_warning_replace);
                }
                return str2;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public CharSequence getSettingName(int i, String str, int i2) {
            String str2;
            if (i != UserHandle.getCallingUserId()) {
                AuthService.this.checkInternalPermission();
            } else {
                AuthService.this.checkPermission();
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int supportedModalities = AuthService.this.mBiometricService.getSupportedModalities(i2);
                if (supportedModalities == 0) {
                    str2 = null;
                } else if (supportedModalities == 1) {
                    str2 = AuthService.this.getContext().getString(17042524);
                } else if (supportedModalities == 2) {
                    str2 = AuthService.this.getContext().getString(R.string.launchBrowserDefault);
                } else if (supportedModalities == 4) {
                    str2 = AuthService.this.getContext().getString(R.string.config_defaultDndDeniedPackages);
                } else if (supportedModalities == 8) {
                    str2 = AuthService.this.getContext().getString(R.string.keyguard_accessibility_unlock_area_expanded);
                } else if ((supportedModalities & 1) == 0) {
                    str2 = AuthService.this.getContext().getString(R.string.config_defaultDndDeniedPackages);
                } else {
                    int i3 = supportedModalities & (-2);
                    if (i3 == 2) {
                        str2 = AuthService.this.getContext().getString(R.string.lockscreen_access_pattern_detected);
                    } else if (i3 == 8) {
                        str2 = AuthService.this.getContext().getString(R.string.kg_failed_attempts_almost_at_wipe);
                    } else {
                        str2 = AuthService.this.getContext().getString(R.string.config_defaultNearbySharingSliceUri);
                    }
                }
                return str2;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public AuthService(Context context) {
        this(context, new Injector());
    }

    public AuthService(Context context, Injector injector) {
        super(context);
        this.mInjector = injector;
        this.mImpl = new AuthServiceImpl();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        SensorConfig[] sensorConfigArr;
        this.mBiometricService = this.mInjector.getBiometricService();
        if (this.mInjector.isHidlDisabled(getContext())) {
            sensorConfigArr = null;
        } else {
            int i = SystemProperties.getInt("ro.board.api_level", SystemProperties.getInt("ro.board.first_api_level", 0));
            String[] configuration = this.mInjector.getConfiguration(getContext());
            if (configuration.length == 0 && i == 30) {
                Slog.w("AuthService", "Found R vendor partition without config_biometric_sensors");
                configuration = generateRSdkCompatibleConfiguration();
            }
            sensorConfigArr = new SensorConfig[configuration.length];
            for (int i2 = 0; i2 < configuration.length; i2++) {
                sensorConfigArr[i2] = new SensorConfig(configuration[i2]);
            }
        }
        registerAuthenticators(sensorConfigArr);
        this.mInjector.publishBinderService(this, this.mImpl);
    }

    public final String[] generateRSdkCompatibleConfiguration() {
        PackageManager packageManager = getContext().getPackageManager();
        ArrayList arrayList = new ArrayList();
        if (packageManager.hasSystemFeature("android.hardware.fingerprint")) {
            arrayList.add(String.valueOf(2));
        }
        if (packageManager.hasSystemFeature("android.hardware.biometrics.face")) {
            arrayList.add(String.valueOf(8));
        }
        String valueOf = String.valueOf(4095);
        String[] strArr = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            strArr[i] = String.join(XmlUtils.STRING_ARRAY_SEPARATOR, String.valueOf(i), (String) arrayList.get(i), valueOf);
        }
        Slog.d("AuthService", "Generated config_biometric_sensors: " + Arrays.toString(strArr));
        return strArr;
    }

    public final void registerAuthenticators(SensorConfig[] sensorConfigArr) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        if (sensorConfigArr != null) {
            for (SensorConfig sensorConfig : sensorConfigArr) {
                Slog.d("AuthService", "Registering HIDL ID: " + sensorConfig.f1652id + " Modality: " + sensorConfig.modality + " Strength: " + sensorConfig.strength);
                int i = sensorConfig.modality;
                if (i == 2) {
                    arrayList.add(getHidlFingerprintSensorProps(sensorConfig.f1652id, sensorConfig.strength));
                } else if (i == 4) {
                    arrayList3.add(getHidlIrisSensorProps(sensorConfig.f1652id, sensorConfig.strength));
                } else if (i == 8) {
                    arrayList2.add(getHidlFaceSensorProps(sensorConfig.f1652id, sensorConfig.strength));
                } else {
                    Slog.e("AuthService", "Unknown modality: " + sensorConfig.modality);
                }
            }
        }
        IFingerprintService fingerprintService = this.mInjector.getFingerprintService();
        if (fingerprintService != null) {
            try {
                fingerprintService.registerAuthenticators(arrayList);
            } catch (RemoteException e) {
                Slog.e("AuthService", "RemoteException when registering fingerprint authenticators", e);
            }
        } else if (arrayList.size() > 0) {
            Slog.e("AuthService", "HIDL fingerprint configuration exists, but FingerprintService is null.");
        }
        IFaceService faceService = this.mInjector.getFaceService();
        if (faceService != null) {
            try {
                faceService.registerAuthenticators(arrayList2);
            } catch (RemoteException e2) {
                Slog.e("AuthService", "RemoteException when registering face authenticators", e2);
            }
        } else if (arrayList2.size() > 0) {
            Slog.e("AuthService", "HIDL face configuration exists, but FaceService is null.");
        }
        IIrisService irisService = this.mInjector.getIrisService();
        if (irisService != null) {
            try {
                irisService.registerAuthenticators(arrayList3);
                return;
            } catch (RemoteException e3) {
                Slog.e("AuthService", "RemoteException when registering iris authenticators", e3);
                return;
            }
        }
        if (arrayList3.size() > 0) {
            Slog.e("AuthService", "HIDL iris configuration exists, but IrisService is null.");
        }
    }

    public final void checkInternalPermission() {
        getContext().enforceCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL", "Must have USE_BIOMETRIC_INTERNAL permission");
    }

    public final void checkPermission() {
        if (getContext().checkCallingOrSelfPermission("android.permission.USE_FINGERPRINT") != 0) {
            getContext().enforceCallingOrSelfPermission("android.permission.USE_BIOMETRIC", "Must have USE_BIOMETRIC permission");
        }
    }

    public final boolean checkAppOps(int i, String str, String str2) {
        return this.mInjector.getAppOps(getContext()).noteOp(78, i, str, (String) null, str2) == 0;
    }

    public final FingerprintSensorPropertiesInternal getHidlFingerprintSensorProps(int i, int i2) {
        return new FingerprintSensorPropertiesInternal(i, Utils.authenticatorStrengthToPropertyStrength(i2), FingerprintUtils.semGetMaxTemplateNumberFromSPF(), new ArrayList(), FingerprintUtils.semGetSensorType(), false);
    }

    public final FaceSensorPropertiesInternal getHidlFaceSensorProps(int i, int i2) {
        boolean z = getContext().getResources().getBoolean(R.bool.config_enableNightMode);
        return new FaceSensorPropertiesInternal(i, Utils.authenticatorStrengthToPropertyStrength(i2), getContext().getResources().getInteger(R.integer.config_letterboxActivityCornersRadius), new ArrayList(), 0, false, z, true);
    }

    public final SensorPropertiesInternal getHidlIrisSensorProps(int i, int i2) {
        return new SensorPropertiesInternal(i, Utils.authenticatorStrengthToPropertyStrength(i2), 1, new ArrayList(), false, false);
    }

    public final void checkPrivilegedPermission() {
        getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.BIOMETRICS_PRIVILEGED", "Must have BIOMETRICS_PRIVILEGED permission");
    }
}
