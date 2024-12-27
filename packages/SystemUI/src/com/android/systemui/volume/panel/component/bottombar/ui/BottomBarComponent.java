package com.android.systemui.volume.panel.component.bottombar.ui;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$SpaceBetween$1;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.volume.panel.component.bottombar.ui.viewmodel.BottomBarViewModel;
import com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent;
import com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

public final class BottomBarComponent implements ComposeVolumePanelUiComponent {
    public final BottomBarViewModel viewModel;

    public BottomBarComponent(BottomBarViewModel bottomBarViewModel) {
        this.viewModel = bottomBarViewModel;
    }

    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    public final void Content(final VolumePanelComposeScope volumePanelComposeScope, final Modifier modifier, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(695359912);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        float f = volumePanelComposeScope.state.isLargeScreen ? 54 : 48;
        Dp.Companion companion = Dp.Companion;
        Modifier fillMaxWidth = SizeKt.fillMaxWidth(SizeKt.m110heightInVpY3zN4$default(modifier, f, 0.0f, 2), 1.0f);
        Arrangement.INSTANCE.getClass();
        Arrangement$SpaceBetween$1 arrangement$SpaceBetween$1 = Arrangement.SpaceBetween;
        Alignment.Companion.getClass();
        RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$SpaceBetween$1, Alignment.Companion.CenterVertically, composerImpl, 54);
        int i2 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, fillMaxWidth);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m276setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
        BottomBarViewModel bottomBarViewModel = this.viewModel;
        BottomBarComponent$Content$1$1 bottomBarComponent$Content$1$1 = new BottomBarComponent$Content$1$1(bottomBarViewModel);
        ButtonDefaults buttonDefaults = ButtonDefaults.INSTANCE;
        MaterialTheme.INSTANCE.getClass();
        long j = MaterialTheme.getColorScheme(composerImpl).onSurface;
        buttonDefaults.getClass();
        ButtonColors m210outlinedButtonColorsro_MJ88 = ButtonDefaults.m210outlinedButtonColorsro_MJ88(j, composerImpl);
        ComposableSingletons$BottomBarComponentKt.INSTANCE.getClass();
        ButtonKt.OutlinedButton(bottomBarComponent$Content$1$1, null, false, null, m210outlinedButtonColorsro_MJ88, null, null, null, null, ComposableSingletons$BottomBarComponentKt.f80lambda1, composerImpl, 805306368, 494);
        ButtonKt.Button(new BottomBarComponent$Content$1$2(bottomBarViewModel), null, false, null, null, null, null, null, null, ComposableSingletons$BottomBarComponentKt.f81lambda2, composerImpl, 805306368, 510);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.bottombar.ui.BottomBarComponent$Content$2
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    BottomBarComponent.this.Content(volumePanelComposeScope, modifier, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
