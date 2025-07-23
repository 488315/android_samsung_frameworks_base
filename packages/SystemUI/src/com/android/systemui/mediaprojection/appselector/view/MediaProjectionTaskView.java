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
import com.android.systemui.utils.windowmanager.WindowManagerUtils;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
        float f6;
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
        float f7 = thumbnailData.scale;
        if (measuredWidth == 0 || measuredHeight == 0 || f7 == 0.0f) {
            f = 0.0f;
            f2 = 0.0f;
            z = false;
        } else {
            boolean z7 = i > 0 && z5;
            float width = rect.width() / f7;
            float height2 = rect.height() / f7;
            float f8 = measuredWidth;
            float f9 = measuredHeight;
            float f10 = f8 / f9;
            float f11 = z7 ? height2 / width : width / height2;
            boolean z8 = Math.abs(f10 - f11) / Math.abs((f10 + f11) / 2.0f) > 0.1f;
            if (z7 && z8) {
                z7 = false;
                z2 = false;
            } else {
                z2 = z6;
            }
            if (z8) {
                Rect rect2 = thumbnailData.letterboxInsets;
                float f12 = rect2.left;
                rectF.left = f12;
                f4 = 0.0f;
                float f13 = rect2.right;
                rectF.right = f13;
                z3 = z4;
                float f14 = rect2.top;
                rectF.top = f14;
                float f15 = rect2.bottom;
                rectF.bottom = f15;
                f6 = width - (f12 + f13);
                f5 = height2 - (f14 + f15);
            } else {
                z3 = z4;
                f4 = 0.0f;
                f5 = height2;
                f6 = width;
            }
            if (z2) {
                f9 = f8;
                f8 = f9;
            }
            float f16 = f8 / f9;
            float f17 = f6 / f16;
            if (f17 > f5) {
                f17 = f5 < f9 ? Math.min(f9, height2) : f5;
                float f18 = f17 * f16;
                if (f18 > width) {
                    f17 = width / f16;
                } else {
                    width = f18;
                }
            } else {
                width = f6;
            }
            if (z3) {
                float f19 = (f6 - width) + rectF.left;
                rectF.left = f19;
                float f20 = rectF.right;
                if (f20 < f4) {
                    rectF.left = f19 + f20;
                    f = f4;
                    rectF.right = f;
                } else {
                    f = f4;
                }
            } else {
                f = f4;
                float f21 = (f6 - width) + rectF.right;
                rectF.right = f21;
                float f22 = rectF.left;
                if (f22 < f) {
                    rectF.right = f21 + f22;
                    rectF.left = f;
                }
            }
            float f23 = (f5 - f17) + rectF.bottom;
            rectF.bottom = f23;
            float f24 = rectF.top;
            if (f24 < f) {
                rectF.bottom = f23 + f24;
                rectF.top = f;
            } else if (f23 < f) {
                rectF.top = f24 + f23;
                rectF.bottom = f;
            }
            f2 = f8 / (width * f7);
            z = z7;
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
            previewPositionHelper.mMatrix.setTranslate((-rectF.left) * f7, (-rectF.top) * f7);
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
        this.windowManager = WindowManagerUtils.getWindowManager(context);
        this.paint = new Paint(1);
        Paint paint = new Paint(1);
        paint.setColor(color);
        this.backgroundPaint = paint;
        this.cornerRadius = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.media_projection_app_selector_task_rounded_corners);
        this.previewPositionHelper = new PreviewPositionHelper();
        this.previewRect = new Rect();
    }
}
