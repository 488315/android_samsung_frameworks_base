package com.android.systemui.edgelighting.effect.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.systemui.edgelighting.effect.utils.Utils;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class DrawEdgeLayout extends FrameLayout {
    public Bitmap mBgBitmap;
    public int mHeight;
    public boolean mIsMultiResolutionSupoorted;
    public boolean mIsNoFrame;
    public Paint mMaskBgPaint;
    public Paint mMaskPaint;
    public final Path mMaskPath;
    public boolean mMaskingEdgeArea;
    public int mOrientation;
    public final Path mOutsideMaskPath;
    public float mStrokeWidth;
    public int mWidth;

    public DrawEdgeLayout(Context context) {
        super(context);
        this.mMaskPath = new Path();
        this.mOutsideMaskPath = new Path();
        Utils.getRadiusController();
        this.mStrokeWidth = 10.0f;
        this.mMaskingEdgeArea = true;
        this.mIsMultiResolutionSupoorted = false;
        this.mIsNoFrame = false;
        initializeScreen();
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0587  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x059d  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x058d  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void dispatchDraw(android.graphics.Canvas r18) {
        /*
            Method dump skipped, instructions count: 1646
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.edgelighting.effect.view.DrawEdgeLayout.dispatchDraw(android.graphics.Canvas):void");
    }

    public final void initializeScreen() {
        this.mWidth = com.android.systemui.edgelighting.utils.Utils.getScreenSize(getContext()).getWidth();
        this.mHeight = com.android.systemui.edgelighting.utils.Utils.getScreenSize(getContext()).getHeight();
        Utils.getRadiusController();
        Paint paint = new Paint(1);
        this.mMaskPaint = paint;
        paint.setColor(0);
        this.mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.mMaskPaint.setAntiAlias(true);
        Paint paint2 = new Paint(1);
        this.mMaskBgPaint = paint2;
        paint2.setAntiAlias(true);
        if (Build.VERSION.SEM_PLATFORM_INT < 100500 || Settings.System.getInt(getContext().getContentResolver(), SettingsHelper.INDEX_NEGATIVE_COLORS, 0) != 1) {
            this.mMaskBgPaint.setColor(-16777216);
        } else {
            this.mMaskBgPaint.setColor(-1);
        }
    }

    public final void makeSubScreenPath(Path path, float f, float f2) {
        float rotation = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getRotation();
        path.addRoundRect(0.0f, 0.0f, this.mWidth, this.mHeight, rotation == 0.0f ? new float[]{f, f, f, f, f2, f2, f2, f2} : rotation == 1.0f ? new float[]{f, f, f2, f2, f2, f2, f, f} : rotation == 2.0f ? new float[]{f2, f2, f2, f2, f, f, f, f} : new float[]{f2, f2, f, f, f, f, f2, f2}, Path.Direction.CW);
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mWidth = getWidth();
        this.mHeight = getHeight();
        if (this.mBgBitmap != null && this.mOrientation != getResources().getConfiguration().orientation) {
            Matrix matrix = new Matrix();
            if (this.mOrientation == 2) {
                matrix.postRotate(90.0f);
            } else {
                matrix.postRotate(-90.0f);
            }
            Bitmap bitmap = this.mBgBitmap;
            this.mBgBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.mBgBitmap.getHeight(), matrix, true);
            this.mOrientation = getResources().getConfiguration().orientation;
        }
        invalidate();
    }

    public final void makeSubScreenPath(Path path, float f, float f2, float f3) {
        float rotation = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getRotation();
        float cutoffHeight = Utils.getCutoffHeight(getContext());
        if (rotation == 0.0f) {
            path.moveTo(f3, 0.0f);
            float f4 = f - f3;
            path.lineTo(f4, 0.0f);
            path.quadTo(f, 0.0f, f, f3);
            float f5 = f2 - cutoffHeight;
            path.lineTo(f, f5 - f3);
            path.quadTo(f, f5, f4, f5);
            path.lineTo(f * 0.58f, f5);
            path.quadTo(f * 0.52f, f5, 0.485f * f, f2 - (cutoffHeight / 2.0f));
            path.quadTo(f * 0.45f, f2, f * 0.4f, f2);
            path.lineTo(f3, f2);
            path.quadTo(0.0f, f2, 0.0f, f2 - f3);
            path.lineTo(0.0f, f3);
            path.quadTo(0.0f, 0.0f, f3 + 0.0f, 0.0f);
            path.close();
            return;
        }
        if (rotation == 1.0f) {
            path.moveTo(f3, 0.0f);
            float f6 = f - cutoffHeight;
            path.lineTo(f6 - f3, 0.0f);
            path.quadTo(f6, 0.0f, f6, f3);
            path.lineTo(f6, f2 * 0.42f);
            path.quadTo(f6, f2 * 0.48f, f - (cutoffHeight / 2.0f), f2 * 0.515f);
            path.quadTo(f, f2 * 0.55f, f, f2 * 0.6f);
            float f7 = f2 - f3;
            path.lineTo(f, f7);
            path.quadTo(f, f2, f - f3, f2);
            path.lineTo(f3, f2);
            path.quadTo(0.0f, f2, 0.0f, f7);
            path.lineTo(0.0f, f3);
            path.quadTo(0.0f, 0.0f, f3, 0.0f);
            path.close();
            return;
        }
        if (rotation == 2.0f) {
            path.moveTo(f3, cutoffHeight);
            path.lineTo(f * 0.42f, cutoffHeight);
            path.quadTo(f * 0.48f, cutoffHeight, f * 0.515f, cutoffHeight / 2.0f);
            path.quadTo(f * 0.55f, 0.0f, f * 0.6f, 0.0f);
            float f8 = f - f3;
            path.lineTo(f8, 0.0f);
            path.quadTo(f, 0.0f, f, f3);
            float f9 = f2 - f3;
            path.lineTo(f, f9);
            path.quadTo(f, f2, f8, f2);
            path.lineTo(f3, f2);
            path.quadTo(0.0f, f2, 0.0f, f9);
            path.lineTo(0.0f, cutoffHeight + f3);
            path.quadTo(0.0f, cutoffHeight, f3, cutoffHeight);
            path.close();
            return;
        }
        path.moveTo(f3, 0.0f);
        float f10 = f - f3;
        path.lineTo(f10, 0.0f);
        path.quadTo(f, 0.0f, f, f3);
        float f11 = f2 - f3;
        path.lineTo(f, f11);
        path.quadTo(f, f2, f10, f2);
        path.lineTo(cutoffHeight + f3, f2);
        path.quadTo(cutoffHeight, f2, cutoffHeight, f11);
        path.lineTo(cutoffHeight, f2 * 0.58f);
        path.quadTo(cutoffHeight, f2 * 0.52f, cutoffHeight / 2.0f, f2 * 0.485f);
        path.quadTo(0.0f, f2 * 0.45f, 0.0f, f2 * 0.4f);
        path.lineTo(0.0f, f3);
        path.quadTo(0.0f, 0.0f, f3, 0.0f);
        path.close();
    }

    public DrawEdgeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaskPath = new Path();
        this.mOutsideMaskPath = new Path();
        Utils.getRadiusController();
        this.mStrokeWidth = 10.0f;
        this.mMaskingEdgeArea = true;
        this.mIsMultiResolutionSupoorted = false;
        this.mIsNoFrame = false;
        initializeScreen();
    }

    public DrawEdgeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMaskPath = new Path();
        this.mOutsideMaskPath = new Path();
        Utils.getRadiusController();
        this.mStrokeWidth = 10.0f;
        this.mMaskingEdgeArea = true;
        this.mIsMultiResolutionSupoorted = false;
        this.mIsNoFrame = false;
        initializeScreen();
    }

    public DrawEdgeLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMaskPath = new Path();
        this.mOutsideMaskPath = new Path();
        Utils.getRadiusController();
        this.mStrokeWidth = 10.0f;
        this.mMaskingEdgeArea = true;
        this.mIsMultiResolutionSupoorted = false;
        this.mIsNoFrame = false;
        initializeScreen();
    }
}
