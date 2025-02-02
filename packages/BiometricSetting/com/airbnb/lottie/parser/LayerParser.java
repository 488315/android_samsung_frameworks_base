package com.airbnb.lottie.parser;

import android.graphics.Color;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatableShapeValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class LayerParser {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final JsonReader.Options NAMES = JsonReader.Options.m8of("nm", "ind", "refId", "ty", "parent", "sw", "sh", "sc", "ks", "tt", "masksProperties", "shapes", "t", "ef", "sr", "st", "w", "h", "ip", "op", "tm", "cl", "hd");
    private static final JsonReader.Options TEXT_NAMES = JsonReader.Options.m8of("d", "a");
    private static final JsonReader.Options EFFECTS_NAMES = JsonReader.Options.m8of("nm");

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:5:0x0057. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0205  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Layer parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        ArrayList arrayList;
        ArrayList arrayList2;
        String str;
        String str2;
        long j;
        JsonReader jsonReader2;
        String str3;
        long j2;
        JsonReader jsonReader3;
        char c;
        String str4;
        long j3;
        char c2;
        Layer.MatteType matteType = Layer.MatteType.NONE;
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        jsonReader.beginObject();
        Float valueOf = Float.valueOf(1.0f);
        Float valueOf2 = Float.valueOf(0.0f);
        boolean z = false;
        Layer.MatteType matteType2 = matteType;
        float f = 1.0f;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        boolean z2 = false;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        long j4 = -1;
        Layer.LayerType layerType = null;
        String str5 = null;
        AnimatableTransform animatableTransform = null;
        AnimatableTextFrame animatableTextFrame = null;
        AnimatableTextProperties animatableTextProperties = null;
        AnimatableFloatValue animatableFloatValue = null;
        String str6 = "UNSET";
        long j5 = 0;
        String str7 = null;
        JsonReader jsonReader4 = jsonReader;
        while (jsonReader.hasNext()) {
            switch (jsonReader4.selectName(NAMES)) {
                case 0:
                    str2 = str7;
                    j = j5;
                    jsonReader2 = jsonReader;
                    str6 = jsonReader.nextString();
                    jsonReader4 = jsonReader2;
                    str7 = str2;
                    j5 = j;
                    break;
                case 1:
                    jsonReader2 = jsonReader;
                    str2 = str7;
                    j = jsonReader.nextInt();
                    jsonReader4 = jsonReader2;
                    str7 = str2;
                    j5 = j;
                    break;
                case 2:
                    str2 = str7;
                    j = j5;
                    jsonReader2 = jsonReader;
                    str5 = jsonReader.nextString();
                    jsonReader4 = jsonReader2;
                    str7 = str2;
                    j5 = j;
                    break;
                case 3:
                    str2 = str7;
                    j = j5;
                    jsonReader2 = jsonReader;
                    int nextInt = jsonReader.nextInt();
                    layerType = nextInt < 6 ? Layer.LayerType.values()[nextInt] : Layer.LayerType.UNKNOWN;
                    jsonReader4 = jsonReader2;
                    str7 = str2;
                    j5 = j;
                    break;
                case 4:
                    j = j5;
                    j4 = jsonReader.nextInt();
                    jsonReader4 = jsonReader;
                    j5 = j;
                    break;
                case 5:
                    str3 = str7;
                    j2 = j5;
                    jsonReader3 = jsonReader;
                    i = (int) (Utils.dpScale() * jsonReader.nextInt());
                    jsonReader4 = jsonReader3;
                    j5 = j2;
                    str7 = str3;
                    break;
                case 6:
                    str3 = str7;
                    j2 = j5;
                    jsonReader3 = jsonReader;
                    i2 = (int) (Utils.dpScale() * jsonReader.nextInt());
                    jsonReader4 = jsonReader3;
                    j5 = j2;
                    str7 = str3;
                    break;
                case 7:
                    str2 = str7;
                    j = j5;
                    jsonReader2 = jsonReader;
                    i3 = Color.parseColor(jsonReader.nextString());
                    jsonReader4 = jsonReader2;
                    str7 = str2;
                    j5 = j;
                    break;
                case 8:
                    str2 = str7;
                    j = j5;
                    jsonReader2 = jsonReader;
                    animatableTransform = AnimatableTransformParser.parse(jsonReader, lottieComposition);
                    jsonReader4 = jsonReader2;
                    str7 = str2;
                    j5 = j;
                    break;
                case 9:
                    str2 = str7;
                    j = j5;
                    jsonReader2 = jsonReader;
                    int nextInt2 = jsonReader.nextInt();
                    if (nextInt2 >= Layer.MatteType.values().length) {
                        lottieComposition.addWarning("Unsupported matte type: " + nextInt2);
                    } else {
                        matteType2 = Layer.MatteType.values()[nextInt2];
                        int ordinal = matteType2.ordinal();
                        if (ordinal == 3) {
                            lottieComposition.addWarning("Unsupported matte type: Luma");
                        } else if (ordinal == 4) {
                            lottieComposition.addWarning("Unsupported matte type: Luma Inverted");
                        }
                        lottieComposition.incrementMatteOrMaskCount(1);
                    }
                    jsonReader4 = jsonReader2;
                    str7 = str2;
                    j5 = j;
                    break;
                case 10:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        AnimatableIntegerValue animatableIntegerValue = null;
                        Mask.MaskMode maskMode = null;
                        boolean z3 = false;
                        AnimatableShapeValue animatableShapeValue = null;
                        while (jsonReader.hasNext()) {
                            String nextName = jsonReader.nextName();
                            nextName.getClass();
                            switch (nextName.hashCode()) {
                                case 111:
                                    if (nextName.equals("o")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3588:
                                    if (nextName.equals("pt")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 104433:
                                    if (nextName.equals("inv")) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3357091:
                                    if (nextName.equals("mode")) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    c = 65535;
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    str4 = str7;
                                    j3 = j5;
                                    animatableIntegerValue = AnimatableValueParser.parseInteger(jsonReader, lottieComposition);
                                    j5 = j3;
                                    str7 = str4;
                                    break;
                                case 1:
                                    str4 = str7;
                                    j3 = j5;
                                    animatableShapeValue = new AnimatableShapeValue(KeyframesParser.parse(Utils.dpScale(), lottieComposition, ShapeDataParser.INSTANCE, jsonReader));
                                    j5 = j3;
                                    str7 = str4;
                                    break;
                                case 2:
                                    str4 = str7;
                                    z3 = jsonReader.nextBoolean();
                                    j3 = j5;
                                    j5 = j3;
                                    str7 = str4;
                                    break;
                                case 3:
                                    String nextString = jsonReader.nextString();
                                    nextString.getClass();
                                    int hashCode = nextString.hashCode();
                                    str4 = str7;
                                    if (hashCode == 97) {
                                        if (nextString.equals("a")) {
                                            c2 = 0;
                                            Mask.MaskMode maskMode2 = Mask.MaskMode.MASK_MODE_ADD;
                                            if (c2 != 0) {
                                            }
                                            maskMode = maskMode2;
                                            j3 = j5;
                                        }
                                        c2 = 65535;
                                        Mask.MaskMode maskMode22 = Mask.MaskMode.MASK_MODE_ADD;
                                        if (c2 != 0) {
                                        }
                                        maskMode = maskMode22;
                                        j3 = j5;
                                    } else if (hashCode == 105) {
                                        if (nextString.equals("i")) {
                                            c2 = 1;
                                            Mask.MaskMode maskMode222 = Mask.MaskMode.MASK_MODE_ADD;
                                            if (c2 != 0) {
                                            }
                                            maskMode = maskMode222;
                                            j3 = j5;
                                        }
                                        c2 = 65535;
                                        Mask.MaskMode maskMode2222 = Mask.MaskMode.MASK_MODE_ADD;
                                        if (c2 != 0) {
                                        }
                                        maskMode = maskMode2222;
                                        j3 = j5;
                                    } else if (hashCode != 110) {
                                        if (hashCode == 115 && nextString.equals("s")) {
                                            c2 = 3;
                                            Mask.MaskMode maskMode22222 = Mask.MaskMode.MASK_MODE_ADD;
                                            if (c2 != 0) {
                                                if (c2 == 1) {
                                                    lottieComposition.addWarning("Animation contains intersect masks. They are not supported but will be treated like add masks.");
                                                    maskMode = Mask.MaskMode.MASK_MODE_INTERSECT;
                                                } else if (c2 == 2) {
                                                    maskMode = Mask.MaskMode.MASK_MODE_NONE;
                                                } else if (c2 != 3) {
                                                    Logger.warning("Unknown mask mode " + nextName + ". Defaulting to Add.");
                                                } else {
                                                    maskMode = Mask.MaskMode.MASK_MODE_SUBTRACT;
                                                }
                                                j3 = j5;
                                            }
                                            maskMode = maskMode22222;
                                            j3 = j5;
                                        }
                                        c2 = 65535;
                                        Mask.MaskMode maskMode222222 = Mask.MaskMode.MASK_MODE_ADD;
                                        if (c2 != 0) {
                                        }
                                        maskMode = maskMode222222;
                                        j3 = j5;
                                    } else {
                                        if (nextString.equals("n")) {
                                            c2 = 2;
                                            Mask.MaskMode maskMode2222222 = Mask.MaskMode.MASK_MODE_ADD;
                                            if (c2 != 0) {
                                            }
                                            maskMode = maskMode2222222;
                                            j3 = j5;
                                        }
                                        c2 = 65535;
                                        Mask.MaskMode maskMode22222222 = Mask.MaskMode.MASK_MODE_ADD;
                                        if (c2 != 0) {
                                        }
                                        maskMode = maskMode22222222;
                                        j3 = j5;
                                    }
                                    j5 = j3;
                                    str7 = str4;
                                    break;
                                default:
                                    jsonReader.skipValue();
                                    break;
                            }
                        }
                        jsonReader.endObject();
                        arrayList3.add(new Mask(maskMode, animatableShapeValue, animatableIntegerValue, z3));
                        j5 = j5;
                        str7 = str7;
                    }
                    str2 = str7;
                    j = j5;
                    jsonReader2 = jsonReader;
                    lottieComposition.incrementMatteOrMaskCount(arrayList3.size());
                    jsonReader.endArray();
                    jsonReader4 = jsonReader2;
                    str7 = str2;
                    j5 = j;
                    break;
                case 11:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        ContentModel parse = ContentModelParser.parse(jsonReader, lottieComposition);
                        if (parse != null) {
                            arrayList4.add(parse);
                        }
                    }
                    jsonReader.endArray();
                    str2 = str7;
                    j = j5;
                    jsonReader2 = jsonReader;
                    jsonReader4 = jsonReader2;
                    str7 = str2;
                    j5 = j;
                    break;
                case 12:
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        int selectName = jsonReader4.selectName(TEXT_NAMES);
                        if (selectName == 0) {
                            animatableTextFrame = AnimatableValueParser.parseDocumentData(jsonReader, lottieComposition);
                        } else if (selectName != 1) {
                            jsonReader.skipName();
                            jsonReader.skipValue();
                        } else {
                            jsonReader.beginArray();
                            if (jsonReader.hasNext()) {
                                animatableTextProperties = AnimatableTextPropertiesParser.parse(jsonReader, lottieComposition);
                            }
                            while (jsonReader.hasNext()) {
                                jsonReader.skipValue();
                            }
                            jsonReader.endArray();
                        }
                    }
                    jsonReader.endObject();
                    j = j5;
                    j5 = j;
                    break;
                case 13:
                    jsonReader.beginArray();
                    ArrayList arrayList5 = new ArrayList();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            if (jsonReader4.selectName(EFFECTS_NAMES) != 0) {
                                jsonReader.skipName();
                                jsonReader.skipValue();
                            } else {
                                arrayList5.add(jsonReader.nextString());
                            }
                        }
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();
                    lottieComposition.addWarning("Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: " + arrayList5);
                    str2 = str7;
                    j = j5;
                    jsonReader2 = jsonReader;
                    jsonReader4 = jsonReader2;
                    str7 = str2;
                    j5 = j;
                    break;
                case 14:
                    f = (float) jsonReader.nextDouble();
                    j = j5;
                    j5 = j;
                    break;
                case 15:
                    f4 = (float) jsonReader.nextDouble();
                    j = j5;
                    j5 = j;
                    break;
                case 16:
                    i4 = (int) (Utils.dpScale() * jsonReader.nextInt());
                    j = j5;
                    j5 = j;
                    break;
                case 17:
                    i5 = (int) (Utils.dpScale() * jsonReader.nextInt());
                    j = j5;
                    j5 = j;
                    break;
                case 18:
                    f2 = (float) jsonReader.nextDouble();
                    j = j5;
                    j5 = j;
                    break;
                case 19:
                    f3 = (float) jsonReader.nextDouble();
                    j = j5;
                    j5 = j;
                    break;
                case 20:
                    animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader4, lottieComposition, z);
                    j = j5;
                    j5 = j;
                    break;
                case 21:
                    str7 = jsonReader.nextString();
                    j = j5;
                    j5 = j;
                    break;
                case 22:
                    z2 = jsonReader.nextBoolean();
                    j = j5;
                    j5 = j;
                    break;
                default:
                    str2 = str7;
                    j = j5;
                    jsonReader2 = jsonReader;
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    jsonReader4 = jsonReader2;
                    str7 = str2;
                    j5 = j;
                    break;
            }
            z = false;
        }
        String str8 = str7;
        long j6 = j5;
        jsonReader.endObject();
        float f5 = f2 / f;
        float f6 = f3 / f;
        ArrayList arrayList6 = new ArrayList();
        if (f5 > 0.0f) {
            arrayList2 = arrayList3;
            str = str8;
            arrayList = arrayList4;
            arrayList6.add(new Keyframe(lottieComposition, valueOf2, valueOf2, null, 0.0f, Float.valueOf(f5)));
        } else {
            arrayList = arrayList4;
            arrayList2 = arrayList3;
            str = str8;
        }
        if (f6 <= 0.0f) {
            f6 = lottieComposition.getEndFrame();
        }
        arrayList6.add(new Keyframe(lottieComposition, valueOf, valueOf, null, f5, Float.valueOf(f6)));
        arrayList6.add(new Keyframe(lottieComposition, valueOf2, valueOf2, null, f6, Float.valueOf(Float.MAX_VALUE)));
        if (str6.endsWith(".ai") || "ai".equals(str)) {
            lottieComposition.addWarning("Convert your Illustrator layers to shape layers.");
        }
        return new Layer(arrayList, lottieComposition, str6, j6, layerType, j4, str5, arrayList2, animatableTransform, i, i2, i3, f, f4, i4, i5, animatableTextFrame, animatableTextProperties, arrayList6, matteType2, animatableFloatValue, z2);
    }
}
