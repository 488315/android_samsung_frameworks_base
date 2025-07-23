package androidx.compose.ui.graphics.vector;

import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composition;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VectorPainter extends Painter {
    public final MutableState autoMirror$delegate;
    public Composition composition;
    public float currentAlpha;
    public ColorFilter currentColorFilter;
    public int drawCount;
    public final MutableIntState invalidateCount$delegate;
    public final MutableState size$delegate;
    public final VectorComponent vector;

    public VectorPainter() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final boolean applyAlpha(float f) {
        this.currentAlpha = f;
        return true;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final boolean applyColorFilter(ColorFilter colorFilter) {
        this.currentColorFilter = colorFilter;
        return true;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    /* renamed from: getIntrinsicSize-NH-jbRc */
    public final long mo561getIntrinsicSizeNHjbRc() {
        return ((Size) ((SnapshotMutableStateImpl) this.size$delegate).getValue()).packedValue;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final void onDraw(DrawScope drawScope) {
        ColorFilter colorFilter = this.currentColorFilter;
        VectorComponent vectorComponent = this.vector;
        if (colorFilter == null) {
            colorFilter = (ColorFilter) ((SnapshotMutableStateImpl) vectorComponent.intrinsicColorFilter$delegate).getValue();
        }
        if (((Boolean) ((SnapshotMutableStateImpl) this.autoMirror$delegate).getValue()).booleanValue() && drawScope.getLayoutDirection() == LayoutDirection.Rtl) {
            long mo544getCenterF1C5BW0 = drawScope.mo544getCenterF1C5BW0();
            CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
            long m526getSizeNHjbRc = drawContext.m526getSizeNHjbRc();
            drawContext.getCanvas().save();
            try {
                drawContext.transform.m530scale0AR0LA0(-1.0f, 1.0f, mo544getCenterF1C5BW0);
                vectorComponent.draw(drawScope, this.currentAlpha, colorFilter);
            } finally {
                BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, m526getSizeNHjbRc);
            }
        } else {
            vectorComponent.draw(drawScope, this.currentAlpha, colorFilter);
        }
        this.drawCount = ((SnapshotMutableIntStateImpl) this.invalidateCount$delegate).getIntValue();
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [androidx.compose.ui.graphics.vector.VectorPainter$vector$1$1, kotlin.jvm.internal.Lambda] */
    public VectorPainter(GroupComponent groupComponent) {
        Size.Companion.getClass();
        this.size$delegate = SnapshotStateKt.mutableStateOf$default(Size.m413boximpl(0L));
        this.autoMirror$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
        VectorComponent vectorComponent = new VectorComponent(groupComponent);
        vectorComponent.invalidateCallback = new Function0() { // from class: androidx.compose.ui.graphics.vector.VectorPainter$vector$1$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                VectorPainter vectorPainter = VectorPainter.this;
                if (vectorPainter.drawCount == ((SnapshotMutableIntStateImpl) vectorPainter.invalidateCount$delegate).getIntValue()) {
                    VectorPainter vectorPainter2 = VectorPainter.this;
                    ((SnapshotMutableIntStateImpl) vectorPainter2.invalidateCount$delegate).setIntValue(((SnapshotMutableIntStateImpl) vectorPainter2.invalidateCount$delegate).getIntValue() + 1);
                }
                return Unit.INSTANCE;
            }
        };
        this.vector = vectorComponent;
        this.invalidateCount$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
        this.currentAlpha = 1.0f;
        this.drawCount = -1;
    }

    public /* synthetic */ VectorPainter(GroupComponent groupComponent, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new GroupComponent() : groupComponent);
    }
}
