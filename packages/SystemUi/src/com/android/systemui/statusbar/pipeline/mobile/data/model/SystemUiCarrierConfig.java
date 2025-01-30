package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.os.PersistableBundle;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SystemUiCarrierConfig {
    public boolean isUsingDefault = true;
    public final ReadonlyStateFlow shouldInflateSignalStrength;
    public final List trackedConfigs;

    public SystemUiCarrierConfig(int i, PersistableBundle persistableBundle) {
        BooleanCarrierConfig booleanCarrierConfig = new BooleanCarrierConfig("inflate_signal_strength_bool", persistableBundle);
        this.shouldInflateSignalStrength = booleanCarrierConfig.config;
        this.trackedConfigs = CollectionsKt__CollectionsKt.listOf(booleanCarrierConfig, new BooleanCarrierConfig("show_operator_name_in_statusbar_bool", persistableBundle));
    }

    public final void processNewCarrierConfig(PersistableBundle persistableBundle) {
        this.isUsingDefault = false;
        for (BooleanCarrierConfig booleanCarrierConfig : this.trackedConfigs) {
            booleanCarrierConfig._configValue.setValue(Boolean.valueOf(persistableBundle.getBoolean(booleanCarrierConfig.key)));
        }
    }

    public final String toString() {
        return CollectionsKt___CollectionsKt.joinToString$default(this.trackedConfigs, null, null, null, new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.model.SystemUiCarrierConfig$toString$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ((BooleanCarrierConfig) obj).toString();
            }
        }, 31);
    }

    public final String toStringConsideringDefaults() {
        return this.isUsingDefault ? "using defaults" : CollectionsKt___CollectionsKt.joinToString$default(this.trackedConfigs, null, null, null, new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.model.SystemUiCarrierConfig$toStringConsideringDefaults$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ((BooleanCarrierConfig) obj).toString();
            }
        }, 31);
    }

    public static /* synthetic */ void isUsingDefault$annotations() {
    }
}
