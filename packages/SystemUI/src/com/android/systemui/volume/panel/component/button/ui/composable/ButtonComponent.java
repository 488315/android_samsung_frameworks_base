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
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
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

public final class ButtonComponent implements ComposeVolumePanelUiComponent {
    public final Function2 onClick;
    public final StateFlow viewModelFlow;

    public ButtonComponent(StateFlow stateFlow, Function2 function2) {
        this.viewModelFlow = stateFlow;
        this.onClick = function2;
    }

    /* JADX WARN: Type inference failed for: r6v9, types: [com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1, kotlin.jvm.internal.Lambda] */
    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    public final void Content(final VolumePanelComposeScope volumePanelComposeScope, final Modifier modifier, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1397659621);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final ButtonViewModel buttonViewModel = (ButtonViewModel) FlowExtKt.collectAsStateWithLifecycle(this.viewModelFlow, composerImpl).getValue();
        if (buttonViewModel == null) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$viewModel$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj2).intValue();
                        ButtonComponent.this.Content(volumePanelComposeScope, modifier, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        final String obj = buttonViewModel.label.toString();
        composerImpl.startReplaceGroup(2141306049);
        Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
        float f = ((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).screenWidthDp;
        Dp.Companion companion = Dp.Companion;
        final float mo61toPx0680j_4 = density.mo61toPx0680j_4(f);
        composerImpl.end(false);
        composerImpl.startReplaceGroup(2141306154);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = SnapshotIntStateKt.mutableIntStateOf(1);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final MutableIntState mutableIntState = (MutableIntState) rememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(2141306298);
        boolean changed = composerImpl.changed(mo61toPx0680j_4);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changed || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new Function1() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    MutableIntState mutableIntState2 = mutableIntState;
                    VolumePanelPopup.Companion companion2 = VolumePanelPopup.Companion;
                    float f2 = mo61toPx0680j_4;
                    companion2.getClass();
                    ((SnapshotMutableIntStateImpl) mutableIntState2).setIntValue(VolumePanelPopup.Companion.calculateGravity((LayoutCoordinates) obj2, f2));
                    return Unit.INSTANCE;
                }
            };
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        composerImpl.end(false);
        Modifier onGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(modifier, (Function1) rememberedValue2);
        Arrangement.INSTANCE.getClass();
        Arrangement.SpacedAligned m75spacedBy0680j_4 = Arrangement.m75spacedBy0680j_4(12);
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m75spacedBy0680j_4, Alignment.Companion.CenterHorizontally, composerImpl, 54);
        int i2 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, onGloballyPositioned);
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
        Updater.m276setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m276setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m276setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        BottomComponentButtonSurfaceKt.BottomComponentButtonSurface(null, ComposableLambdaKt.rememberComposableLambda(-440073138, composerImpl, new Function2() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Code restructure failed: missing block: B:12:0x004c, code lost:
            
                if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L12;
             */
            /* JADX WARN: Type inference failed for: r14v7, types: [com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1$3, kotlin.jvm.internal.Lambda] */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke(java.lang.Object r14, java.lang.Object r15) {
                /*
                    r13 = this;
                    androidx.compose.runtime.Composer r14 = (androidx.compose.runtime.Composer) r14
                    java.lang.Number r15 = (java.lang.Number) r15
                    int r15 = r15.intValue()
                    r15 = r15 & 11
                    r0 = 2
                    if (r15 != r0) goto L1c
                    r15 = r14
                    androidx.compose.runtime.ComposerImpl r15 = (androidx.compose.runtime.ComposerImpl) r15
                    boolean r0 = r15.getSkipping()
                    if (r0 != 0) goto L17
                    goto L1c
                L17:
                    r15.skipToGroupEnd()
                    goto Le5
                L1c:
                    androidx.compose.runtime.OpaqueKey r15 = androidx.compose.runtime.ComposerKt.invocation
                    androidx.compose.ui.Modifier$Companion r15 = androidx.compose.ui.Modifier.Companion
                    androidx.compose.foundation.layout.FillElement r0 = androidx.compose.foundation.layout.SizeKt.FillWholeMaxSize
                    r15.then(r0)
                    r15 = 8
                    float r15 = (float) r15
                    androidx.compose.ui.unit.Dp$Companion r1 = androidx.compose.ui.unit.Dp.Companion
                    androidx.compose.ui.Modifier r15 = androidx.compose.foundation.layout.PaddingKt.m102padding3ABfNKs(r0, r15)
                    r10 = r14
                    androidx.compose.runtime.ComposerImpl r10 = (androidx.compose.runtime.ComposerImpl) r10
                    r14 = -1552117019(0xffffffffa37c92e5, float:-1.36920535E-17)
                    r10.startReplaceGroup(r14)
                    java.lang.String r14 = r1
                    boolean r14 = r10.changed(r14)
                    java.lang.String r0 = r1
                    java.lang.Object r1 = r10.rememberedValue()
                    if (r14 != 0) goto L4e
                    androidx.compose.runtime.Composer$Companion r14 = androidx.compose.runtime.Composer.Companion
                    r14.getClass()
                    androidx.compose.runtime.Composer$Companion$Empty$1 r14 = androidx.compose.runtime.Composer.Companion.Empty
                    if (r1 != r14) goto L56
                L4e:
                    com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1$1$1 r1 = new com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1$1$1
                    r1.<init>()
                    r10.updateRememberedValue(r1)
                L56:
                    kotlin.jvm.functions.Function1 r1 = (kotlin.jvm.functions.Function1) r1
                    r14 = 0
                    r10.end(r14)
                    androidx.compose.ui.Modifier r3 = androidx.compose.ui.semantics.SemanticsModifierKt.semantics(r15, r14, r1)
                    com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel r15 = r2
                    boolean r15 = r15.isActive
                    if (r15 == 0) goto L7b
                    r15 = -1552116756(0xffffffffa37c93ec, float:-1.3692271E-17)
                    r10.startReplaceGroup(r15)
                    androidx.compose.material3.MaterialTheme r15 = androidx.compose.material3.MaterialTheme.INSTANCE
                    r15.getClass()
                    androidx.compose.material3.ColorScheme r15 = androidx.compose.material3.MaterialTheme.getColorScheme(r10)
                    long r0 = r15.tertiaryContainer
                    r10.end(r14)
                    goto L8f
                L7b:
                    r15 = -1552116651(0xffffffffa37c9455, float:-1.3692358E-17)
                    r10.startReplaceGroup(r15)
                    androidx.compose.material3.MaterialTheme r15 = androidx.compose.material3.MaterialTheme.INSTANCE
                    r15.getClass()
                    androidx.compose.material3.ColorScheme r15 = androidx.compose.material3.MaterialTheme.getColorScheme(r10)
                    long r0 = r15.surface
                    r10.end(r14)
                L8f:
                    r15 = 20
                    float r15 = (float) r15
                    androidx.compose.foundation.shape.RoundedCornerShape r2 = androidx.compose.foundation.shape.RoundedCornerShapeKt.m151RoundedCornerShape0680j_4(r15)
                    com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel r15 = r2
                    boolean r15 = r15.isActive
                    if (r15 == 0) goto Lb1
                    r15 = -1552116422(0xffffffffa37c953a, float:-1.3692547E-17)
                    r10.startReplaceGroup(r15)
                    androidx.compose.material3.MaterialTheme r15 = androidx.compose.material3.MaterialTheme.INSTANCE
                    r15.getClass()
                    androidx.compose.material3.ColorScheme r15 = androidx.compose.material3.MaterialTheme.getColorScheme(r10)
                    long r4 = r15.onTertiaryContainer
                    r10.end(r14)
                    goto Lc5
                Lb1:
                    r15 = -1552116315(0xffffffffa37c95a5, float:-1.3692636E-17)
                    r10.startReplaceGroup(r15)
                    androidx.compose.material3.MaterialTheme r15 = androidx.compose.material3.MaterialTheme.INSTANCE
                    r15.getClass()
                    androidx.compose.material3.ColorScheme r15 = androidx.compose.material3.MaterialTheme.getColorScheme(r10)
                    long r4 = r15.onSurface
                    r10.end(r14)
                Lc5:
                    com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1$2 r7 = new com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1$2
                    com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent r14 = r3
                    androidx.compose.runtime.MutableIntState r15 = r4
                    r7.<init>()
                    com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1$3 r14 = new com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1$3
                    com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel r13 = r2
                    r14.<init>()
                    r13 = 2145209882(0x7fdd4e1a, float:NaN)
                    androidx.compose.runtime.internal.ComposableLambdaImpl r9 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r13, r10, r14)
                    r11 = 12582912(0xc00000, float:1.7632415E-38)
                    r12 = 80
                    r6 = 0
                    r8 = 0
                    com.android.compose.animation.ExpandableKt.m810ExpandableQIcBpto(r0, r2, r3, r4, r6, r7, r8, r9, r10, r11, r12)
                Le5:
                    kotlin.Unit r13 = kotlin.Unit.INSTANCE
                    return r13
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
            }
        }), composerImpl, 48, 1);
        Modifier m25basicMarquee1Mj1MLw$default = BasicMarqueeKt.m25basicMarquee1Mj1MLw$default(SemanticsModifierKt.clearAndSetSemantics(Modifier.Companion, new Function1() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                return Unit.INSTANCE;
            }
        }));
        MaterialTheme.INSTANCE.getClass();
        TextKt.m257Text4IGK_g(obj, m25basicMarquee1Mj1MLw$default, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 2, 0, null, MaterialTheme.getTypography(composerImpl).labelMedium, composerImpl, 0, 3072, 57340);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block = new Function2() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    ButtonComponent.this.Content(volumePanelComposeScope, modifier, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
