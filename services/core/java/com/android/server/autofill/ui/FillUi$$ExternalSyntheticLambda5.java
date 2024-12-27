package com.android.server.autofill.ui;

import android.app.PendingIntent;
import android.util.Slog;
import android.view.View;
import android.widget.RemoteViews;
import com.android.server.autofill.Helper;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FillUi$$ExternalSyntheticLambda5 implements RemoteViews.InteractionHandler {
    public final boolean onInteraction(View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
        if (!Helper.sVerbose) {
            return true;
        }
        Slog.v("FillUi", "Ignoring click on " + view);
        return true;
    }
}
