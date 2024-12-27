package com.android.systemui.statusbar.policy;

import android.widget.Button;
import java.util.List;

public final class InflatedSmartReplyViewHolder {
    public final SmartReplyView smartReplyView;
    public final List smartSuggestionButtons;

    public InflatedSmartReplyViewHolder(SmartReplyView smartReplyView, List<? extends Button> list) {
        this.smartReplyView = smartReplyView;
        this.smartSuggestionButtons = list;
    }
}
