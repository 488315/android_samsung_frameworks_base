package androidx.compose.foundation.pager;

import androidx.compose.foundation.CheckScrollableContainerConstraintsKt;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.ScrollingContainerKt;
import androidx.compose.foundation.gestures.BringIntoViewSpec;
import androidx.compose.foundation.gestures.BringIntoViewSpec_androidKt;
import androidx.compose.foundation.gestures.ForEachGestureKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.gestures.TargetedFlingBehavior;
import androidx.compose.foundation.gestures.snapping.SnapPosition;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsModifierLocalKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsStateKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProviderKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScope;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScopeImpl;
import androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState;
import androidx.compose.foundation.lazy.layout.LazyLayoutSemanticsKt;
import androidx.compose.foundation.lazy.layout.NearestRangeKeyIndexMap;
import androidx.compose.foundation.pager.PageSize;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection;
import androidx.compose.ui.input.nestedscroll.NestedScrollModifierKt;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventKt;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputEventHandler;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.SubcomposeMeasureScope;
import androidx.compose.ui.semantics.AccessibilityAction;
import androidx.compose.ui.semantics.CollectionInfo;
import androidx.compose.ui.semantics.SemanticsActions;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* loaded from: classes.dex */
public abstract class LazyLayoutPagerKt {
    /* JADX WARN: Removed duplicated region for block: B:103:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x02c0  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0303  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x0487 A[PHI: r3
      0x0487: PHI (r3v73 int) = (r3v46 int), (r3v74 int) binds: [B:332:0x0485, B:328:0x047e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:340:0x04a7  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x04ca  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x04d1  */
    /* JADX WARN: Removed duplicated region for block: B:347:0x04d4  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x04db  */
    /* JADX WARN: Removed duplicated region for block: B:361:0x04fd  */
    /* JADX WARN: Removed duplicated region for block: B:364:0x0502  */
    /* JADX WARN: Removed duplicated region for block: B:368:0x0514  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x051d  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0520  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x0527  */
    /* JADX WARN: Removed duplicated region for block: B:376:0x052a  */
    /* JADX WARN: Removed duplicated region for block: B:379:0x0532 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:382:0x0538  */
    /* JADX WARN: Removed duplicated region for block: B:385:0x0550  */
    /* JADX WARN: Removed duplicated region for block: B:386:0x0553  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x0561  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x056e  */
    /* JADX WARN: Removed duplicated region for block: B:420:0x05e0  */
    /* JADX WARN: Removed duplicated region for block: B:423:0x060c  */
    /* JADX WARN: Removed duplicated region for block: B:424:0x060f  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x0613  */
    /* JADX WARN: Removed duplicated region for block: B:427:0x0624  */
    /* JADX WARN: Removed duplicated region for block: B:430:0x0665  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x066a  */
    /* JADX WARN: Removed duplicated region for block: B:435:0x067b  */
    /* JADX WARN: Removed duplicated region for block: B:437:? A[RETURN, SYNTHETIC] */
    /* renamed from: Pager-eLwUrMk, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m177PagereLwUrMk(final Modifier modifier, final PagerState pagerState, final PaddingValues paddingValues, final boolean z, final Orientation orientation, final TargetedFlingBehavior targetedFlingBehavior, final boolean z2, final OverscrollEffect overscrollEffect, int i, float f, final PageSize pageSize, final NestedScrollConnection nestedScrollConnection, final Function1 function1, final Alignment.Horizontal horizontal, final Alignment.Vertical vertical, final SnapPosition snapPosition, final Function4 function4, Composer composer, final int i2, final int i3, final int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        Alignment.Vertical vertical2;
        SnapPosition snapPosition2;
        Function4 function42;
        int i16;
        final PagerState pagerState2;
        ComposerImpl composerImpl;
        final int i17;
        final float f2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        float f3;
        int i18;
        boolean zChanged;
        int i19;
        boolean z3;
        boolean zChanged2;
        Object obj;
        Object obj2;
        int i20;
        final CoroutineScope coroutineScope;
        final PagerState pagerState3;
        float f4;
        KProperty0 kProperty0;
        boolean zChanged3;
        Object obj3;
        boolean z4;
        Object objRememberedValue;
        BringIntoViewSpec bringIntoViewSpec;
        boolean zChanged4;
        Object objRememberedValue2;
        KProperty0 kProperty02;
        Modifier modifierLazyLayoutBeyondBoundsModifier;
        int i21 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1125194810);
        if ((i4 & 1) != 0) {
            i5 = i2 | 6;
        } else if ((i2 & 6) == 0) {
            i5 = i2 | (composerImpl2.changed(modifier) ? 4 : 2);
        } else {
            i5 = i2;
        }
        if ((i4 & 2) != 0) {
            i5 |= 48;
        } else if ((i2 & 48) == 0) {
            i5 |= composerImpl2.changed(pagerState) ? 32 : 16;
        }
        int i22 = i5;
        if ((i4 & 4) != 0) {
            i6 = i22 | 384;
        } else if ((i2 & 384) == 0) {
            i6 = i22 | (composerImpl2.changed(paddingValues) ? 256 : 128);
        } else {
            i6 = i22;
        }
        if ((i4 & 8) != 0) {
            i7 = i6 | 3072;
        } else {
            int i23 = i6;
            if ((i2 & 3072) == 0) {
                i7 = i23 | (composerImpl2.changed(z) ? 2048 : 1024);
            } else {
                i7 = i23;
            }
        }
        if ((i4 & 16) != 0) {
            i8 = i7 | 24576;
        } else {
            int i24 = i7;
            if ((i2 & 24576) == 0) {
                i8 = i24 | (composerImpl2.changed(orientation) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192);
            } else {
                i8 = i24;
            }
        }
        if ((i4 & 32) != 0) {
            i8 |= 196608;
        } else if ((i2 & 196608) == 0) {
            i8 |= composerImpl2.changed(targetedFlingBehavior) ? 131072 : 65536;
        }
        if ((i4 & 64) != 0) {
            i8 |= 1572864;
        } else if ((i2 & 1572864) == 0) {
            i8 |= composerImpl2.changed(z2) ? 1048576 : 524288;
        }
        if ((i4 & 128) != 0) {
            i8 |= 12582912;
        } else if ((i2 & 12582912) == 0) {
            i8 |= composerImpl2.changed(overscrollEffect) ? 8388608 : 4194304;
        }
        int i25 = i4 & 256;
        if (i25 != 0) {
            i8 |= 100663296;
        } else {
            if ((i2 & 100663296) == 0) {
                i9 = 100663296;
                i8 |= composerImpl2.changed(i) ? 67108864 : 33554432;
            }
            i10 = i4 & 512;
            if (i10 == 0) {
                i8 |= 805306368;
                i11 = i10;
            } else if ((i2 & 805306368) == 0) {
                i11 = i10;
                i8 |= composerImpl2.changed(f) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
            } else {
                i11 = i10;
            }
            i12 = i8;
            if ((i4 & 1024) == 0) {
                i14 = i3 | 6;
            } else if ((i3 & 6) == 0) {
                i14 = i3 | (composerImpl2.changed(pageSize) ? 4 : 2);
            } else {
                i13 = i3;
                if ((i4 & 2048) != 0) {
                    i15 = i13 | 48;
                } else if ((i3 & 48) == 0) {
                    i15 = i13 | (composerImpl2.changedInstance(nestedScrollConnection) ? 32 : 16);
                } else {
                    i15 = i13;
                }
                if ((i4 & 4096) != 0) {
                    i15 |= 384;
                } else if ((i3 & 384) == 0) {
                    i15 |= composerImpl2.changedInstance(function1) ? 256 : 128;
                }
                if ((i4 & 8192) != 0) {
                    i15 |= 3072;
                } else if ((i3 & 3072) == 0) {
                    i15 |= composerImpl2.changed(horizontal) ? 2048 : 1024;
                }
                if ((i4 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
                    i15 |= 24576;
                    vertical2 = vertical;
                } else {
                    vertical2 = vertical;
                    if ((i3 & 24576) == 0) {
                        i15 |= composerImpl2.changed(vertical2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                    }
                }
                if ((32768 & i4) == 0) {
                    if ((i3 & 196608) == 0) {
                        snapPosition2 = snapPosition;
                        i15 |= composerImpl2.changed(snapPosition2) ? 131072 : 65536;
                    }
                    if ((i4 & 65536) == 0) {
                        i16 = i15 | 1572864;
                        function42 = function4;
                    } else if ((i3 & 1572864) == 0) {
                        int i26 = i15;
                        function42 = function4;
                        if (composerImpl2.changedInstance(function42)) {
                            i21 = 1048576;
                        }
                        i16 = i26 | i21;
                    } else {
                        int i27 = i15;
                        function42 = function4;
                        i16 = i27;
                    }
                    if (composerImpl2.shouldExecute(i12 & 1, (i12 & 306783379) == 306783378 || (599187 & i16) != 599186)) {
                        pagerState2 = pagerState;
                        composerImpl = composerImpl2;
                        composerImpl.skipToGroupEnd();
                        i17 = i;
                        f2 = f;
                    } else {
                        int i28 = i25 != 0 ? 0 : i;
                        if (i11 != 0) {
                            f3 = 0;
                            Dp.Companion companion = Dp.Companion;
                        } else {
                            f3 = f;
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.foundation.pager.Pager (LazyLayoutPager.kt:103)");
                        }
                        if (i28 < 0) {
                            InlineClassHelperKt.throwIllegalArgumentException("beyondViewportPageCount should be greater than or equal to 0, you selected " + i28);
                        }
                        int i29 = i12 & 112;
                        boolean z5 = i29 == 32;
                        Object objRememberedValue3 = composerImpl2.rememberedValue();
                        Composer.Companion companion2 = Composer.Companion;
                        if (z5) {
                            i18 = i28;
                        } else {
                            companion2.getClass();
                            i18 = i28;
                            if (objRememberedValue3 == Composer.Companion.Empty) {
                            }
                            final Function0 function0 = (Function0) objRememberedValue3;
                            int i30 = i12 >> 3;
                            int i31 = i30 & 14;
                            int i32 = i16 >> 15;
                            int i33 = i31 | (i32 & 112) | (i16 & 896);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.foundation.pager.rememberPagerItemProviderLambda (LazyLayoutPager.kt:259)");
                            }
                            int i34 = i16;
                            final MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function42, composerImpl2);
                            final MutableState mutableStateRememberUpdatedState2 = SnapshotStateKt.rememberUpdatedState(function1, composerImpl2);
                            zChanged = ((((i33 & 14) ^ 6) <= 4 && composerImpl2.changed(pagerState)) || (i33 & 6) == 4) | composerImpl2.changed(mutableStateRememberUpdatedState) | composerImpl2.changed(mutableStateRememberUpdatedState2) | composerImpl2.changed(function0);
                            Object objRememberedValue4 = composerImpl2.rememberedValue();
                            if (!zChanged) {
                                companion2.getClass();
                                if (objRememberedValue4 == Composer.Companion.Empty) {
                                    final State stateDerivedStateOf = SnapshotStateKt.derivedStateOf(SnapshotStateKt.referentialEqualityPolicy(), new Function0() { // from class: androidx.compose.foundation.pager.LazyLayoutPagerKt$rememberPagerItemProviderLambda$1$intervalContentState$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        /* JADX WARN: Multi-variable type inference failed */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return new PagerLayoutIntervalContent((Function4) mutableStateRememberUpdatedState.getValue(), (Function1) mutableStateRememberUpdatedState2.getValue(), ((Number) function0.invoke()).intValue());
                                        }
                                    });
                                    final State stateDerivedStateOf2 = SnapshotStateKt.derivedStateOf(SnapshotStateKt.referentialEqualityPolicy(), new Function0() { // from class: androidx.compose.foundation.pager.LazyLayoutPagerKt$rememberPagerItemProviderLambda$1$itemProviderState$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            PagerLayoutIntervalContent pagerLayoutIntervalContent = (PagerLayoutIntervalContent) stateDerivedStateOf.getValue();
                                            return new PagerLazyLayoutItemProvider(pagerState, pagerLayoutIntervalContent, new NearestRangeKeyIndexMap((IntRange) pagerState.scrollPosition.nearestRangeState.getValue(), pagerLayoutIntervalContent));
                                        }
                                    });
                                    objRememberedValue4 = new PropertyReference0Impl(stateDerivedStateOf2) { // from class: androidx.compose.foundation.pager.LazyLayoutPagerKt$rememberPagerItemProviderLambda$1$1
                                        @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                                        public final Object get() {
                                            return ((State) this.receiver).getValue();
                                        }
                                    };
                                    composerImpl2.updateRememberedValue(objRememberedValue4);
                                }
                                final KProperty0 kProperty03 = (KProperty0) objRememberedValue4;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                Object objRememberedValue5 = composerImpl2.rememberedValue();
                                companion2.getClass();
                                Object obj4 = Composer.Companion.Empty;
                                if (objRememberedValue5 == obj4) {
                                    objRememberedValue5 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                                    composerImpl2.updateRememberedValue(objRememberedValue5);
                                }
                                CoroutineScope coroutineScope2 = (CoroutineScope) objRememberedValue5;
                                boolean z6 = i29 == 32;
                                Object objRememberedValue6 = composerImpl2.rememberedValue();
                                if (z6 || objRememberedValue6 == obj4) {
                                    objRememberedValue6 = new Function0() { // from class: androidx.compose.foundation.pager.LazyLayoutPagerKt$Pager$measurePolicy$1$1
                                        {
                                            super(0);
                                        }

                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            return Integer.valueOf(pagerState.getPageCount());
                                        }
                                    };
                                    composerImpl2.updateRememberedValue(objRememberedValue6);
                                }
                                final Function0 function02 = (Function0) objRememberedValue6;
                                int i35 = i12 >> 9;
                                int i36 = (i12 & 65520) | (i35 & 458752) | (i35 & 3670016) | ((i34 << 21) & 29360128);
                                int i37 = i34 << 15;
                                int i38 = i36 | (i37 & 234881024) | (i37 & 1879048192);
                                int i39 = i32 & 14;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.foundation.pager.rememberPagerMeasurePolicy (PagerMeasurePolicy.kt:57)");
                                }
                                boolean zChanged5 = ((((i38 & 896) ^ 384) > 256 && composerImpl2.changed(paddingValues)) || (i38 & 384) == 256) | ((((i38 & 112) ^ 48) > 32 && composerImpl2.changed(pagerState)) || (i38 & 48) == 32) | ((((i38 & 7168) ^ 3072) > 2048 && composerImpl2.changed(z)) || (i38 & 3072) == 2048) | ((((57344 & i38) ^ 24576) > 16384 && composerImpl2.changed(orientation)) || (i38 & 24576) == 16384) | ((((234881024 & i38) ^ i9) > 67108864 && composerImpl2.changed(horizontal)) || (i38 & i9) == 67108864) | ((((1879048192 & i38) ^ 805306368) > 536870912 && composerImpl2.changed(vertical2)) || (i38 & 805306368) == 536870912) | ((((3670016 & i38) ^ 1572864) > 1048576 && composerImpl2.changed(f3)) || (i38 & 1572864) == 1048576) | ((((29360128 & i38) ^ 12582912) > 8388608 && composerImpl2.changed(pageSize)) || (i38 & 12582912) == 8388608) | (((i39 ^ 6) > 4 && composerImpl2.changed(snapPosition2)) || (i32 & 6) == 4) | composerImpl2.changed(function02);
                                if (((i38 & 458752) ^ 196608) > 131072) {
                                    i19 = i18;
                                    if (composerImpl2.changed(i19)) {
                                        z3 = true;
                                        zChanged2 = zChanged5 | z3 | composerImpl2.changed(coroutineScope2);
                                        Object objRememberedValue7 = composerImpl2.rememberedValue();
                                        if (!zChanged2 || objRememberedValue7 == obj4) {
                                            i17 = i19;
                                            obj2 = obj4;
                                            final float f5 = f3;
                                            final Alignment.Vertical vertical3 = vertical2;
                                            i20 = 4;
                                            coroutineScope = coroutineScope2;
                                            composerImpl = composerImpl2;
                                            obj = new Function2() { // from class: androidx.compose.foundation.pager.PagerMeasurePolicyKt$rememberPagerMeasurePolicy$1$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(2);
                                                }

                                                /* JADX WARN: Multi-variable type inference failed */
                                                /* JADX WARN: Type inference failed for: r2v84, types: [kotlin.ranges.IntProgression] */
                                                /* JADX WARN: Type inference failed for: r3v52, types: [java.util.ArrayList] */
                                                /* JADX WARN: Type inference failed for: r3v53 */
                                                /* JADX WARN: Type inference failed for: r3v55, types: [java.util.ArrayList] */
                                                /* JADX WARN: Type inference failed for: r3v56 */
                                                /* JADX WARN: Type inference failed for: r3v69, types: [kotlin.collections.EmptyList] */
                                                /* JADX WARN: Type inference failed for: r3v70, types: [kotlin.collections.EmptyList] */
                                                @Override // kotlin.jvm.functions.Function2
                                                public final Object invoke(Object obj5, Object obj6) {
                                                    int iMo52roundToPx0680j_4;
                                                    int iMo52roundToPx0680j_42;
                                                    LazyLayoutMeasureScope lazyLayoutMeasureScope;
                                                    long j;
                                                    int i40;
                                                    int i41;
                                                    int i42;
                                                    int i43;
                                                    long j2;
                                                    final long j3;
                                                    int i44;
                                                    int i45;
                                                    int i46;
                                                    int i47;
                                                    int i48;
                                                    int i49;
                                                    LazyLayoutMeasureScope lazyLayoutMeasureScope2;
                                                    int i50;
                                                    int i51;
                                                    int i52;
                                                    LazyLayoutMeasureScope lazyLayoutMeasureScope3;
                                                    int i53;
                                                    int i54;
                                                    int i55;
                                                    MeasuredPage measuredPage;
                                                    long j4;
                                                    ArrayList arrayList;
                                                    int i56;
                                                    int i57;
                                                    SubcomposeMeasureScope subcomposeMeasureScope;
                                                    final ArrayList arrayList2;
                                                    boolean z7;
                                                    ArrayDeque arrayDeque;
                                                    int i58;
                                                    List list;
                                                    ArrayList arrayList3;
                                                    ?? arrayList4;
                                                    ?? arrayList5;
                                                    Orientation orientation2;
                                                    int i59;
                                                    int i60;
                                                    int i61;
                                                    SnapPosition snapPosition3;
                                                    int i62;
                                                    ArrayList arrayList6;
                                                    int i63;
                                                    boolean z8;
                                                    Object obj7;
                                                    PagerMeasureResult pagerMeasureResult;
                                                    int i64;
                                                    int i65;
                                                    int i66;
                                                    int i67;
                                                    int i68;
                                                    int i69;
                                                    LazyLayoutMeasureScope lazyLayoutMeasureScope4 = (LazyLayoutMeasureScope) obj5;
                                                    final long j5 = ((Constraints) obj6).value;
                                                    pagerState.measurementScopeInvalidator.getValue();
                                                    Orientation orientation3 = orientation;
                                                    Orientation orientation4 = Orientation.Vertical;
                                                    boolean z9 = orientation3 == orientation4;
                                                    CheckScrollableContainerConstraintsKt.m32checkScrollableContainerConstraintsK40F9xA(z9 ? orientation4 : Orientation.Horizontal, j5);
                                                    if (z9) {
                                                        LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope4;
                                                        iMo52roundToPx0680j_4 = lazyLayoutMeasureScopeImpl.subcomposeMeasureScope.mo52roundToPx0680j_4(paddingValues.mo111calculateLeftPaddingu2uoSUM(lazyLayoutMeasureScopeImpl.subcomposeMeasureScope.getLayoutDirection()));
                                                    } else {
                                                        LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl2 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope4;
                                                        iMo52roundToPx0680j_4 = lazyLayoutMeasureScopeImpl2.subcomposeMeasureScope.mo52roundToPx0680j_4(PaddingKt.calculateStartPadding(paddingValues, lazyLayoutMeasureScopeImpl2.subcomposeMeasureScope.getLayoutDirection()));
                                                    }
                                                    if (z9) {
                                                        LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl3 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope4;
                                                        iMo52roundToPx0680j_42 = lazyLayoutMeasureScopeImpl3.subcomposeMeasureScope.mo52roundToPx0680j_4(paddingValues.mo112calculateRightPaddingu2uoSUM(lazyLayoutMeasureScopeImpl3.subcomposeMeasureScope.getLayoutDirection()));
                                                    } else {
                                                        LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl4 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope4;
                                                        iMo52roundToPx0680j_42 = lazyLayoutMeasureScopeImpl4.subcomposeMeasureScope.mo52roundToPx0680j_4(PaddingKt.calculateEndPadding(paddingValues, lazyLayoutMeasureScopeImpl4.subcomposeMeasureScope.getLayoutDirection()));
                                                    }
                                                    LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl5 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope4;
                                                    int iMo52roundToPx0680j_43 = lazyLayoutMeasureScopeImpl5.subcomposeMeasureScope.mo52roundToPx0680j_4(paddingValues.mo113calculateTopPaddingD9Ej5fM());
                                                    float fMo110calculateBottomPaddingD9Ej5fM = paddingValues.mo110calculateBottomPaddingD9Ej5fM();
                                                    SubcomposeMeasureScope subcomposeMeasureScope2 = lazyLayoutMeasureScopeImpl5.subcomposeMeasureScope;
                                                    int iMo52roundToPx0680j_44 = subcomposeMeasureScope2.mo52roundToPx0680j_4(fMo110calculateBottomPaddingD9Ej5fM);
                                                    int i70 = iMo52roundToPx0680j_42;
                                                    final int i71 = iMo52roundToPx0680j_43 + iMo52roundToPx0680j_44;
                                                    int i72 = iMo52roundToPx0680j_4 + i70;
                                                    int i73 = z9 ? i71 : i72;
                                                    boolean z10 = true;
                                                    int i74 = (!z9 || z) ? (z9 && z) ? iMo52roundToPx0680j_44 : (z9 || z) ? i70 : iMo52roundToPx0680j_4 : iMo52roundToPx0680j_43;
                                                    int i75 = i73 - i74;
                                                    long jM835offsetNN6EwU = ConstraintsKt.m835offsetNN6EwU(-i72, -i71, j5);
                                                    pagerState.density = lazyLayoutMeasureScope4;
                                                    int iMo52roundToPx0680j_45 = subcomposeMeasureScope2.mo52roundToPx0680j_4(f5);
                                                    int iM822getMaxHeightimpl = z9 ? Constraints.m822getMaxHeightimpl(j5) - i71 : Constraints.m823getMaxWidthimpl(j5) - i72;
                                                    boolean z11 = z9;
                                                    if (!z || iM822getMaxHeightimpl > 0) {
                                                        lazyLayoutMeasureScope = lazyLayoutMeasureScope4;
                                                        j = (iMo52roundToPx0680j_4 << 32) | (iMo52roundToPx0680j_43 & 4294967295L);
                                                        IntOffset.Companion companion3 = IntOffset.Companion;
                                                    } else {
                                                        if (!z11) {
                                                            iMo52roundToPx0680j_4 += iM822getMaxHeightimpl;
                                                        }
                                                        if (z11) {
                                                            iMo52roundToPx0680j_43 += iM822getMaxHeightimpl;
                                                        }
                                                        lazyLayoutMeasureScope = lazyLayoutMeasureScope4;
                                                        j = (iMo52roundToPx0680j_4 << 32) | (iMo52roundToPx0680j_43 & 4294967295L);
                                                        IntOffset.Companion companion4 = IntOffset.Companion;
                                                    }
                                                    long j6 = j;
                                                    ((PageSize.Fill) pageSize).getClass();
                                                    int i76 = iM822getMaxHeightimpl < 0 ? 0 : iM822getMaxHeightimpl;
                                                    pagerState.premeasureConstraints = ConstraintsKt.Constraints$default(0, orientation == orientation4 ? Constraints.m823getMaxWidthimpl(jM835offsetNN6EwU) : i76, 0, orientation != orientation4 ? Constraints.m822getMaxHeightimpl(jM835offsetNN6EwU) : i76, 5);
                                                    PagerLazyLayoutItemProvider pagerLazyLayoutItemProvider = (PagerLazyLayoutItemProvider) kProperty03.invoke();
                                                    int i77 = iM822getMaxHeightimpl + i74 + i75;
                                                    Snapshot.Companion companion5 = Snapshot.Companion;
                                                    PagerState pagerState4 = pagerState;
                                                    SnapPosition snapPosition4 = snapPosition;
                                                    companion5.getClass();
                                                    Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                                                    Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
                                                    Snapshot snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                                                    long j7 = jM835offsetNN6EwU;
                                                    try {
                                                        int currentPage = pagerState4.getCurrentPage();
                                                        PagerScrollPosition pagerScrollPosition = pagerState4.scrollPosition;
                                                        int iFindIndexByKey = LazyLayoutItemProviderKt.findIndexByKey(pagerLazyLayoutItemProvider, currentPage, pagerScrollPosition.lastKnownCurrentPageKey);
                                                        if (currentPage != iFindIndexByKey) {
                                                            i40 = i72;
                                                            ((SnapshotMutableIntStateImpl) pagerScrollPosition.currentPage$delegate).setIntValue(iFindIndexByKey);
                                                            pagerScrollPosition.nearestRangeState.update(currentPage);
                                                        } else {
                                                            i40 = i72;
                                                        }
                                                        pagerState4.getCurrentPage();
                                                        float currentPageOffsetFraction = pagerState4.getCurrentPageOffsetFraction();
                                                        pagerState4.getPageCount();
                                                        int i78 = i76 + iMo52roundToPx0680j_45;
                                                        int iRoundToInt = MathKt__MathJVMKt.roundToInt(snapPosition4.position(i77, i76, i74, i75) - (currentPageOffsetFraction * i78));
                                                        Unit unit = Unit.INSTANCE;
                                                        Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                                                        PagerState pagerState5 = pagerState;
                                                        List listCalculateLazyLayoutPinnedIndices = LazyLayoutBeyondBoundsStateKt.calculateLazyLayoutPinnedIndices(pagerLazyLayoutItemProvider, pagerState5.pinnedPages, pagerState5.beyondBoundsInfo);
                                                        int iIntValue = ((Number) function02.invoke()).intValue();
                                                        final MutableState mutableState = pagerState.placementScopeInvalidator;
                                                        final Orientation orientation5 = orientation;
                                                        int i79 = iRoundToInt;
                                                        final Alignment.Vertical vertical4 = vertical3;
                                                        List list2 = listCalculateLazyLayoutPinnedIndices;
                                                        final Alignment.Horizontal horizontal2 = horizontal;
                                                        boolean z12 = z;
                                                        int i80 = i76;
                                                        int i81 = i17;
                                                        SnapPosition snapPosition5 = snapPosition;
                                                        CoroutineScope coroutineScope3 = coroutineScope;
                                                        PagerLazyLayoutItemProvider pagerLazyLayoutItemProvider2 = pagerLazyLayoutItemProvider;
                                                        boolean z13 = z12;
                                                        final LazyLayoutMeasureScope lazyLayoutMeasureScope5 = lazyLayoutMeasureScope;
                                                        final int i82 = i40;
                                                        Function3 function3 = new Function3() { // from class: androidx.compose.foundation.pager.PagerMeasurePolicyKt$rememberPagerMeasurePolicy$1$1$measureResult$1
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(3);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function3
                                                            public final Object invoke(Object obj8, Object obj9, Object obj10) {
                                                                int iIntValue2 = ((Number) obj8).intValue();
                                                                int iIntValue3 = ((Number) obj9).intValue();
                                                                LazyLayoutMeasureScope lazyLayoutMeasureScope6 = lazyLayoutMeasureScope5;
                                                                int iM834constrainWidthK40F9xA = ConstraintsKt.m834constrainWidthK40F9xA(iIntValue2 + i82, j5);
                                                                int iM833constrainHeightK40F9xA = ConstraintsKt.m833constrainHeightK40F9xA(iIntValue3 + i71, j5);
                                                                Map mapEmptyMap = MapsKt__MapsKt.emptyMap();
                                                                return ((LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope6).subcomposeMeasureScope.layout$1(iM834constrainWidthK40F9xA, iM833constrainHeightK40F9xA, mapEmptyMap, (Function1) obj10);
                                                            }
                                                        };
                                                        if (i74 < 0) {
                                                            InlineClassHelperKt.throwIllegalArgumentException("negative beforeContentPadding");
                                                        }
                                                        if (i75 < 0) {
                                                            InlineClassHelperKt.throwIllegalArgumentException("negative afterContentPadding");
                                                        }
                                                        int i83 = i78 < 0 ? 0 : i78;
                                                        if (iIntValue <= 0) {
                                                            pagerMeasureResult = new PagerMeasureResult(EmptyList.INSTANCE, i80, iMo52roundToPx0680j_45, i75, orientation5, -i74, iM822getMaxHeightimpl + i75, false, i81, null, null, 0.0f, 0, false, snapPosition5, (MeasureResult) function3.invoke(Integer.valueOf(Constraints.m825getMinWidthimpl(j7)), Integer.valueOf(Constraints.m824getMinHeightimpl(j7)), new Function1() { // from class: androidx.compose.foundation.pager.PagerMeasureKt$measurePager$4
                                                                @Override // kotlin.jvm.functions.Function1
                                                                /* renamed from: invoke */
                                                                public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj8) {
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }), false, null, null, coroutineScope3, 393216, null);
                                                            subcomposeMeasureScope = subcomposeMeasureScope2;
                                                        } else {
                                                            int i84 = i75;
                                                            int i85 = i80;
                                                            Function3 function32 = function3;
                                                            long jConstraints$default = ConstraintsKt.Constraints$default(0, orientation5 == orientation4 ? Constraints.m823getMaxWidthimpl(j7) : i85, 0, orientation5 != orientation4 ? Constraints.m822getMaxHeightimpl(j7) : i85, 5);
                                                            int i86 = iFindIndexByKey;
                                                            while (i86 > 0 && i79 > 0) {
                                                                i86--;
                                                                i79 -= i83;
                                                            }
                                                            int i87 = i79 * (-1);
                                                            if (i86 >= iIntValue) {
                                                                i86 = iIntValue - 1;
                                                                i87 = 0;
                                                            }
                                                            ArrayDeque arrayDeque2 = new ArrayDeque();
                                                            int i88 = iM822getMaxHeightimpl;
                                                            int i89 = -i74;
                                                            int i90 = i89 + (iMo52roundToPx0680j_45 < 0 ? iMo52roundToPx0680j_45 : 0);
                                                            int i91 = i74;
                                                            int i92 = i87 + i90;
                                                            int i93 = i86;
                                                            int i94 = 0;
                                                            while (i92 < 0 && i93 > 0) {
                                                                int i95 = i93 - 1;
                                                                int i96 = i78;
                                                                int i97 = i92;
                                                                int i98 = i94;
                                                                LazyLayoutMeasureScope lazyLayoutMeasureScope6 = lazyLayoutMeasureScope5;
                                                                int i99 = iIntValue;
                                                                boolean z14 = z13;
                                                                int i100 = i90;
                                                                int i101 = i83;
                                                                long j8 = jConstraints$default;
                                                                long j9 = j6;
                                                                PagerLazyLayoutItemProvider pagerLazyLayoutItemProvider3 = pagerLazyLayoutItemProvider2;
                                                                MeasuredPage measuredPageM179getAndMeasureSGf7dI0 = PagerMeasureKt.m179getAndMeasureSGf7dI0(lazyLayoutMeasureScope6, i95, j8, pagerLazyLayoutItemProvider3, j9, orientation5, horizontal2, vertical4, subcomposeMeasureScope2.getLayoutDirection(), z14, i85);
                                                                pagerLazyLayoutItemProvider2 = pagerLazyLayoutItemProvider3;
                                                                arrayDeque2.add(0, measuredPageM179getAndMeasureSGf7dI0);
                                                                int iMax = Math.max(i98, measuredPageM179getAndMeasureSGf7dI0.crossAxisSize);
                                                                i93 = i95;
                                                                z13 = z14;
                                                                i78 = i96;
                                                                j7 = j7;
                                                                iMo52roundToPx0680j_45 = iMo52roundToPx0680j_45;
                                                                list2 = list2;
                                                                iIntValue = i99;
                                                                snapPosition5 = snapPosition5;
                                                                lazyLayoutMeasureScope5 = lazyLayoutMeasureScope6;
                                                                i94 = iMax;
                                                                i92 = i97 + i101;
                                                                i83 = i101;
                                                                i90 = i100;
                                                                i84 = i84;
                                                                jConstraints$default = j8;
                                                                function32 = function32;
                                                                j6 = j9;
                                                            }
                                                            int i102 = i92;
                                                            int i103 = i94;
                                                            LazyLayoutMeasureScope lazyLayoutMeasureScope7 = lazyLayoutMeasureScope5;
                                                            int i104 = i84;
                                                            int i105 = i78;
                                                            int i106 = iIntValue;
                                                            Function3 function33 = function32;
                                                            int i107 = iMo52roundToPx0680j_45;
                                                            final boolean z15 = z13;
                                                            long j10 = j7;
                                                            List list3 = list2;
                                                            SnapPosition snapPosition6 = snapPosition5;
                                                            int i108 = i90;
                                                            int i109 = i83;
                                                            long j11 = j6;
                                                            long j12 = jConstraints$default;
                                                            int i110 = i102;
                                                            if (i110 < i108) {
                                                                i110 = i108;
                                                            }
                                                            int i111 = i110 - i108;
                                                            int i112 = i88 + i104;
                                                            int i113 = i112 < 0 ? 0 : i112;
                                                            int i114 = i103;
                                                            LazyLayoutMeasureScope lazyLayoutMeasureScope8 = lazyLayoutMeasureScope7;
                                                            int i115 = i93;
                                                            int i116 = -i111;
                                                            int i117 = 0;
                                                            boolean z16 = false;
                                                            while (i117 < arrayDeque2.size) {
                                                                if (i116 >= i113) {
                                                                    arrayDeque2.removeAt(i117);
                                                                    z16 = true;
                                                                } else {
                                                                    i115++;
                                                                    i116 += i109;
                                                                    i117++;
                                                                }
                                                            }
                                                            int i118 = i116;
                                                            int i119 = i108;
                                                            int i120 = i111;
                                                            long j13 = j11;
                                                            int i121 = i115;
                                                            final PagerLazyLayoutItemProvider pagerLazyLayoutItemProvider4 = pagerLazyLayoutItemProvider2;
                                                            boolean z17 = z16;
                                                            int i122 = i93;
                                                            while (true) {
                                                                int i123 = i106;
                                                                if (i121 >= i123) {
                                                                    int i124 = i114;
                                                                    i41 = i109;
                                                                    i42 = i124;
                                                                    i43 = i123;
                                                                    j2 = j12;
                                                                    j3 = j13;
                                                                    i44 = i118;
                                                                    i45 = i120;
                                                                    i46 = i104;
                                                                    i47 = i121;
                                                                    i48 = i88;
                                                                    break;
                                                                }
                                                                if (i118 >= i113 && i118 > 0 && !arrayDeque2.isEmpty()) {
                                                                    int i125 = i114;
                                                                    i41 = i109;
                                                                    i42 = i125;
                                                                    i43 = i123;
                                                                    j2 = j12;
                                                                    j3 = j13;
                                                                    i44 = i118;
                                                                    i45 = i120;
                                                                    i48 = i88;
                                                                    i46 = i104;
                                                                    i47 = i121;
                                                                    break;
                                                                }
                                                                int i126 = i119;
                                                                int i127 = i114;
                                                                int i128 = i109;
                                                                int i129 = i127;
                                                                int i130 = i120;
                                                                i106 = i123;
                                                                int i131 = i104;
                                                                long j14 = j12;
                                                                int i132 = i118;
                                                                int i133 = i113;
                                                                LazyLayoutMeasureScope lazyLayoutMeasureScope9 = lazyLayoutMeasureScope8;
                                                                long j15 = j13;
                                                                MeasuredPage measuredPageM179getAndMeasureSGf7dI02 = PagerMeasureKt.m179getAndMeasureSGf7dI0(lazyLayoutMeasureScope9, i121, j14, pagerLazyLayoutItemProvider4, j15, orientation5, horizontal2, vertical4, subcomposeMeasureScope2.getLayoutDirection(), z15, i85);
                                                                lazyLayoutMeasureScope8 = lazyLayoutMeasureScope9;
                                                                int i134 = i121;
                                                                int i135 = i106 - 1;
                                                                int i136 = i132 + (i134 == i135 ? i85 : i128);
                                                                if (i136 > i126 || i134 == i135) {
                                                                    int iMax2 = Math.max(i129, measuredPageM179getAndMeasureSGf7dI02.crossAxisSize);
                                                                    arrayDeque2.addLast(measuredPageM179getAndMeasureSGf7dI02);
                                                                    i129 = iMax2;
                                                                    i69 = i130;
                                                                } else {
                                                                    i69 = i130 - i128;
                                                                    i122 = i134 + 1;
                                                                    z17 = true;
                                                                }
                                                                i121 = i134 + 1;
                                                                i114 = i129;
                                                                i109 = i128;
                                                                i119 = i126;
                                                                i118 = i136;
                                                                i120 = i69;
                                                                j13 = j15;
                                                                i113 = i133;
                                                                j12 = j14;
                                                                i104 = i131;
                                                            }
                                                            if (i44 < i48) {
                                                                int i137 = i48 - i44;
                                                                int i138 = i45 - i137;
                                                                int i139 = i44 + i137;
                                                                int iMax3 = i42;
                                                                int i140 = i138;
                                                                while (true) {
                                                                    i68 = i91;
                                                                    if (i140 >= i68 || i122 <= 0) {
                                                                        break;
                                                                    }
                                                                    int i141 = i122 - 1;
                                                                    int i142 = i47;
                                                                    i91 = i68;
                                                                    LazyLayoutMeasureScope lazyLayoutMeasureScope10 = lazyLayoutMeasureScope8;
                                                                    long j16 = j2;
                                                                    MeasuredPage measuredPageM179getAndMeasureSGf7dI03 = PagerMeasureKt.m179getAndMeasureSGf7dI0(lazyLayoutMeasureScope10, i141, j16, pagerLazyLayoutItemProvider4, j3, orientation5, horizontal2, vertical4, subcomposeMeasureScope2.getLayoutDirection(), z15, i85);
                                                                    j2 = j16;
                                                                    arrayDeque2.add(0, measuredPageM179getAndMeasureSGf7dI03);
                                                                    i140 += i41;
                                                                    lazyLayoutMeasureScope8 = lazyLayoutMeasureScope10;
                                                                    i122 = i141;
                                                                    iMax3 = Math.max(iMax3, measuredPageM179getAndMeasureSGf7dI03.crossAxisSize);
                                                                    i47 = i142;
                                                                }
                                                                i49 = i47;
                                                                i91 = i68;
                                                                int i143 = i140;
                                                                lazyLayoutMeasureScope2 = lazyLayoutMeasureScope8;
                                                                int i144 = iMax3;
                                                                if (i143 < 0) {
                                                                    int i145 = i139 + i143;
                                                                    i50 = i144;
                                                                    i52 = i145;
                                                                    i51 = 0;
                                                                } else {
                                                                    i50 = i144;
                                                                    i52 = i139;
                                                                    i51 = i143;
                                                                }
                                                            } else {
                                                                i49 = i47;
                                                                lazyLayoutMeasureScope2 = lazyLayoutMeasureScope8;
                                                                i50 = i42;
                                                                i51 = i45;
                                                                i52 = i44;
                                                            }
                                                            if (i51 < 0) {
                                                                InlineClassHelperKt.throwIllegalArgumentException("invalid currentFirstPageScrollOffset");
                                                            }
                                                            int i146 = -i51;
                                                            MeasuredPage measuredPage2 = (MeasuredPage) arrayDeque2.first();
                                                            if (i91 > 0 || i107 < 0) {
                                                                int size = arrayDeque2.getSize();
                                                                MeasuredPage measuredPage3 = measuredPage2;
                                                                int i147 = i51;
                                                                int i148 = 0;
                                                                while (i148 < size && i147 != 0) {
                                                                    i53 = i85;
                                                                    i54 = i41;
                                                                    if (i54 > i147) {
                                                                        lazyLayoutMeasureScope3 = lazyLayoutMeasureScope2;
                                                                        break;
                                                                    }
                                                                    lazyLayoutMeasureScope3 = lazyLayoutMeasureScope2;
                                                                    if (i148 == arrayDeque2.getSize() - 1) {
                                                                        break;
                                                                    }
                                                                    i147 -= i54;
                                                                    i148++;
                                                                    measuredPage3 = (MeasuredPage) arrayDeque2.get(i148);
                                                                    i41 = i54;
                                                                    i85 = i53;
                                                                    lazyLayoutMeasureScope2 = lazyLayoutMeasureScope3;
                                                                }
                                                                lazyLayoutMeasureScope3 = lazyLayoutMeasureScope2;
                                                                i53 = i85;
                                                                i54 = i41;
                                                                i55 = i147;
                                                                measuredPage = measuredPage3;
                                                            } else {
                                                                lazyLayoutMeasureScope3 = lazyLayoutMeasureScope2;
                                                                measuredPage = measuredPage2;
                                                                i53 = i85;
                                                                i54 = i41;
                                                                i55 = i51;
                                                            }
                                                            int i149 = i54;
                                                            final int i150 = i53;
                                                            final LazyLayoutMeasureScope lazyLayoutMeasureScope11 = lazyLayoutMeasureScope3;
                                                            ArrayDeque arrayDeque3 = arrayDeque2;
                                                            MeasuredPage measuredPage4 = measuredPage;
                                                            final long j17 = j2;
                                                            Function1 function12 = new Function1() { // from class: androidx.compose.foundation.pager.PagerMeasureKt$measurePager$extraPagesBefore$1
                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                {
                                                                    super(1);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function1
                                                                /* renamed from: invoke */
                                                                public final Object mo781invoke(Object obj8) {
                                                                    int iIntValue2 = ((Number) obj8).intValue();
                                                                    LazyLayoutMeasureScope lazyLayoutMeasureScope12 = lazyLayoutMeasureScope11;
                                                                    return PagerMeasureKt.m179getAndMeasureSGf7dI0(lazyLayoutMeasureScope12, iIntValue2, j17, pagerLazyLayoutItemProvider4, j3, orientation5, horizontal2, vertical4, ((LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope12).subcomposeMeasureScope.getLayoutDirection(), z15, i150);
                                                                }
                                                            };
                                                            int iMax4 = Math.max(0, i122 - i81);
                                                            int i151 = i122 - 1;
                                                            if (iMax4 <= i151) {
                                                                ArrayList arrayList7 = null;
                                                                while (true) {
                                                                    if (arrayList7 == null) {
                                                                        arrayList7 = new ArrayList();
                                                                    }
                                                                    j4 = j17;
                                                                    arrayList = arrayList7;
                                                                    arrayList.add(function12.mo781invoke(Integer.valueOf(i151)));
                                                                    if (i151 == iMax4) {
                                                                        break;
                                                                    }
                                                                    i151--;
                                                                    arrayList7 = arrayList;
                                                                    j17 = j4;
                                                                }
                                                            } else {
                                                                j4 = j17;
                                                                arrayList = null;
                                                            }
                                                            List list4 = list3;
                                                            List list5 = list4;
                                                            int size2 = list5.size();
                                                            List arrayList8 = arrayList;
                                                            int i152 = 0;
                                                            while (i152 < size2) {
                                                                List list6 = list4;
                                                                int iIntValue2 = ((Number) list4.get(i152)).intValue();
                                                                if (iIntValue2 < iMax4) {
                                                                    if (arrayList8 == null) {
                                                                        arrayList8 = new ArrayList();
                                                                    }
                                                                    i67 = i152;
                                                                    List list7 = arrayList8;
                                                                    list7.add(function12.mo781invoke(Integer.valueOf(iIntValue2)));
                                                                    arrayList8 = list7;
                                                                } else {
                                                                    i67 = i152;
                                                                }
                                                                i152 = i67 + 1;
                                                                list4 = list6;
                                                            }
                                                            List list8 = list4;
                                                            if (arrayList8 == null) {
                                                                arrayList8 = EmptyList.INSTANCE;
                                                            }
                                                            List list9 = arrayList8;
                                                            int size3 = list9.size();
                                                            int iMax5 = i50;
                                                            for (int i153 = 0; i153 < size3; i153++) {
                                                                iMax5 = Math.max(iMax5, ((MeasuredPage) list9.get(i153)).crossAxisSize);
                                                            }
                                                            int i154 = ((MeasuredPage) arrayDeque3.last()).index;
                                                            int i155 = iMax5;
                                                            final boolean z18 = z15;
                                                            final long j18 = j4;
                                                            Function1 function13 = new Function1() { // from class: androidx.compose.foundation.pager.PagerMeasureKt$measurePager$extraPagesAfter$1
                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                {
                                                                    super(1);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function1
                                                                /* renamed from: invoke */
                                                                public final Object mo781invoke(Object obj8) {
                                                                    int iIntValue3 = ((Number) obj8).intValue();
                                                                    LazyLayoutMeasureScope lazyLayoutMeasureScope12 = lazyLayoutMeasureScope11;
                                                                    return PagerMeasureKt.m179getAndMeasureSGf7dI0(lazyLayoutMeasureScope12, iIntValue3, j18, pagerLazyLayoutItemProvider4, j3, orientation5, horizontal2, vertical4, ((LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope12).subcomposeMeasureScope.getLayoutDirection(), z18, i150);
                                                                }
                                                            };
                                                            int iMin = Math.min(i154 + i81, i43 - 1);
                                                            int i156 = i154 + 1;
                                                            List arrayList9 = null;
                                                            if (i156 <= iMin) {
                                                                while (true) {
                                                                    if (arrayList9 == null) {
                                                                        arrayList9 = new ArrayList();
                                                                    }
                                                                    arrayList9.add(function13.mo781invoke(Integer.valueOf(i156)));
                                                                    if (i156 == iMin) {
                                                                        break;
                                                                    }
                                                                    i156++;
                                                                }
                                                            }
                                                            int size4 = list5.size();
                                                            int i157 = 0;
                                                            while (i157 < size4) {
                                                                int iIntValue3 = ((Number) list8.get(i157)).intValue();
                                                                if (iMin + 1 <= iIntValue3) {
                                                                    i66 = i43;
                                                                    if (iIntValue3 < i66) {
                                                                        if (arrayList9 == null) {
                                                                            arrayList9 = new ArrayList();
                                                                        }
                                                                        arrayList9.add(function13.mo781invoke(Integer.valueOf(iIntValue3)));
                                                                    }
                                                                } else {
                                                                    i66 = i43;
                                                                }
                                                                i157++;
                                                                i43 = i66;
                                                            }
                                                            int i158 = i43;
                                                            if (arrayList9 == null) {
                                                                arrayList9 = EmptyList.INSTANCE;
                                                            }
                                                            int size5 = arrayList9.size();
                                                            int iMax6 = i155;
                                                            for (int i159 = 0; i159 < size5; i159++) {
                                                                iMax6 = Math.max(iMax6, ((MeasuredPage) arrayList9.get(i159)).crossAxisSize);
                                                            }
                                                            boolean z19 = Intrinsics.areEqual(measuredPage4, arrayDeque3.first()) && list9.isEmpty() && arrayList9.isEmpty();
                                                            Orientation orientation6 = Orientation.Vertical;
                                                            int iM834constrainWidthK40F9xA = ConstraintsKt.m834constrainWidthK40F9xA(orientation5 == orientation6 ? iMax6 : i52, j10);
                                                            if (orientation5 == orientation6) {
                                                                iMax6 = i52;
                                                            }
                                                            int iM833constrainHeightK40F9xA = ConstraintsKt.m833constrainHeightK40F9xA(iMax6, j10);
                                                            if (orientation5 == orientation6) {
                                                                i56 = iM833constrainHeightK40F9xA;
                                                            } else {
                                                                i56 = iM833constrainHeightK40F9xA;
                                                                iM833constrainHeightK40F9xA = iM834constrainWidthK40F9xA;
                                                            }
                                                            boolean z20 = i52 < Math.min(iM833constrainHeightK40F9xA, i48);
                                                            if (!z20 || i146 == 0) {
                                                                i57 = i146;
                                                            } else {
                                                                StringBuilder sb = new StringBuilder("non-zero pagesScrollOffset=");
                                                                i57 = i146;
                                                                sb.append(i57);
                                                                InlineClassHelperKt.throwIllegalStateException(sb.toString());
                                                            }
                                                            ArrayList arrayList10 = new ArrayList(arrayList9.size() + list9.size() + arrayDeque3.getSize());
                                                            if (z20) {
                                                                if (!list9.isEmpty() || !arrayList9.isEmpty()) {
                                                                    InlineClassHelperKt.throwIllegalArgumentException("No extra pages");
                                                                }
                                                                int size6 = arrayDeque3.getSize();
                                                                int[] iArr = new int[size6];
                                                                for (int i160 = 0; i160 < size6; i160++) {
                                                                    iArr[i160] = i150;
                                                                }
                                                                int[] iArr2 = new int[size6];
                                                                Arrangement.Absolute absolute = Arrangement.Absolute.INSTANCE;
                                                                int i161 = i56;
                                                                z7 = z19;
                                                                float fMo55toDpu2uoSUM = subcomposeMeasureScope2.mo55toDpu2uoSUM(i107);
                                                                absolute.getClass();
                                                                subcomposeMeasureScope = subcomposeMeasureScope2;
                                                                i107 = i107;
                                                                Arrangement.SpacedAligned spacedAligned = new Arrangement.SpacedAligned(fMo55toDpu2uoSUM, false, null, null);
                                                                if (orientation5 == Orientation.Vertical) {
                                                                    spacedAligned.arrange(lazyLayoutMeasureScope11, iM833constrainHeightK40F9xA, iArr, iArr2);
                                                                    arrayList2 = arrayList10;
                                                                    i58 = i161;
                                                                } else {
                                                                    i58 = i161;
                                                                    arrayList2 = arrayList10;
                                                                    spacedAligned.arrange(lazyLayoutMeasureScope11, iM833constrainHeightK40F9xA, iArr, LayoutDirection.Ltr, iArr2);
                                                                }
                                                                IntRange indices = ArraysKt___ArraysKt.getIndices(iArr2);
                                                                IntRange intRangeReversed = indices;
                                                                if (z18) {
                                                                    intRangeReversed = RangesKt___RangesKt.reversed(indices);
                                                                }
                                                                int i162 = intRangeReversed.first;
                                                                int i163 = intRangeReversed.last;
                                                                int i164 = intRangeReversed.step;
                                                                if ((i164 > 0 && i162 <= i163) || (i164 < 0 && i163 <= i162)) {
                                                                    while (true) {
                                                                        int i165 = iArr2[i162];
                                                                        if (z18) {
                                                                            i64 = i164;
                                                                            i65 = (size6 - i162) - 1;
                                                                        } else {
                                                                            i64 = i164;
                                                                            i65 = i162;
                                                                        }
                                                                        int i166 = iM833constrainHeightK40F9xA;
                                                                        arrayDeque = arrayDeque3;
                                                                        MeasuredPage measuredPage5 = (MeasuredPage) arrayDeque.get(i65);
                                                                        if (z18) {
                                                                            i165 = (i166 - i165) - measuredPage5.size;
                                                                        }
                                                                        measuredPage5.position(i165, iM834constrainWidthK40F9xA, i58);
                                                                        arrayList2.add(measuredPage5);
                                                                        if (i162 == i163) {
                                                                            break;
                                                                        }
                                                                        i162 += i64;
                                                                        arrayDeque3 = arrayDeque;
                                                                        iM833constrainHeightK40F9xA = i166;
                                                                        i164 = i64;
                                                                    }
                                                                } else {
                                                                    arrayDeque = arrayDeque3;
                                                                }
                                                                list = list9;
                                                            } else {
                                                                subcomposeMeasureScope = subcomposeMeasureScope2;
                                                                arrayList2 = arrayList10;
                                                                z7 = z19;
                                                                arrayDeque = arrayDeque3;
                                                                i58 = i56;
                                                                int size7 = list9.size();
                                                                int i167 = i57;
                                                                int i168 = 0;
                                                                while (i168 < size7) {
                                                                    int i169 = size7;
                                                                    MeasuredPage measuredPage6 = (MeasuredPage) list9.get(i168);
                                                                    i167 -= i105;
                                                                    measuredPage6.position(i167, iM834constrainWidthK40F9xA, i58);
                                                                    arrayList2.add(measuredPage6);
                                                                    i168++;
                                                                    size7 = i169;
                                                                }
                                                                list = list9;
                                                                int size8 = arrayDeque.getSize();
                                                                for (int i170 = 0; i170 < size8; i170++) {
                                                                    MeasuredPage measuredPage7 = (MeasuredPage) arrayDeque.get(i170);
                                                                    measuredPage7.position(i57, iM834constrainWidthK40F9xA, i58);
                                                                    arrayList2.add(measuredPage7);
                                                                    i57 += i105;
                                                                }
                                                                int size9 = arrayList9.size();
                                                                for (int i171 = 0; i171 < size9; i171++) {
                                                                    MeasuredPage measuredPage8 = (MeasuredPage) arrayList9.get(i171);
                                                                    measuredPage8.position(i57, iM834constrainWidthK40F9xA, i58);
                                                                    arrayList2.add(measuredPage8);
                                                                    i57 += i105;
                                                                }
                                                            }
                                                            if (z7) {
                                                                arrayList3 = arrayList2;
                                                            } else {
                                                                arrayList3 = new ArrayList(arrayList2.size());
                                                                int size10 = arrayList2.size();
                                                                int i172 = 0;
                                                                while (i172 < size10) {
                                                                    Object obj8 = arrayList2.get(i172);
                                                                    int i173 = size10;
                                                                    MeasuredPage measuredPage9 = (MeasuredPage) obj8;
                                                                    ArrayDeque arrayDeque4 = arrayDeque;
                                                                    int i174 = i172;
                                                                    if (measuredPage9.index >= ((MeasuredPage) arrayDeque4.first()).index) {
                                                                        if (measuredPage9.index <= ((MeasuredPage) arrayDeque4.last()).index) {
                                                                            arrayList3.add(obj8);
                                                                        }
                                                                    }
                                                                    i172 = i174 + 1;
                                                                    size10 = i173;
                                                                    arrayDeque = arrayDeque4;
                                                                }
                                                            }
                                                            ArrayDeque arrayDeque5 = arrayDeque;
                                                            if (list.isEmpty()) {
                                                                arrayList4 = EmptyList.INSTANCE;
                                                            } else {
                                                                arrayList4 = new ArrayList(arrayList2.size());
                                                                int size11 = arrayList2.size();
                                                                int i175 = 0;
                                                                while (i175 < size11) {
                                                                    Object obj9 = arrayList2.get(i175);
                                                                    int i176 = size11;
                                                                    if (((MeasuredPage) obj9).index < ((MeasuredPage) arrayDeque5.first()).index) {
                                                                        arrayList4.add(obj9);
                                                                    }
                                                                    i175++;
                                                                    size11 = i176;
                                                                }
                                                            }
                                                            List list10 = arrayList4;
                                                            if (arrayList9.isEmpty()) {
                                                                arrayList5 = EmptyList.INSTANCE;
                                                            } else {
                                                                arrayList5 = new ArrayList(arrayList2.size());
                                                                int size12 = arrayList2.size();
                                                                for (int i177 = 0; i177 < size12; i177++) {
                                                                    Object obj10 = arrayList2.get(i177);
                                                                    if (((MeasuredPage) obj10).index > ((MeasuredPage) arrayDeque5.last()).index) {
                                                                        arrayList5.add(obj10);
                                                                    }
                                                                }
                                                            }
                                                            List list11 = arrayList5;
                                                            if (arrayList3.isEmpty()) {
                                                                orientation2 = orientation5;
                                                                i63 = i58;
                                                                z8 = z18;
                                                                i59 = i149;
                                                                i60 = i77;
                                                                i61 = i91;
                                                                snapPosition3 = snapPosition6;
                                                                i62 = i46;
                                                                obj7 = null;
                                                                arrayList6 = arrayList3;
                                                            } else {
                                                                Object obj11 = arrayList3.get(0);
                                                                int i178 = ((MeasuredPage) obj11).offset;
                                                                Object obj12 = obj11;
                                                                orientation2 = orientation5;
                                                                i59 = i149;
                                                                i60 = i77;
                                                                i61 = i91;
                                                                snapPosition3 = snapPosition6;
                                                                i62 = i46;
                                                                float f6 = -Math.abs(i178 - snapPosition3.position(i60, i59, i61, i62));
                                                                int size13 = arrayList3.size() - 1;
                                                                if (1 <= size13) {
                                                                    i63 = i58;
                                                                    float f7 = f6;
                                                                    int i179 = 1;
                                                                    while (true) {
                                                                        obj7 = arrayList3.get(i179);
                                                                        arrayList6 = arrayList3;
                                                                        z8 = z18;
                                                                        float f8 = -Math.abs(((MeasuredPage) obj7).offset - snapPosition3.position(i60, i59, i61, i62));
                                                                        if (Float.compare(f7, f8) < 0) {
                                                                            f7 = f8;
                                                                        } else {
                                                                            obj7 = obj12;
                                                                        }
                                                                        z10 = true;
                                                                        if (i179 == size13) {
                                                                            break;
                                                                        }
                                                                        i179++;
                                                                        obj12 = obj7;
                                                                        arrayList3 = arrayList6;
                                                                        z18 = z8;
                                                                    }
                                                                } else {
                                                                    arrayList6 = arrayList3;
                                                                    z10 = true;
                                                                    i63 = i58;
                                                                    z8 = z18;
                                                                    obj7 = obj12;
                                                                }
                                                            }
                                                            MeasuredPage measuredPage10 = (MeasuredPage) obj7;
                                                            pagerMeasureResult = new PagerMeasureResult(arrayList6, i150, i107, i62, orientation2, i89, i112, z8, i81, measuredPage4, measuredPage10, i59 == 0 ? 0.0f : RangesKt___RangesKt.coerceIn((snapPosition3.position(i60, i150, i61, i62) - (measuredPage10 != null ? measuredPage10.offset : 0)) / i59, -0.5f, 0.5f), i55, (i49 < i158 || i52 > i48) ? z10 : false, snapPosition3, (MeasureResult) function33.invoke(Integer.valueOf(iM834constrainWidthK40F9xA), Integer.valueOf(i63), new Function1() { // from class: androidx.compose.foundation.pager.PagerMeasureKt$measurePager$14
                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                {
                                                                    super(1);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function1
                                                                /* renamed from: invoke */
                                                                public final Object mo781invoke(Object obj13) {
                                                                    Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj13;
                                                                    final List<MeasuredPage> list12 = arrayList2;
                                                                    Function1 function14 = new Function1() { // from class: androidx.compose.foundation.pager.PagerMeasureKt$measurePager$14.1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(1);
                                                                        }

                                                                        @Override // kotlin.jvm.functions.Function1
                                                                        /* renamed from: invoke */
                                                                        public final Object mo781invoke(Object obj14) {
                                                                            List<MeasuredPage> list13;
                                                                            char c;
                                                                            int i180;
                                                                            Placeable.PlacementScope placementScope2 = (Placeable.PlacementScope) obj14;
                                                                            List<MeasuredPage> list14 = list12;
                                                                            int size14 = list14.size();
                                                                            for (int i181 = 0; i181 < size14; i181++) {
                                                                                MeasuredPage measuredPage11 = list14.get(i181);
                                                                                if (measuredPage11.mainAxisLayoutSize == Integer.MIN_VALUE) {
                                                                                    InlineClassHelperKt.throwIllegalArgumentException("position() should be called first");
                                                                                }
                                                                                int size15 = measuredPage11.placeables.size();
                                                                                int i182 = 0;
                                                                                while (i182 < size15) {
                                                                                    Placeable placeable = (Placeable) measuredPage11.placeables.get(i182);
                                                                                    int i183 = i182 * 2;
                                                                                    int[] iArr3 = measuredPage11.placeableOffsets;
                                                                                    long j19 = (iArr3[i183] << 32) | (iArr3[i183 + 1] & 4294967295L);
                                                                                    IntOffset.Companion companion6 = IntOffset.Companion;
                                                                                    boolean z21 = measuredPage11.reverseLayout;
                                                                                    boolean z22 = measuredPage11.isVertical;
                                                                                    if (z21) {
                                                                                        if (z22) {
                                                                                            c = ' ';
                                                                                            list13 = list14;
                                                                                            i180 = (int) (j19 >> 32);
                                                                                        } else {
                                                                                            c = ' ';
                                                                                            list13 = list14;
                                                                                            i180 = (measuredPage11.mainAxisLayoutSize - ((int) (j19 >> 32))) - (z22 ? placeable.height : placeable.width);
                                                                                        }
                                                                                        j19 = ((z22 ? (measuredPage11.mainAxisLayoutSize - ((int) (j19 & 4294967295L))) - (z22 ? placeable.height : placeable.width) : (int) (j19 & 4294967295L)) & 4294967295L) | (i180 << c);
                                                                                    } else {
                                                                                        list13 = list14;
                                                                                    }
                                                                                    long jM853plusqkQi6aY = IntOffset.m853plusqkQi6aY(j19, measuredPage11.visualOffset);
                                                                                    if (z22) {
                                                                                        Placeable.PlacementScope.m631placeWithLayeraW9wM$default(placementScope2, placeable, jM853plusqkQi6aY, null, 6);
                                                                                    } else {
                                                                                        Placeable.PlacementScope.m629placeRelativeWithLayeraW9wM$default(placementScope2, placeable, jM853plusqkQi6aY);
                                                                                    }
                                                                                    i182++;
                                                                                    list14 = list13;
                                                                                }
                                                                            }
                                                                            return Unit.INSTANCE;
                                                                        }
                                                                    };
                                                                    placementScope.motionFrameOfReferencePlacement = true;
                                                                    function14.mo781invoke(placementScope);
                                                                    placementScope.motionFrameOfReferencePlacement = false;
                                                                    mutableState.getValue();
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }), z17, list10, list11, coroutineScope3);
                                                        }
                                                        PagerMeasureResult pagerMeasureResult2 = pagerMeasureResult;
                                                        pagerState.applyMeasureResult$foundation_release(pagerMeasureResult2, subcomposeMeasureScope.isLookingAhead(), false);
                                                        return pagerMeasureResult2;
                                                    } catch (Throwable th) {
                                                        Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                                                        throw th;
                                                    }
                                                }
                                            };
                                            pagerState3 = pagerState;
                                            f4 = f5;
                                            kProperty0 = kProperty03;
                                            composerImpl.updateRememberedValue(obj);
                                        } else {
                                            coroutineScope = coroutineScope2;
                                            i17 = i19;
                                            obj2 = obj4;
                                            obj = objRememberedValue7;
                                            f4 = f3;
                                            composerImpl = composerImpl2;
                                            kProperty0 = kProperty03;
                                            i20 = 4;
                                            pagerState3 = pagerState;
                                        }
                                        Function2 function2 = (Function2) obj;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        Orientation orientation2 = Orientation.Vertical;
                                        final boolean z7 = orientation != orientation2;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.foundation.pager.rememberPagerSemanticState (PagerSemantics.kt:26)");
                                        }
                                        zChanged3 = (((i31 ^ 6) <= i20 && composerImpl.changed(pagerState3)) || (i30 & 6) == i20) | composerImpl.changed(z7);
                                        Object objRememberedValue8 = composerImpl.rememberedValue();
                                        if (zChanged3) {
                                            obj3 = obj2;
                                        } else {
                                            obj3 = obj2;
                                            if (objRememberedValue8 == obj3) {
                                            }
                                            LazyLayoutSemanticState lazyLayoutSemanticState = (LazyLayoutSemanticState) objRememberedValue8;
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            z4 = ((i12 & 458752) != 131072) | (i29 != 32);
                                            objRememberedValue = composerImpl.rememberedValue();
                                            if (!z4 || objRememberedValue == obj3) {
                                                objRememberedValue = new PagerWrapperFlingBehavior(targetedFlingBehavior, pagerState3);
                                                composerImpl.updateRememberedValue(objRememberedValue);
                                            }
                                            PagerWrapperFlingBehavior pagerWrapperFlingBehavior = (PagerWrapperFlingBehavior) objRememberedValue;
                                            bringIntoViewSpec = (BringIntoViewSpec) composerImpl.consume(BringIntoViewSpec_androidKt.LocalBringIntoViewSpec);
                                            zChanged4 = (i29 != 32) | composerImpl.changed(bringIntoViewSpec);
                                            objRememberedValue2 = composerImpl.rememberedValue();
                                            if (!zChanged4 || objRememberedValue2 == obj3) {
                                                objRememberedValue2 = new PagerBringIntoViewSpec(pagerState3, bringIntoViewSpec);
                                                composerImpl.updateRememberedValue(objRememberedValue2);
                                            }
                                            PagerBringIntoViewSpec pagerBringIntoViewSpec = (PagerBringIntoViewSpec) objRememberedValue2;
                                            if (z2) {
                                                kProperty02 = kProperty0;
                                                composerImpl.startReplaceGroup(1935788068);
                                                composerImpl.end(false);
                                                modifierLazyLayoutBeyondBoundsModifier = Modifier.Companion;
                                            } else {
                                                composerImpl.startReplaceGroup(1935359245);
                                                Modifier.Companion companion3 = Modifier.Companion;
                                                int i40 = i31 | ((i12 >> 21) & 112);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("androidx.compose.foundation.pager.rememberPagerBeyondBoundsState (PagerBeyondBoundsModifier.kt:25)");
                                                }
                                                kProperty02 = kProperty0;
                                                boolean z8 = ((((i40 & 14) ^ 6) > 4 && composerImpl.changed(pagerState3)) || (i40 & 6) == 4) | ((((i40 & 112) ^ 48) > 32 && composerImpl.changed(i17)) || (i40 & 48) == 32);
                                                Object objRememberedValue9 = composerImpl.rememberedValue();
                                                if (z8 || objRememberedValue9 == obj3) {
                                                    objRememberedValue9 = new PagerBeyondBoundsState(pagerState3, i17);
                                                    composerImpl.updateRememberedValue(objRememberedValue9);
                                                }
                                                PagerBeyondBoundsState pagerBeyondBoundsState = (PagerBeyondBoundsState) objRememberedValue9;
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                modifierLazyLayoutBeyondBoundsModifier = LazyLayoutBeyondBoundsModifierLocalKt.lazyLayoutBeyondBoundsModifier(companion3, pagerBeyondBoundsState, pagerState3.beyondBoundsInfo, z, orientation);
                                                composerImpl.end(false);
                                            }
                                            Modifier modifierLazyLayoutSemantics = LazyLayoutSemanticsKt.lazyLayoutSemantics(modifier.then(pagerState3.remeasurementModifier).then(pagerState3.awaitLayoutModifier), kProperty02, lazyLayoutSemanticState, orientation, z2, z);
                                            final boolean z9 = orientation != orientation2;
                                            Modifier modifierThen = !z2 ? modifierLazyLayoutSemantics.then(SemanticsModifierKt.semantics(Modifier.Companion, false, new Function1() { // from class: androidx.compose.foundation.pager.PagerKt$pagerSemantics$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj5) {
                                                    SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj5;
                                                    if (z9) {
                                                        final PagerState pagerState4 = pagerState3;
                                                        final CoroutineScope coroutineScope3 = coroutineScope;
                                                        Function0 function03 = new Function0() { // from class: androidx.compose.foundation.pager.PagerKt$pagerSemantics$1.1
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(0);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                boolean z10;
                                                                PagerState pagerState5 = pagerState4;
                                                                CoroutineScope coroutineScope4 = coroutineScope3;
                                                                if (pagerState5.getCanScrollBackward()) {
                                                                    BuildersKt.launch$default(coroutineScope4, null, null, new PagerKt$pagerSemantics$performBackwardPaging$1(pagerState5, null), 3);
                                                                    z10 = true;
                                                                } else {
                                                                    z10 = false;
                                                                }
                                                                return Boolean.valueOf(z10);
                                                            }
                                                        };
                                                        KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                                                        SemanticsActions.INSTANCE.getClass();
                                                        SemanticsConfiguration semanticsConfiguration = (SemanticsConfiguration) semanticsPropertyReceiver;
                                                        semanticsConfiguration.set(SemanticsActions.PageUp, new AccessibilityAction(null, function03));
                                                        final PagerState pagerState5 = pagerState3;
                                                        final CoroutineScope coroutineScope4 = coroutineScope;
                                                        semanticsConfiguration.set(SemanticsActions.PageDown, new AccessibilityAction(null, new Function0() { // from class: androidx.compose.foundation.pager.PagerKt$pagerSemantics$1.2
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(0);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                boolean z10;
                                                                PagerState pagerState6 = pagerState5;
                                                                CoroutineScope coroutineScope5 = coroutineScope4;
                                                                if (pagerState6.getCanScrollForward()) {
                                                                    BuildersKt.launch$default(coroutineScope5, null, null, new PagerKt$pagerSemantics$performForwardPaging$1(pagerState6, null), 3);
                                                                    z10 = true;
                                                                } else {
                                                                    z10 = false;
                                                                }
                                                                return Boolean.valueOf(z10);
                                                            }
                                                        }));
                                                    } else {
                                                        final PagerState pagerState6 = pagerState3;
                                                        final CoroutineScope coroutineScope5 = coroutineScope;
                                                        Function0 function04 = new Function0() { // from class: androidx.compose.foundation.pager.PagerKt$pagerSemantics$1.3
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(0);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                boolean z10;
                                                                PagerState pagerState7 = pagerState6;
                                                                CoroutineScope coroutineScope6 = coroutineScope5;
                                                                if (pagerState7.getCanScrollBackward()) {
                                                                    BuildersKt.launch$default(coroutineScope6, null, null, new PagerKt$pagerSemantics$performBackwardPaging$1(pagerState7, null), 3);
                                                                    z10 = true;
                                                                } else {
                                                                    z10 = false;
                                                                }
                                                                return Boolean.valueOf(z10);
                                                            }
                                                        };
                                                        KProperty[] kPropertyArr2 = SemanticsPropertiesKt.$$delegatedProperties;
                                                        SemanticsActions semanticsActions = SemanticsActions.INSTANCE;
                                                        semanticsActions.getClass();
                                                        SemanticsConfiguration semanticsConfiguration2 = (SemanticsConfiguration) semanticsPropertyReceiver;
                                                        semanticsConfiguration2.set(SemanticsActions.PageLeft, new AccessibilityAction(null, function04));
                                                        final PagerState pagerState7 = pagerState3;
                                                        final CoroutineScope coroutineScope6 = coroutineScope;
                                                        Function0 function05 = new Function0() { // from class: androidx.compose.foundation.pager.PagerKt$pagerSemantics$1.4
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(0);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function0
                                                            public final Object invoke() {
                                                                boolean z10;
                                                                PagerState pagerState8 = pagerState7;
                                                                CoroutineScope coroutineScope7 = coroutineScope6;
                                                                if (pagerState8.getCanScrollForward()) {
                                                                    BuildersKt.launch$default(coroutineScope7, null, null, new PagerKt$pagerSemantics$performForwardPaging$1(pagerState8, null), 3);
                                                                    z10 = true;
                                                                } else {
                                                                    z10 = false;
                                                                }
                                                                return Boolean.valueOf(z10);
                                                            }
                                                        };
                                                        semanticsActions.getClass();
                                                        semanticsConfiguration2.set(SemanticsActions.PageRight, new AccessibilityAction(null, function05));
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            })) : modifierLazyLayoutSemantics.then(Modifier.Companion);
                                            PagerState pagerState4 = pagerState3;
                                            pagerState2 = pagerState4;
                                            LazyLayoutKt.LazyLayout(kProperty02, NestedScrollModifierKt.nestedScroll(ScrollingContainerKt.scrollingContainer(modifierThen.then(modifierLazyLayoutBeyondBoundsModifier), pagerState4, orientation, z2, z, pagerWrapperFlingBehavior, pagerState4.internalInteractionSource, false, overscrollEffect, pagerBringIntoViewSpec).then(SuspendingPointerInputFilterKt.pointerInput(Modifier.Companion, pagerState2, new PointerInputEventHandler() { // from class: androidx.compose.foundation.pager.LazyLayoutPagerKt$dragDirectionDetector$1

                                                /* renamed from: androidx.compose.foundation.pager.LazyLayoutPagerKt$dragDirectionDetector$1$1, reason: invalid class name */
                                                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                                    final /* synthetic */ PagerState $state;
                                                    final /* synthetic */ PointerInputScope $this_pointerInput;
                                                    int label;

                                                    /* renamed from: androidx.compose.foundation.pager.LazyLayoutPagerKt$dragDirectionDetector$1$1$1, reason: invalid class name and collision with other inner class name */
                                                    final class C00131 extends RestrictedSuspendLambda implements Function2 {
                                                        final /* synthetic */ PagerState $state;
                                                        private /* synthetic */ Object L$0;
                                                        Object L$1;
                                                        Object L$2;
                                                        int label;

                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        public C00131(PagerState pagerState, Continuation continuation) {
                                                            super(2, continuation);
                                                            this.$state = pagerState;
                                                        }

                                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                        public final Continuation create(Object obj, Continuation continuation) {
                                                            C00131 c00131 = new C00131(this.$state, continuation);
                                                            c00131.L$0 = obj;
                                                            return c00131;
                                                        }

                                                        @Override // kotlin.jvm.functions.Function2
                                                        public final Object invoke(Object obj, Object obj2) {
                                                            return ((C00131) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                                        }

                                                        /* JADX WARN: Code restructure failed: missing block: B:11:0x003f, code lost:
                                                        
                                                            if (r11 == r0) goto L17;
                                                         */
                                                        /* JADX WARN: Code restructure failed: missing block: B:16:0x006c, code lost:
                                                        
                                                            if (r11 == r0) goto L17;
                                                         */
                                                        /* JADX WARN: Code restructure failed: missing block: B:17:0x006e, code lost:
                                                        
                                                            return r0;
                                                         */
                                                        /* JADX WARN: Removed duplicated region for block: B:15:0x005e  */
                                                        /* JADX WARN: Removed duplicated region for block: B:25:0x0097  */
                                                        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x006c -> B:18:0x006f). Please report as a decompilation issue!!! */
                                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invokeSuspend(Object obj) {
                                                            AwaitPointerEventScope awaitPointerEventScope;
                                                            AwaitPointerEventScope awaitPointerEventScope2;
                                                            PointerInputChange pointerInputChange;
                                                            PointerInputChange pointerInputChange2;
                                                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                            int i = this.label;
                                                            if (i == 0) {
                                                                ResultKt.throwOnFailure(obj);
                                                                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                                                                PointerEventPass pointerEventPass = PointerEventPass.Initial;
                                                                this.L$0 = awaitPointerEventScope;
                                                                this.label = 1;
                                                                obj = TapGestureDetectorKt.awaitFirstDown(awaitPointerEventScope, false, pointerEventPass, this);
                                                            } else if (i == 1) {
                                                                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                                                                ResultKt.throwOnFailure(obj);
                                                            } else {
                                                                if (i != 2) {
                                                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                                }
                                                                pointerInputChange = (PointerInputChange) this.L$2;
                                                                pointerInputChange2 = (PointerInputChange) this.L$1;
                                                                awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                                                                ResultKt.throwOnFailure(obj);
                                                                PointerEvent pointerEvent = (PointerEvent) obj;
                                                                List list = pointerEvent.changes;
                                                                int size = list.size();
                                                                int i2 = 0;
                                                                while (true) {
                                                                    if (i2 >= size) {
                                                                        pointerInputChange = (PointerInputChange) pointerEvent.changes.get(0);
                                                                        break;
                                                                    }
                                                                    if (!PointerEventKt.changedToUp((PointerInputChange) list.get(i2))) {
                                                                        break;
                                                                    }
                                                                    i2++;
                                                                }
                                                                if (pointerInputChange == null) {
                                                                    ((SnapshotMutableStateImpl) this.$state.upDownDifference$delegate).setValue(Offset.m395boximpl(Offset.m402minusMKHz9U(pointerInputChange.position, pointerInputChange2.position)));
                                                                    return Unit.INSTANCE;
                                                                }
                                                                PointerEventPass pointerEventPass2 = PointerEventPass.Initial;
                                                                this.L$0 = awaitPointerEventScope2;
                                                                this.L$1 = pointerInputChange2;
                                                                this.L$2 = pointerInputChange;
                                                                this.label = 2;
                                                                obj = awaitPointerEventScope2.awaitPointerEvent(pointerEventPass2, this);
                                                            }
                                                            PagerState pagerState = this.$state;
                                                            Offset.Companion.getClass();
                                                            ((SnapshotMutableStateImpl) pagerState.upDownDifference$delegate).setValue(Offset.m395boximpl(0L));
                                                            awaitPointerEventScope2 = awaitPointerEventScope;
                                                            pointerInputChange = null;
                                                            pointerInputChange2 = (PointerInputChange) obj;
                                                            if (pointerInputChange == null) {
                                                            }
                                                        }
                                                    }

                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    public AnonymousClass1(PointerInputScope pointerInputScope, PagerState pagerState, Continuation continuation) {
                                                        super(2, continuation);
                                                        this.$this_pointerInput = pointerInputScope;
                                                        this.$state = pagerState;
                                                    }

                                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                    public final Continuation create(Object obj, Continuation continuation) {
                                                        return new AnonymousClass1(this.$this_pointerInput, this.$state, continuation);
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
                                                            PointerInputScope pointerInputScope = this.$this_pointerInput;
                                                            C00131 c00131 = new C00131(this.$state, null);
                                                            this.label = 1;
                                                            if (ForEachGestureKt.awaitEachGesture(pointerInputScope, c00131, this) == coroutineSingletons) {
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

                                                @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                                public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                                    Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass1(pointerInputScope, pagerState2, null), continuation);
                                                    return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
                                                }
                                            })), nestedScrollConnection, null), pagerState2.prefetchState, function2, composerImpl, 0, 0);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            f2 = f4;
                                        }
                                        objRememberedValue8 = new LazyLayoutSemanticState() { // from class: androidx.compose.foundation.pager.LazyLayoutSemanticStateKt$LazyLayoutSemanticState$1
                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final CollectionInfo collectionInfo() {
                                                boolean z10 = z7;
                                                PagerState pagerState5 = pagerState3;
                                                return z10 ? new CollectionInfo(pagerState5.getPageCount(), 1) : new CollectionInfo(1, pagerState5.getPageCount());
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final int getContentPadding() {
                                                PagerState pagerState5 = pagerState3;
                                                return (-((PagerMeasureResult) pagerState5.getLayoutInfo()).viewportStartOffset) + ((PagerMeasureResult) pagerState5.getLayoutInfo()).afterContentPadding;
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final float getMaxScrollOffset() {
                                                PagerState pagerState5 = pagerState3;
                                                return PagerStateKt.calculateNewMaxScrollOffset(pagerState5.getLayoutInfo(), pagerState5.getPageCount());
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final float getScrollOffset() {
                                                return PagerScrollPositionKt.currentAbsoluteScrollOffset(pagerState3);
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final int getViewport() {
                                                PagerState pagerState5 = pagerState3;
                                                return (int) (((PagerMeasureResult) pagerState5.getLayoutInfo()).orientation == Orientation.Vertical ? ((PagerMeasureResult) pagerState5.getLayoutInfo()).m180getViewportSizeYbymL2g() & 4294967295L : ((PagerMeasureResult) pagerState5.getLayoutInfo()).m180getViewportSizeYbymL2g() >> 32);
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final Object scrollToItem(int i41, Continuation continuation) {
                                                Object objScrollToPage$default = PagerState.scrollToPage$default(pagerState3, i41, continuation);
                                                return objScrollToPage$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objScrollToPage$default : Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl.updateRememberedValue(objRememberedValue8);
                                        LazyLayoutSemanticState lazyLayoutSemanticState2 = (LazyLayoutSemanticState) objRememberedValue8;
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                        if (i29 != 32) {
                                        }
                                        z4 = ((i12 & 458752) != 131072) | (i29 != 32);
                                        objRememberedValue = composerImpl.rememberedValue();
                                        if (!z4) {
                                            objRememberedValue = new PagerWrapperFlingBehavior(targetedFlingBehavior, pagerState3);
                                            composerImpl.updateRememberedValue(objRememberedValue);
                                            PagerWrapperFlingBehavior pagerWrapperFlingBehavior2 = (PagerWrapperFlingBehavior) objRememberedValue;
                                            bringIntoViewSpec = (BringIntoViewSpec) composerImpl.consume(BringIntoViewSpec_androidKt.LocalBringIntoViewSpec);
                                            zChanged4 = (i29 != 32) | composerImpl.changed(bringIntoViewSpec);
                                            objRememberedValue2 = composerImpl.rememberedValue();
                                            if (!zChanged4) {
                                                objRememberedValue2 = new PagerBringIntoViewSpec(pagerState3, bringIntoViewSpec);
                                                composerImpl.updateRememberedValue(objRememberedValue2);
                                                PagerBringIntoViewSpec pagerBringIntoViewSpec2 = (PagerBringIntoViewSpec) objRememberedValue2;
                                                if (z2) {
                                                }
                                                Modifier modifierLazyLayoutSemantics2 = LazyLayoutSemanticsKt.lazyLayoutSemantics(modifier.then(pagerState3.remeasurementModifier).then(pagerState3.awaitLayoutModifier), kProperty02, lazyLayoutSemanticState2, orientation, z2, z);
                                                if (orientation != orientation2) {
                                                }
                                                if (!z2) {
                                                }
                                                PagerState pagerState42 = pagerState3;
                                                pagerState2 = pagerState42;
                                                LazyLayoutKt.LazyLayout(kProperty02, NestedScrollModifierKt.nestedScroll(ScrollingContainerKt.scrollingContainer(modifierThen.then(modifierLazyLayoutBeyondBoundsModifier), pagerState42, orientation, z2, z, pagerWrapperFlingBehavior2, pagerState42.internalInteractionSource, false, overscrollEffect, pagerBringIntoViewSpec2).then(SuspendingPointerInputFilterKt.pointerInput(Modifier.Companion, pagerState2, new PointerInputEventHandler() { // from class: androidx.compose.foundation.pager.LazyLayoutPagerKt$dragDirectionDetector$1

                                                    /* renamed from: androidx.compose.foundation.pager.LazyLayoutPagerKt$dragDirectionDetector$1$1, reason: invalid class name */
                                                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                                        final /* synthetic */ PagerState $state;
                                                        final /* synthetic */ PointerInputScope $this_pointerInput;
                                                        int label;

                                                        /* renamed from: androidx.compose.foundation.pager.LazyLayoutPagerKt$dragDirectionDetector$1$1$1, reason: invalid class name and collision with other inner class name */
                                                        final class C00131 extends RestrictedSuspendLambda implements Function2 {
                                                            final /* synthetic */ PagerState $state;
                                                            private /* synthetic */ Object L$0;
                                                            Object L$1;
                                                            Object L$2;
                                                            int label;

                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            public C00131(PagerState pagerState, Continuation continuation) {
                                                                super(2, continuation);
                                                                this.$state = pagerState;
                                                            }

                                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                            public final Continuation create(Object obj, Continuation continuation) {
                                                                C00131 c00131 = new C00131(this.$state, continuation);
                                                                c00131.L$0 = obj;
                                                                return c00131;
                                                            }

                                                            @Override // kotlin.jvm.functions.Function2
                                                            public final Object invoke(Object obj, Object obj2) {
                                                                return ((C00131) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                                            }

                                                            /* JADX WARN: Code restructure failed: missing block: B:11:0x003f, code lost:
                                                            
                                                                if (r11 == r0) goto L17;
                                                             */
                                                            /* JADX WARN: Code restructure failed: missing block: B:16:0x006c, code lost:
                                                            
                                                                if (r11 == r0) goto L17;
                                                             */
                                                            /* JADX WARN: Code restructure failed: missing block: B:17:0x006e, code lost:
                                                            
                                                                return r0;
                                                             */
                                                            /* JADX WARN: Removed duplicated region for block: B:15:0x005e  */
                                                            /* JADX WARN: Removed duplicated region for block: B:25:0x0097  */
                                                            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x006c -> B:18:0x006f). Please report as a decompilation issue!!! */
                                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                            /*
                                                                Code decompiled incorrectly, please refer to instructions dump.
                                                            */
                                                            public final Object invokeSuspend(Object obj) {
                                                                AwaitPointerEventScope awaitPointerEventScope;
                                                                AwaitPointerEventScope awaitPointerEventScope2;
                                                                PointerInputChange pointerInputChange;
                                                                PointerInputChange pointerInputChange2;
                                                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                                                int i = this.label;
                                                                if (i == 0) {
                                                                    ResultKt.throwOnFailure(obj);
                                                                    awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                                                                    PointerEventPass pointerEventPass = PointerEventPass.Initial;
                                                                    this.L$0 = awaitPointerEventScope;
                                                                    this.label = 1;
                                                                    obj = TapGestureDetectorKt.awaitFirstDown(awaitPointerEventScope, false, pointerEventPass, this);
                                                                } else if (i == 1) {
                                                                    awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                                                                    ResultKt.throwOnFailure(obj);
                                                                } else {
                                                                    if (i != 2) {
                                                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                                    }
                                                                    pointerInputChange = (PointerInputChange) this.L$2;
                                                                    pointerInputChange2 = (PointerInputChange) this.L$1;
                                                                    awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                                                                    ResultKt.throwOnFailure(obj);
                                                                    PointerEvent pointerEvent = (PointerEvent) obj;
                                                                    List list = pointerEvent.changes;
                                                                    int size = list.size();
                                                                    int i2 = 0;
                                                                    while (true) {
                                                                        if (i2 >= size) {
                                                                            pointerInputChange = (PointerInputChange) pointerEvent.changes.get(0);
                                                                            break;
                                                                        }
                                                                        if (!PointerEventKt.changedToUp((PointerInputChange) list.get(i2))) {
                                                                            break;
                                                                        }
                                                                        i2++;
                                                                    }
                                                                    if (pointerInputChange == null) {
                                                                        ((SnapshotMutableStateImpl) this.$state.upDownDifference$delegate).setValue(Offset.m395boximpl(Offset.m402minusMKHz9U(pointerInputChange.position, pointerInputChange2.position)));
                                                                        return Unit.INSTANCE;
                                                                    }
                                                                    PointerEventPass pointerEventPass2 = PointerEventPass.Initial;
                                                                    this.L$0 = awaitPointerEventScope2;
                                                                    this.L$1 = pointerInputChange2;
                                                                    this.L$2 = pointerInputChange;
                                                                    this.label = 2;
                                                                    obj = awaitPointerEventScope2.awaitPointerEvent(pointerEventPass2, this);
                                                                }
                                                                PagerState pagerState = this.$state;
                                                                Offset.Companion.getClass();
                                                                ((SnapshotMutableStateImpl) pagerState.upDownDifference$delegate).setValue(Offset.m395boximpl(0L));
                                                                awaitPointerEventScope2 = awaitPointerEventScope;
                                                                pointerInputChange = null;
                                                                pointerInputChange2 = (PointerInputChange) obj;
                                                                if (pointerInputChange == null) {
                                                                }
                                                            }
                                                        }

                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        public AnonymousClass1(PointerInputScope pointerInputScope, PagerState pagerState, Continuation continuation) {
                                                            super(2, continuation);
                                                            this.$this_pointerInput = pointerInputScope;
                                                            this.$state = pagerState;
                                                        }

                                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                        public final Continuation create(Object obj, Continuation continuation) {
                                                            return new AnonymousClass1(this.$this_pointerInput, this.$state, continuation);
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
                                                                PointerInputScope pointerInputScope = this.$this_pointerInput;
                                                                C00131 c00131 = new C00131(this.$state, null);
                                                                this.label = 1;
                                                                if (ForEachGestureKt.awaitEachGesture(pointerInputScope, c00131, this) == coroutineSingletons) {
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

                                                    @Override // androidx.compose.ui.input.pointer.PointerInputEventHandler
                                                    public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                                                        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass1(pointerInputScope, pagerState2, null), continuation);
                                                        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
                                                    }
                                                })), nestedScrollConnection, null), pagerState2.prefetchState, function2, composerImpl, 0, 0);
                                                if (ComposerKt.isTraceInProgress()) {
                                                }
                                                f2 = f4;
                                            }
                                        }
                                    }
                                } else {
                                    i19 = i18;
                                }
                                if ((i38 & 196608) != 131072) {
                                    z3 = false;
                                }
                                zChanged2 = zChanged5 | z3 | composerImpl2.changed(coroutineScope2);
                                Object objRememberedValue72 = composerImpl2.rememberedValue();
                                if (zChanged2) {
                                    i17 = i19;
                                    obj2 = obj4;
                                    final float f52 = f3;
                                    final Alignment.Vertical vertical32 = vertical2;
                                    i20 = 4;
                                    coroutineScope = coroutineScope2;
                                    composerImpl = composerImpl2;
                                    obj = new Function2() { // from class: androidx.compose.foundation.pager.PagerMeasurePolicyKt$rememberPagerMeasurePolicy$1$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        /* JADX WARN: Multi-variable type inference failed */
                                        /* JADX WARN: Type inference failed for: r2v84, types: [kotlin.ranges.IntProgression] */
                                        /* JADX WARN: Type inference failed for: r3v52, types: [java.util.ArrayList] */
                                        /* JADX WARN: Type inference failed for: r3v53 */
                                        /* JADX WARN: Type inference failed for: r3v55, types: [java.util.ArrayList] */
                                        /* JADX WARN: Type inference failed for: r3v56 */
                                        /* JADX WARN: Type inference failed for: r3v69, types: [kotlin.collections.EmptyList] */
                                        /* JADX WARN: Type inference failed for: r3v70, types: [kotlin.collections.EmptyList] */
                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj5, Object obj6) {
                                            int iMo52roundToPx0680j_4;
                                            int iMo52roundToPx0680j_42;
                                            LazyLayoutMeasureScope lazyLayoutMeasureScope;
                                            long j;
                                            int i402;
                                            int i41;
                                            int i42;
                                            int i43;
                                            long j2;
                                            final long j3;
                                            int i44;
                                            int i45;
                                            int i46;
                                            int i47;
                                            int i48;
                                            int i49;
                                            LazyLayoutMeasureScope lazyLayoutMeasureScope2;
                                            int i50;
                                            int i51;
                                            int i52;
                                            LazyLayoutMeasureScope lazyLayoutMeasureScope3;
                                            int i53;
                                            int i54;
                                            int i55;
                                            MeasuredPage measuredPage;
                                            long j4;
                                            ArrayList arrayList;
                                            int i56;
                                            int i57;
                                            SubcomposeMeasureScope subcomposeMeasureScope;
                                            final List<MeasuredPage> arrayList2;
                                            boolean z72;
                                            ArrayDeque arrayDeque;
                                            int i58;
                                            List list;
                                            ArrayList arrayList3;
                                            ?? arrayList4;
                                            ?? arrayList5;
                                            Orientation orientation22;
                                            int i59;
                                            int i60;
                                            int i61;
                                            SnapPosition snapPosition3;
                                            int i62;
                                            ArrayList arrayList6;
                                            int i63;
                                            boolean z82;
                                            Object obj7;
                                            PagerMeasureResult pagerMeasureResult;
                                            int i64;
                                            int i65;
                                            int i66;
                                            int i67;
                                            int i68;
                                            int i69;
                                            LazyLayoutMeasureScope lazyLayoutMeasureScope4 = (LazyLayoutMeasureScope) obj5;
                                            final long j5 = ((Constraints) obj6).value;
                                            pagerState.measurementScopeInvalidator.getValue();
                                            Orientation orientation3 = orientation;
                                            Orientation orientation4 = Orientation.Vertical;
                                            boolean z92 = orientation3 == orientation4;
                                            CheckScrollableContainerConstraintsKt.m32checkScrollableContainerConstraintsK40F9xA(z92 ? orientation4 : Orientation.Horizontal, j5);
                                            if (z92) {
                                                LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope4;
                                                iMo52roundToPx0680j_4 = lazyLayoutMeasureScopeImpl.subcomposeMeasureScope.mo52roundToPx0680j_4(paddingValues.mo111calculateLeftPaddingu2uoSUM(lazyLayoutMeasureScopeImpl.subcomposeMeasureScope.getLayoutDirection()));
                                            } else {
                                                LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl2 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope4;
                                                iMo52roundToPx0680j_4 = lazyLayoutMeasureScopeImpl2.subcomposeMeasureScope.mo52roundToPx0680j_4(PaddingKt.calculateStartPadding(paddingValues, lazyLayoutMeasureScopeImpl2.subcomposeMeasureScope.getLayoutDirection()));
                                            }
                                            if (z92) {
                                                LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl3 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope4;
                                                iMo52roundToPx0680j_42 = lazyLayoutMeasureScopeImpl3.subcomposeMeasureScope.mo52roundToPx0680j_4(paddingValues.mo112calculateRightPaddingu2uoSUM(lazyLayoutMeasureScopeImpl3.subcomposeMeasureScope.getLayoutDirection()));
                                            } else {
                                                LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl4 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope4;
                                                iMo52roundToPx0680j_42 = lazyLayoutMeasureScopeImpl4.subcomposeMeasureScope.mo52roundToPx0680j_4(PaddingKt.calculateEndPadding(paddingValues, lazyLayoutMeasureScopeImpl4.subcomposeMeasureScope.getLayoutDirection()));
                                            }
                                            LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl5 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope4;
                                            int iMo52roundToPx0680j_43 = lazyLayoutMeasureScopeImpl5.subcomposeMeasureScope.mo52roundToPx0680j_4(paddingValues.mo113calculateTopPaddingD9Ej5fM());
                                            float fMo110calculateBottomPaddingD9Ej5fM = paddingValues.mo110calculateBottomPaddingD9Ej5fM();
                                            SubcomposeMeasureScope subcomposeMeasureScope2 = lazyLayoutMeasureScopeImpl5.subcomposeMeasureScope;
                                            int iMo52roundToPx0680j_44 = subcomposeMeasureScope2.mo52roundToPx0680j_4(fMo110calculateBottomPaddingD9Ej5fM);
                                            int i70 = iMo52roundToPx0680j_42;
                                            final int i71 = iMo52roundToPx0680j_43 + iMo52roundToPx0680j_44;
                                            int i72 = iMo52roundToPx0680j_4 + i70;
                                            int i73 = z92 ? i71 : i72;
                                            boolean z10 = true;
                                            int i74 = (!z92 || z) ? (z92 && z) ? iMo52roundToPx0680j_44 : (z92 || z) ? i70 : iMo52roundToPx0680j_4 : iMo52roundToPx0680j_43;
                                            int i75 = i73 - i74;
                                            long jM835offsetNN6EwU = ConstraintsKt.m835offsetNN6EwU(-i72, -i71, j5);
                                            pagerState.density = lazyLayoutMeasureScope4;
                                            int iMo52roundToPx0680j_45 = subcomposeMeasureScope2.mo52roundToPx0680j_4(f52);
                                            int iM822getMaxHeightimpl = z92 ? Constraints.m822getMaxHeightimpl(j5) - i71 : Constraints.m823getMaxWidthimpl(j5) - i72;
                                            boolean z11 = z92;
                                            if (!z || iM822getMaxHeightimpl > 0) {
                                                lazyLayoutMeasureScope = lazyLayoutMeasureScope4;
                                                j = (iMo52roundToPx0680j_4 << 32) | (iMo52roundToPx0680j_43 & 4294967295L);
                                                IntOffset.Companion companion32 = IntOffset.Companion;
                                            } else {
                                                if (!z11) {
                                                    iMo52roundToPx0680j_4 += iM822getMaxHeightimpl;
                                                }
                                                if (z11) {
                                                    iMo52roundToPx0680j_43 += iM822getMaxHeightimpl;
                                                }
                                                lazyLayoutMeasureScope = lazyLayoutMeasureScope4;
                                                j = (iMo52roundToPx0680j_4 << 32) | (iMo52roundToPx0680j_43 & 4294967295L);
                                                IntOffset.Companion companion4 = IntOffset.Companion;
                                            }
                                            long j6 = j;
                                            ((PageSize.Fill) pageSize).getClass();
                                            int i76 = iM822getMaxHeightimpl < 0 ? 0 : iM822getMaxHeightimpl;
                                            pagerState.premeasureConstraints = ConstraintsKt.Constraints$default(0, orientation == orientation4 ? Constraints.m823getMaxWidthimpl(jM835offsetNN6EwU) : i76, 0, orientation != orientation4 ? Constraints.m822getMaxHeightimpl(jM835offsetNN6EwU) : i76, 5);
                                            PagerLazyLayoutItemProvider pagerLazyLayoutItemProvider = (PagerLazyLayoutItemProvider) kProperty03.invoke();
                                            int i77 = iM822getMaxHeightimpl + i74 + i75;
                                            Snapshot.Companion companion5 = Snapshot.Companion;
                                            PagerState pagerState43 = pagerState;
                                            SnapPosition snapPosition4 = snapPosition;
                                            companion5.getClass();
                                            Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                                            Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
                                            Snapshot snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                                            long j7 = jM835offsetNN6EwU;
                                            try {
                                                int currentPage = pagerState43.getCurrentPage();
                                                PagerScrollPosition pagerScrollPosition = pagerState43.scrollPosition;
                                                int iFindIndexByKey = LazyLayoutItemProviderKt.findIndexByKey(pagerLazyLayoutItemProvider, currentPage, pagerScrollPosition.lastKnownCurrentPageKey);
                                                if (currentPage != iFindIndexByKey) {
                                                    i402 = i72;
                                                    ((SnapshotMutableIntStateImpl) pagerScrollPosition.currentPage$delegate).setIntValue(iFindIndexByKey);
                                                    pagerScrollPosition.nearestRangeState.update(currentPage);
                                                } else {
                                                    i402 = i72;
                                                }
                                                pagerState43.getCurrentPage();
                                                float currentPageOffsetFraction = pagerState43.getCurrentPageOffsetFraction();
                                                pagerState43.getPageCount();
                                                int i78 = i76 + iMo52roundToPx0680j_45;
                                                int iRoundToInt = MathKt__MathJVMKt.roundToInt(snapPosition4.position(i77, i76, i74, i75) - (currentPageOffsetFraction * i78));
                                                Unit unit = Unit.INSTANCE;
                                                Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                                                PagerState pagerState5 = pagerState;
                                                List listCalculateLazyLayoutPinnedIndices = LazyLayoutBeyondBoundsStateKt.calculateLazyLayoutPinnedIndices(pagerLazyLayoutItemProvider, pagerState5.pinnedPages, pagerState5.beyondBoundsInfo);
                                                int iIntValue = ((Number) function02.invoke()).intValue();
                                                final MutableState<Unit> mutableState = pagerState.placementScopeInvalidator;
                                                final Orientation orientation5 = orientation;
                                                int i79 = iRoundToInt;
                                                final Alignment.Vertical vertical4 = vertical32;
                                                List list2 = listCalculateLazyLayoutPinnedIndices;
                                                final Alignment.Horizontal horizontal2 = horizontal;
                                                boolean z12 = z;
                                                int i80 = i76;
                                                int i81 = i17;
                                                SnapPosition snapPosition5 = snapPosition;
                                                CoroutineScope coroutineScope3 = coroutineScope;
                                                PagerLazyLayoutItemProvider pagerLazyLayoutItemProvider2 = pagerLazyLayoutItemProvider;
                                                boolean z13 = z12;
                                                final LazyLayoutMeasureScope lazyLayoutMeasureScope5 = lazyLayoutMeasureScope;
                                                final int i82 = i402;
                                                Function3 function3 = new Function3() { // from class: androidx.compose.foundation.pager.PagerMeasurePolicyKt$rememberPagerMeasurePolicy$1$1$measureResult$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(3);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function3
                                                    public final Object invoke(Object obj8, Object obj9, Object obj10) {
                                                        int iIntValue2 = ((Number) obj8).intValue();
                                                        int iIntValue3 = ((Number) obj9).intValue();
                                                        LazyLayoutMeasureScope lazyLayoutMeasureScope6 = lazyLayoutMeasureScope5;
                                                        int iM834constrainWidthK40F9xA = ConstraintsKt.m834constrainWidthK40F9xA(iIntValue2 + i82, j5);
                                                        int iM833constrainHeightK40F9xA = ConstraintsKt.m833constrainHeightK40F9xA(iIntValue3 + i71, j5);
                                                        Map mapEmptyMap = MapsKt__MapsKt.emptyMap();
                                                        return ((LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope6).subcomposeMeasureScope.layout$1(iM834constrainWidthK40F9xA, iM833constrainHeightK40F9xA, mapEmptyMap, (Function1) obj10);
                                                    }
                                                };
                                                if (i74 < 0) {
                                                    InlineClassHelperKt.throwIllegalArgumentException("negative beforeContentPadding");
                                                }
                                                if (i75 < 0) {
                                                    InlineClassHelperKt.throwIllegalArgumentException("negative afterContentPadding");
                                                }
                                                int i83 = i78 < 0 ? 0 : i78;
                                                if (iIntValue <= 0) {
                                                    pagerMeasureResult = new PagerMeasureResult(EmptyList.INSTANCE, i80, iMo52roundToPx0680j_45, i75, orientation5, -i74, iM822getMaxHeightimpl + i75, false, i81, null, null, 0.0f, 0, false, snapPosition5, (MeasureResult) function3.invoke(Integer.valueOf(Constraints.m825getMinWidthimpl(j7)), Integer.valueOf(Constraints.m824getMinHeightimpl(j7)), new Function1() { // from class: androidx.compose.foundation.pager.PagerMeasureKt$measurePager$4
                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj8) {
                                                            return Unit.INSTANCE;
                                                        }
                                                    }), false, null, null, coroutineScope3, 393216, null);
                                                    subcomposeMeasureScope = subcomposeMeasureScope2;
                                                } else {
                                                    int i84 = i75;
                                                    int i85 = i80;
                                                    Function3 function32 = function3;
                                                    long jConstraints$default = ConstraintsKt.Constraints$default(0, orientation5 == orientation4 ? Constraints.m823getMaxWidthimpl(j7) : i85, 0, orientation5 != orientation4 ? Constraints.m822getMaxHeightimpl(j7) : i85, 5);
                                                    int i86 = iFindIndexByKey;
                                                    while (i86 > 0 && i79 > 0) {
                                                        i86--;
                                                        i79 -= i83;
                                                    }
                                                    int i87 = i79 * (-1);
                                                    if (i86 >= iIntValue) {
                                                        i86 = iIntValue - 1;
                                                        i87 = 0;
                                                    }
                                                    ArrayDeque arrayDeque2 = new ArrayDeque();
                                                    int i88 = iM822getMaxHeightimpl;
                                                    int i89 = -i74;
                                                    int i90 = i89 + (iMo52roundToPx0680j_45 < 0 ? iMo52roundToPx0680j_45 : 0);
                                                    int i91 = i74;
                                                    int i92 = i87 + i90;
                                                    int i93 = i86;
                                                    int i94 = 0;
                                                    while (i92 < 0 && i93 > 0) {
                                                        int i95 = i93 - 1;
                                                        int i96 = i78;
                                                        int i97 = i92;
                                                        int i98 = i94;
                                                        LazyLayoutMeasureScope lazyLayoutMeasureScope6 = lazyLayoutMeasureScope5;
                                                        int i99 = iIntValue;
                                                        boolean z14 = z13;
                                                        int i100 = i90;
                                                        int i101 = i83;
                                                        long j8 = jConstraints$default;
                                                        long j9 = j6;
                                                        PagerLazyLayoutItemProvider pagerLazyLayoutItemProvider3 = pagerLazyLayoutItemProvider2;
                                                        MeasuredPage measuredPageM179getAndMeasureSGf7dI0 = PagerMeasureKt.m179getAndMeasureSGf7dI0(lazyLayoutMeasureScope6, i95, j8, pagerLazyLayoutItemProvider3, j9, orientation5, horizontal2, vertical4, subcomposeMeasureScope2.getLayoutDirection(), z14, i85);
                                                        pagerLazyLayoutItemProvider2 = pagerLazyLayoutItemProvider3;
                                                        arrayDeque2.add(0, measuredPageM179getAndMeasureSGf7dI0);
                                                        int iMax = Math.max(i98, measuredPageM179getAndMeasureSGf7dI0.crossAxisSize);
                                                        i93 = i95;
                                                        z13 = z14;
                                                        i78 = i96;
                                                        j7 = j7;
                                                        iMo52roundToPx0680j_45 = iMo52roundToPx0680j_45;
                                                        list2 = list2;
                                                        iIntValue = i99;
                                                        snapPosition5 = snapPosition5;
                                                        lazyLayoutMeasureScope5 = lazyLayoutMeasureScope6;
                                                        i94 = iMax;
                                                        i92 = i97 + i101;
                                                        i83 = i101;
                                                        i90 = i100;
                                                        i84 = i84;
                                                        jConstraints$default = j8;
                                                        function32 = function32;
                                                        j6 = j9;
                                                    }
                                                    int i102 = i92;
                                                    int i103 = i94;
                                                    LazyLayoutMeasureScope lazyLayoutMeasureScope7 = lazyLayoutMeasureScope5;
                                                    int i104 = i84;
                                                    int i105 = i78;
                                                    int i106 = iIntValue;
                                                    Function3 function33 = function32;
                                                    int i107 = iMo52roundToPx0680j_45;
                                                    final boolean z15 = z13;
                                                    long j10 = j7;
                                                    List list3 = list2;
                                                    SnapPosition snapPosition6 = snapPosition5;
                                                    int i108 = i90;
                                                    int i109 = i83;
                                                    long j11 = j6;
                                                    long j12 = jConstraints$default;
                                                    int i110 = i102;
                                                    if (i110 < i108) {
                                                        i110 = i108;
                                                    }
                                                    int i111 = i110 - i108;
                                                    int i112 = i88 + i104;
                                                    int i113 = i112 < 0 ? 0 : i112;
                                                    int i114 = i103;
                                                    LazyLayoutMeasureScope lazyLayoutMeasureScope8 = lazyLayoutMeasureScope7;
                                                    int i115 = i93;
                                                    int i116 = -i111;
                                                    int i117 = 0;
                                                    boolean z16 = false;
                                                    while (i117 < arrayDeque2.size) {
                                                        if (i116 >= i113) {
                                                            arrayDeque2.removeAt(i117);
                                                            z16 = true;
                                                        } else {
                                                            i115++;
                                                            i116 += i109;
                                                            i117++;
                                                        }
                                                    }
                                                    int i118 = i116;
                                                    int i119 = i108;
                                                    int i120 = i111;
                                                    long j13 = j11;
                                                    int i121 = i115;
                                                    final PagerLazyLayoutItemProvider pagerLazyLayoutItemProvider4 = pagerLazyLayoutItemProvider2;
                                                    boolean z17 = z16;
                                                    int i122 = i93;
                                                    while (true) {
                                                        int i123 = i106;
                                                        if (i121 >= i123) {
                                                            int i124 = i114;
                                                            i41 = i109;
                                                            i42 = i124;
                                                            i43 = i123;
                                                            j2 = j12;
                                                            j3 = j13;
                                                            i44 = i118;
                                                            i45 = i120;
                                                            i46 = i104;
                                                            i47 = i121;
                                                            i48 = i88;
                                                            break;
                                                        }
                                                        if (i118 >= i113 && i118 > 0 && !arrayDeque2.isEmpty()) {
                                                            int i125 = i114;
                                                            i41 = i109;
                                                            i42 = i125;
                                                            i43 = i123;
                                                            j2 = j12;
                                                            j3 = j13;
                                                            i44 = i118;
                                                            i45 = i120;
                                                            i48 = i88;
                                                            i46 = i104;
                                                            i47 = i121;
                                                            break;
                                                        }
                                                        int i126 = i119;
                                                        int i127 = i114;
                                                        int i128 = i109;
                                                        int i129 = i127;
                                                        int i130 = i120;
                                                        i106 = i123;
                                                        int i131 = i104;
                                                        long j14 = j12;
                                                        int i132 = i118;
                                                        int i133 = i113;
                                                        LazyLayoutMeasureScope lazyLayoutMeasureScope9 = lazyLayoutMeasureScope8;
                                                        long j15 = j13;
                                                        MeasuredPage measuredPageM179getAndMeasureSGf7dI02 = PagerMeasureKt.m179getAndMeasureSGf7dI0(lazyLayoutMeasureScope9, i121, j14, pagerLazyLayoutItemProvider4, j15, orientation5, horizontal2, vertical4, subcomposeMeasureScope2.getLayoutDirection(), z15, i85);
                                                        lazyLayoutMeasureScope8 = lazyLayoutMeasureScope9;
                                                        int i134 = i121;
                                                        int i135 = i106 - 1;
                                                        int i136 = i132 + (i134 == i135 ? i85 : i128);
                                                        if (i136 > i126 || i134 == i135) {
                                                            int iMax2 = Math.max(i129, measuredPageM179getAndMeasureSGf7dI02.crossAxisSize);
                                                            arrayDeque2.addLast(measuredPageM179getAndMeasureSGf7dI02);
                                                            i129 = iMax2;
                                                            i69 = i130;
                                                        } else {
                                                            i69 = i130 - i128;
                                                            i122 = i134 + 1;
                                                            z17 = true;
                                                        }
                                                        i121 = i134 + 1;
                                                        i114 = i129;
                                                        i109 = i128;
                                                        i119 = i126;
                                                        i118 = i136;
                                                        i120 = i69;
                                                        j13 = j15;
                                                        i113 = i133;
                                                        j12 = j14;
                                                        i104 = i131;
                                                    }
                                                    if (i44 < i48) {
                                                        int i137 = i48 - i44;
                                                        int i138 = i45 - i137;
                                                        int i139 = i44 + i137;
                                                        int iMax3 = i42;
                                                        int i140 = i138;
                                                        while (true) {
                                                            i68 = i91;
                                                            if (i140 >= i68 || i122 <= 0) {
                                                                break;
                                                            }
                                                            int i141 = i122 - 1;
                                                            int i142 = i47;
                                                            i91 = i68;
                                                            LazyLayoutMeasureScope lazyLayoutMeasureScope10 = lazyLayoutMeasureScope8;
                                                            long j16 = j2;
                                                            MeasuredPage measuredPageM179getAndMeasureSGf7dI03 = PagerMeasureKt.m179getAndMeasureSGf7dI0(lazyLayoutMeasureScope10, i141, j16, pagerLazyLayoutItemProvider4, j3, orientation5, horizontal2, vertical4, subcomposeMeasureScope2.getLayoutDirection(), z15, i85);
                                                            j2 = j16;
                                                            arrayDeque2.add(0, measuredPageM179getAndMeasureSGf7dI03);
                                                            i140 += i41;
                                                            lazyLayoutMeasureScope8 = lazyLayoutMeasureScope10;
                                                            i122 = i141;
                                                            iMax3 = Math.max(iMax3, measuredPageM179getAndMeasureSGf7dI03.crossAxisSize);
                                                            i47 = i142;
                                                        }
                                                        i49 = i47;
                                                        i91 = i68;
                                                        int i143 = i140;
                                                        lazyLayoutMeasureScope2 = lazyLayoutMeasureScope8;
                                                        int i144 = iMax3;
                                                        if (i143 < 0) {
                                                            int i145 = i139 + i143;
                                                            i50 = i144;
                                                            i52 = i145;
                                                            i51 = 0;
                                                        } else {
                                                            i50 = i144;
                                                            i52 = i139;
                                                            i51 = i143;
                                                        }
                                                    } else {
                                                        i49 = i47;
                                                        lazyLayoutMeasureScope2 = lazyLayoutMeasureScope8;
                                                        i50 = i42;
                                                        i51 = i45;
                                                        i52 = i44;
                                                    }
                                                    if (i51 < 0) {
                                                        InlineClassHelperKt.throwIllegalArgumentException("invalid currentFirstPageScrollOffset");
                                                    }
                                                    int i146 = -i51;
                                                    MeasuredPage measuredPage2 = (MeasuredPage) arrayDeque2.first();
                                                    if (i91 > 0 || i107 < 0) {
                                                        int size = arrayDeque2.getSize();
                                                        MeasuredPage measuredPage3 = measuredPage2;
                                                        int i147 = i51;
                                                        int i148 = 0;
                                                        while (i148 < size && i147 != 0) {
                                                            i53 = i85;
                                                            i54 = i41;
                                                            if (i54 > i147) {
                                                                lazyLayoutMeasureScope3 = lazyLayoutMeasureScope2;
                                                                break;
                                                            }
                                                            lazyLayoutMeasureScope3 = lazyLayoutMeasureScope2;
                                                            if (i148 == arrayDeque2.getSize() - 1) {
                                                                break;
                                                            }
                                                            i147 -= i54;
                                                            i148++;
                                                            measuredPage3 = (MeasuredPage) arrayDeque2.get(i148);
                                                            i41 = i54;
                                                            i85 = i53;
                                                            lazyLayoutMeasureScope2 = lazyLayoutMeasureScope3;
                                                        }
                                                        lazyLayoutMeasureScope3 = lazyLayoutMeasureScope2;
                                                        i53 = i85;
                                                        i54 = i41;
                                                        i55 = i147;
                                                        measuredPage = measuredPage3;
                                                    } else {
                                                        lazyLayoutMeasureScope3 = lazyLayoutMeasureScope2;
                                                        measuredPage = measuredPage2;
                                                        i53 = i85;
                                                        i54 = i41;
                                                        i55 = i51;
                                                    }
                                                    int i149 = i54;
                                                    final int i150 = i53;
                                                    final LazyLayoutMeasureScope lazyLayoutMeasureScope11 = lazyLayoutMeasureScope3;
                                                    ArrayDeque arrayDeque3 = arrayDeque2;
                                                    MeasuredPage measuredPage4 = measuredPage;
                                                    final long j17 = j2;
                                                    Function1 function12 = new Function1() { // from class: androidx.compose.foundation.pager.PagerMeasureKt$measurePager$extraPagesBefore$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(1);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj8) {
                                                            int iIntValue2 = ((Number) obj8).intValue();
                                                            LazyLayoutMeasureScope lazyLayoutMeasureScope12 = lazyLayoutMeasureScope11;
                                                            return PagerMeasureKt.m179getAndMeasureSGf7dI0(lazyLayoutMeasureScope12, iIntValue2, j17, pagerLazyLayoutItemProvider4, j3, orientation5, horizontal2, vertical4, ((LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope12).subcomposeMeasureScope.getLayoutDirection(), z15, i150);
                                                        }
                                                    };
                                                    int iMax4 = Math.max(0, i122 - i81);
                                                    int i151 = i122 - 1;
                                                    if (iMax4 <= i151) {
                                                        ArrayList arrayList7 = null;
                                                        while (true) {
                                                            if (arrayList7 == null) {
                                                                arrayList7 = new ArrayList();
                                                            }
                                                            j4 = j17;
                                                            arrayList = arrayList7;
                                                            arrayList.add(function12.mo781invoke(Integer.valueOf(i151)));
                                                            if (i151 == iMax4) {
                                                                break;
                                                            }
                                                            i151--;
                                                            arrayList7 = arrayList;
                                                            j17 = j4;
                                                        }
                                                    } else {
                                                        j4 = j17;
                                                        arrayList = null;
                                                    }
                                                    List list4 = list3;
                                                    List list5 = list4;
                                                    int size2 = list5.size();
                                                    List arrayList8 = arrayList;
                                                    int i152 = 0;
                                                    while (i152 < size2) {
                                                        List list6 = list4;
                                                        int iIntValue2 = ((Number) list4.get(i152)).intValue();
                                                        if (iIntValue2 < iMax4) {
                                                            if (arrayList8 == null) {
                                                                arrayList8 = new ArrayList();
                                                            }
                                                            i67 = i152;
                                                            List list7 = arrayList8;
                                                            list7.add(function12.mo781invoke(Integer.valueOf(iIntValue2)));
                                                            arrayList8 = list7;
                                                        } else {
                                                            i67 = i152;
                                                        }
                                                        i152 = i67 + 1;
                                                        list4 = list6;
                                                    }
                                                    List list8 = list4;
                                                    if (arrayList8 == null) {
                                                        arrayList8 = EmptyList.INSTANCE;
                                                    }
                                                    List list9 = arrayList8;
                                                    int size3 = list9.size();
                                                    int iMax5 = i50;
                                                    for (int i153 = 0; i153 < size3; i153++) {
                                                        iMax5 = Math.max(iMax5, ((MeasuredPage) list9.get(i153)).crossAxisSize);
                                                    }
                                                    int i154 = ((MeasuredPage) arrayDeque3.last()).index;
                                                    int i155 = iMax5;
                                                    final boolean z18 = z15;
                                                    final long j18 = j4;
                                                    Function1 function13 = new Function1() { // from class: androidx.compose.foundation.pager.PagerMeasureKt$measurePager$extraPagesAfter$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(1);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj8) {
                                                            int iIntValue3 = ((Number) obj8).intValue();
                                                            LazyLayoutMeasureScope lazyLayoutMeasureScope12 = lazyLayoutMeasureScope11;
                                                            return PagerMeasureKt.m179getAndMeasureSGf7dI0(lazyLayoutMeasureScope12, iIntValue3, j18, pagerLazyLayoutItemProvider4, j3, orientation5, horizontal2, vertical4, ((LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope12).subcomposeMeasureScope.getLayoutDirection(), z18, i150);
                                                        }
                                                    };
                                                    int iMin = Math.min(i154 + i81, i43 - 1);
                                                    int i156 = i154 + 1;
                                                    List arrayList9 = null;
                                                    if (i156 <= iMin) {
                                                        while (true) {
                                                            if (arrayList9 == null) {
                                                                arrayList9 = new ArrayList();
                                                            }
                                                            arrayList9.add(function13.mo781invoke(Integer.valueOf(i156)));
                                                            if (i156 == iMin) {
                                                                break;
                                                            }
                                                            i156++;
                                                        }
                                                    }
                                                    int size4 = list5.size();
                                                    int i157 = 0;
                                                    while (i157 < size4) {
                                                        int iIntValue3 = ((Number) list8.get(i157)).intValue();
                                                        if (iMin + 1 <= iIntValue3) {
                                                            i66 = i43;
                                                            if (iIntValue3 < i66) {
                                                                if (arrayList9 == null) {
                                                                    arrayList9 = new ArrayList();
                                                                }
                                                                arrayList9.add(function13.mo781invoke(Integer.valueOf(iIntValue3)));
                                                            }
                                                        } else {
                                                            i66 = i43;
                                                        }
                                                        i157++;
                                                        i43 = i66;
                                                    }
                                                    int i158 = i43;
                                                    if (arrayList9 == null) {
                                                        arrayList9 = EmptyList.INSTANCE;
                                                    }
                                                    int size5 = arrayList9.size();
                                                    int iMax6 = i155;
                                                    for (int i159 = 0; i159 < size5; i159++) {
                                                        iMax6 = Math.max(iMax6, ((MeasuredPage) arrayList9.get(i159)).crossAxisSize);
                                                    }
                                                    boolean z19 = Intrinsics.areEqual(measuredPage4, arrayDeque3.first()) && list9.isEmpty() && arrayList9.isEmpty();
                                                    Orientation orientation6 = Orientation.Vertical;
                                                    int iM834constrainWidthK40F9xA = ConstraintsKt.m834constrainWidthK40F9xA(orientation5 == orientation6 ? iMax6 : i52, j10);
                                                    if (orientation5 == orientation6) {
                                                        iMax6 = i52;
                                                    }
                                                    int iM833constrainHeightK40F9xA = ConstraintsKt.m833constrainHeightK40F9xA(iMax6, j10);
                                                    if (orientation5 == orientation6) {
                                                        i56 = iM833constrainHeightK40F9xA;
                                                    } else {
                                                        i56 = iM833constrainHeightK40F9xA;
                                                        iM833constrainHeightK40F9xA = iM834constrainWidthK40F9xA;
                                                    }
                                                    boolean z20 = i52 < Math.min(iM833constrainHeightK40F9xA, i48);
                                                    if (!z20 || i146 == 0) {
                                                        i57 = i146;
                                                    } else {
                                                        StringBuilder sb = new StringBuilder("non-zero pagesScrollOffset=");
                                                        i57 = i146;
                                                        sb.append(i57);
                                                        InlineClassHelperKt.throwIllegalStateException(sb.toString());
                                                    }
                                                    ArrayList arrayList10 = new ArrayList(arrayList9.size() + list9.size() + arrayDeque3.getSize());
                                                    if (z20) {
                                                        if (!list9.isEmpty() || !arrayList9.isEmpty()) {
                                                            InlineClassHelperKt.throwIllegalArgumentException("No extra pages");
                                                        }
                                                        int size6 = arrayDeque3.getSize();
                                                        int[] iArr = new int[size6];
                                                        for (int i160 = 0; i160 < size6; i160++) {
                                                            iArr[i160] = i150;
                                                        }
                                                        int[] iArr2 = new int[size6];
                                                        Arrangement.Absolute absolute = Arrangement.Absolute.INSTANCE;
                                                        int i161 = i56;
                                                        z72 = z19;
                                                        float fMo55toDpu2uoSUM = subcomposeMeasureScope2.mo55toDpu2uoSUM(i107);
                                                        absolute.getClass();
                                                        subcomposeMeasureScope = subcomposeMeasureScope2;
                                                        i107 = i107;
                                                        Arrangement.SpacedAligned spacedAligned = new Arrangement.SpacedAligned(fMo55toDpu2uoSUM, false, null, null);
                                                        if (orientation5 == Orientation.Vertical) {
                                                            spacedAligned.arrange(lazyLayoutMeasureScope11, iM833constrainHeightK40F9xA, iArr, iArr2);
                                                            arrayList2 = arrayList10;
                                                            i58 = i161;
                                                        } else {
                                                            i58 = i161;
                                                            arrayList2 = arrayList10;
                                                            spacedAligned.arrange(lazyLayoutMeasureScope11, iM833constrainHeightK40F9xA, iArr, LayoutDirection.Ltr, iArr2);
                                                        }
                                                        IntRange indices = ArraysKt___ArraysKt.getIndices(iArr2);
                                                        IntRange intRangeReversed = indices;
                                                        if (z18) {
                                                            intRangeReversed = RangesKt___RangesKt.reversed(indices);
                                                        }
                                                        int i162 = intRangeReversed.first;
                                                        int i163 = intRangeReversed.last;
                                                        int i164 = intRangeReversed.step;
                                                        if ((i164 > 0 && i162 <= i163) || (i164 < 0 && i163 <= i162)) {
                                                            while (true) {
                                                                int i165 = iArr2[i162];
                                                                if (z18) {
                                                                    i64 = i164;
                                                                    i65 = (size6 - i162) - 1;
                                                                } else {
                                                                    i64 = i164;
                                                                    i65 = i162;
                                                                }
                                                                int i166 = iM833constrainHeightK40F9xA;
                                                                arrayDeque = arrayDeque3;
                                                                MeasuredPage measuredPage5 = (MeasuredPage) arrayDeque.get(i65);
                                                                if (z18) {
                                                                    i165 = (i166 - i165) - measuredPage5.size;
                                                                }
                                                                measuredPage5.position(i165, iM834constrainWidthK40F9xA, i58);
                                                                arrayList2.add(measuredPage5);
                                                                if (i162 == i163) {
                                                                    break;
                                                                }
                                                                i162 += i64;
                                                                arrayDeque3 = arrayDeque;
                                                                iM833constrainHeightK40F9xA = i166;
                                                                i164 = i64;
                                                            }
                                                        } else {
                                                            arrayDeque = arrayDeque3;
                                                        }
                                                        list = list9;
                                                    } else {
                                                        subcomposeMeasureScope = subcomposeMeasureScope2;
                                                        arrayList2 = arrayList10;
                                                        z72 = z19;
                                                        arrayDeque = arrayDeque3;
                                                        i58 = i56;
                                                        int size7 = list9.size();
                                                        int i167 = i57;
                                                        int i168 = 0;
                                                        while (i168 < size7) {
                                                            int i169 = size7;
                                                            MeasuredPage measuredPage6 = (MeasuredPage) list9.get(i168);
                                                            i167 -= i105;
                                                            measuredPage6.position(i167, iM834constrainWidthK40F9xA, i58);
                                                            arrayList2.add(measuredPage6);
                                                            i168++;
                                                            size7 = i169;
                                                        }
                                                        list = list9;
                                                        int size8 = arrayDeque.getSize();
                                                        for (int i170 = 0; i170 < size8; i170++) {
                                                            MeasuredPage measuredPage7 = (MeasuredPage) arrayDeque.get(i170);
                                                            measuredPage7.position(i57, iM834constrainWidthK40F9xA, i58);
                                                            arrayList2.add(measuredPage7);
                                                            i57 += i105;
                                                        }
                                                        int size9 = arrayList9.size();
                                                        for (int i171 = 0; i171 < size9; i171++) {
                                                            MeasuredPage measuredPage8 = (MeasuredPage) arrayList9.get(i171);
                                                            measuredPage8.position(i57, iM834constrainWidthK40F9xA, i58);
                                                            arrayList2.add(measuredPage8);
                                                            i57 += i105;
                                                        }
                                                    }
                                                    if (z72) {
                                                        arrayList3 = arrayList2;
                                                    } else {
                                                        arrayList3 = new ArrayList(arrayList2.size());
                                                        int size10 = arrayList2.size();
                                                        int i172 = 0;
                                                        while (i172 < size10) {
                                                            Object obj8 = arrayList2.get(i172);
                                                            int i173 = size10;
                                                            MeasuredPage measuredPage9 = (MeasuredPage) obj8;
                                                            ArrayDeque arrayDeque4 = arrayDeque;
                                                            int i174 = i172;
                                                            if (measuredPage9.index >= ((MeasuredPage) arrayDeque4.first()).index) {
                                                                if (measuredPage9.index <= ((MeasuredPage) arrayDeque4.last()).index) {
                                                                    arrayList3.add(obj8);
                                                                }
                                                            }
                                                            i172 = i174 + 1;
                                                            size10 = i173;
                                                            arrayDeque = arrayDeque4;
                                                        }
                                                    }
                                                    ArrayDeque arrayDeque5 = arrayDeque;
                                                    if (list.isEmpty()) {
                                                        arrayList4 = EmptyList.INSTANCE;
                                                    } else {
                                                        arrayList4 = new ArrayList(arrayList2.size());
                                                        int size11 = arrayList2.size();
                                                        int i175 = 0;
                                                        while (i175 < size11) {
                                                            Object obj9 = arrayList2.get(i175);
                                                            int i176 = size11;
                                                            if (((MeasuredPage) obj9).index < ((MeasuredPage) arrayDeque5.first()).index) {
                                                                arrayList4.add(obj9);
                                                            }
                                                            i175++;
                                                            size11 = i176;
                                                        }
                                                    }
                                                    List list10 = arrayList4;
                                                    if (arrayList9.isEmpty()) {
                                                        arrayList5 = EmptyList.INSTANCE;
                                                    } else {
                                                        arrayList5 = new ArrayList(arrayList2.size());
                                                        int size12 = arrayList2.size();
                                                        for (int i177 = 0; i177 < size12; i177++) {
                                                            Object obj10 = arrayList2.get(i177);
                                                            if (((MeasuredPage) obj10).index > ((MeasuredPage) arrayDeque5.last()).index) {
                                                                arrayList5.add(obj10);
                                                            }
                                                        }
                                                    }
                                                    List list11 = arrayList5;
                                                    if (arrayList3.isEmpty()) {
                                                        orientation22 = orientation5;
                                                        i63 = i58;
                                                        z82 = z18;
                                                        i59 = i149;
                                                        i60 = i77;
                                                        i61 = i91;
                                                        snapPosition3 = snapPosition6;
                                                        i62 = i46;
                                                        obj7 = null;
                                                        arrayList6 = arrayList3;
                                                    } else {
                                                        Object obj11 = arrayList3.get(0);
                                                        int i178 = ((MeasuredPage) obj11).offset;
                                                        Object obj12 = obj11;
                                                        orientation22 = orientation5;
                                                        i59 = i149;
                                                        i60 = i77;
                                                        i61 = i91;
                                                        snapPosition3 = snapPosition6;
                                                        i62 = i46;
                                                        float f6 = -Math.abs(i178 - snapPosition3.position(i60, i59, i61, i62));
                                                        int size13 = arrayList3.size() - 1;
                                                        if (1 <= size13) {
                                                            i63 = i58;
                                                            float f7 = f6;
                                                            int i179 = 1;
                                                            while (true) {
                                                                obj7 = arrayList3.get(i179);
                                                                arrayList6 = arrayList3;
                                                                z82 = z18;
                                                                float f8 = -Math.abs(((MeasuredPage) obj7).offset - snapPosition3.position(i60, i59, i61, i62));
                                                                if (Float.compare(f7, f8) < 0) {
                                                                    f7 = f8;
                                                                } else {
                                                                    obj7 = obj12;
                                                                }
                                                                z10 = true;
                                                                if (i179 == size13) {
                                                                    break;
                                                                }
                                                                i179++;
                                                                obj12 = obj7;
                                                                arrayList3 = arrayList6;
                                                                z18 = z82;
                                                            }
                                                        } else {
                                                            arrayList6 = arrayList3;
                                                            z10 = true;
                                                            i63 = i58;
                                                            z82 = z18;
                                                            obj7 = obj12;
                                                        }
                                                    }
                                                    MeasuredPage measuredPage10 = (MeasuredPage) obj7;
                                                    pagerMeasureResult = new PagerMeasureResult(arrayList6, i150, i107, i62, orientation22, i89, i112, z82, i81, measuredPage4, measuredPage10, i59 == 0 ? 0.0f : RangesKt___RangesKt.coerceIn((snapPosition3.position(i60, i150, i61, i62) - (measuredPage10 != null ? measuredPage10.offset : 0)) / i59, -0.5f, 0.5f), i55, (i49 < i158 || i52 > i48) ? z10 : false, snapPosition3, (MeasureResult) function33.invoke(Integer.valueOf(iM834constrainWidthK40F9xA), Integer.valueOf(i63), new Function1() { // from class: androidx.compose.foundation.pager.PagerMeasureKt$measurePager$14
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(1);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj13) {
                                                            Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj13;
                                                            final List<MeasuredPage> list12 = arrayList2;
                                                            Function1 function14 = new Function1() { // from class: androidx.compose.foundation.pager.PagerMeasureKt$measurePager$14.1
                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                {
                                                                    super(1);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function1
                                                                /* renamed from: invoke */
                                                                public final Object mo781invoke(Object obj14) {
                                                                    List<MeasuredPage> list13;
                                                                    char c;
                                                                    int i180;
                                                                    Placeable.PlacementScope placementScope2 = (Placeable.PlacementScope) obj14;
                                                                    List<MeasuredPage> list14 = list12;
                                                                    int size14 = list14.size();
                                                                    for (int i181 = 0; i181 < size14; i181++) {
                                                                        MeasuredPage measuredPage11 = list14.get(i181);
                                                                        if (measuredPage11.mainAxisLayoutSize == Integer.MIN_VALUE) {
                                                                            InlineClassHelperKt.throwIllegalArgumentException("position() should be called first");
                                                                        }
                                                                        int size15 = measuredPage11.placeables.size();
                                                                        int i182 = 0;
                                                                        while (i182 < size15) {
                                                                            Placeable placeable = (Placeable) measuredPage11.placeables.get(i182);
                                                                            int i183 = i182 * 2;
                                                                            int[] iArr3 = measuredPage11.placeableOffsets;
                                                                            long j19 = (iArr3[i183] << 32) | (iArr3[i183 + 1] & 4294967295L);
                                                                            IntOffset.Companion companion6 = IntOffset.Companion;
                                                                            boolean z21 = measuredPage11.reverseLayout;
                                                                            boolean z22 = measuredPage11.isVertical;
                                                                            if (z21) {
                                                                                if (z22) {
                                                                                    c = ' ';
                                                                                    list13 = list14;
                                                                                    i180 = (int) (j19 >> 32);
                                                                                } else {
                                                                                    c = ' ';
                                                                                    list13 = list14;
                                                                                    i180 = (measuredPage11.mainAxisLayoutSize - ((int) (j19 >> 32))) - (z22 ? placeable.height : placeable.width);
                                                                                }
                                                                                j19 = ((z22 ? (measuredPage11.mainAxisLayoutSize - ((int) (j19 & 4294967295L))) - (z22 ? placeable.height : placeable.width) : (int) (j19 & 4294967295L)) & 4294967295L) | (i180 << c);
                                                                            } else {
                                                                                list13 = list14;
                                                                            }
                                                                            long jM853plusqkQi6aY = IntOffset.m853plusqkQi6aY(j19, measuredPage11.visualOffset);
                                                                            if (z22) {
                                                                                Placeable.PlacementScope.m631placeWithLayeraW9wM$default(placementScope2, placeable, jM853plusqkQi6aY, null, 6);
                                                                            } else {
                                                                                Placeable.PlacementScope.m629placeRelativeWithLayeraW9wM$default(placementScope2, placeable, jM853plusqkQi6aY);
                                                                            }
                                                                            i182++;
                                                                            list14 = list13;
                                                                        }
                                                                    }
                                                                    return Unit.INSTANCE;
                                                                }
                                                            };
                                                            placementScope.motionFrameOfReferencePlacement = true;
                                                            function14.mo781invoke(placementScope);
                                                            placementScope.motionFrameOfReferencePlacement = false;
                                                            mutableState.getValue();
                                                            return Unit.INSTANCE;
                                                        }
                                                    }), z17, list10, list11, coroutineScope3);
                                                }
                                                PagerMeasureResult pagerMeasureResult2 = pagerMeasureResult;
                                                pagerState.applyMeasureResult$foundation_release(pagerMeasureResult2, subcomposeMeasureScope.isLookingAhead(), false);
                                                return pagerMeasureResult2;
                                            } catch (Throwable th) {
                                                Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                                                throw th;
                                            }
                                        }
                                    };
                                    pagerState3 = pagerState;
                                    f4 = f52;
                                    kProperty0 = kProperty03;
                                    composerImpl.updateRememberedValue(obj);
                                    Function2 function22 = (Function2) obj;
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    Orientation orientation22 = Orientation.Vertical;
                                    if (orientation != orientation22) {
                                    }
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    if ((i31 ^ 6) <= i20) {
                                        zChanged3 = (((i31 ^ 6) <= i20 && composerImpl.changed(pagerState3)) || (i30 & 6) == i20) | composerImpl.changed(z7);
                                        Object objRememberedValue82 = composerImpl.rememberedValue();
                                        if (zChanged3) {
                                        }
                                        objRememberedValue82 = new LazyLayoutSemanticState() { // from class: androidx.compose.foundation.pager.LazyLayoutSemanticStateKt$LazyLayoutSemanticState$1
                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final CollectionInfo collectionInfo() {
                                                boolean z10 = z7;
                                                PagerState pagerState5 = pagerState3;
                                                return z10 ? new CollectionInfo(pagerState5.getPageCount(), 1) : new CollectionInfo(1, pagerState5.getPageCount());
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final int getContentPadding() {
                                                PagerState pagerState5 = pagerState3;
                                                return (-((PagerMeasureResult) pagerState5.getLayoutInfo()).viewportStartOffset) + ((PagerMeasureResult) pagerState5.getLayoutInfo()).afterContentPadding;
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final float getMaxScrollOffset() {
                                                PagerState pagerState5 = pagerState3;
                                                return PagerStateKt.calculateNewMaxScrollOffset(pagerState5.getLayoutInfo(), pagerState5.getPageCount());
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final float getScrollOffset() {
                                                return PagerScrollPositionKt.currentAbsoluteScrollOffset(pagerState3);
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final int getViewport() {
                                                PagerState pagerState5 = pagerState3;
                                                return (int) (((PagerMeasureResult) pagerState5.getLayoutInfo()).orientation == Orientation.Vertical ? ((PagerMeasureResult) pagerState5.getLayoutInfo()).m180getViewportSizeYbymL2g() & 4294967295L : ((PagerMeasureResult) pagerState5.getLayoutInfo()).m180getViewportSizeYbymL2g() >> 32);
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final Object scrollToItem(int i41, Continuation continuation) {
                                                Object objScrollToPage$default = PagerState.scrollToPage$default(pagerState3, i41, continuation);
                                                return objScrollToPage$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objScrollToPage$default : Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl.updateRememberedValue(objRememberedValue82);
                                        LazyLayoutSemanticState lazyLayoutSemanticState22 = (LazyLayoutSemanticState) objRememberedValue82;
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                        if (i29 != 32) {
                                        }
                                        z4 = ((i12 & 458752) != 131072) | (i29 != 32);
                                        objRememberedValue = composerImpl.rememberedValue();
                                        if (!z4) {
                                        }
                                    } else {
                                        zChanged3 = (((i31 ^ 6) <= i20 && composerImpl.changed(pagerState3)) || (i30 & 6) == i20) | composerImpl.changed(z7);
                                        Object objRememberedValue822 = composerImpl.rememberedValue();
                                        if (zChanged3) {
                                        }
                                        objRememberedValue822 = new LazyLayoutSemanticState() { // from class: androidx.compose.foundation.pager.LazyLayoutSemanticStateKt$LazyLayoutSemanticState$1
                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final CollectionInfo collectionInfo() {
                                                boolean z10 = z7;
                                                PagerState pagerState5 = pagerState3;
                                                return z10 ? new CollectionInfo(pagerState5.getPageCount(), 1) : new CollectionInfo(1, pagerState5.getPageCount());
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final int getContentPadding() {
                                                PagerState pagerState5 = pagerState3;
                                                return (-((PagerMeasureResult) pagerState5.getLayoutInfo()).viewportStartOffset) + ((PagerMeasureResult) pagerState5.getLayoutInfo()).afterContentPadding;
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final float getMaxScrollOffset() {
                                                PagerState pagerState5 = pagerState3;
                                                return PagerStateKt.calculateNewMaxScrollOffset(pagerState5.getLayoutInfo(), pagerState5.getPageCount());
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final float getScrollOffset() {
                                                return PagerScrollPositionKt.currentAbsoluteScrollOffset(pagerState3);
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final int getViewport() {
                                                PagerState pagerState5 = pagerState3;
                                                return (int) (((PagerMeasureResult) pagerState5.getLayoutInfo()).orientation == Orientation.Vertical ? ((PagerMeasureResult) pagerState5.getLayoutInfo()).m180getViewportSizeYbymL2g() & 4294967295L : ((PagerMeasureResult) pagerState5.getLayoutInfo()).m180getViewportSizeYbymL2g() >> 32);
                                            }

                                            @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                            public final Object scrollToItem(int i41, Continuation continuation) {
                                                Object objScrollToPage$default = PagerState.scrollToPage$default(pagerState3, i41, continuation);
                                                return objScrollToPage$default == CoroutineSingletons.COROUTINE_SUSPENDED ? objScrollToPage$default : Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl.updateRememberedValue(objRememberedValue822);
                                        LazyLayoutSemanticState lazyLayoutSemanticState222 = (LazyLayoutSemanticState) objRememberedValue822;
                                        if (ComposerKt.isTraceInProgress()) {
                                        }
                                        if (i29 != 32) {
                                        }
                                        z4 = ((i12 & 458752) != 131072) | (i29 != 32);
                                        objRememberedValue = composerImpl.rememberedValue();
                                        if (!z4) {
                                        }
                                    }
                                }
                            }
                        }
                        objRememberedValue3 = new Function0() { // from class: androidx.compose.foundation.pager.LazyLayoutPagerKt$Pager$pagerItemProvider$1$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return Integer.valueOf(pagerState.getPageCount());
                            }
                        };
                        composerImpl2.updateRememberedValue(objRememberedValue3);
                        final Function0 function03 = (Function0) objRememberedValue3;
                        int i302 = i12 >> 3;
                        int i312 = i302 & 14;
                        int i322 = i16 >> 15;
                        int i332 = i312 | (i322 & 112) | (i16 & 896);
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        int i342 = i16;
                        final State<? extends Function4> mutableStateRememberUpdatedState3 = SnapshotStateKt.rememberUpdatedState(function42, composerImpl2);
                        final State<? extends Function1> mutableStateRememberUpdatedState22 = SnapshotStateKt.rememberUpdatedState(function1, composerImpl2);
                        if (((i332 & 14) ^ 6) <= 4) {
                            zChanged = ((((i332 & 14) ^ 6) <= 4 && composerImpl2.changed(pagerState)) || (i332 & 6) == 4) | composerImpl2.changed(mutableStateRememberUpdatedState3) | composerImpl2.changed(mutableStateRememberUpdatedState22) | composerImpl2.changed(function03);
                            Object objRememberedValue42 = composerImpl2.rememberedValue();
                            if (!zChanged) {
                            }
                        } else {
                            zChanged = ((((i332 & 14) ^ 6) <= 4 && composerImpl2.changed(pagerState)) || (i332 & 6) == 4) | composerImpl2.changed(mutableStateRememberUpdatedState3) | composerImpl2.changed(mutableStateRememberUpdatedState22) | composerImpl2.changed(function03);
                            Object objRememberedValue422 = composerImpl2.rememberedValue();
                            if (!zChanged) {
                            }
                        }
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                        final PagerState pagerState5 = pagerState2;
                        final int i41 = i17;
                        recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.pager.LazyLayoutPagerKt$Pager$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj5, Object obj6) {
                                ((Number) obj6).intValue();
                                LazyLayoutPagerKt.m177PagereLwUrMk(modifier, pagerState5, paddingValues, z, orientation, targetedFlingBehavior, z2, overscrollEffect, i41, f2, pageSize, nestedScrollConnection, function1, horizontal, vertical, snapPosition, function4, (Composer) obj5, RecomposeScopeImplKt.updateChangedFlags(i2 | 1), RecomposeScopeImplKt.updateChangedFlags(i3), i4);
                                return Unit.INSTANCE;
                            }
                        };
                        return;
                    }
                    return;
                }
                i15 |= 196608;
                snapPosition2 = snapPosition;
                if ((i4 & 65536) == 0) {
                }
                if (composerImpl2.shouldExecute(i12 & 1, (i12 & 306783379) == 306783378 || (599187 & i16) != 599186)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            i13 = i14;
            if ((i4 & 2048) != 0) {
            }
            if ((i4 & 4096) != 0) {
            }
            if ((i4 & 8192) != 0) {
            }
            if ((i4 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
            }
            if ((32768 & i4) == 0) {
            }
            snapPosition2 = snapPosition;
            if ((i4 & 65536) == 0) {
            }
            if (composerImpl2.shouldExecute(i12 & 1, (i12 & 306783379) == 306783378 || (599187 & i16) != 599186)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        i9 = 100663296;
        i10 = i4 & 512;
        if (i10 == 0) {
        }
        i12 = i8;
        if ((i4 & 1024) == 0) {
        }
        i13 = i14;
        if ((i4 & 2048) != 0) {
        }
        if ((i4 & 4096) != 0) {
        }
        if ((i4 & 8192) != 0) {
        }
        if ((i4 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0) {
        }
        if ((32768 & i4) == 0) {
        }
        snapPosition2 = snapPosition;
        if ((i4 & 65536) == 0) {
        }
        if (composerImpl2.shouldExecute(i12 & 1, (i12 & 306783379) == 306783378 || (599187 & i16) != 599186)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
