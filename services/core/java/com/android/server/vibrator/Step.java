package com.android.server.vibrator;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
