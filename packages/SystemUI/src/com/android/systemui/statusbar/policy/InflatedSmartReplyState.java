package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class InflatedSmartReplyState {
    public final boolean hasPhishingAction;
    public final SmartReplyView.SmartActions smartActions;
    public final SmartReplyView.SmartReplies smartReplies;
    public final SuppressedActions suppressedActions;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
