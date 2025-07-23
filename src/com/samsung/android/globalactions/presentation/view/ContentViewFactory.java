package com.samsung.android.globalactions.presentation.view;

import android.app.Dialog;
import android.content.Context;
import com.samsung.android.globalactions.presentation.SamsungGlobalActionsPresenter;
import com.samsung.android.globalactions.presentation.features.FeatureFactory;
import com.samsung.android.globalactions.util.ConditionChecker;
import com.samsung.android.globalactions.util.HandlerUtil;
import com.samsung.android.globalactions.util.LogWrapper;
import com.samsung.android.globalactions.util.SystemConditions;
import com.samsung.android.globalactions.util.ToastController;
import com.samsung.android.globalactions.util.WindowManagerUtils;

/* loaded from: classes6.dex */
public class ContentViewFactory implements ContentViewFactoryBase {
    private final ConditionChecker mConditionChecker;
    private final Context mContext;
    private final FeatureFactory mFeatureFactory;
    private final boolean mFromSystemServer;
    private final HandlerUtil mHandlerUtil;
    private final LogWrapper mLogWrapper;
    private final ExtendableGlobalActionsView mParentView;
    private final SamsungGlobalActionsPresenter mPresenter;
    private final ResourceFactory mResourceFactory;
    private final ToastController mToastController;
    private final WindowManagerUtils mWindowManagerUtil;

    public ContentViewFactory(Context context, ExtendableGlobalActionsView extendableGlobalActionsView, FeatureFactory featureFactory, ConditionChecker conditionChecker, WindowManagerUtils windowManagerUtils, ResourceFactory resourceFactory, LogWrapper logWrapper, HandlerUtil handlerUtil, ToastController toastController, SamsungGlobalActionsPresenter samsungGlobalActionsPresenter, boolean z) {
        this.mContext = context;
        this.mParentView = extendableGlobalActionsView;
        this.mFeatureFactory = featureFactory;
        this.mConditionChecker = conditionChecker;
        this.mResourceFactory = resourceFactory;
        this.mLogWrapper = logWrapper;
        this.mHandlerUtil = handlerUtil;
        this.mWindowManagerUtil = windowManagerUtils;
        this.mToastController = toastController;
        this.mPresenter = samsungGlobalActionsPresenter;
        this.mFromSystemServer = z;
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentViewFactoryBase
    public ContentView createContentView(Dialog dialog) {
        return new GlobalActionsContentView(this.mContext, this.mParentView, this.mFeatureFactory, this.mConditionChecker, this.mWindowManagerUtil, this.mResourceFactory, this.mLogWrapper, dialog, this.mConditionChecker.isEnabled(SystemConditions.IS_CLEAR_COVER_CLOSED));
    }
}
