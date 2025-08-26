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

/* loaded from: classes.dex */
public class LayerParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.of("nm", "ind", "refId", "ty", "parent", "sw", "sh", "sc", "ks", "tt", "masksProperties", "shapes", "t", "ef", "sr", "st", "w", "h", "ip", "op", "tm", "cl", "hd");
    public static final JsonReader.Options TEXT_NAMES = JsonReader.Options.of("d", "a");
    public static final JsonReader.Options EFFECTS_NAMES = JsonReader.Options.of("ty", "nm");

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
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x03d5  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01c4  */
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
        Float fValueOf = Float.valueOf(0.0f);
        Float fValueOf2 = Float.valueOf(1.0f);
        String strNextString = "UNSET";
        float fNextDouble = 0.0f;
        boolean z7 = false;
        Layer.MatteType matteType2 = matteType;
        int iDpScale = 0;
        int color = 0;
        boolean zNextBoolean = false;
        float fNextDouble2 = 0.0f;
        float fNextDouble3 = 0.0f;
        long jNextInt = -1;
        String strNextString2 = null;
        String strNextString3 = null;
        Layer.LayerType layerType = null;
        BlurEffect blurEffect = null;
        DropShadowEffect dropShadowEffect = null;
        AnimatableTransform animatableTransform = null;
        AnimatableTextFrame animatableTextFrame = null;
        AnimatableTextProperties animatableTextProperties = null;
        AnimatableFloatValue animatableFloatValue5 = null;
        int iDpScale2 = 0;
        long jNextInt2 = 0;
        float fNextDouble4 = 0.0f;
        float fNextDouble5 = 1.0f;
        float fNextDouble6 = 0.0f;
        while (jsonUtf8Reader.hasNext()) {
            switch (jsonUtf8Reader.selectName(NAMES)) {
                case 0:
                    z3 = z6;
                    strNextString = jsonUtf8Reader.nextString();
                    z6 = z3;
                    break;
                case 1:
                    z3 = z6;
                    z4 = z7;
                    str4 = strNextString3;
                    jNextInt2 = jsonUtf8Reader.nextInt();
                    strNextString3 = str4;
                    z7 = z4;
                    z6 = z3;
                    break;
                case 2:
                    z3 = z6;
                    strNextString3 = jsonUtf8Reader.nextString();
                    z6 = z3;
                    break;
                case 3:
                    z2 = z6;
                    str2 = strNextString2;
                    z = z7;
                    str3 = strNextString3;
                    int iNextInt = jsonUtf8Reader.nextInt();
                    layerType = Layer.LayerType.UNKNOWN;
                    if (iNextInt < layerType.ordinal()) {
                        layerType = Layer.LayerType.values()[iNextInt];
                    }
                    z7 = z;
                    z6 = z2;
                    strNextString2 = str2;
                    strNextString3 = str3;
                    break;
                case 4:
                    z3 = z6;
                    z4 = z7;
                    str4 = strNextString3;
                    jNextInt = jsonUtf8Reader.nextInt();
                    strNextString3 = str4;
                    z7 = z4;
                    z6 = z3;
                    break;
                case 5:
                    z5 = z6;
                    str5 = strNextString2;
                    iDpScale2 = (int) (Utils.dpScale() * jsonUtf8Reader.nextInt());
                    z6 = z5;
                    strNextString2 = str5;
                    break;
                case 6:
                    z5 = z6;
                    str5 = strNextString2;
                    iDpScale = (int) (Utils.dpScale() * jsonUtf8Reader.nextInt());
                    z6 = z5;
                    strNextString2 = str5;
                    break;
                case 7:
                    z3 = z6;
                    color = Color.parseColor(jsonUtf8Reader.nextString());
                    z6 = z3;
                    break;
                case 8:
                    z3 = z6;
                    animatableTransform = AnimatableTransformParser.parse(jsonUtf8Reader, lottieComposition);
                    z6 = z3;
                    break;
                case 9:
                    str2 = strNextString2;
                    z = z7;
                    str3 = strNextString3;
                    int iNextInt2 = jsonUtf8Reader.nextInt();
                    if (iNextInt2 < Layer.MatteType.values().length) {
                        matteType2 = Layer.MatteType.values()[iNextInt2];
                        int i = AnonymousClass1.$SwitchMap$com$airbnb$lottie$model$layer$Layer$MatteType[matteType2.ordinal()];
                        z2 = true;
                        if (i == 1) {
                            lottieComposition.addWarning("Unsupported matte type: Luma");
                        } else if (i == 2) {
                            lottieComposition.addWarning("Unsupported matte type: Luma Inverted");
                        }
                        lottieComposition.maskAndMatteCount++;
                        z7 = z;
                        z6 = z2;
                        strNextString2 = str2;
                        strNextString3 = str3;
                        break;
                    } else {
                        lottieComposition.addWarning("Unsupported matte type: " + iNextInt2);
                        z7 = z;
                        strNextString2 = str2;
                        strNextString3 = str3;
                        z6 = true;
                        break;
                    }
                case 10:
                    str2 = strNextString2;
                    str3 = strNextString3;
                    Mask.MaskMode maskMode = null;
                    jsonUtf8Reader.beginArray();
                    while (jsonUtf8Reader.hasNext()) {
                        jsonUtf8Reader.beginObject();
                        Mask.MaskMode maskMode2 = maskMode;
                        Mask.MaskMode maskMode3 = maskMode2;
                        ?? integer = maskMode3;
                        boolean zNextBoolean2 = false;
                        ?? animatableShapeValue = maskMode3;
                        while (jsonUtf8Reader.hasNext()) {
                            String strNextName = jsonUtf8Reader.nextName();
                            strNextName.getClass();
                            switch (strNextName.hashCode()) {
                                case 111:
                                    if (!strNextName.equals("o")) {
                                        r14 = -1;
                                        break;
                                    } else {
                                        r14 = 0;
                                        break;
                                    }
                                case 3588:
                                    if (strNextName.equals("pt")) {
                                        r14 = z6;
                                        break;
                                    }
                                    break;
                                case 104433:
                                    if (strNextName.equals("inv")) {
                                        r14 = 2;
                                        break;
                                    }
                                    break;
                                case 3357091:
                                    if (strNextName.equals("mode")) {
                                        r14 = 3;
                                        break;
                                    }
                                    break;
                            }
                            switch (r14) {
                                case 0:
                                    integer = AnimatableValueParser.parseInteger(jsonUtf8Reader, lottieComposition);
                                    break;
                                case 1:
                                    animatableShapeValue = new AnimatableShapeValue(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, Utils.dpScale(), ShapeDataParser.INSTANCE, false));
                                    break;
                                case 2:
                                    zNextBoolean2 = jsonUtf8Reader.nextBoolean();
                                    break;
                                case 3:
                                    String strNextString4 = jsonUtf8Reader.nextString();
                                    strNextString4.getClass();
                                    switch (strNextString4.hashCode()) {
                                        case 97:
                                            if (!strNextString4.equals("a")) {
                                                r3 = -1;
                                                break;
                                            } else {
                                                r3 = 0;
                                                break;
                                            }
                                        case 105:
                                            if (strNextString4.equals("i")) {
                                                r3 = z6;
                                                break;
                                            }
                                            break;
                                        case 110:
                                            if (strNextString4.equals("n")) {
                                                r3 = 2;
                                                break;
                                            }
                                            break;
                                        case 115:
                                            if (strNextString4.equals("s")) {
                                                r3 = 3;
                                                break;
                                            }
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
                                            Logger.warning("Unknown mask mode " + strNextName + ". Defaulting to Add.");
                                            maskMode2 = Mask.MaskMode.MASK_MODE_ADD;
                                            break;
                                    }
                                    break;
                                default:
                                    jsonUtf8Reader.skipValue();
                                    break;
                            }
                            z6 = true;
                            animatableShapeValue = animatableShapeValue;
                        }
                        jsonUtf8Reader.endObject();
                        arrayList.add(new Mask(maskMode2, animatableShapeValue, integer, zNextBoolean2));
                        z6 = true;
                        maskMode = null;
                    }
                    z = false;
                    lottieComposition.maskAndMatteCount += arrayList.size();
                    jsonUtf8Reader.endArray();
                    z7 = z;
                    strNextString2 = str2;
                    strNextString3 = str3;
                    z6 = true;
                    break;
                case 11:
                    str2 = strNextString2;
                    str3 = strNextString3;
                    jsonUtf8Reader.beginArray();
                    while (jsonUtf8Reader.hasNext()) {
                        ContentModel contentModel = ContentModelParser.parse(jsonUtf8Reader, lottieComposition);
                        if (contentModel != null) {
                            arrayList2.add(contentModel);
                        }
                    }
                    jsonUtf8Reader.endArray();
                    z = false;
                    z7 = z;
                    strNextString2 = str2;
                    strNextString3 = str3;
                    z6 = true;
                    break;
                case 12:
                    str6 = strNextString2;
                    str7 = strNextString3;
                    jsonUtf8Reader.beginObject();
                    while (jsonUtf8Reader.hasNext()) {
                        int iSelectName = jsonUtf8Reader.selectName(TEXT_NAMES);
                        if (iSelectName == 0) {
                            animatableTextFrame = new AnimatableTextFrame(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, Utils.dpScale(), DocumentDataParser.INSTANCE, false));
                        } else if (iSelectName != z6) {
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
                                        AnimatableColorValue color2 = null;
                                        AnimatableColorValue color3 = null;
                                        AnimatableFloatValue animatableFloatValue6 = null;
                                        AnimatableFloatValue animatableFloatValue7 = null;
                                        while (jsonUtf8Reader.hasNext()) {
                                            int iSelectName2 = jsonUtf8Reader.selectName(AnimatableTextPropertiesParser.ANIMATABLE_PROPERTIES_NAMES);
                                            if (iSelectName2 == 0) {
                                                color2 = AnimatableValueParser.parseColor(jsonUtf8Reader, lottieComposition);
                                            } else if (iSelectName2 == z6) {
                                                color3 = AnimatableValueParser.parseColor(jsonUtf8Reader, lottieComposition);
                                            } else if (iSelectName2 == 2) {
                                                animatableFloatValue6 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z6);
                                            } else if (iSelectName2 != 3) {
                                                jsonUtf8Reader.skipName();
                                                jsonUtf8Reader.skipValue();
                                            } else {
                                                animatableFloatValue7 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z6);
                                            }
                                        }
                                        jsonUtf8Reader.endObject();
                                        animatableTextProperties2 = new AnimatableTextProperties(color2, color3, animatableFloatValue6, animatableFloatValue7);
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
                    strNextString2 = str6;
                    strNextString3 = str7;
                    z7 = false;
                    break;
                case 13:
                    str6 = strNextString2;
                    str7 = strNextString3;
                    jsonUtf8Reader.beginArray();
                    ArrayList arrayList3 = new ArrayList();
                    while (jsonUtf8Reader.hasNext()) {
                        jsonUtf8Reader.beginObject();
                        while (jsonUtf8Reader.hasNext()) {
                            int iSelectName3 = jsonUtf8Reader.selectName(EFFECTS_NAMES);
                            if (iSelectName3 == 0) {
                                int iNextInt3 = jsonUtf8Reader.nextInt();
                                if (iNextInt3 == 29) {
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
                                                boolean z8 = false;
                                                BlurEffect blurEffect2 = null;
                                                while (jsonUtf8Reader.hasNext()) {
                                                    int iSelectName4 = jsonUtf8Reader.selectName(BlurEffectParser.INNER_BLUR_EFFECT_NAMES);
                                                    if (iSelectName4 == 0) {
                                                        z8 = jsonUtf8Reader.nextInt() == 0 ? z6 : false;
                                                    } else if (iSelectName4 != z6) {
                                                        jsonUtf8Reader.skipName();
                                                        jsonUtf8Reader.skipValue();
                                                    } else if (z8) {
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
                                } else if (iNextInt3 == 25) {
                                    DropShadowEffectParser dropShadowEffectParser = new DropShadowEffectParser();
                                    while (jsonUtf8Reader.hasNext()) {
                                        if (jsonUtf8Reader.selectName(DropShadowEffectParser.DROP_SHADOW_EFFECT_NAMES) != 0) {
                                            jsonUtf8Reader.skipName();
                                            jsonUtf8Reader.skipValue();
                                        } else {
                                            jsonUtf8Reader.beginArray();
                                            while (jsonUtf8Reader.hasNext()) {
                                                jsonUtf8Reader.beginObject();
                                                String strNextString5 = "";
                                                while (jsonUtf8Reader.hasNext()) {
                                                    int iSelectName5 = jsonUtf8Reader.selectName(DropShadowEffectParser.INNER_EFFECT_NAMES);
                                                    if (iSelectName5 == 0) {
                                                        strNextString5 = jsonUtf8Reader.nextString();
                                                    } else if (iSelectName5 == z6) {
                                                        strNextString5.getClass();
                                                        switch (strNextString5.hashCode()) {
                                                            case 353103893:
                                                                if (!strNextString5.equals("Distance")) {
                                                                    r142 = -1;
                                                                    break;
                                                                } else {
                                                                    r142 = 0;
                                                                    break;
                                                                }
                                                            case 397447147:
                                                                if (strNextString5.equals("Opacity")) {
                                                                    r142 = z6;
                                                                    break;
                                                                }
                                                                break;
                                                            case 1041377119:
                                                                if (strNextString5.equals("Direction")) {
                                                                    r142 = 2;
                                                                    break;
                                                                }
                                                                break;
                                                            case 1379387491:
                                                                if (strNextString5.equals("Shadow Color")) {
                                                                    r142 = 3;
                                                                    break;
                                                                }
                                                                break;
                                                            case 1383710113:
                                                                if (strNextString5.equals("Softness")) {
                                                                    r142 = 4;
                                                                    break;
                                                                }
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
                                    AnimatableColorValue animatableColorValue = dropShadowEffectParser.color;
                                    dropShadowEffect = (animatableColorValue == null || (animatableFloatValue = dropShadowEffectParser.opacity) == null || (animatableFloatValue2 = dropShadowEffectParser.direction) == null || (animatableFloatValue3 = dropShadowEffectParser.distance) == null || (animatableFloatValue4 = dropShadowEffectParser.radius) == null) ? null : new DropShadowEffect(animatableColorValue, animatableFloatValue, animatableFloatValue2, animatableFloatValue3, animatableFloatValue4);
                                }
                            } else if (iSelectName3 != z6) {
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
                    strNextString2 = str6;
                    strNextString3 = str7;
                    z7 = false;
                    break;
                case 14:
                    str8 = strNextString2;
                    fNextDouble5 = (float) jsonUtf8Reader.nextDouble();
                    strNextString2 = str8;
                    z7 = false;
                    break;
                case 15:
                    str8 = strNextString2;
                    fNextDouble3 = (float) jsonUtf8Reader.nextDouble();
                    strNextString2 = str8;
                    z7 = false;
                    break;
                case 16:
                    str6 = strNextString2;
                    str7 = strNextString3;
                    fNextDouble = (float) (jsonUtf8Reader.nextDouble() * Utils.dpScale());
                    strNextString2 = str6;
                    strNextString3 = str7;
                    z7 = false;
                    break;
                case 17:
                    str6 = strNextString2;
                    str7 = strNextString3;
                    fNextDouble2 = (float) (jsonUtf8Reader.nextDouble() * Utils.dpScale());
                    strNextString2 = str6;
                    strNextString3 = str7;
                    z7 = false;
                    break;
                case 18:
                    str5 = strNextString2;
                    fNextDouble6 = (float) jsonUtf8Reader.nextDouble();
                    strNextString2 = str5;
                    break;
                case 19:
                    str5 = strNextString2;
                    fNextDouble4 = (float) jsonUtf8Reader.nextDouble();
                    strNextString2 = str5;
                    break;
                case 20:
                    animatableFloatValue5 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, z7);
                    break;
                case 21:
                    strNextString2 = jsonUtf8Reader.nextString();
                    break;
                case 22:
                    zNextBoolean = jsonUtf8Reader.nextBoolean();
                    break;
                default:
                    jsonUtf8Reader.skipName();
                    jsonUtf8Reader.skipValue();
                    str2 = strNextString2;
                    z = z7;
                    str3 = strNextString3;
                    z7 = z;
                    strNextString2 = str2;
                    strNextString3 = str3;
                    z6 = true;
                    break;
            }
        }
        String str9 = strNextString2;
        String str10 = strNextString3;
        jsonUtf8Reader.endObject();
        ArrayList arrayList4 = new ArrayList();
        if (fNextDouble6 > 0.0f) {
            str = str9;
            f = fValueOf;
            arrayList4.add(new Keyframe(lottieComposition, fValueOf, fValueOf, null, 0.0f, Float.valueOf(fNextDouble6)));
        } else {
            f = fValueOf;
            str = str9;
        }
        if (fNextDouble4 <= 0.0f) {
            fNextDouble4 = lottieComposition.endFrame;
        }
        arrayList4.add(new Keyframe(lottieComposition, fValueOf2, fValueOf2, null, fNextDouble6, Float.valueOf(fNextDouble4)));
        arrayList4.add(new Keyframe(lottieComposition, f, f, null, fNextDouble4, Float.valueOf(Float.MAX_VALUE)));
        if (strNextString.endsWith(".ai") || "ai".equals(str)) {
            lottieComposition.addWarning("Convert your Illustrator layers to shape layers.");
        }
        return new Layer(arrayList2, lottieComposition, strNextString, jNextInt2, layerType, jNextInt, str10, arrayList, animatableTransform, iDpScale2, iDpScale, color, fNextDouble5, fNextDouble3, fNextDouble, fNextDouble2, animatableTextFrame, animatableTextProperties, arrayList4, matteType2, animatableFloatValue5, zNextBoolean, blurEffect, dropShadowEffect);
    }
}
