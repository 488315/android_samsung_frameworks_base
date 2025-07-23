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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
        ArrayList arrayList = this.mSprites;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Sprite sprite = (Sprite) obj;
            for (int i2 = 0; i2 < sprite.mModifierCount; i2++) {
                sprite.mModifiers[i2].onUpdate(sprite);
            }
            canvas.save();
            canvas.translate(sprite.x, sprite.y);
            float f = sprite.mScale;
            canvas.scale(f, f);
            canvas.clipRect(0.0f, 0.0f, sprite.width, sprite.height);
            canvas.drawBitmap(sprite.mBitmap, (-sprite.currentFrame) * sprite.width, 0.0f, (Paint) null);
            canvas.restore();
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
        ArrayList arrayList = this.mSprites;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Sprite sprite = (Sprite) obj;
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
