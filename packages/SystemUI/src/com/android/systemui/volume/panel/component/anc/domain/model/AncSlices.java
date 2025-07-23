package com.android.systemui.volume.panel.component.anc.domain.model;

import androidx.slice.Slice;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface AncSlices {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Ready implements AncSlices {
        public final Slice buttonSlice;
        public final Slice popupSlice;

        public Ready(Slice slice, Slice slice2) {
            this.popupSlice = slice;
            this.buttonSlice = slice2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Ready)) {
                return false;
            }
            Ready ready = (Ready) obj;
            return Intrinsics.areEqual(this.popupSlice, ready.popupSlice) && Intrinsics.areEqual(this.buttonSlice, ready.buttonSlice);
        }

        public final int hashCode() {
            return this.buttonSlice.hashCode() + (this.popupSlice.hashCode() * 31);
        }

        public final String toString() {
            return "Ready(popupSlice=" + this.popupSlice + ", buttonSlice=" + this.buttonSlice + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Unavailable implements AncSlices {
        public static final Unavailable INSTANCE = new Unavailable();

        private Unavailable() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Unavailable);
        }

        public final int hashCode() {
            return -1541257742;
        }

        public final String toString() {
            return "Unavailable";
        }
    }
}
