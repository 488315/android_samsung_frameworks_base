package com.android.systemui.qs.external;

import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CustomTile$$ExternalSyntheticLambda8 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), ((CustomTile.SubscreenSALog) obj).getLogId());
    }
}
