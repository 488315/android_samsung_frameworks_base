package com.samsung.android.graphics.spr.animation;

import android.graphics.drawable.Drawable;

import com.samsung.android.graphics.spr.document.SprDocument;

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

    public SprDrawableAnimation(byte type, Drawable drawable, SprDocument document) {
        if (drawable == null) {
            throw new RuntimeException("A drawable is not allocated.");
        }
        if (document == null) {
            throw new RuntimeException("A document is not allocated.");
        }
        this.mType = type;
        this.mDrawable = drawable;
        this.mDocument = document;
        this.mInterval =
                this.mDocument.mAnimationInterval >= 16 ? this.mDocument.mAnimationInterval : 16;
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

    public int getAnimationIndex() {
        return 0;
    }

    public void update() {}

    public boolean isRunning() {
        return this.mIsRunning;
    }
}
