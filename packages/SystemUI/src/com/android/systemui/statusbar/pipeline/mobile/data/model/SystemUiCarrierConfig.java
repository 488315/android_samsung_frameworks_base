package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.os.PersistableBundle;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class SystemUiCarrierConfig {
    public final ReadonlyStateFlow allowNetworkSliceIndicator;
    public boolean isUsingDefault = true;
    public final ReadonlyStateFlow shouldInflateSignalStrength;
    public final ReadonlyStateFlow showOperatorNameInStatusBar;
    public final List trackedConfigs;

    public SystemUiCarrierConfig(int i, PersistableBundle persistableBundle) {
        BooleanCarrierConfig booleanCarrierConfig = new BooleanCarrierConfig("inflate_signal_strength_bool", persistableBundle);
        this.shouldInflateSignalStrength = booleanCarrierConfig.config;
        BooleanCarrierConfig booleanCarrierConfig2 = new BooleanCarrierConfig("show_operator_name_in_statusbar_bool", persistableBundle);
        this.showOperatorNameInStatusBar = booleanCarrierConfig2.config;
        BooleanCarrierConfig booleanCarrierConfig3 = new BooleanCarrierConfig("show_5g_slice_icon_bool", persistableBundle);
        this.allowNetworkSliceIndicator = booleanCarrierConfig3.config;
        this.trackedConfigs = Arrays.asList(booleanCarrierConfig, booleanCarrierConfig2, booleanCarrierConfig3);
    }

    public final void processNewCarrierConfig(PersistableBundle persistableBundle) {
        this.isUsingDefault = false;
        for (BooleanCarrierConfig booleanCarrierConfig : this.trackedConfigs) {
            booleanCarrierConfig._configValue.updateState(null, Boolean.valueOf(persistableBundle.getBoolean(booleanCarrierConfig.key)));
        }
    }

    public final String toString() {
        return CollectionsKt___CollectionsKt.joinToString$default(this.trackedConfigs, null, null, null, new SystemUiCarrierConfig$$ExternalSyntheticLambda0(0), 31);
    }

    public static /* synthetic */ void isUsingDefault$annotations() {
    }
}
