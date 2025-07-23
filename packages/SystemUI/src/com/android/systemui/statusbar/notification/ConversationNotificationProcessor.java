package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.content.pm.LauncherApps;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ConversationNotificationProcessor {
    public final Context context;
    public final ConversationNotificationManager conversationNotificationManager;
    public final LauncherApps launcherApps;

    public ConversationNotificationProcessor(Context context, LauncherApps launcherApps, ConversationNotificationManager conversationNotificationManager) {
        this.context = context;
        this.launcherApps = launcherApps;
        this.conversationNotificationManager = conversationNotificationManager;
    }
}
