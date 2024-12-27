package com.android.systemui.qs.bar.domain.interactor;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.qs.QSPanelHost;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.bar.VideoCallMicModeBar;
import com.android.systemui.qs.bar.repository.BarOrderRepository;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class BarOrderInteractor {
    public final Executor executor;
    public QSPanelHost host;
    public BarItemImpl lastBar;
    public final BarOrderRepository repository;
    public final UserTracker userTracker;
    public final ArrayList landscapeBars = new ArrayList();
    public final ArrayList landscapeBarParents = new ArrayList();
    public final UserTracker.Callback userChanged = new UserTracker.Callback() { // from class: com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor$userChanged$1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            BarOrderInteractor.this.initValuesAndApply(context);
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public BarOrderInteractor(BarOrderRepository barOrderRepository, Executor executor, UserTracker userTracker) {
        this.repository = barOrderRepository;
        this.executor = executor;
        this.userTracker = userTracker;
    }

    public final void applyBarOrder() {
        Object valueOf;
        QSPanelHost qSPanelHost = this.host;
        if (qSPanelHost == null || !qSPanelHost.isHeader()) {
            ArrayList barViewsByOrder = getBarViewsByOrder();
            ArrayList arrayList = new ArrayList();
            BarOrderRepository barOrderRepository = this.repository;
            List filteredNonEditBars = toFilteredNonEditBars(barOrderRepository.barOrder);
            ArrayList arrayList2 = new ArrayList();
            Iterator it = ((ArrayList) filteredNonEditBars).iterator();
            while (it.hasNext()) {
                BarItemImpl barItem = toBarItem((String) it.next());
                String simpleName = barItem != null ? barItem.getClass().getSimpleName() : null;
                if (simpleName != null) {
                    arrayList2.add(simpleName);
                }
            }
            arrayList.addAll(arrayList2);
            List list = barOrderRepository.nonEditableBars;
            ArrayList arrayList3 = new ArrayList();
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                BarItemImpl barItem2 = toBarItem((String) it2.next());
                if (barItem2 != null) {
                    arrayList3.add(barItem2);
                }
            }
            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
            Iterator it3 = arrayList3.iterator();
            while (it3.hasNext()) {
                BarItemImpl barItemImpl = (BarItemImpl) it3.next();
                if (barItemImpl instanceof VideoCallMicModeBar) {
                    arrayList.add(0, barItemImpl.getClass().getSimpleName());
                    valueOf = Unit.INSTANCE;
                } else {
                    valueOf = Boolean.valueOf(arrayList.add(barItemImpl.getClass().getSimpleName()));
                }
                arrayList4.add(valueOf);
            }
            Log.d("BarOrderInteractor", "applyBarOrder " + arrayList);
            QSPanelHost qSPanelHost2 = this.host;
            if (qSPanelHost2 != null) {
                qSPanelHost2.mTargetView.removeAllViews();
                qSPanelHost2.setBarsToPanel(barViewsByOrder);
                TileChunkLayoutBar tileChunkLayoutBar = (TileChunkLayoutBar) qSPanelHost2.mBarController.getBarInExpanded(BarType.TILE_CHUNK_LAYOUT);
                if (tileChunkLayoutBar != null) {
                    tileChunkLayoutBar.mCollapsedRow = barOrderRepository.collapsedBarRow;
                }
                ArrayList barItems = qSPanelHost2.getBarItems();
                if (barItems != null) {
                    Iterator it4 = barItems.iterator();
                    while (it4.hasNext()) {
                        ((BarItemImpl) it4.next()).updateHeightMargins();
                    }
                }
            }
            updateLastShowingBar();
        }
    }

    public final ArrayList getBarViewsByOrder() {
        Object valueOf;
        ArrayList arrayList = new ArrayList();
        BarOrderRepository barOrderRepository = this.repository;
        List filteredNonEditBars = toFilteredNonEditBars(barOrderRepository.barOrder);
        ArrayList arrayList2 = new ArrayList();
        Iterator it = ((ArrayList) filteredNonEditBars).iterator();
        while (it.hasNext()) {
            BarItemImpl barItem = toBarItem((String) it.next());
            View view = barItem != null ? barItem.mBarRootView : null;
            if (view != null) {
                arrayList2.add(view);
            }
        }
        arrayList.addAll(arrayList2);
        List list = barOrderRepository.nonEditableBars;
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            BarItemImpl barItem2 = toBarItem((String) it2.next());
            if (barItem2 != null) {
                arrayList3.add(barItem2);
            }
        }
        ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            BarItemImpl barItemImpl = (BarItemImpl) it3.next();
            if (barItemImpl instanceof VideoCallMicModeBar) {
                arrayList.add(0, ((VideoCallMicModeBar) barItemImpl).mBarRootView);
                valueOf = Unit.INSTANCE;
            } else {
                valueOf = Boolean.valueOf(arrayList.add(barItemImpl.mBarRootView));
            }
            arrayList4.add(valueOf);
        }
        return arrayList;
    }

    public final void initValuesAndApply(Context context) {
        BarOrderRepository barOrderRepository = this.repository;
        barOrderRepository.getClass();
        Log.d("BarOrderRepository", "initValues()");
        barOrderRepository.setBarOrder(CollectionsKt___CollectionsKt.toList(StringsKt__StringsKt.split$default(barOrderRepository.loadBarOrder(), new String[]{","}, 0, 6)));
        barOrderRepository.setCollapsedBarRow(barOrderRepository.loadCollapsedBarRow());
        if (context.getResources().getConfiguration().orientation != 2) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor$initValuesAndApply$1
                @Override // java.lang.Runnable
                public final void run() {
                    BarOrderInteractor.this.applyBarOrder();
                }
            });
        }
    }

    public final BarItemImpl toBarItem(String str) {
        ArrayList barItems;
        QSPanelHost qSPanelHost = this.host;
        Object obj = null;
        if (qSPanelHost == null || (barItems = qSPanelHost.getBarItems()) == null) {
            return null;
        }
        Iterator it = barItems.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            if (((BarItemImpl) next).getClass().getSimpleName().equals(str)) {
                obj = next;
                break;
            }
        }
        return (BarItemImpl) obj;
    }

    public final List toFilteredNonEditBars(List list) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!this.repository.nonEditableBars.contains((String) obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final void updateLastShowingBar() {
        Object obj;
        List filteredNonEditBars = toFilteredNonEditBars(this.repository.barOrder);
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) filteredNonEditBars).iterator();
        while (it.hasNext()) {
            BarItemImpl barItem = toBarItem((String) it.next());
            if (barItem != null) {
                arrayList.add(barItem);
            }
        }
        ListIterator listIterator = arrayList.listIterator(arrayList.size());
        while (true) {
            if (!listIterator.hasPrevious()) {
                obj = null;
                break;
            }
            obj = listIterator.previous();
            BarItemImpl barItemImpl = (BarItemImpl) obj;
            if (barItemImpl.isAvailable() && barItemImpl.mShowing && barItemImpl.mBarRootView.getVisibility() == 0) {
                break;
            }
        }
        BarItemImpl barItemImpl2 = (BarItemImpl) obj;
        if (barItemImpl2 == null) {
            return;
        }
        BarItemImpl barItemImpl3 = this.lastBar;
        if (barItemImpl3 == null || !barItemImpl3.equals(barItemImpl2)) {
            BarItemImpl barItemImpl4 = this.lastBar;
            Integer valueOf = barItemImpl4 != null ? Integer.valueOf(barItemImpl4.orignBottomMargin()) : null;
            if (this.lastBar != null) {
                int intValue = valueOf != null ? valueOf.intValue() : 0;
                BarItemImpl barItemImpl5 = this.lastBar;
                View view = barItemImpl5 != null ? barItemImpl5.mBarRootView : null;
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) (view != null ? view.getLayoutParams() : null);
                if ((layoutParams == null || layoutParams.bottomMargin != intValue) && layoutParams != null) {
                    layoutParams.bottomMargin = intValue;
                }
            }
            this.lastBar = barItemImpl2;
        }
        BarItemImpl barItemImpl6 = this.lastBar;
        View view2 = barItemImpl6 != null ? barItemImpl6.mBarRootView : null;
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) (view2 != null ? view2.getLayoutParams() : null);
        if ((layoutParams2 == null || layoutParams2.bottomMargin != 0) && layoutParams2 != null) {
            layoutParams2.bottomMargin = 0;
        }
    }
}
