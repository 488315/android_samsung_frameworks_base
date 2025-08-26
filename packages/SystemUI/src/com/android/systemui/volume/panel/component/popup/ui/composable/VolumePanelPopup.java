package com.android.systemui.volume.panel.component.popup.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.IconButtonColors;
import androidx.compose.material3.IconButtonDefaults;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.statusbar.phone.ComponentSystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialogFactoryExtKt;
import com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class VolumePanelPopup {
    public static final Companion Companion = new Companion(null);
    public final SystemUIDialogFactory dialogFactory;
    public final DialogTransitionAnimator dialogTransitionAnimator;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static int calculateGravity(LayoutCoordinates layoutCoordinates, float f) {
            Rect rectBoundsInRoot = LayoutCoordinatesKt.boundsInRoot(layoutCoordinates);
            float f2 = rectBoundsInRoot.right;
            float f3 = rectBoundsInRoot.left;
            Offset.Companion companion = Offset.Companion;
            float fIntBitsToFloat = Float.intBitsToFloat((int) (((Float.floatToRawIntBits(((f2 - f3) / 2.0f) + f3) << 32) | (Float.floatToRawIntBits(rectBoundsInRoot.bottom) & 4294967295L)) >> 32));
            float f4 = f / 2;
            if (fIntBitsToFloat < f4) {
                return 3;
            }
            return fIntBitsToFloat > f4 ? 5 : 1;
        }

        private Companion() {
        }
    }

    public VolumePanelPopup(SystemUIDialogFactory systemUIDialogFactory, DialogTransitionAnimator dialogTransitionAnimator) {
        this.dialogFactory = systemUIDialogFactory;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0261  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void PopupComposable(final SystemUIDialog systemUIDialog, final Function3 function3, final Function3 function32, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(499530445);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(systemUIDialog) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function3) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function32) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup.PopupComposable (VolumePanelPopup.kt:89)");
            }
            final String strStringResource = StringResources_androidKt.stringResource(R.string.accessibility_volume_settings, composerImpl);
            Modifier.Companion companion = Modifier.Companion;
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion, 1.0f);
            composerImpl.startReplaceGroup(1266066916);
            boolean zChanged = composerImpl.changed(strStringResource);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion2 = Composer.Companion;
            if (!zChanged) {
                companion2.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function1() { // from class: com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            VolumePanelPopup.Companion companion3 = VolumePanelPopup.Companion;
                            SemanticsPropertiesKt.setPaneTitle((SemanticsPropertyReceiver) obj, strStringResource);
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierFillMaxWidth, false, (Function1) objRememberedValue);
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierSemantics);
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
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
                }
                Function2 function24 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                Modifier modifierFillMaxWidth2 = SizeKt.fillMaxWidth(companion, 1.0f);
                float f = 20;
                Dp.Companion companion3 = Dp.Companion;
                int i3 = i2;
                Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(modifierFillMaxWidth2, 0.0f, f, 1);
                Arrangement.INSTANCE.getClass();
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.m92spacedBy0680j_4(f), Alignment.Companion.Start, composerImpl, 6);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierM127paddingVpY3zN4$default);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, columnMeasurePolicy, function2);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                Modifier modifierWrapContentHeight$default = SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(PaddingKt.m127paddingVpY3zN4$default(companion, 80, 0.0f, 2), 1.0f), 3);
                BiasAlignment biasAlignment = Alignment.Companion.Center;
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierWrapContentHeight$default);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function2);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function22);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function24);
                int i4 = i3 & 14;
                function3.invoke(systemUIDialog, composerImpl, Integer.valueOf(i3 & 126));
                composerImpl.end(true);
                Modifier modifierWrapContentHeight$default2 = SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(PaddingKt.m127paddingVpY3zN4$default(companion, 16, 0.0f, 2), 1.0f), 3);
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, modifierWrapContentHeight$default2);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy3, function2);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope4, function22);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function23);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier4, function24);
                function32.invoke(systemUIDialog, composerImpl, Integer.valueOf(((i3 >> 3) & 112) | i4));
                composerImpl.end(true);
                composerImpl.end(true);
                Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(SizeKt.m140size3ABfNKs(boxScopeInstance.align(companion, Alignment.Companion.TopEnd), 64), f);
                IconButtonDefaults iconButtonDefaults = IconButtonDefaults.INSTANCE;
                MaterialTheme.INSTANCE.getClass();
                long j = MaterialTheme.getColorScheme(composerImpl).outline;
                iconButtonDefaults.getClass();
                IconButtonColors iconButtonColorsM267iconButtonColorsro_MJ88 = IconButtonDefaults.m267iconButtonColorsro_MJ88(0L, j, composerImpl, 13);
                composerImpl.startReplaceGroup(-867978599);
                boolean zChangedInstance = composerImpl.changedInstance(systemUIDialog);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    companion2.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new Function0() { // from class: com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                VolumePanelPopup.Companion companion4 = VolumePanelPopup.Companion;
                                systemUIDialog.dismiss();
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(false);
                    ComposableSingletons$VolumePanelPopupKt.INSTANCE.getClass();
                    IconButtonKt.IconButton(1572864, 52, null, iconButtonColorsM267iconButtonColorsro_MJ88, composerImpl, modifierM125padding3ABfNKs, null, (Function0) objRememberedValue2, ComposableSingletons$VolumePanelPopupKt.f117lambda1, false);
                    composerImpl.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).intValue();
                    VolumePanelPopup.Companion companion4 = VolumePanelPopup.Companion;
                    this.f$0.PopupComposable(systemUIDialog, function3, function32, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public final void show(Expandable expandable, int i, final ComposableLambdaImpl composableLambdaImpl, final ComposableLambdaImpl composableLambdaImpl2) {
        ComponentSystemUIDialog componentSystemUIDialogCreate$default = SystemUIDialogFactoryExtKt.create$default(this.dialogFactory, null, Integer.valueOf(i), null, new ComposableLambdaImpl(-1804575553, true, new Function3() { // from class: com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup$show$dialog$1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                SystemUIDialog systemUIDialog = (SystemUIDialog) obj;
                Composer composer = (Composer) obj2;
                int iIntValue = ((Number) obj3).intValue();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.popup.ui.composable.VolumePanelPopup.show.<anonymous> (VolumePanelPopup.kt:74)");
                }
                int i2 = iIntValue & 14;
                VolumePanelPopup.Companion companion = VolumePanelPopup.Companion;
                this.this$0.PopupComposable(systemUIDialog, composableLambdaImpl, composableLambdaImpl2, composer, i2);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }), 21);
        DialogTransitionAnimator.Controller controllerDialogTransitionController = null;
        if (expandable != null) {
            Expandable.Companion companion = Expandable.Companion;
            controllerDialogTransitionController = expandable.dialogTransitionController(null);
        }
        if (controllerDialogTransitionController == null) {
            componentSystemUIDialogCreate$default.show();
        } else {
            TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
            this.dialogTransitionAnimator.show(componentSystemUIDialogCreate$default, controllerDialogTransitionController, false);
        }
    }
}
