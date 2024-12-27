package com.android.systemui.communal.widgets;

import androidx.activity.ComponentActivity;
import com.android.systemui.util.ReferenceExtKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CoroutineDispatcher;

public final class WidgetConfigurationController implements WidgetConfigurator {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final ComponentActivity activity;
    public final CommunalAppWidgetHost appWidgetHost;
    public final CoroutineDispatcher bgDispatcher;
    public final ReadWriteProperty result$delegate = ReferenceExtKt.nullableAtomicReference$default(null, 1, null);

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public interface Factory {
        WidgetConfigurationController create(ComponentActivity componentActivity);
    }

    static {
        MutablePropertyReference1Impl mutablePropertyReference1Impl = new MutablePropertyReference1Impl(WidgetConfigurationController.class, "result", "getResult()Lkotlinx/coroutines/CompletableDeferred;", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{mutablePropertyReference1Impl};
        new Companion(null);
    }

    public WidgetConfigurationController(ComponentActivity componentActivity, CommunalAppWidgetHost communalAppWidgetHost, CoroutineDispatcher coroutineDispatcher) {
        this.activity = componentActivity;
        this.appWidgetHost = communalAppWidgetHost;
        this.bgDispatcher = coroutineDispatcher;
    }

    public final Object configureWidget(int i, Continuation continuation) {
        return BuildersKt.withContext(this.bgDispatcher, new WidgetConfigurationController$configureWidget$2(this, i, null), continuation);
    }

    public final CompletableDeferred getResult() {
        return (CompletableDeferred) this.result$delegate.getValue(this, $$delegatedProperties[0]);
    }
}
