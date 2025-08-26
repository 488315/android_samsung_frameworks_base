package androidx.compose.material3;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.material3.TextFieldLabelPosition;
import androidx.compose.material3.internal.FloatProducer;
import androidx.compose.material3.internal.LayoutUtilKt;
import androidx.compose.material3.internal.TextFieldImplKt;
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
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.comparisons.ComparisonsKt___ComparisonsJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.math.MathKt__MathJVMKt;

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
    public final int m277calculateHeightmKXJcVc(IntrinsicMeasureScope intrinsicMeasureScope, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j, boolean z, float f) {
        int iMaxOf = ComparisonsKt___ComparisonsJvmKt.maxOf(i5, i7, i3, i4, z ? 0 : MathHelpersKt.lerp(f, i6, 0));
        PaddingValues paddingValues = this.paddingValues;
        float fMo58toPx0680j_4 = intrinsicMeasureScope.mo58toPx0680j_4(paddingValues.mo113calculateTopPaddingD9Ej5fM());
        if (!z) {
            fMo58toPx0680j_4 = MathHelpersKt.lerp(fMo58toPx0680j_4, Math.max(fMo58toPx0680j_4, i6 / 2.0f), f);
        }
        float fMo58toPx0680j_42 = fMo58toPx0680j_4 + iMaxOf + intrinsicMeasureScope.mo58toPx0680j_4(paddingValues.mo110calculateBottomPaddingD9Ej5fM());
        if (!z) {
            i6 = 0;
        }
        return ConstraintsKt.m833constrainHeightK40F9xA(Math.max(i, Math.max(i2, MathKt__MathJVMKt.roundToInt(fMo58toPx0680j_42))) + i6 + i8, j);
    }

    /* renamed from: calculateWidth-IzADHW4, reason: not valid java name */
    public final int m278calculateWidthIzADHW4(IntrinsicMeasureScope intrinsicMeasureScope, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, float f) {
        int i8 = i3 + i4;
        int iMax = Math.max(i5 + i8, Math.max(i7 + i8, MathHelpersKt.lerp(f, i6, 0))) + i + i2;
        LayoutDirection layoutDirection = LayoutDirection.Ltr;
        PaddingValues paddingValues = this.paddingValues;
        float fMo112calculateRightPaddingu2uoSUM = paddingValues.mo112calculateRightPaddingu2uoSUM(layoutDirection) + paddingValues.mo111calculateLeftPaddingu2uoSUM(layoutDirection);
        Dp.Companion companion = Dp.Companion;
        return ConstraintsKt.m834constrainWidthK40F9xA(Math.max(iMax, MathKt__MathJVMKt.roundToInt((i6 + intrinsicMeasureScope.mo58toPx0680j_4(fMo112calculateRightPaddingu2uoSUM)) * f)), j);
    }

    public final int intrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i, Function2 function2) {
        Object obj;
        int iSubtractConstraintSafely;
        int iIntValue;
        Object obj2;
        int iIntValue2;
        Object obj3;
        Object obj4;
        int iIntValue3;
        Object obj5;
        int iIntValue4;
        Object obj6;
        Object obj7;
        OutlinedTextFieldMeasurePolicy outlinedTextFieldMeasurePolicy = this;
        float fInvoke = outlinedTextFieldMeasurePolicy.labelProgress.invoke();
        int size = list.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                obj = null;
                break;
            }
            obj = list.get(i2);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj), "Leading")) {
                break;
            }
            i2++;
        }
        IntrinsicMeasurable intrinsicMeasurable = (IntrinsicMeasurable) obj;
        if (intrinsicMeasurable != null) {
            iSubtractConstraintSafely = LayoutUtilKt.subtractConstraintSafely(i, intrinsicMeasurable.maxIntrinsicWidth(Integer.MAX_VALUE));
            iIntValue = ((Number) function2.invoke(intrinsicMeasurable, Integer.valueOf(i))).intValue();
        } else {
            iSubtractConstraintSafely = i;
            iIntValue = 0;
        }
        int size2 = list.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size2) {
                obj2 = null;
                break;
            }
            obj2 = list.get(i3);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj2), "Trailing")) {
                break;
            }
            i3++;
        }
        IntrinsicMeasurable intrinsicMeasurable2 = (IntrinsicMeasurable) obj2;
        if (intrinsicMeasurable2 != null) {
            iSubtractConstraintSafely = LayoutUtilKt.subtractConstraintSafely(iSubtractConstraintSafely, intrinsicMeasurable2.maxIntrinsicWidth(Integer.MAX_VALUE));
            iIntValue2 = ((Number) function2.invoke(intrinsicMeasurable2, Integer.valueOf(i))).intValue();
        } else {
            iIntValue2 = 0;
        }
        int size3 = list.size();
        int i4 = 0;
        while (true) {
            if (i4 >= size3) {
                obj3 = null;
                break;
            }
            obj3 = list.get(i4);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj3), "Label")) {
                break;
            }
            i4++;
        }
        Object obj8 = (IntrinsicMeasurable) obj3;
        int iIntValue5 = obj8 != null ? ((Number) function2.invoke(obj8, Integer.valueOf(MathHelpersKt.lerp(fInvoke, iSubtractConstraintSafely, i)))).intValue() : 0;
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
        if (intrinsicMeasurable3 != null) {
            iIntValue3 = ((Number) function2.invoke(intrinsicMeasurable3, Integer.valueOf(iSubtractConstraintSafely))).intValue();
            iSubtractConstraintSafely = LayoutUtilKt.subtractConstraintSafely(iSubtractConstraintSafely, intrinsicMeasurable3.maxIntrinsicWidth(Integer.MAX_VALUE));
        } else {
            iIntValue3 = 0;
        }
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
        if (intrinsicMeasurable4 != null) {
            iIntValue4 = ((Number) function2.invoke(intrinsicMeasurable4, Integer.valueOf(iSubtractConstraintSafely))).intValue();
            iSubtractConstraintSafely = LayoutUtilKt.subtractConstraintSafely(iSubtractConstraintSafely, intrinsicMeasurable4.maxIntrinsicWidth(Integer.MAX_VALUE));
        } else {
            iIntValue4 = 0;
        }
        int size6 = list.size();
        int i7 = 0;
        while (i7 < size6) {
            Object obj9 = list.get(i7);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj9), "TextField")) {
                int iIntValue6 = ((Number) function2.invoke(obj9, Integer.valueOf(iSubtractConstraintSafely))).intValue();
                int size7 = list.size();
                int i8 = 0;
                while (true) {
                    if (i8 >= size7) {
                        obj6 = null;
                        break;
                    }
                    obj6 = list.get(i8);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj6), "Hint")) {
                        break;
                    }
                    i8++;
                }
                Object obj10 = (IntrinsicMeasurable) obj6;
                int iIntValue7 = obj10 != null ? ((Number) function2.invoke(obj10, Integer.valueOf(iSubtractConstraintSafely))).intValue() : 0;
                int size8 = list.size();
                int i9 = 0;
                while (true) {
                    if (i9 >= size8) {
                        obj7 = null;
                        break;
                    }
                    obj7 = list.get(i9);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj7), "Supporting")) {
                        break;
                    }
                    i9++;
                }
                Object obj11 = (IntrinsicMeasurable) obj7;
                return outlinedTextFieldMeasurePolicy.m277calculateHeightmKXJcVc(intrinsicMeasureScope, iIntValue, iIntValue2, iIntValue3, iIntValue4, iIntValue6, iIntValue5, iIntValue7, obj11 != null ? ((Number) function2.invoke(obj11, Integer.valueOf(i))).intValue() : 0, ConstraintsKt.Constraints$default(0, 0, 0, 0, 15), outlinedTextFieldMeasurePolicy.labelPosition instanceof TextFieldLabelPosition.Above, fInvoke);
            }
            i7++;
            iIntValue4 = iIntValue4;
            iIntValue3 = iIntValue3;
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
                int iIntValue = ((Number) function2.invoke(obj7, Integer.valueOf(i))).intValue();
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
                int iIntValue2 = intrinsicMeasurable != null ? ((Number) function2.invoke(intrinsicMeasurable, Integer.valueOf(i))).intValue() : 0;
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
                int iIntValue3 = intrinsicMeasurable2 != null ? ((Number) function2.invoke(intrinsicMeasurable2, Integer.valueOf(i))).intValue() : 0;
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
                int iIntValue4 = intrinsicMeasurable3 != null ? ((Number) function2.invoke(intrinsicMeasurable3, Integer.valueOf(i))).intValue() : 0;
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
                int iIntValue5 = intrinsicMeasurable4 != null ? ((Number) function2.invoke(intrinsicMeasurable4, Integer.valueOf(i))).intValue() : 0;
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
                int iIntValue6 = intrinsicMeasurable5 != null ? ((Number) function2.invoke(intrinsicMeasurable5, Integer.valueOf(i))).intValue() : 0;
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
                return m278calculateWidthIzADHW4(intrinsicMeasureScope, iIntValue4, iIntValue3, iIntValue5, iIntValue6, iIntValue, iIntValue2, intrinsicMeasurable6 != null ? ((Number) function2.invoke(intrinsicMeasurable6, Integer.valueOf(i))).intValue() : 0, ConstraintsKt.Constraints$default(0, 0, 0, 0, 15), this.labelProgress.invoke());
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicHeight(intrinsicMeasureScope, list, i, new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldMeasurePolicy.maxIntrinsicHeight.1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((IntrinsicMeasurable) obj).maxIntrinsicHeight(((Number) obj2).intValue()));
            }
        });
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicWidth(intrinsicMeasureScope, list, i, new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldMeasurePolicy.maxIntrinsicWidth.1
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
        Placeable placeableMo610measureBRTryo0;
        Object obj3;
        Placeable placeable2;
        int i2;
        Placeable placeableMo610measureBRTryo02;
        Object obj4;
        Placeable placeable3;
        int i3;
        int i4;
        Placeable placeableMo610measureBRTryo03;
        boolean z;
        Object obj5;
        float f;
        int iMinIntrinsicHeight;
        PaddingValues paddingValues;
        Object obj6;
        int iMax;
        Object obj7;
        Placeable placeable4;
        int i5;
        Measurable measurable;
        int i6;
        String str;
        Placeable placeable5;
        Measurable measurable2;
        Ref$ObjectRef ref$ObjectRef;
        int i7;
        Placeable placeable6;
        Placeable placeable7;
        Function1 function1;
        Measurable measurable3;
        int i8;
        int i9;
        Placeable placeable8;
        Placeable placeable9;
        float f2;
        boolean z2;
        int i10;
        OutlinedTextFieldMeasurePolicy outlinedTextFieldMeasurePolicy;
        long j2;
        Placeable placeable10;
        int i11;
        long j3;
        int i12;
        Placeable placeable11;
        Placeable placeable12;
        int i13;
        int i14;
        Placeable placeable13;
        int i15;
        int i16;
        Placeable placeable14;
        Placeable placeable15;
        float f3;
        Placeable placeable16;
        String str2;
        OutlinedTextFieldMeasurePolicy outlinedTextFieldMeasurePolicy2;
        MeasureScope measureScope2;
        long j4;
        Ref$ObjectRef ref$ObjectRef2;
        boolean z3;
        Placeable placeable17;
        long jSize;
        List list2 = list;
        float fInvoke = this.labelProgress.invoke();
        PaddingValues paddingValues2 = this.paddingValues;
        int iMo52roundToPx0680j_4 = measureScope.mo52roundToPx0680j_4(paddingValues2.mo110calculateBottomPaddingD9Ej5fM());
        long jM816copyZbe2FdA$default = Constraints.m816copyZbe2FdA$default(j, 0, 0, 0, 0, 10);
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
        Placeable placeableMo610measureBRTryo04 = measurable4 != null ? measurable4.mo610measureBRTryo0(jM816copyZbe2FdA$default) : null;
        int i18 = placeableMo610measureBRTryo04 != null ? placeableMo610measureBRTryo04.width : 0;
        int iMax2 = Math.max(0, placeableMo610measureBRTryo04 != null ? placeableMo610measureBRTryo04.height : 0);
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
            placeable = placeableMo610measureBRTryo04;
            i = i18;
            placeableMo610measureBRTryo0 = measurable5.mo610measureBRTryo0(ConstraintsKt.m836offsetNN6EwU$default(-i18, 0, 2, jM816copyZbe2FdA$default));
        } else {
            placeable = placeableMo610measureBRTryo04;
            i = i18;
            placeableMo610measureBRTryo0 = null;
        }
        int i20 = i + (placeableMo610measureBRTryo0 != null ? placeableMo610measureBRTryo0.width : 0);
        int iMax3 = Math.max(iMax2, placeableMo610measureBRTryo0 != null ? placeableMo610measureBRTryo0.height : 0);
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
            placeable2 = placeableMo610measureBRTryo0;
            i2 = i20;
            placeableMo610measureBRTryo02 = measurable6.mo610measureBRTryo0(ConstraintsKt.m836offsetNN6EwU$default(-i20, 0, 2, jM816copyZbe2FdA$default));
        } else {
            placeable2 = placeableMo610measureBRTryo0;
            i2 = i20;
            placeableMo610measureBRTryo02 = null;
        }
        int i23 = i2 + (placeableMo610measureBRTryo02 != null ? placeableMo610measureBRTryo02.width : 0);
        int iMax4 = Math.max(iMax3, placeableMo610measureBRTryo02 != null ? placeableMo610measureBRTryo02.height : 0);
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
            placeable3 = placeableMo610measureBRTryo02;
            i3 = i23;
            i4 = 0;
            placeableMo610measureBRTryo03 = measurable7.mo610measureBRTryo0(ConstraintsKt.m836offsetNN6EwU$default(-i23, 0, 2, jM816copyZbe2FdA$default));
        } else {
            placeable3 = placeableMo610measureBRTryo02;
            i3 = i23;
            i4 = 0;
            placeableMo610measureBRTryo03 = null;
        }
        int i26 = i3 + (placeableMo610measureBRTryo03 != null ? placeableMo610measureBRTryo03.width : i4);
        int iMax5 = Math.max(iMax4, placeableMo610measureBRTryo03 != null ? placeableMo610measureBRTryo03.height : i4);
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
        long jSize2 = 0;
        if (z) {
            f = fInvoke;
            iMinIntrinsicHeight = measurable8 != null ? measurable8.minIntrinsicHeight(Constraints.m825getMinWidthimpl(j)) : 0;
        } else {
            int iMo52roundToPx0680j_42 = measureScope.mo52roundToPx0680j_4(paddingValues2.mo112calculateRightPaddingu2uoSUM(measureScope.getLayoutDirection())) + measureScope.mo52roundToPx0680j_4(paddingValues2.mo111calculateLeftPaddingu2uoSUM(measureScope.getLayoutDirection()));
            f = fInvoke;
            ?? Mo610measureBRTryo0 = measurable8 != null ? measurable8.mo610measureBRTryo0(ConstraintsKt.m835offsetNN6EwU(-MathHelpersKt.lerp(fInvoke, i26 + iMo52roundToPx0680j_42, iMo52roundToPx0680j_42), -iMo52roundToPx0680j_4, jM816copyZbe2FdA$default)) : 0;
            ref$ObjectRef3.element = Mo610measureBRTryo0;
            if (Mo610measureBRTryo0 != 0) {
                jSize = SizeKt.Size(Mo610measureBRTryo0.width, Mo610measureBRTryo0.height);
            } else {
                Size.Companion.getClass();
                jSize = 0;
            }
            function12.mo781invoke(Size.m415boximpl(jSize));
            iMinIntrinsicHeight = 0;
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
        int iMinIntrinsicHeight2 = measurable9 != null ? measurable9.minIntrinsicHeight(Constraints.m825getMinWidthimpl(j)) : 0;
        if (z) {
            iMax = measureScope.mo52roundToPx0680j_4(paddingValues.mo113calculateTopPaddingD9Ej5fM());
        } else {
            Placeable placeable18 = (Placeable) ref$ObjectRef3.element;
            iMax = Math.max((placeable18 != null ? placeable18.height : 0) / 2, measureScope.mo52roundToPx0680j_4(paddingValues.mo113calculateTopPaddingD9Ej5fM()));
        }
        long jM816copyZbe2FdA$default2 = Constraints.m816copyZbe2FdA$default(ConstraintsKt.m835offsetNN6EwU(-i26, (((-iMo52roundToPx0680j_4) - iMax) - iMinIntrinsicHeight) - iMinIntrinsicHeight2, j), 0, 0, 0, 0, 11);
        int size7 = list2.size();
        int i30 = 0;
        while (i30 < size7) {
            Measurable measurable10 = (Measurable) list2.get(i30);
            int i31 = iMax;
            int i32 = iMo52roundToPx0680j_4;
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable10), "TextField")) {
                Placeable placeableMo610measureBRTryo05 = measurable10.mo610measureBRTryo0(jM816copyZbe2FdA$default2);
                long jM816copyZbe2FdA$default3 = Constraints.m816copyZbe2FdA$default(jM816copyZbe2FdA$default2, 0, 0, 0, 0, 14);
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
                Placeable placeableMo610measureBRTryo06 = measurable11 != null ? measurable11.mo610measureBRTryo0(jM816copyZbe2FdA$default3) : null;
                int iMax6 = Math.max(iMax5, Math.max(placeableMo610measureBRTryo05.height, placeableMo610measureBRTryo06 != null ? placeableMo610measureBRTryo06.height : 0) + i31 + i32);
                int i35 = placeable != null ? placeable.width : 0;
                Placeable placeable19 = placeable2;
                int i36 = placeable2 != null ? placeable19.width : 0;
                Placeable placeable20 = placeable3;
                int i37 = placeable3 != null ? placeable20.width : 0;
                if (placeableMo610measureBRTryo03 != null) {
                    placeable4 = placeable19;
                    i5 = placeableMo610measureBRTryo03.width;
                } else {
                    placeable4 = placeable19;
                    i5 = 0;
                }
                int i38 = placeableMo610measureBRTryo05.width;
                Placeable placeable21 = (Placeable) ref$ObjectRef3.element;
                if (placeable21 != null) {
                    Measurable measurable12 = measurable8;
                    i6 = placeable21.width;
                    measurable = measurable12;
                } else {
                    measurable = measurable8;
                    i6 = 0;
                }
                if (placeableMo610measureBRTryo06 != null) {
                    str = "Collection contains no element matching the predicate.";
                    placeable5 = placeableMo610measureBRTryo05;
                    measurable2 = measurable;
                    ref$ObjectRef = ref$ObjectRef3;
                    i7 = placeableMo610measureBRTryo06.width;
                    placeable6 = placeableMo610measureBRTryo06;
                    placeable7 = placeableMo610measureBRTryo03;
                    function1 = function12;
                    measurable3 = measurable9;
                    i8 = i37;
                    i9 = iMax6;
                    placeable8 = placeable4;
                    placeable9 = placeable20;
                    f2 = f;
                    i10 = 0;
                    outlinedTextFieldMeasurePolicy = this;
                    j2 = j;
                    z2 = z;
                } else {
                    str = "Collection contains no element matching the predicate.";
                    placeable5 = placeableMo610measureBRTryo05;
                    measurable2 = measurable;
                    ref$ObjectRef = ref$ObjectRef3;
                    i7 = 0;
                    placeable6 = placeableMo610measureBRTryo06;
                    placeable7 = placeableMo610measureBRTryo03;
                    function1 = function12;
                    measurable3 = measurable9;
                    i8 = i37;
                    i9 = iMax6;
                    placeable8 = placeable4;
                    placeable9 = placeable20;
                    f2 = f;
                    z2 = z;
                    i10 = 0;
                    outlinedTextFieldMeasurePolicy = this;
                    j2 = j;
                }
                final int iM278calculateWidthIzADHW4 = outlinedTextFieldMeasurePolicy.m278calculateWidthIzADHW4(measureScope, i35, i36, i8, i5, i38, i6, i7, j2, f2);
                if (z2) {
                    placeable10 = placeable;
                    i11 = i10;
                    j3 = jM816copyZbe2FdA$default;
                    ?? Mo610measureBRTryo02 = measurable2 != null ? measurable2.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(jM816copyZbe2FdA$default, 0, iM278calculateWidthIzADHW4, 0, iMinIntrinsicHeight, 5)) : 0;
                    ref$ObjectRef.element = Mo610measureBRTryo02;
                    if (Mo610measureBRTryo02 != 0) {
                        jSize2 = SizeKt.Size(Mo610measureBRTryo02.width, Mo610measureBRTryo02.height);
                    } else {
                        Size.Companion.getClass();
                    }
                    function1.mo781invoke(Size.m415boximpl(jSize2));
                } else {
                    placeable10 = placeable;
                    i11 = i10;
                    j3 = jM816copyZbe2FdA$default;
                }
                Measurable measurable13 = measurable3;
                Placeable placeableMo610measureBRTryo07 = measurable13 != null ? measurable13.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(ConstraintsKt.m836offsetNN6EwU$default(i11, -i9, 1, j3), 0, iM278calculateWidthIzADHW4, 0, 0, 9)) : null;
                int i39 = placeableMo610measureBRTryo07 != null ? placeableMo610measureBRTryo07.height : i11;
                int i40 = placeable10 != null ? placeable10.height : i11;
                if (placeable8 != null) {
                    Placeable placeable22 = placeable8;
                    placeable11 = placeable22;
                    i12 = placeable22.height;
                } else {
                    i12 = i11;
                    placeable11 = placeable8;
                }
                final Placeable placeable23 = placeable9;
                int i41 = placeable9 != null ? placeable23.height : i11;
                Placeable placeable24 = placeable7;
                int i42 = placeable7 != null ? placeable24.height : i11;
                Placeable placeable25 = placeable5;
                int i43 = placeable25.height;
                Placeable placeable26 = (Placeable) ref$ObjectRef.element;
                int i44 = placeable26 != null ? placeable26.height : i11;
                Placeable placeable27 = placeable6;
                if (placeable27 != null) {
                    placeable12 = placeable25;
                    i13 = placeable27.height;
                } else {
                    placeable12 = placeable25;
                    i13 = i11;
                }
                if (placeableMo610measureBRTryo07 != null) {
                    i14 = i11;
                    placeable13 = placeable24;
                    i15 = i44;
                    i16 = placeableMo610measureBRTryo07.height;
                    placeable14 = placeable10;
                    placeable15 = placeable27;
                    placeable16 = placeableMo610measureBRTryo07;
                    str2 = str;
                    outlinedTextFieldMeasurePolicy2 = this;
                    measureScope2 = measureScope;
                    f3 = f2;
                    ref$ObjectRef2 = ref$ObjectRef;
                    z3 = z2;
                    j4 = j;
                } else {
                    i14 = i11;
                    placeable13 = placeable24;
                    i15 = i44;
                    i16 = i14;
                    placeable14 = placeable10;
                    placeable15 = placeable27;
                    f3 = f2;
                    placeable16 = placeableMo610measureBRTryo07;
                    str2 = str;
                    outlinedTextFieldMeasurePolicy2 = this;
                    measureScope2 = measureScope;
                    j4 = j;
                    ref$ObjectRef2 = ref$ObjectRef;
                    z3 = z2;
                }
                final int iM277calculateHeightmKXJcVc = outlinedTextFieldMeasurePolicy2.m277calculateHeightmKXJcVc(measureScope2, i40, i12, i41, i42, i43, i15, i13, i16, j4, z3, f3);
                final boolean z5 = z3;
                final float f4 = f3;
                int i45 = (iM277calculateHeightmKXJcVc - i39) - ((!z5 || (placeable17 = (Placeable) ref$ObjectRef2.element) == null) ? i14 : placeable17.height);
                int size9 = list.size();
                int i46 = i14;
                while (i46 < size9) {
                    Measurable measurable14 = (Measurable) list.get(i46);
                    if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable14), "Container")) {
                        final Placeable placeableMo610measureBRTryo08 = measurable14.mo610measureBRTryo0(ConstraintsKt.Constraints(iM278calculateWidthIzADHW4 != Integer.MAX_VALUE ? iM278calculateWidthIzADHW4 : i14, iM278calculateWidthIzADHW4, i45 != Integer.MAX_VALUE ? i45 : i14, i45));
                        final Ref$ObjectRef ref$ObjectRef4 = ref$ObjectRef2;
                        final Placeable placeable28 = placeable11;
                        final Placeable placeable29 = placeable16;
                        final Placeable placeable30 = placeable14;
                        final Placeable placeable31 = placeable12;
                        final Placeable placeable32 = placeable13;
                        final Placeable placeable33 = placeable15;
                        return measureScope.layout$1(iM278calculateWidthIzADHW4, iM277calculateHeightmKXJcVc, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.OutlinedTextFieldMeasurePolicy$measure$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj8) {
                                int i47;
                                Placeable placeable34;
                                int i48;
                                OutlinedTextFieldMeasurePolicy outlinedTextFieldMeasurePolicy3;
                                int i49;
                                int i50;
                                Placeable placeable35;
                                OutlinedTextFieldMeasurePolicy outlinedTextFieldMeasurePolicy4;
                                float f5;
                                int iAlign;
                                float f6;
                                float f7;
                                float f8;
                                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj8;
                                OutlinedTextFieldMeasurePolicy outlinedTextFieldMeasurePolicy5 = this.this$0;
                                int i51 = iM277calculateHeightmKXJcVc;
                                int i52 = iM278calculateWidthIzADHW4;
                                Placeable placeable36 = placeable30;
                                Placeable placeable37 = placeable28;
                                Placeable placeable38 = placeable23;
                                Placeable placeable39 = placeable32;
                                Placeable placeable40 = placeable31;
                                Placeable placeable41 = ref$ObjectRef4.element;
                                Placeable placeable42 = placeable33;
                                Placeable placeable43 = placeableMo610measureBRTryo08;
                                Placeable placeable44 = placeable29;
                                float density = measureScope.getDensity();
                                LayoutDirection layoutDirection = measureScope.getLayoutDirection();
                                boolean z6 = z5;
                                float f9 = f4;
                                float fMo58toPx0680j_4 = measureScope.mo58toPx0680j_4(this.this$0.horizontalIconPadding);
                                outlinedTextFieldMeasurePolicy5.getClass();
                                int i53 = (!z6 || placeable41 == null) ? 0 : placeable41.height;
                                placementScope.place(placeable43, 0, i53, 0.0f);
                                int i54 = (i51 - (placeable44 != null ? placeable44.height : 0)) - ((!z6 || placeable41 == null) ? 0 : placeable41.height);
                                PaddingValues paddingValues3 = outlinedTextFieldMeasurePolicy5.paddingValues;
                                int iRoundToInt = MathKt__MathJVMKt.roundToInt(paddingValues3.mo113calculateTopPaddingD9Ej5fM() * density);
                                if (placeable36 != null) {
                                    Alignment.Companion.getClass();
                                    i47 = i53;
                                    placeable34 = placeable38;
                                    i48 = 0;
                                    placementScope.placeRelative(placeable36, 0, Alignment.Companion.CenterVertically.align(placeable36.height, i54) + i47, 0.0f);
                                } else {
                                    i47 = i53;
                                    placeable34 = placeable38;
                                    i48 = 0;
                                }
                                if (placeable41 == null) {
                                    outlinedTextFieldMeasurePolicy3 = outlinedTextFieldMeasurePolicy5;
                                } else {
                                    if (z6) {
                                        iAlign = i48;
                                    } else if (outlinedTextFieldMeasurePolicy5.singleLine) {
                                        Alignment.Companion.getClass();
                                        iAlign = Alignment.Companion.CenterVertically.align(placeable41.height, i54);
                                    } else {
                                        iAlign = iRoundToInt;
                                    }
                                    int iLerp = MathHelpersKt.lerp(f9, iAlign, z6 ? i48 : -(placeable41.height / 2));
                                    TextFieldLabelPosition textFieldLabelPosition = outlinedTextFieldMeasurePolicy5.labelPosition;
                                    if (z6) {
                                        placementScope.place(placeable41, TextFieldImplKt.getMinimizedAlignment(textFieldLabelPosition).align(placeable41.width, i52, layoutDirection), iLerp, 0.0f);
                                        outlinedTextFieldMeasurePolicy3 = outlinedTextFieldMeasurePolicy5;
                                    } else {
                                        float fCalculateStartPadding = PaddingKt.calculateStartPadding(paddingValues3, layoutDirection) * density;
                                        float fCalculateEndPadding = PaddingKt.calculateEndPadding(paddingValues3, layoutDirection) * density;
                                        if (placeable36 == null) {
                                            f6 = fCalculateStartPadding;
                                        } else {
                                            float f10 = placeable36.width;
                                            float f11 = fCalculateStartPadding - fMo58toPx0680j_4;
                                            if (f11 < 0.0f) {
                                                f11 = 0.0f;
                                            }
                                            f6 = f10 + f11;
                                        }
                                        if (placeable37 == null) {
                                            f7 = fCalculateEndPadding;
                                            f8 = f7;
                                        } else {
                                            f7 = fCalculateEndPadding;
                                            float f12 = placeable37.width;
                                            float f13 = f7 - fMo58toPx0680j_4;
                                            if (f13 < 0.0f) {
                                                f13 = 0.0f;
                                            }
                                            f8 = f12 + f13;
                                        }
                                        LayoutDirection layoutDirection2 = LayoutDirection.Ltr;
                                        outlinedTextFieldMeasurePolicy3 = outlinedTextFieldMeasurePolicy5;
                                        placementScope.place(placeable41, MathKt__MathJVMKt.roundToInt(MathHelpersKt.lerp(TextFieldImplKt.getExpandedAlignment(textFieldLabelPosition).align(placeable41.width, i52 - MathKt__MathJVMKt.roundToInt(f6 + f8), layoutDirection) + (layoutDirection == layoutDirection2 ? f6 : f8), TextFieldImplKt.getMinimizedAlignment(textFieldLabelPosition).align(placeable41.width, i52 - MathKt__MathJVMKt.roundToInt(fCalculateStartPadding + f7), layoutDirection) + (layoutDirection == layoutDirection2 ? fCalculateStartPadding : f7), f9)), iLerp, 0.0f);
                                    }
                                }
                                if (placeable34 != null) {
                                    i49 = iRoundToInt;
                                    i50 = i47;
                                    placeable35 = placeable34;
                                    outlinedTextFieldMeasurePolicy4 = outlinedTextFieldMeasurePolicy3;
                                    placementScope.placeRelative(placeable35, placeable36 != null ? placeable36.width : 0, OutlinedTextFieldMeasurePolicy.place$calculateVerticalPosition(i50, outlinedTextFieldMeasurePolicy4, i54, i49, placeable41, placeable35), 0.0f);
                                } else {
                                    i49 = iRoundToInt;
                                    i50 = i47;
                                    placeable35 = placeable34;
                                    outlinedTextFieldMeasurePolicy4 = outlinedTextFieldMeasurePolicy3;
                                }
                                int i55 = (placeable36 != null ? placeable36.width : 0) + (placeable35 != null ? placeable35.width : 0);
                                placementScope.placeRelative(placeable40, i55, OutlinedTextFieldMeasurePolicy.place$calculateVerticalPosition(i50, outlinedTextFieldMeasurePolicy4, i54, i49, placeable41, placeable40), 0.0f);
                                if (placeable42 != null) {
                                    placementScope.placeRelative(placeable42, i55, OutlinedTextFieldMeasurePolicy.place$calculateVerticalPosition(i50, outlinedTextFieldMeasurePolicy4, i54, i49, placeable41, placeable42), 0.0f);
                                }
                                if (placeable39 != null) {
                                    placementScope.placeRelative(placeable39, (i52 - (placeable37 != null ? placeable37.width : 0)) - placeable39.width, OutlinedTextFieldMeasurePolicy.place$calculateVerticalPosition(i50, outlinedTextFieldMeasurePolicy4, i54, i49, placeable41, placeable39), 0.0f);
                                }
                                if (placeable37 != null) {
                                    int i56 = i52 - placeable37.width;
                                    Alignment.Companion.getClass();
                                    f5 = 0.0f;
                                    placementScope.placeRelative(placeable37, i56, Alignment.Companion.CenterVertically.align(placeable37.height, i54) + i50, 0.0f);
                                } else {
                                    f5 = 0.0f;
                                }
                                if (placeable44 != null) {
                                    placementScope.placeRelative(placeable44, 0, i50 + i54, f5);
                                }
                                return Unit.INSTANCE;
                            }
                        });
                    }
                    i46++;
                    iM277calculateHeightmKXJcVc = iM277calculateHeightmKXJcVc;
                }
                throw new NoSuchElementException(str2);
            }
            i30++;
            measurable8 = measurable8;
            function12 = function12;
            ref$ObjectRef3 = ref$ObjectRef3;
            iMax = i31;
            iMo52roundToPx0680j_4 = i32;
            placeable3 = placeable3;
            list2 = list2;
            z = z;
            placeableMo610measureBRTryo03 = placeableMo610measureBRTryo03;
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicHeight(intrinsicMeasureScope, list, i, new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldMeasurePolicy.minIntrinsicHeight.1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((IntrinsicMeasurable) obj).minIntrinsicHeight(((Number) obj2).intValue()));
            }
        });
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicWidth(intrinsicMeasureScope, list, i, new Function2() { // from class: androidx.compose.material3.OutlinedTextFieldMeasurePolicy.minIntrinsicWidth.1
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
