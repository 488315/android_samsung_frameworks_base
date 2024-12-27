package com.android.systemui.doze;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.TimeUtils;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.core.animation.ValueAnimator$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
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
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DozeLog implements Dumpable {
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
            dozeLog.mLogger.logEmergencyCall();
            dozeLog.mEmergencyCallStats.mCount++;
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onFinishedGoingToSleep(int i) {
            DozeLogger dozeLogger = DozeLog.this.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            DozeLogger$logScreenOff$2 dozeLogger$logScreenOff$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logScreenOff$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "Screen off, why=");
                }
            };
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logScreenOff$2, null);
            ((LogMessageImpl) obtain).int1 = i;
            logBuffer.commit(obtain);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
            DozeLogger dozeLogger = DozeLog.this.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            DozeLogger$logKeyguardBouncerChanged$2 dozeLogger$logKeyguardBouncerChanged$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logKeyguardBouncerChanged$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Keyguard bouncer changed, showing=", ((LogMessage) obj).getBool1());
                }
            };
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logKeyguardBouncerChanged$2, null);
            ((LogMessageImpl) obtain).bool1 = z;
            logBuffer.commit(obtain);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onKeyguardVisibilityChanged(boolean z) {
            DozeLog dozeLog = DozeLog.this;
            DozeLogger dozeLogger = dozeLog.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            DozeLogger$logKeyguardVisibilityChange$2 dozeLogger$logKeyguardVisibilityChange$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logKeyguardVisibilityChange$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Keyguard visibility change, isVisible=", ((LogMessage) obj).getBool1());
                }
            };
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logKeyguardVisibilityChange$2, null);
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
            DozeLogger$logScreenOn$2 dozeLogger$logScreenOn$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logScreenOn$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Screen on, pulsing=", ((LogMessage) obj).getBool1());
                }
            };
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logScreenOn$2, null);
            ((LogMessageImpl) obtain).bool1 = z;
            logBuffer.commit(obtain);
            (dozeLog.mPulsing ? dozeLog.mScreenOnPulsingStats : dozeLog.mScreenOnNotPulsingStats).mCount++;
            dozeLog.mPulsing = false;
        }
    };
    public final long mSince = System.currentTimeMillis();
    public final SummaryStats[][] mProxStats = (SummaryStats[][]) Array.newInstance((Class<?>) SummaryStats.class, 13, 2);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SummaryStats {
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

    public final void traceDozing(boolean z) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$logDozing$2 dozeLogger$logDozing$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logDozing$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Dozing=", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logDozing$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
        this.mPulsing = false;
    }

    public final void traceDozingChanged(boolean z) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$logDozingChanged$2 dozeLogger$logDozingChanged$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logDozingChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("Dozing changed dozing=", ((LogMessage) obj).getBool1());
            }
        };
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logDozingChanged$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void traceFling(boolean z, boolean z2, boolean z3) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        DozeLogger$logFling$2 dozeLogger$logFling$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logFling$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("Fling expand=", " aboveThreshold=", " thresholdNeeded=", bool1, bool2), logMessage.getBool3(), " screenOnFromTouch=", logMessage.getBool4());
            }
        };
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logFling$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.bool4 = z3;
        logBuffer.commit(obtain);
    }

    public final void tracePulseDropped(String str, DozeMachine.State state) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$logPulseDropped$2 dozeLogger$logPulseDropped$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logPulseDropped$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return FontProvider$$ExternalSyntheticOutline0.m("Pulse dropped, cannot pulse from=", logMessage.getStr1(), " state=", logMessage.getStr2());
            }
        };
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logPulseDropped$2, null);
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
        DozeLogger$logPulseEvent$2 dozeLogger$logPulseEvent$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logPulseEvent$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                String str2 = logMessage.getStr2();
                StringBuilder m = CarrierTextManagerLogger$logCallbackSentFromUpdate$2$$ExternalSyntheticOutline0.m("Pulse-", str1, " dozing=", " pulseReason=", bool1);
                m.append(str2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logPulseEvent$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logMessageImpl.str2 = reasonToString;
        logBuffer.commit(obtain);
    }

    public final void tracePulseFinish() {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$logPulseFinish$2 dozeLogger$logPulseFinish$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logPulseFinish$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Pulse finish";
            }
        };
        LogBuffer logBuffer = dozeLogger.buffer;
        logBuffer.commit(logBuffer.obtain("DozeLog", logLevel, dozeLogger$logPulseFinish$2, null));
        this.mPulsing = false;
    }

    public final void tracePulseStart(int i) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$logPulseStart$2 dozeLogger$logPulseStart$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logPulseStart$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return "Pulse start, reason=".concat(DozeLog.reasonToString(((LogMessage) obj).getInt1()));
            }
        };
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logPulseStart$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
        this.mPulsing = true;
    }

    public final void traceSensorEventDropped(int i, String str) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$logSensorEventDropped$2 dozeLogger$logSensorEventDropped$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logSensorEventDropped$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "SensorEvent [", "] dropped, reason=", logMessage.getStr1());
            }
        };
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logSensorEventDropped$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.str1 = str;
        logBuffer.commit(obtain);
    }

    public final void traceSetAodDimmingScrim(float f) {
        long j = (long) f;
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$logSetAodDimmingScrim$2 dozeLogger$logSetAodDimmingScrim$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logSetAodDimmingScrim$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ValueAnimator$$ExternalSyntheticOutline0.m("Doze aod dimming scrim opacity set, opacity=", ((LogMessage) obj).getLong1());
            }
        };
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logSetAodDimmingScrim$2, null);
        ((LogMessageImpl) obtain).long1 = j;
        logBuffer.commit(obtain);
    }

    public final void tracePulseDropped(String str) {
        DozeLogger dozeLogger = this.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$logPulseDropped$4 dozeLogger$logPulseDropped$4 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logPulseDropped$4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Pulse dropped, why=", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logPulseDropped$4, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
    }
}
