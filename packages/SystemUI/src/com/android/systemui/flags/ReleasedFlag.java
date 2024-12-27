package com.android.systemui.flags;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ReleasedFlag extends BooleanFlag {
    public final String name;
    public final String namespace;
    public final boolean overridden;

    public /* synthetic */ ReleasedFlag(String str, String str2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? false : z);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ReleasedFlag)) {
            return false;
        }
        ReleasedFlag releasedFlag = (ReleasedFlag) obj;
        return Intrinsics.areEqual(this.name, releasedFlag.name) && Intrinsics.areEqual(this.namespace, releasedFlag.namespace) && this.overridden == releasedFlag.overridden;
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

    public final int hashCode() {
        return Boolean.hashCode(this.overridden) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.name.hashCode() * 31, 31, this.namespace);
    }

    public final String toString() {
        String str = this.name;
        String str2 = this.namespace;
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("ReleasedFlag(name=", str, ", namespace=", str2, ", overridden="), this.overridden, ")");
    }

    public ReleasedFlag(String str, String str2, boolean z) {
        super(str, str2, true, false, z);
        this.name = str;
        this.namespace = str2;
        this.overridden = z;
    }
}
