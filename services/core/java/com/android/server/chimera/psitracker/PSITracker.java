package com.android.server.chimera.psitracker;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.server.backup.BackupManagerConstants;
import com.android.server.chimera.SystemRepository;

import java.lang.ref.WeakReference;
import java.util.Calendar;

public final class PSITracker {
    public static boolean mFirstTriggeredAfterBooting;
    public PSIDBManager db;
    public final AnonymousClass1 mAvailMemRecord240AlarmListener =
            new AlarmManager
                    .OnAlarmListener() { // from class:
                                         // com.android.server.chimera.psitracker.PSITracker.1
                @Override // android.app.AlarmManager.OnAlarmListener
                public final void onAlarm() {
                    PSITracker.this.scheduleAvailMem240PeriodRecord("240 Alarm fired");
                    PSITracker.this.mHandler.sendMessage(PSITracker.this.mHandler.obtainMessage(1));
                }
            };
    public final PSITrackerCollector mCollector = new PSITrackerCollector();
    public final Context mContext;
    public final PSITrackerHandler mHandler;
    public final SystemRepository mSystemRepository;

    public final class PSITrackerHandler extends Handler {
        public PSITrackerHandler(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Removed duplicated region for block: B:64:0x00f7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r12) {
            /*
                Method dump skipped, instructions count: 316
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.chimera.psitracker.PSITracker.PSITrackerHandler.handleMessage(android.os.Message):void");
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.chimera.psitracker.PSITracker$1] */
    public PSITracker(Context context, Looper looper, SystemRepository systemRepository) {
        this.mSystemRepository = systemRepository;
        this.mContext = context;
        if (context != null) {
            PSIDBManager.mContext = new WeakReference(context);
        }
        this.mHandler = new PSITrackerHandler(looper);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00d5, code lost:

       r8 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00da, code lost:

       if (r8 >= r0.size()) goto L41;
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00dc, code lost:

       r9.println("AvailMem ID : " + r0.get(r8) + "Availmem : " + r1.get(r8) + ", running : " + r2.get(r8) + ", cached : " + r3.get(r8) + ", checkTime : " + r4.get(r8));
       r8 = r8 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0124, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00d2, code lost:

       if (r11 == null) goto L30;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void getPSIValueListDump(java.io.PrintWriter r9, long r10, long r12) {
        /*
            Method dump skipped, instructions count: 299
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.chimera.psitracker.PSITracker.getPSIValueListDump(java.io.PrintWriter,"
                    + " long, long):void");
    }

    public final void scheduleAvailMem240PeriodRecord(String str) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        long currentTimeMillis = System.currentTimeMillis();
        calendar.setTimeInMillis(
                System.currentTimeMillis()
                        + BackupManagerConstants.DEFAULT_KEY_VALUE_BACKUP_INTERVAL_MILLISECONDS);
        Log.d(
                "PSITracker",
                "Schedule next trigger time interval: 4 /now Time: " + currentTimeMillis);
        Log.d(
                "PSITracker",
                "Schedule next trigger time: " + calendar.getTimeInMillis() + " reason = " + str);
        AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        alarmManager.cancel(this.mAvailMemRecord240AlarmListener);
        alarmManager.set(
                1,
                calendar.getTimeInMillis(),
                "RecordAvailMem240",
                this.mAvailMemRecord240AlarmListener,
                this.mHandler);
    }
}
