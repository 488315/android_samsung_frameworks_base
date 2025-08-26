package androidx.compose.ui.node;

import androidx.compose.ui.layout.AlignmentLine;
import androidx.compose.ui.layout.ApproachIntrinsicsMeasureScope;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntSize;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class NodeMeasuringIntrinsics {
    public static final NodeMeasuringIntrinsics INSTANCE = new NodeMeasuringIntrinsics();

    public interface ApproachMeasureBlock {
        /* renamed from: measure-3p2s80s */
        MeasureResult mo607measure3p2s80s(ApproachIntrinsicsMeasureScope approachIntrinsicsMeasureScope, Measurable measurable, long j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final class DefaultIntrinsicMeasurable implements Measurable {
        public final IntrinsicMeasurable measurable;
        public final IntrinsicMinMax minMax;
        public final IntrinsicWidthHeight widthHeight;

        public DefaultIntrinsicMeasurable(IntrinsicMeasurable intrinsicMeasurable, IntrinsicMinMax intrinsicMinMax, IntrinsicWidthHeight intrinsicWidthHeight) {
            this.measurable = intrinsicMeasurable;
            this.minMax = intrinsicMinMax;
            this.widthHeight = intrinsicWidthHeight;
        }

        @Override // androidx.compose.ui.layout.IntrinsicMeasurable
        public final Object getParentData() {
            return this.measurable.getParentData();
        }

        @Override // androidx.compose.ui.layout.IntrinsicMeasurable
        public final int maxIntrinsicHeight(int i) {
            return this.measurable.maxIntrinsicHeight(i);
        }

        @Override // androidx.compose.ui.layout.IntrinsicMeasurable
        public final int maxIntrinsicWidth(int i) {
            return this.measurable.maxIntrinsicWidth(i);
        }

        @Override // androidx.compose.ui.layout.Measurable
        /* renamed from: measure-BRTryo0 */
        public final Placeable mo610measureBRTryo0(long j) {
            IntrinsicWidthHeight intrinsicWidthHeight = this.widthHeight;
            IntrinsicWidthHeight intrinsicWidthHeight2 = IntrinsicWidthHeight.Width;
            IntrinsicMinMax intrinsicMinMax = this.minMax;
            IntrinsicMeasurable intrinsicMeasurable = this.measurable;
            if (intrinsicWidthHeight == intrinsicWidthHeight2) {
                return new EmptyPlaceable(intrinsicMinMax == IntrinsicMinMax.Max ? intrinsicMeasurable.maxIntrinsicWidth(Constraints.m822getMaxHeightimpl(j)) : intrinsicMeasurable.minIntrinsicWidth(Constraints.m822getMaxHeightimpl(j)), Constraints.m818getHasBoundedHeightimpl(j) ? Constraints.m822getMaxHeightimpl(j) : 32767);
            }
            return new EmptyPlaceable(Constraints.m819getHasBoundedWidthimpl(j) ? Constraints.m823getMaxWidthimpl(j) : 32767, intrinsicMinMax == IntrinsicMinMax.Max ? intrinsicMeasurable.maxIntrinsicHeight(Constraints.m823getMaxWidthimpl(j)) : intrinsicMeasurable.minIntrinsicHeight(Constraints.m823getMaxWidthimpl(j)));
        }

        @Override // androidx.compose.ui.layout.IntrinsicMeasurable
        public final int minIntrinsicHeight(int i) {
            return this.measurable.minIntrinsicHeight(i);
        }

        @Override // androidx.compose.ui.layout.IntrinsicMeasurable
        public final int minIntrinsicWidth(int i) {
            return this.measurable.minIntrinsicWidth(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class IntrinsicMinMax {
        public static final /* synthetic */ IntrinsicMinMax[] $VALUES;
        public static final IntrinsicMinMax Max;
        public static final IntrinsicMinMax Min;

        static {
            IntrinsicMinMax intrinsicMinMax = new IntrinsicMinMax("Min", 0);
            Min = intrinsicMinMax;
            IntrinsicMinMax intrinsicMinMax2 = new IntrinsicMinMax("Max", 1);
            Max = intrinsicMinMax2;
            IntrinsicMinMax[] intrinsicMinMaxArr = {intrinsicMinMax, intrinsicMinMax2};
            $VALUES = intrinsicMinMaxArr;
            EnumEntriesKt.enumEntries(intrinsicMinMaxArr);
        }

        private IntrinsicMinMax(String str, int i) {
        }

        public static IntrinsicMinMax valueOf(String str) {
            return (IntrinsicMinMax) Enum.valueOf(IntrinsicMinMax.class, str);
        }

        public static IntrinsicMinMax[] values() {
            return (IntrinsicMinMax[]) $VALUES.clone();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class IntrinsicWidthHeight {
        public static final /* synthetic */ IntrinsicWidthHeight[] $VALUES;
        public static final IntrinsicWidthHeight Height;
        public static final IntrinsicWidthHeight Width;

        static {
            IntrinsicWidthHeight intrinsicWidthHeight = new IntrinsicWidthHeight("Width", 0);
            Width = intrinsicWidthHeight;
            IntrinsicWidthHeight intrinsicWidthHeight2 = new IntrinsicWidthHeight("Height", 1);
            Height = intrinsicWidthHeight2;
            IntrinsicWidthHeight[] intrinsicWidthHeightArr = {intrinsicWidthHeight, intrinsicWidthHeight2};
            $VALUES = intrinsicWidthHeightArr;
            EnumEntriesKt.enumEntries(intrinsicWidthHeightArr);
        }

        private IntrinsicWidthHeight(String str, int i) {
        }

        public static IntrinsicWidthHeight valueOf(String str) {
            return (IntrinsicWidthHeight) Enum.valueOf(IntrinsicWidthHeight.class, str);
        }

        public static IntrinsicWidthHeight[] values() {
            return (IntrinsicWidthHeight[]) $VALUES.clone();
        }
    }

    private NodeMeasuringIntrinsics() {
    }

    final class EmptyPlaceable extends Placeable {
        public EmptyPlaceable(int i, int i2) {
            IntSize.Companion companion = IntSize.Companion;
            m626setMeasuredSizeozmzZPI((i2 & 4294967295L) | (i << 32));
        }

        @Override // androidx.compose.ui.layout.Measured
        public final int get(AlignmentLine alignmentLine) {
            return Integer.MIN_VALUE;
        }

        @Override // androidx.compose.ui.layout.Placeable
        /* renamed from: placeAt-f8xVGno */
        public final void mo611placeAtf8xVGno(long j, float f, Function1 function1) {
        }
    }
}
