package com.android.systemui.qs.external;

import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.function.Consumer;

public final /* synthetic */ class CustomTile$$ExternalSyntheticLambda8 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), ((CustomTile.SubscreenSALog) obj).getLogId());
    }
}
