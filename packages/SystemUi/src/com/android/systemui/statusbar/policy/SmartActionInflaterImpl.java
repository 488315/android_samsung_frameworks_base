package com.android.systemui.statusbar.policy;

import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.SmartReplyController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SmartActionInflaterImpl implements SmartActionInflater {
    public final ActivityStarter activityStarter;
    public final SmartReplyConstants constants;
    public final SmartReplyController smartReplyController;

    public SmartActionInflaterImpl(SmartReplyConstants smartReplyConstants, ActivityStarter activityStarter, SmartReplyController smartReplyController, HeadsUpManager headsUpManager) {
        this.constants = smartReplyConstants;
        this.activityStarter = activityStarter;
        this.smartReplyController = smartReplyController;
    }
}
