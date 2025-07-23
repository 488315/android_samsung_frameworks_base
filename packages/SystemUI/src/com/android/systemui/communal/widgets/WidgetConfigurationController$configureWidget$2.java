package com.android.systemui.communal.widgets;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class WidgetConfigurationController$configureWidget$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $appWidgetId;
    int label;
    final /* synthetic */ WidgetConfigurationController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WidgetConfigurationController$configureWidget$2(WidgetConfigurationController widgetConfigurationController, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = widgetConfigurationController;
        this.$appWidgetId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WidgetConfigurationController$configureWidget$2(this.this$0, this.$appWidgetId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WidgetConfigurationController$configureWidget$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x007f, code lost:
    
        if (((java.lang.Boolean) r11).booleanValue() == true) goto L23;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L16
            if (r1 != r2) goto Le
            kotlin.ResultKt.throwOnFailure(r11)
            goto L79
        Le:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L16:
            kotlin.ResultKt.throwOnFailure(r11)
            com.android.systemui.communal.widgets.WidgetConfigurationController r11 = r10.this$0
            kotlin.reflect.KProperty[] r1 = com.android.systemui.communal.widgets.WidgetConfigurationController.$$delegatedProperties
            kotlinx.coroutines.CompletableDeferred r11 = r11.getResult()
            if (r11 != 0) goto L94
            com.android.systemui.communal.widgets.WidgetConfigurationController r11 = r10.this$0
            kotlinx.coroutines.CompletableDeferredImpl r1 = kotlinx.coroutines.CompletableDeferredKt.CompletableDeferred$default()
            kotlin.properties.ReadWriteProperty r4 = r11.result$delegate
            kotlin.reflect.KProperty[] r5 = com.android.systemui.communal.widgets.WidgetConfigurationController.$$delegatedProperties
            r5 = r5[r3]
            r4.setValue(r11, r5, r1)
            com.android.systemui.communal.widgets.WidgetConfigurationController r11 = r10.this$0     // Catch: java.lang.Exception -> L5f
            com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelper r11 = r11.glanceableHubMultiUserHelper     // Catch: java.lang.Exception -> L5f
            r11.getClass()     // Catch: java.lang.Exception -> L5f
            com.android.systemui.communal.widgets.WidgetConfigurationController r11 = r10.this$0     // Catch: java.lang.Exception -> L5f
            dagger.Lazy r11 = r11.appWidgetHostLazy     // Catch: java.lang.Exception -> L5f
            java.lang.Object r11 = r11.get()     // Catch: java.lang.Exception -> L5f
            com.android.systemui.communal.widgets.WidgetConfigurationController r1 = r10.this$0     // Catch: java.lang.Exception -> L5f
            int r6 = r10.$appWidgetId     // Catch: java.lang.Exception -> L5f
            r4 = r11
            com.android.systemui.communal.widgets.CommunalAppWidgetHost r4 = (com.android.systemui.communal.widgets.CommunalAppWidgetHost) r4     // Catch: java.lang.Exception -> L5f
            androidx.activity.ComponentActivity r5 = r1.activity     // Catch: java.lang.Exception -> L5f
            android.app.ActivityOptions r11 = android.app.ActivityOptions.makeBasic()     // Catch: java.lang.Exception -> L5f
            r11.setPendingIntentBackgroundActivityStartMode(r2)     // Catch: java.lang.Exception -> L5f
            r11.setSplashScreenStyle(r3)     // Catch: java.lang.Exception -> L5f
            android.os.Bundle r9 = r11.toBundle()     // Catch: java.lang.Exception -> L5f
            r7 = 0
            r8 = 100
            r4.startAppWidgetConfigureActivityForResult(r5, r6, r7, r8, r9)     // Catch: java.lang.Exception -> L5f
            goto L64
        L5f:
            com.android.systemui.communal.widgets.WidgetConfigurationController r11 = r10.this$0
            r11.setConfigurationResult(r3)
        L64:
            com.android.systemui.communal.widgets.WidgetConfigurationController r11 = r10.this$0
            kotlinx.coroutines.CompletableDeferred r11 = r11.getResult()
            if (r11 == 0) goto L82
            r10.label = r2
            kotlinx.coroutines.CompletableDeferredImpl r11 = (kotlinx.coroutines.CompletableDeferredImpl) r11
            java.lang.Object r11 = r11.awaitInternal(r10)
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            if (r11 != r0) goto L79
            return r0
        L79:
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 != r2) goto L82
            goto L83
        L82:
            r2 = r3
        L83:
            com.android.systemui.communal.widgets.WidgetConfigurationController r10 = r10.this$0
            kotlin.properties.ReadWriteProperty r11 = r10.result$delegate
            kotlin.reflect.KProperty[] r0 = com.android.systemui.communal.widgets.WidgetConfigurationController.$$delegatedProperties
            r0 = r0[r3]
            r1 = 0
            r11.setValue(r10, r0, r1)
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r2)
            return r10
        L94:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "There is already a pending configuration"
            r10.<init>(r11)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.widgets.WidgetConfigurationController$configureWidget$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
