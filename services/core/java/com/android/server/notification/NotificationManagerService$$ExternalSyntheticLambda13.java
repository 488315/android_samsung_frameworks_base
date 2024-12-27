package com.android.server.notification;

import com.android.server.notification.NotificationManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationManagerService$$ExternalSyntheticLambda13 implements NotificationManagerService.FlagChecker {
    @Override // com.android.server.notification.NotificationManagerService.FlagChecker
    public final boolean apply(int i) {
        return (i & 64) == 0 && (32768 & i) == 0;
    }
}
