package com.android.systemui.notifications.ui.viewmodel;

import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
final class NotificationsShadeOverlayContentViewModel$showMedia$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaCarouselInteractor $mediaCarouselInteractor;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationsShadeOverlayContentViewModel$showMedia$2(MediaCarouselInteractor mediaCarouselInteractor, Continuation continuation) {
        super(2, continuation);
        this.$mediaCarouselInteractor = mediaCarouselInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationsShadeOverlayContentViewModel$showMedia$2 notificationsShadeOverlayContentViewModel$showMedia$2 = new NotificationsShadeOverlayContentViewModel$showMedia$2(this.$mediaCarouselInteractor, continuation);
        notificationsShadeOverlayContentViewModel$showMedia$2.L$0 = obj;
        return notificationsShadeOverlayContentViewModel$showMedia$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationsShadeOverlayContentViewModel$showMedia$2) create((DisableFlagsModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return ((DisableFlagsModel) this.L$0).isQuickSettingsEnabled() ? this.$mediaCarouselInteractor.hasActiveMediaOrRecommendation : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
    }
}
