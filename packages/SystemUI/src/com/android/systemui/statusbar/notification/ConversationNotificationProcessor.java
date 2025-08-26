package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.content.pm.LauncherApps;

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
