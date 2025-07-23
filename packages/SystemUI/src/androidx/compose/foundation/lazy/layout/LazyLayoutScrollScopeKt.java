package androidx.compose.foundation.lazy.layout;

import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.ex.knoxAI.KnoxAiManagerInternal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyLayoutScrollScopeKt {
    public static final float BoundDistance;
    public static final float MinimumDistance;
    public static final float TargetDistance;

    static {
        float f = KnoxAiManagerInternal.CONN_MAX_WAIT_TIME;
        Dp.Companion companion = Dp.Companion;
        TargetDistance = f;
        BoundDistance = 1500;
        MinimumDistance = 50;
    }

    public static final boolean access$animateScrollToItem$isOvershot(boolean z, LazyLayoutScrollScope lazyLayoutScrollScope, int i, int i2) {
        if (z) {
            if (lazyLayoutScrollScope.getFirstVisibleItemIndex() > i) {
                return true;
            }
            return lazyLayoutScrollScope.getFirstVisibleItemIndex() == i && lazyLayoutScrollScope.getFirstVisibleItemScrollOffset() > i2;
        }
        if (lazyLayoutScrollScope.getFirstVisibleItemIndex() < i) {
            return true;
        }
        return lazyLayoutScrollScope.getFirstVisibleItemIndex() == i && lazyLayoutScrollScope.getFirstVisibleItemScrollOffset() < i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00d4 A[Catch: ItemFoundInScroll -> 0x01bb, TryCatch #5 {ItemFoundInScroll -> 0x01bb, blocks: (B:26:0x00d0, B:28:0x00d4, B:30:0x00da, B:38:0x0104, B:41:0x0133), top: B:25:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0208  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0077  */
    /* JADX WARN: Type inference failed for: r11v0, types: [T, androidx.compose.animation.core.AnimationState] */
    /* JADX WARN: Type inference failed for: r12v6, types: [T, androidx.compose.animation.core.AnimationState] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:54:0x018b -> B:21:0x006c). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object animateScrollToItem(androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope r28, int r29, int r30, int r31, androidx.compose.ui.unit.Density r32, kotlin.coroutines.jvm.internal.ContinuationImpl r33) {
        /*
            Method dump skipped, instructions count: 564
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.LazyLayoutScrollScopeKt.animateScrollToItem(androidx.compose.foundation.lazy.layout.LazyLayoutScrollScope, int, int, int, androidx.compose.ui.unit.Density, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static final boolean isItemVisible(LazyLayoutScrollScope lazyLayoutScrollScope, int i) {
        return i <= lazyLayoutScrollScope.getLastVisibleItemIndex() && lazyLayoutScrollScope.getFirstVisibleItemIndex() <= i;
    }
}
