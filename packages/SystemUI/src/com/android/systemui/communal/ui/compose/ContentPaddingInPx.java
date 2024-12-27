package com.android.systemui.communal.ui.compose;

public final class ContentPaddingInPx {
    public final float start;
    public final float top;

    public ContentPaddingInPx(float f, float f2) {
        this.start = f;
        this.top = f2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ContentPaddingInPx)) {
            return false;
        }
        ContentPaddingInPx contentPaddingInPx = (ContentPaddingInPx) obj;
        return Float.compare(this.start, contentPaddingInPx.start) == 0 && Float.compare(this.top, contentPaddingInPx.top) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.top) + (Float.hashCode(this.start) * 31);
    }

    public final String toString() {
        return "ContentPaddingInPx(start=" + this.start + ", top=" + this.top + ")";
    }
}
