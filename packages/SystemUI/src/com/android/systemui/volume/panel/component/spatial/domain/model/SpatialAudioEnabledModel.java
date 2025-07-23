package com.android.systemui.volume.panel.component.spatial.domain.model;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SpatialAudioEnabledModel {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final List values = Arrays.asList(Disabled.INSTANCE, SpatialAudioEnabled.Companion, HeadTrackingEnabled.INSTANCE);

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SpatialAudioEnabled extends SpatialAudioEnabledModel {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion implements SpatialAudioEnabled {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            return C2paManifestList.UNKNOWN_VALUE;
        }
    }
}
