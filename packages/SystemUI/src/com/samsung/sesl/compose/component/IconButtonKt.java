package com.samsung.sesl.compose.component;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.HoverableKt;
import androidx.compose.foundation.interaction.HoverInteractionKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.shape.RoundedCornerShape;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.IconButtonColors;
import androidx.compose.material3.InteractiveComponentSizeKt;
import androidx.compose.material3.MinimumInteractiveModifier;
import androidx.compose.material3.TooltipState;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
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
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.unit.Dp;
import com.android.bouncer.ui.composable.SecPasswordBouncerKt$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.foundation.RecoilKt;
import com.samsung.sesl.compose.foundation.SeslRecoilPreset;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes4.dex */
public abstract class IconButtonKt {
    public static final void SeslIconButton(final Function0 function0, Modifier.Companion companion, boolean z, IconButtonColors iconButtonColors, final MutableInteractionSource mutableInteractionSource, final String str, final ComposableLambdaImpl composableLambdaImpl, Composer composer, final int i) {
        int i2;
        IconButtonColors iconButtonColors2;
        final boolean z2;
        int i3;
        final Modifier.Companion companion2;
        boolean z3;
        Modifier modifierHoverable;
        MutableInteractionSource mutableInteractionSource2;
        ComposerImpl composerImpl;
        final boolean z4;
        final IconButtonColors iconButtonColors3;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(517510659);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        int i4 = i2 | 432;
        if ((i & 3072) == 0) {
            i4 = i2 | 1456;
        }
        if ((i & 24576) == 0) {
            i4 |= composerImpl2.changed(mutableInteractionSource) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i4 |= composerImpl2.changed(str) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i4 |= composerImpl2.changedInstance(composableLambdaImpl) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((599187 & i4) == 599186 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            companion2 = companion;
            z4 = z;
            iconButtonColors3 = iconButtonColors;
            mutableInteractionSource2 = mutableInteractionSource;
            composerImpl = composerImpl2;
        } else {
            composerImpl2.startDefaults();
            if ((i & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                Modifier.Companion companion3 = Modifier.Companion;
                SeslIconButtonDefaults.INSTANCE.getClass();
                iconButtonColors2 = SeslIconButtonDefaults.iconButtonColors(composerImpl2);
                z2 = true;
                i3 = i4 & (-7169);
                companion2 = companion3;
            } else {
                composerImpl2.skipToGroupEnd();
                int i5 = i4 & (-7169);
                companion2 = companion;
                z2 = z;
                iconButtonColors2 = iconButtonColors;
                i3 = i5;
            }
            composerImpl2.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslIconButton (IconButton.kt:118)");
            }
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                CompositionScopedCoroutineScopeCanceller compositionScopedCoroutineScopeCanceller = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2));
                composerImpl2.updateRememberedValue(compositionScopedCoroutineScopeCanceller);
                objRememberedValue = compositionScopedCoroutineScopeCanceller;
            }
            final CoroutineScope coroutineScope = ((CompositionScopedCoroutineScopeCanceller) objRememberedValue).coroutineScope;
            final TooltipState tooltipStateRememberTooltipState = androidx.compose.material3.TooltipKt.rememberTooltipState(composerImpl2);
            composerImpl2.startReplaceGroup(-199432713);
            if (str != null) {
                Modifier.Companion companion4 = Modifier.Companion;
                composerImpl2.startReplaceGroup(-199429194);
                boolean zChangedInstance = composerImpl2.changedInstance(coroutineScope) | composerImpl2.changedInstance(tooltipStateRememberTooltipState);
                Object objRememberedValue2 = composerImpl2.rememberedValue();
                if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                    objRememberedValue2 = new Function0() { // from class: com.samsung.sesl.compose.component.IconButtonKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            BuildersKt.launch$default(coroutineScope, null, null, new IconButtonKt$SeslIconButton$tooltipModifier$1$1$1(tooltipStateRememberTooltipState, null), 3);
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl2.updateRememberedValue(objRememberedValue2);
                }
                z3 = false;
                composerImpl2.end(false);
                modifierHoverable = HoverableKt.hoverable(mutableInteractionSource, ClickableKt.m38combinedClickablecJG_KMw$default(companion4, z2, (Function0) objRememberedValue2, function0), z2);
            } else {
                z3 = false;
                modifierHoverable = Modifier.Companion;
            }
            composerImpl2.end(z3);
            final IconButtonColors iconButtonColors4 = iconButtonColors2;
            final Modifier modifier = modifierHoverable;
            boolean z5 = z2;
            mutableInteractionSource2 = mutableInteractionSource;
            final ComposableLambdaImpl composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(2095247880, new Function2() { // from class: com.samsung.sesl.compose.component.IconButtonKt$SeslIconButton$iconButton$1
                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
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
                                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslIconButton.<anonymous> (IconButton.kt:138)");
                            }
                            IconButtonKt.SeslIconButton(function0, companion2.then(modifier), z2, iconButtonColors4, mutableInteractionSource, composableLambdaImpl, composer2, 0, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl2);
            if (str != null) {
                composerImpl2.startReplaceGroup(-1886716662);
                int i6 = i3 >> 12;
                MutableState mutableStateCollectIsHoveredAsState = HoverInteractionKt.collectIsHoveredAsState(mutableInteractionSource2, composerImpl2, i6 & 14);
                Boolean bool = (Boolean) mutableStateCollectIsHoveredAsState.getValue();
                boolean zM = SecPasswordBouncerKt$$ExternalSyntheticOutline0.m(bool, composerImpl2, -199406081, mutableStateCollectIsHoveredAsState) | composerImpl2.changedInstance(tooltipStateRememberTooltipState);
                Object objRememberedValue3 = composerImpl2.rememberedValue();
                if (zM || objRememberedValue3 == composer$Companion$Empty$1) {
                    objRememberedValue3 = new IconButtonKt$SeslIconButton$5$1(tooltipStateRememberTooltipState, mutableStateCollectIsHoveredAsState, null);
                    composerImpl2.updateRememberedValue(objRememberedValue3);
                }
                composerImpl2.end(false);
                EffectsKt.LaunchedEffect(composerImpl2, bool, (Function2) objRememberedValue3);
                composerImpl = composerImpl2;
                TooltipKt.SeslTooltipBox(tooltipStateRememberTooltipState, str, companion2, null, null, ComposableLambdaKt.rememberComposableLambda(-1866370108, new Function2() { // from class: com.samsung.sesl.compose.component.IconButtonKt.SeslIconButton.6
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
                                    ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslIconButton.<anonymous> (IconButton.kt:163)");
                                }
                                composableLambdaImplRememberComposableLambda.invoke(composer2, 6);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, composerImpl2), composerImpl, (i6 & 112) | 1575936 | ((i3 << 3) & 896));
                composerImpl.end(false);
            } else {
                composerImpl = composerImpl2;
                composerImpl.startReplaceGroup(-1886322559);
                composableLambdaImplRememberComposableLambda.invoke((Object) composerImpl, (Object) 6);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            z4 = z5;
            iconButtonColors3 = iconButtonColors4;
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final MutableInteractionSource mutableInteractionSource3 = mutableInteractionSource2;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.IconButtonKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    String str2 = str;
                    ComposableLambdaImpl composableLambdaImpl2 = composableLambdaImpl;
                    IconButtonKt.SeslIconButton(function0, companion2, z4, iconButtonColors3, mutableInteractionSource3, str2, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:106:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SeslIconButton(final Function0 function0, Modifier modifier, boolean z, IconButtonColors iconButtonColors, MutableInteractionSource mutableInteractionSource, final Function2 function2, Composer composer, final int i, final int i2) {
        Modifier modifier2;
        int i3;
        boolean z2;
        int i4;
        IconButtonColors iconButtonColors2;
        int i5;
        MutableInteractionSource mutableInteractionSource2;
        int i6;
        MutableInteractionSource mutableInteractionSource3;
        Modifier modifier3;
        IconButtonColors iconButtonColors3;
        boolean z3;
        long j;
        int i7;
        int i8;
        long j2;
        final IconButtonColors iconButtonColors4;
        final MutableInteractionSource mutableInteractionSource4;
        final boolean z4;
        final Modifier modifier4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1816712562);
        int i9 = i | (composerImpl.changedInstance(function0) ? 4 : 2);
        int i10 = i2 & 2;
        if (i10 != 0) {
            i3 = i9 | 48;
            modifier2 = modifier;
        } else {
            modifier2 = modifier;
            i3 = i9 | (composerImpl.changed(modifier2) ? 32 : 16);
        }
        int i11 = i2 & 4;
        if (i11 != 0) {
            i4 = i3 | 384;
            z2 = z;
        } else {
            z2 = z;
            i4 = i3 | (composerImpl.changed(z2) ? 256 : 128);
        }
        if ((i2 & 8) == 0) {
            iconButtonColors2 = iconButtonColors;
            int i12 = composerImpl.changed(iconButtonColors2) ? 2048 : 1024;
            int i13 = i4 | i12;
            i5 = i2 & 16;
            if (i5 == 0) {
                i6 = i13 | 24576;
                mutableInteractionSource2 = mutableInteractionSource;
            } else {
                mutableInteractionSource2 = mutableInteractionSource;
                i6 = i13 | (composerImpl.changed(mutableInteractionSource2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192);
            }
            if ((i & 196608) == 0) {
                i6 |= composerImpl.changedInstance(function2) ? 131072 : 65536;
            }
            if ((74899 & i6) != 74898 && composerImpl.getSkipping()) {
                composerImpl.skipToGroupEnd();
                modifier4 = modifier2;
                z4 = z2;
                iconButtonColors4 = iconButtonColors2;
                mutableInteractionSource4 = mutableInteractionSource2;
            } else {
                composerImpl.startDefaults();
                if ((i & 1) == 0 && !composerImpl.getDefaultsInvalid()) {
                    composerImpl.skipToGroupEnd();
                    if ((i2 & 8) != 0) {
                        i6 &= -7169;
                    }
                } else {
                    if (i10 != 0) {
                        modifier2 = Modifier.Companion;
                    }
                    if (i11 != 0) {
                        z2 = true;
                    }
                    if ((i2 & 8) != 0) {
                        SeslIconButtonDefaults.INSTANCE.getClass();
                        i6 &= -7169;
                        iconButtonColors2 = SeslIconButtonDefaults.iconButtonColors(composerImpl);
                    }
                    if (i5 == 0) {
                        composerImpl.startReplaceGroup(-199333812);
                        Object objRememberedValue = composerImpl.rememberedValue();
                        Composer.Companion.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                            composerImpl.updateRememberedValue(objRememberedValue);
                        }
                        composerImpl.end(false);
                        Modifier modifier5 = modifier2;
                        mutableInteractionSource3 = (MutableInteractionSource) objRememberedValue;
                        modifier3 = modifier5;
                        iconButtonColors3 = iconButtonColors2;
                    }
                    z3 = z2;
                    composerImpl.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslIconButton (IconButton.kt:204)");
                    }
                    if (z3) {
                        j = iconButtonColors3.containerColor;
                    } else {
                        j = iconButtonColors3.disabledContainerColor;
                    }
                    if (z3) {
                        i7 = 8;
                        i8 = i6;
                        j2 = iconButtonColors3.contentColor;
                    } else {
                        i7 = 8;
                        i8 = i6;
                        j2 = iconButtonColors3.disabledContentColor;
                    }
                    StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                    int i14 = i7;
                    Dp.Companion companion = Dp.Companion;
                    Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(modifier3.then(MinimumInteractiveModifier.INSTANCE), 36);
                    Role.Companion.getClass();
                    long j3 = j;
                    Modifier modifierM34clickableO2vRcR0$default = ClickableKt.m34clickableO2vRcR0$default(modifierM140size3ABfNKs, mutableInteractionSource3, null, z3, null, Role.m715boximpl(0), function0, 8);
                    SeslRecoilPreset seslRecoilPreset = SeslRecoilPreset.IconButton;
                    RoundedCornerShape roundedCornerShape = RoundedCornerShapeKt.CircleShape;
                    Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(RecoilKt.m3349seslRecoil3f6hBDE(modifierM34clickableO2vRcR0$default, mutableInteractionSource3, seslRecoilPreset, roundedCornerShape, 0L, z3, PaddingKt.m120PaddingValues0680j_4(Dp.m838equalsimpl0(((Dp) composerImpl.consume(InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize)).value, (float) 48) ? 0 : 6), composerImpl, ((i8 >> 9) & 112) | 384 | (458752 & (i8 << 9)), 8), j3, roundedCornerShape);
                    Alignment.Companion.getClass();
                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM26backgroundbw27NRU);
                    ComposeUiNode.Companion.getClass();
                    Function0 function02 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl.applier != null) {
                        composerImpl.startReusableNode();
                        if (composerImpl.inserting) {
                            composerImpl.createNode(function02);
                        } else {
                            composerImpl.useNode();
                        }
                        Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function22);
                        }
                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                        CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(j2)), function2, composerImpl, i14 | ((i8 >> 12) & 112));
                        composerImpl.end(true);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        iconButtonColors4 = iconButtonColors3;
                        mutableInteractionSource4 = mutableInteractionSource3;
                        z4 = z3;
                        modifier4 = modifier3;
                    } else {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                }
                modifier3 = modifier2;
                iconButtonColors3 = iconButtonColors2;
                mutableInteractionSource3 = mutableInteractionSource2;
                z3 = z2;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                if (z3) {
                }
                if (z3) {
                }
                StaticProvidableCompositionLocal staticProvidableCompositionLocal2 = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                int i142 = i7;
                Dp.Companion companion2 = Dp.Companion;
                Modifier modifierM140size3ABfNKs2 = SizeKt.m140size3ABfNKs(modifier3.then(MinimumInteractiveModifier.INSTANCE), 36);
                Role.Companion.getClass();
                long j32 = j;
                Modifier modifierM34clickableO2vRcR0$default2 = ClickableKt.m34clickableO2vRcR0$default(modifierM140size3ABfNKs2, mutableInteractionSource3, null, z3, null, Role.m715boximpl(0), function0, 8);
                SeslRecoilPreset seslRecoilPreset2 = SeslRecoilPreset.IconButton;
                RoundedCornerShape roundedCornerShape2 = RoundedCornerShapeKt.CircleShape;
                Modifier modifierM26backgroundbw27NRU2 = BackgroundKt.m26backgroundbw27NRU(RecoilKt.m3349seslRecoil3f6hBDE(modifierM34clickableO2vRcR0$default2, mutableInteractionSource3, seslRecoilPreset2, roundedCornerShape2, 0L, z3, PaddingKt.m120PaddingValues0680j_4(Dp.m838equalsimpl0(((Dp) composerImpl.consume(InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize)).value, (float) 48) ? 0 : 6), composerImpl, ((i8 >> 9) & 112) | 384 | (458752 & (i8 << 9)), 8), j32, roundedCornerShape2);
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierM26backgroundbw27NRU2);
                ComposeUiNode.Companion.getClass();
                Function0 function022 = ComposeUiNode.Companion.Constructor;
                if (composerImpl.applier != null) {
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.IconButtonKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        Function2 function23 = function2;
                        IconButtonKt.SeslIconButton(function0, modifier4, z4, iconButtonColors4, mutableInteractionSource4, function23, (Composer) obj, iUpdateChangedFlags, i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        iconButtonColors2 = iconButtonColors;
        int i132 = i4 | i12;
        i5 = i2 & 16;
        if (i5 == 0) {
        }
        if ((i & 196608) == 0) {
        }
        if ((74899 & i6) != 74898) {
            composerImpl.startDefaults();
            if ((i & 1) == 0) {
                if (i10 != 0) {
                }
                if (i11 != 0) {
                }
                if ((i2 & 8) != 0) {
                }
                if (i5 == 0) {
                    modifier3 = modifier2;
                    iconButtonColors3 = iconButtonColors2;
                    mutableInteractionSource3 = mutableInteractionSource2;
                }
                z3 = z2;
                composerImpl.endDefaults();
                if (ComposerKt.isTraceInProgress()) {
                }
                if (z3) {
                }
                if (z3) {
                }
                StaticProvidableCompositionLocal staticProvidableCompositionLocal22 = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                int i1422 = i7;
                Dp.Companion companion22 = Dp.Companion;
                Modifier modifierM140size3ABfNKs22 = SizeKt.m140size3ABfNKs(modifier3.then(MinimumInteractiveModifier.INSTANCE), 36);
                Role.Companion.getClass();
                long j322 = j;
                Modifier modifierM34clickableO2vRcR0$default22 = ClickableKt.m34clickableO2vRcR0$default(modifierM140size3ABfNKs22, mutableInteractionSource3, null, z3, null, Role.m715boximpl(0), function0, 8);
                SeslRecoilPreset seslRecoilPreset22 = SeslRecoilPreset.IconButton;
                RoundedCornerShape roundedCornerShape22 = RoundedCornerShapeKt.CircleShape;
                Modifier modifierM26backgroundbw27NRU22 = BackgroundKt.m26backgroundbw27NRU(RecoilKt.m3349seslRecoil3f6hBDE(modifierM34clickableO2vRcR0$default22, mutableInteractionSource3, seslRecoilPreset22, roundedCornerShape22, 0L, z3, PaddingKt.m120PaddingValues0680j_4(Dp.m838equalsimpl0(((Dp) composerImpl.consume(InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize)).value, (float) 48) ? 0 : 6), composerImpl, ((i8 >> 9) & 112) | 384 | (458752 & (i8 << 9)), 8), j322, roundedCornerShape22);
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy22 = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
                int currentCompositeKeyHash22 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope22 = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier22 = ComposedModifierKt.materializeModifier(composerImpl, modifierM26backgroundbw27NRU22);
                ComposeUiNode.Companion.getClass();
                Function0 function0222 = ComposeUiNode.Companion.Constructor;
                if (composerImpl.applier != null) {
                }
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
