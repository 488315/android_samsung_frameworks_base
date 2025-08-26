package com.android.keyguard;

/* loaded from: classes.dex */
public interface KeyguardSecurityView {
    boolean needsInput();

    default void onStartingToHide() {
    }
}
