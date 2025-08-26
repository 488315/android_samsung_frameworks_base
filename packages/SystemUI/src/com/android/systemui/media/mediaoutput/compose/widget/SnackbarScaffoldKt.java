package com.android.systemui.media.mediaoutput.compose.widget;

import android.content.res.Resources;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.SnackbarData;
import androidx.compose.material3.SnackbarHostKt;
import androidx.compose.material3.SnackbarHostState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.samsung.sesl.compose.component.ScaffoldKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
public abstract class SnackbarScaffoldKt {
    /* JADX WARN: Removed duplicated region for block: B:15:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SnackbarScaffold(Modifier.Companion companion, ComposableLambdaImpl composableLambdaImpl, ComposableLambdaImpl composableLambdaImpl2, final ComposableLambdaImpl composableLambdaImpl3, Composer composer, final int i, final int i2) {
        ComposableLambdaImpl composableLambdaImpl4;
        int i3;
        ComposableLambdaImpl composableLambdaImpl5;
        ComposableLambdaImpl composableLambdaImpl6;
        final ComposableLambdaImpl composableLambdaImpl7;
        Object objRememberedValue;
        ComposableLambdaImpl composableLambdaImpl8;
        ComposerImpl composerImpl;
        final Modifier.Companion companion2;
        final ComposableLambdaImpl composableLambdaImpl9;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-219847807);
        int i4 = i | 6;
        int i5 = i2 & 2;
        if (i5 != 0) {
            i4 = i | 54;
        } else {
            if ((i & 48) == 0) {
                composableLambdaImpl4 = composableLambdaImpl;
                i4 |= composerImpl2.changedInstance(composableLambdaImpl4) ? 32 : 16;
            }
            i3 = i2 & 4;
            if (i3 != 0) {
                if ((i & 384) == 0) {
                    composableLambdaImpl5 = composableLambdaImpl2;
                    i4 |= composerImpl2.changedInstance(composableLambdaImpl5) ? 256 : 128;
                }
                if ((i4 & 1171) == 1170 && composerImpl2.getSkipping()) {
                    composerImpl2.skipToGroupEnd();
                    companion2 = companion;
                    composerImpl = composerImpl2;
                    composableLambdaImpl9 = composableLambdaImpl4;
                    composableLambdaImpl7 = composableLambdaImpl5;
                } else {
                    Modifier.Companion companion3 = Modifier.Companion;
                    if (i5 == 0) {
                        ComposableSingletons$SnackbarScaffoldKt.INSTANCE.getClass();
                        composableLambdaImpl6 = ComposableSingletons$SnackbarScaffoldKt.f87lambda1;
                    } else {
                        composableLambdaImpl6 = composableLambdaImpl4;
                    }
                    composableLambdaImpl7 = i3 == 0 ? null : composableLambdaImpl5;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffold (SnackbarScaffold.kt:30)");
                    }
                    composerImpl2.startReplaceGroup(-1814030185);
                    objRememberedValue = composerImpl2.rememberedValue();
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new SnackbarHostState();
                        composerImpl2.updateRememberedValue(objRememberedValue);
                    }
                    final SnackbarHostState snackbarHostState = (SnackbarHostState) objRememberedValue;
                    composerImpl2.end(false);
                    final SnackbarData snackbarData = (SnackbarData) ((SnapshotMutableStateImpl) snackbarHostState.currentSnackbarData$delegate).getValue();
                    Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion3, 1.0f);
                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-2124895918, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt.SnackbarScaffold.1
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2) throws Resources.NotFoundException {
                            Composer composer2 = (Composer) obj;
                            if ((((Number) obj2).intValue() & 3) == 2) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffold.<anonymous> (SnackbarScaffold.kt:38)");
                                    }
                                    ComposableSingletons$SnackbarScaffoldKt.INSTANCE.getClass();
                                    SnackbarHostKt.SnackbarHost(snackbarHostState, null, ComposableSingletons$SnackbarScaffoldKt.f88lambda2, composer2, 390, 2);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl2);
                    if (composableLambdaImpl7 != null) {
                        ComposableSingletons$SnackbarScaffoldKt.INSTANCE.getClass();
                        composableLambdaImpl8 = ComposableSingletons$SnackbarScaffoldKt.f89lambda3;
                    } else {
                        composableLambdaImpl8 = composableLambdaImpl7;
                    }
                    Color.Companion.getClass();
                    composerImpl = composerImpl2;
                    ComposableLambdaImpl composableLambdaImpl10 = composableLambdaImpl6;
                    ScaffoldKt.m3341SeslScaffold5k0As8s(modifierFillMaxWidth, composableLambdaImpl10, null, composableLambdaImplRememberComposableLambda, null, composableLambdaImpl8, 0, Color.Transparent, null, ComposableLambdaKt.rememberComposableLambda(-1633598269, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt.SnackbarScaffold.2
                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:25:0x007d  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            final PaddingValues paddingValues = (PaddingValues) obj;
                            Composer composer2 = (Composer) obj2;
                            int iIntValue = ((Number) obj3).intValue();
                            if ((iIntValue & 6) == 0) {
                                iIntValue |= ((ComposerImpl) composer2).changed(paddingValues) ? 4 : 2;
                            }
                            if ((iIntValue & 19) == 18) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffold.<anonymous> (SnackbarScaffold.kt:58)");
                                    }
                                    ProvidedValue providedValueDefaultProvidedValue$runtime_release = CompositionExtKt.LocalSnackbarHostState.defaultProvidedValue$runtime_release(snackbarHostState);
                                    final Function3 function3 = composableLambdaImpl3;
                                    CompositionLocalKt.CompositionLocalProvider(providedValueDefaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(-2046862973, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt.SnackbarScaffold.2.1
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj4, Object obj5) {
                                            Composer composer3 = (Composer) obj4;
                                            if ((((Number) obj5).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                if (composerImpl4.getSkipping()) {
                                                    composerImpl4.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffold.<anonymous>.<anonymous> (SnackbarScaffold.kt:59)");
                                                    }
                                                    function3.invoke(paddingValues, composer3, 0);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composer2), composer2, 56);
                                    final SnackbarData snackbarData2 = snackbarData;
                                    if (snackbarData2 != null) {
                                        Modifier modifierFillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                        composerImpl4.startReplaceGroup(-1545644763);
                                        boolean zChanged = composerImpl4.changed(snackbarData2);
                                        Object objRememberedValue2 = composerImpl4.rememberedValue();
                                        if (!zChanged) {
                                            Composer.Companion.getClass();
                                            if (objRememberedValue2 == Composer.Companion.Empty) {
                                                objRememberedValue2 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$SnackbarScaffold$2$$ExternalSyntheticLambda0
                                                    @Override // kotlin.jvm.functions.Function0
                                                    public final Object invoke() {
                                                        snackbarData2.dismiss();
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl4.updateRememberedValue(objRememberedValue2);
                                            }
                                            composerImpl4.end(false);
                                            BoxKt.Box(ClickableKt.m35clickableXHw0xAI$default(modifierFillMaxSize, false, null, (Function0) objRememberedValue2, 7), composerImpl4, 0);
                                        }
                                    }
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl2), composerImpl, (i4 & 112) | 12585984, 852);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    companion2 = companion3;
                    composableLambdaImpl9 = composableLambdaImpl10;
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            ComposableLambdaImpl composableLambdaImpl11 = composableLambdaImpl3;
                            SnackbarScaffoldKt.SnackbarScaffold(companion2, composableLambdaImpl9, composableLambdaImpl7, composableLambdaImpl11, (Composer) obj, iUpdateChangedFlags, i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i4 |= 384;
            composableLambdaImpl5 = composableLambdaImpl2;
            if ((i4 & 1171) == 1170) {
                Modifier.Companion companion32 = Modifier.Companion;
                if (i5 == 0) {
                }
                if (i3 == 0) {
                }
                if (ComposerKt.isTraceInProgress()) {
                }
                composerImpl2.startReplaceGroup(-1814030185);
                objRememberedValue = composerImpl2.rememberedValue();
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                }
                final SnackbarHostState snackbarHostState2 = (SnackbarHostState) objRememberedValue;
                composerImpl2.end(false);
                final SnackbarData snackbarData2 = (SnackbarData) ((SnapshotMutableStateImpl) snackbarHostState2.currentSnackbarData$delegate).getValue();
                Modifier modifierFillMaxWidth2 = SizeKt.fillMaxWidth(companion32, 1.0f);
                ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(-2124895918, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt.SnackbarScaffold.1
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2) throws Resources.NotFoundException {
                        Composer composer2 = (Composer) obj;
                        if ((((Number) obj2).intValue() & 3) == 2) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            if (composerImpl3.getSkipping()) {
                                composerImpl3.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffold.<anonymous> (SnackbarScaffold.kt:38)");
                                }
                                ComposableSingletons$SnackbarScaffoldKt.INSTANCE.getClass();
                                SnackbarHostKt.SnackbarHost(snackbarHostState2, null, ComposableSingletons$SnackbarScaffoldKt.f88lambda2, composer2, 390, 2);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2);
                if (composableLambdaImpl7 != null) {
                }
                Color.Companion.getClass();
                composerImpl = composerImpl2;
                ComposableLambdaImpl composableLambdaImpl102 = composableLambdaImpl6;
                ScaffoldKt.m3341SeslScaffold5k0As8s(modifierFillMaxWidth2, composableLambdaImpl102, null, composableLambdaImplRememberComposableLambda2, null, composableLambdaImpl8, 0, Color.Transparent, null, ComposableLambdaKt.rememberComposableLambda(-1633598269, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt.SnackbarScaffold.2
                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:25:0x007d  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        final PaddingValues paddingValues = (PaddingValues) obj;
                        Composer composer2 = (Composer) obj2;
                        int iIntValue = ((Number) obj3).intValue();
                        if ((iIntValue & 6) == 0) {
                            iIntValue |= ((ComposerImpl) composer2).changed(paddingValues) ? 4 : 2;
                        }
                        if ((iIntValue & 19) == 18) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            if (composerImpl3.getSkipping()) {
                                composerImpl3.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffold.<anonymous> (SnackbarScaffold.kt:58)");
                                }
                                ProvidedValue providedValueDefaultProvidedValue$runtime_release = CompositionExtKt.LocalSnackbarHostState.defaultProvidedValue$runtime_release(snackbarHostState2);
                                final Function3 function3 = composableLambdaImpl3;
                                CompositionLocalKt.CompositionLocalProvider(providedValueDefaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(-2046862973, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt.SnackbarScaffold.2.1
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj4, Object obj5) {
                                        Composer composer3 = (Composer) obj4;
                                        if ((((Number) obj5).intValue() & 3) == 2) {
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            if (composerImpl4.getSkipping()) {
                                                composerImpl4.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffold.<anonymous>.<anonymous> (SnackbarScaffold.kt:59)");
                                                }
                                                function3.invoke(paddingValues, composer3, 0);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composer2), composer2, 56);
                                final SnackbarData snackbarData22 = snackbarData2;
                                if (snackbarData22 != null) {
                                    Modifier modifierFillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    composerImpl4.startReplaceGroup(-1545644763);
                                    boolean zChanged = composerImpl4.changed(snackbarData22);
                                    Object objRememberedValue2 = composerImpl4.rememberedValue();
                                    if (!zChanged) {
                                        Composer.Companion.getClass();
                                        if (objRememberedValue2 == Composer.Companion.Empty) {
                                            objRememberedValue2 = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.SnackbarScaffoldKt$SnackbarScaffold$2$$ExternalSyntheticLambda0
                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    snackbarData22.dismiss();
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl4.updateRememberedValue(objRememberedValue2);
                                        }
                                        composerImpl4.end(false);
                                        BoxKt.Box(ClickableKt.m35clickableXHw0xAI$default(modifierFillMaxSize, false, null, (Function0) objRememberedValue2, 7), composerImpl4, 0);
                                    }
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2), composerImpl, (i4 & 112) | 12585984, 852);
                if (ComposerKt.isTraceInProgress()) {
                }
                companion2 = companion32;
                composableLambdaImpl9 = composableLambdaImpl102;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        composableLambdaImpl4 = composableLambdaImpl;
        i3 = i2 & 4;
        if (i3 != 0) {
        }
        composableLambdaImpl5 = composableLambdaImpl2;
        if ((i4 & 1171) == 1170) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }
}
