package com.android.systemui.screenshot;

import android.content.Intent;
import android.os.Bundle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.screenshot.ActionIntentExecutor$launchIntent$2", m277f = "ActionIntentExecutor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class ActionIntentExecutor$launchIntent$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Bundle $bundle;
    final /* synthetic */ Intent $intent;
    int label;
    final /* synthetic */ ActionIntentExecutor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActionIntentExecutor$launchIntent$2(ActionIntentExecutor actionIntentExecutor, Intent intent, Bundle bundle, Continuation<? super ActionIntentExecutor$launchIntent$2> continuation) {
        super(2, continuation);
        this.this$0 = actionIntentExecutor;
        this.$intent = intent;
        this.$bundle = bundle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ActionIntentExecutor$launchIntent$2(this.this$0, this.$intent, this.$bundle, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActionIntentExecutor$launchIntent$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.context.startActivity(this.$intent, this.$bundle);
        return Unit.INSTANCE;
    }
}
