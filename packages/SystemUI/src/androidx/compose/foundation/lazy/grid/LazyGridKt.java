package androidx.compose.foundation.lazy.grid;

import androidx.collection.IntListKt;
import androidx.compose.foundation.CheckScrollableContainerConstraintsKt;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.ScrollingContainerKt;
import androidx.compose.foundation.gestures.FlingBehavior;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.ScrollableDefaults;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.lazy.grid.LazyGridSpanLayoutProvider;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsModifierLocalKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutBeyondBoundsStateKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimator;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProviderKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScope;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScopeImpl;
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItemKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState;
import androidx.compose.foundation.lazy.layout.LazyLayoutSemanticsKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutStickyItemsKt;
import androidx.compose.foundation.lazy.layout.NearestRangeKeyIndexMap;
import androidx.compose.foundation.lazy.layout.StickyItemsPlacement;
import androidx.compose.foundation.lazy.layout.StickyItemsPlacement$Companion$StickToTopPlacement$1;
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
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.SubcomposeMeasureScope;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.CollectionInfo;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.KProperty0;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public abstract class LazyGridKt {
    /* JADX WARN: Removed duplicated region for block: B:104:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x01ee  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0231  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x02ac  */
    /* JADX WARN: Removed duplicated region for block: B:320:0x04b9  */
    /* JADX WARN: Removed duplicated region for block: B:323:0x04cb  */
    /* JADX WARN: Removed duplicated region for block: B:325:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x011b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LazyGrid(Modifier modifier, final LazyGridState lazyGridState, final LazyGridSlotsProvider lazyGridSlotsProvider, PaddingValues paddingValues, boolean z, final boolean z2, FlingBehavior flingBehavior, final boolean z3, final OverscrollEffect overscrollEffect, final Arrangement.Vertical vertical, final Arrangement.Horizontal horizontal, final Function1 function1, Composer composer, final int i, final int i2, final int i3) {
        int i4;
        int i5;
        int i6;
        boolean z4;
        int i7;
        int i8;
        int i9;
        int i10;
        ComposerImpl composerImpl;
        Modifier modifier2;
        final PaddingValues paddingValues2;
        final FlingBehavior flingBehavior2;
        final boolean z5;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        Modifier modifier3;
        PaddingValues paddingValuesM120PaddingValues0680j_4;
        FlingBehavior flingBehavior3;
        boolean z6;
        StickyItemsPlacement$Companion$StickToTopPlacement$1 stickyItemsPlacement$Companion$StickToTopPlacement$1;
        Modifier modifier4;
        ComposerImpl composerImpl2;
        int i11;
        final boolean z7;
        LazyGridState lazyGridState2;
        PaddingValues paddingValues3;
        KProperty0 kProperty0;
        Modifier modifierLazyLayoutBeyondBoundsModifier;
        final LazyGridState lazyGridState3 = lazyGridState;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(708740370);
        boolean z8 = true;
        int i12 = i3 & 1;
        if (i12 != 0) {
            i5 = i | 6;
            i4 = 4;
        } else {
            i4 = 4;
            if ((i & 6) == 0) {
                i5 = i | (composerImpl3.changed(modifier) ? 4 : 2);
            } else {
                i5 = i;
            }
        }
        if ((i3 & 2) != 0) {
            i5 |= 48;
        } else if ((i & 48) == 0) {
            i5 |= composerImpl3.changed(lazyGridState3) ? 32 : 16;
        }
        int i13 = i5;
        if ((i3 & 4) != 0) {
            i13 |= 384;
        } else if ((i & 384) == 0) {
            i13 |= (i & 512) == 0 ? composerImpl3.changed(lazyGridSlotsProvider) : composerImpl3.changedInstance(lazyGridSlotsProvider) ? 256 : 128;
        }
        int i14 = i3 & 8;
        if (i14 != 0) {
            i13 |= 3072;
        } else {
            if ((i & 3072) == 0) {
                i13 |= composerImpl3.changed(paddingValues) ? 2048 : 1024;
            }
            i6 = i3 & 16;
            if (i6 == 0) {
                i13 |= 24576;
            } else {
                if ((i & 24576) == 0) {
                    z4 = z;
                    i13 |= composerImpl3.changed(z4) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                }
                if ((i3 & 32) != 0) {
                    i13 |= 196608;
                } else if ((i & 196608) == 0) {
                    i13 |= composerImpl3.changed(z2) ? 131072 : 65536;
                }
                if ((i & 1572864) == 0) {
                    i13 |= ((i3 & 64) == 0 && composerImpl3.changed(flingBehavior)) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                }
                if ((128 & i3) != 0) {
                    i13 |= 12582912;
                } else if ((i & 12582912) == 0) {
                    i13 |= composerImpl3.changed(z3) ? 8388608 : 4194304;
                }
                if ((i3 & 256) != 0) {
                    i13 |= 100663296;
                } else {
                    if ((i & 100663296) == 0) {
                        i13 |= composerImpl3.changed(overscrollEffect) ? 67108864 : 33554432;
                    }
                    if ((i3 & 512) == 0) {
                        i13 |= 805306368;
                    } else if ((i & 805306368) == 0) {
                        i13 |= composerImpl3.changed(vertical) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                    }
                    if ((1024 & i3) == 0) {
                        i8 = i2 | 6;
                    } else {
                        if ((i2 & 6) != 0) {
                            i7 = i2;
                            if ((i3 & 2048) != 0) {
                                i9 = i7 | 48;
                            } else if ((i2 & 48) == 0) {
                                i9 = i7 | (composerImpl3.changedInstance(function1) ? 32 : 16);
                            } else {
                                i9 = i7;
                            }
                            i10 = i9;
                            if (composerImpl3.shouldExecute(i13 & 1, ((306783379 & i13) == 306783378 && (i10 & 19) == 18) ? false : true)) {
                                composerImpl3.startDefaults();
                                if ((i & 1) == 0 || composerImpl3.getDefaultsInvalid()) {
                                    modifier3 = i12 != 0 ? Modifier.Companion : modifier;
                                    if (i14 != 0) {
                                        Dp.Companion companion = Dp.Companion;
                                        paddingValuesM120PaddingValues0680j_4 = PaddingKt.m120PaddingValues0680j_4(0);
                                    } else {
                                        paddingValuesM120PaddingValues0680j_4 = paddingValues;
                                    }
                                    if (i6 != 0) {
                                        z4 = false;
                                    }
                                    if ((i3 & 64) != 0) {
                                        ScrollableDefaults.INSTANCE.getClass();
                                        i13 &= -3670017;
                                        flingBehavior3 = ScrollableDefaults.flingBehavior(composerImpl3);
                                    }
                                    boolean z9 = z4;
                                    composerImpl3.endDefaults();
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.foundation.lazy.grid.LazyGrid (LazyGrid.kt:82)");
                                    }
                                    int i15 = i13 >> 3;
                                    int i16 = i15 & 14;
                                    int i17 = i16 | (i10 & 112);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.foundation.lazy.grid.rememberLazyGridItemProviderLambda (LazyGridItemProvider.kt:42)");
                                    }
                                    final MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function1, composerImpl3);
                                    Modifier modifier5 = modifier3;
                                    int i18 = i4;
                                    z6 = (((i17 & 14) ^ 6) <= i18 && composerImpl3.changed(lazyGridState3)) || (i17 & 6) == i18;
                                    Object objRememberedValue = composerImpl3.rememberedValue();
                                    Composer.Companion companion2 = Composer.Companion;
                                    if (!z6) {
                                        companion2.getClass();
                                        if (objRememberedValue == Composer.Companion.Empty) {
                                            final State stateDerivedStateOf = SnapshotStateKt.derivedStateOf(SnapshotStateKt.referentialEqualityPolicy(), new Function0() { // from class: androidx.compose.foundation.lazy.grid.LazyGridItemProviderKt$rememberLazyGridItemProviderLambda$1$intervalContentState$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                /* JADX WARN: Multi-variable type inference failed */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    return new LazyGridIntervalContent((Function1) mutableStateRememberUpdatedState.getValue());
                                                }
                                            });
                                            final State stateDerivedStateOf2 = SnapshotStateKt.derivedStateOf(SnapshotStateKt.referentialEqualityPolicy(), new Function0() { // from class: androidx.compose.foundation.lazy.grid.LazyGridItemProviderKt$rememberLazyGridItemProviderLambda$1$itemProviderState$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                public final Object invoke() {
                                                    LazyGridIntervalContent lazyGridIntervalContent = (LazyGridIntervalContent) stateDerivedStateOf.getValue();
                                                    return new LazyGridItemProviderImpl(lazyGridState3, lazyGridIntervalContent, new NearestRangeKeyIndexMap((IntRange) lazyGridState3.scrollPosition.nearestRangeState.getValue(), lazyGridIntervalContent));
                                                }
                                            });
                                            objRememberedValue = new PropertyReference0Impl(stateDerivedStateOf2) { // from class: androidx.compose.foundation.lazy.grid.LazyGridItemProviderKt$rememberLazyGridItemProviderLambda$1$1
                                                @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                                                public final Object get() {
                                                    return ((State) this.receiver).getValue();
                                                }
                                            };
                                            composerImpl3.updateRememberedValue(objRememberedValue);
                                        }
                                        final KProperty0 kProperty02 = (KProperty0) objRememberedValue;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        int i19 = i16 | ((i13 >> 9) & 112);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.foundation.lazy.grid.rememberLazyGridSemanticState (LazySemantics.kt:31)");
                                        }
                                        boolean z10 = ((((i19 & 112) ^ 48) > 32 && composerImpl3.changed(z9)) || (i19 & 48) == 32) | ((((i19 & 14) ^ 6) > 4 && composerImpl3.changed(lazyGridState3)) || (i19 & 6) == 4);
                                        Object objRememberedValue2 = composerImpl3.rememberedValue();
                                        if (!z10) {
                                            companion2.getClass();
                                            if (objRememberedValue2 == Composer.Companion.Empty) {
                                                objRememberedValue2 = new LazyLayoutSemanticState() { // from class: androidx.compose.foundation.lazy.grid.LazySemanticsKt$rememberLazyGridSemanticState$1$1
                                                    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                                    public final CollectionInfo collectionInfo() {
                                                        return new CollectionInfo(-1, -1);
                                                    }

                                                    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                                    public final int getContentPadding() {
                                                        LazyGridState lazyGridState4 = lazyGridState3;
                                                        return (-((LazyGridMeasureResult) lazyGridState4.getLayoutInfo()).viewportStartOffset) + ((LazyGridMeasureResult) lazyGridState4.getLayoutInfo()).afterContentPadding;
                                                    }

                                                    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                                    public final float getMaxScrollOffset() {
                                                        LazyGridState lazyGridState4 = lazyGridState3;
                                                        int index = lazyGridState4.scrollPosition.getIndex();
                                                        int scrollOffset = lazyGridState4.scrollPosition.getScrollOffset();
                                                        return lazyGridState4.getCanScrollForward() ? (index * 500) + scrollOffset + 100 : (index * 500) + scrollOffset;
                                                    }

                                                    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                                    public final float getScrollOffset() {
                                                        LazyGridState lazyGridState4 = lazyGridState3;
                                                        return (lazyGridState4.scrollPosition.getIndex() * 500) + lazyGridState4.scrollPosition.getScrollOffset();
                                                    }

                                                    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                                    public final int getViewport() {
                                                        LazyGridState lazyGridState4 = lazyGridState3;
                                                        return (int) (((LazyGridMeasureResult) lazyGridState4.getLayoutInfo()).orientation == Orientation.Vertical ? ((LazyGridMeasureResult) lazyGridState4.getLayoutInfo()).m161getViewportSizeYbymL2g() & 4294967295L : ((LazyGridMeasureResult) lazyGridState4.getLayoutInfo()).m161getViewportSizeYbymL2g() >> 32);
                                                    }

                                                    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                                    public final Object scrollToItem(int i20, Continuation continuation) {
                                                        LazyGridState.Companion companion3 = LazyGridState.Companion;
                                                        Object objScrollToItem = lazyGridState3.scrollToItem(i20, 0, (SuspendLambda) continuation);
                                                        return objScrollToItem == CoroutineSingletons.COROUTINE_SUSPENDED ? objScrollToItem : Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl3.updateRememberedValue(objRememberedValue2);
                                            }
                                            LazySemanticsKt$rememberLazyGridSemanticState$1$1 lazySemanticsKt$rememberLazyGridSemanticState$1$1 = (LazySemanticsKt$rememberLazyGridSemanticState$1$1) objRememberedValue2;
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            Object objRememberedValue3 = composerImpl3.rememberedValue();
                                            companion2.getClass();
                                            Object obj = Composer.Companion.Empty;
                                            if (objRememberedValue3 == obj) {
                                                objRememberedValue3 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl3);
                                                composerImpl3.updateRememberedValue(objRememberedValue3);
                                            }
                                            final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue3;
                                            final GraphicsContext graphicsContext = (GraphicsContext) composerImpl3.consume(CompositionLocalsKt.LocalGraphicsContext);
                                            if (((Boolean) composerImpl3.consume(CompositionLocalsKt.LocalProvidableScrollCaptureInProgress)).booleanValue()) {
                                                stickyItemsPlacement$Companion$StickToTopPlacement$1 = null;
                                            } else {
                                                StickyItemsPlacement.Companion.getClass();
                                                stickyItemsPlacement$Companion$StickToTopPlacement$1 = StickyItemsPlacement.Companion.StickToTopPlacement;
                                            }
                                            int i20 = (i13 & 524272) | ((i10 << 18) & 3670016) | ((i13 >> 6) & 29360128);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.foundation.lazy.grid.rememberLazyGridMeasurePolicy (LazyGrid.kt:179)");
                                            }
                                            final StickyItemsPlacement$Companion$StickToTopPlacement$1 stickyItemsPlacement$Companion$StickToTopPlacement$12 = stickyItemsPlacement$Companion$StickToTopPlacement$1;
                                            boolean zChanged = ((((i20 & 896) ^ 384) > 256 && composerImpl3.changed(lazyGridSlotsProvider)) || (i20 & 384) == 256) | ((((i20 & 112) ^ 48) > 32 && composerImpl3.changed(lazyGridState3)) || (i20 & 48) == 32) | ((((i20 & 7168) ^ 3072) > 2048 && composerImpl3.changed(paddingValuesM120PaddingValues0680j_4)) || (i20 & 3072) == 2048) | ((((57344 & i20) ^ 24576) > 16384 && composerImpl3.changed(z9)) || (i20 & 24576) == 16384) | ((((458752 & i20) ^ 196608) > 131072 && composerImpl3.changed(z2)) || (i20 & 196608) == 131072) | ((((3670016 & i20) ^ 1572864) > 1048576 && composerImpl3.changed(horizontal)) || (i20 & 1572864) == 1048576) | ((((29360128 & i20) ^ 12582912) > 8388608 && composerImpl3.changed(vertical)) || (i20 & 12582912) == 8388608) | composerImpl3.changed(graphicsContext);
                                            Object objRememberedValue4 = composerImpl3.rememberedValue();
                                            if (zChanged || objRememberedValue4 == obj) {
                                                modifier4 = modifier5;
                                                composerImpl2 = composerImpl3;
                                                i11 = 4;
                                                final PaddingValues paddingValues4 = paddingValuesM120PaddingValues0680j_4;
                                                z7 = z9;
                                                Object obj2 = new Function2() { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(2);
                                                    }

                                                    /* JADX WARN: Multi-variable type inference failed */
                                                    /* JADX WARN: Removed duplicated region for block: B:103:0x029e  */
                                                    /* JADX WARN: Removed duplicated region for block: B:107:0x02e4  */
                                                    /* JADX WARN: Removed duplicated region for block: B:110:0x02ec  */
                                                    /* JADX WARN: Removed duplicated region for block: B:113:0x02f7  */
                                                    /* JADX WARN: Removed duplicated region for block: B:125:0x0379  */
                                                    /* JADX WARN: Removed duplicated region for block: B:172:0x0450  */
                                                    /* JADX WARN: Removed duplicated region for block: B:221:0x0523  */
                                                    /* JADX WARN: Removed duplicated region for block: B:232:0x056c  */
                                                    /* JADX WARN: Removed duplicated region for block: B:276:0x0637  */
                                                    /* JADX WARN: Removed duplicated region for block: B:279:0x063e  */
                                                    /* JADX WARN: Removed duplicated region for block: B:282:0x0647  */
                                                    /* JADX WARN: Removed duplicated region for block: B:304:0x06c9  */
                                                    /* JADX WARN: Removed duplicated region for block: B:307:0x06cf A[ADDED_TO_REGION] */
                                                    /* JADX WARN: Removed duplicated region for block: B:310:0x06d5  */
                                                    /* JADX WARN: Removed duplicated region for block: B:312:0x06de  */
                                                    /* JADX WARN: Removed duplicated region for block: B:320:0x0703  */
                                                    /* JADX WARN: Removed duplicated region for block: B:322:0x0709  */
                                                    /* JADX WARN: Removed duplicated region for block: B:324:0x0710  */
                                                    /* JADX WARN: Removed duplicated region for block: B:326:0x0716  */
                                                    /* JADX WARN: Removed duplicated region for block: B:330:0x0723  */
                                                    /* JADX WARN: Removed duplicated region for block: B:332:0x072c  */
                                                    /* JADX WARN: Removed duplicated region for block: B:333:0x072e  */
                                                    /* JADX WARN: Removed duplicated region for block: B:336:0x0735  */
                                                    /* JADX WARN: Removed duplicated region for block: B:337:0x0738  */
                                                    /* JADX WARN: Removed duplicated region for block: B:344:0x074b A[LOOP:12: B:343:0x0749->B:344:0x074b, LOOP_END] */
                                                    /* JADX WARN: Removed duplicated region for block: B:347:0x0767  */
                                                    /* JADX WARN: Removed duplicated region for block: B:393:0x0850  */
                                                    /* JADX WARN: Removed duplicated region for block: B:412:0x0902  */
                                                    /* JADX WARN: Removed duplicated region for block: B:429:0x095b  */
                                                    /* JADX WARN: Removed duplicated region for block: B:433:0x0985  */
                                                    /* JADX WARN: Removed duplicated region for block: B:437:0x098c  */
                                                    /* JADX WARN: Removed duplicated region for block: B:440:0x09ae  */
                                                    /* JADX WARN: Removed duplicated region for block: B:442:0x09b3  */
                                                    /* JADX WARN: Removed duplicated region for block: B:468:0x0568 A[EDGE_INSN: B:468:0x0568->B:230:0x0568 BREAK  A[LOOP:4: B:219:0x051f->B:229:0x0561], SYNTHETIC] */
                                                    /* JADX WARN: Removed duplicated region for block: B:50:0x0137  */
                                                    /* JADX WARN: Removed duplicated region for block: B:51:0x0161  */
                                                    /* JADX WARN: Removed duplicated region for block: B:54:0x016c  */
                                                    /* JADX WARN: Removed duplicated region for block: B:59:0x0180  */
                                                    /* JADX WARN: Removed duplicated region for block: B:64:0x0198  */
                                                    /* JADX WARN: Removed duplicated region for block: B:65:0x019f  */
                                                    /* JADX WARN: Removed duplicated region for block: B:69:0x01b2  */
                                                    /* JADX WARN: Removed duplicated region for block: B:80:0x0223  */
                                                    /* JADX WARN: Removed duplicated region for block: B:82:0x022a  */
                                                    /* JADX WARN: Removed duplicated region for block: B:86:0x0243 A[Catch: all -> 0x0262, TryCatch #0 {all -> 0x0262, blocks: (B:84:0x0231, B:86:0x0243, B:91:0x0259, B:96:0x026f, B:95:0x0265), top: B:450:0x0231 }] */
                                                    /* JADX WARN: Removed duplicated region for block: B:87:0x0252  */
                                                    /* JADX WARN: Removed duplicated region for block: B:95:0x0265 A[Catch: all -> 0x0262, TryCatch #0 {all -> 0x0262, blocks: (B:84:0x0231, B:86:0x0243, B:91:0x0259, B:96:0x026f, B:95:0x0265), top: B:450:0x0231 }] */
                                                    /* JADX WARN: Type inference failed for: r32v0, types: [androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1, androidx.compose.foundation.lazy.grid.LazyGridMeasuredLineProvider] */
                                                    /* JADX WARN: Type inference failed for: r3v28, types: [androidx.compose.foundation.lazy.layout.LazyLayoutItemAnimator] */
                                                    /* JADX WARN: Type inference failed for: r3v65, types: [kotlin.ranges.IntProgression] */
                                                    /* JADX WARN: Type inference failed for: r42v0, types: [androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredItemProvider$1, androidx.compose.foundation.lazy.grid.LazyGridMeasuredItemProvider, androidx.compose.foundation.lazy.layout.LazyLayoutMeasuredItemProvider] */
                                                    @Override // kotlin.jvm.functions.Function2
                                                    /*
                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                    */
                                                    public final Object invoke(Object obj3, Object obj4) {
                                                        int iMo52roundToPx0680j_4;
                                                        int iMo52roundToPx0680j_42;
                                                        int i21;
                                                        LazyGridSlots lazyGridSlots;
                                                        int length;
                                                        int i22;
                                                        final LazyGridSlots lazyGridSlots2;
                                                        LazyGridItemProvider lazyGridItemProvider;
                                                        float fMo95getSpacingD9Ej5fM;
                                                        final int i23;
                                                        final boolean z11;
                                                        final LazyGridSpanLayoutProvider lazyGridSpanLayoutProvider;
                                                        long j;
                                                        final int i24;
                                                        final LazyGridSpanLayoutProvider lazyGridSpanLayoutProvider2;
                                                        Snapshot currentThreadSnapshot;
                                                        Function1 readObserver;
                                                        Snapshot snapshotMakeCurrentNonObservable;
                                                        LazyGridScrollPosition lazyGridScrollPosition;
                                                        int index;
                                                        int iFindIndexByKey;
                                                        int i25;
                                                        int lineIndexOfItem;
                                                        int scrollOffset;
                                                        int i26;
                                                        boolean z12;
                                                        int i27;
                                                        int i28;
                                                        int i29;
                                                        LazyGridMeasuredLine lazyGridMeasuredLine;
                                                        int size;
                                                        List arrayList;
                                                        int i30;
                                                        LazyGridSpanLayoutProvider lazyGridSpanLayoutProvider3;
                                                        List list;
                                                        List list2;
                                                        LazyLayoutMeasureScope lazyLayoutMeasureScope;
                                                        int i31;
                                                        List arrayList2;
                                                        int size2;
                                                        int i32;
                                                        int size3;
                                                        int i33;
                                                        boolean z13;
                                                        int size4;
                                                        int i34;
                                                        boolean z14;
                                                        int i35;
                                                        LazyGridMeasuredLine lazyGridMeasuredLine2;
                                                        int i36;
                                                        Function3 function3;
                                                        int i37;
                                                        int i38;
                                                        ArrayList arrayList3;
                                                        int i39;
                                                        LazyGridItemProvider lazyGridItemProvider2;
                                                        float f;
                                                        boolean z15;
                                                        int i40;
                                                        float f2;
                                                        int i41;
                                                        LazyGridMeasureResult lazyGridMeasureResult;
                                                        boolean z16;
                                                        int i42;
                                                        LazyLayoutMeasureScope lazyLayoutMeasureScope2;
                                                        int i43;
                                                        int i44;
                                                        int i45;
                                                        int i46;
                                                        LazyGridMeasuredItemProvider lazyGridMeasuredItemProvider;
                                                        LazyGridItemInfo lazyGridItemInfo;
                                                        int iMin;
                                                        int i47;
                                                        int i48;
                                                        LazyGridMeasuredItem[] lazyGridMeasuredItemArr;
                                                        final LazyLayoutMeasureScope lazyLayoutMeasureScope3 = (LazyLayoutMeasureScope) obj3;
                                                        final long j2 = ((Constraints) obj4).value;
                                                        lazyGridState.measurementScopeInvalidator.getValue();
                                                        boolean z17 = lazyGridState.hasLookaheadOccurred || ((LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope3).subcomposeMeasureScope.isLookingAhead();
                                                        CheckScrollableContainerConstraintsKt.m32checkScrollableContainerConstraintsK40F9xA(z2 ? Orientation.Vertical : Orientation.Horizontal, j2);
                                                        if (z2) {
                                                            LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope3;
                                                            iMo52roundToPx0680j_4 = lazyLayoutMeasureScopeImpl.subcomposeMeasureScope.mo52roundToPx0680j_4(paddingValues4.mo111calculateLeftPaddingu2uoSUM(lazyLayoutMeasureScopeImpl.subcomposeMeasureScope.getLayoutDirection()));
                                                        } else {
                                                            LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl2 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope3;
                                                            iMo52roundToPx0680j_4 = lazyLayoutMeasureScopeImpl2.subcomposeMeasureScope.mo52roundToPx0680j_4(PaddingKt.calculateStartPadding(paddingValues4, lazyLayoutMeasureScopeImpl2.subcomposeMeasureScope.getLayoutDirection()));
                                                        }
                                                        if (z2) {
                                                            LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl3 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope3;
                                                            iMo52roundToPx0680j_42 = lazyLayoutMeasureScopeImpl3.subcomposeMeasureScope.mo52roundToPx0680j_4(paddingValues4.mo112calculateRightPaddingu2uoSUM(lazyLayoutMeasureScopeImpl3.subcomposeMeasureScope.getLayoutDirection()));
                                                        } else {
                                                            LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl4 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope3;
                                                            iMo52roundToPx0680j_42 = lazyLayoutMeasureScopeImpl4.subcomposeMeasureScope.mo52roundToPx0680j_4(PaddingKt.calculateEndPadding(paddingValues4, lazyLayoutMeasureScopeImpl4.subcomposeMeasureScope.getLayoutDirection()));
                                                        }
                                                        LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl5 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope3;
                                                        int iMo52roundToPx0680j_43 = lazyLayoutMeasureScopeImpl5.subcomposeMeasureScope.mo52roundToPx0680j_4(paddingValues4.mo113calculateTopPaddingD9Ej5fM());
                                                        float fMo110calculateBottomPaddingD9Ej5fM = paddingValues4.mo110calculateBottomPaddingD9Ej5fM();
                                                        SubcomposeMeasureScope subcomposeMeasureScope = lazyLayoutMeasureScopeImpl5.subcomposeMeasureScope;
                                                        int iMo52roundToPx0680j_44 = subcomposeMeasureScope.mo52roundToPx0680j_4(fMo110calculateBottomPaddingD9Ej5fM);
                                                        final int i49 = iMo52roundToPx0680j_43 + iMo52roundToPx0680j_44;
                                                        int i50 = iMo52roundToPx0680j_4 + iMo52roundToPx0680j_42;
                                                        boolean z18 = z2;
                                                        int i51 = z18 ? i49 : i50;
                                                        int i52 = (!z18 || z7) ? (z18 && z7) ? iMo52roundToPx0680j_44 : (z18 || z7) ? iMo52roundToPx0680j_42 : iMo52roundToPx0680j_4 : iMo52roundToPx0680j_43;
                                                        final int i53 = i51 - i52;
                                                        long jM835offsetNN6EwU = ConstraintsKt.m835offsetNN6EwU(-i50, -i49, j2);
                                                        LazyGridItemProvider lazyGridItemProvider3 = (LazyGridItemProvider) kProperty02.invoke();
                                                        LazyGridItemProviderImpl lazyGridItemProviderImpl = (LazyGridItemProviderImpl) lazyGridItemProvider3;
                                                        LazyGridSpanLayoutProvider lazyGridSpanLayoutProvider4 = lazyGridItemProviderImpl.intervalContent.spanLayoutProvider;
                                                        GridSlotCache gridSlotCache = (GridSlotCache) lazyGridSlotsProvider;
                                                        try {
                                                            if (gridSlotCache.cachedSizes != null) {
                                                                i21 = i50;
                                                                if (Constraints.m817equalsimpl0(gridSlotCache.cachedConstraints, jM835offsetNN6EwU) && gridSlotCache.cachedDensity == subcomposeMeasureScope.getDensity()) {
                                                                    lazyGridSlots = gridSlotCache.cachedSizes;
                                                                    lazyGridSlots.getClass();
                                                                }
                                                                length = lazyGridSlots.sizes.length;
                                                                if (length == lazyGridSpanLayoutProvider4.slotsPerLine) {
                                                                    lazyGridSpanLayoutProvider4.slotsPerLine = length;
                                                                    lazyGridSpanLayoutProvider4.buckets.clear();
                                                                    i22 = i21;
                                                                    lazyGridSlots2 = lazyGridSlots;
                                                                    lazyGridItemProvider = lazyGridItemProvider3;
                                                                    lazyGridSpanLayoutProvider4.buckets.add(new LazyGridSpanLayoutProvider.Bucket(0, 0, 2, null));
                                                                    lazyGridSpanLayoutProvider4.lastLineIndex = 0;
                                                                    lazyGridSpanLayoutProvider4.lastLineStartItemIndex = 0;
                                                                    lazyGridSpanLayoutProvider4.lastLineStartKnownSpan = 0;
                                                                    lazyGridSpanLayoutProvider4.cachedBucketIndex = -1;
                                                                    ((ArrayList) lazyGridSpanLayoutProvider4.cachedBucket).clear();
                                                                } else {
                                                                    i22 = i21;
                                                                    lazyGridSlots2 = lazyGridSlots;
                                                                    lazyGridItemProvider = lazyGridItemProvider3;
                                                                }
                                                                if (z2) {
                                                                    Arrangement.Horizontal horizontal2 = horizontal;
                                                                    if (horizontal2 == null) {
                                                                        InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("null horizontalArrangement when isVertical == false");
                                                                        throw new KotlinNothingValueException();
                                                                    }
                                                                    fMo95getSpacingD9Ej5fM = horizontal2.mo95getSpacingD9Ej5fM();
                                                                } else {
                                                                    Arrangement.Vertical vertical2 = vertical;
                                                                    if (vertical2 == null) {
                                                                        InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("null verticalArrangement when isVertical == true");
                                                                        throw new KotlinNothingValueException();
                                                                    }
                                                                    fMo95getSpacingD9Ej5fM = vertical2.mo95getSpacingD9Ej5fM();
                                                                }
                                                                final int iMo52roundToPx0680j_45 = subcomposeMeasureScope.mo52roundToPx0680j_4(fMo95getSpacingD9Ej5fM);
                                                                i23 = lazyGridItemProviderImpl.intervalContent.getIntervals$1().size;
                                                                int iM822getMaxHeightimpl = !z2 ? Constraints.m822getMaxHeightimpl(j2) - i49 : Constraints.m823getMaxWidthimpl(j2) - i22;
                                                                z11 = z7;
                                                                if (z11 || iM822getMaxHeightimpl > 0) {
                                                                    lazyGridSpanLayoutProvider = lazyGridSpanLayoutProvider4;
                                                                    j = (iMo52roundToPx0680j_43 & 4294967295L) | (iMo52roundToPx0680j_4 << 32);
                                                                    IntOffset.Companion companion3 = IntOffset.Companion;
                                                                } else {
                                                                    boolean z19 = z2;
                                                                    if (!z19) {
                                                                        iMo52roundToPx0680j_4 += iM822getMaxHeightimpl;
                                                                    }
                                                                    if (z19) {
                                                                        iMo52roundToPx0680j_43 += iM822getMaxHeightimpl;
                                                                    }
                                                                    lazyGridSpanLayoutProvider = lazyGridSpanLayoutProvider4;
                                                                    j = (iMo52roundToPx0680j_43 & 4294967295L) | (iMo52roundToPx0680j_4 << 32);
                                                                    IntOffset.Companion companion4 = IntOffset.Companion;
                                                                }
                                                                final long j3 = j;
                                                                final LazyGridState lazyGridState4 = lazyGridState;
                                                                final boolean z20 = z2;
                                                                final LazyGridItemProvider lazyGridItemProvider4 = lazyGridItemProvider;
                                                                i24 = i52;
                                                                final int i54 = i22;
                                                                int i55 = iM822getMaxHeightimpl;
                                                                List arrayList4 = null;
                                                                final ?? r42 = new LazyGridMeasuredItemProvider(lazyGridItemProvider4, lazyLayoutMeasureScope3, iMo52roundToPx0680j_45, lazyGridState4, z20, z11, i24, i53, j3) { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredItemProvider$1
                                                                    public final /* synthetic */ int $afterContentPadding;
                                                                    public final /* synthetic */ int $beforeContentPadding;
                                                                    public final /* synthetic */ boolean $isVertical;
                                                                    public final /* synthetic */ boolean $reverseLayout;
                                                                    public final /* synthetic */ LazyGridState $state;
                                                                    public final /* synthetic */ LazyLayoutMeasureScope $this_null;
                                                                    public final /* synthetic */ long $visualItemOffset;

                                                                    {
                                                                        this.$this_null = lazyLayoutMeasureScope3;
                                                                        this.$state = lazyGridState4;
                                                                        this.$isVertical = z20;
                                                                        this.$reverseLayout = z11;
                                                                        this.$beforeContentPadding = i24;
                                                                        this.$afterContentPadding = i53;
                                                                        this.$visualItemOffset = j3;
                                                                    }

                                                                    @Override // androidx.compose.foundation.lazy.grid.LazyGridMeasuredItemProvider
                                                                    /* renamed from: createItem-O3s9Psw, reason: not valid java name */
                                                                    public final LazyGridMeasuredItem mo160createItemO3s9Psw(int i56, Object obj5, Object obj6, int i57, int i58, List list3, long j4, int i59, int i60) {
                                                                        LayoutDirection layoutDirection = ((LazyLayoutMeasureScopeImpl) this.$this_null).subcomposeMeasureScope.getLayoutDirection();
                                                                        LazyLayoutItemAnimator lazyLayoutItemAnimator = this.$state.itemAnimator;
                                                                        return new LazyGridMeasuredItem(i56, obj5, this.$isVertical, i57, i58, this.$reverseLayout, layoutDirection, this.$beforeContentPadding, this.$afterContentPadding, list3, this.$visualItemOffset, obj6, lazyLayoutItemAnimator, j4, i59, i60, null);
                                                                    }
                                                                };
                                                                final boolean z21 = z2;
                                                                final ?? r32 = new LazyGridMeasuredLineProvider(z21, lazyGridSlots2, i23, iMo52roundToPx0680j_45, r42, lazyGridSpanLayoutProvider) { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1
                                                                    public final /* synthetic */ boolean $isVertical;
                                                                    public final /* synthetic */ LazyGridSlots $resolvedSlots;

                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(z21, lazyGridSlots2, i23, iMo52roundToPx0680j_45, r42, lazyGridSpanLayoutProvider);
                                                                        this.$isVertical = z21;
                                                                        this.$resolvedSlots = lazyGridSlots2;
                                                                    }

                                                                    @Override // androidx.compose.foundation.lazy.grid.LazyGridMeasuredLineProvider
                                                                    public final LazyGridMeasuredLine createLine(int i56, LazyGridMeasuredItem[] lazyGridMeasuredItemArr2, List list3, int i57) {
                                                                        return new LazyGridMeasuredLine(i56, lazyGridMeasuredItemArr2, this.$resolvedSlots, list3, this.$isVertical, i57);
                                                                    }
                                                                };
                                                                lazyGridSpanLayoutProvider2 = lazyGridSpanLayoutProvider;
                                                                Function1 function12 = new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$prefetchInfoRetriever$1
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    /* renamed from: invoke */
                                                                    public final Object mo781invoke(Object obj5) {
                                                                        LazyGridSpanLayoutProvider.LineConfiguration lineConfiguration = lazyGridSpanLayoutProvider2.getLineConfiguration(((Number) obj5).intValue());
                                                                        ArrayList arrayList5 = new ArrayList(lineConfiguration.spans.size());
                                                                        List list3 = lineConfiguration.spans;
                                                                        LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1 lazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1 = r32;
                                                                        int size5 = list3.size();
                                                                        int i56 = lineConfiguration.firstItemIndex;
                                                                        int i57 = 0;
                                                                        for (int i58 = 0; i58 < size5; i58++) {
                                                                            int i59 = (int) ((GridItemSpan) list3.get(i58)).packedValue;
                                                                            arrayList5.add(new Pair(Integer.valueOf(i56), Constraints.m815boximpl(lazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1.m164childConstraintsJhjzzOo$foundation_release(i57, i59))));
                                                                            i56++;
                                                                            i57 += i59;
                                                                        }
                                                                        return arrayList5;
                                                                    }
                                                                };
                                                                Snapshot.Companion companion5 = Snapshot.Companion;
                                                                LazyGridState lazyGridState5 = lazyGridState;
                                                                companion5.getClass();
                                                                currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                                                                readObserver = currentThreadSnapshot == null ? currentThreadSnapshot.getReadObserver() : null;
                                                                snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                                                                lazyGridScrollPosition = lazyGridState5.scrollPosition;
                                                                index = lazyGridScrollPosition.getIndex();
                                                                iFindIndexByKey = LazyLayoutItemProviderKt.findIndexByKey(lazyGridItemProvider4, index, lazyGridScrollPosition.lastKnownFirstItemKey);
                                                                if (index == iFindIndexByKey) {
                                                                    i25 = i55;
                                                                    ((SnapshotMutableIntStateImpl) lazyGridScrollPosition.index$delegate).setIntValue(iFindIndexByKey);
                                                                    lazyGridScrollPosition.nearestRangeState.update(index);
                                                                } else {
                                                                    i25 = i55;
                                                                }
                                                                if (iFindIndexByKey >= i23 || i23 <= 0) {
                                                                    lineIndexOfItem = lazyGridSpanLayoutProvider2.getLineIndexOfItem(iFindIndexByKey);
                                                                    scrollOffset = lazyGridScrollPosition.getScrollOffset();
                                                                } else {
                                                                    lineIndexOfItem = lazyGridSpanLayoutProvider2.getLineIndexOfItem(i23 - 1);
                                                                    scrollOffset = 0;
                                                                }
                                                                int i56 = lineIndexOfItem;
                                                                Unit unit = Unit.INSTANCE;
                                                                Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                                                                LazyGridState lazyGridState6 = lazyGridState;
                                                                List listCalculateLazyLayoutPinnedIndices = LazyLayoutBeyondBoundsStateKt.calculateLazyLayoutPinnedIndices(lazyGridItemProvider4, lazyGridState6.pinnedItems, lazyGridState6.beyondBoundsInfo);
                                                                float fFloatValue = (subcomposeMeasureScope.isLookingAhead() && z17) ? ((Number) ((SnapshotMutableStateImpl) lazyGridState._lazyLayoutScrollDeltaBetweenPasses._scrollDeltaBetweenPasses.value$delegate).getValue()).floatValue() : lazyGridState.scrollToBeConsumed;
                                                                ?? r3 = lazyGridState.itemAnimator;
                                                                final boolean zIsLookingAhead = subcomposeMeasureScope.isLookingAhead();
                                                                LazyGridState lazyGridState7 = lazyGridState;
                                                                LazyGridMeasureResult lazyGridMeasureResult2 = lazyGridState7.approachLayoutInfo;
                                                                boolean z22 = z2;
                                                                Arrangement.Vertical vertical3 = vertical;
                                                                Arrangement.Horizontal horizontal3 = horizontal;
                                                                boolean z23 = z7;
                                                                CoroutineScope coroutineScope2 = coroutineScope;
                                                                GraphicsContext graphicsContext2 = graphicsContext;
                                                                StickyItemsPlacement stickyItemsPlacement = stickyItemsPlacement$Companion$StickToTopPlacement$12;
                                                                LazyGridState lazyGridState8 = lazyGridState7;
                                                                int i57 = i56;
                                                                final LazyLayoutMeasureScope lazyLayoutMeasureScope4 = lazyLayoutMeasureScope3;
                                                                int i58 = scrollOffset;
                                                                Function3 function32 = new Function3() { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measureResult$1
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(3);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function3
                                                                    public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                                                        int iIntValue = ((Number) obj5).intValue();
                                                                        int iIntValue2 = ((Number) obj6).intValue();
                                                                        LazyLayoutMeasureScope lazyLayoutMeasureScope5 = lazyLayoutMeasureScope4;
                                                                        int iM834constrainWidthK40F9xA = ConstraintsKt.m834constrainWidthK40F9xA(iIntValue + i54, j2);
                                                                        int iM833constrainHeightK40F9xA = ConstraintsKt.m833constrainHeightK40F9xA(iIntValue2 + i49, j2);
                                                                        Map mapEmptyMap = MapsKt__MapsKt.emptyMap();
                                                                        return ((LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope5).subcomposeMeasureScope.layout$1(iM834constrainWidthK40F9xA, iM833constrainHeightK40F9xA, mapEmptyMap, (Function1) obj7);
                                                                    }
                                                                };
                                                                if (i24 < 0) {
                                                                    InlineClassHelperKt.throwIllegalArgumentException("negative beforeContentPadding");
                                                                }
                                                                if (i53 < 0) {
                                                                    InlineClassHelperKt.throwIllegalArgumentException("negative afterContentPadding");
                                                                }
                                                                LazyGridItemProvider lazyGridItemProvider5 = r42.itemProvider;
                                                                if (i23 > 0) {
                                                                    int iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(jM835offsetNN6EwU);
                                                                    int iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(jM835offsetNN6EwU);
                                                                    r3.onMeasured(0, iM825getMinWidthimpl, iM824getMinHeightimpl, new ArrayList(), ((LazyGridItemProviderImpl) lazyGridItemProvider5).keyIndexMap, r42, z22, zIsLookingAhead, length, z17, 0, 0, coroutineScope2, graphicsContext2);
                                                                    if (!zIsLookingAhead) {
                                                                        long jM168getMinSizeToFitDisappearingItemsYbymL2g = r3.m168getMinSizeToFitDisappearingItemsYbymL2g();
                                                                        IntSize.Companion.getClass();
                                                                        if (!IntSize.m863equalsimpl0(jM168getMinSizeToFitDisappearingItemsYbymL2g, 0L)) {
                                                                            iM825getMinWidthimpl = ConstraintsKt.m834constrainWidthK40F9xA((int) (jM168getMinSizeToFitDisappearingItemsYbymL2g >> 32), jM835offsetNN6EwU);
                                                                            iM824getMinHeightimpl = ConstraintsKt.m833constrainHeightK40F9xA((int) (jM168getMinSizeToFitDisappearingItemsYbymL2g & 4294967295L), jM835offsetNN6EwU);
                                                                        }
                                                                    }
                                                                    lazyGridMeasureResult = new LazyGridMeasureResult(null, 0, false, 0.0f, (MeasureResult) function32.invoke(Integer.valueOf(iM825getMinWidthimpl), Integer.valueOf(iM824getMinHeightimpl), new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridMeasureKt$measureLazyGrid$3
                                                                        @Override // kotlin.jvm.functions.Function1
                                                                        /* renamed from: invoke */
                                                                        public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj5) {
                                                                            return Unit.INSTANCE;
                                                                        }
                                                                    }), 0.0f, false, coroutineScope2, lazyLayoutMeasureScope4, length, function12, EmptyList.INSTANCE, -i24, i25 + i53, 0, z23, z22 ? Orientation.Vertical : Orientation.Horizontal, i53, iMo52roundToPx0680j_45);
                                                                    z16 = false;
                                                                } else {
                                                                    LazyGridMeasuredItemProvider lazyGridMeasuredItemProvider2 = r42;
                                                                    int i59 = i53;
                                                                    int iRound = Math.round(fFloatValue);
                                                                    int i60 = i58 - iRound;
                                                                    if (i57 == 0 && i60 < 0) {
                                                                        iRound += i60;
                                                                        i60 = 0;
                                                                    }
                                                                    ArrayDeque arrayDeque = new ArrayDeque();
                                                                    int i61 = -i24;
                                                                    int i62 = i61 + (iMo52roundToPx0680j_45 < 0 ? iMo52roundToPx0680j_45 : 0);
                                                                    int i63 = i60 + i62;
                                                                    while (i63 < 0 && i57 > 0) {
                                                                        int i64 = i61;
                                                                        int i65 = i57 - 1;
                                                                        int i66 = i59;
                                                                        LazyGridMeasuredLine andMeasure = r32.getAndMeasure(i65);
                                                                        arrayDeque.add(0, andMeasure);
                                                                        i63 += andMeasure.mainAxisSizeWithSpacings;
                                                                        i57 = i65;
                                                                        i61 = i64;
                                                                        i59 = i66;
                                                                        lazyGridState8 = lazyGridState8;
                                                                    }
                                                                    int i67 = i61;
                                                                    LazyGridState lazyGridState9 = lazyGridState8;
                                                                    int i68 = i59;
                                                                    int i69 = 0;
                                                                    if (i63 < i62) {
                                                                        iRound -= i62 - i63;
                                                                        i63 = i62;
                                                                    }
                                                                    int i70 = iRound;
                                                                    int i71 = i63 - i62;
                                                                    int i72 = i25 + i68;
                                                                    if (i72 < 0) {
                                                                        i26 = i72;
                                                                    } else {
                                                                        i69 = i72;
                                                                        i26 = i69;
                                                                    }
                                                                    int i73 = i71;
                                                                    int i74 = -i71;
                                                                    int i75 = i57;
                                                                    int i76 = 0;
                                                                    boolean z24 = false;
                                                                    while (i76 < arrayDeque.size) {
                                                                        if (i74 >= i69) {
                                                                            arrayDeque.removeAt(i76);
                                                                            z24 = true;
                                                                        } else {
                                                                            i75++;
                                                                            i74 += ((LazyGridMeasuredLine) arrayDeque.get(i76)).mainAxisSizeWithSpacings;
                                                                            i76++;
                                                                        }
                                                                    }
                                                                    boolean z25 = z24;
                                                                    int i77 = i75;
                                                                    while (i77 < i23 && (i74 < i69 || i74 <= 0 || arrayDeque.isEmpty())) {
                                                                        z12 = z25;
                                                                        LazyGridMeasuredLine andMeasure2 = r32.getAndMeasure(i77);
                                                                        int i78 = i69;
                                                                        LazyGridMeasuredItem[] lazyGridMeasuredItemArr2 = andMeasure2.items;
                                                                        int i79 = i77;
                                                                        if (lazyGridMeasuredItemArr2.length == 0) {
                                                                            break;
                                                                        }
                                                                        int i80 = andMeasure2.mainAxisSizeWithSpacings;
                                                                        i74 += i80;
                                                                        int i81 = i62;
                                                                        if (i74 > i62) {
                                                                            arrayDeque.addLast(andMeasure2);
                                                                            z25 = z12;
                                                                        } else {
                                                                            if (lazyGridMeasuredItemArr2.length == 0) {
                                                                                throw new NoSuchElementException("Array is empty.");
                                                                            }
                                                                            if (lazyGridMeasuredItemArr2[lazyGridMeasuredItemArr2.length - 1].index != i23 - 1) {
                                                                                i73 -= i80;
                                                                                i57 = i79 + 1;
                                                                                z25 = true;
                                                                            }
                                                                        }
                                                                        i77 = i79 + 1;
                                                                        i69 = i78;
                                                                        i62 = i81;
                                                                    }
                                                                    z12 = z25;
                                                                    int i82 = i25;
                                                                    if (i74 < i82) {
                                                                        int i83 = i82 - i74;
                                                                        int i84 = i74 + i83;
                                                                        int i85 = i73 - i83;
                                                                        while (i85 < i24 && i57 > 0) {
                                                                            int i86 = i57 - 1;
                                                                            int i87 = i83;
                                                                            LazyGridMeasuredLine andMeasure3 = r32.getAndMeasure(i86);
                                                                            arrayDeque.add(0, andMeasure3);
                                                                            i85 += andMeasure3.mainAxisSizeWithSpacings;
                                                                            i83 = i87;
                                                                            i84 = i84;
                                                                            i57 = i86;
                                                                        }
                                                                        int i88 = i84;
                                                                        i28 = i70 + i83;
                                                                        if (i85 < 0) {
                                                                            i28 += i85;
                                                                            i27 = i88 + i85;
                                                                            i29 = 0;
                                                                        } else {
                                                                            i29 = i85;
                                                                            i27 = i88;
                                                                        }
                                                                    } else {
                                                                        i27 = i74;
                                                                        i28 = i70;
                                                                        i29 = i73;
                                                                    }
                                                                    float f3 = (Integer.signum(Math.round(fFloatValue)) != Integer.signum(i28) || Math.abs(Math.round(fFloatValue)) < Math.abs(i28)) ? fFloatValue : i28;
                                                                    float f4 = fFloatValue - f3;
                                                                    float f5 = 0.0f;
                                                                    if (zIsLookingAhead && i28 > i70 && f4 <= 0.0f) {
                                                                        f5 = (i28 - i70) + f4;
                                                                    }
                                                                    if (i29 < 0) {
                                                                        InlineClassHelperKt.throwIllegalArgumentException("negative initial offset");
                                                                    }
                                                                    int i89 = -i29;
                                                                    LazyGridMeasuredLine lazyGridMeasuredLine3 = (LazyGridMeasuredLine) arrayDeque.first();
                                                                    LazyGridMeasuredItem[] lazyGridMeasuredItemArr3 = lazyGridMeasuredLine3.items;
                                                                    int i90 = i29;
                                                                    LazyGridMeasuredItem lazyGridMeasuredItem = lazyGridMeasuredItemArr3.length == 0 ? null : lazyGridMeasuredItemArr3[0];
                                                                    int i91 = lazyGridMeasuredItem != null ? lazyGridMeasuredItem.index : 0;
                                                                    LazyGridMeasuredLine lazyGridMeasuredLine4 = (LazyGridMeasuredLine) arrayDeque.lastOrNull();
                                                                    if (lazyGridMeasuredLine4 == null || (lazyGridMeasuredItemArr = lazyGridMeasuredLine4.items) == null) {
                                                                        lazyGridMeasuredLine = lazyGridMeasuredLine3;
                                                                    } else {
                                                                        lazyGridMeasuredLine = lazyGridMeasuredLine3;
                                                                        LazyGridMeasuredItem lazyGridMeasuredItem2 = lazyGridMeasuredItemArr.length == 0 ? null : lazyGridMeasuredItemArr[lazyGridMeasuredItemArr.length - 1];
                                                                        int i92 = lazyGridMeasuredItem2 != null ? lazyGridMeasuredItem2.index : 0;
                                                                        List list3 = listCalculateLazyLayoutPinnedIndices;
                                                                        size = list3.size();
                                                                        float f6 = f3;
                                                                        float f7 = f5;
                                                                        arrayList = null;
                                                                        i30 = 0;
                                                                        while (true) {
                                                                            lazyGridSpanLayoutProvider3 = r32.spanLayoutProvider;
                                                                            if (i30 < size) {
                                                                                break;
                                                                            }
                                                                            int i93 = size;
                                                                            int iIntValue = ((Number) listCalculateLazyLayoutPinnedIndices.get(i30)).intValue();
                                                                            if (iIntValue < 0 || iIntValue >= i91) {
                                                                                i48 = i91;
                                                                            } else {
                                                                                i48 = i91;
                                                                                int i94 = lazyGridSpanLayoutProvider3.slotsPerLine;
                                                                                int iSpanOf = lazyGridSpanLayoutProvider3.spanOf(iIntValue);
                                                                                LazyGridMeasuredItem lazyGridMeasuredItemM163getAndMeasurem8Kt_7k = lazyGridMeasuredItemProvider2.m163getAndMeasurem8Kt_7k(iIntValue, r32.m164childConstraintsJhjzzOo$foundation_release(0, iSpanOf), 0, iSpanOf, lazyGridMeasuredItemProvider2.defaultMainAxisSpacing);
                                                                                if (arrayList == null) {
                                                                                    arrayList = new ArrayList();
                                                                                }
                                                                                List list4 = arrayList;
                                                                                list4.add(lazyGridMeasuredItemM163getAndMeasurem8Kt_7k);
                                                                                arrayList = list4;
                                                                            }
                                                                            i30++;
                                                                            size = i93;
                                                                            i91 = i48;
                                                                        }
                                                                        int i95 = i91;
                                                                        if (arrayList == null) {
                                                                            arrayList = EmptyList.INSTANCE;
                                                                        }
                                                                        list = arrayList;
                                                                        if (zIsLookingAhead || lazyGridMeasureResult2 == null || lazyGridMeasureResult2.visibleItemsInfo.isEmpty()) {
                                                                            list2 = list;
                                                                        } else {
                                                                            List list5 = lazyGridMeasureResult2.visibleItemsInfo;
                                                                            list2 = list;
                                                                            for (int size5 = list5.size() - 1; -1 < size5; size5--) {
                                                                                if (((LazyGridMeasuredItem) ((LazyGridItemInfo) list5.get(size5))).index > i92 && (size5 == 0 || ((LazyGridMeasuredItem) ((LazyGridItemInfo) list5.get(size5 - 1))).index <= i92)) {
                                                                                    lazyGridItemInfo = (LazyGridItemInfo) list5.get(size5);
                                                                                    break;
                                                                                }
                                                                            }
                                                                            lazyGridItemInfo = null;
                                                                            LazyGridItemInfo lazyGridItemInfo2 = (LazyGridItemInfo) CollectionsKt___CollectionsKt.last(lazyGridMeasureResult2.visibleItemsInfo);
                                                                            LazyGridMeasuredLine lazyGridMeasuredLine5 = (LazyGridMeasuredLine) CollectionsKt___CollectionsKt.lastOrNull(arrayDeque);
                                                                            int i96 = lazyGridMeasuredLine5 != null ? lazyGridMeasuredLine5.index + 1 : 0;
                                                                            if (lazyGridItemInfo != null && (i47 = ((LazyGridMeasuredItem) lazyGridItemInfo).index) <= (iMin = Math.min(((LazyGridMeasuredItem) lazyGridItemInfo2).index, i23 - 1))) {
                                                                                int i97 = i47;
                                                                                arrayList2 = null;
                                                                                while (true) {
                                                                                    if (arrayList2 != null) {
                                                                                        i31 = i92;
                                                                                        int size6 = arrayList2.size();
                                                                                        lazyLayoutMeasureScope = lazyLayoutMeasureScope4;
                                                                                        int i98 = 0;
                                                                                        while (i98 < size6) {
                                                                                            int i99 = i98;
                                                                                            LazyGridMeasuredItem[] lazyGridMeasuredItemArr4 = ((LazyGridMeasuredLine) arrayList2.get(i98)).items;
                                                                                            int i100 = size6;
                                                                                            int length2 = lazyGridMeasuredItemArr4.length;
                                                                                            int i101 = 0;
                                                                                            while (i101 < length2) {
                                                                                                int i102 = i101;
                                                                                                if (lazyGridMeasuredItemArr4[i102].index == i97) {
                                                                                                    break;
                                                                                                }
                                                                                                i101 = i102 + 1;
                                                                                            }
                                                                                            i98 = i99 + 1;
                                                                                            size6 = i100;
                                                                                        }
                                                                                    } else {
                                                                                        lazyLayoutMeasureScope = lazyLayoutMeasureScope4;
                                                                                        i31 = i92;
                                                                                    }
                                                                                    if (arrayList2 == null) {
                                                                                        arrayList2 = new ArrayList();
                                                                                    }
                                                                                    LazyGridMeasuredLine andMeasure4 = r32.getAndMeasure(i96);
                                                                                    i96++;
                                                                                    arrayList2.add(andMeasure4);
                                                                                    if (i97 == iMin) {
                                                                                        break;
                                                                                    }
                                                                                    i97++;
                                                                                    i92 = i31;
                                                                                    lazyLayoutMeasureScope4 = lazyLayoutMeasureScope;
                                                                                }
                                                                            }
                                                                            if (arrayList2 == null) {
                                                                                arrayList2 = EmptyList.INSTANCE;
                                                                            }
                                                                            size2 = list3.size();
                                                                            i32 = 0;
                                                                            while (i32 < size2) {
                                                                                int iIntValue2 = ((Number) listCalculateLazyLayoutPinnedIndices.get(i32)).intValue();
                                                                                if (i31 + 1 > iIntValue2 || iIntValue2 >= i23) {
                                                                                    i45 = size2;
                                                                                    i46 = i32;
                                                                                } else {
                                                                                    if (zIsLookingAhead) {
                                                                                        int size7 = arrayList2.size();
                                                                                        int i103 = 0;
                                                                                        while (i103 < size7) {
                                                                                            i45 = size2;
                                                                                            LazyGridMeasuredItem[] lazyGridMeasuredItemArr5 = ((LazyGridMeasuredLine) arrayList2.get(i103)).items;
                                                                                            i46 = i32;
                                                                                            int length3 = lazyGridMeasuredItemArr5.length;
                                                                                            int i104 = 0;
                                                                                            while (i104 < length3) {
                                                                                                int i105 = i104;
                                                                                                if (lazyGridMeasuredItemArr5[i105].index != iIntValue2) {
                                                                                                    i104 = i105 + 1;
                                                                                                }
                                                                                            }
                                                                                            i103++;
                                                                                            size2 = i45;
                                                                                            i32 = i46;
                                                                                        }
                                                                                    }
                                                                                    i45 = size2;
                                                                                    i46 = i32;
                                                                                    int i106 = lazyGridSpanLayoutProvider3.slotsPerLine;
                                                                                    int iSpanOf2 = lazyGridSpanLayoutProvider3.spanOf(iIntValue2);
                                                                                    lazyGridMeasuredItemProvider = lazyGridMeasuredItemProvider2;
                                                                                    LazyGridMeasuredItem lazyGridMeasuredItemM163getAndMeasurem8Kt_7k2 = lazyGridMeasuredItemProvider.m163getAndMeasurem8Kt_7k(iIntValue2, r32.m164childConstraintsJhjzzOo$foundation_release(0, iSpanOf2), 0, iSpanOf2, lazyGridMeasuredItemProvider2.defaultMainAxisSpacing);
                                                                                    if (arrayList4 == null) {
                                                                                        arrayList4 = new ArrayList();
                                                                                    }
                                                                                    List list6 = arrayList4;
                                                                                    list6.add(lazyGridMeasuredItemM163getAndMeasurem8Kt_7k2);
                                                                                    arrayList4 = list6;
                                                                                    i32 = i46 + 1;
                                                                                    lazyGridMeasuredItemProvider2 = lazyGridMeasuredItemProvider;
                                                                                    size2 = i45;
                                                                                }
                                                                                lazyGridMeasuredItemProvider = lazyGridMeasuredItemProvider2;
                                                                                i32 = i46 + 1;
                                                                                lazyGridMeasuredItemProvider2 = lazyGridMeasuredItemProvider;
                                                                                size2 = i45;
                                                                            }
                                                                            final LazyGridMeasuredItemProvider lazyGridMeasuredItemProvider3 = lazyGridMeasuredItemProvider2;
                                                                            if (arrayList4 == null) {
                                                                                arrayList4 = EmptyList.INSTANCE;
                                                                            }
                                                                            List list7 = arrayList4;
                                                                            if (i24 > 0 || iMo52roundToPx0680j_45 < 0) {
                                                                                size3 = arrayDeque.getSize();
                                                                                int i107 = i90;
                                                                                i33 = 0;
                                                                                while (i33 < size3) {
                                                                                    int i108 = ((LazyGridMeasuredLine) arrayDeque.get(i33)).mainAxisSizeWithSpacings;
                                                                                    if (i107 == 0 || i108 > i107 || i33 == arrayDeque.getSize() - 1) {
                                                                                        break;
                                                                                    }
                                                                                    i107 -= i108;
                                                                                    i33++;
                                                                                    lazyGridMeasuredLine = (LazyGridMeasuredLine) arrayDeque.get(i33);
                                                                                }
                                                                                i90 = i107;
                                                                            }
                                                                            LazyGridMeasuredLine lazyGridMeasuredLine6 = lazyGridMeasuredLine;
                                                                            int iM823getMaxWidthimpl = !z22 ? Constraints.m823getMaxWidthimpl(jM835offsetNN6EwU) : ConstraintsKt.m834constrainWidthK40F9xA(i27, jM835offsetNN6EwU);
                                                                            int iM833constrainHeightK40F9xA = !z22 ? ConstraintsKt.m833constrainHeightK40F9xA(i27, jM835offsetNN6EwU) : Constraints.m822getMaxHeightimpl(jM835offsetNN6EwU);
                                                                            List listPlus = arrayDeque;
                                                                            if (!arrayList2.isEmpty()) {
                                                                                listPlus = CollectionsKt___CollectionsKt.plus((Iterable) arrayList2, (Collection) arrayDeque);
                                                                            }
                                                                            List list8 = listPlus;
                                                                            int i109 = !z22 ? iM833constrainHeightK40F9xA : iM823getMaxWidthimpl;
                                                                            z13 = i27 >= Math.min(i109, i82);
                                                                            if (z13 && i89 != 0) {
                                                                                InlineClassHelperKt.throwIllegalStateException("non-zero firstLineScrollOffset");
                                                                            }
                                                                            size4 = list8.size();
                                                                            i34 = 0;
                                                                            int length4 = 0;
                                                                            while (i34 < size4) {
                                                                                length4 += ((LazyGridMeasuredLine) list8.get(i34)).items.length;
                                                                                i34++;
                                                                                z13 = z13;
                                                                            }
                                                                            z14 = z13;
                                                                            ArrayList arrayList5 = new ArrayList(length4);
                                                                            if (z14) {
                                                                                i35 = i23;
                                                                                lazyGridMeasuredLine2 = lazyGridMeasuredLine6;
                                                                                i36 = i67;
                                                                                function3 = function32;
                                                                                i37 = i31;
                                                                                i38 = i95;
                                                                                arrayList3 = arrayList5;
                                                                                i39 = i27;
                                                                                lazyGridItemProvider2 = lazyGridItemProvider5;
                                                                                int size8 = list2.size() - 1;
                                                                                if (size8 >= 0) {
                                                                                    int i110 = i89;
                                                                                    while (true) {
                                                                                        int i111 = size8 - 1;
                                                                                        List list9 = list2;
                                                                                        LazyGridMeasuredItem lazyGridMeasuredItem3 = (LazyGridMeasuredItem) list9.get(size8);
                                                                                        i110 -= lazyGridMeasuredItem3.mainAxisSizeWithSpacings;
                                                                                        lazyGridMeasuredItem3.position(i110, 0, iM823getMaxWidthimpl, iM833constrainHeightK40F9xA);
                                                                                        arrayList3.add(lazyGridMeasuredItem3);
                                                                                        if (i111 < 0) {
                                                                                            break;
                                                                                        }
                                                                                        size8 = i111;
                                                                                        list2 = list9;
                                                                                    }
                                                                                }
                                                                                int size9 = list8.size();
                                                                                int i112 = i89;
                                                                                int i113 = 0;
                                                                                while (i113 < size9) {
                                                                                    LazyGridMeasuredLine lazyGridMeasuredLine7 = (LazyGridMeasuredLine) list8.get(i113);
                                                                                    LazyGridMeasuredItem[] lazyGridMeasuredItemArrPosition = lazyGridMeasuredLine7.position(i112, iM823getMaxWidthimpl, iM833constrainHeightK40F9xA);
                                                                                    int length5 = lazyGridMeasuredItemArrPosition.length;
                                                                                    int i114 = size9;
                                                                                    int i115 = 0;
                                                                                    while (i115 < length5) {
                                                                                        int i116 = i115;
                                                                                        arrayList3.add(lazyGridMeasuredItemArrPosition[i116]);
                                                                                        i115 = i116 + 1;
                                                                                    }
                                                                                    i112 += lazyGridMeasuredLine7.mainAxisSizeWithSpacings;
                                                                                    i113++;
                                                                                    size9 = i114;
                                                                                }
                                                                                int size10 = list7.size();
                                                                                int i117 = i112;
                                                                                for (int i118 = 0; i118 < size10; i118++) {
                                                                                    LazyGridMeasuredItem lazyGridMeasuredItem4 = (LazyGridMeasuredItem) list7.get(i118);
                                                                                    lazyGridMeasuredItem4.position(i117, 0, iM823getMaxWidthimpl, iM833constrainHeightK40F9xA);
                                                                                    arrayList3.add(lazyGridMeasuredItem4);
                                                                                    i117 += lazyGridMeasuredItem4.mainAxisSizeWithSpacings;
                                                                                }
                                                                                f = f6;
                                                                            } else {
                                                                                if (!list2.isEmpty() || !list7.isEmpty()) {
                                                                                    InlineClassHelperKt.throwIllegalArgumentException("no items");
                                                                                }
                                                                                int size11 = list8.size();
                                                                                int[] iArr = new int[size11];
                                                                                for (int i119 = 0; i119 < size11; i119++) {
                                                                                    iArr[i119] = ((LazyGridMeasuredLine) list8.get(!z23 ? i119 : (size11 - i119) - 1)).mainAxisSize;
                                                                                }
                                                                                int[] iArr2 = new int[size11];
                                                                                if (!z22) {
                                                                                    i42 = size11;
                                                                                    LazyLayoutMeasureScope lazyLayoutMeasureScope5 = lazyLayoutMeasureScope;
                                                                                    if (horizontal3 == null) {
                                                                                        InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("null horizontalArrangement");
                                                                                        throw new KotlinNothingValueException();
                                                                                    }
                                                                                    i35 = i23;
                                                                                    lazyGridMeasuredLine2 = lazyGridMeasuredLine6;
                                                                                    i36 = i67;
                                                                                    function3 = function32;
                                                                                    i37 = i31;
                                                                                    i38 = i95;
                                                                                    arrayList3 = arrayList5;
                                                                                    i39 = i27;
                                                                                    lazyGridItemProvider2 = lazyGridItemProvider5;
                                                                                    lazyLayoutMeasureScope2 = lazyLayoutMeasureScope5;
                                                                                    horizontal3.arrange(lazyLayoutMeasureScope2, i109, iArr, LayoutDirection.Ltr, iArr2);
                                                                                } else {
                                                                                    if (vertical3 == null) {
                                                                                        InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("null verticalArrangement");
                                                                                        throw new KotlinNothingValueException();
                                                                                    }
                                                                                    i42 = size11;
                                                                                    LazyLayoutMeasureScope lazyLayoutMeasureScope6 = lazyLayoutMeasureScope;
                                                                                    vertical3.arrange(lazyLayoutMeasureScope6, i109, iArr, iArr2);
                                                                                    i35 = i23;
                                                                                    lazyGridMeasuredLine2 = lazyGridMeasuredLine6;
                                                                                    i36 = i67;
                                                                                    function3 = function32;
                                                                                    i37 = i31;
                                                                                    i38 = i95;
                                                                                    arrayList3 = arrayList5;
                                                                                    lazyLayoutMeasureScope2 = lazyLayoutMeasureScope6;
                                                                                    i39 = i27;
                                                                                    lazyGridItemProvider2 = lazyGridItemProvider5;
                                                                                }
                                                                                IntRange indices = ArraysKt___ArraysKt.getIndices(iArr2);
                                                                                IntRange intRangeReversed = indices;
                                                                                if (z23) {
                                                                                    intRangeReversed = RangesKt___RangesKt.reversed(indices);
                                                                                }
                                                                                int i120 = intRangeReversed.first;
                                                                                int i121 = intRangeReversed.last;
                                                                                int i122 = intRangeReversed.step;
                                                                                if ((i122 > 0 && i120 <= i121) || (i122 < 0 && i121 <= i120)) {
                                                                                    while (true) {
                                                                                        int i123 = iArr2[i120];
                                                                                        if (z23) {
                                                                                            i43 = i122;
                                                                                            i44 = (i42 - i120) - 1;
                                                                                        } else {
                                                                                            i43 = i122;
                                                                                            i44 = i120;
                                                                                        }
                                                                                        LazyGridMeasuredLine lazyGridMeasuredLine8 = (LazyGridMeasuredLine) list8.get(i44);
                                                                                        if (z23) {
                                                                                            lazyLayoutMeasureScope = lazyLayoutMeasureScope2;
                                                                                            i123 = (i109 - i123) - lazyGridMeasuredLine8.mainAxisSize;
                                                                                        } else {
                                                                                            lazyLayoutMeasureScope = lazyLayoutMeasureScope2;
                                                                                        }
                                                                                        LazyGridMeasuredItem[] lazyGridMeasuredItemArrPosition2 = lazyGridMeasuredLine8.position(i123, iM823getMaxWidthimpl, iM833constrainHeightK40F9xA);
                                                                                        int length6 = lazyGridMeasuredItemArrPosition2.length;
                                                                                        int i124 = 0;
                                                                                        while (i124 < length6) {
                                                                                            LazyGridMeasuredItem[] lazyGridMeasuredItemArr6 = lazyGridMeasuredItemArrPosition2;
                                                                                            arrayList3.add(lazyGridMeasuredItemArr6[i124]);
                                                                                            i124++;
                                                                                            lazyGridMeasuredItemArrPosition2 = lazyGridMeasuredItemArr6;
                                                                                        }
                                                                                        if (i120 == i121) {
                                                                                            break;
                                                                                        }
                                                                                        i120 += i43;
                                                                                        i122 = i43;
                                                                                        lazyLayoutMeasureScope2 = lazyLayoutMeasureScope;
                                                                                    }
                                                                                } else {
                                                                                    lazyLayoutMeasureScope = lazyLayoutMeasureScope2;
                                                                                }
                                                                                f = f6;
                                                                            }
                                                                            int i125 = i90;
                                                                            final ArrayList arrayList6 = arrayList3;
                                                                            r3.onMeasured((int) f, iM823getMaxWidthimpl, iM833constrainHeightK40F9xA, arrayList6, ((LazyGridItemProviderImpl) lazyGridItemProvider2).keyIndexMap, lazyGridMeasuredItemProvider3, z22, zIsLookingAhead, length, z17, i125, i39, coroutineScope2, graphicsContext2);
                                                                            int iM834constrainWidthK40F9xA = iM823getMaxWidthimpl;
                                                                            int i126 = i39;
                                                                            if (zIsLookingAhead) {
                                                                                long jM168getMinSizeToFitDisappearingItemsYbymL2g2 = r3.m168getMinSizeToFitDisappearingItemsYbymL2g();
                                                                                IntSize.Companion.getClass();
                                                                                z15 = z22;
                                                                                i40 = i126;
                                                                                if (!IntSize.m863equalsimpl0(jM168getMinSizeToFitDisappearingItemsYbymL2g2, 0L)) {
                                                                                    int i127 = z15 ? iM833constrainHeightK40F9xA : iM834constrainWidthK40F9xA;
                                                                                    f2 = f;
                                                                                    iM834constrainWidthK40F9xA = ConstraintsKt.m834constrainWidthK40F9xA(Math.max(iM834constrainWidthK40F9xA, (int) (jM168getMinSizeToFitDisappearingItemsYbymL2g2 >> 32)), jM835offsetNN6EwU);
                                                                                    int iM833constrainHeightK40F9xA2 = ConstraintsKt.m833constrainHeightK40F9xA(Math.max(iM833constrainHeightK40F9xA, (int) (jM168getMinSizeToFitDisappearingItemsYbymL2g2 & 4294967295L)), jM835offsetNN6EwU);
                                                                                    int i128 = z15 ? iM833constrainHeightK40F9xA2 : iM834constrainWidthK40F9xA;
                                                                                    if (i128 != i127) {
                                                                                        int size12 = arrayList6.size();
                                                                                        for (int i129 = 0; i129 < size12; i129++) {
                                                                                            LazyGridMeasuredItem lazyGridMeasuredItem5 = (LazyGridMeasuredItem) arrayList6.get(i129);
                                                                                            lazyGridMeasuredItem5.mainAxisLayoutSize = i128;
                                                                                            lazyGridMeasuredItem5.maxMainAxisOffset = lazyGridMeasuredItem5.afterContentPadding + i128;
                                                                                        }
                                                                                    }
                                                                                    i41 = iM833constrainHeightK40F9xA2;
                                                                                }
                                                                                int i130 = iM834constrainWidthK40F9xA;
                                                                                ((LazyGridItemProviderImpl) lazyGridItemProvider2).intervalContent.getClass();
                                                                                final List listApplyStickyItems = LazyLayoutStickyItemsKt.applyStickyItems(stickyItemsPlacement, arrayList6, IntListKt.EmptyIntList, i24, i130, i41, new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridMeasureKt$measureLazyGrid$stickingItems$1
                                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                    {
                                                                                        super(1);
                                                                                    }

                                                                                    @Override // kotlin.jvm.functions.Function1
                                                                                    /* renamed from: invoke */
                                                                                    public final Object mo781invoke(Object obj5) {
                                                                                        int iIntValue3 = ((Number) obj5).intValue();
                                                                                        LazyGridSpanLayoutProvider lazyGridSpanLayoutProvider5 = r32.spanLayoutProvider;
                                                                                        int i131 = lazyGridSpanLayoutProvider5.slotsPerLine;
                                                                                        int iSpanOf3 = lazyGridSpanLayoutProvider5.spanOf(iIntValue3);
                                                                                        long jM164childConstraintsJhjzzOo$foundation_release = r32.m164childConstraintsJhjzzOo$foundation_release(0, iSpanOf3);
                                                                                        LazyGridMeasuredItemProvider lazyGridMeasuredItemProvider4 = lazyGridMeasuredItemProvider3;
                                                                                        return lazyGridMeasuredItemProvider4.m163getAndMeasurem8Kt_7k(iIntValue3, jM164childConstraintsJhjzzOo$foundation_release, 0, iSpanOf3, lazyGridMeasuredItemProvider4.defaultMainAxisSpacing);
                                                                                    }
                                                                                });
                                                                                int i131 = i37;
                                                                                boolean z26 = i131 != i35 + (-1) || i40 > i82;
                                                                                Integer numValueOf = Integer.valueOf(i130);
                                                                                Integer numValueOf2 = Integer.valueOf(i41);
                                                                                final MutableState mutableState = lazyGridState9.placementScopeInvalidator;
                                                                                MeasureResult measureResult = (MeasureResult) function3.invoke(numValueOf, numValueOf2, new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridMeasureKt$measureLazyGrid$6
                                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                    {
                                                                                        super(1);
                                                                                    }

                                                                                    @Override // kotlin.jvm.functions.Function1
                                                                                    /* renamed from: invoke */
                                                                                    public final Object mo781invoke(Object obj5) {
                                                                                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj5;
                                                                                        final List<LazyGridMeasuredItem> list10 = arrayList6;
                                                                                        final List<LazyGridMeasuredItem> list11 = listApplyStickyItems;
                                                                                        final boolean z27 = zIsLookingAhead;
                                                                                        Function1 function13 = new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridMeasureKt$measureLazyGrid$6.1
                                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                            {
                                                                                                super(1);
                                                                                            }

                                                                                            @Override // kotlin.jvm.functions.Function1
                                                                                            /* renamed from: invoke */
                                                                                            public final Object mo781invoke(Object obj6) {
                                                                                                Placeable.PlacementScope placementScope2 = (Placeable.PlacementScope) obj6;
                                                                                                List<LazyGridMeasuredItem> list12 = list10;
                                                                                                boolean z28 = z27;
                                                                                                int size13 = list12.size();
                                                                                                for (int i132 = 0; i132 < size13; i132++) {
                                                                                                    list12.get(i132).place(placementScope2, z28);
                                                                                                }
                                                                                                List<LazyGridMeasuredItem> list13 = list11;
                                                                                                boolean z29 = z27;
                                                                                                int size14 = list13.size();
                                                                                                for (int i133 = 0; i133 < size14; i133++) {
                                                                                                    list13.get(i133).place(placementScope2, z29);
                                                                                                }
                                                                                                return Unit.INSTANCE;
                                                                                            }
                                                                                        };
                                                                                        placementScope.motionFrameOfReferencePlacement = true;
                                                                                        function13.mo781invoke(placementScope);
                                                                                        placementScope.motionFrameOfReferencePlacement = false;
                                                                                        mutableState.getValue();
                                                                                        return Unit.INSTANCE;
                                                                                    }
                                                                                });
                                                                                z16 = false;
                                                                                lazyGridMeasureResult = new LazyGridMeasureResult(lazyGridMeasuredLine2, i125, z26, f2, measureResult, f7, z12, coroutineScope2, lazyLayoutMeasureScope, length, function12, LazyLayoutMeasuredItemKt.updatedVisibleItems(i38, i131, arrayList6, listApplyStickyItems), i36, i26, i35, z23, !z15 ? Orientation.Vertical : Orientation.Horizontal, i68, iMo52roundToPx0680j_45);
                                                                            } else {
                                                                                z15 = z22;
                                                                                i40 = i126;
                                                                            }
                                                                            f2 = f;
                                                                            i41 = iM833constrainHeightK40F9xA;
                                                                            int i1302 = iM834constrainWidthK40F9xA;
                                                                            ((LazyGridItemProviderImpl) lazyGridItemProvider2).intervalContent.getClass();
                                                                            final List<LazyGridMeasuredItem> listApplyStickyItems2 = LazyLayoutStickyItemsKt.applyStickyItems(stickyItemsPlacement, arrayList6, IntListKt.EmptyIntList, i24, i1302, i41, new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridMeasureKt$measureLazyGrid$stickingItems$1
                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                {
                                                                                    super(1);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                /* renamed from: invoke */
                                                                                public final Object mo781invoke(Object obj5) {
                                                                                    int iIntValue3 = ((Number) obj5).intValue();
                                                                                    LazyGridSpanLayoutProvider lazyGridSpanLayoutProvider5 = r32.spanLayoutProvider;
                                                                                    int i1312 = lazyGridSpanLayoutProvider5.slotsPerLine;
                                                                                    int iSpanOf3 = lazyGridSpanLayoutProvider5.spanOf(iIntValue3);
                                                                                    long jM164childConstraintsJhjzzOo$foundation_release = r32.m164childConstraintsJhjzzOo$foundation_release(0, iSpanOf3);
                                                                                    LazyGridMeasuredItemProvider lazyGridMeasuredItemProvider4 = lazyGridMeasuredItemProvider3;
                                                                                    return lazyGridMeasuredItemProvider4.m163getAndMeasurem8Kt_7k(iIntValue3, jM164childConstraintsJhjzzOo$foundation_release, 0, iSpanOf3, lazyGridMeasuredItemProvider4.defaultMainAxisSpacing);
                                                                                }
                                                                            });
                                                                            int i1312 = i37;
                                                                            if (i1312 != i35 + (-1)) {
                                                                                Integer numValueOf3 = Integer.valueOf(i1302);
                                                                                Integer numValueOf22 = Integer.valueOf(i41);
                                                                                final MutableState<Unit> mutableState2 = lazyGridState9.placementScopeInvalidator;
                                                                                MeasureResult measureResult2 = (MeasureResult) function3.invoke(numValueOf3, numValueOf22, new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridMeasureKt$measureLazyGrid$6
                                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                    {
                                                                                        super(1);
                                                                                    }

                                                                                    @Override // kotlin.jvm.functions.Function1
                                                                                    /* renamed from: invoke */
                                                                                    public final Object mo781invoke(Object obj5) {
                                                                                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj5;
                                                                                        final List<LazyGridMeasuredItem> list10 = arrayList6;
                                                                                        final List<LazyGridMeasuredItem> list11 = listApplyStickyItems2;
                                                                                        final boolean z27 = zIsLookingAhead;
                                                                                        Function1 function13 = new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridMeasureKt$measureLazyGrid$6.1
                                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                            {
                                                                                                super(1);
                                                                                            }

                                                                                            @Override // kotlin.jvm.functions.Function1
                                                                                            /* renamed from: invoke */
                                                                                            public final Object mo781invoke(Object obj6) {
                                                                                                Placeable.PlacementScope placementScope2 = (Placeable.PlacementScope) obj6;
                                                                                                List<LazyGridMeasuredItem> list12 = list10;
                                                                                                boolean z28 = z27;
                                                                                                int size13 = list12.size();
                                                                                                for (int i132 = 0; i132 < size13; i132++) {
                                                                                                    list12.get(i132).place(placementScope2, z28);
                                                                                                }
                                                                                                List<LazyGridMeasuredItem> list13 = list11;
                                                                                                boolean z29 = z27;
                                                                                                int size14 = list13.size();
                                                                                                for (int i133 = 0; i133 < size14; i133++) {
                                                                                                    list13.get(i133).place(placementScope2, z29);
                                                                                                }
                                                                                                return Unit.INSTANCE;
                                                                                            }
                                                                                        };
                                                                                        placementScope.motionFrameOfReferencePlacement = true;
                                                                                        function13.mo781invoke(placementScope);
                                                                                        placementScope.motionFrameOfReferencePlacement = false;
                                                                                        mutableState2.getValue();
                                                                                        return Unit.INSTANCE;
                                                                                    }
                                                                                });
                                                                                z16 = false;
                                                                                lazyGridMeasureResult = new LazyGridMeasureResult(lazyGridMeasuredLine2, i125, z26, f2, measureResult2, f7, z12, coroutineScope2, lazyLayoutMeasureScope, length, function12, LazyLayoutMeasuredItemKt.updatedVisibleItems(i38, i1312, arrayList6, listApplyStickyItems2), i36, i26, i35, z23, !z15 ? Orientation.Vertical : Orientation.Horizontal, i68, iMo52roundToPx0680j_45);
                                                                            }
                                                                        }
                                                                        lazyLayoutMeasureScope = lazyLayoutMeasureScope4;
                                                                        i31 = i92;
                                                                        arrayList2 = null;
                                                                        if (arrayList2 == null) {
                                                                        }
                                                                        size2 = list3.size();
                                                                        i32 = 0;
                                                                        while (i32 < size2) {
                                                                        }
                                                                        final LazyGridMeasuredItemProvider lazyGridMeasuredItemProvider32 = lazyGridMeasuredItemProvider2;
                                                                        if (arrayList4 == null) {
                                                                        }
                                                                        List list72 = arrayList4;
                                                                        if (i24 > 0) {
                                                                            size3 = arrayDeque.getSize();
                                                                            int i1072 = i90;
                                                                            i33 = 0;
                                                                            while (i33 < size3) {
                                                                            }
                                                                            i90 = i1072;
                                                                            LazyGridMeasuredLine lazyGridMeasuredLine62 = lazyGridMeasuredLine;
                                                                            int iM823getMaxWidthimpl2 = !z22 ? Constraints.m823getMaxWidthimpl(jM835offsetNN6EwU) : ConstraintsKt.m834constrainWidthK40F9xA(i27, jM835offsetNN6EwU);
                                                                            int iM833constrainHeightK40F9xA3 = !z22 ? ConstraintsKt.m833constrainHeightK40F9xA(i27, jM835offsetNN6EwU) : Constraints.m822getMaxHeightimpl(jM835offsetNN6EwU);
                                                                            List listPlus2 = arrayDeque;
                                                                            if (!arrayList2.isEmpty()) {
                                                                            }
                                                                            List list82 = listPlus2;
                                                                            if (!z22) {
                                                                            }
                                                                            if (i27 >= Math.min(i109, i82)) {
                                                                            }
                                                                            if (z13) {
                                                                                InlineClassHelperKt.throwIllegalStateException("non-zero firstLineScrollOffset");
                                                                            }
                                                                            size4 = list82.size();
                                                                            i34 = 0;
                                                                            int length42 = 0;
                                                                            while (i34 < size4) {
                                                                            }
                                                                            z14 = z13;
                                                                            ArrayList arrayList52 = new ArrayList(length42);
                                                                            if (z14) {
                                                                            }
                                                                            int i1252 = i90;
                                                                            final List<LazyGridMeasuredItem> arrayList62 = arrayList3;
                                                                            r3.onMeasured((int) f, iM823getMaxWidthimpl2, iM833constrainHeightK40F9xA3, arrayList62, ((LazyGridItemProviderImpl) lazyGridItemProvider2).keyIndexMap, lazyGridMeasuredItemProvider32, z22, zIsLookingAhead, length, z17, i1252, i39, coroutineScope2, graphicsContext2);
                                                                            int iM834constrainWidthK40F9xA2 = iM823getMaxWidthimpl2;
                                                                            int i1262 = i39;
                                                                            if (zIsLookingAhead) {
                                                                            }
                                                                            f2 = f;
                                                                            i41 = iM833constrainHeightK40F9xA3;
                                                                            int i13022 = iM834constrainWidthK40F9xA2;
                                                                            ((LazyGridItemProviderImpl) lazyGridItemProvider2).intervalContent.getClass();
                                                                            final List<LazyGridMeasuredItem> listApplyStickyItems22 = LazyLayoutStickyItemsKt.applyStickyItems(stickyItemsPlacement, arrayList62, IntListKt.EmptyIntList, i24, i13022, i41, new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridMeasureKt$measureLazyGrid$stickingItems$1
                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                {
                                                                                    super(1);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                /* renamed from: invoke */
                                                                                public final Object mo781invoke(Object obj5) {
                                                                                    int iIntValue3 = ((Number) obj5).intValue();
                                                                                    LazyGridSpanLayoutProvider lazyGridSpanLayoutProvider5 = r32.spanLayoutProvider;
                                                                                    int i13122 = lazyGridSpanLayoutProvider5.slotsPerLine;
                                                                                    int iSpanOf3 = lazyGridSpanLayoutProvider5.spanOf(iIntValue3);
                                                                                    long jM164childConstraintsJhjzzOo$foundation_release = r32.m164childConstraintsJhjzzOo$foundation_release(0, iSpanOf3);
                                                                                    LazyGridMeasuredItemProvider lazyGridMeasuredItemProvider4 = lazyGridMeasuredItemProvider32;
                                                                                    return lazyGridMeasuredItemProvider4.m163getAndMeasurem8Kt_7k(iIntValue3, jM164childConstraintsJhjzzOo$foundation_release, 0, iSpanOf3, lazyGridMeasuredItemProvider4.defaultMainAxisSpacing);
                                                                                }
                                                                            });
                                                                            int i13122 = i37;
                                                                            if (i13122 != i35 + (-1)) {
                                                                            }
                                                                        }
                                                                    }
                                                                    List list32 = listCalculateLazyLayoutPinnedIndices;
                                                                    size = list32.size();
                                                                    float f62 = f3;
                                                                    float f72 = f5;
                                                                    arrayList = null;
                                                                    i30 = 0;
                                                                    while (true) {
                                                                        lazyGridSpanLayoutProvider3 = r32.spanLayoutProvider;
                                                                        if (i30 < size) {
                                                                        }
                                                                        i30++;
                                                                        size = i93;
                                                                        i91 = i48;
                                                                    }
                                                                    int i952 = i91;
                                                                    if (arrayList == null) {
                                                                    }
                                                                    list = arrayList;
                                                                    if (zIsLookingAhead) {
                                                                        list2 = list;
                                                                        lazyLayoutMeasureScope = lazyLayoutMeasureScope4;
                                                                        i31 = i92;
                                                                        arrayList2 = null;
                                                                        if (arrayList2 == null) {
                                                                        }
                                                                        size2 = list32.size();
                                                                        i32 = 0;
                                                                        while (i32 < size2) {
                                                                        }
                                                                        final LazyGridMeasuredItemProvider lazyGridMeasuredItemProvider322 = lazyGridMeasuredItemProvider2;
                                                                        if (arrayList4 == null) {
                                                                        }
                                                                        List list722 = arrayList4;
                                                                        if (i24 > 0) {
                                                                        }
                                                                    }
                                                                }
                                                                lazyGridState.applyMeasureResult$foundation_release(lazyGridMeasureResult, subcomposeMeasureScope.isLookingAhead(), z16);
                                                                return lazyGridMeasureResult;
                                                            }
                                                            i21 = i50;
                                                            lazyGridScrollPosition = lazyGridState5.scrollPosition;
                                                            index = lazyGridScrollPosition.getIndex();
                                                            iFindIndexByKey = LazyLayoutItemProviderKt.findIndexByKey(lazyGridItemProvider4, index, lazyGridScrollPosition.lastKnownFirstItemKey);
                                                            if (index == iFindIndexByKey) {
                                                            }
                                                            if (iFindIndexByKey >= i23) {
                                                                lineIndexOfItem = lazyGridSpanLayoutProvider2.getLineIndexOfItem(iFindIndexByKey);
                                                                scrollOffset = lazyGridScrollPosition.getScrollOffset();
                                                            }
                                                            int i562 = lineIndexOfItem;
                                                            Unit unit2 = Unit.INSTANCE;
                                                            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                                                            LazyGridState lazyGridState62 = lazyGridState;
                                                            List listCalculateLazyLayoutPinnedIndices2 = LazyLayoutBeyondBoundsStateKt.calculateLazyLayoutPinnedIndices(lazyGridItemProvider4, lazyGridState62.pinnedItems, lazyGridState62.beyondBoundsInfo);
                                                            float fFloatValue2 = (subcomposeMeasureScope.isLookingAhead() && z17) ? ((Number) ((SnapshotMutableStateImpl) lazyGridState._lazyLayoutScrollDeltaBetweenPasses._scrollDeltaBetweenPasses.value$delegate).getValue()).floatValue() : lazyGridState.scrollToBeConsumed;
                                                            ?? r33 = lazyGridState.itemAnimator;
                                                            final boolean zIsLookingAhead2 = subcomposeMeasureScope.isLookingAhead();
                                                            LazyGridState lazyGridState72 = lazyGridState;
                                                            LazyGridMeasureResult lazyGridMeasureResult22 = lazyGridState72.approachLayoutInfo;
                                                            boolean z222 = z2;
                                                            Arrangement.Vertical vertical32 = vertical;
                                                            Arrangement.Horizontal horizontal32 = horizontal;
                                                            boolean z232 = z7;
                                                            CoroutineScope coroutineScope22 = coroutineScope;
                                                            GraphicsContext graphicsContext22 = graphicsContext;
                                                            StickyItemsPlacement stickyItemsPlacement2 = stickyItemsPlacement$Companion$StickToTopPlacement$12;
                                                            LazyGridState lazyGridState82 = lazyGridState72;
                                                            int i572 = i562;
                                                            final LazyLayoutMeasureScope lazyLayoutMeasureScope42 = lazyLayoutMeasureScope3;
                                                            int i582 = scrollOffset;
                                                            Function3 function322 = new Function3() { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measureResult$1
                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                {
                                                                    super(3);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function3
                                                                public final Object invoke(Object obj5, Object obj6, Object obj7) {
                                                                    int iIntValue3 = ((Number) obj5).intValue();
                                                                    int iIntValue22 = ((Number) obj6).intValue();
                                                                    LazyLayoutMeasureScope lazyLayoutMeasureScope52 = lazyLayoutMeasureScope42;
                                                                    int iM834constrainWidthK40F9xA3 = ConstraintsKt.m834constrainWidthK40F9xA(iIntValue3 + i54, j2);
                                                                    int iM833constrainHeightK40F9xA4 = ConstraintsKt.m833constrainHeightK40F9xA(iIntValue22 + i49, j2);
                                                                    Map mapEmptyMap = MapsKt__MapsKt.emptyMap();
                                                                    return ((LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope52).subcomposeMeasureScope.layout$1(iM834constrainWidthK40F9xA3, iM833constrainHeightK40F9xA4, mapEmptyMap, (Function1) obj7);
                                                                }
                                                            };
                                                            if (i24 < 0) {
                                                            }
                                                            if (i53 < 0) {
                                                            }
                                                            LazyGridItemProvider lazyGridItemProvider52 = r42.itemProvider;
                                                            if (i23 > 0) {
                                                            }
                                                            lazyGridState.applyMeasureResult$foundation_release(lazyGridMeasureResult, subcomposeMeasureScope.isLookingAhead(), z16);
                                                            return lazyGridMeasureResult;
                                                        } catch (Throwable th) {
                                                            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                                                            throw th;
                                                        }
                                                        gridSlotCache.cachedConstraints = jM835offsetNN6EwU;
                                                        gridSlotCache.cachedDensity = subcomposeMeasureScope.getDensity();
                                                        LazyGridSlots lazyGridSlots3 = (LazyGridSlots) gridSlotCache.calculation.invoke(lazyLayoutMeasureScopeImpl5, Constraints.m815boximpl(jM835offsetNN6EwU));
                                                        gridSlotCache.cachedSizes = lazyGridSlots3;
                                                        lazyGridSlots = lazyGridSlots3;
                                                        length = lazyGridSlots.sizes.length;
                                                        if (length == lazyGridSpanLayoutProvider4.slotsPerLine) {
                                                        }
                                                        if (z2) {
                                                        }
                                                        final int iMo52roundToPx0680j_452 = subcomposeMeasureScope.mo52roundToPx0680j_4(fMo95getSpacingD9Ej5fM);
                                                        i23 = lazyGridItemProviderImpl.intervalContent.getIntervals$1().size;
                                                        if (!z2) {
                                                        }
                                                        z11 = z7;
                                                        if (z11) {
                                                            lazyGridSpanLayoutProvider = lazyGridSpanLayoutProvider4;
                                                            j = (iMo52roundToPx0680j_43 & 4294967295L) | (iMo52roundToPx0680j_4 << 32);
                                                            IntOffset.Companion companion32 = IntOffset.Companion;
                                                        }
                                                        final long j32 = j;
                                                        final LazyGridState lazyGridState42 = lazyGridState;
                                                        final boolean z202 = z2;
                                                        final LazyGridItemProvider lazyGridItemProvider42 = lazyGridItemProvider;
                                                        i24 = i52;
                                                        final int i542 = i22;
                                                        int i552 = iM822getMaxHeightimpl;
                                                        List arrayList42 = null;
                                                        final LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredItemProvider$1 r422 = new LazyGridMeasuredItemProvider(lazyGridItemProvider42, lazyLayoutMeasureScope3, iMo52roundToPx0680j_452, lazyGridState42, z202, z11, i24, i53, j32) { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredItemProvider$1
                                                            public final /* synthetic */ int $afterContentPadding;
                                                            public final /* synthetic */ int $beforeContentPadding;
                                                            public final /* synthetic */ boolean $isVertical;
                                                            public final /* synthetic */ boolean $reverseLayout;
                                                            public final /* synthetic */ LazyGridState $state;
                                                            public final /* synthetic */ LazyLayoutMeasureScope $this_null;
                                                            public final /* synthetic */ long $visualItemOffset;

                                                            {
                                                                this.$this_null = lazyLayoutMeasureScope3;
                                                                this.$state = lazyGridState42;
                                                                this.$isVertical = z202;
                                                                this.$reverseLayout = z11;
                                                                this.$beforeContentPadding = i24;
                                                                this.$afterContentPadding = i53;
                                                                this.$visualItemOffset = j32;
                                                            }

                                                            @Override // androidx.compose.foundation.lazy.grid.LazyGridMeasuredItemProvider
                                                            /* renamed from: createItem-O3s9Psw, reason: not valid java name */
                                                            public final LazyGridMeasuredItem mo160createItemO3s9Psw(int i563, Object obj5, Object obj6, int i573, int i583, List list33, long j4, int i592, int i602) {
                                                                LayoutDirection layoutDirection = ((LazyLayoutMeasureScopeImpl) this.$this_null).subcomposeMeasureScope.getLayoutDirection();
                                                                LazyLayoutItemAnimator lazyLayoutItemAnimator = this.$state.itemAnimator;
                                                                return new LazyGridMeasuredItem(i563, obj5, this.$isVertical, i573, i583, this.$reverseLayout, layoutDirection, this.$beforeContentPadding, this.$afterContentPadding, list33, this.$visualItemOffset, obj6, lazyLayoutItemAnimator, j4, i592, i602, null);
                                                            }
                                                        };
                                                        final boolean z212 = z2;
                                                        final LazyGridMeasuredLineProvider r322 = new LazyGridMeasuredLineProvider(z212, lazyGridSlots2, i23, iMo52roundToPx0680j_452, r422, lazyGridSpanLayoutProvider) { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1
                                                            public final /* synthetic */ boolean $isVertical;
                                                            public final /* synthetic */ LazyGridSlots $resolvedSlots;

                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(z212, lazyGridSlots2, i23, iMo52roundToPx0680j_452, r422, lazyGridSpanLayoutProvider);
                                                                this.$isVertical = z212;
                                                                this.$resolvedSlots = lazyGridSlots2;
                                                            }

                                                            @Override // androidx.compose.foundation.lazy.grid.LazyGridMeasuredLineProvider
                                                            public final LazyGridMeasuredLine createLine(int i563, LazyGridMeasuredItem[] lazyGridMeasuredItemArr22, List list33, int i573) {
                                                                return new LazyGridMeasuredLine(i563, lazyGridMeasuredItemArr22, this.$resolvedSlots, list33, this.$isVertical, i573);
                                                            }
                                                        };
                                                        lazyGridSpanLayoutProvider2 = lazyGridSpanLayoutProvider;
                                                        Function1 function122 = new Function1() { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt$rememberLazyGridMeasurePolicy$1$1$prefetchInfoRetriever$1
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(1);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj5) {
                                                                LazyGridSpanLayoutProvider.LineConfiguration lineConfiguration = lazyGridSpanLayoutProvider2.getLineConfiguration(((Number) obj5).intValue());
                                                                ArrayList arrayList53 = new ArrayList(lineConfiguration.spans.size());
                                                                List list33 = lineConfiguration.spans;
                                                                LazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1 lazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1 = r322;
                                                                int size52 = list33.size();
                                                                int i563 = lineConfiguration.firstItemIndex;
                                                                int i573 = 0;
                                                                for (int i583 = 0; i583 < size52; i583++) {
                                                                    int i592 = (int) ((GridItemSpan) list33.get(i583)).packedValue;
                                                                    arrayList53.add(new Pair(Integer.valueOf(i563), Constraints.m815boximpl(lazyGridKt$rememberLazyGridMeasurePolicy$1$1$measuredLineProvider$1.m164childConstraintsJhjzzOo$foundation_release(i573, i592))));
                                                                    i563++;
                                                                    i573 += i592;
                                                                }
                                                                return arrayList53;
                                                            }
                                                        };
                                                        Snapshot.Companion companion52 = Snapshot.Companion;
                                                        LazyGridState lazyGridState52 = lazyGridState;
                                                        companion52.getClass();
                                                        currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                                                        if (currentThreadSnapshot == null) {
                                                        }
                                                        snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                                                    }
                                                };
                                                lazyGridState2 = lazyGridState;
                                                paddingValues3 = paddingValues4;
                                                kProperty0 = kProperty02;
                                                composerImpl2.updateRememberedValue(obj2);
                                                objRememberedValue4 = obj2;
                                            } else {
                                                modifier4 = modifier5;
                                                kProperty0 = kProperty02;
                                                paddingValues3 = paddingValuesM120PaddingValues0680j_4;
                                                z7 = z9;
                                                composerImpl2 = composerImpl3;
                                                i11 = 4;
                                                lazyGridState2 = lazyGridState;
                                            }
                                            Function2 function2 = (Function2) objRememberedValue4;
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            Orientation orientation = z2 ? Orientation.Vertical : Orientation.Horizontal;
                                            if (z3) {
                                                composerImpl2.startReplaceGroup(-1614890700);
                                                Modifier.Companion companion3 = Modifier.Companion;
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("androidx.compose.foundation.lazy.grid.rememberLazyGridBeyondBoundsState (LazyGridBeyondBoundsModifier.kt:24)");
                                                }
                                                if (((i16 ^ 6) <= i11 || !composerImpl2.changed(lazyGridState2)) && (i15 & 6) != i11) {
                                                    z8 = false;
                                                }
                                                Object objRememberedValue5 = composerImpl2.rememberedValue();
                                                if (z8 || objRememberedValue5 == obj) {
                                                    objRememberedValue5 = new LazyGridBeyondBoundsState(lazyGridState2);
                                                    composerImpl2.updateRememberedValue(objRememberedValue5);
                                                }
                                                LazyGridBeyondBoundsState lazyGridBeyondBoundsState = (LazyGridBeyondBoundsState) objRememberedValue5;
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                modifierLazyLayoutBeyondBoundsModifier = LazyLayoutBeyondBoundsModifierLocalKt.lazyLayoutBeyondBoundsModifier(companion3, lazyGridBeyondBoundsState, lazyGridState2.beyondBoundsInfo, z7, orientation);
                                                composerImpl2.end(false);
                                            } else {
                                                composerImpl2.startReplaceGroup(-1614595456);
                                                composerImpl2.end(false);
                                                modifierLazyLayoutBeyondBoundsModifier = Modifier.Companion;
                                            }
                                            modifier2 = modifier4;
                                            boolean z11 = z7;
                                            KProperty0 kProperty03 = kProperty0;
                                            Modifier modifierThen = LazyLayoutSemanticsKt.lazyLayoutSemantics(modifier2.then(lazyGridState2.remeasurementModifier).then(lazyGridState2.awaitLayoutModifier), kProperty0, lazySemanticsKt$rememberLazyGridSemanticState$1$1, orientation, z3, z11).then(modifierLazyLayoutBeyondBoundsModifier).then(lazyGridState2.itemAnimator.modifier);
                                            lazyGridState3 = lazyGridState2;
                                            FlingBehavior flingBehavior4 = flingBehavior3;
                                            composerImpl = composerImpl2;
                                            LazyLayoutKt.LazyLayout(kProperty03, ScrollingContainerKt.scrollingContainer(modifierThen, lazyGridState3, orientation, z3, z11, flingBehavior4, lazyGridState3.internalInteractionSource, false, overscrollEffect, null), lazyGridState3.prefetchState, function2, composerImpl, 0, 0);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            flingBehavior2 = flingBehavior4;
                                            z5 = z11;
                                            paddingValues2 = paddingValues3;
                                        }
                                    }
                                } else {
                                    composerImpl3.skipToGroupEnd();
                                    if ((i3 & 64) != 0) {
                                        i13 &= -3670017;
                                    }
                                    modifier3 = modifier;
                                    paddingValuesM120PaddingValues0680j_4 = paddingValues;
                                }
                                flingBehavior3 = flingBehavior;
                                boolean z92 = z4;
                                composerImpl3.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                int i152 = i13 >> 3;
                                int i162 = i152 & 14;
                                int i172 = i162 | (i10 & 112);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                final State<? extends Function1> mutableStateRememberUpdatedState2 = SnapshotStateKt.rememberUpdatedState(function1, composerImpl3);
                                Modifier modifier52 = modifier3;
                                int i182 = i4;
                                if (((i172 & 14) ^ 6) <= i182) {
                                    Object objRememberedValue6 = composerImpl3.rememberedValue();
                                    Composer.Companion companion22 = Composer.Companion;
                                    if (!z6) {
                                    }
                                } else {
                                    Object objRememberedValue62 = composerImpl3.rememberedValue();
                                    Composer.Companion companion222 = Composer.Companion;
                                    if (!z6) {
                                    }
                                }
                            } else {
                                composerImpl = composerImpl3;
                                composerImpl.skipToGroupEnd();
                                modifier2 = modifier;
                                paddingValues2 = paddingValues;
                                flingBehavior2 = flingBehavior;
                                z5 = z4;
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup != null) {
                                final LazyGridState lazyGridState4 = lazyGridState3;
                                final Modifier modifier6 = modifier2;
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.lazy.grid.LazyGridKt.LazyGrid.1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj3, Object obj4) {
                                        ((Number) obj4).intValue();
                                        LazyGridKt.LazyGrid(modifier6, lazyGridState4, lazyGridSlotsProvider, paddingValues2, z5, z2, flingBehavior2, z3, overscrollEffect, vertical, horizontal, function1, (Composer) obj3, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2), i3);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i8 = i2 | (composerImpl3.changed(horizontal) ? i4 : 2);
                    }
                    i7 = i8;
                    if ((i3 & 2048) != 0) {
                    }
                    i10 = i9;
                    if (composerImpl3.shouldExecute(i13 & 1, ((306783379 & i13) == 306783378 && (i10 & 19) == 18) ? false : true)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup != null) {
                    }
                }
                if ((i3 & 512) == 0) {
                }
                if ((1024 & i3) == 0) {
                }
                i7 = i8;
                if ((i3 & 2048) != 0) {
                }
                i10 = i9;
                if (composerImpl3.shouldExecute(i13 & 1, ((306783379 & i13) == 306783378 && (i10 & 19) == 18) ? false : true)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                }
            }
            z4 = z;
            if ((i3 & 32) != 0) {
            }
            if ((i & 1572864) == 0) {
            }
            if ((128 & i3) != 0) {
            }
            if ((i3 & 256) != 0) {
            }
            if ((i3 & 512) == 0) {
            }
            if ((1024 & i3) == 0) {
            }
            i7 = i8;
            if ((i3 & 2048) != 0) {
            }
            i10 = i9;
            if (composerImpl3.shouldExecute(i13 & 1, ((306783379 & i13) == 306783378 && (i10 & 19) == 18) ? false : true)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        i6 = i3 & 16;
        if (i6 == 0) {
        }
        z4 = z;
        if ((i3 & 32) != 0) {
        }
        if ((i & 1572864) == 0) {
        }
        if ((128 & i3) != 0) {
        }
        if ((i3 & 256) != 0) {
        }
        if ((i3 & 512) == 0) {
        }
        if ((1024 & i3) == 0) {
        }
        i7 = i8;
        if ((i3 & 2048) != 0) {
        }
        i10 = i9;
        if (composerImpl3.shouldExecute(i13 & 1, ((306783379 & i13) == 306783378 && (i10 & 19) == 18) ? false : true)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }
}
