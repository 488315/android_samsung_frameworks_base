package com.android.p038wm.shell.freeform;

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
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.PathParser;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FreeformContainerIconLoader {
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

    public FreeformContainerIconLoader(Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
        loadResources();
    }

    public static Bitmap clipPath(Bitmap bitmap, Path path) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        canvas.clipPath(path, Region.Op.DIFFERENCE);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        return createBitmap;
    }

    public final Bitmap createIconFrameBitmap(Path path, int i, int i2, boolean z) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setColor(i2);
        canvas.drawPath(path, paint);
        if (!z) {
            return createBitmap;
        }
        Bitmap createIconFrameShadowBitmap = createIconFrameShadowBitmap(path, i);
        Bitmap createBitmap2 = Bitmap.createBitmap(createIconFrameShadowBitmap.getWidth(), createIconFrameShadowBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(createBitmap2);
        canvas.drawBitmap(createIconFrameShadowBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.save();
        canvas.translate(createBitmap2.getWidth() / 2.0f, createBitmap2.getHeight() / 2.0f);
        canvas.drawBitmap(createBitmap, (-createBitmap.getWidth()) / 2.0f, (-createBitmap.getHeight()) / 2.0f, (Paint) null);
        canvas.restore();
        createBitmap.recycle();
        createIconFrameShadowBitmap.recycle();
        return createBitmap2;
    }

    public final Bitmap createIconFrameShadowBitmap(Path path, int i) {
        int i2 = (this.mIconFrameShadowSize * 2) + i;
        Bitmap createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setColor(0);
        paint.setShadowLayer(this.mIconFrameShadowSize, 0.0f, 3.0f, this.mIconFrameShadowColor);
        float f = this.mIconFrameShadowSize;
        canvas.translate(f, f);
        canvas.drawPath(path, paint);
        return createBitmap;
    }

    public final Drawable getShowingIcon(Drawable drawable, Drawable drawable2) {
        Context context = this.mContext;
        if (drawable2 == null) {
            int i = this.mAppIconSize;
            Bitmap clipPath = clipPath(scale(drawable, i, i), this.mAppIconPath);
            Bitmap createIconFrameBitmap = createIconFrameBitmap(this.mAppIconFramePath, this.mAppIconFrameSize, this.mIconFrameColor, true);
            int i2 = this.mFreeformContainerOuterSize;
            Bitmap createBitmap = Bitmap.createBitmap(i2, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint(1);
            canvas.save();
            float f = this.mFreeformContainerOuterSizeRadius;
            canvas.translate(f, f);
            canvas.drawBitmap(createIconFrameBitmap, (-createIconFrameBitmap.getWidth()) / 2.0f, (-createIconFrameBitmap.getHeight()) / 2.0f, paint);
            canvas.drawBitmap(clipPath, (-clipPath.getWidth()) / 2.0f, (-clipPath.getHeight()) / 2.0f, paint);
            canvas.restore();
            clipPath.recycle();
            createIconFrameBitmap.recycle();
            return new BitmapDrawable(context.getResources(), createBitmap);
        }
        int i3 = this.mAppIconSize;
        Bitmap scale = scale(drawable, i3, i3);
        int i4 = this.mPhotoIconSize;
        Bitmap scale2 = scale(drawable2, i4, i4);
        Bitmap clipPath2 = clipPath(scale, this.mAppIconPath);
        Bitmap createIconFrameBitmap2 = createIconFrameBitmap(this.mAppIconFramePath, this.mAppIconFrameSize, this.mIconFrameColor, true);
        Bitmap clipPath3 = clipPath(scale2, this.mPhotoIconPath);
        Bitmap createIconFrameBitmap3 = createIconFrameBitmap(this.mPhotoIconFramePath, this.mPhotoIconFrameSize, this.mIconFrameColor, false);
        Bitmap createIconFrameShadowBitmap = createIconFrameShadowBitmap(this.mPhotoIconFramePath, this.mPhotoIconFrameSize);
        int i5 = this.mFreeformContainerOuterSize;
        Bitmap createBitmap2 = Bitmap.createBitmap(i5, i5, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(createBitmap2);
        Paint paint2 = new Paint(1);
        canvas2.save();
        float f2 = this.mFreeformContainerOuterSizeRadius;
        canvas2.translate(f2, f2);
        float f3 = this.mAppIconFrameSize / 2.0f;
        canvas2.save();
        canvas2.translate((f3 - (createIconFrameBitmap3.getWidth() / 2.0f)) - this.mPhotoIconRightBottomPaddingSize, (f3 - (createIconFrameBitmap3.getHeight() / 2.0f)) - this.mPhotoIconRightBottomPaddingSize);
        canvas2.drawBitmap(createIconFrameShadowBitmap, (-createIconFrameShadowBitmap.getWidth()) / 2.0f, (-createIconFrameShadowBitmap.getHeight()) / 2.0f, paint2);
        canvas2.restore();
        canvas2.drawBitmap(createIconFrameBitmap2, (-createIconFrameBitmap2.getWidth()) / 2.0f, (-createIconFrameBitmap2.getHeight()) / 2.0f, paint2);
        canvas2.save();
        float f4 = f3 - this.mPhotoIconRightBottomPaddingSize;
        canvas2.translate(f4, f4);
        canvas2.drawBitmap(createIconFrameBitmap3, -createIconFrameBitmap3.getWidth(), -createIconFrameBitmap3.getHeight(), paint2);
        canvas2.restore();
        canvas2.drawBitmap(clipPath2, (-clipPath2.getWidth()) / 2.0f, (-clipPath2.getHeight()) / 2.0f, paint2);
        canvas2.save();
        canvas2.translate((clipPath2.getWidth() / 2.0f) - this.mPhotoIconRightBottomPaddingSize, (clipPath2.getHeight() / 2.0f) - this.mPhotoIconRightBottomPaddingSize);
        canvas2.drawBitmap(clipPath3, -clipPath3.getWidth(), -clipPath3.getHeight(), paint2);
        canvas2.restore();
        canvas2.restore();
        clipPath2.recycle();
        createIconFrameBitmap2.recycle();
        clipPath3.recycle();
        createIconFrameBitmap3.recycle();
        createIconFrameShadowBitmap.recycle();
        return new BitmapDrawable(context.getResources(), createBitmap2);
    }

    public final void loadResources() {
        Resources resources = this.mContext.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.freeform_container_folder_item_size);
        this.mFreeformContainerOuterSize = dimensionPixelSize;
        this.mFreeformContainerOuterSizeRadius = dimensionPixelSize / 2.0f;
        this.mAppIconFrameSize = resources.getDimensionPixelSize(R.dimen.freeform_container_app_icon_frame_size);
        this.mAppIconSize = resources.getDimensionPixelSize(R.dimen.freeform_container_app_icon_size);
        this.mIconFrameShadowSize = resources.getDimensionPixelSize(R.dimen.freeform_container_icon_frame_shadow_size);
        this.mIconFrameColor = resources.getColor(R.color.freeform_container_icon_frame_color);
        this.mIconFrameShadowColor = resources.getColor(R.color.freeform_container_icon_frame_shadow_color);
        Path createPathFromPathData = PathParser.createPathFromPathData(resources.getString(android.R.string.dump_heap_ready_text));
        this.mAppIconPath = new Path(createPathFromPathData);
        Matrix matrix = new Matrix();
        int i = this.mAppIconSize;
        matrix.setScale(i / 100.0f, i / 100.0f);
        this.mAppIconPath.transform(matrix);
        this.mAppIconFramePath = new Path(createPathFromPathData);
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
        path.addCircle(f, f, f, Path.Direction.CW);
        Path path2 = new Path();
        this.mPhotoIconFramePath = path2;
        float f2 = this.mPhotoIconFrameSize / 2.0f;
        path2.addCircle(f2, f2, f2, Path.Direction.CW);
    }

    public final Bitmap scale(Drawable drawable, int i, int i2) {
        int i3 = this.mContext.getResources().getConfiguration().densityDpi;
        if (!(drawable instanceof BitmapDrawable)) {
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            if (drawable != null) {
                Canvas canvas = new Canvas(createBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            } else {
                Log.w("FreeformContainer", "[IconLoader] drawable is null");
            }
            return createBitmap;
        }
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(((BitmapDrawable) drawable).getBitmap(), i, i2, true);
        if (i3 > 0 && i3 != createScaledBitmap.getDensity()) {
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("[IconLoader] change bitmap densityDpi=", i3, ", old=");
            m1m.append(createScaledBitmap.getDensity());
            Log.i("FreeformContainer", m1m.toString());
            createScaledBitmap.setDensity(i3);
        }
        return createScaledBitmap;
    }
}
