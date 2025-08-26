package com.android.compose.animation.scene;

import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class ElementKey$Companion$withIdentity$1 implements ElementMatcher {
    public final /* synthetic */ Function1 $predicate;

    public ElementKey$Companion$withIdentity$1(Function1 function1) {
        this.$predicate = function1;
    }

    @Override // com.android.compose.animation.scene.ElementMatcher
    public final boolean matches(ContentKey contentKey, ElementKey elementKey) {
        return ((Boolean) this.$predicate.mo781invoke(elementKey.identity)).booleanValue();
    }
}
