package com.samsung.android.globalactions.presentation.viewmodel;

public class ForceRestartMessageActionViewModel implements ActionViewModel {
    private ActionInfo mInfo;

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public ActionInfo getActionInfo() {
        return this.mInfo;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void setActionInfo(ActionInfo info) {
        this.mInfo = info;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onPress() {}
}
