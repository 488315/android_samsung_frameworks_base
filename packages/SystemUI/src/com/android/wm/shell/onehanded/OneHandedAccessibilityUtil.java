package com.android.wm.shell.onehanded;

import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OneHandedAccessibilityUtil {
    public final AccessibilityManager mAccessibilityManager;
    public String mDescription;
    public final String mPackageName;
    public final String mStartOneHandedDescription;
    public final String mStopOneHandedDescription;

    public OneHandedAccessibilityUtil(Context context) {
        this.mAccessibilityManager = AccessibilityManager.getInstance(context);
        this.mPackageName = context.getPackageName();
        this.mStartOneHandedDescription = context.getResources().getString(R.string.accessibility_action_start_one_handed);
        this.mStopOneHandedDescription = context.getResources().getString(R.string.accessibility_action_stop_one_handed);
    }

    public final void announcementForScreenReader(String str) {
        if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
            this.mDescription = str;
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setPackageName(this.mPackageName);
            obtain.setEventType(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
            obtain.getText().add(this.mDescription);
            this.mAccessibilityManager.sendAccessibilityEvent(obtain);
        }
    }
}
