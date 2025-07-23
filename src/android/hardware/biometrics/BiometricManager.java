package android.hardware.biometrics;

import android.annotation.SystemApi;
import android.content.Context;
import android.hardware.biometrics.BiometricTestSession;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class BiometricManager {
    public static final int BIOMETRIC_ERROR_HW_UNAVAILABLE = 1;
    public static final int BIOMETRIC_ERROR_IDENTITY_CHECK_NOT_ACTIVE = 20;
    public static final int BIOMETRIC_ERROR_LOCKOUT = 7;
    public static final int BIOMETRIC_ERROR_NONE_ENROLLED = 11;
    public static final int BIOMETRIC_ERROR_NOT_ENABLED_FOR_APPS = 21;
    public static final int BIOMETRIC_ERROR_NO_HARDWARE = 12;
    public static final int BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED = 15;
    public static final long BIOMETRIC_NO_AUTHENTICATION = -1;
    public static final int BIOMETRIC_SUCCESS = 0;
    public static final String EXTRA_ENROLL_REASON = "enroll_reason";
    private static final int GET_LAST_AUTH_TIME_ALLOWED_AUTHENTICATORS = 32783;
    private static final String TAG = "BiometricManager";
    private final Context mContext;
    private final IAuthService mService;

    public interface Authenticators {

        @SystemApi
        public static final int BIOMETRIC_CONVENIENCE = 4095;
        public static final int BIOMETRIC_MAX_STRENGTH = 1;
        public static final int BIOMETRIC_MIN_STRENGTH = 32767;
        public static final int BIOMETRIC_STRONG = 15;
        public static final int BIOMETRIC_WEAK = 255;
        public static final int DEVICE_CREDENTIAL = 32768;

        @SystemApi
        public static final int EMPTY_SET = 0;
        public static final int IDENTITY_CHECK = 65536;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Types {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BiometricError {
    }

    public static String authenticatorToStr(int i) {
        if (i == 15) {
            return "BIOMETRIC_STRONG";
        }
        if (i == 255) {
            return "BIOMETRIC_WEAK";
        }
        if (i == 4095) {
            return "BIOMETRIC_CONVENIENCE";
        }
        if (i == 32768) {
            return "DEVICE_CREDENTIAL";
        }
        return "Unknown authenticator type: " + i;
    }

    public static class Strings {
        int mAuthenticators;
        private final Context mContext;
        private final IAuthService mService;

        private Strings(Context context, IAuthService iAuthService, int i) {
            this.mContext = context;
            this.mService = iAuthService;
            this.mAuthenticators = i;
        }

        public CharSequence getButtonLabel() {
            try {
                return this.mService.getButtonLabel(this.mContext.getUserId(), this.mContext.getOpPackageName(), this.mAuthenticators);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public CharSequence getPromptMessage() {
            try {
                return this.mService.getPromptMessage(this.mContext.getUserId(), this.mContext.getOpPackageName(), this.mAuthenticators);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public CharSequence getSettingName() {
            try {
                return this.mService.getSettingName(this.mContext.getUserId(), this.mContext.getOpPackageName(), this.mAuthenticators);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public BiometricManager(Context context, IAuthService iAuthService) {
        this.mContext = context;
        this.mService = iAuthService;
    }

    public List<SensorProperties> getSensorProperties() {
        try {
            List<SensorPropertiesInternal> sensorProperties = this.mService.getSensorProperties(this.mContext.getOpPackageName());
            ArrayList arrayList = new ArrayList();
            Iterator<SensorPropertiesInternal> it = sensorProperties.iterator();
            while (it.hasNext()) {
                arrayList.add(SensorProperties.from(it.next()));
            }
            return arrayList;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public BiometricTestSession createTestSession(int i) {
        try {
            return new BiometricTestSession(this.mContext, getSensorProperties(), i, new BiometricTestSession.TestSessionProvider() { // from class: android.hardware.biometrics.BiometricManager$$ExternalSyntheticLambda0
                @Override // android.hardware.biometrics.BiometricTestSession.TestSessionProvider
                public final ITestSession createTestSession(Context context, int i2, ITestSessionCallback iTestSessionCallback) {
                    ITestSession lambda$createTestSession$0;
                    lambda$createTestSession$0 = BiometricManager.this.lambda$createTestSession$0(context, i2, iTestSessionCallback);
                    return lambda$createTestSession$0;
                }
            });
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ITestSession lambda$createTestSession$0(Context context, int i, ITestSessionCallback iTestSessionCallback) throws RemoteException {
        return this.mService.createTestSession(i, iTestSessionCallback, context.getOpPackageName());
    }

    public String getUiPackage() {
        try {
            return this.mService.getUiPackage();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int canAuthenticate() {
        int canAuthenticate = canAuthenticate(this.mContext.getUserId(), 255);
        FrameworkStatsLog.write(354, false, 0, canAuthenticate);
        FrameworkStatsLog.write(356, 4, this.mContext.getApplicationInfo().uid, this.mContext.getApplicationInfo().targetSdkVersion);
        return canAuthenticate;
    }

    public int canAuthenticate(int i) {
        int canAuthenticate = canAuthenticate(this.mContext.getUserId(), i);
        FrameworkStatsLog.write(354, true, i, canAuthenticate);
        return canAuthenticate;
    }

    public int canAuthenticate(int i, int i2) {
        if (this.mService != null) {
            try {
                return this.mService.canAuthenticate(this.mContext.getOpPackageName(), i, i2);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "canAuthenticate(): Service not connected");
        return 1;
    }

    public Strings getStrings(int i) {
        return new Strings(this.mContext, this.mService, i);
    }

    public boolean hasEnrolledBiometrics(int i) {
        IAuthService iAuthService = this.mService;
        if (iAuthService != null) {
            try {
                return iAuthService.hasEnrolledBiometrics(i, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                Slog.w(TAG, "Remote exception in hasEnrolledBiometrics(): " + e);
            }
        }
        return false;
    }

    public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) {
        IAuthService iAuthService = this.mService;
        if (iAuthService != null) {
            try {
                iAuthService.registerEnabledOnKeyguardCallback(iBiometricEnabledOnKeyguardCallback);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "registerEnabledOnKeyguardCallback(): Service not connected");
    }

    public void registerAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) {
        IAuthService iAuthService = this.mService;
        if (iAuthService != null) {
            try {
                iAuthService.registerAuthenticationStateListener(authenticationStateListener);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "registerAuthenticationStateListener(): Service not connected");
    }

    public void unregisterAuthenticationStateListener(AuthenticationStateListener authenticationStateListener) {
        IAuthService iAuthService = this.mService;
        if (iAuthService != null) {
            try {
                iAuthService.unregisterAuthenticationStateListener(authenticationStateListener);
                return;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "unregisterAuthenticationStateListener(): Service not connected");
    }

    public void invalidateAuthenticatorIds(int i, int i2, IInvalidationCallback iInvalidationCallback) {
        IAuthService iAuthService = this.mService;
        if (iAuthService != null) {
            try {
                iAuthService.invalidateAuthenticatorIds(i, i2, iInvalidationCallback);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long[] getAuthenticatorIds() {
        return getAuthenticatorIds(UserHandle.myUserId());
    }

    public long[] getAuthenticatorIds(int i) {
        IAuthService iAuthService = this.mService;
        if (iAuthService != null) {
            try {
                return iAuthService.getAuthenticatorIds(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Slog.w(TAG, "getAuthenticatorIds(): Service not connected");
        return new long[0];
    }

    public void resetLockoutTimeBound(IBinder iBinder, String str, int i, int i2, byte[] bArr) {
        IAuthService iAuthService = this.mService;
        if (iAuthService != null) {
            try {
                iAuthService.resetLockoutTimeBound(iBinder, str, i, i2, bArr);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void resetLockout(int i, byte[] bArr) {
        IAuthService iAuthService = this.mService;
        if (iAuthService != null) {
            try {
                iAuthService.resetLockout(i, bArr);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public long getLastAuthenticationTime(int i) {
        if (i == 0 || (GET_LAST_AUTH_TIME_ALLOWED_AUTHENTICATORS & i) != i) {
            throw new IllegalArgumentException("Only BIOMETRIC_STRONG and DEVICE_CREDENTIAL authenticators may be used.");
        }
        IAuthService iAuthService = this.mService;
        if (iAuthService == null) {
            return -1L;
        }
        try {
            return iAuthService.getLastAuthenticationTime(UserHandle.myUserId(), i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
