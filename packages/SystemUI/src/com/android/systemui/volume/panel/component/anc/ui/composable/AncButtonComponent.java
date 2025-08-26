package com.android.systemui.volume.panel.component.anc.ui.composable;

import android.content.res.Configuration;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BasicMarqueeKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.ButtonColors;
import androidx.compose.material3.ButtonKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.LiveRegionMode;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import androidx.slice.Slice;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.volume.panel.component.anc.ui.viewmodel.AncViewModel;
import com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent;
import com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

/* loaded from: classes3.dex */
public final class AncButtonComponent implements ComposeVolumePanelUiComponent {
    public final AncPopup ancPopup;
    public final AncViewModel viewModel;

    public AncButtonComponent(AncViewModel ancViewModel, AncPopup ancPopup) {
        this.viewModel = ancViewModel;
        this.ancPopup = ancPopup;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x027d  */
    @Override // com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void Content(VolumePanelComposeScope volumePanelComposeScope, Modifier modifier, Composer composer, int i) {
        MutableState mutableState;
        int currentCompositeKeyHash;
        boolean zChangedInstance;
        Object objRememberedValue;
        boolean zChanged;
        Object objRememberedValue2;
        boolean zChangedInstance2;
        Object objRememberedValue3;
        Object objM;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(59322415);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.anc.ui.composable.AncButtonComponent.Content (AncButtonComponent.kt:64)");
        }
        AncViewModel ancViewModel = this.viewModel;
        MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(ancViewModel.buttonSlice, composerImpl);
        final String strStringResource = StringResources_androidKt.stringResource(R.string.volume_panel_noise_control_title, composerImpl);
        composerImpl.startReplaceGroup(1396634368);
        Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
        float f = ((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).screenWidthDp;
        Dp.Companion companion = Dp.Companion;
        final float fMo58toPx0680j_4 = density.mo58toPx0680j_4(f);
        composerImpl.end(false);
        composerImpl.startReplaceGroup(1396637704);
        Object objRememberedValue4 = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Object obj = Composer.Companion.Empty;
        if (objRememberedValue4 == obj) {
            objRememberedValue4 = SnapshotIntStateKt.mutableIntStateOf(1);
            composerImpl.updateRememberedValue(objRememberedValue4);
        }
        final MutableIntState mutableIntState = (MutableIntState) objRememberedValue4;
        composerImpl.end(false);
        boolean zIsClickable = AncViewModel.isClickable((Slice) mutableStateCollectAsStateWithLifecycle.getValue());
        composerImpl.startReplaceGroup(1396644083);
        boolean zChanged2 = composerImpl.changed(fMo58toPx0680j_4);
        Object objRememberedValue5 = composerImpl.rememberedValue();
        if (zChanged2 || objRememberedValue5 == obj) {
            objRememberedValue5 = new Function1() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncButtonComponent$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    VolumePanelPopup.Companion.getClass();
                    ((SnapshotMutableIntStateImpl) mutableIntState).setIntValue(VolumePanelPopup.Companion.calculateGravity((LayoutCoordinates) obj2, fMo58toPx0680j_4));
                    return Unit.INSTANCE;
                }
            };
            composerImpl.updateRememberedValue(objRememberedValue5);
        }
        composerImpl.end(false);
        Modifier modifierOnGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(modifier, (Function1) objRememberedValue5);
        Arrangement.INSTANCE.getClass();
        Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(12);
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_4, Alignment.Companion.CenterHorizontally, composerImpl, 54);
        int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierOnGloballyPositioned);
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
        Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
        Updater.m337setimpl(composerImpl, columnMeasurePolicy, function2);
        Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
        Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (!composerImpl.inserting) {
            mutableState = mutableStateCollectAsStateWithLifecycle;
            if (!Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            Modifier.Companion companion2 = Modifier.Companion;
            Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(companion2, 64);
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierM131height3ABfNKs);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.useNode();
            } else {
                composerImpl.createNode(function0);
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
            if (!composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            Modifier modifierFillMaxSize = SizeKt.fillMaxSize(modifier, 1.0f);
            Slice slice = (Slice) mutableState.getValue();
            composerImpl.startReplaceGroup(1486673393);
            zChangedInstance = composerImpl.changedInstance(ancViewModel);
            objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance || objRememberedValue == obj) {
                objRememberedValue = new AncButtonComponent$Content$2$1$1$1(ancViewModel);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            composerImpl.end(false);
            SliceAndroidViewKt.SliceAndroidView(slice, modifierFillMaxSize, (Function1) ((KFunction) objRememberedValue), false, composerImpl, 3072, 0);
            Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(SizeKt.fillMaxSize(modifier, 1.0f), 8);
            composerImpl.startReplaceGroup(1486680894);
            zChanged = composerImpl.changed(strStringResource);
            objRememberedValue2 = composerImpl.rememberedValue();
            if (!zChanged || objRememberedValue2 == obj) {
                objRememberedValue2 = new Function1() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncButtonComponent$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj2;
                        LiveRegionMode.Companion.getClass();
                        SemanticsPropertiesKt.m718setLiveRegionhR3wRGc(semanticsPropertyReceiver, 0);
                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, strStringResource);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM125padding3ABfNKs, false, (Function1) objRememberedValue2);
            Color.Companion.getClass();
            long j = Color.Transparent;
            ButtonColors buttonColors = new ButtonColors(j, j, j, j, null);
            composerImpl.startReplaceGroup(1486687831);
            zChangedInstance2 = composerImpl.changedInstance(this);
            objRememberedValue3 = composerImpl.rememberedValue();
            if (!zChangedInstance2 || objRememberedValue3 == obj) {
                objRememberedValue3 = new Function0() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncButtonComponent$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        final AncPopup ancPopup = this.f$0.ancPopup;
                        int intValue = ((SnapshotMutableIntStateImpl) mutableIntState).getIntValue();
                        ancPopup.uiEventLogger.log(VolumePanelUiEvent.VOLUME_PANEL_ANC_POPUP_SHOWN);
                        ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(-1384461170, true, new Function3() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$show$1
                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj2, Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                ((Number) obj4).intValue();
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup.show.<anonymous> (AncPopup.kt:53)");
                                }
                                ancPopup.Title(0, composer2);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        });
                        ComposableLambdaImpl composableLambdaImpl2 = new ComposableLambdaImpl(-304919123, true, new Function3() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$show$2
                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj2, Object obj3, Object obj4) {
                                SystemUIDialog systemUIDialog = (SystemUIDialog) obj2;
                                Composer composer2 = (Composer) obj3;
                                int iIntValue = ((Number) obj4).intValue();
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup.show.<anonymous> (AncPopup.kt:53)");
                                }
                                ancPopup.Content(systemUIDialog, composer2, iIntValue & 14);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                return Unit.INSTANCE;
                            }
                        });
                        ancPopup.volumePanelPopup.show(null, intValue | 80, composableLambdaImpl, composableLambdaImpl2);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            composerImpl.end(false);
            ComposableSingletons$AncButtonComponentKt.INSTANCE.getClass();
            ButtonKt.Button((Function0) objRememberedValue3, modifierSemantics, zIsClickable, null, buttonColors, null, null, null, null, ComposableSingletons$AncButtonComponentKt.f112lambda1, composerImpl, 805306368, VolteConstants.ErrorCode.NOT_ACCEPTABLE_HERE);
            objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, true, 2114761136);
            if (objM == obj) {
                objM = new AncButtonComponent$$ExternalSyntheticLambda3();
                composerImpl.updateRememberedValue(objM);
            }
            composerImpl.end(false);
            Modifier modifierM27basicMarquee1Mj1MLw$default = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(SemanticsModifierKt.clearAndSetSemantics(companion2, (Function1) objM), 0, 63);
            MaterialTheme.INSTANCE.getClass();
            TextKt.m317Text4IGK_g(strStringResource, modifierM27basicMarquee1Mj1MLw$default, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 2, 0, null, MaterialTheme.getTypography(composerImpl).labelMedium, composerImpl, 0, 3072, 57340);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            composerImpl.end(false);
        }
        mutableState = mutableStateCollectAsStateWithLifecycle;
        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
        Function2 function242 = ComposeUiNode.Companion.SetModifier;
        Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function242);
        ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
        Modifier.Companion companion22 = Modifier.Companion;
        Modifier modifierM131height3ABfNKs2 = SizeKt.m131height3ABfNKs(companion22, 64);
        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
        currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope22 = composerImpl.currentCompositionLocalScope();
        Modifier modifierMaterializeModifier22 = ComposedModifierKt.materializeModifier(composerImpl, modifierM131height3ABfNKs2);
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
        }
        Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function2);
        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope22, function22);
        if (!composerImpl.inserting) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
        }
        Updater.m337setimpl(composerImpl, modifierMaterializeModifier22, function242);
        BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
        Modifier modifierFillMaxSize2 = SizeKt.fillMaxSize(modifier, 1.0f);
        Slice slice2 = (Slice) mutableState.getValue();
        composerImpl.startReplaceGroup(1486673393);
        zChangedInstance = composerImpl.changedInstance(ancViewModel);
        objRememberedValue = composerImpl.rememberedValue();
        if (!zChangedInstance) {
            objRememberedValue = new AncButtonComponent$Content$2$1$1$1(ancViewModel);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        composerImpl.end(false);
        SliceAndroidViewKt.SliceAndroidView(slice2, modifierFillMaxSize2, (Function1) ((KFunction) objRememberedValue), false, composerImpl, 3072, 0);
        Modifier modifierM125padding3ABfNKs2 = PaddingKt.m125padding3ABfNKs(SizeKt.fillMaxSize(modifier, 1.0f), 8);
        composerImpl.startReplaceGroup(1486680894);
        zChanged = composerImpl.changed(strStringResource);
        objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChanged) {
            objRememberedValue2 = new Function1() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncButtonComponent$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj2;
                    LiveRegionMode.Companion.getClass();
                    SemanticsPropertiesKt.m718setLiveRegionhR3wRGc(semanticsPropertyReceiver, 0);
                    SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, strStringResource);
                    return Unit.INSTANCE;
                }
            };
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        composerImpl.end(false);
        Modifier modifierSemantics2 = SemanticsModifierKt.semantics(modifierM125padding3ABfNKs2, false, (Function1) objRememberedValue2);
        Color.Companion.getClass();
        long j2 = Color.Transparent;
        ButtonColors buttonColors2 = new ButtonColors(j2, j2, j2, j2, null);
        composerImpl.startReplaceGroup(1486687831);
        zChangedInstance2 = composerImpl.changedInstance(this);
        objRememberedValue3 = composerImpl.rememberedValue();
        if (!zChangedInstance2) {
            objRememberedValue3 = new Function0() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncButtonComponent$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    final AncPopup ancPopup = this.f$0.ancPopup;
                    int intValue = ((SnapshotMutableIntStateImpl) mutableIntState).getIntValue();
                    ancPopup.uiEventLogger.log(VolumePanelUiEvent.VOLUME_PANEL_ANC_POPUP_SHOWN);
                    ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(-1384461170, true, new Function3() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$show$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj2, Object obj3, Object obj4) {
                            Composer composer2 = (Composer) obj3;
                            ((Number) obj4).intValue();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup.show.<anonymous> (AncPopup.kt:53)");
                            }
                            ancPopup.Title(0, composer2);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    });
                    ComposableLambdaImpl composableLambdaImpl2 = new ComposableLambdaImpl(-304919123, true, new Function3() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup$show$2
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj2, Object obj3, Object obj4) {
                            SystemUIDialog systemUIDialog = (SystemUIDialog) obj2;
                            Composer composer2 = (Composer) obj3;
                            int iIntValue = ((Number) obj4).intValue();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.anc.ui.composable.AncPopup.show.<anonymous> (AncPopup.kt:53)");
                            }
                            ancPopup.Content(systemUIDialog, composer2, iIntValue & 14);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    });
                    ancPopup.volumePanelPopup.show(null, intValue | 80, composableLambdaImpl, composableLambdaImpl2);
                    return Unit.INSTANCE;
                }
            };
            composerImpl.updateRememberedValue(objRememberedValue3);
        }
        composerImpl.end(false);
        ComposableSingletons$AncButtonComponentKt.INSTANCE.getClass();
        ButtonKt.Button((Function0) objRememberedValue3, modifierSemantics2, zIsClickable, null, buttonColors2, null, null, null, null, ComposableSingletons$AncButtonComponentKt.f112lambda1, composerImpl, 805306368, VolteConstants.ErrorCode.NOT_ACCEPTABLE_HERE);
        objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, true, 2114761136);
        if (objM == obj) {
        }
        composerImpl.end(false);
        Modifier modifierM27basicMarquee1Mj1MLw$default2 = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(SemanticsModifierKt.clearAndSetSemantics(companion22, (Function1) objM), 0, 63);
        MaterialTheme.INSTANCE.getClass();
        TextKt.m317Text4IGK_g(strStringResource, modifierM27basicMarquee1Mj1MLw$default2, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 2, 0, null, MaterialTheme.getTypography(composerImpl).labelMedium, composerImpl, 0, 3072, 57340);
        composerImpl.end(true);
        if (ComposerKt.isTraceInProgress()) {
        }
        composerImpl.end(false);
    }
}
