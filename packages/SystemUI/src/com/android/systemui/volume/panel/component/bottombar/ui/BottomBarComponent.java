package com.android.systemui.volume.panel.component.bottombar.ui;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$SpaceBetween$1;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Dp;
import com.android.compose.PlatformButtonsKt;
import com.android.systemui.volume.panel.component.bottombar.ui.viewmodel.BottomBarViewModel;
import com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent;
import com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

/* loaded from: classes3.dex */
public final class BottomBarComponent implements ComposeVolumePanelUiComponent {
    public final BottomBarViewModel viewModel;

    public BottomBarComponent(BottomBarViewModel bottomBarViewModel) {
        this.viewModel = bottomBarViewModel;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00e2  */
    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void Content(VolumePanelComposeScope volumePanelComposeScope, Modifier modifier, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(695359912);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.bottombar.ui.BottomBarComponent.Content (BottomBarComponent.kt:45)");
        }
        float f = volumePanelComposeScope.state.isLargeScreen ? 54 : 48;
        Dp.Companion companion = Dp.Companion;
        Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(SizeKt.m133heightInVpY3zN4$default(modifier, f, 0.0f, 2), 1.0f);
        Arrangement.INSTANCE.getClass();
        Arrangement$SpaceBetween$1 arrangement$SpaceBetween$1 = Arrangement.SpaceBetween;
        Alignment.Companion.getClass();
        RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$SpaceBetween$1, Alignment.Companion.CenterVertically, composerImpl, 54);
        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxWidth);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        if (composerImpl.applier == null) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
        }
        Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
        RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
        composerImpl.startReplaceGroup(1494614847);
        BottomBarViewModel bottomBarViewModel = this.viewModel;
        boolean zChangedInstance = composerImpl.changedInstance(bottomBarViewModel);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion2 = Composer.Companion;
        if (!zChangedInstance) {
            companion2.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new BottomBarComponent$Content$1$1$1(bottomBarViewModel);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        composerImpl.end(false);
        ComposableSingletons$BottomBarComponentKt.INSTANCE.getClass();
        PlatformButtonsKt.PlatformOutlinedButton((Function0) ((KFunction) objRememberedValue), null, false, null, null, ComposableSingletons$BottomBarComponentKt.f113lambda1, composerImpl, 196608);
        composerImpl.startReplaceGroup(1494620379);
        boolean zChangedInstance2 = composerImpl.changedInstance(bottomBarViewModel);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChangedInstance2) {
            companion2.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                objRememberedValue2 = new BottomBarComponent$Content$1$2$1(bottomBarViewModel);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
        }
        composerImpl.end(false);
        PlatformButtonsKt.PlatformButton((Function0) ((KFunction) objRememberedValue2), null, false, null, null, null, ComposableSingletons$BottomBarComponentKt.f114lambda2, composerImpl, 1572864, 62);
        composerImpl.end(true);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }
}
