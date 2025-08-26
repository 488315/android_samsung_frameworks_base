package androidx.compose.foundation;

import android.view.View;
import android.widget.Magnifier;
import androidx.compose.ui.unit.Density;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: classes.dex */
public final class PlatformMagnifierFactoryApi29Impl implements PlatformMagnifierFactory {
    public static final PlatformMagnifierFactoryApi29Impl INSTANCE = new PlatformMagnifierFactoryApi29Impl();
    public static final boolean canUpdateZoom = true;

    public final class PlatformMagnifierImpl extends PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl {
        public PlatformMagnifierImpl(Magnifier magnifier) {
            super(magnifier);
        }

        @Override // androidx.compose.foundation.PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl, androidx.compose.foundation.PlatformMagnifier
        /* renamed from: update-Wko1d7g */
        public final void mo46updateWko1d7g(long j, long j2, float f) {
            if (!Float.isNaN(f)) {
                this.magnifier.setZoom(f);
            }
            if ((9223372034707292159L & j2) != 9205357640488583168L) {
                this.magnifier.show(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)), Float.intBitsToFloat((int) (j2 >> 32)), Float.intBitsToFloat((int) (j2 & 4294967295L)));
            } else {
                this.magnifier.show(Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)));
            }
        }
    }

    private PlatformMagnifierFactoryApi29Impl() {
    }

    @Override // androidx.compose.foundation.PlatformMagnifierFactory
    /* renamed from: create-nHHXs2Y */
    public final PlatformMagnifier mo47createnHHXs2Y(View view, boolean z, long j, float f, float f2, boolean z2, Density density, float f3) {
        if (z) {
            return new PlatformMagnifierImpl(new Magnifier(view));
        }
        long jMo59toSizeXkaWNTQ = density.mo59toSizeXkaWNTQ(j);
        float fMo58toPx0680j_4 = density.mo58toPx0680j_4(f);
        float fMo58toPx0680j_42 = density.mo58toPx0680j_4(f2);
        Magnifier.Builder builder = new Magnifier.Builder(view);
        if (jMo59toSizeXkaWNTQ != 9205357640488583168L) {
            builder.setSize(MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat((int) (jMo59toSizeXkaWNTQ >> 32))), MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat((int) (jMo59toSizeXkaWNTQ & 4294967295L))));
        }
        if (!Float.isNaN(fMo58toPx0680j_4)) {
            builder.setCornerRadius(fMo58toPx0680j_4);
        }
        if (!Float.isNaN(fMo58toPx0680j_42)) {
            builder.setElevation(fMo58toPx0680j_42);
        }
        if (!Float.isNaN(f3)) {
            builder.setInitialZoom(f3);
        }
        builder.setClippingEnabled(z2);
        return new PlatformMagnifierImpl(builder.build());
    }

    @Override // androidx.compose.foundation.PlatformMagnifierFactory
    public final boolean getCanUpdateZoom() {
        return canUpdateZoom;
    }
}
