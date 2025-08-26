package com.android.compose.animation.scene;

import androidx.compose.animation.SplineBasedFloatDecayAnimationSpec_androidKt;
import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.foundation.OverscrollFactory;
import androidx.compose.foundation.OverscrollKt;
import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.LookaheadScope;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public abstract class SceneTransitionLayoutKt {
    /* JADX WARN: Removed duplicated region for block: B:43:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SceneTransitionLayout(final MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, final Modifier modifier, SwipeSourceDetector swipeSourceDetector, SwipeDetector swipeDetector, float f, final Function1 function1, Composer composer, final int i, final int i2) {
        int i3;
        SwipeDetector swipeDetector2;
        int i4;
        Function1 function12;
        ComposerImpl composerImpl;
        final SwipeSourceDetector swipeSourceDetector2;
        final SwipeDetector swipeDetector3;
        final float f2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        SwipeSourceDetector swipeSourceDetector3 = swipeSourceDetector;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1074935753);
        if ((i & 6) == 0) {
            i3 = (composerImpl2.changed(mutableSceneTransitionLayoutStateImpl) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl2.changed(modifier) ? 32 : 16;
        }
        int i5 = i2 & 4;
        if (i5 != 0) {
            i3 |= 384;
        } else if ((i & 384) == 0) {
            i3 |= (i & 512) == 0 ? composerImpl2.changed(swipeSourceDetector3) : composerImpl2.changedInstance(swipeSourceDetector3) ? 256 : 128;
        }
        int i6 = i2 & 8;
        if (i6 == 0) {
            if ((i & 3072) == 0) {
                swipeDetector2 = swipeDetector;
                i3 |= composerImpl2.changed(swipeDetector2) ? 2048 : 1024;
            }
            i4 = i3 | 221184;
            if ((1572864 & i) != 0) {
                function12 = function1;
                i4 |= composerImpl2.changedInstance(function12) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
            } else {
                function12 = function1;
            }
            if ((599187 & i4) == 599186 || !composerImpl2.getSkipping()) {
                if (i5 != 0) {
                    swipeSourceDetector3 = EdgeDetectorKt.DefaultEdgeDetector;
                }
                SwipeSourceDetector swipeSourceDetector4 = swipeSourceDetector3;
                SwipeDetector swipeDetector4 = i6 == 0 ? SwipeDetectorKt.DefaultSwipeDetector : swipeDetector2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.compose.animation.scene.SceneTransitionLayout (SceneTransitionLayout.kt:71)");
                }
                composerImpl = composerImpl2;
                SceneTransitionLayoutForTesting(mutableSceneTransitionLayoutStateImpl, modifier, swipeSourceDetector4, swipeDetector4, 0.05f, null, null, null, false, function12, composerImpl, (i4 & 14) | 196608 | (i4 & 112) | (i4 & 896) | (i4 & 7168) | (57344 & i4) | ((i4 << 12) & 1879048192), (i4 >> 18) & 14, 448);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                swipeSourceDetector2 = swipeSourceDetector4;
                swipeDetector3 = swipeDetector4;
                f2 = 0.05f;
            } else {
                composerImpl2.skipToGroupEnd();
                swipeSourceDetector2 = swipeSourceDetector3;
                composerImpl = composerImpl2;
                swipeDetector3 = swipeDetector2;
                f2 = f;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.compose.animation.scene.SceneTransitionLayoutKt$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        Function1 function13 = function1;
                        SceneTransitionLayoutKt.SceneTransitionLayout(mutableSceneTransitionLayoutStateImpl, modifier, swipeSourceDetector2, swipeDetector3, f2, function13, (Composer) obj, iUpdateChangedFlags, i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 3072;
        swipeDetector2 = swipeDetector;
        i4 = i3 | 221184;
        if ((1572864 & i) != 0) {
        }
        if ((599187 & i4) == 599186) {
            if (i5 != 0) {
            }
            SwipeSourceDetector swipeSourceDetector42 = swipeSourceDetector3;
            if (i6 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            composerImpl = composerImpl2;
            SceneTransitionLayoutForTesting(mutableSceneTransitionLayoutStateImpl, modifier, swipeSourceDetector42, swipeDetector4, 0.05f, null, null, null, false, function12, composerImpl, (i4 & 14) | 196608 | (i4 & 112) | (i4 & 896) | (i4 & 7168) | (57344 & i4) | ((i4 << 12) & 1879048192), (i4 >> 18) & 14, 448);
            if (ComposerKt.isTraceInProgress()) {
            }
            swipeSourceDetector2 = swipeSourceDetector42;
            swipeDetector3 = swipeDetector4;
            f2 = 0.05f;
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x018a  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x01d7  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x030d  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0323  */
    /* JADX WARN: Removed duplicated region for block: B:198:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0112  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SceneTransitionLayoutForTesting(final MutableSceneTransitionLayoutStateImpl mutableSceneTransitionLayoutStateImpl, Modifier modifier, SwipeSourceDetector swipeSourceDetector, SwipeDetector swipeDetector, float f, Map map, List list, LookaheadScope lookaheadScope, final boolean z, final Function1 function1, Composer composer, final int i, final int i2, final int i3) {
        int i4;
        SwipeDetector swipeDetector2;
        int i5;
        float f2;
        Map map2;
        List list2;
        int i6;
        int i7;
        int i8;
        final Map map3;
        SwipeSourceDetector swipeSourceDetector2;
        SwipeDetector swipeDetector3;
        final List list3;
        float f3;
        final LookaheadScope lookaheadScope2;
        Object objConsume;
        ComposerImpl composerImpl;
        int i9;
        final float f4;
        Density density;
        LayoutDirection layoutDirection;
        OverscrollFactory overscrollFactory;
        Function1 function12;
        final SwipeDetector swipeDetector4;
        final SceneTransitionLayoutImpl sceneTransitionLayoutImpl;
        final Map map4;
        final LookaheadScope lookaheadScope3;
        final SwipeDetector swipeDetector5;
        final float f5;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        final Modifier modifier2 = modifier;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1512112735);
        if ((i & 6) == 0) {
            i4 = (composerImpl2.changed(mutableSceneTransitionLayoutStateImpl) ? 4 : 2) | i;
        } else {
            i4 = i;
        }
        if ((i & 48) == 0) {
            i4 |= composerImpl2.changed(modifier2) ? 32 : 16;
        }
        int i10 = i3 & 4;
        if (i10 != 0) {
            i4 |= 384;
        } else if ((i & 384) == 0) {
            i4 |= (i & 512) == 0 ? composerImpl2.changed(swipeSourceDetector) : composerImpl2.changedInstance(swipeSourceDetector) ? 256 : 128;
        }
        int i11 = i3 & 8;
        if (i11 != 0) {
            i4 |= 3072;
        } else {
            if ((i & 3072) == 0) {
                swipeDetector2 = swipeDetector;
                i4 |= composerImpl2.changed(swipeDetector2) ? 2048 : 1024;
            }
            i5 = i3 & 16;
            if (i5 != 0) {
                if ((i & 24576) == 0) {
                    f2 = f;
                    i4 |= composerImpl2.changed(f2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                if ((i & 196608) == 0) {
                    i4 |= composerImpl2.changedInstance(null) ? 131072 : 65536;
                }
                if ((i & 1572864) == 0) {
                    map2 = map;
                    i4 |= ((i3 & 64) == 0 && composerImpl2.changedInstance(map2)) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                } else {
                    map2 = map;
                }
                if ((i & 12582912) == 0) {
                    if ((i3 & 128) == 0) {
                        list2 = list;
                        int i12 = composerImpl2.changedInstance(list2) ? 8388608 : 4194304;
                        i4 |= i12;
                    } else {
                        list2 = list;
                    }
                    i4 |= i12;
                } else {
                    list2 = list;
                }
                i6 = i3 & 256;
                if (i6 != 0) {
                    i4 |= 100663296;
                } else if ((i & 100663296) == 0) {
                    i4 |= composerImpl2.changedInstance(lookaheadScope) ? 67108864 : 33554432;
                }
                if ((i & 805306368) == 0) {
                    i4 |= composerImpl2.changed(z) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                }
                if ((i2 & 6) == 0) {
                    i7 = i2 | (composerImpl2.changedInstance(function1) ? 4 : 2);
                } else {
                    i7 = i2;
                }
                if ((i4 & 306783379) == 306783378 && (i7 & 3) == 2 && composerImpl2.getSkipping()) {
                    composerImpl2.skipToGroupEnd();
                    lookaheadScope3 = lookaheadScope;
                    composerImpl = composerImpl2;
                    f5 = f2;
                    map4 = map2;
                    swipeDetector5 = swipeDetector2;
                    swipeSourceDetector2 = swipeSourceDetector;
                } else {
                    composerImpl2.startDefaults();
                    i8 = i & 1;
                    Composer.Companion companion = Composer.Companion;
                    if (i8 != 0 || composerImpl2.getDefaultsInvalid()) {
                        SwipeSourceDetector swipeSourceDetector3 = i10 == 0 ? EdgeDetectorKt.DefaultEdgeDetector : swipeSourceDetector;
                        SwipeDetector swipeDetector6 = i11 == 0 ? SwipeDetectorKt.DefaultSwipeDetector : swipeDetector2;
                        if (i5 != 0) {
                            f2 = 0.0f;
                        }
                        if ((i3 & 64) != 0) {
                            Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl2, 1942397561, companion);
                            if (objM == Composer.Companion.Empty) {
                                objM = new LinkedHashMap();
                                composerImpl2.updateRememberedValue(objM);
                            }
                            map2 = (Map) objM;
                            composerImpl2.end(false);
                            i4 &= -3670017;
                        }
                        if ((i3 & 128) != 0) {
                            Object objM2 = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl2, 1942399510, companion);
                            if (objM2 == Composer.Companion.Empty) {
                                objM2 = EmptyList.INSTANCE;
                                composerImpl2.updateRememberedValue(objM2);
                            }
                            list2 = (List) objM2;
                            composerImpl2.end(false);
                            i4 &= -29360129;
                        }
                        List list4 = list2;
                        map3 = map2;
                        swipeSourceDetector2 = swipeSourceDetector3;
                        swipeDetector3 = swipeDetector6;
                        list3 = list4;
                        f3 = f2;
                        lookaheadScope2 = i6 == 0 ? null : lookaheadScope;
                    } else {
                        composerImpl2.skipToGroupEnd();
                        if ((i3 & 64) != 0) {
                            i4 &= -3670017;
                        }
                        if ((i3 & 128) != 0) {
                            i4 &= -29360129;
                        }
                        lookaheadScope2 = lookaheadScope;
                        f3 = f2;
                        list3 = list2;
                        swipeDetector3 = swipeDetector2;
                        map3 = map2;
                        swipeSourceDetector2 = swipeSourceDetector;
                    }
                    composerImpl2.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.compose.animation.scene.SceneTransitionLayoutForTesting (SceneTransitionLayout.kt:756)");
                    }
                    Density density2 = (Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity);
                    float touchSlop = ((ViewConfiguration) composerImpl2.consume(CompositionLocalsKt.LocalViewConfiguration)).getTouchSlop();
                    LayoutDirection layoutDirection2 = (LayoutDirection) composerImpl2.consume(CompositionLocalsKt.LocalLayoutDirection);
                    objConsume = composerImpl2.consume(OverscrollKt.LocalOverscrollFactory);
                    if (objConsume != null) {
                        throw new IllegalStateException("Required value was null.");
                    }
                    OverscrollFactory overscrollFactory2 = (OverscrollFactory) objConsume;
                    Object objRememberedValue = composerImpl2.rememberedValue();
                    companion.getClass();
                    Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                    if (objRememberedValue == composer$Companion$Empty$1) {
                        objRememberedValue = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                        composerImpl2.updateRememberedValue(objRememberedValue);
                    }
                    CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue;
                    final DecayAnimationSpec decayAnimationSpecRememberSplineBasedDecay = SplineBasedFloatDecayAnimationSpec_androidKt.rememberSplineBasedDecay(composerImpl2);
                    composerImpl2.startReplaceGroup(1942418344);
                    Object objRememberedValue2 = composerImpl2.rememberedValue();
                    if (objRememberedValue2 == composer$Companion$Empty$1) {
                        i9 = i4;
                        LookaheadScope lookaheadScope4 = lookaheadScope2;
                        density = density2;
                        Map map5 = map3;
                        layoutDirection = layoutDirection2;
                        List list5 = list3;
                        SceneTransitionLayoutImpl sceneTransitionLayoutImpl2 = new SceneTransitionLayoutImpl(mutableSceneTransitionLayoutStateImpl, density, layoutDirection, swipeSourceDetector2, swipeDetector3, f3, decayAnimationSpecRememberSplineBasedDecay, function1, coroutineScope, touchSlop, map5, list5, z, lookaheadScope4, overscrollFactory2);
                        f4 = f3;
                        overscrollFactory = overscrollFactory2;
                        function12 = function1;
                        map3 = map5;
                        list3 = list5;
                        lookaheadScope2 = lookaheadScope4;
                        composerImpl2.updateRememberedValue(sceneTransitionLayoutImpl2);
                        composerImpl = composerImpl2;
                        objRememberedValue2 = sceneTransitionLayoutImpl2;
                    } else {
                        composerImpl = composerImpl2;
                        i9 = i4;
                        f4 = f3;
                        density = density2;
                        layoutDirection = layoutDirection2;
                        overscrollFactory = overscrollFactory2;
                        function12 = function1;
                    }
                    SceneTransitionLayoutImpl sceneTransitionLayoutImpl3 = (SceneTransitionLayoutImpl) objRememberedValue2;
                    composerImpl.end(false);
                    sceneTransitionLayoutImpl3.updateContents$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(function12, layoutDirection, overscrollFactory);
                    composerImpl.startReplaceGroup(1942454985);
                    boolean zChangedInstance = ((i9 & 14) == 4) | composerImpl.changedInstance(map3) | composerImpl.changedInstance(list3) | composerImpl.changedInstance(lookaheadScope2) | composerImpl.changed(density) | composerImpl.changed(layoutDirection) | ((i9 & 896) == 256 || ((i9 & 512) != 0 && composerImpl.changedInstance(swipeSourceDetector2))) | ((i9 & 7168) == 2048) | ((57344 & i9) == 16384) | composerImpl.changedInstance(decayAnimationSpecRememberSplineBasedDecay);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (zChangedInstance || objRememberedValue3 == composer$Companion$Empty$1) {
                        swipeDetector4 = swipeDetector3;
                        final Density density3 = density;
                        final LayoutDirection layoutDirection3 = layoutDirection;
                        final SwipeSourceDetector swipeSourceDetector4 = swipeSourceDetector2;
                        sceneTransitionLayoutImpl = sceneTransitionLayoutImpl3;
                        Function0 function0 = new Function0(sceneTransitionLayoutImpl, map3, list3, lookaheadScope2, density3, layoutDirection3, swipeSourceDetector4, swipeDetector4, f4, decayAnimationSpecRememberSplineBasedDecay) { // from class: com.android.compose.animation.scene.SceneTransitionLayoutKt$$ExternalSyntheticLambda1
                            public final /* synthetic */ SceneTransitionLayoutImpl f$1;
                            public final /* synthetic */ DecayAnimationSpec f$10;
                            public final /* synthetic */ Map f$2;
                            public final /* synthetic */ List f$3;
                            public final /* synthetic */ LookaheadScope f$4;
                            public final /* synthetic */ Density f$5;
                            public final /* synthetic */ LayoutDirection f$6;
                            public final /* synthetic */ SwipeSourceDetector f$7;
                            public final /* synthetic */ SwipeDetector f$8;

                            {
                                this.f$10 = decayAnimationSpecRememberSplineBasedDecay;
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                Map map6 = this.f$2;
                                List list6 = this.f$3;
                                SceneTransitionLayoutImpl sceneTransitionLayoutImpl4 = this.f$1;
                                if (!Intrinsics.areEqual(this.f$0, sceneTransitionLayoutImpl4.state)) {
                                    throw new IllegalStateException("This SceneTransitionLayout was bound to a different SceneTransitionLayoutState that was used when creating it, which is not supported");
                                }
                                if (!Intrinsics.areEqual(sceneTransitionLayoutImpl4.elements, map6)) {
                                    throw new IllegalStateException("This SceneTransitionLayout was bound to a different elements map that was used when creating it, which is not supported");
                                }
                                if (!Intrinsics.areEqual(sceneTransitionLayoutImpl4.ancestors, list6)) {
                                    throw new IllegalStateException("This SceneTransitionLayout was bound to a different ancestors that was used when creating it, which is not supported");
                                }
                                LookaheadScope lookaheadScope5 = this.f$4;
                                if (lookaheadScope5 != null) {
                                    LookaheadScope lookaheadScope6 = sceneTransitionLayoutImpl4._lookaheadScope;
                                    lookaheadScope6.getClass();
                                    if (!lookaheadScope6.equals(lookaheadScope5)) {
                                        throw new IllegalStateException("This SceneTransitionLayout was bound to a different lookaheadScope that was used when creating it, which is not supported");
                                    }
                                }
                                sceneTransitionLayoutImpl4.density = this.f$5;
                                sceneTransitionLayoutImpl4.layoutDirection = this.f$6;
                                sceneTransitionLayoutImpl4.swipeSourceDetector = this.f$7;
                                sceneTransitionLayoutImpl4.swipeDetector = this.f$8;
                                sceneTransitionLayoutImpl4.decayAnimationSpec = this.f$10;
                                return Unit.INSTANCE;
                            }
                        };
                        composerImpl.updateRememberedValue(function0);
                        objRememberedValue3 = function0;
                    } else {
                        sceneTransitionLayoutImpl = sceneTransitionLayoutImpl3;
                        swipeDetector4 = swipeDetector3;
                    }
                    composerImpl.end(false);
                    EffectsKt.SideEffect((Function0) objRememberedValue3, composerImpl);
                    int i13 = ((i9 >> 3) & 14) | 48;
                    modifier2 = modifier;
                    sceneTransitionLayoutImpl.Content$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(modifier2, composerImpl, i13);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    map4 = map3;
                    list2 = list3;
                    lookaheadScope3 = lookaheadScope2;
                    swipeDetector5 = swipeDetector4;
                    f5 = f4;
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final SwipeSourceDetector swipeSourceDetector5 = swipeSourceDetector2;
                    final List list6 = list2;
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.compose.animation.scene.SceneTransitionLayoutKt$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Integer) obj2).getClass();
                            int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                            int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(i2);
                            Function1 function13 = function1;
                            int i14 = i3;
                            SceneTransitionLayoutKt.SceneTransitionLayoutForTesting(mutableSceneTransitionLayoutStateImpl, modifier2, swipeSourceDetector5, swipeDetector5, f5, map4, list6, lookaheadScope3, z, function13, (Composer) obj, iUpdateChangedFlags, iUpdateChangedFlags2, i14);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i4 |= 24576;
            f2 = f;
            if ((i & 196608) == 0) {
            }
            if ((i & 1572864) == 0) {
            }
            if ((i & 12582912) == 0) {
            }
            i6 = i3 & 256;
            if (i6 != 0) {
            }
            if ((i & 805306368) == 0) {
            }
            if ((i2 & 6) == 0) {
            }
            if ((i4 & 306783379) == 306783378) {
                composerImpl2.startDefaults();
                i8 = i & 1;
                Composer.Companion companion2 = Composer.Companion;
                if (i8 != 0) {
                    if (i10 == 0) {
                    }
                    if (i11 == 0) {
                    }
                    if (i5 != 0) {
                    }
                    if ((i3 & 64) != 0) {
                    }
                    if ((i3 & 128) != 0) {
                    }
                    List list42 = list2;
                    map3 = map2;
                    swipeSourceDetector2 = swipeSourceDetector3;
                    swipeDetector3 = swipeDetector6;
                    list3 = list42;
                    f3 = f2;
                    lookaheadScope2 = i6 == 0 ? null : lookaheadScope;
                    composerImpl2.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    Density density22 = (Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity);
                    float touchSlop2 = ((ViewConfiguration) composerImpl2.consume(CompositionLocalsKt.LocalViewConfiguration)).getTouchSlop();
                    LayoutDirection layoutDirection22 = (LayoutDirection) composerImpl2.consume(CompositionLocalsKt.LocalLayoutDirection);
                    objConsume = composerImpl2.consume(OverscrollKt.LocalOverscrollFactory);
                    if (objConsume != null) {
                    }
                }
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        swipeDetector2 = swipeDetector;
        i5 = i3 & 16;
        if (i5 != 0) {
        }
        f2 = f;
        if ((i & 196608) == 0) {
        }
        if ((i & 1572864) == 0) {
        }
        if ((i & 12582912) == 0) {
        }
        i6 = i3 & 256;
        if (i6 != 0) {
        }
        if ((i & 805306368) == 0) {
        }
        if ((i2 & 6) == 0) {
        }
        if ((i4 & 306783379) == 306783378) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }
}
