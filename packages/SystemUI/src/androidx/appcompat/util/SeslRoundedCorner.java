package androidx.appcompat.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.TypedValue;
import android.view.View;
import androidx.core.graphics.Insets;
import androidx.core.graphics.PathParser;
import com.android.systemui.R;
import java.util.Locale;

/* loaded from: classes.dex */
public class SeslRoundedCorner {
    public final SeslRoundedChunkingDrawable mBottomLeftRound;
    public int mBottomLeftRoundColor;
    public final SeslRoundedChunkingDrawable mBottomRightRound;
    public int mBottomRightRoundColor;
    public Insets mInsets;
    public final int mRoundRadius;
    public final Rect mRoundedCornerBounds;
    public int mRoundedCornerMode;
    public final SeslRoundedChunkingDrawable mTopLeftRound;
    public final int mTopLeftRoundColor;
    public final SeslRoundedChunkingDrawable mTopRightRound;
    public final int mTopRightRoundColor;

    public class SeslRoundedChunkingDrawable extends Drawable {
        public final float mAngle;
        public ColorFilter mColorFilter;
        public final Paint mPaint;
        public final int mRoundRadius;
        public int mWidth = 0;
        public int mHeight = 0;
        public PathParser.PathDataNode[] mPathDataNodes = null;
        public final Path mPath = new Path();

        public SeslRoundedChunkingDrawable(int i, Paint paint, float f) {
            this.mRoundRadius = i;
            this.mPaint = paint;
            this.mAngle = f;
        }

        @Override // android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            boolean z;
            Path path;
            if (this.mWidth == canvas.getWidth() && this.mHeight == canvas.getHeight()) {
                z = false;
            } else {
                this.mWidth = canvas.getWidth();
                this.mHeight = canvas.getHeight();
                z = true;
            }
            this.mPaint.setColorFilter(this.mColorFilter);
            float f = this.mRoundRadius;
            int i = this.mWidth;
            int i2 = this.mHeight;
            if (i <= 0 || i2 <= 0) {
                path = new Path();
            } else {
                float f2 = i / 2.0f;
                float f3 = i2 / 2.0f;
                float fMin = Math.min(f2, f3);
                float fMin2 = Math.min(Math.max(f, 0.0f), fMin);
                float f4 = fMin2 / fMin;
                float fMin3 = f4 > 0.5f ? 1.0f - (Math.min(1.0f, (f4 - 0.5f) / 0.4f) * 0.13877845f) : 1.0f;
                float fMin4 = ((double) f4) > 0.6d ? 1.0f + (Math.min(1.0f, (f4 - 0.6f) / 0.3f) * 0.042454004f) : 1.0f;
                if (z || this.mPathDataNodes == null) {
                    float f5 = (f2 / fMin2) * 100.0f;
                    Locale locale = Locale.ENGLISH;
                    float f6 = fMin3 * 128.19f;
                    String str = String.format(locale, "L %f %f ", Float.valueOf(0.0f), Float.valueOf(Math.min((f3 / fMin2) * 100.0f, f6)));
                    float f7 = fMin4 * 83.62f;
                    String str2 = String.format(locale, "C %f %f %f %f %f %f ", Float.valueOf(0.0f), Float.valueOf(f7), Float.valueOf(4.64f), Float.valueOf(67.45f), Float.valueOf(13.36f), Float.valueOf(51.16f));
                    String str3 = String.format(locale, "C %f %f %f %f %f %f ", Float.valueOf(22.07f), Float.valueOf(34.86f), Float.valueOf(34.86f), Float.valueOf(22.07f), Float.valueOf(51.16f), Float.valueOf(13.36f));
                    String str4 = String.format(locale, "C %f %f %f %f %f %f ", Float.valueOf(67.45f), Float.valueOf(4.64f), Float.valueOf(f7), Float.valueOf(0.0f), Float.valueOf(Math.min(f5, f6)), Float.valueOf(0.0f));
                    String str5 = String.format(locale, "L %f %f ", Float.valueOf(Math.min(f5, f6)), Float.valueOf(0.0f));
                    StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("M 0 0 ", str, str2, str3, str4);
                    sbM.append(str5);
                    sbM.append("Z");
                    this.mPathDataNodes = PathParser.createNodesFromPathData(sbM.toString());
                }
                this.mPath.reset();
                PathParser.nodesToPath(this.mPathDataNodes, this.mPath);
                path = this.mPath;
                Matrix matrix = new Matrix();
                float f8 = fMin2 / 100.0f;
                matrix.setScale(f8, f8);
                path.transform(matrix);
                Rect bounds = getBounds();
                Matrix matrix2 = new Matrix();
                matrix2.setRotate(this.mAngle, bounds.width() / 2.0f, bounds.height() / 2.0f);
                path.transform(matrix2);
                Matrix matrix3 = new Matrix();
                matrix3.setTranslate(bounds.left, bounds.top);
                path.transform(matrix3);
            }
            canvas.drawPath(path, this.mPaint);
        }

