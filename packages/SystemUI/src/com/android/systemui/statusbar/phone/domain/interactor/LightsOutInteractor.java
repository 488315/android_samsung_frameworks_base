package com.android.systemui.statusbar.phone.domain.interactor;

import com.android.systemui.statusbar.data.model.StatusBarMode;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LightsOutInteractor {
    public final StatusBarModeRepositoryStore repository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
