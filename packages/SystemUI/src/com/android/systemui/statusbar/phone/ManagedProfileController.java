package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.policy.CallbackController;

public interface ManagedProfileController extends CallbackController {

    public interface Callback {
        void onManagedProfileChanged();

        void onManagedProfileRemoved();
    }
}
