package com.android.systemui.qs.panels.ui.compose;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Start$1;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.pager.PagerState;
import androidx.compose.foundation.shape.CornerSizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.common.ui.compose.PagerDotsKt;
import com.android.systemui.development.ui.compose.BuildNumberKt;
import com.android.systemui.development.ui.viewmodel.BuildNumberViewModel;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.android.systemui.qs.panels.ui.compose.toolbar.EditModeButtonKt;
import com.android.systemui.qs.panels.ui.viewmodel.toolbar.EditModeButtonViewModel;
import com.android.systemui.qs.ui.compose.BorderOnFocusKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class PaginatedGridLayoutKt {
    /* JADX WARN: Removed duplicated region for block: B:31:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void FooterBar(BuildNumberViewModel.Factory factory, PagerState pagerState, final EditModeButtonViewModel.Factory factory2, Composer composer, final int i) {
        final PagerState pagerState2;
        final BuildNumberViewModel.Factory factory3;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1139726295);
        int i2 = i | (composerImpl2.changed(factory) ? 4 : 2) | (composerImpl2.changed(pagerState) ? 32 : 16) | (composerImpl2.changed(factory2) ? 256 : 128);
        if ((i2 & 147) == 146 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            factory3 = factory;
            pagerState2 = pagerState;
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.panels.ui.compose.FooterBar (PaginatedGridLayout.kt:157)");
            }
            composerImpl2.startReplaceGroup(-1738860849);
            boolean z = (i2 & 896) == 256;
            Object objRememberedValue = composerImpl2.rememberedValue();
            if (!z) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function0() { // from class: com.android.systemui.qs.panels.ui.compose.PaginatedGridLayoutKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return factory2.create();
                        }
                    };
                    composerImpl2.updateRememberedValue(objRememberedValue);
                }
                composerImpl2.end(false);
                EditModeButtonViewModel editModeButtonViewModel = (EditModeButtonViewModel) SysUiViewModelKt.rememberViewModel("PaginatedGridLayout-editButtonViewModel", null, (Function0) objRememberedValue, composerImpl2, 6, 2);
                Modifier.Companion companion = Modifier.Companion;
                Dimensions.INSTANCE.getClass();
                Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(SizeKt.m134requiredHeight3ABfNKs(companion, Dimensions.FooterHeight), 1.0f);
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                Arrangement arrangement = Arrangement.INSTANCE;
                Dp.Companion companion2 = Dp.Companion;
                arrangement.getClass();
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.m92spacedBy0680j_4(8), vertical, composerImpl2, 54);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierFillMaxWidth);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (composerImpl2.applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl2.startReusableNode();
                if (composerImpl2.inserting) {
                    composerImpl2.createNode(function0);
                } else {
                    composerImpl2.useNode();
                }
                Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                Updater.m337setimpl(composerImpl2, rowMeasurePolicy, function2);
                Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function23);
                }
                Function2 function24 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, function24);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                Modifier modifierWeight = rowScopeInstance.weight(companion, 1.0f, true);
                Arrangement$Start$1 arrangement$Start$1 = Arrangement.Start;
                BiasAlignment.Vertical vertical2 = Alignment.Companion.Top;
                RowMeasurePolicy rowMeasurePolicy2 = RowKt.rowMeasurePolicy(arrangement$Start$1, vertical2, composerImpl2, 0);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl2, modifierWeight);
                composerImpl2.startReusableNode();
                if (composerImpl2.inserting) {
                    composerImpl2.createNode(function0);
                } else {
                    composerImpl2.useNode();
                }
                Updater.m337setimpl(composerImpl2, rowMeasurePolicy2, function2);
                Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function23);
                }
                Updater.m337setimpl(composerImpl2, modifierMaterializeModifier2, function24);
                MaterialTheme.INSTANCE.getClass();
                BuildNumberKt.m2567BuildNumber3IgeMak(factory, MaterialTheme.getColorScheme(composerImpl2).onSurface, SizeKt.wrapContentSize$default(BorderOnFocusKt.m2937borderOnFocusPOIbLQ4$default(companion, MaterialTheme.getColorScheme(composerImpl2).secondary, CornerSizeKt.m186CornerSize0680j_4(1)), null, 3), composerImpl2, i2 & 14);
                SpacerKt.Spacer(composerImpl2, rowScopeInstance.weight(companion, 1.0f, true));
                composerImpl2.end(true);
                pagerState2 = pagerState;
                factory3 = factory;
                PagerDotsKt.m1075PagerDotsLLiWm1Q(pagerState2, MaterialTheme.getColorScheme(composerImpl2).primary, MaterialTheme.getColorScheme(composerImpl2).surfaceVariant, SizeKt.wrapContentWidth$default(companion, null, 3), 0.0f, 0.0f, composerImpl2, ((i2 >> 3) & 14) | 3072);
                composerImpl = composerImpl2;
                Modifier modifierWeight2 = rowScopeInstance.weight(companion, 1.0f, true);
                RowMeasurePolicy rowMeasurePolicy3 = RowKt.rowMeasurePolicy(arrangement$Start$1, vertical2, composerImpl, 0);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierWeight2);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, rowMeasurePolicy3, function2);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function22);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function24);
                SpacerKt.Spacer(composerImpl, rowScopeInstance.weight(companion, 1.0f, true));
                EditModeButtonKt.EditModeButton(editModeButtonViewModel, null, composerImpl, 0);
                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, true, true)) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(pagerState2, factory2, i) { // from class: com.android.systemui.qs.panels.ui.compose.PaginatedGridLayoutKt$$ExternalSyntheticLambda1
                public final /* synthetic */ PagerState f$1;
                public final /* synthetic */ EditModeButtonViewModel.Factory f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    PagerState pagerState3 = this.f$1;
                    EditModeButtonViewModel.Factory factory4 = this.f$2;
                    PaginatedGridLayoutKt.FooterBar(this.f$0, pagerState3, factory4, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
