package com.android.server.wm;

import android.graphics.Rect;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DexPersistBoundsParams {
    public final Rect mDexDualBounds = new Rect();
    public final Rect mDexStandAloneBounds = new Rect();
    public int mDexWindowingMode;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && DexPersistBoundsParams.class == obj.getClass()) {
            DexPersistBoundsParams dexPersistBoundsParams = (DexPersistBoundsParams) obj;
            if (this.mDexWindowingMode == dexPersistBoundsParams.mDexWindowingMode && this.mDexStandAloneBounds.equals(dexPersistBoundsParams.mDexStandAloneBounds) && this.mDexDualBounds.equals(dexPersistBoundsParams.mDexDualBounds)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((this.mDexDualBounds.hashCode() + (this.mDexStandAloneBounds.hashCode() * 31)) * 31) + this.mDexWindowingMode;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" dexDualBounds=" + this.mDexDualBounds);
        sb.append(" dexStandAloneBounds=" + this.mDexStandAloneBounds);
        sb.append(" dexWindowingMode=" + this.mDexWindowingMode);
        return sb.toString();
    }
}
