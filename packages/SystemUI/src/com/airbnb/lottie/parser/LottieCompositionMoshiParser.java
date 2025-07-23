package com.airbnb.lottie.parser;

import android.graphics.Rect;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonUtf8Reader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LottieCompositionMoshiParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.of("w", "h", "ip", "op", "fr", "v", "layers", "assets", "fonts", "chars", "markers");
    public static final JsonReader.Options ASSETS_NAMES = JsonReader.Options.of("id", "layers", "w", "h", "p", "u");
    public static final JsonReader.Options FONT_NAMES = JsonReader.Options.of("list");
    public static final JsonReader.Options MARKER_NAMES = JsonReader.Options.of("cm", "tm", "dr");

    public static LottieComposition parse(JsonUtf8Reader jsonUtf8Reader) {
        float f;
        ArrayList arrayList;
        SparseArrayCompat sparseArrayCompat;
        float f2;
        float f3;
        float dpScale = Utils.dpScale();
        LongSparseArray longSparseArray = new LongSparseArray();
        ArrayList arrayList2 = new ArrayList();
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        ArrayList arrayList3 = new ArrayList();
        SparseArrayCompat sparseArrayCompat2 = new SparseArrayCompat();
        LottieComposition lottieComposition = new LottieComposition();
        jsonUtf8Reader.beginObject();
        float f4 = 0.0f;
        int i = 0;
        int i2 = 0;
        float f5 = 0.0f;
        float f6 = 0.0f;
        while (jsonUtf8Reader.hasNext()) {
            switch (jsonUtf8Reader.selectName(NAMES)) {
                case 0:
                    i = jsonUtf8Reader.nextInt();
                    break;
                case 1:
                    i2 = jsonUtf8Reader.nextInt();
                    break;
                case 2:
                    f3 = dpScale;
                    f5 = (float) jsonUtf8Reader.nextDouble();
                    dpScale = f3;
                    break;
                case 3:
                    f3 = dpScale;
                    f4 = ((float) jsonUtf8Reader.nextDouble()) - 0.01f;
                    dpScale = f3;
                    break;
                case 4:
                    f3 = dpScale;
                    f6 = (float) jsonUtf8Reader.nextDouble();
                    dpScale = f3;
                    break;
                case 5:
                    f = dpScale;
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    f2 = f4;
                    String[] split = jsonUtf8Reader.nextString().split("\\.");
                    int parseInt = Integer.parseInt(split[0]);
                    int parseInt2 = Integer.parseInt(split[1]);
                    int parseInt3 = Integer.parseInt(split[2]);
                    if (parseInt < 4 || (parseInt <= 4 && (parseInt2 < 4 || (parseInt2 <= 4 && parseInt3 < 0)))) {
                        lottieComposition.addWarning("Lottie only supports bodymovin >= 4.4.0");
                    }
                    f4 = f2;
                    arrayList3 = arrayList;
                    dpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
                case 6:
                    f = dpScale;
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    f2 = f4;
                    jsonUtf8Reader.beginArray();
                    int i3 = 0;
                    while (jsonUtf8Reader.hasNext()) {
                        Layer parse = LayerParser.parse(jsonUtf8Reader, lottieComposition);
                        if (parse.layerType == Layer.LayerType.IMAGE) {
                            i3++;
                        }
                        arrayList2.add(parse);
                        longSparseArray.put(parse.layerId, parse);
                        if (i3 > 4) {
                            Logger.warning("You have " + i3 + " images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers.");
                        }
                    }
                    jsonUtf8Reader.endArray();
                    f4 = f2;
                    arrayList3 = arrayList;
                    dpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
                case 7:
                    f = dpScale;
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    f2 = f4;
                    jsonUtf8Reader.beginArray();
                    while (jsonUtf8Reader.hasNext()) {
                        ArrayList arrayList4 = new ArrayList();
                        LongSparseArray longSparseArray2 = new LongSparseArray();
                        jsonUtf8Reader.beginObject();
                        String str = null;
                        String str2 = null;
                        String str3 = null;
                        int i4 = 0;
                        int i5 = 0;
                        while (jsonUtf8Reader.hasNext()) {
                            int selectName = jsonUtf8Reader.selectName(ASSETS_NAMES);
                            if (selectName == 0) {
                                str = jsonUtf8Reader.nextString();
                            } else if (selectName == 1) {
                                jsonUtf8Reader.beginArray();
                                while (jsonUtf8Reader.hasNext()) {
                                    Layer parse2 = LayerParser.parse(jsonUtf8Reader, lottieComposition);
                                    longSparseArray2.put(parse2.layerId, parse2);
                                    arrayList4.add(parse2);
                                }
                                jsonUtf8Reader.endArray();
                            } else if (selectName == 2) {
                                i4 = jsonUtf8Reader.nextInt();
                            } else if (selectName == 3) {
                                i5 = jsonUtf8Reader.nextInt();
                            } else if (selectName == 4) {
                                str2 = jsonUtf8Reader.nextString();
                            } else if (selectName != 5) {
                                jsonUtf8Reader.skipName();
                                jsonUtf8Reader.skipValue();
                            } else {
                                str3 = jsonUtf8Reader.nextString();
                            }
                        }
                        jsonUtf8Reader.endObject();
                        if (str2 != null) {
                            LottieImageAsset lottieImageAsset = new LottieImageAsset(i4, i5, str, str2, str3);
                            hashMap2.put(lottieImageAsset.id, lottieImageAsset);
                        } else {
                            hashMap.put(str, arrayList4);
                        }
                    }
                    jsonUtf8Reader.endArray();
                    f4 = f2;
                    arrayList3 = arrayList;
                    dpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
                case 8:
                    f = dpScale;
                    f2 = f4;
                    jsonUtf8Reader.beginObject();
                    while (jsonUtf8Reader.hasNext()) {
                        if (jsonUtf8Reader.selectName(FONT_NAMES) != 0) {
                            jsonUtf8Reader.skipName();
                            jsonUtf8Reader.skipValue();
                        } else {
                            jsonUtf8Reader.beginArray();
                            while (jsonUtf8Reader.hasNext()) {
                                JsonReader.Options options = FontParser.NAMES;
                                jsonUtf8Reader.beginObject();
                                String str4 = null;
                                String str5 = null;
                                String str6 = null;
                                float f7 = 0.0f;
                                while (jsonUtf8Reader.hasNext()) {
                                    ArrayList arrayList5 = arrayList3;
                                    int selectName2 = jsonUtf8Reader.selectName(FontParser.NAMES);
                                    if (selectName2 != 0) {
                                        SparseArrayCompat sparseArrayCompat3 = sparseArrayCompat2;
                                        if (selectName2 == 1) {
                                            str5 = jsonUtf8Reader.nextString();
                                        } else if (selectName2 == 2) {
                                            str6 = jsonUtf8Reader.nextString();
                                        } else if (selectName2 != 3) {
                                            jsonUtf8Reader.skipName();
                                            jsonUtf8Reader.skipValue();
                                        } else {
                                            f7 = (float) jsonUtf8Reader.nextDouble();
                                        }
                                        arrayList3 = arrayList5;
                                        sparseArrayCompat2 = sparseArrayCompat3;
                                    } else {
                                        str4 = jsonUtf8Reader.nextString();
                                        arrayList3 = arrayList5;
                                    }
                                }
                                ArrayList arrayList6 = arrayList3;
                                jsonUtf8Reader.endObject();
                                Font font = new Font(str4, str5, str6, f7);
                                hashMap3.put(font.name, font);
                                arrayList3 = arrayList6;
                                sparseArrayCompat2 = sparseArrayCompat2;
                            }
                            jsonUtf8Reader.endArray();
                        }
                    }
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    jsonUtf8Reader.endObject();
                    f4 = f2;
                    arrayList3 = arrayList;
                    dpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
                case 9:
                    f = dpScale;
                    f2 = f4;
                    jsonUtf8Reader.beginArray();
                    while (jsonUtf8Reader.hasNext()) {
                        JsonReader.Options options2 = FontCharacterParser.NAMES;
                        ArrayList arrayList7 = new ArrayList();
                        jsonUtf8Reader.beginObject();
                        double d = 0.0d;
                        double d2 = 0.0d;
                        String str7 = null;
                        String str8 = null;
                        char c = 0;
                        while (jsonUtf8Reader.hasNext()) {
                            int selectName3 = jsonUtf8Reader.selectName(FontCharacterParser.NAMES);
                            if (selectName3 == 0) {
                                c = jsonUtf8Reader.nextString().charAt(0);
                            } else if (selectName3 == 1) {
                                d = jsonUtf8Reader.nextDouble();
                            } else if (selectName3 == 2) {
                                d2 = jsonUtf8Reader.nextDouble();
                            } else if (selectName3 == 3) {
                                str7 = jsonUtf8Reader.nextString();
                            } else if (selectName3 == 4) {
                                str8 = jsonUtf8Reader.nextString();
                            } else if (selectName3 != 5) {
                                jsonUtf8Reader.skipName();
                                jsonUtf8Reader.skipValue();
                            } else {
                                jsonUtf8Reader.beginObject();
                                while (jsonUtf8Reader.hasNext()) {
                                    if (jsonUtf8Reader.selectName(FontCharacterParser.DATA_NAMES) != 0) {
                                        jsonUtf8Reader.skipName();
                                        jsonUtf8Reader.skipValue();
                                    } else {
                                        jsonUtf8Reader.beginArray();
                                        while (jsonUtf8Reader.hasNext()) {
                                            arrayList7.add((ShapeGroup) ContentModelParser.parse(jsonUtf8Reader, lottieComposition));
                                        }
                                        jsonUtf8Reader.endArray();
                                    }
                                }
                                jsonUtf8Reader.endObject();
                            }
                        }
                        jsonUtf8Reader.endObject();
                        FontCharacter fontCharacter = new FontCharacter(arrayList7, c, d, d2, str7, str8);
                        sparseArrayCompat2.put(fontCharacter.hashCode(), fontCharacter);
                    }
                    jsonUtf8Reader.endArray();
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    f4 = f2;
                    arrayList3 = arrayList;
                    dpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
                case 10:
                    jsonUtf8Reader.beginArray();
                    while (jsonUtf8Reader.hasNext()) {
                        jsonUtf8Reader.beginObject();
                        String str9 = null;
                        float f8 = 0.0f;
                        float f9 = 0.0f;
                        while (jsonUtf8Reader.hasNext()) {
                            int selectName4 = jsonUtf8Reader.selectName(MARKER_NAMES);
                            if (selectName4 != 0) {
                                float f10 = dpScale;
                                if (selectName4 == 1) {
                                    f8 = (float) jsonUtf8Reader.nextDouble();
                                } else if (selectName4 != 2) {
                                    jsonUtf8Reader.skipName();
                                    jsonUtf8Reader.skipValue();
                                } else {
                                    f9 = (float) jsonUtf8Reader.nextDouble();
                                }
                                dpScale = f10;
                            } else {
                                str9 = jsonUtf8Reader.nextString();
                            }
                        }
                        float f11 = dpScale;
                        jsonUtf8Reader.endObject();
                        arrayList3.add(new Marker(str9, f8, f9));
                        f4 = f4;
                        dpScale = f11;
                    }
                    f = dpScale;
                    f2 = f4;
                    jsonUtf8Reader.endArray();
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    f4 = f2;
                    arrayList3 = arrayList;
                    dpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
                default:
                    jsonUtf8Reader.skipName();
                    jsonUtf8Reader.skipValue();
                    f = dpScale;
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    f2 = f4;
                    f4 = f2;
                    arrayList3 = arrayList;
                    dpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
            }
        }
        float f12 = dpScale;
        lottieComposition.bounds = new Rect(0, 0, (int) (i * f12), (int) (i2 * f12));
        lottieComposition.startFrame = f5;
        lottieComposition.endFrame = f4;
        lottieComposition.frameRate = f6;
        lottieComposition.layers = arrayList2;
        lottieComposition.layerMap = longSparseArray;
        lottieComposition.precomps = hashMap;
        lottieComposition.images = hashMap2;
        lottieComposition.characters = sparseArrayCompat2;
        lottieComposition.fonts = hashMap3;
        lottieComposition.markers = arrayList3;
        return lottieComposition;
    }
}
