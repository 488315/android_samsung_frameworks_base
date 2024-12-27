package com.android.systemui.controls.management;

import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.UserAwareController;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public interface ControlsListingController extends CallbackController, UserAwareController {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface ControlsListingCallback {
        void onServicesUpdated(List list);
    }
}
