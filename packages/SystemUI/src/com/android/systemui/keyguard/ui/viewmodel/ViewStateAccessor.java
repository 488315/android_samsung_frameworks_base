package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ViewStateAccessor {
    public final Function0 alpha;
    public final Function0 translationX;
    public final Function0 translationY;

    public ViewStateAccessor() {
        this(null, null, null, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ViewStateAccessor)) {
            return false;
        }
        ViewStateAccessor viewStateAccessor = (ViewStateAccessor) obj;
        return Intrinsics.areEqual(this.alpha, viewStateAccessor.alpha) && Intrinsics.areEqual(this.translationY, viewStateAccessor.translationY) && Intrinsics.areEqual(this.translationX, viewStateAccessor.translationX);
    }

    public final int hashCode() {
        return this.translationX.hashCode() + ((this.translationY.hashCode() + (this.alpha.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "ViewStateAccessor(alpha=" + this.alpha + ", translationY=" + this.translationY + ", translationX=" + this.translationX + ")";
    }

    public ViewStateAccessor(Function0 function0, Function0 function02, Function0 function03) {
        this.alpha = function0;
        this.translationY = function02;
        this.translationX = function03;
    }

    public /* synthetic */ ViewStateAccessor(Function0 function0, Function0 function02, Function0 function03, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1() : function0, (i & 2) != 0 ? new ViewStateAccessor$$ExternalSyntheticLambda0() : function02, (i & 4) != 0 ? new ViewStateAccessor$$ExternalSyntheticLambda0() : function03);
    }
}
