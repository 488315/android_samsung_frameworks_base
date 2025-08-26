package com.android.systemui.controls.controller;

import com.android.systemui.util.UserAwareController;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public interface ControlsBindingController extends UserAwareController {

    public interface LoadCallback extends Consumer {
        void error(String str);
    }
}
