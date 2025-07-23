package com.android.systemui.shared.clocks;

import android.text.Layout;
import com.android.systemui.animation.TextAnimator;
import com.android.systemui.animation.TextAnimatorListener;
import com.android.systemui.animation.TypefaceVariantCacheImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class AnimatableClockView$$ExternalSyntheticLambda2 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Layout layout = (Layout) obj;
        final Function0 function0 = (Function0) obj2;
        String str = AnimatableClockView.TAG;
        return new TextAnimator(layout, new TypefaceVariantCacheImpl(layout.getPaint().getTypeface(), 30), new TextAnimatorListener() { // from class: com.android.systemui.shared.clocks.AnimatableClockView$textAnimatorFactory$1$1
            @Override // com.android.systemui.animation.TextAnimatorListener
            public final void onInvalidate() {
                Function0.this.invoke();
            }
        });
    }
}
