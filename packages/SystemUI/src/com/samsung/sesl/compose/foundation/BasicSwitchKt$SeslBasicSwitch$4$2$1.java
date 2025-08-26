package com.samsung.sesl.compose.foundation;

import androidx.compose.foundation.gestures.DragGestureDetectorKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.State;
import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes4.dex */
final class BasicSwitchKt$SeslBasicSwitch$4$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<Function1> $animateFraction$delegate;
    final /* synthetic */ boolean $checked;
    final /* synthetic */ CoroutineScope $coroutineScope;
    final /* synthetic */ boolean $enabled;
    final /* synthetic */ State<Float> $fraction$delegate;
    final /* synthetic */ MutableState<Boolean> $needUpdateFraction$delegate;
    final /* synthetic */ State<Function1> $onCheckedChangeByUser$delegate;
    final /* synthetic */ MutableFloatState $rawPosition$delegate;
    final /* synthetic */ MutableIntState $width$delegate;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BasicSwitchKt$SeslBasicSwitch$4$2$1(boolean z, State<Float> state, MutableIntState mutableIntState, MutableFloatState mutableFloatState, CoroutineScope coroutineScope, State<? extends Function1> state2, State<? extends Function1> state3, MutableState<Boolean> mutableState, boolean z2, Continuation continuation) {
        super(2, continuation);
        this.$enabled = z;
        this.$fraction$delegate = state;
        this.$width$delegate = mutableIntState;
        this.$rawPosition$delegate = mutableFloatState;
        this.$coroutineScope = coroutineScope;
        this.$onCheckedChangeByUser$delegate = state2;
        this.$animateFraction$delegate = state3;
        this.$needUpdateFraction$delegate = mutableState;
        this.$checked = z2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BasicSwitchKt$SeslBasicSwitch$4$2$1 basicSwitchKt$SeslBasicSwitch$4$2$1 = new BasicSwitchKt$SeslBasicSwitch$4$2$1(this.$enabled, this.$fraction$delegate, this.$width$delegate, this.$rawPosition$delegate, this.$coroutineScope, this.$onCheckedChangeByUser$delegate, this.$animateFraction$delegate, this.$needUpdateFraction$delegate, this.$checked, continuation);
        basicSwitchKt$SeslBasicSwitch$4$2$1.L$0 = obj;
        return basicSwitchKt$SeslBasicSwitch$4$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BasicSwitchKt$SeslBasicSwitch$4$2$1) create((PointerInputScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.samsung.sesl.compose.foundation.BasicSwitchKt$SeslBasicSwitch$4$2$1$$ExternalSyntheticLambda1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PointerInputScope pointerInputScope = (PointerInputScope) this.L$0;
            if (this.$enabled) {
                State<Float> state = this.$fraction$delegate;
                final MutableIntState mutableIntState = this.$width$delegate;
                final MutableFloatState mutableFloatState = this.$rawPosition$delegate;
                BasicSwitchKt$$ExternalSyntheticLambda1 basicSwitchKt$$ExternalSyntheticLambda1 = new BasicSwitchKt$$ExternalSyntheticLambda1(state, mutableIntState, mutableFloatState, 1);
                final CoroutineScope coroutineScope = this.$coroutineScope;
                final State<Function1> state2 = this.$onCheckedChangeByUser$delegate;
                final State<Function1> state3 = this.$animateFraction$delegate;
                final MutableState<Boolean> mutableState = this.$needUpdateFraction$delegate;
                final boolean z = this.$checked;
                ?? r4 = new Function0() { // from class: com.samsung.sesl.compose.foundation.BasicSwitchKt$SeslBasicSwitch$4$2$1$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        float floatValue = ((SnapshotMutableFloatStateImpl) mutableFloatState).getFloatValue();
                        SnapshotMutableIntStateImpl snapshotMutableIntStateImpl = (SnapshotMutableIntStateImpl) mutableIntState;
                        float intValue = snapshotMutableIntStateImpl.getIntValue();
                        if (floatValue > intValue) {
                            floatValue = intValue;
                        }
                        float f = floatValue < ((float) (snapshotMutableIntStateImpl.getIntValue() / 2)) ? 0.0f : 1.0f;
                        ((Function1) state2.getValue()).mo781invoke(Boolean.valueOf(f > 0.5f));
                        State state4 = state3;
                        ((Function1) state4.getValue()).mo781invoke(Float.valueOf(f));
                        Boolean bool = Boolean.TRUE;
                        MutableState mutableState2 = mutableState;
                        mutableState2.setValue(bool);
                        BuildersKt.launch$default(coroutineScope, null, null, new BasicSwitchKt$SeslBasicSwitch$4$2$1$2$1(z, mutableState2, state4, null), 3);
                        return Unit.INSTANCE;
                    }
                };
                Function2 function2 = new Function2() { // from class: com.samsung.sesl.compose.foundation.BasicSwitchKt$SeslBasicSwitch$4$2$1$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        float fFloatValue = ((Float) obj3).floatValue();
                        SnapshotMutableFloatStateImpl snapshotMutableFloatStateImpl = (SnapshotMutableFloatStateImpl) mutableFloatState;
                        snapshotMutableFloatStateImpl.setFloatValue(snapshotMutableFloatStateImpl.getFloatValue() + fFloatValue);
                        if (((SnapshotMutableIntStateImpl) mutableIntState).getIntValue() > 0) {
                            ((Function1) state3.getValue()).mo781invoke(Float.valueOf(RangesKt___RangesKt.coerceIn(snapshotMutableFloatStateImpl.getFloatValue(), 0.0f, r3.getIntValue()) / r3.getIntValue()));
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (DragGestureDetectorKt.detectHorizontalDragGestures$default(pointerInputScope, basicSwitchKt$$ExternalSyntheticLambda1, r4, function2, this, 4) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
