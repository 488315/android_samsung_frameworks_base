package com.android.server.power;

import android.R;
import android.app.IActivityManager;
import android.app.ProgressDialog;
import android.app.admin.SecurityLog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.p000pm.PackageManagerInternal;
import android.hardware.input.IInputManager;
import android.hardware.usb.UsbManager;
import android.media.AudioAttributes;
import android.net.INetd;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RecoverySystem;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.SystemVibrator;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.system.Os;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.TimingsTraceLog;
import android.view.ContextThemeWrapper;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;
import com.android.server.RescueParty;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import com.samsung.android.sepunion.SemUnionManager;
import com.samsung.android.service.HermesService.IHermesService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

/* loaded from: classes3.dex */
public final class ShutdownThread extends Thread {
    public static long currentTimeMillisStart = 0;
    public static ShutdownDialog dlgAnim = null;
    public static LogFileWriter logFileWriter = null;
    public static String mCallerName = null;
    public static String mReason = null;
    public static boolean mReboot = false;
    public static boolean mRebootHasProgressBar = false;
    public static boolean mRebootSafeMode = false;
    public static boolean mSupportQmg = false;
    public static boolean sIsRestrict = false;
    public static boolean sIsStarted = false;
    public boolean mActionDone;
    public final Object mActionDoneSync = new Object();
    public Context mContext;
    public PowerManager.WakeLock mCpuWakeLock;
    public Handler mHandler;
    public PowerManager mPowerManager;
    public ProgressDialog mProgressDialog;
    public PowerManager.WakeLock mScreenWakeLock;
    public static final Object sIsStartedGuard = new Object();
    public static final ShutdownThread sInstance = new ShutdownThread();
    public static final AudioAttributes VIBRATION_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
    public static final ArrayMap TRON_METRICS = new ArrayMap();
    public static String METRIC_SYSTEM_SERVER = "shutdown_system_server";
    public static String METRIC_SEND_BROADCAST = "shutdown_send_shutdown_broadcast";
    public static String METRIC_AM = "shutdown_activity_manager";
    public static String METRIC_PM = "shutdown_package_manager";
    public static String METRIC_RADIOS = "shutdown_radios";
    public static String METRIC_RADIO = "shutdown_radio";
    public static String METRIC_SHUTDOWN_TIME_START = "begin_shutdown";
    public static final boolean BIN_TYPE_USER = "user".equals(SystemProperties.get("ro.build.type"));
    public static final boolean BIN_TYPE_DEBUG_LOW = "0x4f4c".equals(SystemProperties.get("ro.boot.debug_level"));
    public static final boolean BIN_TYPE_PRODUCTSHIP = !Debug.semIsProductDev();
    public static boolean systemRequest = false;

    public static void shutdown(Context context, String str, boolean z) {
        android.util.Slog.i("ShutdownThread", "shutdown reason : " + str + ", confirm : " + z);
        if (sIsStarted) {
            Log.d("ShutdownThread", "!@Request to shutdown already running, returning.");
            return;
        }
        mReboot = false;
        mRebootSafeMode = false;
        mReason = str;
        mCallerName = null;
        shutdownInner(context);
    }

    public static void systemShutdown(Context context, String str) {
        android.util.Slog.i("ShutdownThread", "systemShutdown - reason: " + str);
        if (sIsStarted) {
            Log.d("ShutdownThread", "!@Request to shutdown already running, returning.");
            return;
        }
        mReboot = false;
        mRebootSafeMode = false;
        mReason = str;
        mCallerName = null;
        systemRequest = true;
        shutdownInner(context);
    }

    public static ShutdownThread get() {
        ShutdownThread shutdownThread;
        synchronized (ShutdownThread.class) {
            shutdownThread = sInstance;
        }
        return shutdownThread;
    }

    public static void sendMylog(String str, String str2) {
        MYLOG.m71i(str, str2);
    }

