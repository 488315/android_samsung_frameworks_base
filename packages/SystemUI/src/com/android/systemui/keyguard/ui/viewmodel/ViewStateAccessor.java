package com.android.systemui.keyguard.ui.viewmodel;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        this((i & 1) != 0 ? new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.ViewStateAccessor.1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        } : function0, (i & 2) != 0 ? new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.ViewStateAccessor.2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return 0;
            }
        } : function02, (i & 4) != 0 ? new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.ViewStateAccessor.3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return 0;
            }
        } : function03);
    }
}
