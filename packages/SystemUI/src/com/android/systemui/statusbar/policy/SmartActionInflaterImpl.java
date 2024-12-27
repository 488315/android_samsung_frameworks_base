package com.android.systemui.statusbar.policy;

import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.SmartReplyController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
