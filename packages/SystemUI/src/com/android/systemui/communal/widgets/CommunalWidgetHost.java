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
import java.util.Optional;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public CommunalWidgetHost(CoroutineScope coroutineScope, Optional<AppWidgetManager> optional, CommunalAppWidgetHost communalAppWidgetHost, SelectedUserInteractor selectedUserInteractor, LogBuffer logBuffer, GlanceableHubMultiUserHelper glanceableHubMultiUserHelper) {
        this.bgScope = coroutineScope;
        this.appWidgetManager = optional;
        this.appWidgetHost = communalAppWidgetHost;
        this.selectedUserInteractor = selectedUserInteractor;
        glanceableHubMultiUserHelper.getClass();
        this.logger = new Logger(logBuffer, "CommunalWidgetHost");
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(MapsKt__MapsKt.emptyMap());
        this._appWidgetProviders = MutableStateFlow;
        this.appWidgetProviders = FlowKt.asStateFlow(MutableStateFlow);
    }

    public final Integer allocateIdAndBindWidget(ComponentName componentName, UserHandle userHandle) {
        boolean z;
        CommunalAppWidgetHost communalAppWidgetHost = this.appWidgetHost;
        int allocateAppWidgetId = communalAppWidgetHost.allocateAppWidgetId();
        if (userHandle == null) {
            userHandle = new UserHandle(this.selectedUserInteractor.getSelectedUserId());
        }
        if (this.appWidgetManager.isPresent()) {
            Bundle bundle = new Bundle();
            bundle.putInt("appWidgetCategory", 2);
            z = ((AppWidgetManager) this.appWidgetManager.get()).bindAppWidgetIdIfAllowed(allocateAppWidgetId, userHandle, componentName, bundle);
        } else {
            z = false;
        }
        Logger logger = this.logger;
        if (!z) {
            communalAppWidgetHost.deleteAppWidgetId(allocateAppWidgetId);
            Logger.d$default(logger, "Failed to bind the widget " + componentName, null, 2, null);
            return null;
        }
        Logger.d$default(logger, "Successfully bound the widget " + componentName, null, 2, null);
        AppWidgetManager appWidgetManager = (AppWidgetManager) this.appWidgetManager.orElse(null);
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new CommunalWidgetHost$onProviderInfoUpdated$1(this, allocateAppWidgetId, appWidgetManager != null ? appWidgetManager.getAppWidgetInfo(allocateAppWidgetId) : null, null), 7);
        return Integer.valueOf(allocateAppWidgetId);
    }

    public final void refreshProviders() {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new CommunalWidgetHost$refreshProviders$1(this, null), 7);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
