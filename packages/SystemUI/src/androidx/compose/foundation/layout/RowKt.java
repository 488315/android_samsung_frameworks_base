package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class RowKt {
    public static final RowMeasurePolicy DefaultRowMeasurePolicy;

    static {
        Arrangement.INSTANCE.getClass();
        Arrangement$Start$1 arrangement$Start$1 = Arrangement.Start;
        Alignment.Companion.getClass();
        DefaultRowMeasurePolicy = new RowMeasurePolicy(arrangement$Start$1, Alignment.Companion.Top);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final RowMeasurePolicy rowMeasurePolicy(Arrangement.Horizontal horizontal, BiasAlignment.Vertical vertical, Composer composer, int i) {
        RowMeasurePolicy rowMeasurePolicy;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.layout.rowMeasurePolicy (Row.kt:121)");
        }
        Arrangement.INSTANCE.getClass();
        if (Intrinsics.areEqual(horizontal, Arrangement.Start)) {
            Alignment.Companion.getClass();
            if (Intrinsics.areEqual(vertical, Alignment.Companion.Top)) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                composerImpl.startReplaceGroup(-848964613);
                composerImpl.end(false);
                rowMeasurePolicy = DefaultRowMeasurePolicy;
            } else {
                ComposerImpl composerImpl2 = (ComposerImpl) composer;
                composerImpl2.startReplaceGroup(-848913742);
                boolean z = true;
                boolean z2 = (((i & 14) ^ 6) > 4 && composerImpl2.changed(horizontal)) || (i & 6) == 4;
                if ((((i & 112) ^ 48) <= 32 || !composerImpl2.changed(vertical)) && (i & 48) != 32) {
                    z = false;
                }
                boolean z3 = z2 | z;
                Object objRememberedValue = composerImpl2.rememberedValue();
                if (!z3) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new RowMeasurePolicy(horizontal, vertical);
                        composerImpl2.updateRememberedValue(objRememberedValue);
                    }
                    rowMeasurePolicy = (RowMeasurePolicy) objRememberedValue;
                    composerImpl2.end(false);
                }
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return rowMeasurePolicy;
    }
}
