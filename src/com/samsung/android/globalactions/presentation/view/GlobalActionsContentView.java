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
import com.samsung.android.globalactions.presentation.view.GlobalActionsContentView;
import com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator;
import com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimatorFSM;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.presentation.viewmodel.DefaultActionNames;
import com.samsung.android.globalactions.presentation.viewmodel.ViewType;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.WindowManagerUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
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
    private SamsungGlobalActionsAnimator.ViewUpdateCallback mBaseAnimatorCallback = new AnonymousClass3();
    private boolean mForceDismiss = false;
    private ViewAnimationState mViewAnimationState = ViewAnimationState.IDLE;
    private ViewStateController mViewStateController = this;

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void forceRequestLayout() {
    }

    public GlobalActionsContentView(Context context, ExtendableGlobalActionsView extendableGlobalActionsView, FeatureFactory featureFactory, ConditionChecker conditionChecker, WindowManagerUtils windowManagerUtils, ResourceFactory resourceFactory, LogWrapper logWrapper, Dialog dialog, boolean z) {
        this.mContext = context;
        this.mParentView = extendableGlobalActionsView;
        this.mFeatureFactory = featureFactory;
        this.mConditionChecker = conditionChecker;
        this.mWindowManagerUtil = windowManagerUtils;
        this.mResourceFactory = resourceFactory;
        this.mLogWrapper = logWrapper;
        this.mDialog = dialog;
        this.mIsClearCoverClosed = z;
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void initDimens() {
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE)) {
            this.mItemHorizontalSpacing = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_global_actions_items_horizontal_spacing_tablet);
            this.mItemVerticalSpacingPort = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_global_actions_items_vertical_spacing_port_tablet);
        } else {
            this.mItemHorizontalSpacing = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_global_actions_items_horizontal_spacing);
            this.mItemVerticalSpacingPort = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_global_actions_items_vertical_spacing_port);
        }
        this.mItemVerticalSpacingLand = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_global_actions_items_vertical_spacing_land);
        this.mItemWidthLand = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_global_actions_items_width_land);
        this.mItemWidthPort = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_global_actions_items_width_port);
        this.mItemWidthPortPhone = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_global_actions_items_width_port_phone);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void initLayouts() {
        SamsungGlobalActionsRootView samsungGlobalActionsRootView = new SamsungGlobalActionsRootView(this.mContext);
        this.mRootView = samsungGlobalActionsRootView;
        ViewGroup viewGroup = (ViewGroup) samsungGlobalActionsRootView.findViewById(this.mResourceFactory.get(ResourceType.ID_ITEM_LIST));
        viewGroup.setContentDescription(this.mContext.getResources().getText(R.string.global_action_power_off_menu));
        SamsungGlobalActionsGridView samsungGlobalActionsGridView = new SamsungGlobalActionsGridView(this, this.mContext, true);
        this.mListView = samsungGlobalActionsGridView;
        viewGroup.addView(samsungGlobalActionsGridView);
        ViewGroup viewGroup2 = (ViewGroup) this.mRootView.findViewById(this.mResourceFactory.get(ResourceType.ID_ITEM_LIST_LAND));
        SamsungGlobalActionsGridView samsungGlobalActionsGridView2 = new SamsungGlobalActionsGridView(this, this.mContext, false);
        this.mLandListView = samsungGlobalActionsGridView2;
        viewGroup2.addView(samsungGlobalActionsGridView2);
        this.mTopView = (LinearLayout) this.mRootView.findViewById(this.mResourceFactory.get(ResourceType.ID_TOP_VIEW));
        this.mBottomButtonView = (LinearLayout) this.mRootView.findViewById(this.mResourceFactory.get(ResourceType.ID_BOTTOM_BUTTON_VIEW));
        this.mBottomMsgView = (LinearLayout) this.mRootView.findViewById(this.mResourceFactory.get(ResourceType.ID_FORCE_RESTART_TEXT_VIEW));
        this.mConfirmationView = (ViewGroup) this.mRootView.findViewById(this.mResourceFactory.get(ResourceType.ID_CONFIRMATION_VIEW));
        FrameLayout frameLayout = (FrameLayout) this.mRootView.findViewById(this.mResourceFactory.get(ResourceType.ID_SCREEN_CAPTURE_POPUP));
        this.mPopupView = frameLayout;
        if (frameLayout != null) {
            ((ImageView) frameLayout.findViewById(this.mResourceFactory.get(ResourceType.ID_ICON))).setOnClickListener(new View.OnClickListener() { // from class: com.samsung.android.globalactions.presentation.view.GlobalActionsContentView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GlobalActionsContentView.this.lambda$initLayouts$0(view);
                }
            });
        }
        this.mIsVoiceAssistantMode = this.mConditionChecker.isEnabled(SystemConditions.IS_VOICE_ASSISTANT_MODE);
        this.mIsWhiteTheme = this.mConditionChecker.isEnabled(SystemConditions.IS_WHITE_THEME);
        BaseContentAdapter baseContentAdapter = new BaseContentAdapter(this.mContext);
        this.mGridViewAdapter = baseContentAdapter;
        this.mListView.setAdapter((ListAdapter) baseContentAdapter);
        this.mLandListView.setAdapter((ListAdapter) this.mGridViewAdapter);
        this.mDialog.setContentView(this.mRootView);
        if (this.mConditionChecker.isEnabled(SystemConditions.IS_SUPPORT_SF_EFFECT) || this.mConditionChecker.isEnabled(SystemConditions.IS_SUPPORT_CAPTURED_BLUR)) {
            this.mBackgroundView = new SamsungGlobalActionsBackgroundView(this, this.mContext);
            Iterator<ViewInflateStrategy> it = this.mFeatureFactory.createViewInflateStrategy().iterator();
            while (it.hasNext()) {
                it.next().onInflateView(this.mBackgroundView);
            }
            this.mDialog.addContentView(this.mBackgroundView, new FrameLayout.LayoutParams(-1, -1));
            this.mRootView.bringToFront();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initLayouts$0(View view) {
        this.mPopupView.setVisibility(8);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void initAnimations() {
        SamsungGlobalActionsAnimator samsungGlobalActionsAnimator = new SamsungGlobalActionsAnimator(this.mContext, this.mConditionChecker, this.mLogWrapper, this);
        this.mAnimator = samsungGlobalActionsAnimator;
        samsungGlobalActionsAnimator.setCallback(this.mBaseAnimatorCallback);
        this.mAnimatorFSM = new SamsungGlobalActionsAnimatorFSM(this.mAnimator, this.mLogWrapper, this);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void updateItemLists(SamsungGlobalActionsPresenter samsungGlobalActionsPresenter) {
        GlobalActionsContentView globalActionsContentView;
        LinearLayout linearLayout = this.mTopView;
        if (linearLayout != null && linearLayout.getChildCount() != 0) {
            this.mTopView.removeAllViews();
        }
        LinearLayout linearLayout2 = this.mBottomButtonView;
        if (linearLayout2 != null && linearLayout2.getChildCount() != 0) {
            this.mBottomButtonView.removeAllViews();
        }
        LinearLayout linearLayout3 = this.mBottomMsgView;
        if (linearLayout3 != null && linearLayout3.getChildCount() != 0) {
            this.mBottomMsgView.removeAllViews();
        }
        this.mGridViewAdapter.resetItems();
        for (ActionViewModel actionViewModel : samsungGlobalActionsPresenter.getValidActions()) {
            if (this.mTopView != null && actionViewModel.getActionInfo().getViewType() == ViewType.TOP_VIEW) {
                globalActionsContentView = this;
                globalActionsContentView.mTopView.addView(new GlobalActionsContentItemView(this.mContext, actionViewModel, this.mTopView, this.mResourceFactory, this.mIsVoiceAssistantMode, this.mIsWhiteTheme, globalActionsContentView).createView(false));
            } else {
                globalActionsContentView = this;
                if ((globalActionsContentView.mBottomButtonView != null && actionViewModel.getActionInfo().getViewType() == ViewType.KEY_SETTINGS_VIEW) || actionViewModel.getActionInfo().getViewType() == ViewType.BOTTOM_BTN_LIST_VIEW) {
                    globalActionsContentView.mBottomButtonView.addView(new GlobalActionsContentItemView(globalActionsContentView.mContext, actionViewModel, globalActionsContentView.mBottomButtonView, globalActionsContentView.mResourceFactory, globalActionsContentView.mIsVoiceAssistantMode, globalActionsContentView.mIsWhiteTheme, globalActionsContentView).createView(false));
                } else if (globalActionsContentView.mBottomMsgView != null && actionViewModel.getActionInfo().getViewType() == ViewType.BOTTOM_FORCE_RESTART_MSG_VIEW) {
                    globalActionsContentView.mBottomMsgView.addView(new GlobalActionsContentItemView(globalActionsContentView.mContext, actionViewModel, globalActionsContentView.mBottomMsgView, globalActionsContentView.mResourceFactory, globalActionsContentView.mIsVoiceAssistantMode, globalActionsContentView.mIsWhiteTheme, globalActionsContentView).createView(false));
                } else if (globalActionsContentView.mPopupView != null && actionViewModel.getActionInfo().getViewType() == ViewType.BOTTOM_POPUP_VIEW) {
                    if (actionViewModel.isAvailableShow()) {
                        globalActionsContentView.mPopupView.setVisibility(0);
                    }
                } else {
                    globalActionsContentView.mGridViewAdapter.addItem(actionViewModel);
                }
            }
            this = globalActionsContentView;
        }
        GlobalActionsContentView globalActionsContentView2 = this;
        globalActionsContentView2.mGridViewAdapter.updateNumColumns();
        globalActionsContentView2.notifyDataSetChanged();
        globalActionsContentView2.mListView.post(new Runnable() { // from class: com.samsung.android.globalactions.presentation.view.GlobalActionsContentView.1
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
        SamsungGlobalActionsAnimatorFSM samsungGlobalActionsAnimatorFSM = this.mAnimatorFSM;
        if (samsungGlobalActionsAnimatorFSM != null) {
            samsungGlobalActionsAnimatorFSM.handleAnimationEvent(SamsungGlobalActionsAnimatorFSM.Event.HIDE);
        }
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void showConfirm(ActionViewModel actionViewModel) {
        this.mSelectedViewModel = actionViewModel;
        this.mAnimatorFSM.handleAnimationEvent(SamsungGlobalActionsAnimatorFSM.Event.SHOW_CONFIRM);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void hideConfirm() {
        this.mAnimatorFSM.handleAnimationEvent(SamsungGlobalActionsAnimatorFSM.Event.HIDE_CONFIRM);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void hideDialogOnSecureConfirm() {
        this.mAnimatorFSM.handleAnimationEvent(SamsungGlobalActionsAnimatorFSM.Event.SECURE_CONFIRM);
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void notifyDataSetChanged() {
        this.mGridViewAdapter.notifyDataSetChanged();
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void registerRotationWatcher() {
        this.mIWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));
        IRotationWatcher.Stub stub = new IRotationWatcher.Stub() { // from class: com.samsung.android.globalactions.presentation.view.GlobalActionsContentView.2
            @Override // android.view.IRotationWatcher
            public void onRotationChanged(int i) {
                GlobalActionsContentView.this.forceRequestLayout();
            }
        };
        this.mRotationWatcher = stub;
        try {
            this.mIWindowManager.watchRotation(stub, this.mContext.getDisplay().getDisplayId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentView
    public void onDismiss() {
        IRotationWatcher.Stub stub = this.mRotationWatcher;
        if (stub != null) {
            try {
                this.mIWindowManager.removeRotationWatcher(stub);
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
        LinearLayout linearLayout = this.mTopView;
        if (linearLayout != null && linearLayout.getChildCount() != 0) {
            this.mTopView.removeAllViews();
            this.mTopView = null;
        }
        LinearLayout linearLayout2 = this.mBottomButtonView;
        if (linearLayout2 != null && linearLayout2.getChildCount() != 0) {
            this.mBottomButtonView.removeAllViews();
            this.mBottomButtonView = null;
        }
        LinearLayout linearLayout3 = this.mBottomMsgView;
        if (linearLayout3 != null && linearLayout3.getChildCount() != 0) {
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
    public void setAnimationState(ViewAnimationState viewAnimationState) {
        setState(viewAnimationState);
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
            addView(View.inflate(context, GlobalActionsContentView.this.mResourceFactory.get(ResourceType.LAYOUT_ROOT_VIEW), null));
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return true;
            }
            GlobalActionsContentView.this.mDialog.cancel();
            return true;
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            if (z || GlobalActionsContentView.this.mNeedToForceUpdate) {
                GlobalActionsContentView.this.mLogWrapper.i(GlobalActionsContentView.TAG, "RootView onLayout");
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
            boolean z = this.mContext.getResources().getConfiguration().orientation == 1;
            GlobalActionsContentView.this.mLogWrapper.logDebug(GlobalActionsContentView.TAG, "onAttachedWindow newConfig.orientation = " + z);
            GlobalActionsContentView.this.mNeedToForceUpdate = true;
            GlobalActionsContentView.this.mAnimatorFSM.setOrientation(z);
            GlobalActionsContentView.this.mAnimatorFSM.handleAnimationEvent(SamsungGlobalActionsAnimatorFSM.Event.SHOW);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            GlobalActionsContentView.this.mAnimatorFSM = null;
            GlobalActionsContentView.this.mAnimator = null;
        }

        @Override // android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            GlobalActionsContentView.this.mAnimatorFSM.setOrientation(configuration.orientation == 1);
            GlobalActionsContentView.this.mAnimatorFSM.handleAnimationEvent(SamsungGlobalActionsAnimatorFSM.Event.CONFIGURATION_CHANGED);
        }

        private void setRootViewPadding() {
            ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            if (this.mContext.getResources().getConfiguration().semMobileKeyboardCovered == 1) {
                GlobalActionsContentView.this.mRootView.setPadding(0, 0, 0, 0);
                return;
            }
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.navigation_bar_height);
            int navBarPosition = GlobalActionsContentView.this.mWindowManagerUtil.getNavBarPosition();
            if (navBarPosition == 1) {
                GlobalActionsContentView.this.mRootView.setPadding(dimensionPixelSize, 0, 0, 0);
                return;
            }
            if (navBarPosition == 2) {
                GlobalActionsContentView.this.mRootView.setPadding(0, 0, dimensionPixelSize, 0);
                return;
            }
            if (GlobalActionsContentView.this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE)) {
                dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.navigation_bar_frame_height);
                if (GlobalActionsContentView.this.mConditionChecker.isEnabled(SystemConditions.IS_DESKTOP_MODE_STANDALONE)) {
                    dimensionPixelSize = GlobalActionsContentView.this.mRootView.getRootWindowInsets().getStableInsetBottom();
                }
            }
            GlobalActionsContentView.this.mRootView.setPadding(0, 0, 0, dimensionPixelSize);
        }

        private void setLandListViewWidth() {
            ViewGroup.LayoutParams layoutParams = GlobalActionsContentView.this.mLandListView.getLayoutParams();
            layoutParams.width = (GlobalActionsContentView.this.mItemWidthLand * GlobalActionsContentView.this.mLandListView.getNumColumns()) + (GlobalActionsContentView.this.mItemHorizontalSpacing * (GlobalActionsContentView.this.mLandListView.getNumColumns() - 1));
            GlobalActionsContentView.this.mLandListView.setLayoutParams(layoutParams);
        }

        private void setPortListViewWidth() {
            if (GlobalActionsContentView.this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE) || (GlobalActionsContentView.this.mConditionChecker.isEnabled(SystemConditions.IS_FOLD_DEVICE) && !GlobalActionsContentView.this.mConditionChecker.isEnabled(SystemConditions.IS_FOLDED))) {
                ViewGroup.LayoutParams layoutParams = GlobalActionsContentView.this.mListView.getLayoutParams();
                layoutParams.width = (GlobalActionsContentView.this.mItemWidthPort * GlobalActionsContentView.this.mListView.getNumColumns()) + (GlobalActionsContentView.this.mItemVerticalSpacingPort * (GlobalActionsContentView.this.mListView.getNumColumns() - 1));
                GlobalActionsContentView.this.mListView.setLayoutParams(layoutParams);
            } else {
                ViewGroup.LayoutParams layoutParams2 = GlobalActionsContentView.this.mListView.getLayoutParams();
                layoutParams2.width = (GlobalActionsContentView.this.mItemWidthPortPhone * GlobalActionsContentView.this.mListView.getNumColumns()) + (GlobalActionsContentView.this.mItemHorizontalSpacing * (GlobalActionsContentView.this.mListView.getNumColumns() - 1));
                GlobalActionsContentView.this.mListView.setLayoutParams(layoutParams2);
            }
        }

        private void setGridViewMargin() {
            if (GlobalActionsContentView.this.mBottomMsgView == null || GlobalActionsContentView.this.mBottomButtonView.getChildCount() == 0) {
                return;
            }
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_global_actions_bottom_view_margin_bottom);
            int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_global_actions_items_vertical_spacing_port_only_4_buttons);
            if (GlobalActionsContentView.this.mBottomMsgView != null && GlobalActionsContentView.this.mBottomMsgView.getChildCount() != 0) {
                LinearLayout linearLayout = (LinearLayout) GlobalActionsContentView.this.mRootView.findViewById(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.ID_BOTTOM_BUTTON_CONTAINER));
                if (this.mContext.getResources().getConfiguration().orientation == 1 && linearLayout != null && isTaskBarEnabled() && isNavBarGestureType()) {
                    dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_BUGREPORT_BOTTOM_MARGIN_TASK));
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
                    marginLayoutParams.bottomMargin = dimensionPixelSize;
                    linearLayout.setLayoutParams(marginLayoutParams);
                } else {
                    dimensionPixelSize2 *= 2;
                }
            }
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) GlobalActionsContentView.this.mListView.getLayoutParams();
            marginLayoutParams2.bottomMargin = (dimensionPixelSize * 2) + dimensionPixelSize2;
            GlobalActionsContentView.this.mListView.setLayoutParams(marginLayoutParams2);
            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) GlobalActionsContentView.this.mLandListView.getLayoutParams();
            marginLayoutParams3.bottomMargin = dimensionPixelSize2;
            GlobalActionsContentView.this.mLandListView.setLayoutParams(marginLayoutParams3);
        }

        private void setTopViewMargin() {
            if (GlobalActionsContentView.this.mTopView == null || GlobalActionsContentView.this.mTopView.getChildCount() <= 0) {
                return;
            }
            boolean z = this.mContext.getResources().getConfiguration().orientation == 1;
            ImageView imageView = (ImageView) GlobalActionsContentView.this.mTopView.findViewById(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.ID_ICON));
            if (imageView != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                if (z) {
                    marginLayoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_BIXBY_SETTINGS_TOP_MARGIN));
                    marginLayoutParams.rightMargin = this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_BIXBY_SETTINGS_RIGHT_MARGIN));
                } else {
                    marginLayoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_BIXBY_SETTINGS_TOP_MARGIN_LAND));
                    marginLayoutParams.rightMargin = this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_BIXBY_SETTINGS_RIGHT_MARGIN_LAND));
                }
                imageView.setLayoutParams(marginLayoutParams);
            }
        }

        public boolean isTaskBarEnabled() {
            return GlobalActionsContentView.this.mConditionChecker.isEnabled(SystemConditions.IS_TASK_BAR_ENABLED);
        }

        public boolean isNavBarGestureType() {
            return GlobalActionsContentView.this.mConditionChecker.isEnabled(SystemConditions.IS_NAV_BAR_GESTURE_ENABLED);
        }

        private void setBugReportViewMargin() {
            if (GlobalActionsContentView.this.mBottomButtonView == null || GlobalActionsContentView.this.mBottomButtonView.getChildCount() <= 0) {
                return;
            }
            boolean z = this.mContext.getResources().getConfiguration().orientation == 1;
            FrameLayout frameLayout = (FrameLayout) GlobalActionsContentView.this.mBottomButtonView.findViewById(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.ID_BUGREPORT_VIEW));
            if (frameLayout != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) frameLayout.getLayoutParams();
                if (z) {
                    if (isTaskBarEnabled() && isNavBarGestureType()) {
                        marginLayoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_BUGREPORT_BOTTOM_MARGIN_TASK));
                    } else {
                        marginLayoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_BUGREPORT_BOTTOM_MARGIN));
                    }
                } else {
                    int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_BUGREPORT_BOTTOM_MARGIN_LAND));
                    if (GlobalActionsContentView.this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE) && GlobalActionsContentView.this.mConditionChecker.isEnabled(SystemConditions.IS_DESKTOP_MODE_STANDALONE)) {
                        dimensionPixelSize += this.mContext.getResources().getDimensionPixelSize(R.dimen.navigation_bar_frame_height);
                    }
                    marginLayoutParams.bottomMargin = dimensionPixelSize;
                }
                frameLayout.setLayoutParams(marginLayoutParams);
            }
        }

        private void setSideKeySettingsViewMargin() {
            if (GlobalActionsContentView.this.mBottomButtonView == null || GlobalActionsContentView.this.mBottomButtonView.getChildCount() <= 0) {
                return;
            }
            FrameLayout frameLayout = (FrameLayout) GlobalActionsContentView.this.mBottomButtonView.findViewById(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.ID_BUGREPORT_VIEW));
            FrameLayout frameLayout2 = (FrameLayout) GlobalActionsContentView.this.mBottomButtonView.findViewById(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.ID_SIDEKEY_SETTINGS_VIEW));
            boolean z = this.mContext.getResources().getConfiguration().orientation == 1;
            if (frameLayout2 != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) frameLayout2.getLayoutParams();
                if (z) {
                    if (frameLayout != null) {
                        marginLayoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_SIDEKEY_SETTINGS_BOTTOM_MARGIN_2BTNS));
                    } else {
                        marginLayoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_SIDEKEY_SETTINGS_BOTTOM_MARGIN_1BTN));
                    }
                    marginLayoutParams.setMarginEnd(0);
                } else {
                    int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_SIDEKEY_SETTINGS_BOTTOM_MARGIN_LAND));
                    if (GlobalActionsContentView.this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE) && GlobalActionsContentView.this.mConditionChecker.isEnabled(SystemConditions.IS_DESKTOP_MODE_STANDALONE)) {
                        dimensionPixelSize += this.mContext.getResources().getDimensionPixelSize(R.dimen.navigation_bar_frame_height);
                    }
                    if (isTaskBarEnabled() && isNavBarGestureType()) {
                        dimensionPixelSize += this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_BUGREPORT_BOTTOM_MARGIN_TASK));
                    }
                    marginLayoutParams.bottomMargin = dimensionPixelSize;
                    if (frameLayout != null) {
                        marginLayoutParams.setMarginEnd(this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_SIDEKEY_SETTINGS_RIGHT_MARGIN_LAND)));
                    }
                }
                frameLayout2.setLayoutParams(marginLayoutParams);
            }
        }

        private void updateBottomViewProperties() {
            if (GlobalActionsContentView.this.mBottomButtonView.getChildCount() > 0) {
                int i = this.mContext.getResources().getConfiguration().orientation != 1 ? 0 : 1;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) GlobalActionsContentView.this.mBottomButtonView.getLayoutParams();
                GlobalActionsContentView.this.mBottomButtonView.setOrientation(i);
                if (i == 0 && GlobalActionsContentView.this.mGridViewAdapter.getCount() < 4) {
                    marginLayoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DIMEN_BOTTOMBUTTONVIEW_BOTTOM_MARGIN_LAND));
                } else {
                    marginLayoutParams.bottomMargin = 0;
                }
                GlobalActionsContentView.this.mBottomButtonView.setLayoutParams(marginLayoutParams);
            }
            GlobalActionsContentView.this.mBottomButtonView.setGravity(16);
        }

        private void setListViewHeight() {
            int i;
            int i2;
            int verticalSpacing;
            int i3 = 0;
            boolean z = this.mContext.getResources().getConfiguration().orientation == 1;
            if (GlobalActionsContentView.this.mListView.getNumColumns() == 2) {
                i2 = 0;
                int i4 = 0;
                int i5 = 0;
                while (i3 < GlobalActionsContentView.this.mListView.getChildCount()) {
                    if (i3 % 2 == 0) {
                        i2 += GlobalActionsContentView.this.mListView.getChildAt(i3).getHeight();
                        i5++;
                    } else {
                        i4 += GlobalActionsContentView.this.mListView.getChildAt(i3).getHeight();
                    }
                    i3++;
                }
                if (i2 <= i4) {
                    i2 = i4;
                }
                verticalSpacing = GlobalActionsContentView.this.mListView.getVerticalSpacing() * (i5 - 1);
            } else if (z && GlobalActionsContentView.this.mListView.getNumColumns() == 1) {
                i2 = 0;
                int i6 = 0;
                while (i3 < GlobalActionsContentView.this.mListView.getChildCount()) {
                    i2 += GlobalActionsContentView.this.mListView.getChildAt(i3).getHeight();
                    i6++;
                    i3++;
                }
                verticalSpacing = GlobalActionsContentView.this.mListView.getVerticalSpacing() * (i6 - 1);
            } else {
                i = 0;
                int childCount = GlobalActionsContentView.this.mListView.getChildCount();
                if (i3 != 0 || childCount <= 0) {
                }
                int i7 = childCount - 1;
                if (GlobalActionsContentView.this.mListView.getChildAt(i7) != null) {
                    int bottom = GlobalActionsContentView.this.mListView.getChildAt(i7).getBottom();
                    if (bottom > i) {
                        i = bottom;
                    }
                    if (i < (GlobalActionsContentView.this.mRootView.getHeight() - GlobalActionsContentView.this.mRootView.getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) GlobalActionsContentView.this.mListView.getLayoutParams()).bottomMargin) {
                        ViewGroup.LayoutParams layoutParams = GlobalActionsContentView.this.mListView.getLayoutParams();
                        layoutParams.height = i;
                        GlobalActionsContentView.this.mListView.setLayoutParams(layoutParams);
                        return;
                    }
                    return;
                }
                return;
            }
            i = verticalSpacing + i2;
            i3 = 1;
            int childCount2 = GlobalActionsContentView.this.mListView.getChildCount();
            if (i3 != 0) {
            }
        }
    }

    public class SamsungGlobalActionsBackgroundView extends FrameLayout {
        public SamsungGlobalActionsBackgroundView(GlobalActionsContentView globalActionsContentView, Context context) {
            super(context);
            addView(View.inflate(context, globalActionsContentView.mResourceFactory.get(ResourceType.LAYOUT_BLUR_BACKGROUND), null));
        }
    }

    public class BaseContentAdapter extends BaseAdapter {
        Context mContext;
        List<ActionViewModel> mViewModelList = new ArrayList();

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        public BaseContentAdapter(Context context) {
            this.mContext = context;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mViewModelList.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return this.mViewModelList.get(i);
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            GlobalActionsContentItemView globalActionsContentItemView = new GlobalActionsContentItemView(this.mContext, this.mViewModelList.get(i), viewGroup, GlobalActionsContentView.this.mResourceFactory, GlobalActionsContentView.this.mIsVoiceAssistantMode, GlobalActionsContentView.this.mIsWhiteTheme, GlobalActionsContentView.this.mViewStateController);
            if (view == null) {
                view = globalActionsContentItemView.inflateView();
            }
            globalActionsContentItemView.setViewAttrs(view, false);
            globalActionsContentItemView.setViewIndex(i);
            return view;
        }

        public void addItem(ActionViewModel actionViewModel) {
            this.mViewModelList.add(actionViewModel);
        }

        public void resetItems() {
            this.mViewModelList.clear();
        }

        public void updateNumColumns() {
            int size = this.mViewModelList.size();
            if (size < 4) {
                GlobalActionsContentView.this.mListView.setNumColumns(1);
            } else {
                GlobalActionsContentView.this.mListView.setNumColumns(2);
            }
            if (size < 4) {
                GlobalActionsContentView.this.mLandListView.setNumColumns(size);
            } else if (size == 4) {
                GlobalActionsContentView.this.mLandListView.setNumColumns(2);
            } else {
                GlobalActionsContentView.this.mLandListView.setNumColumns(3);
            }
            setVerticalSpacing();
        }

        private void setVerticalSpacing() {
            if (GlobalActionsContentView.this.mConditionChecker.isEnabled(SystemConditions.IS_TABLET_DEVICE)) {
                return;
            }
            if (this.mViewModelList.size() == 4) {
                GlobalActionsContentView.this.mListView.setVerticalSpacing(this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_global_actions_items_vertical_spacing_port_only_4_buttons));
            } else {
                GlobalActionsContentView.this.mListView.setVerticalSpacing(GlobalActionsContentView.this.mItemVerticalSpacingPort);
            }
        }
    }

    public class SamsungGlobalActionsGridView extends GridView {
        boolean mIsVerticalMode;

        public SamsungGlobalActionsGridView(GlobalActionsContentView globalActionsContentView, Context context, boolean z) {
            super(context);
            this.mIsVerticalMode = z;
            setHorizontalSpacing(globalActionsContentView.mItemHorizontalSpacing);
            if (z) {
                setVerticalSpacing(globalActionsContentView.mItemVerticalSpacingPort);
            } else {
                setVerticalSpacing(globalActionsContentView.mItemVerticalSpacingLand);
                setColumnWidth(globalActionsContentView.mItemWidthLand);
            }
            setFocusable(false);
            setVerticalScrollBarEnabled(false);
        }

        public boolean isVerticalMode() {
            return this.mIsVerticalMode;
        }

        @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                return false;
            }
            return super.onTouchEvent(motionEvent);
        }
    }

    /* renamed from: com.samsung.android.globalactions.presentation.view.GlobalActionsContentView$3, reason: invalid class name */
    class AnonymousClass3 implements SamsungGlobalActionsAnimator.ViewUpdateCallback {
        AnonymousClass3() {
        }

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
        public ViewGroup getConfirmIconLabelView(ViewGroup viewGroup) {
            return (ViewGroup) viewGroup.findViewById(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.ID_ICON_LABEL));
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public View getConfirmDescriptionView(ViewGroup viewGroup) {
            return viewGroup.findViewById(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.ID_DESCRIPTION));
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public GlobalActionsContentItemView createConfirmView() {
            GlobalActionsContentItemView globalActionsContentItemView = new GlobalActionsContentItemView(GlobalActionsContentView.this.mContext, GlobalActionsContentView.this.mSelectedViewModel, GlobalActionsContentView.this.mConfirmationView, GlobalActionsContentView.this.mResourceFactory, GlobalActionsContentView.this.mIsVoiceAssistantMode, GlobalActionsContentView.this.mIsWhiteTheme, GlobalActionsContentView.this.mViewStateController);
            GlobalActionsContentView.this.mConfirmationView.removeAllViews();
            GlobalActionsContentView.this.mConfirmationView.addView(globalActionsContentItemView.createView(true));
            View findViewById = GlobalActionsContentView.this.mConfirmationView.findViewById(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.ID_STATE));
            GlobalActionsContentView.this.mConfirmationView.setVisibility(0);
            getConfirmDescriptionView(GlobalActionsContentView.this.mConfirmationView).setVisibility(0);
            findViewById.setVisibility(8);
            return globalActionsContentItemView;
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public ViewGroup getSelectedActionView(ViewGroup viewGroup) {
            return (ViewGroup) viewGroup.getChildAt(GlobalActionsContentView.this.mSelectedViewModel.getActionInfo().getViewIndex());
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public boolean isSafeModeConfirm() {
            return GlobalActionsContentView.this.mSelectedViewModel.getActionInfo().getName().equals(DefaultActionNames.ACTION_SAFE_MODE);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getDismissRunnable$0() {
            GlobalActionsContentView.this.mParentView.dismiss();
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public Runnable getDismissRunnable() {
            return new Runnable() { // from class: com.samsung.android.globalactions.presentation.view.GlobalActionsContentView$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalActionsContentView.AnonymousClass3.this.lambda$getDismissRunnable$0();
                }
            };
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public void requestFocusFor(ViewGroup viewGroup, ViewGroup viewGroup2) {
            View findViewById = viewGroup.findViewById(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.ID_ICON));
            View findViewById2 = viewGroup2.findViewById(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.ID_ICON));
            if (GlobalActionsContentView.this.mIsVoiceAssistantMode && findViewById2 != null) {
                if (findViewById2.isAccessibilityFocused()) {
                    findViewById.performAccessibilityAction(64, null);
                    findViewById.requestFocus();
                    return;
                } else {
                    GlobalActionsContentView.this.mRootView.setDescendantFocusability(393216);
                    return;
                }
            }
            if (GlobalActionsContentView.this.mIsVoiceAssistantMode) {
                return;
            }
            findViewById.requestFocus();
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public ViewGroup getPowerOffViewForSafeModeVI(GlobalActionsContentItemView globalActionsContentItemView) {
            ViewGroup viewGroup = (ViewGroup) globalActionsContentItemView.createView(false);
            ImageView imageView = (ImageView) viewGroup.findViewById(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.ID_ICON));
            ((TextView) viewGroup.findViewById(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.ID_LABEL))).lambda$setTextAsync$0(GlobalActionsContentView.this.mContext.getResources().getText(R.string.samsung_global_action_power_off));
            imageView.setImageDrawable(GlobalActionsContentView.this.mContext.getResources().getDrawable(GlobalActionsContentView.this.mResourceFactory.get(ResourceType.DRAWABLE_POWEROFF), null));
            ViewGroup confirmIconLabelView = getConfirmIconLabelView(viewGroup);
            ((ViewGroup) confirmIconLabelView.getParent()).removeAllViews();
            GlobalActionsContentView.this.mConfirmationView.addView(confirmIconLabelView);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) confirmIconLabelView.getLayoutParams();
            layoutParams.gravity = 17;
            confirmIconLabelView.setLayoutParams(layoutParams);
            confirmIconLabelView.setVisibility(0);
            return confirmIconLabelView;
        }

        @Override // com.samsung.android.globalactions.presentation.view.SamsungGlobalActionsAnimator.ViewUpdateCallback
        public void setFlagsForForceDismiss(boolean z) {
            GlobalActionsContentView.this.mParentView.setCoverSecureConfirmState(z);
            GlobalActionsContentView.this.mForceDismiss = z;
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
