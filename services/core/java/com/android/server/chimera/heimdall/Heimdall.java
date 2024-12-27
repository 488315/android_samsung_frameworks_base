package com.android.server.chimera.heimdall;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;

import com.android.server.am.ActivityManagerService;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class Heimdall {
    public boolean DISABLED;
    public final boolean KILL_DISABLED;
    public final int PROP_ALWAYS_RUNNING_DISABLE;
    public int mAnomalyType;
    public final int[] mAnomalyTypeArray;
    public final Context mContext;
    public final HeimdallPhaseManager mHeimdallPhaseManager;
    public final HeimdallReportManager mHeimdallReportManager;
    public final PhaseHandler mPhaseHandler;
    public final Uri mUriAlwaysRunningGlobalQuota;
    public final Uri mUriAnomalyTypeEnable;
    public final Uri mUriRandomSampleRate;
    public final Uri mUriReportHourInterval;
    public final Uri mUriSpecUpdate;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PhaseHandler extends Handler {
        public final AtomicInteger mNumberOfPendingMessages;

        public PhaseHandler(Looper looper) {
            super(looper);
            this.mNumberOfPendingMessages = new AtomicInteger(0);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x004a, code lost:

           if (r6.allowAlwaysRunning != false) goto L18;
        */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x004f, code lost:

           if (r6.ignoreAlwaysRunningSpecKill == false) goto L21;
        */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0051, code lost:

           r4 = 2;
        */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x0053, code lost:

           r4 = true;
        */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r12) {
            /*
                Method dump skipped, instructions count: 782
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.chimera.heimdall.Heimdall.PhaseHandler.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScpmManager extends BroadcastReceiver {
        public final Uri POLICY_ITEM_URI =
                Uri.withAppendedPath(
                        Uri.withAppendedPath(
                                Uri.parse("content://com.samsung.android.sm.policy"),
                                "policy_item"),
                        "heimdall");

        public ScpmManager(Context context) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("sec.app.policy.UPDATE.heimdall");
            context.registerReceiver(this, intentFilter, 2);
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Cursor query;
            String str;
            String str2;
            if ("sec.app.policy.UPDATE.heimdall".equals(intent.getAction())) {
                String trim = SystemProperties.get("ro.product.device", "").trim();
                if ("".equals(trim)
                        || (query =
                                        context.getContentResolver()
                                                .query(
                                                        this.POLICY_ITEM_URI,
                                                        null,
                                                        null,
                                                        null,
                                                        null))
                                == null) {
                    return;
                }
                while (true) {
                    if (!query.moveToNext()) {
                        break;
                    }
                    try {
                        str = query.getString(1);
                        str2 = query.getString(2);
                    } catch (Exception unused) {
                        str = null;
                        str2 = null;
                    }
                    if (str != null && str2 != null && str.equals(trim)) {
                        try {
                            if ("0".equals(str2)) {
                                SystemProperties.set("persist.sys.heimdalld.disable", "0");
                                Heimdall.this.DISABLED = false;
                                Heimdall.log("DISABLED set false");
                            } else if ("1".equals(str2)) {
                                SystemProperties.set("persist.sys.heimdalld.disable", "1");
                                Heimdall.this.DISABLED = true;
                                Heimdall.log("DISABLED set true");
                            }
                        } catch (Exception e) {
                            Heimdall.log(e.getMessage());
                            query.close();
                            return;
                        }
                    }
                }
                query.close();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver() {
            super(Heimdall.this.mPhaseHandler);
        }

        /* JADX WARN: Code restructure failed: missing block: B:37:0x0054, code lost:

           if ((r3 & 4) == 0) goto L27;
        */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0051  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x0052 A[Catch: Exception -> 0x0021, TryCatch #1 {Exception -> 0x0021, blocks: (B:6:0x000c, B:8:0x0014, B:11:0x0024, B:13:0x002f, B:17:0x0040, B:23:0x005b, B:24:0x005f, B:33:0x006c, B:36:0x0052, B:38:0x0039, B:41:0x006d, B:43:0x0077, B:45:0x0083, B:47:0x008d, B:51:0x009f, B:53:0x00a6, B:55:0x00b0, B:57:0x00c8, B:26:0x0060, B:27:0x0067), top: B:5:0x000c, inners: #0 }] */
        @Override // android.database.ContentObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onChange(boolean r4, android.net.Uri r5) {
            /*
                Method dump skipped, instructions count: 231
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.chimera.heimdall.Heimdall.SettingsObserver.onChange(boolean,"
                        + " android.net.Uri):void");
        }
    }

    public Heimdall(Looper looper, Context context, ActivityManagerService activityManagerService) {
        String str = SystemProperties.get("persist.sys.heimdalld.alwaysrunning_disable", "");
        str.getClass();
        this.PROP_ALWAYS_RUNNING_DISABLE = !str.equals("0") ? !str.equals("1") ? -1 : 1 : 0;
        this.DISABLED =
                "true".equals(SystemProperties.get("persist.sys.heimdalld.disable", "false"));
        boolean equals =
                "1".equals(SystemProperties.get("persist.sys.heimdalld.kill_disable", "0"));
        this.KILL_DISABLED = equals;
        this.mAnomalyTypeArray = new int[] {16, 8, 4, 2, 1};
        this.mAnomalyType = 24;
        Uri uriFor = Settings.Global.getUriFor("heimdall_spec_update");
        this.mUriSpecUpdate = uriFor;
        Uri uriFor2 = Settings.Global.getUriFor("heimdall_anomaly_type_enable");
        this.mUriAnomalyTypeEnable = uriFor2;
        Uri uriFor3 = Settings.Global.getUriFor("heimdall_always_running_global_quota");
        this.mUriAlwaysRunningGlobalQuota = uriFor3;
        Uri uriFor4 = Settings.Global.getUriFor("heimdall_report_hour_interval");
        this.mUriReportHourInterval = uriFor4;
        Uri uriFor5 = Settings.Global.getUriFor("heimdall_random_sample_rate");
        this.mUriRandomSampleRate = uriFor5;
        try {
            HeimdallPhaseManager heimdallPhaseManager =
                    new HeimdallPhaseManager(looper, context, activityManagerService);
            this.mHeimdallPhaseManager = heimdallPhaseManager;
            this.mHeimdallReportManager = heimdallPhaseManager.mHeimdallReportManager;
            this.mPhaseHandler = new PhaseHandler(looper);
            new ScpmManager(context);
            SettingsObserver settingsObserver = new SettingsObserver();
            this.mContext = context;
            if (equals) {
                HeimdallKillManager heimdallKillManager = heimdallPhaseManager.mHeimdallKillManager;
                heimdallKillManager.getClass();
                log("KILL_DISABLED set true");
                heimdallKillManager.KILL_DISABLED = true;
            }
            ContentResolver contentResolver = context.getContentResolver();
            contentResolver.registerContentObserver(uriFor, false, settingsObserver);
            contentResolver.registerContentObserver(uriFor2, false, settingsObserver);
            contentResolver.registerContentObserver(uriFor3, false, settingsObserver);
            contentResolver.registerContentObserver(uriFor4, false, settingsObserver);
            contentResolver.registerContentObserver(uriFor5, false, settingsObserver);
        } catch (Exception e) {
            log("Heimdall init failed " + e.getMessage());
            e.printStackTrace();
            this.DISABLED = true;
        }
        log("DISABLED=" + this.DISABLED + " KILL_DISABLED=" + this.KILL_DISABLED);
        if (this.DISABLED) {
            return;
        }
        log("SCAN_VERSION=1");
    }

    public static void log(String str) {
        Log.i("Heimdall", str);
    }

    public static native long[] readMemtrackMemory(int i);

    public final void checkAlwaysRunningProcesses(List list) {
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            HeimdallAlwaysRunningProcInfo heimdallAlwaysRunningProcInfo =
                    (HeimdallAlwaysRunningProcInfo) it.next();
            if (this.mHeimdallReportManager.mReportedAlwaysRunningProcesses.contains(
                    heimdallAlwaysRunningProcInfo.processName)) {
                log(
                        "Skip always running checking for "
                                + heimdallAlwaysRunningProcInfo.processName
                                + " , heimdall already reported it");
            } else {
                PhaseHandler phaseHandler = this.mPhaseHandler;
                Message obtainMessage =
                        phaseHandler.obtainMessage(2, heimdallAlwaysRunningProcInfo);
                phaseHandler.mNumberOfPendingMessages.incrementAndGet();
                phaseHandler.sendMessage(obtainMessage);
            }
        }
    }

    public final void dumpInfo(PrintWriter printWriter, String[] strArr) {
        if (printWriter == null || strArr == null) {
            return;
        }
        printWriter.println("== heimdall dump start ==");
        try {
            int length = strArr.length;
            HeimdallPhaseManager heimdallPhaseManager = this.mHeimdallPhaseManager;
            if (length == 1) {
                printWriter.println("Configurations");
                StringBuilder sb = new StringBuilder("  feature enable: ");
                sb.append(!this.DISABLED);
                printWriter.println(sb.toString());
                if (!this.DISABLED && heimdallPhaseManager != null) {
                    StringBuilder sb2 = new StringBuilder("  kill: ");
                    sb2.append(!this.KILL_DISABLED);
                    printWriter.println(sb2.toString());
                    printWriter.println("  SCAN_VERSION: 1");
                    printWriter.println(
                            "  SPEC_VERSION: "
                                    + heimdallPhaseManager.mHeimdallTriggerManager.mSpecVersion);
                    printWriter.println(
                            "  Load spec success: "
                                    + this.mHeimdallPhaseManager
                                            .mHeimdallTriggerManager
                                            .mSpecManager
                                            .mLoadSuccess);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("  Anomaly type enable: ");
                    int i = this.mAnomalyType;
                    int length2 = this.mAnomalyTypeArray.length;
                    StringBuilder sb4 = new StringBuilder();
                    for (int i2 = 0; i2 < length2; i2++) {
                        sb4.append(i % 2);
                        i >>= 1;
                        if (i2 != length2 - 1) {
                            sb4.append(",");
                        }
                    }
                    sb3.append(sb4.reverse().toString());
                    printWriter.println(sb3.toString());
                    this.mHeimdallReportManager.getClass();
                    printWriter.println(
                            String.format(
                                    "  Random sample rate: "
                                            + HeimdallReportManager.sRandomSampleRate
                                            + ", block %.1f%% reports",
                                    Double.valueOf(
                                            100.0d
                                                    - (HeimdallReportManager.sRandomSampleRate
                                                            * 0.1d))));
                    heimdallPhaseManager.dumpKillStatus(printWriter);
                    heimdallPhaseManager.dumpProcessList(printWriter);
                    printWriter.println("\nOthers");
                    PhaseHandler phaseHandler = this.mPhaseHandler;
                    if (phaseHandler != null) {
                        printWriter.println(
                                "  The number of pending message in phase handler: "
                                        + phaseHandler.mNumberOfPendingMessages.get());
                    }
                    HeimdallAlwaysRunningMonitor.INSTANCE.dump(printWriter);
                    heimdallPhaseManager.mHeimdallReportManager.dumpReportHistory(printWriter);
                    printWriter.println(
                            "report_interval(hour): "
                                    + heimdallPhaseManager
                                            .mHeimdallProcessList
                                            .mTimeoutReportProtectedHour);
                }
            } else if (strArr.length >= 2) {
                if (!this.DISABLED && heimdallPhaseManager != null) {
                    printWriter.println("available in noship binary");
                }
                printWriter.println("heimdall disabled");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        printWriter.println("== heimdall dump end ==");
    }

    public final void updateAlwaysRunningGlobalQuota(ContentResolver contentResolver) {
        int i = Settings.Global.getInt(contentResolver, "heimdall_always_running_global_quota", 0);
        if (i <= 0) {
            return;
        }
        HeimdallTriggerManager.HeimdallSpecManager heimdallSpecManager =
                this.mHeimdallPhaseManager.mHeimdallTriggerManager.mSpecManager;
        if (i <= 0) {
            heimdallSpecManager.getClass();
            return;
        }
        HeimdallTriggerManager.this.mAlwaysRunningGlobalQuotaSpec = i;
        Iterator it = heimdallSpecManager.mSpecMap.values().iterator();
        while (it.hasNext()) {
            for (HeimdallTriggerManager.HeimdallProcSpec heimdallProcSpec : (List) it.next()) {
                if (!heimdallProcSpec.ignoreAlwaysRunningSpecKill) {
                    heimdallProcSpec.alwaysRunningSpecSize =
                            Math.min(heimdallProcSpec.alwaysRunningSpecSizeInitial, i);
                }
            }
        }
    }

    public final void updateAnomalyType(ContentResolver contentResolver) {
        String string = Settings.Global.getString(contentResolver, "heimdall_anomaly_type_enable");
        if (string != null) {
            String[] split = string.split(",");
            int length = split.length;
            int[] iArr = this.mAnomalyTypeArray;
            if (length < iArr.length) {
                return;
            }
            for (int i = 0; i < iArr.length; i++) {
                String str = split[i];
                boolean equals = "1".equals(str);
                HeimdallPhaseManager heimdallPhaseManager = this.mHeimdallPhaseManager;
                if (equals) {
                    int i2 = iArr[i];
                    this.mAnomalyType |= i2;
                    if (i2 == 16) {
                        heimdallPhaseManager.mHeimdallTriggerManager.ENABLE_GLOBAL_KILL = true;
                    } else if (i2 == 8) {
                        heimdallPhaseManager.mHeimdallTriggerManager.ENABLE_SPEC_KILL = true;
                    }
                } else if ("0".equals(str)) {
                    int i3 = iArr[i];
                    this.mAnomalyType &= ~i3;
                    if (i3 == 16) {
                        heimdallPhaseManager.mHeimdallTriggerManager.ENABLE_GLOBAL_KILL = false;
                    } else if (i3 == 8) {
                        heimdallPhaseManager.mHeimdallTriggerManager.ENABLE_SPEC_KILL = false;
                    }
                }
            }
        }
    }

    public final void updateSpec(ContentResolver contentResolver) {
        int parseInt;
        String string = Settings.Global.getString(contentResolver, "heimdall_spec_update");
        if (string == null) {
            return;
        }
        for (String str : string.split(";")) {
            HeimdallTriggerManager heimdallTriggerManager =
                    this.mHeimdallPhaseManager.mHeimdallTriggerManager;
            HeimdallTriggerManager.HeimdallSpecManager heimdallSpecManager =
                    heimdallTriggerManager.mSpecManager;
            try {
                String[] split = str.split(",");
                if (split.length > 7
                        && (parseInt = Integer.parseInt(split[0]))
                                > heimdallTriggerManager.mSpecVersion) {
                    String[] strArr = new String[7];
                    boolean z = true;
                    System.arraycopy(split, 1, strArr, 0, 7);
                    String str2 = strArr[0];
                    List list = (List) heimdallSpecManager.mSpecMap.get(str2);
                    if (list != null) {
                        String str3 = strArr[1];
                        Iterator it = list.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            HeimdallTriggerManager.HeimdallProcSpec heimdallProcSpec =
                                    (HeimdallTriggerManager.HeimdallProcSpec) it.next();
                            if (str3.equals(heimdallProcSpec.procName)) {
                                if (heimdallProcSpec.specVersion >= parseInt) {
                                    z = false;
                                    break;
                                }
                                HeimdallTriggerManager.HeimdallProcSpec heimdallProcSpec2 =
                                        new HeimdallTriggerManager.HeimdallProcSpec(
                                                heimdallTriggerManager, strArr);
                                heimdallProcSpec.specSize = heimdallProcSpec2.specSize;
                                heimdallProcSpec.ignoreGlobalKill =
                                        heimdallProcSpec2.ignoreGlobalKill;
                                heimdallProcSpec.ignoreSpecKill = heimdallProcSpec2.ignoreSpecKill;
                                heimdallProcSpec.allowAlwaysRunning =
                                        heimdallProcSpec2.allowAlwaysRunning;
                                heimdallProcSpec.ignoreAlwaysRunningSpecKill =
                                        heimdallProcSpec2.ignoreAlwaysRunningSpecKill;
                                heimdallProcSpec.alwaysRunningSpecSize =
                                        heimdallProcSpec2.alwaysRunningSpecSize;
                                heimdallProcSpec.specVersion = parseInt;
                                z = false;
                            }
                        }
                    } else {
                        list = new ArrayList();
                        heimdallSpecManager.mSpecMap.put(str2, list);
                    }
                    if (z) {
                        list.add(
                                new HeimdallTriggerManager.HeimdallProcSpec(
                                        heimdallTriggerManager, strArr));
                    }
                }
            } catch (RuntimeException e) {
                log("Update spec catch exception! " + e.getMessage());
            }
        }
    }
}
