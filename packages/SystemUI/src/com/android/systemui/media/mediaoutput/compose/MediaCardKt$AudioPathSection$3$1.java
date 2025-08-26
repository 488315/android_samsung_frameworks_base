package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class MediaCardKt$AudioPathSection$3$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState<Boolean> $groupDeviceExpanded$delegate;
    final /* synthetic */ State<Boolean> $isGroupSpeakerDefaultExpanded$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCardKt$AudioPathSection$3$1(State<Boolean> state, MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$isGroupSpeakerDefaultExpanded$delegate = state;
        this.$groupDeviceExpanded$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCardKt$AudioPathSection$3$1(this.$isGroupSpeakerDefaultExpanded$delegate, this.$groupDeviceExpanded$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCardKt$AudioPathSection$3$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MutableState<Boolean> mutableState = this.$groupDeviceExpanded$delegate;
        Boolean bool = (Boolean) this.$isGroupSpeakerDefaultExpanded$delegate.getValue();
        bool.booleanValue();
        mutableState.setValue(bool);
        return Unit.INSTANCE;
    }
}
