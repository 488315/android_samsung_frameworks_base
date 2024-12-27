package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.foundation.gestures.DragGestureDetectorKt;
import androidx.compose.material3.SliderState;
import androidx.compose.runtime.MutableState;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class SeekbarDefaults$SliderContainer$1$2$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState $delta$delegate;
    final /* synthetic */ MutableState $dragState;
    final /* synthetic */ Function1 $onValueChange;
    final /* synthetic */ SliderState $state;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SeekbarDefaults$SliderContainer$1$2$2(SliderState sliderState, MutableState mutableState, Function1 function1, MutableState mutableState2, Continuation continuation) {
        super(2, continuation);
        this.$state = sliderState;
        this.$dragState = mutableState;
        this.$onValueChange = function1;
        this.$delta$delegate = mutableState2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SeekbarDefaults$SliderContainer$1$2$2 seekbarDefaults$SliderContainer$1$2$2 = new SeekbarDefaults$SliderContainer$1$2$2(this.$state, this.$dragState, this.$onValueChange, this.$delta$delegate, continuation);
        seekbarDefaults$SliderContainer$1$2$2.L$0 = obj;
        return seekbarDefaults$SliderContainer$1$2$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SeekbarDefaults$SliderContainer$1$2$2) create((PointerInputScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PointerInputScope pointerInputScope = (PointerInputScope) this.L$0;
            final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
            ref$FloatRef.element = this.$state.getValue();
            final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
            ref$FloatRef2.element = ref$FloatRef.element;
            final Ref$FloatRef ref$FloatRef3 = new Ref$FloatRef();
            final SliderState sliderState = this.$state;
            final MutableState mutableState = this.$dragState;
            Function1 function1 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$SliderContainer$1$2$2.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    long j = ((Offset) obj2).packedValue;
                    Ref$FloatRef.this.element = Offset.m325getXimpl(j);
                    ref$FloatRef.element = sliderState.getValue();
                    mutableState.setValue(Boolean.TRUE);
                    return Unit.INSTANCE;
                }
            };
            final SliderState sliderState2 = this.$state;
            final MutableState mutableState2 = this.$dragState;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$SliderContainer$1$2$2.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Function0 function02 = SliderState.this.onValueChangeFinished;
                    if (function02 != null) {
                        function02.invoke();
                    }
                    mutableState2.setValue(Boolean.FALSE);
                    return Unit.INSTANCE;
                }
            };
            final SliderState sliderState3 = this.$state;
            final MutableState mutableState3 = this.$dragState;
            Function0 function02 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$SliderContainer$1$2$2.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Function0 function03 = SliderState.this.onValueChangeFinished;
                    if (function03 != null) {
                        function03.invoke();
                    }
                    mutableState3.setValue(Boolean.FALSE);
                    return Unit.INSTANCE;
                }
            };
            final SliderState sliderState4 = this.$state;
            final Function1 function12 = this.$onValueChange;
            final MutableState mutableState4 = this.$delta$delegate;
            Function2 function2 = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$SliderContainer$1$2$2.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    PointerInputChange pointerInputChange = (PointerInputChange) obj2;
                    ((Number) obj3).floatValue();
                    pointerInputChange.consume();
                    float f = Ref$FloatRef.this.element;
                    float m325getXimpl = Offset.m325getXimpl(pointerInputChange.position) - ref$FloatRef3.element;
                    MutableState mutableState5 = mutableState4;
                    SeekbarDefaults seekbarDefaults = SeekbarDefaults.INSTANCE;
                    float floatValue = ((Number) RangesKt___RangesKt.coerceIn(Float.valueOf((m325getXimpl / ((Number) mutableState5.getValue()).floatValue()) + f), sliderState4.valueRange)).floatValue();
                    Ref$FloatRef ref$FloatRef4 = ref$FloatRef2;
                    if (ref$FloatRef4.element != floatValue) {
                        ref$FloatRef4.element = floatValue;
                        function12.invoke(Float.valueOf(floatValue));
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (DragGestureDetectorKt.detectHorizontalDragGestures(pointerInputScope, function1, function0, function02, function2, this) == coroutineSingletons) {
                return coroutineSingletons;
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
