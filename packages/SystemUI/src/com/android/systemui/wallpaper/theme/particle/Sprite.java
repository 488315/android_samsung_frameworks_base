package com.android.systemui.wallpaper.theme.particle;

import android.graphics.Bitmap;
import com.android.systemui.wallpaper.theme.SpriteModifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class Sprite {
    public int currentFrame;
    public int frameSize;
    public float height;
    public Bitmap mBitmap;
    public int mModifierCount;
    public final SpriteModifier[] mModifiers = new SpriteModifier[5];
    public float mScale;
    public float width;
    public final float x;
    public final float y;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SimpleModifier extends SpriteModifier {
        public int mCurrentFrameIndex;

        @Override // com.android.systemui.wallpaper.theme.SpriteModifier
        public final void onUpdate(Sprite sprite) {
            int i = this.mCurrentFrameIndex;
            sprite.currentFrame = i;
            int i2 = i + 1;
            this.mCurrentFrameIndex = i2;
            if (i2 == sprite.frameSize) {
                this.mCurrentFrameIndex = 0;
            }
        }
    }

    public Sprite(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }
}
