package com.android.systemui.controls.management;

import com.android.systemui.statusbar.policy.CallbackController;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ControlsListingController extends CallbackController {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ControlsListingCallback {
        void onServicesUpdated(List list);
    }
}
