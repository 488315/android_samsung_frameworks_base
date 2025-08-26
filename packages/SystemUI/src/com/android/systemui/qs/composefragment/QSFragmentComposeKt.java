package com.android.systemui.qs.composefragment;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.FrameLayout;
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
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.material.SurfaceKt$Surface$3$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
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
import androidx.compose.ui.viewinterop.AndroidView_androidKt;
import com.android.systemui.R;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.qs.ui.composable.QuickSettingsShade;
import com.android.systemui.util.animation.UniqueObjectHostView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

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
            Context contextCreateConfigurationContext = ((Context) composerImpl.consume(staticProvidableCompositionLocal)).createConfigurationContext(configuration);
            ProvidedValue providedValueDefaultProvidedValue$runtime_release = dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(configuration);
            contextCreateConfigurationContext.getClass();
            CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{providedValueDefaultProvidedValue$runtime_release, staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(contextCreateConfigurationContext)}, ComposableLambdaKt.rememberComposableLambda(-1444278218, new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt.AlwaysDarkMode.1
                /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.AlwaysDarkMode.<anonymous> (QSFragmentCompose.kt:1334)");
                            }
                            composableLambdaImpl.invoke(composer2, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(i) { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(7);
                    QSFragmentComposeKt.AlwaysDarkMode(this.f$0, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:81:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void MediaObject(final MediaHost mediaHost, Modifier modifier, Function1 function1, Composer composer, final int i, final int i2) {
        Modifier modifier2;
        int i3;
        Function1 function12;
        int i4;
        final Function1 function13;
        final Function1 function14;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        final int i5 = 1;
        final int i6 = 0;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(258798036);
        int i7 = i | (composerImpl.changedInstance(mediaHost) ? 4 : 2);
        int i8 = i2 & 2;
        if (i8 == 0) {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i7 |= composerImpl.changed(modifier2) ? 32 : 16;
            }
            i3 = i2 & 4;
            if (i3 == 0) {
                i4 = i7 | 384;
                function12 = function1;
            } else {
                function12 = function1;
                i4 = i7 | (composerImpl.changedInstance(function12) ? 256 : 128);
            }
            if ((i4 & 147) == 146 || !composerImpl.getSkipping()) {
                if (i8 != 0) {
                    modifier2 = Modifier.Companion;
                }
                Composer.Companion companion = Composer.Companion;
                if (i3 == 0) {
                    Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, 934063884, companion);
                    if (objM == Composer.Companion.Empty) {
                        objM = new Function1() { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                switch (i6) {
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objM);
                    }
                    function13 = (Function1) objM;
                    composerImpl.end(false);
                } else {
                    function13 = function12;
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.MediaObject (QSFragmentCompose.kt:1212)");
                }
                Modifier.Companion companion2 = Modifier.Companion;
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion2);
                ComposeUiNode.Companion.getClass();
                Function0 function0 = ComposeUiNode.Companion.Constructor;
                if (composerImpl.applier != null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
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
                composerImpl.startReplaceGroup(-282620150);
                boolean zChangedInstance = composerImpl.changedInstance(mediaHost);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new Function1() { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                switch (i6) {
                                    case 0:
                                        UniqueObjectHostView uniqueObjectHostView = ((MediaHost) mediaHost).hostView;
                                        if (uniqueObjectHostView == null) {
                                            uniqueObjectHostView = null;
                                        }
                                        uniqueObjectHostView.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
                                        return uniqueObjectHostView;
                                    default:
                                        ((Function1) mediaHost).mo781invoke((UniqueObjectHostView) obj);
                                        return Unit.INSTANCE;
                                }
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    Function1 function15 = (Function1) objRememberedValue;
                    composerImpl.end(false);
                    composerImpl.startReplaceGroup(-282607893);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    companion.getClass();
                    Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                    if (objRememberedValue2 == composer$Companion$Empty$1) {
                        objRememberedValue2 = new Function1() { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                switch (i5) {
                                }
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    Function1 function16 = (Function1) objRememberedValue2;
                    composerImpl.end(false);
                    composerImpl.startReplaceGroup(-282609438);
                    boolean z = (i4 & 896) == 256;
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (z || objRememberedValue3 == composer$Companion$Empty$1) {
                        objRememberedValue3 = new Function1() { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt$$ExternalSyntheticLambda3
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                switch (i5) {
                                    case 0:
                                        UniqueObjectHostView uniqueObjectHostView = ((MediaHost) function13).hostView;
                                        if (uniqueObjectHostView == null) {
                                            uniqueObjectHostView = null;
                                        }
                                        uniqueObjectHostView.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
                                        return uniqueObjectHostView;
                                    default:
                                        ((Function1) function13).mo781invoke((UniqueObjectHostView) obj);
                                        return Unit.INSTANCE;
                                }
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue3);
                    }
                    composerImpl.end(false);
                    AndroidView_androidKt.AndroidView(function15, modifier2, function16, null, (Function1) objRememberedValue3, composerImpl, (i4 & 112) | 384, 8);
                    composerImpl.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    function14 = function13;
                }
            } else {
                composerImpl.skipToGroupEnd();
                function14 = function12;
            }
            final Modifier modifier3 = modifier2;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt$$ExternalSyntheticLambda6
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        Function1 function17 = function14;
                        QSFragmentComposeKt.MediaObject(mediaHost, modifier3, function17, (Composer) obj, iUpdateChangedFlags, i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i7 |= 48;
        modifier2 = modifier;
        i3 = i2 & 4;
        if (i3 == 0) {
        }
        if ((i4 & 147) == 146) {
            if (i8 != 0) {
            }
            Composer.Companion companion3 = Composer.Companion;
            if (i3 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            Modifier.Companion companion22 = Modifier.Companion;
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, companion22);
            ComposeUiNode.Companion.getClass();
            Function0 function02 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier != null) {
            }
        }
        final Modifier modifier32 = modifier2;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
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
                float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_tile_margin_vertical, composerImpl);
                arrangement.getClass();
                Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(fDimensionResource);
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                Modifier.Companion companion = Modifier.Companion;
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(spacedAlignedM92spacedBy0680j_4, vertical, composerImpl, 48);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
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
                Updater.m337setimpl(composerImpl, rowMeasurePolicy, function23);
                Function2 function24 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function24);
                Function2 function25 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function25);
                }
                Function2 function26 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function26);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                Modifier modifierWeight = rowScopeInstance.weight(companion, 1.0f, true);
                BiasAlignment biasAlignment = Alignment.Companion.TopStart;
                int i3 = i2;
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierWeight);
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
                function2.invoke(composerImpl, Integer.valueOf(i3 & 14));
                composerImpl.end(true);
                Modifier modifierWeight2 = rowScopeInstance.weight(companion, 1.0f, true);
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierWeight2);
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
                SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i3 >> 3) & 14, function22, composerImpl, true, true);
                composerImpl.end(false);
            } else {
                int i4 = i2;
                composerImpl.startReplaceGroup(-1549459650);
                Arrangement arrangement2 = Arrangement.INSTANCE;
                float fDimensionResource2 = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_tile_margin_vertical, composerImpl);
                arrangement2.getClass();
                Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_42 = Arrangement.m92spacedBy0680j_4(fDimensionResource2);
                Modifier.Companion companion2 = Modifier.Companion;
                Alignment.Companion.getClass();
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_42, Alignment.Companion.Start, composerImpl, 0);
                int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, companion2);
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
                Updater.m337setimpl(composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope4, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function27 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function27);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier4, ComposeUiNode.Companion.SetModifier);
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                function2.invoke(composerImpl, Integer.valueOf(i4 & 14));
                SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m((i4 >> 3) & 14, function22, composerImpl, true, false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    QSFragmentComposeKt.QuickQuickSettingsLayout(function2, function22, z, (Composer) obj, iUpdateChangedFlags);
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
                Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(f);
                Alignment.Companion.getClass();
                BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
                Modifier.Companion companion = Modifier.Companion;
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_4, horizontal, composerImpl, 54);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, companion);
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
                Updater.m337setimpl(composerImpl, columnMeasurePolicy, function25);
                Function2 function26 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function26);
                Function2 function27 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function27);
                }
                Function2 function28 = ComposeUiNode.Companion.SetModifier;
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function28);
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                function2.invoke(composerImpl, Integer.valueOf(i2 & 14));
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.m92spacedBy0680j_4(f), Alignment.Companion.CenterVertically, composerImpl, 54);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, companion);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, rowMeasurePolicy, function25);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function26);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function27);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function28);
                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                Modifier modifierWeight = rowScopeInstance.weight(companion, 1.0f, true);
                BiasAlignment biasAlignment = Alignment.Companion.TopStart;
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                int i3 = i2;
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierWeight);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function25);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function26);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function27);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function28);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                function22.invoke(composerImpl, Integer.valueOf((i3 >> 3) & 14));
                composerImpl.end(true);
                Modifier modifierWeight2 = rowScopeInstance.weight(companion, 1.0f, true);
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                int currentCompositeKeyHash4 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope4 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier4 = ComposedModifierKt.materializeModifier(composerImpl, modifierWeight2);
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function0);
                } else {
                    composerImpl.useNode();
                }
                Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function25);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope4, function26);
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash4))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash4, composerImpl, currentCompositeKeyHash4, function27);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier4, function28);
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
                Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_42 = Arrangement.m92spacedBy0680j_4(f2);
                Alignment.Companion.getClass();
                BiasAlignment.Horizontal horizontal2 = Alignment.Companion.CenterHorizontally;
                Modifier.Companion companion2 = Modifier.Companion;
                ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(spacedAlignedM92spacedBy0680j_42, horizontal2, composerImpl, 54);
                int currentCompositeKeyHash5 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope5 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier5 = ComposedModifierKt.materializeModifier(composerImpl, companion2);
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
                Updater.m337setimpl(composerImpl, columnMeasurePolicy2, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope5, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function29 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash5))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash5, composerImpl, currentCompositeKeyHash5, function29);
                }
                Updater.m337setimpl(composerImpl, modifierMaterializeModifier5, ComposeUiNode.Companion.SetModifier);
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
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final Function2 function210 = function24;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.composefragment.QSFragmentComposeKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    QSFragmentComposeKt.QuickSettingsLayout(function210, function22, function23, z, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
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
        float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.qs_horizontal_margin, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return fDimensionResource;
    }
}
