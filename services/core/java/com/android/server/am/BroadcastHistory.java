package com.android.server.am;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.jobs.XmlUtils;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/* loaded from: classes.dex */
public class BroadcastHistory {
  public final int MAX_BROADCAST_HISTORY;
  public final int MAX_BROADCAST_SUMMARY_HISTORY;
  public final String[] mAbortedBroadcastHistory;
  public final BroadcastRecord[] mBroadcastHistory;
  public final Intent[] mBroadcastSummaryHistory;
  public final String[] mBroadcastSummaryHistoryToString;
  public final ActivityManagerService mService;
  public final long[] mSummaryHistoryDispatchTime;
  public final long[] mSummaryHistoryEnqueueTime;
  public final long[] mSummaryHistoryFinishTime;
  public static final int MAX_DELAYED_BROADCAST_HISTORY =
      BroadcastConstants.MAX_HISTORY_DELAYED_BROADCAST;
  public static final int MAX_ABORTED_BROADCAST_HISTORY =
      BroadcastConstants.MAX_HISTORY_ABORTED_BROADCAST;
  public final ArrayList mPendingBroadcasts = new ArrayList();
  public int mHistoryNext = 0;
  public int mSummaryHistoryNext = 0;
  public int mAbortedHistoryNext = 0;

  public final int ringAdvance(int i, int i2, int i3) {
    int i4 = i + i2;
    if (i4 < 0) {
      return i3 - 1;
    }
    if (i4 >= i3) {
      return 0;
    }
    return i4;
  }

  public BroadcastHistory(
      ActivityManagerService activityManagerService, BroadcastConstants broadcastConstants) {
    int i = broadcastConstants.MAX_HISTORY_COMPLETE_SIZE;
    this.MAX_BROADCAST_HISTORY = i;
    int i2 = broadcastConstants.MAX_HISTORY_SUMMARY_SIZE;
    this.MAX_BROADCAST_SUMMARY_HISTORY = i2;
    this.mBroadcastHistory = new BroadcastRecord[i];
    this.mBroadcastSummaryHistory = new Intent[i2];
    this.mSummaryHistoryEnqueueTime = new long[i2];
    this.mSummaryHistoryDispatchTime = new long[i2];
    this.mSummaryHistoryFinishTime = new long[i2];
    this.mBroadcastSummaryHistoryToString = new String[i2];
    this.mAbortedBroadcastHistory = new String[MAX_ABORTED_BROADCAST_HISTORY];
    this.mService = activityManagerService;
  }

  public void onBroadcastEnqueuedLocked(BroadcastRecord broadcastRecord) {
    this.mPendingBroadcasts.add(broadcastRecord);
  }

  public void onBroadcastFinishedLocked(BroadcastRecord broadcastRecord) {
    this.mPendingBroadcasts.remove(broadcastRecord);
    addBroadcastToHistoryLocked(broadcastRecord);
  }

  public void addBroadcastToHistoryLocked(BroadcastRecord broadcastRecord) {
    BroadcastRecord maybeStripForHistory = broadcastRecord.maybeStripForHistory();
    BroadcastRecord[] broadcastRecordArr = this.mBroadcastHistory;
    int i = this.mHistoryNext;
    broadcastRecordArr[i] = maybeStripForHistory;
    this.mHistoryNext = ringAdvance(i, 1, this.MAX_BROADCAST_HISTORY);
    this.mService.mExt.addBroadcastSummaryHistoryLocked(this, maybeStripForHistory);
    long[] jArr = this.mSummaryHistoryEnqueueTime;
    int i2 = this.mSummaryHistoryNext;
    jArr[i2] = maybeStripForHistory.enqueueClockTime;
    this.mSummaryHistoryDispatchTime[i2] = maybeStripForHistory.dispatchClockTime;
    this.mSummaryHistoryFinishTime[i2] = System.currentTimeMillis();
    this.mSummaryHistoryNext =
        ringAdvance(this.mSummaryHistoryNext, 1, this.MAX_BROADCAST_SUMMARY_HISTORY);
  }

  @NeverCompile
  public void dumpDebug(ProtoOutputStream protoOutputStream) {
    for (int i = 0; i < this.mPendingBroadcasts.size(); i++) {
      ((BroadcastRecord) this.mPendingBroadcasts.get(i))
          .dumpDebug(protoOutputStream, 2246267895815L);
    }
    int i2 = this.mHistoryNext;
    int i3 = i2;
    do {
      i3 = ringAdvance(i3, -1, this.MAX_BROADCAST_HISTORY);
      BroadcastRecord broadcastRecord = this.mBroadcastHistory[i3];
      if (broadcastRecord != null) {
        broadcastRecord.dumpDebug(protoOutputStream, 2246267895813L);
      }
    } while (i3 != i2);
    int i4 = this.mSummaryHistoryNext;
    int i5 = i4;
    do {
      i5 = ringAdvance(i5, -1, this.MAX_BROADCAST_SUMMARY_HISTORY);
      Intent intent = this.mBroadcastSummaryHistory[i5];
      if (intent != null) {
        long start = protoOutputStream.start(2246267895814L);
        intent.dumpDebug(protoOutputStream, 1146756268033L, false, true, true, false);
        protoOutputStream.write(1112396529666L, this.mSummaryHistoryEnqueueTime[i5]);
        protoOutputStream.write(1112396529667L, this.mSummaryHistoryDispatchTime[i5]);
        protoOutputStream.write(1112396529668L, this.mSummaryHistoryFinishTime[i5]);
        protoOutputStream.end(start);
      }
    } while (i5 != i4);
  }

