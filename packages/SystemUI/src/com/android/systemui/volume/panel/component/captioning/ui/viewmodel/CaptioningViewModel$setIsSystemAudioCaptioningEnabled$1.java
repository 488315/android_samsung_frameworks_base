package com.android.systemui.volume.panel.component.captioning.ui.viewmodel;

import com.android.settingslib.view.accessibility.data.repository.CaptioningRepositoryImpl;
import com.android.settingslib.view.accessibility.domain.interactor.CaptioningInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class CaptioningViewModel$setIsSystemAudioCaptioningEnabled$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $enabled;
    int label;
    final /* synthetic */ CaptioningViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CaptioningViewModel$setIsSystemAudioCaptioningEnabled$1(CaptioningViewModel captioningViewModel, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = captioningViewModel;
        this.$enabled = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CaptioningViewModel$setIsSystemAudioCaptioningEnabled$1(this.this$0, this.$enabled, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CaptioningViewModel$setIsSystemAudioCaptioningEnabled$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CaptioningInteractor captioningInteractor = this.this$0.captioningInteractor;
            boolean z = this.$enabled;
            this.label = 1;
            Object isSystemAudioCaptioningEnabled = ((CaptioningRepositoryImpl) captioningInteractor.repository).setIsSystemAudioCaptioningEnabled(z, this);
            if (isSystemAudioCaptioningEnabled != coroutineSingletons) {
                isSystemAudioCaptioningEnabled = Unit.INSTANCE;
            }
            if (isSystemAudioCaptioningEnabled == coroutineSingletons) {
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
