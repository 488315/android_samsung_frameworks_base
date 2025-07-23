package com.android.systemui.qs.tiles.impl.flashlight.domain.model;

import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface FlashlightTileModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class FlashlightAvailable implements FlashlightTileModel {
        public final boolean isEnabled;

        private /* synthetic */ FlashlightAvailable(boolean z) {
            this.isEnabled = z;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ FlashlightAvailable m2912boximpl(boolean z) {
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
            return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("FlashlightAvailable(isEnabled="), this.isEnabled, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
