package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.UserHandle;
import android.widget.RemoteViews;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelper;
import com.android.systemui.communal.widgets.CommunalAppWidgetHost;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import java.util.LinkedHashMap;
import java.util.Optional;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class CommunalWidgetHost implements CommunalAppWidgetHost.Observer {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl _appWidgetProviders;
    public final CommunalAppWidgetHost appWidgetHost;
    public final Optional appWidgetManager;
    public final ReadonlyStateFlow appWidgetProviders;
    public final CoroutineScope bgScope;
    public final Logger logger;
    public final SelectedUserInteractor selectedUserInteractor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.communal.widgets.CommunalWidgetHost$refreshProviders$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalWidgetHost.this.new AnonymousClass1(continuation);
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
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int[] appWidgetIds = CommunalWidgetHost.this.appWidgetHost.getAppWidgetIds();
            CommunalWidgetHost communalWidgetHost = CommunalWidgetHost.this;
            for (int i : appWidgetIds) {
                communalWidgetHost.getClass();
                communalWidgetHost.appWidgetHost.setListener(i, new CommunalAppWidgetHostListener(i, new CommunalWidgetHost$addListener$1(communalWidgetHost)));
                Integer num = new Integer(i);
                AppWidgetProviderInfo appWidgetInfo = null;
                AppWidgetManager appWidgetManager = (AppWidgetManager) communalWidgetHost.appWidgetManager.orElse(null);
                if (appWidgetManager != null) {
                    appWidgetInfo = appWidgetManager.getAppWidgetInfo(i);
                }
                linkedHashMap.put(num, appWidgetInfo);
            }
            CommunalWidgetHost.this._appWidgetProviders.setValue(MapsKt__MapsKt.toMap(linkedHashMap));
            return Unit.INSTANCE;
        }
    }

    public CommunalWidgetHost(CoroutineScope coroutineScope, Optional<AppWidgetManager> optional, CommunalAppWidgetHost communalAppWidgetHost, SelectedUserInteractor selectedUserInteractor, LogBuffer logBuffer, GlanceableHubMultiUserHelper glanceableHubMultiUserHelper) {
        this.bgScope = coroutineScope;
        this.appWidgetManager = optional;
        this.appWidgetHost = communalAppWidgetHost;
        this.selectedUserInteractor = selectedUserInteractor;
        glanceableHubMultiUserHelper.getClass();
        this.logger = new Logger(logBuffer, "CommunalWidgetHost");
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(MapsKt__MapsKt.emptyMap());
        this._appWidgetProviders = stateFlowImplMutableStateFlow;
        this.appWidgetProviders = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }

    public final Integer allocateIdAndBindWidget(ComponentName componentName, UserHandle userHandle) {
        boolean zBindAppWidgetIdIfAllowed;
        CommunalAppWidgetHost communalAppWidgetHost = this.appWidgetHost;
        int iAllocateAppWidgetId = communalAppWidgetHost.allocateAppWidgetId();
        if (userHandle == null) {
            userHandle = new UserHandle(this.selectedUserInteractor.getSelectedUserId());
        }
        if (this.appWidgetManager.isPresent()) {
            Bundle bundle = new Bundle();
            bundle.putInt("appWidgetCategory", 2);
            zBindAppWidgetIdIfAllowed = ((AppWidgetManager) this.appWidgetManager.get()).bindAppWidgetIdIfAllowed(iAllocateAppWidgetId, userHandle, componentName, bundle);
        } else {
            zBindAppWidgetIdIfAllowed = false;
        }
        Logger logger = this.logger;
        if (!zBindAppWidgetIdIfAllowed) {
            communalAppWidgetHost.deleteAppWidgetId(iAllocateAppWidgetId);
            Logger.d$default(logger, "Failed to bind the widget " + componentName, null, 2, null);
            return null;
        }
        Logger.d$default(logger, "Successfully bound the widget " + componentName, null, 2, null);
        AppWidgetManager appWidgetManager = (AppWidgetManager) this.appWidgetManager.orElse(null);
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new CommunalWidgetHost$onProviderInfoUpdated$1(this, iAllocateAppWidgetId, appWidgetManager != null ? appWidgetManager.getAppWidgetInfo(iAllocateAppWidgetId) : null, null), 7);
        return Integer.valueOf(iAllocateAppWidgetId);
    }

    public final void refreshProviders() {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new AnonymousClass1(null), 7);
    }

    public final class CommunalAppWidgetHostListener implements AppWidgetHost.AppWidgetHostListener {
        public final int appWidgetId;
        public final Function2 onUpdateProviderInfo;

        public CommunalAppWidgetHostListener(int i, Function2 function2) {
            this.appWidgetId = i;
            this.onUpdateProviderInfo = function2;
        }

        public final void onUpdateProviderInfo(AppWidgetProviderInfo appWidgetProviderInfo) {
            this.onUpdateProviderInfo.invoke(Integer.valueOf(this.appWidgetId), appWidgetProviderInfo);
        }

        public final void onViewDataChanged(int i) {
        }

        public final void updateAppWidget(RemoteViews remoteViews) {
        }
    }
}
