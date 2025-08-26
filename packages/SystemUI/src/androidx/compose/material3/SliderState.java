package androidx.compose.material3;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.MutatorMutex;
import androidx.compose.foundation.gestures.DragScope;
import androidx.compose.foundation.gestures.DraggableState;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.util.MathHelpersKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes.dex */
public final class SliderState implements DraggableState {
    public final SliderState$dragScope$1 dragScope;
    public final Function0 gestureEndAction;
    public final MutableState isDragging$delegate;
    public boolean isRtl;
    public Function1 onValueChange;
    public Function0 onValueChangeFinished;
    public Orientation orientation;
    public final MutableFloatState pressOffset$delegate;
    public final MutableFloatState rawOffset$delegate;
    public boolean reverseVerticalDirection;
    public final MutatorMutex scrollMutex;
    public final int steps;
    public final MutableIntState thumbHeight$delegate;
    public final MutableIntState thumbWidth$delegate;
    public final float[] tickFractions;
    public final MutableIntState totalHeight$delegate;
    public final MutableIntState totalWidth$delegate;
    public final MutableIntState trackHeight$delegate;
    public final MutableIntState trackWidth$delegate;
    public final ClosedFloatingPointRange valueRange;
    public final MutableFloatState valueState$delegate;

    /* renamed from: androidx.compose.material3.SliderState$drag$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $block;
        final /* synthetic */ MutatePriority $dragPriority;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$dragPriority = mutatePriority;
            this.$block = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SliderState.this.new AnonymousClass2(this.$dragPriority, this.$block, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ((SnapshotMutableStateImpl) SliderState.this.isDragging$delegate).setValue(Boolean.TRUE);
                SliderState sliderState = SliderState.this;
                MutatorMutex mutatorMutex = sliderState.scrollMutex;
                MutatePriority mutatePriority = this.$dragPriority;
                Function2 function2 = this.$block;
                this.label = 1;
                if (mutatorMutex.mutateWith(sliderState.dragScope, mutatePriority, function2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            ((SnapshotMutableStateImpl) SliderState.this.isDragging$delegate).setValue(Boolean.FALSE);
            return Unit.INSTANCE;
        }
    }

    public SliderState() {
        this(0.0f, 0, null, null, 15, null);
    }

    public final void dispatchRawDelta(float f) {
        float fMax;
        float fMin;
        if (this.orientation == Orientation.Vertical) {
            float intValue = ((SnapshotMutableIntStateImpl) this.totalHeight$delegate).getIntValue();
            MutableIntState mutableIntState = this.thumbHeight$delegate;
            fMax = Math.max(intValue - (((SnapshotMutableIntStateImpl) mutableIntState).getIntValue() / 2.0f), 0.0f);
            fMin = Math.min(((SnapshotMutableIntStateImpl) mutableIntState).getIntValue() / 2.0f, fMax);
        } else {
            float intValue2 = ((SnapshotMutableIntStateImpl) this.totalWidth$delegate).getIntValue();
            MutableIntState mutableIntState2 = this.thumbWidth$delegate;
            fMax = Math.max(intValue2 - (((SnapshotMutableIntStateImpl) mutableIntState2).getIntValue() / 2.0f), 0.0f);
            fMin = Math.min(((SnapshotMutableIntStateImpl) mutableIntState2).getIntValue() / 2.0f, fMax);
        }
        SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) this.rawOffset$delegate;
        float floatValue = snapshotMutableFloatStateImpl.getFloatValue() + f;
        MutableFloatState mutableFloatState = this.pressOffset$delegate;
        snapshotMutableFloatStateImpl.setFloatValue(((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue() + floatValue);
        ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(0.0f);
        float fAccess$snapValueToTick = SliderKt.access$snapValueToTick(snapshotMutableFloatStateImpl.getFloatValue(), fMin, fMax, this.tickFractions);
        ClosedFloatRange closedFloatRange = (ClosedFloatRange) this.valueRange;
        float f2 = fMax - fMin;
        float fLerp = MathHelpersKt.lerp(closedFloatRange._start, closedFloatRange._endInclusive, RangesKt___RangesKt.coerceIn(f2 == 0.0f ? 0.0f : (fAccess$snapValueToTick - fMin) / f2, 0.0f, 1.0f));
        if (fLerp == getValue()) {
            return;
        }
        Function1 function1 = this.onValueChange;
        if (function1 != null) {
            function1.mo781invoke(Float.valueOf(fLerp));
        } else {
            setValue(fLerp);
        }
    }

    @Override // androidx.compose.foundation.gestures.DraggableState
    public final Object drag(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass2(mutatePriority, function2, null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }

    public final float getCoercedValueAsFraction() {
        ClosedFloatRange closedFloatRange = (ClosedFloatRange) this.valueRange;
        float f = closedFloatRange._start;
        float f2 = closedFloatRange._endInclusive;
        float fCoerceIn = RangesKt___RangesKt.coerceIn(getValue(), closedFloatRange._start, closedFloatRange._endInclusive);
        float f3 = SliderKt.TrackHeight;
        float f4 = f2 - f;
        return RangesKt___RangesKt.coerceIn(f4 == 0.0f ? 0.0f : (fCoerceIn - f) / f4, 0.0f, 1.0f);
    }

    public final float getValue() {
        return ((SnapshotMutableFloatStateImpl) this.valueState$delegate).getFloatValue();
    }

    public final void setValue(float f) {
        ClosedFloatRange closedFloatRange = (ClosedFloatRange) this.valueRange;
        ((SnapshotMutableFloatStateImpl) this.valueState$delegate).setFloatValue(SliderKt.access$snapValueToTick(RangesKt___RangesKt.coerceIn(f, closedFloatRange._start, closedFloatRange._endInclusive), closedFloatRange._start, closedFloatRange._endInclusive, this.tickFractions));
    }

    public SliderState(float f, int i, Function0 function0, ClosedFloatingPointRange closedFloatingPointRange, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0.0f : f, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? null : function0, (i2 & 8) != 0 ? new ClosedFloatRange(0.0f, 1.0f) : closedFloatingPointRange);
    }

    /* JADX WARN: Type inference failed for: r5v8, types: [androidx.compose.material3.SliderState$dragScope$1] */
    public SliderState(float f, int i, Function0 function0, ClosedFloatingPointRange closedFloatingPointRange) {
        float[] fArr;
        this.steps = i;
        this.onValueChangeFinished = function0;
        this.valueRange = closedFloatingPointRange;
        this.valueState$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
        float f2 = SliderKt.TrackHeight;
        if (i == 0) {
            fArr = new float[0];
        } else {
            int i2 = i + 2;
            float[] fArr2 = new float[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                fArr2[i3] = i3 / (i + 1);
            }
            fArr = fArr2;
        }
        this.tickFractions = fArr;
        this.totalWidth$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
        this.totalHeight$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
        this.thumbWidth$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
        this.thumbHeight$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
        this.trackWidth$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
        this.trackHeight$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
        this.orientation = Orientation.Horizontal;
        this.isDragging$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
        this.gestureEndAction = new SliderState$gestureEndAction$1(this);
        ClosedFloatRange closedFloatRange = (ClosedFloatRange) this.valueRange;
        float f3 = closedFloatRange._start;
        float f4 = closedFloatRange._endInclusive - f3;
        this.rawOffset$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(MathHelpersKt.lerp(0.0f, 0.0f, RangesKt___RangesKt.coerceIn(f4 == 0.0f ? 0.0f : (f - f3) / f4, 0.0f, 1.0f)));
        this.pressOffset$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(0.0f);
        this.dragScope = new DragScope() { // from class: androidx.compose.material3.SliderState$dragScope$1
            @Override // androidx.compose.foundation.gestures.DragScope
            public final void dragBy(float f5) {
                this.this$0.dispatchRawDelta(f5);
            }
        };
        this.scrollMutex = new MutatorMutex();
    }
}
