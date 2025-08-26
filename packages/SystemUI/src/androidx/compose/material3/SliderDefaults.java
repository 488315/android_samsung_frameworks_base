package androidx.compose.material3;

import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.HoverableKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.material3.tokens.SliderTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ScaleKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RectKt;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpSize;
import androidx.compose.ui.util.MathHelpersKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.ranges.ClosedFloatRange;

/* loaded from: classes.dex */
public final class SliderDefaults {
    public static final SliderDefaults INSTANCE = new SliderDefaults();
    public static final float TickSize;
    public static final float TrackStopIndicatorSize;
    public static final AndroidPath trackPath;

    static {
        SliderTokens.INSTANCE.getClass();
        float f = SliderTokens.StopIndicatorSize;
        TrackStopIndicatorSize = f;
        TickSize = f;
        trackPath = AndroidPath_androidKt.Path();
    }

    private SliderDefaults() {
    }

    public static SliderColors colors(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.SliderDefaults.colors (Slider.kt:1080)");
        }
        MaterialTheme.INSTANCE.getClass();
        SliderColors defaultSliderColors$material3_release = getDefaultSliderColors$material3_release(MaterialTheme.getColorScheme(composer));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return defaultSliderColors$material3_release;
    }

    /* renamed from: drawStopIndicator-x3O1jOs, reason: not valid java name */
    public static void m293drawStopIndicatorx3O1jOs(DrawScope drawScope, long j, float f, long j2) {
        DrawScope.m534drawCircleVaOC9Bg$default(drawScope, j2, drawScope.mo58toPx0680j_4(f) / 2.0f, j, 0.0f, null, 0, 120);
    }

    /* renamed from: drawTrackPath-zXTsYAs, reason: not valid java name */
    public static void m294drawTrackPathzXTsYAs(DrawScope drawScope, Orientation orientation, long j, long j2, long j3, float f, float f2) {
        RoundRect roundRect;
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f) & 4294967295L);
        CornerRadius.Companion companion = CornerRadius.Companion;
        long jFloatToRawIntBits2 = (Float.floatToRawIntBits(f2) << 32) | (Float.floatToRawIntBits(f2) & 4294967295L);
        if (orientation == Orientation.Vertical) {
            Rect rectM413Recttz77jQw = RectKt.m413Recttz77jQw(j, SizeKt.Size(Size.m419getWidthimpl(j2), Size.m417getHeightimpl(j2)));
            roundRect = new RoundRect(rectM413Recttz77jQw.left, rectM413Recttz77jQw.top, rectM413Recttz77jQw.right, rectM413Recttz77jQw.bottom, jFloatToRawIntBits, jFloatToRawIntBits, jFloatToRawIntBits2, jFloatToRawIntBits2, null);
        } else {
            Rect rectM413Recttz77jQw2 = RectKt.m413Recttz77jQw(j, SizeKt.Size(Size.m419getWidthimpl(j2), Size.m417getHeightimpl(j2)));
            roundRect = new RoundRect(rectM413Recttz77jQw2.left, rectM413Recttz77jQw2.top, rectM413Recttz77jQw2.right, rectM413Recttz77jQw2.bottom, jFloatToRawIntBits, jFloatToRawIntBits2, jFloatToRawIntBits2, jFloatToRawIntBits, null);
        }
        AndroidPath androidPath = trackPath;
        Path.addRoundRect$default(androidPath, roundRect);
        DrawScope.m539drawPathLG529CI$default(drawScope, androidPath, j3, 60);
        androidPath.internalPath.rewind();
    }

    public static SliderColors getDefaultSliderColors$material3_release(ColorScheme colorScheme) {
        SliderColors sliderColors = colorScheme.defaultSliderColorsCached;
        if (sliderColors != null) {
            return sliderColors;
        }
        SliderTokens.INSTANCE.getClass();
        long jFromToken = ColorSchemeKt.fromToken(colorScheme, SliderTokens.HandleColor);
        ColorSchemeKeyTokens colorSchemeKeyTokens = SliderTokens.ActiveTrackColor;
        long jFromToken2 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens);
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = SliderTokens.InactiveTrackColor;
        long jFromToken3 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
        long jFromToken4 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
        long jFromToken5 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens);
        long jFromToken6 = ColorSchemeKt.fromToken(colorScheme, SliderTokens.DisabledHandleColor);
        long jM466compositeOverOWjLjI = ColorKt.m466compositeOverOWjLjI(ColorKt.Color(Color.m463getRedimpl(jFromToken6), Color.m462getGreenimpl(jFromToken6), Color.m460getBlueimpl(jFromToken6), SliderTokens.DisabledHandleOpacity, Color.m461getColorSpaceimpl(jFromToken6)), colorScheme.surface);
        ColorSchemeKeyTokens colorSchemeKeyTokens3 = SliderTokens.DisabledActiveTrackColor;
        long jFromToken7 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
        float f = SliderTokens.DisabledActiveTrackOpacity;
        long jColor = ColorKt.Color(Color.m463getRedimpl(jFromToken7), Color.m462getGreenimpl(jFromToken7), Color.m460getBlueimpl(jFromToken7), f, Color.m461getColorSpaceimpl(jFromToken7));
        ColorSchemeKeyTokens colorSchemeKeyTokens4 = SliderTokens.DisabledInactiveTrackColor;
        long jFromToken8 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
        float f2 = SliderTokens.DisabledInactiveTrackOpacity;
        long jColor2 = ColorKt.Color(Color.m463getRedimpl(jFromToken8), Color.m462getGreenimpl(jFromToken8), Color.m460getBlueimpl(jFromToken8), f2, Color.m461getColorSpaceimpl(jFromToken8));
        long jFromToken9 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
        long jColor3 = ColorKt.Color(Color.m463getRedimpl(jFromToken9), Color.m462getGreenimpl(jFromToken9), Color.m460getBlueimpl(jFromToken9), f2, Color.m461getColorSpaceimpl(jFromToken9));
        long jFromToken10 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
        SliderColors sliderColors2 = new SliderColors(jFromToken, jFromToken2, jFromToken3, jFromToken4, jFromToken5, jM466compositeOverOWjLjI, jColor, jColor2, jColor3, ColorKt.Color(Color.m463getRedimpl(jFromToken10), Color.m462getGreenimpl(jFromToken10), Color.m460getBlueimpl(jFromToken10), f, Color.m461getColorSpaceimpl(jFromToken10)), null);
        colorScheme.defaultSliderColorsCached = sliderColors2;
        return sliderColors2;
    }

    /* JADX WARN: Removed duplicated region for block: B:132:0x01bd  */
    /* renamed from: DrawTrack-J0vdD74, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m295DrawTrackJ0vdD74(final SliderState sliderState, final float f, final Modifier modifier, final boolean z, final SliderColors sliderColors, final Function2 function2, final Function3 function3, final float f2, final float f3, final boolean z2, Composer composer, final int i, final int i2) {
        int i3;
        final float f4;
        float f5;
        int i4;
        long j;
        Modifier modifierM131height3ABfNKs;
        Modifier modifier2;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1066375183);
        if ((i & 6) == 0) {
            i3 = (composerImpl2.changedInstance(sliderState) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl2.changed(f) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl2.changed(modifier) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i3 |= composerImpl2.changed(z) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i3 |= composerImpl2.changed(sliderColors) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i3 |= composerImpl2.changedInstance(function2) ? 131072 : 65536;
        }
        if ((1572864 & i) == 0) {
            i3 |= composerImpl2.changedInstance(function3) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((12582912 & i) == 0) {
            f4 = f2;
            i3 |= composerImpl2.changed(f4) ? 8388608 : 4194304;
        } else {
            f4 = f2;
        }
        if ((100663296 & i) == 0) {
            f5 = f3;
            i3 |= composerImpl2.changed(f5) ? 67108864 : 33554432;
        } else {
            f5 = f3;
        }
        if ((i & 805306368) == 0) {
            i3 |= composerImpl2.changed(z2) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        }
        if ((i3 & 306783379) == 306783378 && (i2 & 1) == 0 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.SliderDefaults.DrawTrack (Slider.kt:1504)");
            }
            final long jM292trackColorWaAFU9c$material3_release = sliderColors.m292trackColorWaAFU9c$material3_release(z, false);
            final long jM292trackColorWaAFU9c$material3_release2 = sliderColors.m292trackColorWaAFU9c$material3_release(z, true);
            long j2 = z ? sliderColors.inactiveTickColor : sliderColors.disabledInactiveTickColor;
            if (z) {
                i4 = i3;
                j = sliderColors.activeTickColor;
            } else {
                i4 = i3;
                j = sliderColors.disabledActiveTickColor;
            }
            if (sliderState.orientation == Orientation.Vertical) {
                modifierM131height3ABfNKs = androidx.compose.foundation.layout.SizeKt.m144width3ABfNKs(modifier, SliderKt.TrackHeight).then(androidx.compose.foundation.layout.SizeKt.FillWholeMaxHeight);
                if (sliderState.reverseVerticalDirection) {
                    modifierM131height3ABfNKs = ScaleKt.scale(modifierM131height3ABfNKs, 1.0f, -1.0f);
                }
            } else {
                modifierM131height3ABfNKs = androidx.compose.foundation.layout.SizeKt.m131height3ABfNKs(androidx.compose.foundation.layout.SizeKt.fillMaxWidth(modifier, 1.0f), SliderKt.TrackHeight);
                if (sliderState.isRtl) {
                    modifierM131height3ABfNKs = ScaleKt.scale(modifierM131height3ABfNKs, -1.0f, 1.0f);
                }
            }
            boolean zChangedInstance = ((i4 & 29360128) == 8388608) | composerImpl2.changedInstance(sliderState) | composerImpl2.changed(jM292trackColorWaAFU9c$material3_release) | composerImpl2.changed(jM292trackColorWaAFU9c$material3_release2) | composerImpl2.changed(j2) | composerImpl2.changed(j) | ((i4 & 234881024) == 67108864) | ((i4 & 112) == 32) | ((i4 & 458752) == 131072) | ((i4 & 3670016) == 1048576) | ((i4 & 1879048192) == 536870912);
            Object objRememberedValue = composerImpl2.rememberedValue();
            if (!zChangedInstance) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    final long j3 = j2;
                    final long j4 = j;
                    modifier2 = modifierM131height3ABfNKs;
                    final float f6 = f5;
                    Function1 function1 = new Function1() { // from class: androidx.compose.material3.SliderDefaults$DrawTrack$3$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            float fMo58toPx0680j_4;
                            float fMo58toPx0680j_42;
                            float f7;
                            float f8;
                            float f9;
                            float f10;
                            boolean z3;
                            Orientation orientation;
                            float f11;
                            Function2 function22;
                            float f12;
                            DrawScope drawScope = (DrawScope) obj;
                            SliderDefaults sliderDefaults = SliderDefaults.INSTANCE;
                            SliderState sliderState2 = sliderState;
                            float[] fArr = sliderState2.tickFractions;
                            float coercedValueAsFraction = sliderState2.getCoercedValueAsFraction();
                            long j5 = jM292trackColorWaAFU9c$material3_release;
                            long j6 = jM292trackColorWaAFU9c$material3_release2;
                            long j7 = j3;
                            long j8 = j4;
                            float fMo55toDpu2uoSUM = drawScope.mo55toDpu2uoSUM(((SnapshotMutableIntStateImpl) sliderState.trackWidth$delegate).getIntValue());
                            float fMo55toDpu2uoSUM2 = drawScope.mo55toDpu2uoSUM(((SnapshotMutableIntStateImpl) sliderState.trackHeight$delegate).getIntValue());
                            float fMo55toDpu2uoSUM3 = drawScope.mo55toDpu2uoSUM(0);
                            float fMo55toDpu2uoSUM4 = drawScope.mo55toDpu2uoSUM(0);
                            float fMo55toDpu2uoSUM5 = drawScope.mo55toDpu2uoSUM(((SnapshotMutableIntStateImpl) sliderState.thumbWidth$delegate).getIntValue());
                            float fMo55toDpu2uoSUM6 = drawScope.mo55toDpu2uoSUM(((SnapshotMutableIntStateImpl) sliderState.thumbHeight$delegate).getIntValue());
                            float f13 = f4;
                            float f14 = f6;
                            float f15 = f;
                            Function2 function23 = function2;
                            Function3 function32 = function3;
                            boolean z4 = z2;
                            Orientation orientation2 = sliderState.orientation;
                            sliderDefaults.getClass();
                            boolean z5 = orientation2 == Orientation.Vertical;
                            long jMo547getSizeNHjbRc = drawScope.mo547getSizeNHjbRc();
                            float fM417getHeightimpl = z5 ? Size.m417getHeightimpl(jMo547getSizeNHjbRc) : Size.m419getWidthimpl(jMo547getSizeNHjbRc);
                            float f16 = fM417getHeightimpl - 0.0f;
                            float f17 = (f16 * coercedValueAsFraction) + 0.0f;
                            float f18 = (f16 * 0.0f) + 0.0f;
                            float fMo58toPx0680j_43 = drawScope.mo58toPx0680j_4(f15);
                            float fMo58toPx0680j_44 = drawScope.mo58toPx0680j_4(f14);
                            Dp.Companion companion = Dp.Companion;
                            if (Float.compare(f13, 0) <= 0) {
                                fMo58toPx0680j_4 = 0.0f;
                                fMo58toPx0680j_42 = 0.0f;
                            } else if (z5) {
                                float fMo58toPx0680j_45 = drawScope.mo58toPx0680j_4(fMo55toDpu2uoSUM4);
                                float f19 = 2;
                                float fMo58toPx0680j_46 = (fMo58toPx0680j_45 / f19) + drawScope.mo58toPx0680j_4(f13);
                                fMo58toPx0680j_4 = (drawScope.mo58toPx0680j_4(fMo55toDpu2uoSUM6) / f19) + drawScope.mo58toPx0680j_4(f13);
                                fMo58toPx0680j_42 = fMo58toPx0680j_46;
                            } else {
                                float fMo58toPx0680j_47 = drawScope.mo58toPx0680j_4(fMo55toDpu2uoSUM3);
                                float f20 = 2;
                                fMo58toPx0680j_42 = (fMo58toPx0680j_47 / f20) + drawScope.mo58toPx0680j_4(f13);
                                fMo58toPx0680j_4 = drawScope.mo58toPx0680j_4(f13) + (drawScope.mo58toPx0680j_4(fMo55toDpu2uoSUM5) / f20);
                            }
                            float f21 = fM417getHeightimpl - fMo58toPx0680j_4;
                            if (!z4) {
                                f21 -= fMo58toPx0680j_43;
                            }
                            if (f17 < f21) {
                                float f22 = f17 + fMo58toPx0680j_4;
                                float f23 = fM417getHeightimpl - f22;
                                long jOffset = z5 ? OffsetKt.Offset(0.0f, f22) : OffsetKt.Offset(f22, 0.0f);
                                f10 = fMo55toDpu2uoSUM;
                                orientation = orientation2;
                                f8 = fMo58toPx0680j_4;
                                f9 = fMo55toDpu2uoSUM2;
                                function22 = function23;
                                z3 = z4;
                                SliderDefaults.m294drawTrackPathzXTsYAs(drawScope, orientation, jOffset, z5 ? SizeKt.Size(drawScope.mo58toPx0680j_4(fMo55toDpu2uoSUM), f23) : SizeKt.Size(f23, drawScope.mo58toPx0680j_4(fMo55toDpu2uoSUM2)), j5, fMo58toPx0680j_44, fMo58toPx0680j_43);
                                f7 = fMo58toPx0680j_44;
                                f11 = fMo58toPx0680j_43;
                                long jOffset2 = z5 ? OffsetKt.Offset(Offset.m400getXimpl(drawScope.mo546getCenterF1C5BW0()), fM417getHeightimpl - f11) : OffsetKt.Offset(fM417getHeightimpl - f11, Offset.m401getYimpl(drawScope.mo546getCenterF1C5BW0()));
                                if (function22 != null) {
                                    function22.invoke(drawScope, Offset.m395boximpl(jOffset2));
                                }
                            } else {
                                f7 = fMo58toPx0680j_44;
                                f8 = fMo58toPx0680j_4;
                                f9 = fMo55toDpu2uoSUM2;
                                f10 = fMo55toDpu2uoSUM;
                                z3 = z4;
                                orientation = orientation2;
                                f11 = fMo58toPx0680j_43;
                                function22 = function23;
                            }
                            float f24 = f17 - f8;
                            float f25 = 0.0f;
                            float f26 = f24 - 0.0f;
                            if (f26 > (z3 ? 0.0f : f11)) {
                                long jOffset3 = OffsetKt.Offset(0.0f, 0.0f);
                                long jSize = z5 ? SizeKt.Size(drawScope.mo58toPx0680j_4(f10), f26) : SizeKt.Size(f26, drawScope.mo58toPx0680j_4(f9));
                                f12 = f24;
                                SliderDefaults.m294drawTrackPathzXTsYAs(drawScope, orientation, jOffset3, jSize, j6, f11, f7);
                                f25 = 0.0f;
                            } else {
                                f12 = f24;
                            }
                            float f27 = f25 + f11;
                            float f28 = fM417getHeightimpl - f11;
                            new ClosedFloatRange(f18 - fMo58toPx0680j_42, f18 + fMo58toPx0680j_42);
                            ClosedFloatRange closedFloatRange = new ClosedFloatRange(f12, f17 + f8);
                            int length = fArr.length;
                            int i5 = 0;
                            int i6 = 0;
                            while (i5 < length) {
                                float f29 = fArr[i5];
                                int i7 = i6 + 1;
                                if (function22 == null || i6 != fArr.length - 1) {
                                    boolean z6 = f29 > coercedValueAsFraction || f29 < 0.0f;
                                    float fLerp = MathHelpersKt.lerp(f27, f28, f29);
                                    if (!closedFloatRange.contains(Float.valueOf(fLerp))) {
                                        long jMo546getCenterF1C5BW0 = drawScope.mo546getCenterF1C5BW0();
                                        function32.invoke(drawScope, Offset.m395boximpl(z5 ? OffsetKt.Offset(Offset.m400getXimpl(jMo546getCenterF1C5BW0), fLerp) : OffsetKt.Offset(fLerp, Offset.m401getYimpl(jMo546getCenterF1C5BW0))), Color.m456boximpl(z6 ? j7 : j8));
                                    }
                                }
                                i5++;
                                i6 = i7;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl = composerImpl2;
                    composerImpl.updateRememberedValue(function1);
                    objRememberedValue = function1;
                } else {
                    modifier2 = modifierM131height3ABfNKs;
                    composerImpl = composerImpl2;
                }
                CanvasKt.Canvas(modifier2, (Function1) objRememberedValue, composerImpl, 0);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SliderDefaults$DrawTrack$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SliderDefaults sliderDefaults = this.$tmp1_rcvr;
                    SliderState sliderState2 = sliderState;
                    float f7 = f;
                    Modifier modifier3 = modifier;
                    boolean z3 = z;
                    SliderColors sliderColors2 = sliderColors;
                    Function2 function22 = function2;
                    Function3 function32 = function3;
                    float f8 = f2;
                    float f9 = f3;
                    boolean z4 = z2;
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i2);
                    SliderDefaults sliderDefaults2 = SliderDefaults.INSTANCE;
                    sliderDefaults.m295DrawTrackJ0vdD74(sliderState2, f7, modifier3, z3, sliderColors2, function22, function32, f8, f9, z4, (Composer) obj, iUpdateChangedFlags, iUpdateChangedFlags2);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:121:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x011b  */
    /* renamed from: Thumb-9LiSoMs, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m296Thumb9LiSoMs(final MutableInteractionSource mutableInteractionSource, Modifier modifier, SliderColors sliderColors, boolean z, long j, Composer composer, final int i, final int i2) {
        int i3;
        Modifier modifier2;
        SliderColors sliderColorsColors;
        int i4;
        boolean z2;
        int i5;
        long j2;
        final SliderDefaults sliderDefaults;
        Object objRememberedValue;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        SnapshotStateList snapshotStateList;
        boolean z3;
        Object objRememberedValue2;
        long jM845copyDwJknco$default;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-290277409);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(mutableInteractionSource) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i6 = i2 & 2;
        if (i6 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 32 : 16;
            }
            if ((i & 384) != 0) {
                if ((i2 & 4) == 0) {
                    sliderColorsColors = sliderColors;
                    int i7 = composerImpl.changed(sliderColorsColors) ? 256 : 128;
                    i3 |= i7;
                } else {
                    sliderColorsColors = sliderColors;
                }
                i3 |= i7;
            } else {
                sliderColorsColors = sliderColors;
            }
            i4 = i2 & 8;
            if (i4 == 0) {
                i3 |= 3072;
            } else {
                if ((i & 3072) == 0) {
                    z2 = z;
                    i3 |= composerImpl.changed(z2) ? 2048 : 1024;
                }
                i5 = i2 & 16;
                if (i5 != 0) {
                    i3 |= 24576;
                } else {
                    if ((i & 24576) == 0) {
                        j2 = j;
                        i3 |= composerImpl.changed(j2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    if ((i2 & 32) != 0) {
                        if ((i & 196608) == 0) {
                            sliderDefaults = this;
                            i3 |= composerImpl.changed(sliderDefaults) ? 131072 : 65536;
                        }
                        if ((i3 & 74899) == 74898 && composerImpl.getSkipping()) {
                            composerImpl.skipToGroupEnd();
                        } else {
                            composerImpl.startDefaults();
                            if ((i & 1) != 0 || composerImpl.getDefaultsInvalid()) {
                                if (i6 != 0) {
                                    modifier2 = Modifier.Companion;
                                }
                                if ((i2 & 4) != 0) {
                                    i3 &= -897;
                                    sliderColorsColors = colors(composerImpl);
                                }
                                if (i4 != 0) {
                                    z2 = true;
                                }
                                if (i5 != 0) {
                                    j2 = SliderKt.ThumbSize;
                                }
                            } else {
                                composerImpl.skipToGroupEnd();
                                if ((i2 & 4) != 0) {
                                    i3 &= -897;
                                }
                            }
                            composerImpl.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.SliderDefaults.Thumb (Slider.kt:1185)");
                            }
                            objRememberedValue = composerImpl.rememberedValue();
                            Composer.Companion.getClass();
                            composer$Companion$Empty$1 = Composer.Companion.Empty;
                            if (objRememberedValue == composer$Companion$Empty$1) {
                                objRememberedValue = new SnapshotStateList();
                                composerImpl.updateRememberedValue(objRememberedValue);
                            }
                            snapshotStateList = (SnapshotStateList) objRememberedValue;
                            z3 = (i3 & 14) != 4;
                            objRememberedValue2 = composerImpl.rememberedValue();
                            if (!z3 || objRememberedValue2 == composer$Companion$Empty$1) {
                                objRememberedValue2 = new SliderDefaults$Thumb$1$1(mutableInteractionSource, snapshotStateList, null);
                                composerImpl.updateRememberedValue(objRememberedValue2);
                            }
                            EffectsKt.LaunchedEffect(composerImpl, mutableInteractionSource, (Function2) objRememberedValue2);
                            if (snapshotStateList.isEmpty()) {
                                float fM847getWidthD9Ej5fM = DpSize.m847getWidthD9Ej5fM(j2) / 2;
                                Dp.Companion companion = Dp.Companion;
                                jM845copyDwJknco$default = DpSize.m845copyDwJknco$default(fM847getWidthD9Ej5fM, 0.0f, j2, 2);
                            } else {
                                jM845copyDwJknco$default = j2;
                            }
                            FillElement fillElement = androidx.compose.foundation.layout.SizeKt.FillWholeMaxWidth;
                            Modifier modifierHoverable = HoverableKt.hoverable(mutableInteractionSource, androidx.compose.foundation.layout.SizeKt.m141sizeVpY3zN4(modifier2, DpSize.m847getWidthD9Ej5fM(jM845copyDwJknco$default), DpSize.m846getHeightD9Ej5fM(jM845copyDwJknco$default)), true);
                            long j3 = !z2 ? sliderColorsColors.thumbColor : sliderColorsColors.disabledThumbColor;
                            SliderTokens.INSTANCE.getClass();
                            SpacerKt.Spacer(composerImpl, BackgroundKt.m26backgroundbw27NRU(modifierHoverable, j3, ShapesKt.getValue(SliderTokens.HandleShape, composerImpl)));
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                        final SliderColors sliderColors2 = sliderColorsColors;
                        final boolean z4 = z2;
                        final long j4 = j2;
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            final Modifier modifier3 = modifier2;
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SliderDefaults$Thumb$2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Number) obj2).intValue();
                                    this.$tmp2_rcvr.m296Thumb9LiSoMs(mutableInteractionSource, modifier3, sliderColors2, z4, j4, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i3 |= 196608;
                    sliderDefaults = this;
                    if ((i3 & 74899) == 74898) {
                        composerImpl.startDefaults();
                        if ((i & 1) != 0) {
                            if (i6 != 0) {
                            }
                            if ((i2 & 4) != 0) {
                            }
                            if (i4 != 0) {
                            }
                            if (i5 != 0) {
                            }
                            composerImpl.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            objRememberedValue = composerImpl.rememberedValue();
                            Composer.Companion.getClass();
                            composer$Companion$Empty$1 = Composer.Companion.Empty;
                            if (objRememberedValue == composer$Companion$Empty$1) {
                            }
                            snapshotStateList = (SnapshotStateList) objRememberedValue;
                            if ((i3 & 14) != 4) {
                            }
                            objRememberedValue2 = composerImpl.rememberedValue();
                            if (!z3) {
                                objRememberedValue2 = new SliderDefaults$Thumb$1$1(mutableInteractionSource, snapshotStateList, null);
                                composerImpl.updateRememberedValue(objRememberedValue2);
                                EffectsKt.LaunchedEffect(composerImpl, mutableInteractionSource, (Function2) objRememberedValue2);
                                if (snapshotStateList.isEmpty()) {
                                }
                                FillElement fillElement2 = androidx.compose.foundation.layout.SizeKt.FillWholeMaxWidth;
                                Modifier modifierHoverable2 = HoverableKt.hoverable(mutableInteractionSource, androidx.compose.foundation.layout.SizeKt.m141sizeVpY3zN4(modifier2, DpSize.m847getWidthD9Ej5fM(jM845copyDwJknco$default), DpSize.m846getHeightD9Ej5fM(jM845copyDwJknco$default)), true);
                                if (!z2) {
                                }
                                SliderTokens.INSTANCE.getClass();
                                SpacerKt.Spacer(composerImpl, BackgroundKt.m26backgroundbw27NRU(modifierHoverable2, j3, ShapesKt.getValue(SliderTokens.HandleShape, composerImpl)));
                                if (ComposerKt.isTraceInProgress()) {
                                }
                            }
                        }
                    }
                    final SliderColors sliderColors22 = sliderColorsColors;
                    final boolean z42 = z2;
                    final long j42 = j2;
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                j2 = j;
                if ((i2 & 32) != 0) {
                }
                sliderDefaults = this;
                if ((i3 & 74899) == 74898) {
                }
                final SliderColors sliderColors222 = sliderColorsColors;
                final boolean z422 = z2;
                final long j422 = j2;
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            z2 = z;
            i5 = i2 & 16;
            if (i5 != 0) {
            }
            j2 = j;
            if ((i2 & 32) != 0) {
            }
            sliderDefaults = this;
            if ((i3 & 74899) == 74898) {
            }
            final SliderColors sliderColors2222 = sliderColorsColors;
            final boolean z4222 = z2;
            final long j4222 = j2;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        if ((i & 384) != 0) {
        }
        i4 = i2 & 8;
        if (i4 == 0) {
        }
        z2 = z;
        i5 = i2 & 16;
        if (i5 != 0) {
        }
        j2 = j;
        if ((i2 & 32) != 0) {
        }
        sliderDefaults = this;
        if ((i3 & 74899) == 74898) {
        }
        final SliderColors sliderColors22222 = sliderColorsColors;
        final boolean z42222 = z2;
        final long j42222 = j2;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:137:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0116  */
    /* renamed from: Thumb-HwbPF3A, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m297ThumbHwbPF3A(final MutableInteractionSource mutableInteractionSource, final SliderState sliderState, Modifier modifier, SliderColors sliderColors, boolean z, long j, Composer composer, final int i, final int i2) {
        int i3;
        Modifier modifier2;
        SliderColors sliderColors2;
        int i4;
        boolean z2;
        int i5;
        long j2;
        boolean z3;
        Modifier modifier3;
        SliderColors sliderColorsColors;
        Object objRememberedValue;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        SnapshotStateList snapshotStateList;
        boolean z4;
        Object objRememberedValue2;
        long jM845copyDwJknco$default;
        final SliderColors sliderColors3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-889714565);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(mutableInteractionSource) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(sliderState) ? 32 : 16;
        }
        int i6 = i2 & 4;
        if (i6 != 0) {
            i3 |= 384;
        } else {
            if ((i & 384) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 256 : 128;
            }
            if ((i & 3072) != 0) {
                if ((i2 & 8) == 0) {
                    sliderColors2 = sliderColors;
                    int i7 = composerImpl.changed(sliderColors2) ? 2048 : 1024;
                    i3 |= i7;
                } else {
                    sliderColors2 = sliderColors;
                }
                i3 |= i7;
            } else {
                sliderColors2 = sliderColors;
            }
            i4 = i2 & 16;
            if (i4 == 0) {
                i3 |= 24576;
            } else {
                if ((i & 24576) == 0) {
                    z2 = z;
                    i3 |= composerImpl.changed(z2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                i5 = i2 & 32;
                if (i5 == 0) {
                    if ((196608 & i) == 0) {
                        j2 = j;
                        i3 |= composerImpl.changed(j2) ? 131072 : 65536;
                    }
                    if ((i2 & 64) == 0) {
                        i3 |= 1572864;
                        z3 = true;
                    } else {
                        z3 = true;
                        if ((i & 1572864) == 0) {
                            i3 |= composerImpl.changed(this) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        }
                    }
                    if ((i3 & 599187) == 599186 || !composerImpl.getSkipping()) {
                        composerImpl.startDefaults();
                        if ((i & 1) != 0 || composerImpl.getDefaultsInvalid()) {
                            modifier3 = i6 == 0 ? Modifier.Companion : modifier2;
                            if ((i2 & 8) == 0) {
                                sliderColorsColors = colors(composerImpl);
                                i3 &= -7169;
                            } else {
                                sliderColorsColors = sliderColors2;
                            }
                            if (i4 != 0) {
                                z2 = z3;
                            }
                            if (i5 != 0) {
                                j2 = SliderKt.ThumbSize;
                            }
                        } else {
                            composerImpl.skipToGroupEnd();
                            if ((i2 & 8) != 0) {
                                i3 &= -7169;
                            }
                            modifier3 = modifier2;
                            sliderColorsColors = sliderColors2;
                        }
                        composerImpl.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.SliderDefaults.Thumb (Slider.kt:1239)");
                        }
                        objRememberedValue = composerImpl.rememberedValue();
                        Composer.Companion.getClass();
                        composer$Companion$Empty$1 = Composer.Companion.Empty;
                        if (objRememberedValue == composer$Companion$Empty$1) {
                            objRememberedValue = new SnapshotStateList();
                            composerImpl.updateRememberedValue(objRememberedValue);
                        }
                        snapshotStateList = (SnapshotStateList) objRememberedValue;
                        z4 = (i3 & 14) != 4 ? z3 : false;
                        objRememberedValue2 = composerImpl.rememberedValue();
                        if (!z4 || objRememberedValue2 == composer$Companion$Empty$1) {
                            objRememberedValue2 = new SliderDefaults$Thumb$3$1(mutableInteractionSource, snapshotStateList, null);
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        EffectsKt.LaunchedEffect(composerImpl, mutableInteractionSource, (Function2) objRememberedValue2);
                        if (!snapshotStateList.isEmpty()) {
                            jM845copyDwJknco$default = j2;
                        } else if (sliderState.orientation == Orientation.Vertical) {
                            float fM846getHeightD9Ej5fM = DpSize.m846getHeightD9Ej5fM(j2) / 2;
                            Dp.Companion companion = Dp.Companion;
                            jM845copyDwJknco$default = DpSize.m845copyDwJknco$default(0.0f, fM846getHeightD9Ej5fM, j2, z3 ? 1 : 0);
                        } else {
                            float fM847getWidthD9Ej5fM = DpSize.m847getWidthD9Ej5fM(j2) / 2;
                            Dp.Companion companion2 = Dp.Companion;
                            jM845copyDwJknco$default = DpSize.m845copyDwJknco$default(fM847getWidthD9Ej5fM, 0.0f, j2, 2);
                        }
                        FillElement fillElement = androidx.compose.foundation.layout.SizeKt.FillWholeMaxWidth;
                        Modifier modifierHoverable = HoverableKt.hoverable(mutableInteractionSource, androidx.compose.foundation.layout.SizeKt.m141sizeVpY3zN4(modifier3, DpSize.m847getWidthD9Ej5fM(jM845copyDwJknco$default), DpSize.m846getHeightD9Ej5fM(jM845copyDwJknco$default)), true);
                        long j3 = !z2 ? sliderColorsColors.thumbColor : sliderColorsColors.disabledThumbColor;
                        SliderTokens.INSTANCE.getClass();
                        SpacerKt.Spacer(composerImpl, BackgroundKt.m26backgroundbw27NRU(modifierHoverable, j3, ShapesKt.getValue(SliderTokens.HandleShape, composerImpl)));
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        sliderColors3 = sliderColorsColors;
                    } else {
                        composerImpl.skipToGroupEnd();
                        modifier3 = modifier2;
                        sliderColors3 = sliderColors2;
                    }
                    final boolean z5 = z2;
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        final Modifier modifier4 = modifier3;
                        final long j4 = j2;
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SliderDefaults$Thumb$4
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                this.$tmp2_rcvr.m297ThumbHwbPF3A(mutableInteractionSource, sliderState, modifier4, sliderColors3, z5, j4, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i3 |= 196608;
                j2 = j;
                if ((i2 & 64) == 0) {
                }
                if ((i3 & 599187) == 599186) {
                    composerImpl.startDefaults();
                    if ((i & 1) != 0) {
                        if (i6 == 0) {
                        }
                        if ((i2 & 8) == 0) {
                        }
                        if (i4 != 0) {
                        }
                        if (i5 != 0) {
                        }
                        composerImpl.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        objRememberedValue = composerImpl.rememberedValue();
                        Composer.Companion.getClass();
                        composer$Companion$Empty$1 = Composer.Companion.Empty;
                        if (objRememberedValue == composer$Companion$Empty$1) {
                        }
                        snapshotStateList = (SnapshotStateList) objRememberedValue;
                        if ((i3 & 14) != 4) {
                        }
                        objRememberedValue2 = composerImpl.rememberedValue();
                        if (!z4) {
                            objRememberedValue2 = new SliderDefaults$Thumb$3$1(mutableInteractionSource, snapshotStateList, null);
                            composerImpl.updateRememberedValue(objRememberedValue2);
                            EffectsKt.LaunchedEffect(composerImpl, mutableInteractionSource, (Function2) objRememberedValue2);
                            if (!snapshotStateList.isEmpty()) {
                            }
                            FillElement fillElement2 = androidx.compose.foundation.layout.SizeKt.FillWholeMaxWidth;
                            Modifier modifierHoverable2 = HoverableKt.hoverable(mutableInteractionSource, androidx.compose.foundation.layout.SizeKt.m141sizeVpY3zN4(modifier3, DpSize.m847getWidthD9Ej5fM(jM845copyDwJknco$default), DpSize.m846getHeightD9Ej5fM(jM845copyDwJknco$default)), true);
                            if (!z2) {
                            }
                            SliderTokens.INSTANCE.getClass();
                            SpacerKt.Spacer(composerImpl, BackgroundKt.m26backgroundbw27NRU(modifierHoverable2, j3, ShapesKt.getValue(SliderTokens.HandleShape, composerImpl)));
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            sliderColors3 = sliderColorsColors;
                        }
                    }
                }
                final boolean z52 = z2;
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            z2 = z;
            i5 = i2 & 32;
            if (i5 == 0) {
            }
            j2 = j;
            if ((i2 & 64) == 0) {
            }
            if ((i3 & 599187) == 599186) {
            }
            final boolean z522 = z2;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        if ((i & 3072) != 0) {
        }
        i4 = i2 & 16;
        if (i4 == 0) {
        }
        z2 = z;
        i5 = i2 & 32;
        if (i5 == 0) {
        }
        j2 = j;
        if ((i2 & 64) == 0) {
        }
        if ((i3 & 599187) == 599186) {
        }
        final boolean z5222 = z2;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:161:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x010a  */
    /* renamed from: Track-4EFweAY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m298Track4EFweAY(final SliderState sliderState, Modifier modifier, boolean z, SliderColors sliderColors, Function2 function2, Function3 function3, float f, float f2, Composer composer, final int i, final int i2) {
        SliderState sliderState2;
        int i3;
        Modifier modifier2;
        int i4;
        final boolean z2;
        final SliderColors sliderColorsColors;
        Function2 function22;
        int i5;
        boolean z3;
        Function3 function32;
        int i6;
        final float f3;
        int i7;
        float f4;
        float f5;
        ComposerImpl composerImpl;
        final Modifier modifier3;
        final boolean z4;
        final SliderColors sliderColors2;
        final Function2 function23;
        final Function3 function33;
        final float f6;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(49984771);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
            sliderState2 = sliderState;
        } else {
            sliderState2 = sliderState;
            if ((i & 6) == 0) {
                i3 = (composerImpl2.changedInstance(sliderState2) ? 4 : 2) | i;
            } else {
                i3 = i;
            }
        }
        int i8 = i2 & 2;
        if (i8 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl2.changed(modifier2) ? 32 : 16;
            }
            i4 = 4 & i2;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    z2 = z;
                    i3 |= composerImpl2.changed(z2) ? 256 : 128;
                }
                if ((i & 3072) == 0) {
                    if ((i2 & 8) == 0) {
                        sliderColorsColors = sliderColors;
                        int i9 = composerImpl2.changed(sliderColorsColors) ? 2048 : 1024;
                        i3 |= i9;
                    } else {
                        sliderColorsColors = sliderColors;
                    }
                    i3 |= i9;
                } else {
                    sliderColorsColors = sliderColors;
                }
                if ((i & 24576) == 0) {
                    if ((i2 & 16) == 0) {
                        function22 = function2;
                        int i10 = composerImpl2.changedInstance(function22) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        i3 |= i10;
                    } else {
                        function22 = function2;
                    }
                    i3 |= i10;
                } else {
                    function22 = function2;
                }
                i5 = i2 & 32;
                if (i5 != 0) {
                    i3 |= 196608;
                    z3 = true;
                    function32 = function3;
                } else {
                    z3 = true;
                    function32 = function3;
                    if ((i & 196608) == 0) {
                        i3 |= composerImpl2.changedInstance(function32) ? 131072 : 65536;
                    }
                }
                i6 = i2 & 64;
                if (i6 != 0) {
                    i3 |= 1572864;
                    f3 = f;
                } else {
                    f3 = f;
                    if ((i & 1572864) == 0) {
                        i3 |= composerImpl2.changed(f3) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                    }
                }
                i7 = 128 & i2;
                if (i7 != 0) {
                    i3 |= 12582912;
                    f4 = f2;
                } else {
                    f4 = f2;
                    if ((i & 12582912) == 0) {
                        i3 |= composerImpl2.changed(f4) ? 8388608 : 4194304;
                    }
                }
                if ((i2 & 256) == 0) {
                    if ((i & 100663296) == 0) {
                        i3 |= composerImpl2.changed(this) ? 67108864 : 33554432;
                    }
                    if ((i3 & 38347923) == 38347922 || !composerImpl2.getSkipping()) {
                        composerImpl2.startDefaults();
                        if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                            if (i8 != 0) {
                                modifier2 = Modifier.Companion;
                            }
                            if (i4 != 0) {
                                z2 = z3;
                            }
                            if ((i2 & 8) != 0) {
                                i3 &= -7169;
                                sliderColorsColors = colors(composerImpl2);
                            }
                            if ((i2 & 16) != 0) {
                                boolean z5 = (((((i3 & 7168) ^ 3072) <= 2048 || !composerImpl2.changed(sliderColorsColors)) && (i3 & 3072) != 2048) ? false : z3) | ((i3 & 896) == 256 ? z3 : false);
                                Object objRememberedValue = composerImpl2.rememberedValue();
                                if (!z5) {
                                    Composer.Companion.getClass();
                                    if (objRememberedValue == Composer.Companion.Empty) {
                                        objRememberedValue = new Function2() { // from class: androidx.compose.material3.SliderDefaults$Track$4$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(2);
                                            }

                                            @Override // kotlin.jvm.functions.Function2
                                            public final Object invoke(Object obj, Object obj2) {
                                                DrawScope drawScope = (DrawScope) obj;
                                                long j = ((Offset) obj2).packedValue;
                                                SliderDefaults sliderDefaults = SliderDefaults.INSTANCE;
                                                long jM292trackColorWaAFU9c$material3_release = sliderColorsColors.m292trackColorWaAFU9c$material3_release(z2, true);
                                                sliderDefaults.getClass();
                                                SliderDefaults.m293drawStopIndicatorx3O1jOs(drawScope, j, SliderDefaults.TrackStopIndicatorSize, jM292trackColorWaAFU9c$material3_release);
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl2.updateRememberedValue(objRememberedValue);
                                    }
                                    function22 = (Function2) objRememberedValue;
                                    i3 = (-57345) & i3;
                                }
                            }
                            if (i5 != 0) {
                                function32 = new Function3() { // from class: androidx.compose.material3.SliderDefaults$Track$5
                                    @Override // kotlin.jvm.functions.Function3
                                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                                        long j = ((Offset) obj2).packedValue;
                                        long j2 = ((Color) obj3).value;
                                        SliderDefaults.INSTANCE.getClass();
                                        SliderDefaults.m293drawStopIndicatorx3O1jOs((DrawScope) obj, j, SliderDefaults.TickSize, j2);
                                        return Unit.INSTANCE;
                                    }
                                };
                            }
                            if (i6 != 0) {
                                f3 = SliderKt.ThumbTrackGapSize;
                            }
                            if (i7 == 0) {
                                f5 = SliderKt.TrackInsideCornerSize;
                            }
                            Function2 function24 = function22;
                            Function3 function34 = function32;
                            float f7 = f3;
                            boolean z6 = z2;
                            SliderColors sliderColors3 = sliderColorsColors;
                            Modifier modifier4 = modifier2;
                            composerImpl2.endDefaults();
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.SliderDefaults.Track (Slider.kt:1420)");
                            }
                            float f8 = SliderKt.TrackHeight / 2;
                            Dp.Companion companion = Dp.Companion;
                            int i11 = i3 << 3;
                            composerImpl = composerImpl2;
                            m295DrawTrackJ0vdD74(sliderState2, f8, modifier4, z6, sliderColors3, function24, function34, f7, f5, false, composerImpl, (i3 & 14) | 805306416 | (i11 & 896) | (i11 & 7168) | (57344 & i11) | (458752 & i11) | (3670016 & i11) | (29360128 & i11) | (i11 & 234881024), (i3 >> 24) & 14);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            modifier3 = modifier4;
                            z4 = z6;
                            sliderColors2 = sliderColors3;
                            function23 = function24;
                            function33 = function34;
                            f3 = f7;
                            f6 = f5;
                        } else {
                            composerImpl2.skipToGroupEnd();
                            if ((i2 & 8) != 0) {
                                i3 &= -7169;
                            }
                            if ((i2 & 16) != 0) {
                                i3 &= -57345;
                            }
                        }
                        f5 = f4;
                        Function2 function242 = function22;
                        Function3 function342 = function32;
                        float f72 = f3;
                        boolean z62 = z2;
                        SliderColors sliderColors32 = sliderColorsColors;
                        Modifier modifier42 = modifier2;
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        float f82 = SliderKt.TrackHeight / 2;
                        Dp.Companion companion2 = Dp.Companion;
                        int i112 = i3 << 3;
                        composerImpl = composerImpl2;
                        m295DrawTrackJ0vdD74(sliderState2, f82, modifier42, z62, sliderColors32, function242, function342, f72, f5, false, composerImpl, (i3 & 14) | 805306416 | (i112 & 896) | (i112 & 7168) | (57344 & i112) | (458752 & i112) | (3670016 & i112) | (29360128 & i112) | (i112 & 234881024), (i3 >> 24) & 14);
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        modifier3 = modifier42;
                        z4 = z62;
                        sliderColors2 = sliderColors32;
                        function23 = function242;
                        function33 = function342;
                        f3 = f72;
                        f6 = f5;
                    } else {
                        composerImpl2.skipToGroupEnd();
                        f6 = f4;
                        composerImpl = composerImpl2;
                        function33 = function32;
                        modifier3 = modifier2;
                        sliderColors2 = sliderColorsColors;
                        function23 = function22;
                        z4 = z2;
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SliderDefaults$Track$6
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                this.$tmp1_rcvr.m298Track4EFweAY(sliderState, modifier3, z4, sliderColors2, function23, function33, f3, f6, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i3 |= 100663296;
                if ((i3 & 38347923) == 38347922) {
                    composerImpl2.startDefaults();
                    if ((i & 1) != 0) {
                        if (i8 != 0) {
                        }
                        if (i4 != 0) {
                        }
                        if ((i2 & 8) != 0) {
                        }
                        if ((i2 & 16) != 0) {
                        }
                        if (i5 != 0) {
                        }
                        if (i6 != 0) {
                        }
                        if (i7 == 0) {
                            f5 = f4;
                        }
                        Function2 function2422 = function22;
                        Function3 function3422 = function32;
                        float f722 = f3;
                        boolean z622 = z2;
                        SliderColors sliderColors322 = sliderColorsColors;
                        Modifier modifier422 = modifier2;
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        float f822 = SliderKt.TrackHeight / 2;
                        Dp.Companion companion22 = Dp.Companion;
                        int i1122 = i3 << 3;
                        composerImpl = composerImpl2;
                        m295DrawTrackJ0vdD74(sliderState2, f822, modifier422, z622, sliderColors322, function2422, function3422, f722, f5, false, composerImpl, (i3 & 14) | 805306416 | (i1122 & 896) | (i1122 & 7168) | (57344 & i1122) | (458752 & i1122) | (3670016 & i1122) | (29360128 & i1122) | (i1122 & 234881024), (i3 >> 24) & 14);
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        modifier3 = modifier422;
                        z4 = z622;
                        sliderColors2 = sliderColors322;
                        function23 = function2422;
                        function33 = function3422;
                        f3 = f722;
                        f6 = f5;
                    }
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            z2 = z;
            if ((i & 3072) == 0) {
            }
            if ((i & 24576) == 0) {
            }
            i5 = i2 & 32;
            if (i5 != 0) {
            }
            i6 = i2 & 64;
            if (i6 != 0) {
            }
            i7 = 128 & i2;
            if (i7 != 0) {
            }
            if ((i2 & 256) == 0) {
            }
            if ((i3 & 38347923) == 38347922) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i4 = 4 & i2;
        if (i4 == 0) {
        }
        z2 = z;
        if ((i & 3072) == 0) {
        }
        if ((i & 24576) == 0) {
        }
        i5 = i2 & 32;
        if (i5 != 0) {
        }
        i6 = i2 & 64;
        if (i6 != 0) {
        }
        i7 = 128 & i2;
        if (i7 != 0) {
        }
        if ((i2 & 256) == 0) {
        }
        if ((i3 & 38347923) == 38347922) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01d5  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0245  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:177:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00fa  */
    /* renamed from: Track-mnvyFg4, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m299TrackmnvyFg4(final SliderState sliderState, final float f, Modifier modifier, boolean z, SliderColors sliderColors, Function2 function2, Function3 function3, float f2, float f3, Composer composer, final int i, final int i2) {
        SliderState sliderState2;
        int i3;
        float f4;
        final Modifier modifier2;
        int i4;
        final boolean z2;
        SliderColors sliderColors2;
        Function2 function22;
        int i5;
        Function3 function32;
        int i6;
        final float f5;
        int i7;
        int i8;
        int i9;
        final SliderColors sliderColorsColors;
        int i10;
        float f6;
        SliderColors sliderColors3;
        ComposerImpl composerImpl;
        final boolean z3;
        final SliderColors sliderColors4;
        final Function2 function23;
        final Function3 function33;
        final float f7;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1691224881);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
            sliderState2 = sliderState;
        } else {
            sliderState2 = sliderState;
            if ((i & 6) == 0) {
                i3 = (composerImpl2.changedInstance(sliderState2) ? 4 : 2) | i;
            } else {
                i3 = i;
            }
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
            f4 = f;
        } else {
            f4 = f;
            if ((i & 48) == 0) {
                i3 |= composerImpl2.changed(f4) ? 32 : 16;
            }
        }
        int i11 = i2 & 4;
        if (i11 != 0) {
            i3 |= 384;
        } else {
            if ((i & 384) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl2.changed(modifier2) ? 256 : 128;
            }
            i4 = i2 & 8;
            if (i4 == 0) {
                i3 |= 3072;
            } else {
                if ((i & 3072) == 0) {
                    z2 = z;
                    i3 |= composerImpl2.changed(z2) ? 2048 : 1024;
                }
                if ((i & 24576) == 0) {
                    if ((i2 & 16) == 0) {
                        sliderColors2 = sliderColors;
                        int i12 = composerImpl2.changed(sliderColors2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        i3 |= i12;
                    } else {
                        sliderColors2 = sliderColors;
                    }
                    i3 |= i12;
                } else {
                    sliderColors2 = sliderColors;
                }
                if ((196608 & i) == 0) {
                    if ((i2 & 32) == 0) {
                        function22 = function2;
                        int i13 = composerImpl2.changedInstance(function22) ? 131072 : 65536;
                        i3 |= i13;
                    } else {
                        function22 = function2;
                    }
                    i3 |= i13;
                } else {
                    function22 = function2;
                }
                i5 = i2 & 64;
                if (i5 != 0) {
                    i3 |= 1572864;
                } else {
                    if ((1572864 & i) == 0) {
                        function32 = function3;
                        i3 |= composerImpl2.changedInstance(function32) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                    }
                    i6 = i2 & 128;
                    if (i6 == 0) {
                        i3 |= 12582912;
                        f5 = f2;
                    } else {
                        f5 = f2;
                        if ((i & 12582912) == 0) {
                            i3 |= composerImpl2.changed(f5) ? 8388608 : 4194304;
                        }
                    }
                    i7 = i3;
                    i8 = i2 & 256;
                    if (i8 == 0) {
                        i7 |= 100663296;
                    } else {
                        if ((i & 100663296) == 0) {
                            i9 = i8;
                            i7 |= composerImpl2.changed(f3) ? 67108864 : 33554432;
                        }
                        if ((i2 & 512) == 0) {
                            if ((i & 805306368) == 0) {
                                i7 |= composerImpl2.changed(this) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                            }
                            if ((i7 & 306783379) == 306783378 || !composerImpl2.getSkipping()) {
                                composerImpl2.startDefaults();
                                if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                                    if (i11 != 0) {
                                        modifier2 = Modifier.Companion;
                                    }
                                    if (i4 != 0) {
                                        z2 = true;
                                    }
                                    if ((i2 & 16) == 0) {
                                        sliderColorsColors = colors(composerImpl2);
                                        i7 &= -57345;
                                    } else {
                                        sliderColorsColors = sliderColors2;
                                    }
                                    int i14 = i7;
                                    if ((i2 & 32) == 0) {
                                        i10 = 57344;
                                        boolean z4 = ((((i14 & 57344) ^ 24576) > 16384 && composerImpl2.changed(sliderColorsColors)) || (i14 & 24576) == 16384) | ((i14 & 7168) == 2048);
                                        Object objRememberedValue = composerImpl2.rememberedValue();
                                        if (!z4) {
                                            Composer.Companion.getClass();
                                            if (objRememberedValue == Composer.Companion.Empty) {
                                                objRememberedValue = new Function2() { // from class: androidx.compose.material3.SliderDefaults$Track$7$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(2);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function2
                                                    public final Object invoke(Object obj, Object obj2) {
                                                        DrawScope drawScope = (DrawScope) obj;
                                                        long j = ((Offset) obj2).packedValue;
                                                        SliderDefaults sliderDefaults = SliderDefaults.INSTANCE;
                                                        long jM292trackColorWaAFU9c$material3_release = sliderColorsColors.m292trackColorWaAFU9c$material3_release(z2, true);
                                                        sliderDefaults.getClass();
                                                        SliderDefaults.m293drawStopIndicatorx3O1jOs(drawScope, j, SliderDefaults.TrackStopIndicatorSize, jM292trackColorWaAFU9c$material3_release);
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl2.updateRememberedValue(objRememberedValue);
                                            }
                                            function22 = (Function2) objRememberedValue;
                                            i7 = i14 & (-458753);
                                        }
                                    } else {
                                        i10 = 57344;
                                        i7 = i14;
                                    }
                                    if (i5 != 0) {
                                        function32 = new Function3() { // from class: androidx.compose.material3.SliderDefaults$Track$8
                                            @Override // kotlin.jvm.functions.Function3
                                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                long j = ((Offset) obj2).packedValue;
                                                long j2 = ((Color) obj3).value;
                                                SliderDefaults.INSTANCE.getClass();
                                                SliderDefaults.m293drawStopIndicatorx3O1jOs((DrawScope) obj, j, SliderDefaults.TickSize, j2);
                                                return Unit.INSTANCE;
                                            }
                                        };
                                    }
                                    if (i6 != 0) {
                                        f5 = SliderKt.ThumbTrackGapSize;
                                    }
                                    f6 = i9 == 0 ? SliderKt.TrackInsideCornerSize : f3;
                                    sliderColors3 = sliderColorsColors;
                                } else {
                                    composerImpl2.skipToGroupEnd();
                                    if ((i2 & 16) != 0) {
                                        i7 &= -57345;
                                    }
                                    if ((i2 & 32) != 0) {
                                        i7 &= -458753;
                                    }
                                    f6 = f3;
                                    i10 = 57344;
                                    sliderColors3 = sliderColors2;
                                }
                                float f8 = f5;
                                Function3 function34 = function32;
                                int i15 = i7;
                                boolean z5 = z2;
                                Function2 function24 = function22;
                                composerImpl2.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.SliderDefaults.Track (Slider.kt:1476)");
                                }
                                composerImpl = composerImpl2;
                                Modifier modifier3 = modifier2;
                                m295DrawTrackJ0vdD74(sliderState2, f4, modifier3, z5, sliderColors3, function24, function34, f8, f6, true, composerImpl, 805306368 | (i15 & 14) | (i15 & 112) | (i15 & 896) | (i15 & 7168) | (i15 & i10) | (458752 & i15) | (3670016 & i15) | (29360128 & i15) | (234881024 & i15), (i15 >> 27) & 14);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                modifier2 = modifier3;
                                z3 = z5;
                                sliderColors4 = sliderColors3;
                                function23 = function24;
                                function33 = function34;
                                f5 = f8;
                                f7 = f6;
                            } else {
                                composerImpl2.skipToGroupEnd();
                                composerImpl = composerImpl2;
                                z3 = z2;
                                sliderColors4 = sliderColors2;
                                function23 = function22;
                                function33 = function32;
                                f7 = f3;
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SliderDefaults$Track$9
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        this.$tmp1_rcvr.m299TrackmnvyFg4(sliderState, f, modifier2, z3, sliderColors4, function23, function33, f5, f7, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i7 |= 805306368;
                        if ((i7 & 306783379) == 306783378) {
                            composerImpl2.startDefaults();
                            if ((i & 1) != 0) {
                                if (i11 != 0) {
                                }
                                if (i4 != 0) {
                                }
                                if ((i2 & 16) == 0) {
                                }
                                int i142 = i7;
                                if ((i2 & 32) == 0) {
                                }
                                if (i5 != 0) {
                                }
                                if (i6 != 0) {
                                }
                                if (i9 == 0) {
                                }
                                sliderColors3 = sliderColorsColors;
                                float f82 = f5;
                                Function3 function342 = function32;
                                int i152 = i7;
                                boolean z52 = z2;
                                Function2 function242 = function22;
                                composerImpl2.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl = composerImpl2;
                                Modifier modifier32 = modifier2;
                                m295DrawTrackJ0vdD74(sliderState2, f4, modifier32, z52, sliderColors3, function242, function342, f82, f6, true, composerImpl, 805306368 | (i152 & 14) | (i152 & 112) | (i152 & 896) | (i152 & 7168) | (i152 & i10) | (458752 & i152) | (3670016 & i152) | (29360128 & i152) | (234881024 & i152), (i152 >> 27) & 14);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                modifier2 = modifier32;
                                z3 = z52;
                                sliderColors4 = sliderColors3;
                                function23 = function242;
                                function33 = function342;
                                f5 = f82;
                                f7 = f6;
                            }
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    i9 = i8;
                    if ((i2 & 512) == 0) {
                    }
                    if ((i7 & 306783379) == 306783378) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                function32 = function3;
                i6 = i2 & 128;
                if (i6 == 0) {
                }
                i7 = i3;
                i8 = i2 & 256;
                if (i8 == 0) {
                }
                i9 = i8;
                if ((i2 & 512) == 0) {
                }
                if ((i7 & 306783379) == 306783378) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            z2 = z;
            if ((i & 24576) == 0) {
            }
            if ((196608 & i) == 0) {
            }
            i5 = i2 & 64;
            if (i5 != 0) {
            }
            function32 = function3;
            i6 = i2 & 128;
            if (i6 == 0) {
            }
            i7 = i3;
            i8 = i2 & 256;
            if (i8 == 0) {
            }
            i9 = i8;
            if ((i2 & 512) == 0) {
            }
            if ((i7 & 306783379) == 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 8;
        if (i4 == 0) {
        }
        z2 = z;
        if ((i & 24576) == 0) {
        }
        if ((196608 & i) == 0) {
        }
        i5 = i2 & 64;
        if (i5 != 0) {
        }
        function32 = function3;
        i6 = i2 & 128;
        if (i6 == 0) {
        }
        i7 = i3;
        i8 = i2 & 256;
        if (i8 == 0) {
        }
        i9 = i8;
        if ((i2 & 512) == 0) {
        }
        if ((i7 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
