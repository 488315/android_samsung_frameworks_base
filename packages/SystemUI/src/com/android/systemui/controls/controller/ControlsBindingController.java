package com.android.systemui.controls.controller;

import com.android.systemui.util.UserAwareController;
import java.util.function.Consumer;

public interface ControlsBindingController extends UserAwareController {

    public interface LoadCallback extends Consumer {
        void error(String str);
    }
}
