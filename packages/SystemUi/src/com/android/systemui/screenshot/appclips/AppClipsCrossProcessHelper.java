package com.android.systemui.screenshot.appclips;

import android.content.Context;
import android.content.Intent;
import android.os.UserManager;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.settings.DisplayTracker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AppClipsCrossProcessHelper {
    public final DisplayTracker mDisplayTracker;
    public final ServiceConnector mProxyConnector;

    public AppClipsCrossProcessHelper(Context context, UserManager userManager, DisplayTracker displayTracker) {
        this.mProxyConnector = new ServiceConnector.Impl(context, new Intent(context, (Class<?>) AppClipsScreenshotHelperService.class), 1073741857, userManager.getMainUser().getIdentifier(), new AppClipsCrossProcessHelper$$ExternalSyntheticLambda0());
        this.mDisplayTracker = displayTracker;
    }
}
