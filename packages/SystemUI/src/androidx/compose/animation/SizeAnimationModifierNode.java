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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SizeAnimationModifierNode extends LayoutModifierNodeWithPassThroughIntrinsics {
    public Alignment alignment;
    public final MutableState animData$delegate;
    public AnimationSpec animationSpec;
    public Function2 listener;
    public long lookaheadConstraints;
    public boolean lookaheadConstraintsAvailable;
    public long lookaheadSize;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            return Intrinsics.areEqual(this.anim, animData.anim) && IntSize.m861equalsimpl0(this.startSize, animData.startSize);
        }

        public final int hashCode() {
            int hashCode = this.anim.hashCode() * 31;
            long j = this.startSize;
            IntSize.Companion companion = IntSize.Companion;
            return Long.hashCode(j) + hashCode;
        }

        public final String toString() {
            return "AnimData(anim=" + this.anim + ", startSize=" + ((Object) IntSize.m862toStringimpl(this.startSize)) + ')';
        }

        private AnimData(Animatable<IntSize, AnimationVector2D> animatable, long j) {
            this.anim = animatable;
            this.startSize = j;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SizeAnimationModifierNode(androidx.compose.animation.core.AnimationSpec r1, androidx.compose.ui.Alignment r2, kotlin.jvm.functions.Function2 r3, int r4, kotlin.jvm.internal.DefaultConstructorMarker r5) {
        /*
            r0 = this;
            r5 = r4 & 2
            if (r5 == 0) goto Lb
            androidx.compose.ui.Alignment$Companion r2 = androidx.compose.ui.Alignment.Companion
            r2.getClass()
            androidx.compose.ui.BiasAlignment r2 = androidx.compose.ui.Alignment.Companion.TopStart
        Lb:
            r4 = r4 & 4
            if (r4 == 0) goto L10
            r3 = 0
        L10:
            r0.<init>(r1, r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.SizeAnimationModifierNode.<init>(androidx.compose.animation.core.AnimationSpec, androidx.compose.ui.Alignment, kotlin.jvm.functions.Function2, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo4measure3p2s80s(final MeasureScope measureScope, Measurable measurable, long j) {
        Placeable mo608measureBRTryo0;
        char c;
        long j2;
        AnimData animData;
        long m829constrain4WqzIAM;
        AnimData animData2;
        MeasureResult layout$1;
        if (measureScope.isLookingAhead()) {
            this.lookaheadConstraints = j;
            this.lookaheadConstraintsAvailable = true;
            mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
        } else {
            mo608measureBRTryo0 = measurable.mo608measureBRTryo0(this.lookaheadConstraintsAvailable ? this.lookaheadConstraints : j);
        }
        final Placeable placeable = mo608measureBRTryo0;
        final long j3 = (placeable.height & 4294967295L) | (placeable.width << 32);
        IntSize.Companion companion = IntSize.Companion;
        if (measureScope.isLookingAhead()) {
            this.lookaheadSize = j3;
            c = ' ';
            m829constrain4WqzIAM = j3;
            j2 = 4294967295L;
        } else {
            long j4 = !IntSize.m861equalsimpl0(this.lookaheadSize, AnimationModifierKt.InvalidSize) ? this.lookaheadSize : j3;
            MutableState mutableState = this.animData$delegate;
            AnimData animData3 = (AnimData) ((SnapshotMutableStateImpl) mutableState).getValue();
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (animData3 != null) {
                Animatable animatable = animData3.anim;
                c = ' ';
                j2 = 4294967295L;
                boolean z = (IntSize.m861equalsimpl0(j4, ((IntSize) animatable.internalState.getValue()).packedValue) || animatable.isRunning()) ? false : true;
                if (!IntSize.m861equalsimpl0(j4, ((IntSize) ((SnapshotMutableStateImpl) animatable.targetValue$delegate).getValue()).packedValue) || z) {
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
                animData = new AnimData(new Animatable(IntSize.m859boximpl(j4), VectorConvertersKt.IntSizeToVector, IntSize.m859boximpl((j5 & 4294967295L) | (j5 << 32)), null, 8, null), j4, defaultConstructorMarker);
            }
            ((SnapshotMutableStateImpl) mutableState).setValue(animData);
            m829constrain4WqzIAM = ConstraintsKt.m829constrain4WqzIAM(j, ((IntSize) animData.anim.internalState.getValue()).packedValue);
        }
        final int i = (int) (m829constrain4WqzIAM >> c);
        final int i2 = (int) (m829constrain4WqzIAM & j2);
        layout$1 = measureScope.layout$1(i, i2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.animation.SizeAnimationModifierNode$measure$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                IntSize.Companion companion2 = IntSize.Companion;
                Placeable.PlacementScope.m626place70tqf50$default((Placeable.PlacementScope) obj, placeable, SizeAnimationModifierNode.this.alignment.mo352alignKFBX0sM(j3, (i << 32) | (i2 & 4294967295L), measureScope.getLayoutDirection()));
                return Unit.INSTANCE;
            }
        });
        return layout$1;
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
