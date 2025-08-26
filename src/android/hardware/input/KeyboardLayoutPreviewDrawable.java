package android.hardware.input;

import android.app.PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.hardware.input.PhysicalKeyLayout;
import android.telecom.Logging.Session;
import android.util.Slog;
import android.util.TypedValue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
final class KeyboardLayoutPreviewDrawable extends Drawable {
    private static final int GRAVITY_BOTTOM = 8;
    private static final int GRAVITY_LEFT = 1;
    private static final int GRAVITY_RIGHT = 2;
    private static final int GRAVITY_TOP = 4;
    private static final int KEYBOARD_PADDING_IN_DP = 10;
    private static final int KEYBOARD_RADIUS_IN_DP = 10;
    private static final int KEY_PADDING_IN_DP = 3;
    private static final int KEY_RADIUS_IN_DP = 5;
    private static final int MAX_GLYPH_TEXT_SIZE_IN_SP = 20;
    private static final int MIN_GLYPH_TEXT_SIZE_IN_SP = 10;
    private static final String TAG = "KeyboardLayoutPreview";
    private static final int TEXT_PADDING_IN_DP = 0;
    private final int mHeight;
    private final PhysicalKeyLayout mKeyLayout;
    private final ResourceProvider mResourceProvider;
    private final int mWidth;
    private final List<KeyDrawable> mKeyDrawables = new ArrayList();
    private final RectF mKeyboardBackground = new RectF();

