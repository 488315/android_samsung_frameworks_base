package com.samsung.android.graphics.spr.animation;

import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.samsung.android.graphics.spr.document.SprDocument;

/* loaded from: classes6.dex */
public class SprDrawableAnimationFrame extends SprDrawableAnimation {
    private int mCurrentFrameIndex;
    private final int mFrameCount;
    private final int mTotalFrameCount;

    public SprDrawableAnimationFrame(Drawable drawable, SprDocument sprDocument) {
        super((byte) 2, drawable, sprDocument);
        this.mCurrentFrameIndex = 0;
        int frameAnimationCount = this.mDocument.getFrameAnimationCount();
        this.mFrameCount = frameAnimationCount;
        this.mTotalFrameCount = frameAnimationCount * this.mDocument.mRepeatCount;
    }

    @Override // com.samsung.android.graphics.spr.animation.SprDrawableAnimation
    public void start() {
        super.start();
        this.mCurrentFrameIndex = 0;
        this.mDrawable.scheduleSelf(this, SystemClock.uptimeMillis());
    }

    @Override // com.samsung.android.graphics.spr.animation.SprDrawableAnimation
    public int getAnimationIndex() {
        if (this.mDocument.mRepeatMode == 2) {
            return this.mCurrentFrameIndex % this.mFrameCount;
        }
        int i = this.mCurrentFrameIndex;
        int i2 = this.mFrameCount;
        int i3 = i % (i2 * 2);
        return i3 < i2 ? i3 : (i2 - (i3 % i2)) - 1;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.mCurrentFrameIndex++;
        if (this.mDocument.mRepeatCount == 0 || this.mCurrentFrameIndex < this.mTotalFrameCount) {
            this.mDrawable.scheduleSelf(this, SystemClock.uptimeMillis() + this.mInterval);
            if (this.mDocument.mRepeatCount == 0) {
                int i = this.mCurrentFrameIndex;
                int i2 = this.mFrameCount;
                if (i > i2 * 2) {
                    this.mCurrentFrameIndex = i - (i2 * 2);
                }
            }
        } else {
            this.mIsRunning = false;
        }
        this.mDrawable.invalidateSelf();
    }
}
