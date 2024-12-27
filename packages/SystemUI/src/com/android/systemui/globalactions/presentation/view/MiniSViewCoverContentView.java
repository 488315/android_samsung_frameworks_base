package com.android.systemui.globalactions.presentation.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListAdapter;
import com.android.systemui.basic.util.CoverUtilWrapper;
import com.android.systemui.globalactions.presentation.view.CoverViewAnimatorFSM;
import com.samsung.android.cover.CoverState;
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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class MiniSViewCoverContentView implements ContentView, ViewStateController {
    public ContentAdapter mAdapter;
    public CoverViewAnimatorFSM mAnimatorFSM;
    public final ConditionChecker mConditionChecker;
    public ViewGroup mConfirmView;
    public final Context mContext;
    public final CoverUtilWrapper mCoverUtilWrapper;
    public final Dialog mDialog;
    public boolean mForceDismiss;
    public final HandlerUtil mHandler;
    public ContentGridView mListView;
    public final LogWrapper mLogWrapper;
    public final AnonymousClass1 mMiniSViewCoverAnimatorCallback = new AnonymousClass1();
    public final ExtendableGlobalActionsView mParentView;
    public final ResourceFactory mResourceFactory;
    public RootView mRootView;
    public ActionViewModel mSelectedViewModel;
    public ViewAnimationState mState;
    public final ToastController mToastController;
    public Rect mVisibleRect;

    /* renamed from: com.android.systemui.globalactions.presentation.view.MiniSViewCoverContentView$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public String toastMessage;

        public AnonymousClass1() {
        }
    }

    public final class ContentAdapter extends BaseAdapter {
        public View mLastAnimatedView;
        public final List mViewModelList = new ArrayList();
        public final List mTempViewModelList = new ArrayList();

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
            MiniSViewCoverContentItemView miniSViewCoverContentItemView = new MiniSViewCoverContentItemView(MiniSViewCoverContentView.this.mContext, (ActionViewModel) ((ArrayList) this.mViewModelList).get(i), viewGroup, MiniSViewCoverContentView.this.mResourceFactory);
            View inflateView = miniSViewCoverContentItemView.inflateView();
            miniSViewCoverContentItemView.setViewAttrs(inflateView);
            miniSViewCoverContentItemView.mViewModel.getActionInfo().setViewIndex(i);
            ActionViewModel actionViewModel = MiniSViewCoverContentView.this.mSelectedViewModel;
            if (actionViewModel != null && actionViewModel.getActionInfo().getStateLabel().equals("confirm_dismiss") && ((ActionViewModel) ((ArrayList) this.mViewModelList).get(i)).getActionInfo().getName().equals(MiniSViewCoverContentView.this.mSelectedViewModel.getActionInfo().getName())) {
                this.mLastAnimatedView = inflateView;
                View view2 = MiniSViewCoverContentView.this.mAdapter.mLastAnimatedView;
                if (view2 != null) {
                    view2.setVisibility(8);
                }
            }
            return inflateView;
        }
    }

    public final class ContentGridView extends GridView {
        public ContentGridView(Context context) {
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

    public final class RootView extends FrameLayout {
        public RootView(Context context) {
            super(context);
            addView(View.inflate(context, MiniSViewCoverContentView.this.mResourceFactory.get(ResourceType.LAYOUT_MINI_SVIEW_COVER_VIEW), null));
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void onAttachedToWindow() {
            super.onAttachedToWindow();
            MiniSViewCoverContentView.this.mAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.SHOW);
        }

        @Override // android.view.View
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return true;
            }
            MiniSViewCoverContentView.this.mDialog.cancel();
            return true;
        }
    }

    public MiniSViewCoverContentView(Context context, ExtendableGlobalActionsView extendableGlobalActionsView, ConditionChecker conditionChecker, ResourceFactory resourceFactory, CoverUtilWrapper coverUtilWrapper, LogWrapper logWrapper, HandlerUtil handlerUtil, ToastController toastController, Dialog dialog) {
        this.mContext = context;
        this.mParentView = extendableGlobalActionsView;
        this.mConditionChecker = conditionChecker;
        this.mResourceFactory = resourceFactory;
        this.mCoverUtilWrapper = coverUtilWrapper;
        this.mLogWrapper = logWrapper;
        this.mHandler = handlerUtil;
        this.mToastController = toastController;
        this.mDialog = dialog;
        context.getResources();
        this.mState = ViewAnimationState.IDLE;
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
        this.mAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.SECURE_CONFIRM);
    }

    public final void initAnimations() {
        MiniSViewCoverViewAnimator miniSViewCoverViewAnimator = new MiniSViewCoverViewAnimator(this.mContext, this.mConditionChecker, this.mLogWrapper, this.mHandler, this);
        miniSViewCoverViewAnimator.mCallback = this.mMiniSViewCoverAnimatorCallback;
        this.mAnimatorFSM = new CoverViewAnimatorFSM(miniSViewCoverViewAnimator, this.mLogWrapper, this);
    }

    public final void initLayouts() {
        CoverUtilWrapper coverUtilWrapper = this.mCoverUtilWrapper;
        coverUtilWrapper.getClass();
        Rect rect = new Rect();
        CoverState coverState = coverUtilWrapper.mCoverState;
        if (coverState != null) {
            rect = coverState.getVisibleRect();
        }
        this.mVisibleRect = rect;
        RootView rootView = new RootView(this.mContext);
        this.mRootView = rootView;
        ViewGroup viewGroup = (ViewGroup) rootView.findViewById(this.mResourceFactory.get(ResourceType.ID_MINI_SVIEW_COVER_ITEM));
        ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
        layoutParams.width = this.mVisibleRect.width();
        layoutParams.height = this.mVisibleRect.height();
        viewGroup.setLayoutParams(layoutParams);
        viewGroup.setX(this.mVisibleRect.left);
        viewGroup.setY(this.mVisibleRect.top);
        ContentGridView contentGridView = new ContentGridView(this.mContext);
        this.mListView = contentGridView;
        viewGroup.addView(contentGridView);
        ContentAdapter contentAdapter = new ContentAdapter();
        this.mAdapter = contentAdapter;
        this.mListView.setAdapter((ListAdapter) contentAdapter);
        this.mListView.setNumColumns(2);
        this.mListView.setGravity(17);
        ViewGroup viewGroup2 = (ViewGroup) this.mRootView.findViewById(this.mResourceFactory.get(ResourceType.ID_MINI_SVIEW_COVER_CONFIRM));
        this.mConfirmView = viewGroup2;
        ViewGroup.LayoutParams layoutParams2 = viewGroup2.getLayoutParams();
        layoutParams2.width = this.mVisibleRect.width();
        layoutParams2.height = this.mVisibleRect.height();
        this.mConfirmView.setLayoutParams(layoutParams2);
        this.mConfirmView.setX(this.mVisibleRect.left);
        this.mConfirmView.setY(this.mVisibleRect.top);
        this.mDialog.setContentView(this.mRootView);
    }

    public final void setAnimationState(ViewAnimationState viewAnimationState) {
        this.mState = viewAnimationState;
    }

    public final void setInterceptor() {
        this.mToastController.setInterceptor(new Consumer() { // from class: com.android.systemui.globalactions.presentation.view.MiniSViewCoverContentView$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MiniSViewCoverContentView miniSViewCoverContentView = MiniSViewCoverContentView.this;
                miniSViewCoverContentView.getClass();
                miniSViewCoverContentView.mSelectedViewModel = (ActionViewModel) miniSViewCoverContentView.mAdapter.getItem(0);
                miniSViewCoverContentView.mMiniSViewCoverAnimatorCallback.toastMessage = (String) obj;
                miniSViewCoverContentView.mAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.COVER_TOAST);
            }
        });
    }

    public final void setState(ViewAnimationState viewAnimationState) {
        this.mState = viewAnimationState;
    }

    public final void show() {
        this.mDialog.show();
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
