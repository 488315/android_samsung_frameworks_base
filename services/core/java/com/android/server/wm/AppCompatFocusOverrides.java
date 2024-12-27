package com.android.server.wm;

import com.android.server.wm.utils.OptPropFactory;

import java.util.Objects;
import java.util.function.BooleanSupplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
