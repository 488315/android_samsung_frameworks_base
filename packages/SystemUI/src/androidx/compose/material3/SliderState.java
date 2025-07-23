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
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.util.MathHelpersKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public SliderState() {
        this(0.0f, 0, null, null, 15, null);
    }

    public final void dispatchRawDelta(float f) {
        float max;
        float min;
        if (this.orientation == Orientation.Vertical) {
            float intValue = ((SnapshotMutableIntStateImpl) this.totalHeight$delegate).getIntValue();
            MutableIntState mutableIntState = this.thumbHeight$delegate;
            max = Math.max(intValue - (((SnapshotMutableIntStateImpl) mutableIntState).getIntValue() / 2.0f), 0.0f);
            min = Math.min(((SnapshotMutableIntStateImpl) mutableIntState).getIntValue() / 2.0f, max);
        } else {
            float intValue2 = ((SnapshotMutableIntStateImpl) this.totalWidth$delegate).getIntValue();
            MutableIntState mutableIntState2 = this.thumbWidth$delegate;
            max = Math.max(intValue2 - (((SnapshotMutableIntStateImpl) mutableIntState2).getIntValue() / 2.0f), 0.0f);
            min = Math.min(((SnapshotMutableIntStateImpl) mutableIntState2).getIntValue() / 2.0f, max);
        }
        SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) this.rawOffset$delegate;
        float floatValue = snapshotMutableFloatStateImpl.getFloatValue() + f;
        MutableFloatState mutableFloatState = this.pressOffset$delegate;
        snapshotMutableFloatStateImpl.setFloatValue(((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue() + floatValue);
        ((SnapshotMutableFloatStateImpl) mutableFloatState).setFloatValue(0.0f);
        float access$snapValueToTick = SliderKt.access$snapValueToTick(snapshotMutableFloatStateImpl.getFloatValue(), min, max, this.tickFractions);
        ClosedFloatRange closedFloatRange = (ClosedFloatRange) this.valueRange;
        float f2 = max - min;
        float lerp = MathHelpersKt.lerp(closedFloatRange._start, closedFloatRange._endInclusive, RangesKt___RangesKt.coerceIn(f2 == 0.0f ? 0.0f : (access$snapValueToTick - min) / f2, 0.0f, 1.0f));
        if (lerp == getValue()) {
            return;
        }
        Function1 function1 = this.onValueChange;
        if (function1 != null) {
            function1.mo779invoke(Float.valueOf(lerp));
        } else {
            setValue(lerp);
        }
    }

    @Override // androidx.compose.foundation.gestures.DraggableState
    public final Object drag(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new SliderState$drag$2(this, mutatePriority, function2, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    public final float getCoercedValueAsFraction() {
        ClosedFloatRange closedFloatRange = (ClosedFloatRange) this.valueRange;
        float f = closedFloatRange._start;
        float f2 = closedFloatRange._endInclusive;
        float coerceIn = RangesKt___RangesKt.coerceIn(getValue(), closedFloatRange._start, closedFloatRange._endInclusive);
        float f3 = SliderKt.TrackHeight;
        float f4 = f2 - f;
        return RangesKt___RangesKt.coerceIn(f4 == 0.0f ? 0.0f : (coerceIn - f) / f4, 0.0f, 1.0f);
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
                SliderState.this.dispatchRawDelta(f5);
            }
        };
        this.scrollMutex = new MutatorMutex();
    }
}
