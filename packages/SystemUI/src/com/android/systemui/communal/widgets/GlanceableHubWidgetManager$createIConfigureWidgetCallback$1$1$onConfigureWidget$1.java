package com.android.systemui.communal.widgets;

import android.os.RemoteException;
import com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class GlanceableHubWidgetManager$createIConfigureWidgetCallback$1$1$onConfigureWidget$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $appWidgetId;
    final /* synthetic */ WidgetConfigurator $configurator;
    final /* synthetic */ IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver $resultReceiver;
    int label;
    final /* synthetic */ GlanceableHubWidgetManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GlanceableHubWidgetManager$createIConfigureWidgetCallback$1$1$onConfigureWidget$1(WidgetConfigurator widgetConfigurator, int i, IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver iResultReceiver, GlanceableHubWidgetManager glanceableHubWidgetManager, Continuation continuation) {
        super(2, continuation);
        this.$configurator = widgetConfigurator;
        this.$appWidgetId = i;
        this.$resultReceiver = iResultReceiver;
        this.this$0 = glanceableHubWidgetManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GlanceableHubWidgetManager$createIConfigureWidgetCallback$1$1$onConfigureWidget$1(this.$configurator, this.$appWidgetId, this.$resultReceiver, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GlanceableHubWidgetManager$createIConfigureWidgetCallback$1$1$onConfigureWidget$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            WidgetConfigurator widgetConfigurator = this.$configurator;
            int i2 = this.$appWidgetId;
            this.label = 1;
            obj = widgetConfigurator.configureWidget(i2, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        boolean booleanValue = ((Boolean) obj).booleanValue();
        try {
            IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver iResultReceiver = this.$resultReceiver;
            if (iResultReceiver != null) {
                iResultReceiver.onResult(booleanValue);
            }
        } catch (RemoteException e) {
            Logger logger = this.this$0.logger;
            GlanceableHubWidgetManager$createIConfigureWidgetCallback$1$1$onConfigureWidget$1$$ExternalSyntheticLambda0 glanceableHubWidgetManager$createIConfigureWidgetCallback$1$1$onConfigureWidget$1$$ExternalSyntheticLambda0 = new GlanceableHubWidgetManager$createIConfigureWidgetCallback$1$1$onConfigureWidget$1$$ExternalSyntheticLambda0();
            LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, glanceableHubWidgetManager$createIConfigureWidgetCallback$1$1$onConfigureWidget$1$$ExternalSyntheticLambda0, null);
            obtain.setStr1(e.getLocalizedMessage());
            logger.getBuffer().commit(obtain);
        }
        return Unit.INSTANCE;
    }
}
