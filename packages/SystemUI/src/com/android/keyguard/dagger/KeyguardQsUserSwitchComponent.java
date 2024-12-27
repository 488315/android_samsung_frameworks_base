package com.android.keyguard.dagger;

import android.widget.FrameLayout;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController;

public interface KeyguardQsUserSwitchComponent {

    public interface Factory {
        KeyguardQsUserSwitchComponent build(FrameLayout frameLayout);
    }

    KeyguardQsUserSwitchController getKeyguardQsUserSwitchController();
}
