package com.airbnb.lottie.parser;

import android.graphics.Path;
import android.support.v4.media.AbstractC0000x2c234b15;
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
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonUtf8Reader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import java.util.ArrayList;
import java.util.Collections;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ContentModelParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.m50of("ty", "d");

    private ContentModelParser() {
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x07e2 A[LOOP:1: B:55:0x07dc->B:57:0x07e2, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ContentModel parse(JsonUtf8Reader jsonUtf8Reader, LottieComposition lottieComposition) {
        String str;
        char c;
        ContentModel circleShape;
        AnimatableFloatValue animatableFloatValue;
        ShapeTrimPath.Type type;
        jsonUtf8Reader.beginObject();
        int i = 2;
        while (true) {
            if (!jsonUtf8Reader.hasNext()) {
                str = null;
                break;
            }
            int selectName = jsonUtf8Reader.selectName(NAMES);
            if (selectName == 0) {
                str = jsonUtf8Reader.nextString();
                break;
            }
            if (selectName != 1) {
                jsonUtf8Reader.skipName();
                jsonUtf8Reader.skipValue();
            } else {
                i = jsonUtf8Reader.nextInt();
            }
        }
        if (str == null) {
            return null;
        }
        int hashCode = str.hashCode();
        boolean z = false;
        if (hashCode == 3239) {
            if (str.equals("el")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode == 3270) {
            if (str.equals("fl")) {
                c = 1;
            }
            c = 65535;
        } else if (hashCode == 3295) {
            if (str.equals("gf")) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode == 3488) {
            if (str.equals("mm")) {
                c = 5;
            }
            c = 65535;
        } else if (hashCode == 3646) {
            if (str.equals("rp")) {
                c = '\b';
            }
            c = 65535;
        } else if (hashCode == 3669) {
            if (str.equals("sh")) {
                c = '\t';
            }
            c = 65535;
        } else if (hashCode == 3679) {
            if (str.equals("sr")) {
                c = '\n';
            }
            c = 65535;
        } else if (hashCode == 3681) {
            if (str.equals("st")) {
                c = 11;
            }
            c = 65535;
        } else if (hashCode == 3705) {
            if (str.equals("tm")) {
                c = '\f';
            }
            c = 65535;
        } else if (hashCode == 3710) {
            if (str.equals("tr")) {
                c = '\r';
            }
            c = 65535;
        } else if (hashCode == 3307) {
            if (str.equals("gr")) {
                c = 3;
            }
            c = 65535;
        } else if (hashCode == 3308) {
            if (str.equals("gs")) {
                c = 4;
            }
            c = 65535;
        } else if (hashCode != 3633) {
            if (hashCode == 3634 && str.equals("rd")) {
                c = 7;
            }
            c = 65535;
        } else {
            if (str.equals("rc")) {
                c = 6;
            }
            c = 65535;
        }
        switch (c) {
            case 0:
                JsonReader.Options options = CircleShapeParser.NAMES;
                boolean z2 = i == 3;
                String str2 = null;
                AnimatableValue animatableValue = null;
                AnimatablePointValue animatablePointValue = null;
                boolean z3 = false;
                while (jsonUtf8Reader.hasNext()) {
                    int selectName2 = jsonUtf8Reader.selectName(CircleShapeParser.NAMES);
                    if (selectName2 == 0) {
                        str2 = jsonUtf8Reader.nextString();
                    } else if (selectName2 == 1) {
                        animatableValue = AnimatablePathValueParser.parseSplitPath(jsonUtf8Reader, lottieComposition);
                    } else if (selectName2 == 2) {
                        animatablePointValue = AnimatableValueParser.parsePoint(jsonUtf8Reader, lottieComposition);
                    } else if (selectName2 == 3) {
                        z3 = jsonUtf8Reader.nextBoolean();
                    } else if (selectName2 != 4) {
                        jsonUtf8Reader.skipName();
                        jsonUtf8Reader.skipValue();
                    } else {
                        z2 = jsonUtf8Reader.nextInt() == 3;
                    }
                }
                circleShape = new CircleShape(str2, animatableValue, animatablePointValue, z2, z3);
                while (jsonUtf8Reader.hasNext()) {
                    jsonUtf8Reader.skipValue();
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            case 1:
                JsonReader.Options options2 = ShapeFillParser.NAMES;
                AnimatableIntegerValue animatableIntegerValue = null;
                String str3 = null;
                int i2 = 1;
                AnimatableColorValue animatableColorValue = null;
                boolean z4 = false;
                boolean z5 = false;
                while (jsonUtf8Reader.hasNext()) {
                    int selectName3 = jsonUtf8Reader.selectName(ShapeFillParser.NAMES);
                    if (selectName3 == 0) {
                        str3 = jsonUtf8Reader.nextString();
                    } else if (selectName3 == 1) {
                        animatableColorValue = AnimatableValueParser.parseColor(jsonUtf8Reader, lottieComposition);
                    } else if (selectName3 == 2) {
                        animatableIntegerValue = AnimatableValueParser.parseInteger(jsonUtf8Reader, lottieComposition);
                    } else if (selectName3 == 3) {
                        z4 = jsonUtf8Reader.nextBoolean();
                    } else if (selectName3 == 4) {
                        i2 = jsonUtf8Reader.nextInt();
                    } else if (selectName3 != 5) {
                        jsonUtf8Reader.skipName();
                        jsonUtf8Reader.skipValue();
                    } else {
                        z5 = jsonUtf8Reader.nextBoolean();
                    }
                }
                if (animatableIntegerValue == null) {
                    animatableIntegerValue = new AnimatableIntegerValue(Collections.singletonList(new Keyframe(100)));
                }
                circleShape = new ShapeFill(str3, z4, i2 == 1 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD, animatableColorValue, animatableIntegerValue, z5);
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            case 2:
                JsonReader.Options options3 = GradientFillParser.NAMES;
                AnimatableIntegerValue animatableIntegerValue2 = null;
                Path.FillType fillType = Path.FillType.WINDING;
                String str4 = null;
                GradientType gradientType = null;
                boolean z6 = false;
                AnimatableGradientColorValue animatableGradientColorValue = null;
                AnimatablePointValue animatablePointValue2 = null;
                AnimatablePointValue animatablePointValue3 = null;
                while (jsonUtf8Reader.hasNext()) {
                    switch (jsonUtf8Reader.selectName(GradientFillParser.NAMES)) {
                        case 0:
                            str4 = jsonUtf8Reader.nextString();
                            break;
                        case 1:
                            jsonUtf8Reader.beginObject();
                            int i3 = -1;
                            while (jsonUtf8Reader.hasNext()) {
                                int selectName4 = jsonUtf8Reader.selectName(GradientFillParser.GRADIENT_NAMES);
                                if (selectName4 == 0) {
                                    i3 = jsonUtf8Reader.nextInt();
                                } else if (selectName4 != 1) {
                                    jsonUtf8Reader.skipName();
                                    jsonUtf8Reader.skipValue();
                                } else {
                                    animatableGradientColorValue = new AnimatableGradientColorValue(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, 1.0f, new GradientColorParser(i3), false));
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
                            animatablePointValue2 = AnimatableValueParser.parsePoint(jsonUtf8Reader, lottieComposition);
                            break;
                        case 5:
                            animatablePointValue3 = AnimatableValueParser.parsePoint(jsonUtf8Reader, lottieComposition);
                            break;
                        case 6:
                            fillType = jsonUtf8Reader.nextInt() == 1 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
                            break;
                        case 7:
                            z6 = jsonUtf8Reader.nextBoolean();
                            break;
                        default:
                            jsonUtf8Reader.skipName();
                            jsonUtf8Reader.skipValue();
                            break;
                    }
                }
                circleShape = new GradientFill(str4, gradientType, fillType, animatableGradientColorValue, animatableIntegerValue2 == null ? new AnimatableIntegerValue(Collections.singletonList(new Keyframe(100))) : animatableIntegerValue2, animatablePointValue2, animatablePointValue3, null, null, z6);
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            case 3:
                JsonReader.Options options4 = ShapeGroupParser.NAMES;
                ArrayList arrayList = new ArrayList();
                String str5 = null;
                while (jsonUtf8Reader.hasNext()) {
                    int selectName5 = jsonUtf8Reader.selectName(ShapeGroupParser.NAMES);
                    if (selectName5 == 0) {
                        str5 = jsonUtf8Reader.nextString();
                    } else if (selectName5 == 1) {
                        z = jsonUtf8Reader.nextBoolean();
                    } else if (selectName5 != 2) {
                        jsonUtf8Reader.skipValue();
                    } else {
                        jsonUtf8Reader.beginArray();
                        while (jsonUtf8Reader.hasNext()) {
                            ContentModel parse = parse(jsonUtf8Reader, lottieComposition);
                            if (parse != null) {
                                arrayList.add(parse);
                            }
                        }
                        jsonUtf8Reader.endArray();
                    }
                }
                circleShape = new ShapeGroup(str5, arrayList, z);
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            case 4:
                JsonReader.Options options5 = GradientStrokeParser.NAMES;
                ArrayList arrayList2 = new ArrayList();
                AnimatableIntegerValue animatableIntegerValue3 = null;
                boolean z7 = false;
                AnimatablePointValue animatablePointValue4 = null;
                AnimatablePointValue animatablePointValue5 = null;
                float f = 0.0f;
                AnimatableFloatValue animatableFloatValue2 = null;
                ShapeStroke.LineCapType lineCapType = null;
                ShapeStroke.LineJoinType lineJoinType = null;
                AnimatableFloatValue animatableFloatValue3 = null;
                String str6 = null;
                GradientType gradientType2 = null;
                AnimatableGradientColorValue animatableGradientColorValue2 = null;
                while (jsonUtf8Reader.hasNext()) {
                    switch (jsonUtf8Reader.selectName(GradientStrokeParser.NAMES)) {
                        case 0:
                            str6 = jsonUtf8Reader.nextString();
                            break;
                        case 1:
                            jsonUtf8Reader.beginObject();
                            int i4 = -1;
                            while (jsonUtf8Reader.hasNext()) {
                                int selectName6 = jsonUtf8Reader.selectName(GradientStrokeParser.GRADIENT_NAMES);
                                if (selectName6 == 0) {
                                    i4 = jsonUtf8Reader.nextInt();
                                } else if (selectName6 != 1) {
                                    jsonUtf8Reader.skipName();
                                    jsonUtf8Reader.skipValue();
                                } else {
                                    animatableGradientColorValue2 = new AnimatableGradientColorValue(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, 1.0f, new GradientColorParser(i4), false));
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
                            animatablePointValue4 = AnimatableValueParser.parsePoint(jsonUtf8Reader, lottieComposition);
                            break;
                        case 5:
                            animatablePointValue5 = AnimatableValueParser.parsePoint(jsonUtf8Reader, lottieComposition);
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
                            f = (float) jsonUtf8Reader.nextDouble();
                            break;
                        case 10:
                            z7 = jsonUtf8Reader.nextBoolean();
                            break;
                        case 11:
                            jsonUtf8Reader.beginArray();
                            while (jsonUtf8Reader.hasNext()) {
                                jsonUtf8Reader.beginObject();
                                AnimatableFloatValue animatableFloatValue4 = null;
                                String str7 = null;
                                while (jsonUtf8Reader.hasNext()) {
                                    int selectName7 = jsonUtf8Reader.selectName(GradientStrokeParser.DASH_PATTERN_NAMES);
                                    if (selectName7 == 0) {
                                        str7 = jsonUtf8Reader.nextString();
                                    } else if (selectName7 != 1) {
                                        jsonUtf8Reader.skipName();
                                        jsonUtf8Reader.skipValue();
                                    } else {
                                        animatableFloatValue4 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                                    }
                                }
                                jsonUtf8Reader.endObject();
                                if (str7.equals("o")) {
                                    animatableFloatValue3 = animatableFloatValue4;
                                } else if (str7.equals("d") || str7.equals("g")) {
                                    lottieComposition.hasDashPattern = true;
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
                        default:
                            jsonUtf8Reader.skipName();
                            jsonUtf8Reader.skipValue();
                            break;
                    }
                }
                circleShape = new GradientStroke(str6, gradientType2, animatableGradientColorValue2, animatableIntegerValue3 == null ? new AnimatableIntegerValue(Collections.singletonList(new Keyframe(100))) : animatableIntegerValue3, animatablePointValue4, animatablePointValue5, animatableFloatValue2, lineCapType, lineJoinType, f, arrayList2, animatableFloatValue3, z7);
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            case 5:
                JsonReader.Options options6 = MergePathsParser.NAMES;
                MergePaths.MergePathsMode mergePathsMode = null;
                String str8 = null;
                while (jsonUtf8Reader.hasNext()) {
                    int selectName8 = jsonUtf8Reader.selectName(MergePathsParser.NAMES);
                    if (selectName8 == 0) {
                        str8 = jsonUtf8Reader.nextString();
                    } else if (selectName8 == 1) {
                        int nextInt = jsonUtf8Reader.nextInt();
                        MergePaths.MergePathsMode mergePathsMode2 = MergePaths.MergePathsMode.MERGE;
                        if (nextInt != 1) {
                            if (nextInt == 2) {
                                mergePathsMode = MergePaths.MergePathsMode.ADD;
                            } else if (nextInt == 3) {
                                mergePathsMode = MergePaths.MergePathsMode.SUBTRACT;
                            } else if (nextInt == 4) {
                                mergePathsMode = MergePaths.MergePathsMode.INTERSECT;
                            } else if (nextInt == 5) {
                                mergePathsMode = MergePaths.MergePathsMode.EXCLUDE_INTERSECTIONS;
                            }
                        }
                        mergePathsMode = mergePathsMode2;
                    } else if (selectName8 != 2) {
                        jsonUtf8Reader.skipName();
                        jsonUtf8Reader.skipValue();
                    } else {
                        z = jsonUtf8Reader.nextBoolean();
                    }
                }
                MergePaths mergePaths = new MergePaths(str8, mergePathsMode, z);
                lottieComposition.addWarning("Animation contains merge paths. Merge paths are only supported on KitKat+ and must be manually enabled by calling enableMergePathsForKitKatAndAbove().");
                circleShape = mergePaths;
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            case 6:
                JsonReader.Options options7 = RectangleShapeParser.NAMES;
                String str9 = null;
                AnimatableValue animatableValue2 = null;
                AnimatablePointValue animatablePointValue6 = null;
                AnimatableFloatValue animatableFloatValue5 = null;
                boolean z8 = false;
                while (jsonUtf8Reader.hasNext()) {
                    int selectName9 = jsonUtf8Reader.selectName(RectangleShapeParser.NAMES);
                    if (selectName9 == 0) {
                        str9 = jsonUtf8Reader.nextString();
                    } else if (selectName9 == 1) {
                        animatableValue2 = AnimatablePathValueParser.parseSplitPath(jsonUtf8Reader, lottieComposition);
                    } else if (selectName9 == 2) {
                        animatablePointValue6 = AnimatableValueParser.parsePoint(jsonUtf8Reader, lottieComposition);
                    } else if (selectName9 == 3) {
                        animatableFloatValue5 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                    } else if (selectName9 != 4) {
                        jsonUtf8Reader.skipValue();
                    } else {
                        z8 = jsonUtf8Reader.nextBoolean();
                    }
                }
                circleShape = new RectangleShape(str9, animatableValue2, animatablePointValue6, animatableFloatValue5, z8);
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            case 7:
                JsonReader.Options options8 = RoundedCornersParser.NAMES;
                String str10 = null;
                AnimatableFloatValue animatableFloatValue6 = null;
                while (jsonUtf8Reader.hasNext()) {
                    int selectName10 = jsonUtf8Reader.selectName(RoundedCornersParser.NAMES);
                    if (selectName10 == 0) {
                        str10 = jsonUtf8Reader.nextString();
                    } else if (selectName10 == 1) {
                        animatableFloatValue6 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                    } else if (selectName10 != 2) {
                        jsonUtf8Reader.skipValue();
                    } else {
                        z = jsonUtf8Reader.nextBoolean();
                    }
                }
                if (!z) {
                    circleShape = new RoundedCorners(str10, animatableFloatValue6);
                    while (jsonUtf8Reader.hasNext()) {
                    }
                    jsonUtf8Reader.endObject();
                    return circleShape;
                }
                circleShape = null;
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            case '\b':
                JsonReader.Options options9 = RepeaterParser.NAMES;
                String str11 = null;
                AnimatableFloatValue animatableFloatValue7 = null;
                AnimatableFloatValue animatableFloatValue8 = null;
                AnimatableTransform animatableTransform = null;
                boolean z9 = false;
                while (jsonUtf8Reader.hasNext()) {
                    int selectName11 = jsonUtf8Reader.selectName(RepeaterParser.NAMES);
                    if (selectName11 == 0) {
                        str11 = jsonUtf8Reader.nextString();
                    } else if (selectName11 == 1) {
                        animatableFloatValue7 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    } else if (selectName11 == 2) {
                        animatableFloatValue8 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    } else if (selectName11 == 3) {
                        animatableTransform = AnimatableTransformParser.parse(jsonUtf8Reader, lottieComposition);
                    } else if (selectName11 != 4) {
                        jsonUtf8Reader.skipValue();
                    } else {
                        z9 = jsonUtf8Reader.nextBoolean();
                    }
                }
                circleShape = new Repeater(str11, animatableFloatValue7, animatableFloatValue8, animatableTransform, z9);
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            case '\t':
                JsonReader.Options options10 = ShapePathParser.NAMES;
                AnimatableShapeValue animatableShapeValue = null;
                String str12 = null;
                int i5 = 0;
                boolean z10 = false;
                while (jsonUtf8Reader.hasNext()) {
                    int selectName12 = jsonUtf8Reader.selectName(ShapePathParser.NAMES);
                    if (selectName12 == 0) {
                        str12 = jsonUtf8Reader.nextString();
                    } else if (selectName12 == 1) {
                        i5 = jsonUtf8Reader.nextInt();
                    } else if (selectName12 == 2) {
                        animatableShapeValue = new AnimatableShapeValue(KeyframesParser.parse(jsonUtf8Reader, lottieComposition, Utils.dpScale(), ShapeDataParser.INSTANCE, false));
                    } else if (selectName12 != 3) {
                        jsonUtf8Reader.skipValue();
                    } else {
                        z10 = jsonUtf8Reader.nextBoolean();
                    }
                }
                circleShape = new ShapePath(str12, i5, animatableShapeValue, z10);
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            case '\n':
                JsonReader.Options options11 = PolystarShapeParser.NAMES;
                boolean z11 = i == 3;
                String str13 = null;
                PolystarShape.Type type2 = null;
                AnimatableFloatValue animatableFloatValue9 = null;
                AnimatableValue animatableValue3 = null;
                AnimatableFloatValue animatableFloatValue10 = null;
                boolean z12 = false;
                AnimatableFloatValue animatableFloatValue11 = null;
                AnimatableFloatValue animatableFloatValue12 = null;
                AnimatableFloatValue animatableFloatValue13 = null;
                AnimatableFloatValue animatableFloatValue14 = null;
                while (jsonUtf8Reader.hasNext()) {
                    switch (jsonUtf8Reader.selectName(PolystarShapeParser.NAMES)) {
                        case 0:
                            str13 = jsonUtf8Reader.nextString();
                            break;
                        case 1:
                            type2 = PolystarShape.Type.forValue(jsonUtf8Reader.nextInt());
                            break;
                        case 2:
                            animatableFloatValue9 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                            break;
                        case 3:
                            animatableValue3 = AnimatablePathValueParser.parseSplitPath(jsonUtf8Reader, lottieComposition);
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
                            z12 = jsonUtf8Reader.nextBoolean();
                            break;
                        case 10:
                            if (jsonUtf8Reader.nextInt() != 3) {
                                z11 = false;
                                break;
                            } else {
                                z11 = true;
                                break;
                            }
                        default:
                            jsonUtf8Reader.skipName();
                            jsonUtf8Reader.skipValue();
                            break;
                    }
                }
                circleShape = new PolystarShape(str13, type2, animatableFloatValue9, animatableValue3, animatableFloatValue10, animatableFloatValue11, animatableFloatValue12, animatableFloatValue13, animatableFloatValue14, z12, z11);
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            case 11:
                JsonReader.Options options12 = ShapeStrokeParser.NAMES;
                ArrayList arrayList3 = new ArrayList();
                AnimatableIntegerValue animatableIntegerValue4 = null;
                ShapeStroke.LineCapType lineCapType2 = null;
                ShapeStroke.LineJoinType lineJoinType2 = null;
                String str14 = null;
                AnimatableFloatValue animatableFloatValue15 = null;
                boolean z13 = false;
                AnimatableColorValue animatableColorValue2 = null;
                AnimatableFloatValue animatableFloatValue16 = null;
                float f2 = 0.0f;
                while (jsonUtf8Reader.hasNext()) {
                    switch (jsonUtf8Reader.selectName(ShapeStrokeParser.NAMES)) {
                        case 0:
                            str14 = jsonUtf8Reader.nextString();
                            break;
                        case 1:
                            animatableColorValue2 = AnimatableValueParser.parseColor(jsonUtf8Reader, lottieComposition);
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
                            f2 = (float) jsonUtf8Reader.nextDouble();
                            break;
                        case 7:
                            z13 = jsonUtf8Reader.nextBoolean();
                            break;
                        case 8:
                            jsonUtf8Reader.beginArray();
                            while (jsonUtf8Reader.hasNext()) {
                                jsonUtf8Reader.beginObject();
                                String str15 = null;
                                animatableFloatValue = null;
                                while (jsonUtf8Reader.hasNext()) {
                                    int selectName13 = jsonUtf8Reader.selectName(ShapeStrokeParser.DASH_PATTERN_NAMES);
                                    if (selectName13 == 0) {
                                        str15 = jsonUtf8Reader.nextString();
                                    } else if (selectName13 != 1) {
                                        jsonUtf8Reader.skipName();
                                        jsonUtf8Reader.skipValue();
                                    } else {
                                        animatableFloatValue = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, true);
                                    }
                                }
                                jsonUtf8Reader.endObject();
                                str15.getClass();
                                str15.hashCode();
                                switch (str15) {
                                    case "d":
                                    case "g":
                                        lottieComposition.hasDashPattern = true;
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
                circleShape = new ShapeStroke(str14, animatableFloatValue15, arrayList3, animatableColorValue2, animatableIntegerValue4 == null ? new AnimatableIntegerValue(Collections.singletonList(new Keyframe(100))) : animatableIntegerValue4, animatableFloatValue16, lineCapType2 == null ? ShapeStroke.LineCapType.BUTT : lineCapType2, lineJoinType2 == null ? ShapeStroke.LineJoinType.MITER : lineJoinType2, f2, z13);
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            case '\f':
                JsonReader.Options options13 = ShapeTrimPathParser.NAMES;
                String str16 = null;
                ShapeTrimPath.Type type3 = null;
                AnimatableFloatValue animatableFloatValue17 = null;
                AnimatableFloatValue animatableFloatValue18 = null;
                AnimatableFloatValue animatableFloatValue19 = null;
                boolean z14 = false;
                while (jsonUtf8Reader.hasNext()) {
                    int selectName14 = jsonUtf8Reader.selectName(ShapeTrimPathParser.NAMES);
                    if (selectName14 == 0) {
                        animatableFloatValue17 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    } else if (selectName14 == 1) {
                        animatableFloatValue18 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    } else if (selectName14 == 2) {
                        animatableFloatValue19 = AnimatableValueParser.parseFloat(jsonUtf8Reader, lottieComposition, false);
                    } else if (selectName14 == 3) {
                        str16 = jsonUtf8Reader.nextString();
                    } else if (selectName14 == 4) {
                        int nextInt2 = jsonUtf8Reader.nextInt();
                        if (nextInt2 == 1) {
                            type = ShapeTrimPath.Type.SIMULTANEOUSLY;
                        } else {
                            if (nextInt2 != 2) {
                                ShapeTrimPath.Type type4 = ShapeTrimPath.Type.SIMULTANEOUSLY;
                                throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Unknown trim path type ", nextInt2));
                            }
                            type = ShapeTrimPath.Type.INDIVIDUALLY;
                        }
                        type3 = type;
                    } else if (selectName14 != 5) {
                        jsonUtf8Reader.skipValue();
                    } else {
                        z14 = jsonUtf8Reader.nextBoolean();
                    }
                }
                circleShape = new ShapeTrimPath(str16, type3, animatableFloatValue17, animatableFloatValue18, animatableFloatValue19, z14);
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            case '\r':
                circleShape = AnimatableTransformParser.parse(jsonUtf8Reader, lottieComposition);
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
            default:
                Logger.warning("Unknown shape type ".concat(str));
                circleShape = null;
                while (jsonUtf8Reader.hasNext()) {
                }
                jsonUtf8Reader.endObject();
                return circleShape;
        }
    }
}
