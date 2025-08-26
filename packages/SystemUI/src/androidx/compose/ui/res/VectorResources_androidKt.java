package androidx.compose.ui.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.BrushKt$ShaderBrush$1;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathParser;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.graphics.vector.compat.AndroidVectorParser;
import androidx.compose.ui.graphics.vector.compat.AndroidVectorResources;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.ImageVectorCache;
import androidx.compose.ui.unit.Dp;
import androidx.core.content.res.ComplexColorCompat;
import androidx.core.content.res.TypedArrayUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public abstract class VectorResources_androidKt {
    /* JADX WARN: Removed duplicated region for block: B:101:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0344  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x035d  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0368  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x036d  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0387  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x038c  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final ImageVectorCache.ImageVectorEntry loadVectorResourceInner(Resources.Theme theme, Resources resources, XmlResourceParser xmlResourceParser, int i) throws XmlPullParserException {
        long jColor;
        int i2;
        List list;
        List list2;
        int i3;
        int namedInt;
        int i4;
        int namedInt2;
        int i5;
        int i6;
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParser);
        int i7 = 2;
        AndroidVectorParser androidVectorParser = new AndroidVectorParser(xmlResourceParser, 0, 2, null);
        AndroidVectorResources.INSTANCE.getClass();
        TypedArray typedArrayObtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, attributeSetAsAttributeSet, AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_TYPE_ARRAY);
        androidVectorParser.updateConfig(typedArrayObtainAttributes.getChangingConfigurations());
        boolean z = !TypedArrayUtils.hasAttribute(androidVectorParser.xmlParser, "autoMirrored") ? false : typedArrayObtainAttributes.getBoolean(AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_AUTO_MIRRORED, false);
        androidVectorParser.updateConfig(typedArrayObtainAttributes.getChangingConfigurations());
        float namedFloat = androidVectorParser.getNamedFloat(typedArrayObtainAttributes, "viewportWidth", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_VIEWPORT_WIDTH, 0.0f);
        float namedFloat2 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes, "viewportHeight", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_VIEWPORT_HEIGHT, 0.0f);
        if (namedFloat <= 0.0f) {
            throw new XmlPullParserException(typedArrayObtainAttributes.getPositionDescription() + "<VectorGraphic> tag requires viewportWidth > 0");
        }
        if (namedFloat2 <= 0.0f) {
            throw new XmlPullParserException(typedArrayObtainAttributes.getPositionDescription() + "<VectorGraphic> tag requires viewportHeight > 0");
        }
        float dimension = typedArrayObtainAttributes.getDimension(AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_WIDTH, 0.0f);
        androidVectorParser.updateConfig(typedArrayObtainAttributes.getChangingConfigurations());
        float dimension2 = typedArrayObtainAttributes.getDimension(AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_HEIGHT, 0.0f);
        androidVectorParser.updateConfig(typedArrayObtainAttributes.getChangingConfigurations());
        int i8 = AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_TINT;
        if (typedArrayObtainAttributes.hasValue(i8)) {
            TypedValue typedValue = new TypedValue();
            typedArrayObtainAttributes.getValue(i8, typedValue);
            if (typedValue.type == 2) {
                Color.Companion.getClass();
                jColor = Color.Unspecified;
            } else {
                ColorStateList namedColorStateList = TypedArrayUtils.getNamedColorStateList(typedArrayObtainAttributes, androidVectorParser.xmlParser, theme, i8);
                androidVectorParser.updateConfig(typedArrayObtainAttributes.getChangingConfigurations());
                if (namedColorStateList != null) {
                    jColor = ColorKt.Color(namedColorStateList.getDefaultColor());
                } else {
                    Color.Companion.getClass();
                    jColor = Color.Unspecified;
                }
            }
        } else {
            Color.Companion.getClass();
            jColor = Color.Unspecified;
        }
        long j = jColor;
        int i9 = typedArrayObtainAttributes.getInt(AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_TINT_MODE, -1);
        androidVectorParser.updateConfig(typedArrayObtainAttributes.getChangingConfigurations());
        if (i9 == -1) {
            BlendMode.Companion.getClass();
            i2 = BlendMode.SrcIn;
        } else if (i9 == 3) {
            BlendMode.Companion.getClass();
            i2 = BlendMode.SrcOver;
        } else if (i9 == 5) {
            BlendMode.Companion.getClass();
            i2 = BlendMode.SrcIn;
        } else if (i9 != 9) {
            switch (i9) {
                case 14:
                    BlendMode.Companion.getClass();
                    i2 = BlendMode.Modulate;
                    break;
                case 15:
                    BlendMode.Companion.getClass();
                    i2 = BlendMode.Screen;
                    break;
                case 16:
                    BlendMode.Companion.getClass();
                    i2 = BlendMode.Plus;
                    break;
                default:
                    BlendMode.Companion.getClass();
                    i2 = BlendMode.SrcIn;
                    break;
            }
        } else {
            BlendMode.Companion.getClass();
            i2 = BlendMode.SrcAtop;
        }
        float f = dimension / resources.getDisplayMetrics().density;
        Dp.Companion companion = Dp.Companion;
        float f2 = dimension2 / resources.getDisplayMetrics().density;
        typedArrayObtainAttributes.recycle();
        ImageVector.Builder builder = new ImageVector.Builder(null, f, f2, namedFloat, namedFloat2, j, i2, z, 1, null);
        int i10 = 0;
        for (int i11 = 3; xmlResourceParser.getEventType() != 1 && (xmlResourceParser.getDepth() >= 1 || xmlResourceParser.getEventType() != i11); i11 = 3) {
            int eventType = androidVectorParser.xmlParser.getEventType();
            if (eventType == i7) {
                String name = androidVectorParser.xmlParser.getName();
                if (name != null) {
                    int iHashCode = name.hashCode();
                    PathParser pathParser = androidVectorParser.pathParser;
                    if (iHashCode != -1649314686) {
                        if (iHashCode != 3433509) {
                            if (iHashCode == 98629247 && name.equals("group")) {
                                AndroidVectorResources.INSTANCE.getClass();
                                TypedArray typedArrayObtainAttributes2 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSetAsAttributeSet, AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_GROUP);
                                androidVectorParser.updateConfig(typedArrayObtainAttributes2.getChangingConfigurations());
                                float namedFloat3 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes2, "rotation", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_GROUP_ROTATION, 0.0f);
                                float f3 = typedArrayObtainAttributes2.getFloat(AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_GROUP_PIVOT_X, 0.0f);
                                androidVectorParser.updateConfig(typedArrayObtainAttributes2.getChangingConfigurations());
                                float f4 = typedArrayObtainAttributes2.getFloat(AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_GROUP_PIVOT_Y, 0.0f);
                                androidVectorParser.updateConfig(typedArrayObtainAttributes2.getChangingConfigurations());
                                float namedFloat4 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes2, "scaleX", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_GROUP_SCALE_X, 1.0f);
                                float namedFloat5 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes2, "scaleY", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_GROUP_SCALE_Y, 1.0f);
                                float namedFloat6 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes2, "translateX", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_GROUP_TRANSLATE_X, 0.0f);
                                float namedFloat7 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes2, "translateY", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_GROUP_TRANSLATE_Y, 0.0f);
                                String string = typedArrayObtainAttributes2.getString(0);
                                androidVectorParser.updateConfig(typedArrayObtainAttributes2.getChangingConfigurations());
                                String str = string == null ? "" : string;
                                typedArrayObtainAttributes2.recycle();
                                builder.addGroup(str, namedFloat3, f3, f4, namedFloat4, namedFloat5, namedFloat6, namedFloat7, VectorKt.EmptyPath);
                            }
                        } else if (name.equals("path")) {
                            AndroidVectorResources.INSTANCE.getClass();
                            TypedArray typedArrayObtainAttributes3 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSetAsAttributeSet, AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH);
                            androidVectorParser.updateConfig(typedArrayObtainAttributes3.getChangingConfigurations());
                            if (!TypedArrayUtils.hasAttribute(androidVectorParser.xmlParser, "pathData")) {
                                throw new IllegalArgumentException("No path data available");
                            }
                            String string2 = typedArrayObtainAttributes3.getString(0);
                            androidVectorParser.updateConfig(typedArrayObtainAttributes3.getChangingConfigurations());
                            String str2 = string2 == null ? "" : string2;
                            String string3 = typedArrayObtainAttributes3.getString(AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_PATH_DATA);
                            androidVectorParser.updateConfig(typedArrayObtainAttributes3.getChangingConfigurations());
                            if (string3 == null) {
                                list2 = VectorKt.EmptyPath;
                            } else {
                                ArrayList arrayList = new ArrayList();
                                pathParser.pathStringToNodes(string3, arrayList);
                                list2 = arrayList;
                            }
                            ComplexColorCompat namedComplexColor = TypedArrayUtils.getNamedComplexColor(typedArrayObtainAttributes3, androidVectorParser.xmlParser, theme, "fillColor", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_FILL_COLOR);
                            androidVectorParser.updateConfig(typedArrayObtainAttributes3.getChangingConfigurations());
                            float namedFloat8 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes3, "fillAlpha", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_FILL_ALPHA, 1.0f);
                            int namedInt3 = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes3, androidVectorParser.xmlParser, "strokeLineCap", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_STROKE_LINE_CAP, -1);
                            androidVectorParser.updateConfig(typedArrayObtainAttributes3.getChangingConfigurations());
                            StrokeCap.Companion.getClass();
                            if (namedInt3 != 0) {
                                if (namedInt3 == 1) {
                                    i6 = StrokeCap.Round;
                                } else if (namedInt3 == 2) {
                                    i6 = StrokeCap.Square;
                                }
                                i3 = i6;
                                namedInt = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes3, androidVectorParser.xmlParser, "strokeLineJoin", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_STROKE_LINE_JOIN, -1);
                                androidVectorParser.updateConfig(typedArrayObtainAttributes3.getChangingConfigurations());
                                StrokeJoin.Companion.getClass();
                                int i12 = StrokeJoin.Bevel;
                                if (namedInt == 0) {
                                    if (namedInt == 1) {
                                        i12 = StrokeJoin.Round;
                                    }
                                    i4 = i12;
                                } else {
                                    i4 = 0;
                                }
                                float namedFloat9 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes3, "strokeMiterLimit", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_STROKE_MITER_LIMIT, 1.0f);
                                ComplexColorCompat namedComplexColor2 = TypedArrayUtils.getNamedComplexColor(typedArrayObtainAttributes3, androidVectorParser.xmlParser, theme, "strokeColor", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_STROKE_COLOR);
                                androidVectorParser.updateConfig(typedArrayObtainAttributes3.getChangingConfigurations());
                                float namedFloat10 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes3, "strokeAlpha", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_STROKE_ALPHA, 1.0f);
                                float namedFloat11 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes3, "strokeWidth", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_STROKE_WIDTH, 1.0f);
                                float namedFloat12 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes3, "trimPathEnd", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_TRIM_PATH_END, 1.0f);
                                float namedFloat13 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes3, "trimPathOffset", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_TRIM_PATH_OFFSET, 0.0f);
                                float namedFloat14 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes3, "trimPathStart", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_TRIM_PATH_START, 0.0f);
                                namedInt2 = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes3, androidVectorParser.xmlParser, "fillType", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_TRIM_PATH_FILLTYPE, 0);
                                androidVectorParser.updateConfig(typedArrayObtainAttributes3.getChangingConfigurations());
                                typedArrayObtainAttributes3.recycle();
                                Shader shader = namedComplexColor.mShader;
                                Brush brushKt$ShaderBrush$1 = !(shader != null || namedComplexColor.mColor != 0) ? shader != null ? new BrushKt$ShaderBrush$1(shader) : new SolidColor(ColorKt.Color(namedComplexColor.mColor), null) : null;
                                Shader shader2 = namedComplexColor2.mShader;
                                Brush brushKt$ShaderBrush$12 = !(shader2 != null || namedComplexColor2.mColor != 0) ? shader2 != null ? new BrushKt$ShaderBrush$1(shader2) : new SolidColor(ColorKt.Color(namedComplexColor2.mColor), null) : null;
                                if (namedInt2 != 0) {
                                    PathFillType.Companion.getClass();
                                    i5 = 0;
                                } else {
                                    PathFillType.Companion.getClass();
                                    i5 = PathFillType.EvenOdd;
                                }
                                builder.m567addPathoIyEayM(str2, list2, i5, brushKt$ShaderBrush$1, namedFloat8, brushKt$ShaderBrush$12, namedFloat10, namedFloat11, i3, i4, namedFloat9, namedFloat14, namedFloat12, namedFloat13);
                            }
                            i3 = 0;
                            namedInt = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes3, androidVectorParser.xmlParser, "strokeLineJoin", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_STROKE_LINE_JOIN, -1);
                            androidVectorParser.updateConfig(typedArrayObtainAttributes3.getChangingConfigurations());
                            StrokeJoin.Companion.getClass();
                            int i122 = StrokeJoin.Bevel;
                            if (namedInt == 0) {
                            }
                            float namedFloat92 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes3, "strokeMiterLimit", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_STROKE_MITER_LIMIT, 1.0f);
                            ComplexColorCompat namedComplexColor22 = TypedArrayUtils.getNamedComplexColor(typedArrayObtainAttributes3, androidVectorParser.xmlParser, theme, "strokeColor", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_STROKE_COLOR);
                            androidVectorParser.updateConfig(typedArrayObtainAttributes3.getChangingConfigurations());
                            float namedFloat102 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes3, "strokeAlpha", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_STROKE_ALPHA, 1.0f);
                            float namedFloat112 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes3, "strokeWidth", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_STROKE_WIDTH, 1.0f);
                            float namedFloat122 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes3, "trimPathEnd", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_TRIM_PATH_END, 1.0f);
                            float namedFloat132 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes3, "trimPathOffset", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_TRIM_PATH_OFFSET, 0.0f);
                            float namedFloat142 = androidVectorParser.getNamedFloat(typedArrayObtainAttributes3, "trimPathStart", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_TRIM_PATH_START, 0.0f);
                            namedInt2 = TypedArrayUtils.getNamedInt(typedArrayObtainAttributes3, androidVectorParser.xmlParser, "fillType", AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_PATH_TRIM_PATH_FILLTYPE, 0);
                            androidVectorParser.updateConfig(typedArrayObtainAttributes3.getChangingConfigurations());
                            typedArrayObtainAttributes3.recycle();
                            Shader shader3 = namedComplexColor.mShader;
                            if (shader3 != null) {
                                if (!(shader3 != null || namedComplexColor.mColor != 0)) {
                                }
                                Shader shader22 = namedComplexColor22.mShader;
                                if (shader22 != null) {
                                    if (!(shader22 != null || namedComplexColor22.mColor != 0)) {
                                    }
                                    if (namedInt2 != 0) {
                                    }
                                    builder.m567addPathoIyEayM(str2, list2, i5, brushKt$ShaderBrush$1, namedFloat8, brushKt$ShaderBrush$12, namedFloat102, namedFloat112, i3, i4, namedFloat92, namedFloat142, namedFloat122, namedFloat132);
                                }
                            }
                        }
                    } else if (name.equals("clip-path")) {
                        AndroidVectorResources.INSTANCE.getClass();
                        TypedArray typedArrayObtainAttributes4 = TypedArrayUtils.obtainAttributes(resources, theme, attributeSetAsAttributeSet, AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_CLIP_PATH);
                        androidVectorParser.updateConfig(typedArrayObtainAttributes4.getChangingConfigurations());
                        String string4 = typedArrayObtainAttributes4.getString(0);
                        androidVectorParser.updateConfig(typedArrayObtainAttributes4.getChangingConfigurations());
                        String str3 = string4 == null ? "" : string4;
                        String string5 = typedArrayObtainAttributes4.getString(AndroidVectorResources.STYLEABLE_VECTOR_DRAWABLE_CLIP_PATH_PATH_DATA);
                        androidVectorParser.updateConfig(typedArrayObtainAttributes4.getChangingConfigurations());
                        if (string5 == null) {
                            list = VectorKt.EmptyPath;
                        } else {
                            ArrayList arrayList2 = new ArrayList();
                            pathParser.pathStringToNodes(string5, arrayList2);
                            list = arrayList2;
                        }
                        typedArrayObtainAttributes4.recycle();
                        builder.addGroup(str3, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, list);
                        i10++;
                    }
                }
                xmlResourceParser.next();
                i7 = 2;
            } else if (eventType == i11 && "group".equals(androidVectorParser.xmlParser.getName())) {
                int i13 = i10 + 1;
                for (int i14 = 0; i14 < i13; i14++) {
                    builder.clearGroup();
                }
                i10 = 0;
            }
            xmlResourceParser.next();
            i7 = 2;
        }
        return new ImageVectorCache.ImageVectorEntry(builder.build(), i);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final ImageVector vectorResource(int i, Composer composer) throws XmlPullParserException, Resources.NotFoundException, IOException {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.ui.res.vectorResource (VectorResources.android.kt:48)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        Resources resources = (Resources) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalResources);
        Resources.Theme theme = context.getTheme();
        Configuration configuration = resources.getConfiguration();
        boolean zChanged = composerImpl.changed(configuration) | composerImpl.changed(i) | composerImpl.changed(resources) | composerImpl.changed(theme);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                TypedValue typedValue = new TypedValue();
                resources.getValue(i, typedValue, true);
                XmlResourceParser xml = resources.getXml(i);
                int next = xml.next();
                while (next != 2 && next != 1) {
                    next = xml.next();
                }
                if (next != 2) {
                    throw new XmlPullParserException("No start tag found");
                }
                Unit unit = Unit.INSTANCE;
                objRememberedValue = loadVectorResourceInner(theme, resources, xml, typedValue.changingConfigurations).imageVector;
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        ImageVector imageVector = (ImageVector) objRememberedValue;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return imageVector;
    }
}
