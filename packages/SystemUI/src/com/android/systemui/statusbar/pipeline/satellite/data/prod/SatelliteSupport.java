package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.satellite.SatelliteManager;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SatelliteSupport {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NotSupported implements SatelliteSupport {
        public static final NotSupported INSTANCE = new NotSupported();

        private NotSupported() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NotSupported);
        }

        public final int hashCode() {
            return -1705600401;
        }

        public final String toString() {
            return ActionResults.RESULT_NOT_SUPPORTED;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Supported implements SatelliteSupport {
        public final SatelliteManager satelliteManager;

        public Supported(SatelliteManager satelliteManager) {
            this.satelliteManager = satelliteManager;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Supported) && Intrinsics.areEqual(this.satelliteManager, ((Supported) obj).satelliteManager);
        }

        public final int hashCode() {
            return this.satelliteManager.hashCode();
        }

        public final String toString() {
            return "Supported(satelliteManager=" + this.satelliteManager + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Unknown implements SatelliteSupport {
        public static final Unknown INSTANCE = new Unknown();

        private Unknown() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Unknown);
        }

        public final int hashCode() {
            return 1450120310;
        }

        public final String toString() {
            return C2paManifestList.UNKNOWN_VALUE;
        }
    }
}
