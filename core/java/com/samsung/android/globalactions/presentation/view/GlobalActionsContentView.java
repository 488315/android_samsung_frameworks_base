package com.samsung.android.globalactions.presentation.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.view.IRotationWatcher;
import android.view.IWindowManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.internal.R;

import com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.strategies.ViewInflateStrategy;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;
import com.samsung.android.globalactions.presentation.viewmodel.ViewType;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.WindowManagerUtils;

import java.util.ArrayList;
import java.util.List;

public class GlobalActionsContentView implements ContentView, ViewStateController {
    private static final int LAND_NUM_COLUMNS_4_ITEMS = 2;
    private static final int LAND_NUM_COLUMNS_MORE_THAN_4_ITEMS = 3;
    private static final int NAV_BAR_POS_LEFT = 1;
    private static final int NAV_BAR_POS_RIGHT = 2;
    private static final int PORT_NUM_COLUMNS_DEFAULT = 1;
    private static final int PORT_NUM_COLUMNS_MORE_THEN_4_ITEMS = 2;
    private static final int REFERENCE_NUM_ITEMS = 4;
    private static final String TAG = "GlobalActionsContentView";
    private SamsungGlobalActionsAnimator mAnimator;
    private SamsungGlobalActionsAnimatorFSM mAnimatorFSM;
    private SamsungGlobalActionsBackgroundView mBackgroundView;
    private LinearLayout mBottomButtonView;
    private LinearLayout mBottomMsgView;
    private final ConditionChecker mConditionChecker;
    private ViewGroup mConfirmationView;
    private final Context mContext;
    private final Dialog mDialog;
    private final FeatureFactory mFeatureFactory;
    private BaseContentAdapter mGridViewAdapter;
    private IWindowManager mIWindowManager;
    private boolean mIsClearCoverClosed;
    private boolean mIsVoiceAssistantMode;
    private boolean mIsWhiteTheme;
    private int mItemHorizontalSpacing;
    private int mItemVerticalSpacingLand;
    private int mItemVerticalSpacingPort;
    private int mItemWidthLand;
    private int mItemWidthPort;
    private int mItemWidthPortPhone;
    private SamsungGlobalActionsGridView mLandListView;
    private SamsungGlobalActionsGridView mListView;
    private final LogWrapper mLogWrapper;
    private final ExtendableGlobalActionsView mParentView;
    private FrameLayout mPopupView;
    private final ResourceFactory mResourceFactory;
    private SamsungGlobalActionsRootView mRootView;
    private IRotationWatcher.Stub mRotationWatcher;
    private ActionViewModel mSelectedViewModel;
    private LinearLayout mTopView;
    private final WindowManagerUtils mWindowManagerUtil;
    private boolean mNeedToForceUpdate = false;
    private SamsungGlobalActionsAnimator.ViewUpdateCallback mBaseAnimatorCallback =
            new AnonymousClass3();
    private boolean mForceDismiss = false;
    private ViewAnimationState mViewAnimationState = ViewAnimationState.IDLE;
    private ViewStateController mViewStateController = this;

