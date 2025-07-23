package com.android.systemui.statusbar.events;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SystemEventChipAnimationController extends SystemStatusAnimationCallback {
    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    SpringAnimatorSet onSystemEventAnimationBegin(boolean z, boolean z2);

    @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
    SpringAnimatorSet onSystemEventAnimationFinish(boolean z, boolean z2, boolean z3);

    void prepareChipAnimation(Function1 function1, boolean z);

    void stop();
}
