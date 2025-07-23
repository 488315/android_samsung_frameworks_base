package androidx.compose.foundation.layout;

import androidx.compose.ui.Alignment;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ColumnKt {
    public static final ColumnMeasurePolicy DefaultColumnMeasurePolicy;

    static {
        Arrangement.INSTANCE.getClass();
        Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
        Alignment.Companion.getClass();
        DefaultColumnMeasurePolicy = new ColumnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0074, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L30;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x008a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.foundation.layout.ColumnMeasurePolicy columnMeasurePolicy(androidx.compose.foundation.layout.Arrangement.Vertical r5, androidx.compose.ui.Alignment.Horizontal r6, androidx.compose.runtime.Composer r7, int r8) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.foundation.layout.columnMeasurePolicy (Column.kt:108)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            androidx.compose.foundation.layout.Arrangement r0 = androidx.compose.foundation.layout.Arrangement.INSTANCE
            r0.getClass()
            androidx.compose.foundation.layout.Arrangement$Top$1 r0 = androidx.compose.foundation.layout.Arrangement.Top
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r0)
            r1 = 0
            if (r0 == 0) goto L34
            androidx.compose.ui.Alignment$Companion r0 = androidx.compose.ui.Alignment.Companion
            r0.getClass()
            androidx.compose.ui.BiasAlignment$Horizontal r0 = androidx.compose.ui.Alignment.Companion.Start
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r0)
            if (r0 == 0) goto L34
            androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
            r5 = 346089448(0x14a0e7e8, float:1.6247342E-26)
            r7.startReplaceGroup(r5)
            r7.end(r1)
            androidx.compose.foundation.layout.ColumnMeasurePolicy r5 = androidx.compose.foundation.layout.ColumnKt.DefaultColumnMeasurePolicy
            goto L84
        L34:
            androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
            r0 = 346143295(0x14a1ba3f, float:1.6330307E-26)
            r7.startReplaceGroup(r0)
            r0 = r8 & 14
            r0 = r0 ^ 6
            r2 = 1
            r3 = 4
            if (r0 <= r3) goto L4a
            boolean r0 = r7.changed(r5)
            if (r0 != 0) goto L4e
        L4a:
            r0 = r8 & 6
            if (r0 != r3) goto L50
        L4e:
            r0 = r2
            goto L51
        L50:
            r0 = r1
        L51:
            r3 = r8 & 112(0x70, float:1.57E-43)
            r3 = r3 ^ 48
            r4 = 32
            if (r3 <= r4) goto L5f
            boolean r3 = r7.changed(r6)
            if (r3 != 0) goto L65
        L5f:
            r8 = r8 & 48
            if (r8 != r4) goto L64
            goto L65
        L64:
            r2 = r1
        L65:
            r8 = r0 | r2
            java.lang.Object r0 = r7.rememberedValue()
            if (r8 != 0) goto L76
            androidx.compose.runtime.Composer$Companion r8 = androidx.compose.runtime.Composer.Companion
            r8.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r8 = androidx.compose.runtime.Composer.Companion.Empty
            if (r0 != r8) goto L7e
        L76:
            androidx.compose.foundation.layout.ColumnMeasurePolicy r0 = new androidx.compose.foundation.layout.ColumnMeasurePolicy
            r0.<init>(r5, r6)
            r7.updateRememberedValue(r0)
        L7e:
            r5 = r0
            androidx.compose.foundation.layout.ColumnMeasurePolicy r5 = (androidx.compose.foundation.layout.ColumnMeasurePolicy) r5
            r7.end(r1)
        L84:
            boolean r6 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r6 == 0) goto L8d
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L8d:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.ColumnKt.columnMeasurePolicy(androidx.compose.foundation.layout.Arrangement$Vertical, androidx.compose.ui.Alignment$Horizontal, androidx.compose.runtime.Composer, int):androidx.compose.foundation.layout.ColumnMeasurePolicy");
    }
}
