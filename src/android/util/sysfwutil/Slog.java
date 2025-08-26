package android.util.sysfwutil;

import android.hardware.usb.UsbManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.FileUtils;
import android.os.Process;
import android.os.SystemProperties;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes4.dex */
public final class Slog {
    private static final boolean DEBUG = false;
    private static final String LOG0_PATH = "/data/log/sfslog.0.gz";
    private static final String LOG1_PATH = "/data/log/sfslog.1.gz";
    private static final String PATH_ENABLE_KERNEL_LOGGING = "/sdcard/Download/usbfwlog";
    private static final String TAG = "SFSLOG";
    private static String kernelLogPrefix = "";
    private long mCurentFileSize;
    private int mMaxLogFileSize;
    private static Slog mSlogInstance = new Slog();
    private static Object mLock = new Object();
    private boolean mSfSlogEnable = true;
    private File mLogFile = null;
    private int mLinesToDump = 50;
    private List<String> mLogList = new ArrayList();

    private static void localLogV(String str) {
    }

    static {
        if (new File(PATH_ENABLE_KERNEL_LOGGING).exists()) {
            kernelLogPrefix = "!@";
        } else {
            v(TAG, "No KERNEL_LOG_PREFIX!");
        }
    }

    private static Slog getInstance() {
        return mSlogInstance;
    }

    private Slog() throws IOException {
        initParam();
    }

    private void initParam() throws IOException {
        localLogV("initParam++");
        int i = SystemProperties.getInt("persist.sys.sfslog.maxfilesize", 262144);
        this.mMaxLogFileSize = i;
        if (i <= 0) {
            this.mSfSlogEnable = false;
        }
        File file = new File(LOG0_PATH);
        File file2 = new File(LOG1_PATH);
        if (file.exists() && file2.exists()) {
            if (file.length() > file2.length()) {
                file = file2;
            }
            this.mLogFile = file;
        } else if (file2.exists()) {
            this.mLogFile = file2;
        } else {
            this.mLogFile = file;
        }
        localLogE("initParam: choose " + this.mLogFile.getAbsolutePath());
        if (this.mLogFile.getParentFile() != null && !this.mLogFile.getParentFile().exists()) {
            localLogE("initParam: warning /data/log is absent ");
        }
        updatePermissions();
        this.mCurentFileSize = this.mLogFile.length();
        localLogE("initParam mSfSlogEnable " + this.mSfSlogEnable);
        localLogE("initParam mLinesToDump " + this.mLinesToDump);
        localLogE("initParam mMaxLogFileSize " + this.mMaxLogFileSize);
        localLogE("initParam mCurentFileSize " + this.mCurentFileSize);
        this.mLogList.add("============== Booting up ============== \n");
    }

    private void updatePermissions() throws IOException {
        try {
            this.mLogFile.createNewFile();
            FileUtils.setPermissions(this.mLogFile.getAbsolutePath(), 416, 1000, 1007);
        } catch (Exception e) {
            localLogE("initParam: error set permissions" + this.mLogFile.getAbsolutePath() + " , " + e);
        }
    }

    private synchronized void addMsgToList(String str, boolean z) {
        if (this.mSfSlogEnable) {
            String string = Integer.toString(Process.myTid());
            String str2 = new SimpleDateFormat("yy-MM-dd (z) HH:mm:ss.SSS", Locale.getDefault()).format(new Date());
            this.mLogList.add(str2 + " " + string + " " + str + ShaderAssembler.NEWLINE);
            localLogV("addMsgToList mLogList.size() " + this.mLogList.size() + " mLinesToDump " + this.mLinesToDump + " strNow[" + str2 + NavigationBarInflaterView.SIZE_MOD_END);
            if (this.mLogList.size() >= this.mLinesToDump || z) {
                dumpLogsToTheFile();
            }
        }
    }

    public static synchronized void shutdown() {
        if (getInstance() != null) {
            getInstance().onShutdown();
        }
    }

