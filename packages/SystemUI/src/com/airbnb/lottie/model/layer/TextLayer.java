package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import androidx.collection.LongSparseArray;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.TextDelegate;
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

/* loaded from: classes.dex */
public class TextLayer extends BaseLayer {
    public final LongSparseArray codePointCache;
    public final ColorKeyframeAnimation colorAnimation;
    public ValueCallbackKeyframeAnimation colorCallbackAnimation;
    public final LottieComposition composition;
    public final Map contentsForCharacter;
    public final AnonymousClass1 fillPaint;
    public final LottieDrawable lottieDrawable;
    public final Matrix matrix;
    public final RectF rectF;
    public final StringBuilder stringBuilder;
    public final ColorKeyframeAnimation strokeColorAnimation;
    public ValueCallbackKeyframeAnimation strokeColorCallbackAnimation;
    public final AnonymousClass2 strokePaint;
    public final FloatKeyframeAnimation strokeWidthAnimation;
    public ValueCallbackKeyframeAnimation strokeWidthCallbackAnimation;
    public final TextKeyframeAnimation textAnimation;
    public ValueCallbackKeyframeAnimation textSizeCallbackAnimation;
    public final List textSubLines;
    public final FloatKeyframeAnimation trackingAnimation;
    public ValueCallbackKeyframeAnimation trackingCallbackAnimation;
    public ValueCallbackKeyframeAnimation typefaceCallbackAnimation;

