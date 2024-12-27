package com.android.server.wm;

import android.graphics.Rect;

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
            if (this.mDexWindowingMode == dexPersistBoundsParams.mDexWindowingMode
                    && this.mDexStandAloneBounds.equals(dexPersistBoundsParams.mDexStandAloneBounds)
                    && this.mDexDualBounds.equals(dexPersistBoundsParams.mDexDualBounds)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((this.mDexDualBounds.hashCode() + (this.mDexStandAloneBounds.hashCode() * 31)) * 31)
                + this.mDexWindowingMode;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" dexDualBounds=" + this.mDexDualBounds);
        sb.append(" dexStandAloneBounds=" + this.mDexStandAloneBounds);
        sb.append(" dexWindowingMode=" + this.mDexWindowingMode);
        return sb.toString();
    }
}
