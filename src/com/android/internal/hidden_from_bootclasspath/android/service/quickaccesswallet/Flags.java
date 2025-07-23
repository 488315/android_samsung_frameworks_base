package com.android.internal.hidden_from_bootclasspath.android.service.quickaccesswallet;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_LAUNCH_SELECTED_CARD_FROM_QS_TILE = "android.service.quickaccesswallet.launch_selected_card_from_qs_tile";
    public static final String FLAG_LAUNCH_WALLET_OPTION_ON_POWER_DOUBLE_TAP = "android.service.quickaccesswallet.launch_wallet_option_on_power_double_tap";
    public static final String FLAG_LAUNCH_WALLET_VIA_SYSUI_CALLBACKS = "android.service.quickaccesswallet.launch_wallet_via_sysui_callbacks";

    public static boolean launchSelectedCardFromQsTile() {
        return FEATURE_FLAGS.launchSelectedCardFromQsTile();
    }

    public static boolean launchWalletOptionOnPowerDoubleTap() {
        return FEATURE_FLAGS.launchWalletOptionOnPowerDoubleTap();
    }

    public static boolean launchWalletViaSysuiCallbacks() {
        return FEATURE_FLAGS.launchWalletViaSysuiCallbacks();
    }
}
