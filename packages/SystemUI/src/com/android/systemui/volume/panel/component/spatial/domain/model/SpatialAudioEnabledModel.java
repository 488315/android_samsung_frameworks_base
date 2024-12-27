package com.android.systemui.volume.panel.component.spatial.domain.model;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;

public interface SpatialAudioEnabledModel {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final List values = CollectionsKt__CollectionsKt.listOf(Disabled.INSTANCE, SpatialAudioEnabled.Companion, HeadTrackingEnabled.INSTANCE);

        private Companion() {
        }
    }

    public final class Disabled implements SpatialAudioEnabledModel {
        public static final Disabled INSTANCE = new Disabled();

        private Disabled() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Disabled);
        }

        public final int hashCode() {
            return 569056123;
        }

        public final String toString() {
            return KnoxVpnPolicyConstants.VPN_CERT_TYPE_DISABLED;
        }
    }

    public final class HeadTrackingEnabled implements SpatialAudioEnabled {
        public static final HeadTrackingEnabled INSTANCE = new HeadTrackingEnabled();

        private HeadTrackingEnabled() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof HeadTrackingEnabled);
        }

        public final int hashCode() {
            return 714776619;
        }

        public final String toString() {
            return "HeadTrackingEnabled";
        }
    }

    public interface SpatialAudioEnabled extends SpatialAudioEnabledModel {
        public static final Companion Companion = Companion.$$INSTANCE;

        public final class Companion implements SpatialAudioEnabled {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }
    }

    public final class Unknown implements SpatialAudioEnabled {
        public static final Unknown INSTANCE = new Unknown();

        private Unknown() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Unknown);
        }

        public final int hashCode() {
            return 1387343723;
        }

        public final String toString() {
            return "Unknown";
        }
    }
}
