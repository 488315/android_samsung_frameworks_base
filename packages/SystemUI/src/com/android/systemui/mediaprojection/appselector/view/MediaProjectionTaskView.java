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
        int iIntValue = ((recentTask == null || (num = recentTask.colorBackground) == null) ? -16777216 : num.intValue()) | (-16777216);
        this.paint.setColor(iIntValue);
        this.backgroundPaint.setColor(iIntValue);
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
        float fHeight;
        float fHeight2;
        boolean z2;
        boolean z3;
        float f3;
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
        boolean zIsLargeScreen = Utilities.isLargeScreen(getContext());
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
        boolean z5 = thumbnailData.windowingMode == 1 && !zIsLargeScreen;
        boolean z6 = (i == 1 || i == 3) && z5;
        float f6 = thumbnailData.scale;
        if (measuredWidth == 0 || measuredHeight == 0 || f6 == 0.0f) {
            f = 0.0f;
            f2 = 0.0f;
            z = false;
        } else {
            boolean z7 = i > 0 && z5;
            float fWidth = rect.width() / f6;
            float fHeight3 = rect.height() / f6;
            float f7 = measuredWidth;
            float f8 = measuredHeight;
            float f9 = f7 / f8;
            float f10 = z7 ? fHeight3 / fWidth : fWidth / fHeight3;
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
                f3 = 0.0f;
                float f12 = rect2.right;
                rectF.right = f12;
                z3 = z4;
                float f13 = rect2.top;
                rectF.top = f13;
                float f14 = rect2.bottom;
                rectF.bottom = f14;
                f5 = fWidth - (f11 + f12);
                f4 = fHeight3 - (f13 + f14);
            } else {
                z3 = z4;
                f3 = 0.0f;
                f4 = fHeight3;
                f5 = fWidth;
            }
            if (z2) {
                f8 = f7;
                f7 = f8;
            }
            float f15 = f7 / f8;
            float fMin = f5 / f15;
            if (fMin > f4) {
                fMin = f4 < f8 ? Math.min(f8, fHeight3) : f4;
                float f16 = fMin * f15;
                if (f16 > fWidth) {
                    fMin = fWidth / f15;
                } else {
                    fWidth = f16;
                }
            } else {
                fWidth = f5;
            }
            if (z3) {
                float f17 = (f5 - fWidth) + rectF.left;
                rectF.left = f17;
                float f18 = rectF.right;
                if (f18 < f3) {
                    rectF.left = f17 + f18;
                    f = f3;
                    rectF.right = f;
                } else {
                    f = f3;
                }
            } else {
                f = f3;
                float f19 = (f5 - fWidth) + rectF.right;
                rectF.right = f19;
                float f20 = rectF.left;
                if (f20 < f) {
                    rectF.right = f19 + f20;
                    rectF.left = f;
                }
            }
            float f21 = (f4 - fMin) + rectF.bottom;
            rectF.bottom = f21;
            float f22 = rectF.top;
            if (f22 < f) {
                rectF.bottom = f21 + f22;
                rectF.top = f;
            } else if (f21 < f) {
                rectF.top = f22 + f21;
                rectF.bottom = f;
            }
            f2 = f7 / (fWidth * f6);
            z = z7;
        }
        if (z) {
            previewPositionHelper.mMatrix.setRotate(i * 90);
            if (i == 1) {
                fHeight = rect.height();
                fHeight2 = f;
            } else if (i == 2) {
                fHeight = rect.width();
                fHeight2 = rect.height();
            } else if (i != 3) {
                fHeight2 = f;
                fHeight = fHeight2;
            } else {
                fHeight2 = rect.width();
                fHeight = f;
            }
            previewPositionHelper.mMatrix.postTranslate(fHeight, fHeight2);
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
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.colorBackgroundFloating});
        int color = typedArrayObtainStyledAttributes.getColor(0, -16777216);
        typedArrayObtainStyledAttributes.recycle();
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
