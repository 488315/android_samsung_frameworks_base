package com.android.wm.shell.freeform;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.PathParser;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public class FreeformContainerIconLoader {
    public Path mAppIconFramePath;
    public int mAppIconFrameSize;
    public Path mAppIconPath;
    public int mAppIconSize;
    public final Context mContext;
    public int mFreeformContainerOuterSize;
    public float mFreeformContainerOuterSizeRadius;
    public int mIconFrameColor;
    public int mIconFrameShadowColor;
    public int mIconFrameShadowSize;
    public final PackageManager mPackageManager;
    public Path mPhotoIconFramePath;
    public int mPhotoIconFrameSize;
    public Path mPhotoIconPath;
    public int mPhotoIconRightBottomPaddingSize;
    public int mPhotoIconSize;

    public FreeformContainerIconLoader(Context context) throws Resources.NotFoundException {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        loadResources();
    }

    public static Bitmap clipPath(Bitmap bitmap, Path path) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.clipPath(path, Region.Op.DIFFERENCE);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        return bitmapCreateBitmap;
    }

    public final Bitmap createIconFrameBitmap(Path path, int i, int i2, boolean z) {
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i, config);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint(1);
        paint.setColor(i2);
        canvas.drawPath(path, paint);
        if (!z) {
            return bitmapCreateBitmap;
        }
        Bitmap bitmapCreateIconFrameShadowBitmap = createIconFrameShadowBitmap(path, i);
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(bitmapCreateIconFrameShadowBitmap.getWidth(), bitmapCreateIconFrameShadowBitmap.getHeight(), config);
        canvas.setBitmap(bitmapCreateBitmap2);
        canvas.drawBitmap(bitmapCreateIconFrameShadowBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.save();
        canvas.translate(bitmapCreateBitmap2.getWidth() / 2.0f, bitmapCreateBitmap2.getHeight() / 2.0f);
        canvas.drawBitmap(bitmapCreateBitmap, (-bitmapCreateBitmap.getWidth()) / 2.0f, (-bitmapCreateBitmap.getHeight()) / 2.0f, (Paint) null);
        canvas.restore();
        bitmapCreateBitmap.recycle();
        bitmapCreateIconFrameShadowBitmap.recycle();
        return bitmapCreateBitmap2;
    }

    public final Bitmap createIconFrameShadowBitmap(Path path, int i) {
        int i2 = (this.mIconFrameShadowSize * 2) + i;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint(1);
        paint.setColor(0);
        paint.setShadowLayer(this.mIconFrameShadowSize, 0.0f, 3.0f, this.mIconFrameShadowColor);
        float f = this.mIconFrameShadowSize;
        canvas.translate(f, f);
        canvas.drawPath(path, paint);
        return bitmapCreateBitmap;
    }

    public final Drawable getShowingIcon(Drawable drawable, Drawable drawable2) {
        if (drawable2 == null) {
            int i = this.mAppIconSize;
            Bitmap bitmapClipPath = clipPath(scale(drawable, i, i), this.mAppIconPath);
            Bitmap bitmapCreateIconFrameBitmap = createIconFrameBitmap(this.mAppIconFramePath, this.mAppIconFrameSize, this.mIconFrameColor, true);
            int i2 = this.mFreeformContainerOuterSize;
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            Paint paint = new Paint(1);
            canvas.save();
            float f = this.mFreeformContainerOuterSizeRadius;
            canvas.translate(f, f);
            canvas.drawBitmap(bitmapCreateIconFrameBitmap, (-bitmapCreateIconFrameBitmap.getWidth()) / 2.0f, (-bitmapCreateIconFrameBitmap.getHeight()) / 2.0f, paint);
            canvas.drawBitmap(bitmapClipPath, (-bitmapClipPath.getWidth()) / 2.0f, (-bitmapClipPath.getHeight()) / 2.0f, paint);
            canvas.restore();
            bitmapClipPath.recycle();
            bitmapCreateIconFrameBitmap.recycle();
            return new BitmapDrawable(this.mContext.getResources(), bitmapCreateBitmap);
        }
        int i3 = this.mAppIconSize;
        Bitmap bitmapScale = scale(drawable, i3, i3);
        int i4 = this.mPhotoIconSize;
        Bitmap bitmapScale2 = scale(drawable2, i4, i4);
        Bitmap bitmapClipPath2 = clipPath(bitmapScale, this.mAppIconPath);
        Bitmap bitmapCreateIconFrameBitmap2 = createIconFrameBitmap(this.mAppIconFramePath, this.mAppIconFrameSize, this.mIconFrameColor, true);
        Bitmap bitmapClipPath3 = clipPath(bitmapScale2, this.mPhotoIconPath);
        Bitmap bitmapCreateIconFrameBitmap3 = createIconFrameBitmap(this.mPhotoIconFramePath, this.mPhotoIconFrameSize, this.mIconFrameColor, false);
        Bitmap bitmapCreateIconFrameShadowBitmap = createIconFrameShadowBitmap(this.mPhotoIconFramePath, this.mPhotoIconFrameSize);
        int i5 = this.mFreeformContainerOuterSize;
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(i5, i5, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
        Paint paint2 = new Paint(1);
        canvas2.save();
        float f2 = this.mFreeformContainerOuterSizeRadius;
        canvas2.translate(f2, f2);
        float f3 = this.mAppIconFrameSize / 2.0f;
        canvas2.save();
        canvas2.translate((f3 - (bitmapCreateIconFrameBitmap3.getWidth() / 2.0f)) - this.mPhotoIconRightBottomPaddingSize, (f3 - (bitmapCreateIconFrameBitmap3.getHeight() / 2.0f)) - this.mPhotoIconRightBottomPaddingSize);
        canvas2.drawBitmap(bitmapCreateIconFrameShadowBitmap, (-bitmapCreateIconFrameShadowBitmap.getWidth()) / 2.0f, (-bitmapCreateIconFrameShadowBitmap.getHeight()) / 2.0f, paint2);
        canvas2.restore();
        canvas2.drawBitmap(bitmapCreateIconFrameBitmap2, (-bitmapCreateIconFrameBitmap2.getWidth()) / 2.0f, (-bitmapCreateIconFrameBitmap2.getHeight()) / 2.0f, paint2);
        canvas2.save();
        float f4 = f3 - this.mPhotoIconRightBottomPaddingSize;
        canvas2.translate(f4, f4);
        canvas2.drawBitmap(bitmapCreateIconFrameBitmap3, -bitmapCreateIconFrameBitmap3.getWidth(), -bitmapCreateIconFrameBitmap3.getHeight(), paint2);
        canvas2.restore();
        canvas2.drawBitmap(bitmapClipPath2, (-bitmapClipPath2.getWidth()) / 2.0f, (-bitmapClipPath2.getHeight()) / 2.0f, paint2);
        canvas2.save();
        canvas2.translate((bitmapClipPath2.getWidth() / 2.0f) - this.mPhotoIconRightBottomPaddingSize, (bitmapClipPath2.getHeight() / 2.0f) - this.mPhotoIconRightBottomPaddingSize);
        canvas2.drawBitmap(bitmapClipPath3, -bitmapClipPath3.getWidth(), -bitmapClipPath3.getHeight(), paint2);
        canvas2.restore();
        canvas2.restore();
        bitmapClipPath2.recycle();
        bitmapCreateIconFrameBitmap2.recycle();
        bitmapClipPath3.recycle();
        bitmapCreateIconFrameBitmap3.recycle();
        bitmapCreateIconFrameShadowBitmap.recycle();
        return new BitmapDrawable(this.mContext.getResources(), bitmapCreateBitmap2);
    }

    public final void loadResources() throws Resources.NotFoundException {
        Resources resources = this.mContext.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.freeform_container_folder_item_size);
        this.mFreeformContainerOuterSize = dimensionPixelSize;
        this.mFreeformContainerOuterSizeRadius = dimensionPixelSize / 2.0f;
        this.mAppIconFrameSize = resources.getDimensionPixelSize(R.dimen.freeform_container_app_icon_frame_size);
        this.mAppIconSize = resources.getDimensionPixelSize(R.dimen.freeform_container_app_icon_size);
        this.mIconFrameShadowSize = resources.getDimensionPixelSize(R.dimen.freeform_container_icon_frame_shadow_size);
        this.mIconFrameColor = resources.getColor(R.color.freeform_container_icon_frame_color);
        this.mIconFrameShadowColor = resources.getColor(R.color.freeform_container_icon_frame_shadow_color);
        Path pathCreatePathFromPathData = PathParser.createPathFromPathData(resources.getString(android.R.string.eventTypeCustom));
        this.mAppIconPath = new Path(pathCreatePathFromPathData);
        Matrix matrix = new Matrix();
        int i = this.mAppIconSize;
        matrix.setScale(i / 100.0f, i / 100.0f);
        this.mAppIconPath.transform(matrix);
        this.mAppIconFramePath = new Path(pathCreatePathFromPathData);
        Matrix matrix2 = new Matrix();
        int i2 = this.mAppIconFrameSize;
        matrix2.setScale(i2 / 100.0f, i2 / 100.0f);
        this.mAppIconFramePath.transform(matrix2);
        this.mPhotoIconSize = resources.getDimensionPixelSize(R.dimen.freeform_container_photo_icon_size);
        this.mPhotoIconFrameSize = resources.getDimensionPixelSize(R.dimen.freeform_container_photo_icon_frame_size);
        this.mPhotoIconRightBottomPaddingSize = resources.getDimensionPixelSize(R.dimen.freeform_container_photo_icon_right_bottom_padding);
        Path path = new Path();
        this.mPhotoIconPath = path;
        float f = this.mPhotoIconSize / 2.0f;
        Path.Direction direction = Path.Direction.CW;
        path.addCircle(f, f, f, direction);
        Path path2 = new Path();
        this.mPhotoIconFramePath = path2;
        float f2 = this.mPhotoIconFrameSize / 2.0f;
        path2.addCircle(f2, f2, f2, direction);
    }

    public final Bitmap scale(Drawable drawable, int i, int i2) {
        int i3 = this.mContext.getResources().getConfiguration().densityDpi;
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(((BitmapDrawable) drawable).getBitmap(), i, i2, true);
            if (i3 > 0 && i3 != bitmapCreateScaledBitmap.getDensity()) {
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "[IconLoader] change bitmap densityDpi=", ", old=");
                sbM.append(bitmapCreateScaledBitmap.getDensity());
                Log.i("FreeformContainer", sbM.toString());
                bitmapCreateScaledBitmap.setDensity(i3);
            }
            return bitmapCreateScaledBitmap;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        if (drawable == null) {
            Log.w("FreeformContainer", "[IconLoader] drawable is null");
            return bitmapCreateBitmap;
        }
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }
}
