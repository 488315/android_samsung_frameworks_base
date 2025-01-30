package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.satellite.SatelliteManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface SatelliteSupport {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NotSupported implements SatelliteSupport {
        public static final NotSupported INSTANCE = new NotSupported();

        private NotSupported() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Unknown implements SatelliteSupport {
        public static final Unknown INSTANCE = new Unknown();

        private Unknown() {
        }
    }
}