    /* renamed from: com.airbnb.lottie.model.layer.TextLayer$3, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass3 {
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

    public class TextSubLine {
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
        AnimatableFloatValue animatableFloatValue;
        AnimatableFloatValue animatableFloatValue2;
        AnimatableColorValue animatableColorValue;
        AnimatableColorValue animatableColorValue2;
        super(lottieDrawable, layer);
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
            BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation = animatableColorValue2.createAnimation();
            this.colorAnimation = (ColorKeyframeAnimation) baseKeyframeAnimationCreateAnimation;
            baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
            addAnimation(baseKeyframeAnimationCreateAnimation);
        }
        if (animatableTextProperties != null && (animatableColorValue = animatableTextProperties.stroke) != null) {
            BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation2 = animatableColorValue.createAnimation();
            this.strokeColorAnimation = (ColorKeyframeAnimation) baseKeyframeAnimationCreateAnimation2;
            baseKeyframeAnimationCreateAnimation2.addUpdateListener(this);
            addAnimation(baseKeyframeAnimationCreateAnimation2);
        }
        if (animatableTextProperties != null && (animatableFloatValue2 = animatableTextProperties.strokeWidth) != null) {
            BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation3 = animatableFloatValue2.createAnimation();
            this.strokeWidthAnimation = (FloatKeyframeAnimation) baseKeyframeAnimationCreateAnimation3;
            baseKeyframeAnimationCreateAnimation3.addUpdateListener(this);
            addAnimation(baseKeyframeAnimationCreateAnimation3);
        }
        if (animatableTextProperties == null || (animatableFloatValue = animatableTextProperties.tracking) == null) {
            return;
        }
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation4 = animatableFloatValue.createAnimation();
        this.trackingAnimation = (FloatKeyframeAnimation) baseKeyframeAnimationCreateAnimation4;
        baseKeyframeAnimationCreateAnimation4.addUpdateListener(this);
        addAnimation(baseKeyframeAnimationCreateAnimation4);
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

    public static void offsetCanvas(Canvas canvas, DocumentData documentData, int i, float f) {
        PointF pointF = documentData.boxPosition;
        PointF pointF2 = documentData.boxSize;
        float fDpScale = Utils.dpScale();
        float f2 = (i * documentData.lineHeight * fDpScale) + (pointF == null ? 0.0f : (documentData.lineHeight * fDpScale) + pointF.y);
        float f3 = pointF == null ? 0.0f : pointF.x;
        float f4 = pointF2 != null ? pointF2.x : 0.0f;
        int i2 = AnonymousClass3.$SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[documentData.justification.ordinal()];
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
        PointF pointF = LottieProperty.TRANSFORM_ANCHOR_POINT;
        if (obj == 1) {
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
        if (obj == 2) {
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

                    public AnonymousClass1(TextKeyframeAnimation textKeyframeAnimation2, LottieFrameInfo lottieFrameInfo, LottieValueCallback lottieValueCallback2, DocumentData documentData) {
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
                        PointF pointF2 = documentData.boxPosition;
                        PointF pointF3 = documentData.boxSize;
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
                        documentData2.boxPosition = pointF2;
                        documentData2.boxSize = pointF3;
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

    /* JADX WARN: Removed duplicated region for block: B:101:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0346  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x034c  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x03f0  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x014a  */
    @Override // com.airbnb.lottie.model.layer.BaseLayer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void drawLayer(Canvas canvas, Matrix matrix, int i) {
        FontAssetManager fontAssetManager;
        FloatKeyframeAnimation floatKeyframeAnimation;
        Typeface typeface;
        float fFloatValue;
        int size;
        int i2;
        Font font;
        float f;
        String string;
        Canvas canvas2;
        float fFloatValue2;
        int i3;
        float f2;
        int i4;
        List list;
        List list2;
        TextLayer textLayer = this;
        DocumentData documentData = (DocumentData) textLayer.textAnimation.getValue();
        LottieComposition lottieComposition = textLayer.composition;
        Font font2 = (Font) ((HashMap) lottieComposition.fonts).get(documentData.fontName);
        if (font2 == null) {
            return;
        }
        canvas.save();
        canvas.concat(matrix);
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = textLayer.colorCallbackAnimation;
        AnonymousClass1 anonymousClass1 = textLayer.fillPaint;
        if (valueCallbackKeyframeAnimation != null) {
            anonymousClass1.setColor(((Integer) valueCallbackKeyframeAnimation.getValue()).intValue());
        } else {
            ColorKeyframeAnimation colorKeyframeAnimation = textLayer.colorAnimation;
            if (colorKeyframeAnimation != null) {
                anonymousClass1.setColor(((Integer) colorKeyframeAnimation.getValue()).intValue());
            } else {
                anonymousClass1.setColor(documentData.color);
            }
        }
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = textLayer.strokeColorCallbackAnimation;
        AnonymousClass2 anonymousClass2 = textLayer.strokePaint;
        if (valueCallbackKeyframeAnimation2 != null) {
            anonymousClass2.setColor(((Integer) valueCallbackKeyframeAnimation2.getValue()).intValue());
        } else {
            ColorKeyframeAnimation colorKeyframeAnimation2 = textLayer.strokeColorAnimation;
            if (colorKeyframeAnimation2 != null) {
                anonymousClass2.setColor(((Integer) colorKeyframeAnimation2.getValue()).intValue());
            } else {
                anonymousClass2.setColor(documentData.strokeColor);
            }
        }
        BaseKeyframeAnimation baseKeyframeAnimation = textLayer.transform.opacity;
        int iIntValue = ((((baseKeyframeAnimation == null ? 100 : ((Integer) baseKeyframeAnimation.getValue()).intValue()) * 255) / 100) * i) / 255;
        anonymousClass1.setAlpha(iIntValue);
        anonymousClass2.setAlpha(iIntValue);
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation3 = textLayer.strokeWidthCallbackAnimation;
        if (valueCallbackKeyframeAnimation3 != null) {
            anonymousClass2.setStrokeWidth(((Float) valueCallbackKeyframeAnimation3.getValue()).floatValue());
        } else {
            FloatKeyframeAnimation floatKeyframeAnimation2 = textLayer.strokeWidthAnimation;
            if (floatKeyframeAnimation2 != null) {
                anonymousClass2.setStrokeWidth(((Float) floatKeyframeAnimation2.getValue()).floatValue());
            } else {
                anonymousClass2.setStrokeWidth(Utils.dpScale() * documentData.strokeWidth);
            }
        }
        LottieDrawable lottieDrawable = textLayer.lottieDrawable;
        Map map = lottieDrawable.fontMap;
        FloatKeyframeAnimation floatKeyframeAnimation3 = textLayer.trackingAnimation;
        String str = font2.style;
        String str2 = font2.family;
        if (map == null && lottieDrawable.textDelegate == null && lottieDrawable.composition.characters.size() > 0) {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation4 = textLayer.textSizeCallbackAnimation;
            float fFloatValue3 = valueCallbackKeyframeAnimation4 != null ? ((Float) valueCallbackKeyframeAnimation4.getValue()).floatValue() : documentData.size;
            Utils.getScale(matrix);
            float f3 = fFloatValue3 / 100.0f;
            List listAsList = Arrays.asList(documentData.text.replaceAll("\r\n", "\r").replaceAll("\u0003", "\r").replaceAll("\n", "\r").split("\r"));
            int size2 = listAsList.size();
            float f4 = documentData.tracking / 10.0f;
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation5 = textLayer.trackingCallbackAnimation;
            if (valueCallbackKeyframeAnimation5 != null) {
                fFloatValue2 = ((Float) valueCallbackKeyframeAnimation5.getValue()).floatValue();
            } else {
                if (floatKeyframeAnimation3 != null) {
                    fFloatValue2 = ((Float) floatKeyframeAnimation3.getValue()).floatValue();
                }
                float f5 = f4;
                i3 = 0;
                int i5 = -1;
                while (i3 < size2) {
                    String str3 = (String) listAsList.get(i3);
                    PointF pointF = documentData.boxSize;
                    float f6 = f3;
                    int i6 = size2;
                    int i7 = i3;
                    List listSplitGlyphTextIntoLines = textLayer.splitGlyphTextIntoLines(str3, pointF == null ? 0.0f : pointF.x, font2, f6, f5, true);
                    int i8 = 0;
                    while (i8 < listSplitGlyphTextIntoLines.size()) {
                        TextSubLine textSubLine = (TextSubLine) listSplitGlyphTextIntoLines.get(i8);
                        List list3 = listSplitGlyphTextIntoLines;
                        int i9 = i5 + 1;
                        canvas.save();
                        int i10 = i8;
                        offsetCanvas(canvas, documentData, i9, textSubLine.width);
                        String str4 = textSubLine.text;
                        i5 = i9;
                        int i11 = 0;
                        while (i11 < str4.length()) {
                            String str5 = str4;
                            FontCharacter fontCharacter = (FontCharacter) lottieComposition.characters.get(FontCharacter.hashFor(str4.charAt(i11), str2, str));
                            if (fontCharacter == null) {
                                f2 = f5;
                                i4 = i11;
                                list = listAsList;
                            } else {
                                if (((HashMap) textLayer.contentsForCharacter).containsKey(fontCharacter)) {
                                    list2 = (List) ((HashMap) textLayer.contentsForCharacter).get(fontCharacter);
                                    f2 = f5;
                                    i4 = i11;
                                    list = listAsList;
                                } else {
                                    List list4 = fontCharacter.shapes;
                                    f2 = f5;
                                    int size3 = list4.size();
                                    i4 = i11;
                                    ArrayList arrayList = new ArrayList(size3);
                                    list = listAsList;
                                    int i12 = 0;
                                    while (i12 < size3) {
                                        arrayList.add(new ContentGroup(lottieDrawable, textLayer, (ShapeGroup) list4.get(i12), lottieComposition));
                                        i12++;
                                        size3 = size3;
                                        list4 = list4;
                                    }
                                    ((HashMap) textLayer.contentsForCharacter).put(fontCharacter, arrayList);
                                    list2 = arrayList;
                                }
                                int i13 = 0;
                                while (i13 < list2.size()) {
                                    Path path = ((ContentGroup) list2.get(i13)).getPath();
                                    List list5 = list2;
                                    path.computeBounds(textLayer.rectF, false);
                                    textLayer.matrix.reset();
                                    int i14 = i13;
                                    textLayer.matrix.preTranslate(0.0f, (-documentData.baselineShift) * Utils.dpScale());
                                    textLayer.matrix.preScale(f6, f6);
                                    path.transform(textLayer.matrix);
                                    if (documentData.strokeOverFill) {
                                        drawGlyph(path, anonymousClass1, canvas);
                                        drawGlyph(path, anonymousClass2, canvas);
                                    } else {
                                        drawGlyph(path, anonymousClass2, canvas);
                                        drawGlyph(path, anonymousClass1, canvas);
                                    }
                                    i13 = i14 + 1;
                                    list2 = list5;
                                }
                                canvas.translate((Utils.dpScale() * ((float) fontCharacter.width) * f6) + f2, 0.0f);
                            }
                            i11 = i4 + 1;
                            str4 = str5;
                            f5 = f2;
                            listAsList = list;
                        }
                        canvas.restore();
                        i8 = i10 + 1;
                        listSplitGlyphTextIntoLines = list3;
                    }
                    i3 = i7 + 1;
                    size2 = i6;
                    f3 = f6;
                }
                canvas2 = canvas;
            }
            f4 += fFloatValue2;
            float f52 = f4;
            i3 = 0;
            int i52 = -1;
            while (i3 < size2) {
            }
            canvas2 = canvas;
        } else {
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation6 = textLayer.typefaceCallbackAnimation;
            if (valueCallbackKeyframeAnimation6 == null || (typeface = (Typeface) valueCallbackKeyframeAnimation6.getValue()) == null) {
                Map map2 = lottieDrawable.fontMap;
                if (map2 == null) {
                    fontAssetManager = lottieDrawable.getFontAssetManager();
                    if (fontAssetManager == null) {
                        MutablePair mutablePair = fontAssetManager.tempPair;
                        mutablePair.first = str2;
                        mutablePair.second = str;
                        floatKeyframeAnimation = floatKeyframeAnimation3;
                        Typeface typefaceCreate = (Typeface) ((HashMap) fontAssetManager.fontMap).get(mutablePair);
                        if (typefaceCreate == null) {
                            typefaceCreate = (Typeface) ((HashMap) fontAssetManager.fontFamilies).get(str2);
                            if (typefaceCreate == null && (typefaceCreate = font2.typeface) == null) {
                                typefaceCreate = Typeface.createFromAsset(fontAssetManager.assetManager, "fonts/" + str2 + fontAssetManager.defaultFontFileExtension);
                                ((HashMap) fontAssetManager.fontFamilies).put(str2, typefaceCreate);
                            }
                            boolean zContains = str.contains("Italic");
                            boolean zContains2 = str.contains("Bold");
                            int i15 = (zContains && zContains2) ? 3 : zContains ? 2 : zContains2 ? 1 : 0;
                            if (typefaceCreate.getStyle() != i15) {
                                typefaceCreate = Typeface.create(typefaceCreate, i15);
                            }
                            ((HashMap) fontAssetManager.fontMap).put(mutablePair, typefaceCreate);
                        }
                        typeface = typefaceCreate;
                    } else {
                        floatKeyframeAnimation = floatKeyframeAnimation3;
                        typeface = null;
                    }
                    if (typeface == null) {
                        typeface = font2.typeface;
                    }
                } else {
                    if (map2.containsKey(str2)) {
                        typeface = (Typeface) map2.get(str2);
                    } else {
                        String str6 = font2.name;
                        if (map2.containsKey(str6)) {
                            typeface = (Typeface) map2.get(str6);
                        } else {
                            String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, "-", str);
                            if (map2.containsKey(strM)) {
                                typeface = (Typeface) map2.get(strM);
                            }
                            fontAssetManager = lottieDrawable.getFontAssetManager();
                            if (fontAssetManager == null) {
                            }
                            if (typeface == null) {
                            }
                        }
                    }
                    floatKeyframeAnimation = floatKeyframeAnimation3;
                    if (typeface == null) {
                    }
                }
            } else {
                floatKeyframeAnimation = floatKeyframeAnimation3;
            }
            if (typeface != null) {
                String str7 = documentData.text;
                TextDelegate textDelegate = lottieDrawable.textDelegate;
                if (textDelegate != null) {
                    String str8 = textLayer.layerModel.layerName;
                    boolean z = textDelegate.cacheText;
                    if (z && ((HashMap) textDelegate.stringMap).containsKey(str7)) {
                        str7 = (String) ((HashMap) textDelegate.stringMap).get(str7);
                    } else if (z) {
                        ((HashMap) textDelegate.stringMap).put(str7, str7);
                    }
                }
                anonymousClass1.setTypeface(typeface);
                ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation7 = textLayer.textSizeCallbackAnimation;
                float fFloatValue4 = valueCallbackKeyframeAnimation7 != null ? ((Float) valueCallbackKeyframeAnimation7.getValue()).floatValue() : documentData.size;
                anonymousClass1.setTextSize(Utils.dpScale() * fFloatValue4);
                anonymousClass2.setTypeface(anonymousClass1.getTypeface());
                anonymousClass2.setTextSize(anonymousClass1.getTextSize());
                float f7 = documentData.tracking / 10.0f;
                ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation8 = textLayer.trackingCallbackAnimation;
                if (valueCallbackKeyframeAnimation8 != null) {
                    fFloatValue = ((Float) valueCallbackKeyframeAnimation8.getValue()).floatValue();
                } else {
                    if (floatKeyframeAnimation != null) {
                        fFloatValue = ((Float) floatKeyframeAnimation.getValue()).floatValue();
                    }
                    float fDpScale = ((Utils.dpScale() * f7) * fFloatValue4) / 100.0f;
                    List listAsList2 = Arrays.asList(str7.replaceAll("\r\n", "\r").replaceAll("\u0003", "\r").replaceAll("\n", "\r").split("\r"));
                    size = listAsList2.size();
                    i2 = 0;
                    int i16 = -1;
                    while (i2 < size) {
                        String str9 = (String) listAsList2.get(i2);
                        PointF pointF2 = documentData.boxSize;
                        float f8 = fDpScale;
                        List listSplitGlyphTextIntoLines2 = textLayer.splitGlyphTextIntoLines(str9, pointF2 == null ? 0.0f : pointF2.x, font2, 0.0f, f8, false);
                        int i17 = 0;
                        while (i17 < listSplitGlyphTextIntoLines2.size()) {
                            TextSubLine textSubLine2 = (TextSubLine) listSplitGlyphTextIntoLines2.get(i17);
                            i16++;
                            canvas.save();
                            offsetCanvas(canvas, documentData, i16, textSubLine2.width);
                            String str10 = textSubLine2.text;
                            int length = 0;
                            while (length < str10.length()) {
                                int iCodePointAt = str10.codePointAt(length);
                                List list6 = listSplitGlyphTextIntoLines2;
                                int iCharCount = Character.charCount(iCodePointAt) + length;
                                int i18 = i17;
                                while (true) {
                                    if (iCharCount >= str10.length()) {
                                        font = font2;
                                        break;
                                    }
                                    int iCodePointAt2 = str10.codePointAt(iCharCount);
                                    font = font2;
                                    if (Character.getType(iCodePointAt2) != 16 && Character.getType(iCodePointAt2) != 27 && Character.getType(iCodePointAt2) != 6 && Character.getType(iCodePointAt2) != 28 && Character.getType(iCodePointAt2) != 8 && Character.getType(iCodePointAt2) != 19) {
                                        break;
                                    }
                                    iCharCount += Character.charCount(iCodePointAt2);
                                    iCodePointAt = (iCodePointAt * 31) + iCodePointAt2;
                                    font2 = font;
                                }
                                long j = iCodePointAt;
                                LongSparseArray longSparseArray = textLayer.codePointCache;
                                if (longSparseArray.indexOfKey(j) >= 0) {
                                    string = (String) longSparseArray.get(j);
                                    f = f8;
                                } else {
                                    f = f8;
                                    StringBuilder sb = textLayer.stringBuilder;
                                    sb.setLength(0);
                                    int iCharCount2 = length;
                                    while (iCharCount2 < iCharCount) {
                                        int i19 = iCharCount;
                                        int iCodePointAt3 = str10.codePointAt(iCharCount2);
                                        sb.appendCodePoint(iCodePointAt3);
                                        iCharCount2 += Character.charCount(iCodePointAt3);
                                        iCharCount = i19;
                                    }
                                    string = sb.toString();
                                    longSparseArray.put(j, string);
                                }
                                length += string.length();
                                if (documentData.strokeOverFill) {
                                    drawCharacter(string, anonymousClass1, canvas);
                                    drawCharacter(string, anonymousClass2, canvas);
                                } else {
                                    drawCharacter(string, anonymousClass2, canvas);
                                    drawCharacter(string, anonymousClass1, canvas);
                                }
                                canvas.translate(anonymousClass1.measureText(string) + f, 0.0f);
                                textLayer = this;
                                listSplitGlyphTextIntoLines2 = list6;
                                f8 = f;
                                i17 = i18;
                                font2 = font;
                            }
                            canvas.restore();
                            i17++;
                            textLayer = this;
                            f8 = f8;
                        }
                        i2++;
                        textLayer = this;
                        fDpScale = f8;
                    }
                }
                f7 += fFloatValue;
                float fDpScale2 = ((Utils.dpScale() * f7) * fFloatValue4) / 100.0f;
                List listAsList22 = Arrays.asList(str7.replaceAll("\r\n", "\r").replaceAll("\u0003", "\r").replaceAll("\n", "\r").split("\r"));
                size = listAsList22.size();
                i2 = 0;
                int i162 = -1;
                while (i2 < size) {
                }
            }
            canvas2 = canvas;
        }
        canvas2.restore();
    }

    public final TextSubLine ensureEnoughSubLines(int i) {
        for (int size = ((ArrayList) this.textSubLines).size(); size < i; size++) {
            ((ArrayList) this.textSubLines).add(new TextSubLine());
        }
        return (TextSubLine) ((ArrayList) this.textSubLines).get(i - 1);
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public final void getBounds(RectF rectF, Matrix matrix, boolean z) {
        super.getBounds(rectF, matrix, z);
        LottieComposition lottieComposition = this.composition;
        rectF.set(0.0f, 0.0f, lottieComposition.bounds.width(), lottieComposition.bounds.height());
    }

    public final List splitGlyphTextIntoLines(String str, float f, Font font, float f2, float f3, boolean z) {
        float fMeasureText;
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        int i3 = 0;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        for (int i4 = 0; i4 < str.length(); i4++) {
            char cCharAt = str.charAt(i4);
            if (z) {
                FontCharacter fontCharacter = (FontCharacter) this.composition.characters.get(FontCharacter.hashFor(cCharAt, font.family, font.style));
                if (fontCharacter != null) {
                    fMeasureText = (Utils.dpScale() * ((float) fontCharacter.width) * f2) + f3;
                }
            } else {
                fMeasureText = measureText(str.substring(i4, i4 + 1)) + f3;
            }
            if (cCharAt == ' ') {
                z2 = true;
                f6 = fMeasureText;
            } else if (z2) {
                z2 = false;
                i3 = i4;
                f5 = fMeasureText;
            } else {
                f5 += fMeasureText;
            }
            f4 += fMeasureText;
            if (f > 0.0f && f4 >= f && cCharAt != ' ') {
                i++;
                TextSubLine textSubLineEnsureEnoughSubLines = ensureEnoughSubLines(i);
                if (i3 == i2) {
                    textSubLineEnsureEnoughSubLines.text = str.substring(i2, i4).trim();
                    textSubLineEnsureEnoughSubLines.width = (f4 - fMeasureText) - ((r10.length() - r8.length()) * f6);
                    i2 = i4;
                    i3 = i2;
                    f4 = fMeasureText;
                    f5 = f4;
                } else {
                    textSubLineEnsureEnoughSubLines.text = str.substring(i2, i3 - 1).trim();
                    textSubLineEnsureEnoughSubLines.width = ((f4 - f5) - ((r8.length() - r14.length()) * f6)) - f6;
                    f4 = f5;
                    i2 = i3;
                }
            }
        }
        if (f4 > 0.0f) {
            i++;
            TextSubLine textSubLineEnsureEnoughSubLines2 = ensureEnoughSubLines(i);
            textSubLineEnsureEnoughSubLines2.text = str.substring(i2);
            textSubLineEnsureEnoughSubLines2.width = f4;
        }
        return ((ArrayList) this.textSubLines).subList(0, i);
    }
}
