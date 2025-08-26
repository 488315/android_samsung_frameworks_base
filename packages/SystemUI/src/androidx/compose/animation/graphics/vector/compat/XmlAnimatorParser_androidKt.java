package androidx.compose.animation.graphics.vector.compat;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.util.Xml;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.PathInterpolator;
import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.Easing;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.RepeatMode;
import androidx.compose.animation.graphics.res.AnimatorResources_androidKt;
import androidx.compose.animation.graphics.res.AnimatorResources_androidKt$$ExternalSyntheticLambda0;
import androidx.compose.animation.graphics.vector.AnimatorSet;
import androidx.compose.animation.graphics.vector.Keyframe;
import androidx.compose.animation.graphics.vector.ObjectAnimator;
import androidx.compose.animation.graphics.vector.Ordering;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder1D;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D;
import androidx.compose.animation.graphics.vector.PropertyValuesHolderColor;
import androidx.compose.animation.graphics.vector.PropertyValuesHolderFloat;
import androidx.compose.animation.graphics.vector.PropertyValuesHolderInt;
import androidx.compose.animation.graphics.vector.PropertyValuesHolderPath;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.core.graphics.PathParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public abstract class XmlAnimatorParser_androidKt {
    public static final ValueType FallbackValueType = ValueType.Float;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ValueType.values().length];
            try {
                iArr[ValueType.Float.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ValueType.Int.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ValueType.Color.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ValueType.Path.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final Easing getInterpolator(TypedArray typedArray, Resources resources, Resources.Theme theme, int i, Easing easing) throws XmlPullParserException, Resources.NotFoundException, IOException {
        TypedArray typedArrayObtainAttributes;
        Easing easing2;
        Easing easing3;
        int resourceId = typedArray.getResourceId(i, 0);
        if (resourceId == 0) {
            return easing;
        }
        Easing easing4 = (Easing) AnimatorResources_androidKt.builtinInterpolators.get(Integer.valueOf(resourceId));
        if (easing4 != null) {
            return easing4;
        }
        XmlResourceParser xml = resources.getXml(resourceId);
        XmlPullParserUtils_androidKt.seekToStartTag(xml);
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xml);
        String name = xml.getName();
        if (name != null) {
            switch (name.hashCode()) {
                case -2140409460:
                    if (name.equals("pathInterpolator")) {
                        AndroidVectorResources.INSTANCE.getClass();
                        int[] iArr = AndroidVectorResources.STYLEABLE_PATH_INTERPOLATOR;
                        if (theme == null || (typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSetAsAttributeSet, iArr, 0, 0)) == null) {
                            TypedArray typedArrayObtainAttributes2 = resources.obtainAttributes(attributeSetAsAttributeSet, iArr);
                        }
                        try {
                            String string = typedArrayObtainAttributes.getString(4);
                            return string != null ? new AnimatorResources_androidKt$$ExternalSyntheticLambda0(new PathInterpolator(PathParser.createPathFromPathData(string))) : (typedArrayObtainAttributes.hasValue(2) && typedArrayObtainAttributes.hasValue(3)) ? new CubicBezierEasing(typedArrayObtainAttributes.getFloat(0, 0.0f), typedArrayObtainAttributes.getFloat(1, 0.0f), typedArrayObtainAttributes.getFloat(2, 1.0f), typedArrayObtainAttributes.getFloat(3, 1.0f)) : new AnimatorResources_androidKt$$ExternalSyntheticLambda0(new PathInterpolator(typedArrayObtainAttributes.getFloat(0, 0.0f), typedArrayObtainAttributes.getFloat(1, 0.0f)));
                        } finally {
                        }
                    }
                    break;
                case -2120889007:
                    if (name.equals("anticipateInterpolator")) {
                        AndroidVectorResources.INSTANCE.getClass();
                        int[] iArr2 = AndroidVectorResources.STYLEABLE_ANTICIPATEOVERSHOOT_INTERPOLATOR;
                        if (theme == null || (typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSetAsAttributeSet, iArr2, 0, 0)) == null) {
                            TypedArray typedArrayObtainAttributes3 = resources.obtainAttributes(attributeSetAsAttributeSet, iArr2);
                        }
                        try {
                            final float f = typedArrayObtainAttributes.getFloat(0, 2.0f);
                            final int i2 = 0;
                            return new Easing() { // from class: androidx.compose.animation.graphics.res.AnimatorResources_androidKt$$ExternalSyntheticLambda1
                                @Override // androidx.compose.animation.core.Easing
                                public final float transform(float f2) {
                                    float f3 = f;
                                    switch (i2) {
                                        case 0:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda3 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            return (((1 + f3) * f2) - f3) * f2 * f2;
                                        case 1:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda32 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            return (float) Math.pow(f2, f3 * 2);
                                        case 2:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda33 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            float f4 = f2 - 1.0f;
                                            return ((((f3 + 1.0f) * f4) + f3) * f4 * f4) + 1.0f;
                                        case 3:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda34 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            return (float) Math.sin(2 * f3 * 3.141592653589793d * f2);
                                        default:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda35 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            return 1.0f - ((float) Math.pow(1.0f - f2, 2 * f3));
                                    }
                                }
                            };
                        } finally {
                        }
                    }
                    break;
                case -1248486260:
                    if (name.equals("linearInterpolator")) {
                        return EasingKt.LinearEasing;
                    }
                    break;
                case -935873468:
                    if (name.equals("accelerateInterpolator")) {
                        AndroidVectorResources.INSTANCE.getClass();
                        int[] iArr3 = AndroidVectorResources.STYLEABLE_ACCELERATE_INTERPOLATOR;
                        if (theme == null || (typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSetAsAttributeSet, iArr3, 0, 0)) == null) {
                            TypedArray typedArrayObtainAttributes4 = resources.obtainAttributes(attributeSetAsAttributeSet, iArr3);
                        }
                        try {
                            final float f2 = typedArrayObtainAttributes.getFloat(0, 1.0f);
                            if (f2 == 1.0f) {
                                easing2 = AnimatorResources_androidKt.AccelerateEasing;
                            } else {
                                final int i3 = 1;
                                easing2 = new Easing() { // from class: androidx.compose.animation.graphics.res.AnimatorResources_androidKt$$ExternalSyntheticLambda1
                                    @Override // androidx.compose.animation.core.Easing
                                    public final float transform(float f22) {
                                        float f3 = f2;
                                        switch (i3) {
                                            case 0:
                                                AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda3 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                                return (((1 + f3) * f22) - f3) * f22 * f22;
                                            case 1:
                                                AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda32 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                                return (float) Math.pow(f22, f3 * 2);
                                            case 2:
                                                AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda33 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                                float f4 = f22 - 1.0f;
                                                return ((((f3 + 1.0f) * f4) + f3) * f4 * f4) + 1.0f;
                                            case 3:
                                                AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda34 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                                return (float) Math.sin(2 * f3 * 3.141592653589793d * f22);
                                            default:
                                                AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda35 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                                return 1.0f - ((float) Math.pow(1.0f - f22, 2 * f3));
                                        }
                                    }
                                };
                            }
                            return easing2;
                        } finally {
                        }
                    }
                    break;
                case -425326737:
                    if (name.equals("bounceInterpolator")) {
                        return AnimatorResources_androidKt.BounceEasing;
                    }
                    break;
                case 1192587314:
                    if (name.equals("overshootInterpolator")) {
                        AndroidVectorResources.INSTANCE.getClass();
                        int[] iArr4 = AndroidVectorResources.STYLEABLE_OVERSHOOT_INTERPOLATOR;
                        if (theme == null || (typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSetAsAttributeSet, iArr4, 0, 0)) == null) {
                            typedArrayObtainAttributes = resources.obtainAttributes(attributeSetAsAttributeSet, iArr4);
                        }
                        try {
                            final float f3 = typedArrayObtainAttributes.getFloat(0, 2.0f);
                            final int i4 = 2;
                            return new Easing() { // from class: androidx.compose.animation.graphics.res.AnimatorResources_androidKt$$ExternalSyntheticLambda1
                                @Override // androidx.compose.animation.core.Easing
                                public final float transform(float f22) {
                                    float f32 = f3;
                                    switch (i4) {
                                        case 0:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda3 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            return (((1 + f32) * f22) - f32) * f22 * f22;
                                        case 1:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda32 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            return (float) Math.pow(f22, f32 * 2);
                                        case 2:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda33 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            float f4 = f22 - 1.0f;
                                            return ((((f32 + 1.0f) * f4) + f32) * f4 * f4) + 1.0f;
                                        case 3:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda34 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            return (float) Math.sin(2 * f32 * 3.141592653589793d * f22);
                                        default:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda35 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            return 1.0f - ((float) Math.pow(1.0f - f22, 2 * f32));
                                    }
                                }
                            };
                        } finally {
                        }
                    }
                    break;
                case 1472030440:
                    if (name.equals("anticipateOvershootInterpolator")) {
                        AndroidVectorResources.INSTANCE.getClass();
                        int[] iArr5 = AndroidVectorResources.STYLEABLE_ANTICIPATEOVERSHOOT_INTERPOLATOR;
                        if (theme == null || (typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSetAsAttributeSet, iArr5, 0, 0)) == null) {
                            TypedArray typedArrayObtainAttributes5 = resources.obtainAttributes(attributeSetAsAttributeSet, iArr5);
                        }
                        try {
                            return new AnimatorResources_androidKt$$ExternalSyntheticLambda0(new AnticipateOvershootInterpolator(typedArrayObtainAttributes.getFloat(0, 2.0f), typedArrayObtainAttributes.getFloat(1, 1.5f)));
                        } finally {
                        }
                    }
                    break;
                case 1962594083:
                    if (name.equals("decelerateInterpolator")) {
                        AndroidVectorResources.INSTANCE.getClass();
                        int[] iArr6 = AndroidVectorResources.STYLEABLE_DECELERATE_INTERPOLATOR;
                        if (theme == null || (typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSetAsAttributeSet, iArr6, 0, 0)) == null) {
                            TypedArray typedArrayObtainAttributes6 = resources.obtainAttributes(attributeSetAsAttributeSet, iArr6);
                        }
                        try {
                            final float f4 = typedArrayObtainAttributes.getFloat(0, 1.0f);
                            if (f4 == 1.0f) {
                                easing3 = AnimatorResources_androidKt.DecelerateEasing;
                            } else {
                                final int i5 = 4;
                                easing3 = new Easing() { // from class: androidx.compose.animation.graphics.res.AnimatorResources_androidKt$$ExternalSyntheticLambda1
                                    @Override // androidx.compose.animation.core.Easing
                                    public final float transform(float f22) {
                                        float f32 = f4;
                                        switch (i5) {
                                            case 0:
                                                AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda3 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                                return (((1 + f32) * f22) - f32) * f22 * f22;
                                            case 1:
                                                AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda32 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                                return (float) Math.pow(f22, f32 * 2);
                                            case 2:
                                                AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda33 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                                float f42 = f22 - 1.0f;
                                                return ((((f32 + 1.0f) * f42) + f32) * f42 * f42) + 1.0f;
                                            case 3:
                                                AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda34 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                                return (float) Math.sin(2 * f32 * 3.141592653589793d * f22);
                                            default:
                                                AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda35 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                                return 1.0f - ((float) Math.pow(1.0f - f22, 2 * f32));
                                        }
                                    }
                                };
                            }
                            return easing3;
                        } finally {
                        }
                    }
                    break;
                case 2019672672:
                    if (name.equals("accelerateDecelerateInterpolator")) {
                        return AnimatorResources_androidKt.AccelerateDecelerateEasing;
                    }
                    break;
                case 2038238413:
                    if (name.equals("cycleInterpolator")) {
                        AndroidVectorResources.INSTANCE.getClass();
                        int[] iArr7 = AndroidVectorResources.STYLEABLE_CYCLE_INTERPOLATOR;
                        if (theme == null || (typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSetAsAttributeSet, iArr7, 0, 0)) == null) {
                            TypedArray typedArrayObtainAttributes7 = resources.obtainAttributes(attributeSetAsAttributeSet, iArr7);
                        }
                        try {
                            final float f5 = typedArrayObtainAttributes.getFloat(0, 1.0f);
                            final int i6 = 3;
                            return new Easing() { // from class: androidx.compose.animation.graphics.res.AnimatorResources_androidKt$$ExternalSyntheticLambda1
                                @Override // androidx.compose.animation.core.Easing
                                public final float transform(float f22) {
                                    float f32 = f5;
                                    switch (i6) {
                                        case 0:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda3 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            return (((1 + f32) * f22) - f32) * f22 * f22;
                                        case 1:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda32 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            return (float) Math.pow(f22, f32 * 2);
                                        case 2:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda33 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            float f42 = f22 - 1.0f;
                                            return ((((f32 + 1.0f) * f42) + f32) * f42 * f42) + 1.0f;
                                        case 3:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda34 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            return (float) Math.sin(2 * f32 * 3.141592653589793d * f22);
                                        default:
                                            AnimatorResources_androidKt$$ExternalSyntheticLambda3 animatorResources_androidKt$$ExternalSyntheticLambda35 = AnimatorResources_androidKt.AccelerateDecelerateEasing;
                                            return 1.0f - ((float) Math.pow(1.0f - f22, 2 * f32));
                                    }
                                }
                            };
                        } finally {
                        }
                    }
                    break;
            }
        }
        throw new RuntimeException("Unknown interpolator: " + xml.getName());
    }

    public static final Keyframe getKeyframe(TypedArray typedArray, float f, Easing easing, ValueType valueType, int i) {
        Object obj;
        int i2 = WhenMappings.$EnumSwitchMapping$0[valueType.ordinal()];
        if (i2 == 1) {
            return new Keyframe(f, Float.valueOf(typedArray.getFloat(i, 0.0f)), easing);
        }
        if (i2 == 2) {
            return new Keyframe(f, Integer.valueOf(typedArray.getInt(i, 0)), easing);
        }
        if (i2 == 3) {
            return new Keyframe(f, Color.m456boximpl(ColorKt.Color(typedArray.getColor(i, 0))), easing);
        }
        if (i2 != 4) {
            throw new NoWhenBranchMatchedException();
        }
        String string = typedArray.getString(i);
        if (string == null) {
            obj = VectorKt.EmptyPath;
        } else {
            EmptyList emptyList = VectorKt.EmptyPath;
            androidx.compose.ui.graphics.vector.PathParser pathParser = new androidx.compose.ui.graphics.vector.PathParser();
            pathParser.parsePathString(string);
            obj = pathParser.nodes;
            if (obj == null) {
                obj = EmptyList.INSTANCE;
            }
        }
        return new Keyframe(f, obj, easing);
    }

    public static final PropertyValuesHolder1D getPropertyValuesHolder1D(TypedArray typedArray, String str, int i, int i2, int i3, Easing easing, Function2 function2) {
        int i4 = typedArray.getInt(i, 4);
        TypedValue typedValuePeekValue = typedArray.peekValue(i2);
        boolean z = typedValuePeekValue != null;
        int i5 = typedValuePeekValue != null ? typedValuePeekValue.type : 4;
        TypedValue typedValuePeekValue2 = typedArray.peekValue(i3);
        boolean z2 = typedValuePeekValue2 != null;
        ValueType valueTypeInferValueType = inferValueType(i4, i5, typedValuePeekValue2 != null ? typedValuePeekValue2.type : 4);
        ArrayList arrayList = new ArrayList();
        if (valueTypeInferValueType == null && (z || z2)) {
            valueTypeInferValueType = ValueType.Float;
        }
        if (z) {
            valueTypeInferValueType.getClass();
            arrayList.add(getKeyframe(typedArray, 0.0f, easing, valueTypeInferValueType, i2));
        }
        if (z2) {
            valueTypeInferValueType.getClass();
            arrayList.add(getKeyframe(typedArray, 1.0f, easing, valueTypeInferValueType, i3));
        }
        ValueType valueType = (ValueType) function2.invoke(valueTypeInferValueType, arrayList);
        if (arrayList.size() > 1) {
            CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList, new Comparator() { // from class: androidx.compose.animation.graphics.vector.compat.XmlAnimatorParser_androidKt$getPropertyValuesHolder1D$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ComparisonsKt__ComparisonsKt.compareValues(Float.valueOf(((Keyframe) obj).fraction), Float.valueOf(((Keyframe) obj2).fraction));
                }
            });
        }
        int i6 = WhenMappings.$EnumSwitchMapping$0[valueType.ordinal()];
        if (i6 == 1) {
            return new PropertyValuesHolderFloat(str, arrayList);
        }
        if (i6 == 2) {
            return new PropertyValuesHolderInt(str, arrayList);
        }
        if (i6 == 3) {
            return new PropertyValuesHolderColor(str, arrayList);
        }
        if (i6 == 4) {
            return new PropertyValuesHolderPath(str, arrayList);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final ValueType inferValueType(int i, int... iArr) {
        if (i == 0) {
            return ValueType.Float;
        }
        if (i == 1) {
            return ValueType.Int;
        }
        if (i == 2) {
            return ValueType.Path;
        }
        if (i == 3) {
            return ValueType.Color;
        }
        for (int i2 : iArr) {
            if (28 > i2 || i2 >= 32) {
                return null;
            }
        }
        return ValueType.Color;
    }

    public static final AnimatorSet parseAnimatorSet(Resources resources, Resources.Theme theme, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        TypedArray typedArrayObtainAttributes;
        AndroidVectorResources.INSTANCE.getClass();
        int[] iArr = AndroidVectorResources.STYLEABLE_ANIMATOR_SET;
        if (theme == null || (typedArrayObtainAttributes = theme.obtainStyledAttributes(attributeSet, iArr, 0, 0)) == null) {
            typedArrayObtainAttributes = resources.obtainAttributes(attributeSet, iArr);
        }
        try {
            int i = typedArrayObtainAttributes.getInt(0, 0);
            ArrayList arrayList = new ArrayList();
            xmlPullParser.next();
            while (!XmlPullParserUtils_androidKt.isAtEnd(xmlPullParser) && (xmlPullParser.getEventType() != 3 || !Intrinsics.areEqual(xmlPullParser.getName(), "set"))) {
                if (xmlPullParser.getEventType() == 2) {
                    String name = xmlPullParser.getName();
                    if (Intrinsics.areEqual(name, "set")) {
                        arrayList.add(parseAnimatorSet(resources, theme, attributeSet, xmlPullParser));
                    } else if (Intrinsics.areEqual(name, "objectAnimator")) {
                        arrayList.add(parseObjectAnimator(resources, theme, attributeSet, xmlPullParser));
                    }
                }
                xmlPullParser.next();
            }
            AnimatorSet animatorSet = new AnimatorSet(arrayList, i != 0 ? Ordering.Sequentially : Ordering.Together);
            typedArrayObtainAttributes.recycle();
            return animatorSet;
        } catch (Throwable th) {
            typedArrayObtainAttributes.recycle();
            throw th;
        }
    }

    public static final ObjectAnimator parseObjectAnimator(Resources resources, Resources.Theme theme, AttributeSet attributeSet, final XmlPullParser xmlPullParser) {
        TypedArray typedArrayObtainAttributes;
        TypedArray typedArrayObtainAttributes2;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Easing easing;
        ArrayList arrayList;
        int i6;
        TypedArray typedArrayObtainAttributes3;
        TypedArray typedArray;
        final Resources resources2 = resources;
        final Resources.Theme theme2 = theme;
        final AttributeSet attributeSet2 = attributeSet;
        AndroidVectorResources.INSTANCE.getClass();
        int[] iArr = AndroidVectorResources.STYLEABLE_ANIMATOR;
        if (theme2 == null || (typedArrayObtainAttributes = theme2.obtainStyledAttributes(attributeSet2, iArr, 0, 0)) == null) {
            typedArrayObtainAttributes = resources2.obtainAttributes(attributeSet2, iArr);
        }
        TypedArray typedArray2 = typedArrayObtainAttributes;
        try {
            int[] iArr2 = AndroidVectorResources.STYLEABLE_PROPERTY_ANIMATOR;
            if (theme2 == null || (typedArrayObtainAttributes2 = theme2.obtainStyledAttributes(attributeSet2, iArr2, 0, 0)) == null) {
                typedArrayObtainAttributes2 = resources2.obtainAttributes(attributeSet2, iArr2);
            }
            typedArray2 = typedArrayObtainAttributes2;
            try {
                Easing interpolator = getInterpolator(typedArray2, resources2, theme2, 0, AnimatorResources_androidKt.AccelerateDecelerateEasing);
                ArrayList arrayList2 = new ArrayList();
                int i7 = 1;
                String string = typedArray2.getString(1);
                int i8 = 3;
                if (string != null) {
                    String string2 = typedArray2.getString(2);
                    string2.getClass();
                    String string3 = typedArray2.getString(3);
                    string3.getClass();
                    EmptyList emptyList = VectorKt.EmptyPath;
                    androidx.compose.ui.graphics.vector.PathParser pathParser = new androidx.compose.ui.graphics.vector.PathParser();
                    pathParser.parsePathString(string);
                    List list = pathParser.nodes;
                    if (list == null) {
                        list = EmptyList.INSTANCE;
                    }
                    arrayList2.add(new PropertyValuesHolder2D(string2, string3, list, interpolator));
                    i = 1;
                    i3 = 3;
                    i2 = 2;
                } else {
                    int i9 = 2;
                    String string4 = typedArray2.getString(0);
                    if (string4 != null) {
                        arrayList2.add(getPropertyValuesHolder1D(typedArray2, string4, 7, 5, 6, interpolator, new Function2() { // from class: androidx.compose.animation.graphics.vector.compat.XmlAnimatorParser_androidKt.getPropertyValuesHolder1D.1
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ValueType valueType = (ValueType) obj;
                                return valueType == null ? XmlAnimatorParser_androidKt.FallbackValueType : valueType;
                            }
                        }));
                    }
                    xmlPullParser.next();
                    while (!XmlPullParserUtils_androidKt.isAtEnd(xmlPullParser) && (xmlPullParser.getEventType() != i8 || !Intrinsics.areEqual(xmlPullParser.getName(), "objectAnimator"))) {
                        if (xmlPullParser.getEventType() == i9 && Intrinsics.areEqual(xmlPullParser.getName(), "propertyValuesHolder")) {
                            AndroidVectorResources.INSTANCE.getClass();
                            int[] iArr3 = AndroidVectorResources.STYLEABLE_PROPERTY_VALUES_HOLDER;
                            if (theme2 == null || (typedArrayObtainAttributes3 = theme2.obtainStyledAttributes(attributeSet2, iArr3, 0, 0)) == null) {
                                typedArrayObtainAttributes3 = resources2.obtainAttributes(attributeSet2, iArr3);
                            }
                            try {
                                String string5 = typedArrayObtainAttributes3.getString(i8);
                                string5.getClass();
                                i4 = i7;
                                i5 = i8;
                                final Easing easing2 = interpolator;
                                i6 = i9;
                                Function2 function2 = new Function2() { // from class: androidx.compose.animation.graphics.vector.compat.XmlAnimatorParser_androidKt$parsePropertyValuesHolder$1$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) throws XmlPullParserException, IOException {
                                        TypedArray typedArrayObtainAttributes4;
                                        ValueType valueTypeInferValueType;
                                        ValueType valueType = (ValueType) obj;
                                        List list2 = (List) obj2;
                                        XmlPullParser xmlPullParser2 = xmlPullParser;
                                        Resources resources3 = resources2;
                                        Resources.Theme theme3 = theme2;
                                        AttributeSet attributeSet3 = attributeSet2;
                                        Easing easing3 = easing2;
                                        xmlPullParser2.next();
                                        ValueType valueType2 = null;
                                        while (!XmlPullParserUtils_androidKt.isAtEnd(xmlPullParser2) && (xmlPullParser2.getEventType() != 3 || !Intrinsics.areEqual(xmlPullParser2.getName(), "propertyValuesHolder"))) {
                                            if (xmlPullParser2.getEventType() == 2 && Intrinsics.areEqual(xmlPullParser2.getName(), "keyframe")) {
                                                ValueType valueType3 = XmlAnimatorParser_androidKt.FallbackValueType;
                                                AndroidVectorResources.INSTANCE.getClass();
                                                int[] iArr4 = AndroidVectorResources.STYLEABLE_KEYFRAME;
                                                if (theme3 == null || (typedArrayObtainAttributes4 = theme3.obtainStyledAttributes(attributeSet3, iArr4, 0, 0)) == null) {
                                                    typedArrayObtainAttributes4 = resources3.obtainAttributes(attributeSet3, iArr4);
                                                }
                                                if (valueType == null) {
                                                    try {
                                                        valueTypeInferValueType = XmlAnimatorParser_androidKt.inferValueType(typedArrayObtainAttributes4.getInt(2, 4), typedArrayObtainAttributes4.peekValue(0).type);
                                                        if (valueTypeInferValueType == null) {
                                                            valueTypeInferValueType = XmlAnimatorParser_androidKt.FallbackValueType;
                                                        }
                                                    } catch (Throwable th) {
                                                        typedArrayObtainAttributes4.recycle();
                                                        throw th;
                                                    }
                                                } else {
                                                    valueTypeInferValueType = valueType;
                                                }
                                                Pair pair = new Pair(XmlAnimatorParser_androidKt.getKeyframe(typedArrayObtainAttributes4, typedArrayObtainAttributes4.getFloat(3, 0.0f), XmlAnimatorParser_androidKt.getInterpolator(typedArrayObtainAttributes4, resources3, theme3, 1, easing3), valueTypeInferValueType, 0), valueTypeInferValueType);
                                                typedArrayObtainAttributes4.recycle();
                                                Keyframe keyframe = (Keyframe) pair.component1();
                                                ValueType valueType4 = (ValueType) pair.component2();
                                                if (valueType2 == null) {
                                                    valueType2 = valueType4;
                                                }
                                                list2.add(keyframe);
                                            }
                                            xmlPullParser2.next();
                                        }
                                        return valueType2 == null ? valueType == null ? XmlAnimatorParser_androidKt.FallbackValueType : valueType : valueType2;
                                    }
                                };
                                easing = easing2;
                                arrayList = arrayList2;
                                typedArray = typedArrayObtainAttributes3;
                                try {
                                    PropertyValuesHolder1D propertyValuesHolder1D = getPropertyValuesHolder1D(typedArray, string5, 2, 0, 1, easing, function2);
                                    typedArray.recycle();
                                    arrayList.add(propertyValuesHolder1D);
                                } catch (Throwable th) {
                                    th = th;
                                    typedArray.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                typedArray = typedArrayObtainAttributes3;
                            }
                        } else {
                            i4 = i7;
                            i5 = i8;
                            easing = interpolator;
                            arrayList = arrayList2;
                            i6 = i9;
                        }
                        xmlPullParser.next();
                        resources2 = resources;
                        theme2 = theme;
                        attributeSet2 = attributeSet;
                        arrayList2 = arrayList;
                        i7 = i4;
                        i8 = i5;
                        i9 = i6;
                        interpolator = easing;
                    }
                    i = i7;
                    i2 = i9;
                    i3 = i8;
                }
                ObjectAnimator objectAnimator = new ObjectAnimator(typedArray2.getInt(i, 300), typedArray2.getInt(i2, 0), typedArray2.getInt(i3, 0), typedArray2.getInt(4, 0) == i2 ? RepeatMode.Reverse : RepeatMode.Restart, arrayList2);
                typedArray2.recycle();
                typedArray2.recycle();
                return objectAnimator;
            } finally {
                typedArray2.recycle();
            }
        } catch (Throwable th3) {
            throw th3;
        }
    }
}
