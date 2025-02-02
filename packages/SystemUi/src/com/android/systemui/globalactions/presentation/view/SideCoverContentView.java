package com.android.systemui.globalactions.presentation.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
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
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SideCoverContentView implements ContentView, ViewStateController {
    public final ConditionChecker mConditionChecker;
    public ViewGroup mConfirmView;
    public final Context mContext;
    public final CoverUtilWrapper mCoverUtilWrapper;
    public final Dialog mDialog;
    public boolean mForceDismiss;
    public SideCoverContentAdapter mGridViewAdapter;
    public final HandlerUtil mHandlerUtil;
    public SideCoverContentGridView mListView;
    public final LogWrapper mLogWrapper;
    public final ExtendableGlobalActionsView mParentView;
    public final ResourceFactory mResourceFactory;
    public final Resources mResources;
    public SideCoverContentRootView mRootView;
    public ActionViewModel mSelectedViewModel;
    public final ToastController mToastController;
    public CoverViewAnimatorFSM mViewAnimatorFSM;
    public Rect mVisibleRect;
    public final C14351 mSideCoverAnimatorCallback = new C14351();
    public ViewAnimationState mViewAnimationState = ViewAnimationState.IDLE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.globalactions.presentation.view.SideCoverContentView$1 */
    public final class C14351 {
        public String toastMessage;

        public C14351() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SideCoverContentAdapter extends ArrayAdapter {
        public View mLastAnimatedView;
        public final List mTempViewModelList;
        public final List mViewModelList;

        public SideCoverContentAdapter(Context context, int i, ArrayList<ActionViewModel> arrayList) {
            super(context, i, arrayList);
            this.mViewModelList = new ArrayList();
            this.mTempViewModelList = new ArrayList();
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final int getCount() {
            return ((ArrayList) this.mViewModelList).size();
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final Object getItem(int i) {
            return ((ArrayList) this.mViewModelList).get(i);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            SideCoverContentItemView sideCoverContentItemView = new SideCoverContentItemView(SideCoverContentView.this.mContext, (ActionViewModel) ((ArrayList) this.mViewModelList).get(i), viewGroup, SideCoverContentView.this.mResourceFactory, false, false);
            View inflateView = sideCoverContentItemView.inflateView();
            sideCoverContentItemView.setViewAttrs(inflateView);
            sideCoverContentItemView.mViewModel.getActionInfo().setViewIndex(i);
            ActionViewModel actionViewModel = SideCoverContentView.this.mSelectedViewModel;
            if (actionViewModel != null && actionViewModel.getActionInfo().getStateLabel().equals("confirm_dismiss") && ((ActionViewModel) ((ArrayList) this.mViewModelList).get(i)).getActionInfo().getName() == SideCoverContentView.this.mSelectedViewModel.getActionInfo().getName()) {
                this.mLastAnimatedView = inflateView;
                View view2 = SideCoverContentView.this.mGridViewAdapter.mLastAnimatedView;
                if (view2 != null) {
                    view2.setVisibility(8);
                }
            }
            return inflateView;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SideCoverContentGridView extends GridView {
        public long mLastTime;
        public float mLastX;
        public float mLastY;

        public SideCoverContentGridView(Context context, boolean z) {
            super(context);
            this.mLastX = 0.0f;
            this.mLastY = 0.0f;
            this.mLastTime = 0L;
            setFocusable(false);
            setVerticalScrollBarEnabled(false);
        }

        @Override // android.view.ViewGroup, android.view.View
        public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                this.mLastX = motionEvent.getX();
                this.mLastY = motionEvent.getY();
                this.mLastTime = System.currentTimeMillis();
            }
            if (motionEvent.getAction() == 2) {
                return true;
            }
            if (motionEvent.getAction() == 1) {
                long currentTimeMillis = System.currentTimeMillis() - this.mLastTime;
                if (Math.abs(motionEvent.getX() - this.mLastX) > 250.0f || Math.abs(motionEvent.getY() - this.mLastY) > 200.0f || currentTimeMillis > 500) {
                    SideCoverContentView.this.mLogWrapper.i("SideCoverContentView", "button click canceled, diff : " + currentTimeMillis);
                    return false;
                }
            }
            return super.dispatchTouchEvent(motionEvent);
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
    public final class SideCoverContentRootView extends FrameLayout {
        public SideCoverContentRootView(Context context) {
            super(context);
            addView(View.inflate(context, SideCoverContentView.this.mResourceFactory.get(ResourceType.LAYOUT_SIDE_COVER_ROOT_VIEW), null));
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void onAttachedToWindow() {
            super.onAttachedToWindow();
            SideCoverContentView.this.mViewAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.SHOW);
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
        }

        @Override // android.view.View
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return true;
            }
            SideCoverContentView.this.mDialog.cancel();
            return true;
        }
    }

    public SideCoverContentView(Context context, ExtendableGlobalActionsView extendableGlobalActionsView, ConditionChecker conditionChecker, ResourceFactory resourceFactory, CoverUtilWrapper coverUtilWrapper, LogWrapper logWrapper, HandlerUtil handlerUtil, ToastController toastController, Dialog dialog) {
        this.mContext = context;
        this.mParentView = extendableGlobalActionsView;
        this.mConditionChecker = conditionChecker;
        this.mResourceFactory = resourceFactory;
        this.mCoverUtilWrapper = coverUtilWrapper;
        this.mLogWrapper = logWrapper;
        this.mHandlerUtil = handlerUtil;
        this.mToastController = toastController;
        this.mDialog = dialog;
        this.mResources = context.getResources();
    }

    public final void dismiss() {
        CoverViewAnimatorFSM coverViewAnimatorFSM = this.mViewAnimatorFSM;
        if (coverViewAnimatorFSM != null) {
            coverViewAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.HIDE);
        }
    }

    public final ViewAnimationState getAnimationState() {
        return this.mViewAnimationState;
    }

    public final ViewAnimationState getState() {
        return this.mViewAnimationState;
    }

    public final void hideConfirm() {
        this.mViewAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.HIDE_CONFIRM);
    }

    public final void hideDialogOnSecureConfirm() {
        this.mViewAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.SECURE_CONFIRM);
    }

    public final void initAnimations() {
        SideCoverViewAnimator sideCoverViewAnimator = new SideCoverViewAnimator(this.mContext, this.mResourceFactory, this.mConditionChecker, this.mLogWrapper, this.mHandlerUtil, this);
        C14351 c14351 = this.mSideCoverAnimatorCallback;
        sideCoverViewAnimator.mCallback = c14351;
        SideCoverContentRootView sideCoverContentRootView = SideCoverContentView.this.mRootView;
        this.mViewAnimatorFSM = new CoverViewAnimatorFSM(sideCoverViewAnimator, this.mLogWrapper, this);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void initLayouts() {
        float f;
        float f2;
        CoverUtilWrapper coverUtilWrapper = this.mCoverUtilWrapper;
        coverUtilWrapper.getClass();
        Rect rect = new Rect();
        CoverState coverState = coverUtilWrapper.mCoverState;
        if (coverState != null) {
            rect = coverState.getVisibleRect();
        }
        this.mVisibleRect = rect;
        SideCoverContentRootView sideCoverContentRootView = new SideCoverContentRootView(this.mContext);
        this.mRootView = sideCoverContentRootView;
        ViewGroup viewGroup = (ViewGroup) sideCoverContentRootView.findViewById(this.mResourceFactory.get(ResourceType.ID_SIDE_COVER_ITEM_LIST));
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
        layoutParams.width = this.mVisibleRect.height();
        layoutParams.height = this.mVisibleRect.width();
        viewGroup.setLayoutParams(layoutParams);
        SideCoverContentGridView sideCoverContentGridView = new SideCoverContentGridView(this.mContext, true);
        this.mListView = sideCoverContentGridView;
        viewGroup.addView(sideCoverContentGridView);
        SideCoverContentAdapter sideCoverContentAdapter = new SideCoverContentAdapter(this.mContext, this.mResourceFactory.get(ResourceType.ID_SIDE_COVER_ITEM_LIST), new ArrayList());
        this.mGridViewAdapter = sideCoverContentAdapter;
        this.mListView.setAdapter((ListAdapter) sideCoverContentAdapter);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mListView.getLayoutParams();
        DisplayMetrics displayMetrics = this.mResources.getDisplayMetrics();
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(this.mResourceFactory.get(ResourceType.DIMEN_SIDE_COVER_WIDTH));
        int i = displayMetrics.widthPixels;
        int i2 = i == 720 ? 320 : i == 1080 ? VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE : 640;
        int i3 = displayMetrics.densityDpi;
        if (i3 >= i2) {
            if (i3 > i2) {
                f = dimensionPixelSize;
                f2 = 0.889f;
            }
            layoutParams2.width = dimensionPixelSize;
            this.mListView.setNumColumns(2);
            this.mListView.setGravity(17);
            this.mListView.setLayoutParams(layoutParams2);
            ViewGroup viewGroup2 = (ViewGroup) this.mRootView.findViewById(this.mResourceFactory.get(ResourceType.ID_SIDE_COVER_CONFIRM));
            this.mConfirmView = viewGroup2;
            ViewGroup.LayoutParams layoutParams3 = viewGroup2.getLayoutParams();
            layoutParams3.width = this.mVisibleRect.height();
            layoutParams3.height = this.mVisibleRect.width();
            this.mConfirmView.setLayoutParams(layoutParams3);
            if (this.mContext.getResources().getConfiguration().orientation == 1) {
                viewGroup.setX(this.mVisibleRect.right);
                viewGroup.setY(this.mVisibleRect.top);
                viewGroup.setPivotX(0.0f);
                viewGroup.setPivotY(0.0f);
                viewGroup.setRotation(90.0f);
                this.mConfirmView.setPivotX(0.0f);
                this.mConfirmView.setPivotY(0.0f);
                this.mConfirmView.setRotation(90.0f);
                this.mConfirmView.setX(this.mVisibleRect.right);
                this.mConfirmView.setY(this.mVisibleRect.top);
            }
            this.mDialog.setContentView(this.mRootView);
        }
        f = dimensionPixelSize;
        f2 = 1.143f;
        dimensionPixelSize = (int) (f * f2);
        layoutParams2.width = dimensionPixelSize;
        this.mListView.setNumColumns(2);
        this.mListView.setGravity(17);
        this.mListView.setLayoutParams(layoutParams2);
        ViewGroup viewGroup22 = (ViewGroup) this.mRootView.findViewById(this.mResourceFactory.get(ResourceType.ID_SIDE_COVER_CONFIRM));
        this.mConfirmView = viewGroup22;
        ViewGroup.LayoutParams layoutParams32 = viewGroup22.getLayoutParams();
        layoutParams32.width = this.mVisibleRect.height();
        layoutParams32.height = this.mVisibleRect.width();
        this.mConfirmView.setLayoutParams(layoutParams32);
        if (this.mContext.getResources().getConfiguration().orientation == 1) {
        }
        this.mDialog.setContentView(this.mRootView);
    }

    public final void notifyDataSetChanged() {
        this.mGridViewAdapter.notifyDataSetChanged();
    }

    public final void setAnimationState(ViewAnimationState viewAnimationState) {
        this.mViewAnimationState = viewAnimationState;
    }

    public final void setInterceptor() {
        this.mToastController.setInterceptor(new Consumer() { // from class: com.android.systemui.globalactions.presentation.view.SideCoverContentView$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SideCoverContentView sideCoverContentView = SideCoverContentView.this;
                sideCoverContentView.getClass();
                sideCoverContentView.mSelectedViewModel = (ActionViewModel) sideCoverContentView.mGridViewAdapter.getItem(0);
                sideCoverContentView.mSideCoverAnimatorCallback.toastMessage = (String) obj;
                sideCoverContentView.mViewAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.COVER_TOAST);
            }
        });
    }

    public final void setState(ViewAnimationState viewAnimationState) {
        this.mViewAnimationState = viewAnimationState;
    }

    public final void show() {
        this.mDialog.show();
    }

    public final void showConfirm(ActionViewModel actionViewModel) {
        this.mSelectedViewModel = actionViewModel;
        this.mViewAnimatorFSM.handleAnimationEvent(CoverViewAnimatorFSM.Event.SHOW_CONFIRM);
    }

    public final void updateItemLists(SamsungGlobalActionsPresenter samsungGlobalActionsPresenter) {
        List<ActionViewModel> validActions = samsungGlobalActionsPresenter.getValidActions();
        if (Settings.System.getIntForUser(this.mContext.getContentResolver(), "cover_text_direction", 0, -2) != 0) {
            Collections.reverse(validActions);
        }
        for (ActionViewModel actionViewModel : validActions) {
            if (actionViewModel.getActionInfo().getName() == "power" || actionViewModel.getActionInfo().getName() == "restart") {
                ((ArrayList) this.mGridViewAdapter.mViewModelList).add(actionViewModel);
            }
        }
        SideCoverContentAdapter sideCoverContentAdapter = this.mGridViewAdapter;
        ((ArrayList) sideCoverContentAdapter.mTempViewModelList).clear();
        ((ArrayList) sideCoverContentAdapter.mTempViewModelList).addAll(sideCoverContentAdapter.mViewModelList);
        this.mGridViewAdapter.notifyDataSetChanged();
    }

    public final void forceRequestLayout() {
    }

    public final void initDimens() {
    }
}
