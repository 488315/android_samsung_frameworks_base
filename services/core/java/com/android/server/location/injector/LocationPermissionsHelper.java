package com.android.server.location.injector;

import android.location.LocationConstants;
import android.location.util.identity.CallerIdentity;
import com.android.server.location.LocationPermissions;
import com.android.server.location.injector.AppOpsHelper;
import com.android.server.location.nsflp.NSPermissionHelper;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public abstract class LocationPermissionsHelper {
    public final AppOpsHelper mAppOps;
    public NSPermissionHelper mNSPermissionHelper;
    public final HashSet mFreezedUids = new HashSet();
    public final Map mPauseReasonByCaller = new ConcurrentHashMap();
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();

    public interface LocationPermissionsListener {
        void onLocationPermissionsChanged(int i);

        void onLocationPermissionsChanged(String str);
    }

    public abstract boolean hasPermission(String str, CallerIdentity callerIdentity);

    public LocationPermissionsHelper(AppOpsHelper appOpsHelper) {
        this.mAppOps = appOpsHelper;
        appOpsHelper.addListener(new AppOpsHelper.LocationAppOpListener() { // from class: com.android.server.location.injector.LocationPermissionsHelper$$ExternalSyntheticLambda0
            @Override // com.android.server.location.injector.AppOpsHelper.LocationAppOpListener
            public final void onAppOpsChanged(String str) {
                LocationPermissionsHelper.this.onAppOpsChanged(str);
            }
        });
    }

    public final void notifyLocationPermissionsChanged(String str) {
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((LocationPermissionsListener) it.next()).onLocationPermissionsChanged(str);
        }
    }

    public final void notifyLocationPermissionsChanged(int i) {
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((LocationPermissionsListener) it.next()).onLocationPermissionsChanged(i);
        }
    }

    public final void onAppOpsChanged(String str) {
        NSPermissionHelper nSPermissionHelper = this.mNSPermissionHelper;
        if (nSPermissionHelper != null) {
            nSPermissionHelper.sendOpChanged(str);
        }
        notifyLocationPermissionsChanged(str);
    }

    public final void addListener(LocationPermissionsListener locationPermissionsListener) {
        this.mListeners.add(locationPermissionsListener);
    }

    public final void removeListener(LocationPermissionsListener locationPermissionsListener) {
        this.mListeners.remove(locationPermissionsListener);
    }

    public final boolean hasLocationPermissions(int i, CallerIdentity callerIdentity) {
        if (i == 0) {
            this.mPauseReasonByCaller.put(Integer.valueOf(callerIdentity.getUid()), LocationConstants.PAUSED_BY.PERMISSION_NONE);
            return false;
        }
        if (!hasPermission(LocationPermissions.asPermission(i), callerIdentity)) {
            this.mPauseReasonByCaller.put(Integer.valueOf(callerIdentity.getUid()), LocationConstants.PAUSED_BY.PERMISSION_CHECK);
            return false;
        }
        if (this.mFreezedUids.contains(Integer.valueOf(callerIdentity.getUid()))) {
            this.mPauseReasonByCaller.put(Integer.valueOf(callerIdentity.getUid()), LocationConstants.PAUSED_BY.FREEZE);
            return false;
        }
        boolean checkOpNoThrow = this.mAppOps.checkOpNoThrow(LocationPermissions.asAppOp(i), callerIdentity);
        if (checkOpNoThrow) {
            this.mPauseReasonByCaller.remove(Integer.valueOf(callerIdentity.getUid()));
        } else {
            this.mPauseReasonByCaller.put(Integer.valueOf(callerIdentity.getUid()), LocationConstants.PAUSED_BY.APP_OPS);
        }
        return checkOpNoThrow;
    }

    public void onFreezeStateChanged(boolean z, int i) {
        if (this.mFreezedUids.contains(Integer.valueOf(i)) == z) {
            return;
        }
        if (z) {
            this.mFreezedUids.add(Integer.valueOf(i));
        } else {
            this.mFreezedUids.remove(Integer.valueOf(i));
        }
        NSPermissionHelper nSPermissionHelper = this.mNSPermissionHelper;
        if (nSPermissionHelper != null) {
            nSPermissionHelper.sendFreezeStateChanged(z, i);
        }
        notifyLocationPermissionsChanged(i);
    }

    public LocationConstants.PAUSED_BY getReasonByCaller(int i) {
        return (LocationConstants.PAUSED_BY) this.mPauseReasonByCaller.getOrDefault(Integer.valueOf(i), LocationConstants.PAUSED_BY.UNKNOWN);
    }

    public void setNSPermissionHelper(NSPermissionHelper nSPermissionHelper) {
        this.mNSPermissionHelper = nSPermissionHelper;
    }
}
