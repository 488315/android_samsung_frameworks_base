package com.android.systemui.communal.widgets;

import com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1 implements WidgetConfigurator {
    public final /* synthetic */ IGlanceableHubWidgetManagerService.IConfigureWidgetCallback $callback;
    public final /* synthetic */ GlanceableHubWidgetManagerService this$0;

    public GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1(IGlanceableHubWidgetManagerService.IConfigureWidgetCallback iConfigureWidgetCallback, GlanceableHubWidgetManagerService glanceableHubWidgetManagerService) {
        this.$callback = iConfigureWidgetCallback;
        this.this$0 = glanceableHubWidgetManagerService;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:0|1|(2:3|(7:5|6|7|(1:(2:10|11)(2:17|18))(3:19|20|(1:22))|12|13|14))|25|6|7|(0)(0)|12|13|14) */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x002b, code lost:
    
        r6 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0058, code lost:
    
        r5 = r5.this$0.logger;
        r7 = new com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0(1);
        r7 = r5.getBuffer().obtain(r5.getTag(), com.android.systemui.log.core.LogLevel.ERROR, r7, null);
        r7.setStr1(r6.getLocalizedMessage());
        r5.getBuffer().commit(r7);
        r5 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
    @Override // com.android.systemui.communal.widgets.WidgetConfigurator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object configureWidget(int r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1 r0 = (com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1 r0 = new com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$configureWidget$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r5 = r0.L$0
            com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1 r5 = (com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1) r5
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: android.os.RemoteException -> L2b
            goto L51
        L2b:
            r6 = move-exception
            goto L58
        L2d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L35:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.CompletableDeferredImpl r7 = kotlinx.coroutines.CompletableDeferredKt.CompletableDeferred$default()     // Catch: android.os.RemoteException -> L2b
            com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$resultReceiver$1 r2 = new com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1$resultReceiver$1     // Catch: android.os.RemoteException -> L2b
            r2.<init>(r7)     // Catch: android.os.RemoteException -> L2b
            com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService$IConfigureWidgetCallback r4 = r5.$callback     // Catch: android.os.RemoteException -> L2b
            r4.onConfigureWidget(r6, r2)     // Catch: android.os.RemoteException -> L2b
            r0.L$0 = r5     // Catch: android.os.RemoteException -> L2b
            r0.label = r3     // Catch: android.os.RemoteException -> L2b
            java.lang.Object r7 = r7.awaitInternal(r0)     // Catch: android.os.RemoteException -> L2b
            if (r7 != r1) goto L51
            return r1
        L51:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch: android.os.RemoteException -> L2b
            boolean r5 = r7.booleanValue()     // Catch: android.os.RemoteException -> L2b
            goto L80
        L58:
            com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService r5 = r5.this$0
            com.android.systemui.log.core.Logger r5 = r5.logger
            com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0 r7 = new com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0
            r0 = 1
            r7.<init>(r0)
            com.android.systemui.log.core.LogLevel r0 = com.android.systemui.log.core.LogLevel.ERROR
            com.android.systemui.log.core.MessageBuffer r1 = r5.getBuffer()
            java.lang.String r2 = r5.getTag()
            r3 = 0
            com.android.systemui.log.core.LogMessage r7 = r1.obtain(r2, r0, r7, r3)
            java.lang.String r6 = r6.getLocalizedMessage()
            r7.setStr1(r6)
            com.android.systemui.log.core.MessageBuffer r5 = r5.getBuffer()
            r5.commit(r7)
            r5 = 0
        L80:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.widgets.GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1.configureWidget(int, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
