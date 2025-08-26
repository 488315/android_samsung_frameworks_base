package com.android.systemui.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.util.Log;
import com.android.app.tracing.TraceUtils;
import com.android.internal.util.Preconditions;
import com.android.systemui.LsRune;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.SecLifecycle$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.DozeServiceHost$$ExternalSyntheticLambda2;
import com.android.systemui.util.AlarmTimeout;
import com.android.systemui.util.Assert;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Objects;
import java.util.Optional;

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
            LogMessage logMessageObtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.bool1 = false;
            logMessageImpl.bool2 = false;
            logBuffer.commit(logMessageObtain);
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
                DozeUi dozeUi = this.f$0;
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                if (jElapsedRealtime > 90000) {
                    String shortElapsedTime = Formatter.formatShortElapsedTime(dozeUi.mContext, jElapsedRealtime);
                    DozeLogger dozeLogger = dozeUi.mDozeLog.mLogger;
                    dozeLogger.getClass();
                    LogLevel logLevel = LogLevel.ERROR;
                    DozeLogger$$ExternalSyntheticLambda0 dozeLogger$$ExternalSyntheticLambda0 = new DozeLogger$$ExternalSyntheticLambda0(19);
                    LogBuffer logBuffer = dozeLogger.buffer;
                    LogMessage logMessageObtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda0, null);
                    ((LogMessageImpl) logMessageObtain).str1 = shortElapsedTime;
                    logBuffer.commit(logMessageObtain);
                    Log.e("DozeMachine", "Missed AOD time tick by " + shortElapsedTime);
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0063  */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.doze.DozeUi$2] */
    @Override // com.android.systemui.doze.DozeMachine.Part
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) throws PackageManager.NameNotFoundException {
        boolean z;
        int[] iArr = AnonymousClass3.$SwitchMap$com$android$systemui$doze$DozeMachine$State;
        int i = iArr[state2.ordinal()];
        boolean z2 = false;
        if (i == 1 || i == 2) {
            if (state == DozeMachine.State.DOZE_AOD_PAUSED || state == DozeMachine.State.DOZE) {
                DozeServiceHost dozeServiceHost = (DozeServiceHost) this.mHost;
                dozeServiceHost.getClass();
                TraceUtils.trace("DozeServiceHost#dozeTimeTick", new DozeServiceHost$$ExternalSyntheticLambda2(dozeServiceHost));
                Handler handler = this.mHandler;
                WakeLock wakeLock = this.mWakeLock;
                DozeHost dozeHost = this.mHost;
                Objects.requireNonNull(dozeHost);
                handler.postDelayed(wakeLock.wrap(new DozeUi$$ExternalSyntheticLambda0(dozeHost)), 500L);
            }
        } else if (i == 7) {
            DozeMachine dozeMachine = this.mMachine;
            dozeMachine.getClass();
            Assert.isMainThread();
            DozeMachine.State state3 = dozeMachine.mState;
            Preconditions.checkState(state3 == DozeMachine.State.DOZE_REQUEST_PULSE || state3 == DozeMachine.State.DOZE_PULSING || state3 == DozeMachine.State.DOZE_PULSING_BRIGHT || state3 == DozeMachine.State.DOZE_PULSE_DONE, "must be in pulsing state, but is " + dozeMachine.mState);
            final int i2 = dozeMachine.mPulseReason;
            ((DozeServiceHost) this.mHost).pulseWhileDozing(new DozeHost.PulseCallback() { // from class: com.android.systemui.doze.DozeUi.2
                @Override // com.android.systemui.doze.DozeHost.PulseCallback
                public final void onPulseFinished() {
                    DozeUi.this.mMachine.requestState(DozeMachine.State.DOZE_PULSE_DONE);
                }

                @Override // com.android.systemui.doze.DozeHost.PulseCallback
                public final void onPulseStarted() {
                    try {
                        DozeUi.this.mMachine.requestState(i2 == 8 ? DozeMachine.State.DOZE_PULSING_BRIGHT : DozeMachine.State.DOZE_PULSING);
                    } catch (IllegalStateException unused) {
                    }
                }
            }, i2);
        } else if (i == 8) {
            DozeServiceHost dozeServiceHost2 = (DozeServiceHost) this.mHost;
            if (!dozeServiceHost2.mDozingRequested) {
                if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_COVER) {
                    WakefulnessLifecycle wakefulnessLifecycle = dozeServiceHost2.mWakefulnessLifecycle;
                    if (wakefulnessLifecycle.mWakefulness != 1) {
                        synchronized (wakefulnessLifecycle.mMsgForLifecycle) {
                            try {
                                Optional optionalFindFirst = wakefulnessLifecycle.mMsgForLifecycle.stream().filter(new SecLifecycle$$ExternalSyntheticLambda0(0)).findFirst();
                                z = optionalFindFirst != null && optionalFindFirst.isPresent();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (!z) {
                            Log.d("DozeServiceHost", "startDozing");
                            dozeServiceHost2.mDozingRequested = true;
                            dozeServiceHost2.updateDozing();
                            dozeServiceHost2.mDozeLog.traceDozing(dozeServiceHost2.mStatusBarStateController.isDozing());
                            CentralSurfacesImpl centralSurfacesImpl = dozeServiceHost2.mCentralSurfaces;
                            if (centralSurfacesImpl != null) {
                                centralSurfacesImpl.updateIsKeyguard(false);
                            }
                        }
                    }
                    com.android.systemui.keyguard.Log.d("DozeServiceHost", "startDozing skipped");
                }
            }
        } else if (i == 9) {
            ((DozeServiceHost) this.mHost).stopDozing();
        }
        int i3 = iArr[state2.ordinal()];
        DozeHost dozeHost2 = this.mHost;
        switch (i3) {
            case 7:
            case 10:
            case 11:
            case 12:
                DozeServiceHost dozeServiceHost3 = (DozeServiceHost) dozeHost2;
                int i4 = dozeServiceHost3.mWakefulnessLifecycle.mWakefulness;
                if (i4 == 2 || i4 == 1) {
                    return;
                }
                dozeServiceHost3.mAnimateWakeup = true;
                return;
            case 8:
            default:
                if (LsRune.AOD_DISABLE_CLOCK_TRANSITION) {
                    DozeServiceHost dozeServiceHost4 = (DozeServiceHost) dozeHost2;
                    int i5 = dozeServiceHost4.mWakefulnessLifecycle.mWakefulness;
                    if (i5 == 2 || i5 == 1) {
                        return;
                    }
                    dozeServiceHost4.mAnimateWakeup = false;
                    return;
                }
                if (this.mCanAnimateTransition && (LsRune.AOD_LIGHT_REVEAL || this.mDozeParameters.getAlwaysOn())) {
                    z2 = true;
                }
                DozeServiceHost dozeServiceHost5 = (DozeServiceHost) dozeHost2;
                int i6 = dozeServiceHost5.mWakefulnessLifecycle.mWakefulness;
                if (i6 == 2 || i6 == 1) {
                    return;
                }
                dozeServiceHost5.mAnimateWakeup = z2;
                return;
            case 9:
                return;
        }
    }
}
