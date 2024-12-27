package com.android.server.am;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.INetd;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.PinnerService;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.game.SemGameManager;
import com.samsung.android.knoxguard.service.utils.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class Pageboost {
    public static final boolean BOOTFILE_ENABLED;
    public static final boolean PAGEBOOST_ACTIVE_LAUNCH_ENABLED;
    public static final int PAGEBOOST_ACTIVE_LAUNCH_TIMEOUT;
    public static boolean PAGEBOOST_DAEMON_ENABLED;
    public static final boolean PAGEBOOST_IGNORE_DRAM_SPECIFICATION;
    public static final boolean PAGEBOOST_IO_PREFETCH_ENABLED =
            "true".equals(SystemProperties.get("ro.config.pageboost.io_prefetch.enabled", "true"));
    public static boolean PAGEBOOST_KERNEL_ENABLED;
    public static final boolean PAGEBOOST_MINIMIZE;
    public static final boolean PAGEBOOST_VRAMDISK_WITHOUT_PREDICT;
    public static ActivityManagerService mActivityManagerService;
    public static SQLiteDatabase mAppDB;
    public static PageboostAppDBHelper mAppDBHelper;
    public static LRUPolicy mBootFileManager;
    public static Context mContext;
    public static SQLiteDatabase mFileDB;
    public static PageboostAppDBHelper mFileDBHelper;
    public static PageboostAppList mGlobalAppLRU;
    public static final AnonymousClass1 mPackageReceiver;
    public static final PageboostPredictor mPredictor;
    public static final AnonymousClass1 mReceiver;
    public static FileMapList mZygote64FileMap;
    public static FileMapList mZygoteFileMap;
    public static final boolean munlock_firstapp;
    public static final PageboostHandler sHandler;
    public static final ServiceThread sHandlerThread;
    public static boolean user_unlock_done;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DaemonConnector {
        public OutputStream sOutputStream;
        public LocalSocket sSocket;
        public String sSocketName;

        public final void openSocket() {
            String str = this.sSocketName;
            try {
                LocalSocket localSocket = new LocalSocket(3);
                this.sSocket = localSocket;
                localSocket.connect(
                        new LocalSocketAddress(str, LocalSocketAddress.Namespace.RESERVED));
                this.sOutputStream = this.sSocket.getOutputStream();
            } catch (IOException unused) {
                boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "socket open failed for ", str, "Pageboost");
                this.sSocket = null;
            }
        }

        public final void writeDaemon(ByteBuffer byteBuffer) {
            if (this.sSocket == null) {
                openSocket();
            }
            if (this.sSocket != null) {
                try {
                    this.sOutputStream.write(byteBuffer.array(), 0, byteBuffer.position());
                } catch (IOException unused) {
                    boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                    StringBuilder sb = new StringBuilder("Error writing to socket ");
                    String str = this.sSocketName;
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(sb, str, "Pageboost");
                    try {
                        this.sSocket.close();
                    } catch (IOException unused2) {
                        boolean z2 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                "Error closing socket ", str, "Pageboost");
                    }
                    this.sSocket = null;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FileMapList {
        public final boolean mCorrectness;
        public final Hashtable mFiles = new Hashtable();

        public FileMapList(int i) {
            this.mCorrectness = false;
            if (i <= 0) {
                return;
            }
            String num = Integer.toString(i);
            boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
            Slog.i("Pageboost", "filemap pid : " + num);
            try {
                BufferedReader bufferedReader =
                        new BufferedReader(new FileReader("/proc/" + num + "/filemap_list"));
                try {
                    this.mCorrectness = true;
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            boolean z2 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                            Slog.i("Pageboost", "filemap pid : " + num + ", done correctly");
                            bufferedReader.close();
                            return;
                        }
                        if (this.mCorrectness && !contain(readLine)) {
                            this.mFiles.put(readLine, 1);
                        }
                    }
                } finally {
                }
            } catch (Exception unused) {
                boolean z3 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                Slog.e("Pageboost", "filemap pid : " + num + ", aborted");
                this.mCorrectness = false;
            }
        }

        public final boolean contain(String str) {
            return this.mCorrectness && this.mFiles.get(str) != null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IoRecord {
        public boolean is64bit;
        public PageboostAppInfo mApp;
        public long mBytesRecorded;
        public boolean mCorrectness;
        public int mPid;
        public boolean mRecordDone;

        public static void fillBitmap(int i, int i2, byte[] bArr, boolean z) {
            int i3 = i % 8;
            if (i3 != 0) {
                int i4 = i / 8;
                int i5 = bArr[i4] & 255;
                while (i3 < 8) {
                    i5 = z ? i5 | (1 << i3) : i5 & (~(1 << i3));
                    i3++;
                }
                bArr[i4] =
                        (byte)
                                (i5
                                        & IDnsResolverUnsolicitedEventListener
                                                .DNS_HEALTH_RESULT_TIMEOUT);
                i = (i4 + 1) * 8;
            }
            int i6 = i / 8;
            int i7 = i2 / 8;
            if (i6 < i7) {
                Arrays.fill(bArr, i6, i7, (byte) (z ? 255 : 0));
            }
            int i8 = i2 % 8;
            if (i8 != 0) {
                int i9 = bArr[i7] & 255;
                for (int i10 = 0; i10 < i8; i10++) {
                    i9 = z ? i9 | (1 << i10) : i9 & (~(1 << i10));
                }
                bArr[i7] =
                        (byte)
                                (i9
                                        & IDnsResolverUnsolicitedEventListener
                                                .DNS_HEALTH_RESULT_TIMEOUT);
            }
        }

        public static int readInt(byte[] bArr) {
            return (bArr[0] & 255)
                    | (bArr[3] << 24)
                    | ((bArr[2] & 255) << 16)
                    | ((bArr[1] & 255) << 8);
        }

        public static boolean write(String str, String str2) {
            try {
                FileWriter fileWriter = new FileWriter(new File(str));
                try {
                    fileWriter.write(str2);
                    fileWriter.flush();
                    fileWriter.close();
                    return true;
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public final void getResultFromKernel() {
            if (this.mCorrectness) {
                boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                StringBuilder sb = new StringBuilder("IoRecord pid : ");
                int i = this.mPid;
                sb.append(i);
                Slog.i("Pageboost", sb.toString());
                String str = "/proc/" + i + "/io_record_control";
                if (!write(str, "3")) {
                    Slog.e("Pageboost", "StopRecording Failed");
                    return;
                }
                if (!write(str, "4")) {
                    Slog.e("Pageboost", "PostRecording Failed");
                    return;
                }
                try {
                    byte[] readAllBytes = Files.readAllBytes(Paths.get(str, new String[0]));
                    Slog.i(
                            "Pageboost",
                            "IoRecord pid : " + i + ", result_size : " + readAllBytes.length);
                    long parsingResult = parsingResult(readAllBytes);
                    if (!write(str, "1")) {
                        Slog.e("Pageboost", "iorecord re-init Failed");
                    } else if (parsingResult >= 0) {
                        this.mRecordDone = true;
                    }
                } catch (Exception unused) {
                    boolean z2 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                    Slog.e("Pageboost", "CatRecordedData Failed");
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:47:0x0110  */
        /* JADX WARN: Removed duplicated region for block: B:54:0x0136  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final long parsingResult(byte[] r26) {
            /*
                Method dump skipped, instructions count: 369
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.am.Pageboost.IoRecord.parsingResult(byte[]):long");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LRUPolicy {
        public Object mLock;
        public Object mRecentPrefetchList;

        public PageboostAppList appFilter(PageboostAppList pageboostAppList) {
            boolean contains;
            PageboostAppList pageboostAppList2 = new PageboostAppList();
            ArrayList arrayList = new ArrayList();
            synchronized (this.mLock) {
                if (Pageboost.mGlobalAppLRU != null) {
                    synchronized (pageboostAppList.mPageboostApps) {
                        try {
                            Iterator it = pageboostAppList.mPageboostApps.iterator();
                            while (it.hasNext()) {
                                arrayList.add(((PageboostAppInfo) it.next()).mName);
                            }
                        } finally {
                        }
                    }
                    synchronized (Pageboost.mGlobalAppLRU.mPageboostApps) {
                        Iterator it2 = Pageboost.mGlobalAppLRU.mPageboostApps.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            PageboostAppInfo pageboostAppInfo = (PageboostAppInfo) it2.next();
                            synchronized (pageboostAppList.mPageboostApps) {
                                contains =
                                        pageboostAppList.mPageboostApps.contains(pageboostAppInfo);
                            }
                            if (contains
                                    && Pageboost.m203$$Nest$smgetPidFromPackageName(
                                                    pageboostAppInfo.mName, true)
                                            == 0) {
                                pageboostAppList2.add(pageboostAppInfo, false);
                                break;
                            }
                        }
                    }
                }
                this.mRecentPrefetchList = pageboostAppList2;
            }
            return pageboostAppList2;
        }

        public void mlockAllFiles() {
            LinkedList linkedList = (LinkedList) this.mRecentPrefetchList;
            if (linkedList == null) {
                return;
            }
            Iterator it = linkedList.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if (BatteryService$$ExternalSyntheticOutline0.m45m(str)) {
                    PinnerService.PinnedFile pinFileInternal =
                            PinnerService.pinFileInternal(Integer.MAX_VALUE, str, false);
                    if (pinFileInternal != null) {
                        ((ArrayList) this.mLock).add(pinFileInternal);
                        boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                        Slog.i("Pageboost", "successfully pinned " + str);
                    }
                } else {
                    boolean z2 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                    BootReceiver$$ExternalSyntheticOutline0.m58m(
                            "skip pinning: ", str, " does not exist", "Pageboost");
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class PageboostAppCapture {
        public static final int PAGEBOOST_IO_PREFETCH_LEVEL =
                SystemProperties.getInt("ro.config.pageboost.io_prefetch.level", 3);
        public static boolean record_ongoing;

        /* JADX WARN: Code restructure failed: missing block: B:64:0x025a, code lost:

           r7 = 1;
        */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:153:0x028b  */
        /* JADX WARN: Removed duplicated region for block: B:156:0x026e A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:57:0x0211  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x02d0  */
        /* JADX WARN: Removed duplicated region for block: B:82:0x0316  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static void capture(com.android.server.am.Pageboost.PageboostAppInfo r34, int r35) {
            /*
                Method dump skipped, instructions count: 825
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.am.Pageboost.PageboostAppCapture.capture(com.android.server.am.Pageboost$PageboostAppInfo,"
                        + " int):void");
        }

        public static void captureFinished(IoRecord ioRecord) {
            int i = PAGEBOOST_IO_PREFETCH_LEVEL;
            if (3 == i) {
                PageboostAppInfo pageboostAppInfo = ioRecord.mApp;
                record_ongoing = false;
                if (pageboostAppInfo == null) {
                    boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                    Slog.e("Pageboost", "captureFinished requested for null app");
                    return;
                }
                if (ioRecord.mRecordDone) {
                    pageboostAppInfo.mCaptured = i;
                    pageboostAppInfo.mSizeForPrefetch = ioRecord.mBytesRecorded;
                    pageboostAppInfo.mMapCaptureSizeForPrefetch = 0L;
                    boolean z2 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                    Slog.i("Pageboost", "captureFinished success : " + ioRecord.mBytesRecorded);
                } else {
                    IoRecord.write("/proc/self/io_record_control", "1");
                    boolean z3 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                    Slog.e("Pageboost", "captureFinished fail");
                }
                Vramdisk.add("RecordEnd," + pageboostAppInfo.mName + "," + ioRecord.mRecordDone);
            }
        }

        public static boolean check64Bit(int i) {
            boolean z = false;
            if (i <= 0) {
                return false;
            }
            try {
                RandomAccessFile randomAccessFile =
                        new RandomAccessFile(
                                new File(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                i, "/proc/", "/exe")),
                                "r");
                try {
                    byte[] bArr = new byte[6];
                    byte[] bytes = "\u007fELF".getBytes("UTF-8");
                    randomAccessFile.read(bArr);
                    boolean z2 =
                            Arrays.equals(Arrays.copyOfRange(bArr, 0, 4), bytes) && bArr[4] == 2;
                    randomAccessFile.close();
                    randomAccessFile.close();
                    z = z2;
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            boolean z3 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
            Slog.i("Pageboost", "64 bit checked : " + z + " for " + i);
            return z;
        }

        public static boolean isCaptureTarget(String str) {
            if (str != null) {
                return ((!str.startsWith("/data")
                                        && !str.startsWith("/system")
                                        && !str.startsWith("/product"))
                                || str.startsWith("/data/misc")
                                || str.contains("(deleted"))
                        ? false
                        : true;
            }
            return false;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PageboostAppDBHelper extends SQLiteOpenHelper {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final /* synthetic */ int $r8$classId;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class AppDBErrorHandler implements DatabaseErrorHandler {
            public final /* synthetic */ int $r8$classId;

            public /* synthetic */ AppDBErrorHandler(int i) {
                this.$r8$classId = i;
            }

            @Override // android.database.DatabaseErrorHandler
            public final void onCorruption(SQLiteDatabase sQLiteDatabase) {
                switch (this.$r8$classId) {
                    case 0:
                        boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                        Slog.i("Pageboost", "appDB is deleted by AppDBErrorHandler");
                        SQLiteDatabase.deleteDatabase(new File(sQLiteDatabase.getPath()));
                        break;
                    default:
                        boolean z2 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                        Slog.i("Pageboost", "fileDB is deleted by FileDBErrorHandler");
                        SQLiteDatabase.deleteDatabase(new File(sQLiteDatabase.getPath()));
                        Slog.i("Pageboost", "appDB is deleted by FileDBErrorHandler");
                        Pageboost.deleteDB("/data/misc/pageboost/pageboost_app_db.db");
                        break;
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ PageboostAppDBHelper(
                Context context,
                String str,
                SQLiteDatabase.CursorFactory cursorFactory,
                int i,
                DatabaseErrorHandler databaseErrorHandler,
                int i2) {
            super(context, str, cursorFactory, i, databaseErrorHandler);
            this.$r8$classId = i2;
        }

        public static String createTable(SQLiteDatabase sQLiteDatabase, String str) {
            File file;
            if (str == null) {
                return null;
            }
            try {
                file = new File("/data/misc/pageboost/pageboost_file_db.db");
            } catch (Exception e) {
                boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                Slog.e("Pageboost", "create table failed : ".concat(str));
                e.printStackTrace();
            }
            if (file.exists() && file.length() < 31457280) {
                String str2 =
                        "CREATE TABLE IF NOT EXISTS "
                                + str
                                + " (FILENAME TEXT, OFFSET INTEGER, SIZE INTEGER, FORVRAMDISK"
                                + " INTEGER, BITMAP BLOB, unique (FILENAME, OFFSET) );";
                boolean z2 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                Slog.i("Pageboost", "db create : " + str2);
                sQLiteDatabase.execSQL(str2);
                return str;
            }
            boolean z3 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
            Slog.e("Pageboost", "db length : " + file.exists() + " " + file.length());
            return null;
        }

        public static void dropTable(SQLiteDatabase sQLiteDatabase, String str) {
            try {
                sQLiteDatabase.execSQL(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "DROP TABLE IF EXISTS ", str));
            } catch (Exception e) {
                boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                Slog.e("Pageboost", "drop table failed : " + str);
                e.printStackTrace();
            }
        }

        public static void insertTable(
                SQLiteDatabase sQLiteDatabase,
                String str,
                String str2,
                int i,
                byte[] bArr,
                int i2,
                int i3) {
            if (str == null) {
                return;
            }
            ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("FILENAME", str2);
            Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                    i, m, "OFFSET", i2, "SIZE");
            m.put("FORVRAMDISK", Integer.valueOf(i3));
            m.put("BITMAP", bArr);
            sQLiteDatabase.insertWithOnConflict(str, null, m, 4);
        }

        private final void onCreate$com$android$server$am$Pageboost$PageboostFileDBHelper(
                SQLiteDatabase sQLiteDatabase) {}

        public static ContentValues setContentValue(PageboostAppInfo pageboostAppInfo, int i) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID", Integer.valueOf(i));
            contentValues.put("APPNAME", pageboostAppInfo.mName);
            contentValues.put("CAPTURED", Integer.valueOf(pageboostAppInfo.mCaptured));
            contentValues.put("ANON", Integer.valueOf(pageboostAppInfo.mAnon));
            contentValues.put("ION", Integer.valueOf(pageboostAppInfo.mION));
            contentValues.put("GPU", Integer.valueOf(pageboostAppInfo.mGPU));
            contentValues.put("SIZE_PR", Long.valueOf(pageboostAppInfo.mSizeForPrefetch));
            contentValues.put(
                    "SIZE_MAP_PR", Long.valueOf(pageboostAppInfo.mMapCaptureSizeForPrefetch));
            contentValues.put("SCORE", Integer.valueOf(pageboostAppInfo.mScore));
            contentValues.put("EXEC_CNT", Integer.valueOf(pageboostAppInfo.mExecCnt));
            contentValues.put("ACC_ETIME", Long.valueOf(pageboostAppInfo.mAccExecTime));
            contentValues.put("PREFETCH_CNT", Integer.valueOf(pageboostAppInfo.mPrefetchCnt));
            contentValues.put("HIT_CNT", Integer.valueOf(pageboostAppInfo.mHitCnt));
            contentValues.put("APK_PATH", pageboostAppInfo.mApkPath);
            return contentValues;
        }

        public static void storeApp(
                SQLiteDatabase sQLiteDatabase,
                PageboostAppList pageboostAppList,
                PageboostAppInfo pageboostAppInfo) {
            if (pageboostAppList == null || pageboostAppInfo == null) {
                return;
            }
            synchronized (pageboostAppList.mPageboostApps) {
                try {
                    ContentValues contentValue = setContentValue(pageboostAppInfo, 0);
                    long update =
                            sQLiteDatabase.update(
                                    "AppLRU",
                                    contentValue,
                                    "APPNAME = ?",
                                    new String[] {pageboostAppInfo.mName});
                    if (update == 0) {
                        update =
                                sQLiteDatabase.insertWithOnConflict(
                                        "AppLRU", null, contentValue, 4);
                    }
                    boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                    Slog.i("Pageboost", "db update :" + pageboostAppInfo.mName + " ret " + update);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public static void storeAppLRU(
                SQLiteDatabase sQLiteDatabase, PageboostAppList pageboostAppList) {
            try {
                long delete = sQLiteDatabase.delete("AppLRU", "1", null);
                boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                Slog.i("Pageboost", "db clear : ret " + delete);
                if (pageboostAppList == null) {
                    return;
                }
                synchronized (pageboostAppList.mPageboostApps) {
                    try {
                        Iterator it = pageboostAppList.mPageboostApps.iterator();
                        int i = 0;
                        while (it.hasNext()) {
                            PageboostAppInfo pageboostAppInfo = (PageboostAppInfo) it.next();
                            int i2 = i + 1;
                            long replace =
                                    sQLiteDatabase.replace(
                                            "AppLRU", null, setContentValue(pageboostAppInfo, i));
                            boolean z2 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                            Slog.i(
                                    "Pageboost",
                                    "db insert :" + pageboostAppInfo.mName + " ret " + replace);
                            i = i2;
                        }
                    } finally {
                    }
                }
            } catch (Exception unused) {
                boolean z3 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                Slog.e("Pageboost", "failed to store app lru info");
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onCreate(SQLiteDatabase sQLiteDatabase) {
            switch (this.$r8$classId) {
                case 0:
                    try {
                        sQLiteDatabase.execSQL(
                                "CREATE TABLE IF NOT EXISTS AppLRU (ID INTEGER, APPNAME TEXT NOT"
                                    + " NULL UNIQUE, CAPTURED INTEGER, ANON INTEGER, ION INTEGER,"
                                    + " GPU INTEGER, SIZE_PR INTEGER, SIZE_MAP_PR INTEGER, SCORE"
                                    + " INTEGER, EXEC_CNT INTEGER, ACC_ETIME INTEGER, PREFETCH_CNT"
                                    + " INTEGER, HIT_CNT INTEGER, APK_PATH TEXT NOT NULL );");
                        boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                        Slog.i("Pageboost", "AppDB table creation done");
                        break;
                    } catch (Exception e) {
                        boolean z2 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                        Slog.e("Pageboost", "failed to create AppDB table");
                        e.printStackTrace();
                        return;
                    }
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            switch (this.$r8$classId) {
                case 0:
                    onUpgrade(sQLiteDatabase, i, i2);
                    break;
                default:
                    onUpgrade(sQLiteDatabase, i, i2);
                    break;
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            switch (this.$r8$classId) {
                case 0:
                    try {
                        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS AppLRU");
                        onCreate(sQLiteDatabase);
                        break;
                    } catch (Exception e) {
                        boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                        Slog.e(
                                "Pageboost",
                                "failed to upgrade or downgrade AppDB table : from "
                                        + i
                                        + " to "
                                        + i2);
                        e.printStackTrace();
                        return;
                    }
                default:
                    Cursor rawQuery =
                            sQLiteDatabase.rawQuery(
                                    "SELECT name FROM sqlite_master WHERE type='table'", null);
                    ArrayList arrayList = new ArrayList();
                    if (rawQuery != null) {
                        while (rawQuery.moveToNext()) {
                            arrayList.add(rawQuery.getString(0));
                        }
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            dropTable(sQLiteDatabase, (String) it.next());
                        }
                        rawQuery.close();
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PageboostAppInfo {
        public long mAccExecTime;
        public int mAnon;
        public String mApkPath;
        public int mCaptured;
        public int mExecCnt;
        public int mGPU;
        public int mHitCnt;
        public int mION;
        public int mInstalled;
        public final boolean mIsGameApp;
        public long mMapCaptureSizeForPrefetch;
        public final String mName;
        public final String mNameTrimmed;
        public int mPid;
        public final int mPrefetchCnt;
        public int mProcStatus;
        public int mProcStatusPid;
        public final int mScore;
        public long mSizeForPrefetch;

        public PageboostAppInfo(String str) {
            this(str, 0, 0, 0, 0, 0L, 0L, 0, 0, 0L, 0, 0, "");
            boolean z = false;
            this.mPid = 0;
            this.mProcStatusPid = 0;
            this.mProcStatus = 0;
            this.mScore = 0;
            this.mExecCnt = 0;
            this.mAccExecTime = 0L;
            this.mPrefetchCnt = 0;
            this.mHitCnt = 0;
            this.mInstalled = 0;
            this.mApkPath = "";
            if (SemGameManager.isAvailable()) {
                try {
                    if (SemGameManager.isGamePackage(str)) {
                        z = true;
                    }
                } catch (Exception unused) {
                }
            }
            this.mIsGameApp = z;
        }

        public PageboostAppInfo(
                String str,
                int i,
                int i2,
                int i3,
                int i4,
                long j,
                long j2,
                int i5,
                int i6,
                long j3,
                int i7,
                int i8,
                String str2) {
            boolean z = false;
            this.mPid = 0;
            this.mProcStatusPid = 0;
            this.mProcStatus = 0;
            this.mInstalled = 0;
            this.mName = str;
            this.mNameTrimmed = str.replace(".", "");
            this.mCaptured = i;
            this.mAnon = i2;
            this.mION = i3;
            this.mGPU = i4;
            this.mSizeForPrefetch = j;
            this.mMapCaptureSizeForPrefetch = j2;
            this.mScore = i5;
            this.mExecCnt = i6;
            this.mAccExecTime = j3;
            this.mPrefetchCnt = i7;
            this.mHitCnt = i8;
            this.mApkPath = str2;
            if (SemGameManager.isAvailable()) {
                try {
                    if (SemGameManager.isGamePackage(str)) {
                        z = true;
                    }
                } catch (Exception unused) {
                }
            }
            this.mIsGameApp = z;
            boolean z2 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i5, "appinfo : ", str, " ", " ");
            ServiceKeeper$$ExternalSyntheticOutline0.m(i6, i7, " ", " ", m);
            SystemServiceManager$$ExternalSyntheticOutline0.m(m, i8, "Pageboost");
        }

        public final boolean activeLaunch() {
            String str = this.mNameTrimmed;
            if (str.length() >= 256) {
                boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "Abort alp due to long package name : ", str, "Pageboost");
                return false;
            }
            int i = this.mProcStatus;
            String str2 = this.mName;
            if (i == 2) {
                boolean z2 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                SystemServiceManager$$ExternalSyntheticOutline0.m(
                        DumpUtils$$ExternalSyntheticOutline0.m("alp skip : ", str2, " , "),
                        this.mProcStatus,
                        "Pageboost");
                return false;
            }
            Vramdisk.executeCmd(4, str);
            boolean z3 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
            SystemServiceManager$$ExternalSyntheticOutline0.m(
                    DumpUtils$$ExternalSyntheticOutline0.m("alp for : ", str2, " , "),
                    this.mProcStatus,
                    "Pageboost");
            return true;
        }

        public final boolean execute() {
            String str = this.mNameTrimmed;
            if (str.length() >= 256) {
                boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "Abort IoPrefetch due to long package name : ", str, "Pageboost");
                return false;
            }
            if (this.mCaptured <= 0) {
                return false;
            }
            Vramdisk.executeCmd(1, str);
            boolean z2 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    new StringBuilder("IO Prefetch for : "), this.mName, "Pageboost");
            return true;
        }

        /* JADX WARN: Code restructure failed: missing block: B:35:0x007f, code lost:

           if (r10.mName.equals(r2.split("[^\\p{Alnum}\\.]+", 2)[0]) != false) goto L67;
        */
        /* JADX WARN: Removed duplicated region for block: B:28:0x0053  */
        /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void getIoinfo(long[] r11) {
            /*
                r10 = this;
                r0 = 1
                java.lang.String r1 = "Pageboost"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r3 = "/proc/"
                r2.<init>(r3)
                int r4 = r10.mPid
                java.lang.String r5 = "/ioinfo"
                java.lang.String r2 = android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0.m(r4, r2, r5)
                r4 = 0
                java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch: java.lang.Exception -> L47
                java.io.FileReader r6 = new java.io.FileReader     // Catch: java.lang.Exception -> L47
                r6.<init>(r2)     // Catch: java.lang.Exception -> L47
                r5.<init>(r6)     // Catch: java.lang.Exception -> L47
                r2 = r4
            L1e:
                java.lang.String r6 = r5.readLine()     // Catch: java.lang.Throwable -> L39
                if (r6 == 0) goto L35
                int r7 = r2 + 1
                long r8 = java.lang.Long.parseLong(r6)     // Catch: java.lang.Throwable -> L33
                r11[r2] = r8     // Catch: java.lang.Throwable -> L33
                int r2 = r11.length     // Catch: java.lang.Throwable -> L33
                if (r7 != r2) goto L31
                r2 = r7
                goto L35
            L31:
                r2 = r7
                goto L1e
            L33:
                r2 = move-exception
                goto L3c
            L35:
                r5.close()     // Catch: java.lang.Exception -> L48
                goto L50
            L39:
                r6 = move-exception
                r7 = r2
                r2 = r6
            L3c:
                r5.close()     // Catch: java.lang.Throwable -> L40
                goto L44
            L40:
                r5 = move-exception
                r2.addSuppressed(r5)     // Catch: java.lang.Exception -> L45
            L44:
                throw r2     // Catch: java.lang.Exception -> L45
            L45:
                r2 = r7
                goto L48
            L47:
                r2 = r4
            L48:
                boolean r5 = com.android.server.am.Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED
                java.lang.String r5 = "ioinfo read failed"
                android.util.Slog.e(r1, r5)
            L50:
                int r5 = r11.length
                if (r2 != r5) goto L9d
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>(r3)
                int r3 = r10.mPid
                java.lang.String r5 = "/cmdline"
                java.lang.String r2 = android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0.m(r3, r2, r5)
                java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Exception -> L92
                java.io.FileReader r5 = new java.io.FileReader     // Catch: java.lang.Exception -> L92
                r5.<init>(r2)     // Catch: java.lang.Exception -> L92
                r3.<init>(r5)     // Catch: java.lang.Exception -> L92
                java.lang.String r2 = r3.readLine()     // Catch: java.lang.Throwable -> L82
                if (r2 == 0) goto L84
                java.lang.String r5 = "[^\\p{Alnum}\\.]+"
                r6 = 2
                java.lang.String[] r2 = r2.split(r5, r6)     // Catch: java.lang.Throwable -> L82
                java.lang.String r10 = r10.mName     // Catch: java.lang.Throwable -> L82
                r2 = r2[r4]     // Catch: java.lang.Throwable -> L82
                boolean r10 = r10.equals(r2)     // Catch: java.lang.Throwable -> L82
                if (r10 == 0) goto L84
                goto L85
            L82:
                r10 = move-exception
                goto L89
            L84:
                r0 = r4
            L85:
                r3.close()     // Catch: java.lang.Exception -> L93
                goto L9b
            L89:
                r3.close()     // Catch: java.lang.Throwable -> L8d
                goto L91
            L8d:
                r0 = move-exception
                r10.addSuppressed(r0)     // Catch: java.lang.Exception -> L92
            L91:
                throw r10     // Catch: java.lang.Exception -> L92
            L92:
                r0 = r4
            L93:
                boolean r10 = com.android.server.am.Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED
                java.lang.String r10 = "cmdline read failed"
                android.util.Slog.e(r1, r10)
            L9b:
                if (r0 != 0) goto La1
            L9d:
                r0 = -1
                r11[r4] = r0
            La1:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.am.Pageboost.PageboostAppInfo.getIoinfo(long[]):void");
        }

        public final void initAalPrefetchList() {
            LinkedList dalvikcacheFileList;
            File[] listFiles;
            if (Pageboost.PAGEBOOST_ACTIVE_LAUNCH_ENABLED
                    && PageboostAppDBHelper.createTable(Pageboost.mFileDB, this.mNameTrimmed)
                            != null) {
                try {
                    String str = this.mApkPath;
                    File parentFile = new File(str).getParentFile();
                    if (parentFile == null) {
                        Slog.e("Pageboost", "apkpath seems not correct : " + str + ", ");
                        return;
                    }
                    String path = parentFile.getPath();
                    boolean z = false;
                    if (path != null) {
                        String[] strArr = {"/oat/arm64/", "/oat/arm/"};
                        boolean z2 = false;
                        for (int i = 0; i < 2; i++) {
                            File file = new File(path + strArr[i]);
                            if (file.exists() && (listFiles = file.listFiles()) != null) {
                                for (File file2 : listFiles) {
                                    if (file2.isFile()) {
                                        PageboostAppDBHelper.insertTable(
                                                Pageboost.mFileDB,
                                                this.mNameTrimmed,
                                                file2.getPath(),
                                                -1,
                                                null,
                                                0,
                                                2);
                                    }
                                }
                                z2 = true;
                            }
                        }
                        z = z2;
                    }
                    if (z) {
                        return;
                    }
                    if ((str.startsWith("/system/") || str.startsWith("/product/"))
                            && (dalvikcacheFileList = Vramdisk.getDalvikcacheFileList(str))
                                    != null) {
                        Iterator it = dalvikcacheFileList.iterator();
                        while (it.hasNext()) {
                            PageboostAppDBHelper.insertTable(
                                    Pageboost.mFileDB,
                                    this.mNameTrimmed,
                                    ((File) it.next()).getPath(),
                                    -1,
                                    null,
                                    0,
                                    2);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public final boolean isApkPathCorrect() {
            String str = this.mApkPath;
            if (str == null) {
                return false;
            }
            return str.startsWith("/data/")
                    || this.mApkPath.startsWith("/system/")
                    || this.mApkPath.startsWith("/product/");
        }

        public final void setApkPath(String str) {
            if (str == null) {
                return;
            }
            boolean equals = str.equals(this.mApkPath);
            String str2 = this.mName;
            if (equals) {
                boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "Reuse the dbinfo for this app info : ", str2, " "),
                        this.mApkPath,
                        "Pageboost");
                return;
            }
            boolean z2 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    DumpUtils$$ExternalSyntheticOutline0.m("Reset this app info : ", str2, " "),
                    this.mApkPath,
                    "Pageboost");
            this.mApkPath = str;
            if (!isApkPathCorrect()) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "app not in internal storage : ", str2, " "),
                        this.mApkPath,
                        "Pageboost");
            } else {
                PageboostAppDBHelper.dropTable(Pageboost.mFileDB, this.mNameTrimmed);
                initAalPrefetchList();
            }
        }

        public final String toString() {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(" " + this.mPid, " ");
            m.append(this.mProcStatusPid);
            StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(m.toString(), " ");
            m2.append(this.mProcStatus);
            StringBuilder m3 = Preconditions$$ExternalSyntheticOutline0.m(m2.toString(), " ");
            m3.append(this.mName);
            StringBuilder m4 = Preconditions$$ExternalSyntheticOutline0.m(m3.toString(), " ");
            m4.append(this.mCaptured);
            StringBuilder m5 = Preconditions$$ExternalSyntheticOutline0.m(m4.toString(), " ");
            m5.append(this.mAnon);
            StringBuilder m6 = Preconditions$$ExternalSyntheticOutline0.m(m5.toString(), " ");
            m6.append(this.mION);
            StringBuilder m7 = Preconditions$$ExternalSyntheticOutline0.m(m6.toString(), " ");
            m7.append(this.mGPU);
            StringBuilder m8 = Preconditions$$ExternalSyntheticOutline0.m(m7.toString(), " ");
            m8.append(this.mSizeForPrefetch);
            StringBuilder m9 = Preconditions$$ExternalSyntheticOutline0.m(m8.toString(), " ");
            m9.append(this.mMapCaptureSizeForPrefetch);
            StringBuilder m10 =
                    Preconditions$$ExternalSyntheticOutline0.m(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                            m9.toString(), " 0"),
                                    " 0"),
                            " ");
            m10.append(this.mScore);
            StringBuilder m11 = Preconditions$$ExternalSyntheticOutline0.m(m10.toString(), " ");
            m11.append(this.mExecCnt);
            StringBuilder m12 = Preconditions$$ExternalSyntheticOutline0.m(m11.toString(), " ");
            m12.append(this.mAccExecTime);
            StringBuilder m13 = Preconditions$$ExternalSyntheticOutline0.m(m12.toString(), " ");
            int i = this.mExecCnt;
            m13.append(i != 0 ? this.mAccExecTime / i : 0L);
            StringBuilder m14 = Preconditions$$ExternalSyntheticOutline0.m(m13.toString(), " ");
            m14.append(this.mPrefetchCnt);
            StringBuilder m15 = Preconditions$$ExternalSyntheticOutline0.m(m14.toString(), " ");
            m15.append(this.mHitCnt);
            StringBuilder m16 = Preconditions$$ExternalSyntheticOutline0.m(m15.toString(), " ");
            m16.append(this.mInstalled);
            StringBuilder m17 = Preconditions$$ExternalSyntheticOutline0.m(m16.toString(), " ");
            m17.append(this.mIsGameApp);
            StringBuilder m18 = Preconditions$$ExternalSyntheticOutline0.m(m17.toString(), " ");
            m18.append(this.mApkPath);
            return m18.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PageboostAppList implements Iterable {
        public final LinkedList mPageboostApps = new LinkedList();

        public final void add(PageboostAppInfo pageboostAppInfo, boolean z) {
            synchronized (this.mPageboostApps) {
                try {
                    if (z) {
                        this.mPageboostApps.addFirst(pageboostAppInfo);
                    } else {
                        this.mPageboostApps.add(pageboostAppInfo);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final PageboostAppInfo getPageboostApp(String str) {
            PageboostAppInfo pageboostAppInfo = null;
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            synchronized (this.mPageboostApps) {
                try {
                    Iterator it = this.mPageboostApps.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        PageboostAppInfo pageboostAppInfo2 = (PageboostAppInfo) it.next();
                        if (str.equals(pageboostAppInfo2.mName)) {
                            pageboostAppInfo = pageboostAppInfo2;
                            break;
                        }
                    }
                } finally {
                }
            }
            return pageboostAppInfo;
        }

        @Override // java.lang.Iterable
        public final Iterator iterator() {
            return this.mPageboostApps.iterator();
        }

        public final String toString() {
            String str = "";
            synchronized (this.mPageboostApps) {
                try {
                    Iterator it = this.mPageboostApps.iterator();
                    while (it.hasNext()) {
                        str = str + ((PageboostAppInfo) it.next()).toString() + "\n";
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return str;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PageboostHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String str;
            int m203$$Nest$smgetPidFromPackageName;
            boolean remove;
            int m203$$Nest$smgetPidFromPackageName2;
            int m203$$Nest$smgetPidFromPackageName3;
            PageboostAppInfo pageboostApp;
            try {
                PageboostAppInfo pageboostAppInfo = null;
                boolean z = false;
                z = false;
                z = false;
                z = false;
                switch (message.what) {
                    case 1:
                        Pageboost.m202$$Nest$smdelayedInitPageboost();
                        return;
                    case 2:
                        if (Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED) {
                            PageboostPredictor.haltPredict();
                            Pageboost.sendMessageWithObject(3, 400, (PageboostAppList) message.obj);
                            return;
                        }
                        return;
                    case 3:
                        if (Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED
                                && !Pageboost.PAGEBOOST_VRAMDISK_WITHOUT_PREDICT) {
                            PageboostAppList pageboostAppList = (PageboostAppList) message.obj;
                            Slog.i("Pageboost", "Launcher Page Up");
                            if (Pageboost.mPredictor != null) {
                                PageboostPredictor.predict(pageboostAppList);
                                return;
                            }
                            return;
                        }
                        return;
                    case 4:
                        Pageboost.m204$$Nest$smrealupdatePackages();
                        return;
                    case 5:
                        if (Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED
                                && Pageboost.mGlobalAppLRU != null
                                && (m203$$Nest$smgetPidFromPackageName =
                                                Pageboost.m203$$Nest$smgetPidFromPackageName(
                                                        (str = (String) message.obj), true))
                                        > 0) {
                            PageboostAppInfo pageboostApp2 =
                                    Pageboost.mGlobalAppLRU.getPageboostApp(str);
                            boolean z2 = PageboostAppCapture.record_ongoing;
                            if (pageboostApp2 == null
                                    || pageboostApp2.mProcStatus == 2
                                    || !pageboostApp2.isApkPathCorrect()
                                    || pageboostApp2.mCaptured
                                            >= PageboostAppCapture.PAGEBOOST_IO_PREFETCH_LEVEL) {
                                return;
                            }
                            Slog.i("Pageboost", "capturing App IO");
                            PageboostAppCapture.capture(
                                    pageboostApp2, m203$$Nest$smgetPidFromPackageName);
                            return;
                        }
                        return;
                    case 6:
                        if (Pageboost.mGlobalAppLRU == null) {
                            return;
                        }
                        PageboostAppDBHelper.storeAppLRU(Pageboost.mAppDB, Pageboost.mGlobalAppLRU);
                        return;
                    case 7:
                        String str2 = (String) message.obj;
                        if (str2 != null) {
                            Pageboost.m201$$Nest$smaddPackage(str2);
                            return;
                        }
                        return;
                    case 8:
                        String str3 = (String) message.obj;
                        if (str3 != null) {
                            Pageboost.m205$$Nest$smremovePackage(str3);
                            return;
                        }
                        return;
                    case 9:
                        PageboostPredictor.haltPredict();
                        Bundle data = message.getData();
                        Message obtainMessage = Pageboost.sHandler.obtainMessage(11);
                        obtainMessage.setData(data);
                        Pageboost.sHandler.sendMessageDelayed(obtainMessage, 3000);
                        return;
                    case 10:
                        PageboostPredictor.haltPredict();
                        Bundle data2 = message.getData();
                        Message obtainMessage2 = Pageboost.sHandler.obtainMessage(12);
                        obtainMessage2.setData(data2);
                        Pageboost.sHandler.sendMessageDelayed(obtainMessage2, 3000);
                        return;
                    case 11:
                    case 12:
                        if (Pageboost.mGlobalAppLRU == null) {
                            return;
                        }
                        Bundle data3 = message.getData();
                        int i = data3.getInt("pid");
                        int i2 = data3.getInt("launchtime");
                        String str4 = "0,0,0";
                        String string = data3.getString("pkg");
                        Slog.i("Pageboost", "Launcher App Execution");
                        if (Pageboost.mPredictor != null) {
                            PageboostAppInfo pageboostApp3 =
                                    Pageboost.mGlobalAppLRU.getPageboostApp(string);
                            if (pageboostApp3 != null) {
                                PageboostAppList pageboostAppList2 = Pageboost.mGlobalAppLRU;
                                synchronized (pageboostAppList2.mPageboostApps) {
                                    remove = pageboostAppList2.mPageboostApps.remove(pageboostApp3);
                                }
                                if (remove) {
                                    if (pageboostApp3.mProcStatus == 0
                                            && i
                                                    == (m203$$Nest$smgetPidFromPackageName2 =
                                                            Pageboost
                                                                    .m203$$Nest$smgetPidFromPackageName(
                                                                            string, false))) {
                                        pageboostApp3.mProcStatusPid =
                                                m203$$Nest$smgetPidFromPackageName2;
                                        pageboostApp3.mProcStatus = 2;
                                    }
                                    if (pageboostApp3.mProcStatus == 1) {
                                        pageboostApp3.mProcStatus = 2;
                                    }
                                    if (i == 0 || i == pageboostApp3.mPid) {
                                        i2 = 0;
                                    } else {
                                        pageboostApp3.mPid = i;
                                        if (i2 > 0 && i2 < 10000) {
                                            pageboostApp3.mExecCnt++;
                                            pageboostApp3.mAccExecTime += i2;
                                        }
                                        long[] jArr = new long[3];
                                        pageboostApp3.getIoinfo(jArr);
                                        if (jArr[0] != -1) {
                                            str4 = "";
                                            for (int i3 = 0; i3 < 3; i3++) {
                                                str4 = str4 + jArr[i3] + ",";
                                            }
                                        }
                                    }
                                    Pageboost.mPredictor.getClass();
                                    if (PageboostPredictor.checkPredictHit(pageboostApp3, i2)) {
                                        pageboostApp3.mHitCnt++;
                                        Slog.i("Pageboost", "Prefetch Hit! : " + string);
                                        z = true;
                                    }
                                    Pageboost.mGlobalAppLRU.add(pageboostApp3, true);
                                }
                            }
                            Pageboost.mPredictor.getClass();
                            LRUPolicy lRUPolicy = PageboostPredictor.mAppSelection;
                            synchronized (lRUPolicy.mLock) {
                                lRUPolicy.mRecentPrefetchList = null;
                            }
                            pageboostAppInfo = pageboostApp3;
                        }
                        if (pageboostAppInfo != null) {
                            if (i2 > 0) {
                                Vramdisk.add(
                                        "AppEntry," + string + "," + i2 + "," + z + "," + str4);
                                return;
                            }
                            if (i2 == 0) {
                                Vramdisk.add("AppReEntry," + string + "," + i2 + "," + z);
                                return;
                            }
                            return;
                        }
                        return;
                    case 13:
                    case 22:
                    default:
                        return;
                    case 14:
                        PageboostAppList pageboostAppList3 = Pageboost.mGlobalAppLRU;
                        if (pageboostAppList3 == null) {
                            return;
                        }
                        String str5 = (String) message.obj;
                        PageboostAppInfo pageboostApp4 = pageboostAppList3.getPageboostApp(str5);
                        boolean z3 = PageboostAppCapture.record_ongoing;
                        if (pageboostApp4 == null
                                || pageboostApp4.mProcStatus == 2
                                || !pageboostApp4.isApkPathCorrect()
                                || pageboostApp4.mCaptured
                                        >= PageboostAppCapture.PAGEBOOST_IO_PREFETCH_LEVEL) {
                            return;
                        }
                        int i4 = pageboostApp4.mProcStatusPid;
                        Slog.i("Pageboost", "Record App IO : " + str5 + " pid " + i4);
                        if (i4 == 0) {
                            for (int i5 = 0; i5 < 10; i5++) {
                                try {
                                    i4 = Pageboost.m203$$Nest$smgetPidFromPackageName(str5, true);
                                    if (i4 <= 0) {
                                        Thread.sleep(50L);
                                    }
                                } catch (Exception e) {
                                    boolean z4 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                                    Slog.e("Pageboost", "Failed to sleep");
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (i4 > 0) {
                            PageboostAppCapture.capture(pageboostApp4, i4);
                            return;
                        }
                        return;
                    case 15:
                        IoRecord ioRecord = (IoRecord) message.obj;
                        ioRecord.getResultFromKernel();
                        PageboostAppCapture.captureFinished(ioRecord);
                        PageboostAppInfo pageboostAppInfo2 = ioRecord.mApp;
                        if (pageboostAppInfo2 != null) {
                            PageboostAppDBHelper.storeApp(
                                    Pageboost.mAppDB, Pageboost.mGlobalAppLRU, pageboostAppInfo2);
                            return;
                        }
                        return;
                    case 16:
                        if (Pageboost.mGlobalAppLRU == null) {
                            return;
                        }
                        Bundle data4 = message.getData();
                        int i6 = data4.getInt(Constants.JSON_CLIENT_DATA_STATUS);
                        int i7 = data4.getInt("pid");
                        String string2 = data4.getString("pkg");
                        PageboostAppInfo pageboostApp5 =
                                Pageboost.mGlobalAppLRU.getPageboostApp(string2);
                        if (pageboostApp5 != null) {
                            if (i6 != 1) {
                                if (i6 == 2 && pageboostApp5.mProcStatusPid == i7) {
                                    pageboostApp5.mProcStatusPid = 0;
                                    pageboostApp5.mProcStatus = 0;
                                    return;
                                }
                                return;
                            }
                            if (pageboostApp5.mProcStatusPid != 0
                                    && (m203$$Nest$smgetPidFromPackageName3 =
                                                    Pageboost.m203$$Nest$smgetPidFromPackageName(
                                                            string2, false))
                                            != 0) {
                                i7 = m203$$Nest$smgetPidFromPackageName3;
                            }
                            if (pageboostApp5.mProcStatusPid != i7) {
                                pageboostApp5.mProcStatusPid = i7;
                                pageboostApp5.mProcStatus = 1;
                                return;
                            }
                            return;
                        }
                        return;
                    case 17:
                        PageboostAppList pageboostAppList4 = Pageboost.mGlobalAppLRU;
                        if (pageboostAppList4 == null
                                || (pageboostApp =
                                                pageboostAppList4.getPageboostApp(
                                                        (String) message.obj))
                                        == null) {
                            return;
                        }
                        if (pageboostApp.mAnon + pageboostApp.mION + pageboostApp.mGPU == 0) {
                            if (pageboostApp.mIsGameApp) {
                                Pageboost.sendMessageWithObject(18, 35000, pageboostApp);
                                return;
                            } else {
                                Pageboost.sendMessageWithObject(18, 5000, pageboostApp);
                                return;
                            }
                        }
                        return;
                    case 18:
                        Vramdisk.collectMemUsage((PageboostAppInfo) message.obj);
                        return;
                    case 19:
                        if (Pageboost.mGlobalAppLRU == null) {
                            return;
                        }
                        Pageboost.prefetchPackage(message.getData().getString("pkg"), "alp");
                        return;
                    case 20:
                        PageboostPredictor.haltPredict();
                        return;
                    case 21:
                        boolean z5 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                        Slog.i("Pageboost", "stop alp by timeout");
                        PageboostPredictor.haltPredict();
                        return;
                    case 23:
                        LinkedList string3 = Vramdisk.getString();
                        LRUPolicy lRUPolicy2 = new LRUPolicy();
                        lRUPolicy2.mRecentPrefetchList = null;
                        lRUPolicy2.mLock = new ArrayList();
                        lRUPolicy2.mRecentPrefetchList = string3;
                        Pageboost.mBootFileManager = lRUPolicy2;
                        lRUPolicy2.mlockAllFiles();
                        return;
                    case 24:
                        LRUPolicy lRUPolicy3 = Pageboost.mBootFileManager;
                        if (lRUPolicy3 != null) {
                            Iterator it = ((ArrayList) lRUPolicy3.mLock).iterator();
                            while (it.hasNext()) {
                                ((PinnerService.PinnedFile) it.next()).close();
                            }
                            ((ArrayList) lRUPolicy3.mLock).clear();
                            boolean z6 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                            Slog.i("Pageboost", "munlockAllFiles() done");
                        }
                        Pageboost.mBootFileManager = null;
                        return;
                }
            } catch (Exception unused) {
                boolean z7 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
                VaultKeeperService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("failed to handleMessage "), message.what, "Pageboost");
            }
            boolean z72 = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
            VaultKeeperService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("failed to handleMessage "), message.what, "Pageboost");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PageboostPredictor {
        public static LRUPolicy mAppSelection;

        public static boolean checkPredictHit(PageboostAppInfo pageboostAppInfo, int i) {
            boolean z = false;
            if (i <= 0) {
                return false;
            }
            LRUPolicy lRUPolicy = mAppSelection;
            synchronized (lRUPolicy.mLock) {
                try {
                    PageboostAppList pageboostAppList =
                            (PageboostAppList) lRUPolicy.mRecentPrefetchList;
                    if (pageboostAppList != null) {
                        synchronized (pageboostAppList.mPageboostApps) {
                            try {
                                Iterator it =
                                        ((PageboostAppList) lRUPolicy.mRecentPrefetchList)
                                                .mPageboostApps.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    if (((PageboostAppInfo) it.next()) == pageboostAppInfo) {
                                        z = true;
                                        break;
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                    lRUPolicy.mRecentPrefetchList = null;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return z;
        }

        public static void haltPredict() {
            if (Pageboost.PAGEBOOST_ACTIVE_LAUNCH_ENABLED) {
                Pageboost.sHandler.removeMessages(21);
            }
            Pageboost.sHandler.removeMessages(3);
            Vramdisk.executeCmd(2, null);
        }

        public static void predict(PageboostAppList pageboostAppList) {
            PageboostAppInfo pageboostAppInfo;
            PageboostAppList appFilter = mAppSelection.appFilter(pageboostAppList);
            String str = "";
            synchronized (appFilter.mPageboostApps) {
                try {
                    Iterator it = appFilter.mPageboostApps.iterator();
                    pageboostAppInfo = null;
                    int i = 0;
                    while (it.hasNext()) {
                        PageboostAppInfo pageboostAppInfo2 = (PageboostAppInfo) it.next();
                        if (pageboostAppInfo2.execute()) {
                            str = str + pageboostAppInfo2.mName + ",";
                        }
                        int i2 =
                                pageboostAppInfo2.mAnon
                                        + pageboostAppInfo2.mION
                                        + pageboostAppInfo2.mGPU;
                        if (i2 >= i) {
                            pageboostAppInfo = pageboostAppInfo2;
                            i = i2;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Prefetch,", str, ",MemPrep,");
            m.append(pageboostAppInfo != null ? pageboostAppInfo.mName : "n/a");
            Vramdisk.add(m.toString());
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Vramdisk {
        public static boolean ENABLED;
        public static DaemonConnector conn;
        public static int cur_idx;
        public static final Object sLock = new Object();
        public static final String[] array = new String[2000];
        public static final Object sLock$1 = new Object();

        public static void add(String str) {
            synchronized (sLock$1) {
                String str2 =
                        new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(new Date()) + " " + str;
                String[] strArr = array;
                int i = cur_idx;
                cur_idx = i + 1;
                strArr[i % 2000] = str2;
            }
        }

        public static void collectMemUsage(PageboostAppInfo pageboostAppInfo) {
            int i = pageboostAppInfo.mProcStatusPid;
            if (i <= 0) {
                return;
            }
            Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(i, memoryInfo);
            int totalSwappedOutPss =
                    memoryInfo.getTotalSwappedOutPss() + memoryInfo.getTotalPrivateDirty();
            if (totalSwappedOutPss <= 0) {
                return;
            }
            int otherPrivate = memoryInfo.getOtherPrivate(14);
            int otherPrivate2 = memoryInfo.getOtherPrivate(15);
            pageboostAppInfo.mAnon = totalSwappedOutPss;
            pageboostAppInfo.mION = otherPrivate;
            pageboostAppInfo.mGPU = otherPrivate2;
            boolean z = Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED;
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            totalSwappedOutPss, otherPrivate, "memUsage collected : ", " ", " ");
            m.append(otherPrivate2);
            m.append(" for ");
            m.append(pageboostAppInfo.mName);
            m.append(" ");
            m.append(i);
            Slog.i("Pageboost", m.toString());
        }

        public static void executeCmd(int i, String str) {
            if (conn == null) {
                return;
            }
            if (i == 1) {
                byte[] bytes = str.getBytes();
                ByteBuffer allocate = ByteBuffer.allocate(bytes.length + 8);
                allocate.putInt(1);
                allocate.putInt(bytes.length);
                allocate.put(bytes);
                conn.writeDaemon(allocate);
                return;
            }
            if (i == 2) {
                ByteBuffer allocate2 = ByteBuffer.allocate(4);
                allocate2.putInt(2);
                conn.writeDaemon(allocate2);
            } else {
                if (i == 3) {
                    ByteBuffer allocate3 = ByteBuffer.allocate(8);
                    allocate3.putInt(3);
                    allocate3.putInt(Integer.parseInt(str));
                    conn.writeDaemon(allocate3);
                    return;
                }
                if (i == 4) {
                    byte[] bytes2 = str.getBytes();
                    ByteBuffer allocate4 = ByteBuffer.allocate(bytes2.length + 8);
                    allocate4.putInt(4);
                    allocate4.putInt(bytes2.length);
                    allocate4.put(bytes2);
                    conn.writeDaemon(allocate4);
                }
            }
        }

        public static LinkedList getDalvikcacheFileList(String str) {
            try {
                LinkedList linkedList = new LinkedList();
                if (!str.startsWith("/system/") && !str.startsWith("/product/")) {
                    return linkedList;
                }
                String[] strArr = {"/data/dalvik-cache/arm/", "/data/dalvik-cache/arm64/"};
                String[] strArr2 = {"art", "dex", "vdex"};
                String str2 = str.substring(1).replace('/', '@') + "@classes.";
                LinkedList linkedList2 = new LinkedList();
                for (int i = 0; i < 2; i++) {
                    for (int i2 = 0; i2 < 3; i2++) {
                        File file = new File(strArr[i] + str2 + strArr2[i2]);
                        if (file.exists()) {
                            linkedList2.add(file);
                        }
                    }
                }
                return linkedList2;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0068 A[ORIG_RETURN, RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static java.util.LinkedList getString() {
            /*
                java.lang.String r0 = "boot_file"
                java.util.LinkedList r1 = new java.util.LinkedList
                r1.<init>()
                r2 = 0
                java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Exception -> L5a
                java.lang.String r4 = "/vendor/etc/vramdiskd.xml"
                r3.<init>(r4)     // Catch: java.lang.Exception -> L5a
                javax.xml.parsers.DocumentBuilderFactory r4 = javax.xml.parsers.DocumentBuilderFactory.newInstance()     // Catch: java.lang.Throwable -> L50
                javax.xml.parsers.DocumentBuilder r4 = r4.newDocumentBuilder()     // Catch: java.lang.Throwable -> L50
                org.w3c.dom.Document r4 = r4.parse(r3)     // Catch: java.lang.Throwable -> L50
                org.w3c.dom.Element r5 = r4.getDocumentElement()     // Catch: java.lang.Throwable -> L50
                r5.normalize()     // Catch: java.lang.Throwable -> L50
                org.w3c.dom.NodeList r0 = r4.getElementsByTagName(r0)     // Catch: java.lang.Throwable -> L50
                r4 = r2
                r5 = r4
            L29:
                int r6 = r0.getLength()     // Catch: java.lang.Throwable -> L47
                if (r4 >= r6) goto L4a
                org.w3c.dom.Node r6 = r0.item(r4)     // Catch: java.lang.Throwable -> L47
                org.w3c.dom.NodeList r6 = r6.getChildNodes()     // Catch: java.lang.Throwable -> L47
                org.w3c.dom.Node r6 = r6.item(r2)     // Catch: java.lang.Throwable -> L47
                java.lang.String r6 = r6.getNodeValue()     // Catch: java.lang.Throwable -> L47
                r1.add(r6)     // Catch: java.lang.Throwable -> L47
                int r5 = r5 + 1
                int r4 = r4 + 1
                goto L29
            L47:
                r0 = move-exception
                r2 = r5
                goto L51
            L4a:
                r3.close()     // Catch: java.lang.Exception -> L4e
                goto L65
            L4e:
                r2 = r5
                goto L5a
            L50:
                r0 = move-exception
            L51:
                r3.close()     // Catch: java.lang.Throwable -> L55
                goto L59
            L55:
                r3 = move-exception
                r0.addSuppressed(r3)     // Catch: java.lang.Exception -> L5a
            L59:
                throw r0     // Catch: java.lang.Exception -> L5a
            L5a:
                boolean r0 = com.android.server.am.Pageboost.PAGEBOOST_IO_PREFETCH_ENABLED
                java.lang.String r0 = "failed to getString of /vendor/etc/vramdiskd.xml"
                java.lang.String r3 = "Pageboost"
                android.util.Slog.e(r3, r0)
                r5 = r2
            L65:
                if (r5 <= 0) goto L68
                goto L69
            L68:
                r1 = 0
            L69:
                return r1
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.am.Pageboost.Vramdisk.getString():java.util.LinkedList");
        }

        public static void print(PrintWriter printWriter) {
            synchronized (sLock$1) {
                for (int i = 0; i < 2000; i++) {
                    try {
                        String[] strArr = array;
                        if (strArr[i] != null) {
                            printWriter.println("[" + i + "] " + strArr[i]);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$smaddPackage, reason: not valid java name */
    public static void m201$$Nest$smaddPackage(String str) {
        String str2;
        if (mGlobalAppLRU == null) {
            return;
        }
        PageboostAppInfo pageboostAppInfo = new PageboostAppInfo(str);
        pageboostAppInfo.mInstalled = 1;
        try {
            Context context = mContext;
            if (context != null
                    && (str2 = context.getPackageManager().getApplicationInfo(str, 0).sourceDir)
                            != null) {
                pageboostAppInfo.setApkPath(str2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mGlobalAppLRU.add(pageboostAppInfo, false);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("add app to global @ runtime: "),
                pageboostAppInfo.mName,
                "Pageboost");
    }

    /* renamed from: -$$Nest$smdelayedInitPageboost, reason: not valid java name */
    public static void m202$$Nest$smdelayedInitPageboost() {
        boolean z = false;
        try {
            if (new File("/proc/self/io_record_control").exists()) {
                Slog.i("Pageboost", "io_record_control file exists: kernel support = true");
            } else {
                PAGEBOOST_KERNEL_ENABLED = false;
                Slog.i(
                        "Pageboost",
                        "io_record_control file does not exist: kernel support = false");
            }
        } catch (Exception unused) {
            Slog.e("Pageboost", "io_record_control file open failed");
        }
        Slog.i(
                "Pageboost",
                "checkPageboostKernelSupport: " + Boolean.toString(PAGEBOOST_KERNEL_ENABLED));
        PAGEBOOST_DAEMON_ENABLED =
                INetd.IF_FLAG_RUNNING.equals(SystemProperties.get("init.svc.pageboostd", ""));
        Slog.i(
                "Pageboost",
                "checkPageboostDaemonSupport: " + Boolean.toString(PAGEBOOST_DAEMON_ENABLED));
        if (isPageboostMinimized()) {
            Slog.i("Pageboost", "abort delayedInitPageboost");
            return;
        }
        if (PageboostAppCapture.PAGEBOOST_IO_PREFETCH_LEVEL == 3) {
            IoRecord.write("/proc/self/io_record_control", "1");
            Slog.i("Pageboost", "emergency reset during bootup");
        }
        if (mContext == null || mReceiver == null || mPackageReceiver == null) {
            Slog.e("Pageboost", "Pageboost Delayed Init Failed");
            return;
        }
        IntentFilter m =
                VcnManagementService$$ExternalSyntheticOutline0.m(
                        "com.sec.android.launcher.action.RUN_APP",
                        "com.android.server.am.ACTION_PAGEBOOST",
                        "com.android.server.am.ACTION_VRAMDISK_PREFETCH",
                        "com.sec.android.intent.action.HQM_UPDATE_REQ",
                        "android.intent.action.ACTION_SHUTDOWN");
        m.addAction("android.intent.action.REBOOT");
        m.addAction("android.intent.action.USER_UNLOCKED");
        mContext.registerReceiver(
                mReceiver, m, "com.android.server.am.permission.PAGEBOOST", null, 2);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        mContext.registerReceiver(mPackageReceiver, intentFilter);
        PageboostAppDBHelper pageboostAppDBHelper =
                new PageboostAppDBHelper(
                        mContext,
                        "/data/misc/pageboost/pageboost_file_db.db",
                        null,
                        3,
                        new PageboostAppDBHelper.AppDBErrorHandler(1),
                        1);
        mFileDBHelper = pageboostAppDBHelper;
        pageboostAppDBHelper.setWriteAheadLoggingEnabled(true);
        try {
            mFileDB = mFileDBHelper.getWritableDatabase();
            Slog.e("Pageboost", "getWritableDatabase for fileDB");
        } catch (SQLiteDatabaseCorruptException unused2) {
            Slog.e("Pageboost", "SQLiteDatabaseCorruptException for fileDB");
            mFileDB = mFileDBHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            Slog.e("Pageboost", "SQLiteException for fileDB " + e.getMessage());
            if (e.getMessage().contains("malformed database")) {
                Slog.e("Pageboost", "delete fileDB and open");
                deleteDB("/data/misc/pageboost/pageboost_file_db.db");
                mFileDB = mFileDBHelper.getWritableDatabase();
                z = true;
            }
        } catch (Exception e2) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("Exception for fileDB "), "Pageboost");
        }
        SQLiteDatabase sQLiteDatabase = mFileDB;
        if (sQLiteDatabase != null) {
            try {
                Slog.i("Pageboost", "db pragma init");
                sQLiteDatabase.execSQL("PRAGMA synchronous = NORMAL;");
            } catch (Exception e3) {
                Slog.e("Pageboost", "pragma init failed");
                e3.printStackTrace();
            }
        }
        if (z) {
            Slog.e("Pageboost", "delete app db and open");
            deleteDB("/data/misc/pageboost/pageboost_app_db.db");
        }
        PageboostAppDBHelper pageboostAppDBHelper2 =
                new PageboostAppDBHelper(
                        mContext,
                        "/data/misc/pageboost/pageboost_app_db.db",
                        null,
                        7,
                        new PageboostAppDBHelper.AppDBErrorHandler(0),
                        0);
        mAppDBHelper = pageboostAppDBHelper2;
        try {
            mAppDB = pageboostAppDBHelper2.getWritableDatabase();
            Slog.e("Pageboost", "getWritableDatabase for appDB");
        } catch (SQLiteDatabaseCorruptException unused3) {
            Slog.e("Pageboost", "SQLiteDatabaseCorruptException for appDB");
            mAppDB = mAppDBHelper.getWritableDatabase();
        } catch (SQLiteException e4) {
            Slog.e("Pageboost", "SQLiteException for appDB " + e4.getMessage());
            if (e4.getMessage().contains("malformed database")) {
                Slog.e("Pageboost", "delete app db and open");
                deleteDB("/data/misc/pageboost/pageboost_app_db.db");
                mAppDB = mAppDBHelper.getWritableDatabase();
            }
        } catch (Exception e5) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    e5, new StringBuilder("Exception for appDB "), "Pageboost");
        }
        mZygote64FileMap = getFileMapFromCmd("zygote64");
        mZygoteFileMap = getFileMapFromCmd("zygote");
        getFileMapFromCmd("system_server");
        synchronized (Vramdisk.sLock) {
        }
        DaemonConnector daemonConnector = new DaemonConnector();
        daemonConnector.sSocketName = "pageboostd";
        daemonConnector.openSocket();
        Vramdisk.conn = daemonConnector;
    }

    /* renamed from: -$$Nest$smgetPidFromPackageName, reason: not valid java name */
    public static int m203$$Nest$smgetPidFromPackageName(String str, boolean z) {
        SparseArray sparseArray;
        int i;
        ActivityManagerService activityManagerService = mActivityManagerService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                sparseArray =
                        (SparseArray)
                                mActivityManagerService
                                        .mProcessList
                                        .mProcessNames
                                        .getMap()
                                        .get(str);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
            if (sparseArray != null) {
                for (int size = sparseArray.size() - 1; size >= 0; size--) {
                    ProcessRecord processRecord = (ProcessRecord) sparseArray.valueAt(size);
                    if (z) {
                        if (processRecord != null
                                && processRecord.mThread != null
                                && !"cch-empty".equals(processRecord.mState.mAdjType)) {
                            i = processRecord.mPid;
                            break;
                        }
                    } else {
                        if (processRecord != null) {
                            i = processRecord.mPid;
                            break;
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            i = 0;
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        return i;
    }

    /* renamed from: -$$Nest$smrealupdatePackages, reason: not valid java name */
    public static void m204$$Nest$smrealupdatePackages() {
        if (isPageboostMinimized()) {
            return;
        }
        if (mContext == null || mPredictor == null) {
            Slog.e("Pageboost", "mContext or mPredictor is not initialized yet.");
            return;
        }
        if (mGlobalAppLRU != null) {
            Slog.i("Pageboost", "reinit global list");
            mGlobalAppLRU = null;
        }
        PackageManager packageManager = mContext.getPackageManager();
        SQLiteDatabase sQLiteDatabase = mAppDB;
        int i = PageboostAppDBHelper.$r8$clinit;
        PageboostAppList pageboostAppList = new PageboostAppList();
        Cursor query = sQLiteDatabase.query("AppLRU", null, null, null, null, null, "ID ASC");
        if (query != null) {
            while (query.moveToNext()) {
                try {
                    pageboostAppList.add(
                            new PageboostAppInfo(
                                    query.getString(query.getColumnIndexOrThrow("APPNAME")),
                                    query.getInt(query.getColumnIndexOrThrow("CAPTURED")),
                                    query.getInt(query.getColumnIndexOrThrow("ANON")),
                                    query.getInt(query.getColumnIndexOrThrow("ION")),
                                    query.getInt(query.getColumnIndexOrThrow("GPU")),
                                    query.getLong(query.getColumnIndexOrThrow("SIZE_PR")),
                                    query.getLong(query.getColumnIndexOrThrow("SIZE_MAP_PR")),
                                    query.getInt(query.getColumnIndexOrThrow("SCORE")),
                                    query.getInt(query.getColumnIndexOrThrow("EXEC_CNT")),
                                    query.getLong(query.getColumnIndexOrThrow("ACC_ETIME")),
                                    query.getInt(query.getColumnIndexOrThrow("PREFETCH_CNT")),
                                    query.getInt(query.getColumnIndexOrThrow("HIT_CNT")),
                                    query.getString(query.getColumnIndexOrThrow("APK_PATH"))),
                            false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            query.close();
        }
        for (ApplicationInfo applicationInfo : packageManager.getInstalledApplications(128)) {
            if (packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        new StringBuilder("pkg info : "), applicationInfo.packageName, "Pageboost");
                PageboostAppInfo pageboostApp =
                        pageboostAppList.getPageboostApp(applicationInfo.packageName);
                if (pageboostApp == null) {
                    PageboostAppInfo pageboostAppInfo =
                            new PageboostAppInfo(applicationInfo.packageName);
                    pageboostAppList.add(pageboostAppInfo, false);
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            new StringBuilder("add app to global : "),
                            applicationInfo.packageName,
                            "Pageboost");
                    String str = applicationInfo.sourceDir;
                    if (str != null) {
                        pageboostAppInfo.setApkPath(str);
                        pageboostAppInfo.mInstalled = 1;
                    }
                } else {
                    String str2 = applicationInfo.sourceDir;
                    if (str2 != null) {
                        pageboostApp.setApkPath(str2);
                        pageboostApp.mInstalled = 1;
                    }
                }
            }
        }
        synchronized (pageboostAppList.mPageboostApps) {
            try {
                Iterator it = pageboostAppList.mPageboostApps.iterator();
                while (it.hasNext()) {
                    PageboostAppInfo pageboostAppInfo2 = (PageboostAppInfo) it.next();
                    if (pageboostAppInfo2.mInstalled == 0) {
                        it.remove();
                        Slog.i(
                                "Pageboost",
                                "remove not installed app : " + pageboostAppInfo2.mName);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        mGlobalAppLRU = pageboostAppList;
    }

    /* renamed from: -$$Nest$smremovePackage, reason: not valid java name */
    public static void m205$$Nest$smremovePackage(String str) {
        PageboostAppList pageboostAppList = mGlobalAppLRU;
        if (pageboostAppList == null) {
            return;
        }
        PageboostAppInfo pageboostAppInfo = null;
        if (!TextUtils.isEmpty(str)) {
            synchronized (pageboostAppList.mPageboostApps) {
                try {
                    Iterator it = pageboostAppList.mPageboostApps.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        PageboostAppInfo pageboostAppInfo2 = (PageboostAppInfo) it.next();
                        if (str.equals(pageboostAppInfo2.mName)) {
                            it.remove();
                            Slog.i(
                                    "Pageboost",
                                    "remove app @ runtime : " + pageboostAppInfo2.mName);
                            pageboostAppInfo = pageboostAppInfo2;
                            break;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        if (pageboostAppInfo != null) {
            PageboostAppDBHelper.dropTable(mFileDB, pageboostAppInfo.mNameTrimmed);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00f5  */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.server.am.Pageboost$1] */
    /* JADX WARN: Type inference failed for: r1v13, types: [com.android.server.am.Pageboost$1] */
    static {
        /*
            Method dump skipped, instructions count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.am.Pageboost.<clinit>():void");
    }

    public static void deleteDB(String str) {
        File databasePath = mContext.getDatabasePath(str);
        if (databasePath == null || !databasePath.exists()) {
            return;
        }
        SQLiteDatabase.deleteDatabase(databasePath);
    }

    public static void dumpInfo(PrintWriter printWriter, String[] strArr) {
        try {
            if (isPageboostMinimized()) {
                printWriter.println(
                        "minimized: "
                                + Boolean.toString(PAGEBOOST_MINIMIZE)
                                + ", kernel support: "
                                + Boolean.toString(PAGEBOOST_KERNEL_ENABLED)
                                + ", daemon support: "
                                + Boolean.toString(PAGEBOOST_DAEMON_ENABLED));
                return;
            }
            if (printWriter != null && mGlobalAppLRU != null) {
                if (strArr.length > 1 && "purge".equals(strArr[1])) {
                    SQLiteDatabase sQLiteDatabase = mAppDB;
                    if (sQLiteDatabase == null) {
                        printWriter.println("app DB not exists. Try again after 5 seconds");
                        return;
                    }
                    int i = PageboostAppDBHelper.$r8$clinit;
                    Slog.i(
                            "Pageboost",
                            "db clear : ret " + sQLiteDatabase.delete("AppLRU", "1", null));
                    printWriter.println("DB is purged. Please type 'adb reboot'");
                    printWriter.println(
                            "(!CAUTION: Do not reboot using 'power key'. Unless DB will be"
                                + " restored)");
                    return;
                }
                if (strArr.length <= 2 || !"heimdall".equals(strArr[1])) {
                    printWriter.println("== Pageboost dump start ==");
                    printWriter.println("");
                    printWriter.println("- Pageboost Configurations");
                    printWriter.println("ignore dram spec: " + PAGEBOOST_IGNORE_DRAM_SPECIFICATION);
                    printWriter.println("prefetch enabled: " + PAGEBOOST_IO_PREFETCH_ENABLED);
                    printWriter.println("alp enabled: " + PAGEBOOST_ACTIVE_LAUNCH_ENABLED);
                    printWriter.println(
                            "prefetch level: " + PageboostAppCapture.PAGEBOOST_IO_PREFETCH_LEVEL);
                    printWriter.println("prefetch app count: 1");
                    printWriter.println(
                            "vramdisk enabled: (config) false (decision_by_dram) "
                                    + Vramdisk.ENABLED);
                    printWriter.println("memprep enabled: false");
                    printWriter.println("");
                    printWriter.println("- Pageboost AppLRU Info");
                    PageboostAppList pageboostAppList = mGlobalAppLRU;
                    synchronized (pageboostAppList.mPageboostApps) {
                        try {
                            Iterator it = pageboostAppList.mPageboostApps.iterator();
                            while (it.hasNext()) {
                                printWriter.println(((PageboostAppInfo) it.next()).toString());
                            }
                        } finally {
                        }
                    }
                    printWriter.println("");
                    printWriter.println("- Pageboost Vramdisk Info");
                    printWriter.println("disabled");
                    printWriter.println("");
                    printWriter.println("- Action Log");
                    Vramdisk.print(printWriter);
                    printWriter.println("- Bigdata: false,  String:  ");
                    printWriter.println("== pageboost dump end ==");
                }
            }
        } catch (Exception unused) {
            Slog.e("Pageboost", "failed to dumpInfo by exception");
        }
    }

    public static FileMapList getFileMapFromCmd(String str) {
        int[] pidsForCommands = Process.getPidsForCommands(new String[] {str});
        if (pidsForCommands == null || pidsForCommands.length <= 0) {
            return null;
        }
        return new FileMapList(pidsForCommands[0]);
    }

    public static boolean isPageboostMinimized() {
        return (!PAGEBOOST_MINIMIZE && PAGEBOOST_KERNEL_ENABLED && PAGEBOOST_DAEMON_ENABLED)
                ? false
                : true;
    }

    public static void onAppLaunch(Intent intent) {
        try {
            if (!isPageboostMinimized()
                    && intent != null
                    && "android.intent.action.MAIN".equals(intent.getAction())
                    && intent.hasCategory("android.intent.category.LAUNCHER")
                    && intent.getComponent() != null) {
                String packageName = intent.getComponent().getPackageName();
                Slog.i("Pageboost", "onAppLaunch : " + packageName);
                if (packageName == null) {
                    return;
                }
                if (PageboostAppCapture.PAGEBOOST_IO_PREFETCH_LEVEL == 3) {
                    sendMessageWithObject(14, 0, packageName);
                } else {
                    sendMessageWithObject(5, 2000, packageName);
                }
                sendMessageWithObject(17, 0, packageName);
            }
        } catch (Exception unused) {
            Slog.e("Pageboost", "failed to onAppLaunch by exception");
        }
    }

    public static void onProcStatusChange(int i, int i2, String str) {
        try {
            if (isPageboostMinimized()) {
                return;
            }
            sendMessage(16, -1, i2, i, 0, str);
        } catch (Exception unused) {
            Slog.e("Pageboost", "failed to onProcStatusChange by exception");
        }
    }

    public static void prefetchPackage(String str, String str2) {
        try {
            if (isPageboostMinimized()) {
                return;
            }
            PageboostAppList pageboostAppList = mGlobalAppLRU;
            if (pageboostAppList == null) {
                Slog.e("Pageboost", "Pageboost pkg list is not initialized yet");
                return;
            }
            if (str == null) {
                Slog.e("Pageboost", "null pkg is requested");
                return;
            }
            PageboostAppInfo pageboostApp = pageboostAppList.getPageboostApp(str);
            if (pageboostApp == null) {
                Slog.i("Pageboost", "not in Pageboost pkg list : ".concat(str));
                return;
            }
            if ("alp".equals(str2)) {
                if (!pageboostApp.activeLaunch()) {
                    return;
                }
                if (PAGEBOOST_ACTIVE_LAUNCH_ENABLED && !isPageboostMinimized()) {
                    sendMessage(21, -1, -1, -1, PAGEBOOST_ACTIVE_LAUNCH_TIMEOUT, null);
                }
            } else if (!pageboostApp.execute()) {
                Slog.i("Pageboost", "failed to prefetch. maybe not captured yet : ".concat(str));
                return;
            }
            Vramdisk.add("prefetchRequested," + str + "," + str2);
        } catch (Exception unused) {
            Slog.e("Pageboost", "failed to prefetchPackage by exception");
        }
    }

    public static void sendMessage(int i, int i2, int i3, int i4, int i5, String str) {
        Bundle bundle = new Bundle();
        if (str != null) {
            bundle.putString("pkg", str);
        }
        if (i2 >= 0) {
            bundle.putInt("launchtime", i2);
        }
        if (i3 >= 0) {
            bundle.putInt("pid", i3);
        }
        if (i4 >= 0) {
            bundle.putInt(Constants.JSON_CLIENT_DATA_STATUS, i4);
        }
        Message obtainMessage = sHandler.obtainMessage(i);
        obtainMessage.setData(bundle);
        if (i5 > 0) {
            sHandler.sendMessageDelayed(obtainMessage, i5);
        } else {
            sHandler.sendMessage(obtainMessage);
        }
    }

    public static void sendMessageWithObject(int i, int i2, Object obj) {
        Message obtainMessage = sHandler.obtainMessage(i, obj);
        if (i2 > 0) {
            sHandler.sendMessageDelayed(obtainMessage, i2);
        } else {
            sHandler.sendMessage(obtainMessage);
        }
    }

    public static void startActiveLaunch(String str) {
        try {
            if (!PAGEBOOST_ACTIVE_LAUNCH_ENABLED || isPageboostMinimized() || str == null) {
                return;
            }
            Slog.i("Pageboost", "start alp : ".concat(str));
            sendMessage(19, -1, -1, -1, 0, str);
        } catch (Exception unused) {
            Slog.e("Pageboost", "failed to startActiveLaunch by exception");
        }
    }

    public static void stopActiveLaunch() {
        if (!PAGEBOOST_ACTIVE_LAUNCH_ENABLED || isPageboostMinimized()) {
            return;
        }
        Slog.i("Pageboost", "stop alp");
        sendMessage(20, -1, -1, -1, 0, null);
    }
}
