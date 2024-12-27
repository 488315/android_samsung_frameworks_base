package com.android.systemui.globalactions.presentation.view;

import android.app.Dialog;
import android.content.Context;
import com.android.systemui.basic.util.CoverUtilWrapper;
import com.android.systemui.globalactions.util.SystemUIConditions;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.presentation.view.ContentView;
import com.samsung.android.globalactions.presentation.view.ContentViewFactoryBase;
import com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView;
import com.samsung.android.globalactions.presentation.view.GlobalActionsContentView;
import com.samsung.android.globalactions.presentation.view.ResourceFactory;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.HandlerUtil;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.ToastController;
import com.samsung.android.globalactions.util.WindowManagerUtils;

public final class ContentViewFactory implements ContentViewFactoryBase {
    public final ConditionChecker mConditionChecker;
    public final Context mContext;
    public final CoverUtilWrapper mCoverUtilWrapper;
    public final FeatureFactory mFeatureFactory;
    public final HandlerUtil mHandlerUtil;
    public final LogWrapper mLogWrapper;
    public final ExtendableGlobalActionsView mParentView;
    public final SamsungGlobalActionsPresenter mPresenter;
    public final ResourceFactory mResourceFactory;
    public final ToastController mToastController;
    public final WindowManagerUtils mWindowManagerUtil;

    public ContentViewFactory(Context context, ExtendableGlobalActionsView extendableGlobalActionsView, FeatureFactory featureFactory, ConditionChecker conditionChecker, WindowManagerUtils windowManagerUtils, ResourceFactory resourceFactory, CoverUtilWrapper coverUtilWrapper, LogWrapper logWrapper, HandlerUtil handlerUtil, ToastController toastController, SamsungGlobalActionsPresenter samsungGlobalActionsPresenter, boolean z) {
        this.mContext = context;
        this.mParentView = extendableGlobalActionsView;
        this.mFeatureFactory = featureFactory;
        this.mConditionChecker = conditionChecker;
        this.mResourceFactory = resourceFactory;
        this.mCoverUtilWrapper = coverUtilWrapper;
        this.mLogWrapper = logWrapper;
        this.mHandlerUtil = handlerUtil;
        this.mWindowManagerUtil = windowManagerUtils;
        this.mToastController = toastController;
        this.mPresenter = samsungGlobalActionsPresenter;
    }

    public final ContentView createContentView(Dialog dialog) {
        if (this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_SIDE_VIEW_COVER_CLOSED)) {
            return new SideCoverContentView(this.mContext, this.mParentView, this.mConditionChecker, this.mResourceFactory, this.mCoverUtilWrapper, this.mLogWrapper, this.mHandlerUtil, this.mToastController, dialog);
        }
        if (this.mConditionChecker.isEnabled(SystemUIConditions.IS_MINI_SVIEW_COVER_CLOSED)) {
            return new MiniSViewCoverContentView(this.mContext, this.mParentView, this.mConditionChecker, this.mResourceFactory, this.mCoverUtilWrapper, this.mLogWrapper, this.mHandlerUtil, this.mToastController, dialog);
        }
        ConditionChecker conditionChecker = this.mConditionChecker;
        SystemConditions systemConditions = SystemConditions.FRONT_LARGE_COVER_DISPLAY;
        if ((!conditionChecker.isEnabled(systemConditions) && this.mConditionChecker.isEnabled(SystemConditions.SUPPORT_SECONDARY_DISPLAY_AS_COVER) && this.mConditionChecker.isEnabled(SystemConditions.IS_FOLDED)) || this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_CAMERA_VIEW_COVER_CLOSED)) {
            return new FrontCoverContentView(this.mContext, this.mParentView, this.mConditionChecker, this.mResourceFactory, this.mLogWrapper, this.mHandlerUtil, this.mToastController, this.mPresenter);
        }
        if (this.mConditionChecker.isEnabled(systemConditions) && this.mConditionChecker.isEnabled(SystemConditions.SUPPORT_SECONDARY_DISPLAY_AS_COVER) && this.mConditionChecker.isEnabled(SystemConditions.IS_FOLDED)) {
            return new FrontLargeCoverContentView(this.mContext, this.mParentView, this.mFeatureFactory, this.mConditionChecker, this.mResourceFactory, this.mLogWrapper, this.mHandlerUtil, this.mToastController, this.mPresenter);
        }
        return new GlobalActionsContentView(this.mContext, this.mParentView, this.mFeatureFactory, this.mConditionChecker, this.mWindowManagerUtil, this.mResourceFactory, this.mLogWrapper, dialog, this.mConditionChecker.isEnabled(SystemUIConditions.IS_CLEAR_COVER_CLOSED));
    }
}
