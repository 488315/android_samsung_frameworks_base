package com.android.systemui.globalactions.presentation.view;

import android.app.Presentation;
import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import com.android.systemui.R;
import com.android.systemui.globalactions.presentation.view.CoverViewAnimatorFSM;
import com.android.systemui.globalactions.util.SystemUIConditions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter;
import com.samsung.android.globalactions.presentation.view.ContentView;
import com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView;
import com.samsung.android.globalactions.presentation.view.ResourceFactory;
import com.samsung.android.globalactions.presentation.view.ResourceType;
import com.samsung.android.globalactions.presentation.view.ViewAnimationState;
import com.samsung.android.globalactions.presentation.view.ViewStateController;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.HandlerUtil;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.ToastController;
import com.samsung.android.view.SemWindowManager;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FrontCoverContentView implements ContentView, ViewStateController {
    public ContentAdapter mAdapter;
    public CoverViewAnimatorFSM mAnimatorFSM;
    public ImageView mBackButton;
    public final ConditionChecker mConditionChecker;
    public ViewGroup mConfirmView;
    public final Context mContext;
    public final Presentation mDialog;
    public C14211 mFoldStateListener;
    public boolean mForceDismiss;
    public final HandlerUtil mHandler;
    public final boolean mIsCameraViewCover;
    public final boolean mIsIconOnly;
    public ContentGridView mListView;
    public final LogWrapper mLogWrapper;
    public final ExtendableGlobalActionsView mParentView;
    public final SamsungGlobalActionsPresenter mPresenter;
    public final ResourceFactory mResourceFactory;
    public RootView mRootView;
    public ActionViewModel mSelectedViewModel;
    public final C14222 mFrontCoverAnimatorCallback = new C14222();
    public ViewAnimationState mState = ViewAnimationState.IDLE;
    public boolean mLastFoldedState = true;
    public boolean mIsSecureConfirming = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.globalactions.presentation.view.FrontCoverContentView$2 */
    public final class C14222 {
        public C14222() {
        }

        public final ViewGroup getConfirmIconLabelView(ViewGroup viewGroup) {
            return (ViewGroup) viewGroup.findViewById(FrontCoverContentView.this.mResourceFactory.get(ResourceType.ID_COVER_BTN_BACKGROUND));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ContentAdapter extends BaseAdapter {
        public View mLastAnimatedView;
        public final List mViewModelList = new ArrayList();
        public final List mTempViewModelList = new ArrayList();
        public boolean mIsConfirmView = false;

        public ContentAdapter() {
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return ((ArrayList) this.mViewModelList).size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return ((ArrayList) this.mViewModelList).get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            Context context = FrontCoverContentView.this.mDialog.getContext();
            ActionViewModel actionViewModel = (ActionViewModel) ((ArrayList) this.mViewModelList).get(i);
            FrontCoverContentView frontCoverContentView = FrontCoverContentView.this;
            FrontCoverContentItemView frontCoverContentItemView = new FrontCoverContentItemView(context, actionViewModel, viewGroup, frontCoverContentView.mIsIconOnly, frontCoverContentView.mIsCameraViewCover, frontCoverContentView.mResourceFactory);
            View inflate = LayoutInflater.from(frontCoverContentItemView.mContext).inflate(frontCoverContentItemView.mResourceFactory.get(ResourceType.LAYOUT_FRONT_COVER_ITEM), frontCoverContentItemView.mParent, false);
            frontCoverContentItemView.setViewAttrs(inflate, this.mIsConfirmView);
            frontCoverContentItemView.mViewModel.getActionInfo().setViewIndex(i);
            ActionViewModel actionViewModel2 = FrontCoverContentView.this.mSelectedViewModel;
            if (actionViewModel2 != null && actionViewModel2.getActionInfo().getStateLabel().equals("confirm_dismiss") && ((ActionViewModel) ((ArrayList) this.mViewModelList).get(i)).getActionInfo().getName() == FrontCoverContentView.this.mSelectedViewModel.getActionInfo().getName()) {
                this.mLastAnimatedView = inflate;
                View view2 = FrontCoverContentView.this.mAdapter.mLastAnimatedView;
                if (view2 != null) {
                    view2.setVisibility(8);
                }
            }
            return inflate;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ContentGridView extends GridView {
        public ContentGridView(FrontCoverContentView frontCoverContentView, Context context) {
            super(context);
            setFocusable(false);
            setVerticalScrollBarEnabled(false);
        }

        @Override // android.widget.AbsListView, android.view.View
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                return false;
            }
            return super.onTouchEvent(motionEvent);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RootView extends FrameLayout {
        public RootView(Context context) {
            super(context);
            addView(View.inflate(context, FrontCoverContentView.this.mResourceFactory.get(ResourceType.LAYOUT_FRONT_COVER_VIEW), null));
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void onAttachedToWindow() {
            super.onAttachedToWindow();
            FrontCoverContentView.this.mAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.SHOW);
        }

        @Override // android.view.View
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return true;
            }
            FrontCoverContentView.this.mPresenter.onCancelDialog();
            return true;
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.globalactions.presentation.view.FrontCoverContentView$1] */
    public FrontCoverContentView(Context context, ExtendableGlobalActionsView extendableGlobalActionsView, ConditionChecker conditionChecker, ResourceFactory resourceFactory, LogWrapper logWrapper, HandlerUtil handlerUtil, ToastController toastController, SamsungGlobalActionsPresenter samsungGlobalActionsPresenter) {
        this.mContext = context;
        this.mParentView = extendableGlobalActionsView;
        this.mConditionChecker = conditionChecker;
        this.mResourceFactory = resourceFactory;
        this.mLogWrapper = logWrapper;
        this.mHandler = handlerUtil;
        this.mPresenter = samsungGlobalActionsPresenter;
        boolean isEnabled = conditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_CAMERA_VIEW_COVER_CLOSED);
        this.mIsCameraViewCover = isEnabled;
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        if (isEnabled) {
            Display[] displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
            Display display = displays.length > 0 ? displays[0] : null;
            if (display != null) {
                this.mDialog = new Presentation(context, display);
                return;
            }
            return;
        }
        Display[] displays2 = displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        if (displays2.length > 1) {
            this.mDialog = new Presentation(context, displays2[1]);
            Point point = new Point();
            displays2[1].getRealSize(point);
            if (point.x < 512) {
                this.mIsIconOnly = true;
            }
        } else {
            logWrapper.e("FrontCoverContentView", "Failed to get front display. The length of array is : " + displays2.length);
        }
        this.mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.android.systemui.globalactions.presentation.view.FrontCoverContentView.1
            public final void onFoldStateChanged(boolean z) {
                FrontCoverContentView frontCoverContentView = FrontCoverContentView.this;
                frontCoverContentView.mLastFoldedState = z;
                if (z || frontCoverContentView.mIsSecureConfirming) {
                    return;
                }
                frontCoverContentView.mPresenter.onDismiss();
                FrontCoverContentView.this.mParentView.dismiss();
                FrontCoverContentView.this.mDialog.dismiss();
            }

            public final void onTableModeChanged(boolean z) {
            }
        };
        SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
    }

    public final void dismiss() {
        CoverViewAnimatorFSM coverViewAnimatorFSM = this.mAnimatorFSM;
        if (coverViewAnimatorFSM != null) {
            coverViewAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.HIDE);
        }
    }

    public final ViewAnimationState getAnimationState() {
        return this.mState;
    }

    public final ViewAnimationState getState() {
        return this.mState;
    }

    public final void hideConfirm() {
        this.mAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.HIDE_CONFIRM);
    }

    public final void hideDialogOnSecureConfirm() {
        this.mIsSecureConfirming = true;
        this.mAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.SECURE_CONFIRM);
    }

    public final void initAnimations() {
        FrontCoverViewAnimator frontCoverViewAnimator = new FrontCoverViewAnimator(this.mDialog.getContext(), this.mConditionChecker, this.mLogWrapper, this.mHandler, this.mResourceFactory, this);
        C14222 c14222 = this.mFrontCoverAnimatorCallback;
        frontCoverViewAnimator.mCallback = c14222;
        frontCoverViewAnimator.mRootView = FrontCoverContentView.this.mRootView;
        this.mAnimatorFSM = new CoverViewAnimatorFSM(frontCoverViewAnimator, this.mLogWrapper, this);
    }

    public final void initLayouts() {
        RootView rootView = new RootView(this.mDialog.getContext());
        this.mRootView = rootView;
        ViewGroup viewGroup = (ViewGroup) rootView.findViewById(this.mResourceFactory.get(ResourceType.ID_FRONT_COVER_ITEM));
        ContentGridView contentGridView = new ContentGridView(this, this.mDialog.getContext());
        this.mListView = contentGridView;
        viewGroup.addView(contentGridView);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(this.mResourceFactory.get(ResourceType.DIMEN_MINI_SVIEW_COVER_SIDE_MARGIN));
        if (this.mIsCameraViewCover) {
            this.mListView.setVerticalSpacing(dimensionPixelSize);
        }
        ContentAdapter contentAdapter = new ContentAdapter();
        this.mAdapter = contentAdapter;
        this.mListView.setAdapter((ListAdapter) contentAdapter);
        this.mListView.setNumColumns(this.mIsCameraViewCover ? 1 : 2);
        this.mListView.setGravity(17);
        this.mConfirmView = (ViewGroup) this.mRootView.findViewById(this.mResourceFactory.get(ResourceType.ID_FRONT_COVER_COFIRM));
        ImageView imageView = (ImageView) this.mRootView.findViewById(R.id.front_cover_back_button);
        this.mBackButton = imageView;
        if (this.mIsIconOnly || this.mIsCameraViewCover) {
            imageView.setVisibility(8);
            viewGroup.getLayoutParams().width = -1;
        } else {
            imageView.setClickable(true);
            this.mBackButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.presentation.view.FrontCoverContentView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FrontCoverContentView.this.mPresenter.onCancelDialog();
                }
            });
        }
        this.mDialog.setContentView(this.mRootView);
        Window window = this.mDialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.semSetScreenTimeout(this.mIsCameraViewCover ? 6000L : 5000L);
        attributes.semSetScreenDimDuration(0L);
        attributes.setTitle(this.mContext.getResources().getString(android.R.string.mediasize_iso_b2));
        window.setAttributes(attributes);
    }

    public final void onDismiss() {
        Presentation presentation = this.mDialog;
        if (presentation != null) {
            presentation.dismiss();
        }
        if (this.mFoldStateListener != null) {
            SemWindowManager.getInstance().unregisterFoldStateListener(this.mFoldStateListener);
        }
    }

    public final void setAnimationState(ViewAnimationState viewAnimationState) {
        this.mState = viewAnimationState;
    }

    public final void setState(ViewAnimationState viewAnimationState) {
        this.mState = viewAnimationState;
    }

    public final void show() {
        if (this.mDialog == null) {
            this.mLogWrapper.e("FrontCoverContentView", "Failed to show front display dialog - the dialog is null.");
        } else {
            this.mLogWrapper.i("FrontCoverContentView", "show FrontCoverContentView");
            this.mDialog.show();
        }
    }

    public final void showConfirm(ActionViewModel actionViewModel) {
        this.mSelectedViewModel = actionViewModel;
        this.mAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.SHOW_CONFIRM);
    }

    public final void updateItemLists(SamsungGlobalActionsPresenter samsungGlobalActionsPresenter) {
        for (ActionViewModel actionViewModel : samsungGlobalActionsPresenter.getValidActions()) {
            if (actionViewModel.getActionInfo().getName() == "power" || actionViewModel.getActionInfo().getName() == "restart") {
                ((ArrayList) this.mAdapter.mViewModelList).add(actionViewModel);
            }
        }
        ContentAdapter contentAdapter = this.mAdapter;
        ((ArrayList) contentAdapter.mTempViewModelList).clear();
        ((ArrayList) contentAdapter.mTempViewModelList).addAll(contentAdapter.mViewModelList);
        this.mAdapter.notifyDataSetChanged();
    }

    public final void forceRequestLayout() {
    }

    public final void initDimens() {
    }
}
