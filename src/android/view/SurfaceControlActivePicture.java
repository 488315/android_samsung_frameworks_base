package android.view;

import android.media.quality.PictureProfileHandle;

/* loaded from: classes4.dex */
public class SurfaceControlActivePicture {
    private final int mLayerId;
    private final int mOwnerUid;
    private final PictureProfileHandle mPictureProfileHandle;

    private SurfaceControlActivePicture(int i, int i2, PictureProfileHandle pictureProfileHandle) {
        this.mLayerId = i;
        this.mOwnerUid = i2;
        this.mPictureProfileHandle = pictureProfileHandle;
    }

    public int getLayerId() {
        return this.mLayerId;
    }

    public int getOwnerUid() {
        return this.mOwnerUid;
    }

    public PictureProfileHandle getPictureProfileHandle() {
        return this.mPictureProfileHandle;
    }
}
