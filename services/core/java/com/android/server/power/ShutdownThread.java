package com.android.server.power;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.BroadcastOptions;
import android.app.IActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.pm.PackageManagerInternal;
import android.hardware.usb.UsbManager;
import android.media.AudioAttributes;
import android.net.INetd;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RecoverySystem;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.SystemVibrator;
import android.os.UserManager;
import android.sysprop.CrashRecoveryProperties;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.TimingsTraceLog;
import android.view.SurfaceControl;
import android.widget.TextView;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.RescueParty;
import com.android.server.power.shutdown.AnimationPlayer;
import com.android.server.power.shutdown.ShutdownDialog;
import com.android.server.power.shutdown.SoundPlayer;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.restriction.IRestrictionPolicy;
import com.samsung.android.sepunion.SemUnionManager;
import com.samsung.android.service.HermesService.IHermesService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.function.BiFunction;

public final class ShutdownThread extends Thread {
    static final int DEFAULT_SHUTDOWN_VIBRATE_MS = 500;
    public static long currentTimeMillisStart;
    public static LogFileWriter logFileWriter;
    public static String mReason;
    public static boolean mReboot;
    public static boolean mRebootHasProgressBar;
    public static boolean mRebootSafeMode;
    public static boolean sIsStarted;
    public static ShutdownDialog shutdownDialog;
    public boolean mActionDone;
    public final Object mActionDoneSync = new Object();
    public Context mContext;
    public PowerManager.WakeLock mCpuWakeLock;
    public AnonymousClass1 mHandler;
    public final Injector mInjector;
    public PowerManager mPowerManager;
    public ProgressDialog mProgressDialog;
    public PowerManager.WakeLock mScreenWakeLock;
    public static final Object sIsStartedGuard = new Object();
    public static final ShutdownThread sInstance = new ShutdownThread(new Injector());
    public static final AudioAttributes VIBRATION_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
    public static final ArrayMap TRON_METRICS = new ArrayMap();
    public static final String METRIC_SYSTEM_SERVER = "shutdown_system_server";
    public static final String METRIC_SEND_BROADCAST = "shutdown_send_shutdown_broadcast";
    public static final String METRIC_AM = "shutdown_activity_manager";
    public static final String METRIC_PM = "shutdown_package_manager";
    public static final String METRIC_RADIOS = "shutdown_radios";
    public static final String METRIC_RADIO = "shutdown_radio";
    public static final String METRIC_SHUTDOWN_TIME_START = "begin_shutdown";
    public static final boolean BIN_TYPE_USER = "user".equals(SystemProperties.get("ro.build.type"));
    public static final boolean BIN_TYPE_DEBUG_LOW = "0x4f4c".equals(SystemProperties.get("ro.boot.debug_level"));
    public static final boolean BIN_TYPE_PRODUCTSHIP = !Debug.semIsProductDev();
    public static boolean systemRequest = false;

    /* renamed from: com.android.server.power.ShutdownThread$1, reason: invalid class name */
    public final class AnonymousClass1 extends Handler {
        public static ShutdownThread$getDelayDumpstate$1 delayDumpRaunnable;
        public static AnonymousClass1 delayhandler;
    }

    class Injector {
    }

    public final class LogFileWriter {
        public FileOutputStream latestShutdownProfile;
        public FileOutputStream shutdownProfile;
    }

