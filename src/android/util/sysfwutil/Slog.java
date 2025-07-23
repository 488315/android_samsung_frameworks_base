package android.util.sysfwutil;

import android.hardware.usb.UsbManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.FileUtils;
import android.os.Process;
import android.os.SystemProperties;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    private Slog() {
        initParam();
    }

    private void initParam() {
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

    private void updatePermissions() {
        try {
            this.mLogFile.createNewFile();
            FileUtils.setPermissions(this.mLogFile.getAbsolutePath(), 416, 1000, 1007);
        } catch (Exception e) {
            localLogE("initParam: error set permissions" + this.mLogFile.getAbsolutePath() + " , " + e);
        }
    }

    private synchronized void addMsgToList(String str, boolean z) {
        if (this.mSfSlogEnable) {
            String num = Integer.toString(Process.myTid());
            String format = new SimpleDateFormat("yy-MM-dd (z) HH:mm:ss.SSS", Locale.getDefault()).format(new Date());
            this.mLogList.add(format + " " + num + " " + str + ShaderAssembler.NEWLINE);
            localLogV("addMsgToList mLogList.size() " + this.mLogList.size() + " mLinesToDump " + this.mLinesToDump + " strNow[" + format + NavigationBarInflaterView.SIZE_MOD_END);
            if (this.mLogList.size() >= this.mLinesToDump || z) {
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

    /* JADX WARN: Removed duplicated region for block: B:20:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void dumpLogsToTheFile() {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.sysfwutil.Slog.dumpLogsToTheFile():void");
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
        int i = 4;
        if (stackTrace.length > 0 && stackTrace.length < 4) {
            i = stackTrace.length;
        } else if (stackTrace.length <= 4) {
            i = 0;
        }
        localLogV("stackTraceLength=" + i);
        if ("!@".equals(kernelLogPrefix)) {
            String str3 = kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + NavigationBarInflaterView.SIZE_MOD_END + exc.toString();
            if (getInstance() != null) {
                localLogV("Print exTitle 1 at SFSLOG");
                getInstance().addMsgToList("D " + str + ": " + str3, false);
            }
            localLogV("Print exTitle 1");
            android.util.Slog.d(str, str3);
            if (i > 0) {
                for (int i2 = 0; i2 < i; i2++) {
                    String str4 = kernelLogPrefix + NavigationBarInflaterView.SIZE_MOD_START + str + "] > " + stackTrace[i2].toString();
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
        if (i > 0) {
            for (int i3 = 0; i3 < i; i3++) {
                String str7 = kernelLogPrefix + " > " + stackTrace[i3].toString();
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
