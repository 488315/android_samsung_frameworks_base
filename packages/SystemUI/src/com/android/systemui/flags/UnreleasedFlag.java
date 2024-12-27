package com.android.systemui.flags;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class UnreleasedFlag extends BooleanFlag {
    public final String name;
    public final String namespace;
    public final boolean overridden;
    public final boolean teamfood;

    public /* synthetic */ UnreleasedFlag(String str, String str2, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UnreleasedFlag)) {
            return false;
        }
        UnreleasedFlag unreleasedFlag = (UnreleasedFlag) obj;
        return Intrinsics.areEqual(this.name, unreleasedFlag.name) && Intrinsics.areEqual(this.namespace, unreleasedFlag.namespace) && this.teamfood == unreleasedFlag.teamfood && this.overridden == unreleasedFlag.overridden;
    }

    @Override // com.android.systemui.flags.BooleanFlag, com.android.systemui.flags.Flag
    public final String getName() {
        return this.name;
    }

    @Override // com.android.systemui.flags.BooleanFlag, com.android.systemui.flags.Flag
    public final String getNamespace() {
        return this.namespace;
    }

    @Override // com.android.systemui.flags.BooleanFlag
    public final boolean getOverridden() {
        return this.overridden;
    }

    @Override // com.android.systemui.flags.BooleanFlag
    public final boolean getTeamfood() {
        return this.teamfood;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.overridden) + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.name.hashCode() * 31, 31, this.namespace), 31, this.teamfood);
    }

    public final String toString() {
        String str = this.name;
        String str2 = this.namespace;
        boolean z = this.teamfood;
        boolean z2 = this.overridden;
        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("UnreleasedFlag(name=", str, ", namespace=", str2, ", teamfood=");
        m.append(z);
        m.append(", overridden=");
        m.append(z2);
        m.append(")");
        return m.toString();
    }

    public UnreleasedFlag(String str, String str2, boolean z, boolean z2) {
        super(str, str2, false, z, z2);
        this.name = str;
        this.namespace = str2;
        this.teamfood = z;
        this.overridden = z2;
    }
}
