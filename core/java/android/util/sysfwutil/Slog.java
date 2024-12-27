package android.util.sysfwutil;

import android.hardware.usb.UsbManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.FileUtils;
import android.os.Process;
import android.os.SystemProperties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;

public final class Slog {
    private static final boolean DEBUG = false;
    private static final String LOG0_PATH = "/data/log/sfslog.0.gz";
    private static final String LOG1_PATH = "/data/log/sfslog.1.gz";
    private static final String PATH_ENABLE_KERNEL_LOGGING = "/sdcard/Download/usbfwlog";
    private static final String TAG = "SFSLOG";
    private static String kernelLogPrefix;
    private long mCurentFileSize;
    private int mMaxLogFileSize;
    private static Slog mSlogInstance = new Slog();
    private static Object mLock = new Object();
    private boolean mSfSlogEnable = true;
    private File mLogFile = null;
    private int mLinesToDump = 50;
    private List<String> mLogList = new ArrayList();

    static {
        kernelLogPrefix = "";
        File file = new File(PATH_ENABLE_KERNEL_LOGGING);
        if (file.exists()) {
            kernelLogPrefix = "!@";
        } else {
            v(TAG, "No KERNEL_LOG_PREFIX!");
        }
    }

    private static Slog getInstance() {
        return mSlogInstance;
    }

    private Slog() {
        initParam();
    }

