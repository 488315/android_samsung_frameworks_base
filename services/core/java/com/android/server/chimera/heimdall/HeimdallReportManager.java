package com.android.server.chimera.heimdall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SemHqmManager;
import android.os.SystemClock;
import android.os.UserHandle;

import com.android.internal.util.RingBuffer;
import com.android.server.am.ActivityManagerService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HeimdallReportManager {
    public static int sRandomSampleRate = 10;
    public final BigdataManager mBigdataManager;
    public final Context mContext;
    public final PackageManager mPackageManager;
    public final ReportHandler mReportHandler;
    public final RingBuffer mReportHistory = new RingBuffer(String.class, 20);
    public final CopyOnWriteArraySet mReportedAlwaysRunningProcesses = new CopyOnWriteArraySet();
    public final ActivityManagerService mService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BigdataInfo {
        public int globalKillCnt;
        public int memoryUsage;
        public String packageName;
        public int realKillCnt;
        public int specKillCnt;
        public String versionName;

        /* renamed from: -$$Nest$mtoJsonData, reason: not valid java name */
        public static String m326$$Nest$mtoJsonData(BigdataInfo bigdataInfo) {
            StringBuilder sb = new StringBuilder("\"pkgName\":\"");
            sb.append(bigdataInfo.packageName);
            sb.append("\",\"version\":\"");
            sb.append(bigdataInfo.versionName);
            sb.append("\",\"memUsage\":\"");
            sb.append(bigdataInfo.memoryUsage * 1024);
            sb.append("\",\"specKillCnt\":\"");
            sb.append(bigdataInfo.specKillCnt);
            sb.append("\",\"glbKillCnt\":\"");
            sb.append(bigdataInfo.globalKillCnt);
            sb.append("\",\"realKillCnt\":\"");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(bigdataInfo.realKillCnt, sb, "\"");
        }

        public BigdataInfo(String str) {
            String[] split = str.split(",");
            if (split.length != 6) {
                return;
            }
            this.packageName = split[0];
            this.versionName = split[1];
            this.memoryUsage = Integer.parseInt(split[2]);
            this.specKillCnt = Integer.parseInt(split[3]);
            this.globalKillCnt = Integer.parseInt(split[4]);
            this.realKillCnt = Integer.parseInt(split[5]);
        }

        public final String toString() {
            return this.packageName
                    + ","
                    + this.versionName
                    + ","
                    + this.memoryUsage
                    + ","
                    + this.specKillCnt
                    + ","
                    + this.globalKillCnt
                    + ","
                    + this.realKillCnt;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BigdataManager {
        public final ArrayList mBigdataInfoList = new ArrayList();
        public final SemHqmManager mSemHqmManager;
        public final /* synthetic */ HeimdallReportManager this$0;

        public BigdataManager(HeimdallReportManager heimdallReportManager) {
            this.mSemHqmManager =
                    (SemHqmManager)
                            heimdallReportManager.mContext.getSystemService("HqmManagerService");
            File file = new File("/data/misc/pageboost/heimdall_last_bigdata_string");
            try {
                if (file.exists()) {
                    try {
                        FileInputStream fileInputStream =
                                new FileInputStream(
                                        "/data/misc/pageboost/heimdall_last_bigdata_string");
                        try {
                            InputStreamReader inputStreamReader =
                                    new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                            try {
                                BufferedReader bufferedReader =
                                        new BufferedReader(inputStreamReader);
                                while (true) {
                                    try {
                                        String readLine = bufferedReader.readLine();
                                        if (readLine == null) {
                                            break;
                                        }
                                        BigdataInfo bigdataInfo = new BigdataInfo(readLine);
                                        if (bigdataInfo.packageName == null) {
                                            break;
                                        } else {
                                            addBigdataInfoList(bigdataInfo);
                                        }
                                    } finally {
                                    }
                                }
                                if (file.delete()) {
                                    Heimdall.log(
                                            "Big data backup file is deleted during importing.");
                                } else {
                                    Heimdall.log(
                                            "Big data backup file is NOT deleted during"
                                                + " importing.");
                                }
                                Heimdall.log("Import BigdataInfoList");
                                bufferedReader.close();
                                inputStreamReader.close();
                                fileInputStream.close();
                            } finally {
                            }
                        } catch (Throwable th) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (IOException | SecurityException e) {
                        Heimdall.log(e.getMessage());
                    }
                }
            } catch (SecurityException e2) {
                Heimdall.log(e2.getMessage());
            }
        }

        public final void addBigdataInfoList(BigdataInfo bigdataInfo) {
            Iterator it = this.mBigdataInfoList.iterator();
            while (it.hasNext()) {
                BigdataInfo bigdataInfo2 = (BigdataInfo) it.next();
                if (bigdataInfo2.packageName.equals(bigdataInfo.packageName)
                        && bigdataInfo2.versionName.equals(bigdataInfo.versionName)) {
                    bigdataInfo2.globalKillCnt += bigdataInfo.globalKillCnt;
                    bigdataInfo2.specKillCnt += bigdataInfo.specKillCnt;
                    bigdataInfo2.realKillCnt += bigdataInfo.realKillCnt;
                    int i = bigdataInfo2.memoryUsage;
                    int i2 = bigdataInfo.memoryUsage;
                    if (i < i2) {
                        bigdataInfo2.memoryUsage = i2;
                    }
                    Heimdall.log(
                            "add BigdataInfo to list. "
                                    + BigdataInfo.m326$$Nest$mtoJsonData(bigdataInfo2));
                    return;
                }
            }
            this.mBigdataInfoList.add(bigdataInfo);
            Heimdall.log(
                    "add BigdataInfo to list. " + BigdataInfo.m326$$Nest$mtoJsonData(bigdataInfo));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BroadcastManager extends BroadcastReceiver {
        public BroadcastManager() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!"android.intent.action.ACTION_SHUTDOWN".equals(action)
                    && !"android.intent.action.REBOOT".equals(action)) {
                if ("com.sec.android.intent.action.HQM_UPDATE_REQ".equals(action)) {
                    BigdataManager bigdataManager = HeimdallReportManager.this.mBigdataManager;
                    Iterator it = bigdataManager.mBigdataInfoList.iterator();
                    while (it.hasNext()) {
                        BigdataInfo bigdataInfo = (BigdataInfo) it.next();
                        if (bigdataManager.mSemHqmManager != null) {
                            String m326$$Nest$mtoJsonData =
                                    BigdataInfo.m326$$Nest$mtoJsonData(bigdataInfo);
                            if (m326$$Nest$mtoJsonData.length() < 1024) {
                                if (bigdataManager.mSemHqmManager.sendHWParamToHQM(
                                        0,
                                        "AP",
                                        "HDMM",
                                        "sm",
                                        "0.0",
                                        "sec",
                                        "",
                                        m326$$Nest$mtoJsonData,
                                        "")) {
                                    Heimdall.log(
                                            "Successful to send data to HQM. "
                                                    + BigdataInfo.m326$$Nest$mtoJsonData(
                                                            bigdataInfo));
                                } else {
                                    Heimdall.log(
                                            "Failed to send data to HQM."
                                                    + BigdataInfo.m326$$Nest$mtoJsonData(
                                                            bigdataInfo));
                                }
                            }
                        }
                    }
                    bigdataManager.mBigdataInfoList.clear();
                    return;
                }
                return;
            }
            BigdataManager bigdataManager2 = HeimdallReportManager.this.mBigdataManager;
            if (bigdataManager2.mBigdataInfoList.size() == 0) {
                return;
            }
            File file = new File("/data/misc/pageboost/heimdall_last_bigdata_string");
            try {
                if (file.exists()) {
                    if (file.delete()) {
                        Heimdall.log("Big data backup file is deleted during exporting.");
                    } else {
                        Heimdall.log("Big data backup file is NOT deleted during exporting.");
                    }
                }
                try {
                    FileOutputStream fileOutputStream =
                            new FileOutputStream(
                                    "/data/misc/pageboost/heimdall_last_bigdata_string");
                    try {
                        OutputStreamWriter outputStreamWriter =
                                new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                        try {
                            Iterator it2 = bigdataManager2.mBigdataInfoList.iterator();
                            while (it2.hasNext()) {
                                outputStreamWriter.append(
                                        (CharSequence)
                                                (((BigdataInfo) it2.next()).toString() + "\n"));
                            }
                            Heimdall.log("Export BigdataInfoList");
                            outputStreamWriter.close();
                            fileOutputStream.close();
                        } finally {
                        }
                    } catch (Throwable th) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (IOException | SecurityException e) {
                    Heimdall.log(e.getMessage());
                }
            } catch (SecurityException e2) {
                Heimdall.log(e2.getMessage());
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ReportHandler extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Object lock;
        public long mLastSendTime;
        public final Queue mReportData;

        public ReportHandler(Looper looper) {
            super(looper);
            this.mReportData = new LinkedList();
            this.lock = new Object();
        }

        public final void addReportHistory(
                HeimdallAlwaysRunningProcInfo heimdallAlwaysRunningProcInfo) {
            HeimdallReportManager.this.mReportHistory.append(
                    new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault())
                                    .format(new Date())
                            + ",pid="
                            + heimdallAlwaysRunningProcInfo.pid
                            + ",processName="
                            + heimdallAlwaysRunningProcInfo.processName
                            + ",packageName="
                            + heimdallAlwaysRunningProcInfo.packageName
                            + ",abnormalType="
                            + heimdallAlwaysRunningProcInfo.abnormalType);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            try {
                if (message.what != 1) {
                    return;
                }
                synchronized (this.lock) {
                    try {
                        removeMessages(1);
                        HeimdallAlwaysRunningProcInfo poll = poll();
                        if (poll == null) {
                            return;
                        }
                        if (!this.mReportData.isEmpty()) {
                            Heimdall.log("report queue poll one but still not empty");
                            sendMessageDelayed(obtainMessage(1), 300000L);
                        }
                        this.mLastSendTime = SystemClock.elapsedRealtime();
                        Heimdall.log("send broadcast for " + poll.toDumpString());
                        sendBroadcast(poll);
                        addReportHistory(poll);
                        HeimdallReportManager.this.mReportedAlwaysRunningProcesses.add(
                                poll.processName);
                    } finally {
                    }
                }
            } catch (Exception e) {
                Heimdall.log("Handler message catch exception " + e.getMessage());
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x00a6 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0000 A[SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final com.android.server.chimera.heimdall.HeimdallAlwaysRunningProcInfo poll() {
            /*
                r11 = this;
            L0:
                java.util.Queue r0 = r11.mReportData
                boolean r0 = r0.isEmpty()
                if (r0 != 0) goto La7
                java.util.Queue r0 = r11.mReportData
                java.util.LinkedList r0 = (java.util.LinkedList) r0
                java.lang.Object r0 = r0.poll()
                com.android.server.chimera.heimdall.HeimdallAlwaysRunningProcInfo r0 = (com.android.server.chimera.heimdall.HeimdallAlwaysRunningProcInfo) r0
                if (r0 == 0) goto L0
                int r1 = r0.abnormalType
                r2 = 1
                r3 = 0
                r4 = 3022(0xbce, float:4.235E-42)
                if (r4 != r1) goto L8e
                com.android.server.chimera.heimdall.HeimdallAlwaysRunningMonitor r1 = com.android.server.chimera.heimdall.HeimdallAlwaysRunningMonitor.INSTANCE
                int r5 = r0.pid
                java.lang.String r6 = r0.packageName
                java.lang.String r7 = r0.processName
                java.util.Map r8 = r1.mAlwaysRunningProcMap
                monitor-enter(r8)
                java.util.Map r1 = r1.mAlwaysRunningProcMap     // Catch: java.lang.Throwable -> L3a
                java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Throwable -> L3a
                android.util.ArrayMap r1 = (android.util.ArrayMap) r1     // Catch: java.lang.Throwable -> L3a
                java.lang.Object r1 = r1.get(r5)     // Catch: java.lang.Throwable -> L3a
                com.android.server.chimera.heimdall.HeimdallAlwaysRunningProcInfo r1 = (com.android.server.chimera.heimdall.HeimdallAlwaysRunningProcInfo) r1     // Catch: java.lang.Throwable -> L3a
                if (r1 != 0) goto L3c
                monitor-exit(r8)     // Catch: java.lang.Throwable -> L3a
                r1 = r3
                goto L5e
            L3a:
                r11 = move-exception
                goto L8c
            L3c:
                java.lang.String r5 = r1.processName     // Catch: java.lang.Throwable -> L3a
                boolean r5 = r5.equals(r7)     // Catch: java.lang.Throwable -> L3a
                if (r5 == 0) goto L5c
                java.lang.String r5 = r1.packageName     // Catch: java.lang.Throwable -> L3a
                boolean r5 = r5.equals(r6)     // Catch: java.lang.Throwable -> L3a
                if (r5 == 0) goto L5c
                long r5 = android.os.SystemClock.elapsedRealtime()     // Catch: java.lang.Throwable -> L3a
                long r9 = r1.alwaysRunningStartTime     // Catch: java.lang.Throwable -> L3a
                long r5 = r5 - r9
                r9 = 10800000(0xa4cb80, double:5.335909E-317)
                int r1 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
                if (r1 <= 0) goto L5c
                r1 = r2
                goto L5d
            L5c:
                r1 = r3
            L5d:
                monitor-exit(r8)     // Catch: java.lang.Throwable -> L3a
            L5e:
                if (r1 != 0) goto L62
            L60:
                r2 = r3
                goto La4
            L62:
                double r5 = java.lang.Math.random()
                r7 = 4636737291354636288(0x4059000000000000, double:100.0)
                double r5 = r5 * r7
                int r1 = com.android.server.chimera.heimdall.HeimdallReportManager.sRandomSampleRate
                double r7 = (double) r1
                r9 = 4591870180066957722(0x3fb999999999999a, double:0.1)
                double r7 = r7 * r9
                int r1 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                if (r1 <= 0) goto L8e
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                java.lang.String r2 = "Block report by RandomSampleRate: "
                r1.<init>(r2)
                java.lang.String r2 = r0.toDumpString()
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                com.android.server.chimera.heimdall.Heimdall.log(r1)
                goto L60
            L8c:
                monitor-exit(r8)     // Catch: java.lang.Throwable -> L3a
                throw r11
            L8e:
                int r1 = r0.abnormalType
                if (r4 == r1) goto L96
                r4 = 3023(0xbcf, float:4.236E-42)
                if (r4 != r1) goto L97
            L96:
                r3 = r2
            L97:
                if (r3 == 0) goto La4
                com.android.server.chimera.heimdall.HeimdallReportManager r1 = com.android.server.chimera.heimdall.HeimdallReportManager.this
                java.util.concurrent.CopyOnWriteArraySet r1 = r1.mReportedAlwaysRunningProcesses
                java.lang.String r3 = r0.processName
                boolean r1 = r1.contains(r3)
                r2 = r2 ^ r1
            La4:
                if (r2 == 0) goto L0
                return r0
            La7:
                r11 = 0
                return r11
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.chimera.heimdall.HeimdallReportManager.ReportHandler.poll():com.android.server.chimera.heimdall.HeimdallAlwaysRunningProcInfo");
        }

        public final void sendBroadcast(
                HeimdallAlwaysRunningProcInfo heimdallAlwaysRunningProcInfo) {
            Intent intent = new Intent();
            intent.setAction("com.samsung.sdhms.MEMORY_ABNORMAL_HEIMDALL");
            intent.setPackage("com.sec.android.sdhms");
            intent.putExtra("package_name", heimdallAlwaysRunningProcInfo.packageName);
            intent.putExtra("process_name", heimdallAlwaysRunningProcInfo.processName);
            intent.putExtra("uid", heimdallAlwaysRunningProcInfo.uid);
            intent.putExtra("pid", heimdallAlwaysRunningProcInfo.pid);
            intent.putExtra("anomaly_type", heimdallAlwaysRunningProcInfo.abnormalType);
            HeimdallReportManager.this.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
        }
    }

    public HeimdallReportManager(
            Looper looper, Context context, ActivityManagerService activityManagerService) {
        this.mContext = context;
        this.mService = activityManagerService;
        this.mReportHandler = new ReportHandler(looper);
        this.mPackageManager = context.getPackageManager();
        BroadcastManager broadcastManager = new BroadcastManager();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("android.intent.action.REBOOT");
        intentFilter.addAction("com.sec.android.intent.action.HQM_UPDATE_REQ");
        context.registerReceiver(broadcastManager, intentFilter);
        this.mBigdataManager = new BigdataManager(this);
    }

    public final void dumpReportHistory(PrintWriter printWriter) {
        RingBuffer ringBuffer = this.mReportHistory;
        if (!ringBuffer.isEmpty()) {
            printWriter.println("\nReport history:");
            for (String str : (String[]) ringBuffer.toArray()) {
                printWriter.println(str);
            }
        }
        ReportHandler reportHandler = this.mReportHandler;
        int i = ReportHandler.$r8$clinit;
        reportHandler.getClass();
        printWriter.println();
        synchronized (reportHandler.lock) {
            try {
                int size = ((LinkedList) reportHandler.mReportData).size();
                printWriter.println("  In-progress report List (length=" + size + ")");
                if (size != 0) {
                    Iterator it = reportHandler.mReportData.iterator();
                    while (it.hasNext()) {
                        printWriter.println(
                                "    " + ((HeimdallAlwaysRunningProcInfo) it.next()).processName);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(26:7|(2:8|9)|10|(1:12)(14:308|309|310|(1:312)(1:347)|313|314|316|317|318|319|320|321|322|323)|13|(4:14|15|(1:17)(1:304)|(6:18|19|21|22|(14:236|237|238|239|240|241|242|243|(3:244|245|(1:247)(1:248))|249|250|251|252|253)|24))|25|(1:27)(15:191|192|193|(1:195)(1:232)|196|197|198|199|200|201|(2:203|204)|206|207|208|209)|28|(5:29|30|(1:32)(1:187)|(6:33|34|35|36|(2:168|169)|(1:39))|40)|41|(3:42|43|(2:44|45))|(20:113|114|115|116|117|118|(20:121|122|123|124|125|126|(16:129|130|131|132|48|49|50|(1:52)(1:109)|53|54|55|(1:57)(14:67|68|69|70|71|72|73|74|75|76|77|59|60|62)|66|59|60|62)|128|48|49|50|(0)(0)|53|54|55|(0)(0)|66|59|60|62)|120|48|49|50|(0)(0)|53|54|55|(0)(0)|66|59|60|62)|47|48|49|50|(0)(0)|53|54|55|(0)(0)|66|59|60|62) */
    /* JADX WARN: Can't wrap try/catch for region: R(40:7|(2:8|9)|10|(1:12)(14:308|309|310|(1:312)(1:347)|313|314|316|317|318|319|320|321|322|323)|13|14|15|(1:17)(1:304)|18|19|21|22|(14:236|237|238|239|240|241|242|243|(3:244|245|(1:247)(1:248))|249|250|251|252|253)|24|25|(1:27)(15:191|192|193|(1:195)(1:232)|196|197|198|199|200|201|(2:203|204)|206|207|208|209)|28|29|30|(1:32)(1:187)|(6:33|34|35|36|(2:168|169)|(1:39))|40|41|42|43|(2:44|45)|(20:113|114|115|116|117|118|(20:121|122|123|124|125|126|(16:129|130|131|132|48|49|50|(1:52)(1:109)|53|54|55|(1:57)(14:67|68|69|70|71|72|73|74|75|76|77|59|60|62)|66|59|60|62)|128|48|49|50|(0)(0)|53|54|55|(0)(0)|66|59|60|62)|120|48|49|50|(0)(0)|53|54|55|(0)(0)|66|59|60|62)|47|48|49|50|(0)(0)|53|54|55|(0)(0)|66|59|60|62) */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0594, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0613, code lost:

       com.android.server.chimera.heimdall.Heimdall.log(r0.getMessage());
    */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0566, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x062f, code lost:

       com.android.server.chimera.heimdall.Heimdall.log(r0.getMessage());
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x058d, code lost:

       if (r4 != null) goto L250;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0569 A[Catch: IOException | SecurityException -> 0x0566, TRY_LEAVE, TryCatch #46 {IOException | SecurityException -> 0x0566, blocks: (B:50:0x054d, B:52:0x0556, B:109:0x0569), top: B:49:0x054d }] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x042a  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x037f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0165 A[Catch: IOException | SecurityException -> 0x0175, TryCatch #21 {IOException | SecurityException -> 0x0175, blocks: (B:15:0x015c, B:17:0x0165, B:304:0x0178), top: B:14:0x015c }] */
    /* JADX WARN: Removed duplicated region for block: B:187:0x035d A[Catch: IOException | SecurityException -> 0x035b, TRY_LEAVE, TryCatch #31 {IOException | SecurityException -> 0x035b, blocks: (B:30:0x0342, B:32:0x034b, B:187:0x035d), top: B:29:0x0342 }] */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0197 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x0178 A[Catch: IOException | SecurityException -> 0x0175, TRY_LEAVE, TryCatch #21 {IOException | SecurityException -> 0x0175, blocks: (B:15:0x015c, B:17:0x0165, B:304:0x0178), top: B:14:0x015c }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x034b A[Catch: IOException | SecurityException -> 0x035b, TryCatch #31 {IOException | SecurityException -> 0x035b, blocks: (B:30:0x0342, B:32:0x034b, B:187:0x035d), top: B:29:0x0342 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0394 A[Catch: IOException -> 0x0398, TRY_ENTER, TRY_LEAVE, TryCatch #8 {IOException -> 0x0398, blocks: (B:34:0x036c, B:39:0x0394, B:183:0x03a5, B:182:0x03a2, B:177:0x039c, B:36:0x0370, B:169:0x037f, B:172:0x038b), top: B:33:0x036c, inners: #0, #35 }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0556 A[Catch: IOException | SecurityException -> 0x0566, TryCatch #46 {IOException | SecurityException -> 0x0566, blocks: (B:50:0x054d, B:52:0x0556, B:109:0x0569), top: B:49:0x054d }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x057e A[Catch: all -> 0x0597, TRY_ENTER, TRY_LEAVE, TryCatch #47 {all -> 0x0597, blocks: (B:57:0x057e, B:67:0x059b, B:70:0x05ad, B:73:0x05bd, B:75:0x05c0, B:80:0x05c9, B:83:0x05d8, B:85:0x05f3, B:87:0x05f9, B:90:0x0600), top: B:55:0x057c, outer: #10, inners: #22 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x059b A[Catch: all -> 0x0597, TRY_ENTER, TRY_LEAVE, TryCatch #47 {all -> 0x0597, blocks: (B:57:0x057e, B:67:0x059b, B:70:0x05ad, B:73:0x05bd, B:75:0x05c0, B:80:0x05c9, B:83:0x05d8, B:85:0x05f3, B:87:0x05f9, B:90:0x0600), top: B:55:0x057c, outer: #10, inners: #22 }] */
    /* JADX WARN: Type inference failed for: r6v15, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v22 */
    /* JADX WARN: Type inference failed for: r6v23 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reportDumpFile(com.android.server.chimera.heimdall.HeimdallProcessData r31) {
        /*
            Method dump skipped, instructions count: 1604
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.chimera.heimdall.HeimdallReportManager.reportDumpFile(com.android.server.chimera.heimdall.HeimdallProcessData):void");
    }

    public final void sendBroadcast2Bartender(HeimdallProcessData heimdallProcessData) {
        if (heimdallProcessData.isGlobalKill()) {
            return;
        }
        HeimdallAlwaysRunningProcInfo heimdallAlwaysRunningProcInfo =
                new HeimdallAlwaysRunningProcInfo(heimdallProcessData);
        ReportHandler reportHandler = this.mReportHandler;
        reportHandler.getClass();
        Heimdall.log(
                "enter requestSendBroadcast2Bartender "
                        + heimdallAlwaysRunningProcInfo.toDumpString());
        synchronized (reportHandler.lock) {
            try {
                ((LinkedList) reportHandler.mReportData).add(heimdallAlwaysRunningProcInfo);
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (elapsedRealtime - reportHandler.mLastSendTime >= 300000) {
                    Heimdall.log(
                            "requestSendBroadcast2Bartender over 5min "
                                    + heimdallAlwaysRunningProcInfo.toDumpString());
                    reportHandler.sendMessage(reportHandler.obtainMessage(1));
                } else if (((LinkedList) reportHandler.mReportData).size() == 1) {
                    Heimdall.log(
                            "requestSendBroadcast2Bartender first call in 5min "
                                    + heimdallAlwaysRunningProcInfo.toDumpString());
                    reportHandler.sendMessageDelayed(
                            reportHandler.obtainMessage(1),
                            300000 - (elapsedRealtime - reportHandler.mLastSendTime));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
