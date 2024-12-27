package com.android.keyguard.dagger;

import android.view.Display;
import com.android.keyguard.KeyguardStatusView;
import com.android.keyguard.KeyguardStatusViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface KeyguardStatusViewComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        KeyguardStatusViewComponent build(KeyguardStatusView keyguardStatusView, Display display);
    }

    KeyguardStatusViewController getKeyguardStatusViewController();
}
