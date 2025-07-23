package com.android.systemui.volume.panel.component.button.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel;
import com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent;
import com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ToggleButtonComponent implements ComposeVolumePanelUiComponent {
    public final Function1 onCheckedChange;
    public final StateFlow viewModelFlow;

    public ToggleButtonComponent(StateFlow stateFlow, Function1 function1) {
        this.viewModelFlow = stateFlow;
        this.onCheckedChange = function1;
    }

    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    public final void Content(VolumePanelComposeScope volumePanelComposeScope, Modifier modifier, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1883072515);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent.Content (ToggleButtonComponent.kt:58)");
        }
        final ButtonViewModel buttonViewModel = (ButtonViewModel) FlowExtKt.collectAsStateWithLifecycle(this.viewModelFlow, composerImpl).getValue();
        if (buttonViewModel == null) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
            return;
        }
        final String obj = buttonViewModel.label.toString();
        Arrangement arrangement = Arrangement.INSTANCE;
        Dp.Companion companion = Dp.Companion;
        arrangement.getClass();
        Arrangement.SpacedAligned m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(12);
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m91spacedBy0680j_4, Alignment.Companion.CenterHorizontally, composerImpl, 54);
        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
        Updater.m336setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
        }
        Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        BottomComponentButtonSurfaceKt.BottomComponentButtonSurface(48, composerImpl, ComposableLambdaKt.rememberComposableLambda(14766006, new Function2() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent$Content$1$1
            /* JADX WARN: Code restructure failed: missing block: B:19:0x00cc, code lost:
            
                if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
             */
            /* JADX WARN: Code restructure failed: missing block: B:23:0x0107, code lost:
            
                if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L25;
             */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke(java.lang.Object r29, java.lang.Object r30) {
                /*
                    Method dump skipped, instructions count: 320
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.button.ui.composable.ToggleButtonComponent$Content$1$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
            }
        }, composerImpl), null);
        Modifier.Companion companion2 = Modifier.Companion;
        composerImpl.startReplaceGroup(-446910608);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue = new ToggleButtonComponent$$ExternalSyntheticLambda0();
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        Modifier m27basicMarquee1Mj1MLw$default = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(SemanticsModifierKt.clearAndSetSemantics(companion2, (Function1) rememberedValue), 0, 63);
        MaterialTheme.INSTANCE.getClass();
        TextKt.m316Text4IGK_g(obj, m27basicMarquee1Mj1MLw$default, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 2, 0, null, MaterialTheme.getTypography(composerImpl).labelMedium, composerImpl, 0, 3072, 57340);
        composerImpl.end(true);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }
}
