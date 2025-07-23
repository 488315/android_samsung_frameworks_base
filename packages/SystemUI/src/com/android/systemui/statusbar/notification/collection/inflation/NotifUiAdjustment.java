package com.android.systemui.statusbar.notification.collection.inflation;

import android.app.Notification;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotifUiAdjustment {
    public static final Companion Companion = new Companion(null);
    public final boolean isChildInGroup;
    public final boolean isConversation;
    public final boolean isGroupSummary;
    public final boolean isMinimized;
    public final boolean isPromoted;
    public final boolean isSnoozeEnabled;
    public final int redactionType;
    public final List smartActions;
    public final List smartReplies;
    public final String summarization;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public NotifUiAdjustment(String str, List<? extends Notification.Action> list, List<? extends CharSequence> list2, boolean z, boolean z2, boolean z3, int i, boolean z4, boolean z5, String str2, boolean z6) {
        this.smartActions = list;
        this.smartReplies = list2;
        this.isConversation = z;
        this.isSnoozeEnabled = z2;
        this.isMinimized = z3;
        this.redactionType = i;
        this.isChildInGroup = z4;
        this.isGroupSummary = z5;
        this.summarization = str2;
        this.isPromoted = z6;
    }
}
