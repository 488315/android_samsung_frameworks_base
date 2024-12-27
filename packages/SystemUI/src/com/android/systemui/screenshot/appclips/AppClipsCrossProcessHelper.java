package com.android.systemui.screenshot.appclips;

import android.content.Context;
import android.content.Intent;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.settings.DisplayTracker;

public final class AppClipsCrossProcessHelper {
    public final DisplayTracker mDisplayTracker;
    public final ServiceConnector mProxyConnector;

    public AppClipsCrossProcessHelper(Context context, DisplayTracker displayTracker) {
        this.mProxyConnector = new ServiceConnector.Impl(context, new Intent(context, (Class<?>) AppClipsScreenshotHelperService.class), 1073741857, 0, new AppClipsCrossProcessHelper$$ExternalSyntheticLambda0());
        this.mDisplayTracker = displayTracker;
    }
}
