package com.android.systemui.qs.pipeline.domain.model;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface AutoAddSignal {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Remove implements AutoAddSignal {
        public final TileSpec spec;

        public Remove(TileSpec tileSpec) {
            this.spec = tileSpec;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Remove) && Intrinsics.areEqual(this.spec, ((Remove) obj).spec);
        }

        public final int hashCode() {
            return this.spec.hashCode();
        }

        public final String toString() {
            return "Remove(spec=" + this.spec + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RemoveTracking implements AutoAddSignal {
        public final TileSpec spec;

        public RemoveTracking(TileSpec tileSpec) {
            this.spec = tileSpec;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof RemoveTracking) && Intrinsics.areEqual(this.spec, ((RemoveTracking) obj).spec);
        }

        public final int hashCode() {
            return this.spec.hashCode();
        }

        public final String toString() {
            return "RemoveTracking(spec=" + this.spec + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Add implements AutoAddSignal {
        public final int position;
        public final TileSpec spec;

        public Add(TileSpec tileSpec, int i) {
            this.spec = tileSpec;
            this.position = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Add)) {
                return false;
            }
            Add add = (Add) obj;
            return Intrinsics.areEqual(this.spec, add.spec) && this.position == add.position;
        }

        public final int hashCode() {
            return Integer.hashCode(this.position) + (this.spec.hashCode() * 31);
        }

        public final String toString() {
            return "Add(spec=" + this.spec + ", position=" + this.position + ")";
        }

        public /* synthetic */ Add(TileSpec tileSpec, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(tileSpec, (i2 & 2) != 0 ? -1 : i);
        }
    }
}
