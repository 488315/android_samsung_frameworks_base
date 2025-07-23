package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectorInfo;
import androidx.compose.ui.platform.ValueElementSequence;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PaddingKt {
    /* renamed from: PaddingValues-0680j_4, reason: not valid java name */
    public static final PaddingValuesImpl m119PaddingValues0680j_4(float f) {
        return new PaddingValuesImpl(f, f, f, f, null);
    }

    /* renamed from: PaddingValues-YgX7TsA, reason: not valid java name */
    public static final PaddingValuesImpl m120PaddingValuesYgX7TsA(float f, float f2) {
        return new PaddingValuesImpl(f, f2, f, f2, null);
    }

    /* renamed from: PaddingValues-YgX7TsA$default, reason: not valid java name */
    public static PaddingValuesImpl m121PaddingValuesYgX7TsA$default(float f, int i) {
        if ((i & 1) != 0) {
            f = 0;
            Dp.Companion companion = Dp.Companion;
        }
        Dp.Companion companion2 = Dp.Companion;
        return m120PaddingValuesYgX7TsA(f, 0);
    }

    /* renamed from: PaddingValues-a9UjIt4, reason: not valid java name */
    public static final PaddingValuesImpl m122PaddingValuesa9UjIt4(float f, float f2, float f3, float f4) {
        return new PaddingValuesImpl(f, f2, f3, f4, null);
    }

    /* renamed from: PaddingValues-a9UjIt4$default, reason: not valid java name */
    public static PaddingValuesImpl m123PaddingValuesa9UjIt4$default(float f, float f2, float f3, float f4, int i) {
        if ((i & 1) != 0) {
            f = 0;
            Dp.Companion companion = Dp.Companion;
        }
        if ((i & 2) != 0) {
            f2 = 0;
            Dp.Companion companion2 = Dp.Companion;
        }
        if ((i & 4) != 0) {
            f3 = 0;
            Dp.Companion companion3 = Dp.Companion;
        }
        if ((i & 8) != 0) {
            f4 = 0;
            Dp.Companion companion4 = Dp.Companion;
        }
        return m122PaddingValuesa9UjIt4(f, f2, f3, f4);
    }

    public static final float calculateEndPadding(PaddingValues paddingValues, LayoutDirection layoutDirection) {
        return layoutDirection == LayoutDirection.Ltr ? paddingValues.mo111calculateRightPaddingu2uoSUM(layoutDirection) : paddingValues.mo110calculateLeftPaddingu2uoSUM(layoutDirection);
    }

    public static final float calculateStartPadding(PaddingValues paddingValues, LayoutDirection layoutDirection) {
        return layoutDirection == LayoutDirection.Ltr ? paddingValues.mo110calculateLeftPaddingu2uoSUM(layoutDirection) : paddingValues.mo111calculateRightPaddingu2uoSUM(layoutDirection);
    }

    public static final Modifier padding(Modifier modifier, final PaddingValues paddingValues) {
        return modifier.then(new PaddingValuesElement(paddingValues, new Function1() { // from class: androidx.compose.foundation.layout.PaddingKt$padding$4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                InspectorInfo inspectorInfo = (InspectorInfo) obj;
                inspectorInfo.getClass();
                inspectorInfo.properties.set(PaddingValues.this, "paddingValues");
                return Unit.INSTANCE;
            }
        }));
    }

    /* renamed from: padding-3ABfNKs, reason: not valid java name */
    public static final Modifier m124padding3ABfNKs(Modifier modifier, final float f) {
        return modifier.then(new PaddingElement(f, f, f, f, true, new Function1() { // from class: androidx.compose.foundation.layout.PaddingKt$padding$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((InspectorInfo) obj).getClass();
                Dp.m835boximpl(f);
                return Unit.INSTANCE;
            }
        }, null));
    }

    /* renamed from: padding-VpY3zN4, reason: not valid java name */
    public static final Modifier m125paddingVpY3zN4(Modifier modifier, final float f, final float f2) {
        return modifier.then(new PaddingElement(f, f2, f, f2, true, new Function1() { // from class: androidx.compose.foundation.layout.PaddingKt$padding$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                InspectorInfo inspectorInfo = (InspectorInfo) obj;
                inspectorInfo.getClass();
                Dp m835boximpl = Dp.m835boximpl(f);
                ValueElementSequence valueElementSequence = inspectorInfo.properties;
                valueElementSequence.set(m835boximpl, "horizontal");
                valueElementSequence.set(Dp.m835boximpl(f2), "vertical");
                return Unit.INSTANCE;
            }
        }, null));
    }

    /* renamed from: padding-VpY3zN4$default, reason: not valid java name */
    public static Modifier m126paddingVpY3zN4$default(Modifier modifier, float f, float f2, int i) {
        if ((i & 1) != 0) {
            f = 0;
            Dp.Companion companion = Dp.Companion;
        }
        if ((i & 2) != 0) {
            f2 = 0;
            Dp.Companion companion2 = Dp.Companion;
        }
        return m125paddingVpY3zN4(modifier, f, f2);
    }

    /* renamed from: padding-qDBjuR0, reason: not valid java name */
    public static final Modifier m127paddingqDBjuR0(Modifier modifier, final float f, final float f2, final float f3, final float f4) {
        return modifier.then(new PaddingElement(f, f2, f3, f4, true, new Function1() { // from class: androidx.compose.foundation.layout.PaddingKt$padding$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                InspectorInfo inspectorInfo = (InspectorInfo) obj;
                inspectorInfo.getClass();
                Dp m835boximpl = Dp.m835boximpl(f);
                ValueElementSequence valueElementSequence = inspectorInfo.properties;
                valueElementSequence.set(m835boximpl, NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                valueElementSequence.set(Dp.m835boximpl(f2), "top");
                valueElementSequence.set(Dp.m835boximpl(f3), NetworkAnalyticsConstants.DataPoints.CLOSE_TIME);
                valueElementSequence.set(Dp.m835boximpl(f4), "bottom");
                return Unit.INSTANCE;
            }
        }, null));
    }

    /* renamed from: padding-qDBjuR0$default, reason: not valid java name */
    public static Modifier m128paddingqDBjuR0$default(Modifier modifier, float f, float f2, float f3, float f4, int i) {
        if ((i & 1) != 0) {
            f = 0;
            Dp.Companion companion = Dp.Companion;
        }
        if ((i & 2) != 0) {
            f2 = 0;
            Dp.Companion companion2 = Dp.Companion;
        }
        if ((i & 4) != 0) {
            f3 = 0;
            Dp.Companion companion3 = Dp.Companion;
        }
        if ((i & 8) != 0) {
            f4 = 0;
            Dp.Companion companion4 = Dp.Companion;
        }
        return m127paddingqDBjuR0(modifier, f, f2, f3, f4);
    }
}
