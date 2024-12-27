package com.android.server.notification;


public final /* synthetic */ class NotificationManagerService$$ExternalSyntheticLambda13
        implements NotificationManagerService.FlagChecker {
    @Override // com.android.server.notification.NotificationManagerService.FlagChecker
    public final boolean apply(int i) {
        return (i & 64) == 0 && (32768 & i) == 0;
    }
}
