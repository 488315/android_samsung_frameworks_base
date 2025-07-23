package androidx.compose.material3;

import androidx.compose.foundation.IndicationNodeFactory;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.unit.Dp;
import kotlin.ULong;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RippleKt {
    public static final RippleNodeFactory DefaultBoundedRipple;
    public static final RippleNodeFactory DefaultUnboundedRipple;
    public static final DynamicProvidableCompositionLocal LocalRippleConfiguration = CompositionLocalKt.compositionLocalOf$default(new Function0() { // from class: androidx.compose.material3.RippleKt$LocalRippleConfiguration$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new RippleConfiguration(0L, null, 3, null);
        }
    });

    static {
        Dp.Companion.getClass();
        float f = Dp.Unspecified;
        Color.Companion.getClass();
        long j = Color.Unspecified;
        DefaultBoundedRipple = new RippleNodeFactory(true, f, j, (DefaultConstructorMarker) null);
        DefaultUnboundedRipple = new RippleNodeFactory(false, f, j, (DefaultConstructorMarker) null);
    }

    /* renamed from: ripple-H2RKhps$default, reason: not valid java name */
    public static IndicationNodeFactory m280rippleH2RKhps$default(float f, boolean z, int i) {
        if ((i & 1) != 0) {
            z = true;
        }
        boolean z2 = z;
        if ((i & 2) != 0) {
            Dp.Companion.getClass();
            f = Dp.Unspecified;
        }
        float f2 = f;
        Color.Companion companion = Color.Companion;
        companion.getClass();
        long j = Color.Unspecified;
        Dp.Companion.getClass();
        if (Dp.m836equalsimpl0(f2, Dp.Unspecified)) {
            companion.getClass();
            if (ULong.m3427equalsimpl0(j, j)) {
                return z2 ? DefaultBoundedRipple : DefaultUnboundedRipple;
            }
        }
        return new RippleNodeFactory(z2, f2, j, (DefaultConstructorMarker) null);
    }
}
