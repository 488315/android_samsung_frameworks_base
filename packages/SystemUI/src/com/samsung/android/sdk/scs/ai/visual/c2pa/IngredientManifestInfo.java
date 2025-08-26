package com.samsung.android.sdk.scs.ai.visual.c2pa;

import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class IngredientManifestInfo {
    private final boolean isParent;
    private final String manifestKey;

    public IngredientManifestInfo(String str, boolean z) {
        this.manifestKey = str;
        this.isParent = z;
    }

    public static /* synthetic */ IngredientManifestInfo copy$default(IngredientManifestInfo ingredientManifestInfo, String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = ingredientManifestInfo.manifestKey;
        }
        if ((i & 2) != 0) {
            z = ingredientManifestInfo.isParent;
        }
        return ingredientManifestInfo.copy(str, z);
    }

    public final String component1() {
        return this.manifestKey;
    }

    public final boolean component2() {
        return this.isParent;
    }

    public final IngredientManifestInfo copy(String str, boolean z) {
        return new IngredientManifestInfo(str, z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IngredientManifestInfo)) {
            return false;
        }
        IngredientManifestInfo ingredientManifestInfo = (IngredientManifestInfo) obj;
        return Intrinsics.areEqual(this.manifestKey, ingredientManifestInfo.manifestKey) && this.isParent == ingredientManifestInfo.isParent;
    }

    public final String getManifestKey() {
        return this.manifestKey;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = this.manifestKey.hashCode() * 31;
        boolean z = this.isParent;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return iHashCode + i;
    }

    public final boolean isParent() {
        return this.isParent;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("IngredientManifestInfo(manifestKey=");
        sb.append(this.manifestKey);
        sb.append(", isParent=");
        return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.isParent, ')');
    }
}
