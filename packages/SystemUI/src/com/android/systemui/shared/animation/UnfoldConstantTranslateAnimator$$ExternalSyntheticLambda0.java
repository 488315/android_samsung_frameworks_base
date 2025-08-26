package com.android.systemui.shared.animation;

import com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class UnfoldConstantTranslateAnimator$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Boolean bool = (Boolean) ((UnfoldConstantTranslateAnimator.ViewIdToTranslate) obj).shouldBeAnimated.invoke();
        bool.booleanValue();
        return bool;
    }
}
