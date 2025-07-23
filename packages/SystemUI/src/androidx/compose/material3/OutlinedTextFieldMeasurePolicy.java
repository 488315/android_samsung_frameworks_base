package androidx.compose.material3;

import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.material3.TextFieldLabelPosition;
import androidx.compose.material3.internal.FloatProducer;
import androidx.compose.material3.internal.LayoutUtilKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.util.MathHelpersKt;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.comparisons.ComparisonsKt___ComparisonsJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class OutlinedTextFieldMeasurePolicy implements MeasurePolicy {
    public final float horizontalIconPadding;
    public final TextFieldLabelPosition labelPosition;
    public final FloatProducer labelProgress;
    public final Function1 onLabelMeasured;
    public final PaddingValues paddingValues;
    public final boolean singleLine;

    public /* synthetic */ OutlinedTextFieldMeasurePolicy(Function1 function1, boolean z, TextFieldLabelPosition textFieldLabelPosition, FloatProducer floatProducer, PaddingValues paddingValues, float f, DefaultConstructorMarker defaultConstructorMarker) {
        this(function1, z, textFieldLabelPosition, floatProducer, paddingValues, f);
    }

    public static final int place$calculateVerticalPosition(int i, OutlinedTextFieldMeasurePolicy outlinedTextFieldMeasurePolicy, int i2, int i3, Placeable placeable, Placeable placeable2) {
        if (outlinedTextFieldMeasurePolicy.singleLine) {
            Alignment.Companion.getClass();
            i3 = Alignment.Companion.CenterVertically.align(placeable2.height, i2);
        }
        int i4 = i + i3;
        if (outlinedTextFieldMeasurePolicy.labelPosition instanceof TextFieldLabelPosition.Above) {
            return i4;
        }
        return Math.max(i4, (placeable != null ? placeable.height : 0) / 2);
    }

    /* renamed from: calculateHeight-mKXJcVc, reason: not valid java name */
    public final int m276calculateHeightmKXJcVc(IntrinsicMeasureScope intrinsicMeasureScope, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j, boolean z, float f) {
        int maxOf = ComparisonsKt___ComparisonsJvmKt.maxOf(i5, i7, i3, i4, z ? 0 : MathHelpersKt.lerp(f, i6, 0));
        PaddingValues paddingValues = this.paddingValues;
        float mo57toPx0680j_4 = intrinsicMeasureScope.mo57toPx0680j_4(paddingValues.mo112calculateTopPaddingD9Ej5fM());
        if (!z) {
            mo57toPx0680j_4 = MathHelpersKt.lerp(mo57toPx0680j_4, Math.max(mo57toPx0680j_4, i6 / 2.0f), f);
        }
        float mo57toPx0680j_42 = mo57toPx0680j_4 + maxOf + intrinsicMeasureScope.mo57toPx0680j_4(paddingValues.mo109calculateBottomPaddingD9Ej5fM());
        if (!z) {
            i6 = 0;
        }
        return ConstraintsKt.m831constrainHeightK40F9xA(Math.max(i, Math.max(i2, MathKt__MathJVMKt.roundToInt(mo57toPx0680j_42))) + i6 + i8, j);
    }

    /* renamed from: calculateWidth-IzADHW4, reason: not valid java name */
    public final int m277calculateWidthIzADHW4(IntrinsicMeasureScope intrinsicMeasureScope, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, float f) {
        int i8 = i3 + i4;
        int max = Math.max(i5 + i8, Math.max(i7 + i8, MathHelpersKt.lerp(f, i6, 0))) + i + i2;
        LayoutDirection layoutDirection = LayoutDirection.Ltr;
        PaddingValues paddingValues = this.paddingValues;
        float mo111calculateRightPaddingu2uoSUM = paddingValues.mo111calculateRightPaddingu2uoSUM(layoutDirection) + paddingValues.mo110calculateLeftPaddingu2uoSUM(layoutDirection);
        Dp.Companion companion = Dp.Companion;
        return ConstraintsKt.m832constrainWidthK40F9xA(Math.max(max, MathKt__MathJVMKt.roundToInt((i6 + intrinsicMeasureScope.mo57toPx0680j_4(mo111calculateRightPaddingu2uoSUM)) * f)), j);
    }

    public final int intrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i, Function2 function2) {
        Object obj;
        int i2;
        int i3;
        Object obj2;
        int i4;
        Object obj3;
        Object obj4;
        int i5;
        Object obj5;
        int i6;
        Object obj6;
        Object obj7;
        OutlinedTextFieldMeasurePolicy outlinedTextFieldMeasurePolicy = this;
        float invoke = outlinedTextFieldMeasurePolicy.labelProgress.invoke();
        int size = list.size();
        int i7 = 0;
        while (true) {
            if (i7 >= size) {
                obj = null;
                break;
            }
            obj = list.get(i7);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj), "Leading")) {
                break;
            }
            i7++;
        }
        IntrinsicMeasurable intrinsicMeasurable = (IntrinsicMeasurable) obj;
        if (intrinsicMeasurable != null) {
            i2 = LayoutUtilKt.subtractConstraintSafely(i, intrinsicMeasurable.maxIntrinsicWidth(Integer.MAX_VALUE));
            i3 = ((Number) function2.invoke(intrinsicMeasurable, Integer.valueOf(i))).intValue();
        } else {
            i2 = i;
            i3 = 0;
        }
        int size2 = list.size();
        int i8 = 0;
        while (true) {
            if (i8 >= size2) {
                obj2 = null;
                break;
            }
            obj2 = list.get(i8);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj2), "Trailing")) {
                break;
            }
            i8++;
        }
        IntrinsicMeasurable intrinsicMeasurable2 = (IntrinsicMeasurable) obj2;
        if (intrinsicMeasurable2 != null) {
            i2 = LayoutUtilKt.subtractConstraintSafely(i2, intrinsicMeasurable2.maxIntrinsicWidth(Integer.MAX_VALUE));
            i4 = ((Number) function2.invoke(intrinsicMeasurable2, Integer.valueOf(i))).intValue();
        } else {
            i4 = 0;
        }
        int size3 = list.size();
        int i9 = 0;
        while (true) {
            if (i9 >= size3) {
                obj3 = null;
                break;
            }
            obj3 = list.get(i9);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj3), "Label")) {
                break;
            }
            i9++;
        }
        Object obj8 = (IntrinsicMeasurable) obj3;
        int intValue = obj8 != null ? ((Number) function2.invoke(obj8, Integer.valueOf(MathHelpersKt.lerp(invoke, i2, i)))).intValue() : 0;
        int size4 = list.size();
        int i10 = 0;
        while (true) {
            if (i10 >= size4) {
                obj4 = null;
                break;
            }
            obj4 = list.get(i10);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj4), "Prefix")) {
                break;
            }
            i10++;
        }
        IntrinsicMeasurable intrinsicMeasurable3 = (IntrinsicMeasurable) obj4;
        if (intrinsicMeasurable3 != null) {
            i5 = ((Number) function2.invoke(intrinsicMeasurable3, Integer.valueOf(i2))).intValue();
            i2 = LayoutUtilKt.subtractConstraintSafely(i2, intrinsicMeasurable3.maxIntrinsicWidth(Integer.MAX_VALUE));
        } else {
            i5 = 0;
        }
        int size5 = list.size();
        int i11 = 0;
        while (true) {
            if (i11 >= size5) {
                obj5 = null;
                break;
            }
            obj5 = list.get(i11);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj5), "Suffix")) {
                break;
            }
            i11++;
        }
        IntrinsicMeasurable intrinsicMeasurable4 = (IntrinsicMeasurable) obj5;
        if (intrinsicMeasurable4 != null) {
            i6 = ((Number) function2.invoke(intrinsicMeasurable4, Integer.valueOf(i2))).intValue();
            i2 = LayoutUtilKt.subtractConstraintSafely(i2, intrinsicMeasurable4.maxIntrinsicWidth(Integer.MAX_VALUE));
        } else {
            i6 = 0;
        }
        int size6 = list.size();
        int i12 = 0;
        while (i12 < size6) {
            Object obj9 = list.get(i12);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj9), "TextField")) {
                int intValue2 = ((Number) function2.invoke(obj9, Integer.valueOf(i2))).intValue();
                int size7 = list.size();
                int i13 = 0;
                while (true) {
                    if (i13 >= size7) {
                        obj6 = null;
                        break;
                    }
                    obj6 = list.get(i13);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj6), "Hint")) {
                        break;
                    }
                    i13++;
                }
                Object obj10 = (IntrinsicMeasurable) obj6;
                int intValue3 = obj10 != null ? ((Number) function2.invoke(obj10, Integer.valueOf(i2))).intValue() : 0;
                int size8 = list.size();
                int i14 = 0;
                while (true) {
                    if (i14 >= size8) {
                        obj7 = null;
                        break;
                    }
                    obj7 = list.get(i14);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj7), "Supporting")) {
                        break;
                    }
                    i14++;
                }
                Object obj11 = (IntrinsicMeasurable) obj7;
                return outlinedTextFieldMeasurePolicy.m276calculateHeightmKXJcVc(intrinsicMeasureScope, i3, i4, i5, i6, intValue2, intValue, intValue3, obj11 != null ? ((Number) function2.invoke(obj11, Integer.valueOf(i))).intValue() : 0, ConstraintsKt.Constraints$default(0, 0, 0, 0, 15), outlinedTextFieldMeasurePolicy.labelPosition instanceof TextFieldLabelPosition.Above, invoke);
            }
            i12++;
            i6 = i6;
            i5 = i5;
            outlinedTextFieldMeasurePolicy = this;
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public final int intrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i, Function2 function2) {
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
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj4), "Leading")) {
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
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj5), "Prefix")) {
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
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj6), "Suffix")) {
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
                return m277calculateWidthIzADHW4(intrinsicMeasureScope, intValue4, intValue3, intValue5, intValue6, intValue, intValue2, intrinsicMeasurable6 != null ? ((Number) function2.invoke(intrinsicMeasurable6, Integer.valueOf(i))).intValue() : 0, ConstraintsKt.Constraints$default(0, 0, 0, 0, 15), this.labelProgress.invoke());
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicHeight(intrinsicMeasureScope, list, i, new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldMeasurePolicy$maxIntrinsicHeight$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((IntrinsicMeasurable) obj).maxIntrinsicHeight(((Number) obj2).intValue()));
            }
        });
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicWidth(intrinsicMeasureScope, list, i, new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldMeasurePolicy$maxIntrinsicWidth$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((IntrinsicMeasurable) obj).maxIntrinsicWidth(((Number) obj2).intValue()));
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v38 */
    /* JADX WARN: Type inference failed for: r0v39, types: [T, androidx.compose.ui.layout.Placeable] */
    /* JADX WARN: Type inference failed for: r0v45 */
    /* JADX WARN: Type inference failed for: r2v33 */
    /* JADX WARN: Type inference failed for: r2v34, types: [T, androidx.compose.ui.layout.Placeable] */
    /* JADX WARN: Type inference failed for: r2v52 */
    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(final MeasureScope measureScope, List list, long j) {
        Object obj;
        Object obj2;
        Placeable placeable;
        int i;
        Placeable placeable2;
        Object obj3;
        Placeable placeable3;
        int i2;
        Placeable placeable4;
        Object obj4;
        Placeable placeable5;
        int i3;
        int i4;
        Placeable placeable6;
        boolean z;
        Object obj5;
        float f;
        int minIntrinsicHeight;
        PaddingValues paddingValues;
        Object obj6;
        int max;
        Object obj7;
        Placeable placeable7;
        int i5;
        Measurable measurable;
        int i6;
        String str;
        Placeable placeable8;
        Measurable measurable2;
        Ref$ObjectRef ref$ObjectRef;
        int i7;
        Placeable placeable9;
        Placeable placeable10;
        Function1 function1;
        Measurable measurable3;
        int i8;
        int i9;
        Placeable placeable11;
        Placeable placeable12;
        float f2;
        boolean z2;
        int i10;
        OutlinedTextFieldMeasurePolicy outlinedTextFieldMeasurePolicy;
        long j2;
        Placeable placeable13;
        int i11;
        long j3;
        int i12;
        Placeable placeable14;
        Placeable placeable15;
        int i13;
        int i14;
        Placeable placeable16;
        int i15;
        int i16;
        Placeable placeable17;
        Placeable placeable18;
        float f3;
        Placeable placeable19;
        String str2;
        OutlinedTextFieldMeasurePolicy outlinedTextFieldMeasurePolicy2;
        MeasureScope measureScope2;
        long j4;
        Ref$ObjectRef ref$ObjectRef2;
        boolean z3;
        MeasureResult layout$1;
        Placeable placeable20;
        long j5;
        List list2 = list;
        float invoke = this.labelProgress.invoke();
        PaddingValues paddingValues2 = this.paddingValues;
        int mo51roundToPx0680j_4 = measureScope.mo51roundToPx0680j_4(paddingValues2.mo109calculateBottomPaddingD9Ej5fM());
        long m814copyZbe2FdA$default = Constraints.m814copyZbe2FdA$default(j, 0, 0, 0, 0, 10);
        int size = list2.size();
        int i17 = 0;
        while (true) {
            if (i17 >= size) {
                obj = null;
                break;
            }
            obj = list2.get(i17);
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj), "Leading")) {
                break;
            }
            i17++;
        }
        Measurable measurable4 = (Measurable) obj;
        Placeable mo608measureBRTryo0 = measurable4 != null ? measurable4.mo608measureBRTryo0(m814copyZbe2FdA$default) : null;
        int i18 = mo608measureBRTryo0 != null ? mo608measureBRTryo0.width : 0;
        int max2 = Math.max(0, mo608measureBRTryo0 != null ? mo608measureBRTryo0.height : 0);
        int size2 = list2.size();
        int i19 = 0;
        while (true) {
            if (i19 >= size2) {
                obj2 = null;
                break;
            }
            obj2 = list2.get(i19);
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj2), "Trailing")) {
                break;
            }
            i19++;
        }
        Measurable measurable5 = (Measurable) obj2;
        if (measurable5 != null) {
            placeable = mo608measureBRTryo0;
            i = i18;
            placeable2 = measurable5.mo608measureBRTryo0(ConstraintsKt.m834offsetNN6EwU$default(-i18, 0, 2, m814copyZbe2FdA$default));
        } else {
            placeable = mo608measureBRTryo0;
            i = i18;
            placeable2 = null;
        }
        int i20 = i + (placeable2 != null ? placeable2.width : 0);
        int max3 = Math.max(max2, placeable2 != null ? placeable2.height : 0);
        int size3 = list2.size();
        int i21 = 0;
        while (true) {
            if (i21 >= size3) {
                obj3 = null;
                break;
            }
            obj3 = list2.get(i21);
            int i22 = size3;
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj3), "Prefix")) {
                break;
            }
            i21++;
            size3 = i22;
        }
        Measurable measurable6 = (Measurable) obj3;
        if (measurable6 != null) {
            placeable3 = placeable2;
            i2 = i20;
            placeable4 = measurable6.mo608measureBRTryo0(ConstraintsKt.m834offsetNN6EwU$default(-i20, 0, 2, m814copyZbe2FdA$default));
        } else {
            placeable3 = placeable2;
            i2 = i20;
            placeable4 = null;
        }
        int i23 = i2 + (placeable4 != null ? placeable4.width : 0);
        int max4 = Math.max(max3, placeable4 != null ? placeable4.height : 0);
        int size4 = list2.size();
        int i24 = 0;
        while (true) {
            if (i24 >= size4) {
                obj4 = null;
                break;
            }
            obj4 = list2.get(i24);
            int i25 = size4;
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj4), "Suffix")) {
                break;
            }
            i24++;
            size4 = i25;
        }
        Measurable measurable7 = (Measurable) obj4;
        if (measurable7 != null) {
            placeable5 = placeable4;
            i3 = i23;
            i4 = 0;
            placeable6 = measurable7.mo608measureBRTryo0(ConstraintsKt.m834offsetNN6EwU$default(-i23, 0, 2, m814copyZbe2FdA$default));
        } else {
            placeable5 = placeable4;
            i3 = i23;
            i4 = 0;
            placeable6 = null;
        }
        int i26 = i3 + (placeable6 != null ? placeable6.width : i4);
        int max5 = Math.max(max4, placeable6 != null ? placeable6.height : i4);
        boolean z4 = this.labelPosition instanceof TextFieldLabelPosition.Above;
        int size5 = list2.size();
        int i27 = i4;
        while (true) {
            if (i27 >= size5) {
                z = z4;
                obj5 = null;
                break;
            }
            obj5 = list2.get(i27);
            z = z4;
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj5), "Label")) {
                break;
            }
            i27++;
            z4 = z;
        }
        Measurable measurable8 = (Measurable) obj5;
        Ref$ObjectRef ref$ObjectRef3 = new Ref$ObjectRef();
        Function1 function12 = this.onLabelMeasured;
        long j6 = 0;
        if (z) {
            f = invoke;
            minIntrinsicHeight = measurable8 != null ? measurable8.minIntrinsicHeight(Constraints.m823getMinWidthimpl(j)) : 0;
        } else {
            int mo51roundToPx0680j_42 = measureScope.mo51roundToPx0680j_4(paddingValues2.mo111calculateRightPaddingu2uoSUM(measureScope.getLayoutDirection())) + measureScope.mo51roundToPx0680j_4(paddingValues2.mo110calculateLeftPaddingu2uoSUM(measureScope.getLayoutDirection()));
            f = invoke;
            ?? mo608measureBRTryo02 = measurable8 != null ? measurable8.mo608measureBRTryo0(ConstraintsKt.m833offsetNN6EwU(-MathHelpersKt.lerp(invoke, i26 + mo51roundToPx0680j_42, mo51roundToPx0680j_42), -mo51roundToPx0680j_4, m814copyZbe2FdA$default)) : 0;
            ref$ObjectRef3.element = mo608measureBRTryo02;
            if (mo608measureBRTryo02 != 0) {
                j5 = SizeKt.Size(mo608measureBRTryo02.width, mo608measureBRTryo02.height);
            } else {
                Size.Companion.getClass();
                j5 = 0;
            }
            function12.mo779invoke(Size.m413boximpl(j5));
            minIntrinsicHeight = 0;
        }
        int size6 = list2.size();
        int i28 = 0;
        while (true) {
            if (i28 >= size6) {
                paddingValues = paddingValues2;
                obj6 = null;
                break;
            }
            obj6 = list2.get(i28);
            int i29 = size6;
            paddingValues = paddingValues2;
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj6), "Supporting")) {
                break;
            }
            i28++;
            paddingValues2 = paddingValues;
            size6 = i29;
        }
        Measurable measurable9 = (Measurable) obj6;
        int minIntrinsicHeight2 = measurable9 != null ? measurable9.minIntrinsicHeight(Constraints.m823getMinWidthimpl(j)) : 0;
        if (z) {
            max = measureScope.mo51roundToPx0680j_4(paddingValues.mo112calculateTopPaddingD9Ej5fM());
        } else {
            Placeable placeable21 = (Placeable) ref$ObjectRef3.element;
            max = Math.max((placeable21 != null ? placeable21.height : 0) / 2, measureScope.mo51roundToPx0680j_4(paddingValues.mo112calculateTopPaddingD9Ej5fM()));
        }
        long m814copyZbe2FdA$default2 = Constraints.m814copyZbe2FdA$default(ConstraintsKt.m833offsetNN6EwU(-i26, (((-mo51roundToPx0680j_4) - max) - minIntrinsicHeight) - minIntrinsicHeight2, j), 0, 0, 0, 0, 11);
        int size7 = list2.size();
        int i30 = 0;
        while (i30 < size7) {
            Measurable measurable10 = (Measurable) list2.get(i30);
            int i31 = max;
            int i32 = mo51roundToPx0680j_4;
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable10), "TextField")) {
                Placeable mo608measureBRTryo03 = measurable10.mo608measureBRTryo0(m814copyZbe2FdA$default2);
                long m814copyZbe2FdA$default3 = Constraints.m814copyZbe2FdA$default(m814copyZbe2FdA$default2, 0, 0, 0, 0, 14);
                int size8 = list2.size();
                int i33 = 0;
                while (true) {
                    if (i33 >= size8) {
                        obj7 = null;
                        break;
                    }
                    obj7 = list2.get(i33);
                    int i34 = size8;
                    if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj7), "Hint")) {
                        break;
                    }
                    i33++;
                    size8 = i34;
                }
                Measurable measurable11 = (Measurable) obj7;
                Placeable mo608measureBRTryo04 = measurable11 != null ? measurable11.mo608measureBRTryo0(m814copyZbe2FdA$default3) : null;
                int max6 = Math.max(max5, Math.max(mo608measureBRTryo03.height, mo608measureBRTryo04 != null ? mo608measureBRTryo04.height : 0) + i31 + i32);
                int i35 = placeable != null ? placeable.width : 0;
                Placeable placeable22 = placeable3;
                int i36 = placeable3 != null ? placeable22.width : 0;
                Placeable placeable23 = placeable5;
                int i37 = placeable5 != null ? placeable23.width : 0;
                if (placeable6 != null) {
                    placeable7 = placeable22;
                    i5 = placeable6.width;
                } else {
                    placeable7 = placeable22;
                    i5 = 0;
                }
                int i38 = mo608measureBRTryo03.width;
                Placeable placeable24 = (Placeable) ref$ObjectRef3.element;
                if (placeable24 != null) {
                    Measurable measurable12 = measurable8;
                    i6 = placeable24.width;
                    measurable = measurable12;
                } else {
                    measurable = measurable8;
                    i6 = 0;
                }
                if (mo608measureBRTryo04 != null) {
                    str = "Collection contains no element matching the predicate.";
                    placeable8 = mo608measureBRTryo03;
                    measurable2 = measurable;
                    ref$ObjectRef = ref$ObjectRef3;
                    i7 = mo608measureBRTryo04.width;
                    placeable9 = mo608measureBRTryo04;
                    placeable10 = placeable6;
                    function1 = function12;
                    measurable3 = measurable9;
                    i8 = i37;
                    i9 = max6;
                    placeable11 = placeable7;
                    placeable12 = placeable23;
                    f2 = f;
                    i10 = 0;
                    outlinedTextFieldMeasurePolicy = this;
                    j2 = j;
                    z2 = z;
                } else {
                    str = "Collection contains no element matching the predicate.";
                    placeable8 = mo608measureBRTryo03;
                    measurable2 = measurable;
                    ref$ObjectRef = ref$ObjectRef3;
                    i7 = 0;
                    placeable9 = mo608measureBRTryo04;
                    placeable10 = placeable6;
                    function1 = function12;
                    measurable3 = measurable9;
                    i8 = i37;
                    i9 = max6;
                    placeable11 = placeable7;
                    placeable12 = placeable23;
                    f2 = f;
                    z2 = z;
                    i10 = 0;
                    outlinedTextFieldMeasurePolicy = this;
                    j2 = j;
                }
                final int m277calculateWidthIzADHW4 = outlinedTextFieldMeasurePolicy.m277calculateWidthIzADHW4(measureScope, i35, i36, i8, i5, i38, i6, i7, j2, f2);
                if (z2) {
                    placeable13 = placeable;
                    i11 = i10;
                    j3 = m814copyZbe2FdA$default;
                    ?? mo608measureBRTryo05 = measurable2 != null ? measurable2.mo608measureBRTryo0(Constraints.m814copyZbe2FdA$default(m814copyZbe2FdA$default, 0, m277calculateWidthIzADHW4, 0, minIntrinsicHeight, 5)) : 0;
                    ref$ObjectRef.element = mo608measureBRTryo05;
                    if (mo608measureBRTryo05 != 0) {
                        j6 = SizeKt.Size(mo608measureBRTryo05.width, mo608measureBRTryo05.height);
                    } else {
                        Size.Companion.getClass();
                    }
                    function1.mo779invoke(Size.m413boximpl(j6));
                } else {
                    placeable13 = placeable;
                    i11 = i10;
                    j3 = m814copyZbe2FdA$default;
                }
                Measurable measurable13 = measurable3;
                Placeable mo608measureBRTryo06 = measurable13 != null ? measurable13.mo608measureBRTryo0(Constraints.m814copyZbe2FdA$default(ConstraintsKt.m834offsetNN6EwU$default(i11, -i9, 1, j3), 0, m277calculateWidthIzADHW4, 0, 0, 9)) : null;
                int i39 = mo608measureBRTryo06 != null ? mo608measureBRTryo06.height : i11;
                int i40 = placeable13 != null ? placeable13.height : i11;
                if (placeable11 != null) {
                    Placeable placeable25 = placeable11;
                    placeable14 = placeable25;
                    i12 = placeable25.height;
                } else {
                    i12 = i11;
                    placeable14 = placeable11;
                }
                final Placeable placeable26 = placeable12;
                int i41 = placeable12 != null ? placeable26.height : i11;
                Placeable placeable27 = placeable10;
                int i42 = placeable10 != null ? placeable27.height : i11;
                Placeable placeable28 = placeable8;
                int i43 = placeable28.height;
                Placeable placeable29 = (Placeable) ref$ObjectRef.element;
                int i44 = placeable29 != null ? placeable29.height : i11;
                Placeable placeable30 = placeable9;
                if (placeable30 != null) {
                    placeable15 = placeable28;
                    i13 = placeable30.height;
                } else {
                    placeable15 = placeable28;
                    i13 = i11;
                }
                if (mo608measureBRTryo06 != null) {
                    i14 = i11;
                    placeable16 = placeable27;
                    i15 = i44;
                    i16 = mo608measureBRTryo06.height;
                    placeable17 = placeable13;
                    placeable18 = placeable30;
                    placeable19 = mo608measureBRTryo06;
                    str2 = str;
                    outlinedTextFieldMeasurePolicy2 = this;
                    measureScope2 = measureScope;
                    f3 = f2;
                    ref$ObjectRef2 = ref$ObjectRef;
                    z3 = z2;
                    j4 = j;
                } else {
                    i14 = i11;
                    placeable16 = placeable27;
                    i15 = i44;
                    i16 = i14;
                    placeable17 = placeable13;
                    placeable18 = placeable30;
                    f3 = f2;
                    placeable19 = mo608measureBRTryo06;
                    str2 = str;
                    outlinedTextFieldMeasurePolicy2 = this;
                    measureScope2 = measureScope;
                    j4 = j;
                    ref$ObjectRef2 = ref$ObjectRef;
                    z3 = z2;
                }
                final int m276calculateHeightmKXJcVc = outlinedTextFieldMeasurePolicy2.m276calculateHeightmKXJcVc(measureScope2, i40, i12, i41, i42, i43, i15, i13, i16, j4, z3, f3);
                final boolean z5 = z3;
                final float f4 = f3;
                int i45 = (m276calculateHeightmKXJcVc - i39) - ((!z5 || (placeable20 = (Placeable) ref$ObjectRef2.element) == null) ? i14 : placeable20.height);
                int size9 = list.size();
                int i46 = i14;
                while (i46 < size9) {
                    Measurable measurable14 = (Measurable) list.get(i46);
                    if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable14), "Container")) {
                        final Placeable mo608measureBRTryo07 = measurable14.mo608measureBRTryo0(ConstraintsKt.Constraints(m277calculateWidthIzADHW4 != Integer.MAX_VALUE ? m277calculateWidthIzADHW4 : i14, m277calculateWidthIzADHW4, i45 != Integer.MAX_VALUE ? i45 : i14, i45));
                        final Ref$ObjectRef ref$ObjectRef4 = ref$ObjectRef2;
                        final Placeable placeable31 = placeable14;
                        final Placeable placeable32 = placeable19;
                        final Placeable placeable33 = placeable17;
                        final Placeable placeable34 = placeable15;
                        final Placeable placeable35 = placeable16;
                        final Placeable placeable36 = placeable18;
                        layout$1 = measureScope.layout$1(m277calculateWidthIzADHW4, m276calculateHeightmKXJcVc, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.OutlinedTextFieldMeasurePolicy$measure$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            /* JADX WARN: Removed duplicated region for block: B:36:0x0157  */
                            /* JADX WARN: Removed duplicated region for block: B:41:0x017b  */
                            /* JADX WARN: Removed duplicated region for block: B:43:0x0181  */
                            /* JADX WARN: Removed duplicated region for block: B:46:0x0192  */
                            /* JADX WARN: Removed duplicated region for block: B:48:0x019c  */
                            /* JADX WARN: Removed duplicated region for block: B:53:0x01b2  */
                            /* JADX WARN: Removed duplicated region for block: B:55:0x01cb  */
                            /* JADX WARN: Removed duplicated region for block: B:59:0x01c8  */
                            /* JADX WARN: Removed duplicated region for block: B:60:0x0184  */
                            /* JADX WARN: Removed duplicated region for block: B:61:0x017e  */
                            /* JADX WARN: Removed duplicated region for block: B:63:0x0171  */
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object mo779invoke(java.lang.Object r27) {
                                /*
                                    Method dump skipped, instructions count: 468
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.OutlinedTextFieldMeasurePolicy$measure$1.mo779invoke(java.lang.Object):java.lang.Object");
                            }
                        });
                        return layout$1;
                    }
                    i46++;
                    m276calculateHeightmKXJcVc = m276calculateHeightmKXJcVc;
                }
                throw new NoSuchElementException(str2);
            }
            i30++;
            measurable8 = measurable8;
            function12 = function12;
            ref$ObjectRef3 = ref$ObjectRef3;
            max = i31;
            mo51roundToPx0680j_4 = i32;
            placeable5 = placeable5;
            list2 = list2;
            z = z;
            placeable6 = placeable6;
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicHeight(intrinsicMeasureScope, list, i, new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldMeasurePolicy$minIntrinsicHeight$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((IntrinsicMeasurable) obj).minIntrinsicHeight(((Number) obj2).intValue()));
            }
        });
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicWidth(intrinsicMeasureScope, list, i, new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldMeasurePolicy$minIntrinsicWidth$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((IntrinsicMeasurable) obj).minIntrinsicWidth(((Number) obj2).intValue()));
            }
        });
    }

    private OutlinedTextFieldMeasurePolicy(Function1 function1, boolean z, TextFieldLabelPosition textFieldLabelPosition, FloatProducer floatProducer, PaddingValues paddingValues, float f) {
        this.onLabelMeasured = function1;
        this.singleLine = z;
        this.labelPosition = textFieldLabelPosition;
        this.labelProgress = floatProducer;
        this.paddingValues = paddingValues;
        this.horizontalIconPadding = f;
    }
}
