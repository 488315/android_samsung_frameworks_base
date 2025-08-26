package android.telephony;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags;
import com.android.internal.telephony.TelephonyPermissions;
import com.android.internal.telephony.util.TelephonyUtils;

/* loaded from: classes4.dex */
public final class LocationAccessPolicy {
    private static final boolean DBG = false;
    public static final int MAX_SDK_FOR_ANY_ENFORCEMENT = 10000;
    private static final String TAG = "LocationAccessPolicy";

    public enum LocationPermissionResult {
        ALLOWED,
        DENIED_SOFT,
        DENIED_HARD
    }

    public static class LocationPermissionQuery {
        public final String callingFeatureId;
        public final String callingPackage;
        public final int callingPid;
        public final int callingUid;
        public final boolean logAsInfo;
        public final String method;
        public final int minSdkVersionForCoarse;
        public final int minSdkVersionForFine;

        private LocationPermissionQuery(String str, String str2, int i, int i2, int i3, int i4, boolean z, String str3) {
            this.callingPackage = str;
            this.callingFeatureId = str2;
            this.callingUid = i;
            this.callingPid = i2;
            this.minSdkVersionForCoarse = i3;
            this.minSdkVersionForFine = i4;
            this.logAsInfo = z;
            this.method = str3;
        }

        public static class Builder {
            private String mCallingFeatureId;
            private String mCallingPackage;
            private int mCallingPid;
            private int mCallingUid;
            private String mMethod;
            private int mMinSdkVersionForCoarse = -1;
            private int mMinSdkVersionForFine = -1;
            private int mMinSdkVersionForEnforcement = -1;
            private boolean mLogAsInfo = false;

            public Builder setCallingPackage(String str) {
                this.mCallingPackage = str;
                return this;
            }

            public Builder setCallingFeatureId(String str) {
                this.mCallingFeatureId = str;
                return this;
            }

            public Builder setCallingUid(int i) {
                this.mCallingUid = i;
                return this;
            }

            public Builder setCallingPid(int i) {
                this.mCallingPid = i;
                return this;
            }

            public Builder setMinSdkVersionForCoarse(int i) {
                this.mMinSdkVersionForCoarse = i;
                return this;
            }

            public Builder setMinSdkVersionForFine(int i) {
                this.mMinSdkVersionForFine = i;
                return this;
            }

            public Builder setMinSdkVersionForEnforcement(int i) {
                this.mMinSdkVersionForEnforcement = i;
                return this;
            }

            public Builder setMethod(String str) {
                this.mMethod = str;
                return this;
            }

            public Builder setLogAsInfo(boolean z) {
                this.mLogAsInfo = z;
                return this;
            }

            public LocationPermissionQuery build() {
                int i;
                int i2 = this.mMinSdkVersionForCoarse;
                if (i2 < 0 || (i = this.mMinSdkVersionForFine) < 0) {
                    throw new IllegalArgumentException("Must specify min sdk versions for enforcement for both coarse and fine permissions");
                }
                if (i > 1 && i2 > 1 && this.mMinSdkVersionForEnforcement != Math.min(i2, i)) {
                    throw new IllegalArgumentException("setMinSdkVersionForEnforcement must be called.");
                }
                if (this.mMinSdkVersionForFine < this.mMinSdkVersionForCoarse) {
                    throw new IllegalArgumentException("Since fine location permission includes access to coarse location, the min sdk level for enforcement of the fine location permission must not be less than the min sdk level for enforcement of the coarse location permission.");
                }
                return new LocationPermissionQuery(this.mCallingPackage, this.mCallingFeatureId, this.mCallingUid, this.mCallingPid, this.mMinSdkVersionForCoarse, this.mMinSdkVersionForFine, this.mLogAsInfo, this.mMethod);
            }
        }
    }

