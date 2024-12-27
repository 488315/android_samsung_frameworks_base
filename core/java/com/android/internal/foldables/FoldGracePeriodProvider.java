package com.android.internal.foldables;

import android.os.Build;
import android.sysprop.FoldLockBehaviorProperties;
import android.util.Slog;

import com.android.internal.foldables.flags.Flags;

import java.util.function.Supplier;

public class FoldGracePeriodProvider {
    private static final String TAG = "FoldGracePeriodProvider";
    private final Supplier<Boolean> mFoldGracePeriodEnabled =
            new Supplier() { // from class:
                             // com.android.internal.foldables.FoldGracePeriodProvider$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Boolean.valueOf(Flags.foldGracePeriodEnabled());
                }
            };

    public boolean isEnabled() {
        if ((Build.IS_ENG || Build.IS_USERDEBUG)
                && FoldLockBehaviorProperties.fold_grace_period_enabled()
                        .orElse(false)
                        .booleanValue()) {
            return true;
        }
        try {
            return this.mFoldGracePeriodEnabled.get().booleanValue();
        } catch (Throwable ex) {
            Slog.i(
                    TAG,
                    "Flags not ready yet. Return false for"
                        + " com.android.internal.foldables.flags.fold_grace_period_enabled",
                    ex);
            return false;
        }
    }
}
