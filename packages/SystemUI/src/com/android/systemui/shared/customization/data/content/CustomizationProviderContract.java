package com.android.systemui.shared.customization.data.content;

import android.net.Uri;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CustomizationProviderContract {
    public static final Uri BASE_URI;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LockScreenQuickAffordances {
        public static final LockScreenQuickAffordances INSTANCE = new LockScreenQuickAffordances();
        public static final Uri LOCK_SCREEN_QUICK_AFFORDANCE_BASE_URI = CustomizationProviderContract.BASE_URI.buildUpon().path("lockscreen_quickaffordance").build();

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
