package com.android.systemui.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.Formatter;
import androidx.constraintlayout.motion.widget.KeyAttributes$$ExternalSyntheticOutline0;
import com.android.internal.util.Preconditions;
import com.android.systemui.LsRune;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.SecLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.util.AlarmTimeout;
import com.android.systemui.util.Assert;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DozeUi implements DozeMachine.Part {
    public final boolean mCanAnimateTransition;
    public final Context mContext;
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public final Handler mHandler;
    public final DozeHost mHost;
    public DozeMachine mMachine;
    public final StatusBarStateController mStatusBarStateController;
    public final AlarmTimeout mTimeTicker;
    public final WakeLock mWakeLock;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.doze.DozeUi$2 */
    public abstract /* synthetic */ class AbstractC12532 {
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

    public DozeUi(Context context, AlarmManager alarmManager, WakeLock wakeLock, DozeHost dozeHost, Handler handler, DozeParameters dozeParameters, StatusBarStateController statusBarStateController, DozeLog dozeLog) {
        this.mContext = context;
        this.mWakeLock = wakeLock;
        this.mHost = dozeHost;
        this.mHandler = handler;
        this.mCanAnimateTransition = !dozeParameters.getDisplayNeedsBlanking();
        this.mDozeParameters = dozeParameters;
        this.mTimeTicker = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda1
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                DozeUi dozeUi = DozeUi.this;
                dozeUi.verifyLastTimeTick();
                ((DozeServiceHost) dozeUi.mHost).dozeTimeTick();
                dozeUi.mHandler.post(dozeUi.mWakeLock.wrap(new AODUi$$ExternalSyntheticLambda0()));
            }
        }, "doze_time_tick", handler);
        this.mDozeLog = dozeLog;
        this.mStatusBarStateController = statusBarStateController;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void setDozeMachine(DozeMachine dozeMachine) {
        this.mMachine = dozeMachine;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x005e, code lost:
    
        if (r1 != false) goto L34;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.doze.DozeUi$1] */
    @Override // com.android.systemui.doze.DozeMachine.Part
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int[] iArr = AbstractC12532.$SwitchMap$com$android$systemui$doze$DozeMachine$State;
        boolean z = false;
        switch (iArr[state2.ordinal()]) {
            case 1:
            case 2:
                if (state == DozeMachine.State.DOZE_AOD_PAUSED || state == DozeMachine.State.DOZE) {
                    ((DozeServiceHost) this.mHost).dozeTimeTick();
                    Handler handler = this.mHandler;
                    WakeLock wakeLock = this.mWakeLock;
                    final DozeHost dozeHost = this.mHost;
                    Objects.requireNonNull(dozeHost);
                    handler.postDelayed(wakeLock.wrap(new Runnable() { // from class: com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ((DozeServiceHost) DozeHost.this).dozeTimeTick();
                        }
                    }), 500L);
                    break;
                }
                break;
            case 4:
            case 5:
            case 6:
                AlarmTimeout alarmTimeout = this.mTimeTicker;
                if (alarmTimeout.mScheduled) {
                    verifyLastTimeTick();
                    alarmTimeout.cancel();
                    break;
                }
                break;
            case 7:
                DozeMachine dozeMachine = this.mMachine;
                dozeMachine.getClass();
                Assert.isMainThread();
                DozeMachine.State state3 = dozeMachine.mState;
                Preconditions.checkState(state3 == DozeMachine.State.DOZE_REQUEST_PULSE || state3 == DozeMachine.State.DOZE_PULSING || state3 == DozeMachine.State.DOZE_PULSING_BRIGHT || state3 == DozeMachine.State.DOZE_PULSE_DONE, "must be in pulsing state, but is " + dozeMachine.mState);
                final int i = dozeMachine.mPulseReason;
                ((DozeServiceHost) this.mHost).pulseWhileDozing(new DozeHost.PulseCallback() { // from class: com.android.systemui.doze.DozeUi.1
                    @Override // com.android.systemui.doze.DozeHost.PulseCallback
                    public final void onPulseFinished() {
                        DozeUi.this.mMachine.requestState(DozeMachine.State.DOZE_PULSE_DONE);
                    }

                    @Override // com.android.systemui.doze.DozeHost.PulseCallback
                    public final void onPulseStarted() {
                        try {
                            DozeUi.this.mMachine.requestState(i == 8 ? DozeMachine.State.DOZE_PULSING_BRIGHT : DozeMachine.State.DOZE_PULSING);
                        } catch (IllegalStateException unused) {
                        }
                    }
                }, i);
                break;
            case 8:
                DozeServiceHost dozeServiceHost = (DozeServiceHost) this.mHost;
                if (!dozeServiceHost.mDozingRequested) {
                    if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_COVER) {
                        WakefulnessLifecycle wakefulnessLifecycle = dozeServiceHost.mWakefulnessLifecycle;
                        if (wakefulnessLifecycle.mWakefulness != 1) {
                            synchronized (wakefulnessLifecycle.mMsgForLifecycle) {
                                Optional findFirst = wakefulnessLifecycle.mMsgForLifecycle.stream().filter(new Predicate() { // from class: com.android.systemui.keyguard.SecLifecycle$$ExternalSyntheticLambda0
                                    public final /* synthetic */ int f$0 = 1;

                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return ((SecLifecycle.Msg) obj).msg == this.f$0;
                                    }
                                }).findFirst();
                                boolean z2 = findFirst != null && findFirst.isPresent();
                                break;
                            }
                        }
                        Log.m138d("DozeServiceHost", "startDozing skipped");
                        break;
                    }
                    android.util.Log.d("DozeServiceHost", "startDozing");
                    dozeServiceHost.mDozingRequested = true;
                    dozeServiceHost.updateDozing();
                    dozeServiceHost.mDozeLog.traceDozing(((StatusBarStateControllerImpl) dozeServiceHost.mStatusBarStateController).mIsDozing);
                    CentralSurfaces centralSurfaces = dozeServiceHost.mCentralSurfaces;
                    if (centralSurfaces != null) {
                        ((CentralSurfacesImpl) centralSurfaces).updateIsKeyguard();
                        break;
                    }
                }
                break;
            case 9:
                ((DozeServiceHost) this.mHost).stopDozing();
                AlarmTimeout alarmTimeout2 = this.mTimeTicker;
                if (alarmTimeout2.mScheduled) {
                    verifyLastTimeTick();
                    alarmTimeout2.cancel();
                    break;
                }
                break;
        }
        int i2 = iArr[state2.ordinal()];
        DozeHost dozeHost2 = this.mHost;
        switch (i2) {
            case 7:
            case 10:
            case 11:
            case 12:
                DozeServiceHost dozeServiceHost2 = (DozeServiceHost) dozeHost2;
                int i3 = dozeServiceHost2.mWakefulnessLifecycle.mWakefulness;
                if (i3 == 2 || i3 == 1) {
                    return;
                }
                dozeServiceHost2.mAnimateWakeup = true;
                return;
            case 8:
            default:
                if (LsRune.AOD_DISABLE_CLOCK_TRANSITION) {
                    DozeServiceHost dozeServiceHost3 = (DozeServiceHost) dozeHost2;
                    int i4 = dozeServiceHost3.mWakefulnessLifecycle.mWakefulness;
                    if (i4 == 2 || i4 == 1) {
                        return;
                    }
                    dozeServiceHost3.mAnimateWakeup = false;
                    return;
                }
                if (this.mCanAnimateTransition && (LsRune.AOD_LIGHT_REVEAL || this.mDozeParameters.getAlwaysOn())) {
                    z = true;
                }
                DozeServiceHost dozeServiceHost4 = (DozeServiceHost) dozeHost2;
                int i5 = dozeServiceHost4.mWakefulnessLifecycle.mWakefulness;
                if (i5 == 2 || i5 == 1) {
                    return;
                }
                dozeServiceHost4.mAnimateWakeup = z;
                return;
            case 9:
                return;
        }
    }

    public final void verifyLastTimeTick() {
        long elapsedRealtime = SystemClock.elapsedRealtime() - 0;
        if (elapsedRealtime > 90000) {
            String formatShortElapsedTime = Formatter.formatShortElapsedTime(this.mContext, elapsedRealtime);
            DozeLogger dozeLogger = this.mDozeLog.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.ERROR;
            DozeLogger$logMissedTick$2 dozeLogger$logMissedTick$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logMissedTick$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return KeyAttributes$$ExternalSyntheticOutline0.m21m("Missed AOD time tick by ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logMissedTick$2, null);
            obtain.setStr1(formatShortElapsedTime);
            logBuffer.commit(obtain);
            android.util.Log.e("DozeMachine", "Missed AOD time tick by " + formatShortElapsedTime);
        }
    }
}
