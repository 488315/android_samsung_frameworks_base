package androidx.compose.material3;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
final class SliderKt$sliderTapModifier$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SliderState $state;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: androidx.compose.material3.SliderKt$sliderTapModifier$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ SliderState $state;
        /* synthetic */ long J$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SliderState sliderState, Continuation continuation) {
            super(3, continuation);
            this.$state = sliderState;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            long j = ((Offset) obj2).packedValue;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$state, (Continuation) obj3);
            anonymousClass1.J$0 = j;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            float fM400getXimpl;
            float intValue;
            float fM400getXimpl2;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            long j = this.J$0;
            SliderState sliderState = this.$state;
            if (sliderState.orientation == Orientation.Vertical) {
                if (sliderState.reverseVerticalDirection) {
                    intValue = ((SnapshotMutableIntStateImpl) sliderState.totalHeight$delegate).getIntValue();
                    fM400getXimpl2 = Offset.m401getYimpl(j);
                    fM400getXimpl = intValue - fM400getXimpl2;
                } else {
                    fM400getXimpl = Offset.m401getYimpl(j);
                }
            } else if (sliderState.isRtl) {
                intValue = ((SnapshotMutableIntStateImpl) sliderState.totalWidth$delegate).getIntValue();
                fM400getXimpl2 = Offset.m400getXimpl(j);
                fM400getXimpl = intValue - fM400getXimpl2;
            } else {
                fM400getXimpl = Offset.m400getXimpl(j);
            }
            ((SnapshotMutableFloatStateImpl) sliderState.pressOffset$delegate).setFloatValue(fM400getXimpl - ((SnapshotMutableFloatStateImpl) sliderState.rawOffset$delegate).getFloatValue());
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SliderKt$sliderTapModifier$1(SliderState sliderState, Continuation continuation) {
        super(2, continuation);
        this.$state = sliderState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SliderKt$sliderTapModifier$1 sliderKt$sliderTapModifier$1 = new SliderKt$sliderTapModifier$1(this.$state, continuation);
        sliderKt$sliderTapModifier$1.L$0 = obj;
        return sliderKt$sliderTapModifier$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SliderKt$sliderTapModifier$1) create((PointerInputScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PointerInputScope pointerInputScope = (PointerInputScope) this.L$0;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$state, null);
            final SliderState sliderState = this.$state;
            Function1 function1 = new Function1() { // from class: androidx.compose.material3.SliderKt$sliderTapModifier$1.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    long j = ((Offset) obj2).packedValue;
                    sliderState.dispatchRawDelta(0.0f);
                    ((SliderState$gestureEndAction$1) sliderState.gestureEndAction).invoke();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, null, anonymousClass1, function1, this, 3) == coroutineSingletons) {
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
