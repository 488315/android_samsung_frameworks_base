package com.android.systemui.controls.controller;

import com.android.systemui.util.UserAwareController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface ControlsBindingController extends UserAwareController {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface LoadCallback extends Consumer {
        void error(String str);
    }
}
