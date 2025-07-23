package androidx.compose.material3;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material3.internal.FloatProducer;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.layout.AlignmentLineKt;
import androidx.compose.ui.layout.HorizontalAlignmentLine;
import androidx.compose.ui.layout.IntrinsicMeasurable;
import androidx.compose.ui.layout.IntrinsicMeasureScope;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.Constraints;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class TopAppBarMeasurePolicy implements MeasurePolicy {
    public final float height;
    public final FloatProducer scrolledOffset;
    public final int titleBottomPadding;
    public final Alignment.Horizontal titleHorizontalAlignment;
    public final Arrangement.Vertical titleVerticalArrangement;

    public /* synthetic */ TopAppBarMeasurePolicy(FloatProducer floatProducer, Arrangement.Vertical vertical, Alignment.Horizontal horizontal, int i, float f, DefaultConstructorMarker defaultConstructorMarker) {
        this(floatProducer, vertical, horizontal, i, f);
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        Integer valueOf;
        int mo51roundToPx0680j_4 = intrinsicMeasureScope.mo51roundToPx0680j_4(this.height);
        if (list.isEmpty()) {
            valueOf = null;
        } else {
            valueOf = Integer.valueOf(((IntrinsicMeasurable) list.get(0)).maxIntrinsicHeight(i));
            int i2 = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    Integer valueOf2 = Integer.valueOf(((IntrinsicMeasurable) list.get(i2)).maxIntrinsicHeight(i));
                    if (valueOf2.compareTo(valueOf) > 0) {
                        valueOf = valueOf2;
                    }
                    if (i2 == size) {
                        break;
                    }
                    i2++;
                }
            }
        }
        return Math.max(mo51roundToPx0680j_4, valueOf != null ? valueOf.intValue() : 0);
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        int size = list.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += ((IntrinsicMeasurable) list.get(i3)).maxIntrinsicWidth(i);
        }
        return i2;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(final MeasureScope measureScope, List list, final long j) {
        int m821getMaxWidthimpl;
        MeasureResult layout$1;
        final TopAppBarMeasurePolicy topAppBarMeasurePolicy = this;
        int size = list.size();
        final int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Measurable measurable = (Measurable) list.get(i2);
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable), "navigationIcon")) {
                final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(Constraints.m814copyZbe2FdA$default(j, 0, 0, 0, 0, 14));
                int size2 = list.size();
                int i3 = 0;
                while (i3 < size2) {
                    Measurable measurable2 = (Measurable) list.get(i3);
                    if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable2), "actionIcons")) {
                        final Placeable mo608measureBRTryo02 = measurable2.mo608measureBRTryo0(Constraints.m814copyZbe2FdA$default(j, 0, 0, 0, 0, 14));
                        if (Constraints.m821getMaxWidthimpl(j) == Integer.MAX_VALUE) {
                            m821getMaxWidthimpl = Constraints.m821getMaxWidthimpl(j);
                        } else {
                            m821getMaxWidthimpl = (Constraints.m821getMaxWidthimpl(j) - mo608measureBRTryo0.width) - mo608measureBRTryo02.width;
                            if (m821getMaxWidthimpl < 0) {
                                m821getMaxWidthimpl = 0;
                            }
                        }
                        int i4 = m821getMaxWidthimpl;
                        int size3 = list.size();
                        int i5 = 0;
                        while (i5 < size3) {
                            Measurable measurable3 = (Measurable) list.get(i5);
                            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable3), UniversalCredentialUtil.AGENT_TITLE)) {
                                final Placeable mo608measureBRTryo03 = measurable3.mo608measureBRTryo0(Constraints.m814copyZbe2FdA$default(j, 0, i4, 0, 0, 12));
                                HorizontalAlignmentLine horizontalAlignmentLine = AlignmentLineKt.LastBaseline;
                                final int i6 = mo608measureBRTryo03.get(horizontalAlignmentLine) != Integer.MIN_VALUE ? mo608measureBRTryo03.get(horizontalAlignmentLine) : 0;
                                float invoke = topAppBarMeasurePolicy.scrolledOffset.invoke();
                                int roundToInt = Float.isNaN(invoke) ? 0 : MathKt__MathJVMKt.roundToInt(invoke);
                                final int max = Math.max(measureScope.mo51roundToPx0680j_4(topAppBarMeasurePolicy.height), mo608measureBRTryo03.height);
                                if (Constraints.m820getMaxHeightimpl(j) == Integer.MAX_VALUE) {
                                    i = max;
                                } else {
                                    int i7 = roundToInt + max;
                                    if (i7 >= 0) {
                                        i = i7;
                                    }
                                }
                                layout$1 = measureScope.layout$1(Constraints.m821getMaxWidthimpl(j), i, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.TopAppBarMeasurePolicy$placeTopAppBar$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:11:0x006b  */
                                    /* JADX WARN: Removed duplicated region for block: B:7:0x0065  */
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                    */
                                    public final java.lang.Object mo779invoke(java.lang.Object r15) {
                                        /*
                                            r14 = this;
                                            androidx.compose.ui.layout.Placeable$PlacementScope r15 = (androidx.compose.ui.layout.Placeable.PlacementScope) r15
                                            androidx.compose.ui.layout.Placeable r0 = androidx.compose.ui.layout.Placeable.this
                                            int r1 = r2
                                            int r2 = r0.height
                                            int r1 = r1 - r2
                                            int r1 = r1 / 2
                                            r2 = 0
                                            androidx.compose.ui.layout.Placeable.PlacementScope.placeRelative$default(r15, r0, r2, r1)
                                            androidx.compose.ui.layout.Placeable r0 = r3
                                            androidx.compose.ui.layout.MeasureScope r1 = r7
                                            androidx.compose.ui.layout.Placeable r3 = androidx.compose.ui.layout.Placeable.this
                                            androidx.compose.ui.layout.Placeable r4 = r4
                                            androidx.compose.material3.TopAppBarMeasurePolicy r5 = r8
                                            long r6 = r5
                                            int r8 = r2
                                            int r9 = r9
                                            int r10 = r10
                                            float r11 = androidx.compose.material3.AppBarKt.TopAppBarTitleInset
                                            int r1 = r1.mo51roundToPx0680j_4(r11)
                                            int r3 = r3.width
                                            int r1 = java.lang.Math.max(r1, r3)
                                            int r3 = r4.width
                                            androidx.compose.ui.Alignment$Horizontal r4 = r5.titleHorizontalAlignment
                                            int r11 = r0.width
                                            int r12 = androidx.compose.ui.unit.Constraints.m821getMaxWidthimpl(r6)
                                            androidx.compose.ui.unit.LayoutDirection r13 = androidx.compose.ui.unit.LayoutDirection.Ltr
                                            int r4 = r4.align(r11, r12, r13)
                                            if (r4 >= r1) goto L42
                                            int r1 = r1 - r4
                                        L40:
                                            int r4 = r4 + r1
                                            goto L56
                                        L42:
                                            int r1 = r0.width
                                            int r1 = r1 + r4
                                            int r11 = androidx.compose.ui.unit.Constraints.m821getMaxWidthimpl(r6)
                                            int r11 = r11 - r3
                                            if (r1 <= r11) goto L56
                                            int r1 = androidx.compose.ui.unit.Constraints.m821getMaxWidthimpl(r6)
                                            int r1 = r1 - r3
                                            int r3 = r0.width
                                            int r3 = r3 + r4
                                            int r1 = r1 - r3
                                            goto L40
                                        L56:
                                            androidx.compose.foundation.layout.Arrangement r1 = androidx.compose.foundation.layout.Arrangement.INSTANCE
                                            r1.getClass()
                                            androidx.compose.foundation.layout.Arrangement$Center$1 r1 = androidx.compose.foundation.layout.Arrangement.Center
                                            androidx.compose.foundation.layout.Arrangement$Vertical r3 = r5.titleVerticalArrangement
                                            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r1)
                                            if (r1 == 0) goto L6b
                                            int r1 = r0.height
                                            int r8 = r8 - r1
                                            int r2 = r8 / 2
                                            goto L8d
                                        L6b:
                                            androidx.compose.foundation.layout.Arrangement$Bottom$1 r1 = androidx.compose.foundation.layout.Arrangement.Bottom
                                            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r1)
                                            if (r1 == 0) goto L8d
                                            int r1 = r5.titleBottomPadding
                                            if (r1 != 0) goto L7c
                                            int r1 = r0.height
                                        L79:
                                            int r2 = r8 - r1
                                            goto L8d
                                        L7c:
                                            int r3 = r0.height
                                            int r5 = r3 - r9
                                            int r1 = r1 - r5
                                            int r5 = r1 + r3
                                            if (r5 <= r10) goto L87
                                            int r5 = r5 - r10
                                            int r1 = r1 - r5
                                        L87:
                                            int r8 = r8 - r3
                                            int r1 = java.lang.Math.max(r2, r1)
                                            goto L79
                                        L8d:
                                            r1 = 0
                                            r15.placeRelative(r0, r4, r2, r1)
                                            androidx.compose.ui.layout.Placeable r0 = r4
                                            long r2 = r5
                                            int r2 = androidx.compose.ui.unit.Constraints.m821getMaxWidthimpl(r2)
                                            androidx.compose.ui.layout.Placeable r3 = r4
                                            int r4 = r3.width
                                            int r2 = r2 - r4
                                            int r14 = r2
                                            int r3 = r3.height
                                            int r14 = r14 - r3
                                            int r14 = r14 / 2
                                            r15.placeRelative(r0, r2, r14, r1)
                                            kotlin.Unit r14 = kotlin.Unit.INSTANCE
                                            return r14
                                        */
                                        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.TopAppBarMeasurePolicy$placeTopAppBar$1.mo779invoke(java.lang.Object):java.lang.Object");
                                    }
                                });
                                return layout$1;
                            }
                            i5++;
                            topAppBarMeasurePolicy = this;
                        }
                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                    }
                    i3++;
                    topAppBarMeasurePolicy = this;
                }
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
            i2++;
            topAppBarMeasurePolicy = this;
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicHeight(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        Integer valueOf;
        int mo51roundToPx0680j_4 = intrinsicMeasureScope.mo51roundToPx0680j_4(this.height);
        if (list.isEmpty()) {
            valueOf = null;
        } else {
            valueOf = Integer.valueOf(((IntrinsicMeasurable) list.get(0)).minIntrinsicHeight(i));
            int i2 = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    Integer valueOf2 = Integer.valueOf(((IntrinsicMeasurable) list.get(i2)).minIntrinsicHeight(i));
                    if (valueOf2.compareTo(valueOf) > 0) {
                        valueOf = valueOf2;
                    }
                    if (i2 == size) {
                        break;
                    }
                    i2++;
                }
            }
        }
        return Math.max(mo51roundToPx0680j_4, valueOf != null ? valueOf.intValue() : 0);
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        int size = list.size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            i2 += ((IntrinsicMeasurable) list.get(i3)).minIntrinsicWidth(i);
        }
        return i2;
    }

    private TopAppBarMeasurePolicy(FloatProducer floatProducer, Arrangement.Vertical vertical, Alignment.Horizontal horizontal, int i, float f) {
        this.scrolledOffset = floatProducer;
        this.titleVerticalArrangement = vertical;
        this.titleHorizontalAlignment = horizontal;
        this.titleBottomPadding = i;
        this.height = f;
    }
}
