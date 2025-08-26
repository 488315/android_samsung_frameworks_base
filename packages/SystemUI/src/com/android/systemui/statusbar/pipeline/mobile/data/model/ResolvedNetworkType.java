package com.android.systemui.statusbar.pipeline.mobile.data.model;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public interface ResolvedNetworkType extends Diffable {

    public final class CarrierMergedNetworkType implements ResolvedNetworkType {
        public static final CarrierMergedNetworkType INSTANCE = new CarrierMergedNetworkType();
        public static final String lookupKey = "cwf";
        public static final SignalIcon$MobileIconGroup iconGroupOverride = TelephonyIcons.CARRIER_MERGED_WIFI;

        private CarrierMergedNetworkType() {
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType
        public final String getLookupKey() {
            return lookupKey;
        }

        public final String toString() {
            return "CarrierMerged";
        }
    }

    public final class Companion {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Companion();
        }

        private Companion() {
        }
    }

    public final class DefaultNetworkType implements ResolvedNetworkType {
        public final String lookupKey;

        public DefaultNetworkType(String str) {
            this.lookupKey = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DefaultNetworkType) && Intrinsics.areEqual(this.lookupKey, ((DefaultNetworkType) obj).lookupKey);
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType
        public final String getLookupKey() {
            return this.lookupKey;
        }

        public final int hashCode() {
            return this.lookupKey.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("DefaultNetworkType(lookupKey="), this.lookupKey, ")");
        }
    }

    public final class OverrideNetworkType implements ResolvedNetworkType {
        public final String lookupKey;

        public OverrideNetworkType(String str) {
            this.lookupKey = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OverrideNetworkType) && Intrinsics.areEqual(this.lookupKey, ((OverrideNetworkType) obj).lookupKey);
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType
        public final String getLookupKey() {
            return this.lookupKey;
        }

        public final int hashCode() {
            return this.lookupKey.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("OverrideNetworkType(lookupKey="), this.lookupKey, ")");
        }
    }

    public final class UnknownNetworkType implements ResolvedNetworkType {
        public static final UnknownNetworkType INSTANCE = new UnknownNetworkType();
        public static final String lookupKey = Integer.toString(0);

        private UnknownNetworkType() {
        }

        @Override // com.android.systemui.statusbar.pipeline.mobile.data.model.ResolvedNetworkType
        public final String getLookupKey() {
            return lookupKey;
        }

        public final String toString() {
            return C2paManifestList.UNKNOWN_VALUE;
        }
    }

    static {
        int i = Companion.$r8$clinit;
    }

    String getLookupKey();

    @Override // com.android.systemui.log.table.Diffable
    default void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        if (Intrinsics.areEqual((ResolvedNetworkType) diffable, this)) {
            return;
        }
        tableRowLoggerImpl.logChange("networkType", toString());
    }
}
