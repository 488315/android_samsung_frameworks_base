package com.android.systemui.qs.panels.domain.interactor;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class InfiniteGridConsistencyInteractor implements GridTypeConsistencyInteractor {
    public final InfiniteGridSizeInteractor gridSizeInteractor;
    public final IconTilesInteractor iconTilesInteractor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
