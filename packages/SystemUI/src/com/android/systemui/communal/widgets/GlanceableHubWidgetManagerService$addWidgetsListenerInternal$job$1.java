package com.android.systemui.communal.widgets;

import android.os.RemoteException;
import com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class GlanceableHubWidgetManagerService$addWidgetsListenerInternal$job$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener $listener;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ GlanceableHubWidgetManagerService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GlanceableHubWidgetManagerService$addWidgetsListenerInternal$job$1(IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener, GlanceableHubWidgetManagerService glanceableHubWidgetManagerService, Continuation continuation) {
        super(2, continuation);
        this.$listener = iGlanceableHubWidgetsListener;
        this.this$0 = glanceableHubWidgetManagerService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        GlanceableHubWidgetManagerService$addWidgetsListenerInternal$job$1 glanceableHubWidgetManagerService$addWidgetsListenerInternal$job$1 = new GlanceableHubWidgetManagerService$addWidgetsListenerInternal$job$1(this.$listener, this.this$0, continuation);
        glanceableHubWidgetManagerService$addWidgetsListenerInternal$job$1.L$0 = obj;
        return glanceableHubWidgetManagerService$addWidgetsListenerInternal$job$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GlanceableHubWidgetManagerService$addWidgetsListenerInternal$job$1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            this.$listener.onWidgetsUpdated((List) this.L$0);
        } catch (RemoteException e) {
            Logger logger = this.this$0.logger;
            GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0 glanceableHubWidgetManagerService$$ExternalSyntheticLambda0 = new GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0(2);
            LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, glanceableHubWidgetManagerService$$ExternalSyntheticLambda0, null);
            obtain.setStr1(e.getLocalizedMessage());
            logger.getBuffer().commit(obtain);
        }
        return Unit.INSTANCE;
    }
}
