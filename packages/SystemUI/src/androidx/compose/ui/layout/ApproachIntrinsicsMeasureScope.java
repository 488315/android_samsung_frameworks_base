package androidx.compose.ui.layout;

import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ApproachIntrinsicsMeasureScope implements ApproachMeasureScope, ApproachIntrinsicMeasureScope {
    public final /* synthetic */ ApproachIntrinsicMeasureScope $$delegate_0;
    public final LayoutDirection layoutDirection;

    public ApproachIntrinsicsMeasureScope(ApproachIntrinsicMeasureScope approachIntrinsicMeasureScope, LayoutDirection layoutDirection) {
        this.layoutDirection = layoutDirection;
        this.$$delegate_0 = approachIntrinsicMeasureScope;
    }

    @Override // androidx.compose.ui.unit.Density
    public final float getDensity() {
        return this.$$delegate_0.getDensity();
    }

    @Override // androidx.compose.ui.unit.FontScaling
    public final float getFontScale() {
        return this.$$delegate_0.getFontScale();
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasureScope
    public final LayoutDirection getLayoutDirection() {
        return this.layoutDirection;
    }

    @Override // androidx.compose.ui.layout.ApproachIntrinsicMeasureScope
    /* renamed from: getLookaheadConstraints-msEJaDk */
    public final long mo601getLookaheadConstraintsmsEJaDk() {
        return this.$$delegate_0.mo601getLookaheadConstraintsmsEJaDk();
    }

    @Override // androidx.compose.ui.layout.ApproachIntrinsicMeasureScope
    /* renamed from: getLookaheadSize-YbymL2g */
    public final long mo602getLookaheadSizeYbymL2g() {
        return this.$$delegate_0.mo602getLookaheadSizeYbymL2g();
    }

    @Override // androidx.compose.ui.layout.IntrinsicMeasureScope
    public final boolean isLookingAhead() {
        return this.$$delegate_0.isLookingAhead();
    }

    @Override // androidx.compose.ui.layout.MeasureScope
    public final MeasureResult layout(final int i, final int i2, final Map map, Function1 function1) {
        if (i < 0) {
            i = 0;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        if ((i & (-16777216)) != 0 || ((-16777216) & i2) != 0) {
            InlineClassHelperKt.throwIllegalStateException("Size(" + i + " x " + i2 + ") is out of range. Each dimension must be between 0 and 16777215.");
        }
        final Function1 function12 = null;
        return new MeasureResult() { // from class: androidx.compose.ui.layout.ApproachIntrinsicsMeasureScope$layout$1
            @Override // androidx.compose.ui.layout.MeasureResult
            public final Map getAlignmentLines() {
                return map;
            }

            @Override // androidx.compose.ui.layout.MeasureResult
            public final int getHeight() {
                return i2;
            }

            @Override // androidx.compose.ui.layout.MeasureResult
            public final Function1 getRulers() {
                return function12;
            }

            @Override // androidx.compose.ui.layout.MeasureResult
            public final int getWidth() {
                return i;
            }

            @Override // androidx.compose.ui.layout.MeasureResult
            public final void placeChildren() {
            }
        };
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: roundToPx-0680j_4 */
    public final int mo51roundToPx0680j_4(float f) {
        return this.$$delegate_0.mo51roundToPx0680j_4(f);
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* renamed from: toDp-GaN1DYA */
    public final float mo52toDpGaN1DYA(long j) {
        return this.$$delegate_0.mo52toDpGaN1DYA(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDp-u2uoSUM */
    public final float mo53toDpu2uoSUM(float f) {
        return this.$$delegate_0.mo53toDpu2uoSUM(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDpSize-k-rfVVM */
    public final long mo55toDpSizekrfVVM(long j) {
        return this.$$delegate_0.mo55toDpSizekrfVVM(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toPx--R2X_6o */
    public final float mo56toPxR2X_6o(long j) {
        return this.$$delegate_0.mo56toPxR2X_6o(j);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toPx-0680j_4 */
    public final float mo57toPx0680j_4(float f) {
        return this.$$delegate_0.mo57toPx0680j_4(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toSize-XkaWNTQ */
    public final long mo58toSizeXkaWNTQ(long j) {
        return this.$$delegate_0.mo58toSizeXkaWNTQ(j);
    }

    @Override // androidx.compose.ui.unit.FontScaling
    /* renamed from: toSp-0xMU5do */
    public final long mo59toSp0xMU5do(float f) {
        return this.$$delegate_0.mo59toSp0xMU5do(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toSp-kPz2Gy4 */
    public final long mo60toSpkPz2Gy4(float f) {
        return this.$$delegate_0.mo60toSpkPz2Gy4(f);
    }

    @Override // androidx.compose.ui.unit.Density
    /* renamed from: toDp-u2uoSUM */
    public final float mo54toDpu2uoSUM(int i) {
        return this.$$delegate_0.mo54toDpu2uoSUM(i);
    }
}
