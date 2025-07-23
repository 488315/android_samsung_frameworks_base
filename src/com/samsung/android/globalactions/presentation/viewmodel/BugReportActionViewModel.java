package com.samsung.android.globalactions.presentation.viewmodel;

import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.util.ResourcesWrapper;
import com.samsung.android.globalactions.util.SystemController;

/* loaded from: classes6.dex */
public class BugReportActionViewModel implements ActionViewModel {
    private final SamsungGlobalActions mGlobalActions;
    private ActionInfo mInfo;
    private final ResourcesWrapper mResourcesWrapper;
    private final SystemController mSystemController;

    public BugReportActionViewModel(SamsungGlobalActions samsungGlobalActions, SystemController systemController, ResourcesWrapper resourcesWrapper) {
        this.mGlobalActions = samsungGlobalActions;
        this.mSystemController = systemController;
        this.mResourcesWrapper = resourcesWrapper;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public ActionInfo getActionInfo() {
        return this.mInfo;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void setActionInfo(ActionInfo actionInfo) {
        this.mInfo = actionInfo;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onPress() {
        this.mSystemController.doBugReport(false);
        this.mGlobalActions.dismissDialog(true);
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onLongPress() {
        this.mSystemController.doBugReport(true);
    }
}
