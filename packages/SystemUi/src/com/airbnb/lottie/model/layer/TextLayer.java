package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import androidx.collection.LongSparseArray;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.content.ContentGroup;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ColorKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TextKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.manager.FontAssetManager;
import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.MutablePair;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TextLayer extends BaseLayer {
    public final LongSparseArray codePointCache;
    public final ColorKeyframeAnimation colorAnimation;
    public ValueCallbackKeyframeAnimation colorCallbackAnimation;
    public final LottieComposition composition;
    public final Map contentsForCharacter;
    public final C06001 fillPaint;
    public final LottieDrawable lottieDrawable;
    public final Matrix matrix;
    public final RectF rectF;
    public final StringBuilder stringBuilder;
    public final ColorKeyframeAnimation strokeColorAnimation;
    public ValueCallbackKeyframeAnimation strokeColorCallbackAnimation;
    public final C06012 strokePaint;
    public final FloatKeyframeAnimation strokeWidthAnimation;
    public ValueCallbackKeyframeAnimation strokeWidthCallbackAnimation;
    public final TextKeyframeAnimation textAnimation;
    public ValueCallbackKeyframeAnimation textSizeCallbackAnimation;
    public final List textSubLines;
    public final FloatKeyframeAnimation trackingAnimation;
    public ValueCallbackKeyframeAnimation trackingCallbackAnimation;
    public ValueCallbackKeyframeAnimation typefaceCallbackAnimation;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.airbnb.lottie.model.layer.TextLayer$3 */
    public abstract /* synthetic */ class AbstractC06023 {
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification;

        static {
            int[] iArr = new int[DocumentData.Justification.values().length];
            $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification = iArr;
            try {
                iArr[DocumentData.Justification.LEFT_ALIGN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[DocumentData.Justification.RIGHT_ALIGN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[DocumentData.Justification.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TextSubLine {
        public String text;
        public float width;

        private TextSubLine() {
            this.text = "";
            this.width = 0.0f;
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.airbnb.lottie.model.layer.TextLayer$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.airbnb.lottie.model.layer.TextLayer$2] */
    public TextLayer(LottieDrawable lottieDrawable, Layer layer) {
        super(lottieDrawable, layer);
        AnimatableFloatValue animatableFloatValue;
        AnimatableFloatValue animatableFloatValue2;
        AnimatableColorValue animatableColorValue;
        AnimatableColorValue animatableColorValue2;
        this.stringBuilder = new StringBuilder(2);
        this.rectF = new RectF();
        this.matrix = new Matrix();
        int i = 1;
        this.fillPaint = new Paint(this, i) { // from class: com.airbnb.lottie.model.layer.TextLayer.1
            {
                setStyle(Paint.Style.FILL);
            }
        };
        this.strokePaint = new Paint(this, i) { // from class: com.airbnb.lottie.model.layer.TextLayer.2
            {
                setStyle(Paint.Style.STROKE);
            }
        };
        this.contentsForCharacter = new HashMap();
        this.codePointCache = new LongSparseArray();
        this.textSubLines = new ArrayList();
        this.lottieDrawable = lottieDrawable;
        this.composition = layer.composition;
        TextKeyframeAnimation textKeyframeAnimation = new TextKeyframeAnimation(layer.text.keyframes);
        this.textAnimation = textKeyframeAnimation;
        textKeyframeAnimation.addUpdateListener(this);
        addAnimation(textKeyframeAnimation);
        AnimatableTextProperties animatableTextProperties = layer.textProperties;
        if (animatableTextProperties != null && (animatableColorValue2 = animatableTextProperties.color) != null) {
            BaseKeyframeAnimation createAnimation = animatableColorValue2.createAnimation();
            this.colorAnimation = (ColorKeyframeAnimation) createAnimation;
            createAnimation.addUpdateListener(this);
            addAnimation(createAnimation);
        }
        if (animatableTextProperties != null && (animatableColorValue = animatableTextProperties.stroke) != null) {
            BaseKeyframeAnimation createAnimation2 = animatableColorValue.createAnimation();
            this.strokeColorAnimation = (ColorKeyframeAnimation) createAnimation2;
            createAnimation2.addUpdateListener(this);
            addAnimation(createAnimation2);
        }
        if (animatableTextProperties != null && (animatableFloatValue2 = animatableTextProperties.strokeWidth) != null) {
            BaseKeyframeAnimation createAnimation3 = animatableFloatValue2.createAnimation();
            this.strokeWidthAnimation = (FloatKeyframeAnimation) createAnimation3;
            createAnimation3.addUpdateListener(this);
            addAnimation(createAnimation3);
        }
        if (animatableTextProperties == null || (animatableFloatValue = animatableTextProperties.tracking) == null) {
            return;
        }
        BaseKeyframeAnimation createAnimation4 = animatableFloatValue.createAnimation();
        this.trackingAnimation = (FloatKeyframeAnimation) createAnimation4;
        createAnimation4.addUpdateListener(this);
        addAnimation(createAnimation4);
    }

    public static void drawCharacter(String str, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawText(str, 0, str.length(), 0.0f, 0.0f, paint);
    }

    public static void drawGlyph(Path path, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawPath(path, paint);
    }

    public static List getTextLines(String str) {
        return Arrays.asList(str.replaceAll("\r\n", "\r").replaceAll("\u0003", "\r").replaceAll("\n", "\r").split("\r"));
    }

    public static void offsetCanvas(Canvas canvas, DocumentData documentData, int i, float f) {
        PointF pointF = documentData.boxPosition;
        PointF pointF2 = documentData.boxSize;
        float dpScale = Utils.dpScale();
        float f2 = (i * documentData.lineHeight * dpScale) + (pointF == null ? 0.0f : (documentData.lineHeight * dpScale) + pointF.y);
        float f3 = pointF == null ? 0.0f : pointF.x;
        float f4 = pointF2 != null ? pointF2.x : 0.0f;
        int i2 = AbstractC06023.$SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[documentData.justification.ordinal()];
        if (i2 == 1) {
            canvas.translate(f3, f2);
        } else if (i2 == 2) {
            canvas.translate((f3 + f4) - f, f2);
        } else {
            if (i2 != 3) {
                return;
            }
            canvas.translate(((f4 / 2.0f) + f3) - (f / 2.0f), f2);
        }
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
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
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
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation4 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
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
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation6 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
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
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation8 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
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
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation10 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.textSizeCallbackAnimation = valueCallbackKeyframeAnimation10;
            valueCallbackKeyframeAnimation10.addUpdateListener(this);
            addAnimation(this.textSizeCallbackAnimation);
            return;
        }
        if (obj != LottieProperty.TYPEFACE) {
            if (obj == LottieProperty.TEXT) {
                TextKeyframeAnimation textKeyframeAnimation = this.textAnimation;
                textKeyframeAnimation.getClass();
                textKeyframeAnimation.setValueCallback(new LottieValueCallback(textKeyframeAnimation, new LottieFrameInfo(), lottieValueCallback, new DocumentData()) { // from class: com.airbnb.lottie.animation.keyframe.TextKeyframeAnimation.1
                    public final /* synthetic */ DocumentData val$documentData;
                    public final /* synthetic */ LottieFrameInfo val$stringFrameInfo;
                    public final /* synthetic */ LottieValueCallback val$valueCallback;

                    public C05961(TextKeyframeAnimation textKeyframeAnimation2, LottieFrameInfo lottieFrameInfo, LottieValueCallback lottieValueCallback2, DocumentData documentData) {
                        this.val$stringFrameInfo = lottieFrameInfo;
                        this.val$valueCallback = lottieValueCallback2;
                        this.val$documentData = documentData;
                    }

                    @Override // com.airbnb.lottie.value.LottieValueCallback
                    public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                        float f = lottieFrameInfo.startFrame;
                        float f2 = lottieFrameInfo.endFrame;
                        String str = ((DocumentData) lottieFrameInfo.startValue).text;
                        String str2 = ((DocumentData) lottieFrameInfo.endValue).text;
                        float f3 = lottieFrameInfo.linearKeyframeProgress;
                        float f4 = lottieFrameInfo.interpolatedKeyframeProgress;
                        float f5 = lottieFrameInfo.overallProgress;
                        LottieFrameInfo lottieFrameInfo2 = this.val$stringFrameInfo;
                        lottieFrameInfo2.startFrame = f;
                        lottieFrameInfo2.endFrame = f2;
                        lottieFrameInfo2.startValue = str;
                        lottieFrameInfo2.endValue = str2;
                        lottieFrameInfo2.linearKeyframeProgress = f3;
                        lottieFrameInfo2.interpolatedKeyframeProgress = f4;
                        lottieFrameInfo2.overallProgress = f5;
                        String str3 = (String) this.val$valueCallback.getValue(lottieFrameInfo2);
                        DocumentData documentData = (DocumentData) (lottieFrameInfo.interpolatedKeyframeProgress == 1.0f ? lottieFrameInfo.endValue : lottieFrameInfo.startValue);
                        String str4 = documentData.fontName;
                        float f6 = documentData.size;
                        DocumentData.Justification justification = documentData.justification;
                        int i = documentData.tracking;
                        float f7 = documentData.lineHeight;
                        float f8 = documentData.baselineShift;
                        int i2 = documentData.color;
                        int i3 = documentData.strokeColor;
                        float f9 = documentData.strokeWidth;
                        boolean z = documentData.strokeOverFill;
                        PointF pointF = documentData.boxPosition;
                        PointF pointF2 = documentData.boxSize;
                        DocumentData documentData2 = this.val$documentData;
                        documentData2.text = str3;
                        documentData2.fontName = str4;
                        documentData2.size = f6;
                        documentData2.justification = justification;
                        documentData2.tracking = i;
                        documentData2.lineHeight = f7;
                        documentData2.baselineShift = f8;
                        documentData2.color = i2;
                        documentData2.strokeColor = i3;
                        documentData2.strokeWidth = f9;
                        documentData2.strokeOverFill = z;
                        documentData2.boxPosition = pointF;
                        documentData2.boxSize = pointF2;
                        return documentData2;
                    }
                });
                return;
            }
            return;
        }
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation11 = this.typefaceCallbackAnimation;
        if (valueCallbackKeyframeAnimation11 != null) {
            removeAnimation(valueCallbackKeyframeAnimation11);
        }
        if (lottieValueCallback2 == null) {
            this.typefaceCallbackAnimation = null;
            return;
        }
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation12 = new ValueCallbackKeyframeAnimation(lottieValueCallback2);
        this.typefaceCallbackAnimation = valueCallbackKeyframeAnimation12;
        valueCallbackKeyframeAnimation12.addUpdateListener(this);
        addAnimation(this.typefaceCallbackAnimation);
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0390  */
    @Override // com.airbnb.lottie.model.layer.BaseLayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void drawLayer(Canvas canvas, Matrix matrix, int i) {
        Font font;
        Typeface typeface;
        float floatValue;
        int size;
        int i2;
        int i3;
        Font font2;
        List list;
        int i4;
        String str;
        float floatValue2;
        int i5;
        List list2;
        String str2;
        int i6;
        String str3;
        List list3;
        C06012 c06012;
        C06012 c060122;
        DocumentData documentData = (DocumentData) this.textAnimation.getValue();
        LottieComposition lottieComposition = this.composition;
        Font font3 = (Font) lottieComposition.fonts.get(documentData.fontName);
        if (font3 == null) {
            return;
        }
        canvas.save();
        canvas.concat(matrix);
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = this.colorCallbackAnimation;
        C06001 c06001 = this.fillPaint;
        if (valueCallbackKeyframeAnimation != null) {
            c06001.setColor(((Integer) valueCallbackKeyframeAnimation.getValue()).intValue());
        } else {
            ColorKeyframeAnimation colorKeyframeAnimation = this.colorAnimation;
            if (colorKeyframeAnimation != null) {
                c06001.setColor(((Integer) colorKeyframeAnimation.getValue()).intValue());
            } else {
                c06001.setColor(documentData.color);
            }
        }
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = this.strokeColorCallbackAnimation;
        C06012 c060123 = this.strokePaint;
        if (valueCallbackKeyframeAnimation2 != null) {
            c060123.setColor(((Integer) valueCallbackKeyframeAnimation2.getValue()).intValue());
        } else {
            ColorKeyframeAnimation colorKeyframeAnimation2 = this.strokeColorAnimation;
            if (colorKeyframeAnimation2 != null) {
                c060123.setColor(((Integer) colorKeyframeAnimation2.getValue()).intValue());
            } else {
                c060123.setColor(documentData.strokeColor);
            }
        }
        BaseKeyframeAnimation baseKeyframeAnimation = this.transform.opacity;
        int intValue = ((((baseKeyframeAnimation == null ? 100 : ((Integer) baseKeyframeAnimation.getValue()).intValue()) * 255) / 100) * i) / 255;
        c06001.setAlpha(intValue);
        c060123.setAlpha(intValue);
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation3 = this.strokeWidthCallbackAnimation;
        if (valueCallbackKeyframeAnimation3 != null) {
            c060123.setStrokeWidth(((Float) valueCallbackKeyframeAnimation3.getValue()).floatValue());
        } else {
            FloatKeyframeAnimation floatKeyframeAnimation = this.strokeWidthAnimation;
            if (floatKeyframeAnimation != null) {
                c060123.setStrokeWidth(((Float) floatKeyframeAnimation.getValue()).floatValue());
            } else {
                c060123.setStrokeWidth(Utils.dpScale() * documentData.strokeWidth);
            }
        }
        LottieDrawable lottieDrawable = this.lottieDrawable;
        boolean z = lottieDrawable.composition.characters.size() > 0;
        FloatKeyframeAnimation floatKeyframeAnimation2 = this.trackingAnimation;
        String str4 = font3.style;
        String str5 = font3.family;
        if (z) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation4 = this.textSizeCallbackAnimation;
            float floatValue3 = (valueCallbackKeyframeAnimation4 != null ? ((Float) valueCallbackKeyframeAnimation4.getValue()).floatValue() : documentData.size) / 100.0f;
            Utils.getScale(matrix);
            List textLines = getTextLines(documentData.text);
            int size2 = textLines.size();
            float f = documentData.tracking / 10.0f;
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation5 = this.trackingCallbackAnimation;
            if (valueCallbackKeyframeAnimation5 != null) {
                floatValue2 = ((Float) valueCallbackKeyframeAnimation5.getValue()).floatValue();
            } else {
                if (floatKeyframeAnimation2 != null) {
                    floatValue2 = ((Float) floatKeyframeAnimation2.getValue()).floatValue();
                }
                float f2 = f;
                i5 = 0;
                int i7 = -1;
                float f3 = 0.0f;
                while (i5 < size2) {
                    String str6 = (String) textLines.get(i5);
                    int i8 = size2;
                    PointF pointF = documentData.boxSize;
                    if (pointF != null) {
                        f3 = pointF.x;
                    }
                    int i9 = 0;
                    List list4 = textLines;
                    int i10 = i5;
                    Font font4 = font3;
                    float f4 = floatValue3;
                    C06012 c060124 = c060123;
                    String str7 = str5;
                    List splitGlyphTextIntoLines = splitGlyphTextIntoLines(str6, f3, font3, floatValue3, f2, true);
                    int i11 = 0;
                    while (i11 < splitGlyphTextIntoLines.size()) {
                        TextSubLine textSubLine = (TextSubLine) splitGlyphTextIntoLines.get(i11);
                        int i12 = i7 + 1;
                        canvas.save();
                        offsetCanvas(canvas, documentData, i12, textSubLine.width);
                        String str8 = textSubLine.text;
                        int i13 = i9;
                        while (i13 < str8.length()) {
                            FontCharacter fontCharacter = (FontCharacter) lottieComposition.characters.get(str4.hashCode() + AppInfo$$ExternalSyntheticOutline0.m41m(str7, str8.charAt(i13) * 31, 31));
                            if (fontCharacter == null) {
                                list2 = splitGlyphTextIntoLines;
                                str2 = str8;
                                i6 = i12;
                                str3 = str4;
                                c06012 = c060124;
                            } else {
                                HashMap hashMap = (HashMap) this.contentsForCharacter;
                                if (hashMap.containsKey(fontCharacter)) {
                                    list3 = (List) hashMap.get(fontCharacter);
                                    list2 = splitGlyphTextIntoLines;
                                    str2 = str8;
                                    i6 = i12;
                                    str3 = str4;
                                } else {
                                    list2 = splitGlyphTextIntoLines;
                                    List list5 = fontCharacter.shapes;
                                    str2 = str8;
                                    int size3 = list5.size();
                                    i6 = i12;
                                    ArrayList arrayList = new ArrayList(size3);
                                    str3 = str4;
                                    int i14 = 0;
                                    while (i14 < size3) {
                                        arrayList.add(new ContentGroup(lottieDrawable, this, (ShapeGroup) list5.get(i14), lottieComposition));
                                        i14++;
                                        size3 = size3;
                                        list5 = list5;
                                    }
                                    hashMap.put(fontCharacter, arrayList);
                                    list3 = arrayList;
                                }
                                int i15 = 0;
                                while (i15 < list3.size()) {
                                    Path path = ((ContentGroup) list3.get(i15)).getPath();
                                    path.computeBounds(this.rectF, false);
                                    Matrix matrix2 = this.matrix;
                                    matrix2.reset();
                                    List list6 = list3;
                                    matrix2.preTranslate(0.0f, (-documentData.baselineShift) * Utils.dpScale());
                                    matrix2.preScale(f4, f4);
                                    path.transform(matrix2);
                                    if (documentData.strokeOverFill) {
                                        drawGlyph(path, c06001, canvas);
                                        c060122 = c060124;
                                        drawGlyph(path, c060122, canvas);
                                    } else {
                                        c060122 = c060124;
                                        drawGlyph(path, c060122, canvas);
                                        drawGlyph(path, c06001, canvas);
                                    }
                                    i15++;
                                    c060124 = c060122;
                                    list3 = list6;
                                }
                                c06012 = c060124;
                                canvas.translate((Utils.dpScale() * ((float) fontCharacter.width) * f4) + f2, 0.0f);
                            }
                            i13++;
                            splitGlyphTextIntoLines = list2;
                            i12 = i6;
                            c060124 = c06012;
                            str8 = str2;
                            str4 = str3;
                        }
                        canvas.restore();
                        i11++;
                        i7 = i12;
                        str4 = str4;
                        i9 = 0;
                        splitGlyphTextIntoLines = splitGlyphTextIntoLines;
                    }
                    i5 = i10 + 1;
                    f3 = 0.0f;
                    floatValue3 = f4;
                    str5 = str7;
                    c060123 = c060124;
                    str4 = str4;
                    size2 = i8;
                    textLines = list4;
                    font3 = font4;
                }
            }
            f += floatValue2;
            float f22 = f;
            i5 = 0;
            int i72 = -1;
            float f32 = 0.0f;
            while (i5 < size2) {
            }
        } else {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation6 = this.typefaceCallbackAnimation;
            if (valueCallbackKeyframeAnimation6 == null || (typeface = (Typeface) valueCallbackKeyframeAnimation6.getValue()) == null) {
                FontAssetManager fontAssetManager = lottieDrawable.getFontAssetManager();
                if (fontAssetManager != null) {
                    MutablePair mutablePair = fontAssetManager.tempPair;
                    mutablePair.first = str5;
                    mutablePair.second = str4;
                    HashMap hashMap2 = (HashMap) fontAssetManager.fontMap;
                    Typeface typeface2 = (Typeface) hashMap2.get(mutablePair);
                    if (typeface2 != null) {
                        typeface = typeface2;
                        font = font3;
                    } else {
                        HashMap hashMap3 = (HashMap) fontAssetManager.fontFamilies;
                        Typeface typeface3 = (Typeface) hashMap3.get(str5);
                        if (typeface3 != null) {
                            typeface = typeface3;
                            font = font3;
                        } else {
                            font = font3;
                            Typeface typeface4 = font.typeface;
                            if (typeface4 != null) {
                                typeface = typeface4;
                            } else {
                                typeface = Typeface.createFromAsset(fontAssetManager.assetManager, "fonts/" + str5 + fontAssetManager.defaultFontFileExtension);
                                hashMap3.put(str5, typeface);
                            }
                        }
                        boolean contains = str4.contains("Italic");
                        boolean contains2 = str4.contains("Bold");
                        int i16 = (contains && contains2) ? 3 : contains ? 2 : contains2 ? 1 : 0;
                        if (typeface.getStyle() != i16) {
                            typeface = Typeface.create(typeface, i16);
                        }
                        hashMap2.put(mutablePair, typeface);
                    }
                } else {
                    font = font3;
                    typeface = null;
                }
                if (typeface == null) {
                    typeface = font.typeface;
                }
            } else {
                font = font3;
            }
            if (typeface != null) {
                String str9 = documentData.text;
                c06001.setTypeface(typeface);
                ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation7 = this.textSizeCallbackAnimation;
                float floatValue4 = valueCallbackKeyframeAnimation7 != null ? ((Float) valueCallbackKeyframeAnimation7.getValue()).floatValue() : documentData.size;
                c06001.setTextSize(Utils.dpScale() * floatValue4);
                c060123.setTypeface(c06001.getTypeface());
                c060123.setTextSize(c06001.getTextSize());
                float f5 = documentData.tracking / 10.0f;
                ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation8 = this.trackingCallbackAnimation;
                if (valueCallbackKeyframeAnimation8 != null) {
                    floatValue = ((Float) valueCallbackKeyframeAnimation8.getValue()).floatValue();
                } else {
                    if (floatKeyframeAnimation2 != null) {
                        floatValue = ((Float) floatKeyframeAnimation2.getValue()).floatValue();
                    }
                    float dpScale = ((Utils.dpScale() * f5) * floatValue4) / 100.0f;
                    List textLines2 = getTextLines(str9);
                    size = textLines2.size();
                    i2 = 0;
                    int i17 = -1;
                    while (i2 < size) {
                        String str10 = (String) textLines2.get(i2);
                        PointF pointF2 = documentData.boxSize;
                        int i18 = i2;
                        List splitGlyphTextIntoLines2 = splitGlyphTextIntoLines(str10, pointF2 == null ? 0.0f : pointF2.x, font, 0.0f, dpScale, false);
                        int i19 = 0;
                        while (i19 < splitGlyphTextIntoLines2.size()) {
                            TextSubLine textSubLine2 = (TextSubLine) splitGlyphTextIntoLines2.get(i19);
                            int i20 = i17 + 1;
                            canvas.save();
                            offsetCanvas(canvas, documentData, i20, textSubLine2.width);
                            String str11 = textSubLine2.text;
                            int i21 = 0;
                            while (i21 < str11.length()) {
                                int codePointAt = str11.codePointAt(i21);
                                int charCount = Character.charCount(codePointAt) + i21;
                                List list7 = splitGlyphTextIntoLines2;
                                while (true) {
                                    if (charCount >= str11.length()) {
                                        i3 = i20;
                                        font2 = font;
                                        break;
                                    }
                                    int codePointAt2 = str11.codePointAt(charCount);
                                    i3 = i20;
                                    font2 = font;
                                    if (!(Character.getType(codePointAt2) == 16 || Character.getType(codePointAt2) == 27 || Character.getType(codePointAt2) == 6 || Character.getType(codePointAt2) == 28 || Character.getType(codePointAt2) == 8 || Character.getType(codePointAt2) == 19)) {
                                        break;
                                    }
                                    charCount += Character.charCount(codePointAt2);
                                    codePointAt = (codePointAt * 31) + codePointAt2;
                                    i20 = i3;
                                    font = font2;
                                }
                                List list8 = textLines2;
                                int i22 = size;
                                long j = codePointAt;
                                LongSparseArray longSparseArray = this.codePointCache;
                                if (longSparseArray.indexOfKey(j) >= 0) {
                                    str = (String) longSparseArray.get(j);
                                    list = list8;
                                    i4 = i22;
                                } else {
                                    StringBuilder sb = this.stringBuilder;
                                    list = list8;
                                    sb.setLength(0);
                                    int i23 = i21;
                                    while (i23 < charCount) {
                                        int i24 = i22;
                                        int codePointAt3 = str11.codePointAt(i23);
                                        sb.appendCodePoint(codePointAt3);
                                        i23 += Character.charCount(codePointAt3);
                                        i22 = i24;
                                    }
                                    i4 = i22;
                                    String sb2 = sb.toString();
                                    longSparseArray.put(j, sb2);
                                    str = sb2;
                                }
                                i21 += str.length();
                                if (documentData.strokeOverFill) {
                                    drawCharacter(str, c06001, canvas);
                                    drawCharacter(str, c060123, canvas);
                                } else {
                                    drawCharacter(str, c060123, canvas);
                                    drawCharacter(str, c06001, canvas);
                                }
                                canvas.translate(c06001.measureText(str) + dpScale, 0.0f);
                                splitGlyphTextIntoLines2 = list7;
                                i20 = i3;
                                textLines2 = list;
                                size = i4;
                                font = font2;
                            }
                            canvas.restore();
                            i19++;
                            i17 = i20;
                        }
                        i2 = i18 + 1;
                    }
                }
                f5 += floatValue;
                float dpScale2 = ((Utils.dpScale() * f5) * floatValue4) / 100.0f;
                List textLines22 = getTextLines(str9);
                size = textLines22.size();
                i2 = 0;
                int i172 = -1;
                while (i2 < size) {
                }
            }
        }
        canvas.restore();
    }

    public final TextSubLine ensureEnoughSubLines(int i) {
        List list = this.textSubLines;
        for (int size = ((ArrayList) list).size(); size < i; size++) {
            ((ArrayList) list).add(new TextSubLine());
        }
        return (TextSubLine) ((ArrayList) list).get(i - 1);
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        super.getBounds(rectF, matrix, z);
        LottieComposition lottieComposition = this.composition;
        rectF.set(0.0f, 0.0f, lottieComposition.bounds.width(), lottieComposition.bounds.height());
    }

    public final List splitGlyphTextIntoLines(String str, float f, Font font, float f2, float f3, boolean z) {
        float measureText;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        int i3 = 0;
        for (int i4 = 0; i4 < str.length(); i4++) {
            char charAt = str.charAt(i4);
            if (z) {
                FontCharacter fontCharacter = (FontCharacter) this.composition.characters.get(font.style.hashCode() + AppInfo$$ExternalSyntheticOutline0.m41m(font.family, charAt * 31, 31));
                if (fontCharacter != null) {
                    measureText = (Utils.dpScale() * ((float) fontCharacter.width) * f2) + f3;
                }
            } else {
                measureText = measureText(str.substring(i4, i4 + 1)) + f3;
            }
            if (charAt == ' ') {
                z2 = true;
                f6 = measureText;
            } else if (z2) {
                i3 = i4;
                f5 = measureText;
                z2 = false;
            } else {
                f5 += measureText;
            }
            f4 += measureText;
            if (f > 0.0f && f4 >= f && charAt != ' ') {
                i++;
                TextSubLine ensureEnoughSubLines = ensureEnoughSubLines(i);
                if (i3 == i2) {
                    ensureEnoughSubLines.text = str.substring(i2, i4).trim();
                    ensureEnoughSubLines.width = (f4 - measureText) - ((r10.length() - r8.length()) * f6);
                    i2 = i4;
                    i3 = i2;
                    f4 = measureText;
                    f5 = f4;
                } else {
                    ensureEnoughSubLines.text = str.substring(i2, i3 - 1).trim();
                    ensureEnoughSubLines.width = ((f4 - f5) - ((r8.length() - r13.length()) * f6)) - f6;
                    f4 = f5;
                    i2 = i3;
                }
            }
        }
        if (f4 > 0.0f) {
            i++;
            TextSubLine ensureEnoughSubLines2 = ensureEnoughSubLines(i);
            ensureEnoughSubLines2.text = str.substring(i2);
            ensureEnoughSubLines2.width = f4;
        }
        return ((ArrayList) this.textSubLines).subList(0, i);
    }
}
