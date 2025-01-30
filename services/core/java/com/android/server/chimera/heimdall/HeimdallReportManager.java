package com.android.server.chimera.heimdall;

import android.app.ActivityThread;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.os.SemHqmManager;
import android.system.ErrnoException;
import android.system.Os;
import com.android.internal.os.KernelAllocationStats;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.am.ActivityManagerService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class HeimdallReportManager {
    public BigdataManager mBigdataManager;
    public BroadcastManager mBroadcastManager;
    public Context mContext;
    public PackageManager mPackageManager;
    public ActivityManagerService mService;

    public HeimdallReportManager(Context context, ActivityManagerService activityManagerService) {
        this.mContext = context;
        this.mService = activityManagerService;
        this.mPackageManager = context.getPackageManager();
        BroadcastManager broadcastManager = new BroadcastManager();
        this.mBroadcastManager = broadcastManager;
        broadcastManager.registerReceiver();
        this.mBigdataManager = new BigdataManager();
    }

    public void reportDumpFile(HeimdallProcessData heimdallProcessData) {
        if (heimdallProcessData.isSpecKill()) {
            try {
                Os.mkdir("/data/log/heimdalld_log/", 509);
                Os.chown("/data/log/heimdalld_log/", 1000, 1007);
            } catch (ErrnoException e) {
                Heimdall.log(e.getMessage());
            }
            reportDumpSysMemInfo(heimdallProcessData);
            reportProcStatus(heimdallProcessData);
            reportDmaBufDump(heimdallProcessData);
            compressReports(heimdallProcessData);
            reportJavaHeapDump(heimdallProcessData);
            try {
                Os.remove("/data/log/heimdalld_log/");
            } catch (ErrnoException e2) {
                Heimdall.log(e2.getMessage());
            }
        }
    }

    public void reportBigdata(HeimdallProcessData heimdallProcessData) {
        heimdallProcessData.firstAppVersionName = getPackageVersion(heimdallProcessData.firstAppPackageName);
        this.mBigdataManager.addBigdataInfoList(new BigdataInfo(heimdallProcessData));
    }

    public final void reportDumpSysMemInfo(HeimdallProcessData heimdallProcessData) {
        writeMemInfo(heimdallProcessData, "/data/log/heimdalld_log/" + ("heimdall_dumpsys_meminfo_" + heimdallProcessData.firstAppPackageName));
    }

    public final void reportProcStatus(HeimdallProcessData heimdallProcessData) {
        writeShellCmdOutput("cat /proc/" + heimdallProcessData.pid + "/status", "/data/log/heimdalld_log/" + ("heimdall_proc_status_" + heimdallProcessData.firstAppPackageName));
    }

    public final void reportDmaBufDump(HeimdallProcessData heimdallProcessData) {
        writeDmaBufSize("/data/log/heimdalld_log/" + ("heimdall_dma_buf_" + heimdallProcessData.firstAppPackageName));
    }

    public final void compressReports(HeimdallProcessData heimdallProcessData) {
        String str = "tar -cvf /data/log/heimdalld_log_" + heimdallProcessData.firstAppPackageName + ".tar /data/log/heimdalld_log/heimdall_dumpsys_meminfo_" + heimdallProcessData.firstAppPackageName + " /data/log/heimdalld_log/heimdall_proc_status_" + heimdallProcessData.firstAppPackageName + " /data/log/heimdalld_log/heimdall_dma_buf_" + heimdallProcessData.firstAppPackageName;
        Heimdall.log("Execute Command: " + str);
        try {
            Runtime.getRuntime().exec(str).waitFor();
            String str2 = "gzip -1 -f /data/log/heimdalld_log_" + heimdallProcessData.firstAppPackageName + ".tar";
            Heimdall.log("Execute Command: " + str2);
            try {
                Runtime.getRuntime().exec(str2).waitFor();
                String str3 = "rm -rf /data/log/heimdalld_log/heimdall_dumpsys_meminfo_" + heimdallProcessData.firstAppPackageName + " /data/log/heimdalld_log/heimdall_proc_status_" + heimdallProcessData.firstAppPackageName + " /data/log/heimdalld_log/heimdall_dma_buf_" + heimdallProcessData.firstAppPackageName;
                Heimdall.log("Execute Command: " + str3);
                try {
                    Runtime.getRuntime().exec(str3).waitFor();
                    String str4 = "/data/log/heimdalld_log_" + heimdallProcessData.firstAppPackageName + ".tar.gz";
                    try {
                        Os.chmod(str4, FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED);
                        Os.chown(str4, 1000, 1007);
                        Heimdall.log("Compress Finished.");
                    } catch (ErrnoException e) {
                        Heimdall.log(e.getMessage());
                    }
                } catch (IOException | InterruptedException | SecurityException e2) {
                    Heimdall.log(e2.getMessage());
                }
            } catch (IOException | InterruptedException | SecurityException e3) {
                Heimdall.log(e3.getMessage());
            }
        } catch (IOException | InterruptedException | SecurityException e4) {
            Heimdall.log(e4.getMessage());
        }
    }

    public final void reportJavaHeapDump(HeimdallProcessData heimdallProcessData) {
        writeHeapDump(heimdallProcessData, "/data/log/core/" + ("heimdall_heapdump_" + heimdallProcessData.firstAppPackageName + ".prof"), true, false);
    }

    public final void writeMemInfo(HeimdallProcessData heimdallProcessData, String str) {
        PrintWriter printWriter;
        Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
        if (!Debug.getMemoryInfo(heimdallProcessData.pid, memoryInfo)) {
            Heimdall.log("Failed to dumpsys meminfo");
            return;
        }
        File file = new File(str);
        try {
            file.setWritable(true);
            if (file.createNewFile()) {
                Heimdall.log("Create new file. " + str);
            } else {
                Heimdall.log("Failed to create new file. " + str);
            }
            try {
                PrintWriter printWriter2 = new PrintWriter(file, StandardCharsets.UTF_8);
                try {
                    printWriter2.println("\n** MEMINFO in pid " + heimdallProcessData.pid + " [" + heimdallProcessData.processName + "] **");
                    printWriter = printWriter2;
                    try {
                        ActivityThread.dumpMemInfoTable(printWriter2, memoryInfo, false, true, true, false, heimdallProcessData.pid, heimdallProcessData.processName, 0L, 0L, 0L, 0L, 0L, 0L);
                        printWriter.close();
                        Heimdall.log("Dumpsys Meminfo Done(path=" + str + ")");
                    } catch (Throwable th) {
                        th = th;
                        Throwable th2 = th;
                        try {
                            printWriter.close();
                            throw th2;
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                            throw th2;
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    printWriter = printWriter2;
                }
            } catch (IOException | SecurityException e) {
                Heimdall.log(e.getMessage());
            }
        } catch (IOException | SecurityException e2) {
            Heimdall.log(e2.getMessage());
        }
    }

    public final void writeShellCmdOutput(String str, String str2) {
        File file = new File(str2);
        try {
            file.setWritable(true);
            if (file.createNewFile()) {
                Heimdall.log("Create new file. " + str2);
            } else {
                Heimdall.log("Failed to create new file. " + str2);
            }
            try {
                Process exec = Runtime.getRuntime().exec(str);
                exec.waitFor();
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(exec.getInputStream(), StandardCharsets.UTF_8);
                    try {
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        try {
                            FileWriter fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
                            try {
                                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                while (true) {
                                    try {
                                        String readLine = bufferedReader.readLine();
                                        if (readLine != null) {
                                            bufferedWriter.write(readLine + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                                        } else {
                                            bufferedWriter.close();
                                            fileWriter.close();
                                            bufferedReader.close();
                                            inputStreamReader.close();
                                            Heimdall.log("Command Done(path=" + str2 + "): " + str);
                                            return;
                                        }
                                    } finally {
                                    }
                                }
                            } finally {
                            }
                        } finally {
                        }
                    } finally {
                    }
                } catch (IOException e) {
                    Heimdall.log(e.getMessage());
                }
            } catch (IOException | InterruptedException | SecurityException e2) {
                Heimdall.log(e2.getMessage());
            }
        } catch (IOException | SecurityException e3) {
            Heimdall.log(e3.getMessage());
        }
    }

    public final void writeHeapDump(HeimdallProcessData heimdallProcessData, String str, boolean z, boolean z2) {
        ParcelFileDescriptor open;
        File file = new File(str);
        try {
            file.setWritable(true);
            if (file.createNewFile()) {
                Heimdall.log("Create new file. " + str);
            } else {
                Heimdall.log("Failed to create new file. " + str);
            }
            try {
                open = ParcelFileDescriptor.open(file, 536870912);
                try {
                } finally {
                }
            } catch (IOException e) {
                Heimdall.log(e.getMessage());
            }
            if (open == null) {
                Heimdall.log("can't open pfd " + str);
                if (open != null) {
                    open.close();
                    return;
                }
                return;
            }
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            try {
                this.mService.dumpHeap(heimdallProcessData.processName, -2, z, z2, false, str, open, new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.server.chimera.heimdall.HeimdallReportManager.1
                    public void onResult(Bundle bundle) {
                        countDownLatch.countDown();
                    }
                }, (Handler) null));
                try {
                    countDownLatch.await(30L, TimeUnit.SECONDS);
                } catch (InterruptedException e2) {
                    Heimdall.log(e2.getMessage());
                }
                open.close();
                Heimdall.log("JavaHeap dump Done(path=" + str + ")");
            } catch (Exception e3) {
                Heimdall.log("failed getting heapdump " + str + ". Error: " + e3.getMessage());
                try {
                    if (file.delete()) {
                        Heimdall.log("heapdump file is deleted.");
                    } else {
                        Heimdall.log("heapdump file is NOT deleted.");
                    }
                } catch (SecurityException unused) {
                }
                open.close();
            }
        } catch (IOException | SecurityException e4) {
            Heimdall.log(e4.getMessage());
        }
    }

    public final void writeDmaBufSize(String str) {
        FileWriter fileWriter;
        KernelAllocationStats.ProcessDmabuf[] dmabufAllocations = KernelAllocationStats.getDmabufAllocations();
        if (dmabufAllocations == null) {
            Heimdall.log("DmaBuf cannot be read.");
            return;
        }
        File file = new File(str);
        try {
            file.setWritable(true);
            if (file.createNewFile()) {
                Heimdall.log("Create new file. " + str);
            } else {
                Heimdall.log("Failed to create new file. " + str);
            }
            try {
                fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
            } catch (IOException e) {
                Heimdall.log(e.getMessage());
            }
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                try {
                    for (KernelAllocationStats.ProcessDmabuf processDmabuf : dmabufAllocations) {
                        bufferedWriter.write("ProcessName=" + processDmabuf.processName + " retainedSizeKb=" + processDmabuf.retainedSizeKb + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    }
                    bufferedWriter.close();
                    fileWriter.close();
                    Heimdall.log("DmaBuf Done(path=" + str + ")");
                } finally {
                }
            } finally {
            }
        } catch (IOException | SecurityException e2) {
            Heimdall.log(e2.getMessage());
        }
    }

    public final String getPackageVersion(String str) {
        PackageManager packageManager = this.mPackageManager;
        if (packageManager == null) {
            return "unknown";
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            return packageInfo != null ? packageInfo.versionName : "unknown";
        } catch (PackageManager.NameNotFoundException e) {
            Heimdall.log(e.getMessage());
            return "unknown";
        }
    }

    public class BroadcastManager extends BroadcastReceiver {
        public BroadcastManager() {
        }

        public void registerReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
            intentFilter.addAction("android.intent.action.REBOOT");
            intentFilter.addAction("com.sec.android.intent.action.HQM_UPDATE_REQ");
            HeimdallReportManager.this.mContext.registerReceiver(this, intentFilter);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.ACTION_SHUTDOWN".equals(action) || "android.intent.action.REBOOT".equals(action)) {
                HeimdallReportManager.this.mBigdataManager.exportBigdataInfoList();
            } else if ("com.sec.android.intent.action.HQM_UPDATE_REQ".equals(action)) {
                HeimdallReportManager.this.mBigdataManager.sendBigdataInfoList();
            }
        }
    }

    public class BigdataManager {
        public ArrayList mBigdataInfoList = new ArrayList();
        public SemHqmManager mSemHqmManager;

        public BigdataManager() {
            this.mSemHqmManager = (SemHqmManager) HeimdallReportManager.this.mContext.getSystemService("HqmManagerService");
            importBigdataInfoList();
        }

        public final void addBigdataInfoList(BigdataInfo bigdataInfo) {
            Iterator it = this.mBigdataInfoList.iterator();
            while (it.hasNext()) {
                BigdataInfo bigdataInfo2 = (BigdataInfo) it.next();
                if (bigdataInfo2.packageName.equals(bigdataInfo.packageName) && bigdataInfo2.versionName.equals(bigdataInfo.versionName)) {
                    bigdataInfo2.globalKillCnt += bigdataInfo.globalKillCnt;
                    bigdataInfo2.specKillCnt += bigdataInfo.specKillCnt;
                    bigdataInfo2.realKillCnt += bigdataInfo.realKillCnt;
                    int i = bigdataInfo2.memoryUsage;
                    int i2 = bigdataInfo.memoryUsage;
                    if (i < i2) {
                        bigdataInfo2.memoryUsage = i2;
                    }
                    Heimdall.log("add BigdataInfo to list. " + bigdataInfo2.toJsonData());
                    return;
                }
            }
            this.mBigdataInfoList.add(bigdataInfo);
            Heimdall.log("add BigdataInfo to list. " + bigdataInfo.toJsonData());
        }

        public final void importBigdataInfoList() {
            File file = new File("/data/misc/pageboost/heimdall_last_bigdata_string");
            try {
                if (file.exists()) {
                    try {
                        FileInputStream fileInputStream = new FileInputStream("/data/misc/pageboost/heimdall_last_bigdata_string");
                        try {
                            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                            try {
                                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                while (true) {
                                    try {
                                        String readLine = bufferedReader.readLine();
                                        if (readLine == null) {
                                            break;
                                        }
                                        BigdataInfo bigdataInfo = HeimdallReportManager.this.new BigdataInfo(readLine);
                                        if (bigdataInfo.packageName == null) {
                                            break;
                                        } else {
                                            addBigdataInfoList(bigdataInfo);
                                        }
                                    } finally {
                                    }
                                }
                                if (file.delete()) {
                                    Heimdall.log("Big data backup file is deleted during importing.");
                                } else {
                                    Heimdall.log("Big data backup file is NOT deleted during importing.");
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

        public final void exportBigdataInfoList() {
            if (this.mBigdataInfoList.size() == 0) {
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
                    FileOutputStream fileOutputStream = new FileOutputStream("/data/misc/pageboost/heimdall_last_bigdata_string");
                    try {
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                        try {
                            Iterator it = this.mBigdataInfoList.iterator();
                            while (it.hasNext()) {
                                outputStreamWriter.append((CharSequence) (((BigdataInfo) it.next()).toString() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE));
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

        public final void sendBigdataInfoList() {
            Iterator it = this.mBigdataInfoList.iterator();
            while (it.hasNext()) {
                sendBigdataInfo((BigdataInfo) it.next());
            }
            this.mBigdataInfoList.clear();
        }

        public final void sendBigdataInfo(BigdataInfo bigdataInfo) {
            if (this.mSemHqmManager == null) {
                return;
            }
            String jsonData = bigdataInfo.toJsonData();
            if (jsonData.length() >= 1024) {
                return;
            }
            if (this.mSemHqmManager.sendHWParamToHQM(0, "AP", "HDMM", "sm", "0.0", "sec", "", jsonData, "")) {
                Heimdall.log("Successful to send data to HQM. " + bigdataInfo.toJsonData());
                return;
            }
            Heimdall.log("Failed to send data to HQM." + bigdataInfo.toJsonData());
        }
    }

    public class BigdataInfo {
        public int globalKillCnt;
        public int memoryUsage;
        public String packageName;
        public int realKillCnt;
        public int specKillCnt;
        public String versionName;

        public BigdataInfo(HeimdallProcessData heimdallProcessData) {
            this.packageName = heimdallProcessData.firstAppPackageName;
            this.versionName = heimdallProcessData.firstAppVersionName;
            this.memoryUsage = heimdallProcessData.anonAfterGc + heimdallProcessData.graphicsAfterGc;
            this.specKillCnt = heimdallProcessData.isSpecKill() ? 1 : 0;
            this.globalKillCnt = heimdallProcessData.isGlobalKill() ? 1 : 0;
            this.realKillCnt = heimdallProcessData.killTime > 0 ? 1 : 0;
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

        public String toString() {
            return this.packageName + "," + this.versionName + "," + this.memoryUsage + "," + this.specKillCnt + "," + this.globalKillCnt + "," + this.realKillCnt;
        }

        public final String toJsonData() {
            return "\"pkgName\":\"" + this.packageName + "\",\"version\":\"" + this.versionName + "\",\"memUsage\":\"" + (this.memoryUsage * 1024) + "\",\"specKillCnt\":\"" + this.specKillCnt + "\",\"glbKillCnt\":\"" + this.globalKillCnt + "\",\"realKillCnt\":\"" + this.realKillCnt + "\"";
        }
    }
}
