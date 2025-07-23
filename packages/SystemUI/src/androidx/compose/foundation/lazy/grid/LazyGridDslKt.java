package androidx.compose.foundation.lazy.grid;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyGridDslKt {
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0293, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L206;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x031e  */
    /* JADX WARN: Removed duplicated region for block: B:116:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x018e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void LazyHorizontalGrid(final int r31, final int r32, final int r33, androidx.compose.foundation.OverscrollEffect r34, androidx.compose.foundation.gestures.FlingBehavior r35, androidx.compose.foundation.layout.Arrangement.Horizontal r36, androidx.compose.foundation.layout.Arrangement.Vertical r37, androidx.compose.foundation.layout.PaddingValues r38, final androidx.compose.foundation.lazy.grid.GridCells r39, androidx.compose.foundation.lazy.grid.LazyGridState r40, androidx.compose.runtime.Composer r41, androidx.compose.ui.Modifier r42, final kotlin.jvm.functions.Function1 r43, boolean r44, boolean r45) {
        /*
            Method dump skipped, instructions count: 810
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.grid.LazyGridDslKt.LazyHorizontalGrid(int, int, int, androidx.compose.foundation.OverscrollEffect, androidx.compose.foundation.gestures.FlingBehavior, androidx.compose.foundation.layout.Arrangement$Horizontal, androidx.compose.foundation.layout.Arrangement$Vertical, androidx.compose.foundation.layout.PaddingValues, androidx.compose.foundation.lazy.grid.GridCells, androidx.compose.foundation.lazy.grid.LazyGridState, androidx.compose.runtime.Composer, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function1, boolean, boolean):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:102:0x0291, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L205;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x031e  */
    /* JADX WARN: Removed duplicated region for block: B:116:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0308  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x018e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void LazyVerticalGrid(final int r31, final int r32, final int r33, androidx.compose.foundation.OverscrollEffect r34, androidx.compose.foundation.gestures.FlingBehavior r35, androidx.compose.foundation.layout.Arrangement.Horizontal r36, androidx.compose.foundation.layout.Arrangement.Vertical r37, androidx.compose.foundation.layout.PaddingValues r38, final androidx.compose.foundation.lazy.grid.GridCells r39, androidx.compose.foundation.lazy.grid.LazyGridState r40, androidx.compose.runtime.Composer r41, androidx.compose.ui.Modifier r42, final kotlin.jvm.functions.Function1 r43, boolean r44, boolean r45) {
        /*
            Method dump skipped, instructions count: 810
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.grid.LazyGridDslKt.LazyVerticalGrid(int, int, int, androidx.compose.foundation.OverscrollEffect, androidx.compose.foundation.gestures.FlingBehavior, androidx.compose.foundation.layout.Arrangement$Horizontal, androidx.compose.foundation.layout.Arrangement$Vertical, androidx.compose.foundation.layout.PaddingValues, androidx.compose.foundation.lazy.grid.GridCells, androidx.compose.foundation.lazy.grid.LazyGridState, androidx.compose.runtime.Composer, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function1, boolean, boolean):void");
    }
}
