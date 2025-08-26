package com.android.systemui.communal.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.communal.shared.log.CommunalSceneLogger;
import com.android.systemui.communal.shared.log.CommunalSceneLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class CommunalSceneInteractor$currentScene$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CommunalSceneInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSceneInteractor$currentScene$1(CommunalSceneInteractor communalSceneInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = communalSceneInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CommunalSceneInteractor$currentScene$1 communalSceneInteractor$currentScene$1 = new CommunalSceneInteractor$currentScene$1(this.this$0, (Continuation) obj3);
        communalSceneInteractor$currentScene$1.L$0 = (SceneKey) obj;
        communalSceneInteractor$currentScene$1.L$1 = (SceneKey) obj2;
        return communalSceneInteractor$currentScene$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SceneKey sceneKey = (SceneKey) this.L$0;
        SceneKey sceneKey2 = (SceneKey) this.L$1;
        CommunalSceneLogger communalSceneLogger = this.this$0.logger;
        communalSceneLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        CommunalSceneLogger$$ExternalSyntheticLambda0 communalSceneLogger$$ExternalSyntheticLambda0 = new CommunalSceneLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = communalSceneLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("CommunalSceneLogger", logLevel, communalSceneLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = sceneKey.toString();
        logMessageImpl.str2 = sceneKey2.toString();
        logBuffer.commit(logMessageObtain);
        return sceneKey2;
    }
}
