package com.android.systemui.communal.data.repository;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelper;
import com.android.systemui.communal.widgets.GlanceableHubWidgetManager;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import java.util.Map;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalWidgetRepositoryRemoteImpl implements CommunalWidgetRepository {
    public final CoroutineScope bgScope;
    public final Flow communalWidgets;
    public final GlanceableHubWidgetManager glanceableHubWidgetManager;

    public CommunalWidgetRepositoryRemoteImpl(CoroutineScope coroutineScope, GlanceableHubWidgetManager glanceableHubWidgetManager, GlanceableHubMultiUserHelper glanceableHubMultiUserHelper) {
        this.bgScope = coroutineScope;
        this.glanceableHubWidgetManager = glanceableHubWidgetManager;
        glanceableHubMultiUserHelper.getClass();
        this.communalWidgets = glanceableHubWidgetManager.widgets;
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void abortRestoreWidgets() {
        throw new IllegalStateException("Restore widgets should be performed on a foreground user");
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void addWidget(ComponentName componentName, UserHandle userHandle, Integer num, WidgetConfigurator widgetConfigurator) {
        BuildersKt.launch$default(this.bgScope, null, null, new CommunalWidgetRepositoryRemoteImpl$addWidget$1(this, componentName, userHandle, num, widgetConfigurator, null), 3);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void deleteWidget(int i) {
        BuildersKt.launch$default(this.bgScope, null, null, new CommunalWidgetRepositoryRemoteImpl$deleteWidget$1(this, i, null), 3);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final Flow getCommunalWidgets() {
        return this.communalWidgets;
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void resizeWidget(Map map, int i, int i2) {
        BuildersKt.launch$default(this.bgScope, null, null, new CommunalWidgetRepositoryRemoteImpl$resizeWidget$1(this, i, i2, map, null), 3);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void restoreWidgets(Map map) {
        throw new IllegalStateException("Restore widgets should be performed on a foreground user");
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void updateWidgetOrder(Map map) {
        BuildersKt.launch$default(this.bgScope, null, null, new CommunalWidgetRepositoryRemoteImpl$updateWidgetOrder$1(this, map, null), 3);
    }
}
