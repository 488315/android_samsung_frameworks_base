package com.android.systemui.qs.panels.ui.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.Modifier;
import com.android.systemui.qs.panels.ui.compose.infinitegrid.InfiniteGridLayout;
import com.android.systemui.qs.panels.ui.viewmodel.PaginatedGridViewModel;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PaginatedGridLayout implements GridLayout {
    public final PaginatableGridLayout delegateGridLayout;
    public final PaginatedGridViewModel.Factory viewModelFactory;

    public PaginatedGridLayout(PaginatedGridViewModel.Factory factory, PaginatableGridLayout paginatableGridLayout) {
        this.viewModelFactory = factory;
        this.delegateGridLayout = paginatableGridLayout;
    }

    @Override // com.android.systemui.qs.panels.ui.compose.GridLayout
    public final void EditTileGrid(List list, Modifier.Companion companion, Function2 function2, Function1 function1, Function1 function12, Function0 function0, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1967745697);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.PaginatedGridLayout.EditTileGrid (PaginatedGridLayout.kt:0)");
        }
        ((InfiniteGridLayout) this.delegateGridLayout).EditTileGrid(list, companion, function2, function1, function12, function0, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0048, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x01b1, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x01f3, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0220, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x02c8, code lost:
    
        if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x02f1, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L98;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x01ee  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0360  */
    @Override // com.android.systemui.qs.panels.ui.compose.GridLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void TileGrid(final com.android.compose.animation.scene.ContentScope r27, java.util.List r28, final kotlin.jvm.functions.Function0 r29, androidx.compose.runtime.Composer r30, int r31) {
        /*
            Method dump skipped, instructions count: 868
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.panels.ui.compose.PaginatedGridLayout.TileGrid(com.android.compose.animation.scene.ContentScope, java.util.List, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int):void");
    }
}
