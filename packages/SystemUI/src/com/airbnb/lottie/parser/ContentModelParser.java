package com.airbnb.lottie.parser;

import android.graphics.Path;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableShapeValue;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.CircleShape;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.GradientFill;
import com.airbnb.lottie.model.content.GradientStroke;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.content.MergePaths;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.model.content.RectangleShape;
import com.airbnb.lottie.model.content.Repeater;
import com.airbnb.lottie.model.content.RoundedCorners;
import com.airbnb.lottie.model.content.ShapeFill;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.model.content.ShapePath;
import com.airbnb.lottie.model.content.ShapeStroke;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.parser.moshi.JsonEncodingException;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonUtf8Reader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes.dex */
public class ContentModelParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.of("ty", "d");

    private ContentModelParser() {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x0753 A[LOOP:1: B:424:0x074d->B:426:0x0753, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ContentModel parse(JsonUtf8Reader jsonUtf8Reader, LottieComposition lottieComposition) throws JsonEncodingException, EOFException {
        AnimatableIntegerValue animatableIntegerValue;
        String strNextString;
        ContentModel circleShape;
        ContentModel shapeFill;
        AnimatableIntegerValue animatableIntegerValue2;
        ContentModel gradientFill;
        String strNextString2;
        ContentModel shapeGroup;
        String strNextString3;
        String strNextString4;
        AnimatableFloatValue animatableFloatValue;
        ShapeTrimPath.Type type;
        boolean zNextBoolean = false;
        jsonUtf8Reader.beginObject();
        int iNextInt = 2;
        while (true) {
            animatableIntegerValue = null;
            shapeFill = null;
            strNextString4 = null;
            shapeFill = null;
            strNextString3 = null;
            strNextString2 = null;
            animatableIntegerValue2 = null;
            if (!jsonUtf8Reader.hasNext()) {
                strNextString = null;
                break;
            }
            int iSelectName = jsonUtf8Reader.selectName(NAMES);
            if (iSelectName == 0) {
                strNextString = jsonUtf8Reader.nextString();
                break;
            }
            if (iSelectName != 1) {
                jsonUtf8Reader.skipName();
                jsonUtf8Reader.skipValue();
            } else {
                iNextInt = jsonUtf8Reader.nextInt();
            }
        }
        if (strNextString == null) {
            return null;
        }
        switch (strNextString) {
            case "el":
                JsonReader.Options options = CircleShapeParser.NAMES;
                boolean z = iNextInt == 3;
                boolean zNextBoolean2 = false;
                String strNextString5 = null;
                AnimatableValue splitPath = null;
                AnimatablePointValue point = null;
                while (jsonUtf8Reader.hasNext()) {
                    int iSelectName2 = jsonUtf8Reader.selectName(CircleShapeParser.NAMES);
                    if (iSelectName2 == 0) {
                        strNextString5 = jsonUtf8Reader.nextString();
                    } else if (iSelectName2 == 1) {
                        splitPath = AnimatablePathValueParser.parseSplitPath(jsonUtf8Reader, lottieComposition);
                    } else if (iSelectName2 == 2) {
                        point = AnimatableValueParser.parsePoint(jsonUtf8Reader, lottieComposition);
                    } else if (iSelectName2 == 3) {
                        zNextBoolean2 = jsonUtf8Reader.nextBoolean();
                    } else if (iSelectName2 != 4) {
                        jsonUtf8Reader.skipName();
                        jsonUtf8Reader.skipValue();
                    } else {
                        z = jsonUtf8Reader.nextInt() == 3;
                    }
                }
                circleShape = new CircleShape(strNextString5, splitPath, point, z, zNextBoolean2);
                shapeFill = circleShape;
                while (jsonUtf8Reader.hasNext()) {
                    jsonUtf8Reader.skipValue();
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            case "fl":
                JsonReader.Options options2 = ShapeFillParser.NAMES;
                boolean zNextBoolean3 = false;
                boolean zNextBoolean4 = false;
                int iNextInt2 = 1;
                String strNextString6 = null;
                AnimatableColorValue color = null;
                while (jsonUtf8Reader.hasNext()) {
                    int iSelectName3 = jsonUtf8Reader.selectName(ShapeFillParser.NAMES);
                    if (iSelectName3 == 0) {
                        strNextString6 = jsonUtf8Reader.nextString();
                    } else if (iSelectName3 == 1) {
                        color = AnimatableValueParser.parseColor(jsonUtf8Reader, lottieComposition);
                    } else if (iSelectName3 == 2) {
                        animatableIntegerValue = AnimatableValueParser.parseInteger(jsonUtf8Reader, lottieComposition);
                    } else if (iSelectName3 == 3) {
                        zNextBoolean3 = jsonUtf8Reader.nextBoolean();
                    } else if (iSelectName3 == 4) {
                        iNextInt2 = jsonUtf8Reader.nextInt();
                    } else if (iSelectName3 != 5) {
                        jsonUtf8Reader.skipName();
                        jsonUtf8Reader.skipValue();
                    } else {
                        zNextBoolean4 = jsonUtf8Reader.nextBoolean();
                    }
                }
                if (animatableIntegerValue == null) {
                    animatableIntegerValue = new AnimatableIntegerValue(Collections.singletonList(new Keyframe(100)));
                }
                shapeFill = new ShapeFill(strNextString6, zNextBoolean3, iNextInt2 == 1 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD, color, animatableIntegerValue, zNextBoolean4);
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            case "gf":
                JsonReader.Options options3 = GradientFillParser.NAMES;
                Path.FillType fillType = Path.FillType.WINDING;
                boolean zNextBoolean5 = false;
                String strNextString7 = null;
                GradientType gradientType = null;
                AnimatableGradientColorValue animatableGradientColorValue = null;
                AnimatablePointValue point2 = null;
                AnimatablePointValue point3 = null;
                while (jsonUtf8Reader.hasNext()) {
                    switch (jsonUtf8Reader.selectName(GradientFillParser.NAMES)) {
                        case 0:
                            strNextString7 = jsonUtf8Reader.nextString();
                            break;
                        case 1:
                            jsonUtf8Reader.beginObject();
                            int iNextInt3 = -1;
                            while (jsonUtf8Reader.hasNext()) {
                                int iSelectName4 = jsonUtf8Reader.selectName(GradientFillParser.GRADIENT_NAMES);
                                if (iSelectName4 == 0) {
                                    iNextInt3 = jsonUtf8Reader.nextInt();
                                } else if (iSelectName4 != 1) {
                                    jsonUtf8Reader.skipName();
                                    jsonUtf8Reader.skipValue();
                                } else {
                                    animatableGradientColorValue = new AnimatableGradientColorValue(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, 1.0f, new GradientColorParser(iNextInt3), false));
                                }
                            }
                            jsonUtf8Reader.endObject();
                            break;
                        case 2:
                            animatableIntegerValue2 = AnimatableValueParser.parseInteger(jsonUtf8Reader, lottieComposition);
                            break;
                        case 3:
                            gradientType = jsonUtf8Reader.nextInt() == 1 ? GradientType.LINEAR : GradientType.RADIAL;
                            break;
                        case 4:
                            point2 = AnimatableValueParser.parsePoint(jsonUtf8Reader, lottieComposition);
                            break;
                        case 5:
                            point3 = AnimatableValueParser.parsePoint(jsonUtf8Reader, lottieComposition);
                            break;
                        case 6:
                            fillType = jsonUtf8Reader.nextInt() == 1 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
                            break;
                        case 7:
                            zNextBoolean5 = jsonUtf8Reader.nextBoolean();
                            break;
                        default:
                            jsonUtf8Reader.skipName();
                            jsonUtf8Reader.skipValue();
                            break;
                    }
                }
                if (animatableIntegerValue2 == null) {
                    animatableIntegerValue2 = new AnimatableIntegerValue(Collections.singletonList(new Keyframe(100)));
                }
                gradientFill = new GradientFill(strNextString7, gradientType, fillType, animatableGradientColorValue, animatableIntegerValue2, point2, point3, null, null, zNextBoolean5);
                shapeFill = gradientFill;
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            case "gr":
                JsonReader.Options options4 = ShapeGroupParser.NAMES;
                ArrayList arrayList = new ArrayList();
                while (jsonUtf8Reader.hasNext()) {
                    int iSelectName5 = jsonUtf8Reader.selectName(ShapeGroupParser.NAMES);
                    if (iSelectName5 == 0) {
                        strNextString2 = jsonUtf8Reader.nextString();
                    } else if (iSelectName5 == 1) {
                        zNextBoolean = jsonUtf8Reader.nextBoolean();
                    } else if (iSelectName5 != 2) {
                        jsonUtf8Reader.skipValue();
                    } else {
                        jsonUtf8Reader.beginArray();
                        while (jsonUtf8Reader.hasNext()) {
                            ContentModel contentModel = parse(jsonUtf8Reader, lottieComposition);
                            if (contentModel != null) {
                                arrayList.add(contentModel);
                            }
                        }
                        jsonUtf8Reader.endArray();
                    }
                }
                shapeGroup = new ShapeGroup(strNextString2, arrayList, zNextBoolean);
                shapeFill = shapeGroup;
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            case "gs":
                JsonReader.Options options5 = GradientStrokeParser.NAMES;
                ArrayList arrayList2 = new ArrayList();
                boolean zNextBoolean6 = false;
                AnimatableIntegerValue animatableIntegerValue3 = null;
                String strNextString8 = null;
                GradientType gradientType2 = null;
                AnimatableGradientColorValue animatableGradientColorValue2 = null;
                AnimatablePointValue point4 = null;
                AnimatablePointValue point5 = null;
                AnimatableFloatValue animatableFloatValue2 = null;
                ShapeStroke.LineCapType lineCapType = null;
                ShapeStroke.LineJoinType lineJoinType = null;
                AnimatableFloatValue animatableFloatValue3 = null;
                float fNextDouble = 0.0f;
                while (jsonUtf8Reader.hasNext()) {
                    switch (jsonUtf8Reader.selectName(GradientStrokeParser.NAMES)) {
                        case 0:
                            strNextString8 = jsonUtf8Reader.nextString();
                            break;
                        case 1:
                            jsonUtf8Reader.beginObject();
                            int iNextInt4 = -1;
                            while (jsonUtf8Reader.hasNext()) {
                                int iSelectName6 = jsonUtf8Reader.selectName(GradientStrokeParser.GRADIENT_NAMES);
                                if (iSelectName6 == 0) {
                                    iNextInt4 = jsonUtf8Reader.nextInt();
                                } else if (iSelectName6 != 1) {
                                    jsonUtf8Reader.skipName();
                                    jsonUtf8Reader.skipValue();
                                } else {
                                    animatableGradientColorValue2 = new AnimatableGradientColorValue(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, 1.0f, new GradientColorParser(iNextInt4), false));
                                }
                            }
                            jsonUtf8Reader.endObject();
                            break;
                        case 2:
                            animatableIntegerValue3 = AnimatableValueParser.parseInteger(jsonUtf8Reader, lottieComposition);
                            break;
                        case 3:
                            gradientType2 = jsonUtf8Reader.nextInt() == 1 ? GradientType.LINEAR : GradientType.RADIAL;
                            break;
                        case 4:
                            point4 = AnimatableValueParser.parsePoint(jsonUtf8Reader, lottieComposition);
                            break;
                        case 5:
                            point5 = AnimatableValueParser.parsePoint(jsonUtf8Reader, lottieComposition);
                            break;
                        case 6:
                            animatableFloatValue2 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                            break;
                        case 7:
                            lineCapType = ShapeStroke.LineCapType.values()[jsonUtf8Reader.nextInt() - 1];
                            break;
                        case 8:
                            lineJoinType = ShapeStroke.LineJoinType.values()[jsonUtf8Reader.nextInt() - 1];
                            break;
                        case 9:
                            fNextDouble = (float) jsonUtf8Reader.nextDouble();
                            break;
                        case 10:
                            zNextBoolean6 = jsonUtf8Reader.nextBoolean();
                            break;
                        case 11:
                            jsonUtf8Reader.beginArray();
                            while (jsonUtf8Reader.hasNext()) {
                                jsonUtf8Reader.beginObject();
                                String strNextString9 = null;
                                AnimatableFloatValue animatableFloatValue4 = null;
                                while (jsonUtf8Reader.hasNext()) {
                                    int iSelectName7 = jsonUtf8Reader.selectName(GradientStrokeParser.DASH_PATTERN_NAMES);
                                    if (iSelectName7 == 0) {
                                        strNextString9 = jsonUtf8Reader.nextString();
                                    } else if (iSelectName7 != 1) {
                                        jsonUtf8Reader.skipName();
                                        jsonUtf8Reader.skipValue();
                                    } else {
                                        animatableFloatValue4 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                                    }
                                }
                                jsonUtf8Reader.endObject();
                                if (strNextString9.equals("o")) {
                                    animatableFloatValue3 = animatableFloatValue4;
                                } else if (strNextString9.equals("d") || strNextString9.equals("g")) {
                                    arrayList2.add(animatableFloatValue4);
                                }
                            }
                            jsonUtf8Reader.endArray();
                            if (arrayList2.size() != 1) {
                                break;
                            } else {
                                arrayList2.add((AnimatableFloatValue) arrayList2.get(0));
                                break;
                            }
                            break;
                        default:
                            jsonUtf8Reader.skipName();
                            jsonUtf8Reader.skipValue();
                            break;
                    }
                }
                if (animatableIntegerValue3 == null) {
                    animatableIntegerValue3 = new AnimatableIntegerValue(Collections.singletonList(new Keyframe(100)));
                }
                gradientFill = new GradientStroke(strNextString8, gradientType2, animatableGradientColorValue2, animatableIntegerValue3, point4, point5, animatableFloatValue2, lineCapType, lineJoinType, fNextDouble, arrayList2, animatableFloatValue3, zNextBoolean6);
                shapeFill = gradientFill;
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            case "mm":
                JsonReader.Options options6 = MergePathsParser.NAMES;
                MergePaths.MergePathsMode mergePathsMode = null;
                while (jsonUtf8Reader.hasNext()) {
                    int iSelectName8 = jsonUtf8Reader.selectName(MergePathsParser.NAMES);
                    if (iSelectName8 == 0) {
                        strNextString3 = jsonUtf8Reader.nextString();
                    } else if (iSelectName8 == 1) {
                        int iNextInt5 = jsonUtf8Reader.nextInt();
                        MergePaths.MergePathsMode mergePathsMode2 = MergePaths.MergePathsMode.MERGE;
                        if (iNextInt5 != 1) {
                            if (iNextInt5 == 2) {
                                mergePathsMode = MergePaths.MergePathsMode.ADD;
                            } else if (iNextInt5 == 3) {
                                mergePathsMode = MergePaths.MergePathsMode.SUBTRACT;
                            } else if (iNextInt5 == 4) {
                                mergePathsMode = MergePaths.MergePathsMode.INTERSECT;
                            } else if (iNextInt5 == 5) {
                                mergePathsMode = MergePaths.MergePathsMode.EXCLUDE_INTERSECTIONS;
                            }
                        }
                        mergePathsMode = mergePathsMode2;
                    } else if (iSelectName8 != 2) {
                        jsonUtf8Reader.skipName();
                        jsonUtf8Reader.skipValue();
                    } else {
                        zNextBoolean = jsonUtf8Reader.nextBoolean();
                    }
                }
                MergePaths mergePaths = new MergePaths(strNextString3, mergePathsMode, zNextBoolean);
                lottieComposition.addWarning("Animation contains merge paths. Merge paths are only supported on KitKat+ and must be manually enabled by calling enableMergePathsForKitKatAndAbove().");
                shapeFill = mergePaths;
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            case "rc":
                JsonReader.Options options7 = RectangleShapeParser.NAMES;
                boolean zNextBoolean7 = false;
                String strNextString10 = null;
                AnimatableValue splitPath2 = null;
                AnimatablePointValue point6 = null;
                AnimatableFloatValue animatableFloatValue5 = null;
                while (jsonUtf8Reader.hasNext()) {
                    int iSelectName9 = jsonUtf8Reader.selectName(RectangleShapeParser.NAMES);
                    if (iSelectName9 == 0) {
                        strNextString10 = jsonUtf8Reader.nextString();
                    } else if (iSelectName9 == 1) {
                        splitPath2 = AnimatablePathValueParser.parseSplitPath(jsonUtf8Reader, lottieComposition);
                    } else if (iSelectName9 == 2) {
                        point6 = AnimatableValueParser.parsePoint(jsonUtf8Reader, lottieComposition);
                    } else if (iSelectName9 == 3) {
                        animatableFloatValue5 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                    } else if (iSelectName9 != 4) {
                        jsonUtf8Reader.skipValue();
                    } else {
                        zNextBoolean7 = jsonUtf8Reader.nextBoolean();
                    }
                }
                circleShape = new RectangleShape(strNextString10, splitPath2, point6, animatableFloatValue5, zNextBoolean7);
                shapeFill = circleShape;
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            case "rd":
                JsonReader.Options options8 = RoundedCornersParser.NAMES;
                String strNextString11 = null;
                AnimatableFloatValue animatableFloatValue6 = null;
                while (jsonUtf8Reader.hasNext()) {
                    int iSelectName10 = jsonUtf8Reader.selectName(RoundedCornersParser.NAMES);
                    if (iSelectName10 == 0) {
                        strNextString11 = jsonUtf8Reader.nextString();
                    } else if (iSelectName10 == 1) {
                        animatableFloatValue6 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                    } else if (iSelectName10 != 2) {
                        jsonUtf8Reader.skipValue();
                    } else {
                        zNextBoolean = jsonUtf8Reader.nextBoolean();
                    }
                }
                if (!zNextBoolean) {
                    shapeFill = new RoundedCorners(strNextString11, animatableFloatValue6);
                }
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            case "rp":
                JsonReader.Options options9 = RepeaterParser.NAMES;
                boolean zNextBoolean8 = false;
                String strNextString12 = null;
                AnimatableFloatValue animatableFloatValue7 = null;
                AnimatableFloatValue animatableFloatValue8 = null;
                AnimatableTransform animatableTransform = null;
                while (jsonUtf8Reader.hasNext()) {
                    int iSelectName11 = jsonUtf8Reader.selectName(RepeaterParser.NAMES);
                    if (iSelectName11 == 0) {
                        strNextString12 = jsonUtf8Reader.nextString();
                    } else if (iSelectName11 == 1) {
                        animatableFloatValue7 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    } else if (iSelectName11 == 2) {
                        animatableFloatValue8 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    } else if (iSelectName11 == 3) {
                        animatableTransform = AnimatableTransformParser.parse(jsonUtf8Reader, lottieComposition);
                    } else if (iSelectName11 != 4) {
                        jsonUtf8Reader.skipValue();
                    } else {
                        zNextBoolean8 = jsonUtf8Reader.nextBoolean();
                    }
                }
                circleShape = new Repeater(strNextString12, animatableFloatValue7, animatableFloatValue8, animatableTransform, zNextBoolean8);
                shapeFill = circleShape;
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            case "sh":
                JsonReader.Options options10 = ShapePathParser.NAMES;
                int iNextInt6 = 0;
                boolean zNextBoolean9 = false;
                AnimatableShapeValue animatableShapeValue = null;
                while (jsonUtf8Reader.hasNext()) {
                    int iSelectName12 = jsonUtf8Reader.selectName(ShapePathParser.NAMES);
                    if (iSelectName12 == 0) {
                        strNextString4 = jsonUtf8Reader.nextString();
                    } else if (iSelectName12 == 1) {
                        iNextInt6 = jsonUtf8Reader.nextInt();
                    } else if (iSelectName12 == 2) {
                        animatableShapeValue = new AnimatableShapeValue(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, Utils.dpScale(), ShapeDataParser.INSTANCE, false));
                    } else if (iSelectName12 != 3) {
                        jsonUtf8Reader.skipValue();
                    } else {
                        zNextBoolean9 = jsonUtf8Reader.nextBoolean();
                    }
                }
                shapeGroup = new ShapePath(strNextString4, iNextInt6, animatableShapeValue, zNextBoolean9);
                shapeFill = shapeGroup;
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            case "sr":
                JsonReader.Options options11 = PolystarShapeParser.NAMES;
                boolean z2 = iNextInt == 3;
                boolean zNextBoolean10 = false;
                String strNextString13 = null;
                PolystarShape.Type typeForValue = null;
                AnimatableFloatValue animatableFloatValue9 = null;
                AnimatableValue splitPath3 = null;
                AnimatableFloatValue animatableFloatValue10 = null;
                AnimatableFloatValue animatableFloatValue11 = null;
                AnimatableFloatValue animatableFloatValue12 = null;
                AnimatableFloatValue animatableFloatValue13 = null;
                AnimatableFloatValue animatableFloatValue14 = null;
                while (jsonUtf8Reader.hasNext()) {
                    switch (jsonUtf8Reader.selectName(PolystarShapeParser.NAMES)) {
                        case 0:
                            strNextString13 = jsonUtf8Reader.nextString();
                            break;
                        case 1:
                            typeForValue = PolystarShape.Type.forValue(jsonUtf8Reader.nextInt());
                            break;
                        case 2:
                            animatableFloatValue9 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                            break;
                        case 3:
                            splitPath3 = AnimatablePathValueParser.parseSplitPath(jsonUtf8Reader, lottieComposition);
                            break;
                        case 4:
                            animatableFloatValue10 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                            break;
                        case 5:
                            animatableFloatValue12 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                            break;
                        case 6:
                            animatableFloatValue14 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                            break;
                        case 7:
                            animatableFloatValue11 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                            break;
                        case 8:
                            animatableFloatValue13 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                            break;
                        case 9:
                            zNextBoolean10 = jsonUtf8Reader.nextBoolean();
                            break;
                        case 10:
                            if (jsonUtf8Reader.nextInt() != 3) {
                                z2 = false;
                                break;
                            } else {
                                z2 = true;
                                break;
                            }
                        default:
                            jsonUtf8Reader.skipName();
                            jsonUtf8Reader.skipValue();
                            break;
                    }
                }
                circleShape = new PolystarShape(strNextString13, typeForValue, animatableFloatValue9, splitPath3, animatableFloatValue10, animatableFloatValue11, animatableFloatValue12, animatableFloatValue13, animatableFloatValue14, zNextBoolean10, z2);
                shapeFill = circleShape;
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            case "st":
                JsonReader.Options options12 = ShapeStrokeParser.NAMES;
                ArrayList arrayList3 = new ArrayList();
                boolean zNextBoolean11 = false;
                AnimatableIntegerValue animatableIntegerValue4 = null;
                ShapeStroke.LineCapType lineCapType2 = null;
                ShapeStroke.LineJoinType lineJoinType2 = null;
                String strNextString14 = null;
                AnimatableFloatValue animatableFloatValue15 = null;
                AnimatableColorValue color2 = null;
                AnimatableFloatValue animatableFloatValue16 = null;
                float fNextDouble2 = 0.0f;
                while (jsonUtf8Reader.hasNext()) {
                    switch (jsonUtf8Reader.selectName(ShapeStrokeParser.NAMES)) {
                        case 0:
                            strNextString14 = jsonUtf8Reader.nextString();
                            break;
                        case 1:
                            color2 = AnimatableValueParser.parseColor(jsonUtf8Reader, lottieComposition);
                            break;
                        case 2:
                            animatableFloatValue16 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                            break;
                        case 3:
                            animatableIntegerValue4 = AnimatableValueParser.parseInteger(jsonUtf8Reader, lottieComposition);
                            break;
                        case 4:
                            lineCapType2 = ShapeStroke.LineCapType.values()[jsonUtf8Reader.nextInt() - 1];
                            break;
                        case 5:
                            lineJoinType2 = ShapeStroke.LineJoinType.values()[jsonUtf8Reader.nextInt() - 1];
                            break;
                        case 6:
                            fNextDouble2 = (float) jsonUtf8Reader.nextDouble();
                            break;
                        case 7:
                            zNextBoolean11 = jsonUtf8Reader.nextBoolean();
                            break;
                        case 8:
                            jsonUtf8Reader.beginArray();
                            while (jsonUtf8Reader.hasNext()) {
                                jsonUtf8Reader.beginObject();
                                String strNextString15 = null;
                                animatableFloatValue = null;
                                while (jsonUtf8Reader.hasNext()) {
                                    int iSelectName13 = jsonUtf8Reader.selectName(ShapeStrokeParser.DASH_PATTERN_NAMES);
                                    if (iSelectName13 == 0) {
                                        strNextString15 = jsonUtf8Reader.nextString();
                                    } else if (iSelectName13 != 1) {
                                        jsonUtf8Reader.skipName();
                                        jsonUtf8Reader.skipValue();
                                    } else {
                                        animatableFloatValue = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                                    }
                                }
                                jsonUtf8Reader.endObject();
                                strNextString15.getClass();
                                switch (strNextString15) {
                                    case "d":
                                    case "g":
                                        arrayList3.add(animatableFloatValue);
                                        break;
                                    case "o":
                                        animatableFloatValue15 = animatableFloatValue;
                                        break;
                                }
                            }
                            jsonUtf8Reader.endArray();
                            if (arrayList3.size() != 1) {
                                break;
                            } else {
                                arrayList3.add((AnimatableFloatValue) arrayList3.get(0));
                                break;
                            }
                            break;
                        default:
                            jsonUtf8Reader.skipValue();
                            break;
                    }
                }
                if (animatableIntegerValue4 == null) {
                    animatableIntegerValue4 = new AnimatableIntegerValue(Collections.singletonList(new Keyframe(100)));
                }
                AnimatableIntegerValue animatableIntegerValue5 = animatableIntegerValue4;
                if (lineCapType2 == null) {
                    lineCapType2 = ShapeStroke.LineCapType.BUTT;
                }
                ShapeStroke.LineCapType lineCapType3 = lineCapType2;
                if (lineJoinType2 == null) {
                    lineJoinType2 = ShapeStroke.LineJoinType.MITER;
                }
                gradientFill = new ShapeStroke(strNextString14, animatableFloatValue15, arrayList3, color2, animatableIntegerValue5, animatableFloatValue16, lineCapType3, lineJoinType2, fNextDouble2, zNextBoolean11);
                shapeFill = gradientFill;
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            case "tm":
                JsonReader.Options options13 = ShapeTrimPathParser.NAMES;
                boolean zNextBoolean12 = false;
                String strNextString16 = null;
                ShapeTrimPath.Type type2 = null;
                AnimatableFloatValue animatableFloatValue17 = null;
                AnimatableFloatValue animatableFloatValue18 = null;
                AnimatableFloatValue animatableFloatValue19 = null;
                while (jsonUtf8Reader.hasNext()) {
                    int iSelectName14 = jsonUtf8Reader.selectName(ShapeTrimPathParser.NAMES);
                    if (iSelectName14 == 0) {
                        animatableFloatValue17 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    } else if (iSelectName14 == 1) {
                        animatableFloatValue18 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    } else if (iSelectName14 == 2) {
                        animatableFloatValue19 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    } else if (iSelectName14 == 3) {
                        strNextString16 = jsonUtf8Reader.nextString();
                    } else if (iSelectName14 == 4) {
                        int iNextInt7 = jsonUtf8Reader.nextInt();
                        if (iNextInt7 == 1) {
                            type = ShapeTrimPath.Type.SIMULTANEOUSLY;
                        } else {
                            if (iNextInt7 != 2) {
                                ShapeTrimPath.Type type3 = ShapeTrimPath.Type.SIMULTANEOUSLY;
                                throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iNextInt7, "Unknown trim path type "));
                            }
                            type = ShapeTrimPath.Type.INDIVIDUALLY;
                        }
                        type2 = type;
                    } else if (iSelectName14 != 5) {
                        jsonUtf8Reader.skipValue();
                    } else {
                        zNextBoolean12 = jsonUtf8Reader.nextBoolean();
                    }
                }
                circleShape = new ShapeTrimPath(strNextString16, type2, animatableFloatValue17, animatableFloatValue18, animatableFloatValue19, zNextBoolean12);
                shapeFill = circleShape;
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            case "tr":
                shapeFill = AnimatableTransformParser.parse(jsonUtf8Reader, lottieComposition);
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
            default:
                Logger.warning("Unknown shape type ".concat(strNextString));
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return shapeFill;
        }
    }
}
