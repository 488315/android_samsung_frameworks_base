package com.samsung.android.globalactions.presentation;

import android.net.Uri;

import com.samsung.android.globalactions.presentation.view.ExtendableGlobalActionsView;
import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;

/* loaded from: classes6.dex */
public interface SamsungGlobalActions {
    void addAction(ActionViewModel actionViewModel);

    void clearActions(String str);

    void confirmAction(ActionViewModel actionViewModel);

    void confirmSafeMode(int i);

    void dismissDialog(boolean z);

    ExtendableGlobalActionsView getGlobalActionsView();

    void hideDialogOnSecureConfirm();

    boolean isActionConfirming();

    boolean isDeviceProvisioned();

    void onCancelDialog();

    void onShowDialog();

    void registerContentObserver(Uri uri, Runnable runnable);

    void registerSecureConfirmAction(ActionViewModel actionViewModel);

    void setDisabled();

    void setKeyguardShowing(boolean z);

    void setOverrideDefaultActions(boolean z);

    default int getSideKeyType() {
        return -1;
    }
}
