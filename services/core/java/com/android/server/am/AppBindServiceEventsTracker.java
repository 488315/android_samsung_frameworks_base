package com.android.server.am;

import android.app.ActivityManagerInternal;
import android.content.Context;
import android.os.SystemClock;
import android.util.proto.ProtoOutputStream;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.am.BaseAppStateEvents;
import com.android.server.am.BaseAppStateTimeSlotEventsTracker;
import com.android.server.am.BaseAppStateTracker;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;

/* loaded from: classes.dex */
public final class AppBindServiceEventsTracker extends BaseAppStateTimeSlotEventsTracker implements ActivityManagerInternal.BindServiceEventListener {
    @Override // com.android.server.am.BaseAppStateTracker
    public int getType() {
        return 7;
    }

    public AppBindServiceEventsTracker(Context context, AppRestrictionController appRestrictionController) {
        this(context, appRestrictionController, null, null);
    }

    public AppBindServiceEventsTracker(Context context, AppRestrictionController appRestrictionController, Constructor constructor, Object obj) {
        super(context, appRestrictionController, constructor, obj);
        BaseAppStateTracker.Injector injector = this.mInjector;
        injector.setPolicy(new AppBindServiceEventsPolicy(injector, this));
    }

    public void onBindingService(String str, int i) {
        if (((AppBindServiceEventsPolicy) this.mInjector.getPolicy()).isEnabled()) {
            onNewEvent(str, i);
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public void onSystemReady() {
        super.onSystemReady();
        this.mInjector.getActivityManagerInternal().addBindServiceEventListener(this);
    }

    @Override // com.android.server.am.BaseAppStateEvents.Factory
    public BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents createAppStateEvents(int i, String str) {
        return new BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents(i, str, ((AppBindServiceEventsPolicy) this.mInjector.getPolicy()).getTimeSlotSize(), "ActivityManager", (BaseAppStateEvents.MaxTrackingDurationConfig) this.mInjector.getPolicy());
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public byte[] getTrackerInfoForStatsd(int i) {
        int totalEventsLocked = getTotalEventsLocked(i, SystemClock.elapsedRealtime());
        if (totalEventsLocked == 0) {
            return null;
        }
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        protoOutputStream.write(1120986464257L, totalEventsLocked);
        protoOutputStream.flush();
        return protoOutputStream.getBytes();
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker, com.android.server.am.BaseAppStateTracker
    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println("APP BIND SERVICE EVENT TRACKER:");
        super.dump(printWriter, "  " + str);
    }

    public final class AppBindServiceEventsPolicy extends BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy {
        public AppBindServiceEventsPolicy(BaseAppStateTracker.Injector injector, AppBindServiceEventsTracker appBindServiceEventsTracker) {
            super(injector, appBindServiceEventsTracker, "bg_bind_svc_monitor_enabled", true, "bg_bind_svc_window", BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS, "bg_ex_bind_svc_threshold", 10000);
        }

        @Override // com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy, com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        public void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.println("APP BIND SERVICE EVENT TRACKER POLICY SETTINGS:");
            super.dump(printWriter, "  " + str);
        }
    }
}