    public GlobalActionsContentView(
            Context context,
            ExtendableGlobalActionsView parentView,
            FeatureFactory featureFactory,
            ConditionChecker conditionChecker,
            WindowManagerUtils windowManagerUtil,
            ResourceFactory resourceFactory,
            LogWrapper logWrapper,
            Dialog dialog,
            boolean coverClosed) {
        this.mContext = context;
        this.mParentView = parentView;
        this.mFeatureFactory = featureFactory;
        this.mConditionChecker = conditionChecker;
        this.mWindowManagerUtil = windowManagerUtil;
        this.mResourceFactory = resourceFactory;
        this.mLogWrapper = logWrapper;
        this.mDialog = dialog;
        this.mIsClearCoverClosed = coverClosed;
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void initDimens() {
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE)) {
            this.mItemHorizontalSpacing =
                    this.mContext
                            .getResources()
                            .getDimensionPixelSize(
                                    R.dimen.sec_global_actions_items_horizontal_spacing_tablet);
            this.mItemVerticalSpacingPort =
                    this.mContext
                            .getResources()
                            .getDimensionPixelSize(
                                    R.dimen.sec_global_actions_items_vertical_spacing_port_tablet);
        } else {
            this.mItemHorizontalSpacing =
                    this.mContext
                            .getResources()
                            .getDimensionPixelSize(
                                    R.dimen.sec_global_actions_items_horizontal_spacing);
            this.mItemVerticalSpacingPort =
                    this.mContext
                            .getResources()
                            .getDimensionPixelSize(
                                    R.dimen.sec_global_actions_items_vertical_spacing_port);
        }
        this.mItemVerticalSpacingLand =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(
                                R.dimen.sec_global_actions_items_vertical_spacing_land);
        this.mItemWidthLand =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_global_actions_items_width_land);
        this.mItemWidthPort =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_global_actions_items_width_port);
        this.mItemWidthPortPhone =
                this.mContext
                        .getResources()
                        .getDimensionPixelSize(R.dimen.sec_global_actions_items_width_port_phone);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void initLayouts() {
        this.mRootView = new SamsungGlobalActionsRootView(this.mContext);
        ViewGroup listViewParent =
                (ViewGroup)
                        this.mRootView.findViewById(
                                this.mResourceFactory.get(ResourceType.ID_ITEM_LIST));
        listViewParent.setContentDescription(
                this.mContext.getResources().getText(R.string.global_action_power_off_menu));
        this.mListView = new SamsungGlobalActionsGridView(this.mContext, true);
        listViewParent.addView(this.mListView);
        ViewGroup landListViewParent =
                (ViewGroup)
                        this.mRootView.findViewById(
                                this.mResourceFactory.get(ResourceType.ID_ITEM_LIST_LAND));
        this.mLandListView = new SamsungGlobalActionsGridView(this.mContext, false);
        landListViewParent.addView(this.mLandListView);
        this.mTopView =
                (LinearLayout)
                        this.mRootView.findViewById(
                                this.mResourceFactory.get(ResourceType.ID_TOP_VIEW));
        this.mBottomButtonView =
                (LinearLayout)
                        this.mRootView.findViewById(
                                this.mResourceFactory.get(ResourceType.ID_BOTTOM_BUTTON_VIEW));
        this.mBottomMsgView =
                (LinearLayout)
                        this.mRootView.findViewById(
                                this.mResourceFactory.get(ResourceType.ID_FORCE_RESTART_TEXT_VIEW));
        this.mConfirmationView =
                (ViewGroup)
                        this.mRootView.findViewById(
                                this.mResourceFactory.get(ResourceType.ID_CONFIRMATION_VIEW));
        this.mPopupView =
                (FrameLayout)
                        this.mRootView.findViewById(
                                this.mResourceFactory.get(ResourceType.ID_SCREEN_CAPTURE_POPUP));
        if (this.mPopupView != null) {
            ImageView popupCloseView =
                    (ImageView)
                            this.mPopupView.findViewById(
                                    this.mResourceFactory.get(ResourceType.ID_ICON));
            popupCloseView.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.globalactions.presentation.view.GlobalActionsContentView$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            GlobalActionsContentView.this.lambda$initLayouts$0(view);
                        }
                    });
        }
        this.mIsVoiceAssistantMode =
                this.mConditionChecker.isEnabled(SystemConditions.IS_VOICE_ASSISTANT_MODE);
        this.mIsWhiteTheme = this.mConditionChecker.isEnabled(SystemConditions.IS_WHITE_THEME);
        this.mGridViewAdapter = new BaseContentAdapter(this.mContext);
        this.mListView.setAdapter((ListAdapter) this.mGridViewAdapter);
        this.mLandListView.setAdapter((ListAdapter) this.mGridViewAdapter);
        this.mDialog.setContentView(this.mRootView);
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_SUPPORT_SF_EFFECT)
                || this.mConditionChecker.isEnabled(SystemConditions.IS_SUPPORT_CAPTURED_BLUR)) {
            this.mBackgroundView = new SamsungGlobalActionsBackgroundView(this.mContext);
            List<ViewInflateStrategy> strategies = this.mFeatureFactory.createViewInflateStrategy();
            for (ViewInflateStrategy strategy : strategies) {
                strategy.onInflateView(this.mBackgroundView);
            }
            this.mDialog.addContentView(this.mBackgroundView, new FrameLayout.LayoutParams(-1, -1));
            this.mRootView.bringToFront();
        }
    }

    public /* synthetic */ void lambda$initLayouts$0(View view) {
        this.mPopupView.setVisibility(8);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void initAnimations() {
        this.mAnimator =
                new SamsungGlobalActionsAnimator(
                        this.mContext, this.mConditionChecker, this.mLogWrapper, this);
        this.mAnimator.setCallback(this.mBaseAnimatorCallback);
        this.mAnimatorFSM =
                new SamsungGlobalActionsAnimatorFSM(this.mAnimator, this.mLogWrapper, this);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void updateItemLists(SamsungGlobalActionsPresenter presenter) {
        if (this.mTopView != null && this.mTopView.getChildCount() != 0) {
            this.mTopView.removeAllViews();
        }
        if (this.mBottomButtonView != null && this.mBottomButtonView.getChildCount() != 0) {
            this.mBottomButtonView.removeAllViews();
        }
        if (this.mBottomMsgView != null && this.mBottomMsgView.getChildCount() != 0) {
            this.mBottomMsgView.removeAllViews();
        }
        this.mGridViewAdapter.resetItems();
        for (ActionViewModel viewModel : presenter.getValidActions()) {
            if (this.mTopView != null
                    && viewModel.getActionInfo().getViewType() == ViewType.TOP_VIEW) {
                GlobalActionsContentItemView item =
                        new GlobalActionsContentItemView(
                                this.mContext,
                                viewModel,
                                this.mTopView,
                                this.mResourceFactory,
                                this.mIsVoiceAssistantMode,
                                this.mIsWhiteTheme,
                                this);
                this.mTopView.addView(item.createView(false));
            } else if ((this.mBottomButtonView != null
                            && viewModel.getActionInfo().getViewType()
                                    == ViewType.KEY_SETTINGS_VIEW)
                    || viewModel.getActionInfo().getViewType() == ViewType.BOTTOM_BTN_LIST_VIEW) {
                GlobalActionsContentItemView item2 =
                        new GlobalActionsContentItemView(
                                this.mContext,
                                viewModel,
                                this.mBottomButtonView,
                                this.mResourceFactory,
                                this.mIsVoiceAssistantMode,
                                this.mIsWhiteTheme,
                                this);
                this.mBottomButtonView.addView(item2.createView(false));
            } else if (this.mBottomMsgView != null
                    && viewModel.getActionInfo().getViewType()
                            == ViewType.BOTTOM_FORCE_RESTART_MSG_VIEW) {
                GlobalActionsContentItemView item3 =
                        new GlobalActionsContentItemView(
                                this.mContext,
                                viewModel,
                                this.mBottomMsgView,
                                this.mResourceFactory,
                                this.mIsVoiceAssistantMode,
                                this.mIsWhiteTheme,
                                this);
                this.mBottomMsgView.addView(item3.createView(false));
            } else if (this.mPopupView != null
                    && viewModel.getActionInfo().getViewType() == ViewType.BOTTOM_POPUP_VIEW) {
                if (viewModel.isAvailableShow()) {
                    this.mPopupView.setVisibility(0);
                }
            } else {
                this.mGridViewAdapter.addItem(viewModel);
            }
        }
        this.mGridViewAdapter.updateNumColumns();
        notifyDataSetChanged();
        this.mListView.post(
                new Runnable() { // from class:
                                 // com.samsung.android.globalactions.presentation.view.GlobalActionsContentView.1
                    @Override // java.lang.Runnable
                    public void run() {
                        GlobalActionsContentView.this.mNeedToForceUpdate = true;
                        GlobalActionsContentView.this.mListView.requestLayout();
                    }
                });
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void show() {
        this.mDialog.show();
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void dismiss() {
        if (this.mAnimatorFSM != null) {
            this.mAnimatorFSM.handleAnimationEvent(SamsungGlobalActionsAnimatorFSM.Event.HIDE);
        }
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void showConfirm(ActionViewModel viewModel) {
        this.mSelectedViewModel = viewModel;
        this.mAnimatorFSM.handleAnimationEvent(SamsungGlobalActionsAnimatorFSM.Event.SHOW_CONFIRM);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void hideConfirm() {
        this.mAnimatorFSM.handleAnimationEvent(SamsungGlobalActionsAnimatorFSM.Event.HIDE_CONFIRM);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void hideDialogOnSecureConfirm() {
        this.mAnimatorFSM.handleAnimationEvent(
                SamsungGlobalActionsAnimatorFSM.Event.SECURE_CONFIRM);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void forceRequestLayout() {}

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void notifyDataSetChanged() {
        this.mGridViewAdapter.notifyDataSetChanged();
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void registerRotationWatcher() {
        this.mIWindowManager =
                IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));
        this.mRotationWatcher =
                new IRotationWatcher
                        .Stub() { // from class:
                                  // com.samsung.android.globalactions.presentation.view.GlobalActionsContentView.2
                    @Override // android.view.IRotationWatcher
                    public void onRotationChanged(int rotation) {
                        GlobalActionsContentView.this.forceRequestLayout();
                    }
                };
        try {
            this.mIWindowManager.watchRotation(
                    this.mRotationWatcher, this.mContext.getDisplay().getDisplayId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void onDismiss() {
        if (this.mRotationWatcher != null) {
            try {
                this.mIWindowManager.removeRotationWatcher(this.mRotationWatcher);
                this.mRotationWatcher = null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (this.mAnimator != null) {
            this.mAnimator = null;
        }
        if (this.mAnimatorFSM != null) {
            this.mAnimatorFSM = null;
        }
        if (this.mTopView != null && this.mTopView.getChildCount() != 0) {
            this.mTopView.removeAllViews();
            this.mTopView = null;
        }
        if (this.mBottomButtonView != null && this.mBottomButtonView.getChildCount() != 0) {
            this.mBottomButtonView.removeAllViews();
            this.mBottomButtonView = null;
        }
        if (this.mBottomMsgView != null && this.mBottomMsgView.getChildCount() != 0) {
            this.mBottomMsgView.removeAllViews();
            this.mBottomMsgView = null;
        }
        this.mGridViewAdapter.resetItems();
        this.mListView.setAdapter((ListAdapter) null);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public ViewAnimationState getAnimationState() {
        return getState();
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void setAnimationState(ViewAnimationState state) {
        setState(state);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ViewStateController
    public ViewAnimationState getState() {
        return this.mViewAnimationState;
    }

    @Override // com.samsung.android.globalactions.presentation.view.ViewStateController
    public void setState(ViewAnimationState viewAnimationState) {
        this.mViewAnimationState = viewAnimationState;
    }

    public class SamsungGlobalActionsRootView extends FrameLayout {
        public SamsungGlobalActionsRootView(Context context) {
            super(context);
            View rootView =
                    View.inflate(
                            context,
                            GlobalActionsContentView.this.mResourceFactory.get(
                                    ResourceType.LAYOUT_ROOT_VIEW),
                            null);
            addView(rootView);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == 0) {
                GlobalActionsContentView.this.mDialog.cancel();
                return true;
            }
            return true;
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            if (changed || GlobalActionsContentView.this.mNeedToForceUpdate) {
                GlobalActionsContentView.this.mLogWrapper.i(
                        GlobalActionsContentView.TAG, "RootView onLayout");
                setRootViewPadding();
                setLandListViewWidth();
                setPortListViewWidth();
                setGridViewMargin();
                setTopViewMargin();
                setBugReportViewMargin();
                setSideKeySettingsViewMargin();
                updateBottomViewProperties();
                setListViewHeight();
                GlobalActionsContentView.this.mNeedToForceUpdate = false;
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            boolean isPortrait = this.mContext.getResources().getConfiguration().orientation == 1;
            GlobalActionsContentView.this.mLogWrapper.logDebug(
                    GlobalActionsContentView.TAG,
                    "onAttachedWindow newConfig.orientation = " + isPortrait);
            GlobalActionsContentView.this.mNeedToForceUpdate = true;
            GlobalActionsContentView.this.mAnimatorFSM.setOrientation(isPortrait);
            GlobalActionsContentView.this.mAnimatorFSM.handleAnimationEvent(
                    SamsungGlobalActionsAnimatorFSM.Event.SHOW);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            GlobalActionsContentView.this.mAnimatorFSM = null;
            GlobalActionsContentView.this.mAnimator = null;
        }

        @Override // android.view.View
        protected void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            boolean isPortrait = newConfig.orientation == 1;
            GlobalActionsContentView.this.mAnimatorFSM.setOrientation(isPortrait);
            GlobalActionsContentView.this.mAnimatorFSM.handleAnimationEvent(
                    SamsungGlobalActionsAnimatorFSM.Event.CONFIGURATION_CHANGED);
        }

        private void setRootViewPadding() {
            WindowManager wm =
                    (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay();
            if (this.mContext.getResources().getConfiguration().semMobileKeyboardCovered == 1) {
                GlobalActionsContentView.this.mRootView.setPadding(0, 0, 0, 0);
                return;
            }
            int delta =
                    this.mContext
                            .getResources()
                            .getDimensionPixelSize(R.dimen.navigation_bar_height);
            int navbarPos = GlobalActionsContentView.this.mWindowManagerUtil.getNavBarPosition();
            if (navbarPos == 1) {
                GlobalActionsContentView.this.mRootView.setPadding(delta, 0, 0, 0);
                return;
            }
            if (navbarPos == 2) {
                GlobalActionsContentView.this.mRootView.setPadding(0, 0, delta, 0);
                return;
            }
            if (GlobalActionsContentView.this.mConditionChecker.isEnabled(
                    SystemConditions.IS_TABLET_DEVICE)) {
                delta =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(R.dimen.navigation_bar_frame_height);
                if (GlobalActionsContentView.this.mConditionChecker.isEnabled(
                        SystemConditions.IS_DESKTOP_MODE_STANDALONE)) {
                    delta =
                            GlobalActionsContentView.this
                                    .mRootView
                                    .getRootWindowInsets()
                                    .getStableInsetBottom();
                }
            }
            GlobalActionsContentView.this.mRootView.setPadding(0, 0, 0, delta);
        }

        private void setLandListViewWidth() {
            ViewGroup.LayoutParams params =
                    GlobalActionsContentView.this.mLandListView.getLayoutParams();
            params.width =
                    (GlobalActionsContentView.this.mItemWidthLand
                                    * GlobalActionsContentView.this.mLandListView.getNumColumns())
                            + (GlobalActionsContentView.this.mItemHorizontalSpacing
                                    * (GlobalActionsContentView.this.mLandListView.getNumColumns()
                                            - 1));
            GlobalActionsContentView.this.mLandListView.setLayoutParams(params);
        }

        private void setPortListViewWidth() {
            if (GlobalActionsContentView.this.mConditionChecker.isEnabled(
                            SystemConditions.IS_TABLET_DEVICE)
                    || (GlobalActionsContentView.this.mConditionChecker.isEnabled(
                                    SystemConditions.IS_FOLD_DEVICE)
                            && !GlobalActionsContentView.this.mConditionChecker.isEnabled(
                                    SystemConditions.IS_FOLDED))) {
                ViewGroup.LayoutParams params =
                        GlobalActionsContentView.this.mListView.getLayoutParams();
                params.width =
                        (GlobalActionsContentView.this.mItemWidthPort
                                        * GlobalActionsContentView.this.mListView.getNumColumns())
                                + (GlobalActionsContentView.this.mItemVerticalSpacingPort
                                        * (GlobalActionsContentView.this.mListView.getNumColumns()
                                                - 1));
                GlobalActionsContentView.this.mListView.setLayoutParams(params);
            } else {
                ViewGroup.LayoutParams params2 =
                        GlobalActionsContentView.this.mListView.getLayoutParams();
                params2.width =
                        (GlobalActionsContentView.this.mItemWidthPortPhone
                                        * GlobalActionsContentView.this.mListView.getNumColumns())
                                + (GlobalActionsContentView.this.mItemHorizontalSpacing
                                        * (GlobalActionsContentView.this.mListView.getNumColumns()
                                                - 1));
                GlobalActionsContentView.this.mListView.setLayoutParams(params2);
            }
        }

        private void setGridViewMargin() {
            if (GlobalActionsContentView.this.mBottomMsgView != null
                    && GlobalActionsContentView.this.mBottomButtonView.getChildCount() != 0) {
                int bottomViewMarginBottom =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.sec_global_actions_bottom_view_margin_bottom);
                int bottomViewHeight =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen
                                                .sec_global_actions_items_vertical_spacing_port_only_4_buttons);
                if (GlobalActionsContentView.this.mBottomMsgView != null
                        && GlobalActionsContentView.this.mBottomMsgView.getChildCount() != 0) {
                    LinearLayout bottomContainerView =
                            (LinearLayout)
                                    GlobalActionsContentView.this.mRootView.findViewById(
                                            GlobalActionsContentView.this.mResourceFactory.get(
                                                    ResourceType.ID_BOTTOM_BUTTON_CONTAINER));
                    int orientation = this.mContext.getResources().getConfiguration().orientation;
                    boolean isPortrait = orientation == 1;
                    if (isPortrait) {
                        if (bottomContainerView != null
                                && isTaskBarEnabled()
                                && isNavBarGestureType()) {
                            bottomViewMarginBottom =
                                    this.mContext
                                            .getResources()
                                            .getDimensionPixelSize(
                                                    GlobalActionsContentView.this.mResourceFactory
                                                            .get(
                                                                    ResourceType
                                                                            .DIMEN_BUGREPORT_BOTTOM_MARGIN_TASK));
                            ViewGroup.MarginLayoutParams lp =
                                    (ViewGroup.MarginLayoutParams)
                                            bottomContainerView.getLayoutParams();
                            lp.bottomMargin = bottomViewMarginBottom;
                            bottomContainerView.setLayoutParams(lp);
                        } else {
                            bottomViewHeight *= 2;
                        }
                    } else {
                        bottomViewHeight *= 2;
                    }
                }
                ViewGroup.MarginLayoutParams lp2 =
                        (ViewGroup.MarginLayoutParams)
                                GlobalActionsContentView.this.mListView.getLayoutParams();
                lp2.bottomMargin = (bottomViewMarginBottom * 2) + bottomViewHeight;
                GlobalActionsContentView.this.mListView.setLayoutParams(lp2);
                ViewGroup.MarginLayoutParams lp3 =
                        (ViewGroup.MarginLayoutParams)
                                GlobalActionsContentView.this.mLandListView.getLayoutParams();
                lp3.bottomMargin = bottomViewHeight;
                GlobalActionsContentView.this.mLandListView.setLayoutParams(lp3);
            }
        }

        private void setTopViewMargin() {
            if (GlobalActionsContentView.this.mTopView == null
                    || GlobalActionsContentView.this.mTopView.getChildCount() <= 0) {
                return;
            }
            int orientation = this.mContext.getResources().getConfiguration().orientation;
            boolean isPortrait = orientation == 1;
            ImageView imageView =
                    (ImageView)
                            GlobalActionsContentView.this.mTopView.findViewById(
                                    GlobalActionsContentView.this.mResourceFactory.get(
                                            ResourceType.ID_ICON));
            if (imageView != null) {
                ViewGroup.MarginLayoutParams lp =
                        (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                if (isPortrait) {
                    lp.topMargin =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            GlobalActionsContentView.this.mResourceFactory.get(
                                                    ResourceType.DIMEN_BIXBY_SETTINGS_TOP_MARGIN));
                    lp.rightMargin =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            GlobalActionsContentView.this.mResourceFactory.get(
                                                    ResourceType
                                                            .DIMEN_BIXBY_SETTINGS_RIGHT_MARGIN));
                } else {
                    lp.topMargin =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            GlobalActionsContentView.this.mResourceFactory.get(
                                                    ResourceType
                                                            .DIMEN_BIXBY_SETTINGS_TOP_MARGIN_LAND));
                    lp.rightMargin =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            GlobalActionsContentView.this.mResourceFactory.get(
                                                    ResourceType
                                                            .DIMEN_BIXBY_SETTINGS_RIGHT_MARGIN_LAND));
                }
                imageView.setLayoutParams(lp);
            }
        }

        public boolean isTaskBarEnabled() {
            return GlobalActionsContentView.this.mConditionChecker.isEnabled(
                    SystemConditions.IS_TASK_BAR_ENABLED);
        }

        public boolean isNavBarGestureType() {
            return GlobalActionsContentView.this.mConditionChecker.isEnabled(
                    SystemConditions.IS_NAV_BAR_GESTURE_ENABLED);
        }

        private void setBugReportViewMargin() {
            if (GlobalActionsContentView.this.mBottomButtonView == null
                    || GlobalActionsContentView.this.mBottomButtonView.getChildCount() <= 0) {
                return;
            }
            int orientation = this.mContext.getResources().getConfiguration().orientation;
            boolean isPortrait = orientation == 1;
            FrameLayout bugreportView =
                    (FrameLayout)
                            GlobalActionsContentView.this.mBottomButtonView.findViewById(
                                    GlobalActionsContentView.this.mResourceFactory.get(
                                            ResourceType.ID_BUGREPORT_VIEW));
            if (bugreportView != null) {
                ViewGroup.MarginLayoutParams lp =
                        (ViewGroup.MarginLayoutParams) bugreportView.getLayoutParams();
                if (isPortrait) {
                    if (isTaskBarEnabled() && isNavBarGestureType()) {
                        lp.bottomMargin =
                                this.mContext
                                        .getResources()
                                        .getDimensionPixelSize(
                                                GlobalActionsContentView.this.mResourceFactory.get(
                                                        ResourceType
                                                                .DIMEN_BUGREPORT_BOTTOM_MARGIN_TASK));
                    } else {
                        lp.bottomMargin =
                                this.mContext
                                        .getResources()
                                        .getDimensionPixelSize(
                                                GlobalActionsContentView.this.mResourceFactory.get(
                                                        ResourceType
                                                                .DIMEN_BUGREPORT_BOTTOM_MARGIN));
                    }
                } else {
                    int margin =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            GlobalActionsContentView.this.mResourceFactory.get(
                                                    ResourceType
                                                            .DIMEN_BUGREPORT_BOTTOM_MARGIN_LAND));
                    if (GlobalActionsContentView.this.mConditionChecker.isEnabled(
                                    SystemConditions.IS_TABLET_DEVICE)
                            && GlobalActionsContentView.this.mConditionChecker.isEnabled(
                                    SystemConditions.IS_DESKTOP_MODE_STANDALONE)) {
                        margin +=
                                this.mContext
                                        .getResources()
                                        .getDimensionPixelSize(R.dimen.navigation_bar_frame_height);
                    }
                    lp.bottomMargin = margin;
                }
                bugreportView.setLayoutParams(lp);
            }
        }

        private void setSideKeySettingsViewMargin() {
            if (GlobalActionsContentView.this.mBottomButtonView == null
                    || GlobalActionsContentView.this.mBottomButtonView.getChildCount() <= 0) {
                return;
            }
            FrameLayout bugreportView =
                    (FrameLayout)
                            GlobalActionsContentView.this.mBottomButtonView.findViewById(
                                    GlobalActionsContentView.this.mResourceFactory.get(
                                            ResourceType.ID_BUGREPORT_VIEW));
            FrameLayout sideKeySettingsView =
                    (FrameLayout)
                            GlobalActionsContentView.this.mBottomButtonView.findViewById(
                                    GlobalActionsContentView.this.mResourceFactory.get(
                                            ResourceType.ID_SIDEKEY_SETTINGS_VIEW));
            int orientation = this.mContext.getResources().getConfiguration().orientation;
            boolean isPortrait = orientation == 1;
            if (sideKeySettingsView != null) {
                ViewGroup.MarginLayoutParams lp =
                        (ViewGroup.MarginLayoutParams) sideKeySettingsView.getLayoutParams();
                if (isPortrait) {
                    if (bugreportView != null) {
                        lp.bottomMargin =
                                this.mContext
                                        .getResources()
                                        .getDimensionPixelSize(
                                                GlobalActionsContentView.this.mResourceFactory.get(
                                                        ResourceType
                                                                .DIMEN_SIDEKEY_SETTINGS_BOTTOM_MARGIN_2BTNS));
                    } else {
                        lp.bottomMargin =
                                this.mContext
                                        .getResources()
                                        .getDimensionPixelSize(
                                                GlobalActionsContentView.this.mResourceFactory.get(
                                                        ResourceType
                                                                .DIMEN_SIDEKEY_SETTINGS_BOTTOM_MARGIN_1BTN));
                    }
                    lp.setMarginEnd(0);
                } else {
                    int margin =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            GlobalActionsContentView.this.mResourceFactory.get(
                                                    ResourceType
                                                            .DIMEN_SIDEKEY_SETTINGS_BOTTOM_MARGIN_LAND));
                    if (GlobalActionsContentView.this.mConditionChecker.isEnabled(
                                    SystemConditions.IS_TABLET_DEVICE)
                            && GlobalActionsContentView.this.mConditionChecker.isEnabled(
                                    SystemConditions.IS_DESKTOP_MODE_STANDALONE)) {
                        margin +=
                                this.mContext
                                        .getResources()
                                        .getDimensionPixelSize(R.dimen.navigation_bar_frame_height);
                    }
                    if (isTaskBarEnabled() && isNavBarGestureType()) {
                        margin +=
                                this.mContext
                                        .getResources()
                                        .getDimensionPixelSize(
                                                GlobalActionsContentView.this.mResourceFactory.get(
                                                        ResourceType
                                                                .DIMEN_BUGREPORT_BOTTOM_MARGIN_TASK));
                    }
                    lp.bottomMargin = margin;
                    if (bugreportView != null) {
                        lp.setMarginEnd(
                                this.mContext
                                        .getResources()
                                        .getDimensionPixelSize(
                                                GlobalActionsContentView.this.mResourceFactory.get(
                                                        ResourceType
                                                                .DIMEN_SIDEKEY_SETTINGS_RIGHT_MARGIN_LAND)));
                    }
                }
                sideKeySettingsView.setLayoutParams(lp);
            }
        }

        private void updateBottomViewProperties() {
            if (GlobalActionsContentView.this.mBottomButtonView.getChildCount() > 0) {
                int orientation = this.mContext.getResources().getConfiguration().orientation;
                boolean isPortrait = orientation == 1;
                ViewGroup.MarginLayoutParams lp =
                        (ViewGroup.MarginLayoutParams)
                                GlobalActionsContentView.this.mBottomButtonView.getLayoutParams();
                GlobalActionsContentView.this.mBottomButtonView.setOrientation(isPortrait ? 1 : 0);
                if (!isPortrait && GlobalActionsContentView.this.mGridViewAdapter.getCount() < 4) {
                    lp.bottomMargin =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            GlobalActionsContentView.this.mResourceFactory.get(
                                                    ResourceType
                                                            .DIMEN_BOTTOMBUTTONVIEW_BOTTOM_MARGIN_LAND));
                } else {
                    lp.bottomMargin = 0;
                }
                GlobalActionsContentView.this.mBottomButtonView.setLayoutParams(lp);
            }
            GlobalActionsContentView.this.mBottomButtonView.setGravity(16);
        }

        private void setListViewHeight() {
            int height = 0;
            int firstColumn = 0;
            int numRows = 0;
            int orientation = this.mContext.getResources().getConfiguration().orientation;
            boolean isPortrait = orientation == 1;
            boolean isListHeightModified = false;
            if (GlobalActionsContentView.this.mListView.getNumColumns() != 2) {
                if (isPortrait && GlobalActionsContentView.this.mListView.getNumColumns() == 1) {
                    for (int i = 0;
                            i < GlobalActionsContentView.this.mListView.getChildCount();
                            i++) {
                        firstColumn +=
                                GlobalActionsContentView.this.mListView.getChildAt(i).getHeight();
                        numRows++;
                    }
                    height =
                            firstColumn
                                    + (GlobalActionsContentView.this.mListView.getVerticalSpacing()
                                            * (numRows - 1));
                    isListHeightModified = true;
                }
            } else {
                int secondColumn = 0;
                for (int i2 = 0;
                        i2 < GlobalActionsContentView.this.mListView.getChildCount();
                        i2++) {
                    if (i2 % 2 == 0) {
                        firstColumn +=
                                GlobalActionsContentView.this.mListView.getChildAt(i2).getHeight();
                        numRows++;
                    } else {
                        secondColumn +=
                                GlobalActionsContentView.this.mListView.getChildAt(i2).getHeight();
                    }
                }
                height =
                        (firstColumn > secondColumn ? firstColumn : secondColumn)
                                + (GlobalActionsContentView.this.mListView.getVerticalSpacing()
                                        * (numRows - 1));
                isListHeightModified = true;
            }
            if (isListHeightModified) {
                int bottom =
                        GlobalActionsContentView.this
                                .mListView
                                .getChildAt(
                                        GlobalActionsContentView.this.mListView.getChildCount() - 1)
                                .getBottom();
                if (bottom > height) {
                    height = bottom;
                }
                ViewGroup.MarginLayoutParams marginLayoutParams =
                        (ViewGroup.MarginLayoutParams)
                                GlobalActionsContentView.this.mListView.getLayoutParams();
                int availableSpace =
                        (GlobalActionsContentView.this.mRootView.getHeight()
                                        - GlobalActionsContentView.this.mRootView
                                                .getPaddingBottom())
                                - marginLayoutParams.bottomMargin;
                if (height < availableSpace) {
                    ViewGroup.LayoutParams params =
                            GlobalActionsContentView.this.mListView.getLayoutParams();
                    params.height = height;
                    GlobalActionsContentView.this.mListView.setLayoutParams(params);
                }
            }
        }
    }

    public class SamsungGlobalActionsBackgroundView extends FrameLayout {
        public SamsungGlobalActionsBackgroundView(Context context) {
            super(context);
            View view =
                    View.inflate(
                            context,
                            GlobalActionsContentView.this.mResourceFactory.get(
                                    ResourceType.LAYOUT_BLUR_BACKGROUND),
                            null);
            addView(view);
        }
    }

    public class BaseContentAdapter extends BaseAdapter {
        Context mContext;
        List<ActionViewModel> mViewModelList = new ArrayList();

        public BaseContentAdapter(Context context) {
            this.mContext = context;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mViewModelList.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return this.mViewModelList.get(position);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            GlobalActionsContentItemView item =
                    new GlobalActionsContentItemView(
                            this.mContext,
                            this.mViewModelList.get(position),
                            parent,
                            GlobalActionsContentView.this.mResourceFactory,
                            GlobalActionsContentView.this.mIsVoiceAssistantMode,
                            GlobalActionsContentView.this.mIsWhiteTheme,
                            GlobalActionsContentView.this.mViewStateController);
            if (convertView == null) {
                convertView = item.inflateView();
            }
            item.setViewAttrs(convertView, false);
            item.setViewIndex(position);
            return convertView;
        }

        public void addItem(ActionViewModel viewModel) {
            this.mViewModelList.add(viewModel);
        }

        public void resetItems() {
            this.mViewModelList.clear();
        }

        public void updateNumColumns() {
            int numItems = this.mViewModelList.size();
            if (numItems >= 4) {
                GlobalActionsContentView.this.mListView.setNumColumns(2);
            } else {
                GlobalActionsContentView.this.mListView.setNumColumns(1);
            }
            if (numItems < 4) {
                GlobalActionsContentView.this.mLandListView.setNumColumns(numItems);
            } else if (numItems == 4) {
                GlobalActionsContentView.this.mLandListView.setNumColumns(2);
            } else {
                GlobalActionsContentView.this.mLandListView.setNumColumns(3);
            }
            setVerticalSpacing();
        }

        private void setVerticalSpacing() {
            if (!GlobalActionsContentView.this.mConditionChecker.isEnabled(
                    SystemConditions.IS_TABLET_DEVICE)) {
                if (this.mViewModelList.size() == 4) {
                    GlobalActionsContentView.this.mListView.setVerticalSpacing(
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen
                                                    .sec_global_actions_items_vertical_spacing_port_only_4_buttons));
                } else {
                    GlobalActionsContentView.this.mListView.setVerticalSpacing(
                            GlobalActionsContentView.this.mItemVerticalSpacingPort);
                }
            }
        }
    }

    public class SamsungGlobalActionsGridView extends GridView {
        boolean mIsVerticalMode;

        public SamsungGlobalActionsGridView(Context context, boolean isVerticalMode) {
            super(context);
            this.mIsVerticalMode = isVerticalMode;
            setHorizontalSpacing(GlobalActionsContentView.this.mItemHorizontalSpacing);
            if (isVerticalMode) {
                setVerticalSpacing(GlobalActionsContentView.this.mItemVerticalSpacingPort);
            } else {
                setVerticalSpacing(GlobalActionsContentView.this.mItemVerticalSpacingLand);
                setColumnWidth(GlobalActionsContentView.this.mItemWidthLand);
            }
            setFocusable(false);
            setVerticalScrollBarEnabled(false);
        }

        public boolean isVerticalMode() {
            return this.mIsVerticalMode;
        }

        @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
        public boolean onTouchEvent(MotionEvent ev) {
            if (ev.getAction() == 0) {
                return false;
            }
            return super.onTouchEvent(ev);
        }
    }

    /* renamed from: com.samsung.android.globalactions.presentation.view.GlobalActionsContentView$3, reason: invalid class name */
    class AnonymousClass3 implements SamsungGlobalActionsAnimator.ViewUpdateCallback {
        AnonymousClass3() {}

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public Dialog getDialog() {
            return GlobalActionsContentView.this.mDialog;
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public ViewGroup getBackgroundView() {
            return GlobalActionsContentView.this.mBackgroundView;
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public ViewGroup getRootView() {
            return GlobalActionsContentView.this.mRootView;
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public ViewGroup getListView() {
            return GlobalActionsContentView.this.mListView;
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public ViewGroup getLandscapeListView() {
            return GlobalActionsContentView.this.mLandListView;
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public View getBottomView() {
            return GlobalActionsContentView.this.mBottomButtonView;
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public ViewGroup getConfirmationView() {
            return GlobalActionsContentView.this.mConfirmationView;
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public ViewGroup getConfirmIconLabelView(ViewGroup v) {
            return (ViewGroup)
                    v.findViewById(
                            GlobalActionsContentView.this.mResourceFactory.get(
                                    ResourceType.ID_ICON_LABEL));
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public View getConfirmDescriptionView(ViewGroup v) {
            return v.findViewById(
                    GlobalActionsContentView.this.mResourceFactory.get(
                            ResourceType.ID_DESCRIPTION));
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public GlobalActionsContentItemView createConfirmView() {
            GlobalActionsContentItemView item =
                    new GlobalActionsContentItemView(
                            GlobalActionsContentView.this.mContext,
                            GlobalActionsContentView.this.mSelectedViewModel,
                            GlobalActionsContentView.this.mConfirmationView,
                            GlobalActionsContentView.this.mResourceFactory,
                            GlobalActionsContentView.this.mIsVoiceAssistantMode,
                            GlobalActionsContentView.this.mIsWhiteTheme,
                            GlobalActionsContentView.this.mViewStateController);
            GlobalActionsContentView.this.mConfirmationView.removeAllViews();
            GlobalActionsContentView.this.mConfirmationView.addView(item.createView(true));
            View stateView =
                    GlobalActionsContentView.this.mConfirmationView.findViewById(
                            GlobalActionsContentView.this.mResourceFactory.get(
                                    ResourceType.ID_STATE));
            GlobalActionsContentView.this.mConfirmationView.setVisibility(0);
            getConfirmDescriptionView(GlobalActionsContentView.this.mConfirmationView)
                    .setVisibility(0);
            stateView.setVisibility(8);
            return item;
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public ViewGroup getSelectedActionView(ViewGroup targetView) {
            return (ViewGroup)
                    targetView.getChildAt(
                            GlobalActionsContentView.this
                                    .mSelectedViewModel
                                    .getActionInfo()
                                    .getViewIndex());
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public boolean isSafeModeConfirm() {
            return GlobalActionsContentView.this
                    .mSelectedViewModel
                    .getActionInfo()
                    .getName()
                    .equals(DefaultActionNames.ACTION_SAFE_MODE);
        }

        public /* synthetic */ void lambda$getDismissRunnable$0() {
            GlobalActionsContentView.this.mParentView.dismiss();
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public Runnable getDismissRunnable() {
            return new Runnable() { // from class:
                                    // com.samsung.android.globalactions.presentation.view.GlobalActionsContentView$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalActionsContentView.AnonymousClass3.this.lambda$getDismissRunnable$0();
                }
            };
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public void requestFocusFor(ViewGroup target, ViewGroup prevTarget) {
            View iconView =
                    target.findViewById(
                            GlobalActionsContentView.this.mResourceFactory.get(
                                    ResourceType.ID_ICON));
            View prevIconView =
                    prevTarget.findViewById(
                            GlobalActionsContentView.this.mResourceFactory.get(
                                    ResourceType.ID_ICON));
            if (GlobalActionsContentView.this.mIsVoiceAssistantMode && prevIconView != null) {
                if (prevIconView.isAccessibilityFocused()) {
                    iconView.performAccessibilityAction(64, null);
                    iconView.requestFocus();
                    return;
                } else {
                    GlobalActionsContentView.this.mRootView.setDescendantFocusability(393216);
                    return;
                }
            }
            if (!GlobalActionsContentView.this.mIsVoiceAssistantMode) {
                iconView.requestFocus();
            }
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public ViewGroup getPowerOffViewForSafeModeVI(GlobalActionsContentItemView item) {
            ViewGroup powerOffItemView = (ViewGroup) item.createView(false);
            ImageView powerOffIconView =
                    (ImageView)
                            powerOffItemView.findViewById(
                                    GlobalActionsContentView.this.mResourceFactory.get(
                                            ResourceType.ID_ICON));
            TextView powerOffLabelView =
                    (TextView)
                            powerOffItemView.findViewById(
                                    GlobalActionsContentView.this.mResourceFactory.get(
                                            ResourceType.ID_LABEL));
            powerOffLabelView.lambda$setTextAsync$0(
                    GlobalActionsContentView.this
                            .mContext
                            .getResources()
                            .getText(R.string.samsung_global_action_power_off));
            powerOffIconView.setImageDrawable(
                    GlobalActionsContentView.this
                            .mContext
                            .getResources()
                            .getDrawable(
                                    GlobalActionsContentView.this.mResourceFactory.get(
                                            ResourceType.DRAWABLE_POWEROFF),
                                    null));
            ViewGroup powerOffView = getConfirmIconLabelView(powerOffItemView);
            ((ViewGroup) powerOffView.getParent()).removeAllViews();
            GlobalActionsContentView.this.mConfirmationView.addView(powerOffView);
            FrameLayout.LayoutParams params =
                    (FrameLayout.LayoutParams) powerOffView.getLayoutParams();
            params.gravity = 17;
            powerOffView.setLayoutParams(params);
            powerOffView.setVisibility(0);
            return powerOffView;
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public void setFlagsForForceDismiss(boolean state) {
            GlobalActionsContentView.this.mParentView.setCoverSecureConfirmState(state);
            GlobalActionsContentView.this.mForceDismiss = state;
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public boolean getForceDismissState() {
            return GlobalActionsContentView.this.mForceDismiss;
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public boolean getClearCoverState() {
            return GlobalActionsContentView.this.mIsClearCoverClosed;
        }
    }
}
