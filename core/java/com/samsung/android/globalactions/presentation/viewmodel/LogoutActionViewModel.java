package com.samsung.android.globalactions.presentation.viewmodel;

import android.app.ActivityManager;
import android.os.RemoteException;

import com.samsung.android.globalactions.presentation.SamsungGlobalActions;
import com.samsung.android.globalactions.util.HandlerUtil;
import com.samsung.android.globalactions.util.LogWrapper;

public class LogoutActionViewModel implements ActionViewModel {
    private static final String TAG = "LogoutActionViewModel";
    private final SamsungGlobalActions mGlobalActions;
    private final HandlerUtil mHandler;
    private ActionInfo mInfo;
    private final LogWrapper mLogWrapper;

    public LogoutActionViewModel(
            SamsungGlobalActions samsungGlobalActions,
            HandlerUtil handlerUtil,
            LogWrapper logWrapper) {
        this.mGlobalActions = samsungGlobalActions;
        this.mHandler = handlerUtil;
        this.mLogWrapper = logWrapper;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public ActionInfo getActionInfo() {
        return this.mInfo;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void setActionInfo(ActionInfo info) {
        this.mInfo = info;
    }

    @Override // com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel
    public void onPress() {
        this.mHandler.postDelayed(
                new Runnable() { // from class:
                                 // com.samsung.android.globalactions.presentation.viewmodel.LogoutActionViewModel$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        LogoutActionViewModel.this.lambda$onPress$0();
                    }
                },
                500L);
        this.mGlobalActions.dismissDialog(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPress$0() {
        try {
            int currentUserID = ActivityManager.getService().getCurrentUser().id;
            ActivityManager.getService().switchUser(0);
            ActivityManager.getService().stopUser(currentUserID, true, null);
        } catch (RemoteException re) {
            this.mLogWrapper.e(TAG, "Couldn't logout user " + re);
        } catch (NullPointerException e) {
            this.mLogWrapper.e(TAG, "getCurrentUser() return null");
        }
    }
}
