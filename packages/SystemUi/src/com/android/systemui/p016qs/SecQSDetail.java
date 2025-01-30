package com.android.systemui.p016qs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.WindowInsets;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.p016qs.SecDarkModeEasel;
import com.android.systemui.p016qs.animator.QsTransitionAnimator;
import com.android.systemui.p016qs.animator.SecQSFragmentAnimatorManager;
import com.android.systemui.p016qs.animator.SecQSFragmentAnimatorManager$$ExternalSyntheticLambda1;
import com.android.systemui.p016qs.tiles.DndTile;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.DetailAdapter;
import com.android.systemui.plugins.p013qs.SQSTile;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shared.shadow.DoubleShadowTextView;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.configuration.DATA;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SecQSDetail extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAnimatingOpen;
    QSDetailClipper mClipper;
    public int mCutOutHeight;
    public final SecDarkModeEasel mDarkModelEasel;
    public DetailAdapter mDetailAdapter;
    public LinearLayout mDetailButtons;
    public View mDetailButtonsDivider;
    public ViewGroup mDetailContent;
    public SecQSDetailContentView mDetailContentParent;
    public Button mDetailDoneButton;
    public LinearLayout mDetailExtended;
    public ViewGroup mDetailExtendedContainer;
    public TextView mDetailExtendedSummary;
    public ViewGroup mDetailExtendedSummaryContainer;
    public DoubleShadowTextView mDetailExtendedText;
    public Button mDetailSettingsButton;
    public final SparseArray mDetailViews;
    public FalsingManager mFalsingManager;
    public SecQuickStatusBarHeader mHeader;
    public final C20693 mHideGridContentWhenDone;
    public int mOrientation;
    public View mQsDetailHeader;
    public ProgressBar mQsDetailHeaderProgress;
    public SecQSSwitch mQsDetailHeaderSwitch;
    public ViewStub mQsDetailHeaderSwitchStub;
    public TextView mQsDetailHeaderTitle;
    public boolean mQsExpanded;
    public final C20682 mQsPanelCallback;
    public SecQSPanelController mQsPanelController;
    public final SecQSPanelResourcePicker mResourcePicker;
    public boolean mScanState;
    public ScrollView mScrollView;
    public DetailAdapter mSwitchAdapter;
    public boolean mSwitchState;
    public final C20704 mTeardownDetailWhenDone;
    public View mToggleDivider;
    public QsTransitionAnimator mTransitionAnimator;
    public boolean mTriggeredExpand;
    public final UiEventLogger mUiEventLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.SecQSDetail$2 */
    public final class C20682 {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.systemui.qs.SecQSDetail$2$3, reason: invalid class name */
        public final class AnonymousClass3 implements Runnable {
            public final /* synthetic */ boolean val$state;

            public AnonymousClass3(boolean z) {
                this.val$state = z;
            }

            @Override // java.lang.Runnable
            public final void run() {
                String str;
                SecQSDetail secQSDetail = SecQSDetail.this;
                boolean z = this.val$state;
                if (secQSDetail.mScanState == z) {
                    return;
                }
                secQSDetail.mScanState = z;
                if (z) {
                    SecQSPanelController secQSPanelController = secQSDetail.mQsPanelController;
                    if (secQSPanelController.mDetailRecord == null || (str = secQSPanelController.mDetailTileSpec) == null) {
                        str = "";
                    }
                    if (str.equals("Wifi") || str.equals("Bluetooth")) {
                        secQSDetail.mQsDetailHeaderProgress.setVisibility(0);
                        return;
                    }
                }
                secQSDetail.mQsDetailHeaderProgress.setVisibility(8);
            }
        }

        public C20682() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.qs.SecQSDetail$5 */
    public final class C20715 {
        public C20715() {
        }
    }

    /* renamed from: $r8$lambda$1uAO_h_OA3h2MPW8fBnCc4-4CwE, reason: not valid java name */
    public static void m1619$r8$lambda$1uAO_h_OA3h2MPW8fBnCc44CwE(SecQSDetail secQSDetail, DetailAdapter detailAdapter) {
        if (secQSDetail.mFalsingManager.isFalseTap(1)) {
            return;
        }
        secQSDetail.announceForAccessibility(((LinearLayout) secQSDetail).mContext.getString(R.string.accessibility_desc_quick_settings));
        if (detailAdapter.onDoneButtonClicked()) {
            return;
        }
        SecQSPanelController secQSPanelController = secQSDetail.mQsPanelController;
        secQSPanelController.showDetail(secQSPanelController.mDetailRecord, false);
    }

    public static void $r8$lambda$GqF35b0Ptg1YPaUYppLYwuej_yI(final SecQSDetail secQSDetail) {
        Drawable verticalScrollbarThumbDrawable;
        DoubleShadowTextView doubleShadowTextView = secQSDetail.mDetailExtendedText;
        if (doubleShadowTextView != null) {
            doubleShadowTextView.setTextColor(((LinearLayout) secQSDetail).mContext.getColor(R.color.sec_qs_detail_header_text_color));
        }
        SecQSDetailContentView secQSDetailContentView = secQSDetail.mDetailContentParent;
        if (secQSDetailContentView != null) {
            secQSDetailContentView.setBackground(((LinearLayout) secQSDetail).mContext.getDrawable(R.drawable.sec_qs_detailed_container_background));
        }
        SparseArray sparseArray = secQSDetail.mDetailViews;
        if (sparseArray != null) {
            sparseArray.clear();
        }
        DetailAdapter detailAdapter = secQSDetail.mDetailAdapter;
        if (detailAdapter != null) {
            secQSDetail.updateDetailTitle(detailAdapter.getToggleState(), secQSDetail.mDetailAdapter.getTitle());
            secQSDetail.post(new Runnable() { // from class: com.android.systemui.qs.SecQSDetail$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    SecQSDetail secQSDetail2 = SecQSDetail.this;
                    secQSDetail2.handleUpdatingDetail(secQSDetail2.mDetailAdapter);
                }
            });
        }
        Button button = secQSDetail.mDetailSettingsButton;
        if (button != null) {
            button.setTextColor(((LinearLayout) secQSDetail).mContext.getColor(R.color.sec_qs_detail_action_button));
        }
        Button button2 = secQSDetail.mDetailDoneButton;
        if (button2 != null) {
            button2.setTextColor(((LinearLayout) secQSDetail).mContext.getColor(R.color.sec_qs_detail_action_button));
        }
        View view = secQSDetail.mDetailButtonsDivider;
        if (view != null) {
            view.setBackgroundColor(((LinearLayout) secQSDetail).mContext.getColor(R.color.sec_qs_detail_buttons_divider));
        }
        ScrollView scrollView = secQSDetail.mScrollView;
        if (scrollView != null && (verticalScrollbarThumbDrawable = scrollView.getVerticalScrollbarThumbDrawable()) != null) {
            verticalScrollbarThumbDrawable.setTint(((LinearLayout) secQSDetail).mContext.getColor(R.color.tw_scrollbar_handle_tint_color_mtrl));
            secQSDetail.mScrollView.setVerticalScrollbarThumbDrawable(verticalScrollbarThumbDrawable);
        }
        ProgressBar progressBar = secQSDetail.mQsDetailHeaderProgress;
        if (progressBar != null) {
            Context context = progressBar.getContext();
            ViewParent parent = secQSDetail.mQsDetailHeaderProgress.getParent();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) secQSDetail.mQsDetailHeaderProgress.getLayoutParams();
            ViewGroup viewGroup = (ViewGroup) parent;
            int indexOfChild = viewGroup.indexOfChild(secQSDetail.mQsDetailHeaderProgress);
            int visibility = secQSDetail.mQsDetailHeaderProgress.getVisibility();
            viewGroup.removeView(secQSDetail.mQsDetailHeaderProgress);
            ProgressBar progressBar2 = new ProgressBar(context);
            secQSDetail.mQsDetailHeaderProgress = progressBar2;
            viewGroup.addView(progressBar2, indexOfChild, layoutParams);
            secQSDetail.mQsDetailHeaderProgress.setVisibility(visibility);
        }
        SecQSSwitch secQSSwitch = secQSDetail.mQsDetailHeaderSwitch;
        if (secQSSwitch == null || secQSDetail.mSwitchAdapter == null) {
            return;
        }
        Context context2 = secQSSwitch.getContext();
        ViewParent parent2 = secQSDetail.mQsDetailHeaderSwitch.getParent();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) secQSDetail.mQsDetailHeaderSwitch.getLayoutParams();
        ViewGroup viewGroup2 = (ViewGroup) parent2;
        int indexOfChild2 = viewGroup2.indexOfChild(secQSDetail.mQsDetailHeaderSwitch);
        viewGroup2.removeView(secQSDetail.mQsDetailHeaderSwitch);
        SecQSSwitch secQSSwitch2 = (SecQSSwitch) LayoutInflater.from(context2).inflate(R.layout.sec_qs_detail_switch, viewGroup2, false);
        secQSDetail.mQsDetailHeaderSwitch = secQSSwitch2;
        viewGroup2.addView(secQSSwitch2, indexOfChild2, layoutParams2);
        secQSDetail.setupHeaderSwitch(secQSDetail.mSwitchAdapter, Boolean.valueOf(secQSDetail.mSwitchState));
        DetailAdapter detailAdapter2 = secQSDetail.mSwitchAdapter;
        secQSDetail.mQsDetailHeaderSwitch.setOnClickListener(new SecQSDetail$$ExternalSyntheticLambda2(secQSDetail, detailAdapter2, 2));
        secQSDetail.mQsDetailHeaderSwitch.setOnCheckedChangeListener(new SecQSDetail$$ExternalSyntheticLambda4(secQSDetail, detailAdapter2));
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* renamed from: $r8$lambda$TxB-zPyGotyrSZn1dD6iSte9HRY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m1620$r8$lambda$TxBzPyGotyrSZn1dD6iSte9HRY(SecQSDetail secQSDetail, DetailAdapter detailAdapter, boolean z) {
        String str;
        if (z) {
            SecQSPanelController secQSPanelController = secQSDetail.mQsPanelController;
            if (secQSPanelController.mDetailRecord == null || (str = secQSPanelController.mDetailTileSpec) == null) {
                str = "";
            }
            if (str.equals("Wifi") || str.equals("Bluetooth")) {
                secQSDetail.mQsDetailHeaderProgress.setVisibility(0);
                if (detailAdapter != null) {
                    SQSTile sQSTile = (SQSTile) secQSDetail.mQsPanelController.getTile(detailAdapter);
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPDE1008", sQSTile != null ? sQSTile.getTileMapKey() : Integer.toString(detailAdapter.getMetricsCategory()), z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                }
                if (secQSDetail.isLandscape()) {
                    secQSDetail.announceForAccessibility(((LinearLayout) secQSDetail).mContext.getString(z ? R.string.switch_bar_on : R.string.switch_bar_off));
                    return;
                }
                return;
            }
        }
        secQSDetail.mQsDetailHeaderProgress.setVisibility(8);
        if (detailAdapter != null) {
        }
        if (secQSDetail.isLandscape()) {
        }
    }

    /* renamed from: -$$Nest$mcheckPendingAnimations, reason: not valid java name */
    public static void m1621$$Nest$mcheckPendingAnimations(SecQSDetail secQSDetail) {
        boolean z = secQSDetail.mSwitchState;
        DetailAdapter detailAdapter = secQSDetail.mDetailAdapter;
        secQSDetail.handleToggleStateChanged(z, detailAdapter != null && detailAdapter.getToggleEnabled());
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.qs.SecQSDetail$3] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.qs.SecQSDetail$4] */
    public SecQSDetail(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDetailViews = new SparseArray();
        QSEvents.INSTANCE.getClass();
        this.mUiEventLogger = QSEvents.qsUiEventsLogger;
        this.mOrientation = -1;
        this.mDarkModelEasel = new SecDarkModeEasel(new SecDarkModeEasel.PictureSubject() { // from class: com.android.systemui.qs.SecQSDetail$$ExternalSyntheticLambda0
            @Override // com.android.systemui.qs.SecDarkModeEasel.PictureSubject
            public final void drawDarkModelPicture() {
                SecQSDetail.$r8$lambda$GqF35b0Ptg1YPaUYppLYwuej_yI(SecQSDetail.this);
            }
        });
        this.mQsPanelCallback = new C20682();
        this.mHideGridContentWhenDone = new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.SecQSDetail.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                animator.removeListener(this);
                SecQSDetail secQSDetail = SecQSDetail.this;
                secQSDetail.mAnimatingOpen = false;
                SecQSDetail.m1621$$Nest$mcheckPendingAnimations(secQSDetail);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SecQSDetail secQSDetail = SecQSDetail.this;
                if (secQSDetail.mDetailAdapter != null) {
                    secQSDetail.mQsPanelController.setGridContentVisibility(false);
                    SecQSDetail.this.mHeader.setVisibility(4);
                }
                SecQSDetail secQSDetail2 = SecQSDetail.this;
                secQSDetail2.mAnimatingOpen = false;
                SecQSDetail.m1621$$Nest$mcheckPendingAnimations(secQSDetail2);
            }
        };
        this.mTeardownDetailWhenDone = new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.SecQSDetail.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                SecQSDetail.this.mDetailContent.removeAllViews();
                SecQSDetail.this.setVisibility(4);
                SecQSDetail.this.getClass();
            }
        };
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
    }

    public final void handleShowingDetail(final DetailAdapter detailAdapter) {
        Interpolator interpolator;
        SecQSPanelController secQSPanelController;
        int i = 1;
        int i2 = 0;
        boolean z = detailAdapter != null;
        boolean z2 = this.mDetailAdapter != null;
        boolean z3 = z && detailAdapter.shouldUseFullScreen();
        setClickable(z);
        if (z) {
            if (z3) {
                findViewById(R.id.qs_detail_extended_container).setVisibility(8);
                findViewById(R.id.qs_detail_full_screen_container).setVisibility(0);
            } else {
                findViewById(R.id.qs_detail_extended_container).setVisibility(0);
                findViewById(R.id.qs_detail_full_screen_container).setVisibility(8);
            }
            if (!z3) {
                setDetailExtendedContainerHeight();
                if (QpRune.QUICK_TABLET) {
                    onSummaryUpdated();
                    updateTabletResources();
                }
                this.mQsDetailHeaderTitle.setText(detailAdapter.getTitle());
                Boolean toggleState = detailAdapter.getToggleState();
                if (toggleState == null) {
                    SecQSSwitch secQSSwitch = this.mQsDetailHeaderSwitch;
                    if (secQSSwitch != null) {
                        secQSSwitch.setVisibility(4);
                    }
                    this.mQsDetailHeader.setClickable(false);
                    updateDetailTitle(null, detailAdapter.getTitle());
                } else {
                    if (this.mQsDetailHeaderSwitch == null) {
                        this.mQsDetailHeaderSwitch = (SecQSSwitch) this.mQsDetailHeaderSwitchStub.inflate();
                    }
                    this.mSwitchAdapter = detailAdapter;
                    this.mSwitchState = toggleState.booleanValue();
                    setupHeaderSwitch(detailAdapter, toggleState);
                    updateDetailTitle(toggleState, detailAdapter.getTitle());
                    this.mQsDetailHeader.setVisibility(0);
                    ViewCompat.setAccessibilityDelegate(this.mQsDetailHeader, new AccessibilityDelegateCompat() { // from class: com.android.systemui.qs.SecQSDetail.1
                        @Override // androidx.core.view.AccessibilityDelegateCompat
                        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                            accessibilityNodeInfoCompat.setCheckable(false);
                            accessibilityNodeInfoCompat.setClassName(Switch.class.getName());
                            SecQSDetail secQSDetail = SecQSDetail.this;
                            accessibilityNodeInfoCompat.setText(secQSDetail.mQsDetailHeaderSwitch.isChecked() ? ((LinearLayout) secQSDetail).mContext.getString(R.string.switch_bar_on) : ((LinearLayout) secQSDetail).mContext.getString(R.string.switch_bar_off));
                        }
                    });
                    this.mQsDetailHeader.setOnClickListener(new SecQSDetail$$ExternalSyntheticLambda2(this, detailAdapter, i));
                    this.mQsDetailHeaderSwitch.setOnClickListener(new SecQSDetail$$ExternalSyntheticLambda2(this, detailAdapter, 2));
                    this.mQsDetailHeaderSwitch.setOnCheckedChangeListener(new SecQSDetail$$ExternalSyntheticLambda4(this, detailAdapter));
                }
            }
            if (!this.mQsExpanded) {
                this.mTriggeredExpand = true;
                SecQSPanelController secQSPanelController2 = this.mQsPanelController;
                if (secQSPanelController2 != null && !secQSPanelController2.mExpandSettingsPanel) {
                    secQSPanelController2.mCollapseExpandAction.run();
                }
            }
            detailAdapter.shouldAnimate();
        } else if (this.mTriggeredExpand) {
            SecQSPanelController secQSPanelController3 = this.mQsPanelController;
            if (secQSPanelController3 != null) {
                secQSPanelController3.mCollapseExpandAction.run();
            }
            this.mTriggeredExpand = false;
        }
        if ((z2 != z) || z2) {
            if (z) {
                int metricsCategory = detailAdapter.getMetricsCategory();
                if (!z3) {
                    this.mDetailContent.setPadding(0, getResources().getDimensionPixelSize(R.dimen.sec_qs_detail_content_top_margin), 0, 0);
                    if (QpRune.QUICK_TABLET) {
                        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sec_qs_detail_content_start_end_margin_tablet);
                        SecQSDetailContentView secQSDetailContentView = this.mDetailContentParent;
                        secQSDetailContentView.setPadding(dimensionPixelSize, secQSDetailContentView.getPaddingTop(), dimensionPixelSize, this.mDetailContentParent.getPaddingBottom());
                    }
                }
                View createDetailView = detailAdapter.createDetailView(((LinearLayout) this).mContext, (View) this.mDetailViews.get(metricsCategory), this.mDetailContent);
                if (createDetailView == null) {
                    Log.e("QSDetail", "Tile = " + ((Object) detailAdapter.getTitle()) + " detailView is null");
                    return;
                }
                final Intent settingsIntent = detailAdapter.getSettingsIntent();
                this.mDetailButtonsDivider.setVisibility(settingsIntent != null ? 0 : 8);
                this.mDetailSettingsButton.setVisibility(settingsIntent != null ? 0 : 8);
                this.mDetailSettingsButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.SecQSDetail$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SecQSDetail secQSDetail = SecQSDetail.this;
                        DetailAdapter detailAdapter2 = detailAdapter;
                        Intent intent = settingsIntent;
                        if (secQSDetail.mFalsingManager.isFalseTap(1)) {
                            return;
                        }
                        secQSDetail.mUiEventLogger.log(detailAdapter2.moreSettingsEvent());
                        ((ActivityStarter) Dependency.get(ActivityStarter.class)).postStartActivityDismissingKeyguard(intent, 0);
                        DetailAdapter detailAdapter3 = secQSDetail.mDetailAdapter;
                        if (detailAdapter3 != null) {
                            SQSTile sQSTile = (SQSTile) secQSDetail.mQsPanelController.getTile(detailAdapter3);
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPDE1007", sQSTile != null ? sQSTile.getTileMapKey() : Integer.toString(secQSDetail.mDetailAdapter.getMetricsCategory()));
                        }
                    }
                });
                this.mDetailDoneButton.setOnClickListener(new SecQSDetail$$ExternalSyntheticLambda2(this, detailAdapter, i2));
                if (z3) {
                    ((ViewGroup) findViewById(R.id.qs_detail_full_screen_container)).addView(createDetailView);
                } else {
                    this.mDetailContent.removeAllViews();
                    this.mDetailContent.addView(createDetailView);
                }
                this.mDetailViews.put(metricsCategory, createDetailView);
                ((MetricsLogger) Dependency.get(MetricsLogger.class)).visible(detailAdapter.getMetricsCategory());
                this.mUiEventLogger.log(detailAdapter.openDetailEvent());
                announceForAccessibility(((LinearLayout) this).mContext.getString(R.string.accessibility_quick_settings_detail) + ", " + ((Object) detailAdapter.getTitle()));
                this.mDetailAdapter = detailAdapter;
                setVisibility(0);
                if (!z3) {
                    updateDetailText();
                    updateDndDetail();
                }
            } else {
                if (this.mDetailAdapter != null && z2) {
                    ((MetricsLogger) Dependency.get(MetricsLogger.class)).hidden(this.mDetailAdapter.getMetricsCategory());
                    this.mUiEventLogger.log(this.mDetailAdapter.closeDetailEvent());
                    this.mDetailAdapter.dismissListPopupWindow();
                    this.mDetailAdapter = null;
                }
                if (this.mQsPanelController != null) {
                    this.mHeader.setVisibility(0);
                    this.mQsPanelController.setGridContentVisibility(this.mQsExpanded);
                }
                C20682 c20682 = this.mQsPanelCallback;
                c20682.getClass();
                SecQSDetail.this.post(c20682.new AnonymousClass3(false));
            }
            sendAccessibilityEvent(32);
            QsTransitionAnimator qsTransitionAnimator = this.mTransitionAnimator;
            boolean z4 = this.mTriggeredExpand;
            if (qsTransitionAnimator.mQsPanel == null || qsTransitionAnimator.mQSPanelController.mExpandSettingsPanel || (!qsTransitionAnimator.isThereNoView() && qsTransitionAnimator.mAnimatorsInitialiezed)) {
                if (z) {
                    qsTransitionAnimator.mDetailStateCallback.setDetailOpening(true);
                    SecQSFragmentAnimatorManager.C20831 c20831 = qsTransitionAnimator.mDetailStateCallback;
                    c20831.getClass();
                    SecQSFragmentAnimatorManager.this.executeConsumer(new SecQSFragmentAnimatorManager$$ExternalSyntheticLambda1(z4, 7));
                } else {
                    qsTransitionAnimator.mDetailStateCallback.setDetailShowing(false);
                    qsTransitionAnimator.mDetailStateCallback.setDetailClosing(true);
                    if (!qsTransitionAnimator.mQsFullyExpanded && !qsTransitionAnimator.mPanelExpanded) {
                        if (!qsTransitionAnimator.isThereNoView()) {
                            Iterator it = qsTransitionAnimator.mPanelContents.iterator();
                            while (true) {
                                boolean hasNext = it.hasNext();
                                interpolator = QsTransitionAnimator.INTERPOLATOR;
                                if (!hasNext) {
                                    break;
                                } else {
                                    ((View) it.next()).animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(100L).setInterpolator(interpolator).start();
                                }
                            }
                            Iterator it2 = qsTransitionAnimator.mDetailContents.iterator();
                            while (it2.hasNext()) {
                                ((View) it2.next()).animate().alpha(0.0f).setDuration(50L).setInterpolator(interpolator).start();
                            }
                            SecQSDetail secQSDetail = SecQSDetail.this;
                            secQSDetail.mDetailContent.removeAllViews();
                            secQSDetail.setVisibility(4);
                            secQSDetail.handleShowingDetail(null);
                            qsTransitionAnimator.mDetailStateCallback.setDetailClosing(false);
                        }
                    }
                }
                boolean z5 = !z;
                if (!qsTransitionAnimator.isThereNoView()) {
                    if (z5) {
                        qsTransitionAnimator.mPanelHideAnimSetForDetail.cancel();
                        qsTransitionAnimator.mPanelShowAnimSetForDetail.start();
                    } else {
                        qsTransitionAnimator.mPanelShowAnimSetForDetail.cancel();
                        qsTransitionAnimator.mPanelHideAnimSetForDetail.start();
                    }
                    qsTransitionAnimator.mHeader.setDescendantFocusability(z5 ? 262144 : 393216);
                }
                if (!qsTransitionAnimator.isThereNoView()) {
                    if (z) {
                        qsTransitionAnimator.mDetailView.setAlpha(0.0f);
                        qsTransitionAnimator.mDetailView.setScaleX(0.93f);
                        qsTransitionAnimator.mDetailView.setScaleY(0.93f);
                        qsTransitionAnimator.mDetailHideAnimSet.cancel();
                        qsTransitionAnimator.mDetailShowAnimSet.start();
                    } else {
                        qsTransitionAnimator.mDetailShowAnimSet.cancel();
                        qsTransitionAnimator.mDetailHideAnimSet.start();
                    }
                }
            }
            if (this.mTriggeredExpand && (secQSPanelController = this.mQsPanelController) != null && secQSPanelController.mExpandSettingsPanel) {
                this.mTriggeredExpand = false;
            }
            SystemUIAnalytics.sendScreenViewLog("QPP101");
        }
    }

    public final void handleToggleStateChanged(boolean z, boolean z2) {
        this.mSwitchState = z;
        if (this.mAnimatingOpen) {
            return;
        }
        this.mQsDetailHeader.setEnabled(z2);
        DetailAdapter detailAdapter = this.mDetailAdapter;
        if (detailAdapter != null && detailAdapter.getToggleState() == null) {
            updateDetailTitle(null, this.mDetailAdapter.getTitle());
            return;
        }
        SecQSSwitch secQSSwitch = this.mQsDetailHeaderSwitch;
        if (secQSSwitch != null) {
            secQSSwitch.setChecked(z);
        }
        SecQSSwitch secQSSwitch2 = this.mQsDetailHeaderSwitch;
        if (secQSSwitch2 != null) {
            secQSSwitch2.setEnabled(z2);
            if (this.mDetailAdapter != null) {
                updateDetailTitle(Boolean.valueOf(z), this.mDetailAdapter.getTitle());
            }
        }
    }

    public final void handleUpdatingDetail(DetailAdapter detailAdapter) {
        if (detailAdapter != null) {
            int metricsCategory = detailAdapter.getMetricsCategory();
            this.mDetailContent.setPadding(0, getResources().getDimensionPixelSize(R.dimen.sec_qs_detail_content_top_margin), 0, 0);
            if (QpRune.QUICK_TABLET) {
                int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sec_qs_detail_content_start_end_margin_tablet);
                SecQSDetailContentView secQSDetailContentView = this.mDetailContentParent;
                secQSDetailContentView.setPadding(dimensionPixelSize, secQSDetailContentView.getPaddingTop(), dimensionPixelSize, this.mDetailContentParent.getPaddingBottom());
            }
            View createDetailView = detailAdapter.createDetailView(((LinearLayout) this).mContext, (View) this.mDetailViews.get(metricsCategory), this.mDetailContent);
            if (createDetailView != null) {
                if (detailAdapter.shouldUseFullScreen()) {
                    ((ViewGroup) findViewById(R.id.qs_detail_full_screen_container)).addView(createDetailView);
                } else {
                    this.mDetailContent.removeAllViews();
                    this.mDetailContent.addView(createDetailView);
                }
                this.mDetailViews.put(metricsCategory, createDetailView);
                Intent settingsIntent = detailAdapter.getSettingsIntent();
                this.mDetailSettingsButton.setVisibility(settingsIntent != null ? 0 : 8);
                this.mDetailButtonsDivider.setVisibility(settingsIntent == null ? 8 : 0);
            }
        }
    }

    public final boolean isLandscape() {
        return getResources().getConfiguration().orientation == 2;
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        DetailAdapter detailAdapter = this.mDetailAdapter;
        if (detailAdapter != null && detailAdapter.shouldUseFullScreen()) {
            return super.onApplyWindowInsets(windowInsets);
        }
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout != null) {
            int safeInsetTop = displayCutout.getSafeInsetTop() - displayCutout.getSafeInsetBottom();
            int i = safeInsetTop >= 0 ? safeInsetTop : 0;
            if (this.mCutOutHeight != i) {
                this.mCutOutHeight = i;
                setDetailExtendedContainerHeight();
                setPanelMargin();
                updateDetailHeader();
                updateDndDetail();
            }
        } else if (this.mCutOutHeight != 0) {
            this.mCutOutHeight = 0;
            setDetailExtendedContainerHeight();
            setPanelMargin();
            updateDetailHeader();
            updateDndDetail();
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        DetailAdapter detailAdapter = this.mDetailAdapter;
        if (detailAdapter != null && detailAdapter.shouldUseFullScreen()) {
            return;
        }
        updateDetailText();
        for (int i = 0; i < this.mDetailViews.size(); i++) {
            ((View) this.mDetailViews.valueAt(i)).dispatchConfigurationChanged(configuration);
        }
        int i2 = this.mOrientation;
        int i3 = configuration.orientation;
        if (i2 != i3) {
            this.mOrientation = i3;
            setDetailExtendedContainerHeight();
            setPanelMargin();
            updateDetailHeader();
            DetailAdapter detailAdapter2 = this.mDetailAdapter;
            if (detailAdapter2 != null) {
                updateDetailTitle(detailAdapter2.getToggleState(), this.mDetailAdapter.getTitle());
                handleUpdatingDetail(this.mDetailAdapter);
            }
            updateDndDetail();
            if (QpRune.QUICK_TABLET) {
                updateTabletResources();
            }
        }
        this.mDarkModelEasel.updateColors(configuration);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDetailContent = (ViewGroup) findViewById(android.R.id.content);
        this.mDetailSettingsButton = (Button) findViewById(R.id.detail_btn);
        this.mDetailDoneButton = (Button) findViewById(R.id.done_btn);
        this.mDetailButtonsDivider = findViewById(R.id.qs_detail_divider);
        boolean z = QpRune.QUICK_TABLET;
        if (z) {
            this.mDetailExtended = (LinearLayout) findViewById(R.id.qs_detail_extended_container);
        }
        this.mDetailExtendedContainer = (ViewGroup) findViewById(R.id.qs_detail_extended_text_container);
        this.mDetailExtendedText = (DoubleShadowTextView) findViewById(R.id.qs_detail_extended_text);
        this.mDetailExtendedSummaryContainer = (ViewGroup) findViewById(R.id.qs_detail_extended_summary_container);
        this.mDetailExtendedSummary = (TextView) findViewById(R.id.qs_detail_extended_summary);
        this.mDetailContentParent = (SecQSDetailContentView) findViewById(R.id.qs_detail_parent);
        this.mScrollView = (ScrollView) findViewById(R.id.qs_detail_scroll);
        this.mToggleDivider = findViewById(R.id.qs_toggle_divider);
        View findViewById = findViewById(R.id.qs_detail_header);
        this.mQsDetailHeader = findViewById;
        this.mQsDetailHeaderTitle = (TextView) findViewById.findViewById(android.R.id.title);
        this.mQsDetailHeaderSwitchStub = (ViewStub) this.mQsDetailHeader.findViewById(R.id.toggle_stub);
        this.mQsDetailHeaderProgress = (ProgressBar) findViewById(R.id.qs_detail_header_progress);
        this.mDetailButtons = (LinearLayout) findViewById(R.id.qs_detail_buttons);
        updateDetailText();
        setPanelMargin();
        updateDetailHeader();
        updateDndDetail();
        if (z) {
            updateTabletResources();
        }
        SecQSDetailContentView secQSDetailContentView = this.mDetailContentParent;
        SecQSPanelController secQSPanelController = this.mQsPanelController;
        if (secQSPanelController == null) {
            secQSDetailContentView.getClass();
        } else {
            secQSDetailContentView.mQsPanelController = secQSPanelController;
        }
    }

    public final void onSummaryUpdated() {
        ViewParent parent;
        if (QpRune.QUICK_TABLET && (parent = this.mDetailExtendedSummaryContainer.getParent()) != null) {
            ((ViewGroup) parent).removeView(this.mDetailExtendedSummaryContainer);
            this.mDetailExtendedContainer.addView(this.mDetailExtendedSummaryContainer);
        }
        DetailAdapter detailAdapter = this.mDetailAdapter;
        if ((detailAdapter == null ? false : detailAdapter.getTitle().toString().equalsIgnoreCase(((LinearLayout) this).mContext.getString(R.string.quick_settings_dnd_detail_title))) && this.mDetailAdapter != null && this.mDetailExtendedSummaryContainer.getVisibility() == 0) {
            this.mDetailExtendedSummary.setText(this.mDetailAdapter.getDetailAdapterSummary());
        }
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public final void setDetailExtendedContainerHeight() {
        if (QpRune.QUICK_TABLET) {
            return;
        }
        this.mDetailExtendedContainer.getLayoutParams().height = !isLandscape() ? -2 : 0;
    }

    public final void setPanelMargin() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) findViewById(R.id.qs_detail_parent).getLayoutParams();
        boolean z = QpRune.QUICK_TABLET;
        int i = 0;
        if (z) {
            layoutParams.topMargin = 0;
        } else if (isLandscape()) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_detail_top_bottom_padding);
            layoutParams.topMargin = 0;
            i = dimensionPixelSize;
        } else {
            layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.qs_detail_margin_top);
        }
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mToggleDivider.getLayoutParams();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        Context context = ((LinearLayout) this).mContext;
        secQSPanelResourcePicker.getClass();
        Resources resources = context.getResources();
        int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.qspanel_layout_detail_divider_side_padding);
        if (z) {
            dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.qspanel_layout_detail_divider_side_padding_tablet);
        }
        layoutParams2.leftMargin = dimensionPixelSize2;
        layoutParams2.rightMargin = dimensionPixelSize2;
        this.mToggleDivider.setLayoutParams(layoutParams2);
        this.mDetailContentParent.setLayoutParams(layoutParams);
        SecQSPanelResourcePicker secQSPanelResourcePicker2 = this.mResourcePicker;
        Context context2 = ((LinearLayout) this).mContext;
        secQSPanelResourcePicker2.getClass();
        Resources resources2 = context2.getResources();
        int dimensionPixelSize3 = resources2.getDimensionPixelSize(R.dimen.qs_detail_side_padding);
        if (z) {
            dimensionPixelSize3 = resources2.getDimensionPixelSize(R.dimen.qs_detail_side_padding_tablet);
        }
        setPadding(dimensionPixelSize3, i, dimensionPixelSize3, i);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        if (z) {
            marginLayoutParams.topMargin = ((ShadeHeaderController) this.mResourcePicker.mShadeHeaderControllerLazy.get()).getViewHeight();
        }
        setLayoutParams(marginLayoutParams);
    }

    public final void setupHeaderSwitch(DetailAdapter detailAdapter, Boolean bool) {
        this.mQsDetailHeaderSwitch.setChecked(bool.booleanValue());
        this.mQsDetailHeaderSwitch.setEnabled(detailAdapter.getToggleEnabled());
        this.mQsDetailHeaderSwitch.setClickable(true);
        this.mQsDetailHeaderSwitch.jumpDrawablesToCurrentState();
        this.mQsDetailHeaderSwitch.setVisibility(0);
    }

    public final void updateDetailHeader() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mQsDetailHeader.getLayoutParams();
        if (this.mQsDetailHeader.getParent() != null) {
            ((ViewGroup) this.mQsDetailHeader.getParent()).removeView(this.mQsDetailHeader);
        }
        if (!isLandscape() || QpRune.QUICK_TABLET) {
            ((ViewGroup) findViewById(R.id.qs_detail_parent)).addView(this.mQsDetailHeader, 0);
            layoutParams.height = getResources().getDimensionPixelSize(R.dimen.sec_qs_detail_header_height);
            layoutParams.bottomMargin = 0;
        } else {
            ((ViewGroup) findViewById(R.id.qs_detail_extended_container)).addView(this.mQsDetailHeader, 0);
            layoutParams.height = -2;
            layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.sec_qs_detail_header_bottom_margin);
        }
    }

    public final void updateDetailText() {
        this.mDetailDoneButton.setText(R.string.sec_quick_settings_done);
        this.mDetailDoneButton.getTypeface().isLikeDefault = true;
        this.mDetailDoneButton.setBackground(((LinearLayout) this).mContext.getDrawable(R.drawable.sec_qs_btn_borderless_rect));
        this.mDetailDoneButton.semSetButtonShapeEnabled(true);
        this.mDetailSettingsButton.setText(R.string.sec_quick_settings_details);
        this.mDetailSettingsButton.getTypeface().isLikeDefault = true;
        this.mDetailSettingsButton.setBackground(((LinearLayout) this).mContext.getDrawable(R.drawable.sec_qs_btn_borderless_rect));
        this.mDetailSettingsButton.semSetButtonShapeEnabled(true);
    }

    public final void updateDetailTitle(Boolean bool, CharSequence charSequence) {
        Context context;
        int i;
        Context context2;
        int i2;
        int color;
        if (charSequence == null || charSequence.toString().isEmpty()) {
            return;
        }
        String trim = charSequence.toString().trim();
        AbstractC0000x2c234b15.m3m("updateDetailTitle", trim, "QSDetail");
        int i3 = 2;
        this.mQsDetailHeaderTitle.setImportantForAccessibility((!isLandscape() || QpRune.QUICK_TABLET) ? 2 : 1);
        DoubleShadowTextView doubleShadowTextView = this.mDetailExtendedText;
        if (doubleShadowTextView != null) {
            doubleShadowTextView.setText(trim);
        }
        if (bool != null) {
            if (!isLandscape() || QpRune.QUICK_TABLET) {
                this.mQsDetailHeader.setClickable(true);
                TextView textView = this.mQsDetailHeaderTitle;
                if (bool.booleanValue()) {
                    context = ((LinearLayout) this).mContext;
                    i = R.string.switch_bar_on;
                } else {
                    context = ((LinearLayout) this).mContext;
                    i = R.string.switch_bar_off;
                }
                textView.setText(context.getString(i));
                if (bool.booleanValue()) {
                    context2 = ((LinearLayout) this).mContext;
                    i2 = R.color.sec_qs_detail_header_on_text_color;
                } else {
                    context2 = ((LinearLayout) this).mContext;
                    i2 = R.color.sec_qs_detail_header_off_text_color;
                }
                color = context2.getColor(i2);
            } else {
                this.mQsDetailHeader.setClickable(false);
                this.mQsDetailHeaderTitle.setText(trim);
                this.mQsDetailHeader.setBackground(null);
                color = ((LinearLayout) this).mContext.getColor(R.color.sec_qs_detail_header_text_color);
            }
            this.mQsDetailHeaderTitle.setTextColor(color);
            SecQSSwitch secQSSwitch = this.mQsDetailHeaderSwitch;
            if (secQSSwitch != null) {
                if (isLandscape() && !QpRune.QUICK_TABLET) {
                    i3 = 1;
                }
                secQSSwitch.setImportantForAccessibility(i3);
            }
        } else if (!isLandscape() || QpRune.QUICK_TABLET) {
            this.mQsDetailHeader.setVisibility(8);
        } else {
            this.mQsDetailHeaderTitle.setText(trim);
            this.mQsDetailHeader.setClickable(false);
            this.mQsDetailHeader.setBackground(null);
            this.mQsDetailHeaderTitle.setTextColor(((LinearLayout) this).mContext.getColor(R.color.sec_qs_detail_header_text_color));
            this.mQsDetailHeader.setVisibility(0);
        }
        if (!isLandscape() || QpRune.QUICK_TABLET) {
            TextView textView2 = this.mQsDetailHeaderTitle;
            textView2.setTypeface(Typeface.create(textView2.getTypeface(), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
        } else {
            TextView textView3 = this.mQsDetailHeaderTitle;
            textView3.setTypeface(Typeface.create(textView3.getTypeface(), 400, false));
        }
        if ((!isLandscape() || QpRune.QUICK_TABLET) && bool != null) {
            this.mToggleDivider.setVisibility(0);
        } else {
            this.mToggleDivider.setVisibility(8);
        }
    }

    public final void updateDndDetail() {
        DetailAdapter detailAdapter = this.mDetailAdapter;
        if (!(detailAdapter == null ? false : detailAdapter.getTitle().toString().equalsIgnoreCase(((LinearLayout) this).mContext.getString(R.string.quick_settings_dnd_detail_title)))) {
            this.mDetailExtendedSummaryContainer.setVisibility(8);
            return;
        }
        if (!isLandscape() || QpRune.QUICK_TABLET) {
            this.mQsDetailHeader.setVisibility(8);
        }
        this.mDetailExtendedSummaryContainer.setVisibility(0);
        DetailAdapter detailAdapter2 = this.mDetailAdapter;
        if (detailAdapter2 == null || !(detailAdapter2 instanceof DndTile.DndDetailAdapter)) {
            return;
        }
        this.mDetailExtendedSummary.setText(detailAdapter2.getDetailAdapterSummary());
        DndTile.this.mSecQSDetail = this;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateTabletResources() {
        boolean z;
        LinearLayout linearLayout;
        SecQSDetailContentView secQSDetailContentView = this.mDetailContentParent;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_detail_extended_container_minheight);
        if (this.mDetailExtendedContainer.getHeight() < dimensionPixelSize) {
            this.mDetailExtendedContainer.setLayoutParams(new LinearLayout.LayoutParams(-1, getResources().getDimensionPixelSize(R.dimen.qs_detail_extended_container_minheight)));
            if (this.mDetailExtendedContainer.getHeight() < dimensionPixelSize) {
                z = true;
                secQSDetailContentView.hasMinHeight = z;
                ((LinearLayout.LayoutParams) this.mDetailExtendedContainer.getLayoutParams()).weight = 1.0f;
                if (this.mDetailButtons.getParent() != null) {
                    ((ViewGroup) this.mDetailButtons.getParent()).removeView(this.mDetailButtons);
                }
                linearLayout = this.mDetailExtended;
                if (linearLayout != null) {
                    linearLayout.getLayoutParams().height = -1;
                    this.mDetailExtended.addView(this.mDetailButtons);
                }
                ((LinearLayout.LayoutParams) this.mDetailButtons.getLayoutParams()).height = getResources().getDimensionPixelSize(R.dimen.qs_detail_buttons_height_tablet);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mDetailDoneButton.getLayoutParams();
                layoutParams.topMargin = 0;
                layoutParams.bottomMargin = 0;
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mDetailSettingsButton.getLayoutParams();
                layoutParams2.topMargin = 0;
                layoutParams2.bottomMargin = 0;
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mDetailButtonsDivider.getLayoutParams();
                layoutParams3.topMargin = 0;
                layoutParams3.bottomMargin = 0;
            }
        }
        z = false;
        secQSDetailContentView.hasMinHeight = z;
        ((LinearLayout.LayoutParams) this.mDetailExtendedContainer.getLayoutParams()).weight = 1.0f;
        if (this.mDetailButtons.getParent() != null) {
        }
        linearLayout = this.mDetailExtended;
        if (linearLayout != null) {
        }
        ((LinearLayout.LayoutParams) this.mDetailButtons.getLayoutParams()).height = getResources().getDimensionPixelSize(R.dimen.qs_detail_buttons_height_tablet);
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.mDetailDoneButton.getLayoutParams();
        layoutParams4.topMargin = 0;
        layoutParams4.bottomMargin = 0;
        LinearLayout.LayoutParams layoutParams22 = (LinearLayout.LayoutParams) this.mDetailSettingsButton.getLayoutParams();
        layoutParams22.topMargin = 0;
        layoutParams22.bottomMargin = 0;
        LinearLayout.LayoutParams layoutParams32 = (LinearLayout.LayoutParams) this.mDetailButtonsDivider.getLayoutParams();
        layoutParams32.topMargin = 0;
        layoutParams32.bottomMargin = 0;
    }
}
