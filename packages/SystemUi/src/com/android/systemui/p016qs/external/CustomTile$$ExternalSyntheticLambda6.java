package com.android.systemui.p016qs.external;

import com.android.systemui.p016qs.external.CustomTile;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CustomTile$$ExternalSyntheticLambda6 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, ((CustomTile.SubscreenSALog) obj).getLogId());
    }
}
