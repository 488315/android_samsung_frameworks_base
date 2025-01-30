package com.android.systemui.statusbar.notification.collection.inflation;

import android.app.Notification;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifUiAdjustment {
    public static final Companion Companion = new Companion(null);
    public final boolean isConversation;
    public final boolean isMinimized;
    public final boolean isSnoozeEnabled;
    public final boolean needsRedaction;
    public final List smartActions;
    public final List smartReplies;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public NotifUiAdjustment(String str, List<? extends Notification.Action> list, List<? extends CharSequence> list2, boolean z, boolean z2, boolean z3, boolean z4) {
        this.smartActions = list;
        this.smartReplies = list2;
        this.isConversation = z;
        this.isSnoozeEnabled = z2;
        this.isMinimized = z3;
        this.needsRedaction = z4;
    }
}
