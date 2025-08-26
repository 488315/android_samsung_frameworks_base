package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.internal.InlineClassHelperKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import java.util.List;

/* loaded from: classes.dex */
public abstract class RowColumnMeasurePolicyKt {
    /* JADX WARN: Removed duplicated region for block: B:73:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0191  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final MeasureResult measure(RowColumnMeasurePolicy rowColumnMeasurePolicy, int i, int i2, int i3, int i4, int i5, MeasureScope measureScope, List list, Placeable[] placeableArr, int i6, int i7, int[] iArr, int i8) {
        RowColumnMeasurePolicy rowColumnMeasurePolicy2;
        int i9;
        int i10;
        long j;
        RowColumnMeasurePolicy rowColumnMeasurePolicy3;
        int i11;
        FlowLayoutData flowLayoutData;
        char c;
        int i12;
        int i13;
        int iIntValue;
        int i14;
        long j2;
        float f;
        int i15;
        RowColumnMeasurePolicy rowColumnMeasurePolicy4;
        int i16;
        FlowLayoutData flowLayoutData2;
        int i17 = i4;
        long j3 = i5;
        int i18 = i7 - i6;
        int[] iArr2 = new int[i18];
        float f2 = 0.0f;
        int i19 = i6;
        float f3 = 0.0f;
        int i20 = 0;
        int i21 = 0;
        int i22 = 0;
        int i23 = 0;
        while (true) {
            Integer numValueOf = null;
            if (i19 >= i7) {
                break;
            }
            Measurable measurable = (Measurable) list.get(i19);
            RowColumnParentData rowColumnParentData = RowColumnImplKt.getRowColumnParentData(measurable);
            float weight = RowColumnImplKt.getWeight(rowColumnParentData);
            if (weight > f2) {
                f3 += weight;
                i22++;
                i14 = i19;
                i16 = i21;
                j2 = j3;
                f = f2;
            } else {
                if (i17 != Integer.MAX_VALUE && rowColumnParentData != null && (flowLayoutData2 = rowColumnParentData.flowLayoutData) != null) {
                    numValueOf = Integer.valueOf(Math.round(flowLayoutData2.fillCrossAxisFraction * i17));
                }
                int i24 = i3 - i23;
                Placeable placeableMo610measureBRTryo0 = placeableArr[i19];
                if (placeableMo610measureBRTryo0 == null) {
                    int iIntValue2 = numValueOf != null ? numValueOf.intValue() : 0;
                    int i25 = i3 != Integer.MAX_VALUE ? i24 < 0 ? 0 : i24 : Integer.MAX_VALUE;
                    int iIntValue3 = numValueOf != null ? numValueOf.intValue() : i17;
                    i14 = i19;
                    j2 = j3;
                    f = f2;
                    i15 = i21;
                    rowColumnMeasurePolicy4 = rowColumnMeasurePolicy;
                    placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(rowColumnMeasurePolicy.mo103createConstraintsxF2OJ5Q(false, 0, iIntValue2, i25, iIntValue3));
                } else {
                    i14 = i19;
                    j2 = j3;
                    f = f2;
                    i15 = i21;
                    rowColumnMeasurePolicy4 = rowColumnMeasurePolicy;
                }
                int iMainAxisSize = rowColumnMeasurePolicy4.mainAxisSize(placeableMo610measureBRTryo0);
                int iCrossAxisSize = rowColumnMeasurePolicy4.crossAxisSize(placeableMo610measureBRTryo0);
                iArr2[i14 - i6] = iMainAxisSize;
                int i26 = i24 - iMainAxisSize;
                if (i26 < 0) {
                    i26 = 0;
                }
                int iMin = Math.min(i5, i26);
                int iMax = Math.max(i15, iCrossAxisSize);
                placeableArr[i14] = placeableMo610measureBRTryo0;
                i23 = iMainAxisSize + iMin + i23;
                i16 = iMax;
                i20 = iMin;
            }
            i19 = i14 + 1;
            i21 = i16;
            f2 = f;
            j3 = j2;
        }
        long j4 = j3;
        float f4 = f2;
        int i27 = i21;
        RowColumnMeasurePolicy rowColumnMeasurePolicy5 = rowColumnMeasurePolicy;
        if (i22 == 0) {
            i23 -= i20;
            rowColumnMeasurePolicy2 = rowColumnMeasurePolicy5;
            i10 = i27;
            i9 = 0;
        } else {
            long j5 = j4 * (i22 - 1);
            long jRound = ((i3 != Integer.MAX_VALUE ? i3 : i) - i23) - j5;
            if (jRound < 0) {
                jRound = 0;
            }
            float f5 = jRound / f3;
            for (int i28 = i6; i28 < i7; i28++) {
                jRound -= Math.round(RowColumnImplKt.getWeight(RowColumnImplKt.getRowColumnParentData((Measurable) list.get(i28))) * f5);
            }
            int i29 = i6;
            int iMax2 = i27;
            int i30 = 0;
            while (i29 < i7) {
                if (placeableArr[i29] == null) {
                    Measurable measurable2 = (Measurable) list.get(i29);
                    RowColumnParentData rowColumnParentData2 = RowColumnImplKt.getRowColumnParentData(measurable2);
                    float weight2 = RowColumnImplKt.getWeight(rowColumnParentData2);
                    long j6 = jRound;
                    Integer numValueOf2 = (i17 == Integer.MAX_VALUE || rowColumnParentData2 == null || (flowLayoutData = rowColumnParentData2.flowLayoutData) == null) ? null : Integer.valueOf(Math.round(flowLayoutData.fillCrossAxisFraction * i17));
                    if (weight2 <= f4) {
                        InlineClassHelperKt.throwIllegalStateException("All weights <= 0 should have placeables");
                    }
                    int iSignum = Long.signum(j6);
                    int i31 = iMax2;
                    j = j6 - iSignum;
                    int iMax3 = Math.max(0, Math.round(weight2 * f5) + iSignum);
                    if (rowColumnParentData2 != null ? rowColumnParentData2.fill : true) {
                        c = 65535;
                        if (iMax3 != Integer.MAX_VALUE) {
                            i12 = i29;
                            i13 = iMax3;
                        }
                        if (numValueOf2 == null) {
                            i11 = i12;
                            iIntValue = numValueOf2.intValue();
                        } else {
                            i11 = i12;
                            iIntValue = 0;
                        }
                        int iIntValue4 = numValueOf2 == null ? numValueOf2.intValue() : i17;
                        rowColumnMeasurePolicy3 = rowColumnMeasurePolicy;
                        Placeable placeableMo610measureBRTryo02 = measurable2.mo610measureBRTryo0(rowColumnMeasurePolicy3.mo103createConstraintsxF2OJ5Q(true, iMax3, iIntValue, i13, iIntValue4));
                        int iMainAxisSize2 = rowColumnMeasurePolicy3.mainAxisSize(placeableMo610measureBRTryo02);
                        int iCrossAxisSize2 = rowColumnMeasurePolicy3.crossAxisSize(placeableMo610measureBRTryo02);
                        iArr2[i11 - i6] = iMainAxisSize2;
                        i30 += iMainAxisSize2;
                        iMax2 = Math.max(i31, iCrossAxisSize2);
                        placeableArr[i11] = placeableMo610measureBRTryo02;
                    } else {
                        c = 65535;
                    }
                    i12 = i29;
                    i13 = iMax3;
                    iMax3 = 0;
                    if (numValueOf2 == null) {
                    }
                    int iIntValue42 = numValueOf2 == null ? numValueOf2.intValue() : i17;
                    rowColumnMeasurePolicy3 = rowColumnMeasurePolicy;
                    Placeable placeableMo610measureBRTryo022 = measurable2.mo610measureBRTryo0(rowColumnMeasurePolicy3.mo103createConstraintsxF2OJ5Q(true, iMax3, iIntValue, i13, iIntValue42));
                    int iMainAxisSize22 = rowColumnMeasurePolicy3.mainAxisSize(placeableMo610measureBRTryo022);
                    int iCrossAxisSize22 = rowColumnMeasurePolicy3.crossAxisSize(placeableMo610measureBRTryo022);
                    iArr2[i11 - i6] = iMainAxisSize22;
                    i30 += iMainAxisSize22;
                    iMax2 = Math.max(i31, iCrossAxisSize22);
                    placeableArr[i11] = placeableMo610measureBRTryo022;
                } else {
                    j = jRound;
                    rowColumnMeasurePolicy3 = rowColumnMeasurePolicy5;
                    i11 = i29;
                }
                i29 = i11 + 1;
                i17 = i4;
                rowColumnMeasurePolicy5 = rowColumnMeasurePolicy3;
                jRound = j;
            }
            int i32 = iMax2;
            rowColumnMeasurePolicy2 = rowColumnMeasurePolicy5;
            i9 = (int) (i30 + j5);
            int i33 = i3 - i23;
            if (i9 < 0) {
                i9 = 0;
            }
            if (i9 > i33) {
                i9 = i33;
            }
            i10 = i32;
        }
        int i34 = i23 + i9;
        if (i34 < 0) {
            i34 = 0;
        }
        int iMax4 = Math.max(i34, i);
        int iMax5 = Math.max(i10, Math.max(i2, 0));
        int[] iArr3 = new int[i18];
        rowColumnMeasurePolicy2.populateMainAxisPositions(iMax4, iArr2, iArr3, measureScope);
        return rowColumnMeasurePolicy2.placeHelper(placeableArr, measureScope, iArr3, iMax4, iMax5, iArr, i8, i6, i7);
    }
}
