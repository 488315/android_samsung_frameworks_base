package com.android.systemui.controls.management;

import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.UserAwareController;
import java.util.List;

/* loaded from: classes2.dex */
public interface ControlsListingController extends CallbackController, UserAwareController {

    public interface ControlsListingCallback {
        void onServicesUpdated(List list);
    }
}
