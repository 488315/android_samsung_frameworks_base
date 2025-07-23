package com.android.systemui.animation;

import android.content.ComponentName;
import com.android.systemui.animation.ActivityTransitionAnimator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ComposableControllerFactory extends ActivityTransitionAnimator.ControllerFactory {
    public final StateFlowImpl expandable;

    public /* synthetic */ ComposableControllerFactory(ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num, Integer num2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(transitionCookie, componentName, (i & 4) != 0 ? null : num, (i & 8) != 0 ? null : num2);
    }

    public ComposableControllerFactory(ActivityTransitionAnimator.TransitionCookie transitionCookie, ComponentName componentName, Integer num, Integer num2) {
        super(transitionCookie, componentName, num, num2);
        this.expandable = StateFlowKt.MutableStateFlow(null);
    }
}
