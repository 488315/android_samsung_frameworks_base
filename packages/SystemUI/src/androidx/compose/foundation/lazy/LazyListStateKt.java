package androidx.compose.foundation.lazy;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.DensityKt;
import java.util.Map;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyListStateKt {
    public static final LazyListMeasureResult EmptyLazyListMeasureResult = new LazyListMeasureResult(null, 0, false, 0.0f, new MeasureResult() { // from class: androidx.compose.foundation.lazy.LazyListStateKt$EmptyLazyListMeasureResult$1
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
    }, 0.0f, false, CoroutineScopeKt.CoroutineScope(EmptyCoroutineContext.INSTANCE), DensityKt.Density$default(1.0f), ConstraintsKt.Constraints$default(0, 0, 0, 0, 15), EmptyList.INSTANCE, 0, 0, 0, false, Orientation.Vertical, 0, 0, null);

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0034, code lost:
    
        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.foundation.lazy.LazyListState rememberLazyListState(androidx.compose.runtime.Composer r8) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.foundation.lazy.rememberLazyListState (LazyListState.kt:73)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            androidx.compose.foundation.lazy.LazyListState$Companion r2 = androidx.compose.foundation.lazy.LazyListState.Companion
            r2.getClass()
            androidx.compose.runtime.saveable.SaverKt$Saver$1 r2 = androidx.compose.foundation.lazy.LazyListState.Saver
            r3 = r8
            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
            boolean r3 = r3.changed(r0)
            r4 = r8
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            boolean r4 = r4.changed(r0)
            r3 = r3 | r4
            r5 = r8
            androidx.compose.runtime.ComposerImpl r5 = (androidx.compose.runtime.ComposerImpl) r5
            java.lang.Object r8 = r5.rememberedValue()
            if (r3 != 0) goto L36
            androidx.compose.runtime.Composer$Companion r3 = androidx.compose.runtime.Composer.Companion
            r3.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
            if (r8 != r3) goto L3e
        L36:
            androidx.compose.foundation.lazy.LazyListStateKt$rememberLazyListState$1$1 r8 = new androidx.compose.foundation.lazy.LazyListStateKt$rememberLazyListState$1$1
            r8.<init>()
            r5.updateRememberedValue(r8)
        L3e:
            r4 = r8
            kotlin.jvm.functions.Function0 r4 = (kotlin.jvm.functions.Function0) r4
            r7 = 4
            r3 = 0
            r6 = 0
            java.lang.Object r8 = androidx.compose.runtime.saveable.RememberSaveableKt.rememberSaveable(r1, r2, r3, r4, r5, r6, r7)
            androidx.compose.foundation.lazy.LazyListState r8 = (androidx.compose.foundation.lazy.LazyListState) r8
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L53
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L53:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.LazyListStateKt.rememberLazyListState(androidx.compose.runtime.Composer):androidx.compose.foundation.lazy.LazyListState");
    }
}
