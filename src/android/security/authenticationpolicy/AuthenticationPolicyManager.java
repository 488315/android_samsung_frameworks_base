package android.security.authenticationpolicy;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.RemoteException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class AuthenticationPolicyManager {

    @SystemApi
    public static final int ERROR_ALREADY_ENABLED = 6;

    @SystemApi
    public static final int ERROR_INSUFFICIENT_BIOMETRICS = 5;

    @SystemApi
    public static final int ERROR_INVALID_PARAMS = 3;

    @SystemApi
    public static final int ERROR_NO_BIOMETRICS_ENROLLED = 4;

    @SystemApi
    public static final int ERROR_UNKNOWN = 0;

    @SystemApi
    public static final int ERROR_UNSUPPORTED = 2;

    @SystemApi
    public static final int SUCCESS = 1;
    private static final String TAG = "AuthenticationPolicyManager";
    private final IAuthenticationPolicyService mAuthenticationPolicyService;
    private final Context mContext;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisableSecureLockDeviceRequestStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EnableSecureLockDeviceRequestStatus {
    }

    public AuthenticationPolicyManager(Context context, IAuthenticationPolicyService iAuthenticationPolicyService) {
        this.mContext = context;
        this.mAuthenticationPolicyService = iAuthenticationPolicyService;
    }

    @SystemApi
    public int enableSecureLockDevice(EnableSecureLockDeviceParams enableSecureLockDeviceParams) {
        try {
            return this.mAuthenticationPolicyService.enableSecureLockDevice(enableSecureLockDeviceParams);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int disableSecureLockDevice(DisableSecureLockDeviceParams disableSecureLockDeviceParams) {
        try {
            return this.mAuthenticationPolicyService.disableSecureLockDevice(disableSecureLockDeviceParams);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
