package androidx.compose.foundation.pager;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.snapping.SnapPosition;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import java.util.Map;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PagerStateKt {
    public static final float DefaultPositionThreshold;
    public static final PagerMeasureResult EmptyLayoutInfo;
    public static final PagerStateKt$UnitDensity$1 UnitDensity;

    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.compose.foundation.pager.PagerStateKt$UnitDensity$1] */
    static {
        Dp.Companion companion = Dp.Companion;
        DefaultPositionThreshold = 56;
        EmptyLayoutInfo = new PagerMeasureResult(EmptyList.INSTANCE, 0, 0, 0, Orientation.Horizontal, 0, 0, false, 0, null, null, 0.0f, 0, false, SnapPosition.Start.INSTANCE, new MeasureResult() { // from class: androidx.compose.foundation.pager.PagerStateKt$EmptyLayoutInfo$1
            public final Map alignmentLines = MapsKt__MapsKt.emptyMap();

            @Override // androidx.compose.ui.layout.MeasureResult
            public final Map getAlignmentLines() {
                return this.alignmentLines;
            }

            @Override // androidx.compose.ui.layout.MeasureResult
            public final int getHeight() {
                return 0;
            }

            @Override // androidx.compose.ui.layout.MeasureResult
            public final int getWidth() {
                return 0;
            }

            @Override // androidx.compose.ui.layout.MeasureResult
            public final void placeChildren() {
            }
        }, false, null, null, CoroutineScopeKt.CoroutineScope(EmptyCoroutineContext.INSTANCE), 393216, null);
        UnitDensity = new Density() { // from class: androidx.compose.foundation.pager.PagerStateKt$UnitDensity$1
            @Override // androidx.compose.ui.unit.Density
            public final float getDensity() {
                return 1.0f;
            }

            @Override // androidx.compose.ui.unit.FontScaling
            public final float getFontScale() {
                return 1.0f;
            }
        };
    }

    public static final long calculateNewMaxScrollOffset(PagerLayoutInfo pagerLayoutInfo, int i) {
        PagerMeasureResult pagerMeasureResult = (PagerMeasureResult) pagerLayoutInfo;
        int i2 = pagerMeasureResult.pageSpacing;
        long j = i * (pagerMeasureResult.pageSize + i2);
        int i3 = -pagerMeasureResult.viewportStartOffset;
        long j2 = ((j + i3) + pagerMeasureResult.afterContentPadding) - i2;
        int m179getViewportSizeYbymL2g = (int) (pagerMeasureResult.orientation == Orientation.Horizontal ? pagerMeasureResult.m179getViewportSizeYbymL2g() >> 32 : pagerMeasureResult.m179getViewportSizeYbymL2g() & 4294967295L);
        long coerceIn = j2 - (m179getViewportSizeYbymL2g - RangesKt___RangesKt.coerceIn(pagerMeasureResult.snapPosition.position(m179getViewportSizeYbymL2g, pagerMeasureResult.pageSize, i3, r1), 0, m179getViewportSizeYbymL2g));
        if (coerceIn < 0) {
            return 0L;
        }
        return coerceIn;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0035, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.foundation.pager.PagerState rememberPagerState(final kotlin.jvm.functions.Function0 r8, androidx.compose.runtime.Composer r9) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.foundation.pager.rememberPagerState (PagerState.kt:87)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            androidx.compose.foundation.pager.DefaultPagerState$Companion r2 = androidx.compose.foundation.pager.DefaultPagerState.Companion
            r2.getClass()
            androidx.compose.runtime.saveable.SaverKt$Saver$1 r2 = androidx.compose.foundation.pager.DefaultPagerState.Saver
            r3 = r9
            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
            r4 = 0
            boolean r3 = r3.changed(r4)
            r5 = r9
            androidx.compose.runtime.ComposerImpl r5 = (androidx.compose.runtime.ComposerImpl) r5
            boolean r5 = r5.changed(r8)
            r3 = r3 | r5
            r5 = r9
            androidx.compose.runtime.ComposerImpl r5 = (androidx.compose.runtime.ComposerImpl) r5
            java.lang.Object r9 = r5.rememberedValue()
            if (r3 != 0) goto L37
            androidx.compose.runtime.Composer$Companion r3 = androidx.compose.runtime.Composer.Companion
            r3.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
            if (r9 != r3) goto L3f
        L37:
            androidx.compose.foundation.pager.PagerStateKt$rememberPagerState$1$1 r9 = new androidx.compose.foundation.pager.PagerStateKt$rememberPagerState$1$1
            r9.<init>()
            r5.updateRememberedValue(r9)
        L3f:
            r4 = r9
            kotlin.jvm.functions.Function0 r4 = (kotlin.jvm.functions.Function0) r4
            r7 = 4
            r3 = 0
            r6 = 0
            java.lang.Object r9 = androidx.compose.runtime.saveable.RememberSaveableKt.rememberSaveable(r1, r2, r3, r4, r5, r6, r7)
            androidx.compose.foundation.pager.DefaultPagerState r9 = (androidx.compose.foundation.pager.DefaultPagerState) r9
            androidx.compose.runtime.MutableState r0 = r9.pageCountState
            androidx.compose.runtime.SnapshotMutableStateImpl r0 = (androidx.compose.runtime.SnapshotMutableStateImpl) r0
            r0.setValue(r8)
            boolean r8 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r8 == 0) goto L5b
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L5b:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.pager.PagerStateKt.rememberPagerState(kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer):androidx.compose.foundation.pager.PagerState");
    }
}
