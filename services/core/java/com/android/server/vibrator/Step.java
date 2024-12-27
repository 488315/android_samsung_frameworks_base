package com.android.server.vibrator;

import java.util.List;

public abstract class Step implements Comparable {
    public final VibrationStepConductor conductor;
    public final long startTime;

    public Step(VibrationStepConductor vibrationStepConductor, long j) {
        this.conductor = vibrationStepConductor;
        this.startTime = j;
    }

    public boolean acceptVibratorCompleteCallback(int i) {
        return false;
    }

    public abstract List cancel();

    public abstract void cancelImmediately();

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return Long.compare(this.startTime, ((Step) obj).startTime);
    }

    public long getVibratorOnDuration() {
        return 0L;
    }

    public boolean isCleanUp() {
        return this instanceof FinishSequentialEffectStep;
    }

    public abstract List play();
}
