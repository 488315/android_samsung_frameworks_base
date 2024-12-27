package com.android.server.am;

import android.content.Intent;

import java.io.PrintWriter;
import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public final class BroadcastHistory {
    public static final int MAX_ABORTED_BROADCAST_HISTORY;
    public final int MAX_BROADCAST_HISTORY;
    public final int MAX_BROADCAST_SUMMARY_HISTORY;
    public final String[] mAbortedBroadcastHistory;
    public SoftReference mBCBrHistoryRef;
    public final BroadcastRecord[] mBroadcastHistory;
    public final Intent[] mBroadcastSummaryHistory;
    public final String[] mBroadcastSummaryHistoryToString;
    public final ActivityManagerService mService;
    public final long[] mSummaryHistoryDispatchTime;
    public final long[] mSummaryHistoryEnqueueTime;
    public final long[] mSummaryHistoryFinishTime;
    public final ArrayList mFrozenBroadcasts = new ArrayList();
    public final ArrayList mPendingBroadcasts = new ArrayList();
    public int mHistoryNext = 0;
    public int mSummaryHistoryNext = 0;
    public int mAbortedHistoryNext = 0;

    static {
        int i = BroadcastConstants.MAX_HISTORY_ABORTED_BROADCAST;
        MAX_ABORTED_BROADCAST_HISTORY = BroadcastConstants.MAX_HISTORY_ABORTED_BROADCAST;
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

    public static void dumpBroadcastList(
            PrintWriter printWriter,
            SimpleDateFormat simpleDateFormat,
            ArrayList arrayList,
            String str) {
        printWriter.print("  ");
        printWriter.print(str);
        printWriter.println(" broadcasts:");
        if (arrayList.isEmpty()) {
            printWriter.println("    <empty>");
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            BroadcastRecord broadcastRecord = (BroadcastRecord) arrayList.get(size);
            printWriter.print(str);
            printWriter.print("  broadcast #");
            printWriter.print(size);
            printWriter.println(":");
            broadcastRecord.dump(printWriter, simpleDateFormat);
        }
    }

    public static int ringAdvance(int i, int i2, int i3) {
        int i4 = i + i2;
        if (i4 < 0) {
            return i3 - 1;
        }
        if (i4 >= i3) {
            return 0;
        }
        return i4;
    }
}
