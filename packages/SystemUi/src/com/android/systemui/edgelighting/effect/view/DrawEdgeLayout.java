package com.android.systemui.edgelighting.effect.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.systemui.edgelighting.effect.Feature;
import com.android.systemui.edgelighting.effect.utils.Utils;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* JADX WARN: Removed duplicated region for block: B:41:0x04d9  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x04ef  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x04df  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dispatchDraw(Canvas canvas) {
        int dimensionPixelSize;
        float f;
        float f2;
        float f3;
        if (!this.mMaskingEdgeArea || this.mIsNoFrame) {
            super.dispatchDraw(canvas);
            return;
        }
        if (this.mWidth == 0 || this.mHeight == 0) {
            return;
        }
        int save = canvas.save();
        this.mMaskPath.reset();
        this.mOutsideMaskPath.reset();
        int i = this.mWidth;
        int i2 = this.mHeight;
        if (i > i2) {
            int i3 = i2 / 2;
        } else {
            int i4 = i / 2;
        }
        canvas.saveLayerAlpha(new RectF(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight()), 255);
        super.dispatchDraw(canvas);
        this.mMaskPaint.setStyle(Paint.Style.STROKE);
        this.mMaskPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        this.mOutsideMaskPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        Context context = getContext();
        boolean z = this.mIsMultiResolutionSupoorted;
        String str = Utils.MODEL_NAME;
        Resources resources = context.getResources();
        String str2 = Utils.MODEL_NAME_FOR_JPN;
        if (!"".equals(str2)) {
            Utils.MODEL_NAME = str2;
        }
        if (Utils.MODEL_NAME.contains("SM-S928")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_s928);
        } else if (Utils.MODEL_NAME.contains("SM-S926")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_s926);
        } else if (Utils.MODEL_NAME.contains("SM-S921")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_s921);
        } else if (Utils.MODEL_NAME.contains("SM-S918")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_s918);
        } else if (Utils.MODEL_NAME.contains("SM-S916")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_s916);
        } else if (Utils.MODEL_NAME.contains("SM-S911")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_s911);
        } else if (Utils.MODEL_NAME.contains("SM-S908")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_s908);
        } else if (Utils.MODEL_NAME.contains("SM-S906")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_s906);
        } else if (Utils.MODEL_NAME.contains("SM-S901")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_s901);
        } else if (Utils.MODEL_NAME.contains("SM-N980")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_n980);
        } else if (Utils.MODEL_NAME.contains("SM-N981")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_n981);
        } else if (Utils.MODEL_NAME.contains("SM-N98")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_n98x);
        } else if (Utils.MODEL_NAME.contains("SM-N97")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_n97x);
        } else if (Utils.MODEL_NAME.contains("SM-N77")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_n77x);
        } else if (Utils.MODEL_NAME.contains("SM-G981")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_g981);
        } else if (Utils.MODEL_NAME.contains("SM-G988")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_g988);
        } else if (Utils.MODEL_NAME.contains("SM-G98")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_g98x);
        } else if (Utils.MODEL_NAME.contains("SM-G97")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_g97x);
        } else if (Utils.MODEL_NAME.contains("SM-G715")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_g715);
        } else if (Utils.MODEL_NAME.contains("SM-G77")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_g77x);
        } else if (Utils.MODEL_NAME.contains("SM-G78")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_g78x);
        } else if (Utils.MODEL_NAME.contains("SM-F74") || Utils.MODEL_NAME.contains("SM-F73") || Utils.MODEL_NAME.contains("SM-F72") || Utils.MODEL_NAME.contains("SM-W7023") || Utils.MODEL_NAME.contains("SM-W7024")) {
            dimensionPixelSize = resources.getDimensionPixelSize(Utils.isFolded() ? R.dimen.sm_f73x_folded : R.dimen.sm_f72x);
        } else if (Utils.MODEL_NAME.contains("SM-F71")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_f71x);
        } else if (Utils.MODEL_NAME.contains("SM-F7")) {
            dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sm_f7xx);
        } else if (Utils.MODEL_NAME.contains("SM-F95")) {
            dimensionPixelSize = resources.getDimensionPixelSize(Utils.isFolded() ? R.dimen.sm_f95x_folded : R.dimen.sm_f95x);
        } else if (Utils.MODEL_NAME.contains("SM-F94") || Utils.MODEL_NAME.contains("SM-F93") || Utils.MODEL_NAME.contains("SM-W9023") || Utils.MODEL_NAME.contains("SM-W9024")) {
            dimensionPixelSize = resources.getDimensionPixelSize(Utils.isFolded() ? R.dimen.sm_f93x_folded : R.dimen.sm_f93x);
        } else if (Utils.MODEL_NAME.contains("SM-W2021")) {
            dimensionPixelSize = resources.getDimensionPixelSize(Utils.isFolded() ? R.dimen.sm_w2021_folded : R.dimen.sm_w2021);
        } else if (Utils.MODEL_NAME.contains("SM-F92") || Utils.MODEL_NAME.contains("SM-W2022")) {
            dimensionPixelSize = resources.getDimensionPixelSize(Utils.isFolded() ? R.dimen.sm_f92x_folded : R.dimen.sm_f92x);
        } else if (Utils.MODEL_NAME.contains("SM-F91")) {
            dimensionPixelSize = resources.getDimensionPixelSize(Utils.isFolded() ? R.dimen.sm_f91x_folded : R.dimen.sm_f91x);
        } else if (Utils.MODEL_NAME.contains("SM-F90")) {
            dimensionPixelSize = resources.getDimensionPixelSize(Utils.isFolded() ? R.dimen.sm_f90x_folded : R.dimen.sm_f90x);
        } else {
            dimensionPixelSize = Utils.MODEL_NAME.contains("SM-G991") ? resources.getDimensionPixelSize(R.dimen.sm_g991) : (Utils.MODEL_NAME.contains("SM-G99") || Utils.MODEL_NAME.contains("SM-S71")) ? resources.getDimensionPixelSize(R.dimen.sm_g99x) : Utils.MODEL_NAME.contains("SM-S72") ? resources.getDimensionPixelSize(R.dimen.sm_s72x) : Utils.MODEL_NAME.contains("SM-G52") ? resources.getDimensionPixelSize(R.dimen.sm_g52x) : Utils.MODEL_NAME.contains("SM-A51") ? resources.getDimensionPixelSize(R.dimen.sm_a51x) : Utils.MODEL_NAME.contains("SM-A52") ? resources.getDimensionPixelSize(R.dimen.sm_a52x) : Utils.MODEL_NAME.contains("SM-A72") ? resources.getDimensionPixelSize(R.dimen.sm_a72x) : Utils.MODEL_NAME.contains("SM-A71") ? resources.getDimensionPixelSize(R.dimen.sm_a71x) : Utils.MODEL_NAME.contains("SM-A32") ? resources.getDimensionPixelSize(R.dimen.sm_a32x) : Utils.MODEL_NAME.contains("SM-T97") ? resources.getDimensionPixelSize(R.dimen.sm_t97x) : Utils.MODEL_NAME.contains("SM-T87") ? resources.getDimensionPixelSize(R.dimen.sm_t87x) : Utils.MODEL_NAME.contains("SM-M127") ? resources.getDimensionPixelSize(R.dimen.sm_m127) : resources.getDimensionPixelSize(R.dimen.sm_default);
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int i5 = displayMetrics.widthPixels;
        int i6 = displayMetrics.heightPixels;
        if (i5 >= i6) {
            i5 = i6;
        }
        float f4 = Utils.MODEL_DEFAULT_DENSITY;
        if (z) {
            float f5 = i5;
            if (f5 <= 1080.0f) {
                f3 = f5 < 1080.0f ? 0.6667f : 1.3333f;
            }
            f4 *= f3;
        }
        float f6 = dimensionPixelSize * (f4 / context.getResources().getConfiguration().densityDpi);
        if (Settings.System.getInt(getContext().getContentResolver(), "any_screen_running", 0) == 1) {
            float parseFloat = Float.parseFloat(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_CORNER_ROUND"));
            DisplayMetrics displayMetrics2 = new DisplayMetrics();
            ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics2);
            String string = Settings.System.getString(getContext().getContentResolver(), "reduce_screen_running_info");
            if (!TextUtils.isEmpty(string)) {
                String[] split = string.split(";");
                if (split.length == 3) {
                    f = Float.parseFloat(split[2]);
                    f2 = f >= 0.65f ? (1.85f - f) * f : f;
                    if (!Feature.FEATURE_IS_CANVAS || Feature.FEATURE_IS_TOP) {
                        f2 = (1.95f - f) * f;
                        if (f < 0.65f) {
                            f2 = (2.05f - f) * f;
                        }
                    }
                    float f7 = parseFloat * 6.0f * displayMetrics2.density * f2;
                    this.mMaskPaint.setStrokeWidth(this.mStrokeWidth);
                    Path path = this.mMaskPath;
                    float f8 = this.mStrokeWidth / 2.0f;
                    path.addRoundRect(f8, f8, this.mWidth - f8, this.mHeight - f8, f7, f7, Path.Direction.CW);
                }
            }
            f = 1.0f;
            if (f >= 0.65f) {
            }
            if (!Feature.FEATURE_IS_CANVAS) {
            }
            f2 = (1.95f - f) * f;
            if (f < 0.65f) {
            }
            float f72 = parseFloat * 6.0f * displayMetrics2.density * f2;
            this.mMaskPaint.setStrokeWidth(this.mStrokeWidth);
            Path path2 = this.mMaskPath;
            float f82 = this.mStrokeWidth / 2.0f;
            path2.addRoundRect(f82, f82, this.mWidth - f82, this.mHeight - f82, f72, f72, Path.Direction.CW);
        } else {
            this.mMaskPaint.setStrokeWidth(this.mStrokeWidth * 1.6f);
            if (Utils.isLargeCoverFlipFolded()) {
                makeSubScreenPath(this.mMaskPath, this.mWidth, this.mHeight, f6);
                makeSubScreenPath(this.mOutsideMaskPath, this.mWidth, this.mHeight, f6);
            } else {
                this.mMaskPath.addRoundRect(0.0f, 0.0f, this.mWidth, this.mHeight, f6, f6, Path.Direction.CW);
                this.mOutsideMaskPath.addRoundRect(0.0f, 0.0f, this.mWidth, this.mHeight, f6, f6, Path.Direction.CW);
            }
        }
        Path path3 = this.mMaskPath;
        if (path3 != null && !path3.isEmpty()) {
            canvas.drawPath(this.mMaskPath, this.mMaskPaint);
        }
        Path path4 = this.mOutsideMaskPath;
        if (path4 != null && !path4.isEmpty()) {
            canvas.drawPath(this.mOutsideMaskPath, this.mMaskBgPaint);
        }
        canvas.restoreToCount(save);
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
        if (Build.VERSION.SEM_PLATFORM_INT < 100500 || Settings.System.getInt(getContext().getContentResolver(), "high_contrast", 0) != 1) {
            this.mMaskBgPaint.setColor(EmergencyPhoneWidget.BG_COLOR);
        } else {
            this.mMaskBgPaint.setColor(-1);
        }
    }

    public final void makeSubScreenPath(Path path, float f, float f2, float f3) {
        float rotation = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getRotation();
        float dimensionPixelOffset = getContext().getResources().getDimensionPixelOffset(R.dimen.cut_off_height);
        if (rotation == 0.0f) {
            path.moveTo(f3, 0.0f);
            float f4 = f - f3;
            path.lineTo(f4, 0.0f);
            path.quadTo(f, 0.0f, f, f3);
            float f5 = f2 - dimensionPixelOffset;
            path.lineTo(f, f5 - f3);
            path.quadTo(f, f5, f4, f5);
            path.lineTo(f * 0.58f, f5);
            path.quadTo(f * 0.52f, f5, 0.485f * f, f2 - (dimensionPixelOffset / 2.0f));
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
            float f6 = f - dimensionPixelOffset;
            path.lineTo(f6 - f3, 0.0f);
            path.quadTo(f6, 0.0f, f6, f3);
            path.lineTo(f6, f2 * 0.42f);
            path.quadTo(f6, f2 * 0.48f, f - (dimensionPixelOffset / 2.0f), f2 * 0.515f);
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
            path.moveTo(f3, dimensionPixelOffset);
            path.lineTo(f * 0.42f, dimensionPixelOffset);
            path.quadTo(f * 0.48f, dimensionPixelOffset, f * 0.515f, dimensionPixelOffset / 2.0f);
            path.quadTo(f * 0.55f, 0.0f, f * 0.6f, 0.0f);
            float f8 = f - f3;
            path.lineTo(f8, 0.0f);
            path.quadTo(f, 0.0f, f, f3);
            float f9 = f2 - f3;
            path.lineTo(f, f9);
            path.quadTo(f, f2, f8, f2);
            path.lineTo(f3, f2);
            path.quadTo(0.0f, f2, 0.0f, f9);
            path.lineTo(0.0f, dimensionPixelOffset + f3);
            path.quadTo(0.0f, dimensionPixelOffset, f3, dimensionPixelOffset);
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
        path.lineTo(dimensionPixelOffset + f3, f2);
        path.quadTo(dimensionPixelOffset, f2, dimensionPixelOffset, f11);
        path.lineTo(dimensionPixelOffset, f2 * 0.58f);
        path.quadTo(dimensionPixelOffset, f2 * 0.52f, dimensionPixelOffset / 2.0f, f2 * 0.485f);
        path.quadTo(0.0f, f2 * 0.45f, 0.0f, f2 * 0.4f);
        path.lineTo(0.0f, f3);
        path.quadTo(0.0f, 0.0f, f3, 0.0f);
        path.close();
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
