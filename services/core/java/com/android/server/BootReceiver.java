package com.android.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Build;
import android.os.DropBoxManager;
import android.os.Environment;
import android.os.FileUtils;
import android.os.MessageQueue;
import android.os.ParcelFileDescriptor;
import android.os.SemHqmManager;
import android.os.SystemProperties;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.AtomicFile;
import android.util.EventLog;
import android.util.Slog;
import android.util.Xml;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.am.DropboxRateLimiter;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class BootReceiver extends BroadcastReceiver {
    public static final int LASTK_LOG_SIZE;
    public static final String[] LAST_KMSG_FILES;
    public static final int LOG_SIZE;
    public static final long MAX_TOMBSTONE_SIZE_BYTES;
    public static final String[] MOUNT_DURATION_PROPS_POSTFIX;
    public static final File TOMBSTONE_TMP_DIR;
    public static final File lastHeaderFile;
    public static String logFileKernel;
    public static AudioManager mAudioManager;
    public static boolean proc_rr_read_done;
    public static String proc_rr_value;
    public static int reset;
    public static final DropboxRateLimiter sDropboxRateLimiter;
    public static final AtomicFile sFile;
    public static int sSentReports;
    public static String storeExtraInfo;
    public static boolean store_lastkmsg_read_done;
    public static int store_lastkmsg_val;
    public static final UUID uniqueID;
    public String EVENT_ID = Build.VERSION.INCREMENTAL;
    public String RESULT_CODE = null;
    public String LOG_FILE = null;
    public boolean isCaseByRescueParty = false;
    public SaveLastkmsg saveLastkmsg = null;
    public SemHqmManager mSemHqmManager = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.BootReceiver$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return Long.valueOf(((File) obj).lastModified())
                    .compareTo(Long.valueOf(((File) obj2).lastModified()));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.BootReceiver$2, reason: invalid class name */
    public final class AnonymousClass2 implements FileFilter {
        @Override // java.io.FileFilter
        public final boolean accept(File file) {
            return file.getName().startsWith("dumpstate_");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.BootReceiver$4, reason: invalid class name */
    public final class AnonymousClass4 implements MessageQueue.OnFileDescriptorEventListener {
        public byte[] mTraceBuffer;

        @Override // android.os.MessageQueue.OnFileDescriptorEventListener
        public final int onFileDescriptorEvents(FileDescriptor fileDescriptor, int i) {
            try {
                if (Os.read(fileDescriptor, this.mTraceBuffer, 0, 1024) > 0
                        && new String(this.mTraceBuffer).indexOf("\n") != -1
                        && BootReceiver.sSentReports < 8) {
                    SystemProperties.set("dmesgd.start", "1");
                    BootReceiver.sSentReports++;
                }
                return 1;
            } catch (Exception e) {
                Slog.wtf("BootReceiver", "Error watching for trace events", e);
                return 0;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Dump {
        public final String dumpInFix;
        public final List fileList;
        public final int listMax;

        public Dump(int i, String str, List list) {
            this.fileList = list;
            this.listMax = i;
            this.dumpInFix = str;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ResetReasonFactory {
        public static ResetReasonFactory instance;

        public static ResetReasonCode createResetReasonCode(String str) {
            String[] split = str.split("\\|");
            StringBuilder sb = new StringBuilder("com.android.server.ResetReason");
            sb.append(split.length > 1 ? split[1] : "Unknown");
            try {
                Class[] clsArr = new Class[0];
                return (ResetReasonCode)
                        Class.forName(sb.toString()).getDeclaredConstructor(null).newInstance(null);
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                Slog.w("BootReceiver", "Cannot find class " + e.getMessage());
                return new ResetReasonUnknown();
            } catch (IllegalAccessException
                    | InstantiationException
                    | InvocationTargetException e2) {
                Slog.w(
                        "BootReceiver",
                        "Cannot obtain an instance of parameterless constructor: "
                                + e2.getMessage());
                return new ResetReasonUnknown();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SaveLastkmsg extends Thread {
        public FileInputStream fin = null;
        public FileOutputStream fout = null;
        public final byte[] buffer = new byte[2048];
        public boolean isSaveLastkmsgDone = false;

        public SaveLastkmsg() {}

        /* JADX WARN: Code restructure failed: missing block: B:118:0x041e, code lost:

           if (r0 != null) goto L118;
        */
        /* JADX WARN: Code restructure failed: missing block: B:119:0x0420, code lost:

           r0.close();
        */
        /* JADX WARN: Code restructure failed: missing block: B:154:0x04c2, code lost:

           if (r2 == null) goto L234;
        */
        /* JADX WARN: Code restructure failed: missing block: B:202:0x0454, code lost:

           if (r0 != null) goto L118;
        */
        /* JADX WARN: Removed duplicated region for block: B:133:0x0488 A[Catch: all -> 0x048e, IOException -> 0x0491, LOOP:8: B:130:0x047d->B:133:0x0488, LOOP_END, TRY_LEAVE, TryCatch #15 {IOException -> 0x0491, blocks: (B:131:0x047d, B:133:0x0488), top: B:130:0x047d }] */
        /* JADX WARN: Removed duplicated region for block: B:134:0x0493 A[EDGE_INSN: B:134:0x0493->B:135:0x0493 BREAK  A[LOOP:8: B:130:0x047d->B:133:0x0488], EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:164:0x04e2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:170:? A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:171:0x04dd A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:197:0x0445 A[Catch: IOException -> 0x0448, TRY_LEAVE, TryCatch #6 {IOException -> 0x0448, blocks: (B:195:0x0441, B:197:0x0445), top: B:194:0x0441 }] */
        /* JADX WARN: Removed duplicated region for block: B:203:0x044f A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:207:0x044a A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:217:0x04ea A[Catch: IOException -> 0x04ed, TRY_LEAVE, TryCatch #4 {IOException -> 0x04ed, blocks: (B:215:0x04e6, B:217:0x04ea), top: B:214:0x04e6 }] */
        /* JADX WARN: Removed duplicated region for block: B:221:0x04f4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:228:0x04fb A[Catch: IOException -> 0x04fe, TRY_LEAVE, TryCatch #9 {IOException -> 0x04fe, blocks: (B:226:0x04f7, B:228:0x04fb), top: B:225:0x04f7 }] */
        /* JADX WARN: Removed duplicated region for block: B:231:? A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:234:0x04ef A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void logLastKmsg() {
            /*
                Method dump skipped, instructions count: 1279
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.BootReceiver.SaveLastkmsg.logLastKmsg():void");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            File file = new File("/data/log");
            File[] listFiles = file.listFiles();
            if (file.exists()) {
                if (listFiles != null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new Dump(5, "lastkmsg", new ArrayList()));
                    arrayList.add(new Dump(1, "latest_lastkmsg", new ArrayList()));
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        Dump dump = (Dump) it.next();
                        try {
                            BootReceiver bootReceiver = BootReceiver.this;
                            List list = dump.fileList;
                            int i = dump.listMax;
                            String str = dump.dumpInFix;
                            int i2 = BootReceiver.LOG_SIZE;
                            bootReceiver.getClass();
                            BootReceiver._trimADumpFile(listFiles, list, i, str);
                        } catch (Exception e) {
                            Slog.e("BootReceiver", "trim" + dump.dumpInFix + " error" + e);
                        }
                    }
                }
            } else if (!file.mkdir()) {
                Slog.e("BootReceiver", "trimLastKmsg - logFolder mkdir failed");
            }
            logLastKmsg();
            this.isSaveLastkmsgDone = true;
        }

        public final int waitUntildebughistoryDone() {
            for (int i = 0; i < 30; i++) {
                try {
                    if (SystemProperties.getInt("sys.boot.debug_history", 0) == 1) {
                        return i;
                    }
                    Thread.sleep(1000L);
                } catch (Exception e) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            e, "waitUntildebughistoryDone error", "BootReceiver");
                    return -1;
                }
            }
            return -1;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:271:0x0351, code lost:

       if (r4 == null) goto L120;
    */
    /* JADX WARN: Code restructure failed: missing block: B:282:0x0353, code lost:

       r4.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x0371, code lost:

       if (r4 != null) goto L327;
    */
    /* JADX WARN: Removed duplicated region for block: B:109:0x05e2  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0600  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x06f6  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0717  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x078f  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0792  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x07fa  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0810  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0803 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x07e9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:182:0x07df  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0604  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0506 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:294:0x036e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:303:0x04df A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:309:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:310:0x04da A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x03ae  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0418  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x043c  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x048c  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x04f6  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0510  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x057f  */
    /* renamed from: -$$Nest$mlogBootEvents, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m52$$Nest$mlogBootEvents(
            com.android.server.BootReceiver r35, android.content.Context r36) {
        /*
            Method dump skipped, instructions count: 2108
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.BootReceiver.m52$$Nest$mlogBootEvents(com.android.server.BootReceiver,"
                    + " android.content.Context):void");
    }

    /* renamed from: -$$Nest$mlogLastAboxMsg, reason: not valid java name */
    public static void m53$$Nest$mlogLastAboxMsg(
            BootReceiver bootReceiver, ZipOutputStream zipOutputStream) {
        bootReceiver.getClass();
        String[] strArr = {
            "/sys/kernel/debug/abox/snapshot_0/log",
            "/sys/kernel/debug/abox/snapshot_1/log",
            "/proc/abox/snapshot_0/log",
            "/proc/abox/snapshot_1/log"
        };
        String[] strArr2 = {
            "/sys/kernel/debug/abox/snapshot_0/valid",
            "/sys/kernel/debug/abox/snapshot_1/valid",
            "/proc/abox/snapshot_0/valid",
            "/proc/abox/snapshot_1/valid"
        };
        byte[] bArr = new byte[4096];
        FileInputStream fileInputStream = null;
        for (int i = 0; i < 4; i++) {
            File file = new File(strArr[i]);
            File file2 = new File(strArr2[i]);
            String m =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i % 2, "lastaboxmsg_", ".bin");
            if (file.isFile()) {
                try {
                    try {
                        if (file2.isFile()) {
                            String readTextFile = FileUtils.readTextFile(file2, 4, null);
                            if (readTextFile.contains("Y")) {
                                Slog.d("BootReceiver", "get " + m);
                                FileInputStream fileInputStream2 = new FileInputStream(file);
                                try {
                                    zipOutputStream.putNextEntry(new ZipEntry(m));
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
                                Slog.d("BootReceiver", "skip dump " + m + " valid:" + readTextFile);
                            }
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException unused3) {
                            }
                        }
                    } catch (IOException e3) {
                        e = e3;
                    } catch (NullPointerException e4) {
                        e = e4;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        }
    }

    /* renamed from: -$$Nest$mproc_reset_reason, reason: not valid java name */
    public static /* bridge */ /* synthetic */ String m54$$Nest$mproc_reset_reason(
            BootReceiver bootReceiver) {
        bootReceiver.getClass();
        return proc_reset_reason();
    }

    /* renamed from: -$$Nest$mwaitUntileRRpDone, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m55$$Nest$mwaitUntileRRpDone(
            BootReceiver bootReceiver) {
        bootReceiver.getClass();
        waitUntileRRpDone();
    }

    static {
        int i = SystemProperties.getInt("ro.debuggable", 0);
        int i2 = EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
        LOG_SIZE = i == 1 ? 98304 : 65536;
        if (SystemProperties.getInt("ro.debuggable", 0) == 1) {
            i2 = 196608;
        }
        LASTK_LOG_SIZE = i2;
        TOMBSTONE_TMP_DIR = new File("/data/tombstones");
        sFile =
                new AtomicFile(
                        new File(Environment.getDataSystemDirectory(), "log-files.xml"),
                        "log-files");
        lastHeaderFile = new File(Environment.getDataSystemDirectory(), "last-header.txt");
        MOUNT_DURATION_PROPS_POSTFIX = new String[] {"early", "default", "late"};
        LAST_KMSG_FILES = new String[] {"/sys/fs/pstore/console-ramoops", "/proc/last_kmsg"};
        sSentReports = 0;
        MAX_TOMBSTONE_SIZE_BYTES = DropBoxManagerService.DEFAULT_QUOTA_KB * 1024;
        reset = -1;
        storeExtraInfo = "";
        logFileKernel = "";
        uniqueID = UUID.randomUUID();
        mAudioManager = null;
        store_lastkmsg_read_done = false;
        store_lastkmsg_val = -1;
        proc_rr_read_done = false;
        proc_rr_value = null;
        sDropboxRateLimiter = new DropboxRateLimiter();
    }

    /* JADX WARN: Code restructure failed: missing block: B:175:0x0241, code lost:

       if (r8.exists() == false) goto L103;
    */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x024b, code lost:

       if (r8.length() <= 0) goto L102;
    */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x024d, code lost:

       r6 = android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(r6, "InsufficientLogInfo");
    */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0252, code lost:

       r6 = android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(r6, "EmptyCrashBuffer");
    */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x0257, code lost:

       r6 = android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(r6, "NoLogFile");
    */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x02be, code lost:

       if (r14 != null) goto L176;
    */
    /* JADX WARN: Removed duplicated region for block: B:103:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0312 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:115:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x030d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0308 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x02bb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x02b6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String GetRescuePartyLog() {
        /*
            Method dump skipped, instructions count: 790
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.BootReceiver.GetRescuePartyLog():java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x056d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01be A[LOOP:4: B:145:0x01bc->B:146:0x01be, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01d2 A[LOOP:5: B:149:0x01cc->B:151:0x01d2, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x033a A[Catch: IOException -> 0x0336, TRY_LEAVE, TryCatch #2 {IOException -> 0x0336, blocks: (B:191:0x0332, B:185:0x033a), top: B:190:0x0332 }] */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0332 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0279 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0358  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x03a0  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x03cf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String GetResetLog(java.lang.String r26) {
        /*
            Method dump skipped, instructions count: 1393
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.BootReceiver.GetResetLog(java.lang.String):java.lang.String");
    }

    public static void _trimADumpFile(File[] fileArr, List list, int i, String str) {
        for (File file : fileArr) {
            String name = file.getName();
            if (file.isFile() && name.startsWith("dumpstate_".concat(str))) {
                ((ArrayList) list).add(file);
            }
        }
        Collections.sort(list, new AnonymousClass1());
        StringBuilder sb = new StringBuilder("trim");
        sb.append(str);
        sb.append(" - Num of existing listOf");
        sb.append(str);
        sb.append(" is ");
        ArrayList arrayList = (ArrayList) list;
        sb.append(arrayList.size());
        Slog.i("BootReceiver", sb.toString());
        while (arrayList.size() >= i) {
            StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("trim", str, " - Delete file");
            m.append(((File) arrayList.get(0)).getName());
            Slog.i("BootReceiver", m.toString());
            if (!((File) arrayList.get(0)).delete()) {
                StringBuilder m2 = DumpUtils$$ExternalSyntheticOutline0.m("trim", str, " - ");
                m2.append(((File) arrayList.get(0)).getName());
                m2.append(" delete failed");
                Slog.e("BootReceiver", m2.toString());
            }
            arrayList.remove(0);
        }
    }

    public static void addAugmentedProtoToDropbox(
            File file,
            DropBoxManager dropBoxManager,
            DropboxRateLimiter.RateLimitResult rateLimitResult) {
        ParcelFileDescriptor open;
        if (file.length() > MAX_TOMBSTONE_SIZE_BYTES) {
            Slog.w("BootReceiver", "Tombstone too large to add to DropBox: " + file.toPath());
            return;
        }
        byte[] readAllBytes = Files.readAllBytes(file.toPath());
        File createTempFile = File.createTempFile(file.getName(), ".tmp", TOMBSTONE_TMP_DIR);
        Files.setPosixFilePermissions(
                createTempFile.toPath(), PosixFilePermissions.fromString("rw-rw----"));
        try {
            try {
                try {
                    open = ParcelFileDescriptor.open(createTempFile, 805306368);
                } catch (IOException | RuntimeException e) {
                    Slog.e("BootReceiver", "Exception during write: " + createTempFile, e);
                }
                try {
                    ProtoOutputStream protoOutputStream =
                            new ProtoOutputStream(open.getFileDescriptor());
                    protoOutputStream.write(1151051235329L, readAllBytes);
                    protoOutputStream.write(
                            1120986464258L, rateLimitResult.mDroppedCountSinceRateLimitActivated);
                    protoOutputStream.flush();
                    dropBoxManager.addFile(
                            "SYSTEM_TOMBSTONE_PROTO_WITH_HEADERS", createTempFile, 0);
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
            } catch (FileNotFoundException e2) {
                Slog.e("BootReceiver", "failed to open for write: " + createTempFile, e2);
                throw e2;
            }
        } finally {
            try {
                createTempFile.delete();
            } catch (Exception unused) {
            }
        }
    }

    public static void addFileWithFootersToDropBox(
            DropBoxManager dropBoxManager,
            HashMap hashMap,
            String str,
            String str2,
            String str3,
            int i,
            String str4) {
        if (dropBoxManager == null || !dropBoxManager.isTagEnabled(str4)) {
            return;
        }
        File file = new File(str3);
        if (recordFileTimestamp(file, hashMap)) {
            String readTextFile = FileUtils.readTextFile(file, i, "[[TRUNCATED]]\n");
            String m = AnyMotionDetector$$ExternalSyntheticOutline0.m(str, readTextFile, str2);
            if (str4.equals("SYSTEM_TOMBSTONE") && readTextFile.contains(">>> system_server <<<")) {
                addTextToDropBox(dropBoxManager, "system_server_native_crash", m, str3, i);
            }
            if (str4.equals("SYSTEM_TOMBSTONE")) {
                FrameworkStatsLog.write(186);
            }
            addTextToDropBox(dropBoxManager, str4, m, str3, i);
        }
    }

    public static void addLastkToDropBox(
            DropBoxManager dropBoxManager,
            HashMap hashMap,
            String str,
            String str2,
            String str3,
            int i,
            String str4) {
        int length = str2.length() + str.length() + 14;
        if (LASTK_LOG_SIZE + length > 196608) {
            i = 196608 > length ? -(196608 - length) : 0;
        }
        addFileWithFootersToDropBox(dropBoxManager, hashMap, str, str2, str3, i, str4);
    }

    public static void addTextToDropBox(
            DropBoxManager dropBoxManager, String str, String str2, String str3, int i) {
        Slog.i(
                "BootReceiver",
                XmlUtils$$ExternalSyntheticOutline0.m("Copying ", str3, " to DropBox (", str, ")"));
        dropBoxManager.addText(str, str2);
        EventLog.writeEvent(81002, str3, Integer.valueOf(i), str);
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
                if (str2.contains("FILE SYSTEM WAS MODIFIED")
                        || str2.contains("[FSCK] Unreachable")) {
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
                    Slog.i(
                            "BootReceiver",
                            "fs_stat, partition:" + str + " found tree optimization:" + str2);
                    i4 = 1;
                    z2 = true;
                } else if (str2.startsWith("[QUOTA WARNING]") && str3.equals("5")) {
                    Slog.i(
                            "BootReceiver",
                            "fs_stat, partition:" + str + " found quota warning:" + str2);
                    if (!z2) {
                        z = false;
                        z3 = true;
                        break;
                    }
                    i4 = 1;
                    z3 = true;
                } else {
                    if (!str2.startsWith("Update quota info") || !str3.equals("5")) {
                        if (!str2.startsWith("Timestamp(s) on inode")
                                || !str2.contains("beyond 2310-04-04 are likely pre-1970")
                                || !str3.equals("1")) {
                            str2 = str2.trim();
                            if (!str2.isEmpty() && !str3.isEmpty()) {
                                z = true;
                                break;
                            }
                        } else {
                            Slog.i(
                                    "BootReceiver",
                                    "fs_stat, partition:"
                                            + str
                                            + " found timestamp adjustment:"
                                            + str2);
                            int i6 = i5 + 1;
                            if (strArr[i6].contains("Fix? yes")) {
                                i5 = i6;
                            }
                            i4 = 1;
                            z4 = true;
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
                Slog.i(
                        "BootReceiver",
                        "fs_stat, got quota fix without tree optimization, partition:" + str);
            } else if ((z2 && z3) || z4) {
                BootReceiver$$ExternalSyntheticOutline0.m58m(
                        "fs_stat, partition:", str, " fix ignored", "BootReceiver");
                return i & (-1025);
            }
        }
        return i;
    }

    public static String getANRFileName() {
        File[] listFiles =
                new File("/data/anr").listFiles(new BootReceiver$$ExternalSyntheticLambda8(0));
        if (listFiles == null || listFiles.length == 0) {
            return null;
        }
        Arrays.sort(listFiles);
        return "/data/anr/" + listFiles[listFiles.length - 1].getName();
    }

    public static String getBootHeadersToLogAndUpdate() {
        String str = null;
        try {
            str = FileUtils.readTextFile(lastHeaderFile, 0, null);
        } catch (IOException unused) {
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(512, "Build: ");
        m.append(Build.FINGERPRINT);
        m.append("\nHardware: ");
        m.append(Build.BOARD);
        m.append("\nRevision: ");
        m.append(SystemProperties.get("ro.revision", ""));
        m.append("\nBootloader: ");
        m.append(Build.BOOTLOADER);
        m.append("\nRadio: ");
        m.append(Build.getRadioVersion());
        m.append("\nKernel: ");
        m.append(FileUtils.readTextFile(new File("/proc/version"), 1024, "...\n"));
        long sysconf = Os.sysconf(OsConstants._SC_PAGESIZE);
        if (sysconf != 4096) {
            BootReceiver$$ExternalSyntheticOutline0.m(m, "PageSize: ", sysconf, "\n");
        }
        m.append("\n");
        String sb = m.toString();
        try {
            FileUtils.stringToFile(lastHeaderFile, sb);
        } catch (IOException e) {
            Slog.e("BootReceiver", "Error writing " + lastHeaderFile, e);
        }
        return str == null
                ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "isPrevious: false\n", sb)
                : "isPrevious: true\n".concat(str);
    }

    public static int is_store_lastkmsg() {
        String str;
        File file = new File("/proc/store_lastkmsg");
        if (!store_lastkmsg_read_done) {
            if (file.isFile()) {
                store_lastkmsg_val = 0;
                try {
                    str = FileUtils.readTextFile(file, 1024, "\n");
                } catch (IOException e) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            "PROC_STORE_KMSG : read fail ", e, "BootReceiver");
                    str = null;
                }
                if (str != null && str.length() >= 1 && str.substring(0, 1).equals("1")) {
                    store_lastkmsg_val = 1;
                }
            } else {
                Slog.e("BootReceiver", "PROC_STORE_KMSG : no exist");
            }
            store_lastkmsg_read_done = true;
        }
        return store_lastkmsg_val;
    }

    public static void moveFile(String str, String str2) {
        try {
            Path path = Paths.get(str, new String[0]);
            Path path2 = Paths.get(str2, new String[0]);
            if (Files.exists(path, new LinkOption[0])) {
                Files.move(path, path2, StandardCopyOption.REPLACE_EXISTING);
                FileUtils.setPermissions(
                        str2, FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED, 1000, 1007);
            }
        } catch (IOException e) {
            BootReceiver$$ExternalSyntheticOutline0.m("Failed to move file", e, "BootReceiver");
        }
    }

    public static String proc_reset_reason() {
        File file = new File("/proc/reset_reason");
        if (!proc_rr_read_done) {
            if (file.isFile()) {
                try {
                    proc_rr_value = FileUtils.readTextFile(file, 1024, "\n");
                } catch (IOException e) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            "PROC_RESET_REASON : read fail ", e, "BootReceiver");
                }
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder("reset_reason = "), proc_rr_value, "BootReceiver");
                String str = proc_rr_value;
                if (str != null && str.length() >= 2) {
                    proc_rr_value = proc_rr_value.substring(0, 2);
                }
            } else {
                Slog.e("BootReceiver", "/proc/reset_reason : no exist");
            }
            if (proc_rr_value == null) {
                proc_rr_value = "NA";
            }
            proc_rr_read_done = true;
        }
        return proc_rr_value;
    }

    public static String readSysfsFile(String str) {
        File file = new File(str);
        if (file.isFile()) {
            try {
                return FileUtils.readTextFile(file, 32768, null);
            } catch (IOException e) {
                BootReceiver$$ExternalSyntheticOutline0.m("readTextFile error", e, "BootReceiver");
            }
        } else {
            Slog.e("BootReceiver", "no file: ".concat(str));
        }
        return "";
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x016b, code lost:

       if (r2 != false) goto L89;
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0147, code lost:

       if (r7 != false) goto L98;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00dc, code lost:

       if (r7 != false) goto L98;
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0038, code lost:

       if (r6 != 4) goto L113;
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0046, code lost:

       if (r4.getName().equals("log") == false) goto L114;
    */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x005f, code lost:

       android.util.Slog.w("BootReceiver", "Unknown tag: " + r4.getName());
       com.android.internal.util.XmlUtils.skipCurrentTag(r4);
    */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0048, code lost:

       r1.put(r4.getAttributeValue((java.lang.String) null, "filename"), java.lang.Long.valueOf(r4.getAttributeLong((java.lang.String) null, "timestamp")));
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.HashMap readTimestamps() {
        /*
            Method dump skipped, instructions count: 377
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.BootReceiver.readTimestamps():java.util.HashMap");
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

    public static void resetDropboxRateLimiter() {
        sDropboxRateLimiter.reset();
    }

    public static void sendResetLog(Context context, String str) {
        SemHqmManager semHqmManager = (SemHqmManager) context.getSystemService("HqmManagerService");
        if (semHqmManager != null) {
            semHqmManager.sendHWParamToHQM(0, "AP", "REST", "ph", "0.0", "", "", str, "");
            Slog.i("BootReceiver", "send broadcast to HQM about reset reason : " + str);
        }
    }

    public static void waitUntileRRpDone() {
        for (int i = 0; i < 30; i++) {
            try {
                if (SystemProperties.getInt("sys.boot.errp", 0) == 1) {
                    Slog.i("BootReceiver", "We waited make eRRp Done for " + i + "s");
                    return;
                }
                Thread.sleep(1000L);
            } catch (Exception e) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        e, "waitUntileRRpDone error", "BootReceiver");
            }
        }
        Slog.i("BootReceiver", "Waited enough time(30s) for eRRp done, but timed out");
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
                            resolveSerializer.attributeLong(
                                    (String) null,
                                    "timestamp",
                                    ((Long) hashMap.get(str)).longValue());
                            resolveSerializer.endTag((String) null, "log");
                        }
                        resolveSerializer.endTag((String) null, "log-files");
                        resolveSerializer.endDocument();
                        sFile.finishWrite(startWrite);
                    } catch (IOException e) {
                        Slog.w(
                                "BootReceiver",
                                "Failed to write timestamp file, using the backup: " + e);
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

    public final String getDumpFilename(String str) {
        String str2;
        File file = new File("/data/log");
        String str3 = null;
        if ("P|UR".equals(str)) {
            Path path = Paths.get("/data/log/unknown_platform_reset.log", new String[0]);
            if (Files.exists(path, new LinkOption[0])
                    && Files.isRegularFile(path, new LinkOption[0])) {
                return "/data/log/unknown_platform_reset.log";
            }
            return null;
        }
        File[] listFiles = file.listFiles(new BootReceiver$$ExternalSyntheticLambda8(1));
        if (listFiles == null) {
            return null;
        }
        if (ResetReasonFactory.instance == null) {
            ResetReasonFactory.instance = new ResetReasonFactory();
        }
        ResetReasonFactory.instance.getClass();
        String addSuffix = ResetReasonFactory.createResetReasonCode(str).addSuffix();
        if ("".equals(addSuffix)) {
            switch (str) {
                case "K|CP":
                case "K|DP":
                case "K|KP":
                case "K|PP":
                case "K|SP":
                case "K|TP":
                case "K|WP":
                    str2 = "lastkmsg";
                    addSuffix = str2;
                    break;
                default:
                    if (this.isCaseByRescueParty) {
                        str2 = "sys_error";
                        addSuffix = str2;
                        break;
                    } else {
                        addSuffix = null;
                        break;
                    }
            }
        }
        Arrays.sort(listFiles);
        for (File file2 : listFiles) {
            if (file2.isFile()) {
                if (file2.getName().startsWith("dumpstate_" + addSuffix)) {
                    str3 = "/data/log/" + file2.getName();
                }
            }
        }
        return str3;
    }

    public final boolean isNotRescueParty() {
        if ("NONE".equals(RescueParty.getRescuePartyReason())) {
            return true;
        }
        SystemProperties.set(
                "sys.reset_reason", "N|R" + SystemProperties.get("persist.sys.rescue_level", "0"));
        this.isCaseByRescueParty = true;
        return false;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, Intent intent) {
        final boolean equals =
                "android.intent.action.LOCKED_BOOT_COMPLETED".equals(intent.getAction());
        new Thread() { // from class: com.android.server.BootReceiver.3
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Removed duplicated region for block: B:132:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:197:0x01f8  */
            /* JADX WARN: Removed duplicated region for block: B:22:0x00ac  */
            /* JADX WARN: Removed duplicated region for block: B:25:0x00c7  */
            /* JADX WARN: Removed duplicated region for block: B:31:0x031a  */
            /* JADX WARN: Removed duplicated region for block: B:53:0x03fa  */
            /* JADX WARN: Removed duplicated region for block: B:68:0x0624 A[Catch: IOException | IllegalStateException -> 0x062c, TryCatch #6 {IOException | IllegalStateException -> 0x062c, blocks: (B:66:0x061a, B:68:0x0624, B:69:0x062e), top: B:65:0x061a }] */
            @Override // java.lang.Thread, java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    Method dump skipped, instructions count: 1716
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.android.server.BootReceiver.AnonymousClass3.run():void");
            }
        }.start();
        if (equals) {
            return;
        }
        try {
            FileDescriptor open =
                    Os.open(
                            "/sys/kernel/tracing/instances/bootreceiver/trace_pipe",
                            OsConstants.O_RDONLY,
                            FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT);
            AnonymousClass4 anonymousClass4 = new AnonymousClass4();
            anonymousClass4.mTraceBuffer = new byte[1024];
            IoThread.get()
                    .getLooper()
                    .getQueue()
                    .addOnFileDescriptorEventListener(open, 1, anonymousClass4);
        } catch (ErrnoException e) {
            Slog.wtf(
                    "BootReceiver",
                    "Could not open /sys/kernel/tracing/instances/bootreceiver/trace_pipe",
                    e);
        }
    }
}
