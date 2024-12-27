package com.android.systemui.volume.panel.component.spatial.domain.model;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface SpatialAudioEnabledModel {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final List values = CollectionsKt__CollectionsKt.listOf(Disabled.INSTANCE, SpatialAudioEnabled.Companion, HeadTrackingEnabled.INSTANCE);

        private Companion() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface SpatialAudioEnabled extends SpatialAudioEnabledModel {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Companion implements SpatialAudioEnabled {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
