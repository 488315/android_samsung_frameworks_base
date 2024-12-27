package com.android.keyguard.dagger;

import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherView;

public interface KeyguardUserSwitcherComponent {

    public interface Factory {
        KeyguardUserSwitcherComponent build(KeyguardUserSwitcherView keyguardUserSwitcherView);
    }

    KeyguardUserSwitcherController getKeyguardUserSwitcherController();
}
