package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import androidx.collection.LongSparseArray;
import androidx.collection.LongSparseArrayKt;
import androidx.collection.SparseArrayCompat;
import androidx.collection.SparseArrayCompatKt;
import androidx.collection.internal.ContainerHelpersKt;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.content.ContentGroup;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ColorKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TextKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class TextLayer extends BaseLayer {
    private final LongSparseArray<String> codePointCache;
    private ColorKeyframeAnimation colorAnimation;
    private ValueCallbackKeyframeAnimation colorCallbackAnimation;
    private final LottieComposition composition;
    private final Map<FontCharacter, List<ContentGroup>> contentsForCharacter;
    private final Paint fillPaint;
    private final LottieDrawable lottieDrawable;
    private final Matrix matrix;
    private final RectF rectF;
    private final StringBuilder stringBuilder;
    private ColorKeyframeAnimation strokeColorAnimation;
    private ValueCallbackKeyframeAnimation strokeColorCallbackAnimation;
    private final Paint strokePaint;
    private FloatKeyframeAnimation strokeWidthAnimation;
    private ValueCallbackKeyframeAnimation strokeWidthCallbackAnimation;
    private final TextKeyframeAnimation textAnimation;
    private ValueCallbackKeyframeAnimation textSizeCallbackAnimation;
    private FloatKeyframeAnimation trackingAnimation;
    private ValueCallbackKeyframeAnimation trackingCallbackAnimation;

    TextLayer(LottieDrawable lottieDrawable, Layer layer) {
        super(lottieDrawable, layer);
        AnimatableFloatValue animatableFloatValue;
        AnimatableFloatValue animatableFloatValue2;
        AnimatableColorValue animatableColorValue;
        AnimatableColorValue animatableColorValue2;
        this.stringBuilder = new StringBuilder(2);
        this.rectF = new RectF();
        this.matrix = new Matrix();
        this.fillPaint = new Paint() { // from class: com.airbnb.lottie.model.layer.TextLayer.1
            {
                setStyle(Paint.Style.FILL);
            }
        };
        this.strokePaint = new Paint() { // from class: com.airbnb.lottie.model.layer.TextLayer.2
            {
                setStyle(Paint.Style.STROKE);
            }
        };
        this.contentsForCharacter = new HashMap();
        this.codePointCache = new LongSparseArray<>();
        this.lottieDrawable = lottieDrawable;
        this.composition = layer.getComposition();
        TextKeyframeAnimation createAnimation = layer.getText().createAnimation();
        this.textAnimation = createAnimation;
        createAnimation.addUpdateListener(this);
        addAnimation(createAnimation);
        AnimatableTextProperties textProperties = layer.getTextProperties();
        if (textProperties != null && (animatableColorValue2 = textProperties.color) != null) {
            BaseKeyframeAnimation<Integer, Integer> createAnimation2 = animatableColorValue2.createAnimation();
            this.colorAnimation = (ColorKeyframeAnimation) createAnimation2;
            createAnimation2.addUpdateListener(this);
            addAnimation(this.colorAnimation);
        }
        if (textProperties != null && (animatableColorValue = textProperties.stroke) != null) {
            BaseKeyframeAnimation<Integer, Integer> createAnimation3 = animatableColorValue.createAnimation();
            this.strokeColorAnimation = (ColorKeyframeAnimation) createAnimation3;
            createAnimation3.addUpdateListener(this);
            addAnimation(this.strokeColorAnimation);
        }
        if (textProperties != null && (animatableFloatValue2 = textProperties.strokeWidth) != null) {
            BaseKeyframeAnimation<Float, Float> createAnimation4 = animatableFloatValue2.createAnimation();
            this.strokeWidthAnimation = (FloatKeyframeAnimation) createAnimation4;
            createAnimation4.addUpdateListener(this);
            addAnimation(this.strokeWidthAnimation);
        }
        if (textProperties == null || (animatableFloatValue = textProperties.tracking) == null) {
            return;
        }
        BaseKeyframeAnimation<Float, Float> createAnimation5 = animatableFloatValue.createAnimation();
        this.trackingAnimation = (FloatKeyframeAnimation) createAnimation5;
        createAnimation5.addUpdateListener(this);
        addAnimation(this.trackingAnimation);
    }

    private static void drawCharacter(String str, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawText(str, 0, str.length(), 0.0f, 0.0f, paint);
    }

    private static void drawGlyph(Path path, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawPath(path, paint);
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.KeyPathElement
    public final void addValueCallback(LottieValueCallback lottieValueCallback, Object obj) {
        super.addValueCallback(lottieValueCallback, obj);
        if (obj == LottieProperty.COLOR) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorCallbackAnimation;
            if (valueCallbackKeyframeAnimation != null) {
                removeAnimation(valueCallbackKeyframeAnimation);
            }
            if (lottieValueCallback == null) {
                this.colorCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
            this.colorCallbackAnimation = valueCallbackKeyframeAnimation2;
            valueCallbackKeyframeAnimation2.addUpdateListener(this);
            addAnimation(this.colorCallbackAnimation);
            return;
        }
        if (obj == LottieProperty.STROKE_COLOR) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation3 = this.strokeColorCallbackAnimation;
            if (valueCallbackKeyframeAnimation3 != null) {
                removeAnimation(valueCallbackKeyframeAnimation3);
            }
            if (lottieValueCallback == null) {
                this.strokeColorCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation4 = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
            this.strokeColorCallbackAnimation = valueCallbackKeyframeAnimation4;
            valueCallbackKeyframeAnimation4.addUpdateListener(this);
            addAnimation(this.strokeColorCallbackAnimation);
            return;
        }
        if (obj == LottieProperty.STROKE_WIDTH) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation5 = this.strokeWidthCallbackAnimation;
            if (valueCallbackKeyframeAnimation5 != null) {
                removeAnimation(valueCallbackKeyframeAnimation5);
            }
            if (lottieValueCallback == null) {
                this.strokeWidthCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation6 = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
            this.strokeWidthCallbackAnimation = valueCallbackKeyframeAnimation6;
            valueCallbackKeyframeAnimation6.addUpdateListener(this);
            addAnimation(this.strokeWidthCallbackAnimation);
            return;
        }
        if (obj == LottieProperty.TEXT_TRACKING) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation7 = this.trackingCallbackAnimation;
            if (valueCallbackKeyframeAnimation7 != null) {
                removeAnimation(valueCallbackKeyframeAnimation7);
            }
            if (lottieValueCallback == null) {
                this.trackingCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation8 = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
            this.trackingCallbackAnimation = valueCallbackKeyframeAnimation8;
            valueCallbackKeyframeAnimation8.addUpdateListener(this);
            addAnimation(this.trackingCallbackAnimation);
            return;
        }
        if (obj == LottieProperty.TEXT_SIZE) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation9 = this.textSizeCallbackAnimation;
            if (valueCallbackKeyframeAnimation9 != null) {
                removeAnimation(valueCallbackKeyframeAnimation9);
            }
            if (lottieValueCallback == null) {
                this.textSizeCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation10 = new ValueCallbackKeyframeAnimation(lottieValueCallback, null);
            this.textSizeCallbackAnimation = valueCallbackKeyframeAnimation10;
            valueCallbackKeyframeAnimation10.addUpdateListener(this);
            addAnimation(this.textSizeCallbackAnimation);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:98:0x03a9  */
    @Override // com.airbnb.lottie.model.layer.BaseLayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void drawLayer(Canvas canvas, Matrix matrix, int i) {
        float floatValue;
        int size;
        int i2;
        List list;
        int i3;
        float f;
        float f2;
        int i4;
        String sb;
        Object obj;
        LottieComposition lottieComposition;
        DocumentData.Justification justification;
        LottieDrawable lottieDrawable;
        List list2;
        Paint paint;
        DocumentData documentData;
        int i5;
        int i6;
        float floatValue2;
        Paint paint2;
        Paint paint3;
        LottieComposition lottieComposition2;
        Font font;
        LottieDrawable lottieDrawable2;
        canvas.save();
        LottieDrawable lottieDrawable3 = this.lottieDrawable;
        if (!lottieDrawable3.useTextGlyphs()) {
            canvas.concat(matrix);
        }
        DocumentData value = this.textAnimation.getValue();
        LottieComposition lottieComposition3 = this.composition;
        Font font2 = lottieComposition3.getFonts().get(value.fontName);
        if (font2 == null) {
            canvas.restore();
            return;
        }
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorCallbackAnimation;
        Paint paint4 = this.fillPaint;
        if (valueCallbackKeyframeAnimation != null) {
            paint4.setColor(((Integer) valueCallbackKeyframeAnimation.getValue()).intValue());
        } else {
            ColorKeyframeAnimation colorKeyframeAnimation = this.colorAnimation;
            if (colorKeyframeAnimation != null) {
                paint4.setColor(colorKeyframeAnimation.getValue().intValue());
            } else {
                paint4.setColor(value.color);
            }
        }
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = this.strokeColorCallbackAnimation;
        Paint paint5 = this.strokePaint;
        if (valueCallbackKeyframeAnimation2 != null) {
            paint5.setColor(((Integer) valueCallbackKeyframeAnimation2.getValue()).intValue());
        } else {
            ColorKeyframeAnimation colorKeyframeAnimation2 = this.strokeColorAnimation;
            if (colorKeyframeAnimation2 != null) {
                paint5.setColor(colorKeyframeAnimation2.getValue().intValue());
            } else {
                paint5.setColor(value.strokeColor);
            }
        }
        TransformKeyframeAnimation transformKeyframeAnimation = this.transform;
        int intValue = ((transformKeyframeAnimation.getOpacity() == null ? 100 : transformKeyframeAnimation.getOpacity().getValue().intValue()) * 255) / 100;
        paint4.setAlpha(intValue);
        paint5.setAlpha(intValue);
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation3 = this.strokeWidthCallbackAnimation;
        if (valueCallbackKeyframeAnimation3 != null) {
            paint5.setStrokeWidth(((Float) valueCallbackKeyframeAnimation3.getValue()).floatValue());
        } else {
            FloatKeyframeAnimation floatKeyframeAnimation = this.strokeWidthAnimation;
            if (floatKeyframeAnimation != null) {
                paint5.setStrokeWidth(floatKeyframeAnimation.getValue().floatValue());
            } else {
                paint5.setStrokeWidth(Utils.dpScale() * value.strokeWidth * Utils.getScale(matrix));
            }
        }
        boolean useTextGlyphs = lottieDrawable3.useTextGlyphs();
        boolean z = value.strokeOverFill;
        DocumentData.Justification justification2 = value.justification;
        int i7 = value.tracking;
        float f3 = value.lineHeight;
        int i8 = i7;
        float f4 = value.size;
        String str = value.text;
        if (useTextGlyphs) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation4 = this.textSizeCallbackAnimation;
            float floatValue3 = (valueCallbackKeyframeAnimation4 != null ? ((Float) valueCallbackKeyframeAnimation4.getValue()).floatValue() : f4) / 100.0f;
            Paint paint6 = paint5;
            float scale = Utils.getScale(matrix);
            float dpScale = Utils.dpScale() * f3;
            List asList = Arrays.asList(str.replaceAll("\r\n", "\r").replaceAll("\n", "\r").split("\r"));
            int size2 = asList.size();
            int i9 = 0;
            while (i9 < size2) {
                String str2 = (String) asList.get(i9);
                Paint paint7 = paint4;
                List list3 = asList;
                int i10 = 0;
                float f5 = 0.0f;
                while (i10 < str2.length()) {
                    boolean z2 = z;
                    DocumentData documentData2 = value;
                    int hashFor = FontCharacter.hashFor(str2.charAt(i10), font2.getFamily(), font2.getStyle());
                    SparseArrayCompat<FontCharacter> characters = lottieComposition3.getCharacters();
                    characters.getClass();
                    FontCharacter fontCharacter = (FontCharacter) SparseArrayCompatKt.commonGet(characters, hashFor);
                    if (fontCharacter == null) {
                        lottieDrawable2 = lottieDrawable3;
                        lottieComposition2 = lottieComposition3;
                        font = font2;
                    } else {
                        lottieComposition2 = lottieComposition3;
                        font = font2;
                        lottieDrawable2 = lottieDrawable3;
                        f5 = (float) ((fontCharacter.getWidth() * floatValue3 * Utils.dpScale() * scale) + f5);
                    }
                    i10++;
                    lottieComposition3 = lottieComposition2;
                    font2 = font;
                    z = z2;
                    value = documentData2;
                    lottieDrawable3 = lottieDrawable2;
                }
                LottieDrawable lottieDrawable4 = lottieDrawable3;
                DocumentData documentData3 = value;
                LottieComposition lottieComposition4 = lottieComposition3;
                boolean z3 = z;
                Font font3 = font2;
                canvas.save();
                int ordinal = justification2.ordinal();
                if (ordinal == 1) {
                    canvas.translate(-f5, 0.0f);
                } else if (ordinal == 2) {
                    canvas.translate((-f5) / 2.0f, 0.0f);
                }
                canvas.translate(0.0f, (i9 * dpScale) - (((size2 - 1) * dpScale) / 2.0f));
                int i11 = 0;
                while (i11 < str2.length()) {
                    int hashFor2 = FontCharacter.hashFor(str2.charAt(i11), font3.getFamily(), font3.getStyle());
                    SparseArrayCompat<FontCharacter> characters2 = lottieComposition4.getCharacters();
                    characters2.getClass();
                    FontCharacter fontCharacter2 = (FontCharacter) SparseArrayCompatKt.commonGet(characters2, hashFor2);
                    if (fontCharacter2 == null) {
                        lottieComposition = lottieComposition4;
                        justification = justification2;
                        i6 = i8;
                        paint = paint7;
                        lottieDrawable = lottieDrawable4;
                        documentData = documentData3;
                        i5 = size2;
                    } else {
                        HashMap hashMap = (HashMap) this.contentsForCharacter;
                        if (hashMap.containsKey(fontCharacter2)) {
                            list2 = (List) hashMap.get(fontCharacter2);
                            lottieComposition = lottieComposition4;
                            justification = justification2;
                            lottieDrawable = lottieDrawable4;
                        } else {
                            List<ShapeGroup> shapes = fontCharacter2.getShapes();
                            int size3 = shapes.size();
                            ArrayList arrayList = new ArrayList(size3);
                            lottieComposition = lottieComposition4;
                            int i12 = 0;
                            while (i12 < size3) {
                                arrayList.add(new ContentGroup(lottieDrawable4, this, shapes.get(i12)));
                                i12++;
                                size3 = size3;
                                shapes = shapes;
                                justification2 = justification2;
                            }
                            justification = justification2;
                            lottieDrawable = lottieDrawable4;
                            hashMap.put(fontCharacter2, arrayList);
                            list2 = arrayList;
                        }
                        int i13 = 0;
                        while (i13 < list2.size()) {
                            Path path = ((ContentGroup) list2.get(i13)).getPath();
                            path.computeBounds(this.rectF, false);
                            Matrix matrix2 = this.matrix;
                            matrix2.set(matrix);
                            List list4 = list2;
                            DocumentData documentData4 = documentData3;
                            int i14 = size2;
                            matrix2.preTranslate(0.0f, (-documentData4.baselineShift) * Utils.dpScale());
                            matrix2.preScale(floatValue3, floatValue3);
                            path.transform(matrix2);
                            if (z3) {
                                paint3 = paint7;
                                drawGlyph(path, paint3, canvas);
                                paint2 = paint6;
                                drawGlyph(path, paint2, canvas);
                            } else {
                                paint2 = paint6;
                                paint3 = paint7;
                                drawGlyph(path, paint2, canvas);
                                drawGlyph(path, paint3, canvas);
                            }
                            i13++;
                            paint7 = paint3;
                            paint6 = paint2;
                            size2 = i14;
                            list2 = list4;
                            documentData3 = documentData4;
                        }
                        paint = paint7;
                        documentData = documentData3;
                        i5 = size2;
                        float dpScale2 = Utils.dpScale() * ((float) fontCharacter2.getWidth()) * floatValue3 * scale;
                        i6 = i8;
                        float f6 = i6 / 10.0f;
                        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation5 = this.trackingCallbackAnimation;
                        if (valueCallbackKeyframeAnimation5 != null) {
                            floatValue2 = ((Float) valueCallbackKeyframeAnimation5.getValue()).floatValue();
                        } else {
                            FloatKeyframeAnimation floatKeyframeAnimation2 = this.trackingAnimation;
                            if (floatKeyframeAnimation2 != null) {
                                floatValue2 = floatKeyframeAnimation2.getValue().floatValue();
                            }
                            canvas.translate((f6 * scale) + dpScale2, 0.0f);
                        }
                        f6 += floatValue2;
                        canvas.translate((f6 * scale) + dpScale2, 0.0f);
                    }
                    i11++;
                    i8 = i6;
                    paint7 = paint;
                    size2 = i5;
                    lottieComposition4 = lottieComposition;
                    documentData3 = documentData;
                    lottieDrawable4 = lottieDrawable;
                    justification2 = justification;
                }
                LottieComposition lottieComposition5 = lottieComposition4;
                canvas.restore();
                i9++;
                paint4 = paint7;
                lottieDrawable3 = lottieDrawable4;
                font2 = font3;
                asList = list3;
                z = z3;
                lottieComposition3 = lottieComposition5;
                value = documentData3;
                justification2 = justification2;
            }
        } else {
            Utils.getScale(matrix);
            Typeface typeface = lottieDrawable3.getTypeface(font2.getFamily(), font2.getStyle());
            if (typeface != null) {
                paint4.setTypeface(typeface);
                ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation6 = this.textSizeCallbackAnimation;
                float floatValue4 = valueCallbackKeyframeAnimation6 != null ? ((Float) valueCallbackKeyframeAnimation6.getValue()).floatValue() : f4;
                paint4.setTextSize(Utils.dpScale() * floatValue4);
                paint5.setTypeface(paint4.getTypeface());
                paint5.setTextSize(paint4.getTextSize());
                float dpScale3 = Utils.dpScale() * f3;
                float f7 = i8 / 10.0f;
                ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation7 = this.trackingCallbackAnimation;
                if (valueCallbackKeyframeAnimation7 != null) {
                    floatValue = ((Float) valueCallbackKeyframeAnimation7.getValue()).floatValue();
                } else {
                    FloatKeyframeAnimation floatKeyframeAnimation3 = this.trackingAnimation;
                    if (floatKeyframeAnimation3 != null) {
                        floatValue = floatKeyframeAnimation3.getValue().floatValue();
                    }
                    float dpScale4 = ((Utils.dpScale() * f7) * floatValue4) / 100.0f;
                    List asList2 = Arrays.asList(str.replaceAll("\r\n", "\r").replaceAll("\n", "\r").split("\r"));
                    size = asList2.size();
                    i2 = 0;
                    while (i2 < size) {
                        String str3 = (String) asList2.get(i2);
                        float length = ((str3.length() - 1) * dpScale4) + paint5.measureText(str3);
                        canvas.save();
                        int ordinal2 = justification2.ordinal();
                        if (ordinal2 == 1) {
                            canvas.translate(-length, 0.0f);
                        } else if (ordinal2 == 2) {
                            canvas.translate((-length) / 2.0f, 0.0f);
                        }
                        canvas.translate(0.0f, (i2 * dpScale3) - (((size - 1) * dpScale3) / 2.0f));
                        int i15 = 0;
                        while (i15 < str3.length()) {
                            int codePointAt = str3.codePointAt(i15);
                            int charCount = Character.charCount(codePointAt) + i15;
                            while (charCount < str3.length()) {
                                int codePointAt2 = str3.codePointAt(charCount);
                                if (!(Character.getType(codePointAt2) == 16 || Character.getType(codePointAt2) == 27 || Character.getType(codePointAt2) == 6 || Character.getType(codePointAt2) == 28 || Character.getType(codePointAt2) == 19)) {
                                    break;
                                }
                                charCount += Character.charCount(codePointAt2);
                                codePointAt = (codePointAt * 31) + codePointAt2;
                            }
                            long j = codePointAt;
                            LongSparseArray<String> longSparseArray = this.codePointCache;
                            if (longSparseArray.garbage) {
                                int i16 = longSparseArray.size;
                                list = asList2;
                                long[] jArr = longSparseArray.keys;
                                i3 = size;
                                Object[] objArr = longSparseArray.values;
                                f = dpScale3;
                                i4 = i2;
                                int i17 = 0;
                                int i18 = 0;
                                while (i18 < i16) {
                                    int i19 = i16;
                                    Object obj2 = objArr[i18];
                                    float f8 = dpScale4;
                                    obj = LongSparseArrayKt.DELETED;
                                    if (obj2 != obj) {
                                        if (i18 != i17) {
                                            jArr[i17] = jArr[i18];
                                            objArr[i17] = obj2;
                                            objArr[i18] = null;
                                        }
                                        i17++;
                                    }
                                    i18++;
                                    i16 = i19;
                                    dpScale4 = f8;
                                }
                                f2 = dpScale4;
                                longSparseArray.garbage = false;
                                longSparseArray.size = i17;
                            } else {
                                list = asList2;
                                i3 = size;
                                f = dpScale3;
                                f2 = dpScale4;
                                i4 = i2;
                            }
                            if (ContainerHelpersKt.binarySearch(longSparseArray.keys, longSparseArray.size, j) >= 0) {
                                sb = longSparseArray.get(j);
                            } else {
                                StringBuilder sb2 = this.stringBuilder;
                                sb2.setLength(0);
                                int i20 = i15;
                                while (i20 < charCount) {
                                    int codePointAt3 = str3.codePointAt(i20);
                                    sb2.appendCodePoint(codePointAt3);
                                    i20 += Character.charCount(codePointAt3);
                                }
                                sb = sb2.toString();
                                longSparseArray.put(j, sb);
                            }
                            i15 += sb.length();
                            if (z) {
                                drawCharacter(sb, paint4, canvas);
                                drawCharacter(sb, paint5, canvas);
                            } else {
                                drawCharacter(sb, paint5, canvas);
                                drawCharacter(sb, paint4, canvas);
                            }
                            canvas.translate(paint4.measureText(sb) + f2, 0.0f);
                            asList2 = list;
                            size = i3;
                            dpScale3 = f;
                            i2 = i4;
                            dpScale4 = f2;
                        }
                        canvas.restore();
                        i2++;
                        size = size;
                        dpScale3 = dpScale3;
                    }
                }
                f7 += floatValue;
                float dpScale42 = ((Utils.dpScale() * f7) * floatValue4) / 100.0f;
                List asList22 = Arrays.asList(str.replaceAll("\r\n", "\r").replaceAll("\n", "\r").split("\r"));
                size = asList22.size();
                i2 = 0;
                while (i2 < size) {
                }
            }
        }
        canvas.restore();
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        super.getBounds(rectF, matrix, z);
        LottieComposition lottieComposition = this.composition;
        rectF.set(0.0f, 0.0f, lottieComposition.getBounds().width(), lottieComposition.getBounds().height());
    }
}
