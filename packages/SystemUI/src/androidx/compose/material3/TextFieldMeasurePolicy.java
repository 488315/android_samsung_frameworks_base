package androidx.compose.material3;

import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.material3.TextFieldLabelPosition;
import androidx.compose.material3.internal.FloatProducer;
import androidx.compose.material3.internal.LayoutUtilKt;
import androidx.compose.material3.internal.TextFieldImplKt;
import androidx.compose.material3.tokens.MotionTokens;
import androidx.compose.ui.Alignment;
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
import androidx.compose.ui.unit.IntOffset;
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
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj4), "Prefix")) {
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
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj5), "Suffix")) {
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
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj6), "Leading")) {
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
                int i9 = iIntValue4 + iIntValue5;
                return ConstraintsKt.m834constrainWidthK40F9xA(Math.max(iIntValue + i9, Math.max((intrinsicMeasurable6 != null ? ((Number) function2.invoke(intrinsicMeasurable6, Integer.valueOf(i))).intValue() : 0) + i9, iIntValue2)) + iIntValue6 + iIntValue3, ConstraintsKt.Constraints$default(0, 0, 0, 0, 15));
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
    public final int m316calculateHeightmKXJcVc$1(IntrinsicMeasureScope intrinsicMeasureScope, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j, boolean z, float f) {
        int iMax;
        PaddingValues paddingValues = this.paddingValues;
        float fMo110calculateBottomPaddingD9Ej5fM = paddingValues.mo110calculateBottomPaddingD9Ej5fM() + paddingValues.mo113calculateTopPaddingD9Ej5fM();
        Dp.Companion companion = Dp.Companion;
        int iMo52roundToPx0680j_4 = intrinsicMeasureScope.mo52roundToPx0680j_4(fMo110calculateBottomPaddingD9Ej5fM);
        int iMaxOf = ComparisonsKt___ComparisonsJvmKt.maxOf(i, i7, i5, i6, z ? 0 : MathHelpersKt.lerp(f, i2, 0));
        if (i2 <= 0 || z) {
            iMax = 0;
        } else {
            int iMo52roundToPx0680j_42 = intrinsicMeasureScope.mo52roundToPx0680j_4(this.minimizedLabelHalfHeight * 2);
            MotionTokens.INSTANCE.getClass();
            iMax = Math.max(iMo52roundToPx0680j_42, MathHelpersKt.lerp(MotionTokens.EasingEmphasizedAccelerateCubicBezier.transform(f), 0, i2));
        }
        int i9 = iMo52roundToPx0680j_4 + iMax + iMaxOf;
        if (!z) {
            i2 = 0;
        }
        return ConstraintsKt.m833constrainHeightK40F9xA(Math.max(i3, Math.max(i4, i9)) + i2 + i8, j);
    }

    public final int intrinsicHeight$1(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i, Function2 function2) {
        Object obj;
        int i2;
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
        TextFieldMeasurePolicy textFieldMeasurePolicy = this;
        int size = list.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                obj = null;
                break;
            }
            obj = list.get(i3);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj), "Leading")) {
                break;
            }
            i3++;
        }
        IntrinsicMeasurable intrinsicMeasurable = (IntrinsicMeasurable) obj;
        if (intrinsicMeasurable != null) {
            i2 = i;
            iSubtractConstraintSafely = LayoutUtilKt.subtractConstraintSafely(i2, intrinsicMeasurable.maxIntrinsicWidth(Integer.MAX_VALUE));
            iIntValue = ((Number) function2.invoke(intrinsicMeasurable, Integer.valueOf(i2))).intValue();
        } else {
            i2 = i;
            iSubtractConstraintSafely = i2;
            iIntValue = 0;
        }
        int size2 = list.size();
        int i4 = 0;
        while (true) {
            if (i4 >= size2) {
                obj2 = null;
                break;
            }
            obj2 = list.get(i4);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj2), "Trailing")) {
                break;
            }
            i4++;
        }
        IntrinsicMeasurable intrinsicMeasurable2 = (IntrinsicMeasurable) obj2;
        if (intrinsicMeasurable2 != null) {
            iSubtractConstraintSafely = LayoutUtilKt.subtractConstraintSafely(iSubtractConstraintSafely, intrinsicMeasurable2.maxIntrinsicWidth(Integer.MAX_VALUE));
            iIntValue2 = ((Number) function2.invoke(intrinsicMeasurable2, Integer.valueOf(i2))).intValue();
        } else {
            iIntValue2 = 0;
        }
        int size3 = list.size();
        int i5 = 0;
        while (true) {
            if (i5 >= size3) {
                obj3 = null;
                break;
            }
            obj3 = list.get(i5);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj3), "Label")) {
                break;
            }
            i5++;
        }
        Object obj8 = (IntrinsicMeasurable) obj3;
        int iIntValue5 = obj8 != null ? ((Number) function2.invoke(obj8, Integer.valueOf(iSubtractConstraintSafely))).intValue() : 0;
        int size4 = list.size();
        int i6 = 0;
        while (true) {
            if (i6 >= size4) {
                obj4 = null;
                break;
            }
            obj4 = list.get(i6);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj4), "Prefix")) {
                break;
            }
            i6++;
        }
        IntrinsicMeasurable intrinsicMeasurable3 = (IntrinsicMeasurable) obj4;
        if (intrinsicMeasurable3 != null) {
            iIntValue3 = ((Number) function2.invoke(intrinsicMeasurable3, Integer.valueOf(iSubtractConstraintSafely))).intValue();
            iSubtractConstraintSafely = LayoutUtilKt.subtractConstraintSafely(iSubtractConstraintSafely, intrinsicMeasurable3.maxIntrinsicWidth(Integer.MAX_VALUE));
        } else {
            iIntValue3 = 0;
        }
        int size5 = list.size();
        int i7 = 0;
        while (true) {
            if (i7 >= size5) {
                obj5 = null;
                break;
            }
            obj5 = list.get(i7);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj5), "Suffix")) {
                break;
            }
            i7++;
        }
        IntrinsicMeasurable intrinsicMeasurable4 = (IntrinsicMeasurable) obj5;
        if (intrinsicMeasurable4 != null) {
            iIntValue4 = ((Number) function2.invoke(intrinsicMeasurable4, Integer.valueOf(iSubtractConstraintSafely))).intValue();
            iSubtractConstraintSafely = LayoutUtilKt.subtractConstraintSafely(iSubtractConstraintSafely, intrinsicMeasurable4.maxIntrinsicWidth(Integer.MAX_VALUE));
        } else {
            iIntValue4 = 0;
        }
        int size6 = list.size();
        int i8 = 0;
        while (i8 < size6) {
            Object obj9 = list.get(i8);
            if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj9), "TextField")) {
                int iIntValue6 = ((Number) function2.invoke(obj9, Integer.valueOf(iSubtractConstraintSafely))).intValue();
                int size7 = list.size();
                int i9 = 0;
                while (true) {
                    if (i9 >= size7) {
                        obj6 = null;
                        break;
                    }
                    obj6 = list.get(i9);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj6), "Hint")) {
                        break;
                    }
                    i9++;
                }
                Object obj10 = (IntrinsicMeasurable) obj6;
                int iIntValue7 = obj10 != null ? ((Number) function2.invoke(obj10, Integer.valueOf(iSubtractConstraintSafely))).intValue() : 0;
                int size8 = list.size();
                int i10 = 0;
                while (true) {
                    if (i10 >= size8) {
                        obj7 = null;
                        break;
                    }
                    obj7 = list.get(i10);
                    if (Intrinsics.areEqual(LayoutUtilKt.getLayoutId((IntrinsicMeasurable) obj7), "Supporting")) {
                        break;
                    }
                    i10++;
                }
                Object obj11 = (IntrinsicMeasurable) obj7;
                int iIntValue8 = obj11 != null ? ((Number) function2.invoke(obj11, Integer.valueOf(i2))).intValue() : 0;
                int i11 = iIntValue7;
                return textFieldMeasurePolicy.m316calculateHeightmKXJcVc$1(intrinsicMeasureScope, iIntValue6, iIntValue5, iIntValue, iIntValue2, iIntValue3, iIntValue4, i11, iIntValue8, ConstraintsKt.Constraints$default(0, 0, 0, 0, 15), textFieldMeasurePolicy.labelPosition instanceof TextFieldLabelPosition.Above, textFieldMeasurePolicy.labelProgress.invoke());
            }
            i8++;
            textFieldMeasurePolicy = this;
            iIntValue4 = iIntValue4;
            iIntValue = iIntValue;
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicHeight$1(intrinsicMeasureScope, list, i, new Function2() { // from class: androidx.compose.material3.TextFieldMeasurePolicy.maxIntrinsicHeight.1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((IntrinsicMeasurable) obj).maxIntrinsicHeight(((Number) obj2).intValue()));
            }
        });
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicWidth(list, i, new Function2() { // from class: androidx.compose.material3.TextFieldMeasurePolicy.maxIntrinsicWidth.1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((IntrinsicMeasurable) obj).maxIntrinsicWidth(((Number) obj2).intValue()));
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x01c0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01a0  */
    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final MeasureResult mo3measure3p2s80s(final MeasureScope measureScope, List list, long j) {
        Object obj;
        Object obj2;
        int i;
        Placeable placeableMo610measureBRTryo0;
        Object obj3;
        Placeable placeable;
        int i2;
        Placeable placeableMo610measureBRTryo02;
        Object obj4;
        int i3;
        Placeable placeableMo610measureBRTryo03;
        Object obj5;
        int i4;
        Placeable placeable2;
        int size;
        int i5;
        long j2;
        Object obj6;
        int size2;
        int i6;
        Object obj7;
        String str;
        long j3;
        Placeable placeable3;
        int i7;
        Placeable placeable4;
        int i8;
        Placeable placeable5;
        Placeable placeable6;
        int i9;
        Ref$ObjectRef ref$ObjectRef;
        int i10;
        Placeable placeable7;
        Placeable placeable8;
        int i11;
        Placeable placeable9;
        boolean z;
        int i12;
        Placeable placeable10;
        float f;
        Placeable placeable11;
        TextFieldMeasurePolicy textFieldMeasurePolicy;
        MeasureScope measureScope2;
        long j4;
        Placeable placeable12;
        int i13;
        List list2 = list;
        float fInvoke = this.labelProgress.invoke();
        PaddingValues paddingValues = this.paddingValues;
        final int iMo52roundToPx0680j_4 = measureScope.mo52roundToPx0680j_4(paddingValues.mo113calculateTopPaddingD9Ej5fM());
        int iMo52roundToPx0680j_42 = measureScope.mo52roundToPx0680j_4(paddingValues.mo110calculateBottomPaddingD9Ej5fM());
        long jM816copyZbe2FdA$default = Constraints.m816copyZbe2FdA$default(j, 0, 0, 0, 0, 10);
        int size3 = list2.size();
        int i14 = 0;
        while (true) {
            if (i14 >= size3) {
                obj = null;
                break;
            }
            obj = list2.get(i14);
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj), "Leading")) {
                break;
            }
            i14++;
        }
        Measurable measurable = (Measurable) obj;
        Placeable placeableMo610measureBRTryo04 = measurable != null ? measurable.mo610measureBRTryo0(jM816copyZbe2FdA$default) : null;
        int i15 = placeableMo610measureBRTryo04 != null ? placeableMo610measureBRTryo04.width : 0;
        int iMax = Math.max(0, placeableMo610measureBRTryo04 != null ? placeableMo610measureBRTryo04.height : 0);
        int size4 = list2.size();
        int i16 = 0;
        while (true) {
            if (i16 >= size4) {
                obj2 = null;
                break;
            }
            obj2 = list2.get(i16);
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj2), "Trailing")) {
                break;
            }
            i16++;
        }
        Measurable measurable2 = (Measurable) obj2;
        if (measurable2 != null) {
            i = i15;
            placeableMo610measureBRTryo0 = measurable2.mo610measureBRTryo0(ConstraintsKt.m836offsetNN6EwU$default(-i15, 0, 2, jM816copyZbe2FdA$default));
        } else {
            i = i15;
            placeableMo610measureBRTryo0 = null;
        }
        int i17 = (placeableMo610measureBRTryo0 != null ? placeableMo610measureBRTryo0.width : 0) + i;
        int iMax2 = Math.max(iMax, placeableMo610measureBRTryo0 != null ? placeableMo610measureBRTryo0.height : 0);
        int size5 = list2.size();
        int i18 = 0;
        while (true) {
            if (i18 >= size5) {
                obj3 = null;
                break;
            }
            obj3 = list2.get(i18);
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj3), "Prefix")) {
                break;
            }
            i18++;
        }
        Measurable measurable3 = (Measurable) obj3;
        if (measurable3 != null) {
            placeable = placeableMo610measureBRTryo0;
            i2 = i17;
            placeableMo610measureBRTryo02 = measurable3.mo610measureBRTryo0(ConstraintsKt.m836offsetNN6EwU$default(-i17, 0, 2, jM816copyZbe2FdA$default));
        } else {
            placeable = placeableMo610measureBRTryo0;
            i2 = i17;
            placeableMo610measureBRTryo02 = null;
        }
        int i19 = i2 + (placeableMo610measureBRTryo02 != null ? placeableMo610measureBRTryo02.width : 0);
        int iMax3 = Math.max(iMax2, placeableMo610measureBRTryo02 != null ? placeableMo610measureBRTryo02.height : 0);
        int size6 = list2.size();
        int i20 = 0;
        while (true) {
            if (i20 >= size6) {
                obj4 = null;
                break;
            }
            obj4 = list2.get(i20);
            int i21 = size6;
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj4), "Suffix")) {
                break;
            }
            i20++;
            size6 = i21;
        }
        Measurable measurable4 = (Measurable) obj4;
        if (measurable4 != null) {
            i3 = i19;
            placeableMo610measureBRTryo03 = measurable4.mo610measureBRTryo0(ConstraintsKt.m836offsetNN6EwU$default(-i19, 0, 2, jM816copyZbe2FdA$default));
        } else {
            i3 = i19;
            placeableMo610measureBRTryo03 = null;
        }
        int i22 = i3 + (placeableMo610measureBRTryo03 != null ? placeableMo610measureBRTryo03.width : 0);
        int iMax4 = Math.max(iMax3, placeableMo610measureBRTryo03 != null ? placeableMo610measureBRTryo03.height : 0);
        boolean z2 = this.labelPosition instanceof TextFieldLabelPosition.Above;
        int size7 = list2.size();
        int i23 = 0;
        while (true) {
            if (i23 >= size7) {
                obj5 = null;
                break;
            }
            obj5 = list2.get(i23);
            int i24 = size7;
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj5), "Label")) {
                break;
            }
            i23++;
            size7 = i24;
        }
        Measurable measurable5 = (Measurable) obj5;
        Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
        if (z2) {
            i4 = iMo52roundToPx0680j_42;
            placeable2 = placeable;
            int iMinIntrinsicHeight = measurable5 != null ? measurable5.minIntrinsicHeight(Constraints.m825getMinWidthimpl(j)) : 0;
            size = list2.size();
            i5 = 0;
            while (true) {
                if (i5 < size) {
                    j2 = jM816copyZbe2FdA$default;
                    obj6 = null;
                    break;
                }
                obj6 = list2.get(i5);
                i13 = size;
                j2 = jM816copyZbe2FdA$default;
                if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj6), "Supporting")) {
                    break;
                }
                i5++;
                size = i13;
                jM816copyZbe2FdA$default = j2;
            }
            Measurable measurable6 = (Measurable) obj6;
            int iMinIntrinsicHeight2 = measurable6 == null ? measurable6.minIntrinsicHeight(Constraints.m825getMinWidthimpl(j)) : 0;
            Placeable placeable13 = (Placeable) ref$ObjectRef2.element;
            int i25 = (placeable13 == null ? placeable13.height : 0) + iMinIntrinsicHeight + iMo52roundToPx0680j_4;
            int i26 = iMinIntrinsicHeight;
            long jM835offsetNN6EwU = ConstraintsKt.m835offsetNN6EwU(-i22, ((-i25) - i4) - iMinIntrinsicHeight2, Constraints.m816copyZbe2FdA$default(j, 0, 0, 0, 0, 11));
            size2 = list2.size();
            i6 = 0;
            while (i6 < size2) {
                int i27 = size2;
                Measurable measurable7 = (Measurable) list2.get(i6);
                int i28 = i25;
                int i29 = i6;
                if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable7), "TextField")) {
                    Placeable placeableMo610measureBRTryo05 = measurable7.mo610measureBRTryo0(jM835offsetNN6EwU);
                    long jM816copyZbe2FdA$default2 = Constraints.m816copyZbe2FdA$default(jM835offsetNN6EwU, 0, 0, 0, 0, 14);
                    int size8 = list2.size();
                    int i30 = 0;
                    while (true) {
                        if (i30 >= size8) {
                            obj7 = null;
                            break;
                        }
                        obj7 = list2.get(i30);
                        int i31 = size8;
                        int i32 = i30;
                        if (Intrinsics.areEqual(LayoutIdKt.getLayoutId((Measurable) obj7), "Hint")) {
                            break;
                        }
                        i30 = i32 + 1;
                        size8 = i31;
                    }
                    Measurable measurable8 = (Measurable) obj7;
                    Placeable placeableMo610measureBRTryo06 = measurable8 != null ? measurable8.mo610measureBRTryo0(jM816copyZbe2FdA$default2) : null;
                    int iMax5 = Math.max(iMax4, Math.max(placeableMo610measureBRTryo05.height, placeableMo610measureBRTryo06 != null ? placeableMo610measureBRTryo06.height : 0) + i28 + i4);
                    int i33 = placeableMo610measureBRTryo04 != null ? placeableMo610measureBRTryo04.width : 0;
                    Placeable placeable14 = placeable2;
                    int i34 = placeable2 != null ? placeable14.width : 0;
                    int i35 = placeableMo610measureBRTryo02 != null ? placeableMo610measureBRTryo02.width : 0;
                    int i36 = placeableMo610measureBRTryo03 != null ? placeableMo610measureBRTryo03.width : 0;
                    int i37 = placeableMo610measureBRTryo05.width;
                    Placeable placeable15 = (Placeable) ref$ObjectRef2.element;
                    int i38 = i35 + i36;
                    final int iM834constrainWidthK40F9xA = ConstraintsKt.m834constrainWidthK40F9xA(Math.max(i37 + i38, Math.max((placeableMo610measureBRTryo06 != null ? placeableMo610measureBRTryo06.width : 0) + i38, placeable15 != null ? placeable15.width : 0)) + i33 + i34, j);
                    if (z2) {
                        long j5 = j2;
                        str = "Collection contains no element matching the predicate.";
                        j3 = j5;
                        ref$ObjectRef2.element = measurable5 != null ? measurable5.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(j5, 0, iM834constrainWidthK40F9xA, 0, i26, 5)) : 0;
                    } else {
                        long j6 = j2;
                        str = "Collection contains no element matching the predicate.";
                        j3 = j6;
                    }
                    Placeable placeableMo610measureBRTryo07 = measurable6 != null ? measurable6.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(ConstraintsKt.m836offsetNN6EwU$default(0, -iMax5, 1, j3), 0, iM834constrainWidthK40F9xA, 0, 0, 9)) : null;
                    int i39 = placeableMo610measureBRTryo07 != null ? placeableMo610measureBRTryo07.height : 0;
                    int i40 = placeableMo610measureBRTryo05.height;
                    Placeable placeable16 = (Placeable) ref$ObjectRef2.element;
                    int i41 = placeable16 != null ? placeable16.height : 0;
                    int i42 = placeableMo610measureBRTryo04 != null ? placeableMo610measureBRTryo04.height : 0;
                    if (placeable14 != null) {
                        placeable3 = placeableMo610measureBRTryo05;
                        i7 = placeable14.height;
                    } else {
                        placeable3 = placeableMo610measureBRTryo05;
                        i7 = 0;
                    }
                    int i43 = placeableMo610measureBRTryo02 != null ? placeableMo610measureBRTryo02.height : 0;
                    if (placeableMo610measureBRTryo03 != null) {
                        placeable4 = placeableMo610measureBRTryo03;
                        i8 = placeableMo610measureBRTryo03.height;
                    } else {
                        placeable4 = placeableMo610measureBRTryo03;
                        i8 = 0;
                    }
                    int i44 = placeableMo610measureBRTryo06 != null ? placeableMo610measureBRTryo06.height : 0;
                    if (placeableMo610measureBRTryo07 != null) {
                        int i45 = i41;
                        int i46 = placeableMo610measureBRTryo07.height;
                        placeable5 = placeableMo610measureBRTryo04;
                        i9 = i45;
                        placeable6 = placeableMo610measureBRTryo07;
                        ref$ObjectRef = ref$ObjectRef2;
                        i10 = i46;
                        placeable7 = placeableMo610measureBRTryo06;
                        placeable8 = placeable3;
                        i11 = i43;
                        placeable9 = placeable4;
                        z = z2;
                        i12 = 0;
                        placeable10 = placeableMo610measureBRTryo02;
                        f = fInvoke;
                        placeable11 = placeable14;
                        measureScope2 = measureScope;
                        j4 = j;
                        textFieldMeasurePolicy = this;
                    } else {
                        placeable5 = placeableMo610measureBRTryo04;
                        placeable6 = placeableMo610measureBRTryo07;
                        i9 = i41;
                        ref$ObjectRef = ref$ObjectRef2;
                        i10 = 0;
                        placeable7 = placeableMo610measureBRTryo06;
                        placeable8 = placeable3;
                        i11 = i43;
                        placeable9 = placeable4;
                        z = z2;
                        i12 = 0;
                        placeable10 = placeableMo610measureBRTryo02;
                        f = fInvoke;
                        placeable11 = placeable14;
                        textFieldMeasurePolicy = this;
                        measureScope2 = measureScope;
                        j4 = j;
                    }
                    final int iM316calculateHeightmKXJcVc$1 = textFieldMeasurePolicy.m316calculateHeightmKXJcVc$1(measureScope2, i40, i9, i42, i7, i11, i8, i44, i10, j4, z, f);
                    final int i47 = (iM316calculateHeightmKXJcVc$1 - i39) - ((!z || (placeable12 = (Placeable) ref$ObjectRef.element) == null) ? i12 : placeable12.height);
                    int size9 = list.size();
                    for (int i48 = i12; i48 < size9; i48++) {
                        Measurable measurable9 = (Measurable) list.get(i48);
                        if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable9), "Container")) {
                            final Placeable placeableMo610measureBRTryo08 = measurable9.mo610measureBRTryo0(ConstraintsKt.Constraints(iM834constrainWidthK40F9xA != Integer.MAX_VALUE ? iM834constrainWidthK40F9xA : i12, iM834constrainWidthK40F9xA, i47 != Integer.MAX_VALUE ? i47 : i12, i47));
                            final Ref$ObjectRef ref$ObjectRef3 = ref$ObjectRef;
                            final boolean z3 = z;
                            final Placeable placeable17 = placeable5;
                            final Placeable placeable18 = placeable9;
                            final Placeable placeable19 = placeable6;
                            final Placeable placeable20 = placeable8;
                            final Placeable placeable21 = placeable7;
                            final Placeable placeable22 = placeable11;
                            final float f2 = f;
                            final Placeable placeable23 = placeable10;
                            return measureScope.layout$1(iM834constrainWidthK40F9xA, iM316calculateHeightmKXJcVc$1, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.TextFieldMeasurePolicy$measure$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                /* JADX WARN: Removed duplicated region for block: B:43:0x00da  */
                                /* JADX WARN: Removed duplicated region for block: B:48:0x00e8  */
                                /* JADX WARN: Removed duplicated region for block: B:49:0x00eb  */
                                /* JADX WARN: Removed duplicated region for block: B:52:0x00f2  */
                                /* JADX WARN: Removed duplicated region for block: B:53:0x00f5  */
                                /* JADX WARN: Removed duplicated region for block: B:56:0x0104  */
                                /* JADX WARN: Removed duplicated region for block: B:57:0x0107  */
                                /* JADX WARN: Removed duplicated region for block: B:60:0x010e  */
                                /* JADX WARN: Removed duplicated region for block: B:61:0x0111  */
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object mo781invoke(Object obj8) {
                                    float f3;
                                    float f4;
                                    int iMo52roundToPx0680j_43;
                                    int i49;
                                    int i50;
                                    int i51;
                                    Placeable placeable24;
                                    int i52;
                                    float f5;
                                    float f6;
                                    Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj8;
                                    if (ref$ObjectRef3.element != null) {
                                        if (z3) {
                                            iMo52roundToPx0680j_43 = 0;
                                        } else {
                                            TextFieldMeasurePolicy textFieldMeasurePolicy2 = this;
                                            if (textFieldMeasurePolicy2.singleLine) {
                                                Alignment.Companion.getClass();
                                                iMo52roundToPx0680j_43 = Alignment.Companion.CenterVertically.align(ref$ObjectRef3.element.height, i47);
                                            } else {
                                                iMo52roundToPx0680j_43 = measureScope.mo52roundToPx0680j_4(textFieldMeasurePolicy2.minimizedLabelHalfHeight) + iMo52roundToPx0680j_4;
                                            }
                                        }
                                        boolean z4 = z3;
                                        int i53 = z4 ? 0 : iMo52roundToPx0680j_4;
                                        TextFieldMeasurePolicy textFieldMeasurePolicy3 = this;
                                        int i54 = iM834constrainWidthK40F9xA;
                                        int i55 = iM316calculateHeightmKXJcVc$1;
                                        Placeable placeable25 = placeable20;
                                        Placeable placeable26 = ref$ObjectRef3.element;
                                        Placeable placeable27 = placeable21;
                                        Placeable placeable28 = placeable17;
                                        Placeable placeable29 = placeable22;
                                        Placeable placeable30 = placeable23;
                                        Placeable placeable31 = placeable18;
                                        Placeable placeable32 = placeableMo610measureBRTryo08;
                                        Placeable placeable33 = placeable19;
                                        float f7 = f2;
                                        int i56 = iMo52roundToPx0680j_4;
                                        if (z4) {
                                            i49 = i56;
                                            i50 = 0;
                                        } else {
                                            i49 = i56;
                                            i50 = placeable26.height;
                                        }
                                        int i57 = i49 + i50;
                                        LayoutDirection layoutDirection = measureScope.getLayoutDirection();
                                        textFieldMeasurePolicy3.getClass();
                                        int i58 = z4 ? placeable26.height : 0;
                                        placementScope.place(placeable32, 0, i58, 0.0f);
                                        int i59 = (i55 - (placeable33 != null ? placeable33.height : 0)) - (z4 ? placeable26.height : 0);
                                        if (placeable28 != null) {
                                            Alignment.Companion.getClass();
                                            i51 = i58;
                                            placeable24 = placeable33;
                                            placementScope.placeRelative(placeable28, 0, Alignment.Companion.CenterVertically.align(placeable28.height, i59) + i51, 0.0f);
                                        } else {
                                            i51 = i58;
                                            placeable24 = placeable33;
                                        }
                                        int iLerp = MathHelpersKt.lerp(f7, iMo52roundToPx0680j_43, i53);
                                        TextFieldLabelPosition textFieldLabelPosition = textFieldMeasurePolicy3.labelPosition;
                                        if (z4) {
                                            placementScope.place(placeable26, TextFieldImplKt.getMinimizedAlignment(textFieldLabelPosition).align(placeable26.width, i54, layoutDirection), iLerp, 0.0f);
                                            f5 = 0.0f;
                                        } else if (layoutDirection == LayoutDirection.Ltr) {
                                            i52 = placeable28 != null ? placeable28.width : 0;
                                            int iLerp2 = MathHelpersKt.lerp(f7, TextFieldImplKt.getExpandedAlignment(textFieldLabelPosition).align(placeable26.width, (i54 - (placeable28 == null ? placeable28.width : 0)) - (placeable29 == null ? placeable29.width : 0), layoutDirection) + i52, TextFieldImplKt.getMinimizedAlignment(textFieldLabelPosition).align(placeable26.width, (i54 - (placeable28 == null ? placeable28.width : 0)) - (placeable29 == null ? placeable29.width : 0), layoutDirection) + i52);
                                            f5 = 0.0f;
                                            placementScope.place(placeable26, iLerp2, iLerp, 0.0f);
                                        } else {
                                            if (placeable29 != null) {
                                                i52 = placeable29.width;
                                            }
                                            int iLerp22 = MathHelpersKt.lerp(f7, TextFieldImplKt.getExpandedAlignment(textFieldLabelPosition).align(placeable26.width, (i54 - (placeable28 == null ? placeable28.width : 0)) - (placeable29 == null ? placeable29.width : 0), layoutDirection) + i52, TextFieldImplKt.getMinimizedAlignment(textFieldLabelPosition).align(placeable26.width, (i54 - (placeable28 == null ? placeable28.width : 0)) - (placeable29 == null ? placeable29.width : 0), layoutDirection) + i52);
                                            f5 = 0.0f;
                                            placementScope.place(placeable26, iLerp22, iLerp, 0.0f);
                                        }
                                        if (placeable30 != null) {
                                            placementScope.placeRelative(placeable30, placeable28 != null ? placeable28.width : 0, i51 + i57, f5);
                                        }
                                        int i60 = (placeable28 != null ? placeable28.width : 0) + (placeable30 != null ? placeable30.width : 0);
                                        int i61 = i51 + i57;
                                        placementScope.placeRelative(placeable25, i60, i61, 0.0f);
                                        if (placeable27 != null) {
                                            placementScope.placeRelative(placeable27, i60, i61, 0.0f);
                                        }
                                        if (placeable31 != null) {
                                            placementScope.placeRelative(placeable31, (i54 - (placeable29 != null ? placeable29.width : 0)) - placeable31.width, i61, 0.0f);
                                        }
                                        if (placeable29 != null) {
                                            int i62 = i54 - placeable29.width;
                                            Alignment.Companion.getClass();
                                            f6 = 0.0f;
                                            placementScope.placeRelative(placeable29, i62, Alignment.Companion.CenterVertically.align(placeable29.height, i59) + i51, 0.0f);
                                        } else {
                                            f6 = 0.0f;
                                        }
                                        if (placeable24 != null) {
                                            placementScope.placeRelative(placeable24, 0, i51 + i59, f6);
                                        }
                                    } else {
                                        TextFieldMeasurePolicy textFieldMeasurePolicy4 = this;
                                        int i63 = iM834constrainWidthK40F9xA;
                                        int i64 = iM316calculateHeightmKXJcVc$1;
                                        Placeable placeable34 = placeable20;
                                        Placeable placeable35 = placeable21;
                                        Placeable placeable36 = placeable17;
                                        Placeable placeable37 = placeable22;
                                        Placeable placeable38 = placeable23;
                                        Placeable placeable39 = placeable18;
                                        Placeable placeable40 = placeableMo610measureBRTryo08;
                                        Placeable placeable41 = placeable19;
                                        float density = measureScope.getDensity();
                                        textFieldMeasurePolicy4.getClass();
                                        IntOffset.Companion.getClass();
                                        Placeable.PlacementScope.m628place70tqf50$default(placementScope, placeable40, 0L);
                                        int i65 = i64 - (placeable41 != null ? placeable41.height : 0);
                                        int iRoundToInt = MathKt__MathJVMKt.roundToInt(textFieldMeasurePolicy4.paddingValues.mo113calculateTopPaddingD9Ej5fM() * density);
                                        if (placeable36 != null) {
                                            Alignment.Companion.getClass();
                                            f3 = 0.0f;
                                            placementScope.placeRelative(placeable36, 0, Alignment.Companion.CenterVertically.align(placeable36.height, i65), 0.0f);
                                        } else {
                                            f3 = 0.0f;
                                        }
                                        if (placeable38 != null) {
                                            placementScope.placeRelative(placeable38, placeable36 != null ? placeable36.width : 0, TextFieldMeasurePolicy.placeWithoutLabel$calculateVerticalPosition(textFieldMeasurePolicy4, i65, iRoundToInt, placeable38), f3);
                                        }
                                        int i66 = (placeable36 != null ? placeable36.width : 0) + (placeable38 != null ? placeable38.width : 0);
                                        placementScope.placeRelative(placeable34, i66, TextFieldMeasurePolicy.placeWithoutLabel$calculateVerticalPosition(textFieldMeasurePolicy4, i65, iRoundToInt, placeable34), 0.0f);
                                        if (placeable35 != null) {
                                            placementScope.placeRelative(placeable35, i66, TextFieldMeasurePolicy.placeWithoutLabel$calculateVerticalPosition(textFieldMeasurePolicy4, i65, iRoundToInt, placeable35), 0.0f);
                                        }
                                        if (placeable39 != null) {
                                            placementScope.placeRelative(placeable39, (i63 - (placeable37 != null ? placeable37.width : 0)) - placeable39.width, TextFieldMeasurePolicy.placeWithoutLabel$calculateVerticalPosition(textFieldMeasurePolicy4, i65, iRoundToInt, placeable39), 0.0f);
                                        }
                                        if (placeable37 != null) {
                                            int i67 = i63 - placeable37.width;
                                            Alignment.Companion.getClass();
                                            f4 = 0.0f;
                                            placementScope.placeRelative(placeable37, i67, Alignment.Companion.CenterVertically.align(placeable37.height, i65), 0.0f);
                                        } else {
                                            f4 = 0.0f;
                                        }
                                        if (placeable41 != null) {
                                            placementScope.placeRelative(placeable41, 0, i65, f4);
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            });
                        }
                    }
                    throw new NoSuchElementException(str);
                }
                i25 = i28;
                list2 = list2;
                placeableMo610measureBRTryo04 = placeableMo610measureBRTryo04;
                jM835offsetNN6EwU = jM835offsetNN6EwU;
                i6 = i29 + 1;
                placeableMo610measureBRTryo02 = placeableMo610measureBRTryo02;
                ref$ObjectRef2 = ref$ObjectRef2;
                size2 = i27;
                placeableMo610measureBRTryo03 = placeableMo610measureBRTryo03;
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        i4 = iMo52roundToPx0680j_42;
        placeable2 = placeable;
        ref$ObjectRef2.element = measurable5 != null ? measurable5.mo610measureBRTryo0(ConstraintsKt.m835offsetNN6EwU(-i22, -iMo52roundToPx0680j_42, jM816copyZbe2FdA$default)) : 0;
        size = list2.size();
        i5 = 0;
        while (true) {
            if (i5 < size) {
            }
            i5++;
            size = i13;
            jM816copyZbe2FdA$default = j2;
        }
        Measurable measurable62 = (Measurable) obj6;
        if (measurable62 == null) {
        }
        Placeable placeable132 = (Placeable) ref$ObjectRef2.element;
        int i252 = (placeable132 == null ? placeable132.height : 0) + iMinIntrinsicHeight + iMo52roundToPx0680j_4;
        int i262 = iMinIntrinsicHeight;
        long jM835offsetNN6EwU2 = ConstraintsKt.m835offsetNN6EwU(-i22, ((-i252) - i4) - iMinIntrinsicHeight2, Constraints.m816copyZbe2FdA$default(j, 0, 0, 0, 0, 11));
        size2 = list2.size();
        i6 = 0;
        while (i6 < size2) {
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicHeight$1(intrinsicMeasureScope, list, i, new Function2() { // from class: androidx.compose.material3.TextFieldMeasurePolicy.minIntrinsicHeight.1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return Integer.valueOf(((IntrinsicMeasurable) obj).minIntrinsicHeight(((Number) obj2).intValue()));
            }
        });
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        return intrinsicWidth(list, i, new Function2() { // from class: androidx.compose.material3.TextFieldMeasurePolicy.minIntrinsicWidth.1
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
