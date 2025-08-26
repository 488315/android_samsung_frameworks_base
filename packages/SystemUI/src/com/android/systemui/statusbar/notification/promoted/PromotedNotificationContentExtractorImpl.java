package com.android.systemui.statusbar.notification.promoted;

import android.content.Context;
import com.android.systemui.statusbar.notification.row.shared.SkeletonImageTransform;
import com.android.systemui.util.time.SystemClock;

/* loaded from: classes3.dex */
public final class PromotedNotificationContentExtractorImpl implements PromotedNotificationContentExtractor {
    public final SystemClock systemClock;

    public PromotedNotificationContentExtractorImpl(Context context, SkeletonImageTransform skeletonImageTransform, SystemClock systemClock, PromotedNotificationLogger promotedNotificationLogger) {
        this.systemClock = systemClock;
    }
}
