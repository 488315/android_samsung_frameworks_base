package com.android.systemui.qs.pipeline.domain.model;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface AutoAddTracking {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Always implements AutoAddTracking {
        public static final Always INSTANCE = new Always();

        private Always() {
        }

        public final String toString() {
            return "Always";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Disabled implements AutoAddTracking {
        public static final Disabled INSTANCE = new Disabled();

        private Disabled() {
        }

        public final String toString() {
            return KnoxVpnPolicyConstants.VPN_CERT_TYPE_DISABLED;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class IfNotAdded implements AutoAddTracking {
        public final TileSpec spec;

        public IfNotAdded(TileSpec tileSpec) {
            this.spec = tileSpec;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof IfNotAdded) && Intrinsics.areEqual(this.spec, ((IfNotAdded) obj).spec);
        }

        public final int hashCode() {
            return this.spec.hashCode();
        }

        public final String toString() {
            return "IfNotAdded(spec=" + this.spec + ")";
        }
    }
}
