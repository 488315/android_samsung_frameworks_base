package com.android.systemui.navigationbar.util;

import com.android.systemui.navigationbar.gestural.MotionPauseDetector$$ExternalSyntheticLambda0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScopeTimer {
    public StandaloneCoroutine job;
    public final CoroutineScope scope;

    public ScopeTimer(CoroutineScope coroutineScope) {
        this.scope = coroutineScope;
    }

    public final void cancel() {
        StandaloneCoroutine standaloneCoroutine = this.job;
        if (standaloneCoroutine == null || !standaloneCoroutine.isActive()) {
            return;
        }
        StandaloneCoroutine standaloneCoroutine2 = this.job;
        if (standaloneCoroutine2 != null) {
            standaloneCoroutine2.cancel(null);
        }
        this.job = null;
    }

    public final void start(long j, MotionPauseDetector$$ExternalSyntheticLambda0 motionPauseDetector$$ExternalSyntheticLambda0) {
        cancel();
        this.job = BuildersKt.launch$default(this.scope, null, null, new ScopeTimer$start$1(j, motionPauseDetector$$ExternalSyntheticLambda0, this, null), 3);
    }
}
