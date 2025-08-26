package androidx.compose.animation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationVector2D;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes.dex */
final class SizeAnimationModifierNode extends LayoutModifierNodeWithPassThroughIntrinsics {
    public Alignment alignment;
    public final MutableState animData$delegate;
    public AnimationSpec animationSpec;
    public Function2 listener;
    public long lookaheadConstraints;
    public boolean lookaheadConstraintsAvailable;
    public long lookaheadSize;

    public final class AnimData {
        public final Animatable anim;
        public long startSize;

        public /* synthetic */ AnimData(Animatable animatable, long j, DefaultConstructorMarker defaultConstructorMarker) {
            this(animatable, j);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AnimData)) {
                return false;
            }
            AnimData animData = (AnimData) obj;
            return Intrinsics.areEqual(this.anim, animData.anim) && IntSize.m863equalsimpl0(this.startSize, animData.startSize);
        }

        public final int hashCode() {
            int iHashCode = this.anim.hashCode() * 31;
            long j = this.startSize;
            IntSize.Companion companion = IntSize.Companion;
            return Long.hashCode(j) + iHashCode;
        }

        public final String toString() {
            return "AnimData(anim=" + this.anim + ", startSize=" + ((Object) IntSize.m864toStringimpl(this.startSize)) + ')';
        }

        private AnimData(Animatable<IntSize, AnimationVector2D> animatable, long j) {
            this.anim = animatable;
            this.startSize = j;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SizeAnimationModifierNode(AnimationSpec animationSpec, Alignment alignment, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            Alignment.Companion.getClass();
            alignment = Alignment.Companion.TopStart;
        }
        this(animationSpec, alignment, (i & 4) != 0 ? null : function2);
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(final MeasureScope measureScope, Measurable measurable, long j) {
        Placeable placeableMo610measureBRTryo0;
        char c;
        long j2;
        AnimData animData;
        long jM831constrain4WqzIAM;
        AnimData animData2;
        if (measureScope.isLookingAhead()) {
            this.lookaheadConstraints = j;
            this.lookaheadConstraintsAvailable = true;
            placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
        } else {
            placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(this.lookaheadConstraintsAvailable ? this.lookaheadConstraints : j);
        }
        final Placeable placeable = placeableMo610measureBRTryo0;
        final long j3 = (placeable.height & 4294967295L) | (placeable.width << 32);
        IntSize.Companion companion = IntSize.Companion;
        if (measureScope.isLookingAhead()) {
            this.lookaheadSize = j3;
            c = ' ';
            jM831constrain4WqzIAM = j3;
            j2 = 4294967295L;
        } else {
            long j4 = !IntSize.m863equalsimpl0(this.lookaheadSize, AnimationModifierKt.InvalidSize) ? this.lookaheadSize : j3;
            MutableState mutableState = this.animData$delegate;
            AnimData animData3 = (AnimData) ((SnapshotMutableStateImpl) mutableState).getValue();
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (animData3 != null) {
                Animatable animatable = animData3.anim;
                c = ' ';
                j2 = 4294967295L;
                boolean z = (IntSize.m863equalsimpl0(j4, ((IntSize) animatable.internalState.getValue()).packedValue) || animatable.isRunning()) ? false : true;
                if (!IntSize.m863equalsimpl0(j4, ((IntSize) ((SnapshotMutableStateImpl) animatable.targetValue$delegate).getValue()).packedValue) || z) {
                    animData3.startSize = ((IntSize) animatable.internalState.getValue()).packedValue;
                    animData2 = animData3;
                    BuildersKt.launch$default(getCoroutineScope(), null, null, new SizeAnimationModifierNode$animateTo$data$1$1(animData2, j4, this, null), 3);
                } else {
                    animData2 = animData3;
                }
                animData = animData2;
            } else {
                c = ' ';
                j2 = 4294967295L;
                long j5 = 1;
                animData = new AnimData(new Animatable(IntSize.m861boximpl(j4), VectorConvertersKt.IntSizeToVector, IntSize.m861boximpl((j5 & 4294967295L) | (j5 << 32)), null, 8, null), j4, defaultConstructorMarker);
            }
            ((SnapshotMutableStateImpl) mutableState).setValue(animData);
            jM831constrain4WqzIAM = ConstraintsKt.m831constrain4WqzIAM(j, ((IntSize) animData.anim.internalState.getValue()).packedValue);
        }
        final int i = (int) (jM831constrain4WqzIAM >> c);
        final int i2 = (int) (jM831constrain4WqzIAM & j2);
        return measureScope.layout$1(i, i2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.animation.SizeAnimationModifierNode$measure$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                IntSize.Companion companion2 = IntSize.Companion;
                Placeable.PlacementScope.m628place70tqf50$default((Placeable.PlacementScope) obj, placeable, this.this$0.alignment.mo353alignKFBX0sM(j3, (i << 32) | (i2 & 4294967295L), measureScope.getLayoutDirection()));
                return Unit.INSTANCE;
            }
        });
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onAttach() {
        this.lookaheadSize = AnimationModifierKt.InvalidSize;
        this.lookaheadConstraintsAvailable = false;
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onReset() {
        ((SnapshotMutableStateImpl) this.animData$delegate).setValue(null);
    }

    public SizeAnimationModifierNode(AnimationSpec<IntSize> animationSpec, Alignment alignment, Function2 function2) {
        this.animationSpec = animationSpec;
        this.alignment = alignment;
        this.listener = function2;
        this.lookaheadSize = AnimationModifierKt.InvalidSize;
        this.lookaheadConstraints = ConstraintsKt.Constraints$default(0, 0, 0, 0, 15);
        this.animData$delegate = SnapshotStateKt.mutableStateOf$default(null);
    }
}
