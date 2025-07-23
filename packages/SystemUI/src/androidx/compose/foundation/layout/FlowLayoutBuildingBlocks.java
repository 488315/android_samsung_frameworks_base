package androidx.compose.foundation.layout;

import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.Placeable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FlowLayoutBuildingBlocks {
    public final long constraints;
    public final int crossAxisSpacing;
    public final int mainAxisSpacing;
    public final int maxItemsInMainAxis;
    public final int maxLines;
    public final FlowLayoutOverflowState overflow;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WrapEllipsisInfo {
        public final Measurable ellipsis;
        public final long ellipsisSize;
        public boolean placeEllipsisOnLastContentLine;
        public final Placeable placeable;

        public /* synthetic */ WrapEllipsisInfo(Measurable measurable, Placeable placeable, long j, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
            this(measurable, placeable, j, z);
        }

        private WrapEllipsisInfo(Measurable measurable, Placeable placeable, long j, boolean z) {
            this.ellipsis = measurable;
            this.placeable = placeable;
            this.ellipsisSize = j;
            this.placeEllipsisOnLastContentLine = z;
        }

        public /* synthetic */ WrapEllipsisInfo(Measurable measurable, Placeable placeable, long j, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(measurable, placeable, j, (i & 8) != 0 ? true : z, null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class WrapInfo {
        public final boolean isLastItemInContainer;
        public final boolean isLastItemInLine;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public WrapInfo() {
            /*
                r3 = this;
                r0 = 3
                r1 = 0
                r2 = 0
                r3.<init>(r2, r2, r0, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.FlowLayoutBuildingBlocks.WrapInfo.<init>():void");
        }

        public WrapInfo(boolean z, boolean z2) {
            this.isLastItemInLine = z;
            this.isLastItemInContainer = z2;
        }

        public /* synthetic */ WrapInfo(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2);
        }
    }

    public /* synthetic */ FlowLayoutBuildingBlocks(int i, FlowLayoutOverflowState flowLayoutOverflowState, long j, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, flowLayoutOverflowState, j, i2, i3, i4);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.compose.foundation.layout.FlowLayoutBuildingBlocks.WrapEllipsisInfo getWrapEllipsisInfo(androidx.compose.foundation.layout.FlowLayoutBuildingBlocks.WrapInfo r17, boolean r18, int r19, int r20, int r21, int r22) {
        /*
            r16 = this;
            r0 = r16
            r1 = r19
            r2 = r22
            r3 = r17
            boolean r3 = r3.isLastItemInContainer
            r4 = 0
            if (r3 != 0) goto Le
            goto L63
        Le:
            androidx.compose.foundation.layout.FlowLayoutOverflowState r3 = r0.overflow
            r3.getClass()
            int[] r5 = androidx.compose.foundation.layout.FlowLayoutOverflowState.WhenMappings.$EnumSwitchMapping$0
            androidx.compose.foundation.layout.FlowLayoutOverflow$OverflowType r6 = r3.type
            int r6 = r6.ordinal()
            r5 = r5[r6]
            r6 = 1
            if (r5 == r6) goto L51
            r7 = 2
            if (r5 == r7) goto L51
            r7 = 3
            if (r5 == r7) goto L30
            r7 = 4
            if (r5 != r7) goto L2a
            goto L30
        L2a:
            kotlin.NoWhenBranchMatchedException r0 = new kotlin.NoWhenBranchMatchedException
            r0.<init>()
            throw r0
        L30:
            if (r18 == 0) goto L3b
            androidx.compose.ui.layout.Measurable r5 = r3.seeMoreMeasurable
            androidx.collection.IntIntPair r7 = r3.seeMoreSize
            androidx.compose.ui.layout.Placeable r3 = r3.seeMorePlaceable
        L38:
            r10 = r3
            r9 = r5
            goto L4f
        L3b:
            int r5 = r3.minLinesToShowCollapse
            int r5 = r5 - r6
            if (r1 < r5) goto L49
            int r5 = r3.minCrossAxisSizeToShowCollapse
            r7 = r20
            if (r7 < r5) goto L49
            androidx.compose.ui.layout.Measurable r5 = r3.collapseMeasurable
            goto L4a
        L49:
            r5 = r4
        L4a:
            androidx.collection.IntIntPair r7 = r3.collapseSize
            androidx.compose.ui.layout.Placeable r3 = r3.collapsePlaceable
            goto L38
        L4f:
            if (r9 != 0) goto L53
        L51:
            r8 = r4
            goto L61
        L53:
            androidx.compose.foundation.layout.FlowLayoutBuildingBlocks$WrapEllipsisInfo r8 = new androidx.compose.foundation.layout.FlowLayoutBuildingBlocks$WrapEllipsisInfo
            r7.getClass()
            r14 = 8
            r15 = 0
            long r11 = r7.packedValue
            r13 = 0
            r8.<init>(r9, r10, r11, r13, r14, r15)
        L61:
            if (r8 != 0) goto L64
        L63:
            return r4
        L64:
            if (r1 < 0) goto L77
            if (r2 == 0) goto L78
            r1 = 32
            long r3 = r8.ellipsisSize
            long r3 = r3 >> r1
            int r1 = (int) r3
            int r1 = r21 - r1
            if (r1 < 0) goto L77
            int r0 = r0.maxItemsInMainAxis
            if (r2 >= r0) goto L77
            goto L78
        L77:
            r6 = 0
        L78:
            r8.placeEllipsisOnLastContentLine = r6
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.FlowLayoutBuildingBlocks.getWrapEllipsisInfo(androidx.compose.foundation.layout.FlowLayoutBuildingBlocks$WrapInfo, boolean, int, int, int, int):androidx.compose.foundation.layout.FlowLayoutBuildingBlocks$WrapEllipsisInfo");
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x005c, code lost:
    
        if ((((int) (r25 >> 32)) - ((int) (r20 >> 32))) < 0) goto L23;
     */
    /* renamed from: getWrapInfo-OpUlnko, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final androidx.compose.foundation.layout.FlowLayoutBuildingBlocks.WrapInfo m103getWrapInfoOpUlnko(boolean r23, int r24, long r25, androidx.collection.IntIntPair r27, int r28, int r29, int r30, boolean r31, boolean r32) {
        /*
            Method dump skipped, instructions count: 255
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.FlowLayoutBuildingBlocks.m103getWrapInfoOpUlnko(boolean, int, long, androidx.collection.IntIntPair, int, int, int, boolean, boolean):androidx.compose.foundation.layout.FlowLayoutBuildingBlocks$WrapInfo");
    }

    private FlowLayoutBuildingBlocks(int i, FlowLayoutOverflowState flowLayoutOverflowState, long j, int i2, int i3, int i4) {
        this.maxItemsInMainAxis = i;
        this.overflow = flowLayoutOverflowState;
        this.constraints = j;
        this.maxLines = i2;
        this.mainAxisSpacing = i3;
        this.crossAxisSpacing = i4;
    }
}
