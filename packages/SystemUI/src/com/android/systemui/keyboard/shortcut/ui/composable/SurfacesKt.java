package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.IndicationKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.InteractiveComponentSizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.MinimumInteractiveModifier;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.material3.TextKt;
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
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import com.android.systemui.keyboard.shortcut.ui.model.IconSource;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SurfacesKt {
    /* renamed from: ClickableShortcutSurface-9FW6N_Y, reason: not valid java name */
    public static final void m2578ClickableShortcutSurface9FW6N_Y(final Function0 function0, final Modifier modifier, boolean z, final RoundedCornerShape roundedCornerShape, final long j, BorderStroke borderStroke, final InteractionsConfig interactionsConfig, final ComposableLambdaImpl composableLambdaImpl, Composer composer, int i, int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(732300793);
        final boolean z2 = (i2 & 4) != 0 ? true : z;
        long m258contentColorForek8zF_U = ColorSchemeKt.m258contentColorForek8zF_U(j, composerImpl);
        float f = 0;
        Dp.Companion companion = Dp.Companion;
        final float f2 = 0;
        final BorderStroke borderStroke2 = (i2 & 256) != 0 ? null : borderStroke;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ClickableShortcutSurface (Surfaces.kt:161)");
        }
        composerImpl.startReplaceGroup(-1133595928);
        composerImpl.startReplaceGroup(-1133595277);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue = InteractionSourceKt.MutableInteractionSource();
            composerImpl.updateRememberedValue(rememberedValue);
        }
        final MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) rememberedValue;
        composerImpl.end(false);
        composerImpl.end(false);
        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = SurfaceKt.LocalAbsoluteTonalElevation;
        final float f3 = ((Dp) composerImpl.consume(dynamicProvidableCompositionLocal)).value + f;
        CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m454boximpl(m258contentColorForek8zF_U)), dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Dp.m835boximpl(f3))}, ComposableLambdaKt.rememberComposableLambda(-117290311, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt$ClickableShortcutSurface$1
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
                    ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ClickableShortcutSurface.<anonymous> (Surfaces.kt:169)");
                }
                StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                Modifier then = Modifier.this.then(MinimumInteractiveModifier.INSTANCE);
                long m2582access$surfaceColorAtElevationCLU3JFs = SurfacesKt.m2582access$surfaceColorAtElevationCLU3JFs(j, f3, composer2);
                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                Modifier m34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(SurfacesKt.m2581access$surfaceXOJAsU(then, roundedCornerShape, m2582access$surfaceColorAtElevationCLU3JFs, borderStroke2, ((Density) composerImpl3.consume(CompositionLocalsKt.LocalDensity)).mo57toPx0680j_4(f2)), mutableInteractionSource, new ShortcutHelperIndication(interactionsConfig), z2, null, null, function0, 24);
                Alignment.Companion.getClass();
                MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, true);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, m34clickableO2vRcR0$default);
                ComposeUiNode.Companion.getClass();
                Function0 function02 = ComposeUiNode.Companion.Constructor;
                if (composerImpl3.applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl3.startReusableNode();
                if (composerImpl3.inserting) {
                    composerImpl3.createNode(function02);
                } else {
                    composerImpl3.useNode();
                }
                Updater.m336setimpl(composerImpl3, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                Updater.m336setimpl(composerImpl3, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                }
                Updater.m336setimpl(composerImpl3, materializeModifier, ComposeUiNode.Companion.SetModifier);
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                composableLambdaImpl.invoke(composerImpl3, 0);
                composerImpl3.end(true);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }, composerImpl), composerImpl, 56);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }

    public static final void ProvideShortcutHelperIndication(final InteractionsConfig interactionsConfig, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(490602238);
        if ((((composerImpl.changed(interactionsConfig) ? 4 : 2) | i) & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ProvideShortcutHelperIndication (Surfaces.kt:445)");
            }
            CompositionLocalKt.CompositionLocalProvider(IndicationKt.LocalIndication.defaultProvidedValue$runtime_release(new ShortcutHelperIndication(interactionsConfig)), ComposableLambdaKt.rememberComposableLambda(2070672318, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt$ProvideShortcutHelperIndication$1
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
                        ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ProvideShortcutHelperIndication.<anonymous> (Surfaces.kt:449)");
                    }
                    Function2.this.invoke(composer2, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(composableLambdaImpl, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt$$ExternalSyntheticLambda0
                public final /* synthetic */ ComposableLambdaImpl f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                    SurfacesKt.ProvideShortcutHelperIndication(InteractionsConfig.this, this.f$1, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:78:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0232  */
    /* renamed from: ShortcutHelperButton-01TuoB8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m2579ShortcutHelperButton01TuoB8(final kotlin.jvm.functions.Function0 r43, final long r44, final long r46, final androidx.compose.ui.Modifier r48, androidx.compose.foundation.shape.RoundedCornerShape r49, com.android.systemui.keyboard.shortcut.ui.model.IconSource r50, java.lang.String r51, float r52, float r53, boolean r54, androidx.compose.foundation.BorderStroke r55, java.lang.String r56, androidx.compose.runtime.Composer r57, final int r58, final int r59) {
        /*
            Method dump skipped, instructions count: 767
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt.m2579ShortcutHelperButton01TuoB8(kotlin.jvm.functions.Function0, long, long, androidx.compose.ui.Modifier, androidx.compose.foundation.shape.RoundedCornerShape, com.android.systemui.keyboard.shortcut.ui.model.IconSource, java.lang.String, float, float, boolean, androidx.compose.foundation.BorderStroke, java.lang.String, androidx.compose.runtime.Composer, int, int):void");
    }

    /* renamed from: ShortcutHelperButtonContent-3IgeMak, reason: not valid java name */
    public static final void m2580ShortcutHelperButtonContent3IgeMak(final IconSource iconSource, final long j, final String str, final String str2, Composer composer, final int i) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-408906921);
        int i2 = i | (composerImpl2.changedInstance(iconSource) ? 4 : 2) | (composerImpl2.changed(j) ? 32 : 16) | (composerImpl2.changed(str) ? 256 : 128) | (composerImpl2.changed(str2) ? 2048 : 1024);
        if ((i2 & 1171) == 1170 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperButtonContent (Surfaces.kt:271)");
            }
            composerImpl2.startReplaceGroup(149024134);
            ImageVector imageVector = iconSource.imageVector;
            if (imageVector != null) {
                Dp.Companion companion = Dp.Companion;
                Modifier m139size3ABfNKs = SizeKt.m139size3ABfNKs(Modifier.Companion, 20);
                Alignment.Companion.getClass();
                IconKt.m270Iconww6aTOc(imageVector, str2, SizeKt.wrapContentSize$default(m139size3ABfNKs, Alignment.Companion.Center, 2), j, composerImpl2, ((i2 << 6) & 7168) | ((i2 >> 6) & 112) | 384, 0);
            }
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(149033159);
            if (iconSource.imageVector != null && str != null) {
                Dp.Companion companion2 = Dp.Companion;
                SpacerKt.Spacer(composerImpl2, SizeKt.m143width3ABfNKs(Modifier.Companion, 8));
            }
            composerImpl2.end(false);
            if (str != null) {
                long sp = TextUnitKt.getSp(14);
                MaterialTheme.INSTANCE.getClass();
                TextStyle textStyle = MaterialTheme.getTypography(composerImpl2).labelLarge;
                Modifier.Companion companion3 = Modifier.Companion;
                Alignment.Companion.getClass();
                Modifier wrapContentSize$default = SizeKt.wrapContentSize$default(companion3, Alignment.Companion.Center, 2);
                TextOverflow.Companion.getClass();
                composerImpl = composerImpl2;
                TextKt.m316Text4IGK_g(str, wrapContentSize$default, j, sp, null, null, null, 0L, null, null, 0L, TextOverflow.Ellipsis, false, 0, 0, null, textStyle, composerImpl, (14 & (i2 >> 6)) | 3120 | ((i2 << 3) & 896), 48, 63472);
            } else {
                composerImpl = composerImpl2;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(j, str, str2, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt$$ExternalSyntheticLambda3
                public final /* synthetic */ long f$1;
                public final /* synthetic */ String f$2;
                public final /* synthetic */ String f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    String str3 = this.f$2;
                    String str4 = this.f$3;
                    SurfacesKt.m2580ShortcutHelperButtonContent3IgeMak(IconSource.this, this.f$1, str3, str4, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: access$surface-XO-JAsU, reason: not valid java name */
    public static final Modifier m2581access$surfaceXOJAsU(Modifier modifier, Shape shape, long j, BorderStroke borderStroke, float f) {
        Modifier then = f > 0.0f ? modifier.then(GraphicsLayerModifierKt.m477graphicsLayer_6ThJ44$default(Modifier.Companion, 0.0f, 0.0f, 0.0f, f, 0.0f, shape, false, 0, 518111)) : modifier;
        if (borderStroke != null) {
            then = then.then(BorderKt.m29borderziNgDLE(Modifier.Companion, borderStroke.width, borderStroke.brush, shape));
        }
        return BackgroundKt.m26backgroundbw27NRU(then, j, shape);
    }

    /* renamed from: access$surfaceColorAtElevation-CLU3JFs, reason: not valid java name */
    public static final long m2582access$surfaceColorAtElevationCLU3JFs(long j, float f, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1038469993);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.surfaceColorAtElevation (Surfaces.kt:299)");
        }
        MaterialTheme.INSTANCE.getClass();
        ColorScheme colorScheme = MaterialTheme.getColorScheme(composerImpl);
        composerImpl.startReplaceGroup(-2055033482);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.applyTonalElevation (Surfaces.kt:304)");
        }
        boolean booleanValue = ((Boolean) composerImpl.consume(ColorSchemeKt.LocalTonalElevationEnabled)).booleanValue();
        long j2 = colorScheme.surface;
        Color.Companion companion = Color.Companion;
        if (ULong.m3427equalsimpl0(j, j2) && booleanValue) {
            j = ColorSchemeKt.m260surfaceColorAtElevation3ABfNKs(colorScheme, f);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return j;
    }
}
