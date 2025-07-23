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
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.sesl.compose.component.tokens.SeslSwitchColorSchemeKeyTokens;
import com.samsung.sesl.compose.foundation.BasicSwitchKt;
import com.samsung.sesl.compose.foundation.theme.BasicColorSchemeKt;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslDefaultSwitchThumb (Switch.kt:245)");
            }
            MutableState collectIsHighlightedAsState = HighlightedInteractionKt.collectIsHighlightedAsState(interactionSource, composerImpl, (i2 >> 6) & 14);
            SeslSwitchDefaults.INSTANCE.getClass();
            float f3 = SeslSwitchDefaults.ThumbDiameter;
            if (((Boolean) collectIsHighlightedAsState.getValue()).booleanValue()) {
                Dp.Companion companion = Dp.Companion;
                f2 = 0.8f * f3;
            } else {
                f2 = f3;
            }
            State m8animateDpAsStateAjpBEmI = AnimateAsStateKt.m8animateDpAsStateAjpBEmI(f2, null, "", composerImpl, 384, 10);
            long j = z ? seslSwitchColors.uncheckedThumbColor : seslSwitchColors.disabledUncheckedThumbColor;
            long j2 = z ? seslSwitchColors.checkedThumbColor : seslSwitchColors.disabledCheckedThumbColor;
            Color.Companion companion2 = Color.Companion;
            State m7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(ColorUtilKt.m3341lerpIbeAmgk(j, j2, f), null, "", composerImpl, 384, 10);
            composerImpl = composerImpl;
            Modifier m135requiredSize3ABfNKs = SizeKt.m135requiredSize3ABfNKs(modifier, f3);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.Center, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m135requiredSize3ABfNKs);
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
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            BoxKt.Box(BackgroundKt.m26backgroundbw27NRU(PaddingKt.m124padding3ABfNKs(SizeKt.m135requiredSize3ABfNKs(Modifier.Companion, ((Dp) m8animateDpAsStateAjpBEmI.getValue()).value), SeslSwitchDefaults.ThumbPadding), ((Color) m7animateColorAsStateeuL9pac.getValue()).value, SeslSwitchDefaults.ThumbShape), composerImpl, 0);
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new SwitchKt$$ExternalSyntheticLambda1(f, seslSwitchColors, interactionSource, modifier, z, i, 1);
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
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslDefaultSwitchTrack (Switch.kt:212)");
            }
            long j = z ? seslSwitchColors.checkedTrackColor : seslSwitchColors.disabledCheckedTrackColor;
            long j2 = z ? seslSwitchColors.uncheckedTrackColor : seslSwitchColors.disabledUncheckedTrackColor;
            Color.Companion companion = Color.Companion;
            long m3341lerpIbeAmgk = ColorUtilKt.m3341lerpIbeAmgk(j2, j, f);
            if (z) {
                j = m3341lerpIbeAmgk;
            } else if (f < 0.5f) {
                j = j2;
            }
            State m7animateColorAsStateeuL9pac = SingleValueAnimationKt.m7animateColorAsStateeuL9pac(j, null, "", composerImpl, 384, 10);
            SeslSwitchDefaults.INSTANCE.getClass();
            BoxKt.Box(BackgroundKt.m26backgroundbw27NRU(SizeKt.m136requiredSizeVpY3zN4(modifier, SeslSwitchDefaults.TrackWidth, SeslSwitchDefaults.TrackHeight), ((Color) m7animateColorAsStateeuL9pac.getValue()).value, SeslSwitchDefaults.TrackShape), composerImpl, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new SwitchKt$$ExternalSyntheticLambda0(f, seslSwitchColors, modifier, z, i, 1);
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
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslOpenThemeSwitchThumb (Switch.kt:385)");
            }
            SeslSwitchOpenThemeTokens rememberSwitchOpenThemeTokens = rememberSwitchOpenThemeTokens((Context) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalContext), composerImpl2);
            Painter painter = rememberSwitchOpenThemeTokens.thumb;
            Painter painter2 = z ? rememberSwitchOpenThemeTokens.thumbOn : rememberSwitchOpenThemeTokens.thumbDisabledOn;
            Painter painter3 = z ? rememberSwitchOpenThemeTokens.thumbOff : rememberSwitchOpenThemeTokens.thumbDisabledOff;
            if (Intrinsics.areEqual(painter, EmptyPainter.INSTANCE)) {
                composerImpl2.startReplaceGroup(-1148148744);
                composerImpl = composerImpl2;
                SeslDefaultSwitchThumb(f, seslSwitchColors, interactionSource, modifier, z, composerImpl, i2 & 65534);
                composerImpl.end(false);
            } else {
                composerImpl2.startReplaceGroup(-1148361931);
                BoxKt.Box(PainterModifierKt.paint$default(PainterModifierKt.paint$default(PainterModifierKt.paint$default(modifier, painter, null, null, 0.0f, null, 62), painter2, null, null, f, null, 46), painter3, null, null, 1 - f, null, 46), composerImpl2, 0);
                composerImpl2.end(false);
                composerImpl = composerImpl2;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new SwitchKt$$ExternalSyntheticLambda1(f, seslSwitchColors, interactionSource, modifier, z, i, 0);
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
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslOpenThemeSwitchTrack (Switch.kt:280)");
            }
            SeslSwitchOpenThemeTokens rememberSwitchOpenThemeTokens = rememberSwitchOpenThemeTokens((Context) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalContext), composerImpl2);
            Painter painter = z ? rememberSwitchOpenThemeTokens.trackOn : rememberSwitchOpenThemeTokens.trackDisabledOn;
            Painter painter2 = z ? rememberSwitchOpenThemeTokens.trackOff : rememberSwitchOpenThemeTokens.trackDisabledOff;
            Painter painter3 = rememberSwitchOpenThemeTokens.thumb;
            EmptyPainter emptyPainter = EmptyPainter.INSTANCE;
            if (Intrinsics.areEqual(painter, emptyPainter) || Intrinsics.areEqual(painter2, emptyPainter)) {
                composerImpl2.startReplaceGroup(-1448417418);
                composerImpl = composerImpl2;
                SeslDefaultSwitchTrack(f, seslSwitchColors, modifier, z, composerImpl, i2 & 8190);
                composerImpl.end(false);
            } else {
                composerImpl2.startReplaceGroup(-1448186499);
                Density density = (Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity);
                float mo53toDpu2uoSUM = density.mo53toDpu2uoSUM(Size.m417getWidthimpl(painter3.mo561getIntrinsicSizeNHjbRc()));
                SeslSwitchDefaults.INSTANCE.getClass();
                Dp m835boximpl = Dp.m835boximpl((SeslSwitchDefaults.TrackWidth / SeslSwitchDefaults.ThumbWidth) * mo53toDpu2uoSUM);
                Dp m835boximpl2 = Dp.m835boximpl(density.mo53toDpu2uoSUM(Size.m417getWidthimpl(painter.mo561getIntrinsicSizeNHjbRc())));
                if (m835boximpl.compareTo(m835boximpl2) < 0) {
                    m835boximpl = m835boximpl2;
                }
                Dp m835boximpl3 = Dp.m835boximpl(density.mo53toDpu2uoSUM(Size.m417getWidthimpl(painter2.mo561getIntrinsicSizeNHjbRc())));
                if (m835boximpl.compareTo(m835boximpl3) < 0) {
                    m835boximpl = m835boximpl3;
                }
                float f2 = m835boximpl.value;
                Dp m835boximpl4 = Dp.m835boximpl(SeslSwitchDefaults.TrackHeight);
                Dp m835boximpl5 = Dp.m835boximpl(density.mo53toDpu2uoSUM(Size.m415getHeightimpl(painter.mo561getIntrinsicSizeNHjbRc())));
                if (m835boximpl4.compareTo(m835boximpl5) < 0) {
                    m835boximpl4 = m835boximpl5;
                }
                Dp m835boximpl6 = Dp.m835boximpl(density.mo53toDpu2uoSUM(Size.m415getHeightimpl(painter2.mo561getIntrinsicSizeNHjbRc())));
                if (m835boximpl4.compareTo(m835boximpl6) < 0) {
                    m835boximpl4 = m835boximpl6;
                }
                Modifier m136requiredSizeVpY3zN4 = SizeKt.m136requiredSizeVpY3zN4(PaddingKt.m124padding3ABfNKs(modifier, SeslSwitchDefaults.ThumbPadding), f2, m835boximpl4.value);
                ContentScale.Companion.getClass();
                ContentScale$Companion$FillBounds$1 contentScale$Companion$FillBounds$1 = ContentScale.Companion.FillBounds;
                BoxKt.Box(PainterModifierKt.paint$default(PainterModifierKt.paint$default(m136requiredSizeVpY3zN4, painter, null, contentScale$Companion$FillBounds$1, f, null, 38), painter2, null, contentScale$Companion$FillBounds$1, 1 - f, null, 38), composerImpl2, 0);
                composerImpl2.end(false);
                composerImpl = composerImpl2;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new SwitchKt$$ExternalSyntheticLambda0(f, seslSwitchColors, modifier, z, i, 0);
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
        long Color;
        long Color2;
        long Color3;
        long Color4;
        final Modifier.Companion companion3;
        final boolean z5;
        final MutableInteractionSource mutableInteractionSource3;
        int i5;
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
        int i6 = i3 | 3456;
        if ((i & 24576) == 0) {
            if ((i2 & 16) == 0) {
                seslSwitchColors2 = seslSwitchColors;
                if (composerImpl.changed(seslSwitchColors2)) {
                    i5 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                    i6 |= i5;
                }
            } else {
                seslSwitchColors2 = seslSwitchColors;
            }
            i5 = 8192;
            i6 |= i5;
        } else {
            seslSwitchColors2 = seslSwitchColors;
        }
        int i7 = i6 | 196608;
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
                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitchDefaults.colors (Switch.kt:524)");
                    }
                    SeslTheme.INSTANCE.getClass();
                    SeslColorScheme colorScheme = SeslTheme.getColorScheme(composerImpl);
                    composerImpl.startReplaceGroup(739932881);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitchDefaults.defaultSwitchColors (Switch.kt:566)");
                    }
                    SeslSwitchColors seslSwitchColors4 = colorScheme.defaultSwitchColorsCached;
                    if (seslSwitchColors4 == null) {
                        long color = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.TrackOnColor, composerImpl);
                        long color2 = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.TrackOffColor, composerImpl);
                        i4 = -57345;
                        long color3 = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.ThumbOnColor, composerImpl);
                        long color4 = BasicColorSchemeKt.toColor(SeslSwitchColorSchemeKeyTokens.ThumbOffColor, composerImpl);
                        Color = ColorKt.Color(Color.m461getRedimpl(color3), Color.m460getGreenimpl(color3), Color.m458getBlueimpl(color3), 0.4f, Color.m459getColorSpaceimpl(color3));
                        Color2 = ColorKt.Color(Color.m461getRedimpl(color4), Color.m460getGreenimpl(color4), Color.m458getBlueimpl(color4), 0.4f, Color.m459getColorSpaceimpl(color4));
                        Color3 = ColorKt.Color(Color.m461getRedimpl(color), Color.m460getGreenimpl(color), Color.m458getBlueimpl(color), 0.4f, Color.m459getColorSpaceimpl(color));
                        Color4 = ColorKt.Color(Color.m461getRedimpl(color2), Color.m460getGreenimpl(color2), Color.m458getBlueimpl(color2), 0.4f, Color.m459getColorSpaceimpl(color2));
                        seslSwitchColors3 = new SeslSwitchColors(color3, color4, Color, Color2, color, color2, Color3, Color4, null);
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
                composerImpl.startReplaceGroup(345878357);
                Object rememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                if (rememberedValue == Composer.Companion.Empty) {
                    rememberedValue = InteractionSourceKt.MutableInteractionSource();
                    composerImpl.updateRememberedValue(rememberedValue);
                }
                composerImpl.end(false);
                companion2 = companion4;
                mutableInteractionSource2 = (MutableInteractionSource) rememberedValue;
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
                ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitch (Switch.kt:123)");
            }
            BasicSwitchKt.SeslBasicSwitch(z, function1, companion2, ComposableLambdaKt.rememberComposableLambda(766207923, new Function3() { // from class: com.samsung.sesl.compose.component.SwitchKt$SeslSwitch$4
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    float floatValue = ((Number) obj).floatValue();
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 6) == 0) {
                        intValue |= ((ComposerImpl) composer2).changed(floatValue) ? 4 : 2;
                    }
                    if ((intValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitch.<anonymous> (Switch.kt:131)");
                    }
                    SeslSwitchDefault.INSTANCE.Thumb(floatValue, SeslSwitchColors.this, mutableInteractionSource2, null, z3, composer2, intValue & 14);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), ComposableLambdaKt.rememberComposableLambda(1029904948, new Function3() { // from class: com.samsung.sesl.compose.component.SwitchKt$SeslSwitch$5
                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    float floatValue = ((Number) obj).floatValue();
                    Composer composer2 = (Composer) obj2;
                    int intValue = ((Number) obj3).intValue();
                    if ((intValue & 6) == 0) {
                        intValue |= ((ComposerImpl) composer2).changed(floatValue) ? 4 : 2;
                    }
                    if ((intValue & 19) == 18) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                            return Unit.INSTANCE;
                        }
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslSwitch.<anonymous> (Switch.kt:141)");
                    }
                    SeslSwitchDefault.INSTANCE.Track(floatValue, SeslSwitchColors.this, null, z3, composer2, intValue & 14);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.samsung.sesl.compose.component.SwitchKt$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    MutableInteractionSource mutableInteractionSource4 = mutableInteractionSource3;
                    SwitchKt.SeslSwitch(z, function1, companion3, z5, seslSwitchColors5, mutableInteractionSource4, (Composer) obj, updateChangedFlags, i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x002a, code lost:
    
        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final com.samsung.sesl.compose.component.SeslSwitchOpenThemeTokens rememberSwitchOpenThemeTokens(android.content.Context r12, androidx.compose.runtime.Composer r13) {
        /*
            androidx.compose.runtime.ComposerImpl r13 = (androidx.compose.runtime.ComposerImpl) r13
            r0 = 157505492(0x96357d4, float:2.7365428E-33)
            r13.startReplaceGroup(r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L13
            java.lang.String r0 = "com.samsung.sesl.compose.component.rememberSwitchOpenThemeTokens (Switch.kt:343)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L13:
            r0 = -474900220(0xffffffffe3b19904, float:-6.552199E21)
            r13.startReplaceGroup(r0)
            boolean r0 = r13.changed(r12)
            java.lang.Object r1 = r13.rememberedValue()
            if (r0 != 0) goto L2c
            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
            r0.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
            if (r1 != r0) goto L74
        L2c:
            com.samsung.sesl.compose.component.SeslSwitchOpenThemeTokens r2 = new com.samsung.sesl.compose.component.SeslSwitchOpenThemeTokens
            r0 = 2131235522(0x7f0812c2, float:1.808724E38)
            androidx.compose.ui.graphics.painter.Painter r3 = com.samsung.sesl.compose.phone.resources.DrawableResourcesKt.seslDrawablePainter(r0, r12)
            r0 = 2131235520(0x7f0812c0, float:1.8087236E38)
            androidx.compose.ui.graphics.painter.Painter r4 = com.samsung.sesl.compose.phone.resources.DrawableResourcesKt.seslDrawablePainter(r0, r12)
            r0 = 2131235521(0x7f0812c1, float:1.8087238E38)
            androidx.compose.ui.graphics.painter.Painter r5 = com.samsung.sesl.compose.phone.resources.DrawableResourcesKt.seslDrawablePainter(r0, r12)
            r0 = 2131235519(0x7f0812bf, float:1.8087234E38)
            androidx.compose.ui.graphics.painter.Painter r6 = com.samsung.sesl.compose.phone.resources.DrawableResourcesKt.seslDrawablePainter(r0, r12)
            r0 = 2131235512(0x7f0812b8, float:1.808722E38)
            androidx.compose.ui.graphics.painter.Painter r7 = com.samsung.sesl.compose.phone.resources.DrawableResourcesKt.seslDrawablePainter(r0, r12)
            r0 = 2131235510(0x7f0812b6, float:1.8087216E38)
            androidx.compose.ui.graphics.painter.Painter r8 = com.samsung.sesl.compose.phone.resources.DrawableResourcesKt.seslDrawablePainter(r0, r12)
            r0 = 2131235511(0x7f0812b7, float:1.8087218E38)
            androidx.compose.ui.graphics.painter.Painter r9 = com.samsung.sesl.compose.phone.resources.DrawableResourcesKt.seslDrawablePainter(r0, r12)
            r0 = 2131235509(0x7f0812b5, float:1.8087214E38)
            androidx.compose.ui.graphics.painter.Painter r10 = com.samsung.sesl.compose.phone.resources.DrawableResourcesKt.seslDrawablePainter(r0, r12)
            r0 = 2131235526(0x7f0812c6, float:1.8087248E38)
            androidx.compose.ui.graphics.painter.Painter r11 = com.samsung.sesl.compose.phone.resources.DrawableResourcesKt.seslDrawablePainter(r0, r12)
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11)
            r13.updateRememberedValue(r2)
            r1 = r2
        L74:
            com.samsung.sesl.compose.component.SeslSwitchOpenThemeTokens r1 = (com.samsung.sesl.compose.component.SeslSwitchOpenThemeTokens) r1
            r12 = 0
            r13.end(r12)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L83
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L83:
            r13.end(r12)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.sesl.compose.component.SwitchKt.rememberSwitchOpenThemeTokens(android.content.Context, androidx.compose.runtime.Composer):com.samsung.sesl.compose.component.SeslSwitchOpenThemeTokens");
    }
}
