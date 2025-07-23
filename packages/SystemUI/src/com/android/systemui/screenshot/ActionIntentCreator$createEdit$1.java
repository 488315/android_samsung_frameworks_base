package com.android.systemui.screenshot;

import android.content.Intent;
import android.net.Uri;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ActionIntentCreator$createEdit$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Consumer<Intent> $consumer;
    final /* synthetic */ Uri $rawUri;
    Object L$0;
    int label;
    final /* synthetic */ ActionIntentCreator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActionIntentCreator$createEdit$1(Consumer<Intent> consumer, ActionIntentCreator actionIntentCreator, Uri uri, Continuation continuation) {
        super(2, continuation);
        this.$consumer = consumer;
        this.this$0 = actionIntentCreator;
        this.$rawUri = uri;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ActionIntentCreator$createEdit$1(this.$consumer, this.this$0, this.$rawUri, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActionIntentCreator$createEdit$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Consumer consumer;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Consumer<Intent> consumer2 = this.$consumer;
            ActionIntentCreator actionIntentCreator = this.this$0;
            Uri uri = this.$rawUri;
            this.L$0 = consumer2;
            this.label = 1;
            Object createEdit = actionIntentCreator.createEdit(uri, this);
            if (createEdit == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = createEdit;
            consumer = consumer2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            consumer = (Consumer) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        consumer.accept(obj);
        return Unit.INSTANCE;
    }
}
