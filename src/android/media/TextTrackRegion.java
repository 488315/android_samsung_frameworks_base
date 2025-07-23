package android.media;

/* compiled from: WebVttRenderer.java */
/* loaded from: classes2.dex */
class TextTrackRegion {
    static final int SCROLL_VALUE_NONE = 300;
    static final int SCROLL_VALUE_SCROLL_UP = 301;
    String mId = "";
    float mWidth = 100.0f;
    int mLines = 3;
    float mViewportAnchorPointX = 0.0f;
    float mAnchorPointX = 0.0f;
    float mViewportAnchorPointY = 100.0f;
    float mAnchorPointY = 100.0f;
    int mScrollValue = 300;

    TextTrackRegion() {
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder(" {id:\"");
        sb.append(this.mId);
        sb.append("\", width:");
        sb.append(this.mWidth);
        sb.append(", lines:");
        sb.append(this.mLines);
        sb.append(", anchorPoint:(");
        sb.append(this.mAnchorPointX);
        sb.append(", ");
        sb.append(this.mAnchorPointY);
        sb.append("), viewportAnchorPoints:");
        sb.append(this.mViewportAnchorPointX);
        sb.append(", ");
        sb.append(this.mViewportAnchorPointY);
        sb.append("), scrollValue:");
        int i = this.mScrollValue;
        if (i == 300) {
            str = "none";
        } else if (i == 301) {
            str = "scroll_up";
        } else {
            str = "INVALID";
        }
        sb.append(str);
        sb.append("}");
        return sb.toString();
    }
}
