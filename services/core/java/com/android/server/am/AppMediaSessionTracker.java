package com.android.server.am;

import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.SparseArray;
import com.android.internal.app.ProcessMap;
import com.android.server.am.BaseAppStateDurationsTracker;
import com.android.server.am.BaseAppStateEvents;
import com.android.server.am.BaseAppStateEventsTracker;
import com.android.server.am.BaseAppStateTracker;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class AppMediaSessionTracker extends BaseAppStateDurationsTracker {
    public final HandlerExecutor mHandlerExecutor;
    public final MediaSessionManager.OnActiveSessionsChangedListener mSessionsChangedListener;
    public final ProcessMap mTmpMediaControllers;

    @Override // com.android.server.am.BaseAppStateTracker
    public int getType() {
        return 4;
    }

    public AppMediaSessionTracker(Context context, AppRestrictionController appRestrictionController) {
        this(context, appRestrictionController, null, null);
    }

    public AppMediaSessionTracker(Context context, AppRestrictionController appRestrictionController, Constructor constructor, Object obj) {
        super(context, appRestrictionController, constructor, obj);
        this.mSessionsChangedListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.android.server.am.AppMediaSessionTracker$$ExternalSyntheticLambda0
            @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
            public final void onActiveSessionsChanged(List list) {
                AppMediaSessionTracker.this.handleMediaSessionChanged(list);
            }
        };
        this.mTmpMediaControllers = new ProcessMap();
        this.mHandlerExecutor = new HandlerExecutor(this.mBgHandler);
        BaseAppStateTracker.Injector injector = this.mInjector;
        injector.setPolicy(new AppMediaSessionPolicy(injector, this));
    }

    @Override // com.android.server.am.BaseAppStateEvents.Factory
    public BaseAppStateDurationsTracker.SimplePackageDurations createAppStateEvents(int i, String str) {
        return new BaseAppStateDurationsTracker.SimplePackageDurations(i, str, (BaseAppStateEvents.MaxTrackingDurationConfig) this.mInjector.getPolicy());
    }

    @Override // com.android.server.am.BaseAppStateEvents.Factory
    public BaseAppStateDurationsTracker.SimplePackageDurations createAppStateEvents(BaseAppStateDurationsTracker.SimplePackageDurations simplePackageDurations) {
        return new BaseAppStateDurationsTracker.SimplePackageDurations(simplePackageDurations);
    }

    public final void onBgMediaSessionMonitorEnabled(boolean z) {
        if (z) {
            this.mInjector.getMediaSessionManager().addOnActiveSessionsChangedListener(null, UserHandle.ALL, this.mHandlerExecutor, this.mSessionsChangedListener);
        } else {
            this.mInjector.getMediaSessionManager().removeOnActiveSessionsChangedListener(this.mSessionsChangedListener);
        }
    }

    public final void handleMediaSessionChanged(List list) {
        int i;
        int i2;
        if (list != null) {
            synchronized (this.mLock) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    MediaController mediaController = (MediaController) it.next();
                    String packageName = mediaController.getPackageName();
                    int uid = mediaController.getSessionToken().getUid();
                    BaseAppStateDurationsTracker.SimplePackageDurations simplePackageDurations = (BaseAppStateDurationsTracker.SimplePackageDurations) this.mPkgEvents.get(uid, packageName);
                    if (simplePackageDurations == null) {
                        simplePackageDurations = createAppStateEvents(uid, packageName);
                        this.mPkgEvents.put(uid, packageName, simplePackageDurations);
                    }
                    if (!simplePackageDurations.isActive()) {
                        simplePackageDurations.addEvent(true, elapsedRealtime);
                        notifyListenersOnStateChange(simplePackageDurations.mUid, simplePackageDurations.mPackageName, true, elapsedRealtime, 1);
                    }
                    this.mTmpMediaControllers.put(packageName, uid, Boolean.TRUE);
                }
                SparseArray map = this.mPkgEvents.getMap();
                for (int size = map.size() - 1; size >= 0; size--) {
                    ArrayMap arrayMap = (ArrayMap) map.valueAt(size);
                    int size2 = arrayMap.size() - 1;
                    while (size2 >= 0) {
                        BaseAppStateDurationsTracker.SimplePackageDurations simplePackageDurations2 = (BaseAppStateDurationsTracker.SimplePackageDurations) arrayMap.valueAt(size2);
                        if (simplePackageDurations2.isActive() && this.mTmpMediaControllers.get(simplePackageDurations2.mPackageName, simplePackageDurations2.mUid) == null) {
                            simplePackageDurations2.addEvent(false, elapsedRealtime);
                            i2 = size2;
                            notifyListenersOnStateChange(simplePackageDurations2.mUid, simplePackageDurations2.mPackageName, false, elapsedRealtime, 1);
                        } else {
                            i2 = size2;
                        }
                        size2 = i2 - 1;
                    }
                }
            }
            this.mTmpMediaControllers.clear();
            return;
        }
        synchronized (this.mLock) {
            SparseArray map2 = this.mPkgEvents.getMap();
            long elapsedRealtime2 = SystemClock.elapsedRealtime();
            for (int size3 = map2.size() - 1; size3 >= 0; size3--) {
                ArrayMap arrayMap2 = (ArrayMap) map2.valueAt(size3);
                int size4 = arrayMap2.size() - 1;
                while (size4 >= 0) {
                    BaseAppStateDurationsTracker.SimplePackageDurations simplePackageDurations3 = (BaseAppStateDurationsTracker.SimplePackageDurations) arrayMap2.valueAt(size4);
                    if (simplePackageDurations3.isActive()) {
                        simplePackageDurations3.addEvent(false, elapsedRealtime2);
                        i = size4;
                        notifyListenersOnStateChange(simplePackageDurations3.mUid, simplePackageDurations3.mPackageName, false, elapsedRealtime2, 1);
                    } else {
                        i = size4;
                    }
                    size4 = i - 1;
                }
            }
        }
    }

    public final void trimDurations() {
        trim(Math.max(0L, SystemClock.elapsedRealtime() - ((AppMediaSessionPolicy) this.mInjector.getPolicy()).getMaxTrackingDuration()));
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker, com.android.server.am.BaseAppStateTracker
    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println("APP MEDIA SESSION TRACKER:");
        super.dump(printWriter, "  " + str);
    }

    public final class AppMediaSessionPolicy extends BaseAppStateEventsTracker.BaseAppStateEventsPolicy {
        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        public String getExemptionReasonString(String str, int i, int i2) {
            return "n/a";
        }

        public AppMediaSessionPolicy(BaseAppStateTracker.Injector injector, AppMediaSessionTracker appMediaSessionTracker) {
            super(injector, appMediaSessionTracker, "bg_media_session_monitor_enabled", true, "bg_media_session_monitor_max_tracking_duration", 345600000L);
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onTrackerEnabled(boolean z) {
            ((AppMediaSessionTracker) this.mTracker).onBgMediaSessionMonitorEnabled(z);
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        public void onMaxTrackingDurationChanged(long j) {
            BaseAppStateTracker baseAppStateTracker = this.mTracker;
            Handler handler = ((AppMediaSessionTracker) baseAppStateTracker).mBgHandler;
            final AppMediaSessionTracker appMediaSessionTracker = (AppMediaSessionTracker) baseAppStateTracker;
            Objects.requireNonNull(appMediaSessionTracker);
            handler.post(new Runnable() { // from class: com.android.server.am.AppMediaSessionTracker$AppMediaSessionPolicy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AppMediaSessionTracker.this.trimDurations();
                }
            });
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        public void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.println("APP MEDIA SESSION TRACKER POLICY SETTINGS:");
            super.dump(printWriter, "  " + str);
        }
    }
}
