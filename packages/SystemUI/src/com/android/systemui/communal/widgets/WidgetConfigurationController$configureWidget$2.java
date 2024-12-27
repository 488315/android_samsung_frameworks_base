package com.android.systemui.communal.widgets;

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineScope;

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

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            WidgetConfigurationController widgetConfigurationController = this.this$0;
            KProperty[] kPropertyArr = WidgetConfigurationController.$$delegatedProperties;
            if (widgetConfigurationController.getResult() != null) {
                throw new IllegalStateException("There is already a pending configuration");
            }
            WidgetConfigurationController widgetConfigurationController2 = this.this$0;
            CompletableDeferredImpl CompletableDeferred$default = CompletableDeferredKt.CompletableDeferred$default();
            widgetConfigurationController2.getClass();
            widgetConfigurationController2.result$delegate.setValue(widgetConfigurationController2, WidgetConfigurationController.$$delegatedProperties[0], CompletableDeferred$default);
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityStartMode(1);
            makeBasic.setSplashScreenStyle(0);
            try {
                WidgetConfigurationController widgetConfigurationController3 = this.this$0;
                widgetConfigurationController3.appWidgetHost.startAppWidgetConfigureActivityForResult(widgetConfigurationController3.activity, this.$appWidgetId, 0, 100, makeBasic.toBundle());
            } catch (ActivityNotFoundException unused) {
                CompletableDeferred result = this.this$0.getResult();
                if (result != null) {
                    ((CompletableDeferredImpl) result).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(false);
                }
            }
            CompletableDeferred result2 = this.this$0.getResult();
            if (result2 == null) {
                z = false;
                WidgetConfigurationController widgetConfigurationController4 = this.this$0;
                KProperty[] kPropertyArr2 = WidgetConfigurationController.$$delegatedProperties;
                widgetConfigurationController4.getClass();
                widgetConfigurationController4.result$delegate.setValue(widgetConfigurationController4, WidgetConfigurationController.$$delegatedProperties[0], null);
                return Boolean.valueOf(z);
            }
            this.label = 1;
            obj = ((CompletableDeferredImpl) result2).awaitInternal(this);
            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        z = ((Boolean) obj).booleanValue();
        WidgetConfigurationController widgetConfigurationController42 = this.this$0;
        KProperty[] kPropertyArr22 = WidgetConfigurationController.$$delegatedProperties;
        widgetConfigurationController42.getClass();
        widgetConfigurationController42.result$delegate.setValue(widgetConfigurationController42, WidgetConfigurationController.$$delegatedProperties[0], null);
        return Boolean.valueOf(z);
    }
}
