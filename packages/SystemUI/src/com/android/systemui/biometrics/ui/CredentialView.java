package com.android.systemui.biometrics.ui;

import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.AuthPanelController;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;

/* loaded from: classes.dex */
public interface CredentialView {

    public interface Host {
    }

    void init(CredentialViewModel credentialViewModel, AuthContainerView authContainerView, AuthPanelController authPanelController, boolean z, Spaghetti.Callback callback);
}
