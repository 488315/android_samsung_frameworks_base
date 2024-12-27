package com.android.server.wm;

import com.android.server.wm.utils.OptPropFactory;

import java.util.Objects;
import java.util.function.BooleanSupplier;

public final class AppCompatFocusOverrides {
    public final ActivityRecord mActivityRecord;
    public final OptPropFactory.OptProp mFakeFocusOptProp;

    public AppCompatFocusOverrides(
            ActivityRecord activityRecord,
            final AppCompatConfiguration appCompatConfiguration,
            OptPropFactory optPropFactory) {
        this.mActivityRecord = activityRecord;
        Objects.requireNonNull(appCompatConfiguration);
        this.mFakeFocusOptProp =
                optPropFactory.create(
                        "android.window.PROPERTY_COMPAT_ENABLE_FAKE_FOCUS",
                        new BooleanSupplier() { // from class:
                                                // com.android.server.wm.AppCompatFocusOverrides$$ExternalSyntheticLambda0
                            @Override // java.util.function.BooleanSupplier
                            public final boolean getAsBoolean() {
                                return AppCompatConfiguration.this.mDeviceConfig.getFlagValue(
                                        "enable_compat_fake_focus");
                            }
                        });
    }
}
