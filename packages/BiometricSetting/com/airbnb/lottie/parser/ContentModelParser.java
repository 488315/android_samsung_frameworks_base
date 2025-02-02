package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableShapeValue;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.ShapePath;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import java.io.IOException;

/* loaded from: classes.dex */
final class ContentModelParser {
    private static JsonReader.Options NAMES = JsonReader.Options.m8of("ty", "d");

    static ContentModel parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        String str;
        String str2;
        int i;
        ContentModel parse;
        jsonReader.beginObject();
        int i2 = 2;
        while (true) {
            str = null;
            parse = null;
            if (!jsonReader.hasNext()) {
                str2 = null;
                break;
            }
            int selectName = jsonReader.selectName(NAMES);
            if (selectName == 0) {
                str2 = jsonReader.nextString();
                break;
            }
            if (selectName != 1) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                i2 = jsonReader.nextInt();
            }
        }
        if (str2 == null) {
            return null;
        }
        i = 0;
        switch (str2) {
            case "el":
                parse = CircleShapeParser.parse(jsonReader, lottieComposition, i2);
                break;
            case "fl":
                parse = ShapeFillParser.parse(jsonReader, lottieComposition);
                break;
            case "gf":
                parse = GradientFillParser.parse(jsonReader, lottieComposition);
                break;
            case "gr":
                parse = ShapeGroupParser.parse(jsonReader, lottieComposition);
                break;
            case "gs":
                parse = GradientStrokeParser.parse(jsonReader, lottieComposition);
                break;
            case "mm":
                parse = MergePathsParser.parse(jsonReader);
                lottieComposition.addWarning("Animation contains merge paths. Merge paths are only supported on KitKat+ and must be manually enabled by calling enableMergePathsForKitKatAndAbove().");
                break;
            case "rc":
                parse = RectangleShapeParser.parse(jsonReader, lottieComposition);
                break;
            case "rp":
                parse = RepeaterParser.parse(jsonReader, lottieComposition);
                break;
            case "sh":
                JsonReader.Options options = ShapePathParser.NAMES;
                AnimatableShapeValue animatableShapeValue = null;
                boolean z = false;
                while (jsonReader.hasNext()) {
                    int selectName2 = jsonReader.selectName(ShapePathParser.NAMES);
                    if (selectName2 == 0) {
                        str = jsonReader.nextString();
                    } else if (selectName2 == 1) {
                        i = jsonReader.nextInt();
                    } else if (selectName2 == 2) {
                        animatableShapeValue = new AnimatableShapeValue(KeyframesParser.parse(Utils.dpScale(), lottieComposition, ShapeDataParser.INSTANCE, jsonReader));
                    } else if (selectName2 != 3) {
                        jsonReader.skipValue();
                    } else {
                        z = jsonReader.nextBoolean();
                    }
                }
                parse = new ShapePath(str, i, animatableShapeValue, z);
                break;
            case "sr":
                parse = PolystarShapeParser.parse(jsonReader, lottieComposition);
                break;
            case "st":
                parse = ShapeStrokeParser.parse(jsonReader, lottieComposition);
                break;
            case "tm":
                parse = ShapeTrimPathParser.parse(jsonReader, lottieComposition);
                break;
            case "tr":
                parse = AnimatableTransformParser.parse(jsonReader, lottieComposition);
                break;
            default:
                Logger.warning("Unknown shape type ".concat(str2));
                break;
        }
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        jsonReader.endObject();
        return parse;
    }
}
