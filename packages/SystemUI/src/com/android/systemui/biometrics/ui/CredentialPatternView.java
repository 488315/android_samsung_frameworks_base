package com.android.systemui.biometrics.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import com.android.systemui.biometrics.AuthPanelController;
import com.android.systemui.biometrics.ui.CredentialView;
import com.android.systemui.biometrics.ui.binder.CredentialViewBinder;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;

public final class CredentialPatternView extends LinearLayout implements CredentialView, View.OnApplyWindowInsetsListener {
    public CredentialPatternView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.biometrics.ui.CredentialView
    public final void init(CredentialViewModel credentialViewModel, CredentialView.Host host, AuthPanelController authPanelController, boolean z, Spaghetti.Callback callback) {
        CredentialViewBinder.bind$default(this, host, credentialViewModel, authPanelController, z, callback);
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        setPadding(0, windowInsets.getInsets(WindowInsets.Type.statusBars()).top, 0, windowInsets.getInsets(WindowInsets.Type.navigationBars()).bottom);
        return WindowInsets.CONSUMED;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setOnApplyWindowInsetsListener(this);
    }
}
