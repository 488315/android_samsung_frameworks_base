package com.android.wm.shell.sysui;

/* loaded from: classes3.dex */
public interface KeyguardChangeListener {
    void onKeyguardVisibilityChanged(boolean z, boolean z2, boolean z3);

    default void onKeyguardDismissAnimationFinished() {
    }
}
