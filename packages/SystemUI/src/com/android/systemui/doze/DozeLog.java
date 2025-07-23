package com.android.systemui.doze;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.TimeUtils;
import android.view.Display;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dumpable;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.subscreen.SubRoom;
import java.io.PrintWriter;
import java.lang.reflect.Array;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class DozeLog implements Dumpable {
    public final SummaryStats mEmergencyCallStats;
    public final DozeLogger mLogger;
    public final SummaryStats mNotificationPulseStats;
    public final SummaryStats mPickupPulseNearVibrationStats;
    public final SummaryStats mPickupPulseNotNearVibrationStats;
    public boolean mPulsing;
    public final SummaryStats mScreenOnNotPulsingStats;
    public final SummaryStats mScreenOnPulsingStats;
    public final KeyguardUpdateMonitorCallback mKeyguardCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.doze.DozeLog.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onEmergencyCallAction() {
            DozeLog dozeLog = DozeLog.this;
            DozeLogger dozeLogger = dozeLog.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(13);
            LogBuffer logBuffer = dozeLogger.buffer;
            logBuffer.commit(logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null));
            dozeLog.mEmergencyCallStats.mCount++;
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onFinishedGoingToSleep(int i) {
            DozeLogger dozeLogger = DozeLog.this.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(8);
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).int1 = i;
            logBuffer.commit(obtain);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
            DozeLogger dozeLogger = DozeLog.this.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(10);
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).bool1 = z;
            logBuffer.commit(obtain);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardVisibilityChanged(boolean z) {
            DozeLog dozeLog = DozeLog.this;
            DozeLogger dozeLogger = dozeLog.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).bool1 = z;
            logBuffer.commit(obtain);
            if (z) {
                return;
            }
            dozeLog.mPulsing = false;
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStartedWakingUp() {
            DozeLog dozeLog = DozeLog.this;
            boolean z = dozeLog.mPulsing;
            DozeLogger dozeLogger = dozeLog.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(2);
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).bool1 = z;
            logBuffer.commit(obtain);
            (dozeLog.mPulsing ? dozeLog.mScreenOnPulsingStats : dozeLog.mScreenOnNotPulsingStats).mCount++;
            dozeLog.mPulsing = false;
        }
    };
    public final long mSince = System.currentTimeMillis();
    public final SummaryStats[][] mProxStats = (SummaryStats[][]) Array.newInstance((Class<?>) SummaryStats.class, 13, 2);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SummaryStats {
        public int mCount;

        public /* synthetic */ SummaryStats(DozeLog dozeLog, int i) {
            this();
        }

        public final void dump(PrintWriter printWriter, String str) {
            if (this.mCount == 0) {
                return;
            }
            printWriter.print("    ");
            printWriter.print(str);
            printWriter.print(": n=");
            printWriter.print(this.mCount);
            printWriter.print(" (");
            printWriter.print((this.mCount / (System.currentTimeMillis() - DozeLog.this.mSince)) * 1000.0d * 60.0d * 60.0d);
            printWriter.print("/hr)");
            printWriter.println();
        }

        private SummaryStats() {
        }
    }

    public DozeLog(KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, DozeLogger dozeLogger) {
        this.mLogger = dozeLogger;
        int i = 0;
        this.mPickupPulseNearVibrationStats = new SummaryStats(this, i);
        this.mPickupPulseNotNearVibrationStats = new SummaryStats(this, i);
        this.mNotificationPulseStats = new SummaryStats(this, i);
        this.mScreenOnPulsingStats = new SummaryStats(this, i);
        this.mScreenOnNotPulsingStats = new SummaryStats(this, i);
        this.mEmergencyCallStats = new SummaryStats(this, i);
        for (int i2 = 0; i2 < 13; i2++) {
            this.mProxStats[i2][0] = new SummaryStats(this, i);
            this.mProxStats[i2][1] = new SummaryStats(this, i);
        }
        if (keyguardUpdateMonitor != null) {
            keyguardUpdateMonitor.registerCallback(this.mKeyguardCallback);
        }
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "DumpStats", this);
    }

    public static String reasonToString(int i) {
        switch (i) {
            case 0:
                return "intent";
            case 1:
                return SubRoom.EXTRA_VALUE_NOTIFICATION;
            case 2:
                return "sigmotion";
            case 3:
                return "pickup";
            case 4:
                return "doubletap";
            case 5:
                return "longpress";
            case 6:
                return "docking";
            case 7:
                return "presence-wakeup";
            case 8:
                return "reach-wakelockscreen";
            case 9:
                return "tap";
            case 10:
                return "udfps";
            case 11:
                return "quickPickup";
            case 12:
                return "fingerprint-triggered";
            default:
                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "invalid reason: "));
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        synchronized (DozeLog.class) {
            try {
                printWriter.print("  Doze summary stats (for ");
                TimeUtils.formatDuration(System.currentTimeMillis() - this.mSince, printWriter);
                printWriter.println("):");
                this.mPickupPulseNearVibrationStats.dump(printWriter, "Pickup pulse (near vibration)");
                this.mPickupPulseNotNearVibrationStats.dump(printWriter, "Pickup pulse (not near vibration)");
                this.mNotificationPulseStats.dump(printWriter, "Notification pulse");
                this.mScreenOnPulsingStats.dump(printWriter, "Screen on (pulsing)");
                this.mScreenOnNotPulsingStats.dump(printWriter, "Screen on (not pulsing)");
                this.mEmergencyCallStats.dump(printWriter, "Emergency call");
                for (int i = 0; i < 13; i++) {
                    String reasonToString = reasonToString(i);
                    this.mProxStats[i][0].dump(printWriter, "Proximity near (" + reasonToString + ")");
                    this.mProxStats[i][1].dump(printWriter, "Proximity far (" + reasonToString + ")");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void traceDisplayState(int i, boolean z) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$$ExternalSyntheticLambda3 dozeLogger$$ExternalSyntheticLambda3 = new DozeLogger$$ExternalSyntheticLambda3(8);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda3, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = Display.stateToString(i);
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void traceDozeScreenBrightness(int i, boolean z) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$$ExternalSyntheticLambda3 dozeLogger$$ExternalSyntheticLambda3 = new DozeLogger$$ExternalSyntheticLambda3(9);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda3, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void traceDozeScreenBrightnessFloat(float f, boolean z) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(12);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
        double d = f;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.double1 = d;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void traceDozing(boolean z) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(6);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
        this.mPulsing = false;
    }

    public final void tracePostureChanged(int i, String str) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$$ExternalSyntheticLambda3 dozeLogger$$ExternalSyntheticLambda3 = new DozeLogger$$ExternalSyntheticLambda3(1);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda3, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.str1 = str;
        logBuffer.commit(obtain);
    }

    public final void tracePulseDropped(String str, DozeMachine.State state) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(29);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = state != null ? state.name() : null;
        logBuffer.commit(obtain);
    }

    public final void tracePulseEvent(int i, String str, boolean z) {
        String reasonToString = reasonToString(i);
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        DozeLogger$$ExternalSyntheticLambda3 dozeLogger$$ExternalSyntheticLambda3 = new DozeLogger$$ExternalSyntheticLambda3(4);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda3, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logMessageImpl.str2 = reasonToString;
        logBuffer.commit(obtain);
    }

    public final void traceSensorEventDropped(int i, String str) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(23);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.str1 = str;
        logBuffer.commit(obtain);
    }

    public final void tracePulseDropped(String str) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }
}
