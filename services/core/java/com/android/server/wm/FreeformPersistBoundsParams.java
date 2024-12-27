package com.android.server.wm;

import android.graphics.Rect;
import android.view.Surface;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class FreeformPersistBoundsParams {
    public int mRotation;
    public final Rect mFreeformBounds = new Rect();
    public final Rect mDisplayBounds = new Rect();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && FreeformPersistBoundsParams.class == obj.getClass()) {
            FreeformPersistBoundsParams freeformPersistBoundsParams =
                    (FreeformPersistBoundsParams) obj;
            if (this.mFreeformBounds.equals(freeformPersistBoundsParams.mFreeformBounds)
                    && this.mDisplayBounds.equals(freeformPersistBoundsParams.mDisplayBounds)
                    && this.mRotation == freeformPersistBoundsParams.mRotation) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((this.mDisplayBounds.hashCode() + (this.mFreeformBounds.hashCode() * 31)) * 31)
                + this.mRotation;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" freeformBounds=" + this.mFreeformBounds);
        sb.append(" displayBounds=" + this.mDisplayBounds);
        sb.append(" rotation=" + Surface.rotationToString(this.mRotation));
        return sb.toString();
    }
}
