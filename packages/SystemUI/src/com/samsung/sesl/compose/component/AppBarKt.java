package com.samsung.sesl.compose.component;

import android.content.Context;
import android.content.res.Configuration;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsPaddingKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.ComputedProvidableCompositionLocal;
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
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.input.pointer.SuspendPointerInputElement;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.InspectionModeKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.unit.Dp;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.component.tokens.SeslAppBarColorSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslAppBarDimensionSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslDpProducer;
import com.samsung.sesl.compose.foundation.theme.BasicColorSchemeKt;
import com.samsung.sesl.compose.foundation.theme.BasicDimensionSchemeKt;
import com.samsung.sesl.compose.foundation.theme.SeslTokenScheme;
import com.samsung.sesl.compose.foundation.theme.TokenSchemeKt;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$ActionScope;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$NavigationScope;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$TitleScope;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public abstract class AppBarKt {
    /* JADX WARN: Removed duplicated region for block: B:66:0x00d0  */
    /* renamed from: SeslSingleRowTopAppBar-iHT-50w, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m3336SeslSingleRowTopAppBariHT50w(final ComposableLambdaImpl composableLambdaImpl, final Function3 function3, final WindowInsets windowInsets, final SeslTopAppBarColors seslTopAppBarColors, final float f, final Modifier.Companion companion, final ComposableLambdaImpl composableLambdaImpl2, Composer composer, final int i) {
        int i2;
        final ComposableLambdaImpl composableLambdaImpl3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2107573450);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(composableLambdaImpl) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function3) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(windowInsets) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(seslTopAppBarColors) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(f) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changed(companion) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            composableLambdaImpl3 = composableLambdaImpl2;
            i2 |= composerImpl.changedInstance(composableLambdaImpl3) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        } else {
            composableLambdaImpl3 = composableLambdaImpl2;
        }
        if ((i2 & 599187) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSingleRowTopAppBar (AppBar.kt:166)");
            }
            Modifier modifierWindowInsetsPadding = WindowInsetsPaddingKt.windowInsetsPadding(companion, windowInsets);
            composerImpl.startReplaceGroup(-2005196388);
            final long j = seslTopAppBarColors.containerColor;
            boolean zChanged = composerImpl.changed(j);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion2 = Composer.Companion;
            if (!zChanged) {
                companion2.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function1() { // from class: com.samsung.sesl.compose.component.AppBarKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            DrawScope drawScope = (DrawScope) obj;
                            Color.Companion.getClass();
                            long j2 = Color.Unspecified;
                            long j3 = j;
                            if (!ULong.m3447equalsimpl0(j3, j2)) {
                                DrawScope.m541drawRectnJ9OG0$default(drawScope, j3, 0L, 0L, 0.0f, null, null, 0, 126);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(DrawModifierKt.drawBehind(modifierWindowInsetsPadding, (Function1) objRememberedValue), 0.0f, f, 0.0f, 0.0f, 13);
                Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, -2005190038, companion2);
                Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                if (objM == composer$Companion$Empty$1) {
                    objM = new AppBarKt$$ExternalSyntheticLambda2();
                    composerImpl.updateRememberedValue(objM);
                }
                composerImpl.end(false);
                Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierM129paddingqDBjuR0$default, false, (Function1) objM);
                Unit unit = Unit.INSTANCE;
                composerImpl.startReplaceGroup(-2005188271);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (objRememberedValue2 == composer$Companion$Empty$1) {
                    objRememberedValue2 = new AppBarKt$SeslSingleRowTopAppBar$3$1(null);
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                composerImpl.end(false);
                Modifier modifierThen = modifierSemantics.then(new SuspendPointerInputElement(unit, null, null, new SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0((Function2) objRememberedValue2), 6, null));
                SeslTopAppBarDefaults.INSTANCE.getClass();
                composerImpl.startReplaceGroup(887726322);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslTopAppBarDefaults.appBarHeight (AppBar.kt:474)");
                }
                DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = AndroidCompositionLocals_androidKt.LocalConfiguration;
                Configuration configuration = (Configuration) composerImpl.consume(dynamicProvidableCompositionLocal);
                float f2 = ((Configuration) composerImpl.consume(dynamicProvidableCompositionLocal)).screenHeightDp;
                Dp.Companion companion3 = Dp.Companion;
                int i3 = ((Configuration) composerImpl.consume(dynamicProvidableCompositionLocal)).orientation;
                composerImpl.startReplaceGroup(1544320199);
                boolean zChanged2 = composerImpl.changed(configuration);
                Object objRememberedValue3 = composerImpl.rememberedValue();
                if (zChanged2 || objRememberedValue3 == composer$Companion$Empty$1) {
                    objRememberedValue3 = Dp.m837boximpl((i3 != 1 && Float.compare(f2, (float) 579) <= 0) ? 56 : 64);
                    composerImpl.updateRememberedValue(objRememberedValue3);
                }
                float f3 = ((Dp) objRememberedValue3).value;
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                Modifier modifierM131height3ABfNKs = SizeKt.m131height3ABfNKs(modifierThen, f3);
                Alignment.Companion.getClass();
                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                Arrangement.INSTANCE.getClass();
                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composerImpl, 48);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM131height3ABfNKs);
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
                final RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                final long color = BasicColorSchemeKt.toColor(SeslAppBarColorSchemeKeyTokens.TopAppBarTitleTextColor, composerImpl);
                CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(color)), ComposableLambdaKt.rememberComposableLambda(1465816210, new Function2() { // from class: com.samsung.sesl.compose.component.AppBarKt$SeslSingleRowTopAppBar$4$1
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
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
                                    ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSingleRowTopAppBar.<anonymous>.<anonymous> (AppBar.kt:186)");
                                }
                                Function3 function32 = composableLambdaImpl3;
                                if (function32 == null) {
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    composerImpl3.startReplaceGroup(1051497931);
                                    Modifier.Companion companion4 = Modifier.Companion;
                                    SeslTopAppBarDefaults.INSTANCE.getClass();
                                    SpacerKt.Spacer(composerImpl3, SizeKt.m144width3ABfNKs(companion4, SeslTopAppBarDefaults.TopAppBarTitleSlotInsetStart));
                                    composerImpl3.end(false);
                                } else {
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    composerImpl4.startReplaceGroup(1051611112);
                                    Modifier.Companion companion5 = Modifier.Companion;
                                    SeslTopAppBarDefaults.INSTANCE.getClass();
                                    SpacerKt.Spacer(composerImpl4, SizeKt.m144width3ABfNKs(companion5, SeslTopAppBarDefaults.NavigateUpButtonInsetStart));
                                    SeslTopAppBarTemplate$NavigationScope.Companion.getClass();
                                    function32.invoke(SeslTopAppBarTemplate$NavigationScope.instance, composerImpl4, 0);
                                    composerImpl4.end(false);
                                }
                                Modifier modifierWeight = rowScopeInstance.weight(Modifier.Companion, 1.0f, true);
                                Alignment.Companion.getClass();
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl5.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer2, modifierWeight);
                                ComposeUiNode.Companion.getClass();
                                Function0 function02 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl5.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl5.startReusableNode();
                                if (composerImpl5.inserting) {
                                    composerImpl5.createNode(function02);
                                } else {
                                    composerImpl5.useNode();
                                }
                                Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope2, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl5, currentCompositeKeyHash2, function22);
                                }
                                Updater.m337setimpl(composer2, modifierMaterializeModifier2, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                composerImpl5.startReplaceGroup(177993033);
                                StaticProvidableCompositionLocal staticProvidableCompositionLocal = CompositionLocalsKt.LocalDensity;
                                Density density = (Density) composerImpl5.consume(staticProvidableCompositionLocal);
                                float fontScale = density.getFontScale();
                                if (fontScale > 1.3f) {
                                    fontScale = 1.3f;
                                }
                                composerImpl5.startReplaceGroup(-1246172339);
                                StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = AndroidCompositionLocals_androidKt.LocalContext;
                                Context context = (Context) composerImpl5.consume(staticProvidableCompositionLocal2);
                                if (((Boolean) composerImpl5.consume(InspectionModeKt.LocalInspectionMode)).booleanValue()) {
                                    context.getResources().getConfiguration().fontScale = fontScale;
                                }
                                composerImpl5.end(false);
                                ProvidedValue[] providedValueArr = {staticProvidableCompositionLocal.defaultProvidedValue$runtime_release(DensityKt.Density(density.getDensity(), fontScale)), staticProvidableCompositionLocal2.defaultProvidedValue$runtime_release(context)};
                                final long j2 = color;
                                final Function3 function33 = composableLambdaImpl;
                                CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableLambdaKt.rememberComposableLambda(-1027159543, new Function2() { // from class: com.samsung.sesl.compose.component.AppBarKt$SeslSingleRowTopAppBar$4$1$invoke$lambda$1$$inlined$SeslUpToLarge$1
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj3, Object obj4) {
                                        Composer composer3 = (Composer) obj3;
                                        if ((((Number) obj4).intValue() & 3) == 2) {
                                            ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                            if (composerImpl6.getSkipping()) {
                                                composerImpl6.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.samsung.sesl.compose.utils.SeslUpToLarge.<anonymous> (UptoLarge.kt:42)");
                                                }
                                                ComposerImpl composerImpl7 = (ComposerImpl) composer3;
                                                composerImpl7.startReplaceGroup(-297545017);
                                                SeslTopAppBarDefaults.INSTANCE.getClass();
                                                TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(SeslTopAppBarDefaults.titleTextStyle, j2, 0L, null, null, 0L, 0, 0L, null, null, 0, 16777214);
                                                final Function3 function34 = function33;
                                                TextKt.ProvideTextStyle(textStyleM756copyp1EtxEg$default, ComposableLambdaKt.rememberComposableLambda(-198764913, new Function2() { // from class: com.samsung.sesl.compose.component.AppBarKt$SeslSingleRowTopAppBar$4$1$1$1$1
                                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                                    @Override // kotlin.jvm.functions.Function2
                                                    /*
                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                    */
                                                    public final Object invoke(Object obj5, Object obj6) {
                                                        Composer composer4 = (Composer) obj5;
                                                        if ((((Number) obj6).intValue() & 3) == 2) {
                                                            ComposerImpl composerImpl8 = (ComposerImpl) composer4;
                                                            if (composerImpl8.getSkipping()) {
                                                                composerImpl8.skipToGroupEnd();
                                                            } else {
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSingleRowTopAppBar.<anonymous>.<anonymous>.<anonymous>.<anonymous>.<anonymous> (AppBar.kt:199)");
                                                                }
                                                                SeslTopAppBarTemplate$TitleScope.Companion.getClass();
                                                                function34.invoke(SeslTopAppBarTemplate$TitleScope.instance, composer4, 0);
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventEnd();
                                                                }
                                                            }
                                                        }
                                                        return Unit.INSTANCE;
                                                    }
                                                }, composerImpl7), composerImpl7, 48);
                                                composerImpl7.end(false);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composer2), composer2, 56);
                                composerImpl5.end(false);
                                composerImpl5.end(true);
                                ProvidedValue providedValueDefaultProvidedValue$runtime_release = ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(seslTopAppBarColors.actionIconContentColor));
                                final Function3 function34 = function3;
                                CompositionLocalKt.CompositionLocalProvider(providedValueDefaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(90200018, new Function2() { // from class: com.samsung.sesl.compose.component.AppBarKt$SeslSingleRowTopAppBar$4$1.2
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj3, Object obj4) {
                                        Composer composer3 = (Composer) obj3;
                                        if ((((Number) obj4).intValue() & 3) == 2) {
                                            ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                            if (composerImpl6.getSkipping()) {
                                                composerImpl6.skipToGroupEnd();
                                            } else {
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSingleRowTopAppBar.<anonymous>.<anonymous>.<anonymous> (AppBar.kt:204)");
                                                }
                                                SeslTopAppBarTemplate$ActionScope.Companion.getClass();
                                                function34.invoke(SeslTopAppBarTemplate$ActionScope.instance, composer3, 0);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }, composer2), composer2, 56);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl), composerImpl, 56);
                composerImpl.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.AppBarKt$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    WindowInsets windowInsets2 = windowInsets;
                    SeslTopAppBarColors seslTopAppBarColors2 = seslTopAppBarColors;
                    Modifier.Companion companion4 = companion;
                    ComposableLambdaImpl composableLambdaImpl4 = composableLambdaImpl2;
                    AppBarKt.m3336SeslSingleRowTopAppBariHT50w(composableLambdaImpl, function3, windowInsets2, seslTopAppBarColors2, f, companion4, composableLambdaImpl4, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: SeslTopAppBar-au3_HiA, reason: not valid java name */
    public static final void m3337SeslTopAppBarau3_HiA(final ComposableLambdaImpl composableLambdaImpl, Modifier.Companion companion, final ComposableLambdaImpl composableLambdaImpl2, Function3 function3, final WindowInsets windowInsets, final SeslTopAppBarColors seslTopAppBarColors, float f, Composer composer, final int i, final int i2) {
        Function3 function32;
        int i3;
        Function3 function33;
        int i4;
        Function3 function34;
        float f2;
        Modifier.Companion companion2;
        ComposerImpl composerImpl;
        final Function3 function35;
        final float f3;
        final Modifier.Companion companion3;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1528341034);
        int i5 = i | 48;
        if ((i & 384) == 0) {
            i5 |= composerImpl2.changedInstance(composableLambdaImpl2) ? 256 : 128;
        }
        int i6 = i2 & 8;
        if (i6 != 0) {
            i3 = i5 | 3072;
            function32 = function3;
        } else {
            function32 = function3;
            i3 = i5 | (composerImpl2.changedInstance(function32) ? 2048 : 1024);
        }
        int i7 = i3 | (composerImpl2.changed(windowInsets) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | (composerImpl2.changed(seslTopAppBarColors) ? 131072 : 65536) | NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        if ((599187 & i7) == 599186 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            companion3 = companion;
            f3 = f;
            composerImpl = composerImpl2;
            function35 = function32;
        } else {
            composerImpl2.startDefaults();
            if ((i & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                Modifier.Companion companion4 = Modifier.Companion;
                if (i6 != 0) {
                    ComposableSingletons$AppBarKt.INSTANCE.getClass();
                    function33 = ComposableSingletons$AppBarKt.f119lambda2;
                } else {
                    function33 = function32;
                }
                SeslAppBarDimensionSchemeKeyTokens seslAppBarDimensionSchemeKeyTokens = SeslAppBarDimensionSchemeKeyTokens.TopAppBarTopPadding;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.theme.toDp (BasicDimensionScheme.kt:28)");
                }
                SeslDpProducer.Params params = new SeslDpProducer.Params((Configuration) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalConfiguration));
                ComputedProvidableCompositionLocal computedProvidableCompositionLocal = TokenSchemeKt.LocalSeslTokenScheme;
                BasicDimensionSchemeKt.fromToken((SeslTokenScheme) composerImpl2.consume(computedProvidableCompositionLocal), seslAppBarDimensionSchemeKeyTokens);
                float fMo3335produceu2uoSUM = BasicDimensionSchemeKt.fromToken((SeslTokenScheme) composerImpl2.consume(computedProvidableCompositionLocal), seslAppBarDimensionSchemeKeyTokens).mo3335produceu2uoSUM(params);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                i4 = i7 & (-3670017);
                function34 = function33;
                f2 = fMo3335produceu2uoSUM;
                companion2 = companion4;
            } else {
                composerImpl2.skipToGroupEnd();
                i4 = i7 & (-3670017);
                companion2 = companion;
                f2 = f;
                function34 = function32;
            }
            composerImpl2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslTopAppBar (AppBar.kt:141)");
            }
            int i8 = i4 >> 6;
            composerImpl = composerImpl2;
            m3336SeslSingleRowTopAppBariHT50w(composableLambdaImpl, function34, windowInsets, seslTopAppBarColors, f2, companion2, composableLambdaImpl2, composerImpl, (i8 & 7168) | (i8 & 112) | 6 | (i8 & 896) | 196608 | ((i4 << 12) & 3670016));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            function35 = function34;
            f3 = f2;
            companion3 = companion2;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.AppBarKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    WindowInsets windowInsets2 = windowInsets;
                    SeslTopAppBarColors seslTopAppBarColors2 = seslTopAppBarColors;
                    float f4 = f3;
                    AppBarKt.m3337SeslTopAppBarau3_HiA(composableLambdaImpl, companion3, composableLambdaImpl2, function35, windowInsets2, seslTopAppBarColors2, f4, (Composer) obj, iUpdateChangedFlags, i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
