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
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.LiveRegionMode;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.compose.animation.ExpandableKt;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.ui.compose.ColorKt;
import com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.ConnectedDeviceViewModel;
import com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel;
import com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.MediaOutputViewModel;
import com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent;
import com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class MediaOutputComponent implements ComposeVolumePanelUiComponent {
    public final MediaOutputViewModel viewModel;

    public MediaOutputComponent(MediaOutputViewModel mediaOutputViewModel) {
        this.viewModel = mediaOutputViewModel;
    }

    public final void ConnectedDeviceIcon(final DeviceIconViewModel deviceIconViewModel, Composer composer, final int i) throws Throwable {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(575301017);
        int i2 = (composerImpl.changed(deviceIconViewModel) ? 4 : 2) | i;
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent.ConnectedDeviceIcon (MediaOutputComponent.kt:142)");
            }
            Transition transitionUpdateTransition = TransitionKt.updateTransition(deviceIconViewModel, "MediaOutputIconTransition", composerImpl, (i2 & 14) | 48, 0);
            Dp.Companion companion = Dp.Companion;
            Modifier modifierThen = PaddingKt.m125padding3ABfNKs(Modifier.Companion, 16).then(SizeKt.FillWholeMaxHeight).then(new AspectRatioElement(1.0f, false, InspectableValueKt.NoInspectorInfo));
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierThen);
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
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composerImpl.startReplaceGroup(247926506);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = new MediaOutputComponent$$ExternalSyntheticLambda3(0);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            Function1 function1 = (Function1) objRememberedValue;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 247923927);
            if (objM == composer$Companion$Empty$1) {
                objM = new MediaOutputComponent$$ExternalSyntheticLambda3(1);
                composerImpl.updateRememberedValue(objM);
            }
            composerImpl.end(false);
            ComposableSingletons$MediaOutputComponentKt.INSTANCE.getClass();
            AnimatedContentKt.AnimatedContent(transitionUpdateTransition, null, function1, null, (Function1) objM, ComposableSingletons$MediaOutputComponentKt.f115lambda1, composerImpl, 221568, 5);
            composerImpl.startReplaceGroup(247967630);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new MediaOutputComponent$$ExternalSyntheticLambda3(2);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            Function1 function12 = (Function1) objRememberedValue2;
            Object objM2 = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 247965356);
            if (objM2 == composer$Companion$Empty$1) {
                objM2 = new MediaOutputComponent$$ExternalSyntheticLambda3(3);
                composerImpl.updateRememberedValue(objM2);
            }
            composerImpl.end(false);
            AnimatedContentKt.AnimatedContent(transitionUpdateTransition, null, function12, null, (Function1) objM2, ComposableSingletons$MediaOutputComponentKt.f116lambda2, composerImpl, 221568, 5);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(deviceIconViewModel, i) { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent$$ExternalSyntheticLambda7
                public final /* synthetic */ DeviceIconViewModel f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Throwable {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    DeviceIconViewModel deviceIconViewModel2 = this.f$1;
                    this.f$0.ConnectedDeviceIcon(deviceIconViewModel2, (Composer) obj, iUpdateChangedFlags);
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
            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(rowScopeInstance.weight(companion, 1.0f, true), 24, 0.0f, 0.0f, 0.0f, 14);
            Arrangement.INSTANCE.getClass();
            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(4);
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_4, Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM129paddingqDBjuR0$default);
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
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            Modifier modifierM27basicMarquee1Mj1MLw$default = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(companion, 0, 63);
            String string = connectedDeviceViewModel.label.toString();
            MaterialTheme.INSTANCE.getClass();
            TextKt.m317Text4IGK_g(string, modifierM27basicMarquee1Mj1MLw$default, ColorKt.toColor(connectedDeviceViewModel.labelColor, composerImpl), 0L, null, null, null, 0L, null, null, 0L, 0, false, 1, 0, null, MaterialTheme.getTypography(composerImpl).labelMedium, composerImpl, 48, 3072, 57336);
            composerImpl = composerImpl;
            CharSequence charSequence = connectedDeviceViewModel.deviceName;
            composerImpl.startReplaceGroup(732193574);
            if (charSequence != null) {
                TextKt.m317Text4IGK_g(charSequence.toString(), BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(companion, 0, 63), ColorKt.toColor(connectedDeviceViewModel.deviceNameColor, composerImpl), 0L, null, null, null, 0L, null, null, 0L, 0, false, 1, 0, null, MaterialTheme.getTypography(composerImpl).titleMedium, composerImpl, 48, 3072, 57336);
                composerImpl = composerImpl;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(rowScopeInstance, connectedDeviceViewModel, i) { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent$$ExternalSyntheticLambda2
                public final /* synthetic */ RowScopeInstance f$1;
                public final /* synthetic */ ConnectedDeviceViewModel f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(7);
                    ConnectedDeviceViewModel connectedDeviceViewModel2 = this.f$2;
                    this.f$0.ConnectedDeviceText(this.f$1, connectedDeviceViewModel2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0061  */
    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void Content(VolumePanelComposeScope volumePanelComposeScope, Modifier modifier, Composer composer, int i) {
        long j;
        Function1 function1;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1150893050);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent.Content (MediaOutputComponent.kt:77)");
        }
        MediaOutputViewModel mediaOutputViewModel = this.viewModel;
        final MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(mediaOutputViewModel.connectedDeviceViewModel, composerImpl);
        final MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(mediaOutputViewModel.deviceIconViewModel, composerImpl);
        final String strStringResource = StringResources_androidKt.stringResource(R.string.volume_panel_enter_media_output_settings, composerImpl);
        MutableState mutableStateCollectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle(mediaOutputViewModel.enabled, composerImpl);
        Dp.Companion companion = Dp.Companion;
        Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(SizeKt.fillMaxWidth(Modifier.Companion, 1.0f), 80);
        composerImpl.startReplaceGroup(-1565441142);
        boolean zChanged = composerImpl.changed(strStringResource) | composerImpl.changedInstance(this);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion2 = Composer.Companion;
        if (!zChanged) {
            companion2.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new Function1() { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                        LiveRegionMode.Companion.getClass();
                        SemanticsPropertiesKt.m718setLiveRegionhR3wRGc(semanticsPropertyReceiver, 0);
                        final MediaOutputComponent mediaOutputComponent = this;
                        SemanticsPropertiesKt.onClick(semanticsPropertyReceiver, strStringResource, new Function0() { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent$$ExternalSyntheticLambda8
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                mediaOutputComponent.viewModel.onBarClick(null);
                                return Boolean.TRUE;
                            }
                        });
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        composerImpl.end(false);
        Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM131height3ABfNKs, false, (Function1) objRememberedValue);
        if (((Boolean) mutableStateCollectAsStateWithLifecycle3.getValue()).booleanValue()) {
            composerImpl.startReplaceGroup(-1283760298);
            MaterialTheme.INSTANCE.getClass();
            j = MaterialTheme.getColorScheme(composerImpl).surface;
            composerImpl.end(false);
        } else {
            composerImpl.startReplaceGroup(-1283681434);
            MaterialTheme.INSTANCE.getClass();
            j = MaterialTheme.getColorScheme(composerImpl).surfaceContainerHighest;
            composerImpl.end(false);
        }
        RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(28);
        composerImpl.startReplaceGroup(-1565424058);
        if (((Boolean) mutableStateCollectAsStateWithLifecycle3.getValue()).booleanValue()) {
            composerImpl.startReplaceGroup(-1565423041);
            boolean zChangedInstance = composerImpl.changedInstance(this);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                companion2.getClass();
                if (objRememberedValue2 == Composer.Companion.Empty) {
                    objRememberedValue2 = new Function1() { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            this.f$0.viewModel.onBarClick((Expandable) obj);
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                function1 = (Function1) objRememberedValue2;
                composerImpl.end(false);
            }
        } else {
            function1 = null;
        }
        composerImpl.end(false);
        ExpandableKt.m912ExpandableS04cQl8(j, roundedCornerShapeM187RoundedCornerShape0680j_4, modifierSemantics, 0L, null, function1, null, false, false, null, ComposableLambdaKt.rememberComposableLambda(1041740582, new Function3() { // from class: com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent.Content.3
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) throws Throwable {
                Composer composer2 = (Composer) obj2;
                ((Number) obj3).intValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.mediaoutput.ui.composable.MediaOutputComponent.Content.<anonymous> (MediaOutputComponent.kt:108)");
                }
                Modifier.Companion companion3 = Modifier.Companion;
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                Arrangement.INSTANCE.getClass();
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composer2, 48);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, companion3);
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
                Updater.m337setimpl(composer2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                ConnectedDeviceViewModel connectedDeviceViewModel = (ConnectedDeviceViewModel) mutableStateCollectAsStateWithLifecycle.getValue();
                composerImpl2.startReplaceGroup(-204919488);
                MediaOutputComponent mediaOutputComponent = this;
                if (connectedDeviceViewModel != null) {
                    mediaOutputComponent.ConnectedDeviceText(rowScopeInstance, connectedDeviceViewModel, composer2, 6);
                }
                composerImpl2.end(false);
                DeviceIconViewModel deviceIconViewModel = (DeviceIconViewModel) mutableStateCollectAsStateWithLifecycle2.getValue();
                composerImpl2.startReplaceGroup(-204917248);
                if (deviceIconViewModel != null) {
                    mediaOutputComponent.ConnectedDeviceIcon(deviceIconViewModel, composer2, 0);
                }
                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, false, true)) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }, composerImpl), composerImpl, 0, 984);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }
}
