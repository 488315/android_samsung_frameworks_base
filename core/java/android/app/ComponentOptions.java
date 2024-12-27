package android.app;

import android.os.Bundle;

public class ComponentOptions {
    public static final String KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED =
            "android.pendingIntent.backgroundActivityAllowed";
    public static final String KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED_BY_PERMISSION =
            "android.pendingIntent.backgroundActivityAllowedByPermission";
    private Integer mPendingIntentBalAllowed;
    private boolean mPendingIntentBalAllowedByPermission;

    ComponentOptions() {
        this.mPendingIntentBalAllowed = 0;
        this.mPendingIntentBalAllowedByPermission = false;
    }

    ComponentOptions(Bundle opts) {
        this.mPendingIntentBalAllowed = 0;
        this.mPendingIntentBalAllowedByPermission = false;
        opts.setDefusable(true);
        this.mPendingIntentBalAllowed =
                Integer.valueOf(opts.getInt(KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED, 0));
        setPendingIntentBackgroundActivityLaunchAllowedByPermission(
                opts.getBoolean(
                        KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED_BY_PERMISSION, false));
    }

    @Deprecated
    public void setPendingIntentBackgroundActivityLaunchAllowed(boolean allowed) {
        this.mPendingIntentBalAllowed = Integer.valueOf(allowed ? 1 : 2);
    }

    @Deprecated
    public boolean isPendingIntentBackgroundActivityLaunchAllowed() {
        return this.mPendingIntentBalAllowed.intValue() != 2;
    }

    public ComponentOptions setPendingIntentBackgroundActivityStartMode(int state) {
        switch (state) {
            case -1:
            case 0:
            case 1:
            case 2:
                this.mPendingIntentBalAllowed = Integer.valueOf(state);
                return this;
            default:
                this.mPendingIntentBalAllowed = 1;
                return this;
        }
    }

    public int getPendingIntentBackgroundActivityStartMode() {
        return this.mPendingIntentBalAllowed.intValue();
    }

    public void setPendingIntentBackgroundActivityLaunchAllowedByPermission(boolean allowed) {
        this.mPendingIntentBalAllowedByPermission = allowed;
    }

    public boolean isPendingIntentBackgroundActivityLaunchAllowedByPermission() {
        return this.mPendingIntentBalAllowedByPermission;
    }

    public Bundle toBundle() {
        Bundle b = new Bundle();
        if (this.mPendingIntentBalAllowed.intValue() != 0) {
            b.putInt(
                    KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED,
                    this.mPendingIntentBalAllowed.intValue());
        }
        if (this.mPendingIntentBalAllowedByPermission) {
            b.putBoolean(
                    KEY_PENDING_INTENT_BACKGROUND_ACTIVITY_ALLOWED_BY_PERMISSION,
                    this.mPendingIntentBalAllowedByPermission);
        }
        return b;
    }

    public static ComponentOptions fromBundle(Bundle options) {
        if (options != null) {
            return new ComponentOptions(options);
        }
        return null;
    }
}
