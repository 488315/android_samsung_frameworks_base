package android.app;

import android.os.Bundle;

/* loaded from: classes.dex */
public class ComponentOptions {
    public static final String KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED = "android.pendingIntent.backgroundActivityAllowed";
    private Integer mPendingIntentBalAllowed;

    ComponentOptions() {
        this.mPendingIntentBalAllowed = 0;
    }

    ComponentOptions(Bundle bundle) {
        this.mPendingIntentBalAllowed = 0;
        bundle.setDefusable(true);
        this.mPendingIntentBalAllowed = Integer.valueOf(bundle.getInt(KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED, 0));
    }

    @Deprecated
    public void setPendingIntentBackgroundActivityLaunchAllowed(boolean z) {
        this.mPendingIntentBalAllowed = Integer.valueOf(z ? 1 : 2);
    }

    @Deprecated
    public boolean isPendingIntentBackgroundActivityLaunchAllowed() {
        return this.mPendingIntentBalAllowed.intValue() != 2;
    }

    public ComponentOptions setPendingIntentBackgroundActivityStartMode(int i) {
        if (i == -1 || i == 0 || i == 1 || i == 2 || i == 3 || i == 4) {
            this.mPendingIntentBalAllowed = Integer.valueOf(i);
            return this;
        }
        this.mPendingIntentBalAllowed = 1;
        return this;
    }

    public int getPendingIntentBackgroundActivityStartMode() {
        return this.mPendingIntentBalAllowed.intValue();
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        if (this.mPendingIntentBalAllowed.intValue() != 0) {
            bundle.putInt(KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED, this.mPendingIntentBalAllowed.intValue());
        }
        return bundle;
    }

    public static ComponentOptions fromBundle(Bundle bundle) {
        if (bundle != null) {
            return new ComponentOptions(bundle);
        }
        return null;
    }
}
