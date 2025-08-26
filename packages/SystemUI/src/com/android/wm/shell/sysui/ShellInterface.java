package com.android.wm.shell.sysui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import java.io.PrintWriter;
import java.util.List;

/* loaded from: classes3.dex */
public interface ShellInterface {
    default boolean handleCommand(PrintWriter printWriter, String[] strArr) {
        return false;
    }

    default void createExternalInterfaces(Bundle bundle) {
    }

    default void dump(PrintWriter printWriter) {
    }

    default void onBeforeUserSwitching(int i) {
    }

    default void onConfigurationChanged(Configuration configuration) {
    }

    default void onUserProfilesChanged(List list) {
    }

    default void onInit() {
    }

    default void onKeyguardDismissAnimationFinished() {
    }

    default void onUserChanged(int i, Context context) {
    }

    default void onKeyguardVisibilityChanged(boolean z, boolean z2, boolean z3) {
    }
}
