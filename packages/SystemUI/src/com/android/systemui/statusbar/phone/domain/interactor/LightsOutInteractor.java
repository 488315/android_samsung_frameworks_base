package com.android.systemui.statusbar.phone.domain.interactor;

import com.android.systemui.statusbar.data.model.StatusBarMode;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;

/* loaded from: classes3.dex */
public final class LightsOutInteractor {
    public final StatusBarModeRepositoryStore repository;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[StatusBarMode.values().length];
            try {
                iArr[StatusBarMode.LIGHTS_OUT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[StatusBarMode.LIGHTS_OUT_TRANSPARENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public LightsOutInteractor(StatusBarModeRepositoryStore statusBarModeRepositoryStore) {
        this.repository = statusBarModeRepositoryStore;
    }
}
