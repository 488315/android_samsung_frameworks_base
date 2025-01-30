package com.android.systemui.biometrics.ui;

import com.android.systemui.biometrics.AuthPanelController;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface CredentialView {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Host {
    }

    void init(CredentialViewModel credentialViewModel, Host host, AuthPanelController authPanelController, boolean z);
}