    public abstract class MYLOG {
        public static void i(String str, String str2) {
            try {
                android.util.Slog.i(str, str2);
                ShutdownThread.wirteLogFileWriter(str2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ShutdownThread.shutdownDialog == null || ShutdownThread.BIN_TYPE_USER) {
                return;
            }
            Calendar calendar = Calendar.getInstance();
            final String format = String.format("%02d-%02d %02d:%02d:%02d.%03d %s: %s\n", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)), "ShutdownThread", str2);
            final ShutdownDialog shutdownDialog = ShutdownThread.shutdownDialog;
            Handler handler = shutdownDialog.logHandler;
            if (handler == null || shutdownDialog.mLogView == null) {
                return;
            }
            handler.post(new Runnable() { // from class: com.android.server.power.shutdown.ShutdownDialog$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ShutdownDialog shutdownDialog2 = ShutdownDialog.this;
                    String str3 = format;
                    TextView textView = shutdownDialog2.mLogView;
                    if (textView != null) {
                        textView.append(str3);
                    }
                }
            });
        }
    }

    public ShutdownThread(Injector injector) {
        this.mInjector = injector;
    }

    public static void closeLogFileWriter() {
        if (logFileWriter != null) {
            android.util.Slog.i("ShutdownThread", "logFileWriter saveAndClose logFileWriter : " + logFileWriter);
            LogFileWriter logFileWriter2 = logFileWriter;
            FileOutputStream fileOutputStream = logFileWriter2.shutdownProfile;
            try {
                try {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.flush();
                            logFileWriter2.shutdownProfile.close();
                            logFileWriter2.shutdownProfile = null;
                            FileOutputStream fileOutputStream2 = logFileWriter2.latestShutdownProfile;
                            if (fileOutputStream2 != null) {
                                fileOutputStream2.flush();
                                logFileWriter2.latestShutdownProfile.close();
                                logFileWriter2.latestShutdownProfile = null;
                            }
                            FileOutputStream fileOutputStream3 = logFileWriter2.shutdownProfile;
                            if (fileOutputStream3 != null) {
                                fileOutputStream3.close();
                                logFileWriter2.shutdownProfile = null;
                            }
                            FileOutputStream fileOutputStream4 = logFileWriter2.latestShutdownProfile;
                            if (fileOutputStream4 != null) {
                                fileOutputStream4.close();
                                logFileWriter2.latestShutdownProfile = null;
                            }
                        } catch (IOException e) {
                            android.util.Slog.e("ShutdownThread", "LogFileWriter.saveAndClose fail", e);
                            FileOutputStream fileOutputStream5 = logFileWriter2.shutdownProfile;
                            if (fileOutputStream5 != null) {
                                fileOutputStream5.close();
                                logFileWriter2.shutdownProfile = null;
                            }
                            FileOutputStream fileOutputStream6 = logFileWriter2.latestShutdownProfile;
                            if (fileOutputStream6 != null) {
                                fileOutputStream6.close();
                                logFileWriter2.latestShutdownProfile = null;
                            }
                        }
                    }
                } catch (Throwable th) {
                    try {
                        FileOutputStream fileOutputStream7 = logFileWriter2.shutdownProfile;
                        if (fileOutputStream7 != null) {
                            fileOutputStream7.close();
                            logFileWriter2.shutdownProfile = null;
                        }
                        FileOutputStream fileOutputStream8 = logFileWriter2.latestShutdownProfile;
                        if (fileOutputStream8 != null) {
                            fileOutputStream8.close();
                            logFileWriter2.latestShutdownProfile = null;
                        }
                    } catch (IOException e2) {
                        android.util.Slog.e("ShutdownThread", "LogFileWriter.saveAndClose error", e2);
                    }
                    throw th;
                }
            } catch (IOException e3) {
                android.util.Slog.e("ShutdownThread", "LogFileWriter.saveAndClose error", e3);
            }
            logFileWriter = null;
        }
    }

    public static void get() {
        synchronized (ShutdownThread.class) {
        }
    }

    public static synchronized ShutdownDialog getShutdownDialog(Context context) {
        ShutdownDialog shutdownDialog2;
        synchronized (ShutdownThread.class) {
            try {
                if (shutdownDialog == null) {
                    shutdownDialog = new ShutdownDialog(context);
                }
                shutdownDialog2 = shutdownDialog;
            } catch (Throwable th) {
                throw th;
            }
        }
        return shutdownDialog2;
    }

    public static void metricEnded(String str) {
        ArrayMap arrayMap = TRON_METRICS;
        synchronized (arrayMap) {
            arrayMap.put(str, Long.valueOf(SystemClock.elapsedRealtime() + ((Long) arrayMap.get(str)).longValue()));
        }
    }

    public static void metricStarted(String str) {
        ArrayMap arrayMap = TRON_METRICS;
        synchronized (arrayMap) {
            arrayMap.put(str, Long.valueOf(SystemClock.elapsedRealtime() * (-1)));
        }
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
        shutdownInner(context);
    }

    public static void rebootOrShutdown(Context context, String str, boolean z) {
        if (SystemProperties.get("ro.boot.debug_level", "0x4f4c").equals("0x494d")) {
            long elapsedRealtime = (60 * 1000) + SystemClock.elapsedRealtime();
            while (true) {
                if (!INetd.IF_FLAG_RUNNING.equals(SystemProperties.get("init.svc.bugreportd")) && !INetd.IF_FLAG_RUNNING.equals(SystemProperties.get("init.svc.bugreportm"))) {
                    break;
                }
                if (elapsedRealtime - SystemClock.elapsedRealtime() <= 0) {
                    android.util.Slog.w("ShutdownThread", "!@Dumpstate finish wait timed out");
                    break;
                } else {
                    android.util.Slog.d("ShutdownThread", "!@wait for finish Dumpstate: sleep 1000ms");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException unused) {
                        android.util.Slog.e("ShutdownThread", "InterruptedException");
                    }
                }
            }
        }
        if (z) {
            MYLOG.i("ShutdownThread", "!@Rebooting, reason: " + str);
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
                    } catch (InterruptedException unused2) {
                    }
                }
            } catch (Exception e) {
                Log.w("ShutdownThread", "Failed to vibrate during shutdown.", e);
            }
        }
        MYLOG.i("ShutdownThread", "!@Performing low-level shutdown...");
        closeLogFileWriter();
        SimpleDateFormat simpleDateFormat = PowerManagerService.DATE_FORMAT;
        Slog.d("PowerManagerService", "[api] lowLevelShutdown: " + PowerManagerUtil.callerInfoToString(true));
        Slog.saveLogAsFile();
        if (str == null) {
            str = "";
        }
        SystemProperties.set("sys.powerctl", "shutdown,".concat(str));
    }

    public static void rebootSafeMode(Context context) {
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
        shutdownInner(context);
    }

    public static ProgressDialog showShutdownDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        String str = mReason;
        if (str == null || !str.startsWith("recovery-update")) {
            String str2 = mReason;
            if (str2 != null && str2.equals("recovery")) {
                int i = RescueParty.LEVEL_ISRB_BOOT;
                if (((Boolean) CrashRecoveryProperties.attemptingFactoryReset().orElse(Boolean.FALSE)).booleanValue() || RescueParty.isRebootPropertySet()) {
                    progressDialog.setTitle(context.getText(17042469));
                    progressDialog.setMessage(context.getText(17043035));
                    progressDialog.setIndeterminate(true);
                } else {
                    if (showSysuiReboot()) {
                        return null;
                    }
                    progressDialog.setTitle(context.getText(17042522));
                    progressDialog.setMessage(context.getText(17042521));
                    progressDialog.setIndeterminate(true);
                }
            } else {
                if (showSysuiReboot()) {
                    return null;
                }
                progressDialog.setTitle(context.getText(17042469));
                progressDialog.setMessage(context.getText(17043035));
                progressDialog.setIndeterminate(true);
            }
        } else {
            mRebootHasProgressBar = RecoverySystem.UNCRYPT_PACKAGE_FILE.exists() && !RecoverySystem.BLOCK_MAP_FILE.exists();
            progressDialog.setTitle(context.getText(17042526));
            if (mRebootHasProgressBar) {
                progressDialog.setMax(100);
                progressDialog.setProgress(0);
                progressDialog.setIndeterminate(false);
                if (!context.getResources().getBoolean(R.bool.config_sms_force_7bit_encoding)) {
                    progressDialog.setProgressPercentFormat(null);
                }
                progressDialog.setProgressNumberFormat(null);
                progressDialog.setProgressStyle(1);
                progressDialog.setMessage(context.getText(17042524));
            } else {
                if (showSysuiReboot()) {
                    return null;
                }
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(context.getText(17042525));
            }
        }
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setType(2009);
        progressDialog.show();
        return progressDialog;
    }

    public static boolean showSysuiReboot() {
        IStatusBar iStatusBar;
        try {
            StatusBarManagerInternal statusBarManagerInternal = (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
            boolean z = mReboot;
            String str = mReason;
            StatusBarManagerService.AnonymousClass2 anonymousClass2 = (StatusBarManagerService.AnonymousClass2) statusBarManagerInternal;
            if (!StatusBarManagerService.this.mContext.getResources().getBoolean(R.bool.config_sms_utf8_support) || (iStatusBar = StatusBarManagerService.this.mBar) == null) {
                return false;
            }
            iStatusBar.showShutdownUi(z, str);
            return true;
        } catch (RemoteException | Exception unused) {
            return false;
        }
    }

    public static void shutdown(Context context, String str, boolean z) {
        android.util.Slog.i("ShutdownThread", "shutdown reason : " + str + ", confirm : " + z);
        if (sIsStarted) {
            Log.d("ShutdownThread", "!@Request to shutdown already running, returning.");
            return;
        }
        mReboot = false;
        mRebootSafeMode = false;
        mReason = str;
        shutdownInner(context);
    }

    /* JADX WARN: Code restructure failed: missing block: B:142:0x0192, code lost:
    
        r15 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0193, code lost:
    
        android.util.Slog.e("ShutdownThread", "InterruptedException", r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x017d, code lost:
    
        r15 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0184, code lost:
    
        r15.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0187, code lost:
    
        if (r5 != null) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0189, code lost:
    
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0174, code lost:
    
        r15 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0175, code lost:
    
        r15.printStackTrace();
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0182, code lost:
    
        r15 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0183, code lost:
    
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x017f, code lost:
    
        r15 = th;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void shutdownInner(android.content.Context r15) {
        /*
            Method dump skipped, instructions count: 1095
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.ShutdownThread.shutdownInner(android.content.Context):void");
    }

    public static void systemShutdown(Context context, String str) {
        android.util.Slog.i("ShutdownThread", "systemShutdown - reason: ".concat(str));
        if (sIsStarted) {
            Log.d("ShutdownThread", "!@Request to shutdown already running, returning.");
            return;
        }
        mReboot = false;
        mRebootSafeMode = false;
        mReason = str;
        systemRequest = true;
        shutdownInner(context);
    }

    public static void wirteLogFileWriter(String str) {
        LogFileWriter logFileWriter2 = logFileWriter;
        if (logFileWriter2 != null) {
            if (logFileWriter2.shutdownProfile == null) {
                android.util.Slog.i("ShutdownThread", "shutdownProfile is null");
                return;
            }
            Calendar calendar = Calendar.getInstance();
            String format = String.format("%02d-%02d %02d:%02d:%02d.%03d %s: %s\n", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)), "ShutdownThread", str);
            try {
                logFileWriter2.shutdownProfile.write(format.getBytes());
                FileOutputStream fileOutputStream = logFileWriter2.latestShutdownProfile;
                if (fileOutputStream != null) {
                    fileOutputStream.write(format.getBytes());
                }
            } catch (IOException e) {
                android.util.Slog.e("ShutdownThread", "LogFileWriter.write fail", e);
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void playShutdownVibration(android.content.Context r10) {
        /*
            r9 = this;
            com.android.server.power.ShutdownThread$Injector r0 = r9.mInjector
            r0.getClass()
            android.os.SystemVibrator r0 = new android.os.SystemVibrator
            r0.<init>(r10)
            boolean r1 = r0.hasVibrator()
            if (r1 != 0) goto L11
            return
        L11:
            com.android.server.power.ShutdownThread$Injector r1 = r9.mInjector
            r1.getClass()
            android.content.res.Resources r10 = r10.getResources()
            r1 = 17040177(0x1040331, float:2.424686E-38)
            java.lang.String r10 = r10.getString(r1)
            boolean r1 = android.text.TextUtils.isEmpty(r10)
            java.lang.String r2 = "ShutdownThread"
            if (r1 != 0) goto L3d
            java.io.FileReader r1 = new java.io.FileReader     // Catch: java.lang.Exception -> L37
            r1.<init>(r10)     // Catch: java.lang.Exception -> L37
            android.os.vibrator.persistence.ParsedVibration r10 = android.os.vibrator.persistence.VibrationXmlParser.parseDocument(r1)     // Catch: java.lang.Exception -> L37
            android.os.VibrationEffect r10 = r10.resolve(r0)     // Catch: java.lang.Exception -> L37
            goto L3e
        L37:
            r10 = move-exception
            java.lang.String r1 = "Error parsing default shutdown vibration effect."
            android.util.Log.e(r2, r1, r10)
        L3d:
            r10 = 0
        L3e:
            r1 = -1
            r3 = 500(0x1f4, double:2.47E-321)
            if (r10 != 0) goto L48
            android.os.VibrationEffect r10 = android.os.VibrationEffect.createOneShot(r3, r1)
            goto L5e
        L48:
            long r5 = r10.getDuration()
            r7 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 != 0) goto L5e
            java.lang.String r10 = "The parsed shutdown vibration is indefinite."
            android.util.Log.w(r2, r10)
            android.os.VibrationEffect r10 = android.os.VibrationEffect.createOneShot(r3, r1)
        L5e:
            r1 = 18
            android.os.VibrationAttributes r1 = android.os.VibrationAttributes.createForUsage(r1)
            r0.vibrate(r10, r1)
            long r0 = r10.getDuration()
            com.android.server.power.ShutdownThread$Injector r9 = r9.mInjector
            r5 = 0
            int r10 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r10 >= 0) goto L74
            goto L75
        L74:
            r3 = r0
        L75:
            r9.getClass()
            java.lang.Thread.sleep(r3)     // Catch: java.lang.InterruptedException -> L7b
        L7b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.power.ShutdownThread.playShutdownVibration(android.content.Context):void");
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        SoundPlayer soundPlayer;
        Context context;
        File file;
        TimingsTraceLog timingsTraceLog = new TimingsTraceLog("ShutdownTiming", 524288L);
        timingsTraceLog.traceBegin("SystemServerShutdown");
        ArrayMap arrayMap = TRON_METRICS;
        synchronized (arrayMap) {
            arrayMap.put(METRIC_SHUTDOWN_TIME_START, Long.valueOf(System.currentTimeMillis()));
        }
        metricStarted(METRIC_SYSTEM_SERVER);
        SurfaceControl.notifyShutdown();
        String str = mReboot ? "1" : "0";
        String str2 = mReason;
        if (str2 == null) {
            str2 = "";
        }
        SystemProperties.set("sys.shutdown.requested", str.concat(str2));
        if (mRebootSafeMode) {
            SystemProperties.set("persist.sys.safemode", "1");
        }
        timingsTraceLog.traceBegin("DumpPreRebootInfo");
        try {
            android.util.Slog.i("ShutdownThread", "Logging pre-reboot information...");
            context = this.mContext;
            String[] strArr = PreRebootLogger.BUFFERS_TO_DUMP;
            file = new File(Environment.getDataMiscDirectory(), "prereboot");
        } catch (Exception e) {
            android.util.Slog.e("ShutdownThread", "Failed to log pre-reboot information", e);
        }
        if (!file.exists() || !file.isDirectory()) {
            throw new UnsupportedOperationException("Pre-reboot dump directory not found");
        }
        PreRebootLogger.log(context, file);
        timingsTraceLog.traceEnd();
        MYLOG.i("ShutdownThread", "!@Shutting down Hermes manager...");
        Thread thread = new Thread() { // from class: com.android.server.power.ShutdownThread.4
            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
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
                } catch (Exception e2) {
                    android.util.Slog.e("ShutdownThread", "!@Exception during hermesservice shutdown", e2);
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
        metricStarted(METRIC_SEND_BROADCAST);
        timingsTraceLog.traceBegin("SendShutdownBroadcast");
        MYLOG.i("ShutdownThread", "!@Sending shutdown broadcast...");
        this.mActionDone = false;
        ((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).broadcastIntentWithCallback(BatteryService$$ExternalSyntheticOutline0.m(1342177280, "android.intent.action.ACTION_SHUTDOWN"), new IIntentReceiver.Stub() { // from class: com.android.server.power.ShutdownThread.2
            public final void performReceive(Intent intent, int i, String str3, Bundle bundle, boolean z, boolean z2, int i2) {
                final ShutdownThread shutdownThread = ShutdownThread.this;
                shutdownThread.mHandler.post(new Runnable() { // from class: com.android.server.power.ShutdownThread$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ShutdownThread shutdownThread2 = ShutdownThread.this;
                        synchronized (shutdownThread2.mActionDoneSync) {
                            shutdownThread2.mActionDone = true;
                            shutdownThread2.mActionDoneSync.notifyAll();
                        }
                    }
                });
            }
        }, (String[]) null, -1, (int[]) null, (BiFunction) null, BroadcastOptions.makeBasic().setDeferralPolicy(2).toBundle());
        long elapsedRealtime = SystemClock.elapsedRealtime() + 10000;
        synchronized (this.mActionDoneSync) {
            while (true) {
                try {
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
                        } catch (InterruptedException unused2) {
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(2, null);
        }
        long currentTimeMillis = System.currentTimeMillis() - currentTimeMillisStart;
        String valueOf = String.valueOf(SystemProperties.get("dev.shutdownbroadcast.on", ""));
        if (!(BIN_TYPE_PRODUCTSHIP && BIN_TYPE_DEBUG_LOW) && currentTimeMillis >= 5000) {
            MYLOG.i("ShutdownThread", "!@shutdown is too slow, elapsed time from POWEROFF START to BROADCAST_SHUTDOWN is " + currentTimeMillis);
        } else {
            "On".equalsIgnoreCase(valueOf);
        }
        timingsTraceLog.traceEnd();
        metricEnded(METRIC_SEND_BROADCAST);
        MYLOG.i("ShutdownThread", "!@Shutting down activity manager...");
        timingsTraceLog.traceBegin("ShutdownActivityManager");
        metricStarted(METRIC_AM);
        IActivityManager asInterface = IActivityManager.Stub.asInterface(ServiceManager.checkService("activity"));
        if (asInterface != null) {
            try {
                asInterface.shutdown(10000);
            } catch (RemoteException unused3) {
            }
        }
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(4, null);
        }
        timingsTraceLog.traceEnd();
        metricEnded(METRIC_AM);
        MYLOG.i("ShutdownThread", "!@Shutting down package manager...");
        timingsTraceLog.traceBegin("ShutdownPackageManager");
        String str3 = METRIC_PM;
        metricStarted(str3);
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        if (packageManagerInternal != null) {
            packageManagerInternal.shutdown();
        }
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(6, null);
        }
        timingsTraceLog.traceEnd();
        metricEnded(str3);
        timingsTraceLog.traceBegin("ShutdownRadios");
        metricStarted(METRIC_RADIOS);
        long j = 12000;
        final long elapsedRealtime3 = SystemClock.elapsedRealtime() + j;
        final boolean[] zArr = new boolean[1];
        Thread thread2 = new Thread() { // from class: com.android.server.power.ShutdownThread.5
            public final /* synthetic */ int val$timeout = 12000;

            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
                MYLOG.i("ShutdownThread", "!@Start shutdown radios");
                TimingsTraceLog timingsTraceLog2 = new TimingsTraceLog("ShutdownTiming", 524288L);
                TelephonyManager telephonyManager = (TelephonyManager) ShutdownThread.this.mContext.getSystemService(TelephonyManager.class);
                boolean z = telephonyManager == null || !telephonyManager.isAnyRadioPoweredOn();
                if (!z) {
                    Log.w("ShutdownThread", "Turning off cellular radios...");
                    ShutdownThread.metricStarted(ShutdownThread.METRIC_RADIO);
                    telephonyManager.shutdownAllRadios();
                }
                MYLOG.i("ShutdownThread", "Waiting for Radio...");
                long j2 = elapsedRealtime3;
                long elapsedRealtime4 = SystemClock.elapsedRealtime();
                while (true) {
                    if (j2 - elapsedRealtime4 <= 0) {
                        return;
                    }
                    if (ShutdownThread.mRebootHasProgressBar) {
                        ShutdownThread.sInstance.setRebootProgress(((int) ((((r8 - r6) * 1.0d) * 12.0d) / this.val$timeout)) + 6, null);
                    }
                    if (!z && (!telephonyManager.isAnyRadioPoweredOn())) {
                        MYLOG.i("ShutdownThread", "!@Radio turned off.");
                        String str4 = ShutdownThread.METRIC_RADIO;
                        ShutdownThread.metricEnded(str4);
                        timingsTraceLog2.logDuration("ShutdownRadio", ((Long) ShutdownThread.TRON_METRICS.get(str4)).longValue());
                    }
                    if (z) {
                        Log.i("ShutdownThread", "Radio shutdown complete.");
                        zArr[0] = true;
                        return;
                    }
                    MYLOG.i("ShutdownThread", "!@before sleep");
                    SystemClock.sleep(100L);
                    MYLOG.i("ShutdownThread", "!@after sleep");
                    MYLOG.i("ShutdownThread", "!@[Phone off retry:" + SystemClock.elapsedRealtime() + "] : " + elapsedRealtime3 + " radio=" + z);
                    j2 = elapsedRealtime3;
                    elapsedRealtime4 = SystemClock.elapsedRealtime();
                }
            }
        };
        thread2.start();
        try {
            thread2.join(j);
        } catch (InterruptedException unused4) {
        }
        if (!zArr[0]) {
            MYLOG.i("ShutdownThread", "Timed out waiting for Radio shutdown.");
        }
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(18, null);
        }
        timingsTraceLog.traceEnd();
        metricEnded(METRIC_RADIOS);
        if (mRebootHasProgressBar) {
            sInstance.setRebootProgress(20, null);
            Log.i("ShutdownThread", "Calling uncrypt and monitoring the progress...");
            final ?? r0 = new RecoverySystem.ProgressListener() { // from class: com.android.server.power.ShutdownThread.6
                @Override // android.os.RecoverySystem.ProgressListener
                public final void onProgress(int i) {
                    if (i >= 0 && i < 100) {
                        ShutdownThread.sInstance.setRebootProgress(((int) ((i * 80.0d) / 100.0d)) + 20, ShutdownThread.this.mContext.getText(17042523));
                    } else if (i == 100) {
                        ShutdownThread.sInstance.setRebootProgress(i, ShutdownThread.this.mContext.getText(17042525));
                    }
                }
            };
            final boolean[] zArr2 = {false};
            Thread thread3 = new Thread() { // from class: com.android.server.power.ShutdownThread.7
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    int i = 3;
                    while (i > 0) {
                        try {
                            RecoverySystem.processPackage(ShutdownThread.this.mContext, new File(FileUtils.readTextFile(RecoverySystem.UNCRYPT_PACKAGE_FILE, 0, null)), r0);
                            MYLOG.i("ShutdownThread", "!@uncrypt finished. No need to retry uncrypt");
                            break;
                        } catch (Exception e2) {
                            i--;
                            MYLOG.i("ShutdownThread", "!@Error uncrypting file : " + Log.getStackTraceString(e2));
                        }
                    }
                    zArr2[0] = true;
                }
            };
            thread3.start();
            try {
                thread3.join(900000L);
            } catch (InterruptedException unused5) {
            }
            if (!zArr2[0]) {
                Log.w("ShutdownThread", "Timed out waiting for uncrypt.");
                try {
                    FileUtils.stringToFile(RecoverySystem.UNCRYPT_STATUS_FILE, String.format("uncrypt_time: %d\nuncrypt_error: %d\n", Integer.valueOf(FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT), 100));
                } catch (IOException e2) {
                    Log.e("ShutdownThread", "Failed to write timeout message to uncrypt status", e2);
                }
            }
        }
        MYLOG.i("ShutdownThread", "!@waitForAnimEnd");
        if ("ON".equals(SystemProperties.get("service.poweranimation.on", ""))) {
            MYLOG.i("ShutdownThread", "!@wait for PNG animation end");
            long elapsedRealtime4 = (10 * 1000) + SystemClock.elapsedRealtime();
            while (true) {
                if ("END".equals(SystemProperties.get("dev.shutdownanimation.end"))) {
                    break;
                }
                if (elapsedRealtime4 - SystemClock.elapsedRealtime() <= 0) {
                    android.util.Slog.w("ShutdownThread", "!@Animation finish wait timed out");
                    break;
                } else {
                    android.util.Slog.d("ShutdownThread", "!@wait for finish : sleep 100ms");
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException unused6) {
                        android.util.Slog.e("ShutdownThread", "InterruptedException");
                    }
                }
            }
        } else {
            ShutdownDialog shutdownDialog2 = shutdownDialog;
            if (shutdownDialog2 != null && shutdownDialog2.isShowing()) {
                MYLOG.i("ShutdownThread", "!@wait for animation end");
                ShutdownDialog shutdownDialog3 = shutdownDialog;
                shutdownDialog3.getClass();
                long elapsedRealtime5 = (10 * 1000) + SystemClock.elapsedRealtime();
                while (true) {
                    AnimationPlayer animationPlayer = shutdownDialog3.animationPlayer;
                    if ((animationPlayer == null || !animationPlayer.isPlaying()) && ((soundPlayer = shutdownDialog3.soundPlayer) == null || !soundPlayer.isPlaying())) {
                        break;
                    }
                    if (elapsedRealtime5 - SystemClock.elapsedRealtime() <= 0) {
                        android.util.Slog.w("Shutdown-ShutdownDialog", "!@Animation finish wait timed out");
                        break;
                    } else {
                        android.util.Slog.d("Shutdown-ShutdownDialog", "!@wait for finish : sleep 100ms");
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException unused7) {
                            android.util.Slog.e("Shutdown-ShutdownDialog", "InterruptedException");
                        }
                    }
                }
            }
        }
        ((UsbManager) this.mContext.getSystemService("usb")).enableUsbDataSignal(false);
        StringBuilder sb = new StringBuilder("!@run, ");
        sb.append(mReboot ? "reboot" : "shutdown");
        sb.append(" requested reason=");
        String str4 = mReason;
        if (str4 == null) {
            str4 = "null";
        }
        sb.append(str4);
        MYLOG.i("ShutdownThread", sb.toString());
        rebootOrShutdown(this.mContext, mReason, mReboot);
    }

    public final void setRebootProgress(final int i, final CharSequence charSequence) {
        this.mHandler.post(new Runnable() { // from class: com.android.server.power.ShutdownThread.3
            @Override // java.lang.Runnable
            public final void run() {
                ProgressDialog progressDialog = ShutdownThread.this.mProgressDialog;
                if (progressDialog != null) {
                    progressDialog.setProgress(i);
                    CharSequence charSequence2 = charSequence;
                    if (charSequence2 != null) {
                        ShutdownThread.this.mProgressDialog.setMessage(charSequence2);
                    }
                }
            }
        });
    }
}
