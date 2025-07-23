package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetHost;
import android.content.Context;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelper;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalAppWidgetHost extends AppWidgetHost {
    public final SharedFlowImpl _appWidgetIdToRemove;
    public final ReadonlySharedFlow appWidgetIdToRemove;
    public final CoroutineScope backgroundScope;
    public final Logger logger;
    public final Set observers;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Observer {
    }

    static {
        new Companion(null);
    }

    public CommunalAppWidgetHost(Context context, CoroutineScope coroutineScope, int i, LogBuffer logBuffer, GlanceableHubMultiUserHelper glanceableHubMultiUserHelper) {
        super(context, i);
        this.backgroundScope = coroutineScope;
        glanceableHubMultiUserHelper.getClass();
        this.logger = new Logger(logBuffer, "CommunalAppWidgetHost");
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._appWidgetIdToRemove = MutableSharedFlow$default;
        this.appWidgetIdToRemove = FlowKt.asSharedFlow(MutableSharedFlow$default);
        this.observers = new LinkedHashSet();
    }

    @Override // android.appwidget.AppWidgetHost
    public final int allocateAppWidgetId() {
        int allocateAppWidgetId = super.allocateAppWidgetId();
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new CommunalAppWidgetHost$allocateAppWidgetId$1$1(this, allocateAppWidgetId, null), 7);
        return allocateAppWidgetId;
    }

    @Override // android.appwidget.AppWidgetHost
    public final void deleteAppWidgetId(int i) {
        super.deleteAppWidgetId(i);
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new CommunalAppWidgetHost$deleteAppWidgetId$1(this, i, null), 7);
    }

    @Override // android.appwidget.AppWidgetHost
    public final void onAppWidgetRemoved(int i) {
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new CommunalAppWidgetHost$onAppWidgetRemoved$1(this, i, null), 7);
    }

    @Override // android.appwidget.AppWidgetHost
    public final void startListening() {
        super.startListening();
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new CommunalAppWidgetHost$startListening$1(this, null), 7);
    }

    @Override // android.appwidget.AppWidgetHost
    public final void stopListening() {
        super.stopListening();
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new CommunalAppWidgetHost$stopListening$1(this, null), 7);
    }
}
