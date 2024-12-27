package com.android.systemui.searcle;

import android.app.contextualsearch.ContextualSearchManager;
import android.content.Context;
import android.util.Log;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class SearcleManager$invokeSearcle$3 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SearcleManager this$0;

    public SearcleManager$invokeSearcle$3(SearcleManager searcleManager, Continuation continuation) {
        super(2, continuation);
        this.this$0 = searcleManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SearcleManager$invokeSearcle$3(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SearcleManager$invokeSearcle$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SearcleManager searcleManager = this.this$0;
        Context context = searcleManager.context;
        int access$getOmniEntryPoint = SearcleManager.access$getOmniEntryPoint(searcleManager);
        Context context2 = OmniAPI.mContext;
        Log.d("OmniAPI", "invokeOmni omniEntryPoint = " + access$getOmniEntryPoint);
        ContextualSearchManager contextualSearchManager = (ContextualSearchManager) context.getSystemService(ContextualSearchManager.class);
        if (contextualSearchManager == null) {
            Log.i("OmniAPI", "CSS system service is null");
            Log.d("SearcleManager", "invokeSearcle invokeOmni return false");
        } else {
            contextualSearchManager.startContextualSearch(access$getOmniEntryPoint);
            Log.i("OmniAPI", "invokeOmni");
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_INVOKE_SEARCLE, this.this$0.invokedPackageName);
        }
        this.this$0.invokedPackageName = "";
        return Unit.INSTANCE;
    }
}
