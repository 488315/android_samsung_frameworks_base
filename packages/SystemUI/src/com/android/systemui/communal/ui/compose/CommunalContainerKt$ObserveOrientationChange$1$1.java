package com.android.systemui.communal.ui.compose;

import android.content.res.Configuration;
import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class CommunalContainerKt$ObserveOrientationChange$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Configuration $configuration;
    final /* synthetic */ CommunalViewModel $viewModel;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalContainerKt$ObserveOrientationChange$1$1(CommunalViewModel communalViewModel, Configuration configuration, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = communalViewModel;
        this.$configuration = configuration;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalContainerKt$ObserveOrientationChange$1$1(this.$viewModel, this.$configuration, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalContainerKt$ObserveOrientationChange$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((CommunalSceneRepositoryImpl) this.$viewModel.communalSceneInteractor.repository)._communalContainerOrientation.updateState(null, Integer.valueOf(this.$configuration.orientation));
        return Unit.INSTANCE;
    }
}
