package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$$ExternalSyntheticLambda1;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BurnInParameters {
    public final int minViewY;
    public final int topInset;
    public final Function0 translationX;
    public final Function0 translationY;

    public BurnInParameters() {
        this(0, 0, null, null, 15, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v2, types: [kotlin.jvm.functions.Function0] */
    public static BurnInParameters copy$default(BurnInParameters burnInParameters, int i, int i2, Function0 function0, KeyguardRootViewBinder$$ExternalSyntheticLambda1 keyguardRootViewBinder$$ExternalSyntheticLambda1, int i3) {
        if ((i3 & 1) != 0) {
            i = burnInParameters.topInset;
        }
        if ((i3 & 2) != 0) {
            i2 = burnInParameters.minViewY;
        }
        if ((i3 & 4) != 0) {
            function0 = burnInParameters.translationY;
        }
        KeyguardRootViewBinder$$ExternalSyntheticLambda1 keyguardRootViewBinder$$ExternalSyntheticLambda12 = keyguardRootViewBinder$$ExternalSyntheticLambda1;
        if ((i3 & 8) != 0) {
            keyguardRootViewBinder$$ExternalSyntheticLambda12 = burnInParameters.translationX;
        }
        burnInParameters.getClass();
        return new BurnInParameters(i, i2, function0, keyguardRootViewBinder$$ExternalSyntheticLambda12);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BurnInParameters)) {
            return false;
        }
        BurnInParameters burnInParameters = (BurnInParameters) obj;
        return this.topInset == burnInParameters.topInset && this.minViewY == burnInParameters.minViewY && Intrinsics.areEqual(this.translationY, burnInParameters.translationY) && Intrinsics.areEqual(this.translationX, burnInParameters.translationX);
    }

    public final int hashCode() {
        return this.translationX.hashCode() + ((this.translationY.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.minViewY, Integer.hashCode(this.topInset) * 31, 31)) * 31);
    }

    public final String toString() {
        return "BurnInParameters(topInset=" + this.topInset + ", minViewY=" + this.minViewY + ", translationY=" + this.translationY + ", translationX=" + this.translationX + ")";
    }

    public BurnInParameters(int i, int i2, Function0 function0, Function0 function02) {
        this.topInset = i;
        this.minViewY = i2;
        this.translationY = function0;
        this.translationX = function02;
    }

    public /* synthetic */ BurnInParameters(int i, int i2, Function0 function0, Function0 function02, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? Integer.MAX_VALUE : i2, (i3 & 4) != 0 ? new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.BurnInParameters.1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return null;
            }
        } : function0, (i3 & 8) != 0 ? new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.BurnInParameters.2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return null;
            }
        } : function02);
    }
}
