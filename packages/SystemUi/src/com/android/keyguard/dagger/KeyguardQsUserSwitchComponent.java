package com.android.keyguard.dagger;

import android.widget.FrameLayout;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface KeyguardQsUserSwitchComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        KeyguardQsUserSwitchComponent build(FrameLayout frameLayout);
    }

    KeyguardQsUserSwitchController getKeyguardQsUserSwitchController();
}
