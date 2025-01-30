package com.samsung.android.desktopsystemui.sharedlib.statusbar;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class RemoteViewsInteractionWrapper {
    private static final String TAG = "[DSU]InteractionHandlerWrapper";
    private static final RemoteViewsInteractionWrapper instance = new RemoteViewsInteractionWrapper();

    public static RemoteViewsInteractionWrapper getInstance() {
        return instance;
    }

    public View applyInteractionHandler(RemoteViews remoteViews, final Context context, ViewGroup viewGroup) {
        RemoteViews.InteractionHandler interactionHandler = new RemoteViews.InteractionHandler() { // from class: com.samsung.android.desktopsystemui.sharedlib.statusbar.RemoteViewsInteractionWrapper.1
            public boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
                Pair launchOptions = remoteResponse.getLaunchOptions(view);
                int displayId = context.getDisplay().getDisplayId();
                ((Intent) launchOptions.first).putExtra("ACTION_CLICK_DISPLAYID", displayId);
                ((ActivityOptions) launchOptions.second).setLaunchDisplayId(displayId);
                Log.d(RemoteViewsInteractionWrapper.TAG, "Set ACTION_CLICK_DISPLAYID = " + displayId);
                return RemoteViews.startPendingIntent(view, pendingIntent, launchOptions);
            }
        };
        if (remoteViews != null) {
            return remoteViews.apply(context, viewGroup, interactionHandler);
        }
        return null;
    }
}
