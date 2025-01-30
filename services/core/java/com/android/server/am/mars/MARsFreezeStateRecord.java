package com.android.server.am.mars;

import android.net.INetd;
import android.util.ArraySet;
import android.util.Slog;
import com.android.server.am.FreecessController;
import com.android.server.am.FreecessPkgStatus;
import com.android.server.am.MARsPolicyManager;
import com.android.server.backup.BackupManagerConstants;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

/* loaded from: classes.dex */
public class MARsFreezeStateRecord {
  public long initialStateTime;
  public long mAvailableTokens;
  public int mDetectionVer;
  public FreecessPkgStatus mFreecessParent;
  public long mTokensUpdateTime;
  public ArraySet mUnfreezeAbuseDetections;
  public int[] mFreezeCounts = new int[FreezeReasonType.values().length];
  public int[] mUnfreezeCounts = new int[UnfreezeReasonType.values().length];
  public EventRecorder mFreezeEventRecorder = new EventRecorder();
  public boolean isFreezed = false;
  public boolean isLcdOnFreezed = false;
  public boolean isOLAFFreezed = false;
  public boolean isLcdOffFreezed = false;
  public long freezedTime = 0;
  public long unfreezedTime = 0;
  public String freezedReason = null;
  public String unfreezedReason = null;

  public enum Event {
    FREEZE,
    UNFREEZE
  }

  public enum FreezeReasonType {
    FREEZE_TOTAL(0, "Total"),
    FREEZE_REASON_LCDON(1, "Bg"),
    FREEZE_REASON_LCDOFF(2, "LEV"),
    FREEZE_REASON_OLAF(3, "OLAF");

    private final String freezeType;
    private final int typeNum;

    FreezeReasonType(Integer num, String str) {
      this.typeNum = num.intValue();
      this.freezeType = str;
    }

    public int getTypeNum() {
      return this.typeNum;
    }
  }

  public enum UnfreezeReasonType {
    UNFREEZE_TOTAL(0, "Total"),
    UNFREEZE_REASON_ALARM(1, "Alarm"),
    UNFREEZE_REASON_PACKET(2, "Packet"),
    UNFREEZE_REASON_WAKELOCK(3, "Wakelock"),
    UNFREEZE_REASON_BINDER_1(4, "Binder(1)"),
    UNFREEZE_REASON_BINDER_0(5, "Binder(0)"),
    UNFREEZE_REASON_DEVICE_IDLE_OFF(6, "DeviceIdleOFF"),
    UNFREEZE_REASON_START_PROCESS(7, "StartProcessP"),
    UNFREEZE_REASON_LAUNCHING_PROVIDER(8, "LaunchingProvider"),
    UNFREEZE_REASON_RECEIVING_INTENT(9, "ReceivingIntent"),
    UNFREEZE_REASON_EXECUITNG_SERVICE(10, "ExecutingService"),
    UNFREEZE_REASON_UIDACTIVE(11, "UidActive"),
    UNFREEZE_REASON_STARTSERVICE(12, "startService"),
    UNFREEZE_REASON_BINDSERVICE(13, "bindService"),
    UNFREEZE_REASON_ACTIVITY(14, "activity"),
    UNFREEZE_REASON_BROADCAST(15, INetd.IF_FLAG_BROADCAST),
    UNFREEZE_REASON_PROVIDER(16, "provider"),
    UNFREEZE_REASON_UNBINDSERVICE(17, "unbindService"),
    UNFREEZE_REASON_STARTPROCESS(18, "startProcess"),
    UNFREEZE_REASON_SERVICEANR(19, "ServiceANR"),
    UNFREEZE_REASON_SIGNAL(20, "Signal"),
    UNFREEZE_REASON_CFB(21, "Cfb"),
    UNFREEZE_REASON_OLAF(22, "OLAF:"),
    UNFREEZE_REASON_NONE(23, "None");

    private final int typeNum;
    private final String unfreezeType;

    UnfreezeReasonType(Integer num, String str) {
      this.typeNum = num.intValue();
      this.unfreezeType = str;
    }

    public String getString() {
      return this.unfreezeType;
    }

