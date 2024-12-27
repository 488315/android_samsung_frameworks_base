package com.android.systemui.statusbar.pipeline.mobile.data.model;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface ResolvedNetworkType extends Diffable {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Companion();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("DefaultNetworkType(lookupKey="), this.lookupKey, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            return ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder("OverrideNetworkType(lookupKey="), this.lookupKey, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            return "Unknown";
        }
    }

    static {
        int i = Companion.$r8$clinit;
    }

    String getLookupKey();

    @Override // com.android.systemui.log.table.Diffable
    default void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        if (((ResolvedNetworkType) obj).equals(this)) {
            return;
        }
        tableRowLoggerImpl.logChange("networkType", toString());
    }
}
