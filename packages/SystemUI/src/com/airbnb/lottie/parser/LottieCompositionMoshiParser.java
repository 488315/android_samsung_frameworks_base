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

/* loaded from: classes.dex */
public class LottieCompositionMoshiParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.of("w", "h", "ip", "op", "fr", "v", "layers", "assets", "fonts", "chars", "markers");
    public static final JsonReader.Options ASSETS_NAMES = JsonReader.Options.of("id", "layers", "w", "h", "p", "u");
    public static final JsonReader.Options FONT_NAMES = JsonReader.Options.of("list");
    public static final JsonReader.Options MARKER_NAMES = JsonReader.Options.of("cm", "tm", "dr");

    public static LottieComposition parse(JsonUtf8Reader jsonUtf8Reader) throws NumberFormatException {
        float f;
        ArrayList arrayList;
        SparseArrayCompat sparseArrayCompat;
        float f2;
        float f3;
        float fDpScale = Utils.dpScale();
        LongSparseArray longSparseArray = new LongSparseArray();
        ArrayList arrayList2 = new ArrayList();
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        ArrayList arrayList3 = new ArrayList();
        SparseArrayCompat sparseArrayCompat2 = new SparseArrayCompat();
        LottieComposition lottieComposition = new LottieComposition();
        jsonUtf8Reader.beginObject();
        float fNextDouble = 0.0f;
        int iNextInt = 0;
        int iNextInt2 = 0;
        float fNextDouble2 = 0.0f;
        float fNextDouble3 = 0.0f;
        while (jsonUtf8Reader.hasNext()) {
            switch (jsonUtf8Reader.selectName(NAMES)) {
                case 0:
                    iNextInt = jsonUtf8Reader.nextInt();
                    break;
                case 1:
                    iNextInt2 = jsonUtf8Reader.nextInt();
                    break;
                case 2:
                    f3 = fDpScale;
                    fNextDouble2 = (float) jsonUtf8Reader.nextDouble();
                    fDpScale = f3;
                    break;
                case 3:
                    f3 = fDpScale;
                    fNextDouble = ((float) jsonUtf8Reader.nextDouble()) - 0.01f;
                    fDpScale = f3;
                    break;
                case 4:
                    f3 = fDpScale;
                    fNextDouble3 = (float) jsonUtf8Reader.nextDouble();
                    fDpScale = f3;
                    break;
                case 5:
                    f = fDpScale;
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    f2 = fNextDouble;
                    String[] strArrSplit = jsonUtf8Reader.nextString().split("\\.");
                    int i = Integer.parseInt(strArrSplit[0]);
                    int i2 = Integer.parseInt(strArrSplit[1]);
                    int i3 = Integer.parseInt(strArrSplit[2]);
                    if (i < 4 || (i <= 4 && (i2 < 4 || (i2 <= 4 && i3 < 0)))) {
                        lottieComposition.addWarning("Lottie only supports bodymovin >= 4.4.0");
                    }
                    fNextDouble = f2;
                    arrayList3 = arrayList;
                    fDpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
                case 6:
                    f = fDpScale;
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    f2 = fNextDouble;
                    jsonUtf8Reader.beginArray();
                    int i4 = 0;
                    while (jsonUtf8Reader.hasNext()) {
                        Layer layer = LayerParser.parse(jsonUtf8Reader, lottieComposition);
                        if (layer.layerType == Layer.LayerType.IMAGE) {
                            i4++;
                        }
                        arrayList2.add(layer);
                        longSparseArray.put(layer.layerId, layer);
                        if (i4 > 4) {
                            Logger.warning("You have " + i4 + " images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers.");
                        }
                    }
                    jsonUtf8Reader.endArray();
                    fNextDouble = f2;
                    arrayList3 = arrayList;
                    fDpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
                case 7:
                    f = fDpScale;
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    f2 = fNextDouble;
                    jsonUtf8Reader.beginArray();
                    while (jsonUtf8Reader.hasNext()) {
                        ArrayList arrayList4 = new ArrayList();
                        LongSparseArray longSparseArray2 = new LongSparseArray();
                        jsonUtf8Reader.beginObject();
                        String strNextString = null;
                        String strNextString2 = null;
                        String strNextString3 = null;
                        int iNextInt3 = 0;
                        int iNextInt4 = 0;
                        while (jsonUtf8Reader.hasNext()) {
                            int iSelectName = jsonUtf8Reader.selectName(ASSETS_NAMES);
                            if (iSelectName == 0) {
                                strNextString = jsonUtf8Reader.nextString();
                            } else if (iSelectName == 1) {
                                jsonUtf8Reader.beginArray();
                                while (jsonUtf8Reader.hasNext()) {
                                    Layer layer2 = LayerParser.parse(jsonUtf8Reader, lottieComposition);
                                    longSparseArray2.put(layer2.layerId, layer2);
                                    arrayList4.add(layer2);
                                }
                                jsonUtf8Reader.endArray();
                            } else if (iSelectName == 2) {
                                iNextInt3 = jsonUtf8Reader.nextInt();
                            } else if (iSelectName == 3) {
                                iNextInt4 = jsonUtf8Reader.nextInt();
                            } else if (iSelectName == 4) {
                                strNextString2 = jsonUtf8Reader.nextString();
                            } else if (iSelectName != 5) {
                                jsonUtf8Reader.skipName();
                                jsonUtf8Reader.skipValue();
                            } else {
                                strNextString3 = jsonUtf8Reader.nextString();
                            }
                        }
                        jsonUtf8Reader.endObject();
                        if (strNextString2 != null) {
                            LottieImageAsset lottieImageAsset = new LottieImageAsset(iNextInt3, iNextInt4, strNextString, strNextString2, strNextString3);
                            map2.put(lottieImageAsset.id, lottieImageAsset);
                        } else {
                            map.put(strNextString, arrayList4);
                        }
                    }
                    jsonUtf8Reader.endArray();
                    fNextDouble = f2;
                    arrayList3 = arrayList;
                    fDpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
                case 8:
                    f = fDpScale;
                    f2 = fNextDouble;
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
                                String strNextString4 = null;
                                String strNextString5 = null;
                                String strNextString6 = null;
                                float fNextDouble4 = 0.0f;
                                while (jsonUtf8Reader.hasNext()) {
                                    ArrayList arrayList5 = arrayList3;
                                    int iSelectName2 = jsonUtf8Reader.selectName(FontParser.NAMES);
                                    if (iSelectName2 != 0) {
                                        SparseArrayCompat sparseArrayCompat3 = sparseArrayCompat2;
                                        if (iSelectName2 == 1) {
                                            strNextString5 = jsonUtf8Reader.nextString();
                                        } else if (iSelectName2 == 2) {
                                            strNextString6 = jsonUtf8Reader.nextString();
                                        } else if (iSelectName2 != 3) {
                                            jsonUtf8Reader.skipName();
                                            jsonUtf8Reader.skipValue();
                                        } else {
                                            fNextDouble4 = (float) jsonUtf8Reader.nextDouble();
                                        }
                                        arrayList3 = arrayList5;
                                        sparseArrayCompat2 = sparseArrayCompat3;
                                    } else {
                                        strNextString4 = jsonUtf8Reader.nextString();
                                        arrayList3 = arrayList5;
                                    }
                                }
                                ArrayList arrayList6 = arrayList3;
                                jsonUtf8Reader.endObject();
                                Font font = new Font(strNextString4, strNextString5, strNextString6, fNextDouble4);
                                map3.put(font.name, font);
                                arrayList3 = arrayList6;
                                sparseArrayCompat2 = sparseArrayCompat2;
                            }
                            jsonUtf8Reader.endArray();
                        }
                    }
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    jsonUtf8Reader.endObject();
                    fNextDouble = f2;
                    arrayList3 = arrayList;
                    fDpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
                case 9:
                    f = fDpScale;
                    f2 = fNextDouble;
                    jsonUtf8Reader.beginArray();
                    while (jsonUtf8Reader.hasNext()) {
                        JsonReader.Options options2 = FontCharacterParser.NAMES;
                        ArrayList arrayList7 = new ArrayList();
                        jsonUtf8Reader.beginObject();
                        double dNextDouble = 0.0d;
                        double dNextDouble2 = 0.0d;
                        String strNextString7 = null;
                        String strNextString8 = null;
                        char cCharAt = 0;
                        while (jsonUtf8Reader.hasNext()) {
                            int iSelectName3 = jsonUtf8Reader.selectName(FontCharacterParser.NAMES);
                            if (iSelectName3 == 0) {
                                cCharAt = jsonUtf8Reader.nextString().charAt(0);
                            } else if (iSelectName3 == 1) {
                                dNextDouble = jsonUtf8Reader.nextDouble();
                            } else if (iSelectName3 == 2) {
                                dNextDouble2 = jsonUtf8Reader.nextDouble();
                            } else if (iSelectName3 == 3) {
                                strNextString7 = jsonUtf8Reader.nextString();
                            } else if (iSelectName3 == 4) {
                                strNextString8 = jsonUtf8Reader.nextString();
                            } else if (iSelectName3 != 5) {
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
                        FontCharacter fontCharacter = new FontCharacter(arrayList7, cCharAt, dNextDouble, dNextDouble2, strNextString7, strNextString8);
                        sparseArrayCompat2.put(fontCharacter.hashCode(), fontCharacter);
                    }
                    jsonUtf8Reader.endArray();
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    fNextDouble = f2;
                    arrayList3 = arrayList;
                    fDpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
                case 10:
                    jsonUtf8Reader.beginArray();
                    while (jsonUtf8Reader.hasNext()) {
                        jsonUtf8Reader.beginObject();
                        String strNextString9 = null;
                        float fNextDouble5 = 0.0f;
                        float fNextDouble6 = 0.0f;
                        while (jsonUtf8Reader.hasNext()) {
                            int iSelectName4 = jsonUtf8Reader.selectName(MARKER_NAMES);
                            if (iSelectName4 != 0) {
                                float f4 = fDpScale;
                                if (iSelectName4 == 1) {
                                    fNextDouble5 = (float) jsonUtf8Reader.nextDouble();
                                } else if (iSelectName4 != 2) {
                                    jsonUtf8Reader.skipName();
                                    jsonUtf8Reader.skipValue();
                                } else {
                                    fNextDouble6 = (float) jsonUtf8Reader.nextDouble();
                                }
                                fDpScale = f4;
                            } else {
                                strNextString9 = jsonUtf8Reader.nextString();
                            }
                        }
                        float f5 = fDpScale;
                        jsonUtf8Reader.endObject();
                        arrayList3.add(new Marker(strNextString9, fNextDouble5, fNextDouble6));
                        fNextDouble = fNextDouble;
                        fDpScale = f5;
                    }
                    f = fDpScale;
                    f2 = fNextDouble;
                    jsonUtf8Reader.endArray();
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    fNextDouble = f2;
                    arrayList3 = arrayList;
                    fDpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
                default:
                    jsonUtf8Reader.skipName();
                    jsonUtf8Reader.skipValue();
                    f = fDpScale;
                    arrayList = arrayList3;
                    sparseArrayCompat = sparseArrayCompat2;
                    f2 = fNextDouble;
                    fNextDouble = f2;
                    arrayList3 = arrayList;
                    fDpScale = f;
                    sparseArrayCompat2 = sparseArrayCompat;
                    break;
            }
        }
        float f6 = fDpScale;
        lottieComposition.bounds = new Rect(0, 0, (int) (iNextInt * f6), (int) (iNextInt2 * f6));
        lottieComposition.startFrame = fNextDouble2;
        lottieComposition.endFrame = fNextDouble;
        lottieComposition.frameRate = fNextDouble3;
        lottieComposition.layers = arrayList2;
        lottieComposition.layerMap = longSparseArray;
        lottieComposition.precomps = map;
        lottieComposition.images = map2;
        lottieComposition.characters = sparseArrayCompat2;
        lottieComposition.fonts = map3;
        lottieComposition.markers = arrayList3;
        return lottieComposition;
    }
}
