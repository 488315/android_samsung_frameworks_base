package com.android.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Environment;
import android.os.FileUtils;
import android.os.MessageQueue;
import android.os.ParcelFileDescriptor;
import android.os.RecoverySystem;
import android.os.SemHqmManager;
import android.os.SystemProperties;
import android.os.IInstalld;
import android.provider.Downloads;
import android.provider.Settings;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.AtomicFile;
import android.util.EventLog;
import android.util.Pair;
import android.util.Slog;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.am.DropboxRateLimiter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class BootReceiver extends BroadcastReceiver {
    public static final int LASTK_LOG_SIZE;
    public static final String[] LAST_KMSG_FILES;
    public static final int LOG_SIZE;
    public static final String[] MOUNT_DURATION_PROPS_POSTFIX;
    public static final File TOMBSTONE_TMP_DIR;
    public static final File lastHeaderFile;
    public static String logFileKernel;
    public static boolean proc_rr_read_done;
    public static String proc_rr_value;
    public static final DropboxRateLimiter sDropboxRateLimiter;
    public static final AtomicFile sFile;
    public static int sSentReports;
    public static String storeExtraInfo;
    public static boolean store_lastkmsg_read_done;
    public static int store_lastkmsg_val;
    public int reset = -1;
    public String EVENT_ID = Build.VERSION.INCREMENTAL;
    public String RESULT_CODE = null;
    public String LOG_FILE = null;
    public boolean isRescueParty = false;
    public SaveLastkmsg saveLastkmsg = null;
    public SemHqmManager mSemHqmManager = null;
    public UUID uniqueID = null;
    public AudioManager mAudioManager = null;

    static {
        LOG_SIZE = SystemProperties.getInt("ro.debuggable", 0) == 1 ? 98304 : 65536;
        LASTK_LOG_SIZE = SystemProperties.getInt("ro.debuggable", 0) == 1 ? 196608 : 65536;
        TOMBSTONE_TMP_DIR = new File("/data/tombstones");
        sFile = new AtomicFile(new File(Environment.getDataSystemDirectory(), "log-files.xml"), "log-files");
        lastHeaderFile = new File(Environment.getDataSystemDirectory(), "last-header.txt");
        MOUNT_DURATION_PROPS_POSTFIX = new String[]{"early", "default", "late"};
        LAST_KMSG_FILES = new String[]{"/sys/fs/pstore/console-ramoops", "/proc/last_kmsg"};
        sSentReports = 0;
        storeExtraInfo = "";
        logFileKernel = "";
        store_lastkmsg_read_done = false;
        store_lastkmsg_val = -1;
        proc_rr_read_done = false;
        proc_rr_value = null;
        sDropboxRateLimiter = new DropboxRateLimiter();
    }

    public final void waitUntileRRpDone(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            try {
                if (SystemProperties.getInt("sys.boot.errp", 0) == 1) {
                    Slog.i("BootReceiver", "We waited make eRRp Done for " + i2 + "s");
                    return;
                }
                Thread.sleep(1000L);
            } catch (Exception e) {
                Slog.e("BootReceiver", "waitUntileRRpDone error" + e);
            }
        }
        Slog.i("BootReceiver", "Waited enough time(30s) for eRRp done, but timed out");
    }

    public final void logLastAboxMsg(ZipOutputStream zipOutputStream) {
        String[] strArr = {"/sys/kernel/debug/abox/snapshot_0/log", "/sys/kernel/debug/abox/snapshot_1/log", "/proc/abox/snapshot_0/log", "/proc/abox/snapshot_1/log"};
        String[] strArr2 = {"/sys/kernel/debug/abox/snapshot_0/valid", "/sys/kernel/debug/abox/snapshot_1/valid", "/proc/abox/snapshot_0/valid", "/proc/abox/snapshot_1/valid"};
        byte[] bArr = new byte[IInstalld.FLAG_USE_QUOTA];
        FileInputStream fileInputStream = null;
        for (int i = 0; i < 4; i++) {
            File file = new File(strArr[i]);
            File file2 = new File(strArr2[i]);
            String str = "lastaboxmsg_" + (i % 2) + ".bin";
            if (file.isFile()) {
                try {
                    try {
                        if (file2.isFile()) {
                            String readTextFile = FileUtils.readTextFile(file2, 4, null);
                            if (readTextFile.contains("Y")) {
                                Slog.d("BootReceiver", "get " + str);
                                FileInputStream fileInputStream2 = new FileInputStream(file);
                                try {
                                    zipOutputStream.putNextEntry(new ZipEntry(str));
                                    while (true) {
                                        int read = fileInputStream2.read(bArr);
                                        if (read <= 0) {
                                            break;
                                        } else {
                                            zipOutputStream.write(bArr, 0, read);
                                        }
                                    }
                                    fileInputStream2.close();
                                    zipOutputStream.closeEntry();
                                    fileInputStream = fileInputStream2;
                                } catch (IOException e) {
                                    e = e;
                                    fileInputStream = fileInputStream2;
                                    Slog.e("BootReceiver", "logLastAboxMsg IO err :" + e);
                                    e.printStackTrace();
                                    if (fileInputStream == null) {
                                        return;
                                    }
                                    try {
                                        fileInputStream.close();
                                        return;
                                    } catch (IOException unused) {
                                        return;
                                    }
                                } catch (NullPointerException e2) {
                                    e = e2;
                                    fileInputStream = fileInputStream2;
                                    Slog.e("BootReceiver", "logLastAboxMsg Null pointer :" + e);
                                    e.printStackTrace();
                                    if (fileInputStream == null) {
                                        return;
                                    }
                                    fileInputStream.close();
                                    return;
                                } catch (Throwable th) {
                                    th = th;
                                    fileInputStream = fileInputStream2;
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException unused2) {
                                        }
                                    }
                                    throw th;
                                }
                            } else {
                                Slog.d("BootReceiver", "skip dump " + str + " valid:" + readTextFile);
                            }
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException unused3) {
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (IOException e3) {
                    e = e3;
                } catch (NullPointerException e4) {
                    e = e4;
                }
            }
        }
    }

    public class SaveLastkmsg extends Thread {
        public byte[] buffer;
        public int debughistoryDone;
        public FileInputStream fin;
        public FileOutputStream fout;
        public boolean isSaveLastkmsgDone;

        public SaveLastkmsg() {
            this.fin = null;
            this.fout = null;
            this.buffer = new byte[IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES];
            this.isSaveLastkmsgDone = false;
            this.debughistoryDone = 0;
        }

        public int waitUntilSaveLastkmsgDone(int i) {
            for (int i2 = 0; i2 < i; i2++) {
                try {
                    if (this.isSaveLastkmsgDone) {
                        return i2;
                    }
                    Thread.sleep(1000L);
                } catch (Exception e) {
                    Slog.e("BootReceiver", "waitUntilSaveLastkmsgDone error" + e);
                    return -1;
                }
            }
            return -1;
        }

        public final int waitUntildebughistoryDone(int i) {
            for (int i2 = 0; i2 < i; i2++) {
                try {
                    int i3 = SystemProperties.getInt("sys.boot.debug_history", 0);
                    this.debughistoryDone = i3;
                    if (i3 == 1) {
                        return i2;
                    }
                    Thread.sleep(1000L);
                } catch (Exception e) {
                    Slog.e("BootReceiver", "waitUntildebughistoryDone error" + e);
                    return -1;
                }
            }
            return -1;
        }

        public final void trimDumps() {
            File file = new File("/data/log");
            File[] listFiles = file.listFiles();
            if (!file.exists()) {
                if (file.mkdir()) {
                    return;
                }
                Slog.e("BootReceiver", "trimLastKmsg - logFolder mkdir failed");
                return;
            }
            if (listFiles == null) {
                return;
            }
            ArrayList<Dump> arrayList = new ArrayList();
            arrayList.add(new Dump(new ArrayList(), 5, "lastkmsg"));
            arrayList.add(new Dump(new ArrayList(), 1, "latest_lastkmsg"));
            for (Dump dump : arrayList) {
                try {
                    BootReceiver.this._trimADumpFile(listFiles, dump.getFileList(), dump.getListMax(), dump.getDumpInFix());
                } catch (Exception e) {
                    Slog.e("BootReceiver", "trim" + dump.getDumpInFix() + " error" + e);
                }
            }
        }

        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:136:0x0527
            	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
            	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
            	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
            */
        public final void logLastKmsg() {
            /*
                Method dump skipped, instructions count: 1398
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.BootReceiver.SaveLastkmsg.logLastKmsg():void");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            trimDumps();
            logLastKmsg();
            this.isSaveLastkmsgDone = true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:146:0x0235, code lost:
    
        if (r7.exists() == false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x023f, code lost:
    
        if (r7.length() <= 0) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0241, code lost:
    
        r5 = r5 + "InsufficientLogInfo";
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0251, code lost:
    
        r5 = r5 + "EmptyCrashBuffer";
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x0261, code lost:
    
        r5 = r5 + "NoLogFile";
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x02ee, code lost:
    
        if (r13 == null) goto L136;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 15, insn: 0x0351: MOVE (r12 I:??[OBJECT, ARRAY]) = (r15 I:??[OBJECT, ARRAY]), block:B:212:0x0350 */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0369 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:197:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0364 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x035f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x02eb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x02e6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5, types: [java.io.BufferedReader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String GetRescuePartyLog() {
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        String str;
        boolean z;
        BufferedReader bufferedReader;
        ?? r15;
        boolean z2;
        String str2;
        String str3;
        String str4 = "\"TAG\":\"" + this.uniqueID + "\",";
        String str5 = "\"CAUSE\":\"";
        String str6 = "\",\"STACK\":\"";
        File file = new File("/data/log/rescueparty_log");
        BufferedReader bufferedReader2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Throwable th) {
                th = th;
                z = false;
                bufferedReader2 = bufferedReader;
                if (z && file.exists()) {
                    file.length();
                }
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException unused) {
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException unused2) {
                    }
                }
                if (fileInputStream == null) {
                    try {
                        fileInputStream.close();
                        throw th;
                    } catch (IOException unused3) {
                        throw th;
                    }
                }
                throw th;
            }
        } catch (IOException | RuntimeException e) {
            e = e;
            str = str4;
            fileInputStream = null;
            inputStreamReader = null;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
            inputStreamReader = null;
        }
        try {
            inputStreamReader = new InputStreamReader(fileInputStream);
        } catch (IOException | RuntimeException e2) {
            e = e2;
            str = str4;
            inputStreamReader = null;
            r15 = inputStreamReader;
            z2 = true;
            Slog.e("BootReceiver", "GetRescuePartyLog() Error", e);
            if (z2) {
            }
            if (r15 != 0) {
            }
            if (inputStreamReader != null) {
            }
        } catch (Throwable th3) {
            th = th3;
            inputStreamReader = null;
        }
        try {
            r15 = new BufferedReader(inputStreamReader);
            z2 = true;
            while (true) {
                try {
                    String readLine = r15.readLine();
                    if (readLine == null) {
                        str = str4;
                        break;
                    }
                    if (!readLine.startsWith("***") && !readLine.startsWith("Build") && !readLine.startsWith("Revision") && !readLine.startsWith("ABI") && !readLine.startsWith("uid:") && !readLine.startsWith("tagged_addr_ctrl:") && !readLine.startsWith("pac_enabled_keys:") && !readLine.startsWith("    lr") && !readLine.startsWith("    x") && !readLine.startsWith("    sp")) {
                        if (z2) {
                            if (readLine.startsWith("!@*** FATAL")) {
                                str5 = str5 + readLine.substring(readLine.indexOf("PROCESS: ") + 9);
                            } else if (readLine.startsWith("PLATFORM WATCHDOG RESET")) {
                                str5 = str5 + "system_server(watchdog)";
                            } else if (readLine.startsWith("Process: ")) {
                                String readLine2 = r15.readLine();
                                if (readLine2 == null || !readLine2.startsWith("DeadSystemException")) {
                                    str = str4;
                                    try {
                                        String substring = readLine.substring(9, readLine.indexOf(", PID"));
                                        if (substring.startsWith("com.android.systemui")) {
                                            str6 = str6 + readLine2.trim() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
                                            str5 = str5 + substring;
                                            str4 = str;
                                        }
                                    } catch (IOException | RuntimeException e3) {
                                        e = e3;
                                        Slog.e("BootReceiver", "GetRescuePartyLog() Error", e);
                                        if (z2) {
                                            if (!file.exists()) {
                                                str3 = str5 + "NoLogFile";
                                            } else if (file.length() > 0) {
                                                str3 = str5 + "InsufficientLogInfo";
                                            } else {
                                                str3 = str5 + "EmptyCrashBuffer";
                                            }
                                            str5 = str3;
                                        }
                                        if (r15 != 0) {
                                            try {
                                                r15.close();
                                            } catch (IOException unused4) {
                                            }
                                        }
                                        if (inputStreamReader != null) {
                                            try {
                                                inputStreamReader.close();
                                            } catch (IOException unused5) {
                                            }
                                        }
                                    }
                                }
                            } else {
                                str = str4;
                                if (readLine.startsWith("pid: ")) {
                                    String substring2 = readLine.substring(readLine.indexOf(">>> ") + 4, readLine.indexOf(" <<<"));
                                    if ("system_server".equals(substring2) || "/system/bin/netd".equals(substring2) || "/system/bin/vold".equals(substring2) || "/system/bin/surfaceflinger".equals(substring2) || substring2.startsWith("/vendor/bin/hw/android.hardware.graphics") || substring2.startsWith("zygote")) {
                                        str5 = str5 + substring2 + "(" + readLine.substring(readLine.indexOf("name: ") + 6, readLine.indexOf("  >>>")) + ")";
                                        str4 = str;
                                    }
                                }
                            }
                            z2 = false;
                        } else {
                            str = str4;
                            if (readLine.startsWith("!@*** FATAL") || readLine.startsWith("FATAL EXCEPTION") || readLine.startsWith("Fatal signal") || readLine.startsWith("PLATFORM WATCHDOG")) {
                                break;
                            }
                            if (readLine.startsWith("!@")) {
                                readLine = readLine.substring(2);
                            }
                            if (readLine.endsWith("annotated stack trace:") && readLine.indexOf("annotated") >= 2) {
                                readLine = readLine.substring(0, readLine.indexOf("annotated") - 1) + XmlUtils.STRING_ARRAY_SEPARATOR;
                            }
                            str6 = str6 + readLine.trim() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
                        }
                        str4 = str;
                    }
                    str = str4;
                    str4 = str;
                } catch (IOException | RuntimeException e4) {
                    e = e4;
                    str = str4;
                }
            }
            try {
                r15.close();
            } catch (IOException unused6) {
            }
            try {
                inputStreamReader.close();
            } catch (IOException unused7) {
            }
        } catch (IOException | RuntimeException e5) {
            e = e5;
            str = str4;
            r15 = 0;
            z2 = true;
            Slog.e("BootReceiver", "GetRescuePartyLog() Error", e);
            if (z2) {
            }
            if (r15 != 0) {
            }
            if (inputStreamReader != null) {
            }
        } catch (Throwable th4) {
            th = th4;
            z = true;
            if (z) {
                file.length();
            }
            if (bufferedReader2 != null) {
            }
            if (inputStreamReader != null) {
            }
            if (fileInputStream == null) {
            }
        }
        try {
            fileInputStream.close();
        } catch (IOException unused8) {
        }
        String str7 = str + str5;
        if (str6.length() < (60000 - str5.length()) - 1) {
            str2 = str7 + str6 + "\"";
        } else {
            str2 = str7 + str6.substring(0, (60000 - str5.length()) - 1) + "\"";
        }
        return str2.replace("\t", " ");
    }

    public final void setISRBmode() {
        SystemProperties.set("persist.sys.isrb_havesentlog", Boolean.toString(true));
        if (SystemProperties.getBoolean("sys.isrblevel.callreboot", false)) {
            return;
        }
        SystemProperties.set("persist.sys.rescue_level", Integer.toString(6));
        SystemProperties.set("persist.sys.enable_isrb", Boolean.toString(false));
        SystemProperties.set("persist.sys.rescue_mode", "isrb_boot");
    }

    public final boolean isNotRescueParty() {
        int i = SystemProperties.getInt("persist.sys.rescue_level", 0);
        String str = SystemProperties.get("persist.sys.emergency_reset", "");
        if (i == 0) {
            return true;
        }
        if (i == 7 && !"emergency".equals(str)) {
            SystemProperties.set("persist.sys.rescue_level", "0");
            return true;
        }
        SystemProperties.set("sys.reset_reason", "N|R" + Integer.toString(i));
        this.isRescueParty = true;
        return false;
    }

    public final void sendBroadcastToHWParm(String str, String str2, String str3) {
        if (this.mSemHqmManager != null) {
            Slog.d("BootReceiver", "sendBroadcastToHWParm() mSemHqmManager.sendHWParamToHQM");
            this.mSemHqmManager.sendHWParamToHQM(0, str, str2, "ph", "0.0", "", "", str3, "");
        } else {
            Slog.d("BootReceiver", "sendBroadcastToHWParm() mSemHqmManager is null");
        }
    }

    public final int is_store_lastkmsg() {
        String str;
        File file = new File("/proc/store_lastkmsg");
        if (!store_lastkmsg_read_done) {
            if (!file.isFile()) {
                Slog.e("BootReceiver", "PROC_STORE_KMSG : no exist");
            } else {
                store_lastkmsg_val = 0;
                try {
                    str = FileUtils.readTextFile(file, 1024, KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                } catch (IOException e) {
                    Slog.e("BootReceiver", "PROC_STORE_KMSG : read fail " + e);
                    str = null;
                }
                if (str != null && str.length() >= 1 && str.substring(0, 1).equals("1")) {
                    store_lastkmsg_val = 1;
                }
            }
            store_lastkmsg_read_done = true;
        }
        return store_lastkmsg_val;
    }

    public final String proc_reset_reason() {
        File file = new File("/proc/reset_reason");
        if (!proc_rr_read_done) {
            if (!file.isFile()) {
                Slog.e("BootReceiver", "/proc/reset_reason : no exist");
            } else {
                try {
                    proc_rr_value = FileUtils.readTextFile(file, 1024, KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                } catch (IOException e) {
                    Slog.e("BootReceiver", "PROC_RESET_REASON : read fail " + e);
                }
                Slog.d("BootReceiver", "reset_reason = " + proc_rr_value);
                String str = proc_rr_value;
                if (str != null && str.length() >= 2) {
                    proc_rr_value = proc_rr_value.substring(0, 2);
                }
            }
            if (proc_rr_value == null) {
                proc_rr_value = "NA";
            }
            proc_rr_read_done = true;
        }
        return proc_rr_value;
    }

    public final void sendHWParamInfo(String str, String str2) {
        File file = new File(str2);
        if (file.isFile()) {
            String str3 = "";
            if ("ETRA".equals(str)) {
                try {
                    String readTextFile = FileUtils.readTextFile(file, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES, null);
                    String str4 = SystemProperties.get("ro.crypto.state", "none");
                    String str5 = SystemProperties.get("ro.crypto.type", "none");
                    if (readTextFile.length() > 1) {
                        str3 = readTextFile + ",\"CPT\":\"" + str4 + "/" + str5 + "\"";
                    }
                } catch (IOException e) {
                    Slog.e(str, "readTextFile error" + e);
                }
            } else if ("ETRT".equals(str)) {
                String str6 = SystemProperties.get("ro.soc.model", "none");
                if (str6.equals("none")) {
                    str6 = SystemProperties.get("ro.hardware.chipname", "none");
                }
                try {
                    String readTextFile2 = FileUtils.readTextFile(file, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES, null);
                    if (readTextFile2.length() > 1) {
                        str3 = readTextFile2 + ",\"APNM\":\"" + str6 + "\"";
                    }
                } catch (IOException e2) {
                    Slog.e(str, "readTextFile error" + e2);
                }
            } else {
                try {
                    str3 = FileUtils.readTextFile(file, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES, null);
                } catch (IOException e3) {
                    Slog.e(str, "readTextFile error" + e3);
                }
            }
            if (str3.length() > 1) {
                sendBroadcastToHWParm("AP", str, str3);
                storeExtraInfo += str + " - " + str3 + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
            }
        }
    }

    public class Dump {
        public final String dumpInFix;
        public final List fileList;
        public final int listMax;

        public Dump(List list, int i, String str) {
            this.fileList = list;
            this.listMax = i;
            this.dumpInFix = str;
        }

        public List getFileList() {
            return this.fileList;
        }

        public int getListMax() {
            return this.listMax;
        }

        public String getDumpInFix() {
            return this.dumpInFix;
        }
    }

    public final void _trimADumpFile(File[] fileArr, List list, int i, String str) {
        for (File file : fileArr) {
            String name = file.getName();
            if (file.isFile()) {
                if (name.startsWith("dumpstate_" + str)) {
                    list.add(file);
                }
            }
        }
        Collections.sort(list, new Comparator() { // from class: com.android.server.BootReceiver.1
            @Override // java.util.Comparator
            public int compare(File file2, File file3) {
                return Long.valueOf(file2.lastModified()).compareTo(Long.valueOf(file3.lastModified()));
            }
        });
        Slog.i("BootReceiver", "trim" + str + " - Num of existing listOf" + str + " is " + list.size());
        while (list.size() >= i) {
            Slog.i("BootReceiver", "trim" + str + " - Delete file" + ((File) list.get(0)).getName());
            if (!((File) list.get(0)).delete()) {
                Slog.e("BootReceiver", "trim" + str + " - " + ((File) list.get(0)).getName() + " delete failed");
            }
            list.remove(0);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x00cb, code lost:
    
        if (r5 == null) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00cd, code lost:
    
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00f0, code lost:
    
        if (r5 == null) goto L56;
     */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0128 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0123 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void makeDebugHistory() {
        FileOutputStream fileOutputStream;
        File file;
        File file2 = new File("/data/log");
        File[] listFiles = file2.listFiles();
        if (!file2.exists()) {
            if (file2.mkdir()) {
                return;
            }
            Slog.e("BootReceiver", "trimLastKmsg - logFolder mkdir failed");
            return;
        }
        if (listFiles == null) {
            return;
        }
        ArrayList<Dump> arrayList = new ArrayList();
        arrayList.add(new Dump(new ArrayList(), 1, "debug_history"));
        for (Dump dump : arrayList) {
            try {
                _trimADumpFile(listFiles, dump.getFileList(), dump.getListMax(), dump.getDumpInFix());
            } catch (Exception e) {
                Slog.e("BootReceiver", "trim" + dump.getDumpInFix() + " error" + e);
            }
        }
        SystemProperties.set("sys.boot.debug_history", "0");
        byte[] bArr = new byte[IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES];
        FileInputStream fileInputStream = null;
        try {
            File file3 = new File("/proc/debug_history");
            if (file3.isFile()) {
                FileInputStream fileInputStream2 = new FileInputStream(file3);
                try {
                    try {
                        file = new File("/data/log/dumpstate_debug_history.lst");
                        try {
                            fileOutputStream = new FileOutputStream(file);
                            while (true) {
                                try {
                                    int read = fileInputStream2.read(bArr);
                                    if (read <= 0) {
                                        break;
                                    } else {
                                        fileOutputStream.write(bArr, 0, read);
                                    }
                                } catch (IOException e2) {
                                    e = e2;
                                    fileInputStream = fileInputStream2;
                                    try {
                                        Slog.e("BootReceiver", "debug_history - File copy error" + e);
                                        if (fileInputStream != null) {
                                            try {
                                                fileInputStream.close();
                                            } catch (IOException unused) {
                                            }
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        if (fileInputStream != null) {
                                            try {
                                                fileInputStream.close();
                                            } catch (IOException unused2) {
                                            }
                                        }
                                        if (fileOutputStream == null) {
                                            try {
                                                fileOutputStream.close();
                                                throw th;
                                            } catch (IOException unused3) {
                                                throw th;
                                            }
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    fileInputStream = fileInputStream2;
                                    if (fileInputStream != null) {
                                    }
                                    if (fileOutputStream == null) {
                                    }
                                }
                            }
                            fileInputStream = fileInputStream2;
                        } catch (IOException e3) {
                            e = e3;
                            fileOutputStream = null;
                        }
                    } catch (IOException e4) {
                        e = e4;
                        file = null;
                        fileOutputStream = null;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = null;
                }
            } else {
                file = null;
                fileOutputStream = null;
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException unused4) {
                }
            }
        } catch (IOException e5) {
            e = e5;
            file = null;
            fileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
        }
        if (file != null) {
            try {
                String canonicalPath = file.getCanonicalPath();
                if (canonicalPath != null) {
                    FileUtils.setPermissions(canonicalPath, FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, 1000, 1007);
                }
            } catch (IOException e6) {
                Slog.e("BootReceiver", "dumpstate_debug_history - getCanonicalPath error" + e6);
            }
            SystemProperties.set("sys.boot.debug_history", "1");
        }
        return;
        SystemProperties.set("sys.boot.debug_history", "1");
    }

    public final void logResetReason() {
        String str;
        File file = new File("/proc/reset_reason");
        File file2 = new File("/proc/store_lastkmsg");
        if (!file.isFile()) {
            Slog.e("BootReceiver", "Can't find PROC_RESET_REASON");
            return;
        }
        try {
            str = FileUtils.readTextFile(file, 1024, KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        } catch (IOException e) {
            Slog.e("BootReceiver", "readTextFile error" + e);
            str = null;
        }
        if (str == null) {
            Slog.e("BootReceiver", "resetString is null");
            str = "NA";
        } else {
            Slog.i("BootReceiver", "resetString = " + str);
        }
        if (str.length() >= 2) {
            str = str.substring(0, 2);
        }
        if (!file2.isFile()) {
            if ("RP".equals(str) || "BP".equals(str) || "NP".equals(str)) {
                if (isNotRescueParty()) {
                    SystemProperties.set("sys.reset_reason", "N|" + str);
                }
                this.reset = 0;
            } else {
                SystemProperties.set("sys.reset_reason", "K|" + str);
                this.reset = 1;
            }
            SystemProperties.set("ctl.restart", "resetreason");
            return;
        }
        if ("KP".equals(str) || "PP".equals(str) || "DP".equals(str) || "WP".equals(str) || "TP".equals(str) || "SP".equals(str)) {
            SystemProperties.set("sys.reset_reason", "K|" + str);
            this.reset = 1;
        } else {
            if ("MP".equals(str) || isNotRescueParty()) {
                SystemProperties.set("sys.reset_reason", "N|" + str);
            }
            this.reset = 0;
        }
        SystemProperties.set("ctl.restart", "resetreason");
        if (this.reset == 1 && is_store_lastkmsg() == 1) {
            sendHWParamInfo("ETRA", "/sys/class/sec/sec_hw_param/extra_info");
            sendHWParamInfo("ETRB", "/sys/class/sec/sec_hw_param/extrb_info");
            sendHWParamInfo("ETRC", "/sys/class/sec/sec_hw_param/extrc_info");
            sendHWParamInfo("ETRM", "/sys/class/sec/sec_hw_param/extrm_info");
            sendHWParamInfo("ETRF", "/sys/class/sec/sec_hw_param/extrf_info");
            sendHWParamInfo("ETRT", "/sys/class/sec/sec_hw_param/extrt_info");
        }
    }

    public final void sendResetLog(Context context, String str) {
        SemHqmManager semHqmManager = (SemHqmManager) context.getSystemService("HqmManagerService");
        if (semHqmManager != null) {
            boolean z = false;
            if (this.isRescueParty) {
                try {
                    Thread.sleep(BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
                    if (SystemProperties.getInt("persist.sys.rescue_level", 0) == 6) {
                        boolean z2 = SystemProperties.getBoolean("persist.sys.isrb_havesentlog", false);
                        try {
                            setISRBmode();
                        } catch (InterruptedException unused) {
                        }
                        z = z2;
                    } else {
                        SystemProperties.set("persist.sys.rescue_level", "0");
                    }
                } catch (InterruptedException unused2) {
                }
            }
            if (!z) {
                semHqmManager.sendHWParamToHQM(0, "AP", "REST", "ph", "0.0", "", "", str, "");
            }
            Slog.i("BootReceiver", "send broadcast to HQM about reset reason : " + str);
        }
    }

    public final void sendToMembers(Context context, String str, String str2) {
        if (this.LOG_FILE == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.voc.action.SYSTEM_ERROR");
        intent.setPackage("com.samsung.android.voc");
        intent.putExtra("com.samsung.android.voc.extra.SYSTEM_TYPE", str);
        intent.putExtra("com.samsung.android.voc.extra.RESET_REASON", str2);
        intent.putExtra("com.samsung.android.voc.extra.TAG", this.uniqueID.toString());
        intent.putExtra("com.samsung.android.voc.extra.LOG_PATH", this.LOG_FILE);
        Slog.i("BootReceiver", "Send to Samsung Members, system type: " + str + ", reset_reason: " + str2 + ", tag: " + this.uniqueID.toString() + ", log file: " + this.LOG_FILE + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        context.sendBroadcast(intent, "android.permission.DUMP");
    }

    public final void sendToDiagmon(Context context, String str, String str2) {
        if (Settings.System.getInt(context.getContentResolver(), "samsung_errorlog_agree", 0) != 1 || this.LOG_FILE == null) {
            return;
        }
        Slog.i("BootReceiver", "send broadcast intent to diagmon : " + this.LOG_FILE);
        this.EVENT_ID += System.currentTimeMillis();
        this.RESULT_CODE = str2 + KnoxVpnFirewallHelper.DELIMITER + str;
        Intent intent = new Intent("com.sec.android.diagmonagent.intent.REPORT_ERROR_V2");
        Bundle bundle = new Bundle();
        intent.setPackage("com.sec.android.diagmonagent");
        intent.addFlags(32);
        try {
            bundle.putBundle("DiagMon", new Bundle());
            bundle.getBundle("DiagMon").putBundle("CFailLogUpload", new Bundle());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").putString("ServiceID", "ny6xfd3iri");
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").putBundle("Ext", new Bundle());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("ClientV", Build.VERSION.INCREMENTAL);
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("UiMode", "0");
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("ResultCode", this.RESULT_CODE);
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("WifiOnlyFeature", "1");
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("EventID", this.EVENT_ID);
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("Ext").putString("Tag", this.uniqueID.toString());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").putBundle("IntentOnly", new Bundle());
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("IntentOnly").putString("IntentOnlyMode", "1");
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("IntentOnly").putString("Agree", "D");
            bundle.getBundle("DiagMon").getBundle("CFailLogUpload").getBundle("IntentOnly").putString("LogPath", this.LOG_FILE);
            intent.putExtra("uploadMO", bundle);
            intent.setFlags(32);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            Slog.e("BootReceiver", "Exception while sending a bug report.", e);
        }
    }

    public final String getDumpFilename(String str) {
        File file = new File("/data/log");
        String str2 = null;
        if ("P|UR".equals(str)) {
            Path path = Paths.get("/data/log/unknown_platform_reset.log", new String[0]);
            if (Files.exists(path, new LinkOption[0]) && Files.isRegularFile(path, new LinkOption[0])) {
                return "/data/log/unknown_platform_reset.log";
            }
            return null;
        }
        File[] listFiles = file.listFiles(new FilenameFilter() { // from class: com.android.server.BootReceiver$$ExternalSyntheticLambda1
            @Override // java.io.FilenameFilter
            public final boolean accept(File file2, String str3) {
                boolean lambda$getDumpFilename$0;
                lambda$getDumpFilename$0 = BootReceiver.lambda$getDumpFilename$0(file2, str3);
                return lambda$getDumpFilename$0;
            }
        });
        if (listFiles == null) {
            return null;
        }
        String addSuffix = getResetReasonFactory().createResetReasonCode(str).addSuffix();
        if ("".equals(addSuffix)) {
            if (!this.isRescueParty) {
                return null;
            }
            addSuffix = "sys_error";
        }
        Arrays.sort(listFiles);
        for (File file2 : listFiles) {
            if (file2.isFile()) {
                if (file2.getName().startsWith("dumpstate_" + addSuffix)) {
                    str2 = "/data/log/" + file2.getName();
                }
            }
        }
        return str2;
    }

    public static /* synthetic */ boolean lambda$getDumpFilename$0(File file, String str) {
        return str.endsWith("zip") || str.endsWith("gz");
    }

    public final String getANRFileName() {
        File[] listFiles = new File("/data/anr").listFiles(new FilenameFilter() { // from class: com.android.server.BootReceiver$$ExternalSyntheticLambda0
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                boolean lambda$getANRFileName$1;
                lambda$getANRFileName$1 = BootReceiver.lambda$getANRFileName$1(file, str);
                return lambda$getANRFileName$1;
            }
        });
        if (listFiles == null || listFiles.length == 0) {
            return null;
        }
        Arrays.sort(listFiles);
        return "/data/anr/" + listFiles[listFiles.length - 1].getName();
    }

    public static /* synthetic */ boolean lambda$getANRFileName$1(File file, String str) {
        return str.startsWith("anr_");
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x01eb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0131 A[LOOP:1: B:49:0x012f->B:50:0x0131, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0145 A[LOOP:2: B:53:0x013f->B:55:0x0145, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01ee  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02ca A[Catch: IOException -> 0x02c6, TRY_LEAVE, TryCatch #0 {IOException -> 0x02c6, blocks: (B:97:0x02c2, B:88:0x02ca), top: B:96:0x02c2 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x02c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String GetPWatchdog() {
        BufferedReader bufferedReader;
        FileReader fileReader;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        boolean z;
        boolean z2;
        int i6;
        int i7;
        String aNRFileName;
        Pattern pattern;
        Pattern pattern2;
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        Pattern compile = Pattern.compile("\"(.*)\".*prio=\\d+ tid=(\\d+).*");
        Pattern compile2 = Pattern.compile("  at.*");
        Pattern compile3 = Pattern.compile(".*held by thread (\\d+)");
        int i8 = 1;
        try {
            aNRFileName = getANRFileName();
        } catch (IOException e) {
            e = e;
            bufferedReader = null;
            fileReader = null;
        }
        if (aNRFileName == null) {
            return null;
        }
        fileReader = new FileReader(aNRFileName);
        try {
            bufferedReader = new BufferedReader(fileReader);
            String str = "";
            int i9 = 0;
            i = 0;
            int i10 = 0;
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    if (readLine.equals("Cmd line: system_server")) {
                        hashMap.clear();
                        hashMap3.clear();
                        hashMap2.clear();
                        i9 = i8;
                    } else if (i9 != 0 && readLine.startsWith("----- end")) {
                        break;
                    }
                    if (i9 != 0) {
                        Matcher matcher = compile.matcher(readLine);
                        if (matcher.matches()) {
                            int parseInt = Integer.parseInt(matcher.group(2));
                            String group = matcher.group(i8);
                            if (parseInt > i) {
                                i = parseInt;
                            }
                            hashMap.put(Integer.valueOf(parseInt), group);
                            hashMap2.put(group, "");
                            i10 = parseInt;
                            str = group;
                        }
                        Matcher matcher2 = compile2.matcher(readLine);
                        if (matcher2.matches()) {
                            String str2 = (String) hashMap2.get(str);
                            pattern = compile;
                            if (str2 == null) {
                                pattern2 = compile2;
                                hashMap2.put(str, matcher2.group(0).trim() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                            } else {
                                pattern2 = compile2;
                                hashMap2.put(str, (str2 + matcher2.group(0).trim()) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                            }
                        } else {
                            pattern = compile;
                            pattern2 = compile2;
                        }
                        Matcher matcher3 = compile3.matcher(readLine);
                        if (matcher3.matches()) {
                            hashMap3.put(Integer.valueOf(i10), Integer.valueOf(Integer.parseInt(matcher3.group(1))));
                        }
                        compile = pattern;
                        compile2 = pattern2;
                        i8 = 1;
                    }
                } catch (IOException e2) {
                    e = e2;
                    Slog.e("BootReceiver", "getPWatchdog() Error", e);
                    i2 = i + 1;
                    int[] iArr = new int[i2];
                    while (i3 < i2) {
                    }
                    while (r5.hasNext()) {
                    }
                    i4 = 0;
                    i5 = 0;
                    while (true) {
                        if (hashMap3.isEmpty()) {
                        }
                        hashMap3.remove(Integer.valueOf(i7));
                        i4 = i7;
                        i5 = i6;
                    }
                    if (i5 != 0) {
                    }
                    if (bufferedReader != null) {
                    }
                    if (fileReader == null) {
                    }
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e3) {
            e = e3;
            bufferedReader = null;
            i = 0;
            Slog.e("BootReceiver", "getPWatchdog() Error", e);
            i2 = i + 1;
            int[] iArr2 = new int[i2];
            while (i3 < i2) {
            }
            while (r5.hasNext()) {
            }
            i4 = 0;
            i5 = 0;
            while (true) {
                if (hashMap3.isEmpty()) {
                }
                hashMap3.remove(Integer.valueOf(i7));
                i4 = i7;
                i5 = i6;
            }
            if (i5 != 0) {
            }
            if (bufferedReader != null) {
            }
            if (fileReader == null) {
            }
        }
        i2 = i + 1;
        int[] iArr22 = new int[i2];
        for (i3 = 0; i3 < i2; i3++) {
            iArr22[i3] = 0;
        }
        for (Map.Entry entry : hashMap3.entrySet()) {
            iArr22[((Integer) entry.getValue()).intValue()] = iArr22[((Integer) entry.getValue()).intValue()] + 1;
        }
        i4 = 0;
        i5 = 0;
        while (true) {
            if (hashMap3.isEmpty()) {
                z = false;
                break;
            }
            Iterator it = hashMap3.entrySet().iterator();
            int i11 = 0;
            int i12 = 0;
            while (true) {
                if (!it.hasNext()) {
                    z2 = true;
                    i6 = i5;
                    i7 = i4;
                    break;
                }
                Map.Entry entry2 = (Map.Entry) it.next();
                if (iArr22[((Integer) entry2.getKey()).intValue()] == 0) {
                    z2 = true;
                    iArr22[((Integer) entry2.getValue()).intValue()] = iArr22[((Integer) entry2.getValue()).intValue()] - 1;
                    i7 = ((Integer) entry2.getKey()).intValue();
                    i6 = ((Integer) entry2.getValue()).intValue();
                    break;
                }
                i11 = ((Integer) entry2.getKey()).intValue();
                i12 = ((Integer) entry2.getValue()).intValue();
            }
            if (i7 == i4) {
                i4 = i11;
                i5 = i12;
                z = z2;
                break;
            }
            hashMap3.remove(Integer.valueOf(i7));
            i4 = i7;
            i5 = i6;
        }
        if (i5 != 0 || hashMap.get(Integer.valueOf(i5)) == null || hashMap.get(Integer.valueOf(i4)) == null) {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    Slog.e("BootReceiver", "Failed to close in getPWatchdog()", e4);
                    return null;
                }
            }
            if (fileReader == null) {
                return null;
            }
            fileReader.close();
            return null;
        }
        String str3 = "\"CAUSE\":\"" + ((String) hashMap.get(Integer.valueOf(i5))) + "," + ((String) hashMap.get(Integer.valueOf(i4)));
        String str4 = (!z ? str3 + ",WAITLOCK\"," : str3 + ",DEADLOCK\",") + "\"STACK\":\"" + ((String) hashMap.get(Integer.valueOf(i5))) + ":\n" + ((String) hashMap2.get(hashMap.get(Integer.valueOf(i5)))) + ((String) hashMap.get(Integer.valueOf(i4))) + ":\n" + ((String) hashMap2.get(hashMap.get(Integer.valueOf(i4))));
        if (str4.length() >= 59999) {
            str4 = str4.substring(0, 59999);
        }
        return str4.concat("\"");
    }

    public final String GetResetLog(String str) {
        Pattern pattern;
        String GetPWatchdog;
        if (str.matches(".*R[1-9].*")) {
            return GetRescuePartyLog();
        }
        String str2 = "\"TAG\":\"" + this.uniqueID + "\",";
        if (str.equals("P|WD") && (GetPWatchdog = GetPWatchdog()) != null) {
            return str2 + GetPWatchdog;
        }
        ResetReasonCode createResetReasonCode = getResetReasonFactory().createResetReasonCode(str);
        Pattern patternByReason = createResetReasonCode.getPatternByReason();
        String str3 = "\"CAUSE\":\"" + createResetReasonCode.addCauseContents();
        String str4 = "\"STACK\":\"" + createResetReasonCode.addStackContents();
        String str5 = "";
        if (Pattern.compile(".*").equals(patternByReason)) {
            if (!str.equals("N|RP") && !str.equals("N|NP")) {
                return "";
            }
            return str2 + str3 + "\"," + str4 + "\"";
        }
        Pattern compile = Pattern.compile("[EFW]\\/(.*)\\((\\s*\\d+)\\):\\s+(.*)");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("logcat -v brief -b crash -d -t 1000").getInputStream()));
        ArrayList<Pair> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        StringBuilder sb = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                break;
            }
            Matcher matcher = compile.matcher(readLine);
            if (matcher.matches()) {
                String group = matcher.group(1);
                if ("AndroidRuntime".equals(group) || "Watchdog".equals(group) || group.startsWith("DEBUG")) {
                    pattern = compile;
                    arrayList.add(Pair.create(matcher.group(2), matcher.group(3)));
                    sb.append(matcher.group(1) + "(" + matcher.group(2) + ") : " + matcher.group(3));
                } else {
                    pattern = compile;
                }
                compile = pattern;
            }
        }
        for (Pair pair : arrayList) {
            if (patternByReason.matcher((CharSequence) pair.second).matches()) {
                str5 = (String) pair.first;
            }
        }
        for (Pair pair2 : arrayList) {
            if (str5.equals(pair2.first)) {
                arrayList2.add((String) pair2.second);
            }
        }
        if (arrayList2.size() <= 1) {
            String str6 = str2 + "\"CAUSE\":\"NO_LOG\",\"STACK\":\"";
            int length = sb.length();
            return str6.concat(sb.substring(length - (length <= 59000 ? length : 59000))).concat("\"");
        }
        List addCauseStackFromContexts = createResetReasonCode.addCauseStackFromContexts(arrayList2);
        String replace = (str2 + (str3 + ((String) addCauseStackFromContexts.get(0))) + "\"," + (str4 + ((String) addCauseStackFromContexts.get(1)))).replace("\t", " ");
        if (replace.length() >= 59999) {
            replace = replace.substring(0, 59999);
        }
        return replace.concat("\"");
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, Intent intent) {
        new Thread() { // from class: com.android.server.BootReceiver.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    BootReceiver.this.logBootEvents(context);
                } catch (Exception e) {
                    Slog.e("BootReceiver", "Can't log boot events", e);
                }
                try {
                    BootReceiver.this.removeOldUpdatePackages(context);
                } catch (Exception e2) {
                    Slog.e("BootReceiver", "Can't remove old update packages", e2);
                }
            }
        }.start();
        try {
            FileDescriptor open = Os.open("/sys/kernel/tracing/instances/bootreceiver/trace_pipe", OsConstants.O_RDONLY, FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT);
            IoThread.get().getLooper().getQueue().addOnFileDescriptorEventListener(open, 1, new MessageQueue.OnFileDescriptorEventListener() { // from class: com.android.server.BootReceiver.3
                public final int mBufferSize = 1024;
                public byte[] mTraceBuffer = new byte[1024];

                @Override // android.os.MessageQueue.OnFileDescriptorEventListener
                public int onFileDescriptorEvents(FileDescriptor fileDescriptor, int i) {
                    try {
                        if (Os.read(fileDescriptor, this.mTraceBuffer, 0, 1024) > 0 && new String(this.mTraceBuffer).indexOf(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE) != -1 && BootReceiver.sSentReports < 8) {
                            SystemProperties.set("dmesgd.start", "1");
                            BootReceiver.sSentReports++;
                        }
                        return 1;
                    } catch (Exception e) {
                        Slog.wtf("BootReceiver", "Error watching for trace events", e);
                        return 0;
                    }
                }
            });
        } catch (ErrnoException e) {
            Slog.wtf("BootReceiver", "Could not open /sys/kernel/tracing/instances/bootreceiver/trace_pipe", e);
        }
    }

    public final void removeOldUpdatePackages(Context context) {
        Downloads.removeAllDownloadsByPackage(context, "com.google.android.systemupdater", "com.google.android.systemupdater.SystemUpdateReceiver");
    }

    public static String getPreviousBootHeaders() {
        try {
            return FileUtils.readTextFile(lastHeaderFile, 0, null);
        } catch (IOException unused) {
            return null;
        }
    }

    public static String getCurrentBootHeaders() {
        StringBuilder sb = new StringBuilder(512);
        sb.append("Build: ");
        sb.append(Build.FINGERPRINT);
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("Hardware: ");
        sb.append(Build.BOARD);
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("Revision: ");
        sb.append(SystemProperties.get("ro.revision", ""));
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("Bootloader: ");
        sb.append(Build.BOOTLOADER);
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("Radio: ");
        sb.append(Build.getRadioVersion());
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("Kernel: ");
        sb.append(FileUtils.readTextFile(new File("/proc/version"), 1024, "...\n"));
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        return sb.toString();
    }

    public static String getBootHeadersToLogAndUpdate() {
        String previousBootHeaders = getPreviousBootHeaders();
        String currentBootHeaders = getCurrentBootHeaders();
        try {
            FileUtils.stringToFile(lastHeaderFile, currentBootHeaders);
        } catch (IOException e) {
            Slog.e("BootReceiver", "Error writing " + lastHeaderFile, e);
        }
        if (previousBootHeaders == null) {
            return "isPrevious: false\n" + currentBootHeaders;
        }
        return "isPrevious: true\n" + previousBootHeaders;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:21|(3:72|(1:74)|75)|(2:25|26)|27|(6:41|(2:43|(1:63)(2:47|(1:49)(2:60|(1:62))))(2:64|(3:66|(1:68)|69))|50|(1:52)(1:59)|53|(7:55|(1:57)(1:58)|32|33|(1:35)|36|37))|31|32|33|(0)|36|37) */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x03b2, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x03b3, code lost:
    
        android.util.Slog.e(r12, "get reset log error", r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0396 A[Catch: IOException | IllegalStateException -> 0x03b2, TryCatch #5 {IOException | IllegalStateException -> 0x03b2, blocks: (B:33:0x038c, B:35:0x0396, B:36:0x03aa), top: B:32:0x038c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void logBootEvents(Context context) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        BootReceiver bootReceiver;
        Context context2;
        String GetResetLog;
        String str7;
        String str8;
        String str9;
        DropBoxManager dropBoxManager = (DropBoxManager) context.getSystemService("dropbox");
        String bootHeadersToLogAndUpdate = getBootHeadersToLogAndUpdate();
        String str10 = SystemProperties.get("ro.boot.bootreason", (String) null);
        Time time = new Time();
        this.uniqueID = UUID.randomUUID();
        String handleAftermath = RecoverySystem.handleAftermath(context);
        if (handleAftermath != null && dropBoxManager != null) {
            dropBoxManager.addText("SYSTEM_RECOVERY_LOG", bootHeadersToLogAndUpdate + handleAftermath);
        }
        String str11 = "";
        if (str10 != null) {
            StringBuilder sb = new StringBuilder(512);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            sb.append("Boot info:\n");
            sb.append("Last boot reason: ");
            sb.append(str10);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            str = sb.toString();
        } else {
            str = "";
        }
        HashMap readTimestamps = readTimestamps();
        SystemProperties.set("sys.is_members", "exist");
        Slog.i("BootReceiver", "prepare setting eRR.p ");
        SystemProperties.set("sys.boot.errp", "0");
        String proc_reset_reason = proc_reset_reason();
        if (SystemProperties.getLong("ro.runtime.firstboot", 0L) == 0) {
            SystemProperties.set("ro.runtime.firstboot", Long.toString(System.currentTimeMillis()));
            if (dropBoxManager != null) {
                dropBoxManager.addText("SYSTEM_BOOT", bootHeadersToLogAndUpdate);
            }
            this.mSemHqmManager = (SemHqmManager) context.getSystemService("HqmManagerService");
            this.mAudioManager = (AudioManager) context.getSystemService("audio");
            logResetReason();
            makeDebugHistory();
            File file = new File("/proc/reset_rwc");
            time.setToNow();
            String format = time.format("%Y%m%d_%H%M%S");
            String str12 = "SYSTEM_LAST_KMSG_" + format + "_" + proc_reset_reason;
            if (file.isFile()) {
                str7 = str12;
                str8 = "ctl.restart";
                try {
                    str9 = "SYSTEM_LAST_KMSG_" + FileUtils.readTextFile(file, 1024, null) + "_" + format + "_" + proc_reset_reason;
                } catch (IOException e) {
                    Slog.e("BootReceiver", "readTextFile error" + e);
                }
                int i = LASTK_LOG_SIZE;
                int i2 = -i;
                String str13 = str;
                str2 = proc_reset_reason;
                str3 = "BootReceiver";
                String str14 = str9;
                addLastkToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, str13, "/proc/last_kmsg", i2, str14);
                addLastkToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, str13, "/sys/fs/pstore/console-ramoops", -i, str14);
                addLastkToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, str13, "/sys/fs/pstore/console-ramoops-0", -i, str14);
                int i3 = LOG_SIZE;
                addFileToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, "/cache/recovery/log", -i3, "SYSTEM_RECOVERY_LOG");
                addFileToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, "/cache/recovery/last_kmsg", -i3, "SYSTEM_RECOVERY_KMSG");
                addAuditErrorsToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, -i3, "SYSTEM_AUDIT");
                str6 = str8;
                str4 = "sys.reset_reason";
                str5 = "resetreason";
                bootReceiver = this;
            } else {
                str7 = str12;
                str8 = "ctl.restart";
            }
            str9 = str7;
            int i4 = LASTK_LOG_SIZE;
            int i22 = -i4;
            String str132 = str;
            str2 = proc_reset_reason;
            str3 = "BootReceiver";
            String str142 = str9;
            addLastkToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, str132, "/proc/last_kmsg", i22, str142);
            addLastkToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, str132, "/sys/fs/pstore/console-ramoops", -i4, str142);
            addLastkToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, str132, "/sys/fs/pstore/console-ramoops-0", -i4, str142);
            int i32 = LOG_SIZE;
            addFileToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, "/cache/recovery/log", -i32, "SYSTEM_RECOVERY_LOG");
            addFileToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, "/cache/recovery/last_kmsg", -i32, "SYSTEM_RECOVERY_KMSG");
            addAuditErrorsToDropBox(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, -i32, "SYSTEM_AUDIT");
            str6 = str8;
            str4 = "sys.reset_reason";
            str5 = "resetreason";
            bootReceiver = this;
        } else {
            str2 = proc_reset_reason;
            str3 = "BootReceiver";
            if (dropBoxManager != null) {
                dropBoxManager.addText("SYSTEM_RESTART", bootHeadersToLogAndUpdate);
            }
            str4 = "sys.reset_reason";
            if (!isNotRescueParty()) {
                str5 = "resetreason";
            } else if ("".equals(SystemProperties.get(str4, ""))) {
                Path path = Paths.get("/data/log/prev_dump.log", new String[0]);
                Path path2 = Paths.get("/data/log/unknown_platform_reset.log", new String[0]);
                if (Files.exists(path, new LinkOption[0])) {
                    try {
                        FileChannel open = FileChannel.open(path, StandardOpenOption.READ);
                        try {
                            open = FileChannel.open(path2, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                            try {
                                ByteBuffer allocate = ByteBuffer.allocate((int) Files.size(path));
                                open.read(allocate);
                                String str15 = new String(allocate.array());
                                allocate.clear();
                                open.write(Charset.defaultCharset().encode(str15.replaceAll("Chip ID : .*?\n|androidboot[.]ap_serial=.*? |androidboot[.]serialno=.*? ", "")));
                                Slog.i(str3, "PF_UR case. Leave prev_dump.log as unknown_platform_reset.log");
                                FileUtils.copyPermissions(new File("/data/log/prev_dump.log"), new File("/data/log/unknown_platform_reset.log"));
                                Matcher matcher = Pattern.compile("Out of memory: Kill(ed)? process [0-9]+ [(]system_server[)]|Sending (SIGKILL|SIGTERM) to process system_server").matcher(str15);
                                if (matcher.find()) {
                                    String group = matcher.group();
                                    SystemProperties.set("sys.reset_info", group.substring(0, Math.min(group.length(), 91)));
                                } else {
                                    SystemProperties.set("sys.reset_info", "unknown");
                                }
                                open.close();
                                open.close();
                            } finally {
                                if (open == null) {
                                    throw th;
                                }
                                try {
                                    open.close();
                                    throw th;
                                } catch (Throwable th) {
                                    th.addSuppressed(th);
                                }
                            }
                        } catch (Throwable th2) {
                            if (open != null) {
                                try {
                                    open.close();
                                    throw th2;
                                } catch (Throwable th3) {
                                    th2.addSuppressed(th3);
                                    throw th2;
                                }
                            }
                        }
                    } catch (IOException e2) {
                        Slog.e(str3, "Failed to copy prev_dump.log file.", e2);
                    }
                }
                SystemProperties.set(str4, "P|UR");
                str5 = "resetreason";
                SystemProperties.set("ctl.restart", str5);
            } else {
                str5 = "resetreason";
                SystemProperties.set("sys.boot.errp", "1");
            }
            str6 = "ctl.restart";
            bootReceiver = this;
            bootReceiver.reset = 2;
        }
        StringBuilder sb2 = new StringBuilder();
        String str16 = SystemProperties.get(str4, (String) null);
        String str17 = SystemProperties.get("ro.boot.hardware", (String) null);
        if (str16 != null) {
            if (bootReceiver.reset == 1 || "MP".equals(str2)) {
                Slog.i(str3, "We start saveLastkmsg.waitUntilSaveLastkmsgDone( timeout = 30 )");
                if (bootReceiver.saveLastkmsg == null) {
                    bootReceiver.saveLastkmsg = new SaveLastkmsg();
                }
                Slog.i(str3, "saveLastkmsg null and start()");
                bootReceiver.saveLastkmsg.start();
                Slog.i(str3, "We waited saveLastkmsg() for " + bootReceiver.saveLastkmsg.waitUntilSaveLastkmsgDone(30) + "s ");
            }
            try {
                bootReceiver.LOG_FILE = bootReceiver.getDumpFilename(str16);
            } catch (IOException unused) {
                bootReceiver.LOG_FILE = null;
            }
            sb2.append("\"REASON\":\"" + str16 + "\"");
            int i5 = bootReceiver.reset;
            if (i5 != 0 || bootReceiver.isRescueParty) {
                if (i5 == 1) {
                    if (str17.contains("exynos") || str17.contains("s5e")) {
                        str11 = "KERNEL_L";
                    } else if (str17.contains("qcom")) {
                        str11 = "KERNEL_Q";
                    } else if (str17.contains("mt")) {
                        str11 = "KERNEL_M";
                    }
                } else if (i5 == 2) {
                    if (bootReceiver.isRescueParty) {
                        Slog.d(str3, "leave rescueparty errp");
                        SystemProperties.set(str6, str5);
                    }
                    str11 = "PLATFORM";
                }
                boolean z = SystemProperties.getInt("persist.sys.rescue_level", 0) == 6 ? SystemProperties.getBoolean("persist.sys.isrb_havesentlog", false) : false;
                String str18 = SystemProperties.get("sys.members.installed", "UNKNOWN");
                if (!z) {
                    if ("true".equalsIgnoreCase(str18)) {
                        context2 = context;
                        bootReceiver.sendToMembers(context2, str11, str16);
                    } else {
                        context2 = context;
                        bootReceiver.sendToDiagmon(context2, str11, str16);
                    }
                    GetResetLog = bootReceiver.GetResetLog(str16);
                    if (!GetResetLog.isEmpty()) {
                        sb2.append("," + GetResetLog);
                    }
                    bootReceiver.sendResetLog(context2, sb2.toString());
                    bootReceiver.waitUntileRRpDone(30);
                    SystemProperties.set(str4, (String) null);
                }
            }
            context2 = context;
            GetResetLog = bootReceiver.GetResetLog(str16);
            if (!GetResetLog.isEmpty()) {
            }
            bootReceiver.sendResetLog(context2, sb2.toString());
            bootReceiver.waitUntileRRpDone(30);
            SystemProperties.set(str4, (String) null);
        }
        logFsShutdownTime();
        logFsMountTime();
        addFsckErrorsToDropBoxAndLogFsStat(dropBoxManager, readTimestamps, bootHeadersToLogAndUpdate, -LOG_SIZE, "SYSTEM_FSCK");
        logSystemServerShutdownTimeMetrics();
        writeTimestamps(readTimestamps);
    }

    public static void resetDropboxRateLimiter() {
        sDropboxRateLimiter.reset();
    }

    public static void addTombstoneToDropBox(Context context, File file, boolean z, String str, ReentrantLock reentrantLock) {
        DropBoxManager dropBoxManager = (DropBoxManager) context.getSystemService(DropBoxManager.class);
        if (dropBoxManager == null) {
            Slog.e("BootReceiver", "Can't log tombstone: DropBoxManager not available");
            return;
        }
        DropboxRateLimiter.RateLimitResult shouldRateLimit = sDropboxRateLimiter.shouldRateLimit(z ? "SYSTEM_TOMBSTONE_PROTO_WITH_HEADERS" : "SYSTEM_TOMBSTONE", str);
        if (shouldRateLimit.shouldRateLimit()) {
            return;
        }
        HashMap readTimestamps = readTimestamps();
        try {
            if (z) {
                if (recordFileTimestamp(file, readTimestamps)) {
                    reentrantLock.lock();
                    try {
                        addAugmentedProtoToDropbox(file, dropBoxManager, shouldRateLimit);
                        reentrantLock.unlock();
                    } catch (Throwable th) {
                        reentrantLock.unlock();
                        throw th;
                    }
                }
            } else {
                addFileToDropBox(dropBoxManager, readTimestamps, getBootHeadersToLogAndUpdate() + shouldRateLimit.createHeader(), file.getPath(), LOG_SIZE, "SYSTEM_TOMBSTONE");
            }
        } catch (IOException e) {
            Slog.e("BootReceiver", "Can't log tombstone", e);
        }
        writeTimestamps(readTimestamps);
    }

    public static void addAugmentedProtoToDropbox(File file, DropBoxManager dropBoxManager, DropboxRateLimiter.RateLimitResult rateLimitResult) {
        ParcelFileDescriptor open;
        if (file.length() > 10485760) {
            Slog.w("BootReceiver", "Tombstone too large to add to DropBox: " + file.toPath());
            return;
        }
        byte[] readAllBytes = Files.readAllBytes(file.toPath());
        File createTempFile = File.createTempFile(file.getName(), ".tmp", TOMBSTONE_TMP_DIR);
        Files.setPosixFilePermissions(createTempFile.toPath(), PosixFilePermissions.fromString("rw-rw----"));
        try {
            try {
                try {
                    open = ParcelFileDescriptor.open(createTempFile, 805306368);
                } catch (FileNotFoundException e) {
                    Slog.e("BootReceiver", "failed to open for write: " + createTempFile, e);
                    throw e;
                }
            } catch (IOException | RuntimeException e2) {
                Slog.e("BootReceiver", "Exception during write: " + createTempFile, e2);
            }
            try {
                ProtoOutputStream protoOutputStream = new ProtoOutputStream(open.getFileDescriptor());
                protoOutputStream.write(1151051235329L, readAllBytes);
                protoOutputStream.write(1120986464258L, rateLimitResult.droppedCountSinceRateLimitActivated());
                protoOutputStream.flush();
                dropBoxManager.addFile("SYSTEM_TOMBSTONE_PROTO_WITH_HEADERS", createTempFile, 0);
                open.close();
            } catch (Throwable th) {
                if (open != null) {
                    try {
                        open.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } finally {
            try {
                createTempFile.delete();
            } catch (Exception unused) {
            }
        }
    }

    public static void addLastkToDropBox(DropBoxManager dropBoxManager, HashMap hashMap, String str, String str2, String str3, int i, String str4) {
        int length = str.length() + 14 + str2.length();
        if (LASTK_LOG_SIZE + length > 196608) {
            i = 196608 > length ? -(196608 - length) : 0;
        }
        addFileWithFootersToDropBox(dropBoxManager, hashMap, str, str2, str3, i, str4);
    }

    public static void addFileToDropBox(DropBoxManager dropBoxManager, HashMap hashMap, String str, String str2, int i, String str3) {
        addFileWithFootersToDropBox(dropBoxManager, hashMap, str, "", str2, i, str3);
    }

    public static void addFileWithFootersToDropBox(DropBoxManager dropBoxManager, HashMap hashMap, String str, String str2, String str3, int i, String str4) {
        if (dropBoxManager == null || !dropBoxManager.isTagEnabled(str4)) {
            return;
        }
        File file = new File(str3);
        if (recordFileTimestamp(file, hashMap)) {
            String readTextFile = FileUtils.readTextFile(file, i, "[[TRUNCATED]]\n");
            String str5 = str + readTextFile + str2;
            if (str4.equals("SYSTEM_TOMBSTONE") && readTextFile.contains(">>> system_server <<<")) {
                addTextToDropBox(dropBoxManager, "system_server_native_crash", str5, str3, i);
            }
            if (str4.equals("SYSTEM_TOMBSTONE")) {
                FrameworkStatsLog.write(186);
            }
            addTextToDropBox(dropBoxManager, str4, str5, str3, i);
        }
    }

    public static boolean recordFileTimestamp(File file, HashMap hashMap) {
        long lastModified = file.lastModified();
        if (lastModified <= 0) {
            return false;
        }
        String path = file.getPath();
        if (hashMap.containsKey(path) && ((Long) hashMap.get(path)).longValue() == lastModified) {
            return false;
        }
        hashMap.put(path, Long.valueOf(lastModified));
        return true;
    }

    public static void addTextToDropBox(DropBoxManager dropBoxManager, String str, String str2, String str3, int i) {
        Slog.i("BootReceiver", "Copying " + str3 + " to DropBox (" + str + ")");
        dropBoxManager.addText(str, str2);
        EventLog.writeEvent(81002, str3, Integer.valueOf(i), str);
    }

    public static void addAuditErrorsToDropBox(DropBoxManager dropBoxManager, HashMap hashMap, String str, int i, String str2) {
        if (dropBoxManager == null || !dropBoxManager.isTagEnabled(str2)) {
            return;
        }
        Slog.i("BootReceiver", "Copying audit failures to DropBox");
        File file = new File("/proc/last_kmsg");
        long lastModified = file.lastModified();
        if (lastModified <= 0) {
            file = new File("/sys/fs/pstore/console-ramoops");
            lastModified = file.lastModified();
            if (lastModified <= 0) {
                file = new File("/sys/fs/pstore/console-ramoops-0");
                lastModified = file.lastModified();
            }
        }
        if (lastModified <= 0) {
            return;
        }
        if (hashMap.containsKey(str2) && ((Long) hashMap.get(str2)).longValue() == lastModified) {
            return;
        }
        hashMap.put(str2, Long.valueOf(lastModified));
        String readTextFile = FileUtils.readTextFile(file, i, "[[TRUNCATED]]\n");
        StringBuilder sb = new StringBuilder();
        for (String str3 : readTextFile.split(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE)) {
            if (str3.contains("audit")) {
                sb.append(str3 + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            }
        }
        Slog.i("BootReceiver", "Copied " + sb.toString().length() + " worth of audits to DropBox");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(sb.toString());
        dropBoxManager.addText(str2, sb2.toString());
    }

    public static void addFsckErrorsToDropBoxAndLogFsStat(DropBoxManager dropBoxManager, HashMap hashMap, String str, int i, String str2) {
        boolean z = dropBoxManager != null && dropBoxManager.isTagEnabled(str2);
        Slog.i("BootReceiver", "Checking for fsck errors");
        File file = new File("/dev/fscklogs/log");
        if (file.lastModified() <= 0) {
            return;
        }
        String readTextFile = FileUtils.readTextFile(file, i, "[[TRUNCATED]]\n");
        Pattern compile = Pattern.compile("fs_stat,[^,]*/([^/,]+),(0x[0-9a-fA-F]+)");
        String[] split = readTextFile.split(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        boolean z2 = false;
        int i2 = 0;
        int i3 = 0;
        for (String str3 : split) {
            if (str3.contains("FILE SYSTEM WAS MODIFIED") || str3.contains("[FSCK] Unreachable")) {
                z2 = true;
            } else if (str3.contains("fs_stat")) {
                Matcher matcher = compile.matcher(str3);
                if (matcher.find()) {
                    handleFsckFsStat(matcher, split, i2, i3);
                    i2 = i3;
                } else {
                    Slog.w("BootReceiver", "cannot parse fs_stat:" + str3);
                }
            }
            i3++;
        }
        if (z && z2) {
            addFileToDropBox(dropBoxManager, hashMap, str, "/dev/fscklogs/log", i, str2);
        }
        file.renameTo(new File("/dev/fscklogs/fsck"));
    }

    public static void logFsMountTime() {
        int i;
        for (String str : MOUNT_DURATION_PROPS_POSTFIX) {
            int i2 = SystemProperties.getInt("ro.boottime.init.mount_all." + str, 0);
            if (i2 != 0) {
                str.hashCode();
                switch (str) {
                    case "late":
                        i = 12;
                        FrameworkStatsLog.write(FrameworkStatsLog.BOOT_TIME_EVENT_DURATION_REPORTED, i, i2);
                        break;
                    case "early":
                        i = 11;
                        FrameworkStatsLog.write(FrameworkStatsLog.BOOT_TIME_EVENT_DURATION_REPORTED, i, i2);
                        break;
                    case "default":
                        i = 10;
                        FrameworkStatsLog.write(FrameworkStatsLog.BOOT_TIME_EVENT_DURATION_REPORTED, i, i2);
                        break;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void logSystemServerShutdownTimeMetrics() {
        String readTextFile;
        File file = new File("/data/system/shutdown-metrics.txt");
        String str = null;
        if (file.exists()) {
            try {
                readTextFile = FileUtils.readTextFile(file, 0, null);
            } catch (IOException e) {
                Slog.e("BootReceiver", "Problem reading " + file, e);
            }
            if (!TextUtils.isEmpty(readTextFile)) {
                String str2 = null;
                String str3 = null;
                String str4 = null;
                for (String str5 : readTextFile.split(",")) {
                    String[] split = str5.split(XmlUtils.STRING_ARRAY_SEPARATOR);
                    if (split.length != 2) {
                        Slog.e("BootReceiver", "Wrong format of shutdown metrics - " + readTextFile);
                    } else {
                        if (split[0].startsWith("shutdown_")) {
                            logTronShutdownMetric(split[0], split[1]);
                            if (split[0].equals("shutdown_system_server")) {
                                str4 = split[1];
                            }
                        }
                        if (split[0].equals("reboot")) {
                            str = split[1];
                        } else if (split[0].equals("reason")) {
                            str2 = split[1];
                        } else if (split[0].equals("begin_shutdown")) {
                            str3 = split[1];
                        }
                    }
                }
                logStatsdShutdownAtom(str, str2, str3, str4);
            }
            file.delete();
        }
        readTextFile = null;
        if (!TextUtils.isEmpty(readTextFile)) {
        }
        file.delete();
    }

    public static void logTronShutdownMetric(String str, String str2) {
        try {
            int parseInt = Integer.parseInt(str2);
            if (parseInt >= 0) {
                MetricsLogger.histogram((Context) null, str, parseInt);
            }
        } catch (NumberFormatException unused) {
            Slog.e("BootReceiver", "Cannot parse metric " + str + " int value - " + str2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0065 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0043 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void logStatsdShutdownAtom(String str, String str2, String str3, String str4) {
        boolean z;
        long parseLong;
        if (str != null) {
            if (!str.equals("y")) {
                if (!str.equals("n")) {
                    Slog.e("BootReceiver", "Unexpected value for reboot : " + str);
                }
            } else {
                z = true;
                boolean z2 = z;
                if (str2 == null) {
                    Slog.e("BootReceiver", "No value received for shutdown reason");
                    str2 = "<EMPTY>";
                }
                String str5 = str2;
                long j = 0;
                if (str3 == null) {
                    try {
                        parseLong = Long.parseLong(str3);
                    } catch (NumberFormatException unused) {
                        Slog.e("BootReceiver", "Cannot parse shutdown start time: " + str3);
                    }
                    if (str4 != null) {
                        try {
                            j = Long.parseLong(str4);
                        } catch (NumberFormatException unused2) {
                            Slog.e("BootReceiver", "Cannot parse shutdown duration: " + str3);
                        }
                    } else {
                        Slog.e("BootReceiver", "No value received for shutdown duration");
                    }
                    FrameworkStatsLog.write(56, z2, str5, parseLong, j);
                }
                Slog.e("BootReceiver", "No value received for shutdown start time");
                parseLong = 0;
                if (str4 != null) {
                }
                FrameworkStatsLog.write(56, z2, str5, parseLong, j);
            }
        } else {
            Slog.e("BootReceiver", "No value received for reboot");
        }
        z = false;
        boolean z22 = z;
        if (str2 == null) {
        }
        String str52 = str2;
        long j2 = 0;
        if (str3 == null) {
        }
        parseLong = 0;
        if (str4 != null) {
        }
        FrameworkStatsLog.write(56, z22, str52, parseLong, j2);
    }

    public static void logFsShutdownTime() {
        File file;
        String[] strArr = LAST_KMSG_FILES;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                file = null;
                break;
            }
            file = new File(strArr[i]);
            if (file.exists()) {
                break;
            } else {
                i++;
            }
        }
        if (file == null) {
            return;
        }
        try {
            Matcher matcher = Pattern.compile("powerctl_shutdown_time_ms:([0-9]+):([0-9]+)", 8).matcher(FileUtils.readTextFile(file, -16384, null));
            if (matcher.find()) {
                FrameworkStatsLog.write(FrameworkStatsLog.BOOT_TIME_EVENT_DURATION_REPORTED, 9, Integer.parseInt(matcher.group(1)));
                FrameworkStatsLog.write(242, 2, Integer.parseInt(matcher.group(2)));
                Slog.i("BootReceiver", "boot_fs_shutdown," + matcher.group(1) + "," + matcher.group(2));
                return;
            }
            FrameworkStatsLog.write(242, 2, 4);
            Slog.w("BootReceiver", "boot_fs_shutdown, string not found");
        } catch (IOException e) {
            Slog.w("BootReceiver", "cannot read last msg", e);
        }
    }

    public static int fixFsckFsStat(String str, int i, String[] strArr, int i2, int i3) {
        String str2;
        boolean z;
        int i4;
        if ((i & 1024) != 0) {
            Pattern compile = Pattern.compile("Pass ([1-9]E?):");
            Pattern compile2 = Pattern.compile("Inode [0-9]+ extent tree.*could be shorter");
            String str3 = "";
            boolean z2 = false;
            boolean z3 = false;
            boolean z4 = false;
            int i5 = i2;
            while (i5 < i3) {
                str2 = strArr[i5];
                if (str2.contains("FILE SYSTEM WAS MODIFIED") || str2.contains("[FSCK] Unreachable")) {
                    break;
                }
                if (str2.startsWith("Pass ")) {
                    Matcher matcher = compile.matcher(str2);
                    if (matcher.find()) {
                        str3 = matcher.group(1);
                    }
                    i4 = 1;
                } else if (str2.startsWith("Inode ")) {
                    if (!compile2.matcher(str2).find() || !str3.equals("1")) {
                        z = true;
                        break;
                    }
                    Slog.i("BootReceiver", "fs_stat, partition:" + str + " found tree optimization:" + str2);
                    i4 = 1;
                    z2 = true;
                } else if (str2.startsWith("[QUOTA WARNING]") && str3.equals("5")) {
                    Slog.i("BootReceiver", "fs_stat, partition:" + str + " found quota warning:" + str2);
                    if (!z2) {
                        z = false;
                        z3 = true;
                        break;
                    }
                    i4 = 1;
                    z3 = true;
                } else {
                    if (!str2.startsWith("Update quota info") || !str3.equals("5")) {
                        if (str2.startsWith("Timestamp(s) on inode") && str2.contains("beyond 2310-04-04 are likely pre-1970") && str3.equals("1")) {
                            Slog.i("BootReceiver", "fs_stat, partition:" + str + " found timestamp adjustment:" + str2);
                            int i6 = i5 + 1;
                            if (strArr[i6].contains("Fix? yes")) {
                                i5 = i6;
                            }
                            i4 = 1;
                            z4 = true;
                        } else {
                            str2 = str2.trim();
                            if (!str2.isEmpty() && !str3.isEmpty()) {
                                z = true;
                                break;
                            }
                        }
                    }
                    i4 = 1;
                }
                i5 += i4;
            }
            str2 = null;
            z = false;
            if (z) {
                if (str2 != null) {
                    Slog.i("BootReceiver", "fs_stat, partition:" + str + " fix:" + str2);
                }
            } else if (z3 && !z2) {
                Slog.i("BootReceiver", "fs_stat, got quota fix without tree optimization, partition:" + str);
            } else if ((z2 && z3) || z4) {
                Slog.i("BootReceiver", "fs_stat, partition:" + str + " fix ignored");
                return i & (-1025);
            }
        }
        return i;
    }

    public static void handleFsckFsStat(Matcher matcher, String[] strArr, int i, int i2) {
        String group = matcher.group(1);
        try {
            int fixFsckFsStat = fixFsckFsStat(group, Integer.decode(matcher.group(2)).intValue(), strArr, i, i2);
            if ("userdata".equals(group) || "data".equals(group)) {
                FrameworkStatsLog.write(242, 3, fixFsckFsStat);
            }
            Slog.i("BootReceiver", "fs_stat, partition:" + group + " stat:0x" + Integer.toHexString(fixFsckFsStat));
        } catch (NumberFormatException unused) {
            Slog.w("BootReceiver", "cannot parse fs_stat: partition:" + group + " stat:" + matcher.group(2));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x013c, code lost:
    
        if (r2 != false) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0116, code lost:
    
        if (r7 != false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00be, code lost:
    
        if (r7 != false) goto L90;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static HashMap readTimestamps() {
        HashMap hashMap;
        boolean z;
        XmlPullParserException e;
        NullPointerException e2;
        IllegalStateException e3;
        IOException e4;
        FileInputStream openRead;
        TypedXmlPullParser resolvePullParser;
        int next;
        AtomicFile atomicFile = sFile;
        synchronized (atomicFile) {
            hashMap = new HashMap();
            boolean z2 = false;
            try {
                try {
                    try {
                        openRead = atomicFile.openRead();
                        try {
                            resolvePullParser = Xml.resolvePullParser(openRead);
                            do {
                                next = resolvePullParser.next();
                                z = true;
                                if (next == 2) {
                                    break;
                                }
                            } while (next != 1);
                        } finally {
                        }
                    } catch (Throwable th) {
                        z = false;
                        th = th;
                        if (!z) {
                            hashMap.clear();
                        }
                        throw th;
                    }
                } catch (FileNotFoundException unused) {
                } catch (IOException e5) {
                    z = false;
                    e4 = e5;
                } catch (IllegalStateException e6) {
                    z = false;
                    e3 = e6;
                } catch (NullPointerException e7) {
                    z = false;
                    e2 = e7;
                } catch (XmlPullParserException e8) {
                    z = false;
                    e = e8;
                }
                if (next != 2) {
                    throw new IllegalStateException("no start tag found");
                }
                int depth = resolvePullParser.getDepth();
                while (true) {
                    int next2 = resolvePullParser.next();
                    if (next2 == 1 || (next2 == 3 && resolvePullParser.getDepth() <= depth)) {
                        break;
                    }
                    if (next2 != 3 && next2 != 4) {
                        if (resolvePullParser.getName().equals("log")) {
                            hashMap.put(resolvePullParser.getAttributeValue((String) null, "filename"), Long.valueOf(resolvePullParser.getAttributeLong((String) null, "timestamp")));
                        } else {
                            Slog.w("BootReceiver", "Unknown tag: " + resolvePullParser.getName());
                            com.android.internal.util.XmlUtils.skipCurrentTag(resolvePullParser);
                        }
                    }
                }
                if (openRead != null) {
                    try {
                        openRead.close();
                    } catch (FileNotFoundException unused2) {
                        z2 = true;
                        Slog.i("BootReceiver", "No existing last log timestamp file " + sFile.getBaseFile() + "; starting empty");
                    } catch (IOException e9) {
                        e4 = e9;
                        Slog.w("BootReceiver", "Failed parsing " + e4);
                    } catch (IllegalStateException e10) {
                        e3 = e10;
                        Slog.w("BootReceiver", "Failed parsing " + e3);
                        if (!z) {
                            hashMap.clear();
                        }
                        return hashMap;
                    } catch (NullPointerException e11) {
                        e2 = e11;
                        Slog.w("BootReceiver", "Failed parsing " + e2);
                        if (!z) {
                            hashMap.clear();
                        }
                        return hashMap;
                    } catch (XmlPullParserException e12) {
                        e = e12;
                        Slog.w("BootReceiver", "Failed parsing " + e);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        return hashMap;
    }

    public static void writeTimestamps(HashMap hashMap) {
        AtomicFile atomicFile = sFile;
        synchronized (atomicFile) {
            try {
                try {
                    FileOutputStream startWrite = atomicFile.startWrite();
                    try {
                        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                        resolveSerializer.startDocument((String) null, Boolean.TRUE);
                        resolveSerializer.startTag((String) null, "log-files");
                        for (String str : hashMap.keySet()) {
                            resolveSerializer.startTag((String) null, "log");
                            resolveSerializer.attribute((String) null, "filename", str);
                            resolveSerializer.attributeLong((String) null, "timestamp", ((Long) hashMap.get(str)).longValue());
                            resolveSerializer.endTag((String) null, "log");
                        }
                        resolveSerializer.endTag((String) null, "log-files");
                        resolveSerializer.endDocument();
                        sFile.finishWrite(startWrite);
                    } catch (IOException e) {
                        Slog.w("BootReceiver", "Failed to write timestamp file, using the backup: " + e);
                        sFile.failWrite(startWrite);
                    }
                } catch (IOException e2) {
                    Slog.w("BootReceiver", "Failed to write timestamp file: " + e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public ResetReasonFactory getResetReasonFactory() {
        return ResetReasonFactory.getInstance();
    }

    public class ResetReasonFactory {
        public static ResetReasonFactory instance;

        public static ResetReasonFactory getInstance() {
            if (instance == null) {
                instance = new ResetReasonFactory();
            }
            return instance;
        }

        public ResetReasonCode createResetReasonCode(String str) {
            String[] split = str.split("\\|");
            StringBuilder sb = new StringBuilder();
            sb.append("com.android.server.ResetReason");
            sb.append(split.length > 1 ? split[1] : "Unknown");
            try {
                return (ResetReasonCode) Class.forName(sb.toString()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                Slog.w("BootReceiver", "Cannot find class " + e.getMessage());
                return new ResetReasonUnknown();
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e2) {
                Slog.w("BootReceiver", "Cannot obtain an instance of parameterless constructor: " + e2.getMessage());
                return new ResetReasonUnknown();
            }
        }
    }
}
