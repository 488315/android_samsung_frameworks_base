package com.android.systemui.ambient.statusbar.ui;

import android.util.PluralsMessageFormatter;
import com.android.systemui.R;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AmbientStatusBarViewController$$ExternalSyntheticLambda2 {
    public final /* synthetic */ AmbientStatusBarViewController f$0;

    public /* synthetic */ AmbientStatusBarViewController$$ExternalSyntheticLambda2(AmbientStatusBarViewController ambientStatusBarViewController) {
        this.f$0 = ambientStatusBarViewController;
    }

    public final void onNotificationCountChanged(int i) {
        AmbientStatusBarViewController ambientStatusBarViewController = this.f$0;
        ambientStatusBarViewController.mMainExecutor.execute(new AmbientStatusBarViewController$$ExternalSyntheticLambda10(ambientStatusBarViewController, i > 0, 0, i > 0 ? PluralsMessageFormatter.format(ambientStatusBarViewController.mResources, Map.of(SystemUIAnalytics.QPNE_KEY_COUNT, Integer.valueOf(i)), R.string.dream_overlay_status_bar_notification_indicator) : null));
    }
}
