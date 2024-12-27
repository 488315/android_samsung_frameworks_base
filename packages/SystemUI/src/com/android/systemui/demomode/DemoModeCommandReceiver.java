package com.android.systemui.demomode;

import android.os.Bundle;

public interface DemoModeCommandReceiver {
    void dispatchDemoCommand(Bundle bundle, String str);

    default void onDemoModeFinished() {
    }
}
