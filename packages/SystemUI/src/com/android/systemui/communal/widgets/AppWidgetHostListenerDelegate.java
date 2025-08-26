package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetProviderInfo;
import android.widget.RemoteViews;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class AppWidgetHostListenerDelegate implements AppWidgetHost.AppWidgetHostListener {
    public final AppWidgetHost.AppWidgetHostListener listener;
    public final CoroutineScope mainScope;
    public final String tag;

    public interface Factory {
        AppWidgetHostListenerDelegate create(String str, AppWidgetHost.AppWidgetHostListener appWidgetHostListener);
    }

    /* renamed from: com.android.systemui.communal.widgets.AppWidgetHostListenerDelegate$onUpdateProviderInfo$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ AppWidgetProviderInfo $appWidget;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(AppWidgetProviderInfo appWidgetProviderInfo, Continuation continuation) {
            super(2, continuation);
            this.$appWidget = appWidgetProviderInfo;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AppWidgetHostListenerDelegate.this.new AnonymousClass1(this.$appWidget, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AppWidgetHostListenerDelegate.this.listener.onUpdateProviderInfo(this.$appWidget);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.widgets.AppWidgetHostListenerDelegate$onViewDataChanged$1, reason: invalid class name and case insensitive filesystem */
    final class C08451 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $viewId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08451(int i, Continuation continuation) {
            super(2, continuation);
            this.$viewId = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AppWidgetHostListenerDelegate.this.new C08451(this.$viewId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08451) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AppWidgetHostListenerDelegate.this.listener.onViewDataChanged(this.$viewId);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.communal.widgets.AppWidgetHostListenerDelegate$updateAppWidget$1, reason: invalid class name and case insensitive filesystem */
    final class C08461 extends SuspendLambda implements Function2 {
        final /* synthetic */ RemoteViews $views;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08461(RemoteViews remoteViews, Continuation continuation) {
            super(2, continuation);
            this.$views = remoteViews;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AppWidgetHostListenerDelegate.this.new C08461(this.$views, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08461) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            AppWidgetHostListenerDelegate.this.listener.updateAppWidget(this.$views);
            return Unit.INSTANCE;
        }
    }

    public AppWidgetHostListenerDelegate(CoroutineScope coroutineScope, String str, AppWidgetHost.AppWidgetHostListener appWidgetHostListener) {
        this.mainScope = coroutineScope;
        this.tag = str;
        this.listener = appWidgetHostListener;
    }

    public final void onUpdateProviderInfo(AppWidgetProviderInfo appWidgetProviderInfo) {
        CoroutineTracingKt.launchTraced$default(this.mainScope, null, null, new AnonymousClass1(appWidgetProviderInfo, null), 6);
    }

    public final void onViewDataChanged(int i) {
        CoroutineTracingKt.launchTraced$default(this.mainScope, null, null, new C08451(i, null), 6);
    }

    public final void updateAppWidget(RemoteViews remoteViews) {
        CoroutineTracingKt.launchTraced$default(this.mainScope, null, null, new C08461(remoteViews, null), 6);
    }
}
