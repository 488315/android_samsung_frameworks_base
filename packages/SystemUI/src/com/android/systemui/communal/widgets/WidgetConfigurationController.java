package com.android.systemui.communal.widgets;

import android.app.ActivityOptions;
import android.content.IntentSender;
import android.os.OutcomeReceiver;
import androidx.activity.ComponentActivity;
import com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelper;
import com.android.systemui.util.ReferenceExtKt;
import dagger.Lazy;
import java.util.concurrent.Executor;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WidgetConfigurationController implements WidgetConfigurator, OutcomeReceiver {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final ComponentActivity activity;
    public final Lazy appWidgetHostLazy;
    public final CoroutineDispatcher bgDispatcher;
    public final GlanceableHubMultiUserHelper glanceableHubMultiUserHelper;
    public final Executor mainExecutor;
    public final ReadWriteProperty result$delegate = ReferenceExtKt.nullableAtomicReference$default(null, 1, null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        WidgetConfigurationController create(ComponentActivity componentActivity);
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
        return BuildersKt.withContext(this.bgDispatcher, new WidgetConfigurationController$configureWidget$2(this, i, null), continuation);
    }

    public final CompletableDeferred getResult() {
        return (CompletableDeferred) this.result$delegate.getValue(this, $$delegatedProperties[0]);
    }

    @Override // android.os.OutcomeReceiver
    public final void onError(Throwable th) {
        setConfigurationResult(0);
    }

    @Override // android.os.OutcomeReceiver
    public final void onResult(Object obj) {
        IntentSender intentSender = (IntentSender) obj;
        if (intentSender == null) {
            setConfigurationResult(0);
            return;
        }
        ComponentActivity componentActivity = this.activity;
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setPendingIntentBackgroundActivityStartMode(1);
        makeBasic.setSplashScreenStyle(0);
        componentActivity.startIntentSenderForResult(intentSender, 100, null, 0, 0, 0, makeBasic.toBundle());
    }

    public final void setConfigurationResult(int i) {
        CompletableDeferred result = getResult();
        if (result != null) {
            ((CompletableDeferredImpl) result).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Boolean.valueOf(i == -1));
        }
    }
}
