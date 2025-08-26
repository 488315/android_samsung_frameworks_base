package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.material3.AppBarKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.TopAppBarDefaults;
import androidx.compose.material3.TopAppBarScrollBehavior;
import androidx.compose.material3.TopAppBarState;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.input.nestedscroll.NestedScrollModifierKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpSize;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import com.android.systemui.media.mediaoutput.compose.ext.CompositionExtKt;
import com.android.systemui.media.mediaoutput.compose.ext.ConfigurationExtKt;
import com.android.systemui.media.mediaoutput.compose.material.ContainerBoxKt;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import com.samsung.sesl.compose.component.ScaffoldKt;
import com.samsung.sesl.compose.component.SeslTopAppBarDefaults;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$NavigationScope;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$TitleScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class ActionBarKt {
    /* JADX WARN: Removed duplicated region for block: B:30:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:79:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SecTitle(final Function0 function0, final String str, ComposableLambdaImpl composableLambdaImpl, final ComposableLambdaImpl composableLambdaImpl2, Composer composer, final int i, final int i2) {
        int i3;
        ComposableLambdaImpl composableLambdaImpl3;
        final ComposableLambdaImpl composableLambdaImpl4;
        ComposerImpl composerImpl;
        int i4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1310621619);
        if ((i & 6) == 0) {
            i3 = (composerImpl2.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl2.changed(str) ? 32 : 16;
        }
        int i5 = i2 & 4;
        if (i5 == 0) {
            if ((i & 384) == 0) {
                composableLambdaImpl3 = composableLambdaImpl;
                i3 |= composerImpl2.changedInstance(composableLambdaImpl3) ? 256 : 128;
            }
            if ((i & 3072) == 0) {
                i3 |= composerImpl2.changedInstance(composableLambdaImpl2) ? 2048 : 1024;
            }
            if ((i3 & 1171) == 1170 || !composerImpl2.getSkipping()) {
                if (i5 == 0) {
                    ComposableSingletons$ActionBarKt.INSTANCE.getClass();
                    composableLambdaImpl4 = ComposableSingletons$ActionBarKt.f84lambda1;
                } else {
                    composableLambdaImpl4 = composableLambdaImpl3;
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecTitle (ActionBar.kt:42)");
                }
                Feature feature = (Feature) composerImpl2.consume(CompositionExtKt.LocalFeature);
                TopAppBarDefaults topAppBarDefaults = TopAppBarDefaults.INSTANCE;
                TopAppBarState topAppBarStateRememberTopAppBarState = AppBarKt.rememberTopAppBarState(composerImpl2);
                topAppBarDefaults.getClass();
                TopAppBarScrollBehavior topAppBarScrollBehaviorPinnedScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarStateRememberTopAppBarState, composerImpl2);
                float f = 0;
                Dp.Companion companion = Dp.Companion;
                final float fMax = Math.max(f, ((1.0f - ContainerBoxKt.containerFraction(feature.isFullScreen, composerImpl2, 0).getWidth()) * DpSize.m847getWidthD9Ej5fM(((DpSize) composerImpl2.consume(CompositionExtKt.LocalRootSize)).packedValue)) / 2);
                final float f2 = !ConfigurationExtKt.isLandscape(composerImpl2) ? f : 48;
                Modifier.Companion companion2 = Modifier.Companion;
                Modifier modifierM127paddingVpY3zN4$default = PaddingKt.m127paddingVpY3zN4$default(companion2, fMax, 0.0f, 2);
                Arrangement.INSTANCE.getClass();
                Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                Alignment.Companion.getClass();
                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl2, 0);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl2.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl2, modifierM127paddingVpY3zN4$default);
                ComposeUiNode.Companion.getClass();
                Function0 function02 = ComposeUiNode.Companion.Constructor;
                if (composerImpl2.applier != null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl2.startReusableNode();
                if (composerImpl2.inserting) {
                    composerImpl2.createNode(function02);
                } else {
                    composerImpl2.useNode();
                }
                Updater.m337setimpl(composerImpl2, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m337setimpl(composerImpl2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl2.inserting || !Intrinsics.areEqual(composerImpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl2, currentCompositeKeyHash, function2);
                }
                Updater.m337setimpl(composerImpl2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                ColumnScopeInstance columnScopeInstance = ColumnScopeInstance.INSTANCE;
                if (ConfigurationExtKt.isLandscape(composerImpl2)) {
                    Feature.Companion.getClass();
                    i4 = feature.from != 10 ? 24 : 64;
                    SpacerKt.Spacer(composerImpl2, SizeKt.m131height3ABfNKs(companion2, f));
                    Modifier modifierNestedScroll = NestedScrollModifierKt.nestedScroll(SizeKt.fillMaxSize(companion2, 1.0f), topAppBarScrollBehaviorPinnedScrollBehavior.getNestedScrollConnection(), null);
                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-515393010, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt$SecTitle$1$1
                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
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
                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecTitle.<anonymous>.<anonymous> (ActionBar.kt:56)");
                                    }
                                    final String str2 = str;
                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(638142151, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt$SecTitle$1$1.1
                                        /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
                                        @Override // kotlin.jvm.functions.Function3
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                            SeslTopAppBarTemplate$TitleScope seslTopAppBarTemplate$TitleScope = (SeslTopAppBarTemplate$TitleScope) obj3;
                                            Composer composer3 = (Composer) obj4;
                                            int iIntValue = ((Number) obj5).intValue();
                                            if ((iIntValue & 6) == 0) {
                                                iIntValue |= (iIntValue & 8) == 0 ? ((ComposerImpl) composer3).changed(seslTopAppBarTemplate$TitleScope) : ((ComposerImpl) composer3).changedInstance(seslTopAppBarTemplate$TitleScope) ? 4 : 2;
                                            }
                                            if ((iIntValue & 19) == 18) {
                                                ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                if (composerImpl4.getSkipping()) {
                                                    composerImpl4.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecTitle.<anonymous>.<anonymous>.<anonymous> (ActionBar.kt:58)");
                                                    }
                                                    final String str3 = str2;
                                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda3 = ComposableLambdaKt.rememberComposableLambda(1712267642, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt.SecTitle.1.1.1.1
                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj6, Object obj7) {
                                                            Composer composer4 = (Composer) obj6;
                                                            if ((((Number) obj7).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl5 = (ComposerImpl) composer4;
                                                                if (composerImpl5.getSkipping()) {
                                                                    composerImpl5.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecTitle.<anonymous>.<anonymous>.<anonymous>.<anonymous> (ActionBar.kt:58)");
                                                                    }
                                                                    TextOverflow.Companion.getClass();
                                                                    TextKt.m317Text4IGK_g(str3, null, 0L, 0L, null, null, null, 0L, null, null, 0L, TextOverflow.Ellipsis, false, 1, 0, null, TypeKt.TitleTextStyle(composer4), composer4, 0, 3120, 55294);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composer3);
                                                    SeslTopAppBarTemplate$TitleScope.Companion companion3 = SeslTopAppBarTemplate$TitleScope.Companion;
                                                    seslTopAppBarTemplate$TitleScope.Title(((iIntValue << 9) & 7168) | 6, composer3, composableLambdaImplRememberComposableLambda3, null);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composer2);
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    composerImpl4.startReplaceGroup(-461827773);
                                    final Function0 function03 = function0;
                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda3 = function03 == null ? null : ComposableLambdaKt.rememberComposableLambda(1616886570, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt$SecTitle$1$1$2$1
                                        /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
                                        /* JADX WARN: Removed duplicated region for block: B:25:0x0065  */
                                        @Override // kotlin.jvm.functions.Function3
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                            SeslTopAppBarTemplate$NavigationScope seslTopAppBarTemplate$NavigationScope = (SeslTopAppBarTemplate$NavigationScope) obj3;
                                            Composer composer3 = (Composer) obj4;
                                            int iIntValue = ((Number) obj5).intValue();
                                            if ((iIntValue & 6) == 0) {
                                                iIntValue |= (iIntValue & 8) == 0 ? ((ComposerImpl) composer3).changed(seslTopAppBarTemplate$NavigationScope) : ((ComposerImpl) composer3).changedInstance(seslTopAppBarTemplate$NavigationScope) ? 4 : 2;
                                            }
                                            if ((iIntValue & 19) == 18) {
                                                ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                if (composerImpl5.getSkipping()) {
                                                    composerImpl5.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecTitle.<anonymous>.<anonymous>.<anonymous>.<anonymous> (ActionBar.kt:62)");
                                                    }
                                                    ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                                    composerImpl6.startReplaceGroup(-30996492);
                                                    final Function0 function04 = function03;
                                                    boolean zChanged = composerImpl6.changed(function04);
                                                    Object objRememberedValue = composerImpl6.rememberedValue();
                                                    if (!zChanged) {
                                                        Composer.Companion.getClass();
                                                        if (objRememberedValue == Composer.Companion.Empty) {
                                                            objRememberedValue = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt$SecTitle$1$1$2$1$$ExternalSyntheticLambda0
                                                                @Override // kotlin.jvm.functions.Function0
                                                                public final Object invoke() {
                                                                    function04.invoke();
                                                                    return Unit.INSTANCE;
                                                                }
                                                            };
                                                            composerImpl6.updateRememberedValue(objRememberedValue);
                                                        }
                                                        composerImpl6.end(false);
                                                        ComposableSingletons$ActionBarKt.INSTANCE.getClass();
                                                        ComposableLambdaImpl composableLambdaImpl5 = ComposableSingletons$ActionBarKt.f85lambda2;
                                                        SeslTopAppBarTemplate$NavigationScope.Companion companion3 = SeslTopAppBarTemplate$NavigationScope.Companion;
                                                        seslTopAppBarTemplate$NavigationScope.NavigationUp((Function0) objRememberedValue, null, null, composableLambdaImpl5, composerImpl6, 3072 | ((iIntValue << 12) & 57344));
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl4);
                                    composerImpl4.end(false);
                                    WindowInsets WindowInsets = WindowInsetsKt.WindowInsets();
                                    SeslTopAppBarDefaults seslTopAppBarDefaults = SeslTopAppBarDefaults.INSTANCE;
                                    Color.Companion.getClass();
                                    long j = Color.Transparent;
                                    seslTopAppBarDefaults.getClass();
                                    com.samsung.sesl.compose.component.AppBarKt.m3337SeslTopAppBarau3_HiA(composableLambdaImplRememberComposableLambda2, null, composableLambdaImplRememberComposableLambda3, composableLambdaImpl4, WindowInsets, SeslTopAppBarDefaults.m3345topAppBarColors5tl4gsc(j, composerImpl4), 0.0f, composerImpl4, 6, 66);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl2);
                    ComposableSingletons$ActionBarKt.INSTANCE.getClass();
                    ScaffoldKt.m3341SeslScaffold5k0As8s(modifierNestedScroll, composableLambdaImplRememberComposableLambda, null, null, null, ComposableSingletons$ActionBarKt.f86lambda3, 0, 0L, null, ComposableLambdaKt.rememberComposableLambda(-980437253, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt$SecTitle$1$2
                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        @Override // kotlin.jvm.functions.Function3
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            PaddingValues paddingValues = (PaddingValues) obj;
                            Composer composer2 = (Composer) obj2;
                            int iIntValue = ((Number) obj3).intValue();
                            if ((iIntValue & 6) == 0) {
                                iIntValue |= ((ComposerImpl) composer2).changed(paddingValues) ? 4 : 2;
                            }
                            if ((iIntValue & 19) == 18) {
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                if (composerImpl3.getSkipping()) {
                                    composerImpl3.skipToGroupEnd();
                                } else {
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecTitle.<anonymous>.<anonymous> (ActionBar.kt:81)");
                                    }
                                    float f3 = fMax;
                                    Dp dpM837boximpl = Dp.m837boximpl(f3);
                                    float f4 = 0;
                                    if (Float.compare(dpM837boximpl.value, f4) > 0) {
                                        dpM837boximpl = null;
                                    }
                                    float f5 = dpM837boximpl != null ? 15 : f4;
                                    float fMo113calculateTopPaddingD9Ej5fM = paddingValues.mo113calculateTopPaddingD9Ej5fM();
                                    Dp dpM837boximpl2 = Dp.m837boximpl(f3);
                                    if ((Float.compare(dpM837boximpl2.value, f4) <= 0 ? dpM837boximpl2 : null) != null) {
                                        f4 = 15;
                                    }
                                    composableLambdaImpl2.invoke(PaddingKt.m123PaddingValuesa9UjIt4(f5, fMo113calculateTopPaddingD9Ej5fM, f4, paddingValues.mo110calculateBottomPaddingD9Ej5fM() + f2), composer2, 0);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, composerImpl2), composerImpl2, 196656, 988);
                    composerImpl = composerImpl2;
                    composerImpl.end(true);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
                f = i4;
                SpacerKt.Spacer(composerImpl2, SizeKt.m131height3ABfNKs(companion2, f));
                Modifier modifierNestedScroll2 = NestedScrollModifierKt.nestedScroll(SizeKt.fillMaxSize(companion2, 1.0f), topAppBarScrollBehaviorPinnedScrollBehavior.getNestedScrollConnection(), null);
                ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(-515393010, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt$SecTitle$1$1
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
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
                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecTitle.<anonymous>.<anonymous> (ActionBar.kt:56)");
                                }
                                final String str2 = str;
                                ComposableLambdaImpl composableLambdaImplRememberComposableLambda22 = ComposableLambdaKt.rememberComposableLambda(638142151, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt$SecTitle$1$1.1
                                    /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
                                    @Override // kotlin.jvm.functions.Function3
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                        SeslTopAppBarTemplate$TitleScope seslTopAppBarTemplate$TitleScope = (SeslTopAppBarTemplate$TitleScope) obj3;
                                        Composer composer3 = (Composer) obj4;
                                        int iIntValue = ((Number) obj5).intValue();
                                        if ((iIntValue & 6) == 0) {
                                            iIntValue |= (iIntValue & 8) == 0 ? ((ComposerImpl) composer3).changed(seslTopAppBarTemplate$TitleScope) : ((ComposerImpl) composer3).changedInstance(seslTopAppBarTemplate$TitleScope) ? 4 : 2;
                                        }
                                        if ((iIntValue & 19) == 18) {
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                            if (composerImpl4.getSkipping()) {
                                                composerImpl4.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecTitle.<anonymous>.<anonymous>.<anonymous> (ActionBar.kt:58)");
                                                }
                                                final String str3 = str2;
                                                ComposableLambdaImpl composableLambdaImplRememberComposableLambda3 = ComposableLambdaKt.rememberComposableLambda(1712267642, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt.SecTitle.1.1.1.1
                                                    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
                                                    @Override // kotlin.jvm.functions.Function2
                                                    /*
                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                    */
                                                    public final Object invoke(Object obj6, Object obj7) {
                                                        Composer composer4 = (Composer) obj6;
                                                        if ((((Number) obj7).intValue() & 3) == 2) {
                                                            ComposerImpl composerImpl5 = (ComposerImpl) composer4;
                                                            if (composerImpl5.getSkipping()) {
                                                                composerImpl5.skipToGroupEnd();
                                                            } else {
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecTitle.<anonymous>.<anonymous>.<anonymous>.<anonymous> (ActionBar.kt:58)");
                                                                }
                                                                TextOverflow.Companion.getClass();
                                                                TextKt.m317Text4IGK_g(str3, null, 0L, 0L, null, null, null, 0L, null, null, 0L, TextOverflow.Ellipsis, false, 1, 0, null, TypeKt.TitleTextStyle(composer4), composer4, 0, 3120, 55294);
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventEnd();
                                                                }
                                                            }
                                                        }
                                                        return Unit.INSTANCE;
                                                    }
                                                }, composer3);
                                                SeslTopAppBarTemplate$TitleScope.Companion companion3 = SeslTopAppBarTemplate$TitleScope.Companion;
                                                seslTopAppBarTemplate$TitleScope.Title(((iIntValue << 9) & 7168) | 6, composer3, composableLambdaImplRememberComposableLambda3, null);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composer2);
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                composerImpl4.startReplaceGroup(-461827773);
                                final Function0 function03 = function0;
                                ComposableLambdaImpl composableLambdaImplRememberComposableLambda3 = function03 == null ? null : ComposableLambdaKt.rememberComposableLambda(1616886570, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt$SecTitle$1$1$2$1
                                    /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
                                    /* JADX WARN: Removed duplicated region for block: B:25:0x0065  */
                                    @Override // kotlin.jvm.functions.Function3
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                        SeslTopAppBarTemplate$NavigationScope seslTopAppBarTemplate$NavigationScope = (SeslTopAppBarTemplate$NavigationScope) obj3;
                                        Composer composer3 = (Composer) obj4;
                                        int iIntValue = ((Number) obj5).intValue();
                                        if ((iIntValue & 6) == 0) {
                                            iIntValue |= (iIntValue & 8) == 0 ? ((ComposerImpl) composer3).changed(seslTopAppBarTemplate$NavigationScope) : ((ComposerImpl) composer3).changedInstance(seslTopAppBarTemplate$NavigationScope) ? 4 : 2;
                                        }
                                        if ((iIntValue & 19) == 18) {
                                            ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                            if (composerImpl5.getSkipping()) {
                                                composerImpl5.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecTitle.<anonymous>.<anonymous>.<anonymous>.<anonymous> (ActionBar.kt:62)");
                                                }
                                                ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                                composerImpl6.startReplaceGroup(-30996492);
                                                final Function0 function04 = function03;
                                                boolean zChanged = composerImpl6.changed(function04);
                                                Object objRememberedValue = composerImpl6.rememberedValue();
                                                if (!zChanged) {
                                                    Composer.Companion.getClass();
                                                    if (objRememberedValue == Composer.Companion.Empty) {
                                                        objRememberedValue = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt$SecTitle$1$1$2$1$$ExternalSyntheticLambda0
                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                function04.invoke();
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        composerImpl6.updateRememberedValue(objRememberedValue);
                                                    }
                                                    composerImpl6.end(false);
                                                    ComposableSingletons$ActionBarKt.INSTANCE.getClass();
                                                    ComposableLambdaImpl composableLambdaImpl5 = ComposableSingletons$ActionBarKt.f85lambda2;
                                                    SeslTopAppBarTemplate$NavigationScope.Companion companion3 = SeslTopAppBarTemplate$NavigationScope.Companion;
                                                    seslTopAppBarTemplate$NavigationScope.NavigationUp((Function0) objRememberedValue, null, null, composableLambdaImpl5, composerImpl6, 3072 | ((iIntValue << 12) & 57344));
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composerImpl4);
                                composerImpl4.end(false);
                                WindowInsets WindowInsets = WindowInsetsKt.WindowInsets();
                                SeslTopAppBarDefaults seslTopAppBarDefaults = SeslTopAppBarDefaults.INSTANCE;
                                Color.Companion.getClass();
                                long j = Color.Transparent;
                                seslTopAppBarDefaults.getClass();
                                com.samsung.sesl.compose.component.AppBarKt.m3337SeslTopAppBarau3_HiA(composableLambdaImplRememberComposableLambda22, null, composableLambdaImplRememberComposableLambda3, composableLambdaImpl4, WindowInsets, SeslTopAppBarDefaults.m3345topAppBarColors5tl4gsc(j, composerImpl4), 0.0f, composerImpl4, 6, 66);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2);
                ComposableSingletons$ActionBarKt.INSTANCE.getClass();
                ScaffoldKt.m3341SeslScaffold5k0As8s(modifierNestedScroll2, composableLambdaImplRememberComposableLambda2, null, null, null, ComposableSingletons$ActionBarKt.f86lambda3, 0, 0L, null, ComposableLambdaKt.rememberComposableLambda(-980437253, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt$SecTitle$1$2
                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    @Override // kotlin.jvm.functions.Function3
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        PaddingValues paddingValues = (PaddingValues) obj;
                        Composer composer2 = (Composer) obj2;
                        int iIntValue = ((Number) obj3).intValue();
                        if ((iIntValue & 6) == 0) {
                            iIntValue |= ((ComposerImpl) composer2).changed(paddingValues) ? 4 : 2;
                        }
                        if ((iIntValue & 19) == 18) {
                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                            if (composerImpl3.getSkipping()) {
                                composerImpl3.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.widget.SecTitle.<anonymous>.<anonymous> (ActionBar.kt:81)");
                                }
                                float f3 = fMax;
                                Dp dpM837boximpl = Dp.m837boximpl(f3);
                                float f4 = 0;
                                if (Float.compare(dpM837boximpl.value, f4) > 0) {
                                    dpM837boximpl = null;
                                }
                                float f5 = dpM837boximpl != null ? 15 : f4;
                                float fMo113calculateTopPaddingD9Ej5fM = paddingValues.mo113calculateTopPaddingD9Ej5fM();
                                Dp dpM837boximpl2 = Dp.m837boximpl(f3);
                                if ((Float.compare(dpM837boximpl2.value, f4) <= 0 ? dpM837boximpl2 : null) != null) {
                                    f4 = 15;
                                }
                                composableLambdaImpl2.invoke(PaddingKt.m123PaddingValuesa9UjIt4(f5, fMo113calculateTopPaddingD9Ej5fM, f4, paddingValues.mo110calculateBottomPaddingD9Ej5fM() + f2), composer2, 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2), composerImpl2, 196656, 988);
                composerImpl = composerImpl2;
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                }
            } else {
                composerImpl2.skipToGroupEnd();
                composerImpl = composerImpl2;
                composableLambdaImpl4 = composableLambdaImpl3;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.widget.ActionBarKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        ComposableLambdaImpl composableLambdaImpl5 = composableLambdaImpl2;
                        ActionBarKt.SecTitle(function0, str, composableLambdaImpl4, composableLambdaImpl5, (Composer) obj, iUpdateChangedFlags, i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 384;
        composableLambdaImpl3 = composableLambdaImpl;
        if ((i & 3072) == 0) {
        }
        if ((i3 & 1171) == 1170) {
            if (i5 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            Feature feature2 = (Feature) composerImpl2.consume(CompositionExtKt.LocalFeature);
            TopAppBarDefaults topAppBarDefaults2 = TopAppBarDefaults.INSTANCE;
            TopAppBarState topAppBarStateRememberTopAppBarState2 = AppBarKt.rememberTopAppBarState(composerImpl2);
            topAppBarDefaults2.getClass();
            TopAppBarScrollBehavior topAppBarScrollBehaviorPinnedScrollBehavior2 = TopAppBarDefaults.pinnedScrollBehavior(topAppBarStateRememberTopAppBarState2, composerImpl2);
            float f3 = 0;
            Dp.Companion companion3 = Dp.Companion;
            final float fMax2 = Math.max(f3, ((1.0f - ContainerBoxKt.containerFraction(feature2.isFullScreen, composerImpl2, 0).getWidth()) * DpSize.m847getWidthD9Ej5fM(((DpSize) composerImpl2.consume(CompositionExtKt.LocalRootSize)).packedValue)) / 2);
            if (!ConfigurationExtKt.isLandscape(composerImpl2)) {
            }
            Modifier.Companion companion22 = Modifier.Companion;
            Modifier modifierM127paddingVpY3zN4$default2 = PaddingKt.m127paddingVpY3zN4$default(companion22, fMax2, 0.0f, 2);
            Arrangement.INSTANCE.getClass();
            Arrangement$Top$1 arrangement$Top$12 = Arrangement.Top;
            Alignment.Companion.getClass();
            ColumnMeasurePolicy columnMeasurePolicy2 = ColumnKt.columnMeasurePolicy(arrangement$Top$12, Alignment.Companion.Start, composerImpl2, 0);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl2);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl2.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl2, modifierM127paddingVpY3zN4$default2);
            ComposeUiNode.Companion.getClass();
            Function0 function022 = ComposeUiNode.Companion.Constructor;
            if (composerImpl2.applier != null) {
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
