package com.android.systemui.screenshot.appclips;

import android.content.Context;
import android.content.Intent;
import com.android.internal.infra.ServiceConnector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class AppClipsCrossProcessHelper {
    public final ServiceConnector mProxyConnector;

    public AppClipsCrossProcessHelper(Context context) {
        this.mProxyConnector = new ServiceConnector.Impl(context, new Intent(context, (Class<?>) AppClipsScreenshotHelperService.class), 1073741857, 0, new AppClipsCrossProcessHelper$$ExternalSyntheticLambda0());
    }
}
