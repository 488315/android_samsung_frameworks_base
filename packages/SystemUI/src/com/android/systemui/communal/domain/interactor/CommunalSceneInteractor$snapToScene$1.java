package com.android.systemui.communal.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.data.repository.CommunalSceneRepository;
import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalSceneInteractor$snapToScene$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $delayMillis;
    final /* synthetic */ KeyguardState $keyguardState;
    final /* synthetic */ String $loggingReason;
    final /* synthetic */ SceneKey $newScene;
    int label;
    final /* synthetic */ CommunalSceneInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSceneInteractor$snapToScene$1(CommunalSceneInteractor communalSceneInteractor, SceneKey sceneKey, String str, long j, KeyguardState keyguardState, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalSceneInteractor;
        this.$newScene = sceneKey;
        this.$loggingReason = str;
        this.$delayMillis = j;
        this.$keyguardState = keyguardState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalSceneInteractor$snapToScene$1(this.this$0, this.$newScene, this.$loggingReason, this.$delayMillis, this.$keyguardState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalSceneInteractor$snapToScene$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long j = this.$delayMillis;
            this.label = 1;
            if (DelayKt.delay(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        if (Intrinsics.areEqual(this.this$0.currentScene.$$delegate_0.getValue(), this.$newScene)) {
            return Unit.INSTANCE;
        }
        CommunalSceneInteractor communalSceneInteractor = this.this$0;
        communalSceneInteractor.logger.logSceneChangeRequested((SceneKey) communalSceneInteractor.currentScene.$$delegate_0.getValue(), this.$newScene, this.$loggingReason, true);
        CommunalSceneInteractor communalSceneInteractor2 = this.this$0;
        SceneKey sceneKey = this.$newScene;
        KeyguardState keyguardState = this.$keyguardState;
        Iterator it = communalSceneInteractor2.onSceneAboutToChangeListener.iterator();
        while (it.hasNext()) {
            ((CommunalSceneInteractor.OnSceneAboutToChangeListener) it.next()).onSceneAboutToChange(sceneKey, keyguardState);
        }
        CommunalSceneRepository communalSceneRepository = this.this$0.repository;
        ((CommunalSceneRepositoryImpl) communalSceneRepository).sceneDataSource.snapToScene(this.$newScene);
        return Unit.INSTANCE;
    }
}
