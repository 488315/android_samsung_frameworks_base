package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.UserHandle;
import android.widget.RemoteViews;
import com.android.systemui.communal.widgets.CommunalAppWidgetHost;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import java.util.Optional;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

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
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public CommunalWidgetHost(CoroutineScope coroutineScope, Optional<AppWidgetManager> optional, CommunalAppWidgetHost communalAppWidgetHost, SelectedUserInteractor selectedUserInteractor, LogBuffer logBuffer) {
        this.bgScope = coroutineScope;
        this.appWidgetManager = optional;
        this.appWidgetHost = communalAppWidgetHost;
        this.selectedUserInteractor = selectedUserInteractor;
        this.logger = new Logger(logBuffer, "CommunalWidgetHost");
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(MapsKt__MapsKt.emptyMap());
        this._appWidgetProviders = MutableStateFlow;
        this.appWidgetProviders = FlowKt.asStateFlow(MutableStateFlow);
    }

    public final Integer allocateIdAndBindWidget(ComponentName componentName, UserHandle userHandle) {
        CommunalAppWidgetHost communalAppWidgetHost = this.appWidgetHost;
        int allocateAppWidgetId = communalAppWidgetHost.allocateAppWidgetId();
        boolean z = false;
        if (userHandle == null) {
            userHandle = new UserHandle(this.selectedUserInteractor.getSelectedUserId(false));
        }
        if (this.appWidgetManager.isPresent()) {
            Bundle bundle = new Bundle();
            bundle.putInt("appWidgetCategory", 2);
            z = ((AppWidgetManager) this.appWidgetManager.get()).bindAppWidgetIdIfAllowed(allocateAppWidgetId, userHandle, componentName, bundle);
        }
        Logger logger = this.logger;
        if (!z) {
            communalAppWidgetHost.deleteAppWidgetId(allocateAppWidgetId);
            Logger.d$default(logger, "Failed to bind the widget " + componentName, null, 2, null);
            return null;
        }
        Logger.d$default(logger, "Successfully bound the widget " + componentName, null, 2, null);
        AppWidgetManager appWidgetManager = (AppWidgetManager) this.appWidgetManager.orElse(null);
        BuildersKt.launch$default(this.bgScope, null, null, new CommunalWidgetHost$onProviderInfoUpdated$1(this, allocateAppWidgetId, appWidgetManager != null ? appWidgetManager.getAppWidgetInfo(allocateAppWidgetId) : null, null), 3);
        return Integer.valueOf(allocateAppWidgetId);
    }

    public final void refreshProviders() {
        BuildersKt.launch$default(this.bgScope, null, null, new CommunalWidgetHost$refreshProviders$1(this, null), 3);
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
