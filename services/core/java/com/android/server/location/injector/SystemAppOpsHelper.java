package com.android.server.location.injector;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationConstants;
import android.location.util.identity.CallerIdentity;
import android.os.Binder;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.location.LocationManagerService;
import com.android.server.location.LocationManagerService$$ExternalSyntheticLambda2;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.nsflp.NSPermissionHelper;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemAppOpsHelper {
    public AppOpsManager mAppOps;
    public final Context mContext;
    public final CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList mMockLocationListeners = new CopyOnWriteArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.location.injector.SystemAppOpsHelper$1, reason: invalid class name */
    public final class AnonymousClass1 extends AppOpsManager.OnOpChangedInternalListener {
        public AnonymousClass1() {
        }

        public final void onOpChanged(int i, final String str) {
            LocationServiceThread.getHandler().post(new Runnable(str) { // from class: com.android.server.location.injector.SystemAppOpsHelper$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Iterator it = SystemAppOpsHelper.this.mMockLocationListeners.iterator();
                    while (it.hasNext()) {
                        LocationManagerService locationManagerService = ((LocationManagerService$$ExternalSyntheticLambda2) it.next()).f$0;
                        synchronized (locationManagerService.mLock) {
                            if (!locationManagerService.mMockLocationRecord.isEmpty()) {
                                String str2 = (String) ((Map.Entry) locationManagerService.mMockLocationRecord.entrySet().iterator().next()).getValue();
                                try {
                                    if (!((LocationManagerService.SystemInjector) locationManagerService.mInjector).mAppOpsHelper.checkMockLocationAccess(locationManagerService.mPackageManager.getPackageUid(str2, 0), str2)) {
                                        locationManagerService.recoverRealProviderLocked(str2);
                                    }
                                } catch (PackageManager.NameNotFoundException unused) {
                                    Log.e("LocationManagerService", "missing package: " + str2);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    public SystemAppOpsHelper(Context context) {
        this.mContext = context;
    }

    public final boolean checkMockLocationAccess(int i, String str) {
        Preconditions.checkState(this.mAppOps != null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mAppOps.checkOpNoThrow(58, i, str) == 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void finishOp(int i, CallerIdentity callerIdentity) {
        Preconditions.checkState(this.mAppOps != null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mAppOps.finishOp(i, callerIdentity.getUid(), callerIdentity.getPackageName(), callerIdentity.getAttributionTag());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean noteOp(CallerIdentity callerIdentity) {
        Preconditions.checkState(this.mAppOps != null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mAppOps.noteOp(58, callerIdentity.getUid(), callerIdentity.getPackageName(), callerIdentity.getAttributionTag(), callerIdentity.getListenerId()) == 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean noteOpNoThrow(int i, CallerIdentity callerIdentity) {
        Preconditions.checkState(this.mAppOps != null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mAppOps.noteOpNoThrow(i, callerIdentity.getUid(), callerIdentity.getPackageName(), callerIdentity.getAttributionTag(), callerIdentity.getListenerId()) == 0;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onSystemReady() {
        if (this.mAppOps != null) {
            return;
        }
        AppOpsManager appOpsManager = (AppOpsManager) this.mContext.getSystemService(AppOpsManager.class);
        Objects.requireNonNull(appOpsManager);
        this.mAppOps = appOpsManager;
        appOpsManager.startWatchingMode(0, (String) null, 1, new AppOpsManager.OnOpChangedListener() { // from class: com.android.server.location.injector.SystemAppOpsHelper$$ExternalSyntheticLambda0
            @Override // android.app.AppOpsManager.OnOpChangedListener
            public final void onOpChanged(String str, final String str2) {
                final SystemAppOpsHelper systemAppOpsHelper = SystemAppOpsHelper.this;
                systemAppOpsHelper.getClass();
                LocationServiceThread.getHandler().post(new Runnable() { // from class: com.android.server.location.injector.SystemAppOpsHelper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SystemAppOpsHelper systemAppOpsHelper2 = SystemAppOpsHelper.this;
                        String str3 = str2;
                        Iterator it = systemAppOpsHelper2.mListeners.iterator();
                        while (it.hasNext()) {
                            SystemLocationPermissionsHelper systemLocationPermissionsHelper = ((LocationPermissionsHelper$$ExternalSyntheticLambda0) it.next()).f$0;
                            NSPermissionHelper nSPermissionHelper = systemLocationPermissionsHelper.mNSPermissionHelper;
                            if (nSPermissionHelper != null) {
                                nSPermissionHelper.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.OP_CHANGED, AccountManagerService$$ExternalSyntheticOutline0.m142m("packageName", str3));
                            }
                            Iterator it2 = systemLocationPermissionsHelper.mListeners.iterator();
                            while (it2.hasNext()) {
                                ((LocationPermissionsHelper$LocationPermissionsListener) it2.next()).onLocationPermissionsChanged(str3);
                            }
                        }
                    }
                });
            }
        });
        this.mAppOps.startWatchingMode(58, (String) null, 0, (AppOpsManager.OnOpChangedListener) new AnonymousClass1());
    }

    public final void startOpNoThrow(int i, CallerIdentity callerIdentity) {
        Preconditions.checkState(this.mAppOps != null);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mAppOps.startOpNoThrow(i, callerIdentity.getUid(), callerIdentity.getPackageName(), false, callerIdentity.getAttributionTag(), callerIdentity.getListenerId());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
