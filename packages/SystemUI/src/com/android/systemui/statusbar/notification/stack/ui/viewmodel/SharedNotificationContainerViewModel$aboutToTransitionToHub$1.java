package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.kairos.BuildScopeKt;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class SharedNotificationContainerViewModel$aboutToTransitionToHub$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SharedNotificationContainerViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SharedNotificationContainerViewModel$aboutToTransitionToHub$1(SharedNotificationContainerViewModel sharedNotificationContainerViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sharedNotificationContainerViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SharedNotificationContainerViewModel$aboutToTransitionToHub$1 sharedNotificationContainerViewModel$aboutToTransitionToHub$1 = new SharedNotificationContainerViewModel$aboutToTransitionToHub$1(this.this$0, continuation);
        sharedNotificationContainerViewModel$aboutToTransitionToHub$1.L$0 = obj;
        return sharedNotificationContainerViewModel$aboutToTransitionToHub$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SharedNotificationContainerViewModel$aboutToTransitionToHub$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int i = 1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            CommunalSceneInteractor.OnSceneAboutToChangeListener onSceneAboutToChangeListener = new CommunalSceneInteractor.OnSceneAboutToChangeListener() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel$aboutToTransitionToHub$1$callback$1
                @Override // com.android.systemui.communal.domain.interactor.CommunalSceneInteractor.OnSceneAboutToChangeListener
                public final void onSceneAboutToChange(SceneKey sceneKey, KeyguardState keyguardState) {
                    if (Intrinsics.areEqual(sceneKey, CommunalScenes.Communal)) {
                        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Unit.INSTANCE);
                    }
                }
            };
            CommunalSceneInteractor communalSceneInteractor = this.this$0.communalSceneInteractor;
            communalSceneInteractor.getClass();
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            int i3 = SceneContainerFlag.$r8$clinit;
            communalSceneInteractor.onSceneAboutToChangeListener.add(onSceneAboutToChangeListener);
            SharedNotificationContainerViewModel$$ExternalSyntheticLambda0 sharedNotificationContainerViewModel$$ExternalSyntheticLambda0 = new SharedNotificationContainerViewModel$$ExternalSyntheticLambda0(this.this$0, onSceneAboutToChangeListener, i);
            this.label = 1;
            if (BuildScopeKt.awaitClose(sharedNotificationContainerViewModel$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
