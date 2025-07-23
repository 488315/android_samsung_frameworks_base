package com.airbnb.lottie.parser;

import android.graphics.Color;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LayerParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.of("nm", "ind", "refId", "ty", "parent", "sw", "sh", "sc", "ks", "tt", "masksProperties", "shapes", "t", "ef", "sr", "st", "w", "h", "ip", "op", "tm", "cl", "hd");
    public static final JsonReader.Options TEXT_NAMES = JsonReader.Options.of("d", "a");
    public static final JsonReader.Options EFFECTS_NAMES = JsonReader.Options.of("ty", "nm");

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.airbnb.lottie.parser.LayerParser$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
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
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v15 */
    /* JADX WARN: Type inference failed for: r14v18 */
    /* JADX WARN: Type inference failed for: r14v21 */
    /* JADX WARN: Type inference failed for: r14v22 */
    /* JADX WARN: Type inference failed for: r14v31 */
    /* JADX WARN: Type inference failed for: r14v53 */
    /* JADX WARN: Type inference failed for: r14v54 */
    /* JADX WARN: Type inference failed for: r14v55 */
    /* JADX WARN: Type inference failed for: r14v56 */
    /* JADX WARN: Type inference failed for: r14v57 */
    /* JADX WARN: Type inference failed for: r14v58 */
    /* JADX WARN: Type inference failed for: r14v61 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v24 */
    /* JADX WARN: Type inference failed for: r3v25 */
    /* JADX WARN: Type inference failed for: r3v34 */
    /* JADX WARN: Type inference failed for: r6v10, types: [com.airbnb.lottie.model.animatable.AnimatableShapeValue] */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v52 */
    /* JADX WARN: Type inference failed for: r6v53 */
    /* JADX WARN: Type inference failed for: r6v9, types: [com.airbnb.lottie.model.animatable.AnimatableShapeValue] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5, types: [com.airbnb.lottie.model.animatable.AnimatableIntegerValue] */
    /* JADX WARN: Type inference failed for: r8v6, types: [com.airbnb.lottie.model.animatable.AnimatableIntegerValue] */
    /* JADX WARN: Type inference failed for: r8v7 */
    public static Layer parse(JsonUtf8Reader jsonUtf8Reader, LottieComposition lottieComposition) {
        Float f;
        String str;
        String str2;
        boolean z;
        String str3;
        boolean z2;
        ?? r14;
        ?? r3;
        boolean z3;
        boolean z4;
        String str4;
        boolean z5;
        String str5;
        String str6;
        String str7;
        AnimatableFloatValue animatableFloatValue;
        AnimatableFloatValue animatableFloatValue2;
        AnimatableFloatValue animatableFloatValue3;
        AnimatableFloatValue animatableFloatValue4;
        ?? r142;
        String str8;
        boolean z6 = true;
        Layer.MatteType matteType = Layer.MatteType.NONE;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        jsonUtf8Reader.beginObject();
        Float valueOf = Float.valueOf(0.0f);
        Float valueOf2 = Float.valueOf(1.0f);
        String str9 = "UNSET";
        float f2 = 0.0f;
        boolean z7 = false;
        Layer.MatteType matteType2 = matteType;
        int i = 0;
        int i2 = 0;
        boolean z8 = false;
        float f3 = 0.0f;
        float f4 = 0.0f;
        long j = -1;
        String str10 = null;
        String str11 = null;
        Layer.LayerType layerType = null;
        BlurEffect blurEffect = null;
        DropShadowEffect dropShadowEffect = null;
        AnimatableTransform animatableTransform = null;
        AnimatableTextFrame animatableTextFrame = null;
        AnimatableTextProperties animatableTextProperties = null;
        AnimatableFloatValue animatableFloatValue5 = null;
        int i3 = 0;
        long j2 = 0;
        float f5 = 0.0f;
        float f6 = 1.0f;
        float f7 = 0.0f;
        while (jsonUtf8Reader.hasNext()) {
            switch (jsonUtf8Reader.selectName(NAMES)) {
                case 0:
                    z3 = z6;
                    str9 = jsonUtf8Reader.nextString();
                    z6 = z3;
                    break;
                case 1:
                    z3 = z6;
                    z4 = z7;
                    str4 = str11;
                    j2 = jsonUtf8Reader.nextInt();
                    str11 = str4;
                    z7 = z4;
                    z6 = z3;
                    break;
                case 2:
                    z3 = z6;
                    str11 = jsonUtf8Reader.nextString();
                    z6 = z3;
                    break;
                case 3:
                    z2 = z6;
                    str2 = str10;
                    z = z7;
                    str3 = str11;
                    int nextInt = jsonUtf8Reader.nextInt();
                    layerType = Layer.LayerType.UNKNOWN;
                    if (nextInt < layerType.ordinal()) {
                        layerType = Layer.LayerType.values()[nextInt];
                    }
                    z7 = z;
                    z6 = z2;
                    str10 = str2;
                    str11 = str3;
                    break;
                case 4:
                    z3 = z6;
                    z4 = z7;
                    str4 = str11;
                    j = jsonUtf8Reader.nextInt();
                    str11 = str4;
                    z7 = z4;
                    z6 = z3;
                    break;
                case 5:
                    z5 = z6;
                    str5 = str10;
                    i3 = (int) (Utils.dpScale() * jsonUtf8Reader.nextInt());
                    z6 = z5;
                    str10 = str5;
                    break;
                case 6:
                    z5 = z6;
                    str5 = str10;
                    i = (int) (Utils.dpScale() * jsonUtf8Reader.nextInt());
                    z6 = z5;
                    str10 = str5;
                    break;
                case 7:
                    z3 = z6;
                    i2 = Color.parseColor(jsonUtf8Reader.nextString());
                    z6 = z3;
                    break;
                case 8:
                    z3 = z6;
                    animatableTransform = AnimatableTransformParser.parse(jsonUtf8Reader, lottieComposition);
                    z6 = z3;
                    break;
                case 9:
                    str2 = str10;
                    z = z7;
                    str3 = str11;
                    int nextInt2 = jsonUtf8Reader.nextInt();
                    if (nextInt2 < Layer.MatteType.values().length) {
                        matteType2 = Layer.MatteType.values()[nextInt2];
                        int i4 = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[matteType2.ordinal()];
                        z2 = true;
                        if (i4 == 1) {
                            lottieComposition.addWarning("Unsupported matte type: Luma");
                        } else if (i4 == 2) {
                            lottieComposition.addWarning("Unsupported matte type: Luma Inverted");
                        }
                        lottieComposition.maskAndMatteCount++;
                        z7 = z;
                        z6 = z2;
                        str10 = str2;
                        str11 = str3;
                        break;
                    } else {
                        lottieComposition.addWarning("Unsupported matte type: " + nextInt2);
                        z7 = z;
                        str10 = str2;
                        str11 = str3;
                        z6 = true;
                        break;
                    }
                case 10:
                    str2 = str10;
                    str3 = str11;
                    Mask.MaskMode maskMode = null;
                    jsonUtf8Reader.beginArray();
                    while (jsonUtf8Reader.hasNext()) {
                        jsonUtf8Reader.beginObject();
                        Mask.MaskMode maskMode2 = maskMode;
                        Mask.MaskMode maskMode3 = maskMode2;
                        ?? r8 = maskMode3;
                        boolean z9 = false;
                        ?? r6 = maskMode3;
                        while (jsonUtf8Reader.hasNext()) {
                            String nextName = jsonUtf8Reader.nextName();
                            nextName.getClass();
                            switch (nextName.hashCode()) {
                                case 111:
                                    if (nextName.equals("o")) {
                                        r14 = 0;
                                        break;
                                    }
                                    r14 = -1;
                                    break;
                                case 3588:
                                    if (nextName.equals("pt")) {
                                        r14 = z6;
                                        break;
                                    }
                                    r14 = -1;
                                    break;
                                case 104433:
                                    if (nextName.equals("inv")) {
                                        r14 = 2;
                                        break;
                                    }
                                    r14 = -1;
                                    break;
                                case 3357091:
                                    if (nextName.equals("mode")) {
                                        r14 = 3;
                                        break;
                                    }
                                    r14 = -1;
                                    break;
                                default:
                                    r14 = -1;
                                    break;
                            }
                            switch (r14) {
                                case 0:
                                    r8 = AnimatableValueParser.parseInteger(jsonUtf8Reader, lottieComposition);
                                    break;
                                case 1:
                                    r6 = new AnimatableShapeValue(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, Utils.dpScale(), ShapeDataParser.INSTANCE, false));
                                    break;
                                case 2:
                                    z9 = jsonUtf8Reader.nextBoolean();
                                    break;
                                case 3:
                                    String nextString = jsonUtf8Reader.nextString();
                                    nextString.getClass();
                                    switch (nextString.hashCode()) {
                                        case 97:
                                            if (nextString.equals("a")) {
                                                r3 = 0;
                                                break;
                                            }
                                            r3 = -1;
                                            break;
                                        case 105:
                                            if (nextString.equals("i")) {
                                                r3 = z6;
                                                break;
                                            }
                                            r3 = -1;
                                            break;
                                        case 110:
                                            if (nextString.equals("n")) {
                                                r3 = 2;
                                                break;
                                            }
                                            r3 = -1;
                                            break;
                                        case 115:
                                            if (nextString.equals("s")) {
                                                r3 = 3;
                                                break;
                                            }
                                            r3 = -1;
                                            break;
                                        default:
                                            r3 = -1;
                                            break;
                                    }
                                    switch (r3) {
                                        case 0:
                                            maskMode2 = Mask.MaskMode.MASK_MODE_ADD;
                                            break;
                                        case 1:
                                            lottieComposition.addWarning("Animation contains intersect masks. They are not supported but will be treated like add masks.");
                                            maskMode2 = Mask.MaskMode.MASK_MODE_INTERSECT;
                                            break;
                                        case 2:
                                            maskMode2 = Mask.MaskMode.MASK_MODE_NONE;
                                            break;
                                        case 3:
                                            maskMode2 = Mask.MaskMode.MASK_MODE_SUBTRACT;
                                            break;
                                        default:
                                            Logger.warning("Unknown mask mode " + nextName + ". Defaulting to Add.");
                                            maskMode2 = Mask.MaskMode.MASK_MODE_ADD;
                                            break;
                                    }
                                    break;
                                default:
                                    jsonUtf8Reader.skipValue();
                                    break;
                            }
                            z6 = true;
                            r6 = r6;
                        }
                        jsonUtf8Reader.endObject();
                        arrayList.add(new Mask(maskMode2, r6, r8, z9));
                        z6 = true;
                        maskMode = null;
                    }
                    z = false;
                    lottieComposition.maskAndMatteCount += arrayList.size();
                    jsonUtf8Reader.endArray();
                    z7 = z;
                    str10 = str2;
                    str11 = str3;
                    z6 = true;
                    break;
                case 11:
                    str2 = str10;
                    str3 = str11;
                    jsonUtf8Reader.beginArray();
                    while (jsonUtf8Reader.hasNext()) {
                        ContentModel parse = ContentModelParser.parse(jsonUtf8Reader, lottieComposition);
                        if (parse != null) {
                            arrayList2.add(parse);
                        }
                    }
                    jsonUtf8Reader.endArray();
                    z = false;
                    z7 = z;
                    str10 = str2;
                    str11 = str3;
                    z6 = true;
                    break;
                case 12:
                    str6 = str10;
                    str7 = str11;
                    jsonUtf8Reader.beginObject();
                    while (jsonUtf8Reader.hasNext()) {
                        int selectName = jsonUtf8Reader.selectName(TEXT_NAMES);
                        if (selectName == 0) {
                            animatableTextFrame = new AnimatableTextFrame(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, Utils.dpScale(), DocumentDataParser.INSTANCE, false));
                        } else if (selectName != z6) {
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
                                            } else if (selectName2 == z6) {
                                                animatableColorValue2 = AnimatableValueParser.parseColor(jsonUtf8Reader, lottieComposition);
                                            } else if (selectName2 == 2) {
                                                animatableFloatValue6 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z6);
                                            } else if (selectName2 != 3) {
                                                jsonUtf8Reader.skipName();
                                                jsonUtf8Reader.skipValue();
                                            } else {
                                                animatableFloatValue7 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z6);
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
                    str10 = str6;
                    str11 = str7;
                    z7 = false;
                    break;
                case 13:
                    str6 = str10;
                    str7 = str11;
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
                                                boolean z10 = false;
                                                BlurEffect blurEffect2 = null;
                                                while (jsonUtf8Reader.hasNext()) {
                                                    int selectName4 = jsonUtf8Reader.selectName(BlurEffectParser.INNER_BLUR_EFFECT_NAMES);
                                                    if (selectName4 == 0) {
                                                        z10 = jsonUtf8Reader.nextInt() == 0 ? z6 : false;
                                                    } else if (selectName4 != z6) {
                                                        jsonUtf8Reader.skipName();
                                                        jsonUtf8Reader.skipValue();
                                                    } else if (z10) {
                                                        blurEffect2 = new BlurEffect(AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z6));
                                                    } else {
                                                        jsonUtf8Reader.skipValue();
                                                    }
                                                }
                                                jsonUtf8Reader.endObject();
                                                if (blurEffect2 != null) {
                                                    blurEffect = blurEffect2;
                                                }
                                            }
                                            jsonUtf8Reader.endArray();
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
                                                String str12 = "";
                                                while (jsonUtf8Reader.hasNext()) {
                                                    int selectName5 = jsonUtf8Reader.selectName(DropShadowEffectParser.INNER_EFFECT_NAMES);
                                                    if (selectName5 == 0) {
                                                        str12 = jsonUtf8Reader.nextString();
                                                    } else if (selectName5 == z6) {
                                                        str12.getClass();
                                                        switch (str12.hashCode()) {
                                                            case 353103893:
                                                                if (str12.equals("Distance")) {
                                                                    r142 = 0;
                                                                    break;
                                                                }
                                                                r142 = -1;
                                                                break;
                                                            case 397447147:
                                                                if (str12.equals("Opacity")) {
                                                                    r142 = z6;
                                                                    break;
                                                                }
                                                                r142 = -1;
                                                                break;
                                                            case 1041377119:
                                                                if (str12.equals("Direction")) {
                                                                    r142 = 2;
                                                                    break;
                                                                }
                                                                r142 = -1;
                                                                break;
                                                            case 1379387491:
                                                                if (str12.equals("Shadow Color")) {
                                                                    r142 = 3;
                                                                    break;
                                                                }
                                                                r142 = -1;
                                                                break;
                                                            case 1383710113:
                                                                if (str12.equals("Softness")) {
                                                                    r142 = 4;
                                                                    break;
                                                                }
                                                                r142 = -1;
                                                                break;
                                                            default:
                                                                r142 = -1;
                                                                break;
                                                        }
                                                        switch (r142) {
                                                            case 0:
                                                                dropShadowEffectParser.distance = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z6);
                                                                break;
                                                            case 1:
                                                                dropShadowEffectParser.opacity = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                                                                break;
                                                            case 2:
                                                                dropShadowEffectParser.direction = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                                                                break;
                                                            case 3:
                                                                dropShadowEffectParser.color = AnimatableValueParser.parseColor(jsonUtf8Reader, lottieComposition);
                                                                break;
                                                            case 4:
                                                                dropShadowEffectParser.radius = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z6);
                                                                break;
                                                            default:
                                                                jsonUtf8Reader.skipValue();
                                                                break;
                                                        }
                                                    } else {
                                                        jsonUtf8Reader.skipName();
                                                        jsonUtf8Reader.skipValue();
                                                    }
                                                }
                                                jsonUtf8Reader.endObject();
                                            }
                                            jsonUtf8Reader.endArray();
                                        }
                                    }
                                    AnimatableColorValue animatableColorValue3 = dropShadowEffectParser.color;
                                    dropShadowEffect = (animatableColorValue3 == null || (animatableFloatValue = dropShadowEffectParser.opacity) == null || (animatableFloatValue2 = dropShadowEffectParser.direction) == null || (animatableFloatValue3 = dropShadowEffectParser.distance) == null || (animatableFloatValue4 = dropShadowEffectParser.radius) == null) ? null : new DropShadowEffect(animatableColorValue3, animatableFloatValue, animatableFloatValue2, animatableFloatValue3, animatableFloatValue4);
                                }
                            } else if (selectName3 != z6) {
                                jsonUtf8Reader.skipName();
                                jsonUtf8Reader.skipValue();
                            } else {
                                arrayList3.add(jsonUtf8Reader.nextString());
                            }
                        }
                        jsonUtf8Reader.endObject();
                    }
                    jsonUtf8Reader.endArray();
                    lottieComposition.addWarning("Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: " + arrayList3);
                    str10 = str6;
                    str11 = str7;
                    z7 = false;
                    break;
                case 14:
                    str8 = str10;
                    f6 = (float) jsonUtf8Reader.nextDouble();
                    str10 = str8;
                    z7 = false;
                    break;
                case 15:
                    str8 = str10;
                    f4 = (float) jsonUtf8Reader.nextDouble();
                    str10 = str8;
                    z7 = false;
                    break;
                case 16:
                    str6 = str10;
                    str7 = str11;
                    f2 = (float) (jsonUtf8Reader.nextDouble() * Utils.dpScale());
                    str10 = str6;
                    str11 = str7;
                    z7 = false;
                    break;
                case 17:
                    str6 = str10;
                    str7 = str11;
                    f3 = (float) (jsonUtf8Reader.nextDouble() * Utils.dpScale());
                    str10 = str6;
                    str11 = str7;
                    z7 = false;
                    break;
                case 18:
                    str5 = str10;
                    f7 = (float) jsonUtf8Reader.nextDouble();
                    str10 = str5;
                    break;
                case 19:
                    str5 = str10;
                    f5 = (float) jsonUtf8Reader.nextDouble();
                    str10 = str5;
                    break;
                case 20:
                    animatableFloatValue5 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z7);
                    break;
                case 21:
                    str10 = jsonUtf8Reader.nextString();
                    break;
                case 22:
                    z8 = jsonUtf8Reader.nextBoolean();
                    break;
                default:
                    jsonUtf8Reader.skipName();
                    jsonUtf8Reader.skipValue();
                    str2 = str10;
                    z = z7;
                    str3 = str11;
                    z7 = z;
                    str10 = str2;
                    str11 = str3;
                    z6 = true;
                    break;
            }
        }
        String str13 = str10;
        String str14 = str11;
        jsonUtf8Reader.endObject();
        ArrayList arrayList4 = new ArrayList();
        if (f7 > 0.0f) {
            str = str13;
            f = valueOf;
            arrayList4.add(new Keyframe(lottieComposition, valueOf, valueOf, null, 0.0f, Float.valueOf(f7)));
        } else {
            f = valueOf;
            str = str13;
        }
        if (f5 <= 0.0f) {
            f5 = lottieComposition.endFrame;
        }
        arrayList4.add(new Keyframe(lottieComposition, valueOf2, valueOf2, null, f7, Float.valueOf(f5)));
        arrayList4.add(new Keyframe(lottieComposition, f, f, null, f5, Float.valueOf(Float.MAX_VALUE)));
        if (str9.endsWith(".ai") || "ai".equals(str)) {
            lottieComposition.addWarning("Convert your Illustrator layers to shape layers.");
        }
        return new Layer(arrayList2, lottieComposition, str9, j2, layerType, j, str14, arrayList, animatableTransform, i3, i, i2, f6, f4, f2, f3, animatableTextFrame, animatableTextProperties, arrayList4, matteType2, animatableFloatValue5, z8, blurEffect, dropShadowEffect);
    }
}