    private void initParam() {
        localLogV("initParam++");
        this.mMaxLogFileSize = SystemProperties.getInt("persist.sys.sfslog.maxfilesize", 262144);
        if (this.mMaxLogFileSize <= 0) {
            this.mSfSlogEnable = false;
        }
        File t0 = new File(LOG0_PATH);
        File t1 = new File(LOG1_PATH);
        if (t0.exists() && t1.exists()) {
            this.mLogFile = t0.length() > t1.length() ? t1 : t0;
        } else if (t1.exists()) {
            this.mLogFile = t1;
        } else {
            this.mLogFile = t0;
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

    private void updatePermissions() {
        try {
            this.mLogFile.createNewFile();
            FileUtils.setPermissions(this.mLogFile.getAbsolutePath(), 416, 1000, 1007);
        } catch (Exception e) {
            localLogE(
                    "initParam: error set permissions"
                            + this.mLogFile.getAbsolutePath()
                            + " , "
                            + e);
        }
    }

    private synchronized void addMsgToList(String msg, boolean force) {
        if (this.mSfSlogEnable) {
            String tid = Integer.toString(Process.myTid());
            SimpleDateFormat sdf =
                    new SimpleDateFormat("yy-MM-dd (z) HH:mm:ss.SSS", Locale.getDefault());
            String strNow = sdf.format(new Date());
            this.mLogList.add(strNow + " " + tid + " " + msg + "\n");
            localLogV(
                    "addMsgToList mLogList.size() "
                            + this.mLogList.size()
                            + " mLinesToDump "
                            + this.mLinesToDump
                            + " strNow["
                            + strNow
                            + NavigationBarInflaterView.SIZE_MOD_END);
            if (this.mLogList.size() >= this.mLinesToDump || force) {
                dumpLogsToTheFile();
            }
        }
    }

    public static synchronized void shutdown() {
        synchronized (Slog.class) {
            if (getInstance() != null) {
                getInstance().onShutdown();
            }
        }
    }

    private synchronized void onShutdown() {
        localLogE(UsbManager.USB_FUNCTION_SHUTDOWN);
        dumpLogsToTheFile();
    }

    private void dumpLogsToTheFile() {
        localLogV("dumpLogsToTheFile++");
        if (this.mLogFile == null) {
            return;
        }
        FileOutputStream fos = null;
        GZIPOutputStream gzos = null;
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                fos = new FileOutputStream(this.mLogFile, true);
                CountingOutputStream cos = new CountingOutputStream(fos);
                gzos = new GZIPOutputStream(cos);
                for (String element : this.mLogList) {
                    gzos.write(element.getBytes("UTF-8"));
                }
                this.mCurentFileSize += cos.getCount();
                gzos.close();
                fos.close();
            } catch (IOException e2) {
                localLogE("Can't write: " + e2);
                if (gzos != null) {
                    gzos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
            this.mLogList.clear();
            localLogV("dumpLogsToTheFile: mCurentFileSize " + this.mCurentFileSize);
            if (this.mCurentFileSize > this.mMaxLogFileSize) {
                localLogV(
                        "dumpLogsToTheFile: swap file, current " + this.mLogFile.getAbsolutePath());
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
        } catch (Throwable th) {
            if (gzos != null) {
                try {
                    gzos.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                    throw th;
                }
            }
            if (fos != null) {
                fos.close();
            }
            throw th;
        }
    }

    private static void localLogE(String msg) {
        android.util.Slog.e(TAG, msg);
    }

    private static void localLogV(String msg) {}

    public static int v(String tag, String msg) {
        if (getInstance() != null) {
            getInstance().addMsgToList("V " + tag + ": " + msg, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.v(
                    tag,
                    kernelLogPrefix
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + tag
                            + NavigationBarInflaterView.SIZE_MOD_END
                            + msg);
        }
        return android.util.Slog.v(tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (getInstance() != null) {
            getInstance().addMsgToList("V " + tag + ": " + msg, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.v(
                    tag,
                    kernelLogPrefix
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + tag
                            + NavigationBarInflaterView.SIZE_MOD_END
                            + msg,
                    tr);
        }
        return android.util.Slog.v(tag, msg, tr);
    }

    public static int d(String tag, String msg) {
        if (getInstance() != null) {
            getInstance().addMsgToList("D " + tag + ": " + msg, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.d(
                    tag,
                    kernelLogPrefix
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + tag
                            + NavigationBarInflaterView.SIZE_MOD_END
                            + msg);
        }
        return android.util.Slog.d(tag, msg);
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (getInstance() != null) {
            getInstance().addMsgToList("D " + tag + ": " + msg, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.d(
                    tag,
                    kernelLogPrefix
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + tag
                            + NavigationBarInflaterView.SIZE_MOD_END
                            + msg,
                    tr);
        }
        return android.util.Slog.d(tag, msg, tr);
    }

    public static int i(String tag, String msg) {
        if (getInstance() != null) {
            getInstance().addMsgToList("I " + tag + ": " + msg, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.i(
                    tag,
                    kernelLogPrefix
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + tag
                            + NavigationBarInflaterView.SIZE_MOD_END
                            + msg);
        }
        return android.util.Slog.i(tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (getInstance() != null) {
            getInstance().addMsgToList("I " + tag + ": " + msg, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.i(
                    tag,
                    kernelLogPrefix
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + tag
                            + NavigationBarInflaterView.SIZE_MOD_END
                            + msg,
                    tr);
        }
        return android.util.Slog.i(tag, msg, tr);
    }

    public static int w(String tag, String msg) {
        if (getInstance() != null) {
            getInstance().addMsgToList("W " + tag + ": " + msg, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.w(
                    tag,
                    kernelLogPrefix
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + tag
                            + NavigationBarInflaterView.SIZE_MOD_END
                            + msg);
        }
        return android.util.Slog.w(tag, msg);
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (getInstance() != null) {
            getInstance().addMsgToList("W " + tag + ": " + msg, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.w(
                    tag,
                    kernelLogPrefix
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + tag
                            + NavigationBarInflaterView.SIZE_MOD_END
                            + msg,
                    tr);
        }
        return android.util.Slog.w(tag, msg, tr);
    }

    public static int w(String tag, Throwable tr) {
        return android.util.Slog.w(tag, tr);
    }

    public static int e(String tag, String msg) {
        if (getInstance() != null) {
            getInstance().addMsgToList("E " + tag + ": " + msg, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.e(
                    tag,
                    kernelLogPrefix
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + tag
                            + NavigationBarInflaterView.SIZE_MOD_END
                            + msg);
        }
        return android.util.Slog.e(tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (getInstance() != null) {
            getInstance().addMsgToList("E " + tag + ": " + msg, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.e(
                    tag,
                    kernelLogPrefix
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + tag
                            + NavigationBarInflaterView.SIZE_MOD_END
                            + msg,
                    tr);
        }
        return android.util.Slog.e(tag, msg, tr);
    }

    public static int wtf(String tag, String msg) {
        if (getInstance() != null) {
            getInstance().addMsgToList("WTF " + tag + ": " + msg, false);
        }
        return android.util.Slog.wtf(tag, msg);
    }

    public static int wtf(String tag, Throwable tr) {
        return android.util.Slog.wtf(tag, tr);
    }

    public static int wtf(String tag, String msg, Throwable tr) {
        if (getInstance() != null) {
            getInstance().addMsgToList("WTF " + tag + ": " + msg, false);
        }
        if ("!@".equals(kernelLogPrefix)) {
            return android.util.Slog.wtf(
                    tag,
                    kernelLogPrefix
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + tag
                            + NavigationBarInflaterView.SIZE_MOD_END
                            + msg,
                    tr);
        }
        return android.util.Slog.wtf(tag, msg, tr);
    }

    public static int who(String tag, String methodName, Exception ex) {
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        int stackTraceLength = 0;
        if (stackTraceElements.length <= 0 || stackTraceElements.length >= 4) {
            if (stackTraceElements.length > 4) {
                stackTraceLength = 4;
            }
        } else {
            stackTraceLength = stackTraceElements.length;
        }
        localLogV("stackTraceLength=" + stackTraceLength);
        if ("!@".equals(kernelLogPrefix)) {
            String exTitle =
                    kernelLogPrefix
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + tag
                            + NavigationBarInflaterView.SIZE_MOD_END
                            + ex.toString();
            if (getInstance() != null) {
                localLogV("Print exTitle 1 at SFSLOG");
                getInstance().addMsgToList("D " + tag + ": " + exTitle, false);
            }
            localLogV("Print exTitle 1");
            android.util.Slog.d(tag, exTitle);
            if (stackTraceLength > 0) {
                for (int p = 0; p < stackTraceLength; p++) {
                    String exStackMsg =
                            kernelLogPrefix
                                    + NavigationBarInflaterView.SIZE_MOD_START
                                    + tag
                                    + "] > "
                                    + stackTraceElements[p].toString();
                    if (getInstance() != null) {
                        getInstance().addMsgToList("D " + tag + ": " + exStackMsg, false);
                    }
                    android.util.Slog.d(tag, exStackMsg);
                }
            }
            String exEndStr =
                    kernelLogPrefix
                            + NavigationBarInflaterView.SIZE_MOD_START
                            + tag
                            + "]Print StackTrace of "
                            + methodName
                            + " Done";
            if (getInstance() != null) {
                getInstance().addMsgToList("D " + tag + ": " + exEndStr, true);
            }
            return android.util.Slog.d(tag, exEndStr);
        }
        String exTitle2 = kernelLogPrefix + ex.toString();
        if (getInstance() != null) {
            localLogV("Print exTitle 2 at SFSLOG");
            getInstance().addMsgToList("D " + tag + ": " + exTitle2, false);
        }
        localLogV("Print exTitle 2");
        android.util.Slog.d(tag, exTitle2);
        if (stackTraceLength > 0) {
            for (int p2 = 0; p2 < stackTraceLength; p2++) {
                String exStackMsg2 = kernelLogPrefix + " > " + stackTraceElements[p2].toString();
                if (getInstance() != null) {
                    getInstance().addMsgToList("D " + tag + ": " + exStackMsg2, false);
                }
                android.util.Slog.d(tag, exStackMsg2);
            }
        }
        String exEndStr2 = kernelLogPrefix + "Print StackTrace of " + methodName + " Done";
        if (getInstance() != null) {
            getInstance().addMsgToList("D " + tag + ": " + exEndStr2, true);
        }
        return android.util.Slog.d(tag, exEndStr2);
    }

    public static int println(int priority, String tag, String msg) {
        return android.util.Slog.println(priority, kernelLogPrefix + tag, msg);
    }
}
