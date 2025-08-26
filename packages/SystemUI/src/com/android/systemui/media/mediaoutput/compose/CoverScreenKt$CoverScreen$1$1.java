package com.android.systemui.media.mediaoutput.compose;

import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.viewmodel.SessionAudioPathViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class CoverScreenKt$CoverScreen$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Feature $feature;
    final /* synthetic */ SessionAudioPathViewModel $sessionAudioPathViewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CoverScreenKt$CoverScreen$1$1(SessionAudioPathViewModel sessionAudioPathViewModel, Feature feature, Continuation continuation) {
        super(2, continuation);
        this.$sessionAudioPathViewModel = sessionAudioPathViewModel;
        this.$feature = feature;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CoverScreenKt$CoverScreen$1$1(this.$sessionAudioPathViewModel, this.$feature, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CoverScreenKt$CoverScreen$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$sessionAudioPathViewModel.setPackageName(this.$feature.packageName);
        return Unit.INSTANCE;
    }
}
