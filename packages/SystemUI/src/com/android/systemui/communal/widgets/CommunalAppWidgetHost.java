package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.os.Looper;
import android.widget.RemoteViews;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class CommunalAppWidgetHost extends AppWidgetHost {
    public final SharedFlowImpl _appWidgetIdToRemove;
    public final ReadonlySharedFlow appWidgetIdToRemove;
    public final CoroutineScope backgroundScope;
    public final Logger logger;
    public final Set observers;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Observer {
    }

    static {
        new Companion(null);
    }

    public CommunalAppWidgetHost(Context context, CoroutineScope coroutineScope, int i, RemoteViews.InteractionHandler interactionHandler, Looper looper, LogBuffer logBuffer) {
        super(context, i, interactionHandler, looper);
        this.backgroundScope = coroutineScope;
        this.logger = new Logger(logBuffer, "CommunalAppWidgetHost");
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._appWidgetIdToRemove = MutableSharedFlow$default;
        this.appWidgetIdToRemove = FlowKt.asSharedFlow(MutableSharedFlow$default);
        this.observers = new LinkedHashSet();
    }

    @Override // android.appwidget.AppWidgetHost
    public final int allocateAppWidgetId() {
        int allocateAppWidgetId = super.allocateAppWidgetId();
        BuildersKt.launch$default(this.backgroundScope, null, null, new CommunalAppWidgetHost$allocateAppWidgetId$1$1(this, allocateAppWidgetId, null), 3);
        return allocateAppWidgetId;
    }

    @Override // android.appwidget.AppWidgetHost
    public final void deleteAppWidgetId(int i) {
        super.deleteAppWidgetId(i);
        BuildersKt.launch$default(this.backgroundScope, null, null, new CommunalAppWidgetHost$deleteAppWidgetId$1(this, i, null), 3);
    }

    @Override // android.appwidget.AppWidgetHost
    public final void onAppWidgetRemoved(int i) {
        BuildersKt.launch$default(this.backgroundScope, null, null, new CommunalAppWidgetHost$onAppWidgetRemoved$1(this, i, null), 3);
    }

    @Override // android.appwidget.AppWidgetHost
    public final AppWidgetHostView onCreateView(Context context, int i, AppWidgetProviderInfo appWidgetProviderInfo) {
        return new CommunalAppWidgetHostView(context);
    }

    @Override // android.appwidget.AppWidgetHost
    public final void startListening() {
        super.startListening();
        BuildersKt.launch$default(this.backgroundScope, null, null, new CommunalAppWidgetHost$startListening$1(this, null), 3);
    }

    @Override // android.appwidget.AppWidgetHost
    public final void stopListening() {
        super.stopListening();
        BuildersKt.launch$default(this.backgroundScope, null, null, new CommunalAppWidgetHost$stopListening$1(this, null), 3);
    }
}
