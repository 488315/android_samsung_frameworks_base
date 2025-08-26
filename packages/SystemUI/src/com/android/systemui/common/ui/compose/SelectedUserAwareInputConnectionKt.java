package com.android.systemui.common.ui.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.PlatformTextInputInterceptor;
import androidx.compose.ui.platform.PlatformTextInputModifierNodeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class SelectedUserAwareInputConnectionKt {
    /* JADX WARN: Removed duplicated region for block: B:23:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SelectedUserAwareInputConnection(final int i, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1182961154);
        int i3 = (composerImpl.changed(i) ? 4 : 2) | i2;
        if ((i3 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.common.ui.compose.SelectedUserAwareInputConnection (SelectedUserAwareInputConnection.kt:49)");
            }
            composerImpl.startReplaceGroup(1655794033);
            boolean z = (i3 & 14) == 4;
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1(i);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                PlatformTextInputModifierNodeKt.InterceptPlatformTextInput((PlatformTextInputInterceptor) objRememberedValue, ComposableLambdaKt.rememberComposableLambda(1346161880, new Function2() { // from class: com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt.SelectedUserAwareInputConnection.2
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2) {
                        Composer composer2 = (Composer) obj;
                        if ((((Number) obj2).intValue() & 3) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.common.ui.compose.SelectedUserAwareInputConnection.<anonymous> (SelectedUserAwareInputConnection.kt:75)");
                                }
                                composableLambdaImpl.invoke(composer2, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 48);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(i, composableLambdaImpl, i2) { // from class: com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$$ExternalSyntheticLambda0
                public final /* synthetic */ int f$0;
                public final /* synthetic */ ComposableLambdaImpl f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                    ComposableLambdaImpl composableLambdaImpl2 = this.f$1;
                    SelectedUserAwareInputConnectionKt.SelectedUserAwareInputConnection(this.f$0, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
