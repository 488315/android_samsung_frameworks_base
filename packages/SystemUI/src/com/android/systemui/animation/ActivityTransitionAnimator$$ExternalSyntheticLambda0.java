package com.android.systemui.animation;

import android.view.RemoteAnimationAdapter;
import com.android.systemui.animation.ActivityTransitionAnimator;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class ActivityTransitionAnimator$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ ActivityTransitionAnimator.PendingIntentStarter f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ActivityTransitionAnimator.Companion companion = ActivityTransitionAnimator.Companion;
        return Integer.valueOf(this.f$0.startPendingIntent((RemoteAnimationAdapter) obj));
    }
}
