package com.android.systemui.bouncer.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.bouncer.ui.BouncerDialogFactory;
import com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BouncerContainerKt {
    public static final void BouncerContainer(final BouncerOverlayContentViewModel.Factory factory, final BouncerDialogFactory bouncerDialogFactory, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1707193382);
        if ((((composerImpl.changed(factory) ? 4 : 2) | i | (composerImpl.changed(bouncerDialogFactory) ? 32 : 16)) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.bouncer.ui.composable.BouncerContainer (BouncerContainer.kt:38)");
            }
            PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(-1633209008, new Function2() { // from class: com.android.systemui.bouncer.ui.composable.BouncerContainerKt$BouncerContainer$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0054, code lost:
                
                    if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:28:0x00e3, code lost:
                
                    if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L31;
                 */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r12, java.lang.Object r13) {
                    /*
                        Method dump skipped, instructions count: 287
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.composable.BouncerContainerKt$BouncerContainer$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }, composerImpl), composerImpl, 48, 1);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(bouncerDialogFactory, i) { // from class: com.android.systemui.bouncer.ui.composable.BouncerContainerKt$$ExternalSyntheticLambda0
                public final /* synthetic */ BouncerDialogFactory f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    BouncerContainerKt.BouncerContainer(BouncerOverlayContentViewModel.Factory.this, this.f$1, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
