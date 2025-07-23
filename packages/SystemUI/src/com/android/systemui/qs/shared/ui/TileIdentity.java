package com.android.systemui.qs.shared.ui;

import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileIdentity {
    public final int position;
    public final TileSpec spec;

    public TileIdentity(TileSpec tileSpec, int i) {
        this.spec = tileSpec;
        this.position = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TileIdentity)) {
            return false;
        }
        TileIdentity tileIdentity = (TileIdentity) obj;
        return Intrinsics.areEqual(this.spec, tileIdentity.spec) && this.position == tileIdentity.position;
    }

    public final int hashCode() {
        return Integer.hashCode(this.position) + (this.spec.hashCode() * 31);
    }

    public final String toString() {
        return "TileIdentity(spec=" + this.spec + ", position=" + this.position + ")";
    }
}
