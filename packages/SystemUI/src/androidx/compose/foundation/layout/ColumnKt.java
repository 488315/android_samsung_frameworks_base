package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.Alignment;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class ColumnKt {
    public static final ColumnMeasurePolicy DefaultColumnMeasurePolicy;

    static {
        Arrangement.INSTANCE.getClass();
        Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
        Alignment.Companion.getClass();
        DefaultColumnMeasurePolicy = new ColumnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final ColumnMeasurePolicy columnMeasurePolicy(Arrangement.Vertical vertical, Alignment.Horizontal horizontal, Composer composer, int i) {
        ColumnMeasurePolicy columnMeasurePolicy;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.layout.columnMeasurePolicy (Column.kt:108)");
        }
        Arrangement.INSTANCE.getClass();
        if (Intrinsics.areEqual(vertical, Arrangement.Top)) {
            Alignment.Companion.getClass();
            if (Intrinsics.areEqual(horizontal, Alignment.Companion.Start)) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                composerImpl.startReplaceGroup(346089448);
                composerImpl.end(false);
                columnMeasurePolicy = DefaultColumnMeasurePolicy;
            } else {
                ComposerImpl composerImpl2 = (ComposerImpl) composer;
                composerImpl2.startReplaceGroup(346143295);
                boolean z = true;
                boolean z2 = (((i & 14) ^ 6) > 4 && composerImpl2.changed(vertical)) || (i & 6) == 4;
                if ((((i & 112) ^ 48) <= 32 || !composerImpl2.changed(horizontal)) && (i & 48) != 32) {
                    z = false;
                }
                boolean z3 = z2 | z;
                Object objRememberedValue = composerImpl2.rememberedValue();
                if (!z3) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new ColumnMeasurePolicy(vertical, horizontal);
                        composerImpl2.updateRememberedValue(objRememberedValue);
                    }
                    columnMeasurePolicy = (ColumnMeasurePolicy) objRememberedValue;
                    composerImpl2.end(false);
                }
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return columnMeasurePolicy;
    }
}
