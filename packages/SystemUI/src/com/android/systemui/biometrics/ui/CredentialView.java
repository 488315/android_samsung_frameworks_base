package com.android.systemui.biometrics.ui;

import com.android.systemui.biometrics.AuthPanelController;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;

public interface CredentialView {

    public interface Host {
    }

    void init(CredentialViewModel credentialViewModel, Host host, AuthPanelController authPanelController, boolean z, Spaghetti.Callback callback);
}
