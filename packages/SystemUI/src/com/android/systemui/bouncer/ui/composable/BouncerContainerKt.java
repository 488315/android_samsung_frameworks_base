package com.android.systemui.bouncer.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import com.android.bouncer.ui.composable.SecBouncerContentKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.bouncer.ui.BouncerDialogFactory;
import com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel;
import com.android.systemui.compose.modifiers.SysuiTestTagKt;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

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
            PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(-1633209008, new Function2() { // from class: com.android.systemui.bouncer.ui.composable.BouncerContainerKt.BouncerContainer.1
                /* JADX WARN: Removed duplicated region for block: B:15:0x0056  */
                /* JADX WARN: Removed duplicated region for block: B:31:0x00e5  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
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
                                ComposerKt.traceEventStart("com.android.systemui.bouncer.ui.composable.BouncerContainer.<anonymous> (BouncerContainer.kt:40)");
                            }
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            composerImpl3.startReplaceGroup(65517697);
                            Color.Companion.getClass();
                            final long j = Color.Transparent;
                            composerImpl3.end(false);
                            composerImpl3.startReplaceGroup(65523005);
                            final BouncerOverlayContentViewModel.Factory factory2 = factory;
                            boolean zChangedInstance = composerImpl3.changedInstance(factory2);
                            Object objRememberedValue = composerImpl3.rememberedValue();
                            Composer.Companion companion = Composer.Companion;
                            if (!zChangedInstance) {
                                companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    objRememberedValue = new Function0() { // from class: com.android.systemui.bouncer.ui.composable.BouncerContainerKt$BouncerContainer$1$$ExternalSyntheticLambda0
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return factory2.create();
                                        }
                                    };
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                composerImpl3.end(false);
                                BouncerOverlayContentViewModel bouncerOverlayContentViewModel = (BouncerOverlayContentViewModel) SysUiViewModelKt.rememberViewModel("BouncerContainer", null, (Function0) objRememberedValue, composerImpl3, 6, 2);
                                Modifier.Companion companion2 = Modifier.Companion;
                                Alignment.Companion.getClass();
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, companion2);
                                ComposeUiNode.Companion.getClass();
                                Function0 function0 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl3.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl3.startReusableNode();
                                if (composerImpl3.inserting) {
                                    composerImpl3.createNode(function0);
                                } else {
                                    composerImpl3.useNode();
                                }
                                Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                                }
                                Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion2, 1.0f);
                                composerImpl3.startReplaceGroup(1825638948);
                                boolean zChanged = composerImpl3.changed(j);
                                Object objRememberedValue2 = composerImpl3.rememberedValue();
                                if (!zChanged) {
                                    companion.getClass();
                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                        objRememberedValue2 = new Function1() { // from class: com.android.systemui.bouncer.ui.composable.BouncerContainerKt$BouncerContainer$1$$ExternalSyntheticLambda1
                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj3) {
                                                DrawScope.m541drawRectnJ9OG0$default((DrawScope) obj3, j, 0L, 0L, 0.0f, null, null, 0, 126);
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl3.updateRememberedValue(objRememberedValue2);
                                    }
                                    composerImpl3.end(false);
                                    CanvasKt.Canvas(modifierFillMaxSize, (Function1) objRememberedValue2, composerImpl3, 6);
                                    composerImpl3.startReplaceGroup(760462407);
                                    SecBouncerContentKt.SecBouncerContent(bouncerOverlayContentViewModel, bouncerDialogFactory, SizeKt.fillMaxSize(SysuiTestTagKt.sysuiResTag(companion2, "bouncer_root"), 1.0f), composerImpl3, 384);
                                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl3, false, true)) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 48, 1);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(bouncerDialogFactory, i) { // from class: com.android.systemui.bouncer.ui.composable.BouncerContainerKt$$ExternalSyntheticLambda0
                public final /* synthetic */ BouncerDialogFactory f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    BouncerContainerKt.BouncerContainer(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
