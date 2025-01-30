package com.samsung.android.graphics.spr.animation;

import android.graphics.drawable.Drawable;
import android.p009os.SystemClock;
import com.samsung.android.graphics.spr.document.SprDocument;

/* loaded from: classes5.dex */
public class SprDrawableAnimationFrame extends SprDrawableAnimation {
  private int mCurrentFrameIndex;
  private final int mFrameCount;
  private final int mTotalFrameCount;

  public SprDrawableAnimationFrame(Drawable drawable, SprDocument document) {
    super((byte) 2, drawable, document);
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
      int result = this.mCurrentFrameIndex % this.mFrameCount;
      return result;
    }
    int result2 = this.mCurrentFrameIndex;
    int i = this.mFrameCount;
    int index = result2 % (i * 2);
    int result3 = index < i ? index : (i - (index % i)) - 1;
    return result3;
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
