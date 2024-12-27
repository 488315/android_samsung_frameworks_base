package com.android.server.autofill.ui;

import android.app.PendingIntent;
import android.view.View;
import android.widget.RemoteViews;

import com.android.server.autofill.Session;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DialogFillUi$$ExternalSyntheticLambda5
        implements RemoteViews.InteractionHandler {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DialogFillUi f$0;

    public /* synthetic */ DialogFillUi$$ExternalSyntheticLambda5(
            DialogFillUi dialogFillUi, int i) {
        this.$r8$classId = i;
        this.f$0 = dialogFillUi;
    }

    public final boolean onInteraction(
            View view, PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
        int i = this.$r8$classId;
        DialogFillUi dialogFillUi = this.f$0;
        switch (i) {
            case 0:
                if (pendingIntent == null) {
                    dialogFillUi.getClass();
                    break;
                } else {
                    AutoFillUI.AnonymousClass3 anonymousClass3 = dialogFillUi.mCallback;
                    ((Session) AutoFillUI.this.mCallback)
                            .startIntentSender(pendingIntent.getIntentSender(), null);
                    break;
                }
            case 1:
                if (pendingIntent == null) {
                    dialogFillUi.getClass();
                    break;
                } else {
                    AutoFillUI.AnonymousClass3 anonymousClass32 = dialogFillUi.mCallback;
                    ((Session) AutoFillUI.this.mCallback)
                            .startIntentSender(pendingIntent.getIntentSender(), null);
                    break;
                }
            default:
                if (pendingIntent == null) {
                    dialogFillUi.getClass();
                    break;
                } else {
                    AutoFillUI.AnonymousClass3 anonymousClass33 = dialogFillUi.mCallback;
                    ((Session) AutoFillUI.this.mCallback)
                            .startIntentSender(pendingIntent.getIntentSender(), null);
                    break;
                }
        }
        return true;
    }
}
