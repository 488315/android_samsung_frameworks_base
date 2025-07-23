package com.android.compose.animation;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.Constraints;
import com.android.compose.animation.BounceableKt;
import com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BounceableNode extends Modifier.Node implements LayoutModifierNode {
    public boolean bounceEnd;
    public Bounceable bounceable;
    public Bounceable nextBounceable;
    public Orientation orientation;
    public Bounceable previousBounceable;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Orientation.values().length];
            try {
                iArr[Orientation.Horizontal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Orientation.Vertical.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ BounceableNode(Bounceable bounceable, Bounceable bounceable2, Bounceable bounceable3, Orientation orientation, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bounceable, bounceable2, bounceable3, orientation, (i & 16) != 0 ? bounceable3 != null : z);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        MeasureResult layout$12;
        int i = BounceableKt.WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i != 1) {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            if (!Constraints.m818getHasFixedHeightimpl(j)) {
                throw new IllegalStateException("Modifier.bounceable() should receive a fixed height from its parent. Make sure that it is used *after* a fixed-height Modifier in the vertical axis (like Modifier.fillMaxHeight() or Modifier.height()).");
            }
        } else if (!Constraints.m819getHasFixedWidthimpl(j)) {
            throw new IllegalStateException("Modifier.bounceable() should receive a fixed width from its parent. Make sure that it is used *after* a fixed-width Modifier in the horizontal axis (like Modifier.fillMaxWidth() or Modifier.width()).");
        }
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        Bounceable bounceable = this.previousBounceable;
        if (bounceable != null) {
            ref$FloatRef.element = (measureScope.mo57toPx0680j_4(((BounceableTileViewModel) this.bounceable).m2892getBounceD9Ej5fM()) - measureScope.mo57toPx0680j_4(((BounceableTileViewModel) bounceable).m2892getBounceD9Ej5fM())) + ref$FloatRef.element;
        }
        Bounceable bounceable2 = this.nextBounceable;
        float f = 0.0f;
        if (bounceable2 != null) {
            f = 0.0f + (measureScope.mo57toPx0680j_4(((BounceableTileViewModel) this.bounceable).m2892getBounceD9Ej5fM()) - measureScope.mo57toPx0680j_4(((BounceableTileViewModel) bounceable2).m2892getBounceD9Ej5fM()));
        } else if (this.bounceEnd) {
            f = 0.0f + measureScope.mo57toPx0680j_4(((BounceableTileViewModel) this.bounceable).m2892getBounceD9Ej5fM());
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i2 == 1) {
            int m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j);
            int roundToInt = MathKt__MathJVMKt.roundToInt(m821getMaxWidthimpl + ref$FloatRef.element + f);
            final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(Constraints.m814copyZbe2FdA$default(j, roundToInt, roundToInt, 0, 0, 12));
            final int i3 = 0;
            layout$1 = measureScope.layout$1(m821getMaxWidthimpl, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.compose.animation.BounceableNode$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                    switch (i3) {
                        case 0:
                            placementScope.placeRelative(mo608measureBRTryo0, -MathKt__MathJVMKt.roundToInt(ref$FloatRef.element), 0, 0.0f);
                            break;
                        default:
                            placementScope.placeRelative(mo608measureBRTryo0, 0, -MathKt__MathJVMKt.roundToInt(ref$FloatRef.element), 0.0f);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            });
            return layout$1;
        }
        if (i2 != 2) {
            throw new NoWhenBranchMatchedException();
        }
        int m820getMaxHeightimpl = Constraints.m820getMaxHeightimpl(j);
        int roundToInt2 = MathKt__MathJVMKt.roundToInt(m820getMaxHeightimpl + ref$FloatRef.element + f);
        final Placeable mo608measureBRTryo02 = measurable.mo608measureBRTryo0(Constraints.m814copyZbe2FdA$default(j, 0, 0, roundToInt2, roundToInt2, 3));
        final int i4 = 1;
        layout$12 = measureScope.layout$1(mo608measureBRTryo02.width, m820getMaxHeightimpl, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.compose.animation.BounceableNode$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                switch (i4) {
                    case 0:
                        placementScope.placeRelative(mo608measureBRTryo02, -MathKt__MathJVMKt.roundToInt(ref$FloatRef.element), 0, 0.0f);
                        break;
                    default:
                        placementScope.placeRelative(mo608measureBRTryo02, 0, -MathKt__MathJVMKt.roundToInt(ref$FloatRef.element), 0.0f);
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        return layout$12;
    }

    public BounceableNode(Bounceable bounceable, Bounceable bounceable2, Bounceable bounceable3, Orientation orientation, boolean z) {
        this.bounceable = bounceable;
        this.previousBounceable = bounceable2;
        this.nextBounceable = bounceable3;
        this.orientation = orientation;
        this.bounceEnd = z;
    }
}
