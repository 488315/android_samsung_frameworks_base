package androidx.compose.foundation.pager;

import androidx.compose.animation.SplineBasedFloatDecayAnimationSpec_androidKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.foundation.OverscrollEffect;
import androidx.compose.foundation.OverscrollKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.TargetedFlingBehavior;
import androidx.compose.foundation.gestures.snapping.FinalSnappingItem;
import androidx.compose.foundation.gestures.snapping.PagerSnapLayoutInfoProviderKt;
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior;
import androidx.compose.foundation.gestures.snapping.SnapFlingBehaviorKt;
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider;
import androidx.compose.foundation.gestures.snapping.SnapPosition;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.pager.MeasuredPage;
import androidx.compose.foundation.pager.PageInfo;
import androidx.compose.foundation.pager.PageSize;
import androidx.compose.foundation.pager.PagerLayoutInfoKt;
import androidx.compose.foundation.pager.PagerMeasureResult;
import androidx.compose.foundation.pager.PagerSnapDistanceMaxPages;
import androidx.compose.foundation.pager.PagerState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.IntCompanionObject;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public abstract class PagerKt {
    /* JADX WARN: Removed duplicated region for block: B:100:0x0128 A[PHI: r33
      0x0128: PHI (r33v15 int) = (r33v0 int), (r33v3 int), (r33v4 int) binds: [B:99:0x0126, B:107:0x013c, B:106:0x0139] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:102:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x0373  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0465  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x0484  */
    /* JADX WARN: Removed duplicated region for block: B:277:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x010f  */
    /* renamed from: HorizontalPager--8jOkeI, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m178HorizontalPager8jOkeI(final PagerState pagerState, Modifier modifier, PaddingValues paddingValues, PageSize pageSize, int i, float f, Alignment.Vertical vertical, TargetedFlingBehavior targetedFlingBehavior, boolean z, boolean z2, Function1 function1, NestedScrollConnection nestedScrollConnection, SnapPosition snapPosition, OverscrollEffect overscrollEffect, final Function4 function4, Composer composer, final int i2, final int i3, final int i4) {
        int i5;
        Modifier modifier2;
        int i6;
        int i7;
        PaddingValues paddingValuesM120PaddingValues0680j_4;
        int i8;
        PageSize pageSize2;
        int i9;
        boolean z3;
        int i10;
        int i11;
        int i12;
        Alignment.Vertical vertical2;
        TargetedFlingBehavior targetedFlingBehavior2;
        int i13;
        boolean z4;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        ComposerImpl composerImpl;
        final float f2;
        final Function1 function12;
        final NestedScrollConnection nestedScrollConnection2;
        final OverscrollEffect overscrollEffect2;
        final PageSize pageSize3;
        final Modifier modifier3;
        final Alignment.Vertical vertical3;
        final TargetedFlingBehavior targetedFlingBehavior3;
        final int i21;
        final PaddingValues paddingValues2;
        final boolean z5;
        final boolean z6;
        final SnapPosition snapPosition2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        float f3;
        Modifier modifier4;
        int i22;
        PageSize pageSize4;
        float f4;
        TargetedFlingBehavior targetedFlingBehavior4;
        NestedScrollConnection nestedScrollConnection3;
        int i23;
        PageSize pageSize5;
        boolean z7;
        NestedScrollConnection nestedScrollConnection4;
        OverscrollEffect overscrollEffectRememberOverscrollEffect;
        Function1 function13;
        PaddingValues paddingValues3;
        TargetedFlingBehavior targetedFlingBehavior5;
        SnapPosition snapPosition3;
        int i24;
        int i25;
        Modifier modifier5;
        Alignment.Vertical vertical4;
        float f5;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-1372972868);
        int i26 = 16;
        if ((i4 & 1) != 0) {
            i5 = i2 | 6;
        } else if ((i2 & 6) == 0) {
            i5 = (composerImpl2.changed(pagerState) ? 4 : 2) | i2;
        } else {
            i5 = i2;
        }
        int i27 = i4 & 2;
        if (i27 != 0) {
            i5 |= 48;
            modifier2 = modifier;
        } else {
            modifier2 = modifier;
            if ((i2 & 48) == 0) {
                i6 = 32;
                i5 |= composerImpl2.changed(modifier2) ? 32 : 16;
            }
            i7 = i4 & 4;
            if (i7 == 0) {
                i5 |= 384;
            } else {
                if ((i2 & 384) == 0) {
                    paddingValuesM120PaddingValues0680j_4 = paddingValues;
                    i5 |= composerImpl2.changed(paddingValuesM120PaddingValues0680j_4) ? 256 : 128;
                }
                i8 = i4 & 8;
                if (i8 != 0) {
                    i5 |= 3072;
                } else {
                    if ((i2 & 3072) == 0) {
                        pageSize2 = pageSize;
                        i5 |= composerImpl2.changed(pageSize2) ? 2048 : 1024;
                    }
                    i9 = i4 & 16;
                    if (i9 == 0) {
                        i5 |= 24576;
                        z3 = true;
                    } else {
                        z3 = true;
                        if ((i2 & 24576) == 0) {
                            i10 = i;
                            i5 |= composerImpl2.changed(i10) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
                        }
                        i11 = i4 & 32;
                        if (i11 != 0) {
                            i5 |= 196608;
                        } else if ((i2 & 196608) == 0) {
                            i5 |= composerImpl2.changed(f) ? 131072 : 65536;
                        }
                        i12 = i4 & 64;
                        if (i12 != 0) {
                            i5 |= 1572864;
                            vertical2 = vertical;
                        } else {
                            vertical2 = vertical;
                            if ((i2 & 1572864) == 0) {
                                i5 |= composerImpl2.changed(vertical2) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                            }
                        }
                        if ((i2 & 12582912) == 0) {
                            if ((i4 & 128) == 0) {
                                targetedFlingBehavior2 = targetedFlingBehavior;
                                int i28 = composerImpl2.changed(targetedFlingBehavior2) ? 8388608 : 4194304;
                                i5 |= i28;
                            } else {
                                targetedFlingBehavior2 = targetedFlingBehavior;
                            }
                            i5 |= i28;
                        } else {
                            targetedFlingBehavior2 = targetedFlingBehavior;
                        }
                        i13 = i4 & 256;
                        if (i13 != 0) {
                            i5 |= 100663296;
                            z4 = z;
                        } else {
                            z4 = z;
                            if ((i2 & 100663296) == 0) {
                                i5 |= composerImpl2.changed(z4) ? 67108864 : 33554432;
                            }
                        }
                        i14 = i4 & 512;
                        int i29 = 805306368;
                        if (i14 != 0) {
                            i5 |= i29;
                        } else if ((i2 & 805306368) == 0) {
                            i29 = composerImpl2.changed(z2) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
                            i5 |= i29;
                        }
                        i15 = i4 & 1024;
                        if (i15 != 0) {
                            i16 = i3 | 6;
                        } else if ((i3 & 6) == 0) {
                            i16 = i3 | (composerImpl2.changedInstance(function1) ? 4 : 2);
                        } else {
                            i16 = i3;
                        }
                        if ((i3 & 48) == 0) {
                            i17 = i15;
                            if ((i4 & 2048) == 0 && composerImpl2.changedInstance(nestedScrollConnection)) {
                                i26 = i6;
                            }
                            i16 |= i26;
                        } else {
                            i17 = i15;
                        }
                        i18 = i16;
                        i19 = i4 & 4096;
                        if (i19 != 0) {
                            i18 |= 384;
                            i20 = i19;
                        } else {
                            i20 = i19;
                            if ((i3 & 384) == 0) {
                                i18 |= composerImpl2.changed(snapPosition) ? 256 : 128;
                            }
                        }
                        if ((i3 & 3072) == 0) {
                            i18 |= ((i4 & 8192) == 0 && composerImpl2.changed(overscrollEffect)) ? 2048 : 1024;
                        }
                        int i30 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                        if ((i4 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
                            if ((i3 & 24576) == 0) {
                                if (!composerImpl2.changedInstance(function4)) {
                                    i30 = 8192;
                                }
                                i18 |= i30;
                            }
                            if (composerImpl2.shouldExecute(i5 & 1, ((306783379 & i5) == 306783378 || (i18 & 9363) != 9362) ? z3 : false)) {
                                composerImpl = composerImpl2;
                                composerImpl.skipToGroupEnd();
                                f2 = f;
                                function12 = function1;
                                nestedScrollConnection2 = nestedScrollConnection;
                                overscrollEffect2 = overscrollEffect;
                                pageSize3 = pageSize2;
                                modifier3 = modifier2;
                                vertical3 = vertical2;
                                targetedFlingBehavior3 = targetedFlingBehavior2;
                                i21 = i10;
                                paddingValues2 = paddingValuesM120PaddingValues0680j_4;
                                z5 = z;
                                z6 = z2;
                                snapPosition2 = snapPosition;
                            } else {
                                composerImpl2.startDefaults();
                                if ((i2 & 1) == 0 || composerImpl2.getDefaultsInvalid()) {
                                    Modifier modifier6 = i27 != 0 ? Modifier.Companion : modifier2;
                                    if (i7 != 0) {
                                        Dp.Companion companion = Dp.Companion;
                                        paddingValuesM120PaddingValues0680j_4 = PaddingKt.m120PaddingValues0680j_4(0);
                                    }
                                    if (i8 != 0) {
                                        pageSize2 = PageSize.Fill.INSTANCE;
                                    }
                                    if (i9 != 0) {
                                        i10 = 0;
                                    }
                                    if (i11 != 0) {
                                        f3 = 0;
                                        Dp.Companion companion2 = Dp.Companion;
                                    } else {
                                        f3 = f;
                                    }
                                    if (i12 != 0) {
                                        Alignment.Companion.getClass();
                                        vertical2 = Alignment.Companion.CenterVertically;
                                    }
                                    int i31 = 128 & i4;
                                    Composer.Companion companion3 = Composer.Companion;
                                    if (i31 != 0) {
                                        int i32 = (i5 & 14) | 196608;
                                        PagerDefaults.INSTANCE.getClass();
                                        PagerSnapDistance.Companion.getClass();
                                        final PagerSnapDistanceMaxPages pagerSnapDistanceMaxPages = new PagerSnapDistanceMaxPages(z3 ? 1 : 0);
                                        DecayAnimationSpec decayAnimationSpecRememberSplineBasedDecay = SplineBasedFloatDecayAnimationSpec_androidKt.rememberSplineBasedDecay(composerImpl2);
                                        int i33 = IntCompanionObject.$r8$clinit;
                                        Rect rect = VisibilityThresholdsKt.RectVisibilityThreshold;
                                        modifier4 = modifier6;
                                        i22 = i18;
                                        pageSize4 = pageSize2;
                                        SpringSpec springSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 400.0f, Float.valueOf(1), 1);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.foundation.pager.PagerDefaults.flingBehavior (Pager.kt:383)");
                                        }
                                        Density density = (Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity);
                                        final LayoutDirection layoutDirection = (LayoutDirection) composerImpl2.consume(CompositionLocalsKt.LocalLayoutDirection);
                                        f4 = f3;
                                        boolean zChanged = ((((i32 & 14) ^ 6) > 4 && composerImpl2.changed(pagerState)) || (i32 & 6) == 4) | composerImpl2.changed(decayAnimationSpecRememberSplineBasedDecay) | composerImpl2.changed(springSpecSpring$default) | composerImpl2.changed(pagerSnapDistanceMaxPages) | composerImpl2.changed(density) | composerImpl2.changed(layoutDirection);
                                        Object objRememberedValue = composerImpl2.rememberedValue();
                                        if (!zChanged) {
                                            companion3.getClass();
                                            Object obj = objRememberedValue;
                                            if (objRememberedValue == Composer.Companion.Empty) {
                                                final float f6 = 0.5f;
                                                final Function3 function3 = new Function3() { // from class: androidx.compose.foundation.pager.PagerDefaults$flingBehavior$2$snapLayoutInfoProvider$1
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(3);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function3
                                                    public final Object invoke(Object obj2, Object obj3, Object obj4) {
                                                        int i34;
                                                        float fFloatValue = ((Number) obj2).floatValue();
                                                        float fFloatValue2 = ((Number) obj3).floatValue();
                                                        float fFloatValue3 = ((Number) obj4).floatValue();
                                                        PagerState pagerState2 = pagerState;
                                                        LayoutDirection layoutDirection2 = layoutDirection;
                                                        float f7 = f6;
                                                        boolean zIsScrollingForward = PagerSnapLayoutInfoProviderKt.isScrollingForward(pagerState2, fFloatValue);
                                                        if (((PagerMeasureResult) pagerState2.getLayoutInfo()).orientation != Orientation.Vertical && layoutDirection2 != LayoutDirection.Ltr) {
                                                            zIsScrollingForward = !zIsScrollingForward;
                                                        }
                                                        int i35 = ((PagerMeasureResult) pagerState2.getLayoutInfo()).pageSize;
                                                        float fDragGestureDelta = i35 == 0 ? 0.0f : PagerSnapLayoutInfoProviderKt.dragGestureDelta(pagerState2) / i35;
                                                        float f8 = fDragGestureDelta - ((int) fDragGestureDelta);
                                                        if (Math.abs(fFloatValue) < pagerState2.density.mo58toPx0680j_4(SnapFlingBehaviorKt.MinFlingVelocityDp)) {
                                                            FinalSnappingItem.Companion.getClass();
                                                            i34 = 0;
                                                        } else if (fFloatValue > 0.0f) {
                                                            FinalSnappingItem.Companion.getClass();
                                                            i34 = FinalSnappingItem.NextItem;
                                                        } else {
                                                            FinalSnappingItem.Companion.getClass();
                                                            i34 = FinalSnappingItem.PreviousItem;
                                                        }
                                                        FinalSnappingItem.Companion.getClass();
                                                        if (i34 == 0) {
                                                            fFloatValue2 = Math.abs(f8) > f7 ? fFloatValue3 : fFloatValue3;
                                                        } else if (i34 != FinalSnappingItem.NextItem) {
                                                            if (i34 != FinalSnappingItem.PreviousItem) {
                                                                fFloatValue2 = 0.0f;
                                                            }
                                                        }
                                                        return Float.valueOf(fFloatValue2);
                                                    }
                                                };
                                                SnapLayoutInfoProvider snapLayoutInfoProvider = new SnapLayoutInfoProvider() { // from class: androidx.compose.foundation.gestures.snapping.PagerSnapLayoutInfoProviderKt$SnapLayoutInfoProvider$1
                                                    @Override // androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
                                                    public final float calculateApproachOffset(float f7, float f8) {
                                                        PagerState pagerState2 = pagerState;
                                                        int pageSize$foundation_release = ((PagerMeasureResult) ((SnapshotMutableStateImpl) pagerState2.pagerLayoutInfoState).getValue()).pageSpacing + pagerState2.getPageSize$foundation_release();
                                                        if (pageSize$foundation_release == 0) {
                                                            return 0.0f;
                                                        }
                                                        int i34 = f7 < 0.0f ? pagerState2.firstVisiblePage + 1 : pagerState2.firstVisiblePage;
                                                        int iCoerceIn = RangesKt___RangesKt.coerceIn(((int) (f8 / pageSize$foundation_release)) + i34, 0, pagerState2.getPageCount());
                                                        pagerState2.getPageSize$foundation_release();
                                                        int i35 = ((PagerMeasureResult) ((SnapshotMutableStateImpl) pagerState2.pagerLayoutInfoState).getValue()).pageSpacing;
                                                        long j = i34;
                                                        long j2 = ((PagerSnapDistanceMaxPages) pagerSnapDistanceMaxPages).pagesLimit;
                                                        long j3 = j - j2;
                                                        if (j3 < 0) {
                                                            j3 = 0;
                                                        }
                                                        int i36 = (int) j3;
                                                        long j4 = j + j2;
                                                        if (j4 > 2147483647L) {
                                                            j4 = 2147483647L;
                                                        }
                                                        int iAbs = Math.abs((RangesKt___RangesKt.coerceIn(RangesKt___RangesKt.coerceIn(iCoerceIn, i36, (int) j4), 0, pagerState2.getPageCount()) - i34) * pageSize$foundation_release) - pageSize$foundation_release;
                                                        int i37 = iAbs >= 0 ? iAbs : 0;
                                                        if (i37 == 0) {
                                                            return i37;
                                                        }
                                                        return Math.signum(f7) * i37;
                                                    }

                                                    @Override // androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
                                                    public final float calculateSnapOffset(float f7) {
                                                        PagerState pagerState2 = pagerState;
                                                        SnapPosition snapPosition4 = ((PagerMeasureResult) pagerState2.getLayoutInfo()).snapPosition;
                                                        List list = ((PagerMeasureResult) pagerState2.getLayoutInfo()).visiblePagesInfo;
                                                        int size = list.size();
                                                        float f8 = Float.NEGATIVE_INFINITY;
                                                        float f9 = Float.POSITIVE_INFINITY;
                                                        for (int i34 = 0; i34 < size; i34++) {
                                                            PageInfo pageInfo = (PageInfo) list.get(i34);
                                                            int mainAxisViewportSize = PagerLayoutInfoKt.getMainAxisViewportSize(pagerState2.getLayoutInfo());
                                                            int i35 = -((PagerMeasureResult) pagerState2.getLayoutInfo()).viewportStartOffset;
                                                            int i36 = ((PagerMeasureResult) pagerState2.getLayoutInfo()).afterContentPadding;
                                                            int i37 = ((PagerMeasureResult) pagerState2.getLayoutInfo()).pageSize;
                                                            int i38 = ((MeasuredPage) pageInfo).offset;
                                                            pagerState2.getPageCount();
                                                            float fPosition = i38 - snapPosition4.position(mainAxisViewportSize, i37, i35, i36);
                                                            if (fPosition <= 0.0f && fPosition > f8) {
                                                                f8 = fPosition;
                                                            }
                                                            if (fPosition >= 0.0f && fPosition < f9) {
                                                                f9 = fPosition;
                                                            }
                                                        }
                                                        if (f8 == Float.NEGATIVE_INFINITY) {
                                                            f8 = f9;
                                                        }
                                                        if (f9 == Float.POSITIVE_INFINITY) {
                                                            f9 = f8;
                                                        }
                                                        if (!pagerState2.getCanScrollForward()) {
                                                            if (PagerSnapLayoutInfoProviderKt.isScrollingForward(pagerState2, f7)) {
                                                                f8 = 0.0f;
                                                                f9 = 0.0f;
                                                            } else {
                                                                f9 = 0.0f;
                                                            }
                                                        }
                                                        if (!pagerState2.getCanScrollBackward()) {
                                                            f8 = 0.0f;
                                                            if (!PagerSnapLayoutInfoProviderKt.isScrollingForward(pagerState2, f7)) {
                                                                f9 = 0.0f;
                                                            }
                                                        }
                                                        Pair pair = new Pair(Float.valueOf(f8), Float.valueOf(f9));
                                                        float fFloatValue = ((Number) pair.component1()).floatValue();
                                                        float fFloatValue2 = ((Number) pair.component2()).floatValue();
                                                        float fFloatValue3 = ((Number) function3.invoke(Float.valueOf(f7), Float.valueOf(fFloatValue), Float.valueOf(fFloatValue2))).floatValue();
                                                        if (fFloatValue3 != fFloatValue && fFloatValue3 != fFloatValue2 && fFloatValue3 != 0.0f) {
                                                            InlineClassHelperKt.throwIllegalStateException("Final Snapping Offset Should Be one of " + fFloatValue + ", " + fFloatValue2 + " or 0.0");
                                                        }
                                                        if (fFloatValue3 == Float.POSITIVE_INFINITY || fFloatValue3 == Float.NEGATIVE_INFINITY) {
                                                            return 0.0f;
                                                        }
                                                        return fFloatValue3;
                                                    }
                                                };
                                                float f7 = SnapFlingBehaviorKt.MinFlingVelocityDp;
                                                Object snapFlingBehavior = new SnapFlingBehavior(snapLayoutInfoProvider, decayAnimationSpecRememberSplineBasedDecay, springSpecSpring$default);
                                                composerImpl2.updateRememberedValue(snapFlingBehavior);
                                                obj = snapFlingBehavior;
                                            }
                                            targetedFlingBehavior4 = (TargetedFlingBehavior) obj;
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            i5 &= -29360129;
                                        }
                                    } else {
                                        modifier4 = modifier6;
                                        i22 = i18;
                                        pageSize4 = pageSize2;
                                        f4 = f3;
                                        targetedFlingBehavior4 = targetedFlingBehavior2;
                                    }
                                    boolean z8 = i13 != 0 ? true : z;
                                    boolean z9 = i14 != 0 ? false : z2;
                                    Function1 function14 = i17 != 0 ? null : function1;
                                    if ((2048 & i4) != 0) {
                                        PagerDefaults pagerDefaults = PagerDefaults.INSTANCE;
                                        Orientation orientation = Orientation.Horizontal;
                                        int i34 = (i5 & 14) | 432;
                                        pagerDefaults.getClass();
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.foundation.pager.PagerDefaults.pageNestedScrollConnection (Pager.kt:432)");
                                        }
                                        boolean z10 = (((i34 & 14) ^ 6) > 4 && composerImpl2.changed(pagerState)) || (i34 & 6) == 4;
                                        Object objRememberedValue2 = composerImpl2.rememberedValue();
                                        if (!z10) {
                                            companion3.getClass();
                                            Object obj2 = objRememberedValue2;
                                            if (objRememberedValue2 == Composer.Companion.Empty) {
                                                Object defaultPagerNestedScrollConnection = new DefaultPagerNestedScrollConnection(pagerState, orientation);
                                                composerImpl2.updateRememberedValue(defaultPagerNestedScrollConnection);
                                                obj2 = defaultPagerNestedScrollConnection;
                                            }
                                            nestedScrollConnection3 = (DefaultPagerNestedScrollConnection) obj2;
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            i23 = i22 & (-113);
                                        }
                                    } else {
                                        nestedScrollConnection3 = nestedScrollConnection;
                                        i23 = i22;
                                    }
                                    SnapPosition snapPosition4 = i20 != 0 ? SnapPosition.Start.INSTANCE : snapPosition;
                                    if ((8192 & i4) != 0) {
                                        z7 = z9;
                                        nestedScrollConnection4 = nestedScrollConnection3;
                                        i18 = i23 & (-7169);
                                        overscrollEffectRememberOverscrollEffect = OverscrollKt.rememberOverscrollEffect(composerImpl2);
                                        composerImpl = composerImpl2;
                                        pageSize5 = pageSize4;
                                    } else {
                                        pageSize5 = pageSize4;
                                        z7 = z9;
                                        nestedScrollConnection4 = nestedScrollConnection3;
                                        i18 = i23;
                                        composerImpl = composerImpl2;
                                        overscrollEffectRememberOverscrollEffect = overscrollEffect;
                                    }
                                    z4 = z8;
                                    function13 = function14;
                                    paddingValues3 = paddingValuesM120PaddingValues0680j_4;
                                    targetedFlingBehavior5 = targetedFlingBehavior4;
                                    snapPosition3 = snapPosition4;
                                    i24 = i10;
                                    i25 = i5;
                                    modifier5 = modifier4;
                                    vertical4 = vertical2;
                                    f5 = f4;
                                } else {
                                    composerImpl2.skipToGroupEnd();
                                    if ((128 & i4) != 0) {
                                        i5 &= -29360129;
                                    }
                                    if ((2048 & i4) != 0) {
                                        i18 &= -113;
                                    }
                                    if ((8192 & i4) != 0) {
                                        i18 &= -7169;
                                    }
                                    z7 = z2;
                                    nestedScrollConnection4 = nestedScrollConnection;
                                    modifier5 = modifier2;
                                    targetedFlingBehavior5 = targetedFlingBehavior2;
                                    composerImpl = composerImpl2;
                                    i24 = i10;
                                    i25 = i5;
                                    paddingValues3 = paddingValuesM120PaddingValues0680j_4;
                                    function13 = function1;
                                    snapPosition3 = snapPosition;
                                    pageSize5 = pageSize2;
                                    vertical4 = vertical2;
                                    f5 = f;
                                    overscrollEffectRememberOverscrollEffect = overscrollEffect;
                                }
                                composerImpl.endDefaults();
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.foundation.pager.HorizontalPager (Pager.kt:129)");
                                }
                                int i35 = i18;
                                Orientation orientation2 = Orientation.Horizontal;
                                Alignment.Companion.getClass();
                                Modifier modifier7 = modifier5;
                                int i36 = i25 >> 6;
                                int i37 = i25 << 12;
                                int i38 = i35 << 6;
                                LazyLayoutPagerKt.m177PagereLwUrMk(modifier7, pagerState, paddingValues3, z7, orientation2, targetedFlingBehavior5, z4, overscrollEffectRememberOverscrollEffect, i24, f5, pageSize5, nestedScrollConnection4, function13, Alignment.Companion.CenterHorizontally, vertical4, snapPosition3, function4, composerImpl, ((i25 >> 3) & 14) | 24576 | ((i25 << 3) & 112) | (i25 & 896) | ((i25 >> 18) & 7168) | (i36 & 458752) | (i36 & 3670016) | ((i35 << 12) & 29360128) | (i37 & 234881024) | (i37 & 1879048192), (i38 & 896) | ((i25 >> 9) & 14) | 3072 | (i35 & 112) | (i36 & 57344) | ((i35 << 9) & 458752) | (i38 & 3670016), 0);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                int i39 = i24;
                                targetedFlingBehavior3 = targetedFlingBehavior5;
                                i21 = i39;
                                float f8 = f5;
                                z5 = z4;
                                f2 = f8;
                                Alignment.Vertical vertical5 = vertical4;
                                overscrollEffect2 = overscrollEffectRememberOverscrollEffect;
                                vertical3 = vertical5;
                                Function1 function15 = function13;
                                nestedScrollConnection2 = nestedScrollConnection4;
                                function12 = function15;
                                pageSize3 = pageSize5;
                                snapPosition2 = snapPosition3;
                                z6 = z7;
                                paddingValues2 = paddingValues3;
                                modifier3 = modifier7;
                            }
                            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                            if (recomposeScopeImplEndRestartGroup == null) {
                                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.pager.PagerKt$HorizontalPager$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public final Object invoke(Object obj3, Object obj4) {
                                        ((Number) obj4).intValue();
                                        PagerKt.m178HorizontalPager8jOkeI(pagerState, modifier3, paddingValues2, pageSize3, i21, f2, vertical3, targetedFlingBehavior3, z5, z6, function12, nestedScrollConnection2, snapPosition2, overscrollEffect2, function4, (Composer) obj3, RecomposeScopeImplKt.updateChangedFlags(i2 | 1), RecomposeScopeImplKt.updateChangedFlags(i3), i4);
                                        return Unit.INSTANCE;
                                    }
                                };
                                return;
                            }
                            return;
                        }
                        i18 |= 24576;
                        if (composerImpl2.shouldExecute(i5 & 1, ((306783379 & i5) == 306783378 || (i18 & 9363) != 9362) ? z3 : false)) {
                        }
                        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                        if (recomposeScopeImplEndRestartGroup == null) {
                        }
                    }
                    i10 = i;
                    i11 = i4 & 32;
                    if (i11 != 0) {
                    }
                    i12 = i4 & 64;
                    if (i12 != 0) {
                    }
                    if ((i2 & 12582912) == 0) {
                    }
                    i13 = i4 & 256;
                    if (i13 != 0) {
                    }
                    i14 = i4 & 512;
                    int i292 = 805306368;
                    if (i14 != 0) {
                    }
                    i15 = i4 & 1024;
                    if (i15 != 0) {
                    }
                    if ((i3 & 48) == 0) {
                    }
                    i18 = i16;
                    i19 = i4 & 4096;
                    if (i19 != 0) {
                    }
                    if ((i3 & 3072) == 0) {
                    }
                    int i302 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                    if ((i4 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
                    }
                    if (composerImpl2.shouldExecute(i5 & 1, ((306783379 & i5) == 306783378 || (i18 & 9363) != 9362) ? z3 : false)) {
                    }
                    recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                    if (recomposeScopeImplEndRestartGroup == null) {
                    }
                }
                pageSize2 = pageSize;
                i9 = i4 & 16;
                if (i9 == 0) {
                }
                i10 = i;
                i11 = i4 & 32;
                if (i11 != 0) {
                }
                i12 = i4 & 64;
                if (i12 != 0) {
                }
                if ((i2 & 12582912) == 0) {
                }
                i13 = i4 & 256;
                if (i13 != 0) {
                }
                i14 = i4 & 512;
                int i2922 = 805306368;
                if (i14 != 0) {
                }
                i15 = i4 & 1024;
                if (i15 != 0) {
                }
                if ((i3 & 48) == 0) {
                }
                i18 = i16;
                i19 = i4 & 4096;
                if (i19 != 0) {
                }
                if ((i3 & 3072) == 0) {
                }
                int i3022 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
                if ((i4 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
                }
                if (composerImpl2.shouldExecute(i5 & 1, ((306783379 & i5) == 306783378 || (i18 & 9363) != 9362) ? z3 : false)) {
                }
                recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
                if (recomposeScopeImplEndRestartGroup == null) {
                }
            }
            paddingValuesM120PaddingValues0680j_4 = paddingValues;
            i8 = i4 & 8;
            if (i8 != 0) {
            }
            pageSize2 = pageSize;
            i9 = i4 & 16;
            if (i9 == 0) {
            }
            i10 = i;
            i11 = i4 & 32;
            if (i11 != 0) {
            }
            i12 = i4 & 64;
            if (i12 != 0) {
            }
            if ((i2 & 12582912) == 0) {
            }
            i13 = i4 & 256;
            if (i13 != 0) {
            }
            i14 = i4 & 512;
            int i29222 = 805306368;
            if (i14 != 0) {
            }
            i15 = i4 & 1024;
            if (i15 != 0) {
            }
            if ((i3 & 48) == 0) {
            }
            i18 = i16;
            i19 = i4 & 4096;
            if (i19 != 0) {
            }
            if ((i3 & 3072) == 0) {
            }
            int i30222 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
            if ((i4 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
            }
            if (composerImpl2.shouldExecute(i5 & 1, ((306783379 & i5) == 306783378 || (i18 & 9363) != 9362) ? z3 : false)) {
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
            }
        }
        i6 = 32;
        i7 = i4 & 4;
        if (i7 == 0) {
        }
        paddingValuesM120PaddingValues0680j_4 = paddingValues;
        i8 = i4 & 8;
        if (i8 != 0) {
        }
        pageSize2 = pageSize;
        i9 = i4 & 16;
        if (i9 == 0) {
        }
        i10 = i;
        i11 = i4 & 32;
        if (i11 != 0) {
        }
        i12 = i4 & 64;
        if (i12 != 0) {
        }
        if ((i2 & 12582912) == 0) {
        }
        i13 = i4 & 256;
        if (i13 != 0) {
        }
        i14 = i4 & 512;
        int i292222 = 805306368;
        if (i14 != 0) {
        }
        i15 = i4 & 1024;
        if (i15 != 0) {
        }
        if ((i3 & 48) == 0) {
        }
        i18 = i16;
        i19 = i4 & 4096;
        if (i19 != 0) {
        }
        if ((i3 & 3072) == 0) {
        }
        int i302222 = NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT;
        if ((i4 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) == 0) {
        }
        if (composerImpl2.shouldExecute(i5 & 1, ((306783379 & i5) == 306783378 || (i18 & 9363) != 9362) ? z3 : false)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
