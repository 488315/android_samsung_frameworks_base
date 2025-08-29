package androidx.compose.material3;

import android.content.res.Resources;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimatableKt;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.foundation.CanvasKt;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.gestures.DraggableKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$Top$1;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScope;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.ColumnScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsPaddingKt;
import androidx.compose.foundation.layout.WindowInsetsPadding_androidKt$imePadding$$inlined$windowInsetsPadding$1;
import androidx.compose.material.SurfaceKt$Surface$3$$ExternalSyntheticOutline0;
import androidx.compose.material3.internal.AnchoredDraggableKt;
import androidx.compose.material3.internal.AnchoredDraggableState;
import androidx.compose.material3.internal.AnchoredDraggableState$draggableState$1;
import androidx.compose.material3.internal.DraggableAnchors;
import androidx.compose.material3.internal.DraggableAnchorsConfig;
import androidx.compose.material3.internal.Strings;
import androidx.compose.material3.internal.Strings_androidKt;
import androidx.compose.material3.tokens.MotionSchemeKeyTokens;
import androidx.compose.material3.tokens.ScrimTokens;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.runtime.saveable.SaverKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.TransformOriginKt;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection;
import androidx.compose.ui.input.nestedscroll.NestedScrollModifierKt;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.input.pointer.SuspendPointerInputElement;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.util.MathHelpersKt;
import com.android.systemui.R;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes.dex */
public abstract class ModalBottomSheetKt {
    public static final long PredictiveBackChildTransformOrigin;
    public static final float PredictiveBackMaxScaleXDistance;
    public static final float PredictiveBackMaxScaleYDistance;

