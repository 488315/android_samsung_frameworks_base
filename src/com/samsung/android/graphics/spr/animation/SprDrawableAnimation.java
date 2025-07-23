package com.samsung.android.graphics.spr.animation;

import android.graphics.drawable.Drawable;
import com.samsung.android.graphics.spr.document.SprDocument;

/* loaded from: classes6.dex */
public abstract class SprDrawableAnimation implements Runnable {
    private static final int DEFAULT_FRAME_DURATION = 16;
    public static final byte TYPE_FRAMEANIMATION = 2;
    public static final byte TYPE_NONE = 0;
    public static final byte TYPE_VALUEANIMATION = 1;
    protected final SprDocument mDocument;
    protected final Drawable mDrawable;
    protected final int mInterval;
    protected boolean mIsRunning = false;
    public final byte mType;

    public int getAnimationIndex() {
        return 0;
    }

    public void update() {
    }

    public SprDrawableAnimation(byte b, Drawable drawable, SprDocument sprDocument) {
        if (drawable == null) {
            throw new RuntimeException("A drawable is not allocated.");
        }
        if (sprDocument == null) {
            throw new RuntimeException("A document is not allocated.");
        }
        this.mType = b;
        this.mDrawable = drawable;
        this.mDocument = sprDocument;
        this.mInterval = sprDocument.mAnimationInterval >= 16 ? sprDocument.mAnimationInterval : 16;
    }

    public void start() {
        if (this.mIsRunning) {
            stop();
        }
        this.mIsRunning = true;
    }

    public void stop() {
        this.mDrawable.unscheduleSelf(this);
        this.mIsRunning = false;
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }
}
