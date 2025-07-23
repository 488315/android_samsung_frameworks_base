package androidx.compose.ui.layout;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.samsung.android.knox.EnterpriseContainerCallback;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SubcomposeLayoutKt {
    public static final SubcomposeLayoutKt$ReusedSlotId$1 ReusedSlotId = new Object() { // from class: androidx.compose.ui.layout.SubcomposeLayoutKt$ReusedSlotId$1
        public final String toString() {
            return "ReusedSlotId";
        }
    };

    public static final void SubcomposeLayout(final Modifier modifier, Function2 function2, Composer composer, final int i, final int i2) {
        int i3;
        final Function2 function22;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1298353104);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(function2) ? 32 : 16;
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 19) != 18)) {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            Modifier modifier2 = modifier;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.ui.layout.SubcomposeLayout (SubcomposeLayout.kt:84)");
            }
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new SubcomposeLayoutState();
                composerImpl.updateRememberedValue(rememberedValue);
            }
            function22 = function2;
            SubcomposeLayout((SubcomposeLayoutState) rememberedValue, modifier2, function22, composerImpl, (i3 << 3) & EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            modifier = modifier2;
        } else {
            function22 = function2;
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.ui.layout.SubcomposeLayoutKt$SubcomposeLayout$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SubcomposeLayoutKt.SubcomposeLayout(Modifier.this, function22, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00ec, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L61;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SubcomposeLayout(final androidx.compose.ui.layout.SubcomposeLayoutState r8, androidx.compose.ui.Modifier r9, final kotlin.jvm.functions.Function2 r10, androidx.compose.runtime.Composer r11, final int r12, final int r13) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.layout.SubcomposeLayoutKt.SubcomposeLayout(androidx.compose.ui.layout.SubcomposeLayoutState, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int, int):void");
    }
}
