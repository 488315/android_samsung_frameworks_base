package com.android.systemui.doze;

import android.app.AlarmManager;
import android.os.Handler;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.util.AlarmTimeout;

public final class DozePauser implements DozeMachine.Part {
    public DozeMachine mMachine;
    public final AlarmTimeout mPauseTimeout;
    public final AlwaysOnDisplayPolicy mPolicy;

    /* renamed from: com.android.systemui.doze.DozePauser$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public DozePauser(Handler handler, AlarmManager alarmManager, AlwaysOnDisplayPolicy alwaysOnDisplayPolicy) {
        this.mPauseTimeout = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.android.systemui.doze.DozePauser$$ExternalSyntheticLambda0
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                DozePauser.this.mMachine.requestState(DozeMachine.State.DOZE_AOD_PAUSED);
            }
        }, "DozePauser", handler);
        this.mPolicy = alwaysOnDisplayPolicy;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void setDozeMachine(DozeMachine dozeMachine) {
        this.mMachine = dozeMachine;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int i = AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()];
        AlarmTimeout alarmTimeout = this.mPauseTimeout;
        if (i != 1) {
            alarmTimeout.cancel();
        } else {
            alarmTimeout.schedule(this.mPolicy.proxScreenOffDelayMs, 1);
        }
    }
}
