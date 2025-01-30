package com.airbnb.lottie.parser;

import android.graphics.Color;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatableShapeValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.BlurEffect;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonUtf8Reader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LayerParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.m50of("nm", "ind", "refId", "ty", "parent", "sw", "sh", "sc", "ks", "tt", "masksProperties", "shapes", "t", "ef", "sr", "st", "w", "h", "ip", "op", "tm", "cl", "hd");
    public static final JsonReader.Options TEXT_NAMES = JsonReader.Options.m50of("d", "a");
    public static final JsonReader.Options EFFECTS_NAMES = JsonReader.Options.m50of("ty", "nm");

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.airbnb.lottie.parser.LayerParser$1 */
    public abstract /* synthetic */ class AbstractC06041 {
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType;

        static {
            int[] iArr = new int[Layer.MatteType.values().length];
            $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType = iArr;
            try {
                iArr[Layer.MatteType.LUMA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[Layer.MatteType.LUMA_INVERTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private LayerParser() {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:294:0x0405  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x0437  */
    /* JADX WARN: Type inference failed for: r6v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Layer parse(JsonUtf8Reader jsonUtf8Reader, LottieComposition lottieComposition) {
        String str;
        boolean z;
        char c;
        char c2;
        AnimatableFloatValue animatableFloatValue;
        AnimatableFloatValue animatableFloatValue2;
        AnimatableFloatValue animatableFloatValue3;
        AnimatableFloatValue animatableFloatValue4;
        char c3;
        Layer.MatteType matteType = Layer.MatteType.NONE;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        jsonUtf8Reader.beginObject();
        Float valueOf = Float.valueOf(0.0f);
        Float valueOf2 = Float.valueOf(1.0f);
        boolean z2 = false;
        Layer.MatteType matteType2 = matteType;
        float f = 1.0f;
        long j = 0;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        boolean z3 = false;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        long j2 = -1;
        String str2 = null;
        Layer.LayerType layerType = null;
        String str3 = null;
        AnimatableTransform animatableTransform = null;
        AnimatableTextFrame animatableTextFrame = null;
        AnimatableTextProperties animatableTextProperties = null;
        AnimatableFloatValue animatableFloatValue5 = null;
        BlurEffect blurEffect = null;
        DropShadowEffect dropShadowEffect = null;
        String str4 = "UNSET";
        float f6 = 0.0f;
        while (jsonUtf8Reader.hasNext()) {
            char c4 = 2;
            switch (jsonUtf8Reader.selectName(NAMES)) {
                case 0:
                    z = z2;
                    str4 = jsonUtf8Reader.nextString();
                    break;
                case 1:
                    z = z2;
                    j = jsonUtf8Reader.nextInt();
                    break;
                case 2:
                    z = z2;
                    str3 = jsonUtf8Reader.nextString();
                    break;
                case 3:
                    z = z2;
                    int nextInt = jsonUtf8Reader.nextInt();
                    layerType = Layer.LayerType.UNKNOWN;
                    if (nextInt >= layerType.ordinal()) {
                        break;
                    } else {
                        layerType = Layer.LayerType.values()[nextInt];
                        break;
                    }
                case 4:
                    z = z2;
                    j2 = jsonUtf8Reader.nextInt();
                    break;
                case 5:
                    z = z2;
                    i = (int) (Utils.dpScale() * jsonUtf8Reader.nextInt());
                    break;
                case 6:
                    z = z2;
                    i2 = (int) (Utils.dpScale() * jsonUtf8Reader.nextInt());
                    break;
                case 7:
                    z = z2;
                    i3 = Color.parseColor(jsonUtf8Reader.nextString());
                    break;
                case 8:
                    z = z2;
                    animatableTransform = AnimatableTransformParser.parse(jsonUtf8Reader, lottieComposition);
                    break;
                case 9:
                    z = z2;
                    int nextInt2 = jsonUtf8Reader.nextInt();
                    if (nextInt2 < Layer.MatteType.values().length) {
                        matteType2 = Layer.MatteType.values()[nextInt2];
                        int i4 = AbstractC06041.$SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[matteType2.ordinal()];
                        if (i4 == 1) {
                            lottieComposition.addWarning("Unsupported matte type: Luma");
                        } else if (i4 == 2) {
                            lottieComposition.addWarning("Unsupported matte type: Luma Inverted");
                        }
                        lottieComposition.maskAndMatteCount++;
                        break;
                    } else {
                        lottieComposition.addWarning("Unsupported matte type: " + nextInt2);
                        break;
                    }
                case 10:
                    Mask.MaskMode maskMode = null;
                    jsonUtf8Reader.beginArray();
                    while (jsonUtf8Reader.hasNext()) {
                        jsonUtf8Reader.beginObject();
                        Mask.MaskMode maskMode2 = maskMode;
                        Mask.MaskMode maskMode3 = maskMode2;
                        AnimatableIntegerValue animatableIntegerValue = maskMode3;
                        boolean z4 = false;
                        AnimatableShapeValue animatableShapeValue = maskMode3;
                        while (jsonUtf8Reader.hasNext()) {
                            String nextName = jsonUtf8Reader.nextName();
                            nextName.getClass();
                            int hashCode = nextName.hashCode();
                            if (hashCode == 111) {
                                if (nextName.equals("o")) {
                                    c = 0;
                                }
                                c = 65535;
                            } else if (hashCode == 3588) {
                                if (nextName.equals("pt")) {
                                    c = 1;
                                }
                                c = 65535;
                            } else if (hashCode != 104433) {
                                if (hashCode == 3357091 && nextName.equals("mode")) {
                                    c = 3;
                                }
                                c = 65535;
                            } else {
                                if (nextName.equals("inv")) {
                                    c = 2;
                                }
                                c = 65535;
                            }
                            if (c == 0) {
                                animatableIntegerValue = AnimatableValueParser.parseInteger(jsonUtf8Reader, lottieComposition);
                            } else if (c == 1) {
                                animatableShapeValue = new AnimatableShapeValue(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, Utils.dpScale(), ShapeDataParser.INSTANCE, false));
                            } else if (c == 2) {
                                z4 = jsonUtf8Reader.nextBoolean();
                            } else if (c != 3) {
                                jsonUtf8Reader.skipValue();
                            } else {
                                String nextString = jsonUtf8Reader.nextString();
                                nextString.getClass();
                                int hashCode2 = nextString.hashCode();
                                if (hashCode2 == 97) {
                                    if (nextString.equals("a")) {
                                        c2 = 0;
                                        if (c2 != 0) {
                                        }
                                    }
                                    c2 = 65535;
                                    if (c2 != 0) {
                                    }
                                } else if (hashCode2 == 105) {
                                    if (nextString.equals("i")) {
                                        c2 = 1;
                                        if (c2 != 0) {
                                        }
                                    }
                                    c2 = 65535;
                                    if (c2 != 0) {
                                    }
                                } else if (hashCode2 != 110) {
                                    if (hashCode2 == 115 && nextString.equals("s")) {
                                        c2 = 3;
                                        if (c2 != 0) {
                                            maskMode2 = Mask.MaskMode.MASK_MODE_ADD;
                                        } else if (c2 == 1) {
                                            lottieComposition.addWarning("Animation contains intersect masks. They are not supported but will be treated like add masks.");
                                            maskMode2 = Mask.MaskMode.MASK_MODE_INTERSECT;
                                        } else if (c2 == 2) {
                                            maskMode2 = Mask.MaskMode.MASK_MODE_NONE;
                                        } else if (c2 != 3) {
                                            Logger.warning("Unknown mask mode " + nextName + ". Defaulting to Add.");
                                            maskMode2 = Mask.MaskMode.MASK_MODE_ADD;
                                        } else {
                                            maskMode2 = Mask.MaskMode.MASK_MODE_SUBTRACT;
                                        }
                                    }
                                    c2 = 65535;
                                    if (c2 != 0) {
                                    }
                                } else {
                                    if (nextString.equals("n")) {
                                        c2 = 2;
                                        if (c2 != 0) {
                                        }
                                    }
                                    c2 = 65535;
                                    if (c2 != 0) {
                                    }
                                }
                            }
                            animatableShapeValue = animatableShapeValue;
                        }
                        jsonUtf8Reader.endObject();
                        arrayList.add(new Mask(maskMode2, animatableShapeValue, animatableIntegerValue, z4));
                        maskMode = null;
                    }
                    z = false;
                    lottieComposition.maskAndMatteCount += arrayList.size();
                    jsonUtf8Reader.endArray();
                    break;
                case 11:
                    jsonUtf8Reader.beginArray();
                    while (jsonUtf8Reader.hasNext()) {
                        ContentModel parse = ContentModelParser.parse(jsonUtf8Reader, lottieComposition);
                        if (parse != null) {
                            arrayList2.add(parse);
                        }
                    }
                    jsonUtf8Reader.endArray();
                    z = false;
                    break;
                case 12:
                    jsonUtf8Reader.beginObject();
                    while (jsonUtf8Reader.hasNext()) {
                        int selectName = jsonUtf8Reader.selectName(TEXT_NAMES);
                        if (selectName == 0) {
                            animatableTextFrame = new AnimatableTextFrame(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, Utils.dpScale(), DocumentDataParser.INSTANCE, false));
                        } else if (selectName != 1) {
                            jsonUtf8Reader.skipName();
                            jsonUtf8Reader.skipValue();
                        } else {
                            jsonUtf8Reader.beginArray();
                            if (jsonUtf8Reader.hasNext()) {
                                JsonReader.Options options = AnimatableTextPropertiesParser.PROPERTIES_NAMES;
                                jsonUtf8Reader.beginObject();
                                AnimatableTextProperties animatableTextProperties2 = null;
                                while (jsonUtf8Reader.hasNext()) {
                                    if (jsonUtf8Reader.selectName(AnimatableTextPropertiesParser.PROPERTIES_NAMES) != 0) {
                                        jsonUtf8Reader.skipName();
                                        jsonUtf8Reader.skipValue();
                                    } else {
                                        jsonUtf8Reader.beginObject();
                                        AnimatableColorValue animatableColorValue = null;
                                        AnimatableColorValue animatableColorValue2 = null;
                                        AnimatableFloatValue animatableFloatValue6 = null;
                                        AnimatableFloatValue animatableFloatValue7 = null;
                                        while (jsonUtf8Reader.hasNext()) {
                                            int selectName2 = jsonUtf8Reader.selectName(AnimatableTextPropertiesParser.ANIMATABLE_PROPERTIES_NAMES);
                                            if (selectName2 == 0) {
                                                animatableColorValue = AnimatableValueParser.parseColor(jsonUtf8Reader, lottieComposition);
                                            } else if (selectName2 == 1) {
                                                animatableColorValue2 = AnimatableValueParser.parseColor(jsonUtf8Reader, lottieComposition);
                                            } else if (selectName2 == 2) {
                                                animatableFloatValue6 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                                            } else if (selectName2 != 3) {
                                                jsonUtf8Reader.skipName();
                                                jsonUtf8Reader.skipValue();
                                            } else {
                                                animatableFloatValue7 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                                            }
                                        }
                                        jsonUtf8Reader.endObject();
                                        animatableTextProperties2 = new AnimatableTextProperties(animatableColorValue, animatableColorValue2, animatableFloatValue6, animatableFloatValue7);
                                    }
                                }
                                jsonUtf8Reader.endObject();
                                if (animatableTextProperties2 == null) {
                                    animatableTextProperties2 = new AnimatableTextProperties(null, null, null, null);
                                }
                                animatableTextProperties = animatableTextProperties2;
                            }
                            while (jsonUtf8Reader.hasNext()) {
                                jsonUtf8Reader.skipValue();
                            }
                            jsonUtf8Reader.endArray();
                        }
                    }
                    jsonUtf8Reader.endObject();
                    z = false;
                    break;
                case 13:
                    jsonUtf8Reader.beginArray();
                    ArrayList arrayList3 = new ArrayList();
                    while (jsonUtf8Reader.hasNext()) {
                        jsonUtf8Reader.beginObject();
                        while (jsonUtf8Reader.hasNext()) {
                            int selectName3 = jsonUtf8Reader.selectName(EFFECTS_NAMES);
                            if (selectName3 == 0) {
                                int nextInt3 = jsonUtf8Reader.nextInt();
                                if (nextInt3 == 29) {
                                    JsonReader.Options options2 = BlurEffectParser.BLUR_EFFECT_NAMES;
                                    blurEffect = null;
                                    while (jsonUtf8Reader.hasNext()) {
                                        if (jsonUtf8Reader.selectName(BlurEffectParser.BLUR_EFFECT_NAMES) != 0) {
                                            jsonUtf8Reader.skipName();
                                            jsonUtf8Reader.skipValue();
                                        } else {
                                            jsonUtf8Reader.beginArray();
                                            while (jsonUtf8Reader.hasNext()) {
                                                jsonUtf8Reader.beginObject();
                                                BlurEffect blurEffect2 = null;
                                                while (true) {
                                                    boolean z5 = z2;
                                                    while (jsonUtf8Reader.hasNext()) {
                                                        int selectName4 = jsonUtf8Reader.selectName(BlurEffectParser.INNER_BLUR_EFFECT_NAMES);
                                                        if (selectName4 != 0) {
                                                            if (selectName4 != 1) {
                                                                jsonUtf8Reader.skipName();
                                                                jsonUtf8Reader.skipValue();
                                                            } else if (z5) {
                                                                blurEffect2 = new BlurEffect(AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true));
                                                            } else {
                                                                jsonUtf8Reader.skipValue();
                                                            }
                                                        } else if (jsonUtf8Reader.nextInt() == 0) {
                                                            z5 = true;
                                                        }
                                                    }
                                                    jsonUtf8Reader.endObject();
                                                    if (blurEffect2 != null) {
                                                        blurEffect = blurEffect2;
                                                    }
                                                    z2 = false;
                                                    z2 = false;
                                                }
                                            }
                                            jsonUtf8Reader.endArray();
                                            z2 = false;
                                        }
                                    }
                                } else if (nextInt3 == 25) {
                                    DropShadowEffectParser dropShadowEffectParser = new DropShadowEffectParser();
                                    while (jsonUtf8Reader.hasNext()) {
                                        if (jsonUtf8Reader.selectName(DropShadowEffectParser.DROP_SHADOW_EFFECT_NAMES) != 0) {
                                            jsonUtf8Reader.skipName();
                                            jsonUtf8Reader.skipValue();
                                        } else {
                                            jsonUtf8Reader.beginArray();
                                            while (jsonUtf8Reader.hasNext()) {
                                                jsonUtf8Reader.beginObject();
                                                String str5 = "";
                                                while (jsonUtf8Reader.hasNext()) {
                                                    int selectName5 = jsonUtf8Reader.selectName(DropShadowEffectParser.INNER_EFFECT_NAMES);
                                                    if (selectName5 == 0) {
                                                        str5 = jsonUtf8Reader.nextString();
                                                    } else if (selectName5 != 1) {
                                                        jsonUtf8Reader.skipName();
                                                        jsonUtf8Reader.skipValue();
                                                    } else {
                                                        str5.getClass();
                                                        switch (str5.hashCode()) {
                                                            case 353103893:
                                                                if (str5.equals("Distance")) {
                                                                    c3 = 0;
                                                                    break;
                                                                }
                                                                c3 = 65535;
                                                                break;
                                                            case 397447147:
                                                                if (str5.equals("Opacity")) {
                                                                    c3 = 1;
                                                                    break;
                                                                }
                                                                c3 = 65535;
                                                                break;
                                                            case 1041377119:
                                                                if (str5.equals("Direction")) {
                                                                    c3 = c4;
                                                                    break;
                                                                }
                                                                c3 = 65535;
                                                                break;
                                                            case 1379387491:
                                                                if (str5.equals("Shadow Color")) {
                                                                    c3 = 3;
                                                                    break;
                                                                }
                                                                c3 = 65535;
                                                                break;
                                                            case 1383710113:
                                                                if (str5.equals("Softness")) {
                                                                    c3 = 4;
                                                                    break;
                                                                }
                                                                c3 = 65535;
                                                                break;
                                                            default:
                                                                c3 = 65535;
                                                                break;
                                                        }
                                                        if (c3 == 0) {
                                                            dropShadowEffectParser.distance = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                                                        } else if (c3 == 1) {
                                                            dropShadowEffectParser.opacity = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                                                        } else if (c3 == c4) {
                                                            dropShadowEffectParser.direction = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                                                        } else if (c3 == 3) {
                                                            dropShadowEffectParser.color = AnimatableValueParser.parseColor(jsonUtf8Reader, lottieComposition);
                                                        } else if (c3 != 4) {
                                                            jsonUtf8Reader.skipValue();
                                                        } else {
                                                            dropShadowEffectParser.radius = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                                                        }
                                                    }
                                                    c4 = 2;
                                                }
                                                jsonUtf8Reader.endObject();
                                                c4 = 2;
                                            }
                                            jsonUtf8Reader.endArray();
                                            c4 = 2;
                                        }
                                    }
                                    AnimatableColorValue animatableColorValue3 = dropShadowEffectParser.color;
                                    dropShadowEffect = (animatableColorValue3 == null || (animatableFloatValue = dropShadowEffectParser.opacity) == null || (animatableFloatValue2 = dropShadowEffectParser.direction) == null || (animatableFloatValue3 = dropShadowEffectParser.distance) == null || (animatableFloatValue4 = dropShadowEffectParser.radius) == null) ? null : new DropShadowEffect(animatableColorValue3, animatableFloatValue, animatableFloatValue2, animatableFloatValue3, animatableFloatValue4);
                                }
                            } else if (selectName3 != 1) {
                                jsonUtf8Reader.skipName();
                                jsonUtf8Reader.skipValue();
                            } else {
                                arrayList3.add(jsonUtf8Reader.nextString());
                            }
                            c4 = 2;
                            z2 = false;
                        }
                        jsonUtf8Reader.endObject();
                        c4 = 2;
                        z2 = false;
                    }
                    jsonUtf8Reader.endArray();
                    lottieComposition.addWarning("Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: " + arrayList3);
                    z = false;
                    break;
                case 14:
                    f = (float) jsonUtf8Reader.nextDouble();
                    z = z2;
                    break;
                case 15:
                    f2 = (float) jsonUtf8Reader.nextDouble();
                    z = z2;
                    break;
                case 16:
                    f3 = (float) (jsonUtf8Reader.nextDouble() * Utils.dpScale());
                    z = z2;
                    break;
                case 17:
                    f4 = (float) (jsonUtf8Reader.nextDouble() * Utils.dpScale());
                    z = z2;
                    break;
                case 18:
                    f6 = (float) jsonUtf8Reader.nextDouble();
                    z = z2;
                    break;
                case 19:
                    f5 = (float) jsonUtf8Reader.nextDouble();
                    z = z2;
                    break;
                case 20:
                    animatableFloatValue5 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z2);
                    z = z2;
                    break;
                case 21:
                    str2 = jsonUtf8Reader.nextString();
                    z = z2;
                    break;
                case 22:
                    z3 = jsonUtf8Reader.nextBoolean();
                    z = z2;
                    break;
                default:
                    z = z2;
                    jsonUtf8Reader.skipName();
                    jsonUtf8Reader.skipValue();
                    break;
            }
            z2 = z;
        }
        jsonUtf8Reader.endObject();
        ArrayList arrayList4 = new ArrayList();
        if (f6 > 0.0f) {
            str = str2;
            arrayList4.add(new Keyframe(lottieComposition, valueOf, valueOf, null, 0.0f, Float.valueOf(f6)));
        } else {
            str = str2;
        }
        if (f5 <= 0.0f) {
            f5 = lottieComposition.endFrame;
        }
        arrayList4.add(new Keyframe(lottieComposition, valueOf2, valueOf2, null, f6, Float.valueOf(f5)));
        arrayList4.add(new Keyframe(lottieComposition, valueOf, valueOf, null, f5, Float.valueOf(Float.MAX_VALUE)));
        if (str4.endsWith(".ai") || "ai".equals(str)) {
            lottieComposition.addWarning("Convert your Illustrator layers to shape layers.");
        }
        return new Layer(arrayList2, lottieComposition, str4, j, layerType, j2, str3, arrayList, animatableTransform, i, i2, i3, f, f2, f3, f4, animatableTextFrame, animatableTextProperties, arrayList4, matteType2, animatableFloatValue5, z3, blurEffect, dropShadowEffect);
    }
}
