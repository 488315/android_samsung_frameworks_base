package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ScaffoldKt;
import androidx.compose.material3.SnackbarData;
import androidx.compose.material3.SnackbarHostKt;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class SnackbarScaffoldKt {
    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$SnackbarScaffold$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$SnackbarScaffold$2, kotlin.jvm.internal.Lambda] */
    public static final void SnackbarScaffold(final Function3 function3, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(921324581);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changedInstance(function3) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            composerImpl2.startReplaceGroup(357124448);
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = new SnackbarHostState();
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            final SnackbarHostState snackbarHostState = (SnackbarHostState) rememberedValue;
            composerImpl2.end(false);
            final SnackbarData snackbarData = (SnackbarData) snackbarHostState.currentSnackbarData$delegate.getValue();
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(Modifier.Companion, 1.0f);
            ComposableLambdaImpl rememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(2113739551, composerImpl2, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$SnackbarScaffold$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 11) == 2) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        if (composerImpl3.getSkipping()) {
                            composerImpl3.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                    SnackbarHostState snackbarHostState2 = SnackbarHostState.this;
                    ComposableSingletons$SnackbarScaffoldKt.INSTANCE.getClass();
                    SnackbarHostKt.SnackbarHost(snackbarHostState2, null, ComposableSingletons$SnackbarScaffoldKt.f71lambda1, composer2, 390, 2);
                    return Unit.INSTANCE;
                }
            });
            Color.Companion.getClass();
            composerImpl = composerImpl2;
            ScaffoldKt.m232ScaffoldTvnljyQ(fillMaxWidth, null, null, rememberComposableLambda, null, 0, Color.Transparent, 0L, null, ComposableLambdaKt.rememberComposableLambda(-476988426, composerImpl2, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$SnackbarScaffold$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                /* JADX WARN: Code restructure failed: missing block: B:21:0x0071, code lost:
                
                    if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L22;
                 */
                /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$SnackbarScaffold$2$1, kotlin.jvm.internal.Lambda] */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r3, java.lang.Object r4, java.lang.Object r5) {
                    /*
                        r2 = this;
                        androidx.compose.foundation.layout.PaddingValues r3 = (androidx.compose.foundation.layout.PaddingValues) r3
                        androidx.compose.runtime.Composer r4 = (androidx.compose.runtime.Composer) r4
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        r0 = r5 & 14
                        if (r0 != 0) goto L1b
                        r0 = r4
                        androidx.compose.runtime.ComposerImpl r0 = (androidx.compose.runtime.ComposerImpl) r0
                        boolean r0 = r0.changed(r3)
                        if (r0 == 0) goto L19
                        r0 = 4
                        goto L1a
                    L19:
                        r0 = 2
                    L1a:
                        r5 = r5 | r0
                    L1b:
                        r5 = r5 & 91
                        r0 = 18
                        if (r5 != r0) goto L2f
                        r5 = r4
                        androidx.compose.runtime.ComposerImpl r5 = (androidx.compose.runtime.ComposerImpl) r5
                        boolean r0 = r5.getSkipping()
                        if (r0 != 0) goto L2b
                        goto L2f
                    L2b:
                        r5.skipToGroupEnd()
                        goto L8a
                    L2f:
                        androidx.compose.runtime.OpaqueKey r5 = androidx.compose.runtime.ComposerKt.invocation
                        androidx.compose.runtime.StaticProvidableCompositionLocal r5 = com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt.LocalSnackbarHostState
                        androidx.compose.material3.SnackbarHostState r0 = androidx.compose.material3.SnackbarHostState.this
                        androidx.compose.runtime.ProvidedValue r5 = r5.defaultProvidedValue$runtime_release(r0)
                        com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$SnackbarScaffold$2$1 r0 = new com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$SnackbarScaffold$2$1
                        kotlin.jvm.functions.Function3 r1 = r3
                        r0.<init>()
                        r3 = -1510616906(0xffffffffa5f5d0b6, float:-4.2642153E-16)
                        androidx.compose.runtime.internal.ComposableLambdaImpl r3 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r3, r4, r0)
                        r0 = 56
                        androidx.compose.runtime.CompositionLocalKt.CompositionLocalProvider(r5, r3, r4, r0)
                        androidx.compose.material3.SnackbarData r2 = r2
                        if (r2 != 0) goto L51
                        goto L8a
                    L51:
                        androidx.compose.ui.Modifier$Companion r3 = androidx.compose.ui.Modifier.Companion
                        androidx.compose.foundation.layout.FillElement r5 = androidx.compose.foundation.layout.SizeKt.FillWholeMaxSize
                        r3.then(r5)
                        androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                        r3 = -1019691543(0xffffffffc338bde9, float:-184.74184)
                        r4.startReplaceGroup(r3)
                        boolean r3 = r4.changed(r2)
                        java.lang.Object r0 = r4.rememberedValue()
                        if (r3 != 0) goto L73
                        androidx.compose.runtime.Composer$Companion r3 = androidx.compose.runtime.Composer.Companion
                        r3.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r0 != r3) goto L7b
                    L73:
                        com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$SnackbarScaffold$2$2$1$1 r0 = new com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$SnackbarScaffold$2$2$1$1
                        r0.<init>()
                        r4.updateRememberedValue(r0)
                    L7b:
                        kotlin.jvm.functions.Function0 r0 = (kotlin.jvm.functions.Function0) r0
                        r2 = 0
                        r4.end(r2)
                        r3 = 0
                        r1 = 7
                        androidx.compose.ui.Modifier r3 = androidx.compose.foundation.ClickableKt.m31clickableXHw0xAI$default(r5, r2, r3, r0, r1)
                        androidx.compose.foundation.layout.BoxKt.Box(r3, r4, r2)
                    L8a:
                        kotlin.Unit r2 = kotlin.Unit.INSTANCE
                        return r2
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$SnackbarScaffold$2.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }
            }), composerImpl2, 806882310, 438);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$SnackbarScaffold$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SnackbarScaffoldKt.SnackbarScaffold(Function3.this, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
