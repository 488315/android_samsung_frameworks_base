package com.android.wm.shell.sysui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ShellInterface {
    default boolean handleCommand(PrintWriter printWriter, String[] strArr) {
        return false;
    }

    default void createExternalInterfaces(Bundle bundle) {
    }

    default void dump(PrintWriter printWriter) {
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
