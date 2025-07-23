package androidx.compose.foundation;

import android.view.View;
import android.widget.Magnifier;
import androidx.compose.ui.unit.Density;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PlatformMagnifierFactoryApi29Impl implements PlatformMagnifierFactory {
    public static final PlatformMagnifierFactoryApi29Impl INSTANCE = new PlatformMagnifierFactoryApi29Impl();
    public static final boolean canUpdateZoom = true;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PlatformMagnifierImpl extends PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl {
        public PlatformMagnifierImpl(Magnifier magnifier) {
            super(magnifier);
        }

        @Override // androidx.compose.foundation.PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl, androidx.compose.foundation.PlatformMagnifier
        /* renamed from: update-Wko1d7g */
        public final void mo45updateWko1d7g(long j, long j2, float f) {
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
    public final PlatformMagnifier mo46createnHHXs2Y(View view, boolean z, long j, float f, float f2, boolean z2, Density density, float f3) {
        if (z) {
            return new PlatformMagnifierImpl(new Magnifier(view));
        }
        long mo58toSizeXkaWNTQ = density.mo58toSizeXkaWNTQ(j);
        float mo57toPx0680j_4 = density.mo57toPx0680j_4(f);
        float mo57toPx0680j_42 = density.mo57toPx0680j_4(f2);
        Magnifier.Builder builder = new Magnifier.Builder(view);
        if (mo58toSizeXkaWNTQ != 9205357640488583168L) {
            builder.setSize(MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat((int) (mo58toSizeXkaWNTQ >> 32))), MathKt__MathJVMKt.roundToInt(Float.intBitsToFloat((int) (mo58toSizeXkaWNTQ & 4294967295L))));
        }
        if (!Float.isNaN(mo57toPx0680j_4)) {
            builder.setCornerRadius(mo57toPx0680j_4);
        }
        if (!Float.isNaN(mo57toPx0680j_42)) {
            builder.setElevation(mo57toPx0680j_42);
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
