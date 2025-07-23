package com.android.systemui.statusbar.notification.promoted;

import android.content.Context;
import com.android.systemui.statusbar.notification.row.shared.SkeletonImageTransform;
import com.android.systemui.util.time.SystemClock;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PromotedNotificationContentExtractorImpl implements PromotedNotificationContentExtractor {
    public final SystemClock systemClock;

    public PromotedNotificationContentExtractorImpl(Context context, SkeletonImageTransform skeletonImageTransform, SystemClock systemClock, PromotedNotificationLogger promotedNotificationLogger) {
        this.systemClock = systemClock;
    }
}