    private interface KeyDrawable {
        void draw(Canvas canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public KeyboardLayoutPreviewDrawable(Context context, PhysicalKeyLayout physicalKeyLayout, int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mResourceProvider = new ResourceProvider(context);
        this.mKeyLayout = physicalKeyLayout;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mHeight;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        float f;
        int i;
        int i2;
        PhysicalKeyLayout.LayoutKey[] layoutKeyArr;
        int i3;
        super.onBoundsChange(rect);
        this.mKeyDrawables.clear();
        PhysicalKeyLayout.LayoutKey[][] keys = this.mKeyLayout.getKeys();
        if (keys == null) {
            return;
        }
        PhysicalKeyLayout.EnterKey enterKey = this.mKeyLayout.getEnterKey();
        int iWidth = rect.width();
        int iHeight = rect.height();
        int keyboardPadding = this.mResourceProvider.getKeyboardPadding();
        int keyPadding = this.mResourceProvider.getKeyPadding();
        float keyRadius = this.mResourceProvider.getKeyRadius();
        this.mKeyboardBackground.set(0.0f, 0.0f, iWidth, iHeight);
        int i4 = keyboardPadding * 2;
        int i5 = iWidth - i4;
        int i6 = iHeight - i4;
        if (i5 <= 0 || i6 <= 0) {
            Slog.e(TAG, "Invalid width and height to draw layout preview, width = " + i5 + ", height = " + i6);
            return;
        }
        int length = keys.length;
        float f2 = (i6 - ((length * 2) * keyPadding)) / length;
        this.mResourceProvider.calculateBestTextSizeForKey(f2);
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        int i7 = 0;
        while (i7 < length) {
            PhysicalKeyLayout.LayoutKey[] layoutKeyArr2 = keys[i7];
            int length2 = layoutKeyArr2.length;
            PhysicalKeyLayout.LayoutKey[][] layoutKeyArr3 = keys;
            float fKeyWeight = 0.0f;
            for (PhysicalKeyLayout.LayoutKey layoutKey : layoutKeyArr2) {
                fKeyWeight += layoutKey.keyWeight();
            }
            float f6 = (i5 - ((length2 * 2) * keyPadding)) / fKeyWeight;
            float f7 = (i7 * f2) + (((i7 * 2) + 1) * keyPadding) + keyboardPadding;
            float f8 = f3;
            float f9 = f4;
            float f10 = f5;
            int i8 = 0;
            float f11 = 0.0f;
            while (i8 < length2) {
                float f12 = (((i8 * 2) + 1) * keyPadding) + keyboardPadding + (f11 * f6);
                float fKeyWeight2 = f11 + layoutKeyArr2[i8].keyWeight();
                PhysicalKeyLayout.EnterKey enterKey2 = enterKey;
                RectF rectF = new RectF(f12, f7, (layoutKeyArr2[i8].keyWeight() * f6) + f12, f7 + f2);
                if (enterKey2 != null && layoutKeyArr2[i8].keyCode() == 66) {
                    if (enterKey2.row() == i7 && enterKey2.column() == i8) {
                        f = f7;
                        f9 = rectF.left;
                        i = i7;
                        i2 = i8;
                        f10 = rectF.top;
                        layoutKeyArr = layoutKeyArr2;
                        i3 = length2;
                        f8 = f6;
                    } else {
                        f = f7;
                        i = i7;
                        i2 = i8;
                        layoutKeyArr = layoutKeyArr2;
                        i3 = length2;
                    }
                } else if (PhysicalKeyLayout.isSpecialKey(layoutKeyArr2[i8])) {
                    f = f7;
                    i = i7;
                    i2 = i8;
                    layoutKeyArr = layoutKeyArr2;
                    i3 = length2;
                    this.mKeyDrawables.add(new TypingKey(null, rectF, keyRadius, this.mResourceProvider.getTextPadding(), this.mResourceProvider.getSpecialKeyPaint(), this.mResourceProvider.getSpecialKeyPaint(), this.mResourceProvider.getSpecialKeyPaint()));
                } else {
                    f = f7;
                    i = i7;
                    i2 = i8;
                    layoutKeyArr = layoutKeyArr2;
                    i3 = length2;
                    if (PhysicalKeyLayout.isKeyPositionUnsure(layoutKeyArr[i2])) {
                        this.mKeyDrawables.add(new UnsureTypingKey(layoutKeyArr[i2].glyph(), rectF, keyRadius, this.mResourceProvider.getTextPadding(), this.mResourceProvider.getTypingKeyPaint(), this.mResourceProvider.getPrimaryGlyphPaint(), this.mResourceProvider.getSecondaryGlyphPaint()));
                    } else {
                        this.mKeyDrawables.add(new TypingKey(layoutKeyArr[i2].glyph(), rectF, keyRadius, this.mResourceProvider.getTextPadding(), this.mResourceProvider.getTypingKeyPaint(), this.mResourceProvider.getPrimaryGlyphPaint(), this.mResourceProvider.getSecondaryGlyphPaint()));
                    }
                }
                i8 = i2 + 1;
                layoutKeyArr2 = layoutKeyArr;
                f11 = fKeyWeight2;
                enterKey = enterKey2;
                f7 = f;
                length2 = i3;
                i7 = i;
            }
            i7++;
            keys = layoutKeyArr3;
            f3 = f8;
            f4 = f9;
            f5 = f10;
        }
        PhysicalKeyLayout.EnterKey enterKey3 = enterKey;
        if (enterKey3 != null) {
            IsoEnterKey.Builder builder = new IsoEnterKey.Builder(keyRadius, this.mResourceProvider.getSpecialKeyPaint());
            builder.setTopWidth(enterKey3.topKeyWeight() * f3).setStartPoint(f4, f5).setVerticalEdges(f2, (keyPadding + f2) * 2.0f).setBottomWidth(enterKey3.bottomKeyWeight() * f3);
            this.mKeyDrawables.add(builder.build());
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        float backgroundRadius = this.mResourceProvider.getBackgroundRadius();
        canvas.drawRoundRect(this.mKeyboardBackground, backgroundRadius, backgroundRadius, this.mResourceProvider.getBackgroundPaint());
        Iterator<KeyDrawable> it = this.mKeyDrawables.iterator();
        while (it.hasNext()) {
            it.next().draw(canvas);
        }
    }

    private static class TypingKey implements KeyDrawable {
        private final Paint mBaseTextPaint;
        private final List<GlyphDrawable> mGlyphDrawables;
        private final Paint mKeyPaint;
        private final float mKeyRadius;
        private final RectF mKeyRect;
        private final Paint mModifierTextPaint;
        private final float mTextPadding;

        private TypingKey(PhysicalKeyLayout.KeyGlyph keyGlyph, RectF rectF, float f, float f2, Paint paint, Paint paint2, Paint paint3) {
            this.mGlyphDrawables = new ArrayList();
            this.mKeyRect = rectF;
            this.mKeyRadius = f;
            this.mTextPadding = f2;
            this.mKeyPaint = paint;
            this.mBaseTextPaint = paint2;
            this.mModifierTextPaint = paint3;
            initGlyphs(keyGlyph);
        }

        private void initGlyphs(PhysicalKeyLayout.KeyGlyph keyGlyph) {
            createGlyphs(keyGlyph);
            measureGlyphs();
        }

        private void createGlyphs(PhysicalKeyLayout.KeyGlyph keyGlyph) {
            if (keyGlyph != null && keyGlyph.hasBaseText()) {
                this.mGlyphDrawables.add(new GlyphDrawable(keyGlyph.getBaseText(), new RectF(), 9, this.mBaseTextPaint));
                if (keyGlyph.hasValidShiftText()) {
                    this.mGlyphDrawables.add(new GlyphDrawable(keyGlyph.getShiftText(), new RectF(), 5, this.mModifierTextPaint));
                }
                if (keyGlyph.hasValidAltGrText()) {
                    this.mGlyphDrawables.add(new GlyphDrawable(keyGlyph.getAltGrText(), new RectF(), 10, this.mModifierTextPaint));
                }
                if (keyGlyph.hasValidAltGrShiftText()) {
                    this.mGlyphDrawables.add(new GlyphDrawable(keyGlyph.getAltGrShiftText(), new RectF(), 6, this.mModifierTextPaint));
                }
            }
        }

        private void measureGlyphs() {
            float fWidth = this.mKeyRect.width();
            float fHeight = this.mKeyRect.height();
            for (GlyphDrawable glyphDrawable : this.mGlyphDrawables) {
                float f = fWidth / 2.0f;
                float f2 = fHeight / 2.0f;
                if ((glyphDrawable.gravity & 1) != 0) {
                    f = (f - (fWidth / 4.0f)) + (this.mTextPadding / 2.0f);
                }
                if ((glyphDrawable.gravity & 2) != 0) {
                    f = (f + (fWidth / 4.0f)) - (this.mTextPadding / 2.0f);
                }
                if ((glyphDrawable.gravity & 4) != 0) {
                    f2 = (f2 - (fHeight / 4.0f)) + (this.mTextPadding / 2.0f);
                }
                if ((glyphDrawable.gravity & 8) != 0) {
                    f2 = (f2 + (fHeight / 4.0f)) - (this.mTextPadding / 2.0f);
                }
                Rect rect = new Rect();
                glyphDrawable.paint.getTextBounds(glyphDrawable.text, 0, glyphDrawable.text.length(), rect);
                float fWidth2 = rect.width() / 2.0f;
                float fHeight2 = rect.height() / 2.0f;
                glyphDrawable.rect.set(f - fWidth2, (f2 - fHeight2) - rect.top, f + fWidth2, (f2 + fHeight2) - rect.top);
            }
        }

        @Override // android.hardware.input.KeyboardLayoutPreviewDrawable.KeyDrawable
        public void draw(Canvas canvas) {
            RectF rectF = this.mKeyRect;
            float f = this.mKeyRadius;
            canvas.drawRoundRect(rectF, f, f, this.mKeyPaint);
            for (GlyphDrawable glyphDrawable : this.mGlyphDrawables) {
                float fWidth = glyphDrawable.rect.width();
                float fHeight = glyphDrawable.rect.height();
                float fWidth2 = this.mKeyRect.width();
                float fHeight2 = this.mKeyRect.height();
                if (fWidth == 0.0f || fHeight == 0.0f || fWidth2 == 0.0f || fHeight2 == 0.0f) {
                    return;
                } else {
                    canvas.drawText(glyphDrawable.text, 0, glyphDrawable.text.length(), this.mKeyRect.left + glyphDrawable.rect.left, this.mKeyRect.top + glyphDrawable.rect.top, glyphDrawable.paint);
                }
            }
        }
    }

    private static class UnsureTypingKey extends TypingKey {
        private UnsureTypingKey(PhysicalKeyLayout.KeyGlyph keyGlyph, RectF rectF, float f, float f2, Paint paint, Paint paint2, Paint paint3) {
            super(keyGlyph, rectF, f, f2, KeyboardLayoutPreviewDrawable.createGreyedOutPaint(paint), KeyboardLayoutPreviewDrawable.createGreyedOutPaint(paint2), KeyboardLayoutPreviewDrawable.createGreyedOutPaint(paint3));
        }
    }

    private static class IsoEnterKey implements KeyDrawable {
        private final Paint mKeyPaint;
        private final Path mPath;

        private IsoEnterKey(Paint paint, Path path) {
            this.mKeyPaint = paint;
            this.mPath = path;
        }

        @Override // android.hardware.input.KeyboardLayoutPreviewDrawable.KeyDrawable
        public void draw(Canvas canvas) {
            canvas.drawPath(this.mPath, this.mKeyPaint);
        }

        private static class Builder {
            private float mBottomWidth;
            private final Paint mKeyPaint;
            private final float mKeyRadius;
            private float mLeft;
            private float mLeftHeight;
            private float mRightHeight;
            private float mTop;
            private float mTopWidth;

            private Builder(float f, Paint paint) {
                this.mKeyRadius = f;
                this.mKeyPaint = paint;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public Builder setStartPoint(float f, float f2) {
                this.mLeft = f;
                this.mTop = f2;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public Builder setTopWidth(float f) {
                this.mTopWidth = f;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public Builder setBottomWidth(float f) {
                this.mBottomWidth = f;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public Builder setVerticalEdges(float f, float f2) {
                this.mLeftHeight = f;
                this.mRightHeight = f2;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public IsoEnterKey build() {
                Path path = new Path();
                float f = this.mKeyRadius;
                RectF rectF = new RectF(-f, -f, f, f);
                path.moveTo(this.mLeft + this.mKeyRadius, this.mTop);
                path.lineTo((this.mLeft + this.mTopWidth) - this.mKeyRadius, this.mTop);
                rectF.offsetTo((this.mLeft + this.mTopWidth) - (this.mKeyRadius * 2.0f), this.mTop);
                path.arcTo(rectF, 270.0f, 90.0f);
                path.lineTo(this.mLeft + this.mTopWidth, (this.mTop + this.mRightHeight) - this.mKeyRadius);
                float f2 = this.mLeft + this.mTopWidth;
                float f3 = this.mKeyRadius;
                rectF.offsetTo(f2 - (f3 * 2.0f), (this.mTop + this.mRightHeight) - (f3 * 2.0f));
                path.arcTo(rectF, 0.0f, 90.0f);
                path.lineTo(((this.mLeft + this.mTopWidth) - this.mBottomWidth) + this.mKeyRadius, this.mTop + this.mRightHeight);
                rectF.offsetTo((this.mLeft + this.mTopWidth) - this.mBottomWidth, (this.mTop + this.mRightHeight) - (this.mKeyRadius * 2.0f));
                path.arcTo(rectF, 90.0f, 90.0f);
                path.lineTo((this.mLeft + this.mTopWidth) - this.mBottomWidth, (this.mTop + this.mLeftHeight) - this.mKeyRadius);
                rectF.offsetTo(((this.mLeft + this.mTopWidth) - this.mBottomWidth) - (this.mKeyRadius * 2.0f), this.mTop + this.mLeftHeight);
                path.arcTo(rectF, 0.0f, -90.0f);
                path.lineTo(this.mLeft + this.mKeyRadius, this.mTop + this.mLeftHeight);
                rectF.offsetTo(this.mLeft, (this.mTop + this.mLeftHeight) - (this.mKeyRadius * 2.0f));
                path.arcTo(rectF, 90.0f, 90.0f);
                path.lineTo(this.mLeft, this.mTop + this.mKeyRadius);
                rectF.offsetTo(this.mLeft, this.mTop);
                path.arcTo(rectF, 180.0f, 90.0f);
                path.close();
                return new IsoEnterKey(this.mKeyPaint, path);
            }
        }
    }

    private static final class GlyphDrawable extends Record {
        private final int gravity;
        private final Paint paint;
        private final RectF rect;
        private final String text;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof GlyphDrawable)) {
                return false;
            }
            GlyphDrawable glyphDrawable = (GlyphDrawable) obj;
            return this.gravity == glyphDrawable.gravity && Objects.equals(this.text, glyphDrawable.text) && Objects.equals(this.rect, glyphDrawable.rect) && Objects.equals(this.paint, glyphDrawable.paint);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{this.text, this.rect, Integer.valueOf(this.gravity), this.paint};
        }

        private GlyphDrawable(String text, RectF rect, int gravity, Paint paint) {
            this.text = text;
            this.rect = rect;
            this.gravity = gravity;
            this.paint = paint;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        public int gravity() {
            return this.gravity;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.gravity, this.text, this.rect, this.paint);
        }

        public Paint paint() {
            return this.paint;
        }

        public RectF rect() {
            return this.rect;
        }

        public String text() {
            return this.text;
        }

        @Override // java.lang.Record
        public final String toString() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), GlyphDrawable.class, "text;rect;gravity;paint");
        }
    }

    private static class ResourceProvider {
        private final Paint mBackgroundPaint;
        private final float mBackgroundRadius;
        private final Paint.FontMetrics mFontMetrics;
        private final int mKeyPadding;
        private final float mKeyRadius;
        private final int mKeyboardPadding;
        private final Paint mPrimaryGlyphPaint;
        private final Paint mSecondaryGlyphPaint;
        private final float mSpToPxMultiplier;
        private final Paint mSpecialKeyPaint;
        private final float mTextPadding;
        private final Paint mTypingKeyPaint;

        private ResourceProvider(Context context) {
            this.mKeyPadding = (int) TypedValue.applyDimension(1, 3.0f, context.getResources().getDisplayMetrics());
            this.mKeyboardPadding = (int) TypedValue.applyDimension(1, 10.0f, context.getResources().getDisplayMetrics());
            this.mKeyRadius = (int) TypedValue.applyDimension(1, 5.0f, context.getResources().getDisplayMetrics());
            this.mBackgroundRadius = (int) TypedValue.applyDimension(1, 10.0f, context.getResources().getDisplayMetrics());
            float fApplyDimension = TypedValue.applyDimension(2, 1.0f, context.getResources().getDisplayMetrics());
            this.mSpToPxMultiplier = fApplyDimension;
            this.mTextPadding = TypedValue.applyDimension(1, 0.0f, context.getResources().getDisplayMetrics());
            boolean z = (context.getResources().getConfiguration().uiMode & 48) == 32;
            int color = context.getColor(z ? 17170625 : 17170543);
            int color2 = context.getColor(z ? 17170471 : 17170530);
            int color3 = context.getColor(z ? 17170584 : 17170541);
            int color4 = context.getColor(z ? 17170594 : 17170551);
            int color5 = context.getColor(z ? 17170587 : 17170544);
            Paint paintCreateTextPaint = KeyboardLayoutPreviewDrawable.createTextPaint(color3, fApplyDimension * 10.0f, Typeface.create(Typeface.SANS_SERIF, 1));
            this.mPrimaryGlyphPaint = paintCreateTextPaint;
            this.mSecondaryGlyphPaint = KeyboardLayoutPreviewDrawable.createTextPaint(color4, fApplyDimension * 10.0f, Typeface.create(Typeface.SANS_SERIF, 0));
            this.mFontMetrics = paintCreateTextPaint.getFontMetrics();
            this.mTypingKeyPaint = KeyboardLayoutPreviewDrawable.createFillPaint(color);
            this.mSpecialKeyPaint = KeyboardLayoutPreviewDrawable.createFillPaint(color2);
            this.mBackgroundPaint = KeyboardLayoutPreviewDrawable.createFillPaint(color5);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void calculateBestTextSizeForKey(float f) {
            int i = ((int) (this.mSpToPxMultiplier * 10.0f)) + 1;
            int iMax = Math.max(1, Math.min(i, ((int) f) / 4));
            if (i > iMax) {
                Slog.d(KeyboardLayoutPreviewDrawable.TAG, "calculateBestTextSizeForKey: adjust initial size " + i + Session.SUBSESSION_SEPARATION_CHAR + iMax);
                i = iMax;
            }
            while (true) {
                float f2 = i;
                if (f2 >= this.mSpToPxMultiplier * 20.0f) {
                    break;
                }
                updateTextSize(f2);
                if ((this.mFontMetrics.bottom - this.mFontMetrics.top) + (this.mTextPadding * 3.0f) > f / 2.0f) {
                    i--;
                    break;
                }
                i++;
            }
            Slog.d(KeyboardLayoutPreviewDrawable.TAG, "final textSize=" + i + ", keyHeight=" + f);
            updateTextSize((float) i);
        }

        private void updateTextSize(float f) {
            this.mPrimaryGlyphPaint.setTextSize(f);
            this.mSecondaryGlyphPaint.setTextSize(f);
            this.mPrimaryGlyphPaint.getFontMetrics(this.mFontMetrics);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Paint getBackgroundPaint() {
            return this.mBackgroundPaint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Paint getTypingKeyPaint() {
            return this.mTypingKeyPaint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Paint getSpecialKeyPaint() {
            return this.mSpecialKeyPaint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Paint getPrimaryGlyphPaint() {
            return this.mPrimaryGlyphPaint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Paint getSecondaryGlyphPaint() {
            return this.mSecondaryGlyphPaint;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getKeyPadding() {
            return this.mKeyPadding;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getKeyboardPadding() {
            return this.mKeyboardPadding;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getTextPadding() {
            return this.mTextPadding;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getKeyRadius() {
            return this.mKeyRadius;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getBackgroundRadius() {
            return this.mBackgroundRadius;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Paint createTextPaint(int i, float f, Typeface typeface) {
        Paint paint = new Paint();
        paint.setColor(i);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(f);
        paint.setTypeface(typeface);
        return paint;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Paint createFillPaint(int i) {
        Paint paint = new Paint();
        paint.setColor(i);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Paint createGreyedOutPaint(Paint paint) {
        Paint paint2 = new Paint(paint);
        paint2.setAlpha(100);
        return paint2;
    }
}
