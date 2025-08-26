package androidx.compose.material3;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
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
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

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
        Integer numValueOf;
        int iMo52roundToPx0680j_4 = intrinsicMeasureScope.mo52roundToPx0680j_4(this.height);
        if (list.isEmpty()) {
            numValueOf = null;
        } else {
            numValueOf = Integer.valueOf(((IntrinsicMeasurable) list.get(0)).maxIntrinsicHeight(i));
            int i2 = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    Integer numValueOf2 = Integer.valueOf(((IntrinsicMeasurable) list.get(i2)).maxIntrinsicHeight(i));
                    if (numValueOf2.compareTo(numValueOf) > 0) {
                        numValueOf = numValueOf2;
                    }
                    if (i2 == size) {
                        break;
                    }
                    i2++;
                }
            }
        }
        return Math.max(iMo52roundToPx0680j_4, numValueOf != null ? numValueOf.intValue() : 0);
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int maxIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        int size = list.size();
        int iMaxIntrinsicWidth = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iMaxIntrinsicWidth += ((IntrinsicMeasurable) list.get(i2)).maxIntrinsicWidth(i);
        }
        return iMaxIntrinsicWidth;
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo3measure3p2s80s(final MeasureScope measureScope, List list, final long j) {
        int iM823getMaxWidthimpl;
        final TopAppBarMeasurePolicy topAppBarMeasurePolicy = this;
        int size = list.size();
        final int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Measurable measurable = (Measurable) list.get(i2);
            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable), "navigationIcon")) {
                final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(j, 0, 0, 0, 0, 14));
                int size2 = list.size();
                int i3 = 0;
                while (i3 < size2) {
                    Measurable measurable2 = (Measurable) list.get(i3);
                    if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable2), "actionIcons")) {
                        final Placeable placeableMo610measureBRTryo02 = measurable2.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(j, 0, 0, 0, 0, 14));
                        if (Constraints.m823getMaxWidthimpl(j) == Integer.MAX_VALUE) {
                            iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(j);
                        } else {
                            iM823getMaxWidthimpl = (Constraints.m823getMaxWidthimpl(j) - placeableMo610measureBRTryo0.width) - placeableMo610measureBRTryo02.width;
                            if (iM823getMaxWidthimpl < 0) {
                                iM823getMaxWidthimpl = 0;
                            }
                        }
                        int i4 = iM823getMaxWidthimpl;
                        int size3 = list.size();
                        int i5 = 0;
                        while (i5 < size3) {
                            Measurable measurable3 = (Measurable) list.get(i5);
                            if (Intrinsics.areEqual(LayoutIdKt.getLayoutId(measurable3), UniversalCredentialUtil.AGENT_TITLE)) {
                                final Placeable placeableMo610measureBRTryo03 = measurable3.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(j, 0, i4, 0, 0, 12));
                                HorizontalAlignmentLine horizontalAlignmentLine = AlignmentLineKt.LastBaseline;
                                final int i6 = placeableMo610measureBRTryo03.get(horizontalAlignmentLine) != Integer.MIN_VALUE ? placeableMo610measureBRTryo03.get(horizontalAlignmentLine) : 0;
                                float fInvoke = topAppBarMeasurePolicy.scrolledOffset.invoke();
                                int iRoundToInt = Float.isNaN(fInvoke) ? 0 : MathKt__MathJVMKt.roundToInt(fInvoke);
                                final int iMax = Math.max(measureScope.mo52roundToPx0680j_4(topAppBarMeasurePolicy.height), placeableMo610measureBRTryo03.height);
                                if (Constraints.m822getMaxHeightimpl(j) == Integer.MAX_VALUE) {
                                    i = iMax;
                                } else {
                                    int i7 = iRoundToInt + iMax;
                                    if (i7 >= 0) {
                                        i = i7;
                                    }
                                }
                                return measureScope.layout$1(Constraints.m823getMaxWidthimpl(j), i, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.TopAppBarMeasurePolicy$placeTopAppBar$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:11:0x0065  */
                                    /* JADX WARN: Removed duplicated region for block: B:12:0x006b  */
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object mo781invoke(Object obj) {
                                        int iM823getMaxWidthimpl2;
                                        Arrangement$Center$1 arrangement$Center$1;
                                        Arrangement.Vertical vertical;
                                        int iMax2;
                                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                                        Placeable placeable = placeableMo610measureBRTryo0;
                                        int i8 = 0;
                                        placementScope.placeRelative(placeable, 0, (i - placeable.height) / 2, 0.0f);
                                        Placeable placeable2 = placeableMo610measureBRTryo03;
                                        MeasureScope measureScope2 = measureScope;
                                        Placeable placeable3 = placeableMo610measureBRTryo0;
                                        Placeable placeable4 = placeableMo610measureBRTryo02;
                                        TopAppBarMeasurePolicy topAppBarMeasurePolicy2 = topAppBarMeasurePolicy;
                                        long j2 = j;
                                        int i9 = i;
                                        int i10 = i6;
                                        int i11 = iMax;
                                        int iMax3 = Math.max(measureScope2.mo52roundToPx0680j_4(AppBarKt.TopAppBarTitleInset), placeable3.width);
                                        int i12 = placeable4.width;
                                        int iAlign = topAppBarMeasurePolicy2.titleHorizontalAlignment.align(placeable2.width, Constraints.m823getMaxWidthimpl(j2), LayoutDirection.Ltr);
                                        if (iAlign >= iMax3) {
                                            if (placeable2.width + iAlign > Constraints.m823getMaxWidthimpl(j2) - i12) {
                                                iM823getMaxWidthimpl2 = (Constraints.m823getMaxWidthimpl(j2) - i12) - (placeable2.width + iAlign);
                                            }
                                            Arrangement.INSTANCE.getClass();
                                            arrangement$Center$1 = Arrangement.Center;
                                            vertical = topAppBarMeasurePolicy2.titleVerticalArrangement;
                                            if (!Intrinsics.areEqual(vertical, arrangement$Center$1)) {
                                                i8 = (i9 - placeable2.height) / 2;
                                            } else if (Intrinsics.areEqual(vertical, Arrangement.Bottom)) {
                                                int i13 = topAppBarMeasurePolicy2.titleBottomPadding;
                                                if (i13 == 0) {
                                                    iMax2 = placeable2.height;
                                                } else {
                                                    int i14 = placeable2.height;
                                                    int i15 = i13 - (i14 - i10);
                                                    int i16 = i15 + i14;
                                                    if (i16 > i11) {
                                                        i15 -= i16 - i11;
                                                    }
                                                    i9 -= i14;
                                                    iMax2 = Math.max(0, i15);
                                                }
                                                i8 = i9 - iMax2;
                                            }
                                            placementScope.placeRelative(placeable2, iAlign, i8, 0.0f);
                                            Placeable placeable5 = placeableMo610measureBRTryo02;
                                            int iM823getMaxWidthimpl3 = Constraints.m823getMaxWidthimpl(j);
                                            Placeable placeable6 = placeableMo610measureBRTryo02;
                                            placementScope.placeRelative(placeable5, iM823getMaxWidthimpl3 - placeable6.width, (i - placeable6.height) / 2, 0.0f);
                                            return Unit.INSTANCE;
                                        }
                                        iM823getMaxWidthimpl2 = iMax3 - iAlign;
                                        iAlign += iM823getMaxWidthimpl2;
                                        Arrangement.INSTANCE.getClass();
                                        arrangement$Center$1 = Arrangement.Center;
                                        vertical = topAppBarMeasurePolicy2.titleVerticalArrangement;
                                        if (!Intrinsics.areEqual(vertical, arrangement$Center$1)) {
                                        }
                                        placementScope.placeRelative(placeable2, iAlign, i8, 0.0f);
                                        Placeable placeable52 = placeableMo610measureBRTryo02;
                                        int iM823getMaxWidthimpl32 = Constraints.m823getMaxWidthimpl(j);
                                        Placeable placeable62 = placeableMo610measureBRTryo02;
                                        placementScope.placeRelative(placeable52, iM823getMaxWidthimpl32 - placeable62.width, (i - placeable62.height) / 2, 0.0f);
                                        return Unit.INSTANCE;
                                    }
                                });
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
        Integer numValueOf;
        int iMo52roundToPx0680j_4 = intrinsicMeasureScope.mo52roundToPx0680j_4(this.height);
        if (list.isEmpty()) {
            numValueOf = null;
        } else {
            numValueOf = Integer.valueOf(((IntrinsicMeasurable) list.get(0)).minIntrinsicHeight(i));
            int i2 = 1;
            int size = list.size() - 1;
            if (1 <= size) {
                while (true) {
                    Integer numValueOf2 = Integer.valueOf(((IntrinsicMeasurable) list.get(i2)).minIntrinsicHeight(i));
                    if (numValueOf2.compareTo(numValueOf) > 0) {
                        numValueOf = numValueOf2;
                    }
                    if (i2 == size) {
                        break;
                    }
                    i2++;
                }
            }
        }
        return Math.max(iMo52roundToPx0680j_4, numValueOf != null ? numValueOf.intValue() : 0);
    }

    @Override // androidx.compose.ui.layout.MeasurePolicy
    public final int minIntrinsicWidth(IntrinsicMeasureScope intrinsicMeasureScope, List list, int i) {
        int size = list.size();
        int iMinIntrinsicWidth = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iMinIntrinsicWidth += ((IntrinsicMeasurable) list.get(i2)).minIntrinsicWidth(i);
        }
        return iMinIntrinsicWidth;
    }

    private TopAppBarMeasurePolicy(FloatProducer floatProducer, Arrangement.Vertical vertical, Alignment.Horizontal horizontal, int i, float f) {
        this.scrolledOffset = floatProducer;
        this.titleVerticalArrangement = vertical;
        this.titleHorizontalAlignment = horizontal;
        this.titleBottomPadding = i;
        this.height = f;
    }
}
