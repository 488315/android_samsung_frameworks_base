package com.android.systemui.inputdevice.tutorial.ui.composable;

import android.content.res.Configuration;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Center$1;
import androidx.compose.foundation.layout.Arrangement$Start$1;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.layout.WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1;
import androidx.compose.foundation.text.CoreTextFieldKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass;
import androidx.compose.material3.windowsizeclass.WindowSizeClass;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Dp;
import com.android.compose.windowsizeclass.WindowSizeClassKt;
import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState;
import com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperUtilsKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class ActionTutorialContentKt {
    /* JADX WARN: Removed duplicated region for block: B:48:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:99:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ActionTutorialContent(final TutorialActionState tutorialActionState, final Function0 function0, final TutorialScreenConfig tutorialScreenConfig, Function1 function1, Composer composer, final int i, final int i2) {
        int i3;
        final Function1 function12;
        int i4;
        TutorialActionState tutorialActionState2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1473698835);
        if ((i & 6) == 0) {
            i3 = ((i & 8) == 0 ? composerImpl.changed(tutorialActionState) : composerImpl.changedInstance(tutorialActionState) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= (i & 512) == 0 ? composerImpl.changed(tutorialScreenConfig) : composerImpl.changedInstance(tutorialScreenConfig) ? 256 : 128;
        }
        int i5 = i2 & 8;
        if (i5 == 0) {
            if ((i & 3072) == 0) {
                function12 = function1;
                i3 |= composerImpl.changedInstance(function12) ? 2048 : 1024;
            }
            i4 = i3;
            if ((i4 & 1171) == 1170 || !composerImpl.getSkipping()) {
                Function1 function13 = i5 == 0 ? null : function12;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.composable.ActionTutorialContent (ActionTutorialContent.kt:111)");
                }
                Arrangement.INSTANCE.getClass();
                Arrangement$Center$1 arrangement$Center$1 = Arrangement.Center;
                Modifier.Companion companion = Modifier.Companion;
                Modifier modifierComposed = ComposedModifierKt.composed(BackgroundKt.m26backgroundbw27NRU(SizeKt.fillMaxSize(companion, 1.0f), tutorialScreenConfig.colors.background, RectangleShapeKt.RectangleShape), InspectableValueKt.NoInspectorInfo, new WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1());
                Alignment.Companion.getClass();
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Center$1, Alignment.Companion.Start, composerImpl, 6);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierComposed);
                ComposeUiNode.Companion.getClass();
                Function0 function02 = ComposeUiNode.Companion.Constructor;
                if (composerImpl.applier != null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function02);
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
                boolean zHasCompactWindowSize = ShortcutHelperUtilsKt.hasCompactWindowSize(composerImpl);
                if (((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).orientation == 2) {
                    composerImpl.startReplaceGroup(437568457);
                    HorizontalDescriptionAndAnimation(tutorialActionState, tutorialScreenConfig, zHasCompactWindowSize, columnScopeInstance.weight(companion, 1.0f, true), composerImpl, ((i4 >> 3) & 112) | (i4 & 14) | 64);
                    composerImpl.end(false);
                    tutorialActionState2 = tutorialActionState;
                } else {
                    composerImpl.startReplaceGroup(437810443);
                    tutorialActionState2 = tutorialActionState;
                    VerticalDescriptionAndAnimation(tutorialActionState2, tutorialScreenConfig, zHasCompactWindowSize, columnScopeInstance.weight(companion, 1.0f, true), composerImpl, (i4 & 14) | 64 | ((i4 >> 3) & 112));
                    composerImpl.end(false);
                }
                boolean z = tutorialActionState2 instanceof TutorialActionState.Finished;
                function12 = function13;
                final State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(z ? 1.0f : 0.0f, null, null, null, composerImpl, 0, 30);
                Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(companion, 60, 0.0f, 2);
                composerImpl.startReplaceGroup(1676705713);
                boolean zChanged = composerImpl.changed(stateAnimateFloatAsState);
                Object objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion companion2 = Composer.Companion;
                if (!zChanged) {
                    companion2.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function1() { // from class: com.android.systemui.inputdevice.tutorial.ui.composable.ActionTutorialContentKt$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj)).setAlpha(((Number) stateAnimateFloatAsState.getValue()).floatValue());
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    composerImpl.end(false);
                    TutorialComponentsKt.DoneButton(function0, GraphicsLayerModifierKt.graphicsLayer(modifierM127paddingVpY3zN4$default, (Function1) objRememberedValue), z, function12 != null, composerImpl, (i4 >> 3) & 14, 0);
                    composerImpl.end(true);
                    if (z) {
                        Unit unit = Unit.INSTANCE;
                        composerImpl.startReplaceGroup(1120290486);
                        boolean zChangedInstance = composerImpl.changedInstance(function12);
                        Object objRememberedValue2 = composerImpl.rememberedValue();
                        if (!zChangedInstance) {
                            companion2.getClass();
                            if (objRememberedValue2 == Composer.Companion.Empty) {
                                objRememberedValue2 = new ActionTutorialContentKt$ActionTutorialContent$2$1(function12, null);
                                composerImpl.updateRememberedValue(objRememberedValue2);
                            }
                            composerImpl.end(false);
                            EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) objRememberedValue2);
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            } else {
                composerImpl.skipToGroupEnd();
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.inputdevice.tutorial.ui.composable.ActionTutorialContentKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        Function1 function14 = function12;
                        ActionTutorialContentKt.ActionTutorialContent(tutorialActionState, function0, tutorialScreenConfig, function14, (Composer) obj, iUpdateChangedFlags, i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 3072;
        function12 = function1;
        i4 = i3;
        if ((i4 & 1171) == 1170) {
            if (i5 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            Arrangement.INSTANCE.getClass();
            Arrangement$Center$1 arrangement$Center$12 = Arrangement.Center;
            Modifier.Companion companion3 = Modifier.Companion;
            Modifier modifierComposed2 = ComposedModifierKt.composed(BackgroundKt.m26backgroundbw27NRU(SizeKt.fillMaxSize(companion3, 1.0f), tutorialScreenConfig.colors.background, RectangleShapeKt.RectangleShape), InspectableValueKt.NoInspectorInfo, new WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1());
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Center$12, Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierComposed2);
            ComposeUiNode.Companion.getClass();
            Function0 function022 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier != null) {
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    public static final void HorizontalDescriptionAndAnimation(TutorialActionState tutorialActionState, TutorialScreenConfig tutorialScreenConfig, boolean z, Modifier modifier, Composer composer, int i) {
        int i2;
        boolean z2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-938307334);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl.changed(tutorialActionState) : composerImpl.changedInstance(tutorialActionState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= (i & 64) == 0 ? composerImpl.changed(tutorialScreenConfig) : composerImpl.changedInstance(tutorialScreenConfig) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            z2 = z;
            i2 |= composerImpl.changed(z2) ? 256 : 128;
        } else {
            z2 = z;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(modifier) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.composable.HorizontalDescriptionAndAnimation (ActionTutorialContent.kt:154)");
            }
            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(modifier, 1.0f);
            float f = 48;
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM128paddingqDBjuR0 = PaddingKt.m128paddingqDBjuR0(modifierFillMaxWidth, f, 100, f, 8);
            Arrangement.INSTANCE.getClass();
            Arrangement$Start$1 arrangement$Start$1 = Arrangement.Start;
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Start$1, Alignment.Companion.Top, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM128paddingqDBjuR0);
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
            Updater.m337setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            Modifier.Companion companion2 = Modifier.Companion;
            int i3 = (i2 & 112) | (i2 & 14) | 64;
            TutorialDescription(tutorialActionState, tutorialScreenConfig, z2, rowScopeInstance.weight(companion2, 1.0f, true), composerImpl, i3 | (i2 & 896));
            SpacerKt.Spacer(composerImpl, SizeKt.m144width3ABfNKs(companion2, 24));
            TutorialAnimationKt.TutorialAnimation(tutorialActionState, tutorialScreenConfig, rowScopeInstance.weight(companion2, 1.0f, true), composerImpl, i3);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ActionTutorialContentKt$$ExternalSyntheticLambda2(tutorialActionState, tutorialScreenConfig, z, modifier, i, 1);
        }
    }

    public static final void TutorialDescription(TutorialActionState tutorialActionState, TutorialScreenConfig tutorialScreenConfig, boolean z, Modifier modifier, Composer composer, int i) {
        int i2;
        Pair pair;
        TextStyle textStyle;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1320005281);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl.changed(tutorialActionState) : composerImpl.changedInstance(tutorialActionState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= (i & 64) == 0 ? composerImpl.changed(tutorialScreenConfig) : composerImpl.changedInstance(tutorialScreenConfig) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(z) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(modifier) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.composable.TutorialDescription (ActionTutorialContent.kt:197)");
            }
            composerImpl.startReplaceGroup(1370179441);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl);
            }
            FocusRequester focusRequester = (FocusRequester) objRememberedValue;
            composerImpl.end(false);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(1370181205);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new ActionTutorialContentKt$TutorialDescription$1$1(focusRequester, null);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) objRememberedValue2);
            if (tutorialActionState instanceof TutorialActionState.Finished) {
                pair = new Pair(Integer.valueOf(tutorialScreenConfig.strings.titleSuccessResId), Integer.valueOf(tutorialScreenConfig.strings.bodySuccessResId));
            } else if (Intrinsics.areEqual(tutorialActionState, TutorialActionState.Error.INSTANCE) || (tutorialActionState instanceof TutorialActionState.InProgressAfterError)) {
                pair = new Pair(Integer.valueOf(tutorialScreenConfig.strings.titleErrorResId), Integer.valueOf(tutorialScreenConfig.strings.bodyErrorResId));
            } else {
                if (!(tutorialActionState instanceof TutorialActionState.NotStarted) && !(tutorialActionState instanceof TutorialActionState.InProgress)) {
                    throw new NoWhenBranchMatchedException();
                }
                pair = new Pair(Integer.valueOf(tutorialScreenConfig.strings.titleResId), Integer.valueOf(tutorialScreenConfig.strings.bodyResId));
            }
            int iIntValue = ((Number) pair.component1()).intValue();
            int iIntValue2 = ((Number) pair.component2()).intValue();
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            String strStringResource = StringResources_androidKt.stringResource(iIntValue, composerImpl);
            if (z) {
                composerImpl.startReplaceGroup(-1198557568);
                MaterialTheme.INSTANCE.getClass();
                textStyle = MaterialTheme.getTypography(composerImpl).headlineLarge;
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-1198555648);
                MaterialTheme.INSTANCE.getClass();
                textStyle = MaterialTheme.getTypography(composerImpl).displayMedium;
                composerImpl.end(false);
            }
            TextStyle textStyle2 = textStyle;
            long j = tutorialScreenConfig.colors.title;
            Modifier.Companion companion = Modifier.Companion;
            TextKt.m317Text4IGK_g(strStringResource, FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(companion, focusRequester), false, null, 3), j, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, textStyle2, composerImpl, 0, 0, 65528);
            Dp.Companion companion2 = Dp.Companion;
            SpacerKt.Spacer(composerImpl, SizeKt.m131height3ABfNKs(companion, 16));
            String strStringResource2 = StringResources_androidKt.stringResource(iIntValue2, composerImpl);
            MaterialTheme.INSTANCE.getClass();
            TextKt.m317Text4IGK_g(strStringResource2, null, tutorialScreenConfig.colors.bodyText, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).bodyLarge, composerImpl, 0, 0, 65530);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ActionTutorialContentKt$$ExternalSyntheticLambda2(tutorialActionState, tutorialScreenConfig, z, modifier, i, 2);
        }
    }

    public static final void VerticalDescriptionAndAnimation(TutorialActionState tutorialActionState, TutorialScreenConfig tutorialScreenConfig, boolean z, Modifier modifier, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1284117452);
        if ((i & 6) == 0) {
            i2 = ((i & 8) == 0 ? composerImpl.changed(tutorialActionState) : composerImpl.changedInstance(tutorialActionState) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= (i & 64) == 0 ? composerImpl.changed(tutorialScreenConfig) : composerImpl.changedInstance(tutorialScreenConfig) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(z) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(modifier) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.composable.VerticalDescriptionAndAnimation (ActionTutorialContent.kt:171)");
            }
            float f = z ? 24 : 96;
            Dp.Companion companion = Dp.Companion;
            int i3 = ((WindowSizeClass) composerImpl.consume(WindowSizeClassKt.LocalWindowSizeClass)).heightSizeClass;
            WindowHeightSizeClass.Companion.getClass();
            boolean z2 = i3 == WindowHeightSizeClass.Expanded;
            float f2 = 0;
            Modifier modifierM128paddingqDBjuR0 = PaddingKt.m128paddingqDBjuR0(SizeKt.fillMaxWidth(modifier, 1.0f), f2, 100, f2, 8);
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM128paddingqDBjuR0);
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
            composerImpl.startReplaceGroup(-722088098);
            if (z2) {
                SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(Modifier.Companion, 0.3f, true));
            }
            composerImpl.end(false);
            Modifier.Companion companion2 = Modifier.Companion;
            Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(columnScopeInstance.weight(companion2, 1.0f, true), f, 0.0f, 2);
            int i4 = (i2 & 14) | 64 | (i2 & 112);
            TutorialDescription(tutorialActionState, tutorialScreenConfig, z, modifierM127paddingVpY3zN4$default, composerImpl, (i2 & 896) | i4);
            TutorialAnimationKt.TutorialAnimation(tutorialActionState, tutorialScreenConfig, SizeKt.fillMaxWidth(columnScopeInstance.weight(companion2, 1.8f, true), 1.0f), composerImpl, i4);
            composerImpl.startReplaceGroup(-722076706);
            if (z2) {
                SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 0.3f, true));
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new ActionTutorialContentKt$$ExternalSyntheticLambda2(tutorialActionState, tutorialScreenConfig, z, modifier, i, 0);
        }
    }
}
