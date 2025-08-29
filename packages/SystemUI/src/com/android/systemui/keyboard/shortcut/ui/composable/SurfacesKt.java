package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.IndicationKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
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
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import com.android.systemui.keyboard.shortcut.ui.model.IconSource;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class SurfacesKt {
    /* renamed from: ClickableShortcutSurface-9FW6N_Y, reason: not valid java name */
    public static final void m2593ClickableShortcutSurface9FW6N_Y(final Function0 function0, final Modifier modifier, boolean z, final RoundedCornerShape roundedCornerShape, final long j, BorderStroke borderStroke, final InteractionsConfig interactionsConfig, final ComposableLambdaImpl composableLambdaImpl, Composer composer, int i, int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(732300793);
        final boolean z2 = (i2 & 4) != 0 ? true : z;
        long jM259contentColorForek8zF_U = ColorSchemeKt.m259contentColorForek8zF_U(j, composerImpl);
        float f = 0;
        Dp.Companion companion = Dp.Companion;
        final float f2 = 0;
        final BorderStroke borderStroke2 = (i2 & 256) != 0 ? null : borderStroke;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ClickableShortcutSurface (Surfaces.kt:161)");
        }
        composerImpl.startReplaceGroup(-1133595928);
        composerImpl.startReplaceGroup(-1133595277);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (objRememberedValue == Composer.Companion.Empty) {
            objRememberedValue = InteractionSourceKt.MutableInteractionSource();
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        final MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) objRememberedValue;
        composerImpl.end(false);
        composerImpl.end(false);
        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = SurfaceKt.LocalAbsoluteTonalElevation;
        final float f3 = ((Dp) composerImpl.consume(dynamicProvidableCompositionLocal)).value + f;
        CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(jM259contentColorForek8zF_U)), dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Dp.m837boximpl(f3))}, ComposableLambdaKt.rememberComposableLambda(-117290311, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt$ClickableShortcutSurface$1
            /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
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
                            ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ClickableShortcutSurface.<anonymous> (Surfaces.kt:169)");
                        }
                        StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                        Modifier modifierThen = modifier.then(MinimumInteractiveModifier.INSTANCE);
                        long jM2597access$surfaceColorAtElevationCLU3JFs = SurfacesKt.m2597access$surfaceColorAtElevationCLU3JFs(j, f3, composer2);
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        Modifier modifierM34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(SurfacesKt.m2596access$surfaceXOJAsU(modifierThen, roundedCornerShape, jM2597access$surfaceColorAtElevationCLU3JFs, borderStroke2, ((Density) composerImpl3.consume(CompositionLocalsKt.LocalDensity)).mo58toPx0680j_4(f2)), mutableInteractionSource, new ShortcutHelperIndication(interactionsConfig), z2, null, null, function0, 24);
                        Alignment.Companion.getClass();
                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, true);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierM34clickableO2vRcR0$default);
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
                        Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                        }
                        Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                        composableLambdaImpl.invoke(composerImpl3, 0);
                        composerImpl3.end(true);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
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
            CompositionLocalKt.CompositionLocalProvider(IndicationKt.LocalIndication.defaultProvidedValue$runtime_release(new ShortcutHelperIndication(interactionsConfig)), ComposableLambdaKt.rememberComposableLambda(2070672318, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt.ProvideShortcutHelperIndication.1
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
                                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ProvideShortcutHelperIndication.<anonymous> (Surfaces.kt:449)");
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
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(composableLambdaImpl, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt$$ExternalSyntheticLambda0
                public final /* synthetic */ ComposableLambdaImpl f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                    SurfacesKt.ProvideShortcutHelperIndication(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:173:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x012e  */
    /* renamed from: ShortcutHelperButton-01TuoB8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m2594ShortcutHelperButton01TuoB8(final Function0 function0, final long j, final long j2, final Modifier modifier, RoundedCornerShape roundedCornerShape, IconSource iconSource, String str, float f, float f2, boolean z, BorderStroke borderStroke, String str2, Composer composer, final int i, final int i2) {
        Function0 function02;
        int i3;
        RoundedCornerShape roundedCornerShape2;
        int i4;
        String str3;
        int i5;
        float f3;
        int i6;
        float f4;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        RoundedCornerShape roundedCornerShapeM187RoundedCornerShape0680j_4;
        RoundedCornerShape roundedCornerShape3;
        IconSource iconSource2;
        float f5;
        String str4;
        BorderStroke borderStroke2;
        int i12;
        IconSource iconSource3;
        boolean z2;
        Object objRememberedValue;
        ComposerImpl composerImpl;
        final float f6;
        final IconSource iconSource4;
        final String str5;
        final boolean z3;
        final RoundedCornerShape roundedCornerShape4;
        final BorderStroke borderStroke3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(335385849);
        if ((i & 6) == 0) {
            function02 = function0;
            i3 = i | (composerImpl2.changedInstance(function02) ? 4 : 2);
        } else {
            function02 = function0;
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl2.changed(j) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl2.changed(j2) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i3 |= composerImpl2.changed(modifier) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            if ((i2 & 16) == 0) {
                roundedCornerShape2 = roundedCornerShape;
                int i13 = composerImpl2.changed(roundedCornerShape2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                i3 |= i13;
            } else {
                roundedCornerShape2 = roundedCornerShape;
            }
            i3 |= i13;
        } else {
            roundedCornerShape2 = roundedCornerShape;
        }
        if ((i & 196608) == 0) {
            i3 |= ((i2 & 32) == 0 && composerImpl2.changedInstance(iconSource)) ? 131072 : 65536;
        }
        int i14 = i2 & 64;
        if (i14 == 0) {
            if ((i & 1572864) == 0) {
                i4 = 16;
                str3 = str;
                i3 |= composerImpl2.changed(str3) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
            }
            i5 = 128 & i2;
            if (i5 == 0) {
                i3 |= 12582912;
                f3 = f;
            } else {
                f3 = f;
                if ((i & 12582912) == 0) {
                    i3 |= composerImpl2.changed(f3) ? 8388608 : 4194304;
                }
            }
            i6 = 256 & i2;
            if (i6 == 0) {
                i3 |= 100663296;
                f4 = f2;
            } else {
                f4 = f2;
                if ((i & 100663296) == 0) {
                    i3 |= composerImpl2.changed(f4) ? 67108864 : 33554432;
                }
            }
            i7 = i2 & 512;
            if (i7 == 0) {
                i3 |= 805306368;
                i8 = i7;
            } else if ((i & 805306368) == 0) {
                i8 = i7;
                i3 |= composerImpl2.changed(z) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
            } else {
                i8 = i7;
            }
            i9 = 1024 & i2;
            int i15 = i9 == 0 ? 6 : composerImpl2.changed(borderStroke) ? 4 : 2;
            i10 = 2048 & i2;
            if (i10 == 0) {
                i11 = i15 | 48;
            } else {
                i11 = i15 | (composerImpl2.changed(str2) ? 32 : i4);
            }
            if ((i3 & 306783379) != 306783378 && (i11 & 19) == 18 && composerImpl2.getSkipping()) {
                composerImpl2.skipToGroupEnd();
                iconSource4 = iconSource;
                borderStroke3 = borderStroke;
                f6 = f3;
                composerImpl = composerImpl2;
                roundedCornerShape4 = roundedCornerShape2;
                z3 = z;
                str5 = str2;
            } else {
                composerImpl2.startDefaults();
                if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                    if ((i2 & 16) == 0) {
                        Dp.Companion companion = Dp.Companion;
                        roundedCornerShapeM187RoundedCornerShape0680j_4 = RoundedCornerShapeKt.m187RoundedCornerShape0680j_4(360);
                        i3 &= -57345;
                    } else {
                        roundedCornerShapeM187RoundedCornerShape0680j_4 = roundedCornerShape2;
                    }
                    if ((i2 & 32) == 0) {
                        roundedCornerShape3 = roundedCornerShapeM187RoundedCornerShape0680j_4;
                        iconSource2 = new IconSource(null, null, 3, null);
                        i3 &= -458753;
                    } else {
                        roundedCornerShape3 = roundedCornerShapeM187RoundedCornerShape0680j_4;
                        iconSource2 = iconSource;
                    }
                    if (i14 != 0) {
                        str3 = null;
                    }
                    if (i5 == 0) {
                        f5 = i4;
                        Dp.Companion companion2 = Dp.Companion;
                    } else {
                        f5 = f3;
                    }
                    if (i6 != 0) {
                        f4 = 10;
                        Dp.Companion companion3 = Dp.Companion;
                    }
                    boolean z4 = i8 == 0 ? z : true;
                    BorderStroke borderStroke4 = i9 == 0 ? null : borderStroke;
                    str4 = i10 == 0 ? str2 : null;
                    borderStroke2 = borderStroke4;
                    i12 = i11;
                    f3 = f5;
                    iconSource3 = iconSource2;
                    z2 = z4;
                    roundedCornerShape2 = roundedCornerShape3;
                } else {
                    composerImpl2.skipToGroupEnd();
                    if ((i2 & 16) != 0) {
                        i3 &= -57345;
                    }
                    if ((i2 & 32) != 0) {
                        i3 &= -458753;
                    }
                    iconSource3 = iconSource;
                    borderStroke2 = borderStroke;
                    str4 = str2;
                    i12 = i11;
                    z2 = z;
                }
                composerImpl2.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperButton (Surfaces.kt:230)");
                }
                long jColor = !z2 ? j2 : ColorKt.Color(Color.m463getRedimpl(j2), Color.m462getGreenimpl(j2), Color.m460getBlueimpl(j2), 0.38f, Color.m461getColorSpaceimpl(j2));
                composerImpl2.startReplaceGroup(-646046205);
                objRememberedValue = composerImpl2.rememberedValue();
                Composer.Companion.getClass();
                final IconSource iconSource5 = iconSource3;
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new SurfacesKt$$ExternalSyntheticLambda1();
                    composerImpl2.updateRememberedValue(objRememberedValue);
                }
                composerImpl2.end(false);
                Modifier modifierSemantics = SemanticsModifierKt.semantics(modifier, false, (Function1) objRememberedValue);
                MaterialTheme.INSTANCE.getClass();
                final String str6 = str4;
                Dp.Companion companion4 = Dp.Companion;
                final float f7 = f4;
                final float f8 = f3;
                final String str7 = str3;
                RoundedCornerShape roundedCornerShape5 = roundedCornerShape2;
                composerImpl = composerImpl2;
                m2593ClickableShortcutSurface9FW6N_Y(function02, modifierSemantics, z2, roundedCornerShape5, jColor, borderStroke2, new InteractionsConfig(MaterialTheme.getColorScheme(composerImpl2).onSurface, 0.11f, MaterialTheme.getColorScheme(composerImpl2).onSurface, 0.15f, MaterialTheme.getColorScheme(composerImpl2).secondary, 3, 2, 28, 33, 0.0f, 0.0f, 1536, null), ComposableLambdaKt.rememberComposableLambda(-115441137, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt$ShortcutHelperButton$2
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001d  */
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
                                    ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperButton.<anonymous> (Surfaces.kt:251)");
                                }
                                Modifier modifierM126paddingVpY3zN4 = PaddingKt.m126paddingVpY3zN4(Modifier.Companion, f8, f7);
                                Alignment.Companion.getClass();
                                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                                Arrangement.INSTANCE.getClass();
                                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Center, vertical, composer2, 54);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierM126paddingVpY3zN4);
                                ComposeUiNode.Companion.getClass();
                                Function0 function03 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl4.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl4.startReusableNode();
                                if (composerImpl4.inserting) {
                                    composerImpl4.createNode(function03);
                                } else {
                                    composerImpl4.useNode();
                                }
                                Updater.m337setimpl(composer2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function2);
                                }
                                Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                                SurfacesKt.m2595ShortcutHelperButtonContent3IgeMak(iconSource5, j, str7, str6, composer2, 0);
                                composerImpl4.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2), composerImpl, (i3 & 14) | ((i3 >> 21) & 896) | ((i3 >> 3) & 7168) | ((i12 << 24) & 234881024), 736);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                f6 = f8;
                iconSource4 = iconSource5;
                str5 = str6;
                z3 = z2;
                roundedCornerShape4 = roundedCornerShape5;
                borderStroke3 = borderStroke2;
            }
            final String str8 = str3;
            final float f9 = f4;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        String str9 = str5;
                        int i16 = i2;
                        SurfacesKt.m2594ShortcutHelperButton01TuoB8(function0, j, j2, modifier, roundedCornerShape4, iconSource4, str8, f6, f9, z3, borderStroke3, str9, (Composer) obj, iUpdateChangedFlags, i16);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 1572864;
        i4 = 16;
        str3 = str;
        i5 = 128 & i2;
        if (i5 == 0) {
        }
        i6 = 256 & i2;
        if (i6 == 0) {
        }
        i7 = i2 & 512;
        if (i7 == 0) {
        }
        i9 = 1024 & i2;
        if (i9 == 0) {
        }
        i10 = 2048 & i2;
        if (i10 == 0) {
        }
        if ((i3 & 306783379) != 306783378) {
            composerImpl2.startDefaults();
            if ((i & 1) != 0) {
                if ((i2 & 16) == 0) {
                }
                if ((i2 & 32) == 0) {
                }
                if (i14 != 0) {
                }
                if (i5 == 0) {
                }
                if (i6 != 0) {
                }
                if (i8 == 0) {
                }
                if (i9 == 0) {
                }
                if (i10 == 0) {
                }
                borderStroke2 = borderStroke4;
                i12 = i11;
                f3 = f5;
                iconSource3 = iconSource2;
                z2 = z4;
                roundedCornerShape2 = roundedCornerShape3;
                composerImpl2.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                if (!z2) {
                }
                composerImpl2.startReplaceGroup(-646046205);
                objRememberedValue = composerImpl2.rememberedValue();
                Composer.Companion.getClass();
                final IconSource iconSource52 = iconSource3;
                if (objRememberedValue == Composer.Companion.Empty) {
                }
                composerImpl2.end(false);
                Modifier modifierSemantics2 = SemanticsModifierKt.semantics(modifier, false, (Function1) objRememberedValue);
                MaterialTheme.INSTANCE.getClass();
                final String str62 = str4;
                Dp.Companion companion42 = Dp.Companion;
                final float f72 = f4;
                final float f82 = f3;
                final String str72 = str3;
                RoundedCornerShape roundedCornerShape52 = roundedCornerShape2;
                composerImpl = composerImpl2;
                m2593ClickableShortcutSurface9FW6N_Y(function02, modifierSemantics2, z2, roundedCornerShape52, jColor, borderStroke2, new InteractionsConfig(MaterialTheme.getColorScheme(composerImpl2).onSurface, 0.11f, MaterialTheme.getColorScheme(composerImpl2).onSurface, 0.15f, MaterialTheme.getColorScheme(composerImpl2).secondary, 3, 2, 28, 33, 0.0f, 0.0f, 1536, null), ComposableLambdaKt.rememberComposableLambda(-115441137, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt$ShortcutHelperButton$2
                    /* JADX WARN: Removed duplicated region for block: B:8:0x001d  */
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
                                    ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelperButton.<anonymous> (Surfaces.kt:251)");
                                }
                                Modifier modifierM126paddingVpY3zN4 = PaddingKt.m126paddingVpY3zN4(Modifier.Companion, f82, f72);
                                Alignment.Companion.getClass();
                                BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                                Arrangement.INSTANCE.getClass();
                                RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Center, vertical, composer2, 54);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierM126paddingVpY3zN4);
                                ComposeUiNode.Companion.getClass();
                                Function0 function03 = ComposeUiNode.Companion.Constructor;
                                if (composerImpl4.applier == null) {
                                    ComposablesKt.invalidApplier();
                                    throw null;
                                }
                                composerImpl4.startReusableNode();
                                if (composerImpl4.inserting) {
                                    composerImpl4.createNode(function03);
                                } else {
                                    composerImpl4.useNode();
                                }
                                Updater.m337setimpl(composer2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function2);
                                }
                                Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
                                SurfacesKt.m2595ShortcutHelperButtonContent3IgeMak(iconSource52, j, str72, str62, composer2, 0);
                                composerImpl4.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2), composerImpl, (i3 & 14) | ((i3 >> 21) & 896) | ((i3 >> 3) & 7168) | ((i12 << 24) & 234881024), 736);
                if (ComposerKt.isTraceInProgress()) {
                }
                f6 = f82;
                iconSource4 = iconSource52;
                str5 = str62;
                z3 = z2;
                roundedCornerShape4 = roundedCornerShape52;
                borderStroke3 = borderStroke2;
            }
        }
        final String str82 = str3;
        final float f92 = f4;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* renamed from: ShortcutHelperButtonContent-3IgeMak, reason: not valid java name */
    public static final void m2595ShortcutHelperButtonContent3IgeMak(final IconSource iconSource, final long j, final String str, final String str2, Composer composer, final int i) {
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
                Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(Modifier.Companion, 20);
                Alignment.Companion.getClass();
                IconKt.m271Iconww6aTOc(imageVector, str2, SizeKt.wrapContentSize$default(modifierM140size3ABfNKs, Alignment.Companion.Center, 2), j, composerImpl2, ((i2 << 6) & 7168) | ((i2 >> 6) & 112) | 384, 0);
            }
            composerImpl2.end(false);
            composerImpl2.startReplaceGroup(149033159);
            if (iconSource.imageVector != null && str != null) {
                Dp.Companion companion2 = Dp.Companion;
                SpacerKt.Spacer(composerImpl2, SizeKt.m144width3ABfNKs(Modifier.Companion, 8));
            }
            composerImpl2.end(false);
            if (str != null) {
                long sp = TextUnitKt.getSp(14);
                MaterialTheme.INSTANCE.getClass();
                TextStyle textStyle = MaterialTheme.getTypography(composerImpl2).labelLarge;
                Modifier.Companion companion3 = Modifier.Companion;
                Alignment.Companion.getClass();
                Modifier modifierWrapContentSize$default = SizeKt.wrapContentSize$default(companion3, Alignment.Companion.Center, 2);
                TextOverflow.Companion.getClass();
                composerImpl = composerImpl2;
                TextKt.m317Text4IGK_g(str, modifierWrapContentSize$default, j, sp, null, null, null, 0L, null, null, 0L, TextOverflow.Ellipsis, false, 0, 0, null, textStyle, composerImpl, (14 & (i2 >> 6)) | 3120 | ((i2 << 3) & 896), 48, 63472);
            } else {
                composerImpl = composerImpl2;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(j, str, str2, i) { // from class: com.android.systemui.keyboard.shortcut.ui.composable.SurfacesKt$$ExternalSyntheticLambda3
                public final /* synthetic */ long f$1;
                public final /* synthetic */ String f$2;
                public final /* synthetic */ String f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    String str3 = this.f$2;
                    String str4 = this.f$3;
                    SurfacesKt.m2595ShortcutHelperButtonContent3IgeMak(this.f$0, this.f$1, str3, str4, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: access$surface-XO-JAsU, reason: not valid java name */
    public static final Modifier m2596access$surfaceXOJAsU(Modifier modifier, Shape shape, long j, BorderStroke borderStroke, float f) {
        Modifier modifierThen = f > 0.0f ? modifier.then(GraphicsLayerModifierKt.m479graphicsLayer_6ThJ44$default(Modifier.Companion, 0.0f, 0.0f, 0.0f, f, 0.0f, shape, false, 0, 518111)) : modifier;
        if (borderStroke != null) {
            modifierThen = modifierThen.then(BorderKt.m29borderziNgDLE(Modifier.Companion, borderStroke.width, borderStroke.brush, shape));
        }
        return BackgroundKt.m26backgroundbw27NRU(modifierThen, j, shape);
    }

    /* renamed from: access$surfaceColorAtElevation-CLU3JFs, reason: not valid java name */
    public static final long m2597access$surfaceColorAtElevationCLU3JFs(long j, float f, Composer composer) {
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
        boolean zBooleanValue = ((Boolean) composerImpl.consume(ColorSchemeKt.LocalTonalElevationEnabled)).booleanValue();
        long j2 = colorScheme.surface;
        Color.Companion companion = Color.Companion;
        if (ULong.m3446equalsimpl0(j, j2) && zBooleanValue) {
            j = ColorSchemeKt.m261surfaceColorAtElevation3ABfNKs(colorScheme, f);
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
