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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class MediaCardKt$AudioPathSection$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState $groupDeviceExpanded$delegate;
    final /* synthetic */ State $isGroupSpeakerDefaultExpanded$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCardKt$AudioPathSection$2$1(State state, MutableState mutableState, Continuation continuation) {
        super(2, continuation);
        this.$isGroupSpeakerDefaultExpanded$delegate = state;
        this.$groupDeviceExpanded$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCardKt$AudioPathSection$2$1(this.$isGroupSpeakerDefaultExpanded$delegate, this.$groupDeviceExpanded$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCardKt$AudioPathSection$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MutableState mutableState = this.$groupDeviceExpanded$delegate;
        Boolean bool = (Boolean) this.$isGroupSpeakerDefaultExpanded$delegate.getValue();
        bool.booleanValue();
        mutableState.setValue(bool);
        return Unit.INSTANCE;
    }
}
