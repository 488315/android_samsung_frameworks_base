package com.airbnb.lottie.parser;

import android.graphics.Rect;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes.dex */
public final class LottieCompositionMoshiParser {
    private static final JsonReader.Options NAMES = JsonReader.Options.m8of("w", "h", "ip", "op", "fr", "v", "layers", "assets", "fonts", "chars", "markers");
    static JsonReader.Options ASSETS_NAMES = JsonReader.Options.m8of("id", "layers", "w", "h", "p", "u");
    private static final JsonReader.Options FONT_NAMES = JsonReader.Options.m8of("list");
    private static final JsonReader.Options MARKER_NAMES = JsonReader.Options.m8of("cm", "tm", "dr");

    /* JADX WARN: Failed to find 'out' block for switch in B:5:0x0046. Please report as an issue. */
    public static LottieComposition parse(JsonReader jsonReader) throws IOException {
        int i;
        float f;
        SparseArrayCompat<FontCharacter> sparseArrayCompat;
        HashMap hashMap;
        ArrayList arrayList;
        LongSparseArray<Layer> longSparseArray;
        float f2;
        float f3;
        ArrayList arrayList2;
        LongSparseArray<Layer> longSparseArray2;
        ArrayList arrayList3;
        LongSparseArray<Layer> longSparseArray3;
        float f4;
        SparseArrayCompat<FontCharacter> sparseArrayCompat2;
        HashMap hashMap2;
        ArrayList arrayList4;
        LongSparseArray<Layer> longSparseArray4;
        float dpScale = Utils.dpScale();
        LongSparseArray<Layer> longSparseArray5 = new LongSparseArray<>();
        ArrayList arrayList5 = new ArrayList();
        HashMap hashMap3 = new HashMap();
        HashMap hashMap4 = new HashMap();
        HashMap hashMap5 = new HashMap();
        ArrayList arrayList6 = new ArrayList();
        SparseArrayCompat<FontCharacter> sparseArrayCompat3 = new SparseArrayCompat<>();
        LottieComposition lottieComposition = new LottieComposition();
        jsonReader.beginObject();
        int i2 = 0;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        int i3 = 0;
        while (jsonReader.hasNext()) {
            float f8 = f7;
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    f4 = f6;
                    sparseArrayCompat2 = sparseArrayCompat3;
                    hashMap2 = hashMap5;
                    arrayList4 = arrayList6;
                    longSparseArray4 = longSparseArray5;
                    i3 = jsonReader.nextInt();
                    longSparseArray5 = longSparseArray4;
                    f7 = f8;
                    arrayList6 = arrayList4;
                    f6 = f4;
                    hashMap5 = hashMap2;
                    sparseArrayCompat3 = sparseArrayCompat2;
                    break;
                case 1:
                    f4 = f6;
                    sparseArrayCompat2 = sparseArrayCompat3;
                    hashMap2 = hashMap5;
                    arrayList4 = arrayList6;
                    longSparseArray4 = longSparseArray5;
                    i2 = jsonReader.nextInt();
                    longSparseArray5 = longSparseArray4;
                    f7 = f8;
                    arrayList6 = arrayList4;
                    f6 = f4;
                    hashMap5 = hashMap2;
                    sparseArrayCompat3 = sparseArrayCompat2;
                    break;
                case 2:
                    f4 = f6;
                    sparseArrayCompat2 = sparseArrayCompat3;
                    hashMap2 = hashMap5;
                    arrayList4 = arrayList6;
                    longSparseArray4 = longSparseArray5;
                    f5 = (float) jsonReader.nextDouble();
                    i2 = i2;
                    longSparseArray5 = longSparseArray4;
                    f7 = f8;
                    arrayList6 = arrayList4;
                    f6 = f4;
                    hashMap5 = hashMap2;
                    sparseArrayCompat3 = sparseArrayCompat2;
                    break;
                case 3:
                    i = i2;
                    f = f5;
                    sparseArrayCompat = sparseArrayCompat3;
                    hashMap = hashMap5;
                    arrayList = arrayList6;
                    longSparseArray = longSparseArray5;
                    f6 = ((float) jsonReader.nextDouble()) - 0.01f;
                    f7 = f8;
                    longSparseArray5 = longSparseArray;
                    f5 = f;
                    arrayList6 = arrayList;
                    hashMap5 = hashMap;
                    sparseArrayCompat3 = sparseArrayCompat;
                    i2 = i;
                    break;
                case 4:
                    i = i2;
                    f = f5;
                    sparseArrayCompat = sparseArrayCompat3;
                    hashMap = hashMap5;
                    arrayList = arrayList6;
                    longSparseArray = longSparseArray5;
                    f7 = (float) jsonReader.nextDouble();
                    longSparseArray5 = longSparseArray;
                    f5 = f;
                    arrayList6 = arrayList;
                    hashMap5 = hashMap;
                    sparseArrayCompat3 = sparseArrayCompat;
                    i2 = i;
                    break;
                case 5:
                    i = i2;
                    f2 = f5;
                    f3 = f6;
                    sparseArrayCompat = sparseArrayCompat3;
                    hashMap = hashMap5;
                    arrayList2 = arrayList6;
                    longSparseArray2 = longSparseArray5;
                    String[] split = jsonReader.nextString().split("\\.");
                    int parseInt = Integer.parseInt(split[0]);
                    int parseInt2 = Integer.parseInt(split[1]);
                    if (!(parseInt >= 4 && (parseInt > 4 || (parseInt2 >= 4 && (parseInt2 > 4 || Integer.parseInt(split[2]) >= 0))))) {
                        lottieComposition.addWarning("Lottie only supports bodymovin >= 4.4.0");
                    }
                    longSparseArray5 = longSparseArray2;
                    f7 = f8;
                    f5 = f2;
                    arrayList6 = arrayList2;
                    f6 = f3;
                    hashMap5 = hashMap;
                    sparseArrayCompat3 = sparseArrayCompat;
                    i2 = i;
                    break;
                case 6:
                    i = i2;
                    f2 = f5;
                    f3 = f6;
                    ArrayList arrayList7 = arrayList5;
                    sparseArrayCompat = sparseArrayCompat3;
                    hashMap = hashMap5;
                    arrayList2 = arrayList6;
                    longSparseArray2 = longSparseArray5;
                    jsonReader.beginArray();
                    int i4 = 0;
                    while (jsonReader.hasNext()) {
                        Layer parse = LayerParser.parse(jsonReader, lottieComposition);
                        if (parse.getLayerType() == Layer.LayerType.IMAGE) {
                            i4++;
                        }
                        ArrayList arrayList8 = arrayList7;
                        arrayList8.add(parse);
                        longSparseArray2.put(parse.getId(), parse);
                        if (i4 > 4) {
                            Logger.warning("You have " + i4 + " images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers.");
                        }
                        arrayList7 = arrayList8;
                    }
                    arrayList5 = arrayList7;
                    jsonReader.endArray();
                    longSparseArray5 = longSparseArray2;
                    f7 = f8;
                    f5 = f2;
                    arrayList6 = arrayList2;
                    f6 = f3;
                    hashMap5 = hashMap;
                    sparseArrayCompat3 = sparseArrayCompat;
                    i2 = i;
                    break;
                case 7:
                    f2 = f5;
                    f3 = f6;
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        ArrayList arrayList9 = new ArrayList();
                        LongSparseArray longSparseArray6 = new LongSparseArray();
                        jsonReader.beginObject();
                        HashMap hashMap6 = hashMap5;
                        ArrayList arrayList10 = arrayList6;
                        String str = null;
                        String str2 = null;
                        int i5 = 0;
                        int i6 = 0;
                        while (jsonReader.hasNext()) {
                            SparseArrayCompat<FontCharacter> sparseArrayCompat4 = sparseArrayCompat3;
                            int selectName = jsonReader.selectName(ASSETS_NAMES);
                            if (selectName != 0) {
                                int i7 = i2;
                                if (selectName != 1) {
                                    if (selectName == 2) {
                                        i5 = jsonReader.nextInt();
                                    } else if (selectName == 3) {
                                        i6 = jsonReader.nextInt();
                                    } else if (selectName == 4) {
                                        str2 = jsonReader.nextString();
                                    } else if (selectName != 5) {
                                        jsonReader.skipName();
                                        jsonReader.skipValue();
                                        arrayList3 = arrayList5;
                                        longSparseArray3 = longSparseArray5;
                                    } else {
                                        jsonReader.nextString();
                                    }
                                    sparseArrayCompat3 = sparseArrayCompat4;
                                    i2 = i7;
                                } else {
                                    jsonReader.beginArray();
                                    while (jsonReader.hasNext()) {
                                        Layer parse2 = LayerParser.parse(jsonReader, lottieComposition);
                                        longSparseArray6.put(parse2.getId(), parse2);
                                        arrayList9.add(parse2);
                                        longSparseArray5 = longSparseArray5;
                                        arrayList5 = arrayList5;
                                    }
                                    arrayList3 = arrayList5;
                                    longSparseArray3 = longSparseArray5;
                                    jsonReader.endArray();
                                }
                                longSparseArray5 = longSparseArray3;
                                sparseArrayCompat3 = sparseArrayCompat4;
                                i2 = i7;
                                arrayList5 = arrayList3;
                            } else {
                                str = jsonReader.nextString();
                                sparseArrayCompat3 = sparseArrayCompat4;
                            }
                        }
                        int i8 = i2;
                        ArrayList arrayList11 = arrayList5;
                        SparseArrayCompat<FontCharacter> sparseArrayCompat5 = sparseArrayCompat3;
                        LongSparseArray<Layer> longSparseArray7 = longSparseArray5;
                        jsonReader.endObject();
                        if (str2 != null) {
                            LottieImageAsset lottieImageAsset = new LottieImageAsset(i5, i6, str, str2);
                            hashMap4.put(lottieImageAsset.getId(), lottieImageAsset);
                        } else {
                            hashMap3.put(str, arrayList9);
                        }
                        longSparseArray5 = longSparseArray7;
                        arrayList6 = arrayList10;
                        hashMap5 = hashMap6;
                        sparseArrayCompat3 = sparseArrayCompat5;
                        i2 = i8;
                        arrayList5 = arrayList11;
                    }
                    i = i2;
                    sparseArrayCompat = sparseArrayCompat3;
                    hashMap = hashMap5;
                    arrayList2 = arrayList6;
                    longSparseArray2 = longSparseArray5;
                    jsonReader.endArray();
                    longSparseArray5 = longSparseArray2;
                    f7 = f8;
                    f5 = f2;
                    arrayList6 = arrayList2;
                    f6 = f3;
                    hashMap5 = hashMap;
                    sparseArrayCompat3 = sparseArrayCompat;
                    i2 = i;
                    break;
                case 8:
                    f2 = f5;
                    f3 = f6;
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        if (jsonReader.selectName(FONT_NAMES) != 0) {
                            jsonReader.skipName();
                            jsonReader.skipValue();
                        } else {
                            jsonReader.beginArray();
                            while (jsonReader.hasNext()) {
                                Font parse3 = FontParser.parse(jsonReader);
                                hashMap5.put(parse3.getName(), parse3);
                            }
                            jsonReader.endArray();
                        }
                    }
                    jsonReader.endObject();
                    i = i2;
                    sparseArrayCompat = sparseArrayCompat3;
                    hashMap = hashMap5;
                    arrayList2 = arrayList6;
                    longSparseArray2 = longSparseArray5;
                    longSparseArray5 = longSparseArray2;
                    f7 = f8;
                    f5 = f2;
                    arrayList6 = arrayList2;
                    f6 = f3;
                    hashMap5 = hashMap;
                    sparseArrayCompat3 = sparseArrayCompat;
                    i2 = i;
                    break;
                case 9:
                    f2 = f5;
                    f3 = f6;
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        FontCharacter parse4 = FontCharacterParser.parse(jsonReader, lottieComposition);
                        sparseArrayCompat3.put(parse4.hashCode(), parse4);
                    }
                    jsonReader.endArray();
                    i = i2;
                    sparseArrayCompat = sparseArrayCompat3;
                    hashMap = hashMap5;
                    arrayList2 = arrayList6;
                    longSparseArray2 = longSparseArray5;
                    longSparseArray5 = longSparseArray2;
                    f7 = f8;
                    f5 = f2;
                    arrayList6 = arrayList2;
                    f6 = f3;
                    hashMap5 = hashMap;
                    sparseArrayCompat3 = sparseArrayCompat;
                    i2 = i;
                    break;
                case 10:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        String str3 = null;
                        float f9 = 0.0f;
                        float f10 = 0.0f;
                        while (jsonReader.hasNext()) {
                            int selectName2 = jsonReader.selectName(MARKER_NAMES);
                            if (selectName2 != 0) {
                                float f11 = f6;
                                if (selectName2 == 1) {
                                    f9 = (float) jsonReader.nextDouble();
                                } else if (selectName2 != 2) {
                                    jsonReader.skipName();
                                    jsonReader.skipValue();
                                } else {
                                    f10 = (float) jsonReader.nextDouble();
                                }
                                f6 = f11;
                            } else {
                                str3 = jsonReader.nextString();
                            }
                        }
                        jsonReader.endObject();
                        arrayList6.add(new Marker(str3, f9, f10));
                        f5 = f5;
                        f6 = f6;
                    }
                    f2 = f5;
                    f3 = f6;
                    jsonReader.endArray();
                    i = i2;
                    sparseArrayCompat = sparseArrayCompat3;
                    hashMap = hashMap5;
                    arrayList2 = arrayList6;
                    longSparseArray2 = longSparseArray5;
                    longSparseArray5 = longSparseArray2;
                    f7 = f8;
                    f5 = f2;
                    arrayList6 = arrayList2;
                    f6 = f3;
                    hashMap5 = hashMap;
                    sparseArrayCompat3 = sparseArrayCompat;
                    i2 = i;
                    break;
                default:
                    i = i2;
                    f2 = f5;
                    f3 = f6;
                    sparseArrayCompat = sparseArrayCompat3;
                    hashMap = hashMap5;
                    arrayList2 = arrayList6;
                    longSparseArray2 = longSparseArray5;
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    longSparseArray5 = longSparseArray2;
                    f7 = f8;
                    f5 = f2;
                    arrayList6 = arrayList2;
                    f6 = f3;
                    hashMap5 = hashMap;
                    sparseArrayCompat3 = sparseArrayCompat;
                    i2 = i;
                    break;
            }
        }
        lottieComposition.init(new Rect(0, 0, (int) (i3 * dpScale), (int) (i2 * dpScale)), f5, f6, f7, arrayList5, longSparseArray5, hashMap3, hashMap4, sparseArrayCompat3, hashMap5, arrayList6);
        return lottieComposition;
    }
}
