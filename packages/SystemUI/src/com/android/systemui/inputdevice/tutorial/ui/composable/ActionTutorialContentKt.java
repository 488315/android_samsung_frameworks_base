package com.android.systemui.inputdevice.tutorial.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.layout.Arrangement;
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
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Dp;
import com.android.compose.windowsizeclass.WindowSizeClassKt;
import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ActionTutorialContentKt {
    /* JADX WARN: Code restructure failed: missing block: B:65:0x019d, code lost:
    
        if (r8 == androidx.compose.runtime.Composer.Companion.Empty) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01dc, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L88;
     */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void ActionTutorialContent(final com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState r18, final kotlin.jvm.functions.Function0 r19, final com.android.systemui.inputdevice.tutorial.ui.composable.TutorialScreenConfig r20, kotlin.jvm.functions.Function1 r21, androidx.compose.runtime.Composer r22, final int r23, final int r24) {
        /*
            Method dump skipped, instructions count: 532
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputdevice.tutorial.ui.composable.ActionTutorialContentKt.ActionTutorialContent(com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState, kotlin.jvm.functions.Function0, com.android.systemui.inputdevice.tutorial.ui.composable.TutorialScreenConfig, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int, int):void");
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
            Modifier fillMaxWidth = SizeKt.fillMaxWidth(modifier, 1.0f);
            float f = 48;
            Dp.Companion companion = Dp.Companion;
            Modifier m127paddingqDBjuR0 = PaddingKt.m127paddingqDBjuR0(fillMaxWidth, f, 100, f, 8);
            Arrangement.INSTANCE.getClass();
            Arrangement$Start$1 arrangement$Start$1 = Arrangement.Start;
            Alignment.Companion.getClass();
            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$Start$1, Alignment.Companion.Top, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m127paddingqDBjuR0);
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
            Updater.m336setimpl(composerImpl, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            Modifier.Companion companion2 = Modifier.Companion;
            int i3 = (i2 & 112) | (i2 & 14) | 64;
            TutorialDescription(tutorialActionState, tutorialScreenConfig, z2, rowScopeInstance.weight(companion2, 1.0f, true), composerImpl, i3 | (i2 & 896));
            SpacerKt.Spacer(composerImpl, SizeKt.m143width3ABfNKs(companion2, 24));
            TutorialAnimationKt.TutorialAnimation(tutorialActionState, tutorialScreenConfig, rowScopeInstance.weight(companion2, 1.0f, true), composerImpl, i3);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ActionTutorialContentKt$$ExternalSyntheticLambda2(tutorialActionState, tutorialScreenConfig, z, modifier, i, 1);
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
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl);
            }
            FocusRequester focusRequester = (FocusRequester) rememberedValue;
            composerImpl.end(false);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(1370181205);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new ActionTutorialContentKt$TutorialDescription$1$1(focusRequester, null);
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) rememberedValue2);
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
            int intValue = ((Number) pair.component1()).intValue();
            int intValue2 = ((Number) pair.component2()).intValue();
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 6);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
            String stringResource = StringResources_androidKt.stringResource(intValue, composerImpl);
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
            TextKt.m316Text4IGK_g(stringResource, FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(companion, focusRequester), false, null, 3), j, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, textStyle2, composerImpl, 0, 0, 65528);
            Dp.Companion companion2 = Dp.Companion;
            SpacerKt.Spacer(composerImpl, SizeKt.m130height3ABfNKs(companion, 16));
            String stringResource2 = StringResources_androidKt.stringResource(intValue2, composerImpl);
            MaterialTheme.INSTANCE.getClass();
            TextKt.m316Text4IGK_g(stringResource2, null, tutorialScreenConfig.colors.bodyText, 0L, null, null, null, 0L, null, null, 0L, 0, false, 0, 0, null, MaterialTheme.getTypography(composerImpl).bodyLarge, composerImpl, 0, 0, 65530);
            composerImpl = composerImpl;
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ActionTutorialContentKt$$ExternalSyntheticLambda2(tutorialActionState, tutorialScreenConfig, z, modifier, i, 2);
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
            Modifier m127paddingqDBjuR0 = PaddingKt.m127paddingqDBjuR0(SizeKt.fillMaxWidth(modifier, 1.0f), f2, 100, f2, 8);
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl, 0);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m127paddingqDBjuR0);
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
            composerImpl.startReplaceGroup(-722088098);
            if (z2) {
                SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(Modifier.Companion, 0.3f, true));
            }
            composerImpl.end(false);
            Modifier.Companion companion2 = Modifier.Companion;
            Modifier m126paddingVpY3zN4$default = PaddingKt.m126paddingVpY3zN4$default(columnScopeInstance.weight(companion2, 1.0f, true), f, 0.0f, 2);
            int i4 = (i2 & 14) | 64 | (i2 & 112);
            TutorialDescription(tutorialActionState, tutorialScreenConfig, z, m126paddingVpY3zN4$default, composerImpl, (i2 & 896) | i4);
            TutorialAnimationKt.TutorialAnimation(tutorialActionState, tutorialScreenConfig, SizeKt.fillMaxWidth(columnScopeInstance.weight(companion2, 1.8f, true), 1.0f), composerImpl, i4);
            composerImpl.startReplaceGroup(-722076706);
            if (z2) {
                SpacerKt.Spacer(composerImpl, columnScopeInstance.weight(companion2, 0.3f, true));
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, false, true)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new ActionTutorialContentKt$$ExternalSyntheticLambda2(tutorialActionState, tutorialScreenConfig, z, modifier, i, 0);
        }
    }
}
