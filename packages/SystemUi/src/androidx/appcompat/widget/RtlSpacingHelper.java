package androidx.appcompat.widget;

import com.samsung.android.nexus.video.VideoPlayer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RtlSpacingHelper {
    public int mLeft = 0;
    public int mRight = 0;
    public int mStart = VideoPlayer.MEDIA_ERROR_SYSTEM;
    public int mEnd = VideoPlayer.MEDIA_ERROR_SYSTEM;
    public int mExplicitLeft = 0;
    public int mExplicitRight = 0;
    public boolean mIsRtl = false;
    public boolean mIsRelative = false;

    public final void setRelative(int i, int i2) {
        this.mStart = i;
        this.mEnd = i2;
        this.mIsRelative = true;
        if (this.mIsRtl) {
            if (i2 != Integer.MIN_VALUE) {
                this.mLeft = i2;
            }
            if (i != Integer.MIN_VALUE) {
                this.mRight = i;
                return;
            }
            return;
        }
        if (i != Integer.MIN_VALUE) {
            this.mLeft = i;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.mRight = i2;
        }
    }
}
