package com.android.systemui.qs;

import android.content.Context;
import android.graphics.Rect;
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
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.animator.QsDetailPopupAnimator;
import com.android.systemui.qs.animator.QsTransitionAnimator;
import com.android.systemui.qs.animator.SecQSImplAnimatorManager;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.tiles.detail.DndDetailAdapter;
import com.android.systemui.samsung.quicksetting.SecQSPanelComposeAdapter;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateChangeEvent;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor;
import com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecQSDetailController extends ViewController {
    public static final /* synthetic */ int $r8$clinit = 0;
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
    public ViewGroup detailExtendedSummarContainer;
    public TextView detailExtendedSummary;
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
                StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(i6, i8, "shadeHeaderLayoutChangeListener ", ",", " > ");
                m.append(i2);
                m.append(",");
                m.append(i4);
                Log.d("SecQSDetailController", m.toString());
                if (i8 - i6 != i4 - i2) {
                    SecQSDetailController secQSDetailController = SecQSDetailController.this;
                    int i9 = SecQSDetailController.$r8$clinit;
                    secQSDetailController.updateMarginAndPadding();
                }
            }
        };
        this.panelExpansionStateListener = new SecPanelExpansionStateListener() { // from class: com.android.systemui.qs.SecQSDetailController$panelExpansionStateListener$1
            @Override // com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener
            public final void onPanelExpansionStateChanged(SecPanelExpansionStateChangeEvent secPanelExpansionStateChangeEvent) {
                boolean z = secPanelExpansionStateChangeEvent.panelExpansionState == 1;
                SecQSDetailController.this.isPanelExpanding = z;
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onPanelExpansionStateChanged isPanelExpanding = ", "SecQSDetailController", z);
            }
        };
        this.detailCallback = new QsTransitionAnimator.DetailCallback() { // from class: com.android.systemui.qs.SecQSDetailController$detailCallback$1
            @Override // com.android.systemui.qs.animator.QsTransitionAnimator.DetailCallback
            public final void hideDetailAnimEnd() {
                SecQSDetailController secQSDetailController = SecQSDetailController.this;
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
            public final void showDetailAnimEnd() {
                SecQSDetailController secQSDetailController = SecQSDetailController.this;
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
            public final void hideDetailAnimEnd() {
                SecQSDetailController secQSDetailController = SecQSDetailController.this;
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
            public final void showDetailAnimEnd() {
                SecQSDetailController secQSDetailController = SecQSDetailController.this;
                boolean z = secQSDetailController.switchState;
                DetailAdapter detailAdapter = secQSDetailController.detailAdapter;
                boolean z2 = false;
                if (detailAdapter != null && detailAdapter.getToggleEnabled()) {
                    z2 = true;
                }
                SecQSDetailController.access$handleToggleStateChanged(secQSDetailController, z, z2);
            }
        };
        init();
    }

    public static final void access$handleToggleStateChanged(SecQSDetailController secQSDetailController, boolean z, boolean z2) {
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
            View createDetailView = detailAdapter.createDetailView(secQSDetailController.getContext(), (View) secQSDetailController.detailViews.get(metricsCategory), secQSDetailController.detailContent);
            if (createDetailView != null) {
                if (detailAdapter.shouldUseFullScreen()) {
                    ViewGroup viewGroup = secQSDetailController.toViewGroup(R.id.qs_detail_full_screen_container);
                    if (viewGroup != null) {
                        viewGroup.addView(createDetailView);
                    }
                } else {
                    ViewGroup viewGroup2 = secQSDetailController.detailContent;
                    if (viewGroup2 != null) {
                        viewGroup2.removeAllViews();
                        viewGroup2.addView(createDetailView);
                    }
                }
                secQSDetailController.detailViews.put(metricsCategory, createDetailView);
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

    public static boolean isLargeScreen$5() {
        return ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet();
    }

    public static final void updateDetailButtonText$update(TextView textView, int i) {
        FontSizeUtils.updateFontSize(textView, R.dimen.sec_qs_detail_button_text_size, 0.8f, 1.6f);
        if (textView != null) {
            textView.setText(i);
            textView.getTypeface().isLikeDefault = true;
            textView.setBackground(textView.getContext().getDrawable(R.drawable.sec_qs_btn_borderless_rect));
            textView.semSetButtonShapeEnabled(true);
        }
    }

    public final void closeDetail() {
        this.closeDetailOnRelease = false;
        SecQSPanelControllerBase.Record record = this.currentRecord;
        if (record != null) {
            showDetail(false, record);
        }
    }

    public final void closeTargetDetail(DetailAdapter detailAdapter) {
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
    /* JADX WARN: Removed duplicated region for block: B:102:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01ae  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleShowingDetail(com.android.systemui.plugins.qs.DetailAdapter r11) {
        /*
            Method dump skipped, instructions count: 443
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.SecQSDetailController.handleShowingDetail(com.android.systemui.plugins.qs.DetailAdapter):void");
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
        return getResources().getConfiguration().orientation == 1 || (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && isLargeScreen$5());
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        updateMarginAndPadding();
        this.panelExpansionStateInteractor.registerListener(this.panelExpansionStateListener);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        SecQSDetail secQSDetail = this.view;
        ((ArrayList) secQSDetail.mOnConfigurationChangedListeners).add(this.onConfigurationChangedListener);
        secQSDetail.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.SecQSDetailController$onViewAttached$1$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                SecQSDetailController secQSDetailController = SecQSDetailController.this;
                motionEvent.getClass();
                int i = SecQSDetailController.$r8$clinit;
                secQSDetailController.getClass();
                if (motionEvent.getAction() == 0) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    Rect rect = new Rect();
                    View findViewById = secQSDetailController.view.findViewById(R.id.qs_detail_extended_container);
                    if (findViewById != null) {
                        findViewById.getGlobalVisibleRect(rect);
                    }
                    secQSDetailController.closeDetailOnRelease = !rect.contains((int) x, (int) y);
                } else if (motionEvent.getAction() == 1 && secQSDetailController.closeDetailOnRelease) {
                    SecQSDetailController.this.closeDetail();
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

    public final void setDetailExtendedContainerHeight() {
        int measuredHeight;
        boolean z = QpRune.QUICK_TABLET;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        if (z) {
            LinearLayout linearLayout = this.detailExtendedContainer;
            if (linearLayout != null) {
                ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
                LinearLayout.LayoutParams layoutParams2 = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : null;
                if (layoutParams2 != null) {
                    layoutParams2.bottomMargin = toDp(R.dimen.sec_qs_detail_header_bottom_margin_tablet);
                }
            }
            boolean isBluetoothOrWifiTile = isBluetoothOrWifiTile();
            int detailContentViewMaxHeight = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getDetailContentViewMaxHeight(getContext());
            Context context = getContext();
            SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = secQSPanelResourcePicker.resourcePickHelper;
            int detailContentViewMinHeight = secQSPanelResourcePickHelper.getTargetPicker().getDetailContentViewMinHeight(context);
            SecQSDetailContentView secQSDetailContentView = this.detailContentParent;
            if (secQSDetailContentView != null) {
                ViewGroup.LayoutParams layoutParams3 = secQSDetailContentView.getLayoutParams();
                LinearLayout.LayoutParams layoutParams4 = layoutParams3 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams3 : null;
                if (layoutParams4 != null) {
                    secQSDetailContentView.setMinimumHeight(detailContentViewMinHeight);
                    layoutParams4.height = isBluetoothOrWifiTile ? detailContentViewMaxHeight : -2;
                }
            }
            LinearLayout linearLayout2 = this.qsDetailExtendedContainer;
            if (linearLayout2 != null) {
                linearLayout2.measure(0, 0);
                ViewGroup.LayoutParams layoutParams5 = linearLayout2.getLayoutParams();
                r4 = layoutParams5 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams5 : null;
                if (r4 != null) {
                    r4.gravity = 49;
                    if (isLargeScreen$5()) {
                        measuredHeight = secQSPanelResourcePickHelper.getTargetPicker().getDetailExtendedContainerTopMargin(linearLayout2.getContext());
                    } else {
                        measuredHeight = (int) ((((linearLayout2.getResources().getDisplayMetrics().heightPixels - linearLayout2.getMeasuredHeight()) - this.shadeHeaderController.header.getMeasuredHeight()) - ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getNavBarHeight(this.view.getContext())) * (isPortrait$1() ? 0.19455254f : 0.5f));
                    }
                    r4.topMargin = measuredHeight;
                    return;
                }
                return;
            }
            return;
        }
        LinearLayout linearLayout3 = this.detailExtendedContainer;
        if (linearLayout3 != null) {
            ViewGroup.LayoutParams layoutParams6 = linearLayout3.getLayoutParams();
            LinearLayout.LayoutParams layoutParams7 = layoutParams6 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams6 : null;
            if (layoutParams7 != null) {
                layoutParams7.height = isPortrait$1() ? -2 : 0;
            } else {
                layoutParams7 = null;
            }
            linearLayout3.setLayoutParams(layoutParams7);
        }
        boolean isBluetoothOrWifiTile2 = isBluetoothOrWifiTile();
        int detailContentViewMaxHeight2 = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getDetailContentViewMaxHeight(getContext());
        SecQSDetailContentView secQSDetailContentView2 = this.detailContentParent;
        if (secQSDetailContentView2 != null) {
            ViewGroup.LayoutParams layoutParams8 = secQSDetailContentView2.getLayoutParams();
            LinearLayout.LayoutParams layoutParams9 = layoutParams8 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams8 : null;
            if (layoutParams9 != null) {
                layoutParams9.bottomMargin = isPortrait$1() ? 0 : toDp(R.dimen.sec_qs_detail_bottom_margin);
                layoutParams9.topMargin = isPortrait$1() ? toDp(R.dimen.qs_detail_margin_top) : 0;
                if (isBluetoothOrWifiTile2) {
                    if (isPortrait$1()) {
                        r1 = detailContentViewMaxHeight2;
                    } else {
                        View view = this.scrollView;
                        r1 = (view != null ? view.getHeight() : 0) - layoutParams9.bottomMargin;
                    }
                }
                layoutParams9.height = r1;
            } else {
                layoutParams9 = null;
            }
            secQSDetailContentView2.setLayoutParams(layoutParams9);
        }
        ViewGroup viewGroup = this.detailExtendedSummarContainer;
        if (viewGroup != null) {
            ViewGroup.LayoutParams layoutParams10 = viewGroup.getLayoutParams();
            LinearLayout.LayoutParams layoutParams11 = layoutParams10 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams10 : null;
            if (layoutParams11 != null) {
                layoutParams11.bottomMargin = isPortrait$1() ? 0 : toDp(R.dimen.sec_qs_detail_bottom_extended_margin);
            } else {
                layoutParams11 = null;
            }
            viewGroup.setLayoutParams(layoutParams11);
        }
        TextView textView = this.detailExtendedSummary;
        if (textView != null) {
            ViewGroup.LayoutParams layoutParams12 = textView.getLayoutParams();
            LinearLayout.LayoutParams layoutParams13 = layoutParams12 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams12 : null;
            if (layoutParams13 != null) {
                layoutParams13.gravity = isPortrait$1() ? 17 : 8388611;
                r4 = layoutParams13;
            }
            textView.setLayoutParams(r4);
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
                    SecQSDetailController.this.activityStarter.postStartActivityDismissingKeyguard(detailAdapter.getSettingsIntent(), 0);
                }
            });
        }
        Button button2 = this.detailDoneButton;
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.SecQSDetailController$setupDetailFooter$2
                /* JADX WARN: Removed duplicated region for block: B:10:0x0043  */
                @Override // android.view.View.OnClickListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onClick(android.view.View r5) {
                    /*
                        r4 = this;
                        com.android.systemui.qs.SecQSDetailController r5 = com.android.systemui.qs.SecQSDetailController.this
                        com.android.systemui.plugins.qs.DetailAdapter r0 = r5.detailAdapter
                        r1 = 0
                        if (r0 == 0) goto L5a
                        com.android.systemui.qs.SecQSPanelController r5 = r5.panelController
                        if (r5 == 0) goto L3e
                        com.android.systemui.qs.QSPanelHost r2 = r5.mQsPanelHost
                        r2.getClass()
                        com.android.systemui.qs.SecQSDetailController r5 = r5.mDetailController
                        com.android.systemui.qs.SecQSPanelControllerBase$Record r5 = r5.currentRecord
                        boolean r5 = r5 instanceof com.android.systemui.qs.SecQSPanelControllerBase.TileRecord
                        if (r5 != 0) goto L19
                        goto L3e
                    L19:
                        java.util.ArrayList r5 = r2.mRecords
                        java.util.stream.Stream r5 = r5.stream()
                        com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda6 r2 = new com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda6
                        r3 = 2
                        r2.<init>(r3)
                        java.util.stream.Stream r5 = r5.map(r2)
                        com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda25 r2 = new com.android.systemui.qs.QSPanelHost$$ExternalSyntheticLambda25
                        r3 = 0
                        r2.<init>(r0, r3)
                        java.util.stream.Stream r5 = r5.filter(r2)
                        java.util.Optional r5 = r5.findFirst()
                        java.lang.Object r5 = r5.orElse(r1)
                        com.android.systemui.plugins.qs.QSTile r5 = (com.android.systemui.plugins.qs.QSTile) r5
                        goto L3f
                    L3e:
                        r5 = r1
                    L3f:
                        boolean r2 = r5 instanceof com.android.systemui.plugins.qs.SQSTile
                        if (r2 == 0) goto L46
                        r1 = r5
                        com.android.systemui.plugins.qs.SQSTile r1 = (com.android.systemui.plugins.qs.SQSTile) r1
                    L46:
                        if (r1 == 0) goto L51
                        java.lang.String r5 = r1.getTileMapKey()
                        if (r5 != 0) goto L4f
                        goto L51
                    L4f:
                        r1 = r5
                        goto L5a
                    L51:
                        int r5 = r0.getMetricsCategory()
                        java.lang.String r5 = java.lang.String.valueOf(r5)
                        goto L4f
                    L5a:
                        java.lang.String r5 = com.android.systemui.util.SystemUIAnalytics.getCurrentScreenID()
                        java.lang.String r0 = "QPDE1007"
                        com.android.systemui.util.SystemUIAnalytics.sendEventLog(r5, r0, r1)
                        com.android.systemui.qs.SecQSDetailController r5 = com.android.systemui.qs.SecQSDetailController.this
                        com.android.systemui.qs.SecQSDetail r0 = r5.view
                        android.content.Context r5 = r5.getContext()
                        r1 = 2131951762(0x7f130092, float:1.9539948E38)
                        java.lang.String r5 = r5.getString(r1)
                        r0.announceForAccessibility(r5)
                        com.android.systemui.plugins.qs.DetailAdapter r5 = r2
                        boolean r5 = r5.onDoneButtonClicked()
                        if (r5 != 0) goto L82
                        com.android.systemui.qs.SecQSDetailController r4 = com.android.systemui.qs.SecQSDetailController.this
                        r4.closeDetail()
                    L82:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.SecQSDetailController$setupDetailFooter$2.onClick(android.view.View):void");
                }
            });
        }
    }

    public final void setupDetailHeader(final DetailAdapter detailAdapter) {
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
                    View inflate = viewStub.inflate();
                    this.qsDetailHeaderSwitch = inflate instanceof SecQSSwitch ? (SecQSSwitch) inflate : null;
                }
            }
            this.switchAdapter = detailAdapter;
            this.switchState = toggleState.booleanValue();
            boolean booleanValue = toggleState.booleanValue();
            SecQSSwitch secQSSwitch2 = this.qsDetailHeaderSwitch;
            if (secQSSwitch2 != null) {
                secQSSwitch2.setChecked(booleanValue);
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
                        SecQSSwitch secQSSwitch3 = SecQSDetailController.this.qsDetailHeaderSwitch;
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
                        DetailAdapter.this.setToggleState(secQSSwitch3.isChecked());
                    }
                });
                secQSSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qs.SecQSDetailController$setupHeaderSwitchListener$1$2
                    /* JADX WARN: Removed duplicated region for block: B:10:0x0021  */
                    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onCheckedChanged(android.widget.CompoundButton r2, boolean r3) {
                        /*
                            r1 = this;
                            if (r3 == 0) goto Le
                            com.android.systemui.qs.SecQSDetailController r2 = com.android.systemui.qs.SecQSDetailController.this
                            int r0 = com.android.systemui.qs.SecQSDetailController.$r8$clinit
                            boolean r2 = r2.isBluetoothOrWifiTile()
                            if (r2 == 0) goto Le
                            r2 = 0
                            goto L10
                        Le:
                            r2 = 8
                        L10:
                            com.android.systemui.qs.SecQSDetailController r0 = com.android.systemui.qs.SecQSDetailController.this
                            android.widget.ProgressBar r0 = r0.qsDetailHeaderProgress
                            if (r0 == 0) goto L19
                            r0.setVisibility(r2)
                        L19:
                            com.android.systemui.qs.SecQSDetailController r2 = com.android.systemui.qs.SecQSDetailController.this
                            boolean r2 = r2.isPortrait$1()
                            if (r2 == 0) goto L3b
                            if (r3 == 0) goto L27
                            r2 = 2131957033(0x7f131529, float:1.9550639E38)
                            goto L2a
                        L27:
                            r2 = 2131957032(0x7f131528, float:1.9550636E38)
                        L2a:
                            com.android.systemui.qs.SecQSDetailController r3 = com.android.systemui.qs.SecQSDetailController.this
                            com.android.systemui.qs.SecQSSwitch r1 = r2
                            com.android.systemui.qs.SecQSDetail r3 = r3.view
                            android.content.Context r1 = r1.getContext()
                            java.lang.String r1 = r1.getString(r2)
                            r3.announceForAccessibility(r1)
                        L3b:
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.SecQSDetailController$setupHeaderSwitchListener$1$2.onCheckedChanged(android.widget.CompoundButton, boolean):void");
                    }
                });
            }
        }
        updateDetailTitle(toggleState, detailAdapter.getTitle());
    }

    public final void showDetail(boolean z, SecQSPanelControllerBase.Record record) {
        Pair pair;
        if (z && (QsAnimatorState.isCustomizerShowing || QsAnimatorState.isSliding || QsAnimatorState.isDetailPopupShowing || QsAnimatorState.isDetailShowing || QsAnimatorState.isDetailOpening || this.isPanelExpanding)) {
            String str = QsAnimatorState.isCustomizerShowing ? "customizer is showing," : "";
            String str2 = QsAnimatorState.isSliding ? "isSliding," : "";
            String str3 = QsAnimatorState.isDetailPopupShowing ? "detail popup is showing," : "";
            String str4 = QsAnimatorState.isDetailShowing ? "detail is showing," : "";
            String str5 = QsAnimatorState.isDetailOpening ? "detail is opening," : "";
            String str6 = this.isPanelExpanding ? "panel is opening," : "";
            StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("showDetail ", str, str2, str3, str4);
            m.append(str5);
            m.append(str6);
            m.append(" ignore detail show request");
            Log.d("SecQSDetailController", m.toString());
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
            if (!isLargeScreen$5() && !QpRune.QUICK_PANEL_BLUR_MASSIVE) {
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

    public final void showTargetDetail(DetailAdapter detailAdapter) {
        SecQSPanelControllerBase.Record record = new SecQSPanelControllerBase.Record();
        record.mDetailAdapter = detailAdapter;
        Unit unit = Unit.INSTANCE;
        showDetail(true, record);
    }

    public final int toDp(int i) {
        return getResources().getDimensionPixelSize(i);
    }

    public final ViewGroup toViewGroup(int i) {
        View findViewById = this.view.findViewById(i);
        if (findViewById instanceof ViewGroup) {
            return (ViewGroup) findViewById;
        }
        return null;
    }

    public final void updateDetailButtonText() {
        if (isDNDTile()) {
            ViewGroup viewGroup = this.detailExtendedSummarContainer;
            if (viewGroup != null) {
                viewGroup.setVisibility(0);
            }
            TextView textView = this.detailExtendedSummary;
            if (textView != null) {
                textView.setVisibility(0);
            }
        } else {
            ViewGroup viewGroup2 = this.detailExtendedSummarContainer;
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
            if (isPortrait$1()) {
                ViewGroup viewGroup2 = toViewGroup(R.id.qs_detail_parent);
                if (viewGroup2 != null) {
                    viewGroup2.addView(view, 0);
                }
                if (layoutParams2 != null) {
                    layoutParams2.height = toDp(R.dimen.sec_qs_detail_header_height);
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
                layoutParams2.height = toDp(R.dimen.sec_qs_detail_header_height);
                layoutParams2.topMargin = toDp(R.dimen.sec_qs_detail_header_marginVertical);
                layoutParams2.bottomMargin = toDp(R.dimen.sec_qs_detail_header_marginVertical);
            }
        }
    }

    public final void updateDetailTitle(Boolean bool, CharSequence charSequence) {
        if (charSequence == null || charSequence.toString().length() == 0) {
            return;
        }
        String obj = charSequence.toString();
        int length = obj.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean z2 = Intrinsics.compare(obj.charAt(!z ? i : length), 32) <= 0;
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
        String obj2 = obj.subSequence(i, length + 1).toString();
        TextView textView = this.detailExtendedText;
        if (textView != null) {
            textView.setText(obj2);
        }
        Log.d("SecQSDetailController", "updateDetailTitle" + obj2);
        boolean isPortrait$1 = isPortrait$1();
        if (bool != null) {
            View view = this.qsDetailHeader;
            if (view != null) {
                if (isPortrait$1) {
                    view.setClickable(true);
                } else {
                    view.setClickable(false);
                    view.setBackground(null);
                }
            }
            TextView textView2 = this.qsDetailHeaderTitle;
            if (textView2 != null) {
                Pair pair = isPortrait$1 ? bool.booleanValue() ? new Pair(textView2.getContext().getString(R.string.sec_switch_bar_on), Integer.valueOf(R.color.sec_qs_detail_header_on_text_color)) : new Pair(textView2.getContext().getString(R.string.sec_switch_bar_off), Integer.valueOf(R.color.sec_qs_detail_header_off_text_color)) : new Pair(obj2, Integer.valueOf(R.color.sec_qs_detail_header_text_color));
                textView2.setText((CharSequence) pair.getFirst());
                textView2.setTextColor(textView2.getContext().getColor(((Number) pair.getSecond()).intValue()));
                FontSizeUtils.updateFontSize(textView2, R.dimen.sec_qs_detail_header_text_size, 0.8f, 1.6f);
            }
            SecQSSwitch secQSSwitch = this.qsDetailHeaderSwitch;
            if (secQSSwitch != null) {
                secQSSwitch.setImportantForAccessibility(isPortrait$1 ? 2 : 1);
            }
        } else {
            View view2 = this.qsDetailHeader;
            if (view2 != null) {
                if (isPortrait$1) {
                    view2.setVisibility(8);
                } else {
                    view2.setVisibility(0);
                    view2.setClickable(false);
                    view2.setBackground(null);
                }
            }
            TextView textView3 = this.qsDetailHeaderTitle;
            if (textView3 != null) {
                TextView textView4 = isPortrait$1 ? null : textView3;
                if (textView4 != null) {
                    textView4.setText(obj2);
                    textView4.setTextColor(textView4.getContext().getColor(R.color.sec_qs_detail_header_text_color));
                }
            }
        }
        TextView textView5 = this.qsDetailHeaderTitle;
        if (textView5 != null) {
            textView5.setImportantForAccessibility(isPortrait$1 ? 2 : 1);
        }
        View view3 = this.toggleDivider;
        if (view3 != null) {
            view3.setVisibility((!isPortrait$1 || bool == null) ? 8 : 0);
        }
    }

    public final void updateDndDetail() {
        View view;
        if (!isDNDTile()) {
            ViewGroup viewGroup = this.detailExtendedSummarContainer;
            if (viewGroup != null) {
                viewGroup.setVisibility(8);
                return;
            }
            return;
        }
        if ((isPortrait$1() || QpRune.QUICK_TABLET) && (view = this.qsDetailHeader) != null) {
            view.setVisibility(8);
        }
        ViewGroup viewGroup2 = this.detailExtendedSummarContainer;
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
        boolean isLargeScreen$5 = isLargeScreen$5();
        ShadeHeaderController shadeHeaderController = this.shadeHeaderController;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        SecQSDetail secQSDetail = this.view;
        if (!isLargeScreen$5) {
            View findViewById = ((SecQSDetail) this.mView).findViewById(R.id.panel_adjusted_detail);
            if (findViewById != null) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) findViewById.getLayoutParams();
                layoutParams.topMargin = shadeHeaderController.header.getMeasuredHeight();
                layoutParams.bottomMargin = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getNavBarHeight(secQSDetail.getContext());
                layoutParams.width = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getPanelWidth(secQSDetail.getContext());
                findViewById.setPadding(secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getDetailSidePadding(((SecQSDetail) this.mView).getContext()), ((SecQSDetail) this.mView).getPaddingTop(), secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getDetailSidePadding(((SecQSDetail) this.mView).getContext()), ((SecQSDetail) this.mView).getPaddingBottom());
                return;
            }
            return;
        }
        SecQSDetail secQSDetail2 = (SecQSDetail) this.mView;
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) secQSDetail2.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = shadeHeaderController.header.getMeasuredHeight();
        ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getNavBarHeight(secQSDetail.getContext());
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getPanelWidth(secQSDetail.getContext());
        secQSDetail2.setPadding(secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getDetailSidePadding(((SecQSDetail) this.mView).getContext()), ((SecQSDetail) this.mView).getPaddingTop(), secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getDetailSidePadding(((SecQSDetail) this.mView).getContext()), ((SecQSDetail) this.mView).getPaddingBottom());
        View findViewById2 = ((SecQSDetail) this.mView).findViewById(R.id.qs_detail_container);
        if (findViewById2 != null) {
            findViewById2.getLayoutParams().height = SecQSDetailController$$ExternalSyntheticOutline0.m(findViewById2, R.dimen.qs_pop_over_blur_detail_height);
            findViewById2.setPadding(findViewById2.getPaddingLeft(), findViewById2.getPaddingTop(), findViewById2.getPaddingRight(), findViewById2.getContext().getResources().getDimensionPixelSize(R.dimen.qs_affordance_arrow_translation_y_tablet));
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
        View findViewById = secQSDetail.findViewById(R.id.qs_detail_header);
        if (findViewById != null) {
            this.qsDetailHeaderTitle = (TextView) findViewById.findViewById(R.id.title);
            this.qsDetailHeaderSwitchStub = (ViewStub) findViewById.findViewById(R.id.toggle_stub);
            this.qsDetailHeaderSwitch = null;
        } else {
            findViewById = null;
        }
        this.qsDetailHeader = findViewById;
        this.qsDetailHeaderProgress = (ProgressBar) secQSDetail.findViewById(R.id.qs_detail_header_progress);
        this.detailExtendedSummarContainer = (ViewGroup) secQSDetail.findViewById(R.id.qs_detail_extended_summary_container);
        this.detailExtendedSummary = (TextView) secQSDetail.findViewById(R.id.qs_detail_extended_summary);
        this.qsDetailExtendedContainer = (LinearLayout) secQSDetail.findViewById(R.id.qs_detail_extended_container);
        if (isDNDTile()) {
            updateDndDetail();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0011, code lost:
    
        if ((r5 != null ? r5.shouldUseFullScreen() : false) != false) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateVisibility(com.android.systemui.plugins.qs.DetailAdapter r5) {
        /*
            r4 = this;
            r0 = 1
            r1 = 0
            if (r5 == 0) goto L6
            r2 = r0
            goto L7
        L6:
            r2 = r1
        L7:
            if (r2 == 0) goto L14
            if (r5 == 0) goto L10
            boolean r5 = r5.shouldUseFullScreen()
            goto L11
        L10:
            r5 = r1
        L11:
            if (r5 == 0) goto L14
            goto L15
        L14:
            r0 = r1
        L15:
            if (r2 == 0) goto L48
            r5 = 2131364348(0x7f0a09fc, float:1.834853E38)
            r2 = 8
            r3 = 2131364343(0x7f0a09f7, float:1.834852E38)
            com.android.systemui.qs.SecQSDetail r4 = r4.view
            if (r0 == 0) goto L36
            android.view.View r0 = r4.findViewById(r3)
            if (r0 == 0) goto L2c
            r0.setVisibility(r2)
        L2c:
            android.view.View r4 = r4.findViewById(r5)
            if (r4 == 0) goto L48
            r4.setVisibility(r1)
            return
        L36:
            android.view.View r0 = r4.findViewById(r3)
            if (r0 == 0) goto L3f
            r0.setVisibility(r1)
        L3f:
            android.view.View r4 = r4.findViewById(r5)
            if (r4 == 0) goto L48
            r4.setVisibility(r2)
        L48:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.SecQSDetailController.updateVisibility(com.android.systemui.plugins.qs.DetailAdapter):void");
    }
}
