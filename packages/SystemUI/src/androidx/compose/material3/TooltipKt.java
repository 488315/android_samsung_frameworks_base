package androidx.compose.material3;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionKt;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.foundation.MutatorMutex;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.material3.internal.BasicTooltipDefaults;
import androidx.compose.material3.internal.BasicTooltipKt;
import androidx.compose.material3.tokens.MotionSchemeKeyTokens;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.OnGloballyPositionedModifierKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.window.PopupPositionProvider;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class TooltipKt {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        Dp.Companion companion = Dp.Companion;
        PaddingKt.m121PaddingValuesYgX7TsA(8, 4);
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:107:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x00f6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void TooltipBox(final PopupPositionProvider popupPositionProvider, final Function3 function3, final TooltipState tooltipState, Modifier modifier, boolean z, boolean z2, final Function2 function2, Composer composer, final int i, final int i2) {
        int i3;
        final Modifier modifier2;
        int i4;
        boolean z3;
        int i5;
        boolean z4;
        final boolean z5;
        final boolean z6;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1836749106);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(popupPositionProvider) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(function3) ? 32 : 16;
        }
        if ((i2 & 4) != 0) {
            i3 |= 384;
        } else if ((i & 384) == 0) {
            i3 |= (i & 512) == 0 ? composerImpl.changed(tooltipState) : composerImpl.changedInstance(tooltipState) ? 256 : 128;
        }
        int i6 = i2 & 8;
        if (i6 != 0) {
            i3 |= 3072;
        } else {
            if ((i & 3072) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 2048 : 1024;
            }
            i4 = i2 & 16;
            if (i4 == 0) {
                i3 |= 24576;
            } else {
                if ((i & 24576) == 0) {
                    z3 = z;
                    i3 |= composerImpl.changed(z3) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                i5 = i2 & 32;
                if (i5 != 0) {
                    i3 |= 196608;
                } else {
                    if ((196608 & i) == 0) {
                        z4 = z2;
                        i3 |= composerImpl.changed(z4) ? 131072 : 65536;
                    }
                    if ((i2 & 64) != 0) {
                        if ((i & 1572864) == 0) {
                            i3 |= composerImpl.changedInstance(function2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        }
                        if ((599187 & i3) == 599186 && composerImpl.getSkipping()) {
                            composerImpl.skipToGroupEnd();
                            z5 = z3;
                            z6 = z4;
                        } else {
                            Modifier modifier3 = i6 == 0 ? Modifier.Companion : modifier2;
                            z5 = i4 == 0 ? true : z3;
                            z6 = i5 == 0 ? true : z4;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.TooltipBox (Tooltip.kt:141)");
                            }
                            int i7 = (i3 & 14) | 24576 | (i3 & 112) | (i3 & 896) | (i3 & 7168);
                            int i8 = i3 << 3;
                            TooltipBox(popupPositionProvider, function3, tooltipState, modifier3, null, z5, z6, function2, composerImpl, i7 | (458752 & i8) | (3670016 & i8) | (i8 & 29360128), 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            modifier2 = modifier3;
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.TooltipKt.TooltipBox.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Number) obj2).intValue();
                                    TooltipKt.TooltipBox(popupPositionProvider, function3, tooltipState, modifier2, z5, z6, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i3 |= 1572864;
                    if ((599187 & i3) == 599186) {
                        if (i6 == 0) {
                        }
                        if (i4 == 0) {
                        }
                        if (i5 == 0) {
                        }
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        int i72 = (i3 & 14) | 24576 | (i3 & 112) | (i3 & 896) | (i3 & 7168);
                        int i82 = i3 << 3;
                        TooltipBox(popupPositionProvider, function3, tooltipState, modifier3, null, z5, z6, function2, composerImpl, i72 | (458752 & i82) | (3670016 & i82) | (i82 & 29360128), 0);
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        modifier2 = modifier3;
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                z4 = z2;
                if ((i2 & 64) != 0) {
                }
                if ((599187 & i3) == 599186) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            z3 = z;
            i5 = i2 & 32;
            if (i5 != 0) {
            }
            z4 = z2;
            if ((i2 & 64) != 0) {
            }
            if ((599187 & i3) == 599186) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 16;
        if (i4 == 0) {
        }
        z3 = z;
        i5 = i2 & 32;
        if (i5 != 0) {
        }
        z4 = z2;
        if ((i2 & 64) != 0) {
        }
        if ((599187 & i3) == 599186) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final TooltipState rememberTooltipState(Composer composer) {
        BasicTooltipDefaults.INSTANCE.getClass();
        MutatorMutex mutatorMutex = BasicTooltipDefaults.GlobalMutatorMutex;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.rememberTooltipState (Tooltip.kt:699)");
        }
        boolean zChanged = ((ComposerImpl) composer).changed(false) | ((ComposerImpl) composer).changed(mutatorMutex);
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new TooltipStateImpl(false, false, mutatorMutex);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        TooltipStateImpl tooltipStateImpl = (TooltipStateImpl) objRememberedValue;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return tooltipStateImpl;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:127:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x010e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void TooltipBox(final PopupPositionProvider popupPositionProvider, final Function3 function3, final TooltipState tooltipState, Modifier modifier, Function0 function0, boolean z, boolean z2, final Function2 function2, Composer composer, final int i, final int i2) {
        int i3;
        Modifier modifier2;
        int i4;
        Function0 function02;
        int i5;
        boolean z3;
        int i6;
        boolean z4;
        Object objRememberedValue;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        Object objRememberedValue2;
        final Modifier modifier3;
        final Function0 function03;
        final boolean z5;
        final boolean z6;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1947209790);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(popupPositionProvider) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(function3) ? 32 : 16;
        }
        if ((i2 & 4) != 0) {
            i3 |= 384;
        } else if ((i & 384) == 0) {
            i3 |= (i & 512) == 0 ? composerImpl.changed(tooltipState) : composerImpl.changedInstance(tooltipState) ? 256 : 128;
        }
        int i7 = i2 & 8;
        if (i7 != 0) {
            i3 |= 3072;
        } else {
            if ((i & 3072) == 0) {
                modifier2 = modifier;
                i3 |= composerImpl.changed(modifier2) ? 2048 : 1024;
            }
            i4 = i2 & 16;
            if (i4 == 0) {
                i3 |= 24576;
            } else {
                if ((i & 24576) == 0) {
                    function02 = function0;
                    i3 |= composerImpl.changedInstance(function02) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                i5 = i2 & 32;
                if (i5 != 0) {
                    i3 |= 196608;
                } else {
                    if ((196608 & i) == 0) {
                        z3 = z;
                        i3 |= composerImpl.changed(z3) ? 131072 : 65536;
                    }
                    i6 = i2 & 64;
                    if (i6 != 0) {
                        if ((1572864 & i) == 0) {
                            z4 = z2;
                            i3 |= composerImpl.changed(z4) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                        }
                        if ((i2 & 128) != 0) {
                            i3 |= 12582912;
                        } else if ((i & 12582912) == 0) {
                            i3 |= composerImpl.changedInstance(function2) ? 8388608 : 4194304;
                        }
                        if ((4793491 & i3) == 4793490 && composerImpl.getSkipping()) {
                            composerImpl.skipToGroupEnd();
                            modifier3 = modifier2;
                            function03 = function02;
                            z5 = z3;
                            z6 = z4;
                        } else {
                            Modifier modifier4 = i7 == 0 ? Modifier.Companion : modifier2;
                            Function0 function04 = i4 == 0 ? null : function02;
                            boolean z7 = i5 == 0 ? true : z3;
                            boolean z8 = i6 == 0 ? true : z4;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.TooltipBox (Tooltip.kt:216)");
                            }
                            final Transition transitionUpdateTransition = TransitionKt.updateTransition(((TooltipStateImpl) tooltipState).transition, "tooltip transition", composerImpl, 48);
                            objRememberedValue = composerImpl.rememberedValue();
                            Composer.Companion.getClass();
                            composer$Companion$Empty$1 = Composer.Companion.Empty;
                            if (objRememberedValue == composer$Companion$Empty$1) {
                                objRememberedValue = SnapshotStateKt.mutableStateOf$default(null);
                                composerImpl.updateRememberedValue(objRememberedValue);
                            }
                            final MutableState mutableState = (MutableState) objRememberedValue;
                            objRememberedValue2 = composerImpl.rememberedValue();
                            if (objRememberedValue2 == composer$Companion$Empty$1) {
                                objRememberedValue2 = new TooltipScopeImpl(new Function0() { // from class: androidx.compose.material3.TooltipKt$TooltipBox$scope$1$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        return (LayoutCoordinates) mutableState.getValue();
                                    }
                                });
                                composerImpl.updateRememberedValue(objRememberedValue2);
                            }
                            final TooltipScopeImpl tooltipScopeImpl = (TooltipScopeImpl) objRememberedValue2;
                            BasicTooltipKt.BasicTooltipBox(popupPositionProvider, ComposableLambdaKt.rememberComposableLambda(1746743156, new Function2() { // from class: androidx.compose.material3.TooltipKt.TooltipBox.2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

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
                                                ComposerKt.traceEventStart("androidx.compose.material3.TooltipBox.<anonymous> (Tooltip.kt:228)");
                                            }
                                            Modifier.Companion companion = Modifier.Companion;
                                            final Transition<Boolean> transition = transitionUpdateTransition;
                                            int i8 = TooltipKt.$r8$clinit;
                                            Modifier modifierComposed = ComposedModifierKt.composed(companion, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.material3.TooltipKt$animateTooltip$2
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(3);
                                                }

                                                @Override // kotlin.jvm.functions.Function3
                                                public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                                    Modifier modifier5 = (Modifier) obj3;
                                                    ((Number) obj5).intValue();
                                                    ComposerImpl composerImpl3 = (ComposerImpl) ((Composer) obj4);
                                                    composerImpl3.startReplaceGroup(-1498516085);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous> (Tooltip.kt:848)");
                                                    }
                                                    final SpringSpec springSpecValue = MotionSchemeKt.value(MotionSchemeKeyTokens.FastSpatial, composerImpl3);
                                                    final SpringSpec springSpecValue2 = MotionSchemeKt.value(MotionSchemeKeyTokens.FastEffects, composerImpl3);
                                                    Transition<Boolean> transition2 = transition;
                                                    Function3 function32 = new Function3() { // from class: androidx.compose.material3.TooltipKt$animateTooltip$2$scale$2
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(3);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function3
                                                        public final Object invoke(Object obj6, Object obj7, Object obj8) {
                                                            ((Number) obj8).intValue();
                                                            ComposerImpl composerImpl4 = (ComposerImpl) ((Composer) obj7);
                                                            composerImpl4.startReplaceGroup(386845748);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous>.<anonymous> (Tooltip.kt:852)");
                                                            }
                                                            FiniteAnimationSpec<Float> finiteAnimationSpec = springSpecValue;
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                            composerImpl4.end(false);
                                                            return finiteAnimationSpec;
                                                        }
                                                    };
                                                    FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
                                                    TwoWayConverter twoWayConverter = VectorConvertersKt.FloatToVector;
                                                    boolean zBooleanValue = ((Boolean) transition2.transitionState.getCurrentState()).booleanValue();
                                                    composerImpl3.startReplaceGroup(-1553362193);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous>.<anonymous> (Tooltip.kt:855)");
                                                    }
                                                    float f = zBooleanValue ? 1.0f : 0.8f;
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                    composerImpl3.end(false);
                                                    Float fValueOf = Float.valueOf(f);
                                                    boolean zBooleanValue2 = ((Boolean) ((SnapshotMutableStateImpl) transition2.targetState$delegate).getValue()).booleanValue();
                                                    composerImpl3.startReplaceGroup(-1553362193);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous>.<anonymous> (Tooltip.kt:855)");
                                                    }
                                                    float f2 = zBooleanValue2 ? 1.0f : 0.8f;
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                    composerImpl3.end(false);
                                                    Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation = TransitionKt.createTransitionAnimation(transition2, fValueOf, Float.valueOf(f2), (FiniteAnimationSpec) function32.invoke(transition2.getSegment(), composerImpl3, 0), twoWayConverter, "tooltip transition: scaling", composerImpl3, 196608);
                                                    Transition<Boolean> transition3 = transition;
                                                    Function3 function33 = new Function3() { // from class: androidx.compose.material3.TooltipKt$animateTooltip$2$alpha$2
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(3);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function3
                                                        public final Object invoke(Object obj6, Object obj7, Object obj8) {
                                                            ((Number) obj8).intValue();
                                                            ComposerImpl composerImpl4 = (ComposerImpl) ((Composer) obj7);
                                                            composerImpl4.startReplaceGroup(-281714272);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous>.<anonymous> (Tooltip.kt:860)");
                                                            }
                                                            FiniteAnimationSpec<Float> finiteAnimationSpec = springSpecValue2;
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                            composerImpl4.end(false);
                                                            return finiteAnimationSpec;
                                                        }
                                                    };
                                                    boolean zBooleanValue3 = ((Boolean) transition3.transitionState.getCurrentState()).booleanValue();
                                                    composerImpl3.startReplaceGroup(2073045083);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous>.<anonymous> (Tooltip.kt:863)");
                                                    }
                                                    float f3 = zBooleanValue3 ? 1.0f : 0.0f;
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                    composerImpl3.end(false);
                                                    Float fValueOf2 = Float.valueOf(f3);
                                                    boolean zBooleanValue4 = ((Boolean) ((SnapshotMutableStateImpl) transition3.targetState$delegate).getValue()).booleanValue();
                                                    composerImpl3.startReplaceGroup(2073045083);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous>.<anonymous> (Tooltip.kt:863)");
                                                    }
                                                    float f4 = zBooleanValue4 ? 1.0f : 0.0f;
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                    composerImpl3.end(false);
                                                    Modifier modifierM478graphicsLayerAp8cVGQ$default = GraphicsLayerModifierKt.m478graphicsLayerAp8cVGQ$default(modifier5, ((Number) transitionAnimationStateCreateTransitionAnimation.getValue()).floatValue(), ((Number) transitionAnimationStateCreateTransitionAnimation.getValue()).floatValue(), ((Number) TransitionKt.createTransitionAnimation(transition3, fValueOf2, Float.valueOf(f4), (FiniteAnimationSpec) function33.invoke(transition3.getSegment(), composerImpl3, 0), twoWayConverter, "tooltip transition: alpha", composerImpl3, 196608).getValue()).floatValue(), 0.0f, null, 131064);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                    composerImpl3.end(false);
                                                    return modifierM478graphicsLayerAp8cVGQ$default;
                                                }
                                            });
                                            Function3 function32 = function3;
                                            TooltipScopeImpl tooltipScopeImpl2 = tooltipScopeImpl;
                                            Alignment.Companion.getClass();
                                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierComposed);
                                            ComposeUiNode.Companion.getClass();
                                            Function0 function05 = ComposeUiNode.Companion.Constructor;
                                            if (composerImpl3.applier == null) {
                                                ComposablesKt.invalidApplier();
                                                throw null;
                                            }
                                            composerImpl3.startReusableNode();
                                            if (composerImpl3.inserting) {
                                                composerImpl3.createNode(function05);
                                            } else {
                                                composerImpl3.useNode();
                                            }
                                            Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                            Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                            Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function22);
                                            }
                                            Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                            function32.invoke(tooltipScopeImpl2, composer2, 6);
                                            composerImpl3.end(true);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl), tooltipState, modifier4, function04, z7, z8, ComposableLambdaKt.rememberComposableLambda(1784036624, new Function2() { // from class: androidx.compose.material3.TooltipKt$TooltipBox$wrappedContent$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

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
                                                ComposerKt.traceEventStart("androidx.compose.material3.TooltipBox.<anonymous> (Tooltip.kt:223)");
                                            }
                                            Modifier.Companion companion = Modifier.Companion;
                                            final MutableState<LayoutCoordinates> mutableState2 = mutableState;
                                            ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                            Object objRememberedValue3 = composerImpl3.rememberedValue();
                                            Composer.Companion.getClass();
                                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                                objRememberedValue3 = new Function1() { // from class: androidx.compose.material3.TooltipKt$TooltipBox$wrappedContent$1$1$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(1);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj3) {
                                                        mutableState2.setValue((LayoutCoordinates) obj3);
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl3.updateRememberedValue(objRememberedValue3);
                                            }
                                            Modifier modifierOnGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(companion, (Function1) objRememberedValue3);
                                            Function2 function22 = function2;
                                            Alignment.Companion.getClass();
                                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierOnGloballyPositioned);
                                            ComposeUiNode.Companion.getClass();
                                            Function0 function05 = ComposeUiNode.Companion.Constructor;
                                            if (composerImpl3.applier == null) {
                                                ComposablesKt.invalidApplier();
                                                throw null;
                                            }
                                            composerImpl3.startReusableNode();
                                            if (composerImpl3.inserting) {
                                                composerImpl3.createNode(function05);
                                            } else {
                                                composerImpl3.useNode();
                                            }
                                            Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                            Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                            Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                            if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                                            }
                                            Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                            function22.invoke(composerImpl3, 0);
                                            composerImpl3.end(true);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl), composerImpl, (i3 & 14) | 12582960 | (i3 & 896) | (i3 & 7168) | (57344 & i3) | (458752 & i3) | (i3 & 3670016), 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            modifier3 = modifier4;
                            function03 = function04;
                            z5 = z7;
                            z6 = z8;
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup != null) {
                            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.TooltipKt.TooltipBox.3
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Number) obj2).intValue();
                                    TooltipKt.TooltipBox(popupPositionProvider, function3, tooltipState, modifier3, function03, z5, z6, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                                    return Unit.INSTANCE;
                                }
                            };
                            return;
                        }
                        return;
                    }
                    i3 |= 1572864;
                    z4 = z2;
                    if ((i2 & 128) != 0) {
                    }
                    if ((4793491 & i3) == 4793490) {
                        if (i7 == 0) {
                        }
                        if (i4 == 0) {
                        }
                        if (i5 == 0) {
                        }
                        if (i6 == 0) {
                        }
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        final Transition<Boolean> transitionUpdateTransition2 = TransitionKt.updateTransition(((TooltipStateImpl) tooltipState).transition, "tooltip transition", composerImpl, 48);
                        objRememberedValue = composerImpl.rememberedValue();
                        Composer.Companion.getClass();
                        composer$Companion$Empty$1 = Composer.Companion.Empty;
                        if (objRememberedValue == composer$Companion$Empty$1) {
                        }
                        final MutableState<LayoutCoordinates> mutableState2 = (MutableState) objRememberedValue;
                        objRememberedValue2 = composerImpl.rememberedValue();
                        if (objRememberedValue2 == composer$Companion$Empty$1) {
                        }
                        final TooltipScopeImpl tooltipScopeImpl2 = (TooltipScopeImpl) objRememberedValue2;
                        BasicTooltipKt.BasicTooltipBox(popupPositionProvider, ComposableLambdaKt.rememberComposableLambda(1746743156, new Function2() { // from class: androidx.compose.material3.TooltipKt.TooltipBox.2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

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
                                            ComposerKt.traceEventStart("androidx.compose.material3.TooltipBox.<anonymous> (Tooltip.kt:228)");
                                        }
                                        Modifier.Companion companion = Modifier.Companion;
                                        final Transition<Boolean> transition = transitionUpdateTransition2;
                                        int i8 = TooltipKt.$r8$clinit;
                                        Modifier modifierComposed = ComposedModifierKt.composed(companion, InspectableValueKt.NoInspectorInfo, new Function3() { // from class: androidx.compose.material3.TooltipKt$animateTooltip$2
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(3);
                                            }

                                            @Override // kotlin.jvm.functions.Function3
                                            public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                                Modifier modifier5 = (Modifier) obj3;
                                                ((Number) obj5).intValue();
                                                ComposerImpl composerImpl3 = (ComposerImpl) ((Composer) obj4);
                                                composerImpl3.startReplaceGroup(-1498516085);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous> (Tooltip.kt:848)");
                                                }
                                                final FiniteAnimationSpec<Float> springSpecValue = MotionSchemeKt.value(MotionSchemeKeyTokens.FastSpatial, composerImpl3);
                                                final FiniteAnimationSpec<Float> springSpecValue2 = MotionSchemeKt.value(MotionSchemeKeyTokens.FastEffects, composerImpl3);
                                                Transition<Boolean> transition2 = transition;
                                                Function3 function32 = new Function3() { // from class: androidx.compose.material3.TooltipKt$animateTooltip$2$scale$2
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(3);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function3
                                                    public final Object invoke(Object obj6, Object obj7, Object obj8) {
                                                        ((Number) obj8).intValue();
                                                        ComposerImpl composerImpl4 = (ComposerImpl) ((Composer) obj7);
                                                        composerImpl4.startReplaceGroup(386845748);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous>.<anonymous> (Tooltip.kt:852)");
                                                        }
                                                        FiniteAnimationSpec<Float> finiteAnimationSpec = springSpecValue;
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                        composerImpl4.end(false);
                                                        return finiteAnimationSpec;
                                                    }
                                                };
                                                FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
                                                TwoWayConverter twoWayConverter = VectorConvertersKt.FloatToVector;
                                                boolean zBooleanValue = ((Boolean) transition2.transitionState.getCurrentState()).booleanValue();
                                                composerImpl3.startReplaceGroup(-1553362193);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous>.<anonymous> (Tooltip.kt:855)");
                                                }
                                                float f = zBooleanValue ? 1.0f : 0.8f;
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                composerImpl3.end(false);
                                                Float fValueOf = Float.valueOf(f);
                                                boolean zBooleanValue2 = ((Boolean) ((SnapshotMutableStateImpl) transition2.targetState$delegate).getValue()).booleanValue();
                                                composerImpl3.startReplaceGroup(-1553362193);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous>.<anonymous> (Tooltip.kt:855)");
                                                }
                                                float f2 = zBooleanValue2 ? 1.0f : 0.8f;
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                composerImpl3.end(false);
                                                Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation = TransitionKt.createTransitionAnimation(transition2, fValueOf, Float.valueOf(f2), (FiniteAnimationSpec) function32.invoke(transition2.getSegment(), composerImpl3, 0), twoWayConverter, "tooltip transition: scaling", composerImpl3, 196608);
                                                Transition<Boolean> transition3 = transition;
                                                Function3 function33 = new Function3() { // from class: androidx.compose.material3.TooltipKt$animateTooltip$2$alpha$2
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(3);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function3
                                                    public final Object invoke(Object obj6, Object obj7, Object obj8) {
                                                        ((Number) obj8).intValue();
                                                        ComposerImpl composerImpl4 = (ComposerImpl) ((Composer) obj7);
                                                        composerImpl4.startReplaceGroup(-281714272);
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous>.<anonymous> (Tooltip.kt:860)");
                                                        }
                                                        FiniteAnimationSpec<Float> finiteAnimationSpec = springSpecValue2;
                                                        if (ComposerKt.isTraceInProgress()) {
                                                            ComposerKt.traceEventEnd();
                                                        }
                                                        composerImpl4.end(false);
                                                        return finiteAnimationSpec;
                                                    }
                                                };
                                                boolean zBooleanValue3 = ((Boolean) transition3.transitionState.getCurrentState()).booleanValue();
                                                composerImpl3.startReplaceGroup(2073045083);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous>.<anonymous> (Tooltip.kt:863)");
                                                }
                                                float f3 = zBooleanValue3 ? 1.0f : 0.0f;
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                composerImpl3.end(false);
                                                Float fValueOf2 = Float.valueOf(f3);
                                                boolean zBooleanValue4 = ((Boolean) ((SnapshotMutableStateImpl) transition3.targetState$delegate).getValue()).booleanValue();
                                                composerImpl3.startReplaceGroup(2073045083);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("androidx.compose.material3.animateTooltip.<anonymous>.<anonymous> (Tooltip.kt:863)");
                                                }
                                                float f4 = zBooleanValue4 ? 1.0f : 0.0f;
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                composerImpl3.end(false);
                                                Modifier modifierM478graphicsLayerAp8cVGQ$default = GraphicsLayerModifierKt.m478graphicsLayerAp8cVGQ$default(modifier5, ((Number) transitionAnimationStateCreateTransitionAnimation.getValue()).floatValue(), ((Number) transitionAnimationStateCreateTransitionAnimation.getValue()).floatValue(), ((Number) TransitionKt.createTransitionAnimation(transition3, fValueOf2, Float.valueOf(f4), (FiniteAnimationSpec) function33.invoke(transition3.getSegment(), composerImpl3, 0), twoWayConverter, "tooltip transition: alpha", composerImpl3, 196608).getValue()).floatValue(), 0.0f, null, 131064);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                composerImpl3.end(false);
                                                return modifierM478graphicsLayerAp8cVGQ$default;
                                            }
                                        });
                                        Function3 function32 = function3;
                                        TooltipScopeImpl tooltipScopeImpl22 = tooltipScopeImpl2;
                                        Alignment.Companion.getClass();
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierComposed);
                                        ComposeUiNode.Companion.getClass();
                                        Function0 function05 = ComposeUiNode.Companion.Constructor;
                                        if (composerImpl3.applier == null) {
                                            ComposablesKt.invalidApplier();
                                            throw null;
                                        }
                                        composerImpl3.startReusableNode();
                                        if (composerImpl3.inserting) {
                                            composerImpl3.createNode(function05);
                                        } else {
                                            composerImpl3.useNode();
                                        }
                                        Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                        Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                        Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function22);
                                        }
                                        Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                        function32.invoke(tooltipScopeImpl22, composer2, 6);
                                        composerImpl3.end(true);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composerImpl), tooltipState, modifier4, function04, z7, z8, ComposableLambdaKt.rememberComposableLambda(1784036624, new Function2() { // from class: androidx.compose.material3.TooltipKt$TooltipBox$wrappedContent$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

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
                                            ComposerKt.traceEventStart("androidx.compose.material3.TooltipBox.<anonymous> (Tooltip.kt:223)");
                                        }
                                        Modifier.Companion companion = Modifier.Companion;
                                        final MutableState<LayoutCoordinates> mutableState22 = mutableState2;
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        Object objRememberedValue3 = composerImpl3.rememberedValue();
                                        Composer.Companion.getClass();
                                        if (objRememberedValue3 == Composer.Companion.Empty) {
                                            objRememberedValue3 = new Function1() { // from class: androidx.compose.material3.TooltipKt$TooltipBox$wrappedContent$1$1$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj3) {
                                                    mutableState22.setValue((LayoutCoordinates) obj3);
                                                    return Unit.INSTANCE;
                                                }
                                            };
                                            composerImpl3.updateRememberedValue(objRememberedValue3);
                                        }
                                        Modifier modifierOnGloballyPositioned = OnGloballyPositionedModifierKt.onGloballyPositioned(companion, (Function1) objRememberedValue3);
                                        Function2 function22 = function2;
                                        Alignment.Companion.getClass();
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl3);
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, modifierOnGloballyPositioned);
                                        ComposeUiNode.Companion.getClass();
                                        Function0 function05 = ComposeUiNode.Companion.Constructor;
                                        if (composerImpl3.applier == null) {
                                            ComposablesKt.invalidApplier();
                                            throw null;
                                        }
                                        composerImpl3.startReusableNode();
                                        if (composerImpl3.inserting) {
                                            composerImpl3.createNode(function05);
                                        } else {
                                            composerImpl3.useNode();
                                        }
                                        Updater.m337setimpl(composerImpl3, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                        Updater.m337setimpl(composerImpl3, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                        Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                                        }
                                        Updater.m337setimpl(composerImpl3, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                        function22.invoke(composerImpl3, 0);
                                        composerImpl3.end(true);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composerImpl), composerImpl, (i3 & 14) | 12582960 | (i3 & 896) | (i3 & 7168) | (57344 & i3) | (458752 & i3) | (i3 & 3670016), 0);
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        modifier3 = modifier4;
                        function03 = function04;
                        z5 = z7;
                        z6 = z8;
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                z3 = z;
                i6 = i2 & 64;
                if (i6 != 0) {
                }
                z4 = z2;
                if ((i2 & 128) != 0) {
                }
                if ((4793491 & i3) == 4793490) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            function02 = function0;
            i5 = i2 & 32;
            if (i5 != 0) {
            }
            z3 = z;
            i6 = i2 & 64;
            if (i6 != 0) {
            }
            z4 = z2;
            if ((i2 & 128) != 0) {
            }
            if ((4793491 & i3) == 4793490) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        modifier2 = modifier;
        i4 = i2 & 16;
        if (i4 == 0) {
        }
        function02 = function0;
        i5 = i2 & 32;
        if (i5 != 0) {
        }
        z3 = z;
        i6 = i2 & 64;
        if (i6 != 0) {
        }
        z4 = z2;
        if ((i2 & 128) != 0) {
        }
        if ((4793491 & i3) == 4793490) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }
}
