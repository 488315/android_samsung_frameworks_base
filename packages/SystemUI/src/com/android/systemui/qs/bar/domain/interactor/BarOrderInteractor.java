package com.android.systemui.qs.bar.domain.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.QSPanelHost;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.bar.VideoCallMicModeBar;
import com.android.systemui.qs.bar.repository.BarOrderRepository;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BarOrderInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Executor executor;
    public QSPanelHost host;
    public BarItemImpl lastBar;
    public final BarOrderRepository repository;
    public final Context systemUIContext;
    public final UserTracker userTracker;
    public final ArrayList landscapeBars = new ArrayList();
    public final ArrayList landscapeBarParents = new ArrayList();
    public final UserTracker.Callback userChanged = new UserTracker.Callback() { // from class: com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor$userChanged$1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            int i2 = BarOrderInteractor.$r8$clinit;
            BarOrderInteractor.this.initValuesAndApply(context, true);
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public BarOrderInteractor(BarOrderRepository barOrderRepository, Executor executor, UserTracker userTracker, Context context) {
        this.repository = barOrderRepository;
        this.executor = executor;
        this.userTracker = userTracker;
        this.systemUIContext = context;
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
            ArrayList arrayList3 = (ArrayList) filteredNonEditBars;
            int size = arrayList3.size();
            int i = 0;
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList3.get(i2);
                i2++;
                BarItemImpl barItem = toBarItem((String) obj);
                String simpleName = barItem != null ? barItem.getClass().getSimpleName() : null;
                if (simpleName != null) {
                    arrayList2.add(simpleName);
                }
            }
            arrayList.addAll(arrayList2);
            List list = barOrderRepository.nonEditableBars;
            ArrayList arrayList4 = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                BarItemImpl barItem2 = toBarItem((String) it.next());
                if (barItem2 != null) {
                    arrayList4.add(barItem2);
                }
            }
            ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList4, 10));
            int size2 = arrayList4.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList4.get(i3);
                i3++;
                BarItemImpl barItemImpl = (BarItemImpl) obj2;
                if (barItemImpl instanceof VideoCallMicModeBar) {
                    arrayList.add(0, barItemImpl.getClass().getSimpleName());
                    valueOf = Unit.INSTANCE;
                } else {
                    valueOf = Boolean.valueOf(arrayList.add(barItemImpl.getClass().getSimpleName()));
                }
                arrayList5.add(valueOf);
            }
            Log.d("BarOrderInteractor", "applyBarOrder " + arrayList);
            QSPanelHost qSPanelHost2 = this.host;
            if (qSPanelHost2 != null) {
                qSPanelHost2.mTargetView.removeAllViews();
                qSPanelHost2.setBarsToPanel(barViewsByOrder);
                TileChunkLayoutBar tileChunkLayoutBar = (TileChunkLayoutBar) qSPanelHost2.mBarController.getBarInExpanded(BarType.TILE_CHUNK_LAYOUT);
                if (tileChunkLayoutBar != null) {
                    tileChunkLayoutBar.mCollapsedRowSettingValue = barOrderRepository.collapsedBarRow;
                }
                ArrayList barItems = qSPanelHost2.getBarItems();
                if (barItems != null) {
                    int size3 = barItems.size();
                    while (i < size3) {
                        Object obj3 = barItems.get(i);
                        i++;
                        ((BarItemImpl) obj3).updateHeightMargins();
                    }
                }
            }
            updateLastShowingBar();
        }
    }

    public final void flushBarParent() {
        ArrayList arrayList = this.landscapeBarParents;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            View view = (View) obj;
            LinearLayout linearLayout = view instanceof LinearLayout ? (LinearLayout) view : null;
            if (linearLayout != null) {
                linearLayout.removeAllViews();
            }
        }
        arrayList.clear();
        this.landscapeBars.clear();
    }

    public final ArrayList getBarViewsByOrder() {
        Object valueOf;
        ArrayList arrayList = new ArrayList();
        BarOrderRepository barOrderRepository = this.repository;
        List filteredNonEditBars = toFilteredNonEditBars(barOrderRepository.barOrder);
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = (ArrayList) filteredNonEditBars;
        int size = arrayList3.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList3.get(i);
            i++;
            BarItemImpl barItem = toBarItem((String) obj);
            View view = barItem != null ? barItem.mBarRootView : null;
            if (view != null) {
                arrayList2.add(view);
            }
        }
        arrayList.addAll(arrayList2);
        List list = barOrderRepository.nonEditableBars;
        ArrayList arrayList4 = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            BarItemImpl barItem2 = toBarItem((String) it.next());
            if (barItem2 != null) {
                arrayList4.add(barItem2);
            }
        }
        ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList4, 10));
        int size2 = arrayList4.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = arrayList4.get(i2);
            i2++;
            BarItemImpl barItemImpl = (BarItemImpl) obj2;
            if (barItemImpl instanceof VideoCallMicModeBar) {
                arrayList.add(0, ((VideoCallMicModeBar) barItemImpl).mBarRootView);
                valueOf = Unit.INSTANCE;
            } else {
                valueOf = Boolean.valueOf(arrayList.add(barItemImpl.mBarRootView));
            }
            arrayList5.add(valueOf);
        }
        return arrayList;
    }

    public final void initValuesAndApply(final Context context, boolean z) {
        if (z) {
            BarOrderRepository barOrderRepository = this.repository;
            barOrderRepository.getClass();
            Log.d("BarOrderRepository", "initValues()");
            barOrderRepository.setBarOrder(CollectionsKt___CollectionsKt.toList(StringsKt__StringsKt.split$default(barOrderRepository.loadBarOrder(), new String[]{","}, 0, 6)));
            barOrderRepository.setCollapsedBarRow(barOrderRepository.loadCollapsedBarRow());
            sendOrderStatusLog();
            sendCollapsedRowStatusLog();
        }
        if (context.getResources().getConfiguration().orientation != 2 || QpRune.QUICK_TABLET || ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor$initValuesAndApply$1
                @Override // java.lang.Runnable
                public final void run() {
                    BarOrderInteractor.this.applyBarOrder();
                }
            });
        } else {
            flushBarParent();
            this.executor.execute(new Runnable() { // from class: com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor$initValuesAndApply$2
                @Override // java.lang.Runnable
                public final void run() {
                    BarOrderInteractor.this.makeLandscapeView(context);
                }
            });
        }
    }

    public final void makeLandscapeView(Context context) {
        LinearLayout linearLayout;
        View view;
        Log.d("BarOrderInteractor", "makeLandscapeView");
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.large_tile_between_margin);
        QSPanelHost qSPanelHost = this.host;
        if (qSPanelHost != null) {
            BarController barController = qSPanelHost.mBarController;
            BarType barType = BarType.TILE_CHUNK_LAYOUT;
            TileChunkLayoutBar tileChunkLayoutBar = (TileChunkLayoutBar) barController.getBarInExpanded(barType);
            if (tileChunkLayoutBar != null) {
                tileChunkLayoutBar.mCollapsedRowSettingValue = 1;
            }
            TileChunkLayoutBar tileChunkLayoutBar2 = (TileChunkLayoutBar) qSPanelHost.mBarController.getBarInExpanded(barType);
            if (tileChunkLayoutBar2 != null) {
                tileChunkLayoutBar2.updateHeightMargins();
            }
            ArrayList barItems = qSPanelHost.getBarItems();
            if (barItems != null) {
                ArrayList arrayList = new ArrayList();
                int size = barItems.size();
                int i = 0;
                while (i < size) {
                    Object obj = barItems.get(i);
                    i++;
                    BarItemImpl barItemImpl = (BarItemImpl) obj;
                    if ((barItemImpl != null && (view = barItemImpl.mBarRootView) != null && view.getVisibility() == 0) || (barItemImpl != null && barItemImpl.getBarWidthWeight(context) == 4)) {
                        arrayList.add(obj);
                    }
                }
                int size2 = arrayList.size();
                LinearLayout linearLayout2 = null;
                int i2 = 0;
                int i3 = 0;
                while (i3 < size2) {
                    Object obj2 = arrayList.get(i3);
                    i3++;
                    BarItemImpl barItemImpl2 = (BarItemImpl) obj2;
                    int barWidthWeight = barItemImpl2.getBarWidthWeight(context);
                    if (barWidthWeight == 4) {
                        this.landscapeBars.add(barItemImpl2.mBarRootView);
                    } else {
                        if (i2 == 0 || i2 + barWidthWeight > 4) {
                            linearLayout = new LinearLayout(context);
                            linearLayout.setOrientation(0);
                            linearLayout.setWeightSum(4.0f);
                            linearLayout.setTag("expand_anim");
                            this.landscapeBarParents.add(linearLayout);
                            this.landscapeBars.add(linearLayout);
                        } else {
                            linearLayout = linearLayout2;
                        }
                        if (linearLayout != null) {
                            linearLayout.addView(barItemImpl2.mBarRootView);
                            linearLayout2 = linearLayout;
                        }
                        if (i2 == 0 || (i2 = i2 + barWidthWeight) > 4) {
                            i2 = barWidthWeight;
                        }
                        View view2 = barItemImpl2.mBarRootView;
                        ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                        LinearLayout.LayoutParams layoutParams2 = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : null;
                        if (layoutParams2 != null) {
                            if (i2 < 4) {
                                layoutParams2.setMarginEnd(dimensionPixelSize);
                            }
                            layoutParams2.weight = barWidthWeight;
                            layoutParams2.width = 0;
                            layoutParams2.bottomMargin = 0;
                            view2.setLayoutParams(layoutParams2);
                        }
                    }
                }
            }
            qSPanelHost.setBarsToPanel(this.landscapeBars);
        }
    }

    public final void sendCollapsedRowStatusLog() {
        SharedPreferences.Editor edit = this.systemUIContext.getSharedPreferences(SystemUIAnalytics.QUICK_PREF_NAME, 0).edit();
        String m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(this.repository.collapsedBarRow, "Number, ");
        if (edit != null) {
            edit.putString(SystemUIAnalytics.STATUS_QUICK_PANEL_COLLAPSED_ROW, m);
            edit.apply();
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    public final void sendOrderStatusLog() {
        int i = 0;
        SharedPreferences.Editor edit = this.systemUIContext.getSharedPreferences(SystemUIAnalytics.QUICK_PREF_NAME, 0).edit();
        Iterator it = ((ArrayList) toFilteredNonEditBars(this.repository.barOrder)).iterator();
        String str = "";
        while (it.hasNext()) {
            Object next = it.next();
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            String str2 = (String) next;
            switch (str2.hashCode()) {
                case -1967680040:
                    if (str2.equals("SmartViewLargeTileBar")) {
                        str2 = "Smart View, Modes";
                        break;
                    }
                    break;
                case -385434990:
                    if (str2.equals("MultiSIMPreferredSlotBar")) {
                        str2 = "Multi sim";
                        break;
                    }
                    break;
                case -208127222:
                    if (str2.equals("TileChunkLayoutBar")) {
                        str2 = "Full quick settings chunk";
                        break;
                    }
                    break;
                case -120728417:
                    if (str2.equals("TopLargeTileBar")) {
                        str2 = "Wi-Fi, Bluetooth";
                        break;
                    }
                    break;
                case 898905264:
                    if (str2.equals("QSMediaPlayerBar")) {
                        str2 = "Media player";
                        break;
                    }
                    break;
                case 1175330997:
                    if (str2.equals("BottomLargeTileBar")) {
                        str2 = "Nearby devices, Device control";
                        break;
                    }
                    break;
                case 1319156515:
                    if (str2.equals("QuickControlBar")) {
                        str2 = "Remote control";
                        break;
                    }
                    break;
                case 1570149672:
                    if (str2.equals("BrightnessVolumeBar")) {
                        str2 = "Brightness, Sound";
                        break;
                    }
                    break;
            }
            str = ((Object) str) + "rowName, " + str2 + ", position, " + i2;
            if (i < r7.size() - 1) {
                str = ((Object) str) + ", ";
            }
            i = i2;
        }
        if (edit != null) {
            edit.putString(SystemUIAnalytics.STATUS_QUICK_PANEL_LAYOUT, str);
            edit.apply();
        }
    }

    public final BarItemImpl toBarItem(String str) {
        ArrayList barItems;
        QSPanelHost qSPanelHost = this.host;
        Object obj = null;
        if (qSPanelHost == null || (barItems = qSPanelHost.getBarItems()) == null) {
            return null;
        }
        int size = barItems.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            Object obj2 = barItems.get(i);
            i++;
            if (((BarItemImpl) obj2).getClass().getSimpleName().equals(str)) {
                obj = obj2;
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
        View view;
        List filteredNonEditBars = toFilteredNonEditBars(this.repository.barOrder);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) filteredNonEditBars;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList2.get(i);
            i++;
            BarItemImpl barItem = toBarItem((String) obj2);
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
            if (barItemImpl.isAvailable() && barItemImpl.mShowing && (view = barItemImpl.mBarRootView) != null && view.getVisibility() == 0) {
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
                View view2 = barItemImpl5 != null ? barItemImpl5.mBarRootView : null;
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) (view2 != null ? view2.getLayoutParams() : null);
                if ((layoutParams == null || layoutParams.bottomMargin != intValue) && layoutParams != null) {
                    layoutParams.bottomMargin = intValue;
                }
            }
            this.lastBar = barItemImpl2;
        }
        BarItemImpl barItemImpl6 = this.lastBar;
        View view3 = barItemImpl6 != null ? barItemImpl6.mBarRootView : null;
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) (view3 != null ? view3.getLayoutParams() : null);
        if ((layoutParams2 == null || layoutParams2.bottomMargin != 0) && layoutParams2 != null) {
            layoutParams2.bottomMargin = 0;
        }
    }
}
