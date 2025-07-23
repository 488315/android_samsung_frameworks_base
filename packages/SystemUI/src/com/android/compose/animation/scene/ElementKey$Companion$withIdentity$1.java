package com.android.compose.animation.scene;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ElementKey$Companion$withIdentity$1 implements ElementMatcher {
    public final /* synthetic */ Function1 $predicate;

    public ElementKey$Companion$withIdentity$1(Function1 function1) {
        this.$predicate = function1;
    }

    @Override // com.android.compose.animation.scene.ElementMatcher
    public final boolean matches(ContentKey contentKey, ElementKey elementKey) {
        return ((Boolean) this.$predicate.mo779invoke(elementKey.identity)).booleanValue();
    }
}
