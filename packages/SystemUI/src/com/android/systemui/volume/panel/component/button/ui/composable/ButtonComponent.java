package com.android.systemui.volume.panel.component.button.ui.composable;

import android.content.res.Configuration;
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
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel;
import com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup;
import com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent;
import com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ButtonComponent implements ComposeVolumePanelUiComponent {
    public final Function2 onClick;
    public final StateFlow viewModelFlow;

    public ButtonComponent(StateFlow stateFlow, Function2 function2) {
        this.viewModelFlow = stateFlow;
        this.onClick = function2;
    }

    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    public final void Content(VolumePanelComposeScope volumePanelComposeScope, Modifier modifier, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1397659621);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent.Content (ButtonComponent.kt:63)");
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
        composerImpl.startReplaceGroup(1955982452);
        Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
        float f = ((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).screenWidthDp;
        Dp.Companion companion = Dp.Companion;
        final float mo57toPx0680j_4 = density.mo57toPx0680j_4(f);
        composerImpl.end(false);
        composerImpl.startReplaceGroup(1955985788);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = SnapshotIntStateKt.mutableIntStateOf(1);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final MutableIntState mutableIntState = (MutableIntState) rememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(1955990386);
        boolean changed = composerImpl.changed(mo57toPx0680j_4);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changed || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new Function1() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    VolumePanelPopup.Companion.getClass();
                    ((SnapshotMutableIntStateImpl) mutableIntState).setIntValue(VolumePanelPopup.Companion.calculateGravity((LayoutCoordinates) obj2, mo57toPx0680j_4));
                    return Unit.INSTANCE;
                }
            };
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        composerImpl.end(false);
        Modifier onGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(modifier, (Function1) rememberedValue2);
        Arrangement.INSTANCE.getClass();
        Arrangement.SpacedAligned m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(12);
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m91spacedBy0680j_4, Alignment.Companion.CenterHorizontally, composerImpl, 54);
        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, onGloballyPositioned);
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
        BottomComponentButtonSurfaceKt.BottomComponentButtonSurface(48, composerImpl, ComposableLambdaKt.rememberComposableLambda(-440073138, new Function2() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1
            /* JADX WARN: Code restructure failed: missing block: B:15:0x0059, code lost:
            
                if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
             */
            /* JADX WARN: Code restructure failed: missing block: B:25:0x0102, code lost:
            
                if (r13 == androidx.compose.runtime.Composer.Companion.Empty) goto L28;
             */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke(java.lang.Object r20, java.lang.Object r21) {
                /*
                    Method dump skipped, instructions count: 317
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
            }
        }, composerImpl), null);
        Modifier.Companion companion2 = Modifier.Companion;
        composerImpl.startReplaceGroup(-1675983356);
        Object rememberedValue3 = composerImpl.rememberedValue();
        if (rememberedValue3 == composer$Companion$Empty$1) {
            rememberedValue3 = new ButtonComponent$$ExternalSyntheticLambda1();
            composerImpl.updateRememberedValue(rememberedValue3);
        }
        composerImpl.end(false);
        Modifier m27basicMarquee1Mj1MLw$default = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(SemanticsModifierKt.clearAndSetSemantics(companion2, (Function1) rememberedValue3), 0, 63);
        MaterialTheme.INSTANCE.getClass();
        TextKt.m316Text4IGK_g(obj, m27basicMarquee1Mj1MLw$default, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 2, 0, null, MaterialTheme.getTypography(composerImpl).labelMedium, composerImpl, 0, 3072, 57340);
        composerImpl.end(true);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }
}
