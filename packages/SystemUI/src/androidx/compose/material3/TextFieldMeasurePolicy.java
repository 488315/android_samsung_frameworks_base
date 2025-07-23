package androidx.compose.material3;

import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.material3.TextFieldLabelPosition;
import androidx.compose.material3.internal.FloatProducer;
import androidx.compose.material3.internal.LayoutUtilKt;
import androidx.compose.material3.tokens.MotionTokens;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.util.MathHelpersKt;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.comparisons.ComparisonsKt___ComparisonsJvmKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TextFieldMeasurePolicy implements MeasurePolicy {
    public final TextFieldLabelPosition labelPosition;
    public final FloatProducer labelProgress;
    public final float minimizedLabelHalfHeight;
    public final PaddingValues paddingValues;
    public final boolean singleLine;

    public /* synthetic */ TextFieldMeasurePolicy(boolean z, TextFieldLabelPosition textFieldLabelPosition, FloatProducer floatProducer, PaddingValues paddingValues, float f, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, textFieldLabelPosition, floatProducer, paddingValues, f);
    }

    public static int intrinsicWidth(List list, int i, Function2 function2) {
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        Object obj5;
        Object obj6;
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            Object obj7 = list.get(i2);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj7), "TextField")) {
                int intValue = ((Number) function2.invoke(obj7, Integer.valueOf(i))).intValue();
                int size2 = list.size();
                int i3 = 0;
                while (true) {
                    obj = null;
                    if (i3 >= size2) {
                        obj2 = null;
                        break;
                    }
                    obj2 = list.get(i3);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj2), "Label")) {
                        break;
                    }
                    i3++;
                }
                IntrinsicMeasurable intrinsicMeasurable = (IntrinsicMeasurable) obj2;
                int intValue2 = intrinsicMeasurable != null ? ((Number) function2.invoke(intrinsicMeasurable, Integer.valueOf(i))).intValue() : 0;
                int size3 = list.size();
                int i4 = 0;
                while (true) {
                    if (i4 >= size3) {
                        obj3 = null;
                        break;
                    }
                    obj3 = list.get(i4);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj3), "Trailing")) {
                        break;
                    }
                    i4++;
                }
                IntrinsicMeasurable intrinsicMeasurable2 = (IntrinsicMeasurable) obj3;
                int intValue3 = intrinsicMeasurable2 != null ? ((Number) function2.invoke(intrinsicMeasurable2, Integer.valueOf(i))).intValue() : 0;
                int size4 = list.size();
                int i5 = 0;
                while (true) {
                    if (i5 >= size4) {
                        obj4 = null;
                        break;
                    }
                    obj4 = list.get(i5);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj4), "Prefix")) {
                        break;
                    }
                    i5++;
                }
                IntrinsicMeasurable intrinsicMeasurable3 = (IntrinsicMeasurable) obj4;
                int intValue4 = intrinsicMeasurable3 != null ? ((Number) function2.invoke(intrinsicMeasurable3, Integer.valueOf(i))).intValue() : 0;
                int size5 = list.size();
                int i6 = 0;
                while (true) {
                    if (i6 >= size5) {
                        obj5 = null;
                        break;
                    }
                    obj5 = list.get(i6);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj5), "Suffix")) {
                        break;
                    }
                    i6++;
                }
                IntrinsicMeasurable intrinsicMeasurable4 = (IntrinsicMeasurable) obj5;
                int intValue5 = intrinsicMeasurable4 != null ? ((Number) function2.invoke(intrinsicMeasurable4, Integer.valueOf(i))).intValue() : 0;
                int size6 = list.size();
                int i7 = 0;
                while (true) {
                    if (i7 >= size6) {
                        obj6 = null;
                        break;
                    }
                    obj6 = list.get(i7);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj6), "Leading")) {
                        break;
                    }
                    i7++;
                }
                IntrinsicMeasurable intrinsicMeasurable5 = (IntrinsicMeasurable) obj6;
                int intValue6 = intrinsicMeasurable5 != null ? ((Number) function2.invoke(intrinsicMeasurable5, Integer.valueOf(i))).intValue() : 0;
                int size7 = list.size();
                int i8 = 0;
                while (true) {
                    if (i8 >= size7) {
                        break;
                    }
                    Object obj8 = list.get(i8);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj8), "Hint")) {
                        obj = obj8;
                        break;
                    }
                    i8++;
                }
                IntrinsicMeasurable intrinsicMeasurable6 = (IntrinsicMeasurable) obj;
                int i9 = intValue4 + intValue5;
                return ConstraintsKt.m832constrainWidthK40F9xA(Math.max(intValue + i9, Math.max((intrinsicMeasurable6 != null ? ((Number) function2.invoke(intrinsicMeasurable6, Integer.valueOf(i))).intValue() : 0) + i9, intValue2)) + intValue6 + intValue3, ConstraintsKt.Constraints$default(0, 0, 0, 0, 15));
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public static final int placeWithoutLabel$calculateVerticalPosition(TextFieldMeasurePolicy textFieldMeasurePolicy, int i, int i2, Placeable placeable) {
        if (!textFieldMeasurePolicy.singleLine) {
            return i2;
        }
        Alignment.Companion.getClass();
        return Alignment.Companion.CenterVertically.align(placeable.height, i);
    }

    /* renamed from: calculateHeight-mKXJcVc$1, reason: not valid java name */
    public final int m315calculateHeightmKXJcVc$1(IntrinsicMeasureScope intrinsicMeasureScope, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j, boolean z, float f) {
        int i9;
        PaddingValues paddingValues = this.paddingValues;
        float mo109calculateBottomPaddingD9Ej5fM = paddingValues.mo109calculateBottomPaddingD9Ej5fM() + paddingValues.mo112calculateTopPaddingD9Ej5fM();
        Dp.Companion companion = Dp.Companion;
        int mo51roundToPx0680j_4 = intrinsicMeasureScope.mo51roundToPx0680j_4(mo109calculateBottomPaddingD9Ej5fM);
        int maxOf = ComparisonsKt___ComparisonsJvmKt.maxOf(i, i7, i5, i6, z ? 0 : MathHelpersKt.lerp(f, i2, 0));
        if (i2 <= 0 || z) {
            i9 = 0;
        } else {
            int mo51roundToPx0680j_42 = intrinsicMeasureScope.mo51roundToPx0680j_4(this.minimizedLabelHalfHeight * 2);
            MotionTokens.INSTANCE.getClass();
            i9 = Math.max(mo51roundToPx0680j_42, MathHelpersKt.lerp(MotionTokens.EasingEmphasizedAccelerateCubicBezier.transform(f), 0, i2));
        }
        int i10 = mo51roundToPx0680j_4 + i9 + maxOf;
        if (!z) {
            i2 = 0;
        }
        return ConstraintsKt.m831constrainHeightK40F9xA(Math.max(i3, Math.max(i4, i10)) + i2 + i8, j);
    }

    public final int intrinsicHeight$1(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i, Function2 function2) {
        Object obj;
        int i2;
        int i3;
        int i4;
        Object obj2;
        int i5;
        Object obj3;
        Object obj4;
        int i6;
        Object obj5;
        int i7;
        Object obj6;
        Object obj7;
        TextFieldMeasurePolicy textFieldMeasurePolicy = this;
        int size = list.size();
        int i8 = 0;
        while (true) {
            if (i8 >= size) {
                obj = null;
                break;
            }
            obj = list.get(i8);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj), "Leading")) {
                break;
            }
            i8++;
        }
        IntrinsicMeasurable intrinsicMeasurable = (IntrinsicMeasurable) obj;
        if (intrinsicMeasurable != null) {
            i2 = i;
            i3 = LayoutUtilKt.subtractConstraintSafely(i2, intrinsicMeasurable.maxIntrinsicWidth(Integer.MAX_VALUE));
            i4 = ((Number) function2.invoke(intrinsicMeasurable, Integer.valueOf(i2))).intValue();
        } else {
            i2 = i;
            i3 = i2;
            i4 = 0;
        }
        int size2 = list.size();
        int i9 = 0;
        while (true) {
            if (i9 >= size2) {
                obj2 = null;
                break;
            }
            obj2 = list.get(i9);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj2), "Trailing")) {
                break;
            }
            i9++;
        }
        IntrinsicMeasurable intrinsicMeasurable2 = (IntrinsicMeasurable) obj2;
        if (intrinsicMeasurable2 != null) {
            i3 = LayoutUtilKt.subtractConstraintSafely(i3, intrinsicMeasurable2.maxIntrinsicWidth(Integer.MAX_VALUE));
            i5 = ((Number) function2.invoke(intrinsicMeasurable2, Integer.valueOf(i2))).intValue();
        } else {
            i5 = 0;
        }
        int size3 = list.size();
        int i10 = 0;
        while (true) {
            if (i10 >= size3) {
                obj3 = null;
                break;
            }
            obj3 = list.get(i10);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj3), "Label")) {
                break;
            }
            i10++;
        }
        Object obj8 = (IntrinsicMeasurable) obj3;
        int intValue = obj8 != null ? ((Number) function2.invoke(obj8, Integer.valueOf(i3))).intValue() : 0;
        int size4 = list.size();
        int i11 = 0;
        while (true) {
            if (i11 >= size4) {
                obj4 = null;
                break;
            }
            obj4 = list.get(i11);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj4), "Prefix")) {
                break;
            }
            i11++;
        }
        IntrinsicMeasurable intrinsicMeasurable3 = (IntrinsicMeasurable) obj4;
        if (intrinsicMeasurable3 != null) {
            i6 = ((Number) function2.invoke(intrinsicMeasurable3, Integer.valueOf(i3))).intValue();
            i3 = LayoutUtilKt.subtractConstraintSafely(i3, intrinsicMeasurable3.maxIntrinsicWidth(Integer.MAX_VALUE));
        } else {
            i6 = 0;
        }
        int size5 = list.size();
        int i12 = 0;
        while (true) {
            if (i12 >= size5) {
                obj5 = null;
                break;
            }
            obj5 = list.get(i12);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj5), "Suffix")) {
                break;
            }
            i12++;
        }
        IntrinsicMeasurable intrinsicMeasurable4 = (IntrinsicMeasurable) obj5;
        if (intrinsicMeasurable4 != null) {
            i7 = ((Number) function2.invoke(intrinsicMeasurable4, Integer.valueOf(i3))).intValue();
            i3 = LayoutUtilKt.subtractConstraintSafely(i3, intrinsicMeasurable4.maxIntrinsicWidth(Integer.MAX_VALUE));
        } else {
            i7 = 0;
        }
        int size6 = list.size();
        int i13 = 0;
        while (i13 < size6) {
            Object obj9 = list.get(i13);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj9), "TextField")) {
                int intValue2 = ((Number) function2.invoke(obj9, Integer.valueOf(i3))).intValue();
                int size7 = list.size();
                int i14 = 0;
                while (true) {
                    if (i14 >= size7) {
                        obj6 = null;
                        break;
                    }
                    obj6 = list.get(i14);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj6), "Hint")) {
                        break;
                    }
                    i14++;
                }
                Object obj10 = (IntrinsicMeasurable) obj6;
                int intValue3 = obj10 != null ? ((Number) function2.invoke(obj10, Integer.valueOf(i3))).intValue() : 0;
                int size8 = list.size();
                int i15 = 0;
                while (true) {
                    if (i15 >= size8) {
                        obj7 = null;
                        break;
                    }
                    obj7 = list.get(i15);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj7), "Supporting")) {
                        break;
                    }
                    i15++;
                }
                Object obj11 = (IntrinsicMeasurable) obj7;
                int intValue4 = obj11 != null ? ((Number) function2.invoke(obj11, Integer.valueOf(i2))).intValue() : 0;
                int i16 = intValue3;
                return textFieldMeasurePolicy.m315calculateHeightmKXJcVc$1(intrinsicMeasureScope, intValue2, intValue, i4, i5, i6, i7, i16, intValue4, ConstraintsKt.Constraints$default(0, 0, 0, 0, 15), textFieldMeasurePolicy.labelPosition instanceof TextFieldLabelPosition.Above, textFieldMeasurePolicy.labelProgress.invoke());
            }
            i13++;
            textFieldMeasurePolicy = this;
            i7 = i7;
            i4 = i4;
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicHeight$1(intrinsicMeasureScope, list, i, new Function2() { // from class: androidx.compose.material3.TextFieldMeasurePolicy$maxIntrinsicHeight$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((IntrinsicMeasurable) obj).maxIntrinsicHeight(((Number) obj2).intValue()));
            }
        });
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicWidth(list, i, new Function2() { // from class: androidx.compose.material3.TextFieldMeasurePolicy$maxIntrinsicWidth$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((IntrinsicMeasurable) obj).maxIntrinsicWidth(((Number) obj2).intValue()));
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:193:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x01c0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0209  */
    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.compose.ui.layout.MeasureResult mo3measure3p2s80s(final androidx.compose.ui.layout.MeasureScope r35, java.util.List r36, long r37) {
        /*
            Method dump skipped, instructions count: 1084
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.TextFieldMeasurePolicy.mo3measure3p2s80s(androidx.compose.ui.layout.MeasureScope, java.util.List, long):androidx.compose.ui.layout.MeasureResult");
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicHeight$1(intrinsicMeasureScope, list, i, new Function2() { // from class: androidx.compose.material3.TextFieldMeasurePolicy$minIntrinsicHeight$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((IntrinsicMeasurable) obj).minIntrinsicHeight(((Number) obj2).intValue()));
            }
        });
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicWidth(list, i, new Function2() { // from class: androidx.compose.material3.TextFieldMeasurePolicy$minIntrinsicWidth$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((IntrinsicMeasurable) obj).minIntrinsicWidth(((Number) obj2).intValue()));
            }
        });
    }

    private TextFieldMeasurePolicy(boolean z, TextFieldLabelPosition textFieldLabelPosition, FloatProducer floatProducer, PaddingValues paddingValues, float f) {
        this.singleLine = z;
        this.labelPosition = textFieldLabelPosition;
        this.labelProgress = floatProducer;
        this.paddingValues = paddingValues;
        this.minimizedLabelHalfHeight = f;
    }
}
