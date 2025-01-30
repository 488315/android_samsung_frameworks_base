package com.android.wm.shell.sysui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
