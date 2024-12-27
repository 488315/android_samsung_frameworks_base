package com.android.internal.foldables;

import android.content.res.Resources;
import android.os.Build;
import android.sysprop.FoldLockBehaviorProperties;
import android.util.Slog;

import com.android.internal.R;
import com.android.internal.foldables.flags.Flags;

import java.util.function.Supplier;

public class FoldLockSettingAvailabilityProvider {
    private static final String TAG = "FoldLockSettingAvailabilityProvider";
    private final boolean mFoldLockBehaviorResourceValue;
    private final Supplier<Boolean> mFoldLockSettingEnabled =
            new Supplier() { // from class:
                             // com.android.internal.foldables.FoldLockSettingAvailabilityProvider$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Boolean.valueOf(Flags.foldLockSettingEnabled());
                }
            };

    public FoldLockSettingAvailabilityProvider(Resources resources) {
        this.mFoldLockBehaviorResourceValue =
                resources.getBoolean(R.bool.config_fold_lock_behavior);
    }

    public boolean isFoldLockBehaviorAvailable() {
        return this.mFoldLockBehaviorResourceValue && flagOrSystemProperty();
    }

    private boolean flagOrSystemProperty() {
        if ((Build.IS_ENG || Build.IS_USERDEBUG)
                && FoldLockBehaviorProperties.fold_lock_setting_enabled()
                        .orElse(false)
                        .booleanValue()) {
            return true;
        }
        try {
            return this.mFoldLockSettingEnabled.get().booleanValue();
        } catch (Throwable ex) {
            Slog.i(
                    TAG,
                    "Flags not ready yet. Return false for"
                        + " com.android.internal.foldables.flags.fold_lock_setting_enabled",
                    ex);
            return false;
        }
    }
}
