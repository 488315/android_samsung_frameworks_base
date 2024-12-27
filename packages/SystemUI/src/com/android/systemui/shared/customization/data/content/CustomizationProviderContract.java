package com.android.systemui.shared.customization.data.content;

import android.net.Uri;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class CustomizationProviderContract {
    public static final Uri BASE_URI;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class LockScreenQuickAffordances {
        public static final LockScreenQuickAffordances INSTANCE = new LockScreenQuickAffordances();
        public static final Uri LOCK_SCREEN_QUICK_AFFORDANCE_BASE_URI = CustomizationProviderContract.BASE_URI.buildUpon().path("lockscreen_quickaffordance").build();

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class SelectionTable {
            public static final SelectionTable INSTANCE = new SelectionTable();
            public static final Uri URI = LockScreenQuickAffordances.LOCK_SCREEN_QUICK_AFFORDANCE_BASE_URI.buildUpon().appendPath("selections").build();

            private SelectionTable() {
            }
        }

        private LockScreenQuickAffordances() {
        }
    }

    static {
        new CustomizationProviderContract();
        BASE_URI = new Uri.Builder().scheme("content").authority("com.android.systemui.customization").build();
    }

    private CustomizationProviderContract() {
    }
}
