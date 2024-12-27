package com.android.systemui.mediaprojection.appselector.view;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.shared.recents.utilities.PreviewPositionHelper;
import com.android.systemui.shared.recents.utilities.Utilities;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class MediaProjectionTaskView extends View {
    public final Paint backgroundPaint;
    public BitmapShader bitmapShader;
    public final int cornerRadius;
    public final Paint paint;
    public final PreviewPositionHelper previewPositionHelper;
    public final Rect previewRect;
    public RecentTask task;
    public ThumbnailData thumbnailData;
    public final WindowManager windowManager;

    public MediaProjectionTaskView(Context context) {
        this(context, null, 0, 6, null);
    }

    public final void bindTask(RecentTask recentTask, ThumbnailData thumbnailData) {
        Integer num;
        this.task = recentTask;
        this.thumbnailData = thumbnailData;
        int intValue = ((recentTask == null || (num = recentTask.colorBackground) == null) ? -16777216 : num.intValue()) | (-16777216);
        this.paint.setColor(intValue);
        this.backgroundPaint.setColor(intValue);
        ThumbnailData thumbnailData2 = this.thumbnailData;
        Bitmap bitmap = thumbnailData2 != null ? thumbnailData2.thumbnail : null;
        if (bitmap != null) {
            bitmap.prepareToDraw();
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            BitmapShader bitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
            this.bitmapShader = bitmapShader;
            this.paint.setShader(bitmapShader);
            updateThumbnailMatrix();
        } else {
            this.bitmapShader = null;
            this.paint.setShader(null);
        }
        invalidate();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int i = this.cornerRadius;
        canvas.drawRoundRect(0.0f, 1.0f, getWidth(), getHeight() - 1, i, i, this.backgroundPaint);
        if (this.task == null || this.bitmapShader == null || this.thumbnailData == null) {
            return;
        }
        float width = getWidth();
        float height = getHeight();
        int i2 = this.cornerRadius;
        canvas.drawRoundRect(0.0f, 0.0f, width, height, i2, i2, this.paint);
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        updateThumbnailMatrix();
        invalidate();
    }

    public final void updateThumbnailMatrix() {
        ThumbnailData thumbnailData;
        Bitmap bitmap;
        Display display;
        float f;
        float f2;
        boolean z;
        float height;
        float f3;
        boolean z2;
        boolean z3;
        float f4;
        float f5;
        this.previewPositionHelper.getClass();
        BitmapShader bitmapShader = this.bitmapShader;
        if (bitmapShader == null || (thumbnailData = this.thumbnailData) == null || (bitmap = thumbnailData.thumbnail) == null || (display = getContext().getDisplay()) == null) {
            return;
        }
        this.windowManager.getMaximumWindowMetrics();
        this.previewRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        int rotation = display.getRotation();
        boolean z4 = getLayoutDirection() == 1;
        boolean isLargeScreen = Utilities.isLargeScreen(getContext());
        PreviewPositionHelper previewPositionHelper = this.previewPositionHelper;
        Rect rect = this.previewRect;
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        previewPositionHelper.getClass();
        int i = thumbnailData.rotation - rotation;
        if (i < 0) {
            i += 4;
        }
        RectF rectF = new RectF();
        boolean z5 = thumbnailData.windowingMode == 1 && !isLargeScreen;
        boolean z6 = (i == 1 || i == 3) && z5;
        float f6 = thumbnailData.scale;
        if (measuredWidth == 0 || measuredHeight == 0 || f6 == 0.0f) {
            f = 0.0f;
            f2 = 0.0f;
            z = false;
        } else {
            boolean z7 = i > 0 && z5;
            float width = rect.width() / f6;
            float height2 = rect.height() / f6;
            float f7 = measuredWidth;
            float f8 = measuredHeight;
            float f9 = f7 / f8;
            float f10 = z7 ? height2 / width : width / height2;
            boolean z8 = Math.abs(f9 - f10) / Math.abs((f9 + f10) / 2.0f) > 0.1f;
            if (z7 && z8) {
                z7 = false;
                z2 = false;
            } else {
                z2 = z6;
            }
            if (z8) {
                Rect rect2 = thumbnailData.letterboxInsets;
                float f11 = rect2.left;
                rectF.left = f11;
                float f12 = rect2.right;
                rectF.right = f12;
                z3 = z7;
                float f13 = rect2.top;
                rectF.top = f13;
                float f14 = rect2.bottom;
                rectF.bottom = f14;
                f5 = width - (f11 + f12);
                f4 = height2 - (f13 + f14);
            } else {
                z3 = z7;
                f4 = height2;
                f5 = width;
            }
            if (z2) {
                f8 = f7;
                f7 = f8;
            }
            float f15 = f7 / f8;
            float f16 = f5 / f15;
            if (f16 > f4) {
                f16 = f4 < f8 ? Math.min(f8, height2) : f4;
                float f17 = f16 * f15;
                if (f17 > width) {
                    f16 = width / f15;
                } else {
                    width = f17;
                }
            } else {
                width = f5;
            }
            if (z4) {
                float f18 = (f5 - width) + rectF.left;
                rectF.left = f18;
                float f19 = rectF.right;
                f = 0.0f;
                if (f19 < 0.0f) {
                    rectF.left = f18 + f19;
                    rectF.right = 0.0f;
                }
            } else {
                f = 0.0f;
                float f20 = (f5 - width) + rectF.right;
                rectF.right = f20;
                float f21 = rectF.left;
                if (f21 < 0.0f) {
                    rectF.right = f20 + f21;
                    rectF.left = 0.0f;
                }
            }
            float f22 = (f4 - f16) + rectF.bottom;
            rectF.bottom = f22;
            float f23 = rectF.top;
            if (f23 < f) {
                rectF.bottom = f22 + f23;
                rectF.top = f;
            } else if (f22 < f) {
                rectF.top = f23 + f22;
                rectF.bottom = f;
            }
            f2 = f7 / (width * f6);
            z = z3;
        }
        if (z) {
            previewPositionHelper.mMatrix.setRotate(i * 90);
            if (i == 1) {
                height = rect.height();
                f3 = f;
            } else if (i == 2) {
                height = rect.width();
                f3 = rect.height();
            } else if (i != 3) {
                f3 = f;
                height = f3;
            } else {
                f3 = rect.width();
                height = f;
            }
            previewPositionHelper.mMatrix.postTranslate(height, f3);
        } else {
            previewPositionHelper.mMatrix.setTranslate((-rectF.left) * f6, (-rectF.top) * f6);
        }
        previewPositionHelper.mMatrix.postScale(f2, f2);
        bitmapShader.setLocalMatrix(this.previewPositionHelper.mMatrix);
        this.paint.setShader(bitmapShader);
    }

    public MediaProjectionTaskView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
    }

    public /* synthetic */ MediaProjectionTaskView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public MediaProjectionTaskView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.colorBackgroundFloating});
        int color = obtainStyledAttributes.getColor(0, -16777216);
        obtainStyledAttributes.recycle();
        Object systemService = context.getSystemService((Class<Object>) WindowManager.class);
        Intrinsics.checkNotNull(systemService);
        this.windowManager = (WindowManager) systemService;
        this.paint = new Paint(1);
        Paint paint = new Paint(1);
        paint.setColor(color);
        this.backgroundPaint = paint;
        this.cornerRadius = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.media_projection_app_selector_task_rounded_corners);
        this.previewPositionHelper = new PreviewPositionHelper();
        this.previewRect = new Rect();
    }
}