  /* JADX WARN: Removed duplicated region for block: B:10:0x010b A[EDGE_INSN: B:10:0x010b->B:11:0x010b BREAK  A[LOOP:0: B:5:0x004c->B:9:0x01fc], SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:9:0x01fc A[LOOP:0: B:5:0x004c->B:9:0x01fc, LOOP_END] */
  @NeverCompile
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean dumpLocked(
      PrintWriter printWriter,
      String str,
      String str2,
      SimpleDateFormat simpleDateFormat,
      boolean z,
      boolean z2) {
    boolean z3;
    String str3;
    boolean z4;
    boolean z5;
    int i;
    BroadcastHistory broadcastHistory = this;
    printWriter.println("  Pending broadcasts:");
    boolean isEmpty = broadcastHistory.mPendingBroadcasts.isEmpty();
    String str4 = XmlUtils.STRING_ARRAY_SEPARATOR;
    if (isEmpty) {
      printWriter.println("    <empty>");
    } else {
      for (int size = broadcastHistory.mPendingBroadcasts.size() - 1; size >= 0; size--) {
        BroadcastRecord broadcastRecord =
            (BroadcastRecord) broadcastHistory.mPendingBroadcasts.get(size);
        printWriter.print("  Broadcast #");
        printWriter.print(size);
        printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
        broadcastRecord.dump(printWriter, "    ", simpleDateFormat);
      }
    }
    int i2 = broadcastHistory.mHistoryNext;
    int i3 = -1;
    boolean z6 = z2;
    int i4 = i2;
    int i5 = -1;
    boolean z7 = false;
    while (true) {
      i4 = broadcastHistory.ringAdvance(i4, i3, broadcastHistory.MAX_BROADCAST_HISTORY);
      BroadcastRecord broadcastRecord2 = broadcastHistory.mBroadcastHistory[i4];
      if (broadcastRecord2 != null) {
        i5++;
        if (str == null || str.equals(broadcastRecord2.callerPackage)) {
          if (!z7) {
            if (z6) {
              printWriter.println();
            }
            printWriter.println("  Historical broadcasts [" + str2 + "]:");
            z6 = true;
            z7 = true;
          }
          if (z) {
            StringBuilder sb = new StringBuilder();
            z3 = z6;
            sb.append("  Historical Broadcast ");
            sb.append(str2);
            sb.append(" #");
            printWriter.print(sb.toString());
            printWriter.print(i5);
            printWriter.println(str4);
            broadcastRecord2.dump(printWriter, "    ", simpleDateFormat);
            str3 = str4;
            z4 = false;
          } else {
            z3 = z6;
            printWriter.print("  #");
            printWriter.print(i5);
            printWriter.print(": ");
            printWriter.println(broadcastRecord2);
            printWriter.print("    ");
            str3 = str4;
            z4 = false;
            printWriter.println(broadcastRecord2.intent.toShortString(false, true, true, false));
            ComponentName componentName = broadcastRecord2.targetComp;
            if (componentName != null && componentName != broadcastRecord2.intent.getComponent()) {
              printWriter.print("    targetComp: ");
              printWriter.println(broadcastRecord2.targetComp.toShortString());
            }
            Bundle extras = broadcastRecord2.intent.getExtras();
            if (extras != null) {
              printWriter.print("    extras: ");
              printWriter.println(extras.toString());
            }
          }
          z5 = z3;
          if (i4 != i2) {
            break;
          }
          z6 = z5;
          str4 = str3;
          i3 = -1;
          broadcastHistory = this;
        }
      }
      str3 = str4;
      z5 = z6;
      z4 = false;
      if (i4 != i2) {}
    }
    if (str == null) {
      int i6 = this.mSummaryHistoryNext;
      if (z) {
        i = i6;
        i5 = -1;
      } else {
        i = i6;
        int i7 = i5;
        while (i7 > 0 && i != i6) {
          boolean z8 = z5;
          i = ringAdvance(i, -1, this.MAX_BROADCAST_SUMMARY_HISTORY);
          if (this.mBroadcastHistory[i] != null) {
            i7--;
          }
          z5 = z8;
        }
        z5 = z5;
        z4 = z7;
      }
      while (true) {
        i = ringAdvance(i, -1, this.MAX_BROADCAST_SUMMARY_HISTORY);
        String str5 = this.mBroadcastSummaryHistoryToString[i];
        if (str5 != null) {
          if (!z4) {
            if (z5) {
              printWriter.println();
            }
            printWriter.println("  Historical broadcasts summary [" + str2 + "]:");
            z5 = true;
            z4 = true;
          }
          if (!z && i5 >= 50) {
            printWriter.println("  ...");
            break;
          }
          i5++;
          printWriter.print("  #");
          printWriter.print(i5);
          printWriter.print(": ");
          printWriter.println(str5);
          printWriter.print("    ");
          TimeUtils.formatDuration(
              this.mSummaryHistoryDispatchTime[i] - this.mSummaryHistoryEnqueueTime[i],
              printWriter);
          printWriter.print(" dispatch ");
          TimeUtils.formatDuration(
              this.mSummaryHistoryFinishTime[i] - this.mSummaryHistoryDispatchTime[i], printWriter);
          printWriter.println(" finish");
          printWriter.print("    enq=");
          printWriter.print(simpleDateFormat.format(new Date(this.mSummaryHistoryEnqueueTime[i])));
          printWriter.print(" disp=");
          printWriter.print(simpleDateFormat.format(new Date(this.mSummaryHistoryDispatchTime[i])));
          printWriter.print(" fin=");
          printWriter.println(simpleDateFormat.format(new Date(this.mSummaryHistoryFinishTime[i])));
          z5 = z5;
        }
        if (i == i6) {
          break;
        }
      }
    }
    if (z && str == null) {
      this.mService.mExt.dumpForBroadcastQueueLocked(this, str2, printWriter, z5);
    }
    return z5;
  }
}
