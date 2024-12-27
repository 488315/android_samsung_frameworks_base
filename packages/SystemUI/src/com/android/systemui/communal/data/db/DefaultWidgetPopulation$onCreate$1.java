package com.android.systemui.communal.data.db;

import com.android.systemui.log.core.Logger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class DefaultWidgetPopulation$onCreate$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DefaultWidgetPopulation this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DefaultWidgetPopulation$onCreate$1(DefaultWidgetPopulation defaultWidgetPopulation, Continuation continuation) {
        super(2, continuation);
        this.this$0 = defaultWidgetPopulation;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DefaultWidgetPopulation$onCreate$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DefaultWidgetPopulation$onCreate$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DefaultWidgetPopulation defaultWidgetPopulation = this.this$0;
            this.label = 1;
            int i2 = DefaultWidgetPopulation.$r8$clinit;
            defaultWidgetPopulation.getClass();
            Object withContext = BuildersKt.withContext(defaultWidgetPopulation.bgDispatcher, new DefaultWidgetPopulation$addDefaultWidgets$2(defaultWidgetPopulation, null), this);
            if (withContext != obj2) {
                withContext = Unit.INSTANCE;
            }
            if (withContext == obj2) {
                return obj2;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        Logger.i$default(this.this$0.logger, "Default widgets were populated in the database.", null, 2, null);
        return Unit.INSTANCE;
    }
}
