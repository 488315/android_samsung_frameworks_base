package com.android.systemui.volume.panel.component.button.ui.composable;

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
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
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
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.compose.animation.ExpandableKt;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.ui.compose.IconKt;
import com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel;
import com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup;
import com.android.systemui.volume.panel.ui.composable.ComposeVolumePanelUiComponent;
import com.android.systemui.volume.panel.ui.composable.VolumePanelComposeScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlow;

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
        final String string = buttonViewModel.label.toString();
        composerImpl.startReplaceGroup(1955982452);
        Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
        float f = ((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).screenWidthDp;
        Dp.Companion companion = Dp.Companion;
        final float fMo58toPx0680j_4 = density.mo58toPx0680j_4(f);
        composerImpl.end(false);
        composerImpl.startReplaceGroup(1955985788);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            objRememberedValue = SnapshotIntStateKt.mutableIntStateOf(1);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        final MutableIntState mutableIntState = (MutableIntState) objRememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(1955990386);
        boolean zChanged = composerImpl.changed(fMo58toPx0680j_4);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (zChanged || objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = new Function1() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    VolumePanelPopup.Companion.getClass();
                    ((SnapshotMutableIntStateImpl) mutableIntState).setIntValue(VolumePanelPopup.Companion.calculateGravity((LayoutCoordinates) obj, fMo58toPx0680j_4));
                    return Unit.INSTANCE;
                }
            };
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        composerImpl.end(false);
        Modifier modifierOnGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(modifier, (Function1) objRememberedValue2);
        Arrangement.INSTANCE.getClass();
        Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(12);
        Alignment.Companion.getClass();
        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_4, Alignment.Companion.CenterHorizontally, composerImpl, 54);
        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
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
        Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
        }
        Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
        BottomComponentButtonSurfaceKt.BottomComponentButtonSurface(48, composerImpl, ComposableLambdaKt.rememberComposableLambda(-440073138, new Function2() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1
            /* JADX WARN: Removed duplicated region for block: B:15:0x005b  */
            /* JADX WARN: Removed duplicated region for block: B:28:0x0104  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj, Object obj2) {
                long j;
                long j2;
                Composer composer2 = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                    } else {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent.Content.<anonymous>.<anonymous> (ButtonComponent.kt:79)");
                        }
                        Dp.Companion companion2 = Dp.Companion;
                        Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(SizeKt.fillMaxSize(Modifier.Companion, 1.0f), 8);
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        composerImpl3.startReplaceGroup(-870982302);
                        final String str = string;
                        boolean zChanged2 = composerImpl3.changed(str);
                        Object objRememberedValue3 = composerImpl3.rememberedValue();
                        Composer.Companion companion3 = Composer.Companion;
                        if (!zChanged2) {
                            companion3.getClass();
                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                objRememberedValue3 = new Function1() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj3) {
                                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj3;
                                        Role.Companion.getClass();
                                        SemanticsPropertiesKt.m719setRolekuIjeqM(semanticsPropertyReceiver, 0);
                                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, str);
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl3.updateRememberedValue(objRememberedValue3);
                            }
                            composerImpl3.end(false);
                            Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM125padding3ABfNKs, false, (Function1) objRememberedValue3);
                            final ButtonViewModel buttonViewModel2 = buttonViewModel;
                            if (buttonViewModel2.isActive) {
                                composerImpl3.startReplaceGroup(-1230437125);
                                composerImpl3.startReplaceGroup(-1230283210);
                                MaterialTheme.INSTANCE.getClass();
                                j = MaterialTheme.getColorScheme(composerImpl3).tertiaryContainer;
                                composerImpl3.end(false);
                                composerImpl3.end(false);
                            } else {
                                composerImpl3.startReplaceGroup(-1230139432);
                                composerImpl3.startReplaceGroup(-1229973024);
                                MaterialTheme.INSTANCE.getClass();
                                j = MaterialTheme.getColorScheme(composerImpl3).surface;
                                composerImpl3.end(false);
                                composerImpl3.end(false);
                            }
                            RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(20);
                            if (buttonViewModel2.isActive) {
                                composerImpl3.startReplaceGroup(-1229705897);
                                composerImpl3.startReplaceGroup(-1229550060);
                                MaterialTheme.INSTANCE.getClass();
                                j2 = MaterialTheme.getColorScheme(composerImpl3).onTertiaryContainer;
                                composerImpl3.end(false);
                                composerImpl3.end(false);
                            } else {
                                composerImpl3.startReplaceGroup(-1229410746);
                                MaterialTheme.INSTANCE.getClass();
                                j2 = MaterialTheme.getColorScheme(composerImpl3).onSurface;
                                composerImpl3.end(false);
                            }
                            composerImpl3.startReplaceGroup(-870938535);
                            final ButtonComponent buttonComponent = this;
                            boolean zChangedInstance = composerImpl3.changedInstance(buttonComponent);
                            Object objRememberedValue4 = composerImpl3.rememberedValue();
                            if (!zChangedInstance) {
                                companion3.getClass();
                                if (objRememberedValue4 == Composer.Companion.Empty) {
                                    final MutableIntState mutableIntState2 = mutableIntState;
                                    objRememberedValue4 = new Function1() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1$$ExternalSyntheticLambda1
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj3) {
                                            buttonComponent.onClick.invoke((Expandable) obj3, Integer.valueOf(((SnapshotMutableIntStateImpl) mutableIntState2).getIntValue()));
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl3.updateRememberedValue(objRememberedValue4);
                                }
                                composerImpl3.end(false);
                                ExpandableKt.m912ExpandableS04cQl8(j, roundedCornerShapeM187RoundedCornerShape0680j_4, modifierSemantics, j2, null, (Function1) objRememberedValue4, null, false, false, null, ComposableLambdaKt.rememberComposableLambda(556023202, new Function3() { // from class: com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent$Content$2$1.3
                                    @Override // kotlin.jvm.functions.Function3
                                    public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                        Composer composer3 = (Composer) obj4;
                                        ((Number) obj5).intValue();
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.button.ui.composable.ButtonComponent.Content.<anonymous>.<anonymous>.<anonymous> (ButtonComponent.kt:112)");
                                        }
                                        Modifier.Companion companion4 = Modifier.Companion;
                                        Modifier modifierFillMaxSize = SizeKt.fillMaxSize(companion4, 1.0f);
                                        Alignment.Companion.getClass();
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                                        int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                        ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl4.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer3, modifierFillMaxSize);
                                        ComposeUiNode.Companion.getClass();
                                        Function0 function02 = ComposeUiNode.Companion.Constructor;
                                        if (composerImpl4.applier == null) {
                                            ComposablesKt.invalidApplier();
                                            throw null;
                                        }
                                        composerImpl4.startReusableNode();
                                        if (composerImpl4.inserting) {
                                            composerImpl4.createNode(function02);
                                        } else {
                                            composerImpl4.useNode();
                                        }
                                        Updater.m337setimpl(composer3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                        Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope2, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                        Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl4, currentCompositeKeyHash2, function22);
                                        }
                                        Updater.m337setimpl(composer3, modifierMaterializeModifier2, ComposeUiNode.Companion.SetModifier);
                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                        Dp.Companion companion5 = Dp.Companion;
                                        IconKt.m1074IconFNF3uiM(buttonViewModel2.icon, SizeKt.m140size3ABfNKs(companion4, 24), 0L, composer3, 48, 4);
                                        composerImpl4.end(true);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl3), composerImpl3, 0, 976);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        }, composerImpl), null);
        Modifier.Companion companion2 = Modifier.Companion;
        composerImpl.startReplaceGroup(-1675983356);
        Object objRememberedValue3 = composerImpl.rememberedValue();
        if (objRememberedValue3 == composer$Companion$Empty$1) {
            objRememberedValue3 = new ButtonComponent$$ExternalSyntheticLambda1();
            composerImpl.updateRememberedValue(objRememberedValue3);
        }
        composerImpl.end(false);
        Modifier modifierM27basicMarquee1Mj1MLw$default = BasicMarqueeKt.m27basicMarquee1Mj1MLw$default(SemanticsModifierKt.clearAndSetSemantics(companion2, (Function1) objRememberedValue3), 0, 63);
        MaterialTheme.INSTANCE.getClass();
        TextKt.m317Text4IGK_g(string, modifierM27basicMarquee1Mj1MLw$default, 0L, 0L, null, null, null, 0L, null, null, 0L, 0, false, 2, 0, null, MaterialTheme.getTypography(composerImpl).labelMedium, composerImpl, 0, 3072, 57340);
        composerImpl.end(true);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }
}
