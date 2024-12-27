package com.android.systemui.qs.tiles.impl.flashlight.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

public interface FlashlightTileModel {

    public final class FlashlightAvailable implements FlashlightTileModel {
        public final boolean isEnabled;

        private /* synthetic */ FlashlightAvailable(boolean z) {
            this.isEnabled = z;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ FlashlightAvailable m2090boximpl(boolean z) {
            return new FlashlightAvailable(z);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof FlashlightAvailable) {
                return this.isEnabled == ((FlashlightAvailable) obj).isEnabled;
            }
            return false;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isEnabled);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("FlashlightAvailable(isEnabled="), this.isEnabled, ")");
        }
    }

    public final class FlashlightTemporarilyUnavailable implements FlashlightTileModel {
        public static final FlashlightTemporarilyUnavailable INSTANCE = new FlashlightTemporarilyUnavailable();

        private FlashlightTemporarilyUnavailable() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof FlashlightTemporarilyUnavailable);
        }

        public final int hashCode() {
            return 408229728;
        }

        public final String toString() {
            return "FlashlightTemporarilyUnavailable";
        }
    }
}
