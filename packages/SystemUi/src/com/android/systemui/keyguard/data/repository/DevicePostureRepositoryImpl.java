package com.android.systemui.keyguard.data.repository;

import com.android.systemui.statusbar.policy.DevicePostureController;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DevicePostureRepositoryImpl implements DevicePostureRepository {
    public final DevicePostureController postureController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public DevicePostureRepositoryImpl(DevicePostureController devicePostureController) {
        this.postureController = devicePostureController;
    }
}
