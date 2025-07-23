package com.android.systemui.controls.management;

import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.UserAwareController;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ControlsListingController extends CallbackController, UserAwareController {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ControlsListingCallback {
        void onServicesUpdated(List list);
    }
}
