package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
final class KeyguardMediaViewModel$isMediaVisible$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaCarouselInteractor $mediaCarouselInteractor;
    /* synthetic */ boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardMediaViewModel$isMediaVisible$2(MediaCarouselInteractor mediaCarouselInteractor, Continuation continuation) {
        super(2, continuation);
        this.$mediaCarouselInteractor = mediaCarouselInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardMediaViewModel$isMediaVisible$2 keyguardMediaViewModel$isMediaVisible$2 = new KeyguardMediaViewModel$isMediaVisible$2(this.$mediaCarouselInteractor, continuation);
        keyguardMediaViewModel$isMediaVisible$2.Z$0 = ((Boolean) obj).booleanValue();
        return keyguardMediaViewModel$isMediaVisible$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((KeyguardMediaViewModel$isMediaVisible$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.Z$0 ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : this.$mediaCarouselInteractor.hasActiveMediaOrRecommendation;
    }
}
