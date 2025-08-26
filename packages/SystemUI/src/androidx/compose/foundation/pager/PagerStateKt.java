package androidx.compose.foundation.pager;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.snapping.SnapPosition;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import java.util.Map;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScopeKt;

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
        int iM180getViewportSizeYbymL2g = (int) (pagerMeasureResult.orientation == Orientation.Horizontal ? pagerMeasureResult.m180getViewportSizeYbymL2g() >> 32 : pagerMeasureResult.m180getViewportSizeYbymL2g() & 4294967295L);
        long jCoerceIn = j2 - (iM180getViewportSizeYbymL2g - RangesKt___RangesKt.coerceIn(pagerMeasureResult.snapPosition.position(iM180getViewportSizeYbymL2g, pagerMeasureResult.pageSize, i3, r1), 0, iM180getViewportSizeYbymL2g));
        if (jCoerceIn < 0) {
            return 0L;
        }
        return jCoerceIn;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final PagerState rememberPagerState(final Function0 function0, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.pager.rememberPagerState (PagerState.kt:87)");
        }
        final int i = 0;
        Object[] objArr = new Object[0];
        DefaultPagerState.Companion.getClass();
        SaverKt$Saver$1 saverKt$Saver$1 = DefaultPagerState.Saver;
        final float f = 0.0f;
        boolean zChanged = ((ComposerImpl) composer).changed(0.0f) | ((ComposerImpl) composer).changed(function0);
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new Function0() { // from class: androidx.compose.foundation.pager.PagerStateKt$rememberPagerState$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new DefaultPagerState(i, f, function0);
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        DefaultPagerState defaultPagerState = (DefaultPagerState) RememberSaveableKt.rememberSaveable(objArr, saverKt$Saver$1, null, (Function0) objRememberedValue, composerImpl, 0, 4);
        ((SnapshotMutableStateImpl) defaultPagerState.pageCountState).setValue(function0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return defaultPagerState;
    }
}
