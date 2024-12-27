package com.android.systemui.communal.widgets;

import android.app.PendingIntent;
import android.view.View;
import android.widget.RemoteViews;
import com.android.systemui.communal.util.InteractionHandlerDelegate;
import com.android.systemui.plugins.ActivityStarter;
import kotlin.jvm.functions.Function1;

public final class WidgetInteractionHandler implements RemoteViews.InteractionHandler {
    public final ActivityStarter activityStarter;
    public final InteractionHandlerDelegate delegate = new InteractionHandlerDelegate(new Function1() { // from class: com.android.systemui.communal.widgets.WidgetInteractionHandler$delegate$1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return Boolean.valueOf(((View) obj) instanceof CommunalAppWidgetHostView);
        }
    }, new WidgetInteractionHandler$delegate$2(this));

    public WidgetInteractionHandler(ActivityStarter activityStarter) {
        this.activityStarter = activityStarter;
    }

    public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
        return this.delegate.onInteraction(view, pendingIntent, remoteResponse);
    }
}
