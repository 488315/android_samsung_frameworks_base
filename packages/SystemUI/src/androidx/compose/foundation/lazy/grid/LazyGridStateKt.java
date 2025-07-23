package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.unit.DensityKt;
import java.util.Map;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyGridStateKt {
    public static final LazyGridMeasureResult EmptyLazyGridLayoutInfo;

    static {
        MeasureResult measureResult = new MeasureResult() { // from class: androidx.compose.foundation.lazy.grid.LazyGridStateKt$EmptyLazyGridLayoutInfo$1
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
        };
        EmptyList emptyList = EmptyList.INSTANCE;
        Orientation orientation = Orientation.Vertical;
        EmptyLazyGridLayoutInfo = new LazyGridMeasureResult(null, 0, false, 0.0f, measureResult, 0.0f, false, CoroutineScopeKt.CoroutineScope(EmptyCoroutineContext.INSTANCE), DensityKt.Density$default(1.0f), 0, new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridStateKt$EmptyLazyGridLayoutInfo$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                ((Number) obj).intValue();
                return EmptyList.INSTANCE;
            }
        }, emptyList, 0, 0, 0, false, orientation, 0, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x003e, code lost:
    
        if (r11 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.foundation.lazy.grid.LazyGridState rememberLazyGridState(final int r9, final int r10, androidx.compose.runtime.Composer r11, int r12) {
        /*
            r0 = r12 & 1
            r1 = 0
            if (r0 == 0) goto L6
            r9 = r1
        L6:
            r12 = r12 & 2
            if (r12 == 0) goto Lb
            r10 = r1
        Lb:
            boolean r12 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r12 == 0) goto L16
            java.lang.String r12 = "androidx.compose.foundation.lazy.grid.rememberLazyGridState (LazyGridState.kt:74)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r12)
        L16:
            java.lang.Object[] r2 = new java.lang.Object[r1]
            androidx.compose.foundation.lazy.grid.LazyGridState$Companion r12 = androidx.compose.foundation.lazy.grid.LazyGridState.Companion
            r12.getClass()
            androidx.compose.runtime.saveable.SaverKt$Saver$1 r3 = androidx.compose.foundation.lazy.grid.LazyGridState.Saver
            r12 = r11
            androidx.compose.runtime.ComposerImpl r12 = (androidx.compose.runtime.ComposerImpl) r12
            boolean r12 = r12.changed(r9)
            r0 = r11
            androidx.compose.runtime.ComposerImpl r0 = (androidx.compose.runtime.ComposerImpl) r0
            boolean r0 = r0.changed(r10)
            r12 = r12 | r0
            r6 = r11
            androidx.compose.runtime.ComposerImpl r6 = (androidx.compose.runtime.ComposerImpl) r6
            java.lang.Object r11 = r6.rememberedValue()
            if (r12 != 0) goto L40
            androidx.compose.runtime.Composer$Companion r12 = androidx.compose.runtime.Composer.Companion
            r12.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r12 = androidx.compose.runtime.Composer.Companion.Empty
            if (r11 != r12) goto L48
        L40:
            androidx.compose.foundation.lazy.grid.LazyGridStateKt$rememberLazyGridState$1$1 r11 = new androidx.compose.foundation.lazy.grid.LazyGridStateKt$rememberLazyGridState$1$1
            r11.<init>()
            r6.updateRememberedValue(r11)
        L48:
            r5 = r11
            kotlin.jvm.functions.Function0 r5 = (kotlin.jvm.functions.Function0) r5
            r8 = 4
            r4 = 0
            r7 = 0
            java.lang.Object r9 = androidx.compose.runtime.saveable.RememberSaveableKt.rememberSaveable(r2, r3, r4, r5, r6, r7, r8)
            androidx.compose.foundation.lazy.grid.LazyGridState r9 = (androidx.compose.foundation.lazy.grid.LazyGridState) r9
            boolean r10 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r10 == 0) goto L5d
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L5d:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.grid.LazyGridStateKt.rememberLazyGridState(int, int, androidx.compose.runtime.Composer, int):androidx.compose.foundation.lazy.grid.LazyGridState");
    }
}
