package com.android.systemui.demomode;

import android.os.Bundle;

/* loaded from: classes2.dex */
public interface DemoModeCommandReceiver {
    void dispatchDemoCommand(Bundle bundle, String str);

    default void onDemoModeFinished() {
    }
}
