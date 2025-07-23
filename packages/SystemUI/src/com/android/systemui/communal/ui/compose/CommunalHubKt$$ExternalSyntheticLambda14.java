package com.android.systemui.communal.ui.compose;

import android.widget.RemoteViews;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.foundation.lazy.grid.GridItemSpan;
import androidx.compose.foundation.lazy.grid.LazyGridIntervalContent;
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope;
import androidx.compose.foundation.lazy.grid.LazyGridScope;
import androidx.compose.foundation.lazy.grid.LazyGridSpanKt;
import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import com.android.compose.animation.scene.ContentScope;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.model.CommunalContentSize;
import com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import com.android.systemui.communal.util.ResizeUtils;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        ?? r3;
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
            r3 = new ArrayList();
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
                    ((CommunalContentModel.Ongoing) communalContentModel).setSize(CommunalContentSize.Responsive.m1074boximpl(i6));
                    r3.add(communalContentModel);
                    i4 -= i6 - 1;
                    i5 -= i6;
                } else {
                    if (i > 0 && i5 > 0) {
                        r3.add(new CommunalContentModel.Spacer(CommunalContentSize.Responsive.m1074boximpl(i5)));
                    }
                    r3.add(communalContentModel);
                    i5 = -1;
                }
            }
        } else {
            r3 = (List) ref$ObjectRef.element;
        }
        final List list3 = r3;
        final CommunalHubKt$$ExternalSyntheticLambda16 communalHubKt$$ExternalSyntheticLambda16 = new CommunalHubKt$$ExternalSyntheticLambda16();
        final Function3 function3 = new Function3() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj3, Object obj4, Object obj5) {
                int span;
                ((Integer) obj4).getClass();
                CommunalContentModel communalContentModel2 = (CommunalContentModel) obj5;
                SizeInfo sizeInfo2 = SizeInfo.this;
                Integer valueOf = sizeInfo2 != null ? Integer.valueOf((int) (sizeInfo2.gridSize & 4294967295L)) : null;
                if (valueOf != null) {
                    span = communalContentModel2.getSize().getSpan();
                    int intValue = valueOf.intValue();
                    if (span > intValue) {
                        span = intValue;
                    }
                } else {
                    span = communalContentModel2.getSize().getSpan();
                }
                return GridItemSpan.m157boximpl(LazyGridSpanKt.GridItemSpan(span));
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
            public final Object mo779invoke(Object obj3) {
                int intValue = ((Number) obj3).intValue();
                return Function2.this.invoke(Integer.valueOf(intValue), list3.get(intValue));
            }
        };
        Function2 function2 = new Function2() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHubLazyGrid_MGE6UKE$lambda$81$$inlined$itemsIndexed$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj3, Object obj4) {
                int intValue = ((Number) obj4).intValue();
                return GridItemSpan.m157boximpl(((GridItemSpan) Function3.this.invoke((LazyGridItemSpanScope) obj3, Integer.valueOf(intValue), list3.get(intValue))).packedValue);
            }
        };
        Function1 function12 = new Function1() { // from class: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHubLazyGrid_MGE6UKE$lambda$81$$inlined$itemsIndexed$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj3) {
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

            /* JADX WARN: Code restructure failed: missing block: B:27:0x0092, code lost:
            
                if (r13 > r10) goto L36;
             */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function4
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object invoke(java.lang.Object r30, java.lang.Object r31, java.lang.Object r32, java.lang.Object r33) {
                /*
                    Method dump skipped, instructions count: 974
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.CommunalHubKt$CommunalHubLazyGrid_MGE6UKE$lambda$81$$inlined$itemsIndexed$4.invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
            }
        }));
        return Unit.INSTANCE;
    }
}
