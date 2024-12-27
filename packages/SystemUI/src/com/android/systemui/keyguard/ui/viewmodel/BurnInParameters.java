package com.android.systemui.keyguard.ui.viewmodel;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BurnInParameters {
    public final int minViewY;
    public final int topInset;
    public final Function0 translationY;

    public BurnInParameters() {
        this(0, 0, null, 7, null);
    }

    public static BurnInParameters copy$default(BurnInParameters burnInParameters, int i, int i2, Function0 function0, int i3) {
        if ((i3 & 1) != 0) {
            i = burnInParameters.topInset;
        }
        if ((i3 & 2) != 0) {
            i2 = burnInParameters.minViewY;
        }
        if ((i3 & 4) != 0) {
            function0 = burnInParameters.translationY;
        }
        burnInParameters.getClass();
        return new BurnInParameters(i, i2, function0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BurnInParameters)) {
            return false;
        }
        BurnInParameters burnInParameters = (BurnInParameters) obj;
        return this.topInset == burnInParameters.topInset && this.minViewY == burnInParameters.minViewY && Intrinsics.areEqual(this.translationY, burnInParameters.translationY);
    }

    public final int hashCode() {
        return this.translationY.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.minViewY, Integer.hashCode(this.topInset) * 31, 31);
    }

    public final String toString() {
        return "BurnInParameters(topInset=" + this.topInset + ", minViewY=" + this.minViewY + ", translationY=" + this.translationY + ")";
    }

    public BurnInParameters(int i, int i2, Function0 function0) {
        this.topInset = i;
        this.minViewY = i2;
        this.translationY = function0;
    }

    public /* synthetic */ BurnInParameters(int i, int i2, Function0 function0, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? Integer.MAX_VALUE : i2, (i3 & 4) != 0 ? new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.BurnInParameters.1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return null;
            }
        } : function0);
    }
}
