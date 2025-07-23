package com.android.systemui.animation;

import android.view.RemoteAnimationAdapter;
import com.android.systemui.animation.ActivityTransitionAnimator;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class ActivityTransitionAnimator$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ ActivityTransitionAnimator.PendingIntentStarter f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ActivityTransitionAnimator.Companion companion = ActivityTransitionAnimator.Companion;
        return Integer.valueOf(this.f$0.startPendingIntent((RemoteAnimationAdapter) obj));
    }
}
