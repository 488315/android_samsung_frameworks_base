package com.android.systemui.statusbar.notification.stack;

import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import java.util.HashSet;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class StackStateAnimator$1$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ HashSet f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        this.f$0.add((DynamicAnimation) obj);
    }
}
