package com.android.systemui.communal.widgets;

import android.app.ActivityOptions;
import android.content.IntentSender;
import android.os.OutcomeReceiver;
import androidx.activity.ComponentActivity;
import com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelper;
import com.android.systemui.util.ReferenceExtKt;
import dagger.Lazy;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class WidgetConfigurationController implements WidgetConfigurator, OutcomeReceiver {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final ComponentActivity activity;
    public final Lazy appWidgetHostLazy;
    public final CoroutineDispatcher bgDispatcher;
    public final GlanceableHubMultiUserHelper glanceableHubMultiUserHelper;
    public final Executor mainExecutor;
    public final ReadWriteProperty result$delegate = ReferenceExtKt.nullableAtomicReference$default(null, 1, null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        WidgetConfigurationController create(ComponentActivity componentActivity);
    }

    /* renamed from: com.android.systemui.communal.widgets.WidgetConfigurationController$configureWidget$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $appWidgetId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i, Continuation continuation) {
            super(2, continuation);
            this.$appWidgetId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return WidgetConfigurationController.this.new AnonymousClass2(this.$appWidgetId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                WidgetConfigurationController widgetConfigurationController = WidgetConfigurationController.this;
                KProperty[] kPropertyArr = WidgetConfigurationController.$$delegatedProperties;
                if (widgetConfigurationController.getResult() != null) {
                    throw new IllegalStateException("There is already a pending configuration");
                }
                WidgetConfigurationController widgetConfigurationController2 = WidgetConfigurationController.this;
                widgetConfigurationController2.result$delegate.setValue(widgetConfigurationController2, WidgetConfigurationController.$$delegatedProperties[0], CompletableDeferredKt.CompletableDeferred$default());
                try {
                    WidgetConfigurationController.this.glanceableHubMultiUserHelper.getClass();
                    Object obj2 = WidgetConfigurationController.this.appWidgetHostLazy.get();
                    WidgetConfigurationController widgetConfigurationController3 = WidgetConfigurationController.this;
                    int i2 = this.$appWidgetId;
                    CommunalAppWidgetHost communalAppWidgetHost = (CommunalAppWidgetHost) obj2;
                    ComponentActivity componentActivity = widgetConfigurationController3.activity;
                    ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                    activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
                    activityOptionsMakeBasic.setSplashScreenStyle(0);
                    communalAppWidgetHost.startAppWidgetConfigureActivityForResult(componentActivity, i2, 0, 100, activityOptionsMakeBasic.toBundle());
                } catch (Exception unused) {
                    WidgetConfigurationController.this.setConfigurationResult(0);
                }
                CompletableDeferred result = WidgetConfigurationController.this.getResult();
                if (result != null) {
                    this.label = 1;
                    obj = ((CompletableDeferredImpl) result).awaitInternal(this);
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                WidgetConfigurationController widgetConfigurationController4 = WidgetConfigurationController.this;
                widgetConfigurationController4.result$delegate.setValue(widgetConfigurationController4, WidgetConfigurationController.$$delegatedProperties[0], null);
                return Boolean.valueOf(z);
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            boolean z = ((Boolean) obj).booleanValue();
            WidgetConfigurationController widgetConfigurationController42 = WidgetConfigurationController.this;
            widgetConfigurationController42.result$delegate.setValue(widgetConfigurationController42, WidgetConfigurationController.$$delegatedProperties[0], null);
            return Boolean.valueOf(z);
        }
    }

    static {
        MutablePropertyReference1Impl mutablePropertyReference1Impl = new MutablePropertyReference1Impl(WidgetConfigurationController.class, "result", "getResult()Lkotlinx/coroutines/CompletableDeferred;", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{mutablePropertyReference1Impl};
        new Companion(null);
    }

    public WidgetConfigurationController(ComponentActivity componentActivity, Lazy lazy, CoroutineDispatcher coroutineDispatcher, GlanceableHubMultiUserHelper glanceableHubMultiUserHelper, Lazy lazy2, Executor executor) {
        this.activity = componentActivity;
        this.appWidgetHostLazy = lazy;
        this.bgDispatcher = coroutineDispatcher;
        this.glanceableHubMultiUserHelper = glanceableHubMultiUserHelper;
        this.mainExecutor = executor;
    }

    @Override // com.android.systemui.communal.widgets.WidgetConfigurator
    public final Object configureWidget(int i, Continuation continuation) {
        return BuildersKt.withContext(this.bgDispatcher, new AnonymousClass2(i, null), continuation);
    }

    public final CompletableDeferred getResult() {
        return (CompletableDeferred) this.result$delegate.getValue(this, $$delegatedProperties[0]);
    }

    @Override // android.os.OutcomeReceiver
    public final void onError(Throwable th) {
        setConfigurationResult(0);
    }

    @Override // android.os.OutcomeReceiver
    public final void onResult(Object obj) throws IntentSender.SendIntentException {
        IntentSender intentSender = (IntentSender) obj;
        if (intentSender == null) {
            setConfigurationResult(0);
            return;
        }
        ComponentActivity componentActivity = this.activity;
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
        activityOptionsMakeBasic.setSplashScreenStyle(0);
        componentActivity.startIntentSenderForResult(intentSender, 100, null, 0, 0, 0, activityOptionsMakeBasic.toBundle());
    }

    public final void setConfigurationResult(int i) {
        CompletableDeferred result = getResult();
        if (result != null) {
            ((CompletableDeferredImpl) result).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Boolean.valueOf(i == -1));
        }
    }
}