    private synchronized void onShutdown() {
        localLogE(UsbManager.USB_FUNCTION_SHUTDOWN);
        dumpLogsToTheFile();
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void dumpLogsToTheFile() throws Throwable {
        FileOutputStream fileOutputStream;
        localLogV("dumpLogsToTheFile++");
        if (this.mLogFile == null) {
            return;
        }
        Iterator<String> it = this.mLogList.iterator();
        GZIPOutputStream gZIPOutputStream = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(this.mLogFile, true);
                try {
                    try {
                        CountingOutputStream countingOutputStream = new CountingOutputStream(fileOutputStream);
                        GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(countingOutputStream);
                        while (it.hasNext()) {
                            try {
                                gZIPOutputStream2.write(it.next().getBytes("UTF-8"));
                            } catch (IOException e) {
                                e = e;
                                gZIPOutputStream = gZIPOutputStream2;
                                localLogE("Can't write: " + e);
                                if (gZIPOutputStream != null) {
                                    gZIPOutputStream.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                                this.mLogList.clear();
                                localLogV("dumpLogsToTheFile: mCurentFileSize " + this.mCurentFileSize);
                                if (this.mCurentFileSize > this.mMaxLogFileSize) {
                                }
                                localLogV("dumpLogsToTheFile--");
                            } catch (Throwable th) {
                                th = th;
                                gZIPOutputStream = gZIPOutputStream2;
                                if (gZIPOutputStream != null) {
                                    try {
                                        gZIPOutputStream.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                        throw th;
                                    }
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                                throw th;
                            }
                        }
                        this.mCurentFileSize += countingOutputStream.getCount();
                        gZIPOutputStream2.close();
                        fileOutputStream.close();
                    } catch (IOException e3) {
                        e = e3;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        } catch (IOException e5) {
            e = e5;
            fileOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
        }
        this.mLogList.clear();
        localLogV("dumpLogsToTheFile: mCurentFileSize " + this.mCurentFileSize);
        if (this.mCurentFileSize > this.mMaxLogFileSize) {
            localLogV("dumpLogsToTheFile: swap file, current " + this.mLogFile.getAbsolutePath());
            if (this.mLogFile.getAbsolutePath().equals(LOG0_PATH)) {
                this.mLogFile = new File(LOG1_PATH);
            } else {
                this.mLogFile = new File(LOG0_PATH);
            }
            localLogV("dumpLogsToTheFile: swap file, new " + this.mLogFile.getAbsolutePath());
            this.mLogFile.delete();
            this.mCurentFileSize = 0L;
            updatePermissions();
        }
        localLogV("dumpLogsToTheFile--");
    }

    private static void localLogE(String str) {
        android.util.Slog.e(TAG, str);
    }

    public static int v(String str, String str2) {
        if (getInstance() != null) {
            getInstance().addMsgToList("V " + str + ": " + str2, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.v(str, kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + NavigationBarInflaterView.SIZE_MOD_END + str2);
        }
        return android.util.Slog.v(str, str2);
    }

    public static int v(String str, String str2, Throwable th) {
        if (getInstance() != null) {
            getInstance().addMsgToList("V " + str + ": " + str2, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.v(str, kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + NavigationBarInflaterView.SIZE_MOD_END + str2, th);
        }
        return android.util.Slog.v(str, str2, th);
    }

    public static int d(String str, String str2) {
        if (getInstance() != null) {
            getInstance().addMsgToList("D " + str + ": " + str2, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.d(str, kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + NavigationBarInflaterView.SIZE_MOD_END + str2);
        }
        return android.util.Slog.d(str, str2);
    }

    public static int d(String str, String str2, Throwable th) {
        if (getInstance() != null) {
            getInstance().addMsgToList("D " + str + ": " + str2, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.d(str, kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + NavigationBarInflaterView.SIZE_MOD_END + str2, th);
        }
        return android.util.Slog.d(str, str2, th);
    }

    public static int i(String str, String str2) {
        if (getInstance() != null) {
            getInstance().addMsgToList("I " + str + ": " + str2, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.i(str, kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + NavigationBarInflaterView.SIZE_MOD_END + str2);
        }
        return android.util.Slog.i(str, str2);
    }

    public static int i(String str, String str2, Throwable th) {
        if (getInstance() != null) {
            getInstance().addMsgToList("I " + str + ": " + str2, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.i(str, kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + NavigationBarInflaterView.SIZE_MOD_END + str2, th);
        }
        return android.util.Slog.i(str, str2, th);
    }

    public static int w(String str, String str2) {
        if (getInstance() != null) {
            getInstance().addMsgToList("W " + str + ": " + str2, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.w(str, kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + NavigationBarInflaterView.SIZE_MOD_END + str2);
        }
        return android.util.Slog.w(str, str2);
    }

    public static int w(String str, String str2, Throwable th) {
        if (getInstance() != null) {
            getInstance().addMsgToList("W " + str + ": " + str2, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.w(str, kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + NavigationBarInflaterView.SIZE_MOD_END + str2, th);
        }
        return android.util.Slog.w(str, str2, th);
    }

    public static int w(String str, Throwable th) {
        return android.util.Slog.w(str, th);
    }

    public static int e(String str, String str2) {
        if (getInstance() != null) {
            getInstance().addMsgToList("E " + str + ": " + str2, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.e(str, kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + NavigationBarInflaterView.SIZE_MOD_END + str2);
        }
        return android.util.Slog.e(str, str2);
    }

    public static int e(String str, String str2, Throwable th) {
        if (getInstance() != null) {
            getInstance().addMsgToList("E " + str + ": " + str2, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.e(str, kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + NavigationBarInflaterView.SIZE_MOD_END + str2, th);
        }
        return android.util.Slog.e(str, str2, th);
    }

    public static int wtf(String str, String str2) {
        if (getInstance() != null) {
            getInstance().addMsgToList("WTF " + str + ": " + str2, false);
        }
        return android.util.Slog.wtf(str, str2);
    }

    public static int wtf(String str, Throwable th) {
        return android.util.Slog.wtf(str, th);
    }

    public static int wtf(String str, String str2, Throwable th) {
        if (getInstance() != null) {
            getInstance().addMsgToList("WTF " + str + ": " + str2, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.wtf(str, kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + NavigationBarInflaterView.SIZE_MOD_END + str2, th);
        }
        return android.util.Slog.wtf(str, str2, th);
    }

    public static int who(String str, String str2, Exception exc) {
        StackTraceElement[] stackTrace = exc.getStackTrace();
        int length = 4;
        if (stackTrace.length > 0 && stackTrace.length < 4) {
            length = stackTrace.length;
        } else if (stackTrace.length <= 4) {
            length = 0;
        }
        localLogV("stackTraceLength=" + length);
        if ("!@".equals(kernelLogPrefix)) {
            String str3 = kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + NavigationBarInflaterView.SIZE_MOD_END + exc.toString();
            if (getInstance() != null) {
                localLogV("Print exTitle 1 at SFSLOG");
                getInstance().addMsgToList("D " + str + ": " + str3, false);
            }
            localLogV("Print exTitle 1");
            android.util.Slog.d(str, str3);
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    String str4 = kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + "] > " + stackTrace[i].toString();
                    if (getInstance() != null) {
                        getInstance().addMsgToList("D " + str + ": " + str4, false);
                    }
                    android.util.Slog.d(str, str4);
                }
            }
            String str5 = kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + "]Print StackTrace of " + str2 + " Done";
            if (getInstance() != null) {
                getInstance().addMsgToList("D " + str + ": " + str5, true);
            }
            return android.util.Slog.d(str, str5);
        }
        String str6 = kernelLogPrefix + exc.toString();
        if (getInstance() != null) {
            localLogV("Print exTitle 2 at SFSLOG");
            getInstance().addMsgToList("D " + str + ": " + str6, false);
        }
        localLogV("Print exTitle 2");
        android.util.Slog.d(str, str6);
        if (length > 0) {
            for (int i2 = 0; i2 < length; i2++) {
                String str7 = kernelLogPrefix + " > " + stackTrace[i2].toString();
                if (getInstance() != null) {
                    getInstance().addMsgToList("D " + str + ": " + str7, false);
                }
                android.util.Slog.d(str, str7);
            }
        }
        String str8 = kernelLogPrefix + "Print StackTrace of " + str2 + " Done";
        if (getInstance() != null) {
            getInstance().addMsgToList("D " + str + ": " + str8, true);
        }
        return android.util.Slog.d(str, str8);
    }

    public static int println(int i, String str, String str2) {
        return android.util.Slog.println(i, kernelLogPrefix + str, str2);
    }
}