    public int getTypeNum() {
      return this.typeNum;
    }

    public static UnfreezeReasonType reasonTypeOf(String str) {
      if (str == null || str.isEmpty()) {
        return UNFREEZE_REASON_NONE;
      }
      for (UnfreezeReasonType unfreezeReasonType : values()) {
        if (str.contains(unfreezeReasonType.getString())) {
          return unfreezeReasonType;
        }
      }
      return UNFREEZE_REASON_NONE;
    }
  }

  public MARsFreezeStateRecord(FreecessPkgStatus freecessPkgStatus) {
    this.mFreecessParent = freecessPkgStatus;
    initRealtimeDetection();
  }

  public void updateFreezeState(long j, String str) {
    this.freezedTime = j;
    this.freezedReason = str;
    int[] iArr = this.mFreezeCounts;
    int typeNum = FreezeReasonType.FREEZE_TOTAL.getTypeNum();
    iArr[typeNum] = iArr[typeNum] + 1;
    this.mFreezeEventRecorder.addEvent(Event.FREEZE, j, str);
  }

  public void updateUnfreezeState(long j, String str) {
    this.unfreezedTime = j;
    this.unfreezedReason = str;
    int[] iArr = this.mUnfreezeCounts;
    int typeNum = UnfreezeReasonType.UNFREEZE_TOTAL.getTypeNum();
    iArr[typeNum] = iArr[typeNum] + 1;
    this.mFreezeEventRecorder.addEvent(Event.UNFREEZE, j, str);
    try {
      isAssignedUnfreezeDetection(str, this.mUnfreezeCounts);
      if (MARsPolicyManager.getInstance().isChinaPolicyEnabled()
          && !FreecessController.getInstance().isInFreecessExcludeList(this.mFreecessParent)
          && abnormalRealtimeDetectionVer0(j)) {
        Slog.i(
            "MARsFreezeStateRecord",
            "ver:0 catch abnormal unfreeze detection at " + formatDateTime(j));
        handleAbnormalApp(j);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void handleAbnormalApp(long j) {
    if (MARsDebugConfig.DEBUG_MARs) {
      Slog.d("MARsFreezeStateRecord", "handleAbnormalApp");
    }
    this.mUnfreezeAbuseDetections.add(Long.valueOf(j));
    int maxUnfreezeType = getMaxUnfreezeType();
    MARsPolicyManager mARsPolicyManager = MARsPolicyManager.getInstance();
    FreecessPkgStatus freecessPkgStatus = this.mFreecessParent;
    mARsPolicyManager.restrictAbusiveApp(
        freecessPkgStatus.name, freecessPkgStatus.uid, maxUnfreezeType);
    if (UnfreezeReasonType.UNFREEZE_REASON_ALARM.getTypeNum() == maxUnfreezeType
        || UnfreezeReasonType.UNFREEZE_REASON_PACKET.getTypeNum() == maxUnfreezeType) {
      FreecessController.getInstance().addRestrictedPackages(this.mFreecessParent);
    }
    resetToken();
  }

  public void cancelRestrictState() {
    if (MARsDebugConfig.DEBUG_MARs) {
      Slog.d("MARsFreezeStateRecord", "updateRestrictState");
    }
    MARsPolicyManager mARsPolicyManager = MARsPolicyManager.getInstance();
    FreecessPkgStatus freecessPkgStatus = this.mFreecessParent;
    mARsPolicyManager.cancelRestrict(freecessPkgStatus.name, freecessPkgStatus.uid);
    FreecessController.getInstance().removeRestrictedPackages(this.mFreecessParent);
  }

  public final void initRealtimeDetection() {
    if (MARsDebugConfig.DEBUG_FREECESS) {
      Slog.d(
          "MARsFreezeStateRecord",
          "initRealtimeDetection " + this.mFreecessParent.name + " rate:12");
    }
    this.mAvailableTokens = 512L;
    this.mDetectionVer = 0;
    long currentTimeMillis = System.currentTimeMillis();
    this.mTokensUpdateTime = currentTimeMillis;
    this.initialStateTime = currentTimeMillis;
    ArraySet arraySet = this.mUnfreezeAbuseDetections;
    if (arraySet != null) {
      arraySet.clear();
    } else {
      this.mUnfreezeAbuseDetections = new ArraySet();
    }
  }

  public final int getMaxUnfreezeType() {
    int i = this.mUnfreezeCounts[1];
    int i2 = i <= 0 ? -1 : 1;
    int i3 = 2;
    while (true) {
      int[] iArr = this.mUnfreezeCounts;
      if (i3 >= iArr.length) {
        return i2;
      }
      int i4 = iArr[i3];
      if (i < i4) {
        i2 = i3;
        i = i4;
      }
      i3++;
    }
  }

  public final boolean abnormalRealtimeDetectionVer0(long j) {
    long j2 = j - this.mTokensUpdateTime;
    if (MARsDebugConfig.DEBUG_FREECESS) {
      Slog.d(
          "MARsFreezeStateRecord",
          "abnormalRealtimeDetection " + formatDateTime(j) + " elapsed:" + j2);
    }
    if (j2 < 0) {
      this.mTokensUpdateTime = j;
      return false;
    }
    long j3 = j2 >> 12;
    this.mAvailableTokens = Math.min(512L, this.mAvailableTokens + j3);
    this.mTokensUpdateTime = j;
    if (MARsDebugConfig.DEBUG_FREECESS) {
      Slog.d(
          "MARsFreezeStateRecord",
          "newTokens:" + j3 + " adjusted available tokens: " + this.mAvailableTokens);
    }
    long j4 = this.mAvailableTokens;
    if (j4 <= 0) {
      return true;
    }
    this.mAvailableTokens = j4 - 1;
    return false;
  }

  public final void resetToken() {
    this.mAvailableTokens = 512L;
  }

  public final String formatDateTime(long j) {
    return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(Long.valueOf(j));
  }

  public long getInitialStateTime() {
    return this.initialStateTime;
  }

  public ArraySet getUnfreezeAbuseDetections() {
    return this.mUnfreezeAbuseDetections;
  }

  public boolean hasUnfreezeAbuseHistory() {
    return !this.mUnfreezeAbuseDetections.isEmpty();
  }

  public long getLastUnfreezeAbuseTime() {
    return ((Long) this.mUnfreezeAbuseDetections.valueAt(r2.size() - 1)).longValue();
  }

  public final void isAssignedUnfreezeDetection(String str, int[] iArr) {
    int typeNum = UnfreezeReasonType.reasonTypeOf(str).getTypeNum();
    iArr[typeNum] = iArr[typeNum] + 1;
  }

  public String dumpUfzContent() {
    StringBuilder sb = new StringBuilder();
    for (UnfreezeReasonType unfreezeReasonType : UnfreezeReasonType.values()) {
      sb.append(
          String.format(
              "%-4d|", Integer.valueOf(this.mUnfreezeCounts[unfreezeReasonType.getTypeNum()])));
    }
    return sb.toString();
  }

  public void dumpCmd(PrintWriter printWriter, String[] strArr) {
    if ("mfsr".equals(strArr[1])) {
      if ("ver".equals(strArr[2])) {
        this.mDetectionVer = Integer.parseInt(strArr[3]);
        printWriter.println("set real time detection as ver:" + this.mDetectionVer);
        return;
      }
      if ("history".equals(strArr[2])) {
        printWriter.println(this.mFreecessParent.name + " detection version:" + this.mDetectionVer);
        printWriter.println(dumpUfzContent());
      }
    }
  }

  public static String getDumpUnfreezeTitle() {
    StringBuilder sb = new StringBuilder();
    for (UnfreezeReasonType unfreezeReasonType : UnfreezeReasonType.values()) {
      sb.append(String.format("T%-3d|", Integer.valueOf(unfreezeReasonType.getTypeNum())));
    }
    return sb.toString();
  }

  public void compute(long j, long j2, boolean z) {
    this.mFreezeEventRecorder.compute(j, j2, z);
  }

  public Long getFrozenTime() {
    return Long.valueOf(this.mFreezeEventRecorder.getUidFrozenTime());
  }

  public Long getUidRunningTime() {
    return Long.valueOf(this.mFreezeEventRecorder.getUidRunningTime());
  }

  public ArrayList getUnfreezeCounts() {
    return this.mFreezeEventRecorder.getUnfreezeCounts();
  }

  public class EventRecorder {
    public final ArrayDeque mEventArraySelfLocked = new ArrayDeque();
    public long uidRunningTime = 0;
    public long uidFrozenTime = 0;
    public final ArrayList unfreezeCounts =
        new ArrayList(Collections.nCopies(UnfreezeReasonType.values().length, 0));

    public void onUidStart(long j) {
      synchronized (this.mEventArraySelfLocked) {
        removeOutdated();
        this.mEventArraySelfLocked.add(new UidEventRecord(j));
      }
    }

    public void onUidStop(int i, long j) {
      synchronized (this.mEventArraySelfLocked) {
        removeOutdated();
        if (!this.mEventArraySelfLocked.isEmpty()
            && ((UidEventRecord) this.mEventArraySelfLocked.getLast()).runningEndTime
                == Long.MAX_VALUE) {
          ((UidEventRecord) this.mEventArraySelfLocked.getLast()).runningEndTime = j;
        } else {
          boolean isEmpty = this.mEventArraySelfLocked.isEmpty();
          StringBuilder sb = new StringBuilder();
          sb.append("something wrong at onUidStop uid : ");
          sb.append(i);
          sb.append(" is empty? ");
          sb.append(isEmpty);
          sb.append(" uidEndTime : ");
          sb.append(
              isEmpty
                  ? "null"
                  : Long.valueOf(
                      ((UidEventRecord) this.mEventArraySelfLocked.getLast()).runningEndTime));
          Slog.e("MARsFreezeStateRecord", sb.toString());
        }
      }
    }

    public final void addEvent(Event event, long j, String str) {
      synchronized (this.mEventArraySelfLocked) {
        removeOutdated();
        try {
          ((UidEventRecord) this.mEventArraySelfLocked.getLast()).addEvent(event, j, str);
        } catch (Exception e) {
          Slog.e(
              "MARsFreezeStateRecord",
              "error at addEvent. App frozen event without uid running event",
              e);
        }
      }
    }

    public void printAllEvents() {
      synchronized (this.mEventArraySelfLocked) {
        Iterator it = this.mEventArraySelfLocked.iterator();
        while (it.hasNext()) {
          UidEventRecord uidEventRecord = (UidEventRecord) it.next();
          Slog.d(
              "MARsFreezeStateRecord",
              "running start time : " + new Date(uidEventRecord.runningStartTime));
          Slog.d(
              "MARsFreezeStateRecord",
              "running end time : " + new Date(uidEventRecord.runningEndTime));
          uidEventRecord.printAllEvents();
        }
      }
    }

    public final long getUidFrozenTime() {
      return this.uidFrozenTime;
    }

    public final long getUidRunningTime() {
      return this.uidRunningTime;
    }

    public final ArrayList getUnfreezeCounts() {
      return this.unfreezeCounts;
    }

    public final void compute(long j, long j2, boolean z) {
      Collections.fill(this.unfreezeCounts, 0);
      this.uidRunningTime = computeUidRunningTimeFor(j, j2);
      this.uidFrozenTime = computeFrozenTimeAndUnfreezeReasonsFor(j, j2, z);
    }

    public class UidEventRecord {
      public final ArrayDeque freezeEvents;
      public long runningEndTime;
      public final long runningStartTime;

      public UidEventRecord(long j) {
        this.runningStartTime = j;
        this.runningEndTime = Long.MAX_VALUE;
        this.freezeEvents = new ArrayDeque();
      }

      public final void addEvent(Event event, long j, String str) {
        this.freezeEvents.add(new FreezeEventRecord(event, j, str));
      }

      public final void removeOutdated(long j) {
        while (!this.freezeEvents.isEmpty()
            && j - BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS
                > ((FreezeEventRecord) this.freezeEvents.getFirst()).time) {
          this.freezeEvents.removeFirst();
        }
      }

      public final void printAllEvents() {
        Iterator it = this.freezeEvents.iterator();
        while (it.hasNext()) {
          FreezeEventRecord freezeEventRecord = (FreezeEventRecord) it.next();
          Slog.d(
              "MARsFreezeStateRecord",
              "event : "
                  + freezeEventRecord.event
                  + " time : "
                  + new Date(freezeEventRecord.time)
                  + " reason : "
                  + freezeEventRecord.reason);
        }
      }

      public class FreezeEventRecord {
        public final Event event;
        public final String reason;
        public final long time;

        public FreezeEventRecord(Event event, long j, String str) {
          this.event = event;
          this.time = j;
          this.reason = str;
        }
      }
    }

    public final long computeFrozenTimeAndUnfreezeReasonsFor(long j, long j2, boolean z) {
      long j3;
      if (!isArgumentsValid(j, j2)) {
        return -1L;
      }
      synchronized (this.mEventArraySelfLocked) {
        removeOutdated();
        Iterator it = this.mEventArraySelfLocked.iterator();
        long j4 = 0;
        j3 = 0;
        while (it.hasNext()) {
          UidEventRecord uidEventRecord = (UidEventRecord) it.next();
          if (uidEventRecord.runningEndTime >= j) {
            Iterator it2 = uidEventRecord.freezeEvents.iterator();
            while (it2.hasNext()) {
              UidEventRecord.FreezeEventRecord freezeEventRecord =
                  (UidEventRecord.FreezeEventRecord) it2.next();
              if (freezeEventRecord.event == Event.FREEZE) {
                j4 = freezeEventRecord.time;
              } else {
                long j5 = freezeEventRecord.time;
                if (j4 == 0) {
                  j4 = Math.min(j, freezeEventRecord.time);
                }
                countUnfreezeReason(j5, j, j2, freezeEventRecord);
                j3 += calculateOverlapPeriod(j, j2, j4, j5);
                j4 = 0;
              }
            }
          }
        }
        if (z) {
          j3 += j4 != 0 ? calculateOverlapPeriod(j, j2, Math.max(j4, j), j2) : j2 - j;
        }
      }
      return j3;
    }

    public final void countUnfreezeReason(
        long j, long j2, long j3, UidEventRecord.FreezeEventRecord freezeEventRecord) {
      if (j < j2 || j > j3) {
        return;
      }
      int i = UnfreezeReasonType.reasonTypeOf(freezeEventRecord.reason).typeNum;
      ArrayList arrayList = this.unfreezeCounts;
      arrayList.set(i, Integer.valueOf(((Integer) arrayList.get(i)).intValue() + 1));
    }

    public final long calculateOverlapPeriod(long j, long j2, long j3, long j4) {
      if (j4 < j || j2 < j3) {
        return 0L;
      }
      return (j3 >= j || j2 >= j4)
          ? (j > j3 || j4 > j2) ? Math.min(j2, j4) - Math.max(j, j3) : j4 - j3
          : j2 - j;
    }

    public long computeUidRunningTimeFor(long j, long j2) {
      long j3;
      synchronized (this.mEventArraySelfLocked) {
        removeOutdated();
        Iterator it = this.mEventArraySelfLocked.iterator();
        j3 = 0;
        while (it.hasNext()) {
          UidEventRecord uidEventRecord = (UidEventRecord) it.next();
          j3 +=
              calculateOverlapPeriod(
                  j, j2, uidEventRecord.runningStartTime, uidEventRecord.runningEndTime);
        }
      }
      return j3;
    }

    public final void removeOutdated() {
      long currentTimeMillis = System.currentTimeMillis();
      while (!this.mEventArraySelfLocked.isEmpty()
          && currentTimeMillis - BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS
              > ((UidEventRecord) this.mEventArraySelfLocked.getFirst()).runningEndTime) {
        this.mEventArraySelfLocked.removeFirst();
      }
      if (this.mEventArraySelfLocked.isEmpty()) {
        return;
      }
      ((UidEventRecord) this.mEventArraySelfLocked.getFirst()).removeOutdated(currentTimeMillis);
    }

    public final boolean isArgumentsValid(long j, long j2) {
      return System.currentTimeMillis()
                  - BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS
              <= j
          && j <= j2;
    }
  }
}
