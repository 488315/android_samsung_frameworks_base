package androidx.compose.animation.graphics.res;

import android.animation.TimeInterpolator;
import androidx.compose.animation.core.Easing;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AnimatorResources_androidKt$$ExternalSyntheticLambda0 implements Easing {
    public final /* synthetic */ TimeInterpolator f$0;

    public /* synthetic */ AnimatorResources_androidKt$$ExternalSyntheticLambda0(TimeInterpolator timeInterpolator) {
        this.f$0 = timeInterpolator;
    }

    @Override // androidx.compose.animation.core.Easing
    public final float transform(float f) {
        TimeInterpolator timeInterpolator = this.f$0;
        AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda3 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
        return timeInterpolator.getInterpolation(f);
    }
}
