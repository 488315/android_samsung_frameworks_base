package com.android.systemui.demomode;

import android.os.Bundle;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface DemoModeCommandReceiver {
    void dispatchDemoCommand(Bundle bundle, String str);

    default void onDemoModeFinished() {
    }
}
