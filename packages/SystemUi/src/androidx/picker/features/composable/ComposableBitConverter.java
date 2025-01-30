package androidx.picker.features.composable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.Constants;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComposableBitConverter {
    public final Map cachedMapByComposableType;
    public final Map cachedMapByViewType;
    public final List[] frameInfo;
    public final ComposableStrategy frameStrategy;
    public final int maxBit;
    public final IntRange[] rangeList;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ComposableBitConverter(ComposableStrategy composableStrategy) {
        List<ComposableFrame> leftFrameList;
        this.frameStrategy = composableStrategy;
        List[] listArr = new List[4];
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                leftFrameList = this.frameStrategy.getLeftFrameList();
            } else if (i == 1) {
                leftFrameList = this.frameStrategy.getIconFrameList();
            } else if (i == 2) {
                leftFrameList = this.frameStrategy.getTitleFrameList();
            } else {
                if (i != 3) {
                    throw new RuntimeException("UnReachable");
                }
                leftFrameList = this.frameStrategy.getWidgetFrameList();
            }
            listArr[i] = leftFrameList;
        }
        this.frameInfo = listArr;
        this.cachedMapByViewType = new LinkedHashMap();
        this.cachedMapByComposableType = new LinkedHashMap();
        ArrayList arrayList = new ArrayList(listArr.length);
        int length = listArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int ceil = ((int) Math.ceil(Math.log(listArr[i2].size() + 1) / Constants.LN2)) + i3;
            arrayList.add(RangesKt___RangesKt.until(i3, ceil));
            i2++;
            i3 = ceil;
        }
        this.rangeList = (IntRange[]) arrayList.toArray(new IntRange[0]);
        this.maxBit = (1 << i3) - 1;
    }

    public final ComposableFrame decodeAsFrame(int i, int i2) {
        IntRange intRange = this.rangeList[i];
        int i3 = 1 << (intRange.last + 1);
        int i4 = intRange.first;
        int i5 = (i2 & (i3 - (1 << i4))) >> i4;
        if (i5 == 0) {
            return null;
        }
        return (ComposableFrame) this.frameInfo[i].get(i5 - 1);
    }
}
