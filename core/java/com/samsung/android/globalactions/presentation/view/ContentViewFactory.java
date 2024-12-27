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

    public ContentViewFactory(
            Context context,
            ExtendableGlobalActionsView dialogBaseView,
            FeatureFactory featureFactory,
            ConditionChecker conditionChecker,
            WindowManagerUtils windowManagerUtil,
            ResourceFactory resourceFactory,
            LogWrapper logWrapper,
            HandlerUtil handlerUtil,
            ToastController toastController,
            SamsungGlobalActionsPresenter presenter,
            boolean fromSystemServer) {
        this.mContext = context;
        this.mParentView = dialogBaseView;
        this.mFeatureFactory = featureFactory;
        this.mConditionChecker = conditionChecker;
        this.mResourceFactory = resourceFactory;
        this.mLogWrapper = logWrapper;
        this.mHandlerUtil = handlerUtil;
        this.mWindowManagerUtil = windowManagerUtil;
        this.mToastController = toastController;
        this.mPresenter = presenter;
        this.mFromSystemServer = fromSystemServer;
    }

    @Override // com.samsung.android.globalactions.presentation.view.ContentViewFactoryBase
    public ContentView createContentView(Dialog dialog) {
        boolean clearCoverClosed =
                this.mConditionChecker.isEnabled(SystemConditions.IS_CLEAR_COVER_CLOSED);
        ContentView contentView =
                new GlobalActionsContentView(
                        this.mContext,
                        this.mParentView,
                        this.mFeatureFactory,
                        this.mConditionChecker,
                        this.mWindowManagerUtil,
                        this.mResourceFactory,
                        this.mLogWrapper,
                        dialog,
                        clearCoverClosed);
        return contentView;
    }
}
