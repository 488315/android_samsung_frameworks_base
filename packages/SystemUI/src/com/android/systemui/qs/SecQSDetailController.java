package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.pluginlock.component.PluginLockShortcutTask;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.animator.QsDetailPopupAnimator;
import com.android.systemui.qs.animator.QsTransitionAnimator;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.BarType;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.qs.buttons.QSTooltipWindow;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.tiles.detail.DndDetailAdapter;
import com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter;
import com.android.systemui.samsung.quicksetting.SecQSPanelComposeAdapter;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateChangeEvent;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class SecQSDetailController extends ViewController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final float CONTENT_AREA_RATIO;
    public final float DETAIL_TITLE_SUMMARY_RATIO;
    public final float DND_DETAIL_TITLE_SUMMARY_RATIO;
    public final float DND_SUMMARY_CONTENT_RATIO;
    public final ActivityStarter activityStarter;
    public boolean closeDetailOnRelease;
    public final ColoredBGHelper coloredBGHelper;
    public final InterestingConfigChanges configChanges;
    public SecQSPanelControllerBase.Record currentRecord;
    public DetailAdapter detailAdapter;
    public SecQSDetailController$handleShowingDetail$6$1 detailAdapterDismissRunnable;
    public View detailButtonsDivider;
    public final SecQSDetailController$detailCallback$1 detailCallback;
    public final SecQSDetailController$detailCallbackForPopup$1 detailCallbackForPopup;
    public ViewGroup detailContent;
    public SecQSDetailContentView detailContentParent;
    public Button detailDoneButton;
    public LinearLayout detailExtendedContainer;
    public TextView detailExtendedSummary;
    public ViewGroup detailExtendedSummaryContainer;
    public TextView detailExtendedText;
    public QsDetailPopupAnimator detailPopupAnimator;
    public Button detailSettingsButton;
    public String detailTileSpec;
    public final SparseArray detailViews;
    public final SecQSDetailController$dndSummaryCallback$1 dndSummaryCallback;
    public final CurrentTilesInteractor interactor;
    public boolean isPanelExpanding;
    public final MetricsLogger metricsLogger;
    public int oldOrientation;
    public final SecQSDetailController$onConfigurationChangedListener$1 onConfigurationChangedListener;
    public SecQSPanelController panelController;
    public final SecPanelExpansionStateInteractor panelExpansionStateInteractor;
    public final SecQSDetailController$panelExpansionStateListener$1 panelExpansionStateListener;
    public int popUpHeight;
    public SecQSImplAnimatorManager qsAnimatorManager;
    public LinearLayout qsDetailExtendedContainer;
    public View qsDetailHeader;
    public ProgressBar qsDetailHeaderProgress;
    public SecQSSwitch qsDetailHeaderSwitch;
    public ViewStub qsDetailHeaderSwitchStub;
    public TextView qsDetailHeaderTitle;
    public final SecQSPanelResourcePicker resourcePicker;
    public boolean scanState;
    public View scrollView;
    public final ShadeHeaderController shadeHeaderController;
    public final SecQSDetailController$shadeHeaderLayoutChangeListener$1 shadeHeaderLayoutChangeListener;
    public final ShadeInteractor shadeInteractor;
    public final StatusBarStateControllerImpl statusBarStateController;
    public DetailAdapter switchAdapter;
    public boolean switchState;
    public View toggleDivider;
    public QsTransitionAnimator transitionAnimator;
    public final SecQSDetail view;

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

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.qs.SecQSDetailController$panelExpansionStateListener$1] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.qs.SecQSDetailController$detailCallback$1] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.systemui.qs.SecQSDetailController$detailCallbackForPopup$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.qs.SecQSDetailController$shadeHeaderLayoutChangeListener$1] */
    public SecQSDetailController(SecQSDetail secQSDetail, ShadeInteractor shadeInteractor, StatusBarStateControllerImpl statusBarStateControllerImpl, SecQSPanelResourcePicker secQSPanelResourcePicker, ActivityStarter activityStarter, MetricsLogger metricsLogger, ShadeHeaderController shadeHeaderController, ColoredBGHelper coloredBGHelper, SecQSPanelComposeAdapter secQSPanelComposeAdapter, CurrentTilesInteractor currentTilesInteractor, SecPanelExpansionStateInteractor secPanelExpansionStateInteractor) {
        super(secQSDetail);
        this.view = secQSDetail;
        this.shadeInteractor = shadeInteractor;
        this.statusBarStateController = statusBarStateControllerImpl;
        this.resourcePicker = secQSPanelResourcePicker;
        this.activityStarter = activityStarter;
        this.metricsLogger = metricsLogger;
        this.shadeHeaderController = shadeHeaderController;
        this.coloredBGHelper = coloredBGHelper;
        this.interactor = currentTilesInteractor;
        this.panelExpansionStateInteractor = secPanelExpansionStateInteractor;
        this.configChanges = new InterestingConfigChanges(-1073671676);
        this.detailViews = new SparseArray();
        this.dndSummaryCallback = new SecQSDetailController$dndSummaryCallback$1(this);
        this.onConfigurationChangedListener = new SecQSDetailController$onConfigurationChangedListener$1(this);
        this.oldOrientation = getContext().getResources().getConfiguration().orientation;
        this.shadeHeaderLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.SecQSDetailController$shadeHeaderLayoutChangeListener$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i6, i8, "shadeHeaderLayoutChangeListener ", ",", " > ");
                sbM.append(i2);
                sbM.append(",");
                sbM.append(i4);
                Log.d("SecQSDetailController", sbM.toString());
                if (i8 - i6 != i4 - i2) {
                    SecQSDetailController secQSDetailController = this.this$0;
                    int i9 = SecQSDetailController.$r8$clinit;
                    secQSDetailController.updateMarginAndPadding();
                }
            }
        };
        this.panelExpansionStateListener = new SecPanelExpansionStateListener() { // from class: com.android.systemui.qs.SecQSDetailController$panelExpansionStateListener$1
            @Override // com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener
            public final void onPanelExpansionStateChanged(SecPanelExpansionStateChangeEvent secPanelExpansionStateChangeEvent) {
                boolean z = secPanelExpansionStateChangeEvent.panelExpansionState == 1;
                this.this$0.isPanelExpanding = z;
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onPanelExpansionStateChanged isPanelExpanding = ", "SecQSDetailController", z);
            }
        };
        this.detailCallback = new QsTransitionAnimator.DetailCallback() { // from class: com.android.systemui.qs.SecQSDetailController$detailCallback$1
            @Override // com.android.systemui.qs.animator.QsTransitionAnimator.DetailCallback
            public final void hideDetailAnimEnd() throws Resources.NotFoundException {
                SecQSDetailController secQSDetailController = this.this$0;
                SecQSDetailController$handleShowingDetail$6$1 secQSDetailController$handleShowingDetail$6$1 = secQSDetailController.detailAdapterDismissRunnable;
                if (secQSDetailController$handleShowingDetail$6$1 != null) {
                    secQSDetailController$handleShowingDetail$6$1.run();
                    secQSDetailController.detailAdapterDismissRunnable = null;
                }
                ViewGroup viewGroup = secQSDetailController.detailContent;
                if (viewGroup != null) {
                    viewGroup.removeAllViews();
                }
                secQSDetailController.view.setVisibility(4);
                secQSDetailController.handleShowingDetail(null);
            }

            @Override // com.android.systemui.qs.animator.QsTransitionAnimator.DetailCallback
            public final void showDetailAnimEnd() throws Resources.NotFoundException {
                SecQSDetailController secQSDetailController = this.this$0;
                SecQSPanelController secQSPanelController = secQSDetailController.panelController;
                if (secQSPanelController != null) {
                    if (secQSDetailController.detailAdapter == null) {
                        secQSPanelController = null;
                    }
                    if (secQSPanelController != null) {
                        secQSPanelController.setGridContentVisibility(QsAnimatorState.isDetailClosing);
                    }
                }
                boolean z = secQSDetailController.switchState;
                DetailAdapter detailAdapter = secQSDetailController.detailAdapter;
                boolean z2 = false;
                if (detailAdapter != null && detailAdapter.getToggleEnabled()) {
                    z2 = true;
                }
                SecQSDetailController.access$handleToggleStateChanged(secQSDetailController, z, z2);
            }
        };
        this.detailCallbackForPopup = new QsTransitionAnimator.DetailCallback() { // from class: com.android.systemui.qs.SecQSDetailController$detailCallbackForPopup$1
            @Override // com.android.systemui.qs.animator.QsTransitionAnimator.DetailCallback
            public final void hideDetailAnimEnd() throws Resources.NotFoundException {
                SecQSDetailController secQSDetailController = this.this$0;
                SecQSDetailController$handleShowingDetail$6$1 secQSDetailController$handleShowingDetail$6$1 = secQSDetailController.detailAdapterDismissRunnable;
                if (secQSDetailController$handleShowingDetail$6$1 != null) {
                    secQSDetailController$handleShowingDetail$6$1.run();
                    secQSDetailController.detailAdapterDismissRunnable = null;
                }
                ViewGroup viewGroup = secQSDetailController.detailContent;
                if (viewGroup != null) {
                    viewGroup.removeAllViews();
                }
                secQSDetailController.view.setVisibility(4);
                secQSDetailController.handleShowingDetail(null);
            }

            @Override // com.android.systemui.qs.animator.QsTransitionAnimator.DetailCallback
            public final void showDetailAnimEnd() throws Resources.NotFoundException {
                SecQSDetailController secQSDetailController = this.this$0;
                boolean z = secQSDetailController.switchState;
                DetailAdapter detailAdapter = secQSDetailController.detailAdapter;
                boolean z2 = false;
                if (detailAdapter != null && detailAdapter.getToggleEnabled()) {
                    z2 = true;
                }
                SecQSDetailController.access$handleToggleStateChanged(secQSDetailController, z, z2);
            }
        };
        this.CONTENT_AREA_RATIO = 0.64f;
        this.DND_SUMMARY_CONTENT_RATIO = 0.13f;
        this.DETAIL_TITLE_SUMMARY_RATIO = 0.33f;
        this.DND_DETAIL_TITLE_SUMMARY_RATIO = 0.2f;
        init();
    }

    public static final void access$handleToggleStateChanged(SecQSDetailController secQSDetailController, boolean z, boolean z2) throws Resources.NotFoundException {
        CharSequence title;
        secQSDetailController.switchState = z;
        View view = secQSDetailController.qsDetailHeader;
        if (view != null) {
            view.setEnabled(z2);
        }
        DetailAdapter detailAdapter = secQSDetailController.detailAdapter;
        if (detailAdapter != null && detailAdapter.getToggleState() == null) {
            DetailAdapter detailAdapter2 = secQSDetailController.detailAdapter;
            secQSDetailController.updateDetailTitle(null, detailAdapter2 != null ? detailAdapter2.getTitle() : null);
            return;
        }
        SecQSSwitch secQSSwitch = secQSDetailController.qsDetailHeaderSwitch;
        if (secQSSwitch != null) {
            secQSSwitch.setChecked(z);
            secQSSwitch.setEnabled(z2);
            DetailAdapter detailAdapter3 = secQSDetailController.detailAdapter;
            if (detailAdapter3 == null || (title = detailAdapter3.getTitle()) == null) {
                return;
            }
            secQSDetailController.updateDetailTitle(Boolean.valueOf(z), title);
        }
    }

    public static final void access$handleUpdatingDetail(SecQSDetailController secQSDetailController, DetailAdapter detailAdapter) {
        secQSDetailController.getClass();
        if (detailAdapter != null) {
            int metricsCategory = detailAdapter.getMetricsCategory();
            View viewCreateDetailView = detailAdapter.createDetailView(secQSDetailController.getContext(), (View) secQSDetailController.detailViews.get(metricsCategory), secQSDetailController.detailContent);
            if (viewCreateDetailView != null) {
                if (detailAdapter.shouldUseFullScreen()) {
                    ViewGroup viewGroup = secQSDetailController.toViewGroup(R.id.qs_detail_full_screen_container);
                    if (viewGroup != null) {
                        viewGroup.addView(viewCreateDetailView);
                    }
                } else {
                    ViewGroup viewGroup2 = secQSDetailController.detailContent;
                    if (viewGroup2 != null) {
                        viewGroup2.removeAllViews();
                        viewGroup2.addView(viewCreateDetailView);
                    }
                }
                secQSDetailController.detailViews.put(metricsCategory, viewCreateDetailView);
                int i = detailAdapter.getSettingsIntent() == null ? 8 : 0;
                Button button = secQSDetailController.detailSettingsButton;
                if (button != null) {
                    button.setVisibility(i);
                }
                View view = secQSDetailController.detailButtonsDivider;
                if (view != null) {
                    view.setVisibility(i);
                }
            }
        }
    }

    public static boolean isLargeScreen$6() {
        return ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
    }

    public static final void updateDetailButtonText$update(TextView textView, int i) throws Resources.NotFoundException {
        FontSizeUtils.updateFontSize(textView, R.dimen.sec_qs_detail_button_text_size, 0.8f, 1.7f);
        if (textView != null) {
            textView.setText(i);
            textView.getTypeface().isLikeDefault = true;
            textView.setBackground(textView.getContext().getDrawable(R.drawable.sec_qs_btn_borderless_rect));
            textView.semSetButtonShapeEnabled(true);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setMaxLines(1);
        }
    }

    public final void closeDetail() throws Resources.NotFoundException {
        this.closeDetailOnRelease = false;
        SecQSPanelControllerBase.Record record = this.currentRecord;
        if (record != null) {
            showDetail(false, record);
        }
    }

    public final void closeTargetDetail(DetailAdapter detailAdapter) throws Resources.NotFoundException {
        SecQSPanelControllerBase.Record record = this.currentRecord;
        if (record != null) {
            if (!Intrinsics.areEqual(record.mDetailAdapter, detailAdapter)) {
                record = null;
            }
            if (record != null) {
                closeDetail();
            }
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void destroy() {
        super.destroy();
        ((CopyOnWriteArrayList) this.panelExpansionStateInteractor.expansionStateListeners$delegate.getValue()).remove(this.panelExpansionStateListener);
    }

    public final boolean getQsExpanded() {
        return ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isQsExpanded().getValue()).booleanValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleShowingDetail(DetailAdapter detailAdapter) throws Resources.NotFoundException {
        Object[] objArr;
        SecQSPanelController secQSPanelController;
        boolean z = detailAdapter != null;
        SecQSDetail secQSDetail = this.view;
        secQSDetail.setClickable(z);
        boolean z2 = this.detailAdapter != null;
        if (z) {
            if (detailAdapter != null ? detailAdapter.shouldUseFullScreen() : false) {
                objArr = true;
            }
        } else {
            objArr = false;
        }
        if (z && !QsAnimatorState.panelExpanded) {
            closeDetail();
            return;
        }
        updateVisibility(detailAdapter);
        if (z && objArr == false) {
            setDetailExtendedContainerHeight();
            if (detailAdapter != null) {
                setupDetailHeader(detailAdapter);
            }
            updateHeaderProgress(false);
        }
        if ((z2 != z) == true || z2) {
            View viewFindViewWithTag = null;
            viewFindViewWithTag = null;
            viewFindViewWithTag = null;
            if (z) {
                secQSDetail.setTranslationX(isLargeScreen$6() ? this.resourcePicker.getQsFrameX() : 0.0f);
                Integer numValueOf = detailAdapter != null ? Integer.valueOf(detailAdapter.getMetricsCategory()) : null;
                if (detailAdapter != null) {
                    View viewCreateDetailView = detailAdapter.createDetailView(getContext(), (View) this.detailViews.get(numValueOf != null ? numValueOf.intValue() : 0), this.detailContent);
                    if (viewCreateDetailView != null) {
                        ViewGroup viewGroup = objArr != false ? toViewGroup(R.id.qs_detail_full_screen_container) : this.detailContent;
                        if (viewGroup != null) {
                            viewGroup.removeAllViews();
                            viewGroup.addView(viewCreateDetailView);
                        }
                        this.detailViews.put(numValueOf != null ? numValueOf.intValue() : 0, viewCreateDetailView);
                        setupDetailFooter(detailAdapter);
                        this.metricsLogger.visible(detailAdapter.getMetricsCategory());
                        secQSDetail.announceForAccessibility(getContext().getString(R.string.accessibility_quick_settings_detail) + ", " + ((Object) detailAdapter.getTitle()));
                        this.detailAdapter = detailAdapter;
                        this.detailAdapterDismissRunnable = null;
                        updateMarginAndPadding();
                        secQSDetail.setVisibility(0);
                        if (objArr == false) {
                            updateDetailButtonText();
                            if (isDNDTile()) {
                                updateDndDetail();
                            }
                        }
                    }
                }
                Log.e("SecQSDetailController", "Tile = " + ((Object) (detailAdapter != null ? detailAdapter.getTitle() : null)) + " detailView is null");
                return;
            }
            DetailAdapter detailAdapter2 = this.detailAdapter;
            if (detailAdapter2 != null) {
                if (!z2) {
                    detailAdapter2 = null;
                }
                if (detailAdapter2 != null) {
                    this.detailAdapterDismissRunnable = new SecQSDetailController$handleShowingDetail$6$1(this, detailAdapter2);
                }
            }
            if (((this.statusBarStateController.mState == 1) == false || getQsExpanded()) && (secQSPanelController = this.panelController) != null) {
                secQSPanelController.setGridContentVisibility(getQsExpanded());
            }
            if (this.scanState) {
                this.scanState = false;
                updateHeaderProgress(false);
            }
            secQSDetail.sendAccessibilityEvent(32);
            if (!((isLargeScreen$6() || QpRune.QUICK_PANEL_BLUR_MASSIVE) ? false : true)) {
                QsTransitionAnimator qsTransitionAnimator = this.transitionAnimator;
                if (qsTransitionAnimator != null) {
                    qsTransitionAnimator.transitionDetail(z);
                }
            } else if (!(z && ((isBluetoothOrWifiTile() || (detailAdapter instanceof MediaOutputDetailAdapter)) && getQsExpanded())) && (z || !QsAnimatorState.isDetailPopupShowing)) {
                QsTransitionAnimator qsTransitionAnimator2 = this.transitionAnimator;
                if (qsTransitionAnimator2 != null) {
                    qsTransitionAnimator2.transitionDetail(z);
                }
            } else {
                if (isBluetoothOrWifiTile()) {
                    SecQSPanelController secQSPanelController2 = this.panelController;
                    if (secQSPanelController2 != null) {
                        QSTileView tileView = secQSPanelController2.mQsPanelHost.getTileView(this.detailTileSpec);
                        if (tileView != null) {
                            viewFindViewWithTag = tileView.findViewWithTag("anchor");
                        }
                    }
                } else {
                    SecQSPanelController secQSPanelController3 = this.panelController;
                    if (secQSPanelController3 != null) {
                        viewFindViewWithTag = secQSPanelController3.mQsPanelHost.mBarController.getBarInExpanded(BarType.QS_MEDIA_PLAYER).mBarRootView;
                    }
                }
                QsDetailPopupAnimator qsDetailPopupAnimator = this.detailPopupAnimator;
                if (qsDetailPopupAnimator != null) {
                    qsDetailPopupAnimator.transitionDetail(viewFindViewWithTag, z);
                }
            }
            SystemUIAnalytics.sendScreenViewLog(SystemUIAnalytics.SID_QUICKPANEL_EXPANDED);
        }
    }

    public final boolean isBluetoothOrWifiTile() {
        if (QpRune.QUICK_TABLET) {
            return false;
        }
        String str = this.detailTileSpec;
        return Intrinsics.areEqual(str, "Wifi") || Intrinsics.areEqual(str, "Bluetooth");
    }

    public final boolean isDNDTile() {
        return Intrinsics.areEqual(this.detailTileSpec, PluginLockShortcutTask.DO_NOT_DISTURB_TASK);
    }

    public final boolean isPortrait$1() {
        return getResources().getConfiguration().orientation == 1 || (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && isLargeScreen$6());
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        updateMarginAndPadding();
        this.panelExpansionStateInteractor.registerListener(this.panelExpansionStateListener);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() throws Resources.NotFoundException {
        SecQSDetail secQSDetail = this.view;
        ((ArrayList) secQSDetail.mOnConfigurationChangedListeners).add(this.onConfigurationChangedListener);
        secQSDetail.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.SecQSDetailController$onViewAttached$1$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) throws Resources.NotFoundException {
                SecQSDetailController secQSDetailController = this.this$0;
                motionEvent.getClass();
                int i = SecQSDetailController.$r8$clinit;
                secQSDetailController.getClass();
                if (motionEvent.getAction() == 0) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    Rect rect = new Rect();
                    View viewFindViewById = secQSDetailController.view.findViewById(R.id.qs_detail_extended_container);
                    if (viewFindViewById != null) {
                        viewFindViewById.getGlobalVisibleRect(rect);
                    }
                    secQSDetailController.closeDetailOnRelease = !rect.contains((int) x, (int) y);
                } else if (motionEvent.getAction() == 1 && secQSDetailController.closeDetailOnRelease) {
                    this.this$0.closeDetail();
                }
                return true;
            }
        });
        updateViews(secQSDetail);
        updateDetailButtonText();
        updateDetailHeader();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        ((ArrayList) this.view.mOnConfigurationChangedListeners).remove(this.onConfigurationChangedListener);
    }

    public final void setDetailExtendedContainerHeight() throws Resources.NotFoundException {
        float f;
        boolean z = QpRune.QUICK_TABLET;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        if (z) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_pop_over_blur_detail_height);
            int availableDisplayHeight = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).resourcePickHelper.getTargetPicker().getAvailableDisplayHeight(getContext());
            if (dimensionPixelSize > availableDisplayHeight) {
                dimensionPixelSize = availableDisplayHeight;
            }
            this.popUpHeight = dimensionPixelSize;
            if (isDNDTile()) {
                ViewGroup viewGroup = this.detailExtendedSummaryContainer;
                if (viewGroup != null) {
                    ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                    LinearLayout.LayoutParams layoutParams2 = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : null;
                    if (layoutParams2 != null) {
                        layoutParams2.height = (int) (this.DND_SUMMARY_CONTENT_RATIO * this.popUpHeight);
                    }
                }
                f = this.DND_DETAIL_TITLE_SUMMARY_RATIO;
            } else {
                f = this.DETAIL_TITLE_SUMMARY_RATIO;
            }
            LinearLayout linearLayout = this.detailExtendedContainer;
            if (linearLayout != null) {
                ViewGroup.LayoutParams layoutParams3 = linearLayout.getLayoutParams();
                LinearLayout.LayoutParams layoutParams4 = layoutParams3 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams3 : null;
                if (layoutParams4 != null) {
                    layoutParams4.height = (int) (this.popUpHeight * f);
                }
            }
            SecQSDetailContentView secQSDetailContentView = this.detailContentParent;
            if (secQSDetailContentView != null) {
                ViewGroup.LayoutParams layoutParams5 = secQSDetailContentView.getLayoutParams();
                LinearLayout.LayoutParams layoutParams6 = layoutParams5 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams5 : null;
                if (layoutParams6 != null) {
                    secQSDetailContentView.setMinimumHeight(secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getDetailContentViewMinHeight(secQSDetailContentView.getContext()));
                    layoutParams6.height = (int) (this.popUpHeight * this.CONTENT_AREA_RATIO);
                }
            }
            LinearLayout linearLayout2 = this.qsDetailExtendedContainer;
            if (linearLayout2 != null) {
                linearLayout2.measure(0, 0);
                ViewGroup.LayoutParams layoutParams7 = linearLayout2.getLayoutParams();
                LinearLayout.LayoutParams layoutParams8 = layoutParams7 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams7 : null;
                if (layoutParams8 != null) {
                    layoutParams8.gravity = 49;
                }
            }
            TextView textView = this.detailExtendedSummary;
            if (textView != null) {
                ViewGroup.LayoutParams layoutParams9 = textView.getLayoutParams();
                layoutParams = layoutParams9 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams9 : null;
                if (layoutParams != null) {
                    layoutParams.gravity = 17;
                    textView.setLayoutParams(layoutParams);
                }
                FontSizeUtils.updateFontSize(textView, R.dimen.sec_qs_detail_extended_container_text_size, 0.8f, 1.0f);
                return;
            }
            return;
        }
        LinearLayout linearLayout3 = this.detailExtendedContainer;
        int height = -2;
        if (linearLayout3 != null) {
            ViewGroup.LayoutParams layoutParams10 = linearLayout3.getLayoutParams();
            LinearLayout.LayoutParams layoutParams11 = layoutParams10 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams10 : null;
            if (layoutParams11 != null) {
                layoutParams11.height = isPortrait$1() ? -2 : 0;
            } else {
                layoutParams11 = null;
            }
            linearLayout3.setLayoutParams(layoutParams11);
        }
        boolean zIsBluetoothOrWifiTile = isBluetoothOrWifiTile();
        int detailContentViewMaxHeight = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getDetailContentViewMaxHeight(getContext());
        SecQSDetailContentView secQSDetailContentView2 = this.detailContentParent;
        if (secQSDetailContentView2 != null) {
            ViewGroup.LayoutParams layoutParams12 = secQSDetailContentView2.getLayoutParams();
            LinearLayout.LayoutParams layoutParams13 = layoutParams12 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams12 : null;
            if (layoutParams13 != null) {
                layoutParams13.bottomMargin = isPortrait$1() ? 0 : getResources().getDimensionPixelSize(R.dimen.sec_qs_detail_bottom_margin);
                layoutParams13.topMargin = isPortrait$1() ? getResources().getDimensionPixelSize(R.dimen.qs_detail_margin_top) : 0;
                if (zIsBluetoothOrWifiTile) {
                    if (isPortrait$1()) {
                        height = detailContentViewMaxHeight;
                    } else {
                        View view = this.scrollView;
                        height = (view != null ? view.getHeight() : 0) - layoutParams13.bottomMargin;
                    }
                }
                layoutParams13.height = height;
            } else {
                layoutParams13 = null;
            }
            secQSDetailContentView2.setLayoutParams(layoutParams13);
        }
        ViewGroup viewGroup2 = this.detailExtendedSummaryContainer;
        if (viewGroup2 != null) {
            ViewGroup.LayoutParams layoutParams14 = viewGroup2.getLayoutParams();
            LinearLayout.LayoutParams layoutParams15 = layoutParams14 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams14 : null;
            if (layoutParams15 != null) {
                layoutParams15.bottomMargin = isPortrait$1() ? 0 : getResources().getDimensionPixelSize(R.dimen.sec_qs_detail_bottom_extended_margin);
            } else {
                layoutParams15 = null;
            }
            viewGroup2.setLayoutParams(layoutParams15);
        }
        TextView textView2 = this.detailExtendedSummary;
        if (textView2 != null) {
            ViewGroup.LayoutParams layoutParams16 = textView2.getLayoutParams();
            LinearLayout.LayoutParams layoutParams17 = layoutParams16 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams16 : null;
            if (layoutParams17 != null) {
                layoutParams17.gravity = isPortrait$1() ? 17 : 8388611;
                layoutParams = layoutParams17;
            }
            textView2.setLayoutParams(layoutParams);
        }
    }

    public final void setQsAnimatorManager(SecQSImplAnimatorManager secQSImplAnimatorManager) {
        QsTransitionAnimator qsTransitionAnimator = secQSImplAnimatorManager != null ? secQSImplAnimatorManager.mTransitionAnimator : null;
        if (qsTransitionAnimator != null) {
            qsTransitionAnimator.detailCallback = this.detailCallback;
        }
        this.transitionAnimator = qsTransitionAnimator;
        QsDetailPopupAnimator qsDetailPopupAnimator = secQSImplAnimatorManager != null ? secQSImplAnimatorManager.mQsDetailPopupAnimator : null;
        if (qsDetailPopupAnimator != null) {
            qsDetailPopupAnimator.detailCallback = this.detailCallbackForPopup;
        }
        this.detailPopupAnimator = qsDetailPopupAnimator;
        this.qsAnimatorManager = secQSImplAnimatorManager;
    }

    public final void setupDetailFooter(final DetailAdapter detailAdapter) {
        Log.d("SecQSDetailController", "setupDetailFooter");
        int i = detailAdapter.getSettingsIntent() == null ? 8 : 0;
        View view = this.detailButtonsDivider;
        if (view != null) {
            view.setVisibility(i);
        }
        Button button = this.detailSettingsButton;
        if (button != null) {
            button.setVisibility(i);
            button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.SecQSDetailController$setupDetailFooter$1$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.this$0.activityStarter.postStartActivityDismissingKeyguard(detailAdapter.getSettingsIntent(), 0);
                }
            });
        }
        Button button2 = this.detailDoneButton;
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.SecQSDetailController.setupDetailFooter.2
                /* JADX WARN: Removed duplicated region for block: B:10:0x003e  */
                @Override // android.view.View.OnClickListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onClick(View view2) throws Resources.NotFoundException {
                    String strValueOf;
                    SecQSDetailController secQSDetailController = SecQSDetailController.this;
                    DetailAdapter detailAdapter2 = secQSDetailController.detailAdapter;
                    String str = null;
                    if (detailAdapter2 != null) {
                        SecQSPanelController secQSPanelController = secQSDetailController.panelController;
                        if (secQSPanelController != null) {
                            QSPanelHost qSPanelHost = secQSPanelController.mQsPanelHost;
                            qSPanelHost.getClass();
                            QSTile qSTile = !(secQSPanelController.mDetailController.currentRecord instanceof SecQSPanelControllerBase.TileRecord) ? null : (QSTile) qSPanelHost.mRecords.stream().map(new QSPanelHost$$ExternalSyntheticLambda6(2)).filter(new QSPanelHost$$ExternalSyntheticLambda25(detailAdapter2, 0)).findFirst().orElse(null);
                            SQSTile sQSTile = qSTile instanceof SQSTile ? (SQSTile) qSTile : null;
                            if (sQSTile == null || (strValueOf = sQSTile.getTileMapKey()) == null) {
                                strValueOf = String.valueOf(detailAdapter2.getMetricsCategory());
                            }
                            str = strValueOf;
                        }
                    }
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_DETAIL_DETAILS, str);
                    SecQSDetailController secQSDetailController2 = SecQSDetailController.this;
                    secQSDetailController2.view.announceForAccessibility(secQSDetailController2.getContext().getString(R.string.accessibility_desc_quick_settings));
                    if (detailAdapter.onDoneButtonClicked()) {
                        return;
                    }
                    SecQSDetailController.this.closeDetail();
                }
            });
        }
    }

    public final void setupDetailHeader(final DetailAdapter detailAdapter) throws Resources.NotFoundException {
        TextView textView = this.qsDetailHeaderTitle;
        if (textView != null) {
            textView.setText(detailAdapter.getTitle());
        }
        Boolean toggleState = detailAdapter.getToggleState();
        if (toggleState == null) {
            SecQSSwitch secQSSwitch = this.qsDetailHeaderSwitch;
            if (secQSSwitch != null) {
                secQSSwitch.setVisibility(4);
            }
            View view = this.qsDetailHeader;
            if (view != null) {
                view.setClickable(false);
            }
        } else {
            ViewStub viewStub = this.qsDetailHeaderSwitchStub;
            if (viewStub != null) {
                if (this.qsDetailHeaderSwitch != null) {
                    viewStub = null;
                }
                if (viewStub != null) {
                    View viewInflate = viewStub.inflate();
                    this.qsDetailHeaderSwitch = viewInflate instanceof SecQSSwitch ? (SecQSSwitch) viewInflate : null;
                }
            }
            this.switchAdapter = detailAdapter;
            this.switchState = toggleState.booleanValue();
            boolean zBooleanValue = toggleState.booleanValue();
            SecQSSwitch secQSSwitch2 = this.qsDetailHeaderSwitch;
            if (secQSSwitch2 != null) {
                secQSSwitch2.setChecked(zBooleanValue);
                secQSSwitch2.setEnabled(detailAdapter.getToggleEnabled());
                secQSSwitch2.setClickable(true);
                secQSSwitch2.jumpDrawablesToCurrentState();
                secQSSwitch2.setVisibility(0);
            }
            View view2 = this.qsDetailHeader;
            if (view2 != null) {
                view2.setVisibility(0);
                view2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.SecQSDetailController$setupDetailHeader$3$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view3) {
                        SecQSSwitch secQSSwitch3 = this.this$0.qsDetailHeaderSwitch;
                        if (secQSSwitch3 != null) {
                            DetailAdapter detailAdapter2 = detailAdapter;
                            boolean z = !secQSSwitch3.isChecked();
                            secQSSwitch3.setChecked(z);
                            detailAdapter2.setToggleState(z);
                        }
                    }
                });
            }
            final SecQSSwitch secQSSwitch3 = this.qsDetailHeaderSwitch;
            if (secQSSwitch3 != null) {
                secQSSwitch3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.SecQSDetailController$setupHeaderSwitchListener$1$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view3) {
                        detailAdapter.setToggleState(secQSSwitch3.isChecked());
                    }
                });
                secQSSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.SecQSDetailController$setupHeaderSwitchListener$1$2
                    /* JADX WARN: Removed duplicated region for block: B:6:0x000e  */
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        int i;
                        if (z) {
                            SecQSDetailController secQSDetailController = this.this$0;
                            int i2 = SecQSDetailController.$r8$clinit;
                            i = secQSDetailController.isBluetoothOrWifiTile() ? 0 : 8;
                        }
                        ProgressBar progressBar = this.this$0.qsDetailHeaderProgress;
                        if (progressBar != null) {
                            progressBar.setVisibility(i);
                        }
                        if (this.this$0.isPortrait$1()) {
                            this.this$0.view.announceForAccessibility(secQSSwitch3.getContext().getString(z ? R.string.switch_bar_on : R.string.switch_bar_off));
                        }
                    }
                });
            }
        }
        updateDetailTitle(toggleState, detailAdapter.getTitle());
    }

    public final void showDetail(boolean z, SecQSPanelControllerBase.Record record) throws Resources.NotFoundException {
        Pair pair;
        QSTooltipWindow qSTooltipWindow;
        if (z && (qSTooltipWindow = QSTooltipWindow.getInstance(getContext())) != null) {
            qSTooltipWindow.hideToolTip();
        }
        if (z && (QsAnimatorState.isCustomizerShowing || QsAnimatorState.isSliding || QsAnimatorState.isDetailPopupShowing || QsAnimatorState.isDetailShowing || QsAnimatorState.isDetailOpening || this.isPanelExpanding)) {
            String str = QsAnimatorState.isCustomizerShowing ? "customizer is showing," : "";
            String str2 = QsAnimatorState.isSliding ? "isSliding," : "";
            String str3 = QsAnimatorState.isDetailPopupShowing ? "detail popup is showing," : "";
            String str4 = QsAnimatorState.isDetailShowing ? "detail is showing," : "";
            String str5 = QsAnimatorState.isDetailOpening ? "detail is opening," : "";
            String str6 = this.isPanelExpanding ? "panel is opening," : "";
            StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("showDetail ", str, str2, str3, str4);
            sbM.append(str5);
            sbM.append(str6);
            sbM.append(" ignore detail show request");
            Log.d("SecQSDetailController", sbM.toString());
            return;
        }
        SecQSPanelControllerBase.TileRecord tileRecord = record instanceof SecQSPanelControllerBase.TileRecord ? (SecQSPanelControllerBase.TileRecord) record : null;
        if (tileRecord != null) {
            SecQSPanelControllerBase.Record record2 = this.currentRecord;
            if ((record2 != null) == z && Intrinsics.areEqual(record2, record)) {
                return;
            }
            QSTile qSTile = tileRecord.tile;
            if (z) {
                DetailAdapter detailAdapter = qSTile.getDetailAdapter();
                tileRecord.mDetailAdapter = detailAdapter;
                if (detailAdapter == null) {
                    return;
                }
            }
            this.detailTileSpec = z ? qSTile.getTileSpec() : "";
            if (!isLargeScreen$6() && !QpRune.QUICK_PANEL_BLUR_MASSIVE) {
                if (isBluetoothOrWifiTile() && getQsExpanded()) {
                    SecQSDetailContentView secQSDetailContentView = this.detailContentParent;
                    if (secQSDetailContentView != null) {
                        secQSDetailContentView.setBackground(null);
                    }
                } else if (!QsAnimatorState.isDetailPopupShowing) {
                    SecQSDetailContentView secQSDetailContentView2 = this.detailContentParent;
                    if (secQSDetailContentView2 == null) {
                        secQSDetailContentView2 = null;
                    }
                    ColoredBGHelper coloredBGHelper = this.coloredBGHelper;
                    coloredBGHelper.setBackGroundDrawable(secQSDetailContentView2, coloredBGHelper.getBGColor());
                }
            }
            qSTile.setDetailListening(z);
        }
        MotionLayout motionLayout = this.shadeHeaderController.header;
        SecQSDetailController$shadeHeaderLayoutChangeListener$1 secQSDetailController$shadeHeaderLayoutChangeListener$1 = this.shadeHeaderLayoutChangeListener;
        if (z) {
            motionLayout.addOnLayoutChangeListener(secQSDetailController$shadeHeaderLayoutChangeListener$1);
        } else {
            motionLayout.removeOnLayoutChangeListener(secQSDetailController$shadeHeaderLayoutChangeListener$1);
        }
        if (z) {
            pair = new Pair(record, record != null ? record.mDetailAdapter : null);
        } else {
            pair = new Pair(null, null);
        }
        SecQSPanelControllerBase.Record record3 = (SecQSPanelControllerBase.Record) pair.getFirst();
        if (!Intrinsics.areEqual(record3, this.currentRecord)) {
            this.currentRecord = record3;
        }
        if (isAttachedToWindow()) {
            handleShowingDetail((DetailAdapter) pair.getSecond());
        }
    }

    public final void showTargetDetail(DetailAdapter detailAdapter) throws Resources.NotFoundException {
        SecQSPanelControllerBase.Record record = new SecQSPanelControllerBase.Record();
        record.mDetailAdapter = detailAdapter;
        Unit unit = Unit.INSTANCE;
        showDetail(true, record);
    }

    public final ViewGroup toViewGroup(int i) {
        View viewFindViewById = this.view.findViewById(i);
        if (viewFindViewById instanceof ViewGroup) {
            return (ViewGroup) viewFindViewById;
        }
        return null;
    }

    public final void updateDetailButtonText() throws Resources.NotFoundException {
        if (isDNDTile()) {
            ViewGroup viewGroup = this.detailExtendedSummaryContainer;
            if (viewGroup != null) {
                viewGroup.setVisibility(0);
            }
            TextView textView = this.detailExtendedSummary;
            if (textView != null) {
                textView.setVisibility(0);
            }
        } else {
            ViewGroup viewGroup2 = this.detailExtendedSummaryContainer;
            if (viewGroup2 != null) {
                viewGroup2.setVisibility(8);
            }
            TextView textView2 = this.detailExtendedSummary;
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
        }
        updateDetailButtonText$update(this.detailDoneButton, R.string.sec_quick_settings_done);
        updateDetailButtonText$update(this.detailSettingsButton, R.string.sec_quick_settings_details);
    }

    public final void updateDetailHeader() {
        View view = this.qsDetailHeader;
        if (view != null) {
            ViewParent parent = view.getParent();
            ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : null;
            boolean zIsLargeScreen$6 = isLargeScreen$6();
            SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
            if (zIsLargeScreen$6 || isPortrait$1()) {
                ViewGroup viewGroup2 = toViewGroup(R.id.qs_detail_parent);
                if (viewGroup2 != null) {
                    viewGroup2.addView(view, 0);
                }
                if (layoutParams2 != null) {
                    layoutParams2.height = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getDetailHeaderHeight(view.getContext());
                    layoutParams2.bottomMargin = 0;
                    return;
                }
                return;
            }
            ViewGroup viewGroup3 = toViewGroup(R.id.qs_detail_extended_container);
            if (viewGroup3 != null) {
                viewGroup3.addView(view, 0);
            }
            if (layoutParams2 != null) {
                layoutParams2.height = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getDetailHeaderHeight(view.getContext());
                layoutParams2.topMargin = getResources().getDimensionPixelSize(R.dimen.sec_qs_detail_header_marginVertical);
                layoutParams2.bottomMargin = getResources().getDimensionPixelSize(R.dimen.sec_qs_detail_header_marginVertical);
            }
        }
    }

    public final void updateDetailTitle(Boolean bool, CharSequence charSequence) throws Resources.NotFoundException {
        if (charSequence == null || charSequence.toString().length() == 0) {
            return;
        }
        String string = charSequence.toString();
        int length = string.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean z2 = Intrinsics.compare(string.charAt(!z ? i : length), 32) <= 0;
            if (z) {
                if (!z2) {
                    break;
                } else {
                    length--;
                }
            } else if (z2) {
                i++;
            } else {
                z = true;
            }
        }
        String string2 = string.subSequence(i, length + 1).toString();
        TextView textView = this.detailExtendedText;
        if (textView != null) {
            textView.setText(string2);
        }
        Log.d("SecQSDetailController", "updateDetailTitle" + string2);
        boolean zIsPortrait$1 = isPortrait$1();
        if (bool != null) {
            View view = this.qsDetailHeader;
            if (view != null) {
                if (zIsPortrait$1) {
                    view.setClickable(true);
                } else {
                    view.setClickable(false);
                    view.setBackground(null);
                }
            }
            TextView textView2 = this.qsDetailHeaderTitle;
            if (textView2 != null) {
                Pair pair = (isLargeScreen$6() || zIsPortrait$1) ? bool.booleanValue() ? new Pair(textView2.getContext().getString(R.string.sec_switch_bar_on), Integer.valueOf(R.color.sec_qs_detail_header_on_text_color)) : new Pair(textView2.getContext().getString(R.string.sec_switch_bar_off), Integer.valueOf(R.color.sec_qs_detail_header_off_text_color)) : new Pair(string2, Integer.valueOf(R.color.sec_qs_detail_header_text_color));
                textView2.setText((CharSequence) pair.getFirst());
                textView2.setTextColor(textView2.getContext().getColor(((Number) pair.getSecond()).intValue()));
                FontSizeUtils.updateFontSize(textView2, R.dimen.sec_qs_detail_header_text_size, 0.8f, 1.3f);
            }
            SecQSSwitch secQSSwitch = this.qsDetailHeaderSwitch;
            if (secQSSwitch != null) {
                secQSSwitch.setImportantForAccessibility(zIsPortrait$1 ? 2 : 1);
            }
        } else {
            View view2 = this.qsDetailHeader;
            if (view2 != null) {
                if (isLargeScreen$6() || zIsPortrait$1) {
                    view2.setVisibility(8);
                } else {
                    view2.setVisibility(0);
                    view2.setClickable(false);
                    view2.setBackground(null);
                }
            }
            TextView textView3 = this.qsDetailHeaderTitle;
            if (textView3 != null) {
                TextView textView4 = zIsPortrait$1 ? null : textView3;
                if (textView4 != null) {
                    textView4.setText(string2);
                    textView4.setTextColor(textView4.getContext().getColor(R.color.sec_qs_detail_header_text_color));
                    FontSizeUtils.updateFontSize(textView4, R.dimen.sec_qs_detail_header_text_size, 0.8f, 1.3f);
                }
            }
        }
        TextView textView5 = this.qsDetailHeaderTitle;
        if (textView5 != null) {
            textView5.setImportantForAccessibility(zIsPortrait$1 ? 2 : 1);
        }
        View view3 = this.toggleDivider;
        if (view3 != null) {
            view3.setVisibility((!zIsPortrait$1 || bool == null) ? 8 : 0);
        }
    }

    public final void updateDndDetail() {
        View view;
        if (!isDNDTile()) {
            ViewGroup viewGroup = this.detailExtendedSummaryContainer;
            if (viewGroup != null) {
                viewGroup.setVisibility(8);
                return;
            }
            return;
        }
        if ((isPortrait$1() || QpRune.QUICK_TABLET) && (view = this.qsDetailHeader) != null) {
            view.setVisibility(8);
        }
        ViewGroup viewGroup2 = this.detailExtendedSummaryContainer;
        if (viewGroup2 != null) {
            viewGroup2.setVisibility(0);
        }
        DetailAdapter detailAdapter = this.detailAdapter;
        if (detailAdapter instanceof DndDetailAdapter) {
            TextView textView = this.detailExtendedSummary;
            if (textView != null) {
                textView.setText(detailAdapter != null ? detailAdapter.getDetailAdapterSummary() : null);
            }
            ((DndDetailAdapter) this.detailAdapter).mSecQSDetailController = this;
        }
    }

    public final void updateHeaderProgress(boolean z) {
        ProgressBar progressBar = this.qsDetailHeaderProgress;
        if (progressBar != null) {
            progressBar.setVisibility((z && isBluetoothOrWifiTile()) ? 0 : 8);
        }
    }

    public final void updateMarginAndPadding() {
        boolean zIsLargeScreen$6 = isLargeScreen$6();
        ShadeHeaderController shadeHeaderController = this.shadeHeaderController;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        SecQSDetail secQSDetail = this.view;
        if (!zIsLargeScreen$6) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) ((SecQSDetail) this.mView).getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = 0;
            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = 0;
            ((ViewGroup.MarginLayoutParams) layoutParams).width = -1;
            View viewFindViewById = ((SecQSDetail) this.mView).findViewById(R.id.qs_detail_container);
            if (viewFindViewById != null) {
                viewFindViewById.getLayoutParams().height = -1;
                viewFindViewById.setPadding(0, 0, 0, 0);
            }
            View viewFindViewById2 = ((SecQSDetail) this.mView).findViewById(R.id.panel_adjusted_detail);
            if (viewFindViewById2 != null) {
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) viewFindViewById2.getLayoutParams();
                layoutParams2.topMargin = shadeHeaderController.header.getMeasuredHeight();
                layoutParams2.bottomMargin = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getNavBarHeight(secQSDetail.getContext());
                layoutParams2.width = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getPanelWidth(secQSDetail.getContext());
                viewFindViewById2.setPadding(secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getDetailSidePadding(((SecQSDetail) this.mView).getContext()), ((SecQSDetail) this.mView).getPaddingTop(), secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getDetailSidePadding(((SecQSDetail) this.mView).getContext()), ((SecQSDetail) this.mView).getPaddingBottom());
                return;
            }
            return;
        }
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) ((SecQSDetail) this.mView).getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = shadeHeaderController.header.getMeasuredHeight();
        ((ViewGroup.MarginLayoutParams) layoutParams3).bottomMargin = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getNavBarHeight(secQSDetail.getContext());
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getPanelWidth(secQSDetail.getContext());
        SecQSPanelResourceCommon.Companion companion = SecQSPanelResourceCommon.Companion;
        Context context = getContext();
        companion.getClass();
        int iDp = SecQSPanelResourceCommon.Companion.dp(R.dimen.qs_pop_over_blur_detail_height, context);
        this.popUpHeight = iDp;
        int availableDisplayHeight = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getAvailableDisplayHeight(secQSDetail.getContext());
        SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = secQSPanelResourcePicker.resourcePickHelper;
        if (iDp > availableDisplayHeight) {
            this.popUpHeight = secQSPanelResourcePickHelper.getTargetPicker().getAvailableDisplayHeight(secQSDetail.getContext());
        }
        View viewFindViewById3 = ((SecQSDetail) this.mView).findViewById(R.id.qs_detail_container);
        if (viewFindViewById3 != null) {
            viewFindViewById3.getLayoutParams().height = this.popUpHeight;
            viewFindViewById3.setPadding(viewFindViewById3.getPaddingLeft(), viewFindViewById3.getPaddingTop(), viewFindViewById3.getPaddingRight(), viewFindViewById3.getContext().getResources().getDimensionPixelSize(R.dimen.qs_affordance_arrow_translation_y_tablet));
        }
        View viewFindViewById4 = ((SecQSDetail) this.mView).findViewById(R.id.panel_adjusted_detail);
        if (viewFindViewById4 != null) {
            viewFindViewById4.setPadding(secQSPanelResourcePickHelper.getTargetPicker().getDetailSidePadding(((SecQSDetail) this.mView).getContext()), viewFindViewById4.getPaddingTop(), secQSPanelResourcePickHelper.getTargetPicker().getDetailSidePadding(((SecQSDetail) this.mView).getContext()), viewFindViewById4.getPaddingBottom());
        }
    }

    public final void updateViews(SecQSDetail secQSDetail) {
        this.detailContent = (ViewGroup) secQSDetail.findViewById(R.id.qs_detail_content);
        this.detailSettingsButton = (Button) secQSDetail.findViewById(R.id.detail_btn);
        this.detailDoneButton = (Button) secQSDetail.findViewById(R.id.done_btn);
        this.detailButtonsDivider = secQSDetail.findViewById(R.id.qs_detail_divider);
        this.detailExtendedContainer = (LinearLayout) secQSDetail.findViewById(R.id.qs_detail_extended_text_container);
        this.detailExtendedText = (TextView) secQSDetail.findViewById(R.id.qs_detail_extended_text);
        this.detailContentParent = (SecQSDetailContentView) secQSDetail.findViewById(R.id.qs_detail_parent);
        this.toggleDivider = secQSDetail.findViewById(R.id.qs_toggle_divider);
        View viewFindViewById = secQSDetail.findViewById(R.id.qs_detail_header);
        if (viewFindViewById != null) {
            this.qsDetailHeaderTitle = (TextView) viewFindViewById.findViewById(R.id.title);
            this.qsDetailHeaderSwitchStub = (ViewStub) viewFindViewById.findViewById(R.id.toggle_stub);
            this.qsDetailHeaderSwitch = null;
        } else {
            viewFindViewById = null;
        }
        this.qsDetailHeader = viewFindViewById;
        this.qsDetailHeaderProgress = (ProgressBar) secQSDetail.findViewById(R.id.qs_detail_header_progress);
        this.detailExtendedSummaryContainer = (ViewGroup) secQSDetail.findViewById(R.id.qs_detail_extended_summary_container);
        this.detailExtendedSummary = (TextView) secQSDetail.findViewById(R.id.qs_detail_extended_summary);
        this.qsDetailExtendedContainer = (LinearLayout) secQSDetail.findViewById(R.id.qs_detail_extended_container);
        if (isDNDTile()) {
            updateDndDetail();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateVisibility(DetailAdapter detailAdapter) {
        boolean z = true;
        boolean z2 = detailAdapter != null;
        if (z2) {
            if (!(detailAdapter != null ? detailAdapter.shouldUseFullScreen() : false)) {
            }
        } else {
            z = false;
        }
        if (z2) {
            SecQSDetail secQSDetail = this.view;
            if (z) {
                View viewFindViewById = secQSDetail.findViewById(R.id.qs_detail_extended_container);
                if (viewFindViewById != null) {
                    viewFindViewById.setVisibility(8);
                }
                View viewFindViewById2 = secQSDetail.findViewById(R.id.qs_detail_full_screen_container);
                if (viewFindViewById2 != null) {
                    viewFindViewById2.setVisibility(0);
                    return;
                }
                return;
            }
            View viewFindViewById3 = secQSDetail.findViewById(R.id.qs_detail_extended_container);
            if (viewFindViewById3 != null) {
                viewFindViewById3.setVisibility(0);
            }
            View viewFindViewById4 = secQSDetail.findViewById(R.id.qs_detail_full_screen_container);
            if (viewFindViewById4 != null) {
                viewFindViewById4.setVisibility(8);
            }
        }
    }
}
