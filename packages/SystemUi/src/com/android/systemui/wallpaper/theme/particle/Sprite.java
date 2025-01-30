package com.android.systemui.wallpaper.theme.particle;

import android.graphics.Bitmap;
import com.android.systemui.wallpaper.theme.SpriteModifier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Sprite {
    public int currentFrame;
    public int frameSize;
    public float height;
    public Bitmap mBitmap;
    public int mModifierCount;
    public float mScale;
    public float width;

    /* renamed from: x */
    public final float f433x;

    /* renamed from: y */
    public final float f434y;
    public final boolean visible = true;
    public final SpriteModifier[] mModifiers = new SpriteModifier[5];

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SimpleModifier extends SpriteModifier {
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
        this.f433x = f;
        this.f434y = f2;
        this.width = f3;
        this.height = f4;
    }
}
