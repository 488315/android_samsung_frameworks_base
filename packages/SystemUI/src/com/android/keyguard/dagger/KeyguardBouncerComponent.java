package com.android.keyguard.dagger;

import android.view.ViewGroup;
import com.android.keyguard.KeyguardSecSecurityContainerController;

public interface KeyguardBouncerComponent {

    public interface Factory {
        KeyguardBouncerComponent create(ViewGroup viewGroup);
    }

    KeyguardSecSecurityContainerController getSecurityContainerController();
}
