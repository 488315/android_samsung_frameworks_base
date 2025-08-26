package androidx.compose.material3;

import android.view.KeyEvent;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.ProgressSemanticsKt;
import androidx.compose.foundation.gestures.DraggableKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.internal.AccessibilityUtilKt;
import androidx.compose.material3.tokens.SliderTokens;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.KeyEventType;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.key.KeyInputModifierKt;
import androidx.compose.ui.input.key.Key_androidKt;
import androidx.compose.ui.input.pointer.SuspendPointerInputElement;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.OnRemeasuredModifierKt;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.util.MathHelpersKt;
import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public abstract class SliderKt {
    public static final long ThumbSize;
    public static final float ThumbTrackGapSize;
    public static final float ThumbWidth;
    public static final float TrackHeight;
    public static final float TrackInsideCornerSize;
    public static final long VerticalThumbSize;

    static {
        SliderTokens.INSTANCE.getClass();
        TrackHeight = SliderTokens.InactiveTrackHeight;
        float f = SliderTokens.HandleWidth;
        ThumbWidth = f;
        float f2 = SliderTokens.HandleHeight;
        ThumbSize = DpKt.m840DpSizeYgX7TsA(f, f2);
        VerticalThumbSize = DpKt.m840DpSizeYgX7TsA(f2, f);
        ThumbTrackGapSize = SliderTokens.ActiveHandleLeadingSpace;
        Dp.Companion companion = Dp.Companion;
        TrackInsideCornerSize = 2;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0239  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0263  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:200:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void Slider(final float f, final Function1 function1, Modifier modifier, boolean z, Function0 function0, SliderColors sliderColors, MutableInteractionSource mutableInteractionSource, int i, Function3 function3, Function3 function32, ClosedFloatingPointRange closedFloatingPointRange, Composer composer, final int i2, final int i3, final int i4) {
        int i5;
        Modifier modifier2;
        int i6;
        final boolean z2;
        int i7;
        Function0 function02;
        SliderColors sliderColors2;
        int i8;
        final MutableInteractionSource mutableInteractionSource2;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        boolean z3;
        Function0 function03;
        final SliderColors sliderColorsColors;
        final int i17;
        Function3 function3RememberComposableLambda;
        Function3 function3RememberComposableLambda2;
        Modifier modifier3;
        ClosedFloatingPointRange closedFloatRange;
        boolean z4;
        final boolean z5;
        final Function3 function33;
        final Function3 function34;
        final ClosedFloatingPointRange closedFloatingPointRange2;
        final Modifier modifier4;
        final MutableInteractionSource mutableInteractionSource3;
        final Function0 function04;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1191170377);
        if ((i4 & 1) != 0) {
            i5 = i2 | 6;
        } else if ((i2 & 6) == 0) {
            i5 = (composerImpl.changed(f) ? 4 : 2) | i2;
        } else {
            i5 = i2;
        }
        if ((i4 & 2) != 0) {
            i5 |= 48;
        } else if ((i2 & 48) == 0) {
            i5 |= composerImpl.changedInstance(function1) ? 32 : 16;
        }
        int i18 = i4 & 4;
        if (i18 != 0) {
            i5 |= 384;
        } else {
            if ((i2 & 384) == 0) {
                modifier2 = modifier;
                i5 |= composerImpl.changed(modifier2) ? 256 : 128;
            }
            i6 = i4 & 8;
            if (i6 == 0) {
                i5 |= 3072;
            } else {
                if ((i2 & 3072) == 0) {
                    z2 = z;
                    i5 |= composerImpl.changed(z2) ? 2048 : 1024;
                }
                i7 = i4 & 16;
                if (i7 != 0) {
                    i5 |= 24576;
                } else {
                    if ((i2 & 24576) == 0) {
                        function02 = function0;
                        i5 |= composerImpl.changedInstance(function02) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    if ((196608 & i2) != 0) {
                        if ((i4 & 32) == 0) {
                            sliderColors2 = sliderColors;
                            int i19 = composerImpl.changed(sliderColors2) ? 131072 : 65536;
                            i5 |= i19;
                        } else {
                            sliderColors2 = sliderColors;
                        }
                        i5 |= i19;
                    } else {
                        sliderColors2 = sliderColors;
                    }
                    i8 = i4 & 64;
                    if (i8 == 0) {
                        i5 |= 1572864;
                        mutableInteractionSource2 = mutableInteractionSource;
                    } else {
                        mutableInteractionSource2 = mutableInteractionSource;
                        if ((i2 & 1572864) == 0) {
                            i5 |= composerImpl.changed(mutableInteractionSource2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        }
                    }
                    i9 = i4 & 128;
                    i10 = i5;
                    if (i9 == 0) {
                        i10 |= 12582912;
                    } else if ((i2 & 12582912) == 0) {
                        i10 |= composerImpl.changed(i) ? 8388608 : 4194304;
                    }
                    i11 = i4 & 256;
                    if (i11 == 0) {
                        i10 |= 100663296;
                    } else {
                        if ((i2 & 100663296) == 0) {
                            i12 = i11;
                            i10 |= composerImpl.changedInstance(function3) ? 67108864 : 33554432;
                        }
                        i13 = i4 & 512;
                        if (i13 == 0) {
                            if ((i2 & 805306368) == 0) {
                                i14 = i13;
                                i10 |= composerImpl.changedInstance(function32) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                            }
                            if ((i3 & 6) != 0) {
                                i15 = i3 | (((i4 & 1024) == 0 && composerImpl.changed(closedFloatingPointRange)) ? 4 : 2);
                            } else {
                                i15 = i3;
                            }
                            if ((i10 & 306783379) != 306783378 && (i15 & 3) == 2 && composerImpl.getSkipping()) {
                                composerImpl.skipToGroupEnd();
                                i17 = i;
                                function34 = function32;
                                modifier4 = modifier2;
                                z5 = z2;
                                function33 = function3;
                                mutableInteractionSource3 = mutableInteractionSource2;
                                function04 = function02;
                                closedFloatingPointRange2 = closedFloatingPointRange;
                            } else {
                                composerImpl.startDefaults();
                                i16 = i2 & 1;
                                Composer.Companion companion = Composer.Companion;
                                z3 = true;
                                if (i16 != 0 || composerImpl.getDefaultsInvalid()) {
                                    Modifier modifier5 = i18 == 0 ? Modifier.Companion : modifier2;
                                    if (i6 != 0) {
                                        z2 = true;
                                    }
                                    function03 = i7 == 0 ? null : function02;
                                    if ((i4 & 32) == 0) {
                                        SliderDefaults.INSTANCE.getClass();
                                        sliderColorsColors = SliderDefaults.colors(composerImpl);
                                        i10 &= -458753;
                                    } else {
                                        sliderColorsColors = sliderColors2;
                                    }
                                    if (i8 != 0) {
                                        Object objRememberedValue = composerImpl.rememberedValue();
                                        companion.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                                            composerImpl.updateRememberedValue(objRememberedValue);
                                        }
                                        mutableInteractionSource2 = (MutableInteractionSource) objRememberedValue;
                                    }
                                    i17 = i9 == 0 ? 0 : i;
                                    function3RememberComposableLambda = i12 == 0 ? ComposableLambdaKt.rememberComposableLambda(-1756326375, new Function3() { // from class: androidx.compose.material3.SliderKt.Slider.6
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                                            Composer composer2 = (Composer) obj2;
                                            ((Number) obj3).intValue();
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.material3.Slider.<anonymous> (Slider.kt:278)");
                                            }
                                            SliderDefaults.INSTANCE.m296Thumb9LiSoMs(mutableInteractionSource2, null, sliderColorsColors, z2, 0L, composer2, 196608, 18);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl) : function3;
                                    function3RememberComposableLambda2 = i14 == 0 ? ComposableLambdaKt.rememberComposableLambda(2083675534, new Function3() { // from class: androidx.compose.material3.SliderKt.Slider.7
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                                            SliderState sliderState = (SliderState) obj;
                                            Composer composer2 = (Composer) obj2;
                                            int iIntValue = ((Number) obj3).intValue();
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.material3.Slider.<anonymous> (Slider.kt:285)");
                                            }
                                            SliderDefaults.INSTANCE.m298Track4EFweAY(sliderState, null, z2, sliderColorsColors, null, null, 0.0f, 0.0f, composer2, (iIntValue & 14) | 100663296, IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl) : function32;
                                    if ((i4 & 1024) == 0) {
                                        modifier3 = modifier5;
                                        i15 &= -15;
                                        closedFloatRange = new ClosedFloatRange(0.0f, 1.0f);
                                    } else {
                                        modifier3 = modifier5;
                                        closedFloatRange = closedFloatingPointRange;
                                    }
                                    sliderColors2 = sliderColorsColors;
                                    modifier2 = modifier3;
                                } else {
                                    composerImpl.skipToGroupEnd();
                                    if ((i4 & 32) != 0) {
                                        i10 &= -458753;
                                    }
                                    if ((i4 & 1024) != 0) {
                                        i15 &= -15;
                                    }
                                    i17 = i;
                                    function3RememberComposableLambda = function3;
                                    closedFloatRange = closedFloatingPointRange;
                                    function03 = function02;
                                    function3RememberComposableLambda2 = function32;
                                }
                                composerImpl.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.Slider (Slider.kt:288)");
                                }
                                boolean z6 = (i10 & 29360128) != 8388608;
                                if ((((i15 & 14) ^ 6) > 4 || !composerImpl.changed(closedFloatRange)) && (i15 & 6) != 4) {
                                    z3 = false;
                                }
                                z4 = z6 | z3;
                                Object objRememberedValue2 = composerImpl.rememberedValue();
                                if (!z4) {
                                    companion.getClass();
                                    if (objRememberedValue2 == Composer.Companion.Empty) {
                                        objRememberedValue2 = new SliderState(f, i17, function03, closedFloatRange);
                                        composerImpl.updateRememberedValue(objRememberedValue2);
                                    }
                                    SliderState sliderState = (SliderState) objRememberedValue2;
                                    sliderState.onValueChangeFinished = function03;
                                    sliderState.onValueChange = function1;
                                    sliderState.setValue(f);
                                    int i20 = i10 >> 9;
                                    Slider(sliderState, modifier2, z2, null, mutableInteractionSource2, function3RememberComposableLambda, function3RememberComposableLambda2, composerImpl, ((i10 >> 3) & EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_SUCCESS) | ((i10 >> 6) & 57344) | (i20 & 458752) | (i20 & 3670016), 8);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    z5 = z2;
                                    function33 = function3RememberComposableLambda;
                                    function34 = function3RememberComposableLambda2;
                                    closedFloatingPointRange2 = closedFloatRange;
                                    modifier4 = modifier2;
                                    mutableInteractionSource3 = mutableInteractionSource2;
                                    function04 = function03;
                                }
                            }
                            final SliderColors sliderColors3 = sliderColors2;
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SliderKt.Slider.8
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        SliderKt.Slider(f, function1, modifier4, z5, function04, sliderColors3, mutableInteractionSource3, i17, function33, function34, closedFloatingPointRange2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i2 | 1), RecomposeScopeImplKt.updateChangedFlags(i3), i4);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i10 |= 805306368;
                        i14 = i13;
                        if ((i3 & 6) != 0) {
                        }
                        if ((i10 & 306783379) != 306783378) {
                            composerImpl.startDefaults();
                            i16 = i2 & 1;
                            Composer.Companion companion2 = Composer.Companion;
                            z3 = true;
                            if (i16 != 0) {
                                if (i18 == 0) {
                                }
                                if (i6 != 0) {
                                }
                                if (i7 == 0) {
                                }
                                if ((i4 & 32) == 0) {
                                }
                                if (i8 != 0) {
                                }
                                if (i9 == 0) {
                                }
                                if (i12 == 0) {
                                }
                                if (i14 == 0) {
                                }
                                if ((i4 & 1024) == 0) {
                                }
                                sliderColors2 = sliderColorsColors;
                                modifier2 = modifier3;
                                composerImpl.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                boolean z62 = (i10 & 29360128) != 8388608;
                                if (((i15 & 14) ^ 6) > 4) {
                                    z3 = false;
                                    z4 = z62 | z3;
                                    Object objRememberedValue22 = composerImpl.rememberedValue();
                                    if (!z4) {
                                    }
                                } else {
                                    z3 = false;
                                    z4 = z62 | z3;
                                    Object objRememberedValue222 = composerImpl.rememberedValue();
                                    if (!z4) {
                                    }
                                }
                            }
                        }
                        final SliderColors sliderColors32 = sliderColors2;
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    i12 = i11;
                    i13 = i4 & 512;
                    if (i13 == 0) {
                    }
                    i14 = i13;
                    if ((i3 & 6) != 0) {
                    }
                    if ((i10 & 306783379) != 306783378) {
                    }
                    final SliderColors sliderColors322 = sliderColors2;
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                function02 = function0;
                if ((196608 & i2) != 0) {
                }
                i8 = i4 & 64;
                if (i8 == 0) {
                }
                i9 = i4 & 128;
                i10 = i5;
                if (i9 == 0) {
                }
                i11 = i4 & 256;
                if (i11 == 0) {
                }
                i12 = i11;
                i13 = i4 & 512;
                if (i13 == 0) {
                }
                i14 = i13;
                if ((i3 & 6) != 0) {
                }
                if ((i10 & 306783379) != 306783378) {
                }
                final SliderColors sliderColors3222 = sliderColors2;
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            z2 = z;
            i7 = i4 & 16;
            if (i7 != 0) {
            }
            function02 = function0;
            if ((196608 & i2) != 0) {
            }
            i8 = i4 & 64;
            if (i8 == 0) {
            }
            i9 = i4 & 128;
            i10 = i5;
            if (i9 == 0) {
            }
            i11 = i4 & 256;
            if (i11 == 0) {
            }
            i12 = i11;
            i13 = i4 & 512;
            if (i13 == 0) {
            }
            i14 = i13;
            if ((i3 & 6) != 0) {
            }
            if ((i10 & 306783379) != 306783378) {
            }
            final SliderColors sliderColors32222 = sliderColors2;
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i6 = i4 & 8;
        if (i6 == 0) {
        }
        z2 = z;
        i7 = i4 & 16;
        if (i7 != 0) {
        }
        function02 = function0;
        if ((196608 & i2) != 0) {
        }
        i8 = i4 & 64;
        if (i8 == 0) {
        }
        i9 = i4 & 128;
        i10 = i5;
        if (i9 == 0) {
        }
        i11 = i4 & 256;
        if (i11 == 0) {
        }
        i12 = i11;
        i13 = i4 & 512;
        if (i13 == 0) {
        }
        i14 = i13;
        if ((i3 & 6) != 0) {
        }
        if ((i10 & 306783379) != 306783378) {
        }
        final SliderColors sliderColors322222 = sliderColors2;
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:109:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01c0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SliderImpl(final Modifier modifier, SliderState sliderState, final boolean z, final MutableInteractionSource mutableInteractionSource, final Function3 function3, final Function3 function32, Composer composer, final int i) {
        int i2;
        final SliderState sliderState2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1390990089);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(sliderState) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(z) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changed(mutableInteractionSource) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changedInstance(function3) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changedInstance(function32) ? 131072 : 65536;
        }
        int i3 = i2;
        if ((i3 & 74899) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            sliderState2 = sliderState;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.SliderImpl (Slider.kt:759)");
            }
            boolean z2 = composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection) == LayoutDirection.Rtl;
            sliderState.isRtl = z2;
            Orientation orientation = sliderState.orientation;
            boolean z3 = (orientation == Orientation.Horizontal && z2) || (orientation == Orientation.Vertical && sliderState.reverseVerticalDirection);
            Modifier.Companion companion = Modifier.Companion;
            Modifier modifierThen = z ? companion.then(new SuspendPointerInputElement(sliderState, mutableInteractionSource, null, new SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0(new SliderKt$sliderTapModifier$1(sliderState, null)), 4, null)) : companion;
            Orientation orientation2 = sliderState.orientation;
            boolean zBooleanValue = ((Boolean) ((SnapshotMutableStateImpl) sliderState.isDragging$delegate).getValue()).booleanValue();
            boolean zChangedInstance = composerImpl.changedInstance(sliderState);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion2 = Composer.Companion;
            if (!zChangedInstance) {
                companion2.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new SliderKt$SliderImpl$drag$1$1(sliderState, null);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                Modifier modifier2 = modifierThen;
                Modifier modifierDraggable$default = DraggableKt.draggable$default(companion, sliderState, orientation2, z, mutableInteractionSource, zBooleanValue, null, (Function3) objRememberedValue, z3, 32);
                final boolean z4 = z3;
                sliderState2 = sliderState;
                Orientation orientation3 = sliderState2.orientation;
                Orientation orientation4 = Orientation.Vertical;
                Modifier modifierWrapContentHeight$default = orientation3 == orientation4 ? SizeKt.wrapContentHeight$default(LayoutIdKt.layoutId(companion, SliderComponents.THUMB), 3) : SizeKt.wrapContentWidth$default(LayoutIdKt.layoutId(companion, SliderComponents.THUMB), null, 3);
                StaticProvidableCompositionLocal staticProvidableCompositionLocal = InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize;
                Modifier modifierThen2 = modifier.then(MinimumInteractiveModifier.INSTANCE);
                Orientation orientation5 = sliderState2.orientation;
                float f = ThumbWidth;
                float f2 = TrackHeight;
                Modifier modifierThen3 = SemanticsModifierKt.semantics(SizeKt.m138requiredSizeInqDBjuR0$default(modifierThen2, orientation5 == orientation4 ? f2 : f, orientation5 == orientation4 ? f : f2, 0.0f, 0.0f, 12), false, new Function1() { // from class: androidx.compose.material3.SliderKt$sliderSemantics$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                        if (!z) {
                            SemanticsPropertiesKt.disabled(semanticsPropertyReceiver);
                        }
                        final SliderState sliderState3 = sliderState2;
                        SemanticsPropertiesKt.setProgress$default(semanticsPropertyReceiver, new Function1() { // from class: androidx.compose.material3.SliderKt$sliderSemantics$1.1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj2) {
                                int i4;
                                float fCoerceIn = RangesKt___RangesKt.coerceIn(((Number) obj2).floatValue(), ((ClosedFloatRange) sliderState3.valueRange)._start, ((ClosedFloatRange) sliderState3.valueRange)._endInclusive);
                                int i5 = sliderState3.steps;
                                boolean z5 = true;
                                if (i5 > 0 && (i4 = i5 + 1) >= 0) {
                                    float fAbs = fCoerceIn;
                                    float f3 = fAbs;
                                    int i6 = 0;
                                    while (true) {
                                        float fLerp = MathHelpersKt.lerp(((ClosedFloatRange) sliderState3.valueRange)._start, ((ClosedFloatRange) sliderState3.valueRange)._endInclusive, i6 / (sliderState3.steps + 1));
                                        float f4 = fLerp - fCoerceIn;
                                        if (Math.abs(f4) <= fAbs) {
                                            fAbs = Math.abs(f4);
                                            f3 = fLerp;
                                        }
                                        if (i6 == i4) {
                                            break;
                                        }
                                        i6++;
                                    }
                                    fCoerceIn = f3;
                                }
                                if (fCoerceIn == sliderState3.getValue()) {
                                    z5 = false;
                                } else {
                                    if (fCoerceIn != sliderState3.getValue()) {
                                        SliderState sliderState4 = sliderState3;
                                        Function1 function1 = sliderState4.onValueChange;
                                        if (function1 != null) {
                                            function1.mo781invoke(Float.valueOf(fCoerceIn));
                                        } else {
                                            sliderState4.setValue(fCoerceIn);
                                        }
                                    }
                                    Function0 function0 = sliderState3.onValueChangeFinished;
                                    if (function0 != null) {
                                        function0.invoke();
                                    }
                                }
                                return Boolean.valueOf(z5);
                            }
                        });
                        return Unit.INSTANCE;
                    }
                }).then(AccessibilityUtilKt.IncreaseHorizontalSemanticsBounds);
                float value = sliderState2.getValue();
                ClosedFloatRange closedFloatRange = (ClosedFloatRange) sliderState2.valueRange;
                Modifier modifierFocusable = FocusableKt.focusable(mutableInteractionSource, ProgressSemanticsKt.progressSemantics(modifierThen3, value, new ClosedFloatRange(closedFloatRange._start, closedFloatRange._endInclusive), sliderState2.steps), z);
                final float value2 = sliderState2.getValue();
                final Function1 function1 = sliderState2.onValueChange;
                final Function0 function0 = sliderState2.onValueChangeFinished;
                final int i4 = sliderState2.steps;
                if (i4 < 0) {
                    throw new IllegalArgumentException("steps should be >= 0");
                }
                Modifier modifier3 = modifierWrapContentHeight$default;
                final ClosedFloatingPointRange closedFloatingPointRange = sliderState2.valueRange;
                Modifier modifierThen4 = KeyInputModifierKt.onKeyEvent(modifierFocusable, new Function1() { // from class: androidx.compose.material3.SliderKt$slideOnKeyEvents$2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj).nativeKeyEvent;
                        if (!z) {
                            return Boolean.FALSE;
                        }
                        if (function1 == null) {
                            return Boolean.FALSE;
                        }
                        int iM581getTypeZmokQxo = KeyEvent_androidKt.m581getTypeZmokQxo(keyEvent);
                        KeyEventType.Companion.getClass();
                        boolean z5 = false;
                        if (iM581getTypeZmokQxo == KeyEventType.KeyDown) {
                            ClosedFloatingPointRange closedFloatingPointRange2 = closedFloatingPointRange;
                            float fAbs = Math.abs(((ClosedFloatRange) closedFloatingPointRange2)._endInclusive - ((ClosedFloatRange) closedFloatingPointRange2)._start);
                            int i5 = i4;
                            float f3 = fAbs / (i5 > 0 ? i5 + 1 : 100);
                            int i6 = z4 ? -1 : 1;
                            long jKey = Key_androidKt.Key(keyEvent.getKeyCode());
                            Key.Companion.getClass();
                            if (Key.m578equalsimpl0(jKey, Key.DirectionUp)) {
                                function1.mo781invoke(RangesKt___RangesKt.coerceIn(Float.valueOf((i6 * f3) + value2), closedFloatingPointRange));
                            } else if (Key.m578equalsimpl0(jKey, Key.DirectionDown)) {
                                function1.mo781invoke(RangesKt___RangesKt.coerceIn(Float.valueOf(value2 - (i6 * f3)), closedFloatingPointRange));
                            } else if (Key.m578equalsimpl0(jKey, Key.DirectionRight)) {
                                function1.mo781invoke(RangesKt___RangesKt.coerceIn(Float.valueOf((i6 * f3) + value2), closedFloatingPointRange));
                            } else if (Key.m578equalsimpl0(jKey, Key.DirectionLeft)) {
                                function1.mo781invoke(RangesKt___RangesKt.coerceIn(Float.valueOf(value2 - (i6 * f3)), closedFloatingPointRange));
                            } else if (Key.m578equalsimpl0(jKey, Key.MoveHome)) {
                                function1.mo781invoke(Float.valueOf(((ClosedFloatRange) closedFloatingPointRange)._start));
                            } else if (Key.m578equalsimpl0(jKey, Key.MoveEnd)) {
                                function1.mo781invoke(Float.valueOf(((ClosedFloatRange) closedFloatingPointRange)._endInclusive));
                            } else if (Key.m578equalsimpl0(jKey, Key.PageUp)) {
                                function1.mo781invoke(RangesKt___RangesKt.coerceIn(Float.valueOf(value2 - (RangesKt___RangesKt.coerceIn(r1 / 10, 1, 10) * f3)), closedFloatingPointRange));
                            } else if (Key.m578equalsimpl0(jKey, Key.PageDown)) {
                                function1.mo781invoke(RangesKt___RangesKt.coerceIn(Float.valueOf((RangesKt___RangesKt.coerceIn(r1 / 10, 1, 10) * f3) + value2), closedFloatingPointRange));
                            }
                            z5 = true;
                        } else if (iM581getTypeZmokQxo == KeyEventType.KeyUp) {
                            long jKey2 = Key_androidKt.Key(keyEvent.getKeyCode());
                            Key.Companion.getClass();
                            if (Key.m578equalsimpl0(jKey2, Key.DirectionUp) ? true : Key.m578equalsimpl0(jKey2, Key.DirectionDown) ? true : Key.m578equalsimpl0(jKey2, Key.DirectionRight) ? true : Key.m578equalsimpl0(jKey2, Key.DirectionLeft) ? true : Key.m578equalsimpl0(jKey2, Key.MoveHome) ? true : Key.m578equalsimpl0(jKey2, Key.MoveEnd) ? true : Key.m578equalsimpl0(jKey2, Key.PageUp) ? true : Key.m578equalsimpl0(jKey2, Key.PageDown)) {
                                Function0 function02 = function0;
                                if (function02 != null) {
                                    function02.invoke();
                                }
                                z5 = true;
                            }
                        }
                        return Boolean.valueOf(z5);
                    }
                }).then(modifier2).then(modifierDraggable$default);
                boolean zChangedInstance2 = composerImpl.changedInstance(sliderState2);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2) {
                    companion2.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new MeasurePolicy() { // from class: androidx.compose.material3.SliderKt$SliderImpl$2$1
                            @Override // androidx.compose.ui.layout.MeasurePolicy
                            /* renamed from: measure-3p2s80s */
                            public final MeasureResult mo3measure3p2s80s(MeasureScope measureScope, List list, long j) {
                                int iMax;
                                int iMax2;
                                int i5;
                                int i6;
                                int iRoundToInt;
                                int size = list.size();
                                for (int i7 = 0; i7 < size; i7++) {
                                    Measurable measurable = (Measurable) list.get(i7);
                                    if (LayoutIdKt.getLayoutId(measurable) == SliderComponents.THUMB) {
                                        final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(j);
                                        int size2 = list.size();
                                        for (int i8 = 0; i8 < size2; i8++) {
                                            Measurable measurable2 = (Measurable) list.get(i8);
                                            if (LayoutIdKt.getLayoutId(measurable2) == SliderComponents.TRACK) {
                                                SliderState sliderState3 = sliderState2;
                                                Orientation orientation6 = sliderState3.orientation;
                                                Orientation orientation7 = Orientation.Vertical;
                                                final Placeable placeableMo610measureBRTryo02 = orientation6 == orientation7 ? measurable2.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(ConstraintsKt.m836offsetNN6EwU$default(0, -placeableMo610measureBRTryo0.height, 1, j), 0, 0, 0, 0, 14)) : measurable2.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(ConstraintsKt.m836offsetNN6EwU$default(-placeableMo610measureBRTryo0.width, 0, 2, j), 0, 0, 0, 0, 11));
                                                final Ref$IntRef ref$IntRef = new Ref$IntRef();
                                                if (sliderState3.orientation == orientation7) {
                                                    iMax = Math.max(placeableMo610measureBRTryo02.width, placeableMo610measureBRTryo0.width);
                                                    int i9 = placeableMo610measureBRTryo0.height;
                                                    int i10 = placeableMo610measureBRTryo02.height;
                                                    iMax2 = i9 + i10;
                                                    i5 = (iMax - placeableMo610measureBRTryo02.width) / 2;
                                                    i6 = i9 / 2;
                                                    iRoundToInt = (iMax - placeableMo610measureBRTryo0.width) / 2;
                                                    int iRoundToInt2 = MathKt__MathJVMKt.roundToInt(sliderState3.getCoercedValueAsFraction() * i10);
                                                    ref$IntRef.element = iRoundToInt2;
                                                    if (sliderState3.reverseVerticalDirection) {
                                                        ref$IntRef.element = placeableMo610measureBRTryo02.height - iRoundToInt2;
                                                    }
                                                } else {
                                                    iMax = placeableMo610measureBRTryo0.width + placeableMo610measureBRTryo02.width;
                                                    iMax2 = Math.max(placeableMo610measureBRTryo02.height, placeableMo610measureBRTryo0.height);
                                                    i5 = placeableMo610measureBRTryo0.width / 2;
                                                    i6 = (iMax2 - placeableMo610measureBRTryo02.height) / 2;
                                                    iRoundToInt = MathKt__MathJVMKt.roundToInt(sliderState3.getCoercedValueAsFraction() * placeableMo610measureBRTryo02.width);
                                                    ref$IntRef.element = (iMax2 - placeableMo610measureBRTryo0.height) / 2;
                                                }
                                                final int i11 = i6;
                                                final int i12 = i5;
                                                final int i13 = iRoundToInt;
                                                int i14 = placeableMo610measureBRTryo02.width;
                                                int i15 = placeableMo610measureBRTryo02.height;
                                                ((SnapshotMutableIntStateImpl) sliderState3.trackWidth$delegate).setIntValue(i14);
                                                ((SnapshotMutableIntStateImpl) sliderState3.trackHeight$delegate).setIntValue(i15);
                                                ((SnapshotMutableIntStateImpl) sliderState3.totalWidth$delegate).setIntValue(iMax);
                                                ((SnapshotMutableIntStateImpl) sliderState3.totalHeight$delegate).setIntValue(iMax2);
                                                return measureScope.layout$1(iMax, iMax2, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.SliderKt$SliderImpl$2$1.1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(1);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj) {
                                                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                                                        placementScope.placeRelative(placeableMo610measureBRTryo02, i12, i11, 0.0f);
                                                        placementScope.placeRelative(placeableMo610measureBRTryo0, i13, ref$IntRef.element, 0.0f);
                                                        return Unit.INSTANCE;
                                                    }
                                                });
                                            }
                                        }
                                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                                    }
                                }
                                throw new NoSuchElementException("Collection contains no element matching the predicate.");
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    MeasurePolicy measurePolicy = (MeasurePolicy) objRememberedValue2;
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierThen4);
                    ComposeUiNode.Companion.getClass();
                    Function0 function02 = ComposeUiNode.Companion.Constructor;
                    if (composerImpl.applier == null) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl.startReusableNode();
                    if (composerImpl.inserting) {
                        composerImpl.createNode(function02);
                    } else {
                        composerImpl.useNode();
                    }
                    Function2 function2 = ComposeUiNode.Companion.SetMeasurePolicy;
                    Updater.m337setimpl(composerImpl, measurePolicy, function2);
                    Function2 function22 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                    Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, function22);
                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function23);
                    }
                    Function2 function24 = ComposeUiNode.Companion.SetModifier;
                    Updater.m337setimpl(composerImpl, modifierMaterializeModifier, function24);
                    boolean zChangedInstance3 = composerImpl.changedInstance(sliderState2);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (!zChangedInstance3) {
                        companion2.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            objRememberedValue3 = new Function1() { // from class: androidx.compose.material3.SliderKt$SliderImpl$1$1$1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    long j = ((IntSize) obj).packedValue;
                                    SliderState sliderState3 = sliderState2;
                                    IntSize.Companion companion3 = IntSize.Companion;
                                    ((SnapshotMutableIntStateImpl) sliderState3.thumbWidth$delegate).setIntValue((int) (j >> 32));
                                    ((SnapshotMutableIntStateImpl) sliderState2.thumbHeight$delegate).setIntValue((int) (j & 4294967295L));
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        }
                        Modifier modifierOnSizeChanged = OnRemeasuredModifierKt.onSizeChanged(modifier3, (Function1) objRememberedValue3);
                        Alignment.Companion.getClass();
                        BiasAlignment biasAlignment = Alignment.Companion.TopStart;
                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                        int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl, modifierOnSizeChanged);
                        composerImpl.startReusableNode();
                        if (composerImpl.inserting) {
                            composerImpl.createNode(function02);
                        } else {
                            composerImpl.useNode();
                        }
                        Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, function2);
                        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope2, function22);
                        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl, currentCompositeKeyHash2, function23);
                        }
                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier2, function24);
                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                        int i5 = (i3 >> 3) & 14;
                        function3.invoke(sliderState2, composerImpl, Integer.valueOf(((i3 >> 9) & 112) | i5));
                        composerImpl.end(true);
                        Modifier modifierLayoutId = LayoutIdKt.layoutId(companion, SliderComponents.TRACK);
                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(biasAlignment, false);
                        int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope3 = composerImpl.currentCompositionLocalScope();
                        Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerImpl, modifierLayoutId);
                        composerImpl.startReusableNode();
                        if (composerImpl.inserting) {
                            composerImpl.createNode(function02);
                        } else {
                            composerImpl.useNode();
                        }
                        Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy2, function2);
                        Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope3, function22);
                        if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash3, composerImpl, currentCompositeKeyHash3, function23);
                        }
                        Updater.m337setimpl(composerImpl, modifierMaterializeModifier3, function24);
                        function32.invoke(sliderState2, composerImpl, Integer.valueOf(i5 | ((i3 >> 12) & 112)));
                        composerImpl.end(true);
                        composerImpl.end(true);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            final SliderState sliderState3 = sliderState2;
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SliderKt.SliderImpl.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    SliderKt.SliderImpl(modifier, sliderState3, z, mutableInteractionSource, function3, function32, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:141:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0117  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void VerticalSlider(final SliderState sliderState, Modifier modifier, boolean z, boolean z2, SliderColors sliderColors, MutableInteractionSource mutableInteractionSource, Function3 function3, Function3 function32, Composer composer, final int i, final int i2) {
        int i3;
        Modifier modifier2;
        int i4;
        final boolean z3;
        int i5;
        boolean z4;
        final SliderColors sliderColorsColors;
        int i6;
        final MutableInteractionSource mutableInteractionSource2;
        int i7;
        Function3 function3RememberComposableLambda;
        int i8;
        int i9;
        int i10;
        final Modifier modifier3;
        Function3 function3RememberComposableLambda2;
        final boolean z5;
        final Function3 function33;
        final SliderColors sliderColors2;
        final Function3 function34;
        final boolean z6;
        final MutableInteractionSource mutableInteractionSource3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2121211368);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changedInstance(sliderState) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i11 = i2 & 2;
        if (i11 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 32 : 16;
            }
            i4 = i2 & 4;
            if (i4 == 0) {
                i3 |= 384;
            } else {
                if ((i & 384) == 0) {
                    z3 = z;
                    i3 |= composerImpl.changed(z3) ? 256 : 128;
                }
                i5 = i2 & 8;
                if (i5 != 0) {
                    i3 |= 3072;
                } else {
                    if ((i & 3072) == 0) {
                        z4 = z2;
                        i3 |= composerImpl.changed(z4) ? 2048 : 1024;
                    }
                    if ((i & 24576) != 0) {
                        if ((i2 & 16) == 0) {
                            sliderColorsColors = sliderColors;
                            int i12 = composerImpl.changed(sliderColorsColors) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                            i3 |= i12;
                        } else {
                            sliderColorsColors = sliderColors;
                        }
                        i3 |= i12;
                    } else {
                        sliderColorsColors = sliderColors;
                    }
                    i6 = i2 & 32;
                    if (i6 == 0) {
                        i3 |= 196608;
                    } else {
                        if ((196608 & i) == 0) {
                            mutableInteractionSource2 = mutableInteractionSource;
                            i3 |= composerImpl.changed(mutableInteractionSource2) ? 131072 : 65536;
                        }
                        i7 = i2 & 64;
                        if (i7 == 0) {
                            if ((1572864 & i) == 0) {
                                function3RememberComposableLambda = function3;
                                i3 |= composerImpl.changedInstance(function3RememberComposableLambda) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            }
                            int i13 = i3;
                            i8 = i2 & 128;
                            if (i8 == 0) {
                                i10 = i13 | 12582912;
                                i9 = i8;
                            } else if ((i & 12582912) == 0) {
                                i9 = i8;
                                i10 = i13 | (composerImpl.changedInstance(function32) ? 8388608 : 4194304);
                            } else {
                                i9 = i8;
                                i10 = i13;
                            }
                            if ((i10 & 4793491) == 4793490 || !composerImpl.getSkipping()) {
                                composerImpl.startDefaults();
                                if ((i & 1) != 0 || composerImpl.getDefaultsInvalid()) {
                                    modifier3 = i11 == 0 ? Modifier.Companion : modifier2;
                                    if (i4 != 0) {
                                        z3 = true;
                                    }
                                    if (i5 != 0) {
                                        z4 = false;
                                    }
                                    if ((i2 & 16) != 0) {
                                        SliderDefaults.INSTANCE.getClass();
                                        i10 &= -57345;
                                        sliderColorsColors = SliderDefaults.colors(composerImpl);
                                    }
                                    if (i6 != 0) {
                                        Object objRememberedValue = composerImpl.rememberedValue();
                                        Composer.Companion.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                                            composerImpl.updateRememberedValue(objRememberedValue);
                                        }
                                        mutableInteractionSource2 = (MutableInteractionSource) objRememberedValue;
                                    }
                                    if (i7 != 0) {
                                        function3RememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(833198872, new Function3() { // from class: androidx.compose.material3.SliderKt.VerticalSlider.2
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(3);
                                            }

                                            @Override // kotlin.jvm.functions.Function3
                                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                SliderState sliderState2 = (SliderState) obj;
                                                Composer composer2 = (Composer) obj2;
                                                int iIntValue = ((Number) obj3).intValue();
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("androidx.compose.material3.VerticalSlider.<anonymous> (Slider.kt:428)");
                                                }
                                                SliderDefaults.INSTANCE.m297ThumbHwbPF3A(mutableInteractionSource2, sliderState2, null, sliderColorsColors, z3, SliderKt.VerticalThumbSize, composer2, ((iIntValue << 3) & 112) | 1769472, 4);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composerImpl);
                                    }
                                    function3RememberComposableLambda2 = i9 == 0 ? ComposableLambdaKt.rememberComposableLambda(330080451, new Function3() { // from class: androidx.compose.material3.SliderKt.VerticalSlider.3
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                                            SliderState sliderState2 = (SliderState) obj;
                                            Composer composer2 = (Composer) obj2;
                                            int iIntValue = ((Number) obj3).intValue();
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.material3.VerticalSlider.<anonymous> (Slider.kt:437)");
                                            }
                                            SliderDefaults sliderDefaults = SliderDefaults.INSTANCE;
                                            float intValue = ((SnapshotMutableIntStateImpl) sliderState2.trackWidth$delegate).getIntValue();
                                            Dp.Companion companion = Dp.Companion;
                                            sliderDefaults.m299TrackmnvyFg4(sliderState2, intValue / 2, null, z3, sliderColorsColors, null, null, 0.0f, 0.0f, composer2, (iIntValue & 14) | 805306368, VolteConstants.ErrorCode.ADDRESS_INCOMPLETE);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl) : function32;
                                } else {
                                    composerImpl.skipToGroupEnd();
                                    if ((i2 & 16) != 0) {
                                        i10 &= -57345;
                                    }
                                    function3RememberComposableLambda2 = function32;
                                    modifier3 = modifier2;
                                }
                                int i14 = i10;
                                composerImpl.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.VerticalSlider (Slider.kt:444)");
                                }
                                if (sliderState.steps >= 0) {
                                    throw new IllegalArgumentException("steps should be >= 0");
                                }
                                sliderState.orientation = Orientation.Vertical;
                                sliderState.reverseVerticalDirection = z4;
                                int i15 = ((i14 >> 3) & 14) | ((i14 << 3) & 112) | (i14 & 896);
                                int i16 = i14 >> 6;
                                SliderImpl(modifier3, sliderState, z3, mutableInteractionSource2, function3RememberComposableLambda, function3RememberComposableLambda2, composerImpl, (i16 & 458752) | i15 | (i16 & 7168) | (57344 & i16));
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                z5 = z4;
                                function33 = function3RememberComposableLambda2;
                                sliderColors2 = sliderColorsColors;
                                function34 = function3RememberComposableLambda;
                                z6 = z3;
                                mutableInteractionSource3 = mutableInteractionSource2;
                            } else {
                                composerImpl.skipToGroupEnd();
                                modifier3 = modifier2;
                                z5 = z4;
                                function33 = function32;
                                z6 = z3;
                                sliderColors2 = sliderColorsColors;
                                mutableInteractionSource3 = mutableInteractionSource2;
                                function34 = function3RememberComposableLambda;
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SliderKt.VerticalSlider.5
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        SliderKt.VerticalSlider(sliderState, modifier3, z6, z5, sliderColors2, mutableInteractionSource3, function34, function33, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i3 |= 1572864;
                        function3RememberComposableLambda = function3;
                        int i132 = i3;
                        i8 = i2 & 128;
                        if (i8 == 0) {
                        }
                        if ((i10 & 4793491) == 4793490) {
                            composerImpl.startDefaults();
                            if ((i & 1) != 0) {
                                if (i11 == 0) {
                                }
                                if (i4 != 0) {
                                }
                                if (i5 != 0) {
                                }
                                if ((i2 & 16) != 0) {
                                }
                                if (i6 != 0) {
                                }
                                if (i7 != 0) {
                                }
                                if (i9 == 0) {
                                }
                                int i142 = i10;
                                composerImpl.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                if (sliderState.steps >= 0) {
                                }
                            }
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    mutableInteractionSource2 = mutableInteractionSource;
                    i7 = i2 & 64;
                    if (i7 == 0) {
                    }
                    function3RememberComposableLambda = function3;
                    int i1322 = i3;
                    i8 = i2 & 128;
                    if (i8 == 0) {
                    }
                    if ((i10 & 4793491) == 4793490) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                z4 = z2;
                if ((i & 24576) != 0) {
                }
                i6 = i2 & 32;
                if (i6 == 0) {
                }
                mutableInteractionSource2 = mutableInteractionSource;
                i7 = i2 & 64;
                if (i7 == 0) {
                }
                function3RememberComposableLambda = function3;
                int i13222 = i3;
                i8 = i2 & 128;
                if (i8 == 0) {
                }
                if ((i10 & 4793491) == 4793490) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            z3 = z;
            i5 = i2 & 8;
            if (i5 != 0) {
            }
            z4 = z2;
            if ((i & 24576) != 0) {
            }
            i6 = i2 & 32;
            if (i6 == 0) {
            }
            mutableInteractionSource2 = mutableInteractionSource;
            i7 = i2 & 64;
            if (i7 == 0) {
            }
            function3RememberComposableLambda = function3;
            int i132222 = i3;
            i8 = i2 & 128;
            if (i8 == 0) {
            }
            if ((i10 & 4793491) == 4793490) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        z3 = z;
        i5 = i2 & 8;
        if (i5 != 0) {
        }
        z4 = z2;
        if ((i & 24576) != 0) {
        }
        i6 = i2 & 32;
        if (i6 == 0) {
        }
        mutableInteractionSource2 = mutableInteractionSource;
        i7 = i2 & 64;
        if (i7 == 0) {
        }
        function3RememberComposableLambda = function3;
        int i1322222 = i3;
        i8 = i2 & 128;
        if (i8 == 0) {
        }
        if ((i10 & 4793491) == 4793490) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    public static final float access$snapValueToTick(float f, float f2, float f3, float[] fArr) {
        Float fValueOf;
        if (fArr.length == 0) {
            fValueOf = null;
        } else {
            float f4 = fArr[0];
            int length = fArr.length - 1;
            if (length == 0) {
                fValueOf = Float.valueOf(f4);
            } else {
                float fAbs = Math.abs(MathHelpersKt.lerp(f2, f3, f4) - f);
                IntProgressionIterator it = new IntRange(1, length).iterator();
                while (it.hasNext) {
                    float f5 = fArr[it.nextInt()];
                    float fAbs2 = Math.abs(MathHelpersKt.lerp(f2, f3, f5) - f);
                    if (Float.compare(fAbs, fAbs2) > 0) {
                        f4 = f5;
                        fAbs = fAbs2;
                    }
                }
                fValueOf = Float.valueOf(f4);
            }
        }
        return fValueOf != null ? MathHelpersKt.lerp(f2, f3, fValueOf.floatValue()) : f;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:130:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x010d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void Slider(final SliderState sliderState, Modifier modifier, boolean z, SliderColors sliderColors, MutableInteractionSource mutableInteractionSource, Function3 function3, Function3 function32, Composer composer, final int i, final int i2) {
        int i3;
        Modifier modifier2;
        int i4;
        boolean z2;
        final SliderColors sliderColorsColors;
        int i5;
        final MutableInteractionSource mutableInteractionSource2;
        int i6;
        Function3 function3RememberComposableLambda;
        int i7;
        Function3 function33;
        SliderColors sliderColors2;
        Function3 function34;
        Function3 function3RememberComposableLambda2;
        int i8;
        Modifier modifier3;
        boolean z3;
        final Function3 function35;
        final MutableInteractionSource mutableInteractionSource3;
        final boolean z4;
        final Modifier modifier4;
        ComposerImpl composerImpl;
        final Function3 function36;
        final SliderColors sliderColors3;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1303883986);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl2.changedInstance(sliderState) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i9 = i2 & 2;
        if (i9 != 0) {
            i3 |= 48;
        } else {
            if ((i & 48) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl2.changed(modifier2) ? 32 : 16;
            }
            i4 = i2 & 4;
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
                        int i10 = composerImpl2.changed(sliderColorsColors) ? 2048 : 1024;
                        i3 |= i10;
                    } else {
                        sliderColorsColors = sliderColors;
                    }
                    i3 |= i10;
                } else {
                    sliderColorsColors = sliderColors;
                }
                i5 = i2 & 16;
                if (i5 != 0) {
                    i3 |= 24576;
                } else {
                    if ((i & 24576) == 0) {
                        mutableInteractionSource2 = mutableInteractionSource;
                        i3 |= composerImpl2.changed(mutableInteractionSource2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                    i6 = i2 & 32;
                    if (i6 == 0) {
                        i3 |= 196608;
                    } else {
                        if ((196608 & i) == 0) {
                            function3RememberComposableLambda = function3;
                            i3 |= composerImpl2.changedInstance(function3RememberComposableLambda) ? 131072 : 65536;
                        }
                        i7 = i2 & 64;
                        if (i7 == 0) {
                            if ((1572864 & i) == 0) {
                                function33 = function32;
                                i3 |= composerImpl2.changedInstance(function33) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            }
                            if ((599187 & i3) != 599186 && composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                modifier4 = modifier2;
                                z4 = z2;
                                composerImpl = composerImpl2;
                                sliderColors3 = sliderColorsColors;
                                mutableInteractionSource3 = mutableInteractionSource2;
                                function36 = function3RememberComposableLambda;
                                function35 = function33;
                            } else {
                                composerImpl2.startDefaults();
                                if ((i & 1) == 0 && !composerImpl2.getDefaultsInvalid()) {
                                    composerImpl2.skipToGroupEnd();
                                    if ((i2 & 8) != 0) {
                                        i3 &= -7169;
                                    }
                                    z3 = z2;
                                    sliderColors2 = sliderColorsColors;
                                    function34 = function3RememberComposableLambda;
                                    function3RememberComposableLambda2 = function33;
                                    i8 = i3;
                                    modifier3 = modifier2;
                                } else {
                                    Modifier modifier5 = i9 == 0 ? Modifier.Companion : modifier2;
                                    final boolean z5 = i4 == 0 ? true : z2;
                                    if ((i2 & 8) != 0) {
                                        SliderDefaults.INSTANCE.getClass();
                                        i3 &= -7169;
                                        sliderColorsColors = SliderDefaults.colors(composerImpl2);
                                    }
                                    if (i5 != 0) {
                                        Object objRememberedValue = composerImpl2.rememberedValue();
                                        Composer.Companion.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                                            composerImpl2.updateRememberedValue(objRememberedValue);
                                        }
                                        mutableInteractionSource2 = (MutableInteractionSource) objRememberedValue;
                                    }
                                    if (i6 != 0) {
                                        function3RememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(1426271326, new Function3() { // from class: androidx.compose.material3.SliderKt.Slider.10
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(3);
                                            }

                                            @Override // kotlin.jvm.functions.Function3
                                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                Composer composer2 = (Composer) obj2;
                                                ((Number) obj3).intValue();
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("androidx.compose.material3.Slider.<anonymous> (Slider.kt:363)");
                                                }
                                                SliderDefaults.INSTANCE.m296Thumb9LiSoMs(mutableInteractionSource2, null, sliderColorsColors, z5, 0L, composer2, 196608, 18);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composerImpl2);
                                    }
                                    if (i7 == 0) {
                                        function3RememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(577038345, new Function3() { // from class: androidx.compose.material3.SliderKt.Slider.11
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(3);
                                            }

                                            @Override // kotlin.jvm.functions.Function3
                                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                SliderState sliderState2 = (SliderState) obj;
                                                Composer composer2 = (Composer) obj2;
                                                int iIntValue = ((Number) obj3).intValue();
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("androidx.compose.material3.Slider.<anonymous> (Slider.kt:370)");
                                                }
                                                SliderDefaults.INSTANCE.m298Track4EFweAY(sliderState2, null, z5, sliderColorsColors, null, null, 0.0f, 0.0f, composer2, (iIntValue & 14) | 100663296, IKnoxCustomManager.Stub.TRANSACTION_getZeroPageState);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composerImpl2);
                                        sliderColors2 = sliderColorsColors;
                                        function34 = function3RememberComposableLambda;
                                    } else {
                                        sliderColors2 = sliderColorsColors;
                                        function34 = function3RememberComposableLambda;
                                        function3RememberComposableLambda2 = function33;
                                    }
                                    i8 = i3;
                                    modifier3 = modifier5;
                                    z3 = z5;
                                }
                                MutableInteractionSource mutableInteractionSource4 = mutableInteractionSource2;
                                composerImpl2.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.Slider (Slider.kt:372)");
                                }
                                if (sliderState.steps < 0) {
                                    int i11 = i8 >> 3;
                                    SliderImpl(modifier3, sliderState, z3, mutableInteractionSource4, function34, function3RememberComposableLambda2, composerImpl2, (i8 & 896) | (i11 & 14) | ((i8 << 3) & 112) | (i11 & 7168) | (57344 & i11) | (i11 & 458752));
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    function35 = function3RememberComposableLambda2;
                                    mutableInteractionSource3 = mutableInteractionSource4;
                                    z4 = z3;
                                    modifier4 = modifier3;
                                    composerImpl = composerImpl2;
                                    function36 = function34;
                                    sliderColors3 = sliderColors2;
                                } else {
                                    throw new IllegalArgumentException("steps should be >= 0");
                                }
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.SliderKt.Slider.13
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        SliderKt.Slider(sliderState, modifier4, z4, sliderColors3, mutableInteractionSource3, function36, function35, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i3 |= 1572864;
                        function33 = function32;
                        if ((599187 & i3) != 599186) {
                            composerImpl2.startDefaults();
                            if ((i & 1) == 0) {
                                if (i9 == 0) {
                                }
                                if (i4 == 0) {
                                }
                                if ((i2 & 8) != 0) {
                                }
                                if (i5 != 0) {
                                }
                                if (i6 != 0) {
                                }
                                if (i7 == 0) {
                                }
                                i8 = i3;
                                modifier3 = modifier5;
                                z3 = z5;
                                MutableInteractionSource mutableInteractionSource42 = mutableInteractionSource2;
                                composerImpl2.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                if (sliderState.steps < 0) {
                                }
                            }
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    function3RememberComposableLambda = function3;
                    i7 = i2 & 64;
                    if (i7 == 0) {
                    }
                    function33 = function32;
                    if ((599187 & i3) != 599186) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                mutableInteractionSource2 = mutableInteractionSource;
                i6 = i2 & 32;
                if (i6 == 0) {
                }
                function3RememberComposableLambda = function3;
                i7 = i2 & 64;
                if (i7 == 0) {
                }
                function33 = function32;
                if ((599187 & i3) != 599186) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            z2 = z;
            if ((i & 3072) == 0) {
            }
            i5 = i2 & 16;
            if (i5 != 0) {
            }
            mutableInteractionSource2 = mutableInteractionSource;
            i6 = i2 & 32;
            if (i6 == 0) {
            }
            function3RememberComposableLambda = function3;
            i7 = i2 & 64;
            if (i7 == 0) {
            }
            function33 = function32;
            if ((599187 & i3) != 599186) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 4;
        if (i4 == 0) {
        }
        z2 = z;
        if ((i & 3072) == 0) {
        }
        i5 = i2 & 16;
        if (i5 != 0) {
        }
        mutableInteractionSource2 = mutableInteractionSource;
        i6 = i2 & 32;
        if (i6 == 0) {
        }
        function3RememberComposableLambda = function3;
        i7 = i2 & 64;
        if (i7 == 0) {
        }
        function33 = function32;
        if ((599187 & i3) != 599186) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
