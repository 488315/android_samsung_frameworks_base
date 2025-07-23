package com.android.wm.shell.sysui;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface KeyguardChangeListener {
    void onKeyguardVisibilityChanged(boolean z, boolean z2, boolean z3);

    default void onKeyguardDismissAnimationFinished() {
    }
}
