package com.android.systemui.deviceentry.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class DeviceUnlockSource {
    public final boolean dismissesLockscreen;

    public final class BouncerInput extends DeviceUnlockSource {
        public static final BouncerInput INSTANCE = new BouncerInput();

        private BouncerInput() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof BouncerInput);
        }

        public final int hashCode() {
            return 2089338940;
        }

        public final String toString() {
            return "BouncerInput";
        }
    }

    public final class FaceWithBypass extends DeviceUnlockSource {
        public static final FaceWithBypass INSTANCE = new FaceWithBypass();

        private FaceWithBypass() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof FaceWithBypass);
        }

        public final int hashCode() {
            return -35153465;
        }

        public final String toString() {
            return "FaceWithBypass";
        }
    }

    public final class FaceWithoutBypass extends DeviceUnlockSource {
        public static final FaceWithoutBypass INSTANCE = new FaceWithoutBypass();

        private FaceWithoutBypass() {
            super(false, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof FaceWithoutBypass);
        }

        public final int hashCode() {
            return 975857847;
        }

        public final String toString() {
            return "FaceWithoutBypass";
        }
    }

    public final class Fingerprint extends DeviceUnlockSource {
        public static final Fingerprint INSTANCE = new Fingerprint();

        private Fingerprint() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Fingerprint);
        }

        public final int hashCode() {
            return -1818240984;
        }

        public final String toString() {
            return "Fingerprint";
        }
    }

    public final class TrustAgent extends DeviceUnlockSource {
        public static final TrustAgent INSTANCE = new TrustAgent();

        private TrustAgent() {
            super(false, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof TrustAgent);
        }

        public final int hashCode() {
            return -1523751991;
        }

        public final String toString() {
            return "TrustAgent";
        }
    }

    public /* synthetic */ DeviceUnlockSource(boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(z);
    }

    private DeviceUnlockSource(boolean z) {
        this.dismissesLockscreen = z;
    }
}
