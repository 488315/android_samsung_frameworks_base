package com.android.systemui.shade.data.repository;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import java.util.UUID;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FlingInfo {
    public final boolean expand;
    public final UUID id;
    public final float velocity;

    public FlingInfo(boolean z) {
        this(z, 0.0f, null, 6, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FlingInfo)) {
            return false;
        }
        FlingInfo flingInfo = (FlingInfo) obj;
        return this.expand == flingInfo.expand && Float.compare(this.velocity, flingInfo.velocity) == 0 && Intrinsics.areEqual(this.id, flingInfo.id);
    }

    public final int hashCode() {
        return this.id.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.velocity, Boolean.hashCode(this.expand) * 31, 31);
    }

    public final String toString() {
        return "FlingInfo(expand=" + this.expand + ", velocity=" + this.velocity + ", id=" + this.id + ")";
    }

    public FlingInfo(boolean z, float f) {
        this(z, f, null, 4, null);
    }

    public FlingInfo(boolean z, float f, UUID uuid) {
        this.expand = z;
        this.velocity = f;
        this.id = uuid;
    }

    public /* synthetic */ FlingInfo(boolean z, float f, UUID uuid, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? 0.0f : f, (i & 4) != 0 ? UUID.randomUUID() : uuid);
    }
}
