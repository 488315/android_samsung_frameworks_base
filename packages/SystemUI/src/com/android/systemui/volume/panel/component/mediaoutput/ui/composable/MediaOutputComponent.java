package com.android.systemui.volume.panel.component.mediaoutput.ui.composable;

import androidx.compose.animation.AnimatedContentKt;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionKt;
import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.AspectRatioElement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.unit.Dp;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.systemui.common.ui.compose.ColorKt;
import com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.ConnectedDeviceViewModel;
import com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel;
import com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel;
import com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MediaOutputComponent implements ComposeVolumePanelUiComponent {
    public final MediaOutputViewModel viewModel;

    public MediaOutputComponent(MediaOutputViewModel mediaOutputViewModel) {
        this.viewModel = mediaOutputViewModel;
    }

    public final void ConnectedDeviceIcon(final DeviceIconViewModel deviceIconViewModel, Composer composer, final int i) {
        Modifier then;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(575301017);
        int i2 = (composerImpl.changed(deviceIconViewModel) ? 4 : 2) | i;
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent.ConnectedDeviceIcon (MediaOutputComponent.kt:142)");
            }
            Transition updateTransition = TransitionKt.updateTransition(deviceIconViewModel, "MediaOutputIconTransition", composerImpl, (i2 & 14) | 48, 0);
            Dp.Companion companion = Dp.Companion;
            then = PaddingKt.m124padding3ABfNKs(Modifier.Companion, 16).then(SizeKt.FillWholeMaxHeight).then(new AspectRatioElement(1.0f, false, InspectableValueKt.NoInspectorInfo));
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, then);
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
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(247926506);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = new MediaOutputComponent$$ExternalSyntheticLambda3(0);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            Function1 function1 = (Function1) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 247923927);
            if (m == composer$Companion$Empty$1) {
                m = new MediaOutputComponent$$ExternalSyntheticLambda3(1);
                composerImpl.updateRememberedValue(m);
            }
            composerImpl.end(false);
            ComposableSingletons$MediaOutputComponentKt.INSTANCE.getClass();
            AnimatedContentKt.AnimatedContent(updateTransition, null, function1, null, (Function1) m, ComposableSingletons$MediaOutputComponentKt.f115lambda1, composerImpl, 221568, 5);
            composerImpl.startReplaceGroup(247967630);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new MediaOutputComponent$$ExternalSyntheticLambda3(2);
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            Function1 function12 = (Function1) rememberedValue2;
            Object m2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 247965356);
            if (m2 == composer$Companion$Empty$1) {
                m2 = new MediaOutputComponent$$ExternalSyntheticLambda3(3);
                composerImpl.updateRememberedValue(m2);
            }
            composerImpl.end(false);
            AnimatedContentKt.AnimatedContent(updateTransition, null, function12, null, (Function1) m2, ComposableSingletons$MediaOutputComponentKt.f116lambda2, composerImpl, 221568, 5);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(deviceIconViewModel, i) { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent$$ExternalSyntheticLambda7
                public final /* synthetic */ DeviceIconViewModel f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    DeviceIconViewModel deviceIconViewModel2 = this.f$1;
                    MediaOutputComponent.this.ConnectedDeviceIcon(deviceIconViewModel2, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public final void ConnectedDeviceText(final RowScopeInstance rowScopeInstance, final ConnectedDeviceViewModel connectedDeviceViewModel, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1947319223);
        if (((i | (composerImpl.changedInstance(connectedDeviceViewModel) ? 32 : 16)) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent.ConnectedDeviceText (MediaOutputComponent.kt:117)");
            }
            Modifier.Companion companion = Modifier.Companion;
            Dp.Companion companion2 = Dp.Companion;
            Modifier m128paddingqDBjuR0$default = PaddingKt.m128paddingqDBjuR0$default(rowScopeInstance.weight(companion, 1.0f, true), 24, 0.0f, 0.0f, 0.0f, 14);
            Arrangement.INSTANCE.getClass();
            Arrangement.SpacedAligned m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(4);
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m91spacedBy0680j_4, Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m128paddingqDBjuR0$default);
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
            Modifier m27basicMarquee1Mj1MLw$default = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(companion, 0, 63);
            String obj = connectedDeviceViewModel.label.toString();
            MaterialTheme.INSTANCE.getClass();
            TextKt.m316Text4IGK_g(obj, m27basicMarquee1Mj1MLw$default, ColorKt.toColor(connectedDeviceViewModel.labelColor, composerImpl), 0L, null, null, null, 0L, null, null, 0L, 0, false, 1, 0, null, MaterialTheme.getTypography(composerImpl).labelMedium, composerImpl, 48, 3072, 57336);
            composerImpl = composerImpl;
            CharSequence charSequence = connectedDeviceViewModel.deviceName;
            composerImpl.startReplaceGroup(732193574);
            if (charSequence != null) {
                TextKt.m316Text4IGK_g(charSequence.toString(), BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(companion, 0, 63), ColorKt.toColor(connectedDeviceViewModel.deviceNameColor, composerImpl), 0L, null, null, null, 0L, null, null, 0L, 0, false, 1, 0, null, MaterialTheme.getTypography(composerImpl).titleMedium, composerImpl, 48, 3072, 57336);
                composerImpl = composerImpl;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(rowScopeInstance, connectedDeviceViewModel, i) { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent$$ExternalSyntheticLambda2
                public final /* synthetic */ RowScopeInstance f$1;
                public final /* synthetic */ ConnectedDeviceViewModel f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(7);
                    ConnectedDeviceViewModel connectedDeviceViewModel2 = this.f$2;
                    MediaOutputComponent.this.ConnectedDeviceText(this.f$1, connectedDeviceViewModel2, (Composer) obj2, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x00d6, code lost:
    
        if (r10 == androidx.compose.runtime.Composer.Companion.Empty) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x005f, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void Content(com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope r18, androidx.compose.ui.Modifier r19, androidx.compose.runtime.Composer r20, int r21) {
        /*
            Method dump skipped, instructions count: 283
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent.Content(com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }
}
