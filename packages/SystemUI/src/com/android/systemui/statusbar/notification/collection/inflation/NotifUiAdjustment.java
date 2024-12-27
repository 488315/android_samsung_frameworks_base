package com.android.systemui.statusbar.notification.collection.inflation;

import android.app.Notification;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class NotifUiAdjustment {
    public static final Companion Companion = new Companion(null);
    public final boolean isChildInGroup;
    public final boolean isConversation;
    public final boolean isGroupSummary;
    public final boolean isMinimized;
    public final boolean isPromoted;
    public final boolean isSnoozeEnabled;
    public final boolean needsRedaction;
    public final List smartActions;
    public final List smartReplies;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public NotifUiAdjustment(String str, List<? extends Notification.Action> list, List<? extends CharSequence> list2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
        this.smartActions = list;
        this.smartReplies = list2;
        this.isConversation = z;
        this.isSnoozeEnabled = z2;
        this.isMinimized = z3;
        this.needsRedaction = z4;
        this.isChildInGroup = z5;
        this.isGroupSummary = z6;
        this.isPromoted = z7;
    }
}
