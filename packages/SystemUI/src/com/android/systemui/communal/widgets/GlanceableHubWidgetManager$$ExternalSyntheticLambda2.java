package com.android.systemui.communal.widgets;

import com.android.server.servicewatcher.ServiceWatcher;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class GlanceableHubWidgetManager$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ GlanceableHubWidgetManager f$1;

    public /* synthetic */ GlanceableHubWidgetManager$$ExternalSyntheticLambda2(GlanceableHubWidgetManager glanceableHubWidgetManager, GlanceableHubWidgetManager$widgets$1$callback$1 glanceableHubWidgetManager$widgets$1$callback$1) {
        this.f$1 = glanceableHubWidgetManager;
        this.f$0 = glanceableHubWidgetManager$widgets$1$callback$1;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        GlanceableHubWidgetManager glanceableHubWidgetManager = this.f$1;
        Object obj = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                int i = GlanceableHubWidgetManager.$r8$clinit;
                GlanceableHubWidgetManagerServiceWatcherFactoryImpl glanceableHubWidgetManagerServiceWatcherFactoryImpl = (GlanceableHubWidgetManagerServiceWatcherFactoryImpl) ((ServiceWatcherFactory) obj);
                return ServiceWatcher.create(glanceableHubWidgetManagerServiceWatcherFactoryImpl.context, glanceableHubWidgetManagerServiceWatcherFactoryImpl.handler, "GlanceableHubWidgetManagerService", glanceableHubWidgetManagerServiceWatcherFactoryImpl.supplier, glanceableHubWidgetManager);
            default:
                GlanceableHubWidgetManager$$ExternalSyntheticLambda1 glanceableHubWidgetManager$$ExternalSyntheticLambda1 = new GlanceableHubWidgetManager$$ExternalSyntheticLambda1((GlanceableHubWidgetManager$widgets$1$callback$1) obj, 2);
                int i2 = GlanceableHubWidgetManager.$r8$clinit;
                glanceableHubWidgetManager.runOnService(glanceableHubWidgetManager$$ExternalSyntheticLambda1);
                return Unit.INSTANCE;
        }
    }

    public /* synthetic */ GlanceableHubWidgetManager$$ExternalSyntheticLambda2(ServiceWatcherFactory serviceWatcherFactory, GlanceableHubWidgetManager glanceableHubWidgetManager) {
        this.f$0 = serviceWatcherFactory;
        this.f$1 = glanceableHubWidgetManager;
    }
}
