package com.android.systemui.statusbar;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.view.View;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationRemoteInputManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ NotificationRemoteInputManager f$0;
    public final /* synthetic */ View f$1;
    public final /* synthetic */ RemoteInput[] f$2;
    public final /* synthetic */ RemoteInput f$3;
    public final /* synthetic */ PendingIntent f$4;
    public final /* synthetic */ NotificationEntry.EditedSuggestionInfo f$5;

    public /* synthetic */ NotificationRemoteInputManager$$ExternalSyntheticLambda0(NotificationRemoteInputManager notificationRemoteInputManager, View view, RemoteInput[] remoteInputArr, RemoteInput remoteInput, PendingIntent pendingIntent, NotificationEntry.EditedSuggestionInfo editedSuggestionInfo) {
        this.f$0 = notificationRemoteInputManager;
        this.f$1 = view;
        this.f$2 = remoteInputArr;
        this.f$3 = remoteInput;
        this.f$4 = pendingIntent;
        this.f$5 = editedSuggestionInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NotificationRemoteInputManager notificationRemoteInputManager = this.f$0;
        View view = this.f$1;
        RemoteInput[] remoteInputArr = this.f$2;
        RemoteInput remoteInput = this.f$3;
        PendingIntent pendingIntent = this.f$4;
        NotificationEntry.EditedSuggestionInfo editedSuggestionInfo = this.f$5;
        boolean z = NotificationRemoteInputManager.ENABLE_REMOTE_INPUT;
        notificationRemoteInputManager.activateRemoteInputOnExpanded(view, remoteInputArr, remoteInput, pendingIntent, editedSuggestionInfo);
    }
}
