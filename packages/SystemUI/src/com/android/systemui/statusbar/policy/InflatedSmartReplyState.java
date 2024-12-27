package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class InflatedSmartReplyState {
    public final boolean hasPhishingAction;
    public final SmartReplyView.SmartActions smartActions;
    public final SmartReplyView.SmartReplies smartReplies;
    public final SuppressedActions suppressedActions;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
