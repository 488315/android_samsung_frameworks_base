package com.android.systemui.qs.external;

import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class CustomTile$$ExternalSyntheticLambda8 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), ((CustomTile.SubscreenSALog) obj).getLogId());
    }
}
