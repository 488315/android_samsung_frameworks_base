package com.android.server.am;

import android.app.ActivityManagerInternal;
import android.os.SystemClock;
import android.util.proto.ProtoOutputStream;

import java.io.PrintWriter;

public final class AppBindServiceEventsTracker extends BaseAppStateTimeSlotEventsTracker
        implements ActivityManagerInternal.BindServiceEventListener {

    public final class AppBindServiceEventsPolicy
            extends BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy {
        @Override // com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy, com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        public final void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.println("APP BIND SERVICE EVENT TRACKER POLICY SETTINGS:");
            super.dump(printWriter, "  " + str);
        }
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    public final BaseAppStateEvents createAppStateEvents(int i, String str) {
        BaseAppStatePolicy baseAppStatePolicy = this.mInjector.mAppStatePolicy;
        return new BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents(
                i,
                str,
                ((AppBindServiceEventsPolicy) baseAppStatePolicy).mTimeSlotSize,
                (BaseAppStateEventsTracker.BaseAppStateEventsPolicy) baseAppStatePolicy);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker,
              // com.android.server.am.BaseAppStateTracker
    public final void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println("APP BIND SERVICE EVENT TRACKER:");
        super.dump(printWriter, "  " + str);
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final byte[] getTrackerInfoForStatsd(int i) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents
                simpleAppStateTimeslotEvents =
                        (BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents)
                                getUidEventsLocked(i);
        int totalEventsSince =
                simpleAppStateTimeslotEvents == null
                        ? 0
                        : simpleAppStateTimeslotEvents.getTotalEventsSince(
                                simpleAppStateTimeslotEvents.getEarliest(0L), elapsedRealtime);
        if (totalEventsSince == 0) {
            return null;
        }
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        protoOutputStream.write(1120986464257L, totalEventsSince);
        protoOutputStream.flush();
        return protoOutputStream.getBytes();
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final int getType() {
        return 7;
    }

    public final void onBindingService(String str, int i) {
        if (((AppBindServiceEventsPolicy) this.mInjector.mAppStatePolicy).mTrackerEnabled) {
            this.mHandler.obtainMessage(0, i, 0, str).sendToTarget();
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onSystemReady() {
        super.onSystemReady();
        this.mInjector.mActivityManagerInternal.addBindServiceEventListener(this);
    }
}
