package android.appwidget;

import android.os.Handler;
import java.util.concurrent.Executor;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1 implements Executor {
    public final /* synthetic */ Handler f$0;

    public /* synthetic */ AppWidgetManager$ServiceCollectionCache$ConnectionTask$$ExternalSyntheticLambda1(Handler handler) {
        this.f$0 = handler;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        this.f$0.post(runnable);
    }
}
