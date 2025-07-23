package com.android.bouncer.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Bottom$1;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
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
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.bouncer.ui.compose.theme.BouncerStyleKt;
import com.android.compose.animation.scene.TransitionDslKt;
import com.android.systemui.R;
import com.android.systemui.bouncer.ui.BouncerDialogFactory;
import com.android.systemui.bouncer.ui.composable.BouncerOverlayLayout;
import com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel;
import com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.sesl.compose.foundation.RecoilKt;
import com.samsung.sesl.compose.theme.ThemeKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SecBouncerContentKt {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        Dp.Companion companion = Dp.Companion;
        TransitionDslKt.transitions(new SecBouncerContentKt$$ExternalSyntheticLambda1(0));
    }

    public static final void PasswordLayout(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Modifier.Companion companion, Composer composer, int i) {
        int i2;
        Modifier.Companion companion2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1890050064);
        if ((i & 6) == 0) {
            i2 = i | (composerImpl.changedInstance(bouncerOverlayContentViewModel) ? 4 : 2);
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
        } else {
            companion2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.PasswordLayout (SecBouncerContent.kt:285)");
            }
            MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(bouncerOverlayContentViewModel.isInputEnabled, composerImpl);
            Alignment.Companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
            Arrangement.INSTANCE.getClass();
            Arrangement$Bottom$1 arrangement$Bottom$1 = Arrangement.Bottom;
            Modifier then = SizeKt.fillMaxWidth(companion2, 1.0f).then(SizeKt.FillWholeMaxHeight);
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Bottom$1, horizontal, composerImpl, 54);
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
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl, materializeModifier, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 1.0f, true));
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion2, 1.0f);
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            int i3 = i2;
            ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, fillMaxWidth);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, columnMeasurePolicy2, function2);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m336setimpl(composerImpl, materializeModifier2, function24);
            Modifier fillMaxWidth2 = SizeKt.fillMaxWidth(companion2, 1.0f);
            ColumnMeasurePolicy columnMeasurePolicy3 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, fillMaxWidth2);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, columnMeasurePolicy3, function2);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope3, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
            }
            Updater.m336setimpl(composerImpl, materializeModifier3, function24);
            composerImpl.startReplaceGroup(1282105201);
            if (((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue()) {
                SecHintMessage(bouncerOverlayContentViewModel.getMessage(), null, composerImpl, 0);
            }
            composerImpl.end(false);
            SecStatusMessage(bouncerOverlayContentViewModel.getMessage(), companion2, composerImpl, 48, 0);
            composerImpl.end(true);
            SpacerKt.Spacer(composerImpl, SizeKt.m130height3ABfNKs(companion2, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_input_area_top_space, composerImpl)));
            composerImpl.startReplaceGroup(-69858554);
            if (((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue()) {
                SecOutputArea(bouncerOverlayContentViewModel, SizeKt.m143width3ABfNKs(SizeKt.m130height3ABfNKs(companion2, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_output_area_height, composerImpl)), PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_output_area_width, composerImpl)), composerImpl, i3 & 14);
            }
            composerImpl.end(false);
            composerImpl.end(true);
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 1.0f, true));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new SecBouncerContentKt$$ExternalSyntheticLambda3(bouncerOverlayContentViewModel, companion2, i, 1);
        }
    }

    public static final void PatternLayout(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Modifier.Companion companion, Composer composer, int i) {
        int i2;
        Modifier.Companion companion2;
        float f;
        BouncerOverlayContentViewModel bouncerOverlayContentViewModel2 = bouncerOverlayContentViewModel;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(695824759);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(bouncerOverlayContentViewModel2) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
        } else {
            companion2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.PatternLayout (SecBouncerContent.kt:161)");
            }
            MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(bouncerOverlayContentViewModel2.isInputEnabled, composerImpl);
            Alignment.Companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
            Arrangement.INSTANCE.getClass();
            Arrangement$Bottom$1 arrangement$Bottom$1 = Arrangement.Bottom;
            Modifier then = SizeKt.wrapContentWidth$default(companion2, null, 3).then(SizeKt.FillWholeMaxHeight);
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Bottom$1, horizontal, composerImpl, 54);
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
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl, materializeModifier, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 2.0f, true));
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion2, 1.0f);
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            int i3 = i2;
            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, fillMaxWidth);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, columnMeasurePolicy2, function2);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m336setimpl(composerImpl, materializeModifier2, function24);
            composerImpl.startReplaceGroup(512272457);
            if (((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue()) {
                SecHintMessage(bouncerOverlayContentViewModel.getMessage(), null, composerImpl, 0);
            }
            composerImpl.end(false);
            SecStatusMessage(bouncerOverlayContentViewModel.getMessage(), companion2, composerImpl, 48, 0);
            composerImpl.end(true);
            composerImpl.startReplaceGroup(1104559812);
            if (((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue()) {
                SpacerKt.Spacer(composerImpl, SizeKt.m130height3ABfNKs(companion2, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pattern_input_area_top_space, composerImpl)));
                Modifier wrapContentHeight$default = SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(companion2, 1.0f), 3);
                ColumnMeasurePolicy columnMeasurePolicy3 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, wrapContentHeight$default);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, columnMeasurePolicy3, function2);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope3, function22);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
                }
                Updater.m336setimpl(composerImpl, materializeModifier3, function24);
                f = 1.0f;
                bouncerOverlayContentViewModel2 = bouncerOverlayContentViewModel;
                m900SecInputAreaDzVHIIc(bouncerOverlayContentViewModel2, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_vertical_space, composerImpl), true, null, composerImpl, (i3 & 14) | 384);
                composerImpl.end(true);
            } else {
                f = 1.0f;
                bouncerOverlayContentViewModel2 = bouncerOverlayContentViewModel;
            }
            composerImpl.end(false);
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, ((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue() ? f : 2.0f, true));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new SecBouncerContentKt$$ExternalSyntheticLambda3(bouncerOverlayContentViewModel2, companion2, i, 2);
        }
    }

    public static final void PinLayout(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Modifier.Companion companion, Composer composer, int i) {
        int i2;
        Modifier.Companion companion2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1289838162);
        if ((i & 6) == 0) {
            i2 = i | (composerImpl.changedInstance(bouncerOverlayContentViewModel) ? 4 : 2);
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
        } else {
            companion2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.PinLayout (SecBouncerContent.kt:212)");
            }
            MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(bouncerOverlayContentViewModel.isInputEnabled, composerImpl);
            Alignment.Companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
            Arrangement.INSTANCE.getClass();
            Arrangement$Bottom$1 arrangement$Bottom$1 = Arrangement.Bottom;
            Modifier then = SizeKt.wrapContentWidth$default(companion2, null, 3).then(SizeKt.FillWholeMaxHeight);
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Bottom$1, horizontal, composerImpl, 54);
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
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m336setimpl(composerImpl, columnMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl, materializeModifier, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 2.0f, true));
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(companion2, 1.0f);
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            int i3 = i2;
            ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, fillMaxWidth);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, columnMeasurePolicy2, function2);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m336setimpl(composerImpl, materializeModifier2, function24);
            Modifier fillMaxWidth2 = SizeKt.fillMaxWidth(companion2, 1.0f);
            ColumnMeasurePolicy columnMeasurePolicy3 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, fillMaxWidth2);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, columnMeasurePolicy3, function2);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope3, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
            }
            Updater.m336setimpl(composerImpl, materializeModifier3, function24);
            composerImpl.startReplaceGroup(1167041565);
            if (((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue()) {
                SecHintMessage(bouncerOverlayContentViewModel.getMessage(), null, composerImpl, 0);
            }
            composerImpl.end(false);
            Modifier wrapContentHeight$default = SizeKt.wrapContentHeight$default(companion2, 3);
            BiasAlignment biasAlignment = Alignment.Companion.TopStart;
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, wrapContentHeight$default);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, function2);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope4, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function23);
            }
            Updater.m336setimpl(composerImpl, materializeModifier4, function24);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            SecStatusMessage(bouncerOverlayContentViewModel.getMessage(), null, composerImpl, 0, 2);
            Modifier align = boxScopeInstance.align(SizeKt.wrapContentHeight$default(companion2, 3), Alignment.Companion.BottomCenter);
            MeasurePolicy maybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash5 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope5 = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier5 = ComposedModifierKt.materializeModifier(composerImpl, align);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy2, function2);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope5, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash5))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash5, composerImpl, currentCompositeKeyHash5, function23);
            }
            Updater.m336setimpl(composerImpl, materializeModifier5, function24);
            int i4 = i3 & 14;
            SecOutputArea(bouncerOverlayContentViewModel, SizeKt.fillMaxWidth(SizeKt.m130height3ABfNKs(companion2, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_output_area_height, composerImpl)), 1.0f), composerImpl, i4);
            composerImpl.end(true);
            composerImpl.end(true);
            composerImpl.end(true);
            composerImpl.end(true);
            composerImpl.startReplaceGroup(-60490196);
            if (((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue()) {
                SpacerKt.Spacer(composerImpl, SizeKt.m130height3ABfNKs(companion2, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_input_area_top_space, composerImpl)));
                Modifier wrapContentHeight$default2 = SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(companion2, 1.0f), 3);
                ColumnMeasurePolicy columnMeasurePolicy4 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
                int currentCompositeKeyHash6 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope6 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier6 = ComposedModifierKt.materializeModifier(composerImpl, wrapContentHeight$default2);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, columnMeasurePolicy4, function2);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope6, function22);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash6))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash6, composerImpl, currentCompositeKeyHash6, function23);
                }
                Updater.m336setimpl(composerImpl, materializeModifier6, function24);
                ColumnMeasurePolicy columnMeasurePolicy5 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
                int currentCompositeKeyHash7 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope7 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier7 = ComposedModifierKt.materializeModifier(composerImpl, companion2);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, columnMeasurePolicy5, function2);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope7, function22);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash7))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash7, composerImpl, currentCompositeKeyHash7, function23);
                }
                Updater.m336setimpl(composerImpl, materializeModifier7, function24);
                m900SecInputAreaDzVHIIc(bouncerOverlayContentViewModel, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_vertical_space, composerImpl), false, null, composerImpl, i4 | 384);
                composerImpl.end(true);
                composerImpl.end(true);
            }
            composerImpl.end(false);
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, ((Boolean) collectAsStateWithLifecycle.getValue()).booleanValue() ? 1.0f : 2.0f, true));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new SecBouncerContentKt$$ExternalSyntheticLambda3(bouncerOverlayContentViewModel, companion2, i, 0);
        }
    }

    public static final void SecBouncerContent(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, BouncerDialogFactory bouncerDialogFactory, Modifier modifier, Composer composer, final int i) {
        final BouncerOverlayContentViewModel bouncerOverlayContentViewModel2;
        final BouncerDialogFactory bouncerDialogFactory2;
        final Modifier modifier2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1357418919);
        int i2 = (composerImpl.changedInstance(bouncerOverlayContentViewModel) ? 4 : 2) | i | (composerImpl.changed(bouncerDialogFactory) ? 32 : 16);
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            bouncerOverlayContentViewModel2 = bouncerOverlayContentViewModel;
            bouncerDialogFactory2 = bouncerDialogFactory;
            modifier2 = modifier;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecBouncerContent (SecBouncerContent.kt:100)");
            }
            int i3 = i2 << 3;
            bouncerOverlayContentViewModel2 = bouncerOverlayContentViewModel;
            bouncerDialogFactory2 = bouncerDialogFactory;
            modifier2 = modifier;
            SecBouncerContent(BouncerOverlayLayout.STANDARD_BOUNCER, bouncerOverlayContentViewModel2, bouncerDialogFactory2, modifier2, composerImpl, (i3 & 896) | (i3 & 112) | 6 | 3072);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(bouncerDialogFactory2, modifier2, i) { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda2
                public final /* synthetic */ BouncerDialogFactory f$1;
                public final /* synthetic */ Modifier f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(385);
                    BouncerDialogFactory bouncerDialogFactory3 = this.f$1;
                    Modifier modifier3 = this.f$2;
                    SecBouncerContentKt.SecBouncerContent(BouncerOverlayContentViewModel.this, bouncerDialogFactory3, modifier3, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SecHintMessage(final BouncerMessageViewModel bouncerMessageViewModel, final Modifier.Companion companion, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1515033914);
        if ((((composerImpl.changedInstance(bouncerMessageViewModel) ? 4 : 2) | i | 48) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            companion = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecHintMessage (SecBouncerContent.kt:337)");
            }
            final MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(bouncerMessageViewModel.hintMessage, composerImpl);
            final MutableState collectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(bouncerMessageViewModel.messageColor, composerImpl);
            final float dimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.keyguard_hint_radius, composerImpl);
            if (((String) collectAsStateWithLifecycle.getValue()) != null) {
                ThemeKt.SeslTheme(false, null, ComposableLambdaKt.rememberComposableLambda(1714118394, new Function2() { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        String stringResource;
                        TextDecoration textDecoration;
                        Composer composer2 = (Composer) obj;
                        if ((((Number) obj2).intValue() & 3) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecHintMessage.<anonymous> (SecBouncerContent.kt:344)");
                        }
                        Alignment.Companion.getClass();
                        BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                        Modifier m128paddingqDBjuR0$default = PaddingKt.m128paddingqDBjuR0$default(SizeKt.wrapContentHeight$default(SizeKt.wrapContentWidth$default(Modifier.this, null, 3), 3), 0.0f, 0.0f, 0.0f, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_hint_message_bottom_padding, composer2), 7);
                        Arrangement.INSTANCE.getClass();
                        ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, horizontal, composer2, 48);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, m128paddingqDBjuR0$default);
                        ComposeUiNode.Companion.getClass();
                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                        if (composerImpl3.applier == null) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl3.startReusableNode();
                        if (composerImpl3.inserting) {
                            composerImpl3.createNode(function0);
                        } else {
                            composerImpl3.useNode();
                        }
                        Updater.m336setimpl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m336setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                        }
                        Updater.m336setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                        ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                        composerImpl3.startReplaceGroup(602223910);
                        Object rememberedValue = composerImpl3.rememberedValue();
                        Composer.Companion.getClass();
                        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                        if (rememberedValue == composer$Companion$Empty$1) {
                            rememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                            composerImpl3.updateRememberedValue(rememberedValue);
                        }
                        MutableState mutableState = (MutableState) rememberedValue;
                        composerImpl3.end(false);
                        if (((Boolean) mutableState.getValue()).booleanValue()) {
                            composerImpl3.startReplaceGroup(602227307);
                            int i2 = SecBouncerContentKt.$r8$clinit;
                            String str = (String) collectAsStateWithLifecycle.getValue();
                            if (str == null) {
                                str = "";
                            }
                            stringResource = StringResources_androidKt.stringResource(R.string.kg_password_hint_show, new Object[]{str}, composer2);
                            composerImpl3.end(false);
                        } else {
                            composerImpl3.startReplaceGroup(602232365);
                            stringResource = StringResources_androidKt.stringResource(R.string.kg_password_hint, composer2);
                            composerImpl3.end(false);
                        }
                        composerImpl3.startReplaceGroup(602237355);
                        Object rememberedValue2 = composerImpl3.rememberedValue();
                        if (rememberedValue2 == composer$Companion$Empty$1) {
                            rememberedValue2 = InteractionSourceKt.MutableInteractionSource();
                            composerImpl3.updateRememberedValue(rememberedValue2);
                        }
                        MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) rememberedValue2;
                        Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl3, false, 602241018);
                        if (m == composer$Companion$Empty$1) {
                            m = new SecBouncerContentKt$$ExternalSyntheticLambda10(mutableState, 1);
                            composerImpl3.updateRememberedValue(m);
                        }
                        composerImpl3.end(false);
                        Modifier m34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(Modifier.this, mutableInteractionSource, null, false, null, null, (Function0) m, 28);
                        composerImpl3.startReplaceGroup(602245106);
                        final float f = dimensionResource;
                        boolean changed = composerImpl3.changed(f);
                        Object rememberedValue3 = composerImpl3.rememberedValue();
                        if (changed || rememberedValue3 == composer$Companion$Empty$1) {
                            rememberedValue3 = new Function1() { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticLambda2
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo779invoke(Object obj3) {
                                    DrawScope drawScope = (DrawScope) obj3;
                                    Color.Companion.getClass();
                                    long j = Color.Transparent;
                                    float mo57toPx0680j_4 = drawScope.mo57toPx0680j_4(f);
                                    long floatToRawIntBits = (Float.floatToRawIntBits(mo57toPx0680j_4) << 32) | (Float.floatToRawIntBits(mo57toPx0680j_4) & 4294967295L);
                                    CornerRadius.Companion companion2 = CornerRadius.Companion;
                                    DrawScope.m541drawRoundRectuAw5IA$default(drawScope, j, 0L, 0L, floatToRawIntBits, null, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_getHomeScreenMode);
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl3.updateRememberedValue(rememberedValue3);
                        }
                        composerImpl3.end(false);
                        Modifier m3334seslRecoilfWhpE4E = RecoilKt.m3334seslRecoilfWhpE4E(ClipKt.clip(DrawModifierKt.drawBehind(m34clickableO2vRcR0$default, (Function1) rememberedValue3), RoundedCornerShapeKt.m186RoundedCornerShape0680j_4(f)), true, composer2, 24576);
                        composerImpl3.startReplaceGroup(232617846);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.bouncer.ui.compose.theme.HintMessageTextSytle (BouncerStyle.kt:20)");
                        }
                        TextStyle.Companion companion2 = TextStyle.Companion;
                        TextStyle m754copyp1EtxEg$default = TextStyle.m754copyp1EtxEg$default(TypeKt.getSecRegular(), 0L, BouncerStyleKt.getDpTextUnit(PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_hint_message_text_size, composerImpl3), composerImpl3), null, null, 0L, 0, 0L, null, null, 0, 16777213);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        composerImpl3.end(false);
                        int i3 = SecBouncerContentKt.$r8$clinit;
                        long j = ((Color) collectAsStateWithLifecycle2.getValue()).value;
                        if (((Boolean) mutableState.getValue()).booleanValue()) {
                            TextDecoration.Companion.getClass();
                            textDecoration = TextDecoration.None;
                        } else {
                            TextDecoration.Companion.getClass();
                            textDecoration = TextDecoration.Underline;
                        }
                        TextDecoration textDecoration2 = textDecoration;
                        TextOverflow.Companion.getClass();
                        int i4 = TextOverflow.Ellipsis;
                        TextAlign.Companion.getClass();
                        TextKt.m316Text4IGK_g(stringResource, m3334seslRecoilfWhpE4E, j, 0L, null, null, null, 0L, textDecoration2, TextAlign.m805boximpl(TextAlign.Center), 0L, i4, false, 1, 0, null, m754copyp1EtxEg$default, composer2, 0, 3120, 54520);
                        composerImpl3.end(true);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 384, 3);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(companion, i) { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda12
                public final /* synthetic */ Modifier.Companion f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    SecBouncerContentKt.SecHintMessage(BouncerMessageViewModel.this, this.f$1, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: SecInputArea-DzVHIIc, reason: not valid java name */
    public static final void m900SecInputAreaDzVHIIc(final BouncerOverlayContentViewModel bouncerOverlayContentViewModel, final float f, boolean z, Modifier.Companion companion, Composer composer, final int i) {
        int i2;
        final boolean z2;
        final Modifier.Companion companion2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-760348455);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(bouncerOverlayContentViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(f) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(z) ? 256 : 128;
        }
        int i3 = i2 | 3072;
        if ((i3 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion2 = companion;
            z2 = z;
        } else {
            Modifier.Companion companion3 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecInputArea (SecBouncerContent.kt:475)");
            }
            AuthMethodBouncerViewModel authMethodBouncerViewModel = (AuthMethodBouncerViewModel) FlowExtKt.collectAsStateWithLifecycle(bouncerOverlayContentViewModel.authMethodViewModel, composerImpl).getValue();
            if (authMethodBouncerViewModel instanceof PinBouncerViewModel) {
                composerImpl.startReplaceGroup(1053439427);
                SecPinBouncerKt.m904SecPinPaduFdPcIQ((PinBouncerViewModel) authMethodBouncerViewModel, f, companion3, composerImpl, ((i3 >> 3) & 896) | (i3 & 112));
                composerImpl.end(false);
                z2 = z;
            } else if (authMethodBouncerViewModel instanceof PatternBouncerViewModel) {
                composerImpl.startReplaceGroup(1053671958);
                SecPatternBouncerKt.SecPatternBouncer((PatternBouncerViewModel) authMethodBouncerViewModel, z, companion3, composerImpl, (i3 >> 3) & EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS, 0);
                z2 = z;
                composerImpl.end(false);
            } else {
                z2 = z;
                composerImpl.startReplaceGroup(-2044213567);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion2 = companion3;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    BouncerOverlayContentViewModel bouncerOverlayContentViewModel2 = BouncerOverlayContentViewModel.this;
                    boolean z3 = z2;
                    Modifier.Companion companion4 = companion2;
                    SecBouncerContentKt.m900SecInputAreaDzVHIIc(bouncerOverlayContentViewModel2, f, z3, companion4, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x0092, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SecOutputArea(com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel r5, androidx.compose.ui.Modifier r6, androidx.compose.runtime.Composer r7, int r8) {
        /*
            androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
            r0 = 1162123282(0x45449812, float:3145.5044)
            r7.startRestartGroup(r0)
            r0 = r8 & 6
            if (r0 != 0) goto L17
            boolean r0 = r7.changedInstance(r5)
            if (r0 == 0) goto L14
            r0 = 4
            goto L15
        L14:
            r0 = 2
        L15:
            r0 = r0 | r8
            goto L18
        L17:
            r0 = r8
        L18:
            r1 = r8 & 48
            if (r1 != 0) goto L28
            boolean r1 = r7.changed(r6)
            if (r1 == 0) goto L25
            r1 = 32
            goto L27
        L25:
            r1 = 16
        L27:
            r0 = r0 | r1
        L28:
            r1 = r0 & 19
            r2 = 18
            if (r1 != r2) goto L3a
            boolean r1 = r7.getSkipping()
            if (r1 != 0) goto L35
            goto L3a
        L35:
            r7.skipToGroupEnd()
            goto Lc1
        L3a:
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto L45
            java.lang.String r1 = "com.android.bouncer.ui.composable.SecOutputArea (SecBouncerContent.kt:440)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r1)
        L45:
            kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r5.authMethodViewModel
            androidx.compose.runtime.MutableState r1 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r1, r7)
            androidx.compose.runtime.StaticProvidableCompositionLocal r2 = androidx.compose.ui.platform.CompositionLocalsKt.LocalFocusManager
            java.lang.Object r2 = r7.consume(r2)
            androidx.compose.ui.focus.FocusManager r2 = (androidx.compose.ui.focus.FocusManager) r2
            java.lang.Object r1 = r1.getValue()
            com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel r1 = (com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel) r1
            boolean r3 = r1 instanceof com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel
            r4 = 0
            if (r3 == 0) goto L6f
            r2 = 148557913(0x8dad059, float:1.316938E-33)
            r7.startReplaceGroup(r2)
            com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel r1 = (com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel) r1
            r0 = r0 & 112(0x70, float:1.57E-43)
            com.android.systemui.bouncer.ui.composable.PinInputDisplayKt.PinInputDisplay(r1, r6, r7, r0)
            r7.end(r4)
            goto Lb8
        L6f:
            boolean r0 = r1 instanceof com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel
            if (r0 == 0) goto Laf
            r0 = 148563304(0x8dae568, float:1.3174331E-33)
            r7.startReplaceGroup(r0)
            com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel r1 = (com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel) r1
            r0 = 148566761(0x8daf2e9, float:1.3177506E-33)
            r7.startReplaceGroup(r0)
            boolean r0 = r7.changedInstance(r2)
            java.lang.Object r3 = r7.rememberedValue()
            if (r0 != 0) goto L94
            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r0) goto L9d
        L94:
            com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda10 r3 = new com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda10
            r0 = 0
            r3.<init>(r2, r0)
            r7.updateRememberedValue(r3)
        L9d:
            kotlin.jvm.functions.Function0 r3 = (kotlin.jvm.functions.Function0) r3
            r7.end(r4)
            r0 = 7
            r2 = 0
            androidx.compose.ui.Modifier r0 = androidx.compose.foundation.ClickableKt.m35clickableXHw0xAI$default(r6, r4, r2, r3, r0)
            com.android.bouncer.ui.composable.SecPasswordBouncerKt.SecPasswordBouncer(r1, r0, r7, r4)
            r7.end(r4)
            goto Lb8
        Laf:
            r0 = 148569836(0x8dafeec, float:1.318033E-33)
            r7.startReplaceGroup(r0)
            r7.end(r4)
        Lb8:
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lc1
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lc1:
            androidx.compose.runtime.RecomposeScopeImpl r7 = r7.endRestartGroup()
            if (r7 == 0) goto Lcf
            com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda3 r0 = new com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda3
            r1 = 3
            r0.<init>(r5, r6, r8, r1)
            r7.block = r0
        Lcf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.bouncer.ui.composable.SecBouncerContentKt.SecOutputArea(com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0070, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SecStatusMessage(final com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r9, final androidx.compose.ui.Modifier.Companion r10, androidx.compose.runtime.Composer r11, final int r12, final int r13) {
        /*
            r5 = r11
            androidx.compose.runtime.ComposerImpl r5 = (androidx.compose.runtime.ComposerImpl) r5
            r11 = -1811759109(0xffffffff9402bffb, float:-6.601184E-27)
            r5.startRestartGroup(r11)
            boolean r11 = r5.changedInstance(r9)
            if (r11 == 0) goto L11
            r11 = 4
            goto L12
        L11:
            r11 = 2
        L12:
            r11 = r11 | r12
            r0 = r13 & 2
            if (r0 == 0) goto L1a
            r11 = r11 | 48
            goto L2a
        L1a:
            r1 = r12 & 48
            if (r1 != 0) goto L2a
            boolean r1 = r5.changed(r10)
            if (r1 == 0) goto L27
            r1 = 32
            goto L29
        L27:
            r1 = 16
        L29:
            r11 = r11 | r1
        L2a:
            r11 = r11 & 19
            r1 = 18
            if (r11 != r1) goto L3c
            boolean r11 = r5.getSkipping()
            if (r11 != 0) goto L37
            goto L3c
        L37:
            r5.skipToGroupEnd()
            goto Lc9
        L3c:
            if (r0 == 0) goto L40
            androidx.compose.ui.Modifier$Companion r10 = androidx.compose.ui.Modifier.Companion
        L40:
            boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r11 == 0) goto L4b
            java.lang.String r11 = "com.android.bouncer.ui.composable.SecStatusMessage (SecBouncerContent.kt:389)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r11)
        L4b:
            kotlinx.coroutines.flow.ReadonlyStateFlow r11 = r9.messageColor
            androidx.compose.runtime.MutableState r11 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r11, r5)
            kotlinx.coroutines.flow.StateFlowImpl r0 = r9.message
            androidx.compose.runtime.MutableState r0 = androidx.lifecycle.compose.FlowExtKt.collectAsStateWithLifecycle(r0, r5)
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            r2 = 1658309747(0x62d7cc73, float:1.990391E21)
            r5.startReplaceGroup(r2)
            boolean r2 = r5.changedInstance(r9)
            java.lang.Object r3 = r5.rememberedValue()
            if (r2 != 0) goto L72
            androidx.compose.runtime.Composer$Companion r2 = androidx.compose.runtime.Composer.Companion
            r2.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r2 = androidx.compose.runtime.Composer.Companion.Empty
            if (r3 != r2) goto L7a
        L72:
            com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda8 r3 = new com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda8
            r3.<init>()
            r5.updateRememberedValue(r3)
        L7a:
            kotlin.jvm.functions.Function1 r3 = (kotlin.jvm.functions.Function1) r3
            r2 = 0
            r5.end(r2)
            androidx.compose.runtime.EffectsKt.DisposableEffect(r1, r3, r5)
            java.lang.Object r1 = r0.getValue()
            com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r1 = (com.android.systemui.bouncer.ui.viewmodel.MessageViewModel) r1
            java.lang.Object r0 = r0.getValue()
            com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r0 = (com.android.systemui.bouncer.ui.viewmodel.MessageViewModel) r0
            if (r0 == 0) goto L9e
            boolean r0 = r0.isUpdateAnimated
            r3 = 1
            if (r0 != r3) goto L9e
            r0 = 7
            r3 = 0
            androidx.compose.animation.core.TweenSpec r0 = androidx.compose.animation.core.AnimationSpecKt.tween$default(r2, r2, r3, r0)
        L9c:
            r2 = r0
            goto La3
        L9e:
            androidx.compose.animation.core.SnapSpec r0 = androidx.compose.animation.core.AnimationSpecKt.snap$default()
            goto L9c
        La3:
            r0 = 1065353216(0x3f800000, float:1.0)
            androidx.compose.ui.Modifier r0 = androidx.compose.foundation.layout.SizeKt.fillMaxWidth(r10, r0)
            com.android.bouncer.ui.composable.SecBouncerContentKt$SecStatusMessage$2 r3 = new com.android.bouncer.ui.composable.SecBouncerContentKt$SecStatusMessage$2
            r3.<init>()
            r11 = -1037506415(0xffffffffc228e891, float:-42.227116)
            androidx.compose.runtime.internal.ComposableLambdaImpl r4 = androidx.compose.runtime.internal.ComposableLambdaKt.rememberComposableLambda(r11, r3, r5)
            r7 = 0
            java.lang.String r3 = "Bouncer message"
            r6 = 27648(0x6c00, float:3.8743E-41)
            r8 = r1
            r1 = r0
            r0 = r8
            androidx.compose.animation.CrossfadeKt.Crossfade(r0, r1, r2, r3, r4, r5, r6, r7)
            boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r11 == 0) goto Lc9
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        Lc9:
            androidx.compose.runtime.RecomposeScopeImpl r11 = r5.endRestartGroup()
            if (r11 == 0) goto Ld6
            com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda9 r0 = new com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda9
            r0.<init>()
            r11.block = r0
        Ld6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.bouncer.ui.composable.SecBouncerContentKt.SecStatusMessage(com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel, androidx.compose.ui.Modifier$Companion, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x007d, code lost:
    
        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L37;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SecBouncerContent(final com.android.systemui.bouncer.ui.composable.BouncerOverlayLayout r10, final com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel r11, final com.android.systemui.bouncer.ui.BouncerDialogFactory r12, final androidx.compose.ui.Modifier r13, androidx.compose.runtime.Composer r14, final int r15) {
        /*
            Method dump skipped, instructions count: 407
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.bouncer.ui.composable.SecBouncerContentKt.SecBouncerContent(com.android.systemui.bouncer.ui.composable.BouncerOverlayLayout, com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel, com.android.systemui.bouncer.ui.BouncerDialogFactory, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }
}
