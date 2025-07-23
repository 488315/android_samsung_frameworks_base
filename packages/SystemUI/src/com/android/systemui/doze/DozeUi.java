package com.android.systemui.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.util.Log;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.AlarmTimeout;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class DozeUi implements DozeMachine.Part {
    public final DelayableExecutor mBgExecutor;
    public final boolean mCanAnimateTransition;
    public final AnonymousClass1 mCancelTimeTickerRunnable = new Runnable() { // from class: com.android.systemui.doze.DozeUi.1
        @Override // java.lang.Runnable
        public final void run() {
            DozeLogger dozeLogger = DozeUi.this.mDozeLog.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(14);
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.bool1 = false;
            logMessageImpl.bool2 = false;
            logBuffer.commit(obtain);
            DozeUi.this.getClass();
            DozeUi.this.mTimeTicker.cancel();
        }
    };
    public final Context mContext;
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public final Handler mHandler;
    public final DozeHost mHost;
    public DozeMachine mMachine;
    public final AlarmTimeout mTimeTicker;
    public final WakeLock mWakeLock;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.doze.DozeUi$3, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass3 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.DOZE_AOD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_DOCKED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_SUSPEND_TRIGGERS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_REQUEST_PULSE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.INITIALIZED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING_BRIGHT.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSE_DONE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.doze.DozeUi$1] */
    public DozeUi(Context context, AlarmManager alarmManager, WakeLock wakeLock, DozeHost dozeHost, Handler handler, Handler handler2, DozeParameters dozeParameters, DelayableExecutor delayableExecutor, DozeLog dozeLog) {
        this.mContext = context;
        this.mWakeLock = wakeLock;
        this.mHost = dozeHost;
        this.mHandler = handler;
        this.mBgExecutor = delayableExecutor;
        this.mCanAnimateTransition = !dozeParameters.getDisplayNeedsBlanking();
        this.mDozeParameters = dozeParameters;
        this.mTimeTicker = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda1
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                DozeUi dozeUi = DozeUi.this;
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (elapsedRealtime > 90000) {
                    String formatShortElapsedTime = Formatter.formatShortElapsedTime(dozeUi.mContext, elapsedRealtime);
                    DozeLogger dozeLogger = dozeUi.mDozeLog.mLogger;
                    dozeLogger.getClass();
                    LogLevel logLevel = LogLevel.ERROR;
                    DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(19);
                    LogBuffer logBuffer = dozeLogger.buffer;
                    LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
                    ((LogMessageImpl) obtain).str1 = formatShortElapsedTime;
                    logBuffer.commit(obtain);
                    Log.e("DozeMachine", "Missed AOD time tick by " + formatShortElapsedTime);
                }
                DozeHost dozeHost2 = dozeUi.mHost;
                Objects.requireNonNull(dozeHost2);
                dozeUi.mHandler.post(dozeUi.mWakeLock.wrap(new DozeUi$$ExternalSyntheticLambda0(dozeHost2)));
            }
        }, "doze_time_tick", handler2);
        this.mDozeLog = dozeLog;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void setDozeMachine(DozeMachine dozeMachine) {
        this.mMachine = dozeMachine;
    }

    /* JADX WARN: Code restructure failed: missing block: B:65:0x0060, code lost:
    
        if (r1 != false) goto L39;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.doze.DozeUi$2] */
    @Override // com.android.systemui.doze.DozeMachine.Part
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void transitionTo(com.android.systemui.doze.DozeMachine.State r9, com.android.systemui.doze.DozeMachine.State r10) {
        /*
            Method dump skipped, instructions count: 346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeUi.transitionTo(com.android.systemui.doze.DozeMachine$State, com.android.systemui.doze.DozeMachine$State):void");
    }
}
