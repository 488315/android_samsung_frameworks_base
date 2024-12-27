package com.android.keyguard.dagger;

import android.view.Display;
import com.android.keyguard.KeyguardStatusView;
import com.android.keyguard.KeyguardStatusViewController;

public interface KeyguardStatusViewComponent {

    public interface Factory {
        KeyguardStatusViewComponent build(KeyguardStatusView keyguardStatusView, Display display);
    }

    KeyguardStatusViewController getKeyguardStatusViewController();
}