    private static void logError(Context context, LocationPermissionQuery locationPermissionQuery, String str) {
        if (locationPermissionQuery.logAsInfo) {
            Log.i(TAG, str);
            return;
        }
        Log.e(TAG, str);
        try {
            if (TelephonyUtils.IS_DEBUGGABLE) {
                Toast.makeText(context, str, 0).show();
            }
        } catch (Throwable unused) {
        }
    }

    private static LocationPermissionResult appOpsModeToPermissionResult(int i) {
        if (i == 0) {
            return LocationPermissionResult.ALLOWED;
        }
        if (i == 2) {
            return LocationPermissionResult.DENIED_HARD;
        }
        return LocationPermissionResult.DENIED_SOFT;
    }

    private static String getAppOpsString(String str) {
        str.hashCode();
        if (str.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
            return AppOpsManager.OPSTR_FINE_LOCATION;
        }
        if (str.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            return AppOpsManager.OPSTR_COARSE_LOCATION;
        }
        return null;
    }

    private static LocationPermissionResult checkAppLocationPermissionHelper(Context context, LocationPermissionQuery locationPermissionQuery, String str) {
        String str2 = Manifest.permission.ACCESS_FINE_LOCATION.equals(str) ? "fine" : "coarse";
        if (checkManifestPermission(context, locationPermissionQuery.callingPid, locationPermissionQuery.callingUid, str)) {
            int iNoteOpNoThrow = ((AppOpsManager) context.getSystemService(AppOpsManager.class)).noteOpNoThrow(getAppOpsString(str), locationPermissionQuery.callingUid, locationPermissionQuery.callingPackage, locationPermissionQuery.callingFeatureId, (String) null);
            if (iNoteOpNoThrow == 0) {
                return LocationPermissionResult.ALLOWED;
            }
            Log.i(TAG, locationPermissionQuery.callingPackage + " is aware of " + str2 + " but the app-ops permission is specifically denied.");
            return appOpsModeToPermissionResult(iNoteOpNoThrow);
        }
        int i = Manifest.permission.ACCESS_FINE_LOCATION.equals(str) ? locationPermissionQuery.minSdkVersionForFine : locationPermissionQuery.minSdkVersionForCoarse;
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(locationPermissionQuery.callingUid);
        if (i > 10000) {
            logError(context, locationPermissionQuery, "Allowing " + locationPermissionQuery.callingPackage + " " + str2 + " because we're not enforcing API " + i + " yet. Please fix this app because it will break in the future. Called from " + locationPermissionQuery.method);
            return null;
        }
        if (!isAppAtLeastSdkVersion(context, userHandleForUid, locationPermissionQuery.callingPackage, i)) {
            logError(context, locationPermissionQuery, "Allowing " + locationPermissionQuery.callingPackage + " " + str2 + " because it doesn't target API " + i + " yet. Please fix this app. Called from " + locationPermissionQuery.method);
            return null;
        }
        return LocationPermissionResult.DENIED_HARD;
    }

