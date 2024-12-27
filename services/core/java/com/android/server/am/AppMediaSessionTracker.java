package com.android.server.am;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.HandlerExecutor;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.SparseArray;

import com.android.internal.app.ProcessMap;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppMediaSessionTracker extends BaseAppStateDurationsTracker {
    public final HandlerExecutor mHandlerExecutor;
    public final AppMediaSessionTracker$$ExternalSyntheticLambda0 mSessionsChangedListener;
    public final ProcessMap mTmpMediaControllers;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppMediaSessionPolicy
            extends BaseAppStateEventsTracker.BaseAppStateEventsPolicy {
        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy,
                  // com.android.server.am.BaseAppStatePolicy
        public final void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.println("APP MEDIA SESSION TRACKER POLICY SETTINGS:");
            super.dump(printWriter, "  " + str);
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        public final String getExemptionReasonString(int i, int i2, String str) {
            return "n/a";
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        public final void onMaxTrackingDurationChanged() {
            final AppMediaSessionTracker appMediaSessionTracker =
                    (AppMediaSessionTracker) this.mTracker;
            AppRestrictionController.BgHandler bgHandler = appMediaSessionTracker.mBgHandler;
            Objects.requireNonNull(appMediaSessionTracker);
            bgHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.am.AppMediaSessionTracker$AppMediaSessionPolicy$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppMediaSessionTracker appMediaSessionTracker2 =
                                    AppMediaSessionTracker.this;
                            appMediaSessionTracker2.getClass();
                            appMediaSessionTracker2.trim(
                                    Math.max(
                                            0L,
                                            SystemClock.elapsedRealtime()
                                                    - ((AppMediaSessionTracker
                                                                            .AppMediaSessionPolicy)
                                                                    appMediaSessionTracker2
                                                                            .mInjector
                                                                            .mAppStatePolicy)
                                                            .mMaxTrackingDuration));
                        }
                    });
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final void onTrackerEnabled(boolean z) {
            AppMediaSessionTracker appMediaSessionTracker = (AppMediaSessionTracker) this.mTracker;
            BaseAppStateTracker.Injector injector = appMediaSessionTracker.mInjector;
            AppMediaSessionTracker$$ExternalSyntheticLambda0
                    appMediaSessionTracker$$ExternalSyntheticLambda0 =
                            appMediaSessionTracker.mSessionsChangedListener;
            if (z) {
                injector.mMediaSessionManager.addOnActiveSessionsChangedListener(
                        null,
                        UserHandle.ALL,
                        appMediaSessionTracker.mHandlerExecutor,
                        appMediaSessionTracker$$ExternalSyntheticLambda0);
            } else {
                injector.mMediaSessionManager.removeOnActiveSessionsChangedListener(
                        appMediaSessionTracker$$ExternalSyntheticLambda0);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r9v1, types: [com.android.server.am.AppMediaSessionTracker$$ExternalSyntheticLambda0] */
    public AppMediaSessionTracker(
            Context context, AppRestrictionController appRestrictionController) {
        super(context, appRestrictionController);
        this.mSessionsChangedListener =
                new MediaSessionManager
                        .OnActiveSessionsChangedListener() { // from class:
                                                             // com.android.server.am.AppMediaSessionTracker$$ExternalSyntheticLambda0
                    @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
                    public final void onActiveSessionsChanged(List list) {
                        int i;
                        int i2;
                        AppMediaSessionTracker appMediaSessionTracker = AppMediaSessionTracker.this;
                        if (list == null) {
                            synchronized (appMediaSessionTracker.mLock) {
                                try {
                                    SparseArray sparseArray =
                                            appMediaSessionTracker.mPkgEvents.mMap;
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    for (int size = sparseArray.size() - 1; size >= 0; size--) {
                                        ArrayMap arrayMap = (ArrayMap) sparseArray.valueAt(size);
                                        int size2 = arrayMap.size() - 1;
                                        while (size2 >= 0) {
                                            BaseAppStateDurationsTracker.SimplePackageDurations
                                                    simplePackageDurations =
                                                            (BaseAppStateDurationsTracker
                                                                            .SimplePackageDurations)
                                                                    arrayMap.valueAt(size2);
                                            if (simplePackageDurations.isActive(0)) {
                                                simplePackageDurations.addEvent(
                                                        false,
                                                        new BaseAppStateTimeEvents$BaseTimeEvent(
                                                                elapsedRealtime),
                                                        0);
                                                i = size2;
                                                appMediaSessionTracker.notifyListenersOnStateChange(
                                                        simplePackageDurations.mUid,
                                                        1,
                                                        elapsedRealtime,
                                                        simplePackageDurations.mPackageName,
                                                        false);
                                            } else {
                                                i = size2;
                                            }
                                            size2 = i - 1;
                                        }
                                    }
                                } finally {
                                }
                            }
                            return;
                        }
                        synchronized (appMediaSessionTracker.mLock) {
                            try {
                                long elapsedRealtime2 = SystemClock.elapsedRealtime();
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    MediaController mediaController = (MediaController) it.next();
                                    String packageName = mediaController.getPackageName();
                                    int uid = mediaController.getSessionToken().getUid();
                                    BaseAppStateDurationsTracker.SimplePackageDurations
                                            simplePackageDurations2 =
                                                    (BaseAppStateDurationsTracker
                                                                    .SimplePackageDurations)
                                                            appMediaSessionTracker.mPkgEvents.get(
                                                                    uid, packageName);
                                    if (simplePackageDurations2 == null) {
                                        simplePackageDurations2 =
                                                new BaseAppStateDurationsTracker
                                                        .SimplePackageDurations(
                                                        uid,
                                                        packageName,
                                                        (BaseAppStateEventsTracker
                                                                        .BaseAppStateEventsPolicy)
                                                                appMediaSessionTracker
                                                                        .mInjector
                                                                        .mAppStatePolicy);
                                        appMediaSessionTracker.mPkgEvents.put(
                                                packageName, uid, simplePackageDurations2);
                                    }
                                    if (!simplePackageDurations2.isActive(0)) {
                                        simplePackageDurations2.addEvent(
                                                true,
                                                new BaseAppStateTimeEvents$BaseTimeEvent(
                                                        elapsedRealtime2),
                                                0);
                                        appMediaSessionTracker.notifyListenersOnStateChange(
                                                simplePackageDurations2.mUid,
                                                1,
                                                elapsedRealtime2,
                                                simplePackageDurations2.mPackageName,
                                                true);
                                    }
                                    appMediaSessionTracker.mTmpMediaControllers.put(
                                            packageName, uid, Boolean.TRUE);
                                }
                                SparseArray sparseArray2 = appMediaSessionTracker.mPkgEvents.mMap;
                                for (int size3 = sparseArray2.size() - 1; size3 >= 0; size3--) {
                                    ArrayMap arrayMap2 = (ArrayMap) sparseArray2.valueAt(size3);
                                    int size4 = arrayMap2.size() - 1;
                                    while (size4 >= 0) {
                                        BaseAppStateDurationsTracker.SimplePackageDurations
                                                simplePackageDurations3 =
                                                        (BaseAppStateDurationsTracker
                                                                        .SimplePackageDurations)
                                                                arrayMap2.valueAt(size4);
                                        if (simplePackageDurations3.isActive(0)
                                                && appMediaSessionTracker.mTmpMediaControllers.get(
                                                                simplePackageDurations3
                                                                        .mPackageName,
                                                                simplePackageDurations3.mUid)
                                                        == null) {
                                            simplePackageDurations3.addEvent(
                                                    false,
                                                    new BaseAppStateTimeEvents$BaseTimeEvent(
                                                            elapsedRealtime2),
                                                    0);
                                            i2 = size4;
                                            appMediaSessionTracker.notifyListenersOnStateChange(
                                                    simplePackageDurations3.mUid,
                                                    1,
                                                    elapsedRealtime2,
                                                    simplePackageDurations3.mPackageName,
                                                    false);
                                        } else {
                                            i2 = size4;
                                        }
                                        size4 = i2 - 1;
                                    }
                                }
                            } finally {
                            }
                        }
                        appMediaSessionTracker.mTmpMediaControllers.clear();
                    }
                };
        this.mTmpMediaControllers = new ProcessMap();
        this.mHandlerExecutor = new HandlerExecutor(this.mBgHandler);
        BaseAppStateTracker.Injector injector = this.mInjector;
        injector.mAppStatePolicy =
                new AppMediaSessionPolicy(
                        injector,
                        this,
                        "bg_media_session_monitor_enabled",
                        true,
                        "bg_media_session_monitor_max_tracking_duration",
                        345600000L);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    public final BaseAppStateEvents createAppStateEvents(int i, String str) {
        return new BaseAppStateDurationsTracker.SimplePackageDurations(
                i,
                str,
                (BaseAppStateEventsTracker.BaseAppStateEventsPolicy)
                        this.mInjector.mAppStatePolicy);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    public final BaseAppStateEvents createAppStateEvents(BaseAppStateEvents baseAppStateEvents) {
        return new BaseAppStateDurationsTracker.SimplePackageDurations(
                (BaseAppStateDurationsTracker.SimplePackageDurations) baseAppStateEvents);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker,
              // com.android.server.am.BaseAppStateTracker
    public final void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println("APP MEDIA SESSION TRACKER:");
        super.dump(printWriter, "  " + str);
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final int getType() {
        return 4;
    }
}
