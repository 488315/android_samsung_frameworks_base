package com.android.systemui.communal.ui.compose;

import android.appwidget.AppWidgetProviderInfo;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.SizeF;
import android.widget.RemoteViews;
import androidx.compose.animation.core.AnimateAsStateKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.grid.GridItemSpan;
import androidx.compose.foundation.lazy.grid.LazyGridIntervalContent;
import androidx.compose.foundation.lazy.grid.LazyGridItemScope;
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope;
import androidx.compose.foundation.lazy.grid.LazyGridScope;
import androidx.compose.foundation.lazy.grid.LazyGridSpanKt;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.snapshots.SnapshotStateList;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.ZIndexModifierKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.unit.DpSize;
import com.android.compose.animation.scene.ContentScope;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.model.CommunalContentSize;
import com.android.systemui.communal.ui.compose.Dimensions;
import com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import com.android.systemui.communal.ui.viewmodel.DragHandle;
import com.android.systemui.communal.ui.viewmodel.ResizeInfo;
import com.android.systemui.communal.ui.viewmodel.ResizeableItemFrameViewModel;
import com.android.systemui.communal.util.DensityUtils;
import com.android.systemui.communal.util.ResizeUtils;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalHubKt$$ExternalSyntheticLambda14 implements Function2 {
    public final /* synthetic */ Ref$ObjectRef f$0;
    public final /* synthetic */ State f$1;
    public final /* synthetic */ WidgetConfigurator f$10;
    public final /* synthetic */ BaseCommunalViewModel f$2;
    public final /* synthetic */ Ref$ObjectRef f$3;
    public final /* synthetic */ PaddingValues f$4;
    public final /* synthetic */ LazyGridState f$5;
    public final /* synthetic */ ContentListState f$6;
    public final /* synthetic */ RemoteViews.InteractionHandler f$7;
    public final /* synthetic */ CommunalAppWidgetSection f$8;
    public final /* synthetic */ ContentScope f$9;

    public /* synthetic */ CommunalHubKt$$ExternalSyntheticLambda14(Ref$ObjectRef ref$ObjectRef, State state, BaseCommunalViewModel baseCommunalViewModel, Ref$ObjectRef ref$ObjectRef2, PaddingValues paddingValues, LazyGridState lazyGridState, ContentListState contentListState, RemoteViews.InteractionHandler interactionHandler, CommunalAppWidgetSection communalAppWidgetSection, ContentScope contentScope, WidgetConfigurator widgetConfigurator) {
        this.f$0 = ref$ObjectRef;
        this.f$1 = state;
        this.f$2 = baseCommunalViewModel;
        this.f$3 = ref$ObjectRef2;
        this.f$4 = paddingValues;
        this.f$5 = lazyGridState;
        this.f$6 = contentListState;
        this.f$7 = interactionHandler;
        this.f$8 = communalAppWidgetSection;
        this.f$9 = contentScope;
        this.f$10 = widgetConfigurator;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.util.ArrayList] */
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        ?? arrayList;
        int i;
        final RemoteViews.InteractionHandler interactionHandler = this.f$7;
        LazyGridScope lazyGridScope = (LazyGridScope) obj;
        final SizeInfo sizeInfo = (SizeInfo) obj2;
        Ref$ObjectRef ref$ObjectRef = this.f$0;
        if (sizeInfo != null) {
            ResizeUtils resizeUtils = ResizeUtils.INSTANCE;
            List<CommunalContentModel> list = (List) ref$ObjectRef.element;
            int i2 = (int) (4294967295L & sizeInfo.gridSize);
            resizeUtils.getClass();
            arrayList = new ArrayList();
            List list2 = list;
            if ((list2 instanceof Collection) && list2.isEmpty()) {
                i = 0;
            } else {
                Iterator it = list2.iterator();
                i = 0;
                while (it.hasNext()) {
                    if ((((CommunalContentModel) it.next()) instanceof CommunalContentModel.Ongoing) && (i = i + 1) < 0) {
                        CollectionsKt__CollectionsKt.throwCountOverflow();
                        throw null;
                    }
                }
            }
            int i3 = i % i2;
            int i4 = i3 != 0 ? i2 - i3 : 0;
            int i5 = i2;
            for (CommunalContentModel communalContentModel : list) {
                if (communalContentModel instanceof CommunalContentModel.Ongoing) {
                    if (i5 == 0) {
                        i5 = i2;
                    }
                    int i6 = (i4 <= 0 || i5 <= 1) ? 1 : 2;
                    ((CommunalContentModel.Ongoing) communalContentModel).setSize(CommunalContentSize.Responsive.m1076boximpl(i6));
                    arrayList.add(communalContentModel);
                    i4 -= i6 - 1;
                    i5 -= i6;
                } else {
                    if (i > 0 && i5 > 0) {
                        arrayList.add(new CommunalContentModel.Spacer(CommunalContentSize.Responsive.m1076boximpl(i5)));
                    }
                    arrayList.add(communalContentModel);
                    i5 = -1;
                }
            }
        } else {
            arrayList = (List) ref$ObjectRef.element;
        }
        final List list3 = arrayList;
        final CommunalHubKt$$ExternalSyntheticLambda16 communalHubKt$$ExternalSyntheticLambda16 = new CommunalHubKt$$ExternalSyntheticLambda16();
        final Function3 function3 = new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj3, Object obj4, Object obj5) {
                int span;
                ((Integer) obj4).getClass();
                CommunalContentModel communalContentModel2 = (CommunalContentModel) obj5;
                SizeInfo sizeInfo2 = sizeInfo;
                Integer numValueOf = sizeInfo2 != null ? Integer.valueOf((int) (sizeInfo2.gridSize & 4294967295L)) : null;
                if (numValueOf != null) {
                    span = communalContentModel2.getSize().getSpan();
                    int iIntValue = numValueOf.intValue();
                    if (span > iIntValue) {
                        span = iIntValue;
                    }
                } else {
                    span = communalContentModel2.getSize().getSpan();
                }
                return GridItemSpan.m158boximpl(LazyGridSpanKt.GridItemSpan(span));
            }
        };
        int size = list3.size();
        Function1 function1 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHubLazyGrid_MGE6UKE$lambda$81$$inlined$itemsIndexed$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj3) {
                int iIntValue = ((Number) obj3).intValue();
                return communalHubKt$$ExternalSyntheticLambda16.invoke(Integer.valueOf(iIntValue), list3.get(iIntValue));
            }
        };
        Function2 function2 = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHubLazyGrid_MGE6UKE$lambda$81$$inlined$itemsIndexed$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj3, Object obj4) {
                int iIntValue = ((Number) obj4).intValue();
                return GridItemSpan.m158boximpl(((GridItemSpan) function3.invoke((LazyGridItemSpanScope) obj3, Integer.valueOf(iIntValue), list3.get(iIntValue))).packedValue);
            }
        };
        Function1 function12 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHubLazyGrid_MGE6UKE$lambda$81$$inlined$itemsIndexed$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj3) {
                return ((CommunalContentModel) list3.get(((Number) obj3).intValue())).getKey();
            }
        };
        final Ref$ObjectRef ref$ObjectRef2 = this.f$3;
        final ContentScope contentScope = this.f$9;
        final WidgetConfigurator widgetConfigurator = this.f$10;
        final State state = this.f$1;
        final BaseCommunalViewModel baseCommunalViewModel = this.f$2;
        final PaddingValues paddingValues = this.f$4;
        final LazyGridState lazyGridState = this.f$5;
        final ContentListState contentListState = this.f$6;
        final CommunalAppWidgetSection communalAppWidgetSection = this.f$8;
        ((LazyGridIntervalContent) lazyGridScope).items(size, function1, function2, function12, new ComposableLambdaImpl(1229287273, true, new Function4() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHubLazyGrid_MGE6UKE$lambda$81$$inlined$itemsIndexed$4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(4);
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:129:0x0368  */
            /* JADX WARN: Removed duplicated region for block: B:132:0x03c3  */
            /* JADX WARN: Removed duplicated region for block: B:38:0x00a4  */
            /* JADX WARN: Removed duplicated region for block: B:40:0x00c6  */
            /* JADX WARN: Removed duplicated region for block: B:46:0x0114  */
            /* JADX WARN: Removed duplicated region for block: B:49:0x0135  */
            @Override // kotlin.jvm.functions.Function4
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj3, Object obj4, Object obj5, Object obj6) {
                int i7;
                int span;
                int iIntValue;
                SizeInfo sizeInfo2;
                long jM840DpSizeYgX7TsA;
                final SizeF sizeF;
                Object objRememberedValue;
                Object obj7;
                final ResizeableItemFrameViewModel resizeableItemFrameViewModel;
                boolean z;
                ComposerImpl composerImpl;
                Dp dpM837boximpl;
                CommunalContentModel communalContentModel2;
                WidgetSizeInfo widgetSizeInfo;
                boolean z2;
                PaddingValues paddingValues2;
                float fM1084getItemSpacingD9Ej5fM;
                boolean z3;
                final LazyGridItemScope lazyGridItemScope = (LazyGridItemScope) obj3;
                final int iIntValue2 = ((Number) obj4).intValue();
                Composer composer = (Composer) obj5;
                int iIntValue3 = ((Number) obj6).intValue();
                if ((iIntValue3 & 6) == 0) {
                    i7 = (((ComposerImpl) composer).changed(lazyGridItemScope) ? 4 : 2) | iIntValue3;
                } else {
                    i7 = iIntValue3;
                }
                if ((iIntValue3 & 48) == 0) {
                    i7 |= ((ComposerImpl) composer).changed(iIntValue2) ? 32 : 16;
                }
                ComposerImpl composerImpl2 = (ComposerImpl) composer;
                if (composerImpl2.shouldExecute(i7 & 1, (i7 & 147) != 146)) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.lazy.grid.itemsIndexed.<anonymous> (LazyGridDsl.kt:579)");
                    }
                    int i8 = i7 & 126;
                    CommunalContentModel communalContentModel3 = (CommunalContentModel) list3.get(iIntValue2);
                    composerImpl2.startReplaceGroup(948617666);
                    SizeInfo sizeInfo3 = sizeInfo;
                    Integer numValueOf = sizeInfo3 != null ? Integer.valueOf((int) (sizeInfo3.gridSize & 4294967295L)) : null;
                    if (numValueOf != null) {
                        span = communalContentModel3.getSize().getSpan();
                        iIntValue = numValueOf.intValue();
                        if (span <= iIntValue) {
                        }
                        sizeInfo2 = sizeInfo;
                        if (sizeInfo2 == null) {
                            float fM847getWidthD9Ej5fM = DpSize.m847getWidthD9Ej5fM(sizeInfo2.cellSize);
                            SizeInfo sizeInfo4 = sizeInfo;
                            float fM846getHeightD9Ej5fM = iIntValue * DpSize.m846getHeightD9Ej5fM(sizeInfo4.cellSize);
                            Dp.Companion companion = Dp.Companion;
                            jM840DpSizeYgX7TsA = DpKt.m840DpSizeYgX7TsA(fM847getWidthD9Ej5fM, ((iIntValue - 1) * sizeInfo4.verticalArrangement) + fM846getHeightD9Ej5fM);
                        } else {
                            Dimensions.Companion.getClass();
                            DensityUtils.Companion.getClass();
                            jM840DpSizeYgX7TsA = DpKt.m840DpSizeYgX7TsA(DensityUtils.Companion.m1091getAdjustedDpu2uoSUM(360), CommunalHubKt.dp((CommunalContentSize.FixedSize) communalContentModel3.getSize()));
                        }
                        int i9 = iIntValue;
                        sizeF = new SizeF(DpSize.m847getWidthD9Ej5fM(jM840DpSizeYgX7TsA), DpSize.m846getHeightD9Ej5fM(jM840DpSizeYgX7TsA));
                        final boolean zAreEqual = Intrinsics.areEqual(communalContentModel3.getKey(), state.getValue());
                        boolean z4 = communalContentModel3 instanceof CommunalContentModel.WidgetContent.Widget;
                        boolean z5 = (z4 || (((CommunalContentModel.WidgetContent.Widget) communalContentModel3).providerInfo.resizeMode & 2) == 0) ? false : true;
                        String strM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iIntValue2, "ResizeableItemFrame.viewModel.");
                        Integer numValueOf2 = Integer.valueOf(i9);
                        composerImpl2.startReplaceGroup(-800657812);
                        objRememberedValue = composerImpl2.rememberedValue();
                        Composer.Companion.getClass();
                        obj7 = Composer.Companion.Empty;
                        if (objRememberedValue == obj7) {
                            objRememberedValue = new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHubLazyGrid$2$4$resizeableItemFrameViewModel$1$1
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    return new ResizeableItemFrameViewModel();
                                }
                            };
                            composerImpl2.updateRememberedValue(objRememberedValue);
                        }
                        composerImpl2.end(false);
                        resizeableItemFrameViewModel = (ResizeableItemFrameViewModel) SysUiViewModelKt.rememberViewModel(strM, numValueOf2, (Function0) objRememberedValue, composerImpl2, 384, 0);
                        if (baseCommunalViewModel.isEditMode() || ref$ObjectRef2.element == 0) {
                            z = false;
                            composerImpl2.startReplaceGroup(952852792);
                            composerImpl2.startReplaceGroup(-800546157);
                            composerImpl2.end(false);
                            long j = jM840DpSizeYgX7TsA;
                            BaseCommunalViewModel baseCommunalViewModel2 = baseCommunalViewModel;
                            Modifier.Companion companion2 = Modifier.Companion;
                            FillElement fillElement = SizeKt.FillWholeMaxWidth;
                            CommunalHubKt.CommunalContent(communalContentModel3, baseCommunalViewModel2, sizeF, false, LazyGridItemScope.animateItem$default(lazyGridItemScope, SizeKt.m137requiredSizeVpY3zN4(companion2, DpSize.m847getWidthD9Ej5fM(j), DpSize.m846getHeightD9Ej5fM(j)), null, 3), null, iIntValue2, contentListState, interactionHandler, communalAppWidgetSection, resizeableItemFrameViewModel, contentScope, composerImpl2, 3072 | ((i8 << 15) & 3670016), 32);
                            composerImpl = composerImpl2;
                            composerImpl.end(false);
                        } else {
                            composerImpl2.startReplaceGroup(949644199);
                            boolean zAreEqual2 = Intrinsics.areEqual(((GridDragDropState) ref$ObjectRef2.element).dragDropState.getDraggingItemKey(), communalContentModel3.getKey());
                            final State stateAnimateFloatAsState = AnimateAsStateKt.animateFloatAsState(zAreEqual ? 1.0f : 0.0f, AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5), "Widget resizing outline alpha", null, composerImpl2, 3120, 20);
                            SizeInfo sizeInfo5 = sizeInfo;
                            Dp dpM837boximpl2 = sizeInfo5 != null ? Dp.m837boximpl(DpSize.m846getHeightD9Ej5fM(sizeInfo5.cellSize)) : null;
                            SizeInfo sizeInfo6 = sizeInfo;
                            if (sizeInfo6 != null) {
                                PaddingValues paddingValues3 = sizeInfo6.contentPadding;
                                float fMo110calculateBottomPaddingD9Ej5fM = sizeInfo6.maxHeight - paddingValues3.mo110calculateBottomPaddingD9Ej5fM();
                                Dp.Companion companion3 = Dp.Companion;
                                dpM837boximpl = Dp.m837boximpl(fMo110calculateBottomPaddingD9Ej5fM - paddingValues3.mo113calculateTopPaddingD9Ej5fM());
                            } else {
                                dpM837boximpl = null;
                            }
                            composerImpl2.startReplaceGroup(-1510715994);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.calculateWidgetSize (CommunalHub.kt:738)");
                            }
                            Density density = (Density) composerImpl2.consume(CompositionLocalsKt.LocalDensity);
                            float fDp = dpM837boximpl2 != null ? dpM837boximpl2.value : CommunalHubKt.dp(CommunalContentSize.FixedSize.HALF);
                            float fDp2 = dpM837boximpl != null ? dpM837boximpl.value : CommunalHubKt.dp(CommunalContentSize.FixedSize.FULL);
                            if (z5 && z4) {
                                CommunalContentModel.WidgetContent.Widget widget = (CommunalContentModel.WidgetContent.Widget) communalContentModel3;
                                AppWidgetProviderInfo appWidgetProviderInfo = widget.providerInfo;
                                communalContentModel2 = communalContentModel3;
                                int iMin = Math.min(appWidgetProviderInfo.minResizeHeight, appWidgetProviderInfo.minHeight);
                                int iMo52roundToPx0680j_4 = density.mo52roundToPx0680j_4(fDp);
                                if (iMin < iMo52roundToPx0680j_4) {
                                    iMin = iMo52roundToPx0680j_4;
                                }
                                AppWidgetProviderInfo appWidgetProviderInfo2 = widget.providerInfo;
                                int i10 = appWidgetProviderInfo2.maxResizeHeight;
                                widgetSizeInfo = new WidgetSizeInfo(iMin, RangesKt___RangesKt.coerceIn(i10 > 0 ? Math.max(i10, appWidgetProviderInfo2.minHeight) : Integer.MAX_VALUE, iMin, density.mo52roundToPx0680j_4(fDp2)));
                                z2 = false;
                            } else {
                                communalContentModel2 = communalContentModel3;
                                z2 = false;
                                widgetSizeInfo = new WidgetSizeInfo(0, 0);
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl2.end(z2);
                            String key = communalContentModel2.getKey();
                            long jGridItemSpan = LazyGridSpanKt.GridItemSpan(i9);
                            SizeInfo sizeInfo7 = sizeInfo;
                            if (sizeInfo7 == null || (paddingValues2 = sizeInfo7.contentPadding) == null) {
                                paddingValues2 = paddingValues;
                            }
                            PaddingValues paddingValues4 = paddingValues2;
                            Arrangement arrangement = Arrangement.INSTANCE;
                            if (sizeInfo7 != null) {
                                fM1084getItemSpacingD9Ej5fM = sizeInfo7.verticalArrangement;
                            } else {
                                Dimensions.Companion.getClass();
                                fM1084getItemSpacingD9Ej5fM = Dimensions.Companion.m1084getItemSpacingD9Ej5fM();
                            }
                            arrangement.getClass();
                            Arrangement.SpacedAligned spacedAlignedM92spacedBy0680j_4 = Arrangement.m92spacedBy0680j_4(fM1084getItemSpacingD9Ej5fM);
                            boolean z6 = zAreEqual && !zAreEqual2;
                            Modifier.Companion companion4 = Modifier.Companion;
                            FillElement fillElement2 = SizeKt.FillWholeMaxWidth;
                            Modifier modifierM137requiredSizeVpY3zN4 = SizeKt.m137requiredSizeVpY3zN4(companion4, DpSize.m847getWidthD9Ej5fM(jM840DpSizeYgX7TsA), DpSize.m846getHeightD9Ej5fM(jM840DpSizeYgX7TsA));
                            WidgetSizeInfo widgetSizeInfo2 = widgetSizeInfo;
                            if (zAreEqual2) {
                                z3 = true;
                            } else {
                                z3 = true;
                                modifierM137requiredSizeVpY3zN4 = modifierM137requiredSizeVpY3zN4.then(LazyGridItemScope.animateItem$default(lazyGridItemScope, companion4, AnimationSpecKt.spring$default(0.0f, 400.0f, null, 5), 1));
                            }
                            if (zAreEqual2) {
                                modifierM137requiredSizeVpY3zN4 = modifierM137requiredSizeVpY3zN4.then(ZIndexModifierKt.zIndex(companion4, 1.0f));
                            }
                            Modifier modifier = modifierM137requiredSizeVpY3zN4;
                            LazyGridState lazyGridState2 = lazyGridState;
                            composerImpl2.startReplaceGroup(-800612618);
                            boolean zChanged = composerImpl2.changed(stateAnimateFloatAsState);
                            Object objRememberedValue2 = composerImpl2.rememberedValue();
                            if (zChanged || objRememberedValue2 == obj7) {
                                objRememberedValue2 = new Function0() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHubLazyGrid$2$4$3$1
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        return Float.valueOf(((Number) stateAnimateFloatAsState.getValue()).floatValue());
                                    }
                                };
                                composerImpl2.updateRememberedValue(objRememberedValue2);
                            }
                            Function0 function0 = (Function0) objRememberedValue2;
                            composerImpl2.end(false);
                            composerImpl2.startReplaceGroup(-800590526);
                            boolean zChangedInstance = composerImpl2.changedInstance(contentListState);
                            if ((((i7 & 112) ^ 48) <= 32 || !composerImpl2.changed(iIntValue2)) && (i7 & 48) != 32) {
                                z3 = false;
                            }
                            boolean z7 = z3 | zChangedInstance;
                            Object objRememberedValue3 = composerImpl2.rememberedValue();
                            if (z7 || objRememberedValue3 == obj7) {
                                final ContentListState contentListState2 = contentListState;
                                objRememberedValue3 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHubLazyGrid$2$4$4$1
                                    /* JADX WARN: Removed duplicated region for block: B:15:0x0085  */
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final Object mo781invoke(Object obj8) {
                                        Map mapEmptyMap;
                                        ResizeInfo resizeInfo = (ResizeInfo) obj8;
                                        ContentListState contentListState3 = contentListState2;
                                        SnapshotStateList snapshotStateList = contentListState3.list;
                                        int i11 = iIntValue2;
                                        CommunalContentModel communalContentModel4 = (CommunalContentModel) snapshotStateList.get(i11);
                                        int span2 = communalContentModel4.getSize().getSpan();
                                        int i12 = resizeInfo.spans + span2;
                                        if (span2 != i12 && (communalContentModel4 instanceof CommunalContentModel.WidgetContent.Widget)) {
                                            CommunalContentModel.WidgetContent.Widget widget2 = (CommunalContentModel.WidgetContent.Widget) communalContentModel4;
                                            CommunalContentSize.Companion.getClass();
                                            snapshotStateList.set(i11, new CommunalContentModel.WidgetContent.Widget(widget2.appWidgetId, widget2.rank, widget2.providerInfo, widget2.inQuietMode, CommunalContentSize.Companion.toSize(i12)));
                                            int i13 = i11 - 1;
                                            CommunalContentModel communalContentModel5 = (CommunalContentModel) CollectionsKt___CollectionsKt.getOrNull(i13, snapshotStateList);
                                            boolean z8 = resizeInfo.isExpanding;
                                            int i14 = widget2.appWidgetId;
                                            if (z8) {
                                                if (resizeInfo.fromHandle == DragHandle.TOP && (communalContentModel5 instanceof CommunalContentModel.WidgetContent.Widget)) {
                                                    SnapshotStateList snapshotStateList2 = contentListState3.list;
                                                    snapshotStateList2.add(i11, snapshotStateList2.remove(i13));
                                                    mapEmptyMap = MapsKt__MapsKt.mapOf(new Pair(Integer.valueOf(((CommunalContentModel.WidgetContent.Widget) communalContentModel5).appWidgetId), Integer.valueOf(i11)), new Pair(Integer.valueOf(i14), Integer.valueOf(i13)));
                                                } else {
                                                    mapEmptyMap = MapsKt__MapsKt.emptyMap();
                                                }
                                                Map map = mapEmptyMap;
                                                contentListState3.onResizeWidget.invoke(Integer.valueOf(i14), Integer.valueOf(i12), map, widget2.componentName, Integer.valueOf(widget2.rank));
                                            }
                                        }
                                        return Unit.INSTANCE;
                                    }
                                };
                                composerImpl2.updateRememberedValue(objRememberedValue3);
                            }
                            Function1 function13 = (Function1) objRememberedValue3;
                            composerImpl2.end(false);
                            final Ref$ObjectRef ref$ObjectRef3 = ref$ObjectRef2;
                            final BaseCommunalViewModel baseCommunalViewModel3 = baseCommunalViewModel;
                            final WidgetConfigurator widgetConfigurator2 = widgetConfigurator;
                            final ContentListState contentListState3 = contentListState;
                            final RemoteViews.InteractionHandler interactionHandler2 = interactionHandler;
                            final CommunalAppWidgetSection communalAppWidgetSection2 = communalAppWidgetSection;
                            final long j2 = jM840DpSizeYgX7TsA;
                            final CommunalContentModel communalContentModel4 = communalContentModel2;
                            CommunalHubKt.m1081ResizableItemFrameWrapperiBr3E7A(key, jGridItemSpan, lazyGridState2, paddingValues4, spacedAlignedM92spacedBy0680j_4, z6, widgetSizeInfo2.minHeightPx, widgetSizeInfo2.maxHeightPx, modifier, function0, resizeableItemFrameViewModel, function13, ComposableLambdaKt.rememberComposableLambda(902377079, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHubLazyGrid$2$4$5
                                /* JADX WARN: Multi-variable type inference failed */
                                /* JADX WARN: Removed duplicated region for block: B:15:0x0037  */
                                @Override // kotlin.jvm.functions.Function3
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj8, Object obj9, Object obj10) {
                                    Modifier modifier2 = (Modifier) obj8;
                                    Composer composer2 = (Composer) obj9;
                                    int iIntValue4 = ((Number) obj10).intValue();
                                    if ((iIntValue4 & 6) == 0) {
                                        iIntValue4 |= ((ComposerImpl) composer2).changed(modifier2) ? 4 : 2;
                                    }
                                    if ((iIntValue4 & 19) == 18) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalHubLazyGrid.<anonymous>.<anonymous>.<anonymous> (CommunalHub.kt:993)");
                                            }
                                            GridDragDropState gridDragDropState = (GridDragDropState) ref$ObjectRef3.element;
                                            CommunalContentModel communalContentModel5 = communalContentModel4;
                                            communalContentModel5.getClass();
                                            String key2 = communalContentModel5.getKey();
                                            final SizeF sizeF2 = sizeF;
                                            final RemoteViews.InteractionHandler interactionHandler3 = interactionHandler2;
                                            final CommunalAppWidgetSection communalAppWidgetSection3 = communalAppWidgetSection2;
                                            final ResizeableItemFrameViewModel resizeableItemFrameViewModel2 = resizeableItemFrameViewModel;
                                            final long j3 = j2;
                                            final boolean z8 = zAreEqual;
                                            final CommunalContentModel communalContentModel6 = communalContentModel4;
                                            final BaseCommunalViewModel baseCommunalViewModel4 = baseCommunalViewModel3;
                                            final WidgetConfigurator widgetConfigurator3 = widgetConfigurator2;
                                            int i11 = iIntValue4;
                                            final int i12 = iIntValue2;
                                            final ContentListState contentListState4 = contentListState3;
                                            GridDragDropStateKt.DraggableItem(lazyGridItemScope, gridDragDropState, key2, communalContentModel5 instanceof CommunalContentModel.WidgetContent, z8, modifier2, ComposableLambdaKt.rememberComposableLambda(1236305621, new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHubLazyGrid$2$4$5.1
                                                /* JADX WARN: Removed duplicated region for block: B:15:0x003b  */
                                                @Override // kotlin.jvm.functions.Function3
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj11, Object obj12, Object obj13) {
                                                    boolean zBooleanValue = ((Boolean) obj11).booleanValue();
                                                    Composer composer3 = (Composer) obj12;
                                                    int iIntValue5 = ((Number) obj13).intValue();
                                                    if ((iIntValue5 & 6) == 0) {
                                                        iIntValue5 |= ((ComposerImpl) composer3).changed(zBooleanValue) ? 4 : 2;
                                                    }
                                                    if ((iIntValue5 & 19) == 18) {
                                                        ComposerImpl composerImpl4 = (ComposerImpl) composer3;
                                                        if (composerImpl4.getSkipping()) {
                                                            composerImpl4.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.CommunalHubLazyGrid.<anonymous>.<anonymous>.<anonymous>.<anonymous> (CommunalHub.kt:1000)");
                                                            }
                                                            Modifier.Companion companion5 = Modifier.Companion;
                                                            FillElement fillElement3 = SizeKt.FillWholeMaxWidth;
                                                            long j4 = j3;
                                                            Modifier modifierM137requiredSizeVpY3zN42 = SizeKt.m137requiredSizeVpY3zN4(companion5, DpSize.m847getWidthD9Ej5fM(j4), DpSize.m846getHeightD9Ej5fM(j4));
                                                            CommunalHubKt.CommunalContent(communalContentModel6, baseCommunalViewModel4, sizeF2, z8 && !zBooleanValue, modifierM137requiredSizeVpY3zN42, widgetConfigurator3, i12, contentListState4, interactionHandler3, communalAppWidgetSection3, resizeableItemFrameViewModel2, null, composer3, 0, 2048);
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventEnd();
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composer2), composer2, 1572864 | ((i11 << 15) & 458752));
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl2), composerImpl2, 0);
                            composerImpl = composerImpl2;
                            composerImpl.end(false);
                            z = false;
                        }
                        composerImpl.end(z);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    } else {
                        span = communalContentModel3.getSize().getSpan();
                    }
                    iIntValue = span;
                    sizeInfo2 = sizeInfo;
                    if (sizeInfo2 == null) {
                    }
                    int i92 = iIntValue;
                    sizeF = new SizeF(DpSize.m847getWidthD9Ej5fM(jM840DpSizeYgX7TsA), DpSize.m846getHeightD9Ej5fM(jM840DpSizeYgX7TsA));
                    final boolean zAreEqual3 = Intrinsics.areEqual(communalContentModel3.getKey(), state.getValue());
                    boolean z42 = communalContentModel3 instanceof CommunalContentModel.WidgetContent.Widget;
                    if (z42) {
                        String strM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iIntValue2, "ResizeableItemFrame.viewModel.");
                        Integer numValueOf22 = Integer.valueOf(i92);
                        composerImpl2.startReplaceGroup(-800657812);
                        objRememberedValue = composerImpl2.rememberedValue();
                        Composer.Companion.getClass();
                        obj7 = Composer.Companion.Empty;
                        if (objRememberedValue == obj7) {
                        }
                        composerImpl2.end(false);
                        resizeableItemFrameViewModel = (ResizeableItemFrameViewModel) SysUiViewModelKt.rememberViewModel(strM2, numValueOf22, (Function0) objRememberedValue, composerImpl2, 384, 0);
                        if (baseCommunalViewModel.isEditMode()) {
                            z = false;
                            composerImpl2.startReplaceGroup(952852792);
                            composerImpl2.startReplaceGroup(-800546157);
                            composerImpl2.end(false);
                            long j3 = jM840DpSizeYgX7TsA;
                            BaseCommunalViewModel baseCommunalViewModel22 = baseCommunalViewModel;
                            Modifier.Companion companion22 = Modifier.Companion;
                            FillElement fillElement3 = SizeKt.FillWholeMaxWidth;
                            CommunalHubKt.CommunalContent(communalContentModel3, baseCommunalViewModel22, sizeF, false, LazyGridItemScope.animateItem$default(lazyGridItemScope, SizeKt.m137requiredSizeVpY3zN4(companion22, DpSize.m847getWidthD9Ej5fM(j3), DpSize.m846getHeightD9Ej5fM(j3)), null, 3), null, iIntValue2, contentListState, interactionHandler, communalAppWidgetSection, resizeableItemFrameViewModel, contentScope, composerImpl2, 3072 | ((i8 << 15) & 3670016), 32);
                            composerImpl = composerImpl2;
                            composerImpl.end(false);
                            composerImpl.end(z);
                            if (ComposerKt.isTraceInProgress()) {
                            }
                        }
                    }
                } else {
                    composerImpl2.skipToGroupEnd();
                }
                return Unit.INSTANCE;
            }
        }));
        return Unit.INSTANCE;
    }
}
