package androidx.compose.foundation.lazy;

import androidx.collection.IntListKt;
import androidx.compose.foundation.CheckScrollableContainerConstraintsKt;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.ScrollingContainerKt;
import androidx.compose.foundation.gestures.FlingBehavior;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.lazy.LazyListState;
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
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.GraphicsContext;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.layout.SubcomposeMeasureScope;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.CollectionInfo;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.KotlinNothingValueException;
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
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.KProperty0;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public abstract class LazyListKt {
    /* JADX WARN: Removed duplicated region for block: B:108:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0280  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0301  */
    /* JADX WARN: Removed duplicated region for block: B:366:0x0585  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x0598  */
    /* JADX WARN: Removed duplicated region for block: B:371:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0116  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void LazyList(final Modifier modifier, final LazyListState lazyListState, final PaddingValues paddingValues, final boolean z, final boolean z2, final FlingBehavior flingBehavior, final boolean z3, final OverscrollEffect overscrollEffect, int i, Alignment.Horizontal horizontal, Arrangement.Vertical vertical, Alignment.Vertical vertical2, Arrangement.Horizontal horizontal2, final Function1 function1, Composer composer, final int i2, final int i3, final int i4) {
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
        LazyListState lazyListState2;
        ComposerImpl composerImpl;
        final Alignment.Horizontal horizontal3;
        final Arrangement.Vertical vertical3;
        final Alignment.Vertical vertical4;
        final Arrangement.Horizontal horizontal4;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        final Arrangement.Horizontal horizontal5;
        final Alignment.Horizontal horizontal6;
        int i15;
        Alignment.Vertical vertical5;
        final Arrangement.Vertical vertical6;
        StickyItemsPlacement$Companion$StickToTopPlacement$1 stickyItemsPlacement$Companion$StickToTopPlacement$1;
        Object obj;
        int i16;
        Arrangement.Vertical vertical7;
        LazyListState lazyListState3;
        KProperty0 kProperty0;
        Modifier modifierLazyLayoutBeyondBoundsModifier;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(924924659);
        if ((i4 & 1) != 0) {
            i5 = i2 | 6;
        } else if ((i2 & 6) == 0) {
            i5 = (composerImpl2.changed(modifier) ? 4 : 2) | i2;
        } else {
            i5 = i2;
        }
        if ((i4 & 2) != 0) {
            i5 |= 48;
        } else if ((i2 & 48) == 0) {
            i5 |= composerImpl2.changed(lazyListState) ? 32 : 16;
        }
        if ((i4 & 4) != 0) {
            i5 |= 384;
        } else if ((i2 & 384) == 0) {
            i5 |= composerImpl2.changed(paddingValues) ? 256 : 128;
        }
        if ((i4 & 8) != 0) {
            i5 |= 3072;
        } else if ((i2 & 3072) == 0) {
            i5 |= composerImpl2.changed(z) ? 2048 : 1024;
        }
        if ((i4 & 16) != 0) {
            i5 |= 24576;
        } else if ((i2 & 24576) == 0) {
            i5 |= composerImpl2.changed(z2) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((i4 & 32) != 0) {
            i5 |= 196608;
        } else {
            if ((i2 & 196608) == 0) {
                i5 |= composerImpl2.changed(flingBehavior) ? 131072 : 65536;
            }
            if ((i4 & 64) == 0) {
                i5 |= 1572864;
            } else if ((i2 & 1572864) == 0) {
                i5 |= composerImpl2.changed(z3) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
            }
            if ((i4 & 128) != 0) {
                if ((i2 & 12582912) == 0) {
                    i5 |= composerImpl2.changed(overscrollEffect) ? 8388608 : 4194304;
                }
                if ((i2 & 100663296) == 0) {
                    if ((i4 & 256) == 0) {
                        i6 = i;
                        int i17 = composerImpl2.changed(i6) ? 67108864 : 33554432;
                        i5 |= i17;
                    } else {
                        i6 = i;
                    }
                    i5 |= i17;
                } else {
                    i6 = i;
                }
                i7 = i4 & 512;
                if (i7 != 0) {
                    i5 |= 805306368;
                } else if ((i2 & 805306368) == 0) {
                    i5 |= composerImpl2.changed(horizontal) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                }
                i8 = i4 & 1024;
                if (i8 != 0) {
                    i10 = i3 | 6;
                    i9 = i8;
                } else if ((i3 & 6) == 0) {
                    i9 = i8;
                    i10 = i3 | (composerImpl2.changed(vertical) ? 4 : 2);
                } else {
                    i9 = i8;
                    i10 = i3;
                }
                i11 = i4 & 2048;
                if (i11 != 0) {
                    i10 |= 48;
                    i12 = i11;
                } else if ((i3 & 48) == 0) {
                    i12 = i11;
                    i10 |= composerImpl2.changed(vertical2) ? 32 : 16;
                } else {
                    i12 = i11;
                }
                int i18 = i10;
                i13 = i4 & 4096;
                if (i13 != 0) {
                    i14 = i18 | 384;
                } else {
                    int i19 = i18;
                    if ((i3 & 384) == 0) {
                        i19 |= composerImpl2.changed(horizontal2) ? 256 : 128;
                    }
                    i14 = i19;
                }
                if ((i4 & 8192) != 0) {
                    i14 |= 3072;
                } else if ((i3 & 3072) == 0) {
                    i14 |= composerImpl2.changedInstance(function1) ? 2048 : 1024;
                }
                boolean z4 = true;
                if (composerImpl2.shouldExecute(i5 & 1, ((306783379 & i5) == 306783378 && (i14 & 1171) == 1170) ? false : true)) {
                    composerImpl2.startDefaults();
                    if ((i2 & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                        if ((i4 & 256) != 0) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.foundation.lazy.defaultLazyListBeyondBoundsItemCount (LazyList.android.kt:20)");
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            i5 &= -234881025;
                            i6 = 0;
                        }
                        Alignment.Horizontal horizontal7 = i7 != 0 ? null : horizontal;
                        Arrangement.Vertical vertical8 = i9 != 0 ? null : vertical;
                        Alignment.Vertical vertical9 = i12 != 0 ? null : vertical2;
                        if (i13 != 0) {
                            horizontal6 = horizontal7;
                            horizontal5 = null;
                            i15 = i5;
                            vertical5 = vertical9;
                        } else {
                            horizontal5 = horizontal2;
                            horizontal6 = horizontal7;
                            i15 = i5;
                            vertical5 = vertical9;
                        }
                        vertical6 = vertical8;
                    } else {
                        composerImpl2.skipToGroupEnd();
                        if ((i4 & 256) != 0) {
                            i5 &= -234881025;
                        }
                        horizontal6 = horizontal;
                        vertical5 = vertical2;
                        horizontal5 = horizontal2;
                        i15 = i5;
                        vertical6 = vertical;
                    }
                    composerImpl2.endDefaults();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.lazy.LazyList (LazyList.kt:84)");
                    }
                    int i20 = (i15 >> 3) & 14;
                    int i21 = i20 | ((i14 >> 6) & 112);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.lazy.rememberLazyListItemProviderLambda (LazyListItemProvider.kt:44)");
                    }
                    final MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function1, composerImpl2);
                    int i22 = i14;
                    boolean z5 = (((i21 & 14) ^ 6) > 4 && composerImpl2.changed(lazyListState)) || (i21 & 6) == 4;
                    Object objRememberedValue = composerImpl2.rememberedValue();
                    Composer.Companion companion = Composer.Companion;
                    if (!z5) {
                        companion.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            final LazyItemScopeImpl lazyItemScopeImpl = new LazyItemScopeImpl();
                            final State stateDerivedStateOf = SnapshotStateKt.derivedStateOf(SnapshotStateKt.referentialEqualityPolicy(), new Function0() { // from class: androidx.compose.foundation.lazy.LazyListItemProviderKt$rememberLazyListItemProviderLambda$1$intervalContentState$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                /* JADX WARN: Multi-variable type inference failed */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    return new LazyListIntervalContent((Function1) mutableStateRememberUpdatedState.getValue());
                                }
                            });
                            final State stateDerivedStateOf2 = SnapshotStateKt.derivedStateOf(SnapshotStateKt.referentialEqualityPolicy(), new Function0() { // from class: androidx.compose.foundation.lazy.LazyListItemProviderKt$rememberLazyListItemProviderLambda$1$itemProviderState$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    LazyListIntervalContent lazyListIntervalContent = (LazyListIntervalContent) stateDerivedStateOf.getValue();
                                    return new LazyListItemProviderImpl(lazyListState, lazyListIntervalContent, lazyItemScopeImpl, new NearestRangeKeyIndexMap((IntRange) lazyListState.scrollPosition.nearestRangeState.getValue(), lazyListIntervalContent));
                                }
                            });
                            objRememberedValue = new PropertyReference0Impl(stateDerivedStateOf2) { // from class: androidx.compose.foundation.lazy.LazyListItemProviderKt$rememberLazyListItemProviderLambda$1$1
                                @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                                public final Object get() {
                                    return ((State) this.receiver).getValue();
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue);
                        }
                        final KProperty0 kProperty02 = (KProperty0) objRememberedValue;
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        int i23 = i15 >> 9;
                        int i24 = i20 | (i23 & 112);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.foundation.lazy.rememberLazyListSemanticState (LazyListSemantics.kt:26)");
                        }
                        boolean z6 = ((((i24 & 112) ^ 48) > 32 && composerImpl2.changed(z2)) || (i24 & 48) == 32) | ((((i24 & 14) ^ 6) > 4 && composerImpl2.changed(lazyListState)) || (i24 & 6) == 4);
                        Object objRememberedValue2 = composerImpl2.rememberedValue();
                        if (!z6) {
                            companion.getClass();
                            if (objRememberedValue2 == Composer.Companion.Empty) {
                                objRememberedValue2 = new LazyLayoutSemanticState() { // from class: androidx.compose.foundation.lazy.LazyLayoutSemanticStateKt$LazyLayoutSemanticState$1
                                    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                    public final CollectionInfo collectionInfo() {
                                        return z2 ? new CollectionInfo(-1, 1) : new CollectionInfo(1, -1);
                                    }

                                    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                    public final int getContentPadding() {
                                        LazyListState lazyListState4 = lazyListState;
                                        return (-((LazyListMeasureResult) lazyListState4.getLayoutInfo()).viewportStartOffset) + ((LazyListMeasureResult) lazyListState4.getLayoutInfo()).afterContentPadding;
                                    }

                                    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                    public final float getMaxScrollOffset() {
                                        LazyListState lazyListState4 = lazyListState;
                                        int index = lazyListState4.scrollPosition.getIndex();
                                        int scrollOffset = lazyListState4.scrollPosition.getScrollOffset();
                                        return lazyListState4.getCanScrollForward() ? (index * 500) + scrollOffset + 100 : (index * 500) + scrollOffset;
                                    }

                                    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                    public final float getScrollOffset() {
                                        LazyListState lazyListState4 = lazyListState;
                                        return (lazyListState4.scrollPosition.getIndex() * 500) + lazyListState4.scrollPosition.getScrollOffset();
                                    }

                                    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                    public final int getViewport() {
                                        LazyListState lazyListState4 = lazyListState;
                                        return (int) (((LazyListMeasureResult) lazyListState4.getLayoutInfo()).orientation == Orientation.Vertical ? ((LazyListMeasureResult) lazyListState4.getLayoutInfo()).m152getViewportSizeYbymL2g() & 4294967295L : ((LazyListMeasureResult) lazyListState4.getLayoutInfo()).m152getViewportSizeYbymL2g() >> 32);
                                    }

                                    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutSemanticState
                                    public final Object scrollToItem(int i25, Continuation continuation) {
                                        LazyListState.Companion companion2 = LazyListState.Companion;
                                        Object objScrollToItem = lazyListState.scrollToItem(i25, 0, (SuspendLambda) continuation);
                                        return objScrollToItem == CoroutineSingletons.COROUTINE_SUSPENDED ? objScrollToItem : Unit.INSTANCE;
                                    }
                                };
                                composerImpl2.updateRememberedValue(objRememberedValue2);
                            }
                            LazyLayoutSemanticState lazyLayoutSemanticState = (LazyLayoutSemanticState) objRememberedValue2;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            Object objRememberedValue3 = composerImpl2.rememberedValue();
                            companion.getClass();
                            Object obj2 = Composer.Companion.Empty;
                            if (objRememberedValue3 == obj2) {
                                objRememberedValue3 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                                composerImpl2.updateRememberedValue(objRememberedValue3);
                            }
                            final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue3;
                            final GraphicsContext graphicsContext = (GraphicsContext) composerImpl2.consume(CompositionLocalsKt.LocalGraphicsContext);
                            if (((Boolean) composerImpl2.consume(CompositionLocalsKt.LocalProvidableScrollCaptureInProgress)).booleanValue()) {
                                stickyItemsPlacement$Companion$StickToTopPlacement$1 = null;
                            } else {
                                StickyItemsPlacement.Companion.getClass();
                                stickyItemsPlacement$Companion$StickToTopPlacement$1 = StickyItemsPlacement.Companion.StickToTopPlacement;
                            }
                            int i25 = i22 << 18;
                            int i26 = (i15 & 65520) | (i23 & 458752) | (i23 & 3670016) | (i25 & 29360128) | (i25 & 234881024) | ((i22 << 27) & 1879048192);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.foundation.lazy.rememberLazyListMeasurePolicy (LazyList.kt:187)");
                            }
                            boolean zChanged = ((((458752 & i26) ^ 196608) > 131072 && composerImpl2.changed(i6)) || (i26 & 196608) == 131072) | ((((i26 & 112) ^ 48) > 32 && composerImpl2.changed(lazyListState)) || (i26 & 48) == 32) | ((((i26 & 896) ^ 384) > 256 && composerImpl2.changed(paddingValues)) || (i26 & 384) == 256) | ((((i26 & 7168) ^ 3072) > 2048 && composerImpl2.changed(z)) || (i26 & 3072) == 2048) | ((((57344 & i26) ^ 24576) > 16384 && composerImpl2.changed(z2)) || (i26 & 24576) == 16384) | ((((i26 & 3670016) ^ 1572864) > 1048576 && composerImpl2.changed(horizontal6)) || (i26 & 1572864) == 1048576) | ((((i26 & 29360128) ^ 12582912) > 8388608 && composerImpl2.changed(vertical5)) || (i26 & 12582912) == 8388608) | ((((i26 & 234881024) ^ 100663296) > 67108864 && composerImpl2.changed(horizontal5)) || (i26 & 100663296) == 67108864) | ((((i26 & 1879048192) ^ 805306368) > 536870912 && composerImpl2.changed(vertical6)) || (i26 & 805306368) == 536870912) | composerImpl2.changed(graphicsContext) | composerImpl2.changed(stickyItemsPlacement$Companion$StickToTopPlacement$1);
                            Object objRememberedValue4 = composerImpl2.rememberedValue();
                            if (zChanged || objRememberedValue4 == obj2) {
                                obj = obj2;
                                i16 = 4;
                                final StickyItemsPlacement$Companion$StickToTopPlacement$1 stickyItemsPlacement$Companion$StickToTopPlacement$12 = stickyItemsPlacement$Companion$StickToTopPlacement$1;
                                composerImpl = composerImpl2;
                                final int i27 = i6;
                                final Alignment.Vertical vertical10 = vertical5;
                                objRememberedValue4 = new Function2() { // from class: androidx.compose.foundation.lazy.LazyListKt$rememberLazyListMeasurePolicy$1$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    /* JADX WARN: Multi-variable type inference failed */
                                    /* JADX WARN: Removed duplicated region for block: B:288:0x069e  */
                                    /* JADX WARN: Removed duplicated region for block: B:290:0x06a6  */
                                    /* JADX WARN: Removed duplicated region for block: B:408:0x091b  */
                                    /* JADX WARN: Removed duplicated region for block: B:414:0x094b  */
                                    /* JADX WARN: Removed duplicated region for block: B:419:0x095e  */
                                    /* JADX WARN: Removed duplicated region for block: B:423:0x096f  */
                                    /* JADX WARN: Removed duplicated region for block: B:424:0x0972  */
                                    /* JADX WARN: Removed duplicated region for block: B:432:0x098a  */
                                    /* JADX WARN: Removed duplicated region for block: B:435:0x09a7  */
                                    /* JADX WARN: Removed duplicated region for block: B:436:0x09ac  */
                                    /* JADX WARN: Removed duplicated region for block: B:438:0x09af  */
                                    /* JADX WARN: Removed duplicated region for block: B:439:0x09b4  */
                                    /* JADX WARN: Removed duplicated region for block: B:442:0x09bb  */
                                    /* JADX WARN: Removed duplicated region for block: B:444:0x09c1  */
                                    /* JADX WARN: Type inference failed for: r12v21 */
                                    /* JADX WARN: Type inference failed for: r12v22 */
                                    /* JADX WARN: Type inference failed for: r12v23, types: [java.lang.Object] */
                                    /* JADX WARN: Type inference failed for: r3v54, types: [kotlin.ranges.IntProgression] */
                                    @Override // kotlin.jvm.functions.Function2
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object invoke(Object obj3, Object obj4) {
                                        int iMo52roundToPx0680j_4;
                                        int iMo52roundToPx0680j_42;
                                        float fMo95getSpacingD9Ej5fM;
                                        int i28;
                                        String str;
                                        long j;
                                        SubcomposeMeasureScope subcomposeMeasureScope;
                                        int i29;
                                        int i30;
                                        int i31;
                                        float f;
                                        int i32;
                                        LazyListMeasuredItem lazyListMeasuredItem;
                                        int i33;
                                        int i34;
                                        List arrayList;
                                        float f2;
                                        int i35;
                                        ArrayList arrayList2;
                                        List list;
                                        List arrayList3;
                                        boolean z7;
                                        int i36;
                                        long j2;
                                        Function3 function3;
                                        int i37;
                                        LazyLayoutMeasureScope lazyLayoutMeasureScope;
                                        LazyListMeasuredItem lazyListMeasuredItem2;
                                        final LazyListMeasuredItemProvider lazyListMeasuredItemProvider;
                                        int i38;
                                        Integer numValueOf;
                                        Integer numValueOf2;
                                        LazyListMeasureResult lazyListMeasureResult;
                                        boolean z8;
                                        int i39;
                                        LazyListItemInfo lazyListItemInfo;
                                        LazyListItemInfo lazyListItemInfo2;
                                        float f3;
                                        LazyListMeasuredItem lazyListMeasuredItem3;
                                        LazyListMeasuredItem lazyListMeasuredItem4;
                                        LazyListMeasuredItem lazyListMeasuredItem5;
                                        int i40;
                                        Object obj5;
                                        int index;
                                        int iMin;
                                        LazyListMeasuredItem lazyListMeasuredItem6;
                                        Object obj6;
                                        LazyLayoutMeasureScope lazyLayoutMeasureScope2;
                                        long j3;
                                        LazyListKt$rememberLazyListMeasurePolicy$1$1 lazyListKt$rememberLazyListMeasurePolicy$1$1 = this;
                                        final LazyLayoutMeasureScope lazyLayoutMeasureScope3 = (LazyLayoutMeasureScope) obj3;
                                        final long j4 = ((Constraints) obj4).value;
                                        lazyListState.measurementScopeInvalidator.getValue();
                                        boolean z9 = lazyListState.hasLookaheadOccurred || ((LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope3).subcomposeMeasureScope.isLookingAhead();
                                        CheckScrollableContainerConstraintsKt.m32checkScrollableContainerConstraintsK40F9xA(z2 ? Orientation.Vertical : Orientation.Horizontal, j4);
                                        if (z2) {
                                            LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope3;
                                            iMo52roundToPx0680j_4 = lazyLayoutMeasureScopeImpl.subcomposeMeasureScope.mo52roundToPx0680j_4(paddingValues.mo111calculateLeftPaddingu2uoSUM(lazyLayoutMeasureScopeImpl.subcomposeMeasureScope.getLayoutDirection()));
                                        } else {
                                            LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl2 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope3;
                                            iMo52roundToPx0680j_4 = lazyLayoutMeasureScopeImpl2.subcomposeMeasureScope.mo52roundToPx0680j_4(PaddingKt.calculateStartPadding(paddingValues, lazyLayoutMeasureScopeImpl2.subcomposeMeasureScope.getLayoutDirection()));
                                        }
                                        if (z2) {
                                            LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl3 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope3;
                                            iMo52roundToPx0680j_42 = lazyLayoutMeasureScopeImpl3.subcomposeMeasureScope.mo52roundToPx0680j_4(paddingValues.mo112calculateRightPaddingu2uoSUM(lazyLayoutMeasureScopeImpl3.subcomposeMeasureScope.getLayoutDirection()));
                                        } else {
                                            LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl4 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope3;
                                            iMo52roundToPx0680j_42 = lazyLayoutMeasureScopeImpl4.subcomposeMeasureScope.mo52roundToPx0680j_4(PaddingKt.calculateEndPadding(paddingValues, lazyLayoutMeasureScopeImpl4.subcomposeMeasureScope.getLayoutDirection()));
                                        }
                                        LazyLayoutMeasureScopeImpl lazyLayoutMeasureScopeImpl5 = (LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope3;
                                        int iMo52roundToPx0680j_43 = lazyLayoutMeasureScopeImpl5.subcomposeMeasureScope.mo52roundToPx0680j_4(paddingValues.mo113calculateTopPaddingD9Ej5fM());
                                        float fMo110calculateBottomPaddingD9Ej5fM = paddingValues.mo110calculateBottomPaddingD9Ej5fM();
                                        SubcomposeMeasureScope subcomposeMeasureScope2 = lazyLayoutMeasureScopeImpl5.subcomposeMeasureScope;
                                        int iMo52roundToPx0680j_44 = subcomposeMeasureScope2.mo52roundToPx0680j_4(fMo110calculateBottomPaddingD9Ej5fM);
                                        final int i41 = iMo52roundToPx0680j_43 + iMo52roundToPx0680j_44;
                                        final int i42 = iMo52roundToPx0680j_4 + iMo52roundToPx0680j_42;
                                        boolean z10 = z2;
                                        int i43 = z10 ? i41 : i42;
                                        final int i44 = (!z10 || z) ? (z10 && z) ? iMo52roundToPx0680j_44 : (z10 || z) ? iMo52roundToPx0680j_42 : iMo52roundToPx0680j_4 : iMo52roundToPx0680j_43;
                                        final int i45 = i43 - i44;
                                        final long jM835offsetNN6EwU = ConstraintsKt.m835offsetNN6EwU(-i42, -i41, j4);
                                        final LazyListItemProvider lazyListItemProvider = (LazyListItemProvider) kProperty02.invoke();
                                        LazyListItemProviderImpl lazyListItemProviderImpl = (LazyListItemProviderImpl) lazyListItemProvider;
                                        LazyItemScopeImpl lazyItemScopeImpl2 = lazyListItemProviderImpl.itemScope;
                                        int iM823getMaxWidthimpl = Constraints.m823getMaxWidthimpl(jM835offsetNN6EwU);
                                        int i46 = -1;
                                        int iM822getMaxHeightimpl = Constraints.m822getMaxHeightimpl(jM835offsetNN6EwU);
                                        ((SnapshotMutableIntStateImpl) lazyItemScopeImpl2.maxWidthState).setIntValue(iM823getMaxWidthimpl);
                                        ((SnapshotMutableIntStateImpl) lazyItemScopeImpl2.maxHeightState).setIntValue(iM822getMaxHeightimpl);
                                        if (z2) {
                                            Arrangement.Vertical vertical11 = vertical6;
                                            if (vertical11 == null) {
                                                InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("null verticalArrangement when isVertical == true");
                                                throw new KotlinNothingValueException();
                                            }
                                            fMo95getSpacingD9Ej5fM = vertical11.mo95getSpacingD9Ej5fM();
                                        } else {
                                            Arrangement.Horizontal horizontal8 = horizontal5;
                                            if (horizontal8 == null) {
                                                InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("null horizontalAlignment when isVertical == false");
                                                throw new KotlinNothingValueException();
                                            }
                                            fMo95getSpacingD9Ej5fM = horizontal8.mo95getSpacingD9Ej5fM();
                                        }
                                        final int iMo52roundToPx0680j_45 = subcomposeMeasureScope2.mo52roundToPx0680j_4(fMo95getSpacingD9Ej5fM);
                                        int i47 = lazyListItemProviderImpl.intervalContent.getIntervals$1().size;
                                        int iM822getMaxHeightimpl2 = z2 ? Constraints.m822getMaxHeightimpl(j4) - i41 : Constraints.m823getMaxWidthimpl(j4) - i42;
                                        final boolean z11 = z;
                                        if (!z11 || iM822getMaxHeightimpl2 > 0) {
                                            i28 = i47;
                                            str = "null verticalArrangement when isVertical == true";
                                            j = (iMo52roundToPx0680j_4 << 32) | (iMo52roundToPx0680j_43 & 4294967295L);
                                            IntOffset.Companion companion2 = IntOffset.Companion;
                                        } else {
                                            boolean z12 = z2;
                                            if (!z12) {
                                                iMo52roundToPx0680j_4 += iM822getMaxHeightimpl2;
                                            }
                                            if (z12) {
                                                iMo52roundToPx0680j_43 += iM822getMaxHeightimpl2;
                                            }
                                            i28 = i47;
                                            str = "null verticalArrangement when isVertical == true";
                                            j = (iMo52roundToPx0680j_4 << 32) | (iMo52roundToPx0680j_43 & 4294967295L);
                                            IntOffset.Companion companion3 = IntOffset.Companion;
                                        }
                                        final boolean z13 = z2;
                                        final Alignment.Horizontal horizontal9 = horizontal6;
                                        final Alignment.Vertical vertical12 = vertical10;
                                        final LazyListState lazyListState4 = lazyListState;
                                        int i48 = iM822getMaxHeightimpl2;
                                        final int i49 = i28;
                                        final long j5 = j;
                                        LazyListMeasuredItemProvider lazyListMeasuredItemProvider2 = new LazyListMeasuredItemProvider(jM835offsetNN6EwU, z13, lazyListItemProvider, lazyLayoutMeasureScope3, i49, iMo52roundToPx0680j_45, horizontal9, vertical12, z11, i44, i45, j5, lazyListState4) { // from class: androidx.compose.foundation.lazy.LazyListKt$rememberLazyListMeasurePolicy$1$1$measuredItemProvider$1
                                            public final /* synthetic */ int $afterContentPadding;
                                            public final /* synthetic */ int $beforeContentPadding;
                                            public final /* synthetic */ Alignment.Horizontal $horizontalAlignment;
                                            public final /* synthetic */ boolean $isVertical;
                                            public final /* synthetic */ int $itemsCount;
                                            public final /* synthetic */ boolean $reverseLayout;
                                            public final /* synthetic */ int $spaceBetweenItems;
                                            public final /* synthetic */ LazyListState $state;
                                            public final /* synthetic */ LazyLayoutMeasureScope $this_null;
                                            public final /* synthetic */ Alignment.Vertical $verticalAlignment;
                                            public final /* synthetic */ long $visualItemOffset;

                                            {
                                                this.$isVertical = z13;
                                                this.$this_null = lazyLayoutMeasureScope3;
                                                this.$itemsCount = i49;
                                                this.$spaceBetweenItems = iMo52roundToPx0680j_45;
                                                this.$horizontalAlignment = horizontal9;
                                                this.$verticalAlignment = vertical12;
                                                this.$reverseLayout = z11;
                                                this.$beforeContentPadding = i44;
                                                this.$afterContentPadding = i45;
                                                this.$visualItemOffset = j5;
                                                this.$state = lazyListState4;
                                            }

                                            @Override // androidx.compose.foundation.lazy.LazyListMeasuredItemProvider
                                            /* renamed from: createItem-X9ElhV4, reason: not valid java name */
                                            public final LazyListMeasuredItem mo151createItemX9ElhV4(int i50, Object obj7, Object obj8, List list2, long j6) {
                                                int i51 = i50 == this.$itemsCount + (-1) ? 0 : this.$spaceBetweenItems;
                                                return new LazyListMeasuredItem(i50, list2, this.$isVertical, this.$horizontalAlignment, this.$verticalAlignment, ((LazyLayoutMeasureScopeImpl) this.$this_null).subcomposeMeasureScope.getLayoutDirection(), this.$reverseLayout, this.$beforeContentPadding, this.$afterContentPadding, i51, this.$visualItemOffset, obj7, obj8, this.$state.itemAnimator, j6, null);
                                            }
                                        };
                                        Snapshot.Companion companion4 = Snapshot.Companion;
                                        LazyListState lazyListState5 = lazyListState;
                                        companion4.getClass();
                                        Snapshot currentThreadSnapshot = Snapshot.Companion.getCurrentThreadSnapshot();
                                        Function1 readObserver = currentThreadSnapshot != null ? currentThreadSnapshot.getReadObserver() : null;
                                        Snapshot snapshotMakeCurrentNonObservable = Snapshot.Companion.makeCurrentNonObservable(currentThreadSnapshot);
                                        try {
                                            LazyListScrollPosition lazyListScrollPosition = lazyListState5.scrollPosition;
                                            int index2 = lazyListScrollPosition.getIndex();
                                            int iFindIndexByKey = LazyLayoutItemProviderKt.findIndexByKey(lazyListItemProvider, index2, lazyListScrollPosition.lastKnownFirstItemKey);
                                            if (index2 != iFindIndexByKey) {
                                                subcomposeMeasureScope = subcomposeMeasureScope2;
                                                ((SnapshotMutableIntStateImpl) lazyListScrollPosition.index$delegate).setIntValue(iFindIndexByKey);
                                                lazyListScrollPosition.nearestRangeState.update(index2);
                                            } else {
                                                subcomposeMeasureScope = subcomposeMeasureScope2;
                                            }
                                            int scrollOffset = lazyListScrollPosition.getScrollOffset();
                                            Unit unit = Unit.INSTANCE;
                                            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                                            LazyListState lazyListState6 = lazyListState;
                                            List listCalculateLazyLayoutPinnedIndices = LazyLayoutBeyondBoundsStateKt.calculateLazyLayoutPinnedIndices(lazyListItemProvider, lazyListState6.pinnedItems, lazyListState6.beyondBoundsInfo);
                                            float fFloatValue = (subcomposeMeasureScope.isLookingAhead() || !z9) ? lazyListState.scrollToBeConsumed : ((Number) ((SnapshotMutableStateImpl) lazyListState._lazyLayoutScrollDeltaBetweenPasses._scrollDeltaBetweenPasses.value$delegate).getValue()).floatValue();
                                            boolean z14 = z2;
                                            Arrangement.Vertical vertical13 = vertical6;
                                            Arrangement.Horizontal horizontal10 = horizontal5;
                                            boolean z15 = z;
                                            LazyLayoutItemAnimator lazyLayoutItemAnimator = lazyListState.itemAnimator;
                                            int i50 = scrollOffset;
                                            int i51 = i27;
                                            final boolean zIsLookingAhead = subcomposeMeasureScope.isLookingAhead();
                                            LazyListState lazyListState7 = lazyListState;
                                            LazyListMeasureResult lazyListMeasureResult2 = lazyListState7.approachLayoutInfo;
                                            CoroutineScope coroutineScope2 = coroutineScope;
                                            GraphicsContext graphicsContext2 = graphicsContext;
                                            StickyItemsPlacement stickyItemsPlacement = stickyItemsPlacement$Companion$StickToTopPlacement$12;
                                            int i52 = iFindIndexByKey;
                                            Arrangement.Vertical vertical14 = vertical13;
                                            Function3 function32 = new Function3() { // from class: androidx.compose.foundation.lazy.LazyListKt$rememberLazyListMeasurePolicy$1$1$measureResult$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(3);
                                                }

                                                @Override // kotlin.jvm.functions.Function3
                                                public final Object invoke(Object obj7, Object obj8, Object obj9) {
                                                    int iIntValue = ((Number) obj7).intValue();
                                                    int iIntValue2 = ((Number) obj8).intValue();
                                                    LazyLayoutMeasureScope lazyLayoutMeasureScope4 = lazyLayoutMeasureScope3;
                                                    int iM834constrainWidthK40F9xA = ConstraintsKt.m834constrainWidthK40F9xA(iIntValue + i42, j4);
                                                    int iM833constrainHeightK40F9xA = ConstraintsKt.m833constrainHeightK40F9xA(iIntValue2 + i41, j4);
                                                    Map mapEmptyMap = MapsKt__MapsKt.emptyMap();
                                                    return ((LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope4).subcomposeMeasureScope.layout$1(iM834constrainWidthK40F9xA, iM833constrainHeightK40F9xA, mapEmptyMap, (Function1) obj9);
                                                }
                                            };
                                            if (i44 < 0) {
                                                InlineClassHelperKt.throwIllegalArgumentException("invalid beforeContentPadding");
                                            }
                                            if (i45 < 0) {
                                                InlineClassHelperKt.throwIllegalArgumentException("invalid afterContentPadding");
                                            }
                                            LazyListItemProvider lazyListItemProvider2 = lazyListMeasuredItemProvider2.itemProvider;
                                            if (i49 <= 0) {
                                                int iM825getMinWidthimpl = Constraints.m825getMinWidthimpl(jM835offsetNN6EwU);
                                                int iM824getMinHeightimpl = Constraints.m824getMinHeightimpl(jM835offsetNN6EwU);
                                                int i53 = i44;
                                                lazyLayoutItemAnimator.onMeasured(0, iM825getMinWidthimpl, iM824getMinHeightimpl, new ArrayList(), ((LazyListItemProviderImpl) lazyListItemProvider2).keyIndexMap, lazyListMeasuredItemProvider2, z14, zIsLookingAhead, 1, z9, 0, 0, coroutineScope2, graphicsContext2);
                                                if (!zIsLookingAhead) {
                                                    long jM168getMinSizeToFitDisappearingItemsYbymL2g = lazyLayoutItemAnimator.m168getMinSizeToFitDisappearingItemsYbymL2g();
                                                    IntSize.Companion.getClass();
                                                    if (!IntSize.m863equalsimpl0(jM168getMinSizeToFitDisappearingItemsYbymL2g, 0L)) {
                                                        int iM834constrainWidthK40F9xA = ConstraintsKt.m834constrainWidthK40F9xA((int) (jM168getMinSizeToFitDisappearingItemsYbymL2g >> 32), jM835offsetNN6EwU);
                                                        iM824getMinHeightimpl = ConstraintsKt.m833constrainHeightK40F9xA((int) (jM168getMinSizeToFitDisappearingItemsYbymL2g & 4294967295L), jM835offsetNN6EwU);
                                                        iM825getMinWidthimpl = iM834constrainWidthK40F9xA;
                                                    }
                                                }
                                                lazyListMeasuredItemProvider = lazyListMeasuredItemProvider2;
                                                lazyListMeasureResult = new LazyListMeasureResult(null, 0, false, 0.0f, (MeasureResult) function32.invoke(Integer.valueOf(iM825getMinWidthimpl), Integer.valueOf(iM824getMinHeightimpl), new Function1() { // from class: androidx.compose.foundation.lazy.LazyListMeasureKt$measureLazyList$3
                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj7) {
                                                        return Unit.INSTANCE;
                                                    }
                                                }), 0.0f, false, coroutineScope2, lazyLayoutMeasureScope3, lazyListMeasuredItemProvider2.childConstraints, EmptyList.INSTANCE, -i53, i48 + i45, 0, z15, z14 ? Orientation.Vertical : Orientation.Horizontal, i45, iMo52roundToPx0680j_45, null);
                                            } else {
                                                boolean z16 = z14;
                                                LazyListItemProvider lazyListItemProvider3 = lazyListItemProvider2;
                                                boolean z17 = z9;
                                                if (i52 >= i49) {
                                                    i52 = i49 - 1;
                                                    i50 = 0;
                                                }
                                                int iRound = Math.round(fFloatValue);
                                                int i54 = i50 - iRound;
                                                if (i52 == 0 && i54 < 0) {
                                                    iRound += i54;
                                                    i54 = 0;
                                                }
                                                int i55 = i52;
                                                ArrayDeque arrayDeque = new ArrayDeque();
                                                int i56 = -i44;
                                                int i57 = i56 + (iMo52roundToPx0680j_45 < 0 ? iMo52roundToPx0680j_45 : 0);
                                                int i58 = i54 + i57;
                                                int iMax = 0;
                                                while (i58 < 0 && i55 > 0) {
                                                    LazyListItemProvider lazyListItemProvider4 = lazyListItemProvider3;
                                                    int i59 = i55 - 1;
                                                    boolean z18 = z16;
                                                    LazyListMeasuredItem lazyListMeasuredItemM156getAndMeasure0kLqBqw$default = LazyListMeasuredItemProvider.m156getAndMeasure0kLqBqw$default(lazyListMeasuredItemProvider2, i59);
                                                    i55 = i59;
                                                    arrayDeque.add(0, lazyListMeasuredItemM156getAndMeasure0kLqBqw$default);
                                                    iMax = Math.max(iMax, lazyListMeasuredItemM156getAndMeasure0kLqBqw$default.crossAxisSize);
                                                    i58 += lazyListMeasuredItemM156getAndMeasure0kLqBqw$default.mainAxisSizeWithSpacings;
                                                    lazyListItemProvider3 = lazyListItemProvider4;
                                                    z16 = z18;
                                                }
                                                LazyListItemProvider lazyListItemProvider5 = lazyListItemProvider3;
                                                boolean z19 = z16;
                                                if (i58 < i57) {
                                                    iRound -= i57 - i58;
                                                    i58 = i57;
                                                }
                                                int i60 = iRound;
                                                int i61 = i58 - i57;
                                                int i62 = i48 + i45;
                                                int i63 = i62 < 0 ? 0 : i62;
                                                int i64 = iMax;
                                                int i65 = -i61;
                                                int i66 = i55;
                                                int i67 = 0;
                                                boolean z20 = false;
                                                while (i67 < arrayDeque.size) {
                                                    if (i65 >= i63) {
                                                        arrayDeque.removeAt(i67);
                                                        z20 = true;
                                                    } else {
                                                        i66++;
                                                        i65 += ((LazyListMeasuredItem) arrayDeque.get(i67)).mainAxisSizeWithSpacings;
                                                        i67++;
                                                    }
                                                }
                                                int iMax2 = i64;
                                                int i68 = i61;
                                                int i69 = i66;
                                                while (i69 < i49 && (i65 < i63 || i65 <= 0 || arrayDeque.isEmpty())) {
                                                    int i70 = i63;
                                                    LazyListMeasuredItem lazyListMeasuredItemM156getAndMeasure0kLqBqw$default2 = LazyListMeasuredItemProvider.m156getAndMeasure0kLqBqw$default(lazyListMeasuredItemProvider2, i69);
                                                    Arrangement.Vertical vertical15 = vertical14;
                                                    int i71 = lazyListMeasuredItemM156getAndMeasure0kLqBqw$default2.mainAxisSizeWithSpacings;
                                                    i65 += i71;
                                                    int i72 = i57;
                                                    if (i65 > i57 || i69 == i49 - 1) {
                                                        int iMax3 = Math.max(iMax2, lazyListMeasuredItemM156getAndMeasure0kLqBqw$default2.crossAxisSize);
                                                        arrayDeque.addLast(lazyListMeasuredItemM156getAndMeasure0kLqBqw$default2);
                                                        iMax2 = iMax3;
                                                    } else {
                                                        i55 = i69 + 1;
                                                        i68 -= i71;
                                                        z20 = true;
                                                    }
                                                    i69++;
                                                    i63 = i70;
                                                    vertical14 = vertical15;
                                                    i57 = i72;
                                                }
                                                Arrangement.Vertical vertical16 = vertical14;
                                                if (i65 < i48) {
                                                    int i73 = i48 - i65;
                                                    int i74 = i65 + i73;
                                                    int i75 = i68 - i73;
                                                    while (i75 < i44 && i55 > 0) {
                                                        int i76 = i55 - 1;
                                                        int i77 = i73;
                                                        LazyListMeasuredItem lazyListMeasuredItemM156getAndMeasure0kLqBqw$default3 = LazyListMeasuredItemProvider.m156getAndMeasure0kLqBqw$default(lazyListMeasuredItemProvider2, i76);
                                                        arrayDeque.add(0, lazyListMeasuredItemM156getAndMeasure0kLqBqw$default3);
                                                        iMax2 = Math.max(iMax2, lazyListMeasuredItemM156getAndMeasure0kLqBqw$default3.crossAxisSize);
                                                        i75 += lazyListMeasuredItemM156getAndMeasure0kLqBqw$default3.mainAxisSizeWithSpacings;
                                                        i55 = i76;
                                                        i74 = i74;
                                                        i73 = i77;
                                                    }
                                                    int i78 = i74;
                                                    i30 = i60 + i73;
                                                    if (i75 < 0) {
                                                        i30 += i75;
                                                        i29 = i78 + i75;
                                                        i31 = 0;
                                                    } else {
                                                        i31 = i75;
                                                        i29 = i78;
                                                    }
                                                } else {
                                                    i29 = i65;
                                                    i30 = i60;
                                                    i31 = i68;
                                                }
                                                int i79 = iMax2;
                                                float f4 = (Integer.signum(Math.round(fFloatValue)) != Integer.signum(i30) || Math.abs(Math.round(fFloatValue)) < Math.abs(i30)) ? fFloatValue : i30;
                                                float f5 = fFloatValue - f4;
                                                float f6 = (!zIsLookingAhead || i30 <= i60 || f5 > 0.0f) ? 0.0f : (i30 - i60) + f5;
                                                if (i31 < 0) {
                                                    InlineClassHelperKt.throwIllegalArgumentException("negative currentFirstItemScrollOffset");
                                                }
                                                int i80 = -i31;
                                                LazyListMeasuredItem lazyListMeasuredItem7 = (LazyListMeasuredItem) arrayDeque.first();
                                                if (i44 > 0 || iMo52roundToPx0680j_45 < 0) {
                                                    f = f6;
                                                    int size = arrayDeque.getSize();
                                                    LazyListMeasuredItem lazyListMeasuredItem8 = lazyListMeasuredItem7;
                                                    int i81 = 0;
                                                    while (i81 < size) {
                                                        int i82 = size;
                                                        int i83 = ((LazyListMeasuredItem) arrayDeque.get(i81)).mainAxisSizeWithSpacings;
                                                        if (i31 == 0 || i83 > i31 || i81 == arrayDeque.getSize() - 1) {
                                                            break;
                                                        }
                                                        i31 -= i83;
                                                        i81++;
                                                        lazyListMeasuredItem8 = (LazyListMeasuredItem) arrayDeque.get(i81);
                                                        size = i82;
                                                    }
                                                    i32 = i31;
                                                    lazyListMeasuredItem = lazyListMeasuredItem8;
                                                } else {
                                                    f = f6;
                                                    i32 = i31;
                                                    lazyListMeasuredItem = lazyListMeasuredItem7;
                                                }
                                                int i84 = i32;
                                                int iMax4 = Math.max(0, i55 - i51);
                                                int i85 = i55 - 1;
                                                if (iMax4 <= i85) {
                                                    List arrayList4 = null;
                                                    while (true) {
                                                        if (arrayList4 == null) {
                                                            arrayList4 = new ArrayList();
                                                        }
                                                        i33 = i80;
                                                        arrayList = arrayList4;
                                                        i34 = i69;
                                                        arrayList.add(LazyListMeasuredItemProvider.m156getAndMeasure0kLqBqw$default(lazyListMeasuredItemProvider2, i85));
                                                        if (i85 == iMax4) {
                                                            break;
                                                        }
                                                        i85--;
                                                        i69 = i34;
                                                        arrayList4 = arrayList;
                                                        i80 = i33;
                                                    }
                                                } else {
                                                    i33 = i80;
                                                    i34 = i69;
                                                    arrayList = null;
                                                }
                                                List list2 = listCalculateLazyLayoutPinnedIndices;
                                                int size2 = list2.size() - 1;
                                                if (size2 >= 0) {
                                                    while (true) {
                                                        int i86 = size2 - 1;
                                                        int iIntValue = ((Number) listCalculateLazyLayoutPinnedIndices.get(size2)).intValue();
                                                        if (iIntValue < iMax4) {
                                                            if (arrayList == null) {
                                                                arrayList = new ArrayList();
                                                            }
                                                            arrayList.add(LazyListMeasuredItemProvider.m156getAndMeasure0kLqBqw$default(lazyListMeasuredItemProvider2, iIntValue));
                                                        }
                                                        if (i86 < 0) {
                                                            break;
                                                        }
                                                        size2 = i86;
                                                    }
                                                }
                                                if (arrayList == null) {
                                                    arrayList = EmptyList.INSTANCE;
                                                }
                                                int size3 = arrayList.size();
                                                int iMax5 = i79;
                                                for (int i87 = 0; i87 < size3; i87++) {
                                                    iMax5 = Math.max(iMax5, ((LazyListMeasuredItem) arrayList.get(i87)).crossAxisSize);
                                                }
                                                int i88 = i49 - 1;
                                                int iMin2 = Math.min(((LazyListMeasuredItem) CollectionsKt___CollectionsKt.last(arrayDeque)).index + i51, i88);
                                                int i89 = iMax5;
                                                int i90 = ((LazyListMeasuredItem) CollectionsKt___CollectionsKt.last(arrayDeque)).index + 1;
                                                if (i90 <= iMin2) {
                                                    ArrayList arrayList5 = null;
                                                    while (true) {
                                                        if (arrayList5 == null) {
                                                            arrayList5 = new ArrayList();
                                                        }
                                                        i35 = i44;
                                                        arrayList2 = arrayList5;
                                                        f2 = f4;
                                                        arrayList2.add(LazyListMeasuredItemProvider.m156getAndMeasure0kLqBqw$default(lazyListMeasuredItemProvider2, i90));
                                                        if (i90 == iMin2) {
                                                            break;
                                                        }
                                                        i90++;
                                                        f4 = f2;
                                                        arrayList5 = arrayList2;
                                                        i44 = i35;
                                                    }
                                                } else {
                                                    f2 = f4;
                                                    i35 = i44;
                                                    arrayList2 = null;
                                                }
                                                if (!zIsLookingAhead || lazyListMeasureResult2 == null || lazyListMeasureResult2.visibleItemsInfo.isEmpty()) {
                                                    list = arrayList;
                                                    arrayList3 = arrayList2;
                                                } else {
                                                    List list3 = lazyListMeasureResult2.visibleItemsInfo;
                                                    ArrayList arrayList6 = arrayList2;
                                                    for (int size4 = list3.size() - 1; i46 < size4; size4--) {
                                                        if (((LazyListItemInfo) list3.get(size4)).getIndex() > iMin2 && (size4 == 0 || ((LazyListItemInfo) list3.get(size4 - 1)).getIndex() <= iMin2)) {
                                                            lazyListItemInfo = (LazyListItemInfo) list3.get(size4);
                                                            break;
                                                        }
                                                        i46 = -1;
                                                    }
                                                    lazyListItemInfo = null;
                                                    LazyListItemInfo lazyListItemInfo3 = (LazyListItemInfo) CollectionsKt___CollectionsKt.last(lazyListMeasureResult2.visibleItemsInfo);
                                                    if (lazyListItemInfo != null && (index = lazyListItemInfo.getIndex()) <= (iMin = Math.min(lazyListItemInfo3.getIndex(), i88))) {
                                                        arrayList3 = arrayList6;
                                                        while (true) {
                                                            if (arrayList3 != null) {
                                                                lazyListItemInfo2 = lazyListItemInfo3;
                                                                int size5 = arrayList3.size();
                                                                list = arrayList;
                                                                int i91 = 0;
                                                                while (true) {
                                                                    if (i91 >= size5) {
                                                                        obj6 = null;
                                                                        break;
                                                                    }
                                                                    obj6 = arrayList3.get(i91);
                                                                    int i92 = i91;
                                                                    if (((LazyListMeasuredItem) obj6).index == index) {
                                                                        break;
                                                                    }
                                                                    i91 = i92 + 1;
                                                                }
                                                                lazyListMeasuredItem6 = (LazyListMeasuredItem) obj6;
                                                            } else {
                                                                list = arrayList;
                                                                lazyListItemInfo2 = lazyListItemInfo3;
                                                                lazyListMeasuredItem6 = null;
                                                            }
                                                            if (lazyListMeasuredItem6 == null) {
                                                                if (arrayList3 == null) {
                                                                    arrayList3 = new ArrayList();
                                                                }
                                                                arrayList3.add(LazyListMeasuredItemProvider.m156getAndMeasure0kLqBqw$default(lazyListMeasuredItemProvider2, index));
                                                            }
                                                            if (index == iMin) {
                                                                break;
                                                            }
                                                            index++;
                                                            lazyListItemInfo3 = lazyListItemInfo2;
                                                            arrayList = list;
                                                        }
                                                    } else {
                                                        list = arrayList;
                                                        lazyListItemInfo2 = lazyListItemInfo3;
                                                        arrayList3 = arrayList6;
                                                    }
                                                    float offset = ((lazyListMeasureResult2.viewportEndOffset - lazyListItemInfo2.getOffset()) - lazyListItemInfo2.getSize()) - f2;
                                                    if (offset > 0.0f) {
                                                        int index3 = lazyListItemInfo2.getIndex() + 1;
                                                        int i93 = 0;
                                                        while (index3 < i49 && i93 < offset) {
                                                            if (index3 <= iMin2) {
                                                                int size6 = arrayDeque.getSize();
                                                                int i94 = 0;
                                                                while (true) {
                                                                    if (i94 >= size6) {
                                                                        f3 = offset;
                                                                        obj5 = null;
                                                                        break;
                                                                    }
                                                                    obj5 = arrayDeque.get(i94);
                                                                    f3 = offset;
                                                                    if (((LazyListMeasuredItem) obj5).index == index3) {
                                                                        break;
                                                                    }
                                                                    i94++;
                                                                    offset = f3;
                                                                }
                                                                lazyListMeasuredItem5 = (LazyListMeasuredItem) obj5;
                                                            } else {
                                                                f3 = offset;
                                                                if (arrayList3 != null) {
                                                                    int size7 = arrayList3.size();
                                                                    int i95 = 0;
                                                                    while (true) {
                                                                        if (i95 >= size7) {
                                                                            lazyListMeasuredItem4 = 0;
                                                                            break;
                                                                        }
                                                                        lazyListMeasuredItem4 = arrayList3.get(i95);
                                                                        int i96 = size7;
                                                                        if (((LazyListMeasuredItem) lazyListMeasuredItem4).index == index3) {
                                                                            break;
                                                                        }
                                                                        i95++;
                                                                        size7 = i96;
                                                                    }
                                                                    lazyListMeasuredItem5 = lazyListMeasuredItem4;
                                                                } else {
                                                                    lazyListMeasuredItem3 = null;
                                                                    if (lazyListMeasuredItem3 == null) {
                                                                        index3++;
                                                                        i40 = lazyListMeasuredItem3.mainAxisSizeWithSpacings;
                                                                    } else {
                                                                        if (arrayList3 == null) {
                                                                            arrayList3 = new ArrayList();
                                                                        }
                                                                        arrayList3.add(LazyListMeasuredItemProvider.m156getAndMeasure0kLqBqw$default(lazyListMeasuredItemProvider2, index3));
                                                                        index3++;
                                                                        i40 = ((LazyListMeasuredItem) CollectionsKt___CollectionsKt.last(arrayList3)).mainAxisSizeWithSpacings;
                                                                    }
                                                                    i93 += i40;
                                                                    offset = f3;
                                                                }
                                                            }
                                                            lazyListMeasuredItem3 = lazyListMeasuredItem5;
                                                            if (lazyListMeasuredItem3 == null) {
                                                            }
                                                            i93 += i40;
                                                            offset = f3;
                                                        }
                                                    }
                                                }
                                                if (arrayList3 != null && ((LazyListMeasuredItem) CollectionsKt___CollectionsKt.last(arrayList3)).index > iMin2) {
                                                    iMin2 = ((LazyListMeasuredItem) CollectionsKt___CollectionsKt.last(arrayList3)).index;
                                                }
                                                int size8 = list2.size();
                                                for (int i97 = 0; i97 < size8; i97++) {
                                                    int iIntValue2 = ((Number) listCalculateLazyLayoutPinnedIndices.get(i97)).intValue();
                                                    if (iIntValue2 > iMin2) {
                                                        if (arrayList3 == null) {
                                                            arrayList3 = new ArrayList();
                                                        }
                                                        arrayList3.add(LazyListMeasuredItemProvider.m156getAndMeasure0kLqBqw$default(lazyListMeasuredItemProvider2, iIntValue2));
                                                    }
                                                }
                                                if (arrayList3 == null) {
                                                    arrayList3 = EmptyList.INSTANCE;
                                                }
                                                int size9 = arrayList3.size();
                                                int iMax6 = i89;
                                                for (int i98 = 0; i98 < size9; i98++) {
                                                    iMax6 = Math.max(iMax6, ((LazyListMeasuredItem) arrayList3.get(i98)).crossAxisSize);
                                                }
                                                boolean z21 = Intrinsics.areEqual(lazyListMeasuredItem, arrayDeque.first()) && list.isEmpty() && arrayList3.isEmpty();
                                                int iM834constrainWidthK40F9xA2 = ConstraintsKt.m834constrainWidthK40F9xA(z19 ? iMax6 : i29, jM835offsetNN6EwU);
                                                if (z19) {
                                                    iMax6 = i29;
                                                }
                                                int iM833constrainHeightK40F9xA = ConstraintsKt.m833constrainHeightK40F9xA(iMax6, jM835offsetNN6EwU);
                                                int i99 = z19 ? iM833constrainHeightK40F9xA : iM834constrainWidthK40F9xA2;
                                                boolean z22 = i29 < Math.min(i99, i48);
                                                if (z22 && i33 != 0) {
                                                    InlineClassHelperKt.throwIllegalStateException("non-zero itemsScrollOffset");
                                                }
                                                final ArrayList arrayList7 = new ArrayList(arrayList3.size() + list.size() + arrayDeque.getSize());
                                                if (z22) {
                                                    if (!list.isEmpty() || !arrayList3.isEmpty()) {
                                                        InlineClassHelperKt.throwIllegalArgumentException("no extra items");
                                                    }
                                                    int size10 = arrayDeque.getSize();
                                                    int[] iArr = new int[size10];
                                                    int i100 = 0;
                                                    while (i100 < size10) {
                                                        if (z15) {
                                                            z8 = z21;
                                                            i39 = (size10 - i100) - 1;
                                                        } else {
                                                            z8 = z21;
                                                            i39 = i100;
                                                        }
                                                        iArr[i100] = ((LazyListMeasuredItem) arrayDeque.get(i39)).size;
                                                        i100++;
                                                        z21 = z8;
                                                    }
                                                    z7 = z21;
                                                    int[] iArr2 = new int[size10];
                                                    if (!z19) {
                                                        lazyLayoutMeasureScope = lazyLayoutMeasureScope3;
                                                        if (horizontal10 == null) {
                                                            InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("null horizontalArrangement when isVertical == false");
                                                            throw new KotlinNothingValueException();
                                                        }
                                                        i36 = i48;
                                                        function3 = function32;
                                                        i37 = i34;
                                                        j2 = jM835offsetNN6EwU;
                                                        lazyListMeasuredItem2 = lazyListMeasuredItem;
                                                        horizontal10.arrange(lazyLayoutMeasureScope, i99, iArr, LayoutDirection.Ltr, iArr2);
                                                    } else {
                                                        if (vertical16 == null) {
                                                            InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck(str);
                                                            throw new KotlinNothingValueException();
                                                        }
                                                        lazyLayoutMeasureScope = lazyLayoutMeasureScope3;
                                                        vertical16.arrange(lazyLayoutMeasureScope, i99, iArr, iArr2);
                                                        i36 = i48;
                                                        function3 = function32;
                                                        i37 = i34;
                                                        j2 = jM835offsetNN6EwU;
                                                        lazyListMeasuredItem2 = lazyListMeasuredItem;
                                                    }
                                                    IntRange indices = ArraysKt___ArraysKt.getIndices(iArr2);
                                                    IntRange intRangeReversed = indices;
                                                    if (z15) {
                                                        intRangeReversed = RangesKt___RangesKt.reversed(indices);
                                                    }
                                                    int i101 = intRangeReversed.first;
                                                    int i102 = intRangeReversed.last;
                                                    int i103 = intRangeReversed.step;
                                                    if ((i103 > 0 && i101 <= i102) || (i103 < 0 && i102 <= i101)) {
                                                        while (true) {
                                                            int i104 = iArr2[i101];
                                                            LazyListMeasuredItem lazyListMeasuredItem9 = (LazyListMeasuredItem) arrayDeque.get(!z15 ? i101 : (size10 - i101) - 1);
                                                            if (z15) {
                                                                i104 = (i99 - i104) - lazyListMeasuredItem9.size;
                                                            }
                                                            lazyListMeasuredItem9.position(i104, iM834constrainWidthK40F9xA2, iM833constrainHeightK40F9xA);
                                                            arrayList7.add(lazyListMeasuredItem9);
                                                            if (i101 == i102) {
                                                                break;
                                                            }
                                                            i101 += i103;
                                                        }
                                                    }
                                                } else {
                                                    z7 = z21;
                                                    i36 = i48;
                                                    j2 = jM835offsetNN6EwU;
                                                    function3 = function32;
                                                    i37 = i34;
                                                    lazyLayoutMeasureScope = lazyLayoutMeasureScope3;
                                                    lazyListMeasuredItem2 = lazyListMeasuredItem;
                                                    int size11 = list.size();
                                                    int i105 = i33;
                                                    int i106 = 0;
                                                    while (i106 < size11) {
                                                        List list4 = list;
                                                        LazyListMeasuredItem lazyListMeasuredItem10 = (LazyListMeasuredItem) list4.get(i106);
                                                        i105 -= lazyListMeasuredItem10.mainAxisSizeWithSpacings;
                                                        lazyListMeasuredItem10.position(i105, iM834constrainWidthK40F9xA2, iM833constrainHeightK40F9xA);
                                                        arrayList7.add(lazyListMeasuredItem10);
                                                        i106++;
                                                        list = list4;
                                                    }
                                                    int size12 = arrayDeque.getSize();
                                                    int i107 = i33;
                                                    for (int i108 = 0; i108 < size12; i108++) {
                                                        LazyListMeasuredItem lazyListMeasuredItem11 = (LazyListMeasuredItem) arrayDeque.get(i108);
                                                        lazyListMeasuredItem11.position(i107, iM834constrainWidthK40F9xA2, iM833constrainHeightK40F9xA);
                                                        arrayList7.add(lazyListMeasuredItem11);
                                                        i107 += lazyListMeasuredItem11.mainAxisSizeWithSpacings;
                                                    }
                                                    int size13 = arrayList3.size();
                                                    for (int i109 = 0; i109 < size13; i109++) {
                                                        LazyListMeasuredItem lazyListMeasuredItem12 = (LazyListMeasuredItem) arrayList3.get(i109);
                                                        lazyListMeasuredItem12.position(i107, iM834constrainWidthK40F9xA2, iM833constrainHeightK40F9xA);
                                                        arrayList7.add(lazyListMeasuredItem12);
                                                        i107 += lazyListMeasuredItem12.mainAxisSizeWithSpacings;
                                                    }
                                                }
                                                int i110 = i29;
                                                float f7 = f2;
                                                int iM834constrainWidthK40F9xA3 = iM834constrainWidthK40F9xA2;
                                                lazyLayoutItemAnimator.onMeasured((int) f7, iM834constrainWidthK40F9xA3, iM833constrainHeightK40F9xA, arrayList7, ((LazyListItemProviderImpl) lazyListItemProvider5).keyIndexMap, lazyListMeasuredItemProvider2, z19, zIsLookingAhead, 1, z17, i84, i110, coroutineScope2, graphicsContext2);
                                                lazyListMeasuredItemProvider = lazyListMeasuredItemProvider2;
                                                if (!zIsLookingAhead) {
                                                    long jM168getMinSizeToFitDisappearingItemsYbymL2g2 = lazyLayoutItemAnimator.m168getMinSizeToFitDisappearingItemsYbymL2g();
                                                    IntSize.Companion.getClass();
                                                    if (IntSize.m863equalsimpl0(jM168getMinSizeToFitDisappearingItemsYbymL2g2, 0L)) {
                                                        i38 = iM833constrainHeightK40F9xA;
                                                    } else {
                                                        int i111 = z19 ? iM833constrainHeightK40F9xA : iM834constrainWidthK40F9xA3;
                                                        long j6 = j2;
                                                        iM834constrainWidthK40F9xA3 = ConstraintsKt.m834constrainWidthK40F9xA(Math.max(iM834constrainWidthK40F9xA3, (int) (jM168getMinSizeToFitDisappearingItemsYbymL2g2 >> 32)), j6);
                                                        int iM833constrainHeightK40F9xA2 = ConstraintsKt.m833constrainHeightK40F9xA(Math.max(iM833constrainHeightK40F9xA, (int) (jM168getMinSizeToFitDisappearingItemsYbymL2g2 & 4294967295L)), j6);
                                                        int i112 = z19 ? iM833constrainHeightK40F9xA2 : iM834constrainWidthK40F9xA3;
                                                        if (i112 != i111) {
                                                            int size14 = arrayList7.size();
                                                            for (int i113 = 0; i113 < size14; i113++) {
                                                                LazyListMeasuredItem lazyListMeasuredItem13 = (LazyListMeasuredItem) arrayList7.get(i113);
                                                                lazyListMeasuredItem13.mainAxisLayoutSize = i112;
                                                                lazyListMeasuredItem13.maxMainAxisOffset = lazyListMeasuredItem13.afterContentPadding + i112;
                                                            }
                                                        }
                                                        i38 = iM833constrainHeightK40F9xA2;
                                                    }
                                                    int i114 = iM834constrainWidthK40F9xA3;
                                                    ((LazyListItemProviderImpl) lazyListItemProvider5).intervalContent.getClass();
                                                    final List listApplyStickyItems = LazyLayoutStickyItemsKt.applyStickyItems(stickyItemsPlacement, arrayList7, IntListKt.EmptyIntList, i35, i114, i38, new Function1() { // from class: androidx.compose.foundation.lazy.LazyListMeasureKt$measureLazyList$stickingItems$1
                                                        {
                                                            super(1);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function1
                                                        /* renamed from: invoke */
                                                        public final Object mo781invoke(Object obj7) {
                                                            return LazyListMeasuredItemProvider.m156getAndMeasure0kLqBqw$default(lazyListMeasuredItemProvider, ((Number) obj7).intValue());
                                                        }
                                                    });
                                                    if (z7) {
                                                        LazyListMeasuredItem lazyListMeasuredItem14 = (LazyListMeasuredItem) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList7);
                                                        numValueOf = lazyListMeasuredItem14 != null ? Integer.valueOf(lazyListMeasuredItem14.index) : null;
                                                        if (z7) {
                                                            LazyListMeasuredItem lazyListMeasuredItem15 = (LazyListMeasuredItem) arrayDeque.lastOrNull();
                                                            if (lazyListMeasuredItem15 != null) {
                                                                numValueOf2 = Integer.valueOf(lazyListMeasuredItem15.index);
                                                            }
                                                            if (i37 >= i49) {
                                                                Integer numValueOf3 = Integer.valueOf(i114);
                                                                Integer numValueOf4 = Integer.valueOf(i38);
                                                                final MutableState mutableState = lazyListState7.placementScopeInvalidator;
                                                                lazyListMeasureResult = new LazyListMeasureResult(lazyListMeasuredItem2, i84, z, f7, (MeasureResult) function3.invoke(numValueOf3, numValueOf4, new Function1() { // from class: androidx.compose.foundation.lazy.LazyListMeasureKt$measureLazyList$8
                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    /* renamed from: invoke */
                                                                    public final Object mo781invoke(Object obj7) {
                                                                        Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj7;
                                                                        final List<LazyListMeasuredItem> list5 = arrayList7;
                                                                        final List<LazyListMeasuredItem> list6 = listApplyStickyItems;
                                                                        final boolean z23 = zIsLookingAhead;
                                                                        Function1 function12 = new Function1() { // from class: androidx.compose.foundation.lazy.LazyListMeasureKt$measureLazyList$8.1
                                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                            {
                                                                                super(1);
                                                                            }

                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            /* renamed from: invoke */
                                                                            public final Object mo781invoke(Object obj8) {
                                                                                Placeable.PlacementScope placementScope2 = (Placeable.PlacementScope) obj8;
                                                                                List<LazyListMeasuredItem> list7 = list5;
                                                                                boolean z24 = z23;
                                                                                int size15 = list7.size();
                                                                                for (int i115 = 0; i115 < size15; i115++) {
                                                                                    list7.get(i115).place(placementScope2, z24);
                                                                                }
                                                                                List<LazyListMeasuredItem> list8 = list6;
                                                                                boolean z25 = z23;
                                                                                int size16 = list8.size();
                                                                                for (int i116 = 0; i116 < size16; i116++) {
                                                                                    list8.get(i116).place(placementScope2, z25);
                                                                                }
                                                                                return Unit.INSTANCE;
                                                                            }
                                                                        };
                                                                        placementScope.motionFrameOfReferencePlacement = true;
                                                                        function12.mo781invoke(placementScope);
                                                                        placementScope.motionFrameOfReferencePlacement = false;
                                                                        mutableState.getValue();
                                                                        return Unit.INSTANCE;
                                                                    }
                                                                }), f, z20, coroutineScope2, lazyLayoutMeasureScope, lazyListMeasuredItemProvider.childConstraints, LazyLayoutMeasuredItemKt.updatedVisibleItems(numValueOf == null ? numValueOf.intValue() : 0, numValueOf2 == null ? numValueOf2.intValue() : 0, arrayList7, listApplyStickyItems), i56, i62, i49, z15, !z19 ? Orientation.Vertical : Orientation.Horizontal, i45, iMo52roundToPx0680j_45, null);
                                                                lazyListKt$rememberLazyListMeasurePolicy$1$1 = this;
                                                            }
                                                        } else {
                                                            LazyListMeasuredItem lazyListMeasuredItem16 = (LazyListMeasuredItem) CollectionsKt___CollectionsKt.lastOrNull(arrayList7);
                                                            numValueOf2 = lazyListMeasuredItem16 != null ? Integer.valueOf(lazyListMeasuredItem16.index) : null;
                                                            boolean z23 = i37 >= i49 || i110 > i36;
                                                            Integer numValueOf32 = Integer.valueOf(i114);
                                                            Integer numValueOf42 = Integer.valueOf(i38);
                                                            final MutableState<Unit> mutableState2 = lazyListState7.placementScopeInvalidator;
                                                            lazyListMeasureResult = new LazyListMeasureResult(lazyListMeasuredItem2, i84, z23, f7, (MeasureResult) function3.invoke(numValueOf32, numValueOf42, new Function1() { // from class: androidx.compose.foundation.lazy.LazyListMeasureKt$measureLazyList$8
                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                {
                                                                    super(1);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function1
                                                                /* renamed from: invoke */
                                                                public final Object mo781invoke(Object obj7) {
                                                                    Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj7;
                                                                    final List<LazyListMeasuredItem> list5 = arrayList7;
                                                                    final List<LazyListMeasuredItem> list6 = listApplyStickyItems;
                                                                    final boolean z232 = zIsLookingAhead;
                                                                    Function1 function12 = new Function1() { // from class: androidx.compose.foundation.lazy.LazyListMeasureKt$measureLazyList$8.1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(1);
                                                                        }

                                                                        @Override // kotlin.jvm.functions.Function1
                                                                        /* renamed from: invoke */
                                                                        public final Object mo781invoke(Object obj8) {
                                                                            Placeable.PlacementScope placementScope2 = (Placeable.PlacementScope) obj8;
                                                                            List<LazyListMeasuredItem> list7 = list5;
                                                                            boolean z24 = z232;
                                                                            int size15 = list7.size();
                                                                            for (int i115 = 0; i115 < size15; i115++) {
                                                                                list7.get(i115).place(placementScope2, z24);
                                                                            }
                                                                            List<LazyListMeasuredItem> list8 = list6;
                                                                            boolean z25 = z232;
                                                                            int size16 = list8.size();
                                                                            for (int i116 = 0; i116 < size16; i116++) {
                                                                                list8.get(i116).place(placementScope2, z25);
                                                                            }
                                                                            return Unit.INSTANCE;
                                                                        }
                                                                    };
                                                                    placementScope.motionFrameOfReferencePlacement = true;
                                                                    function12.mo781invoke(placementScope);
                                                                    placementScope.motionFrameOfReferencePlacement = false;
                                                                    mutableState2.getValue();
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }), f, z20, coroutineScope2, lazyLayoutMeasureScope, lazyListMeasuredItemProvider.childConstraints, LazyLayoutMeasuredItemKt.updatedVisibleItems(numValueOf == null ? numValueOf.intValue() : 0, numValueOf2 == null ? numValueOf2.intValue() : 0, arrayList7, listApplyStickyItems), i56, i62, i49, z15, !z19 ? Orientation.Vertical : Orientation.Horizontal, i45, iMo52roundToPx0680j_45, null);
                                                            lazyListKt$rememberLazyListMeasurePolicy$1$1 = this;
                                                        }
                                                    } else {
                                                        LazyListMeasuredItem lazyListMeasuredItem17 = (LazyListMeasuredItem) arrayDeque.firstOrNull();
                                                        if (lazyListMeasuredItem17 != null) {
                                                            numValueOf = Integer.valueOf(lazyListMeasuredItem17.index);
                                                        }
                                                        if (z7) {
                                                        }
                                                    }
                                                }
                                            }
                                            lazyListState.applyMeasureResult$foundation_release(lazyListMeasureResult, subcomposeMeasureScope.isLookingAhead(), false);
                                            LazyListPrefetchStrategy lazyListPrefetchStrategy = lazyListState.prefetchStrategy;
                                            CacheWindowListPrefetchStrategy cacheWindowListPrefetchStrategy = lazyListPrefetchStrategy instanceof CacheWindowListPrefetchStrategy ? (CacheWindowListPrefetchStrategy) lazyListPrefetchStrategy : null;
                                            if (cacheWindowListPrefetchStrategy != null) {
                                                List list5 = lazyListMeasureResult.visibleItemsInfo;
                                                if (cacheWindowListPrefetchStrategy.prefetchWindowStartIndex != Integer.MAX_VALUE && cacheWindowListPrefetchStrategy.prefetchWindowEndIndex != Integer.MIN_VALUE && !list5.isEmpty()) {
                                                    int i115 = ((LazyListMeasuredItem) CollectionsKt___CollectionsKt.first(list5)).index;
                                                    int i116 = ((LazyListMeasuredItem) CollectionsKt___CollectionsKt.last(list5)).index;
                                                    int i117 = cacheWindowListPrefetchStrategy.prefetchWindowStartIndex;
                                                    while (true) {
                                                        lazyLayoutMeasureScope2 = lazyListMeasuredItemProvider.measureScope;
                                                        j3 = lazyListMeasuredItemProvider.childConstraints;
                                                        if (i117 >= i115) {
                                                            break;
                                                        }
                                                        ((LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope2).m170measure0kLqBqw(i117, j3);
                                                        i117++;
                                                    }
                                                    int i118 = i116 + 1;
                                                    int i119 = cacheWindowListPrefetchStrategy.prefetchWindowEndIndex;
                                                    if (i118 <= i119) {
                                                        while (true) {
                                                            ((LazyLayoutMeasureScopeImpl) lazyLayoutMeasureScope2).m170measure0kLqBqw(i118, j3);
                                                            if (i118 == i119) {
                                                                break;
                                                            }
                                                            i118++;
                                                        }
                                                    }
                                                }
                                            }
                                            return lazyListMeasureResult;
                                        } catch (Throwable th) {
                                            Snapshot.Companion.restoreNonObservable(currentThreadSnapshot, snapshotMakeCurrentNonObservable, readObserver);
                                            throw th;
                                        }
                                    }
                                };
                                vertical7 = vertical6;
                                horizontal4 = horizontal5;
                                vertical4 = vertical10;
                                lazyListState3 = lazyListState;
                                kProperty0 = kProperty02;
                                i6 = i27;
                                composerImpl.updateRememberedValue(objRememberedValue4);
                            } else {
                                vertical4 = vertical5;
                                vertical7 = vertical6;
                                horizontal4 = horizontal5;
                                composerImpl = composerImpl2;
                                obj = obj2;
                                i16 = 4;
                                lazyListState3 = lazyListState;
                                kProperty0 = kProperty02;
                            }
                            Function2 function2 = (Function2) objRememberedValue4;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            Orientation orientation = z2 ? Orientation.Vertical : Orientation.Horizontal;
                            if (z3) {
                                composerImpl.startReplaceGroup(-1513111077);
                                Modifier.Companion companion2 = Modifier.Companion;
                                int i28 = i20 | ((i15 >> 21) & 112);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.foundation.lazy.rememberLazyListBeyondBoundsState (LazyListBeyondBoundsModifier.kt:27)");
                                }
                                boolean z7 = (((i28 & 14) ^ 6) > i16 && composerImpl.changed(lazyListState3)) || (i28 & 6) == i16;
                                if ((((i28 & 112) ^ 48) <= 32 || !composerImpl.changed(i6)) && (i28 & 48) != 32) {
                                    z4 = false;
                                }
                                boolean z8 = z7 | z4;
                                Object objRememberedValue5 = composerImpl.rememberedValue();
                                if (z8 || objRememberedValue5 == obj) {
                                    objRememberedValue5 = new LazyListBeyondBoundsState(lazyListState3, i6);
                                    composerImpl.updateRememberedValue(objRememberedValue5);
                                }
                                LazyListBeyondBoundsState lazyListBeyondBoundsState = (LazyListBeyondBoundsState) objRememberedValue5;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                modifierLazyLayoutBeyondBoundsModifier = LazyLayoutBeyondBoundsModifierLocalKt.lazyLayoutBeyondBoundsModifier(companion2, lazyListBeyondBoundsState, lazyListState3.beyondBoundsInfo, z, orientation);
                                composerImpl.end(false);
                            } else {
                                composerImpl.startReplaceGroup(-1512684176);
                                composerImpl.end(false);
                                modifierLazyLayoutBeyondBoundsModifier = Modifier.Companion;
                            }
                            KProperty0 kProperty03 = kProperty0;
                            Modifier modifierThen = LazyLayoutSemanticsKt.lazyLayoutSemantics(modifier.then(lazyListState3.remeasurementModifier).then(lazyListState3.awaitLayoutModifier), kProperty0, lazyLayoutSemanticState, orientation, z3, z).then(modifierLazyLayoutBeyondBoundsModifier).then(lazyListState3.itemAnimator.modifier);
                            LazyListState lazyListState4 = lazyListState3;
                            lazyListState2 = lazyListState4;
                            LazyLayoutKt.LazyLayout(kProperty03, ScrollingContainerKt.scrollingContainer(modifierThen, lazyListState4, orientation, z3, z, flingBehavior, lazyListState4.internalInteractionSource, false, overscrollEffect, null), lazyListState2.prefetchState, function2, composerImpl, 0, 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            horizontal3 = horizontal6;
                            vertical3 = vertical7;
                        }
                    }
                } else {
                    lazyListState2 = lazyListState;
                    composerImpl = composerImpl2;
                    composerImpl.skipToGroupEnd();
                    horizontal3 = horizontal;
                    vertical3 = vertical;
                    vertical4 = vertical2;
                    horizontal4 = horizontal2;
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup != null) {
                    final LazyListState lazyListState5 = lazyListState2;
                    final int i29 = i6;
                    recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.lazy.LazyListKt.LazyList.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj3, Object obj4) {
                            ((Number) obj4).intValue();
                            LazyListKt.LazyList(modifier, lazyListState5, paddingValues, z, z2, flingBehavior, z3, overscrollEffect, i29, horizontal3, vertical3, vertical4, horizontal4, function1, (Composer) obj3, RecomposeScopeImplKt.updateChangedFlags(i2 | 1), RecomposeScopeImplKt.updateChangedFlags(i3), i4);
                            return Unit.INSTANCE;
                        }
                    };
                    return;
                }
                return;
            }
            i5 |= 12582912;
            if ((i2 & 100663296) == 0) {
            }
            i7 = i4 & 512;
            if (i7 != 0) {
            }
            i8 = i4 & 1024;
            if (i8 != 0) {
            }
            i11 = i4 & 2048;
            if (i11 != 0) {
            }
            int i182 = i10;
            i13 = i4 & 4096;
            if (i13 != 0) {
            }
            if ((i4 & 8192) != 0) {
            }
            boolean z42 = true;
            if (composerImpl2.shouldExecute(i5 & 1, ((306783379 & i5) == 306783378 && (i14 & 1171) == 1170) ? false : true)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup != null) {
            }
        }
        if ((i4 & 64) == 0) {
        }
        if ((i4 & 128) != 0) {
        }
        if ((i2 & 100663296) == 0) {
        }
        i7 = i4 & 512;
        if (i7 != 0) {
        }
        i8 = i4 & 1024;
        if (i8 != 0) {
        }
        i11 = i4 & 2048;
        if (i11 != 0) {
        }
        int i1822 = i10;
        i13 = i4 & 4096;
        if (i13 != 0) {
        }
        if ((i4 & 8192) != 0) {
        }
        boolean z422 = true;
        if (composerImpl2.shouldExecute(i5 & 1, ((306783379 & i5) == 306783378 && (i14 & 1171) == 1170) ? false : true)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
        }
    }
}
