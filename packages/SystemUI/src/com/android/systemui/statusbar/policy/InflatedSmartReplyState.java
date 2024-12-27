package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.List;

public final class InflatedSmartReplyState {
    public final boolean hasPhishingAction;
    public final SmartReplyView.SmartActions smartActions;
    public final SmartReplyView.SmartReplies smartReplies;
    public final SuppressedActions suppressedActions;

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