    public static void shutdownInner(Context context) {
        context.assertRuntimeOverlayThemable();
        synchronized (sIsStartedGuard) {
            if (sIsStarted) {
                Log.d("ShutdownThread", "!@Request to shutdown already running, returning./shutdowninner");
                return;
            }
            sIsRestrict = false;
            try {
                IRestrictionPolicy asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
                if ((!mReboot || mReason != null) && asInterface != null && !systemRequest && !asInterface.isPowerOffAllowed(new ContextInfo(), true)) {
                    android.util.Slog.d("ShutdownThread", "Shutdown Disabled by Administrator");
                    sIsRestrict = true;
                    sendPowerOffCancelBroadcast(context);
                    return;
                }
            } catch (RemoteException e) {
                android.util.Slog.e("ShutdownThread", "RemoteException", e);
            } catch (Exception e2) {
                android.util.Slog.e("ShutdownThread", "Exception", e2);
            }
            if (SystemProperties.getInt("persist.sys.rescue_level", 0) == 6) {
                SystemProperties.set("persist.sys.enable_isrb", Boolean.toString(true));
                SystemProperties.set("persist.sys.rescue_mode", "isrb_off");
                SystemProperties.set("sys.isrblevel.callreboot", Boolean.toString(true));
            }
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, (context.getResources().getConfiguration().uiMode & 48) == 32 ? R.style.Theme.DeviceDefault : R.style.Theme.DeviceDefault.Light);
            UpdatePoweroffResetReason(new Exception("It is not an exception!! just save the trace for process which called shutdown thread!! ShutdownThread.shutdown"));
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e3) {
                android.util.Slog.e("ShutdownThread", "InterruptedException", e3);
            }
            android.util.Slog.i("ShutdownThread", "!@########POWEROFF START###### current time : " + new SimpleDateFormat("yy/MM/dd HH:mm:ss.SSS").format(new Date(System.currentTimeMillis())));
            currentTimeMillisStart = System.currentTimeMillis();
            beginShutdownSequence(contextThemeWrapper);
        }
    }

    public static void UpdatePoweroffResetReason(Exception exc) {
        FileOutputStream fileOutputStream;
        android.util.Slog.i("ShutdownThread", "save power_off_reset_reason.txt");
        File file = new File(Environment.getDataDirectory().toString() + "/log/power_off_reset_reason.txt");
        if (file.length() > 10240) {
            ifOverSizeFileBackup(file);
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                try {
                    fileOutputStream = new FileOutputStream(file, true);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            Os.chmod(file.getPath(), FrameworkStatsLog.DISPLAY_HBM_STATE_CHANGED);
            Os.chown(file.getPath(), 1000, 1007);
            printWriter.println(new SimpleDateFormat("yy/MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
            if (mCallerName != null) {
                printWriter.println("caller : " + mCallerName);
            }
            printWriter.println("reason : " + mReason);
            exc.printStackTrace(printWriter);
            printWriter.flush();
            printWriter.close();
            fileOutputStream.close();
        } catch (Exception e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            e.printStackTrace();
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static void ifOverSizeFileBackup(File file) {
        android.util.Slog.i("ShutdownThread", "The size of power_off_reset_reason.txt is over than 10KB. Rename to power_off_reset_reason_backup.txt for backup.");
        File file2 = new File(Environment.getDataDirectory().toString() + "/log/power_off_reset_reason_backup.txt");
        if (file2.exists()) {
            android.util.Slog.i("ShutdownThread", "power_off_reset_reason_backup.txt file is already exist. So, delete it.");
            if (!file2.delete()) {
                android.util.Slog.e("ShutdownThread", "power_off_reset_reason_backup.txt delete fail");
                return;
            }
        }
        file.renameTo(file2);
    }

    public static void reboot(Context context, String str, boolean z) {
        android.util.Slog.i("ShutdownThread", "reboot reason : " + str + ", confirm : " + z);
        if (sIsStarted) {
            Log.d("ShutdownThread", "!@Request to shutdown already running, returning.");
            return;
        }
        mReboot = true;
        mRebootSafeMode = false;
        mRebootHasProgressBar = false;
        mReason = str;
        mCallerName = null;
        shutdownInner(context);
    }

    public static void rebootSafeMode(Context context, boolean z) {
        if (sIsStarted) {
            Log.d("ShutdownThread", "!@Request to shutdown already running, returning./rebootSafeMode()");
            return;
        }
        if (((UserManager) context.getSystemService("user")).hasUserRestriction("no_safe_boot")) {
            return;
        }
        try {
            IRestrictionPolicy asInterface = IRestrictionPolicy.Stub.asInterface(ServiceManager.getService("restriction_policy"));
            if (asInterface != null && !asInterface.isSafeModeAllowed(new ContextInfo())) {
                android.util.Slog.i("ShutdownThread", "safe mode is not allowed by IT admin");
                return;
            }
        } catch (RemoteException unused) {
        }
        mReboot = true;
        mRebootSafeMode = true;
        mRebootHasProgressBar = false;
        mReason = "SafeMode";
        mCallerName = null;
        shutdownInner(context);
    }

    public static ProgressDialog showShutdownDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        String str = mReason;
        if (str != null && str.startsWith("recovery-update")) {
            mRebootHasProgressBar = RecoverySystem.UNCRYPT_PACKAGE_FILE.exists() && !RecoverySystem.BLOCK_MAP_FILE.exists();
            progressDialog.setTitle(context.getText(17042370));
            if (mRebootHasProgressBar) {
                progressDialog.setMax(100);
                progressDialog.setProgress(0);
                progressDialog.setIndeterminate(false);
                progressDialog.setProgressNumberFormat(null);
                progressDialog.setProgressStyle(1);
                progressDialog.setMessage(context.getText(17042368));
            } else {
                if (showSysuiReboot()) {
                    return null;
                }
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(context.getText(17042369));
            }
        } else {
            String str2 = mReason;
            if (str2 != null && str2.equals("recovery")) {
                if (RescueParty.isAttemptingFactoryReset()) {
                    progressDialog.setTitle(context.getText(17042327));
                    progressDialog.setMessage(context.getText(17042825));
                    progressDialog.setIndeterminate(true);
                } else {
                    if (showSysuiReboot()) {
                        return null;
                    }
                    progressDialog.setTitle(context.getText(17042366));
                    progressDialog.setMessage(context.getText(17042365));
                    progressDialog.setIndeterminate(true);
                }
            } else {
                if (showSysuiReboot()) {
                    return null;
                }
                progressDialog.setTitle(context.getText(17042327));
                progressDialog.setMessage(context.getText(17042825));
                progressDialog.setIndeterminate(true);
            }
        }
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setType(2009);
        progressDialog.show();
        return progressDialog;
    }

    public static boolean showSysuiReboot() {
        Log.d("ShutdownThread", "Attempting to use SysUI shutdown UI");
        try {
            if (((StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class)).showShutdownUi(mReboot, mReason)) {
                Log.d("ShutdownThread", "SysUI handling shutdown UI");
                return true;
            }
        } catch (Exception unused) {
        }
        Log.d("ShutdownThread", "SysUI is unavailable");
        return false;
    }

    public static void sendPowerOffCancelBroadcast(Context context) {
        context.sendBroadcast(new Intent("POWER_OFF_CANCEL"));
    }

    public static void beginShutdownSequence(Context context) {
        synchronized (sIsStartedGuard) {
            if (sIsStarted) {
                Log.d("ShutdownThread", "!@Shutdown sequence already running, returning./beginShutdownSequence");
                return;
            }
            sIsStarted = true;
            getDelayDumpstate.startState();
            openLogFileWriter();
            MYLOG.m71i("ShutdownThread", "!@beginShutdownSequence");
            setInputKeysDisable();
            Intent intent = new Intent();
            intent.setAction("com.sec.android.internal.ims.FLIGHT_MODE");
            intent.putExtra("powerofftriggered", 0);
            intent.putExtra("isShutDownForRCS", true);
            context.sendBroadcast(intent);
            MYLOG.m71i("ShutdownThread", "!@Shutdown animation will start");
            dlgAnim = new ShutdownDialog(context);
            String str = mReason;
            if (str != null && str.startsWith("recovery-update")) {
                sInstance.mProgressDialog = showShutdownDialog(context);
            } else {
                if (LibQmg.checkSupportQmg()) {
                    boolean existAnim = dlgAnim.existAnim();
                    mSupportQmg = existAnim;
                    if (existAnim) {
                        android.util.Slog.i("ShutdownThread", "!@play QMG animation");
                        if ("silent.sec".equals(mReason) || new File("/efs/sec_efs/auto_reboot/silence_LCDoff.txt").exists()) {
                            dlgAnim.setSilentShutdown(true);
                        }
                        dlgAnim.prepareShutdown();
                        dlgAnim.show();
                    }
                }
                sInstance.mProgressDialog = showShutdownDialog(context);
            }
            ShutdownThread shutdownThread = sInstance;
            shutdownThread.mContext = context;
            PowerManager powerManager = (PowerManager) context.getSystemService("power");
            shutdownThread.mPowerManager = powerManager;
            shutdownThread.mCpuWakeLock = null;
            try {
                PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "ShutdownThread-cpu");
                shutdownThread.mCpuWakeLock = newWakeLock;
                newWakeLock.setReferenceCounted(false);
                shutdownThread.mCpuWakeLock.acquire();
            } catch (SecurityException e) {
                Log.w("ShutdownThread", "No permission to acquire wake lock", e);
                sInstance.mCpuWakeLock = null;
            }
            ShutdownThread shutdownThread2 = sInstance;
            shutdownThread2.mScreenWakeLock = null;
            if (shutdownThread2.mPowerManager.isScreenOn()) {
                try {
                    PowerManager.WakeLock newWakeLock2 = shutdownThread2.mPowerManager.newWakeLock(26, "ShutdownThread-screen");
                    shutdownThread2.mScreenWakeLock = newWakeLock2;
                    newWakeLock2.setReferenceCounted(false);
                    shutdownThread2.mScreenWakeLock.acquire();
                } catch (SecurityException e2) {
                    Log.w("ShutdownThread", "No permission to acquire wake lock", e2);
                    sInstance.mScreenWakeLock = null;
                }
            }
            if (SecurityLog.isLoggingEnabled()) {
                SecurityLog.writeEvent(210010, new Object[0]);
            }
            ShutdownThread shutdownThread3 = sInstance;
            shutdownThread3.mHandler = new Handler() { // from class: com.android.server.power.ShutdownThread.1
            };
            shutdownThread3.start();
        }
    }

    public static void setInputKeysDisable() {
        IInputManager asInterface = IInputManager.Stub.asInterface(ServiceManager.checkService("input"));
        if (asInterface == null) {
            android.util.Slog.e("ShutdownThread", "ServiceManager.checkService fail");
            return;
        }
        try {
            android.util.Slog.i("ShutdownThread", "setInputKeysDisable");
            asInterface.setStartedShutdown(true);
        } catch (RemoteException e) {
            android.util.Slog.d("ShutdownThread", "error occur while input disable");
            e.printStackTrace();
        }
    }

    public void actionDone() {
        synchronized (this.mActionDoneSync) {
            this.mActionDone = true;
            this.mActionDoneSync.notifyAll();
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        TimingsTraceLog newTimingsLog = newTimingsLog();
        newTimingsLog.traceBegin("SystemServerShutdown");
        metricShutdownStart();
        metricStarted(METRIC_SYSTEM_SERVER);
        Led.m70On();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.power.ShutdownThread.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                ShutdownThread.this.actionDone();
            }
        };
        StringBuilder sb = new StringBuilder();
        sb.append(mReboot ? "1" : "0");
        String str = mReason;
        if (str == null) {
            str = "";
        }
        sb.append(str);
        SystemProperties.set("sys.shutdown.requested", sb.toString());
        if (mRebootSafeMode) {
            SystemProperties.set("persist.sys.safemode", "1");
        }
        newTimingsLog.traceBegin("DumpPreRebootInfo");
        try {
            android.util.Slog.i("ShutdownThread", "Logging pre-reboot information...");
            PreRebootLogger.log(this.mContext);
        } catch (Exception e) {
            android.util.Slog.e("ShutdownThread", "Failed to log pre-reboot information", e);
        }
        newTimingsLog.traceEnd();
        metricStarted(METRIC_SEND_BROADCAST);
        newTimingsLog.traceBegin("SendShutdownBroadcast");
        MYLOG.m71i("ShutdownThread", "!@Sending shutdown broadcast...");
        this.mActionDone = false;
        Intent intent = new Intent("android.intent.action.ACTION_SHUTDOWN");
        intent.addFlags(1342177280);
        this.mContext.sendOrderedBroadcastAsUser(intent, UserHandle.ALL, null, broadcastReceiver, this.mHandler, 0, null, null);
        long elapsedRealtime = SystemClock.elapsedRealtime() + 10000;
        synchronized (this.mActionDoneSync) {
            while (true) {
                if (this.mActionDone) {
                    break;
                }
                long elapsedRealtime2 = elapsedRealtime - SystemClock.elapsedRealtime();
                if (elapsedRealtime2 <= 0) {
                    Log.w("ShutdownThread", "Shutdown broadcast timed out");
                    break;
                } else {
                    if (mRebootHasProgressBar) {
                        sInstance.setRebootProgress((int) ((((10000 - elapsedRealtime2) * 1.0d) * 2.0d) / 10000.0d), null);
                    }
                    try {
                        this.mActionDoneSync.wait(Math.min(elapsedRealtime2, 500L));
                    } catch (InterruptedException unused) {
                    }
                }
            }
        }
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(2, null);
        }
        long currentTimeMillis = System.currentTimeMillis() - currentTimeMillisStart;
        String valueOf = String.valueOf(SystemProperties.get("dev.shutdownbroadcast.on", ""));
        if ((!BIN_TYPE_PRODUCTSHIP || !BIN_TYPE_DEBUG_LOW) && currentTimeMillis >= 5000) {
            MYLOG.m71i("ShutdownThread", "!@shutdown is too slow, elapsed time from POWEROFF START to BROADCAST_SHUTDOWN is " + currentTimeMillis);
        } else {
            "On".equalsIgnoreCase(valueOf);
        }
        newTimingsLog.traceEnd();
        metricEnded(METRIC_SEND_BROADCAST);
        shutdownHermes();
        MYLOG.m71i("ShutdownThread", "!@Shutting down activity manager...");
        newTimingsLog.traceBegin("ShutdownActivityManager");
        metricStarted(METRIC_AM);
        IActivityManager asInterface = IActivityManager.Stub.asInterface(ServiceManager.checkService("activity"));
        if (asInterface != null) {
            try {
                asInterface.shutdown(10000);
            } catch (RemoteException unused2) {
            }
        }
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(4, null);
        }
        newTimingsLog.traceEnd();
        metricEnded(METRIC_AM);
        MYLOG.m71i("ShutdownThread", "!@Shutting down package manager...");
        newTimingsLog.traceBegin("ShutdownPackageManager");
        metricStarted(METRIC_PM);
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        if (packageManagerInternal != null) {
            packageManagerInternal.shutdown();
        }
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(6, null);
        }
        newTimingsLog.traceEnd();
        metricEnded(METRIC_PM);
        newTimingsLog.traceBegin("ShutdownRadios");
        metricStarted(METRIC_RADIOS);
        shutdownRadios(12000);
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(18, null);
        }
        newTimingsLog.traceEnd();
        metricEnded(METRIC_RADIOS);
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(20, null);
            uncrypt();
        }
        MYLOG.m71i("ShutdownThread", "!@waitForAnimEnd");
        if ("ON".equals(SystemProperties.get("service.poweranimation.on", ""))) {
            MYLOG.m71i("ShutdownThread", "!@wait for PNG animation end");
            waitForAnimationEnd(10);
        } else if (LibQmg.checkSupportQmg() && mSupportQmg) {
            MYLOG.m71i("ShutdownThread", "!@wait for QMG animation end");
            dlgAnim.waitForAnimEnd(10);
        }
        ((UsbManager) this.mContext.getSystemService("usb")).enableUsbDataSignal(false);
        if (SystemProperties.get("ro.boot.debug_level", "0x4f4c").equals("0x494d")) {
            waitForDumpstateEnd(60);
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("!@run, ");
        sb2.append(mReboot ? "reboot" : "shutdown");
        sb2.append(" requested reason=");
        String str2 = mReason;
        if (str2 == null) {
            str2 = "null";
        }
        sb2.append(str2);
        MYLOG.m71i("ShutdownThread", sb2.toString());
        rebootOrShutdown(this.mContext, mReboot, mReason);
    }

    public boolean waitForAnimationEnd(int i) {
        long elapsedRealtime = SystemClock.elapsedRealtime() + (i * 1000);
        while (!"END".equals(SystemProperties.get("dev.shutdownanimation.end"))) {
            if (elapsedRealtime - SystemClock.elapsedRealtime() <= 0) {
                android.util.Slog.w("ShutdownThread", "!@Animation finish wait timed out");
                return true;
            }
            android.util.Slog.d("ShutdownThread", "!@wait for finish : sleep 100ms");
            try {
                Thread.sleep(100L);
            } catch (InterruptedException unused) {
                android.util.Slog.e("ShutdownThread", "InterruptedException");
            }
        }
        return true;
    }

    public boolean waitForDumpstateEnd(int i) {
        long elapsedRealtime = SystemClock.elapsedRealtime() + (i * 1000);
        while (true) {
            if (!INetd.IF_FLAG_RUNNING.equals(SystemProperties.get("init.svc.bugreportd")) && !INetd.IF_FLAG_RUNNING.equals(SystemProperties.get("init.svc.bugreportm"))) {
                return true;
            }
            if (elapsedRealtime - SystemClock.elapsedRealtime() <= 0) {
                android.util.Slog.w("ShutdownThread", "!@Dumpstate finish wait timed out");
                return true;
            }
            android.util.Slog.d("ShutdownThread", "!@wait for finish Dumpstate: sleep 1000ms");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException unused) {
                android.util.Slog.e("ShutdownThread", "InterruptedException");
            }
        }
    }

    public static TimingsTraceLog newTimingsLog() {
        return new TimingsTraceLog("ShutdownTiming", 524288L);
    }

    public static void metricStarted(String str) {
        ArrayMap arrayMap = TRON_METRICS;
        synchronized (arrayMap) {
            arrayMap.put(str, Long.valueOf(SystemClock.elapsedRealtime() * (-1)));
        }
    }

    public static void metricEnded(String str) {
        ArrayMap arrayMap = TRON_METRICS;
        synchronized (arrayMap) {
            arrayMap.put(str, Long.valueOf(SystemClock.elapsedRealtime() + ((Long) arrayMap.get(str)).longValue()));
        }
    }

    public static void metricShutdownStart() {
        ArrayMap arrayMap = TRON_METRICS;
        synchronized (arrayMap) {
            arrayMap.put(METRIC_SHUTDOWN_TIME_START, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public final void setRebootProgress(final int i, final CharSequence charSequence) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.power.ShutdownThread.3
            @Override // java.lang.Runnable
            public void run() {
                if (ShutdownThread.this.mProgressDialog != null) {
                    ShutdownThread.this.mProgressDialog.setProgress(i);
                    if (charSequence != null) {
                        ShutdownThread.this.mProgressDialog.setMessage(charSequence);
                    }
                }
            }
        });
    }

    public final void shutdownHermes() {
        Thread thread = new Thread() { // from class: com.android.server.power.ShutdownThread.4
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                SemUnionManager semUnionManager = (SemUnionManager) ShutdownThread.this.mContext.getSystemService("sepunion");
                try {
                    if (semUnionManager != null) {
                        IBinder semSystemService = semUnionManager.getSemSystemService("HermesService");
                        if (semSystemService != null) {
                            IHermesService asInterface = IHermesService.Stub.asInterface(semSystemService);
                            if (asInterface != null) {
                                asInterface.hermesTerminateService();
                            } else {
                                android.util.Slog.e("ShutdownThread", "!@Shutdown HermesService is skipped.");
                            }
                        } else {
                            android.util.Slog.e("ShutdownThread", "!@Shutdown HermesService is skipped um is null.");
                        }
                    } else {
                        android.util.Slog.e("ShutdownThread", "!@Shutdown HermesService is skipped um is null.");
                    }
                } catch (Exception e) {
                    android.util.Slog.e("ShutdownThread", "!@Exception during hermesservice shutdown", e);
                }
            }
        };
        android.util.Slog.i("ShutdownThread", "!@Shutdown HermesService start.");
        thread.start();
        try {
            thread.join(30L);
        } catch (InterruptedException unused) {
        }
        android.util.Slog.i("ShutdownThread", "!@Shutdown HermesService end.");
    }

    public abstract class Led {
        /* renamed from: On */
        public static void m70On() {
            fileWriteInt(6);
        }

        public static void fileWriteInt(int i) {
            FileOutputStream fileOutputStream;
            File file = new File("/sys/class/sec/led/led_pattern");
            if (!file.isFile()) {
                android.util.Slog.i("LED", "!@LED File is not exist");
                return;
            }
            FileOutputStream fileOutputStream2 = null;
            try {
                try {
                    try {
                        fileOutputStream = new FileOutputStream(file);
                    } catch (IOException unused) {
                    }
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    fileOutputStream.write(Integer.toString(i).getBytes());
                    fileOutputStream.close();
                } catch (IOException unused2) {
                    fileOutputStream2 = fileOutputStream;
                    android.util.Slog.i("LED", "!@Exception has raised while file write");
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e) {
                            android.util.Slog.e("ShutdownThread", "led file close error", e);
                        }
                    }
                    throw th;
                }
            } catch (IOException e2) {
                android.util.Slog.e("ShutdownThread", "led file close error", e2);
            }
        }
    }

    public final void shutdownRadios(final int i) {
        long j = i;
        final long elapsedRealtime = SystemClock.elapsedRealtime() + j;
        final boolean[] zArr = new boolean[1];
        Thread thread = new Thread() { // from class: com.android.server.power.ShutdownThread.5
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                MYLOG.m71i("ShutdownThread", "!@Start shutdown radios");
                TimingsTraceLog newTimingsLog = ShutdownThread.newTimingsLog();
                TelephonyManager telephonyManager = (TelephonyManager) ShutdownThread.this.mContext.getSystemService(TelephonyManager.class);
                boolean z = telephonyManager == null || !telephonyManager.isAnyRadioPoweredOn();
                if (!z) {
                    Log.w("ShutdownThread", "Turning off cellular radios...");
                    ShutdownThread.metricStarted(ShutdownThread.METRIC_RADIO);
                    telephonyManager.shutdownAllRadios();
                }
                MYLOG.m71i("ShutdownThread", "Waiting for Radio...");
                long j2 = elapsedRealtime;
                long elapsedRealtime2 = SystemClock.elapsedRealtime();
                while (true) {
                    if (j2 - elapsedRealtime2 <= 0) {
                        return;
                    }
                    if (ShutdownThread.mRebootHasProgressBar) {
                        ShutdownThread.sInstance.setRebootProgress(((int) ((((r8 - r6) * 1.0d) * 12.0d) / i)) + 6, null);
                    }
                    if (!z && (!telephonyManager.isAnyRadioPoweredOn())) {
                        MYLOG.m71i("ShutdownThread", "!@Radio turned off.");
                        ShutdownThread.metricEnded(ShutdownThread.METRIC_RADIO);
                        newTimingsLog.logDuration("ShutdownRadio", ((Long) ShutdownThread.TRON_METRICS.get(ShutdownThread.METRIC_RADIO)).longValue());
                    }
                    if (z) {
                        MYLOG.m71i("ShutdownThread", "!@Radio shutdown complete.");
                        zArr[0] = true;
                        return;
                    }
                    MYLOG.m71i("ShutdownThread", "!@before sleep");
                    SystemClock.sleep(100L);
                    MYLOG.m71i("ShutdownThread", "!@after sleep");
                    MYLOG.m71i("ShutdownThread", "!@[Phone off retry:" + SystemClock.elapsedRealtime() + "] : " + elapsedRealtime + " radio=" + z);
                    j2 = elapsedRealtime;
                    elapsedRealtime2 = SystemClock.elapsedRealtime();
                }
            }
        };
        thread.start();
        try {
            thread.join(j);
        } catch (InterruptedException unused) {
        }
        if (zArr[0]) {
            return;
        }
        MYLOG.m71i("ShutdownThread", "Timed out waiting for Radio shutdown.");
    }

    public static void rebootOrShutdown(Context context, boolean z, String str) {
        if (z) {
            MYLOG.m71i("ShutdownThread", "!@Rebooting, reason: " + str);
            PowerManagerService.lowLevelReboot(str);
            Log.e("ShutdownThread", "!@Reboot failed, will attempt shutdown instead");
            closeLogFileWriter();
            str = "[shutdownthread]rebootFailed";
        } else if (context != null) {
            SystemVibrator systemVibrator = new SystemVibrator(context);
            try {
                if (systemVibrator.hasVibrator()) {
                    systemVibrator.vibrate(500L, VIBRATION_ATTRIBUTES);
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException unused) {
                    }
                }
            } catch (Exception e) {
                Log.w("ShutdownThread", "Failed to vibrate during shutdown.", e);
            }
        }
        MYLOG.m71i("ShutdownThread", "!@Performing low-level shutdown...");
        closeLogFileWriter();
        PowerManagerService.lowLevelShutdown(str);
    }

    public final void uncrypt() {
        Log.i("ShutdownThread", "Calling uncrypt and monitoring the progress...");
        final RecoverySystem.ProgressListener progressListener = new RecoverySystem.ProgressListener() { // from class: com.android.server.power.ShutdownThread.6
            @Override // android.os.RecoverySystem.ProgressListener
            public void onProgress(int i) {
                if (i >= 0 && i < 100) {
                    ShutdownThread.sInstance.setRebootProgress(((int) ((i * 80.0d) / 100.0d)) + 20, ShutdownThread.this.mContext.getText(17042367));
                } else if (i == 100) {
                    ShutdownThread.sInstance.setRebootProgress(i, ShutdownThread.this.mContext.getText(17042369));
                }
            }
        };
        final boolean[] zArr = {false};
        Thread thread = new Thread() { // from class: com.android.server.power.ShutdownThread.7
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                int i = 3;
                while (i > 0) {
                    try {
                        RecoverySystem.processPackage(ShutdownThread.this.mContext, new File(FileUtils.readTextFile(RecoverySystem.UNCRYPT_PACKAGE_FILE, 0, null)), progressListener);
                        MYLOG.m71i("ShutdownThread", "!@uncrypt finished. No need to retry uncrypt");
                        break;
                    } catch (Exception e) {
                        i--;
                        MYLOG.m71i("ShutdownThread", "!@Error uncrypting file : " + Log.getStackTraceString(e));
                    }
                }
                zArr[0] = true;
            }
        };
        thread.start();
        try {
            thread.join(900000L);
        } catch (InterruptedException unused) {
        }
        if (zArr[0]) {
            return;
        }
        Log.w("ShutdownThread", "Timed out waiting for uncrypt.");
        try {
            FileUtils.stringToFile(RecoverySystem.UNCRYPT_STATUS_FILE, String.format("uncrypt_time: %d\nuncrypt_error: %d\n", 900, 100));
        } catch (IOException e) {
            Log.e("ShutdownThread", "Failed to write timeout message to uncrypt status", e);
        }
    }

    public static void openLogFileWriter() {
        android.util.Slog.i("ShutdownThread", "Shutdown logFileWriter start");
        if (logFileWriter == null) {
            logFileWriter = new LogFileWriter("/data/log/", "shutdown_profile");
        }
    }

    public static void closeLogFileWriter() {
        if (logFileWriter != null) {
            android.util.Slog.i("ShutdownThread", "logFileWriter saveAndClose logFileWriter : " + logFileWriter);
            logFileWriter.saveAndClose();
            logFileWriter = null;
        }
    }

    public static void wirteLogFileWriter(String str) {
        LogFileWriter logFileWriter2 = logFileWriter;
        if (logFileWriter2 != null) {
            logFileWriter2.write("ShutdownThread", str);
        }
    }

    public class LogFileWriter {
        public File file;
        public FileOutputStream latestShutdownProfile;
        public File latestfile;
        public FileOutputStream shutdownProfile;

        public LogFileWriter(String str, String str2) {
            this.latestShutdownProfile = null;
            String generateFilename = generateFilename(str, str2);
            if (generateFilename != null) {
                try {
                    this.file = new File(str + generateFilename);
                    this.shutdownProfile = new FileOutputStream(this.file);
                    this.latestfile = new File(str + str2 + "_latest.txt");
                    this.latestShutdownProfile = new FileOutputStream(this.latestfile);
                    this.file.setReadable(true, false);
                    this.latestfile.setReadable(true, false);
                } catch (Exception e) {
                    android.util.Slog.e("ShutdownThread", "LogFileWriter.LogFileWriter error", e);
                }
            }
        }

        public final String generateFilename(String str, String str2) {
            File[] listFiles = new File(str).listFiles();
            if (listFiles == null) {
                return null;
            }
            TreeMap treeMap = new TreeMap();
            for (File file : listFiles) {
                String name = file.getName();
                if (file.isFile() && name.startsWith(str2)) {
                    treeMap.put(Long.valueOf(file.lastModified()), name);
                }
            }
            if (treeMap.size() < 5) {
                return String.format("%s.%d.%s", str2, Integer.valueOf(treeMap.size() + 1), "txt");
            }
            return (String) treeMap.get(Long.valueOf(((Long) treeMap.keySet().iterator().next()).longValue()));
        }

        public void write(String str, String str2) {
            if (this.shutdownProfile == null) {
                android.util.Slog.i(str, "shutdownProfile is null");
                return;
            }
            StringBuilder sb = new StringBuilder();
            Calendar calendar = Calendar.getInstance();
            sb.append(String.format("%02d-%02d %02d:%02d:%02d.%03d %s: %s\n", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)), str, str2));
            try {
                this.shutdownProfile.write(sb.toString().getBytes());
                FileOutputStream fileOutputStream = this.latestShutdownProfile;
                if (fileOutputStream != null) {
                    fileOutputStream.write(sb.toString().getBytes());
                }
            } catch (IOException e) {
                android.util.Slog.e(str, "LogFileWriter.write fail", e);
            }
        }

        public void saveAndClose() {
            FileOutputStream fileOutputStream = this.shutdownProfile;
            if (fileOutputStream == null) {
                return;
            }
            try {
                try {
                    try {
                        fileOutputStream.flush();
                        this.shutdownProfile.close();
                        this.shutdownProfile = null;
                        FileOutputStream fileOutputStream2 = this.latestShutdownProfile;
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.flush();
                            this.latestShutdownProfile.close();
                            this.latestShutdownProfile = null;
                        }
                        FileOutputStream fileOutputStream3 = this.shutdownProfile;
                        if (fileOutputStream3 != null) {
                            fileOutputStream3.close();
                            this.shutdownProfile = null;
                        }
                        FileOutputStream fileOutputStream4 = this.latestShutdownProfile;
                        if (fileOutputStream4 != null) {
                            fileOutputStream4.close();
                            this.latestShutdownProfile = null;
                        }
                    } catch (IOException e) {
                        android.util.Slog.e("ShutdownThread", "LogFileWriter.saveAndClose fail", e);
                        FileOutputStream fileOutputStream5 = this.shutdownProfile;
                        if (fileOutputStream5 != null) {
                            fileOutputStream5.close();
                            this.shutdownProfile = null;
                        }
                        FileOutputStream fileOutputStream6 = this.latestShutdownProfile;
                        if (fileOutputStream6 != null) {
                            fileOutputStream6.close();
                            this.latestShutdownProfile = null;
                        }
                    }
                } catch (Throwable th) {
                    try {
                        FileOutputStream fileOutputStream7 = this.shutdownProfile;
                        if (fileOutputStream7 != null) {
                            fileOutputStream7.close();
                            this.shutdownProfile = null;
                        }
                        FileOutputStream fileOutputStream8 = this.latestShutdownProfile;
                        if (fileOutputStream8 != null) {
                            fileOutputStream8.close();
                            this.latestShutdownProfile = null;
                        }
                    } catch (IOException e2) {
                        android.util.Slog.e("ShutdownThread", "LogFileWriter.saveAndClose error", e2);
                    }
                    throw th;
                }
            } catch (IOException e3) {
                android.util.Slog.e("ShutdownThread", "LogFileWriter.saveAndClose error", e3);
            }
        }
    }

    public abstract class MYLOG {
        /* renamed from: i */
        public static int m71i(String str, String str2) {
            int i;
            try {
                i = android.util.Slog.i(str, str2);
            } catch (Exception e) {
                e = e;
                i = -1;
            }
            try {
                ShutdownThread.wirteLogFileWriter(str2);
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                if (ShutdownThread.dlgAnim != null) {
                    StringBuilder sb = new StringBuilder();
                    Calendar calendar = Calendar.getInstance();
                    sb.append(String.format("%02d-%02d %02d:%02d:%02d.%03d %s: %s\n", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)), "ShutdownThread", str2));
                    ShutdownThread.dlgAnim.appendTextLog(sb.toString());
                }
                return i;
            }
            if (ShutdownThread.dlgAnim != null && !ShutdownThread.BIN_TYPE_USER) {
                StringBuilder sb2 = new StringBuilder();
                Calendar calendar2 = Calendar.getInstance();
                sb2.append(String.format("%02d-%02d %02d:%02d:%02d.%03d %s: %s\n", Integer.valueOf(calendar2.get(2) + 1), Integer.valueOf(calendar2.get(5)), Integer.valueOf(calendar2.get(11)), Integer.valueOf(calendar2.get(12)), Integer.valueOf(calendar2.get(13)), Integer.valueOf(calendar2.get(14)), "ShutdownThread", str2));
                ShutdownThread.dlgAnim.appendTextLog(sb2.toString());
            }
            return i;
        }
    }

    public class getDelayDumpstate extends Handler {
        public static Runnable delayDumpRaunnable;
        public static getDelayDumpstate delayhandler;

        public getDelayDumpstate(Looper looper) {
            super(looper);
        }

        public static void startState() {
            if (delayhandler == null) {
                android.util.Slog.i("ShutdownDelay", "Start handler");
                delayhandler = new getDelayDumpstate(Looper.getMainLooper());
            }
            delayDumpRaunnable = new Runnable() { // from class: com.android.server.power.ShutdownThread.getDelayDumpstate.1
                @Override // java.lang.Runnable
                public void run() {
                    if (ShutdownThread.BIN_TYPE_PRODUCTSHIP || "recovery".equals(ShutdownThread.mReason) || "recovery-update".equals(ShutdownThread.mReason)) {
                        android.util.Slog.i("ShutdownDelay", "!@ShutdownThread.run() : Checking timeout, done. Try force shutdown again.");
                        ShutdownThread.rebootOrShutdown(ShutdownThread.sInstance.mContext, ShutdownThread.mReboot, ShutdownThread.mReason);
                    } else {
                        android.util.Slog.i("ShutdownDelay", "!@ShutdownThread.run() : checking timeout, done.");
                    }
                }
            };
            if ("recovery".equals(ShutdownThread.mReason) || "recovery-update".equals(ShutdownThread.mReason)) {
                delayhandler.postDelayed(delayDumpRaunnable, 900000L);
            } else {
                delayhandler.postDelayed(delayDumpRaunnable, 120000L);
            }
        }
    }

    public static boolean isFOTAAvailable(Context context) {
        return context != null && Settings.System.getInt(context.getContentResolver(), "attfota_forceinstall_FN_sim", 0) == 1;
    }
}
