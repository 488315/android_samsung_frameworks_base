package android.appwidget;

import android.appwidget.AppWidgetManager;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ AppWidgetManager.ServiceCollectionCache.ConnectionTask f$0;

    public /* synthetic */ AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda2(AppWidgetManager.ServiceCollectionCache.ConnectionTask connectionTask) {
        this.f$0 = connectionTask;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.handleNext();
    }
}
