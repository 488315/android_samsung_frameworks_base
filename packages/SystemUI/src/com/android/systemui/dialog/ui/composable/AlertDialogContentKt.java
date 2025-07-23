package com.android.systemui.dialog.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
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
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class AlertDialogContentKt {
    public static final PaddingValuesImpl DialogPaddings;

    static {
        float f = 24;
        Dp.Companion companion = Dp.Companion;
        DialogPaddings = PaddingKt.m122PaddingValuesa9UjIt4(f, f, f, 18);
    }

    public static final void AlertDialogButtons(final ComposableLambdaImpl composableLambdaImpl, final Function2 function2, final Function2 function22, Modifier modifier, Composer composer, final int i) {
        int i2;
        final Modifier modifier2;
        boolean z;
        boolean z2;
        boolean z3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(379332730);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(composableLambdaImpl) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function22) ? 256 : 128;
        }
        if (((i2 | 3072) & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            modifier2 = modifier;
        } else {
            modifier2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.dialog.ui.composable.AlertDialogButtons (AlertDialogContent.kt:118)");
            }
            composerImpl.startReplaceGroup(144519693);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (rememberedValue == Composer.Companion.Empty) {
                rememberedValue = AlertDialogContentKt$AlertDialogButtons$2$1.INSTANCE;
                composerImpl.updateRememberedValue(rememberedValue);
            }
            MeasurePolicy measurePolicy = (MeasurePolicy) rememberedValue;
            boolean z4 = false;
            composerImpl.end(false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier2);
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
            Function2 function23 = ComposeUiNode.Companion.SetMeasurePolicy;
            Updater.m336setimpl(composerImpl, measurePolicy, function23);
            Function2 function24 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function24);
            Function2 function25 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function25);
            }
            Function2 function26 = ComposeUiNode.Companion.SetModifier;
            Updater.m336setimpl(composerImpl, materializeModifier, function26);
            composerImpl.startReplaceGroup(2018365821);
            if (composableLambdaImpl != null) {
                Modifier layoutId = LayoutIdKt.layoutId(modifier2, "positive");
                Alignment.Companion.getClass();
                MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, layoutId);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, function23);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope2, function24);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function25);
                }
                Updater.m336setimpl(composerImpl, materializeModifier2, function26);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                composableLambdaImpl.invoke((Object) composerImpl, (Object) 0);
                composerImpl.end(true);
                Unit unit = Unit.INSTANCE;
                z4 = false;
            }
            composerImpl.end(z4);
            composerImpl.startReplaceGroup(2018368381);
            if (function2 == null) {
                z = false;
            } else {
                Modifier layoutId2 = LayoutIdKt.layoutId(modifier2, "negative");
                Alignment.Companion.getClass();
                MeasurePolicy maybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, layoutId2);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy2, function23);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope3, function24);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function25);
                }
                Updater.m336setimpl(composerImpl, materializeModifier3, function26);
                BoxScopeInstance boxScopeInstance2 = BoxScopeInstance.INSTANCE;
                z = false;
                function2.invoke(composerImpl, 0);
                composerImpl.end(true);
                Unit unit2 = Unit.INSTANCE;
            }
            composerImpl.end(z);
            composerImpl.startReplaceGroup(2018370908);
            if (function22 == null) {
                z3 = true;
                z2 = false;
            } else {
                Modifier layoutId3 = LayoutIdKt.layoutId(modifier2, "neutral");
                Alignment.Companion.getClass();
                MeasurePolicy maybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, layoutId3);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy3, function23);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope4, function24);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function25);
                }
                Updater.m336setimpl(composerImpl, materializeModifier4, function26);
                BoxScopeInstance boxScopeInstance3 = BoxScopeInstance.INSTANCE;
                z2 = false;
                function22.invoke(composerImpl, 0);
                z3 = true;
                composerImpl.end(true);
                Unit unit3 = Unit.INSTANCE;
            }
            if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl, z2, z3)) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.dialog.ui.composable.AlertDialogContentKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Function2 function27 = function22;
                    Modifier modifier3 = modifier2;
                    AlertDialogContentKt.AlertDialogButtons(ComposableLambdaImpl.this, function2, function27, modifier3, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void AlertDialogContent(final androidx.compose.runtime.internal.ComposableLambdaImpl r18, androidx.compose.runtime.internal.ComposableLambdaImpl r19, androidx.compose.ui.Modifier r20, final androidx.compose.runtime.internal.ComposableLambdaImpl r21, androidx.compose.runtime.internal.ComposableLambdaImpl r22, androidx.compose.runtime.internal.ComposableLambdaImpl r23, androidx.compose.runtime.Composer r24, final int r25, final int r26) {
        /*
            Method dump skipped, instructions count: 528
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.dialog.ui.composable.AlertDialogContentKt.AlertDialogContent(androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.ui.Modifier, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.Composer, int, int):void");
    }
}
