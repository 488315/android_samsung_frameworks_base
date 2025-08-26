package com.android.systemui.communal.widgets;

import com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes2.dex */
public final class GlanceableHubWidgetManager$createIConfigureWidgetCallback$1$1 extends IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.Stub {
    public final /* synthetic */ WidgetConfigurator $configurator;
    public final /* synthetic */ GlanceableHubWidgetManager this$0;

    public GlanceableHubWidgetManager$createIConfigureWidgetCallback$1$1(GlanceableHubWidgetManager glanceableHubWidgetManager, WidgetConfigurator widgetConfigurator) {
        this.this$0 = glanceableHubWidgetManager;
        this.$configurator = widgetConfigurator;
    }

    @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService.IConfigureWidgetCallback
    public final void onConfigureWidget(int i, IGlanceableHubWidgetManagerService.IConfigureWidgetCallback.IResultReceiver iResultReceiver) {
        GlanceableHubWidgetManager glanceableHubWidgetManager = this.this$0;
        BuildersKt.launch$default(glanceableHubWidgetManager.bgScope, null, null, new GlanceableHubWidgetManager$createIConfigureWidgetCallback$1$1$onConfigureWidget$1(this.$configurator, i, iResultReceiver, glanceableHubWidgetManager, null), 3);
    }
}
