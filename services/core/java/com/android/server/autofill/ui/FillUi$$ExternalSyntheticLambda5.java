package com.android.server.autofill.ui;

import android.app.PendingIntent;
import android.util.Slog;
import android.view.View;
import android.widget.RemoteViews;

import com.android.server.autofill.Helper;

public final /* synthetic */ class FillUi$$ExternalSyntheticLambda5
        implements RemoteViews.InteractionHandler {
    public final boolean onInteraction(
            View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
        if (!Helper.sVerbose) {
            return true;
        }
        Slog.v("FillUi", "Ignoring click on " + view);
        return true;
    }
}
