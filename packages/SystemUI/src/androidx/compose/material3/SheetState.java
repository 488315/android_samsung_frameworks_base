package androidx.compose.material3;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.foundation.MutatePriority;
import androidx.compose.material3.internal.AnchoredDraggableState;
import androidx.compose.material3.internal.DraggableAnchors;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.unit.Density;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SheetState {
    public static final Companion Companion = new Companion(null);
    public AnimationSpec anchoredDraggableMotionSpec;
    public final AnchoredDraggableState anchoredDraggableState;
    public final Function1 confirmValueChange;
    public FiniteAnimationSpec hideMotionSpec;
    public FiniteAnimationSpec showMotionSpec;
    public final boolean skipHiddenState;
    public final boolean skipPartiallyExpanded;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public SheetState(boolean z, final Function0 function0, Function0 function02, SheetValue sheetValue, Function1 function1, boolean z2) {
        this.skipPartiallyExpanded = z;
        this.confirmValueChange = function1;
        this.skipHiddenState = z2;
        if (z && sheetValue == SheetValue.PartiallyExpanded) {
            throw new IllegalArgumentException("The initial value must not be set to PartiallyExpanded if skipPartiallyExpanded is set to true.");
        }
        if (z2 && sheetValue == SheetValue.Hidden) {
            throw new IllegalArgumentException("The initial value must not be set to Hidden if skipHiddenState is set to true.");
        }
        this.anchoredDraggableMotionSpec = SheetDefaultsKt.BottomSheetAnimationSpec;
        this.anchoredDraggableState = new AnchoredDraggableState(sheetValue, new Function1() { // from class: androidx.compose.material3.SheetState$anchoredDraggableState$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((Number) obj).floatValue();
                return (Float) Function0.this.invoke();
            }
        }, function02, new Function0() { // from class: androidx.compose.material3.SheetState$anchoredDraggableState$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return SheetState.this.anchoredDraggableMotionSpec;
            }
        }, function1);
        this.showMotionSpec = AnimationSpecKt.snap$default();
        this.hideMotionSpec = AnimationSpecKt.snap$default();
    }

    public static Object animateTo$material3_release$default(SheetState sheetState, SheetValue sheetValue, FiniteAnimationSpec finiteAnimationSpec, SuspendLambda suspendLambda) {
        float floatValue = ((SnapshotMutableFloatStateImpl) sheetState.anchoredDraggableState.lastVelocity$delegate).getFloatValue();
        sheetState.getClass();
        SheetState$animateTo$2 sheetState$animateTo$2 = new SheetState$animateTo$2(sheetState, floatValue, finiteAnimationSpec, null);
        AnchoredDraggableState anchoredDraggableState = sheetState.anchoredDraggableState;
        int i = AnchoredDraggableState.$r8$clinit;
        Object anchoredDrag = anchoredDraggableState.anchoredDrag(sheetValue, MutatePriority.Default, sheetState$animateTo$2, suspendLambda);
        return anchoredDrag == CoroutineSingletons.COROUTINE_SUSPENDED ? anchoredDrag : Unit.INSTANCE;
    }

    public final Object expand(SuspendLambda suspendLambda) {
        SheetValue sheetValue = SheetValue.Expanded;
        if (!((Boolean) this.confirmValueChange.mo779invoke(sheetValue)).booleanValue()) {
            return Unit.INSTANCE;
        }
        Object animateTo$material3_release$default = animateTo$material3_release$default(this, sheetValue, this.showMotionSpec, suspendLambda);
        return animateTo$material3_release$default == CoroutineSingletons.COROUTINE_SUSPENDED ? animateTo$material3_release$default : Unit.INSTANCE;
    }

    public final Object hide(SuspendLambda suspendLambda) {
        if (this.skipHiddenState) {
            throw new IllegalStateException("Attempted to animate to hidden when skipHiddenState was enabled. Set skipHiddenState to false to use this function.");
        }
        SheetValue sheetValue = SheetValue.Hidden;
        if (!((Boolean) this.confirmValueChange.mo779invoke(sheetValue)).booleanValue()) {
            return Unit.INSTANCE;
        }
        Object animateTo$material3_release$default = animateTo$material3_release$default(this, sheetValue, this.hideMotionSpec, suspendLambda);
        return animateTo$material3_release$default == CoroutineSingletons.COROUTINE_SUSPENDED ? animateTo$material3_release$default : Unit.INSTANCE;
    }

    public final boolean isVisible() {
        return ((SnapshotMutableStateImpl) this.anchoredDraggableState.currentValue$delegate).getValue() != SheetValue.Hidden;
    }

    public final Object partialExpand(SuspendLambda suspendLambda) {
        if (this.skipPartiallyExpanded) {
            throw new IllegalStateException("Attempted to animate to partial expanded when skipPartiallyExpanded was enabled. Set skipPartiallyExpanded to false to use this function.");
        }
        SheetValue sheetValue = SheetValue.PartiallyExpanded;
        if (!((Boolean) this.confirmValueChange.mo779invoke(sheetValue)).booleanValue()) {
            return Unit.INSTANCE;
        }
        Object animateTo$material3_release$default = animateTo$material3_release$default(this, sheetValue, this.showMotionSpec, suspendLambda);
        return animateTo$material3_release$default == CoroutineSingletons.COROUTINE_SUSPENDED ? animateTo$material3_release$default : Unit.INSTANCE;
    }

    public final Object show(SuspendLambda suspendLambda) {
        DraggableAnchors anchors = this.anchoredDraggableState.getAnchors();
        SheetValue sheetValue = SheetValue.PartiallyExpanded;
        if (!anchors.hasAnchorFor(sheetValue)) {
            sheetValue = SheetValue.Expanded;
        }
        if (!((Boolean) this.confirmValueChange.mo779invoke(sheetValue)).booleanValue()) {
            return Unit.INSTANCE;
        }
        Object animateTo$material3_release$default = animateTo$material3_release$default(this, sheetValue, this.showMotionSpec, suspendLambda);
        return animateTo$material3_release$default == CoroutineSingletons.COROUTINE_SUSPENDED ? animateTo$material3_release$default : Unit.INSTANCE;
    }

    public /* synthetic */ SheetState(boolean z, Function0 function0, Function0 function02, SheetValue sheetValue, Function1 function1, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, function0, function02, (i & 8) != 0 ? SheetValue.Hidden : sheetValue, (i & 16) != 0 ? new Function1() { // from class: androidx.compose.material3.SheetState.1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                return Boolean.TRUE;
            }
        } : function1, (i & 32) != 0 ? false : z2);
    }

    public /* synthetic */ SheetState(boolean z, Density density, SheetValue sheetValue, Function1 function1, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, density, (i & 4) != 0 ? SheetValue.Hidden : sheetValue, (i & 8) != 0 ? new Function1() { // from class: androidx.compose.material3.SheetState.4
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo779invoke(Object obj) {
                return Boolean.TRUE;
            }
        } : function1, (i & 16) != 0 ? false : z2);
    }

    public /* synthetic */ SheetState(boolean z, final Density density, SheetValue sheetValue, Function1 function1, boolean z2) {
        this(z, new Function0() { // from class: androidx.compose.material3.SheetState.5
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Density density2 = Density.this;
                BottomSheetDefaults.INSTANCE.getClass();
                return Float.valueOf(density2.mo57toPx0680j_4(BottomSheetDefaults.PositionalThreshold));
            }
        }, new Function0() { // from class: androidx.compose.material3.SheetState.6
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Density density2 = Density.this;
                BottomSheetDefaults.INSTANCE.getClass();
                return Float.valueOf(density2.mo57toPx0680j_4(BottomSheetDefaults.VelocityThreshold));
            }
        }, sheetValue, function1, z2);
    }
}
