package com.android.keyguard.dagger;

import android.widget.FrameLayout;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public interface KeyguardQsUserSwitchComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        KeyguardQsUserSwitchComponent build(FrameLayout frameLayout);
    }

    KeyguardQsUserSwitchController getKeyguardQsUserSwitchController();
}
