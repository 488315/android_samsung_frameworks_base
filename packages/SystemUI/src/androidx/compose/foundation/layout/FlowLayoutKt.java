package androidx.compose.foundation.layout;

import androidx.collection.IntIntPair;
import androidx.compose.foundation.layout.CrossAxisAlignment;
import androidx.compose.foundation.layout.FlowLayoutBuildingBlocks;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.ConstraintsKt;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class FlowLayoutKt {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        CrossAxisAlignment.Companion companion = CrossAxisAlignment.Companion;
        Alignment.Companion.getClass();
        BiasAlignment.Vertical vertical = Alignment.Companion.Top;
        companion.getClass();
        new CrossAxisAlignment.VerticalCrossAxisAlignment(vertical);
        new CrossAxisAlignment.HorizontalCrossAxisAlignment(Alignment.Companion.Start);
    }

    /* JADX WARN: Code restructure failed: missing block: B:108:0x028e, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L193;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x02f7, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L217;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0270  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x02f2  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x031a  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0382  */
    /* JADX WARN: Removed duplicated region for block: B:131:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x036d  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x02b6  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x02bd  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0272  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0371  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0264  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void FlowRow(androidx.compose.ui.Modifier r36, androidx.compose.foundation.layout.Arrangement.Horizontal r37, androidx.compose.foundation.layout.Arrangement.Vertical r38, androidx.compose.ui.Alignment.Vertical r39, int r40, int r41, androidx.compose.foundation.layout.FlowRowOverflow r42, final kotlin.jvm.functions.Function3 r43, androidx.compose.runtime.Composer r44, final int r45, final int r46) {
        /*
            Method dump skipped, instructions count: 908
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.FlowLayoutKt.FlowRow(androidx.compose.ui.Modifier, androidx.compose.foundation.layout.Arrangement$Horizontal, androidx.compose.foundation.layout.Arrangement$Vertical, androidx.compose.ui.Alignment$Vertical, int, int, androidx.compose.foundation.layout.FlowRowOverflow, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final long intrinsicCrossAxisSize(List list, Function3 function3, Function3 function32, int i, int i2, int i3, int i4, int i5, FlowLayoutOverflowState flowLayoutOverflowState) {
        int i6;
        if (list.isEmpty()) {
            return IntIntPair.m1constructorimpl(0, 0);
        }
        FlowLayoutBuildingBlocks flowLayoutBuildingBlocks = new FlowLayoutBuildingBlocks(i4, flowLayoutOverflowState, ConstraintsKt.Constraints(0, i, 0, Integer.MAX_VALUE), i5, i2, i3, null);
        IntrinsicMeasurable intrinsicMeasurable = (IntrinsicMeasurable) CollectionsKt___CollectionsKt.getOrNull(0, list);
        int intValue = intrinsicMeasurable != null ? ((Number) function32.invoke(intrinsicMeasurable, 0, Integer.valueOf(i))).intValue() : 0;
        int intValue2 = intrinsicMeasurable != null ? ((Number) function3.invoke(intrinsicMeasurable, 0, Integer.valueOf(intValue))).intValue() : 0;
        int i7 = 0;
        int i8 = 0;
        if (flowLayoutBuildingBlocks.m103getWrapInfoOpUlnko(list.size() > 1, 0, IntIntPair.m1constructorimpl(i, Integer.MAX_VALUE), intrinsicMeasurable == null ? null : IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(intValue2, intValue)), 0, 0, 0, false, false).isLastItemInContainer) {
            IntIntPair m105ellipsisSizeF35zmw$foundation_layout = flowLayoutOverflowState.m105ellipsisSizeF35zmw$foundation_layout(0, 0, intrinsicMeasurable != null);
            return IntIntPair.m1constructorimpl(m105ellipsisSizeF35zmw$foundation_layout != null ? (int) (m105ellipsisSizeF35zmw$foundation_layout.packedValue & 4294967295L) : 0, 0);
        }
        int size = list.size();
        int i9 = i;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        while (true) {
            int i14 = i8;
            if (i10 >= size) {
                i6 = i11;
                break;
            }
            int i15 = i9 - intValue2;
            int i16 = i10 + 1;
            i8 = Math.max(i14, intValue);
            IntrinsicMeasurable intrinsicMeasurable2 = (IntrinsicMeasurable) CollectionsKt___CollectionsKt.getOrNull(i16, list);
            int intValue3 = intrinsicMeasurable2 != null ? ((Number) function32.invoke(intrinsicMeasurable2, Integer.valueOf(i16), Integer.valueOf(i))).intValue() : 0;
            int intValue4 = intrinsicMeasurable2 != null ? ((Number) function3.invoke(intrinsicMeasurable2, Integer.valueOf(i16), Integer.valueOf(intValue3))).intValue() + i2 : 0;
            int i17 = i16 - i12;
            i6 = i16;
            int i18 = i13;
            FlowLayoutBuildingBlocks.WrapInfo m103getWrapInfoOpUlnko = flowLayoutBuildingBlocks.m103getWrapInfoOpUlnko(i10 + 2 < list.size(), i17, IntIntPair.m1constructorimpl(i15, Integer.MAX_VALUE), intrinsicMeasurable2 == null ? null : IntIntPair.m0boximpl(IntIntPair.m1constructorimpl(intValue4, intValue3)), i18, i7, i8, false, false);
            if (m103getWrapInfoOpUlnko.isLastItemInLine) {
                int i19 = i8 + i3 + i7;
                FlowLayoutBuildingBlocks.WrapEllipsisInfo wrapEllipsisInfo = flowLayoutBuildingBlocks.getWrapEllipsisInfo(m103getWrapInfoOpUlnko, intrinsicMeasurable2 != null, i18, i19, i15, i17);
                intValue4 -= i2;
                i13 = i18 + 1;
                if (m103getWrapInfoOpUlnko.isLastItemInContainer) {
                    if (wrapEllipsisInfo != null && !wrapEllipsisInfo.placeEllipsisOnLastContentLine) {
                        i19 = ((int) (wrapEllipsisInfo.ellipsisSize & 4294967295L)) + i3 + i19;
                    }
                    i7 = i19;
                } else {
                    i9 = i;
                    i12 = i6;
                    i7 = i19;
                    i8 = 0;
                }
            } else {
                i9 = i15;
                i13 = i18;
            }
            intValue2 = intValue4;
            intValue = intValue3;
            i10 = i6;
            i11 = i10;
        }
        return IntIntPair.m1constructorimpl(i7 - i3, i6);
    }

    /* renamed from: measureAndCache-rqJ1uqs, reason: not valid java name */
    public static final long m104measureAndCacherqJ1uqs(Measurable measurable, FlowLineMeasurePolicy flowLineMeasurePolicy, long j, Function1 function1) {
        FlowLayoutData flowLayoutData;
        if (RowColumnImplKt.getWeight(RowColumnImplKt.getRowColumnParentData(measurable)) == 0.0f) {
            RowColumnParentData rowColumnParentData = RowColumnImplKt.getRowColumnParentData(measurable);
            if (((rowColumnParentData == null || (flowLayoutData = rowColumnParentData.flowLayoutData) == null) ? null : Float.valueOf(flowLayoutData.fillCrossAxisFraction)) == null) {
                Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
                function1.mo779invoke(mo608measureBRTryo0);
                return IntIntPair.m1constructorimpl(flowLineMeasurePolicy.mainAxisSize(mo608measureBRTryo0), flowLineMeasurePolicy.crossAxisSize(mo608measureBRTryo0));
            }
        }
        boolean z = ((FlowMeasurePolicy) flowLineMeasurePolicy).isHorizontal;
        int minIntrinsicWidth = z ? measurable.minIntrinsicWidth(Integer.MAX_VALUE) : measurable.minIntrinsicHeight(Integer.MAX_VALUE);
        return IntIntPair.m1constructorimpl(minIntrinsicWidth, z ? measurable.minIntrinsicHeight(minIntrinsicWidth) : measurable.minIntrinsicWidth(minIntrinsicWidth));
    }

    public static final Measurable safeNext(Iterator it, FlowLineInfo flowLineInfo) {
        try {
            if (!(it instanceof ContextualFlowItemIterator)) {
                return (Measurable) it.next();
            }
            flowLineInfo.getClass();
            return ((ContextualFlowItemIterator) it).getNext$foundation_layout(flowLineInfo);
        } catch (IndexOutOfBoundsException unused) {
            return null;
        }
    }
}
