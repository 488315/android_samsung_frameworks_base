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

/* loaded from: classes.dex */
public final class BounceableNode extends Modifier.Node implements LayoutModifierNode {
    public boolean bounceEnd;
    public Bounceable bounceable;
    public Bounceable nextBounceable;
    public Orientation orientation;
    public Bounceable previousBounceable;

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
        int i = BounceableKt.WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i != 1) {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            if (!Constraints.m820getHasFixedHeightimpl(j)) {
                throw new IllegalStateException("Modifier.bounceable() should receive a fixed height from its parent. Make sure that it is used *after* a fixed-height Modifier in the vertical axis (like Modifier.fillMaxHeight() or Modifier.height()).");
            }
        } else if (!Constraints.m821getHasFixedWidthimpl(j)) {
            throw new IllegalStateException("Modifier.bounceable() should receive a fixed width from its parent. Make sure that it is used *after* a fixed-width Modifier in the horizontal axis (like Modifier.fillMaxWidth() or Modifier.width()).");
        }
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        Bounceable bounceable = this.previousBounceable;
        if (bounceable != null) {
            ref$FloatRef.element = (measureScope.mo58toPx0680j_4(((BounceableTileViewModel) this.bounceable).m2909getBounceD9Ej5fM()) - measureScope.mo58toPx0680j_4(((BounceableTileViewModel) bounceable).m2909getBounceD9Ej5fM())) + ref$FloatRef.element;
        }
        Bounceable bounceable2 = this.nextBounceable;
        float fMo58toPx0680j_4 = 0.0f;
        if (bounceable2 != null) {
            fMo58toPx0680j_4 = 0.0f + (measureScope.mo58toPx0680j_4(((BounceableTileViewModel) this.bounceable).m2909getBounceD9Ej5fM()) - measureScope.mo58toPx0680j_4(((BounceableTileViewModel) bounceable2).m2909getBounceD9Ej5fM()));
        } else if (this.bounceEnd) {
            fMo58toPx0680j_4 = 0.0f + measureScope.mo58toPx0680j_4(((BounceableTileViewModel) this.bounceable).m2909getBounceD9Ej5fM());
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[this.orientation.ordinal()];
        if (i2 == 1) {
            int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
            int iRoundToInt = MathKt__MathJVMKt.roundToInt(iM823getMaxWidthimpl + ref$FloatRef.element + fMo58toPx0680j_4);
            final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(j, iRoundToInt, iRoundToInt, 0, 0, 12));
            final int i3 = 0;
            return measureScope.layout$1(iM823getMaxWidthimpl, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.compose.animation.BounceableNode$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                    switch (i3) {
                        case 0:
                            placementScope.placeRelative(placeableMo610measureBRTryo0, -MathKt__MathJVMKt.roundToInt(ref$FloatRef.element), 0, 0.0f);
                            break;
                        default:
                            placementScope.placeRelative(placeableMo610measureBRTryo0, 0, -MathKt__MathJVMKt.roundToInt(ref$FloatRef.element), 0.0f);
                            break;
                    }
                    return Unit.INSTANCE;
                }
            });
        }
        if (i2 != 2) {
            throw new NoWhenBranchMatchedException();
        }
        int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(j);
        int iRoundToInt2 = MathKt__MathJVMKt.roundToInt(iM822getMaxHeightimpl + ref$FloatRef.element + fMo58toPx0680j_4);
        final Placeable placeableMo610measureBRTryo02 = measurable.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(j, 0, 0, iRoundToInt2, iRoundToInt2, 3));
        final int i4 = 1;
        return measureScope.layout$1(placeableMo610measureBRTryo02.width, iM822getMaxHeightimpl, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.compose.animation.BounceableNode$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                switch (i4) {
                    case 0:
                        placementScope.placeRelative(placeableMo610measureBRTryo02, -MathKt__MathJVMKt.roundToInt(ref$FloatRef.element), 0, 0.0f);
                        break;
                    default:
                        placementScope.placeRelative(placeableMo610measureBRTryo02, 0, -MathKt__MathJVMKt.roundToInt(ref$FloatRef.element), 0.0f);
                        break;
                }
                return Unit.INSTANCE;
            }
        });
    }

    public BounceableNode(Bounceable bounceable, Bounceable bounceable2, Bounceable bounceable3, Orientation orientation, boolean z) {
        this.bounceable = bounceable;
        this.previousBounceable = bounceable2;
        this.nextBounceable = bounceable3;
        this.orientation = orientation;
        this.bounceEnd = z;
    }
}
