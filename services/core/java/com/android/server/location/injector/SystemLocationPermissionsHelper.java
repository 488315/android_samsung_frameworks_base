package com.android.server.location.injector;

import android.content.Context;
import android.location.LocationConstants;
import android.location.util.identity.CallerIdentity;
import android.os.Binder;

import com.android.internal.util.Preconditions;
import com.android.server.location.LocationPermissions;
import com.android.server.location.nsflp.NSPermissionHelper;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SystemLocationPermissionsHelper {
    public final SystemAppOpsHelper mAppOps;
    public final Context mContext;
    public boolean mInited;
    public NSPermissionHelper mNSPermissionHelper;
    public Consumer mSLocationConsumer;
    public final HashSet mFreezedUids = new HashSet();
    public final Map mPauseReasonByCaller = new ConcurrentHashMap();
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();

    public SystemLocationPermissionsHelper(Context context, SystemAppOpsHelper systemAppOpsHelper) {
        this.mAppOps = systemAppOpsHelper;
        systemAppOpsHelper.mListeners.add(
                new LocationPermissionsHelper$$ExternalSyntheticLambda0(this));
        this.mContext = context;
    }

    public final boolean hasLocationPermissions(int i, CallerIdentity callerIdentity) {
        String str;
        if (i == 0) {
            ((ConcurrentHashMap) this.mPauseReasonByCaller)
                    .put(
                            Integer.valueOf(callerIdentity.getUid()),
                            LocationConstants.PAUSED_BY.PERMISSION_NONE);
            return false;
        }
        if (i == 1) {
            str = "android.permission.ACCESS_COARSE_LOCATION";
        } else {
            if (i != 2) {
                throw new IllegalArgumentException();
            }
            str = "android.permission.ACCESS_FINE_LOCATION";
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!(this.mContext.checkPermission(
                            str, callerIdentity.getPid(), callerIdentity.getUid())
                    == 0)) {
                ((ConcurrentHashMap) this.mPauseReasonByCaller)
                        .put(
                                Integer.valueOf(callerIdentity.getUid()),
                                LocationConstants.PAUSED_BY.PERMISSION_CHECK);
                return false;
            }
            if (this.mFreezedUids.contains(Integer.valueOf(callerIdentity.getUid()))) {
                ((ConcurrentHashMap) this.mPauseReasonByCaller)
                        .put(
                                Integer.valueOf(callerIdentity.getUid()),
                                LocationConstants.PAUSED_BY.FREEZE);
                return false;
            }
            int asAppOp = LocationPermissions.asAppOp(i);
            SystemAppOpsHelper systemAppOpsHelper = this.mAppOps;
            Preconditions.checkState(systemAppOpsHelper.mAppOps != null);
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                boolean z =
                        systemAppOpsHelper.mAppOps.checkOpNoThrow(
                                        asAppOp,
                                        callerIdentity.getUid(),
                                        callerIdentity.getPackageName())
                                == 0;
                if (z) {
                    ((ConcurrentHashMap) this.mPauseReasonByCaller)
                            .remove(Integer.valueOf(callerIdentity.getUid()));
                } else {
                    ((ConcurrentHashMap) this.mPauseReasonByCaller)
                            .put(
                                    Integer.valueOf(callerIdentity.getUid()),
                                    LocationConstants.PAUSED_BY.APP_OPS);
                }
                return z;
            } finally {
            }
        } finally {
        }
    }
}
