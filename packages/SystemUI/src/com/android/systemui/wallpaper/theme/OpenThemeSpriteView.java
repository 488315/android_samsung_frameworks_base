package com.android.systemui.wallpaper.theme;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import com.android.systemui.wallpaper.theme.particle.Sprite;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class OpenThemeSpriteView extends OpenThemeSurfaceView {
    public final String TAG;
    public Bitmap mBackgroundBitmap;
    public final ArrayList mSprites;

    public OpenThemeSpriteView(Context context) {
        super(context);
        this.TAG = "OpenThemeSurfaceView";
        this.mSprites = new ArrayList();
        this.mHolder.setFormat(1);
    }

    @Override // com.android.systemui.wallpaper.theme.OpenThemeSurfaceView
    public final void drawFrame(Canvas canvas) {
        canvas.drawBitmap(this.mBackgroundBitmap, 0.0f, 0.0f, (Paint) null);
        Iterator it = this.mSprites.iterator();
        while (it.hasNext()) {
            Sprite sprite = (Sprite) it.next();
            for (int i = 0; i < sprite.mModifierCount; i++) {
                sprite.mModifiers[i].onUpdate(sprite);
            }
            if (sprite.visible) {
                canvas.save();
                canvas.translate(sprite.x, sprite.y);
                float f = sprite.mScale;
                canvas.scale(f, f);
                canvas.clipRect(0.0f, 0.0f, sprite.width, sprite.height);
                canvas.drawBitmap(sprite.mBitmap, (-sprite.currentFrame) * sprite.width, 0.0f, (Paint) null);
                canvas.restore();
            }
        }
    }

    @Override // com.android.systemui.wallpaper.theme.OpenThemeSurfaceView, android.view.SurfaceView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Bitmap bitmap = this.mBackgroundBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mBackgroundBitmap = null;
        }
        Iterator it = this.mSprites.iterator();
        while (it.hasNext()) {
            Sprite sprite = (Sprite) it.next();
            Bitmap bitmap2 = sprite.mBitmap;
            if (bitmap2 != null) {
                bitmap2.recycle();
                sprite.mBitmap = null;
            }
        }
        Log.d(this.TAG, "ondetach2");
    }

    public OpenThemeSpriteView(Context context, Resources resources, int i) {
        super(context);
        this.TAG = "OpenThemeSurfaceView";
        this.mSprites = new ArrayList();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inDither = true;
        this.mBackgroundBitmap = BitmapFactory.decodeResource(resources, i, options);
        this.mHolder.setFormat(1);
        Log.d("OpenThemeSurfaceView", "bg: " + this.mBackgroundBitmap.getWidth() + ", " + this.mBackgroundBitmap.getHeight());
    }
}
