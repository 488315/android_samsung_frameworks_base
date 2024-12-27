package com.android.server.am;

import android.os.Binder;
import android.os.SystemClock;
import android.util.Log;
import com.android.internal.app.AppLockPolicy;
import com.android.internal.os.BinderCallHeavyHitterWatcher;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActivityManagerService;
import com.android.server.clipboard.ClipboardService;
import com.android.server.pm.PackageManagerService;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ActivityManagerService$$ExternalSyntheticLambda11 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityManagerService f$0;

    public /* synthetic */ ActivityManagerService$$ExternalSyntheticLambda11(ActivityManagerService activityManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = activityManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i;
        float f;
        BinderCallHeavyHitterWatcher.BinderCallHeavyHitterListener binderCallHeavyHitterListener;
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                ActivityManagerService activityManagerService = this.f$0;
                activityManagerService.getClass();
                if (MaintenanceModeManager.isInMaintenanceMode()) {
                    Log.i("ActivityManager", "Switch to Maintenance mode");
                    activityManagerService.mUserController.switchUser(77);
                    return;
                }
                return;
            case 1:
                ActivityManagerService activityManagerService2 = this.f$0;
                activityManagerService2.mActivityTaskManager.mAppLockPolicy = AppLockPolicy.getInstance(activityManagerService2.mContext, activityManagerService2.mHandler);
                return;
            case 2:
                final ActivityManagerService activityManagerService3 = this.f$0;
                ActivityManagerProcLock activityManagerProcLock = activityManagerService3.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerProcLock) {
                    try {
                        if (ActivityManagerConstants.BINDER_HEAVY_HITTER_WATCHER_ENABLED) {
                            activityManagerService3.mHandler.removeMessages(72);
                            i = ActivityManagerConstants.BINDER_HEAVY_HITTER_WATCHER_BATCHSIZE;
                            f = ActivityManagerConstants.BINDER_HEAVY_HITTER_WATCHER_THRESHOLD;
                            final int i2 = 0;
                            binderCallHeavyHitterListener = new BinderCallHeavyHitterWatcher.BinderCallHeavyHitterListener() { // from class: com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda33
                                public final void onHeavyHit(final List list, final int i3, final float f2, final long j) {
                                    switch (i2) {
                                        case 0:
                                            final ActivityManagerService activityManagerService4 = activityManagerService3;
                                            final int i4 = 1;
                                            activityManagerService4.mHandler.post(new Runnable() { // from class: com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda42
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    switch (i4) {
                                                        case 0:
                                                            ActivityManagerService activityManagerService5 = activityManagerService4;
                                                            List list2 = list;
                                                            int i5 = i3;
                                                            float f3 = f2;
                                                            long j2 = j;
                                                            activityManagerService5.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list2, i5, f3, j2);
                                                            break;
                                                        case 1:
                                                            ActivityManagerService activityManagerService6 = activityManagerService4;
                                                            List list3 = list;
                                                            int i6 = i3;
                                                            float f4 = f2;
                                                            long j3 = j;
                                                            activityManagerService6.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list3, i6, f4, j3);
                                                            break;
                                                        default:
                                                            ActivityManagerService activityManagerService7 = activityManagerService4;
                                                            List list4 = list;
                                                            int i7 = i3;
                                                            float f5 = f2;
                                                            long j4 = j;
                                                            activityManagerService7.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list4, i7, f5, j4);
                                                            break;
                                                    }
                                                }
                                            });
                                            break;
                                        case 1:
                                            final ActivityManagerService activityManagerService5 = activityManagerService3;
                                            final int i5 = 0;
                                            activityManagerService5.mHandler.post(new Runnable() { // from class: com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda42
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    switch (i5) {
                                                        case 0:
                                                            ActivityManagerService activityManagerService52 = activityManagerService5;
                                                            List list2 = list;
                                                            int i52 = i3;
                                                            float f3 = f2;
                                                            long j2 = j;
                                                            activityManagerService52.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list2, i52, f3, j2);
                                                            break;
                                                        case 1:
                                                            ActivityManagerService activityManagerService6 = activityManagerService5;
                                                            List list3 = list;
                                                            int i6 = i3;
                                                            float f4 = f2;
                                                            long j3 = j;
                                                            activityManagerService6.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list3, i6, f4, j3);
                                                            break;
                                                        default:
                                                            ActivityManagerService activityManagerService7 = activityManagerService5;
                                                            List list4 = list;
                                                            int i7 = i3;
                                                            float f5 = f2;
                                                            long j4 = j;
                                                            activityManagerService7.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list4, i7, f5, j4);
                                                            break;
                                                    }
                                                }
                                            });
                                            break;
                                        default:
                                            final ActivityManagerService activityManagerService6 = activityManagerService3;
                                            final int i6 = 2;
                                            activityManagerService6.mHandler.post(new Runnable() { // from class: com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda42
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    switch (i6) {
                                                        case 0:
                                                            ActivityManagerService activityManagerService52 = activityManagerService6;
                                                            List list2 = list;
                                                            int i52 = i3;
                                                            float f3 = f2;
                                                            long j2 = j;
                                                            activityManagerService52.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list2, i52, f3, j2);
                                                            break;
                                                        case 1:
                                                            ActivityManagerService activityManagerService62 = activityManagerService6;
                                                            List list3 = list;
                                                            int i62 = i3;
                                                            float f4 = f2;
                                                            long j3 = j;
                                                            activityManagerService62.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list3, i62, f4, j3);
                                                            break;
                                                        default:
                                                            ActivityManagerService activityManagerService7 = activityManagerService6;
                                                            List list4 = list;
                                                            int i7 = i3;
                                                            float f5 = f2;
                                                            long j4 = j;
                                                            activityManagerService7.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list4, i7, f5, j4);
                                                            break;
                                                    }
                                                }
                                            });
                                            break;
                                    }
                                }
                            };
                            z = true;
                        } else if (activityManagerService3.mHandler.hasMessages(72)) {
                            boolean z2 = ActivityManagerConstants.BINDER_HEAVY_HITTER_AUTO_SAMPLER_ENABLED;
                            int i3 = ActivityManagerConstants.BINDER_HEAVY_HITTER_AUTO_SAMPLER_BATCHSIZE;
                            float f2 = ActivityManagerConstants.BINDER_HEAVY_HITTER_AUTO_SAMPLER_THRESHOLD;
                            final int i4 = 1;
                            BinderCallHeavyHitterWatcher.BinderCallHeavyHitterListener binderCallHeavyHitterListener2 = new BinderCallHeavyHitterWatcher.BinderCallHeavyHitterListener() { // from class: com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda33
                                public final void onHeavyHit(final List list, final int i32, final float f22, final long j) {
                                    switch (i4) {
                                        case 0:
                                            final ActivityManagerService activityManagerService4 = activityManagerService3;
                                            final int i42 = 1;
                                            activityManagerService4.mHandler.post(new Runnable() { // from class: com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda42
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    switch (i42) {
                                                        case 0:
                                                            ActivityManagerService activityManagerService52 = activityManagerService4;
                                                            List list2 = list;
                                                            int i52 = i32;
                                                            float f3 = f22;
                                                            long j2 = j;
                                                            activityManagerService52.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list2, i52, f3, j2);
                                                            break;
                                                        case 1:
                                                            ActivityManagerService activityManagerService62 = activityManagerService4;
                                                            List list3 = list;
                                                            int i62 = i32;
                                                            float f4 = f22;
                                                            long j3 = j;
                                                            activityManagerService62.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list3, i62, f4, j3);
                                                            break;
                                                        default:
                                                            ActivityManagerService activityManagerService7 = activityManagerService4;
                                                            List list4 = list;
                                                            int i7 = i32;
                                                            float f5 = f22;
                                                            long j4 = j;
                                                            activityManagerService7.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list4, i7, f5, j4);
                                                            break;
                                                    }
                                                }
                                            });
                                            break;
                                        case 1:
                                            final ActivityManagerService activityManagerService5 = activityManagerService3;
                                            final int i5 = 0;
                                            activityManagerService5.mHandler.post(new Runnable() { // from class: com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda42
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    switch (i5) {
                                                        case 0:
                                                            ActivityManagerService activityManagerService52 = activityManagerService5;
                                                            List list2 = list;
                                                            int i52 = i32;
                                                            float f3 = f22;
                                                            long j2 = j;
                                                            activityManagerService52.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list2, i52, f3, j2);
                                                            break;
                                                        case 1:
                                                            ActivityManagerService activityManagerService62 = activityManagerService5;
                                                            List list3 = list;
                                                            int i62 = i32;
                                                            float f4 = f22;
                                                            long j3 = j;
                                                            activityManagerService62.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list3, i62, f4, j3);
                                                            break;
                                                        default:
                                                            ActivityManagerService activityManagerService7 = activityManagerService5;
                                                            List list4 = list;
                                                            int i7 = i32;
                                                            float f5 = f22;
                                                            long j4 = j;
                                                            activityManagerService7.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list4, i7, f5, j4);
                                                            break;
                                                    }
                                                }
                                            });
                                            break;
                                        default:
                                            final ActivityManagerService activityManagerService6 = activityManagerService3;
                                            final int i6 = 2;
                                            activityManagerService6.mHandler.post(new Runnable() { // from class: com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda42
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    switch (i6) {
                                                        case 0:
                                                            ActivityManagerService activityManagerService52 = activityManagerService6;
                                                            List list2 = list;
                                                            int i52 = i32;
                                                            float f3 = f22;
                                                            long j2 = j;
                                                            activityManagerService52.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list2, i52, f3, j2);
                                                            break;
                                                        case 1:
                                                            ActivityManagerService activityManagerService62 = activityManagerService6;
                                                            List list3 = list;
                                                            int i62 = i32;
                                                            float f4 = f22;
                                                            long j3 = j;
                                                            activityManagerService62.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list3, i62, f4, j3);
                                                            break;
                                                        default:
                                                            ActivityManagerService activityManagerService7 = activityManagerService6;
                                                            List list4 = list;
                                                            int i7 = i32;
                                                            float f5 = f22;
                                                            long j4 = j;
                                                            activityManagerService7.getClass();
                                                            ActivityManagerService.handleBinderHeavyHitters(list4, i7, f5, j4);
                                                            break;
                                                    }
                                                }
                                            });
                                            break;
                                    }
                                }
                            };
                            z = z2;
                            i = i3;
                            f = f2;
                            binderCallHeavyHitterListener = binderCallHeavyHitterListener2;
                        } else {
                            i = 0;
                            f = FullScreenMagnificationGestureHandler.MAX_SCALE;
                            binderCallHeavyHitterListener = null;
                            z = false;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                Binder.setHeavyHitterWatcherConfig(z, i, f, binderCallHeavyHitterListener);
                return;
            case 3:
                ActivityManagerService activityManagerService4 = this.f$0;
                activityManagerService4.getClass();
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService4) {
                    try {
                        activityManagerService4.mExt.initLongLivePackageLocked();
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
            case 4:
                ActivityManagerService activityManagerService5 = this.f$0;
                activityManagerService5.getClass();
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService5) {
                    try {
                        ActivityManagerServiceExt activityManagerServiceExt = activityManagerService5.mExt;
                        activityManagerServiceExt.mCanTmoPkgAvoidForceStop = "com.tmobile.echolocate".equals(PackageManagerService.ensureSystemPackageName(((PackageManagerService.PackageManagerInternalImpl) activityManagerServiceExt.mService.getPackageManagerInternal()).mService.snapshotComputer(), "com.tmobile.echolocate"));
                    } finally {
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
            default:
                final ActivityManagerService activityManagerService6 = this.f$0;
                ActivityManagerProcLock activityManagerProcLock2 = activityManagerService6.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerProcLock2) {
                    try {
                        if (!ActivityManagerConstants.BINDER_HEAVY_HITTER_AUTO_SAMPLER_ENABLED) {
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                            return;
                        }
                        if (ActivityManagerConstants.BINDER_HEAVY_HITTER_WATCHER_ENABLED) {
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                            return;
                        }
                        long uptimeMillis = SystemClock.uptimeMillis();
                        if (activityManagerService6.mLastBinderHeavyHitterAutoSamplerStart + ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS > uptimeMillis) {
                            ActivityManagerService.resetPriorityAfterProcLockedSection();
                            return;
                        }
                        int i5 = ActivityManagerConstants.BINDER_HEAVY_HITTER_AUTO_SAMPLER_BATCHSIZE;
                        float f3 = ActivityManagerConstants.BINDER_HEAVY_HITTER_AUTO_SAMPLER_THRESHOLD;
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        activityManagerService6.mLastBinderHeavyHitterAutoSamplerStart = uptimeMillis;
                        final int i6 = 2;
                        Binder.setHeavyHitterWatcherConfig(true, i5, f3, new BinderCallHeavyHitterWatcher.BinderCallHeavyHitterListener() { // from class: com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda33
                            public final void onHeavyHit(final List list, final int i32, final float f22, final long j) {
                                switch (i6) {
                                    case 0:
                                        final ActivityManagerService activityManagerService42 = activityManagerService6;
                                        final int i42 = 1;
                                        activityManagerService42.mHandler.post(new Runnable() { // from class: com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda42
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                switch (i42) {
                                                    case 0:
                                                        ActivityManagerService activityManagerService52 = activityManagerService42;
                                                        List list2 = list;
                                                        int i52 = i32;
                                                        float f32 = f22;
                                                        long j2 = j;
                                                        activityManagerService52.getClass();
                                                        ActivityManagerService.handleBinderHeavyHitters(list2, i52, f32, j2);
                                                        break;
                                                    case 1:
                                                        ActivityManagerService activityManagerService62 = activityManagerService42;
                                                        List list3 = list;
                                                        int i62 = i32;
                                                        float f4 = f22;
                                                        long j3 = j;
                                                        activityManagerService62.getClass();
                                                        ActivityManagerService.handleBinderHeavyHitters(list3, i62, f4, j3);
                                                        break;
                                                    default:
                                                        ActivityManagerService activityManagerService7 = activityManagerService42;
                                                        List list4 = list;
                                                        int i7 = i32;
                                                        float f5 = f22;
                                                        long j4 = j;
                                                        activityManagerService7.getClass();
                                                        ActivityManagerService.handleBinderHeavyHitters(list4, i7, f5, j4);
                                                        break;
                                                }
                                            }
                                        });
                                        break;
                                    case 1:
                                        final ActivityManagerService activityManagerService52 = activityManagerService6;
                                        final int i52 = 0;
                                        activityManagerService52.mHandler.post(new Runnable() { // from class: com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda42
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                switch (i52) {
                                                    case 0:
                                                        ActivityManagerService activityManagerService522 = activityManagerService52;
                                                        List list2 = list;
                                                        int i522 = i32;
                                                        float f32 = f22;
                                                        long j2 = j;
                                                        activityManagerService522.getClass();
                                                        ActivityManagerService.handleBinderHeavyHitters(list2, i522, f32, j2);
                                                        break;
                                                    case 1:
                                                        ActivityManagerService activityManagerService62 = activityManagerService52;
                                                        List list3 = list;
                                                        int i62 = i32;
                                                        float f4 = f22;
                                                        long j3 = j;
                                                        activityManagerService62.getClass();
                                                        ActivityManagerService.handleBinderHeavyHitters(list3, i62, f4, j3);
                                                        break;
                                                    default:
                                                        ActivityManagerService activityManagerService7 = activityManagerService52;
                                                        List list4 = list;
                                                        int i7 = i32;
                                                        float f5 = f22;
                                                        long j4 = j;
                                                        activityManagerService7.getClass();
                                                        ActivityManagerService.handleBinderHeavyHitters(list4, i7, f5, j4);
                                                        break;
                                                }
                                            }
                                        });
                                        break;
                                    default:
                                        final ActivityManagerService activityManagerService62 = activityManagerService6;
                                        final int i62 = 2;
                                        activityManagerService62.mHandler.post(new Runnable() { // from class: com.android.server.am.ActivityManagerService$$ExternalSyntheticLambda42
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                switch (i62) {
                                                    case 0:
                                                        ActivityManagerService activityManagerService522 = activityManagerService62;
                                                        List list2 = list;
                                                        int i522 = i32;
                                                        float f32 = f22;
                                                        long j2 = j;
                                                        activityManagerService522.getClass();
                                                        ActivityManagerService.handleBinderHeavyHitters(list2, i522, f32, j2);
                                                        break;
                                                    case 1:
                                                        ActivityManagerService activityManagerService622 = activityManagerService62;
                                                        List list3 = list;
                                                        int i622 = i32;
                                                        float f4 = f22;
                                                        long j3 = j;
                                                        activityManagerService622.getClass();
                                                        ActivityManagerService.handleBinderHeavyHitters(list3, i622, f4, j3);
                                                        break;
                                                    default:
                                                        ActivityManagerService activityManagerService7 = activityManagerService62;
                                                        List list4 = list;
                                                        int i7 = i32;
                                                        float f5 = f22;
                                                        long j4 = j;
                                                        activityManagerService7.getClass();
                                                        ActivityManagerService.handleBinderHeavyHitters(list4, i7, f5, j4);
                                                        break;
                                                }
                                            }
                                        });
                                        break;
                                }
                            }
                        });
                        ActivityManagerService.UiHandler uiHandler = activityManagerService6.mHandler;
                        uiHandler.sendMessageDelayed(uiHandler.obtainMessage(72), 300000L);
                        return;
                    } finally {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                    }
                }
        }
    }
}
