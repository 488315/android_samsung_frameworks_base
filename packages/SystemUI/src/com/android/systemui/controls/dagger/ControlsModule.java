package com.android.systemui.controls.dagger;

import android.content.pm.PackageManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class ControlsModule {
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public static final boolean providesControlsFeatureEnabled(PackageManager packageManager) {
        Companion.getClass();
        return packageManager.hasSystemFeature("android.software.controls");
    }
}
