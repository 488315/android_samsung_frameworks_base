package com.android.systemui.controls.controller;

import com.android.systemui.util.UserAwareController;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ControlsBindingController extends UserAwareController {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface LoadCallback extends Consumer {
        void error(String str);
    }
}
