package com.android.systemui.communal.smartspace;

import android.app.PendingIntent;
import android.view.View;
import android.widget.RemoteViews;
import com.android.systemui.communal.util.InteractionHandlerDelegate;
import com.android.systemui.communal.widgets.SmartspaceAppWidgetHostView;
import com.android.systemui.plugins.ActivityStarter;
import kotlin.jvm.functions.Function1;

public final class SmartspaceInteractionHandler implements RemoteViews.InteractionHandler {
    public final ActivityStarter activityStarter;
    public final InteractionHandlerDelegate delegate = new InteractionHandlerDelegate(new Function1() { // from class: com.android.systemui.communal.smartspace.SmartspaceInteractionHandler$delegate$1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return Boolean.valueOf(((View) obj) instanceof SmartspaceAppWidgetHostView);
        }
    }, new SmartspaceInteractionHandler$delegate$2(this));

    public SmartspaceInteractionHandler(ActivityStarter activityStarter) {
        this.activityStarter = activityStarter;
    }

    public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
        return this.delegate.onInteraction(view, pendingIntent, remoteResponse);
    }
}
