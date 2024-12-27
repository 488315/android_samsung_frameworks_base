package com.android.server.biometrics;

import android.R;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.biometrics.AuthenticationStateListener;
import android.hardware.biometrics.IAuthService;
import android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback;
import android.hardware.biometrics.IBiometricService;
import android.hardware.biometrics.IBiometricServiceReceiver;
import android.hardware.biometrics.IInvalidationCallback;
import android.hardware.biometrics.ITestSession;
import android.hardware.biometrics.ITestSessionCallback;
import android.hardware.biometrics.PromptInfo;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.biometrics.face.IFace;
import android.hardware.biometrics.fingerprint.IFingerprint;
import android.hardware.face.FaceSensorConfigurations;
import android.hardware.face.IFaceService;
import android.hardware.fingerprint.FingerprintSensorConfigurations;
import android.hardware.fingerprint.IFingerprintService;
import android.hardware.iris.IIrisService;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AuthService extends SystemService {
    public IBiometricService mBiometricService;
    final IAuthService.Stub mImpl;
    public final Injector mInjector;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AuthServiceImpl extends IAuthService.Stub {
        public AuthServiceImpl() {
        }

        public static void authenticateFastFail(String str, IBiometricServiceReceiver iBiometricServiceReceiver) {
            BootReceiver$$ExternalSyntheticOutline0.m("authenticateFastFail: ", str, "AuthService");
            try {
                iBiometricServiceReceiver.onError(0, 5, 0);
            } catch (RemoteException e) {
                Slog.e("AuthService", "authenticateFastFail failed to notify caller", e);
            }
        }

        public final long authenticate(IBinder iBinder, long j, int i, IBiometricServiceReceiver iBiometricServiceReceiver, String str, PromptInfo promptInfo) {
            Slog.i("AuthService", "authenticate: [" + i + "] from pkg=" + str);
            int callingUserId = UserHandle.getCallingUserId();
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            if (i == callingUserId) {
                AuthService.m311$$Nest$mcheckPermission(AuthService.this);
            } else {
                PendingIntentController$$ExternalSyntheticOutline0.m(callingUserId, i, "User ", " is requesting authentication of userid: ", "AuthService");
                AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            }
            AuthService authService = AuthService.this;
            if (authService.mInjector.getAppOps(authService.getContext()).noteOp(78, callingUid, str, (String) null, "authenticate()") != 0) {
                authenticateFastFail("Denied by app ops: " + str, iBiometricServiceReceiver);
                return -1L;
            }
            if (iBinder == null || iBiometricServiceReceiver == null || str == null || promptInfo == null) {
                authenticateFastFail("Unable to authenticate, one or more null arguments", iBiometricServiceReceiver);
                return -1L;
            }
            if (!Utils.isForeground(callingUid, callingPid)) {
                authenticateFastFail("Caller is not foreground: ".concat(str), iBiometricServiceReceiver);
                return -1L;
            }
            if (promptInfo.requiresTestOrInternalPermission() && AuthService.this.getContext().checkCallingOrSelfPermission("android.permission.TEST_BIOMETRIC") != 0) {
                AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            }
            if (promptInfo.requiresInternalPermission()) {
                AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            }
            if (promptInfo.requiresAdvancedPermission()) {
                AuthService.this.getContext().enforceCallingOrSelfPermission("android.permission.SET_BIOMETRIC_DIALOG_ADVANCED", "Must have SET_BIOMETRIC_DIALOG_ADVANCED permission");
            }
            if (promptInfo.semGetPrivilegedFlag() != 0 || promptInfo.semGetBiometricType() != 0) {
                AuthService.this.getContext().enforceCallingOrSelfPermission("com.samsung.android.permission.BIOMETRICS_PRIVILEGED", "Must have BIOMETRICS_PRIVILEGED permission");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                VirtualDeviceManagerInternal virtualDeviceManagerInternal = (VirtualDeviceManagerInternal) AuthService.this.getLocalService(VirtualDeviceManagerInternal.class);
                if (virtualDeviceManagerInternal != null) {
                    virtualDeviceManagerInternal.onAuthenticationPrompt(callingUid);
                }
                long authenticate = AuthService.this.mBiometricService.authenticate(iBinder, j, i, iBiometricServiceReceiver, str, promptInfo);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return authenticate;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final int canAuthenticate(String str, int i, int i2) {
            int callingUserId = UserHandle.getCallingUserId();
            if (i != callingUserId) {
                AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            } else {
                AuthService.m311$$Nest$mcheckPermission(AuthService.this);
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

        public final void cancelAuthentication(IBinder iBinder, String str, long j) {
            Slog.d("AuthService", "cancelAuthentication: [" + iBinder + "], [" + str + "]");
            AuthService.m311$$Nest$mcheckPermission(AuthService.this);
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

        public final ITestSession createTestSession(int i, ITestSessionCallback iTestSessionCallback, String str) {
            createTestSession_enforcePermission();
            Slog.d("AuthService", "createTestSession: " + str);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return AuthService.this.mInjector.getBiometricService().createTestSession(i, iTestSessionCallback, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final long[] getAuthenticatorIds(int i) {
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

        public final CharSequence getButtonLabel(int i, String str, int i2) {
            int callingUserId = UserHandle.getCallingUserId();
            if (i != callingUserId) {
                AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            } else {
                AuthService.m311$$Nest$mcheckPermission(AuthService.this);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int currentModality = AuthService.this.mBiometricService.getCurrentModality(str, i, callingUserId, i2);
                if (currentModality != 1) {
                    currentModality &= -2;
                }
                String string = currentModality != 0 ? currentModality != 1 ? currentModality != 2 ? currentModality != 8 ? AuthService.this.getContext().getString(R.string.config_defaultOnDeviceIntelligenceService) : AuthService.this.getContext().getString(R.string.kg_wrong_pattern) : AuthService.this.getContext().getString(R.string.lockscreen_permanent_disabled_sim_message_short) : AuthService.this.getContext().getString(17042722) : null;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return string;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final long getLastAuthenticationTime(int i, int i2) {
            if (i != UserHandle.getCallingUserId()) {
                AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            } else {
                AuthService.m311$$Nest$mcheckPermission(AuthService.this);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags.lastAuthenticationTime()) {
                    return AuthService.this.mBiometricService.getLastAuthenticationTime(i, i2);
                }
                throw new UnsupportedOperationException();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final CharSequence getPromptMessage(int i, String str, int i2) {
            int callingUserId = UserHandle.getCallingUserId();
            if (i != callingUserId) {
                AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            } else {
                AuthService.m311$$Nest$mcheckPermission(AuthService.this);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int currentModality = AuthService.this.mBiometricService.getCurrentModality(str, i, callingUserId, i2);
                boolean isCredentialRequested = Utils.isCredentialRequested(i2);
                if (currentModality != 1) {
                    currentModality &= -2;
                }
                String string = currentModality != 0 ? currentModality != 1 ? currentModality != 2 ? currentModality != 8 ? isCredentialRequested ? AuthService.this.getContext().getString(R.string.config_defaultTextClassifierPackage) : AuthService.this.getContext().getString(R.string.config_defaultProfcollectReportUploaderAction) : isCredentialRequested ? AuthService.this.getContext().getString(R.string.lock_to_app_unlock_pattern) : AuthService.this.getContext().getString(R.string.language_picker_section_suggested_bilingual) : isCredentialRequested ? AuthService.this.getContext().getString(R.string.managed_profile_app_label) : AuthService.this.getContext().getString(R.string.lockscreen_sound_off_label) : AuthService.this.getContext().getString(17042723) : null;
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return string;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final List getSensorProperties(String str) {
            getSensorProperties_enforcePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return AuthService.this.mInjector.getBiometricService().getSensorProperties(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final CharSequence getSettingName(int i, String str, int i2) {
            String str2;
            if (i != UserHandle.getCallingUserId()) {
                AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            } else {
                AuthService.m311$$Nest$mcheckPermission(AuthService.this);
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int supportedModalities = AuthService.this.mBiometricService.getSupportedModalities(i2);
                if (supportedModalities == 0) {
                    str2 = null;
                } else if (supportedModalities == 1) {
                    str2 = AuthService.this.getContext().getString(17042722);
                } else if (supportedModalities == 2) {
                    str2 = AuthService.this.getContext().getString(R.string.lockscreen_permanent_disabled_sim_message_short);
                } else if (supportedModalities == 4) {
                    str2 = AuthService.this.getContext().getString(R.string.config_defaultOnDeviceIntelligenceService);
                } else if (supportedModalities == 8) {
                    str2 = AuthService.this.getContext().getString(R.string.kg_wrong_pattern);
                } else if ((supportedModalities & 1) == 0) {
                    str2 = AuthService.this.getContext().getString(R.string.config_defaultOnDeviceIntelligenceService);
                } else {
                    int i3 = supportedModalities & (-2);
                    str2 = i3 == 2 ? AuthService.this.getContext().getString(R.string.low_memory) : i3 == 8 ? AuthService.this.getContext().getString(R.string.lock_to_app_unlock_password) : AuthService.this.getContext().getString(R.string.config_defaultSystemCaptionsManagerService);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return str2;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final String getUiPackage() {
            getUiPackage_enforcePermission();
            return AuthService.this.getContext().getResources().getString(AuthService.this.getContext().getResources().getIdentifier("config_biometric_prompt_ui_package", "string", "android"));
        }

        public final boolean hasEnrolledBiometrics(int i, String str) {
            AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return AuthService.this.mBiometricService.hasEnrolledBiometrics(i, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback) {
            AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuthService.this.mBiometricService.invalidateAuthenticatorIds(i, i2, iInvalidationCallback);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) {
            AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            IFingerprintService fingerprintService = AuthService.this.mInjector.getFingerprintService();
            if (fingerprintService != null) {
                fingerprintService.registerAuthenticationStateListener(authenticationStateListener);
            }
            IFaceService faceService = AuthService.this.mInjector.getFaceService();
            if (faceService != null) {
                faceService.registerAuthenticationStateListener(authenticationStateListener);
            }
        }

        public final void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) {
            AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuthService.this.mBiometricService.registerEnabledOnKeyguardCallback(iBiometricEnabledOnKeyguardCallback);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void resetLockout(int i, byte[] bArr) {
            AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuthService.this.mBiometricService.resetLockout(i, bArr);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr) {
            AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                AuthService.this.mBiometricService.resetLockoutTimeBound(iBinder, str, i, i2, bArr);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) {
            AuthService.m310$$Nest$mcheckInternalPermission(AuthService.this);
            IFingerprintService fingerprintService = AuthService.this.mInjector.getFingerprintService();
            if (fingerprintService != null) {
                fingerprintService.unregisterAuthenticationStateListener(authenticationStateListener);
            }
            IFaceService faceService = AuthService.this.mInjector.getFaceService();
            if (faceService != null) {
                faceService.unregisterAuthenticationStateListener(authenticationStateListener);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Injector {
        public AppOpsManager getAppOps(Context context) {
            return (AppOpsManager) context.getSystemService(AppOpsManager.class);
        }

        public IBiometricService getBiometricService() {
            return IBiometricService.Stub.asInterface(ServiceManager.getService("biometric"));
        }

        public String[] getConfiguration(Context context) {
            boolean z = Utils.DEBUG;
            ArrayList arrayList = new ArrayList();
            if (SemBiometricFeature.FP_FEATURE_SUPPORT_FINGERPRINT) {
                int i = SystemProperties.getInt("ro.board.first_api_level", 0);
                int i2 = Build.VERSION.DEVICE_INITIAL_SDK_INT;
                if ((i == 0 && i2 < 33) || i < 33 || SemBiometricFeature.FEATURE_FINGERPRINT_JDM_HAL) {
                    arrayList.add("0:2:15");
                }
            }
            arrayList.add("1:8:" + (SemBiometricFeature.FEATURE_JDM_HAL ? 4095 : IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT));
            return (String[]) arrayList.toArray(new String[0]);
        }

        public String[] getFaceAidlInstances() {
            return ServiceManager.getDeclaredInstances(IFace.DESCRIPTOR);
        }

        public String[] getFaceConfiguration(Context context) {
            return getConfiguration(context);
        }

        public IFaceService getFaceService() {
            return IFaceService.Stub.asInterface(ServiceManager.getService("face"));
        }

        public String[] getFingerprintAidlInstances() {
            return ServiceManager.getDeclaredInstances(IFingerprint.DESCRIPTOR);
        }

        public String[] getFingerprintConfiguration(Context context) {
            return getConfiguration(context);
        }

        public IFingerprintService getFingerprintService() {
            return IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint"));
        }

        public String[] getIrisConfiguration(Context context) {
            return getConfiguration(context);
        }

        public IIrisService getIrisService() {
            return IIrisService.Stub.asInterface(ServiceManager.getService("iris"));
        }

        public boolean isHidlDisabled(Context context) {
            return (Build.IS_ENG || Build.IS_USERDEBUG) && Settings.Secure.getIntForUser(context.getContentResolver(), "com.android.server.biometrics.AuthService.hidlDisabled", 0, -2) == 1;
        }

        public void publishBinderService(AuthService authService, IAuthService.Stub stub) {
            authService.publishBinderService("auth", stub);
        }
    }

    /* renamed from: -$$Nest$mcheckInternalPermission, reason: not valid java name */
    public static void m310$$Nest$mcheckInternalPermission(AuthService authService) {
        authService.getContext().enforceCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL", "Must have USE_BIOMETRIC_INTERNAL permission");
    }

    /* renamed from: -$$Nest$mcheckPermission, reason: not valid java name */
    public static void m311$$Nest$mcheckPermission(AuthService authService) {
        if (authService.getContext().checkCallingOrSelfPermission("android.permission.USE_FINGERPRINT") != 0) {
            authService.getContext().enforceCallingOrSelfPermission("android.permission.USE_BIOMETRIC", "Must have USE_BIOMETRIC permission");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthService(Context context) {
        super(context);
        Injector injector = new Injector();
        this.mInjector = injector;
        this.mImpl = new AuthServiceImpl();
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
            strArr[i] = String.join(":", String.valueOf(i), (String) arrayList.get(i), valueOf);
        }
        BootReceiver$$ExternalSyntheticOutline0.m(new StringBuilder("Generated config_biometric_sensors: "), Arrays.toString(strArr), "AuthService");
        return strArr;
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        String[] strArr;
        int i;
        SensorConfig[] sensorConfigArr;
        int i2;
        Injector injector = this.mInjector;
        this.mBiometricService = injector.getBiometricService();
        int i3 = 0;
        if (!injector.isHidlDisabled(getContext())) {
            int i4 = SystemProperties.getInt("ro.board.api_level", SystemProperties.getInt("ro.board.first_api_level", 0));
            String[] configuration = injector.getConfiguration(getContext());
            if (configuration.length == 0 && i4 == 30) {
                Slog.w("AuthService", "Found R vendor partition without config_biometric_sensors");
                configuration = generateRSdkCompatibleConfiguration();
            }
            SensorConfig[] sensorConfigArr2 = new SensorConfig[configuration.length];
            for (int i5 = 0; i5 < configuration.length; i5++) {
                sensorConfigArr2[i5] = new SensorConfig(configuration[i5]);
            }
        }
        injector.getClass();
        BiometricHandlerProvider biometricHandlerProvider = BiometricHandlerProvider.sBiometricHandlerProvider;
        final String[] fingerprintAidlInstances = injector.getFingerprintAidlInstances();
        String[] fingerprintConfiguration = injector.getFingerprintConfiguration(getContext());
        final Context context = getContext();
        final IFingerprintService fingerprintService = injector.getFingerprintService();
        boolean z = Utils.DEBUG;
        if (fingerprintConfiguration == null || fingerprintConfiguration.length == 0) {
            strArr = new String[0];
        } else {
            ArrayList arrayList = new ArrayList();
            int length = fingerprintConfiguration.length;
            int i6 = 0;
            while (i6 < length) {
                String str = fingerprintConfiguration[i6];
                if (str != null && str.contains(":2:")) {
                    arrayList.add(str);
                }
                i6++;
                i3 = 0;
            }
            strArr = (String[]) arrayList.toArray(new String[i3]);
        }
        final String[] strArr2 = strArr;
        final boolean z2 = fingerprintAidlInstances != null && fingerprintAidlInstances.length > 0;
        final boolean z3 = strArr2.length > 0;
        if (z3 || z2) {
            biometricHandlerProvider.getFingerprintHandler().post(new Runnable() { // from class: com.android.server.biometrics.AuthService$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    boolean z4 = z2;
                    String[] strArr3 = fingerprintAidlInstances;
                    String[] strArr4 = strArr2;
                    boolean z5 = z3;
                    Context context2 = context;
                    IFingerprintService iFingerprintService = fingerprintService;
                    if (Utils.DEBUG) {
                        StringBuilder sb = new StringBuilder("registerFingerprintSensors: {");
                        int i7 = 0;
                        if (z4) {
                            sb.append("aidl=");
                            int length2 = strArr3.length;
                            while (i7 < length2) {
                                sb.append(strArr3[i7]);
                                sb.append(", ");
                                i7++;
                            }
                        } else {
                            sb.append("hidl=");
                            int length3 = strArr4.length;
                            while (i7 < length3) {
                                sb.append(strArr4[i7]);
                                sb.append(", ");
                                i7++;
                            }
                        }
                        BootReceiver$$ExternalSyntheticOutline0.m(sb, "}", "AuthService");
                    }
                    FingerprintSensorConfigurations fingerprintSensorConfigurations = new FingerprintSensorConfigurations(z5);
                    if (z4) {
                        fingerprintSensorConfigurations.addAidlSensors(strArr3);
                    } else {
                        fingerprintSensorConfigurations.addHidlSensors(strArr4, context2);
                    }
                    if (iFingerprintService == null) {
                        if (fingerprintSensorConfigurations.hasSensorConfigurations()) {
                            Slog.e("AuthService", "Fingerprint configuration exists, but FingerprintService is null.");
                        }
                    } else {
                        try {
                            iFingerprintService.registerAuthenticators(fingerprintSensorConfigurations);
                        } catch (RemoteException e) {
                            Slog.e("AuthService", "RemoteException when registering fingerprint authenticators", e);
                        }
                    }
                }
            });
        } else {
            Slog.d("AuthService", "No fingerprint sensors.");
        }
        final String[] faceAidlInstances = injector.getFaceAidlInstances();
        final String[] faceConfiguration = injector.getFaceConfiguration(getContext());
        final Context context2 = getContext();
        final IFaceService faceService = injector.getFaceService();
        if ((faceConfiguration == null || faceConfiguration.length == 0) && (faceAidlInstances == null || faceAidlInstances.length == 0)) {
            Slog.d("AuthService", "No face sensors.");
        } else {
            biometricHandlerProvider.getFaceHandler().post(new Runnable() { // from class: com.android.server.biometrics.AuthService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    String[] strArr3 = faceAidlInstances;
                    String[] strArr4 = faceConfiguration;
                    Context context3 = context2;
                    IFaceService iFaceService = faceService;
                    FaceSensorConfigurations faceSensorConfigurations = new FaceSensorConfigurations(false);
                    if (strArr3 != null && strArr3.length > 0) {
                        faceSensorConfigurations.addAidlConfigs(strArr3);
                    }
                    if (!faceSensorConfigurations.hasSensorConfigurations() && strArr4 != null && strArr4.length > 0) {
                        faceSensorConfigurations.addHidlConfigs(strArr4, context3);
                    }
                    if (iFaceService == null) {
                        if (faceSensorConfigurations.hasSensorConfigurations()) {
                            Slog.e("AuthService", "Face configuration exists, but FaceService is null.");
                        }
                    } else {
                        try {
                            iFaceService.registerAuthenticators(faceSensorConfigurations);
                        } catch (RemoteException e) {
                            Slog.e("AuthService", "RemoteException when registering face authenticators", e);
                        }
                    }
                }
            });
        }
        String[] irisConfiguration = injector.getIrisConfiguration(getContext());
        if (injector.isHidlDisabled(getContext())) {
            i = 0;
            sensorConfigArr = null;
        } else {
            i = 0;
            int i7 = SystemProperties.getInt("ro.board.api_level", SystemProperties.getInt("ro.board.first_api_level", 0));
            if (irisConfiguration.length == 0 && i7 == 30) {
                Slog.w("AuthService", "Found R vendor partition without config_biometric_sensors");
                irisConfiguration = generateRSdkCompatibleConfiguration();
            }
            sensorConfigArr = new SensorConfig[irisConfiguration.length];
            for (int i8 = 0; i8 < irisConfiguration.length; i8++) {
                sensorConfigArr[i8] = new SensorConfig(irisConfiguration[i8]);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        if (sensorConfigArr != null) {
            int length2 = sensorConfigArr.length;
            for (int i9 = i; i9 < length2; i9++) {
                SensorConfig sensorConfig = sensorConfigArr[i9];
                if (sensorConfig.modality != 4) {
                    VaultKeeperService$$ExternalSyntheticOutline0.m(new StringBuilder("Unknown modality: "), sensorConfig.modality, "AuthService");
                } else {
                    ArrayList arrayList3 = new ArrayList();
                    boolean z4 = Utils.DEBUG;
                    int i10 = sensorConfig.strength;
                    if (i10 == 15) {
                        i2 = 2;
                    } else if (i10 != 255) {
                        if (i10 != 4095) {
                            DeviceIdleController$$ExternalSyntheticOutline0.m(i10, "authenticatorStrengthToPropertyStrength: Unknown strength ", "BiometricUtils");
                        }
                        i2 = i;
                    } else {
                        i2 = 1;
                    }
                    arrayList2.add(new SensorPropertiesInternal(sensorConfig.id, i2, 1, arrayList3, false, false));
                }
            }
        }
        IIrisService irisService = injector.getIrisService();
        if (irisService != null) {
            try {
                irisService.registerAuthenticators(arrayList2);
            } catch (RemoteException e) {
                Slog.e("AuthService", "RemoteException when registering iris authenticators", e);
            }
        } else if (arrayList2.size() > 0) {
            Slog.e("AuthService", "HIDL iris configuration exists, but IrisService is null.");
        }
        injector.publishBinderService(this, this.mImpl);
    }
}
