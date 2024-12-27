package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.BarItemImpl;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.TileChunkLayoutBar;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ConfigurationState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntSupplier;
import javax.inject.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecQSPanelController extends SecQSPanelControllerBase {
    public final BarOrderInteractor mBarOrderInteractor;
    public boolean mGridContentVisible;
    public final HideRemovableTileHelper mHideRemovableTileHelper;
    public final ConfigurationState mLastConfigurationState;
    public int mOrientation;
    public final QSCMainViewController mQSCMainViewController;
    public SecQSImplAnimatorManager mSecAnimatorManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class HideRemovableTileHelper implements TunerService.Tunable {
        public /* synthetic */ HideRemovableTileHelper(SecQSPanelController secQSPanelController, int i) {
            this();
        }

        @Override // com.android.systemui.tuner.TunerService.Tunable
        public final void onTuningChanged(String str, String str2) {
            QSPanelHost qSPanelHost;
            if (QpRune.QUICK_TILE_HIDE_FROM_BAR && (qSPanelHost = SecQSPanelController.this.mQsPanelHost) != null) {
                qSPanelHost.setTiles();
                qSPanelHost.mQsHost.getQQSTileHost().mQSTileHost.refreshTileList();
            }
        }

        private HideRemovableTileHelper() {
        }
    }

    public SecQSPanelController(SecQSPanel secQSPanel, QSHost qSHost, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, DumpManager dumpManager, QSPanelHost qSPanelHost, Provider provider, SecQSPanelResourcePicker secQSPanelResourcePicker, StatusBarKeyguardViewManager statusBarKeyguardViewManager, QSCMainViewController qSCMainViewController, BarOrderInteractor barOrderInteractor, MediaHost mediaHost, SecQSDetailController secQSDetailController) {
        super(secQSPanel, qSHost, metricsLogger, uiEventLogger, qSLogger, dumpManager, qSPanelHost, (BarController) provider.get(), secQSPanelResourcePicker, mediaHost, secQSDetailController);
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DISPLAY_DEVICE_TYPE, ConfigurationState.ConfigurationField.UI_MODE, ConfigurationState.ConfigurationField.DENSITY_DPI));
        this.mGridContentVisible = true;
        this.mHideRemovableTileHelper = new HideRemovableTileHelper(this, 0);
        this.mQSCMainViewController = qSCMainViewController;
        qSCMainViewController.panelHost = qSPanelHost;
        secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().qsPanelController = this;
        this.mBarOrderInteractor = barOrderInteractor;
        barOrderInteractor.host = qSPanelHost;
        qSPanelHost.resetBarSettingsRunnable = new SecQSPanelController$$ExternalSyntheticLambda0(barOrderInteractor, 0);
        secQSDetailController.panelController = this;
        this.mQsPanelHost.mOrientationSupplier = new IntSupplier() { // from class: com.android.systemui.qs.SecQSPanelController$$ExternalSyntheticLambda1
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                SecQSPanelController secQSPanelController = SecQSPanelController.this;
                int i = secQSPanelController.mOrientation;
                return i != 0 ? i : secQSPanelController.getContext().getResources().getConfiguration().orientation;
            }
        };
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final void addBarItems() {
        LinearLayout linearLayout;
        View view;
        super.addBarItems();
        this.mQsPanelHost.mTargetView.removeAllViews();
        BarOrderInteractor barOrderInteractor = this.mBarOrderInteractor;
        ArrayList arrayList = barOrderInteractor.landscapeBarParents;
        Iterator it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            View view2 = (View) it.next();
            LinearLayout linearLayout2 = view2 instanceof LinearLayout ? (LinearLayout) view2 : null;
            if (linearLayout2 != null) {
                linearLayout2.removeAllViews();
            }
        }
        arrayList.clear();
        barOrderInteractor.landscapeBars.clear();
        if (getContext().getResources().getConfiguration().orientation != 2 || QpRune.QUICK_TABLET) {
            barOrderInteractor.applyBarOrder();
            return;
        }
        Context context = getContext();
        Log.d("BarOrderInteractor", "makeLandscapeView");
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.large_tile_between_margin);
        QSPanelHost qSPanelHost = barOrderInteractor.host;
        if (qSPanelHost != null) {
            BarController barController = qSPanelHost.mBarController;
            BarType barType = BarType.TILE_CHUNK_LAYOUT;
            TileChunkLayoutBar tileChunkLayoutBar = (TileChunkLayoutBar) barController.getBarInExpanded(barType);
            if (tileChunkLayoutBar != null) {
                tileChunkLayoutBar.mCollapsedRow = 1;
            }
            TileChunkLayoutBar tileChunkLayoutBar2 = (TileChunkLayoutBar) qSPanelHost.mBarController.getBarInExpanded(barType);
            if (tileChunkLayoutBar2 != null) {
                tileChunkLayoutBar2.updateHeightMargins();
            }
            ArrayList barItems = qSPanelHost.getBarItems();
            if (barItems != null) {
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : barItems) {
                    BarItemImpl barItemImpl = (BarItemImpl) obj;
                    if ((barItemImpl != null && (view = barItemImpl.mBarRootView) != null && view.getVisibility() == 0) || (barItemImpl != null && barItemImpl.getBarWidthWeight(context) == 4)) {
                        arrayList2.add(obj);
                    }
                }
                Iterator it2 = arrayList2.iterator();
                LinearLayout linearLayout3 = null;
                int i = 0;
                while (it2.hasNext()) {
                    BarItemImpl barItemImpl2 = (BarItemImpl) it2.next();
                    int barWidthWeight = barItemImpl2.getBarWidthWeight(context);
                    if (barWidthWeight == 4) {
                        barOrderInteractor.landscapeBars.add(barItemImpl2.mBarRootView);
                    } else {
                        if (i == 0 || i + barWidthWeight > 4) {
                            linearLayout = new LinearLayout(context);
                            linearLayout.setOrientation(0);
                            linearLayout.setWeightSum(4.0f);
                            linearLayout.setTag("expand_anim");
                            barOrderInteractor.landscapeBarParents.add(linearLayout);
                            barOrderInteractor.landscapeBars.add(linearLayout);
                        } else {
                            linearLayout = linearLayout3;
                        }
                        if (linearLayout != null) {
                            linearLayout.addView(barItemImpl2.mBarRootView);
                            linearLayout3 = linearLayout;
                        }
                        if (i == 0 || (i = i + barWidthWeight) > 4) {
                            i = barWidthWeight;
                        }
                        View view3 = barItemImpl2.mBarRootView;
                        ViewGroup.LayoutParams layoutParams = view3.getLayoutParams();
                        LinearLayout.LayoutParams layoutParams2 = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : null;
                        if (layoutParams2 != null) {
                            if (i < 4) {
                                layoutParams2.setMarginEnd(dimensionPixelSize);
                            }
                            layoutParams2.weight = barWidthWeight;
                            layoutParams2.width = 0;
                            layoutParams2.bottomMargin = 0;
                            view3.setLayoutParams(layoutParams2);
                        }
                    }
                }
            }
            qSPanelHost.setBarsToPanel(barOrderInteractor.landscapeBars);
        }
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final SecQSPanel.QSTileLayout getOrCreateTileLayout() {
        return new SecTileChunkLayout(((SecQSPanel) this.mView).getContext());
    }

    public final int getPaddingBottom() {
        return ((SecQSPanel) this.mView).getPaddingBottom();
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = getContext().getResources().getConfiguration().orientation;
        ConfigurationState configurationState = this.mLastConfigurationState;
        boolean needToUpdate = configurationState.needToUpdate(configuration);
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onConfigurationChanged currentOrientation = ", ", newConfig.orientation = ");
        m.append(configuration.orientation);
        m.append(", mOrientation = ");
        m.append(this.mOrientation);
        m.append(", needToUpdate = ");
        m.append(needToUpdate);
        Log.d("SecQSPanelController", m.toString());
        Log.d("SecQSPanelController", "onConfigurationChanged diff = " + configurationState.toCompareString(configuration));
        if (this.mOrientation != i) {
            addBarItems();
        }
        if (configurationState.needToUpdate(configuration) || this.mOrientation != i) {
            this.mOrientation = i;
            if (this.mListening) {
                refreshAllTiles();
            }
            configurationState.update(configuration);
            this.mQsPanelHost.setTiles();
        }
        this.mSecAnimatorManager.onConfigurationChanged(configuration);
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase, com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        this.mMediaHost.init(0);
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        if (this.mListening) {
            refreshAllTiles();
        }
        BarOrderInteractor barOrderInteractor = this.mBarOrderInteractor;
        ((UserTrackerImpl) barOrderInteractor.userTracker).addCallback(barOrderInteractor.userChanged, barOrderInteractor.executor);
        boolean z = QpRune.QUICK_TILE_HIDE_FROM_BAR;
        if (z) {
            HideRemovableTileHelper hideRemovableTileHelper = this.mHideRemovableTileHelper;
            hideRemovableTileHelper.getClass();
            if (z) {
                ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).addTunable(hideRemovableTileHelper, "hide_smart_view_large_tile_on_panel");
            }
        }
        this.mOrientation = getContext().getResources().getConfiguration().orientation;
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        BarOrderInteractor barOrderInteractor = this.mBarOrderInteractor;
        ((UserTrackerImpl) barOrderInteractor.userTracker).removeCallback(barOrderInteractor.userChanged);
        boolean z = QpRune.QUICK_TILE_HIDE_FROM_BAR;
        if (z) {
            HideRemovableTileHelper hideRemovableTileHelper = this.mHideRemovableTileHelper;
            hideRemovableTileHelper.getClass();
            if (z) {
                ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).removeTunable(hideRemovableTileHelper);
            }
        }
    }

    public final void setGridContentVisibility(boolean z) {
        int i = z ? 0 : 4;
        setVisibility(i);
        if (this.mGridContentVisible == z) {
            return;
        }
        this.mMetricsLogger.visibility(111, i);
        this.mGridContentVisible = z;
    }

    public final void setVisibility(int i) {
        ((SecQSPanel) this.mView).setVisibility(i);
    }

    public final void showEdit() {
        this.mQSCMainViewController.prepareForShowing(true);
        ((SecQSPanel) this.mView).post(new SecQSPanelController$$ExternalSyntheticLambda0(this, 1));
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final void updatePanelContents() {
        addBarItems();
    }
}
