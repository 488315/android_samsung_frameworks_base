package com.samsung.sesl.compose.component;

import android.content.Context;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.SingleValueAnimationKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.PainterModifierKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.layout.ContentScale$Companion$FillBounds$1;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.component.tokens.SeslSwitchColorSchemeKeyTokens;
import com.samsung.sesl.compose.foundation.BasicSwitchKt;
import com.samsung.sesl.compose.foundation.theme.BasicColorSchemeKt;
import com.samsung.sesl.compose.phone.resources.DrawableResourcesKt;
import com.samsung.sesl.compose.phone.resources.EmptyPainter;
import com.samsung.sesl.compose.theme.SeslColorScheme;
import com.samsung.sesl.compose.theme.SeslTheme;
import com.samsung.sesl.compose.ui.interaction.HighlightedInteractionKt;
import com.samsung.sesl.compose.utils.ColorUtilKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public abstract class SwitchKt {
    public static final void SeslDefaultSwitchThumb(float f, SeslSwitchColors seslSwitchColors, InteractionSource interactionSource, Modifier modifier, boolean z, Composer composer, int i) {
        int i2;
        float f2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1282018482);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(f) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(seslSwitchColors) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(interactionSource) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(modifier) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(z) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i2 & 9363) == 9362 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslDefaultSwitchThumb (Switch.kt:244)");
            }
            MutableState mutableStateCollectIsHighlightedAsState = HighlightedInteractionKt.collectIsHighlightedAsState(interactionSource, composerImpl, (i2 >> 6) & 14);
            SeslSwitchDefaults.INSTANCE.getClass();
            float f3 = SeslSwitchDefaults.ThumbDiameter;
            if (((Boolean) mutableStateCollectIsHighlightedAsState.getValue()).booleanValue()) {
                Dp.Companion companion = Dp.Companion;
                f2 = 0.8f * f3;
            } else {
                f2 = f3;
            }
            State stateM8animateDpAsStateAjpBEmI = AnimateAsStateKt.m8animateDpAsStateAjpBEmI(f2, null, "", composerImpl, 384, 10);
            long j = z ? seslSwitchColors.uncheckedThumbColor : seslSwitchColors.disabledUncheckedThumbColor;
            long j2 = z ? seslSwitchColors.checkedThumbColor : seslSwitchColors.disabledCheckedThumbColor;
            Color.Companion companion2 = Color.Companion;
            State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(ColorUtilKt.m3359lerpIbeAmgk(j, j2, f), null, "", composerImpl, 384, 10);
            composerImpl = composerImpl;
            Modifier modifierM136requiredSize3ABfNKs = SizeKt.m136requiredSize3ABfNKs(modifier, f3);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM136requiredSize3ABfNKs);
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
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            BoxKt.Box(BackgroundKt.m26backgroundbw27NRU(PaddingKt.m125padding3ABfNKs(SizeKt.m136requiredSize3ABfNKs(Modifier.Companion, ((Dp) stateM8animateDpAsStateAjpBEmI.getValue()).value), SeslSwitchDefaults.ThumbPadding), ((Color) stateM7animateColorAsStateeuL9pac.getValue()).value, SeslSwitchDefaults.ThumbShape), composerImpl, 0);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new SwitchKt$$ExternalSyntheticLambda1(f, seslSwitchColors, interactionSource, modifier, z, i, 1);
        }
    }

    public static final void SeslDefaultSwitchTrack(float f, SeslSwitchColors seslSwitchColors, Modifier modifier, boolean z, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1784213384);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(f) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(seslSwitchColors) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(z) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslDefaultSwitchTrack (Switch.kt:211)");
            }
            long j = z ? seslSwitchColors.checkedTrackColor : seslSwitchColors.disabledCheckedTrackColor;
            long j2 = z ? seslSwitchColors.uncheckedTrackColor : seslSwitchColors.disabledUncheckedTrackColor;
            Color.Companion companion = Color.Companion;
            long jM3359lerpIbeAmgk = ColorUtilKt.m3359lerpIbeAmgk(j2, j, f);
            if (z) {
                j = jM3359lerpIbeAmgk;
            } else if (f < 0.5f) {
                j = j2;
            }
            State stateM7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(j, null, "", composerImpl, 384, 10);
            SeslSwitchDefaults.INSTANCE.getClass();
            BoxKt.Box(BackgroundKt.m26backgroundbw27NRU(SizeKt.m137requiredSizeVpY3zN4(modifier, SeslSwitchDefaults.TrackWidth, SeslSwitchDefaults.TrackHeight), ((Color) stateM7animateColorAsStateeuL9pac.getValue()).value, SeslSwitchDefaults.TrackShape), composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new SwitchKt$$ExternalSyntheticLambda0(f, seslSwitchColors, modifier, z, i, 1);
        }
    }

    public static final void SeslOpenThemeSwitchThumb(float f, SeslSwitchColors seslSwitchColors, InteractionSource interactionSource, Modifier modifier, boolean z, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1430789356);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(f) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(seslSwitchColors) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(interactionSource) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl2.changed(z) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i2 & 9363) == 9362 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslOpenThemeSwitchThumb (Switch.kt:384)");
            }
            SeslSwitchOpenThemeTokens seslSwitchOpenThemeTokensRememberSwitchOpenThemeTokens = rememberSwitchOpenThemeTokens((Context) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalContext), composerImpl2);
            Painter painter = seslSwitchOpenThemeTokensRememberSwitchOpenThemeTokens.thumb;
            Painter painter2 = z ? seslSwitchOpenThemeTokensRememberSwitchOpenThemeTokens.thumbOn : seslSwitchOpenThemeTokensRememberSwitchOpenThemeTokens.thumbDisabledOn;
            Painter painter3 = z ? seslSwitchOpenThemeTokensRememberSwitchOpenThemeTokens.thumbOff : seslSwitchOpenThemeTokensRememberSwitchOpenThemeTokens.thumbDisabledOff;
            if (Intrinsics.areEqual(painter, EmptyPainter.INSTANCE)) {
                composerImpl2.startReplaceGroup(-1148006888);
                composerImpl = composerImpl2;
                SeslDefaultSwitchThumb(f, seslSwitchColors, interactionSource, modifier, z, composerImpl, i2 & 65534);
                composerImpl.end(false);
            } else {
                composerImpl2.startReplaceGroup(-1148220075);
                BoxKt.Box(PainterModifierKt.paint$default(PainterModifierKt.paint$default(PainterModifierKt.paint$default(modifier, painter, null, null, 0.0f, null, 62), painter2, null, null, f, null, 46), painter3, null, null, 1 - f, null, 46), composerImpl2, 0);
                composerImpl2.end(false);
                composerImpl = composerImpl2;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new SwitchKt$$ExternalSyntheticLambda1(f, seslSwitchColors, interactionSource, modifier, z, i, 0);
        }
    }

    public static final void SeslOpenThemeSwitchTrack(float f, SeslSwitchColors seslSwitchColors, Modifier modifier, boolean z, Composer composer, int i) {
        int i2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1357576810);
        if ((i & 6) == 0) {
            i2 = (composerImpl2.changed(f) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl2.changed(seslSwitchColors) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl2.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl2.changed(z) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslOpenThemeSwitchTrack (Switch.kt:279)");
            }
            SeslSwitchOpenThemeTokens seslSwitchOpenThemeTokensRememberSwitchOpenThemeTokens = rememberSwitchOpenThemeTokens((Context) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalContext), composerImpl2);
            Painter painter = z ? seslSwitchOpenThemeTokensRememberSwitchOpenThemeTokens.trackOn : seslSwitchOpenThemeTokensRememberSwitchOpenThemeTokens.trackDisabledOn;
            Painter painter2 = z ? seslSwitchOpenThemeTokensRememberSwitchOpenThemeTokens.trackOff : seslSwitchOpenThemeTokensRememberSwitchOpenThemeTokens.trackDisabledOff;
            Painter painter3 = seslSwitchOpenThemeTokensRememberSwitchOpenThemeTokens.thumb;
            EmptyPainter emptyPainter = EmptyPainter.INSTANCE;
            if (Intrinsics.areEqual(painter, emptyPainter) || Intrinsics.areEqual(painter2, emptyPainter)) {
                composerImpl2.startReplaceGroup(-1448445194);
                composerImpl = composerImpl2;
                SeslDefaultSwitchTrack(f, seslSwitchColors, modifier, z, composerImpl, i2 & 8190);
                composerImpl.end(false);
            } else {
                composerImpl2.startReplaceGroup(-1448214275);
                Density density = (Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity);
                float fMo54toDpu2uoSUM = density.mo54toDpu2uoSUM(Size.m419getWidthimpl(painter3.mo563getIntrinsicSizeNHjbRc()));
                SeslSwitchDefaults.INSTANCE.getClass();
                Dp dpM837boximpl = Dp.m837boximpl((SeslSwitchDefaults.TrackWidth / SeslSwitchDefaults.ThumbWidth) * fMo54toDpu2uoSUM);
                Dp dpM837boximpl2 = Dp.m837boximpl(density.mo54toDpu2uoSUM(Size.m419getWidthimpl(painter.mo563getIntrinsicSizeNHjbRc())));
                if (dpM837boximpl.compareTo(dpM837boximpl2) < 0) {
                    dpM837boximpl = dpM837boximpl2;
                }
                Dp dpM837boximpl3 = Dp.m837boximpl(density.mo54toDpu2uoSUM(Size.m419getWidthimpl(painter2.mo563getIntrinsicSizeNHjbRc())));
                if (dpM837boximpl.compareTo(dpM837boximpl3) < 0) {
                    dpM837boximpl = dpM837boximpl3;
                }
                float f2 = dpM837boximpl.value;
                Dp dpM837boximpl4 = Dp.m837boximpl(SeslSwitchDefaults.TrackHeight);
                Dp dpM837boximpl5 = Dp.m837boximpl(density.mo54toDpu2uoSUM(Size.m417getHeightimpl(painter.mo563getIntrinsicSizeNHjbRc())));
                if (dpM837boximpl4.compareTo(dpM837boximpl5) < 0) {
                    dpM837boximpl4 = dpM837boximpl5;
                }
                Dp dpM837boximpl6 = Dp.m837boximpl(density.mo54toDpu2uoSUM(Size.m417getHeightimpl(painter2.mo563getIntrinsicSizeNHjbRc())));
                if (dpM837boximpl4.compareTo(dpM837boximpl6) < 0) {
                    dpM837boximpl4 = dpM837boximpl6;
                }
                Modifier modifierM137requiredSizeVpY3zN4 = SizeKt.m137requiredSizeVpY3zN4(PaddingKt.m125padding3ABfNKs(modifier, SeslSwitchDefaults.ThumbPadding), f2, dpM837boximpl4.value);
                ContentScale.Companion.getClass();
                ContentScale$Companion$FillBounds$1 contentScale$Companion$FillBounds$1 = ContentScale.Companion.FillBounds;
                BoxKt.Box(PainterModifierKt.paint$default(PainterModifierKt.paint$default(modifierM137requiredSizeVpY3zN4, painter, null, contentScale$Companion$FillBounds$1, f, null, 38), painter2, null, contentScale$Companion$FillBounds$1, 1 - f, null, 38), composerImpl2, 0);
                composerImpl2.end(false);
                composerImpl = composerImpl2;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new SwitchKt$$ExternalSyntheticLambda0(f, seslSwitchColors, modifier, z, i, 0);
        }
    }

    public static final void SeslSwitch(final boolean z, final Function1 function1, Modifier.Companion companion, boolean z2, SeslSwitchColors seslSwitchColors, MutableInteractionSource mutableInteractionSource, Composer composer, final int i, final int i2) {
        int i3;
        final SeslSwitchColors seslSwitchColors2;
        Modifier.Companion companion2;
        final MutableInteractionSource mutableInteractionSource2;
        final boolean z3;
        int i4;
        boolean z4;
        SeslSwitchColors seslSwitchColors3;
        final Modifier.Companion companion3;
        final boolean z5;
        final MutableInteractionSource mutableInteractionSource3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1960604926);
        if ((i & 6) == 0) {
            i3 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(function1) ? 32 : 16;
        }
        int i5 = i3 | 3456;
        if ((i & 24576) == 0) {
            if ((i2 & 16) == 0) {
                seslSwitchColors2 = seslSwitchColors;
                int i6 = composerImpl.changed(seslSwitchColors2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                i5 |= i6;
            } else {
                seslSwitchColors2 = seslSwitchColors;
            }
            i5 |= i6;
        } else {
            seslSwitchColors2 = seslSwitchColors;
        }
        int i7 = i5 | 196608;
        if ((74899 & i7) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            companion3 = companion;
            z5 = z2;
            mutableInteractionSource3 = mutableInteractionSource;
        } else {
            composerImpl.startDefaults();
            if ((i & 1) == 0 || composerImpl.getDefaultsInvalid()) {
                Modifier.Companion companion4 = Modifier.Companion;
                if ((i2 & 16) != 0) {
                    SeslSwitchDefaults.INSTANCE.getClass();
                    composerImpl.startReplaceGroup(-1848188967);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitchDefaults.colors (Switch.kt:523)");
                    }
                    SeslTheme.INSTANCE.getClass();
                    SeslColorScheme colorScheme = SeslTheme.getColorScheme(composerImpl);
                    composerImpl.startReplaceGroup(739932881);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitchDefaults.defaultSwitchColors (Switch.kt:565)");
                    }
                    SeslSwitchColors seslSwitchColors4 = colorScheme.defaultSwitchColorsCached;
                    if (seslSwitchColors4 == null) {
                        long color = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.TrackOnColor, composerImpl);
                        long color2 = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.TrackOffColor, composerImpl);
                        i4 = -57345;
                        long color3 = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.ThumbOnColor, composerImpl);
                        long color4 = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.ThumbOffColor, composerImpl);
                        seslSwitchColors3 = new SeslSwitchColors(color3, color4, ColorKt.Color(Color.m463getRedimpl(color3), Color.m462getGreenimpl(color3), Color.m460getBlueimpl(color3), 0.4f, Color.m461getColorSpaceimpl(color3)), ColorKt.Color(Color.m463getRedimpl(color4), Color.m462getGreenimpl(color4), Color.m460getBlueimpl(color4), 0.4f, Color.m461getColorSpaceimpl(color4)), color, color2, ColorKt.Color(Color.m463getRedimpl(color), Color.m462getGreenimpl(color), Color.m460getBlueimpl(color), 0.4f, Color.m461getColorSpaceimpl(color)), ColorKt.Color(Color.m463getRedimpl(color2), Color.m462getGreenimpl(color2), Color.m460getBlueimpl(color2), 0.4f, Color.m461getColorSpaceimpl(color2)), null);
                        colorScheme.defaultSwitchColorsCached = seslSwitchColors3;
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        z4 = false;
                        composerImpl.end(false);
                    } else {
                        i4 = -57345;
                        z4 = false;
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        composerImpl.end(false);
                        seslSwitchColors3 = seslSwitchColors4;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(z4);
                    i7 &= i4;
                    seslSwitchColors2 = seslSwitchColors3;
                }
                composerImpl.startReplaceGroup(345877461);
                Object objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                companion2 = companion4;
                mutableInteractionSource2 = (MutableInteractionSource) objRememberedValue;
                z3 = true;
            } else {
                composerImpl.skipToGroupEnd();
                if ((i2 & 16) != 0) {
                    i7 &= -57345;
                }
                companion2 = companion;
                z3 = z2;
                mutableInteractionSource2 = mutableInteractionSource;
            }
            composerImpl.endDefaults();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitch (Switch.kt:122)");
            }
            BasicSwitchKt.SeslBasicSwitch(z, function1, companion2, ComposableLambdaKt.rememberComposableLambda(766207923, new Function3() { // from class: com.samsung.sesl.compose.component.SwitchKt.SeslSwitch.4
                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    float fFloatValue = ((Number) obj).floatValue();
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= ((ComposerImpl) composer2).changed(fFloatValue) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitch.<anonymous> (Switch.kt:130)");
                            }
                            SeslSwitchDefault.INSTANCE.Thumb(fFloatValue, seslSwitchColors2, mutableInteractionSource2, null, z3, composer2, iIntValue & 14);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), ComposableLambdaKt.rememberComposableLambda(1029904948, new Function3() { // from class: com.samsung.sesl.compose.component.SwitchKt.SeslSwitch.5
                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                @Override // kotlin.jvm.functions.Function3
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    float fFloatValue = ((Number) obj).floatValue();
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    if ((iIntValue & 6) == 0) {
                        iIntValue |= ((ComposerImpl) composer2).changed(fFloatValue) ? 4 : 2;
                    }
                    if ((iIntValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitch.<anonymous> (Switch.kt:140)");
                            }
                            SeslSwitchDefault.INSTANCE.Track(fFloatValue, seslSwitchColors2, null, z3, composer2, iIntValue & 14);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), z3, mutableInteractionSource2, composerImpl, (i7 & 14) | 27648 | (i7 & 112) | (i7 & 896) | ((i7 << 6) & 458752) | ((i7 << 3) & 3670016));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion3 = companion2;
            z5 = z3;
            mutableInteractionSource3 = mutableInteractionSource2;
        }
        final SeslSwitchColors seslSwitchColors5 = seslSwitchColors2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.SwitchKt$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    MutableInteractionSource mutableInteractionSource4 = mutableInteractionSource3;
                    SwitchKt.SeslSwitch(z, function1, companion3, z5, seslSwitchColors5, mutableInteractionSource4, (Composer) obj, iUpdateChangedFlags, i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final SeslSwitchOpenThemeTokens rememberSwitchOpenThemeTokens(Context context, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(157505492);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.component.rememberSwitchOpenThemeTokens (Switch.kt:342)");
        }
        composerImpl.startReplaceGroup(-474900945);
        boolean zChanged = composerImpl.changed(context);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                SeslSwitchOpenThemeTokens seslSwitchOpenThemeTokens = new SeslSwitchOpenThemeTokens(DrawableResourcesKt.seslDrawablePainter(R.drawable.sesl_switch_track_on, context), DrawableResourcesKt.seslDrawablePainter(R.drawable.sesl_switch_track_disabled_on, context), DrawableResourcesKt.seslDrawablePainter(R.drawable.sesl_switch_track_off, context), DrawableResourcesKt.seslDrawablePainter(R.drawable.sesl_switch_track_disabled_off, context), DrawableResourcesKt.seslDrawablePainter(R.drawable.sesl_switch_thumb_on, context), DrawableResourcesKt.seslDrawablePainter(R.drawable.sesl_switch_thumb_disabled_on, context), DrawableResourcesKt.seslDrawablePainter(R.drawable.sesl_switch_thumb_off, context), DrawableResourcesKt.seslDrawablePainter(R.drawable.sesl_switch_thumb_disabled_off, context), DrawableResourcesKt.seslDrawablePainter(R.drawable.sesl_switchthumb, context));
                composerImpl.updateRememberedValue(seslSwitchOpenThemeTokens);
                objRememberedValue = seslSwitchOpenThemeTokens;
            }
        }
        SeslSwitchOpenThemeTokens seslSwitchOpenThemeTokens2 = (SeslSwitchOpenThemeTokens) objRememberedValue;
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return seslSwitchOpenThemeTokens2;
    }
}
