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
import com.android.internal.logging.MetricsLogger;
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
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.qs.tiles.detail.DndDetailAdapter;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shared.shadow.DoubleShadowTextView;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.util.ViewController;
import java.util.ArrayList;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SecQSDetailController extends ViewController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public boolean closeDetailOnRelease;
    public final ColoredBGHelper coloredBGHelper;
    public final InterestingConfigChanges configChanges;
    public SecQSPanelControllerBase.Record currentRecord;
    public DetailAdapter detailAdapter;
    public Runnable detailAdapterDismissRunnable;
    public View detailButtonsDivider;
    public final SecQSDetailController$detailCallback$1 detailCallback;
    public final SecQSDetailController$detailCallbackForPopup$1 detailCallbackForPopup;
    public ViewGroup detailContent;
    public SecQSDetailContentView detailContentParent;
    public Button detailDoneButton;
    public LinearLayout detailExtendedContainer;
    public ViewGroup detailExtendedSummarContainer;
    public TextView detailExtendedSummary;
    public DoubleShadowTextView detailExtendedText;
    public QsDetailPopupAnimator detailPopupAnimator;
    public Button detailSettingsButton;
    public String detailTileSpec;
    public final SparseArray detailViews;
    public final SecQSDetailController$dndSummaryCallback$1 dndSummaryCallback;
    public final MetricsLogger metricsLogger;
    public int oldOrientation;
    public final SecQSDetailController$onConfigurationChangedListener$1 onConfigurationChangedListener;
    public SecQSPanelController panelController;
    public View qsDetailHeader;
    public ProgressBar qsDetailHeaderProgress;
    public SecQSSwitch qsDetailHeaderSwitch;
    public ViewStub qsDetailHeaderSwitchStub;
    public TextView qsDetailHeaderTitle;
    public final SecQSPanelResourcePicker resourcePicker;
    public boolean scanState;
    public final ShadeHeaderController shadeHeaderController;
    public final ShadeInteractor shadeInteractor;
    public final StatusBarStateControllerImpl statusBarStateController;
    public DetailAdapter switchAdapter;
    public boolean switchState;
    public final QSTileHost tileHost;
    public View toggleDivider;
    public QsTransitionAnimator transitionAnimator;
    public final SecQSDetail view;

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

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.qs.SecQSDetailController$detailCallback$1] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.qs.SecQSDetailController$detailCallbackForPopup$1] */
    public SecQSDetailController(SecQSDetail secQSDetail, ShadeInteractor shadeInteractor, StatusBarStateControllerImpl statusBarStateControllerImpl, SecQSPanelResourcePicker secQSPanelResourcePicker, ActivityStarter activityStarter, MetricsLogger metricsLogger, QSTileHost qSTileHost, ShadeHeaderController shadeHeaderController, ColoredBGHelper coloredBGHelper) {
        super(secQSDetail);
        this.view = secQSDetail;
        this.shadeInteractor = shadeInteractor;
        this.statusBarStateController = statusBarStateControllerImpl;
        this.resourcePicker = secQSPanelResourcePicker;
        this.activityStarter = activityStarter;
        this.metricsLogger = metricsLogger;
        this.tileHost = qSTileHost;
        this.shadeHeaderController = shadeHeaderController;
        this.coloredBGHelper = coloredBGHelper;
        this.configChanges = new InterestingConfigChanges(-1073671676);
        this.detailViews = new SparseArray();
        this.dndSummaryCallback = new SecQSDetailController$dndSummaryCallback$1(this);
        this.onConfigurationChangedListener = new SecQSDetailController$onConfigurationChangedListener$1(this);
        this.oldOrientation = -1;
        this.detailCallback = new QsTransitionAnimator.DetailCallback() { // from class: com.android.systemui.qs.SecQSDetailController$detailCallback$1
            @Override // com.android.systemui.qs.animator.QsTransitionAnimator.DetailCallback
            public final void hideDetailAnimEnd() {
                SecQSDetailController secQSDetailController = SecQSDetailController.this;
                Runnable runnable = secQSDetailController.detailAdapterDismissRunnable;
                if (runnable != null) {
                    runnable.run();
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
                Runnable runnable = secQSDetailController.detailAdapterDismissRunnable;
                if (runnable != null) {
                    runnable.run();
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
                if (view == null) {
                    return;
                }
                view.setVisibility(i);
            }
        }
    }

    public static final void updateDetailButtonText$update(TextView textView, int i) {
        FontSizeUtils.updateFontSize(textView, R.dimen.sec_qs_detail_button_text_size);
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

    public final boolean getQsExpanded() {
        return ((Boolean) ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.isQsExpanded().getValue()).booleanValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleShowingDetail(com.android.systemui.plugins.qs.DetailAdapter r11) {
        /*
            Method dump skipped, instructions count: 693
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.SecQSDetailController.handleShowingDetail(com.android.systemui.plugins.qs.DetailAdapter):void");
    }

    public final boolean isBluetoothOrWifiTile() {
        String str = this.detailTileSpec;
        return Intrinsics.areEqual(str, "Wifi") || Intrinsics.areEqual(str, "Bluetooth");
    }

    public final boolean isPortrait() {
        return getResources().getConfiguration().orientation == 1;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        super.onInit();
        updateMarginAndPadding();
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        SecQSDetail secQSDetail = this.view;
        ((ArrayList) secQSDetail.mOnConfigurationChangedListeners).add(this.onConfigurationChangedListener);
        secQSDetail.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.SecQSDetailController$onViewAttached$1$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                SecQSDetailController secQSDetailController = SecQSDetailController.this;
                Intrinsics.checkNotNull(motionEvent);
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
        if (QpRune.QUICK_TABLET) {
            return;
        }
        LinearLayout linearLayout = this.detailExtendedContainer;
        LinearLayout.LayoutParams layoutParams = null;
        if (linearLayout != null) {
            ViewGroup.LayoutParams layoutParams2 = linearLayout.getLayoutParams();
            LinearLayout.LayoutParams layoutParams3 = layoutParams2 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams2 : null;
            if (layoutParams3 != null) {
                layoutParams3.height = isPortrait() ? -2 : 0;
            } else {
                layoutParams3 = null;
            }
            linearLayout.setLayoutParams(layoutParams3);
        }
        SecQSDetailContentView secQSDetailContentView = this.detailContentParent;
        if (secQSDetailContentView != null) {
            ViewGroup.LayoutParams layoutParams4 = secQSDetailContentView.getLayoutParams();
            LinearLayout.LayoutParams layoutParams5 = layoutParams4 instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams4 : null;
            if (layoutParams5 != null) {
                layoutParams5.bottomMargin = isPortrait() ? 0 : toDp(R.dimen.sec_qs_detail_bottom_margin);
                layoutParams5.topMargin = isPortrait() ? toDp(R.dimen.qs_detail_margin_top) : 0;
                layoutParams = layoutParams5;
            }
            secQSDetailContentView.setLayoutParams(layoutParams);
        }
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
                /* JADX WARN: Removed duplicated region for block: B:12:0x0049  */
                /* JADX WARN: Removed duplicated region for block: B:14:0x004f  */
                /* JADX WARN: Removed duplicated region for block: B:15:0x0046  */
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
                        if (r0 == 0) goto L58
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
                        com.android.systemui.plugins.qs.SQSTile r5 = (com.android.systemui.plugins.qs.SQSTile) r5
                        goto L47
                    L46:
                        r5 = r1
                    L47:
                        if (r5 == 0) goto L4d
                        java.lang.String r1 = r5.getTileMapKey()
                    L4d:
                        if (r1 != 0) goto L58
                        int r5 = r0.getMetricsCategory()
                        java.lang.String r5 = java.lang.String.valueOf(r5)
                        r1 = r5
                    L58:
                        java.lang.String r5 = com.android.systemui.util.SystemUIAnalytics.getCurrentScreenID()
                        java.lang.String r0 = "QPDE1007"
                        com.android.systemui.util.SystemUIAnalytics.sendEventLog(r5, r0, r1)
                        com.android.systemui.qs.SecQSDetailController r5 = com.android.systemui.qs.SecQSDetailController.this
                        com.android.systemui.qs.SecQSDetail r0 = r5.view
                        android.content.Context r5 = r5.getContext()
                        r1 = 2131951747(0x7f130083, float:1.9539917E38)
                        java.lang.String r5 = r5.getString(r1)
                        r0.announceForAccessibility(r5)
                        com.android.systemui.plugins.qs.DetailAdapter r5 = r2
                        boolean r5 = r5.onDoneButtonClicked()
                        if (r5 != 0) goto L80
                        com.android.systemui.qs.SecQSDetailController r4 = com.android.systemui.qs.SecQSDetailController.this
                        r4.closeDetail()
                    L80:
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
                    /* JADX WARN: Removed duplicated region for block: B:10:0x0022  */
                    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
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
                            if (r0 != 0) goto L17
                            goto L1a
                        L17:
                            r0.setVisibility(r2)
                        L1a:
                            com.android.systemui.qs.SecQSDetailController r2 = com.android.systemui.qs.SecQSDetailController.this
                            boolean r2 = r2.isPortrait()
                            if (r2 == 0) goto L3c
                            if (r3 == 0) goto L28
                            r2 = 2131956490(0x7f13130a, float:1.9549537E38)
                            goto L2b
                        L28:
                            r2 = 2131956489(0x7f131309, float:1.9549535E38)
                        L2b:
                            com.android.systemui.qs.SecQSDetailController r3 = com.android.systemui.qs.SecQSDetailController.this
                            com.android.systemui.qs.SecQSSwitch r1 = r2
                            com.android.systemui.qs.SecQSDetail r3 = r3.view
                            android.content.Context r1 = r1.getContext()
                            java.lang.String r1 = r1.getString(r2)
                            r3.announceForAccessibility(r1)
                        L3c:
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
            SecQSDetailContentView secQSDetailContentView = this.detailContentParent;
            if (secQSDetailContentView != null) {
                secQSDetailContentView.mRecord = tileRecord;
            }
            this.detailTileSpec = z ? qSTile.getTileSpec() : "";
            if (isBluetoothOrWifiTile() && getQsExpanded()) {
                SecQSDetailContentView secQSDetailContentView2 = this.detailContentParent;
                if (secQSDetailContentView2 != null) {
                    secQSDetailContentView2.setBackground(null);
                }
            } else if (!QsAnimatorState.isDetailPopupShowing) {
                SecQSDetailContentView secQSDetailContentView3 = this.detailContentParent;
                if (!(secQSDetailContentView3 instanceof View)) {
                    secQSDetailContentView3 = null;
                }
                ColoredBGHelper coloredBGHelper = this.coloredBGHelper;
                coloredBGHelper.setBackGroundDrawable(secQSDetailContentView3, coloredBGHelper.getBGColor());
            }
            qSTile.setDetailListening(z);
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
        if (Intrinsics.areEqual(this.detailTileSpec, PluginLockShortcutTask.DO_NOT_DISTURB_TASK)) {
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
            if (isPortrait()) {
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
        DoubleShadowTextView doubleShadowTextView = this.detailExtendedText;
        if (doubleShadowTextView != null) {
            doubleShadowTextView.setText(obj2);
        }
        Log.d("SecQSDetailController", "updateDetailTitle" + obj2);
        boolean isPortrait = isPortrait();
        if (bool != null) {
            View view = this.qsDetailHeader;
            if (view != null) {
                if (isPortrait) {
                    view.setClickable(true);
                } else {
                    view.setClickable(false);
                    view.setBackground(null);
                }
            }
            TextView textView = this.qsDetailHeaderTitle;
            if (textView != null) {
                Pair pair = isPortrait ? bool.booleanValue() ? new Pair(textView.getContext().getString(R.string.sec_switch_bar_on), Integer.valueOf(R.color.sec_qs_detail_header_on_text_color)) : new Pair(textView.getContext().getString(R.string.sec_switch_bar_off), Integer.valueOf(R.color.sec_qs_detail_header_off_text_color)) : new Pair(obj2, Integer.valueOf(R.color.sec_qs_detail_header_text_color));
                textView.setText((CharSequence) pair.getFirst());
                textView.setTextColor(textView.getContext().getColor(((Number) pair.getSecond()).intValue()));
            }
            SecQSSwitch secQSSwitch = this.qsDetailHeaderSwitch;
            if (secQSSwitch != null) {
                secQSSwitch.setImportantForAccessibility(isPortrait ? 2 : 1);
            }
        } else {
            View view2 = this.qsDetailHeader;
            if (view2 != null) {
                if (isPortrait) {
                    view2.setVisibility(8);
                } else {
                    view2.setVisibility(0);
                    view2.setClickable(false);
                    view2.setBackground(null);
                }
            }
            TextView textView2 = this.qsDetailHeaderTitle;
            if (textView2 != null) {
                TextView textView3 = isPortrait ? null : textView2;
                if (textView3 != null) {
                    textView3.setText(obj2);
                    textView3.setTextColor(textView3.getContext().getColor(R.color.sec_qs_detail_header_text_color));
                }
            }
        }
        TextView textView4 = this.qsDetailHeaderTitle;
        if (textView4 != null) {
            textView4.setImportantForAccessibility(isPortrait ? 2 : 1);
        }
        View view3 = this.toggleDivider;
        if (view3 != null) {
            view3.setVisibility((!isPortrait || bool == null) ? 8 : 0);
        }
    }

    public final void updateDndDetail() {
        View view;
        if (!Intrinsics.areEqual(this.detailTileSpec, PluginLockShortcutTask.DO_NOT_DISTURB_TASK)) {
            ViewGroup viewGroup = this.detailExtendedSummarContainer;
            if (viewGroup != null) {
                viewGroup.setVisibility(8);
                return;
            }
            return;
        }
        if ((isPortrait() || QpRune.QUICK_TABLET) && (view = this.qsDetailHeader) != null) {
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
                textView.setText(detailAdapter != null ? ((DndDetailAdapter) detailAdapter).mDndTile.mDndMenuSummary : null);
            }
            ((DndDetailAdapter) this.detailAdapter).mSecQSDetailController = this;
        }
    }

    public final void updateMarginAndPadding() {
        View findViewById = ((SecQSDetail) this.mView).findViewById(R.id.panel_adjusted_detail);
        if (findViewById != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) findViewById.getLayoutParams();
            layoutParams.topMargin = this.shadeHeaderController.header.getHeight();
            SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
            SecQSDetail secQSDetail = this.view;
            layoutParams.bottomMargin = secQSPanelResourcePicker.getNavBarHeight(secQSDetail.getContext());
            layoutParams.width = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getPanelWidth(secQSDetail.getContext());
            Context context = ((SecQSDetail) this.mView).getContext();
            SecQSPanelResourcePicker secQSPanelResourcePicker2 = this.resourcePicker;
            findViewById.setPadding(secQSPanelResourcePicker2.resourcePickHelper.getTargetPicker().getDetailSidePadding(context), ((SecQSDetail) this.mView).getPaddingTop(), secQSPanelResourcePicker2.resourcePickHelper.getTargetPicker().getDetailSidePadding(((SecQSDetail) this.mView).getContext()), ((SecQSDetail) this.mView).getPaddingBottom());
        }
    }

    public final void updateViews(SecQSDetail secQSDetail) {
        this.detailContent = (ViewGroup) secQSDetail.findViewById(R.id.qs_detail_content);
        this.detailSettingsButton = (Button) secQSDetail.findViewById(R.id.detail_btn);
        this.detailDoneButton = (Button) secQSDetail.findViewById(R.id.done_btn);
        this.detailButtonsDivider = secQSDetail.findViewById(R.id.qs_detail_divider);
        this.detailExtendedContainer = (LinearLayout) secQSDetail.findViewById(R.id.qs_detail_extended_text_container);
        this.detailExtendedText = (DoubleShadowTextView) secQSDetail.findViewById(R.id.qs_detail_extended_text);
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
        if (Intrinsics.areEqual(this.detailTileSpec, PluginLockShortcutTask.DO_NOT_DISTURB_TASK)) {
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
            if (r2 == 0) goto L4c
            r5 = 2131364177(0x7f0a0951, float:1.8348184E38)
            r2 = 8
            r3 = 2131364172(0x7f0a094c, float:1.8348174E38)
            com.android.systemui.qs.SecQSDetail r4 = r4.view
            if (r0 == 0) goto L38
            android.view.View r0 = r4.findViewById(r3)
            if (r0 != 0) goto L2a
            goto L2d
        L2a:
            r0.setVisibility(r2)
        L2d:
            android.view.View r4 = r4.findViewById(r5)
            if (r4 != 0) goto L34
            goto L4c
        L34:
            r4.setVisibility(r1)
            goto L4c
        L38:
            android.view.View r0 = r4.findViewById(r3)
            if (r0 != 0) goto L3f
            goto L42
        L3f:
            r0.setVisibility(r1)
        L42:
            android.view.View r4 = r4.findViewById(r5)
            if (r4 != 0) goto L49
            goto L4c
        L49:
            r4.setVisibility(r2)
        L4c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.SecQSDetailController.updateVisibility(com.android.systemui.plugins.qs.DetailAdapter):void");
    }
}
