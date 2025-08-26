package com.samsung.sesl.compose.foundation;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import androidx.compose.ui.hapticfeedback.HapticFeedback;
import com.samsung.sesl.compose.ui.hapticfeedback.SeslHapticFeedbackType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes4.dex */
final class BasicSwitchKt$SeslBasicSwitch$5$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<Function1> $animateFraction$delegate;
    final /* synthetic */ boolean $checked;
    final /* synthetic */ HapticFeedback $hapticFeedback;
    final /* synthetic */ MutableState<Boolean> $needUpdateFraction$delegate;
    final /* synthetic */ MutableState<Boolean> $prevChecked$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BasicSwitchKt$SeslBasicSwitch$5$1(HapticFeedback hapticFeedback, boolean z, MutableState<Boolean> mutableState, MutableState<Boolean> mutableState2, State<? extends Function1> state, Continuation continuation) {
        super(2, continuation);
        this.$hapticFeedback = hapticFeedback;
        this.$checked = z;
        this.$prevChecked$delegate = mutableState;
        this.$needUpdateFraction$delegate = mutableState2;
        this.$animateFraction$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BasicSwitchKt$SeslBasicSwitch$5$1(this.$hapticFeedback, this.$checked, this.$prevChecked$delegate, this.$needUpdateFraction$delegate, this.$animateFraction$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BasicSwitchKt$SeslBasicSwitch$5$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (((Boolean) this.$prevChecked$delegate.getValue()) != null) {
            HapticFeedback hapticFeedback = this.$hapticFeedback;
            SeslHapticFeedbackType.INSTANCE.getClass();
            hapticFeedback.mo572performHapticFeedbackCdsT49E(SeslHapticFeedbackType.EffectSwitch);
        }
        this.$prevChecked$delegate.setValue(Boolean.valueOf(this.$checked));
        this.$needUpdateFraction$delegate.setValue(Boolean.FALSE);
        ((Function1) this.$animateFraction$delegate.getValue()).mo781invoke(new Float(this.$checked ? 1.0f : 0.0f));
        return Unit.INSTANCE;
    }
}
