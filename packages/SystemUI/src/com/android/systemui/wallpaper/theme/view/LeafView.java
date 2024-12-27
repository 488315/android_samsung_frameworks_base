package com.android.systemui.wallpaper.theme.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.theme.DensityUtil;
import com.android.systemui.wallpaper.theme.LockscreenCallback;
import com.android.systemui.wallpaper.theme.particle.Leaf;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class LeafView extends View implements LockscreenCallback {
    public final Context mContext;
    public final int[] mLeafImageId;
    public final Leaf[] mLeafPool;
    public final float[] mLeafSizeScale;
    public final Paint mPaint;
    public final Matrix matrix;
    public boolean refresh;

    public LeafView(Context context) {
        super(context);
        this.mPaint = new Paint();
        this.refresh = true;
        this.mLeafImageId = new int[]{R.drawable.leaf_01, R.drawable.leaf_02, R.drawable.leaf_03, R.drawable.leaf_04};
        this.mLeafSizeScale = new float[]{0.6f, 0.7f, 0.8f, 0.9f, 1.2f, 1.3f};
        this.matrix = new Matrix();
        this.mContext = context;
        this.mLeafPool = new Leaf[35];
        int i = 0;
        while (true) {
            Leaf[] leafArr = this.mLeafPool;
            if (i >= leafArr.length) {
                return;
            }
            leafArr[i] = new Leaf(context);
            i++;
        }
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.refresh) {
            boolean z = WallpaperUtils.mIsExternalLiveWallpaper;
        }
        for (Leaf leaf : this.mLeafPool) {
            this.matrix.setRotate(leaf.rotate);
            Matrix matrix = this.matrix;
            float f = this.mLeafSizeScale[leaf.leafSize];
            matrix.setScale(f, f);
            Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(this.mLeafImageId[leaf.leafKind])).getBitmap();
            this.matrix.postTranslate(DensityUtil.dip2px(this.mContext, leaf.cx), DensityUtil.dip2px(this.mContext, leaf.cy));
            canvas.drawBitmap(bitmap, this.matrix, this.mPaint);
            float f2 = leaf.cx + leaf.mXSpeed;
            leaf.cx = f2;
            float f3 = leaf.cy + leaf.mYSpeed;
            leaf.cy = f3;
            leaf.rotate += 2;
            if (f2 <= 0.0f || f2 >= DensityUtil.sMetricsWidth || f3 >= DensityUtil.sMetricsHeight) {
                leaf.cx = leaf.mRandom.nextInt(DensityUtil.sMetricsWidth - 32) + 16;
                leaf.cy = 0.0f;
            }
            if (leaf.rotate >= 360) {
                leaf.rotate = 0;
            }
        }
    }

    @Override // com.android.systemui.wallpaper.theme.LockscreenCallback
    public final void screenTurnedOff() {
        this.refresh = false;
    }

    @Override // com.android.systemui.wallpaper.theme.LockscreenCallback
    public final void screenTurnedOn() {
        this.refresh = true;
        invalidate();
    }
}
