package com.android.server.am;

import android.app.ActivityManagerInternal;
import android.content.Context;
import android.os.SystemClock;
import android.util.proto.ProtoOutputStream;
import com.android.server.backup.BackupManagerConstants;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;

/* loaded from: classes.dex */
public final class AppBroadcastEventsTracker extends BaseAppStateTimeSlotEventsTracker
    implements ActivityManagerInternal.BroadcastEventListener {
  @Override // com.android.server.am.BaseAppStateTracker
  public int getType() {
    return 6;
  }

  public AppBroadcastEventsTracker(
      Context context, AppRestrictionController appRestrictionController) {
    this(context, appRestrictionController, null, null);
  }

  public AppBroadcastEventsTracker(
      Context context,
      AppRestrictionController appRestrictionController,
      Constructor constructor,
      Object obj) {
    super(context, appRestrictionController, constructor, obj);
    BaseAppStateTracker.Injector injector = this.mInjector;
    injector.setPolicy(new AppBroadcastEventsPolicy(injector, this));
  }

  public void onSendingBroadcast(String str, int i) {
    if (((AppBroadcastEventsPolicy) this.mInjector.getPolicy()).isEnabled()) {
      onNewEvent(str, i);
    }
  }

  @Override // com.android.server.am.BaseAppStateTracker
  public void onSystemReady() {
    super.onSystemReady();
    this.mInjector.getActivityManagerInternal().addBroadcastEventListener(this);
  }

  @Override // com.android.server.am.BaseAppStateEvents.Factory
  public BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents createAppStateEvents(
      int i, String str) {
    return new BaseAppStateTimeSlotEventsTracker.SimpleAppStateTimeslotEvents(
        i,
        str,
        ((AppBroadcastEventsPolicy) this.mInjector.getPolicy()).getTimeSlotSize(),
        "ActivityManager",
        (BaseAppStateEvents.MaxTrackingDurationConfig) this.mInjector.getPolicy());
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

  @Override // com.android.server.am.BaseAppStateEventsTracker,
            // com.android.server.am.BaseAppStateTracker
  public void dump(PrintWriter printWriter, String str) {
    printWriter.print(str);
    printWriter.println("APP BROADCAST EVENT TRACKER:");
    super.dump(printWriter, "  " + str);
  }

  public final class AppBroadcastEventsPolicy
      extends BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy {
    public AppBroadcastEventsPolicy(
        BaseAppStateTracker.Injector injector,
        AppBroadcastEventsTracker appBroadcastEventsTracker) {
      super(
          injector,
          appBroadcastEventsTracker,
          "bg_broadcast_monitor_enabled",
          true,
          "bg_broadcast_window",
          BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS,
          "bg_ex_broadcast_threshold",
          10000);
    }

    @Override // com.android.server.am.BaseAppStateTimeSlotEventsTracker.BaseAppStateTimeSlotEventsPolicy, com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
    public void dump(PrintWriter printWriter, String str) {
      printWriter.print(str);
      printWriter.println("APP BROADCAST EVENT TRACKER POLICY SETTINGS:");
      super.dump(printWriter, "  " + str);
    }
  }
}
