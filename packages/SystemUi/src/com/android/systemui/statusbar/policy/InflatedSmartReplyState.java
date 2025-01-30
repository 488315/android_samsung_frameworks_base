package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class InflatedSmartReplyState {
    public final boolean hasPhishingAction;
    public final SmartReplyView.SmartActions smartActions;
    public final SmartReplyView.SmartReplies smartReplies;
    public final SuppressedActions suppressedActions;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SuppressedActions {
        public final List suppressedActionIndices;

        public SuppressedActions(List<Integer> list) {
            this.suppressedActionIndices = list;
        }
    }

    public InflatedSmartReplyState(SmartReplyView.SmartReplies smartReplies, SmartReplyView.SmartActions smartActions, SuppressedActions suppressedActions, boolean z) {
        this.smartReplies = smartReplies;
        this.smartActions = smartActions;
        this.suppressedActions = suppressedActions;
        this.hasPhishingAction = z;
    }
}
