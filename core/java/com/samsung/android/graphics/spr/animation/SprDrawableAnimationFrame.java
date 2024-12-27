package com.samsung.android.graphics.spr.animation;

import android.graphics.drawable.Drawable;
import android.os.SystemClock;

import com.samsung.android.graphics.spr.document.SprDocument;

public class SprDrawableAnimationFrame extends SprDrawableAnimation {
    private int mCurrentFrameIndex;
    private final int mFrameCount;
    private final int mTotalFrameCount;

    public SprDrawableAnimationFrame(Drawable drawable, SprDocument document) {
        super((byte) 2, drawable, document);
        this.mCurrentFrameIndex = 0;
        this.mFrameCount = this.mDocument.getFrameAnimationCount();
        this.mTotalFrameCount = this.mFrameCount * this.mDocument.mRepeatCount;
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
            int result = this.mCurrentFrameIndex % this.mFrameCount;
            return result;
        }
        int result2 = this.mCurrentFrameIndex;
        int index = result2 % (this.mFrameCount * 2);
        int result3 =
                index < this.mFrameCount
                        ? index
                        : (this.mFrameCount - (index % this.mFrameCount)) - 1;
        return result3;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.mCurrentFrameIndex++;
        if (this.mDocument.mRepeatCount == 0 || this.mCurrentFrameIndex < this.mTotalFrameCount) {
            this.mDrawable.scheduleSelf(this, SystemClock.uptimeMillis() + this.mInterval);
            if (this.mDocument.mRepeatCount == 0
                    && this.mCurrentFrameIndex > this.mFrameCount * 2) {
                this.mCurrentFrameIndex -= this.mFrameCount * 2;
            }
        } else {
            this.mIsRunning = false;
        }
        this.mDrawable.invalidateSelf();
    }
}
