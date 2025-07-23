package com.android.systemui.demomode;

import android.os.Bundle;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface DemoModeCommandReceiver {
    void dispatchDemoCommand(Bundle bundle, String str);

    default void onDemoModeFinished() {
    }
}
