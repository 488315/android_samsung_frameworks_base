package com.android.systemui.communal.posturing.shared.model;

import com.samsung.android.sdk.scs.ai.visual.c2pa.C2paManifestList;

/* loaded from: classes2.dex */
public interface PosturedState {

    public final class NotPostured implements PosturedState {
        public static final NotPostured INSTANCE = new NotPostured();

        private NotPostured() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NotPostured);
        }

        public final int hashCode() {
            return 1283681680;
        }

        public final String toString() {
            return "NotPostured";
        }
    }

    public final class Postured implements PosturedState {
        public static final Postured INSTANCE = new Postured();

        private Postured() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Postured);
        }

        public final int hashCode() {
            return -1679620293;
        }

        public final String toString() {
            return "Postured";
        }
    }

    public final class Unknown implements PosturedState {
        public static final Unknown INSTANCE = new Unknown();

        private Unknown() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Unknown);
        }

        public final int hashCode() {
            return 52168139;
        }

        public final String toString() {
            return C2paManifestList.UNKNOWN_VALUE;
        }
    }
}
