package com.android.systemui.qs.panels.domain.interactor;

import kotlin.jvm.internal.DefaultConstructorMarker;

public final class InfiniteGridConsistencyInteractor implements GridTypeConsistencyInteractor {
    public final InfiniteGridSizeInteractor gridSizeInteractor;
    public final IconTilesInteractor iconTilesInteractor;

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

    public InfiniteGridConsistencyInteractor(IconTilesInteractor iconTilesInteractor, InfiniteGridSizeInteractor infiniteGridSizeInteractor) {
    }
}