    static {
        Dp.Companion companion = Dp.Companion;
        PredictiveBackMaxScaleXDistance = 48;
        PredictiveBackMaxScaleYDistance = 24;
        PredictiveBackChildTransformOrigin = TransformOriginKt.TransformOrigin(0.5f, 0.0f);
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x025a  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0279  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x02ef  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x02f5  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x0300  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x036b  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x037d  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x03bd  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x03c6  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:339:0x0543  */
    /* JADX WARN: Removed duplicated region for block: B:341:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0129  */
    /* renamed from: ModalBottomSheet-YbuCTN8, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m272ModalBottomSheetYbuCTN8(final Function0 function0, Modifier modifier, SheetState sheetState, float f, boolean z, Shape shape, long j, long j2, float f2, long j3, Function2 function2, Function2 function22, ModalBottomSheetProperties modalBottomSheetProperties, final Function3 function3, Composer composer, final int i, final int i2, final int i3) {
        int i4;
        int i5;
        final SheetState sheetStateRememberModalBottomSheetState;
        int i6;
        int i7;
        int i8;
        int i9;
        boolean z2;
        boolean z3;
        int i10;
        final long containerColor;
        long jM259contentColorForek8zF_U;
        int i11;
        float f3;
        int i12;
        Function2 function23;
        int i13;
        int i14;
        float f4;
        Shape expandedShape;
        long jColor;
        Function2 function24;
        Function2 function25;
        final Function2 function26;
        final long j4;
        ModalBottomSheetProperties modalBottomSheetProperties2;
        final long j5;
        final float f5;
        final Function2 function27;
        final long j6;
        final boolean z4;
        final float f6;
        final Modifier modifier2;
        final Shape shape2;
        boolean zChangedInstance;
        ComposerImpl composerImpl;
        final ModalBottomSheetProperties modalBottomSheetProperties3;
        final SheetState sheetState2;
        final long j7;
        final Modifier modifier3;
        final float f7;
        final boolean z5;
        final Shape shape3;
        final long j8;
        final float f8;
        final Function2 function28;
        final Function2 function29;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        int i15;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-2063607331);
        if ((i3 & 1) != 0) {
            i4 = i | 6;
        } else if ((i & 6) == 0) {
            i4 = i | (composerImpl2.changedInstance(function0) ? 4 : 2);
        } else {
            i4 = i;
        }
        int i16 = i3 & 2;
        if (i16 != 0) {
            i4 |= 48;
        } else {
            if ((i & 48) == 0) {
                i5 = -29360129;
                i4 |= composerImpl2.changed(modifier) ? 32 : 16;
            }
            if ((i & 384) != 0) {
                if ((i3 & 4) == 0) {
                    sheetStateRememberModalBottomSheetState = sheetState;
                    if (composerImpl2.changed(sheetStateRememberModalBottomSheetState)) {
                        i15 = 256;
                    }
                    i4 |= i15;
                } else {
                    sheetStateRememberModalBottomSheetState = sheetState;
                }
                i15 = 128;
                i4 |= i15;
            } else {
                sheetStateRememberModalBottomSheetState = sheetState;
            }
            i6 = i4;
            int i17 = 16;
            i7 = i3 & 8;
            if (i7 == 0) {
                i6 |= 3072;
                i8 = 32;
            } else {
                i8 = 32;
                if ((i & 3072) == 0) {
                    i6 |= composerImpl2.changed(f) ? 2048 : 1024;
                }
            }
            i9 = i3 & 16;
            if (i9 == 0) {
                i6 |= 24576;
                z2 = true;
            } else {
                z2 = true;
                if ((i & 24576) == 0) {
                    z3 = z;
                    i6 |= composerImpl2.changed(z3) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                if ((i & 196608) == 0) {
                    if ((i3 & 32) == 0) {
                        i10 = 4;
                        int i18 = composerImpl2.changed(shape) ? 131072 : 65536;
                        i6 |= i18;
                    } else {
                        i10 = 4;
                    }
                    i6 |= i18;
                } else {
                    i10 = 4;
                }
                if ((i & 1572864) == 0) {
                    containerColor = j;
                    i6 |= ((i3 & 64) == 0 && composerImpl2.changed(containerColor)) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                } else {
                    containerColor = j;
                }
                if ((i & 12582912) == 0) {
                    jM259contentColorForek8zF_U = j2;
                    i6 |= ((i3 & 128) == 0 && composerImpl2.changed(jM259contentColorForek8zF_U)) ? 8388608 : 4194304;
                } else {
                    jM259contentColorForek8zF_U = j2;
                }
                i11 = i3 & 256;
                if (i11 != 0) {
                    i6 |= 100663296;
                    f3 = f2;
                } else {
                    f3 = f2;
                    if ((i & 100663296) == 0) {
                        i6 |= composerImpl2.changed(f3) ? 67108864 : 33554432;
                    }
                }
                if ((i & 805306368) == 0) {
                    i6 |= ((i3 & 512) == 0 && composerImpl2.changed(j3)) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                }
                i12 = i3 & 1024;
                if (i12 != 0) {
                    i13 = i2 | 6;
                    function23 = function2;
                } else if ((i2 & 6) == 0) {
                    function23 = function2;
                    i13 = i2 | (composerImpl2.changedInstance(function23) ? i10 : 2);
                } else {
                    function23 = function2;
                    i13 = i2;
                }
                if ((i2 & 48) == 0) {
                    i14 = i12;
                    if ((i3 & 2048) == 0 && composerImpl2.changedInstance(function22)) {
                        i17 = i8;
                    }
                    i13 |= i17;
                } else {
                    i14 = i12;
                }
                if ((i2 & 384) == 0) {
                    i13 |= ((i3 & 4096) == 0 && composerImpl2.changed(modalBottomSheetProperties)) ? 256 : 128;
                }
                int i19 = i13;
                if ((8192 & i3) == 0) {
                    if ((i2 & 3072) == 0) {
                        i19 |= composerImpl2.changedInstance(function3) ? 2048 : 1024;
                    }
                    if ((i6 & 306783379) != 306783378 && (i19 & 1171) == 1170 && composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        modifier3 = modifier;
                        f7 = f;
                        modalBottomSheetProperties3 = modalBottomSheetProperties;
                        sheetState2 = sheetStateRememberModalBottomSheetState;
                        f8 = f3;
                        j8 = jM259contentColorForek8zF_U;
                        composerImpl = composerImpl2;
                        shape3 = shape;
                        j7 = j3;
                        function28 = function23;
                        z5 = z3;
                        function29 = function22;
                    } else {
                        composerImpl2.startDefaults();
                        if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                            Modifier modifier4 = i16 == 0 ? Modifier.Companion : modifier;
                            if ((i3 & 4) != 0) {
                                sheetStateRememberModalBottomSheetState = rememberModalBottomSheetState(composerImpl2);
                                i6 &= -897;
                            }
                            if (i7 == 0) {
                                BottomSheetDefaults.INSTANCE.getClass();
                                f4 = BottomSheetDefaults.SheetMaxWidth;
                            } else {
                                f4 = f;
                            }
                            if (i9 != 0) {
                                z3 = z2;
                            }
                            if ((i3 & 32) == 0) {
                                BottomSheetDefaults.INSTANCE.getClass();
                                expandedShape = BottomSheetDefaults.getExpandedShape(composerImpl2);
                                i6 &= -458753;
                            } else {
                                expandedShape = shape;
                            }
                            if ((i3 & 64) != 0) {
                                BottomSheetDefaults.INSTANCE.getClass();
                                containerColor = BottomSheetDefaults.getContainerColor(composerImpl2);
                                i6 &= -3670017;
                            }
                            if ((128 & i3) != 0) {
                                jM259contentColorForek8zF_U = ColorSchemeKt.m259contentColorForek8zF_U(containerColor, composerImpl2);
                                i6 &= i5;
                            }
                            if (i11 != 0) {
                                f3 = 0;
                                Dp.Companion companion = Dp.Companion;
                            }
                            if ((i3 & 512) == 0) {
                                BottomSheetDefaults.INSTANCE.getClass();
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.BottomSheetDefaults.<get-ScrimColor> (SheetDefaults.kt:388)");
                                }
                                ScrimTokens.INSTANCE.getClass();
                                long value = ColorSchemeKt.getValue(ScrimTokens.ContainerColor, composerImpl2);
                                jColor = ColorKt.Color(Color.m463getRedimpl(value), Color.m462getGreenimpl(value), Color.m460getBlueimpl(value), 0.32f, Color.m461getColorSpaceimpl(value));
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                i6 &= -1879048193;
                            } else {
                                jColor = j3;
                            }
                            if (i14 == 0) {
                                ComposableSingletons$ModalBottomSheetKt.INSTANCE.getClass();
                                function24 = ComposableSingletons$ModalBottomSheetKt.f7lambda1;
                            } else {
                                function24 = function2;
                            }
                            int i20 = i19;
                            if ((2048 & i3) == 0) {
                                i20 &= -113;
                                function25 = new Function2() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$1
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        ComposerImpl composerImpl3 = (ComposerImpl) ((Composer) obj);
                                        composerImpl3.startReplaceGroup(807771048);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.ModalBottomSheet.<anonymous> (ModalBottomSheet.kt:134)");
                                        }
                                        BottomSheetDefaults.INSTANCE.getClass();
                                        WindowInsets windowInsets = BottomSheetDefaults.getWindowInsets(composerImpl3);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl3.end(false);
                                        return windowInsets;
                                    }
                                };
                            } else {
                                function25 = function22;
                            }
                            i19 = i20;
                            Modifier modifier5 = modifier4;
                            if ((i3 & 4096) == 0) {
                                Color.Companion companion2 = Color.Companion;
                                companion2.getClass();
                                long j9 = Color.Transparent;
                                long j10 = jColor;
                                boolean z6 = (ULong.m3446equalsimpl0(jM259contentColorForek8zF_U, j9) || ((double) ColorKt.m468luminance8_81llA(jM259contentColorForek8zF_U)) > 0.5d) ? false : z2;
                                companion2.getClass();
                                modalBottomSheetProperties2 = new ModalBottomSheetProperties(false, z6, (ULong.m3446equalsimpl0(jM259contentColorForek8zF_U, j9) || ((double) ColorKt.m468luminance8_81llA(jM259contentColorForek8zF_U)) > 0.5d) ? false : z2, 1, null);
                                i19 &= -897;
                                function26 = function25;
                                j4 = j10;
                            } else {
                                function26 = function25;
                                j4 = jColor;
                                modalBottomSheetProperties2 = modalBottomSheetProperties;
                            }
                            j5 = containerColor;
                            f5 = f3;
                            function27 = function24;
                            j6 = jM259contentColorForek8zF_U;
                            z4 = z3;
                            f6 = f4;
                            modifier2 = modifier5;
                            shape2 = expandedShape;
                        } else {
                            composerImpl2.skipToGroupEnd();
                            if ((i3 & 4) != 0) {
                                i6 &= -897;
                            }
                            if ((i3 & 32) != 0) {
                                i6 &= -458753;
                            }
                            if ((i3 & 64) != 0) {
                                i6 &= -3670017;
                            }
                            if ((i3 & 128) != 0) {
                                i6 &= i5;
                            }
                            if ((i3 & 512) != 0) {
                                i6 &= -1879048193;
                            }
                            if ((2048 & i3) != 0) {
                                i19 &= -113;
                            }
                            if ((i3 & 4096) != 0) {
                                i19 &= -897;
                            }
                            modifier2 = modifier;
                            f6 = f;
                            shape2 = shape;
                            j4 = j3;
                            function26 = function22;
                            modalBottomSheetProperties2 = modalBottomSheetProperties;
                            function27 = function23;
                            j5 = containerColor;
                            f5 = f3;
                            j6 = jM259contentColorForek8zF_U;
                            z4 = z3;
                        }
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.ModalBottomSheet (ModalBottomSheet.kt:141)");
                        }
                        MotionSchemeKeyTokens motionSchemeKeyTokens = MotionSchemeKeyTokens.DefaultSpatial;
                        final SpringSpec springSpecValue = MotionSchemeKt.value(motionSchemeKeyTokens, composerImpl2);
                        final SpringSpec springSpecValue2 = MotionSchemeKt.value(motionSchemeKeyTokens, composerImpl2);
                        final SpringSpec springSpecValue3 = MotionSchemeKt.value(MotionSchemeKeyTokens.FastEffects, composerImpl2);
                        int i21 = (i6 & 896) ^ 384;
                        zChangedInstance = (((i21 > 256 || !composerImpl2.changed(sheetStateRememberModalBottomSheetState)) && (i6 & 384) != 256) ? false : z2) | composerImpl2.changedInstance(springSpecValue2) | composerImpl2.changedInstance(springSpecValue3) | composerImpl2.changedInstance(springSpecValue);
                        Object objRememberedValue = composerImpl2.rememberedValue();
                        if (!zChangedInstance) {
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new Function0() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$2$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        SheetState sheetState3 = sheetStateRememberModalBottomSheetState;
                                        sheetState3.showMotionSpec = springSpecValue2;
                                        sheetState3.hideMotionSpec = springSpecValue3;
                                        sheetState3.anchoredDraggableMotionSpec = springSpecValue;
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl2.updateRememberedValue(objRememberedValue);
                            }
                            EffectsKt.SideEffect((Function0) objRememberedValue, composerImpl2);
                            Object objRememberedValue2 = composerImpl2.rememberedValue();
                            Composer.Companion.getClass();
                            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                            if (objRememberedValue2 == composer$Companion$Empty$1) {
                                CompositionScopedCoroutineScopeCanceller compositionScopedCoroutineScopeCanceller = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2));
                                composerImpl2.updateRememberedValue(compositionScopedCoroutineScopeCanceller);
                                objRememberedValue2 = compositionScopedCoroutineScopeCanceller;
                            }
                            final CoroutineScope coroutineScope = ((CompositionScopedCoroutineScopeCanceller) objRememberedValue2).coroutineScope;
                            int i22 = i6 & 14;
                            boolean zChangedInstance2 = (((i21 <= 256 || !composerImpl2.changed(sheetStateRememberModalBottomSheetState)) && (i6 & 384) != 256) ? false : z2) | composerImpl2.changedInstance(coroutineScope) | (i22 == i10 ? z2 : false);
                            Object objRememberedValue3 = composerImpl2.rememberedValue();
                            if (zChangedInstance2 || objRememberedValue3 == composer$Companion$Empty$1) {
                                objRememberedValue3 = new Function0() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$animateToDismiss$1$1

                                    /* renamed from: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$animateToDismiss$1$1$1, reason: invalid class name */
                                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                        final /* synthetic */ SheetState $sheetState;
                                        int label;

                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        public AnonymousClass1(SheetState sheetState, Continuation continuation) {
                                            super(2, continuation);
                                            this.$sheetState = sheetState;
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Continuation create(Object obj, Continuation continuation) {
                                            return new AnonymousClass1(this.$sheetState, continuation);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Object invokeSuspend(Object obj) {
                                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                            int i = this.label;
                                            if (i == 0) {
                                                ResultKt.throwOnFailure(obj);
                                                SheetState sheetState = this.$sheetState;
                                                this.label = 1;
                                                if (sheetState.hide(this) == coroutineSingletons) {
                                                    return coroutineSingletons;
                                                }
                                            } else {
                                                if (i != 1) {
                                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                }
                                                ResultKt.throwOnFailure(obj);
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        if (((Boolean) sheetStateRememberModalBottomSheetState.anchoredDraggableState.confirmValueChange.mo781invoke(SheetValue.Hidden)).booleanValue()) {
                                            StandaloneCoroutine standaloneCoroutineLaunch$default = BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(sheetStateRememberModalBottomSheetState, null), 3);
                                            final SheetState sheetState3 = sheetStateRememberModalBottomSheetState;
                                            final Function0 function02 = function0;
                                            standaloneCoroutineLaunch$default.invokeOnCompletion(new Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$animateToDismiss$1$1.2
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj) {
                                                    if (!sheetState3.isVisible()) {
                                                        function02.invoke();
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            });
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl2.updateRememberedValue(objRememberedValue3);
                            }
                            final Function0 function02 = (Function0) objRememberedValue3;
                            boolean zChangedInstance3 = composerImpl2.changedInstance(coroutineScope) | (((i21 <= 256 || !composerImpl2.changed(sheetStateRememberModalBottomSheetState)) && (i6 & 384) != 256) ? false : z2) | (i22 == 4 ? z2 : false);
                            Object objRememberedValue4 = composerImpl2.rememberedValue();
                            if (zChangedInstance3 || objRememberedValue4 == composer$Companion$Empty$1) {
                                objRememberedValue4 = new Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$settleToDismiss$1$1

                                    /* renamed from: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$settleToDismiss$1$1$1, reason: invalid class name */
                                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                        final /* synthetic */ float $it;
                                        final /* synthetic */ SheetState $sheetState;
                                        int label;

                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        public AnonymousClass1(SheetState sheetState, float f, Continuation continuation) {
                                            super(2, continuation);
                                            this.$sheetState = sheetState;
                                            this.$it = f;
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Continuation create(Object obj, Continuation continuation) {
                                            return new AnonymousClass1(this.$sheetState, this.$it, continuation);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Object invokeSuspend(Object obj) {
                                            Object objAnimateTo;
                                            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                                            int i = this.label;
                                            if (i == 0) {
                                                ResultKt.throwOnFailure(obj);
                                                SheetState sheetState = this.$sheetState;
                                                float f = this.$it;
                                                this.label = 1;
                                                AnchoredDraggableState anchoredDraggableState = sheetState.anchoredDraggableState;
                                                Object value = ((SnapshotMutableStateImpl) anchoredDraggableState.currentValue$delegate).getValue();
                                                Object objComputeTarget = anchoredDraggableState.computeTarget(anchoredDraggableState.requireOffset(), f, value);
                                                if (((Boolean) anchoredDraggableState.confirmValueChange.mo781invoke(objComputeTarget)).booleanValue()) {
                                                    objAnimateTo = AnchoredDraggableKt.animateTo(anchoredDraggableState, objComputeTarget, f, this);
                                                    if (objAnimateTo != obj2) {
                                                        objAnimateTo = Unit.INSTANCE;
                                                    }
                                                } else {
                                                    objAnimateTo = AnchoredDraggableKt.animateTo(anchoredDraggableState, value, f, this);
                                                    if (objAnimateTo != obj2) {
                                                        objAnimateTo = Unit.INSTANCE;
                                                    }
                                                }
                                                if (objAnimateTo != obj2) {
                                                    objAnimateTo = Unit.INSTANCE;
                                                }
                                                if (objAnimateTo == obj2) {
                                                    return obj2;
                                                }
                                            } else {
                                                if (i != 1) {
                                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                }
                                                ResultKt.throwOnFailure(obj);
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        StandaloneCoroutine standaloneCoroutineLaunch$default = BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(sheetStateRememberModalBottomSheetState, ((Number) obj).floatValue(), null), 3);
                                        final SheetState sheetState3 = sheetStateRememberModalBottomSheetState;
                                        final Function0 function03 = function0;
                                        standaloneCoroutineLaunch$default.invokeOnCompletion(new Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$settleToDismiss$1$1.2
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            /* renamed from: invoke */
                                            public final Object mo781invoke(Object obj2) {
                                                if (!sheetState3.isVisible()) {
                                                    function03.invoke();
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        });
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl2.updateRememberedValue(objRememberedValue4);
                            }
                            final Function1 function1 = (Function1) objRememberedValue4;
                            Object objRememberedValue5 = composerImpl2.rememberedValue();
                            if (objRememberedValue5 == composer$Companion$Empty$1) {
                                objRememberedValue5 = AnimatableKt.Animatable(0.0f, 0.01f);
                                composerImpl2.updateRememberedValue(objRememberedValue5);
                            }
                            final Animatable animatable = (Animatable) objRememberedValue5;
                            boolean zChangedInstance4 = (i22 == 4 ? z2 : false) | (((i21 <= 256 || !composerImpl2.changed(sheetStateRememberModalBottomSheetState)) && (i6 & 384) != 256) ? false : z2) | composerImpl2.changedInstance(coroutineScope) | composerImpl2.changedInstance(animatable);
                            Object objRememberedValue6 = composerImpl2.rememberedValue();
                            if (zChangedInstance4 || objRememberedValue6 == composer$Companion$Empty$1) {
                                objRememberedValue6 = new Function0() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$3$1

                                    /* renamed from: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$3$1$1, reason: invalid class name */
                                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                        final /* synthetic */ Animatable<Float, AnimationVector1D> $predictiveBackProgress;
                                        int label;

                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        public AnonymousClass1(Animatable<Float, AnimationVector1D> animatable, Continuation continuation) {
                                            super(2, continuation);
                                            this.$predictiveBackProgress = animatable;
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Continuation create(Object obj, Continuation continuation) {
                                            return new AnonymousClass1(this.$predictiveBackProgress, continuation);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Object invokeSuspend(Object obj) {
                                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                            int i = this.label;
                                            if (i == 0) {
                                                ResultKt.throwOnFailure(obj);
                                                Animatable<Float, AnimationVector1D> animatable = this.$predictiveBackProgress;
                                                Float f = new Float(0.0f);
                                                this.label = 1;
                                                if (Animatable.animateTo$default(animatable, f, null, null, null, this, 14) == coroutineSingletons) {
                                                    return coroutineSingletons;
                                                }
                                            } else {
                                                if (i != 1) {
                                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                }
                                                ResultKt.throwOnFailure(obj);
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }

                                    /* renamed from: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$3$1$2, reason: invalid class name */
                                    final class AnonymousClass2 extends SuspendLambda implements Function2 {
                                        final /* synthetic */ SheetState $sheetState;
                                        int label;

                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        public AnonymousClass2(SheetState sheetState, Continuation continuation) {
                                            super(2, continuation);
                                            this.$sheetState = sheetState;
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Continuation create(Object obj, Continuation continuation) {
                                            return new AnonymousClass2(this.$sheetState, continuation);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Object invokeSuspend(Object obj) {
                                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                            int i = this.label;
                                            if (i == 0) {
                                                ResultKt.throwOnFailure(obj);
                                                SheetState sheetState = this.$sheetState;
                                                this.label = 1;
                                                if (sheetState.partialExpand(this) == coroutineSingletons) {
                                                    return coroutineSingletons;
                                                }
                                            } else {
                                                if (i != 1) {
                                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                }
                                                ResultKt.throwOnFailure(obj);
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }

                                    /* renamed from: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$3$1$3, reason: invalid class name */
                                    final class AnonymousClass3 extends SuspendLambda implements Function2 {
                                        final /* synthetic */ SheetState $sheetState;
                                        int label;

                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        public AnonymousClass3(SheetState sheetState, Continuation continuation) {
                                            super(2, continuation);
                                            this.$sheetState = sheetState;
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Continuation create(Object obj, Continuation continuation) {
                                            return new AnonymousClass3(this.$sheetState, continuation);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Object invokeSuspend(Object obj) {
                                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                            int i = this.label;
                                            if (i == 0) {
                                                ResultKt.throwOnFailure(obj);
                                                SheetState sheetState = this.$sheetState;
                                                this.label = 1;
                                                if (sheetState.hide(this) == coroutineSingletons) {
                                                    return coroutineSingletons;
                                                }
                                            } else {
                                                if (i != 1) {
                                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                }
                                                ResultKt.throwOnFailure(obj);
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        if (((SheetValue) ((SnapshotMutableStateImpl) sheetStateRememberModalBottomSheetState.anchoredDraggableState.currentValue$delegate).getValue()) == SheetValue.Expanded && sheetStateRememberModalBottomSheetState.anchoredDraggableState.getAnchors().hasAnchorFor(SheetValue.PartiallyExpanded)) {
                                            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(animatable, null), 3);
                                            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(sheetStateRememberModalBottomSheetState, null), 3);
                                        } else {
                                            StandaloneCoroutine standaloneCoroutineLaunch$default = BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(sheetStateRememberModalBottomSheetState, null), 3);
                                            final Function0 function03 = function0;
                                            standaloneCoroutineLaunch$default.invokeOnCompletion(new Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$3$1.4
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj) {
                                                    function03.invoke();
                                                    return Unit.INSTANCE;
                                                }
                                            });
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl2.updateRememberedValue(objRememberedValue6);
                            }
                            final SheetState sheetState3 = sheetStateRememberModalBottomSheetState;
                            ModalBottomSheet_androidKt.ModalBottomSheetDialog((Function0) objRememberedValue6, modalBottomSheetProperties2, animatable, ComposableLambdaKt.rememberComposableLambda(976758462, new Function2() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$4
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                                @Override // kotlin.jvm.functions.Function2
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj, Object obj2) throws Resources.NotFoundException {
                                    Composer composer2 = (Composer) obj;
                                    if ((((Number) obj2).intValue() & 3) == 2) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.material3.ModalBottomSheet.<anonymous> (ModalBottomSheet.kt:188)");
                                            }
                                            Modifier modifierSemantics = SemanticsModifierKt.semantics(ComposedModifierKt.composed(SizeKt.fillMaxSize(Modifier.Companion, 1.0f), InspectableValueKt.NoInspectorInfo, new WindowInsetsPadding_androidKt$imePadding$$inlined$windowInsetsPadding$1()), false, new Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$4.1
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj3) {
                                                    SemanticsPropertiesKt.setTraversalGroup((SemanticsPropertyReceiver) obj3);
                                                    return Unit.INSTANCE;
                                                }
                                            });
                                            long j11 = j4;
                                            Function0 function03 = function02;
                                            SheetState sheetState4 = sheetState3;
                                            Animatable<Float, AnimationVector1D> animatable2 = animatable;
                                            CoroutineScope coroutineScope2 = coroutineScope;
                                            Function1 function12 = function1;
                                            Modifier modifier6 = modifier2;
                                            float f9 = f6;
                                            boolean z7 = z4;
                                            Shape shape4 = shape2;
                                            long j12 = j5;
                                            long j13 = j6;
                                            float f10 = f5;
                                            Function2 function210 = function27;
                                            Function2 function211 = function26;
                                            Function3 function32 = function3;
                                            Alignment.Companion.getClass();
                                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                            ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierSemantics);
                                            ComposeUiNode.Companion.getClass();
                                            Function0 function04 = ComposeUiNode.Companion.Constructor;
                                            if (composerImpl4.applier == null) {
                                                ComposablesKt.invalidApplier();
                                                throw null;
                                            }
                                            composerImpl4.startReusableNode();
                                            if (composerImpl4.inserting) {
                                                composerImpl4.createNode(function04);
                                            } else {
                                                composerImpl4.useNode();
                                            }
                                            Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                            Function2 function212 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                            if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function212);
                                            }
                                            Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                            ModalBottomSheetKt.m274access$Scrim3JVO9M(j11, function03, ((SheetValue) sheetState4.anchoredDraggableState.targetValue$delegate.getValue()) != SheetValue.Hidden, composer2, 0);
                                            ModalBottomSheetKt.m273ModalBottomSheetContent7e2Q(boxScopeInstance, animatable2, coroutineScope2, function03, function12, modifier6, sheetState4, f9, z7, shape4, j12, j13, f10, function210, function211, function32, composer2, 70, 0, 0);
                                            composerImpl4.end(true);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl2), composerImpl2, ((i19 >> 3) & 112) | 3584);
                            composerImpl = composerImpl2;
                            if (sheetStateRememberModalBottomSheetState.anchoredDraggableState.getAnchors().hasAnchorFor(SheetValue.Expanded)) {
                                composerImpl.startReplaceGroup(2074626392);
                                boolean z7 = ((i21 <= 256 || !composerImpl.changed(sheetStateRememberModalBottomSheetState)) && (i6 & 384) != 256) ? false : z2;
                                Object objRememberedValue7 = composerImpl.rememberedValue();
                                if (z7 || objRememberedValue7 == composer$Companion$Empty$1) {
                                    objRememberedValue7 = new ModalBottomSheetKt$ModalBottomSheet$5$1(sheetStateRememberModalBottomSheetState, null);
                                    composerImpl.updateRememberedValue(objRememberedValue7);
                                }
                                EffectsKt.LaunchedEffect(composerImpl, sheetStateRememberModalBottomSheetState, (Function2) objRememberedValue7);
                                composerImpl.end(false);
                            } else {
                                composerImpl.startReplaceGroup(2074687896);
                                composerImpl.end(false);
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            modalBottomSheetProperties3 = modalBottomSheetProperties2;
                            sheetState2 = sheetStateRememberModalBottomSheetState;
                            j7 = j4;
                            modifier3 = modifier2;
                            f7 = f6;
                            z5 = z4;
                            shape3 = shape2;
                            containerColor = j5;
                            j8 = j6;
                            f8 = f5;
                            function28 = function27;
                            function29 = function26;
                        }
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheet$6
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                ModalBottomSheetKt.m272ModalBottomSheetYbuCTN8(function0, modifier3, sheetState2, f7, z5, shape3, containerColor, j8, f8, j7, function28, function29, modalBottomSheetProperties3, function3, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2), i3);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i19 |= 3072;
                if ((i6 & 306783379) != 306783378) {
                    composerImpl2.startDefaults();
                    if ((i & 1) != 0) {
                        if (i16 == 0) {
                        }
                        if ((i3 & 4) != 0) {
                        }
                        if (i7 == 0) {
                        }
                        if (i9 != 0) {
                        }
                        if ((i3 & 32) == 0) {
                        }
                        if ((i3 & 64) != 0) {
                        }
                        if ((128 & i3) != 0) {
                        }
                        if (i11 != 0) {
                        }
                        if ((i3 & 512) == 0) {
                        }
                        if (i14 == 0) {
                        }
                        int i202 = i19;
                        if ((2048 & i3) == 0) {
                        }
                        i19 = i202;
                        Modifier modifier52 = modifier4;
                        if ((i3 & 4096) == 0) {
                        }
                        j5 = containerColor;
                        f5 = f3;
                        function27 = function24;
                        j6 = jM259contentColorForek8zF_U;
                        z4 = z3;
                        f6 = f4;
                        modifier2 = modifier52;
                        shape2 = expandedShape;
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        MotionSchemeKeyTokens motionSchemeKeyTokens2 = MotionSchemeKeyTokens.DefaultSpatial;
                        final FiniteAnimationSpec<Float> springSpecValue4 = MotionSchemeKt.value(motionSchemeKeyTokens2, composerImpl2);
                        final FiniteAnimationSpec<Float> springSpecValue22 = MotionSchemeKt.value(motionSchemeKeyTokens2, composerImpl2);
                        final FiniteAnimationSpec<Float> springSpecValue32 = MotionSchemeKt.value(MotionSchemeKeyTokens.FastEffects, composerImpl2);
                        int i212 = (i6 & 896) ^ 384;
                        if (i212 > 256) {
                            zChangedInstance = (((i212 > 256 || !composerImpl2.changed(sheetStateRememberModalBottomSheetState)) && (i6 & 384) != 256) ? false : z2) | composerImpl2.changedInstance(springSpecValue22) | composerImpl2.changedInstance(springSpecValue32) | composerImpl2.changedInstance(springSpecValue4);
                            Object objRememberedValue8 = composerImpl2.rememberedValue();
                            if (!zChangedInstance) {
                            }
                        } else {
                            zChangedInstance = (((i212 > 256 || !composerImpl2.changed(sheetStateRememberModalBottomSheetState)) && (i6 & 384) != 256) ? false : z2) | composerImpl2.changedInstance(springSpecValue22) | composerImpl2.changedInstance(springSpecValue32) | composerImpl2.changedInstance(springSpecValue4);
                            Object objRememberedValue82 = composerImpl2.rememberedValue();
                            if (!zChangedInstance) {
                            }
                        }
                    }
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            z3 = z;
            if ((i & 196608) == 0) {
            }
            if ((i & 1572864) == 0) {
            }
            if ((i & 12582912) == 0) {
            }
            i11 = i3 & 256;
            if (i11 != 0) {
            }
            if ((i & 805306368) == 0) {
            }
            i12 = i3 & 1024;
            if (i12 != 0) {
            }
            if ((i2 & 48) == 0) {
            }
            if ((i2 & 384) == 0) {
            }
            int i192 = i13;
            if ((8192 & i3) == 0) {
            }
            if ((i6 & 306783379) != 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        i5 = -29360129;
        if ((i & 384) != 0) {
        }
        i6 = i4;
        int i172 = 16;
        i7 = i3 & 8;
        if (i7 == 0) {
        }
        i9 = i3 & 16;
        if (i9 == 0) {
        }
        z3 = z;
        if ((i & 196608) == 0) {
        }
        if ((i & 1572864) == 0) {
        }
        if ((i & 12582912) == 0) {
        }
        i11 = i3 & 256;
        if (i11 != 0) {
        }
        if ((i & 805306368) == 0) {
        }
        i12 = i3 & 1024;
        if (i12 != 0) {
        }
        if ((i2 & 48) == 0) {
        }
        if ((i2 & 384) == 0) {
        }
        int i1922 = i13;
        if ((8192 & i3) == 0) {
        }
        if ((i6 & 306783379) != 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x01eb  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x01f7  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0238  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0285  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x02a3  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x02b6  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x02bc  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x02e5  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x02ed  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x02f1  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0301  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x031d  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0359  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0385  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x03c4  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x03d0  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x03de  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x0426  */
    /* JADX WARN: Removed duplicated region for block: B:294:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:316:0x0496  */
    /* JADX WARN: Removed duplicated region for block: B:323:0x0509  */
    /* JADX WARN: Removed duplicated region for block: B:325:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0121  */
    /* renamed from: ModalBottomSheetContent-7---e2Q, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m273ModalBottomSheetContent7e2Q(final BoxScope boxScope, final Animatable animatable, final CoroutineScope coroutineScope, final Function0 function0, final Function1 function1, Modifier modifier, SheetState sheetState, float f, boolean z, Shape shape, long j, long j2, float f2, Function2 function2, Function2 function22, final Function3 function3, Composer composer, final int i, final int i2, final int i3) throws Resources.NotFoundException {
        int i4;
        int i5;
        boolean z2;
        Function0 function02;
        int i6;
        Modifier modifier2;
        int i7;
        float f3;
        int i8;
        boolean z3;
        int i9;
        int i10;
        int i11;
        int i12;
        float f4;
        int i13;
        int i14;
        SheetState sheetStateRememberModalBottomSheetState;
        Shape expandedShape;
        int i15;
        long containerColor;
        long jM259contentColorForek8zF_U;
        float f5;
        Function2 function23;
        Shape shape2;
        int i16;
        Function2 function24;
        float f6;
        long j3;
        final SheetState sheetState2;
        long j4;
        int i17;
        Modifier modifierNestedScroll;
        int i18;
        final boolean z4;
        boolean z5;
        ComposerImpl composerImpl;
        final SheetState sheetState3;
        final Function2 function25;
        final boolean z6;
        final Modifier modifier3;
        final float f7;
        final Function2 function26;
        final Shape shape3;
        final long j5;
        final long j6;
        final float f8;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        int i19 = 16;
        composerImpl2.startRestartGroup(833345609);
        int i20 = 4;
        if ((Integer.MIN_VALUE & i3) != 0) {
            i4 = i | 6;
        } else if ((i & 6) == 0) {
            i4 = (composerImpl2.changed(boxScope) ? 4 : 2) | i;
        } else {
            i4 = i;
        }
        if ((i3 & 1) != 0) {
            i4 |= 48;
        } else if ((i & 48) == 0) {
            i4 |= (i & 64) == 0 ? composerImpl2.changed(animatable) : composerImpl2.changedInstance(animatable) ? 32 : 16;
        }
        if ((i3 & 2) != 0) {
            i4 |= 384;
            i5 = 32;
        } else {
            i5 = 32;
            if ((i & 384) == 0) {
                i4 |= composerImpl2.changedInstance(coroutineScope) ? 256 : 128;
            }
        }
        if ((i3 & 4) != 0) {
            i4 |= 3072;
            z2 = true;
        } else {
            z2 = true;
            if ((i & 3072) == 0) {
                function02 = function0;
                i4 |= composerImpl2.changedInstance(function02) ? 2048 : 1024;
            }
            if ((i3 & 8) == 0) {
                i4 |= 24576;
            } else if ((i & 24576) == 0) {
                i4 |= composerImpl2.changedInstance(function1) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
            }
            i6 = i3 & 16;
            if (i6 == 0) {
                i4 |= 196608;
                modifier2 = modifier;
            } else {
                modifier2 = modifier;
                if ((i & 196608) == 0) {
                    i4 |= composerImpl2.changed(modifier2) ? 131072 : 65536;
                }
            }
            if ((i & 1572864) == 0) {
                i4 |= ((i3 & 32) == 0 && composerImpl2.changed(sheetState)) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
            }
            i7 = i3 & 64;
            if (i7 == 0) {
                i4 |= 12582912;
                f3 = f;
            } else {
                f3 = f;
                if ((i & 12582912) == 0) {
                    i4 |= composerImpl2.changed(f3) ? 8388608 : 4194304;
                }
            }
            i8 = i3 & 128;
            if (i8 == 0) {
                i4 |= 100663296;
                z3 = z;
            } else {
                z3 = z;
                if ((i & 100663296) == 0) {
                    i4 |= composerImpl2.changed(z3) ? 67108864 : 33554432;
                }
            }
            if ((i & 805306368) == 0) {
                i4 |= ((i3 & 256) == 0 && composerImpl2.changed(shape)) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
            }
            if ((i2 & 6) != 0) {
                if ((i3 & 512) == 0) {
                    i9 = i6;
                    if (!composerImpl2.changed(j)) {
                    }
                    i10 = i2 | i20;
                } else {
                    i9 = i6;
                }
                i20 = 2;
                i10 = i2 | i20;
            } else {
                i9 = i6;
                i10 = i2;
            }
            if ((i2 & 48) != 0) {
                i11 = i9;
                if ((i3 & 1024) == 0 && composerImpl2.changed(j2)) {
                    i19 = i5;
                }
                i10 |= i19;
            } else {
                i11 = i9;
            }
            int i21 = i10;
            i12 = i3 & 2048;
            if (i12 == 0) {
                i21 |= 384;
            } else {
                if ((i2 & 384) == 0) {
                    f4 = f2;
                    i21 |= composerImpl2.changed(f4) ? 256 : 128;
                }
                i13 = i3 & 4096;
                if (i13 != 0) {
                    i14 = i21 | 3072;
                } else {
                    i14 = i21;
                    if ((i2 & 3072) == 0) {
                        i14 |= composerImpl2.changedInstance(function2) ? 2048 : 1024;
                    }
                }
                if ((i2 & 24576) == 0) {
                    i14 |= ((i3 & 8192) == 0 && composerImpl2.changedInstance(function22)) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
                    if ((i2 & 196608) == 0) {
                        i14 |= composerImpl2.changedInstance(function3) ? 131072 : 65536;
                    }
                    if ((306783379 & i4) != 306783378 && (i14 & 74899) == 74898 && composerImpl2.getSkipping()) {
                        composerImpl2.skipToGroupEnd();
                        j5 = j;
                        function25 = function2;
                        function26 = function22;
                        composerImpl = composerImpl2;
                        f8 = f4;
                        z6 = z3;
                        modifier3 = modifier2;
                        f7 = f3;
                        sheetState3 = sheetState;
                        shape3 = shape;
                        j6 = j2;
                    } else {
                        composerImpl2.startDefaults();
                        if ((i & 1) != 0 || composerImpl2.getDefaultsInvalid()) {
                            if (i11 != 0) {
                                modifier2 = Modifier.Companion;
                            }
                            if ((i3 & 32) == 0) {
                                sheetStateRememberModalBottomSheetState = rememberModalBottomSheetState(composerImpl2);
                                i4 &= -3670017;
                            } else {
                                sheetStateRememberModalBottomSheetState = sheetState;
                            }
                            if (i7 != 0) {
                                BottomSheetDefaults.INSTANCE.getClass();
                                f3 = BottomSheetDefaults.SheetMaxWidth;
                            }
                            if (i8 != 0) {
                                z3 = z2;
                            }
                            if ((256 & i3) == 0) {
                                BottomSheetDefaults.INSTANCE.getClass();
                                expandedShape = BottomSheetDefaults.getExpandedShape(composerImpl2);
                                i4 &= -1879048193;
                            } else {
                                expandedShape = shape;
                            }
                            if ((i3 & 512) == 0) {
                                BottomSheetDefaults.INSTANCE.getClass();
                                i14 &= -15;
                                i15 = i12;
                                containerColor = BottomSheetDefaults.getContainerColor(composerImpl2);
                            } else {
                                i15 = i12;
                                containerColor = j;
                            }
                            SheetState sheetState4 = sheetStateRememberModalBottomSheetState;
                            if ((1024 & i3) == 0) {
                                jM259contentColorForek8zF_U = ColorSchemeKt.m259contentColorForek8zF_U(containerColor, composerImpl2);
                                i14 &= -113;
                            } else {
                                jM259contentColorForek8zF_U = j2;
                            }
                            if (i15 == 0) {
                                BottomSheetDefaults.INSTANCE.getClass();
                                f5 = BottomSheetDefaults.Elevation;
                            } else {
                                f5 = f2;
                            }
                            if (i13 == 0) {
                                ComposableSingletons$ModalBottomSheetKt.INSTANCE.getClass();
                                function23 = ComposableSingletons$ModalBottomSheetKt.f8lambda3;
                            } else {
                                function23 = function2;
                            }
                            if ((8192 & i3) == 0) {
                                function24 = new Function2() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$1
                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj, Object obj2) {
                                        ((Number) obj2).intValue();
                                        ComposerImpl composerImpl3 = (ComposerImpl) ((Composer) obj);
                                        composerImpl3.startReplaceGroup(1381594270);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.ModalBottomSheetContent.<anonymous> (ModalBottomSheet.kt:272)");
                                        }
                                        BottomSheetDefaults.INSTANCE.getClass();
                                        WindowInsets windowInsets = BottomSheetDefaults.getWindowInsets(composerImpl3);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl3.end(false);
                                        return windowInsets;
                                    }
                                };
                                int i22 = i14 & (-57345);
                                shape2 = expandedShape;
                                i16 = i22;
                            } else {
                                int i23 = i14;
                                shape2 = expandedShape;
                                i16 = i23;
                                function24 = function22;
                            }
                            f6 = f5;
                            j3 = containerColor;
                            sheetState2 = sheetState4;
                            j4 = jM259contentColorForek8zF_U;
                            i17 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                        } else {
                            composerImpl2.skipToGroupEnd();
                            if ((i3 & 32) != 0) {
                                i4 &= -3670017;
                            }
                            if ((256 & i3) != 0) {
                                i4 &= -1879048193;
                            }
                            if ((i3 & 512) != 0) {
                                i14 &= -15;
                            }
                            if ((1024 & i3) != 0) {
                                i14 &= -113;
                            }
                            if ((8192 & i3) != 0) {
                                i14 &= -57345;
                            }
                            sheetState2 = sheetState;
                            j3 = j;
                            function23 = function2;
                            function24 = function22;
                            f6 = f4;
                            i16 = i14;
                            i17 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                            shape2 = shape;
                            j4 = j2;
                        }
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.ModalBottomSheetContent (ModalBottomSheet.kt:274)");
                        }
                        int i24 = Strings.$r8$clinit;
                        final String strM322getString2EP1pXo = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_bottom_sheet_pane_title, composerImpl2);
                        Alignment.Companion.getClass();
                        int i25 = i16;
                        Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(SizeKt.m146widthInVpY3zN4$default(boxScope.align(modifier2, Alignment.Companion.TopCenter), 0.0f, f3, 1), 1.0f);
                        Composer.Companion companion = Composer.Companion;
                        if (z3) {
                            composerImpl2.startReplaceGroup(1237957879);
                            composerImpl2.end(false);
                            modifierNestedScroll = Modifier.Companion;
                        } else {
                            composerImpl2.startReplaceGroup(1237943399);
                            Modifier.Companion companion2 = Modifier.Companion;
                            boolean z7 = (((i4 & 3670016) ^ 1572864) > 1048576 && composerImpl2.changed(sheetState2)) || (i4 & 1572864) == 1048576;
                            Object objRememberedValue = composerImpl2.rememberedValue();
                            if (!z7) {
                                companion.getClass();
                                if (objRememberedValue == Composer.Companion.Empty) {
                                    final Orientation orientation = Orientation.Vertical;
                                    float f9 = SheetDefaultsKt.DragHandleVerticalPadding;
                                    Object obj = new NestedScrollConnection() { // from class: androidx.compose.material3.SheetDefaultsKt$ConsumeSwipeWithinBottomSheetBoundsNestedScrollConnection$1
                                        @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
                                        /* renamed from: onPostFling-RZ2iAVY */
                                        public final Object mo78onPostFlingRZ2iAVY(long j7, long j8, Continuation continuation) {
                                            function1.mo781invoke(new Float(orientation == Orientation.Horizontal ? Velocity.m880getXimpl(j8) : Velocity.m881getYimpl(j8)));
                                            return Velocity.m878boximpl(j8);
                                        }

                                        @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
                                        /* renamed from: onPostScroll-DzOQY0M */
                                        public final long mo79onPostScrollDzOQY0M(int i26, long j7, long j8) {
                                            NestedScrollSource.Companion.getClass();
                                            if (i26 != NestedScrollSource.UserInput) {
                                                Offset.Companion.getClass();
                                                return 0L;
                                            }
                                            AnchoredDraggableState anchoredDraggableState = sheetState2.anchoredDraggableState;
                                            Orientation orientation2 = Orientation.Horizontal;
                                            Orientation orientation3 = orientation;
                                            float fDispatchRawDelta = anchoredDraggableState.dispatchRawDelta(orientation3 == orientation2 ? Offset.m400getXimpl(j8) : Offset.m401getYimpl(j8));
                                            float f10 = orientation3 == orientation2 ? fDispatchRawDelta : 0.0f;
                                            if (orientation3 != Orientation.Vertical) {
                                                fDispatchRawDelta = 0.0f;
                                            }
                                            return OffsetKt.Offset(f10, fDispatchRawDelta);
                                        }

                                        @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
                                        /* renamed from: onPreFling-QWom1Mo, reason: not valid java name */
                                        public final Object mo289onPreFlingQWom1Mo(long j7, Continuation continuation) {
                                            float fM880getXimpl = orientation == Orientation.Horizontal ? Velocity.m880getXimpl(j7) : Velocity.m881getYimpl(j7);
                                            SheetState sheetState5 = sheetState2;
                                            float fRequireOffset = sheetState5.anchoredDraggableState.requireOffset();
                                            float fMinAnchor = sheetState5.anchoredDraggableState.getAnchors().minAnchor();
                                            if (fM880getXimpl >= 0.0f || fRequireOffset <= fMinAnchor) {
                                                Velocity.Companion.getClass();
                                                j7 = 0;
                                            } else {
                                                function1.mo781invoke(new Float(fM880getXimpl));
                                            }
                                            return Velocity.m878boximpl(j7);
                                        }

                                        @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
                                        /* renamed from: onPreScroll-OzD1aCk */
                                        public final long mo176onPreScrollOzD1aCk(int i26, long j7) {
                                            Orientation orientation2 = Orientation.Horizontal;
                                            Orientation orientation3 = orientation;
                                            float fM400getXimpl = orientation3 == orientation2 ? Offset.m400getXimpl(j7) : Offset.m401getYimpl(j7);
                                            if (fM400getXimpl < 0.0f) {
                                                NestedScrollSource.Companion.getClass();
                                                if (i26 == NestedScrollSource.UserInput) {
                                                    float fDispatchRawDelta = sheetState2.anchoredDraggableState.dispatchRawDelta(fM400getXimpl);
                                                    return OffsetKt.Offset(orientation3 == orientation2 ? fDispatchRawDelta : 0.0f, orientation3 == Orientation.Vertical ? fDispatchRawDelta : 0.0f);
                                                }
                                            }
                                            Offset.Companion.getClass();
                                            return 0L;
                                        }
                                    };
                                    composerImpl2.updateRememberedValue(obj);
                                    objRememberedValue = obj;
                                }
                                modifierNestedScroll = NestedScrollModifierKt.nestedScroll(companion2, (NestedScrollConnection) objRememberedValue, null);
                                composerImpl2.end(false);
                            }
                        }
                        Modifier modifierThen = modifierFillMaxWidth.then(modifierNestedScroll);
                        AnchoredDraggableState anchoredDraggableState = sheetState2.anchoredDraggableState;
                        Orientation orientation2 = Orientation.Vertical;
                        i18 = (i4 & 3670016) ^ 1572864;
                        final Function2 function27 = function23;
                        if (i18 > 1048576 || !composerImpl2.changed(sheetState2)) {
                            z4 = z3;
                            if ((i4 & 1572864) != 1048576) {
                                z5 = false;
                            }
                            Object objRememberedValue2 = composerImpl2.rememberedValue();
                            if (!z5) {
                                companion.getClass();
                                if (objRememberedValue2 == Composer.Companion.Empty) {
                                    objRememberedValue2 = new Function2() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$3$1

                                        public abstract /* synthetic */ class WhenMappings {
                                            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                                            static {
                                                int[] iArr = new int[SheetValue.values().length];
                                                try {
                                                    iArr[SheetValue.Hidden.ordinal()] = 1;
                                                } catch (NoSuchFieldError unused) {
                                                }
                                                try {
                                                    iArr[SheetValue.PartiallyExpanded.ordinal()] = 2;
                                                } catch (NoSuchFieldError unused2) {
                                                }
                                                try {
                                                    iArr[SheetValue.Expanded.ordinal()] = 3;
                                                } catch (NoSuchFieldError unused3) {
                                                }
                                                $EnumSwitchMapping$0 = iArr;
                                            }
                                        }

                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj2, Object obj3) {
                                            SheetValue sheetValue;
                                            final long j7 = ((IntSize) obj2).packedValue;
                                            final float fM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(((Constraints) obj3).value);
                                            final SheetState sheetState5 = sheetState2;
                                            DraggableAnchors DraggableAnchors = AnchoredDraggableKt.DraggableAnchors(new Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$3$1$newAnchors$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj4) {
                                                    DraggableAnchorsConfig draggableAnchorsConfig = (DraggableAnchorsConfig) obj4;
                                                    draggableAnchorsConfig.anchors.put(SheetValue.Hidden, Float.valueOf(fM822getMaxHeightimpl));
                                                    long j8 = j7;
                                                    IntSize.Companion companion3 = IntSize.Companion;
                                                    float f10 = (int) (j8 & 4294967295L);
                                                    float f11 = fM822getMaxHeightimpl;
                                                    if (f10 > f11 / 2 && !sheetState5.skipPartiallyExpanded) {
                                                        draggableAnchorsConfig.anchors.put(SheetValue.PartiallyExpanded, Float.valueOf(f11 / 2.0f));
                                                    }
                                                    int i26 = (int) (j7 & 4294967295L);
                                                    if (i26 != 0) {
                                                        draggableAnchorsConfig.anchors.put(SheetValue.Expanded, Float.valueOf(Math.max(0.0f, fM822getMaxHeightimpl - i26)));
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            });
                                            int i26 = WhenMappings.$EnumSwitchMapping$0[((SheetValue) sheetState2.anchoredDraggableState.targetValue$delegate.getValue()).ordinal()];
                                            if (i26 == 1) {
                                                sheetValue = SheetValue.Hidden;
                                            } else if (i26 == 2) {
                                                sheetValue = SheetValue.PartiallyExpanded;
                                                if (!DraggableAnchors.hasAnchorFor(sheetValue)) {
                                                    sheetValue = SheetValue.Expanded;
                                                    if (!DraggableAnchors.hasAnchorFor(sheetValue)) {
                                                        sheetValue = SheetValue.Hidden;
                                                    }
                                                }
                                            } else {
                                                if (i26 != 3) {
                                                    throw new NoWhenBranchMatchedException();
                                                }
                                                sheetValue = SheetValue.Expanded;
                                                if (!DraggableAnchors.hasAnchorFor(sheetValue)) {
                                                    sheetValue = SheetValue.Hidden;
                                                }
                                            }
                                            return new Pair(DraggableAnchors, sheetValue);
                                        }
                                    };
                                    composerImpl2.updateRememberedValue(objRememberedValue2);
                                }
                                Modifier modifierDraggableAnchors = AnchoredDraggableKt.draggableAnchors(modifierThen, anchoredDraggableState, orientation2, (Function2) objRememberedValue2);
                                AnchoredDraggableState$draggableState$1 anchoredDraggableState$draggableState$1 = sheetState2.anchoredDraggableState.draggableState;
                                boolean z8 = z4 && sheetState2.isVisible();
                                boolean z9 = ((SnapshotMutableStateImpl) sheetState2.anchoredDraggableState.dragTarget$delegate).getValue() != null;
                                boolean z10 = (57344 & i4) == i17;
                                Object objRememberedValue3 = composerImpl2.rememberedValue();
                                if (!z10) {
                                    companion.getClass();
                                    if (objRememberedValue3 == Composer.Companion.Empty) {
                                        objRememberedValue3 = new ModalBottomSheetKt$ModalBottomSheetContent$4$1(function1, null);
                                        composerImpl2.updateRememberedValue(objRememberedValue3);
                                    }
                                    Modifier modifierDraggable$default = DraggableKt.draggable$default(modifierDraggableAnchors, anchoredDraggableState$draggableState$1, orientation2, z8, null, z9, null, (Function3) objRememberedValue3, false, 168);
                                    boolean zChanged = composerImpl2.changed(strM322getString2EP1pXo);
                                    Object objRememberedValue4 = composerImpl2.rememberedValue();
                                    if (!zChanged) {
                                        companion.getClass();
                                        if (objRememberedValue4 == Composer.Companion.Empty) {
                                            objRememberedValue4 = new Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$5$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj2) {
                                                    SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj2;
                                                    SemanticsPropertiesKt.setPaneTitle(semanticsPropertyReceiver, strM322getString2EP1pXo);
                                                    SemanticsProperties.INSTANCE.getClass();
                                                    SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.TraversalIndex;
                                                    KProperty kProperty = SemanticsPropertiesKt.$$delegatedProperties[9];
                                                    semanticsPropertyKey.setValue(semanticsPropertyReceiver, Float.valueOf(0.0f));
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl2.updateRememberedValue(objRememberedValue4);
                                        }
                                        Modifier modifierSemantics = SemanticsModifierKt.semantics(modifierDraggable$default, false, (Function1) objRememberedValue4);
                                        boolean z11 = ((i4 & 112) == 32 || ((i4 & 64) != 0 && composerImpl2.changedInstance(animatable))) | ((i18 > 1048576 && composerImpl2.changed(sheetState2)) || (i4 & 1572864) == 1048576);
                                        Object objRememberedValue5 = composerImpl2.rememberedValue();
                                        if (!z11) {
                                            companion.getClass();
                                            if (objRememberedValue5 == Composer.Companion.Empty) {
                                                objRememberedValue5 = new Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$6$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(1);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj2) {
                                                        GraphicsLayerScope graphicsLayerScope = (GraphicsLayerScope) obj2;
                                                        float floatValue = ((SnapshotMutableFloatStateImpl) sheetState2.anchoredDraggableState.offset$delegate).getFloatValue();
                                                        ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) graphicsLayerScope;
                                                        float fM417getHeightimpl = Size.m417getHeightimpl(reusableGraphicsLayerScope.size);
                                                        if (!Float.isNaN(floatValue) && !Float.isNaN(fM417getHeightimpl) && fM417getHeightimpl != 0.0f) {
                                                            float fFloatValue = ((Number) animatable.internalState.getValue()).floatValue();
                                                            reusableGraphicsLayerScope.setScaleX(ModalBottomSheetKt.access$calculatePredictiveBackScaleX(graphicsLayerScope, fFloatValue));
                                                            reusableGraphicsLayerScope.setScaleY(ModalBottomSheetKt.access$calculatePredictiveBackScaleY(graphicsLayerScope, fFloatValue));
                                                            reusableGraphicsLayerScope.m498setTransformOrigin__ExYCQ(TransformOriginKt.TransformOrigin(0.5f, (floatValue + fM417getHeightimpl) / fM417getHeightimpl));
                                                        }
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl2.updateRememberedValue(objRememberedValue5);
                                            }
                                            final SheetState sheetState5 = sheetState2;
                                            final Function0 function03 = function02;
                                            final Function2 function28 = function24;
                                            boolean z12 = z4;
                                            int i26 = i25 << 6;
                                            composerImpl = composerImpl2;
                                            SurfaceKt.m304SurfaceT9BRK9s(GraphicsLayerModifierKt.graphicsLayer(GraphicsLayerModifierKt.graphicsLayer(modifierSemantics, (Function1) objRememberedValue5), new Function1() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$verticalScaleUp$1
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj2) {
                                                    float fM417getHeightimpl;
                                                    GraphicsLayerScope graphicsLayerScope = (GraphicsLayerScope) obj2;
                                                    float floatValue = ((SnapshotMutableFloatStateImpl) sheetState2.anchoredDraggableState.offset$delegate).getFloatValue();
                                                    float fMinAnchor = sheetState2.anchoredDraggableState.getAnchors().minAnchor();
                                                    float f10 = floatValue < fMinAnchor ? fMinAnchor - floatValue : 0.0f;
                                                    if (f10 > 0.0f) {
                                                        ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) graphicsLayerScope;
                                                        fM417getHeightimpl = (Size.m417getHeightimpl(reusableGraphicsLayerScope.size) + f10) / Size.m417getHeightimpl(reusableGraphicsLayerScope.size);
                                                    } else {
                                                        fM417getHeightimpl = 1.0f;
                                                    }
                                                    ReusableGraphicsLayerScope reusableGraphicsLayerScope2 = (ReusableGraphicsLayerScope) graphicsLayerScope;
                                                    reusableGraphicsLayerScope2.setScaleY(fM417getHeightimpl);
                                                    reusableGraphicsLayerScope2.m498setTransformOrigin__ExYCQ(TransformOriginKt.TransformOrigin(0.5f, 0.0f));
                                                    return Unit.INSTANCE;
                                                }
                                            }), shape2, j3, j4, f6, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-479877532, new Function2() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$7
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(2);
                                                }

                                                /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
                                                /* JADX WARN: Removed duplicated region for block: B:31:0x00ea  */
                                                /* JADX WARN: Removed duplicated region for block: B:40:0x0172  */
                                                /* JADX WARN: Removed duplicated region for block: B:42:0x0179  */
                                                /* JADX WARN: Removed duplicated region for block: B:55:0x01e4  */
                                                /* JADX WARN: Removed duplicated region for block: B:58:0x0208  */
                                                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                                                @Override // kotlin.jvm.functions.Function2
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj2, Object obj3) throws Resources.NotFoundException {
                                                    Composer.Companion companion3;
                                                    Function3 function32;
                                                    ColumnScopeInstance columnScopeInstance;
                                                    Function2 function29;
                                                    boolean zChanged2;
                                                    Object obj4;
                                                    Function2 function210;
                                                    Function0 function04;
                                                    Function2 function211;
                                                    Composer composer2 = (Composer) obj2;
                                                    if ((((Number) obj3).intValue() & 3) == 2) {
                                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                        if (composerImpl3.getSkipping()) {
                                                            composerImpl3.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("androidx.compose.material3.ModalBottomSheetContent.<anonymous> (ModalBottomSheet.kt:360)");
                                                            }
                                                            Modifier.Companion companion4 = Modifier.Companion;
                                                            Modifier modifierWindowInsetsPadding = WindowInsetsPaddingKt.windowInsetsPadding(SizeKt.fillMaxWidth(companion4, 1.0f), (WindowInsets) function28.invoke(composer2, 0));
                                                            ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                            boolean zChangedInstance = composerImpl4.changedInstance(animatable);
                                                            final Animatable<Float, AnimationVector1D> animatable2 = animatable;
                                                            Object objRememberedValue6 = composerImpl4.rememberedValue();
                                                            Composer.Companion companion5 = Composer.Companion;
                                                            if (!zChangedInstance) {
                                                                companion5.getClass();
                                                                if (objRememberedValue6 == Composer.Companion.Empty) {
                                                                    objRememberedValue6 = new Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$7$1$1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(1);
                                                                        }

                                                                        @Override // kotlin.jvm.functions.Function1
                                                                        /* renamed from: invoke */
                                                                        public final Object mo781invoke(Object obj5) {
                                                                            GraphicsLayerScope graphicsLayerScope = (GraphicsLayerScope) obj5;
                                                                            float fFloatValue = ((Number) animatable2.internalState.getValue()).floatValue();
                                                                            float fAccess$calculatePredictiveBackScaleX = ModalBottomSheetKt.access$calculatePredictiveBackScaleX(graphicsLayerScope, fFloatValue);
                                                                            float fAccess$calculatePredictiveBackScaleY = ModalBottomSheetKt.access$calculatePredictiveBackScaleY(graphicsLayerScope, fFloatValue);
                                                                            ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) graphicsLayerScope;
                                                                            reusableGraphicsLayerScope.setScaleY(fAccess$calculatePredictiveBackScaleY == 0.0f ? 1.0f : fAccess$calculatePredictiveBackScaleX / fAccess$calculatePredictiveBackScaleY);
                                                                            reusableGraphicsLayerScope.m498setTransformOrigin__ExYCQ(ModalBottomSheetKt.PredictiveBackChildTransformOrigin);
                                                                            return Unit.INSTANCE;
                                                                        }
                                                                    };
                                                                    composerImpl4.updateRememberedValue(objRememberedValue6);
                                                                }
                                                                Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifierWindowInsetsPadding, (Function1) objRememberedValue6);
                                                                final SheetState sheetState6 = sheetState5;
                                                                Modifier modifierGraphicsLayer2 = GraphicsLayerModifierKt.graphicsLayer(modifierGraphicsLayer, new Function1() { // from class: androidx.compose.material3.BottomSheetScaffoldKt$verticalScaleDown$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    /* renamed from: invoke */
                                                                    public final Object mo781invoke(Object obj5) {
                                                                        float fM417getHeightimpl;
                                                                        GraphicsLayerScope graphicsLayerScope = (GraphicsLayerScope) obj5;
                                                                        float floatValue = ((SnapshotMutableFloatStateImpl) sheetState6.anchoredDraggableState.offset$delegate).getFloatValue();
                                                                        float fMinAnchor = sheetState6.anchoredDraggableState.getAnchors().minAnchor();
                                                                        float f10 = floatValue < fMinAnchor ? fMinAnchor - floatValue : 0.0f;
                                                                        if (f10 > 0.0f) {
                                                                            ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) graphicsLayerScope;
                                                                            fM417getHeightimpl = 1 / ((Size.m417getHeightimpl(reusableGraphicsLayerScope.size) + f10) / Size.m417getHeightimpl(reusableGraphicsLayerScope.size));
                                                                        } else {
                                                                            fM417getHeightimpl = 1.0f;
                                                                        }
                                                                        ReusableGraphicsLayerScope reusableGraphicsLayerScope2 = (ReusableGraphicsLayerScope) graphicsLayerScope;
                                                                        reusableGraphicsLayerScope2.setScaleY(fM417getHeightimpl);
                                                                        reusableGraphicsLayerScope2.m498setTransformOrigin__ExYCQ(TransformOriginKt.TransformOrigin(0.5f, 0.0f));
                                                                        return Unit.INSTANCE;
                                                                    }
                                                                });
                                                                Function2 function212 = function27;
                                                                final SheetState sheetState7 = sheetState5;
                                                                final Function0 function05 = function03;
                                                                final CoroutineScope coroutineScope2 = coroutineScope;
                                                                final boolean z13 = z4;
                                                                Function3 function33 = function3;
                                                                Arrangement.INSTANCE.getClass();
                                                                Arrangement$Top$1 arrangement$Top$1 = Arrangement.Top;
                                                                Alignment.Companion.getClass();
                                                                ColumnMeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(arrangement$Top$1, Alignment.Companion.Start, composerImpl4, 0);
                                                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                                                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl4.currentCompositionLocalScope();
                                                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl4, modifierGraphicsLayer2);
                                                                ComposeUiNode.Companion.getClass();
                                                                Function0 function06 = ComposeUiNode.Companion.Constructor;
                                                                if (composerImpl4.applier == null) {
                                                                    ComposablesKt.invalidApplier();
                                                                    throw null;
                                                                }
                                                                composerImpl4.startReusableNode();
                                                                if (composerImpl4.inserting) {
                                                                    composerImpl4.createNode(function06);
                                                                } else {
                                                                    composerImpl4.useNode();
                                                                }
                                                                Function2 function213 = ComposeUiNode.Companion.SetMeasurePolicy;
                                                                Updater.m337setimpl(composerImpl4, columnMeasurePolicy, function213);
                                                                Function2 function214 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                                                Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope, function214);
                                                                Function2 function215 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                if (composerImpl4.inserting) {
                                                                    companion3 = companion5;
                                                                } else {
                                                                    companion3 = companion5;
                                                                    if (!Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                    }
                                                                    Function2 function216 = ComposeUiNode.Companion.SetModifier;
                                                                    Updater.m337setimpl(composerImpl4, modifierMaterializeModifier, function216);
                                                                    ColumnScopeInstance columnScopeInstance2 = ColumnScopeInstance.INSTANCE;
                                                                    if (function212 == null) {
                                                                        composerImpl4.startReplaceGroup(809904841);
                                                                        int i27 = Strings.$r8$clinit;
                                                                        final String strM322getString2EP1pXo2 = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_bottom_sheet_collapse_description, composerImpl4);
                                                                        final String strM322getString2EP1pXo3 = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_bottom_sheet_dismiss_description, composerImpl4);
                                                                        final String strM322getString2EP1pXo4 = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_bottom_sheet_expand_description, composerImpl4);
                                                                        Modifier modifierAlign = columnScopeInstance2.align(companion4, Alignment.Companion.CenterHorizontally);
                                                                        boolean zChanged3 = composerImpl4.changed(sheetState7) | composerImpl4.changed(function05) | composerImpl4.changedInstance(coroutineScope2);
                                                                        Object objRememberedValue7 = composerImpl4.rememberedValue();
                                                                        if (zChanged3) {
                                                                            function29 = function213;
                                                                        } else {
                                                                            companion3.getClass();
                                                                            function29 = function213;
                                                                            if (objRememberedValue7 == Composer.Companion.Empty) {
                                                                            }
                                                                            function32 = function33;
                                                                            columnScopeInstance = columnScopeInstance2;
                                                                            Modifier modifierM35clickableXHw0xAI$default = ClickableKt.m35clickableXHw0xAI$default(modifierAlign, false, null, (Function0) objRememberedValue7, 7);
                                                                            zChanged2 = composerImpl4.changed(z13) | composerImpl4.changed(sheetState7) | composerImpl4.changed(strM322getString2EP1pXo3) | composerImpl4.changed(function05) | composerImpl4.changed(strM322getString2EP1pXo4) | composerImpl4.changedInstance(coroutineScope2) | composerImpl4.changed(strM322getString2EP1pXo2);
                                                                            Object objRememberedValue8 = composerImpl4.rememberedValue();
                                                                            if (zChanged2) {
                                                                                companion3.getClass();
                                                                                if (objRememberedValue8 == Composer.Companion.Empty) {
                                                                                    function210 = function215;
                                                                                    function04 = function06;
                                                                                    function211 = function29;
                                                                                    obj4 = new Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$7$2$2$1
                                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                        {
                                                                                            super(1);
                                                                                        }

                                                                                        @Override // kotlin.jvm.functions.Function1
                                                                                        /* renamed from: invoke */
                                                                                        public final Object mo781invoke(Object obj5) {
                                                                                            SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj5;
                                                                                            if (z13) {
                                                                                                final SheetState sheetState8 = sheetState7;
                                                                                                String str = strM322getString2EP1pXo3;
                                                                                                String str2 = strM322getString2EP1pXo4;
                                                                                                String str3 = strM322getString2EP1pXo2;
                                                                                                final Function0 function07 = function05;
                                                                                                final CoroutineScope coroutineScope3 = coroutineScope2;
                                                                                                Function0 function08 = new Function0() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$7$2$2$1$1$1
                                                                                                    {
                                                                                                        super(0);
                                                                                                    }

                                                                                                    @Override // kotlin.jvm.functions.Function0
                                                                                                    public final Object invoke() {
                                                                                                        function07.invoke();
                                                                                                        return Boolean.TRUE;
                                                                                                    }
                                                                                                };
                                                                                                KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                                                                                                SemanticsActions semanticsActions = SemanticsActions.INSTANCE;
                                                                                                semanticsActions.getClass();
                                                                                                SemanticsConfiguration semanticsConfiguration = (SemanticsConfiguration) semanticsPropertyReceiver;
                                                                                                semanticsConfiguration.set(SemanticsActions.Dismiss, new AccessibilityAction(str, function08));
                                                                                                SheetValue sheetValue = (SheetValue) ((SnapshotMutableStateImpl) sheetState8.anchoredDraggableState.currentValue$delegate).getValue();
                                                                                                SheetValue sheetValue2 = SheetValue.PartiallyExpanded;
                                                                                                if (sheetValue == sheetValue2) {
                                                                                                    Function0 function09 = new Function0() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$7$2$2$1$1$2

                                                                                                        /* renamed from: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$7$2$2$1$1$2$1, reason: invalid class name */
                                                                                                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                                                                                            final /* synthetic */ SheetState $sheetState;
                                                                                                            int label;

                                                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                                            public AnonymousClass1(SheetState sheetState, Continuation continuation) {
                                                                                                                super(2, continuation);
                                                                                                                this.$sheetState = sheetState;
                                                                                                            }

                                                                                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                                                            public final Continuation create(Object obj, Continuation continuation) {
                                                                                                                return new AnonymousClass1(this.$sheetState, continuation);
                                                                                                            }

                                                                                                            @Override // kotlin.jvm.functions.Function2
                                                                                                            public final Object invoke(Object obj, Object obj2) {
                                                                                                                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                                                                                            }

                                                                                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                                                            public final Object invokeSuspend(Object obj) {
                                                                                                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                                                                                int i = this.label;
                                                                                                                if (i == 0) {
                                                                                                                    ResultKt.throwOnFailure(obj);
                                                                                                                    SheetState sheetState = this.$sheetState;
                                                                                                                    this.label = 1;
                                                                                                                    if (sheetState.expand(this) == coroutineSingletons) {
                                                                                                                        return coroutineSingletons;
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    if (i != 1) {
                                                                                                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                                                                                    }
                                                                                                                    ResultKt.throwOnFailure(obj);
                                                                                                                }
                                                                                                                return Unit.INSTANCE;
                                                                                                            }
                                                                                                        }

                                                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                                        {
                                                                                                            super(0);
                                                                                                        }

                                                                                                        @Override // kotlin.jvm.functions.Function0
                                                                                                        public final Object invoke() {
                                                                                                            if (((Boolean) sheetState8.anchoredDraggableState.confirmValueChange.mo781invoke(SheetValue.Expanded)).booleanValue()) {
                                                                                                                BuildersKt.launch$default(coroutineScope3, null, null, new AnonymousClass1(sheetState8, null), 3);
                                                                                                            }
                                                                                                            return Boolean.TRUE;
                                                                                                        }
                                                                                                    };
                                                                                                    semanticsActions.getClass();
                                                                                                    semanticsConfiguration.set(SemanticsActions.Expand, new AccessibilityAction(str2, function09));
                                                                                                } else if (sheetState8.anchoredDraggableState.getAnchors().hasAnchorFor(sheetValue2)) {
                                                                                                    Function0 function010 = new Function0() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$7$2$2$1$1$3

                                                                                                        /* renamed from: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$7$2$2$1$1$3$1, reason: invalid class name */
                                                                                                        final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                                                                                            final /* synthetic */ SheetState $this_with;
                                                                                                            int label;

                                                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                                            public AnonymousClass1(SheetState sheetState, Continuation continuation) {
                                                                                                                super(2, continuation);
                                                                                                                this.$this_with = sheetState;
                                                                                                            }

                                                                                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                                                            public final Continuation create(Object obj, Continuation continuation) {
                                                                                                                return new AnonymousClass1(this.$this_with, continuation);
                                                                                                            }

                                                                                                            @Override // kotlin.jvm.functions.Function2
                                                                                                            public final Object invoke(Object obj, Object obj2) {
                                                                                                                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                                                                                            }

                                                                                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                                                            public final Object invokeSuspend(Object obj) {
                                                                                                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                                                                                int i = this.label;
                                                                                                                if (i == 0) {
                                                                                                                    ResultKt.throwOnFailure(obj);
                                                                                                                    SheetState sheetState = this.$this_with;
                                                                                                                    this.label = 1;
                                                                                                                    if (sheetState.partialExpand(this) == coroutineSingletons) {
                                                                                                                        return coroutineSingletons;
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    if (i != 1) {
                                                                                                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                                                                                    }
                                                                                                                    ResultKt.throwOnFailure(obj);
                                                                                                                }
                                                                                                                return Unit.INSTANCE;
                                                                                                            }
                                                                                                        }

                                                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                                        {
                                                                                                            super(0);
                                                                                                        }

                                                                                                        @Override // kotlin.jvm.functions.Function0
                                                                                                        public final Object invoke() {
                                                                                                            if (((Boolean) sheetState8.anchoredDraggableState.confirmValueChange.mo781invoke(SheetValue.PartiallyExpanded)).booleanValue()) {
                                                                                                                BuildersKt.launch$default(coroutineScope3, null, null, new AnonymousClass1(sheetState8, null), 3);
                                                                                                            }
                                                                                                            return Boolean.TRUE;
                                                                                                        }
                                                                                                    };
                                                                                                    semanticsActions.getClass();
                                                                                                    semanticsConfiguration.set(SemanticsActions.Collapse, new AccessibilityAction(str3, function010));
                                                                                                }
                                                                                            }
                                                                                            return Unit.INSTANCE;
                                                                                        }
                                                                                    };
                                                                                    composerImpl4.updateRememberedValue(obj4);
                                                                                } else {
                                                                                    obj4 = objRememberedValue8;
                                                                                    function210 = function215;
                                                                                    function04 = function06;
                                                                                    function211 = function29;
                                                                                }
                                                                                Modifier modifierSemantics2 = SemanticsModifierKt.semantics(modifierM35clickableXHw0xAI$default, true, (Function1) obj4);
                                                                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                                                int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerImpl4);
                                                                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl4.currentCompositionLocalScope();
                                                                                Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerImpl4, modifierSemantics2);
                                                                                composerImpl4.startReusableNode();
                                                                                if (composerImpl4.inserting) {
                                                                                    composerImpl4.createNode(function04);
                                                                                } else {
                                                                                    composerImpl4.useNode();
                                                                                }
                                                                                Updater.m337setimpl(composerImpl4, measurePolicyMaybeCachedBoxMeasurePolicy, function211);
                                                                                Updater.m337setimpl(composerImpl4, persistentCompositionLocalMapCurrentCompositionLocalScope2, function214);
                                                                                if (composerImpl4.inserting || !Intrinsics.areEqual(composerImpl4.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                                                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl4, currentCompositeKeyHash2, function210);
                                                                                }
                                                                                Updater.m337setimpl(composerImpl4, modifierMaterializeModifier2, function216);
                                                                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                                                SurfaceKt$Surface$3$$ExternalSyntheticOutline0.m(0, function212, composerImpl4, true, false);
                                                                            }
                                                                        }
                                                                        objRememberedValue7 = new Function0() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$7$2$1$1

                                                                            /* renamed from: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$7$2$1$1$1, reason: invalid class name */
                                                                            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                                                                final /* synthetic */ SheetState $sheetState;
                                                                                int label;

                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                public AnonymousClass1(SheetState sheetState, Continuation continuation) {
                                                                                    super(2, continuation);
                                                                                    this.$sheetState = sheetState;
                                                                                }

                                                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                                public final Continuation create(Object obj, Continuation continuation) {
                                                                                    return new AnonymousClass1(this.$sheetState, continuation);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function2
                                                                                public final Object invoke(Object obj, Object obj2) {
                                                                                    return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                                                                }

                                                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                                public final Object invokeSuspend(Object obj) {
                                                                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                                                    int i = this.label;
                                                                                    if (i == 0) {
                                                                                        ResultKt.throwOnFailure(obj);
                                                                                        SheetState sheetState = this.$sheetState;
                                                                                        this.label = 1;
                                                                                        if (sheetState.expand(this) == coroutineSingletons) {
                                                                                            return coroutineSingletons;
                                                                                        }
                                                                                    } else {
                                                                                        if (i != 1) {
                                                                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                                                        }
                                                                                        ResultKt.throwOnFailure(obj);
                                                                                    }
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            }

                                                                            /* renamed from: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$7$2$1$1$2, reason: invalid class name */
                                                                            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                                                                                final /* synthetic */ SheetState $sheetState;
                                                                                int label;

                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                public AnonymousClass2(SheetState sheetState, Continuation continuation) {
                                                                                    super(2, continuation);
                                                                                    this.$sheetState = sheetState;
                                                                                }

                                                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                                public final Continuation create(Object obj, Continuation continuation) {
                                                                                    return new AnonymousClass2(this.$sheetState, continuation);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function2
                                                                                public final Object invoke(Object obj, Object obj2) {
                                                                                    return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                                                                }

                                                                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                                                public final Object invokeSuspend(Object obj) {
                                                                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                                                    int i = this.label;
                                                                                    if (i == 0) {
                                                                                        ResultKt.throwOnFailure(obj);
                                                                                        SheetState sheetState = this.$sheetState;
                                                                                        this.label = 1;
                                                                                        if (sheetState.show(this) == coroutineSingletons) {
                                                                                            return coroutineSingletons;
                                                                                        }
                                                                                    } else {
                                                                                        if (i != 1) {
                                                                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                                                        }
                                                                                        ResultKt.throwOnFailure(obj);
                                                                                    }
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            }

                                                                            public abstract /* synthetic */ class WhenMappings {
                                                                                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                                                                                static {
                                                                                    int[] iArr = new int[SheetValue.values().length];
                                                                                    try {
                                                                                        iArr[SheetValue.Expanded.ordinal()] = 1;
                                                                                    } catch (NoSuchFieldError unused) {
                                                                                    }
                                                                                    try {
                                                                                        iArr[SheetValue.PartiallyExpanded.ordinal()] = 2;
                                                                                    } catch (NoSuchFieldError unused2) {
                                                                                    }
                                                                                    $EnumSwitchMapping$0 = iArr;
                                                                                }
                                                                            }

                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                            {
                                                                                super(0);
                                                                            }

                                                                            @Override // kotlin.jvm.functions.Function0
                                                                            public final Object invoke() {
                                                                                int i28 = WhenMappings.$EnumSwitchMapping$0[((SheetValue) ((SnapshotMutableStateImpl) sheetState7.anchoredDraggableState.currentValue$delegate).getValue()).ordinal()];
                                                                                if (i28 == 1) {
                                                                                    function05.invoke();
                                                                                } else if (i28 != 2) {
                                                                                    BuildersKt.launch$default(coroutineScope2, null, null, new AnonymousClass2(sheetState7, null), 3);
                                                                                } else {
                                                                                    BuildersKt.launch$default(coroutineScope2, null, null, new AnonymousClass1(sheetState7, null), 3);
                                                                                }
                                                                                return Unit.INSTANCE;
                                                                            }
                                                                        };
                                                                        composerImpl4.updateRememberedValue(objRememberedValue7);
                                                                        function32 = function33;
                                                                        columnScopeInstance = columnScopeInstance2;
                                                                        Modifier modifierM35clickableXHw0xAI$default2 = ClickableKt.m35clickableXHw0xAI$default(modifierAlign, false, null, (Function0) objRememberedValue7, 7);
                                                                        zChanged2 = composerImpl4.changed(z13) | composerImpl4.changed(sheetState7) | composerImpl4.changed(strM322getString2EP1pXo3) | composerImpl4.changed(function05) | composerImpl4.changed(strM322getString2EP1pXo4) | composerImpl4.changedInstance(coroutineScope2) | composerImpl4.changed(strM322getString2EP1pXo2);
                                                                        Object objRememberedValue82 = composerImpl4.rememberedValue();
                                                                        if (zChanged2) {
                                                                        }
                                                                    } else {
                                                                        function32 = function33;
                                                                        columnScopeInstance = columnScopeInstance2;
                                                                        composerImpl4.startReplaceGroup(812807061);
                                                                        composerImpl4.end(false);
                                                                    }
                                                                    function32.invoke(columnScopeInstance, composerImpl4, 6);
                                                                    composerImpl4.end(true);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl4, currentCompositeKeyHash, function215);
                                                                Function2 function2162 = ComposeUiNode.Companion.SetModifier;
                                                                Updater.m337setimpl(composerImpl4, modifierMaterializeModifier, function2162);
                                                                ColumnScopeInstance columnScopeInstance22 = ColumnScopeInstance.INSTANCE;
                                                                if (function212 == null) {
                                                                }
                                                                function32.invoke(columnScopeInstance, composerImpl4, 6);
                                                                composerImpl4.end(true);
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                }
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composerImpl2), composerImpl, ((i4 >> 24) & 112) | 12582912 | (i26 & 896) | (i26 & 7168) | (i26 & 57344), 96);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            sheetState3 = sheetState2;
                                            function25 = function27;
                                            z6 = z12;
                                            modifier3 = modifier2;
                                            f7 = f3;
                                            function26 = function24;
                                            shape3 = shape2;
                                            j5 = j3;
                                            j6 = j4;
                                            f8 = f6;
                                        }
                                    }
                                }
                            }
                        } else {
                            z4 = z3;
                        }
                        z5 = true;
                        Object objRememberedValue22 = composerImpl2.rememberedValue();
                        if (!z5) {
                        }
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.ModalBottomSheetKt$ModalBottomSheetContent$8
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj2, Object obj3) throws Resources.NotFoundException {
                                ((Number) obj3).intValue();
                                ModalBottomSheetKt.m273ModalBottomSheetContent7e2Q(boxScope, animatable, coroutineScope, function0, function1, modifier3, sheetState3, f7, z6, shape3, j5, j6, f8, function25, function26, function3, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2), i3);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i14 |= 196608;
                if ((306783379 & i4) != 306783378) {
                    composerImpl2.startDefaults();
                    if ((i & 1) != 0) {
                        if (i11 != 0) {
                        }
                        if ((i3 & 32) == 0) {
                        }
                        if (i7 != 0) {
                        }
                        if (i8 != 0) {
                        }
                        if ((256 & i3) == 0) {
                        }
                        if ((i3 & 512) == 0) {
                        }
                        SheetState sheetState42 = sheetStateRememberModalBottomSheetState;
                        if ((1024 & i3) == 0) {
                        }
                        if (i15 == 0) {
                        }
                        if (i13 == 0) {
                        }
                        if ((8192 & i3) == 0) {
                        }
                        f6 = f5;
                        j3 = containerColor;
                        sheetState2 = sheetState42;
                        j4 = jM259contentColorForek8zF_U;
                        i17 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                        composerImpl2.endDefaults();
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        int i242 = Strings.$r8$clinit;
                        final String strM322getString2EP1pXo2 = Strings_androidKt.m322getString2EP1pXo(R.string.m3c_bottom_sheet_pane_title, composerImpl2);
                        Alignment.Companion.getClass();
                        int i252 = i16;
                        Modifier modifierFillMaxWidth2 = SizeKt.fillMaxWidth(SizeKt.m146widthInVpY3zN4$default(boxScope.align(modifier2, Alignment.Companion.TopCenter), 0.0f, f3, 1), 1.0f);
                        Composer.Companion companion3 = Composer.Companion;
                        if (z3) {
                        }
                        Modifier modifierThen2 = modifierFillMaxWidth2.then(modifierNestedScroll);
                        AnchoredDraggableState anchoredDraggableState2 = sheetState2.anchoredDraggableState;
                        Orientation orientation22 = Orientation.Vertical;
                        i18 = (i4 & 3670016) ^ 1572864;
                        final Function2 function272 = function23;
                        if (i18 > 1048576) {
                            z4 = z3;
                            if ((i4 & 1572864) != 1048576) {
                                z5 = true;
                            }
                            Object objRememberedValue222 = composerImpl2.rememberedValue();
                            if (!z5) {
                            }
                        }
                    }
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            f4 = f2;
            i13 = i3 & 4096;
            if (i13 != 0) {
            }
            if ((i2 & 24576) == 0) {
            }
            if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
            }
            if ((306783379 & i4) != 306783378) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        function02 = function0;
        if ((i3 & 8) == 0) {
        }
        i6 = i3 & 16;
        if (i6 == 0) {
        }
        if ((i & 1572864) == 0) {
        }
        i7 = i3 & 64;
        if (i7 == 0) {
        }
        i8 = i3 & 128;
        if (i8 == 0) {
        }
        if ((i & 805306368) == 0) {
        }
        if ((i2 & 6) != 0) {
        }
        if ((i2 & 48) != 0) {
        }
        int i212 = i10;
        i12 = i3 & 2048;
        if (i12 == 0) {
        }
        f4 = f2;
        i13 = i3 & 4096;
        if (i13 != 0) {
        }
        if ((i2 & 24576) == 0) {
        }
        if ((i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
        }
        if ((306783379 & i4) != 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x011f  */
    /* renamed from: access$Scrim-3J-VO9M, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m274access$Scrim3JVO9M(final long j, final Function0 function0, final boolean z, Composer composer, final int i) throws Resources.NotFoundException {
        int i2;
        Modifier modifierSemantics;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(951870469);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(j) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(z) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.Scrim (ModalBottomSheet.kt:509)");
            }
            if (j != 16) {
                composerImpl.startReplaceGroup(484651512);
                final State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(z ? 1.0f : 0.0f, MotionSchemeKt.value(MotionSchemeKeyTokens.DefaultEffects, composerImpl), null, null, composerImpl, 0, 28);
                int i3 = Strings.$r8$clinit;
                final String strM322getString2EP1pXo = Strings_androidKt.m322getString2EP1pXo(R.string.close_sheet, composerImpl);
                Composer.Companion companion = Composer.Companion;
                if (z) {
                    composerImpl.startReplaceGroup(484942571);
                    Modifier.Companion companion2 = Modifier.Companion;
                    int i4 = i2 & 112;
                    boolean z2 = i4 == 32;
                    Object objRememberedValue = composerImpl.rememberedValue();
                    if (!z2) {
                        companion.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            objRememberedValue = new ModalBottomSheetKt$Scrim$dismissSheet$1$1(function0, null);
                            composerImpl.updateRememberedValue(objRememberedValue);
                        }
                        Modifier modifierThen = companion2.then(new SuspendPointerInputElement(function0, null, null, new SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0((Function2) objRememberedValue), 6, null));
                        boolean zChanged = (i4 == 32) | composerImpl.changed(strM322getString2EP1pXo);
                        Object objRememberedValue2 = composerImpl.rememberedValue();
                        if (!zChanged) {
                            companion.getClass();
                            if (objRememberedValue2 == Composer.Companion.Empty) {
                                objRememberedValue2 = new Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt$Scrim$dismissSheet$2$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj) {
                                        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                                        KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                                        SemanticsProperties.INSTANCE.getClass();
                                        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.TraversalIndex;
                                        KProperty kProperty = SemanticsPropertiesKt.$$delegatedProperties[9];
                                        semanticsPropertyKey.setValue(semanticsPropertyReceiver, Float.valueOf(1.0f));
                                        SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, strM322getString2EP1pXo);
                                        final Function0 function02 = function0;
                                        SemanticsPropertiesKt.onClick(semanticsPropertyReceiver, null, new Function0() { // from class: androidx.compose.material3.ModalBottomSheetKt$Scrim$dismissSheet$2$1.1
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                function02.invoke();
                                                return Boolean.TRUE;
                                            }
                                        });
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl.updateRememberedValue(objRememberedValue2);
                            }
                            modifierSemantics = SemanticsModifierKt.semantics(modifierThen, true, (Function1) objRememberedValue2);
                            composerImpl.end(false);
                        }
                    }
                } else {
                    composerImpl.startReplaceGroup(485368759);
                    composerImpl.end(false);
                    modifierSemantics = Modifier.Companion;
                }
                Modifier modifierThen2 = SizeKt.fillMaxSize(Modifier.Companion, 1.0f).then(modifierSemantics);
                boolean zChanged2 = composerImpl.changed(stateAnimateFloatAsState) | ((i2 & 14) == 4);
                Object objRememberedValue3 = composerImpl.rememberedValue();
                if (!zChanged2) {
                    companion.getClass();
                    if (objRememberedValue3 == Composer.Companion.Empty) {
                        objRememberedValue3 = new Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt$Scrim$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                DrawScope drawScope = (DrawScope) obj;
                                long j2 = j;
                                State<Float> state = stateAnimateFloatAsState;
                                float f = ModalBottomSheetKt.PredictiveBackMaxScaleXDistance;
                                DrawScope.m541drawRectnJ9OG0$default(drawScope, j2, 0L, 0L, RangesKt___RangesKt.coerceIn(((Number) state.getValue()).floatValue(), 0.0f, 1.0f), null, null, 0, 118);
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue3);
                    }
                    CanvasKt.Canvas(modifierThen2, (Function1) objRememberedValue3, composerImpl, 0);
                    composerImpl.end(false);
                }
            } else {
                composerImpl.startReplaceGroup(485550047);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.ModalBottomSheetKt$Scrim$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) throws Resources.NotFoundException {
                    ((Number) obj2).intValue();
                    ModalBottomSheetKt.m274access$Scrim3JVO9M(j, function0, z, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final float access$calculatePredictiveBackScaleX(GraphicsLayerScope graphicsLayerScope, float f) {
        ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) graphicsLayerScope;
        float fM419getWidthimpl = Size.m419getWidthimpl(reusableGraphicsLayerScope.size);
        if (Float.isNaN(fM419getWidthimpl) || fM419getWidthimpl == 0.0f) {
            return 1.0f;
        }
        return 1.0f - (MathHelpersKt.lerp(0.0f, Math.min(reusableGraphicsLayerScope.graphicsDensity.getDensity() * PredictiveBackMaxScaleXDistance, fM419getWidthimpl), f) / fM419getWidthimpl);
    }

    public static final float access$calculatePredictiveBackScaleY(GraphicsLayerScope graphicsLayerScope, float f) {
        ReusableGraphicsLayerScope reusableGraphicsLayerScope = (ReusableGraphicsLayerScope) graphicsLayerScope;
        float fM417getHeightimpl = Size.m417getHeightimpl(reusableGraphicsLayerScope.size);
        if (Float.isNaN(fM417getHeightimpl) || fM417getHeightimpl == 0.0f) {
            return 1.0f;
        }
        return 1.0f - (MathHelpersKt.lerp(0.0f, Math.min(reusableGraphicsLayerScope.graphicsDensity.getDensity() * PredictiveBackMaxScaleYDistance, fM417getHeightimpl), f) / fM417getHeightimpl);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final SheetState rememberModalBottomSheetState(Composer composer) {
        final AnonymousClass1 anonymousClass1 = new Function1() { // from class: androidx.compose.material3.ModalBottomSheetKt.rememberModalBottomSheetState.1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                return Boolean.TRUE;
            }
        };
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.rememberModalBottomSheetState (ModalBottomSheet.kt:502)");
        }
        final SheetValue sheetValue = SheetValue.Hidden;
        float f = SheetDefaultsKt.DragHandleVerticalPadding;
        BottomSheetDefaults.INSTANCE.getClass();
        final float f2 = BottomSheetDefaults.PositionalThreshold;
        final float f3 = BottomSheetDefaults.VelocityThreshold;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.rememberSheetState (SheetDefaults.kt:495)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        final Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
        boolean zChanged = composerImpl.changed(density) | composerImpl.changed(f2);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!zChanged) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new Function0() { // from class: androidx.compose.material3.SheetDefaultsKt$rememberSheetState$positionalThresholdToPx$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Float.valueOf(density.mo58toPx0680j_4(f2));
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        final Function0 function0 = (Function0) objRememberedValue;
        boolean zChanged2 = composerImpl.changed(density) | composerImpl.changed(f3);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChanged2) {
            companion.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                objRememberedValue2 = new Function0() { // from class: androidx.compose.material3.SheetDefaultsKt$rememberSheetState$velocityThresholdToPx$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return Float.valueOf(density.mo58toPx0680j_4(f3));
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
        }
        final Function0 function02 = (Function0) objRememberedValue2;
        final boolean z = false;
        Object[] objArr = {false, anonymousClass1, Boolean.FALSE};
        SheetState.Companion.getClass();
        SheetState$Companion$Saver$1 sheetState$Companion$Saver$1 = new Function2() { // from class: androidx.compose.material3.SheetState$Companion$Saver$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return (SheetValue) ((SnapshotMutableStateImpl) ((SheetState) obj2).anchoredDraggableState.currentValue$delegate).getValue();
            }
        };
        final boolean z2 = false;
        Function1 function1 = new Function1() { // from class: androidx.compose.material3.SheetState$Companion$Saver$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return new SheetState(z, function0, function02, (SheetValue) obj, anonymousClass1, z2);
            }
        };
        SaverKt$Saver$1 saverKt$Saver$1 = SaverKt.AutoSaver;
        SaverKt$Saver$1 saverKt$Saver$12 = new SaverKt$Saver$1(sheetState$Companion$Saver$1, function1);
        boolean zChanged3 = composerImpl.changed(false) | composerImpl.changed(function0) | composerImpl.changed(function02) | composerImpl.changed(anonymousClass1) | composerImpl.changed(false);
        Object objRememberedValue3 = composerImpl.rememberedValue();
        if (!zChanged3) {
            companion.getClass();
            if (objRememberedValue3 == Composer.Companion.Empty) {
                Object obj = new Function0() { // from class: androidx.compose.material3.SheetDefaultsKt$rememberSheetState$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new SheetState(z, function0, function02, sheetValue, anonymousClass1, z2);
                    }
                };
                composerImpl.updateRememberedValue(obj);
                objRememberedValue3 = obj;
            }
        }
        SheetState sheetState = (SheetState) RememberSaveableKt.rememberSaveable(objArr, saverKt$Saver$12, null, (Function0) objRememberedValue3, composerImpl, 0, 4);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return sheetState;
    }
}