    public static LocationPermissionResult checkLocationPermission(Context context, LocationPermissionQuery locationPermissionQuery) {
        LocationPermissionResult locationPermissionResultCheckAppLocationPermissionHelper;
        LocationPermissionResult locationPermissionResultCheckAppLocationPermissionHelper2;
        if (TelephonyPermissions.isSystemOrPhone(locationPermissionQuery.callingUid) || UserHandle.isSameApp(locationPermissionQuery.callingUid, Process.NETWORK_STACK_UID) || UserHandle.isSameApp(locationPermissionQuery.callingUid, 0)) {
            return LocationPermissionResult.ALLOWED;
        }
        if (!checkSystemLocationAccess(context, locationPermissionQuery.callingUid, locationPermissionQuery.callingPid, locationPermissionQuery.callingPackage)) {
            Log.i(TAG, "checkLocationPermission - callingUid: " + locationPermissionQuery.callingUid + ", callingPid: " + locationPermissionQuery.callingPid + ", result: DENIED_SOFT");
            return LocationPermissionResult.DENIED_SOFT;
        }
        if (locationPermissionQuery.minSdkVersionForFine < Integer.MAX_VALUE && (locationPermissionResultCheckAppLocationPermissionHelper2 = checkAppLocationPermissionHelper(context, locationPermissionQuery, Manifest.permission.ACCESS_FINE_LOCATION)) != null) {
            if (locationPermissionResultCheckAppLocationPermissionHelper2 != LocationPermissionResult.ALLOWED) {
                Log.i(TAG, "checkLocationPermission - callingUid: " + locationPermissionQuery.callingUid + ", callingPid: " + locationPermissionQuery.callingPid + ", resultForFine: " + locationPermissionResultCheckAppLocationPermissionHelper2);
            }
            return locationPermissionResultCheckAppLocationPermissionHelper2;
        }
        if (locationPermissionQuery.minSdkVersionForCoarse < Integer.MAX_VALUE && (locationPermissionResultCheckAppLocationPermissionHelper = checkAppLocationPermissionHelper(context, locationPermissionQuery, Manifest.permission.ACCESS_COARSE_LOCATION)) != null) {
            if (locationPermissionResultCheckAppLocationPermissionHelper != LocationPermissionResult.ALLOWED) {
                Log.i(TAG, "checkLocationPermission - callingUid: " + locationPermissionQuery.callingUid + ", callingPid: " + locationPermissionQuery.callingPid + ", resultForCoarse: " + locationPermissionResultCheckAppLocationPermissionHelper);
            }
            return locationPermissionResultCheckAppLocationPermissionHelper;
        }
        return LocationPermissionResult.ALLOWED;
    }

    private static boolean checkManifestPermission(Context context, int i, int i2, String str) {
        return context.checkPermission(str, i, i2) == 0;
    }

    private static boolean checkSystemLocationAccess(Context context, int i, int i2, String str) {
        if (isLocationModeEnabled(context, UserHandle.getUserHandleForUid(i).getIdentifier()) || isLocationBypassAllowed(context, str)) {
            return isCurrentProfile(context, i) || checkInteractAcrossUsersFull(context, i2, i);
        }
        return false;
    }

    public static boolean isLocationModeEnabled(Context context, int i) {
        LocationManager locationManager = (LocationManager) context.getSystemService(LocationManager.class);
        if (locationManager == null) {
            Log.w(TAG, "Couldn't get location manager, denying location access");
            return false;
        }
        return locationManager.isLocationEnabledForUser(UserHandle.of(i));
    }

    private static boolean isLocationBypassAllowed(Context context, String str) {
        for (String str2 : getLocationBypassPackages(context)) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public static String[] getLocationBypassPackages(Context context) {
        return context.getResources().getStringArray(R.array.config_serviceStateLocationAllowedPackages);
    }

    private static boolean checkInteractAcrossUsersFull(Context context, int i, int i2) {
        return checkManifestPermission(context, i, i2, Manifest.permission.INTERACT_ACROSS_USERS_FULL);
    }

    private static boolean isCurrentProfile(Context context, int i) {
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (UserHandle.getUserHandleForUid(i).getIdentifier() != ActivityManager.getCurrentUser()) {
                ActivityManager activityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
                if (activityManager != null) {
                    return activityManager.isProfileForeground(UserHandle.getUserHandleForUid(ActivityManager.getCurrentUser()));
                }
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                return false;
            }
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            return true;
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    private static boolean isAppAtLeastSdkVersion(Context context, UserHandle userHandle, String str, int i) {
        try {
            if (Flags.hsumPackageManager()) {
                if (context.getPackageManager().getApplicationInfoAsUser(str, 0, userHandle).targetSdkVersion >= i) {
                    return true;
                }
            } else if (context.getPackageManager().getApplicationInfo(str, 0).targetSdkVersion >= i) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }
}
