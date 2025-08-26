package com.samsung.sesl.compose.foundation;

import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.input.pointer.PointerInputScope;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes4.dex */
final class BasicSwitchKt$SeslBasicSwitch$4$3$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $checked;
    final /* synthetic */ boolean $enabled;
    final /* synthetic */ State<Function1> $onCheckedChangeByUser$delegate;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BasicSwitchKt$SeslBasicSwitch$4$3$1(boolean z, boolean z2, State<? extends Function1> state, Continuation continuation) {
        super(2, continuation);
        this.$enabled = z;
        this.$checked = z2;
        this.$onCheckedChangeByUser$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BasicSwitchKt$SeslBasicSwitch$4$3$1 basicSwitchKt$SeslBasicSwitch$4$3$1 = new BasicSwitchKt$SeslBasicSwitch$4$3$1(this.$enabled, this.$checked, this.$onCheckedChangeByUser$delegate, continuation);
        basicSwitchKt$SeslBasicSwitch$4$3$1.L$0 = obj;
        return basicSwitchKt$SeslBasicSwitch$4$3$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BasicSwitchKt$SeslBasicSwitch$4$3$1) create((PointerInputScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PointerInputScope pointerInputScope = (PointerInputScope) this.L$0;
            if (this.$enabled) {
                final boolean z = this.$checked;
                final State<Function1> state = this.$onCheckedChangeByUser$delegate;
                Function1 function1 = new Function1() { // from class: com.samsung.sesl.compose.foundation.BasicSwitchKt$SeslBasicSwitch$4$3$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        ((Function1) state.getValue()).mo781invoke(Boolean.valueOf(!z));
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, null, null, function1, this, 7) == coroutineSingletons) {
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
