package com.android.systemui.dreams;

import android.util.PluralsMessageFormatter;
import com.android.systemui.R;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DreamOverlayStatusBarViewController$$ExternalSyntheticLambda3 {
    public final /* synthetic */ DreamOverlayStatusBarViewController f$0;

    public final void onNotificationCountChanged(int i) {
        DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController = this.f$0;
        dreamOverlayStatusBarViewController.getClass();
        dreamOverlayStatusBarViewController.mMainExecutor.execute(new DreamOverlayStatusBarViewController$$ExternalSyntheticLambda6(dreamOverlayStatusBarViewController, 0, i > 0, i > 0 ? PluralsMessageFormatter.format(dreamOverlayStatusBarViewController.mResources, Map.of("count", Integer.valueOf(i)), R.string.dream_overlay_status_bar_notification_indicator) : null));
    }
}
