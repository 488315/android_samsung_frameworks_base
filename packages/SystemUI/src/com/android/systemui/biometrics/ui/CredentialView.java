package com.android.systemui.biometrics.ui;

import com.android.systemui.biometrics.AuthPanelController;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public interface CredentialView {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Host {
    }

    void init(CredentialViewModel credentialViewModel, Host host, AuthPanelController authPanelController, boolean z, Spaghetti.Callback callback);
}
