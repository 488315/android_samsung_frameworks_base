package com.android.systemui.wallpaper.theme.particle;

import android.graphics.Bitmap;
import com.android.systemui.wallpaper.theme.SpriteModifier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class Sprite {
    public int currentFrame;
    public int frameSize;
    public float height;
    public Bitmap mBitmap;
    public int mModifierCount;
    public float mScale;
    public float width;
    public final float x;
    public final float y;
    public final boolean visible = true;
    public final SpriteModifier[] mModifiers = new SpriteModifier[5];

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }
}