        @Override // android.graphics.drawable.Drawable
        public final ColorFilter getColorFilter() {
            return this.mColorFilter;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getOpacity() {
            return -1;
        }

        @Override // android.graphics.drawable.Drawable
        public final void setAlpha(int i) {
            this.mPaint.setAlpha(i);
        }

        @Override // android.graphics.drawable.Drawable
        public final void setColorFilter(ColorFilter colorFilter) {
            this.mColorFilter = colorFilter;
        }
    }

    public SeslRoundedCorner(Context context) {
        this(context, false);
    }

    public void drawRoundedCorner(View view, Canvas canvas) {
        int left;
        int top;
        if (view.getTranslationY() != 0.0f) {
            left = Math.round(view.getX());
            top = Math.round(view.getY());
            canvas.translate((view.getX() - left) + 0.5f, (view.getY() - top) + 0.5f);
        } else {
            left = view.getLeft();
            top = view.getTop();
        }
        this.mRoundedCornerBounds.set(left, top, view.getWidth() + left, view.getHeight() + top);
        drawRoundedCornerInternal$1(canvas);
    }

    public final void drawRoundedCornerInternal$1(Canvas canvas) {
        Rect rect = this.mRoundedCornerBounds;
        int i = rect.left;
        Insets insets = this.mInsets;
        int i2 = i + (insets != null ? insets.left : 0);
        int i3 = rect.right - (insets != null ? insets.right : 0);
        int i4 = rect.top + (insets != null ? insets.top : 0);
        int i5 = rect.bottom - (insets != null ? insets.bottom : 0);
        int i6 = this.mRoundedCornerMode & 1;
        int i7 = this.mRoundRadius;
        if (i6 != 0) {
            SeslRoundedChunkingDrawable seslRoundedChunkingDrawable = this.mTopLeftRound;
            seslRoundedChunkingDrawable.setBounds(i2, i4, i2 + i7, i4 + i7);
            seslRoundedChunkingDrawable.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 2) != 0) {
            SeslRoundedChunkingDrawable seslRoundedChunkingDrawable2 = this.mTopRightRound;
            seslRoundedChunkingDrawable2.setBounds(i3 - i7, i4, i3, i4 + i7);
            seslRoundedChunkingDrawable2.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 4) != 0) {
            SeslRoundedChunkingDrawable seslRoundedChunkingDrawable3 = this.mBottomLeftRound;
            seslRoundedChunkingDrawable3.setBounds(i2, i5 - i7, i2 + i7, i5);
            seslRoundedChunkingDrawable3.draw(canvas);
        }
        if ((this.mRoundedCornerMode & 8) != 0) {
            SeslRoundedChunkingDrawable seslRoundedChunkingDrawable4 = this.mBottomRightRound;
            seslRoundedChunkingDrawable4.setBounds(i3 - i7, i5 - i7, i3, i5);
            seslRoundedChunkingDrawable4.draw(canvas);
        }
        int i8 = this.mTopLeftRoundColor;
        if (i8 == this.mTopRightRoundColor && i8 == this.mBottomLeftRoundColor && i8 == this.mBottomRightRoundColor) {
            Paint paint = new Paint();
            paint.setColor(i8);
            Insets insets2 = this.mInsets;
            if (insets2 != null && insets2.top > 0) {
                Insets insets3 = this.mInsets;
                canvas.drawRect(new Rect(i2 - insets3.left, i4 - insets3.top, insets3.right + i3, i4), paint);
            }
            Insets insets4 = this.mInsets;
            if (insets4 != null && insets4.bottom > 0) {
                Insets insets5 = this.mInsets;
                canvas.drawRect(new Rect(i2 - insets5.left, i5, insets5.right + i3, insets5.bottom + i5), paint);
            }
            Insets insets6 = this.mInsets;
            if (insets6 != null && insets6.left > 0) {
                Insets insets7 = this.mInsets;
                canvas.drawRect(new Rect(i2 - insets7.left, i4 - insets7.top, i2, insets7.bottom + i5), paint);
            }
            Insets insets8 = this.mInsets;
            if (insets8 == null || insets8.right <= 0) {
                return;
            }
            Insets insets9 = this.mInsets;
            canvas.drawRect(new Rect(i3, i4 - insets9.top, insets9.right + i3, i5 + insets9.bottom), paint);
        }
    }

    public final void setRoundedCorners(int i) {
        if ((i & (-16)) != 0) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Use wrong rounded corners to the param, corners = "));
        }
        this.mRoundedCornerMode = i;
    }

    public SeslRoundedCorner(Context context, boolean z) throws Resources.NotFoundException {
        int color;
        int i;
        int i2;
        this.mRoundedCornerBounds = new Rect();
        this.mInsets = null;
        Resources resources = context.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sesl_rounded_corner_radius);
        this.mRoundRadius = dimensionPixelSize;
        boolean zIsLightTheme = SeslMisc.isLightTheme(context);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.roundedCornerColor, typedValue, true);
        int i3 = typedValue.resourceId;
        if (i3 <= 0 || (i2 = typedValue.type) < 28 || i2 > 31) {
            color = typedValue.data;
            if (color <= 0 || (i = typedValue.type) < 28 || i > 31) {
                color = resources.getColor(!zIsLightTheme ? R.color.sesl_round_and_bgcolor_dark : R.color.sesl_round_and_bgcolor_light);
            }
        } else {
            color = resources.getColor(i3);
        }
        this.mBottomRightRoundColor = color;
        this.mBottomLeftRoundColor = color;
        this.mTopRightRoundColor = color;
        this.mTopLeftRoundColor = color;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(-1);
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
        SeslRoundedChunkingDrawable seslRoundedChunkingDrawable = new SeslRoundedChunkingDrawable(dimensionPixelSize, paint, 0.0f);
        this.mTopLeftRound = seslRoundedChunkingDrawable;
        seslRoundedChunkingDrawable.mColorFilter = porterDuffColorFilter;
        SeslRoundedChunkingDrawable seslRoundedChunkingDrawable2 = new SeslRoundedChunkingDrawable(dimensionPixelSize, paint, 90.0f);
        this.mTopRightRound = seslRoundedChunkingDrawable2;
        seslRoundedChunkingDrawable2.mColorFilter = porterDuffColorFilter;
        SeslRoundedChunkingDrawable seslRoundedChunkingDrawable3 = new SeslRoundedChunkingDrawable(dimensionPixelSize, paint, 270.0f);
        this.mBottomLeftRound = seslRoundedChunkingDrawable3;
        seslRoundedChunkingDrawable3.mColorFilter = porterDuffColorFilter;
        SeslRoundedChunkingDrawable seslRoundedChunkingDrawable4 = new SeslRoundedChunkingDrawable(dimensionPixelSize, paint, 180.0f);
        this.mBottomRightRound = seslRoundedChunkingDrawable4;
        seslRoundedChunkingDrawable4.mColorFilter = porterDuffColorFilter;
    }
}
