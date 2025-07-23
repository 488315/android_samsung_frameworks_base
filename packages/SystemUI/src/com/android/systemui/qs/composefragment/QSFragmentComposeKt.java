package com.android.systemui.qs.composefragment;

import android.content.Context;
import android.content.res.Configuration;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.material.SurfaceKt$Surface$3$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import com.android.systemui.R;
import com.android.systemui.qs.ui.composable.QuickSettingsShade;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class QSFragmentComposeKt {
    public static final QSFragmentComposeKt$instanceProvider$1 instanceProvider = new QSFragmentComposeKt$instanceProvider$1();

    public static final void AlwaysDarkMode(final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2113850158);
        if ((i & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.AlwaysDarkMode (QSFragmentCompose.kt:1318)");
            }
            composerImpl.startReplaceGroup(-571588056);
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = AndroidCompositionLocals_androidKt.LocalConfiguration;
            Configuration configuration = new Configuration((Configuration) composerImpl.consume(dynamicProvidableCompositionLocal));
            configuration.uiMode = (configuration.uiMode & (-49)) | 32;
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = AndroidCompositionLocals_androidKt.LocalContext;
            Context createConfigurationContext = ((Context) composerImpl.consume(staticProvidableCompositionLocal)).createConfigurationContext(configuration);
            ProvidedValue defaultProvidedValue$runtime_release = dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(configuration);
            createConfigurationContext.getClass();
            CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{defaultProvidedValue$runtime_release, staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(createConfigurationContext)}, ComposableLambdaKt.rememberComposableLambda(-1444278218, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt$AlwaysDarkMode$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.AlwaysDarkMode.<anonymous> (QSFragmentCompose.kt:1334)");
                    }
                    Function2.this.invoke(composer2, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(i) { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(7);
                    QSFragmentComposeKt.AlwaysDarkMode(ComposableLambdaImpl.this, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00fd, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L59;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void MediaObject(final com.android.systemui.media.controls.ui.view.MediaHost r15, androidx.compose.ui.Modifier r16, kotlin.jvm.functions.Function1 r17, androidx.compose.runtime.Composer r18, final int r19, final int r20) {
        /*
            Method dump skipped, instructions count: 385
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.composefragment.QSFragmentComposeKt.MediaObject(com.android.systemui.media.controls.ui.view.MediaHost, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function1, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final void QuickQuickSettingsLayout(final Function2 function2, final Function2 function22, final boolean z, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1448142738);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function2) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function22) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(z) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QuickQuickSettingsLayout (QSFragmentCompose.kt:1237)");
            }
            Applier applier = composerImpl.applier;
            if (z) {
                composerImpl.startReplaceGroup(-1549778020);
                Arrangement arrangement = Arrangement.INSTANCE;
                float dimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_tile_margin_vertical, composerImpl);
                arrangement.getClass();
                Arrangement.SpacedAligned m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(dimensionResource);
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                Modifier.Companion companion = Modifier.Companion;
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(m91spacedBy0680j_4, vertical, composerImpl, 48);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (applier == null) {
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
                Updater.m336setimpl(composerImpl, rowMeasurePolicy, function23);
                Function2 function24 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function24);
                Function2 function25 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function25);
                }
                Function2 function26 = ComposeUiNode.Companion.SetModifier;
                Updater.m336setimpl(composerImpl, materializeModifier, function26);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                Modifier weight = rowScopeInstance.weight(companion, 1.0f, true);
                BiasAlignment biasAlignment = Alignment.Companion.TopStart;
                int i3 = i2;
                MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, weight);
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
                function2.invoke(composerImpl, Integer.valueOf(i3 & 14));
                composerImpl.end(true);
                Modifier weight2 = rowScopeInstance.weight(companion, 1.0f, true);
                MeasurePolicy maybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, weight2);
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
                SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i3 >> 3) & 14, function22, composerImpl, true, true);
                composerImpl.end(false);
            } else {
                int i4 = i2;
                composerImpl.startReplaceGroup(-1549459650);
                Arrangement arrangement2 = Arrangement.INSTANCE;
                float dimensionResource2 = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_tile_margin_vertical, composerImpl);
                arrangement2.getClass();
                Arrangement.SpacedAligned m91spacedBy0680j_42 = Arrangement.m91spacedBy0680j_4(dimensionResource2);
                Modifier.Companion companion2 = Modifier.Companion;
                Alignment.Companion.getClass();
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m91spacedBy0680j_42, Alignment.Companion.Start, composerImpl, 0);
                int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, companion2);
                ComposeUiNode.Companion.getClass();
                Function0 function02 = ComposeUiNode.Companion.Constructor;
                if (applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function02);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope4, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function27 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function27);
                }
                Updater.m336setimpl(composerImpl, materializeModifier4, ComposeUiNode.Companion.SetModifier);
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                function2.invoke(composerImpl, Integer.valueOf(i4 & 14));
                SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i4 >> 3) & 14, function22, composerImpl, true, false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    QSFragmentComposeKt.QuickQuickSettingsLayout(Function2.this, function22, z, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void QuickSettingsLayout(Function2 function2, final Function2 function22, final Function2 function23, final boolean z, Composer composer, final int i) {
        int i2;
        Function2 function24;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1324695678);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function2) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function22) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function23) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(z) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function24 = function2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.QuickSettingsLayout (QSFragmentCompose.kt:1261)");
            }
            Applier applier = composerImpl.applier;
            if (z) {
                composerImpl.startReplaceGroup(-78349952);
                Arrangement arrangement = Arrangement.INSTANCE;
                QuickSettingsShade.Dimensions.INSTANCE.getClass();
                float f = QuickSettingsShade.Dimensions.Padding;
                arrangement.getClass();
                Arrangement.SpacedAligned m91spacedBy0680j_4 = Arrangement.m91spacedBy0680j_4(f);
                Alignment.Companion.getClass();
                BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                Modifier.Companion companion = Modifier.Companion;
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(m91spacedBy0680j_4, horizontal, composerImpl, 54);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Function2 function25 = ComposeUiNode.Companion.SetMeasurePolicy;
                Updater.m336setimpl(composerImpl, columnMeasurePolicy, function25);
                Function2 function26 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope, function26);
                Function2 function27 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function27);
                }
                Function2 function28 = ComposeUiNode.Companion.SetModifier;
                Updater.m336setimpl(composerImpl, materializeModifier, function28);
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                function2.invoke(composerImpl, Integer.valueOf(i2 & 14));
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.m91spacedBy0680j_4(f), Alignment.Companion.CenterVertically, composerImpl, 54);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, companion);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, rowMeasurePolicy, function25);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope2, function26);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function27);
                }
                Updater.m336setimpl(composerImpl, materializeModifier2, function28);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                Modifier weight = rowScopeInstance.weight(companion, 1.0f, true);
                BiasAlignment biasAlignment = Alignment.Companion.TopStart;
                MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                int i3 = i2;
                PersistentCompositionLocalMap currentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, weight);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, function25);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope3, function26);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function27);
                }
                Updater.m336setimpl(composerImpl, materializeModifier3, function28);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                function22.invoke(composerImpl, Integer.valueOf((i3 >> 3) & 14));
                composerImpl.end(true);
                Modifier weight2 = rowScopeInstance.weight(companion, 1.0f, true);
                MeasurePolicy maybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, weight2);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy2, function25);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope4, function26);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function27);
                }
                Updater.m336setimpl(composerImpl, materializeModifier4, function28);
                SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i3 >> 6) & 14, function23, composerImpl, true, true);
                composerImpl.end(true);
                composerImpl.end(false);
                function24 = function2;
            } else {
                int i4 = i2;
                composerImpl.startReplaceGroup(-77812288);
                Arrangement arrangement2 = Arrangement.INSTANCE;
                QuickSettingsShade.Dimensions.INSTANCE.getClass();
                float f2 = QuickSettingsShade.Dimensions.Padding;
                arrangement2.getClass();
                Arrangement.SpacedAligned m91spacedBy0680j_42 = Arrangement.m91spacedBy0680j_4(f2);
                Alignment.Companion.getClass();
                BiasAlignment.Horizontal horizontal2 = Alignment.Companion.CenterHorizontally;
                Modifier.Companion companion2 = Modifier.Companion;
                ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(m91spacedBy0680j_42, horizontal2, composerImpl, 54);
                int currentCompositeKeyHash5 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap currentCompositionLocalScope5 = composerImpl.currentCompositionLocalScope();
                Modifier materializeModifier5 = ComposedModifierKt.materializeModifier(composerImpl, companion2);
                ComposeUiNode.Companion.getClass();
                Function0 function02 = ComposeUiNode.Companion.Constructor;
                if (applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function02);
                } else {
                    composerImpl.useNode();
                }
                Updater.m336setimpl(composerImpl, columnMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m336setimpl(composerImpl, currentCompositionLocalScope5, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function29 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash5))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash5, composerImpl, currentCompositeKeyHash5, function29);
                }
                Updater.m336setimpl(composerImpl, materializeModifier5, ComposeUiNode.Companion.SetModifier);
                ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                function24 = function2;
                function24.invoke(composerImpl, Integer.valueOf(i4 & 14));
                function22.invoke(composerImpl, Integer.valueOf((i4 >> 3) & 14));
                SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i4 >> 6) & 14, function23, composerImpl, true, false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Function2 function210 = function24;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    QSFragmentComposeKt.QuickSettingsLayout(Function2.this, function22, function23, z, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final float access$qsHorizontalMargin(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1180820589);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.qsHorizontalMargin (QSFragmentCompose.kt:1295)");
        }
        float dimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_horizontal_margin, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return dimensionResource;
    }
}
