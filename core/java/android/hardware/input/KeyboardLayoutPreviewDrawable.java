package android.hardware.input;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Slog;
import android.util.TypedValue;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;

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
    private static final int TEXT_PADDING_IN_DP = 1;
    private final int mHeight;
    private final PhysicalKeyLayout mKeyLayout;
    private final ResourceProvider mResourceProvider;
    private final int mWidth;
    private final List<KeyDrawable> mKeyDrawables = new ArrayList();
    private final RectF mKeyboardBackground = new RectF();

    private interface KeyDrawable {
        void draw(Canvas canvas);
    }

    public KeyboardLayoutPreviewDrawable(
            Context context, PhysicalKeyLayout keyLayout, int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
        this.mResourceProvider = new ResourceProvider(context);
        this.mKeyLayout = keyLayout;
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
    protected void onBoundsChange(Rect bounds) {
        int width;
        int height;
        float keyWidthInPx;
        int height2;
        int j;
        PhysicalKeyLayout.LayoutKey[] row;
        int i;
        float keyHeight;
        float top;
        int width2;
        super.onBoundsChange(bounds);
        this.mKeyDrawables.clear();
        PhysicalKeyLayout.LayoutKey[][] keys = this.mKeyLayout.getKeys();
        if (keys == null) {
            return;
        }
        PhysicalKeyLayout.EnterKey enterKey = this.mKeyLayout.getEnterKey();
        int width3 = bounds.width();
        int height3 = bounds.height();
        int keyboardPadding = this.mResourceProvider.getKeyboardPadding();
        int keyPadding = this.mResourceProvider.getKeyPadding();
        float keyRadius = this.mResourceProvider.getKeyRadius();
        this.mKeyboardBackground.set(0.0f, 0.0f, width3, height3);
        int width4 = width3 - (keyboardPadding * 2);
        int height4 = height3 - (keyboardPadding * 2);
        if (width4 <= 0) {
            width = width4;
            height = height4;
        } else {
            if (height4 > 0) {
                int rowCount = keys.length;
                float keyHeight2 = (height4 - ((rowCount * 2) * keyPadding)) / rowCount;
                this.mResourceProvider.calculateBestTextSizeForKey(keyHeight2);
                float isoEnterKeyLeft = 0.0f;
                float isoEnterKeyTop = 0.0f;
                float isoEnterWidthUnit = 0.0f;
                int i2 = 0;
                while (i2 < rowCount) {
                    PhysicalKeyLayout.LayoutKey[] row2 = keys[i2];
                    float isoEnterKeyTop2 = isoEnterKeyTop;
                    int keysInRow = row2.length;
                    PhysicalKeyLayout.LayoutKey[][] keys2 = keys;
                    float totalRowWeight = 0.0f;
                    for (PhysicalKeyLayout.LayoutKey layoutKey : row2) {
                        totalRowWeight += layoutKey.keyWeight();
                    }
                    float keyWidthInPx2 =
                            (width4 - ((keysInRow * 2) * keyPadding)) / totalRowWeight;
                    int rowCount2 = rowCount;
                    float top2 =
                            (i2 * keyHeight2) + keyboardPadding + (((i2 * 2) + 1) * keyPadding);
                    int j2 = 0;
                    float isoEnterWidthUnit2 = isoEnterWidthUnit;
                    float isoEnterKeyTop3 = isoEnterKeyTop2;
                    float isoEnterKeyLeft2 = isoEnterKeyLeft;
                    float isoEnterKeyLeft3 = 0.0f;
                    while (j2 < keysInRow) {
                        float left =
                                (((j2 * 2) + 1) * keyPadding)
                                        + keyboardPadding
                                        + (isoEnterKeyLeft3 * keyWidthInPx2);
                        float rowWeightOnLeft = isoEnterKeyLeft3 + row2[j2].keyWeight();
                        int keyboardPadding2 = keyboardPadding;
                        int keysInRow2 = keysInRow;
                        RectF keyRect =
                                new RectF(
                                        left,
                                        top2,
                                        left + (row2[j2].keyWeight() * keyWidthInPx2),
                                        top2 + keyHeight2);
                        if (enterKey != null && row2[j2].keyCode() == 66) {
                            if (enterKey.row() != i2 || enterKey.column() != j2) {
                                keyWidthInPx = keyWidthInPx2;
                                width2 = width4;
                                height2 = height4;
                                j = j2;
                                row = row2;
                                i = i2;
                                keyHeight = keyHeight2;
                                top = top2;
                            } else {
                                float isoEnterKeyLeft4 = keyRect.left;
                                float isoEnterKeyTop4 = keyRect.top;
                                isoEnterWidthUnit2 = keyWidthInPx2;
                                keyWidthInPx = keyWidthInPx2;
                                width2 = width4;
                                height2 = height4;
                                isoEnterKeyLeft2 = isoEnterKeyLeft4;
                                isoEnterKeyTop3 = isoEnterKeyTop4;
                                j = j2;
                                row = row2;
                                i = i2;
                                keyHeight = keyHeight2;
                                top = top2;
                            }
                        } else if (PhysicalKeyLayout.isSpecialKey(row2[j2])) {
                            keyWidthInPx = keyWidthInPx2;
                            height2 = height4;
                            j = j2;
                            row = row2;
                            i = i2;
                            keyHeight = keyHeight2;
                            top = top2;
                            this.mKeyDrawables.add(
                                    new TypingKey(
                                            null,
                                            keyRect,
                                            keyRadius,
                                            this.mResourceProvider.getTextPadding(),
                                            this.mResourceProvider.getSpecialKeyPaint(),
                                            this.mResourceProvider.getSpecialKeyPaint(),
                                            this.mResourceProvider.getSpecialKeyPaint()));
                            width2 = width4;
                        } else {
                            keyWidthInPx = keyWidthInPx2;
                            height2 = height4;
                            j = j2;
                            row = row2;
                            i = i2;
                            keyHeight = keyHeight2;
                            top = top2;
                            if (PhysicalKeyLayout.isKeyPositionUnsure(row[j])) {
                                width2 = width4;
                                this.mKeyDrawables.add(
                                        new UnsureTypingKey(
                                                row[j].glyph(),
                                                keyRect,
                                                keyRadius,
                                                this.mResourceProvider.getTextPadding(),
                                                this.mResourceProvider.getTypingKeyPaint(),
                                                this.mResourceProvider.getPrimaryGlyphPaint(),
                                                this.mResourceProvider.getSecondaryGlyphPaint()));
                            } else {
                                width2 = width4;
                                this.mKeyDrawables.add(
                                        new TypingKey(
                                                row[j].glyph(),
                                                keyRect,
                                                keyRadius,
                                                this.mResourceProvider.getTextPadding(),
                                                this.mResourceProvider.getTypingKeyPaint(),
                                                this.mResourceProvider.getPrimaryGlyphPaint(),
                                                this.mResourceProvider.getSecondaryGlyphPaint()));
                            }
                        }
                        j2 = j + 1;
                        i2 = i;
                        isoEnterKeyLeft3 = rowWeightOnLeft;
                        keyboardPadding = keyboardPadding2;
                        keysInRow = keysInRow2;
                        keyWidthInPx2 = keyWidthInPx;
                        top2 = top;
                        width4 = width2;
                        height4 = height2;
                        row2 = row;
                        keyHeight2 = keyHeight;
                    }
                    i2++;
                    keys = keys2;
                    rowCount = rowCount2;
                    isoEnterKeyLeft = isoEnterKeyLeft2;
                    isoEnterKeyTop = isoEnterKeyTop3;
                    isoEnterWidthUnit = isoEnterWidthUnit2;
                }
                float isoEnterKeyTop5 = isoEnterKeyTop;
                float keyHeight3 = keyHeight2;
                if (enterKey != null) {
                    IsoEnterKey.Builder isoEnterKeyBuilder =
                            new IsoEnterKey.Builder(
                                    keyRadius, this.mResourceProvider.getSpecialKeyPaint());
                    isoEnterKeyBuilder
                            .setTopWidth(enterKey.topKeyWeight() * isoEnterWidthUnit)
                            .setStartPoint(isoEnterKeyLeft, isoEnterKeyTop5)
                            .setVerticalEdges(keyHeight3, (keyHeight3 + keyPadding) * 2.0f)
                            .setBottomWidth(enterKey.bottomKeyWeight() * isoEnterWidthUnit);
                    this.mKeyDrawables.add(isoEnterKeyBuilder.build());
                    return;
                }
                return;
            }
            width = width4;
            height = height4;
        }
        Slog.e(
                TAG,
                "Invalid width and height to draw layout preview, width = "
                        + width
                        + ", height = "
                        + height);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        float keyboardRadius = this.mResourceProvider.getBackgroundRadius();
        canvas.drawRoundRect(
                this.mKeyboardBackground,
                keyboardRadius,
                keyboardRadius,
                this.mResourceProvider.getBackgroundPaint());
        for (KeyDrawable key : this.mKeyDrawables) {
            key.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {}

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {}

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -1;
    }

    private static class TypingKey implements KeyDrawable {
        private final Paint mBaseTextPaint;
        private final List<GlyphDrawable> mGlyphDrawables;
        private final Paint mKeyPaint;
        private final float mKeyRadius;
        private final RectF mKeyRect;
        private final Paint mModifierTextPaint;
        private final float mTextPadding;

        private TypingKey(
                PhysicalKeyLayout.KeyGlyph glyphData,
                RectF keyRect,
                float keyRadius,
                float textPadding,
                Paint keyPaint,
                Paint baseTextPaint,
                Paint modifierTextPaint) {
            this.mGlyphDrawables = new ArrayList();
            this.mKeyRect = keyRect;
            this.mKeyRadius = keyRadius;
            this.mTextPadding = textPadding;
            this.mKeyPaint = keyPaint;
            this.mBaseTextPaint = baseTextPaint;
            this.mModifierTextPaint = modifierTextPaint;
            initGlyphs(glyphData);
        }

        private void initGlyphs(PhysicalKeyLayout.KeyGlyph glyphData) {
            createGlyphs(glyphData);
            measureGlyphs();
        }

        private void createGlyphs(PhysicalKeyLayout.KeyGlyph glyphData) {
            if (glyphData == null || !glyphData.hasBaseText()) {
                return;
            }
            this.mGlyphDrawables.add(
                    new GlyphDrawable(
                            glyphData.getBaseText(), new RectF(), 9, this.mBaseTextPaint));
            if (glyphData.hasValidShiftText()) {
                this.mGlyphDrawables.add(
                        new GlyphDrawable(
                                glyphData.getShiftText(), new RectF(), 5, this.mModifierTextPaint));
            }
            if (glyphData.hasValidAltGrText()) {
                this.mGlyphDrawables.add(
                        new GlyphDrawable(
                                glyphData.getAltGrText(),
                                new RectF(),
                                10,
                                this.mModifierTextPaint));
            }
            if (glyphData.hasValidAltGrShiftText()) {
                this.mGlyphDrawables.add(
                        new GlyphDrawable(
                                glyphData.getAltGrShiftText(),
                                new RectF(),
                                6,
                                this.mModifierTextPaint));
            }
        }

        private void measureGlyphs() {
            float keyWidth = this.mKeyRect.width();
            float keyHeight = this.mKeyRect.height();
            for (GlyphDrawable glyph : this.mGlyphDrawables) {
                float centerX = keyWidth / 2.0f;
                float centerY = keyHeight / 2.0f;
                if ((glyph.gravity & 1) != 0) {
                    centerX = (centerX - (keyWidth / 4.0f)) + (this.mTextPadding / 2.0f);
                }
                if ((glyph.gravity & 2) != 0) {
                    centerX = (centerX + (keyWidth / 4.0f)) - (this.mTextPadding / 2.0f);
                }
                if ((glyph.gravity & 4) != 0) {
                    centerY = (centerY - (keyHeight / 4.0f)) + (this.mTextPadding / 2.0f);
                }
                if ((glyph.gravity & 8) != 0) {
                    centerY = (centerY + (keyHeight / 4.0f)) - (this.mTextPadding / 2.0f);
                }
                Rect textBounds = new Rect();
                glyph.paint.getTextBounds(glyph.text, 0, glyph.text.length(), textBounds);
                float textWidth = textBounds.width();
                float textHeight = textBounds.height();
                glyph.rect.set(
                        centerX - (textWidth / 2.0f),
                        (centerY - (textHeight / 2.0f)) - textBounds.top,
                        (textWidth / 2.0f) + centerX,
                        ((textHeight / 2.0f) + centerY) - textBounds.top);
            }
        }

        @Override // android.hardware.input.KeyboardLayoutPreviewDrawable.KeyDrawable
        public void draw(Canvas canvas) {
            canvas.drawRoundRect(this.mKeyRect, this.mKeyRadius, this.mKeyRadius, this.mKeyPaint);
            for (GlyphDrawable glyph : this.mGlyphDrawables) {
                float textWidth = glyph.rect.width();
                float textHeight = glyph.rect.height();
                float keyWidth = this.mKeyRect.width();
                float keyHeight = this.mKeyRect.height();
                if (textWidth == 0.0f
                        || textHeight == 0.0f
                        || keyWidth == 0.0f
                        || keyHeight == 0.0f) {
                    return;
                } else {
                    canvas.drawText(
                            glyph.text,
                            0,
                            glyph.text.length(),
                            this.mKeyRect.left + glyph.rect.left,
                            this.mKeyRect.top + glyph.rect.top,
                            glyph.paint);
                }
            }
        }
    }

    private static class UnsureTypingKey extends TypingKey {
        private UnsureTypingKey(
                PhysicalKeyLayout.KeyGlyph glyphData,
                RectF keyRect,
                float keyRadius,
                float textPadding,
                Paint keyPaint,
                Paint baseTextPaint,
                Paint modifierTextPaint) {
            super(
                    glyphData,
                    keyRect,
                    keyRadius,
                    textPadding,
                    KeyboardLayoutPreviewDrawable.createGreyedOutPaint(keyPaint),
                    KeyboardLayoutPreviewDrawable.createGreyedOutPaint(baseTextPaint),
                    KeyboardLayoutPreviewDrawable.createGreyedOutPaint(modifierTextPaint));
        }
    }

    private static class IsoEnterKey implements KeyDrawable {
        private final Paint mKeyPaint;
        private final Path mPath;

        private IsoEnterKey(Paint keyPaint, Path path) {
            this.mKeyPaint = keyPaint;
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

            private Builder(float keyRadius, Paint keyPaint) {
                this.mKeyRadius = keyRadius;
                this.mKeyPaint = keyPaint;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public Builder setStartPoint(float left, float top) {
                this.mLeft = left;
                this.mTop = top;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public Builder setTopWidth(float width) {
                this.mTopWidth = width;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public Builder setBottomWidth(float width) {
                this.mBottomWidth = width;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public Builder setVerticalEdges(float leftHeight, float rightHeight) {
                this.mLeftHeight = leftHeight;
                this.mRightHeight = rightHeight;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public IsoEnterKey build() {
                Path enterKey = new Path();
                RectF oval =
                        new RectF(
                                -this.mKeyRadius,
                                -this.mKeyRadius,
                                this.mKeyRadius,
                                this.mKeyRadius);
                enterKey.moveTo(this.mLeft + this.mKeyRadius, this.mTop);
                enterKey.lineTo((this.mLeft + this.mTopWidth) - this.mKeyRadius, this.mTop);
                oval.offsetTo((this.mLeft + this.mTopWidth) - (this.mKeyRadius * 2.0f), this.mTop);
                enterKey.arcTo(oval, 270.0f, 90.0f);
                enterKey.lineTo(
                        this.mLeft + this.mTopWidth,
                        (this.mTop + this.mRightHeight) - this.mKeyRadius);
                oval.offsetTo(
                        (this.mLeft + this.mTopWidth) - (this.mKeyRadius * 2.0f),
                        (this.mTop + this.mRightHeight) - (this.mKeyRadius * 2.0f));
                enterKey.arcTo(oval, 0.0f, 90.0f);
                enterKey.lineTo(
                        ((this.mLeft + this.mTopWidth) - this.mBottomWidth) + this.mKeyRadius,
                        this.mTop + this.mRightHeight);
                oval.offsetTo(
                        (this.mLeft + this.mTopWidth) - this.mBottomWidth,
                        (this.mTop + this.mRightHeight) - (this.mKeyRadius * 2.0f));
                enterKey.arcTo(oval, 90.0f, 90.0f);
                enterKey.lineTo(
                        (this.mLeft + this.mTopWidth) - this.mBottomWidth,
                        (this.mTop + this.mLeftHeight) - this.mKeyRadius);
                oval.offsetTo(
                        ((this.mLeft + this.mTopWidth) - this.mBottomWidth)
                                - (this.mKeyRadius * 2.0f),
                        this.mTop + this.mLeftHeight);
                enterKey.arcTo(oval, 0.0f, -90.0f);
                enterKey.lineTo(this.mLeft + this.mKeyRadius, this.mTop + this.mLeftHeight);
                oval.offsetTo(
                        this.mLeft, (this.mTop + this.mLeftHeight) - (this.mKeyRadius * 2.0f));
                enterKey.arcTo(oval, 90.0f, 90.0f);
                enterKey.lineTo(this.mLeft, this.mTop + this.mKeyRadius);
                oval.offsetTo(this.mLeft, this.mTop);
                enterKey.arcTo(oval, 180.0f, 90.0f);
                enterKey.close();
                return new IsoEnterKey(this.mKeyPaint, enterKey);
            }
        }
    }

    private static final class GlyphDrawable extends Record {
        private final int gravity;
        private final Paint paint;
        private final RectF rect;
        private final String text;

        private GlyphDrawable(String text, RectF rect, int gravity, Paint paint) {
            this.text = text;
            this.rect = rect;
            this.gravity = gravity;
            this.paint = paint;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "equals",
                                    MethodType.methodType(
                                            Boolean.TYPE, GlyphDrawable.class, Object.class),
                                    GlyphDrawable.class,
                                    "text;rect;gravity;paint",
                                    "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->text:Ljava/lang/String;",
                                    "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->rect:Landroid/graphics/RectF;",
                                    "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->gravity:I",
                                    "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->paint:Landroid/graphics/Paint;")
                            .dynamicInvoker()
                            .invoke(this, o) /* invoke-custom */;
        }

        public int gravity() {
            return this.gravity;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "hashCode",
                                    MethodType.methodType(Integer.TYPE, GlyphDrawable.class),
                                    GlyphDrawable.class,
                                    "text;rect;gravity;paint",
                                    "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->text:Ljava/lang/String;",
                                    "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->rect:Landroid/graphics/RectF;",
                                    "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->gravity:I",
                                    "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->paint:Landroid/graphics/Paint;")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
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
            return (String)
                    ObjectMethods.bootstrap(
                                    MethodHandles.lookup(),
                                    "toString",
                                    MethodType.methodType(String.class, GlyphDrawable.class),
                                    GlyphDrawable.class,
                                    "text;rect;gravity;paint",
                                    "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->text:Ljava/lang/String;",
                                    "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->rect:Landroid/graphics/RectF;",
                                    "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->gravity:I",
                                    "FIELD:Landroid/hardware/input/KeyboardLayoutPreviewDrawable$GlyphDrawable;->paint:Landroid/graphics/Paint;")
                            .dynamicInvoker()
                            .invoke(this) /* invoke-custom */;
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
            this.mKeyPadding =
                    (int)
                            TypedValue.applyDimension(
                                    1, 3.0f, context.getResources().getDisplayMetrics());
            this.mKeyboardPadding =
                    (int)
                            TypedValue.applyDimension(
                                    1, 10.0f, context.getResources().getDisplayMetrics());
            this.mKeyRadius =
                    (int)
                            TypedValue.applyDimension(
                                    1, 5.0f, context.getResources().getDisplayMetrics());
            this.mBackgroundRadius =
                    (int)
                            TypedValue.applyDimension(
                                    1, 10.0f, context.getResources().getDisplayMetrics());
            this.mSpToPxMultiplier =
                    TypedValue.applyDimension(2, 1.0f, context.getResources().getDisplayMetrics());
            this.mTextPadding =
                    TypedValue.applyDimension(1, 1.0f, context.getResources().getDisplayMetrics());
            boolean isDark = (context.getResources().getConfiguration().uiMode & 48) == 32;
            int typingKeyColor = context.getColor(isDark ? 17170625 : 17170543);
            int specialKeyColor = context.getColor(isDark ? 17170471 : 17170530);
            int primaryGlyphColor = context.getColor(isDark ? 17170584 : 17170541);
            int secondaryGlyphColor = context.getColor(isDark ? 17170594 : 17170551);
            int backgroundColor = context.getColor(isDark ? 17170587 : 17170544);
            this.mPrimaryGlyphPaint =
                    KeyboardLayoutPreviewDrawable.createTextPaint(
                            primaryGlyphColor,
                            this.mSpToPxMultiplier * 10.0f,
                            Typeface.create(Typeface.SANS_SERIF, 1));
            this.mSecondaryGlyphPaint =
                    KeyboardLayoutPreviewDrawable.createTextPaint(
                            secondaryGlyphColor,
                            this.mSpToPxMultiplier * 10.0f,
                            Typeface.create(Typeface.SANS_SERIF, 0));
            this.mFontMetrics = this.mPrimaryGlyphPaint.getFontMetrics();
            this.mTypingKeyPaint = KeyboardLayoutPreviewDrawable.createFillPaint(typingKeyColor);
            this.mSpecialKeyPaint = KeyboardLayoutPreviewDrawable.createFillPaint(specialKeyColor);
            this.mBackgroundPaint = KeyboardLayoutPreviewDrawable.createFillPaint(backgroundColor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void calculateBestTextSizeForKey(float keyHeight) {
            int textSize = (int) (this.mSpToPxMultiplier * 10.0f);
            while (true) {
                textSize++;
                if (textSize >= this.mSpToPxMultiplier * 20.0f) {
                    break;
                }
                updateTextSize(textSize);
                if ((this.mFontMetrics.bottom - this.mFontMetrics.top) + (this.mTextPadding * 3.0f)
                        > keyHeight / 2.0f) {
                    textSize--;
                    break;
                }
            }
            updateTextSize(textSize);
        }

        private void updateTextSize(float textSize) {
            this.mPrimaryGlyphPaint.setTextSize(textSize);
            this.mSecondaryGlyphPaint.setTextSize(textSize);
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
    public static Paint createTextPaint(int textColor, float textSize, Typeface typeface) {
        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(textSize);
        paint.setTypeface(typeface);
        return paint;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Paint createFillPaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Paint createGreyedOutPaint(Paint paint) {
        Paint result = new Paint(paint);
        result.setAlpha(100);
        return result;
    }
}
