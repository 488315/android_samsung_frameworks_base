package com.android.systemui.qs.pipeline.domain.model;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import kotlin.jvm.internal.Intrinsics;

public interface AutoAddTracking {

    public final class Always implements AutoAddTracking {
        public static final Always INSTANCE = new Always();

        private Always() {
        }

        public final String toString() {
            return "Always";
        }
    }

    public final class Disabled implements AutoAddTracking {
        public static final Disabled INSTANCE = new Disabled();

        private Disabled() {
        }

        public final String toString() {
            return KnoxVpnPolicyConstants.VPN_CERT_TYPE_DISABLED;
        }
    }

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
