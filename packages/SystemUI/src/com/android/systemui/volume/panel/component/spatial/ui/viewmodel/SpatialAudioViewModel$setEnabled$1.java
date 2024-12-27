package com.android.systemui.volume.panel.component.spatial.ui.viewmodel;

import com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class SpatialAudioViewModel$setEnabled$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SpatialAudioEnabledModel $model;
    int label;
    final /* synthetic */ SpatialAudioViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SpatialAudioViewModel$setEnabled$1(SpatialAudioViewModel spatialAudioViewModel, SpatialAudioEnabledModel spatialAudioEnabledModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = spatialAudioViewModel;
        this.$model = spatialAudioEnabledModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SpatialAudioViewModel$setEnabled$1(this.this$0, this.$model, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SpatialAudioViewModel$setEnabled$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SpatialAudioComponentInteractor spatialAudioComponentInteractor = this.this$0.interactor;
            SpatialAudioEnabledModel spatialAudioEnabledModel = this.$model;
            this.label = 1;
            if (spatialAudioComponentInteractor.setEnabled(spatialAudioEnabledModel, this) == coroutineSingletons) {
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
