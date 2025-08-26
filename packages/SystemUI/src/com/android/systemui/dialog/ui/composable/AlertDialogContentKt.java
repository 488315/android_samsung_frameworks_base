package com.android.systemui.dialog.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.ScrollKt;
import androidx.compose.foundation.ScrollState;
import androidx.compose.foundation.ScrollingContainerKt;
import androidx.compose.foundation.ScrollingLayoutElement;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class AlertDialogContentKt {
    public static final PaddingValuesImpl DialogPaddings;

    static {
        float f = 24;
        Dp.Companion companion = Dp.Companion;
        DialogPaddings = PaddingKt.m123PaddingValuesa9UjIt4(f, f, f, 18);
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
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = AlertDialogContentKt$AlertDialogButtons$2$1.INSTANCE;
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MeasurePolicy measurePolicy = (MeasurePolicy) objRememberedValue;
            boolean z4 = false;
            composerImpl.end(false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifier2);
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
            Updater.m337setimpl(composerImpl, measurePolicy, function23);
            Function2 function24 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function24);
            Function2 function25 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function25);
            }
            Function2 function26 = ComposeUiNode.Companion.SetModifier;
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function26);
            composerImpl.startReplaceGroup(2018365821);
            if (composableLambdaImpl != null) {
                Modifier modifierLayoutId = LayoutIdKt.layoutId(modifier2, "positive");
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierLayoutId);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function23);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function24);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function25);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function26);
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
                Modifier modifierLayoutId2 = LayoutIdKt.layoutId(modifier2, "negative");
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierLayoutId2);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function23);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function24);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function25);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function26);
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
                Modifier modifierLayoutId3 = LayoutIdKt.layoutId(modifier2, "neutral");
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy3 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, modifierLayoutId3);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy3, function23);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope4, function24);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function25);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier4, function26);
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.dialog.ui.composable.AlertDialogContentKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    Function2 function27 = function22;
                    Modifier modifier3 = modifier2;
                    AlertDialogContentKt.AlertDialogButtons(composableLambdaImpl, function2, function27, modifier3, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:88:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void AlertDialogContent(final ComposableLambdaImpl composableLambdaImpl, ComposableLambdaImpl composableLambdaImpl2, Modifier modifier, final ComposableLambdaImpl composableLambdaImpl3, ComposableLambdaImpl composableLambdaImpl4, ComposableLambdaImpl composableLambdaImpl5, Composer composer, final int i, final int i2) {
        Modifier modifier2;
        int i3;
        ComposableLambdaImpl composableLambdaImpl6;
        int i4;
        ComposableLambdaImpl composableLambdaImpl7;
        final ComposableLambdaImpl composableLambdaImpl8;
        ComposerImpl composerImpl;
        final Modifier modifier3;
        final ComposableLambdaImpl composableLambdaImpl9;
        final ComposableLambdaImpl composableLambdaImpl10;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-271131171);
        int i5 = i2 & 4;
        if (i5 != 0) {
            i3 = i | 384;
            modifier2 = modifier;
        } else {
            modifier2 = modifier;
            i3 = (composerImpl2.changed(modifier2) ? 256 : 128) | i;
        }
        int i6 = i3 | 3072;
        int i7 = i2 & 32;
        if (i7 != 0) {
            i6 = 199680 | i3;
        } else {
            if ((196608 & i) == 0) {
                composableLambdaImpl6 = composableLambdaImpl4;
                i6 |= composerImpl2.changedInstance(composableLambdaImpl6) ? 131072 : 65536;
            }
            i4 = i2 & 64;
            if (i4 != 0) {
                if ((1572864 & i) == 0) {
                    composableLambdaImpl7 = composableLambdaImpl5;
                    i6 |= composerImpl2.changedInstance(composableLambdaImpl7) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                }
                if ((599187 & i6) == 599186 && composerImpl2.getSkipping()) {
                    composerImpl2.skipToGroupEnd();
                    composableLambdaImpl8 = composableLambdaImpl2;
                    composerImpl = composerImpl2;
                    composableLambdaImpl10 = composableLambdaImpl7;
                    composableLambdaImpl9 = composableLambdaImpl6;
                    modifier3 = modifier2;
                } else {
                    Modifier modifier4 = i5 == 0 ? Modifier.Companion : modifier2;
                    ComposableLambdaImpl composableLambdaImpl11 = i7 == 0 ? null : composableLambdaImpl6;
                    ComposableLambdaImpl composableLambdaImpl12 = i4 == 0 ? null : composableLambdaImpl7;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.dialog.ui.composable.AlertDialogContent (AlertDialogContent.kt:58)");
                    }
                    Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(modifier4, 1.0f);
                    ScrollState scrollStateRememberScrollState = ScrollKt.rememberScrollState(composerImpl2);
                    Modifier modifierPadding = PaddingKt.padding(ScrollingContainerKt.scrollingContainer(modifierFillMaxWidth, scrollStateRememberScrollState, Orientation.Vertical, (14 & 2) != 0, false, null, scrollStateRememberScrollState.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(scrollStateRememberScrollState, false, true)), DialogPaddings);
                    Alignment.Companion.getClass();
                    BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                    Arrangement.INSTANCE.getClass();
                    ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.Top, horizontal, composerImpl2, 48);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierPadding);
                    ComposeUiNode.Companion.getClass();
                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl2.applier != null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl2.startReusableNode();
                    if (composerImpl2.inserting) {
                        composerImpl2.createNode(function0);
                    } else {
                        composerImpl2.useNode();
                    }
                    Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                    Updater.m337setimpl(composerImpl2, columnMeasurePolicy, function2);
                    Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                    Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function23);
                    }
                    Function2 function24 = ComposeUiNode.Companion.SetModifier;
                    Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, function24);
                    ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                    composerImpl2.startReplaceGroup(2018855940);
                    composerImpl2.end(false);
                    MaterialTheme.INSTANCE.getClass();
                    long j = MaterialTheme.getColorScheme(composerImpl2).onSurface;
                    DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = ContentColorKt.LocalContentColor;
                    int i8 = i6;
                    CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m456boximpl(j)), ComposableLambdaKt.rememberComposableLambda(-976717037, new Function2() { // from class: com.android.systemui.dialog.ui.composable.AlertDialogContentKt$AlertDialogContent$1$2
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer2 = (Composer) obj;
                            if ((((Number) obj2).intValue() & 3) == 2) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.dialog.ui.composable.AlertDialogContent.<anonymous>.<anonymous> (AlertDialogContent.kt:80)");
                                    }
                                    MaterialTheme.INSTANCE.getClass();
                                    TextStyle textStyle = MaterialTheme.getTypography(composer2).headlineSmall;
                                    TextAlign.Companion.getClass();
                                    TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(textStyle, 0L, 0L, null, null, 0L, TextAlign.Center, 0L, null, null, 0, 16744447);
                                    final Function2 function25 = composableLambdaImpl;
                                    TextKt.ProvideTextStyle(textStyleM756copyp1EtxEg$default, ComposableLambdaKt.rememberComposableLambda(-1443226556, new Function2() { // from class: com.android.systemui.dialog.ui.composable.AlertDialogContentKt$AlertDialogContent$1$2.1
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj3, Object obj4) {
                                            Composer composer3 = (Composer) obj3;
                                            if ((((Number) obj4).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                if (composerImpl4.getSkipping()) {
                                                    composerImpl4.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.dialog.ui.composable.AlertDialogContent.<anonymous>.<anonymous>.<anonymous> (AlertDialogContent.kt:83)");
                                                    }
                                                    function25.invoke(composer3, 0);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composer2), composer2, 48);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl2), composerImpl2, 56);
                    Modifier.Companion companion = Modifier.Companion;
                    Dp.Companion companion2 = Dp.Companion;
                    SpacerKt.Spacer(composerImpl2, SizeKt.m131height3ABfNKs(companion, 16));
                    long j2 = MaterialTheme.getColorScheme(composerImpl2).onSurfaceVariant;
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                    int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                    ComposableLambdaImpl composableLambdaImpl13 = composableLambdaImpl11;
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
                    ComposableLambdaImpl composableLambdaImpl14 = composableLambdaImpl12;
                    Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl2, companion);
                    composerImpl2.startReusableNode();
                    if (composerImpl2.inserting) {
                        composerImpl2.createNode(function0);
                    } else {
                        composerImpl2.useNode();
                    }
                    Updater.m337setimpl(composerImpl2, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                    Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                    if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl2, currentCompositeKeyHash2, function23);
                    }
                    Updater.m337setimpl(composerImpl2, modifierMaterializeModifier2, function24);
                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                    composableLambdaImpl8 = composableLambdaImpl2;
                    CompositionLocalKt.CompositionLocalProvider(dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Color.m456boximpl(j2)), ComposableLambdaKt.rememberComposableLambda(-2093541351, new Function2() { // from class: com.android.systemui.dialog.ui.composable.AlertDialogContentKt$AlertDialogContent$1$3$1
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer2 = (Composer) obj;
                            if ((((Number) obj2).intValue() & 3) == 2) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.dialog.ui.composable.AlertDialogContent.<anonymous>.<anonymous>.<anonymous> (AlertDialogContent.kt:92)");
                                    }
                                    MaterialTheme.INSTANCE.getClass();
                                    TextStyle textStyle = MaterialTheme.getTypography(composer2).bodyMedium;
                                    TextAlign.Companion.getClass();
                                    TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(textStyle, 0L, 0L, null, null, 0L, TextAlign.Center, 0L, null, null, 0, 16744447);
                                    final Function2 function25 = composableLambdaImpl8;
                                    TextKt.ProvideTextStyle(textStyleM756copyp1EtxEg$default, ComposableLambdaKt.rememberComposableLambda(135809930, new Function2() { // from class: com.android.systemui.dialog.ui.composable.AlertDialogContentKt$AlertDialogContent$1$3$1.1
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj3, Object obj4) {
                                            Composer composer3 = (Composer) obj3;
                                            if ((((Number) obj4).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                if (composerImpl4.getSkipping()) {
                                                    composerImpl4.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.dialog.ui.composable.AlertDialogContent.<anonymous>.<anonymous>.<anonymous>.<anonymous> (AlertDialogContent.kt:95)");
                                                    }
                                                    function25.invoke(composer3, 0);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composer2), composer2, 48);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl2), composerImpl2, 56);
                    composerImpl2.end(true);
                    SpacerKt.Spacer(composerImpl2, SizeKt.m131height3ABfNKs(companion, 32));
                    composerImpl2.startReplaceGroup(2018897307);
                    if (composableLambdaImpl3 != null || composableLambdaImpl13 != null || composableLambdaImpl14 != null) {
                        AlertDialogButtons(composableLambdaImpl3, composableLambdaImpl13, composableLambdaImpl14, null, composerImpl2, (i8 >> 12) & 1022);
                    }
                    if (AnimatedContentKt$$ExternalSyntheticOutline0.m(composerImpl2, false, true)) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl = composerImpl2;
                    modifier3 = modifier4;
                    composableLambdaImpl9 = composableLambdaImpl13;
                    composableLambdaImpl10 = composableLambdaImpl14;
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final ComposableLambdaImpl composableLambdaImpl15 = composableLambdaImpl8;
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.dialog.ui.composable.AlertDialogContentKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            ComposableLambdaImpl composableLambdaImpl16 = composableLambdaImpl10;
                            AlertDialogContentKt.AlertDialogContent(composableLambdaImpl, composableLambdaImpl15, modifier3, composableLambdaImpl3, composableLambdaImpl9, composableLambdaImpl16, (Composer) obj, iUpdateChangedFlags, i2);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i6 |= 1572864;
            composableLambdaImpl7 = composableLambdaImpl5;
            if ((599187 & i6) == 599186) {
                if (i5 == 0) {
                }
                if (i7 == 0) {
                }
                if (i4 == 0) {
                }
                if (ComposerKt.isTraceInProgress()) {
                }
                Modifier modifierFillMaxWidth2 = SizeKt.fillMaxWidth(modifier4, 1.0f);
                ScrollState scrollStateRememberScrollState2 = ScrollKt.rememberScrollState(composerImpl2);
                Modifier modifierPadding2 = PaddingKt.padding(ScrollingContainerKt.scrollingContainer(modifierFillMaxWidth2, scrollStateRememberScrollState2, Orientation.Vertical, (14 & 2) != 0, false, null, scrollStateRememberScrollState2.internalInteractionSource, true, null, null).then(new ScrollingLayoutElement(scrollStateRememberScrollState2, false, true)), DialogPaddings);
                Alignment.Companion.getClass();
                BiasAlignment.Horizontal horizontal2 = Alignment.Companion.CenterHorizontally;
                Arrangement.INSTANCE.getClass();
                ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(Arrangement.Top, horizontal2, composerImpl2, 48);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl2.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl2, modifierPadding2);
                ComposeUiNode.Companion.getClass();
                Function0 function02 = ComposeUiNode.Companion.Constructor;
                if (composerImpl2.applier != null) {
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        composableLambdaImpl6 = composableLambdaImpl4;
        i4 = i2 & 64;
        if (i4 != 0) {
        }
        composableLambdaImpl7 = composableLambdaImpl5;
        if ((599187 & i6) == 599186) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }
}
