package com.samsung.android.knox.zt.networktrust.filter.serviceprovider;

import android.R;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.IVpnManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.LimitExceededException;
import android.os.Process;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.container.RCPPolicy$$ExternalSyntheticOutline0;
import com.samsung.android.knox.zt.networktrust.filter.IKnoxNetworkFilterService;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class NetworkFilterService {
    public static final String ACTION_NOTIFY_STATUS = "com.samsung.android.knox.intent.action.NOTIFY_STATUS";
    public static final String BUNDLE_EXTRA_CONFIG_VIEWER_KEY = "ConfigViewer";
    public static final String BUNDLE_EXTRA_UNENROLL_VIEWER_KEY = "UnEnrollViewer";
    public static final int ERROR_APP_LIMIT_REACHED = -17;
    public static final int ERROR_BAD_STATE = -10;
    public static final int ERROR_INTERNAL = -8;
    public static final int ERROR_INVALID_JSON_FORMAT = -3;
    public static final int ERROR_MANAGED_USER_NOT_SUPPORTED = -14;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NULL_PARAMETER = -7;
    public static final int ERROR_PACKAGE_NOT_REGISTERED = -5;
    public static final int ERROR_PROFILE_LIMIT_REACHED = -6;
    public static final int ERROR_PROFILE_NAME_EXISTS_DIFFERENT_OWNER = -12;
    public static final int ERROR_PROFILE_NOT_FOUND = -2;
    public static final int ERROR_PROFILE_RUNNING = -9;
    public static final int ERROR_REGION_NOT_SUPPORTED = -15;
    public static final int ERROR_USER_DISAGREE = -11;
    public static final int ERROR_USER_NOT_SUPPORTED = -13;
    public static final int ERROR_USER_REGISTERED = -16;
    public static final String EXTRA_ERROR_CODE = "com.samsung.android.knox.intent.extra.ERROR_CODE";
    public static final String EXTRA_STATUS = "com.samsung.android.knox.intent.extra.STATUS";
    public static final String EXTRA_TYPE = "com.samsung.android.knox.intent.extra.TYPE";
    public static final int RESULT_API_PAUSE = 102;
    public static final int RESULT_API_REMOVED = 103;
    public static final int RESULT_API_START = 100;
    public static final int RESULT_API_STOP = 101;
    public static final int STATUS_PACKAGE_AUTHORIZED = 7;
    public static final int STATUS_PACKAGE_REGISTERED = 1;
    public static final int STATUS_PACKAGE_UNREGISTERED = 2;
    public static final int STATUS_PROFILE_IDLE = 6;
    public static final int STATUS_PROFILE_PAUSED = 4;
    public static final int STATUS_PROFILE_RUNNING = 3;
    public static final int STATUS_PROFILE_STOPPED = 5;
    public static final String TAG = "knoxNwFilter-NetworkFilterService";
    public static final int TYPE_BOOT_COMPLETED = 2;
    public static final int TYPE_LOCK_BOOT_COMPLETED = 1;
    public static NetworkFilterService mNfs;
    public static IKnoxNetworkFilterService mNwFilterMgrService;
    public static final Object mSync = new Object();
    public Context mContext;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class UnSupportedManagedUserException extends RuntimeException {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class UnSupportedRegionException extends RuntimeException {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class UnSupportedUserException extends RuntimeException {
    }

    private NetworkFilterService(Context context) {
        this.mContext = context;
    }

    public static void enforceCountryIsoCode() {
        String countryIso = SemSystemProperties.getCountryIso();
        Log.i(TAG, "getCountryIso  " + countryIso);
        countryIso.getClass();
    }

    public static void enforceUser(Context context) throws UnSupportedUserException, UnSupportedManagedUserException {
        int userId = UserHandle.getUserId(Process.myUid());
        try {
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException " + e);
        }
        if (IVpnManager.Stub.asInterface(ServiceManager.getService("vpn_management")).isDoEnabled(userId)) {
            Log.e(TAG, "prepare filtering failed since device owner is configured for user " + userId);
            throw new UnSupportedManagedUserException();
        }
        boolean isOrganizationOwnedDeviceWithManagedProfile = ((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class)).isOrganizationOwnedDeviceWithManagedProfile();
        if (userId == 0) {
            if (isOrganizationOwnedDeviceWithManagedProfile) {
                Log.e(TAG, "prepare filtering failed since wpc configured for user " + userId);
                throw new UnSupportedUserException();
            }
            return;
        }
        if (isOrganizationOwnedDeviceWithManagedProfile) {
            Log.e(TAG, "prepare filtering failed since wpc configured for user " + userId);
            throw new UnSupportedManagedUserException();
        }
        if (SemPersonaManager.isSecureFolderId(userId)) {
            Log.e(TAG, "prepare filtering failed since user is secure folder user " + userId);
            throw new UnSupportedUserException();
        }
        if (((UserManager) context.getSystemService(UserManager.class)).isManagedProfile()) {
            Log.e(TAG, "prepare filtering failed since wpp configured for user " + userId);
            throw new UnSupportedManagedUserException();
        }
        Log.e(TAG, "prepare filtering failed since user is unmanaged other than primary user " + userId);
        throw new UnSupportedUserException();
    }

    public static NetworkFilterService getInstance(Context context) {
        NetworkFilterService networkFilterService;
        if (getService() == null) {
            Log.e(TAG, "getInstance API failed since service not started yet");
            return null;
        }
        try {
            if (mNwFilterMgrService.getInstanceValidation() != 0) {
                throw new SecurityException();
            }
            synchronized (mSync) {
                try {
                    if (mNfs == null) {
                        Log.i(TAG, "getInstance API creating new object");
                        mNfs = new NetworkFilterService(context);
                    }
                    networkFilterService = mNfs;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return networkFilterService;
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("unknown error occured while fetching the instance "), TAG);
            return null;
        }
    }

    public static Intent getIntentForConfirmation(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        Intent intent = new Intent();
        intent.putExtras(bundle);
        ComponentName unflattenFromString = ComponentName.unflattenFromString(Resources.getSystem().getString(R.string.expires_on));
        intent.setClassName(unflattenFromString.getPackageName(), unflattenFromString.getClassName());
        return intent;
    }

    public static IKnoxNetworkFilterService getService() {
        if (mNwFilterMgrService == null) {
            mNwFilterMgrService = IKnoxNetworkFilterService.Stub.asInterface(ServiceManager.getService("knox_nwFilterMgr_policy"));
        }
        return mNwFilterMgrService;
    }

    public static int getVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.samsung.android.knox.app.networkfilter", 0).versionCode;
        } catch (PackageManager.NameNotFoundException | NullPointerException unused) {
            return -1;
        }
    }

    public static Intent prepare(Context context, Bundle bundle) throws UnSupportedUserException, UnSupportedManagedUserException, UnSupportedRegionException, SecurityException, LimitExceededException {
        boolean z;
        try {
            enforceUser(context);
            z = false;
        } catch (UnSupportedManagedUserException unused) {
            z = true;
        }
        if (!z) {
            enforceCountryIsoCode();
        }
        if (getService() == null) {
            Log.e(TAG, "Failed due to service null during isAuthorized call");
            throw new SecurityException("service not started");
        }
        try {
            if (mNwFilterMgrService.isAuthorized()) {
                return null;
            }
            try {
                if (mNwFilterMgrService.isUserLimitReached(z)) {
                    Log.e(TAG, "prepare filtering failed since user limit reached");
                    throw new LimitExceededException();
                }
                if (!z) {
                    return getIntentForConfirmation(bundle);
                }
                try {
                    int prepareFiltering = mNwFilterMgrService.prepareFiltering(context.getPackageName(), bundle);
                    Log.i(TAG, "prepare: packageName: " + context.getPackageName() + " result: " + prepareFiltering);
                    if (prepareFiltering != -16) {
                        showManagedUserDialog(context);
                        return null;
                    }
                    Log.e(TAG, "prepare filtering failed KSP/EMM has already configured the user");
                    throw new UnSupportedManagedUserException();
                } catch (RemoteException unused2) {
                    Log.e(TAG, "Failed due to remote exception during prepareFiltering call");
                    throw new SecurityException("internal error");
                }
            } catch (RemoteException unused3) {
                Log.e(TAG, "Failed due to remote exception during isUserLimitReached call");
                throw new SecurityException("internal error");
            }
        } catch (RemoteException unused4) {
            Log.e(TAG, "Failed due to remote exception during isAuthorized call");
            throw new SecurityException("internal error");
        }
    }

    public static void showManagedUserDialog(Context context) {
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        Intent intent = new Intent();
        ComponentName unflattenFromString = ComponentName.unflattenFromString(Resources.getSystem().getString(R.string.ext_media_badremoval_notification_message));
        intent.setClassName(unflattenFromString.getPackageName(), unflattenFromString.getClassName());
        intent.addFlags(1350565888);
        Log.i(TAG, "startActivityAsUser  KnoxVpnPPDialog userId = " + userId);
        context.startActivityAsUser(intent, new UserHandle(userId));
    }

    public List<String> getAllProfiles() {
        if (getService() == null) {
            Log.e(TAG, "getAllProfiles API failed since service not started yet");
            return null;
        }
        try {
            return mNwFilterMgrService.getAllProfiles();
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed to getAllProfiles"), TAG);
            return null;
        }
    }

    public String getConfig(String str) {
        if (getService() == null) {
            Log.e(TAG, "getConfig API failed since service not started yet");
            return null;
        }
        try {
            return mNwFilterMgrService.getConfig(str);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed to getConfig"), TAG);
            return null;
        }
    }

    public int getProfileStatus(String str) {
        if (getService() == null) {
            Log.e(TAG, "getProfileStatus API failed since service not started yet");
            return -8;
        }
        try {
            return mNwFilterMgrService.getProfileStatus(str);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at getProfileStatus"), TAG);
            return -8;
        }
    }

    public String getRegisteredListeners(String str) {
        if (getService() == null) {
            Log.e(TAG, "getRegisteredListeners API failed since service not started yet");
            return null;
        }
        try {
            return mNwFilterMgrService.getRegisteredListeners(str);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at getRegisteredListeners"), TAG);
            return null;
        }
    }

    public int pause(String str) {
        if (getService() == null) {
            Log.e(TAG, "pause API failed since service not started yet");
            return -8;
        }
        try {
            return mNwFilterMgrService.pause(str);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed to pause"), TAG);
            return -8;
        }
    }

    public int registerListeners(String str, String str2) {
        if (getService() == null) {
            Log.e(TAG, "registerListeners API failed since service not started yet");
            return -8;
        }
        try {
            return mNwFilterMgrService.registerListeners(str, str2);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed at registerListeners"), TAG);
            return -8;
        }
    }

    public int setConfig(String str, String str2) {
        if (getService() == null) {
            Log.e(TAG, "setConfig API failed since service not started yet");
            return -8;
        }
        try {
            return mNwFilterMgrService.setConfig(str, str2);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed to setConfig "), TAG);
            return -8;
        }
    }

    public int start(String str) {
        if (getService() == null) {
            Log.e(TAG, "start API failed since service not started yet");
            return -8;
        }
        try {
            return mNwFilterMgrService.start(str);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed to start"), TAG);
            return -8;
        }
    }

    public int stop(String str, String str2) {
        if (getService() == null) {
            Log.e(TAG, "stop API failed since service not started yet");
            return -8;
        }
        try {
            return mNwFilterMgrService.stop(str, str2);
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(e, new StringBuilder("Failed to stop"), TAG);
            return -8;
        }
    }
}
