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
import androidx.compose.animation.graphics.res.AnimatorResources_androidKt;
import androidx.compose.animation.graphics.res.AnimatorResources_androidKt$$ExternalSyntheticLambda0;
import androidx.compose.animation.graphics.vector.AnimatorSet;
import androidx.compose.animation.graphics.vector.Keyframe;
import androidx.compose.animation.graphics.vector.Ordering;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder1D;
import androidx.compose.animation.graphics.vector.PropertyValuesHolderColor;
import androidx.compose.animation.graphics.vector.PropertyValuesHolderFloat;
import androidx.compose.animation.graphics.vector.PropertyValuesHolderInt;
import androidx.compose.animation.graphics.vector.PropertyValuesHolderPath;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.core.graphics.PathParser;
import java.util.ArrayList;
import java.util.Comparator;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class XmlAnimatorParser_androidKt {
    public static final ValueType FallbackValueType = ValueType.Float;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public static final Easing getInterpolator(TypedArray typedArray, Resources resources, Resources.Theme theme, int i, Easing easing) {
        TypedArray typedArray2;
        Easing animatorResources_androidKt$$ExternalSyntheticLambda0;
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
        AttributeSet asAttributeSet = Xml.asAttributeSet(xml);
        String name = xml.getName();
        if (name != null) {
            switch (name.hashCode()) {
                case -2140409460:
                    if (name.equals("pathInterpolator")) {
                        AndroidVectorResources.INSTANCE.getClass();
                        int[] iArr = AndroidVectorResources.STYLEABLE_PATH_INTERPOLATOR;
                        if (theme == null || (r7 = theme.obtainStyledAttributes(asAttributeSet, iArr, 0, 0)) == null) {
                            TypedArray obtainAttributes = resources.obtainAttributes(asAttributeSet, iArr);
                        }
                        try {
                            String string = typedArray2.getString(4);
                            if (string != null) {
                                animatorResources_androidKt$$ExternalSyntheticLambda0 = new AnimatorResources_androidKt$$ExternalSyntheticLambda0(new PathInterpolator(PathParser.createPathFromPathData(string)));
                            } else {
                                if (typedArray2.hasValue(2) && typedArray2.hasValue(3)) {
                                    animatorResources_androidKt$$ExternalSyntheticLambda0 = new CubicBezierEasing(typedArray2.getFloat(0, 0.0f), typedArray2.getFloat(1, 0.0f), typedArray2.getFloat(2, 1.0f), typedArray2.getFloat(3, 1.0f));
                                }
                                animatorResources_androidKt$$ExternalSyntheticLambda0 = new AnimatorResources_androidKt$$ExternalSyntheticLambda0(new PathInterpolator(typedArray2.getFloat(0, 0.0f), typedArray2.getFloat(1, 0.0f)));
                            }
                            return animatorResources_androidKt$$ExternalSyntheticLambda0;
                        } finally {
                        }
                    }
                    break;
                case -2120889007:
                    if (name.equals("anticipateInterpolator")) {
                        AndroidVectorResources.INSTANCE.getClass();
                        int[] iArr2 = AndroidVectorResources.STYLEABLE_ANTICIPATEOVERSHOOT_INTERPOLATOR;
                        if (theme == null || (r7 = theme.obtainStyledAttributes(asAttributeSet, iArr2, 0, 0)) == null) {
                            TypedArray obtainAttributes2 = resources.obtainAttributes(asAttributeSet, iArr2);
                        }
                        try {
                            final float f = typedArray2.getFloat(0, 2.0f);
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
                        if (theme == null || (r7 = theme.obtainStyledAttributes(asAttributeSet, iArr3, 0, 0)) == null) {
                            TypedArray obtainAttributes3 = resources.obtainAttributes(asAttributeSet, iArr3);
                        }
                        try {
                            final float f2 = typedArray2.getFloat(0, 1.0f);
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
                        if (theme == null || (typedArray2 = theme.obtainStyledAttributes(asAttributeSet, iArr4, 0, 0)) == null) {
                            typedArray2 = resources.obtainAttributes(asAttributeSet, iArr4);
                        }
                        try {
                            final float f3 = typedArray2.getFloat(0, 2.0f);
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
                        if (theme == null || (r7 = theme.obtainStyledAttributes(asAttributeSet, iArr5, 0, 0)) == null) {
                            TypedArray obtainAttributes4 = resources.obtainAttributes(asAttributeSet, iArr5);
                        }
                        try {
                            return new AnimatorResources_androidKt$$ExternalSyntheticLambda0(new AnticipateOvershootInterpolator(typedArray2.getFloat(0, 2.0f), typedArray2.getFloat(1, 1.5f)));
                        } finally {
                        }
                    }
                    break;
                case 1962594083:
                    if (name.equals("decelerateInterpolator")) {
                        AndroidVectorResources.INSTANCE.getClass();
                        int[] iArr6 = AndroidVectorResources.STYLEABLE_DECELERATE_INTERPOLATOR;
                        if (theme == null || (r7 = theme.obtainStyledAttributes(asAttributeSet, iArr6, 0, 0)) == null) {
                            TypedArray obtainAttributes5 = resources.obtainAttributes(asAttributeSet, iArr6);
                        }
                        try {
                            final float f4 = typedArray2.getFloat(0, 1.0f);
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
                        if (theme == null || (r7 = theme.obtainStyledAttributes(asAttributeSet, iArr7, 0, 0)) == null) {
                            TypedArray obtainAttributes6 = resources.obtainAttributes(asAttributeSet, iArr7);
                        }
                        try {
                            final float f5 = typedArray2.getFloat(0, 1.0f);
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
            return new Keyframe(f, Color.m454boximpl(ColorKt.Color(typedArray.getColor(i, 0))), easing);
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
        TypedValue peekValue = typedArray.peekValue(i2);
        boolean z = peekValue != null;
        int i5 = peekValue != null ? peekValue.type : 4;
        TypedValue peekValue2 = typedArray.peekValue(i3);
        boolean z2 = peekValue2 != null;
        ValueType inferValueType = inferValueType(i4, i5, peekValue2 != null ? peekValue2.type : 4);
        ArrayList arrayList = new ArrayList();
        if (inferValueType == null && (z || z2)) {
            inferValueType = ValueType.Float;
        }
        if (z) {
            inferValueType.getClass();
            arrayList.add(getKeyframe(typedArray, 0.0f, easing, inferValueType, i2));
        }
        if (z2) {
            inferValueType.getClass();
            arrayList.add(getKeyframe(typedArray, 1.0f, easing, inferValueType, i3));
        }
        ValueType valueType = (ValueType) function2.invoke(inferValueType, arrayList);
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
        TypedArray obtainAttributes;
        AndroidVectorResources.INSTANCE.getClass();
        int[] iArr = AndroidVectorResources.STYLEABLE_ANIMATOR_SET;
        if (theme == null || (obtainAttributes = theme.obtainStyledAttributes(attributeSet, iArr, 0, 0)) == null) {
            obtainAttributes = resources.obtainAttributes(attributeSet, iArr);
        }
        try {
            int i = obtainAttributes.getInt(0, 0);
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
            obtainAttributes.recycle();
            return animatorSet;
        } catch (Throwable th) {
            obtainAttributes.recycle();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0047 A[Catch: all -> 0x0074, TryCatch #0 {all -> 0x0074, blocks: (B:14:0x0033, B:16:0x0047, B:19:0x0068, B:21:0x0118, B:23:0x012f, B:24:0x0134, B:29:0x0132, B:30:0x0066, B:31:0x0077, B:33:0x007e, B:34:0x008a, B:35:0x008f, B:37:0x0095, B:39:0x009b, B:43:0x00a9, B:45:0x00af, B:47:0x00bb, B:49:0x00c4, B:56:0x00f2, B:58:0x0107, B:62:0x00fd, B:63:0x0100, B:67:0x00ca), top: B:13:0x0033, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x012f A[Catch: all -> 0x0074, TryCatch #0 {all -> 0x0074, blocks: (B:14:0x0033, B:16:0x0047, B:19:0x0068, B:21:0x0118, B:23:0x012f, B:24:0x0134, B:29:0x0132, B:30:0x0066, B:31:0x0077, B:33:0x007e, B:34:0x008a, B:35:0x008f, B:37:0x0095, B:39:0x009b, B:43:0x00a9, B:45:0x00af, B:47:0x00bb, B:49:0x00c4, B:56:0x00f2, B:58:0x0107, B:62:0x00fd, B:63:0x0100, B:67:0x00ca), top: B:13:0x0033, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0132 A[Catch: all -> 0x0074, TryCatch #0 {all -> 0x0074, blocks: (B:14:0x0033, B:16:0x0047, B:19:0x0068, B:21:0x0118, B:23:0x012f, B:24:0x0134, B:29:0x0132, B:30:0x0066, B:31:0x0077, B:33:0x007e, B:34:0x008a, B:35:0x008f, B:37:0x0095, B:39:0x009b, B:43:0x00a9, B:45:0x00af, B:47:0x00bb, B:49:0x00c4, B:56:0x00f2, B:58:0x0107, B:62:0x00fd, B:63:0x0100, B:67:0x00ca), top: B:13:0x0033, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0077 A[Catch: all -> 0x0074, TryCatch #0 {all -> 0x0074, blocks: (B:14:0x0033, B:16:0x0047, B:19:0x0068, B:21:0x0118, B:23:0x012f, B:24:0x0134, B:29:0x0132, B:30:0x0066, B:31:0x0077, B:33:0x007e, B:34:0x008a, B:35:0x008f, B:37:0x0095, B:39:0x009b, B:43:0x00a9, B:45:0x00af, B:47:0x00bb, B:49:0x00c4, B:56:0x00f2, B:58:0x0107, B:62:0x00fd, B:63:0x0100, B:67:0x00ca), top: B:13:0x0033, outer: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.animation.graphics.vector.ObjectAnimator parseObjectAnimator(android.content.res.Resources r22, android.content.res.Resources.Theme r23, android.util.AttributeSet r24, final org.xmlpull.v1.XmlPullParser r25) {
        /*
            Method dump skipped, instructions count: 326
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.animation.graphics.vector.compat.XmlAnimatorParser_androidKt.parseObjectAnimator(android.content.res.Resources, android.content.res.Resources$Theme, android.util.AttributeSet, org.xmlpull.v1.XmlPullParser):androidx.compose.animation.graphics.vector.ObjectAnimator");
    }
}
