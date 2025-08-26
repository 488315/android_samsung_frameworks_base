package com.android.systemui.communal.widgets;

import android.os.RemoteException;
import com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;

/* loaded from: classes2.dex */
public final class GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1 implements WidgetConfigurator {
    public final /* synthetic */ IGlanceableHubWidgetManagerService.IConfigureWidgetCallback $callback;
    public final /* synthetic */ GlanceableHubWidgetManagerService this$0;

    public GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1(IGlanceableHubWidgetManagerService.IConfigureWidgetCallback iConfigureWidgetCallback, GlanceableHubWidgetManagerService glanceableHubWidgetManagerService) {
        this.$callback = iConfigureWidgetCallback;
        this.this$0 = glanceableHubWidgetManagerService;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.communal.widgets.WidgetConfigurator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object configureWidget(int i, Continuation continuation) {
        GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1 glanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1;
        boolean zBooleanValue;
        if (continuation instanceof GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1) {
            glanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1 = (GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1) continuation;
            int i2 = glanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                glanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1.label = i2 - Integer.MIN_VALUE;
            } else {
                glanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1 = new GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1(this, continuation);
            }
        }
        Object objAwaitInternal = glanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = glanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1.label;
        try {
            if (i3 == 0) {
                ResultKt.throwOnFailure(objAwaitInternal);
                CompletableDeferredImpl completableDeferredImplCompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
                this.$callback.onConfigureWidget(i, new GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$resultReceiver$1(completableDeferredImplCompletableDeferred$default));
                glanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1.L$0 = this;
                glanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1.label = 1;
                objAwaitInternal = completableDeferredImplCompletableDeferred$default.awaitInternal(glanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1);
                if (objAwaitInternal == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i3 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objAwaitInternal);
            }
            zBooleanValue = ((Boolean) objAwaitInternal).booleanValue();
        } catch (RemoteException e) {
            Logger logger = this.this$0.logger;
            GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0 glanceableHubWidgetManagerService$$ExternalSyntheticLambda0 = new GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0(1);
            LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, glanceableHubWidgetManagerService$$ExternalSyntheticLambda0, null);
            logMessageObtain.setStr1(e.getLocalizedMessage());
            logger.getBuffer().commit(logMessageObtain);
            zBooleanValue = false;
        }
        return Boolean.valueOf(zBooleanValue);
    }
}
