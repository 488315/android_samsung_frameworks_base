package androidx.compose.foundation.lazy.layout;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsInfo;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.BeyondBoundsLayout;
import androidx.compose.ui.layout.BeyondBoundsLayoutKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.modifier.ModifierLocal;
import androidx.compose.ui.modifier.ModifierLocalMap;
import androidx.compose.ui.modifier.ModifierLocalModifierNode;
import androidx.compose.ui.modifier.SingleLocalMap;
import androidx.compose.ui.node.DelegatableNodeKt;
import androidx.compose.ui.node.LayoutModifierNode;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyLayoutBeyondBoundsModifierNode extends Modifier.Node implements ModifierLocalModifierNode, BeyondBoundsLayout, LayoutModifierNode {
    public static final LazyLayoutBeyondBoundsModifierNode$Companion$emptyBeyondBoundsScope$1 emptyBeyondBoundsScope;
    public LazyLayoutBeyondBoundsInfo beyondBoundsInfo;
    public Orientation orientation;
    public boolean reverseLayout;
    public LazyLayoutBeyondBoundsState state;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LayoutDirection.values().length];
            try {
                iArr[LayoutDirection.Ltr.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LayoutDirection.Rtl.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsModifierNode$Companion$emptyBeyondBoundsScope$1] */
    static {
        new Companion(null);
        emptyBeyondBoundsScope = new BeyondBoundsLayout.BeyondBoundsScope() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsModifierNode$Companion$emptyBeyondBoundsScope$1
            @Override // androidx.compose.ui.layout.BeyondBoundsLayout.BeyondBoundsScope
            public final boolean getHasMoreContent() {
                return false;
            }
        };
    }

    public LazyLayoutBeyondBoundsModifierNode(LazyLayoutBeyondBoundsState lazyLayoutBeyondBoundsState, LazyLayoutBeyondBoundsInfo lazyLayoutBeyondBoundsInfo, boolean z, Orientation orientation) {
        this.state = lazyLayoutBeyondBoundsState;
        this.beyondBoundsInfo = lazyLayoutBeyondBoundsInfo;
        this.reverseLayout = z;
        this.orientation = orientation;
    }

    @Override // androidx.compose.ui.modifier.ModifierLocalModifierNode
    public final ModifierLocalMap getProvidedValues() {
        Pair pair = new Pair(BeyondBoundsLayoutKt.ModifierLocalBeyondBoundsLayout, this);
        SingleLocalMap singleLocalMap = new SingleLocalMap((ModifierLocal) pair.getFirst());
        ModifierLocal modifierLocal = (ModifierLocal) pair.getFirst();
        Object second = pair.getSecond();
        if (modifierLocal != singleLocalMap.key) {
            InlineClassHelperKt.throwIllegalStateException("Check failed.");
        }
        ((SnapshotMutableStateImpl) singleLocalMap.value$delegate).setValue(second);
        return singleLocalMap;
    }

    /* renamed from: hasMoreContent-FR3nfPY, reason: not valid java name */
    public final boolean m164hasMoreContentFR3nfPY(LazyLayoutBeyondBoundsInfo.Interval interval, int i) {
        BeyondBoundsLayout.LayoutDirection.Companion.getClass();
        if (i == BeyondBoundsLayout.LayoutDirection.Above || i == BeyondBoundsLayout.LayoutDirection.Below) {
            if (this.orientation == Orientation.Horizontal) {
                return false;
            }
        } else if (i == BeyondBoundsLayout.LayoutDirection.Left || i == BeyondBoundsLayout.LayoutDirection.Right) {
            if (this.orientation == Orientation.Vertical) {
                return false;
            }
        } else if (i != BeyondBoundsLayout.LayoutDirection.Before && i != BeyondBoundsLayout.LayoutDirection.After) {
            throw new IllegalStateException("Lazy list does not support beyond bounds layout for the specified direction");
        }
        if (m165isForward4vf7U8o(i)) {
            if (interval.end >= this.state.getItemCount() - 1) {
                return false;
            }
        } else if (interval.start <= 0) {
            return false;
        }
        return true;
    }

    /* renamed from: isForward-4vf7U8o, reason: not valid java name */
    public final boolean m165isForward4vf7U8o(int i) {
        BeyondBoundsLayout.LayoutDirection.Companion.getClass();
        if (i == BeyondBoundsLayout.LayoutDirection.Before) {
            return false;
        }
        if (i != BeyondBoundsLayout.LayoutDirection.After) {
            if (i == BeyondBoundsLayout.LayoutDirection.Above) {
                return this.reverseLayout;
            }
            if (i == BeyondBoundsLayout.LayoutDirection.Below) {
                if (this.reverseLayout) {
                    return false;
                }
            } else if (i == BeyondBoundsLayout.LayoutDirection.Left) {
                int i2 = WhenMappings.$EnumSwitchMapping$0[DelegatableNodeKt.requireLayoutNode(this).layoutDirection.ordinal()];
                if (i2 == 1) {
                    return this.reverseLayout;
                }
                if (i2 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                if (this.reverseLayout) {
                    return false;
                }
            } else {
                if (i != BeyondBoundsLayout.LayoutDirection.Right) {
                    throw new IllegalStateException("Lazy list does not support beyond bounds layout for the specified direction");
                }
                int i3 = WhenMappings.$EnumSwitchMapping$0[DelegatableNodeKt.requireLayoutNode(this).layoutDirection.ordinal()];
                if (i3 != 1) {
                    if (i3 == 2) {
                        return this.reverseLayout;
                    }
                    throw new NoWhenBranchMatchedException();
                }
                if (this.reverseLayout) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        MeasureResult layout$1;
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
        layout$1 = measureScope.layout$1(mo608measureBRTryo0.width, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsModifierNode$measure$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((Placeable.PlacementScope) obj).place(Placeable.this, 0, 0, 0.0f);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }
}
