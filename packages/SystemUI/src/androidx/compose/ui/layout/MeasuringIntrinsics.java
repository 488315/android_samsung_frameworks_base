package androidx.compose.ui.layout;

import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntSize;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MeasuringIntrinsics {
    public static final MeasuringIntrinsics INSTANCE = new MeasuringIntrinsics();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class DefaultIntrinsicMeasurable implements Measurable {
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
        public final Placeable mo608measureBRTryo0(long j) {
            IntrinsicWidthHeight intrinsicWidthHeight = this.widthHeight;
            IntrinsicWidthHeight intrinsicWidthHeight2 = IntrinsicWidthHeight.Width;
            IntrinsicMinMax intrinsicMinMax = this.minMax;
            IntrinsicMeasurable intrinsicMeasurable = this.measurable;
            if (intrinsicWidthHeight == intrinsicWidthHeight2) {
                return new EmptyPlaceable(intrinsicMinMax == IntrinsicMinMax.Max ? intrinsicMeasurable.maxIntrinsicWidth(Constraints.m820getMaxHeightimpl(j)) : intrinsicMeasurable.minIntrinsicWidth(Constraints.m820getMaxHeightimpl(j)), Constraints.m816getHasBoundedHeightimpl(j) ? Constraints.m820getMaxHeightimpl(j) : 32767);
            }
            return new EmptyPlaceable(Constraints.m817getHasBoundedWidthimpl(j) ? Constraints.m821getMaxWidthimpl(j) : 32767, intrinsicMinMax == IntrinsicMinMax.Max ? intrinsicMeasurable.maxIntrinsicHeight(Constraints.m821getMaxWidthimpl(j)) : intrinsicMeasurable.minIntrinsicHeight(Constraints.m821getMaxWidthimpl(j)));
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

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class IntrinsicMinMax {
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

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class IntrinsicWidthHeight {
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

    private MeasuringIntrinsics() {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class EmptyPlaceable extends Placeable {
        public EmptyPlaceable(int i, int i2) {
            IntSize.Companion companion = IntSize.Companion;
            m624setMeasuredSizeozmzZPI((i2 & 4294967295L) | (i << 32));
        }

        @Override // androidx.compose.ui.layout.Measured
        public final int get(AlignmentLine alignmentLine) {
            return Integer.MIN_VALUE;
        }

        @Override // androidx.compose.ui.layout.Placeable
        /* renamed from: placeAt-f8xVGno */
        public final void mo609placeAtf8xVGno(long j, float f, Function1 function1) {
        }
    }
}
