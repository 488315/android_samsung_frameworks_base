package com.android.systemui.qs;

import android.content.res.Configuration;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.qs.SecQSPanel;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.animator.QsTransitionAnimator;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.domain.interactor.BarOrderInteractor;
import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import dagger.Lazy;
import java.util.Arrays;
import java.util.function.IntSupplier;
import javax.inject.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SecQSPanelController extends SecQSPanelControllerBase {
    public final BarOrderInteractor mBarOrderInteractor;
    public boolean mGridContentVisible;
    public final HideRemovableTileHelper mHideRemovableTileHelper;
    public final ConfigurationState mLastConfigurationState;
    public int mOrientation;
    public final QSCMainViewController mQSCMainViewController;
    public SecQSImplAnimatorManager mSecAnimatorManager;
    public final SecQsUiDisplayModeInteractor mSecQsUiDisplayModeInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HideRemovableTileHelper implements TunerService.Tunable {
        public /* synthetic */ HideRemovableTileHelper(SecQSPanelController secQSPanelController, int i) {
            this();
        }

        @Override // com.android.systemui.tuner.TunerService.Tunable
        public final void onTuningChanged(String str, String str2) {
            QSPanelHost qSPanelHost;
            if (QpRune.QUICK_TILE_HIDE_FROM_BAR && (qSPanelHost = SecQSPanelController.this.mQsPanelHost) != null) {
                qSPanelHost.setTiles(Boolean.TRUE);
                qSPanelHost.mQsHost.refreshTileList();
            }
        }

        private HideRemovableTileHelper() {
        }
    }

    public SecQSPanelController(SecQSPanel secQSPanel, QSHost qSHost, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, DumpManager dumpManager, QSPanelHost qSPanelHost, Provider provider, SecQSPanelResourcePicker secQSPanelResourcePicker, StatusBarKeyguardViewManager statusBarKeyguardViewManager, QSCMainViewController qSCMainViewController, BarOrderInteractor barOrderInteractor, MediaHost mediaHost, SecQSDetailController secQSDetailController, Lazy lazy, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        super(secQSPanel, qSHost, metricsLogger, uiEventLogger, qSLogger, dumpManager, qSPanelHost, (BarController) provider.get(), secQSPanelResourcePicker, mediaHost, secQSDetailController);
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DISPLAY_DEVICE_TYPE, ConfigurationState.ConfigurationField.UI_MODE, ConfigurationState.ConfigurationField.DENSITY_DPI));
        this.mGridContentVisible = true;
        this.mHideRemovableTileHelper = new HideRemovableTileHelper(this, 0);
        this.mQSCMainViewController = qSCMainViewController;
        qSCMainViewController.panelHost = qSPanelHost;
        this.mSecQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
        secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().qsPanelController = this;
        this.mBarOrderInteractor = barOrderInteractor;
        barOrderInteractor.host = qSPanelHost;
        qSPanelHost.resetBarSettingsRunnable = new SecQSPanelController$$ExternalSyntheticLambda0(barOrderInteractor, 0);
        qSPanelHost.applyBarOrderRunnable = new SecQSPanelController$$ExternalSyntheticLambda1(this, 0);
        secQSDetailController.panelController = this;
        this.mQsPanelHost.mOrientationSupplier = new IntSupplier() { // from class: com.android.systemui.qs.SecQSPanelController$$ExternalSyntheticLambda2
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
        super.addBarItems();
        this.mQsPanelHost.mTargetView.removeAllViews();
        BarOrderInteractor barOrderInteractor = this.mBarOrderInteractor;
        barOrderInteractor.flushBarParent();
        if (getContext().getResources().getConfiguration().orientation != 2 || QpRune.QUICK_TABLET || ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
            barOrderInteractor.applyBarOrder();
        } else {
            barOrderInteractor.makeLandscapeView(getContext());
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
            this.mQsPanelHost.setTiles(Boolean.FALSE);
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
        if (QsAnimatorState.isDetailOpening || QsAnimatorState.isDetailShowing || QsAnimatorState.isDetailClosing || QsAnimatorState.isDetailPopupShowing || QsAnimatorState.isSliding) {
            Log.d("SecQSPanelController", (QsAnimatorState.isSliding ? "while sliding animation" : "detail is showing").concat(", ignore customizer show request"));
            return;
        }
        QsTransitionAnimator qsTransitionAnimator = this.mQSCMainViewController.transitionAnimator;
        if (qsTransitionAnimator == null) {
            qsTransitionAnimator = null;
        }
        if (!qsTransitionAnimator.isThereNoView() && qsTransitionAnimator.mAnimatorsInitialiezed && qsTransitionAnimator.animStateCallback != null) {
            SecQSImplAnimatorManager.AnonymousClass2.setCustomizerShowing(true);
        }
        ((SecQSPanel) this.mView).post(new SecQSPanelController$$ExternalSyntheticLambda1(this, 1));
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final void updatePaddingAndMargins() {
        super.updatePaddingAndMargins();
        if (this.mSecQsUiDisplayModeInteractor.isTablet()) {
            int popOverMargin = this.mResourcePicker.resourcePickHelper.getTargetPicker().getPopOverMargin(getContext());
            ((SecQSPanel) this.mView).setPadding(popOverMargin, 0, popOverMargin, popOverMargin);
        }
    }

    @Override // com.android.systemui.qs.SecQSPanelControllerBase
    public final void updatePanelContents() {
        addBarItems();
    }
}
