package com.android.systemui.screenshot.policy;

import com.android.systemui.screenshot.data.repository.ProfileTypeRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class WorkProfilePolicy implements CapturePolicy {

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public WorkProfilePolicy(ProfileTypeRepository profileTypeRepository) {
    }
}
