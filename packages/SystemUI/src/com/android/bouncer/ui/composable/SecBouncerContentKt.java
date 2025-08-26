package com.android.bouncer.ui.composable;

import android.content.res.Resources;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.CrossfadeKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.BackgroundKt;
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
import androidx.compose.foundation.layout.WindowInsetsPadding_androidKt$imePadding$$inlined$windowInsetsPadding$1;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.focus.FocusManager;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.input.key.KeyInputModifierKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.bouncer.ui.compose.theme.BouncerStyleKt;
import com.android.compose.animation.scene.TransitionDslKt;
import com.android.systemui.R;
import com.android.systemui.bouncer.ui.BouncerDialogFactory;
import com.android.systemui.bouncer.ui.composable.BouncerOverlayLayout;
import com.android.systemui.bouncer.ui.composable.PinInputDisplayKt;
import com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel;
import com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel;
import com.android.systemui.bouncer.ui.viewmodel.MessageViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.sesl.compose.foundation.RecoilKt;
import com.samsung.sesl.compose.theme.ThemeKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

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
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(bouncerOverlayContentViewModel.isInputEnabled, composerImpl);
            Alignment.Companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
            Arrangement.INSTANCE.getClass();
            Arrangement$Bottom$1 arrangement$Bottom$1 = Arrangement.Bottom;
            Modifier modifierThen = SizeKt.fillMaxWidth(companion2, 1.0f).then(SizeKt.FillWholeMaxHeight);
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Bottom$1, horizontal, composerImpl, 54);
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
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 1.0f, true));
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion2, 1.0f);
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            int i3 = i2;
            ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxWidth);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, columnMeasurePolicy2, function2);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
            Modifier modifierFillMaxWidth2 = SizeKt.fillMaxWidth(companion2, 1.0f);
            ColumnMeasurePolicy columnMeasurePolicy3 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxWidth2);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, columnMeasurePolicy3, function2);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function24);
            composerImpl.startReplaceGroup(1282105201);
            if (((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue()) {
                SecHintMessage(bouncerOverlayContentViewModel.getMessage(), null, composerImpl, 0);
            }
            composerImpl.end(false);
            SecStatusMessage(bouncerOverlayContentViewModel.getMessage(), companion2, composerImpl, 48, 0);
            composerImpl.end(true);
            SpacerKt.Spacer(composerImpl, SizeKt.m131height3ABfNKs(companion2, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_input_area_top_space, composerImpl)));
            composerImpl.startReplaceGroup(-69858554);
            if (((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue()) {
                SecOutputArea(bouncerOverlayContentViewModel, SizeKt.m144width3ABfNKs(SizeKt.m131height3ABfNKs(companion2, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_output_area_height, composerImpl)), PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_output_area_width, composerImpl)), composerImpl, i3 & 14);
            }
            composerImpl.end(false);
            composerImpl.end(true);
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 1.0f, true));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new SecBouncerContentKt$$ExternalSyntheticLambda3(bouncerOverlayContentViewModel, companion2, i, 1);
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
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(bouncerOverlayContentViewModel2.isInputEnabled, composerImpl);
            Alignment.Companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
            Arrangement.INSTANCE.getClass();
            Arrangement$Bottom$1 arrangement$Bottom$1 = Arrangement.Bottom;
            Modifier modifierThen = SizeKt.wrapContentWidth$default(companion2, null, 3).then(SizeKt.FillWholeMaxHeight);
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Bottom$1, horizontal, composerImpl, 54);
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
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 2.0f, true));
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion2, 1.0f);
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            int i3 = i2;
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxWidth);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, columnMeasurePolicy2, function2);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
            composerImpl.startReplaceGroup(512272457);
            if (((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue()) {
                SecHintMessage(bouncerOverlayContentViewModel.getMessage(), null, composerImpl, 0);
            }
            composerImpl.end(false);
            SecStatusMessage(bouncerOverlayContentViewModel.getMessage(), companion2, composerImpl, 48, 0);
            composerImpl.end(true);
            composerImpl.startReplaceGroup(1104559812);
            if (((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue()) {
                SpacerKt.Spacer(composerImpl, SizeKt.m131height3ABfNKs(companion2, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pattern_input_area_top_space, composerImpl)));
                Modifier modifierWrapContentHeight$default = SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(companion2, 1.0f), 3);
                ColumnMeasurePolicy columnMeasurePolicy3 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierWrapContentHeight$default);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, columnMeasurePolicy3, function2);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function22);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function24);
                f = 1.0f;
                bouncerOverlayContentViewModel2 = bouncerOverlayContentViewModel;
                m902SecInputAreaDzVHIIc(bouncerOverlayContentViewModel2, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_vertical_space, composerImpl), true, null, composerImpl, (i3 & 14) | 384);
                composerImpl.end(true);
            } else {
                f = 1.0f;
                bouncerOverlayContentViewModel2 = bouncerOverlayContentViewModel;
            }
            composerImpl.end(false);
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, ((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue() ? f : 2.0f, true));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new SecBouncerContentKt$$ExternalSyntheticLambda3(bouncerOverlayContentViewModel2, companion2, i, 2);
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
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(bouncerOverlayContentViewModel.isInputEnabled, composerImpl);
            Alignment.Companion.getClass();
            BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
            Arrangement.INSTANCE.getClass();
            Arrangement$Bottom$1 arrangement$Bottom$1 = Arrangement.Bottom;
            Modifier modifierThen = SizeKt.wrapContentWidth$default(companion2, null, 3).then(SizeKt.FillWholeMaxHeight);
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Bottom$1, horizontal, composerImpl, 54);
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
            Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m337setimpl(composerImpl, columnMeasurePolicy, function2);
            Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
            }
            Function2 function24 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
            ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 2.0f, true));
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion2, 1.0f);
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            int i3 = i2;
            ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxWidth);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, columnMeasurePolicy2, function2);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
            Modifier modifierFillMaxWidth2 = SizeKt.fillMaxWidth(companion2, 1.0f);
            ColumnMeasurePolicy columnMeasurePolicy3 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierFillMaxWidth2);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, columnMeasurePolicy3, function2);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function24);
            composerImpl.startReplaceGroup(1167041565);
            if (((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue()) {
                SecHintMessage(bouncerOverlayContentViewModel.getMessage(), null, composerImpl, 0);
            }
            composerImpl.end(false);
            Modifier modifierWrapContentHeight$default = SizeKt.wrapContentHeight$default(companion2, 3);
            BiasAlignment biasAlignment = Alignment.Companion.TopStart;
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, modifierWrapContentHeight$default);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope4, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier4, function24);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            SecStatusMessage(bouncerOverlayContentViewModel.getMessage(), null, composerImpl, 0, 2);
            Modifier modifierAlign = boxScopeInstance.align(SizeKt.wrapContentHeight$default(companion2, 3), Alignment.Companion.BottomCenter);
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
            int currentCompositeKeyHash5 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope5 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier5 = ComposedModifierKt.materializeModifier(composerImpl, modifierAlign);
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function2);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope5, function22);
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash5))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash5, composerImpl, currentCompositeKeyHash5, function23);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier5, function24);
            int i4 = i3 & 14;
            SecOutputArea(bouncerOverlayContentViewModel, SizeKt.fillMaxWidth(SizeKt.m131height3ABfNKs(companion2, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_output_area_height, composerImpl)), 1.0f), composerImpl, i4);
            composerImpl.end(true);
            composerImpl.end(true);
            composerImpl.end(true);
            composerImpl.end(true);
            composerImpl.startReplaceGroup(-60490196);
            if (((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue()) {
                SpacerKt.Spacer(composerImpl, SizeKt.m131height3ABfNKs(companion2, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_input_area_top_space, composerImpl)));
                Modifier modifierWrapContentHeight$default2 = SizeKt.wrapContentHeight$default(SizeKt.fillMaxWidth(companion2, 1.0f), 3);
                ColumnMeasurePolicy columnMeasurePolicy4 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
                int currentCompositeKeyHash6 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope6 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier6 = ComposedModifierKt.materializeModifier(composerImpl, modifierWrapContentHeight$default2);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, columnMeasurePolicy4, function2);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope6, function22);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash6))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash6, composerImpl, currentCompositeKeyHash6, function23);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier6, function24);
                ColumnMeasurePolicy columnMeasurePolicy5 = ColumnKt.columnMeasurePolicy(arrangement$Top$1, horizontal, composerImpl, 48);
                int currentCompositeKeyHash7 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope7 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier7 = ComposedModifierKt.materializeModifier(composerImpl, companion2);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, columnMeasurePolicy5, function2);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope7, function22);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash7))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash7, composerImpl, currentCompositeKeyHash7, function23);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier7, function24);
                m902SecInputAreaDzVHIIc(bouncerOverlayContentViewModel, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_vertical_space, composerImpl), false, null, composerImpl, i4 | 384);
                composerImpl.end(true);
                composerImpl.end(true);
            }
            composerImpl.end(false);
            SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, ((Boolean) mutableStateCollectAsStateWithLifecycle.getValue()).booleanValue() ? 1.0f : 2.0f, true));
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new SecBouncerContentKt$$ExternalSyntheticLambda3(bouncerOverlayContentViewModel, companion2, i, 0);
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(bouncerDialogFactory2, modifier2, i) { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda2
                public final /* synthetic */ BouncerDialogFactory f$1;
                public final /* synthetic */ Modifier f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(385);
                    BouncerDialogFactory bouncerDialogFactory3 = this.f$1;
                    Modifier modifier3 = this.f$2;
                    SecBouncerContentKt.SecBouncerContent(this.f$0, bouncerDialogFactory3, modifier3, (Composer) obj, iUpdateChangedFlags);
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
            final MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(bouncerMessageViewModel.hintMessage, composerImpl);
            final MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(bouncerMessageViewModel.messageColor, composerImpl);
            final float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.keyguard_hint_radius, composerImpl);
            if (((String) mutableStateCollectAsStateWithLifecycle.getValue()) != null) {
                ThemeKt.SeslTheme(false, null, ComposableLambdaKt.rememberComposableLambda(1714118394, new Function2() { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt.SecHintMessage.1
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2) throws Resources.NotFoundException {
                        String strStringResource;
                        TextDecoration textDecoration;
                        Composer composer2 = (Composer) obj;
                        if ((((Number) obj2).intValue() & 3) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecHintMessage.<anonymous> (SecBouncerContent.kt:344)");
                                }
                                Alignment.Companion.getClass();
                                BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                                Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(SizeKt.wrapContentHeight$default(SizeKt.wrapContentWidth$default(companion, null, 3), 3), 0.0f, 0.0f, 0.0f, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_hint_message_bottom_padding, composer2), 7);
                                Arrangement.INSTANCE.getClass();
                                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, horizontal, composer2, 48);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierM129paddingqDBjuR0$default);
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
                                Updater.m337setimpl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                                }
                                Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                                composerImpl3.startReplaceGroup(602223910);
                                Object objRememberedValue = composerImpl3.rememberedValue();
                                Composer.Companion.getClass();
                                Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                                if (objRememberedValue == composer$Companion$Empty$1) {
                                    objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                }
                                MutableState mutableState = (MutableState) objRememberedValue;
                                composerImpl3.end(false);
                                if (((Boolean) mutableState.getValue()).booleanValue()) {
                                    composerImpl3.startReplaceGroup(602227307);
                                    int i2 = SecBouncerContentKt.$r8$clinit;
                                    String str = (String) mutableStateCollectAsStateWithLifecycle.getValue();
                                    if (str == null) {
                                        str = "";
                                    }
                                    strStringResource = StringResources_androidKt.stringResource(R.string.kg_password_hint_show, new Object[]{str}, composer2);
                                    composerImpl3.end(false);
                                } else {
                                    composerImpl3.startReplaceGroup(602232365);
                                    strStringResource = StringResources_androidKt.stringResource(R.string.kg_password_hint, composer2);
                                    composerImpl3.end(false);
                                }
                                composerImpl3.startReplaceGroup(602237355);
                                Object objRememberedValue2 = composerImpl3.rememberedValue();
                                if (objRememberedValue2 == composer$Companion$Empty$1) {
                                    objRememberedValue2 = InteractionSourceKt.MutableInteractionSource();
                                    composerImpl3.updateRememberedValue(objRememberedValue2);
                                }
                                MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) objRememberedValue2;
                                Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl3, false, 602241018);
                                if (objM == composer$Companion$Empty$1) {
                                    objM = new SecBouncerContentKt$$ExternalSyntheticLambda10(mutableState, 1);
                                    composerImpl3.updateRememberedValue(objM);
                                }
                                composerImpl3.end(false);
                                Modifier modifierM34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(companion, mutableInteractionSource, null, false, null, null, (Function0) objM, 28);
                                composerImpl3.startReplaceGroup(602245106);
                                final float f = fDimensionResource;
                                boolean zChanged = composerImpl3.changed(f);
                                Object objRememberedValue3 = composerImpl3.rememberedValue();
                                if (zChanged || objRememberedValue3 == composer$Companion$Empty$1) {
                                    objRememberedValue3 = new Function1() { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticLambda2
                                        @Override // kotlin.jvm.functions.Function1
                                        /* renamed from: invoke */
                                        public final Object mo781invoke(Object obj3) {
                                            DrawScope drawScope = (DrawScope) obj3;
                                            Color.Companion.getClass();
                                            long j = Color.Transparent;
                                            float fMo58toPx0680j_4 = drawScope.mo58toPx0680j_4(f);
                                            long jFloatToRawIntBits = (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(fMo58toPx0680j_4) & 4294967295L);
                                            CornerRadius.Companion companion2 = CornerRadius.Companion;
                                            DrawScope.m543drawRoundRectuAw5IA$default(drawScope, j, 0L, 0L, jFloatToRawIntBits, null, 0.0f, IKnoxCustomManager.Stub.TRANSACTION_getHomeScreenMode);
                                            return Unit.INSTANCE;
                                        }
                                    };
                                    composerImpl3.updateRememberedValue(objRememberedValue3);
                                }
                                composerImpl3.end(false);
                                Modifier modifierM3351seslRecoilfWhpE4E = RecoilKt.m3351seslRecoilfWhpE4E(ClipKt.clip(DrawModifierKt.drawBehind(modifierM34clickableO2vRcR0$default, (Function1) objRememberedValue3), RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(f)), true, composer2, 24576);
                                composerImpl3.startReplaceGroup(232617846);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.bouncer.ui.compose.theme.HintMessageTextSytle (BouncerStyle.kt:20)");
                                }
                                TextStyle.Companion companion2 = TextStyle.Companion;
                                TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(TypeKt.getSecRegular(), 0L, BouncerStyleKt.getDpTextUnit(PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_hint_message_text_size, composerImpl3), composerImpl3), null, null, 0L, 0, 0L, null, null, 0, 16777213);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                composerImpl3.end(false);
                                int i3 = SecBouncerContentKt.$r8$clinit;
                                long j = ((Color) mutableStateCollectAsStateWithLifecycle2.getValue()).value;
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
                                TextKt.m317Text4IGK_g(strStringResource, modifierM3351seslRecoilfWhpE4E, j, 0L, null, null, null, 0L, textDecoration2, TextAlign.m807boximpl(TextAlign.Center), 0L, i4, false, 1, 0, null, textStyleM756copyp1EtxEg$default, composer2, 0, 3120, 54520);
                                composerImpl3.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 384, 3);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(companion, i) { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda12
                public final /* synthetic */ Modifier.Companion f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    SecBouncerContentKt.SecHintMessage(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: SecInputArea-DzVHIIc, reason: not valid java name */
    public static final void m902SecInputAreaDzVHIIc(final BouncerOverlayContentViewModel bouncerOverlayContentViewModel, final float f, boolean z, Modifier.Companion companion, Composer composer, final int i) throws Resources.NotFoundException {
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
                SecPinBouncerKt.m906SecPinPaduFdPcIQ((PinBouncerViewModel) authMethodBouncerViewModel, f, companion3, composerImpl, ((i3 >> 3) & 896) | (i3 & 112));
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Resources.NotFoundException {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    BouncerOverlayContentViewModel bouncerOverlayContentViewModel2 = bouncerOverlayContentViewModel;
                    boolean z3 = z2;
                    Modifier.Companion companion4 = companion2;
                    SecBouncerContentKt.m902SecInputAreaDzVHIIc(bouncerOverlayContentViewModel2, f, z3, companion4, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SecOutputArea(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Modifier modifier, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1162123282);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(bouncerOverlayContentViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecOutputArea (SecBouncerContent.kt:440)");
            }
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(bouncerOverlayContentViewModel.authMethodViewModel, composerImpl);
            FocusManager focusManager = (FocusManager) composerImpl.consume(CompositionLocalsKt.LocalFocusManager);
            AuthMethodBouncerViewModel authMethodBouncerViewModel = (AuthMethodBouncerViewModel) mutableStateCollectAsStateWithLifecycle.getValue();
            if (authMethodBouncerViewModel instanceof PinBouncerViewModel) {
                composerImpl.startReplaceGroup(148557913);
                PinInputDisplayKt.PinInputDisplay((PinBouncerViewModel) authMethodBouncerViewModel, modifier, composerImpl, i2 & 112);
                composerImpl.end(false);
            } else if (authMethodBouncerViewModel instanceof PasswordBouncerViewModel) {
                composerImpl.startReplaceGroup(148563304);
                PasswordBouncerViewModel passwordBouncerViewModel = (PasswordBouncerViewModel) authMethodBouncerViewModel;
                composerImpl.startReplaceGroup(148566761);
                boolean zChangedInstance = composerImpl.changedInstance(focusManager);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    Composer.Companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new SecBouncerContentKt$$ExternalSyntheticLambda10(focusManager, 0);
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    composerImpl.end(false);
                    SecPasswordBouncerKt.SecPasswordBouncer(passwordBouncerViewModel, ClickableKt.m35clickableXHw0xAI$default(modifier, false, null, (Function0) objRememberedValue, 7), composerImpl, 0);
                    composerImpl.end(false);
                }
            } else {
                composerImpl.startReplaceGroup(148569836);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new SecBouncerContentKt$$ExternalSyntheticLambda3(bouncerOverlayContentViewModel, modifier, i, 3);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SecStatusMessage(final BouncerMessageViewModel bouncerMessageViewModel, final Modifier.Companion companion, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1811759109);
        int i3 = (composerImpl.changedInstance(bouncerMessageViewModel) ? 4 : 2) | i;
        int i4 = i2 & 2;
        if (i4 != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(companion) ? 32 : 16;
        }
        if ((i3 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i4 != 0) {
                companion = Modifier.Companion;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecStatusMessage (SecBouncerContent.kt:389)");
            }
            final MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(bouncerMessageViewModel.messageColor, composerImpl);
            MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(bouncerMessageViewModel.message, composerImpl);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(1658309747);
            boolean zChangedInstance = composerImpl.changedInstance(bouncerMessageViewModel);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function1() { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda8
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            bouncerMessageViewModel.resetToDefault.tryEmit(Boolean.TRUE);
                            return new DisposableEffectResult() { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt$SecStatusMessage$lambda$31$lambda$30$$inlined$onDispose$1
                                @Override // androidx.compose.runtime.DisposableEffectResult
                                public final void dispose() {
                                }
                            };
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                EffectsKt.DisposableEffect(unit, (Function1) objRememberedValue, composerImpl);
                MessageViewModel messageViewModel = (MessageViewModel) mutableStateCollectAsStateWithLifecycle2.getValue();
                MessageViewModel messageViewModel2 = (MessageViewModel) mutableStateCollectAsStateWithLifecycle2.getValue();
                CrossfadeKt.Crossfade(messageViewModel, SizeKt.fillMaxWidth(companion, 1.0f), (messageViewModel2 == null || !messageViewModel2.isUpdateAnimated) ? AnimationSpecKt.snap$default() : AnimationSpecKt.tween$default(0, 0, null, 7), "Bouncer message", ComposableLambdaKt.rememberComposableLambda(-1037506415, new Function3() { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt.SecStatusMessage.2
                    /* JADX WARN: Removed duplicated region for block: B:15:0x0037  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        ComposerImpl composerImpl2;
                        MessageViewModel messageViewModel3 = (MessageViewModel) obj;
                        Composer composer2 = (Composer) obj2;
                        int iIntValue = ((Number) obj3).intValue();
                        if ((iIntValue & 6) == 0) {
                            iIntValue |= ((ComposerImpl) composer2).changed(messageViewModel3) ? 4 : 2;
                        }
                        if ((iIntValue & 19) == 18) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            if (composerImpl3.getSkipping()) {
                                composerImpl3.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecStatusMessage.<anonymous> (SecBouncerContent.kt:406)");
                                }
                                Alignment.Companion.getClass();
                                BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                                Arrangement.INSTANCE.getClass();
                                Arrangement$Bottom$1 arrangement$Bottom$1 = Arrangement.Bottom;
                                Modifier.Companion companion2 = Modifier.Companion;
                                Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(companion2, 1.0f);
                                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Bottom$1, horizontal, composer2, 54);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierFillMaxWidth);
                                ComposeUiNode.Companion.getClass();
                                Function0 function0 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl4.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl4.startReusableNode();
                                if (composerImpl4.inserting) {
                                    composerImpl4.createNode(function0);
                                } else {
                                    composerImpl4.useNode();
                                }
                                Updater.m337setimpl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function2);
                                }
                                Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                                composerImpl4.startReplaceGroup(192227604);
                                if (messageViewModel3 == null) {
                                    composerImpl2 = composerImpl4;
                                } else {
                                    composerImpl4.startReplaceGroup(1653079172);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.bouncer.ui.compose.theme.MainMessageTextSytle (BouncerStyle.kt:25)");
                                    }
                                    TextStyle.Companion companion3 = TextStyle.Companion;
                                    TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(TypeKt.getSecRegular(), 0L, BouncerStyleKt.getDpTextUnit(PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_main_message_text_size, composerImpl4), composerImpl4), null, null, 0L, 0, 0L, null, null, 0, 16777213);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    composerImpl4.end(false);
                                    int i5 = SecBouncerContentKt.$r8$clinit;
                                    State state = mutableStateCollectAsStateWithLifecycle;
                                    long j = ((Color) state.getValue()).value;
                                    long sp = TextUnitKt.getSp(24);
                                    TextOverflow.Companion.getClass();
                                    int i6 = TextOverflow.Ellipsis;
                                    TextAlign.Companion.getClass();
                                    TextKt.m317Text4IGK_g(messageViewModel3.text, null, j, 0L, null, null, null, 0L, null, TextAlign.m807boximpl(TextAlign.Center), sp, i6, false, 0, 0, null, textStyleM756copyp1EtxEg$default, composer2, 0, 54, 61946);
                                    Dp.Companion companion4 = Dp.Companion;
                                    SpacerKt.Spacer(composer2, SizeKt.m140size3ABfNKs(companion2, 4));
                                    String str = messageViewModel3.secondaryText;
                                    if (str == null) {
                                        str = "";
                                    }
                                    long j2 = ((Color) state.getValue()).value;
                                    composerImpl4.startReplaceGroup(315893625);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.bouncer.ui.compose.theme.SubMessageTextSytle (BouncerStyle.kt:31)");
                                    }
                                    TextStyle textStyleM756copyp1EtxEg$default2 = TextStyle.m756copyp1EtxEg$default(TypeKt.getSecRegular(), 0L, BouncerStyleKt.getDpTextUnit(PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_sub_message_text_size, composerImpl4), composerImpl4), null, null, 0L, 0, 0L, null, null, 0, 16777213);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    composerImpl4.end(false);
                                    String str2 = str;
                                    composerImpl2 = composerImpl4;
                                    TextKt.m317Text4IGK_g(str2, null, j2, 0L, null, null, null, 0L, null, null, TextUnitKt.getSp(20), i6, false, 2, 0, null, textStyleM756copyp1EtxEg$default2, composer2, 0, 3126, 54266);
                                }
                                if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, false, true)) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 27648, 0);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Modifier.Companion companion2 = companion;
                    int i5 = i2;
                    SecBouncerContentKt.SecStatusMessage(bouncerMessageViewModel, companion2, (Composer) obj, iUpdateChangedFlags, i5);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SecBouncerContent(final BouncerOverlayLayout bouncerOverlayLayout, final BouncerOverlayContentViewModel bouncerOverlayContentViewModel, final BouncerDialogFactory bouncerDialogFactory, final Modifier modifier, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(947773847);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(bouncerOverlayLayout) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(bouncerOverlayContentViewModel) ? 32 : 16;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(modifier) ? 2048 : 1024;
        }
        if ((i2 & 1043) == 1042 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecBouncerContent (SecBouncerContent.kt:111)");
            }
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(bouncerOverlayContentViewModel.authMethodViewModel, composerImpl);
            Modifier modifierComposed = ComposedModifierKt.composed(modifier, InspectableValueKt.NoInspectorInfo, new WindowInsetsPadding_androidKt$imePadding$$inlined$windowInsetsPadding$1());
            composerImpl.startReplaceGroup(-1335788194);
            boolean zChangedInstance = composerImpl.changedInstance(bouncerOverlayContentViewModel);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new SecBouncerContentKt$SecBouncerContent$2$1(bouncerOverlayContentViewModel);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierOnKeyEvent = KeyInputModifierKt.onKeyEvent(modifierComposed, (Function1) ((KFunction) objRememberedValue));
                Color.Companion.getClass();
                Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(modifierOnKeyEvent, Color.Transparent, RectangleShapeKt.RectangleShape);
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM26backgroundbw27NRU);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (composerImpl.applier != null) {
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
                    composerImpl.startReplaceGroup(-1851421155);
                    if (bouncerOverlayLayout == BouncerOverlayLayout.STANDARD_BOUNCER) {
                        AuthMethodBouncerViewModel authMethodBouncerViewModel = (AuthMethodBouncerViewModel) mutableStateCollectAsStateWithLifecycle.getValue();
                        if (authMethodBouncerViewModel instanceof PinBouncerViewModel) {
                            composerImpl.startReplaceGroup(-1559322669);
                            PinLayout(bouncerOverlayContentViewModel, null, composerImpl, (i2 >> 3) & 14);
                            composerImpl.end(false);
                        } else if (authMethodBouncerViewModel instanceof PatternBouncerViewModel) {
                            composerImpl.startReplaceGroup(-1559138033);
                            PatternLayout(bouncerOverlayContentViewModel, null, composerImpl, (i2 >> 3) & 14);
                            composerImpl.end(false);
                        } else if (authMethodBouncerViewModel instanceof PasswordBouncerViewModel) {
                            composerImpl.startReplaceGroup(-1558948530);
                            PasswordLayout(bouncerOverlayContentViewModel, null, composerImpl, (i2 >> 3) & 14);
                            composerImpl.end(false);
                        } else {
                            composerImpl.startReplaceGroup(-1851398708);
                            composerImpl.end(false);
                        }
                        if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                            ComposerKt.traceEventEnd();
                        }
                    } else {
                        if (bouncerOverlayLayout != BouncerOverlayLayout.SPLIT_BOUNCER) {
                            if (bouncerOverlayLayout != BouncerOverlayLayout.BELOW_USER_SWITCHER) {
                                if (bouncerOverlayLayout != BouncerOverlayLayout.BESIDE_USER_SWITCHER) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                throw new NotImplementedError(null, 1, null);
                            }
                            throw new NotImplementedError(null, 1, null);
                        }
                        throw new NotImplementedError(null, 1, null);
                    }
                } else {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.bouncer.ui.composable.SecBouncerContentKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    SecBouncerContentKt.SecBouncerContent(bouncerOverlayLayout, bouncerOverlayContentViewModel, bouncerDialogFactory, modifier, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
