package com.android.tradeinmode.flags;

/* loaded from: classes6.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ENABLE_TRADE_IN_MODE = "com.android.tradeinmode.flags.enable_trade_in_mode";
    public static final String FLAG_TRADE_IN_MODE_2025Q4 = "com.android.tradeinmode.flags.trade_in_mode_2025q4";

    public static boolean enableTradeInMode() {
        return FEATURE_FLAGS.enableTradeInMode();
    }

    public static boolean tradeInMode2025q4() {
        return FEATURE_FLAGS.tradeInMode2025q4();
    }
}
